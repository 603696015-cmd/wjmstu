package com.sopia.statman.entities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.sopia.classman.entities.ElClass;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.StudyConstants;
import com.sopia.studyman.entities.MyCourse;

public class MyClass {
	private ELUser user;
	private ElClass elClass;
	private int bxCount;
	private int xxCredit;
	private int yxBxCount;
	private int yxxxCredit;
	private Date begintime;
	private int status;
	private Timestamp endtime;
	private List<MyCourse> myCourseB;
	private List<MyCourse> myCourseX;
//	private List<MyCourse> myCourseZ;
	private int certificateno;
	private String certificatenoStr;
	public boolean passed;
	public List<String> noPassRemack;//培训班未通过的说明
	
	
	private double process;
	private List<Course> courses;
	private Course course;
	private double processForElc;
	private int courseCount;
	private int userid;
	private int coforpassed; //已通过课程数
	private int classCount;
	
	private boolean canLearn;//培训班是否能学习
	private int canExam;//培训班对应的考场是否能考试  0不能，1能，-1初始定级通过
	private boolean finish;//培训班对应考场是否通过
	private int sortid;
	private ExamRoom examRoom;
	private boolean intelligentPointsUp;//智能辅导分是否达标
	private int hasExam;//培训班是否有考场
	private double countScoreBX;//必修课总学分
	
	private int myxxCredit;//我的选秀学分
	private int mybxCredit;//我的必修学分
	private int mytCredit; //总分
	
	public int getMyxxCredit() {
		return myxxCredit;
	}
	public void setMyxxCredit(int myxxCredit) {
		this.myxxCredit = myxxCredit;
	}
	public int getMybxCredit() {
		return mybxCredit;
	}
	public void setMybxCredit(int mybxCredit) {
		this.mybxCredit = mybxCredit;
	}
	public int getMytCredit() {
		return mytCredit;
	}
	public void setMytCredit(int mytCredit) {
		this.mytCredit = mytCredit;
	}
	public double getCountScoreBX() {
		return countScoreBX;
	}
	public void setCountScoreBX(double countScoreBX) {
		this.countScoreBX = countScoreBX;
	}
	public int getHasExam() {
		return hasExam;
	}
	public void setHasExam(int hasExam) {
		this.hasExam = hasExam;
	}
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	
	public int getCanExam() {
		return canExam;
	}
	public void setCanExam(int canExam) {
		this.canExam = canExam;
	}
	public boolean isIntelligentPointsUp() {
		return intelligentPointsUp;
	}
	public void setIntelligentPointsUp(boolean intelligentPointsUp) {
		this.intelligentPointsUp = intelligentPointsUp;
	}
	public ExamRoom getExamRoom() {
		return examRoom;
	}
	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	public boolean isCanLearn() {
		return canLearn;
	}
	public void setCanLearn(boolean canLearn) {
		this.canLearn = canLearn;
	}
	public double getProcess() {
		return process;
	}
	public void setProcess(double process) {
		this.process = process;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public double getProcessForElc() {
		return processForElc;
	}
	public void setProcessForElc(double processForElc) {
		this.processForElc = processForElc;
	}
	public int getCourseCount() {
		return courseCount;
	}
	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getCoforpassed() {
		return coforpassed;
	}
	public void setCoforpassed(int coforpassed) {
		this.coforpassed = coforpassed;
	}
	public int getClassCount() {
		return classCount;
	}
	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}
	public List<String> getNoPassRemack() {
		return noPassRemack;
	}
	public void setNoPassRemack(List<String> noPassRemack) {
		this.noPassRemack = noPassRemack;
	}
	public boolean getPassed() {
		return passed;
	}
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	public void setCertificatenoStr(String certificatenoStr) {
		this.certificatenoStr = certificatenoStr;
	}
	//证书号后四位
	public String getCertificatenoStr() {
		certificatenoStr = certificateno+"";
		for (int i = 0; i <4-(certificateno+"").length(); i++) {
			 certificatenoStr= "0"+certificatenoStr;
		}
		return certificatenoStr;
	}
	public int getCertificateno() {
		return certificateno;
	}

	public void setCertificateno(int certificateno) {
		this.certificateno = certificateno;
	}

	public List<MyCourse> getMyCourseB() {
		return myCourseB;
	}

	public void setMyCourseB(List<MyCourse> myCourseB) {
		this.myCourseB = myCourseB;
	}

	public List<MyCourse> getMyCourseX() {
		return myCourseX;
	}

	public void setMyCourseX(List<MyCourse> myCourseX) {
		this.myCourseX = myCourseX;
	}

//	public List<MyCourse> getMyCourseZ() {
//		return myCourseZ;
//	}

//	public void setMyCourseZ(List<MyCourse> myCourseZ) {
//		this.myCourseZ = myCourseZ;
//	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getXxCredit() {
		return xxCredit;
	}

	public void setXxCredit(int xxCredit) {
		this.xxCredit = xxCredit;
	}

	public ELUser getUser() {
		return user;
	}

	public void setUser(ELUser user) {
		this.user = user;
	}

	public ElClass getElClass() {
		return elClass;
	}

	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}

	public int getYxxxCredit() {
		return yxxxCredit;
	}

	public void setYxxxCredit(int yxxxCredit) {
		this.yxxxCredit = yxxxCredit;
	}

	public int getBxCount() {
		return bxCount;
	}

	public void setBxCount(int bxCount) {
		this.bxCount = bxCount;
	}

	public int getYxBxCount() {
		return yxBxCount;
	}

	public void setYxBxCount(int yxBxCount) {
		this.yxBxCount = yxBxCount;
	}

	public String getStatusName() {

		return status == StudyConstants.STUDY_CLASS_STATUS_NO ? "未通过审核"
				: (status == StudyConstants.STUDY_CLASS_STATUS_YES ? "通过审核"
						: (status == StudyConstants.STUDY_CLASS_STATUS_WAIT ? "审核中"
								: (status == StudyConstants.STUDY_CLASS_STATUS_WAIT ? "修改申请中"
								: "学习中")));

	}
}
