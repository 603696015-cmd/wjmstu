package com.sopia.questionman.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public class QuestionLib extends ElNode{
	private String name ;
//	private ELUser elUser;
	private String description;
	private List<QuestionLib> child;
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
	public QuestionLib() {
	}
	public QuestionLib(int id) {
	super(id);
	}
	public  QuestionLib(int id,String name) {
		super(id);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public ELUser getElUser() {
//		return elUser;
//	}
//	public void setElUser(ELUser elUser) {
//		this.elUser = elUser;
//	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<QuestionLib> getChild() {
		return child;
	}
	public void setChild(List<QuestionLib> child) {
		this.child = child;
	}
	
	
}
