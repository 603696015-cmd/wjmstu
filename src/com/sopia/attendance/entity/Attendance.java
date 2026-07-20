package com.sopia.attendance.entity;

import java.sql.Timestamp;

public class Attendance {
	
	private int id;
	private int userid;
	private Timestamp worktime;
	private Timestamp outworktime;
	private String[] weekdaytime;//1 == 星期六，2 == 星期天，1,2 == 星期六+星期天
	private String holidays;//节假日
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Timestamp getWorktime() {
		return worktime;
	}
	public void setWorktime(Timestamp worktime) {
		this.worktime = worktime;
	}
	public Timestamp getOutworktime() {
		return outworktime;
	}
	public void setOutworktime(Timestamp outworktime) {
		this.outworktime = outworktime;
	}
	public String[] getWeekdaytime() {
		return weekdaytime;
	}
	public void setWeekdaytime(String[] weekdaytime) {
		this.weekdaytime = weekdaytime;
	}
	public String getHolidays() {
		return holidays;
	}
	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}
	
	
	

}
