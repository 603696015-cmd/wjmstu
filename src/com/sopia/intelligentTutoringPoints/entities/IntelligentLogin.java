package com.sopia.intelligentTutoringPoints.entities;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;
/**
 * 智能辅导分之登录
 * @author TMK
 *
 */
public class IntelligentLogin {
	private int id;
	private int userid;
	private int classid;
	private Timestamp begintime;
	private Timestamp endtime;
	private int logintime;//本次登录到退出的时间  秒
	private String today;//登录在哪天20130816
	private int loginType;//-1：3天未登录   1：当天第一次登录   0：当天不是第一次登录
	private double totalScore;//总分
	private double score;//每次登陆得分
	private ELUser elUser;
	public IntelligentLogin(){}
	public IntelligentLogin(int id,int userid,Timestamp begintime){
		this.id = id;
		this.userid = userid;
		this.begintime = begintime;
	}
	public IntelligentLogin(int userid,Timestamp begintime,Timestamp endtime,int logintime,String today,double score,int classid){
		this.userid = userid;
		this.begintime = begintime;
		this.endtime = endtime;
		this.logintime = logintime;
		this.today = today;
		this.score = score;
		this.classid = classid;
	}
	
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
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
	public int getLogintime() {
		return logintime;
	}
	public void setLogintime(int logintime) {
		this.logintime = logintime;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLoginType() {
		return loginType;
	}
	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	
	

}
