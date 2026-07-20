package com.sopia.wjm.entities;

import java.sql.Timestamp;

public class QuizpaperLogInfo {
	private int userid;
	private int classid;
	private int courseid;
	private int pageid;
	private int myexampaperid;
	private Timestamp begintime;
	private Timestamp endtime;
	private int passtime;
	private float score;
	
	public QuizpaperLogInfo(){}
	public QuizpaperLogInfo(int classid,int courseid,int pageid,int myexampaperid,Timestamp begintime,Timestamp endtime,int passtime,float score){
		this.classid = classid;
		this.courseid = courseid;
		this.pageid = pageid;
		this.myexampaperid = myexampaperid;
		this.begintime = begintime;
		this.endtime = endtime;
		this.passtime = passtime;
		this.score = score;
	}
	
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
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
	public int getPasstime() {
		return passtime;
	}
	public void setPasstime(int passtime) {
		this.passtime = passtime;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
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
	public int getMyexampaperid() {
		return myexampaperid;
	}
	public void setMyexampaperid(int myexampaperid) {
		this.myexampaperid = myexampaperid;
	}
	
	

}
