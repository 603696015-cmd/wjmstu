package com.sopia.classman.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public class ElClType extends ElNode {
	private String name;
	private String description;
	private Integer isshared;	//add by luocw 是否为共享节点，0不是共享节点，1是共享节点。默认为0
	private List<ELUser> opusers;//管理权限用户
	private List<ELUser> useusers; //使用权限用户
	private List<ElClType> child;
	public List<ElClType> getChild() {
		return child;
	}
	public void setChild(List<ElClType> child) {
		this.child = child;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ElClType() {
		super();
	}
	public ElClType(int id) {
		super(id);
	}
	public ElClType(int id,String name){
		super(id);
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
