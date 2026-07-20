package com.sopia.lable.entites;

import com.google.gson.annotations.Expose;

/**
 * ËÑË÷Ìõ¼þ¿ò
 * @author Administrator
 *
 */
public class Search {
	@Expose
	private  String  name ;
	@Expose
	private  String  type;
	@Expose
	private  String  typeName;
	@Expose
	private  String  searchName;
	private  int     aaaa;
	
	

	public int getAaaa() {
		return aaaa;
	}
	public void setAaaa(int aaaa) {
		this.aaaa = aaaa;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	

}
