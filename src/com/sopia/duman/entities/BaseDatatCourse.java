package com.sopia.duman.entities;

public class BaseDatatCourse {
	private int id;
	private int typeid;
	private String basevalue;
	private String remack;
	private int sortid;
	private int sortManner;//排序方式
	private int selected;//0未选 1 选中
	
	private BaseDataTypeCourse baseCourseType;
	 
	public BaseDataTypeCourse getBaseCourseType() {
		return baseCourseType;
	}
	public void setBaseCourseType(BaseDataTypeCourse baseCourseType) {
		this.baseCourseType = baseCourseType;
	}
	public int getSelected() {
		return selected;
	}
	public void setSelected(int selected) {
		this.selected = selected;
	}
	public int getSortManner() {
		return sortManner;
	}
	public void setSortManner(int sortManner) {
		this.sortManner = sortManner;
	}
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public String getTypeName() { 
		String typeName="";
		switch (typeid) {
		case 1:
			typeName="适合岗位";
			break;
		case 2:
			typeName="专业类别";
			break;
		case 3:
			typeName="专业级别";
			break;
		case 4:
			typeName="适合部门";
			break;
		case 5:
			typeName="内容类型";
			break;
		case 6:
			typeName="培训类别";
			break;
		case 7:
			typeName="适合学位";
			break;
		case 8:
			typeName="课程性质";
			break;
		default:
			typeName="未知";
			break;
		}
		return typeName;
	}
	public String getBasevalue() { 
		return basevalue;
	}
	public void setBasevalue(String basevalue) {
		this.basevalue = basevalue;
	}
	public String getRemack() {
		return remack;
	}
	public void setRemack(String remack) {
		this.remack = remack;
	}
	
	public BaseDatatCourse() {
	}
	public BaseDatatCourse(int id, String basevalue) {
		this.id = id;
		this.basevalue = basevalue;
	}
	
}
