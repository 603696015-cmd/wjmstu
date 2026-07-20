package com.sopia.courseman.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.JTM;
import com.sopia.common.JTMSystemConfOp;
import com.sopia.common.OnlineUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseAuditDescribes;
import com.sopia.courseman.entities.CourseNote;
import com.sopia.courseman.entities.CourseRegistration;
import com.sopia.courseman.entities.CourseRemarks;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.QuizPaper;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.BaseDataTypeCourse;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.BaseDatatCourse;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.forumman.dao.ForumAdminDao;
import com.sopia.forumman.entities.Forum;
import com.sopia.forumman.entities.ForumBlock;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.openmeetings.OmDao;
import com.sopia.openmeetings.OmUtil;
import com.sopia.openmeetings.Rooms;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.statman.dao.StatisticCourseDao;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyExamPaper;

public class CourseAction extends BaseAction {
	private CourseType ctype;
	private List<CourseType> ctypes;
	private CourseDao courseDao; 
	private StatisticCourseDao statisticCourseDao;
	private CourseTypeDao ctypeDao;
	private Course course;
	private CourseRemarks cRemarks;
	private List<Course> courses;
	private List<MyCourse> mycourses;
	private MyCourse mycourse;
	private int course_sourse;
	private List<ELUser> canAssignUsers;
	private List<ELUser> xassignedUsers;
	private List<ELUser> bassignedUsers;
	private List<ELUser> zassignedUsers;
	private int status;
	private int astatus;
	private List<Department> canAssignDeps;
	private List<Department> assignDeps;
	private List<ExamRoom> examRooms;
	private ExamRoom examRoom;
	private ExamRoom examRoom_bk;
	private List<ExamPaper> examPapers;
	private List<ELUser> supervisors;
	private List<MyExamPaper> myExamPapers;
	private MyExamPaper myExamPaper;
	private List<QuizPaper> quizPapers;
	private OmDao omDao;
	private String ids;//add by jiahaijiang
	private String Return;// 审核 返回路径
	private List<Course> mymultis;
	private CourseType ctypeTree;
	private CourseAuditDescribes courseAudit;

	private CourseRegistration coRegistration;
	private List<ElClass> elClasss;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private ElRole role;
	private List<ElRole> roles;
	private RoleDao roleDao;
	private String optype;
	Department canAssignDep;
	Department xassignedDep;
	Department bassignedDep;
	Department zassignedDep;
	private List<ELUser> elusers;
	private String starttime;
	private String endtime;
	private Integer deptid;
	private String userids;
	private Department depTree;
	private Department deprTree;
	private StudyQuizDao studyQuizDao;
	private List<ELUser> elUsers;
	private Department department;
	private int sub_department;
	private ELUser elUser;
	private List<MyCourse> myCourses;
	private StudyCourseDao studyCourseDao;
	private List<CourseNote> cnotes;
	private List<BaseDatat> dishis;
	private IndexDataUtil indexDataUtil;//首页数据显示帮助类
	
	private Map<String,String> statusMap;//课程状态
	
	private String shihegangwei;//适合岗位
	private String zhuanyeleibie;//专业类别
	private String zhuanyejibie;//专业级别
	private String shihebumen;//适合部门
	private String neirongleixing;//内容类型
	private String peixunleibie;//培训类别
	private String shihexuewei;//适合学位
	private String kechengxingzhi;//课程性质
	private List<BaseDatatCourse> shihegangweis;
	private List<BaseDatatCourse> zhuanyeleibies;
	private List<BaseDatatCourse> zhuanyejibies;
	private List<BaseDatatCourse> shihebumens;
	private List<BaseDatatCourse> neirongleixings;
	private List<BaseDatatCourse> peixunleibies;
	private List<BaseDatatCourse> shihexueweis;
	private List<BaseDatatCourse> kechengxingzhis; 
	private Station stTree;
	private Station station;
	
	private String weidu;//课程维度
	
	private int jieyeid;//课程结业方式
	
	private List<ForumBlockType> fbtypes;
	private ForumAdminDao forumAdminDao;
	private List<Forum> jhforums;
	private List<Forum> rmforums;
	private List<Forum> zxforums;
	private ForumBlock fblock;
	private Forum forum;
	
	private HttpRequestDeviceUtils httpRequestDeviceUtils;
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public int getJieyeid() {
		return jieyeid;
	}

	public void setJieyeid(int jieyeid) {
		this.jieyeid = jieyeid;
	}

	public String getWeidu() {
		return weidu;
	}

	public void setWeidu(String weidu) {
		this.weidu = weidu;
	}

	public String getShihegangwei() {
		return shihegangwei;
	}

	public void setShihegangwei(String shihegangwei) {
		this.shihegangwei = shihegangwei;
	}

	public String getZhuanyeleibie() {
		return zhuanyeleibie;
	}

	public void setZhuanyeleibie(String zhuanyeleibie) {
		this.zhuanyeleibie = zhuanyeleibie;
	}

	public String getZhuanyejibie() {
		return zhuanyejibie;
	}

	public void setZhuanyejibie(String zhuanyejibie) {
		this.zhuanyejibie = zhuanyejibie;
	}

	public String getShihebumen() {
		return shihebumen;
	}

	public void setShihebumen(String shihebumen) {
		this.shihebumen = shihebumen;
	}

	public String getNeirongleixing() {
		return neirongleixing;
	}

	public void setNeirongleixing(String neirongleixing) {
		this.neirongleixing = neirongleixing;
	}

	public String getPeixunleibie() {
		return peixunleibie;
	}

	public void setPeixunleibie(String peixunleibie) {
		this.peixunleibie = peixunleibie;
	}

	public String getShihexuewei() {
		return shihexuewei;
	}

	public void setShihexuewei(String shihexuewei) {
		this.shihexuewei = shihexuewei;
	}

	public String getKechengxingzhi() {
		return kechengxingzhi;
	}

	public void setKechengxingzhi(String kechengxingzhi) {
		this.kechengxingzhi = kechengxingzhi;
	}

	public List<BaseDatatCourse> getShihegangweis() {
		return shihegangweis;
	}

	public void setShihegangweis(List<BaseDatatCourse> shihegangweis) {
		this.shihegangweis = shihegangweis;
	}

	public List<BaseDatatCourse> getZhuanyeleibies() {
		return zhuanyeleibies;
	}

	public void setZhuanyeleibies(List<BaseDatatCourse> zhuanyeleibies) {
		this.zhuanyeleibies = zhuanyeleibies;
	}

	public List<BaseDatatCourse> getZhuanyejibies() {
		return zhuanyejibies;
	}

	public void setZhuanyejibies(List<BaseDatatCourse> zhuanyejibies) {
		this.zhuanyejibies = zhuanyejibies;
	}

	public List<BaseDatatCourse> getShihebumens() {
		return shihebumens;
	}

	public void setShihebumens(List<BaseDatatCourse> shihebumens) {
		this.shihebumens = shihebumens;
	}

	public List<BaseDatatCourse> getNeirongleixings() {
		return neirongleixings;
	}

	public void setNeirongleixings(List<BaseDatatCourse> neirongleixings) {
		this.neirongleixings = neirongleixings;
	}

	public List<BaseDatatCourse> getPeixunleibies() {
		return peixunleibies;
	}

	public void setPeixunleibies(List<BaseDatatCourse> peixunleibies) {
		this.peixunleibies = peixunleibies;
	}

	public List<BaseDatatCourse> getShihexueweis() {
		return shihexueweis;
	}

	public void setShihexueweis(List<BaseDatatCourse> shihexueweis) {
		this.shihexueweis = shihexueweis;
	}

	public List<BaseDatatCourse> getKechengxingzhis() {
		return kechengxingzhis;
	}

	public void setKechengxingzhis(List<BaseDatatCourse> kechengxingzhis) {
		this.kechengxingzhis = kechengxingzhis;
	}

	public Map<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	public List<ElClass> getElClasss() {
		return elClasss;
	}

	public void setElClasss(List<ElClass> elClasss) {
		this.elClasss = elClasss;
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

	public List<BaseDatat> getGangweis() {
		return gangweis;
	}

	public void setGangweis(List<BaseDatat> gangweis) {
		this.gangweis = gangweis;
	}

	public List<BaseDatat> getDishis() {
		return dishis;
	}

	public void setDishis(List<BaseDatat> dishis) {
		this.dishis = dishis;
	}

	public CourseRegistration getCoRegistration() {
		return coRegistration;
	}

	public void setCoRegistration(CourseRegistration coRegistration) {
		this.coRegistration = coRegistration;
	}

	public CourseAuditDescribes getCourseAudit() {
		return courseAudit;
	}

	public void setCourseAudit(CourseAuditDescribes courseAudit) {
		this.courseAudit = courseAudit;
	}

	public List<Course> getMymultis() {
		return mymultis;
	}

	public void setMymultis(List<Course> mymultis) {
		this.mymultis = mymultis;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<MyExamPaper> getMyExamPapers() {
		return myExamPapers;
	}

	public void setMyExamPapers(List<MyExamPaper> myExamPapers) {
		this.myExamPapers = myExamPapers;
	}

	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}

	public List<ELUser> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<ELUser> supervisors) {
		this.supervisors = supervisors;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public List<Department> getAssignDeps() {
		return assignDeps;
	}

	public void setAssignDeps(List<Department> assignDeps) {
		this.assignDeps = assignDeps;
	}

	public List<Department> getCanAssignDeps() {
		return canAssignDeps;
	}

	public void setCanAssignDeps(List<Department> canAssignDeps) {
		this.canAssignDeps = canAssignDeps;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCourse_sourse() {
		return course_sourse;
	}

	public void setCourse_sourse(int course_sourse) {
		this.course_sourse = course_sourse;
	}

	public List<ELUser> getCanAssignUsers() {
		return canAssignUsers;
	}

	public void setCanAssignUsers(List<ELUser> canAssignUsers) {
		this.canAssignUsers = canAssignUsers;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String coursetype_list() throws ElException {
		//如果是超级管理员查询出所有节点 否则需要根据管理权限查询
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true);
//		}else{
//		ctypeTree =ctypeDao.getCtypeTreeByPerOrShar(
//		ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),false,"COURSE_OP_TYPE");
//		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "OP",ElConstants.TREE_FIANL, true);
		}

		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "coursetype_list_phone"; 
		}

		return "coursetype_list";
	}

