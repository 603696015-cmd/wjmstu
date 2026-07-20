package com.sopia.statman.dao.impl;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.statman.dao.StatisticscoreDao;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;

public class StatisticscoreDaoImpl  implements  StatisticscoreDao{
	private static final Log logger = LogFactory
	.getLog(StatisticCourseDaoImpl.class);

	public List<ELUser> studentscoer(ElNode tree, 
			int sublibs,ELUser elUser,int pageNow, int pageSize) throws ElException {
//		btotalscore
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser>  lu= new  ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			String  str="";
				if(elUser!=null&&elUser.getRealname()!=null){
					str+= "  and t.elrealname  like  '%"+elUser.getRealname()+"%' ";
				}
				if(elUser!=null&&elUser.getSex()!=null&&!"".equals(elUser.getSex())){
					
					str+=" and t.elsex like '"+elUser.getSex()+"' ";
				}
//			String sql = "select * from ( select t1.*,rownum rn from ( select t.elid telid,t.elrealname telrealname,t.eldepid teldepid,dept.name deptname," +
//					" sum(t.mycredit) summycredit,t.age tage from (select el.id elid ,el.realname elrealname,el.depid eldepid,sum(cs.credit) as mycredit ," +
//					" nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(el.shengri,'yyyy')),-1)  as age from eluser el " +
//					" left join  study_course  stc on stc.userid=el.id and stc.classid=0  left join  exam_room exr on " +
//					" exr.courseid=stc.courseid  and exr.classid=stc.classid  left join study_room str on " +
//					" str.roomid=exr.id and str.userid=el.id and str.ispassed=1 left join course cs on " +
//					" cs.id=stc.courseid  "+str+" group by  el.id,el.realname,el.depid ,el.shengri " +
//					" union all " +
//					" select  el1.id elid1 ,el1.realname elrealname1,el1.depid eldepid1,sum(clc1.setcredit)  as mycredit," +
//					" nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(el1.shengri,'yyyy')),-1)  as age from eluser el1 " +
//					" left join   study_course  stc1 on el1.id=stc1.userid  and stc1.passed=1  left join " +
//					" class_course clc1 on clc1.courseid=stc1.courseid and clc1.classid=stc1.classid and " +
//					" clc1.getcredit=1   "+str+" group by el1.id,el1.realname,el1.depid,el1.shengri" +
//					" union all " +
//					" select el.id elid ,el.realname elrealname,el.depid eldepid,sum(cs.setcredit)  as mycredit  ," +
//					" nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(el.shengri,'yyyy')),-1)  as age from   eluser el  " +
//					" left join   study_room  str   on str.userid=el.id  left join  class_course  cs   on " +
//					" str.roomid = cs.eroomid  and str.ispassed=1 and  cs.getcredit=2   "+str+"   group by  " +
//					" el.id, el.realname,el.depid ,el.shengri" +
//					" union all" +
//					" select  el.id elid ,el.realname elrealname,el.depid eldepid,sum(clc.setcredit)  as mycredit,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(el.shengri,'yyyy')),-1)  from eluser el left " +
//					" join   study_course  stc on el.id=stc.userid  and stc.passed=1   left join class_course " +
//					" clc on clc.courseid=stc.courseid and clc.classid=stc.classid and clc.getcredit=3  left " +
//					" join study_room str  on str.userid=el.id  and str.classid=stc.classid  and str.ispassed=1" +
//					"  "+str+" group by el.id,el.realname,el.depid,el.shengri" +
//							" ) t  left join  department  dept  on dept.id=t.eldepid where" +
//					" "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+"   "+str1+" " +
//					" group by  t.elid,t.elrealname,t.eldepid,dept.name,t.age )t1 where rownum <= ? ) where rn>=?" ;
			String  sql ="select * from ( select t4.*,rownum rn from (  select t.elid  telid,t.elrealname " +
					"  ,t.eldepid teldepid,dept.name deptname,t.mycredit " +
					"  tmycredit,t1.mycredit1  t1mycredit, " +
					" t2.mycredit2  t2mycredit,t3.mycredit3 t3mycredit," +
					" t.age ,t.elsex from " +
					" (select el.id elid ,el.realname elrealname,el.depid eldepid,sum(cs.credit)" +
					" as mycredit , nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(el.shengri,'yyyy')),-1)" +
					"  as age , el.sex elsex from eluser el  left join  study_course   stc on stc.userid=el.id and stc.classid=0  " +
					" left join  exam_room exr on  exr.courseid=stc.courseid  and exr.classid=stc.classid  " +
					"   left join study_room str on  str.roomid=exr.id and str.userid=el.id and str.ispassed=1 " +
					" left join course cs on  cs.id=stc.courseid   group by  el.id,el.realname,el.depid ,el.shengri,el.sex ) t" +
					" left join    " +
					" (select  el.id elid1 ,sum(clc.setcredit)  as" +
					" mycredit1 " +
					" from eluser el  left join   study_course  stc on el.id=stc.userid  and stc.passed=1  left join  " +
					" class_course clc on clc.courseid=stc.courseid and clc.classid=stc.classid and  clc.getcredit=1   " +
					"    group by el.id ) t1  on t.elid=t1.elid1 " +
					" left join  " +
					" (select el.id elid2 ,sum(cs.setcredit)  as mycredit2  " +
					"  from   eluser el" +
					"  left join   study_room  str   on str.userid=el.id  left join  class_course  cs   on  str.roomid = " +
					" cs.eroomid  and str.ispassed=1 and  cs.getcredit=2        group by   el.id, el.realname,el.depid ,el.shengri )t2 " +
					" on t2.elid2=t.elid left join(select  el.id elid3 ,sum(clc.setcredit)  as" +
					"  mycredit3  from eluser el left  join   " +
					" study_course  stc on el.id=stc.userid  and stc.passed=1   left join class_course  clc on clc.courseid=stc.courseid " +
					" and clc.classid=stc.classid and clc.getcredit=3  left  join study_room str  on str.userid=el.id  and str.classid=stc.classid  " +
					" and str.ispassed=1   group by el.id ) t3 on t3.elid3=t.elid " +
					" left join " +
					" department  dept  on dept.id=t.eldepid where  "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+"  "+str+"    )t4 " +
					" where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()){
				ELUser e = new  ELUser();
				e.setId(rs.getInt(1));
				e.setRealname(rs.getString(2));
				Department  d = new Department();
				d.setName(rs.getString(4));
				d.setId(rs.getInt(3));
				e.setBtotalscore(rs.getInt(5)+rs.getInt(6)+rs.getInt(7)+rs.getInt(8));
				e.setDepartment(d);
				e.setAge(rs.getInt(9));
				e.setSex(rs.getString(10));
				lu.add(e);
			}
		
		} catch (Exception e) {
			logger.error("得到我的个人订单列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lu;
	}
	
	public int studentscoersize(ElNode tree, 
			int sublibs,ELUser elUser) throws ElException {
//		btotalscore
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String  str="";
			if(elUser!=null&&elUser.getRealname()!=null){
				str+= "  and el.realname  like  '%"+elUser.getRealname()+"%' ";
			}
			if(elUser!=null&&elUser.getSex()!=null&&!"".equals(elUser.getSex())){
				
				str+=" and el.sex   like '"+elUser.getSex()+"' ";
			}
			String sql= "select count(1) from (select el.id  from eluser el left join  department dept on dept.id= el.depid where  "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+"  "+str+"  )";
					
			ps = ct.prepareStatement(sql);
			rs=ps.executeQuery();
				rs.next();
				return rs.getInt(1);
			
		
		} catch (Exception e) {
			logger.error("得到我的个人订单列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public List<MyCourse> scoerinfo_list_byuserid( int userid,int pS , int pN)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			String sql="";
			sql ="  select *  from (select t.*,rownum  rn from (select distinct  * from( select eu.id,eu.username,eu.realname,eu.depid,dep.name  depname," +
					" c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_," +
					" sqi.myScore,sqi.ispassed,ec.id as classId,ec.name as className,sc.passtime_2/60 passtime2 ," +
					" clc.setcredit clcs,clc.getcredit clcg,sc.passed scpassed  ,str.ispassed  strispassed ," +
					" str.myscore strmyscore,exr.id   strroomid,c.name cname,ct.name ctname ,sc.starttime,sc.finishtime from  COURSE c left join COURSE_TYPE ct on " +
					" c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join eluser eu on " +
					" sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi " +
					" on sqi.id=sc.sqiid left join elclass ec on ec.id=sc.classid left join exam_room exr on " +
					" exr.courseid=sc.courseid  and exr.classid=ec.id left join study_room str   on str.roomid=exr.id " +
					" and str.userid=eu.id  left join  class_course  clc   on clc.classid=ec.id and clc.courseid = " +
					" sc.courseid where  sc.userid =?))t where rownum <= ? ) where rn>=? ";
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")

			//System.out.println("::" + buffer.toString());
			
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pS);
			ps.setInt(3, pN);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(3));
				eu.setUsername(rs.getString(2));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				Course c = new Course();
				c.setName(rs.getString(24));
				c.setCredit(rs.getInt(6));
				c.setDuring(rs.getInt(7));
				c.setSetcredit(rs.getInt(18));
				c.setGetcredit(rs.getInt(19));
				CourseType cct=new CourseType();
				cct.setName(rs.getString(25));
				c.setCtype(cct);
				MyCourse mc = new MyCourse();
				mc.setApplyDate(rs.getTimestamp(26));
				mc.setEndtime(rs.getTimestamp(27));
				mc.setPassed(rs.getInt(20) == 0 ? false : true);
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				mc.setMyCredit(rs.getFloat(11));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(12)));
				mc.getMyExamPaper().setMyScore(rs.getFloat(13));
				mc.getMyExamPaper().setIspassed(rs.getInt(14));
				mc.setClassId(rs.getInt("classId"));
				mc.setClassName(rs.getString("className"));
				if(mc.getClassName()!=null){
					c.setCredit(rs.getInt(18));//新添加的 2012 9月4
				}
				mc.setPasstime2(rs.getInt("passtime2"));
				mc.setUser(eu);
				mc.setMyRoom(new MyRoom(rs.getInt("strroomid")));
				if(mc.getMyRoom().getId()!=0){
					mc.getMyRoom().setIspassed(rs.getInt(21));
					mc.getMyRoom().setMyScore(rs.getFloat(22));
					
				}
			
				
				if(mc.getClassName()!=null){//当为培训班内课程时
					//用来判断是否结业方式通过
					if (c.getGetcredit() == 1 && mc.isPassed()) {
						mc.setMyCredit(c.getSetcredit());
					} else if (c.getGetcredit() == 2 && mc.getMyRoom().getIspassed()== 1) {
						mc.setMyCredit(c.getSetcredit());
					} else if (c.getGetcredit() == 3 && mc.isPassed()) {
						if ( mc.getMyRoom().getIspassed() == 1) {
							mc.setMyCredit(c.getSetcredit());
						} else
							mc.setMyCredit(0);
					} else {
						mc.setMyCredit(0);
					}
				}else{//如果是单独课程
					if( mc.getMyRoom().getIspassed()==1){
						mc.setMyCredit(c.getCredit());
					}else{
						mc.setMyCredit(0);
					}
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
	
	public  int  scoerinfo_size_byuserid( int userid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql="";
			sql ="  select count(1) from (select distinct  * from( select eu.id,eu.username,eu.realname,eu.depid,dep.name  depname," +
			" c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_," +
			" sqi.myScore,sqi.ispassed,ec.id as classId,ec.name as className,sc.passtime_2/60 passtime2 ," +
			" clc.setcredit clcs,clc.getcredit clcg,sc.passed scpassed  ,str.ispassed  strispassed ," +
			" str.myscore strmyscore,exr.id   strroomid,c.name cname,ct.name ctname ,sc.starttime,sc.finishtime from  COURSE c left join COURSE_TYPE ct on " +
			" c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join eluser eu on " +
			" sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi " +
			" on sqi.id=sc.sqiid left join elclass ec on ec.id=sc.classid left join exam_room exr on " +
			" exr.courseid=sc.courseid  and exr.classid=ec.id left join study_room str   on str.roomid=exr.id " +
			" and str.userid=eu.id  left join  class_course  clc   on clc.classid=ec.id and clc.courseid = " +
			" sc.courseid where  sc.userid =?))";
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")
		
			//System.out.println("::" + buffer.toString());
			
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			rs.next();
				
			return  rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	
	}
	
	public List<Integer> allscoerinfo_list_byuserid( int userid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			String sql="";
			sql =" select distinct  * from( select eu.id,eu.username,eu.realname,eu.depid,dep.name  depname," +
			" c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_," +
			" sqi.myScore,sqi.ispassed,ec.id as classId,ec.name as className,sc.passtime_2/60 passtime2 ," +
			" clc.setcredit clcs,clc.getcredit clcg,sc.passed scpassed  ,str.ispassed  strispassed ," +
			" str.myscore strmyscore,exr.id   strroomid,c.name cname,ct.name ctname ,sc.starttime,sc.finishtime from  COURSE c left join COURSE_TYPE ct on " +
			" c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join eluser eu on " +
			" sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi " +
			" on sqi.id=sc.sqiid left join elclass ec on ec.id=sc.classid left join exam_room exr on " +
			" exr.courseid=sc.courseid  and exr.classid=ec.id left join study_room str   on str.roomid=exr.id " +
			" and str.userid=eu.id  left join  class_course  clc   on clc.classid=ec.id and clc.courseid = " +
			" sc.courseid where  sc.userid =?) ";
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")
		
			//System.out.println("::" + buffer.toString());
			
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);

			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(3));
				eu.setUsername(rs.getString(2));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				Course c = new Course();
			
				c.setCredit(rs.getInt(6));
				c.setDuring(rs.getInt(7));
				c.setSetcredit(rs.getInt(18));
				c.setGetcredit(rs.getInt(19));
				MyCourse mc = new MyCourse();
				mc.setApplyDate(rs.getTimestamp(26));
				mc.setEndtime(rs.getTimestamp(27));
				mc.setPassed(rs.getInt(20) == 0 ? false : true);
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				mc.setMyCredit(rs.getFloat(11));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(12)));
				mc.getMyExamPaper().setMyScore(rs.getFloat(13));
				mc.getMyExamPaper().setIspassed(rs.getInt(14));
				mc.setClassId(rs.getInt("classId"));
				mc.setClassName(rs.getString("className"));
				if(mc.getClassName()!=null){
					c.setCredit(rs.getInt(18));//新添加的 2012 9月4
				}
				mc.setPasstime2(rs.getInt("passtime2"));
				mc.setUser(eu);
				mc.setMyRoom(new MyRoom(rs.getInt("strroomid")));
				if(mc.getMyRoom().getId()!=0){
					mc.getMyRoom().setIspassed(rs.getInt(21));
					mc.getMyRoom().setMyScore(rs.getFloat(22));
					
				}
			
				
				if(mc.getClassName()!=null){//当为培训班内课程时
					//用来判断是否结业方式通过
					if (c.getGetcredit() == 1 && mc.isPassed()) {
						mc.setMyCredit(c.getSetcredit());
					} else if (c.getGetcredit() == 2 && mc.getMyRoom().getIspassed()== 1) {
						mc.setMyCredit(c.getSetcredit());
					} else if (c.getGetcredit() == 3 && mc.isPassed()) {
						if ( mc.getMyRoom().getIspassed() == 1) {
							mc.setMyCredit(c.getSetcredit());
						} else
							mc.setMyCredit(0);
					} else {
						mc.setMyCredit(0);
					}
				}else{//如果是单独课程
					if( mc.getMyRoom().getIspassed()==1){
						mc.setMyCredit(c.getCredit());
					}else{
						mc.setMyCredit(0);
					}
				}
				myBxc.add(mc); 
				
				
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		int allscoer=0;//总学分
		int myallscoer=0;//我获得的总学分
		int	alltime=0;//总学时
		int successtime=0;//已完成学时
		int truetime=0;//实际学时
		List<Integer>  l = new ArrayList<Integer>(); 
		for (MyCourse m : myBxc) {
			allscoer+=m.getCourse().getCredit();//循环求总
			myallscoer+=m.getMyCredit();
			alltime+=m.getCourse().getDuring();
			successtime+=m.getPasstime();
			truetime+=m.getPasstime2();
		}
		l.add(allscoer);
		l.add(myallscoer);
		l.add(alltime);
		l.add(successtime);
		l.add(truetime);
		return l;
}

	

}
