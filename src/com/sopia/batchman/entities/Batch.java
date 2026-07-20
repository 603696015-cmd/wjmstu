package com.sopia.batchman.entities;

import java.util.List;

import com.sopia.classman.entities.ElClass;
import com.sopia.duman.entities.ELUser;


public class Batch {
	private int id;
	private String name;
	private String description;
	private List<ElClass> classes;
	private ELUser creater;
	private int userCount;
	private int userPassedCount;
	public float getPassper() {
		java.text.DecimalFormat myformat=new java.text.DecimalFormat( " #0.00 " );  
		return Float.parseFloat(myformat.format(userCount==0?0:userPassedCount*100.0f/userCount));
	}
	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getUserPassedCount() {
		return userPassedCount;
	}

	public void setUserPassedCount(int userPassedCount) {
		this.userPassedCount = userPassedCount;
	}

	public ELUser getCreater() {
		return creater;
	}

	public void setCreater(ELUser creater) {
		this.creater = creater;
	}

	public List<ElClass> getClasses() {
		return classes;
	}

	public void setClasses(List<ElClass> classes) {
		this.classes = classes;
	}

	public Batch() {
		super();
	}

	public Batch(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
