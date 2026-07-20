package com.sopia.lable.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.CheckHtml;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.OracleBlob;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.forumman.dao.impl.ForumAdminDaoImpl;
import com.sopia.lable.common.LableCommon;
import com.sopia.lable.dao.CustomLableDao;
import com.sopia.lable.entites.CirculationListLable;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.LableTree;
import com.sopia.lable.entites.SearchLable;
import com.sopia.lable.entites.Table;
import com.sopia.lable.entites.TableField;
import com.sopia.schedule.entities.CustomReport;

public class CustomLableDaoImpl implements CustomLableDao{
	private static final Log logger = LogFactory.getLog(ForumAdminDaoImpl.class);
	public List<Table> lable_getsystable() throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Table>  tableList=null;
		try{
			String  sql ="select name,tablename from lable_commontable";
			
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			tableList=new ArrayList<Table>();
			while(rs.next()){
				Table  table=new Table();
				table.setName(rs.getString(1));
				table.setTableName(rs.getString(2));
				tableList.add(table);
			}
		} catch (Exception e) {
			logger.error("查询系统表信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tableList;
	}

	public List<Table> lable_getusertable()throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Table>  tableList=null;
		try{
			String  sql ="select modulename,tablename from TB_MODULE_MANAGE";
			
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			tableList=new ArrayList<Table>();
			while(rs.next()){
				Table  table=new Table();
				table.setName(rs.getString(1));
				table.setTableName(rs.getString(2));
				tableList.add(table);
			}
		} catch (Exception e) {
			logger.error("查询用户自定义表信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tableList;
	}

	public List<Table> lable_getlabletableandfield(String name, String tableName)
			throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Table>  tableList=null;
		try{
			String  sql ="select tableinfo,tablefield from "+tableName+" where  name=? ";		
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setString(1, name);
			rs=ps.executeQuery();
			
			if(rs.next()){
				tableList=new ArrayList<Table>();
				String tableArr[]=null;
				String filedArr[]=null;
					if(rs.getString(1)!=null){
						 tableArr =rs.getString(1).split("-");
					
					if(rs.getString(2)!=null){
						 filedArr =rs.getString(2).split("-");
					
					}
				
					tableList=this.Lable_getTableByArr(tableArr);
					for (Table t : tableList) {
						//将含有表名的集合循环查出其自定义标签所对应的字段
						t.setField(lable_gettableFieldListByList(LableCommon.getTableFiledByARR(filedArr, t.getTableName()), t.getTableName()));					
					}
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
	/**
	 * 通过传入的表名数组，返回表信息
	 * @param tableArr:一个存放表名的数组
	 * @return 
	 * @throws ElException
	 */
	public List<Table>  Lable_getTableByArr(String tableArr[]) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Table>  tableList=null;
	
		try{
			//现在系统表中查询
			String  sql ="select name,tablename from lable_commontable where tablename=? ";			
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			tableList=new ArrayList<Table>();
			for (String  name : tableArr) {
				ps.setString(1, name);
				rs=ps.executeQuery();
				if(rs.next()){
					Table  table=new Table();
					table.setName(rs.getString(1));
					table.setTableName(rs.getString(2));
					tableList.add(table);
				}
			}
			//再去自定义表中查询
			sql="select modulename , tablename from TB_MODULE_MANAGE  where tablename=? ";
			ps=ct.prepareStatement(sql);
			for (String  name : tableArr) {
				ps.setString(1, name);
				rs=ps.executeQuery();
				if(rs.next()){
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
	/**
	 * 通过一个包含有用“，”分割的字段名的字符串，及表名，去字段信息表中查询字段信息
	 * @param fieldStr  字段名字符串
	 * @param tableName 表名
	 * @return
	 * @throws ElException
	 */
	public List<TableField> lable_gettableFieldListByList(String fieldStr,String tableName) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TableField>  tableFieldList=null;	
		String  err=null;
		try{
			//先在系统字段表中查询字段信息
			err="系统字段表查询失败";
			String sql="select fieldname , name, tablename ,fieldtype from lable_sysfield  where tablename=? and fieldname in ("+fieldStr+") ";
			ct= DBConnection.getConnection();
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
				tableFieldList.add(tableField);
			}
			
			//如果在系统字段表中没有找到信息，则去自定义字段表中查找字段信息	
			if(tableFieldList==null){
				err="自定义字段表查询失败";
				sql ="select column_name,name_display,table_name,display_type from TB_DESIGNE where table_name=? and column_name in ("+fieldStr+") ";			
				
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
						tableFieldList.add(tableField);
					}
					//union all 自定义表固定字段(ID)
					if(tableFieldList!=null){
						TableField  tableField=new TableField();
						tableField.setName("编号ID");
						tableField.setFieldName("ID");
						tableField.setTableName(tableName.toUpperCase());
						tableField.setFieldType("整数");
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

	public void lable_addlable(String name,int type) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			
			String sql="insert into lable_circulation(name,type,lable,createtime) values(?,?,empty_blob(),?) ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, name);
			ps.setInt(2, type);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
			
		
		} catch (Exception e) {
		logger.error("加入标签名失败！", e);
		throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	
		
	}
	public void lable_addsearchlable(String name,int type) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			
			String sql="insert into lable_search(name,type,searchlable) values(?,?,empty_blob()) ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, name);
			ps.setInt(2, type);
			ps.executeUpdate();
			OracleBlob setblob1 = new OracleBlob("lable_search","name","'"+name+"'","searchlable","<input type='button' value='搜索' id='zidingyisearchsub' />","添加搜索按钮失败",ct);
			setblob1.updateContent(); 
		} catch (Exception e) {
		logger.error("加入标签名失败！", e);
		throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	
		
	}
	public List<TableField> lable_getFieldByTableName(String tableName) throws ElException {
		
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<TableField>  tableFieldList=null;	
			String  err=null;
			try{
				//先在系统字段表中查询字段信息
				err="系统字段表查询失败";
				String sql="select fieldname , name, tablename ,fieldtype from lable_sysfield  where tablename=?  ";
				ct= DBConnection.getConnection();
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
					tableFieldList.add(tableField);
				}
				
				//如果在系统字段表中没有找到信息，则去自定义字段表中查找字段信息	
				if(tableFieldList==null){
					err="自定义字段表查询失败";
					sql ="select column_name,name_display,table_name,display_type,default_value from TB_DESIGNE where table_name=?  ";			
					
					
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
							tableFieldList.add(tableField);
						}
						//union all 自定义表固定字段(ID)
						if(tableFieldList!=null){
							TableField  tableField=new TableField();
							tableField.setName("编号ID");
							tableField.setFieldName("ID");
							tableField.setTableName(tableName.toUpperCase());
							tableField.setFieldType("整数");
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

	public Lable lable_getlableby(String tableName,String lableName) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Lable  lable=null;	
		try{
			String sql="select tableinfo , tablefield from "+tableName+"  where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, lableName);
			rs=ps.executeQuery();
			if(rs.next()){
				lable = new Lable();
				lable.setTablestr(rs.getString(1)==null?"":rs.getString(1));
				lable.setFieldstr(rs.getString(2)==null?"":rs.getString(2));
				
				
			}
			
		} catch (Exception e) {
			logger.error("查询表"+tableName+"表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lable;
		
		
		
	}
	/**
	 * 修改标签 表及字段信息失败
	 * @param tableName:表名
	 * @param name:标签名
	 * @param lable:要修改的信息
	 * @throws ElException 
	 */
	public void lable_updlabletableinfoAndField(String tableName,String name,Lable lable) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		try{
			if(lable.getTablestr()==null&&lable.getFieldstr()==null){
			}else{
				String str="";
				if(lable.getTablestr()!=null){
					str= "tableinfo=tableinfo||'"+lable.getTablestr()+"' , " ; 
				}if(lable.getFieldstr()!=null){
					
					str+=" tablefield=tablefield||'"+lable.getFieldstr()+"' ";
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
	//通过标签名删除标签表中的表名和字段信息
	public  void  lable_delelableTableInfo(String tableName,Lable lable,String lableName) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;		
		try{			
				String sql="update "+tableName+" set tableinfo=? , tablefield=?  where name=?  ";
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(sql);			
				ps.setString(1, lable.getTablestr());
				ps.setString(2, lable.getFieldstr());
				ps.setString(3, lableName);
				ps.executeUpdate();			
			
		} catch (Exception e) {
			logger.error("修改"+tableName+"表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}
	public void lable_updlablesql(String tableName,CirculationListLable cilable) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;		
		try{			
				String sql="update "+tableName+" set sqlcondition=? ,pagesize=?,keyword=?  where name=?  ";
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(sql);			
				ps.setString(1, cilable.getSqlCondition());	
				ps.setInt(2, cilable.getPageSize());
				ps.setString(3, cilable.getKeyword());
				ps.setString(4, cilable.getName());
				ps.executeUpdate();			
			
		} catch (Exception e) {
			logger.error("修改"+tableName+"表sql信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
		
		
	}
	public void lable_updsearchlablesql(String tableName,SearchLable cilable) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;		
		try{			
				String sql="update "+tableName+" set sqlcondition=? ,pagesize=? ,type=?,keyword=? where name=?  ";
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(sql);			
				ps.setString(1, cilable.getSqlCondition());	
				ps.setInt(2, cilable.getPageSize());
				ps.setInt(3, cilable.getType());
				ps.setString(4, cilable.getKeyword());
				ps.setString(5, cilable.getName());
				ps.executeUpdate();			
			
		} catch (Exception e) {
			logger.error("修改"+tableName+"表sql信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
public void lable_updsearchlablesearchset(String tableName,SearchLable cilable) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;		
		try{			
				String sql="update "+tableName+" set  searchlable = empty_blob()  where name=?  ";
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(sql);			
				ps.setString(1, cilable.getName());	
				ps.executeUpdate();	
				OracleBlob setblob1 = new OracleBlob(tableName,"name","'"+cilable.getName()+"'","searchlable",cilable.getSearchlable(),"修改标签搜索标签失败",ct);
				setblob1.updateContent(); 
		} catch (Exception e) {
			logger.error("修改"+tableName+"表标签搜索框信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void lable_updlableHTML(String tableName,Lable cilable)throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;		
		try{			
				String sql="update "+tableName+" set lable = empty_blob() ,sql=?  where name=?  ";
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(sql);		
				ps.setString(1, cilable.getSql());
				ps.setString(2, cilable.getName());
				ps.executeUpdate();			
				OracleBlob setblob1 = new OracleBlob(tableName,"name","'"+cilable.getName()+"'","lable",cilable.getLable(),"修改标签循环体失败",ct);
				setblob1.updateContent(); 
			
		} catch (Exception e) {
			logger.error("修改"+tableName+"表sql信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public Lable lable_getlablesqllable(String tableName,Lable lable)throws ElException{
		

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Lable  l=null;	
		try{
			String sql="select tableinfo , tablefield,pagesize,sqlcondition,groupby,lable ,name,type,keyword,labletreeid from "+tableName+"  where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, lable.getName());
			rs=ps.executeQuery();
			if(rs.next()){
				l = new Lable();
				l.setTablestr(rs.getString(1)==null?"":rs.getString(1));
				l.setFieldstr(rs.getString(1)==null?"":rs.getString(2));
				l.setPageSize(rs.getInt(3));
				l.setSqlCondition(rs.getString(4)==null?"":rs.getString(4));
				l.setOrder(rs.getString(5)==null?"":rs.getString(5));
				l.setLable(new OracleBlob().getContent(rs.getBlob(6)));
				l.setName(rs.getString(7));
				l.setType(rs.getInt(8));
				l.setKeyword(rs.getString(9));
				l.setLabletreeid(rs.getInt(10));
			}
			
		} catch (Exception e) {
			logger.error("查询表"+tableName+"表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return l;
		
		
	}
public Lable lable_getlablesqllablesql(String tableName,String lablename)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Lable  l=null;	
		try{
			String sql="select tableinfo , tablefield,pagesize,sqlcondition,groupby,lable ,name,sql,type from "+tableName+"  where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, lablename);
			rs=ps.executeQuery();
			if(rs.next()){
				l = new Lable();
				l.setTablestr(rs.getString(1)==null?"":rs.getString(1));
				l.setFieldstr(rs.getString(1)==null?"":rs.getString(2));
				l.setPageSize(rs.getInt(3));
				l.setSqlCondition(rs.getString(4)==null?"":rs.getString(4));
				l.setOrder(rs.getString(5)==null?"":rs.getString(5));
				l.setLable(new OracleBlob().getContent(rs.getBlob(6)));
				l.setName(rs.getString(7));
				l.setSql(rs.getString(8));
				l.setType(rs.getInt(9));
			}
			
		} catch (Exception e) {
			logger.error("查询表"+tableName+"表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return l;
		
		
	}
	public List<TableField> lable_getTableFieldByField(String arr[])throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String err ="";
		List<TableField>  l=null;	
		try{
			//现在系统字段表中查找
			err="查询系统字段表失败";
			String sql="select fieldname , name, tablename ,fieldtype from lable_sysfield  where tablename=?  and fieldname=? ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);	
			for (String str : arr) {
				ps.setString(1, LableCommon.lablecommon_delestrtostr(str, "."));
				ps.setString(2, LableCommon.lablecommon_delestrtostr2(str, "."));
				rs=ps.executeQuery();
				if(rs.next()){
					if(l == null) l= new ArrayList<TableField>();//如果找到 new list对象
					TableField tf = new TableField();//new tableField对象
					tf.setFieldName(rs.getString(1));
					tf.setName(rs.getString(2));
					tf.setTableName(rs.getString(3));
					tf.setFieldType(rs.getString(4));
					l.add(tf);
				}
			}
			//然后再自定义表中查找
			err="查询自定义字段表失败";
			sql ="select column_name,name_display,table_name,display_type from TB_DESIGNE where table_name=? and column_name=? ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			if(l==null){//如果为空直接查找
				for (String str : arr) {
					
					ps.setString(1, LableCommon.lablecommon_delestrtostr(str, "."));
					ps.setString(2, LableCommon.lablecommon_delestrtostr2(str, "."));
					rs=ps.executeQuery();
					if(rs.next()){
						if(l == null) l= new ArrayList<TableField>();//如果找到 new list对象
						TableField tf = new TableField();//new tableField对象
						tf.setFieldName(rs.getString(1));
						tf.setName(rs.getString(2));
						tf.setTableName(rs.getString(3));
						tf.setFieldType(rs.getString(4));
						l.add(tf);
					}
				}
				//union all 自定义表固定字段(ID)
				if(l!=null && l.get(0)!=null && l.get(0).getTableName()!=null && !l.get(0).getTableName().equals("")){
					TableField  tableField=new TableField();
					tableField.setName("编号ID");
					tableField.setFieldName("ID");
					tableField.setTableName(l.get(0).getTableName().toUpperCase());
					tableField.setFieldType("整数");
					l.add(tableField);
				}
			}
				else{//如果不为空
					for (String str : arr) {
						int index =0;
						for (TableField tableField : l) {
							//判断 是否已找到
							
							index=str.indexOf(tableField.getFieldName());
							
						}
						if(index==-1){
							//如果没找到
							ps.setString(1, LableCommon.lablecommon_delestrtostr(str, "."));
							ps.setString(2, LableCommon.lablecommon_delestrtostr2(str, "."));
							rs=ps.executeQuery();
							if(rs.next()){
								
								TableField tf = new TableField();//new tableField对象
								tf.setFieldName(rs.getString(1));
								tf.setName(rs.getString(2));
								tf.setTableName(rs.getString(3));
								tf.setFieldType(rs.getString(4));
								l.add(tf);
							}
						}
					}
					//union all 自定义表固定字段(ID)
					if(l!=null && l.get(0)!=null && l.get(0).getTableName()!=null && !l.get(0).getTableName().equals("")){
						TableField  tableField=new TableField();
						tableField.setName("编号ID");
						tableField.setFieldName("ID");
						tableField.setTableName(l.get(0).getTableName().toUpperCase());
						tableField.setFieldType("整数");
						l.add(tableField);
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
	public String lable_getorderStr(String tableName,String name) throws ElException{

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			String sql="select groupby from "+tableName+"  where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setString(1, name);
			rs=ps.executeQuery();
			return rs.next()?rs.getString(1):"";
		} catch (Exception e) {
			logger.error("从"+tableName+" 中查询排序信息失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public String lable_getgroupStr(String tableName,String name) throws ElException{

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			String sql="select groupby_ from "+tableName+"  where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setString(1, name);
			rs=ps.executeQuery();
			return rs.next()?rs.getString(1):"";
		} catch (Exception e) {
			logger.error("从"+tableName+" 中查询分组信息失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	/**
	 * 修改排序设置
	 * @param updstr
	 * @param tableName
	 * @param name
	 * @throws ElException
	 */
	public void lable_updlableorder(String updstr,String tableName,String name) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			String sql="update "+tableName+" set groupby=?    where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setString(1, updstr);
			ps.setString(2, name);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("从"+tableName+" 中查询排序信息失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	/**
	 * 修改分组设置
	 * @param updstr
	 * @param tableName
	 * @param name
	 * @throws ElException
	 */
	public void lable_updlablegroup(String updstr,String tableName,String name) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			String sql="update "+tableName+" set groupby_=?    where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setString(1, updstr);
			ps.setString(2, name);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("从"+tableName+" 中修改分组信息失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public List<Map<String,Object>> getMap(List<TableField> list ,String sql ,CustomReport customReport) throws ElException{
		
		List<Map<String,Object>> listMap= null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		Connection ct2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		try{
			ct2= DBConnection.getConnection();
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement( sql );
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
						m.put(tableField.getTableAndField(), String.valueOf(rs.getDouble(tableField.getTableField())));
					}else if(tableField.getFieldType().equals("整数")){
						m.put(tableField.getTableAndField(), String.valueOf(rs.getInt(tableField.getTableField())));
					}else if(tableField.getFieldType().equals("富文本")){
						m.put(tableField.getTableAndField(), new OracleBlob().getContent(rs.getBlob(tableField.getTableField())));
					}else if(tableField.getFieldType().equals("相关字段")){
						String returnvalue = "";
						String id = rs.getString(tableField.getTableField());// 获取相关字段值，序列id值
						if (id == null)
							continue;
						if (id.equals(""))
							continue;
						String str = tableField.getDefaultvalue();
						String arr[] = str.split("==");
						String sql_relate = " select " + arr[1]
								+ " from " + arr[0] + " where id in ("
								+ id + ")";

						ps2 = ct2.prepareStatement(sql_relate);
						rs2 = ps2.executeQuery();
						while (rs2.next()) {
							returnvalue += rs2.getString(1);
							returnvalue += "<br>";
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
	/**
	 * 执行分页标签的查询语句
	 */
	public List<Map> getpageMap(List<TableField> list ,String sql ,int pagesize,int pagenow) throws ElException{
		
		List<Map> listMap= null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement( sql );
			ps.setInt(1, pagesize);
			ps.setInt(2, pagenow);
			rs=ps.executeQuery();
			while(rs.next()){
				if(listMap==null) listMap = new ArrayList<Map>();
				Map<String, Object>  m = new HashMap<String, Object>();
			for (TableField tableField : list) {
				
				if(tableField.getFieldType().equals("文本")){
					m.put(tableField.getTableAndField(), rs.getString(tableField.getTableField()));
				}else if(tableField.getFieldType().equals("整数")){
					
					m.put(tableField.getTableAndField(), rs.getInt(tableField.getTableField()));
				}else if(tableField.getFieldType().equals("富文本")){
					m.put(tableField.getTableAndField(), new OracleBlob().getContent(rs.getBlob(tableField.getTableField())).toString());
					
				}else if(tableField.getFieldType().equals("实数")){
					
					m.put(tableField.getTableAndField(), rs.getDouble(tableField.getTableField()));
				}
				else if(tableField.getFieldType().equals("日期")){
					m.put(tableField.getTableAndField(), rs.getTimestamp(tableField.getTableField()));
				}
				else{
					
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
	public List<Lable> lable_getalllable(int pageNow,int pageSize,LableTree lableTree,Lable lable)throws ElException{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<Lable> listLable = null;	
			LableTree lTree = null;
			
			String sqljoin = " join ("
				+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.generateSQLByTree("labletree", lableTree, true)
				+ ") lab on lab.id=oo.labletreeid ";
			String sqlwhere = " where 1=1 ";
			String[] keys;
			try{
				if(lable!=null){
					if(lable.getName()!=null&&!lable.getName().equals("")){
						sqlwhere += " and oo.name like '%" + lable.getName() + "%'";
					}
					if(lable.getKeyword()!=null&&!lable.getKeyword().equals("")){
						keys = lable.getKeyword().split(" ");
						if(keys!=null && keys.length>0){
							sqlwhere = sqlwhere + " and ";
							for(int i=0;i<keys.length;i++){
								if(i == 0){
									if(keys.length==1){
										sqlwhere = sqlwhere + "  (oo.keyword like '%" + keys[i] + "%') ";
									}else{
										sqlwhere = sqlwhere + "  (oo.keyword like '%" + keys[i] + "%' ";
									}
								}else if(i == keys.length - 1){
									sqlwhere = sqlwhere + " or oo.keyword like '%" + keys[i] + "%') ";
								}else{
									sqlwhere = sqlwhere + " or oo.keyword like '%" + keys[i] + "%' ";
								}
								
							}
						}
					}
				}
				String sql="select * from (select t.*, rownum rn from (select m.* from ( select oo.name,oo.type, (1) as a ,oo.labletreeid,oo.createtime,lab.id as labid,lab.name as labname from lable_circulation oo  " + sqljoin + sqlwhere + 
						" union all" +
						" select oo.name,oo.type,(2) as a ,oo.labletreeid,oo.createtime,lab.id as labid,lab.name as labname from LABLE_SEARCH oo " + sqljoin + sqlwhere + 
						" ) m order by createtime desc )t where rownum <= ? ) where rn>=?  ";
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(sql);
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
				rs=ps.executeQuery();
				while(rs.next()){
					if(listLable==null) listLable = new ArrayList<Lable>();
					Lable  l = new Lable();
					l.setName(rs.getString(1));
					
					if(rs.getInt(3)==2){
						l.setType(3);
					}else{
						l.setType(rs.getInt(2));
					}
					l.setLabletreeid(rs.getInt(4));
					l.setCreatetime(rs.getTimestamp(5));
					lTree = new LableTree(rs.getInt(6),rs.getString(7));
					l.setLableTree(lTree);
					listLable.add(l);
				}
			} catch (Exception e) {
				logger.error("查询自定义标签名称失败 ", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return listLable;
			
			
		}
	public int lable_getalllableSize(LableTree lableTree,Lable lable)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqljoin = " join ("
			+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("labletree", lableTree, true)
			+ ") lab on lab.id=oo.labletreeid ";
		String sqlwhere = " where 1=1 ";
		String[] keys;
		try{
			if(lable!=null){
				if(lable.getName()!=null&&!lable.getName().equals("")){
					sqlwhere += " and oo.name like '%" + lable.getName() + "%'";
				}
				if(lable.getKeyword()!=null&&!lable.getKeyword().equals("")){
					keys = lable.getKeyword().split(" ");
					if(keys!=null && keys.length>0){
						sqlwhere = sqlwhere + " and ";
						for(int i=0;i<keys.length;i++){
							if(i == 0){
								if(keys.length==1){
									sqlwhere = sqlwhere + "  (oo.keyword like '%" + keys[i] + "%') ";
								}else{
									sqlwhere = sqlwhere + "  (oo.keyword like '%" + keys[i] + "%' ";
								}
							}else if(i == keys.length - 1){
								sqlwhere = sqlwhere + " or oo.keyword like '%" + keys[i] + "%') ";
							}else{
								sqlwhere = sqlwhere + " or oo.keyword like '%" + keys[i] + "%' ";
							}
							
						}
					}
				}
			}
			String sql="select count(*) from (select oo.name from lable_circulation oo " + sqljoin + sqlwhere + 
					" union all" +
						" select oo.name  from LABLE_SEARCH  oo  " + sqljoin + sqlwhere + ")";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			
			rs=ps.executeQuery();
			rs.next();
				
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("查询自定义标签名称失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
		
	}  
	public int  lable_getsqlsagecount(String sql) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement( sql );
			rs=ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("查询分页大小失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public SearchLable lable_getlablesearchlable(String tableName,String lableName)throws ElException{
		

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		SearchLable  l=null;	
		try{
			String sql="select tableinfo , tablefield,pagesize,sqlcondition,groupby,lable ,name,type,searchlable,sql,labletreeid,keyword from "+tableName+"  where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, lableName);
			rs=ps.executeQuery();
			if(rs.next()){
				l = new SearchLable();
				l.setTablestr(rs.getString(1)==null?"":rs.getString(1));
				l.setFieldstr(rs.getString(1)==null?"":rs.getString(2));
				l.setPageSize(rs.getInt(3));
				l.setSqlCondition(rs.getString(4)==null?"":rs.getString(4));
				l.setOrder(rs.getString(5)==null?"":rs.getString(5));
				l.setLable(new OracleBlob().getContent(rs.getBlob(6)));
				l.setName(rs.getString(7));
				l.setType(rs.getInt(8));
				l.setSearchlable(new OracleBlob().getContent(rs.getBlob(9)));
				l.setSql(rs.getString(10));
				l.setLabletreeid(rs.getInt(11));
				l.setKeyword(rs.getString(12));
			}
			
		} catch (Exception e) {
			logger.error("查询表"+tableName+"表信息及字段信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return l;
	}

	public void updateLableTreeid(String name, int labletreeid,String table)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			String sql="update  " + table + " set labletreeid=? where name=?";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);		
			ps.setInt(1, labletreeid);
			ps.setString(2, name);
			ps.executeUpdate();
			
		
		} catch (Exception e) {
		logger.error("加入标签名失败！", e);
		throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public Lable getLableByTablenameAndName_loop(String lableName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Lable  l=null;	
		try{
			String sql="select tableinfo , tablefield,pagesize,sqlcondition,groupby,lable ,name,type,sql,labletreeid from lable_circulation   where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, lableName);
			rs=ps.executeQuery();
			if(rs.next()){
				l = new Lable();
				l.setTablestr(rs.getString(1)==null?"":rs.getString(1));
				l.setFieldstr(rs.getString(1)==null?"":rs.getString(2));
				l.setPageSize(rs.getInt(3));
				l.setSqlCondition(rs.getString(4)==null?"":rs.getString(4));
				l.setOrder(rs.getString(5)==null?"":rs.getString(5));
				l.setLable(new OracleBlob().getContent(rs.getBlob(6)));
				l.setName(rs.getString(7));
				l.setType(rs.getInt(8));
				l.setSql(rs.getString(9)==null?"":rs.getString(9));
				l.setLabletreeid(rs.getInt(10));
				
			}
			
		} catch (Exception e) {
			logger.error("根据name查询循环或者分页标签失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return l;
	}

	public SearchLable getLableByTablenameAndName_search(String lableName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		SearchLable  l=null;	
		try{
			String sql="select tableinfo , tablefield,pagesize,sqlcondition,groupby,lable ,name,type,sql,labletreeid,searchlable from lable_search   where name=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, lableName);
			rs=ps.executeQuery();
			if(rs.next()){
				l = new SearchLable();
				l.setTablestr(rs.getString(1)==null?"":rs.getString(1));
				l.setFieldstr(rs.getString(1)==null?"":rs.getString(2));
				l.setPageSize(rs.getInt(3));
				l.setSqlCondition(rs.getString(4)==null?"":rs.getString(4));
				l.setOrder(rs.getString(5)==null?"":rs.getString(5));
				l.setLable(new OracleBlob().getContent(rs.getBlob(6)));
				l.setName(rs.getString(7));
				l.setType(rs.getInt(8));
				l.setSql(rs.getString(9)==null?"":rs.getString(9));
				l.setLabletreeid(rs.getInt(10));
				l.setSearchlable(new OracleBlob().getContent(rs.getBlob(11)));
				
			}
			
		} catch (Exception e) {
			logger.error("根据name查询搜索标签失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return l;
	}

	public void insertDB_copy_search(SearchLable lable) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			
			String sql="insert into lable_search(name,pagesize,tableinfo,tablefield,sqlcondition,lable,groupby,sql,type,labletreeid,searchlable) values(?,?,?,?,?,empty_blob(),?,?,?,?,empty_blob()) ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, lable.getName());
			ps.setInt(2, lable.getPageSize());
			ps.setString(3, lable.getTablestr());
			ps.setString(4, lable.getFieldstr());
			ps.setString(5, lable.getSqlCondition());
			ps.setString(6, lable.getOrder());
			ps.setString(7, lable.getSql());
			ps.setInt(8, lable.getType());
			ps.setInt(9, lable.getLabletreeid());
			ps.executeUpdate();
			
			OracleBlob setblob1 = new OracleBlob("lable_search","name","'"+lable.getName()+"'","searchlable",lable.getSearchlable(),"添加searchlable失败",ct);
			setblob1.updateContent();
			
			setblob1 = new OracleBlob("lable_search","name","'"+lable.getName()+"'","lable",lable.getLable(),"添加lable失败",ct);
			setblob1.updateContent();
		
		} catch (Exception e) {
		logger.error("复制循环或者分页标签失败！", e);
		throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void insertDB_copy_loop(Lable lable) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			
			String sql="insert into lable_circulation(name,pagesize,tableinfo,tablefield,sqlcondition,lable,groupby,sql,type,labletreeid) values(?,?,?,?,?,empty_blob(),?,?,?,?) ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, lable.getName());
			ps.setInt(2, lable.getPageSize());
			ps.setString(3, lable.getTablestr());
			ps.setString(4, lable.getFieldstr());
			ps.setString(5, lable.getSqlCondition());
			ps.setString(6, lable.getOrder());
			ps.setString(7, lable.getSql());
			ps.setInt(8, lable.getType());
			ps.setInt(9, lable.getLabletreeid());
			ps.executeUpdate();
			
			
			OracleBlob setblob1 = new OracleBlob("lable_circulation","name","'"+lable.getName()+"'","lable",lable.getLable(),"添加lable失败",ct);
			setblob1.updateContent();
			
		
		} catch (Exception e) {
		logger.error("复制循环或者分页标签失败！", e);
		throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public boolean checkNameIsExist(String lablename, String table)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try{
			
			String sql="select count(1) from " + table + " where name=?";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);			
			ps.setString(1, lablename);
			rs = ps.executeQuery();
			
			if(rs.next()){
				flag = true;
			}
		
		} catch (Exception e) {
		logger.error("根据name判断标签名称是否存在失败！", e);
		throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
	
	

}
