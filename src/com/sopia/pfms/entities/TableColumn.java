package com.sopia.pfms.entities;

public class TableColumn {
	private int id;
	private String tableName;//表名
	private String columnName;//列名称
	private String comments;//列注释
	private String dateType;//列类型
	private String dateLength;//列长度
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getDateLength() {
		return dateLength;
	}
	public void setDateLength(String dateLength) {
		this.dateLength = dateLength;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	

}
