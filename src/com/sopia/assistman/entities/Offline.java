package com.sopia.assistman.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.duman.entities.ELUser;


public class Offline {
	private int id;
	private String name ;
	private String description ; 
	private List<ELUser> users;
	private Timestamp begintime;
	private Timestamp endtime;
	private int during;
	private int xueshi;
	private int score;
	private int usercount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ELUser> getUsers() {
		return users;
	}
	public void setUsers(List<ELUser> users) {
		this.users = users;
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
	public int getDuring() {
		return during;
	}
	public void setDuring(int during) {
		this.during = during;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getXueshi() {
		return xueshi;
	}
	public void setXueshi(int xueshi) {
		this.xueshi = xueshi;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getUsercount() {
		return usercount;
	}
	public void setUsercount(int usercount) {
		this.usercount = usercount;
	}
	
	//活动名称、活动简介、参与人员（支持批量导入）、时间段（开始时间、结束时间）、时长、学时、学分
}
