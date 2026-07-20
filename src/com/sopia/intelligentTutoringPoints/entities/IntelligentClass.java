package com.sopia.intelligentTutoringPoints.entities;

import com.sopia.duman.entities.ELUser;

/**
 * 智能辅导分之等级总学习时间
 * @author TMK
 *
 */
public class IntelligentClass {
	private int userid;//用户ID
	private double totalScore;//等级总分
	private int classid;//等级ID
	private int courseid;//课程ID
	private int pageid;//章节ID
	private int totalSecond;//章节总学习时间
	private ELUser elUser;
	public IntelligentClass(){
		
	}
	public IntelligentClass(int userid,double totalscore,int totalsecond,int classid,int courseid,int pageid){
		this.userid = userid;
		this.totalScore = totalscore;
		this.totalSecond = totalsecond;
		this.classid = classid;
		this.courseid = courseid;
		this.pageid = pageid;
	}
	
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
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
	public int getTotalSecond() {
		return totalSecond;
	}
	public void setTotalSecond(int totalSecond) {
		this.totalSecond = totalSecond;
	}
	
	

}
