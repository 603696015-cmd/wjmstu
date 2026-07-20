package com.sopia.studyman.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.PracticePaper;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;

public class MyCPage {
	private ELUser user;
	private CoursePage cpage;
	private int passtime;
	private boolean passed;
	private Timestamp begintime;
	private Timestamp endtime;
	private List<CoursePage>cpages;
	private List<MyPractice> myPracs;
	private int lasttime;
	private int passtime2;
	private List<ExamPaper> examPapers;//章节对应的练习，当前设计 每个章节只对应多个章节
	private ExamPaper examPaper;//章节对应的练习，当前设计 每个章节只对应一个练习
	private float myscore;
	private int passed2;//是否考过
	private PracticePaper pracp;
	//scorm
	private String lessonLocation;
	private String lessonStatus ;
	private String sessionTime;
	private int classid;
	private int cpid;
	private int courseid;
	private float process ;
	private long studyinfo_time ;
	private int studyinfo_rid;
	private List<ExamRoom> examRooms;//章节对应的多个考场
	private ExamRoom examRoom;//章节对应的考场
	private int canLearn;
	private List<MyExamPaper> myExamPapers;
	private MyExamPaper myExamPaper;
	private MyRoom myRoom;
	
	private int isExceedNumberExam;//超过考试次数20141008
	
	public int getIsExceedNumberExam() {
		return isExceedNumberExam;
	}
	public void setIsExceedNumberExam(int isExceedNumberExam) {
		this.isExceedNumberExam = isExceedNumberExam;
	}
	public MyRoom getMyRoom() {
		return myRoom;
	}
	public void setMyRoom(MyRoom myRoom) {
		this.myRoom = myRoom;
	}
	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}
	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}
	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}
	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}
	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}
	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}
	public List<MyExamPaper> getMyExamPapers() {
		return myExamPapers;
	}
	public void setMyExamPapers(List<MyExamPaper> myExamPapers) {
		this.myExamPapers = myExamPapers;
	}
	public int getCanLearn() {
		return canLearn;
	}
	public void setCanLearn(int canLearn) {
		this.canLearn = canLearn;
	}
	public ExamRoom getExamRoom() {
		return examRoom;
	}
	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}
	public int getStudyinfo_rid() {
		return studyinfo_rid;
	}
	public void setStudyinfo_rid(int studyinfo_rid) {
		this.studyinfo_rid = studyinfo_rid;
	}
	public long getStudyinfo_time() {
		return studyinfo_time;
	}
	public void setStudyinfo_time(long studyinfo_time) {
		this.studyinfo_time = studyinfo_time;
	}
	public float getProcess() {
		return process;
	}
	public float getProcessStr() {
		return Math.round(process*100)/100.0f;
	}
	public String getPasstime2Str() {
		if(passtime2<60)
			return passtime2+"秒";
		else if(passtime2<3600)
			return passtime2/60+"分"+passtime2%60+"秒";
		else  
		return  passtime2/3600+"小时"+(passtime2%3600)/60+"分"+passtime2%60+"秒"; 
	}
	public void setProcess(float process) {
		this.process = process;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
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
	public String getLessonLocation() {
		return lessonLocation;
	}
	public void setLessonLocation(String lessonLocation) {
		this.lessonLocation = lessonLocation;
	}
	public PracticePaper getPracp() {
		return pracp;
	}
	public void setPracp(PracticePaper pracp) {
		this.pracp = pracp;
	}
	public float getMyscore() {
		return myscore;
	}
	public void setMyscore(float myscore) {
		this.myscore = myscore;
	}
	public int getPassed2() {
		return passed2;
	}
	public void setPassed2(int passed2) {
		this.passed2 = passed2;
	}
	public ExamPaper getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	public int getPasstime2() {
		return passtime2;
	}
	public void setPasstime2(int passtime2) {
		this.passtime2 = passtime2;
	}
	public int getLasttime() {
		return lasttime;
	}
	public void setLasttime(int lasttime) {
		this.lasttime = lasttime;
	}
	public MyCPage() {
		// TODO Auto-generated constructor stub
	}
	public MyCPage(int userid,int cpid)
	{
		this.user = new ELUser(userid);
		this.cpage = new CoursePage(cpid);
		
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public CoursePage getCpage() {
		return cpage;
	}
	public void setCpage(CoursePage cpage) {
		this.cpage = cpage;
	}
	public int getPasstime() {
		return passtime;
	}
	
//	public double getPasstime_() {
//		return passtime/Double.parseDouble((this.cpage.getDuring()*60)+"")*100;
//	}
	public double getPasstime_() {
		String tempStr=passtime/Double.parseDouble((this.cpage.getDuring()*60)+"")*100*100+"";
		return Double.parseDouble(tempStr.substring(0, tempStr.indexOf(".")))/100.0;
	}
	
	public double getPasstime_2() {//
		double dble=0;
		if(this.getCpage().getGetcredit()==1 || this.getCpage().getGetcredit()==2){
			//学完 或者 考过
			if(this.getPasstime_()==100){
				return 100;
			}
		}else{
			double temp=0;
			if(this.passtime2/60>=this.getCpage().getDuring()){
				temp=50;
			}
			if(this.passed2==1){
				temp+=50;
			}
			return temp;
		}
		return dble;
	}
	
	public void setPasstime(int passtime) {
		this.passtime = passtime;
	}
	public boolean isPassed() {
		return passed;
	}
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	public List<CoursePage> getCpages() {
		return cpages;
	}
	public void setCpages(List<CoursePage> cpages) {
		this.cpages = cpages;
	}
	public Timestamp getBegintime() {
		return begintime;
	}
	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public List<MyPractice> getMyPracs() {
		return myPracs;
	}
	public void setMyPracs(List<MyPractice> myPracs) {
		this.myPracs = myPracs;
	}
	public int getCpid() {
		return cpid;
	}
	public void setCpid(int cpid) {
		this.cpid = cpid;
	}
	
}
