package com.sopia.peice.entities;

import com.sopia.courseman.entities.Course;

public class Peice {
	private Course	course;//改价格所关联的课程
	private float  	coursenowPrice;//现价、会员价
	private float  	courseoldPrice;//原价、普通价
	private int   	status;//价格审核状态
	private int     userid;//审核者ID
	private String  userName;//审核者姓名
	private float   chajia;//差价
	
	
	public float getChajia() {
		return this.courseoldPrice-this.coursenowPrice;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public float getCoursenowPrice() {
		return coursenowPrice;
	}
	public void setCoursenowPrice(float coursenowPrice) {
		this.coursenowPrice = coursenowPrice;
	}
	public float getCourseoldPrice() {
		return courseoldPrice;
	}
	public void setCourseoldPrice(float courseoldPrice) {
		this.courseoldPrice = courseoldPrice;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