	public String coursetype_deleteInit() throws ElException {
		if(ctype.getId()==1){
			setElmessage("不能删除根类别!");
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "error_phone"; 
			}

			return "error";
		}
		ctype = ctypeDao.getCtypeById(ctype.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "coursetype_delete_phone"; 
		}

		return "coursetype_delete";
	}

	public String coursetype_delete() throws ElException {
		// if (course_sourse == 1) {
		// // 并入上级
		//			
		// } else {
		// // 一起删除
		//
		// }
		//ctypeDao.deleteCtype(ctype.getId());
		if(ctype.getId()==1){
			setElmessage("不能删除根类别!");
			return "error";
		}
		if (course_sourse == 1) {
			//一起删除
//			ctypeDao.deleteCtypeAndSub(ctype.getId());
			ctypeDao.deleteCtypeAndSubNot(ctype.getId());
		} else {
			//并入上级
			ctypeDao.deleteCtype(ctype.getId());
		}
		ctype=ctypeDao.getCtypeById(ctype.getId());
		//刷新首页课程模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("course_type");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_COURSELIB,
				ElLoggerConstants.LOG_TYPE_DELETE,ctype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,ctype.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "coursetype_delete_success_phone"; 
		}

		return "coursetype_delete_success";
	}

	public String coursetype_addInit() throws ElException {
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true);
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT, ElConstants.TREE_FIANL,
//		false);
//		}else{
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),false,"COURSE_OP_TYPE");
//		}

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		if (ctypeTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的课程库");
			return "error";
		}
		return "coursetype_add";
	}

	public String coursetype_add() throws ElException {
		ctypeDao.addCtype(ctype);
		ctype=ctypeDao.getCtypeById(ctype.getId());
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("course_type");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_COURSELIB,
				ElLoggerConstants.LOG_TYPE_ADD,ctype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,ctype.getId());
		return "coursetype_add_success";
	}

	//	public String temp(List<CourseType> listCt,int n,boolean bool){
	//		for (int i = 0; i < listCt.size(); i++) {
	//			if(listCt.get(i).getChild().size()!=0){
	//				
	//				for (int j = 0; j < listCt.get(i).getChild().size(); j++) {
	//					temp(listCt.get(j).getChild(),n,bool);
	//				}
	//			}
	//			if(listCt.get(i).getName().equals(ctype.getName())){
	//				n=i;
	//				bool=true;
	//			}
	//		}
	//		if(bool==true){
	//			List<CourseType> list= ctypeTree.getChild();
	////			ElNode parent =list.get(n).getParent();//得到父节点
	////			for (int i = 0; i < list.get(n).getChild().size(); i++) {
	////				//循环把本该节点的所有右节点设置本节点的父节点
	////				CourseType tempCType=list.get(n).getChild().get(i);
	////				tempCType.setParent(parent);
	////				//new CourseTypeDaoImpl().alterCtype(tempCType);
	////			}
	//			list.remove(n);
	//			ctypeTree.setChild(list);
	//		}
	//		return "";
	//	}
	
		public String coursetype_alterInit() throws ElException {
			//超级管理员查询所有课程类别，否则按照管理权限查询
			ctype = ctypeDao.getCtypeById(ctype.getId());
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
				ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
			else { 
				ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "OP",ElConstants.TREE_FIANL, true);
			}
	//		//在此需要做个判定，本节点不能在树中显示出来，不然就可以选择自己做父节点了
	//		//changeNode();
	//		boolean bool=false;
	//		int n=0;
	//		List<CourseType> listCt= ctypeTree.getChild();
	//		//this.temp(listCt,n,bool);
	//		
			
			ctype.setOpusers(ctypeDao.getOpUsers("COURSE_OP_TYPE", ctype.getId()));
//			ctype.setUseusers(ctypeDao.getOpUsers("COURSE_USE_TYPE", ctype.getId()));
			return "coursetype_alter";
		}

	public String coursetype_alter() throws ElException {
			if (ctype.getId() == 1) {
				ctype.setParent(new CourseType(0));
			}
			if(ctype.getParent()==null){
				ctype.setParent(new ElNode(1));
			}
			ctypeDao.alterCtype(ctype);
	//		if (null != ctype.getOpusers()) {
	//			for (int i = 0; i < ctype.getOpusers().size(); i++) {
	//				//这样的写法不好，循环去操作数据会产生性能问题，由于时间关系先暂时参考试题库的代码这样处理（使用量不是很大的话也没什么问题）。
	//				//当出现性能问题时，可以把这段代码改掉，减少数据库的链接次数和做批量处理。备注：jiahaijiang
	//				if (!ctypeDao.checkOpUsers("COURSE_OP_TYPE", ctype.getOpusers()
	//						.get(i).getId(), ctype.getId()))
	//					ctypeDao.addOpusers("COURSE_OP_TYPE", ctype.getOpusers()
	//							.get(i).getId(), ctype.getId());
	//				roleDao.setUserfunc(ctype.getOpusers().get(i).getId(),
	//						"coursetype_list", 0);
	//				roleDao.setUserfunc(ctype.getOpusers().get(i).getId(),
	//						"coursetype_addInit", 0);
	////				roleDao.setUserfunc(ctype.getOpusers().get(i).getId(),
	////				"coursetype_listInit", 0);
	////				roleDao.setUserfunc(ctype.getOpusers().get(i).getId(),
	////				"coursetype_addInit", 0);
	//				roleDao.setUserfunc(ctype.getOpusers().get(i).getId(),
	//						"admin", 0);
	//			}
	//		}
	//		if (null != ctype.getUseusers()) {
	//			for (int i = 0; i < ctype.getUseusers().size(); i++) {
	//						+ ctype.getUseusers().get(i).getId());
	//				if (!ctypeDao.checkOpUsers("COURSE_USE_TYPE", ctype.getUseusers()
	//						.get(i).getId(), ctype.getId()))
	//					ctypeDao.addOpusers("COURSE_USE_TYPE", ctype.getUseusers()
	//							.get(i).getId(), ctype.getId());
	//			}
	//		}
			//刷新首页课程模块
			indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
			ctype=ctypeDao.getCtypeById(ctype.getId());
			((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("course_type");
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_COURSELIB,
					ElLoggerConstants.LOG_TYPE_ALTER,ctype.getName(),
					ElLoggerConstants.LOG_RES_SUCC,ctype.getId());
			return "coursetype_alter_success";
		}

	public String coursetype_view() throws ElException {
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true);
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT, 0,
//		false);
//		}else{
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),false,"COURSE_OP_TYPE");
//		}

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}

		ctype = ctypeDao.getCtypeById(ctype.getId());
//		ctype.setElUsers(ctypeDao.listUserByCtype(ctype.getId()));
		ctype.setOpusers(ctypeDao.getOpUsers("COURSE_OP_TYPE", ctype.getId()));
//		ctype.setUseusers(ctypeDao.getOpUsers("COURSE_USE_TYPE", ctype.getId()));
		return "coursetype_view";
	}
	
	/**
	 * 在修改功能中选择上级节点时不能显示本节点
	 * @param ctypeTree 树
	 */
	public String changeNode(){
		boolean bool=false;
		int n=0;
		for (int i = 0; i < ctypeTree.getChild().size(); i++) {
			if(ctypeTree.getChild().get(i).getName().equals(ctype.getName())){
				n=i;
				bool=true;
			}
		}
		if(bool==true){
			List<CourseType> list= ctypeTree.getChild();
//			ElNode parent =list.get(n).getParent();//得到父节点
//			for (int i = 0; i < list.get(n).getChild().size(); i++) {
//				//循环把本该节点的所有右节点设置本节点的父节点
//				CourseType tempCType=list.get(n).getChild().get(i);
//				tempCType.setParent(parent);
//				//new CourseTypeDaoImpl().alterCtype(tempCType);
//			}
			list.remove(n);
			ctypeTree.setChild(list);
		}
		return "";
	}
	
