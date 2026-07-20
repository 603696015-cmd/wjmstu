package com.sopia.courseman.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;


public class CourseType extends ElNode {
	//private int id ;
	private String name;
	private String description;
//	private   CourseType parent;
	private List<CourseType> child;
	private List<Course> courses;
	private String mainimg;
	private Integer isshared;//add by jiahaijinag 是否为共享节点，0不是共享节点，1是共享节点。默认为0
	private List<ELUser> opusers;//管理权限用户
	private List<ELUser> useusers; //使用权限用户
	
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	public String getMainimg() {
		return mainimg==null?"elfrontimages/lj2.gif":mainimg;
	}
	public void setMainimg(String mainimg) {
		this.mainimg = mainimg;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<CourseType> getChild() {
		return child;
	}
	public void setChild(List<CourseType> child) {
		this.child = child;
	}
	public CourseType( int id ,String name) {
		super(id);
		this.name = name;
	}
	public CourseType() {
	}
	public CourseType(int id) {
		super(id);
	}
	
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public CourseType getParent() {
//		return parent;
//	}
//	public void setParent(CourseType parent) {
//		this.parent = parent;
//	}
	public Integer getIsshared() {
		return isshared;
	}
	public void setIsshared(Integer isshared) {
		this.isshared = isshared;
	}
	public List<ELUser> getOpusers() {
		return opusers;
	}
	public void setOpusers(List<ELUser> opusers) {
		this.opusers = opusers;
	}
	public List<ELUser> getUseusers() {
		return useusers;
	}
	public void setUseusers(List<ELUser> useusers) {
		this.useusers = useusers;
	}
}
