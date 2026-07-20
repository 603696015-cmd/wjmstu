package com.sopia.schedule.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.MD5;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.entities.Department;
import com.sopia.lable.common.LableCommon;
import com.sopia.lable.dao.CustomLableDao;
import com.sopia.lable.entites.CirculationListLable;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.Table;
import com.sopia.lable.entites.TableField;
import com.sopia.schedule.CommonCustom;
import com.sopia.schedule.ScheduleUtil;
import com.sopia.schedule.dao.CustomReportsDao;
import com.sopia.schedule.entities.Client;
import com.sopia.schedule.entities.Clientlinkman;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.Contactstuff;
import com.sopia.schedule.entities.CustomReport;
import com.sopia.schedule.entities.CustomReportJSZ;
import com.sopia.schedule.entities.Tags;

/**
 * 自定义报表管理
 * @author Administrator
 *
 */
public class CustomReportsAction extends BaseAction{
	private CustomReportsDao customReportsDao;
	private CustomReport customReport;
	private List<CustomReport> customReports;
	private int count;
	private int id;
	private  CustomLableDao 			customLableDao;
	private  List<Table> 				usertableList;//自定义表信息集合
	private  List<Table>				lableInTableList;//自定义标签中的表信息
	
	
	private  String resultPage;//返回页面  ***.jsp
	private Department depTree;
	private Department department;
	private int sub_department;
	private  DepartmentDao departmentDao;
	List<Map<String, String>> list_designe = new ArrayList<Map<String, String>>();
	private File st;
	private String stFileName;
	
	private String actionName;
	private String tablename;
	
	private List<CustomReportJSZ> customReportJSZList;
	
	private Map<String,String> orderColumnnameMap;//排序列
	private String orderColumnname;
	private String orderColumnname_type;
	private Map<String,String> searchMap;//搜索标签
	
	
	public String addCustomReportInit() throws ElException{
		return "addCustomReportInitSuccess";
	}
	
	public String addCustomReport() throws ElException{
		int returnId = 0;
		if(customReport != null){
			returnId = customReportsDao.addCustomReport(customReport);
			customReport.setId(returnId);
		}
		
		customReport = customReportsDao.queryCustomReportById(returnId);
		
		return "addCustomReport_success";
	}
	
	
	/**
	 * 自定义报表标签List
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String customReportList() throws ElException, UnsupportedEncodingException{
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		customReports = customReportsDao.listCustomReports(getPageNow(),getPageSize());
		count = customReportsDao.listCustomReportsSize();
		return "customReportList";
	}

	public String queryCustomReportById() throws ElException{
		customReport = customReportsDao.queryCustomReportById(customReport.getId());
		usertableList=customLableDao.lable_getusertable();
		lableInTableList = customLableDao.lable_getlabletableandfield(customReport.getName(),"customreport");
		return "queryCustomReportById_success";
	}
	
	/**
	 * 添加标签是否显示左树
	 * @return
	 * @throws ElException
	 */
	public String updateCustomReportBytree() throws ElException{
		customReportsDao.updateCustomReportByTree(customReport);
		customReport = customReportsDao.queryCustomReportById(customReport.getId());
		usertableList=customLableDao.lable_getusertable();
		lableInTableList = customLableDao.lable_getlabletableandfield(customReport.getName(),"customreport");
		return "updateCustomReportBytree_success";
	}
	
	/**
	 * 添加标签是否显示搜索
	 * @return
	 * @throws ElException
	 */
	public String setover() throws ElException{
		customReportsDao.updateCustomReportBySearch(customReport);
		return "setover_success";
	}
	
