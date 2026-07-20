package com.sopia.userDemo.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sopia.BaseAction;
import com.sopia.common.ElException;
import com.sopia.common.J2EEFileUtil;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.ElRole;
import com.sopia.schedule.TagsUtil;
import com.sopia.userDemo.UserDemoConstants;
import com.sopia.userDemo.UserDemoUtil;
import com.sopia.userDemo.dao.UserDemoDao;
import com.sopia.userDemo.entities.ELUserColumn;
import com.sopia.userDemo.entities.ELUserColumnJs;
import com.sopia.userDemo.entities.ELUserJs;
import com.sopia.userDemo.entities.ELUserColumnPage;
import com.sopia.userDemo.entities.ELUserPage;

/**
 * 自定义用户管理模块action
 * @author Administrator
 *
 */
public class UserDemoAction extends BaseAction{
	private static final Log logger = LogFactory.getLog(UserDemoAction.class);
	
	private UserDemoDao usereDemoDao;
	private List<ELUserColumn> columns;
	private ELUserColumn col;
	private List<ELUserJs> jsTypes ;
	private ELUserColumnJs elUserJs;
	private ELUserPage elUser_page_type;
	private List<ELUserColumnPage> pageInfos;
	private ELUserColumnPage pageInfo;
	private File st;
	private String stFileName;
	
	private List<ElRole> roles;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> dishis;
	private RoleDao roleDao;
	
	private int pageid;
	private Map<String,String> map;

	
	/**
	 * 显示列信息
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String listUserDemoColumns() throws ElException, UnsupportedEncodingException{
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		//查询userdemocolumn表获取用户表的各个字段信息
		//显示的页面上只能修改或者添加自定义的字段
		columns = usereDemoDao.listUserDemoColumns(UserDemoConstants.USERDEMOCOLUMN_TABLE);
		return "listUserDemoColumns";
	}
	
	/**
	 * 修改字段
	 * @return
	 * @throws ElException
	 */
	public String updateUserDemoColumns() throws ElException{
		String[] names = getRequest().getParameterValues("column_name");//列名
		String[] descriptions = getRequest().getParameterValues("description");//描述
		String[] show_add = getRequest().getParameterValues("show_add");//添加页是否显示
		String[] show_update = getRequest().getParameterValues("show_update");//修改页是否显示
		String[] show_view = getRequest().getParameterValues("show_view");//查看页是否显示
		String[] show_register = getRequest().getParameterValues("show_register");//注册页是否显示
		String[] show_user_update = getRequest().getParameterValues("show_user_update");//个人修改页是否显示
		String[] show_user_view = getRequest().getParameterValues("show_user_view");//个人查看页是否显示
		String[] show_list = getRequest().getParameterValues("show_list");//列表页是否显示
		String[] column_types = getRequest().getParameterValues("column_type");//列类型
		String[] formats = getRequest().getParameterValues("format");//列格式或者长度
		String[] need = getRequest().getParameterValues("need");//是否必填 
//		String[] pages = getRequest().getParameterValues("show_page_type");//是否必填
		ELUserColumn co = null;
		if(names!=null){
			for(int i=0;i<names.length;i++){
				co = new ELUserColumn(names[i],descriptions[i]==null?"":descriptions[i],Integer.parseInt(show_add[i]),Integer.parseInt(show_update[i]),Integer.parseInt(show_view[i]),Integer.parseInt(show_register[i]),Integer.parseInt(show_user_update[i]),Integer.parseInt(show_user_view[i]),Integer.parseInt(show_list[i]),column_types[i],formats[i],Integer.parseInt(need[i]));
				usereDemoDao.updateUserDemoColumn(co,UserDemoConstants.USERDEMOCOLUMN_TABLE);
			}
		}
		return "updateUserDemoColumns_success";
	}
	
	public String addUserDemoColumnInit() throws ElException{
		
		return "addUserDemoColumnInit";
	}
	
