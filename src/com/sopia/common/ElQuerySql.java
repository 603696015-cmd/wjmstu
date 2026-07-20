package com.sopia.common;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;

/**
 * 系统sql语句加载，调用类
 * @author Administrator
 *
 */
public class ElQuerySql {
	private static Map<String, String> sqls;
//	private static final Log logger = LogFactory.getLog(ElQuerySql.class);
	private static final Log logger = LogFactory.getLog(ElQuerySql.class);

//	static {
//		try {
//			load();
//		} catch (Exception e) {
//			logger.error("加载SQL语句出错！", e);
//		}
//	}

	@SuppressWarnings("unchecked")
	public  static void init(String path) throws Exception {
		Properties ps = new Properties();
		FileInputStream fis=new FileInputStream(path+"/"+SystemConfOp.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE)+"_querySqls.properties");
		ps.load(fis);
		fis.close();
		Enumeration enum1 = ps.propertyNames();
		sqls = new HashMap<String, String>();
		while (enum1.hasMoreElements()) {
			String key = (String) enum1.nextElement();
			String value = ps.getProperty(key);
			loadEach(value);
		}
	}
	@SuppressWarnings("unchecked")
	private static void loadEach(String source) throws Exception {
		Properties ps = new Properties();
//		String path = Thread.currentThread().getContextClassLoader()
//				.getResource(source)
//				.toURI().toString();new FileInputStream(path.substring(6)
		ps.load(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(source));
		Enumeration enum1 = ps.propertyNames();
		
		
		while (enum1.hasMoreElements()) {
			String key = (String) enum1.nextElement();
			String value = ps.getProperty(key);
			sqls.put(key, value);
		}
	}
	public static String getSQL(String key) throws Exception {
		if(sqls==null){
			init(ServletActionContext.getServletContext().getRealPath("/WEB-INF/config/"));
		}
		return sqls.get(key);
	}
}
