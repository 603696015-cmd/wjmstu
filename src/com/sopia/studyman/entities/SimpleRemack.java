package com.sopia.studyman.entities;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;

public class SimpleRemack {
	private int id;
	private int type;//1.代表考场，2代表培训班
	private int typeid;//类型的id
	private ELUser creater;//备注填写人员
	private ELUser toUser;//发给的人员
	private Timestamp createtime;
	private String title;
	private String phone;
	private String content;
	private String userids;//存放发给人的id集合(逗号分隔)
	public String getUserids() {
		return userids;
	}
	public void setUserids(String userids) {
		this.userids = userids;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
	public ELUser getToUser() {
		return toUser;
	}
	public void setToUser(ELUser toUser) {
		this.toUser = toUser;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public SimpleRemack() {
	}
	public SimpleRemack(int id, String title) {
		this.id = id;
		this.title = title;
	}
}
