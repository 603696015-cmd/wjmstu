package com.sopia.schedule;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspWriter;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.lable.common.LableCommon;
import com.sopia.lable.entites.TableField;
import com.sopia.schedule.dao.impl.CustomReportsDaoImpl;
import com.sopia.schedule.dao.impl.TagsDaoImpl;
import com.sopia.schedule.entities.CustomReportJSZ;

public class ScheduleUtil {
	public static String SEARCHLABLE_BEGIN = "@searchlable#";
	public static String SEARCHLABLE_END = "#searchlable^";
	public static String OTHER = "OTHER";
	public static TagsDaoImpl tagsDao = new TagsDaoImpl();
	
	public static int changeStringToInt(String fieldtype){
		int type = 0;
		if(fieldtype.equals("文本"))		type = 1;
		else if(fieldtype.equals("整数"))	type = 2;
		else if(fieldtype.equals("实数"))	type = 3;
		else if(fieldtype.equals("日期"))	type = 4;
		else if(fieldtype.equals("下拉选项"))	type = 5;
		else if(fieldtype.equals("相关字段"))		type = 6;
		else if(fieldtype.equals("相关负责人"))		type = 7;
		else if(fieldtype.equals("单选"))		type = 8;
		else if(fieldtype.equals("复选"))		type = 9;
		return type;
	}
	
	/**
	 * 将2012-08-03该为自定义格式  如yyyy年MM月dd日
	 * @param str
	 * @param format
	 * @return
	 */
	public static String dateFormat(String str,String format){
		String formatValue = "";
		if(str == null || str.equals("")){
			formatValue = "";
		}else{
			if(format == null || format.trim().equals("")){
				formatValue = str;
			}else{
				 SimpleDateFormat sdfx = new SimpleDateFormat("yyyy-MM-dd");
				 SimpleDateFormat sdfh = new SimpleDateFormat(format.trim());
				 try {
					formatValue = sdfh.format(sdfx.parse(str));
				} catch (ParseException e) {
					e.printStackTrace();
				} 
			}
		}
		return formatValue;
	}
	
	/**
	 * 将格式化字符串时间改为能保存在数据库中的时间字符串
	 * @param str
	 * @param format
	 * @return
	 */
	public static String formatToOra(String str,String format){
		String formatValue = "";
		if(str == null || str.equals("")){
			formatValue = "";
		}else{
			if(str.indexOf("-")!= -1){
				formatValue = str;
			}
			if(format == null || format.equals("")){
				formatValue = str;
			}else{
				 SimpleDateFormat sdfx = new SimpleDateFormat("yyyy-MM-dd");
				 SimpleDateFormat sdfh = new SimpleDateFormat(format.trim());
				 try {
					formatValue = sdfx.format(sdfh.parse(str));
				} catch (ParseException e) {
					e.printStackTrace();
				} 
			}
		}
		return formatValue;
	}
	
	
	public static String formatHeaderByLable(String str) throws ElException{
		String returnValue = "";
		int index=str.indexOf("@");
		String columnname = "";
		String[] tablename_columnname = null;
		
		while(index!=-1){//如果有@符号
			//取出@到^之间的字符串，并进行格式处理
			String str4=str.substring(str.indexOf("@",index)+1, str.indexOf("^",index));
			tablename_columnname = getTablenameAndColumnname(str4);
			columnname = new TagsDaoImpl().getColumnByColumnName(tablename_columnname[0], tablename_columnname[1]);
			returnValue += "<th style='cursor:hand' title="+tablename_columnname[1]+" onclick='combinesearch(this);'>"+columnname+"</th>";
			index=str.indexOf("@",index+1);
		}
		return returnValue;
	}
	
	public static String[] getTablenameAndColumnname(String str){
		String[] ary = null;
		if(str != null && !str.equals("")){
			ary = str.split(",");
		}
		return ary[0].split("\\.");
	}
	
	//获取计算组表头
	public static String lablecommon_getlable_jisuanze(List<CustomReportJSZ> customReportJSZList){
		String header_jisuanze = "";
		for(CustomReportJSZ customReportJSZ:customReportJSZList){
			header_jisuanze += "<th style='cursor:hand' title="+customReportJSZ.getFormula()+" onclick='combinesearch(this);'>"+customReportJSZ.getColumnname()+"</th>";
		}
		return header_jisuanze;
	}
	//获取计算组的值
	public static String lablecommon_getlable_jisuanze_value(Map<String,Object> map,List<CustomReportJSZ> customReportJSZList){
		String value = "";
		String jisuanzu = "";
		String format_jisuanzu = "";
		for(CustomReportJSZ customReportJSZ:customReportJSZList){
			if(map.get("customreportid").equals(String.valueOf(customReportJSZ.getCustomreportid()))){
				jisuanzu = customReportJSZ.getFormula();//计算组
				//GZJH_ZWPF+GZJH_LDPF+GZJH_BMPF
				//	装换为=》3.0+4.0+5.0
				if(jisuanzu != null && !jisuanzu.equals("")){
					if(OperatorUtil.isStringInOperator(jisuanzu,OperatorUtil.SMALL)){//包含count,min,max,sum,avg
//						value = String.valueOf(lablecommon_format_jisuanzu_with_operator(jisuanzu,map));
						format_jisuanzu = lablecommon_format_jisuanzu_with_operator(jisuanzu,map);
					}else{//不包含count,min,max,sum,avg
						format_jisuanzu = lablecommon_format_jisuanzu(jisuanzu,map);
					}
				}
				System.out.println(format_jisuanzu);
				
				if(format_jisuanzu != null && !format_jisuanzu.equals("")){
					if(customReportJSZ.getViewjindutiao() == 1){
						value = JisuanzuUtil.formatNumber(
								Double.parseDouble(
										JisuanzuUtil.computeString(format_jisuanzu)), 
										(customReportJSZ.getFormatnumber()==0|| customReportJSZ.getFormatnumber()==1)?
												JisuanzuUtil.DEFAULTFARMAT:JisuanzuUtil.getFormatNumber(customReportJSZ.getFormatnumber()+2));
						value = JisuanzuUtil.stringPointMoveRight(value,customReportJSZ.getFormatnumber());
						double d = Double.parseDouble(value);
						System.out.println(d);
						if(d>=100)	d = 100;
						value = "<table width='300px' border='0' cellspacing='1' ><tr><td><div   style='border: 1px dotted #FF6633;width:280px'><img height='14' src='images/jd.gif' width='"+d+"%'  /></div></td><td><center><span style='color:red;'>"+d+"%</span></center></td></tr></table>";
					}else{
						value = JisuanzuUtil.formatNumber(
								Double.parseDouble(
										JisuanzuUtil.computeString(format_jisuanzu)), 
										(customReportJSZ.getFormatnumber()==0|| customReportJSZ.getFormatnumber()==1)?
												JisuanzuUtil.DEFAULTFARMAT:JisuanzuUtil.getFormatNumber(customReportJSZ.getFormatnumber()));
					}
				}
			}
		}
		return value;
	}
	
