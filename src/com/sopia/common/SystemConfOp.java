package com.sopia.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.cache.IBaseCache;
import com.sopia.duman.entities.Department;

/**
 * 系统配置处理�"
 * @author Administrator
 *
 */
public class SystemConfOp {
	private static final Log logger = LogFactory.getLog(SystemConfOp.class);
	private static String path = "";
	private static Map<String, String> systemconf;
	
	private static IBaseCache baseCache;
	/*static {
		try {
			 path = Thread.currentThread().getContextClassLoader()
			.getResource("com/sopia/common/configs/systemconf.properties")
			.toURI().toString();
			 path=path.substring(6);
			load();
		} catch (Exception e) {
			logger.error("加载系统设置失败�", e);
		}
	}*/
	
	public static IBaseCache getCache(){
		return baseCache;
	}
	
	public static void setBaseCache(IBaseCache cache){
		baseCache = cache;
	}
	
	public static String getValue(String key){
		if(!systemconf.containsKey(key)) return "无记录";
		return systemconf.get(key);
	}
	public static int getIntValue(String key){
		if(!systemconf.containsKey(key)) return 0 ;
		return new Integer(systemconf.get(key));
	}
	public static boolean getBooleanValue(String key){
		if(!systemconf.containsKey(key)) return false ;
		return  systemconf.get(key).equals("true")? true:false;
	}
	public static void setPath(String path) {
		SystemConfOp.path = path;
	}
	@SuppressWarnings("unchecked")
	public static void load() throws Exception{
		Properties ps = new Properties();
		FileInputStream fis=new FileInputStream(path+"/systemconf.properties");
		ps.load(fis);
		fis.close();
		Enumeration enum1 = ps.propertyNames();
		systemconf = new HashMap<String, String>();
		while (enum1.hasMoreElements()) {
			String key = (String) enum1.nextElement();
			String value = ps.getProperty(key);
			systemconf.put(key, value);
		}
	}
	public static void setProperty(String id,float value)throws ElException{
		try {
			Properties per= new Properties();
			per.load(new FileInputStream(path+"/systemconf.properties"));
			per.put(id, value+"");
			per.store(new FileOutputStream(path+"/systemconf.properties"), "");
			load();
		} catch (Exception e) {
			logger.error ("设置系统配置失败",e);
			throw  new ElException( "设置系统配置失败",e);
		}
	}
	public static void setProperty(String id,int value)throws ElException{
		try {
			Properties per= new Properties();
			FileInputStream fis=new FileInputStream(path+"/systemconf.properties");
			per.load(fis);
			fis.close();
			per.put(id, value+"");
			FileOutputStream fos=new FileOutputStream(path+"/systemconf.properties");
			per.store(fos, "");
			fos.close();
			load();
		} catch (Exception e) {
			logger.error ("设置系统配置失败",e);
			throw  new ElException( "设置系统配置失败",e);
		}
	}
	public static void setProperty(String id,boolean value)throws ElException{
		try {
			Properties per= new Properties();
			FileInputStream fis=new FileInputStream(path+"/systemconf.properties");
			per.load(fis);
			fis.close();
			per.put(id, value+"");
			FileOutputStream fos=new FileOutputStream(path+"/systemconf.properties");
			per.store(fos, "");
			fos.close();
			load();
		} catch (Exception e) {
			logger.error ("设置系统配置失败",e);
			throw  new ElException( "设置系统配置失败",e);
		}
	}
	public static void setProperty(String id,String value)throws ElException{
		try {
			Properties per= new Properties();
			FileInputStream fis=new FileInputStream(path+"/systemconf.properties");
			per.load(fis);
			fis.close();
			per.put(id, value);
			FileOutputStream fos=new FileOutputStream(path+"/systemconf.properties");
			per.store(fos, "");
			fos.close();
			load();
		} catch (Exception e) {
			logger.error ("设置系统配置失败",e);
			throw  new ElException( "设置系统配置失败",e);
		}
	}
	public static synchronized Department getSecondDep(int depid)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,parentid from department where id = ?");
			int id =  getParentDep(depid,1);
			ps.setInt(1,id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	private static int getParentDep(int depid,int stop)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int depid1= 0;
		try {
			if(depid==stop) return depid;
			ct = DBConnection.getConnection();
			ps = ct
				.prepareStatement("select id, parentid from department where id=? ");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				int depid2 = rs.getInt(2);
				if(depid2==0) return depid;
				if(depid2!=stop){
					depid1 = getParentDep(depid2, stop);
				}
				else
					depid1 = depid;
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return depid1;
	}
	public static String getPath() {
		return path;
	}
	public static String toStuffUrl(String str){
		if(str==null)
			return "";
		if(!"无记�".equals(SystemConfOp.getValue(ElConstants.STUFF_URL))
				&&!"".equals(SystemConfOp.getValue(ElConstants.STUFF_URL).trim())){
			if(str.indexOf("elstuffs")>=0)
			str = str.replaceAll("elstuffs",getStuffUrl()+"elstuffs") ;
			
		}
		return str;
	}
	public static String getStuffUrl( ){
		String str="";
		if(!"无记录".equals(SystemConfOp.getValue(ElConstants.STUFF_URL))
				&&!"".equals(SystemConfOp.getValue(ElConstants.STUFF_URL).trim())){
			String url =  SystemConfOp.getValue(ElConstants.STUFF_URL).trim();
			str = url.charAt(url.length()-1)=='/'?url:(url +"/") ;
			
		}
		return str;
	}
	public static String toStuffUrl2(String str){
		if(str==null)
			return "";
		if(!"无记录".equals(SystemConfOp.getValue(ElConstants.STUFF_URL))
				&&!"".equals(SystemConfOp.getValue(ElConstants.STUFF_URL).trim())){
			if(str.indexOf("files/question")>=0)
			str = str.replaceAll("files/question",getStuffUrl()+"files/question") ;
			
		}
		return str;
	}
	
	public static String getHttpsPath(String serverName,String contextPath){
		String httpsPath="https://"+ serverName + ":"+getValue(ElConstants.SYSTEM_CONF_HTTPS_PORT) + contextPath + "/";
		return httpsPath;
	}
	
	public static String getHttpPath(String serverName,String contextPath){
		String httpsPath="http://"+ serverName + ":"+getValue(ElConstants.SYSTEM_CONF_HTTP_PORT) + contextPath + "/";
		return httpsPath;
	}
	
	public static float getFloatValue(String key){ 
		if(!systemconf.containsKey(key)) return 0 ;  
		return Float.parseFloat(systemconf.get(key));
	}
}