//	public String temp(List<CourseType> listCt,int n,boolean bool){
//		for (int i = 0; i < listCt.size(); i++) {
//			if(listCt.get(i).getChild().size()!=0){
//				
//				for (int j = 0; j < listCt.get(i).getChild().size(); j++) {
//					temp(listCt.get(j).getChild(),n,bool);
//				}
//			}
//			if(listCt.get(i).getName().equals(ctype.getName())){
//				n=i;
//				bool=true;
//			}
//		}
//		if(bool==true){
//			List<CourseType> list= ctypeTree.getChild();
////			ElNode parent =list.get(n).getParent();//得到父节点
////			for (int i = 0; i < list.get(n).getChild().size(); i++) {
////				//循环把本该节点的所有右节点设置本节点的父节点
////				CourseType tempCType=list.get(n).getChild().get(i);
////				tempCType.setParent(parent);
////				//new CourseTypeDaoImpl().alterCtype(tempCType);
////			}
//			list.remove(n);
//			ctypeTree.setChild(list);
//		}
//		return "";
//	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public String coursetype_delete_user() throws ElException {
		ctypeDao.deleteOpusers(optype, elUser.getId(), ctype.getId());
		roleDao.checkUserfunc( elUser.getId(),"coursetype_list","COURSE_OP_TYPE");
		roleDao.checkUserfunc( elUser.getId(),"coursetype_addInit","COURSE_OP_TYPE");
		roleDao.checkUserfunc( elUser.getId(),"admin","COURSE_OP_TYPE");

		return null;
	}

	public ElRole getRole() {
		return role;
	}

	public void setRole(ElRole role) {
		this.role = role;
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

	public String ctype_useraddInit() throws ElException {
		getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		ctype = ctypeDao.getCtypeById(ctype.getId());
//		int depid = department == null ? 1 : department.getId();
//		int roleid = role == null ? 0 : role.getId();
		elUser = elUser == null ? new ELUser() : elUser;
		// elUsers = userDao.getUserByDepId(depid, sub_department, elUser,
		// roleid,
		// getPageNow(), getPageSize());
		// count = userDao.getUserByDepIdSize(depid, sub_department, elUser,
		// roleid);
//		if (roleid == 0) {
//			elUsers = userDao.getUserByDepId(depid, sub_department, elUser,
//					getPageNow(), getPageSize());
//			count = userDao.getUserByDepIdSize(depid, sub_department, elUser);
//		} else {
//			elUsers = userDao.getUserByDepId(depid, sub_department, elUser,
//					roleid, getPageNow(), getPageSize());
//			count = userDao.getUserByDepIdSize(depid, sub_department, elUser,
//					roleid);
//
//		}
		if (null != elUsers) {
			for (int i = 0; i < elUsers.size(); i++) {
				elUsers.get(i).setIntroom(
						ctypeDao.checkCtypeUser(elUsers.get(i).getId(), ctype
								.getId()));
			}
		}
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		roles = roleDao.listRoles();
		return "ctype_useradd";
	}

	public String ctype_useradd() throws ElException {
		if (null != elUsers) {
			for (int i = 0; i < elUsers.size(); i++) {
				if (!ctypeDao.checkCtypeUser(elUsers.get(i).getId(), ctype
						.getId()))
					ctypeDao
					.addCtypeUser(elUsers.get(i).getId(), ctype.getId());
			}
		}
		return "ctype_useradd";
	}

	public String ctype_userdelete() throws ElException {
		ctypeDao.deleteCtypeUser(elUser.getId(), ctype.getId());
		return "ctype_userdelete";
	}

	/**
	 * 课程添加初始化
	 * 
	 * @return
	 * @throws ElException
	 */
	public String course_addInit() throws ElException {
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
//		else {
//		List<CourseType> cts = ctypeDao
//		.listCtypeByUser(getSessionIntValue(ElConstants.SESSION_USERID));
//		ctypeTree = new CourseType(0);
//		List<CourseType> ctsc = null;
//		// ctypeTree.setChild(new ArrayList<CourseType>());
//		if (null != cts) {
//		ctsc = new ArrayList<CourseType>();
//		for (int i = 0; i < cts.size(); i++) {
//		ctsc.add(ctypeDao.getCtypeTree(cts.get(i).getId(),
//		ElConstants.TREE_FIANL, true));
//		}
//		}
//		ctypeTree.setChild(ctsc);
//		}
		
		if (fblock == null) {
			fblock = new ForumBlock(1, "");
		}
		// fbtypes = forumAdminDao.listFbtypesWithBlocks();
		fbtypes = forumAdminDao.listFbtypes();

		if (null != fbtypes) {
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(
						forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		if(ctypeTree.getChild().size() == 0 && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的课程类别");
			 return "error"; 
		}
		
		//查基础数据类别 
		baseCourseTypeList=courseDao.getAllBaseDataTypeCourse();

		shihegangweis = courseDao.getBaseDatatCourseByTypeid(1);
		zhuanyeleibies = courseDao.getBaseDatatCourseByTypeid(2);
		zhuanyejibies = courseDao.getBaseDatatCourseByTypeid(3);
		shihebumens = courseDao.getBaseDatatCourseByTypeid(4);
		neirongleixings = courseDao.getBaseDatatCourseByTypeid(5);
		peixunleibies = courseDao.getBaseDatatCourseByTypeid(6);
		shihexueweis = courseDao.getBaseDatatCourseByTypeid(7);
		kechengxingzhis = courseDao.getBaseDatatCourseByTypeid(8); 
		
		course = new Course();
		return "course_add";
	}
	/**
	 * 课程复制
	 * @return
	 * @throws ElException
	 */
	public String course_copy() throws ElException {
		int status = courseDao.courseCopy(course.getId());
		if(status > 0){
			course.setId(status);
			return "course_copy";
		}else{
			setElmessage("复制课程错误！");
			return "erro";
		}
	}
	/**
	 * 课程添加
	 * 
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String course_add() throws ElException, UnsupportedEncodingException {
		//获取课程维度
		String[] weidu_array = getRequest().getParameterValues("weidu");
		weidu = "";
		if(weidu_array!=null&&weidu_array.length>0){
			for(int i=0;i<weidu_array.length;i++){
				if(i == weidu_array.length-1){
					weidu += weidu_array[i];
				}else{
					weidu += weidu_array[i] + ",";
				}
			}
		}
		
		course.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		 
		if(course.getCtype()!=null){
			Rooms room = course.getRoom()==null?new Rooms():course.getRoom();
			if(course.getIslink()==4){
				//添加同步课堂类型
				room.setRoomtype(2);
				room.setComment(course.getDescription());
				room.setName(course.getName());
				omDao.addOmRoom(room);
			}
			course.setRoom(room);
			course.setWeidu(weidu);
			courseDao.addCourse(course);
			
			course = courseDao.getCourseById(course.getId());
			if(course.getName()==null||course.getName().equals("")){
				this.setElmessage("课程名称不能为空");
				return "error";
			}
			
			boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
			if(open_jtm){
				//添加维度信息到JTM
				String cer = JTM.getJTM_cer(String.valueOf(course.getId()));
				boolean addSuccess = false;
				String JTM_URL = JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_COURSES_AYSCHRONIZATION_URL)+
				"?courseid="+course.getId()+
				"&coursename="+URLEncoder.encode(course.getName(), "GB2312")+
				"&url=http://www.google.com/"+
				"&dimid="+weidu+
				"&cer="+cer;
				
				Content c = null;
				try {
					c = Request.Get(JTM_URL).addHeader("Content-Type", "text/html; charset=UTF-8").execute().returnContent();
					String returnValue = c.asString();
					addSuccess = (returnValue!=null&&returnValue.equals("true"))?true:false;
					
					
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(!addSuccess){
					this.setElmessage("课程添加错误，添加维度信息到JTM出错!");
					return "error";
				}
			}
			

			if(course.getIsApplication()==1 && coRegistration != null) {//是否为可申请
				coRegistration.setCourse(course); 
				if(examRooms != null)
					coRegistration.setExamRoom(examRooms);
				if(elClasss != null)
					coRegistration.setElclass(elClasss);
				if(!courseDao.checkCourseRegistration(course.getId())){
					courseDao.addCourseRegistration(coRegistration);
				}else{
					courseDao.alterCourseRegistration(coRegistration);
				}
			} 
		}
		//刷新首页课程模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_COURSE,
				ElLoggerConstants.LOG_TYPE_ADD,course.getName(),
				ElLoggerConstants.LOG_RES_SUCC,course.getId());
		return	"course_list";//返回课程列表页
		
//		
//		
//		
//		return	"course_view";//返回课件制作首页
//		else{
//			return "course_add_failure";
//		}
//		return "course_add_success";
	}

	/**
	 *  课程终审列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String course_sh_list() throws ElException {
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true);
//		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
//		.getId();
//		String name = course == null ? "" : course.getName();
//		//结果集
//		courses = courseDao
//		.listAllCourseFromThis(ctid,getPageNow(), getPageSize());
//		//总记录数
//		count = courseDao.listShCourseSize(ctid,getPageNow(), getPageSize());
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		boolean isShared=false;
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			isShared=true;
		}
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),isShared,"COURSE_USE_TYPE");
//		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
//		.getId();

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}

//		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();  
//		String name = course == null ? "" : course.getName();
		course = course == null ? new Course():course;
		statusMap = course.getStatusMap();
//		courses = courseDao.listAllCourseFromThis(depid, name, ctid,
//		getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_OPEN_WAIT);
//		count = courseDao.listAllCourseSizeFromThis(depid, name, ctid,CourseConstants.COURSE_STATUS_OPEN_WAIT);
//		courses = courseDao.listAllCourseFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,
//				getPageNow(), getPageSize(),""+CourseConstants.COURSE_STATUS_FINAL_WAIT+","+CourseConstants.COURSE_STATUS_DELETE+"");
//		count = courseDao.listAllCourseSizeFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,""+CourseConstants.COURSE_STATUS_FINAL_WAIT+","+CourseConstants.COURSE_STATUS_DELETE+"");
		if(ctype==null||ctype.getId()<=0){
		  ctype=ctypeTree;
		}else{
		  ctype=ctypeDao.getCtypeById(ctype.getId());
		}
//		courses = courseDao.listCourseFromThisStatus(ctype,name,getPageNow(), getPageSize(),""+CourseConstants.COURSE_STATUS_FINAL_WAIT+","+CourseConstants.COURSE_STATUS_DELETE+"");
//		count = courseDao.listCourseSizeFromThisStatus(ctype,name,""+CourseConstants.COURSE_STATUS_FINAL_WAIT+","+CourseConstants.COURSE_STATUS_DELETE+"");
		
		courses = courseDao.listCourseFromThisStatus1(ctype,course,getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8");
		count = courseDao.listCourseSizeFromThisStatus1(ctype,course,"0,1,2,3,4,5,6,7,8");
		
		return "course_sh_list";
	}
	/**
	 *  课程初审核列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String course_primash_list() throws ElException { 
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		boolean isShared=false;
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			isShared=true;
		} 

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}

//		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();  
		String name = course == null ? "" : course.getName();
//		courses = courseDao.listAllCourseFromThis(depid, name, ctid,
//		getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT);
//		count = courseDao.listAllCourseSizeFromThis(depid, name, ctid,CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT); 
//		courses = courseDao.listAllCourseFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,
//				getPageNow(), getPageSize(),""+CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT+","+CourseConstants.COURSE_STATUS_FINAL_NOTGO+"");
//		count = courseDao.listAllCourseSizeFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,""+CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT+","+CourseConstants.COURSE_STATUS_FINAL_NOTGO+"");
		if(ctype==null||ctype.getId()<=0){
	      ctype=ctypeTree;
	    }else{
	      ctype=ctypeDao.getCtypeById(ctype.getId());
	    }
	    courses = courseDao.listCourseFromThisStatus(ctype,name,getPageNow(), getPageSize(),""+CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT+","+CourseConstants.COURSE_STATUS_FINAL_NOTGO+"");
		count = courseDao.listCourseSizeFromThisStatus(ctype,name,""+CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT+","+CourseConstants.COURSE_STATUS_FINAL_NOTGO+"");
		return "course_primash_list";
	}
	
	/**
	 *  课程列表
	 * 
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String course_list() throws ElException, UnsupportedEncodingException { 
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
//		boolean isShared=false;
//		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
//			isShared=true;
//		} 

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)  
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		if(ctype!=null&&ctype.getId()>0&&!((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).checkNode(ctype.getId(), ctypeTree, "course_type")){
			setElmessage("您无权访问此节点");
			return "error";
		}
//		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId(); 
		ctype =ctype == null||ctype.getId()<=0? ctypeTree : ctypeDao.getCtypeById(ctype.getId()); 
		
		String name = course == null ? "" : course.getName(); 
		courses = courseDao.listAllCourseFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,
				getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9");
		count = courseDao.listAllCourseSizeFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,"0,1,2,3,4,5,6,7,8,9");

		return "course_list";
	}
	
	public String word_course_list() throws ElException, UnsupportedEncodingException { 
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
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
//		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId(); 
		ctype =ctype == null||ctype.getId()<=0? ctypeTree : ctypeDao.getCtypeById(ctype.getId()); 
		
		String name = course == null ? "" : course.getName(); 
		courses = courseDao.listAllCourseFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,
				getPageNow(), getPageSize(),"5");
		count = courseDao.listAllCourseSizeFromThisStatus(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,"5");

		return "word_course_list";
	}
	
	public String mess_getCourseInfoJson() throws ElException {
		course = courseDao.getCourseById(course.getId());
//		if (elUser.getRealname() == null || elUser.getRealname().equals(""))
//			elUser.setRealname(elUser.getUsername());
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.println("{\"course\":{\"id\":\"" + course.getId()
					+ "\",\"name\":\"" + course.getName() + "\"}}");
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
		//	logger.error("ajax 获取人员信息错误",e);
		}

		return null;
	}

	public String course_alter_sh_list() throws ElException { 
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		boolean isShared=false;
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			isShared=true;
		} 
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}

//		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();  
		String name = course == null ? "" : course.getName(); 
//		courses = courseDao.listAllCourseFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,
//				getPageNow(), getPageSize(),""+CourseConstants.COURSE_STATUS_ALTER_WAIT+","+CourseConstants.COURSE_STATUS_HASOPENED+"");
//		count = courseDao.listAllCourseSizeFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,""+CourseConstants.COURSE_STATUS_ALTER_WAIT+","+CourseConstants.COURSE_STATUS_HASOPENED+"");
		if(ctype==null||ctype.getId()<=0){
		  ctype=ctypeTree;
		}else{
		  ctype=ctypeDao.getCtypeById(ctype.getId());
		}
		courses = courseDao.listCourseFromThisStatus(ctype,name,getPageNow(), getPageSize(),""+CourseConstants.COURSE_STATUS_ALTER_WAIT+","+CourseConstants.COURSE_STATUS_HASOPENED+"");
		count = courseDao.listCourseSizeFromThisStatus(ctype,name,""+CourseConstants.COURSE_STATUS_ALTER_WAIT+","+CourseConstants.COURSE_STATUS_HASOPENED+"");
		return "course_alter_sh_list";
	}
	
	public String course_applyDelete_list() throws ElException { 
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		boolean isShared=false;
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			isShared=true;
		} 
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}

//		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();  
//		String name = course == null ? "" : course.getName(); 
		course = course == null ? new Course():course;
		statusMap = course.getStatusMap();
//		courses = courseDao.listAllCourseFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,
//				getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8");
//		count = courseDao.listAllCourseSizeFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,"0,1,2,3,4,5,6,7,8");
		if(ctype==null||ctype.getId()<=0){
		  ctype=ctypeTree;
		}else{
		  ctype=ctypeDao.getCtypeById(ctype.getId());
		}
		courses = courseDao.listCourseFromThisStatus1(ctype,course,getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8");
		count = courseDao.listCourseSizeFromThisStatus1(ctype,course,"0,1,2,3,4,5,6,7,8");
		return "course_applyDelete_list";
	}
	
	public String course_alter_list() throws ElException { 
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		boolean isShared=false;
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			isShared=true;
		} 
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}

//		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();  
//		String name = course == null ? "" : course.getName(); 
		course = course == null ? new Course():course;
		statusMap = course.getStatusMap();
//		courses = courseDao.listAllCourseFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,
//				getPageNow(), getPageSize(),""+CourseConstants.COURSE_STATUS_HASOPENED+"");
//		count = courseDao.listAllCourseSizeFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,""+CourseConstants.COURSE_STATUS_HASOPENED+"");
		if(ctype==null||ctype.getId()<=0){
		  ctype=ctypeTree;
		}else{
		  ctype=ctypeDao.getCtypeById(ctype.getId());
		}
		courses = courseDao.listCourseFromThisStatus1(ctype,course,getPageNow(), getPageSize(),""+CourseConstants.COURSE_STATUS_HASOPENED+"");
		count = courseDao.listCourseSizeFromThisStatus1(ctype,course,""+CourseConstants.COURSE_STATUS_HASOPENED+"");
		return "course_alter_list";
	}
	
	/**
	 * 课程审核
	 * 
	 * @return
	 * @throws ElException
	 */
	@SuppressWarnings("unchecked")
	public String course_sh() throws ElException {
		courses=(List<Course>)getSession().getAttribute("course_delete_status");
		if(courses!=null){
			for(Course c:courses){
				if(c.getId()==course.getId()) 
					courseDao.shCourse(course.getId(), c.getStatus());
			}  
		}else{
			courseDao.shCourse(course.getId(), status);
		}
		//下面是在课程学员一起审核的时候用上， 在单独审核课程时, 只需要用上面代码
		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_OP_TYPE");
		int  cid  = course == null ? 0 : course.getId();  
  
		myCourses = statisticCourseDao.course_user_list_BYCtypePage(ctypeTree, 0,"", cid); //只需要cid条件,其余条件可以为空
		//在一门新的课程 第一次审核的时候 如果不通过， 那么学院端不显示该课程
		//在课程管理员申请了课程修改后。 在提交审核， 这时候不通过， 学员端该课程还是应该继续存在 
		if( myCourses.size() != 0 && myCourses.get(0).getStatus() == 3){//如果该课程学员经过审核，不再进行审核。保证第一次学员课程开通后修改不再关闭,只审核一次
			for (int i = 0; i < myCourses.size(); i++) { 
				courseDao.alterassignedUser(cid, myCourses.get(i).getUser().getId(),0,true);//分配学员前状态strtus = 3 先进入人员审核状态,审核通过，学院端才显示, 0代表在学员端显示修改状态 
			} 
		} 
		if(status == 5){courseDao.setisNormal(course.getId(), 1);}
		if(status == 9){courseDao.setisNormal(course.getId(), 0);}
		if(status == 8){courseDao.setaStatus(course.getId(), astatus);}//课程申请删除列表   记录申请之前状态
		//刷新首页课程模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		return Return;  
	}
	public String course_audit() throws ElException { 
		status = courseAudit.getStatus();
		courseAudit = courseDao.getCourseAudit(course.getId());
		if(courseAudit != null){
			course = courseDao.getCourseById(course.getId()); 
			elUser = userDao.getUserById(courseAudit.getCreater().getId());
		}else{
			return "course_audit_success";
		}
		if(status == 2){			
			return "course_audit_H";
		}
		return "course_audit";
	} 
	public String course_audit_reply() throws ElException {   
		if(courseAudit != null)
		courseDao.UCourseAuditContents(courseAudit);  
		return "course_audit_reply";
	} 
	/**
	 * 课程修改审核
	 * 
	 * @return
	 * @throws ElException
	 */
	public String course_alter_sh() throws ElException {
		courses=(List<Course>)getSession().getAttribute("course_delete_status");
		if(courses!=null){
			for(Course c:courses){
				if(c.getId()==course.getId())
					courseDao.shCourse(course.getId(), c.getStatus());
			}
		}else{
			courseDao.shCourse(course.getId(), status);
		}
		//刷新首页课程模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		return "course_alter_sh";
	}
	/**
	 * 课程删除初始化
	 * 
	 * @return
	 * @throws ElException
	 */
	public String course_deleteInit() throws ElException {
		courses = courseDao.getCourseById(this.getIds());
		getSession().setAttribute("course_delete_status", courses);
		return "course_delete";
	}
	
	/**
	 * 课程删除(没有用到的课程会被真删除)
	 * 
	 * @return
	 * @throws ElException
	 */
	public String course_del() throws ElException {
		//courses = courseDao.getCourseById(this.getIds());
		String[] courseIds=this.getIds().split(",");
		if( courseIds!=null)
		for (int i = 0; i < courseIds.length; i++) {
			//1.先检测该课程是否被用到过
			if(courseDao.checkCourseIsUse(Integer.parseInt(courseIds[i]))){
				//被用过，逻辑删除
				courseDao.alterCourseStatus(Integer.parseInt(courseIds[i]), CourseConstants.COURSE_STATUS_DELETE);
			}else{
				//物理删除
				courseDao.deleteCourseByid(Integer.parseInt(courseIds[i]));
			}
		}
		//刷新首页课程模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_COURSE,
				ElLoggerConstants.LOG_TYPE_DELETE,"课程批量删除,ids("+this.getIds()+")",
				ElLoggerConstants.LOG_RES_SUCC,0);
		return "course_list";
	}

	/**
	 * 课程删除提交
	 * 
	 * @return
	 * @throws ElException
	 */
	public String course_delete() throws ElException {
		courses = courseDao.getCourseById(this.getIds());
		courseDao.courseDelete(this.getIds(),getSessionIntValue(ElConstants.SESSION_USERID));
		//刷新首页课程模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_COURSE,
				ElLoggerConstants.LOG_TYPE_DELETE,"课程批量删除",
				ElLoggerConstants.LOG_RES_SUCC,0);
		return "course_delete_success";
	}

	/**
	 * 处理课程删除申请
	 * 
	 * @return
	 * @throws ElException
	 */
	public String course_delete_list() throws ElException {
//		courses = courseDao.listDeleteCourse(getPageNow(), getPageSize());
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
//		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
//		.getId();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
//		int ctid = ctype == null ? ctypeTree.getId() : ctype
//				.getId();
		String name = course == null ? "" : course.getName();
//		courses = courseDao.listAllCourseFromThis(depid, name, ctid,
//		getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_DELETE_WAIT);
//		count = courseDao.listAllCourseSizeFromThis(depid, name, ctid,CourseConstants.COURSE_STATUS_DELETE_WAIT);
//		courses = courseDao.listAllCourseFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE),course, ctid,
//				getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_DELETE_WAIT+"","");
//		count = courseDao.listAllCourseSizeFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,CourseConstants.COURSE_STATUS_DELETE_WAIT+"","");
		if(ctype==null||ctype.getId()<=0){
		  ctype=ctypeTree;
		}else{
		  ctype=ctypeDao.getCtypeById(ctype.getId());
		}
		courses = courseDao.listCourseFromThisStatus(ctype,name,getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_DELETE_WAIT+"");
		count = courseDao.listCourseSizeFromThisStatus(ctype,name,CourseConstants.COURSE_STATUS_DELETE_WAIT+"");
		return "course_delete_list";
	}
	/**
	 * 处理删除课程申请处理
	 * 
	 * @return
	 * @throws ElException
	 */
	public String course_deleteOp() throws ElException {
		course = courseDao.getCourseById(course.getId());
		courseDao.courseDeleteOp(course.getId());
		//刷新首页课程模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_COURSE,
				ElLoggerConstants.LOG_TYPE_DELETE,course.getName(),
				ElLoggerConstants.LOG_RES_SUCC,course.getId());
		return "course_deleteOp_success";
	}

	public String study_course_delete_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		mycourses = courseDao
		.listStudycoursedelete(getPageNow(), getPageSize());
		count = courseDao.listStudycoursedeleteSize();
		return "study_course_delete_list";
	}

	public String study_course_delete_op() throws ElException {
		if (null != mycourse) {
			courseDao.Studycoursedelete_Op(mycourse);
		}
		return "study_course_delete_list";
	}

	public String study_course_delete_unop() throws ElException {
		if (null != mycourse) {
			courseDao.Studycoursedelete_Unop(mycourse);
		}
		return "study_course_delete_list";
	}