	//将字段转换为真正的值返回//GZJH_ZWPF+GZJH_LDPF+GZJH_BMPF=>3.0+4.0+5.0
	public static String lablecommon_format_jisuanzu(String jisuanzu,Map<String,Object> map){
//		String s = "((GZJH_ZWPF+GZJH_LDPF+GZJH_BMPF)/1+3)/2";
		for(String field:map.keySet()){
			String field_without_table = field.substring(field.indexOf(".")+1,field.length());
			if(jisuanzu.indexOf(field_without_table) != -1){
				jisuanzu = jisuanzu.replace(field_without_table, ""+map.get(field));
			}
		}
		return jisuanzu;
	}
	
	//带操作符的字段装换//sum(GZJH_ZWPF)+sum(GZJH_LDPF)=>3.0/20.0    sum(GZJH_ZWPF)
	public static String lablecommon_format_jisuanzu_with_operator(String jisuanzu,Map<String,Object> map){
		String temp_field = "";
		for(String field:map.keySet()){//SUMGZJH.GZJH_ZWPF或者GZJH.GZJH_ZWPF
			if(OperatorUtil.isStringInOperator(field,OperatorUtil.BIG)){//带操作符
				//SUMGZJH.GZJH_ZWPF=>sum(GZJH_ZWPF)
				temp_field = OperatorUtil.getOperatorField_return(field);
				jisuanzu = jisuanzu.replace(temp_field, String.valueOf(map.get(field)==null?0:map.get(field)));
			}
		}
		return jisuanzu;
	}
	
	//显示一行数据
	public static String lablecommon_getlable(Map map,String str,List<CustomReportJSZ> customReportJSZList){
		StringBuilder sb = new StringBuilder(str);
		StringBuilder sb1 =new StringBuilder();
		int index=str.indexOf("@");
		int index2=0;
		while(index!=-1){//如果有@符号
			//取出@到^之间的字符串，并进行格式处理
			String str4=str.substring(str.indexOf("@",index)+1, str.indexOf("^",index));
			sb1.append(str.substring(index2,str.indexOf("@",index)));
			sb1.append(LableCommon.lablecommon_getlablevalue(map,str4));
			index2= str.indexOf("^",index)+1;
			index=str.indexOf("@",index+1);
		}
		
		String value = "";
		String jisuanzu = "";
		String format_jisuanzu = "";
		String format = "";//保留几位小数
		for(CustomReportJSZ customReportJSZ:customReportJSZList){
//			if(map.get("customreportid").equals(String.valueOf(customReportJSZ.getCustomreportid()))){
				jisuanzu = customReportJSZ.getFormula();//计算组
				//GZJH_ZWPF+GZJH_LDPF+GZJH_BMPF
				//	装换为=》3.0+4.0+5.0
				if(jisuanzu != null && !jisuanzu.equals("")){
					if(!customReportJSZ.getFormula().contains(".")){
						if(OperatorUtil.isStringInOperator(jisuanzu,OperatorUtil.SMALL)){//包含count,min,max,sum,avg
							format_jisuanzu = lablecommon_format_jisuanzu_with_operator(jisuanzu,map);
//							value = String.valueOf(lablecommon_format_jisuanzu_with_operator(jisuanzu,map));
						}else{//不包含count,min,max,sum,avg
							format_jisuanzu = lablecommon_format_jisuanzu(jisuanzu,map);
						}
						
						if(format_jisuanzu != null && !format_jisuanzu.equals("")){
							if(customReportJSZ.getViewjindutiao() == 1){
								value = JisuanzuUtil.formatNumber(
										Double.parseDouble(
												JisuanzuUtil.computeString(format_jisuanzu)), 
												(customReportJSZ.getFormatnumber()==0|| customReportJSZ.getFormatnumber()==1)?
														JisuanzuUtil.DEFAULTFARMAT:JisuanzuUtil.getFormatNumber(customReportJSZ.getFormatnumber()+2));
								value = JisuanzuUtil.stringPointMoveRight(value,customReportJSZ.getFormatnumber());
								double d = Double.parseDouble(value);
								System.out.println(d);
								if(d>=100)	d = 100;
								value = "<table width='300px' border='0' cellspacing='1' ><tr><td><div   style='border: 1px dotted #FF6633;width:280px'><img height='14' src='images/jd.gif' width='"+d+"%'  /></div></td><td><center><span style='color:red;'>"+d+"%</span></center></td></tr></table>";
							}else{
								value = JisuanzuUtil.formatNumber(
										Double.parseDouble(
												JisuanzuUtil.computeString(format_jisuanzu)), 
												(customReportJSZ.getFormatnumber()==0|| customReportJSZ.getFormatnumber()==1)?
														JisuanzuUtil.DEFAULTFARMAT:JisuanzuUtil.getFormatNumber(customReportJSZ.getFormatnumber()));
							}
						}
					}else{//带相关的统计
						if(customReportJSZ.getRelatetype()==1){
							value = "<span style='color:red'>"+String.valueOf(map.get(customReportJSZ.getFormula()))+"</span>个";
						}else if(customReportJSZ.getRelatetype()==2){
							value = "<span style='color:red'>"+String.valueOf(map.get(customReportJSZ.getFormula()))+"</span>元";
						}else{
							value = "<span style='color:red'>"+String.valueOf(map.get(customReportJSZ.getFormula()))+"</span>元";
						}
						if(customReportJSZ.getShowview() == 1){
							value += "<br><a href=\"myContactTags_.action?tablename="+jisuanzu.substring(0,jisuanzu.indexOf("."))+"&id="+map.get("id")+"\">查看</a>";
						}
					}
					
				}
//			}
			sb1.append("</td><td align='center'>" + value);
		}
		
		sb1.append(sb.substring(index2));
		System.out.println(sb1.toString());
		return sb1.toString();
		
	}
	
