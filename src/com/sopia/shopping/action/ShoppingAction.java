package com.sopia.shopping.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.balance.dao.BalanceDao;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElGroup;
import com.sopia.duman.entities.Station;
import com.sopia.newsandmess.dao.impl.MessageDaoImpl;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.shopping.dao.ShoppingDao;
import com.sopia.shopping.entities.ClassOrder;
import com.sopia.shopping.entities.CourseOrder;
import com.sopia.statman.dao.StatisticCourseDao;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyCourse;

public class ShoppingAction extends BaseAction {
	private ShoppingDao shoppingDao;
	private CourseType ctypeTree;
	private CourseTypeDao ctypeDao;
	private Course course;
	private CourseType ctype;
	private CourseDao courseDao; 
	private List<Course> courses;
	private Department depTree;
	private Department deprTree;
	private Integer deptid;
	private List<ExamRoom> examRooms;
	private List<ELUser> elusers;
	private String starttime;
	private String endtime;
	private ELUser elUser;
	private ExamRoom examRoom;
	private String userids;
	private int status;
	private float price;
	private float zongprice;
	private List<MyCourse> myCourses;//分配的学员
	private int orderID;
	private CourseOrder corder;//课程订单
	private List<CourseOrder> myOrders;
	private Timestamp stime;//开始时间
	private Timestamp otime;//结束时间
	private int dstatus  ;//判断删除操作状态
	private int all  ;//判断是否要得到全部信息状态
	private String elclassId;//培训班ID
	private StatisticCourseDao statisticCourseDao;
	private IndexDataUtil indexDataUtil;//首页数据显示帮助类
	private int astatus;
	private int count;
	//培训班
	private ElClType cltypeTree;
	private ElClTypeDao elClTypeDao;
	private ElClType cltype;
	private int sublibs;
	private ElClass elClass;
	private List<ElClass> elclasses;
	private ClassDao classDao;  
	private Department department;
	private ElClass elclass;
	private int DBMethods;
	private List<ElClass> elClasss;
	private int sub_department;
	private List<ExamPaper> examPapers;
	private	EroomDao eroomDao;
	private int ajax;
	private ClassOrder classOrder;
	private List<ClassOrder> classOrders;
	

	
	private BalanceDao 		balanceDao;
	private float 			balance;
	private int classorcourse;
	//新的培训班列表页
	private String Return;
	private int state;
	private List<ElGroup> group1;
	private List<ElGroup> group2;
	private RoleDao roleDao;
	private List<Course> bxCourses;
	private ELClassRegistration elRegistration;
	private String[] jztj;//工种条件 
	private String[] dstj;//地市条件
	private String[] zwtj;//职务条件
	private String[] zjtj;//职级条件
	private String[] gwtj;//岗位条件
	private String[] treeType;//部门
	private List<Course> xxCourses;
	
	private List<MyClass> myClasses;
	
	
	private boolean falgdep;
	private List treeAllId;
	private StudyQuizDao 	studyQuizDao;
	
	//10月6日
	private String ids;
	
	private Station stTree;
	private Station station;
	
	public Station getStTree() {
		return stTree;
	}

