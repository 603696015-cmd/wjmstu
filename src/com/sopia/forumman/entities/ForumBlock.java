package com.sopia.forumman.entities;

import java.util.List;

import com.sopia.duman.entities.ELUser;

public class ForumBlock {
	private int id;
	private String title;
	private String description;
	private ELUser manager;
	private ForumBlockType fbtype;
	private int sortid;
	private String luntanjibies;
	
	private int isshared;	//add by luocw 是否为共享节点，0不是共享节点，1是共享节点。默认为0
	private List<ELUser> opusers;//管理权限用户
	private List<ELUser> useusers; //使用权限用户
	
	private int isChecked;
	
	
	
	public String getLuntanjibies() {
		return luntanjibies;
	}
	public void setLuntanjibies(String luntanjibies) {
		this.luntanjibies = luntanjibies;
	}
	public int getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}
	public ForumBlock() {
		// TODO Auto-generated constructor stub
	}
	public ForumBlock(int id,String title) {
		this.id = id;
		this.title = title;
	}
	public ForumBlock(int id) {
		this.id = id;
	}
	
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	public ForumBlockType getFbtype() {
		return fbtype;
	}
	public void setFbtype(ForumBlockType fbtype) {
		this.fbtype = fbtype;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ELUser getManager() {
		return manager;
	}
	public void setManager(ELUser manager) {
		this.manager = manager;
	}
	public int getIsshared() {
		return isshared;
	}
	public void setIsshared(int isshared) {
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
