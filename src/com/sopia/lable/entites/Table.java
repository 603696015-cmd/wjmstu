package com.sopia.lable.entites;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Table {
	@Expose
	private String  name;//表中文名称
	@Expose
	private String  tableName;//表名
	@Expose
	private List<TableField>  field;//字段
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<TableField> getField() {
		return field;
	}
	public void setField(List<TableField> field) {
		this.field = field;
	}
	
	
	
	

}
