package com.sopia.schedule.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sopia.BaseAction;
import com.sopia.common.ElException;
import com.sopia.lable.common.LableCommon;
import com.sopia.lable.dao.CustomLableDao;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.Table;
import com.sopia.lable.entites.TableField;
import com.sopia.schedule.CommonCustom;
import com.sopia.schedule.ScheduleUtil;
import com.sopia.schedule.dao.CustomReportsDao;
import com.sopia.schedule.dao.ModuleManageDao;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.entities.CustomReport;
import com.sopia.schedule.entities.CustomReportJSZ;
import com.sopia.schedule.entities.ModuleManage;

public class CustomReportsAjaxAction extends BaseAction{
	private  String 					lableName;
	private  String 					tableName;
	private  CustomReport				customReport;
	private  CustomLableDao 			customLableDao;
	private  CustomReportsDao			customReportsDao;
	private  ModuleManageDao			moduleManageDao;
	private  String						orderstr;
	private  String						fieldName;
	private  String						jisuanzuname;
	private  String 					value;
	private  int 						type;
	private  CustomReportJSZ			customReportJSZ;
	private  int 						formatnumber;
	private  int 						checkvalue;
	private int id;

	public String checklablename() throws ElException, IOException{
		
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		if(LableCommon.check_lableName(lableName, tableName, "name")){
			out.print("名字已存在请重新输入");	
		}else{
			out.print("该名称可以使用");
		}
		
		out.flush();
		out.close();

		return null;
		
	}
	
	/**
	 * 判断需要添加的计算组名称是否已经存在
	 * @return
	 * @throws ElException
	 */
	public String checkJSZNameIsExist() throws ElException,IOException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		if(customReportsDao.checkJSZNameIsExist(value,customReport.getId())){
			out.print("名字已存在请重新输入");	
		}else{
			out.print("该名称可以使用");
		}
		
		out.flush();
		out.close();

