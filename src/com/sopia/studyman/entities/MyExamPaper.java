package com.sopia.studyman.entities;

import java.sql.Timestamp;

import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;

public class MyExamPaper {
	private int id;
	private String myAnswer;
	private ELUser tester;
	private ExamRoom examRoom;
	private ExamPaper examPaper;
	private int passTime;
	private int status;// 考生试卷状态，0.未参加 1.考试中 2.已作答 3.已批阅
	private int minstatus;
//	private String statusName;
	private float myScore;
	private Timestamp begintime;
	private Timestamp endtime;
	private int ispassed;
	private int mySort;
	private Course course;
	private MyCourse myCourse;
	private float score;
	private ExamPaper prac ;
	private Examprac examprac ;
	private int practimes;
	private float pracscore;
	private int mystatus;
	private int yd;
	private int wd;
	private int jiashi;
	private int passTime_js;
	private int bindingId;
	private int classId;
	private float avgscore;
	private int myexamcount;
	private int examIsCenter;//是否可以进入考试
	private String examIsCenterRemack;//不可进入原因说明
	private float maxscore;
	private int passsize;//及格人数
	private int pass9_ ;//90分以上
	private int pass8_9;//	80-90分
	private int pass7_8;//70-80
	private int pass6_7;//	60-70
	private float pass_6_p;//60以下
	private float pass9__p ;//90分以上
	private float pass8_9_p;//	80-90分
	private float pass7_8_p;//70-80
	private float pass6_7_p;//	60-70
	private int pass_6;//60以下
	private int qksize;//缺考人数
	private int yksize;//应考人数
	private int isdel;//是否删除（study_exampaper）
	private int recordid;
	private double time_Dvalue;//最后一次考试时间与当前时间差值
	private int countforday;//当天已考次数
	public double getTime_Dvalue() {
		return time_Dvalue;
	}

	public void setTime_Dvalue(double time_Dvalue) {
		this.time_Dvalue = time_Dvalue;
	}

	public int getCountforday() {
		return countforday;
	}

	public void setCountforday(int countforday) {
		this.countforday = countforday;
	}

	public int getRecordid() {
		return recordid;
	}

