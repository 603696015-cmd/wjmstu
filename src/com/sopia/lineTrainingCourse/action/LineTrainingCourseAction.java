package com.sopia.lineTrainingCourse.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.lineTrainingCourse.dao.LineTrainingCourseDao;
import com.sopia.lineTrainingCourse.dao.LineTrainingCourseTreeDao;
import com.sopia.lineTrainingCourse.entities.LineTrainingCourse;
import com.sopia.lineTrainingCourse.entities.LineTrainingCourseAssign;
import com.sopia.lineTrainingCourse.entities.TrainType;
import com.sopia.lineTrainingCourse.entities.TrainTypeTree;
import com.sopia.pfms.entities.Product;

public class LineTrainingCourseAction extends BaseAction{
	private DepartmentDao departmentDao;
	private LineTrainingCourseDao lineTrainingCourseDao;
	private LineTrainingCourseTreeDao lineTrainingCourseTreeDao;
	private IndexDataUtil indexDataUtil;
	private Department depTree;
	private List<LineTrainingCourse> lineTrainingCourseList;
	private List<LineTrainingCourse> allLineTrainingCourseList;
	private List<LineTrainingCourseAssign> assignList;
	private LineTrainingCourseAssign assign;
	private LineTrainingCourse lineTrainingCourse;
	private List<TrainType> trainTypes;
	private int count;
	private Department department;
	private int sub_department;
	private Timestamp starttime;
	private Timestamp endtime;
	private boolean line_training_course_add_need_sh;//添加培训是否需要审核
	private int id;//培训id
	private String check_json_result;
	private String ids;
	private String type;
	private int not_open;
	private String type1;
	
	private String fieldName;
	private int status;
	
	private double total_credit;
	private double total_get_credit;
	
	private int assign_id;
	private double assign_score;
	private double assign_credit;
	private int line_training_course_id;
	
	private int change_is_get_certificate;
	
	private File st;
	private String stFileName;
	
	private List<ELUser> elUsers;
	private ELUser elUser;
	
	private String fileName;
	
	private TrainTypeTree trainTypeTree;
	private TrainTypeTree ptype;
	
	private String userIds;
	private String option;
	private int productIsDel;//  1.并入上级类别  2.与本类别同时删除
	
	private String optype;
	private String message;
	
	public String myLineTrainingCourse() throws ElException{
		trainTypes = lineTrainingCourseDao.getTrainTypes();
		depTree = departmentDao.getDepTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		
		if(department==null||department.getId()<=0){	
			sub_department =1;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		
		lineTrainingCourseList = lineTrainingCourseDao.lineTrainingCourseList(null,department,lineTrainingCourse,getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(), getPageSize(),starttime,endtime);
		count = lineTrainingCourseDao.lineTrainingCourseListSize(null,department,lineTrainingCourse,getSessionIntValue(ElConstants.SESSION_USERID),starttime,endtime);
		
		return "lineTrainingCourse_success";
	}
	
	public String allLineTrainingCourse() throws ElException{
		trainTypes = lineTrainingCourseDao.getTrainTypes();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		} else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		
		if(department==null||department.getId()<=0){	
			sub_department =1;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		allLineTrainingCourseList = lineTrainingCourseDao.lineTrainingCourseList(null,department,lineTrainingCourse,0,getPageNow(), getPageSize(),starttime,endtime);
		count = lineTrainingCourseDao.lineTrainingCourseListSize(null,department,lineTrainingCourse,0,starttime,endtime);
		return "lineTrainingCourse_success";
	}
	
	public String addLineTrainingCourseInit() throws ElException{
		trainTypes = lineTrainingCourseDao.getTrainTypes();
		return "addLineTrainingCourseInit";
	}
	
	public String addLineTrainingCourse() throws ElException{
		line_training_course_add_need_sh = SystemConfOp.getBooleanValue(ElConstants.LINE_TRAINING_COURSE_ADD_NEED_SH);
		lineTrainingCourseDao.addLineTrainingCourse(line_training_course_add_need_sh,getSessionIntValue(ElConstants.SESSION_USERID),lineTrainingCourse);
		return "addLineTrainingCourse_success";
	}
	
	public String showLineTrainingCourseView() throws ElException{
		String resultPage = "";
		trainTypes = lineTrainingCourseDao.getTrainTypes();
		
		if(type1 != null && type1.equals("front")){
			lineTrainingCourse = lineTrainingCourseDao.getLineTrainingCourseById("front",id);
			trainTypes = lineTrainingCourseDao.getTrainTypes();
			
			trainTypeTree = lineTrainingCourseTreeDao.getTraintypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
			int nid = ptype == null ? trainTypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());
			if(lineTrainingCourse==null){
				lineTrainingCourse = new LineTrainingCourse();
//				sublibs = 1;
			}
			if (lineTrainingCourse.getPtype() == null
					|| lineTrainingCourse.getPtype().getId() <= 0) {
				lineTrainingCourse.setPtype(trainTypeTree);
			} else {
				lineTrainingCourse.setPtype(lineTrainingCourseTreeDao.getPtypeLibById(lineTrainingCourse.getPtype().getId()));
			}
			resultPage = "lineTrainingCourse_content";
		}else{
			lineTrainingCourse = lineTrainingCourseDao.getLineTrainingCourseById(null,id);
			resultPage = "showLineTrainingCourseView";
		}
		return resultPage;
	}
	
