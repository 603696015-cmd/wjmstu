package com.sopia.duman.entities;

import java.sql.Timestamp;

public class MyLogin {
	private int id;
	private ELUser elUser;
	private Timestamp logintime;
	private Timestamp exittime;
	private String ipAddr;
	private int lognumber;//每日登陆次数
	private float score; //积分得分
	private Double shichang; //时长
	public Double getShichang() {
		return shichang;
	}
	public void setShichang(Double shichang) {
		this.shichang = shichang;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getLognumber() {
		return lognumber;
	}
	public void setLognumber(int lognumber) {
		this.lognumber = lognumber;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public Timestamp getLogintime() {
		return logintime;
	}
	public void setLogintime(Timestamp logintime) {
		this.logintime = logintime;
	}
	public Timestamp getExittime() {
		return exittime;
	}
	public void setExittime(Timestamp exittime) {
		this.exittime = exittime;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
}
