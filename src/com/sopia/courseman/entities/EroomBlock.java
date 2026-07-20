package com.sopia.courseman.entities;

import java.util.List;

import com.sopia.duman.entities.ELUser;



public class EroomBlock {
	private int id;
	private String title;
	private String description; 
	private ELUser creater ;
	private int usersize;
	private int userSize;
	private ExamRoom eroom;
	private List<ErepBlock> erepblocks;
	public ExamRoom getEroom() {
		return eroom;
	}
	public void setEroom(ExamRoom eroom) {
		this.eroom = eroom;
	}
	public List<ErepBlock> getErepblocks() {
		return erepblocks;
	}
	public void setErepblocks(List<ErepBlock> erepblocks) {
		this.erepblocks = erepblocks;
	}
	public int getUserSize() {
		return userSize;
	}
	public void setUserSize(int userSize) {
		this.userSize = userSize;
	}
	public int getUsersize() {
		return usersize;
	}
	public void setUsersize(int usersize) {
		this.usersize = usersize;
	}
	public EroomBlock( int id ,String title) {
		this.id = id;
		this.title = title;
	}
	public EroomBlock() {
	}
	public EroomBlock(int id) {
		this.id = id;
	}
	public String getName() {
		return title;
	}
	public void setName(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
}
