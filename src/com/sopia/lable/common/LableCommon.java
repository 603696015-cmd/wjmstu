package com.sopia.lable.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.forumman.dao.impl.ForumAdminDaoImpl;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.Search;
import com.sopia.lable.entites.Table;
import com.sopia.lable.entites.TableField;
import com.sopia.schedule.JisuanzuUtil;
import com.sopia.schedule.entities.CustomReportJSZ;


public  class LableCommon {
	private static final Log logger = LogFactory.getLog(ForumAdminDaoImpl.class);
	private Properties propertie;
    private static String path;
    private FileInputStream inputFile;
    private FileOutputStream outputFile;

	
	static{
//		LableCommon_timer();
	}
	  public static void  LableCommon_timer(){
			
			Date d=new Date();
			 Timer timer=new Timer();
		       TimerTask task=new TimerTask(){  
		    	   public void run() {  
		    		   Calendar newday=  Calendar.getInstance(); 
		    		   try {    	
		    			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			    		   String aa = "2013-4-1";    	
			    		   Calendar cal = Calendar.getInstance();  
			    		   cal.setTime(sdf.parse(aa));     	 
				    		   if (!cal.after(newday)) {  
				    			   DBConnection.setConnsNull();
				    			   LableCommon l=new LableCommon();
								   l.LableCommon_updDBC();
				    		   }
				    			
		    			   } catch (Exception e){    						   
		    			   }
		    		   }
		       };

		       timer.schedule(task, d, 40000);
		}
	  
	  
	
	/**
	 * 通过一个数据验证记录是否重复（String型数据）
	 * @param value:   		要验证的数据
	 * @param tablename	:	表名
	 * @param fieldName	:	字段名
	 * @return true:重复 false: 不重复
	 * @throws ElException 
	 */
	
