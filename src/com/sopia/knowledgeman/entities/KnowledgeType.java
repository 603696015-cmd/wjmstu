package com.sopia.knowledgeman.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

public class KnowledgeType extends ElNode{
	private int id;
	private String name;
	private String description;
//	private KnowledgeType parent;
	private List<KnowledgeType> child;
	private List<Department> deps;
	private ELUser manager;
	private Integer isshared; //是否共享节点
	private List<ELUser> opusers;//管理权限用户
	private List<ELUser> useusers; //使用权限用户
	public ELUser getManager() {
		return manager;
	}
	public void setManager(ELUser manager) {
		this.manager = manager;
	}
	public List<Department> getDeps() {
		return deps;
	}
	public void setDeps(List<Department> deps) {
		this.deps = deps;
	}
	public KnowledgeType() {
	}
	public KnowledgeType(int id,String name){
		this.id = id;
		this.name = name;
	}
	public KnowledgeType(int id){
		this.id= id;
	}
	public List<KnowledgeType> getChild() {
		return child;
	}
	public void setChild(List<KnowledgeType> child) {
		this.child = child;
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
//	public KnowledgeType getParent() {
//		return parent;
//	}
//	public void setParent(KnowledgeType parent) {
//		this.parent = parent;
//	}
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
