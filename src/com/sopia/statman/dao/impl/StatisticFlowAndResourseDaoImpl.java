package com.sopia.statman.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.StringUtil;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.statman.dao.StatisticFlowAndResourseDao;
import com.sopia.statman.entities.Flow;
import com.sopia.statman.entities.Queryobj;
import com.sopia.statman.entities.Resources;
import com.sopia.statman.entities.Statisticobj;
import com.sopia.studyman.entities.MyExamPaper;

public class StatisticFlowAndResourseDaoImpl implements StatisticFlowAndResourseDao {
	private static final Log logger=LogFactory.getLog(StatisticFlowAndResourseDaoImpl.class);
	
	/**
	 * 获取流量统计信息
	 * @return
	 * @throws ElException
	 */
	public Flow getFlowStatisticInfo(int depId) throws ElException {
		// TODO Auto-generated method stub
		Flow flow=this.getFlowStatisticLoginInfo(depId);
		flow.setRegisterUserCount(this.getRegisterUserCount(depId));
		flow.setLoginUserCount(this.getLoginUserCount(depId));
		flow.setDepartment(((DepartmentDao)SpringContextUtil.getBean("departmentDao")).getDepById(depId));
		return flow;
	}

	/**
	 * 获取流量统计登录信息
	 * @return
	 * @throws ElException
	 */
	public Flow getFlowStatisticLoginInfo(int depId) throws ElException {
		// TODO Auto-generated method stub
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Flow flow=new Flow();
		try {
			ct=DBConnection.getConnection();
			String sql_=((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLById("department", depId, true);
			ps=ct.prepareStatement("select dayInfo.dayCount,dayInfo.daySum,zhouInfo.zhouCount,zhouInfo.zhouSum,monthInfo.monthCount,monthInfo.monthSum from "+
				"(select count(userid) dayCount,sum(loginSum) daySum from("+
				"select userid,count(id) loginSum from eluserlogininfo eul left join eluser eu on eul.userid=eu.id inner join ("+sql_+") dep on eu.depid=dep.id where logintime>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') and logintime<=sysdate group by userid"+
				")) dayInfo, "+
				"(select count(userid) zhouCount,sum(loginSum) zhouSum from("+
				"select userid,count(id) loginSum from eluserlogininfo eul left join eluser eu on eul.userid=eu.id inner join ("+sql_+") dep on eu.depid=dep.id where logintime>=trunc(sysdate,'day')+1 and logintime<=sysdate group by userid"+
				")) zhouInfo,"+
				"(select count(userid) monthCount,sum(loginSum) monthSum from("+
				"select userid,count(id) loginSum from eluserlogininfo eul left join eluser eu on eul.userid=eu.id inner join ("+sql_+") dep on eu.depid=dep.id where logintime>=trunc(sysdate,'month') and logintime<=sysdate group by userid"+
				")) monthInfo");
			rs=ps.executeQuery();
			if(rs.next()){
				flow.setDayLoginUserCount(rs.getInt(1));
				flow.setDayLoginUserSum(rs.getInt(2));
				flow.setWeekLoginUserCount(rs.getInt(3));
				flow.setWeekLoginUserSum(rs.getInt(4));
				flow.setMonthLoginUserCount(rs.getInt(5));
				flow.setMonthLoginUserSum(rs.getInt(6));
			}
		} catch (Exception e) {
			logger.error("获取流量统计登录信息出错",e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flow;
	}
	/**
	 * 获取流量统计登录信息
	 * @return
	 * @throws ElException
	 */
//	public Flow getFlowStatisticLoginInfo(ElNode tree) throws ElException {
//		// TODO Auto-generated method stub
//		Connection ct=null;
//		PreparedStatement ps=null;
//		ResultSet rs=null;
//		Flow flow=new Flow();
//		try {
//			ct=DBConnection.getConnection();
//			String sql_=((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, true);
//			ps=ct.prepareStatement("select dayInfo.dayCount,dayInfo.daySum,zhouInfo.zhouCount,zhouInfo.zhouSum,monthInfo.monthCount,monthInfo.monthSum from "+
//				"(select count(userid) dayCount,sum(loginSum) daySum from("+
//				"select userid,count(id) loginSum from eluserlogininfo eul left join eluser eu on eul.userid=eu.id inner join "+sql_+" dep on eu.depid=dep.id where logintime>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') and logintime<=sysdate group by userid"+
//				")) dayInfo, "+
//				"(select count(userid) zhouCount,sum(loginSum) zhouSum from("+
//				"select userid,count(id) loginSum from eluserlogininfo eul left join eluser eu on eul.userid=eu.id inner join "+sql_+" dep on eu.depid=dep.id where logintime>=trunc(sysdate,'day')+1 and logintime<=sysdate group by userid"+
//				")) zhouInfo,"+
//				"(select count(userid) monthCount,sum(loginSum) monthSum from("+
//				"select userid,count(id) loginSum from eluserlogininfo eul left join eluser eu on eul.userid=eu.id inner join "+sql_+" dep on eu.depid=dep.id where logintime>=trunc(sysdate,'month') and logintime<=sysdate group by userid"+
//				")) monthInfo");
//			rs=ps.executeQuery();
//			if(rs.next()){
//				flow.setDayLoginUserCount(rs.getInt(1));
//				flow.setDayLoginUserSum(rs.getInt(2));
//				flow.setWeekLoginUserCount(rs.getInt(3));
//				flow.setWeekLoginUserSum(rs.getInt(4));
//				flow.setMonthLoginUserCount(rs.getInt(5));
//				flow.setMonthLoginUserSum(rs.getInt(6));
//			}
//		} catch (Exception e) {
//			logger.error("获取流量统计登录信息出错",e);
//			throw new ElException(e);
//		}finally{
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return flow;
//	}
	/**
	 * 获取注册用户的数量
	 * @return
	 * @throws ElException
	 */
	public int getRegisterUserCount(int depId) throws ElException {
		// TODO Auto-generated method stub
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ct=DBConnection.getConnection();
			String sql_=((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLById("department", depId, true);
			ps=ct.prepareStatement("select count(eu.id) from eluser eu inner join ("+sql_+") dep on eu.depid=dep.id");
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取注册用户的数量出错",e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 获取当前登录用户的数量
	 * @return
	 * @throws ElException
	 */
	public int getLoginUserCount(int depId) throws ElException {
		// TODO Auto-generated method stub
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ct=DBConnection.getConnection();
			String sql_=((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLById("department", depId, true);
			ps=ct.prepareStatement("select count(distinct(userid)) from eluserlogininfo eul left join eluser eu on eul.userid=eu.id inner join ("+sql_+") dep on eu.depid=dep.id where exittime is null");
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取当前登录用户的数量出错",e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	private String getLinkSql(String alins,String column,int depid) throws ElException{
		String sql_=" left join eluser eu on eu.id="+alins+"."+column+" inner join " +
				"("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLById("department", depid, true)+") " +
				" dep on dep.id=eu.depid";
		return sql_;
	}
	/**
	 * 资源统计
	 * @param depid 
	 * @return
	 * @throws ElException
	 */
	public Resources getResourceStatistic(int depid) throws ElException{
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Resources resources=new Resources();
		try{
			ct=DBConnection.getConnection();
			ps=ct.prepareStatement("select (select count(c.id) from course c "+this.getLinkSql("c", "creater", depid)+" ) as ccount,"+
				"(select count(cl.id) from elclass cl "+this.getLinkSql("cl", "creater", depid)+") as clcount,"+
				"(select count(q.id) from question q "+this.getLinkSql("q", "userid", depid)+") as qcount,"+
				"(select count(e.id) from exampaper e "+this.getLinkSql("e", "userid", depid)+") as ecount,"+
				"(select count(er.id) from exam_room er "+this.getLinkSql("er", "createrid", depid)+") as ercount,"+
				"(select count(k.id) from knowledge k "+this.getLinkSql("k", "userid", depid)+") as kcount,"+
				"(select count(n.id) from news n "+this.getLinkSql("n", "userid", depid)+") as ncount,"+
				"(select count(f.id) from forum f "+this.getLinkSql("f", "creater", depid)+") as fcount,"+
				"(select count(c.id) from course c "+this.getLinkSql("c", "creater", depid)+" where c.status=5) as ccount_status,"+
				"(select count(cl.id) from elclass cl "+this.getLinkSql("cl", "creater", depid)+" where cl.status=5) as clcount_status ,"+
				"(select count(er.id) from exam_room er "+this.getLinkSql("er", "createrid", depid)+" where er.valid=5) as ercount_status,"+
				"(select count(k.id) from knowledge k "+this.getLinkSql("k", "userid", depid)+" where k.valid=1) as kcount_status,"+
				"(select count(n.id) from news n "+this.getLinkSql("n", "userid", depid)+" where n.status_tow=6) as ncount_status,"+
				"(select count(f.id) from forum f "+this.getLinkSql("f", "creater", depid)+" where f.valid=1) as fcount_status,"+
				"(select count(q.id) from question q "+this.getLinkSql("q", "userid", depid)+" where q.status=0) as qcount_status,"+
				"(select count(e.id) from exampaper e "+this.getLinkSql("e", "userid", depid)+" where e.status=0) as ecount_status"+
				" from dual");
			rs=ps.executeQuery();
			if(rs.next()){
				resources.setCcount(rs.getInt(1));
				resources.setElcount(rs.getInt(2));
				resources.setQcount(rs.getInt(3));
				resources.setEcount(rs.getInt(4));
				resources.setErcount(rs.getInt(5));
				resources.setKcount(rs.getInt(6));
				resources.setNcount(rs.getInt(7));
				resources.setFcount(rs.getInt(8));
				resources.setCcount_status(rs.getInt(9));
				resources.setElcount_status(rs.getInt(10));
				resources.setErcount_status(rs.getInt(11));
				resources.setKcount_status(rs.getInt(12));
				resources.setNcount_status(rs.getInt(13));
				resources.setFcount_status(rs.getInt(14));
				resources.setQcount_status(rs.getInt(15));
				resources.setEcount_status(rs.getInt(16));
			}
			resources.setDepartment(((DepartmentDao)SpringContextUtil.getBean("departmentDao")).getDepById(depid));
		}catch(Exception e){
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return resources;
	}
	/**
	 * 统计用户登陆信息
	 * @return
	 * @throws ElException
	 */
	public Statisticobj getLoginUserInfo() throws ElException{
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Statisticobj sobj=new Statisticobj();
		try {
			ct=DBConnection.getConnection();
			ps=ct.prepareStatement("select * from ("+
				"select count(id) dayCount from (select id from eluserlogininfo where logintime>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') and logintime<=sysdate order by logintime)"+
				") t1,(select count(id) YesterdayCount from (select id from eluserlogininfo where logintime>=to_date(to_char(sysdate-1,'yyyy-mm-dd'),'yyyy-mm-dd') and logintime<=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') order by logintime)"+
				") t2,(select count(id) weekCount from (select id from eluserlogininfo where logintime>=trunc(sysdate,'day')+1 and logintime<=sysdate order by logintime)"+
				") t3");
			rs=ps.executeQuery();
			if(rs.next()){
				sobj.setDayCount(rs.getInt(1));
				sobj.setYesterdayCount(rs.getInt(2));
				sobj.setWeekCount(rs.getInt(3));
			}
			sobj.setCurrentCount(this.getLoginUserCount(1));
		} catch (Exception e) {
			logger.error("统计用户登陆信息出错",e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sobj;
	}
	/**
	 * 统计学员学习（或者考试、练习）的记录信息(例如当前在线学习人次，今天学习人次，昨天学习人次)
	 * @return
	 * @tAlias 表的别名
	 * @throws ElException
	 */
	public Statisticobj getCeRecordInfo(String tAlias) throws ElException{
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Statisticobj sobj=new Statisticobj();
		try {
			ct=DBConnection.getConnection();
			ps=ct.prepareStatement("select * from ("+
				"select count(id) dayCount from (select id from "+tAlias+" where begintime>=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') and begintime<=sysdate order by begintime)"+
				") t1,(select count(id) YesterdayCount from (select id from "+tAlias+" where begintime>=to_date(to_char(sysdate-1,'yyyy-mm-dd'),'yyyy-mm-dd') and begintime<=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') order by begintime)"+
				") t2,(select count(id) weekCount from (select id from "+tAlias+" where begintime>=trunc(sysdate,'day')+1 and begintime<=sysdate order by begintime)"+
				") t3,(select count(id) currentCount from (select id from "+tAlias+" where status=1)"+
			    ") t4");
			rs=ps.executeQuery();
			if(rs.next()){
				sobj.setDayCount(rs.getInt(1));
				sobj.setYesterdayCount(rs.getInt(2));
				sobj.setWeekCount(rs.getInt(3));
				sobj.setCurrentCount(rs.getInt(4));
			}
		} catch (Exception e) {
			logger.error("统计学员学习（或者考试、练习）的记录信息出错",e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sobj;
	}
	/**
	 * 检测参数并且拼接sql
	 * @param sb
	 * @param queryobj
	 * @param param
	 * @param tAlias 主表别名
	 * @throws ElException 
	 */
	private void checkQueryobjParam(StringBuffer sql,Queryobj queryobj,List<Object> param,String tAlias) throws ElException{
		if (queryobj != null) {
			if (queryobj.getElUser().getUsername().trim() != null
					&& !queryobj.getElUser().getUsername().trim().equals("")) {
				sql.append(" and eu.username like ?");
				param.add("%"+StringUtil.toLikeStr(queryobj.getElUser().getUsername().trim())+"%");
			}
			if (queryobj.getElUser().getRealname().trim() != null
					&& !queryobj.getElUser().getRealname().trim().equals("")) {
				sql.append(" and eu.realname like ?");
				param.add("%"+StringUtil.toLikeStr(queryobj.getElUser().getRealname().trim())+"%");
			}
			if (queryobj.getElUser().getDepartment().getId() != 0) {
				Department dep = ((DepartmentDao)SpringContextUtil.getBean("departmentDao")).getDepLRid(queryobj.getElUser().getDepartment().getId());
				sql.append(" and dep.lid>=? and dep.rid<=?");
				param.add(dep.getLid());
				param.add(dep.getRid());
			}
			if (queryobj.getMyExamPaper().getBegintime() != null) {
				sql.append(" and "+tAlias+".begintime >= ?");
				param.add(queryobj.getMyExamPaper().getBegintime());
			}
			if (queryobj.getMyExamPaper().getEndtime() != null) {
				// 还是判断开始时间
				sql.append(" and "+tAlias+".begintime <= ?");
				param.add(queryobj.getMyExamPaper().getEndtime());
			}
		}
	}
	/**
	 * 获取所有学习(考试，练习等)记录信息
	 * @param queryobj
	 * @param tAlias
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Queryobj> getAllCeRecordInfo(Queryobj queryobj,String tAlias, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Queryobj> qobjList = new ArrayList<Queryobj>();
		StringBuffer sql = new StringBuffer("");
		List<Object> param=new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			if("study_course_record".equals(tAlias)){//学习记录信息
				sql.append("select * from (select t.*,rownum rn from(select obj.id scrid,c.id cid,c.name cname,cp.id cpid,cp.title,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,obj.begintime,obj.endtime from study_course_record obj " +
						" left join course c on obj.courseid=c.id " +
						" left join course_page cp on obj.cpid=cp.id and obj.courseid=cp.courseid " +
						" left join eluser eu on obj.userid=eu.id " +
						" left join department dep on eu.depid=dep.id where 1=1");
			}else{
				//考试,练习等
				sql.append("select * from (select t.*,rownum rn from(select sqi.id sqiid,er.id erid,er.title ertitle,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,obj.begintime,obj.endtime from "+tAlias+" obj " +
						" left join study_quizinfo sqi on obj.sqid=sqi.id " +
						" left join exam_room er on er.id=sqi.roomid " +
						" left join eluser eu on eu.id=sqi.userid " +
						" left join department dep on dep.id=eu.depid where 1=1");
			}
			this.checkQueryobjParam(sql, queryobj, param, "obj");
			sql.append(" order by obj.begintime desc )t where rownum <=? ) where rn >=?");
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < param.size(); i++) {
				ps.setObject(i+1, param.get(i));
			}
			ps.setInt(param.size()+1, pageNow);
			ps.setInt(param.size()+2, pageSize);
			rs = ps.executeQuery();
			Queryobj qobj = null;
			while (rs.next()) {
				qobj = new Queryobj();
				if("study_course_record".equals(tAlias)){//学习记录信息
					qobj.setCourse(new Course(rs.getInt(2),rs.getString(3)));
					qobj.setCoursePage(new CoursePage(rs.getInt(4),rs.getString(5)));
					qobj.setElUser(new ELUser(rs.getInt(6),rs.getString(7),rs.getString(8)));
					qobj.getElUser().setDepartment(new Department(rs.getInt(9),rs.getString(10)));
					qobj.getCourse().setBegintime(new Date(rs.getTimestamp(11).getTime()));
					if(rs.getTimestamp(12)!=null){
						qobj.getCourse().setEndtime(new Date(rs.getTimestamp(12).getTime()));
					}
				}else{
					qobj.setMyExamPaper(new MyExamPaper(rs.getInt(1)));
					qobj.getMyExamPaper().setExamRoom(new ExamRoom(rs.getInt(2),rs.getString(3)));
					qobj.setElUser(new ELUser(rs.getInt(4),rs.getString(5),rs.getString(6)));
					qobj.getElUser().setDepartment(new Department(rs.getInt(7),rs.getString(8)));
					qobj.getMyExamPaper().setBegintime(rs.getTimestamp(9));
					qobj.getMyExamPaper().setEndtime(rs.getTimestamp(10));
				}
				qobjList.add(qobj);
			}
		} catch (Exception e) {
			logger.error("获取所有学习(考试，练习等)记录信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qobjList;
	}
	/**
	 * 获取所有学习(考试，练习等)记录信息数量
	 * @param queryobj
	 * @param tAlias
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getAllCeRecordInfoSize(Queryobj queryobj,String tAlias) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sql = new StringBuffer("");
		List<Object> param=new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			if("study_course_record".equals(tAlias)){//学习记录信息
				sql.append("select count(obj.id) from study_course_record obj " +
						" left join course c on obj.courseid=c.id " +
						" left join course_page cp on obj.cpid=cp.id and obj.courseid=cp.courseid " +
						" left join eluser eu on obj.userid=eu.id " +
						" left join department dep on eu.depid=dep.id where 1=1");
			}else{
				//考试,练习等
				sql.append("select count(obj.id) from "+tAlias+" obj " +
						" left join study_quizinfo sqi on obj.sqid=sqi.id " +
						" left join exam_room er on er.id=sqi.roomid " +
						" left join eluser eu on eu.id=sqi.userid " +
						" left join department dep on dep.id=eu.depid where 1=1");
			}
			this.checkQueryobjParam(sql, queryobj, param, "obj");
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < param.size(); i++) {
				ps.setObject(i+1, param.get(i));
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取所有学习(考试，练习等)记录信息数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 根据条件删除学习(考试，练习等)记录信息
	 * @param queryobj
	 * @throws ElException
	 */
	public void deleteCeRecordInfo(Queryobj queryobj) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sql = new StringBuffer("");
		List<Object> param=new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			if("study_course_record".equals(queryobj.getTableName())){//学习记录信息
				sql.append("delete from study_course_record where id in(select obj.id from study_course_record obj " +
						" left join course c on obj.courseid=c.id " +
						" left join course_page cp on obj.cpid=cp.id and obj.courseid=cp.courseid " +
						" left join eluser eu on obj.userid=eu.id " +
						" left join department dep on eu.depid=dep.id where 1=1");
			}else{
				//考试,练习等
				sql.append("delete from "+queryobj.getTableName()+" where id in(select obj.id from "+queryobj.getTableName()+" obj " +
						" left join study_quizinfo sqi on obj.sqid=sqi.id " +
						" left join exam_room er on er.id=sqi.roomid " +
						" left join eluser eu on eu.id=sqi.userid " +
						" left join department dep on dep.id=eu.depid where 1=1");
			}
			this.checkQueryobjParam(sql, queryobj, param, "obj");
			sql.append(")");
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < param.size(); i++) {
				ps.setObject(i+1, param.get(i));
			}
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("根据条件删除学习(考试，练习等)记录信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
}
