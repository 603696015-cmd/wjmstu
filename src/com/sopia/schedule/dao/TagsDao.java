package com.sopia.schedule.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.Department;
import com.sopia.pfms.entities.IC_column_qiuji_qiuhe;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.schedule.entities.AuditMark;
import com.sopia.schedule.entities.CurrentUser;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.Eluser;
import com.sopia.schedule.entities.Tags;
import com.sopia.schedule.entities.TagsMark;
import com.sopia.schedule.entities.Tb_calculate;
import com.sopia.schedule.entities.UserSign;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public interface TagsDao
{

//	/**
//	 * 查询三级节点
//	 * @param userid
//	 * @return
//	 * @throws ElException
//	 */
//	public int getUserIdByUserId(int userid) throws ElException;
	
	/*
	 * 添加自定义列表项目
	 */
	public void insert_designe_field(int type,Tags tags) throws ElException;
	
	/**
	 * 插入计算表
	 * @param tags
	 * @param relate_columnname_calculate
	 * @param relate_tablename_calculate
	 * @throws ElException
	 */
	public void insert_tb_calculate(Tags tags,String relate_columnname_calculate,String relate_tablename_calculate) throws ElException;
	/*
	 * 获得自定义列表
	 * tablename:自定义表名，通过表名获得自定义表信息
	 */
	public List<Tags> select_designe_field_by_tablename(String tablename)
			throws ElException;
	
	public String getAutoColumns(String tablename)
	throws ElException;
	
	
	/**
	 * 相关字段获取自定义列表
	 * @param tablename
	 * @param produce_table
	 * @return
	 * @throws ElException
	 */
	public List<Tags> select_designe_field_by_Producetablename(String tablename,String produce_table) 
			throws ElException;
	
	/**
	 * 字段类型为时间类型
	 * @param tablename
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public List<Tags> select_designe_field_by_tablename_time(String type,String tablename)
	throws ElException;
	
	/*
	 * 获得单个自定义列表
	 */
	public Tags select_designe_field_by_id(int id) throws ElException;
	public int select_designe_field_id_by_columnName(String columnName) throws ElException;


	/*
	 * 获取单个表信息，查看
	 * list_tags:自定义字段，表结构，由select_designe_field_by_tablename返回
	 */
	public List<Tags> select_designe_field_content_by_id(List<Tags> list_tags,
			String tablename, int id) throws ElException;

	/*
	 * 更新表信息
	 * hm:表更新内容，<字段名,字段值>
	 * id:表信息id
	 */
	public void update_designe_field_content_by_id(Map<String, String> hm,
			String tablename, int id) throws ElException;

	
	
	
	/*
	 * 管理页面是否显示
	 * 更新自定义表信息
	 */
	public void manage_designe_field(List<Tags> list) throws ElException;
	
	
	/*
	 * 更新自定义字段
	 */
	public void update_designe_field(Tags tags) throws ElException;
	
	
	/*
	 * 检测富文本是否已有
	 */
	public String checkRichtext(String tablename)  throws ElException;
	
	
	/*
	 * 删除单条信息
	 */
	public void delete_from_tablename_by_id(String tablename,int id) throws ElException;
	
	/**
	 * 根据id、删除过程表中的相关数据
	 * @param produce_table
	 * @param id
	 * @throws ElException
	 */
	public void deleteProduceTableById(String produce_table,int id) throws ElException;
	
	
	
	//----------------------------------------------------------------------------------------
	
	
	/*!!!!!!!>1
	 * 组合查询 获取要显示的表信息,列表显示
	 * list_tag:自定义表信息；由select_designe_field_by_tablename返回
	 * hm:组合搜索条件
	 * userid:用户id，eluser表
	 */
	public List<Map<String, String>> select_my_tableinfo_by_userid(
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			int userid, int pageNow, int pageSize) throws ElException;

	public int select_my_tableinfo_by_userid_count(int type,List<Tags> list_tags,
			Map<String, String> hm, String tablename, int userid)
			throws ElException;
	
	public int select_my_tableinfo_by_userid_count_1(int type,List<Tags> list_tags,
			Map<String, String> hm, String tablename, int userid)
			throws ElException;
	
	
	/*!!!!!!!>1
	 * 组合查询 获取要显示的表信息,负责列表显示
	 * list_tag:自定义表信息；由select_designe_field_by_tablename返回
	 * hm:组合搜索条件
	 * userid:用户id，eluser表
	 */
	public List<Map<String, String>> select_my_tableinfo_by_principalid(
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			int principalid, int pageNow, int pageSize) throws ElException;

	public int select_my_tableinfo_by_principalid_count(List<Tags> list_tags,
			Map<String, String> hm, String tablename, int principalid)
			throws ElException;
	
	
	/*!!!!!!!>1
	 * 组合查询 获取要显示的表信息,列表显示 全部通过
	 * list_tag:自定义表信息；由select_designe_field_by_tablename返回
	 * hm:组合搜索条件
	 * userid:用户id，eluser表
	 */
	public List<Map<String, String>> select_my_pass_tableinfo(int status,
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			int pageNow, int pageSize,ElNode department,String order) throws ElException;

	public int select_my_pass_tableinfo_count(int status,List<Tags> list_tags,
			Map<String, String> hm, String tablename,ElNode department)
			throws ElException;
	
	
	
	/*!!!!!!!!>2
	 * 添加表信息
	 * hm:表信息，map表，<字段名,字段值>
	 * type=1,插入普通表，2，过程表，3，结果表
	 */
	public int insert_tableinfo_by_tablename(int type,Map<String, String> hm,
			String tablename, int userid) throws ElException;
	public int insert_tableinfo_by_tablename_status9(int type,Map<String, String> hm,
			String tablename, int userid) throws ElException;
	public void insert_into_tb_pic(String tablename,Map<String,String> map,int entityid) throws ElException;
	public void update_tb_pic(String tablename,Map<String,String> map,int entityid) throws ElException;
	public void insert_tableinfo_by_tablename_relatecolumn(int userid,String parameters,String tablename) throws ElException;
	
	
	/*!!!!!!!!>3
	 * 部门查询，通过左右id查询
	 * department：通过左右id查询部门情况
	 */
	public List<Map<String, String>> select_my_tableinfo_by_dep(
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			ElNode department, String order,int pageNow, int pageSize)
			throws ElException;

	public int select_my_tableinfo_by_dep_count(List<Tags> list_tags,
			Map<String, String> hm, String tablename, Department department)
			throws ElException;
	
	
	/*!!!!!!!!>4
	 * 部门与负责人查询，通过左右id和负责人id查询
	 * department：通过左右id查询部门情况
	 */
	public List<Map<String, String>> select_my_tableinfo_by_dep_principal(Tags tags,
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			Department department, int search_control,int principalid,int pageNow, int pageSize)
			throws ElException;

	public int select_my_tableinfo_by_dep_principal_count(Tags tags,List<Tags> list_tags,
			Map<String, String> hm, String tablename, Department department,int search_control,int principalid)
			throws ElException;
	
	public List<Map<String, String>> select_my_tableinfo_by_dep_principal_with_judge(Tags tags,
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			Department department, int search_control,int principalid,int pageNow, int pageSize)
			throws ElException;
	
	public int select_my_tableinfo_by_dep_principal_count_with_judge(Tags tags,List<Tags> list_tags,
			Map<String, String> hm, String tablename, Department department,int search_control,int principalid)
			throws ElException;
	
	
	//----------------------------------------------------------------------------------------
	
	/*
	 * 非组合查询 获取要显示的表信息,列表显示
	 */
	public List<Map<String, String>> select_my_tableinfo_by_tablename(
			List<Tags> list_tags, String tablename, int pageNow, int pageSize)
			throws ElException;

	public int select_my_tableinfo_by_tablename_count(List<Tags> list_tags,
			String tablename) throws ElException;

	
	//----------------------------------------------------------------------------------------
	
	/*
	 * 审核
	 */
	//提交初审
	public void commit_verity_pass_tags(String tablename,int id) throws ElException;
	/*
	 *初审
	 */ 
	//通过
	public void verify_pass_tags(String tablename,int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder) throws ElException;
	//不通过
	public void verify_nopass_tags(String tablename,int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder) throws ElException;
	
	/*
	 *终审
	 */
	//通过
	public void verify_pass_final_tags(String tablename,int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder) throws ElException;
	//不通过
	public void verify_nopass_final_tags(String tablename,int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder) throws ElException;

	//申请修改
	public void apply_update(String tablename,int id) throws ElException;
	//申请删除
	public void apply_del(String tablename,int id) throws ElException;
	//允许修改
	public void allow_update(String tablename,int id) throws ElException;
	//允许删除
	public void allow_del(String tablename,int id) throws ElException;
	//不允许修改
	public void noallow_update(String tablename,int id) throws ElException;
	//不允许删除
	public void noallow_del(String tablename,int id) throws ElException;
	//清空
	public void allow_del(String tablename) throws ElException;
	
	
	
	//删除相关
	public void delete_relate_by_relateid_and_conlumnname(String column_in_result_table_,String columns_in_produce_table_,
			String columnname,String tablename,int relateid,int id) throws ElException;
	
	
	
	/*
	 * 获取负责人表eluser
	 */
	public List<Eluser> select_eluser_by_dep(int is_judge_for_user,String tablename,Eluser eluser,ElNode department,int pageNow, int pageSize,int nid) throws ElException;
	public int select_eluser_by_dep_count(int is_judge_for_user,String tablename,Eluser eluser,ElNode department,int pageNow, int pageSize,int nid) throws ElException;
	
	/*
	 * 修改负责人
	 */
	public void update_principal(String tablename,int id,int principalid) throws ElException;
	
	
	/*
	 * 获取sum
	 */
	public float select_sum(String userid,List<Tags> list_tags, String tablename, String columnname,Map<String, String> hm,
			Department department) throws ElException;
	
	
	
	
	//------------------------4、按字段进行升序或降序排列
	
	/*!!!!!!!>1
	 * 组合查询 获取要显示的表信息,列表显示
	 * list_tag:自定义表信息；由select_designe_field_by_tablename返回
	 * hm:组合搜索条件
	 * userid:用户id，eluser表		type为1表示搜索设备
	 */
	public List<Map<String, String>> select_my_tableinfo_by_userid_order(String sqlAppend,int type,
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			int userid, String order,int pageNow, int pageSize) throws ElException;
	
	public List<Map<String, String>> select_my_tableinfo_by_userid_order_1(String sqlAppend,int type,
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			int userid, String order,int pageNow, int pageSize) throws ElException;
	
	public List<Map<String, String>> select_my_tableinfo_by_relate(String sqlAppend,int type,
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			int userid, String order,int pageNow, int pageSize) throws ElException;

	/*!!!!!!!!>3
	 * 部门查询，通过左右id查询
	 * department：通过左右id查询部门情况
	 */
	public List<Map<String, String>> select_my_tableinfo_by_dep(
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			Department department, String order)
			throws ElException;
	/**
	 * 查询所有，针对提醒模块
	 */
	public List<Map<String,String>> selectAll(List<Tags> list_tags,String tablename) throws ElException;
	
	
	public String get_eluser_realname_by_id(int id) throws ElException;
	
	/**
	 * 求和
	 * @param tablename
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public double getQiuheResult(String tablename,String columnName) throws ElException;
	
	public List<Tb_calculate> getTb_calculateByTableNameAndColumnName(String tablename,String columnName) throws ElException;
	
	public double calculate(String ids) throws ElException;
	
	public String getRelateIds(String tableName,String columnName,int id) throws ElException;
	
	/**
	 * 判断表中是否有两个时间字段
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public int checkIfHasTwoDateField(String tablename) throws ElException;
	
	/**
	 * 查找有无业务进度字段
	 * @param id         id不为空的时候是根据字段的id查找
	 * @param tablename  tablename不为空的时候是根据tablename查找
	 * @return
	 * @throws ElException
	 */
	public String IfHasYewuJindu_column(int id,String tablename) throws ElException;
	/**
	 * 有无时间进度
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String IfHasTimeJindu_column(String tablename) throws ElException;
	
	/**
	 * 根据id获取列名
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public String getColumn_name_by_id(int id) throws ElException;
	
	/**
	 * 根据id获取列中文名称
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public String getNameDisplayById(int id) throws ElException;
	
	/**
	 * 根据列名获取列id
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public String getIdByColumnName(String tablename,String columnName) throws ElException;
	
	/**
	 * 根据表名获取模块名称
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String getModuleNameByTablename(String tablename) throws ElException;
	
	/**
	 * 根据表名获取数据名称
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String getModuleShujuNameByTablename(String tablename) throws ElException;
	
	
	/**
	 * 根据表名查找时间进度或者业务进度字段名称
	 * @param type
	 * @param tableName
	 * @return
	 * @throws ElException
	 */
	public String getColumn_name_by_tableName(String type,String tableName) throws ElException;
	
	
	/**
	 * 根据id更改状态
	 * @param tags
	 * @param id
	 * @throws ElException
	 */
	public void accessById(Tags tags, int id) throws ElException;
	
	/**
	 * 更新整个表的时间进度字段
	 * @param table
	 * @param columns
	 * @throws ElException
	 */
	public void updateTimeJindu(String table,String columns) throws ElException;
	
	/**
	 * 根据列名查询需要进行业务计算的ids
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public String getYewu_jindu_by_columnName(String columnName) throws ElException;
	
	/**
	 * 根据列名查询字段的默认值(日期\百分比)
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public String getDisplay_type_by_columnName(String columnName) throws ElException;
	
	/**
	 * 根据id判断该字段是否是某类型字段
	 * @param id
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public boolean checkColumnIsDateById(int id,String type) throws ElException;
	
	/**
	 * 更新业务进度
	 * @param tablename
	 * @param yewu_jindu
	 * @param list_tags
	 * @throws ElException
	 */
	public void updateYewuJindu(String tablename, String yewu_jindu,List<Tags> list_tags) throws ElException;
	
	/**
	 * 根据表名和id获取当前数据的创建者
	 * @param tablename
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public int getUserIdByTablenameAndId(String tablename,int id) throws ElException;
	
	/**
	 * 修改自定义列的负责人字段
	 * @param updateType
	 * @param tablename
	 * @param id
	 * @param update_user_ids
	 * @param columnName
	 * @throws ElException
	 */
	public void update_fuzeren_zidingyi(String updateType,String tablename, int id, String update_user_ids,String columnName)
	throws ElException;
	
	/**
	 * 查询是否有表间计算字段
	 * @return
	 * @throws ElException
	 */
	public String getJisuan_relate(String tablename) throws ElException;
	
	public List<Map<String,String>> getRelateListByTablenameAndIds(List<Tags> list_tags,String ids,String tablename,int danjuid) throws ElException;
	
	/**
	 * 根据表名和列名获取列中文名
	 * @param tablename
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public String getColumnByColumnName(String tablename,String columnName) throws ElException;
	
	
	/**
	 * 根据结果表找到过程表
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String getProduceTableByResultTable(String tablename) throws ElException;
	
	/**
	 * 根据过程表找到结果表
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String getResultTableByProduceTable(String tablename) throws ElException;
	
	/**
	 * 根据业务表找到过程表
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String getProduceTableByTable(String tablename) throws ElException;
	
	/**
	 * 根据过程表查出业务表
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public List<String> getModuleidsByProduceTable(String tablename) throws ElException;
	/**
	 * 表内计算
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public IC_column_qiuji_qiuhe biaoneijisuan(String tablename,String columnName) throws ElException;
	
	/**
	 * 修改结果表中表间计算字段值
	 * @param parameters
	 * @param tablename
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public void updateResultTableBiaojianValue(String parameters,String tablename,int id) throws ElException;
	/**
	 * 添加到过程表
	 * @param tablename
	 * @param id
	 * @param num
	 * @throws ElException
	 */
	public void addToProduce(String jisuan_type,List<Tags> list_tags,String tablename,String parameters,int userid,int danjuid) throws ElException;
	
	/**
	 * 添加到过程表后添加相应数据到tb_tags_relate
	 * @param columnName
	 * @param relateid
	 * @param mainid
	 * @throws ElException
	 */
	public void addToTb_tags_relate(String columnName,int relateid,int mainid) throws ElException;
	
	/**
	 * 判断该表是结果表还是过程表或者普通表
	 * @return
	 */
	public int checkTable(String tablename) throws ElException;
	
	/**
	 * 获取过程表中与结果表不对应的字段
	 * @param tablename
	 * @param showColumnIds
	 * @return
	 * @throws ElException
	 */
	public String getProduceColumns(String tablename,String showColumnIds) throws ElException;
	
	/**
	 * 判断过程表中是否有表间求和
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String getBiaojianqiuheResultTableAndColumn(String tablename) throws ElException;
	
	/**
	 * 获取相关的结果表中字段
	 * @param tablename
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public String getResult_table_column(String tablename,String columnName) throws ElException;
	
	/**
	 * 获取相关ID
	 * @param columnName
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public String getRelateId(String columnName,int id ) throws ElException;
	
	/**
	 * 将204改为231
	 * @param tablename
	 * @param param
	 * @return
	 * @throws ElException
	 */
	public String getProduceColumnByResultColumn(String tablename,String param) throws ElException;
	
	/**
	 * 根据id修改过程表数据
	 * @param map
	 * @param id
	 * @param produce_table
	 * @throws ElException
	 */
	public void updateProduceContent(Map<String,String> map,int id,String produce_table) throws ElException;
	
	public void updateProduceContent_(Map<String,String> map,int id,String produce_table) throws ElException;
	
	/**
	 * 该字段是否表间计算字段，返回列名,计算类型
	 * @return
	 * @throws ElException
	 */
	public String checkColumnIsBiaojianjisuan(String tablename,String columnname) throws ElException;
	
	/**
	 * 终审页面更新结果表中表间计算字段
	 * @param result_table
	 * @param produce_table
	 * @param column_in_result_table_
	 * @param columns_in_produce_table
	 * @param id
	 * @throws ElException
	 */
	public void updateResultContent(String result_table,String produce_table, String column_in_result_table_,String columns_in_produce_table,int entityid,int danjuid) throws ElException;
	
	/**
	 * 根据结果表中字段找到过程表中对应字段
	 * @param resulttable
	 * @param producetable
	 * @param column
	 * @return
	 * @throws ElException
	 */
	public String getColumnNameFromResultAndProduceTable(String resulttable,String producetable,String column) throws ElException;
	
	/**
	 * 更新表内计算的字段值
	 * @param tablename
	 * @param list_tags
	 * @param list_designe
	 * @throws ElException
	 */
	public void updateBiaoneijisuanContent(String tablename,List<Tags> list_tags,List<Map<String,String>> list_designe) throws ElException;
	
	public void updateBiaoneijisuanContent_(String result_table,String produce_table,List<Tags> list_tags,List<Map<String,String>> list_designe) throws ElException;
	
	/**
	 * 判断该字段是否在该表中已经存在
	 * @param tablename
	 * @param columnname
	 * @return
	 * @throws ElException
	 */
	public boolean checkColumnIsExistByTable(String tablename,String columnname) throws ElException;
	
	/**
	 * 判断要添加的列在自动读取的时候选择的列类型是否一致,返回类型
	 * @param tablename
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public String checkSelectColumnIsRight(String tablename,String columnName) throws ElException;
	
	/**
	 * 结果表中添加字段时，过程表中添加相应字段
	 * @param tags
	 * @throws ElException
	 */
	public void addUpdateProduceColumn(Tags tags) throws ElException;
	
	/**
	 * 判断该字段是否是表间计算字段
	 * @param tablename
	 * @param column
	 * @return
	 * @throws ElException
	 */
	public String checkColumnIsCalculate(String tablename ,String column) throws ElException;
	
	/**
	 * 根据id和tablename查询审核状态
	 * @param tablename
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public int checkProduceTableShenheStatus(String tablename,int id) throws ElException;
	
	/**
	 * 根据业务表获取表间求和的表和列
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String getBiaojianqiuheValue(String tablename) throws ElException;
	
	/**
	 * 获取业务表中相关字段的id
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String getRelate_thing_id(String tablename) throws ElException;
	
	/**
	 * 修改页面删除相关数据后，修改业务表表内求和字段的值
	 * @param tablename
	 * @param tags
	 * @param danjuid
	 * @throws ElException
	 */
	public void updateYewuBiaojianqiuheContent(String tablename,Tags tags,int danjuid) throws ElException;
	
	/**
	 * 相关字段完整显示的字段ids
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String getShowColumns(String tablename) throws ElException;
	
	/**
	 * 根据合同表和相关列找到列名
	 * @param tablename
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public String getColumnNameByFromtablenameAndColumnName(String tablename,String columnName) throws ElException;
	
	
	/**
	 * 删除列时查询列值
	 * @param tablename
	 * @param columnname
	 * @return
	 * @throws ElException
	 */
	public List<String> selectColumnValues(String tablename,String columnname) throws ElException;
	
	/**
	 * 根据id和tablename删除列
	 * @param columnName
	 * @param tablename
	 * @param id
	 * @throws ElException
	 */
	public void deleteDesigneField(String tablename,String columnName,int id) throws ElException;
	
	/**
	 * 如果表间计算字段值>0修改has_init为1
	 * @param tablename
	 * @param columnName
	 * @param id
	 * @throws ElException
	 */
	public void updateProduceHas_init (String tablename,String columnName,int id) throws ElException;
	
	/**
	 * 判断是相关字段且关联模块式结果表
	 * @param tablename
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public boolean checkColumnRelateIsResultTable(String tablename,String columnName) throws ElException;
	/**
	 * 获取对应的字段值
	 * @param tablename
	 * @param columnName
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public String getColumnValueByAuto(String tablename,String columnName,int id,String yewu_tablename,int danjuid) throws ElException;
	
	/**
	 * 修改时查询是否是表内计算
	 * @param tablename
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public String getQiujiAndHeInfo(String tablename,String columnName) throws ElException;
	
	public String getDesigneColumns(String tablename) throws ElException;
	
	public boolean checkColumnValueIsExist(String tablename,String columnName,String value) throws ElException;
	
	
	/**
	 * 获取城市类型字段
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public String select_columnname_by_tablename_chengshi(String tablename) throws ElException;
	
	/**
	 * 获取省市县
	 * @param id
	 * @param tablename
	 * @param columnname
	 * @return
	 * @throws ElException
	 */
	public String getShengshixian(int id,String tablename,String columnname) throws ElException;
	
	/**
	 * 获取设置的省市县默认值
	 * @param tablename
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public String getDefaultValue_shengshixian(String tablename,String columnName) throws ElException;
	
	/**
	 * 客户分析一览查看
	 */
	public List<Map<String, String>> select_designe_by_tablename(int id,
			List<Tags> list_tags, String tablename, int pageNow, int pageSize)
			throws ElException;
	
	/**
	 * 客户分析一览查看Size
	 * @return
	 */
	public int select_designe_count_by_tablename(int id,List<Tags> list_tags, String tablename) throws ElException;
	/**
	 * 自动补齐
	 */
	public String getZidongbuqiValue(String tablename,String columnName) throws ElException;
	
	/**
	 * 插入结果表时，修改resulttable_producetable表中信息
	 * @param produce_table
	 * @param produce_column
	 * @param result_table
	 * @param result_column
	 * @throws ElException
	 */
	public void update_resulttable_producetable(String produce_table,String produce_column,String result_table,String result_column) throws ElException;
	
	/**
	 * 获取用户姓名和部门当前信息
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public CurrentUser getCurrentUser(String tablename) throws ElException;
	
	/**
	 * 根据userid获取用户姓名和部门
	 * @param currentUser
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public CurrentUser getCurrentUserByUserId(CurrentUser currentUser,int userid) throws ElException;
	
	
	/**
	 * 修改tb_user
	 * @param currentUser
	 * @throws ElException
	 */
	public void updateTb_userByTablename(CurrentUser currentUser) throws ElException;
	
	/**
	 * 根据相关字段获取与之关联的业务进度column
	 * @param tablename
	 * @param columnName
	 * @return
	 * @throws ElException
	 */
	public String getColumnByRelateColumnAndTable(String tablename,String columnName) throws ElException;
	
	
	/**
	 * 物料需求一览表
	 * @param list_tags
	 * @param tablename
	 * @param hm
	 * @param department
	 * @param order
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Map<String, String>> select_my_tableinfo_by_dep_(
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			ElNode department, String order,int pageNow, int pageSize)
			throws ElException;

	public int select_my_tableinfo_by_dep_count_(List<Tags> list_tags,
			Map<String, String> hm, String tablename, Department department)
			throws ElException;
	
	
	//////////////////////////////////自定义审核
	/**
	 * 根据表名删除自定义审核信息
	 */
	public void delete_audit_by_tablename(String tablename ) throws ElException;
	/**
	 * 插入自定义审核信息
	 * @param tablename
	 * @param sub_level
	 * @param sub_title
	 * @param sub_userid
	 * @param sub_bz
	 * @throws ElException
	 */
	public void insert_tb_auditByTablename(String tablename,String sub_level,String sub_title,
			String sub_userid,String sub_bz) throws ElException;
	/**
	 * 根据表名获取自定义审核信息
	 * @param tablename
	 * @return
	 * @throws ElException
	 */
	public List<CustomAudit> get_audits_by_tablename(String tablename) throws ElException;
	public String get_audit_by_auditOrderAndTablename(String auditOrder,String tablename) throws ElException;
	/**
	 * 获取最小审核级别或最大审核级别
	 * @param tablename
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public CustomAudit get_audit_by_tablename(String tablename,int id) throws ElException;
	/**
	 * 根据表名和用户id获取审核信息
	 * @param tablename
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public CustomAudit get_audit_by_tablename_userid(String tablename,int userid) throws ElException;
	/**
	 * 自定义审核列表
	 * @param userid
	 * @param list_tags
	 * @param tablename
	 * @param hm
	 * @param department
	 * @param order
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Map<String, String>> select_my_audits_by_dep(int roleid,CustomAudit ca,int userid,
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			ElNode department, String order,int pageNow, int pageSize)
			throws ElException;
	/**
	 * 自定义审核数据条数
	 * @param userid
	 * @param list_tags
	 * @param hm
	 * @param tablename
	 * @param department
	 * @return
	 * @throws ElException
	 */
	public int select_my_audits_by_dep_count(int roleid,CustomAudit ca,int userid,List<Tags> list_tags,
			Map<String, String> hm, String tablename, Department department)
			throws ElException;
	/**
	 * 根据数据id获取数据状态
	 * @param tablename
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public int get_status_by_tablename_id(String tablename,int id) throws ElException;
	
	public int getApplicationByIdAndTablename(int moduleid,int userid,int id) throws ElException;
	/**
	 * 根据当前用户id判断是否有审核权限
	 * @param tablename
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserInAudit(String tablename,int userid,String auditOrder) throws ElException;
	
	/**
	 * 根据表名和实体id获取备注信息
	 * @param tablename
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public List<AuditMark> select_audit_mark_by_entityid(String tablename,int id,int pageNow,int pageSize) throws ElException;
	public int select_audit_mark_size_by_entityid (String tablename,int id,int pageNow,int pageSize) throws ElException;
	
	/**
	 * 验证审核级别是否已经存在
	 * @param tablename
	 * @param auditOrder
	 * @return
	 * @throws ElException
	 */
	public boolean check_auditOrderIsExist(String tablename,String auditOrder) throws ElException;
	
	/**
	 * 删除审核用户
	 * @param tablename
	 * @param auditOrder
	 * @throws ElException
	 */
	public void deleteauditOrderUserId(String tablename,String auditOrder,int userid) throws ElException;
	
	/**
	 * 自定义字段添加的时候插入备注表
	 * @param tagsMark
	 * @throws ElException
	 */
	public void insert_tb_tags_mark(TagsMark tagsMark) throws ElException;
	
	/**
	 * 修改自定义字段备注信息
	 * @param tagsMark
	 * @throws ElException
	 */
	public void update_tb_tags_mark(TagsMark tagsMark) throws ElException;
	
	/**
	 * 根据某节点查出下级节点
	 * @return
	 * @throws ElException
	 */
	public List<SelectLevel> getSelectLevelById(int id) throws ElException;
	
	
	////////////////////////////////
	
}