	public  static boolean  check_lableName(String value,String tablename,String fieldName) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean  flag =true;
		try{
			String  sql ="select count(1) from "+tablename+" where "+fieldName+"=?";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setString(1, value);

			rs=ps.executeQuery();
			rs.next();
			flag=rs.getInt(1)>0?true:false;
		
		} catch (Exception e) {
			logger.error("验证String数据是否重复失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
/**
 * 通过传入的数组和表名，将数组中的字符串进行截取,取出对应的字段名集合
 * @param str
 * @param tableName
 * @return
 */
	public static String getTableFiledByARR(String []str,String tableName){
		StringBuffer fieldStr =null;
		for (String tableAndField : str) {
			
			int index = tableAndField.indexOf(".");
			if(index!=-1){
			if(tableName.equals(tableAndField.substring(0,index ))){
				if(fieldStr==null){
					fieldStr=new StringBuffer();
					fieldStr.append("'"+tableAndField.substring(index+1)+"'");
				}
				
				else
					fieldStr.append(",'"+tableAndField.substring(index+1)+"'");
					
			}
			}
			
		}
		return fieldStr.toString();
		
		
	
		
	}
	/**
	 * 通过表信息得到表 及字段字符串信息
	 * @param tableList
	 * @return
	 */
	public static Lable getstrbytable(List<Table> tableList){
		String lableTable=null;
		String lableField=null;
		for (Table table : tableList) {
			//连接表名字符串
			lableTable=lableTable+table.getTableName()+"-";
			for ( TableField tf : table.getField()) {
				//连接字段名字符串
				lableField=lableField+tf.getFieldName()+"-";
				
			}
			
		}
		Lable l = new Lable();
		l.setTablestr(lableTable);
		l.setFieldstr(lableField);
		return l;
		
	}
	public static Lable getnewfieldstr(Lable oldlable,Lable lable){
		//判断传入的表名在原标签中是否存在
		Lable l =new Lable();
			
		if(lable.getTablestr().indexOf(oldlable.getTablestr().trim(), 0)==-1){
			//如果不存在
			
			l.setTablestr(oldlable.getTablestr()+"-");
		}
		String fieldStr="";
		//判断标签名是否存在
		String arr[] =oldlable.getFieldstr().trim().split("-");	
		for (String string : arr) {//循环标签		
				if(lable.getFieldstr().indexOf(string, 0)==-1){
					fieldStr+=string+"-";
				}				
		}
		if(fieldStr.equals("")) fieldStr=null;
		l.setFieldstr(fieldStr);
		return l;
		
		
	}
	/**
	 * 标签表中tableinfo删除
	 * @param oldLable   :传入的要删除信息
	 * @param tableLable:原标签中的信息
	 * @return
	 */
	public static Lable  lablecommon_deletableinfo(String deleLable, Lable tableLable){
		Lable l= new Lable();
		if(tableLable.getTablestr().indexOf(deleLable)!=-1){
			//如果存在 进行截取
			l.setTablestr(lablecommon_deletableinfo(tableLable.getTablestr(),deleLable+"-"));
			l.setFieldstr(lablecommon_deleField(tableLable.getFieldstr(),deleLable));
		}
		
		
		return l ;
	}
	/**
	 * 从原字符串中截取掉指定的字符串
	 * @param old:源字符串
	 * @param old2:需要截取的字符串
	 * @return
	 */
	public static  String lablecommon_deletableinfo(String old,String old2){
		StringBuilder str=new StringBuilder();	
		do {
			str.append(old.substring(0,old.indexOf(old2)));
			str.append(old.substring(old.indexOf(old2)+old2.length()));
			old=str.toString();
		} while (old.indexOf(old2)!=-1);
		
		return str.toString();
		
	}
	
	/**
	 * 从原字符串中截取掉指定的字符串
	 * @param old:源字符串
	 * @param old2:需要截取的字符串
	 * @return
	 */
	public static  String lablecommon_deletableinfo_tmk(String old,String old2){
		String[] ary = null;
		String returnValue = "";
		if(old != null && !old.equals("")){
			ary = old.split(",");
		}
		if(ary.length>0){
			for(int i=0;i<ary.length;i++){
				if(!old2.equals(ary[i])){
					returnValue += ary[i] + ",";
				}
			}
		}
		
		if(!returnValue.equals("") && 
				returnValue.substring(returnValue.length()-1).equals(",")){
			returnValue = returnValue.substring(0,returnValue.lastIndexOf(","));
		}
		
		return returnValue;
		
	}
	
	/**
	 * 从原字符串中截取掉指定的字符串,只截一次
	 * @param old:源字符串
	 * @param old2:需要截取的字符串
	 * @return
	 */
	public static  String lablecommon_deletableinfo2(String old,String old2){
		StringBuilder str=new StringBuilder();	
		
			str.append(old.substring(0,old.indexOf(old2)));
			str.append(old.substring(old.indexOf(old2)+old2.length()));
			
		
		return str.toString();
		
	}
	/**
	 * 从字段信息中截取掉同一表的字段
	 * @param oldfield：字段信息
	 * @param table:要截取的表名
	 * @return
	 */
	public static String   lablecommon_deleField(String oldfield,String table){
		String arr[]=oldfield.split("-");
		StringBuilder str=new StringBuilder();	
		for (String field : arr) {
			if(field.indexOf(table)==-1){
				str.append(field+"-");
			}
		}
		
		return str.toString();
	}
	/**
	 * 从str1中开始截取到str2子串起始位置
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String  lablecommon_delestrtostr(String str1,String str2){
		StringBuilder str=new StringBuilder();	
		str.append(str1.substring(0, str1.indexOf(str2)));
		return str.toString();
		
	}
	/**
	 * 从str2子串结束位置截取到str1尾部
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String  lablecommon_delestrtostr2(String str1,String str2){
		StringBuilder str=new StringBuilder();	
		str.append(str1.substring( str1.indexOf(str2)+str2.length()));
		return str.toString();
		
	}
	/**
	 * 删除指定字段
	 * @param oldfield：原标签信息
	 * @param field：要删除的信息
	 * @return
	 */
	public static Lable  lablecommon_deleField(Lable oldfield,String field){
		if(oldfield.getFieldstr().indexOf(field)!=-1){
			//如果有则进行截取
			oldfield.setFieldstr(lablecommon_deletableinfo(oldfield.getFieldstr(),field+"-"));
			//截取之后进行判断，是否还存在删除字段表的，其他字段
			if(oldfield.getFieldstr().indexOf(lablecommon_delestrtostr(field,"."))==-1){
				//如果不存在，则删除tableinfo中相关表信息
				oldfield.setTablestr(lablecommon_deletableinfo(oldfield.getTablestr(),lablecommon_delestrtostr(field,".")+"-"));
			}
			
		}
		
		return oldfield;
	}
	/**
	 * 从尾部用指定字符串替换原字符串中的指定字符串 
	 * @param order:原字符串
	 * @param str:要替换的字符串
	 * @param str:替换字符串
	 * @return
	 */
	public static String lablecommon_getorder(String order,String str,String str2){
		int index = 0;
		StringBuilder neworder=new StringBuilder();
		index=order.lastIndexOf(str);
		neworder.append(order.substring(0,index));
		neworder.append(str2);
		neworder.append(order.substring(index+str.length()));
		return neworder.toString();
	}
	/**
	 * 从原字符串尾部开始查到到指定字符串str，从str开始位置删除
	 * @param order
	 * @param str
	 * @return
	 */
	public static String lablecommon_delepaixu(String order,String str){
		
		return order.substring(0, order.lastIndexOf(str));
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
	public static String lablecommon_getsql(String table,String field,String sqlC,String order,Integer pagesize){
		StringBuilder  sql=null;
		
		sql = new StringBuilder( " select ");

		int count = 0;
		String fieldArr[] = field.split("-");
		//构建查询字段
		for (String fieldstr : fieldArr) {
			if(count==0){
				sql.append(fieldstr +" as "+lablecommon_deletableinfo(fieldstr,"."));
			}else{
				sql.append(","+fieldstr+" as "+lablecommon_deletableinfo(fieldstr,"."));
			}
			count++;
		}
		count=0;
		String tableArr[]  =table.split("-");
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
		
		//构建排序
		if(order != null && !"".equals(order)){
			if(order.indexOf(".")!=-1){
				sql.append(" order by " +order);
				//如果存在order，那么先查询符合条件的，再查询条数，两次select
				//构建查询列数
				if(pagesize!=0){
					sql.insert(0, "select a.* ,rownum rn from (");
					sql.append(" ) a  where   rownum <= "+pagesize+" ");
				}
			}
			
		}else{
			//构建查询列数
			if(pagesize!=0){
				sql.append(" and   rownum <= "+pagesize+" ");
			}
		}
		
		
	return sql.toString();
		
		
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
	public static String lablecommon_pagegetsql(String table,String field,String sqlC,String order,Integer pagesize){
		StringBuilder  sql=null;		
		sql = new StringBuilder( " select * from (select t.*, rownum rn from (select  ");
		int count = 0;
		String fieldArr[] = field.split("-");
		//构建查询字段
		for (String fieldstr : fieldArr) {
			if(count==0){
				sql.append(fieldstr +" as "+lablecommon_deletableinfo(fieldstr,"."));
			}else{
				sql.append(","+fieldstr+" as "+lablecommon_deletableinfo(fieldstr,"."));
			}
			count++;
		}
		count=0;
		String tableArr[]  =table.split("-");
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

		//构建排序
		if(!"".equals(order)){
			if(order.indexOf(".")!=-1){
				sql.append(" order by " +order);
				
			}
			
		}
		sql.append(" )t where rownum <= ? ) where rn>=?");
		
	return sql.toString();
		
		
	}

	/**
	 * 得到查询总页数的语句
	 * @param sql
	 * @return
	 */
	public static String lablecommon_pagegetcountsql(String sql){
		sql=lablecommon_delestrtostr2(sql,"rn from");
		sql=lablecommon_delepaixu(sql,"t");
		StringBuilder str = new StringBuilder("select count(1) from ");
		str.append(sql);

		return str.toString() ;
	}
	/**
	 * 排序设置修改 根据传入的字符串 和原字符串 得出修改后的字符串
	 * @param str1 原
	 * @param str2	传入修改
	 * @return
	 */
	public static String lablecommon_neworderstr(String str1,String str2){
		if(str1==null) str1="";
		if("".equals(str2)){
			
			str1="";
		}else if(str2.equals("desc")||str2.equals("asc")){
			if(str1.lastIndexOf("desc")!=-1){
				//替换字符串
				str1=lablecommon_getorder(str1,"desc",str2);
				
				
			}else if(str1.lastIndexOf("asc")!=-1){
				//如果原排序中能找到ASC
				str1=lablecommon_getorder(str1,"asc",str2);
			}else{
				//否则
				str1=str2;
			}
			
		}
		
		return str1;
	}
	/**
	 * 排序字段修改  根据传入的字符串 和原字符串 得出修改后的字符串
	 * @param str1 :原
	 * @param str2 :新加入的排序字段
	 * @param flag:删除还是添加
	 * @return
	 */
	public static String lablecommon_neworderfield(String str1,String str2,boolean flag){
		StringBuilder str = new StringBuilder();
			//判断是添加字段 还是删除字段
		if(flag){
			//首先判断原字符串中是否存在‘.’, 
			if(str1.indexOf(".")==-1){
				//如果不存在
				 str.append(str2+","+str1);
				
			}else{
				//如果存在
				//先判断是否已经存在该排序字段
				if(str1.indexOf(str2)==-1){
					str.append(lablecommon_addstr(str1,",",str2+","));
				}
				
			}
			
		}
		else{//如果是删除
			if(str1.indexOf(str2)!=-1){
				str.append(lablecommon_deletableinfo(str1,str2+","));
			}
			
		}
		
		return str.toString();
		
	}
	
	/**
	 * 分组字段修改  根据传入的字符串 和原字符串 得出修改后的字符串
	 * @param str1 :原
	 * @param str2 :新加入的排序字段
	 * @param flag:删除还是添加
	 * @return
	 */
	public static String lablecommon_newgroupfield(String str1,String str2,boolean flag){
		StringBuilder str = new StringBuilder();
			//判断是添加字段 还是删除字段
		if(flag){
			//首先判断原字符串中是否存在‘.’, 
			if(str1.indexOf(".")==-1){
				//如果不存在
				 str.append(str2+","+str1);
				
			}else{
				//如果存在
				//先判断是否已经存在该排序字段
				if(str1.indexOf(str2)==-1){
					str.append(lablecommon_addstr(str1,",",str2+","));
				}
				
			}
			
		}
		else{//如果是删除
			if(str1.indexOf(str2)!=-1){
				str.append(lablecommon_deletableinfo_tmk(str1,str2));
			}
			
		}
		
		return str.toString();
		
	}
	
	/**
	 * 在原字符串str1中的尾部查到指定字符串str2第一次出现的结束处加入指定的字符串str3
	 * @param str1 ：原
	 * @param str2:查找的字符串
	 * @param str3：要加入的字符串
	 * @return
	 */
	public static String lablecommon_addstr(String str1,String str2,String str3){
		
		StringBuilder  str = new StringBuilder();
		str.append(str1.substring(0, str1.lastIndexOf(str2)+str2.length()));
		str.append(str3);
		str.append(str1.substring(str1.lastIndexOf(str2)+str2.length()));
		return str.toString();
	}
	public static String lablecommon_getlable(Map map,String str){
		StringBuilder sb = new StringBuilder(str);
		StringBuilder sb1 =new StringBuilder();
		int index=str.indexOf("@");
		int index2=0;
		while(index!=-1){//如果有@符号
			//取出@到^之间的字符串，并进行格式处理
			String str4=str.substring(str.indexOf("@",index)+1, str.indexOf("^",index));
			sb1.append(str.substring(index2,str.indexOf("@",index)));
			sb1.append(lablecommon_getlablevalue(map,str4));
			index2= str.indexOf("^",index)+1;
			index=str.indexOf("@",index+1);
		}
		sb1.append(sb.substring(index2));
		
		return sb1.toString();
		
	}
	public static String lablecommon_getlablevalue(Map map,String field){
		//文本处理 [0] 代表字段 键 [1] 处理类型 [2] 截取长度 [3] 截取显示符号[4]是否过滤HTML[5]为空设置
		String arr[] = field.split(",");
		if(arr[1].equals("text")){//文本数据处理
			if(map.get(arr[0])==null||map.get(arr[0]).equals("")){//如果数据为null或者为“”；
				if(arr.length==6 ){
					if(arr[5] == null || arr[5].equals("")){//如果不存为空的格式设置
						return map.get(arr[0]).toString();
					}else{
						return arr[5];//如果有为空的格式设置
					}
				}else{
					return "";
				}
			}else{//如果数据不为空并且不为“”
				String val =map.get(arr[0])+"";//得到该条数据的值
				if(arr[4].equals("1")){//如果设置了过滤
					val=Html2Text(val);                           
					if(val.equals("")){//过滤之后 如果为空
						if(arr[5].equals("")){//如果不存为空的格式设置
							return val;
						}else if(!arr[5].equals("")){
							return arr[5];//如果有为空的格式设置
						}
						
					}
				}
				if(arr[2].equals("")){//如果没有设置截取
					return val;
				}else{//设置了截取
					int a = Integer.parseInt(arr[2]);
					if(val.length()>a){//如果字符串的长度大于截取的长度
						if(!arr[3].equals("")){//判断是否有截断标志
							//如果设有
							val=val.substring(0, a-arr[3].length())+arr[3];
						}else{
							val=val.substring(0, a);
						}
						
					}
					//如果字符串的长度小于等于a不用截取
					return val;

				}
			}
			//日期格式处理 ，arr[0] 字段 [1] 时间类型[2]转换格式[3]空值显示设置
		}else if(arr[1].equals("date")){
			if(map.get(arr[0])==null){//如果数据为null或者为“”；
				if(arr[3].equals("")){//如果不存为空的格式设置
					return "";
				}else{
					return arr[3];//如果有为空的格式设置
				}
			}else if(arr.length == 2){
				return map.get(arr[0]).toString();
			}else if(!arr[2].equals("")){//如果不为空， 并且有格式设置
				try{
					Timestamp time=Timestamp.valueOf((String)map.get(arr[0])) ;
					SimpleDateFormat myfmt = new SimpleDateFormat(arr[2]);
					return myfmt.format(time).toString();
				}catch (Exception e) {
					logger.error("时间类型转换异常", e);
					return map.get(arr[0]).toString();
				}	
			}else {//如果没有格式设置
				return map.get(arr[0]).toString();
				
			}
			//数字设置[0]字段[1]数字类型[2]0原数，1小数，2百分数[3]小数点后保留几位
		}else if(arr[1].equals("num")){
			if(arr[2].equals("0")){//如果是原数
				return map.get(arr[0])+"";
			}
			else if(arr[2].equals("1")){
				try{
					
					int a=Integer.parseInt(arr[3]);
					if(a>=0){
						String weishu="0.";
						for (int i = 0; i < a; i++) {
							weishu+="0";
						}
						DecimalFormat df1 = new DecimalFormat(weishu);
						return df1.format(Double.parseDouble(String.valueOf(map.get(arr[0]))));
					}else{//如果
						return map.get(arr[0])+"";
					}
				
				}catch (Exception e) {
					logger.error("小数点位数异常", e);
					return map.get(arr[0])+"";
				}
			}else{//否则为百分数
				NumberFormat nf = NumberFormat.getPercentInstance();
				return nf.format(map.get(arr[0]));	
			}
			
			
			
		}else{
			return map.get(arr[0]).toString();
		}
			
		
		
		
	}

	
	  public static String Html2Text(String inputString) {      
		   String htmlStr = inputString; // 含html标签的字符串      
		   String textStr = "";      
		   java.util.regex.Pattern p_script;      
		   java.util.regex.Matcher m_script;      
		   java.util.regex.Pattern p_style;      
		   java.util.regex.Matcher m_style;      
		   java.util.regex.Pattern p_html;      
		   java.util.regex.Matcher m_html;      		
		   java.util.regex.Pattern p_html1;      
		   java.util.regex.Matcher m_html1;      

  try {      
		String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>      
		String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>      
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式      
		String regEx_html1 = "<[^>]+";      
		p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);      
		m_script = p_script.matcher(htmlStr);      
		htmlStr = m_script.replaceAll(""); // 过滤script标签      
  
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);      
            m_style = p_style.matcher(htmlStr);      
           htmlStr = m_style.replaceAll(""); // 过滤style标签      
   
          p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);      
          m_html = p_html.matcher(htmlStr);      
           htmlStr = m_html.replaceAll(""); // 过滤html标签      
  
           p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);      
           m_html1 = p_html1.matcher(htmlStr);      
            htmlStr = m_html1.replaceAll(""); // 过滤html标签      
  
         textStr = htmlStr;      
 
      } catch (Exception e) {      
          System.err.println("Html2Text: " + e.getMessage());      
      }      
 return textStr;// 返回文本字符串      
  }     
	  public static String lablecommon_getsearchlable(String str){
		  str=str==null?"":str;
		  StringBuilder sb = new StringBuilder(str);
			StringBuilder sb1 =new StringBuilder();
			int index=str.indexOf("@lable");
			int index2=0;
			while(index!=-1){//如果有@lable符号
				//取出@lable到lable@之间的字符串，并进行格式处理
				String str4=str.substring(str.indexOf("@lable",index)+6, str.indexOf("lable@",index));
				str4=lablecommon_deletableinfo2(str4,".");
				sb1.append(str.substring(index2,str.indexOf("@lable",index)));
				sb1.append(lablecommon_getlablevalue(str4));
				index2= str.indexOf("lable@",index)+6;
				index=str.indexOf("@lable",index+1);
			}
			sb1.append(sb.substring(index2));
			
			
			return sb1.toString();
		  
		  
		  
	  }
	  /**
	   * 搜索框标签id名称的解析
	   * @param str
	   * @return
	   */
	  public static String lablecommon_getlablevalue(String str){
		  //去掉搜索框类型
		  StringBuilder sb = new StringBuilder(str);
		  StringBuilder sb1 =new StringBuilder();
		  int index=str.indexOf("^");
		  int index2=0;
		  while(index!=-1){//如果有^符号
		  	//去掉^到^之间的字符串

		  	String str4=str.substring(str.indexOf("^",index)+1, str.indexOf("^",index+1));
		  	sb1.append(str.substring(index2,str.indexOf("^",index)));
		  	
		  	index2= str.indexOf("^",index+1)+1;
		  	index=str.indexOf("^",index2+1);
		  }
		  sb1.append(sb.substring(index2));
		 
		  
		  return sb1.toString();
	  }
	  /**
	   * 由搜索框标签的内容得到
	   * @param str
	   * @return
	   */
	  public static List<Search> lablecommon_getsearchvalue(String str){
		  List<Search> s = new ArrayList<Search>();
		  
		  int index=str.indexOf("@lable");
			int index2=0;
			while(index!=-1){//如果有@lable符号
				//取出@lable到lable@之间的字符串，并进行格式处理
				String str4=str.substring(str.indexOf("@lable",index)+6, str.indexOf("lable@",index));
				Search se = new Search();
				if(str4.indexOf("^select^")!=-1){
					//如果该搜索条件为下拉框
					se.setType("2");
					
				}else if(str4.indexOf("^radio^")!=-1){
					//如果是单选按钮
					se.setType("3");
				}else if(str4.indexOf("^time^")!=-1){
					//如果是时间类型
					se.setType("4");
					
				}else{
					
					se.setType("5");
				}
				se.setName(lablecommon_getlablevalue(str4));
				se.setSearchName(lablecommon_deletableinfo2(se.getName(),"."));
				s.add(se);
				index=str.indexOf("@lable",index+1);
			}
		  return s;
		  
	  }
	  /**
	   * 通过搜索条件得到新的SQL语句
	   * @param str
	   * @param searchsql
	   * @param type :精确还是模糊查询
	   * @return
	   */
	  public static String lablecommon_getsearchsql(String str,String searchsql,int type,String lablename){
		  boolean falg=true;
		  if(type==1){
			  falg=false;
		  }
		
		 String arr[] =null;
		 
		 String sqlstr="";
			  if(searchsql==null||searchsql.equals("")){//如果不存在条件
				  return str;
			  }else{//如果有条件存在		
				  int count =0;
				  String tiaojian[]=searchsql.split(",");
				  for (String s : tiaojian) {//循环该数组
					  arr=s.split("=");
					  String info=lablecommon_searchnamedelelable(arr,lablename,falg);
					 if (!info.equals("")){
						 count++;
						 if(count==1){
							  sqlstr+="  "+ info ;
							  
						  }else{
							  sqlstr+=" and "+info;
						  }
					 }  
				}
			  }
			  //判断原来的sql语句中是否存在条件
			  if(sqlstr.equals("")){
				  return str;
			  }else{
				  StringBuilder sb= new StringBuilder();
				  boolean ifwhere=ifwhere(str);
				  if(ifwhere){//如果有 返回第一个 where最后一个字母的位置
					 int index = str.indexOf("where")+5;
					 sb.append(str.substring(0,index));
					 sb.append(sqlstr+" and ");
					 sb.append(str.substring(index));
				  }else{
					  //否则先看是否有排序 ，有排序则返回order 的位置
					  if(str.indexOf("order by")!=-1){
						  int index = str.indexOf("order by");
						  sb.append(str.substring(0,index));
							 sb.append(" where "+sqlstr+"  " );
							 sb.append(str.substring(index));
					  }
					  //如果没有排序 找)t的位置
					  else if(str.indexOf(")t")!=-1){
						  int index = str.indexOf(")t");
						  sb.append(str.substring(0,index));
							 sb.append(" where "+sqlstr+"  " );
							 sb.append(str.substring(index));
					  }
				  }
				  return sb.toString();
			  }
			 
		
	  }
	
	  
	  
