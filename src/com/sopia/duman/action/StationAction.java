package com.sopia.duman.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyCourse;

public class StationAction extends BaseAction{
	private static final Log logger = LogFactory.getLog(StationAction.class);
	private Station stTree;
	private List<Station> stpsp;
	private Station station;
	private IndexDataUtil indexDataUtil;
	private List<Station> stations;
	private String optype;
	private List<ELUser> elUsers;
	private int sub_operate;
	private ELUser elUser;
	public RoleDao roleDao;
	private List<Course> courses;
	private CourseType ctype;
	private Course course;
	private CourseDao courseDao; 
	private CourseType ctypeTree;
	private CourseTypeDao ctypeDao;
	private String ids;
	private int staid;
	private List<ExamRoom> examRooms;
	private EroomDao eroomDao;
	private List<ELUser> users;
	private int classid;
	private int status;
	private List<ExamPaper> exampapers;
	private StudyQuizDao studyQuizDao;
	private List<MyCourse> myCourses;
	private MyCourse myCourse;
	private String staid2;
	private int roleid;
	
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public String getStaid2() {
		return staid2;
	}
	public void setStaid2(String staid2) {
		this.staid2 = staid2;
	}
	public List<MyCourse> getMyCourses() {
		return myCourses;
	}
	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}
	public MyCourse getMyCourse() {
		return myCourse;
	}
	public void setMyCourse(MyCourse myCourse) {
		this.myCourse = myCourse;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public EroomDao getEroomDao() {
		return eroomDao;
	}
	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}
	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}
	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}
	public int getStaid() {
		return staid;
	}
	public void setStaid(int staid) {
		this.staid = staid;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public CourseDao getCourseDao() {
		return courseDao;
	}
	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public CourseType getCtype() {
		return ctype;
	}
	public void setCtype(CourseType ctype) {
		this.ctype = ctype;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
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
	public int getSub_operate() {
		return sub_operate;
	}
	public void setSub_operate(int sub_operate) {
		this.sub_operate = sub_operate;
	}
	public List<ELUser> getElUsers() {
		return elUsers;
	}
	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
	}
	public List<Station> getStations() {
		return stations;
	}
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}
	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public Station getStTree() {
		return stTree;
	}
	public void setStTree(Station stTree) {
		this.stTree = stTree;
	}
	public List<Station> getStpsp() {
		return stpsp;
	}
	public void setStpsp(List<Station> stpsp) {
		this.stpsp = stpsp;
	}
	/***
	 * 岗位列表
	 * @return
	 * @throws ElException
	 */
	
	public String station_list()throws Exception{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		roleid = getSessionIntValue(ElConstants.SESSION_ROLE);
		stpsp = stationDao.getStByIssp();
		return "station_list";
	}
	
	
	/**
	 * 岗位添加初始化
	 * 
	 * @return
	 * @throws ElException
	 */
	public String station_addInit() throws ElException {
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (stTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的部门类别");
			return "error";
		}
		return "sta_add";
	}
	
	
	/**
	 * 岗位添加
	 * 
	 * @return
	 * @throws ElException
	 */
	public String sta_add() throws ElException {
		// 先检测部门编号是否存在
		if (stationDao.checkStBh(station.getBh())) {
			setElmessage("该岗位编号已经存在，请重新选择。");
			return this.station_addInit();
		}
		if (station.getParent()==null||station.getParent().getId()<=0) {
			setElmessage("请选择有效的上级节点");
			return this.station_addInit();
		}
		if (station.getParent() == null) {
			// 因为ajax树有点缺陷
			station.setParent(new ElNode(station.getId()));
		}
		stationDao.addSt(station);
		((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
		.updatetlrid("station");
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_ST);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_STATION,
				ElLoggerConstants.LOG_TYPE_ADD, station.getName(),
				ElLoggerConstants.LOG_RES_SUCC, station.getId());// **//**//

		return "sta_add_success";
	}
	
	public String list_sta_childs() throws ElException {

		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			stations = stationDao.liststChildsByPId(station.getId());
			String d = "[";
			if (null != stations && stations.size() > 0) {
				for (int i = 0; i < stations.size(); i++) {
					Station dep = stations.get(i);
					String name = dep.getName();
					if (name != null)
						name = name.replaceAll("\"", "\\\\\"");
					d += "{\"id\":\"" + dep.getId() + "\",\"name\":\"" + name
							+ "\",\"bh\":\"" + dep.getBh() + "\",\"ccnt\":\""
							+ dep.getClassCount() + "\",\"lid\":\""
							+ dep.getLid() + "\",\"rid\":\""
							+ dep.getRid() + "\"},";
				}
				d = d.length() > 0 ? d.substring(0, d.length() - 1) : d;
				d += "]";
			} else
				d += "]";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("获取下级部门错误",e);
		}
		return null;
	}
	
	
	public String sta_view() throws ElException {
		if("ajax".equals(optype)){
			station = stationDao.getStById(station.getId());
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				String d= "{\"id\":\"" + station.getId() + "\",\"name\":\"" + station.getName()
								+ "\",\"bh\":\"" + station.getBh() + "\"}";
				localPrintWriter.println(d);
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				logger.error("ajax岗位查看错误",e);
			}
			return null;
		}
		if(station==null||station.getId()<=0)
		{	
			setElmessage("您需要查看的岗位不存在,请重新选择！");
			return "error";
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		station = stationDao.getStById(station.getId());
		station.setOpusers(stationDao
				.getOpUsers("op", station.getId()));
//		department.setUseusers(departmentDao.getOpUsers("use", department
//				.getId()));
		return "sta_view";
	}
	
	public String sta_alterInit() throws ElException {
		if(station==null||station.getId()<=0){
			setElmessage("请选择有效的节点");
			return "error";
		}
		station = stationDao.getStById(station.getId());
		station.setParent(stationDao.getStById(station.getParent().getId()));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			if(stTree!=null&&stTree.getChild()!=null)
				for (int i = 0; i < stTree.getChild().size(); i++) {
					if(station.getId()==stTree.getChild().get(i).getId()){
						setElmessage("被分配的节点（二级节点）不容许修改，选择子节点");
						return "error";
					}
				}
		}
		elUsers = userDao.getEUsByDepid(station.getId());
		station.setOpusers(stationDao
				.getOpUsers("op", station.getId()));
