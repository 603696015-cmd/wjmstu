package com.sopia.wjm.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.wjm.dao.ClassificationDao;
import com.sopia.wjm.entities.Classification;
import com.sopia.wjm.entities.ELUserClassification;
import com.sopia.wjm.entities.QuizpaperLogInfo;

public class ClassificationDaoImpl implements ClassificationDao {
	private static final Log logger = LogFactory.getLog(ClassificationDaoImpl.class);

	public List<Classification> list_classification() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Classification> classifications = new ArrayList<Classification>();
		Classification classification = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select name,scorebegin,scoreend from classification ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				classification = new Classification(rs.getString(1),rs.getInt(2),rs.getInt(3));
				classifications.add(classification);
			}
		} catch (Exception e) {
			logger.error("获取定级列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classifications;
	}

	public void updateClassificationByName(Classification classification)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update classification set scorebegin=? ,scoreend=? where name=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classification.getScorebegin());
			ps.setInt(2, classification.getScoreend());
			ps.setString(3, classification.getName());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据name修改定级信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public int getRoomid() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int roomid = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select roomid from exam_room_sys where sys=1 ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				roomid = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取定级考场失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return roomid;
	}

	public void updateRoomid(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update exam_room_sys set roomid=? where sys=1";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改定级考场失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<ELUser> getStudents(Department searchDep, ELUser searchUser,
			int pageNow, int pageSize,String returnIds) throws ElException {
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
			if(returnIds!=null && !returnIds.equals("")){
				sqlwhere += " and eu.id in (" + returnIds + ") ";
			} 
			
			
			if(pageNow!=-1 && pageSize != -1){
				sql = "select b.*,rn from " +
				" (select a.* ,rownum rn from" +
				" (select eu.id euid,eu.realname eurealname,eu.sex eusex,d.id did,d.name dname " +
				" from eluser eu " +
				" join department d on eu.depid=d.id " + sqljoin + 
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
	
	public List<ELUser> getStudents(Department searchDep, ELUser searchUser,Map<String,Object> params,
			int pageNow, int pageSize,String returnIds) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> users = new ArrayList<ELUser>();
		ELUser user = null;
		Department dep = null;
		String sql = "";
		String sqljoin = "";
		String sqlwhere = "";
		String sqlDate = "";
		try {
			ct = DBConnection.getConnection();
			if(searchDep!=null && searchDep.getId()>0){
				sqljoin += " join (" + ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", searchDep, true) + ") dep on eu.depid = dep.id ";
			}
			if(searchUser!=null && searchUser.getRealname()!=null && !searchUser.getRealname().equals("")){
				sqlwhere += " and eu.realname like '%" + searchUser.getRealname() + "%' ";
			}
			if(returnIds!=null && !returnIds.equals("")){
				sqlwhere += " and eu.id in (" + returnIds + ") ";
			} 
			
			//日期查询
			if(params!= null && params.size()>0){
				sqlDate =" inner join (select * from ("+
						" select scr.classid,scr.userid from study_course_record scr "+ //
						" where scr.begintime  >= to_date('"+params.get("start_date")+"','yyyy-MM-dd HH24:MI:SS') and scr.begintime<=to_date('"+params.get("end_date")+"','yyyy-MM-dd HH24:MI:SS')"+
						" order by scr.begintime desc) ss"+
						" group by ss.classid,ss.userid)b on b.userid = eu.id ";
			}
			
			
			if(pageNow!=-1 && pageSize != -1){
				sql = "select b.*,rn from " +
				" (select a.* ,rownum rn from" +
				" (select eu.id euid,eu.realname eurealname,eu.sex eusex,d.id did,d.name dname " +
				" from eluser eu " +
				" join department d on eu.depid=d.id " + sqljoin + sqlDate+
				" where 1=1 "+sqlwhere+" ) a where rownum<=? ) b where rn>=?";
				System.out.println(sql);
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
	
	public int getStudentsCount(Department searchDep, ELUser searchUser,Map<String,Object> params,
			int pageNow, int pageSize,String returnIds) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqljoin = "";
		String sqlwhere = "";
		String sqlDate = "";
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			if(searchDep!=null && searchDep.getId()>0){
				sqljoin += " join (" + ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", searchDep, true) + ") dep on eu.depid = dep.id ";
			}
			if(searchUser!=null && searchUser.getRealname()!=null && !searchUser.getRealname().equals("")){
				sqlwhere += " and eu.realname like '%" + searchUser.getRealname() + "%' ";
			}
			if(returnIds!=null && !returnIds.equals("")){
				sqlwhere += " and eu.id in (" + returnIds + ") ";
			}
			
			//日期查询
			if(params!= null && params.size()>0){
				sqlDate =" inner join (select * from ("+
						" select scr.classid,scr.userid from study_course_record scr "+
						" where scr.begintime  >= to_date('"+params.get("start_date")+"','yyyy-MM-dd HH24:MI:SS') and scr.begintime<=to_date('"+params.get("end_date")+"','yyyy-MM-dd HH24:MI:SS')"+
						" order by scr.begintime desc) ss"+
						" group by ss.classid,ss.userid)b on b.userid = eu.id ";
			}
			
			
			sql = " select count(1) from eluser eu join department d on eu.depid=d.id"  + sqljoin+sqlDate + " where 1=1 " + sqlwhere;
			ps = ct.prepareStatement(sql); 
			rs = ps.executeQuery();
			if (rs.next()) {
				size =  rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("智能辅导分用户数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}


	public int getStudentsCount(Department searchDep, ELUser searchUser,
			int pageNow, int pageSize,String returnIds) throws ElException {
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
			if(returnIds!=null && !returnIds.equals("")){
				sqlwhere += " and eu.id in (" + returnIds + ") ";
			}
			sql = " select count(1) from eluser eu join department d on eu.depid=d.id"  + sqljoin + " where 1=1 " + sqlwhere;
			ps = ct.prepareStatement(sql); 
			rs = ps.executeQuery();
			if (rs.next()) {
				size =  rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("智能辅导分用户数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public List<ELUser> getPageexamInfo(Department searchDep, ELUser searchUser,
			int pageNow, int pageSize, int type) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> users = new ArrayList<ELUser>();
		ELUser user = null;
		Department dep = null;
		QuizpaperLogInfo log = null;
		Course course = null;
		MyExamPaper myExamPaper = null;
		String sql = "";
		String sqljoin = "";
		String sqlwhere = "";
		String sqlwhere1 = "";
		try {
			ct = DBConnection.getConnection();
			if(searchDep!=null && searchDep.getId()>0){
				sqljoin += " join (" + ((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", searchDep, true) + ") dep on eu.depid = dep.id ";
			}
			if(searchUser!=null && searchUser.getRealname()!=null && !searchUser.getRealname().equals("")){
				sqlwhere += " and eu.realname like '%" + searchUser.getRealname() + "%' ";
			}
			if(type == 0){
				sqlwhere1 += " and qli.pageid>0 ";
			}else{
				sqlwhere1 += " and qli.pageid=0 ";
			}
			if(pageNow!=-1 && pageSize != -1){
				sql = "select b.*,rn from " +
				" (select a.* ,rownum rn from" +
				" (select eu.id euid,eu.realname eurealname,eu.sex eusex,d.id did,d.name dname," +
				" qli.classid,qli.courseid,qli.pageid,qli.myexampaperid,qli.begintime,qli.endtime,qli.passtime,qli.score," +
				" c.id cid,c.name cname," +
				" sqi.id sqiid " +
				" from eluser eu" +
				" join department d on eu.depid=d.id " + sqljoin + 
				" join quizpaper_log_info qli on eu.id=qli.userid " +
				" join course c on c.id=qli.courseid" + 
				" left join study_quizinfo sqi on qli.myexampaperid=sqi.id " + 
				" where 1=1 "+sqlwhere+" "+sqlwhere1+" ) a where rownum<=? ) b where rn>=?";
				ps = ct.prepareStatement(sql); 
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}else{
				sql = "select eu.id euid,eu.realname eurealname,eu.sex eusex,d.id did,d.name dname, " +
				" qli.classid,qli.courseid,qli.pageid,qli.myexampaperid,qli.begintime,qli.endtime,qli.passtime,qli.score," +
				" c.id cid,c.name cname " +
				" ,sqi.id sqiid " +
				" from eluser eu " +
				" join department d on eu.depid=d.id " + sqljoin + 
				" join quizpaper_log_info qli on eu.id=qli.userid  " + 
				" join course c on c.id=qli.courseid " + 
				" left join study_quizinfo sqi on qli.myexampaperid=sqi.id " +
				" where 1=1 " + sqlwhere1;
				ps = ct.prepareStatement(sql); 
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				dep = new Department(rs.getInt(4),rs.getString(5));
				log = new QuizpaperLogInfo(rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getTimestamp(10),rs.getTimestamp(11),rs.getInt(12),rs.getFloat(13));
				course = new Course(rs.getInt(14),rs.getString(15));
				myExamPaper = new MyExamPaper(rs.getInt(16));
				course.setMyExamPaper(myExamPaper);
				user = new ELUser(rs.getInt(1));
				user.setRealname(rs.getString(2));
				user.setSex(rs.getString(3));
				user.setDepartment(dep);
				user.setLog(log);
				user.setCourse(course);
				users.add(user);
			}
		} catch (Exception e) {
			logger.error("章节或者课程考试统计列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return users;
	}

	public int getPageexamInfoCount(Department searchDep, ELUser searchUser,
			int pageNow, int pageSize, int type) throws ElException {
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
			if(type == 0){
				sqlwhere += " and qli.pageid>0";
			}else{
				sqlwhere += " and qli.pageid=0";
			}
			sql = " select count(1) from eluser eu join department d on eu.depid=d.id"  + sqljoin + 
					" join quizpaper_log_info qli on eu.id=qli.userid  " + 
					" join course c on c.id=qli.courseid " + 
					" left join study_quizinfo sqi on qli.myexampaperid=sqi.id " +
					" where 1=1 " + sqlwhere;
			ps = ct.prepareStatement(sql); 
			rs = ps.executeQuery();
			if (rs.next()) {
				size =  rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("章节或者考试统计size数量！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public void updateElUserClassificationByUserid(int userid,int roomid,String classificationname,int time)
			throws ElException {
//		PreparedStatement ps = null;
//		Connection ct = null;
//		ResultSet rs = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("{call set_eluserclassification(?,?,?)}");
//			ps.setInt(1, userid);
//			ps.setInt(2, roomid);
//			ps.setString(3, classificationname);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update eluser_classification set type=1,time=?,status=0,classificationname=? " +
					" where userid=? and roomid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, time);
			ps.setString(2, classificationname);
			ps.setInt(3, userid);
			ps.setInt(4, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改定级信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public ELUserClassification getElUserClassificationByUserid(int userid,int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		ELUserClassification elUserClassification = null;
		try {
			ct = DBConnection.getConnection();
			sql = " select userid,roomid,classificationname,type,time,status from eluser_classification where userid=? and roomid=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				elUserClassification = new ELUserClassification();
				elUserClassification.setUserid(rs.getInt(1));
				elUserClassification.setRoomid(rs.getInt(2));
				elUserClassification.setName(rs.getString(3));
				elUserClassification.setType(rs.getInt(4));
				elUserClassification.setTime(rs.getInt(5));
				elUserClassification.setStatus(rs.getInt(6));
			}
		} catch (Exception e) {
			logger.error("获取用户定级记录出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUserClassification;
	}

	public void addExceptionData(int userid, int roomid,int time) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		String sql = "";
//		try {
//			ct = DBConnection.getConnection();
//			sql = "insert into eluser_classification (userid,roomid,classificationname,type,time,status) " +
//					" values (?,?,?,?,?)";
//			ps = ct.prepareStatement(sql);
//			ps.setInt(1, userid);
//			ps.setInt(2, roomid);
//			ps.setString(3, "1A");
//			ps.setInt(4, 0);
//			ps.setInt(5, 0);
//			ps.setInt(6, -1);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("定级前插入一条异常信息失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call set_eluserclassification_excp(?,?,?)}");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.setInt(3, time);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void checkUserIsAssignToErbatch(int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call assignerbatchuserfenpei(?)}");
			ps.setInt(1, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public float getErbatchProcess(int erbatchid, int userid)
			throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		String sql = "";
//		float process = 0.00f;
//		try {
//			ct = DBConnection.getConnection();
//			sql = "select process from erbatch_user_fenpei where batchid=? and userid=? ";
//			ps = ct.prepareStatement(sql);
//			ps.setInt(1, erbatchid);
//			ps.setInt(2, userid);
//			rs = ps.executeQuery();
//			if(rs.next()){
//				process = rs.getFloat(1);
//			}
//		} catch (Exception e) {
//			logger.error("获取考试批次进度失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return process;
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		float process = 0.00f;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call geterbatchprocess(?,?,?)}");  
			cs.setInt(1, erbatchid);
			cs.setInt(2, userid);
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			System.out.println(cs.getFloat(3));
			process = cs.getFloat(3);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return process;
	}

	public List<ExamRoom> listEroomsByErbatchid(int erbatchid, int userid , int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		ExamRoom er = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from " +
					" (select a.*,rownum rn from " +
					" (select er.id erid,er.title ertitle ,sr.myscore srmyscore,sr.ispassed srispassed " +
					" from " +
					" erbatch_room ebr " +
					" left join exam_room er on ebr.roomid=er.id " +
					" left join study_room sr on er.id=sr.roomid " +
					" where ebr.erbid=? and sr.userid=? ) a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, erbatchid);
			ps.setInt(2, userid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setScore(rs.getFloat(3));
				er.setIsPassed(rs.getInt(4));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public int listEroomsSizeByErbatchid(int erbatchid , int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) " +
			" from " +
			" erbatch_room ebr " +
			" left join exam_room er on ebr.roomid=er.id " +
			" left join study_room sr on er.id=sr.roomid " +
			" where ebr.erbid=? and sr.userid=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, erbatchid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public void addPinyinClass(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call addpinyinclass(?,?)}");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	
	//wjm0212修改
	public void addExceptionData_new(int userid, int roomid,int time) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into eluser_classification (userid,roomid,classificationname,type,time,status) " +
					" values (?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
//			ps.setString(3, "6A");
			ps.setString(3, "4A");
			ps.setInt(4, 1);
			ps.setInt(5, 1);
			ps.setInt(6, -1);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("定级前插入一条异常信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	//判断是否已顶级
	public boolean isDingji(int userid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int count=0;
		boolean flag=false;
		try {
			ct = DBConnection.getConnection();
			sql = " select count(1) from study_class where userid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
				if(count>0){
					flag=true;
				}
			}
		} catch (Exception e) {
			logger.error("定级前插入一条异常信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return flag;
	}


}