	/**
	 * 添加循环标签sql条件
	 * @return
	 * @throws ElException 
	 */
	public String updateCustomReportById() throws ElException{
		customReportsDao.updateCustomReportById(customReport);
		customReport = customReportsDao.queryCustomReportById(customReport.getId());
		List<CustomReportJSZ> customReportJSZList = customReportsDao.queryCustomReport_jisuanzu_list_byid(customReport.getId());//计算组
		if(customReport.getGroupby() != null&&!customReport.getGroupby().equals("")){
			if(customReport.getGroupby().indexOf("desc")!=-1){
				customReport.setOrderstatus("desc");
				
			}else if(customReport.getGroupby().indexOf("asc")!=-1){
				customReport.setOrderstatus("asc");
			}
			if(customReport.getGroupby().indexOf(".")!=-1){
				//如果能找到“.”说明有排序语句
				//剔除排序字符，得到纯排序字段
				//将得到的排序字段分割“，”转化成数组
				String arr[] = LableCommon.lablecommon_delepaixu(customReport.getGroupby(),",").split(",");
				//用该数组得到字段中文名称等信息
				customReport.setField(customLableDao.lable_getTableFieldByField(arr));
				//将最后一个逗号用空格替换掉
				customReport.setGroupby(LableCommon.lablecommon_getorder(customReport.getGroupby(),","," "));
			}
		}
		if(customReport.getPageSize() == 0){
			customReport.setSql(ScheduleUtil.lablecommon_getsql(customReport.getTableinfo(),customReport.getTablefield(),
					customReport.getSqlcondition(),customReport.getGroupby(),customReport.getGroupby_(),customReport.getPageSize(),customReportJSZList));
		}else{
			customReport.setSql(ScheduleUtil.lablecommon_pagegetsql(customReport.getTableinfo(),customReport.getTablefield(),
					customReport.getSqlcondition(),customReport.getGroupby(),customReport.getGroupby_(),customReport.getPageSize(),customReportJSZList));
		}
		return "updateCustomReportById_success";
	}
	
	public String updateCustomReportFinal() throws ElException{
		customReportsDao.updateCustomReportFinal(customReport);
		customReport = customReportsDao.queryCustomReportById(customReport.getId());
		return "updateCustomReportFinal_success";
	}
	
	/**
	 * 自定义显示
	 * @return
	 * @throws ElException
	 */
	public String customReportZDYInit() throws ElException{
		//搜索map
		searchMap = new HashMap<String,String>();
		Enumeration params = getRequest().getParameterNames();
		String paramName = "";
		String[] paramvalue_array = null;
		while (params.hasMoreElements()) {
			paramName = (String) params.nextElement();
			paramvalue_array = getRequest().getParameterValues(paramName);
			String paramvalue = "";
			if(paramvalue_array != null){
				if(paramvalue_array.length>1){
					for(int i=0;i<paramvalue_array.length;i++){
						if(paramvalue_array[i]!=null&&!paramvalue_array[i].equals("")){
							paramvalue += paramvalue_array[i] + ",";
						}
					}
					if(paramvalue !=null&&!paramvalue.equals("")&&String.valueOf(paramvalue.charAt(paramvalue.length()-1)).equals(",")){
						paramvalue = paramvalue.substring(0,paramvalue.lastIndexOf(","));
						searchMap.put(paramName, paramvalue);
					}
				}else{
					if(paramvalue_array[0]!=null&&!paramvalue_array[0].equals("")){
						paramvalue = paramvalue_array[0];
						searchMap.put(paramName, paramvalue);
					}
				}
				
			}
		}
		
		
		//排序列orderColumnname
		orderColumnnameMap = new HashMap<String,String>();
		if(orderColumnname != null && !orderColumnname.equals("")){
			orderColumnnameMap.put("orderColumnname", orderColumnname);
			orderColumnnameMap.put("orderColumnname_type", orderColumnname_type);
		}
		
		actionName = "customReportZDYInit";
//		if(resultPage == null || resultPage.equals("")){
//			this.setElmessage("请查看设置参数resultPage!!!");
//			return "error";
//		}else{
//			String ext = J2EEFileUtil.getExtention(resultPage);
//			if (!ext.equals("jsp")) {
//				setElmessage("模板文件格式只能为jsp！");
//				return "error";
//			}
//		}
//		tablename = resultPage;//页面上面的标签
		//将***.jsp转换为加密码
//		String resultPage_MD5 = MD5.crypt(resultPage)+ ".jsp";
		// 验证是否已上传jsp文件
//		File f = new File(J2EEFileUtil.getRealPath("/")
//				+ CommonCustom.UPLOADURL + resultPage_MD5);
//		if (!f.exists()) {
//			this.setElmessage("对不起，您还未上传相应的模板，请先上传模板!!!");
//			return "error";
//		}
		
		//获取设置标签
		customReport = customReportsDao.queryCustomReportById(customReport.getId());
		
		customReportJSZList = customReportsDao.queryCustomReport_jisuanzu_list_byid(customReport.getId());
		
		if(customReport != null && customReport.getShowtree() == 1){//启用左树
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
						true);
			else {
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
						true);
			}

			if (department == null || department.getId() <= 0) {
				sub_department = 1;
				department = depTree;
			} else
				department = departmentDao.getDepById(department.getId());
		}
		
