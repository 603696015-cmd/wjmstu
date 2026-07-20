package com.sopia.statman.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.dao.impl.EroomDaoImpl;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseNote;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.statman.dao.StatisticCourseDao;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;

/**
 * Description: 课程统计部分 对应struts: com/sopia/common/configs/stat_course.xml
 * Copyright (c) Department of Research and Development/wenyishun110@163.com.
 * All Rights Reserved.
 * @version 1.0  2011-9-4 上午12:07:46  by 闻益舜（wenyishun110@163.com）创建
 */
public class StatisticCourse extends BaseAction {
	private Department depTree;
	private Department department;
	private ELUser elUser;
	private CourseTypeDao ctypeDao;
	private int sub_department;
	private List<ELUser> elUsers;
	private List<Course> courses;
	private StatisticCourseDao statisticCourseDao;
	private List<MyCourse> myCourses;
	private CourseType ctypeTree;
	private CourseDao courseDao;
	private Course course;
	private List<CourseNote> cnotes;
	private StudyCourseDao studyCourseDao;
	private CourseType ctype; 
	private boolean exprot;
	private List<MyCPage> myCpages;
	private String Return;
	private EroomDao eroomDao;
	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}
	private Timestamp       start;//搜索时间  开始时间
	private Timestamp       end;//结束时间
	private CoursePage		cp;
	private String 			alltime;
	private boolean export;
	private UserDao userDao;
	
	private int sta;

	public int getSta() {
		return sta;
	}

	public void setSta(int sta) {
		this.sta = sta;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public CoursePage getCp() {
		return cp;
	}

	public void setCp(CoursePage cp) {
		this.cp = cp;
	}

	public String getAlltime() {
		return alltime;
	}

	public void setAlltime(String alltime) {
		this.alltime = alltime;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public List<MyCPage> getMyCpages() {
		return myCpages;
	}

	public void setMyCpages(List<MyCPage> myCpages) {
		this.myCpages = myCpages;
	}

	public String user_course() throws ElException {
		myCourses = statisticCourseDao.listMyCourse(elUser.getId());
		return "user_course";
	}

	public String user_course_create() throws ElException {
		courses = statisticCourseDao.listCourseByCreater(elUser.getId());
		return "user_course_create";
	}

	public String dep_course_list() throws ElException {
		courses = statisticCourseDao.listCourseByDepid(department.getId());

		return "dep_course_list";
	}

	public String course_searchInit() throws ElException {
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		return "course_searchInit";
	}

	public String course_searchlist() throws ElException {
		//int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype.getId();
//		if(course==null){
//			course=new Course();
//			course.setCtype(new CourseType());
//		}
//		if(course.getCtype().getId()==0){
//			course.getCtype().setId(ctid);
//		}
		//courses = statisticCourseDao.listCourseBYCtype(course.getCtype().getId(),
		//		course.getName());
//		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true); 
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_OP_TYPE");

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
//		int ctid = ctype == null ? -2 : ctype.getId();
//		String name = course == null ? "" : course.getName();
//		int[] ctids=null;
//		if(getRequest().getParameter("str")==null||ctid==1){
//			ctids=new int[ctypeTree.getChild().size()+1];
//			for(int i=0;i<ctypeTree.getChild().size();i++){
//				ctids[i]=ctypeTree.getChild().get(i).getId();
//			}
//		}else{
//			ctids=new int[1];
//			ctids[0]=ctid;
//		}
////		//根节点 没加进去
//		if(ctid==1){
//			ctids=new int[]{1};
//		}
		//statisticCourseDao
//		if(isExprot()){//导出
//			courses = statisticCourseDao.listCourseBYCtypePage(ctypeTree,ctids,name);
//			//courses = courseDao.listCombinationCourse(ctypeTree, course, getSessionIntValue(ElConstants.SESSION_ROLE),getPageNow(), getPageSize());
//			return "course_searchlist_EXCEL";
//		}else{
//			courses = statisticCourseDao.listCourseBYCtypePage(ctypeTree,ctids,name,getPageNow(), getPageSize());
//			count   = statisticCourseDao.listCourseBYCtypePageCount(ctypeTree,1,name,getPageNow(), getPageSize());
//			//courses = courseDao.listCombinationCourse(ctypeTree, course, getSessionIntValue(ElConstants.SESSION_ROLE),getPageNow(), getPageSize());
//			//count=courseDao.listCombinationCourseCount(ctypeTree, course,getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(), getPageSize());
//		}
		String name = course == null ? "" : course.getName();
		if(ctype==null||ctype.getId()<=0){
	        ctype=ctypeTree;
	    }else{
	        ctype=ctypeDao.getCtypeById(ctype.getId());
	    }
		if(isExprot()){//导出
			courses = statisticCourseDao.listCourseBYCtypePage(ctype,name);
			return "course_searchlist_EXCEL";
		}else{
			courses = statisticCourseDao.listCourseBYCtypePage(ctype,name,getPageNow(), getPageSize());
			count   = statisticCourseDao.listCourseBYCtypePageSize(ctype,name);
		}
		return "course_searchlist";
	}

	public String course_stat_view() throws ElException {
		course = courseDao.getCourseById(course.getId());
		return "course_stat_view";
	}
	
	public String course_user_list() throws ElException {
		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype.getId();
		int  cid  = course == null ? 0 : course.getId();  
		String name = elUser == null ? "" : elUser.getRealname();
		int classid=course==null?-1:course.getClassid();
		sta = this.getSta();
		if(sta!=0){
			classid=sta;
		}
		if(course==null){
			course=new Course();
		}
		if(course.getEroom()==null){
			course.setEroom(new ExamRoom());
			course.getEroom().setTitle("");
		}
		if(isExprot()){//导出
			myCourses = statisticCourseDao.course_user_list_BYCtypePage(ctypeTree, ctid,name, cid);
			return "course_userList_EXCEL";
		}else{//course_user_list_BYCtypePage
			//myCourses = statisticCourseDao.course_user_list_BYCtypePage(ctypeTree, ctid,name, cid, getPageNow(), getPageSize());
			//myCourses = statisticCourseDao.course_user_list_BYCtypePage(ctypeTree, ctid,name, cid,classid, getPageNow(), getPageSize(),course.getEroom().getTitle());
			List<MyCourse> myCoursesList = statisticCourseDao.course_user_list_BYCtypePageCount(ctypeTree, ctid,name, cid,classid,course.getEroom().getTitle());
			if(myCourses==null){
				myCourses=new ArrayList<MyCourse>();
			}
			int pageNow=getPageNow();
			if(getPageNow()>myCoursesList.size()){
				pageNow=myCoursesList.size()+1;
			}
			//List<ExamRoom> ers = new ArrayList<ExamRoom>();
			//ExamRoom er = new ExamRoom();
			for(int i=getPageSize()-1;i<pageNow-1;i++){
				myCourses.add(myCoursesList.get(i));
			//	ELUser user = myCoursesList.get(i).getUser();
			//	int userid = user.getId();
			//	List<Integer> roomids = statisticCourseDao.getroomid(userid);
			//	for(int j=0;j<roomids.size();j++){
			//		ers = eroomDao.gettitles(roomids.get(j),cid);
			//	}
			}
			//count = statisticCourseDao.course_user_list_BYCtypeCount(ctypeTree, ctid,name, cid);
			//count = statisticCourseDao.course_user_list_BYCtypeCount(ctypeTree, ctid,name, cid,classid);
			//count = statisticCourseDao.course_user_list_BYCtypePageCount(ctypeTree, ctid,name, cid,classid,course.getEroom().getTitle()).size();
			count=myCoursesList.size();
			//count=myCourses.size();
		}
		//myCourses = statisticCourseDao.course_user_list_BYCtypePage(ctypeTree, ctid,name, cid, getPageNow(), getPageSize());
		//count = statisticCourseDao.course_user_list_BYCtypeCount(ctypeTree, ctid,name, cid);
		//获取课程id，查出该课程所有所在班级
		List<ElClass> classList=new EroomDaoImpl().getStudyCourseInClass(cid);
		//classList.add(new ElClass(0,"单独分配而来"));
		getRequest().setAttribute("classList", classList);
		//查出课程的所有考场（除考核考试）
		List<ExamRoom> eroomList=new EroomDaoImpl().getExamRoomByCourseid(cid);
		getRequest().setAttribute("eroomList", eroomList);
		
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		for(int i=0;i<myCourses.size();i++){
			int courseid = cid;
			classid = myCourses.get(i).getClassId();
			Course c = courseDao.getCourseById(courseid);
			if(c.getExurl()!=null&&c.getExurl().contains("Course-")){
				int finish = courseDao.getUserSCInfo(userid+"",c.getExurl(),"completed");
				int all = courseDao.getSCItemInfo(c.getExurl())-1;
				myCourses.get(i).setProcess((float)finish/(float)all*100);
				int courseduring = myCourses.get(i).getCourse().getDuring();
				double time = (courseduring*((float)finish/(float)all));
				int passtime = (int)time;
				myCourses.get(i).setPasstime(passtime);
				int passtime2 = courseDao.getSCPasstime(courseid,classid);
				myCourses.get(i).setPasstime2(passtime2/60);
			}
		}
		return "course_user_list";
	}
	
	public String course_user_detail_list() throws ElException {
		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype.getId();
		int  cid  = course == null ? 0 : course.getId();  
		String name = elUser == null ? "" : elUser.getRealname();
		if(course==null){
			course=new Course();
		}
		if(course.getEroom()==null){
			course.setEroom(new ExamRoom());
			course.getEroom().setTitle("");
		} 
		myCourses = statisticCourseDao.course_user_list_BYCtypePage(ctypeTree, ctid,name, cid, getPageNow(), getPageSize());
		if(myCourses==null){
			myCourses=new ArrayList<MyCourse>();
		} 
		count = statisticCourseDao.course_user_list_BYCtypeCount(ctypeTree, ctid,name, cid);  
		return "course_user_detail_list";
	}


	public String ctime_user_searchInit() throws ElException {
		int roleid=getSessionIntValue(ElConstants.SESSION_ROLE);
		if(roleid==1){
			depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		}else{
			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
		}
		return "ctime_user_searchInit";
	}

	public String ctime_user_searchlist() throws ElException {
//		elUsers = statisticCourseDao.getStatCtimeUserByDep(department.getId(),
//				sub_department, elUser, getPageNow(), getPageSize());
//		count = statisticCourseDao.getStatCtimeUserByDepCount(department.getId(),
//				sub_department, elUser);
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
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		sub_department = elUser == null ? 1 : sub_department;
		elUsers = statisticCourseDao.getStatCtimeUserByDep(department,sub_department, elUser, getPageNow(), getPageSize());
		count = statisticCourseDao.getStatCtimeUserByDepCount(department,sub_department, elUser);
		return "ctime_user_searchlist";
	}

	public String ctime_user_list() throws ElException {
		elUsers = statisticCourseDao.getStatCtimeUserByDep(department.getId(),
				sub_department, elUser);
		return "ctime_user_list";
	}

	public String ctime_detail_searchlist() throws ElException {
//		elUsers = statisticCourseDao.getStatCtimeUserByDep(department.getId(),
//				sub_department, elUser, getPageNow(), getPageSize());
//		if (null != elUsers)
//			for (int i = 0; i < elUsers.size(); i++) {
//				elUsers.get(i).setMyCourses(
//						statisticCourseDao.listMyCourse(elUsers.get(i).getId()));
//			}
//		count = statisticCourseDao.getStatCtimeUserByDepCount(department.getId(),
//				sub_department, elUser);
		department=departmentDao.getDepById(department.getId());
		sub_department = elUser == null ? 1 : sub_department;
		elUsers = statisticCourseDao.getStatCtimeUserByDep(department,sub_department, elUser, getPageNow(), getPageSize());
		count = statisticCourseDao.getStatCtimeUserByDepCount(department,sub_department, elUser);
		if (null != elUsers){
			for (int i = 0; i < elUsers.size(); i++) {
				elUsers.get(i).setMyCourses(
						statisticCourseDao.listMyCourse(elUsers.get(i).getId()));
			}
		}
		return "ctime_detail_searchlist";
	}
	/**
	 * 统计学员培训班学习和章节练习轨迹
	 * @return
	 * @throws ElException
	 */
	
	public String statisticStudyLearnLocus() throws ElException {
		int classid=course==null?0:course.getClassid();
		int courseid=course==null?0:course.getId();
		myCpages = statisticCourseDao.statisticStudyLearnLocus(elUser.getId(),classid,courseid, getPageNow(), getPageSize());
		count = statisticCourseDao.statisticStudyLearnLocusSize(elUser.getId(),classid,courseid);
		if(Return!=null)
			Return = Return.replaceAll("xyzzyx", "&");
		return "statisticStudyLearnLocus";
	}

	public String ctime_detail_list() throws ElException {
		elUsers = statisticCourseDao.getStatCtimeUserByDep(department.getId(),
				sub_department, elUser);
		if (null != elUsers)
			for (int i = 0; i < elUsers.size(); i++) {
				elUsers.get(i).setMyCourses(
						statisticCourseDao.listMyCourse(elUsers.get(i).getId()));
		}
		// if (null != elUsers)
		// for (int i = 0; i < elUsers.size(); i++) {
		// elUsers.get(i).setMyCourses(
		// studyCourseDao.listMyCourse(elUsers.get(i).getId(), 1));
		// }
		return "ctime_detail_list";
	}


	public String cnote_stat_searchinit() throws ElException {
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);

		return "cnote_stat_search";
	}

	public String cnote_stat_search() throws ElException {
		elUsers = statisticCourseDao.getStatCnoteUserByDep(department.getId(),
				sub_department, elUser, getPageNow(), getPageSize());
		// if (null != elUsers)
		// for (int i = 0; i < elUsers.size(); i++) {
		// elUsers.get(i).setMyCnotes(
		// studyCourseDao.listMyCnotes(elUsers.get(i).getId()));
		// }
		count = statisticCourseDao.getStatCnoteUserByDepCount(department.getId(),
				sub_department, elUser);

		return "cnote_stat_searchlist";
	}

	private CourseNote cnote;

	public String cnote_stat_view() throws ElException {
		elUser = userDao.getUserById(elUser.getId());
		cnote = studyCourseDao.getCnoteByid(cnote.getId());
		return "cnote_stat_view";
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

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}

	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public StatisticCourseDao getStatisticCourseDao() {
		return statisticCourseDao;
	}

	public void setStatisticCourseDao(StatisticCourseDao statisticCourseDao) {
		this.statisticCourseDao = statisticCourseDao;
	}

	public List<MyCourse> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}

	public CourseType getCtypeTree() {
		return ctypeTree;
	}

	public void setCtypeTree(CourseType ctypeTree) {
		this.ctypeTree = ctypeTree;
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

	public List<CourseNote> getCnotes() {
		return cnotes;
	}

	public void setCnotes(List<CourseNote> cnotes) {
		this.cnotes = cnotes;
	}

	public StudyCourseDao getStudyCourseDao() {
		return studyCourseDao;
	}

	public void setStudyCourseDao(StudyCourseDao studyCourseDao) {
		this.studyCourseDao = studyCourseDao;
	}

	public CourseType getCtype() {
		return ctype;
	}

	public void setCtype(CourseType ctype) {
		this.ctype = ctype;
	}

	public CourseNote getCnote() {
		return cnote;
	}

	public void setCnote(CourseNote cnote) {
		this.cnote = cnote;
	}

	public boolean isExprot() {
		return exprot;
	}

	public void setExprot(boolean exprot) {
		this.exprot = exprot;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

}