	/**
	 * 列表标签SQL语句解析
	 * @param table:表信息
	 * @param field:字段信息
	 * @param sqlC:条件信息
	 * @param order:排序信息
	 * @param pagesize:查询列数
	 * @return
	 */
	public static String lablecommon_getsql(String table,String field,String sqlC,String order,String group,Integer pagesize,List<CustomReportJSZ> customReportJSZList){
		StringBuilder  sql=null;
		String tableArr[]  =table.split("-");
		if(group != null && !"".equals(group)){
			sql = new StringBuilder( " select ");
		}else{
			sql = new StringBuilder( " select " + getsql_id(tableArr));
		}

		
		int count = 0;
		
		String fieldArr[] = null;
		
		if(group != null && !"".equals(group)){
			fieldArr = group.split(",");
		}else{
			fieldArr = field.split("-");
		}
		
		//构建查询字段
		for (String fieldstr : fieldArr) {
			if(count==0){
				sql.append(fieldstr +" as "+LableCommon.lablecommon_deletableinfo(fieldstr,"."));
			}else{
				sql.append(","+fieldstr+" as "+LableCommon.lablecommon_deletableinfo(fieldstr,"."));
			}
			count++;
		}
		
		//构建统计字段
		lablecommon_getsql_addtongji_column(customReportJSZList,sql);
		
		count=0;
		
		//构建from 表
		for (String tablestr : tableArr) {
			if(count==0){
				
				sql.append(" from "+tablestr);
			}else{
				sql.append(","+tablestr);
			}
			count++;
		}
		sql.append(" where 1=1 ");
		//构建查询条件
		if(sqlC!= null && !"".equals(sqlC)){
			sql.append(" and "+sqlC);
		}
		//构建查询列数
		if(pagesize!=0){
			if(sqlC!= null && !"".equals(sqlC)){
				sql.append(" and   rownum <= "+pagesize+" ");
				
			}else
			{
				sql.append(" and   rownum <= "+pagesize+" ");
				
			}
			  
		}
		
		//构建分组
		if(group != null && !"".equals(group)){
			if(group.indexOf(".")!=-1){
				sql.append(" group by " +group);
				
			}
			
		}
		
		//构建排序
		if(order != null && !"".equals(order)){
			if(order.indexOf(".")!=-1){
				sql.append(" order by " +order);
				
			}
			
		}
	
		
	return sql.toString();
		
		
	}
	
	/**
	 * 拼接sql的时候如果有计算组，添加统计字段
	 * @param customReportJSZList
	 * @param sql
	 * @return
	 */
	public static String lablecommon_getsql_addtongji_column(List<CustomReportJSZ> customReportJSZList,StringBuilder  sql){
		String value = "";
		String temp = "";
		String[] ary = null;
		if(customReportJSZList != null && customReportJSZList.size()>0){
			for(CustomReportJSZ customReportJSZ:customReportJSZList){
				temp = customReportJSZ.getFormula();
				if(temp != null && !temp.equals("")){
					if(temp.indexOf(".")==-1){//统计方式是相关的话不改变sql
						ary = OperatorUtil.getOperatorField_(temp, 1);
						if(ary != null && ary.length>0){
							for(int i=0;i<ary.length;i++){
								value += " "+ OperatorUtil.getOperatorField_return(ary[i]) + " as " + ary[i].replace(".", "") + ",";
							}
						}else{
							//将temp转化为字段
							value += " " + temp + " as " + formatFormulaOnlyField(temp,"") + ",";
						}
					}
				}
			}
		}
		
		if(!value.equals(""))
			value = "," + value.substring(0,value.lastIndexOf(","))+ " ";
		
		return sql.append(value).toString();
	}
	
	
	public static String formatFormulaOnlyField(String formula,String replacestr){
		return formula.replace("(", replacestr).replace(")", replacestr).replace("+", replacestr).replace("-", replacestr).replace("*", replacestr).replace("/", replacestr);
	}
	
	
	//根据标签sql添加id
	public static String getsql_id(String[] tableArr){
		String sql = "";
		if(tableArr != null && tableArr.length>0){
			for(int i=0;i<tableArr.length;i++){
				if(!tableArr[i].equals("")){
					sql += tableArr[i]+".id as " + "id,";
				}
			}
		}
		return sql;
	}
	
	
	/**
	 * 分页标签SQL语句解析
	 * @param table:表信息
	 * @param field:字段信息
	 * @param sqlC:条件信息
	 * @param order:排序信息
	 * @param pagesize:查询列数
	 * @return
	 */
	public static String lablecommon_pagegetsql(String table,String field,String sqlC,String order,String group,Integer pagesize,List<CustomReportJSZ> customReportJSZList){
		StringBuilder  sql=null;	
		String tableArr[]  =table.split("-");
		if(group != null && !"".equals(group)){
			
			sql = new StringBuilder( " select * from (select t.*, rownum rn from (select  ");
		}else{
			sql = new StringBuilder( " select * from (select t.*, rownum rn from (select  " + getsql_id(tableArr));
		}
		int count = 0;
		String fieldArr[] = null;
		if(group != null && !"".equals(group)){
			fieldArr = group.split(",");
		}else{
			fieldArr = field.split("-");
		}
		
		//构建查询字段====表内
		for (String fieldstr : fieldArr) {
			if(count==0){
				sql.append(fieldstr +" as "+LableCommon.lablecommon_deletableinfo(fieldstr,"."));
			}else{
				sql.append(","+fieldstr+" as "+LableCommon.lablecommon_deletableinfo(fieldstr,"."));
			}
			count++;
		}
		//构建统计字段
		lablecommon_getsql_addtongji_column(customReportJSZList,sql);
		
		
		count=0;
		
		//构建from 表
		for (String tablestr : tableArr) {
			if(count==0){
				
				sql.append(" from "+tablestr);
			}else{
				sql.append(","+tablestr);
			}
			count++;
		}
		sql.append(" where 1=1 ");
		//构建查询条件
		if(sqlC!= null && !"".equals(sqlC)){
			sql.append(" and "+sqlC);
		}
		
		//构建分组
		if(group != null && !"".equals(group)){
			if(group.indexOf(".")!=-1){
				sql.append(" group by " +group);
				
			}
			
		}

		//构建排序
		if(order != null && !"".equals(order)){
			if(order.indexOf(".")!=-1){
				sql.append(" order by " +order);
				
			}
			
		}
		sql.append(" )t where rownum <= ? ) where rn>=?");
		
	return sql.toString();
		
		
	}
	
	
	public static String[] lablecommon_gettablefield(String[] alltablefield,String[] sqlgroup){
		if(sqlgroup == null)	return alltablefield;
		String[] returnArray = null;
		String tablefields = "";
		
		for(int i=0;i<alltablefield.length;i++){
			for(int j=0;j<sqlgroup.length;j++){
				if(alltablefield[i].equals(sqlgroup[j])){
					tablefields += alltablefield[i] + ",";
				}
			}
		}
		
		if(!tablefields.equals("")){
			tablefields = tablefields.substring(0,tablefields.lastIndexOf(","));
			returnArray = tablefields.split(",");
		}
		
		return returnArray;
	}
	
	
	//添加统计信息
	public static List<TableField> lablecommon_addtongjifield(List<CustomReportJSZ> customReportJSZList,List<TableField> list){
		if(customReportJSZList != null && customReportJSZList.size()>0){
			TableField field = null;
			String formula = "";
			for(CustomReportJSZ customReportJSZ:customReportJSZList){
				formula = customReportJSZ.getFormula();//sum(GZJH_ZWPF)
				if(formula != null && !formula.equals("")){
					if(!formula.contains(".")){//不是相关统计字段
						//判断计算方式是否包含操作符
						if(OperatorUtil.isStringInOperator(formula,OperatorUtil.SMALL)){
							String[] strarr = OperatorUtil.getOperatorField_(formula,1);//SUMGZJH.GZJH_ZWPF
							String str = "";
							for(int i=0;i<strarr.length;i++){
								str = strarr[i];
								if(str != null && !str.equals("")){
									//获取求和列，例如：表名.列名
									field = new TableField();
									field.setFieldType("实数");
									field.setName(customReportJSZ.getColumnname());
//									field.setTableName("SUMGZJH");
									field.setTableName(str.substring(0,str.indexOf(".")));
//									field.setFieldName("GZJH_ZWPF");
									field.setFieldName(str.substring(str.indexOf(".")+1,str.length()));
									field.setOriginal_fieldName(formula);
									list.add(field);
								}
							}
						}else{
							field = new TableField();
							field.setFieldType("实数");
							field.setName(customReportJSZ.getColumnname());
//							field.setTableName("SUMGZJH");
							field.setTableName("");
//							field.setFieldName("GZJH_ZWPF");
							field.setFieldName(formatFormulaOnlyField(formula,""));
							field.setOriginal_fieldName(formula);
							list.add(field);
						}
					}else{//相关的统计字段
						field = new TableField();
						field.setFieldType("实数");
						field.setName(customReportJSZ.getColumnname());
						field.setTableName(ScheduleUtil.OTHER);
						field.setFieldName(formula);
						field.setOriginal_fieldName(formula);
						list.add(field);
					}
				}
			}
		}
		return list;
	}
	
