package com.sopia.pfms.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.BeanGenerator.BeanSqlSplice;
import com.sopia.common.BeanGenerator.CglibBean;
import com.sopia.common.BeanGenerator.TableCreateBean;
import com.sopia.pfms.entities.IC_column_qiuji_qiuhe;
import com.sopia.pfms.entities.RelateColumnInformation;
import com.sopia.pfms.entities.InsuranceCategories;
import com.sopia.pfms.entities.RelateTable;

public interface InsuranceCategoriesDao {
	/**
	 * 获取险种列表
	 */
	public List<InsuranceCategories> getICList(InsuranceCategories ic, int pageNow, int pageSize) throws ElException;
	
	public int getICListSize(InsuranceCategories ic) throws ElException;
	/**
	 * 验证险种表 是否存在
	 * @param ic
	 * @return
	 * @throws ElException
	 */
	public boolean CheckIC(InsuranceCategories ic) throws ElException;
	/**
	 * 根据id获取险种信息
	 * @param ic
	 * @return
	 * @throws ElException
	 */
	public InsuranceCategories getByICId(int id) throws ElException; 	
	/**
	 * 根据TableName获取险种信息
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public InsuranceCategories getByICTableName(String name) throws ElException;
	/**
	 * 删除险种
	 * @param ic
	 * @throws ElException
	 */
	public void IC_U_Delete(InsuranceCategories ic) throws ElException ;
	/**
	 * 增加险种管理表数据
	 * @param ic
	 * @return
	 * @throws ElException
	 */
	public int addIC(InsuranceCategories ic) throws ElException;
	/**
	 * 创建险种详情表
	 * @param ic_date
	 * @throws ElException
	 */
	public void createIC_Date(BeanSqlSplice ic_date) throws ElException;
	/**
	 * 增加险种详情表初始化数据
	 * @param ic_date
	 * @throws ElException
	 */
	public void addIC_DateInit(BeanSqlSplice ic_date) throws ElException;
	/**
	 * 根据表名获取表结构
	 */
	public List<TableCreateBean> getByIC_U_tableName(String tableName) throws ElException;
	/**
	 * 获取险种详情表信息
	 * @param tableName
	 * @return
	 * @throws ElException
	 */
	public CglibBean getByIC_U_ByTableName(String tableName) throws ElException; 
	/**
	 * 验证列名是否存在
	 * @param tableName
	 * @param Column
	 * @return
	 * @throws ElException
	 */
	public boolean CheckIC_U_Column(String tableName , String Column_Name) throws ElException;
	/**
	 * 验证表内是否存在BLOB ，  一个表只能存在一个blob 
	 * @param tableName
	 * @return
	 * @throws ElException
	 */
	public boolean CheckIC_U_Column_Blob(String tableName) throws ElException;
	/**
	 * 险种详情信息增加列
	 * @param bcsql
	 * @throws ElException
	 */
	public void addIC_U_Column(BeanSqlSplice bcsql) throws ElException;
	/**
	 * 更新险种模板
	 * @param urlORcss ( url  or  css)
	 * @param demoName
	 * @throws ElException
	 */
	public void updateDemoORCss(int id, String demoName,String urlORcss) throws ElException;
	/**
	 * 
	 * 增加或者更新险种数据    
	 * type 填 add 为新增 填ID为更新
	 * @param sql
	 * @param tableName
	 * @param typeOrId  
	 * @return
	 * @throws ElException
	 */
	public int addOrUpdateIC_U_Date(String sql,String tableName,String typeOrId,String blob) throws ElException;
	
	/**
	 * 获取选择的相关表
	 * @param type
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	@SuppressWarnings("unchecked")
	public List searchRelateTables(String tablename,String type,int pageNow, int pageSize) throws ElException;
	
	/**
	 * 获取选择的相关表数量
	 * @return
	 * @throws ElException
	 */
	public int searchRelateTablesSize(String type) throws ElException;
	
	/**
	 * 根据表名获取列名和列注释和列类型
	 * @param tableName
	 * @return
	 * @throws ElException
	 */
	public RelateTable getRelateTableByTableName(String tableName) throws ElException;
	
	/**
	 * 根据险种表自动获取数据
	 * @param tableName
	 * @return
	 * @throws ElException
	 */
	public List<RelateColumnInformation> getrelateColumns(String tableName) throws ElException;
	
	public String getrelateColumnValueByRelateColumnName(int id,String RelateTableName,String relateColumnName) throws ElException;
	
	/**
	 * 根据表查找求积关联字段
	 * @param tableName
	 * @return
	 * @throws ElException
	 */
	public List<IC_column_qiuji_qiuhe> getQiujiColumns(String tableName) throws ElException;
	
	/**
	 * 自动求积返回求积字段
	 * @param columnName
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String checkColumnNameIsQiuji(String columnName,String tablename) throws ElException;
	
	/**
	 * 修改字段页面显示和范围
	 * @param columnName
	 * @param tableName
	 * @param changeValue
	 * @throws ElException
	 */
	public void changeValueByCname(String columnName,String tableName,String changeValue ) throws ElException;
	
	public TableCreateBean getTSB(String tablename,String columnname) throws ElException;
	
	public void updateColumn(String tablename,String columnname,String value) throws ElException;
}