//	public String myCourse_list() throws ElException {
////	getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
//	int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
//	.getId();
////	ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
////	ElConstants.TREE_FIANL, true);
//	ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//	ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
//	String name = course == null ? "" : course.getName();
//	courses = courseDao.listMyCourse(ctypeTree,
//	getSessionIntValue(ElConstants.SESSION_USERID), ctid, name,
//	getPageNow(), getPageSize());
//	count = courseDao.listMyCourseCount(ctypeTree,
//	getSessionIntValue(ElConstants.SESSION_USERID), ctid, name);
//	return "myCourse_list";
//	}


	public String myCourse_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
//		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
//		.getId();
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true);
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}

		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();  
		String name = course == null ? "" : course.getName();
		if(getRequest().getParameter("str")!=null){
			if("mymulti-media_list".equals(getRequest().getParameter("str"))){
				mymultis=courseDao.MyMultis(ctypeTree,getSessionIntValue(ElConstants.SESSION_USERID), ctid, name,getPageNow(), getPageSize());
				count = courseDao.listMyMultisCount(ctypeTree,getSessionIntValue(ElConstants.SESSION_USERID), ctid, name);
			}
			if("combinationSearchCourse".equals(getRequest().getParameter("str"))){
				courses = courseDao.listMyCourse(ctypeTree, ctid, name,getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
				count = courseDao.listMyCourseCount(ctypeTree,ctid, name);
			}
		}else{ 
			courses = courseDao.listMyCourse(ctypeTree,getSessionIntValue(ElConstants.SESSION_USERID), ctid, name,getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(), getPageSize());
			count = courseDao.listMyCourseCount(ctypeTree,
					getSessionIntValue(ElConstants.SESSION_USERID), ctid, name,getSessionIntValue(ElConstants.SESSION_ROLE));
		}
		return "myCourse_list";
	}

	public String myCoursemake_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
