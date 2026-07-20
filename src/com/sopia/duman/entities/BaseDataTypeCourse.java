package com.sopia.duman.entities;

public class BaseDataTypeCourse {
	private int id;
	private String name;
	private String remack;
	
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
	public String getRemack() {
		return remack;
	}
	public void setRemack(String remack) {
		this.remack = remack;
	}
	public BaseDataTypeCourse() {
	}
	public BaseDataTypeCourse(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public BaseDataTypeCourse(int id) {
		this.id = id;
	}
	
}
