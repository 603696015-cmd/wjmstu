package com.sopia.schedule.dao;

import java.util.List;
import java.util.Map;

import com.sopia.common.ElException;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.Table;
import com.sopia.lable.entites.TableField;
import com.sopia.schedule.entities.CustomReport;
import com.sopia.schedule.entities.CustomReportJSZ;

public interface CustomReportsDao {
//	/**
//	 * 添加报表标签
//	 * @param customReport
//	 * @throws ElException
//	 */
	public int addCustomReport(CustomReport customReport) throws ElException;
//	
//	/**
//	 * 报表标签列表
//	 * @param pageNow
//	 * @param pageSize
//	 * @return
//	 * @throws ElException
//	 */
	public List<CustomReport> listCustomReports(int pageNow,int pageSize) throws ElException;
	public int listCustomReportsSize() throws ElException;
//	
//	/**
//	 * 根据name查询报表标签
//	 * @param id
//	 * @return
//	 * @throws ElException
//	 */
	public CustomReport queryCustomReportById(int id) throws ElException;
//	
//	public CustomReport lable_getlableby(String tableName,String lableName) throws ElException;
	
	public CustomReport lable_getlableby(String tableName,String lableName) throws ElException;
	
	public CustomReport queryCustomReportByJSP(String jspName) throws ElException;
	
	
	public void lable_updlabletableinfoAndField(String tableName,String name,CustomReport customReport) throws ElException;
	
	public void updateCustomReportById(CustomReport customReport) throws ElException;
	public void updateCustomReportByTree(CustomReport customReport) throws ElException;
	public void updateCustomReportBySearch(CustomReport customReport) throws ElException;
	public void updateCustomReportFinal(CustomReport customReport) throws ElException;
	public void updateCustomReportByResultPage(CustomReport customReport,String filename) throws ElException;
	
	public CustomReport lable_getlablesqllable(String tableName,CustomReport customReport)throws ElException;
	
	public List<TableField> getFieldByTableName(String tableName) throws ElException ;
	
	public List<Table>  getTableByArr(String tableArr[]) throws ElException;
	
	public List<TableField> getTableFieldByField(String arr[])throws ElException;
	
	/**
	 * 删除报表标签
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Map<String,String> deleteLableById(int id) throws ElException;
	
	/**
	 * 标签解析结果
	 * @param list
	 * @param sql
	 * @return
	 * @throws ElException
	 */
	public List<Map<String,Object>> getMap(List<TableField> list ,String sql ,int pageNow,int pageSize,CustomReport customReport) throws ElException;
	
	/**
	 * 添加计算组信息
	 * @param customreportid
	 * @param jisuanzuname
	 * @throws ElException
	 */
	public void insertjisuanzu(int customreportid,String jisuanzuname,int type) throws ElException;
	
	/**
	 * 获取计算组
	 * @param customreportid
	 * @return
	 */
	public List<CustomReportJSZ> showzijisuan(int customreportid,int type) throws ElException;
	
	/**
	 * 插入计算公式
	 * @param customreportid
	 * @param value
	 * @throws ElException
	 */
	public void updateCustomReport_jisuanzu_by_columnname(String columnname,String value,int type,int formatnumber,int checkvalue,int customReport_relatetype,String relatecolumnname) throws ElException;
	
	/**
	 * 根据id查询计算组
	 * @param customreportid
	 * @return
	 * @throws ElException
	 */
	public List<CustomReportJSZ> queryCustomReport_jisuanzu_list_byid(int customreportid) throws ElException;
	
	/**
	 * 根据columnname查询计算方式
	 * @param columnname
	 * @return
	 * @throws ElException
	 */
	public CustomReportJSZ QueryJSZByColumnname(String columnname)throws ElException;
	
	
	public int queryCountByTableAndColumn(String tablename,String columnname,String id) throws ElException;
	public double querySumByTableAndColumn(String tablename,String column1,String column2,String id) throws ElException;
	
	/**
	 * 判断需要添加的计算组名称是否已经存在
	 * @return
	 * @throws ElException
	 */
	public boolean checkJSZNameIsExist(String jszName,int customreportid) throws ElException;
	
	/**
	 * 修改统计字段排序
	 * @throws ElException
	 */
	public void changeJSZId(int customreportid,String value) throws ElException;
	/**
	 * 删除统计字段
	 * @param id
	 * @throws ElException
	 */
	public void deleteJSZById(int id) throws ElException;
	

}
