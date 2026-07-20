package com.sopia.schedule.entities;

import com.google.gson.annotations.Expose;

public class Tb_calculate {
	@Expose
	private String tableName;//表名
	@Expose
	private String columnName;//列名
	@Expose
	private String relate_columnName_calculate;//用于计算的列名
	@Expose
	private String relate_tableName_calculate;//用于计算的表名
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
	public String getRelate_columnName_calculate() {
		return relate_columnName_calculate;
	}
	public void setRelate_columnName_calculate(String relate_columnName_calculate) {
		this.relate_columnName_calculate = relate_columnName_calculate;
	}
	public String getRelate_tableName_calculate() {
		return relate_tableName_calculate;
	}
	public void setRelate_tableName_calculate(String relate_tableName_calculate) {
		this.relate_tableName_calculate = relate_tableName_calculate;
	}
	
	

}
