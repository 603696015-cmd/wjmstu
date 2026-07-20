package com.sopia.userDemo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.duman.dao.impl.FuncDaoImpl;
import com.sopia.duman.entities.ElFunc;
import com.sopia.userDemo.UserDemoUtil;
import com.sopia.userDemo.dao.UserDemoDao;
import com.sopia.userDemo.entities.ELUserColumn;
import com.sopia.userDemo.entities.ELUserColumnJs;
import com.sopia.userDemo.entities.ELUserJs;
import com.sopia.userDemo.entities.ELUserColumnPage;
import com.sopia.userDemo.entities.ELUserPage;
import com.sun.star.container.ElementExistException;

public class UserDemoDaoImpl implements UserDemoDao {
	private static final Log logger = LogFactory.getLog(UserDemoDaoImpl.class);

	public List<ELUserColumn> listUserDemoColumns(String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUserColumn> columns = new ArrayList<ELUserColumn>();
		ELUserColumn column = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select type,column_name,description,show_add,show_update,show_view,show_register,show_user_update,show_user_view,show_list,column_type,format,show_page_type from " + tablename + "";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				column = new ELUserColumn(rs.getInt(1),rs.getString(2),rs.getString(3)==null?"":rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10),rs.getString(11),rs.getString(12)==null?"":rs.getString(12),rs.getInt(13));
				columns.add(column);
			}
		} catch (Exception e) {
			logger.error("用户表列信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return columns;
	}
	
	public List<ELUserColumn> getColumnsByPageid(int pageid)
		throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUserColumn> columns = new ArrayList<ELUserColumn>();
		ELUserColumn column = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select type,column_name,description,show_add,show_update,show_view,show_register,show_user_update,show_user_view,show_list,column_type,format,show_page_type from Userdemocolumn " + UserDemoUtil.getShowWhere(null, pageid);
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				column = new ELUserColumn(rs.getInt(1),rs.getString(2),rs.getString(3)==null?"":rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10),rs.getString(11),rs.getString(12)==null?"":rs.getString(12),rs.getInt(13));
				column.setElUserPages(this.getELUserPagesByPageid(pageid,column.getColumn_name()));
				column.setElUserJses(this.getELUserJsesByPageid(pageid,column.getColumn_name()));
				columns.add(column);
			}
		} catch (Exception e) {
			logger.error("用户表列信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return columns;
	}
	
	public List<ELUserPage> getELUserPagesByPageid(int pageid,String columnname) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUserPage> elUserPages = new ArrayList<ELUserPage>();
		ELUserPage elUserPage = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select pageid,column_name,range,default_select,canmodify,need from Eluser_page_type where pageid=? and column_name=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageid);
			ps.setString(2, columnname);
			rs = ps.executeQuery();
			while (rs.next()) {
				elUserPage = new ELUserPage(rs.getString(2),rs.getString(3)==null?"":rs.getString(3),rs.getString(4)==null?"":rs.getString(4),rs.getInt(5),rs.getInt(1),rs.getInt(6));
				elUserPages.add(elUserPage);
			}
		} catch (Exception e) {
			logger.error("根据pageid获取列字段范围设置！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUserPages;
	}
	
	public List<ELUserColumnJs> getELUserJsesByPageid(int pageid,String columnname) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUserColumnJs> elUserJses = new ArrayList<ELUserColumnJs>();
		ELUserColumnJs elUserJs = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select column_name,show_type,check_js_type from Eluser_js where show_type=? and column_name=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageid);
			ps.setString(2, columnname);
			rs = ps.executeQuery();
			while (rs.next()) {
				elUserJs = new ELUserColumnJs(rs.getString(1),rs.getInt(2),rs.getString(3) == null?"":rs.getString(3));
				elUserJses.add(elUserJs);
			}
		} catch (Exception e) {
			logger.error("根据pageid获取列字段JS设置！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUserJses;
	}

	public void updateUserDemoColumn(ELUserColumn co,String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " update " + tablename + " set show_add=?,show_update=?,show_view=?,show_register=?,show_user_update=?,show_user_view=? ,show_list=? where column_name=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, co.getShow_add());
			ps.setInt(2, co.getShow_update());
			ps.setInt(3, co.getShow_view());
			ps.setInt(4, co.getShow_register());
			ps.setInt(5, co.getShow_user_update());
			ps.setInt(6, co.getShow_user_view());
			ps.setInt(7, co.getShow_list());
			ps.setString(8, co.getColumn_name());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改字段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void insertUserDemoColumn(ELUserColumn co,String tablename,String user_tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " insert into "+tablename+" (type,column_name,description,show_add,show_update,show_view,show_register,show_user_update,show_user_view,show_list,column_type,format,show_page_type) " +
					"	values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, co.getType());
			ps.setString(2, co.getColumn_name());
			ps.setString(3, co.getDescription());
			ps.setInt(4, co.getShow_add());
			ps.setInt(5, co.getShow_update());
			ps.setInt(6, co.getShow_view());
			ps.setInt(7, co.getShow_view());
			ps.setInt(8, co.getShow_view());
			ps.setInt(9, co.getShow_view());
			ps.setInt(10, co.getShow_list());
			ps.setString(11, co.getColumn_type());
			ps.setString(12, co.getFormat());
			ps.setInt(13, co.getShow_page_type());
			ps.executeUpdate();
			
			//表中添加字段
			String sql_length = "";
			if(co.getColumn_type().equals("varchar2")){
				sql_length = "("+Integer.parseInt(co.getFormat())+")";
			}else if(co.getColumn_type().equals("number")){
				
			}else if(co.getColumn_type().equals("date")){
				
			}
			sql = "alter table "+user_tablename+" add("+co.getColumn_name()+"  "+co.getColumn_type()+""+sql_length+")";
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加字段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public boolean checkColumnIsExist(String column,String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			sql = " select count(1) from " + tablename + " where column_name=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, column);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0){
					flag = true;
				}
			}
		} catch (Exception e) {
			logger.error("验证字段是否存在失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
		
	}

	public List<ELUserColumn> selectColumnsByShow(int show, String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUserColumn> columns = new ArrayList<ELUserColumn>();
		ELUserColumn column = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select type,column_name,description,show_add,show_update,show_view,show_register,show_user_update,show_user_view,show_list,column_type,format,show_page_type from " + tablename + " where " + UserDemoUtil.getShowWhere(null, show);
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				column = new ELUserColumn(rs.getInt(1),rs.getString(2),rs.getString(3)==null?"":rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10),rs.getString(11),rs.getString(12)==null?"":rs.getString(12),rs.getInt(13));
				columns.add(column);
			}
		} catch (Exception e) {
			logger.error("用户表列信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return columns;
	}

	public List<ELUserJs> listAllJsTypes(String jstable) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUserJs> types = new ArrayList<ELUserJs>();
		ELUserJs type = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select id,name,description,team,llength,rlength,llength1,rlength1,llength2,rlength2 from Eluser_js_type";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				type = new ELUserJs(rs.getInt(1),rs.getString(2),rs.getString(3)==null?"":rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10));
				types.add(type);
			}
		} catch (Exception e) {
			logger.error("js实体集失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return types;
	}
	

	public void insertUserColumnJs(ELUserColumnJs elUserJs, int show)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " insert into Eluser_js (column_name,show_type,check_js_type) values (?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, elUserJs.getColumn_name());
			ps.setInt(2, show);
			ps.setString(3, elUserJs.getCheck_js_type());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("字段添加JS验证失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void setPageType(ELUserPage pageType) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " insert into Eluser_page_type " +
					" (column_name,range,default_select,modify,pageid) values (?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, pageType.getColumn_name());
			ps.setString(2, pageType.getRange());
			ps.setString(3, pageType.getDefault_select());
			ps.setInt(4, pageType.getModify());
			ps.setInt(5, pageType.getPageid());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("给列设置范围失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<ELUserColumnPage> listELUserPage(String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUserColumnPage> infos = new ArrayList<ELUserColumnPage>();
		ELUserColumnPage info = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select pageid,jspname from Eluser_page_info order by pageid asc";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				info = new ELUserColumnPage(rs.getInt(1),rs.getString(2)==null?"":rs.getString(2));
				infos.add(info);
			}
		} catch (Exception e) {
			logger.error("获取页面信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return infos;
	}
	

	public void updateUpload(int pageid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " update Eluser_page_info set upload=? where pageid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, pageid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改上传属性失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}


	public int insertIntoELUser(Map<String, String> map, String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int id = 0;
		String sqlcolumns = "";
		String sqlvalues = "";
		for(String key:map.keySet()){
			sqlcolumns += key + ",";
			sqlvalues += "'" + map.get(key)  + "'" +  ",";
		}
		
		if(sqlcolumns!=null&&!sqlcolumns.equals("")){
			sqlcolumns = sqlcolumns.substring(0,sqlcolumns.lastIndexOf(","));
		}
		if(sqlvalues!=null&&!sqlvalues.equals("")){
			sqlvalues = sqlvalues.substring(0,sqlvalues.lastIndexOf(","));
		}
		try {
			ct = DBConnection.getConnection();
			sql = " insert into " + tablename + " ( " + sqlcolumns + " ) values ( " + sqlvalues + ") ";
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
			
			ps = ct.prepareStatement("select "+tablename+"_sequence.currval from dual");
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("插入用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

}
