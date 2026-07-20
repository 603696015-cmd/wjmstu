package com.sopia.lable.entites;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public class LableTree extends ElNode{
	@Expose
	private int id;
	private int parentid;
	private int lid;
	private int rid;
	@Expose
	private String name;
	private String description;
	private List<LableTree> child;
	private String bh;
	private List<ELUser> opusers;
	private int status;
	private int classCount;
	
	
	public LableTree(){}
	public LableTree(int id){
		this.id = id;
	}
	public LableTree(int id,String name){
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
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
	
	public List<LableTree> getChild() {
		return child;
	}
	public void setChild(List<LableTree> child) {
		this.child = child;
	}
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public List<ELUser> getOpusers() {
		return opusers;
	}
	public void setOpusers(List<ELUser> opusers) {
		this.opusers = opusers;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getClassCount() {
		return classCount;
	}
	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}
	
	
}
