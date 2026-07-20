package com.sopia.schedule.entities;

public class Logfile {
	private int id;	//主键
	private String log_title;	//日志标题
	private String log_plan;	//工作计划
	private String log_result;	//工作结果
	private String log_analysis;	//总结分析
	private String log_co_client;	//关联客户
	private String log_co_plan;	//关联计划
	private String log_upload;
	private String log_createtime;
	private int log_userid;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLog_title() {
		return log_title;
	}
	public void setLog_title(String log_title) {
		this.log_title = log_title;
	}
	public String getLog_plan() {
		return log_plan;
	}
	public void setLog_plan(String log_plan) {
		this.log_plan = log_plan;
	}
	public String getLog_result() {
		return log_result;
	}
	public void setLog_result(String log_result) {
		this.log_result = log_result;
	}
	public String getLog_analysis() {
		return log_analysis;
	}
	public void setLog_analysis(String log_analysis) {
		this.log_analysis = log_analysis;
	}
	public String getLog_co_client() {
		return log_co_client;
	}
	public void setLog_co_client(String log_co_client) {
		this.log_co_client = log_co_client;
	}
	public String getLog_co_plan() {
		return log_co_plan;
	}
	public void setLog_co_plan(String log_co_plan) {
		this.log_co_plan = log_co_plan;
	}
	public String getLog_upload() {
		return log_upload;
	}
	public void setLog_upload(String log_upload) {
		this.log_upload = log_upload;
	}
	public String getLog_createtime() {
		return log_createtime;
	}
	public void setLog_createtime(String log_createtime) {
		this.log_createtime = log_createtime;
	}
	public int getLog_userid() {
		return log_userid;
	}
	public void setLog_userid(int log_userid) {
		this.log_userid = log_userid;
	}
	

}
