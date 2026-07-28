package com.sopia.common;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * @param Connection
 *            //与oracle的连接
 * @param tableName
 *            //表名
 * @param primaryKey
 *            //表的主键名
 * @param sequence
 *            //表的序列名
 * @param fieldName
 *            //表的BLOB字段名
 * @param clobValue
 *            //表的BLOB字段值
 * @param explain
 *            //错误说明
 * @param OracleClob(Connection
 *            connection,String sequence,String tableName,String
 *            primaryKey,String fieldName,String blobValue)
 * @author hwc
 */
public class OracleBlob {
	private static final Log logger = LogFactory.getLog(OracleBlob.class);
	private static final Charset LEGACY_BLOB_CHARSET = Charset.forName("GBK");

	/**
	 * 课程资料来自旧 Oracle 数据库，其中一部分 BLOB 按 GBK 保存，另一部分
	 * 资料已是 UTF-8。先严格尝试 UTF-8，失败时再按 GBK 读取，以兼容两类数据。
	 */
	private String decodeBlobContent(byte[] content) throws Exception {
		CharsetDecoder utf8 = Charset.forName("UTF-8").newDecoder();
		utf8.onMalformedInput(CodingErrorAction.REPORT);
		utf8.onUnmappableCharacter(CodingErrorAction.REPORT);
		try {
			return utf8.decode(ByteBuffer.wrap(content)).toString();
		} catch (CharacterCodingException e) {
			return new String(content, LEGACY_BLOB_CHARSET);
		}
	}

	String tableName = null; // 表名
	String primaryKey = null; // 表的主键名
	String sequence = null; // 序列
	String primaryValue = null; // 表的主键值
	String fieldName = null; // 表的BLOB字段名
	String blobValue = null; // 表的BLOB字段值
	String explain = null; // 错误说明
	Connection ct = null; // 与oracle的连接

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

	public Connection getCt() {
		return ct;
	}

	public void setCt(Connection ct) {
		this.ct = ct;
	}

	/***************************************************************************
	 * 
	 * @param connection
	 * @param sequence
	 * @param tableName
	 * @param primaryKey
	 * @param fieldName
	 * @param clobValue
	 * @param explain
	 */
	public OracleBlob(Connection connection, String sequence, String tableName,
			String primaryKey, String fieldName, String clobValue,
			String explain) {
		this.ct = connection;
		this.tableName = tableName;
		this.primaryKey = primaryKey;
		this.sequence = sequence;
		this.fieldName = fieldName;
		this.blobValue = clobValue;
	}

	public OracleBlob(String tableName, String primaryKey, String primaryValue,
			String fieldName, String clobValue, String explain,
			Connection connection) {
		this.ct = connection;
		this.tableName = tableName;
		this.primaryKey = primaryKey;
		this.primaryValue = primaryValue;
		this.fieldName = fieldName;
		this.blobValue = clobValue;
	}

	public OracleBlob() {
	}

	public void addContent() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CLOB clob = null;
		BLOB blob = null;
		String sql;

