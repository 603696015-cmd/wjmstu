package com.sopia.cms.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.cms.CMSConstants;
import com.sopia.cms.dao.LabelDao;
import com.sopia.cms.dao.TemplateDao;
import com.sopia.cms.entities.ColumnTemplate;
import com.sopia.cms.entities.Label;
import com.sopia.cms.entities.Template;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.forumman.ForumConstants;
import com.sopia.forumman.entities.ForumBlockType;

public class TemplateDaoImpl implements TemplateDao  {
	private static final Log logger = LogFactory.getLog(TemplateDaoImpl.class);
 
	/* (non-Javadoc)
	 * @see com.sopia.cms.impl.TemplateDao#addTemplate(com.sopia.cms.entities.Template)
	 */
	public void addTemplate(Template tmp) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null; 
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSTEMPLATE_ADD));
			ps.setString(1, tmp.getName());
			ps.setString(2, tmp.getJspTmp());
			ps.setString(3, tmp.getJsp()); 
			ps.setString(4, tmp.getRemark());
			ps.setString(5, tmp.getTmpType());
			ps.setInt(6, tmp.getTypeId()); 
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("Ìí¼ÓÄ£°åÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void updateTemplate(Template tmp) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null; 
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSTEMPLATE_UPDATE));
			ps.setString(1, tmp.getName()); 
			ps.setString(2, tmp.getRemark()); 
			ps.setInt(3, tmp.getId());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("ÐÞ¸ÄÄ£°åÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public Template getTemplate(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Template tmp = new Template();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSTEMPLATE_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {   
				tmp.setId(rs.getInt(1));
				tmp.setName(rs.getString(2));
				tmp.setJspTmp(rs.getString(3));
				tmp.setJsp(rs.getString(4));
				tmp.setRemark(rs.getString(5));
			}
		} catch (Exception e) {
			logger.error("²éÑ¯Ä£°åÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tmp;
	}
	
	public List<Template> listAllTemplate()throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Template> templateList=new ArrayList<Template>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSTEMPLATE_QUERY_ALL)); 
			rs = ps.executeQuery();
			while (rs.next()) {   
				Template tmp = new Template();
				tmp.setId(rs.getInt(1));
				tmp.setName(rs.getString(2));
				tmp.setJspTmp(rs.getString(3));
				tmp.setJsp(rs.getString(4));
				tmp.setRemark(rs.getString(5));
				tmp.setTmpType(rs.getString(6));
				tmp.setTypeId(rs.getInt(7));
				templateList.add(tmp);
			}
		} catch (Exception e) {
			logger.error("²éÑ¯Ä£°åÁÐ±íÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return templateList;
	}
	
	public void deleteTemplate(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null; 
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSTEMPLATE_DELETE));
			ps.setInt(1, id); 
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("É¾³ýÄ£°åÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void bindColumnTmp(ColumnTemplate columnTmp) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection ct = null; 
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into cms_ColumnTemp(ColumnTemp_id,Column_name,Column_id,column_type,tmp_id,tmp_name,tmp_jspTmp) values(cmsColumnTemp_SEQUENCE.nextval,?,?,?,?,?,?)");
			ps.setString(1, columnTmp.getColumnName());
			ps.setInt(2, columnTmp.getColumnId());
			ps.setString(3, columnTmp.getColumnType()); 
			ps.setInt(4, columnTmp.getTmpId());
			ps.setString(5, columnTmp.getTmpName());
			ps.setString(6, columnTmp.getTmpJspTmp());  
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("°ó¶¨À¸Ä¿Ä£°åÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void deleteColumnTmp(int id) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection ct = null; 
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete cms_ColumnTemp where ColumnTemp_id=?");
			ps.setInt(1, id); 
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("É¾³ýÀ¸Ä¿Ä£°åÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public ColumnTemplate getColumnTmp(int id) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ColumnTemplate tmp = new ColumnTemplate();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ColumnTemp_id,Column_name,Column_id,column_type,tmp_id,tmp_name,tmp_jspTmp from cms_ColumnTemp where ColumnTemp_id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {   
				tmp.setId(rs.getInt(1));
				tmp.setColumnName(rs.getString(2));
				tmp.setColumnId(rs.getInt(3));
				tmp.setColumnType(rs.getString(4));
				tmp.setTmpId(rs.getInt(5));
				tmp.setTmpName(rs.getString(6));
				tmp.setTmpJspTmp(rs.getString(7));
			}
		} catch (Exception e) {
			logger.error("²éÑ¯À¸Ä¿Ä£°åÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tmp;
	}
	public List<ColumnTemplate> listAllColumnTmp() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ColumnTemplate> templateList=new ArrayList<ColumnTemplate>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ColumnTemp_id,Column_name,Column_id,column_type,tmp_id,tmp_name,tmp_jspTmp from cms_ColumnTemp"); 
			rs = ps.executeQuery();
			while (rs.next()) {   
				ColumnTemplate tmp = new ColumnTemplate();
				tmp.setId(rs.getInt(1));
				tmp.setColumnName(rs.getString(2));
				tmp.setColumnId(rs.getInt(3));
				tmp.setColumnType(rs.getString(4));
				tmp.setTmpId(rs.getInt(5));
				tmp.setTmpName(rs.getString(6));
				tmp.setTmpJspTmp(rs.getString(7));
				templateList.add(tmp);
			}
		} catch (Exception e) {
			logger.error("²éÑ¯À¸Ä¿Ä£°åÁÐ±íÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return templateList; 
	}
	public void updateColumnTmp(ColumnTemplate columnTmp) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection ct = null; 
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update cms_ColumnTemp set tmp_id=?,tmp_name=?,tmp_jspTmp=?  where  ColumnTemp_id=?");
			ps.setInt(1, columnTmp.getTmpId()); 
			ps.setString(2, columnTmp.getTmpName());
			ps.setString(3, columnTmp.getTmpJspTmp());
			ps.setInt(4, columnTmp.getId()); 
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("ÐÞ¸ÄÀ¸Ä¿Ä£°åÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public List<ColumnTemplate> listColumnTmpByType(String type)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ColumnTemplate> templateList=new ArrayList<ColumnTemplate>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ColumnTemp_id,Column_name,Column_id,column_type,tmp_id,tmp_name,tmp_jspTmp from cms_ColumnTemp where column_type=?"); 
			ps.setString(1,type.toUpperCase()); 
			rs = ps.executeQuery();
			while (rs.next()) {   
				ColumnTemplate tmp = new ColumnTemplate();
				tmp.setId(rs.getInt(1));
				tmp.setColumnName(rs.getString(2));
				tmp.setColumnId(rs.getInt(3));
				tmp.setColumnType(rs.getString(4));
				tmp.setTmpId(rs.getInt(5));
				tmp.setTmpName(rs.getString(6));
				tmp.setTmpJspTmp(rs.getString(7));
				templateList.add(tmp);
			}
		} catch (Exception e) {
			logger.error("²éÑ¯À¸Ä¿Ä£°åÁÐ±íÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return templateList; 
	}
	
	
}