	public void setStTree(Station stTree) {
		this.stTree = stTree;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public boolean isFalgdep() {
		return falgdep;
	}

	public void setFalgdep(boolean falgdep) {
		this.falgdep = falgdep;
	}

	public List getTreeAllId() {
		return treeAllId;
	}

	public void setTreeAllId(List treeAllId) {
		this.treeAllId = treeAllId;
	}

	public List<MyClass> getMyClasses() {
		return myClasses;
	}

	public void setMyClasses(List<MyClass> myClasses) {
		this.myClasses = myClasses;
	}

	public List<Course> getBxCourses() {
		return bxCourses;
	}

	public void setBxCourses(List<Course> bxCourses) {
		this.bxCourses = bxCourses;
	}

	public ELClassRegistration getElRegistration() {
		return elRegistration;
	}

	public void setElRegistration(ELClassRegistration elRegistration) {
		this.elRegistration = elRegistration;
	}

	public String[] getJztj() {
		return jztj;
	}

	public void setJztj(String[] jztj) {
		this.jztj = jztj;
	}

	public String[] getDstj() {
		return dstj;
	}

	public void setDstj(String[] dstj) {
		this.dstj = dstj;
	}

	public String[] getZwtj() {
		return zwtj;
	}

	public void setZwtj(String[] zwtj) {
		this.zwtj = zwtj;
	}

	public String[] getZjtj() {
		return zjtj;
	}

	public void setZjtj(String[] zjtj) {
		this.zjtj = zjtj;
	}

	public String[] getGwtj() {
		return gwtj;
	}

	public void setGwtj(String[] gwtj) {
		this.gwtj = gwtj;
	}

	public String[] getTreeType() {
		return treeType;
	}

	public void setTreeType(String[] treeType) {
		this.treeType = treeType;
	}

	public List<Course> getXxCourses() {
		return xxCourses;
	}

	public void setXxCourses(List<Course> xxCourses) {
		this.xxCourses = xxCourses;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public List<ElGroup> getGroup1() {
		return group1;
	}

	public void setGroup1(List<ElGroup> group1) {
		this.group1 = group1;
	}

	public List<ElGroup> getGroup2() {
		return group2;
	}

	public void setGroup2(List<ElGroup> group2) {
		this.group2 = group2;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public int getClassorcourse() {
		return classorcourse;
	}

	public void setClassorcourse(int classorcourse) {
		this.classorcourse = classorcourse;
	}

	public BalanceDao getBalanceDao() {
		return balanceDao;
	}

	public void setBalanceDao(BalanceDao balanceDao) {
		this.balanceDao = balanceDao;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public float getZongprice() {
		return zongprice;
	}

	public void setZongprice(float zongprice) {
		this.zongprice = zongprice;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<ClassOrder> getClassOrders() {
		return classOrders;
	}

	public void setClassOrders(List<ClassOrder> classOrders) {
		this.classOrders = classOrders;
	}

	public ClassOrder getClassOrder() {
		return classOrder;
	}

	public void setClassOrder(ClassOrder classOrder) {
		this.classOrder = classOrder;
	}

	public StatisticCourseDao getStatisticCourseDao() {
		return statisticCourseDao;
	}

	public void setStatisticCourseDao(StatisticCourseDao statisticCourseDao) {
		this.statisticCourseDao = statisticCourseDao;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	public int getAstatus() {
		return astatus;
	}

	public void setAstatus(int astatus) {
		this.astatus = astatus;
	}

	public int getAjax() {
		return ajax;
	}

	public void setAjax(int ajax) {
		this.ajax = ajax;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public ElClass getElclass() {
		return elclass;
	}

	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}

	public int getDBMethods() {
		return DBMethods;
	}

	public void setDBMethods(int methods) {
		DBMethods = methods;
	}

	public List<ElClass> getElClasss() {
		return elClasss;
	}

	public void setElClasss(List<ElClass> elClasss) {
		this.elClasss = elClasss;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public ElClType getCltypeTree() {
		return cltypeTree;
	}

	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}

	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}

	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}

	public ElClType getCltype() {
		return cltype;
	}

	public void setCltype(ElClType cltype) {
		this.cltype = cltype;
	}

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public ElClass getElClass() {
		return elClass;
	}

	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}

	public List<ElClass> getElclasses() {
		return elclasses;
	}

	public void setElclasses(List<ElClass> elclasses) {
		this.elclasses = elclasses;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public int getAll() {
		return all;
	}

	public void setAll(int all) {
		this.all = all;
	}

	public int getDstatus() {
		return dstatus;
	}

	public void setDstatus(int dstatus) {
		this.dstatus = dstatus;
	}

	public Timestamp getStime() {
		return stime;
	}

	public void setStime(Timestamp stime) {
		this.stime = stime;
	}

	public Timestamp getOtime() {
		return otime;
	}

	public void setOtime(Timestamp otime) {
		this.otime = otime;
	}

	public List<CourseOrder> getMyOrders() {
		return myOrders;
	}

	public void setMyOrders(List<CourseOrder> myOrders) {
		this.myOrders = myOrders;
	}

	public CourseOrder getCorder() {
		return corder;
	}

	public void setCorder(CourseOrder corder) {
		this.corder = corder;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public List<MyCourse> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public Department getDeprTree() {
		return deprTree;
	}

	public void setDeprTree(Department deprTree) {
		this.deprTree = deprTree;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}

	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}

	public List<ELUser> getElusers() {
		return elusers;
	}

	public void setElusers(List<ELUser> elusers) {
		this.elusers = elusers;
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

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public ShoppingDao getShoppingDao() {
		return shoppingDao;
	}

	public void setShoppingDao(ShoppingDao shoppingDao) {
		this.shoppingDao = shoppingDao;
	}
/**
 * 得到课程列表，返回分配课程页面
 * @return
 * @throws ElException
 */
	
	public String course_assignList3() throws ElException {
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
//		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
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
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
 		int depid = 1 ;
		if(deptid == null){  
				depid = depTree.getId(); 
		}else{ 
			depid = deptid;
		}  
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) //取消掉课程类别权限书
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
//		else { 
//			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID),"use",ElConstants.TREE_FIANL, true);
//		}
		//int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();  
		

		String name = course == null ? "" : course.getName(); 
//		courses = courseDao.listAllCourseFromThisStatus(ctypeTree,depid, getSessionIntValue(ElConstants.SESSION_ROLE),name, ctid,
//				getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9");
//		count = courseDao.listAllCourseSizeFromThisStatus(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,"0,1,2,3,4,5,6,7,8,9");
		if(ctype==null||ctype.getId()<=0){
			ctype=ctypeTree;
		}else{
			ctype=ctypeDao.getCtypeById(ctype.getId());
		}
		courses = courseDao.listCourseFromThisStatus(ctype,name,
				getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9");
		//得到可操作待定学员人数

		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype.getId();
		for (Course cs : courses) {
			
			cs.setUserCount(shoppingDao.course_user_list_BYCtypeCount(elUser,deprTree,depid,ctypeTree, ctid, cs.getId(),getSessionIntValue(ElConstants.SESSION_ROLE))); 
			
		}
		count = courseDao.listCourseSizeFromThisStatus(ctype,name,"0,1,2,3,4,5,6,7,8,9");
		return "course_assignList3";
	}
	/**
	 * 课程审核
	 * 
	 * @return
	 * @throws ElException
	 */
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
		return course_assignList3();  
	}
	/**
	 * 分配课程，得到可操作学生列表
	 * @return
	 * @throws ElException
	 */
	public String shoping_assigntoUsersInit() throws ElException {

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
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
 		int depid = 1 ;
		if(deptid == null){  
				depid = depTree.getId(); 
		}else{ 
			depid = deptid;
		}  
		
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
		course=shoppingDao.getCourseById(cid);
		if(course==null){
			course=new Course(cid);
		}else{
			//int data=course.getCreatetime().getDate();
			//int time=course.getCreatetime().getTimezoneOffset();
			//System.out.println(time);
		}
		//获取课程所有场次信息
		examRooms=courseDao.getRoomsByCourseid(course.getId());///////
		//getRequest().setAttribute("rooms", rooms);
		elusers = shoppingDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,deprTree,getSessionIntValue(ElConstants.SESSION_ROLE));
		count =shoppingDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,deprTree,getSessionIntValue(ElConstants.SESSION_ROLE));

		
		return "shoping_assigntoUsersInit";
	}
	/**
	 * 分配选中的学生给指定的课程
	 * @return
	 * @throws ElException
	 */
	public String shopping_newassigntoUsers() throws ElException {
		//获取课程所绑定的场次
		int roomid=0;
		if(examRoom!=null){
			roomid=examRoom.getId();
		}
		int cid = (course == null) ? 0 : course.getId();
		String user[] = this.getUserids().split(",");
		for (int i = 0; i < user.length; i++) {
			String users[] =user[i].split(":");
			if(users[1].equals("未分配")){
				//courseDao.assignedUser(cid, Integer.valueOf(users[0]),status);//分配学员  strtus = 3 先进入人员审核状态,审核通过，学院端才显示
				//courseDao.assignedUser(cid, Integer.valueOf(users[0]),status,course.getRoomstart(),course.getRoomend());
				shoppingDao.assignedUser(cid, Integer.valueOf(users[0]),status,roomid);

			}
		}
		return shoping_assigntoUsersInit();
	}
	/**
	 * 取消分配选中的学生给指定的课程
	 * @return
	 * @throws ElException
	 */
	public String shopoing_newunassigntoUsers() throws ElException {
		int cid = (course == null) ? 0 : course.getId();
		String user[] = this.getUserids().split(",");
		for (int i = 0; i < user.length; i++) {
			String users[] =user[i].split(":");
			if(users[1].equals("已分配")){
				courseDao.unassignedUser(cid,  Integer.valueOf(users[0]));
			}
		}
		return shoping_assigntoUsersInit();
	}
	/**
	 * 分配所有搜索结果学生给指定课程
	 * @return
	 * @throws ElException
	 */
	public String shopping_allassigntoUsers() throws ElException {
		//获取课程所绑定的场次
		int roomid=0;
		if(examRoom!=null){
			roomid=examRoom.getId();
		}
		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		stTree = stationDao.getStTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
				true);
		int cid = (course == null) ? 0 : course.getId();
		elusers = courseDao.listAssignedUser(9999999, 1,depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,depTree,stTree,getSessionIntValue(ElConstants.SESSION_ROLE));
		for (ELUser user:elusers) {
			//courseDao.assignedUser(cid, user.getId(),status);
			if(user.getIsAssign().equals("未分配")){
				shoppingDao.assignedUser(cid,user.getId(),status);//先得判断是否分配
			}
		}
		return shoping_assigntoUsersInit();
	}
	/**
	 * 待定学员页面的取消的分配
	 * @return
	 * @throws NumberFormatException
	 * @throws ElException
	 */
	public  String Shopping_user_detail_unassigntoUsers() throws NumberFormatException, ElException{
		if(course!=null){
		int cid =  course.getId();
		String user[] = this.getUserids().split(",");
		for (int i = 0; i < user.length; i++) {
			
				courseDao.unassignedUser(cid,  Integer.valueOf(user[i]));
			
		}
		}
		return Shopping_user_detail_list();
		
	}
	/**
	 * 得到该管理员能操作的该课程已分配未订购用户列表
	 * @return
	 * @throws ElException
	 */
	public String Shopping_user_detail_list() throws ElException {

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
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
 		int depid = 1 ;
		if(deptid == null){  
				depid = depTree.getId(); 
		}else{ 
			depid = deptid;
		}  
		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype.getId();
		int  cid  = course == null ? 0 : course.getId();  
		course=shoppingDao.getCourseById(cid);
		if(course==null){
			course=new Course(cid);
		}
		examRooms=courseDao.getRoomsByCourseid(course.getId());
		myCourses = shoppingDao.course_user_list_BYCtypePage(elUser ,deprTree,depid,ctypeTree, ctid, cid, getPageNow(), getPageSize(),getSessionIntValue(ElConstants.SESSION_ROLE));
		
		if(myCourses==null){
			myCourses=new ArrayList<MyCourse>();
		} 
		count = shoppingDao.course_user_list_BYCtypeCount(elUser,deprTree,depid,ctypeTree, ctid, cid,getSessionIntValue(ElConstants.SESSION_ROLE));  
		return "Shopping_user_detail_list";
	}
	
	public 	String shopping_course_order() throws ElException{
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
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
 		int depid = 1 ;
		if(deptid == null){  
				depid = depTree.getId(); 
		}else{ 
			depid = deptid;
		}  
		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype.getId();
		int  cid  = course == null ? 0 : course.getId();  

//		
//		if(course.getEroom()==null){
//			course.setEroom(new ExamRoom());
//			course.getEroom().setTitle("");
//		} 
		myCourses = shoppingDao.course_user_list_BYCtypePage(elUser,deprTree,depid,ctypeTree, ctid, cid, 9999999, 1,getSessionIntValue(ElConstants.SESSION_ROLE));
		price=shoppingDao.getPeiceValue(cid);
		count=myCourses.size();
		zongprice=price*count;
		return "shopping_course_order_success";
	}
	/**
	 * 确认课程分配学员，生成订单，插入学生，课程订单关系表 返回订单页
	 * @return
	 * @throws ElException 
	 */
	public String mark_course_order() throws ElException{
		
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
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
 		int depid = 1 ;
		if(deptid == null){  
				depid = depTree.getId(); 
		}else{ 
			depid = deptid;
		}  
		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_USE_TYPE");
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype.getId();
		int  cid  = course == null ? 0 : course.getId();  

//		
//		if(course.getEroom()==null){
//			course.setEroom(new ExamRoom());
//			course.getEroom().setTitle("");
//		} 
		myCourses = shoppingDao.course_user_list_BYCtypePage(elUser,deprTree,depid,ctypeTree, ctid, cid, 9999999, 1,getSessionIntValue(ElConstants.SESSION_ROLE));
		
		//得到课程价格
		
		
			price=shoppingDao.getPeiceValue(cid);

		int size=myCourses.size();
		if(size!=0){
		//根据用户ID生成订单
		int orderid=shoppingDao.markorder(getSessionIntValue(ElConstants.SESSION_USERID),0,size*price,0,null,null,null);
		//在人员课程订单关系表中插入记录
		for (MyCourse user:myCourses) {
			//courseDao.assignedUser(cid, user.getId(),status);
			
				shoppingDao.addUserOrder(user.getUser().getId(), cid, orderid);//先得判断是否分配
		}
			
		
			//生成订单详情
			shoppingDao.markorderInfo(cid, orderid, 1, price, size, 0, 0);
			//得到订单信息
			corder=new CourseOrder();
			corder.setId(orderid);
			corder.setCount(size);
			corder.setPrice(price);
			return "mark_course_order";
		}
		else{
			return  Shopping_user_detail_list();
		}
		
	}
	/**
	 * 得到当前课程订单内学生
	 * @return
	 * @throws ElException
	 */
	public String getOrderUserList() throws ElException{
		
		myCourses=shoppingDao.getOrderUserList(corder.getId(), getPageNow(), getPageSize());
		count = shoppingDao.getOrderUserListCount(corder.getId());
		return  "getOrderUserList";
	}
	
	/**
	 * 得到当前用户的所有分配课程订单
	 * @return
	 * @throws ElException
	 */
	public String getMyOrderCourseList() throws ElException{
		if(dstatus==1){
			shoppingDao.deleOrderCourseClass(corder.getId());
			shoppingDao.deleOrderCourseClassOrder(corder.getId());
			shoppingDao.deleOrder(corder.getId());
			shoppingDao.deleOrderInfo(corder.getId());
			dstatus=0;
		}
		depTree = departmentDao.getDepTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		deprTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		int depid = 1 ;
		if(deptid != null){  
			depid = deptid;
		} 
		if(all==1){
			myOrders=shoppingDao.getMyOrderCourseList(deprTree,depid,0,corder,stime,otime, getPageNow(), getPageSize());
	        count=shoppingDao.getMyOrderCourseListCount(deprTree,depid,0, corder, stime, otime);
		}else{
		        myOrders=shoppingDao.getMyOrderCourseList(deprTree,1,getSessionIntValue(ElConstants.SESSION_USERID),corder,stime,otime, getPageNow(), getPageSize());
		        count=shoppingDao.getMyOrderCourseListCount(deprTree,1,getSessionIntValue(ElConstants.SESSION_USERID), corder, stime, otime);
		}
			return "getMyOrderCourseList";
		
	}
	/**
	 * 支付订单信息提示页
	 * @return
	 * @throws ElException
	 */
	public String  getcourseOrderinfo() throws ElException{
		balance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
		corder=shoppingDao.getcourseOrderinfo(corder.getId());
		return "getcourseOrderinfo_success";
		
	}
	/**
	 * 培训班选课
	 * @return
	 * @throws ElException
	 */
	public String shopping_elclass_course_selectList() throws ElException {
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		
		//ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_OP_TYPE");
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();
		String name = course == null ? "" : course.getName();
		
		courses = shoppingDao.listAllSelectCourse(ctypeTree,depid, name, ctid,getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_HASOPENED,Integer.valueOf(elclassId),getSessionIntValue(ElConstants.SESSION_ROLE));
		count = shoppingDao.listAllSelectCourseSize(ctypeTree,depid, name, ctid,CourseConstants.COURSE_STATUS_HASOPENED,Integer.valueOf(elclassId),getSessionIntValue(ElConstants.SESSION_ROLE));
		return "shopping_course_selectList";
	}
	/**
	 * 培训班人员分配
	 * @return
	 * @throws ElException
	 */
	public String shopping_elclass_assignlist2() throws ElException {//第二种流程分配   
//		int typeid = 1;
//		if(sublibs != 0){
//			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		}
//		if(typeid == 0){
//			typeid = 1;
//		}
		
//		int deptId = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);  
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
//		elclasses = classDao.getClassesList2(cltypeTree, deptId, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE),"0,2", getPageNow(), getPageSize());
//		count = classDao.getClassesSize2(cltypeTree, deptId, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE));
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
		elclasses = classDao.getClassList(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8","0,2", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		return "shopping_elclass_assignlist2";
	}
	/**
	 * 得到可分配人员
	 * @return
	 * @throws ElException
	 */
	public String shopping_elclass_assign2userInit() throws ElException { 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		}else{
			depTree = departmentDao.getDepTree_level1(
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
		int depid = 1;
		if(department==null){ 
			if(depTree.getId() == -2){
				depid = -2;
				department = new Department(-2); 
			}else{
				department=new Department(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
			}
		}else{ 
			depid = department.getId();
		}  
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		int cid = (elclass == null) ? 0 : elclass.getId();
		if(DBMethods == 0){//按人员信息搜索
			elusers = classDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,depTree,stTree);
			count =classDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,depTree,stTree);
		}else if(DBMethods == 1){ //按培训班信息搜索
			if( elClasss == null || elClasss.size() == 0){ 
				setElmessage("没有选择培训班");
				return "error";
			}
			elClass = classDao.getClassById(elClasss.get(0).getId());
			elusers = shoppingDao.gettoClassInfoselectUser(depTree,department,"study_class",cid, elClass.getId(), elUser,this.getStarttime(),this.getEndtime(),getPageNow(), getPageSize());
			count = shoppingDao.gettoClassInfoselectUserSize(depTree,department,"study_class",cid,elClass.getId(), elUser,this.getStarttime(),this.getEndtime()); 
		}else if(DBMethods == 2){//按考场信息搜索
			if( examRooms == null || examRooms.size()== 0 ){ 
				setElmessage("没有选择考场");
				return "error";
			}
			examRoom = eroomDao.getExamRoomByid(examRooms.get(0).getId());
			elusers = shoppingDao.gettoEroomInfoselectUser(depTree,department,"study_class",cid,examRoom.getId(), elUser,this.getStarttime(),this.getEndtime(),getPageNow(), getPageSize());
			count = shoppingDao.gettoEroomInfoselectUserSize(depTree,department,"study_class",cid,examRoom.getId(), elUser,this.getStarttime(),this.getEndtime());
			examPapers = eroomDao.getEroomeps(examRoom.getId()); 
		}

		elclass = classDao.getClassById(cid);
		return "shopping_elclass_assign2userInit";
	} 
	/**
	 * 培训班分配选中学员
	 * @return
	 * @throws ElException
	 */
	public String shopping_elclass_newassign2user_add() throws ElException {
		String user[] = this.getUserids().split(",");
		for (int i = 0; i < user.length; i++) {
			String users[] =user[i].split(":");
			if(users[1].equals("未分配")){
				//classDao.assign2userAdd(Integer.valueOf(users[0]),elclass.getId());
				classDao.assign2userAdd3(Integer.valueOf(users[0]),elclass.getId(),ClassConstants.CLASS_SQFS_FP);
			}
		}
//		//如果是申请式的培训班，该培训班的结业课程的考场自动分配人员
//		//把人员分配到该培训班中所有考场
//		//1.获取该培训班中所有被绑定的考场
//		//2.获取每个考场中所有的试卷
//		//3.对每张试卷进行分配人员
//		if(elclass.getIsApplication()==1){
//			StudyQuizDao studyQuizDao=new StudyQuizDaoImpl();
//			List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elclass.getId());
//			List<ExamPaper> examPapers=null;
//			for (int i = 0; i < eroomList.size(); i++) {
//				examPapers = eroomDao.getEroomepwithusizes(eroomList.get(i).getId());//获取该考场中的所有试卷信息
//				for (int k = 0; k < user.length; k++) {
//					String users[] =user[k].split(":");
//					if(users[1].equals("未分配")){
//						//classDao.assign2userAdd3(Integer.valueOf(users[0]),elclass.getId(),ClassConstants.CLASS_SQFS_FP);//这里分配了 上面的可以注掉了
//					
//						for (int j = 0; j < examPapers.size(); j++) {
////							if (!studyQuizDao.hasInQuizPaper(Integer.valueOf(users[0]), eroomList.get(i).getId(), // 检测是否已经进入考场
////									examPapers.get(j).getId(),elclass.getId())) {
////								studyQuizDao.intoQuizPaper(Integer.valueOf(users[0]), eroomList.get(i).getId(),
////										examPapers.get(j).getId(), elclass.getId());
////							}
//							//检测该学员是否分配了该试卷
//							if(!studyQuizDao.checkStudyExamPaper(Integer.valueOf(users[0]),
//									examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId())){
//								//添加该学员到 学员试卷表中
//								studyQuizDao.addStudyExamPaper(Integer.valueOf(users[0]),
//										examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId());
//							}
//						}
//						if (!eroomDao.checkuser2eroom(eroomList.get(i).getId(),  // 检查用户有没有分配到该考场
//								Integer.valueOf(users[0]), elclass.getId())) {
//							eroomDao.adduser2eroom( eroomList.get(i).getId(),
//									Integer.valueOf(users[0]), 1, elclass.getId(),CourseConstants.EXAMROOM_SQFS_SQ);
//						}
//					}
//				}
//			}
//		}

		elclass  = classDao.getClassById(elclass.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_ADD, elclass.getName()+"(分配学员)",
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		if(ajax==1){
			return null;
		}
		return shopping_elclass_assign2userInit();
	}
	/**
	 * 取消分配选中的学员
	 * @return
	 * @throws ElException
	 */
	public String shopping_elclass_newassign2user_delete() throws ElException {
		String user[] = this.getUserids().split(",");
		for (int i = 0; i < user.length; i++) {
			String users[] =user[i].split(":");
			if(users[1].equals("已分配")){ 
			   classDao.assign2userDelete(Integer.valueOf(users[0]),elclass.getId());
			}
		}
		
		elclass  = classDao.getClassById(elclass.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_DELETE, elclass.getName()+"(删除学员)",
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		if(ajax==1){
			return null;
		}
		return shopping_elclass_assign2userInit();
	}
	/**
	 * 分配所有搜索结果的学生给该培训班
	 * @return
	 * @throws ElException
	 */
	public String shopping_elclass_newassign2user_addAll() throws ElException { 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		}else{
			depTree = departmentDao.getDepTree_level1(
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
		int depid = 1 ; 
		if(department==null){ 
			if(depTree.getId() == -2)
				depid = -2; 
			else
				department=new Department(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
		}else{ 
			depid = department.getId();
		}  
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		int cid = (elclass == null) ? 0 : elclass.getId();
		if(DBMethods == 0){//按人员信息搜索 
			elusers = classDao.listAssignedUser(9999999, 1,depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,1,depTree,stTree);//1包含下级
		}else if(DBMethods == 1){ //按培训班信息搜索
			if( elClasss == null || elClasss.size() == 0){ 
				setElmessage("没有选择培训班");
				return "error";
			}
			elClass = classDao.getClassById(elClasss.get(0).getId());
			elusers = shoppingDao.gettoClassInfoselectUser(depTree,department,"study_class",cid, elClass.getId(), elUser,this.getStarttime(),this.getEndtime(),9999999, 1); 
		}else if(DBMethods == 2){//按考场信息搜索
			if( examRooms == null || examRooms.size()== 0 ){ 
				setElmessage("没有选择考场");
				return "error";
			}
			examRoom = eroomDao.getExamRoomByid(examRooms.get(0).getId());
			elusers = shoppingDao.gettoEroomInfoselectUser(depTree,department,"study_class",cid,examRoom.getId(), elUser,this.getStarttime(),this.getEndtime(),9999999, 1); 
		}
		for (ELUser user:elusers) {
			//classDao.assign2userAdd(user.getId(),elclass.getId());
			if(user.getIsAssign().equals("未分配")){
				//classDao.assign2userAdd(Integer.valueOf(users[0]),elclass.getId());
				classDao.assign2userAdd3(user.getId(),elclass.getId(),ClassConstants.CLASS_SQFS_FP);//得先判断
			} 
		}
		
		//如果是申请式的培训班，该培训班的结业课程的考场自动分配人员
		//把人员分配到该培训班中所有考场
		//1.获取该培训班中所有被绑定的考场
		//2.获取每个考场中所有的试卷
		//3.对每张试卷进行分配人员
//		if(elclass.getIsApplication()==1){
//			StudyQuizDao studyQuizDao=new StudyQuizDaoImpl();
//			List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elclass.getId());
//			List<ExamPaper> examPapers=null;
//			for (int i = 0; i < eroomList.size(); i++) {
//				examPapers = eroomDao.getEroomepwithusizes(eroomList.get(i).getId());//获取该考场中的所有试卷信息
//				for (ELUser user:elusers) {
//					if(user.getIsAssign().equals("未分配")){
//						for (int j = 0; j < examPapers.size(); j++) {
////							if (!studyQuizDao.hasInQuizPaper(user.getId(), eroomList.get(i).getId(), // 检测是否已经进入考场
////									examPapers.get(j).getId(),elclass.getId())) {
////								studyQuizDao.intoQuizPaper(user.getId(), eroomList.get(i).getId(),
////										examPapers.get(j).getId(), elclass.getId());
////							}
//							//检测该学员是否分配了该试卷
//							if(!studyQuizDao.checkStudyExamPaper(user.getId(),
//									examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId())){
//								//添加该学员到 学员试卷表中
//								studyQuizDao.addStudyExamPaper(user.getId(),
//									examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId());
//							}
//						}
//						if (!eroomDao.checkuser2eroom(eroomList.get(i).getId(),  // 检查用户有没有分配到该考场
//								user.getId(), elclass.getId())) {
//							eroomDao.adduser2eroom( eroomList.get(i).getId(),
//									user.getId(), 1, elclass.getId(),CourseConstants.EXAMROOM_SQFS_SQ);
//						}
//					}
//				}
//			}
//		}

		elclass  = classDao.getClassById(elclass.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_ADD, elclass.getName()+"(分配所有学员)",
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		return shopping_elclass_assign2userInit();
	}
	public String shopping_elclass_dele_students() throws ElException{
			String user[] = this.getUserids().split(",");
			for (int i = 0; i < user.length; i++) {
			
				   classDao.assign2userDelete(Integer.valueOf(user[i]),elclass.getId());
				
			}
			
			elclass  = classDao.getClassById(elclass.getId());
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_DELETE, elclass.getName()+"(删除学员)",
					ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
			if(ajax==1){
				return null;
			}
			return  shopping_elclass_check_students();
	}
	/**
	 * 得到该管理员所能看到的待订购学员
	 */
	public String shopping_elclass_check_students() throws ElException {
//		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//		depTree = departmentDao.getDepTree_level1(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//				true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		depTree = departmentDao.getDepTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		int depid = department == null ? 1 : department.getId(); 
		if(department==null){
			department=new Department(1); 
		} 
		department.setId(depid);
		
		int cid = (elclass == null) ? 0 : elclass.getId(); 
		
//		elusers = classDao.listAssignedUserIsAssign(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
//		count =classDao.listAssignedUserIsAssignSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		if(elUser == null){
			elUser = new ELUser();
			elUser.setIsAssign("1");
		}
		
		elusers = shoppingDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,depTree);
		count =shoppingDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,depTree);

		return "shopping_elclass_check_students";
	}
	/**
	 * 生成培训班订单
	 * @return
	 * @throws ElException
	 */
	public String saveOrderCourseClass() throws ElException{
		//得到要订购的学员
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
			else {
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
						true);
			}
			int depid = department == null ? 1 : department.getId(); 
			if(department==null){
				department=new Department(1); 
			} 
			department.setId(depid);
			
			int cid = (elclass == null) ? 0 : elclass.getId(); 
			
//			elusers = classDao.listAssignedUserIsAssign(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
//			count =classDao.listAssignedUserIsAssignSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
			if(elUser == null){
				elUser = new ELUser();
				elUser.setIsAssign("1");
			}
			
		elusers = shoppingDao.listAssignedUser(999999, 1,depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,depTree);
		//得到订单ID
		if(elusers.size()!=0){//得到总金额 免费课程等信息
			classOrder=shoppingDao.getAllPriceCourseByClassidID(elclass.getId(), getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));	
			classOrder.setCount(elusers.size());
			classOrder.setZprice();
			orderID=shoppingDao.markorder(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), 0,classOrder.getZprice(),0,null,null,null);
		//插入学员课程班级订单关系表
		for (ELUser user:elusers) {
			shoppingDao.addUserOrderClass(user.getId(), elclass.getId(), orderID);
		}
		//得到培训班价格,免费课程数量，课程总数
					
			//生成订单详情
		shoppingDao.markorderInfo(elclass.getId(), orderID, 2, classOrder.getPrice(), elusers.size(), 0, 0);
		return "saveOrderCourseClass";
		}else{			
			return shopping_elclass_check_students();
		}
		
	}
	
	public String ClassOrderPreview() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
			else {
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
						true);
			}
			int depid = department == null ? 1 : department.getId(); 
			if(department==null){
				department=new Department(1); 
			} 
			department.setId(depid);
			