		try {
			ct = DBConnection.getConnection();
			
//			sql = "select " + sequence + ".nextval from dual";
//			ps = ct.prepareStatement(sql);
//			rs = ps.executeQuery();
			
			sql = "select " + sequence + ".currval from dual";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				primaryValue = rs.getInt(1) + "";
			}
			sql = "select " + fieldName + " from " + tableName + " where "
					+ primaryKey + "=" + primaryValue + " for update";
			ct = DBConnection.getConnection();
			// ct.setAutoCommit(false);
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				blob = (oracle.sql.BLOB) rs.getBlob(1);
			}
			if(blob!=null){
				OutputStream wr = blob.getBinaryOutputStream();
				byte[] contentStr = this.blobValue.getBytes();
				wr.write(contentStr);
				wr.flush();
				wr.close();
			}else{
				logger.error("没有查到数据，addContent方法添加失败!");
			}
			//rs.close();
			// ct.commit();
		} catch (Exception e) {
			logger.error(this.explain, e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void updateContent() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		BLOB blob = null;
		String sql;

		try {
			sql = "select " + fieldName + " from " + tableName + " where "
					+ primaryKey + "=" + primaryValue + " for update";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				blob = (oracle.sql.BLOB) rs.getBlob(1);
			}
			if(blob!=null){
				OutputStream wr = blob.getBinaryOutputStream();
				byte[] contentStr = this.blobValue.getBytes();
				wr.write(contentStr);
				wr.flush();
				wr.close();
			}else{
				logger.error("没有查到数据，updateContent方法更新失败!");
			}
			//rs.close();
			// ct.commit();
		} catch (Exception e) {
			logger.error(this.explain, e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public String getContent(Clob clobc) throws ElException {
		int i;
		String cont = "";
		try {
			Clob clob = clobc;

			InputStream input = clob.getAsciiStream();
			int len = (int) clob.length();
			byte[] content = new byte[len];
//			while (-1 != (i = input.read(content, 0, content.length))) {
//				input.read(content, 0, i);
//			}
			do {
				i= input.read(content, 0, content.length);
			} while (i!=-1);
			cont = new String(content);
		} catch (Exception e) {
			logger.error("获取大字节内容错误", e);
		}
		return cont;
	}

	public String getContent(Blob blobc) throws ElException {
		int i;
		String cont = "";
		try {
			if (blobc != null) {
				Blob blob = blobc;
				InputStream input = blob.getBinaryStream();
				int len = (int) blob.length();
				byte[] content = new byte[len];
//				while (-1 != (i = input.read(content, 0, content.length))) {
//					input.read(content, 0, i);
//				}
				do {
					i= input.read(content, 0, content.length);
				} while (i!=-1);
				cont = decodeBlobContent(content);
			}
		} catch (Exception e) {
			logger.error("获取大字节内容错误", e);
		}
		return cont;
	}

	/**
	 * 供列表页用
	 * 
	 * @param blobc
	 * @return
	 * @throws ElException
	 */
	public String getContent_list(Blob blobc) throws ElException {
		int i;
		String cont = "";
		try {
			if (blobc != null) {
				Blob blob = blobc;
				InputStream input = blob.getBinaryStream();
				int len = (int) blob.length();
				byte[] content = new byte[len];
				// while(-1 != (i = input.read(content, 0, content.length))) {
				// input.read(content, 0, i);
				// }
				
					if (content.length > 1024) {
						int n=1;
						do{
							i=input.read(content, (n-1)*1024, 1024*n);
							cont = StringUtil.htmlParse_(decodeBlobContent(content));
							n++;
						}while(cont.trim().length()<200);
					} else {
						//input.read(content, 0, content.length);
						do {
							i= input.read(content, 0, content.length);
						} while (i!=-1);
						cont = StringUtil.htmlParse_(decodeBlobContent(content));
					}
//					cont = StringUtil.htmlParse_(new String(content));
			}
		} catch (Exception e) {
			logger.error("获取大字节内容(列表)错误", e);
		}
		return cont;
	}

	/**
	 * 供首页用
	 * 
	 * @param blobc
	 * @return
	 * @throws ElException
	 */
	public String getContent_index(Blob blobc) throws ElException {
		int i;
		String cont = "";
		try {
			if (blobc != null) {
				Blob blob = blobc;
				InputStream input = blob.getBinaryStream();
				int len = (int) blob.length();
				byte[] content = new byte[len];
				// while(-1 != (i = input.read(content, 0, content.length))) {
				// input.read(content, 0, i);
				// }
				if (content.length > 1024) {
					int n=1;
					do{
						i=input.read(content, (n-1)*1024, 1024*n);
						cont = StringUtil.htmlParse_(decodeBlobContent(content));
						n++;
					}while(cont.trim().length()<100);
				} else {
					//input.read(content, 0, content.length);
					do {
						i= input.read(content, 0, content.length);
					} while (i!=-1);
					cont = StringUtil.htmlParse_(decodeBlobContent(content));
				}
//				cont = new String(content);
			}
		} catch (Exception e) {
			logger.error("获取大字节内容错误", e);
		}
		return cont;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getBlobValue() {
		return blobValue;
	}

	public void setBlobValue(String blobValue) {
		this.blobValue = blobValue;
	}

	public String showClob(Connection conn, int id) throws Exception {
		Statement ps = null;
		ResultSet rs = null;
		try {
			String str = null;
			// conn=DBConnection.getConn();

			ps = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
					java.sql.ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery("select content from news where id=" + id
					+ " ");
			java.sql.Clob clob = null;
			if (rs.next()) {
				clob = rs.getClob("content");
				CLOB myclob = (CLOB) clob;
				long clen = myclob.length();
				char clobArray[] = new char[(int) clen];
//				int readednum = myclob.getChars(1, (int) clen, clobArray);
				StringBuffer sb = new StringBuffer();
				sb.append(clobArray);
				str = sb.toString();
			}
			return str;
		} catch (Exception e) {
			logger.error("显示大字节内容错误", e);
			throw new Exception(e);
		} finally {
			// DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
}
