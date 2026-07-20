package com.sopia.studyman.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javassist.bytecode.SignatureAttribute.ClassType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.ClassPara;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.ELUser;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.StudyConstants;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;

public class StudyClassDaoImpl implements StudyClassDao {
	private static final Log logger = LogFactory
			.getLog(StudyClassDaoImpl.class);

	private int getMyclassBxCount(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int b = 0;
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select count(sc.courseid) from study_course sc
			// left join study_quizinfo sqi on sqi.id= sc.sqiid where
			// sqi.ispassed =1 and sc.classid = ? and sc.userid = ? and
			// sc.status = 0");
			// ps.setInt(1, classid);
			// ps.setInt(2, userid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// return rs.getInt(1);
			// }
			ps = ct
					.prepareStatement("SELECT courseid,credit,setcredit,getcredit FROM class_course where classid =? and status = 0");
			ps.setInt(1, classid);
			// ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				// PreparedStatement ps1 = ct
				// .prepareStatement("select user_p_course( ?, ?,?,?) from dual
				// ");
				PreparedStatement ps1 = ct
						.prepareStatement("select user_p_course2( ?, ?,?,?)  from dual ");// !!
				ps1.setInt(1, rs.getInt(1));
				ps1.setInt(2, userid);
				ps1.setInt(3, rs.getInt(4));
				ps1.setInt(4, classid);
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next())
					if (rs1.getInt(1) == 1) {
						b = b + 1;
					}
				rs1.close();
				ps1.close();
				// return rs.getInt(1);
			}

		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	private int getMyclassXxCredit(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int b = 0;
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select sum(c.credit) from study_course sc left
			// join study_quizinfo sqi on sqi.id= sc.sqiid left join course c on
			// c.id= sc.courseid where sqi.ispassed =1 and sc.classid = ? and
			// sc.userid = ? and sc.status = 1");
			// ps.setInt(1, classid);
			// ps.setInt(2, userid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// return rs.getInt(1);
			// }
			ps = ct
					.prepareStatement("SELECT courseid,credit,setcredit,getcredit FROM class_course where classid =? and status = 1");
			ps.setInt(1, classid);
			// ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				// PreparedStatement ps1 = ct
				// .prepareStatement("select user_p_course( ?, ?,?) from dual
				// ");
				PreparedStatement ps1 = ct
						.prepareCall("select  user_p_course2( ?, ?,?,?)  from dual ");
				ps1.setInt(1, rs.getInt(1));
				ps1.setInt(2, userid);
				ps1.setInt(3, rs.getInt(4));
				ps1.setInt(4, classid);
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next())
					if (rs1.getInt(1) == 1) {
						b = b + rs.getInt(3);
					}
				// return rs.getInt(1);
				rs1.close();
				ps1.close();
			}

		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	public List<MyClass> listMyStudyClass(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyClass> mcls = new ArrayList<MyClass>();
		try {
			String sql = "select cl.id,cl.name,cl.optionalcredit,cl.createtime,ca.applyDate ,"
					+ "(select count(*) from class_course ccb where ccb.classid = cl.id and ccb.status = 0) as bxCount,"
					+ "(select sum(ccx.setcredit) from course c left join class_course ccx on ccx.courseid= c.id where ccx.classid = cl.id and ccx.status =1) as xxCredit,"
					+ " eu.id,eu.realname,cl.certificatename,cl.starttime,cl.finishtime  from study_class ca ,elclass cl,eluser eu "
					+ "where cl.creater = eu.id and ca.userid = ? and cl.status not in (9) and cl.isNormal = 1 and ca.classid = cl.id order by ca.applyDate desc ";// and
			// cl.status
			// in
			// (1,4)
			// 1已开通，4申请修改
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CLASS_BYUID));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyClass m = new MyClass();
				m.setElClass(new ElClass(rs.getInt(1), rs.getString(2)));
				m.getElClass().setOptionalcredit(rs.getInt(3));
				m.getElClass().setCreatetime(rs.getDate(4));
				m.setBegintime(rs.getDate(5));
				m.getElClass().setBxCount(rs.getInt(6));
				m.setBxCount(getMyclassBxCount(userid, m.getElClass().getId()));
				m.getElClass().setXxCredit(rs.getInt(7));
				m
						.setXxCredit(getMyclassXxCredit(userid, m.getElClass()
								.getId()));
				m.getElClass().setCreater(
						new ELUser(rs.getInt(8), rs.getString(9)));
				m.getElClass().setCertificatename(rs.getString(10));
				// m.setStarttime(rs.getTimestamp("starttime"));
				// m.setFinishtime(rs.getTimestamp("finishtime"));
				m.getElClass().setStarttime(rs.getTimestamp("starttime"));
				m.getElClass().setFinishtime(rs.getTimestamp("finishtime"));

				PreparedStatement ps1 = ct.prepareStatement(ElQuerySql
						.getSQL(StudyConstants.STUDY_CLASS_FINISH_INFO_BYUID));
				ps1.setInt(1, userid);
				ps1.setInt(2, m.getElClass().getId());
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next()) {
					m.setStatus(rs1.getInt(1));
					if (m.getStatus() != StudyConstants.STUDY_CLASS_STATUS_WAIT) {
						m.setEndtime(rs1.getTimestamp(2));
					}
				} else {
					m.setStatus(0);
				}
				rs1.close();
				ps1.close();
				mcls.add(m);
			}
		} catch (Exception e) {
			logger.error("在学培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mcls;
	}

	public List<MyClass> listMyStudyClass(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyClass> mcls = new ArrayList<MyClass>();
		try {
			String sql = "select * from (select t.*, rownum rn from  (select cl.id,cl.name,cl.optionalcredit,cl.createtime,ca.applyDate ,"
					+ "(select count(*) from class_course ccb where ccb.classid = cl.id and ccb.status = 0) as bxCount,"
					+ "(select sum(ccx.setcredit) from course c left join class_course ccx on ccx.courseid= c.id where ccx.classid = cl.id and ccx.status =1) as xxCredit,"
					+ " eu.id eid,eu.realname,cl.certificatename,cl.starttime,cl.finishtime,cl.status classStatus,cl.isApplication,joinway,cl.mainimg  from study_class ca ,elclass cl,eluser eu "
					+ "where cl.creater = eu.id and ca.userid = ? and cl.status not in (9) and cl.isNormal = 1 and ca.classid = cl.id and ca.status!=-1 order by ca.applyDate desc  )t where rownum <= ? ) where rn>=?";// and
			// cl.status
			// in
			// (1,4)
			// 1已开通，4申请修改
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CLASS_BYUID));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyClass m = new MyClass();
				m.setElClass(new ElClass(rs.getInt(1), rs.getString(2)));
				m.getElClass().setOptionalcredit(rs.getInt(3));
				m.getElClass().setCreatetime(rs.getDate(4));
				m.setBegintime(rs.getDate(5));
				m.getElClass().setBxCount(rs.getInt(6));
				m.setBxCount(getMyclassBxCount(userid, m.getElClass().getId()));
				m.getElClass().setXxCredit(rs.getInt(7));
				m
						.setXxCredit(getMyclassXxCredit(userid, m.getElClass()
								.getId()));
				m.getElClass().setCreater(
						new ELUser(rs.getInt(8), rs.getString(9)));
				m.getElClass().setCertificatename(rs.getString(10));
				// m.setStarttime(rs.getTimestamp("starttime"));
				// m.setFinishtime(rs.getTimestamp("finishtime"));
				m.getElClass().setStarttime(rs.getTimestamp("starttime"));
				m.getElClass().setFinishtime(rs.getTimestamp("finishtime"));
				m.getElClass().setStatus(rs.getInt("classStatus"));
				m.getElClass().setIsApplication(rs.getInt("isApplication"));
				m.getElClass().setIsjoin(
						rs.getInt("joinway") == 1 ? "申请" : "分配");
				m.getElClass().setMainimg(rs.getString("mainimg"));
				PreparedStatement ps1 = ct.prepareStatement(ElQuerySql
						.getSQL(StudyConstants.STUDY_CLASS_FINISH_INFO_BYUID));
				ps1.setInt(1, userid);
				ps1.setInt(2, m.getElClass().getId());
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next()) {
					m.setStatus(rs1.getInt(1));
					if (m.getStatus() != StudyConstants.STUDY_CLASS_STATUS_WAIT) {
						m.setEndtime(rs1.getTimestamp(2));
					}
				} else {
					m.setStatus(0);
				}
				rs1.close();
				ps1.close();
				mcls.add(m);
			}
		} catch (Exception e) {
			logger.error("在学培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mcls;
	}

	/**
	 * 个人中心首页我的培训班
	 */
	public List<MyClass> study_index_listMyStudyClass(int userid, int number)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyClass> mcls = new ArrayList<MyClass>();
		try {
			String sql = "select t.*, rownum rn from  (select cl.id,cl.name,cl.optionalcredit,cl.createtime,ca.applyDate ,"
					+ "(select count(*) from class_course ccb where ccb.classid = cl.id and ccb.status = 0) as bxCount,"
					+ "(select sum(ccx.setcredit) from course c left join class_course ccx on ccx.courseid= c.id where ccx.classid = cl.id and ccx.status =1) as xxCredit,"
					+ " eu.id eid,eu.realname,cl.certificatename,cl.starttime,cl.finishtime,cl.status classStatus,cl.isApplication,joinway from study_class ca ,elclass cl,eluser eu "
					+ "where cl.creater = eu.id and ca.userid = ? and cl.status not in (9) and cl.isNormal = 1 and ca.classid = cl.id and ca.status!=-1 order by ca.applyDate desc  )t where rownum <= ?";// and
			// cl.status
			// in
			// (1,4)
			// 1已开通，4申请修改
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CLASS_BYUID));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, number);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyClass m = new MyClass();
				m.setElClass(new ElClass(rs.getInt(1), rs.getString(2)));
				m.getElClass().setOptionalcredit(rs.getInt(3));
				m.getElClass().setCreatetime(rs.getDate(4));
				m.setBegintime(rs.getDate(5));
				m.getElClass().setBxCount(rs.getInt(6));
				m.setBxCount(getMyclassBxCount(userid, m.getElClass().getId()));
				m.getElClass().setXxCredit(rs.getInt(7));
				m
						.setXxCredit(getMyclassXxCredit(userid, m.getElClass()
								.getId()));
				m.getElClass().setCreater(
						new ELUser(rs.getInt(8), rs.getString(9)));
				m.getElClass().setCertificatename(rs.getString(10));
				// m.setStarttime(rs.getTimestamp("starttime"));
				// m.setFinishtime(rs.getTimestamp("finishtime"));
				m.getElClass().setStarttime(rs.getTimestamp("starttime"));
				m.getElClass().setFinishtime(rs.getTimestamp("finishtime"));
				m.getElClass().setStatus(rs.getInt("classStatus"));
				m.getElClass().setIsApplication(rs.getInt("isApplication"));
				m.getElClass().setIsjoin(
						rs.getInt("joinway") == 1 ? "申请" : "分配");
				PreparedStatement ps1 = ct.prepareStatement(ElQuerySql
						.getSQL(StudyConstants.STUDY_CLASS_FINISH_INFO_BYUID));
				ps1.setInt(1, userid);
				ps1.setInt(2, m.getElClass().getId());
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next()) {
					m.setStatus(rs1.getInt(1));
					if (m.getStatus() != StudyConstants.STUDY_CLASS_STATUS_WAIT) {
						m.setEndtime(rs1.getTimestamp(2));
					}
				} else {
					m.setStatus(0);
				}
				rs1.close();
				ps1.close();
				mcls.add(m);
			}
		} catch (Exception e) {
			logger.error("个人中心在学培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mcls;
	}

	public int listMyStudyClassSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int a = 0;
		try {
			String sql = "select count(*) from study_class ca ,elclass cl,eluser eu "
					+ "where cl.creater = eu.id and ca.userid = ? and cl.status not in (9) and cl.isNormal = 1 and ca.classid = cl.id and ca.status!=-1";// and
			// cl.status
			// in
			// (1,4)
			// 1已开通，4申请修改
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CLASS_BYUID));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				a = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("在学培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return a;
	}

	public List<MyClass> OnloacUcenterMyclass(int userid) throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyClass> mcls = new ArrayList<MyClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CLASS_ONLOADUCENTERMYCLASS));// 如需要改开通状态，改这里cl.status
			// =
			// 1（？）
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				if (i > 3) {
					break;
				}
				MyClass m = new MyClass();
				m.setElClass(new ElClass(rs.getInt(1), rs.getString(2)));
				m.getElClass().setOptionalcredit(rs.getInt(3));
				m.getElClass().setCreatetime(rs.getDate(4));
				m.setBegintime(rs.getDate(5));
				m.getElClass().setBxCount(rs.getInt(6));
				m.setBxCount(getMyclassBxCount(userid, m.getElClass().getId()));
				m.getElClass().setXxCredit(rs.getInt(7));
				m
						.setXxCredit(getMyclassXxCredit(userid, m.getElClass()
								.getId()));
				m.getElClass().setCreater(
						new ELUser(rs.getInt(8), rs.getString(9)));
				m.getElClass().setCertificatename(rs.getString(10));
				PreparedStatement ps1 = ct
						.prepareStatement("select ec.status ,sc.applyDate from study_class sc , elclass ec where sc.classid=ec.id and userid= ? and"
								+ " classid = ? and ec.status = 1");
				ps1.setInt(1, userid);
				ps1.setInt(2, m.getElClass().getId());
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next()) {
					m.setStatus(rs1.getInt(1));
					if (m.getStatus() != StudyConstants.STUDY_CLASS_STATUS_WAIT) {
						m.setEndtime(rs1.getTimestamp(2));
					}
				} else {
					m.setStatus(0);
				}
				rs1.close();
				ps1.close();
				mcls.add(m);
			}
		} catch (Exception e) {
			logger.error("在学培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mcls;
	}

	/**
	 * 获取学员所有培训班(分页)
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyClass> OnloacUcenterMyclass(int userid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyClass> mcls = new ArrayList<MyClass>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CLASS_ONLOADUCENTERMYCLASS));//如需要改开通状态，改这里cl.status
			// = 1（？）
			// ps = ct.prepareStatement("select * from (select t.*,rownum rn
			// from (select cl.id
			// clid,cl.name,cl.optionalcredit,cl.createtime,ca.applyDate
			// ,(select count(*) from class_course ccb where ccb.classid = cl.id
			// and ccb.status = 0) as bxCount,(select sum(c.credit) from course
			// c left join class_course ccx on ccx.courseid= c.id where
			// ccx.classid = cl.id and ccx.status =1) as xxCredit,
			// eu.id,eu.realname,cl.certificatename from study_class ca ,elclass
			// cl,eluser eu where cl.creater = eu.id and ca.userid = ? and
			// ca.classid = cl.id and cl.status = 1 order by ca.applyDate desc )
			// t where rownum <=?) where rn>=?");
			// ps = ct.prepareStatement("select * from (select t.*,rownum rn
			// from (select cl.id
			// clid,cl.name,cl.optionalcredit,cl.createtime,ca.applyDate
			// ,(select count(*) from class_course ccb where ccb.classid = cl.id
			// and ccb.status = 0) as bxCount,(select sum(c.credit) from course
			// c left join class_course ccx on ccx.courseid= c.id where
			// ccx.classid = cl.id and ccx.status =1) as xxCredit,
			// eu.id,eu.realname,cl.certificatename,cl.starttime,cl.finishtime
			// from study_class ca ,elclass cl,eluser eu where cl.creater =
			// eu.id and ca.userid = ? and ca.classid = cl.id and cl.isnormal=1
			// and cl.status!=9 order by cl.starttime desc ) t where rownum <=?)
			// where rn>=?");
			ps = ct
					.prepareStatement("select * from (select t.*,rownum rn from ("
							+ " select cl.id clid,cl.name,cl.optionalcredit,cl.createtime,ca.applyDate ,"
							+
							// "(select count(*) from class_course ccb where
							// ccb.classid = cl.id and ccb.status = 0) as
							// bxCount,(select sum(c.credit) from course c left
							// join class_course ccx on ccx.courseid= c.id where
							// ccx.classid = cl.id and ccx.status =1) as
							// xxCredit," +
							" eu.id,eu.realname,cl.certificatename,cl.starttime,cl.finishtime,cl.status classStatus,cl.isApplication "
							+ " from study_class ca ,elclass cl,eluser eu "
							+ " where cl.creater = eu.id and ca.userid = ? and ca.classid = cl.id and cl.isnormal=1 and cl.status!=9 and cl.finishtime>sysdate and starttime<sysdate and ca.status!=-1 "
							+ " order by cl.starttime desc ) t where rownum <=?) where rn>=?");
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			String className = "";
			while (rs.next()) {
				className = rs.getString(2);
				if (className.length() > 28) {
					className = className.substring(0, 28) + "...";
				}
				MyClass m = new MyClass();
				m.setElClass(new ElClass(rs.getInt(1), className));
				m.getElClass().setOptionalcredit(rs.getInt(3));
				m.getElClass().setCreatetime(rs.getDate(4));
				m.setBegintime(rs.getDate(5));
				// m.getElClass().setBxCount(rs.getInt(6));
				// m.setBxCount(getMyclassBxCount(userid,
				// m.getElClass().getId()));
				// m.getElClass().setXxCredit(rs.getInt(7));
				// m.setXxCredit(getMyclassXxCredit(userid,
				// m.getElClass().getId()));
				m.getElClass().setCreater(
						new ELUser(rs.getInt(6), rs.getString(7)));
				m.getElClass().setCertificatename(rs.getString(8));
				m.getElClass().setStarttime(rs.getTimestamp("starttime"));
				m.getElClass().setFinishtime(rs.getTimestamp("finishtime"));
				m.getElClass().setStatus(rs.getInt("classStatus"));
				m.getElClass().setIsApplication(rs.getInt("isApplication"));
				// PreparedStatement ps1 = ct.prepareStatement("select ec.status
				// ,sc.applyDate from study_class sc , elclass ec where
				// sc.classid=ec.id and userid= ? and" +
				// " classid = ? and ec.status = 1");
				// ps1.setInt(1, userid);
				// ps1.setInt(2, m.getElClass().getId());
				// ResultSet rs1 = ps1.executeQuery();
				// if (rs1.next()) {
				// m.setStatus(rs1.getInt(1));
				// if (m.getStatus() != StudyConstants.STUDY_CLASS_STATUS_WAIT)
				// {
				// m.setEndtime(rs1.getTimestamp(2));
				// }
				// } else {
				// m.setStatus(0);
				// }
				// rs1.close();
				// ps1.close();
				mcls.add(m);
			}
		} catch (Exception e) {
			logger.error("获取学员所有培训班(分页)出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mcls;
	}

	/**
	 * 获取学员所有培训班数量
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int OnloacUcenterMyclassCount(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_class ca ,elclass cl,eluser eu where cl.creater = eu.id and ca.userid = ? and ca.classid = cl.id and cl.isnormal=1 and cl.status!=9 and cl.finishtime>sysdate and cl.starttime<sysdate and ca.status!=-1");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取学员所有培训班数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取学员已结业培训班数量
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getClassYesCount(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_class sc left join elclass el on sc.classid=el.id where el.isnormal=1 and sc.userid=? and sc.certificateno is not null");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取学员已结业培训班数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取学员所有培训班数量
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getClassAllCount(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_class sc left join elclass el on sc.classid=el.id where ((el.isnormal=1 and el.status!=9) or (el.isnormal=1 and sc.certificateno is not null)) and userid=?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取学员所有培训班数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取待审核的培训班数量
	 * 
	 * @return
	 * @throws ElException
	 */
	public int getClassEndCount() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from elclass where status=3");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取待审核的培训班数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<MyClass> listMyGraduatedClass(int userid, int status)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<MyClass> cls = new ArrayList<MyClass>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select cl.id,cl.name,cl.certificatename
			// ,eu.id,eu.realname ,sc.applydate,certificateno,sc.status "
			// + " from study_class sc left join elclass cl on sc.classid =cl.id
			// left join eluser eu on sc.userid = eu.id where eu.id = ? and
			// cl.status != 9 and cl.isNormal = 1 ");
			// ps = ct
			// .prepareStatement("select cl.id,cl.name,cl.certificatename
			// ,eu.id,eu.realname
			// ,sc.applydate,certificateno,sc.status,cl.starttime,cl.finishtime
			// "
			// + " from study_class sc left join elclass cl on sc.classid =cl.id
			// left join eluser eu on sc.userid = eu.id where eu.id = ? and
			// ((cl.isNormal = 1 and cl.status!=9) or (cl.isNormal=1 and
			// sc.certificateno is not null)) ");
			ps = ct
					.prepareStatement("select cl.id,cl.name,cl.certificatename ,eu.id,eu.realname ,sc.applydate,certificateno,sc.status,cl.starttime,cl.finishtime "
							+ " from study_class sc left join elclass cl on sc.classid =cl.id left join eluser eu on sc.userid = eu.id where eu.id = ? and sc.certificateno is not null ");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				ELUser eu = new ELUser(rs.getInt(4), rs.getString(5));
				MyClass cl1 = new MyClass();
				Timestamp t = rs.getTimestamp(6);
				cl1.setStatus(rs.getInt(8));
				// if (cl1.getStatus() == 2) {
				// cl1.setCertificateno(rs.getInt(7));
				// cl1.setEndtime(t);
				// cl1.setPassed(true);
				// }
				// 先查看学员培训班是否通过再查证书编号
				this.setMyPassclass(userid, cl.getId());
				// cl1.setCertificateno(rs.getInt(7));
				cl1.setCertificateno(this.getStudyClassCertificateno(
						cl.getId(), userid));
				cl1.setEndtime(t);
				if (cl1.getCertificateno() > 0) {
					cl1.setPassed(true);
				}
				cl.setStarttime(rs.getTimestamp(9));
				cl.setFinishtime(rs.getTimestamp(10));
				cl1.setElClass(cl);
				cl1.setUser(eu);
				cls.add(cl1);
			}
		} catch (Exception e) {
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public List<MyClass> listMyGraduatedClassByNo(String no, int status)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<MyClass> cls = new ArrayList<MyClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select cl.id,cl.name,cl.certificatename ,eu.id,eu.realname ,sc.applydate,certificateno,sc.status,cl.starttime,cl.finishtime "
							+ " from study_class sc left join elclass cl on sc.classid =cl.id left join eluser eu on sc.userid = eu.id where certificateno = ? and ((cl.isNormal = 1 and cl.status!=9) or (cl.isNormal=1 and sc.certificateno is not null)) ");
			ps.setString(1, no);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				ELUser eu = new ELUser(rs.getInt(4), rs.getString(5));
				MyClass cl1 = new MyClass();
				Timestamp t = rs.getTimestamp(6);
				cl1.setStatus(rs.getInt(8));
				// if (cl1.getStatus() == 2) {
				// cl1.setCertificateno(rs.getInt(7));
				// cl1.setEndtime(t);
				// cl1.setPassed(true);
				// }
				// 先查看学员培训班是否通过再查证书编号
				this.setMyPassclass(eu.getId(), cl.getId());
				// cl1.setCertificateno(rs.getInt(7));
				cl1.setCertificateno(this.getStudyClassCertificateno(
						cl.getId(), eu.getId()));
				cl1.setEndtime(t);
				if (cl1.getCertificateno() > 0) {
					cl1.setPassed(true);
				}
				cl.setStarttime(rs.getTimestamp(9));
				cl.setFinishtime(rs.getTimestamp(10));
				cl1.setElClass(cl);
				cl1.setUser(eu);
				cls.add(cl1);
			}
		} catch (Exception e) {
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public List<MyClass> listMyGraduatedClass(int userid, int status,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<MyClass> cls = new ArrayList<MyClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.*, rownum rn from  (select cl.id,cl.name,cl.certificatename ,eu.id  eid,eu.realname ,sc.applydate,sc.certificateno,sc.status,cl.status classStatus,cl.mainimg "
							+ " from study_class sc left join elclass cl on sc.classid =cl.id left join eluser eu on sc.userid = eu.id where eu.id = ? and ((cl.isNormal = 1 and cl.status!=9) or (sc.certificateno is not null)) )t where rownum <= ? ) where rn>=? ");
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setStatus(rs.getInt("classStatus"));
				cl.setMainimg(rs.getString("mainimg"));
				ELUser eu = new ELUser(rs.getInt(4), rs.getString(5));
				MyClass cl1 = new MyClass();
				Timestamp t = rs.getTimestamp(6);
				cl1.setStatus(rs.getInt(8));
				// cl1.setCertificateno(rs.getInt(7));
				// 先查看学员培训班是否通过再查证书编号
				this.setMyPassclass(userid, cl.getId());
				// cl1.setCertificateno(rs.getInt(7));
				cl1.setCertificateno(this.getStudyClassCertificateno(
						cl.getId(), userid));
				cl1.setEndtime(t);
				if (cl1.getCertificateno() > 0) {
					cl1.setPassed(true);
				}
				cl1.setElClass(cl);
				cl1.setUser(eu);
				cls.add(cl1);
			}
		} catch (Exception e) {
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public int listMyGraduatedClassSize(int userid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int a = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*)"
							+ " from study_class sc left join elclass cl on sc.classid =cl.id left join eluser eu on sc.userid = eu.id where eu.id = ? and ((cl.isNormal = 1 and cl.status!=9) or (sc.certificateno is not null))");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				a = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("在学培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return a;
	}

	public void autoSetCourse(int classid, int status, int userid)
			throws ElException {
	}

	public List<MyCourse> listMyClassCourse(int clid, int userid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select c.id cid, c.name,c.creater, eu.realname,c.credit, c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id sqiid_,sqi.myScore,sqi.ispassed,sqi.status from study_course sc left join course c on sc.courseid = c.id left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid where sc.userid =? and sc.status = ? and sc.classid =?");

			ps.setInt(1, userid);
			ps.setInt(2, status);
			ps.setInt(3, clid);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				mc.setMyCredit(rs.getFloat(12));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(13)));
				mc.getMyExamPaper().setMyScore(rs.getInt(14));
				mc.getMyExamPaper().setIspassed(rs.getInt(15));
				mc.getMyExamPaper().setStatus(rs.getInt(16));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<MyCourse> listMyClassCourseStat(int clid, int userid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select c.id cid, c.name,c.creater, eu.realname,c.credit, c.during,c.teachername,cc.suggestcredit,cc.setcredit, cc.getcredit,cc.starttime,cc.finishtime,"
							+ "c.islink, sc.passtime passtime,sc.process, sc.passed,sc.status ,sr.ispassed,sr.myScore,sr.status,c.xx_stuats,cc.eroomid,er.title,"
							+ "er.valid ervalid,er.type ertype,er.uvalid eruvalid,er.svalid,sc.tprocess,cc.isdel,sc.passtime_2 passtime2,c.courseForm,sr.roomid srRoomid,pp.ppcount "
							+ " from (select * from study_course where userid = ? and classid = ?) sc "
							+ " right join (select * from class_course where classid =? and status = ?) cc on cc.courseid = sc.courseid and cc.classid = sc.classid "
							+ " left join course c on cc.courseid = c.id left join eluser eu on c.creater = eu.id "
							+ " left join (select * from study_room where userid=? and classid = ?) sr on sr.roomid=cc.eroomid  "
							+ " left join exam_room er on er.id = cc.eroomid "
							+ " left join (select courseid,count(id) ppcount from practicepaper where cpid=0 group by courseid) pp on cc.courseid=pp.courseid "
							+ " where sc.classid=? ");

			ps.setInt(1, userid);
			ps.setInt(2, clid);
			ps.setInt(3, clid);
			ps.setInt(4, status);
			ps.setInt(5, userid);
			ps.setInt(6, clid);
			ps.setInt(7, clid);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				c.setSuggestcredit(rs.getInt(8));
				c.setSetcredit(rs.getInt(9));
				c.setGetcredit(rs.getInt(10));
				c.setRoomstart(rs.getTimestamp(11));
				c.setRoomend(rs.getTimestamp(12));
				c.setIslink(rs.getInt(13));
				c.setXx_status(rs.getInt(21));
				c.setCourseForm(rs.getInt("courseForm"));
				MyCourse mc = new MyCourse();
				mc.setCourse(c);
				mc.setPasstime(rs.getInt(14));
				mc.setProcess(rs.getInt(15));
				mc.setPassed(rs.getInt(16) == 0 ? false : true);
				mc.setStatus(rs.getInt(17));
				mc.setIsDel(rs.getInt("isdel"));
				mc.setPasstime2(rs.getInt("passtime2"));
				mc.setCpracCount(rs.getInt("ppcount"));
				// mc.setMyCredit(rs.getFloat(12));
				// mc.setPassed(rs.getBoolean(20));

				// 设置学分 modify by luocw
				// int setcredit = rs.getInt(18);
				// int getcredit = rs.getInt(19);
				// int process = rs.getInt(9);
				// int score = rs.getInt(14);
				// int ks_pass = rs.getInt(18);
				// if (c.getGetcredit() == 1 && mc.isPassed()) {
				// mc.setMyCredit(c.getSetcredit());
				// } else if (c.getGetcredit() == 2 && ks_pass == 1) {
				// mc.setMyCredit(c.getSetcredit());
				// } else if (c.getGetcredit() == 3 && mc.isPassed()) {
				// if (ks_pass == 1) {
				// mc.setMyCredit(c.getSetcredit());
				// } else
				// mc.setMyCredit(0);
				// } else {
				// mc.setMyCredit(0);
				// }

				// mc.setMyRoom(new MyRoom());
				mc.setMyRoom(new MyRoom(rs.getInt("srRoomid")));
				if (mc.getMyRoom().getId() != 0) {
					mc.getMyRoom().setIspassed(rs.getInt(18));
					mc.getMyRoom().setMyScore(rs.getInt(19));
					mc.getMyRoom().setStatus(rs.getInt(20));
					mc.getMyRoom().setExamroom(
							new ExamRoom(rs.getInt(22), rs.getString(23)));
					mc.getMyRoom().getExamroom().setValid(rs.getInt(24));
					mc.getMyRoom().getExamroom().setType(rs.getInt(25));
					mc.getMyRoom().getExamroom().setUvalid(rs.getInt(26));
					mc.getMyRoom().getExamroom().setSvalid(rs.getInt(27));
				}
				mc.setTprocess(rs.getFloat(28));
				// mc.setPasstime2(rs.getInt(29));
				// mc.setMyCredit(rs.getFloat(30));
				if (c.getGetcredit() == 1 && mc.isPassed()) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 2
						&& mc.getMyRoom().getIspassed() == 1) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 3 && mc.isPassed()) {
					if (mc.getMyRoom().getIspassed() == 1) {
						mc.setMyCredit(c.getSetcredit());
					} else
						mc.setMyCredit(0);
				} else {
					mc.setMyCredit(0);
				}
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<MyClass> listGraduatedClass(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyClass> mcls = new ArrayList<MyClass>();
		try {
			ct = DBConnection.getConnection();
			// 我的班级列表
			ps = ct
					.prepareStatement("select cl.id ,cl.name,cl.certificatename,sc.status from elclass cl left join (select * from study_class where  userid = ?) sc on cl.id = sc.classid ");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyClass m = new MyClass();
				m.setElClass(new ElClass(rs.getInt(1), rs.getString(2)));
				m.getElClass().setCertificatename(rs.getString(3));
				m.setStatus(rs.getInt(4));
				if (m.getStatus() == 2) {
					m.setPassed(true);
				}
				mcls.add(m);
			}
		} catch (Exception e) {
			logger.error("结业班级列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mcls;
	}

	public List<MyClass> listCanGraduateClass(int userid) throws ElException {
		// 可结业
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyClass> mcls = new ArrayList<MyClass>();
		try {
			ct = DBConnection.getConnection();
			// 我的班级列表
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CLASS_CANGRADUATE_BYUID));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyClass m = new MyClass();
				m.setElClass(new ElClass(rs.getInt(1), rs.getString(2)));
				m.getElClass().setOptionalcredit(rs.getInt(3));
				m.getElClass().setCreatetime(rs.getDate(4));
				m.setBegintime(rs.getDate(5));
				m.getElClass().setBxCount(rs.getInt(6));
				m.setBxCount(getMyclassBxCount(userid, m.getElClass().getId()));
				m.getElClass().setXxCredit(rs.getInt(7));
				m
						.setXxCredit(getMyclassXxCredit(userid, m.getElClass()
								.getId()));
				if (m.getBxCount() >= m.getElClass().getBxCount()
						&& m.getXxCredit() >= m.getElClass()
								.getOptionalcredit())
					mcls.add(m);
			}

		} catch (Exception e) {
			logger.error("可结业班级列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mcls;
	}

	// private boolean checkClassPassed
	public boolean classCanGraduate(int userid, int classid,
			int cloptionalcredit) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 1.比修课修完
			boolean bx = true;
			boolean xx = true;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CLASS_CORUSE_BYSTATUS));
			ps.setInt(1, classid);
			ps.setInt(2, CourseConstants.COURSE_STUDY_STATUS_BX);
			rs = ps.executeQuery();
			// while (rs.next()) {
			// int courseid = rs.getInt(1);
			// bx = checkCourseIsPassed(courseid, userid);
			// }
			ps.close();
			rs.close();
			// 2.选修课修完
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CLASS_CORUSE_BYSTATUS));
			ps.setInt(1, classid);
			ps.setInt(2, CourseConstants.COURSE_STUDY_STATUS_XX);
			rs = ps.executeQuery();
			int score = 0;
			while (rs.next()) {
				// int courseid = rs.getInt(1);
				// xx = checkCourseIsPassed(courseid, userid);
				score += rs.getInt(2);
				if (score >= cloptionalcredit) {
					xx = true;
					break;
				} else
					xx = false;
			}
			ps.close();
			rs.close();
			return bx && xx;
		} catch (Exception e) {
			logger.error("可结业班级列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// return false;
	}

	/**
	 * 申请结业
	 */
	public void graduateClassApplay(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// if (isGraduate(userid, classid))
		// return;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select dbo.class_ispassed(?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			int ispassed = 0;
			if (rs.next()) {
				ispassed = rs.getInt(1);
			}
			rs.close();
			if (ispassed == 0) {
				ps = ct
						.prepareStatement("delete from study_class where userid = ? and classid =?");
				ps.setInt(1, userid);
				ps.setInt(2, classid);
				ps.executeUpdate();
			}
			if (ispassed == 1) {
				ps = ct.prepareStatement("{call class_pass_set ?,?,?}");
				ps.setInt(1, classid);
				ps.setInt(2, userid);
				ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				ps.executeUpdate();
			}
			if (ispassed == 2) {
				ps = ct
						.prepareStatement("select diplomatime from elclass where id=?  ");
				ps.setInt(1, classid);
				rs = ps.executeQuery();
				Timestamp diplomatime = new Timestamp(System
						.currentTimeMillis());
				if (rs.next()) {
					diplomatime = rs.getTimestamp(1);
				}
				rs.close();
				ps = ct.prepareStatement("{call class_pass_set ?,?,?}");
				ps.setInt(1, classid);
				ps.setInt(2, userid);
				ps.setTimestamp(3, diplomatime);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("培训班申请结业！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public MyClass getCraduateClass(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		MyClass cls = new MyClass();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select cl.id,cl.name,cl.certificatename ,eu.id,eu.realname,sc.applyDate,sc.certificateno from study_class sc,elclass cl,eluser eu where sc.classid = cl.id and sc.status=? and sc.userid = eu.id and sc.userid = ? and sc.classid=?");
			ps.setInt(1, ClassConstants.CLASS_APPLY_STATUS_YES);
			ps.setInt(2, userid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				ELUser eu = new ELUser(rs.getInt(4), rs.getString(5));
				cls.setEndtime(rs.getTimestamp(6));
				cls.setElClass(cl);
				cls.setUser(eu);
				cls.setCertificateno(rs.getInt(7));
			}
		} catch (Exception e) {
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	/**
	 * 查看学员证书
	 * 
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public MyClass getCraduateClass2(int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		MyClass cls = new MyClass();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select cl.id,cl.name,cl.certificatename ,eu.id,eu.realname,sc.applyDate,sc.certificateno,cl.createtime from study_class sc,elclass cl,eluser eu where sc.classid = cl.id and sc.certificateno is not null and sc.userid = eu.id and sc.userid = ? and sc.classid=?");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCreatetime(rs.getTimestamp(8));
				ELUser eu = new ELUser(rs.getInt(4), rs.getString(5));
				cls.setEndtime(rs.getTimestamp(6));
				cls.setElClass(cl);
				cls.setUser(eu);
				// cls.setCertificateno(rs.getInt(7));
				// 先查看学员培训班是否通过再查证书编号
				this.setMyPassclass(userid, cl.getId());
				// cl1.setCertificateno(rs.getInt(7));
				cls.setCertificateno(this.getStudyClassCertificateno(
						cl.getId(), userid));
			}
		} catch (Exception e) {
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	/**
	 * 自动设置全局培训班
	 */
	public void atuoSetGlobalClass(int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from elclass where global =1 ");

			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				// int group2 = rs.getInt(2);

				// graduateClassApplay(userid, id);

				if (checkClassInUser(userid, id)) {

				} else {
					ps = ct
							.prepareStatement("insert into class_apply (classid,userid,applyDate,status ) values(?,?,?,? )");
					ps.setInt(1, id);
					ps.setInt(2, userid);
					ps.setDate(3, new Date(System.currentTimeMillis()));
					ps.setInt(4, 2);
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("自动分配培训班", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkClassInUser(int userid, int classid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from class_apply where userid = ? and classid =?");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * Description: 检测并设置培训班通过
	 * 
	 * @Version1.0 2011-9-27 上午11:27:42 by 闻益舜（wenyishun110@163.com）创建
	 * @param userid
	 * @param classid
	 * @throws ElException
	 */
	public void setMyPassclass(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("call user_ispass_class (?,?)");
			// ps = ct.prepareStatement("call user_ispass_class2(?,?)");//!!
//			ps = ct.prepareStatement("call user_ispass_class2(?,?)");
			ps = ct.prepareStatement("call user_ispass_class2_1(?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.executeUpdate();
			// logger.error("检查是否通过培训班");
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	/**
	 * 获取学员在培训班是否获取证书（2：ok）
	 * 
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getStudyClassStatus(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select sc.status,sc.certificateno from
			// study_class sc where userid=? and classid=?");
			ps = ct
					.prepareStatement("select sc.status,sc.certificateno from study_class sc where userid=? and classid=? and certificateno is not null");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				// return rs.getInt("status");
				return 2;
			}
		} catch (Exception e) {
			logger.error("检测是否已学课程情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void updateXX(int courseid, int xx_status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update course set xx_stuats=? where id=?");
			ps.setInt(1, xx_status);
			ps.setInt(2, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("检测是否已学课程情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<MyCourse> listMyClassCourseStat(int clid, int userid,
			int eroomid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select c.id cid, c.name,c.creater,
			// eu.realname,c.credit,
			// c.during,c.teachername,cc.suggestcredit,cc.setcredit,
			// cc.getcredit," +
			// "c.roomstart,c.roomend,c.islink, sc.passtime/60
			// passtime,sc.process, sc.passed,sc.status ,sqi.ispassed," +
			// "sqi.id
			// sqiid_,sqi.myScore,sqi.status,c.xx_stuats,cc.starttime,cc.finishtime
			// "
			// + " from (select * from class_course where classid =? and status
			// = ?) cc left join study_course sc on cc.courseid = sc.courseid
			// left join course c on sc.courseid = c.id left join eluser eu on
			// c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid
			// "
			// + " where sc.userid =? ");

			ps = ct
					.prepareStatement("select c.id cid, c.name,c.creater, eu.realname,c.credit, c.during,c.teachername,cc.suggestcredit,cc.setcredit, cc.getcredit,"
							+ "c.roomstart,c.roomend,c.islink, sc.passtime/60 passtime,sc.process, sc.passed,sc.status ,sqi.ispassed,"
							+ "sqi.id sqiid_,sqi.myScore,sqi.status,c.xx_stuats,cc.starttime,cc.finishtime "
							+ " from (select * from class_course where classid =? and status = ? and eroomid =?) cc left join study_course sc  on cc.courseid = sc.courseid left join course c on sc.courseid = c.id left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid  "
							+ " where sc.userid =? and sc.classid=? ");

			ps.setInt(1, clid);
			ps.setInt(2, status);
			ps.setInt(3, eroomid);
			ps.setInt(4, userid);
			ps.setInt(5, clid);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				c.setSuggestcredit(rs.getInt(8));
				c.setSetcredit(rs.getInt(9));
				c.setGetcredit(rs.getInt(10));
				// c.setRoomstart(rs.getTimestamp(11));
				// c.setRoomend(rs.getTimestamp(12));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				c.setIslink(rs.getInt(13));
				c.setXx_status(rs.getInt(22));
				MyCourse mc = new MyCourse();
				mc.setCourse(c);
				mc.setPasstime(rs.getInt(14));
				mc.setProcess(rs.getInt(15));
				mc.setPassed(rs.getInt(16) == 0 ? false : true);
				mc.setStatus(rs.getInt(17));
				// mc.setMyCredit(rs.getFloat(12));
				// mc.setPassed(rs.getBoolean(20));

				// 设置学分 modify by luocw
				// int setcredit = rs.getInt(18);
				// int getcredit = rs.getInt(19);
				// int process = rs.getInt(9);
				// int score = rs.getInt(14);
				int ks_pass = rs.getInt(18);
				if (c.getGetcredit() == 1 && mc.isPassed()) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 2 && ks_pass == 1) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 3 && mc.isPassed()) {
					if (ks_pass == 1) {
						mc.setMyCredit(c.getSetcredit());
					} else
						mc.setMyCredit(0);
				} else {
					mc.setMyCredit(0);
				}

				mc.setMyExamPaper(new MyExamPaper(rs.getInt(19)));
				mc.getMyExamPaper().setMyScore(rs.getInt(20));
				mc.getMyExamPaper().setIspassed(ks_pass);
				mc.getMyExamPaper().setStatus(rs.getInt(21));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * 获取我的培训班课程信息
	 * 
	 * @param clid
	 * @param userid
	 * @param eroomid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> listMyClassCourseStat(int clid, int userid,
			String eroomid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select c.id cid, c.name,c.creater,
			// eu.realname,c.credit,
			// c.during,c.teachername,cc.suggestcredit,cc.setcredit,
			// cc.getcredit," +
			// "c.roomstart,c.roomend,c.islink, sc.passtime/60
			// passtime,sc.process, sc.passed,sc.status ,sqi.ispassed," +
			// "sqi.id
			// sqiid_,sqi.myScore,sqi.status,c.xx_stuats,cc.starttime,cc.finishtime
			// "
			// + " from (select * from class_course where classid =? and status
			// = ?) cc left join study_course sc on cc.courseid = sc.courseid
			// left join course c on sc.courseid = c.id left join eluser eu on
			// c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid
			// "
			// + " where sc.userid =? ");

			// ps = ct
			// .prepareStatement("select c.id cid, c.name,c.creater,
			// eu.realname,c.credit,
			// c.during,c.teachername,cc.suggestcredit,cc.setcredit,
			// cc.getcredit," +
			// "c.roomstart,c.roomend,c.islink, sc.passtime/60
			// passtime,sc.process, sc.passed,sc.status ,sqi.ispassed," +
			// "sqi.id
			// sqiid_,sqi.myScore,sqi.status,c.xx_stuats,cc.starttime,cc.finishtime
			// "
			// + " from (select * from class_course where classid =? and status
			// = ? and eroomid =?) cc left join study_course sc on cc.courseid =
			// sc.courseid left join course c on sc.courseid = c.id left join
			// eluser eu on c.creater = eu.id left join study_quizinfo sqi on
			// sqi.id=sc.sqiid "
			// + " where sc.userid =? and sc.classid=? ");

			ps = ct
					.prepareStatement("select c.id cid, c.name,c.creater, eu.realname,c.credit, c.during,c.teachername,cc.suggestcredit,cc.setcredit, cc.getcredit,"
							+ "c.roomstart,c.roomend,c.islink, sc.passtime/60 passtime,sc.process, sc.passed,sc.status ,sqi.ispassed,"
							+ "sqi.id sqiid_,sqi.myScore,sqi.status,c.xx_stuats,cc.starttime,cc.finishtime "
							+ " from (select * from class_course where classid =? and status = ? and eroomid in("
							+ eroomid
							+ ")) cc left join study_course sc  on cc.courseid = sc.courseid left join course c on sc.courseid = c.id left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid  "
							+ " where sc.userid =? and sc.classid=? ");

			ps.setInt(1, clid);
			ps.setInt(2, status);
			// ps.setString(3,eroomid );
			ps.setInt(3, userid);
			ps.setInt(4, clid);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				c.setSuggestcredit(rs.getInt(8));
				c.setSetcredit(rs.getInt(9));
				c.setGetcredit(rs.getInt(10));
				// c.setRoomstart(rs.getTimestamp(11));
				// c.setRoomend(rs.getTimestamp(12));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				c.setIslink(rs.getInt(13));
				c.setXx_status(rs.getInt(22));
				MyCourse mc = new MyCourse();
				mc.setCourse(c);
				mc.setPasstime(rs.getInt(14));
				mc.setProcess(rs.getInt(15));
				mc.setPassed(rs.getInt(16) == 0 ? false : true);
				mc.setStatus(rs.getInt(17));
				// mc.setMyCredit(rs.getFloat(12));
				// mc.setPassed(rs.getBoolean(20));

				// 设置学分 modify by luocw
				// int setcredit = rs.getInt(18);
				// int getcredit = rs.getInt(19);
				// int process = rs.getInt(9);
				// int score = rs.getInt(14);
				int ks_pass = rs.getInt(18);
				if (c.getGetcredit() == 1 && mc.isPassed()) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 2 && ks_pass == 1) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 3 && mc.isPassed()) {
					if (ks_pass == 1) {
						mc.setMyCredit(c.getSetcredit());
					} else
						mc.setMyCredit(0);
				} else {
					mc.setMyCredit(0);
				}

				mc.setMyExamPaper(new MyExamPaper(rs.getInt(19)));
				mc.getMyExamPaper().setMyScore(rs.getInt(20));
				mc.getMyExamPaper().setIspassed(ks_pass);
				mc.getMyExamPaper().setStatus(rs.getInt(21));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * 获取我的培训班课程信息2
	 * 
	 * @param clid
	 * @param userid
	 * @param eroomid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> listMyClassCourseStat2(int clid, int userid,
			String eroomid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		// String and;
		try {
			ct = DBConnection.getConnection();

			// if (eroomid.equals("")) {
			// and = " ";
			// } else {
			// and = "and eroomid in(" + eroomid + ")";
			// }

			// ps = ct
			// .prepareStatement("select c.id cid, c.name,c.creater,
			// eu.realname,c.credit,
			// c.during,c.teachername,cc.suggestcredit,cc.setcredit,
			// cc.getcredit," +
			// "c.roomstart,c.roomend,c.islink, sc.passtime/60
			// passtime,sc.process, sc.passed,sc.status ,sqi.ispassed," +
			// "sqi.id
			// sqiid_,sqi.myScore,sqi.status,c.xx_stuats,cc.starttime,cc.finishtime,sqi.roomid
			// "
			// + " from (select * from class_course where classid =? and status
			// = ? "+and+") cc left join study_course sc on cc.courseid =
			// sc.courseid left join course c on sc.courseid = c.id left join
			// eluser eu on c.creater = eu.id left join study_quizinfo sqi on
			// sqi.id=sc.sqiid "
			// + " where sc.userid =? and sc.classid=? ");
			ps = ct
					.prepareStatement("select c.id cid, c.name,c.creater, eu.realname,c.credit, c.during,c.teachername,cc.suggestcredit,cc.setcredit, cc.getcredit,"
							+ "c.roomstart,c.roomend,c.islink, sc.passtime/60 passtime,sc.process, sc.passed,sc.status ,sqi.ispassed,"
							+ "sqi.id sqiid_,sqi.myScore,sqi.status,c.xx_stuats,cc.starttime,cc.finishtime,sqi.roomid,er.valid ervalid,er.isnormal,er.type ertype,er.uvalid eruvalid  "
							+ " from (select * from class_course where classid =? and status = ? ) cc left join study_course sc  on cc.courseid = sc.courseid left join course c on sc.courseid = c.id left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid left join exam_room er on sqi.roomid=er.id  "
							+ " where sc.userid =? and sc.classid=? ");

			ps.setInt(1, clid);
			ps.setInt(2, status);
			// ps.setString(3,eroomid );
			ps.setInt(3, userid);
			ps.setInt(4, clid);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				c.setSuggestcredit(rs.getInt(8));
				c.setSetcredit(rs.getInt(9));
				c.setGetcredit(rs.getInt(10));
				// c.setRoomstart(rs.getTimestamp(11));
				// c.setRoomend(rs.getTimestamp(12));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				c.setIslink(rs.getInt(13));
				c.setXx_status(rs.getInt(22));
				MyCourse mc = new MyCourse();
				mc.setCourse(c);
				mc.setPasstime(rs.getInt(14));
				mc.setProcess(rs.getInt(15));
				mc.setPassed(rs.getInt(16) == 0 ? false : true);
				mc.setStatus(rs.getInt(17));
				// mc.setMyCredit(rs.getFloat(12));
				// mc.setPassed(rs.getBoolean(20));

				// 设置学分 modify by luocw
				// int setcredit = rs.getInt(18);
				// int getcredit = rs.getInt(19);
				// int process = rs.getInt(9);
				// int score = rs.getInt(14);
				int ks_pass = rs.getInt(18);
				if (c.getGetcredit() == 1 && mc.isPassed()) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 2 && ks_pass == 1) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 3 && mc.isPassed()) {
					if (ks_pass == 1) {
						mc.setMyCredit(c.getSetcredit());
					} else
						mc.setMyCredit(0);
				} else {
					mc.setMyCredit(0);
				}

				ExamRoom eroom = new ExamRoom(rs.getInt(25));
				eroom.setIsnormal(rs.getInt(27));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(19)));
				mc.getMyExamPaper().setMyScore(rs.getInt(20));
				mc.getMyExamPaper().setIspassed(ks_pass);
				mc.getMyExamPaper().setStatus(rs.getInt(21));
				eroom.setValid(rs.getInt("ervalid"));
				eroom.setType(rs.getInt("ertype"));
				// er.setValid(rs.getInt("ervalid"));
				eroom.setUvalid(rs.getInt("eruvalid"));
				// er.setIsnormal(rs.getInt("erisnormal"));
				mc.setExamRoom(eroom);// +++
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * 获取我的培训班课程信息（去掉已删除的）
	 * 
	 * @param clid
	 * @param userid
	 * @param eroomid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> listMyClassCourseStat3(int clid, int userid,
			String eroomid, int status, String sqlwhe) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		// String and;
		try {
			ct = DBConnection.getConnection();

			// if (eroomid.equals("")) {
			// and = " ";
			// } else {
			// and = "and eroomid in(" + eroomid + ")";
			// }
			String sql = "select c.id cid, c.name,c.creater, eu.realname,c.credit, c.during,c.teachername,cc.suggestcredit,cc.setcredit, cc.getcredit,"
					+ "c.roomstart,c.roomend,c.islink, sc.passtime/60 passtime,sc.process, sc.passed,sc.status ,sqi.ispassed,"
					+ "sqi.id sqiid_,sqi.myScore,sqi.status,c.xx_stuats,cc.starttime,cc.finishtime,sqi.roomid,er.valid ervalid,er.isnormal,er.type ertype,er.uvalid eruvalid,cc.isdel,er.svalid,sc.passtime_2/60 passtime2,c.courseForm "
					+ " from (select * from class_course where classid =? and status = ? ) cc left join study_course sc  on cc.courseid = sc.courseid left join course c on sc.courseid = c.id left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid left join exam_room er on sqi.roomid=er.id  "
					+ " where sc.userid =? and sc.classid=? " + sqlwhe;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, clid);
			ps.setInt(2, status);
			// ps.setString(3,eroomid );
			ps.setInt(3, userid);
			ps.setInt(4, clid);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				c.setSuggestcredit(rs.getInt(8));
				c.setSetcredit(rs.getInt(9));
				c.setGetcredit(rs.getInt(10));
				// c.setRoomstart(rs.getTimestamp(11));
				// c.setRoomend(rs.getTimestamp(12));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				c.setIslink(rs.getInt(13));
				c.setXx_status(rs.getInt(22));
				c.setCourseForm(rs.getInt("courseForm"));
				MyCourse mc = new MyCourse();
				mc.setCourse(c);
				mc.setPasstime(rs.getInt(14));
				mc.setProcess(rs.getInt(15));
				mc.setPassed(rs.getInt(16) == 0 ? false : true);
				mc.setStatus(rs.getInt(17));
				mc.setIsDel(rs.getInt("isdel"));
				mc.setPasstime2(rs.getInt("passtime2"));
				// mc.setMyCredit(rs.getFloat(12));
				// mc.setPassed(rs.getBoolean(20));

				// 设置学分 modify by luocw
				// int setcredit = rs.getInt(18);
				// int getcredit = rs.getInt(19);
				// int process = rs.getInt(9);
				// int score = rs.getInt(14);
				int ks_pass = rs.getInt(18);
				if (c.getGetcredit() == 1 && mc.isPassed()) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 2 && ks_pass == 1) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 3 && mc.isPassed()) {
					if (ks_pass == 1) {
						mc.setMyCredit(c.getSetcredit());
					} else
						mc.setMyCredit(0);
				} else {
					mc.setMyCredit(0);
				}

				ExamRoom eroom = new ExamRoom(rs.getInt(25));
				eroom.setIsnormal(rs.getInt(27));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(19)));
				mc.getMyExamPaper().setMyScore(rs.getInt(20));
				mc.getMyExamPaper().setIspassed(ks_pass);
				mc.getMyExamPaper().setStatus(rs.getInt(21));
				eroom.setValid(rs.getInt("ervalid"));
				eroom.setType(rs.getInt("ertype"));
				// er.setValid(rs.getInt("ervalid"));
				eroom.setUvalid(rs.getInt("eruvalid"));
				eroom.setSvalid(rs.getInt("svalid"));
				// er.setIsnormal(rs.getInt("erisnormal"));
				mc.setExamRoom(eroom);// +++
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * 获取可申请的培训班详细信息（去掉已删除的）
	 * 
	 * @return
	 * @throws ElException
	 */
	public ElClass getApplyForeElclassById(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElClass elc = new ElClass();
		try {
			ct = DBConnection.getConnection();
			String sql = "select elc.id,elc.name,elc.description,elc.certificatename,elc.createtime,elc.starttime,elc.finishtime, "
					+ "elr.planRecruitStudents,elr.registrationStartTime,elr.registrationStopTime,elr.startAge,elr.stopAge,elr.sex, "
					+ "elr.jingzhong,elr.dishi,elr.zhiwu,elr.zhiji,elr.gangwei,elu.realname,elc.creater,clt.id,clt.name,elr.treeType "
					+ ",elr.examroomIds,elr.elclassIds,elr.classScreeningWay,elr.eroomScreeningWay,elc.mainimg,elr.isAudit,elc.depname,elc.jingzhong,elr.examepids from elclass elc,ELCLASS_registration elr,elclasstype clt,eluser elu "
					+ "where elc.id = elr.classid and elc.cltype = clt.id and elc.creater = elu.id and elc.id =? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				elc.setId(rs.getInt(1));
				elc.setName(rs.getString(2));
				elc.setDescription(rs.getString(3));
				elc.setCertificatename(rs.getString(4));
				elc.setCreatetime(rs.getTimestamp(5));
				elc.setStarttime(rs.getTimestamp(6));
				elc.setFinishtime(rs.getTimestamp(7));
				ELClassRegistration elR = new ELClassRegistration();
				elR.setPlanRecruitStudents(rs.getInt(8));
				elR.setRegistrationStartTime(rs.getTimestamp(9));
				elR.setRegistrationStopTime(rs.getTimestamp(10));
				elR.setStartAge(rs.getInt(11));
				elR.setStopAge(rs.getInt(12));
				elR.setSex(rs.getString(13));
				elR.setJingzhong(rs.getString(14));
				elR.setDishi(rs.getString(15));
				elR.setZhiwu(rs.getString(16));
				elR.setZhiji(rs.getString(17));
				elR.setGangwei(rs.getString(18));
				ELUser user = new ELUser(rs.getInt(20), rs.getString(19));
				ElClType elt = new ElClType(rs.getInt(21), rs.getString(22));
				if (rs.getString(23) != null) {
					elR.setTreeType(rs.getString(23));
				}
				// 考场
				// List<ExamRoom> ers = new ArrayList<ExamRoom>();
				// if (rs.getString(24) != null) {
				// List<String> listR = new ArrayList<String>(Arrays.asList(rs
				// .getString(24).split(",")));
				// for (int i = 0; i < listR.size(); i++) {
				// ers.add(new ExamRoom(Integer.parseInt(listR.get(i))));
				// }
				// }
				// elR.setExamRoom(ers);
				elR.setErParasstr(rs.getString(24));
				// 培训班
				// List<ElClass> elcl = new ArrayList<ElClass>();
				// if (rs.getString(25) != null) {
				// List<String> listC = new ArrayList<String>(Arrays.asList(rs
				// .getString(25).split(",")));
				// for (int i = 0; i < listC.size(); i++) {
				// elcl.add(new ElClass(Integer.parseInt(listC.get(i))));
				// }
				// }
				// elR.setElclasss(elcl);
				elR.setClassParasstr(rs.getString(25));
				elR.setClassScreeningWay(rs.getInt(26));
				elR.setEroomScreeningWay(rs.getInt(27));
				elc.setElRegistration(elR);
				elc.setMainimg(rs.getString(28));
				elR.setIsAudit(rs.getInt(29));
				elc.setDepName(rs.getString(30));
				elc.setJingzhong(rs.getString(31));
				elR.setErepParasstr(rs.getString(32));
				elc.setCreater(user);
				elc.setCltype(elt);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elc;
	}

	/**
	 * 获取可申请的培训班信息（去掉已删除的）
	 * 
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getApplyForeElclass(ElClType tree, int cltid,
			ElClass elClass, int role, String sqlw, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> classlist = new ArrayList<ElClass>();
		try {
			String x = Integer.toString(cltid);
			String ids = ElClTypeById(tree, cltid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			if (cltid == 1) {
				ids = "1," + ids;
			}
			// if(elclass!=null){
			// if(
			// elclass.getName()!=null&&!elclass.getName().equals("")){//培训名称
			// sqls+= " and cl.name like '%"+elclass.getName()+"%'";
			// }
			// if(elclass.getStatus()!=-1){//考场状态
			// sqls+=" and cl.status="+elclass.getStatus();
			// }
			// if(elclass.getBegintime()!=null){
			// sqls+=" and cl.STARTTIME >= to_date('"+ new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(elclass.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
			// }
			// if(elclass.getEndtime()!=null){
			// sqls+=" and cl.FINISHTIME <= to_date('"+ new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
			// }
			// }
			String ClassConditions = "";
			if (elClass != null) {
				if (elClass.getName() != null && !elClass.getName().equals("")) {
					ClassConditions = ClassConditions + " and elc.name like '%"
							+ elClass.getName() + "%'";
				}
			}
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.*, rownum rn from ( "
					+ "select elc.id,elc.name,elc.description,elc.certificatename,elc.createtime,elc.starttime,elc.finishtime, "
					+ "elr.planRecruitStudents,elr.registrationStartTime,elr.registrationStopTime,elr.startAge,elr.stopAge,elr.sex, "
					+ "elr.jingzhong,elr.dishi,elr.zhiwu,elr.zhiji,elr.gangwei,elr.treeType "
					+ ",elr.examroomIds,elr.elclassIds,elr.classScreeningWay,elr.eroomScreeningWay,elc.mainimg,clt.id cltid,elr.isAudit,elc.depname elcdep,elc.jingzhong elcjz,eu.id euid,eu.username,eu.realname,elr.examepids "
					+ "from elclass elc,ELCLASS_registration elr,elclasstype clt,eluser eu "
					+ "where elc.id = elr.classid and elc.cltype = clt.id and elc.creater=eu.id and elc.status in (5) and clt.id in ("
					+ ids + ")" + ClassConditions + sqlw +
					// "and elr.registrationStartTime < sysdate and
					// elr.registrationStopTime > sysdate" +
					")t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass elc = new ElClass();
				elc.setId(rs.getInt(1));
				elc.setName(rs.getString(2));
				elc.setDescription(rs.getString(3));
				elc.setCertificatename(rs.getString(4));
				elc.setCreatetime(rs.getTimestamp(5));
				elc.setStarttime(rs.getTimestamp(6));
				elc.setFinishtime(rs.getTimestamp(7));
				ELClassRegistration elR = new ELClassRegistration();
				elR.setPlanRecruitStudents(rs.getInt(8));
				elR.setRegistrationStartTime(rs.getTimestamp(9));
				elR.setRegistrationStopTime(rs.getTimestamp(10));
				elR.setStartAge(rs.getInt(11));
				elR.setStopAge(rs.getInt(12));
				elR.setSex(rs.getString(13));
				elR.setJingzhong(rs.getString(14));
				elR.setDishi(rs.getString(15));
				elR.setZhiwu(rs.getString(16));
				elR.setZhiji(rs.getString(17));
				elR.setGangwei(rs.getString(18));
				elR.setTreeType(rs.getString(19));
				// 考场
				// List<ExamRoom> ers = new ArrayList<ExamRoom>();
				// if (rs.getString(20) != null) {
				// List<String> listR = new ArrayList<String>(Arrays.asList(rs
				// .getString(20).split(",")));
				// for (int i = 0; i < listR.size(); i++) {
				// ers.add(new ExamRoom(Integer.parseInt(listR.get(i))));
				// }
				// }
				// elR.setExamRoom(ers);
				elR.setErParasstr(rs.getString(20));
				// 培训班
				// List<ElClass> elcl = new ArrayList<ElClass>();
				// if (rs.getString(21) != null) {
				// List<String> listC = new ArrayList<String>(Arrays.asList(rs
				// .getString(21).split(",")));
				// for (int i = 0; i < listC.size(); i++) {
				// elcl.add(new ElClass(Integer.parseInt(listC.get(i))));
				// }
				// }
				// elR.setElclasss(elcl);
				elR.setClassParasstr(rs.getString(21));
				elR.setClassScreeningWay(rs.getInt(22));
				elR.setEroomScreeningWay(rs.getInt(23));
				elc.setElRegistration(elR);
				elc.setMainimg(rs.getString(24));
				elc.setCltype(new ElClType(rs.getInt("cltid")));
				elR.setIsAudit(rs.getInt(26));
				elc.setDepName(rs.getString(27));
				elc.setJingzhong(rs.getString(28));
				elc.setCreater(new ELUser(rs.getInt(29), rs.getString(30), rs
						.getString(31)));
				elR.setJoinNumber(((ClassDao) SpringContextUtil
						.getBean("classDao")).getJoinNumber(elc.getId()));
				elR.setErepParasstr(rs.getString(32));
				classlist.add(elc);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classlist;
	}

	/**
	 * 获取可申请的培训班信息（去掉已删除的）
	 * 
	 * @return
	 * @throws ElException
	 */
	public int getApplyForeElclassSize(ElClType tree, int cltid,
			ElClass elClass, int role, String sqlw) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String x = Integer.toString(cltid);
			String ids = ElClTypeById(tree, cltid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			if (cltid == 1) {
				ids = "1," + ids;
			}
			// if(elclass!=null){
			// if(
			// elclass.getName()!=null&&!elclass.getName().equals("")){//培训名称
			// sqls+= " and cl.name like '%"+elclass.getName()+"%'";
			// }
			// if(elclass.getStatus()!=-1){//考场状态
			// sqls+=" and cl.status="+elclass.getStatus();
			// }
			// if(elclass.getBegintime()!=null){
			// sqls+=" and cl.STARTTIME >= to_date('"+ new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(elclass.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
			// }
			// if(elclass.getEndtime()!=null){
			// sqls+=" and cl.FINISHTIME <= to_date('"+ new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
			// }
			// }
			//					
			String ClassConditions = "";
			if (elClass != null) {
				if (elClass.getName() != null && !elClass.getName().equals("")) {
					ClassConditions = ClassConditions + " and elc.name like '%"
							+ elClass.getName() + "%'";
				}
			}
			ct = DBConnection.getConnection();
			String sql = "select count(elc.id) "
					+ "from elclass elc,ELCLASS_registration elr,elclasstype clt "
					+ "where elc.id = elr.classid and elc.cltype = clt.id and elc.status in (5) "
					+ " and clt.id in(" + ids + ") " + ClassConditions + sqlw; // and
			// elr.registrationStartTime
			// <
			// sysdate
			// and
			// elr.registrationStopTime
			// >
			// sysdate
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public boolean checkClassIsUser(int classid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean is = false;
		try {
			String sql = "select ca.userid from study_class ca where ca.classid=? and ca.userid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				is = true;
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return is;
	}

	/**
	 * 查询出从ctid开始的有权的课程类型ID
	 * 
	 * @author heiweicheng
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String ElClTypeById(ElClType ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree.getId() != ctid) {
				ctypeTree = ElClTypeById(ctypeTree.getChild(), ctid);
			}
			if (ctypeTree.getChild() != null) {
				return createElClTypeId(ctypeTree.getChild(), ctypeTree.getId());
			}
			return String.valueOf(ctypeTree.getId());
		} else {
			return null;
		}
	}

	/**
	 * 构建有权的课程类型ID
	 * 
	 * @author heiweicheng
	 * @param ctypeTree
	 * @return
	 */
	private String createElClTypeId(List<ElClType> listType, int id) {
		String ids = id + "";
		for (ElClType type : listType) {
			ids = ids + "," + createElClTypeId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * 
	 * @author heiweicheng
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private ElClType ElClTypeById(List<ElClType> listType, int ctid) {
		ElClType courseType = null;
		for (ElClType type : listType) {
			if (type.getId() != ctid) {
				courseType = ElClTypeById(type.getChild(), ctid);
				if (courseType != null) {
					return courseType;
				}
			} else {
				courseType = type;
				return courseType;
			}
		}
		return courseType;
	}

	public List<ElClass> registeredElclass(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> classlist = new ArrayList<ElClass>();
		try {
			// String sql = "select * from (select t.*, rownum rn from (select
			// elc.id,elc.name,eu.realname,elc.creater,elc.starttime,elc.finishtime,elc.description,elc.status
			// classStatus,sc.status "
			// + " from elclass elc left join study_class sc on elc.id =
			// sc.classid left join eluser eu on elc.creater = eu.id left join
			// elclass_appliedfor ela "
			// + "on elc.id=ela.classid where ( sc.joinway ='1' and sc.userid =
			// ? ) or ela.userid=?)t where rownum <= ? ) where rn>=?";
			String sql = "select * from (select t.*, rownum rn from ("
					+ " select elc.id,elc.name,eu.realname,elc.creater,elc.starttime,elc.finishtime,elc.description,elc.status classStatus,sc.status scStatus,sca.status "
					+ " from elclass elc left join study_class sc on elc.id = sc.classid "
					+ " left join (select classid,status from study_class_apply where userid=?) sca on elc.id=sca.classid "
					+ " left join eluser eu on elc.creater = eu.id where elc.isapplication=1 and sc.userid=? and sca.status is null"
					+ " union "
					+ " select elc.id,elc.name,eu.realname,elc.creater,elc.starttime,elc.finishtime,elc.description,elc.status classStatus,null,sca.status "
					+ " from elclass elc "
					+ " left join study_class_apply sca on elc.id = sca.classid "
					+ " left join eluser eu on elc.creater = eu.id where elc.isapplication=1 and sca.userid=?"
					+ " )t where rownum <= ? ) where rn>=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass elc = new ElClass();
				elc.setId(rs.getInt(1));
				elc.setName(rs.getString(2));
				elc.setCreater(new ELUser(rs.getInt(4), rs.getString(3)));
				elc.setStarttime(rs.getTimestamp(5));
				elc.setFinishtime(rs.getTimestamp(6));
				elc.setDescription(rs.getString(7));
				elc.setStatus(rs.getInt(9));// 此属性是培训班状态，现在借用此处存储了学员培训班状态
				elc.setAstatus(rs.getInt(10));// 借用此处存储了学员培训班报名状态
				// elc.setIsjoin(checkClassIsUser(rs.getInt(1), userid) ? "true"
				// : "false");
				classlist.add(elc);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classlist;
	}

	public int registeredElclassSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "select count(userid) from (select userid from study_class sc left join elclass ec on ec.id=sc.classid left join (select classid,status from study_class_apply where userid=?) sca on sca.classid=sc.classid where ec.isapplication=1 and sc.userid=? and sca.status is null "
					+ " union all "
					+ " select userid from study_class_apply where userid=?)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 申请考场结果
	 */
	public List<ExamRoom> registeredEroom(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> examlist = new ArrayList<ExamRoom>();
		try {
			// String sql = "select * from (select t.*, rownum rn from (select
			// er.id,er.title,eu.realname,er.createrid,er.BEGINTIME,er.endtime,er.description,er.valid,sr.status
			// "
			// + " from exam_room er left join study_room sr on er.id =
			// sr.roomid left join eluser eu on er.createrid = eu.id left join
			// exam_appliedfor ea "
			// + " on er.id=ea.eroomid where (sr.joinway ='1' and sr.userid = ?
			// ) or ea.userid=?)t where rownum <= ? ) where rn>=?";
			String sql = "select * from (select t.*, rownum rn from ("
					+ " select er.id,er.title,eu.realname,er.createrid,er.BEGINTIME,er.endtime,er.description,er.valid,sr.status srStatus,sra.status "
					+ " from exam_room er left join study_room sr on er.id = sr.roomid "
					+ " left join (select roomid,status from study_room_apply where userid=?) sra on er.id=sra.roomid "
					+ " left join eluser eu on er.createrid = eu.id  where er.isapplication=1 and sr.userid=? and sra.status is null"
					+ " union "
					+ " select er.id,er.title,eu.realname,er.createrid,er.BEGINTIME,er.endtime,er.description,er.valid,null,sra.status "
					+ " from exam_room er left join study_room_apply sra on er.id=sra.roomid "
					+ " left join eluser eu on er.createrid = eu.id  where er.isapplication=1 and sra.userid=?"
					+ " )t where rownum <= ? ) where rn>=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom elc = new ExamRoom();
				elc.setId(rs.getInt(1));
				elc.setTitle(rs.getString(2));
				elc.setCreater(new ELUser(rs.getInt(4), rs.getString(3)));
				elc.setBegintime(rs.getTimestamp(5));
				elc.setEndtime(rs.getTimestamp(6));
				elc.setDescription(rs.getString(7));
				elc.setValid(rs.getInt(9));// 此属性是考场状态，但是这里被借用来存储学员考场状态
				elc.setSvalid(rs.getInt(10));// 借用来存储学员考场报名状态
				examlist.add(elc);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return examlist;
	}

	public int registeredEroomSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "select count(userid) from (select userid from study_room sr inner join exam_room er on er.id=sr.roomid left join (select roomid,status from study_room_apply where userid=?) sca on sca.roomid=er.id where er.isapplication=1 and sr.userid=? and sca.status is null"
					+ " union all "
					+ " select userid from study_room_apply where userid=?)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 申请考场结果
	 */
	public List<ExamRoom> registeredCourse(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> examlist = new ArrayList<ExamRoom>();
		try {
			String sql = "select * from (select t.*, rownum rn from (select er.id,er.title,eu.realname,er.createrid,er.BEGINTIME,er.endtime,er.description,er.valid "
					+ " from exam_room er left join study_room sr on er.id = sr.roomid left join eluser eu on er.createrid = eu.id  left join exam_appliedfor ea "
					+ " on er.id=ea.eroomid   where (sr.joinway ='1' and   sr.userid = ? ) or ea.userid=?)t where rownum <= ? ) where rn>=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom elc = new ExamRoom();
				elc.setId(rs.getInt(1));
				elc.setTitle(rs.getString(2));
				elc.setCreater(new ELUser(rs.getInt(4), rs.getString(3)));
				elc.setBegintime(rs.getTimestamp(5));
				elc.setEndtime(rs.getTimestamp(6));
				elc.setDescription(rs.getString(7));
				elc.setValid(rs.getInt(8));
				examlist.add(elc);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return examlist;
	}

	public int registeredCourseSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = " select count(er.id) "
					+ "from exam_room er left join study_room sr on er.id = sr.roomid left join eluser eu on er.createrid = eu.id  "
					+ "left join exam_appliedfor ea on er.id=ea.eroomid   where (sr.joinway ='1' and   sr.userid = ? ) or ea.userid=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 获取该培训班中所有人员
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listStudyByClass(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> userList = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select userid from study_class where classid=? ");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				user = new ELUser(rs.getInt("userid"));
				userList.add(user);
			}
		} catch (Exception e) {
			logger.error("获取该培训班中所有人员出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	/**
	 * 获取该学员该培训班没有拿证的详细原因
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public MyClass getStudyClassNoPassRemack(int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyClass myClass = new MyClass();
		List<String> remackList = new ArrayList<String>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select cc.courseid,decode(cc.status,0,'必修课',1,'选修课') courseType," +
							"cc.getcredit,cc.eroomid,cc.status,c.name cname,ec.id classId,ec.name className,eae.examroomid ,ec.optionalcredit  " +
							"from class_course cc " +
							"left join course c on cc.courseid=c.id " +
							"left join elclass ec on cc.classid=ec.id" +
							"  left join elclass_assign_examroom  eae on cc.classid=eae.classid" +
							" where classid=? and isdel=0  and cc.status=0 order by cc.status ");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			int getcredit = 0;
			int courseid = 0;
			int roomid=0;
			int tagartXF=0;
			while (rs.next()) {
				myClass.setElClass(new ElClass(rs.getInt(7), rs.getString(8)));
				// 判断课程的结业方式
				getcredit = rs.getInt(3);
				courseid = rs.getInt(1);
				roomid = rs.getInt(9);
				tagartXF = rs.getInt(10);
				
				if (getcredit == 1) {
					
					// 判断该课程有没有学完
					if (!checkStudyCourseIsPass(userid, classid, courseid)) {
						remackList.add(rs.getString(2) + "[" + rs.getString(6)
								+ "]没有学完。");
					}
					
					
				} else if (getcredit == 2) {
					// 判断该课程有没有绑定考场
					if (rs.getInt(4) == 0) {
						remackList.add(rs.getString(2) + "[" + rs.getString(6)
								+ "]没有考场。");
					} else {
						// 判断该课程有没有考过
						if (!checkStudyEroomIsPass(userid, rs.getInt(4),
								classid)) {
							remackList.add(rs.getString(2) + "["
									+ rs.getString(6) + "]没有考过。");
						}
					}
				} else {
					int n = 0;
					int m = 0;
					// 判断该课程有没有学完且考过
					if (!checkStudyCourseIsPass(userid, classid, courseid)) {
						n = 1;
					}
					if (!checkStudyEroomIsPass(userid, rs.getInt(4), classid)) {
						m = 1;
					}
					if (n == 1 && m == 1) {
						remackList.add(rs.getString(2) + "[" + rs.getString(6)
								+ "]没有学完且没有考过。");
					} else if (n == 1 && m == 0) {
						remackList.add(rs.getString(2) + "[" + rs.getString(6)
								+ "]没有学完。");
					} else if (n == 0 && m == 1) {
						remackList.add(rs.getString(2) + "[" + rs.getString(6)
								+ "]没有考过。");
					}
				}
				
				
			}
			int sumXF=getcountXFforXX(userid, classid);
			if(sumXF<tagartXF){
				remackList.add("选修课还没有达到规定的学时数。");
			}
			// 判断该培训班有没有绑定考场
			if (roomid+"" != null) {
				// 判断该考场有没有考过
				if (!checkStudyEroomIsPass(userid, roomid,
						classid)) {
					remackList.add("培训班结业考试还没有通过。");
				}
			} else {
				
			}
			myClass.setNoPassRemack(remackList);
		} catch (Exception e) {
			logger.error("获取该学员该培训班没有拿证的详细原因出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myClass;
	}

	/**
	 * 检测学员的课程学习是否学完
	 * 
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudyCourseIsPass(int userid, int classid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_course where userid=? and classid=? and courseid=? and passed=1 and status=0");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("获取该学员该培训班没有拿证的详细原因出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 检测学员的培训班里面的课程绑定的考场是否通过
	 * 
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudyEroomIsPass(int userid, int roomid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_room where userid=? and roomid=? and classid=? and ispassed=1");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("获取该学员该培训班没有拿证的详细原因出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 更新学员培训班的状态
	 * 
	 * @param userid
	 * @param classid
	 * @param status
	 * @throws ElException
	 */
	public void updateStudyClassStatus(int userid, int classid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_class set status=? where userid=? and classid=?");
			ps.setInt(1, status);
			ps.setInt(2, userid);
			ps.setInt(3, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新学员培训班的状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 培训班是否满足申请要求
	 * 
	 * @author
	 * @return
	 * @throws ElException
	 */
	public boolean checkIsuserApp(ElClass elclass, ELUser elUser)
			throws ElException {
		boolean IsuserApp = true;
		boolean jz = true;
		boolean ds = true;
		boolean zj = true;
		boolean zw = true;
		boolean gw = true;
		boolean nl = true;
		boolean xb = true;
		boolean bm = true;
		boolean erooms = true;
		boolean elClass = true;
		if (elclass.getElRegistration().getDslist() == null) {// 地市不限
			ds = true;
		} else {
			if (elclass.getElRegistration().getDslist() != null
					&& elUser.getDishi() > 0
					&& elclass.getElRegistration().getDslist().contains(
							elUser.getDishi() + "")) {
				ds = true;// dslist不为空 uds不为空 dslist 里没有该地市
			}
		}
		if (elclass.getElRegistration().getJzlist() == null) {
			jz = true;// 不限
		} else {
			if (elclass.getElRegistration().getJzlist() != null
					&& elUser.getJingzhong() > 0
					&& elclass.getElRegistration().getJzlist().contains(
							elUser.getJingzhong() + "")) {
				jz = true;
			}
		}
		if (elclass.getElRegistration().getZjlist() == null) {
			zj = true;// 不限
		} else {
			if (elclass.getElRegistration().getZjlist() != null
					&& elUser.getZhiji() > 0
					&& elclass.getElRegistration().getZjlist().contains(
							elUser.getZhiji() + "")) {
				zj = true;
			}
		}
		if (elclass.getElRegistration().getZwlist() == null) {
			zw = true;// 不限
		} else {
			if (elclass.getElRegistration().getZwlist() != null
					&& elUser.getZhiwu() > 0
					&& elclass.getElRegistration().getZwlist().contains(
							elUser.getZhiwu() + "")) {
				zw = true;
			}
		}
		if (elclass.getElRegistration().getGwlist() == null) {
			gw = true;
		} else {
			if (elclass.getElRegistration().getGwlist() != null
					&& elUser.getGangwei() != null
					&& elclass.getElRegistration().getGwlist().contains(
							elUser.getGangwei())) {
				gw = true;
			}
		}
		// 年龄段
		if (elclass.getElRegistration().getStartAge() == 0
				&& elclass.getElRegistration().getStopAge() == 0) {
			nl = true;
		} else {
			if (elUser.getAGE() > elclass.getElRegistration().getStartAge()
					&& elclass.getElRegistration().getStopAge() > elUser
							.getAGE()) {
				nl = true;
			}
		}
		// 性别
		if (elclass.getElRegistration().getSex().equals("不限")) {
			xb = true;
		} else if (elclass.getElRegistration().getSex().equals(elUser.getSex())) {
			xb = true;
		}
		// 部门
		if (elclass.getElRegistration().getTreeType() == null) {// 部门不限
			bm = true;
		} else {
			// 检测部门条件是否通过
			if (elclass.getElRegistration().getTreeTypes() != null
					&& elUser.getDepartment() != null
					&& ((UserDao) SpringContextUtil.getBean("userDao"))
							.checkUserIsInDep(elUser.getId(), elclass
									.getElRegistration().getTreeType())) {
				bm = true;
			}
		}
		// 考场
		if (elclass.getElRegistration().getExamRoomids() == null
				|| elclass.getElRegistration().getExamRoomids().equals("")) {// 考场不限
			erooms = true;
		} else {
			String sqlWhere = "";
			if (elclass.getElRegistration().getEroomScreeningWay() == 1) {
				sqlWhere = " and ispassed  = 1";
			} else if (elclass.getElRegistration().getEroomScreeningWay() == 2) {
				sqlWhere = " and ispassed  = 0";
			}
			if (!elclass.getElRegistration().getExamRoomids().equals("")
					&& ((EroomDao) SpringContextUtil.getBean("eroomDao"))
							.checkEroomIspassed(elclass.getElRegistration()
									.getExamRoomids(), elUser.getId(), sqlWhere)) {
				erooms = true;
			}
		}
		// 培训班
		if (elclass.getElRegistration().getElclasss() == null
				|| elclass.getElRegistration().getElclasss().size() == 0) {// 培训班不限
			elClass = true;
		} else {
			String sqlWhere = "";
			if (elclass.getElRegistration().getClassScreeningWay() == 1) {
				sqlWhere = "and certificateno is not null";
			} else if (elclass.getElRegistration().getClassScreeningWay() == 2) {
				sqlWhere = "and certificateno is null";
			}
			if (!elclass.getElRegistration().getElclassids().equals("")
					&& ((EroomDao) SpringContextUtil.getBean("eroomDao"))
							.checkElclassIspassed(elclass.getElRegistration()
									.getElclassids(), elUser.getId(), sqlWhere)) {
				elClass = true;
			}
		}
		if (jz && ds && zj && zw && gw && nl && xb && bm && erooms && elClass) {
			IsuserApp = true;
		} else {
			IsuserApp = false;
		}
		return IsuserApp;
	}

	/**
	 * 获取可报名的培训班数量
	 * 
	 * @param userid
	 * @param roleid
	 * @return
	 * @throws ElException
	 */
	public int getClassAppcount(int userid, int roleid) throws ElException {
		ElClType cltypeTree = ((ElClTypeDao) SpringContextUtil
				.getBean("elClTypeDao")).getCltypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		ELUser elUser = ((UserDao) SpringContextUtil.getBean("userDao"))
				.getUserById(userid);
		List<ElClass> elclasses = this
				.getApplyForeElclass(
						cltypeTree,
						1,
						null,
						roleid,
						" and elr.registrationStartTime < sysdate and elr.registrationStopTime > sysdate ",
						999999999, 1); // 不限制条数， 用于获取到可申请的培训班
		if (elclasses.size() != 0) {
			String classids = "";
			for (int i = 0; i < elclasses.size(); i++) { // 获取通过的培训班
				if (checkIsuserApp(elclasses.get(i), elUser)) {// 如果返回false证明有某条不符合条件
					if (classids.equals(""))
						classids = classids + elclasses.get(i).getId();
					else
						classids = classids + "," + elclasses.get(i).getId();
				}
			}
			if (!classids.equals("")) {
				return this.getApplyForeElclassSize(cltypeTree, 1, null,
						roleid, " and elc.id in (" + classids + ") ");
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 添加可申请且需要审核的培训班学员报名信息
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public void addStudyClassApply(int classid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_class_apply(classid,userid,status,createtime) values(?,?,?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.setInt(3, 1);
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("添加可申请且需要审核的培训班学员报名信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 检测学员是否已经报名（培训班）
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudyClassApply(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_class_apply where classid = ? and userid = ?");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经报名（培训班）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 更新学员培训班报名状态
	 * 
	 * @param erid
	 * @param epid
	 * @param delStatus
	 * @throws ElException
	 */
	public void udpateStudyClassApplyStatus(int classid, int userid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_class_apply set status=? where classid = ? and userid =?");
			ps.setInt(1, status);
			ps.setInt(2, classid);
			ps.setInt(3, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新学员培训班报名状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 获取学员培训班证书编号
	 * 
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getStudyClassCertificateno(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int certificateno = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select certificateno from study_class where classid = ? and userid = ?");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				certificateno = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取学员培训班证书编号出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return certificateno;
	}

	public String checkPassClasss(ClassPara erpara, int userid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sb = new StringBuffer();
		try {
			ct = DBConnection.getConnection();
			int classid = erpara.getElClass().getId();
			if (erpara.getIsPassed() != -1) {// 判断是否通过
				ps = ct
						.prepareStatement("select * from study_class where classid = "
								+ classid
								+ " and userid = "
								+ userid
								+ " and status = "
								+ (erpara.getIsPassed() == 1 ? 2 : 1));
				rs = ps.executeQuery();
				if (!rs.next()) {
					sb.append("通过条件不符合，");
				}
				rs.close();
				ps.close();
			}
			ps = ct
					.prepareStatement("select * from study_class where classid = "
							+ classid
							+ " and userid = "
							+ userid
							+ " and tcredit between "
							+ erpara.getSumScoreStart()
							+ " and "
							+ erpara.getSumScoreEnd());
			rs = ps.executeQuery();
			if (!rs.next()) {
				sb.append("总学分不符合，");
			}
			rs.close();
			ps.close();
			ps = ct
					.prepareStatement("select * from study_class where classid = "
							+ classid
							+ " and userid = "
							+ userid
							+ " and bcredit between "
							+ erpara.getBsumScoreStart()
							+ " and "
							+ erpara.getBsumScoreEnd());
			rs = ps.executeQuery();
			if (!rs.next()) {
				sb.append("必修学分不符合，");
			}
			rs.close();
			ps.close();
			ps = ct
					.prepareStatement("select * from study_class where classid = "
							+ classid
							+ " and userid = "
							+ userid
							+ " and xcredit between "
							+ erpara.getXsumScoreStart()
							+ " and "
							+ erpara.getXsumScoreEnd());
			rs = ps.executeQuery();
			if (!rs.next()) {
				sb.append("选修学分不符合，");
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			logger.error("", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sb.length() <= 0 ? "符合" : sb.toString();
	}

	/**
	 * 获取我的培训班课程信息（去掉已删除的）
	 * 
	 * @param clid
	 * @param userid
	 * @param eroomid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> listMyClassCourseStat3(int clid, int userid,
			String eroomid, String tableName, int status, String sqlwhe)
			throws ElException {
		DecimalFormat df = new DecimalFormat("0.0");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		String and;
		String tableNamesql = "";
		try {
			ct = DBConnection.getConnection();

			if (eroomid.equals("")) {
				and = " ";
			} else {
				and = "and eroomid in(" + eroomid + ")";
			}
			if (tableName.equals("CLASS_COURSE_AT")) {
				tableNamesql = "and userid = " + userid;
			}
			String sql = "select c.id cid, c.name,c.creater, eu.realname,c.credit, c.during,c.teachername,cc.suggestcredit,cc.setcredit, cc.getcredit,"
					+ "c.roomstart,c.roomend,c.islink, sc.passtime/60 passtime,sc.process, sc.passed,sc.status ,sqi.ispassed,"
					// + "sqi.id
					// sqiid_,sqi.myScore,sqi.status,c.xx_stuats,cc.starttime,cc.finishtime,sqi.roomid,er.valid
					// ervalid,er.isnormal,er.type ertype,er.uvalid
					// eruvalid,cc.isdel,er.svalid,sc.passtime_2/60
					// passtime2,c.courseForm,cc.firstlearn "
					+ " 1,sqi.myScore,sqi.status,c.xx_stuats,cc.starttime,cc.finishtime,sqi.roomid,er.valid ervalid,er.isnormal,er.type ertype,er.uvalid eruvalid,cc.isdel,er.svalid,sc.passtime_2/60 passtime2,c.courseForm,cc.firstlearn "
					+ " from (select * from "
					+ tableName
					+ " where classid =? and status = ? "
					+ tableNamesql
					+ " ) cc left join study_course sc  on cc.courseid = sc.courseid "
					+ "left join course c on sc.courseid = c.id "
					+ "left join eluser eu on c.creater = eu.id "
					+
					// " left join study_quizinfo sqi on sqi.id=sc.sqiid " +
					" left join exam_room er on  er.courseid=c.id and er.classid=? "
					+ " left join study_room sqi on sqi.roomid=er.id  "
					+
					// " and sqi.classid =? " +
					" and sqi.userid = ? "
					+ " where sc.userid =? and sc.classid=?  " + sqlwhe;
			ps = ct.prepareStatement(sql);
			System.out.println("3182===sql"+sql);
			ps.setInt(1, clid);
			ps.setInt(2, status);
			ps.setInt(3, clid);
			// ps.setInt(4, clid);
			ps.setInt(4, userid);
			ps.setInt(5, userid);
			ps.setInt(6, clid);

			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				c.setSuggestcredit(rs.getInt(8));
				c.setSetcredit(rs.getInt(9));
				c.setGetcredit(rs.getInt(10));
				// c.setRoomstart(rs.getTimestamp(11));
				// c.setRoomend(rs.getTimestamp(12));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				c.setIslink(rs.getInt(13));
				c.setXx_status(rs.getInt(22));
				c.setCourseForm(rs.getInt("courseForm"));
				c.setFirstLearn(rs.getInt("firstlearn"));
				MyCourse mc = new MyCourse();
				mc.setCourse(c);
				mc.setPasstime(rs.getInt(14));
				mc.setProcess(Float.parseFloat(df.format(rs.getFloat(15))));
				mc.setPassed(rs.getInt(16) == 0 ? false : true);
				mc.setStatus(rs.getInt(17));
				mc.setIsDel(rs.getInt("isdel"));
				mc.setPasstime2(rs.getInt("passtime2"));
				// mc.setMyCredit(rs.getFloat(12));
				// mc.setPassed(rs.getBoolean(20));

				// 设置学分 modify by luocw
				// int setcredit = rs.getInt(18);
				// int getcredit = rs.getInt(19);
				// int process = rs.getInt(9);
				// int score = rs.getInt(14);
				int ks_pass = rs.getInt(18);
				if (c.getGetcredit() == 1 && mc.isPassed()) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 2 && ks_pass == 1) {
					mc.setMyCredit(c.getSetcredit());
				} else if (c.getGetcredit() == 3 && mc.isPassed()) {
					if (ks_pass == 1) {
						mc.setMyCredit(c.getSetcredit());
					} else
						mc.setMyCredit(0);
				} else {
					mc.setMyCredit(0);
				}

				ExamRoom eroom = new ExamRoom(rs.getInt(25));
				eroom.setIsnormal(rs.getInt(27));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(19)));
				mc.getMyExamPaper().setMyScore(rs.getInt(20));
				mc.getMyExamPaper().setIspassed(ks_pass);
				mc.getMyExamPaper().setStatus(rs.getInt(21));
				eroom.setValid(rs.getInt("ervalid"));
				eroom.setType(rs.getInt("ertype"));
				// er.setValid(rs.getInt("ervalid"));
				eroom.setUvalid(rs.getInt("eruvalid"));
				eroom.setSvalid(rs.getInt("svalid"));
				// er.setIsnormal(rs.getInt("erisnormal"));
				mc.setExamRoom(eroom);// +++
				// System.out.println("kaocId:"+);
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * Description: 检测并设置培训班通过
	 * 
	 * @Version1.0 2011-9-27 上午11:27:42 by 何伟成创建
	 * @param userid
	 * @param classid
	 * @throws ElException
	 */
	public void setMyPassclass_at(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call user_ispass_class_at(?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.executeUpdate();
			logger.error("检查是否通过培训班");
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 通过classid =1040 and userid = 3589 and courseid = 610 删除CLASS_COURSE_AT
	 */
	public void delete_CLASS_COURSE_AT(int classid, int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" delete from CLASS_COURSE_AT where classid =?  and userid = ? and courseid = ? ");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.setInt(3, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<MyCourse> getCourses(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> courses = new ArrayList<MyCourse>();
		MyCourse c = null;
		Course course = null;
		try {
			ct = DBConnection.getConnection();
			// "select c.id cid, c.name,c.creater, eu.realname,c.credit,
			// c.during,c.teachername,sc.passtime/60
			// passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id
			// sqiid_,sqi.myScore,sqi.ispassed,sqi.status from study_course sc
			// left join course c on sc.courseid = c.id left join eluser eu on
			// c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid
			// where sc.userid =? and sc.status = ? and sc.classid =?"
			
//			ps = ct.prepareStatement(" select c.id,c.name,c.during,sc.passtime/60 passtime,sc.process,cc.getcredit,cc.firstlearn" +
//							"," +
//							" er.valid,er.type,er.uvalid,er.svalid,er.id as erid " +
//							" from study_course sc " +
//							" left join course c on sc.courseid = c.id " +
//							" left join eluser eu on c.creater = eu.id " +
//							" left join class_course cc on cc.courseid=c.id " +
//							" left join exam_room er on  er.courseid=c.id  " + 
//							" left join study_room sqi on sqi.roomid=er.id  " +
//							" where sc.classid=? and  sc.userid=? " +
////							" and  sqi.classid =? " +
////							" and sqi.userid = ? " +
//							" order by cc.orderid asc ");

//			ps = ct
//					.prepareStatement(" select c.id,c.name,c.during,sc.passtime/60 passtime,sc.process,cc.getcredit,cc.firstlearn"
//							+ ","
//							+ " er.valid,er.type,er.uvalid,er.svalid,er.id as erid "
//							+ " from study_course sc "
//							+ " left join course c on sc.courseid = c.id "
//							+ " left join eluser eu on c.creater = eu.id "
//							+ " left join class_course cc on cc.courseid=c.id "
//							+ " left join exam_room er on  er.courseid=c.id  "
//							+ " left join study_room sqi on sqi.roomid=er.id  "
//							+ " where sc.classid=? and  sc.userid=? "
////							+ " and  sqi.classid =? "
////							+ " and sqi.userid = ? "
//							+ " order by cc.orderid asc ");
			ps = ct
			.prepareStatement(" select c.id,c.name,c.during,sc.passtime/60 passtime,sc.process,cc.getcredit,cc.firstlearn"
//					+ ","
//					+ " er.valid,er.type,er.uvalid,er.svalid,er.id as erid "
					+ " from study_course sc "
					+ " left join course c on sc.courseid = c.id "
					+ " left join eluser eu on c.creater = eu.id "
					+ " left join class_course cc on cc.courseid=c.id "
//					+ " left join exam_room er on  er.courseid=c.id  "
					+ " where sc.classid=? and  sc.userid=? "
//					+ " and  sqi.classid =? "
//					+ " and sqi.userid = ? "
					+ " order by cc.orderid asc ");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
//			ps.setInt(3, classid);
//			ps.setInt(4, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				c = new MyCourse();
//				ExamRoom eroom = new ExamRoom(rs.getInt(12));
//				eroom.setValid(rs.getInt(8));
//				eroom.setType(rs.getInt(9));
//				eroom.setUvalid(rs.getInt(10));
//				eroom.setSvalid(rs.getInt(11));

				course = new Course(rs.getInt(1), rs.getString(2));
				course.setClassid(classid);
				course.setDuring(rs.getInt(3));
				course.setGetcredit(rs.getInt(6));
				course.setFirstLearn(rs.getInt(7));

//				c.setExamRoom(eroom);
				c.setCourse(course);
				c.setPasstime(rs.getInt(4));
				c.setProcess(rs.getFloat(5));
				courses.add(c);
			}
		} catch (Exception e) {
			logger.error("获取系统培训班课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	public List<MyCourse> getCourses_wjm(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> courses = new ArrayList<MyCourse>();
		MyCourse c = null;
		Course course = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement(" select c.id,c.name,c.during,sc.passtime/60 passtime,round(sc.process,1),cc.getcredit,cc.firstlearn"
//							+ ","
//							+ " er.valid,er.type,er.uvalid,er.svalid,er.id as erid "
							+ " from study_course sc "
							+ " left join course c on sc.courseid = c.id "
							+ " left join eluser eu on c.creater = eu.id "
							+ " left join class_course cc on cc.courseid=c.id "
//							+ " left join exam_room er on  er.courseid=c.id  "
//							+ " left join study_room sqi on sqi.roomid=er.id  "
							+ " where sc.classid=? and  sc.userid=? "
//							+ " and  sqi.classid =? "
//							+ " and sqi.userid = ? "
							+ " order by cc.orderid asc ");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
//			ps.setInt(3, classid);
//			ps.setInt(4, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				c = new MyCourse();
//				ExamRoom eroom = new ExamRoom(rs.getInt(12));
//				eroom.setValid(rs.getInt(8));
//				eroom.setType(rs.getInt(9));
//				eroom.setUvalid(rs.getInt(10));
//				eroom.setSvalid(rs.getInt(11));

				course = new Course(rs.getInt(1), rs.getString(2));
				course.setClassid(classid);
				course.setDuring(rs.getInt(3));
				course.setGetcredit(rs.getInt(6));
				course.setFirstLearn(rs.getInt(7));

//				c.setExamRoom(eroom);
				c.setCourse(course);
				c.setPasstime(rs.getInt(4));
				c.setProcess(rs.getFloat(5));
				courses.add(c);
			}
		} catch (Exception e) {
			logger.error("获取系统培训班课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	public MyClass getStudyClassStatus(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyClass myClass = null;
		ElClass elClass = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select e.id,e.name,e.finishtime From elclass e where e.nazhengornianjian=0");
			rs = ps.executeQuery();
			if (rs.next()) {
				elClass = new ElClass();
				elClass.setId(rs.getInt(1));
				elClass.setName(rs.getString(2));
				elClass.setEndtime(rs.getDate(3));
				myClass = new MyClass();
				myClass.setElClass(elClass);

			}
		} catch (Exception e) {
			logger.error("获取拿证系统培训班出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myClass;
	}

	public MyClass getNianjianClass(int year) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection ct = null;
		MyClass myClass = null;
		ElClass elClass = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select a.*, rownum rn\n"
					+ "  from (select e.id, e.name, e.finishtime, e.optionalcredit \n"
					+ "          From elclass e where e.nazhengornianjian=1\n"
					+ "         order by e.createtime desc) a\n"
					+ " where rownum = 1\n");
			rs = ps.executeQuery();
			if (rs.next()) {
				elClass = new ElClass();
				elClass.setId(rs.getInt(1));
				elClass.setName(rs.getString(2));
				elClass.setEndtime(rs.getDate(3));
				elClass.setOptionalcredit(rs.getInt(4));
				myClass = new MyClass();
				myClass.setElClass(elClass);

			}
		} catch (Exception e) {
			logger.error("获取最新一期的本年度的培训班出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myClass;
	}

	public MyClass getNaZhengClass(int year) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyClass myClass = null;
		ElClass elClass = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select a.*, rownum rn\n"
					+ "  from (select e.id, e.name, e.finishtime, e.optionalcredit \n"
					+ "          From elclass e where e.nazhengornianjian=0\n"
					+ "         order by e.createtime desc) a\n"
					+ " where rownum = 1\n ");
			rs = ps.executeQuery();
			if (rs.next()) {
				elClass = new ElClass();
				elClass.setId(rs.getInt(1));
				elClass.setName(rs.getString(2));
				elClass.setEndtime(rs.getDate(3));
				elClass.setOptionalcredit(rs.getInt(4));
				myClass = new MyClass();
				myClass.setElClass(elClass);

			}
		} catch (Exception e) {
			logger.error("获取最新一期的本年度的培训班出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myClass;
	}

	// 检查是否选班
	public int getIsChangeclass(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int flag = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_class where userid=?");
			// ps.setInt(1, classid);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag = 1;
				// return flag;
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经报名（培训班）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	// 判断永华是否购买年检培训班
	public int isNianjianClass(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int flag = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_class where classid=(select a.id\n"
							+ "\t\t\t\t\t from (select e.id, e.name, e.finishtime\n"
							+ "\t\t\t\t\t         From elclass e where e.nazhengornianjian=1\n"
							+ "\t\t\t\t\t        order by e.createtime desc) a\n"
							+ "\t\t\t\t where rownum = 1) and userid=?");
			// ps.setInt(1, classid);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag = 1;
				// return flag;
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经报名（培训班）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	// 根据证书号查询
	public MyClass getZhengShuByNo(int year, int classid, int no)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyClass myClass = new MyClass();
		ElClass elClass = null;
		// ELUser el = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select cl.id,\n" + "       cl.name,\n"
					+ "       cl.certificatename,\n" + "       sc.applydate,\n"
					+ "       certificateno,\n" + "       sc.status,\n"
					+ "       cl.starttime,\n" + "       cl.finishtime\n"
					+ "  from study_class sc\n"
					+ "  left join elclass cl on sc.classid = cl.id\n"
					+ " where cl.year=? and cl.id=? and sc.certificateno=?");
			ps.setInt(1, year);
			ps.setInt(2, classid);
			ps.setInt(3, no);
			rs = ps.executeQuery();
			if (rs.next()) {
				elClass = new ElClass(rs.getInt(1), rs.getString(2));
				elClass.setCertificatename(rs.getString(3));
				// ELUser eu = new ELUser(rs.getInt(4), rs.getString(5));
				// MyClass cl1 = new MyClass();
				Timestamp t = rs.getTimestamp(4);
				myClass.setStatus(rs.getInt(6));
				// if (cl1.getStatus() == 2) {
				// cl1.setCertificateno(rs.getInt(7));
				// cl1.setEndtime(t);
				// cl1.setPassed(true);
				// }
				// 先查看学员培训班是否通过再查证书编号
				// this.setMyPassclass(userid, cl.getId());
				myClass.setCertificateno(rs.getInt(5));
				// cl1.setCertificateno(this.getStudyClassCertificateno(
				// cl.getId(), userid));
				myClass.setEndtime(t);
				if (myClass.getCertificateno() > 0) {
					myClass.setPassed(true);
				}
				elClass.setStarttime(rs.getTimestamp(7));
				elClass.setFinishtime(rs.getTimestamp(8));
				myClass.setElClass(elClass);

			}
		} catch (Exception e) {
			logger.error("获取拿证系统培训班出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myClass;
	}

	public int getZhengShuByNo(int year, int classid, int no, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyClass myClass = new MyClass();
		ElClass elClass = null;
		// ELUser el = null;
		int flag = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select cl.id,\n" + "       cl.name,\n"
					+ "       cl.certificatename,\n" + "       sc.applydate,\n"
					+ "       certificateno,\n" + "       sc.status,\n"
					+ "       cl.starttime,\n" + "       cl.finishtime\n"
					+ "  from study_class sc\n"
					+ "  left join elclass cl on sc.classid = cl.id\n"
					+ "  left join eluser eu on sc.userid=eu.id\n"
					+ " where cl.year = ?\n" + "   and cl.id = ?\n"
					+ "   and sc.certificateno = ?\n" + "   and eu.id=?");
			ps.setInt(1, year);
			ps.setInt(2, classid);
			ps.setInt(3, no);
			ps.setInt(4, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				// elClass = new ElClass(rs.getInt(1), rs.getString(2));
				// elClass.setCertificatename(rs.getString(3));
				// // ELUser eu = new ELUser(rs.getInt(4), rs.getString(5));
				// // MyClass cl1 = new MyClass();
				// Timestamp t = rs.getTimestamp(4);
				// myClass.setStatus(rs.getInt(6));
				// // if (cl1.getStatus() == 2) {
				// // cl1.setCertificateno(rs.getInt(7));
				// // cl1.setEndtime(t);
				// // cl1.setPassed(true);
				// // }
				// // 先查看学员培训班是否通过再查证书编号
				// // this.setMyPassclass(userid, cl.getId());
				// myClass.setCertificateno(rs.getInt(5));
				// // cl1.setCertificateno(this.getStudyClassCertificateno(
				// // cl.getId(), userid));
				// myClass.setEndtime(t);
				// if (myClass.getCertificateno() > 0) {
				// myClass.setPassed(true);
				// }
				// elClass.setStarttime(rs.getTimestamp(7));
				// elClass.setFinishtime(rs.getTimestamp(8));
				// myClass.setElClass(elClass);
				flag = 1;
			}
		} catch (Exception e) {
			logger.error("获取拿证系统培训班出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public int getElclassIsPass(int userid,int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int flag = 1;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_room  where userid=? and classid=?");
			// ps.setInt(1, classid);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag = 2;
				// return flag;
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经报名（培训班）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
	//wsj1016修改
	/**
	 * Description: 检测并设置培训班通过
	 * 
	 * @Version1.0 2011-9-27 上午11:27:42 by 闻益舜（wenyishun110@163.com）创建
	 * @param userid
	 * @param classid
	 * @throws ElException
	 */
	public void setMyPassclass2(int userid, int classid,int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("call user_ispass_class (?,?)");
			// ps = ct.prepareStatement("call user_ispass_class2(?,?)");//!!
//			ps = ct.prepareStatement("call user_ispass_class2(?,?)");
			ps = ct.prepareStatement("call user_ispass_class2_1(?,?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.setInt(3, roomid);
			ps.executeUpdate();
			// logger.error("检查是否通过培训班");
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}
	//得到选修课已获得的学分
	public int getcountXFforXX(int userid,int classid ) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int sumXF = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select  sum(mycredit) from study_course where userid = ? and classid =? and  status=1");
			// ps.setInt(1, classid);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				sumXF = rs.getInt(1);
				// return flag;
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经报名（培训班）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sumXF;
	}
	
	//查看培训班是否有未通过的必修课程
	public int isNoPassBX(int userid,int classid)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int isnopass = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select * from study_course where userid=? and classid=? and status=0 and passed=0");
			// ps.setInt(1, classid);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				isnopass = 1;
				// return flag;
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经报名（培训班）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return isnopass;
	}
	
	//获得培训班关联考场相关信息
	public MyRoom myRoom(int userid,int classid)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyRoom myRoom = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select ispassed,myscore from study_room where userid=? and classid=?");
			// ps.setInt(1, classid);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				myRoom = new MyRoom();
				myRoom.setIspassed(rs.getInt(1));
				myRoom.setMyScore(rs.getFloat(2));
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经报名（培训班）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myRoom;
	}
	
	//获得必修课总学分
	public int countScoreBX(int classid)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int countScoreBX = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select sum(setcredit) from class_course where classid=? and status=0");
			 ps.setInt(1, classid);
//			ps.setInt(1, userid);
//			ps.setInt(2, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				countScoreBX = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经报名（培训班）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return countScoreBX;
	}
	
	
	//获得必修课已得到的学分
	public int getScoreBX(int userid,int classid)throws ElException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int sumBX = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select  sum(mycredit) from study_course where userid = ? and classid =? and  status=0");
			// ps.setInt(1, classid);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				sumBX = rs.getInt(1);
				// return flag;
			}
		} catch (Exception e) {
			logger.error("检测学员是否已经报名（培训班）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sumBX;
	}
	
	//根据证书号、身份证查询
	public MyClass getZhengShuByNoIdCard(int year,int classid,int no,String  idcard) throws ElException  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyClass myClass = new MyClass();
		ElClass elClass = null;
		// ELUser el = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select cl.id,\n" + "       cl.name,\n"
					+ "       cl.certificatename,\n" + "       sc.applydate,\n"
					+ "       certificateno,\n" + "       sc.status,\n"
					+ "       cl.starttime,\n" + "       cl.finishtime\n"
					+ "  from study_class sc\n"
					+ "  left join elclass cl on sc.classid = cl.id\n"
					+ "   left join eluser er on sc.userid=er.id "
					+ " where cl.year=? and cl.id=? and sc.certificateno=? and er.shenfenzheng=?");
			ps.setInt(1, year);
			ps.setInt(2, classid);
			ps.setInt(3, no);
			ps.setString(4, idcard);
			rs = ps.executeQuery();
			if (rs.next()) {
				elClass = new ElClass(rs.getInt(1), rs.getString(2));
				elClass.setCertificatename(rs.getString(3));
				// ELUser eu = new ELUser(rs.getInt(4), rs.getString(5));
				// MyClass cl1 = new MyClass();
				Timestamp t = rs.getTimestamp(4);
				myClass.setStatus(rs.getInt(6));
				// if (cl1.getStatus() == 2) {
				// cl1.setCertificateno(rs.getInt(7));
				// cl1.setEndtime(t);
				// cl1.setPassed(true);
				// }
				// 先查看学员培训班是否通过再查证书编号
				// this.setMyPassclass(userid, cl.getId());
				myClass.setCertificateno(rs.getInt(5));
				// cl1.setCertificateno(this.getStudyClassCertificateno(
				// cl.getId(), userid));
				myClass.setEndtime(t);
				if (myClass.getCertificateno() > 0) {
					myClass.setPassed(true);
				}
				elClass.setStarttime(rs.getTimestamp(7));
				elClass.setFinishtime(rs.getTimestamp(8));
				myClass.setElClass(elClass);

			}
		} catch (Exception e) {
			logger.error("获取拿证系统培训班出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myClass;
	}
	
	//判断培训班是否绑定考场
	public boolean isBindEroom(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// ELUser el = null;
		boolean flag=false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select examroomid from  elclass_assign_examroom where  classid=?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag=true;
				return flag;

			}
		} catch (Exception e) {
			logger.error("获取拿证系统培训班出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;

	}

	public List<ElClass> getTjElclass(int ctid, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> classlist = new ArrayList<ElClass>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select a.*,rownum rn from(select * from elclass where cltype=? and hot=? and status=5 order by createtime desc)a where rownum<=8";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, ctid);
			ps.setInt(2, hot);
			rs = ps.executeQuery();
			while(rs.next()){
				ElClass elc = new ElClass();
				elc.setId(rs.getInt(1));
				elc.setName(rs.getString(2));
				elc.setCertificatename(rs.getString(3));
				elc.setCltype(new ElClType(rs.getInt(4)));
				elc.setCreatetime(rs.getTimestamp(11));
				//elc.setStarttime(rs.getTimestamp(6));
				//elc.setFinishtime(rs.getTimestamp(7));
				classlist.add(elc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classlist;
	}

	public List<ElClass> getTjElclass(int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> classlist = new ArrayList<ElClass>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select a.*,rownum rn from(select * from elclass where  hot=? and status=5 order by createtime desc)a where rownum<=8";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, hot);
			rs = ps.executeQuery();
			while(rs.next()){
				ElClass elc = new ElClass();
				elc.setId(rs.getInt(1));
				elc.setName(rs.getString(2));
				elc.setCertificatename(rs.getString(3));
				elc.setCltype(new ElClType(rs.getInt(4)));
				elc.setCreatetime(rs.getTimestamp(11));
				//elc.setStarttime(rs.getTimestamp(6));
				//elc.setFinishtime(rs.getTimestamp(7));
				classlist.add(elc);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classlist;
	}

}