	/**
	 * 添加字段
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String addUserDemoColumn() throws ElException, UnsupportedEncodingException{
		if(col!=null){
			//验证该字段是否已经存在
			boolean flag = usereDemoDao.checkColumnIsExist(col.getColumn_name(),UserDemoConstants.USERDEMOCOLUMN_TABLE);
			if(flag){
				this.setElmessage("该字段名称已经存在,请重新添加");
				return "error";
			}
			usereDemoDao.insertUserDemoColumn(col,UserDemoConstants.USERDEMOCOLUMN_TABLE,UserDemoConstants.TABLENAME);
		}else{
			this.setElmessage("参数有误");
			return "error";
		}
		setElmessage(URLEncoder.encode(URLEncoder.encode("添加字段成功!!!", "UTF-8"), "UTF-8"));
		return "addUserDemoColumn_success";
	}
	
	/**
	 * 用户列表
	 * @throws ElException
	 */
	public String listElUserDemo() throws ElException{
		//首先查询需要显示的列
		List<ELUserColumn> cols = usereDemoDao.selectColumnsByShow(UserDemoConstants.SHOW_LIST,UserDemoConstants.USERDEMOCOLUMN_TABLE);
		//将列拼凑成列sql
		String sqlcolumns = "";
		if(cols!=null && cols.size()>0){
			sqlcolumns = UserDemoUtil.getSqlcolumns(cols,null);
		}
		//查询数据
		
		return "listElUserDemo";
	}
	
	/**
	 * 获取所有的js实体
	 * @return
	 * @throws ElException
	 */
	public String listAllJsTypes() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		String jsonsValue=null;
		jsonsValue = TagsUtil.ToGson(usereDemoDao.listAllJsTypes(UserDemoConstants.JSTYPE_TABLE));
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsonsValue\":" + jsonsValue + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 给列添加js验证
	 * @return
	 * @throws ElException
	 */
	public String addColumnJsInit() throws ElException{
		jsTypes = usereDemoDao.listAllJsTypes(UserDemoConstants.JSTYPE_TABLE);
		return "addColumnJsInit";
	}
	
	//设置列的js校验
	public String addColumnJs() throws ElException, UnsupportedEncodingException{
		usereDemoDao.insertUserColumnJs(elUserJs,elUserJs.getShow_type());
		setElmessage(URLEncoder.encode(URLEncoder.encode("字段'"+elUserJs.getColumn_name()+"'添加JS验证成功!!!", "UTF-8"), "UTF-8"));
		return "addColumnJs";
	}
	
	//设置列的范围和默认值
	public String setPageTypeInit() throws ElException{
		return "setPageTypeInit";
	}
	
	public String setPageType() throws ElException, UnsupportedEncodingException{
		usereDemoDao.setPageType(elUser_page_type);
		setElmessage(URLEncoder.encode(URLEncoder.encode("字段'"+elUserJs.getColumn_name()+"'设置范围成功!!!", "UTF-8"), "UTF-8"));
		return "setPageType";
	}
	
	/**
	 * 各个页面的上传页面
	 * @return
	 * @throws ElException
	 */
	public String listELUserPage() throws ElException{
		pageInfos = usereDemoDao.listELUserPage(UserDemoConstants.ELUSER_PAGE_INFO);
		return "listELUserPage";
	}
	
	/**
	 * 上传初始化
	 * @return
	 * @throws ElException
	 */
	public String uploadPageJspInit() throws ElException{
		
		return "uploadPageJspInit";
	}
	
