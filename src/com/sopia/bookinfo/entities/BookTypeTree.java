package com.sopia.bookinfo.entities;

import java.util.List;

import com.sopia.common.ElNode;

public class BookTypeTree extends ElNode {
	
	private String name;
	private String description;
	private Integer isshared;	//add by luocw 是否为共享节点，0不是共享节点，1是共享节点。默认为0
	private List<BookTypeTree> child;
	private String bh;
	
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public BookTypeTree(int id){
		super(id);
	}
	public BookTypeTree(int id,String name){
		super(id);
		this.name = name;
	}
	public BookTypeTree(){
		
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
	public Integer getIsshared() {
		return isshared;
	}
	public void setIsshared(Integer isshared) {
		this.isshared = isshared;
	}
	public List<BookTypeTree> getChild() {
		return child;
	}
	public void setChild(List<BookTypeTree> child) {
		this.child = child;
	}
	
	
	

}
