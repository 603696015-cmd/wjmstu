package com.sopia;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.StationDao;
import com.sopia.duman.dao.UserDao;

public class BaseAction {
	private static final Log logger = LogFactory.getLog(BaseAction.class);
	protected DepartmentDao departmentDao;
	protected UserDao userDao;
	protected StationDao stationDao;
	protected String elmessage;
	protected String elmessage2;
//	protected String htmlTitle;
	protected String x;
	private int pN;
	private int pS;
	protected int count;
	
	private int pN3;
	private int pS3;
	private int pN4;
	private int pS4;
	protected int count_news;
	protected int count_baoxianProduct;
	private int pN6;
	private int pS6;
	
	

	public int getPN6() {
		return pN6;
	}

	public void setPN6(int pn6) {
		pN6 = pn6;
	}

	public int getPS6() {
		return pS6 = pS6 == 0 ? 3 : pS6;
	}

	public void setPS6(int ps6) {
		pS6 = ps6;
	}

	public int getPN3() {
		return pN3;
	}

	public void setPN3(int pn3) {
		pN3 = pn3;
	}

	public int getPS3() {
		return pS3 = pS3 == 0 ? 6 : pS3;
	}

	public void setPS3(int ps3) {
		pS3 = ps3;
	}

	public int getPN4() {
		return pN4;
	}

	public void setPN4(int pn4) {
		pN4 = pn4;
	}

	public int getPS4() {
		return pS4 = pS4 == 0 ? 6 : pS4;
	}

	public void setPS4(int ps4) {
		pS4 = ps4;
	}

	public int getCount_news() {
		return count_news;
	}

	public void setCount_news(int count_news) {
		this.count_news = count_news;
	}

	public int getCount_baoxianProduct() {
		return count_baoxianProduct;
	}

	public void setCount_baoxianProduct(int count_baoxianProduct) {
		this.count_baoxianProduct = count_baoxianProduct;
	}

	public int getCount() {
		return count;
	}

