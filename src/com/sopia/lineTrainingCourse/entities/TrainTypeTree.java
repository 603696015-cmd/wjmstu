package com.sopia.lineTrainingCourse.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public class TrainTypeTree extends ElNode{
	private String name;
	private String description;
	private List<TrainTypeTree> child;
	private List<LineTrainingCourse> courses;
	private Integer isshared; //是否共享节点
	private List<ELUser> opusers;//管理权限用户
	private List<ELUser> useusers; //使用权限用户
	
	public TrainTypeTree(){
		
	}
	
	public TrainTypeTree(int id,String name){
		super(id);
		try{
			this.name = name;
		}catch(Exception e){
			e.printStackTrace();
		}
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
	public List<TrainTypeTree> getChild() {
		return child;
	}
	public void setChild(List<TrainTypeTree> child) {
		this.child = child;
	}
	public List<LineTrainingCourse> getCourses() {
		return courses;
	}
	public void setCourses(List<LineTrainingCourse> courses) {
		this.courses = courses;
	}
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