//		resultPage = resultPage_MD5;
		
		this.setPS(customReport.getPageSize());
		
		return "customReportZDYInit_success";
	}
	
	public String getJSZByLableid() throws ElException{
		customReportJSZList = customReportsDao.showzijisuan(customReport.getId(),0);
		return "getJSZByLableid_success";
	}
	
	public String uploadJspInit() throws ElException{
		customReport = customReportsDao.queryCustomReportById(customReport.getId());
		return "uploadJspInit_success";
	}
	
	public String uploadJsp() throws Exception{
		customReport = customReportsDao.queryCustomReportById(customReport.getId());
		if (st == null) {
			setElmessage("您还未选择文件！");
			return "uploadJspInit";
		}
		String ext = J2EEFileUtil.getExtention(stFileName);
		if (st.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "uploadJspInit";
		} else{
			if (!ext.equals("jsp")) {
				setElmessage("模板文件格式只能为jsp！");
				return "uploadJspInit";
			}
		}
		customReportsDao.updateCustomReportByResultPage(customReport,stFileName);
		
		J2EEFileUtil.upload_xianzhong(st, ext,
				CommonCustom.UPLOADURL, MD5.crypt(stFileName)
						+ "");
		
		return "uploadJsp_success";
	}
	
	//删除报表标签,连同计算组、上传的JSP一起删除
	public String deleteLableById() throws ElException, UnsupportedEncodingException{
		Map<String,String> map = customReportsDao.deleteLableById(customReport.getId());
		setElmessage(URLEncoder.encode(URLEncoder.encode("删除报表标签"+map.get("name")+"成功!!!", "UTF-8"), "UTF-8"));
		
//		//删除上传的JSP文件
//		if(map.get("resultpage") != null && !map.get("resultpage").equals("")){
//			File jspFile = new File(J2EEFileUtil.getRealPath("/") + CommonCustom.UPLOADURL + "/" + MD5.crypt(map.get("resultpage")) + ".jsp");
//			if (jspFile.exists()){
//				jspFile.delete();
//			}
//		}
		return "deleteLableById_success";
	}
	

	public String getResultPage() {
		return resultPage;
	}

	public void setResultPage(String resultPage) {
		this.resultPage = resultPage;
	}

	public CustomReportsDao getCustomReportsDao() {
		return customReportsDao;
	}

	public void setCustomReportsDao(CustomReportsDao customReportsDao) {
		this.customReportsDao = customReportsDao;
	}

	public CustomReport getCustomReport() {
		return customReport;
	}

	public void setCustomReport(CustomReport customReport) {
		this.customReport = customReport;
	}

	public List<CustomReport> getCustomReports() {
		return customReports;
	}

	public void setCustomReports(List<CustomReport> customReports) {
		this.customReports = customReports;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CustomLableDao getCustomLableDao() {
		return customLableDao;
	}

	public void setCustomLableDao(CustomLableDao customLableDao) {
		this.customLableDao = customLableDao;
	}

	public List<Table> getUsertableList() {
		return usertableList;
	}

	public void setUsertableList(List<Table> usertableList) {
		this.usertableList = usertableList;
	}

	public List<Table> getLableInTableList() {
		return lableInTableList;
	}

	public void setLableInTableList(List<Table> lableInTableList) {
		this.lableInTableList = lableInTableList;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public List<Map<String, String>> getList_designe() {
		return list_designe;
	}

	public void setList_designe(List<Map<String, String>> list_designe) {
		this.list_designe = list_designe;
	}

	public File getSt() {
		return st;
	}

	public void setSt(File st) {
		this.st = st;
	}

	public String getStFileName() {
		return stFileName;
	}

	public void setStFileName(String stFileName) {
		this.stFileName = stFileName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<CustomReportJSZ> getCustomReportJSZList() {
		return customReportJSZList;
	}

	public void setCustomReportJSZList(List<CustomReportJSZ> customReportJSZList) {
		this.customReportJSZList = customReportJSZList;
	}

	public Map<String, String> getOrderColumnnameMap() {
		return orderColumnnameMap;
	}

	public void setOrderColumnnameMap(Map<String, String> orderColumnnameMap) {
		this.orderColumnnameMap = orderColumnnameMap;
	}

	public String getOrderColumnname() {
		return orderColumnname;
	}

	public void setOrderColumnname(String orderColumnname) {
		this.orderColumnname = orderColumnname;
	}

	public String getOrderColumnname_type() {
		return orderColumnname_type;
	}

	public void setOrderColumnname_type(String orderColumnname_type) {
		this.orderColumnname_type = orderColumnname_type;
	}

	public Map<String, String> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<String, String> searchMap) {
		this.searchMap = searchMap;
	}





	
	

}
