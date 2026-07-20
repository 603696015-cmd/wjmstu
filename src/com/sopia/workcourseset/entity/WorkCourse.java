package com.sopia.workcourseset.entity;

public class WorkCourse {
	private int id;
	private int work_type;
	private int work_course_id;
	private int isuse;
	private String work_course_name;
	private String description;  
	
	private String work_anniu_name;//页面对应按钮名称
	
	private String coursename;//课程名称
	private String workTypeName;//职业分类名称
	
	
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getWorkTypeName() {
		return workTypeName;
	}
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWork_type() {
		return work_type;
	}
	public void setWork_type(int work_type) {
		this.work_type = work_type;
	}
	public int getWork_course_id() {
		return work_course_id;
	}
	public void setWork_course_id(int work_course_id) {
		this.work_course_id = work_course_id;
	}
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
	public String getWork_course_name() {
		return work_course_name;
	}
	public void setWork_course_name(String work_course_name) {
		this.work_course_name = work_course_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWork_anniu_name() {
		return work_anniu_name;
	}
	public void setWork_anniu_name(String work_anniu_name) {
		this.work_anniu_name = work_anniu_name;
	}

}
