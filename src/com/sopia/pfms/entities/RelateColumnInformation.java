package com.sopia.pfms.entities;

import com.google.gson.annotations.Expose;

public class RelateColumnInformation {
	@Expose
	private int id;//数据来源表中id
	@Expose
	private String tableName;
	@Expose
	private String columnName;
	@Expose
	private String relateColumnName;//数据来源表名
	@Expose
	private String relateTableName;//数据来源表列
	@Expose
	private String relateColumnValue;//数据来源表值
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRelateColumnValue() {
		return relateColumnValue;
	}
	public void setRelateColumnValue(String relateColumnValue) {
		this.relateColumnValue = relateColumnValue;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getRelateColumnName() {
		return relateColumnName;
	}
	public void setRelateColumnName(String relateColumnName) {
		this.relateColumnName = relateColumnName;
	}
	public String getRelateTableName() {
		return relateTableName;
	}
	public void setRelateTableName(String relateTableName) {
		this.relateTableName = relateTableName;
	}
	
	

}
