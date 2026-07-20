package com.sopia.intelligentTutoringPoints.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.OracleBlob;
import com.sopia.common.StringUtil;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.intelligentTutoringPoints.dao.IntelligentTutoringPointsDao;
import com.sopia.intelligentTutoringPoints.entities.IntelligentAcademic;
import com.sopia.intelligentTutoringPoints.entities.IntelligentAcademicCourse;
import com.sopia.intelligentTutoringPoints.entities.IntelligentClass;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLearnWeek;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLogin;
import com.sopia.intelligentTutoringPoints.entities.IntelligentProportion;
import com.sopia.intelligentTutoringPoints.entities.IntelligentRecoding;
import com.sopia.intelligentTutoringPoints.entities.IntelligentTutoringPoints;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.questionman.entities.ExamPaper;

public class IntelligentTutoringPointsDaoImpl implements
		IntelligentTutoringPointsDao {
	private static final Log logger = LogFactory.getLog(IntelligentTutoringPointsDaoImpl.class);

	public float getPoints(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		float points = 0.0f;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call intelligent_points(?,?,?)}");  
			cs.setInt(1, userid);
			cs.setInt(2, classid);
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			System.out.println(cs.getDouble(3));
			System.out.println(cs.getFloat(3));
			points = cs.getFloat(3);
		} catch (Exception e) {
			logger.error("获取用户当前等级智能辅导分失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return points;
	}

	public int intelligentCount(Department searchDep, ELUser searchUser,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqljoin = "";
		String sqlwhere = "";
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			if(searchDep!=null && searchDep.getId()>0){
				sqljoin += " join (" + ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", searchDep, true) + ") dep on eu.depid = dep.id ";
			}
			if(searchUser!=null && searchUser.getRealname()!=null && !searchUser.getRealname().equals("")){
				sqlwhere += " and eu.realname like '%" + searchUser.getRealname() + "%' ";
			}
			sql = " select count(1) from eluser eu join department d on eu.depid=d.id"  + sqljoin + " where 1=1 " + sqlwhere;
			ps = ct.prepareStatement(sql); 
			rs = ps.executeQuery();
			if (rs.next()) {
				size =  rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("智能辅导分用户数量数量！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public List<ELUser> intelligentUsers(Department searchDep, ELUser searchUser,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> users = new ArrayList<ELUser>();
		ELUser user = null;
		Department dep = null;
		String sql = "";
		String sqljoin = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			if(searchDep!=null && searchDep.getId()>0){
				sqljoin += " join (" + ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", searchDep, true) + ") dep on eu.depid = dep.id ";
			}
			if(searchUser!=null && searchUser.getRealname()!=null && !searchUser.getRealname().equals("")){
				sqlwhere += " and eu.realname like '%" + searchUser.getRealname() + "%' ";
			}
			if(pageNow!=-1 && pageSize != -1){
				sql = "select b.*,rn from " +
				" (select a.* ,rownum rn from" +
				" (select eu.id euid,eu.realname eurealname,eu.sex eusex,d.id did,d.name dname " +
				" from eluser eu join department d on eu.depid=d.id " + sqljoin + 
				" where 1=1 "+sqlwhere+" ) a where rownum<=? ) b where rn>=?";
				ps = ct.prepareStatement(sql); 
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}else{
				sql = "select eu.id euid,eu.realname eurealname,eu.sex eusex,d.id did,d.name dname " +
				" from eluser eu,department d " +
				" where eu.depid=d.id ";
				ps = ct.prepareStatement(sql); 
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				dep = new Department(rs.getInt(4),rs.getString(5));
				user = new ELUser(rs.getInt(1));
				user.setRealname(rs.getString(2));
				user.setSex(rs.getString(3));
				user.setDepartment(dep);
				users.add(user);
			}
		} catch (Exception e) {
			logger.error("智能辅导分用户列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return users;
	}

	public IntelligentTutoringPoints getDifferentPoints(int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		IntelligentTutoringPoints userPoint = null;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call intelligent_different_points(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");  
			cs.setInt(1, userid);
			cs.setInt(2, classid);
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(6, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(7, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(8, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(9, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(10, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(11, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(12, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(13, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(14, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			System.out.println(cs.getFloat(3));
			userPoint = new IntelligentTutoringPoints();
			userPoint.setUserid(userid);
			userPoint.setClassid(classid);
			userPoint.setTotalScore(cs.getFloat(3));//总分
			userPoint.setScoreLogin(cs.getFloat(4));//登录得分
			userPoint.setScoreWeek(cs.getFloat(5));//周学习时间得分
			userPoint.setScoreClass(cs.getFloat(6));//总学习时间得分
			userPoint.setScoreProportion(cs.getFloat(7));//复听得分
			userPoint.setScoreRecoding(cs.getFloat(8));//录音得分
			userPoint.setScoreAcademic(cs.getFloat(9));//章节考试得分
			userPoint.setScoreAcademicCourse(cs.getFloat(10));//课程考试得分
			userPoint.setScoreProportionQ(cs.getFloat(11));//复听数量得分
			userPoint.setScoreProportionT(cs.getFloat(12));//复听次数得分
			userPoint.setScoreRecodingQ(cs.getFloat(13));//录音数量得分
			userPoint.setScoreRecodingT(cs.getFloat(14));//录音次数得分
		} catch (Exception e) {
			logger.error("获取用户各项智能辅导分失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userPoint;
	}

	public List<IntelligentLogin> getLoginInfos(int userid, int classid,int pageNow,int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<IntelligentLogin> loginInfos = new ArrayList<IntelligentLogin>();
		ELUser elUser = null;
		Department department = null;
		IntelligentLogin login = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			//20140522增加开始时间排序
			sql = "select b.*,rn from " +
			" (select a.* ,rownum rn from" +
			" (select i.userid iuserid,i.begintime ibegintime,i.endtime iendtime,i.logintime ilogintime,i.today itoday,i.score iscore,i.classid iclassid," +
			"	e.id eid,e.realname erealname,e.username eusername, " +
			"	d.id did ,d.name dname" +
			"	from intelligent_login i " +
			"	left join eluser e on i.userid=e.id " +
			"	left join department d on e.depid=d.id" +
			"	where userid=? and classid=?  order by i.begintime desc) a where rownum<=? ) b where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				login = new IntelligentLogin(rs.getInt(1),rs.getTimestamp(2),rs.getTimestamp(3),rs.getInt(4),rs.getString(5),rs.getDouble(6),rs.getInt(7));
				department = new Department(rs.getInt(11),rs.getString(12));
				elUser = new ELUser(rs.getInt(8),rs.getString(9));
				elUser.setUsername(rs.getString(10));
				elUser.setDepartment(department);
				login.setElUser(elUser);
				loginInfos.add(login);
			}
		} catch (Exception e) {
			logger.error("登录详情出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return loginInfos;
	}

	public List<IntelligentLearnWeek> getWeekInfos(int userid, int classid,int pageNow,int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<IntelligentLearnWeek> weekInfos = new ArrayList<IntelligentLearnWeek>();
		IntelligentLearnWeek week = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
//			sql = "select b.*,rn from " +
//			" (select a.* ,rownum rn from" +
//			" (select userid,totalscore,classid,courseid,pageid,weekbegin,weekend,learntime from intelligent_week_t where userid=? and classid=? order by weekbegin asc ) a where rownum<=? ) b where rn>=?";
			sql = "select b.*,rn from " +
			" (select a.* ,rownum rn from" +
			" (select sum(learntime),sum(totalscore),weekbegin,weekend from intelligent_week_t where userid=? and classid=? group by weekbegin,weekend order by weekbegin asc ) a where rownum<=? ) b where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				week = new IntelligentLearnWeek();
//				week = new IntelligentLearnWeek(rs.getInt(1),rs.getDouble(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getString(7),rs.getInt(8));
				week.setLearnTime(rs.getInt(1));
				week.setTotalScore(rs.getDouble(2));
				week.setWeekBegin(rs.getString(3));
				week.setWeekEnd(rs.getString(4));
				weekInfos.add(week);
			}
		} catch (Exception e) {
			logger.error("周学习时间详情出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return weekInfos;
	}

	public List<IntelligentClass> getclassInfos(int userid, int classid,int pageNow,int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<IntelligentClass> classInfos = new ArrayList<IntelligentClass>();
		IntelligentClass clazz = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from " +
			" (select a.* ,rownum rn from" +
			" (select userid,totalscore,totalsecond,classid,courseid,pageid from intelligent_class_t where userid=? and classid=? ) a where rownum<=? ) b where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				clazz = new IntelligentClass(rs.getInt(1),rs.getDouble(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6));
				
				classInfos.add(clazz);
			}
		} catch (Exception e) {
			logger.error("等级学习时间详情出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classInfos;
	}

	public int getLoginInfosCount(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from intelligent_login where userid=? and classid=? ";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("登录详情COUNT出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public int getWeekInfosCount(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from (select * from intelligent_week_t where userid=? and classid=? group by weekbegin,weekend order by weekbegin asc)";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("周学习时间详情COUNT出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public int getclassInfosCount(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from intelligent_class_t where userid=? and classid=? ";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("等级学习时间详情COUNT出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public int getLoginInfosDays(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from (select distinct(today) from intelligent_login where userid=? and classid=? ) ";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询登录天数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public float getClassInfoHour(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		float hour = 0.0f;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select hour from intelligent_class_t_t where userid=? and classid=?  ";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				hour = rs.getFloat(1);
			}
		} catch (Exception e) {
			logger.error("获取等级学习时长HOUR出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return hour;
	}

	public IntelligentProportion getProportion(int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		IntelligentProportion propertion = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select totalcount,proportionqcount,proportionqprocess,proportiontcount,proportiontprocess,totalscore,qscore,tscore from intelligent_proportion_t  where userid=? and classid=?  ";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				propertion = new IntelligentProportion(rs.getInt(1),rs.getInt(2),rs.getFloat(3),rs.getInt(4),rs.getFloat(5),rs.getDouble(6),rs.getDouble(7),rs.getDouble(8));
			}
		} catch (Exception e) {
			logger.error("复听智能辅导分出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return propertion;
	}

	public IntelligentRecoding getRecoding(int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		IntelligentRecoding recoding = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select totalcount,recodingqcount,recodingqprocess,recodingtcount,recodingtprocess,totalscore,qscore,tscore from intelligent_recoding_t  where userid=? and classid=?  ";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				recoding = new IntelligentRecoding(rs.getInt(1),rs.getInt(2),rs.getFloat(3),rs.getInt(4),rs.getFloat(5),rs.getDouble(6),rs.getDouble(7),rs.getDouble(8));
			}
		} catch (Exception e) {
			logger.error("录音智能辅导分出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return recoding;
	}

	public List<IntelligentAcademicCourse> getAcademicCourseInfos(int userid,
			int classid, int pageNow, int pageSize, Course course,
			CoursePage coursePage) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<IntelligentAcademicCourse> academicCourseInfos = new ArrayList<IntelligentAcademicCourse>();
		IntelligentAcademicCourse info = null;
		ElClass elClass = null;
		Course c = null;
		ExamRoom examRoom = null;
		ExamPaper examPaper = null;
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			if(course!=null && course.getId()>0){
				sqlwhere = " and t.courseid=" + course.getId() + " ";
			}
			sql = "select b.*, rn from " +
			"	(select a.*, rownum rn from" +
			"	(select t.userid,t.classid,t.courseid,t.totalscore," +
			"	e.id eid ,e.name ename ," +
			"	c.id cid, c.name cname ," +
			"	er.id erid ," +
			"	erp.epid erpepid " +
			"	from intelligent_academic_cou_t_t_t t " +
			"	left join elclass e on t.classid=e.id " +
			"	left join course c on t.courseid=c.id " +
			"	left join exam_room er on t.courseid=er.courseid and er.classid=? and cpid=0 " +
			"	left join exam_reps erp on erp.roomid=er.id " +
			"   left join study_room sr on er.id=sr.roomid  " +
			"	where t.userid=? and t.classid=? and sr.userid=? "+sqlwhere+" ) a " +
			"	where rownum<=? ) b" +
			"	where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.setInt(3, classid);
			ps.setInt(4, userid);
			ps.setInt(5, pageNow);
			ps.setInt(6, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				elClass = new ElClass(rs.getInt(5),rs.getString(6));
				c = new Course(rs.getInt(7),rs.getString(8));
				examPaper = new ExamPaper(rs.getInt(10));
				examRoom = new ExamRoom(rs.getInt(9));
				examRoom.setExamPaper(examPaper);
				c.setExamRoom(examRoom);
				info = new IntelligentAcademicCourse(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDouble(4));
				info.setElClass(elClass);
				info.setCourse(c);
				academicCourseInfos.add(info);
			}
		} catch (Exception e) {
			logger.error("单元考试详情出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return academicCourseInfos;
	}

	public int getAcademicCourseInfosCount(int userid, int classid,
			Course course, CoursePage coursePage) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			if(course!=null && course.getId()>0){
				sqlwhere = " and t.courseid=" + course.getId() + " ";
			}
			sql = "select count(1) " +
			"	from intelligent_academic_cou_t_t_t t " +
			"	left join elclass e on t.classid=e.id " +
			"	left join course c on t.courseid=c.id " +
			"	left join exam_room er on t.courseid=er.courseid and er.classid=? and cpid=0 " +
			"	left join exam_reps erp on erp.roomid=er.id " +
			"   left join study_room sr on er.id=sr.roomid  " +
			"	where t.userid=? and t.classid=? and sr.userid=? " + sqlwhere;
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.setInt(3, classid);
			ps.setInt(4, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("单元考试详情COUNT出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public List<IntelligentAcademic> getAcademicInfos(int userid, int classid,
			int pageNow, int pageSize, Course course, CoursePage coursePage)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<IntelligentAcademic> academicInfos = new ArrayList<IntelligentAcademic>();
		IntelligentAcademic info = null;
		ElClass elClass = null;
		Course c = null;
		CoursePage cPage = null;
		ExamRoom examRoom = null;
		ExamPaper examPaper = null;
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			if(course!=null && course.getId()>0){
				sqlwhere = " and t.courseid=" + course.getId() + " ";
			}
			if(coursePage!=null && coursePage.getId()>0){
				sqlwhere = " and t.pageid=" + coursePage.getId() + " ";
			}
			sql = "select b.*, rn from " +
			"	(select a.*, rownum rn from " +
			"	(select t.userid,t.classid,t.courseid,t.pageid,t.totalscore," +
			"	e.id eid ,e.name ename ," +
			"	c.id cid, c.name cname ," +
			"	cp.id cpid,cp.title cptitle ," +
			"	er.id erid ," +
			"	erp.epid erpepid " +
			"	from intelligent_academic_t_t_t t " +
			"	left join elclass e on t.classid=e.id " +
			"	left join course c on t.courseid=c.id " +
			"	left join course_page cp on t.pageid=cp.id " +
			"	left join exam_room er on t.pageid=er.cpid " +
			"	left join exam_reps erp on erp.roomid=er.id " +
			"   left join study_room sr on er.id=sr.roomid " + 
			"	where t.userid=? and t.classid=? and sr.userid=? "+sqlwhere+" ) a " +
			"	where rownum<=? ) b" +
			"	where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, userid);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				elClass = new ElClass(rs.getInt(6),rs.getString(7));
				c = new Course(rs.getInt(8),rs.getString(9));
				cPage = new CoursePage(rs.getInt(10),rs.getString(11));
				examPaper = new ExamPaper(rs.getInt(13));
				examRoom = new ExamRoom(rs.getInt(12));
				examRoom.setExamPaper(examPaper);
				cPage.setExamRoom(examRoom);
				info = new IntelligentAcademic(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDouble(5));
				info.setElClass(elClass);
				info.setCourse(c);
				info.setCoursePage(cPage);
				academicInfos.add(info);
			}
		} catch (Exception e) {
			logger.error("模块考试详情出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return academicInfos;
	}

	public int getAcademicInfosCount(int userid, int classid, Course course,
			CoursePage coursePage) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			if(course!=null && course.getId()>0){
				sqlwhere = " and t.courseid=" + course.getId() + " ";
			}
			if(coursePage!=null && coursePage.getId()>0){
				sqlwhere = " and t.pageid=" + coursePage.getId() + " ";
			}
			sql = "select count(1) " +
			"	from intelligent_academic_t_t_t t " +
			"	left join elclass e on t.classid=e.id " +
			"	left join course c on t.courseid=c.id " +
			"	left join course_page cp on t.pageid=cp.id " +
			"	left join exam_room er on t.pageid=er.cpid " +
			"	left join exam_reps erp on erp.roomid=er.id " +
			"   left join study_room sr on er.id=sr.roomid " + 
			"	where t.userid=? and t.classid=? and sr.userid=? " + sqlwhere;
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("模块考试详情COUNT出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	
	/**
	 * 返回当前传入得classid的name
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public String getElClssName(int classid) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		String  classname="";
		try {
			ct = DBConnection.getConnection();
			String sql = "select name from elclass where id=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				classname = rs.getString(1);
			}
		} catch (Exception e) {
			logger.error("模块考试详情COUNT出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classname;
	}
	
	
	/**
	 * 获取用户各项智能辅导分(将等级合为一个查看)
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public IntelligentTutoringPoints getDifferentPoints_new(int userid,int classid,String classname) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		IntelligentTutoringPoints userPoint = null;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call intelligent_different_points_1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");  
			cs.setInt(1, userid);
			cs.setInt(2, classid);
			cs.setString(15, classname);
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(6, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(7, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(8, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(9, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(10, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(11, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(12, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(13, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(14, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			System.out.println(cs.getFloat(3));
			userPoint = new IntelligentTutoringPoints();
			userPoint.setUserid(userid);
			userPoint.setClassid(classid);
			userPoint.setTotalScore(cs.getFloat(3));//总分
			userPoint.setScoreLogin(cs.getFloat(4));//登录得分
			userPoint.setScoreWeek(cs.getFloat(5));//周学习时间得分
			userPoint.setScoreClass(cs.getFloat(6));//总学习时间得分
			userPoint.setScoreProportion(cs.getFloat(7));//复听得分
			userPoint.setScoreRecoding(cs.getFloat(8));//录音得分
			userPoint.setScoreAcademic(cs.getFloat(9));//章节考试得分
			userPoint.setScoreAcademicCourse(cs.getFloat(10));//课程考试得分
			userPoint.setScoreProportionQ(cs.getFloat(11));//复听数量得分
			userPoint.setScoreProportionT(cs.getFloat(12));//复听次数得分
			userPoint.setScoreRecodingQ(cs.getFloat(13));//录音数量得分
			userPoint.setScoreRecodingT(cs.getFloat(14));//录音次数得分
		} catch (Exception e) {
			logger.error("获取用户各项智能辅导分失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userPoint;
	}
	
	/**
	 * 获得ab两个等级培训班
	 * @param classname
	 * @return
	 * @throws ElException
	 */
	public List<String> getElClssList(String classname) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<String> elClassNames = new ArrayList<String>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select id from  elclass where name like'%"+classname+"%' order by id ";
			ps = ct.prepareStatement(sql); 
		
			rs = ps.executeQuery();
			while (rs.next()) {
				elClassNames.add(rs.getString(1));
			}
		} catch (Exception e) {
			logger.error("模块考试详情出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elClassNames;
	}

}
