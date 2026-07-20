package com.sopia.duman.entities;

import java.util.List;

import com.sopia.studyman.entities.MyExamPaper;

public class BaseDatat {
	private int id;
	private int typeid;
	private String basevalue;
	private String remack;
	private int sortid;
	private int sortManner;//排序方式
	private int selected;//0未选 1 选中
	private String bh;
	private BaseDataType baseType;
	private int ykuserCount;
	private int qkuserCount;
	private int userCount_jg;
	private int userCount;
	private float avg;
	private String ratio;
	private List<MyExamPaper > myexampapers;
	private ELUser elUser;
	
	
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public List<MyExamPaper> getMyexampapers() {
		return myexampapers;
	}
	public void setMyexampapers(List<MyExamPaper> myexampapers) {
		this.myexampapers = myexampapers;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	public BaseDataType getBaseType() {
		return baseType;
	}
	public void setBaseType(BaseDataType baseType) {
		this.baseType = baseType;
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
			typeName="工种";
			break;
		case 2:
			typeName="职务";
			break;
		case 3:
			typeName="职级";
			break;
		case 4:
			typeName="岗位";
			break;
		case 5:
			typeName="地市";
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
	
	public BaseDatat() {
	}
	public BaseDatat(int id, String basevalue) {
		this.id = id;
		this.basevalue = basevalue;
	}
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public int getYkuserCount() {
		return ykuserCount;
	}
	public void setYkuserCount(int ykuserCount) {
		this.ykuserCount = ykuserCount;
	}
	public int getQkuserCount() {
		return qkuserCount;
	}
	public void setQkuserCount(int qkuserCount) {
		this.qkuserCount = qkuserCount;
	}
	public int getUserCount_jg() {
		return userCount_jg;
	}
	public void setUserCount_jg(int userCount_jg) {
		this.userCount_jg = userCount_jg;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	
}