	public void setRecordid(int recordid) {
		this.recordid = recordid;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

	public float getMaxscore() {
		return maxscore;
	}

	public void setMaxscore(float maxscore) {
		this.maxscore = maxscore;
	}

	public int getExamIsCenter() {
		return examIsCenter;
	}

	public void setExamIsCenter(int examIsCenter) {
		this.examIsCenter = examIsCenter;
	}

	public String getExamIsCenterRemack() {
		return examIsCenterRemack;
	}

	public void setExamIsCenterRemack(String examIsCenterRemack) {
		this.examIsCenterRemack = examIsCenterRemack;
	}

	public int getMyexamcount() {
		return myexamcount;
	}

	public void setMyexamcount(int myexamcount) {
		this.myexamcount = myexamcount;
	}

	public float getAvgscore() {
		return avgscore;
	}

	public void setAvgscore(float avgscore) {
		this.avgscore = avgscore;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getBindingId() {
		return bindingId;
	}

	public void setBindingId(int bindingId) {
		this.bindingId = bindingId;
	}

	public int getJiashi() {
		return jiashi;
	}

	public void setJiashi(int jiashi) {
		this.jiashi = jiashi;
	}

	public int getYd() {
		return yd;
	}

	public void setYd(int yd) {
		this.yd = yd;
	}

	public int getWd() {
		return wd;
	}

	public void setWd(int wd) {
		this.wd = wd;
	}

	public int getMystatus() {
		return mystatus;
	}

	public void setMystatus(int mystatus) {
		this.mystatus = mystatus;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public MyCourse getMyCourse() {
		return myCourse;
	}

	public void setMyCourse(MyCourse myCourse) {
		this.myCourse = myCourse;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getMySort() {
		return mySort;
	}

	public void setMySort(int mySort) {
		this.mySort = mySort;
	}
//
//	public boolean getPassed() {
//		return passed;
//	}
//
//	public void setPassed(boolean passed) {
//		this.passed = passed;
//	}

	// private Course course;
	// public Course getCourse() {
	// return course;
	// }
	// public void setCourse(Course course) {
	// this.course = course;
	// }
	public MyExamPaper() {
	}

	public MyExamPaper(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public String getMyAnswer() {
//		return myAnswer;
//	}
//
//	public void setMyAnswer(String myAnswer) {
//		this.myAnswer = myAnswer;
//	}

	public ELUser getTester() {
		return tester;
	}

	public void setTester(ELUser tester) {
		this.tester = tester;
	}

	public int getPassTime() {
		return passTime;
	}
	public String getPassTimeStr(){
		if(passTime<60)
			return passTime+"秒";
		else if(passTime<3600)
			return passTime/60+"分"+passTime%60+"秒";
		else  
		return  passTime/3600+"小时"+(passTime%3600)/60+"分"+passTime%60+"秒"; 
	}
	public void setPassTime(int passTime) {
		this.passTime = passTime;
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getMyScore() {
		return 365;
	}

	public void setMyScore(float myScore) {
		this.myScore = myScore;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	/**
	 * 获取学员试卷状态(0.未参加 1.考试中 2.已作答 3.已批阅)
	 * @return
	 */
	public String getStatusName() {
		if(this.id==0){//没有绑定考场
			return "无考试";
		}
		if (isdel == 1)
			return "已删除";
		if (status == 0)
			return "未参加";
		if (status == 1)
			return "考试中";
		if (status == 2)
			return "已作答";
		if (status == 3)
			return "已批阅";
		return "未知";
	}
	
//	public String getStatusName() {
//		if(this.id==0){//没有绑定考场
//			return "无考试";
//		}
//		if (status == 0)
//			return "未开始";
//		if (status == 1)
//			return "考试中";
//		if (status == 2)
//			return "等待批阅";
//		if (status == 3)
//			return "已批阅";
//		if (status == 4)
//			return "暂停";
//		return "未知";
//	}

//	public void setStatusName(String statusName) {
//		this.statusName = statusName;
//	}

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

	public int getIspassed() {
		return ispassed;
	}

	public void setIspassed(int ispassed) {
		this.ispassed = ispassed;
	}

	public ExamPaper getPrac() {
		return prac;
	}

	public void setPrac(ExamPaper prac) {
		this.prac = prac;
	}

	public int getPractimes() {
		return practimes;
	}

	public void setPractimes(int practimes) {
		this.practimes = practimes;
	}

	public float getPracscore() {
		return pracscore;
	}

	public void setPracscore(float pracscore) {
		this.pracscore = pracscore;
	}

	public Examprac getExamprac() {
		return examprac;
	}

	public void setExamprac(Examprac examprac) {
		this.examprac = examprac;
	}

	public int getPassTime_js() {
		return passTime_js;
	}

	public void setPassTime_js(int passTime_js) {
		this.passTime_js = passTime_js;
	}

	public int getPasssize() {
		return passsize;
	}

	public void setPasssize(int passsize) {
		this.passsize = passsize;
	}

	public int getPass9_() {
		return pass9_;
	}

	public void setPass9_(int pass9_) {
		this.pass9_ = pass9_;
	}

	public int getPass8_9() {
		return pass8_9;
	}

	public void setPass8_9(int pass8_9) {
		this.pass8_9 = pass8_9;
	}

	public int getPass7_8() {
		return pass7_8;
	}

	public void setPass7_8(int pass7_8) {
		this.pass7_8 = pass7_8;
	}

	public int getPass6_7() {
		return pass6_7;
	}

	public void setPass6_7(int pass6_7) {
		this.pass6_7 = pass6_7;
	}

	public float getPass_6_p() {
		return pass_6_p;
	}

	public void setPass_6_p(float pass_6_p) {
		this.pass_6_p = pass_6_p;
	}

	public float getPass9__p() {
		return pass9__p;
	}

	public void setPass9__p(float pass9__p) {
		this.pass9__p = pass9__p;
	}

	public float getPass8_9_p() {
		return pass8_9_p;
	}

	public void setPass8_9_p(float pass8_9_p) {
		this.pass8_9_p = pass8_9_p;
	}

	public float getPass7_8_p() {
		return pass7_8_p;
	}

	public void setPass7_8_p(float pass7_8_p) {
		this.pass7_8_p = pass7_8_p;
	}

	public float getPass6_7_p() {
		return pass6_7_p;
	}

	public void setPass6_7_p(float pass6_7_p) {
		this.pass6_7_p = pass6_7_p;
	}

	public int getPass_6() {
		return pass_6;
	}

	public void setPass_6(int pass_6) {
		this.pass_6 = pass_6;
	}

	public int getQksize() {
		return qksize;
	}

	public void setQksize(int qksize) {
		this.qksize = qksize;
	}

	public int getYksize() {
		return yksize;
	}

	public void setYksize(int yksize) {
		this.yksize = yksize;
	}
	public String getPass_6_ps() {
		return  float2dot(pass_6_p)  ;
	}
	public String getPass9__ps() {
		return float2dot(pass9__p);
	}
	public String getPass8_9_ps() {
		return float2dot(pass8_9_p);
	}
	public String getPass7_8_ps() {
		return float2dot(pass7_8_p);
	}
	public String getPass6_7_ps() {
		return float2dot(pass6_7_p);
	} 
	public String float2dot(float f){
		String s = f+"";
		if(s.indexOf(".")>=0){
			String s1 = s.substring(s.indexOf(".")+1);
			if(s1.length()>2)
				s = s.substring(0,s.indexOf("."))+"."+s1.substring(0,2);
			else
				s = s.substring(0,s.indexOf("."))+"."+s1 ;
		}
		return s;
	}

	public int getMinstatus() {
		return minstatus;
	}

	public void setMinstatus(int minstatus) {
		this.minstatus = minstatus;
	}

	public String getMyAnswer() {
		return myAnswer;
	}

	public void setMyAnswer(String myAnswer) {
		this.myAnswer = myAnswer;
	}
}
