package com.sopia.schedule.entities;

import com.google.gson.annotations.Expose;

/**
 * 添加和修改字段时，备注
 * @author Administrator
 *
 */
public class TagsMark {
	@Expose
	private String tablename;//表名
	@Expose
	private String columnname;//列名
	@Expose
	private String relates;//备注相关ids
	private String relates_info;//备注相关title
	
	public String getRelates_info() {
		return relates_info;
	}
	public void setRelates_info(String relates_info) {
		this.relates_info = relates_info;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getRelates() {
		return relates;
	}
	public void setRelates(String relates) {
		this.relates = relates;
	}
	
	
}
