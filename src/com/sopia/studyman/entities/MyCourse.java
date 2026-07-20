package com.sopia.studyman.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.shopping.entities.CourseOrder;

public class MyCourse {
	private Course course;
	private float process;
	private int passtime;
	private boolean passed;
	private int status;
	// private String processStr;
	private List<MyExamPaper> myEps;
	private ELUser user;
	private ExamRoom examRoom;
	private Timestamp applyDate;
	private Timestamp endtime;
	private Timestamp deletedate;
	private int quizScore;
	private float myCredit;
	private int lasttime;
	private MyExamPaper myExamPaper;
	private Department department;
	private int classId;
	private String className;
	private int binding;//绑定状态
	private int eroomid;//绑定考场Id
	private List<MyExamPaper> myExamPaperList;
	private int isDel;
	private int passtime2;//实际学习时长
	private MyRoom myRoom;
	private float tprocess;
	//scorm
	private String lessonLocation;
	private String lessonStatus ;
	private String sessionTime;
	
	private CourseOrder corder;//周攀添加
	
	private int canLearn;//该课程当前是否可以学习
	private int examPass;
	private int firstCpid;
	
	public int getFirstCpid() {
		return firstCpid;
	}

	public void setFirstCpid(int firstCpid) {
		this.firstCpid = firstCpid;
	}

	public int getExamPass() {
		return examPass;
	}

	public void setExamPass(int examPass) {
		this.examPass = examPass;
	}

	public int getCanLearn() {
		return canLearn;
	}

	public void setCanLearn(int canLearn) {
		this.canLearn = canLearn;
	}

	public CourseOrder getCorder() {
		return corder;
	}

	public void setCorder(CourseOrder corder) {
		this.corder = corder;
	}
	
	public String getLessonLocation() {
		return lessonLocation;
	}

	public void setLessonLocation(String lessonLocation) {
		this.lessonLocation = lessonLocation;
	}

	public String getLessonStatus() {
		return lessonStatus;
	}

	public void setLessonStatus(String lessonStatus) {
		this.lessonStatus = lessonStatus;
	}

	public String getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(String sessionTime) {
		this.sessionTime = sessionTime;
	}

	private int cpracCount;//课程练习数
	public int getCpracCount() {
		return cpracCount;
	}

	public void setCpracCount(int cpracCount) {
		this.cpracCount = cpracCount;
	}

	public float getTprocess() {
		return tprocess;
	}

	public void setTprocess(float tprocess) {
		this.tprocess = tprocess;
	}

	public MyRoom getMyRoom() {
		return myRoom;
	}

	public void setMyRoom(MyRoom myRoom) {
		this.myRoom = myRoom;
	}