//		department.setUseusers(departmentDao.getOpUsers("use", department
//				.getId()));
		if (stTree!=null&&stTree.getChild()!=null&&stTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的部门类别");
			return "error";
		}
		return "sta_alter";
	}
	
	public String sta_alter() throws ElException {
		// 先检测岗位编号是否存在
		// 先查出本岗位的编号，因为要排除他
		Station d = stationDao.getStById(station.getId());
		if (!d.getBh().equals(station.getBh())) {
			if (stationDao.checkStBh(station.getBh())) {
				setElmessage("该岗位编号已经存在，请重新选择。");
				return this.sta_alterInit();
			}
		}
		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
		if(ens.checkNodeisChild(station.getId(), station.getParent().getId(), "station")){
			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
			return this.sta_alterInit();
		}
		stationDao.alterSta(station); // hec 注释
		//若父节点发生变化则去整理整个树的左右id
		if(station.getParent().getId()!=d.getParent().getId()){
			ens.updatetlrid("station");
		}
//		userDao.setEURole(department.getManager().getId(), 2);
//		if (null != department.getOpusers()) {
//			for (int i = 0; i < department.getOpusers().size(); i++) {
//						+ department.getOpusers().get(i).getId());
//				if (!departmentDao.checkOpUsers("op", department.getOpusers()
//						.get(i).getId(), department.getId()))
//					departmentDao.addOpusers("op", department.getOpusers().get(
//							i).getId(), department.getId());
//				roleDao.setUserfunc(department.getOpusers().get(i).getId(),
//						"dep_list", 0);
//				roleDao.setUserfunc(department.getOpusers().get(i).getId(),
//						"admin", 0);
//				roleDao.setUserfunc(department.getOpusers().get(i).getId(),
//						"account_searchInit", 0);
//			}
//		}
//		if (null != department.getUseusers()) {
//			for (int i = 0; i < department.getUseusers().size(); i++) {
//						+ department.getUseusers().get(i).getId());
//				if (!departmentDao.checkOpUsers("use", department.getUseusers()
//						.get(i).getId(), department.getId()))
//					departmentDao.addOpusers("use", department.getUseusers()
//							.get(i).getId(), department.getId());
//			}
//		}
indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_DEP);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_STATION,
				ElLoggerConstants.LOG_TYPE_ALTER, station.getName(),
				ElLoggerConstants.LOG_RES_SUCC, station.getId());
		return "sta_alter_success";
	}
	
	public String sta_deleteInit() throws ElException {
		if(station.getId()==1){
			setElmessage("不能删除根岗位");
			return "error";
		}
		if (getSessionIntValue(ElConstants.SESSION_MYSTATION) == station
				.getId()) {
			return "noright";
		}
		station = stationDao.getStById(station.getId());
		return "sta_delete";
	}
	
	public String sta_delete() throws ElException {
		if(station.getId()==1){
			setElmessage("不能删除根部门");
			return "error";
		}
		if (getSessionIntValue(ElConstants.SESSION_MYSTATION) == station
				.getId()) {
			return "noright";
		}
		if (sub_operate == 0) {
			// 并入上级部门(首先获取该节点的父节点，然后更新该节点的子节点的父节点为该节点的父节点，然后更新该节点下的人员到该节点的父节点,最后删除该节点)
			station = stationDao.getStById(station.getId());
			stationDao.deleteSta(station.getId(), station.getParent()
					.getId());
		} else {
			// 与本部门同时删除
//			departmentDao.deleteDepAndSub(department.getId());
			stationDao.deleteStaAndSubNot(station.getId());
		}
		station = stationDao.getStById(station.getId());
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
		.updatetlrid("station");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_STATION,
				ElLoggerConstants.LOG_TYPE_DELETE, station.getName(),
				ElLoggerConstants.LOG_RES_SUCC, station.getId());
		return "sta_delete_success";
	}
	
	public String sta_delete_user() throws ElException {
		stationDao.deleteOpusers(optype, elUser.getId(), station.getId());
		roleDao.checkUserfunc(elUser.getId(), "account_searchInit",
				"station_op_user");
		roleDao.checkUserfunc(elUser.getId(), "station_list", "station_op_user");
		roleDao.checkUserfunc(elUser.getId(), "admin", "station_op_user");
		roleDao.checkUserfunc(elUser.getId(), "account_searchInit",
				"station_op_user");
		// roleDao.checkUserfunc(getSessionIntValue(ElConstants.SESSION_USERID),"dep_list","department_op_user");
		// roleDao.checkUserfunc(getSessionIntValue(ElConstants.SESSION_USERID),"admin","department_op_user");

		return null;
	}
	
	public String sta_addCourseInit() throws ElException{
		classid =this.getClassid();
		int id = this.getStaid();
		String s = this.getStaid2();
		if(s==null||s.equals("")){
			staid = this.getStaid();
		}else{
			staid2 = s.substring(0, s.indexOf("."));
			staid = Integer.parseInt(staid2);
		}
		
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)  
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		if(ctype!=null&&ctype.getId()>0&&!((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).checkNode(ctype.getId(), ctypeTree, "course_type")){
			setElmessage("您无权访问此节点");
			return "error";
		}
		ctype =ctype == null||ctype.getId()<=0? ctypeTree : ctypeDao.getCtypeById(ctype.getId()); 
		
		String name = course == null ? "" : course.getName(); 
		courses = courseDao.listAllCourseFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,
				getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9");
		count = courseDao.listAllCourseSizeFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,"0,1,2,3,4,5,6,7,8,9");
