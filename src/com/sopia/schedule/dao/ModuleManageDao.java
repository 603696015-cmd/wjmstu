package com.sopia.schedule.dao;

import java.util.List;
import java.util.Map;

import com.sopia.common.ElException;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.Tags;

public interface ModuleManageDao
{
	/**
	 * 添加模块时验证表名是否已经存在
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public int checkTableIsExist(String tablename) throws ElException;
	/*
	 * 复制模块
	 * 添加数据库表，包含固有字段，id，ststus，userid
	 * 添加序列
	 */
	public void add_module(ModuleManage moduleManage) throws ElException;
	//public void add_module(String modulename) throws ElException;

	
	/*
	 * 查询模块列表，分页
	 */
	public List<ModuleManage> select_mymodule(ModuleManage module_s,int pageNow,int pageSize)  throws	 ElException;
	public int select_mymodule_count(ModuleManage module)  throws	 ElException;
	public void updateDemoORCss(int id, String demoName,String urlORcss) throws ElException;
	/*
	 * update
	 */
	public void update_module_by_id(ModuleManage module) throws ElException;
	
	/*
	 * 查询单个
	 */
	public ModuleManage select_module_by_id(int id) throws ElException;
	public ModuleManage select_module_by_TableName(String tablename) throws ElException;
	
	/*
	 * 查询所有模块，非分页、列表
	 */
	public List<ModuleManage> select_mymodule(int pageNow,int pageSize)  throws ElException;
	public int select_mymodule_size(int pageNow,int pageSize)  throws ElException;
	
	/*
	 * 获得自定义列表
	 * type:用于模块间的计算==type='calculate'的时候
	 * tablename:自定义表名，通过表名获得自定义表信息
	 */
	public List<Tags> select_designe_field_by_tablename(String type,String tablename)
			throws ElException;
	
	
	/*
	 * 获得需要查询的列表页功能代码
	 */
	public List<String> select_my_charge_by_tablename(String tablename,int userid) throws ElException;
	
	/**
	 * 设置数据自动读取时根据tablename获取fromtablename
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String getFromtablenameByTablename(String tablename) throws ElException;
	
	/**
	 * 添加tb_user 
	 * @param tablename
	 * @throws ElException
	 */
	public void addTb_user(String tablename ) throws ElException;
	
	/**
	 * 根据表名修改模块管理表中对应记录的是否开启自定义审核
	 * @param tablename
	 * @param is_enabled
	 * @throws ElException
	 */
	public void update_module_by_tablename(String tablename,int is_enabled) throws ElException;
	
}
