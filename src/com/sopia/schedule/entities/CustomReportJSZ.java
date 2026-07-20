package com.sopia.schedule.entities;

import java.util.List;

import com.google.gson.annotations.Expose;

public class CustomReportJSZ {
	@Expose
	private int id;
	@Expose
	private int customreportid;
	@Expose
	private String columnname;
	@Expose
	private String formula;
	@Expose
	private int type;
	@Expose
	private int formatnumber;//保留几位小数
	@Expose
	private int viewjindutiao;//显示普通数字还是进度条
	@Expose
	private int showview;//是否显示查看按钮
	@Expose
	private int relatetype;
	@Expose
	private String relatecolumnname;
	@Expose
	private int orderid;
	@Expose
	private List<CustomReportJSZ> childrenCustomReportJSZList;
	
	public List<CustomReportJSZ> getChildrenCustomReportJSZList() {
		return childrenCustomReportJSZList;
	}
	public void setChildrenCustomReportJSZList(
			List<CustomReportJSZ> childrenCustomReportJSZList) {
		this.childrenCustomReportJSZList = childrenCustomReportJSZList;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRelatecolumnname() {
		return relatecolumnname;
	}
	public void setRelatecolumnname(String relatecolumnname) {
		this.relatecolumnname = relatecolumnname;
	}
	public int getRelatetype() {
		return relatetype;
	}
	public void setRelatetype(int relatetype) {
		this.relatetype = relatetype;
	}
	public int getShowview() {
		return showview;
	}
	public void setShowview(int showview) {
		this.showview = showview;
	}
	public int getViewjindutiao() {
		return viewjindutiao;
	}
	public void setViewjindutiao(int viewjindutiao) {
		this.viewjindutiao = viewjindutiao;
	}
	public int getFormatnumber() {
		return formatnumber;
	}
	public void setFormatnumber(int formatnumber) {
		this.formatnumber = formatnumber;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCustomreportid() {
		return customreportid;
	}
	public void setCustomreportid(int customreportid) {
		this.customreportid = customreportid;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	

}