//		courses = stationDao.getCourseList();
//		count = stationDao.getCourseCount();
		return "sta_addCourseInit";
	}
	
	
	public String sta_addCourseInit_bx() throws ElException{
		classid =this.getClassid();
		staid = this.getStaid();
		station = stationDao.getStById(staid);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)  
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		if(ctype!=null&&ctype.getId()>0&&!((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).checkNode(ctype.getId(), ctypeTree, "course_type")){
			setElmessage("您无权访问此节点");
			return "error";
		}
		ctype =ctype == null||ctype.getId()<=0? ctypeTree : ctypeDao.getCtypeById(ctype.getId()); 
		
		String name = course == null ? "" : course.getName(); 
		courses = courseDao.listAllCourseFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,
				getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9");
		count = courseDao.listAllCourseSizeFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,"0,1,2,3,4,5,6,7,8,9");
//		courses = stationDao.getCourseList();
//		count = stationDao.getCourseCount();
		return "sta_addCourseInit_bx";
	}
	
	public String sta_addCourseInit_xx() throws ElException{
		classid =this.getClassid();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)  
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		if(ctype!=null&&ctype.getId()>0&&!((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).checkNode(ctype.getId(), ctypeTree, "course_type")){
			setElmessage("您无权访问此节点");
			return "error";
		}
		ctype =ctype == null||ctype.getId()<=0? ctypeTree : ctypeDao.getCtypeById(ctype.getId()); 
		
		String name = course == null ? "" : course.getName(); 
		courses = courseDao.listAllCourseFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,
				getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9");
		count = courseDao.listAllCourseSizeFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,"0,1,2,3,4,5,6,7,8,9");
