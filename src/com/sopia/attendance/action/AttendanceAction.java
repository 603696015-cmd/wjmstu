package com.sopia.attendance.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.attendance.dao.AttendanceDao;
import com.sopia.attendance.dao.impl.AttendanceDaoImpl;
import com.sopia.attendance.entity.Attendance;
import com.sopia.attendance.entity.WorkAttendance;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.office.ExcelOutPut;
import com.sopia.common.register.MACID;
import com.sopia.duman.action.UserAction;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.entities.Tags;

/**
 * 考勤action
 * @author TMK
 *
 */
public class AttendanceAction extends BaseAction{
	private static final Log logger = LogFactory.getLog(AttendanceAction.class);
	private AttendanceDao attendanceDao;
	private RoleDao roleDao;
	private List<ELUser> elUsers;
	private ELUser elUser;
	private Department department;
	private int sub_department;
	private boolean exprot;
	private List<ElRole> roles;
	private Department depTree;
	private Attendance attendance;
	private List<WorkAttendance> workAttendanceList;
	private Map<String,Integer> kqyl;//考勤一览
	private int count;
	private WorkAttendance workAttendance;
	private int id ;
	private int type;//类型  1==签到，2==签退，3==相关请假条，4==备注，5==系统自动计算
	private int userid;
	
	private Timestamp starttime;
	private Timestamp endtime;
	
	private String check_json_result;
	private String value ;
	
	private int orderBy;
	
	private String tablename;
	private String columnName;
	private List<Tags> list_tags = new ArrayList<Tags>();
	private TagsDao tagsDao;
	private List<Map<String, String>> list_designe = new ArrayList<Map<String, String>>();
	
	private String ordersc;
	private String ordercolumn;
	
	private String txtMac;
	
	
	/**
	 * 考勤设置初始化
	 * @return
	 * @throws ElException
	 */
	public String attendanceSetupInit() throws ElException{
//		System.out.println(getSessionIntValue(ElConstants.SESSION_ROLE));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department==null||department.getId()<=0){
			sub_department =1;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		if(exprot == true){//导出
			getResponse().reset(); 		
			getResponse().setHeader("Content-disposition","attachment; filename=user.xls"); 
			getResponse().setContentType("application/vnd.ms-excel");  
			elUsers = userDao.listUsers(department, sub_department, elUser);
			try {
				String titles[] = {"用户名","密码(不能修改此列,新增用户密码不能填写，密码默认是123456)","序号","姓名"	,"性别",	"地市","身份证",	"职级"	,"职务","工种","部门编号"};
				String attrs[]= {"username","password","xuhao","realname","sex","dishi_","shenfenzheng",	"zhiji_"	,"zhiwu_","jingzhong_","department.bh"};
				new ExcelOutPut().writeExcel("用户表",getResponse().getOutputStream(),titles,
						ELUser.class.getName(), elUsers, attrs);
			} catch (Exception e) {
				logger.error("导出账号列表错误",e);
			}
			return null;
		}
		
		roles = roleDao.listRoles();
		
		
		attendance = attendanceDao.getAttendance();
		
		
		elUsers = attendanceDao.listUsers(department, sub_department, elUser, getPageNow(), getPageSize(),ordercolumn,ordersc);
		count = attendanceDao.listUsersSize(department, sub_department, elUser);
		
		return "attendanceSetupInit_success";
	}
	
	/**
	 * 考勤设置
	 * @return
	 * @throws ElException
	 */
	public String attendanceSetup() throws ElException{
//		attendance.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		Attendance attendance1 = attendanceDao.getAttendance();
		
		
		if(attendance != null ){
			attendanceDao.updateAttendance(attendance);
		}else {
			attendanceDao.addAttendance(attendance);
		}
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department==null||department.getId()<=0){
			sub_department =1;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		if(exprot == true){//导出
			getResponse().reset(); 		
			getResponse().setHeader("Content-disposition","attachment; filename=user.xls"); 
			getResponse().setContentType("application/vnd.ms-excel");  
			elUsers = userDao.listUsers(department, sub_department, elUser);
			try {
				String titles[] = {"用户名","密码(不能修改此列,新增用户密码不能填写，密码默认是123456)","序号","姓名"	,"性别",	"地市","身份证",	"职级"	,"职务","工种","部门编号"};
				String attrs[]= {"username","password","xuhao","realname","sex","dishi_","shenfenzheng",	"zhiji_"	,"zhiwu_","jingzhong_","department.bh"};
				new ExcelOutPut().writeExcel("用户表",getResponse().getOutputStream(),titles,
						ELUser.class.getName(), elUsers, attrs);
			} catch (Exception e) {
				logger.error("导出账号列表错误",e);
			}
			return null;
		}
		elUsers = attendanceDao.listUsers(department, sub_department, elUser, getPageNow(), getPageSize(),ordercolumn,ordersc);
		count = attendanceDao.listUsersSize(department, sub_department, elUser);
		roles = roleDao.listRoles();
		return "attendanceSetup_success";
	}
	
