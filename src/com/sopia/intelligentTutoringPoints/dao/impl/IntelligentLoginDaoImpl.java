package com.sopia.intelligentTutoringPoints.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.IntelligentSystemConfOp;
import com.sopia.intelligentTutoringPoints.dao.IntelligentLoginDao;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLogin;


public class IntelligentLoginDaoImpl implements IntelligentLoginDao {
	private static final Log logger = LogFactory.getLog(IntelligentLoginDaoImpl.class);

	
	public Timestamp getLastLoginTime(int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		Timestamp lastLoginTime = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select a.begintime from (select begintime from intelligent_login where userid=? and begintime<sysdate order by begintime desc ) a where rownum=1 ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if(rs.next()){
				lastLoginTime = rs.getTimestamp(1);
			}
		} catch (Exception e) {
			logger.error("最近一次登录时间失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lastLoginTime;
	}

	public void intelligentLoginOut(int userid,int loginId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call intelligent_loginout_pro(?,?)}");
			ps.setInt(1, userid);
			ps.setInt(2, loginId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("退出登录失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void checkUserIsExittime(int userid) throws ElException{
		//如果有没有记录到的，那么就让退出时间等于登录时间
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update intelligent_login set endtime=begintime where id in (select id from intelligent_login where endtime is null and userid=?)");
			ps.setInt(1, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("检测用户是否有退出时间没有记录到的出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public IntelligentLogin intelligentLogin(int userid, boolean notLogin3day,
			int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		IntelligentLogin login = null;
		//检测用户是否有退出时间没有记录到的
		this.checkUserIsExittime(userid);
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct
					.prepareCall("{call intelligent_login_pro(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, userid);
			cs.setInt(2, notLogin3day?1:0);
			cs.setInt(3, classid);
			cs.setDouble(4, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORELOGIN));
			cs.setDouble(5, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORELOGINPER));
			cs.setDouble(6, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORELOGINNOT3DAYPER));
			cs.registerOutParameter(7, oracle.jdbc.OracleTypes.NUMBER);
			cs.registerOutParameter(8, oracle.jdbc.OracleTypes.NUMBER);
			cs.execute();
			login = new IntelligentLogin();
			login.setLoginType(cs.getInt(7));
			login.setId(cs.getInt(8));
		} catch (Exception e) {
			logger.error("登录加分减分失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return login;
	}

	public IntelligentLogin getLoginInfoByLoginid(int loginid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		IntelligentLogin login = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select score from intelligent_login where id=?");
			ps.setInt(1, loginid);
			rs = ps.executeQuery();
			if(rs.next()){
				login = new IntelligentLogin();
				login.setLoginType(rs.getFloat(1)==-0.9f?-1:(rs.getFloat(1)==0.0?0:1));
			}
		} catch (Exception e) {
			logger.error("获取一条登录信息失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return login;
	}

}