	public int getPasstime2() {
		return passtime2;
	}
	public String getPasstimeStr() {
		if(passtime<60)
			return passtime+"秒";
		else if(passtime<3600)
			return passtime/60+"分"+passtime%60+"秒";
		else  
		return  passtime/3600+"小时"+(passtime%3600)/60+"分"+passtime%60+"秒"; 
	}
	public String getPasstime2Str() {
		if(passtime2<60)
			return passtime2+"秒";
		else if(passtime2<3600)
			return passtime2/60+"分"+passtime2%60+"秒";
		else  
		return  passtime2/3600+"小时"+(passtime2%3600)/60+"分"+passtime2%60+"秒"; 
	}
	public void setPasstime2(int passtime2) {
		this.passtime2 = passtime2;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public List<MyExamPaper> getMyExamPaperList() {
		return myExamPaperList;
	}

	public void setMyExamPaperList(List<MyExamPaper> myExamPaperList) {
		this.myExamPaperList = myExamPaperList;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * 课程学习概况
	 */
//	private int zc_time;
//	private int zc_xxtime;
//	private int bxc_time;
//	private int bxc_xxtime;
//	private int xxc_time;
//	private int xxc_xxtime;
//	private int zxc_time;
//	private int zxc_xxtime;
	// 考试
	// private int zep_count;
//	private int bxep_count;
//	private int xxep_count;
//	private int zxep_count;
//	private int qtep_count;
//	private int cpep_count;
//
//	private int bxep_score;
//	private int xxep_score;
//	private int zxep_score;
//	private int qtep_score;
//	private int cpep_score;
//
//	private int bxep_avg;
//	private int xxep_avg;
//	private int zxep_avg;
//	private int qtep_avg;
//	private int cpep_avg;

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public Timestamp getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public MyCourse() {
	}

	public MyCourse(int userid, int courseid) {
		this.user = new ELUser(userid);
		this.course = new Course(courseid);
	}

	public ELUser getUser() {
		return user;
	}

	public void setUser(ELUser user) {
		this.user = user;
	}

	public List<MyExamPaper> getMyEps() {
		return myEps;
	}

	public void setMyEps(List<MyExamPaper> myEps) {
		this.myEps = myEps;
	}

	public String getProcessStr() {
		// if((int)process==100) return "100";
		// process = process * 100;
		// processStr = process + "";
		// processStr =
		// processStr
		// .substring(0,processStr.lastIndexOf(".")+
		// ((processStr.substring(processStr.lastIndexOf("."))).length() >2 ?
		// 2 : (processStr.substring(processStr.lastIndexOf("."))).length()));
		return process + "";
	}
	public String getTprocessStr() {
		// if((int)process==100) return "100";
		// process = process * 100;
		// processStr = process + "";
		// processStr =
		// processStr
		// .substring(0,processStr.lastIndexOf(".")+
		// ((processStr.substring(processStr.lastIndexOf("."))).length() >2 ?
		// 2 : (processStr.substring(processStr.lastIndexOf("."))).length()));
		float temp=Float.parseFloat((this.tprocess*100.0+"").substring(0, (this.tprocess*100.0+"").indexOf(".")));
		return temp/100f + "";
	}

	// public void setProcessStr(String processStr) {
	//
	// this.processStr = processStr;
	// }

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public float getProcess() {
		return process;
	}
	
//	public float getProcess_() {
//		return Integer.parseInt(process*100+"")/100;
//	}
	public float getProcess_() {
		float temp=Float.parseFloat((this.process*100.0+"").substring(0, (this.process*100.0+"").indexOf(".")));
		//(this.process*100.0+"").substring(0, (this.process*100.0+"").indexOf("."));
		return temp/100f;
	}

	public void setProcess(float process) {
		this.process = process;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusName() {
		if (status == CourseConstants.COURSE_STUDY_STATUS_BX)
			return "必修课";
		else if (status == CourseConstants.COURSE_STUDY_STATUS_XX)
			return "选修课";
		else if (status == CourseConstants.COURSE_STUDY_STATUS_ZX)
			return "主修课";
		else
			return "未知状态";
	}
	
	

	public int getPasstime() {
		return passtime;
	}

	public void setPasstime(int passtime) {
		this.passtime = passtime;
	}

	public Timestamp getDeletedate() {
		return deletedate;
	}

	public void setDeletedate(Timestamp deletedate) {
		this.deletedate = deletedate;
	}

//	public int getZc_time() {
//		// return zc_time;
//		return bxc_time + xxc_time + zxc_time;
//	}
//
//	public void setZc_time(int zc_time) {
//		this.zc_time = zc_time;
//	}
//
//	public int getZc_xxtime() {
//		return bxc_xxtime + xxc_xxtime + zxc_xxtime;
//		// return zc_xxtime;
//	}
//
//	public void setZc_xxtime(int zc_xxtime) {
//		this.zc_xxtime = zc_xxtime;
//	}
//
//	public int getBxc_time() {
//		return bxc_time;
//	}
//
//	public void setBxc_time(int bxc_time) {
//		this.bxc_time = bxc_time;
//	}
//
//	public int getBxc_xxtime() {
//		return bxc_xxtime;
//	}
//
//	public void setBxc_xxtime(int bxc_xxtime) {
//		this.bxc_xxtime = bxc_xxtime;
//	}
//
//	public int getXxc_time() {
//		return xxc_time;
//	}
//
//	public void setXxc_time(int xxc_time) {
//		this.xxc_time = xxc_time;
//	}
//
//	public int getXxc_xxtime() {
//		return xxc_xxtime;
//	}
//
//	public void setXxc_xxtime(int xxc_xxtime) {
//		this.xxc_xxtime = xxc_xxtime;
//	}
//
//	public int getZxc_time() {
//		return zxc_time;
//	}
//
//	public void setZxc_time(int zxc_time) {
//		this.zxc_time = zxc_time;
//	}
//
//	public int getZxc_xxtime() {
//		return zxc_xxtime;
//	}
//
//	public void setZxc_xxtime(int zxc_xxtime) {
//		this.zxc_xxtime = zxc_xxtime;
//	}

//	public int getBxep_count() {
//		return bxep_count;
//	}
//
//	public void setBxep_count(int bxep_count) {
//		this.bxep_count = bxep_count;
//	}
//
//	public int getXxep_count() {
//		return xxep_count;
//	}
//
//	public void setXxep_count(int xxep_count) {
//		this.xxep_count = xxep_count;
//	}
//
//	public int getZxep_count() {
//		return zxep_count;
//	}
//
//	public void setZxep_count(int zxep_count) {
//		this.zxep_count = zxep_count;
//	}
//
//	public int getQtep_count() {
//		return qtep_count;
//	}
//
//	public void setQtep_count(int qtep_count) {
//		this.qtep_count = qtep_count;
//	}
//
//	public int getCpep_count() {
//		return cpep_count;
//	}
//
//	public void setCpep_count(int cpep_count) {
//		this.cpep_count = cpep_count;
//	}
//
//	public int getBxep_score() {
//		return bxep_score;
//	}
//
//	public void setBxep_score(int bxep_score) {
//		this.bxep_score = bxep_score;
//	}
//
//	public int getXxep_score() {
//		return xxep_score;
//	}
//
//	public void setXxep_score(int xxep_score) {
//		this.xxep_score = xxep_score;
//	}
//
//	public int getZxep_score() {
//		return zxep_score;
//	}
//
//	public void setZxep_score(int zxep_score) {
//		this.zxep_score = zxep_score;
//	}
//
//	public int getQtep_score() {
//		return qtep_score;
//	}
//
//	public void setQtep_score(int qtep_score) {
//		this.qtep_score = qtep_score;
//	}
//
//	public int getCpep_score() {
//		return cpep_score;
//	}
//
//	public void setCpep_score(int cpep_score) {
//		this.cpep_score = cpep_score;
//	}
//
//	public int getBxep_avg() {
//		return bxep_avg;
//	}
//
//	public void setBxep_avg(int bxep_avg) {
//		this.bxep_avg = bxep_avg;
//	}
//
//	public int getXxep_avg() {
//		return xxep_avg;
//	}
//
//	public void setXxep_avg(int xxep_avg) {
//		this.xxep_avg = xxep_avg;
//	}
//
//	public int getZxep_avg() {
//		return zxep_avg;
//	}
//
//	public void setZxep_avg(int zxep_avg) {
//		this.zxep_avg = zxep_avg;
//	}
//
//	public int getQtep_avg() {
//		return qtep_avg;
//	}
//
//	public void setQtep_avg(int qtep_avg) {
//		this.qtep_avg = qtep_avg;
//	}
//
//	public int getCpep_avg() {
//		return cpep_avg;
//	}
//
//	public void setCpep_avg(int cpep_avg) {
//		this.cpep_avg = cpep_avg;
//	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public int getQuizScore() {
		return quizScore;
	}

	public void setQuizScore(int quizScore) {
		this.quizScore = quizScore;
	}

	public float getMyCredit() {
		return myCredit;
	}

	public void setMyCredit(float myCredit) {
		this.myCredit = myCredit;
	}

	public int getLasttime() {
		return lasttime;
	}

	public void setLasttime(int lasttime) {
		this.lasttime = lasttime;
	}

	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}

	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}

	public int getBinding() {
		return binding;
	}

	public void setBinding(int binding) {
		this.binding = binding;
	}

	public int getEroomid() {
		return eroomid;
	}

	public void setEroomid(int eroomid) {
		this.eroomid = eroomid;
	}

}
