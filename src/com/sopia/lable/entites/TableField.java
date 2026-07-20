package com.sopia.lable.entites;

import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 字段信息表
 * @author Administrator
 *
 */
public class TableField {
	@Expose
	private int id;
	@Expose
	private  String  name;//中文名称
	@Expose
	private  String  value;//字段值
	@Expose
	private  String  fieldName;//字段名称（数据库中字段名）
	@Expose
	private  String  original_fieldName;//字段原来的名称		主要是针对统计字段
	@Expose
	private  String  tableName;//所属表名
	@Expose
	private  String  fieldType;//字段数据类型
	@Expose
	private  String  defaultvalue;//默认值
	
	
	public String getTableAndField(){
		
		return  this.tableName+"."+this.fieldName;
	}
	public String getTableField(){
		
		return  this.tableName+""+this.fieldName;
	}
	
	public String getOriginal_fieldName() {
		return original_fieldName;
	}
	public void setOriginal_fieldName(String original_fieldName) {
		this.original_fieldName = original_fieldName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDefaultvalue() {
		return defaultvalue;
	}
	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getName() {
		
		return this.name==null? fieldName:name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
	

}
