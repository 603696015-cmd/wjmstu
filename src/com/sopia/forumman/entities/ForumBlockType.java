package com.sopia.forumman.entities;

import java.util.List;


public class ForumBlockType {
	private int id;
	private String name;
	private String description;
	private int sortid;
	private List<ForumBlock> fblocks;
	public ForumBlockType(int id){
		this.id =id;
		
	}
	public List<ForumBlock> getFblocks() {
		return fblocks;
	}
	public void setFblocks(List<ForumBlock> fblocks) {
		this.fblocks = fblocks;
	}
	public ForumBlockType() {
	}
	public ForumBlockType(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
