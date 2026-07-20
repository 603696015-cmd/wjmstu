package com.sopia.duman.entities;

import java.util.List;

public class ElGroup {
	private int id;
	private String name;
	private String description;
	private List<ELUser> users;
	private int gtype;
	private String gtypeName;
	public ElGroup() {
	
	}
	public ElGroup(int id) {
		this.id = id;
	}
	public ElGroup(int id,String name){
		this.id = id;
		this.name= name;
		
	}
	public String getGtypeName() {
		if (gtype == 1) {

			return "特殊用户组一";
		}
		if (gtype == 2) {

			return "特殊用户组二";
		}
		if (gtype == 3) {

			return "一般用户组";
		}

		return gtypeName;
	}

	public void setGtypeName(String gtypeName) {
		this.gtypeName = gtypeName;
	}

	public int getGtype() {
		return gtype;
	}

	public void setGtype(int gtype) {
		this.gtype = gtype;
	}

	// TODO 工作组的管理
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

	public List<ELUser> getUsers() {
		return users;
	}

	public void setUsers(List<ELUser> users) {
		this.users = users;
	}

}
