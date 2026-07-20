package com.sopia.schedule;

import java.io.File;

import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.lable.entites.Lable;
import com.sopia.schedule.entities.CustomReport;

public class CommonCustom {
	public static String UPLOADURL = "admin\\customReports\\demo\\";//上传模板路径
	
	//验证某路径下所有文件中是否包含某个文件
	public static boolean checkFilenameIsExistInFolder(String filename){
		boolean flag = true;
		File file = new File(J2EEFileUtil.getRealPath("/") + UPLOADURL);
		File[] filelist = file.listFiles();
		for(int i=0;i<filelist.length;i++){
			if(!filelist[i].isDirectory()){
				if(filelist[i].getName().equals(filename)){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	//验证某路径下所有文件中是否包含某个文件(某个指定文件除外)
	public static boolean checkFilenameIsExistInFolderExceptfilename_(String filename,String filename_){
		boolean flag = true;
		File file = new File(J2EEFileUtil.getRealPath("/") + UPLOADURL);
		File[] filelist = file.listFiles();
		for(int i=0;i<filelist.length;i++){
			if(!filelist[i].isDirectory()){
				if(!filelist[i].getName().equals(filename_) && filelist[i].getName().equals(filename)){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	public static CustomReport getnewfieldstr(CustomReport oldlable,CustomReport lable){
		//判断传入的表名在原标签中是否存在
		CustomReport l =new CustomReport();
			
		if(lable.getTableinfo().indexOf(oldlable.getTableinfo().trim(), 0)==-1){
			//如果不存在
			
			l.setTableinfo(oldlable.getTableinfo()+"-");
		}
		String fieldStr="";
		//判断标签名是否存在
		String arr[] =oldlable.getTablefield().trim().split("-");	
		for (String string : arr) {//循环标签		
				if(lable.getTablefield().indexOf(string, 0)==-1){
					fieldStr+=string+"-";
				}				
		}
		if(fieldStr.equals("")) fieldStr=null;
		l.setTablefield(fieldStr);
		return l;
		
		
	}
	
	/**
	 * 
	 * @param oldinfostr
	 * @param newinfostr
	 * @return
	 */
	public static String getnewtablestr(String oldinfostr,String newinfostr){
		String appendstr = "";
		if(oldinfostr.indexOf(newinfostr)==-1){
			appendstr += newinfostr + "-";
		}
		return oldinfostr + appendstr;
	}
	
	/**
	 * 
	 * @param oldfieldstr
	 * @param newfieldstr
	 * @return
	 */
	public static String getnewfieldstr(String oldfieldstr,String newfieldstr){
		String[] newfields_arr = newfieldstr.split("-");
		String appendstr = "";
		for(int i=0;i<newfields_arr.length;i++){
			if(!newfields_arr[i].equals("")){
				if(oldfieldstr.indexOf(newfields_arr[i])==-1){
					appendstr += newfields_arr[i] + "-";
				}
			}
		}
		return oldfieldstr + appendstr;
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
	 * 列表标签SQL语句解析
	 * @param table:表信息
	 * @param field:字段信息
	 * @param sqlC:条件信息
	 * @param order:排序信息
	 * @param pagesize:查询列数
	 * @return
	 */
	public static String lablecommon_getsql(ElNode department ,String table,String field,String sqlC,String order,Integer pagesize){
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
		sql.append(" join ("
				+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.generateSQLByTree("department", department, true)
				+ ") dep on dep.id=d.id ");
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
		//构建排序
		if(order != null && !"".equals(order)){
			if(order.indexOf(".")!=-1){
				sql.append(" order by " +order);
				
			}
			
		}
	
		
	return sql.toString();
		
		
	}

}