//		courses = stationDao.getCourseList();
//		count = stationDao.getCourseCount();
		return "sta_addCourseInit_xx";
	}
	
	public CourseType getCtypeTree() {
		return ctypeTree;
	}
	public void setCtypeTree(CourseType ctypeTree) {
		this.ctypeTree = ctypeTree;
	}
	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}
	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}
	
	public String sta_addCourse()throws ElException{
		staid = this.getStaid();
		String[] courseIds=this.getIds().split(",");
		int classid = this.getClassid();
		for(int i=0;i<courseIds.length;i++){
			int courseid = Integer.parseInt(courseIds[i]);
			int jieyeid = courseid%10;
			
			courseid = courseid-jieyeid;
			courseid = courseid/10;
			course = courseDao.getCourseById(courseid);
			
			examRooms = eroomDao.listExamRoom2(courseid, 0);
			if(jieyeid==2||jieyeid==3){
				if(examRooms.size()==0){
					String coursename = course.getName();
					String jieye = null;
					if(jieyeid==2){
						jieye = "考过";
					}else{
						jieye = "学完且考过";
					}
					this.setElmessage(coursename+"课程的结业方式为"+jieye+",该课程当前还没有安排结业考场,请调整");
					return "error";
				}
			}
			
			if(examRooms.size()!=0){
				ExamRoom er = examRooms.get(examRooms.size()- 1);
				int examRoomid = er.getId();
				users = stationDao.listUser(staid);
				for(ELUser u : users){
					int userid = u.getId();
					//添加考场
					stationDao.addUserRoom(examRoomid,userid,classid);
					courseDao.assignedUser2(courseid, userid,status,course.getRoomstart(),course.getRoomend(),examRoomid,classid,jieyeid);
					//添加试卷
					
					 
					exampapers = stationDao.getAllExamPaper(examRoomid);
					/**
					 * if(exampapers!=null){
						int sortid=0;
						for(int s = 0;s<exampapers.size();s++){
							int epid = exampapers.get(s).getId();
							ExamPaper ep = exampapers.get(s);
							if (ep != null && epid != 0)
								if (!eroomDao.checkEroomeps(examRoomid, epid)
										&& epid != 0)
									sortid++;
									eroomDao.addEroomeps(examRoomid, epid, 0,
											ep.getPractimes(), ep.getPracscore(), ep
													.getPassgrade(), ep.getStuview(), ep
													.getQuizlook(), ep.getScorelook(), ep
													.getQuizcount(), ep.getPassmanner(),sortid);
						}
					}*/
					for(int s=0;s<exampapers.size();s++){
						studyQuizDao.addStudyExamPaper(userid, exampapers.get(s)
								.getId(), examRoomid, classid);
					}
					
				}
			}else{
				this.setElmessage("课程没有绑定考场!!!");
				return "error";
			}
			//stationDao.addCourse(courseid,jieyeid,staid,classid);
			stationDao.addStationCourse(staid,courseid,jieyeid,classid);
		}
		return "sta_addCourse";
		
	}
	public List<ELUser> getUsers() {
		return users;
	}
	public void setUsers(List<ELUser> users) {
		this.users = users;
	}
	
	
	/**
	 * 提交岗位课程
	 */
	public String sta_addAll()throws ElException{
		
		stpsp = stationDao.getAllSta();
		for(int i=0;i<stpsp.size();i++){
			int staid = stpsp.get(i).getId();
			users = stationDao.listUser(staid);
			courses = stationDao.getCourseList(staid);
			for(int j=0;j<users.size();j++){
				int userid = users.get(j).getId();
				int classid = stationDao.getClassid(userid);
				if(classid !=-2&&classid !=-3){
					for(int s=0;s<courses.size();s++){
						//添加课程
						stationDao.addCourse2(courses.get(s).getId(),courses.get(s).getJieye(),userid,courses.get(s).getClassid());
						ExamRoom er = stationDao.getExamRoom(courses.get(s).getId());
						//添加考场
						stationDao.addUserRoom(er.getId(),userid,courses.get(s).getClassid());
						//添加试卷
						exampapers = stationDao.getAllExamPaper(er.getId());
						for(int m=0;m<exampapers.size();m++){
							studyQuizDao.addStudyExamPaper(userid, exampapers.get(m)
									.getId(), er.getId(), classid);
						}
					}
					
				}
			}
			
		}
		this.setElmessage("提交成功！");
		return "error";
	}
	public List<ExamPaper> getExampapers() {
		return exampapers;
	}
	public void setExampapers(List<ExamPaper> exampapers) {
		this.exampapers = exampapers;
	}
	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}
	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}
	
	
	
	public String stageneral()throws ElException{
		staid = station.getId();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		station = stationDao.getStById(staid);
		
		int count = stationDao.getStationCourse(staid);
		station.setCount(count);
		int bixiu = stationDao.getBiXiuCourse(staid,-2);
		int brc = bixiu*count;
		station.setBrc(brc);
		int bscore = stationDao.getBiXiuScore(staid,-2)*count;
		station.setBscore(bscore);
		return "stageneral";
	}
	
	/**
	 * 岗位课程列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String sta_course() throws ElException {
		staid = station.getId();
		List<Course> bxCourses = stationDao.getCourseList(staid);
		List<Course> xxCourses = stationDao.getCourseList(staid);
	//	elclass = classDao.getClassById(elclass.getId());
	//	bxCourses = classDao.listClassCourses(elclass.getId(),CourseConstants.COURSE_STUDY_STATUS_BX);
	//	xxCourses = classDao.listClassCourses(elclass.getId(),CourseConstants.COURSE_STUDY_STATUS_XX);
	//	int id = station.getId();
		
	//	ctype =ctype == null||ctype.getId()<=0? ctypeTree : ctypeDao.getCtypeById(ctype.getId()); 
	//	String name = course == null ? "" : course.getName(); 
	//	List<Course> bxCourses = courseDao.listAllCourseFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,
	//			getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9");
		return "sta_course";
	}
	
}
