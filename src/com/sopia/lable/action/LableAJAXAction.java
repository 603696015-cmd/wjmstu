package com.sopia.lable.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.lable.common.LableCommon;
import com.sopia.lable.dao.CustomLableDao;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.Mode;
import com.sopia.lable.entites.Search;
import com.sopia.lable.entites.SearchLable;
import com.sopia.lable.entites.Table;
import com.sopia.lable.entites.TableField;
import com.sopia.statman.entities.MyClass;

public class LableAJAXAction {
	private  String 					lableName;
	private  String 					tableName;
	private  CustomLableDao 			customLableDao;
	private  String						fieldName;
	private  Lable						lable;
	private  String						delestr;
	private  String						orderstr;
	private  String						orderfield;
	private  int						type;						
	private  int						page;
	private  int						hidid;
	private	  String					searchvalue;
	private  String						pagesql;
	
	
	public String getPagesql() {
		return pagesql;
	}

	public void setPagesql(String pagesql) {
		this.pagesql = pagesql;
	}

	public String getSearchvalue() {
		return searchvalue;
	}

	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}

	public int getHidid() {
		return hidid;
	}

	public void setHidid(int hidid) {
		this.hidid = hidid;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOrderfield() {
		return orderfield;
	}

	public void setOrderfield(String orderfield) {
		this.orderfield = orderfield;
	}

	public String getOrderstr() {
		return orderstr;
	}

	public void setOrderstr(String orderstr) {
		this.orderstr = orderstr;
	}

	public String getDelestr() {
		return delestr;
	}

	public void setDelestr(String delestr) {
		this.delestr = delestr;
	}

	public Lable getLable() {
		return lable;
	}

	public void setLable(Lable lable) {
		this.lable = lable;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public CustomLableDao getCustomLableDao() {
		return customLableDao;
	}

	public void setCustomLableDao(CustomLableDao customLableDao) {
		this.customLableDao = customLableDao;
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

	public String lableajax_checklablename() throws ElException, IOException{
			
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
	 * 得到对应表的 对应字段信息
	 * @return
	 * @throws ElException
	 * @throws IOException
	 */
	public String lableajax_getFieldByTableName() throws ElException, IOException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		String jsonsField=null;
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();		
		jsonsField = gson.toJson(customLableDao.lable_getFieldByTableName(tableName));
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsonsField\":" + jsonsField + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	/**
	 * 将所选字段信息加入到 标签表中
	 * @return
	 * @throws ElException
	 * @throws IOException
	 */
	public String lableajax_Fieldaddlable() throws ElException, IOException{
		
		
		
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		//1如果接受到的字符串不是null或者“”；
		if(lable.getFieldstr()!=null&&!lable.getFieldstr().equals("")){
			//2得到原标签中的表及其字段信息，通过表名及标签表名称
			Lable  tlable=customLableDao.lable_getlableby(tableName,lableName);
			if(tlable!=null){
				if(!tlable.getFieldstr().equals("")){
				//如果原标签字段不为空，将得到的两个字符串和前台传过来的字符串信息进行比较得出需要添加的表信息和字段信息
					lable=LableCommon.getnewfieldstr(lable,tlable);
				}
				else{
					lable.setTablestr(lable.getTablestr()+"-");
				}
				customLableDao.lable_updlabletableinfoAndField(tableName,lableName,lable);
				
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
	public String  lableajax_tableFielddele() throws ElException{

		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		//获取原表及字段信息字符串
		Lable  tlable=customLableDao.lable_getlableby(tableName,lableName);
		//判断删除信息来自于表名删除，还是字段删除
		if(delestr.indexOf(".")!=-1){
			//为字段删除
			//用要删除的表名信息和 原表及字段信息字符串， 得出新的lable信息
			tlable=LableCommon.lablecommon_deleField(tlable,delestr);
			
			
			
		}else{
			//否则为表删除
			tlable=LableCommon.lablecommon_deletableinfo(delestr,tlable);
			
		}
		//用新的信息修改该标签信息
		customLableDao.lable_delelableTableInfo(tableName,tlable,lableName);
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
	 * 显示可用字段
	 * @return
	 * @throws ElException
	 */
	public String lableajax_showfile() throws ElException{

		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
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
	 * 显示可删除排序字段
	 * @return
	 * @throws ElException
	 */
	public String lableajax_showorderfile() throws ElException{

		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		String jsonsorderField=null;
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		String oldorder=customLableDao.lable_getorderStr(tableName,lableName);
		List<TableField>  list = new ArrayList<TableField>();
		if(oldorder.indexOf(".")!=-1){
			//如果能找到“.”说明有排序字段
			//剔除排序字符，得到纯排序字段
			//将得到的排序字段分割“，”转化成数组
			String arr[] = LableCommon.lablecommon_delepaixu(oldorder,",").split(",");
			//用该数组得到字段中文名称等信息
			list=customLableDao.lable_getTableFieldByField(arr);
			
		}
		
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
	 * 修改排序
	 * @return
	 * @throws ElException 
	 * @throws IOException 
	 */
	public String lableajax_updorder() throws ElException, IOException{

		//修改设置
		//得到原排序设置信息
		String oldorder=customLableDao.lable_getorderStr(tableName,lable.getName());
		//得到新排序信息
		oldorder = LableCommon.lablecommon_neworderstr(oldorder,orderstr);
		customLableDao.lable_updlableorder(oldorder,tableName,lable.getName());
		//得到新的SQL语句
		lable=customLableDao.lable_getlablesqllable(tableName, lable);
		String orderstr=lable.getOrder();
		if(lable.getOrder().indexOf(".")!=-1){
			//如果存在字段信息，则去掉最后一个逗号
			orderstr=LableCommon.lablecommon_getorder(lable.getOrder(),","," ");
		}
		String sqlstr="";
		if(type==1){
			sqlstr=LableCommon.lablecommon_getsql(lable.getTablestr(),lable.getFieldstr(),lable.getSqlCondition(),orderstr,lable.getPageSize());
		}else if(type==2){
			sqlstr=LableCommon.lablecommon_pagegetsql(lable.getTablestr(),lable.getFieldstr(),lable.getSqlCondition(),orderstr,lable.getPageSize());
			
		}
		
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		out.print(sqlstr);	
		out.flush();
		out.close();
		return null;
		
		
		
	}
	/**
	 * 得到修改后的sql语句
	 * @return
	 * @throws ElException
	 * @throws IOException
	 */
	public String lableajax_getnewsql() throws ElException, IOException{
		lable=customLableDao.lable_getlablesqllable(tableName, lable);
		String orderstr=lable.getOrder();
		if(lable.getOrder().indexOf(".")!=-1){
			//如果存在字段信息，则去掉最后一个逗号
			orderstr=LableCommon.lablecommon_getorder(lable.getOrder(),","," ");
		}
		String sqlstr="";
		if(type==1){
			
			sqlstr=LableCommon.lablecommon_getsql(lable.getTablestr(),lable.getFieldstr(),lable.getSqlCondition(),orderstr,lable.getPageSize());
		}
		else if(type==2){
			
			sqlstr=LableCommon.lablecommon_pagegetsql(lable.getTablestr(),lable.getFieldstr(),lable.getSqlCondition(),orderstr,lable.getPageSize());
		}
		
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
	public String lableajax_getallorderfield() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		
		//查出该标签所查询的所有表的，所有字段
		Lable  tlable=customLableDao.lable_getlableby(tableName,lable.getName());
		//用得到的所有表得出所有字段tableField
		String arr[]= tlable.getTablestr().split("-");
		//查出表信息
		List<Table>  tableList =customLableDao.Lable_getTableByArr(arr);
		 
		for (Table t : tableList) {
			
			t.setField(customLableDao.lable_getFieldByTableName(t.getTableName()));
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
	public String lableajax_updorderfield() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		//修改设置
		//得到原排序设置信息
		String oldorder=customLableDao.lable_getorderStr(tableName,lable.getName());
		if(oldorder!=null&&!oldorder.equals("")){//判断原设置信息是否为空
			//在判断加入的字段是否已存在
			if(oldorder.indexOf(fieldName)==-1){
				oldorder = LableCommon.lablecommon_neworderfield(oldorder,fieldName,true);
				//用新信息修改排序信息
				customLableDao.lable_updlableorder(oldorder,tableName,lable.getName());
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
	 * 删除排序字段
	 * @return
	 * @throws ElException
	 */
	public String lableajax_updorderfielddele() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		
		resp.setContentType("text/plain;charset=UTF-8");
		//修改设置
		//得到原排序设置信息
		String oldorder=customLableDao.lable_getorderStr(tableName,lable.getName());
		if(oldorder!=null&&!oldorder.equals("")){//判断原设置信息是否为空
			//在判断删除的字段是否已存在
			if(oldorder.indexOf(fieldName)!=-1&&!fieldName.equals("")){
				oldorder = LableCommon.lablecommon_neworderfield(oldorder,fieldName,false);
				//用新信息修改排序信息
				customLableDao.lable_updlableorder(oldorder,tableName,lable.getName());
			}
		}
		//用修改后的信息，得出新的字段信息
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		List<TableField>  list = new ArrayList<TableField>();
		if(oldorder!=null&&!oldorder.equals("")){//判断原设置信息是否为空
			if(oldorder.indexOf(".")!=-1){
				//如果能找到“.”说明有排序字段
				//剔除排序字符，得到纯排序字段
				//将得到的排序字段分割“，”转化成数组
				String arr[] = LableCommon.lablecommon_delepaixu(oldorder,",").split(",");
				//用该数组得到字段中文名称等信息
				list=customLableDao.lable_getTableFieldByField(arr);
				
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
	public String  lableajax_pagelable() throws ElException, IOException{
		
		
		//得到sql语句
		lable=customLableDao.lable_getlablesqllablesql("lable_circulation",lableName);
		//计算出来分页信息
		int page1 = (page-1)*lable.getPageSize()+1;
		int page2 =page*lable.getPageSize();
		String arr[] = lable.getFieldstr().split("-");
		List<TableField>  list=customLableDao.lable_getTableFieldByField(arr);
		List<Map> listMap =  customLableDao.getpageMap(list, pagesql,page2,page1);
		//得到总页数信息
		String sqlcount=LableCommon.lablecommon_pagegetcountsql(pagesql);
		int count =customLableDao.lable_getsqlsagecount(sqlcount);
		int zongpage=0;
		if(count%lable.getPageSize()==0){
			zongpage=count/lable.getPageSize();
		}else{
			zongpage=count/lable.getPageSize()+1;
		}
		StringBuilder str=new StringBuilder();
		str.append("<tr><td valign='top'><table width='100%'>");
		for (Map map : listMap) {
			str.append(LableCommon.lablecommon_getlable(map,lable.getLable()));
		}
		str.append("</table></td></tr>" );
		str.append("<tr><td valign='baseline'><table width='100%'><tr><td>");
		if (page > 1) {
			str.append("<a style='cursor: hand' href='javascript:lablepage("+hidid+","
					+ 1 + ")'>[首页]</a>");
			str.append("<a style='cursor: hand' href='javascript:lablepage("+hidid+","
					+ (page - 1) + ")'>[上一页]</a>");
		} else {
			
			str.append("[首页]"); 
			str.append("[上一页]");
		}
		if (zongpage > 0) {
			str.append("<select  onchange='lablepage("+hidid+",this.options[this.selectedIndex].value)'>");
			for (int i = 1; i <=zongpage; i++) {
				if(i==page)
					str.append("<option value='" + i + "' selected='selected'>" + i 
						+ "</option>");
				else{
					str.append("<option value='" + i + "'>" + i
							+ "</option>");
				}

			}
			str.append("</select> ");
		}
		if ( page <zongpage) {
			str.append("<a style='cursor: hand' href='javascript:lablepage("+hidid+","
					+(page+1) + ")'>[下一页]</a>");
			str.append("<a style='cursor: hand' href='javascript:lablepage("+hidid+","
					+ zongpage + ")'>[末页]</a>");
		} else {
			str.append("[下一页]");
			str.append("[末页]");
		}
		
		str.append("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>" + count
				+ "<b>条</b></span>");
		
		str.append("<input type='hidden' id='"+hidid+"'  value='"+lableName+"' title='"+pagesql+"'/>");

		str.append("</td></tr></table></td></tr>");
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		out.print(str);	
		out.flush();
		out.close();
		return null;
	}
	/**
	 * 得到所选表的所有字段进行搜索框设置
	 * @throws ElException 
	 */
	public String lableajax_getallsearchfield() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		//查出该标签所查询的所有表的，所有字段
		Lable  tlable=customLableDao.lable_getlableby(tableName,lableName);
		//用得到的所有表得出所有字段tableField
		String arr[]= tlable.getTablestr().split("-");
		//查出表信息
		List<Table>  tableList =customLableDao.Lable_getTableByArr(arr);
		 
		for (Table t : tableList) {
			
			t.setField(customLableDao.lable_getFieldByTableName(t.getTableName()));
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
	 * 得到搜索框的ID
	 * @return
	 * @throws ElException 
	 */
	
	public String lableajax_getallsearchvalue() throws ElException{
		//得到标签
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		SearchLable searchLable1=customLableDao.lable_getlablesearchlable("lable_search",lableName);
		List<Search> s=LableCommon.lablecommon_getsearchvalue(searchLable1.getSearchlable());
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		String jsonsorderField=null;
		jsonsorderField = gson.toJson(s);		
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
	public String  lableajax_pagesearchlable() throws ElException, IOException{
		//得到sql语句
		lable=customLableDao.lable_getlablesqllablesql("lable_search",lableName);
		//计算出来分页信息
		int page1 = (page-1)*lable.getPageSize()+1;
		int page2 =page*lable.getPageSize();
		String arr[] = lable.getFieldstr().split("-");
		
		List<TableField>  list=customLableDao.lable_getTableFieldByField(arr);
		//得到加上搜索条件后的sql语句
		String sql=LableCommon.lablecommon_getsearchsql(lable.getSql(),searchvalue,lable.getType(),lableName);
		//对得到的SQL语句进行重新组合，加入searchvalue 的搜索条件
		
		List<Map> listMap =  customLableDao.getpageMap(list, sql,page2,page1);
		//得到总页数信息
		String sqlcount=LableCommon.lablecommon_pagegetcountsql(sql);
		int count =customLableDao.lable_getsqlsagecount(sqlcount);
		int zongpage=0;
		if(count%lable.getPageSize()==0){
			zongpage=count/lable.getPageSize();
		}else{
			zongpage=count/lable.getPageSize()+1;
		}
		StringBuilder str=new StringBuilder();
		str.append("<tr><td valign='top'><table width='100%'>");
		if(listMap!=null){
		for (Map map : listMap) {
			str.append(LableCommon.lablecommon_getlable(map,lable.getLable()));
		}
		}
		str.append("</table></td></tr>" );
		str.append("<tr><td valign='baseline'><table width='100%'><tr><td>");
		if (page > 1) {
			str.append("<a style='cursor: hand' href='javascript:searchlablepage("+hidid+","
					+ 1 + ")'>[首页]</a>");
			str.append("<a style='cursor: hand' href='javascript:searchlablepage("+hidid+","
					+ (page - 1) + ")'>[上一页]</a>");
		} else {
			
			str.append("[首页]"); 
			str.append("[上一页]");
		}
		if (zongpage > 0) {
			str.append("<select  onchange='searchlablepage("+hidid+",this.options[this.selectedIndex].value)'>");
			for (int i = 1; i <=zongpage; i++) {
				if(i==page)
					str.append("<option value='" + i + "' selected='selected'>" + i 
						+ "</option>");
				else{
					str.append("<option value='" + i + "'>" + i
							+ "</option>");
				}

			}
			str.append("</select> ");
		}
		if ( page <zongpage) {
			str.append("<a style='cursor: hand' href='javascript:searchlablepage("+hidid+","
					+(page+1) + ")'>[下一页]</a>");
			str.append("<a style='cursor: hand' href='javascript:searchlablepage("+hidid+","
					+ zongpage + ")'>[末页]</a>");
		} else {
			str.append("[下一页]");
			str.append("[末页]");
		}
		
		str.append("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>" + count
				+ "<b>条</b></span>");
		
		str.append("<input type='hidden' id='"+hidid+"'  value='"+lableName+"' />");

		str.append("</td></tr></table></td></tr>");
		
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		out.print(str);	
		out.flush();
		out.close();
		return null;
	}
}
