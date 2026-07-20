package com.sopia.schedule.entities;

import com.google.gson.annotations.Expose;

public class CurrentUser {
	@Expose
	private int userid;
	private int user_add;
	private int user_update;
	private int user_view;
	@Expose
	private String name;		//用户名
	@Expose
	private String depname;		//部门名称
	private String tablename;	
	@Expose
	private String zhiwuname;	//职务名称
	@Expose
	private String dishiname;	//地市名称
	
	
	public String getZhiwuname() {
		return zhiwuname;
	}
	public void setZhiwuname(String zhiwuname) {
		this.zhiwuname = zhiwuname;
	}
	public String getDishiname() {
		return dishiname;
	}
	public void setDishiname(String dishiname) {
		this.dishiname = dishiname;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getUser_add() {
		return user_add;
	}
	public void setUser_add(int user_add) {
		this.user_add = user_add;
	}
	public int getUser_update() {
		return user_update;
	}
	public void setUser_update(int user_update) {
		this.user_update = user_update;
	}
	public int getUser_view() {
		return user_view;
	}
	public void setUser_view(int user_view) {
		this.user_view = user_view;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
	}
	
	

}
