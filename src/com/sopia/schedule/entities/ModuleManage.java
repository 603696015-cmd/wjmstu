package com.sopia.schedule.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ModuleManage
{
	@Expose
	private int id;
	@Expose
	private String modulename;
	@Expose
	private String tablename;
	@Expose
	private String remark;
	@Expose
	private Timestamp createtime;
	@Expose
	private int tableType;//表类型	1普遍表	2过程表	3结果表
	@Expose
	private String relatetablename;//针对过程表，对应哪个结果表
	@Expose
	private int can_add_other_func;//是否能添加新的功能
	@Expose
	private String demourl;//url文件路劲
	@Expose
	private String democss;//css文件路径
	@Expose
	private int ondemo;//是否启用模板
	private List<String> list_my_charge_search = new ArrayList<String>();
	@Expose
	private String fromtablename;//针对数据自动读取
	@Expose
	private String shujuname;//数据名称
	@Expose
	private int is_enabled;//是否开启自定义审核
	@Expose
	private int openvisitor;//是否向游客开放
	
	
	public int getOpenvisitor() {
		return openvisitor;
	}
	public void setOpenvisitor(int openvisitor) {
		this.openvisitor = openvisitor;
	}
	public int getIs_enabled() {
		return is_enabled;
	}
	public void setIs_enabled(int is_enabled) {
		this.is_enabled = is_enabled;
	}
	public String getShujuname() {
		return shujuname;
	}
	public void setShujuname(String shujuname) {
		this.shujuname = shujuname;
	}
	public int getCan_add_other_func() {
		return can_add_other_func;
	}
	public void setCan_add_other_func(int can_add_other_func) {
		this.can_add_other_func = can_add_other_func;
	}
	public String getFromtablename() {
		return fromtablename;
	}
	public void setFromtablename(String fromtablename) {
		this.fromtablename = fromtablename;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getDemourl() {
		return demourl;
	}
	public void setDemourl(String demourl) {
		this.demourl = demourl;
	}
	
	public String getDemocss() {
		return democss;
	}
	public void setDemocss(String democss) {
		this.democss = democss;
	}
	public int getOndemo() {
		return ondemo;
	}
	public void setOndemo(int ondemo) {
		this.ondemo = ondemo;
	}
	public String getRelatetablename() {
		return relatetablename;
	}
	public void setRelatetablename(String relatetablename) {
		this.relatetablename = relatetablename;
	}
	public int getTableType() {
		return tableType;
	}
	public void setTableType(int tableType) {
		this.tableType = tableType;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getModulename()
	{
		return modulename;
	}
	public void setModulename(String modulename)
	{
		this.modulename = modulename;
	}
	public String getTablename()
	{
		return tablename;
	}
	public void setTablename(String tablename)
	{
		this.tablename = tablename;
	}
	public List<String> getList_my_charge_search()
	{
		return list_my_charge_search;
	}
	public void setList_my_charge_search(List<String> list_my_charge_search)
	{
		this.list_my_charge_search = list_my_charge_search;
	}
	
	
	
}
