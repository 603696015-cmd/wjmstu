package com.sopia.schedule.entities;

import java.util.List;

import com.sopia.lable.entites.TableField;

public class CustomReport {
	private int id;
	private String name;
	private String tableinfo;
	private String tablefield;
	private int pageSize;
	private String sqlcondition;
	private String sql;
	private String groupby;//≈≈–Ú
	private String groupby_;//∑÷◊È
	private String lable;
	private String  orderstatus;
	private List<TableField>  field;
	private int showtree;
	private int showsearch;
	private String searchhtml;
	private String searchhtmlfield;
	private int searchtype;
	private String resultPage;
	
	public String getOrdername(){
		
		 if("desc".equals(orderstatus))
			 return 	"Ωµ–Ú";
		 if("asc".equals(orderstatus))
			 return   "…˝–Ú";
		 else{
			 return  "Œﬁ…Ë÷√";
		 }
	}

	public int getSearchtype() {
		return searchtype;
	}

	public void setSearchtype(int searchtype) {
		this.searchtype = searchtype;
	}

	public String getSearchhtmlfield() {
		return searchhtmlfield;
	}

	public void setSearchhtmlfield(String searchhtmlfield) {
		this.searchhtmlfield = searchhtmlfield;
	}

	public String getSearchhtml() {
		return searchhtml;
	}

	public void setSearchhtml(String searchhtml) {
		this.searchhtml = searchhtml;
	}

	public String getGroupby_() {
		return groupby_;
	}

	public void setGroupby_(String groupby_) {
		this.groupby_ = groupby_;
	}

	public String getResultPage() {
		return resultPage;
	}

	public void setResultPage(String resultPage) {
		this.resultPage = resultPage;
	}

	public int getShowsearch() {
		return showsearch;
	}

	public void setShowsearch(int showsearch) {
		this.showsearch = showsearch;
	}

	public int getShowtree() {
		return showtree;
	}

	public void setShowtree(int showtree) {
		this.showtree = showtree;
	}

	public List<TableField> getField() {
		return field;
	}

	public void setField(List<TableField> field) {
		this.field = field;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSqlcondition() {
		return sqlcondition;
	}

	public void setSqlcondition(String sqlcondition) {
		this.sqlcondition = sqlcondition;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getGroupby() {
		return groupby;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableinfo() {
		return tableinfo;
	}

	public void setTableinfo(String tableinfo) {
		this.tableinfo = tableinfo;
	}

	public String getTablefield() {
		return tablefield;
	}

	public void setTablefield(String tablefield) {
		this.tablefield = tablefield;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	
	

}