	/**
	 * 上传
	 * @return
	 * @throws ElException
	 */
	public String uploadPageJsp() throws ElException{
		//上传JSP到指定的文件夹
		try {
			J2EEFileUtil.upload_xianzhong(st, "jsp",J2EEFileUtil.getRealPath("/") + 
					UserDemoConstants.FOLDER, pageInfo.getPageid()+".jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//判断是否上传成功
		//检测文件夹下是否存在对应JSP文件
		
		//上传成功后，修改pageid对应的upload属性
		usereDemoDao.updateUpload(pageInfo.getPageid());
		return "uploadPageJsp";
	}
	
	
	//一下为管理用户模块
	/**
	 * 添加页面
	 */
	public String userdemo_addInit() throws ElException{
		if(!UserDemoUtil.checkFilenameIsExist(pageid)){
			this.setElmessage("系统升级中,请稍后");
			return "error";
		}
		roles = roleDao.listRoles();  //角色
		jingzhongs = userDao.getBaseDatatByTypeidc(1);
		zhiwus = userDao.getBaseDatatByTypeidc(2);
		zhijis = userDao.getBaseDatatByTypeidc(3);
		dishis = userDao.getBaseDatatByTypeidc(5);
		
		columns =  usereDemoDao.getColumnsByPageid(pageid);
		jsTypes = usereDemoDao.listAllJsTypes(UserDemoConstants.JSTYPE_TABLE);
		
		return "userdemo_addInit";
	}
	
	/**
	 * 添加操作
	 * @return
	 * @throws ElException
	 */
	public String userdemo_add() throws ElException{
		Map<String,String> map = new HashMap<String,String>();//用来保存值
		//获取添加页面上显示的列和值
		columns = usereDemoDao.getColumnsByPageid(pageid);
		for(int i=0;i<columns.size();i++){
			map.put(columns.get(i).getColumn_name(), UserDemoUtil.setValueByColumn(getRequest(),columns.get(i)));
		}
		
		//插入操作
		if(usereDemoDao.insertIntoELUser(map,UserDemoConstants.USERDEMOCOLUMN_TABLE)<=0){
			this.setElmessage("插入失败");
			return "error";
		}
		return "userdemo_add";
	}
	
	/**
	 * 修改页面
	 * @return
	 * @throws ElException
	 */
	public String userdemo_updateInit() throws ElException{
		//获取值
		return "userdemo_updateInit";
	}
	
	
	
	/////////////////////////////
	//gets   sets
	public UserDemoDao getUsereDemoDao() {
		return usereDemoDao;
	}
	public void setUsereDemoDao(UserDemoDao usereDemoDao) {
		this.usereDemoDao = usereDemoDao;
	}
	public List<ELUserColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<ELUserColumn> columns) {
		this.columns = columns;
	}

	public ELUserColumn getCol() {
		return col;
	}

	public void setCol(ELUserColumn col) {
		this.col = col;
	}

	public List<ELUserJs> getJsTypes() {
		return jsTypes;
	}

	public void setJsTypes(List<ELUserJs> jsTypes) {
		this.jsTypes = jsTypes;
	}

	public ELUserColumnJs getElUserJs() {
		return elUserJs;
	}

	public void setElUserJs(ELUserColumnJs elUserJs) {
		this.elUserJs = elUserJs;
	}

	public ELUserPage getElUser_page_type() {
		return elUser_page_type;
	}

	public void setElUser_page_type(ELUserPage elUser_page_type) {
		this.elUser_page_type = elUser_page_type;
	}

	public List<ELUserColumnPage> getPageInfos() {
		return pageInfos;
	}

	public void setPageInfos(List<ELUserColumnPage> pageInfos) {
		this.pageInfos = pageInfos;
	}

	public ELUserColumnPage getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(ELUserColumnPage pageInfo) {
		this.pageInfo = pageInfo;
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

	public List<ElRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<BaseDatat> getJingzhongs() {
		return jingzhongs;
	}

	public void setJingzhongs(List<BaseDatat> jingzhongs) {
		this.jingzhongs = jingzhongs;
	}

	public List<BaseDatat> getZhiwus() {
		return zhiwus;
	}

	public void setZhiwus(List<BaseDatat> zhiwus) {
		this.zhiwus = zhiwus;
	}

	public List<BaseDatat> getZhijis() {
		return zhijis;
	}

	public void setZhijis(List<BaseDatat> zhijis) {
		this.zhijis = zhijis;
	}

	public List<BaseDatat> getDishis() {
		return dishis;
	}

	public void setDishis(List<BaseDatat> dishis) {
		this.dishis = dishis;
	}

	public int getPageid() {
		return pageid;
	}

	public void setPageid(int pageid) {
		this.pageid = pageid;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	

}
