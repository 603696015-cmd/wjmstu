package com.sopia.common.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

/**
 * 日志系统
 * 
 * @author Administrator
 * 
 */
public class ElLogger {
	private static final Log logger = LogFactory.getLog(ElLogger.class);
	public static String shortString(String something) throws ElException {
		if(something==null)
			return "无内容";
		else{
			if(something.length()>100){
				return something.substring(0,99)+"...";
			}else
				return something;
			
		}
	}
	public static void busilogger(int userid, int opmod, int optype,
			String opcontent, int opresult) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("insert into busilogs(userid,optime,optype,opmod,opcontent,opresult) values(?,?,?,?,?,?)");
//			ps.setInt(1, userid);
//			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
//			ps.setInt(3, optype);
//			ps.setInt(4, opmod);
//			ps.setString(5, opcontent);
//			ps.setInt(6, opresult);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("日志添加失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
		busilogger(userid, opmod, optype,opcontent, opresult,0);
	}
	/**
	 * 添加业务日志
	 * @param userid
	 * @param opmod
	 * @param optype
	 * @param opcontent
	 * @param opresult
	 * @param opid
	 * @throws ElException
	 */
	public static void busilogger(int userid, int opmod, int optype,
			String opcontent, int opresult,int opid) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("insert into busilogs(userid,optime,optype,opmod,opcontent,opresult,opid) values(?,?,?,?,?,?,?)");
