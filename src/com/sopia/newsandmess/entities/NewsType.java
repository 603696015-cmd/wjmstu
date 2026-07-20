package com.sopia.newsandmess.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public class NewsType extends ElNode{
	private String name;
	private String description;
	private List<NewsType> child;
	private List<News> newses;
	private Integer isshared; //是否共享节点
	private List<ELUser> opusers;//管理权限用户
	private List<ELUser> useusers; //使用权限用户
	
	public List<News> getNewses() {
		return newses;
	}
	public void setNewses(List<News> newses) {
		this.newses = newses;
	}
	public List<NewsType> getChild() {
		return child;
	}
	public void setChild(List<NewsType> child) {
		this.child = child;
	}
	public NewsType() {
	}
	public NewsType(int id,String name) {
		super(id);
		this.name = name;
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