	/*public String loginpki() {
		try {
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=utf-8");
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.print("test");
			out.flush();
			out.close();
		} catch (Exception e) {
		}

		return "test";
	}*/
	public String test() {
		try {
		} catch (Exception e) {
		}

		return null;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public HttpSession getSession() {
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return session;
	}
	
	/**
	 * 
	 * @Title: ajaxHead 
	 * @author dongke
	 * @date  2017年12月14日 下午2:12:07
	 * @Description: TODO(设置AJAXhead) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void ajaxHead(){
		HttpServletResponse resp=this.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
	}
	
	/**
	 * 正常
	 * @author dongke
	 */
	public void ajaxSuccess(Object obj,Object obj2){
		PrintWriter localPrintWriter;
		try {
			getResponse().setContentType("application/json;charset=utf-8");
			getResponse().setCharacterEncoding("UTF-8");
			localPrintWriter =this.getResponse().getWriter();
			Gson gson = new Gson(); 
			Map<String,Object> json = new HashMap<String,Object>();
			json.put("status", 200);
			json.put("data", obj);
			json.put("itemNum", obj2);
			System.out.println(gson.toJson(json));
			localPrintWriter.println(gson.toJson(json));
			localPrintWriter.flush();
			localPrintWriter.close();
		}catch(IOException e){
			
		}
	}
	
	/**
	 * 正常
	 * @author dongke
	 */
	public void ajaxSuccess(String obj,boolean flag){
		PrintWriter localPrintWriter;
		try {
			getResponse().setContentType("application/json;charset=utf-8");
			getResponse().setCharacterEncoding("UTF-8");
			localPrintWriter =this.getResponse().getWriter();
			Gson gson = new Gson(); 
			if(flag){
				Map<String,Object> json = new HashMap<String,Object>();
				json.put("status", 200);
				json.put("data", obj);
				localPrintWriter.println(gson.toJson(json));
			}else{
				localPrintWriter.println(obj);
			}
			localPrintWriter.flush();
			localPrintWriter.close();
		}catch(IOException e){
			
		}
	}
	
	/**
	 * 正常
	 * @author dongke
	 */
	public void ajaxSuccess(Object obj){
		PrintWriter localPrintWriter;
		try {
			getResponse().setContentType("application/json;charset=utf-8");
			getResponse().setCharacterEncoding("UTF-8");
			localPrintWriter =this.getResponse().getWriter();
			Gson gson = new Gson(); 
			Map<String,Object> json = new HashMap<String,Object>();
			json.put("status", 200);
			json.put("data", obj);
			localPrintWriter.println(gson.toJson(json));
			localPrintWriter.flush();
			localPrintWriter.close();
		}catch(IOException e){
			
		}
	}
	
	/**
	 * 
	 * @Title: ajaxError 
	 * @author dongke
	 * @date  2017年12月14日 下午2:16:50
	 * @Description: TODO(异步请求失败) 
	 * @param @param obj    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void ajaxError(Object obj){
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =this.getResponse().getWriter();
			Gson gson = new Gson();
			Map<String,Object> json = new HashMap<String,Object>();
			json.put("status", 400);
			json.put("error", obj);
			localPrintWriter.println(gson.toJson(json));
			localPrintWriter.flush();
			localPrintWriter.close();
		}catch(IOException e){
			
		}
	}
	
	

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	public void printMsg(String msg){
		try {
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter pw = getResponse().getWriter();
			pw.print(msg);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			logger.error("response 输出信息错误！",e);
		}
	}
	public int getSessionIntValue(String key) {
 		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		if(session.getAttribute(key)!=null){
			return (Integer) session.getAttribute(key);
		}
		return 0;
	}
	
	public static int getSessionIntValueStatic(String key) {//静态调用
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return (Integer) session.getAttribute(key);
	}

	public String getSessionValue(String key) {
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return (String) session.getAttribute(key);
	}
	public Map<String,Object> getApplication(){
		return ActionContext.getContext().getApplication();
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	

	public String getElmessage() {
		return elmessage;
	}

	public void setElmessage(String elmessage) {
		this.elmessage = elmessage;
	}

	public int getPN() {
		return pN;
	}

	public void setPN(int pn) {
		pN = pn;
	}

	public int getPS() {
		return pS = pS == 0 ? 10 : pS;
	}

	public void setPS(int ps) {
		pS = ps;
	}

	public int getPageSize() {
		int pageend = 0;
		if ("mssql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPN() * getPS() + getPS();
		} else if ("mysql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPS();
		} else if ("oracle".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPN() * getPS() + 1;
		}
		return pageend;
	}
	public int getPageSize3() {
		int pageend = 0;
		if ("mssql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPN3() * getPS3() + getPS3();
		} else if ("mysql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPS();
		} else if ("oracle".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPN3() * getPS3() + 1;
		}
//		System.out.println("pageSize" + pageend);
		return pageend;
	}
	public int getPageSize4() {
		int pageend = 0;
		if ("mssql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPN4() * getPS4() + getPS4();
		} else if ("mysql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPS();
		} else if ("oracle".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPN4() * getPS4() + 1;
		}
//		System.out.println("pageSize" + pageend);
		return pageend;
	}
	public int getPageSize6() {
		int pageend = 0;
		if ("mssql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPN6() * getPS6() + getPS6();
		} else if ("mysql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPS6();
		} else if ("oracle".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pageend = getPN6() * getPS6() + 1;
		}
//		System.out.println("pageSize" + pageend);
		return pageend;
	}

	public int getPageNow() {
		int pagebegin = 0;
		if ("mssql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN() * getPS() + 1;
		} else if ("mysql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN() * getPS();
		} else if ("oracle".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN() * getPS() + getPS();

		}
		return pagebegin;
	}
	public int getPageNow3() {
		int pagebegin = 0;
		if ("mssql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN3() * getPS3() + 1;
		} else if ("mysql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN3() * getPS3();
		} else if ("oracle".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN3() * getPS3() + getPS3();

		}
//		System.out.println("pageNow:" + pagebegin);
		return pagebegin;
	}
	public int getPageNow4() {
		int pagebegin = 0;
		if ("mssql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN4() * getPS4() + 1;
		} else if ("mysql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN4() * getPS4();
		} else if ("oracle".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN4() * getPS4() + getPS4();

		}
//		System.out.println("pageNow:" + pagebegin);
		return pagebegin;
	}
	public int getPageNow6() {
		int pagebegin = 0;
		if ("mssql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN6() * getPS6() + 1;
		} else if ("mysql".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN6() * getPS6();
		} else if ("oracle".equals(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			pagebegin = getPN6() * getPS6() + getPS6();

		}
//		System.out.println("pageNow:" + pagebegin);
		return pagebegin;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public StationDao getStationDao() {
		return stationDao;
	}

	public void setStationDao(StationDao stationDao) {
		this.stationDao = stationDao;
	}

	public String getElmessage2() {
		return elmessage2;
	}

	public void setElmessage2(String elmessage2) {
		this.elmessage2 = elmessage2;
	}
}