//		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
//		.getId();
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true);
//		String name = course == null ? "" : course.getName();
//		courses = courseDao.listMyCourse(
//		getSessionIntValue(ElConstants.SESSION_USERID), ctid, name,
//		getPageNow(), getPageSize());
//		count = courseDao.listMyCourseCount(
//		getSessionIntValue(ElConstants.SESSION_USERID), ctid, name);
		//modify by jiahaijiang
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
				.getId();
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true);
		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		String name = course == null ? "" : course.getName();
		courses = courseDao.listMyCourse(ctypeTree,
				getSessionIntValue(ElConstants.SESSION_USERID), ctid, name,getSessionIntValue(ElConstants.SESSION_ROLE),
				getPageNow(), getPageSize());
		count = courseDao.listMyCourseCount(ctypeTree,
				getSessionIntValue(ElConstants.SESSION_USERID), ctid, name,getSessionIntValue(ElConstants.SESSION_ROLE));
		return "myCoursemake_list";
	}

	public String course_assignInit() throws ElException {
		return "course_assign";
	}

	public String course_assignList() throws ElException {
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		/*
		 * if (course_sourse == 1)// 上级部门分配的 { courses =
		 * courseDao.listAllCourseFromSuper(depid, course .getName(),
		 * getPageNow(), getPageSize()); count =
		 * courseDao.listAllCourseSizeFromSuper(depid, course .getName()); } if
		 * (course_sourse == 2)// 本部门的资源 { courses =
		 * courseDao.listAllCourseFromThis(depid, course .getName(),
		 * getPageNow(), getPageSize()); count =
		 * courseDao.listAllCourseSizeFromThis(depid, course .getName()); }
		 */
		// getPageSize()=getPageSize()==0?10:getPageSize();
//		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype.getId();
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(
//		ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true,String.valueOf(userid),true,"COURSE_USE_TYPE");

//		int userid = getSessionIntValue(ElConstants.SESSION_USERID);

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID),"op",ElConstants.TREE_FIANL, true);
		}
		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId(); 


		String name = course == null ? "" : course.getName();
