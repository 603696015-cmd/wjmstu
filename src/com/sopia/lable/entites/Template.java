package com.sopia.lable.entites;

import com.google.gson.annotations.Expose;

public class Template {
	@Expose
	private   String   trueName;
	@Expose
	private   String   name;
	@Expose
	private   int     	id;
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
