package com.sopia.schedule.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.CheckHtml;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.lable.common.LableCommon;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.Table;
import com.sopia.lable.entites.TableField;
import com.sopia.schedule.CustomReportConstants;
import com.sopia.schedule.ScheduleUtil;
import com.sopia.schedule.dao.CustomReportsDao;
import com.sopia.schedule.entities.CustomReport;
import com.sopia.schedule.entities.CustomReportJSZ;
import com.sopia.statman.StatisticConstants;

public class CustomReportsDaoImpl implements CustomReportsDao {
	private static final Log logger = LogFactory
	.getLog(CustomReportsDaoImpl.class);
	private TagsDaoImpl tagsDao = new TagsDaoImpl();
	
	public TagsDaoImpl getTagsDao() {
		return tagsDao;
	}

	public void setTagsDao(TagsDaoImpl tagsDao) {
		this.tagsDao = tagsDao;
	}

	public int addCustomReport(CustomReport customReport) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int returnId = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORT_INSERT));
			ps.setString(1, customReport.getName());
			ps.executeUpdate();
			
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORT_QUERY_CURRENTVAL));
			rs = ps.executeQuery();
			if(rs.next()){
				returnId = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加报表标签出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnId;
		
	}

	public List<CustomReport> listCustomReports(int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CustomReport> customReports = new ArrayList<CustomReport>();
		CustomReport customReport = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORT_LIST));
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				customReport = new CustomReport();
				customReport.setId(rs.getInt("id"));
				customReport.setName(rs.getString("name"));
				customReport.setResultPage(rs.getString("resultpage"));
				customReports.add(customReport);
			}
		} catch (Exception e) {
			logger.error("查询报表标签出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return customReports;
	}

	public int listCustomReportsSize() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORT_LIST_SIZE));
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询报表标签Size出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	public CustomReport queryCustomReportById(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CustomReport customReport = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORT_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				customReport = new CustomReport();
				customReport.setId(rs.getInt("id"));
				customReport.setName(rs.getString("name"));
				customReport.setTableinfo(rs.getString("tableinfo"));
				customReport.setTablefield(rs.getString("tablefield"));
				customReport.setGroupby(rs.getString("groupby"));
				customReport.setGroupby_(rs.getString("groupby_"));
				customReport.setPageSize(rs.getInt("pagesize"));
				customReport.setSql(rs.getString("sql"));
				customReport.setSqlcondition(rs.getString("sqlcondition"));
				customReport.setShowsearch(rs.getInt("showsearch"));
				customReport.setShowtree(rs.getInt("showtree"));
				customReport.setResultPage(rs.getString("resultpage"));
				customReport.setLable(new OracleBlob().getContent(rs.getBlob("lable")));
				customReport.setSearchhtml(new OracleBlob().getContent(rs.getBlob("searchhtml")));
				customReport.setSearchhtmlfield(rs.getString("searchhtmlfield"));
				customReport.setSearchtype(rs.getInt("searchtype"));
			}
		} catch (Exception e) {
			logger.error("根据id查询报表标签出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return customReport;
	}
	
	public static boolean checkIdIsIn(String[] array, String str) {
		boolean flag = false;
		String temp = "";
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				temp = array[i];
				if (temp != null && !temp.equals("")) {
					if (temp.equals(str)) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}
	
	public CustomReport queryCustomReportByJSP(String jspName) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CustomReport customReport = null;
		String resultPage = "";
		String[] resultPage_array = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORT_LIST_NO_PAGE));
			ps.setString(1, jspName);
			rs = ps.executeQuery();
			while(rs.next()){
				resultPage = rs.getString("resultPage");
				if(resultPage != null && !resultPage.equals("")){
					resultPage_array = resultPage.split(",");
					if(checkIdIsIn(resultPage_array,jspName)){
						customReport = new CustomReport();
						customReport.setId(rs.getInt("id"));
						customReport.setName(rs.getString("name"));
						customReport.setTableinfo(rs.getString("tableinfo"));
						customReport.setTablefield(rs.getString("tablefield"));
						customReport.setGroupby(rs.getString("groupby"));
						customReport.setGroupby_(rs.getString("groupby_"));
						customReport.setPageSize(rs.getInt("pagesize"));
						customReport.setSql(rs.getString("sql"));
						customReport.setSqlcondition(rs.getString("sqlcondition"));
						customReport.setShowsearch(rs.getInt("showsearch"));
						customReport.setShowtree(rs.getInt("showtree"));
						customReport.setLable(new OracleBlob().getContent(rs.getBlob("lable")));
						customReport.setSearchhtml(new OracleBlob().getContent(rs.getBlob("searchhtml")));
						customReport.setSearchhtmlfield(rs.getString("searchhtmlfield"));
						customReport.setSearchtype(rs.getInt("searchtype"));
					}
				}
			}
		} catch (Exception e) {
			logger.error("根据id查询报表标签出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return customReport;
	}
	
	
	public CustomReport lable_getlableby(String tableName,String lableName) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CustomReport  customReport=null;	
		try{
			String sql="select tableinfo , tablefield from "+tableName+"  where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, lableName);
			rs=ps.executeQuery();
			if(rs.next()){
				customReport = new CustomReport();
				customReport.setTableinfo(rs.getString(1)==null?"":rs.getString(1));
				customReport.setTablefield(rs.getString(1)==null?"":rs.getString(2));
				
				
			}
			
		} catch (Exception e) {
			logger.error("查询表"+tableName+"表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return customReport;
		
		
		
	}
	
	/**
	 * 修改标签 表及字段信息失败
	 * @param tableName:表名
	 * @param name:标签名
	 * @param customReport:要修改的信息
	 * @throws ElException 
	 */
	public void lable_updlabletableinfoAndField(String tableName,String name,CustomReport customReport) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		try{
			if(customReport.getTableinfo()==null&&customReport.getTableinfo()==null){
			}else{
				String str="";
				if(customReport.getTableinfo()!=null&&!customReport.getTableinfo().equals("")){
					str += " tableinfo = '" + customReport.getTableinfo() + "', " ; 
				}if(customReport.getTablefield()!=null&&!customReport.getTablefield().equals("")){
					
					str+=" tablefield= '" + customReport.getTablefield() + "' ";
				}
				
				String sql="update "+tableName+" set "+str+"  where name=?  ";
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(sql);			
				ps.setString(1, name);
				ps.executeUpdate();
				
			}
			
			
			
		} catch (Exception e) {
			logger.error("修改表"+tableName+"表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
		
	}

	public void updateCustomReportById(CustomReport customReport)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		try{
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(ElQuerySql
						.getSQL(CustomReportConstants.CUSTOMREPORT_UPDATE_BYID));	
				ps.setString(1, customReport.getSqlcondition());
				ps.setInt(2, customReport.getPageSize());
				ps.setInt(3, customReport.getId());
				ps.executeUpdate();
				
			
			
			
		} catch (Exception e) {
			logger.error("修改表表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void updateCustomReportByTree(CustomReport customReport)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		try{
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(ElQuerySql
						.getSQL(CustomReportConstants.CUSTOMREPORT_UPDATE_TREE));	
				ps.setInt(1, customReport.getShowtree());
				ps.setInt(2, customReport.getId());
				ps.executeUpdate();
				
			
			
			
		} catch (Exception e) {
			logger.error("修改表表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}
	
	public void updateCustomReportBySearch(CustomReport customReport)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		try{
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(ElQuerySql
						.getSQL(CustomReportConstants.CUSTOMREPORT_UPDATE_SEARCH));	
				ps.setInt(1, customReport.getShowsearch());
				ps.setString(2, ScheduleUtil.getSearchHtmlFieldBySearchHtml(customReport.getSearchhtml()));
				ps.setInt(3, customReport.getSearchtype());
				ps.setInt(4, customReport.getId());
				ps.executeUpdate();
				
				OracleBlob setblob1 = new OracleBlob("customreport","name","'"+customReport.getName()+"'","searchhtml",customReport.getSearchhtml(),"修改标签循环体失败",ct);
				setblob1.updateContent();
				
			
			
			
		} catch (Exception e) {
			logger.error("修改表表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}
	
	public void updateCustomReportFinal(CustomReport customReport)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		try{
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(ElQuerySql
						.getSQL(CustomReportConstants.CUSTOMREPORT_UPDATE_FINAL));	
				ps.setString(1, customReport.getSql());
				ps.setInt(2, customReport.getId());
				ps.executeUpdate();
				
				OracleBlob setblob1 = new OracleBlob("customreport","name","'"+customReport.getName()+"'","lable",customReport.getLable(),"修改标签循环体失败",ct);
				setblob1.updateContent(); 
				
			
			
			
		} catch (Exception e) {
			logger.error("修改表表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}
	
	public void updateCustomReportByResultPage(CustomReport customReport,String filename)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String resultPage = "";
		
		try{
				ct= DBConnection.getConnection();
				
				
				ps=ct.prepareStatement(ElQuerySql
						.getSQL(CustomReportConstants.CUSTOMREPORT_UPDATE_JSP));	
				ps.setString(1, filename);
				ps.setInt(2, customReport.getId());
				ps.executeUpdate();
				
			
			
			
		} catch (Exception e) {
			logger.error("修改表表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}
	
	
	public CustomReport lable_getlablesqllable(String tableName,CustomReport customReport)throws ElException{
		

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CustomReport  c=null;	
		try{
			String sql="select tableinfo , tablefield,pagesize,sqlcondition,groupby,lable ,name,groupby_,id from "+tableName+"  where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, customReport.getName());
			rs=ps.executeQuery();
			if(rs.next()){
				c = new CustomReport();
				c.setTableinfo(rs.getString(1)==null?"":rs.getString(1));
				c.setTablefield(rs.getString(1)==null?"":rs.getString(2));
				c.setPageSize(rs.getInt(3));
				c.setSqlcondition(rs.getString(4)==null?"":rs.getString(4));
				c.setGroupby(rs.getString(5)==null?"":rs.getString(5));
				c.setLable(new OracleBlob().getContent(rs.getBlob(6)));
				c.setName(rs.getString(7));
				c.setGroupby_(rs.getString(8));
				c.setId(rs.getInt(9));
			}
			
		} catch (Exception e) {
			logger.error("查询表"+tableName+"表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return c;
		
		
	}
	
	
	public List<TableField> getFieldByTableName(String tableName) throws ElException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TableField>  tableFieldList=null;	
		String  err=null;
		String sql = "";
		try{
			ct= DBConnection.getConnection();
			//如果在系统字段表中没有找到信息，则去自定义字段表中查找字段信息	
			if(tableFieldList==null){
				err="自定义字段表查询失败";
				sql ="select column_name,name_display,table_name,display_type,default_value,id from TB_DESIGNE where table_name=?  ";			
				
				ps=ct.prepareStatement(sql);
				ps.setString(1, tableName);
				rs=ps.executeQuery();
					while(rs.next()){
						if(tableFieldList==null){
							tableFieldList=new ArrayList<TableField>();
						}
						TableField  tableField=new TableField();
						
						tableField.setName(rs.getString(2));
						tableField.setFieldName(rs.getString(1));
						tableField.setTableName(rs.getString(3));
						tableField.setFieldType(rs.getString(4));
						//得到下拉选项的值
						tableField.setValue(rs.getString(5));
						tableField.setId(rs.getInt(6));
						tableFieldList.add(tableField);
					}
			
			
			}
			
		} catch (Exception e) {
			logger.error(err, e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tableFieldList;
		
		
	}
	
	/**
	 * 通过传入的表名数组，返回表信息
	 * @param tableArr:一个存放表名的数组
	 * @return 
	 * @throws ElException
	 */
	public List<Table>  getTableByArr(String tableArr[]) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Table>  tableList=null;
		String sql = "";
	
		try{
			ct= DBConnection.getConnection();
			//再去自定义表中查询
			sql="select modulename , tablename from TB_MODULE_MANAGE  where tablename=? ";
			ps=ct.prepareStatement(sql);
			for (String  name : tableArr) {
				ps.setString(1, name);
				rs=ps.executeQuery();
				if(rs.next()){
					if(tableList==null){
						tableList=new ArrayList<Table>();
					}
					Table  table=new Table();
					table.setName(rs.getString(1));
					table.setTableName(rs.getString(2));
					tableList.add(table);
				}
			}
			
		} catch (Exception e) {
			logger.error("查询用户自定义表信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tableList;
	}
	
	public List<TableField> getTableFieldByField(String arr[])throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String err ="";
		List<TableField>  l=new ArrayList<TableField>();	
		try{
			ct= DBConnection.getConnection();
			err="查询自定义字段表失败";
			String sql ="select column_name,name_display,table_name,display_type,default_value from TB_DESIGNE where table_name=? and column_name=? ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
				for (String str : arr) {
					
					ps.setString(1, LableCommon.lablecommon_delestrtostr(str, "."));
					ps.setString(2, LableCommon.lablecommon_delestrtostr2(str, "."));
					rs=ps.executeQuery();
					if(rs.next()){
						TableField tf = new TableField();//new tableField对象
						tf.setFieldName(rs.getString(1));//列名
						tf.setName(rs.getString(2));//显示名称
						tf.setTableName(rs.getString(3));//表名
						tf.setFieldType(rs.getString(4));//列类型
						tf.setDefaultvalue(rs.getString(5));
						l.add(tf);
					}
				}
			
			
			
			
		} catch (Exception e) {
			logger.error(err, e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return l;
		
		
	}
	
	public List<Map<String,Object>> getMap(List<TableField> list ,String sql ,int pageNow,int pageSize,CustomReport customReport
			) throws ElException{
		
		List<Map<String,Object>> listMap= null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		Connection ct2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		try{
			ct2 = DBConnection.getConnection();
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement( sql );
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()){
				if(listMap==null) listMap = new ArrayList<Map<String,Object>>();
				Map<String, Object>  m = new HashMap<String, Object>();
				if(customReport!=null&&(customReport.getGroupby_()==null||customReport.getGroupby_().equals(""))){
					if(customReport.getTableinfo()!=null&&!customReport.getTableinfo().equals("")){
						m.put("id", String.valueOf(rs.getInt("id")));
					}
				}
				
				for (TableField tableField : list) {
					if(tableField.getFieldType().equals("实数")){
						if(!tableField.getTableName().equals(ScheduleUtil.OTHER))	{//不是相关统计字段
							m.put(tableField.getTableAndField(), String.valueOf(rs.getDouble(tableField.getTableField())));
						}
					}else if(tableField.getFieldType().equals("整数")){
						m.put(tableField.getTableAndField(), String.valueOf(rs.getInt(tableField.getTableField())));
					}else if(tableField.getFieldType().equals("相关字段")){
						String returnvalue = "";
						String id = rs.getString(tableField.getTableField());// 获取相关字段值，序列id值
						if (id == null)
							continue;
						if (id.equals(""))
							continue;
						
						String str = tableField.getDefaultvalue();
						String arr[] = str.split("==");
						
						id = tagsDao.checkRelateIdInInTable(tableField.getFieldName(),id,m.get("id"));
						String sql_relate = "";
						if(id!=null&&!id.equals("")){
							sql_relate = " select " + arr[1]
								+ " from " + arr[0] + " where  id in ("
								+id
										 + ")";
							ps2 = ct2.prepareStatement(sql_relate);
							rs2 = ps2.executeQuery();
							while (rs2.next()) {
								returnvalue += rs2.getString(1);
								returnvalue += "<br>";
							}
						}
						

						m.put(tableField.getTableAndField(),returnvalue);
					}else if(tableField.getFieldType().equals("相关负责人")){
						String returnvalue = "";
						String id = rs.getString(tableField.getTableField());// 获取相关字段值，序列id值
						if (id == null)
							continue;
						if (id.equals(""))
							continue;
						String sql_relate = " select  realname "
								+ " from eluser where id in (" + id
								+ ")";

						ps2 = ct2.prepareStatement(sql_relate);
						rs2 = ps2.executeQuery();
						while (rs2.next()) {
							returnvalue += rs2.getString(1);
							returnvalue += "<br>";
						}

						m.put(tableField.getTableAndField(),returnvalue);
					}else{
						m.put(tableField.getTableAndField(), rs.getString(tableField.getTableField()));
					}
				}	
				listMap.add(m);
			}
			return listMap;
		} catch (Exception e) {
			logger.error("自定义标签查询失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
			
		
	}

	public void insertjisuanzu(int customreportid, String jisuanzuname,int type)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String[] ary = null;
		
		try{
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(ElQuerySql
						.getSQL(CustomReportConstants.CUSTOMREPORT_JISUANZU_INSERT));
				if(jisuanzuname != null && !jisuanzuname.equals("")){
					ary = jisuanzuname.split("=");
				}
				if(ary.length>0){
					for(int i=0;i<ary.length;i++){
						ps.setInt(1, customreportid);
						ps.setString(2, ary[i]);
						ps.setInt(3, type);
						ps.executeUpdate();
					}
				}
				
				
				//跟新orderid的值
				ps=ct.prepareStatement(ElQuerySql
						.getSQL(CustomReportConstants.CUSTOMREPORT_JISUANZE_QUERY_ID));
				rs = ps.executeQuery();
				if(rs.next()){
					updateCustomReportOrderid(rs.getInt(1));
				}
				
		} catch (Exception e) {
			logger.error("添加计算组信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void updateCustomReport_jisuanzu_by_columnname(String columnname, String value,int type,int formatnumber,int checkvalue,int customReport_relatetype,String relatecolumnname)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CustomReportJSZ jsz = null;
		try{
			jsz = QueryJSZByColumnname(columnname);
			if(jsz.getColumnname() != null || !jsz.getColumnname().equals(""))	{//存在
				ct= DBConnection.getConnection();
				if(formatnumber!=-1&&checkvalue!=-1){
					ps=ct.prepareStatement(ElQuerySql
							.getSQL(CustomReportConstants.CUSTOMREPORT_JISUANZU_UPDATE_BYCOLUMNNAME));
					ps.setString(1, value.replaceAll(" ", ""));
					ps.setInt(2, type);
					ps.setInt(3, formatnumber);
					ps.setInt(4, checkvalue);
					ps.setInt(5, customReport_relatetype);
					ps.setString(6, columnname);
					ps.executeUpdate();
				}else{
					ps=ct.prepareStatement(ElQuerySql
							.getSQL(CustomReportConstants.CUSTOMREPORT_JISUANZU_UPDATE_BYCOLUMNNAME_));
					ps.setString(1, value.replaceAll(" ", ""));
					ps.setInt(2, type);
					ps.setInt(3, checkvalue);
					ps.setInt(4, customReport_relatetype);
					ps.setString(5, relatecolumnname);
					ps.setString(6, columnname);
					ps.executeUpdate();
				}
			}
			
		} catch (Exception e) {
			logger.error("添加计算公式失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	
	}
	
	public void updateCustomReportOrderid(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORT_JISUAN_UPDATE_ORDERID_BY_ID));
			ps.setInt(1, id);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("跟新排序id失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	//根据columnname查询计算方式
	public CustomReportJSZ QueryJSZByColumnname(String columnname)throws ElException{
		boolean flag = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CustomReportJSZ jsz = null;
		try{
				
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(ElQuerySql
						.getSQL(CustomReportConstants.CUSTOMREPORT_JISUANZU_QUERY_BYCOLUMNNAME));
				ps.setString(1, columnname);
				rs = ps.executeQuery();
				if(rs.next()){
					jsz = new CustomReportJSZ();
					jsz.setId(rs.getInt("id"));
					jsz.setCustomreportid(rs.getInt("customreportid"));
					jsz.setColumnname(rs.getString("columnname"));
					jsz.setFormula(rs.getString("formula"));
					jsz.setType(rs.getInt("type"));
					jsz.setFormatnumber(rs.getInt("formatnumber"));
					jsz.setViewjindutiao(rs.getInt("viewjindutiao"));
					jsz.setShowview(rs.getInt("showview"));
					jsz.setRelatetype(rs.getInt("relatetype"));
					jsz.setRelatecolumnname(rs.getString("relatecolumnname"));
					jsz.setOrderid(rs.getInt("orderid"));
				}
				
		} catch (Exception e) {
			logger.error("根据columnname查询计算方式失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return jsz;
	}

	public List<CustomReportJSZ> showzijisuan(int customreportid,int type) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CustomReportJSZ> jszs = new ArrayList<CustomReportJSZ>();
		CustomReportJSZ jsz = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if(type == 0){
				sql = ElQuerySql
				.getSQL(CustomReportConstants.CUSTOMREPORT_JISUANZU_LIST_BYID);
			}else if(type == 2){
				sql = ElQuerySql
				.getSQL(CustomReportConstants.CUSTOMREPORT_JISUANZU_LIST_BYID_RELATETYPE_2);
			}
			ps = ct.prepareStatement(sql);
			ps.setInt(1, customreportid);
			rs = ps.executeQuery();
			while(rs.next()){
				jsz = new CustomReportJSZ();
				jsz.setId(rs.getInt("id"));
				jsz.setCustomreportid(rs.getInt("customreportid"));
				jsz.setColumnname(rs.getString("columnname"));
				jsz.setFormula(rs.getString("formula"));
				jsz.setType(rs.getInt("type"));
				jsz.setFormatnumber(rs.getInt("formatnumber"));
				jsz.setViewjindutiao(rs.getInt("viewjindutiao"));
				jsz.setShowview(rs.getInt("showview"));
				jsz.setRelatetype(rs.getInt("relatetype"));
				jsz.setRelatecolumnname(rs.getString("relatecolumnname"));
				jsz.setOrderid(rs.getInt("orderid"));
				jszs.add(jsz);
			}
		} catch (Exception e) {
			logger.error("获取计算组出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return jszs;
	}

	public List<CustomReportJSZ> queryCustomReport_jisuanzu_list_byid(
			int customreportid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CustomReportJSZ> jszs = new ArrayList<CustomReportJSZ>();
		CustomReportJSZ jsz = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORT_JISUANZU_LIST_BYID));
			ps.setInt(1, customreportid);
			rs = ps.executeQuery();
			while(rs.next()){
				jsz = new CustomReportJSZ();
				jsz.setId(rs.getInt("id"));
				jsz.setCustomreportid(rs.getInt("customreportid"));
				jsz.setColumnname(rs.getString("columnname"));
				jsz.setFormula(rs.getString("formula"));
				jsz.setType(rs.getInt("type"));
				jsz.setFormatnumber(rs.getInt("formatnumber"));
				jsz.setViewjindutiao(rs.getInt("viewjindutiao"));
				jsz.setShowview(rs.getInt("showview"));
				jsz.setRelatetype(rs.getInt("relatetype"));
				jsz.setRelatecolumnname(rs.getString("relatecolumnname"));
				jsz.setOrderid(rs.getInt("orderid"));
				jszs.add(jsz);
			}
		} catch (Exception e) {
			logger.error("获取计算组出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return jszs;
	}

	public Map<String,String> deleteLableById(int id) throws ElException {
		CallableStatement cs  = null;
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		Map<String,String> map = null;
		try {
			ct = DBConnection.getConnection();
			cs = ct.prepareCall("{call deletelable(?,?,?)}"); 
			cs.setInt(1, id);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.VARCHAR);  
			cs.execute();
			map = new HashMap<String,String>();
			map.put("name", cs.getString(2));
			map.put("resultpage", cs.getString(3));
		} catch (Exception e) {
			logger.error("删除报表标签出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return map;
	}
	
	public static boolean checkIdIsIn(String[] array,int id){
		boolean flag = false;
		String temp = "";
		if(array != null && array.length>0){
			for(int i=0;i<array.length;i++){
				temp = array[i];
				if(temp != null && !temp.equals("")){
					if(Integer.parseInt(temp) == id){
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	public int queryCountByTableAndColumn(String tablename,
			String columnname,String id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String value = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			sql = " select " + columnname + " from " + tablename ;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString(1) != null && !rs.getString(1).equals("")){
					value = rs.getString(1);//相关客户ids
					if(checkIdIsIn(value.split(","),Integer.parseInt(id))){
						count += 1;
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询报表标签出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	public double querySumByTableAndColumn(String tablename,
			String column1,String column2,String id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String value = "";
		double money = 0;
		try {
			ct = DBConnection.getConnection();
			sql = "select " + column1 + " ," + column2 + " from " + tablename ;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString(1) != null && !rs.getString(1).equals("")){
					value = rs.getString(1);//相关客户ids
					if(checkIdIsIn(value.split(","),id)){
						money += rs.getDouble(2) != 0.0 ? rs.getDouble(2):0.0;
					}
				}
			}
		} catch (Exception e) {
			logger.error("出错出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return money;
	}

	public boolean checkJSZNameIsExist(String jszName, int customreportid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORTJSZ_BOOLEAN_CHECKNAMEISEXIST));
			ps.setInt(1, customreportid);
			ps.setString(2, jszName);
			rs = ps.executeQuery();
			if(rs.next()){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("判断需要添加的计算组名称是否已经存在出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public void changeJSZId(int customreportid, String value)
			throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
		String[] values = null;
		if(value != null && !value.equals("")){
			values = value.split(",");
		}
		try {
//			ct = DBConnection.getConnection();
			if(values!=null&&values.length>0){
				for(int i=0;i<values.length;i++){
					changeJSZId_one(customreportid,values[i].split("=="));
				}
			}
			
		} catch (Exception e) {
			logger.error("修改统计字段排序出错！", e);
			throw new ElException(e);
		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void changeJSZId_one(int customreportid,String[] value) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORT_JISUAN_UPDATE_ORDERID_BY_COLUMNNAME_AND_CUSTOMREPORTID));
			ps.setInt(1, Integer.parseInt(value[1]));
			ps.setString(2, value[0].trim());
			ps.setInt(3, customreportid);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改统计字段排序出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	
	public void deleteJSZById(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CustomReportConstants.CUSTOMREPORT_JISUAN_DELETE_BY_ID));
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改统计字段排序出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}


}
