package com.sopia.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sopia.ElConstants;

/**
 * 数据库连接部分
 * @author Administrator
 *
 */
public class DBConnection {
	private ComboPooledDataSource cpds;
	private static ThreadLocal<DBConnection> conns = new ThreadLocal<DBConnection>();
	private static DBConnection instance;
	private static final Log logger = LogFactory.getLog(DBConnection.class);
	private Connection ct;
	
	public static  void setConnsNull(){
		DBConnection.conns=null;
		
	}
	public static boolean createInstance() {
		try {
			instance = new DBConnection();
		} catch (Exception e) {
			logger.warn("创建数据库连接失败！" + e);
			return false;
		}
		return true;
	}

	public synchronized void createDBcon() throws ElException {
		// Connection c = null ;
		try {
//			logger.info("现在的连接数:" + cpds.getNumConnections());
			DBConnection dbc = new DBConnection();
			Connection c = cpds.getConnection();
			dbc.setCt(c);
			conns.set(dbc);
		} catch (Exception e) {
			logger.error("创建数据库连接失败！", e);
		}
	}

	public void init(String path) throws Exception {
		cpds = new ComboPooledDataSource();
		Properties ps = new Properties();
		FileInputStream fis =new FileInputStream(path + "/"
				+ SystemConfOp.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE)
				+ "_database.properties");
		ps.load(fis);
		fis.close();
		cpds.setUser(ps.getProperty("user"));
		cpds.setPassword(ps.getProperty("password"));
		cpds
				.setAcquireIncrement(Integer.parseInt(ps
						.getProperty("acquireIncrement")));
		cpds.setMaxPoolSize(Integer.parseInt(ps.getProperty("maxPoolSize")));
		cpds.setMinPoolSize(Integer.parseInt(ps.getProperty("minPoolSize")));
		cpds.setDriverClass(ps.getProperty("driverClass"));
		cpds.setJdbcUrl(ps.getProperty("jdbcUrl"));
		cpds.setCheckoutTimeout(Integer.parseInt(ps.getProperty("checkouttimeout")));
		cpds.setIdleConnectionTestPeriod(Integer.parseInt(ps
				.getProperty("idleconnectiontestperiod")));
		cpds.setMaxIdleTime(Integer.parseInt(ps.getProperty("maxidletime")));
		cpds.setInitialPoolSize(Integer.parseInt(ps.getProperty("initialpoolsize")));
		cpds.setTestConnectionOnCheckin(Boolean.parseBoolean(ps.getProperty("testConnectionOnCheckin")));
	}

	public static Connection getConnection() throws Exception {
		DBConnection dbc = conns.get();
		if (null == dbc) {
			instance.createDBcon();
		}
		return conns.get().getCt();
	}

	public Connection getCt() throws ElException {

		return ct;
	}

	public static void closeRs(ResultSet rs) throws ElException {
		if (null != rs) {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {
				throw new ElException("关闭数据集合失败!", e);
			}
		}
	}

	public static void closeCt(Connection ct) throws ElException {
	}

	public static void closePs(PreparedStatement ps) throws ElException {
		if (null != ps) {
			try {
				ps.close();
				ps = null;
			} catch (Exception e) {
				throw new ElException("关闭语句连接失败!", e);
			}
		}
	}

	public static DBConnection getInstance() {
		return instance;
	}

	public static boolean startDatabase(String path) {
		try {
			if (createInstance()) {
				getInstance().init(path);
			}
		} catch (Exception e) {
			logger.error("启动数据库失败！", e);
		}
		return true;
	}

	public synchronized static void closeConnectInfo(Connection ct,
			PreparedStatement ps, ResultSet rs) throws ElException {
		//closeRs(rs);
		//closePs(ps);
//		logger.error("关闭ps 和 rs") ;
//		closeCt(ct);
		   try {  
	            if(rs != null) {  
	                rs.close();  
	            }  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if(ps != null) {  
	                	ps.close();  
	                }  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            } finally {  
	            	
	            }  
	        }  
	}
	

	public void setCt(Connection ct) {
		this.ct = ct;
	}
	public static void setNull(){
		conns.set(null);
	}
	/**连接池数量
	 * @return
	 */
	public static int poolsize(){
		try {
			return instance.cpds.getNumConnections();
		} catch (Exception e) {
			logger.error("获取连接池数量失败！",e);
		}
		return 0;
	}
	/**繁忙连接数
	 * @return
	 */
	public static int poolbusysize(){
		try {
			return instance.cpds.getNumBusyConnections();
		} catch (Exception e) {
			logger.error("获取连接池繁忙连接数失败！",e);
		}
		return 0;
	}
	/**空闲连接数
	 * @return
	 */
	public static int poolidlesize(){
		try {
			return instance.cpds.getNumIdleConnections();
		} catch (Exception e) {
			logger.error("获取连接池空闲连接数失败！",e);
		}
		return 0;
	}
	/**帮助
	 * @return
	 */
	public static int poolHTsize(){
		try {
			return instance.cpds.getNumHelperThreads();
		} catch (Exception e) {
			logger.error("获取连接池数量失败！",e);
		}
		return 0;
	}
}