//		courses = courseDao.listAllCourseFromThis(depid, name, ctid,
//		getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_OPEN);
//		count = courseDao.listAllCourseSizeFromThis(depid, name, ctid,CourseConstants.COURSE_STATUS_OPEN);
		//modyfy by jiahaijiang
		courses = courseDao.listAllCourseFromThisStatus(ctypeTree,depid, getSessionIntValue(ElConstants.SESSION_ROLE),name, ctid,
				getPageNow(), getPageSize(),""+CourseConstants.COURSE_STATUS_INMAKING+","+CourseConstants.COURSE_STATUS_DELETE+"");
		count = courseDao.listAllCourseSizeFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,""+CourseConstants.COURSE_STATUS_INMAKING+","+CourseConstants.COURSE_STATUS_DELETE+"");
		return "course_assignList";
	}
	
	//此处也是可分配课程 ， 与上处的不同的事,接下来流程的走法。
	public String course_assignList2() throws ElException {
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
//		int userid = getSessionIntValue(ElConstants.SESSION_USERID);

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID),"op",ElConstants.TREE_FIANL, true);
		}
		//int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();  

//		String name = course == null ? "" : course.getName(); 
		course = course == null ? new Course():course;
		statusMap = course.getStatusMap();
//		courses = courseDao.listAllCourseFromThisStatus(ctypeTree,depid, getSessionIntValue(ElConstants.SESSION_ROLE),name, ctid,
//				getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9");
//		count = courseDao.listAllCourseSizeFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,"0,1,2,3,4,5,6,7,8,9");
		if(ctype==null||ctype.getId()<=0){
			ctype=ctypeTree;
		}else{
			ctype=ctypeDao.getCtypeById(ctype.getId());
		}
		courses = courseDao.listCourseFromThisStatus1(ctype,course,
				getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9");
		count = courseDao.listCourseSizeFromThisStatus1(ctype,course,"0,1,2,3,4,5,6,7,8,9");
		return "course_assignList2";
	}

	public String course_hotsetInit() throws ElException {

		return "course_hotset";
	}

	public String course_hotset() throws ElException {
		if (null != courses)
		for (int i = 0; i < courses.size(); i++) {
			courseDao.courseHotSet(courses.get(i).getId(), course.getHot());
		}
		//刷新首页课程模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		return "course_hotsetlist";
	}

	public String course_hotsetlist() throws ElException {
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
//		if (course_sourse == 1)// 上级部门分配的
//		{
//		courses = courseDao.listAllCourseFromSuper(depid, course.getName(),
//		getPageNow(), getPageSize());
//		count = courseDao.listAllCourseSizeFromSuper(depid, course
//		.getName());
//		}
//		if (course_sourse == 2)// 本部门的资源
//		{
//		// courses = courseDao.listAllCourseFromThis(depid, course
//		// .getName(), getPageNow(), getPageSize());
//		// count = courseDao.listAllCourseSizeFromThis(depid, course
//		// .getName());
//		}
		// getPageSize()=getPageSize()==0?10:getPageSize();
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true);
	/*	ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
				.getId();
		String name = course == null ? "" : course.getName();
		courses = courseDao.listAllCourseFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,
				getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_OPEN);
		count = courseDao.listAllCourseSizeFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,CourseConstants.COURSE_STATUS_OPEN);*/
//		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		//int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
//		String name = course == null ? "" : course.getName();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID),"op",ElConstants.TREE_FIANL, true);
		}
		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId(); 
		courses = courseDao.listAllCourseFromThis(ctypeTree,depid, getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
				getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_HASOPENED+"","");
		count = courseDao.listAllCourseSizeFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,CourseConstants.COURSE_STATUS_HASOPENED+"","");
		return "course_hotsetlist";
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<ELUser> getElusers() {
		return elusers;
	}

	public void setElusers(List<ELUser> elusers) {
		this.elusers = elusers;
	}

	public String course_assigntoUsersInit() throws ElException {

 		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) { 
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);  
			deprTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		} else { 
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);  
			deprTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
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
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
 		int depid = 1 ;
		if(deptid == null){  
				depid = depTree.getId(); 
		}else{ 
			depid = deptid;
		}  
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
//		canAssignDep = courseDao.listCanAssignDep(depid, course.getId());
//		xassignedDep = courseDao.listAssignedDep(depid, course.getId(),
//		CourseConstants.COURSE_STUDY_STATUS_XX);
//		bassignedDep = courseDao.listAssignedDep(depid, course.getId(),
//		CourseConstants.COURSE_STUDY_STATUS_BX);
//		zassignedDep = courseDao.listAssignedDep(depid, course.getId(),
//		CourseConstants.COURSE_STUDY_STATUS_ZX);

		//