	//将标签的sql转为通过某列数据排序后的sql
	public static String addOrderColumnnameFromsql(String sql,Map<String,String> map,int number){
		int index = -1;
		int index_group = -1;
		String formula_format = "";
		String sql_nulls = "";
		String orderColumnname = map.get("orderColumnname");
		if(orderColumnname!= null && !orderColumnname.equals("")){
			sql_nulls = map.get("orderColumnname_type").equals("asc")?"nulls first":"nulls last";
			if(number == 1){
				if(orderColumnname.indexOf(OperatorUtil.TOTAL_OPERATOR)!=-1){//包含total
					
				}else{//不包含total
					index = sql.indexOf("order by");
					index_group = sql.indexOf("group by");
					if(index!=-1){//sql中包含排序
						String[] splitsql = sql.split("order by");
						if(splitsql[1].indexOf("asc")!=-1 || splitsql[1].indexOf("desc")!=-1){//判断有没有其他排序字段
							sql = splitsql[0] + " order by " + orderColumnname + " " + map.get("orderColumnname_type") + " " +sql_nulls +", "+ splitsql[1];
						}else{
							sql = splitsql[0] + " order by " + orderColumnname + " " + map.get("orderColumnname_type") + " " +sql_nulls +" "+ splitsql[1];
						}
					}else{//sql中无排序
						if(index_group !=-1){//sql有无分组
							String[] splitsql = sql.split("group by");
							String value = "";
							String value_= "";
							value = splitsql[1].substring(0,splitsql[1].indexOf(")")+1).replace(")", " order by " + orderColumnname + " " + map.get("orderColumnname_type") +" " +sql_nulls +" "+ ")");
							value_ = splitsql[1].replace(splitsql[1].substring(0,splitsql[1].indexOf(")")+1), "");
							sql = splitsql[0] + " group by " + value + " " + value_;
						}else{//sql中有分组
							String[] splitsql = sql.split("where 1=1");
							sql = splitsql[0] + " where 1=1 " + " order by " + orderColumnname + " " + map.get("orderColumnname_type") + " " +sql_nulls +" " + splitsql[1];
						}
					}
				}
			}else if(number == 2){
				index = sql.indexOf("order by");
				index_group = sql.indexOf("group by");
				if(index!=-1){//sql中包含排序
					String[] splitsql = sql.split("order by");
					if(splitsql[1].indexOf("asc")!=-1 || splitsql[1].indexOf("desc")!=-1){
						sql = splitsql[0] + " order by " + orderColumnname + " " + map.get("orderColumnname_type") + " " +sql_nulls +", "+ splitsql[1];
					}else{
						sql = splitsql[0] + " order by " + orderColumnname + " " + map.get("orderColumnname_type") + " " +sql_nulls +" "+ splitsql[1];
					}
				}else{//sql中无排序
					if(index_group !=-1){//sql有无分组
						String[] splitsql = sql.split("group by");
						String value = "";
						String value_= "";
						value = splitsql[1].substring(0,splitsql[1].indexOf(")")+1).replace(")", " order by " + orderColumnname + " " + map.get("orderColumnname_type") +" " +sql_nulls +" "+ ")");
						value_ = splitsql[1].replace(splitsql[1].substring(0,splitsql[1].indexOf(")")+1), "");
						sql = splitsql[0] + " group by " + value + " " + value_;
					}else{//sql中有分组
						String[] splitsql = sql.split("where 1=1");
						sql = splitsql[0] + " where 1=1 " + " order by " + orderColumnname + " " + map.get("orderColumnname_type") + " " +sql_nulls +" " + splitsql[1];
					}
				}
			}
		}
		return sql;
	}
	