	/**
	 * 节假日选择页面
	 * @return
	 * @throws ElException
	 */
	public String select_weekdaytime() throws ElException{
		attendance = attendanceDao.getAttendance();
		return "select_weekdaytime";
	}
	
	/**
	 * 查看设置的节假日
	 * @return
	 * @throws ElException
	 */
	public String view_weekdaytime() throws ElException{
		attendance = attendanceDao.getAttendance();
		return "view_weekdaytime";
	}
	
	
	/**
	 * 我的考勤列表
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String myAttendance() throws ElException, UnsupportedEncodingException{
		//查询我的考勤列表
		System.out.println(this.elmessage);
//		if(this.elmessage != null)
//			this.elmessage = new String(this.elmessage.getBytes("iso-8859-1"),"utf-8");
//		System.out.println(this.elmessage);
		if(userid != 0){
			workAttendanceList = attendanceDao.getWorkAttendanceByUserId(userid,getPageNow(), getPageSize(),starttime,endtime);
			count = attendanceDao.getWorkAttendanceSizeByUserId(userid,starttime,endtime);
		}else {
			workAttendance = attendanceDao.getWorkAttendanceByUserIdAndDate(getSessionIntValue(ElConstants.SESSION_USERID));
			
			workAttendance = workAttendance != null?workAttendance:new WorkAttendance();
			
			workAttendanceList = attendanceDao.getWorkAttendanceByUserId(getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(), getPageSize(),starttime,endtime);
			count = attendanceDao.getWorkAttendanceSizeByUserId(getSessionIntValue(ElConstants.SESSION_USERID),starttime,endtime);
		}
		
		//考勤一览
		if(userid != 0){
			kqyl = attendanceDao.getKqyl(userid,0,0);
		}else {
			kqyl = attendanceDao.getKqyl(getSessionIntValue(ElConstants.SESSION_USERID),0,0);
		}
		
		return "myAttendance_success";
	}
	
	//验证mac
	public String checkMacByUserId() throws ElException{
		boolean flag = false;
		//验证Mac
//		if(attendanceDao.getMacAddressByUserId(getSessionIntValue(ElConstants.SESSION_USERID)) == null 
//				|| attendanceDao.getMacAddressByUserId(getSessionIntValue(ElConstants.SESSION_USERID)).equals("")){//mac地址不存在
//			elUser = new ELUser();
//			elUser.setId(getSessionIntValue(ElConstants.SESSION_USERID));
//			elUser.setMac(txtMac==null?"":txtMac);
//			attendanceDao.updateMacAddressByUserId(elUser);
//		}else{//mac存在
//			//验证mac
//			String mac = attendanceDao.getMacAddressByUserId(getSessionIntValue(ElConstants.SESSION_USERID));
//			if(mac != null && !mac.equals("")){//mac地址存在
//				if(!mac.equals(txtMac)){
//					flag = false;
//				}else {
//					flag = true;
//				}
//			}
//		}
		
		check_json_result = String.valueOf(flag);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getSignTuiResult() throws ElException{
		
		
		workAttendance = attendanceDao.getWorkAttendanceByUserIdAndDate(getSessionIntValue(ElConstants.SESSION_USERID));
		
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		attendance = attendanceDao.getAttendance();
		
		String result = "";
		if(workAttendance != null){
			result = new AttendanceDaoImpl().checkResult(week,workAttendance,attendance);
		}else {
			workAttendance = new WorkAttendance();
			workAttendance.setSigntuitime(new Timestamp(System.currentTimeMillis()));
			result = new AttendanceDaoImpl().checkResult(week,workAttendance,attendance);
		}
		

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String updateResultByUserIdAndDate() throws ElException{
		//参数value、type
		//判断当天该用户的考勤信息有木有
		workAttendance = attendanceDao.getWorkAttendanceByUserIdAndDate(getSessionIntValue(ElConstants.SESSION_USERID));
		if(workAttendance == null) {//用户当天的考勤信息不存在
			//添加考勤信息
			attendanceDao.addKaoqinInfo(value,type,getSessionIntValue(ElConstants.SESSION_USERID));
		}else {//用户当天的考勤信息存在
			//修改考勤信息
			attendanceDao.updateKaoqinInfo(value,type,getSessionIntValue(ElConstants.SESSION_USERID));
		}
		String result = "";
		
		

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String addWorkAttendance() throws ElException, UnsupportedEncodingException{
		Map<String,Object> map = new HashMap<String,Object>();
		Calendar cal = Calendar.getInstance();
		long sfm_now = cal.get(Calendar.HOUR_OF_DAY) * 3600 + cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);//当前时间时分秒
		String relate_value = "";
		//判断workAttendance表中有无当前时间的记录
		
		workAttendance = attendanceDao.getWorkAttendanceByUserIdAndDate(getSessionIntValue(ElConstants.SESSION_USERID));
//		workAttendance = workAttendance == null?new WorkAttendance():workAttendance;
		if(type == 1 ){
//			//第一次登陆绑定MAC地址
//			if(attendanceDao.getMacAddressByUserId(getSessionIntValue(ElConstants.SESSION_USERID)) == null 
//					|| attendanceDao.getMacAddressByUserId(getSessionIntValue(ElConstants.SESSION_USERID)).equals("")){//mac地址不存在
//				elUser = new ELUser();
//				elUser.setId(getSessionIntValue(ElConstants.SESSION_USERID));
//				elUser.setMac(txtMac==null?"":txtMac);
//				attendanceDao.updateMacAddressByUserId(elUser);
//			}else{//mac存在
//				//验证mac
//				String mac = attendanceDao.getMacAddressByUserId(getSessionIntValue(ElConstants.SESSION_USERID));
//				if(mac != null && !mac.equals("")){//mac地址存在
//					if(!mac.equals(txtMac)){
//						this.setElmessage("MAC地址不符合,请更换电脑重新操作或者联系管理员!!!");
//						return "error";
//					}
//				}
//			}
			if(workAttendance == null){
				workAttendance = new WorkAttendance();
			}else {
				if(type == 1){//签到
					if(workAttendance.getSigntuitime() != null ){
						if(sfm_now>new AttendanceDaoImpl().getSFM(workAttendance.getSigntuitime())){
							this.setElmessage("签退时间不能小于签到时间");
							return "error";
						}
					}
				}
			}
			
		}
		
		if(type == 4){
			if(workAttendance == null)
				workAttendance = new WorkAttendance();
			workAttendance.setMark(value);
		}
		AttendanceDaoImpl impl = new AttendanceDaoImpl();
		if(type == 3){
			if(workAttendance == null)
				workAttendance = new WorkAttendance();
			workAttendance.setRelateleave(value);
			//判断时间关系
			if(!impl.checkTimeIn(workAttendance, type)){
				this.setElmessage("Your select does not contain today,please select other!!!");
//				this.setElmessage("您选择的相关请假条不包含当天时间，请重新选择请假条!!!");
				return "addWorkAttendance_failure";
			}
			workAttendance.setResult("请假");
		}
		
		if(type == 6){
			if(workAttendance == null)
				workAttendance = new WorkAttendance();
			workAttendance.setRelateout(value);
			if(!impl.checkTimeIn(workAttendance, type)){
//					new String(param.getBytes("iso-8859-1"), "utf-8")
//				this.setElmessage("您选择的相关外出单不包含当天时间，请重新选择外出单!!!");
				this.setElmessage("Your select does not contain today,please select other!!!");
//					this.setElmessage(new String("您选择的相关外出单不包含当天时间，请重新选择外出单!!!".getBytes("iso-8859-1"),"utf-8"));
				return "addWorkAttendance_failure";
			}
			
			workAttendance.setResult("外出");
		}
		if(type == 7){
			if(workAttendance == null)
				workAttendance = new WorkAttendance();
			workAttendance.setRelateretroactive(value);
			if(!impl.checkTimeIn(workAttendance, type)){
				this.setElmessage("Your select does not contain today,please select other!!!");
//				this.setElmessage("您选择的相关补签单和当天考勤无关，请重新选择补签单!!!");
				return "addWorkAttendance_failure";
			}
			workAttendance.setResult("补签");
			
			//判断是签到还是签退、将补签时间写入对应字段
			map = attendanceDao.checkIsSign(workAttendance,"BQGL");
			
			String BQGL_BQLX = ((String)map.get("BQGL_BQLX"));
			Timestamp BQGL_BQDQSJ;
			if(BQGL_BQLX!= null &&!BQGL_BQLX.equals("")) {
				if(BQGL_BQLX.equals("签到")){
					workAttendance.setRelateretroactive_type("签到");
					BQGL_BQDQSJ = (Timestamp)map.get("BQGL_BQDQSJ") == null?null:(Timestamp)map.get("BQGL_BQDQSJ");
					if(BQGL_BQDQSJ != null)
						workAttendance.setSigndaotime(BQGL_BQDQSJ);
				}else if(BQGL_BQLX.equals("签退")){
					workAttendance.setRelateretroactive_type("签退");
					BQGL_BQDQSJ = (Timestamp)map.get("BQGL_BQDQSJ") == null?null:(Timestamp)map.get("BQGL_BQDQSJ");
					if(BQGL_BQDQSJ != null)
						workAttendance.setSigntuitime(BQGL_BQDQSJ);
				}
			}
			
			//考勤结果重新计算
			attendance = attendanceDao.getAttendance();
			String result_ = impl.checkResult(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1, workAttendance, attendance);
			workAttendance.setResult(result_);
		}
		
		if(type == 5){
			if(workAttendance == null ){
				this.setElmessage("No starttime or endtime,please sign!!!");
//				this.setElmessage("您还未填写签到或者签退时间，请添加后再做操作!!!");
				return "addWorkAttendance_failure";
			}
		}
		if(type != 0){
			if(workAttendance.getId() == 0 ){
				id = attendanceDao.addWorkAttendance(workAttendance,getSessionIntValue(ElConstants.SESSION_USERID),type);
			}else {
				attendanceDao.updateWorkAttendance(getSessionIntValue(ElConstants.SESSION_USERID),workAttendance,type);
			}
		}
		
		//查询我的考勤列表
		workAttendanceList = attendanceDao.getWorkAttendanceByUserId(getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(), getPageSize(),starttime,endtime);
		count = attendanceDao.getWorkAttendanceSizeByUserId(getSessionIntValue(ElConstants.SESSION_USERID),starttime,endtime);
		
		return "addWorkAttendance";
	}
	
	//选择相关请假条
	public String selectRelateLeave() throws ElException{
		System.out.println(tablename);
		
		String search_control=getRequest().getParameter("control");
//		String columnName=getRequest().getParameter("columnName");
		if(search_control==null)
			search_control="0";
		
//		list_tags =moduleManageDao 
//		.select_designe_field_by_tablename(null,tablename);
		list_tags = tagsDao
		.select_designe_field_by_tablename(tablename);
		
		String is_judge = "0";
		for(int i=0;i<list_tags.size();i++){
			if(list_tags.get(i).getDisplay_type().equals("相关字段")){
				is_judge = String.valueOf(list_tags.get(i).getIs_judge());
			}
		}
		
		getRequest().setAttribute("is_judge",is_judge);
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else
		{
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		
		if (department == null || department.getId() <= 0)
		{
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());

		Map<String, String> hm = new HashMap<String, String>();

		

		// 组合搜索获取搜索条件
		for (int i = 0; i < list_tags.size(); i++)
		{
			String str = (String) getRequest().getParameter(
					list_tags.get(i).getColumn_name());
			if (str != null && !str.equals(""))
			{
				hm.put(list_tags.get(i).getColumn_type() + "=="
						+ list_tags.get(i).getColumn_name(), str);
				list_tags.get(i).setValue(str);//将搜索条件传回前端
			}
			//日期格式
			if (list_tags.get(i).getColumn_type().equals("date"))
			{
				String str2 = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name() + "_");
				if (str2 != null && !str2.equals(""))
				{
					hm.put(list_tags.get(i).getColumn_type() + "=="
							+ list_tags.get(i).getColumn_name() + "_", str2);
					list_tags.get(i).setValue2(str2);
				}
			}
			//数字
			if(list_tags.get(i).getColumn_type().equals("number")||
					list_tags.get(i).getColumn_type().equals("float"))
			{
				String str2 = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name() + "_");
				if (str2 != null && !str2.equals(""))
				{
					hm.put(list_tags.get(i).getColumn_type() + "=="
							+ list_tags.get(i).getColumn_name() + "_", str2);
					list_tags.get(i).setValue2(str2);
				}
			}
		}

		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		
		String order="";
		
		
//		tags = tagsDao.select_designe_field_by_id(tagsDao.select_designe_field_id_by_columnName(columnName));
		if(is_judge.equals("1")){//权限判断
			list_designe = tagsDao.select_my_tableinfo_by_dep_principal(new Tags(),list_tags,//  department,  
					tablename, hm,	department,										//tablename, hm,
					Integer.valueOf(search_control),userid,										//search_control, principalid,
					getPageNow(), getPageSize());
			count = tagsDao.select_my_tableinfo_by_dep_principal_count(new Tags(),list_tags, hm, //, 
					tablename, department,   //tablename, department
					Integer.valueOf(search_control),userid);//search_control, principalid)
		}
		else if(is_judge.equals("0")){//无权限判断
			list_designe = tagsDao.select_my_tableinfo_by_dep_principal_with_judge(new Tags(),list_tags,  
					tablename, hm,	department,										
					Integer.valueOf(search_control),userid,										
					getPageNow(), getPageSize());
			count = tagsDao.select_my_tableinfo_by_dep_principal_count_with_judge(new Tags(),list_tags, hm, //, 
					tablename, department,   //tablename, department
					Integer.valueOf(search_control),userid);//search_control, principalid)
		}
		
		return "selectRelateLeave";
	}
	
	public String updateWorkAttendanceById() throws ElException{
		
		attendanceDao.updateWorkAttendanceById(id,type,value);
		
		//重新计算结果
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		workAttendance = attendanceDao.getWorkAttendanceById(id);
		attendance = attendanceDao.getAttendance();
		String result = new AttendanceDaoImpl().checkResult(week,workAttendance,attendance);
		if(result != null && !result.equals(""))
			workAttendance.setResult(result);
		attendanceDao.updateWorkAttendanceResult(workAttendance);
		
		return "updateWorkAttendanceById";
	}
	
	/**
	 * 查看考勤根据id
	 * @return
	 * @throws ElException
	 */
	public String viewWorkAttendance() throws ElException{
		workAttendance = attendanceDao.getAttendanceById(id);
		return "viewWorkAttendance";
	}
	