//		return "course_assigntoUsers"; 
		int cid = (course == null) ? 0 : course.getId();
		//获取课程信息
		course=courseDao.getCourseById(cid);
		if(course==null){
			course=new Course(cid);
		}else{
			//int data=course.getCreatetime().getDate();
			//int time=course.getCreatetime().getTimezoneOffset();
		}
		//获取课程所有场次信息
		examRooms=courseDao.getRoomsByCourseid(course.getId());///////
		//getRequest().setAttribute("rooms", rooms);
		elusers = courseDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,deprTree,station,getSessionIntValue(ElConstants.SESSION_ROLE));
		count =courseDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,deprTree,station,getSessionIntValue(ElConstants.SESSION_ROLE));

		jingzhongs=userDao.getBaseDatatByTypeid(1);
		zhiwus=userDao.getBaseDatatByTypeid(2);
		zhijis=userDao.getBaseDatatByTypeid(3);
		gangweis=userDao.getBaseDatatByTypeid(4);
		dishis=userDao.getBaseDatatByTypeid(5);
		return "course_newassigntoUsers";
	}
	public String course_assigntoUsers2Init() throws ElException {
		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		int cid = (course == null) ? 0 : course.getId();
		//获取课程信息
		course=courseDao.getCourseById(cid);
		if(course==null){
			course=new Course(cid);
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
		elusers = courseDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,depTree,stTree,getSessionIntValue(ElConstants.SESSION_ROLE));
		count =courseDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,depTree,stTree,getSessionIntValue(ElConstants.SESSION_ROLE));

		return "course_newassigntoUsers2";
	}
	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}
	public String course_allassigntoUsers() throws ElException {
		//获取课程所绑定的场次
//		int roomid=0;
//		if(examRoom!=null){
//			roomid=examRoom.getId();
//		}
		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		int cid = (course == null) ? 0 : course.getId();
		elusers = courseDao.listAssignedUser(9999999, 1,depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,depTree,stTree,getSessionIntValue(ElConstants.SESSION_ROLE));
		for (ELUser user:elusers) {
			//courseDao.assignedUser(cid, user.getId(),status);
			if(user.getIsAssign().equals("未分配")){
				courseDao.assignedUser(cid,user.getId(),status,course.getRoomstart(),course.getRoomend());//先得判断是否分配
			}
		}
		return course_assigntoUsersInit();
	}

	public String course_newassigntoUsers() throws ElException {
		//获取课程所绑定的场次
		int roomid=0;
		if(examRoom!=null){
			roomid=examRoom.getId();
		}
		int cid = (course == null) ? 0 : course.getId();
		String user[] = this.getUserids().split(",");
		jieyeid = this.getJieyeid();
		for (int i = 0; i < user.length; i++) {
			String users[] =user[i].split(":");
			if(users[1].equals("未分配")){
				//courseDao.assignedUser(cid, Integer.valueOf(users[0]),status);//分配学员  strtus = 3 先进入人员审核状态,审核通过，学院端才显示
				//courseDao.assignedUser(cid, Integer.valueOf(users[0]),status,course.getRoomstart(),course.getRoomend());
				
				//courseDao.assignedUser(cid, Integer.valueOf(users[0]),status,course.getRoomstart(),course.getRoomend(),roomid);
				courseDao.assignedUser2(cid, Integer.valueOf(users[0]), status, course.getRoomstart(), course.getRoomend(), roomid, 0, jieyeid);
			}
		}
		return course_assigntoUsersInit();
	}
	
	public String course_newassigntoUsers2() throws ElException {
		//获取课程所绑定的场次
//		int roomid=0;
//		if(examRoom!=null){
//			roomid=examRoom.getId();
//		}
		int cid = (course == null) ? 0 : course.getId();
		String user[] = this.getUserids().split(",");
		for (int i = 0; i < user.length; i++) {
			String users[] =user[i].split(":");
			if(users[1].equals("未分配")){
				//courseDao.assignedUser(cid, Integer.valueOf(users[0]),status);//分配学员  strtus = 3 先进入人员审核状态,审核通过，学院端才显示
				//courseDao.assignedUser(cid, Integer.valueOf(users[0]),status,course.getRoomstart(),course.getRoomend());
				courseDao.assignedUser(cid, Integer.valueOf(users[0]),status,course.getRoomstart(),course.getRoomend());

			}
		}
		return course_assigntoUsers2Init();
	}
	public String course_newunassigntoUsers() throws ElException {
		int cid = (course == null) ? 0 : course.getId();
		String user[] = this.getUserids().split(",");
		for (int i = 0; i < user.length; i++) {
			String users[] =user[i].split(":");
			if(users[1].equals("已分配")){
				courseDao.unassignedUser(cid,  Integer.valueOf(users[0]));
			}
		}
		return course_assigntoUsersInit();
	}

	public String course_assigntoUsers() throws ElException {

		int cid = (course == null) ? 0 : course.getId();
		if (canAssignUsers != null)
			for (int i = 0; i < canAssignUsers.size(); i++) {
				courseDao.assignedUser(cid, canAssignUsers.get(i).getId(),
						status);//给学员分配课程， 还需要要审核，才能到学员端
			}
		return "course_assigntoUsers";
	}

	public String course_unassigntoUsers() throws ElException {
		int cid = (course == null) ? 0 : course.getId();
		if (status == CourseConstants.COURSE_STUDY_STATUS_XX
				&& xassignedUsers != null)
			for (int i = 0; i < xassignedUsers.size(); i++) {
				courseDao.unassignedUser(cid, xassignedUsers.get(i).getId());
			}
		if (status == CourseConstants.COURSE_STUDY_STATUS_BX
				&& bassignedUsers != null)
			for (int i = 0; i < bassignedUsers.size(); i++) {
				courseDao.unassignedUser(cid, bassignedUsers.get(i).getId());
			}
		if (status == CourseConstants.COURSE_STUDY_STATUS_ZX
				&& zassignedUsers != null)
			for (int i = 0; i < zassignedUsers.size(); i++) {
				courseDao.unassignedUser(cid, zassignedUsers.get(i).getId());
			}
		return "course_unassigntoUsers";
	}

	public String course_assigntoDepsInit() throws ElException {
		// int depid = 1;//
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		//
		// // canAssignDeps = courseDao.listCanAssignDeps(depid,
		// course.getId());
		// assignDeps = courseDao.listAssignedDeps(depid, course.getId());
		// depTree = departmentDao.getDepTree(1, ElConstants.TREE_FIANL, true);
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);

		canAssignDeps = courseDao.listCanAssignDeps(depid, course.getId());
		assignDeps = courseDao.listAssignedDeps(depid, course.getId());
		depTree = departmentDao.getDepTree(depid, ElConstants.TREE_FIANL, true);
		return "course_assigntoDeps";
	}

	public String course_assigntoDeps2Init() throws ElException { 
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);

		canAssignDeps = courseDao.listCanAssignDeps(depid, course.getId());
		assignDeps = courseDao.listAssignedDeps(depid, course.getId());
		depTree = departmentDao.getDepTree(depid, ElConstants.TREE_FIANL, true);
		return "course_assigntoDeps2";
	}
	
	public String course_assigntoDeps() throws ElException {

		if (null != canAssignDeps) {
			if (course != null) {
				courseDao.unassignDepsAll(course.getId());
			}
			for (int i = 0; i < canAssignDeps.size(); i++) {
				if (!courseDao.checkDep2course(canAssignDeps.get(i).getId(),
						course.getId())) {
					courseDao.assignDeps(course.getId(), canAssignDeps.get(i)
							.getId());
				}
			}
		}
		return "course_assigntoDeps";
	}

	public String course_unassigntoDeps() throws ElException {
		if (null != assignDeps) {
			for (int i = 0; i < assignDeps.size(); i++) {
				courseDao.unassignDeps(course.getId(), assignDeps.get(i)
						.getId());
			}
		}
		return "course_assigntoDeps";
	}

	public String course_unassigntoDeps2() throws ElException {
		if (null != assignDeps) {
			for (int i = 0; i < assignDeps.size(); i++) {
				courseDao.unassignDeps(course.getId(), assignDeps.get(i)
						.getId());
			}
		}
		return "course_assigntoDeps2";
	} 
	
	public String course_selectlist() throws ElException {
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
				.getId();
		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		mycourses = courseDao
		.listselectedCourse(ctypeTree,ctid,3, getPageNow(), getPageSize());
		count = courseDao.listselectedCourseSize(ctypeTree,ctid,3);
		return "course_selectlist";
	}

	public String setSelectedCoruse() throws ElException {
		if (null != mycourse){
			courseDao.setSelectedCoruse(mycourse.getStatus(), mycourse);
		}
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
				.getId();
		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		mycourses = courseDao.listselectedCourse(ctypeTree,ctid,status, getPageNow(),
				getPageSize());
		count = courseDao.listselectedCourseSize(ctypeTree,ctid,status);
		return "course_selectlist";
	}
	public String course_notequeryInit() throws ElException {
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "course_notequeryInit";
	}
	/**
	 * 我的课列表
	 * @return
	 * @throws ElException
	 */
	public String mytbcourses() throws ElException{
		courses = courseDao.mytbcourses(getSessionIntValue(ElConstants.SESSION_USERID));
		return "mytbcourses";
	}
	/**
	 * 上课
	 * @return
	 * @throws ElException
	 */
	public String mytbcourse_Into() throws ElException {
		course = courseDao.getCourseById(course.getId());
		if(course.getRoomstart().after(new Date() )){
			setElmessage("课程没到开始时间!");
			return "error";
		}
		if(course.getRoomend().before(new Date() )){
			setElmessage("课程已经结束!");
			return "error";
		} 
		if ( course.getTeacherId() != getSessionIntValue(ElConstants.SESSION_USERID)) {//不是讲师
			if (!omDao.moderatorHasLogin(course.getRoom().getId())) {//讲师未进入
				setElmessage("您不是主持人不能进入！");
				return "error";
			} 
		} else{
			omDao.setModeratorHasLoginOut(course.getRoom().getId(), 1);
			getSession().setAttribute("roomid", course.getRoom().getId()); 
			getSession().setAttribute("teacherId", getSessionIntValue(ElConstants.SESSION_USERID));//讲师id
		}
		Rooms room = omDao.getOmRoom(course.getRoom().getId());

		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		String sid = OmUtil.getSID();
		String revalue = OmUtil.setUser(SystemConfOp.getValue(ElConstants.OPENMEETINGS_ADMIN_USER), SystemConfOp.getValue(ElConstants.OPENMEETINGS_ADMIN_PWD), sid);
		System.out.println(revalue);
		int moderator = 0;
		if (course != null
				&& course.getTeacherId() == getSessionIntValue(ElConstants.SESSION_USERID)) {
			moderator = 1;
		}	
		String secureHash = OmUtil.getSecureHashHash(elUser.getUsername(),
				elUser.getRealname(), sid, room.getId(), "", moderator);
		getRequest().setAttribute(
				"url",
				SystemConfOp.getValue(ElConstants.OPENMEETINGS_URL) + "/?secureHash="
						+ secureHash);
		return "mytbcourse_Into";
	}


	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
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

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public String course_notequerylist() throws ElException {
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		
		if(elUser == null){
			sub_department = ElConstants.SUBOP_YES;
		}
		
		elUsers = userDao.getUserByDepId(department!=null?department.getId():getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), sub_department,
				elUser, userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)).getRole().getId(),getPageNow(), getPageSize());
		count = userDao.getUserByDepIdSize(department!=null?department.getId():getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), sub_department,
				elUser);
		return "course_notequerylist";
	}

	public List<MyCourse> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}

	public StudyCourseDao getStudyCourseDao() {
		return studyCourseDao;
	}

	public void setStudyCourseDao(StudyCourseDao studyCourseDao) {
		this.studyCourseDao = studyCourseDao;
	}

	public List<CourseNote> getCnotes() {
		return cnotes;
	}

	public void setCnotes(List<CourseNote> cnotes) {
		this.cnotes = cnotes;
	}

	public String course_notequery() throws ElException {
		myCourses = studyCourseDao.listMyCourse(elUser.getId(), getPageNow(),
				getPageSize());
		count = studyCourseDao.listMyCourseSize(elUser.getId());
		return "course_notequery";
	}

	public String course_notequeryView() throws ElException {
		course = courseDao.getCourseById(course.getId());
		cnotes = studyCourseDao.listCnotes(elUser.getId(), course.getId());
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "course_notequeryView";
	}
//	课程组合搜索
	public String combinationSearchCourseInit()throws ElException{
		ctypes=courseDao.getCourseType();
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		return "combinationSearchCourse";
	}
	public String combinationSearchCourselist()throws ElException{
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
				.getId();
		String name = course == null ? "" : course.getName();
		if(course==null||course.getCtype()==null){
			setElmessage("没有可操作的课程类别!");
			return "error";
		}
		if(course.getCtype().getId()==0)
			course.getCtype().setId(ctid); 
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		} 

		if(course.getCtype().getId()==-1||course.getCtype().getId()==-2){
			courses = courseDao.listMyCourse(ctypeTree,
					getSessionIntValue(ElConstants.SESSION_USERID), ctid, name,getSessionIntValue(ElConstants.SESSION_ROLE),
					getPageNow(), getPageSize());
			count = courseDao.listMyCourseCount(ctypeTree,
					getSessionIntValue(ElConstants.SESSION_USERID), ctid, name,getSessionIntValue(ElConstants.SESSION_ROLE));
		}else{
			courses = courseDao.listCombinationCourse(ctypeTree, course, getSessionIntValue(ElConstants.SESSION_ROLE),getPageNow(), getPageSize());
			count=courseDao.listCombinationCourseCount(ctypeTree, course,getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(), getPageSize());
		}
		return "combinationSearchCourselist";
	}
	
	/*
	 * 审核流程备注
	 * */
