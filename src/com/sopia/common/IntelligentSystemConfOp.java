package com.sopia.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;

public class IntelligentSystemConfOp {
	
	private static final Log logger = LogFactory.getLog(IntelligentSystemConfOp.class);
	private static String path = "";
	private static Map<String, String> intelligentSystemconfig;
	
	public static String getValue(String key){
		if(!intelligentSystemconfig.containsKey(key)) return "无记录";
		return intelligentSystemconfig.get(key);
	}
	public static int getIntValue(String key){
		if(!intelligentSystemconfig.containsKey(key)) return 0 ;
		return new Integer(intelligentSystemconfig.get(key));
	}
	public static double getDoubleValue(String key){
		if(!intelligentSystemconfig.containsKey(key)) return new Double(0.00) ;
		return new Double(intelligentSystemconfig.get(key));
	}
	public static boolean getBooleanValue(String key){
		if(!intelligentSystemconfig.containsKey(key)) return false ;
		return  intelligentSystemconfig.get(key).equals("true")? true:false;
	}
	public static void setPath(String path) {
		IntelligentSystemConfOp.path = path;
	}
	@SuppressWarnings("unchecked")
	public static void load() throws Exception{
		Properties ps = new Properties();
		FileInputStream fis=new FileInputStream(path+"/intelligentSystemconf.properties");
		ps.load(fis);
		fis.close();
		Enumeration enum1 = ps.propertyNames();
		intelligentSystemconfig = new HashMap<String, String>();
		while (enum1.hasMoreElements()) {
			String key = (String) enum1.nextElement();
			String value = ps.getProperty(key);
			intelligentSystemconfig.put(key, value);
		}
	}
	public static void setProperty(String id,int value)throws ElException{
		try {
			Properties per= new Properties();
			FileInputStream fis=new FileInputStream(path+"/intelligentSystemconf.properties");
			per.load(fis);
			fis.close();
			per.put(id, value+"");
			FileOutputStream fos=new FileOutputStream(path+"/intelligentSystemconf.properties");
			per.store(fos, "");
			fos.close();
			load();
		} catch (Exception e) {
			logger.error ("智能辅导分设置失败",e);
			throw  new ElException( "智能辅导分设置失败",e);
		}
	}
	public static void setProperty(String id,double value)throws ElException{
		try {
			Properties per= new Properties();
			FileInputStream fis=new FileInputStream(path+"/intelligentSystemconf.properties");
			per.load(fis);
			fis.close();
			per.put(id, value+"");
			FileOutputStream fos=new FileOutputStream(path+"/intelligentSystemconf.properties");
			per.store(fos, "");
			fos.close();
			load();
		} catch (Exception e) {
			logger.error ("智能辅导分设置失败",e);
			throw  new ElException( "智能辅导分设置失败",e);
		}
	}
	public static void setProperty(String id,boolean value)throws ElException{
		try {
			Properties per= new Properties();
			FileInputStream fis=new FileInputStream(path+"/intelligentSystemconf.properties");
			per.load(fis);
			fis.close();
			per.put(id, value+"");
			FileOutputStream fos=new FileOutputStream(path+"/intelligentSystemconf.properties");
			per.store(fos, "");
			fos.close();
			load();
		} catch (Exception e) {
			logger.error ("智能辅导分设置失败",e);
			throw  new ElException( "智能辅导分设置失败",e);
		}
	}
	public static void setProperty(String id,String value)throws ElException{
		try {
			Properties per= new Properties();
			FileInputStream fis=new FileInputStream(path+"/intelligentSystemconf.properties");
			per.load(fis);
			fis.close();
			per.put(id, value);
			FileOutputStream fos=new FileOutputStream(path+"/intelligentSystemconf.properties");
			per.store(fos, "");
			fos.close();
			load();
		} catch (Exception e) {
			logger.error ("智能辅导分设置失败",e);
			throw  new ElException( "智能辅导分设置失败",e);
		}
	}
	
	public static String getHttpsPath(String serverName,String contextPath){
		String httpsPath="https://"+ serverName + ":"+getValue(ElConstants.SYSTEM_CONF_HTTPS_PORT) + contextPath + "/";
		return httpsPath;
	}
	
	public static String getHttpPath(String serverName,String contextPath){
		String httpsPath="http://"+ serverName + ":"+getValue(ElConstants.SYSTEM_CONF_HTTP_PORT) + contextPath + "/";
		return httpsPath;
	}

}
