package com.sopia.common;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import oracle.sql.CLOB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/***
 * @param Connection  	 //与oracle的连接 
 * @param tableName   	 //表名 
 * @param primaryKey  	 //表的主键名 
 * @param sequence   	 //表的序列名 
 * @param fieldName 	 //表的CLOB字段名 
 * @param clobValue 	 //表的CLOB字段值 
 * @param explain 		 //错误说明
 * @param OracleClob(Connection connection,String sequence,String tableName,String primaryKey,String fieldName,String clobValue)
 * @author hwc 
 */
public class OracleClob { 
	private static final Log logger = LogFactory.getLog(OracleClob.class);
	String tableName = null; //表名 
	String primaryKey = null; //表的主键名 
	String sequence = null; //序列 
	String primaryValue = null; //表的主键值 
	String fieldName = null; //表的CLOB字段名 
	String clobValue = null; //表的CLOB字段值 
	String explain = null;  //错误说明
	Connection ct = null; //与oracle的连接 
	


	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	} 
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getClobValue() {
		return clobValue;
	}
	public void setClobValue(String clobValue) {
		this.clobValue = clobValue;
	}
	public Connection getCt() {
		return ct;
	}
	public void setCt(Connection ct) {
		this.ct = ct;
	}
	/***
	 * 
	 * @param connection
	 * @param sequence
	 * @param tableName
	 * @param primaryKey
	 * @param fieldName
	 * @param clobValue
	 * @param explain
	 */
	public OracleClob(Connection connection,String sequence,String tableName,String primaryKey,String fieldName,String clobValue,String explain) { 
		this.ct = connection; 
		this.tableName = tableName; 
		this.primaryKey = primaryKey; 
		this.sequence = sequence; 
		this.fieldName = fieldName; 
		this.clobValue = clobValue;  
	}  

	public OracleClob(String tableName,String primaryKey,String primaryValue,String fieldName,String clobValue,String explain,Connection connection) { 
		this.ct = connection; 
		this.tableName = tableName; 
		this.primaryKey = primaryKey; 
		this.primaryValue = primaryValue; 
		this.fieldName = fieldName; 
		this.clobValue = clobValue;  
	}  
	public OracleClob() {   
	}  

	public void addContent()throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CLOB clob = null;
		String sql ;
		
		try {   
			sql ="select "+sequence+".currval from dual";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql); 
			rs = ps.executeQuery(); 
			if(rs.next()){
				primaryValue = rs.getInt(1)+"";
			} 
			sql = "select " + fieldName + " from " + tableName + " where " + primaryKey + "=" + primaryValue +" for update";  
			ct = DBConnection.getConnection(); 
		    //ct.setAutoCommit(false);      
			ps = ct.prepareStatement(sql);  
			rs = ps.executeQuery(); 
			if(rs.next()){ 
				clob = (oracle.sql.CLOB)rs.getClob(1); 
			}
			if(clob!=null){
				OutputStream wr = clob.getAsciiOutputStream();  
				byte[] contentStr = this.clobValue.getBytes();
				wr.write(contentStr);  
				wr.flush(); 
				wr.close();
			}else{
				logger.error("没有查到数据，addContent方法添加失败!");
			}
			//rs.close(); 
			//ct.commit();     
		} catch (Exception e) {
			logger.error(this.explain, e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	}
	

	public void updateContent()throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CLOB clob = null;
		String sql ;
		
		try {   
			sql = "select " + fieldName + " from " + tableName + " where " + primaryKey + "=" + primaryValue +" for update";  
			ct = DBConnection.getConnection(); 
		    ct.setAutoCommit(false);      
			ps = ct.prepareStatement(sql);  
			rs = ps.executeQuery(); 
			if(rs.next()){ 
				clob = (oracle.sql.CLOB)rs.getClob(1); 
			}
			if(clob!=null){
				OutputStream wr = clob.getAsciiOutputStream();  
				byte[] contentStr = this.clobValue.getBytes();
				wr.write(contentStr);  
				wr.flush(); 
				wr.close();
			}else{
				logger.error("没有查到数据，updateContent方法更新失败!");
			}
			//rs.close(); 
			//ct.commit();     
		} catch (Exception e) {
			logger.error(this.explain, e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	}
	
	public String getContent(Clob clobc)throws ElException { 
		int i ;
		String cont ="";
		try {
			Clob clob = clobc; 
			InputStream input = clob.getAsciiStream(); 
			int len = (int)clob.length(); 
			byte[] content = new byte[len];  
//			while(-1 != (i = input.read(content, 0, content.length))) { 
//				input.read(content, 0, i); 			
//			}
			do {
				i= input.read(content, 0, content.length);
			} while (i!=-1);
			cont = new String(content);
		} catch (Exception e) {
			logger.error("获取大文本内容错误",e);
		} 
		return cont;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
}