	/**
	 * 考勤查询
	 * @return
	 * @throws ElException
	 */
	public String attendanceQuery() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department==null||department.getId()<=0){
			sub_department =1;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		
		workAttendanceList = attendanceDao.getWorkAttendanceQuery(department,sub_department,getPageNow(), getPageSize(),starttime,endtime);
		count = attendanceDao.getWorkAttendanceSizeQuery(department,sub_department,starttime,endtime);
		
//		kqyl = attendanceDao.getKqyl(0,getPageNow(), getPageSize());
		return "attendanceQuery";
	}
	
	/**
	 * 考勤排行榜
	 * @return
	 * @throws ElException
	 */
	public String attendanceOrder() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department==null||department.getId()<=0){
			sub_department =1;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		
		roles = roleDao.listRoles();
		
		elUsers = attendanceDao.listUsers(department, sub_department, elUser, getPageNow(), getPageSize(),ordercolumn,ordersc);
		
		for(ELUser e:elUsers){
			e.setKqyl(attendanceDao.getKqyl(e.getId(),0,0));
		}
		
		count = attendanceDao.listUsersSize(department, sub_department, elUser);
		return "attendanceOrder";
	}
	
	public String getStatus() throws ElException{
		workAttendance = attendanceDao.getWorkAttendanceByUserIdAndDate(getSessionIntValue(ElConstants.SESSION_USERID));
		workAttendance = workAttendance != null?workAttendance:new WorkAttendance();
		
		
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
	
	public String sign() throws ElException{
		workAttendance = attendanceDao.getWorkAttendanceByUserIdAndDate(getSessionIntValue(ElConstants.SESSION_USERID));
//		workAttendance = workAttendance == null?new WorkAttendance():workAttendance;
		if(type == 1){//签到
			if(workAttendance != null)
				attendanceDao.updateWorkAttendance(getSessionIntValue(ElConstants.SESSION_USERID), workAttendance, type);
			else 
				attendanceDao.addWorkAttendance(workAttendance, getSessionIntValue(ElConstants.SESSION_USERID), type);
		}else if(type == 2){//签退
			if(workAttendance != null)
				attendanceDao.updateWorkAttendance(getSessionIntValue(ElConstants.SESSION_USERID), workAttendance, type);
			else 
				attendanceDao.addWorkAttendance(workAttendance, getSessionIntValue(ElConstants.SESSION_USERID), type);
		}
		
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		workAttendance = attendanceDao.getWorkAttendanceByUserIdAndDate(getSessionIntValue(ElConstants.SESSION_USERID));
		attendance = attendanceDao.getAttendance();
		String result = new AttendanceDaoImpl().checkResult(week,workAttendance,attendance);
		if(result != null && !result.equals(""))
			workAttendance.setResult(result);
		attendanceDao.updateWorkAttendanceResult(workAttendance);
		return "sign";
	}
	
	/**
	 * 跳转到填写备注页
	 * @return
	 * @throws ElException
	 */
	public String fieldMark() throws ElException{
		return "fieldMark";
	}
	
	public String updateMacInit() throws ElException{
		elUser = userDao.getUserById(id);
		return "updateMacInit_success";
	}
	
	public String updateMac() throws ElException{
		attendanceDao.updateMacAddressByUserId(elUser);
		return "updateMac_success";
	}

	public AttendanceDao getAttendanceDao() {
		return attendanceDao;
	}

	public void setAttendanceDao(AttendanceDao attendanceDao) {
		this.attendanceDao = attendanceDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
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


	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}


	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public Attendance getAttendance() {
		return attendance;
	}

	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}

	public List<WorkAttendance> getWorkAttendanceList() {
		return workAttendanceList;
	}

	public void setWorkAttendanceList(List<WorkAttendance> workAttendanceList) {
		this.workAttendanceList = workAttendanceList;
	}

	public WorkAttendance getWorkAttendance() {
		return workAttendance;
	}

	public void setWorkAttendance(WorkAttendance workAttendance) {
		this.workAttendance = workAttendance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public String getCheck_json_result() {
		return check_json_result;
	}

	public void setCheck_json_result(String check_json_result) {
		this.check_json_result = check_json_result;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<Tags> getList_tags() {
		return list_tags;
	}

	public void setList_tags(List<Tags> list_tags) {
		this.list_tags = list_tags;
	}

	public TagsDao getTagsDao() {
		return tagsDao;
	}

	public void setTagsDao(TagsDao tagsDao) {
		this.tagsDao = tagsDao;
	}

	public List<Map<String, String>> getList_designe() {
		return list_designe;
	}

	public void setList_designe(List<Map<String, String>> list_designe) {
		this.list_designe = list_designe;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Map<String, Integer> getKqyl() {
		return kqyl;
	}

	public void setKqyl(Map<String, Integer> kqyl) {
		this.kqyl = kqyl;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrdersc() {
		return ordersc;
	}

	public void setOrdersc(String ordersc) {
		this.ordersc = ordersc;
	}

	public String getOrdercolumn() {
		return ordercolumn;
	}

	public void setOrdercolumn(String ordercolumn) {
		this.ordercolumn = ordercolumn;
	}

	public String getTxtMac() {
		return txtMac;
	}

	public void setTxtMac(String txtMac) {
		this.txtMac = txtMac;
	}

}
