package com.sopia.common.BeanGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sopia.pfms.impl.InsuranceCategoriesDaoImpl;

public class TableCreateBean { 

	private InsuranceCategoriesDaoImpl ICimpl ; 
	public String tableName;//表名
	public String column_name;//列名
	public String column_comments;//注释   注意 该列的 显示名称与显示方式保存在此位置 中间用== 隔开
	public String data_type;//列数据类型
	public int data_length;//列长度 
	public String data_value;//列数据 
	public int tableName_ID;//表内ID 

	public List<TableCreateBean> TSBs;
	//下面只有get方法  数据来源于column_comments 分割
	public String CName;//名称
	public String Cview;//视图显示方式
	
	public TableCreateBean(String tableName){
		this.tableName = tableName;
	}
	public List<TableCreateBean> getTSBs() {
		return TSBs;
	}
	/**
	 * 查看表数据结构
	 * @return
	 * @throws Exception
	 */
	public List<TableCreateBean> getTSBs_() throws Exception{
		return this.getICimpl().getByIC_U_tableName(this.getTableName());
	}
	
	/**
	 * 查看表数据结构带表内ID的value值
	 * @return
	 * @throws Exception
	 */
	public List<TableCreateBean> getTSBs_value() throws Exception{
		return this.getICimpl().getByIC_U_tableNameAndValue(this.getTableName(),this.tableName_ID);
	}
	public Map getTSBs_viewNAME() throws Exception{
		List<TableCreateBean> bean = this.getICimpl().getByIC_U_tableName(this.getTableName());
		Map map = new HashMap<String, TableCreateBean>();  
		  for(TableCreateBean stu : bean){
		   if(stu==null){
		    continue;
		   }  
		   map.put(stu.getColumn_name(), stu.getCName());
		  }
		return map;
	}
	public void setTSBs(List<TableCreateBean> bs) {
		TSBs = bs;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public int getData_length() {
		return data_length;
	}
	public void setData_length(int data_length) {
		this.data_length = data_length;
	}

	public String getData_type_() {
		if(this.data_type.equals("NUMBER"))
			return "java.lang.Integer";
		if(this.data_type.equals("VARCHAR2"))
			return "java.lang.String";
		if(this.data_type.equals("DATE"))
			return "java.sql.Timestampg"; 
		return"未知";
	}

	public CglibBean createBean() throws Exception{ 
        Map propertyMap = new HashMap();  
        //查询表结构
        this.TSBs = this.getICimpl().getByIC_U_tableName(this.getTableName());
        //设置类成员属性  
        for(int i = 0 ; TSBs.size() > i ; i++){ 
	        propertyMap.put(TSBs.get(i).getColumn_name(), Class.forName(this.getData_type_()));  
        } 
        //生成Bean
        CglibBean bean = new CglibBean(propertyMap);  
		return bean;
	}
	private InsuranceCategoriesDaoImpl getICimpl() {
		return new InsuranceCategoriesDaoImpl();
	}
	public String getTableName() {
		return tableName.toUpperCase();//把小写全部改为大写
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumn_comments() {
		return column_comments;
	}
	public void setColumn_comments(String column_comments) {
		this.column_comments = column_comments;
	}
	// 在column_comments 保存方式 例如   姓名==单行显示
	public String getCName() {
		if(this.column_comments!=null){
			String [] cname = this.column_comments.split("==");
			return cname[0];
		}
		return "";
	}
	public String getCview() {
		if(this.column_comments!=null){
			String [] cview = this.column_comments.split("==");
			return cview[1];
		}
		return "";
	} 
	public String getCview_value() {//页面显示方式范围
		if(this.column_comments!=null){
			String [] cview = this.column_comments.split("==");
			return cview[2];
		}
		return ""; 
	}
	public String getData_value() {
		return data_value;
	}
	public void setData_value(String data_value) {
		this.data_value = data_value;
	}
	public int getTableName_ID() {
		return tableName_ID;
	}
	public void setTableName_ID(int tableName_ID) {
		this.tableName_ID = tableName_ID;
	}
}