	public String updateLineTrainingCourse() throws ElException{
		String resultPage = "";
		lineTrainingCourseDao.updateLineTrainingCourseById(lineTrainingCourse);
		if(type != null && type.equals("all")){
			resultPage = "updateLineTrainingCourse_inall_success";
		}else{
			resultPage = "updateLineTrainingCourse_success";
		}
		return resultPage;
	}
	
	public String check_is_open() throws ElException{
		boolean result = false;
		if(id != 0 ){
			result = lineTrainingCourseDao.checkIs_open(id);//true	开通
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
	
	public String lineTrainingCourseByIds() throws ElException{
		String resultPage = "";
		if(ids!=null){//如果是批量操作
			String[] idss=ids.split(",");
			if(type != null && type.equals("open")){//如果操作是批量开通
				for (int i = 0; i < idss.length; i++) {
					lineTrainingCourseDao.openLineTrainingCourse(Integer.parseInt(idss[i]));
				}
				resultPage = "openLineTrainingCourseByIds_inall_success";
			}else{//操作是批量删除
				for (int i = 0; i < idss.length; i++) {
					lineTrainingCourseDao.deleteLineTrainingCourse(Integer.parseInt(idss[i]));
				}
				if(type1 != null && type1.equals("inall")){//若果在线下培训班查询
					resultPage = "deleteLineTrainingCourseByIds_inall_success";
				}else{//在我发布的培训查询
					resultPage = "deleteLineTrainingCourseByIds_success";
				}
				
			}
		}else{//不是批量操作
			if(not_open == 1){
				lineTrainingCourseDao.NotOpenLineTrainingCourse(id);
			}else{
				lineTrainingCourseDao.openLineTrainingCourse(id);
			}
			resultPage = "openLineTrainingCourseByIds_inall_success";
		}
		return resultPage;
	}
	
	public String line_training_course_center() throws ElException{
		trainTypes = lineTrainingCourseDao.getTrainTypes();
		
		trainTypeTree = lineTrainingCourseTreeDao.getTraintypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		int nid = ptype == null ? trainTypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());
		if(lineTrainingCourse==null){
			lineTrainingCourse = new LineTrainingCourse();
//			sublibs = 1;
		}
		if (lineTrainingCourse.getPtype() == null
				|| lineTrainingCourse.getPtype().getId() <= 0) {
			lineTrainingCourse.setPtype(trainTypeTree);
		} else {
			lineTrainingCourse.setPtype(lineTrainingCourseTreeDao.getPtypeLibById(lineTrainingCourse.getPtype().getId()));
		}
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
//		} else {
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		}
		
//		if(department==null||department.getId()<=0){	
//			sub_department =1;
//			department = depTree;
//		}else
//			department  = departmentDao.getDepById(department.getId());
		allLineTrainingCourseList = lineTrainingCourseDao.lineTrainingCourseList("front",null,lineTrainingCourse,0,getPageNow(), getPageSize(),starttime,endtime);
		count = lineTrainingCourseDao.lineTrainingCourseListSize("front",null,lineTrainingCourse,0,starttime,endtime);
		return "line_training_course_center";
	}
	
