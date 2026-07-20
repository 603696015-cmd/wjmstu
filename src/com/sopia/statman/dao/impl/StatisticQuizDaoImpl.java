package com.sopia.statman.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.DateUtility;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.ElTag;
import com.sopia.common.StringUtil;
import com.sopia.common.getFloat;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ErepBlock;
import com.sopia.courseman.entities.EroomBatch;
import com.sopia.courseman.entities.EroomBlock;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.statman.StatisticConstants;
import com.sopia.statman.dao.StatisticQuizDao;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.entities.MyBatchRoom;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyEprac;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;

public class StatisticQuizDaoImpl implements StatisticQuizDao {
	private static final Log logger = LogFactory
			.getLog(StatisticQuizDaoImpl.class);

	public MyExamPaper getMyEpByCid(int cid, int uid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper mep = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sqi.status,sqi.myScore,sqi.endtime from ROOM_ASSIGN ra "
							+ "left join study_quizinfo sqi on ra.roomid = sqi.roomid "
							+ "where ra.courseid = ? and sqi.userid = ?");
			ps.setInt(1, cid);
			ps.setInt(2, uid);
			rs = ps.executeQuery();
			if (rs.next()) {
				mep.setStatus(rs.getInt(1));
				mep.setMyScore(rs.getFloat(2));
				// mep.setEndtime(rs.getDate(3));
			}
		} catch (Exception e) {
			logger.error("����γ̳ɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mep;
	}

//	public List<ExamRoom> listquziseach(ExamRoom er) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<ExamRoom> ers = new ArrayList<ExamRoom>();
//		try {
//			ct = DBConnection.getConnection();
//			int erlid = er == null ? 1
//					: (er.getEroomLib() == null ? 1
//							: (er.getEroomLib().getId() <= 0 ? 1 : er
//									.getEroomLib().getId()));
//			String title = er == null ? "" : er.getTitle() == null ? "" : er
//					.getTitle().trim();
//			String sql = "select er.id erid,er.title,er.begintime,er.endtime,c.id cid,c.name cname,  count(sqi.userid),erl.id erlid,erl.name erlname from "
//					+ "exam_room er left join eroom_Lib erl on erl.id = er.erlibid left join course c on er.courseid = c.id left join ELUSER eu_c "
//					+ " on er.createrid = eu_c.id left join study_room sqi on sqi.roomid = er.id where er.title like ? and erl.id= ? ";
//			if (null != er.getBegintime() || null != er.getEndtime()) {
//				ps = ct
//						.prepareStatement(sql
//								+ "and er.begintime>?"
//								+ " and er.endtime = ? group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name ");
//				ps.setString(1, "%" + title + "%");
//				ps.setInt(2, erlid);
//				ps.setTimestamp(3, er.getBegintime());
//				ps.setTimestamp(4, er.getEndtime());
//			} else {
//				ps = ct
//						.prepareStatement(sql
//								+ " group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name");
//				ps.setString(1, "%" + title + "%");
//				ps.setInt(2, erlid);
//			}
//
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				ExamRoom er1 = new ExamRoom(rs.getInt(1), rs.getString(2));
//				er1.setBegintime(rs.getTimestamp(3));
//				er1.setEndtime(rs.getTimestamp(4));
//				int cid = rs.getInt(5);
//				Course c = null;
//				if (cid != 0) {
//					c = new Course(cid, rs.getString(6));
//				} else
//					c = new Course(0, "һ�㿼��");
//				er1.setCourse(c);
//				er1.setUserSize(rs.getInt(7));
//				er1.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
//				ers.add(er1);
//			}
//		} catch (Exception e) {
//			logger.error("�鿴���ŵĿ��Գ����б���?", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//
//		return ers;
//	}

//	public int listquziseachCount(ExamRoom er) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		int counts = 0;
//		try {
//			ct = DBConnection.getConnection();
//			int erlid = er == null ? 1
//					: (er.getEroomLib() == null ? 1
//							: (er.getEroomLib().getId() <= 0 ? 1 : er
//									.getEroomLib().getId()));
//			String title = er == null ? "" : er.getTitle() == null ? "" : er
//					.getTitle().trim();
//			String sql = "select count(*) from (select er.id erid,er.title,er.begintime,er.endtime,c.id cid,c.name cname,  count(sqi.userid),erl.id erlid,erl.name erlname from "
//					+ "exam_room er left join eroom_Lib erl on erl.id = er.erlibid left join course c on er.courseid = c.id left join ELUSER eu_c "
//					+ " on er.createrid = eu_c.id left join study_room sqi on sqi.roomid = er.id where er.title like ? and erl.id= ? ";
//			if (null != er.getBegintime() || null != er.getEndtime()) {
//				ps = ct
//						.prepareStatement(sql
//								+ "and er.begintime>?"
//								+ " and er.endtime = ? group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name) t ");
//				ps.setString(1, "%" + title + "%");
//				ps.setInt(2, erlid);
//				ps.setTimestamp(3, er.getBegintime());
//				ps.setTimestamp(4, er.getEndtime());
//			} else {
//				ps = ct
//						.prepareStatement(sql
//								+ " group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name) t");
//				ps.setString(1, "%" + title + "%");
//				ps.setInt(2, erlid);
//			}
//
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				counts += rs.getInt(1);
//			}
//			return counts;
//		} catch (Exception e) {
//			logger.error("�鿴���ŵĿ��Գ����б���?", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//
//	}

//	public List<ExamRoom> listquziseach(ExamRoom er, EroomLib eroomLibTree,
//			int[] libids, int pageNow, int pageSize) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<ExamRoom> ers = new ArrayList<ExamRoom>();
//		try {
//			ct = DBConnection.getConnection();
//			if (libids != null) {
//				for (int i = 0; i < libids.length; i++) {
//
//					String title = er == null ? "" : er.getTitle() == null ? ""
//							: er.getTitle().trim();
//					String sql = "select er.id erid,er.title,er.begintime,er.endtime,c.id cid,c.name cname,  count(sqi.userid),erl.id erlid,erl.name erlname from "
//							+ "exam_room er left join eroom_Lib erl on erl.id = er.erlibid left join course c on er.courseid = c.id left join ELUSER eu_c "
//							+ " on er.createrid = eu_c.id left join study_room sqi on sqi.roomid = er.id where er.title like ? and erl.id in ("
//							+ createPerTypeId(eroomLibTree, libids[i]) + ")";
//					if (null != er.getBegintime() || null != er.getEndtime()) {
//						ps = ct
//								.prepareStatement(sql
//										+ "and er.begintime>?"
//										+ " and er.endtime = ? group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name");
//						ps.setString(1, "%" + title + "%");
//						ps.setTimestamp(2, er.getBegintime());
//						ps.setTimestamp(3, er.getEndtime());
//						ps.setInt(4, pageNow);
//						ps.setInt(5, pageSize);
//					} else {
//						ps = ct
//								.prepareStatement(sql
//										+ " group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name");
//						ps.setString(1, "%" + title + "%");
//						// ps.setInt(2, erlid);
//					}
//
//					rs = ps.executeQuery();
//					while (rs.next()) {
//						ExamRoom er1 = new ExamRoom(rs.getInt(1), rs
//								.getString(2));
//						er1.setBegintime(rs.getTimestamp(3));
//						er1.setEndtime(rs.getTimestamp(4));
//						int cid = rs.getInt(5);
//						Course c = null;
//						if (cid != 0) {
//							c = new Course(cid, rs.getString(6));
//						} else
//							c = new Course(0, "һ�㿼��");
//						er1.setCourse(c);
//						er1.setUserSize(rs.getInt(7));
//						er1.setEroomLib(new EroomLib(rs.getInt(8), rs
//								.getString(9)));
//						ers.add(er1);
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("�鿴���ŵĿ��Գ����б���?", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//
//		return ers;
//	}

//	public int listquziseachCount(ExamRoom er, EroomLib eroomLibTree,
//			int[] libids) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		int counts = 0;
//		try {
//			ct = DBConnection.getConnection();
//			if (libids != null) {
//				for (int i = 0; i < libids.length; i++) {
//
//					String title = er == null ? "" : er.getTitle() == null ? ""
//							: er.getTitle().trim();
//					String sql = "select count(*) from (select er.id erid,er.title,er.begintime,er.endtime,c.id cid,c.name cname,  count(sqi.userid),erl.id erlid,erl.name erlname from "
//							+ "exam_room er left join eroom_Lib erl on erl.id = er.erlibid left join course c on er.courseid = c.id left join ELUSER eu_c "
//							+ " on er.createrid = eu_c.id left join study_room sqi on sqi.roomid = er.id where er.title like ? and erl.id in ("
//							+ createPerTypeId(eroomLibTree, libids[i]) + ")";
//					if (null != er.getBegintime() || null != er.getEndtime()) {
//						ps = ct
//								.prepareStatement(sql
//										+ "and er.begintime>?"
//										+ " and er.endtime = ? group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name) t ");
//						ps.setString(1, "%" + title + "%");
//						ps.setTimestamp(2, er.getBegintime());
//						ps.setTimestamp(3, er.getEndtime());
//					} else {
//						ps = ct
//								.prepareStatement(sql
//										+ " group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name) t");
//						ps.setString(1, "%" + title + "%");
//						// ps.setInt(2, erlid);
//					}
//
//					rs = ps.executeQuery();
//					if (rs.next())
//						counts += rs.getInt(1);
//				}
//			}
//			return counts;
//		} catch (Exception e) {
//			logger.error("�鿴���ŵĿ��Գ����б���?", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}

	public int getERUserByDepidAndERid(int roomid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int counts = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(*) from "
					+ " ROOM_ASSIGN ra,ELUSER eu "
					+ "where ra.userid = eu.id and ra.roomid = ? and eu.depid = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			ps.setInt(2, depid);

			rs = ps.executeQuery();
			if (rs.next()) {
				counts += rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ���������?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return counts;
	}

	public List<MyRoom> listquiz_detail_view(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> meps = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();
			// String sql = " select eu.id,eu.realname,
			// sr.status,eu.username,sr.ispassed,sum(sqi.myScore)
			// myscore,count(sqi.id) from study_room sr left join "
			// + "study_quizinfo sqi on sqi.roomid = sr.roomid and sqi.userid =
			// sr.userid and sqi.roomid = sr.roomid "
			// + "left join ELUSER eu on sr.userid = eu.id "
			// + "where sr.roomid = ? group by eu.id,eu.realname,
			// sr.status,eu.username,sr.ispassed order by myscore desc ";
			String sql = " select  eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,count( sqi.epid) "
					+ "from study_room sr left join ELUSER eu on sr.userid = eu.id left join study_exampaper sqi on sqi.roomid = sr.roomid and sqi.userid = eu.id "
					+ "where sr.roomid = ? group by eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore  order by sr.myscore desc ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyRoom mep = new MyRoom(roomid);
				mep.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				mep.setStatus(rs.getInt(3));
				mep.getTester().setUsername(rs.getString(4));
				mep.setIspassed(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEpsize(rs.getInt(7));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}
	/**
	 * ���ѧԱ��������
	 * @param sql
	 * @param myRoom
	 * @param params
	 */
	private void checkMyRoomParas(StringBuffer sql,MyRoom myRoom,List<Object> params){
		if(myRoom!=null){
			if(myRoom.getTester()!=null){
				if(myRoom.getTester().getUsername()!=null&&!"".equals(myRoom.getTester().getUsername())){
					sql.append(" and eu.username like ?");
					params.add("%"+StringUtil.toLikeStr(myRoom.getTester().getUsername())+"%");
				}
				if(myRoom.getTester().getRealname()!=null&&!"".equals(myRoom.getTester().getRealname())){
					sql.append(" and eu.realname like ?");
					params.add("%"+StringUtil.toLikeStr(myRoom.getTester().getRealname())+"%");
				}
//				if(myRoom.getTester().getDepartment()!=null&&myRoom.getTester().getDepartment().getName()!=null
//						&&"".equals(myRoom.getTester().getDepartment().getName())){
//					sql.append(" and eu.realname=?");
//					params.add(myRoom.getTester().getUsername());
//				}
				if(myRoom.getTester().getDepartment()!=null&&myRoom.getTester().getDepartment().getId()>0){
					sql.append(" and dep.id=?");
					params.add(myRoom.getTester().getDepartment().getId());
				}
			}
			if(myRoom.getStatus()>=0){
				sql.append(" and sr.status=?");
				params.add(myRoom.getStatus());
			}
		}
	}
	/**
	 * ��ѯѧԱ������Ϣ���ľ��б�ҳ��
	 * @param roomid
	 * @param myRoom
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listquiz_detail_view(int roomid,MyRoom myRoom,int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> meps = new ArrayList<MyRoom>();
		try {
			List<Object> params=new ArrayList<Object>();
			ct = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer("select * from ( select t1.* ,rownum rn from (select eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,count( sqi.epid),dep.id depid,dep.name depname "
					+ "from study_room sr left join ELUSER eu on sr.userid = eu.id "
					+ " left join department dep on eu.depid=dep.id "
					+" left join study_exampaper sqi on sqi.roomid = sr.roomid and sqi.userid = eu.id where 1=1");
			this.checkMyRoomParas(sql, myRoom, params);
			sql.append(" and sr.roomid = ? group by eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,dep.id,dep.name order by sr.myscore desc) t1 where rownum <=? ) where rn >=? ");
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			ps.setInt(params.size()+1, roomid);
			ps.setInt(params.size()+2, pageNow);
			ps.setInt(params.size()+3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyRoom mep = new MyRoom(roomid);
				mep.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				mep.getTester().setDepartment(new Department(rs.getInt(8),rs.getString(9)));
				mep.setStatus(rs.getInt(3));
				mep.getTester().setUsername(rs.getString(4));
				mep.setIspassed(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEpsize(rs.getInt(7));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("��ѯѧԱ������Ϣ���ľ��б�ҳ�����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}
	/**
	 * ��ѯѧԱ������Ϣ�������ľ��б�ҳ��
	 * @param roomid
	 * @param myRoom
	 * @return
	 * @throws ElException
	 */
	public int listquiz_detail_viewSize(int roomid,MyRoom myRoom) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			List<Object> params=new ArrayList<Object>();
			ct = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer("select count(sr.userid) "
					+ " from study_room sr left join ELUSER eu on sr.userid = eu.id left join department dep on eu.depid=dep.id where sr.roomid = ? ");
			this.checkMyRoomParas(sql, myRoom, params);
			ps = ct.prepareStatement(sql.toString());
			ps.setInt(1, roomid);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+2, params.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ѯѧԱ������Ϣ�������ľ��б�ҳ�����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<MyRoom> listquiz_detail_view_Page(Department depTree,
			int depid, int roomid, int role, int pageNow, int pageSize)
			throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> meps = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();

			// if (role != 1)
			// depTree.setId(1);
			// String x = Integer.toString(depid);
			// String ids = DepartmentLibById(depTree, depid);
			// if (role != 1 && !ids.equals(x))// ��ɫΪ1����������Ա��ʱû�������ڵ㣬���Բ���Ҫ��ȡ
			// ,����ɫ��Ϊ1ʱids��ֻ��һ����ڵ�ʱҲ����ȡ
			// ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
			// : ids; // ��id���������ʱ,�����е�id��ȥ�������id

			// String sql = " select * from ( select t1.* ,rownum rn from (
			// select eu.id,eu.realname,
			// sr.status,eu.username,sr.ispassed,sr.myscore,count(
			// sqi.id),dep.name,eu.sex ,eu.jingzhong,eu.shengri "
			// + "from study_room sr left join ELUSER eu on sr.userid = eu.id
			// left join study_quizinfo sqi on sqi.roomid = sr.roomid and
			// sqi.userid = eu.id left join department dep on eu.depid = dep.id
			// "
			// + "where sr.roomid = ? and dep.id in ("
			// + ids
			// + ") group by eu.id,eu.realname,
			// sr.status,eu.username,sr.ispassed,sr.myscore,dep.name,eu.sex
			// ,eu.jingzhong,eu.shengri order by sr.myscore desc ) t1 where
			// rownum <=? ) where rn >=?";
			String sql = " select * from ( select t1.* ,rownum rn from ( select  eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,count(sqi.epid),dep.name,eu.sex ,eu.jingzhong,eu.shengri,bdt.basevalue "
					+ "from study_room sr left join ELUSER eu on sr.userid = eu.id left join study_exampaper sqi on sqi.roomid = sr.roomid and sqi.userid = eu.id join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", depTree, true)
					+ ") dep on eu.depid = dep.id "
					+ "left join basedatat bdt on bdt.id=eu.jingzhong where sr.roomid = ?"
					+
					// " and dep.id in ("
					// + ids
					// + ") " +
					"group by eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,dep.name,eu.sex ,eu.jingzhong,eu.shengri,bdt.basevalue order by sr.status desc, sr.myscore desc ) t1 where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyRoom mep = new MyRoom();
				ELUser eluser = new ELUser(rs.getInt(1), rs.getString(2));
				eluser.setDanwei(rs.getString(8));
				eluser.setSex(rs.getString(9));
				// eluser.setJingzhong(rs.getString(10));
				eluser.setAge(DateUtility.GetAge(rs.getDate(11)));
				eluser.setJingzhong(rs.getInt(12));
				mep.setTester(eluser);
				mep.setStatus(rs.getInt(3));
				mep.getTester().setUsername(rs.getString(4));
				mep.setIspassed(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEpsize(rs.getInt(7));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public List<MyRoom> listquiz_detail_view_Page(Department depTree,
			int depid, int roomid, int role ) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> meps = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();
			String sql = " select  eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,count(sqi.epid),dep.name,eu.sex ,eu.jingzhong,eu.shengri,sr.begintime "
					+ "from study_room sr left join ELUSER eu on sr.userid = eu.id left join study_exampaper sqi on sqi.roomid = sr.roomid and sqi.userid = eu.id join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", depTree, true)
					+ ") dep on eu.depid = dep.id "
					+ "where sr.roomid = ?"
					+ "group by eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,dep.name,eu.sex ,eu.jingzhong,eu.shengri,sr.begintime  order by sr.status desc, sr.myscore desc ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyRoom mep = new MyRoom();
				ELUser eluser = new ELUser(rs.getInt(1), rs.getString(2));
				eluser.setDanwei(rs.getString(8));
				eluser.setSex(rs.getString(9));
				eluser.setJingzhong(rs.getInt(10));
				eluser.setAge(DateUtility.GetAge(rs.getDate(11)));
				mep.setTester(eluser);
				mep.setStatus(rs.getInt(3));
				mep.getTester().setUsername(rs.getString(4));
				mep.setIspassed(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEpsize(rs.getInt(7));
				mep.setBegintime(rs.getTimestamp(12));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}
	public List<MyRoom> listquiz_detail_view_Page(Department depTree,
			int depid, ExamRoom room,String ids ) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> meps = new ArrayList<MyRoom>();
		try {
			String wsql = "";
			if(room.getBegintime()!=null){
				wsql+=" and sr.begintime>= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(room.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
			}
			if(room.getEndtime()!=null){
				wsql+=" and sr.begintime<=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(room.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
			}
			ct = DBConnection.getConnection();
			String sql = " select  eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,count(sqi.epid),dep.name,eu.sex ,eu.jingzhong,eu.shengri,sr.begintime,sr.roomid "
					+ "from study_room sr left join ELUSER eu on sr.userid = eu.id left join study_exampaper sqi on sqi.roomid = sr.roomid and sqi.userid = eu.id join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", depTree, true)
					+ ") dep on eu.depid = dep.id "
					+ "where sr.roomid in("+ids+") "+wsql
					+ " group by eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,dep.name,eu.sex ,eu.jingzhong,eu.shengri,sr.begintime,sr.roomid  order by sr.myscore desc ";
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, room.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				MyRoom mep = new MyRoom();
				ELUser eluser = new ELUser(rs.getInt(1), rs.getString(2));
				eluser.setDanwei(rs.getString(8));
				eluser.setSex(rs.getString(9));
				eluser.setJingzhong(rs.getInt(10));
				eluser.setAge(DateUtility.GetAge(rs.getDate(11)));
				mep.setTester(eluser);
				mep.setStatus(rs.getInt(3));
				mep.getTester().setUsername(rs.getString(4));
				mep.setIspassed(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEpsize(rs.getInt(7));
				mep.setBegintime(rs.getTimestamp(12));
				mep.setExamroom(new ExamRoom(rs.getInt(13)));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}
	/**
	 * ����ͳ�ƣ��鿴�����б�ҳ��
	 * @param depTree
	 * @param roomid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listquiz_detail_view(Department depTree,
			ExamRoom examRoom,String ids,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> meps = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();
			String wsql="";
			Vector<Object> params=new Vector<Object>();
			if(examRoom==null){
				return meps;
			}else{
				if(examRoom.getBegintime()!=null){
					wsql+=" and sr.begintime>=? ";
					params.add(examRoom.getBegintime());
				}
				if(examRoom.getEndtime()!=null){
					wsql+=" and sr.begintime<=? ";
					params.add(examRoom.getEndtime());
				}
			}
			String sql = "select * from ( select t1.* ,rownum rn from ( select  eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,count(sqi.epid),dep.name,eu.sex ,eu.jingzhong,eu.shengri,sr.begintime,sr.roomid "
					+ "from study_room sr left join ELUSER eu on sr.userid = eu.id left join study_exampaper sqi on sqi.roomid = sr.roomid and sqi.userid = eu.id join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", depTree, true)
					+ ") dep on eu.depid = dep.id "
					+ "where sr.roomid in("+ids+") "+wsql
					+ "group by eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,dep.name,eu.sex ,eu.jingzhong,eu.shengri,sr.begintime,sr.roomid  order by sr.status desc, sr.myscore desc,sr.begintime asc) t1 where rownum <=? ) where rn >=? ";
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, examRoom.getId());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(1+i, params.get(i));
			}
			ps.setInt(1+params.size(), pageNow);
			ps.setInt(2+params.size(), pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyRoom mep = new MyRoom();
				ELUser eluser = new ELUser(rs.getInt(1), rs.getString(2));
				eluser.setDanwei(rs.getString(8));
				eluser.setSex(rs.getString(9));
				eluser.setJingzhong(rs.getInt(10));
				eluser.setAge(DateUtility.GetAge(rs.getDate(11)));
				mep.setTester(eluser);
				mep.setStatus(rs.getInt(3));
				mep.getTester().setUsername(rs.getString(4));
				mep.setIspassed(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEpsize(rs.getInt(7));
				mep.setBegintime(rs.getTimestamp(12));
				mep.setExamroom(new ExamRoom(rs.getInt(13)));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("����ͳ�ƣ��鿴�����б�ҳ�����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}
	
	/**
	 * ����ͳ���������鿴�����б�ҳ��
	 * @param depTree
	 * @param roomid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listquiz_detail_view_size(Department depTree,
			ExamRoom examRoom,String ids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String wsql="";
			Vector<Object> params=new Vector<Object>();
			if(examRoom==null){
				return 0;
			}else{
				if(examRoom.getBegintime()!=null){
					wsql+=" and sr.begintime>=? ";
					params.add(examRoom.getBegintime());
				}
				if(examRoom.getEndtime()!=null){
					wsql+=" and sr.begintime<=? ";
					params.add(examRoom.getEndtime());
				}
			}
			String sql = "select  count(*) "
					+ "from study_room sr left join ELUSER eu on sr.userid = eu.id join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", depTree, true)
					+ ") dep on eu.depid = dep.id "
					+ "where sr.roomid in ("+ids+") "+wsql;
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, examRoom.getId());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(1+i, params.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("����ͳ���������鿴�����б�ҳ�����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listquiz_detail_view_Count(Department depTree, int depid,
			int roomid, int role) throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();

			// if (role != 1)
			// depTree.setId(1);
			// String x = Integer.toString(depid);
			// String ids = DepartmentLibById(depTree, depid);
			// if (role != 1 && !ids.equals(x))// ��ɫΪ1����������Ա��ʱû�������ڵ㣬���Բ���Ҫ��ȡ
			// // ,����ɫ��Ϊ1ʱids��ֻ��һ����ڵ�ʱҲ����ȡ
			// ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
			// : ids; // ��id���������ʱ,�����е�id��ȥ�������id

			// String sql = " select count(eu.id) from study_room sr left join
			// ELUSER eu on sr.userid = eu.id left join study_quizinfo sqi on
			// sqi.roomid = sr.roomid "
			// + "and sqi.userid = eu.id left join department dep on eu.depid =
			// dep.id "
			// + "where sr.roomid = ? and dep.id in (" + ids + ") ";
			String sql = " select  count(eu.id) from study_room sr left join ELUSER eu on sr.userid = eu.id join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", depTree, true)
					+ ") dep on eu.depid = dep.id  " + "where sr.roomid = ? ";
			// "and dep.id in (" + ids + ") ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	public List<MyExamPaper> list_detail_view_quizpaper(int roomid, int userid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select   sqi.myScore, sqi.status,sqi.ispassed,eprs.id epid1,eprs.title  from ( select * from exam_reps erps1  left join exampaper ep1 on ep1.id = erps1.epid where erps1.roomid =?) eprs left  join (select * from study_exampaper  where  userid = ?) sqi on eprs.id = sqi.epid and eprs.roomid = sqi.roomid order by eprs.id ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(1);
				mep.setMyScore(rs.getFloat(1));
				// mep.setEndtime(rs.getTimestamp(3));
				Object obj=rs.getObject(2);
				if(obj!=null){
					mep.setStatus(Integer.parseInt(obj.toString()));
				}else{
					mep.setId(0);
				}
				mep.setIspassed(rs.getInt(3));
				mep.setExamPaper(new ExamPaper(rs.getInt(4), rs.getString(5)));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;

	}
	private float getEpblockScore(String ids,int roomid,int userid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		float x = 0f;
		try {
			ct = DBConnection.getConnection();
			String sql = "select sum(sb.myscore) from study_blocks sb left join study_quizinfo sq on sq.id=sb.sqid where blockid in("+ids+" ) and sq.userid =? and sq.roomid = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1,userid );
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				x = rs.getFloat(1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}
	public List<MyExamPaper> list_detail_view_quizblock(EroomBlock erblock, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			if(erblock==null||erblock.getErepblocks()==null){
				return null;
			}
			List<ErepBlock>  eepbs= erblock.getErepblocks();
			for (int i = 0; i < eepbs.size(); i++) {
				MyExamPaper mep = new MyExamPaper();
				mep.setMyScore(getEpblockScore(eepbs.get(i).getBlockids(), erblock.getEroom().getId(), userid));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}
	public List<MyRoom> listquizblock_detail_view(Department depTree,
			ExamRoom examRoom, String ids, int pageNow, int pageSize)
			throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<MyRoom> meps = new ArrayList<MyRoom>();
//		try {
//			ct = DBConnection.getConnection();
//			// String sql = " select eu.id,eu.realname,
//			// sr.status,eu.username,sr.ispassed,sum(sqi.myScore)
//			// myscore,count(sqi.id) from study_room sr left join "
//			// + "study_quizinfo sqi on sqi.roomid = sr.roomid and sqi.userid =
//			// sr.userid and sqi.roomid = sr.roomid "
//			// + "left join ELUSER eu on sr.userid = eu.id "
//			// + "where sr.roomid = ? group by eu.id,eu.realname,
//			// sr.status,eu.username,sr.ispassed order by myscore desc ";
//			String sql = " select  eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,count( sqi.epid) "
//					+ "from study_room sr left join ELUSER eu on sr.userid = eu.id left join study_exampaper sqi on sqi.roomid = sr.roomid and sqi.userid = eu.id "
//					+ "where sr.roomid = ? group by eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore  order by sr.myscore desc ";
//			ps = ct.prepareStatement(sql);
//			ps.setInt(1, roomid);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				MyRoom mep = new MyRoom(roomid);
//				mep.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
//				mep.setStatus(rs.getInt(3));
//				mep.getTester().setUsername(rs.getString(4));
//				mep.setIspassed(rs.getInt(5));
//				mep.setMyScore(rs.getFloat(6));
//				mep.setEpsize(rs.getInt(7));
//				meps.add(mep);
//			}
//		} catch (Exception e) {
//			logger.error("�鿴���ŵĿ��Գɼ����?", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
		return null;
	}
	public int listquizblock_detail_view_size(Department depTree,
			ExamRoom examRoom, String ids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String wsql="";
			Vector<Object> params=new Vector<Object>();
			if(examRoom==null){
				return 0;
			}else{
				if(examRoom.getBegintime()!=null){
					wsql+=" and sr.begintime>=? ";
					params.add(examRoom.getBegintime());
				}
				if(examRoom.getEndtime()!=null){
					wsql+=" and sr.begintime<=? ";
					params.add(examRoom.getEndtime());
				}
			}
			String sql = "select  count(*) "
					+ "from study_room sr left join ELUSER eu on sr.userid = eu.id join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", depTree, true)
					+ ") dep on eu.depid = dep.id "
					+ "where sr.roomid in ("+ids+") "+wsql;
			ps = ct.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(1+i, params.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("����ͳ���������鿴�����б�ҳ�����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	public List<MyExamPaper> list_read_quizpaper(int roomid, int epid,
			int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select sqi.id,sqi.myScore,sqi.status ,sqi.ispassed,sqi.begintime,sqi.userid,sqi.endtime from (select * from study_quizinfo  where  userid = ?) sqi where sqi.roomid=? and sqi.epid = ? order by sqi.endtime desc";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.setInt(3, epid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setMyScore(rs.getFloat(2));
				// mep.setEndtime(rs.getTimestamp(3));
				mep.setStatus(rs.getInt(3));
				mep.setIspassed(rs.getInt(4));
				mep.setBegintime(rs.getTimestamp(5));
				// mep.setExamPaper(new ExamPaper(rs.getInt(4),
				// rs.getString(5)));
				mep.setTester(new ELUser(rs.getInt(6)));
				mep.setEndtime(rs.getTimestamp(7));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public List<MyExamPaper> listquizpaper_detail_view(int roomid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			String sql = " select sqi.id sqid, eu.id,eu.realname,sqi.myScore,sqi.endtime,sqi.status,eu.username,sqi.ispassed,ep.id epid,ep.title  from "
					+ "study_quizinfo sqi  left join ELUSER eu on sqi.userid = eu.id left join exampaper ep on ep.id = sqi.epid where sqi.roomid = ? and  sqi.userid = ? order by ep.id desc ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2), rs.getString(3)));
				mep.setMyScore(rs.getFloat(4));
				mep.setEndtime(rs.getTimestamp(5));
				mep.setStatus(rs.getInt(6));
				mep.getTester().setUsername(rs.getString(7));
				mep.setIspassed(rs.getInt(8));
				mep.setExamPaper(new ExamPaper(rs.getInt(9), rs.getString(10)));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public List<ExamPaper> listSimEpByDid(int depid, ExamPaper ep1)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> meps = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_SIMEXAM_DIDANDEPID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaper ep = new ExamPaper();
				ep.setId(rs.getInt(1));
				// ep.setCourse(new Course(rs.getInt(2),rs.getString(3)));
				// ep.getCourse().setCreater(new
				// ELUser(rs.getInt(4),rs.getString(5)));
				ep.setTitle(rs.getString(6));
				meps.add(ep);
			}
		} catch (Exception e) {
			logger.error("�鿴����ģ�⿼�Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public ExamPaper getEPbyEpidAndCid(int epid, int cid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			String sql = "select sep.exampaperid , sep.courseid ,c.name,c.creater,eu_c.realname,ep.title,c.passgrade "
					+ "from SIMEXAMPAPER sep,COURSE c,ELUSER eu_c,EXAMPAPER ep where "
					+ "sep.courseid = c.id and eu_c.id = c.creater and sep.exampaperid = ep.id and "
					+ "c.id = ? and ep.id =?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, cid);
			ps.setInt(2, epid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep.setId(rs.getInt(1));
				// ep.setCourse(new Course(rs.getInt(2),rs.getString(3)));
				// ep.getCourse().setCreater(new
				// ELUser(rs.getInt(4),rs.getString(5)));
				// ep.getCourse().setPassgrade(rs.getInt(7));
				ep.setTitle(rs.getString(6));
			}
		} catch (Exception e) {
			logger.error("�鿴����ģ�⿼�Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	public int getSimUserBydepid(int epid, int depid, int cid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(*) from student_siminfo ssi,ELUSER eu where ssi.epid = ? and ssi.courseid=? and ssi.userid =eu.id and eu.department = ? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, epid);
			ps.setInt(2, cid);
			ps.setInt(3, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("�鿴����ģ�⿼�Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<MyExamPaper> listSimEps(int depid, int epid, int cid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			String sql = " select sqi.id, eu.id,eu.realname,sqi.myScore,sqi.endtime,sqi.status,eu.username from "
					+ " STUDENT_SIMINFO sqi ,ELUSER eu "
					+ "where sqi.epid = ? and eu.id = sqi.userid and sqi.userid in(select id from ELUSER eu1 where eu1.department = ?) "
					+ "and sqi.courseid = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, epid);
			ps.setInt(2, depid);
			ps.setInt(3, cid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2), rs.getString(3)));
				mep.setMyScore(rs.getInt(4));
				// mep.setEndtime(rs.getDate(5));
				mep.setStatus(rs.getInt(6));
				mep.getTester().setUsername(rs.getString(7));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public StatisticQuizDaoImpl() {
	}

	// public List<MyEprac> listexamprac(int depid, Examprac examprac,
	// boolean sub, int begin, int end) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<MyEprac> xx = new ArrayList<MyEprac>();
	// try {
	// ct = DBConnection.getConnection();
	// String title = examprac == null ? ""
	// : examprac.getTitle() == null ? "" : examprac.getTitle()
	// .trim();
	// String con = " and epr.title like '%" + title + "%' ";
	// if (examprac != null && examprac.getBegintime() != null)
	// con = con
	// + " and epr.begintime >=to_date('"
	// + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(examprac.getBegintime())
	// + "','yyyy-MM-dd HH24:mi:ss') ";
	// if (examprac != null && examprac.getEndtime() != null)
	// con = con
	// + " and epr.endtime <= to_date('"
	// + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(examprac.getEndtime())
	// + "','yyyy-MM-dd HH24:mi:ss') ";
	// if (sub) {
	// ps = ct
	// .prepareStatement("select lid,rid from department where id = ?");
	// ps.setInt(1, depid);
	// int lid = 0, rid = 0;
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// lid = rs.getInt(1);
	// rid = rs.getInt(2);
	// }
	// rs.close();
	// String sql = "select * from (select t.*,rownum rn from (select distinct
	// epr.id,epr.title,epr.begintime,epr.endtime "
	// + " from examprac epr left join examprac_assign epra on epra.eprid=
	// epr.id left join eluser eu on epra.userid = eu.id left join department
	// dep on dep.id= eu.depid where dep.lid>=? and dep.rid <=? "
	// + con + ")t where rownum <=?) where rn>=?";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, lid);
	// ps.setInt(2, rid);
	// ps.setInt(3, begin);
	// ps.setInt(4, end);
	// } else {
	// String sql = "select * from (select t.*,rownum rn from (select distinct
	// epr.id,epr.title,epr.begintime,epr.endtime "
	// + " from examprac epr left join examprac_assign epra on epra.eprid=
	// epr.id left join eluser eu on epra.userid = eu.id left join department
	// dep on dep.id= eu.depid where dep.id =? "
	// + con + ")t where rownum <=?) where rn>=?";
	// ps = ct.prepareStatement(sql);
	//
	// ps.setInt(1, depid);
	// ps.setInt(2, begin);
	// ps.setInt(3, end);
	//
	// }
	//
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// MyEprac mepr = new MyEprac();
	// Examprac epr = new Examprac();
	// epr.setId(rs.getInt(1));
	// epr.setTitle(rs.getString(2));
	// epr.setBegintime(rs.getTimestamp(3));
	// epr.setEndtime(rs.getTimestamp(4));
	// mepr.setPrac(epr);
	// // mepr.setTimes(rs.getInt(5));
	// // mepr.setTotalscore(rs.getFloat(6));
	// // mepr.setMaxscore(rs.getFloat(7));
	// // mepr.setAvgscore(mepr.getTimes() == 0 ? 0 : mepr
	// // .getTotalscore()
	// // / mepr.getTimes());
	// xx.add(mepr);
	// }
	// } catch (Exception e) {
	// logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	//
	// return xx;
	// }

	public void checkExampracParam(StringBuffer sql, Examprac examprac,
			List<Object> params) {
		if (examprac != null) {
			if (examprac.getTitle() != null && !examprac.getTitle().equals("")) {
				sql.append(" and epr.title like ?");
				params.add("%" + StringUtil.toLikeStr(examprac.getTitle())
						+ "%");
			}
			if (examprac.getBegintime() != null) {
				sql.append(" and epr.begintime >=?");
				params.add(examprac.getBegintime());
			}
			if (examprac.getEndtime() != null) {
				sql.append(" and epr.endtime <=?");
				params.add(examprac.getEndtime());
			}
		}
	}

	/**
	 * ��ϰͳ�Ʋ�ѯ��ϰ�б�
	 * 
	 * @param tree
	 * @param examprac
	 * @param sublibs
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyEprac> listexamprac(Examprac examprac, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyEprac> myEpracs = new ArrayList<MyEprac>();
		try {
			ct = DBConnection.getConnection();
			// boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			// StringBuffer sql=new StringBuffer("select * from (select
			// t.*,rownum rn from (select distinct
			// epr.id,epr.title,epr.begintime,epr.endtime "+
			// " from examprac epr left join examprac_assign epra on epra.eprid=
			// epr.id " +
			// " left join eluser eu on epra.userid = eu.id " +
			// " inner join
			// ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department",
			// tree, consub)+") dep on dep.id= eu.depid where 1=1 ");
			StringBuffer sql = new StringBuffer(
					"select * from (select t.*,rownum rn from (select distinct epr.id,epr.title,epr.begintime,epr.endtime "
							+ " from examprac epr where 1=1 ");
			this.checkExampracParam(sql, examprac, params);
			sql.append(")t where rownum <=?) where rn>=?");
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			ps.setInt(params.size() + 1, pageNow);
			ps.setInt(params.size() + 2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyEprac mepr = new MyEprac();
				Examprac epr = new Examprac();
				epr.setId(rs.getInt(1));
				epr.setTitle(rs.getString(2));
				epr.setBegintime(rs.getTimestamp(3));
				epr.setEndtime(rs.getTimestamp(4));
				mepr.setPrac(epr);
				// mepr.setTimes(rs.getInt(5));
				// mepr.setTotalscore(rs.getFloat(6));
				// mepr.setMaxscore(rs.getFloat(7));
				// mepr.setAvgscore(mepr.getTimes() == 0 ? 0 : mepr
				// .getTotalscore()
				// / mepr.getTimes());
				myEpracs.add(mepr);
			}
		} catch (Exception e) {
			logger.error("��ϰͳ�Ʋ�ѯ��ϰ�б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myEpracs;
	}

	/**
	 * ��ϰͳ�Ʋ�ѯ��ϰ�б�����
	 * 
	 * @param tree
	 * @param examprac
	 * @param sublibs
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listexampracSize(Examprac examprac) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("select count(epr.id) "
					+ " from examprac epr  where 1=1 ");
			this.checkExampracParam(sql, examprac, params);
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ϰͳ�Ʋ�ѯ��ϰ�б�����ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	// public int listexampracsize(int depid, Examprac examprac, boolean sub)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// int size = 0;
	// try {
	// ct = DBConnection.getConnection();
	// String title = examprac == null ? ""
	// : examprac.getTitle() == null ? "" : examprac.getTitle()
	// .trim();
	// String con = " and epr.title like '%" + title + "%' ";
	// if (examprac != null && examprac.getBegintime() != null)
	// con = con
	// + " and epr.begintime >=to_date('"
	// + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(examprac.getBegintime())
	// + "','yyyy-MM-dd HH24:mi:ss') ";
	// if (examprac != null && examprac.getEndtime() != null)
	// con = con
	// + " and epr.endtime <= to_date('"
	// + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(examprac.getEndtime())
	// + "','yyyy-MM-dd HH24:mi:ss') ";
	// if (sub) {
	// ps = ct
	// .prepareStatement("select lid,rid from department where id = ?");
	// ps.setInt(1, depid);
	// int lid = 0, rid = 0;
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// lid = rs.getInt(1);
	// rid = rs.getInt(2);
	// }
	// rs.close();
	// String sql = "select count(*) from (select t.*,rownum rn from (select
	// distinct epr.id,epr.title,epr.begintime,epr.endtime "
	// + " from examprac epr left join examprac_assign epra on epra.eprid=
	// epr.id left join eluser eu on epra.userid = eu.id left join department
	// dep on dep.id= eu.depid where dep.lid>=? and dep.rid <=? "
	// + con + ")t ) ";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, lid);
	// ps.setInt(2, rid);
	// } else {
	// String sql = "select count(*) from (select t.*,rownum rn from (select
	// distinct epr.id,epr.title,epr.begintime,epr.endtime "
	// + " from examprac epr left join examprac_assign epra on epra.eprid=
	// epr.id left join eluser eu on epra.userid = eu.id left join department
	// dep on dep.id= eu.depid where dep.id =? "
	// + con + ")t ) ";
	// ps = ct.prepareStatement(sql);
	//
	// ps.setInt(1, depid);
	//
	// }
	//
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// size = rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	//
	// return size;
	// }

	// public List<MyEprac> listexamprac(int depid, int pracid, boolean sub,
	// int begin, int end) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<MyEprac> xx = new ArrayList<MyEprac>();
	// try {
	// ct = DBConnection.getConnection();
	//
	// if (sub) {
	// ps = ct
	// .prepareStatement("select lid,rid from department where id = ?");
	// ps.setInt(1, depid);
	// int lid = 0, rid = 0;
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// lid = rs.getInt(1);
	// rid = rs.getInt(2);
	// }
	// rs.close();
	// String sql = "select * from (select t.*,rownum rn from (select eu.id
	// euid,eu.username,eu.realname, "
	// + "avg(epra.myscore) pracscore,dep.id depid,dep.name depname from
	// eprac_quizinfo epra left join eluser eu on epra.userid = eu.id "
	// + "left join department dep on dep.id= eu.depid where dep.lid>=? and
	// dep.rid <=? and epra.pracid = ? group by eu.id
	// ,eu.username,eu.realname,dep.id ,dep.name )t where rownum <=?) where
	// rn>=?";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, lid);
	// ps.setInt(2, rid);
	// ps.setInt(3, pracid);
	// ps.setInt(4, begin);
	// ps.setInt(5, end);
	// } else {
	// String sql = "select * from (select t.*,rownum rn from (select eu.id
	// euid,eu.username,eu.realname, "
	// + "avg(epra.myscore) pracscore,dep.id depid,dep.name depname from
	// eprac_quizinfo epra left join eluser eu on epra.userid = eu.id "
	// + "left join department dep on dep.id= eu.depid where dep.id =? and
	// epra.pracid = ? group by eu.id ,eu.username,eu.realname,dep.id ,dep.name
	// )t where rownum <=?) where rn>=?";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, depid);
	// ps.setInt(2, pracid);
	// ps.setInt(3, begin);
	// ps.setInt(4, end);
	//
	// }
	//
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// MyEprac mepr = new MyEprac();
	// Examprac epr = new Examprac();
	// mepr.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
	// if (mepr.getTester().getRealname() == null
	// || "".equals(mepr.getTester().getRealname().trim())) {
	// mepr.getTester().setRealname(rs.getString(3));
	// }
	// mepr.setAvgscore(rs.getFloat(4));
	//
	// mepr.getTester().setDepartment(
	// new Department(rs.getInt(5), rs.getString(6)));
	// mepr.setPrac(epr);
	//
	// xx.add(mepr);
	// }
	// } catch (Exception e) {
	// logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	//
	// return xx;
	// }
	// /**
	// * ��ȡ��ϰͳ��������Ա�б�
	// * @param depid
	// * @param pracid
	// * @param sub
	// * @param begin
	// * @param end
	// * @return
	// * @throws ElException
	// */
	// public List<MyEprac> listexamprac2(int depid, int pracid, boolean sub,
	// int begin, int end) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<MyEprac> xx = new ArrayList<MyEprac>();
	// try {
	// ct = DBConnection.getConnection();
	//
	// if (sub) {
	// ps = ct.prepareStatement("select lid,rid from department where id = ?");
	// ps.setInt(1, depid);
	// int lid = 0, rid = 0;
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// lid = rs.getInt(1);
	// rid = rs.getInt(2);
	// }
	// rs.close();
	// String sql = "select * from (select t.*,rownum rn from (select eu.id
	// euid,eu.username,eu.realname, avg(epra.myscore) pracscore,dep.id
	// depid,dep.name depname,decode(avg(epra.myscore),null,0,1) isprac from
	// (select userid,myscore from eprac_quizinfo where pracid=?) epra right
	// join examprac_assign ea on epra.userid=ea.userid left join eluser eu on
	// ea.userid = eu.id left join department dep on dep.id= eu.depid where
	// dep.lid>=? and dep.rid <=? and ea.eprid =? group by ea.userid ,eu.id
	// ,eu.username,eu.realname,dep.id ,dep.name order by nvl(pracscore,0)
	// desc)t where rownum <=?) where rn>=?";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, pracid);
	// ps.setInt(2, lid);
	// ps.setInt(3, rid);
	// ps.setInt(4, pracid);
	// ps.setInt(5, begin);
	// ps.setInt(6, end);
	// } else {
	// String sql = "select * from (select t.*,rownum rn from (select eu.id
	// euid,eu.username,eu.realname, "
	// + "avg(epra.myscore) pracscore,dep.id depid,dep.name depname from
	// eprac_quizinfo epra left join eluser eu on epra.userid = eu.id "
	// + "left join department dep on dep.id= eu.depid where dep.id =? and
	// epra.pracid = ? group by eu.id ,eu.username,eu.realname,dep.id ,dep.name
	// )t where rownum <=?) where rn>=?";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, depid);
	// ps.setInt(2, pracid);
	// ps.setInt(3, begin);
	// ps.setInt(4, end);
	//
	// }
	//
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// MyEprac mepr = new MyEprac();
	// Examprac epr = new Examprac();
	// mepr.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
	// if (mepr.getTester().getRealname() == null
	// || "".equals(mepr.getTester().getRealname().trim())) {
	// mepr.getTester().setRealname(rs.getString(3));
	// }
	// mepr.setAvgscore(getFloat.GetFloat(rs.getFloat(4)));
	// mepr.getTester().setDepartment(
	// new Department(rs.getInt(5), rs.getString(6)));
	// mepr.setPrac(epr);
	// //�ж��Ƿ���ϰ��
	// int isprac=rs.getInt("isprac");
	// if(isprac==0){
	// mepr.setPracStatus("δ��ʼ");
	// }else{
	// mepr.setPracStatus("�ѿ�ʼ");
	// }
	// xx.add(mepr);
	// }
	// } catch (Exception e) {
	// logger.error("��ȡ��ϰͳ��������Ա�б�ʧ�ܣ�", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	//
	// return xx;
	// }

	/**
	 * ��ȡ��ϰ�Ծ�ͨ������ķ�ֵ
	 */
	private float getPracPassScore(int pracid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct=null;
		try {
			ct=DBConnection.getConnection();
			String sql = "select ep.ep_tscore*epr.passgrade/100 epPassgrade from examprac epr left join exampaper ep on epr.epid=ep.id where epr.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pracid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ��ϰ�Ծ�ͨ������ķ�ֵʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * ��ȡ��ϰͳ��������Ա�б�
	 * 
	 * @param depid
	 * @param pracid
	 * @param sub
	 * @param begin
	 * @param end
	 * @return
	 * @throws ElException
	 */
	public List<MyEprac> listexamprac(ElNode tree, int pracid, boolean consub,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyEprac> xx = new ArrayList<MyEprac>();
		try {
			ct = DBConnection.getConnection();
			// String sql = "select * from (select t.*,rownum rn from (select
			// eu.id euid,eu.username,eu.realname, avg(epra.myscore)
			// pracscore,dep.id depid,dep.name
			// depname,decode(avg(epra.myscore),null,0,1) isprac from (select
			// userid,myscore from eprac_quizinfo where pracid=?) epra right
			// join examprac_assign ea on epra.userid=ea.userid left join eluser
			// eu on ea.userid = eu.id left join department dep on dep.id=
			// eu.depid where dep.lid>=? and dep.rid <=? and ea.eprid =? group
			// by ea.userid ,eu.id ,eu.username,eu.realname,dep.id ,dep.name
			// order by nvl(pracscore,0) desc)t where rownum <=?) where rn>=?";
			StringBuffer sql = new StringBuffer(
					"select * from (select t.*,rownum rn from ("
							+ "select eu.id euid,eu.username,eu.realname, avg(epra.myscore) pracscore,dep.id depid,dep.name depname,decode(avg(epra.myscore),null,0,1) isprac "
							+ " from (select userid,myscore from eprac_quizinfo where pracid=?) epra "
							+ " right join examprac_assign ea on epra.userid=ea.userid left join eluser eu on ea.userid = eu.id "
							+ " inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean(ElConstants.CLASS_ELNODESQL))
									.generateSQLByTree("department", tree,
											consub)
							+ ") dep on dep.id= eu.depid where ea.eprid =? group by ea.userid,eu.id,eu.username,eu.realname,dep.id ,dep.name order by nvl(pracscore,0) desc"
							+ ")t where rownum <=?) where rn>=?");
			ps = ct.prepareStatement(sql.toString());
			ps.setInt(1, pracid);
			ps.setInt(2, pracid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyEprac mepr = new MyEprac();
				Examprac epr = new Examprac();
				mepr.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				mepr.getTester().setRealname(rs.getString(3));
				mepr.setAvgscore(getFloat.GetFloat(rs.getFloat(4)));
				mepr.getTester().setDepartment(
						new Department(rs.getInt(5), rs.getString(6)));
				mepr.setPrac(epr);
				mepr.setPassScore(this.getPracPassScore(pracid));
				// �ж��Ƿ���ϰ��
				int isprac = rs.getInt("isprac");
				if (isprac == 0) {
					mepr.setPracStatus("δ��ʼ");
				} else {
					mepr.setPracStatus("�ѿ�ʼ");
				}
				xx.add(mepr);
			}
		} catch (Exception e) {
			logger.error("��ȡ��ϰͳ��������Ա�б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return xx;
	}

	/**
	 * ��ȡ��ϰͳ��������Ա�б�����
	 * 
	 * @param depid
	 * @param pracid
	 * @param sub
	 * @param begin
	 * @param end
	 * @return
	 * @throws ElException
	 */
	public int listexampracSize(ElNode tree, int pracid, boolean consub)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer(
					"select count(ea.userid) from examprac_assign ea "
							+ " left join eluser eu on ea.userid = eu.id "
							+ " inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean(ElConstants.CLASS_ELNODESQL))
									.generateSQLByTree("department", tree,
											consub)
							+ ") dep on dep.id= eu.depid where ea.eprid=?");
			ps = ct.prepareStatement(sql.toString());
			ps.setInt(1, pracid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ��ϰͳ��������Ա�б�����ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	// public List<MyEprac> listexamprac(int depid, int pracid, boolean sub)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<MyEprac> xx = new ArrayList<MyEprac>();
	// try {
	// ct = DBConnection.getConnection();
	//
	// if (sub) {
	// ps = ct
	// .prepareStatement("select lid,rid from department where id = ?");
	// ps.setInt(1, depid);
	// int lid = 0, rid = 0;
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// lid = rs.getInt(1);
	// rid = rs.getInt(2);
	// }
	// rs.close();
	// String sql = "select eu.id euid,eu.username,eu.realname, "
	// + "avg(epra.myscore) pracscore,dep.id depid,dep.name depname from
	// eprac_quizinfo epra left join eluser eu on epra.userid = eu.id "
	// + "left join department dep on dep.id= eu.depid where dep.lid>=? and
	// dep.rid <=? and epra.pracid = ? group by eu.id
	// ,eu.username,eu.realname,dep.id ,dep.name ";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, lid);
	// ps.setInt(2, rid);
	// ps.setInt(3, pracid);
	// } else {
	// String sql = "select eu.id euid,eu.username,eu.realname, "
	// + "avg(epra.myscore) pracscore,dep.id depid,dep.name depname from
	// eprac_quizinfo epra left join eluser eu on epra.userid = eu.id "
	// + "left join department dep on dep.id= eu.depid where dep.id =? and
	// epra.pracid = ? group by eu.id ,eu.username,eu.realname,dep.id ,dep.name
	// ";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, depid);
	// ps.setInt(2, pracid);
	//
	// }
	//
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// MyEprac mepr = new MyEprac();
	// Examprac epr = new Examprac();
	// mepr.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
	// if (mepr.getTester().getRealname() == null
	// || "".equals(mepr.getTester().getRealname().trim())) {
	// mepr.getTester().setRealname(rs.getString(3));
	// }
	// mepr.setAvgscore(rs.getFloat(4));
	//
	// mepr.getTester().setDepartment(
	// new Department(rs.getInt(5), rs.getString(6)));
	// mepr.setPrac(epr);
	//
	// xx.add(mepr);
	// }
	// } catch (Exception e) {
	// logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	//
	// return xx;
	// }

	// public int listexampracsize(int depid, int pracid, boolean sub)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// int size = 0;
	// try {
	// ct = DBConnection.getConnection();
	// if (sub) {
	// ps = ct
	// .prepareStatement("select lid,rid from department where id = ?");
	// ps.setInt(1, depid);
	// int lid = 0, rid = 0;
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// lid = rs.getInt(1);
	// rid = rs.getInt(2);
	// }
	// rs.close();
	// String sql = "select count(*) from (select t.*,rownum rn from (select
	// distinct epr.id,epr.title,epr.begintime,epr.endtime "
	// + " from examprac epr left join eprac_quizinfo epra on epra.pracid=
	// epr.id left join eluser eu on epra.userid = eu.id left join department
	// dep on dep.id= eu.depid where dep.lid>=? and dep.rid <=? and epra.pracid
	// = ?)t ) ";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, lid);
	// ps.setInt(2, rid);
	// ps.setInt(3, pracid);
	//
	// } else {
	// String sql = "select count(*) from (select t.*,rownum rn from (select
	// distinct epr.id,epr.title,epr.begintime,epr.endtime "
	// + " from examprac epr left join eprac_quizinfo epra on epra.pracid=
	// epr.id left join eluser eu on epra.userid = eu.id left join department
	// dep on dep.id= eu.depid where dep.id =? and epra.pracid = ?)t ) ";
	// ps = ct.prepareStatement(sql);
	//
	// ps.setInt(1, depid);
	// ps.setInt(2, pracid);
	//
	// }
	//
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// size = rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	//
	// return size;
	// }
	// /**
	// * ��ȡ��ϰͳ����������
	// * @param depid
	// * @param pracid
	// * @param sub
	// * @return
	// * @throws ElException
	// */
	// public int listexampracsize2(int depid, int pracid, boolean sub)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// int size = 0;
	// try {
	// ct = DBConnection.getConnection();
	// if (sub) {
	// ps = ct
	// .prepareStatement("select lid,rid from department where id = ?");
	// ps.setInt(1, depid);
	// int lid = 0, rid = 0;
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// lid = rs.getInt(1);
	// rid = rs.getInt(2);
	// }
	// rs.close();
	// String sql = "select count(*) from examprac_assign ea left join eluser eu
	// on ea.userid = eu.id left join department dep on dep.id= eu.depid where
	// dep.lid>=? and dep.rid <=? and ea.eprid=? ";
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, lid);
	// ps.setInt(2, rid);
	// ps.setInt(3, pracid);
	//		
	// } else {
	// String sql = "select count(*) from (select t.*,rownum rn from (select
	// distinct epr.id,epr.title,epr.begintime,epr.endtime "
	// + " from examprac epr left join eprac_quizinfo epra on epra.pracid=
	// epr.id left join eluser eu on epra.userid = eu.id left join department
	// dep on dep.id= eu.depid where dep.id =? and epra.pracid = ?)t ) ";
	// ps = ct.prepareStatement(sql);
	//		
	// ps.setInt(1, depid);
	// ps.setInt(2, pracid);
	//		
	// }
	//		
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// size = rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("��ȡ��ϰͳ����������ʧ�ܣ�", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return size;
	// }

	public Examprac getexamprac_gk(int depid, int pracid, boolean sub)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Examprac ep = new Examprac();
		try {
			ct = DBConnection.getConnection();
			if (sub) {
				ps = ct
						.prepareStatement("select lid,rid from department where id = ?");
				ps.setInt(1, depid);
				int lid = 0, rid = 0;
				rs = ps.executeQuery();
				if (rs.next()) {
					lid = rs.getInt(1);
					rid = rs.getInt(2);
				}
				rs.close();
				String sql = "select count(usize),avg(avgscore) from (select count( epq.userid) usize,avg(epq.myscore) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.pracid = ? and dep.lid>=? and dep.rid<=? group by epq.userid) ";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, lid);
				ps.setInt(3, rid);
				rs = ps.executeQuery();

			} else {
				String sql = "select count(usize),avg(avgscore) from (select count( epq.userid) usize,avg(epq.myscore) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid where epq.pracid = ? and  eu.depid= ? group by epq.userid)";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, depid);
				rs = ps.executeQuery();

			}
			if (rs.next()) {
				ep.setUsersize(rs.getInt(1));
				ep.setAvgscore(getFloat.GetFloat(rs.getFloat(2)));
			}

			float AvgNumber = getPracAvgNumber(ct, depid, pracid, sub); // ��ϰ�ܴ���
			float depAvgNumber = getPracAssignedPersonnel(ct, depid, pracid) == 0 ? 0
					: AvgNumber / getPracAssignedPersonnel(ct, depid, pracid); // ��ϰ�ܴ���/��������
			// =ƽ����ϰ�˴�
			float passreta = getPracDepPassNumber(ct, depid, pracid) == 0 ? 0
					: getPracPersonalScores(ct, depid, pracid, sub)
							/ getPracDepPassNumber(ct, depid, pracid) * 100; // �����ܳɼ�/���ż��������100
			// =
			// ���ż�����

			ep.setAvgnumber(getFloat.GetFloat(depAvgNumber)); // ƽ����ϰ�˴�
			ep.setPassreta(getFloat.GetFloat(passreta)); // ������
			ep.setTotalnumber(getPracAssignedPersonnel(ct, depid, pracid));

			ep.setPasssize(getPracScoreStep(ct, sub, pracid, depid, 60, 100));
			ep.setPass9_(getPracScoreStep(ct, sub, pracid, depid, 90, 100));
			ep.setPass8_9(getPracScoreStep(ct, sub, pracid, depid, 80, 90));
			ep.setPass7_8(getPracScoreStep(ct, sub, pracid, depid, 70, 80));
			ep.setPass6_7(getPracScoreStep(ct, sub, pracid, depid, 60, 70));
			ep.setPass_6(getPracScoreStep(ct, sub, pracid, depid, 0, 60));

		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	/**
	 * ��ϰͳ��
	 * 
	 * @param depid
	 * @param pracid
	 * @param sub
	 * @return
	 * @throws ElException
	 */
	public Examprac getexamprac_gk2(int depid, int pracid, boolean sub)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Examprac ep = new Examprac();
		try {
			ct = DBConnection.getConnection();
			if (sub) {
				ps = ct
						.prepareStatement("select lid,rid from department where id = ?");
				ps.setInt(1, depid);
				int lid = 0, rid = 0;
				rs = ps.executeQuery();
				if (rs.next()) {
					lid = rs.getInt(1);
					rid = rs.getInt(2);
				}
				rs.close();
				String sql = "select count(usize),avg(avgscore),sum(usize) from (select count( epq.userid) usize,avg(epq.myscore) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.pracid = ? and dep.lid>=? and dep.rid<=? group by epq.userid) ";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, lid);
				ps.setInt(3, rid);
				rs = ps.executeQuery();

			} else {
				String sql = "select count(usize),avg(avgscore),sum(usize) from (select count( epq.userid) usize,avg(epq.myscore) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid where epq.pracid = ? and  eu.depid= ? group by epq.userid)";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, depid);
				rs = ps.executeQuery();

			}
			int AvgNumber = 0;
			if (rs.next()) {
				ep.setUsersize(rs.getInt(1));// ��ϰ����
				ep.setAvgscorejoin(getFloat.GetFloat(rs.getFloat(2)));// ����ϰ��Աƽ���
				AvgNumber = rs.getInt(3);// ��ϰ�ܴ���
			}
			rs.close();
			ps.close();
			// ********ƽ����ϰ�˴� :��ϰ���ܴ���/����������
			// ********����ȫ����Աƽ��֣������ܳɼ����Բ���������
			// ********����ϰ��Աƽ��֣������ܳɼ���������ϰ��Ա����
			// ********����ȫ����Ա�����ʣ����ż���������Բ���������
			// ********����ϰ��Ա�����ʣ����ż��������������ϰ��Ա����
			// ********ÿ���˵ĳɼ���ƽ��ּ���
			// �����ܳɼ�(��ϰ��Աƽ���*��ϰ����)
			float depSumScore = ep.getAvgscorejoin() * ep.getUsersize();
			// ���ż�������
			int depPassCount = getPracDepPassNumber(ct, depid, pracid);
			ep.setPasssize(depPassCount);
			// ��ϰ�ܴ���
			// float depSumScore=getPracPersonalScores(ct, depid, pracid, sub);

			// float AvgNumber = getPracAvgNumber(ct, depid, pracid, sub); //
			// ��ϰ�ܴ���
			// float depAvgNumber = getPracAssignedPersonnel(ct, depid, pracid)
			// == 0 ? 0
			// : AvgNumber / getPracAssignedPersonnel(ct, depid, pracid); //
			// ��ϰ�ܴ���/��������
			// =ƽ����ϰ�˴�(��ϰ���ܴ���/����������)
			// float passreta = getPracDepPassNumber(ct, depid, pracid) == 0 ? 0
			// : depSumScore
			// / getPracDepPassNumber(ct, depid, pracid) * 100; //
			// �����ܳɼ�/���ż��������100
			// =
			// ���ż�����

			// ep.setAvgnumber(getFloat.GetFloat(depAvgNumber)); // ƽ����ϰ�˴�
			// ep.setPassreta(getFloat.GetFloat(passreta)); // ������
			// ep.setPassreta(getFloat.GetFloat(passreta)); //
			// ������(���ż���������Բ���������)
			ep.setTotalnumber(getPracAssignedPersonnel(ct, depid, pracid));
			
			// ep.setPass9_(getPracScoreStep(ct, sub, pracid, depid, 90, 100));
			// ep.setPass8_9(getPracScoreStep(ct, sub, pracid, depid, 80, 90));
			// ep.setPass7_8(getPracScoreStep(ct, sub, pracid, depid, 70, 80));
			// ep.setPass6_7(getPracScoreStep(ct, sub, pracid, depid, 60, 70));
			// ep.setPass_6(getPracScoreStep(ct, sub, pracid, depid, 0, 60));
			ep.setPass9_(getPracScoreStep(ct, sub, pracid, depid, 90, 100f));
			ep.setPass8_9(getPracScoreStep(ct, sub, pracid, depid, 80, 89.99f));
			ep.setPass7_8(getPracScoreStep(ct, sub, pracid, depid, 70, 79.99f));
			ep.setPass6_7(getPracScoreStep(ct, sub, pracid, depid, 60, 69.99f));
			ep.setPass_6(getPracScoreStep(ct, sub, pracid, depid, 0, 59.99f));

			if (ep.getTotalnumber() != 0 && ep.getUsersize() != 0) {
				// ƽ����ϰ�˴� :��ϰ���ܴ���/����������
				ep.setAvgnumber(getFloat.GetFloat(AvgNumber
						/ (ep.getTotalnumber() * 1.0f)));
				// ����ȫ����Աƽ��֣������ܳɼ����Բ���������
				ep.setAvgscore(getFloat.GetFloat(depSumScore
						/ (ep.getTotalnumber() * 1.0f)));
				// ����ȫ����Ա������(���ż���������Բ���������)
				ep.setPassreta(getFloat.GetFloat(depPassCount
						/ (ep.getTotalnumber() * 1.0f) * 100));
				// ����ϰ��Ա������(���ż��������������ϰ��Ա����)
				ep.setPassreta2(getFloat.GetFloat(depPassCount
						/ (ep.getUsersize() * 1.0f) * 100));
			}
		} catch (Exception e) {
			logger.error("��ϰͳ��ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	private int getPracPersonalScores(Connection ct, int depid, int pracid,
			boolean sub) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			if (sub) {
				ps = ct
						.prepareStatement("select lid,rid from department where id = ?");
				ps.setInt(1, depid);
				int lid = 0, rid = 0;
				rs = ps.executeQuery();
				if (rs.next()) {
					lid = rs.getInt(1);
					rid = rs.getInt(2);
				}
				rs.close();
				String sql = "select count(epq.myscore) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.pracid = ? and dep.lid>=? and dep.rid<=? group by epq.userid";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, lid);
				ps.setInt(3, rid);

			} else {
				String sql = "select count(avgscore) from( select avg(myscore) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid where epq.pracid = ? and  eu.depid= ? group by epq.userid) where avgscore  between ? and ?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, depid);

			}
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ������ϰƽ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * ��ȡ���ż�������(��ƽ���)
	 * @param ct
	 * @param depid
	 * @param pracid
	 * @return
	 * @throws Exception
	 */
	private int getPracDepPassNumber(Connection ct, int depid, int pracid)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			int lid = 0, rid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps.close();
			// String sql = "select count(eu.id) from eprac_quizinfo epq left
			// join eluser eu on eu.id = epq.userid left join department dep on
			// dep.id = eu.depid where epq.pracid = ? and dep.lid>=? and
			// dep.rid<=? and epq.myscore > 60";
			//String sql = "select count(distinct eu.id) from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.pracid = ?  and  dep.lid>=? and dep.rid<=? and epq.ispassed=1";
			String sql="select count(t1.userid) from ("+
					"select userid,avg(epq.myscore) avgScorm from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.pracid = ? and dep.lid>=? and dep.rid<=? group by epq.userid"+
					") t1,(select ep.ep_tscore*epr.passgrade/100 epPassgrade from examprac epr left join exampaper ep on epr.epid=ep.id where epr.id=?) t2 where t1.avgScorm>=epPassgrade";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pracid);
			ps.setInt(2, lid);
			ps.setInt(3, rid);
			ps.setInt(4, pracid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���ż�������(��ƽ���)ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	private int getPracAssignedPersonnel(Connection ct, int depid, int pracid)
			throws Exception {// ��ȡ�ѷ�����Ա
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			int lid = 0, rid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps.close();
			String sql = "  select count(distinct userid) from  eprac_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid left join"
					+ " examprac_assign  ea on  epq.pracid = ea.eprid  where eprid = ? and   dep.lid>=? and dep.rid<=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pracid);
			ps.setInt(2, lid);
			ps.setInt(3, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ������ϰƽ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	private int getPracAvgNumber(Connection ct, int depid, int pracid,
			boolean sub) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			int lid = 0, rid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			String sql = "select count( epq.userid) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.pracid = ? and dep.lid>=? and dep.rid<=? group by epq.userid";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pracid);
			ps.setInt(2, lid);
			ps.setInt(3, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ������ϰƽ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	/**
	 * ��ȡ��ϰ���Ծ�ķ�ֵ
	 * @param ct
	 * @param pracid
	 * @return
	 * @throws Exception
	 */
	private float getPracEpScore(Connection ct,int pracid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select ep.ep_tscore from examprac epr inner join exampaper ep on epr.epid=ep.id where epr.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pracid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ��ϰ���Ծ�ķ�ֵʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	private int getPracScoreStep(Connection ct, boolean sub, int pracid,
			int depid, int start, int stop) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			if (sub) {
				ps = ct.prepareStatement("select lid,rid from department where id = ?");
				ps.setInt(1, depid);
				int lid = 0, rid = 0;
				rs = ps.executeQuery();
				if (rs.next()) {
					lid = rs.getInt(1);
					rid = rs.getInt(2);
				}
				rs.close();
				ps.close();
				String sql = "select count(avgscore) from(select avg(myscore) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.pracid = ? and dep.lid>=? and dep.rid<=? group by epq.userid) where avgscore between ? and ?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, lid);
				ps.setInt(3, rid);
				ps.setInt(4, start);
				ps.setInt(5, stop);

			} else {
				String sql = "select count(avgscore) from( select avg(myscore) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid where epq.pracid = ? and  eu.depid= ? group by epq.userid) where avgscore  between ? and ?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, depid);
				ps.setInt(3, start);
				ps.setInt(4, stop);

			}
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	private int getPracScoreStep(Connection ct, boolean sub, int pracid,
			int depid, float start, float stop) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		//��ȡ����ϰ�Ծ���ܷ�
		float epScore=this.getPracEpScore(ct, pracid);
		//��start,stop����
		start=start/epScore*100;
		stop=stop/epScore*100;
		try {
			ct = DBConnection.getConnection();
			if (sub) {
				ps = ct.prepareStatement("select lid,rid from department where id = ?");
				ps.setInt(1, depid);
				int lid = 0, rid = 0;
				rs = ps.executeQuery();
				if (rs.next()) {
					lid = rs.getInt(1);
					rid = rs.getInt(2);
				}
				rs.close();
				ps.close();
				String sql = "select count(avgscore) from(select avg(myscore) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.pracid = ? and dep.lid>=? and dep.rid<=? group by epq.userid) where avgscore between ? and ?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, lid);
				ps.setInt(3, rid);
				ps.setFloat(4, start);
				ps.setFloat(5, stop);

			} else {
				String sql = "select count(avgscore) from( select avg(myscore) avgscore from eprac_quizinfo epq left join eluser eu on eu.id = epq.userid where epq.pracid = ? and  eu.depid= ? group by epq.userid) where avgscore  between ? and ?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, depid);
				ps.setFloat(3, start);
				ps.setFloat(4, stop);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	// public List<Department> listPracEval(int pracid, List<Department> deps1)
	// throws ElException {
	// List<Department> deps = new ArrayList<Department>();
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// StringBuffer sql = new StringBuffer("select
	// dep.id,dep.name,avg(t.avgscore) avg_,dep.lid,dep.rid from department dep
	// left join (select pqi.userid,eu.depid depid,avg(pqi.myscore) avgscore
	// from eprac_quizinfo pqi left join eluser eu on eu.id = pqi.userid ");
	// if (null != deps1) {
	// String ids = "";
	// for (int i = 0; i < deps1.size(); i++) {
	// ids = ids + deps1.get(i).getId() + ",";
	// }
	// ids = ids.length() > 0 ? ids.substring(0, ids.length() - 1)
	// : ids;
	//
	// sql.append(" where pqi.pracid = ? group by pqi.userid,eu.depid )t on
	// dep.id = t.depid where dep.id in("
	// + ids + ") group by dep.id,dep.name,dep.lid,dep.rid order by avg_
	// desc");// "where
	// // dep.id
	// // in("+ids+")
	// // and
	// // pqi.pracid
	// // = ?
	// // group
	// // by
	// // dep.id,dep.name
	// // order
	// // by
	// // tavg
	// // desc";
	// } else{
	// //������������
	// //sql.append(" where pqi.pracid = ? group by pqi.userid,eu.depid )t on
	// dep.id = t.depid where dep.parentid = 1 group by dep.id,dep.name order by
	// avg_ desc");
	// return deps;
	// }
	// ps = ct.prepareStatement(sql.toString());
	// // .prepareStatement("select dep.id,dep.name,t.avgscore from
	// // department dep left join (select pqi.userid,eu.depid
	// // depid,avg(pqi.myscore) avgscore from eprac_quizinfo pqi left join
	// // eluser eu on eu.id = pqi.userid where pqi.pracid = ? group by
	// // pqi.userid,eu.depid )t on dep.id = t.depid where dep.parentid = ?
	// // order by avgscore desc");
	// ps.setInt(1, pracid);
	// // ps.setInt(2, depid);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// Department mep = new Department(rs.getInt(1), rs.getString(2));
	// mep.setAvg(rs.getFloat(3));
	// int usercount = getPracScoreStep(ct, false, pracid,
	// mep.getId(), 0, 100);
	// mep.setUserCount(usercount);
	// int usercredit = getPracScoreStep(ct, false, pracid, mep
	// .getId(), 60, 100);
	// mep.setUserCredit(usercredit);
	// float num = 0;
	// if (usercount != 0)
	// num = (float) usercredit / usercount * 100;
	// String ratio = (int) num + "%";
	// mep.setRatiof(num);
	// mep.setRatio(ratio);
	// //��������id
	// mep.setLid(rs.getInt(4));
	// mep.setRid(rs.getInt(5));
	// deps.add(mep);
	// }
	// // ����
	// deps = this.getDepSortByRatio(deps);
	// } catch (Exception e) {
	// logger.error("��ȡ�γ̿��Գ���ʧ�ܣ�", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return deps;
	// }
	/**
	 * ��ϰͳ�Ʋ��űȽ�
	 * 
	 * @param pracid
	 * @param dep
	 * @return
	 * @throws ElException
	 */
	public Department listPracEval(int pracid, Department dep)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// Department mep =new Department();
		try {
			ct = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer(
					"select t1.avgscore,t2.nopassCount,t3.passCount from ("
							+ "select avg(pqi.myscore) avgscore from eprac_quizinfo pqi left join eluser eu on eu.id = pqi.userid left join department dep on eu.depid=dep.id where pqi.pracid = ? and dep.lid>=? and dep.rid<=?) t1,"
							+ "(select count(t4.nopassCount) nopassCount,max(t4.passScore) from "
							+ " (select pqi.userid nopassCount,avg(pqi.myscore) avgscore,max(ep.ep_tscore*epr.passgrade/100) passScore from eprac_quizinfo pqi left join examprac epr on pqi.pracid=epr.id left join exampaper ep on epr.epid=ep.id " +
									" left join eluser eu on eu.id = pqi.userid left join department dep on eu.depid=dep.id where pqi.pracid = ? and dep.lid>=? and dep.rid<=? group by pqi.userid) t4 where t4.avgscore>=t4.passScore) t2,"
							+ "(select count(distinct pqi.userid) passCount from eprac_quizinfo pqi left join eluser eu on eu.id = pqi.userid left join department dep on eu.depid=dep.id where pqi.pracid = ? and dep.lid>=? and dep.rid<=?) t3");
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < 3; i++) {
				ps.setInt(1 + i * 3, pracid);
				ps.setInt(2 + i * 3, dep.getLid());
				ps.setInt(3 + i * 3, dep.getRid());
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				// mep = new Department(dep.getId(),dep.getName());
				// mep.setLid(dep.getLid());
				// mep.setRid(dep.getRid());
				dep.setAvg(getFloat.GetFloat(rs.getFloat(1)));
				dep.setUserCredit(rs.getInt(2));
				dep.setUserCount(rs.getInt(3));
				if (dep.getUserCount() != 0) {
					// dep.setRatiof(getFloat.GetFloat((float)dep.getUserCredit()
					// / dep.getUserCount()));
					dep.setRatiof_(getFloat.GetFloat_((float) dep
							.getUserCredit()
							/ dep.getUserCount()));
					dep.setRatiof(Float.parseFloat(dep.getRatiof_()));// ������
				}
			}
			// ����
			// deps = this.getDepSortByRatio(deps);
		} catch (Exception e) {
			logger.error("��ȡ�γ̿��Գ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public ExamRoom geteroom_gk(int depid, String roomids, boolean sub)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoom ep = new ExamRoom();
		try {
			ct = DBConnection.getConnection();
			if (sub) {
				ps = ct
						.prepareStatement("select lid,rid from department where id = ?");
				ps.setInt(1, depid);
				int lid = 0, rid = 0;
				rs = ps.executeQuery();
				if (rs.next()) {
					lid = rs.getInt(1);
					rid = rs.getInt(2);
				}
				rs.close();
				String sql = "select count(usize),avg(avgscore) from (select count( epq.userid) usize,avg(epq.myscore) avgscore from study_room epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid in("+roomids+") and dep.lid>=? and dep.rid<=? group by epq.userid) ";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, lid);
				ps.setInt(2, rid);
				rs = ps.executeQuery();

			} else {
				String sql = "select count(usize),avg(avgscore) from (select count( epq.userid) usize,avg(epq.myscore) avgscore from study_room epq left join eluser eu on eu.id = epq.userid where epq.roomid in("+roomids+") and  eu.depid= ? group by epq.userid)";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, depid);
				rs = ps.executeQuery();

			}
			if (rs.next()) {
				ep.setUsersize(rs.getInt(1));
				ep.setAvgscore(getFloat.GetFloat(rs.getFloat(2)));
			}
			ep.setJoinusersize(getEroomToUserSize(ct, new Department(depid), roomids)); // ��ȡӦ��������
			// ep.setLOEusersize(getEroomQKCount(roomid, depid, ct));//
			// ��ȡȱ������������-�μӹ�����
			ep.setLOEusersize(ep.getJoinusersize() - ep.getUsersize());// ��ȡȱ������������-�μӹ�����
			float LOEusersize = ep.getLOEusersize();// �ò��Ųμӹ��Ե�����
			float joinUserSize = ep.getJoinusersize();// �ò��Ųμӹ��Ե�����
			float UserSize = ep.getUsersize() == 0 ? 1.0f : ep.getUsersize();
			ep.setPasssize(getEroomJgCount(roomids, depid, ct));// depid���Ųμӵ�������
			float Passsize = ep.getPasssize();// ��������
			float JoinAvgsCore = getEroomToAvgscore(ct, depid, roomids);// ��ȡ�μӹ��˵��ܳɼ�

			float Passgrade = joinUserSize == 0 ? 0 : (ep.getPasssize()
					/ UserSize * 100);// depid���Ųμӵ������� /�ò��Ųμӹ��Ե����� * 100
			// =�ò��ŵļ�����

			float Passgrade2 = joinUserSize == 0 ? 0
					: (Passsize / joinUserSize * 100);// depid����Ӧ�μӵ�������
			// /�ò��Ųμӹ��Ե����� * 100
			// =�ò��ŵ�Ӧ�μӿ��Եļ�����
			float Avgscorejoin = joinUserSize == 0 ? 0
					: (JoinAvgsCore / joinUserSize);

			ep.setPassgrade(Passgrade); // ��ȡ �ο���Ա������
			ep.setPassgrade2(Passgrade2); // ��ȡӦ����Ա������
			ep.setAvgscorejoin(Avgscorejoin); // ��ȡ�μ������ƽ��ɼ�

			// ep.setPass9_(getEroomScoreStep(ct, sub, roomid, depid, 90, 100));
			// ep.setPass8_9(getEroomScoreStep(ct, sub, roomid, depid, 80, 90));
			// ep.setPass7_8(getEroomScoreStep(ct, sub, roomid, depid, 70, 80));
			// ep.setPass6_7(getEroomScoreStep(ct, sub, roomid, depid, 60, 70));
			// ep.setPass_6(getEroomScoreStep(ct, sub, roomid, depid, 0, 60));
			ep.setPass9_(getEroomScoreStep(ct, sub, roomids, depid, 90, 100));
			ep
					.setPass8_9(getEroomScoreStep(ct, sub, roomids, depid, 80,
							89.99f));
			ep
					.setPass7_8(getEroomScoreStep(ct, sub, roomids, depid, 70,
							79.99f));
			ep
					.setPass6_7(getEroomScoreStep(ct, sub, roomids, depid, 60,
							69.99f));
			ep.setPass_6(getEroomScoreStep(ct, sub, roomids, depid, 0, 59.99f));

		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	/*
	 * ����ͳ��-���űȽ��еĿ�����Ϣ
	 */
	public ExamRoom geteroom_gk_2(int depid, String roomids, boolean sub)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoom ep = new ExamRoom();
		try {
			ct = DBConnection.getConnection();
			int lid = 0, rid = 0;
			ps = ct
			.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			Department d = new Department(depid);
			d.setLid(lid);
			d.setRid(rid);
			rs.close();
			if (sub) {
				
				String sql = "select count(usize),avg(avgscore) from (select count( epq.userid) usize,avg(epq.myscore) avgscore from study_room epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid in("+roomids+") and dep.lid>=? and dep.rid<=? group by epq.userid) ";
				ps = ct.prepareStatement(sql);
//				ps.setInt(1, roomid);
				ps.setInt(1, lid);
				ps.setInt(2, rid);
				rs = ps.executeQuery();

			} else {
				String sql = "select count(usize),avg(avgscore) from (select count( epq.userid) usize,avg(epq.myscore) avgscore from study_room epq left join eluser eu on eu.id = epq.userid where epq.roomid in("+roomids+") and  eu.depid= ? group by epq.userid)";
				ps = ct.prepareStatement(sql);
//				ps.setInt(1, roomid);
				ps.setInt(1, depid);
				rs = ps.executeQuery();

			}
			if (rs.next()) {
				// ͳ�Ƶ�ѧԱ����OK��ƽ���OK
				ep.setUsersize(rs.getInt(1));
				ep.setAvgscore(getFloat.GetFloat(rs.getFloat(2)));
			}
			ps.close();
			rs.close();
			// ep.setJoinusersize(getEroomToUserSize(ct, depid, roomid)); //
			// ��ȡӦ��������
			// ��ȡ�ÿ��������������Ծ���ܷ�
			float roomEpTscore = this.getRoomEpTscore(roomids);
			// ȱ������
//			ep.setLOEusersize(this.getLOEusersize(roomid, lid, rid));
			ep.setLOEusersize(this.getEroomQKCount(roomids, d, ct));
			// �ο�����(�ڿ��Ե�����)
			ep.setJoinusersize(ep.getUsersize() - ep.getLOEusersize());
			// ep.setLOEusersize(getEroomQKCount(roomid, depid, ct));//
			// ��ȡȱ������������-�μӹ�����
			// ep.setLOEusersize(ep.getJoinusersize()-ep.getUsersize());//
			// ��ȡȱ������������-�μӹ�����
			// float LOEusersize = ep.getLOEusersize();//�ò���ȱ��������
			float joinUserSize = ep.getJoinusersize();// �ò��Ųμӹ��Ե�����
			float UserSize = ep.getUsersize() == 0 ? 1.0f : ep.getUsersize();
			ep.setPasssize(getEroomJgCount(roomids, depid, ct));// depid���Ųμӿ��Լ����������
			float Passsize = ep.getPasssize();// �ο���������
			// float JoinAvgsCore = getEroomToAvgscore(ct, depid, roomid);//
			// ��ȡ�μӹ��˵��ܳɼ�

			float Passgrade = joinUserSize == 0 ? 0
					: (Passsize / joinUserSize * 100);// depid���Ųμӿ��Եļ�������
			// /�ò��Ųμӹ��Ե����� * 100
			// =�ò��ŵĲο���Ա������

			float Passgrade2 = joinUserSize == 0 ? 0
					: (Passsize / UserSize * 100);// depid����Ӧ�μӿ��Եļ�������
			// /�ò���Ӧ�òμӿ��Ե����� * 100
			// =�ò��ŵ�Ӧ����Ա������
			// float Avgscorejoin = joinUserSize == 0 ? 0 : (JoinAvgsCore /
			// joinUserSize);
			// ��ȡ�ο���Ա�ܷ�
			// float JoinAvgsCore=this.getJoinUserSumscore(roomid);
			float JoinAvgsCore = this
					.getJoinUserSumscoreByDep(roomids, lid, rid);
			float Avgscorejoin = 0;
			if (joinUserSize * roomEpTscore != 0) {
				Avgscorejoin = joinUserSize == 0 ? 0
						: (JoinAvgsCore / (joinUserSize )) ;
//						: (JoinAvgsCore / (joinUserSize * roomEpTscore)) * 100;
			}

			// float Avgscorejoin = Float.parseFloat("12");
			// ep.setPassgrade(100.33f); // ��ȡ �ο���Ա������
			ep.setPassgrade(Passgrade); // ��ȡ �ο���Ա������
			ep.setPassgrade2(Passgrade2); // ��ȡӦ����Ա������
			ep.setAvgscorejoin(Avgscorejoin); // ��ȡ�μ������ƽ��ɼ�(ѧԱ�ο�����ʵ�ʿ��Է���/�ο������ܷ���)

			// ep.setPass9_(getEroomScoreStep(ct, sub, roomid, depid, 90, 100));
			// ep.setPass8_9(getEroomScoreStep(ct, sub, roomid, depid, 80, 90));
			// ep.setPass7_8(getEroomScoreStep(ct, sub, roomid, depid, 70, 80));
			// ep.setPass6_7(getEroomScoreStep(ct, sub, roomid, depid, 60, 70));
			// ep.setPass_6(getEroomScoreStep(ct, sub, roomid, depid, 0, 60));
			ep.setPass9_(getEroomScoreStep(ct, sub, roomids, depid, 90, 100));
			ep
					.setPass8_9(getEroomScoreStep(ct, sub, roomids, depid, 80,
							89.99f));
			ep
					.setPass7_8(getEroomScoreStep(ct, sub, roomids, depid, 70,
							79.99f));
			ep
					.setPass6_7(getEroomScoreStep(ct, sub, roomids, depid, 60,
							69.99f));
			ep.setPass_6(getEroomScoreStep(ct, sub, roomids, depid, 0, 59.99f));

		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;

	}

	/**
	 * ��ȡ���������Ծ���ܷ�
	 * 
	 * @param roomid
	 * @return
	 * @throws Exception
	 */
	private float getRoomEpTscore(String roomids) throws Exception {
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		float score = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sum(ep.ep_tscore) from exampaper ep left join exam_reps erp on erp.epid=ep.id where erp.roomid in("+roomids+") and erp.pracid=0");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getFloat(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���������Ծ���ܷ�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return score;
	}

	/**
	 * ��ȡ�ο���Ա���ܷ�
	 * 
	 * @param roomid
	 * @return
	 * @throws Exception
	 */
	private float getJoinUserSumscore(int roomid) throws Exception {
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		float score = 0;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select sum(sr.myscore) from study_room
			// sr inner join study_quizinfo sqi on sr.roomid=sqi.roomid where
			// sqi.userid=sr.userid and sr.roomid=? and sqi.status!=0");
			ps = ct
					.prepareStatement("select sum(sr.myscore) from study_room sr where sr.roomid=?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getFloat(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ�ο���Ա���ܷ�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return score;
	}

	/**
	 * ��ȡ�ο���Ա���ܷ�
	 * 
	 * @param roomid
	 * @return
	 * @throws Exception
	 */
	private float getJoinUserSumscoreByDep(String roomids, int lid, int rid)
			throws Exception {
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		float score = 0;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select sum(sr.myscore) from study_room
			// sr inner join study_quizinfo sqi on sr.roomid=sqi.roomid where
			// sqi.userid=sr.userid and sr.roomid=? and sqi.status!=0");
			ps = ct
					.prepareStatement("select sum(sr.myscore) from study_room sr left join eluser eu on sr.userid=eu.id left join department dep on eu.depid=dep.id where sr.roomid  in("+roomids+") and dep.lid>=? and dep.rid<=?");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getFloat(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ�ο���Ա���ܷ�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return score;
	}

	/**
	 * ��ȡȱ������
	 * 
	 * @param ct
	 * @param depid
	 * @param roomid
	 * @return
	 * @throws Exception
	 */
	private int getLOEusersize(int roomid, int lid, int rid) throws Exception {
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select count(*) from study_quizinfo
			// where roomid=? and status=0");
			ps = ct
					.prepareStatement("select count(distinct sqi.userid) from study_quizinfo sqi left join study_room sr on sr.roomid=sqi.roomid left join eluser eu on eu.id = sr.userid left join department dep on dep.id = eu.depid where sqi.userid=sr.userid and sqi.roomid=? and sqi.status=0 and dep.lid>=? and dep.rid<=?");
			ps.setInt(1, roomid);
			ps.setInt(2, lid);
			ps.setInt(3, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡȱ������ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * Description:��ȡ����������
	 * 
	 * @Version1.0 2012-7-17 ����03:05:44 by ����˴��wenyishun110@163.com������
	 * @param ct
	 * @param depid
	 * @param roomid
	 * @return
	 * @throws Exception
	 */
	private int getEroomToUserSize(Connection ct, Department dep , String roomids)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("select lid,rid from department where id = ?");
//			ps.setInt(1, depid);
//			int lid = 0, rid = 0;
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				lid = rs.getInt(1);
//				rid = rs.getInt(2);
//			}
//			rs.close();
			String sql = "select count(tousize) from (select count( epq.userid) tousize from study_room epq join eluser eu on eu.id = epq.userid join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", dep , true)
					+ ") dep on dep.id = eu.depid where epq.roomid in ("+roomids+") group by epq.userid)";
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, roomid);
//			ps.setInt(2, lid);
//			ps.setInt(3, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	private int getEroomToAvgscore(Connection ct, int depid, String roomids)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			int lid = 0, rid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			String sql = "select avg(toavgscore) from (select avg(epq.myscore) toavgscore from study_room epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid in("+roomids+")  and dep.lid >=? and dep.rid <=? group by epq.userid)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * ����ȱ������status==0)δȱ�� Description:
	 * 
	 * @Version1.0 2012-7-17 ����02:27:07 by ����˴��wenyishun110@163.com������
	 * @param roomid
	 * @param depid
	 * @param ct
	 * @return
	 * @throws ElException
	 */
	private int getEroomQKCount(String roomids, Department dep , Connection ct)
			throws ElException {
		int s = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("select lid,rid from department where id = ?");
//			ps.setInt(1, depid);
//			int lid = 0, rid = 0;
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				lid = rs.getInt(1);
//				rid = rs.getInt(2);
//			}
//			rs.close();
			String sql = "select count(tousize) from (select count( epq.userid) tousize from study_room epq  join eluser eu on eu.id = epq.userid join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", dep , true)
					+ ")  dep on dep.id = eu.depid where epq.roomid in("+roomids+") and epq.status =0 group by epq.userid)";
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, roomid);
//			ps.setInt(2, lid);
//			ps.setInt(3, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}
	/**��ȡ����п�����ids
	 * @param erbid
	 * @param ct
	 * @return
	 * @throws ElException
	 */
	public String getEroomsByErbid(int erbid) throws ElException {
			Connection ct = null;
			StringBuffer s = new StringBuffer();
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ct = DBConnection.getConnection();
				String sql = "select roomid from erbatch_room where erbid = ? ";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, erbid);
			
				rs = ps.executeQuery();
				while (rs.next()) {
					s .append(rs.getInt(1));
					s.append(",");
				}
			} catch (Exception e) {
				logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return s.length()>0?s.substring(0,s.length()-1):null;
		}
	/**
	 * Description: ��ݹ��ֻ�ȡȱ������
	 * 
	 * @Version1.0 2012-7-18 ����03:33:55 by ����˴��wenyishun110@163.com������
	 * @param roomid
	 * @param jzid
	 * @param ct
	 * @return
	 * @throws ElException
	 */
//	private int getEroomQKCountByjzId(int roomid, int jzid, Connection ct)
//			throws ElException {
//		int s = 0;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ct = DBConnection.getConnection();
//			String sql = "select count(tousize) from (select count( epq.userid) tousize from study_room epq left join eluser eu on eu.id = epq.userid where epq.roomid = ? and eu.jingzhong=? and epq.status =0  group by epq.userid)";
//			ps = ct.prepareStatement(sql);
//			ps.setInt(1, roomid);
//			ps.setInt(2, jzid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				s = rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return s;
//	}
	/**
	 * ��ݹ��ֻ�ȡȱ������
	 * @param roomid
	 * @param jzid
	 * @param depTree
	 * @param ct
	 * @return
	 * @throws ElException
	 */
	private int getEroomQKCountByjzId(String roomids, int jzid,Department depTree, Connection ct)
	throws ElException {
		int s = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(tousize) from (select count( epq.userid) tousize from study_room epq left join eluser eu on eu.id = epq.userid " +
					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", depTree, true)+") dep on dep.id=eu.depid where epq.roomid in ("+roomids+") and eu.jingzhong=? and epq.status =0  group by epq.userid)";
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, roomid);
			ps.setInt(1, jzid);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	/**
	 * Description: ��ȡ�Ծ�ȱ������
	 * 
	 * @Version1.0 2012-7-18 ����10:22:12 by ����˴��wenyishun110@163.com������
	 * @param roomid
	 * @param depid
	 * @param ct
	 * @return
	 * @throws ElException
	 */
//	private int getEroomEpQKCount(int roomid, int epid, int depid, Connection ct)
//			throws ElException {
//		int s = 0;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("select lid,rid from department where id = ?");
//			ps.setInt(1, depid);
//			int lid = 0, rid = 0;
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				lid = rs.getInt(1);
//				rid = rs.getInt(2);
//			}
//			rs.close();
//			String sql = " select count(userid) from study_exampaper epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid = ? and epq.epid = ? and dep.lid >=? and dep.rid <=? and status =0";
//			ps = ct.prepareStatement(sql);
//			ps.setInt(1, roomid);
//			ps.setInt(2, epid);
//			ps.setInt(3, lid);
//			ps.setInt(4, rid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				s = rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return s;
//	}
	
	/**
	 * ��ȡ�Ծ�ȱ������
	 * @param roomid
	 * @param epid
	 * @param depTree
	 * @param ct
	 * @return
	 * @throws ElException
	 */
	private int getEroomEpQKCount(String roomids, int epid, ElNode depTree, Connection ct)
	throws ElException {
		int s = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = " select count(userid) from study_exampaper epq left join eluser eu on eu.id = epq.userid " +
					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", depTree, true)
					+") dep on dep.id = eu.depid where epq.roomid in("+roomids+") and epq.epid = ? and status =0";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, epid);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	/**
	 * Description: ĳ�����Ծ��ƽ��֣����Ż��ܣ�
	 * 
	 * @Version1.0 2012-7-18 ����01:12:30 by ����˴��wenyishun110@163.com������
	 * @param roomid
	 * @param epid
	 * @param depid
	 * @param ct
	 * @return
	 * @throws ElException
	 */
	private float getEroomEpsAvg(String roomids, List<ExamPaper> eps, int depid,
			Connection ct) throws ElException {
		int s = 0;
		if (eps == null || eps.size() <= 0)
			return 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			int lid = 0, rid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			String ids = "";
			for (int i = 0; i < eps.size(); i++) {
				if (i < eps.size() - 1)
					ids += eps.get(i).getId() + ",";
				else
					ids += eps.get(i).getId();
			}
			String sql = " select sum(myscore) from study_exampaper epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid in("+roomids+") and epq.epid in("
					+ ids + ") and dep.lid >=? and dep.rid <=? ";
			ps = ct.prepareStatement(sql);
			// ps.setInt(2, epid);
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	/**
	 * Description: ĳ�����Ծ���ַܷ֣����Ź��ֻ��ܣ�
	 * 
	 * @Version1.0 2012-7-18 ����01:12:30 by ����˴��wenyishun110@163.com������
	 * @param roomid
	 * @param epid
	 * @param depid
	 * @param ct
	 * @return
	 * @throws ElException
	 */
	private float getEroomEpsSumByJz(String roomids, List<ExamPaper> eps, int jz,
			Connection ct) throws ElException {
		int s = 0;
		if (eps == null || eps.size() <= 0)
			return 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String ids = "";
			for (int i = 0; i < eps.size(); i++) {
				if (i < eps.size() - 1)
					ids += eps.get(i).getId() + ",";
				else
					ids += eps.get(i).getId();
			}
			String sql = " select sum(myscore) from study_exampaper epq left join eluser eu on eu.id = epq.userid where epq.roomid in ("+roomids+") and epq.epid in("
					+ ids + ") and eu.jingzhong=? ";
			ps = ct.prepareStatement(sql);
			// ps.setInt(2, epid);
			ps.setInt(1, jz);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	/**
	 * Description:ͨ������Ӧ����Ա
	 * 
	 * @Version1.0 2012-7-18 ����03:24:25 by ����˴��wenyishun110@163.com������
	 * @param roomid
	 * @param depid
	 * @param ct
	 * @return
	 * @throws ElException
	 */
	private int getEroomYkCount(String roomids, int depid, Connection ct)
			throws ElException {
		int s = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			int lid = 0, rid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			String sql = "select count(tousize) from (select count( epq.userid) tousize from study_room epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid in("+roomids+") and dep.lid >=? and dep.rid<=? group by epq.userid)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	/**
	 * Description: ͨ������Ӧ����Ա
	 * 
	 * @Version1.0 2012-7-18 ����03:25:02 by ����˴��wenyishun110@163.com������
	 * @param roomid
	 * @param jzid
	 * @param ct
	 * @return
	 * @throws ElException
	 */
//	private int getEroomYkCountByJzId(int roomid, int jzid, Connection ct)
//			throws ElException {
//		int s = 0;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ct = DBConnection.getConnection();
//			String sql = "select count(tousize) from (select count( epq.userid) tousize from study_room epq left join eluser eu on eu.id = epq.userid where epq.roomid = ? and eu.jingzhong =? group by epq.userid)";
//			ps = ct.prepareStatement(sql);
//			ps.setInt(1, roomid);
//			ps.setInt(2, jzid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				s = rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return s;
//	}
	/**
	 * ͨ������Ӧ����Ա(�ܿɲ�����������)
	 * @param roomid
	 * @param jzid
	 * @param depTree
	 * @param ct
	 * @return
	 * @throws ElException
	 */
	private int getEroomYkCountByJzId(String roomids, int jzid,Department depTree, Connection ct)
	throws ElException {
		int s = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(tousize) from (select count( epq.userid) tousize from study_room epq left join eluser eu on eu.id = epq.userid " +
					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", depTree, true)+") dep on dep.id=eu.depid where epq.roomid in("+roomids+") and eu.jingzhong =? group by epq.userid)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, jzid);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	/**
	 * Description: ������������
	 * 
	 * @Version1.0 2012-7-18 ����01:11:05 by ����˴��wenyishun110@163.com������
	 * @param roomid
	 * @param depid
	 * @param ct
	 * @return
	 * @throws ElException
	 */
	private int getEroomJgCount(String roomids, int depid, Connection ct)
			throws ElException {
		int s = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			int lid = 0, rid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			ps.close();
			rs.close();
			String sql = "select count(tousize) from (select count( epq.userid) tousize from study_room epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid in("+roomids+") and dep .lid >=? and dep.rid<=? and ispassed = 1 group by epq.userid)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	/**
	 * Description: ��ݹ��ֻ�ȡ��������
	 * 
	 * @Version1.0 2012-7-18 ����03:35:13 by ����˴��wenyishun110@163.com������
	 * @param roomid
	 * @param jz
	 * @param ct
	 * @return
	 * @throws ElException
	 */
//	private int getEroomJgCountByJz(int roomid, int jz, Connection ct)
//			throws ElException {
//		int s = 0;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ct = DBConnection.getConnection();
//			String sql = "select count(tousize) from (select count( epq.userid) tousize from study_room epq left join eluser eu on eu.id = epq.userid where epq.roomid = ? and eu.jingzhong=? and ispassed = 1 group by epq.userid)";
//			ps = ct.prepareStatement(sql);
//			ps.setInt(1, roomid);
//			ps.setInt(2, jz);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				s = rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return s;
//	}
	/**
	 * ��ݹ��ֻ�ȡ��������
	 * @param roomid
	 * @param jz
	 * @param depTree
	 * @param ct
	 * @return
	 * @throws ElException
	 */
	private int getEroomJgCountByJz(String roomids, int jz,Department depTree, Connection ct)
	throws ElException {
		int s = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(tousize) from (select count( epq.userid) tousize from study_room epq left join eluser eu on eu.id = epq.userid " +
					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", depTree, true)+") dep on dep.id=eu.depid where epq.roomid in ("+roomids+") and eu.jingzhong=? and ispassed = 1 group by epq.userid)";
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, roomid);
			ps.setInt(1, jz);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	public List<MyExamPaper> listquiz_detail_paper_view(String roomids,
			List<Department> deps, Department dep) throws ElException {
		List<MyExamPaper> meps = listEroomExampapers(roomids,dep);
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (null != meps) {
				for (int m = 0; m < meps.size(); m++) {
					// ExamRoom ep = new ExamRoom();
					// ep.setPass9_(getEroomScoreStep(ct, true, roomid, depid,
					// 90, 100));
					// ep.setPass8_9(getEroomScoreStep(ct, true, roomid, depid,
					// 80, 90));
					// ep.setPass7_8(getEroomScoreStep(ct, true, roomid, depid,
					// 70, 80));
					// ep.setPass6_7(getEroomScoreStep(ct, true, roomid, depid,
					// 60, 70));
					// ep.setPass_6(getEroomScoreStep(ct, true, roomid, depid,
					// 0, 60));
					// ep.setUsersize(getEroomToUserSize(ct, depid, roomid));
					MyExamPaper mep = meps.get(m);
					mep.setPass9_(getEroomEpScoreStep(ct, true, roomids, meps
							.get(m).getExamPaper().getId(), dep, 90, 10000));
					mep.setPass8_9(getEroomEpScoreStep(ct, true, roomids, meps
							.get(m).getExamPaper().getId(), dep, 80, 90));
					mep.setPass7_8(getEroomEpScoreStep(ct, true, roomids, meps
							.get(m).getExamPaper().getId(), dep, 70, 80));
					mep.setPass6_7(getEroomEpScoreStep(ct, true, roomids, meps
							.get(m).getExamPaper().getId(), dep, 60, 70));
					mep.setPass_6(getEroomEpScoreStep(ct, true, roomids, meps
							.get(m).getExamPaper().getId(), dep, -10000, 60));
					//��ȡ�ο�����
					int ckSize=mep.getYksize()-mep.getQksize();
					mep.setPass_6_p(ckSize == 0 ? 0 : mep.getPass_6()
							* 1.00f / ckSize * 100);
					mep.setPass6_7_p(ckSize == 0 ? 0 : mep
							.getPass6_7()
							* 1.00f / ckSize * 100);
					mep.setPass7_8_p(ckSize == 0 ? 0 : mep
							.getPass7_8()
							* 1.00f / ckSize * 100);
					mep.setPass8_9_p(ckSize == 0 ? 0 : mep
							.getPass8_9()
							* 1.00f / ckSize * 100);
					mep.setPass9__p(ckSize == 0 ? 0 : mep.getPass9_()
							* 1.00f / ckSize * 100);
					// meps.get(m).setExamRoom(ep);
				}
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, null, null);
		}
		return meps;
	}

	public ExamRoom getQuiz_detail_paper_view(Department dep, int roomid)
			throws ElException {
		ExamRoom er = new ExamRoom();
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,title from exam_room where id = ?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setUserSize(getEroomToUserSize(ct, dep , roomid+""));
				er.setUsersize(getEroomQKCount(roomid+"", dep , ct));
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}
	public EroomBatch getQuizBatch_detail_paper_view(Department dep, int batchid)
			throws ElException {
		EroomBatch er = new EroomBatch();
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,title from erbatch where id = ?");
			ps.setInt(1, batchid);
			rs = ps.executeQuery();
			if (rs.next()) {
				er = new EroomBatch(rs.getInt(1), rs.getString(2));
				String ids = getEroomsByErbid(batchid);
				er.setUserSize(getEroomToUserSize(ct, dep , ids));
				er.setUsersize(getEroomQKCount(ids, dep , ct));
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}
	/**
	 * Description:��ȡ��������εķֲ����
	 * 
	 * @Version1.0 2012-7-18 ����09:13:58 by ����˴��wenyishun110@163.com������
	 * @param ct
	 * @param sub
	 * @param pracid
	 * @param depid
	 * @param start
	 * @param stop
	 * @return
	 * @throws Exception
	 */
	private int getEroomScoreStep(Connection ct, boolean sub, String roomids,
			int depid, float start, float stop) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sum(ep.ep_tscore) from exampaper ep left join exam_reps reps on reps.epid = ep.id where reps.roomid in("+roomids+") and reps.status =0");
			rs = ps.executeQuery();
			float t = 0f;
			if (rs.next())
				t = rs.getFloat(1);
			if (t <= 0f)
				t = 1f;
			if (sub) {
				ps.close();
				rs.close();

				ps = ct
						.prepareStatement("select lid,rid from department where id = ?");
				ps.setInt(1, depid);
				int lid = 0, rid = 0;
				rs = ps.executeQuery();
				if (rs.next()) {
					lid = rs.getInt(1);
					rid = rs.getInt(2);
				}
				ps.close();
				rs.close();
				String sql = "select count(avgscore) from(select avg(myscore)*100/"
						+ t
						+ " avgscore from study_room epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid in("+roomids+") and dep.lid>=? and dep.rid<=? group by epq.userid) where avgscore between ? and ?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, lid);
				ps.setInt(2, rid);
				ps.setFloat(3, start);
				ps.setFloat(4, stop);

			} else {
				String sql = "select count(avgscore) from( select avg(myscore)*100/"
						+ t
						+ " avgscore from study_room epq left join eluser eu on eu.id = epq.userid where epq.roomid in(roomids) and  eu.depid= ? group by epq.userid) where avgscore  between ? and ?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, depid);
				ps.setFloat(2, start);
				ps.setFloat(3, stop);

			}
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * Description: ��ȡ����ĳ�Ծ����εķֲ����
	 * 
	 * @Version1.0 2012-7-18 ����09:15:44 by ����˴��wenyishun110@163.com������
	 * @param ct
	 * @param sub
	 * @param pracid
	 * @param depid
	 * @param start
	 * @param stop
	 * @return
	 * @throws Exception
	 */
	private int getEroomEpScoreStep(Connection ct, boolean sub, String roomids,
			int epid, Department dep, int start, int stop) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ep_tscore from exampaper where id = ?");
			float epscore = 0f;
			ps.setInt(1, epid);
			rs = ps.executeQuery();
			if(rs.next()){
				epscore = rs.getFloat(1);
			}
			StringBuffer sql = new StringBuffer(
					" select count(myscore) from study_exampaper epq left join eluser eu on eu.id = epq.userid left join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", dep , true)
					+ ")  dep on dep.id = eu.depid where "
							+ " epq.epid = ? and epq.roomid in("+roomids+") and epq.status<>0 ");//������δ�μӵ�
//			if (sub) {
//				ps = ct
//						.prepareStatement("select lid,rid from department where id = ?");
//				ps.setInt(1, depid);
//				int lid = 0, rid = 0;
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					lid = rs.getInt(1);
//					rid = rs.getInt(2);
//				}
//				ps.close();
//				rs.close();
//				sql.append(" and dep.lid>=" + lid + " and dep.rid<=" + rid
//						+ " ");
//			} else {
//				sql.append(" and dep.id=" + depid + " ");
//			}
			if (start != -10000)
				sql.append(" and myscore>=" + (start/100.0f)*epscore + " ");
			if (stop != 10000)
				sql.append(" and myscore <" + (stop/100.0f)*epscore);
			ps = ct.prepareStatement(sql.toString());
			ps.setInt(1, epid);
//			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	private int getEroomScoreStep(Connection ct, boolean sub, int pracid,
			int depid, float start, float stop) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sum(ep.ep_tscore) from exampaper ep left join exam_reps reps on reps.epid = ep.id where reps.roomid = ?");
			ps.setInt(1, pracid);
			rs = ps.executeQuery();
			float t = 0f;
			if (rs.next())
				t = rs.getFloat(1);
			if (t <= 0f)
				t = 1f;
			if (sub) {
				rs.next();
				ps.close();
				ps = ct
						.prepareStatement("select lid,rid from department where id = ?");
				ps.setInt(1, depid);
				int lid = 0, rid = 0;
				rs = ps.executeQuery();
				if (rs.next()) {
					lid = rs.getInt(1);
					rid = rs.getInt(2);
				}
				ps.close();
				rs.close();
				String sql = "select count(avgscore) from(select avg(myscore)*100/"
						+ t
						+ " avgscore from study_room epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid = ? and dep.lid>=? and dep.rid<=? group by epq.userid) where avgscore between ? and ?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, lid);
				ps.setInt(3, rid);
				ps.setFloat(4, start);
				ps.setFloat(5, stop);

			} else {
				String sql = "select count(avgscore) from( select avg(myscore)*100/"
						+ t
						+ " avgscore from study_room epq left join eluser eu on eu.id = epq.userid where epq.roomid = ? and  eu.depid= ? group by epq.userid) where avgscore  between ? and ?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pracid);
				ps.setInt(2, depid);
				ps.setFloat(3, start);
				ps.setFloat(4, stop);

			}
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public List<MyExamPaper> listEroomExampapers(String roomids,ElNode depTree) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			String sql = " select ep.id,ep.title,count(sep.userid) from exampaper ep left join study_exampaper sep on ep.id = sep.epid left join eluser eu on eu.id=sep.userid inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", depTree, true)
			+") dep on dep.id=eu.depid where sep.roomid in("+roomids+") group by ep.id,ep.title ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaper mep = new ExamPaper(rs.getInt(1), rs.getString(2));
				MyExamPaper examPaper = new MyExamPaper();
//				examPaper.setExamRoom(new ExamRoom(rs.getInt(3)));
				examPaper.setYksize(rs.getInt(3));
				examPaper.setExamPaper(mep);
				//examPaper.setQksize(getEroomEpQKCount(roomid, mep.getId(), depTree.getId(),ct));
				examPaper.setQksize(getEroomEpQKCount(roomids, mep.getId(), depTree,ct));
				meps.add(examPaper);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public MyExamPaper getEroomDepExampaper(String roomids, int epid, int depid,
			List<Department> deps1) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper examPaper = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			String sql = "select avg(toavgscore) from (select avg(epq.myscore) toavgscore from study_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid in("+roomids+") and epq.epid = ? and dep.id =? group by epq.userid)";
			ps = ct.prepareStatement(sql);
			ps.setInt(2, epid);
			ps.setInt(3, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				examPaper.setAvgscore(rs.getInt(1));
			}
			if (null != deps1 && deps1.size() > 0) {
				String ids = "";
				for (int i = 0; i < deps1.size(); i++) {
					ids = ids + deps1.get(i).getId() + ",";
				}
				ids = ids.length() > 0 ? ids.substring(0, ids.length() - 1)
						: ids;
				//
				// sql = sql + "where dep.id in(" + ids
				// + ") group by dep.id,dep.name order by tavg desc";
				ct = DBConnection.getConnection();
				sql = "select rn from (select t.depid depid,rownum rn from(select dep.id depid,avg(epq.myscore) toavgscore from study_quizinfo epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid in("+roomids+") and epq.epid = ? and dep.id in ("
						+ ids
						+ ") group by dep.id order by toavgscore desc)t)t2 where t2.depid = ?";
				ps = ct.prepareStatement(sql);
				ps.setInt(2, epid);
				ps.setInt(3, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					examPaper.setMySort(rs.getInt(1));

				}
			} else
				examPaper.setMySort(1);

		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return examPaper;
	}

	/**
	 * ��ȡѧԱĳ����ƽ���
	 * 
	 * @param roomid
	 * @param epid
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public MyExamPaper getEroomDepExampaper2(String roomids, int epid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper examPaper = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			int lid = 0, rid = 0;
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			String sql = " select sum(epq.myscore) from study_exampaper epq left join eluser eu on eu.id = epq.userid left join department dep on dep.id = eu.depid where epq.roomid in("+roomids+") and epq.epid = ? and dep.lid>=? and dep.rid<=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, epid);
			// ps.setInt(3, depid);
			ps.setInt(2, lid);
			ps.setInt(3, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				examPaper.setScore(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("��ȡѧԱĳ����ƽ��ֳ��?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return examPaper;
	}

	public MyExamPaper getEroomJzExampaper2(String roomids, int epid, int jzid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper examPaper = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			String sql = " select sum(epq.myscore) from study_exampaper epq left join eluser eu on eu.id = epq.userid where epq.roomid in("+roomids+") and epq.epid = ? and eu.jingzhong=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, epid);
			// ps.setInt(3, depid);
			ps.setInt(2, jzid);
			rs = ps.executeQuery();
			if (rs.next()) {
				examPaper.setScore(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("��ȡѧԱĳ����ƽ��ֳ��?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return examPaper;
	}

	public List<Department> listEroomEval(Department depTree, String roomids,
			List<Department> deps1) throws ElException {
		List<Department> deps = new ArrayList<Department>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String sql = "select dep.id,dep.name,nvl(avg(t.avgscore),0) tavg from department dep left join (select pqi.userid,eu.depid depid,nvl(avg(pqi.myscore),0) avgscore from study_room pqi left join eluser eu on eu.id = pqi.userid where pqi.roomid in("+roomids+") group by pqi.userid,eu.depid )t on dep.id = t.depid ";
			if (null != deps1) {
				String ids = "";
				String Sids = "";

				for (int i = 0; i < deps1.size(); i++) {
					ids = ids + deps1.get(i).getId() + ",";
					Sids = Sids
							+ DepartmentLibById(depTree, deps1.get(i).getId())
							+ "-";
				}
				ids = ids.length() > 0 ? ids.substring(0, ids.length() - 1)
						: ids;

				sql = sql + "where dep.id in(" + ids
						+ ") group by dep.id,dep.name order by tavg desc";
			} else
				sql = sql
						+ "where dep.parentid = 1 group by dep.id,dep.name order by tavg desc";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, roomid);
			// ps.setInt(2, depid);
			rs = ps.executeQuery();
			List<MyExamPaper> meps = listEroomExampapers(roomids,depTree);
			while (rs.next()) {
				Department mep = new Department(rs.getInt(1), rs.getString(2));
				mep.setAvg(rs.getFloat(3));
				int usercount = getEroomYkCount(roomids, mep.getId(), ct);
				// getEroomScoreStep(ct, false, roomid, mep
				// .getId(), 0, 100);
				mep.setUserCount(usercount);
				mep.setUserCount_(getEroomQKCount(roomids, mep , ct));
				mep.setUserCount_jg(getEroomJgCount(roomids, mep.getId(), ct));
				int usercredit = mep.getUserCount_jg();
				// getEroomScoreStep(ct, false, roomid, mep
				// .getId(), 60, 100);
				// mep.setUserCredit(usercredit);
				float num = 0;
				if (usercount != 0)
					num = (float) usercredit / usercount * 100;
				// String ratio = (int) num + "%";
				String ratio = getFloat.GetFloat(num) + "%";
				mep.setRatiof(num);
				mep.setRatio(ratio);
				List<MyExamPaper> meps1 = new ArrayList<MyExamPaper>();
				if (null != meps)
					for (int i = 0; i < meps.size(); i++) {
						meps1.add(getEroomDepExampaper(roomids, meps.get(i)
								.getExamPaper().getId(), mep.getId(), deps1));
					}
				mep.setMyexampapers(meps1);
				deps.add(mep);
			}
		} catch (Exception e) {
			logger.error("��ȡ�γ̿��Գ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// ����
		deps = this.getDepSortByRatio(deps);
		// for (int i = 0; i < deps.size(); i++) {
		// }
		return deps;
	}

	/**
	 * ���Ÿſ��Ƚ�
	 * 
	 * @param roomid
	 * @param depid
	 * @param sub
	 * @return
	 * @throws ElException
	 */
	public Department listEroomEval2(String roomids, List<ExamPaper> eps,
			int depid, boolean sub) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = ((DepartmentDao)SpringContextUtil.getBean("departmentDao")).getDepById(depid);
		try {
			ct = DBConnection.getConnection();
			// StringBuffer sqls = new StringBuffer("select
			// count(usize),avg(avgscore) from (select count( epq.userid)
			// usize,avg(epq.myscore) avgscore from study_room epq left join
			// eluser eu on eu.id = epq.userid left join department dep on
			// dep.id = eu.depid where epq.roomid = ? ");
			// if (sub) {
			// ps = ct
			// .prepareStatement("select lid,rid from department where id = ?");
			// ps.setInt(1, depid);
			// int lid = 0, rid = 0;
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// lid = rs.getInt(1);
			// rid = rs.getInt(2);
			// }
			// rs.close();
			// sqls.append(" and dep.lid>="+lid+" and dep.rid<="+rid+" ");
			// } else
			// sqls.append(" and dep.id = "+depid+" ");
			// sqls.append( " group by epq.userid)");
			// ps = ct.prepareStatement(sqls.toString());
			// ps.setInt(1, roomid);
			// rs = ps.executeQuery();
			// List<MyExamPaper> meps = listEroomExampapers(roomid);
			// if (rs.next()) {
			int usercount = getEroomYkCount(roomids, dep.getId(), ct);
			dep.setUserCount(usercount);
			dep.setUserCount_(getEroomQKCount(roomids, dep , ct));
			dep.setUserCount_jg(getEroomJgCount(roomids, dep.getId(), ct));
			dep.setAvg(getFloat.GetFloat(getEroomEpsAvg(roomids, eps, depid, ct)
					/ (dep.getUserCount() == 0 ? 1 : dep.getUserCount()-dep.getUserCount_())));
			int usercredit = dep.getUserCount_jg();
			float num = 0;
			if (usercount != 0){
				//num = (float) usercredit / usercount * 100;
				num = (float) usercredit / (usercount-dep.getUserCount_()) * 100;
			}
			String ratio = getFloat.GetFloat(num) + "%";
			dep.setRatiof(num);
			dep.setRatio(ratio);
			List<MyExamPaper> meps1 = new ArrayList<MyExamPaper>();
			if (null != eps)
				for (int i = 0; i < eps.size(); i++) {
					MyExamPaper m = getEroomDepExampaper2(roomids, eps.get(i)
							.getId(), dep.getId());
					m.setAvgscore(getFloat
							.GetFloat(m.getScore()
									/ (dep.getUserCount() == 0 ? 1 : dep
											.getUserCount()-dep.getUserCount_())));
					meps1.add(m);
				}
			dep.setMyexampapers(meps1);
			// }
		} catch (Exception e) {
			logger.error("��ȡ�γ̿��Գ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public BaseDatat listEroomEval_jz(String roomids, List<ExamPaper> eps,
			int jzid,Department depTree, boolean sub) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		BaseDatat jz = new BaseDatat();
		jz.setId(jzid);
		try {
			ct = DBConnection.getConnection();
			//int usercount = getEroomYkCountByJzId(roomid, jz.getId(), ct);
			int usercount = getEroomYkCountByJzId(roomids, jz.getId(),depTree, ct);
			jz.setUserCount(usercount);//Ӧ������
			//jz.setQkuserCount(getEroomQKCountByjzId(roomid, jz.getId(), ct));//ȱ������
			jz.setQkuserCount(getEroomQKCountByjzId(roomids, jz.getId(),depTree, ct));//ȱ������
			//jz.setUserCount_jg(getEroomJgCountByJz(roomid, jz.getId(), ct));//��������
			jz.setUserCount_jg(getEroomJgCountByJz(roomids, jz.getId(),depTree, ct));//��������
			jz.setAvg(getFloat.GetFloat(getEroomEpsSumByJz(roomids, eps, jz
					.getId(), ct)
					/ (usercount == 0 ? 1 : usercount-jz.getQkuserCount())));//�ο�����ƽ���
			int usercredit = jz.getUserCount_jg();
			float num = 0;
			if (usercount != 0){
				//num = (float) usercredit / usercount * 100;
				num = (float) usercredit / (usercount-jz.getQkuserCount()) * 100;
			}
			String ratio = getFloat.GetFloat(num) + "%";
			jz.setRatio(ratio);
			List<MyExamPaper> meps1 = new ArrayList<MyExamPaper>();
			if (null != eps)
				for (int i = 0; i < eps.size(); i++) {
					MyExamPaper m = getEroomJzExampaper2(roomids, eps.get(i)
							.getId(), jz.getId());
					m.setAvgscore(getFloat.GetFloat(m.getScore()
									/ (jz.getUserCount() == 0 ? 1 : jz
											.getUserCount()-jz.getQkuserCount())));//�ο�����ƽ���
					meps1.add(m);
				}
			jz.setMyexampapers(meps1);
		} catch (Exception e) {
			logger.error("��ȡ�γ̿��Գ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return jz;
	}

	/**
	 * ��ݲ��ŵļ���������
	 * 
	 * @param deps
	 * @return
	 */
	public List<Department> getDepSortByRatio(List<Department> deps) {
		Department[] depArray = new Department[deps.size()];
		// �ȸ�ֵ
		for (int i = 0; i < depArray.length; i++) {
			depArray[i] = deps.get(i);
		}

		for (int i = 0; i < depArray.length - 1; i++) {
			for (int j = 0; j < depArray.length - 1 - i; j++) {
				if (depArray[j].getRatiof() < depArray[j + 1].getRatiof()) {
					Department temp = depArray[j];
					depArray[j] = depArray[j + 1];
					depArray[j + 1] = temp;
				}
			}
		}

		// �ٻ�ֵ
		for (int i = 0; i < deps.size(); i++) {
			deps.set(i, depArray[i]);
		}
		return deps;
	}

	public List<EroomBatch> listErbatchs(int userid,Department dep, int pageNow, int pageSize)
			throws ElException {

		List<EroomBatch> deps = new ArrayList<EroomBatch>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			int lid = 0, rid = 0;
//			ps = ct
//					.prepareStatement("select lid ,rid from erbatch_lib where id = ?");
//			ps.setInt(1, libid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				lid = rs.getInt(1);
//				rid = rs.getInt(2);
//			}
			if(userid!=-1){
					ps = ct
							.prepareStatement("select * from(select t.*,rownum rn from(select eb.id ebid,eb.title,eu.id,eu.realname " +
									"from erbatch eb left join eluser eu on eu.id = eb.creater where eb.creater = ? order by eb.id desc) t where rownum<=?) where rn >=?");
					ps.setInt(1, userid);
					ps.setInt(2, pageNow);
					ps.setInt(3, pageSize);
			}
			else{
				ps = ct
						.prepareStatement("select * from(select t.*,rownum rn from(select eb.id ebid,eb.title,eu.id,eu.realname " +
								"from erbatch eb left join eluser eu on eu.id = eb.creater order by eb.id desc) t where rownum<=?) where rn >=?");
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				EroomBatch mep = new EroomBatch(rs.getInt(1), rs.getString(2));
				String roomids = getEroomsByErbid( mep.getId()) ;
				mep.setUsersize(getEroomQKCount(roomids, dep, ct));
				mep.setUserSize(getEroomToUserSize(ct, dep,roomids));
				mep.setCreater(new ELUser(rs.getInt(3),rs.getString(4)));
				deps.add(mep);
			}
		} catch (Exception e) {
			logger.error("��ȡ�γ̿��Գ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public int listErbatchsSize(int userid,Department dep) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();
//			int lid = 0, rid = 0;
//			ps = ct
//					.prepareStatement("select lid ,rid from erbatch_lib where id = ?");
//			ps.setInt(1, libid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				lid = rs.getInt(1);
//				rid = rs.getInt(2);
//			}
			if(userid!=-1){
				ps = ct
					.prepareStatement(" select count(eb.id) from erbatch eb  where eb.creater=? ");
				ps.setInt(1, userid);
			}
			else
				ps = ct
					.prepareStatement(" select count(eb.id) from erbatch eb ");
			
			
//			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ�γ̿��Գ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	private int getErbtchScoreStep(Connection ct, int erbid, int start, int stop)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(avgscore) from( select avg(myscore) avgscore from study_room epq "
					+ "left join eluser eu on eu.id = epq.userid left join erbatch_room erbr on erbr.roomid = epq.roomid where erbr.erbid = ?  group by epq.userid)"
					+ " where avgscore  between ? and ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, erbid);
			ps.setInt(2, start);
			ps.setInt(3, stop);

			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public EroomBatch getErbatch_gk(int erbid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		EroomBatch ep = new EroomBatch();
		try {
			ct = DBConnection.getConnection();

			String sql = "select count(usize),avg(avgscore) from (select count( epq.userid) usize,avg(epq.myscore) avgscore from study_room epq left join eluser eu on eu.id = epq.userid left join erbatch_room erbr on erbr.roomid = epq.roomid where erbr.erbid = ? group by epq.userid)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, erbid);
			rs = ps.executeQuery();

			if (rs.next()) {
				ep.setUsersize(rs.getInt(1));
				ep.setAvgscore(rs.getInt(2));
			}
			ep.setPasssize(getErbtchScoreStep(ct, erbid, 60, 100));
			ep.setPass9_(getErbtchScoreStep(ct, erbid, 90, 100));
			ep.setPass8_9(getErbtchScoreStep(ct, erbid, 80, 90));
			ep.setPass7_8(getErbtchScoreStep(ct, erbid, 70, 80));
			ep.setPass6_7(getErbtchScoreStep(ct, erbid, 60, 70));
			ep.setPass_6(getErbtchScoreStep(ct, erbid, 0, 60));

		} catch (Exception e) {
			logger.error("��ȡ���Գ����б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;

	}

	public List<ExamRoom> listErbRooms(int erbid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement("select er.id erid,er.title,er.begintime,er.endtime,c.id cid,c.name cname,  count(sqi.userid),erl.id erlid,erl.name erlname from "
							+ "erbatch_room ebrr left join exam_room er on ebrr.roomid = er.id left join eroom_Lib erl on erl.id = er.erlibid left join course c on er.courseid = c.id left join ELUSER eu_c "
							+ " on er.createrid = eu_c.id left join study_room sqi on sqi.roomid = er.id  where ebrr.erbid = ? group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name");
			ps.setInt(1, erbid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er1 = new ExamRoom(rs.getInt(1), rs.getString(2));
				er1.setBegintime(rs.getTimestamp(3));
				er1.setEndtime(rs.getTimestamp(4));
				int cid = rs.getInt(5);
				Course c = null;
				if (cid != 0) {
					c = new Course(cid, rs.getString(6));
				} else
					c = new Course(0, "һ�㿼��");
				er1.setCourse(c);
				er1.setUserSize(rs.getInt(7));
				er1.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
				ers.add(er1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գ����б���?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return ers;
	}

	public List<ExamRoom> listErbRoomsPage(int erbid, int pageNow, int pageSize)
			throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement("select * from ( select t1.* ,rownum rn from(select er.id erid,er.title,er.begintime,er.endtime,c.id cid,c.name cname,  count(sqi.userid),erl.id erlid,erl.name erlname from "
							+ "erbatch_room ebrr left join exam_room er on ebrr.roomid = er.id left join eroom_Lib erl on erl.id = er.erlibid left join course c on er.courseid = c.id left join ELUSER eu_c "
							+ " on er.createrid = eu_c.id left join study_room sqi on sqi.roomid = er.id  where ebrr.erbid = ? group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name ) t1 where rownum <=? ) where rn >=?");
			ps.setInt(1, erbid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er1 = new ExamRoom(rs.getInt(1), rs.getString(2));
				er1.setBegintime(rs.getTimestamp(3));
				er1.setEndtime(rs.getTimestamp(4));
				int cid = rs.getInt(5);
				Course c = null;
				if (cid != 0) {
					c = new Course(cid, rs.getString(6));
				} else
					c = new Course(0, "һ�㿼��");
				er1.setCourse(c);
				er1.setUserSize(rs.getInt(7));
				er1.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
				ers.add(er1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գ����б���?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return ers;
	}

	public List<ExamRoom> listErbRoomsPage(int erbid) throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement("select er.id erid,er.title,er.begintime,er.endtime,c.id cid,c.name cname,  count(sqi.userid),erl.id erlid,erl.name erlname from "
							+ "erbatch_room ebrr left join exam_room er on ebrr.roomid = er.id left join eroom_Lib erl on erl.id = er.erlibid left join course c on er.courseid = c.id left join ELUSER eu_c "
							+ " on er.createrid = eu_c.id left join study_room sqi on sqi.roomid = er.id  where ebrr.erbid = ? group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name");
			ps.setInt(1, erbid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er1 = new ExamRoom(rs.getInt(1), rs.getString(2));
				er1.setBegintime(rs.getTimestamp(3));
				er1.setEndtime(rs.getTimestamp(4));
				int cid = rs.getInt(5);
				Course c = null;
				if (cid != 0) {
					c = new Course(cid, rs.getString(6));
				} else
					c = new Course(0, "һ�㿼��");
				er1.setCourse(c);
				er1.setUserSize(rs.getInt(7));
				er1.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
				ers.add(er1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գ����б���?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return ers;
	}

	public int listErbRoomsCount(int erbid) throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement("select  count(*) from(select count(er.id) erid,er.title,er.begintime,er.endtime,c.id cid,c.name cname,  count(sqi.userid),erl.id erlid,erl.name erlname from "
							+ "erbatch_room ebrr left join exam_room er on ebrr.roomid = er.id left join eroom_Lib erl on erl.id = er.erlibid left join course c on er.courseid = c.id left join ELUSER eu_c "
							+ " on er.createrid = eu_c.id left join study_room sqi on sqi.roomid = er.id  where ebrr.erbid = ? group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name)");
			ps.setInt(1, erbid);
			rs = ps.executeQuery();
			while (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գ����б���?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return s;
	}

	public List<MyBatchRoom> listErbquiz_detail_view(int bid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyBatchRoom> meps = new ArrayList<MyBatchRoom>();
		try {
			ct = DBConnection.getConnection();
			// String sql = " select eu.id,eu.realname,
			// sr.status,eu.username,sr.ispassed,sum(sqi.myScore)
			// myscore,count(sqi.id) from study_room sr left join "
			// + "study_quizinfo sqi on sqi.roomid = sr.roomid and sqi.userid =
			// sr.userid and sqi.roomid = sr.roomid "
			// + "left join ELUSER eu on sr.userid = eu.id "
			// + "where sr.roomid = ? group by eu.id,eu.realname,
			// sr.status,eu.username,sr.ispassed order by myscore desc ";
			String sql = " select  eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,count( sqi.id),erbr.roomid "
					+ "from study_room sr left join ELUSER eu on sr.userid = eu.id left join study_quizinfo sqi on sqi.roomid = sr.roomid and sqi.userid = eu.id "
					+ "left join erbatch_room erbr on erbr.roomid = sr.roomid where erbr.erbid = ? group by eu.id,eu.realname, sr.status,eu.username,sr.ispassed,sr.myscore,erbr.roomid   order by sr.myscore desc ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyBatchRoom mep = new MyBatchRoom();
				mep.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				mep.setStatus(rs.getInt(3));
				mep.getTester().setUsername(rs.getString(4));
				mep.setIspassed(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEpsize(rs.getInt(7));
				mep.setEroomBatch(new EroomBatch(rs.getInt(8)));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;

	}

	/*---->>�޸�ǰ2-23
	public List<MyBatchRoom> listErbquiz_detail_view_Page(int bid, int pageNow,
			int pageSize) throws ElException { // hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyBatchRoom> meps = new ArrayList<MyBatchRoom>();
		try {
			ct = DBConnection.getConnection();
			String sql = " select * from ( select t1.* ,rownum rn from ( select  eu.id,eu.realname ,eu.username ,sum(sr.myscore ) "
					+ "from (select * from study_room sr left join erbatch_room erbr on erbr.roomid = sr.roomid where erbr.erbid = ? ) sr left join ELUSER eu on sr.userid = eu.id "// left
					// join
					// study_quizinfo
					// sqi
					// on
					// sqi.roomid
					// =
					// sr.roomid
					// and
					// sqi.userid
					// =
					// eu.id
					+ " group by eu.id,eu.realname ,eu.username   order by sum(sr.myscore ) desc ) t1 where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			List<MyExamPaper> eps = listErbExampapers(bid);
			while (rs.next()) {
				MyBatchRoom mep = new MyBatchRoom();
				mep.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				// mep.setStatus(rs.getInt(3));
				mep.getTester().setUsername(rs.getString(3));
				// mep.setIspassed(rs.getInt(5));
				mep.setMyScore(rs.getFloat(4));
				// mep.setEpsize(rs.getInt(7));
				// mep.setEroomBatch( new EroomBatch(rs.getInt(8)));
				List<MyExamPaper> list = new ArrayList<MyExamPaper>();
				if (null != eps)
					for (int i = 0; i < eps.size(); i++) {
						list.add(getMyExamPaper(eps.get(i).getExamRoom()
								.getId(), eps.get(i).getExamPaper().getId(),
								mep.getTester().getId()));
					}
				mep.setMyExampapers(list);
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;

	}
	 */
	public List<MyRoom> listErbquiz_detail_view_Page(int bid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> meps = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();
			String sql = " select * from ( select t1.* ,rownum rn from ( select  eu.id euid,eu.realname ,eu.username,er.id erid,er.title ,sum(sr.myscore ) "
					+ "from (select sr1.* from study_room sr1 left join erbatch_room erbr on erbr.roomid = sr1.roomid where erbr.erbid = ? ) sr left join ELUSER eu on sr.userid = eu.id "// left
					+ "left join exam_room er on er.id = sr.roomid group by eu.id,eu.realname ,eu.username,er.id,er.title   order by sum(sr.myscore ) desc ) t1 where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			List<MyExamPaper> eps = listErbExampapers(bid);
			while (rs.next()) {
				MyRoom mep = new MyRoom();
				mep.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				// mep.setStatus(rs.getInt(3));
				mep.getTester().setUsername(rs.getString(3));
				// mep.setIspassed(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				// mep.setEpsize(rs.getInt(7));
				// mep.setEroomBatch( new EroomBatch(rs.getInt(8)));
				mep.setExamroom(new ExamRoom(rs.getInt(4), rs.getString(5)));
				List<MyExamPaper> list = new ArrayList<MyExamPaper>();
				if (null != eps)
					for (int i = 0; i < eps.size(); i++) {
						list.add(getMyExamPaper(eps.get(i).getExamRoom()
								.getId(), eps.get(i).getExamPaper().getId(),
								mep.getTester().getId()));
					}
				mep.setMyExamPapers(list);
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public MyExamPaper getMyExamPaper(int roomid, int epid, int userid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper mep = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			String sql = "select sqi.id sqid, sqi.myScore,sqi.endtime,sqi.status ,sqi.ispassed,eprs.id epid1,eprs.title  from study_quizinfo sqi left join exampaper eprs on eprs.id = sqi.epid where sqi.roomid= ? and sqi.epid= ? and sqi.userid = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			ps.setInt(3, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				mep = new MyExamPaper(rs.getInt(1));
				mep.setMyScore(rs.getFloat(2));
				mep.setEndtime(rs.getTimestamp(3));
				mep.setStatus(rs.getInt(4));
				mep.setIspassed(rs.getInt(5));
				mep.setExamPaper(new ExamPaper(rs.getInt(6), rs.getString(7)));
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mep;

	}

	public List<MyExamPaper> listErbExampapers(int bid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			// ---->>�޸�ǰ2-23
			String sql = " select ep.id,ep.title,er.id  from exam_room er left join erbatch_room erbr  on erbr.roomid = er.id  left join exam_reps reps on reps.roomid = er.id left join examPaper ep on ep.id = reps.epid  where erbr.erbid =? order by er.id ,ep.id asc ";
			// String sql = " select distinct(ep.id),ep.title,er.id from
			// exam_room er left join erbatch_room erbr on erbr.roomid = er.id
			// left join exam_reps reps on reps.roomid = er.id left join
			// examPaper ep on ep.id = reps.epid where erbr.erbid =? order by
			// er.id ,ep.id asc ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaper mep = new ExamPaper(rs.getInt(1), rs.getString(2));
				MyExamPaper examPaper = new MyExamPaper();
				examPaper.setExamRoom(new ExamRoom(rs.getInt(3)));
				examPaper.setExamPaper(mep);
				meps.add(examPaper);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	/*---->>�޸�ǰ2-23
	public List<MyBatchRoom> listErbquiz_detail_view_Page(int bid)
			throws ElException { // hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyBatchRoom> meps = new ArrayList<MyBatchRoom>();
		try {
			ct = DBConnection.getConnection();
			// String sql = " select eu.id,eu.realname,
			// sr.status,eu.username,sr.ispassed,sr.myscore,count(
			// sqi.id),erbr.roomid "
			// + "from study_room sr left join ELUSER eu on sr.userid = eu.id
			// left join study_quizinfo sqi on sqi.roomid = sr.roomid and
			// sqi.userid = eu.id "
			// + "left join erbatch_room erbr on erbr.roomid = sr.roomid where
			// erbr.erbid = ? group by eu.id,eu.realname,
			// sr.status,eu.username,sr.ispassed,sr.myscore,erbr.roomid order by
			// sr.myscore desc";
			String sql = "select  eu.id,eu.realname ,eu.username ,sum(sr.myscore ) "
					+ "from (select * from study_room sr left join erbatch_room erbr on erbr.roomid = sr.roomid where erbr.erbid = ? ) sr left join ELUSER eu on sr.userid = eu.id "// left
					+ " group by eu.id,eu.realname ,eu.username   order by sum(sr.myscore ) desc ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bid);
			rs = ps.executeQuery();
			List<MyExamPaper> eps = listErbExampapers(bid);
			while (rs.next()) {
				// MyBatchRoom mep = new MyBatchRoom();
				// mep.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				// mep.setStatus(rs.getInt(3));
				// mep.getTester().setUsername(rs.getString(4));
				// mep.setIspassed(rs.getInt(5));
				// mep.setMyScore(rs.getFloat(6));
				// mep.setEpsize(rs.getInt(7));
				// // mep.setEroomBatch(new EroomBatch(rs.getInt(8)));
				// meps.add(mep);
				MyBatchRoom mep = new MyBatchRoom();
				mep.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				mep.getTester().setUsername(rs.getString(3));
				mep.setMyScore(rs.getFloat(4));
				List<MyExamPaper> list = new ArrayList<MyExamPaper>();
				if (null != eps)
					for (int i = 0; i < eps.size(); i++) {
						list.add(getMyExamPaper(eps.get(i).getExamRoom()
								.getId(), eps.get(i).getExamPaper().getId(),
								mep.getTester().getId()));
					}
				mep.setMyExampapers(list);
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;

	}*/
	public List<MyRoom> listErbquiz_detail_view_Page(int bid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> meps = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();
			String sql = " select  eu.id euid,eu.realname ,eu.username,er.id erid,er.title ,sum(sr.myscore ) "
					+ "from (select sr1.* from study_room sr1 left join erbatch_room erbr on erbr.roomid = sr1.roomid where erbr.erbid = ? ) sr left join ELUSER eu on sr.userid = eu.id "// left
					+ "left join exam_room er on er.id = sr.roomid group by eu.id,eu.realname ,eu.username,er.id,er.title   order by sum(sr.myscore ) desc";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bid);
			rs = ps.executeQuery();
			List<MyExamPaper> eps = listErbExampapers(bid);
			while (rs.next()) {
				MyRoom mep = new MyRoom();
				mep.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				// mep.setStatus(rs.getInt(3));
				mep.getTester().setUsername(rs.getString(3));
				// mep.setIspassed(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				// mep.setEpsize(rs.getInt(7));
				// mep.setEroomBatch( new EroomBatch(rs.getInt(8)));
				mep.setExamroom(new ExamRoom(rs.getInt(4), rs.getString(5)));
				List<MyExamPaper> list = new ArrayList<MyExamPaper>();
				if (null != eps)
					for (int i = 0; i < eps.size(); i++) {
						list.add(getMyExamPaper(eps.get(i).getExamRoom()
								.getId(), eps.get(i).getExamPaper().getId(),
								mep.getTester().getId()));
					}
				mep.setMyExamPapers(list);
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public int listErbquiz_detail_view_Count(int bid) throws ElException { // hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();
			// ---->>�޸�ǰ2-23 String sql = " select count(*) from (select * from
			// (select * from study_room sr left join erbatch_room erbr on " +
			// "erbr.roomid = sr.roomid where erbr.erbid = ? ) sr left join
			// ELUSER eu on sr.userid = eu.id "// left
			// + " group by eu.id,erbr.id,sr.roomid) ";
			String sql = " select  count(*) from (select sr1.* from study_room sr1 left join erbatch_room erbr on erbr.roomid = sr1.roomid where erbr.erbid = ?) sr left join ELUSER eu on sr.userid = eu.id left join exam_room er on er.id = sr.roomid ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bid);
			rs = ps.executeQuery();
			while (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;

	}

	/*---->>�޸�ǰ2-23
	 * public int listErbquiz_detail_view_Count(int bid) throws ElException { // hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();
			// String sql = " select count(*) from(select eu.id,eu.realname,
			// sr.status,eu.username,sr.ispassed,sr.myscore,count(
			// sqi.id),erbr.roomid "
			// + "from study_room sr left join ELUSER eu on sr.userid = eu.id
			// left join study_quizinfo sqi on sqi.roomid = sr.roomid and
			// sqi.userid = eu.id "
			// + "left join erbatch_room erbr on erbr.roomid = sr.roomid where
			// erbr.erbid = ? group by eu.id,eu.realname,
			// sr.status,eu.username,sr.ispassed,sr.myscore,erbr.roomid order by
			// sr.myscore desc )";
			String sql = " select count(*)  from (select * from (select * from study_room sr left join erbatch_room erbr on erbr.roomid = sr.roomid where erbr.erbid = ? ) sr left join ELUSER eu on sr.userid = eu.id "// left
			// join
					// study_quizinfo
					// sqi
					// on
					// sqi.roomid
					// =
					// sr.roomid
					// and
					// sqi.userid
					// =
					// eu.id
					+ " group by eu.id)  ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bid);
			rs = ps.executeQuery();
			while (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գɼ����?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;

	}*/

	public List<Department> listErBEval(int bid, List<Department> list)
			throws ElException {
		List<Department> deps = new ArrayList<Department>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select dep.id,dep.name,nvl(avg(t.avgscore),0) tavg from department dep left join (select pqi.userid,eu.depid depid,avg(pqi.myscore) avgscore from study_room pqi left join eluser eu on eu.id = pqi.userid left join erbatch_room erbr on erbr.roomid = pqi.roomid where erbr.erbid = ? group by pqi.userid,eu.depid )t on dep.id = t.depid where ";
			if (null != list) {
				String ids = "";
				for (int i = 0; i < list.size(); i++) {
					ids = ids + list.get(i).getId() + ",";
				}
				ids = ids.length() > 0 ? ids.substring(0, ids.length() - 1)
						: ids;

				sql = sql + "dep.id in(" + ids
						+ ") group by dep.id,dep.name order by tavg desc";
			} else
				sql = sql
						+ "dep.parentid =1 group by dep.id,dep.name order by tavg desc";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bid);
			rs = ps.executeQuery();
			List<MyExamPaper> meps = listErbExampapers(bid);
			while (rs.next()) {
				Department mep = new Department(rs.getInt(1), rs.getString(2));
				mep.setAvg(rs.getFloat(3));
				int usercount = getErbtchScoreStep(ct, bid, 0, 100);
				mep.setUserCount(usercount);
				int usercredit = getErbtchScoreStep(ct, bid, 60, 100);
				mep.setUserCredit(usercredit);
				float num = 0;
				if (usercount != 0)
					num = (float) usercredit / usercount * 100;
				String ratio = (int) num + "%";
				mep.setRatiof(num);
				mep.setRatio(ratio);
				List<MyExamPaper> ms = new ArrayList<MyExamPaper>();
				if (null != meps)
					for (int i = 0; i < meps.size(); i++) {
						ms.add(getEroomDepExampaper(meps.get(i).getExamRoom()
								.getId()+"", meps.get(i).getExamPaper().getId(),
								mep.getId(), list));
					}
				mep.setMyexampapers(ms);
				deps.add(mep);
			}
			deps = this.getDepSortByRatio(deps);
		} catch (Exception e) {
			logger.error("��ȡ�γ̿��Գ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	private String createPerTypeId(EroomLib ctypeTree, int ctid) {
		if (ctypeTree.getId() != ctid) {
			ctypeTree = getEroomlibTypeById(ctypeTree.getChild(), ctid);
		}
		if (ctypeTree == null) {
			return "0";
		}
		if (ctypeTree.getChild() != null) {
			return createTypeId(ctypeTree.getChild(), ctypeTree.getId());
		}
		return String.valueOf(ctypeTree.getId());
	}

	private EroomLib getEroomlibTypeById(List<EroomLib> listType, int ctid) {
		EroomLib ctypeTree = null;
		for (EroomLib type : listType) {
			if (type.getId() != ctid) {
				ctypeTree = getEroomlibTypeById(type.getChild(), ctid);
				if (ctypeTree != null) {
					return ctypeTree;
				}
			} else {
				ctypeTree = type;
				return ctypeTree;
			}
		}
		return ctypeTree;
	}

	private String createTypeId(List<EroomLib> listType, int id) {
		String ids = id + "";
		for (EroomLib type : listType) {
			ids = ids + "," + createTypeId(type.getChild(), type.getId());
		}
		return ids;
	}

	public List<ExamRoom> listquziseach(ExamRoom er, EroomLib eroomTree,
			int role,Department dep, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			// String x = Integer.toString(er.getEroomLib().getId());
			// String ids = EroomLibById(eroomTree, er.getEroomLib().getId());
			// if (role != 1 && !ids.equals(x))// ��ɫΪ1����������Ա��ʱû�������ڵ㣬���Բ���Ҫ��ȡ
			// ,����ɫ��Ϊ1ʱids��ֻ��һ����ڵ�ʱҲ����ȡ
			// ids = er.getEroomLib().getId() == 1 ? ids.substring(
			// x.length() + 1, ids.length()) : ids; // ��id���������ʱ,�����е�id��ȥ�������id

			String title = er == null ? "" : er.getTitle() == null ? "" : er
					.getTitle().trim();
			String sql = "select * from ( select t1.* ,rownum rn from (select er.id erid,er.title,er.begintime,er.endtime,c.id cid,c.name cname,  count(sqi.userid),erl.id erlid,erl.name erlname from "
					+ "exam_room er join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("eroom_Lib", eroomTree, true)
					+ ") erl on erl.id = er.erlibid join ELUSER eu_c "
					+ " on er.createrid = eu_c.id left join study_room sqi on sqi.roomid = er.id left join course c on er.courseid = c.id  where er.title like ? ";
			// "and erl.id in ("
			// + ids + ") ";
			if (null!=er && (null != er.getBegintime() || null != er.getEndtime())) {
				int i=0;
				if(null != er.getBegintime()){
					i++;
					sql+=" and er.begintime>=? ";
				}
				if(null != er.getEndtime()){
					i++;
					sql+=" and er.endtime<=? ";
				}
				ps = ct
						.prepareStatement(sql
								+ " group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name ) t1 where rownum <=? ) where rn >=? ");
				ps.setString(1, "%" + StringUtil.toLikeStr(title) + "%");
				// ps.setInt(2, erlid);
				if(null != er.getBegintime()){
					ps.setTimestamp(2, er.getBegintime());
				}
				if(null != er.getEndtime()){
					ps.setTimestamp(1+i, er.getEndtime());
				}
				ps.setInt(2+i, pageNow);
				ps.setInt(3+i, pageSize);
			} else {
				ps = ct
						.prepareStatement(sql
								+ " group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name ) t1 where rownum <=? ) where rn >=?");
				ps.setString(1, "%" + StringUtil.toLikeStr(title) + "%");
				// ps.setInt(2, erlid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er1 = new ExamRoom(rs.getInt(1), rs.getString(2));
				er1.setBegintime(rs.getTimestamp(3));
				er1.setEndtime(rs.getTimestamp(4));
				int cid = rs.getInt(5);
				Course c = null;
				if (cid != 0) {
					c = new Course(cid, rs.getString(6));
				} else
					c = new Course(0, "һ�㿼��");
				er1.setCourse(c);
				er1.setUserSize(getEroomToUserSize(ct, dep,er1.getId()+""));
				er1.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
				er1.setUsersize(getEroomQKCount(er1.getId()+"", dep, ct));
				ers.add(er1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գ����б���?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return ers;
	}

	public int listquziseachCount(ExamRoom er, EroomLib eroomTree, int role,Department dep)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int t = 0;
		try {
			ct = DBConnection.getConnection();

			// String x = Integer.toString(er.getEroomLib().getId());
			// String ids = EroomLibById(eroomTree, er.getEroomLib().getId());
			// if (role != 1 && !ids.equals(x))// ��ɫΪ1����������Ա��ʱû�������ڵ㣬���Բ���Ҫ��ȡ
			// // ,����ɫ��Ϊ1ʱids��ֻ��һ����ڵ�ʱҲ����ȡ
			// ids = er.getEroomLib().getId() == 1 ? ids.substring(
			// x.length() + 1, ids.length()) : ids; // ��id���������ʱ,�����е�id��ȥ�������id

			String title = er == null ? "" : er.getTitle() == null ? "" : er
					.getTitle().trim();
			String sql = "select count(*) from (select er.id erid,er.title,er.begintime,er.endtime,c.id cid,c.name cname,  count(sqi.userid),erl.id erlid,erl.name erlname from "
					+ "exam_room er join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("eroom_Lib", eroomTree, true)
					+ ") erl on erl.id = er.erlibid left join course c on er.courseid = c.id left join ELUSER eu_c "
					+ " on er.createrid = eu_c.id left join study_room sqi on sqi.roomid = er.id where er.title like ? ";
			// "and erl.id in ("
			// + ids + ") ";
			if (null!=er && (null != er.getBegintime() || null != er.getEndtime())) {
				int i=0;
				if(null != er.getBegintime()){
					i++;
					sql+=" and er.begintime>=? ";
				}
				if(null != er.getEndtime()){
					i++;
					sql+=" and er.endtime<=? ";
				}
				ps = ct
						.prepareStatement(sql
								+ " group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name )");
				ps.setString(1, "%" + StringUtil.toLikeStr(title) + "%");
				// ps.setInt(2, erlid);
				if(null != er.getBegintime()){
					ps.setTimestamp(2, er.getBegintime());
				}
				if(null != er.getEndtime()){
					ps.setTimestamp(1+i, er.getEndtime());
				}
			} else {
				ps = ct
						.prepareStatement(sql
								+ " group by er.id ,er.title,er.begintime,er.endtime,c.id,c.name,erl.id,erl.name)");
				ps.setString(1, "%" + StringUtil.toLikeStr(title) + "%");
				// ps.setInt(2, erlid);
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				t = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("�鿴���ŵĿ��Գ����б���?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return t;
	}

	/**
	 * ��ѯ����ctid��ʼ����Ȩ�Ŀ���ID
	 * 
	 * @author heiweicheng
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
//	private String EroomLibById(EroomLib eroomTree, int ctid) {
//		if (eroomTree != null) {
//			if (eroomTree.getId() != ctid) {
//				eroomTree = eroomLibById(eroomTree.getChild(), ctid);
//			}
//			if (eroomTree.getChild() != null) {
//				return createEroomLibId(eroomTree.getChild(), eroomTree.getId());
//			}
//			return String.valueOf(eroomTree.getId());
//		} else {
//			return null;
//		}
//	}

	// ����
	private String DepartmentLibById(Department depTree, int ctid) {
		if (depTree != null) {
			if (depTree.getId() != ctid) {
				depTree = DepLibById(depTree.getChild(), ctid);
			}
			if (depTree.getChild() != null) {
				return createDepLibId(depTree.getChild(), depTree.getId());
			}
			return String.valueOf(depTree.getId());
		} else {
			return null;
		}
	}

	/**
	 * ������Ȩ�Ŀ���ID
	 * 
	 * @author heiweicheng
	 * @param ctypeTree
	 * @return
	 */
	private String createEroomLibId(List<EroomLib> listType, int id) {
		String ids = id + "";
		for (EroomLib type : listType) {
			ids = ids + "," + createEroomLibId(type.getChild(), type.getId());
		}
		return ids;
	}

	// ����
	private String createDepLibId(List<Department> listType, int id) {
		String ids = id + "";
		for (Department type : listType) {
			ids = ids + "," + createDepLibId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * ����Ǹ�ڵ㿪ʼ Ҫ�ҳ���ʼ�ڵ�
	 * 
	 * @author heiweicheng
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private EroomLib eroomLibById(List<EroomLib> listType, int id) {
		EroomLib eroomLib = null;
		for (EroomLib type : listType) {
			if (type.getId() != id) {
				eroomLib = eroomLibById(type.getChild(), id);
				if (eroomLib != null) {
					return eroomLib;
				}
			} else {
				eroomLib = type;
				return eroomLib;
			}
		}
		return eroomLib;
	}

	// ����
	private Department DepLibById(List<Department> listType, int id) {
		Department depLib = null;
		for (Department type : listType) {
			if (type.getId() != id) {
				depLib = DepLibById(type.getChild(), id);
				if (depLib != null) {
					return depLib;
				}
			} else {
				depLib = type;
				return depLib;
			}
		}
		return depLib;
	}
	public List<EroomBlock> listErblocks(int userid, Department dep,
			int pageNow, int pageSize) throws ElException {
		List<EroomBlock> deps = new ArrayList<EroomBlock>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if(userid!=-1){
					ps = ct
							.prepareStatement("select * from(select t.*,rownum rn from(select eb.id ebid,eb.title,eu.id euid,eu.realname,er.id erid,er.title ertitle " +
									"from erblock eb left join eluser eu on eu.id = eb.creater left join exam_room er on er.id =eb.roomid where eb.creater = ? order by eb.id desc) t where rownum<=?) where rn >=?");
					ps.setInt(1, userid);
					ps.setInt(2, pageNow);
					ps.setInt(3, pageSize);
			}
			else{
				ps = ct
						.prepareStatement("select * from(select t.*,rownum rn from(select eb.id ebid,eb.title,eu.id euid,eu.realname,er.id erid,er.title ertitle " +
								"from erblock eb left join eluser eu on eu.id = eb.creater left join exam_room er on er.id =eb.roomid order by eb.id desc) t where rownum<=?) where rn >=?");
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				EroomBlock mep = new EroomBlock(rs.getInt(1), rs.getString(2));
//				String roomids = getEroomsByErbid( mep.getId()) ;
//				mep.setUsersize(getEroomQKCount(roomids, dep, ct));
//				mep.setUserSize(getEroomToUserSize(ct, dep,roomids));
				mep.setCreater(new ELUser(rs.getInt(3),rs.getString(4)));
				mep.setEroom(new ExamRoom(rs.getInt(5),rs.getString(6)));
				deps.add(mep);
			}
		} catch (Exception e) {
			logger.error("��ȡ�γ̿��Գ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}
	public int listErblocksSize(int userid, Department dep) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();
			if(userid!=-1){
				ps = ct
					.prepareStatement(" select count(eb.id) from erblock eb  where eb.creater=? ");
				ps.setInt(1, userid);
			}
			else
				ps = ct
					.prepareStatement(" select count(eb.id) from erblock eb ");
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("��ȡ�γ̿��Գ���ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}
	
	

	public MyCourse getFinishtimeByScore(int classid,int userid,MyCourse mycourse) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		Timestamp t = null;
		float f = 0.00f;
		String sql = "";
		try {
//			ct = DBConnection.getConnection();
//			CallableStatement cs = ct.prepareCall("{call get_score_and_finishtime(?,?,?,?,?)");  
//			cs.setInt(1, classid);
//			cs.setInt(2, mycourse.getCourse().getId());
//			cs.setInt(3, userid);
//			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.FLOAT);  
//			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.TIMESTAMP);  
//			cs.execute(); 
//			System.out.println(cs.getFloat(4));
//			System.out.println(cs.getTimestamp(5));
//			f = cs.getFloat(4);
//			t = cs.getTimestamp(5);
			ct = DBConnection.getConnection();
			sql = "select a.myscore,a.endtime  from " +
                  " (select myscore,endtime from study_quizinfo where userid=? and roomid in " +
                  " (select id  from exam_room where courseid=? and classid=?  ) " +
                  " order by myscore desc,endtime desc) a " + 
                  " where rownum=1 ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, mycourse.getCourse().getId());
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				f = rs.getFloat(1);
				t = rs.getTimestamp(2);
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		if(mycourse.getMyRoom() == null){
			mycourse.setMyRoom(new MyRoom());
		}
		if(t!= null){
			mycourse.setEndtime(t);
		}
		mycourse.getMyRoom().setMyScore(f);
		return mycourse;
	}

	public MyCPage getFinishtimeByScorePage( int courseid,int userid,
			MyCPage myCPage) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		Timestamp t = null;
		float f = 0.00f;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select a.myscore,a.endtime  from " +
                  " (select myscore,endtime from study_quizinfo where userid=? " +
                  " and roomid in " +
                  " (select id   from exam_room where courseid=? and classid=0 and iscommon=0 and cpid=? ) " +
                  " order by myscore desc,endtime desc) a " + 
                  " where rownum=1 ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			ps.setInt(3, myCPage.getCpage().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				f = rs.getFloat(1);
				t = rs.getTimestamp(2);
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		myCPage.setMyscore(f);
		myCPage.setEndtime(t);
		return myCPage;
		
	}
	
	
	
	
	/**
	 * ����ѧ�ֲ�ѯ  wsj20131202
	 */
	public List<MyClass> personScoreList(int userid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyClass> myClasses = new ArrayList<MyClass>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select sc.classid,el.name, nvl(sc.bcredit,0), nvl(sc.xcredit,0), nvl(sc.tcredit,0)\n" +
						"  from study_class sc, elclass el\n" + 
						" where el.id = sc.classid(+)\n" + 
						"   and sc.userid = ?";
			ps = ct.prepareStatement(sql.toString());
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyClass myc =  new MyClass();
				ElClass elc = new ElClass();
				elc.setId(rs.getInt(1));
				elc.setName(rs.getString(2));
				myc.setElClass(elc);
				myc.setMybxCredit(rs.getInt(3));
				myc.setMyxxCredit(rs.getInt(4));
				myc.setMytCredit(rs.getInt(5));
				myClasses.add(myc);
			}
		} catch (Exception e) {
			logger.error("��ȡ��ϰͳ��������Ա�б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myClasses;
	}
	

	/**
	 * ����ѧ�ֲ�ѯ  wsj20131202
	 */
	public List<ElClass> depScoreList(ElNode tree, int sublibs) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> elClasses = new ArrayList<ElClass>();
		try {
			boolean consub = sublibs == 1 ? true : false;
			ct = DBConnection.getConnection();
			String sql = 
						"select el.depid,\n" +
						"       dep1.name,\n" + 
						"       count(el.id),\n" + 
						"       nvl(sum(sc.bcredit),0),\n" + 
						"       nvl(sum(sc.xcredit),0),\n" + 
						"       nvl(sum(sc.tcredit),0),\n" + 
						"       round(nvl(sum(sc.tcredit),0)/count(el.id),2) avgscore,\n" + 
						"       dep1.parentid   "+
						"  from eluser el, study_class sc, " +
						"    ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, consub)+") dep1 \n" + 
						" where sc.userid(+) = el.id\n" + 
						"   and el.depid=dep1.id\n" + 
						" group by el.depid, dep1.name,dep1.parentid order by avgscore desc";

			ps = ct.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass elc = new ElClass();
				Department dep = new Department();
				elc.setId(rs.getInt(1));
				dep.setName(rs.getString(2));
				elc.setDepName(rs.getString(2));
				elc.setCount(rs.getInt(3));
				elc.setBxCredit(rs.getInt(4));
				elc.setXxCredit(rs.getInt(5));
				elc.setTotalCredit(rs.getInt(6));
				elc.setAvgCredit(rs.getFloat(7));
				dep.setParentid(rs.getInt(8));
				elc.setDep(dep);
				elClasses.add(elc);
			}
		} catch (Exception e) {
			logger.error("��ȡ��ϰͳ��������Ա�б�ʧ�ܣ�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elClasses;
	}
	
}
