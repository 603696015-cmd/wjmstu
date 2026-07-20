package com.sopia.assistman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.assistman.OfflineContants;
import com.sopia.assistman.dao.OfflineDao;
import com.sopia.assistman.entities.Offline;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.duman.entities.ELUser;

public class OfflineDaoImpl implements OfflineDao {
	private static final Log logger = LogFactory.getLog(OfflineDaoImpl.class);

	public int addOffline(Offline offline) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.OFFLINE_ADD));
			ps.setString(1, offline.getName());
			ps.setString(2, offline.getDescription());
			ps.setInt(3, offline.getDuring());
			ps.setInt(4, offline.getXueshi());
			ps.setInt(5, offline.getScore());
			ps.setTimestamp(6, offline.getBegintime());
			ps.setTimestamp(7, offline.getEndtime());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('eloffline') AS id");
				rs = ps.executeQuery();
			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			}else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select eloffline_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) { 
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void alterOffline(Offline offline) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.OFFLINE_ALTER));
			ps.setString(1, offline.getName());
			ps.setString(2, offline.getDescription());
			ps.setInt(3, offline.getDuring());
			ps.setInt(4, offline.getXueshi());
			ps.setInt(5, offline.getScore());
			ps.setTimestamp(6, offline.getBegintime());
			ps.setTimestamp(7, offline.getEndtime());
			ps.setInt(8, offline.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void deleteOffline(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.OFFLINE_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void addOffline2User(int userid, int offid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.OFFLINE_USER_ADD));
			ps.setInt(1, userid);
			ps.setInt(2, offid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public boolean checkOffline2AllUser(int userid, int offid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.oFFLINE_USER_CHECK));
			ps.setInt(1, userid);
			ps.setInt(2, offid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteOffline2AllUser(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.OFFLINE_USER_DELETE_ALL));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void deleteOffline2User(int userid, int offid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.OFFLINE_USER_DELETE));
			ps.setInt(1, userid);
			ps.setInt(2, offid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public List<ELUser> listOffline2Users(int offid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> list = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.OFFLINE_USER_LIST));
			ps.setInt(1, offid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser e = new ELUser(rs.getInt(1), rs.getString(2));
				e.setUsername(rs.getString(3));
				list.add(e);
			}
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public Offline getOffline(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Offline offline = new Offline();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.OFFLINE_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				offline.setId(rs.getInt(1));
				offline.setName(rs.getString(2));
				offline.setDescription(rs.getString(3));
				offline.setDuring(rs.getInt(4));
				offline.setXueshi(rs.getInt(5));
				offline.setScore(rs.getInt(6));
				offline.setBegintime(rs.getTimestamp(7));
				offline.setEndtime(rs.getTimestamp(8));
			}
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return offline;
	}

	public List<Offline> listOfflines(int pageB, int pageE) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Offline> offlines = new ArrayList<Offline>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.OFFLINE_LIST));
			ps.setInt(1, pageB);
			ps.setInt(2, pageE);
			rs = ps.executeQuery();
			while (rs.next()) {
				Offline offline = new Offline();
				offline.setId(rs.getInt(1));
				offline.setName(rs.getString(2));
				offline.setDescription(rs.getString(3));
				offline.setDuring(rs.getInt(4));
				offline.setXueshi(rs.getInt(5));
				offline.setScore(rs.getInt(6));
				offline.setBegintime(rs.getTimestamp(7));
				offline.setEndtime(rs.getTimestamp(8));
				offline.setUsercount(rs.getInt(9));
				offlines.add(offline);
			}
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return offlines;
	}

	public int listOfflinesSize() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(OfflineContants.OFFLINE_LIST_SIZE));
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

}
