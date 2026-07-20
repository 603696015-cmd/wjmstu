package com.sopia.userDemo.entities;

import com.google.gson.annotations.Expose;

/**
 * 校验js实体
 * @author Administrator
 *
 */
public class ELUserJs {
	@Expose
	private int id;					//编号
	@Expose
	private String name;			//名称
	@Expose
	private String description;		//描述
	@Expose
	private int team;				//是否有几中校验合起来
	@Expose
	private int llength;			//左边长度
	@Expose
	private int rlength;			//右边长度
	@Expose
	private int llength1;			//左边长度1
	@Expose
	private int rlength1;			//右边长度1
	@Expose
	private int llength2;			//左边长度2
	@Expose
	private int rlength2;			//右边长度2
	
	public ELUserJs(){}
	public ELUserJs(int id,String name,String description,int team,int llength,int rlength,int llength1,int rlength1,int llength2,int rlength2){
		this.id = id;
		this.name = name;
		this.description = description;
		this.team = team;
		this.llength = llength;
		this.rlength = rlength;
		this.llength1 = llength1;
		this.rlength1 = rlength1;
		this.llength2 = llength2;
		this.rlength2 = rlength2;
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
	public int getLlength() {
		return llength;
	}
	public void setLlength(int llength) {
		this.llength = llength;
	}
	public int getRlength() {
		return rlength;
	}
	public void setRlength(int rlength) {
		this.rlength = rlength;
	}
	public int getTeam() {
		return team;
	}
	public void setTeam(int team) {
		this.team = team;
	}
	public int getLlength1() {
		return llength1;
	}
	public void setLlength1(int llength1) {
		this.llength1 = llength1;
	}
	public int getRlength1() {
		return rlength1;
	}
	public void setRlength1(int rlength1) {
		this.rlength1 = rlength1;
	}
	public int getLlength2() {
		return llength2;
	}
	public void setLlength2(int llength2) {
		this.llength2 = llength2;
	}
	public int getRlength2() {
		return rlength2;
	}
	public void setRlength2(int rlength2) {
		this.rlength2 = rlength2;
	}
	
	
	
	
}