//			ps.setInt(1, userid);
//			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
//			ps.setInt(3, optype);
//			ps.setInt(4, opmod);
//			ps.setString(5, opcontent);
//			ps.setInt(6, opresult);
//			ps.setInt(7, opid);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("日志添加失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
		busilogger(userid,opmod, optype,opcontent,opresult,opid,"");
	}
	/**
	 * 添加业务日志
	 * @param userid
	 * @param opmod
	 * @param optype
	 * @param opcontent
	 * @param opresult
	 * @param opid
	 * @throws ElException
	 */
	public static void busilogger(int userid, int opmod, int optype,
			String opcontent, int opresult,int opid,String content) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		content=opcontent;
		if(opcontent!=null&&opcontent.length()>1800){
			opcontent=opcontent.substring(0,1800)+"...";
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into busilogs(userid,optime,optype,opmod,opcontent,opresult,opid,content) values(?,?,?,?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, optype);
			ps.setInt(4, opmod);
			ps.setString(5, opcontent);
			ps.setInt(6, opresult);
			ps.setInt(7, opid);
			ps.setString(8, content);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("日志添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public static List<ElLog> busi_list(ELUser eu, ElLog log, int pbegin,
			int pend) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElLog> logs = new ArrayList<ElLog>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t1.*,rownum rn from( select b.id bid,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name ,b.optime,b.optype,b.opmod,b.opcontent,b.opresult "
					+ "from busilogs b left join eluser eu on b.userid = eu.id left join department dep on dep.id = eu.depid ";
			if (eu != null || log != null)
				sql = sql + "where ";
			if (eu != null) {
				String username = eu.getUsername() != null ? eu.getUsername()
						.trim() : "";
				String realname = eu.getRealname() != null ? eu.getRealname()
						.trim() : "";
				sql = sql + "eu.username like '%" + username
						+ "%' and eu.realname like '%" + realname + "%' ";
			}
			if (log != null) {
				if (log.getOpmod() != 0)
					sql = sql + "and b.opmod= " + log.getOpmod() + " ";
				if (log.getQuerybtime() != null)
					sql = sql + "and b.optime >to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(log.getQuerybtime())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (log.getQueryetime() != null)
					sql = sql + "and b.optime <=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(log.getQueryetime())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}
			ps = ct.prepareStatement(sql + " order by b.optime desc)t1 where rownum <=" + pbegin
					+ ") where rn >=" + pend);
			rs = ps.executeQuery();
			while (rs.next()) {

				ElLog b = new ElLog();
				b.setId(rs.getInt(1));
				ELUser eu1 = new ELUser(rs.getInt(2), rs.getString(4));
				eu1.setUsername(rs.getString(3));
				eu1
						.setDepartment(new Department(rs.getInt(5), rs
								.getString(6)));
				b.setUser(eu1);
				b.setOptime(rs.getTimestamp(7));
				b.setOptype(rs.getInt(8));
				b.setOpmod(rs.getInt(9));
				b.setOpcontent(rs.getString(10));
				b.setOpresult(rs.getInt(11));
				logs.add(b);
			}
		} catch (Exception e) {
			logger.error("日志添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return logs;
	}

	public static int busi_listsize(ELUser eu, ElLog log) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int b = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = " select count( b.id) "
					+ "from busilogs b left join eluser eu on b.userid = eu.id left join department dep on dep.id = eu.depid ";
			if (eu != null || log != null)
				sql = sql + "where ";
			if (eu != null) {
				String username = eu.getUsername() != null ? eu.getUsername()
						.trim() : "";
				String realname = eu.getRealname() != null ? eu.getRealname()
						.trim() : "";
				sql = sql + "eu.username like '%" + username
						+ "%' and eu.realname like '%" + realname + "%' ";
			}
			if (log != null) {
				if (log.getOpmod() != 0)
					sql = sql + "and b.opmod= " + log.getOpmod() + " ";
				if (log.getQuerybtime() != null)
					sql = sql + "and b.optime >to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(log.getQuerybtime())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (log.getQueryetime() != null)
					sql = sql + "and b.optime <=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(log.getQueryetime())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				b = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("日志添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	public static void syslogger(int userid, int opmod, int optype,
			String opcontent) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into syslogs(userid,optime,optype,opmod,opcontent ) values(?,?,?,?,? )");
			ps.setInt(1, userid);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, optype);
			ps.setInt(4, opmod);
			ps.setString(5, opcontent);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("日志添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public static List<ElLog> sys_list(ELUser eu, ElLog log, int pbegin,
			int pend) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElLog> logs = new ArrayList<ElLog>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t1.*,rownum rn from( select b.id bid,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name ,b.optime,b.optype,b.opmod,b.opcontent "
					+ "from syslogs b left join eluser eu on b.userid = eu.id left join department dep on dep.id = eu.depid ";
			if (eu != null || log != null)
				sql = sql + "where ";
			if (eu != null) {
				String username = eu.getUsername() != null ? eu.getUsername()
						.trim() : "";
				String realname = eu.getRealname() != null ? eu.getRealname()
						.trim() : "";
				sql = sql + "eu.username like '%" + username
						+ "%' and eu.realname like '%" + realname + "%' ";
			}
			if (log != null) {
				if (log.getOpmod() != 0)
					sql = sql + "and b.opmod= " + log.getOpmod() + " ";
				if (log.getQuerybtime() != null)
					sql = sql
							+ "and b.optime >to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(log.getQuerybtime())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (log.getQueryetime() != null)
					sql = sql + "and b.optime <=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(log.getQueryetime())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
			}
			ps = ct.prepareStatement(sql + " order by b.optime desc)t1 where rownum <=" + pbegin
					+ ") where rn >=" + pend);
			rs = ps.executeQuery();
			while (rs.next()) {

				ElLog b = new ElLog();
				b.setId(rs.getInt(1));
				ELUser eu1 = new ELUser(rs.getInt(2), rs.getString(4));
				eu1.setUsername(rs.getString(3));
				eu1
						.setDepartment(new Department(rs.getInt(5), rs
								.getString(6)));
				b.setUser(eu1);
				b.setOptime(rs.getTimestamp(7));
				b.setOptype(rs.getInt(8));
				b.setOpmod(rs.getInt(9));
				b.setOpcontent(rs.getString(10));
				logs.add(b);
			}
		} catch (Exception e) {
			logger.error("日志添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return logs;
	}

	public static int sys_listsize(ELUser eu, ElLog log) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int b = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = " select count( b.id) "
					+ "from syslogs b left join eluser eu on b.userid = eu.id left join department dep on dep.id = eu.depid ";
			if (eu != null || log != null)
				sql = sql + "where ";
			if (eu != null) {
				String username = eu.getUsername() != null ? eu.getUsername()
						.trim() : "";
				String realname = eu.getRealname() != null ? eu.getRealname()
						.trim() : "";
				sql = sql + "eu.username like '%" + username
						+ "%' and eu.realname like '%" + realname + "%' ";
			}
			if (log != null) {
				if (log.getOpmod() != 0)
					sql = sql + "and b.opmod= " + log.getOpmod() + " ";
				if (log.getQuerybtime() != null)
					sql = sql + "and b.optime >to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(log.getQuerybtime())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (log.getQueryetime() != null)
					sql = sql + "and b.optime <=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(log.getQueryetime())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
			}
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				b = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("日志添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}
	
	/**
	 * 根据条件获取系统日志
	 * @param opmod
	 * @param optype
	 * @param opcontent
	 * @return
	 * @throws ElException
	 */
	public ELUser getSyslogInUser(int opmod, String optype, String opcontent) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ELUser elUser=new ELUser();
		try {
			ct = DBConnection.getConnection();
			//查出申请操作或者修改操作的系统日志
			ps = ct.prepareStatement("select bl.id,bl.userid,eu.username,eu.realname from busilogs bl  left join eluser eu on bl.userid=eu.id where opmod=? and optype in ("+optype+") and opcontent=? order by optime desc");
			ps.setInt(1, opmod);
			//ps.setString(2, optype);
			ps.setString(2, opcontent);
			rs=ps.executeQuery();
			if(rs.next()){
				elUser.setId(rs.getInt("userid"));
				elUser.setUsername(rs.getString("username"));
				elUser.setRealname(rs.getString("realname"));
			}
		} catch (Exception e) {
			logger.error("根据条件获取系统日志失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUser;
	}
	
	/**
	 * 根据条件获取系统日志
	 * @param opmod
	 * @param optype
	 * @param opcontent
	 * @return
	 * @throws ElException
	 */
	public ELUser getSyslogInUser(int opmod, int optype, String opcontent) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ELUser elUser=new ELUser();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select bl.id,bl.userid,eu.username,eu.realname from busilogs bl  left join eluser eu on bl.userid=eu.id where opmod=? and optype=? and opcontent=? order by optime desc");
			ps.setInt(1, opmod);
			ps.setInt(2, optype);
			ps.setString(3, opcontent);
			rs=ps.executeQuery();
			if(rs.next()){
				elUser.setId(rs.getInt("userid"));
				elUser.setUsername(rs.getString("username"));
				elUser.setRealname(rs.getString("realname"));
			}
		} catch (Exception e) {
			logger.error("根据条件获取系统日志失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUser;
	}
	/**
	 * 根据id获取日志详细信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public static ElLog getBusiInfoById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElLog b = new ElLog();
		try {
			ct = DBConnection.getConnection();
			//查出申请操作或者修改操作的系统日志
			ps = ct.prepareStatement("select b.id bid,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name ,b.optime,b.optype,b.opmod,b.opcontent,b.opresult,b.content " +
					" from busilogs b left join eluser eu on b.userid=eu.id left join department dep on dep.id = eu.depid where b.id=? order by optime desc");
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				b.setId(rs.getInt(1));
				ELUser eu1 = new ELUser(rs.getInt(2), rs.getString(4));
				eu1.setUsername(rs.getString(3));
				eu1.setDepartment(new Department(rs.getInt(5), rs.getString(6)));
				b.setUser(eu1);
				b.setOptime(rs.getTimestamp(7));
				b.setOptype(rs.getInt(8));
				b.setOpmod(rs.getInt(9));
				b.setOpcontent(rs.getString(10));
				b.setOpresult(rs.getInt(11));
				b.setContent(rs.getString(12));
			}
		} catch (Exception e) {
			logger.error("根据id获取日志详细信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}
}