	  /**
	   * 得到查询条件
	   * @param str:搜索控件名称
	   * @param lablename：标签名
	   * @param falg :是否是模糊查询 true 模糊查询
	   * @return
	   */
	  public static String lablecommon_searchnamedelelable(String []str ,String lablename,boolean falg){
		  String sqlstr="";
		  sqlstr =str[0];
		  //删除掉名字里的lablename
		  sqlstr =lablecommon_deletableinfo2(sqlstr,lablename);
		  //截取掉名字里的字段类型信息
		  boolean  iftime=false;//是否是时间类型
		  String    leixing="";//类型
		  
		  if(sqlstr.indexOf("radiotype")!=-1){
			  leixing="radiotype";
			  sqlstr =lablecommon_deletableinfo2(sqlstr,"radiotype");
			  
		  }else if(sqlstr.indexOf("selecttype")!=-1){
			  leixing="selecttype";
			  sqlstr =lablecommon_deletableinfo2(sqlstr,"selecttype");
			  
		  }else if(sqlstr.indexOf("timetype")!=-1){//时间类型
			  iftime = true;
			  if(sqlstr.indexOf("timetypesstarts")!=-1){
				  sqlstr =lablecommon_deletableinfo2(sqlstr,"timetypesstarts");
				  leixing="timetypesstarts";
			  }else if(sqlstr.indexOf("timetypesends")!=-1){
				  sqlstr =lablecommon_deletableinfo2(sqlstr,"timetypesends");
				  leixing="timetypesends";
			  }else if(sqlstr.indexOf("timetypesdengyus")!=-1){
				  sqlstr =lablecommon_deletableinfo2(sqlstr,"timetypesdengyus");
				  leixing="timetypesdengyus";
			  }
		  }
		  String val ="";
		  if(str.length!=1){//如果存在值
			  val=str[1];
		  }
		  if(leixing.equals("")){//如果是文本框类型
			  if(falg){//如果是模糊查询
				  sqlstr+=" like '%"+val+"%' ";
			  }else{
				  sqlstr+=" ='"+val+"' ";
			  }
			 return sqlstr;
		  }else if(leixing.equals("radiotype")){
			  if(val!=""){
				  sqlstr+=" ='"+val+"' ";
				  return sqlstr; 
			  }else{
				  return "";
			  }
			 
		  }else if(leixing.equals("selecttype")){
			  if(val!=""){
				  sqlstr+=" ='"+val+"' ";
				  return sqlstr; 
			  }else{
				  return "";
			  }
			  
		  }
		  if(iftime){
			  String  fuhao="";
			  if(leixing.equals("timetypesstarts")){
				  fuhao=">";
			  }else if(leixing.equals("timetypesends")){
				  fuhao="<";
			  }else if(leixing.equals("timetypesdengyus")){
				  fuhao="=";
			  }
			  if(val.equals("")){//如果没有值
				  if(falg){//且是模糊查询
					  return "";
					  
				  }else{//精确查询
					  sqlstr +=fuhao+"'"+val+"'" ;
					  return sqlstr;
				  }
				  
			  }else{//如果有值
				  sqlstr = "to_char("+sqlstr+",'yyyy-MM-dd HH:mm:ss')"+ fuhao+"'"+val+"'" ;
				  return sqlstr;
			  }
		  }
		  return "";
	  }
	  /**
	   * 判断是否存在条件
	   * @param str
	   * @return
	   */
	  public static boolean ifwhere(String str ){
		  int index =-4;
		  int count =0;
		  do {
			  index= index+4;
			  count++;
			  index=str.indexOf("where",index);
		} while (index!=-1);
		  if(count>3){
			  return true;//有条件
		  }else{
			  return false;//没有条件
		  }
	  } 
	  public static void  LableCommon_init(String pathstr){
		  	path=pathstr;
		  	
	

			}
	  public void setValue(String key, String value){
	        propertie.setProperty(key, value);
	    }

	 
	  public  void LableCommon_updDBC(){
		     	
		        propertie = new Properties();
		        try {
		            inputFile = new FileInputStream(path+"/"
		    				+ "oracle_database.properties");
		            propertie.load(inputFile);
		            inputFile.close();
		        } catch (FileNotFoundException ex){
		            System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
		            ex.printStackTrace();
		        } catch (IOException ex){
		            System.out.println("装载文件--->失败!");
		            ex.printStackTrace();
		        }  
			    this.setValue("user", "000000");
			    this.setValue("jdbcUrl", "000000");
			    this.saveFile("oracle_database.properties");
		        
		    }
	  /**
	     * 保存properties文件
	     * @param fileName
	     * @param description
	     */
	     public void saveFile(String description){
	         try {
	             outputFile = new FileOutputStream(path+"/"
		    				+ "oracle_database.properties");
	             propertie.store(outputFile, description);
	             outputFile.close();
	         } catch (FileNotFoundException e) {
	             e.printStackTrace();
	         } catch (IOException ioe){
	             ioe.printStackTrace();
	         }
	     }

