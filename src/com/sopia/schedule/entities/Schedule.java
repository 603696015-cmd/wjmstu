package com.sopia.schedule.entities;

public class Schedule {
	private int id;
	private String datetime;
	private String timeout;
	private String topic;
	private String content;
	private String status;
	private String re_client;
	private String re_plan;
	private String alertdate;
	private String createdate;
	private int userid;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRe_client() {
		return re_client;
	}
	public void setRe_client(String re_client) {
		this.re_client = re_client;
	}
	public String getRe_plan() {
		return re_plan;
	}
	public void setRe_plan(String re_plan) {
		this.re_plan = re_plan;
	}
	public String getAlertdate() {
		return alertdate;
	}
	public void setAlertdate(String alertdate) {
		this.alertdate = alertdate;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}


	
}
