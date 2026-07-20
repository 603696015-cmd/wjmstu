package com.sopia.pfms.action;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.MD5;
import com.sopia.common.PfmsUtil;
import com.sopia.pfms.dao.BaoxianProductDao;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.entities.Area;
import com.sopia.pfms.entities.DamageMember;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.office.ExcelOutPut;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;

public class PfmsAction extends BaseAction {
	private IndexDao indexDao; 
	private ELUser elUser;
	private PfmsUser pfmsUser;
	private String newpassword;
	private String Question;
	private String Answer;
	private String note;
	private File imgFile;//上传文件
	private int id;

	private String imgFileFileName;//上传文件
	private String imgType;//上传文件类型
	
	private int showType;//1为系统管理中查看会员参数
	
	private String province;//省
	private String city;//市
	private String county;//县
	
	private String areaList;//省市县的json字符串
	private int depid;
	
	private String city_selected;
	private String city_type;
	
	private String oldPassword;
	private String check_json_result;//判断旧密码是否正确的返回值
	private int userId;
	
	private int number;//用来标示修改的是哪一个div
	
	private BaoxianProductDao baoxianProductDao;
	private int roleId;
	private int change_id;//修改保险产品id
	private String select_tuijian;//修改的值
	
	private String userids;
	
	public String getUserids() {
		return userids;
	}
	public void setUserids(String userids) {
		this.userids = userids;
	}
	public BaoxianProductDao getBaoxianProductDao() {
		return baoxianProductDao;
	}
	public void setBaoxianProductDao(BaoxianProductDao baoxianProductDao) {
		this.baoxianProductDao = baoxianProductDao;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getChange_id() {
		return change_id;
	}
	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}
	public String getSelect_tuijian() {
		return select_tuijian;
	}
	public void setSelect_tuijian(String select_tuijian) {
		this.select_tuijian = select_tuijian;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCheck_json_result() {
		return check_json_result;
	}
	public void setCheck_json_result(String check_json_result) {
		this.check_json_result = check_json_result;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getCity_type() {
		return city_type;
	}
	public void setCity_type(String city_type) {
		this.city_type = city_type;
	}
	public String getCity_selected() {
		return city_selected;
	}
	public void setCity_selected(String city_selected) {
		this.city_selected = city_selected;
	}
	public File getBatchImport() {
		return batchImport;
	}
	public void setBatchImport(File batchImport) {
		this.batchImport = batchImport;
	}
	public String getBatchImportFileName() {
		return batchImportFileName;
	}
	public void setBatchImportFileName(String batchImportFileName) {
		this.batchImportFileName = batchImportFileName;
	}
	public int getDepid() {
		return depid;
	}
	public void setDepid(int depid) {
		this.depid = depid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAreaList() {
		return areaList;
	}
	public void setAreaList(String areaList) {
		this.areaList = areaList;
	}
	public int getShowType() {
		return showType;
	}
	public void setShowType(int showType) {
		this.showType = showType;
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
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
	public boolean isExprot() {
		return exprot;
	}
	public void setExprot(boolean exprot) {
		this.exprot = exprot;
	}
	public List<ELUser> getElUsers() {
		return elUsers;
	}
	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}
	public List<ElRole> getRoles() {
		return roles;
	}
	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}
	public Department getDepTree() {
		return depTree;
	}
	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}
	public File getImgFile() {
		return imgFile;
	}
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	} 
	public String getImgType() {
		return imgType;
	}
	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	public String getImgFileFileName() {
		return imgFileFileName;
	}
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	
	public IndexDao getIndexDao() {
		return indexDao;
	}
	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	} 
	public String getQuestion() {
		return Question;
	}
	public void setQuestion(String question) {
		Question = question;
	}
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNote() {
		return note;
	}
	public PfmsUser getPfmsUser() {
		return pfmsUser;
	}
	public void setPfmsUser(PfmsUser pfmsUser) {
		this.pfmsUser = pfmsUser;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	/*
	 * 获取用户信息
	 */
	public String getBaseInfo() throws ElException { 
		String resultPage = "";
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		areaList = gson.toJson(indexDao.areaList(null,null));
		if(showType == 1){
			roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID));
			pfmsUser = indexDao.getUser(elUser.getId(),false);
			resultPage = "manage_success";
		}else{
			pfmsUser = indexDao.getUser(getSessionIntValue(ElConstants.SESSION_USERID),false);
			if(pfmsUser.getId() == 0){//当前用户不在会员表中
				this.setElmessage("对不起，您不是企业会员!!!");
				return "error";
			}
			resultPage = "success";
		}
		
		
		return resultPage;
	}
	
	/*
	 * 修改用户信息
	 */ 
	public String alterBaseInfo() throws ElException { 
		pfmsUser.setProvince_city_county(new PfmsUtil().toProvince_city_county(province, city, county));
		indexDao.alterBaseInfo(pfmsUser,pfmsUser.getId());
		if(showType == 1){
		}else{
			return "personal_success";
		}
		
		return "success";
	}
	
	public String alterUserInfo() throws ElException{
		pfmsUser.setProvince_city_county(new PfmsUtil().toProvince_city_county(province, city, county));
		indexDao.alterBaseInfo(pfmsUser,pfmsUser.getId());
		return "alterUserInfo";
	}
	
	/*
	 * 修改密码
	 */
	public String alterPassword() throws ElException{
		indexDao.alterPassword(newpassword,pfmsUser.getId());
		if(showType == 1){
		}else{
			return "personal_success";
		}
		
		return "success";
	}
	
	
	/*
	 * 修改会员简介
	 */
	public String alterMemberProfile() throws ElException{
		indexDao.alterMemberProfile(pfmsUser,pfmsUser.getId());
		if(showType == 1){
		}else{
			return "personal_success";
		}
		return "success";
	}
	
	public String queryBaseInfo() throws ElException{
		String resultPage = "";
		if(showType == 1){
			pfmsUser = indexDao.getUser(elUser.getId(),false);
			resultPage = "manage_success";
		}else{
			pfmsUser = indexDao.getUser(getSessionIntValue(ElConstants.SESSION_USERID),false);
			if(pfmsUser.getId() == 0){//当前用户不在会员表中
				this.setElmessage("对不起，您不是企业会员!!!");
				return "error";
			}
			resultPage = "success";
		}
		return resultPage;
	}
	
	public String queryUserInfo() throws ElException{
		pfmsUser = indexDao.getUser(elUser.getId(),false);
		return "queryUserInfo";
	}
	
	
	/*
	 * 相关证件的上传
	 */
	public String uploadFile() throws Exception{ 
		if(imgFile == null){
			setElmessage("您上传的文件为空！");
			return "";
		}else if (imgFile.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "";
		} else {
			int type = 0;
			if(imgType.equals("yingyezhizhao"))
				type = 1;
			if(imgType.equals("shuiwudengjizheng"))
				type = 2;
			if(imgType.equals("zuzhijigoudaimazheng"))
				type = 3;
			if(imgType.equals("farenshenfenzheng"))
				type = 4;
			if(imgType.equals("zizhidengjizhengshu"))
				type = 5;
			if(imgType.equals("xinyongdengjipingguzhengshu"))
				type = 6;
			if(imgType.equals("qitazhengshu"))
				type = 7;
			
			String ext = J2EEFileUtil.getExtention(imgFileFileName); 
			pfmsUser = indexDao.getUser(id,false);
			switch(type){
			case 1:
				pfmsUser.setYingyezhizhao(ext);
				J2EEFileUtil.upload_was(imgFile, pfmsUser.getYingyezhizhao(), "images/pfms", imgType+"_"+id);
				break;
			case 2:
				pfmsUser.setShuiwudengjizheng(ext);
				J2EEFileUtil.upload_was(imgFile, pfmsUser.getShuiwudengjizheng(), "images/pfms", imgType+"_"+id);
				break;
			case 3:
				pfmsUser.setZuzhijigoudaimazheng(ext);
				J2EEFileUtil.upload_was(imgFile, pfmsUser.getZuzhijigoudaimazheng(), "images/pfms", imgType+"_"+id);
				break;
			case 4:
				pfmsUser.setFarenshenfenzheng(ext);
				J2EEFileUtil.upload_was(imgFile, pfmsUser.getFarenshenfenzheng(), "images/pfms", imgType+"_"+id);
				break;
			case 5:
				pfmsUser.setZizhidengjizhengshu(ext);
				J2EEFileUtil.upload_was(imgFile, pfmsUser.getZizhidengjizhengshu(), "images/pfms", imgType+"_"+id);
				break;
			case 6:
				pfmsUser.setXinyongdengjipingguzhengshu(ext);
				J2EEFileUtil.upload_was(imgFile, pfmsUser.getXinyongdengjipingguzhengshu(), "images/pfms", imgType+"_"+id);
				break;
			case 7:
				pfmsUser.setQitazhengshu(ext);
				J2EEFileUtil.upload_was(imgFile, pfmsUser.getQitazhengshu(), "images/pfms", imgType+"_"+id);
				break;
			}
			indexDao.alterPfmsUserZhengshu(pfmsUser);
			pfmsUser = indexDao.getUser(id,false);
		} 
		if(showType == 1){
		}else{
			return "personal_success";
		}
		return "success";
	}
	
	public String pfmsUser_addInit() throws ElException{
		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID));
		//获取省市县	edone_area
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		areaList = gson.toJson(indexDao.areaList(null,null));
		return "pfmsUser_addInit";
	}
	
	public String getCity_ajax() throws ElException{
		
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		
		areaList = gson.toJson(indexDao.areaList(city_selected,city_type));
		
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"areaList\":" + areaList + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public String add_pfmsUser() throws ElException{
		pfmsUser.setProvince_city_county(new PfmsUtil().toProvince_city_county(province, city, county));
		int eluserid = indexDao.addPfmsUser(pfmsUser,depid);
		
		int returnDepId = 0;
		if(eluserid >0){
			//添加成功后,将部门添加到选择的部门节点下
			Department d = pfmsUser.getUser().getDepartment() == null?new Department():pfmsUser.getUser().getDepartment();
			
			d.setParent(new ElNode(depid==0?1:depid));
			d.setManager(new ELUser(1));
			d.setName(pfmsUser.getUser().getRealname());
			d.setDescription("");
			
			returnDepId = departmentDao.addDep1(d);
			
			//更新左右id
			((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
			.updatetlrid("department");
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_DEPARTMENT,
					ElLoggerConstants.LOG_TYPE_ADD, d.getName(),
					ElLoggerConstants.LOG_RES_SUCC, d.getId());
			
			//分配该用户该部门节点
			departmentDao.addOpusers("op", eluserid, returnDepId);
			
		}
		
		return "add_pfmsUser_success";
	}
	
	public String userlist() throws ElException{
		roleId = baoxianProductDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
//		else {
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		}
		if(department==null||department.getId()<=0){	
			sub_department =1;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		if(exprot == true){//导出
			getResponse().reset(); 		
			getResponse().setHeader("Content-disposition","attachment; filename=pfmsuser.xls"); 
			getResponse().setContentType("application/vnd.ms-excel");  
			pfmsUsers = indexDao.listAllPfmsUsers(department, sub_department);
			try {
				String titles[] = {"用户名","密码(不能修改此列,新增用户密码不能填写，密码默认是123456)","序号","姓名"	,"性别","身份证","部门编号","会员类型","省市县"};
				String attrs[]= {"username","password","xuhao","realname","sex","shenfenzheng","department.bh","huiyuanleixing","province_city_county"};
				new ExcelOutPut().writeExcel("会员用户表",getResponse().getOutputStream(),titles,
						PfmsUser.class.getName(), pfmsUsers, attrs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		elUsers = indexDao.listUsers(department, sub_department, elUser, getPageNow(), getPageSize());
		count = indexDao.listUsersSize(department, sub_department, elUser);
		roles = roleDao.listRoles();
		return "userlist";
	}
	
	public String pfmsUser_importByDepInit() throws ElException{
		if(elUser == null||elUser.getDepartment()==null||elUser.getDepartment().getId()<=-2){
			setElmessage("请指定用户将要导入有效部门");
			return "error";
		}
		department = departmentDao.getDepById((elUser == null || elUser
				.getDepartment()== null ) ? 1 : elUser.getDepartment().getId());
		return "pfmsUser_importByDepInit";
	}
	
	
	public String pfmsUser_import() throws ElException{
		if (null != batchImport) {
			if (!J2EEFileUtil.getExtention(batchImportFileName).toLowerCase().equals(
					"xls")) {
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return "failure";
			}
			if (batchImport.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "failure";
			} else {
				if(depid != 0){
					List<PfmsUser> pfmsUserList = new PfmsUtil().writePfmsUsers(batchImport,batchImportFileName,depid);
				}else{
					List<PfmsUser> pfmsUserList = new PfmsUtil().writePfmsUsers(batchImport,batchImportFileName,1);
				}
//				for(PfmsUser pfu:pfmsUserList){
//					indexDao.addPfmsUser(pfu,depid);
//				}
				
			}
		} else {
			setElmessage("请输入上传文件");
			return "failure";
		}
		return "pfmsUser_import_success";
	}
	
	public String checkPassword() throws ElException{
		boolean result = false;
		pfmsUser = indexDao.getUser(userId,false);
		if(pfmsUser.getUser().getPassword() != null && !pfmsUser.getUser().getPassword().equals("")){
			if(pfmsUser.getUser().getPassword().equals(new MD5().crypt(oldPassword))){
				result = true;
			}
		}
		
		check_json_result = String.valueOf(result);
		
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String change_tuijian() throws ElException{
		if(userids != null && !userids.equals("")){
			String[] ids_array = userids.split(",");
			for(int i=0;i<ids_array.length;i++){
				baoxianProductDao.change_tuijian(Integer.parseInt(ids_array[i]),select_tuijian,"pfmsuser");
			}
		}
		return "success";
	}
	
	public String delPfmsUser() throws ElException{
		if(userids!=null && !userids.equals("")){
			String[] ids_array=userids.split(",");
			for (int i = 0; i < ids_array.length; i++) {
				indexDao.delUser(Integer.parseInt(ids_array[i]));
			}
		}
		return "delPfmsUser_success";
	}
	
	
	private Department department;
	private int sub_department;
	private boolean exprot;
	private List<ELUser> elUsers;
	private List pfmsUsers;
	private List<ElRole> roles;
	private Department depTree;
	private RoleDao roleDao;
	
	private File batchImport;
	private String batchImportFileName;

	public List getPfmsUsers() {
		return pfmsUsers;
	}
	public void setPfmsUsers(List pfmsUsers) {
		this.pfmsUsers = pfmsUsers;
	}





	

}
