package com.sopia.userDemo.entities;
/**
 * 保存不同字段类型不同页面中的范围表
 * @author Administrator
 *
 */
public class ELUserPage {
	private String column_name;		//列名
	private String range;			//范围
	private String default_select;	//默认选择
	private int modify;				//是否可修改
	private int pageid;				//页面id
	private int need;				//是否必须填写
	
	public ELUserPage(){}
	public ELUserPage(String column_name,String range,String default_select,int modify,int pageid,int need){
		this.column_name = column_name;
		this.range = range;
		this.default_select = default_select;
		this.modify = modify;
		this.pageid = pageid;
		this.need = need ;
	}
	
	public int getNeed() {
		return need;
	}
	public void setNeed(int need) {
		this.need = need;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getDefault_select() {
		return default_select;
	}
	public void setDefault_select(String default_select) {
		this.default_select = default_select;
	}
	public int getModify() {
		return modify;
	}
	public void setModify(int modify) {
		this.modify = modify;
	}
	public int getPageid() {
		return pageid;
	}
	public void setPageid(int pageid) {
		this.pageid = pageid;
	}
	
	

}
