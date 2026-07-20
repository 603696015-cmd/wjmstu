package com.sopia.bookman.entities;

import java.util.List;

import com.sopia.common.ElNode;

public class BookType extends ElNode{
	private String name;
	private String description;
	private List<BookType> child;
	public List<BookType> getChild() {
		return child;
	}
	public void setChild(List<BookType> child) {
		this.child = child;
	}
	public BookType() {
	}
	public BookType(int id,String name) {
		super(id);
		this.name = name;
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
	
}
