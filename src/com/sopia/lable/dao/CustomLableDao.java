package com.sopia.lable.dao;

import java.util.List;
import java.util.Map;


import com.sopia.common.ElException;
import com.sopia.lable.entites.CirculationListLable;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.LableTree;
import com.sopia.lable.entites.SearchLable;
import com.sopia.lable.entites.Table;
import com.sopia.lable.entites.TableField;
import com.sopia.schedule.entities.CustomReport;

public interface CustomLableDao {
	/**
	 * 返回系统表信息
	 * @return
	 */
	public List<Table> lable_getsystable()throws ElException;
	/**
	 * 返回用户自定义表信息
	 * @return
	 */
	public List<Table> lable_getusertable()throws ElException;
	/**
	 * 通过标签名称从标签表中 得到 查询表信息
	 * @param name   :标签名称
	 * @param tableName :标签表名称
	 * @return
	 * @throws ElException
	 */
	public List<Table> lable_getlabletableandfield(String  name,String tableName) throws ElException;
	/**
	 * 保存标签名
	 * @param name :标签名
	 * @throws ElException
	 */
	public void   lable_addlable(String name ,int type) throws ElException;
	/**
	 * 保存标签名
	 * @param name
	 * @param type
	 * @throws ElException
	 */
	public void lable_addsearchlable(String name,int type) throws ElException ;
	/**
	 * 通过表名得到字段信息
	 * @param tableName:表名
	 * @return
	 * @throws ELException
	 */
	public List<TableField> lable_getFieldByTableName(String tableName) throws  ElException;
	/**
	 * 通过标签名及标签标名得到表 、字段信息
	 * @param tableName	:表名
	 * @param lableName:标签名
	 * @return
	 * @throws ElException
	 */
	public Lable lable_getlableby(String tableName,String lableName) throws ElException;
	/**
	 * 修改标签 表及字段信息
	 * @param tableName
	 * @param name
	 * @param lable
	 * @throws ElException
	 */
	public void lable_updlabletableinfoAndField(String tableName,String name,Lable lable) throws ElException;
	/**
	 * 删除标签 表及字段信息
	 * @param tableName:要删除的标签表名
	 * @param lable:删除后的修改修心
	 * @param lableName:标签名称
	 * @throws ElException
	 */
	public  void  lable_delelableTableInfo(String tableName,Lable lable,String lableName) throws ElException;
	
	/**
	 * 修改标签的sql信息
	 * @param tableName:标签表名
	 * @param sqlstr:sql信息
	 * @param lablename:标签名
	 * @throws ElException
	 */
	public void lable_updlablesql(String tableName,CirculationListLable cilable) throws ElException;
	/**
	 * 修改搜索标签的sql信息
	 * @param tableName
	 * @param cilable
	 * @throws ElException
	 */
	public void lable_updsearchlablesql(String tableName,SearchLable cilable) throws ElException;
	/**
	 * 修改标签循环体和sql语句
	 * @param tableName
	 * @param cilable
	 * @throws ElException
	 */
	public void lable_updlableHTML(String tableName,Lable cilable)throws ElException;
	/**
	 * 查询该标签中的 查询语句等信息
	 * @param tableName
	 * @param lable
	 * @return
	 * @throws ElException
	 */
	public Lable lable_getlablesqllable(String tableName,Lable lable)throws ElException;
	/**
	 * 查询搜索框标签的详细信息
	 * @param tableName
	 * @param lableName
	 * @return
	 * @throws ElException
	 */
	public SearchLable lable_getlablesearchlable(String tableName,String lableName)throws ElException;
	/**
	 * 得出标签的所有信息
	 * @param tableName
	 * @param lablename
	 * @return
	 * @throws ElException
	 */
	public Lable lable_getlablesqllablesql(String tableName,String lablename)throws ElException;
	/**
	 * 同过包含字段信息的数组 得出字段详细信息
	 * @param arr
	 * @return
	 * @throws ElException
	 */
	
	public List<TableField> lable_getTableFieldByField(String arr[])throws ElException;
	/**
	 * 保存搜索标签的搜索框设置
	 * @param tableName
	 * @param cilable
	 * @throws ElException
	 */
	public void lable_updsearchlablesearchset(String tableName,SearchLable cilable) throws ElException;
	/**
	 * 修改排序设置
	 * @param updstr
	 * @param tableName
	 * @param name
	 * @throws ElException
	 */
	public void lable_updlableorder(String updstr,String tableName,String name) throws ElException;
	/**
	 * 修改分组设置
	 * @param updstr
	 * @param tableName
	 * @param name
	 * @throws ElException
	 */
	public void lable_updlablegroup(String updstr,String tableName,String name) throws ElException;
	/**
	 * 得到排序字符串信息
	 * @param tableName
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public String lable_getorderStr(String tableName,String name) throws ElException;
	/**
	 * 得到分组字符串信息
	 * @param tableName
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public String lable_getgroupStr(String tableName,String name) throws ElException;
	/**
	 * 通过传入的表名数组，返回表信息
	 * @param tableArr
	 * @return
	 * @throws ElException
	 */
	public List<Table>  Lable_getTableByArr(String tableArr[]) throws ElException;
	/**
	 * 标签解析结果
	 * @param list
	 * @param sql
	 * @return
	 * @throws ElException
	 */
	public List<Map<String,Object>> getMap(List<TableField> list ,String sql ,CustomReport customReport) throws ElException;
	/**
	 * 分页标签解析结果
	 * @param list
	 * @param sql
	 * @param pagesize
	 * @param pagenow
	 * @return
	 * @throws ElException
	 */
	public List<Map> getpageMap(List<TableField> list ,String sql ,int pagesize,int pagenow) throws ElException;
	/**
	 * 分页标签得到页数
	 * @param sql
	 * @return
	 * @throws ElException
	 */
	public int  lable_getsqlsagecount(String sql) throws ElException;
	/**
	 * 得到标签列表
	 * @return
	 * @throws ElException
	 */
	public List<Lable> lable_getalllable(int pageNow,int pageSize,LableTree lableTree,Lable lable)throws ElException;
	
	public int lable_getalllableSize(LableTree lableTree,Lable lable)throws ElException;
	
	
	public void updateLableTreeid(String name,int labletreeid,String table) throws ElException;
	
	public SearchLable getLableByTablenameAndName_search(String lableName) throws ElException;
	public void insertDB_copy_search(SearchLable lable) throws ElException;
	public Lable getLableByTablenameAndName_loop(String lableName) throws ElException;
	public void insertDB_copy_loop(Lable lable) throws ElException;
	
	public boolean checkNameIsExist(String lablename,String table) throws ElException;

}