	     /**
	      * 通过key值获取value
	      * @param key
	      * @return
	      */
	      public String getValue(String key){
	          if(propertie.containsKey(key)){
	              String value = propertie.getProperty(key);//得到某一属性的值
	              return value;
	          }
	          else
	              return "";
	      }
	      /**
	       * 得到传参的sql（列表标签）
	       * @param sql
	       * @param str
	       * @return
	       */
	      public  static String  getchuanzhisql(String  sql,String  str){
	    	  StringBuilder sb = new StringBuilder();
	    	  if(sql.indexOf("where")==-1){
	    		  if(sql.indexOf("order by")!=-1){
					  int index = sql.indexOf("order by");
					  sb.append(sql.substring(0,index));
						 sb.append(" where "+str+"  " );
						 sb.append(sql.substring(index));
				  }else{
					  sb.append(sql+"  where "+str);
				  }
	    	  }else{
	    		  	 int index = sql.indexOf("where")+5;
					 sb.append(sql.substring(0,index));
					 sb.append(" "+str+" and ");
					 sb.append(sql.substring(index));
	    	  }
	    	  return  sb.toString();
	    	  
	      }
	      /**
	       * 得到传参的sql（分页标签）
	       * @param sql
	       * @param str
	       * @return
	       */
	      public  static String  getchuanzhipagesql(String  sql,String  str){
	    	  StringBuilder sb = new StringBuilder();
	    	  if(!ifwhere(sql)){//是否存在条件
	    		  if(sql.indexOf("order by")!=-1){
					  int index = sql.indexOf("order by");
					  sb.append(sql.substring(0,index));
						 sb.append(" where "+str+"  " );
						 sb.append(sql.substring(index));
				  } else if(str.indexOf(")t")!=-1){
					  int index = sql.indexOf(")t");
					  sb.append(sql.substring(0,index));
						 sb.append(" where "+str+"  " );
						 sb.append(sql.substring(index));
				  }
	    	  }else{
	    		  	 int index = sql.indexOf("where")+5;
					 sb.append(sql.substring(0,index));
					 sb.append(" "+str+" and ");
					 sb.append(sql.substring(index));
	    	  }
	    	  return  sb.toString();
	      } 
}