	//将sql加入部门信息
	public static String addSqlDepartment(String sql,ElNode department,String[] tableArr){
		int index = -1;
		String before = "";
		String end = "";
		String type = "where 1=1";
		String sqluserid = " userid in " + 
				"(select id from eluser where valid = 1 and "+tableArr[0]+".userid = e.id and e.depid = d.id) ";
		if(department.getId() != -1){
			index = sql.indexOf(type);
			if(index !=-1){
				before = sql.split(type)[0];
				end = sql.split(type)[1];
			}
			
			sql = before + " ,eluser e,department d " + 
			" join ("
			+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", department, true)
			+ ") dep on dep.id=d.id " + " " +type + " and " + sqluserid + end;
		}
		
		return sql;
		
	}
	
	//将sql加入搜索条件
	public static String addSqlSearchCondition(String sql,Map<String,String> searchMap,List<TableField> list,int searchtype){
		String sqlwhere = "";
		for(TableField tf:list){
			for(String key:searchMap.keySet()){
				if(key.indexOf(OperatorUtil.TOTAL_OPERATOR)==-1){
					if(key.equals((tf.getOriginal_fieldName()==null||tf.getOriginal_fieldName().equals(""))?tf.getFieldName():tf.getOriginal_fieldName())){
						sqlwhere = addsqlwhere(sqlwhere,tf,searchMap,searchtype);
					}
				}
			}
		}
		String[] split_array = sql.split("where 1=1");
		sql = split_array[0] + " where 1=1 " + sqlwhere + split_array[1];
		return sql;
	}
	
	
	//将sql加入搜索相关统计字段的where
	public static String addsqlSearchConditionByRelateSearch(List<CustomReportJSZ> CustomReportJSZList,String sql,Map<String,String> searchMap,String tablename) throws ElException{
		String sql_begin = sql.split("where 1=1")[0];
		String sql_end = sql.split("where 1=1")[1];
		String ids = "";//满足相关统计字段搜索的id
		String rerurn_sql = "";
		int type = 0;
		List<String> ids_list = new ArrayList<String>();
		for(CustomReportJSZ customReportJSZ:CustomReportJSZList){
			if(customReportJSZ.getFormula()!=null&&!customReportJSZ.getFormula().equals("")&&customReportJSZ.getFormula().contains(".")){
				if(searchMap!=null&&searchMap.size()>0){
					for(String key:searchMap.keySet()){
						if(customReportJSZ.getType() == 2 && customReportJSZ.getRelatetype()==2){
							if(key.equals(customReportJSZ.getFormula())){//SK_KHMC=====SK.SK_SKJE
								type = 1;
								//根据表名列名查询出满足条件的ids
								ids = tagsDao.returnIds(customReportJSZ,searchMap,tablename);
								
								ids_list.add(ids);
							}
						}else if(customReportJSZ.getType() == 2 && customReportJSZ.getRelatetype()==3){//相关统计字段再次进行统计
							if(customReportJSZ.getFormula().indexOf(key)!=-1){
								type = 2;
								//SK.SK_SKJE+KHDA.KHDA_FTCB-FK.FK_FKJE
								//获取相关统计字段
								if((searchMap.get(customReportJSZ.getFormula())!=null&&!searchMap.get(customReportJSZ.getFormula()).equals(""))||
										(searchMap.get(customReportJSZ.getFormula()+"_")!=null&&!searchMap.get(customReportJSZ.getFormula()+"_").equals(""))){
									ids = tagsDao.returnIds_relatetype3(customReportJSZ, searchMap, tablename);
									ids_list.add(ids);
								}
							}
						}
//						else if(customReportJSZ.getType() == 1 && customReportJSZ.getRelatetype()==1){//表内统计字段
//							type = 3;
//							//sum(GZJH_ZWPF)/total(GZJH_ZWPF)
//							if((searchMap.get(customReportJSZ.getFormula())!=null&&!searchMap.get(customReportJSZ.getFormula()).equals(""))||
//									(searchMap.get(customReportJSZ.getFormula()+"_")!=null&&!searchMap.get(customReportJSZ.getFormula()+"_").equals(""))){
//								sql = tagsDao.returnIds_total(customReportJSZ, searchMap, tablename,sql);
//							}
//						}
					}
				}
			}
		}
		if(ids_list.size()>0){
			//将获取的ids转换成多个搜索获取的相同id
			if(type == 1 || type == 2)
				ids = getIds( ids_list);
		}
		if(type == 1 || type == 2){
			if(ids!=null&&!ids.equals("")){
				rerurn_sql =  sql_begin + " " + "where 1=1" + " " + "and "+tablename+".id in (" + ids.substring(0,ids.lastIndexOf(",")) + ") " + sql_end;
			}else{
				rerurn_sql =  sql_begin + " " + "where 1=1" + " " + "and "+tablename+".id in (" + "-1" + ") " + sql_end;
			}
		}else if(type == 3){
			rerurn_sql = sql;
		}else if(type == 0){
			rerurn_sql = sql;
		}
		System.out.println(rerurn_sql);
		return rerurn_sql;
	}
	
