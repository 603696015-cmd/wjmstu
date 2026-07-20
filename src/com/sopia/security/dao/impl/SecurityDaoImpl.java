package com.sopia.security.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.security.dao.SecurityDao;
import com.sopia.security.entity.SecurityBindIp;

public class SecurityDaoImpl implements SecurityDao{
	private static final Log logger = LogFactory
	.getLog(SecurityDaoImpl.class);

	public SecurityBindIp getSecurityBindIpByRoleid(int roleid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		SecurityBindIp securityBindIp = null ;
		try {
			ct = DBConnection.getConnection();

			sql = "select * from user_bind where roleid = " + roleid;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				securityBindIp = new SecurityBindIp(rs.getInt("roleid"));
				securityBindIp.setId(rs.getInt("id"));
				securityBindIp.setIs_bind(rs.getInt("is_bind"));
				securityBindIp.setIp_start(rs.getString("ip_start"));
				securityBindIp.setIp_end(rs.getString("ip_end"));
			}
		} catch (Exception e) {
			logger.error("根据roleid获取ip绑定出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return securityBindIp;
	}

	public void inserSecurityBindIpByUser(int roleid,
			SecurityBindIp securityBindIp) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = " insert into user_bind (roleid,is_bind,ip_start,ip_end) " +
					" values (?,?,?,?)" ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roleid);
			ps.setInt(2, securityBindIp.getIs_bind());
			ps.setString(3, securityBindIp.getIp_start()==null?"":securityBindIp.getIp_start());
			ps.setString(4, securityBindIp.getIp_end()==null?"":securityBindIp.getIp_end());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据userid添加用户ip绑定出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void updateSecurityBindIpByUser(int roleid,
			SecurityBindIp securityBindIp) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = " update user_bind set is_bind=?,ip_start=?,ip_end=? where roleid = ?"  ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, securityBindIp.getIs_bind());
			if(securityBindIp.getIs_bind() == 1){
				ps.setString(2, securityBindIp.getIp_start()==null?"":securityBindIp.getIp_start());
				ps.setString(3, securityBindIp.getIp_end()==null?"":securityBindIp.getIp_end());
			}else if(securityBindIp.getIs_bind() == 0){
				ps.setString(2, "");
				ps.setString(3, "");
			}
			ps.setInt(4, roleid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据userid添加用户ip绑定出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

}
