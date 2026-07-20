package com.sopia.intelligentTutoringPoints.entities;
/**
 * 智能辅导分之复听
 */
import java.sql.Timestamp;

public class IntelligentProportion {
	private int userid;//用户ID
	private int myexampaperid;//用户试卷ID
	private int exampaperid;//试卷ID
	private int classid;//培训班ID
	private int courseid;//课程ID
	private int pageid;//章节ID
	private int roomid;//章节考场ID
	private int blockid;//大题ID
	private int questionid;//小题ID
	private Timestamp proportime;//复听时间
	private double totalscore;//总分
	private int totalcount;//总题目数量
	private int qcount;//复听题目数量
	private int tcount;//复听次数
	private float qprocess;//复听题目比例
	private float tprocess;//复听次数比例
	private double qscore;//复听数量得分
	private double tscore;//复听次数得分
	public IntelligentProportion(){}
	public IntelligentProportion(int totalcount,int qcount,float qprocess,int tcount,float tprocess,double totalscore,double qscore,double tscore){
		this.totalcount = totalcount;
		this.qcount = qcount;
		this.qprocess = qprocess * 100;
		this.tcount = tcount;
		this.tprocess = tprocess * 100;
		this.totalscore = totalscore;
		this.qscore = qscore;
		this.tscore = tscore;
	}
	
	public double getQscore() {
		return qscore;
	}
	public void setQscore(double qscore) {
		this.qscore = qscore;
	}
	public double getTscore() {
		return tscore;
	}
	public void setTscore(double tscore) {
		this.tscore = tscore;
	}
	public double getTotalscore() {
		return totalscore;
	}
	public void setTotalscore(double totalscore) {
		this.totalscore = totalscore;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	public int getQcount() {
		return qcount;
	}
	public void setQcount(int qcount) {
		this.qcount = qcount;
	}
	public int getTcount() {
		return tcount;
	}
	public void setTcount(int tcount) {
		this.tcount = tcount;
	}

	public float getQprocess() {
		return qprocess;
	}
	public void setQprocess(float qprocess) {
		this.qprocess = qprocess;
	}
	public float getTprocess() {
		return tprocess;
	}
	public void setTprocess(float tprocess) {
		this.tprocess = tprocess;
	}
	public int getExampaperid() {
		return exampaperid;
	}
	public void setExampaperid(int exampaperid) {
		this.exampaperid = exampaperid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getMyexampaperid() {
		return myexampaperid;
	}
	public void setMyexampaperid(int myexampaperid) {
		this.myexampaperid = myexampaperid;
	}
	public int getBlockid() {
		return blockid;
	}
	public void setBlockid(int blockid) {
		this.blockid = blockid;
	}
	public int getQuestionid() {
		return questionid;
	}
	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public int getPageid() {
		return pageid;
	}
	public void setPageid(int pageid) {
		this.pageid = pageid;
	}
	public int getRoomid() {
		return roomid;
	}
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}
	public Timestamp getProportime() {
		return proportime;
	}
	public void setProportime(Timestamp proportime) {
		this.proportime = proportime;
	}
	

	
}