	public static String getIds(List<String> ids_list){
		String[] ids = null;
		if(ids_list.size()>1){
			ids = intersect(ids_list.get(0).split(","),ids_list.get(1).split(","));
			for(int i=2;i<ids_list.size();i++){
				if(ids_list.get(i)!=null)
					ids = intersect(ids,ids_list.get(i).split(","));
			}
		}else{
			ids = ids_list.get(0).split(",");
		}
		String id = "";
		if(ids!=null&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				id += ids[i] + ",";
			}
		}
		return id;
	}
	
	 //求两个数组的交集  
    public static String[] intersect(String[] arr1, String[] arr2) {  
        Map<String, Boolean> map = new HashMap<String, Boolean>();  
        LinkedList<String> list = new LinkedList<String>();  
        for (String str : arr1) {  
            if (!map.containsKey(str)) {  
                map.put(str, Boolean.FALSE);  
            }  
        }  
        for (String str : arr2) {  
            if (map.containsKey(str)) {  
                map.put(str, Boolean.TRUE);  
            }  
        }  
  
        for (Entry<String, Boolean> e : map.entrySet()) {  
            if (e.getValue().equals(Boolean.TRUE)) {  
                list.add(e.getKey());  
            }  
        }  
  
        String[] result = {};  
        return list.toArray(result);  
    } 
	
	
	//验证前台传过来的搜索值是否有值
	public static boolean checkMapValueIsExist(Map<String,String> searchMap,String key){
		boolean flag = false;
		if(searchMap.get(key)!=null&&!searchMap.get(key).equals(""))
			flag = true;
		return flag;
	}
	
	//根据字段类型添加where
	public static String addsqlwhere(String sqlwhere,TableField tablefield,Map<String,String> searchMap,int searchtype){
		int type = changeStringToInt(tablefield.getFieldType());
		switch(type){
		case 1:{//文本
			if(checkMapValueIsExist(searchMap,tablefield.getFieldName()))
				if(searchtype == 2){//模糊查询
					sqlwhere += " and " + tablefield.getFieldName() + " like '%" + searchMap.get(tablefield.getFieldName()) + "%'";
				}else{//精确查询
					sqlwhere += " and " + tablefield.getFieldName() + " = '" + searchMap.get(tablefield.getFieldName()) + "'";
				}
			break;
		}
		case 5:{//下拉选项
			if(checkMapValueIsExist(searchMap,tablefield.getFieldName()))
				if(searchtype == 2){//模糊查询
					sqlwhere += " and " + tablefield.getFieldName() + " like '%" + searchMap.get(tablefield.getFieldName()) + "%'";
				}else{//精确查询
					sqlwhere += " and " + tablefield.getFieldName() + " = '" + searchMap.get(tablefield.getFieldName()) + "'";
				}
			break;
		}
		case 2:{//整数
			if(checkMapValueIsExist(searchMap,tablefield.getFieldName()))
				sqlwhere += " and " + tablefield.getFieldName() + ">=" + searchMap.get(tablefield.getFieldName()) ;
			if(checkMapValueIsExist(searchMap,tablefield.getFieldName()+"_"))
				sqlwhere += " and " + tablefield.getFieldName() + "<=" + searchMap.get(tablefield.getFieldName()+"_");
			break;
		}
		case 3:{//实数
			if(tablefield.getOriginal_fieldName()!=null&&!tablefield.getOriginal_fieldName().equals("")){//统计字段
				if(!tablefield.getTableName().equals(ScheduleUtil.OTHER)){//不是相关统计字段
					if(checkMapValueIsExist(searchMap,tablefield.getOriginal_fieldName())){
						sqlwhere += " and " + tablefield.getOriginal_fieldName() + ">=" + searchMap.get(tablefield.getOriginal_fieldName());
					}
					if(checkMapValueIsExist(searchMap,tablefield.getOriginal_fieldName()+"_")){
						sqlwhere += " and " + tablefield.getOriginal_fieldName() + "<=" + searchMap.get(tablefield.getOriginal_fieldName()+"_");
					}
				}
			}else{//普通字段
				if(checkMapValueIsExist(searchMap,tablefield.getFieldName()))
					sqlwhere += " and " + tablefield.getFieldName() + ">=" + searchMap.get(tablefield.getFieldName());
				if(checkMapValueIsExist(searchMap,tablefield.getFieldName()+"_"))
					sqlwhere += " and " + tablefield.getFieldName() + "<=" + searchMap.get(tablefield.getFieldName()+"_");
			}
			break;
		}
		case 4:{//日期
			if(checkMapValueIsExist(searchMap,tablefield.getFieldName()))
				sqlwhere += " and " + tablefield.getFieldName() + ">= to_date('" +  searchMap.get(tablefield.getFieldName()) + "','yyyy-mm-dd hh24:mi:ss'";
			if(checkMapValueIsExist(searchMap,tablefield.getFieldName()+"_"))
				sqlwhere += " and " + tablefield.getFieldName() + "<= to_date('" +  searchMap.get(tablefield.getFieldName()+"_") + "','yyyy-mm-dd hh24:mi:ss'" ;
			break;
		}
		case 6:{//相关字段
			if(checkMapValueIsExist(searchMap,tablefield.getFieldName())){
				String default_value = tablefield.getDefaultvalue();//LJR==LJR_XM==姓名==varchar2(500)
				String[] default_array = null;
				if(default_value!=null&&!default_value.equals("")){
					default_array = default_value.split("==");
				}
				if(searchtype == 2){//模糊查询
					sqlwhere += " and "
						+ tablefield.getFieldName()
						+ " is not null and "+tablefield.getTableName()+".id in (select mainid from tb_tags_relate where columnname='"+tablefield.getFieldName()+"' and  relateid in "
						+ " ( select id from "+default_array[0]+" "  + " where "+default_array[1]+" " 
						+ " like '%" + searchMap.get(tablefield.getFieldName()) + "%')) ";
				}else{//精确查询
					sqlwhere += " and "
						+ tablefield.getFieldName()
						+ " is not null and "+tablefield.getTableName()+".id in (select mainid from tb_tags_relate where columnname='"+tablefield.getFieldName()+"' and  relateid in "
						+ " ( select id from "+default_array[0]+" "  + " where "+default_array[1]+" " 
						+ " = '" + searchMap.get(tablefield.getFieldName()) + "')) ";
				}
			}
			break;
		}
		case 7:{//相关负责人
			if(checkMapValueIsExist(searchMap,tablefield.getFieldName())){
				if(searchtype == 2){//模糊查询
					sqlwhere += " and "
						+ tablefield.getFieldName()
						+ " is not null and "+tablefield.getTableName()+".id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from eluser "  + " where realname " 
						+ " like '%" + searchMap.get(tablefield.getFieldName()) + "%')) ";
				}else{//精确查询
					sqlwhere += " and "
						+ tablefield.getFieldName()
						+ " is not null and "+tablefield.getTableName()+".id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from eluser "  + " where realname " 
						+ " = '" + searchMap.get(tablefield.getFieldName()) + "')) ";
				}
			}
			break;
		}
		case 8:{//单选
			if(checkMapValueIsExist(searchMap,tablefield.getFieldName()))
				if(searchtype == 2){//模糊查询
					sqlwhere += " and " + tablefield.getFieldName() + " like '%" + searchMap.get(tablefield.getFieldName()) + "%'";
				}else{//精确查询
					sqlwhere += " and " + tablefield.getFieldName() + " = '" + searchMap.get(tablefield.getFieldName()) + "'";
				}
			break;
		}
		case 9:{//复选
			if(checkMapValueIsExist(searchMap,tablefield.getFieldName()))
				if(searchtype == 2){//模糊查询
					sqlwhere += " and " + tablefield.getFieldName() + " like '%" + searchMap.get(tablefield.getFieldName()) + "%'";
				}else{//精确查询
					sqlwhere += " and " + tablefield.getFieldName() + " = '" + searchMap.get(tablefield.getFieldName()) + "'";
				}
			break;
		}
		}
		return sqlwhere;
	}
	
	//将搜索标签解析成标准html
	/**
	 *  GZJH.GZJH_JHZQ,GZJH.GZJH_JHXZ,GZJH.GZJH_JHMC,GZJH_ZWPF+GZJH_LDPF+GZJH_BMPF
	 */
	public static String resolveSearchhtmlToHtml(String searchhtml,String searchhtmlfield,List<TableField> fieldlist,JspWriter out){
		String[] array = null;
		List<TableField> searchTablefields = null;
		if(searchhtmlfield!=null&& !searchhtmlfield.equals("")){
			array = searchhtmlfield.split(",");
		}
		
		if(array != null && array.length>0){
			//获取搜索的字段list
			searchTablefields = getsearchList(fieldlist,array);
		}
		
		if(searchTablefields!=null&&searchTablefields.size()>0){
			//将searchTablefields转换为html
			searchhtml = resolveSearchfieldsToHtml(searchhtml,searchTablefields,out);
		}
		
		return searchhtml;
	}
	
	//在tablelist中找出在搜索标签中存在的tablefield
	public static List<TableField> getsearchList(List<TableField> tablelist,String[] array){
		List<TableField> searchTablefields = new ArrayList<TableField>();
		for(int i=0;i<array.length;i++){
			for(TableField tf:tablelist){
				//情况分类
				//1  GZJH_ZWPF+GZJH_LDPF+GZJH_BMPF
				//2  sum(GZJH_ZWPF)/total(GZJH_ZWPF)
				//3  SK.SKJE
				
				if(tf.getOriginal_fieldName()!=null&&!tf.getOriginal_fieldName().equals("")){//统计字段
					if(array[i].equals(tf.getOriginal_fieldName())){
						searchTablefields.add(tf);
					}
				}else{//非统计字段
					if(getColumnOnly(array[i]).equals(tf.getFieldName())){//获取列名，去除列名前面的表名+'.'
						searchTablefields.add(tf);
					}
				}
			}
		}
		return searchTablefields;
	}
	
	//将列或统计列去除表
	public static String getColumnOnly(String str){
		int index = -1;
		if(str.contains(".")){//单纯列
			index = str.indexOf(".");
			str = str.substring(index+1, str.length());
		}else{//统计列
			str = formatFormulaOnlyField(str,"");
		}
		return str;
	}
	
	//将搜索的字段列表转化为html
	/**
	 *  <tr>
			<TD vAlign=center align=middle width=120 bgColor=#ffffff rowSpan=100>
				<INPUT class=btn1_mouseout onMouseOver="this.className='btn1_mouseover'" onMouseOut="this.className='btn1_mouseout'" onclick=searchhtml(); type=button value=开始搜索>
			</TD>
		</tr>
		<tr>
		    <td align='center'>@searchlable#GZJH.GZJH_JHZQ#searchlable^</td>
		    <td align='center'>@searchlable#GZJH.GZJH_JHXZ#searchlable^</td>
		    <td align='center'>@searchlable#GZJH.GZJH_JHMC#searchlable^</td>
		</tr>
		<tr>
		    <td>@searchlable#GZJH_ZWPF+GZJH_LDPF+GZJH_BMPF#searchlable^</td>
		</tr>
	 */
	/**
	 * searchTablefields是字段的list
	 * @throws IOException 
	 */
	public static String resolveSearchfieldsToHtml(String searchhtml,List<TableField> searchTablefields,JspWriter out) {
		String html = searchhtml;
		for(TableField tf:searchTablefields){
			html = resolveSearchfieldsToHtmlByDisplayType(html,tf);
		}
		return html;
	}
	
	//根据字段的类型输出html
	public static String resolveSearchfieldsToHtmlByDisplayType(String searchhtml,TableField tablefield){
		int type = changeStringToInt(tablefield.getFieldType());
		String returnValue = "";
		String real_fieldname = "";
		String name = tablefield.getName();
		switch(type){
		case 1:{//文本
			real_fieldname = tablefield.getFieldName();
			returnValue = name + ":"+ "<input type='text' name='"+real_fieldname+"' />";
			break;
		}
		case 5:{//下拉选项
			real_fieldname = tablefield.getFieldName();
			String default_value = tablefield.getDefaultvalue();
			String[] default_options = null;
			String options = "";
			if(default_value!=null&&!default_value.equals("")){
				default_options = default_value.split("==");
				if(default_options!=null&&default_options.length>0){
					for(int i=0;i<default_options.length;i++){
						options += "<option value='"+default_options[i]+"'>"+default_options[i]+"</option>";
					}
				}
			}
			returnValue = name + ":"+ "<select name='"+real_fieldname+"' onchange='this.value=this.options[this.selectedIndex].value'>"+
			"<option value=''>请选择</option>"+
			options+
			"</select>";
			break;
		}
		case 2:{//整数
			real_fieldname = tablefield.getFieldName();
			returnValue = name + ":"+ "从"+"<input type='text' name='"+real_fieldname+"' />"+
							"到"+"<input type='text' name='"+real_fieldname+"_' />";
			break;
		}
		case 3:{//实数
			if(tablefield.getOriginal_fieldName()!=null&&!tablefield.getOriginal_fieldName().equals("")){//统计字段
				real_fieldname = tablefield.getOriginal_fieldName();
				returnValue = name + ":"+ "从"+"<input type='text' name='"+real_fieldname+"' />"+
				"到"+"<input type='text' name='"+real_fieldname+"_' />";
			}else{//普通字段
				real_fieldname = tablefield.getFieldName();
				returnValue = name + ":"+ "从"+"<input type='text' name='"+real_fieldname+"' />"+
				"到"+"<input type='text' name='"+real_fieldname+"_' />";
			}
			break;
		}
		case 4:{//日期
			real_fieldname = tablefield.getFieldName();
			returnValue = name + ":"+ "从"+"<input type='text' name='"+real_fieldname+"' onClick='setday(this)' />"+
			"到"+"<input type='text' name='"+real_fieldname+"_' onClick='setday(this)' />";
			break;
		}
		case 6:{//相关字段
			real_fieldname = tablefield.getFieldName();
			returnValue = name + ":"+ "<input type='text' name='"+real_fieldname+"' />";
			break;
		}
		case 7:{//相关负责人
			real_fieldname = tablefield.getFieldName();
			returnValue = name + ":"+ "<input type='text' name='"+real_fieldname+"' />";
			break;
		}
		case 8:{//单选
			real_fieldname = tablefield.getFieldName();
			String default_value = tablefield.getDefaultvalue();
			String[] default_options = null;
			String radio_body="";
			if(default_value!=null&&!default_value.equals("")){
				default_options = default_value.split("==");
				if(default_options!=null&&default_options.length>0){
					for(int i=0;i<default_options.length;i++){
						radio_body +=  "<input type='radio' name='"+real_fieldname+"' value='"+default_options[i]+"' />" + default_options[i];
					}
				}
			}
			returnValue = name + ":" +radio_body;
			break;
		}
		case 9:{//复选
			real_fieldname = tablefield.getFieldName();
			String default_value = tablefield.getDefaultvalue();
			String[] default_options = null;
			String checkbox_body="";
			if(default_value!=null&&!default_value.equals("")){
				default_options = default_value.split("==");
				if(default_options!=null&&default_options.length>0){
					for(int i=0;i<default_options.length;i++){
						checkbox_body +=  "<input type='checkbox' name='"+real_fieldname+"' value='"+default_options[i]+"' />" + default_options[i];
					}
				}
			}
			returnValue = name + ":" + checkbox_body;
			break;
		}
		}
		if(tablefield.getOriginal_fieldName()!=null&&!tablefield.getOriginal_fieldName().equals("")){//统计字段
			searchhtml = searchhtml.replace(SEARCHLABLE_BEGIN+real_fieldname+SEARCHLABLE_END, returnValue);
		}else{//普通字段
			searchhtml = searchhtml.replace(SEARCHLABLE_BEGIN+tablefield.getTableName()+"."+real_fieldname+SEARCHLABLE_END, returnValue);
		}
		return searchhtml;
	}
	
	
	//将searchhtml转换为字段字符串
	public static String getSearchHtmlFieldBySearchHtml(String searchhtml) {
		/**
		 *  <tr>
				<TD vAlign=center align=middle width=120 bgColor=#ffffff rowSpan=100>
					<INPUT class=btn1_mouseout onMouseOver="this.className='btn1_mouseover'" onMouseOut="this.className='btn1_mouseout'" onclick=searchhtml(); type=button value=开始搜索>
				</TD>
			</tr>
			<tr>
			    <td align='center'>@searchlable#GZJH.GZJH_JHZQ#searchlable^</td>
			    <td align='center'>@searchlable#GZJH.GZJH_JHXZ#searchlable^</td>
			    <td align='center'>@searchlable#GZJH.GZJH_JHMC#searchlable^</td>
			</tr>
			<tr>
			    <td>@searchlable#GZJH_ZWPF+GZJH_LDPF+GZJH_BMPF#searchlable^</td>
			</tr>
		 */
		//转换为
		//GZJH.GZJH_JHZQ,GZJH.GZJH_JHXZ,GZJH.GZJH_JHMC,GZJH_ZWPF+GZJH_LDPF+GZJH_BMPF
		String searchhtmlfield = "";
		searchhtmlfield = febGetSearchHtmlFieldBySearchHtml(searchhtml,searchhtmlfield);
		if(searchhtmlfield!=null&&!searchhtmlfield.equals("")){
			searchhtmlfield = searchhtmlfield.substring(0,searchhtmlfield.lastIndexOf(","));
		}
		return searchhtmlfield;
	}
	
	
	//递归获取每个字段
	public static String febGetSearchHtmlFieldBySearchHtml(String searchhtml,String searchhtmlfield){
		int index = searchhtml.indexOf(SEARCHLABLE_BEGIN);
		String field = "";
		if(index!=-1){
			field = searchhtml.substring(index+13,searchhtml.indexOf(SEARCHLABLE_END));
			searchhtmlfield += field + ",";
			searchhtml = searchhtml.replace(SEARCHLABLE_BEGIN+field+SEARCHLABLE_END, "");
			searchhtmlfield = febGetSearchHtmlFieldBySearchHtml(searchhtml,searchhtmlfield);
		}
		return searchhtmlfield;
	}

	//获取相关字段数组或者表内字段数组
	public static String[] getRelatetype2FieldsOrInsideTableFields(String formula,String tablename,int type){
		String[] return_array = null;
		String[] array1 = null;
		String[] array2 = null;
		String[] array = null;
		String str1 = "";
		String str2 = "";
		String str = formatFormulaOnlyField(formula," ");
		if(str != null &&!str.equals("")){
			array = str.split(" ");
			if(array!=null&&array.length>0){
				for(int i=0;i<array.length;i++){
					if(array[i]!=null&&!array[i].equals("")){
						if(array[i].indexOf(tablename)!=-1){
							str1 += array[i] + ",";//表内字段
						}else{
							str2 += array[i] + ",";//相关统计字段
						}
					}
				}
			}
		}
		
		if(str1!=null&&!str1.equals("")&&String.valueOf(str1.charAt(str1.length()-1)).equals(",")){
			str1 = str1.substring(0,str1.lastIndexOf(","));
			array1 = str1.split(",");
		}
		
		if(str2!=null&&!str2.equals("")&&String.valueOf(str2.charAt(str2.length()-1)).equals(",")){
			str2 = str2.substring(0,str2.lastIndexOf(","));
			array2 = str2.split(",");
		}
		
		if(type == 1){
			return_array = array1;
		}else if(type == 2){
			return_array =  array2;
		}
		return return_array;
	}
	
	
	
	
	
	
	

}
