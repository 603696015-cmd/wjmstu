package com.sopia.cms.entities;

public class Template {
	private int id;
	private String name;
	private String jspTmp;
	private String jsp;
	private String remark;
	
	private String tmpType;
	private int typeId;
	
	public String getTmpType() {
		return tmpType;
	}
	public void setTmpType(String tmpType) {
		this.tmpType = tmpType;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
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
	public String getJspTmp() {
		return jspTmp;
	}
	public void setJspTmp(String jspTmp) {
		this.jspTmp = jspTmp;
	}
	public String getJsp() {
		return jsp;
	}
	public void setJsp(String jsp) {
		this.jsp = jsp;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
