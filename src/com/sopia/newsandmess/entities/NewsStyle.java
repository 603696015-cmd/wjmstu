package com.sopia.newsandmess.entities;

import java.util.List;

public class NewsStyle {
	private int id;
	private String name;
	private String description;
	private List<NewsStyle> listnewsstyle;
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
	public List<NewsStyle> getListnewsstyle() {
		return listnewsstyle;
	}
	public void setListnewsstyle(List<NewsStyle> listnewsstyle) {
		this.listnewsstyle = listnewsstyle;
	}
	
	

	
}
