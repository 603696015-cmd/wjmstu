package com.sopia.cache.modules.domin;

import java.util.List;

/**
 * 一级部门json
 * @author zahj
 *
 */
public class DepartmentJson {
	private int id;
	
	private String name;
	
	private String description;
	
	private List<DepartmentJson> child;

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

	public List<DepartmentJson> getChild() {
		return child;
	}

	public void setChild(List<DepartmentJson> child) {
		this.child = child;
	}
	
	

}