			int cid = (elclass == null) ? 0 : elclass.getId(); 
			
//			elusers = classDao.listAssignedUserIsAssign(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
//			count =classDao.listAssignedUserIsAssignSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
			if(elUser == null){
				elUser = new ELUser();
				elUser.setIsAssign("1");
			}
			
		elusers = shoppingDao.listAssignedUser(999999, 1,depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,depTree);
		//得到订单ID
		if(elusers.size()!=0){
			//得到培训班价格,免费课程数量，课程总数
			classOrder=shoppingDao.getAllPriceCourseByClassidID(elclass.getId(), getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
			classOrder.setCount(elusers.size());
			classOrder.setZprice();
			return "ClassOrderPreview";
		}else{
			return shopping_elclass_check_students();
		}
		
	}
	/**
	 * 我的培训班订单，培训班订单管理，培训班删除
	 * @return
	 * @throws ElException
	 */
	public String myClassOrder_list() throws ElException{
		if(dstatus==1){
			shoppingDao.deleClassOrder( classOrder.getId());
			
			dstatus=0;
		}
		depTree = departmentDao.getDepTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		deprTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		int depid = 1 ;
		if(deptid != null){  
			depid = deptid;
		} 
		

			if(all==0){
				classOrders = shoppingDao.getMyOrderClassList(deprTree,1,getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), 
						classOrder, stime, otime, getPageNow(), getPageSize());
				count = shoppingDao.getMyOrderClassListSize(deprTree,1,getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), classOrder, stime, otime);
			}else{

				classOrders = shoppingDao.getMyOrderClassList(deprTree,depid,0, 
						classOrder, stime, otime, getPageNow(), getPageSize());
				count = shoppingDao.getMyOrderClassListSize(deprTree,depid,0, classOrder, stime, otime);
			
				
				
			}
		
		return "myClassOrder_List";
	}
	/**
	 * 得到培训班分配订单内课程列表
	 * @return
	 * @throws ElException
	 */
	public  String  getclass_order_courseprice() throws ElException{
		
		courses = shoppingDao.getClassOrderCourseByid(elClass.getId());
		return   "getclass_order_courseprice_success";
		
	}
	/**
	 * 得到培训班分配订单内学生列表
	 * @return
	 * @throws ElException 
	 */
	public String  getclassuserinfo_list() throws ElException{
		myCourses=shoppingDao.getclassorderuserinfolist(orderID, getPageNow(), getPageSize());
		count=shoppingDao.getclassorderuserinfolistsize(orderID);
		return "getclassuserinfo_list_success";
	}
	
	//得到培训班订单信息
	public   String   getclassorderinfobyid() throws ElException{
		balance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
		classOrder=shoppingDao.getclassOrderinfo(orderID);
		
		
		return  "getclassorderinfobyid_success";
	}
	/**
	 * 重新写的课程修改， 增加了 课程所属考场的添加修改入口，验证课程是否有考场
	 * @return
	 * @throws ElException
	 */
	public String newcourse_view() throws ElException {
		course = courseDao.getCourseById(course.getId());
		examRoom=shoppingDao.getroomid(course.getId());
		return "newcourse_view";
	}
