package com.sopia.wordman.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public class Word extends ElNode{
//	private int id;
	private String name;
	private String description;
//	private int parentid;
	private List<Word> child;
	private List<ELUser> opusers;
	private int courseid;
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public List<ELUser> getOpusers() {
		return opusers;
	}
	public void setOpusers(List<ELUser> opusers) {
		this.opusers = opusers;
	}
	public Word( int id ,String name) {
		super(id);
		this.name = name;
	}
	public Word() {
	}
	public Word(int id) {
		super(id);
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
	public List<Word> getChild() {
		return child;
	}
	public void setChild(List<Word> child) {
		this.child = child;
	}
	
	
}
