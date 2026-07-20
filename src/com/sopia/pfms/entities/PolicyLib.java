package com.sopia.pfms.entities;

import java.util.List; 

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public class PolicyLib extends ElNode {

	private String name;
	private String description;
	private List<PolicyLib> child;
	private List<Policy> newses;
	private Integer isshared; //是否共享节点
	private List<ELUser> opusers;//管理权限用户
	private List<ELUser> useusers; //使用权限用户
	 
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
	public List<PolicyLib> getChild() {
		return child;
	}
	public void setChild(List<PolicyLib> child) {
		this.child = child;
	}
	public List<Policy> getNewses() {
		return newses;
	}
	public void setNewses(List<Policy> newses) {
		this.newses = newses;
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
	public PolicyLib() {
	}
	public PolicyLib(int id,String name) {
		super(id);
		try{
			this.name = name;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
