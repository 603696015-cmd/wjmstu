package com.sopia.knowledgeManage.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

/**
 * 知识类别树
 * @author Administrator
 *
 */
public class KnowledgeTree extends ElNode{
	private int id;
	private int parentid;
	private int lid;
	private int rid;
	private String name;
	private String description;
	private List<ELUser> opusers;
	private String bh;
	private List<KnowledgeTree> child;
	private int status;
	private int classCount;
	public KnowledgeTree(){
	}
	public KnowledgeTree(int id){
		this.id = id;
	}
	public KnowledgeTree(int id,String name){
		this.id = id;
		this.name = name;
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
	public List<ELUser> getOpusers() {
		return opusers;
	}
	public void setOpusers(List<ELUser> opusers) {
		this.opusers = opusers;
	}
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public List<KnowledgeTree> getChild() {
		return child;
	}
	public void setChild(List<KnowledgeTree> child) {
		this.child = child;
	}
	
	
	
}
