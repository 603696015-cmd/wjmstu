package com.sopia.wordman.entities;

import java.sql.Timestamp;

public class LearnPlan {
	private int id;
	private String name;    //学习计划名称
	private Timestamp starttime;   //计划开始时间
	private Timestamp endtime;		//计划结束时间
	private String period;			//计划周期
	private String content;    		//计划内容
	private Double hours;			//学时
	private int userid;			//用户ID
	private Double sjhours;			//实际学时
	public Double getSjhours() {
		return sjhours;
	}
	public void setSjhours(Double sjhours) {
		this.sjhours = sjhours;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
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
	public Timestamp getStarttime() {
		return starttime;
	}
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Double getHours() {
		return hours;
	}
	public void setHours(Double hours) {
		this.hours = hours;
	}
	
}