//	public String course_remarksList() throws ElException {
//		cRemarks = studyCourseDao.listMyCourse(
//				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
//				getPageSize());
//		count = studyCourseDao
//				.listMyCourseSize(getSessionIntValue(ElConstants.SESSION_USERID));
//		return "mynotecourselist";
//	}
	public CourseType getCtype() {
		return ctype;
	}

	public void setCtype(CourseType ctype) {
		this.ctype = ctype;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public List<ELUser> getXassignedUsers() {
		return xassignedUsers;
	}

	public void setXassignedUsers(List<ELUser> xassignedUsers) {
		this.xassignedUsers = xassignedUsers;
	}

	public List<ELUser> getBassignedUsers() {
		return bassignedUsers;
	}

	public void setBassignedUsers(List<ELUser> bassignedUsers) {
		this.bassignedUsers = bassignedUsers;
	}

	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}

	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}

	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}

	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}

	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}

	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}

	public CourseType getCtypeTree() {
		return ctypeTree;
	}

	public void setCtypeTree(CourseType ctypeTree) {
		this.ctypeTree = ctypeTree;
	}

	public List<QuizPaper> getQuizPapers() {
		return quizPapers;
	}

	public void setQuizPapers(List<QuizPaper> quizPapers) {
		this.quizPapers = quizPapers;
	}

	public List<MyCourse> getMycourses() {
		return mycourses;
	}

	public void setMycourses(List<MyCourse> mycourses) {
		this.mycourses = mycourses;
	}

	public MyCourse getMycourse() {
		return mycourse;
	}

	public void setMycourse(MyCourse mycourse) {
		this.mycourse = mycourse;
	}

	public List<CourseType> getCtypes() {
		return ctypes;
	}

	public void setCtypes(List<CourseType> ctypes) {
		this.ctypes = ctypes;
	}

	public List<ELUser> getZassignedUsers() {
		return zassignedUsers;
	}

	public void setZassignedUsers(List<ELUser> zassignedUsers) {
		this.zassignedUsers = zassignedUsers;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public Department getCanAssignDep() {
		return canAssignDep;
	}

	public void setCanAssignDep(Department canAssignDep) {
		this.canAssignDep = canAssignDep;
	}

	public Department getXassignedDep() {
		return xassignedDep;
	}

	public void setXassignedDep(Department xassignedDep) {
		this.xassignedDep = xassignedDep;
	}

	public Department getBassignedDep() {
		return bassignedDep;
	}

	public void setBassignedDep(Department bassignedDep) {
		this.bassignedDep = bassignedDep;
	}

	public Department getZassignedDep() {
		return zassignedDep;
	}

	public void setZassignedDep(Department zassignedDep) {
		this.zassignedDep = zassignedDep;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}


	public ExamRoom getExamRoom_bk() {
		return examRoom_bk;
	}

	public void setExamRoom_bk(ExamRoom examRoom_bk) {
		this.examRoom_bk = examRoom_bk;
	}

	public OmDao getOmDao() {
		return omDao;
	}

	public void setOmDao(OmDao omDao) {
		this.omDao = omDao;
	}

	public StatisticCourseDao getStatisticCourseDao() {
		return statisticCourseDao;
	}

	public void setStatisticCourseDao(StatisticCourseDao statisticCourseDao) {
		this.statisticCourseDao = statisticCourseDao;
	}

	public int getAstatus() {
		return astatus;
	}

	public void setAstatus(int astatus) {
		this.astatus = astatus;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public CourseRemarks getCRemarks() {
		return cRemarks;
	}

	public void setCRemarks(CourseRemarks remarks) {
		cRemarks = remarks;
	}

	public Department getDeprTree() {
		return deprTree;
	}

	public void setDeprTree(Department deprTree) {
		this.deprTree = deprTree;
	}
	private BaseDatatCourse baseCourse;	
	public BaseDatatCourse getBaseCourse() {
		return baseCourse;
	}

	public void setBaseCourse(BaseDatatCourse baseCourse) {
		this.baseCourse = baseCourse;
	}
	private int allTypeid; 
	public int getAllTypeid() {
		return allTypeid;
	}

	public void setAllTypeid(int allTypeid) {
		this.allTypeid = allTypeid;
	}
	private List<BaseDatatCourse> baseCourseList;
	public List<BaseDatatCourse> getBaseCourseList() {
		return baseCourseList;
	}

	public void setBaseCourseList(List<BaseDatatCourse> baseCourseList) {
		this.baseCourseList = baseCourseList;
	}
	private List<BaseDataTypeCourse> baseCourseTypeList;
	public List<BaseDataTypeCourse> getBaseCourseTypeList() {
		return baseCourseTypeList;
	}

	public void setBaseCourseTypeList(List<BaseDataTypeCourse> baseCourseTypeList) {
		this.baseCourseTypeList = baseCourseTypeList;
	}

	//课程基础数据库 
	public String course_BasedbList() throws ElException{
		if(baseCourse==null){
			baseCourse=new BaseDatatCourse();
			baseCourse.setTypeid(-1);
			allTypeid=1;
		} 
		baseCourseList=courseDao.getBaseCourseByTypeid(baseCourse.getTypeid(),getPageNow(),getPageSize());
		count=courseDao.getBaseCourseByTypeidCount(baseCourse.getTypeid());
		//查基础数据类别
		baseCourseTypeList=courseDao.getAllBaseDataTypeCourse();
		return "course_BasedbList";
	} 
	public String course_addBasedbInit() throws ElException{
		//查基础数据类别
		baseCourseTypeList =courseDao.getAllBaseDataTypeCourse(getPageNow(),getPageSize());
		return "course_addBasedbInit";
	}
	public String course_addBasedb() throws ElException{
		courseDao.addBaseCourseDb(baseCourse);
		return "course_BasedbList";
	} 
	public String course_alterBasedbInit() throws ElException{
		baseCourse = courseDao.getBaseDatatCourseById(baseCourse.getId());
		return "course_alterBasedb";
	}
	public String course_alterBasedb() throws ElException{
		courseDao.updateBaseDbCourse(baseCourse);
		return "course_BasedbList";
	}
	public String course_BasedbDel() throws ElException{
		courseDao.delBaseDbCourse(baseCourse.getId());
		return "course_BasedbList";
	}
	
	public String course_BasedbSort() throws ElException{
		if(baseCourse!=null){
			if(baseCourse.getSortManner()==1){
				courseDao.sortBaseDbsCourse(baseCourse.getTypeid(), baseCourse.getSortid(), 1);
			}else{
				courseDao.sortBaseDbsCourse(baseCourse.getTypeid(), baseCourse.getSortid(), 0);
			}
		}
		return "course_BasedbList";
	}

	
	//wsj1218xiugai 
	/**
	 *  我添加的课程列表
	 * 
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String myAddCourse() throws ElException, UnsupportedEncodingException { 
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		int userid=getSessionIntValue(ElConstants.SESSION_USERID);
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
//		boolean isShared=false;
//		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
//			isShared=true;
//		} 

//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)  
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
//		else { 
//			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
//		}
		if(ctype!=null&&ctype.getId()>0&&!((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).checkNode(ctype.getId(), ctypeTree, "course_type")){
			setElmessage("您无权访问此节点");
			return "error";
		}
//		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId(); 
		ctype =ctype == null||ctype.getId()<=0? ctypeTree : ctypeDao.getCtypeById(ctype.getId()); 
		
		String name = course == null ? "" : course.getName(); 
		courses = courseDao.myListAllCourse(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,
				getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9",userid);
		count = courseDao.myListAllCourseSize(ctype,1,getSessionIntValue(ElConstants.SESSION_ROLE), name, 1,"0,1,2,3,4,5,6,7,8,9",userid);

		return "myAddCourse";
	}
	
	
	
	public Station getStTree() {
		return stTree;
	}

	public void setStTree(Station stTree) {
		this.stTree = stTree;
	}

	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}

	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}

	public List<ForumBlockType> getFbtypes() {
		return fbtypes;
	}

	public void setFbtypes(List<ForumBlockType> fbtypes) {
		this.fbtypes = fbtypes;
	}

	public ForumAdminDao getForumAdminDao() {
		return forumAdminDao;
	}

	public void setForumAdminDao(ForumAdminDao forumAdminDao) {
		this.forumAdminDao = forumAdminDao;
	}

	public List<Forum> getJhforums() {
		return jhforums;
	}

	public void setJhforums(List<Forum> jhforums) {
		this.jhforums = jhforums;
	}

	public List<Forum> getRmforums() {
		return rmforums;
	}

	public void setRmforums(List<Forum> rmforums) {
		this.rmforums = rmforums;
	}

	public List<Forum> getZxforums() {
		return zxforums;
	}

	public void setZxforums(List<Forum> zxforums) {
		this.zxforums = zxforums;
	}

	public ForumBlock getFblock() {
		return fblock;
	}

	public void setFblock(ForumBlock fblock) {
		this.fblock = fblock;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}
}
