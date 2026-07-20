package com.sopia.pfms.entities;

import com.google.gson.annotations.Expose;

public class IC_column_qiuji_qiuhe {
	@Expose
	private String tableName;
	@Expose
	private String columnName;
	@Expose
	private int is_qiuhe;
	@Expose
	private int is_qiuji;
	@Expose
	private int is_zuoweihe;
	@Expose
	private int is_zuoweiji;
	@Expose
	private String qiujiColumnName;
	@Expose
	private String qiuheColumnName;
	@Expose
	private int from_entity;
	
	public int getFrom_entity() {
		return from_entity;
	}
	public void setFrom_entity(int from_entity) {
		this.from_entity = from_entity;
	}
	public int getIs_zuoweihe() {
		return is_zuoweihe;
	}
	public void setIs_zuoweihe(int is_zuoweihe) {
		this.is_zuoweihe = is_zuoweihe;
	}
	public int getIs_zuoweiji() {
		return is_zuoweiji;
	}
	public void setIs_zuoweiji(int is_zuoweiji) {
		this.is_zuoweiji = is_zuoweiji;
	}
	public String getQiujiColumnName() {
		return qiujiColumnName;
	}
	public void setQiujiColumnName(String qiujiColumnName) {
		this.qiujiColumnName = qiujiColumnName;
	}
	public String getQiuheColumnName() {
		return qiuheColumnName;
	}
	public void setQiuheColumnName(String qiuheColumnName) {
		this.qiuheColumnName = qiuheColumnName;
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
	public int getIs_qiuhe() {
		return is_qiuhe;
	}
	public void setIs_qiuhe(int is_qiuhe) {
		this.is_qiuhe = is_qiuhe;
	}
	public int getIs_qiuji() {
		return is_qiuji;
	}
	public void setIs_qiuji(int is_qiuji) {
		this.is_qiuji = is_qiuji;
	}
	
	

}
