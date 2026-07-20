package com.sopia.schedule.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.pfms.entities.TableColumn;
import com.sopia.schedule.dao.ModuleManageDao;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.Tags;

public class ModuleManageDaoImpl implements ModuleManageDao
{
	private static final Log logger = LogFactory
			.getLog(ModuleManageDaoImpl.class);
	
	//创建过程表时，从结果表中获取列，但要修改列名称
	public  String createNewColumn(String column,String tablename) throws	 ElException{
		String newColumn = "";
		if(!column.equals("")){
			newColumn = tablename + "_" + column.split("_")[1];
		}
		return newColumn;
	}
	
	/*
	 * 复制模块 添加数据库表，包含固有字段，id，ststus，userid 添加序列
	 */
	public void add_module(ModuleManage moduleManage) throws ElException
	{
		int id = 0;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		Connection ct = null;
//		String tablename = "tb_mm_";
//		String sql_getid = "";

		String sql_create_table = "";
		String sql_alert_pk = "";
		String sql_create_sequence = "";
		String sql_triggle = "";
		String sql="";
		
		String relateColumns = "";			
		List<Map<String,String>> listMap = new ArrayList<Map<String, String>>();
		Map<String,String> map = null;
		
		Tags tags ;

		List<TableColumn> columns = new ArrayList<TableColumn>();
		TagsDao tagsDao = new TagsDaoImpl();
		try
		{
			ct = DBConnection.getConnection();
			if(moduleManage.getTableType() == 2){
				ps = ct.prepareStatement("select table_name,column_name,data_type,data_length from user_tab_columns where table_name=?");
				ps.setString(1, moduleManage.getRelatetablename());//结果表
				rs = ps.executeQuery();
				TableColumn column ;
				while(rs.next()){
					column = new TableColumn();
					column.setColumnName(rs.getString("column_name"));
					column.setDateLength(rs.getString("data_length"));
					column.setDateType(rs.getString("data_type"));
					column.setTableName(rs.getString("table_name"));
					columns.add(column);
				}
			}
			DBConnection.closeConnectInfo(ct, ps, rs);
			
			//创建表、主键、序列、触发器、插入tb_module_manage、插入tb_user、插入elfunc表
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call create_module(?,?,?,?,?,?,?,?,?)}");
			ps.setInt(1, moduleManage.getTableType());
			ps.setString(2, moduleManage.getRelatetablename());
			ps.setString(3, moduleManage.getModulename());
			ps.setString(4, moduleManage.getTablename());
			ps.setString(5, moduleManage.getRemark());
			ps.setInt(6, moduleManage.getOndemo());
			ps.setString(7, moduleManage.getFromtablename());
			ps.setInt(8, moduleManage.getCan_add_other_func());
			ps.setString(9, moduleManage.getShujuname());
			ps.executeUpdate();
			DBConnection.closeConnectInfo(ct, ps, rs);
			
			
			//过程表中从结果表中复制过来的字段插入tb_designe表
			ct = DBConnection.getConnection();
			if(moduleManage.getTableType() == 2){
				for(int i=0;i<columns.size();i++){//columns为结果表中自定义字段
					if(columns.get(i).getColumnName().contains(columns.get(i).getTableName())){//如果该列名是结果表中的自定义列
						tags = tagsDao.select_designe_field_by_id(tagsDao.select_designe_field_id_by_columnName(columns.get(i).getColumnName()));
						if (map == null)	map = new HashMap<String,String>();
						tags.setColumn_name(createNewColumn(columns.get(i).getColumnName(),moduleManage.getTablename().toUpperCase()));
						tags.setTable_name(moduleManage.getTablename().toUpperCase());
						//tags.setFromResultTable("1");
						//插入tb_designe
						tagsDao.insert_designe_field(0,tags);
						
						//插入resulttable_producetable字段对应表
						map.put(columns.get(i).getColumnName(), tags.getColumn_name());
					}
				}
				//插入过程表和结果表的关系表resulttable_producetable表
				if(tagsDao.checkTable(moduleManage.getTablename().toUpperCase()) == 2 && map != null)
					this.insert_into_resulttable_producetable(moduleManage.getTablename().toUpperCase(),moduleManage.getRelatetablename(),map);
			}
			
		}
		catch (Exception e)
		{
			logger.error("模块复制添加表出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	//插入resulttable_producetable字段对应表
	public void insert_into_resulttable_producetable(String producetable,String resulttable,Map<String,String> map) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String parameters = "";
		int i = -1;
		//构建relateColumns
		for(String key:map.keySet()){
			parameters += key + "==" + (String)map.get(key) + ",";
		}
			
		try
		{
			ct = DBConnection.getConnection();
		
			sql =" insert into resulttable_producetable" +
					" (resulttable,producetable,relateColumns) " +
					" values(?,?,?)" ;
			 
		 	ps = ct.prepareStatement(sql);
		 	ps.setString(1, resulttable);
		 	ps.setString(2, producetable);
		 	ps.setString(3, parameters);
		 	ps.executeUpdate();
		}
		catch (Exception e)
		{
			logger.error("插入结果表过程表字段关联表出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 查询模块列表，分页
	 */
	public List<ModuleManage> select_mymodule(ModuleManage module_s,int pageNow,int pageSize)  throws	 ElException
	{
		List<ModuleManage> list = new ArrayList<ModuleManage>();
		ModuleManage module =null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlBegin = "";
		String sqlEnd="";
		String sql = "";
		try
		{
			ct = DBConnection.getConnection();
			
			if(pageNow ==0 && pageSize == 0){
				sql = "select * from tb_module_manage";
			}else{
				sqlBegin ="SELECT * FROM (" +
		 		"SELECT A.*, ROWNUM RN " +
		 		"FROM (" +
		 		"select * from tb_module_manage " ;
			 
			 	sqlEnd =	" order by id  desc ) A " + 
			 		" WHERE ROWNUM <= ? " +
			 		" )WHERE RN >= ?";
		 	
			 	if(module_s!=null)
			 	{
			 		if(!module_s.getModulename().equals(""))
			 			sql = " where modulename like '%"+module_s.getModulename()+"%' ";
			 		
			 	}
			}
		
			
		 	
		 	
		 	 ps = ct.prepareStatement(sqlBegin +sql+sqlEnd);
		 	 
		 	 if(pageNow !=0 && pageSize != 0){
		 		 ps.setInt(1, pageNow);
				 ps.setInt(2, pageSize);
		 	 }
		 	 
		 	 rs=ps.executeQuery();
			 while (rs.next()) 
			 {
				 module=new ModuleManage();
				 module.setId(rs.getInt("id"));
				 module.setModulename(rs.getString("modulename"));
				 module.setTablename(rs.getString("tablename"));
				 module.setRemark(rs.getString("remark"));
				 module.setDemocss(rs.getString("democss"));
				 module.setDemourl(rs.getString("demourl"));
				 module.setOndemo(rs.getInt("ondemo"));
				 module.setOpenvisitor(rs.getInt("openvisitor"));
				 list.add(module);
			 }
		}
		catch (Exception e)
		{
			logger.error("模块复制添加表出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	public int select_mymodule_count(ModuleManage module)  throws	 ElException
	{
		int count = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlwhere="";
		try
		{
			ct = DBConnection.getConnection();
		
			 sql ="select count(*) from tb_module_manage " ;
			 
			 if(module!=null)
			 	{
			 		if(!module.getModulename().equals(""))
			 			sqlwhere = " where modulename like '%"+module.getModulename()+"%' ";
			 		
			 	}
			 
		 	
		 	 ps = ct.prepareStatement(sql+sqlwhere);
		 	 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
		}
		catch (Exception e)
		{
			logger.error("模块复制添加表出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	/*
	 * update
	 */
	public void update_module_by_id(ModuleManage module) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try
		{
			ct = DBConnection.getConnection();
			 sql =" update  tb_module_manage  set modulename='"+module.getModulename()+"',remark='"+module.getRemark()+"',ondemo="+module.getOndemo()+",relatetablename='"+module.getRelatetablename()+"',fromtablename='"+module.getFromtablename()+"',can_add_other_func="+module.getCan_add_other_func()+",shujuname='"+module.getShujuname()+"' where id="+module.getId() ;
			 
		 	 ps = ct.prepareStatement(sql);
		 	 ps.executeUpdate();
		}
		catch (Exception e)
		{
			logger.error("模块复制更新表出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/*
	 * 查询单个
	 */
	public ModuleManage select_module_by_id(int id) throws ElException
	{
		ModuleManage module = new ModuleManage();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try
		{
			ct = DBConnection.getConnection();
			
			 sql =" select * from tb_module_manage where id="+id;
			 ps=ct.prepareStatement(sql);
		 	 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				 module.setId(rs.getInt("id"));
				 module.setModulename(rs.getString("modulename"));
				 module.setTablename(rs.getString("tablename"));
				 module.setRemark(rs.getString("remark"));
				 module.setDemocss(rs.getString("democss"));
				 module.setDemourl(rs.getString("demourl"));
				 module.setOndemo(rs.getInt("ondemo"));
				 module.setRelatetablename(rs.getString("relatetablename"));
				 module.setTableType(rs.getInt("tabletype"));
				 module.setFromtablename(rs.getString("fromtablename"));
				 module.setCan_add_other_func(rs.getInt("can_add_other_func"));
				 module.setShujuname(rs.getString("shujuname"));
			 }
		}
		catch (Exception e)
		{
			logger.error("模块复制查询表出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return module;
	}
	
	public ModuleManage select_module_by_TableName(String tablename) throws ElException
	{
		ModuleManage module = new ModuleManage();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try
		{
			 ct = DBConnection.getConnection();
			
			 sql =" select * from tb_module_manage where tablename='"+tablename + "'";
			 ps=ct.prepareStatement(sql);
		 	 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				 module.setId(rs.getInt("id"));
				 module.setModulename(rs.getString("modulename"));
				 module.setTablename(rs.getString("tablename"));
				 module.setRemark(rs.getString("remark"));
				 module.setDemocss(rs.getString("democss"));
				 module.setDemourl(rs.getString("demourl"));
				 module.setOndemo(rs.getInt("ondemo"));
				 module.setIs_enabled(rs.getInt("is_audit"));
			 }
		}
		catch (Exception e)
		{
			logger.error("模块复制查询表出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return module;
	}
	
	public void updateDemoORCss(int id, String demoName,String urlORcss) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String tableName = urlORcss.equals("url") ? "DEMOURL" : "DEMOCSS";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update tb_module_manage set "+tableName+"=? where id = ?");
			ps.setString(1, demoName);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新模板出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/*
	 * 查询所有模块，非分页、列表
	 */
	public List<ModuleManage> select_mymodule(int pageNow,int pageSize)  throws ElException
	{
		List<ModuleManage> list = new ArrayList<ModuleManage>();
		ModuleManage module =null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try
		{
			ct = DBConnection.getConnection();
		
//		 	sql = " select t.id as id,t.modulename as modulename,t.tablename as tablename ,t.remark as remark from tb_module_manage t  " +
//		 			" union all " +
//		 			" select i.id as id,i.name as modulename ,i.tablename as tablename,i.description as remark from ic_manage i" ;
			 
		 	sql = " select b.*,rn from (select a.*,rownum rn from (select t.id as id,t.modulename as modulename,t.tablename as tablename ,t.remark as remark from tb_module_manage t  order by t.id desc) a where rownum<=?) b where rn>=?";
		 	 ps = ct.prepareStatement(sql);
		 	 ps.setInt(1, pageNow);
		 	 ps.setInt(2, pageSize);
		 	 rs=ps.executeQuery();
			 while (rs.next()) 
			 {
				 module=new ModuleManage();
				 module.setId(rs.getInt("id"));
				 module.setModulename(rs.getString("modulename"));
				 module.setTablename(rs.getString("tablename"));
				 module.setRemark(rs.getString("remark"));
				 
				 list.add(module);
			 }
		}
		catch (Exception e)
		{
			logger.error("模块复制添加表出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	public int select_mymodule_size(int pageNow,int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int result = 0;
		try
		{
			ct = DBConnection.getConnection();
			 
		 	sql = " select count(1) from tb_module_manage ";
		 	 ps = ct.prepareStatement(sql);
		 	 
		 	 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				 result = rs.getInt(1);
			 }
		}
		catch (Exception e)
		{
			logger.error("获取模块Size出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}
	
	/*
	 * 获得自定义列表
	 */
	public List<Tags> select_designe_field_by_tablename(String type,String tablename)
			throws ElException
	{
		List<Tags> list = new ArrayList<Tags>();
		Tags tags = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try
		{
			ct = DBConnection.getConnection();
			if(type !=null ){
				if(type.equals("calculate")){
					sql = "select * from tb_designe where table_name=?   order by sn ";
				}else if(type.equals("fromtablename")){
					sql = "select * from tb_designe where table_name=?   order by sn ";
				}
			}else {
				sql = "select * from tb_designe where table_name=? and (display_type='文本' or display_type='相关负责人')  order by sn ";
			}
			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();
			while (rs.next())
			{
				tags = new Tags();

				tags.setId(rs.getInt("id"));
				tags.setColumn_name(rs.getString("column_name"));
				tags.setColumn_type(rs.getString("column_type"));
				tags.setTable_name(rs.getString("table_name"));
				tags.setName_display(rs.getString("name_display"));

				tags.setAdd_display(rs.getInt("add_display"));
				tags.setUpdate_display(rs.getInt("update_display"));
				tags.setView_display(rs.getInt("view_display"));
				tags.setList_display(rs.getInt("list_display"));
				tags.setMutilsearch_display(rs.getInt("mutilsearch_display"));

				tags.setSn(rs.getInt("sn"));
				tags.setDisplay_type(rs.getString("display_type"));
				tags.setDefault_value(rs.getString("default_value"));
				tags.setCannot_modify(rs.getInt("cannot_modify"));
				tags.setDepartsearch_display(rs.getInt("departsearch_display"));
				
				tags.setRequired(rs.getInt("required"));
				
				tags.setSum_display(rs.getInt("sum_display"));

				tags.setJindutiao(rs.getInt("jindutiao"));
				tags.setRelateIsShowComplete(rs.getInt("is_show_complete"));
				tags.setShow_time_jindu(rs.getInt("show_time_jindu"));
				tags.setTime_jindu_ids(rs.getString("time_jindu"));

				tags.setYewu_jindu_ids(rs.getString("yewu_jindu"));
				tags.setYewu_jindu_relate_id(rs
						.getString("yewu_jindu_relate_id"));
				tags.setYewu_jindu_relate_begintime(rs
						.getString("yewu_jindu_relate_begintime"));
				tags.setYewu_jindu_relate_endtime(rs
						.getString("yewu_jindu_relate_endtime"));

				tags.setJisuan_relate_type(rs.getString("jisuan_relate_type"));
				tags.setJisuan_relate_id(rs.getString("jisuan_relate_id"));
				tags.setJisuan_produce_table_name(rs
						.getString("jisuan_produce_table_name"));
				tags.setJisuan_produce_relate_id(rs
						.getString("jisuan_produce_relate_id"));
				tags.setJisuan_result_table_name(rs
						.getString("jisuan_result_table_name"));
				tags.setJisuan_result_relate_id(rs
						.getString("jisuan_result_relate_id"));

				tags.setWanzheng(rs.getString("wanzheng"));
				tags.setIs_calculate(rs.getInt("is_calculate"));

				tags.setBiaojianqiuhe_check(rs.getInt("biaojianqiuhe_check"));
				tags.setBiaojianqiuhe_tablename(rs
						.getString("biaojianqiuhe_tablename"));
				tags.setBiaojianqiuhe_column(rs
						.getString("biaojianqiuhe_column"));

				tags.setFromResultTable(rs.getString("from_result_table"));
				
				tags.setWritible(rs.getInt("writible"));
				
				tags.setIs_judge(rs.getInt("is_judge"));
				
				tags.setMark(rs.getString("mark"));
				
				tags.setFromtablename_columnname(rs.getString("fromtablename_columnname"));

				tags.setShowfinalpass(rs.getInt("showfinalpass"));
				
				list.add(tags);

			}
		}
		catch (Exception e)
		{
			logger.error("获取自定义列表出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	/*
	 * 获得需要查询的列表页功能代码
	 */
	public List<String> select_my_charge_by_tablename(String tablename,int userid) throws ElException
	{
		List<String> list = new ArrayList<String>();
		
		String username="";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = " select realname from eluser where id="+userid;
		try
		{
			ct = DBConnection.getConnection();

			
			//------get user name
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				username=rs.getString(1);
			}
			
			//-----get columnname
			sql =" select column_name from tb_designe where table_name='"+tablename+"'  and display_type='相关负责人' ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				String str="";
				str=rs.getString(1);
				str="&"+str+"="+username;
				list.add(str);
			}
		}
		catch (Exception e)
		{
			logger.error("获取自定义列表出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return list;
	}

	public int checkTableIsExist(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int result = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select table_name from user_tables where table_name = '" + tablename.toUpperCase() + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				result = 1;
		} catch (Exception e) {
			logger.error("验证表名是否已经存在出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getFromtablenameByTablename(String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try
		{
			 ct = DBConnection.getConnection();
		
			 sql = "select fromtablename from tb_module_manage where tablename = '" + tablename + "'";
		 	 ps = ct.prepareStatement(sql);
		 	 rs = ps.executeQuery();
		 	 if(rs.next())
		 		 result = rs.getString(1);
		}
		catch (Exception e)
		{
			logger.error("根据tablename获取fromtablename出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public void addTb_user(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into tb_user (tablename) values ('"+tablename+"')";
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加tb_user出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void update_module_by_tablename(String tablename, int is_enabled)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update tb_module_manage set is_audit = " + is_enabled + " where tablename = '" + tablename +"'";
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据表名修改模块管理表中对应记录的是否开启自定义审核出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	
}
