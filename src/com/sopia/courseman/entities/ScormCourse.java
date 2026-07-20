package com.sopia.courseman.entities;

import java.util.List;


public class ScormCourse {
	private String courseid;
	private String userid;
	private String classid;
	private String launch;
	private String scoid;
	private String title;
	private String courseComplete;
	private String control;
	private String lessonStatus;
	private String type;
	private String nowScoid;
	private String navitype;
	private boolean haspreSco;//否有上一节
	private boolean hasnextSco;//否有下一节
	private List<ScormCourse> scoList;
	public List<ScormCourse> getScoList() {
		return scoList;
	}
	public void setScoList(List<ScormCourse> scoList) {
		this.scoList = scoList;
	}
	public boolean isHaspreSco() {
		return haspreSco;
	}
	public void setHaspreSco(boolean haspreSco) {
		this.haspreSco = haspreSco;
	}
	public boolean isHasnextSco() {
		return hasnextSco;
	}
	public void setHasnextSco(boolean hasnextSco) {
		this.hasnextSco = hasnextSco;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getLaunch() {
		return launch;
	}
	public void setLaunch(String launch) {
		this.launch = launch;
	}
	public String getScoid() {
		return scoid;
	}
	public void setScoid(String scoid) {
		this.scoid = scoid;
	}
	public String getCourseComplete() {
		return courseComplete;
	}
	public void setCourseComplete(String courseComplete) {
		this.courseComplete = courseComplete;
	}
	public String getLessonStatus() {
		return lessonStatus;
	}
	public String getLessonStatusName() {
		if(lessonStatus==null)
			return "未知状态";
		if("passed".equals(lessonStatus.toLowerCase())){
			return "通过";
		}
		if("completed".equals(lessonStatus.toLowerCase())){
			return "已完成";
		}
		if("browsed".equals(lessonStatus.toLowerCase())){
			return "浏览过";
		}
		if("incomplete".equals(lessonStatus.toLowerCase())){
			return "未完成";
		}
		if("failed".equals(lessonStatus.toLowerCase())){
			return "失败";
		}
		if("not attempted".equals(lessonStatus.toLowerCase())){
			return "未开始";
		}
		return "未知状态";
	}
	public void setLessonStatus(String lessonStatus) {
		this.lessonStatus = lessonStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNowScoid() {
		return nowScoid;
	}
	public void setNowScoid(String nowScoid) {
		this.nowScoid = nowScoid;
	}
	public String getNavitype() {
		return navitype;
	}
	public void setNavitype(String navitype) {
		this.navitype = navitype;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	 
}
