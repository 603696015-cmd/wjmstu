package com.sopia.questionman.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public class ExamPaperLib extends ElNode{
	private int id ;
	private String name ;
	private String description;
	private List<ExamPaperLib> child;
	private List<ELUser> opusers;
	private List<ELUser> useusers; 
	
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
	public ExamPaperLib() {
	}
	public ExamPaperLib(int id) {
	this.id = id;
	}
	public  ExamPaperLib(int id,String name) {
		this.id = id;
		this.name = name;
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
	public List<ExamPaperLib> getChild() {
		return child;
	}
	public void setChild(List<ExamPaperLib> child) {
		this.child = child;
	}
	
	
}