	public String signByPerson() throws ElException{
		lineTrainingCourseDao.signByPerson(null,lineTrainingCourse,getSessionIntValue(ElConstants.SESSION_USERID));
		
		return "signByPerson_success";
	}
	
	public String goto_shenhe() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		} else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		
		if(department==null||department.getId()<=0){	
			sub_department =1;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		
		assignList = lineTrainingCourseDao.getAssignList(department,lineTrainingCourse,assign,getPageNow(), getPageSize());
		count = lineTrainingCourseDao.getAssignListSize(department,lineTrainingCourse,assign);
		return "goto_shenhe_success";
	}
	
	public String option_in_shenhePage() throws ElException{
		lineTrainingCourseDao.option_in_shenhePage(assign,fieldName,status);
		return "option_in_shenhePage_success";
	}
	
	public String result_entry() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		} else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		
		if(department==null||department.getId()<=0){	
			sub_department =1;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		
		assignList = lineTrainingCourseDao.getAssignList_result_entry(department,lineTrainingCourse,assign,getPageNow(), getPageSize());
		count = lineTrainingCourseDao.getAssignListSize_result_entry(department,lineTrainingCourse,assign);
		
		//学分和获得学分
//		for(LineTrainingCourseAssign assign:assignList){
//			total_credit = total_credit + assign.getLineTrainingCourse().getCredit();
//			if(assign.getIs_get_certificate() == 1)
//				total_get_credit = total_get_credit + as5sign.getLineTrainingCourse().getCredit();
//		}
		for(LineTrainingCourseAssign assign:lineTrainingCourseDao.getCredit_get(lineTrainingCourse.getId())){
			total_credit = total_credit + assign.getLineTrainingCourse().getCredit();
			if(assign.getIs_get_certificate() == 1)
				total_get_credit = total_get_credit + assign.getLineTrainingCourse().getCredit();
		}
		
		return "result_entry_success";
	}
	
	public String changeScore() throws ElException{
		lineTrainingCourseDao.changeScore(assign_id,assign_score);
		
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String changeCredit() throws ElException{
		lineTrainingCourseDao.changeCredit(assign_id,assign_credit);
		
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String changeIs_get_certificate() throws ElException{
		lineTrainingCourseDao.change_is_get_certificate(change_is_get_certificate,assign_id);
		
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String updateCredit() throws ElException{
		lineTrainingCourseDao.changeCredit(assign_id,assign_credit);
//		for(LineTrainingCourseAssign assign:lineTrainingCourseDao.getCredit_get(line_training_course_id)){
//			total_get_credit = total_get_credit + assign.getLineTrainingCourse().getCredit();
//		}
		for(LineTrainingCourseAssign assign:lineTrainingCourseDao.getCredit_get(line_training_course_id)){
			if(assign.getIs_get_certificate() == 1)
				total_get_credit = total_get_credit + assign.getLineTrainingCourse().getCredit();
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"total_get_credit\":" + total_get_credit + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String check_userIds() throws ElException{
		message = "";
		// 过滤去掉已经分配的学员
		List<Integer> userIdList = lineTrainingCourseDao.check_is_signed(line_training_course_id);
		if(userIds != null && !userIds.equals("")){
			String[] userIds_array = userIds.split(",");
			if(userIdList.size() > 0){
				outer:
				for(int i=0;i<userIds_array.length;i++){
					for(int j=0;j<userIdList.size();j++){
						if(userIdList.get(j) == Integer.parseInt(userIds_array[i]) ){
							break outer;
						}else{
							if(j == userIdList.size() - 1){
								if(i == userIds_array.length -1){
									message += userIds_array[i] ; 
								}else{
									message += userIds_array[i] + ",";
								}
							}else{
								continue;
							}
//							if(i == userIds_array.length -1){
//								message += userIds_array[i] ; 
//							}else{
//								message += userIds_array[i] + ",";
//							}
//							break;
						}
					}
				}
			}else{
				message = userIds;
			}
		}
		System.out.println(message);
		if(message.equals("")){
			message = "0";
		}else{
			if(message.substring(message.length()-1).equals(",")){
				message = message.substring(0, message.length()-1);
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"message\":" + message + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String check_is_signed() throws ElException{
		boolean result = false;
		List<Integer> userIdList = lineTrainingCourseDao.check_is_signed(assign_id);
		
		for(Integer userId:userIdList){
			if(getSessionIntValue(ElConstants.SESSION_USERID) == userId){
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
	
	public String accessory_update() throws ElException{
		if(st == null){  
			setElmessage("请选择需要上传的附件！");
			return "error";
		}
		if (st.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "accessory_update";
		} else {
			String ext = J2EEFileUtil.getExtention(stFileName);
			assign.setAccessory(ext);
			lineTrainingCourseDao.updateLineTrainingCourseAssignById(assign);
//			if(!ext.equals("jsp")){ 
//				setElmessage("模板文件格式只能为jsp！");
//				return "accessory_update";
//			}
//			assign = lineTrainingCourseDao.getLineTrainingCourseAssignById(assign.getId());
			try {
				J2EEFileUtil.upload_was(st, ext, "images/lineTrainingCourse/accessory", assign.getId()+ "_accessory_" + assign.getLine_training_course_id()+"_"+assign.getUserId());
//				J2EEFileUtil.upload(st, ext, "lineTrainingCourse\\accessory", assign.getId()+ "_accessory_" + assign.getLine_training_course_id()+"_"+assign.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return "accessory_update";
	}
	
	public String downloadInit() throws ElException{
		try {
			getInputStream(); 
		} catch (Exception e) {
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		return "download";
	}
	
	public InputStream getInputStream() throws ElException {
		fileName = fileName ==null || fileName.equals("") ? "" : fileName;
		InputStream is = null;
		String path=ServletActionContext.getServletContext().getRealPath("images\\lineTrainingCourse\\accessory\\"+fileName);
		try {
			System.out.println(path);
			is = new FileInputStream(path);
		} catch (Exception e) {
			throw new ElException("下载资料出错",e);
		}
        return is;
	}
	
	public String allocation_person() throws ElException{
		lineTrainingCourse = lineTrainingCourseDao.getLineTrainingCourseById(null,lineTrainingCourse.getId());
		int depid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		} else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department==null||department.getId()<=0){
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		if (sub_department == 1) {
			department.setLower(true);
		}
		elUsers = lineTrainingCourseDao.getDistributionStudents(lineTrainingCourse,department, depid, elUser,
				getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(),
				getPageSize());
		if (elUsers != null) {
			for (int i = 0; i < elUsers.size(); i++) {
				//判断是否已被分配
				elUsers.get(i).setIntroom(lineTrainingCourseDao.check_signed(lineTrainingCourse.getId(),elUsers.get(i).getId()));
				// 设置学员的参加方式
				elUsers.get(i).setJoinwayInt(lineTrainingCourseDao.check_joinWay(lineTrainingCourse.getId(),elUsers.get(i).getId()));
			}
		}
		count = lineTrainingCourseDao.getDistributionStudentsCount(lineTrainingCourse,department, depid,
				elUser, getSessionIntValue(ElConstants.SESSION_ROLE));
		return "allocation_person";
	}
	
	public String option_in_allocation() throws ElException{
		if(userIds != null && !userIds.equals("")){
			String[] userIds_arr = userIds.split(",");
			if(option != null){//分配人员
				if(option.equals("add")){
					for(int i=0;i<userIds_arr.length;i++){
						message = lineTrainingCourseDao.signByPerson(option,lineTrainingCourse,Integer.parseInt(userIds_arr[i]));
						if(message != null && !message.equals("")){
							break;
						}
					}
				}else if(option.equals("remove")){//移除人员
					for(int i=0;i<userIds_arr.length;i++){
						message = lineTrainingCourseDao.removePerson(option,lineTrainingCourse,Integer.parseInt(userIds_arr[i]));
						if(message != null && !message.equals("")){
							break;
						}
					}
				}
			}
		}
		setElmessage(message);
		return "option_in_allocation_success";
	}
	
	//个人学籍下的线下培训查询
	public String personal_lineTrainingCourse() throws ElException{
		trainTypes = lineTrainingCourseDao.getTrainTypes();
		lineTrainingCourseList = lineTrainingCourseDao.personal_lineTrainingCourseList(lineTrainingCourse,getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(), getPageSize(),starttime,endtime);
		count = lineTrainingCourseDao.personal_lineTrainingCourseListSize(lineTrainingCourse,getSessionIntValue(ElConstants.SESSION_USERID),starttime,endtime);
		return "personal_lineTrainingCourse";
	}
	
	public String show_personal_lineTrainingCourse() throws ElException{
		lineTrainingCourse = lineTrainingCourseDao.getLineTrainingCourseById_personal(getSessionIntValue(ElConstants.SESSION_USERID),id);
		return "show_personal_lineTrainingCourse";
	}
	
	public String trainTypeTreeList() throws ElException{
		trainTypeTree = lineTrainingCourseTreeDao.getTraintypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		return "trainTypeTreeList";
	}
	
	public String trainType_addInit() throws ElException{
		trainTypeTree = lineTrainingCourseTreeDao.getTraintypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		if(trainTypeTree.getChild().size() == 0  && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的线下培训类别");
			 return "error"; 
		}
		return "trainType_addInit";
	}
	
	public String trainType_add() throws ElException{
		lineTrainingCourseTreeDao.addTraintype(ptype);
		
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).updatetlrid("line_training_type");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
		ElLoggerConstants.LOG_MOD_PRODUCTLIB,
		ElLoggerConstants.LOG_TYPE_ADD, ptype.getName(),
		ElLoggerConstants.LOG_RES_SUCC, ptype.getId());
		return "trainType_add_success";
	}
	
	public String trainType_alterInit()  throws ElException{
		trainTypeTree = lineTrainingCourseTreeDao.getTraintypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		ptype = lineTrainingCourseTreeDao.getTraintypeByid(ptype.getId());
//		ptype.setOpusers(productDao.getOpUsers( ptype
//					.getId()));
		return "trainType_alterInit";
	}
	
	public String trainType_alter() throws ElException{
		trainTypeTree = lineTrainingCourseTreeDao.getTraintypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		lineTrainingCourseTreeDao.alterTrainType(ptype);

		ptype=lineTrainingCourseTreeDao.getTraintypeByid(ptype.getId());
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_USERINFO);
		if(ptype!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_PRODUCTLIB,
					ElLoggerConstants.LOG_TYPE_ALTER, ptype.getName(),
					ElLoggerConstants.LOG_RES_SUCC,ptype.getId());
		}
		return "trainType_alter_success";
	}
	
	public String trainType_view() throws ElException{
		trainTypeTree = lineTrainingCourseTreeDao.getTraintypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		ptype = lineTrainingCourseTreeDao.getTraintypeByid(ptype.getId());
		
//		ptype.setOpusers(productDao.getOpUsers( ptype
//				.getId()));
		return "trainType_view";
	}
	
	public String trainType_delete() throws ElException{
		ptype = lineTrainingCourseTreeDao.getTraintypeByid(ptype.getId());
		if(productIsDel == 1){
			//并入上级	3	1
			lineTrainingCourseTreeDao.updateProductTypeParentid(ptype.getId(), ptype.getParent().getId());
			lineTrainingCourseTreeDao.updateProductParentid(ptype.getId(), ptype.getParent().getId());
			lineTrainingCourseTreeDao.deletePtype(ptype.getId());
		}else{
			//一起删除
			lineTrainingCourseTreeDao.deleteProductTypeAndSub(ptype.getId(),ptype.getParent().getId());
		}
//		更新新闻树左右id
//		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("newstype");
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_USERINFO);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_PRODUCTLIB,
				ElLoggerConstants.LOG_TYPE_DELETE, ptype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,ptype.getId());
		return "trainType_delete";
	}
	
	public String searchtypeInit() throws ElException{
		trainTypeTree = lineTrainingCourseTreeDao.getTraintypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		return "searchtypeInit";
	}
	
	public String addTypeView() throws ElException{
		if("ajax".equals(optype)){
			ptype = lineTrainingCourseTreeDao.getTraintypeByid(ptype.getId());
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				String d= "{\"id\":\"" + ptype.getId() + "\",\"name\":\"" + ptype.getName()
								+  "\"}";
				localPrintWriter.println(d);
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		trainTypeTree = lineTrainingCourseTreeDao.getTraintypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		return "success";
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public LineTrainingCourseDao getLineTrainingCourseDao() {
		return lineTrainingCourseDao;
	}

	public void setLineTrainingCourseDao(LineTrainingCourseDao lineTrainingCourseDao) {
		this.lineTrainingCourseDao = lineTrainingCourseDao;
	}

	public List<LineTrainingCourse> getLineTrainingCourseList() {
		return lineTrainingCourseList;
	}

	public void setLineTrainingCourseList(
			List<LineTrainingCourse> lineTrainingCourseList) {
		this.lineTrainingCourseList = lineTrainingCourseList;
	}

	public LineTrainingCourse getLineTrainingCourse() {
		return lineTrainingCourse;
	}

	public void setLineTrainingCourse(LineTrainingCourse lineTrainingCourse) {
		this.lineTrainingCourse = lineTrainingCourse;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<TrainType> getTrainTypes() {
		return trainTypes;
	}

	public void setTrainTypes(List<TrainType> trainTypes) {
		this.trainTypes = trainTypes;
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

	public boolean isLine_training_course_add_need_sh() {
		return line_training_course_add_need_sh;
	}

	public void setLine_training_course_add_need_sh(
			boolean line_training_course_add_need_sh) {
		this.line_training_course_add_need_sh = line_training_course_add_need_sh;
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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<LineTrainingCourse> getAllLineTrainingCourseList() {
		return allLineTrainingCourseList;
	}

	public void setAllLineTrainingCourseList(
			List<LineTrainingCourse> allLineTrainingCourseList) {
		this.allLineTrainingCourseList = allLineTrainingCourseList;
	}

	public int getNot_open() {
		return not_open;
	}

	public void setNot_open(int not_open) {
		this.not_open = not_open;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public List<LineTrainingCourseAssign> getAssignList() {
		return assignList;
	}

	public void setAssignList(List<LineTrainingCourseAssign> assignList) {
		this.assignList = assignList;
	}

	public LineTrainingCourseAssign getAssign() {
		return assign;
	}

	public void setAssign(LineTrainingCourseAssign assign) {
		this.assign = assign;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getTotal_credit() {
		return total_credit;
	}

	public void setTotal_credit(double total_credit) {
		this.total_credit = total_credit;
	}

	public double getTotal_get_credit() {
		return total_get_credit;
	}

	public void setTotal_get_credit(double total_get_credit) {
		this.total_get_credit = total_get_credit;
	}

	public int getAssign_id() {
		return assign_id;
	}

	public void setAssign_id(int assign_id) {
		this.assign_id = assign_id;
	}

	public double getAssign_score() {
		return assign_score;
	}

	public void setAssign_score(double assign_score) {
		this.assign_score = assign_score;
	}

	public int getChange_is_get_certificate() {
		return change_is_get_certificate;
	}

	public void setChange_is_get_certificate(int change_is_get_certificate) {
		this.change_is_get_certificate = change_is_get_certificate;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LineTrainingCourseTreeDao getLineTrainingCourseTreeDao() {
		return lineTrainingCourseTreeDao;
	}

	public void setLineTrainingCourseTreeDao(
			LineTrainingCourseTreeDao lineTrainingCourseTreeDao) {
		this.lineTrainingCourseTreeDao = lineTrainingCourseTreeDao;
	}

	public TrainTypeTree getTrainTypeTree() {
		return trainTypeTree;
	}

	public void setTrainTypeTree(TrainTypeTree trainTypeTree) {
		this.trainTypeTree = trainTypeTree;
	}

	public TrainTypeTree getPtype() {
		return ptype;
	}

	public void setPtype(TrainTypeTree ptype) {
		this.ptype = ptype;
	}

	public double getAssign_credit() {
		return assign_credit;
	}

	public void setAssign_credit(double assign_credit) {
		this.assign_credit = assign_credit;
	}

	public int getLine_training_course_id() {
		return line_training_course_id;
	}

	public void setLine_training_course_id(int line_training_course_id) {
		this.line_training_course_id = line_training_course_id;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	public int getProductIsDel() {
		return productIsDel;
	}

	public void setProductIsDel(int productIsDel) {
		this.productIsDel = productIsDel;
	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