/**
	//以下为培训班分配给部门action
	public String new
	public String newallempfenpei(){
		//1.先得到部门中的人员
		//2.将部门中的人按照培训班分配的形式分配给部门表
		//3.生成订单，此订单的类型为3，属于部门分配
		//4.支付该订单，不进行扣费，只修改订单状态为已支付状态
		//5.绑定考场
		return  "";
		
		
	}
	**/
	
	/**以下为新的培训班列表 创建培训班
	 * 培训班列表
	 * @return
	 * @throws ElException
	 */
	public String newelclass_alllist() throws ElException {
//		int typeid = 1;
//		
//		String name = elClass == null ? "" : elClass.getName(); 
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
 		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
 			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
 		}else{
 			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
 		} 
// 		if(sublibs != 0){
//			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		}else{
//			typeid=cltypeTree.getId();
//		}
// 		
//		elclasses = classDao.getClassesList3(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE),"0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
//		count = classDao.getClassesSize3(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE));
// 		elclasses = classDao.listClasses(0, typeid, name, getPageNow(), getPageSize());
//		count = classDao.listClassesSize(0, typeid, name);
 		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
 		elclasses = classDao.getClassList(cltype, elClass,sublibs,"0,1,2,3,4,5,6,7,8","0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype, elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		return "elclass_alllist";
	}
	/**
	 * 培训班审核
	 * @return
	 * @throws ElException
	 */
	public String newelclass_sh() throws ElException {
		StringBuffer msg = new StringBuffer();
		//判断如果状态为1就是创建完成
		if(state == 1){
			// 获取培训班的必修课列表
			List<Course> cList = classDao.listClassCourses(elclass.getId(),0);
			for(int i = 0; i < cList.size();i++){
				//判断如果结业方式不等于考过
				if(cList.get(i).getGetcredit() != 2){
					//课程长为0就不能创建
					if(cList.get(i).getDuring() == 0){
						msg.append("<br>该培训班["+cList.get(i).getName()+"]必修课课程时长不能等于0</br>");
					}
				}
			}
			// 获取培训班的选修课
			List<Course> cList2 = classDao.listClassCourses(elclass.getId(),1);
			//全部选修课的设置学分
			int sumscroe = 0;
			for(int i = 0; i < cList2.size();i++){
				//判断如果结业方式不等于考过
				if(cList2.get(i).getGetcredit() != 2){
					//课程长为0就不能创建
					if(cList2.get(i).getDuring() == 0){
						msg.append("<br>该培训班["+cList2.get(i).getName()+"]选修课课程时长不能等于0</br>");
					}
				}
				sumscroe += cList2.get(i).getSetcredit();
			}
			elclass = classDao.getClassById(elclass.getId());
			//判断如果结业条件少于全部选修课的设置学分
			if(elclass.getOptionalcredit() > sumscroe){
				msg.append("<br>该培训班结业条件不能大于修课的设置学分</br>");
			}
			if(!"".equals(msg.toString())){
				setElmessage(msg.toString());
				return "error";
			}
		}
		
		if(Return.equals("elclass_assignlist2")&& status == 1){//分配学员页 无复核人员 需要开通复核并提交申请初审
			classDao.shUvalid(elclass.getId(), 1);//提交复核
		}
		if(Return.equals("elclass_uvalidlist")&& status == 1){//复核学员页 有复核人员 需要开通复核并提交申请初审
			classDao.shUvalid(elclass.getId(), 1);//提交复核
		}
		if(Return.equals("elclass_alllist")&& status == 1){//复核学员页 有复核人员 需要开通复核并提交申请初审
			classDao.shUvalid(elclass.getId(), 1);//提交复核
		}
		if(!classDao.checkElclassUvalid(elclass.getId())&&status != 8&&status != 9){ 
			setElmessage("该培训班没有经过复核");
			return "error";
		}
		elclass=classDao.getClassById(elclass.getId());
		//classDao.shClass(elclass.getId(), status);
		classDao.shClass(elclass.getId(), status,elclass.getIsApplication()); 
		if(status == 0) classDao.shUvalid(elclass.getId(), 0);// 修改通过时，回到制作中的状态，复核也需要回到默认值0
		if(status == 2) classDao.shUvalid(elclass.getId(), 0);// 初审不通过，回到制作中的状态，复核也需要回到默认值0
		if(status == 6) classDao.setaStatus(elclass.getId(), astatus);  
		if(status == 9)	classDao.setisNormal(elclass.getId(), 0); //删除通过时数据在学院端消失   
		if(status == 5)	classDao.setisNormal(elclass.getId(), 1); //终审通过时数据在学院端显示  
		if(status == 8) classDao.setaStatus(elclass.getId(), astatus); //删除申请  
		if(status==1){
			//申请初审
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_VALID,elclass.getName(),
					ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		}else if(status==2){
			//初审不通过
			new MessageDaoImpl().insertMessInApply(elclass.getName(),ElLoggerConstants.LOG_MOD_CLASS, 
					ElLoggerConstants.LOG_TYPE_VALID, getSessionIntValue(ElConstants.SESSION_USERID), 2);
		}else if(status==3){
			//初审通过
			new MessageDaoImpl().insertMessInApply(elclass.getName(),ElLoggerConstants.LOG_MOD_CLASS, 
					ElLoggerConstants.LOG_TYPE_VALID, getSessionIntValue(ElConstants.SESSION_USERID), 1);
			//也相当于申请终审
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_VALID2,elclass.getName(),
					ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		}else if(status==4){
			//终审不通过
			new MessageDaoImpl().insertMessInApply(elclass.getName(),ElLoggerConstants.LOG_MOD_CLASS, 
					ElLoggerConstants.LOG_TYPE_VALID2, getSessionIntValue(ElConstants.SESSION_USERID), 4);
		}else if(status==5 && !Return.equals("elclass_suspended_recovery")){
			//终审通过
			new MessageDaoImpl().insertMessInApply(elclass.getName(),ElLoggerConstants.LOG_MOD_CLASS, 
					ElLoggerConstants.LOG_TYPE_VALID2, getSessionIntValue(ElConstants.SESSION_USERID), 3);
			elusers = classDao.getelClassUser(elclass.getId());
			new MessageDaoImpl().insertMessInUser(elclass.getName(),
					ElLoggerConstants.LOG_MOD_CLASS,
					getSessionIntValue(ElConstants.SESSION_USERID),elusers ,elclass.getStarttime(),elclass.getFinishtime(),elclass.getId()); 
		}else if(status==11){
			//申请暂停
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ALTER,elclass.getName()+"(申请暂停)",
					ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
			
		}else if(Return.equals("elclass_suspended_recovery") && status==5){
			//暂停恢复
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ALTER,elclass.getName()+"(暂停恢复)",
					ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
			
		}
		return Return;
	}
	/**
	 * 添加培训班初始化
	 * @return
	 * @throws ElException
	 */
	public String newelclass_addInit() throws ElException {  
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
//		}
//		else {
//			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
//		}
		//System.out.println(elclass);
		if (StringUtils.isNotBlank(elclassId)) {
			//elclass = classDao.getClassById(getSessionIntValue(ElConstants.SESSION_USERID),Integer.valueOf(elclassId));
			elclass=classDao.getClassById(Integer.valueOf(elclassId));
		}
		//cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
//		cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//				ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),true,"CLASS_USE_TYPE");
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		int typeid = cltype == null ? cltypeTree.getId(): cltype.getId(); 
		group1 = roleDao.listGroupsBytype(1);
		group2 = roleDao.listGroupsBytype(2);
 
		if(cltypeTree.getChild().size() == 0 && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的培训班类别");
			 return "error"; 
		}
		return "elclass_add";
	}
	/**
	 * 培训班必修课程列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String newelclass_course_bx() throws ElException {
		if(StringUtils.isNotBlank(elclassId)){
			bxCourses = classDao.listClassCourses(Integer.valueOf(elclassId), CourseConstants.COURSE_STUDY_STATUS_BX);
			//bxCourses = classDao.listClassCourses2(Integer.valueOf(elclassId), CourseConstants.COURSE_STUDY_STATUS_BX);
			elclass=classDao.getClassById(Integer.valueOf(elclassId)); 
			for(int i = 0 ; i < bxCourses.size() ; i++){
				if(eroomDao.checkuserClassBindingCourse(Integer.valueOf(elclassId), bxCourses.get(i).getId())){
					examRoom = eroomDao.getClassBindingCourseByRoomId(Integer.valueOf(elclassId), bxCourses.get(i).getId());
					if(examRoom != null){
					examRoom = eroomDao.getExamRoomByid(examRoom.getId()); 
						if(examRoom != null){
							bxCourses.get(i).setEroom(examRoom);
							bxCourses.get(i).setExamName(examRoom.getTitle());//借用teacherName字段用于显示examRoom.title。  
						}
					}
				} 
			}
		} 
		return "elclass_course_bx";
	}
	/**
	 * 添加培训班
	 * @return
	 * @throws ElException
	 */
	public String newelclass_add() throws ElException {  
		elclass.setCreater(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		//判断是否新增
		if( StringUtils.isBlank(elclassId)){
			//int id = classDao.addClass(elclass);
			int id = classDao.addClass2(elclass);//添加
			elclass = classDao.getClassById(id);
			if(elclass.getIsApplication()==1 && elRegistration != null) {//是否为可申请
				elRegistration.setElclass(elclass);
				if(examRooms != null)
					elRegistration.setExamRoom(examRooms);
				if(elClasss != null)
					elRegistration.setElclasss(elClasss);
				if(!classDao.checkElclassRegistration(elclass.getId())){ 	 
					classDao.addClassRegistration(elRegistration);
				}else{ 
					classDao.alterClassRegistration(elRegistration);
				}
			} 
			if (null != elclass.getValids()) {//复核人员
				for (int i = 0; i < elclass.getValids().size(); i++) { 
					if (!classDao.checkElclassUsers("valids", elclass.getValids()
							.get(i).getId(), elclass.getId()))
						classDao.addElclassusers("valids", elclass.getValids()
								.get(i).getId(), elclass.getId()); 
				}
			}
			//elclass = classDao.getClassById(getSessionIntValue(ElConstants.SESSION_USERID), id);//获取?为什么要创建者userid呢
			elclassId = String.valueOf(id);
			//由于是新增 ，返回我的培训班页面(防止表单重复提交)
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD, elclass.getName(),
					ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
			return "elclass_alllist";
//			return "elclass_list";
		}else{
			elclass.setId(Integer.valueOf(elclassId));
			classDao.alterClass(elclass);
		}
		
// 		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		//cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
			//	ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),true,"CLASS_USE_TYPE");
	

		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		} 
		
		group1 = roleDao.listGroupsBytype(1);
		group2 = roleDao.listGroupsBytype(2);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_ADD, elclass.getName(),
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());//**//**//
		return "elclass_edit";
	}
	
	/**
	 * 复制培训班
	 * @return
	 * @throws ElException
	 */
	public String newelclass_copy() throws ElException {
		int id = classDao.copyClass(elclass.getId());
		if(id > 0){
			elclass.setId(id);
			return "elclass_copy";
		}else{
			setElmessage("复制试卷错误！");
			return "erro";
		}
	}
	/**
	 * 修改初始化
	 * @return
	 * @throws ElException
	 */
	public String newelclass_alterInit() throws ElException {
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
//		}
//		else {
//			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
//		}
		//elclass = classDao.getClassById(getSessionIntValue(ElConstants.SESSION_USERID), elclass.getId());//类型报空指针异常
		elclass = classDao.getClassById(elclass.getId());
//		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		
//		cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//				ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),true,"CLASS_USE_TYPE");
		
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		elclass.setElRegistration(classDao.getClassRegistration(elclass.getId())); 
		elRegistration = setRegistration(elclass.getElRegistration());
		elclass.setValids(classDao.getElclassUsers("valids", elclass.getId()));
		//group1 = roleDao.listGroupsBytype(1);
		//group2 = roleDao.listGroupsBytype(2);  
		return "elclass_alter";
	}
	public ELClassRegistration setRegistration (ELClassRegistration elRegistration){
		//工种
//		if(elRegistration.getJingzhong() != null && elRegistration.getJingzhong().length()>=1){  
//			jztj = elRegistration.getJingzhong().split(","); 
//			String jz ="";
//			for(int i =0 ;i<jztj.length;i++){
//				if(jz.equals("")){
//					jz = ""+jztj[i];
//				}else{	
//					if(jztj.length == i+1){
//						jz =jz+","+jztj[i]+"";
//					}else{						
//						jz =jz+","+jztj[i];
//					}
//				}
//			}
//			elRegistration.setJingzhong(jz);
//		}
		//工种
		if(jztj != null && jztj.length>=1){
			String jz = "";
			for(int i =0 ;i<jztj.length;i++){
				if(i == jztj.length-1){
					jz = jz+jztj[i].toString();
					break;
				}else{
					jz = dstj[i].toString()+"-"+jz;					
				}
			}
			elRegistration.setJingzhong(jz);
		}
		//地市
		if(dstj != null && dstj.length>=1){
			String ds = "";
			for(int i =0 ;i<dstj.length;i++){
				if(i == dstj.length-1){
					ds = ds+dstj[i].toString();
					break;
				}else{
					ds = dstj[i].toString()+"-"+ds;					
				}
			}
			elRegistration.setDishi(ds);
		}
		//职务
		if(zwtj != null && zwtj.length>=1){
			String zw = "";
			for(int i =0 ;i<zwtj.length;i++){
				if(i == zwtj.length-1){
					zw = zw+zwtj[i].toString();
					break;
				}else{
					zw = zwtj[i].toString()+"-"+zw;					
				}
			}
			elRegistration.setZhiwu(zw);
		}
		//职级
		if(zjtj != null && zjtj.length>=1){
			String zj = "";
			for(int i =0 ;i<zjtj.length;i++){
				if(i == zjtj.length-1){
					zj = zj+zjtj[i].toString();
					break;
				}else{
					zj = zjtj[i].toString()+"-"+zj;					
				}
			}
			elRegistration.setZhiji(zj);
		}
		//岗位
		if(gwtj != null && gwtj.length>=1){
			String gw = "";
			for(int i =0 ;i<gwtj.length;i++){
				if(i == gwtj.length-1){
					gw = gw+gwtj[i].toString();
					break;
				}else{
					gw = gwtj[i].toString()+"-"+gw;					
				}
			}
			elRegistration.setGangwei(gw);
		}
		//部门
		if(treeType != null && treeType.length>=1){
			String bm = "";
			for(int i =0 ;i<treeType.length;i++){
				if(i == treeType.length-1){
					bm = bm+treeType[i].toString();
					break;
				}else{
					bm = treeType[i].toString()+"-"+bm;					
				}
			}
			elRegistration.setTreeType(bm);
		}
		return elRegistration;
	}
	/**
	 * 培训班选修课程列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String newelclass_course_xx() throws ElException {
		if(StringUtils.isNotBlank(elclassId)){
		xxCourses = classDao.listClassCourses(Integer.valueOf(elclassId),CourseConstants.COURSE_STUDY_STATUS_XX);
		//xxCourses = classDao.listClassCourses2(Integer.valueOf(elclassId),CourseConstants.COURSE_STUDY_STATUS_XX);
			elclass=classDao.getClassById(Integer.valueOf(elclassId)); 
				for(int i = 0 ; i < xxCourses.size() ; i++){
					if(eroomDao.checkuserClassBindingCourse(Integer.valueOf(elclassId), xxCourses.get(i).getId())){
						examRoom = eroomDao.getClassBindingCourseByRoomId(Integer.valueOf(elclassId), xxCourses.get(i).getId());
						if(examRoom != null){
							examRoom = eroomDao.getExamRoomByid(examRoom.getId()); 
							if(examRoom != null){
								xxCourses.get(i).setEroom(examRoom);
								xxCourses.get(i).setExamName(examRoom.getTitle());//借用teacherName字段用于显示examRoom.title。
							}
						}
					}
				}
			}
		return "elclass_course_xx";
	}
	/**
	 * 培训班课程选择列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String newelclass_course_selectList() throws ElException {
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		
		//ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_OP_TYPE");
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();
		String name = course == null ? "" : course.getName();
		
		courses = shoppingDao.newlistAllSelectCourse(ctypeTree,depid, name, ctid,getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_HASOPENED,Integer.valueOf(elclassId),getSessionIntValue(ElConstants.SESSION_ROLE));
		count = shoppingDao.newlistAllSelectCourseSize(ctypeTree,depid, name, ctid,CourseConstants.COURSE_STATUS_HASOPENED,Integer.valueOf(elclassId),getSessionIntValue(ElConstants.SESSION_ROLE));
		return "elclass_course_selectList";
	}
	//一下为部门分配培训班action
	
	public String getdepartment_classlist() throws ElException{
 		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
 			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
 		}else{
 			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
 		} 
// 		if(sublibs != 0){
//			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		}else{
//			typeid=cltypeTree.getId();
//		}
// 		
//		elclasses = classDao.getClassesList3(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE),"0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
//		count = classDao.getClassesSize3(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE));
// 		elclasses = classDao.listClasses(0, typeid, name, getPageNow(), getPageSize());
//		count = classDao.listClassesSize(0, typeid, name);
 		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
 		elclasses = classDao.getClassList(cltype, elClass,sublibs,"0,1,2,3,4,5,6,7,8","0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype, elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		return "elclass_alllist";
		
	}
	public String newmycourselist() throws ElException {
		myCourses = shoppingDao.listMyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = shoppingDao
				.listMyCourseSize(getSessionIntValue(ElConstants.SESSION_USERID));
		//返回一个当前时间
		//Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"); 
		String now=sdf.format(new Date());
		System.out.println("***"+now);
		getRequest().setAttribute("now", now);
		return "mycourselist";
	}
	
	/**
	 * 学员在学班级列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String newmyelclass_list() throws ElException {
//		myClasses = studyClassDao.listMyStudyClass(getSessionIntValue(ElConstants.SESSION_USERID));
//		if (null != myClasses) {
//			for (int i = 0; i < myClasses.size(); i++) {
//				studyClassDao.setMyPassclass(
//						getSessionIntValue(ElConstants.SESSION_USERID),
//						myClasses.get(i).getElClass().getId());
//			}
//		}
		myClasses = shoppingDao.listMyStudyClass(getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(), getPageSize());
		count = shoppingDao.listMyStudyClassSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "myelclass_list";
	}
	
	public String shopping_partmentnewmyelclass_list() throws ElException {
 		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
 			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
 		}else{
 			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
 		} 
// 		if(sublibs != 0){
//			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		}else{
//			typeid=cltypeTree.getId();
//		}
// 		
//		elclasses = classDao.getClassesList3(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE),"0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
//		count = classDao.getClassesSize3(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE));
// 		elclasses = classDao.listClasses(0, typeid, name, getPageNow(), getPageSize());
//		count = classDao.listClassesSize(0, typeid, name);
 		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
 		elclasses = classDao.getClassList(cltype, elClass,sublibs,"0,1,2,3,4,5,6,7,8","0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype, elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		return "shopping_partmentnewmyelclass_list_success";
	}
	/**
	 * 显示现有的培训班分配的部门情况
	 * @return
	 * @throws ElException 
	 */
	public String shopping_classtodepartment_init() throws ElException{
		//1查询class_department得出该培训班现有的分配部门树
		depTree =shoppingDao.getExampracDepTree(elclass.getId(), -1, true);
		
		//2查出这门培训班 
		elClass = classDao.getClassById(elclass.getId());
		falgdep=true;
		if(elclass.getStatus()>=5){
			if(getSessionIntValue(ElConstants.SESSION_ROLE) != 1){
				falgdep = false;
			}	
		}
		//if（2==true）{
		//3查出当前用户是否是超级管理员
			//if(3=false){
		//4给标志位 falgdep==false;
		//}
		//}
		
		return "shopping_classtodepartment_init_success";
	}
	/**
	 * 分配选择的部门给该培训班
	 * @return
	 * @throws ElException 
	 */	
	public String  shopping_updclasstodepartment() throws ElException{
		
		String [] chkstr= this.getRequest().getParameterValues("chkNames");
		//在此先删除此培训班已分配的部门
		
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		
			
		List<Integer> oldList = new  ArrayList<Integer>();
		oldList =  shoppingDao.getdepartmenttoclass(elclass.getId());
		department = new Department();
		if(oldList.size()!=0){
			for (Integer id : oldList) {
				
				department.setId(id);
				//得到该部门节点下的用户
				elusers = shoppingDao.gettoEroomInfoselectUser(depTree,department,"study_class",0,0, null,this.getStarttime(),this.getEndtime(),9999999, 1); 
				//删除这些用户的分配关系
				for (ELUser euser : elusers) {
					classDao.assign2userDelete(euser.getId(),elclass.getId());
				}
				 
			}
			//删除该培训班以前的分配关系
			shoppingDao.delete_deptclass(elclass.getId());
		}
		
		
		//添加新分配关系
		if(chkstr==null){
			return "examprac_depInfo";
		}
		for (int i = 0; i < chkstr.length; i++) {
			//添加进培训班部门分配关系表
			shoppingDao.addExamprac_dep(elclass.getId(), Integer.parseInt(chkstr[i]));
			//查询出该部门节点下的用户
			department.setId(Integer.parseInt(chkstr[i]));
			elusers = shoppingDao.gettoEroomInfoselectUser(depTree,department,"study_class",0,0, null,this.getStarttime(),this.getEndtime(),9999999, 1); 
			//给查出的用户分配
			for (ELUser euser : elusers) {
				//分配培训班
				classDao.assign2userAdd3(euser.getId(),elclass.getId(),ClassConstants.CLASS_SQFS_FP);

				 //分配考场
				 examroom_classassignwcInit(elclass.getId(),euser.getId());
				
			}
		}
		
		return "examprac_depInfo";
		//3在class_department表中查出新的部门与培训班的关系 保存在newdepclassList集合中
		//4对两个集合进行比对，
		//4.1找到olddepclassList中有，但是newdepclassList 没有的分配关系，然后参照培训班取消分配人员的方式取消分配关系
		//4.2找到olddepclassList没有，但是newdepclassList 中有的分配关系，然后参照培训班分配人员的方式进行分配

	}
	/**
	 * 显示部门分配培训班信息
	 * @return
	 * @throws ElException 
	 */	
	public String  shopping_classtodepartmentlist() throws ElException{
		//1查询出该管理员具有的权限的部门树
		//2查询出该管理员具有权限的培训班部门分配关系
		departmentDao=new DepartmentDaoImpl();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
		}
		else {
			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
		}
		Department userTree = shoppingDao.getExampracDepTree(elclass.getId(), -1, true);
		treeAllId=userDao.getTreeAllId(userTree,true); 
		return "examprac_doDepInit";
	}
	
	
	/**
	 * 分配学员进入培训班对应的考场和试卷
	 * @param course
	 * @param userid
	 * @throws ElException
	 */
	public void examroom_classassignwcInit(int classid,int userid) throws ElException {
		
		List<ExamRoom>  examRooms = shoppingDao.getroomlistbyclassid(classid);//根据培训班id找出该课程的考场
		
		if(examRooms!=null){
		for (ExamRoom examRoom : examRooms) {
			 examPapers=eroomDao.getEroomepwithusizes(examRoom.getId());
			 if (examPapers == null || examPapers.size() == 0) {
					//如果该考场没有试卷则什么都不做
				}else{//否则 判断试卷有没有被添加进去
					if (!eroomDao.checkuser2eroom(examRoom.getId(), userid, examRoom.getClassid())) {
						eroomDao.adduser2eroom(examRoom.getId(), userid, 1, examRoom.getClassid(),
								CourseConstants.EXAMROOM_FPFS_SQ);
					}
					for (ExamPaper examPaper : examPapers) {
						if (!studyQuizDao.checkStudyExamPaper(userid, examPaper.getId(), examRoom.getId(), examRoom
								.getClassid())) {
							// 添加该学员到 学员试卷表中
							studyQuizDao.addStudyExamPaper(userid, examPaper.getId(), examRoom.getId(),
									examRoom.getClassid());
						}
					}
					}
					ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_ADD, examRoom.getTitle() + "（添加学员）",
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
			 
			}
		}
		
		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());
		
		
	}
	
	/**
	 * 培训班分配部门审核列表
	 * @return
	 * @throws ElException
	 */
	public String shopping_elclass_sh_list() throws ElException {
//		int typeid = 1;
//		if(sublibs != 0){
//			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		}
//		if(typeid == 0){
//			typeid = 1;
//		}
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		
//		int typeid = cltype == null ? cltypeTree.getId(): cltype.getId();
		//cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				//ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),true,"CLASS_USE_TYPE");
		
//		elclasses = classDao.getClassesList(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE),"3", getPageNow(), getPageSize());
//		count = classDao.getClassesSize(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE));
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
		elclasses = classDao.getClassList(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8","3", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		return "shopping_elclass_sh_list_success";
	}

	/**
	 * 培训班课程添加 10 月6日
	 * 
	 * @return
	 * @throws ElException
	 */
	public String newelclass_course_add() throws ElException {
		//获取培训班信息
		if(elclassId!=null){
			elclass=classDao.getClassById(Integer.parseInt(elclassId));
		}
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			//classDao.addClassCourse(Integer.valueOf(elclassId), Integer.valueOf(idArray[i]), status);
			classDao.addClassCourse2(Integer.valueOf(elclassId), Integer.valueOf(idArray[i]), status,elclass.getStarttime(),elclass.getFinishtime());
		}
		if(elclass!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,elclass.getName()+"(添加课程)",
					ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		}
		return "elclass_course_selectList";
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getElclassId() {
		return elclassId;
	}

	public void setElclassId(String elclassId) {
		this.elclassId = elclassId;
	}


}