		return null;
	}
	
	/**
	 * 将所选字段信息加入到 标签表中
	 * @return
	 * @throws ElException
	 * @throws IOException
	 */
	public String fieldaddlable() throws ElException, IOException{
		
		
		
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		//1如果接受到的字符串不是null或者“”；
		if(customReport.getTablefield()!=null&&!customReport.getTablefield().equals("")){
			//2得到原标签中的表及其字段信息，通过表名及标签表名称
			CustomReport  cr=customReportsDao.lable_getlableby(tableName,lableName);
			if(cr!=null){
				if(cr.getTableinfo()!=null&&!cr.getTableinfo().equals("")){
					customReport.setTableinfo(CommonCustom.getnewtablestr(cr.getTableinfo(),customReport.getTableinfo()));
				}else{
					customReport.setTableinfo(customReport.getTableinfo()+"-");
				}
				if(cr.getTablefield()!=null&&!cr.getTablefield().equals("")){
				//如果原标签字段不为空，将得到的两个字符串和前台传过来的字符串信息进行比较得出需要添加的表信息和字段信息
					customReport.setTablefield(CommonCustom.getnewfieldstr(cr.getTablefield(),customReport.getTablefield()));
//					customReport=CommonCustom.getnewfieldstr(customReport,cr);
				}
				else{
					customReport.setTablefield(customReport.getTablefield());
				}
				customReportsDao.lable_updlabletableinfoAndField(tableName,lableName,customReport);
				
			}
			
		}
		
		//返回新的标签信息
		String jsonsnewField=null;
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();		
		jsonsnewField = gson.toJson(customLableDao.lable_getlabletableandfield(lableName,tableName));
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsonsnewField\":" + jsonsnewField + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 修改排序
	 * @return
	 * @throws ElException 
	 * @throws IOException 
	 */
	public String updateorder() throws ElException, IOException{

		//修改设置
		//得到原排序设置信息
		String oldorder=customLableDao.lable_getorderStr(tableName,customReport.getName());
		//得到新排序信息
		oldorder = LableCommon.lablecommon_neworderstr(oldorder,orderstr);
		customLableDao.lable_updlableorder(oldorder,tableName,customReport.getName());
		//得到新的SQL语句
		customReport=customReportsDao.lable_getlablesqllable(tableName, customReport);
		String orderstr=customReport.getGroupby()==null?"":customReport.getGroupby();
		if(!orderstr.equals("") && orderstr.indexOf(".")!=-1){
			//如果存在字段信息，则去掉最后一个逗号
			orderstr=LableCommon.lablecommon_getorder(customReport.getGroupby(),","," ");
		}
		String sqlstr="";
		sqlstr=LableCommon.lablecommon_getsql(customReport.getTableinfo(),customReport.getTablefield(),customReport.getSqlcondition(),orderstr,customReport.getPageSize());
		
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		out.print(sqlstr);	
		out.flush();
		out.close();
		return null;
		
		
		
	}
	
	/**
	 * 得到可用于排序字段
	 * @throws ElException 
	 */
	public String getallorderfield() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		
		//查出该标签所查询的所有表的，所有字段
		CustomReport  cr=customReportsDao.lable_getlableby(tableName,customReport.getName());
		//用得到的所有表得出所有字段tableField
		String arr[]= cr.getTableinfo().split("-");
		//查出表信息
		List<Table>  tableList =customReportsDao.getTableByArr(arr);
		 
		for (Table t : tableList) {
			
			t.setField(customReportsDao.getFieldByTableName(t.getTableName()));
		}
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		String jsonsorderField=null;
		jsonsorderField = gson.toJson(tableList);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsonsorderField\":" + jsonsorderField + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 添加排序字段
	 * @return
	 * @throws ElException
	 */
	public String addorderfield() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		//修改设置
		//得到原排序设置信息
		String oldorder=customLableDao.lable_getorderStr(tableName,customReport.getName());
		if(oldorder!=null&&!oldorder.equals("")){//判断原设置信息是否为空
			//在判断加入的字段是否已存在
			if(oldorder.indexOf(fieldName)==-1){
				oldorder = LableCommon.lablecommon_neworderfield(oldorder,fieldName,true);
				//用新信息修改排序信息
				customLableDao.lable_updlableorder(oldorder,tableName,customReport.getName());
			}
		}
		//用修改后的信息，得出新的字段信息
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		List<TableField>  list = new ArrayList<TableField>();
		if(oldorder.indexOf(".")!=-1){
			//如果能找到“.”说明有排序字段
			//剔除排序字符，得到纯排序字段
			//将得到的排序字段分割“，”转化成数组
			String arr[] = LableCommon.lablecommon_delepaixu(oldorder,",").split(",");
			
			//用该数组得到字段中文名称等信息
			list=customLableDao.lable_getTableFieldByField(arr);
			
		}
		String jsonsorderField=null;
		jsonsorderField = gson.toJson(list);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsonsorderField\":" + jsonsorderField + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	/**
	 * 添加分组字段
	 * @return
	 * @throws ElException
	 */
	public String addgroupfield() throws ElException{
		//用修改后的信息，得出新的字段信息
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		List<TableField>  list = new ArrayList<TableField>();
		String groupfields = "";
		
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		//修改设置
		//得到原排序设置信息
		String oldgroup=customLableDao.lable_getgroupStr(tableName,customReport.getName());
		if(oldgroup!=null&&!oldgroup.equals("")){//判断原设置信息是否为空
			//在判断加入的字段是否已存在
			if(oldgroup.indexOf(fieldName)==-1){
				oldgroup = LableCommon.lablecommon_neworderfield(oldgroup,fieldName,true);
				//用新信息修改分组信息
				customLableDao.lable_updlablegroup(oldgroup,tableName,customReport.getName());
				groupfields = oldgroup;
			}
		}else{
			customLableDao.lable_updlablegroup(fieldName,tableName,customReport.getName());
			groupfields = fieldName;
		}
		
		if(groupfields.indexOf(".")!=-1){
			//如果能找到“.”说明有排序字段
			//剔除排序字符，得到纯排序字段
			//将得到的排序字段分割“，”转化成数组
			String arr[] = groupfields.split(",");
			
			//用该数组得到字段中文名称等信息
			list=customLableDao.lable_getTableFieldByField(arr);
			
		}
		String jsongroupField=null;
		jsongroupField = gson.toJson(list);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsongroupField\":" + jsongroupField + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	/**
	 * 得到修改后的sql语句
	 * @return
	 * @throws ElException
	 * @throws IOException
	 */
	public String getnewsql() throws ElException, IOException{
		customReport=customReportsDao.lable_getlablesqllable(tableName, customReport);
		List<CustomReportJSZ> customReportJSZList = customReportsDao.queryCustomReport_jisuanzu_list_byid(customReport.getId());//计算组
		String orderstr=customReport.getGroupby();
		String groupstr = customReport.getGroupby_();
		if(customReport.getGroupby().indexOf(".")!=-1){
			//如果存在字段信息，则去掉最后一个逗号
			orderstr=LableCommon.lablecommon_getorder(customReport.getGroupby(),","," ");
		}
		String sqlstr="";
		if(customReport.getPageSize() ==0){
			sqlstr = ScheduleUtil.lablecommon_getsql(customReport.getTableinfo(),customReport.getTablefield(),customReport.getSqlcondition(),orderstr,groupstr,customReport.getPageSize(),customReportJSZList);
		}else{
			sqlstr = ScheduleUtil.lablecommon_pagegetsql(customReport.getTableinfo(),customReport.getTablefield(),customReport.getSqlcondition(),orderstr,groupstr,customReport.getPageSize(),customReportJSZList);
		}
		
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		out.print(sqlstr);	
		out.flush();
		out.close();
		return null;
		
		
	}
	
	public String updorderfielddele() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		//修改设置
		//得到原排序设置信息
		String oldorder=customLableDao.lable_getorderStr(tableName,customReport.getName());
		if(oldorder!=null&&!oldorder.equals("")){//判断原设置信息是否为空
			//在判断删除的字段是否已存在
			if(oldorder.indexOf(fieldName)!=-1&&!fieldName.equals("")){
				oldorder = LableCommon.lablecommon_neworderfield(oldorder,fieldName,false);
				//用新信息修改排序信息
				customLableDao.lable_updlableorder(oldorder,tableName,customReport.getName());
			}
		}
		//用修改后的信息，得出新的字段信息
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		List<TableField>  list = new ArrayList<TableField>();
		if(oldorder!=null&&!oldorder.equals("")){
			if(oldorder.indexOf(".")!=-1){
				//如果能找到“.”说明有排序字段
				//剔除排序字符，得到纯排序字段
				//将得到的排序字段分割“，”转化成数组
				String arr[] = LableCommon.lablecommon_delepaixu(oldorder,",").split(",");
				//用该数组得到字段中文名称等信息
				list=customReportsDao.getTableFieldByField(arr);
				
			}
		}
		String jsonsorderField=null;
		jsonsorderField = gson.toJson(list);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsonsorderField\":" + jsonsorderField + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	public String updgroupfielddele() throws ElException{
		//用修改后的信息，得出新的字段信息
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		List<TableField>  list = new ArrayList<TableField>();
		
		
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		//修改设置
		//得到原排序设置信息
		String oldgroup=customLableDao.lable_getgroupStr(tableName,customReport.getName());
		if(oldgroup!=null&&!oldgroup.equals("")){//判断原设置信息是否为空
			//在判断删除的字段是否已存在
			if(oldgroup.indexOf(fieldName)!=-1&&!fieldName.equals("")){
				oldgroup = LableCommon.lablecommon_newgroupfield(oldgroup,fieldName,false);
				//用新信息修改排序信息
				customLableDao.lable_updlablegroup(oldgroup,tableName,customReport.getName());
			}
		}
		
		if(oldgroup!=null&&!oldgroup.equals("")){
			if(oldgroup.indexOf(".")!=-1){
				//如果能找到“.”说明有排序字段
				//剔除排序字符，得到纯排序字段
				//将得到的排序字段分割“，”转化成数组
				String arr[] = oldgroup.split(",");
				//用该数组得到字段中文名称等信息
				list=customReportsDao.getTableFieldByField(arr);
				
			}
		}
		
		String jsongroupField=null;
		jsongroupField = gson.toJson(list);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsongroupField\":" + jsongroupField + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	public String showzijisuan() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		List<CustomReportJSZ>  list = new ArrayList<CustomReportJSZ>();
		list=customReportsDao.showzijisuan(customReport.getId(),0);
		String jsonsorderField=null;
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		jsonsorderField = gson.toJson(list);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsonsorderField\":" + jsonsorderField + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getLableById() throws ElException{
		customReport =  customReportsDao.queryCustomReportById(customReport.getId());
		
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			out.print(customReport.getGroupby_()==null?"":customReport.getGroupby_());	
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String insertjisuanzu() throws ElException{

		customReportsDao.insertjisuanzu(customReport.getId(),jisuanzuname,type);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//表内统计
	public String formula_insert() throws ElException{
		customReportsDao.updateCustomReport_jisuanzu_by_columnname(customReportJSZ.getColumnname(),value,type,formatnumber,checkvalue,0,"");
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//表间统计
	public String formula_insert_biaojian() throws ElException{
		customReportsDao.updateCustomReport_jisuanzu_by_columnname(customReportJSZ.getColumnname(),value,type,-1,checkvalue,customReportJSZ.getRelatetype(),customReportJSZ.getRelatecolumnname());
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String list_modulemanage() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		List<ModuleManage>  list = new ArrayList<ModuleManage>();
		list=moduleManageDao.select_mymodule(null,0,0);
		String json=null;
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		json = gson.toJson(list);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"json\":" + json + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String list_field_by_tablename() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		List<TableField>  list = new ArrayList<TableField>();
		list = customReportsDao.getFieldByTableName(tableName);
		String json=null;
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		json = gson.toJson(list);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"json\":" + json + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	统计字段重新排序
	public String changeJSZId() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		
		customReportsDao.changeJSZId(customReport.getId(),value);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String deleteJSZById() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		
		customReportsDao.deleteJSZById(id);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String select_relatetype2() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		List<CustomReportJSZ>  list = new ArrayList<CustomReportJSZ>();
		list=customReportsDao.showzijisuan(customReport.getId(),2);
		String json=null;
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		json = gson.toJson(list);
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"json\":" + json + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public CustomReport getCustomReport() {
		return customReport;
	}

	public void setCustomReport(CustomReport customReport) {
		this.customReport = customReport;
	}

	public CustomLableDao getCustomLableDao() {
		return customLableDao;
	}

	public void setCustomLableDao(CustomLableDao customLableDao) {
		this.customLableDao = customLableDao;
	}
	public String getOrderstr() {
		return orderstr;
	}

	public void setOrderstr(String orderstr) {
		this.orderstr = orderstr;
	}

	public CustomReportsDao getCustomReportsDao() {
		return customReportsDao;
	}

	public void setCustomReportsDao(CustomReportsDao customReportsDao) {
		this.customReportsDao = customReportsDao;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getJisuanzuname() {
		return jisuanzuname;
	}

	public void setJisuanzuname(String jisuanzuname) {
		this.jisuanzuname = jisuanzuname;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public CustomReportJSZ getCustomReportJSZ() {
		return customReportJSZ;
	}

	public void setCustomReportJSZ(CustomReportJSZ customReportJSZ) {
		this.customReportJSZ = customReportJSZ;
	}

	public int getFormatnumber() {
		return formatnumber;
	}

	public void setFormatnumber(int formatnumber) {
		this.formatnumber = formatnumber;
	}

	public int getCheckvalue() {
		return checkvalue;
	}

	public void setCheckvalue(int checkvalue) {
		this.checkvalue = checkvalue;
	}

	public ModuleManageDao getModuleManageDao() {
		return moduleManageDao;
	}

	public void setModuleManageDao(ModuleManageDao moduleManageDao) {
		this.moduleManageDao = moduleManageDao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	
	

}
