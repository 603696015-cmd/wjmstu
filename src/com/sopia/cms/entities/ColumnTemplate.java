package com.sopia.cms.entities;

public class ColumnTemplate {
	private int id;
	private int tmpId;
	private String tmpName; 
	private int columnId;
	private String columnName;
	private String tmpJspTmp;
	private String columnType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTmpId() {
		return tmpId;
	}
	public void setTmpId(int tmpId) {
		this.tmpId = tmpId;
	}
	public String getTmpName() {
		return tmpName;
	}
	public void setTmpName(String tmpName) {
		this.tmpName = tmpName;
	}
	public int getColumnId() {
		return columnId;
	}
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTmpJspTmp() {
		return tmpJspTmp;
	}
	public void setTmpJspTmp(String tmpJspTmp) {
		this.tmpJspTmp = tmpJspTmp;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
}
