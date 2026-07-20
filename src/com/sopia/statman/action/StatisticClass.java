package com.sopia.statman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.batchman.dao.BatchDao;
import com.sopia.batchman.entities.Batch;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.impl.CourseDaoImpl;
import com.sopia.courseman.entities.Course;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;
import com.sopia.statman.dao.StatisticClassDao;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.dao.StudyClassDao;

/**
 * Description: 培训班统计部分,对应struts: com/sopia/common/configs/stat_class.xml
 * 
 * Copyright (c) Department of Research and Development/wenyishun110@163.com.
 * All Rights Reserved.
 * 
 * @version 1.0 2011-9-4 上午12:08:30 by 闻益舜（wenyishun110@163.com）创建
 */
public class StatisticClass extends BaseAction {
	private Department department;
	private ELUser elUser;
	private List<Department> departments;

	private List<ElClass> classes;
	private ElClass elclass;
	private StatisticClassDao statisticClassDao;
	private StudyClassDao studyClassDao;
	private MyClass myClass;
	private List<ELUser> elUsers;
	private Department depTree;
	private List<MyClass> myClasses;
	/** * 培训班id */
	private String elClassId;
	/** * 培训班名称 */
	private String elClassName;
	private ElClTypeDao elClTypeDao;
	private ElClType cltype;
	private ElClType cltypeTree;
	private ClassDao classDao;
	private boolean export;
	private List<Batch> batchs;
	private Batch batch ;
	private List<ELUser> elusers ;
	private List<BaseDatat> jingzhongs;
	private String Return;
	private BatchDao batchDao;
	private Station stTree;
	private Station station;
	

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

	public BatchDao getBatchDao() {
		return batchDao;
	}

	public void setBatchDao(BatchDao batchDao) {
		this.batchDao = batchDao;
	}

	public List<Batch> getBatchs() {
		return batchs;
	}

	public void setBatchs(List<Batch> batchs) {
		this.batchs = batchs;
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

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<ElClass> getClasses() {
		return classes;
	}

	public void setClasses(List<ElClass> classes) {
		this.classes = classes;
	}

	public ElClass getElclass() {
		return elclass;
	}

	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}

	public StatisticClassDao getStatisticClassDao() {
		return statisticClassDao;
	}

	public void setStatisticClassDao(StatisticClassDao statisticClassDao) {
		this.statisticClassDao = statisticClassDao;
	}

	public StudyClassDao getStudyClassDao() {
		return studyClassDao;
	}

	public void setStudyClassDao(StudyClassDao studyClassDao) {
		this.studyClassDao = studyClassDao;
	}

	public MyClass getMyClass() {
		return myClass;
	}

	public void setMyClass(MyClass myClass) {
		this.myClass = myClass;
	}

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public List<MyClass> getMyClasses() {
		return myClasses;
	}

	public void setMyClasses(List<MyClass> myClasses) {
		this.myClasses = myClasses;
	}

	/**
	 * 选班查看
	 * 
	 * @return
	 * @throws ElException
	 */
	public String dep_class_list() throws ElException {
		classes = statisticClassDao.listClassByDepid(department.getId());
		return "dep_class_list";
	}

	/**培训班统计,列表查看
	 * @return
	 * @throws ElException
	 */
	public String dep_class_view() throws ElException {
//		classes = statisticClassDao.listElclassStateByName(elClassName==null?"":elClassName.trim());
		// getPageSize()= getPageSize()==0?10:getPageSize();
		// elUsers = statisticClassDao.listClassView(department.getId(), elclass
		// .getId(), getPageNow(), getPageSize());
		//
		// count = statisticClassDao.listClassViewSize(department.getId(),
		// elclass
		// .getId());
		int typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
		String name = elclass == null ? "" : elclass.getName();


//		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
//		cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),true,"CLASS_USE_TYPE");

		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
			if(isExport()){
				classes = statisticClassDao.listElclassStateByName(name,cltypeTree,typeid);
				return "dep_class_view_EXCEL";
			}
			//classes = statisticClassDao.listElclassStateByName(name,cltypeTree,typeid,getPageNow(),getPageSize());
			//count = statisticClassDao.listElclassStateByNamesize(name,cltypeTree,typeid);

		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
			int[] typeids=null;
			if(getRequest().getParameter("str")==null||typeid==1){
				typeids=new int[cltypeTree.getChild().size()];
				for(int i=0;i<cltypeTree.getChild().size();i++){
					typeids[i]=cltypeTree.getChild().get(i).getId();
				}			
			}else{
				typeids=new int[1];
				typeids[0]=typeid;
			}
			if(isExport()){
				classes = statisticClassDao.listElclassStateByName(name,cltypeTree,typeids);
				return "dep_class_view_EXCEL";
			}
			//classes = statisticClassDao.listElclassStateByName(name,cltypeTree,typeids,getPageNow(),getPageSize());
			//count = statisticClassDao.listElclassStateByNamesize(name,cltypeTree,typeids);
		}
		if(cltype==null||cltype.getId()<=0){
			cltype=cltypeTree;
		}else{
			cltype=elClTypeDao.getClTypeById(cltype.getId());
		}
		classes = statisticClassDao.listElclassStateByName(name,cltype,getPageNow(),getPageSize());
		count = statisticClassDao.listElclassStateByNamesize(name,cltype);
//		int typeid = cltype == null ? cltypeTree.getId(): cltype.getId();
		return "dep_class_view";
	}
	
	public String dep_class_view3() throws ElException {
//		classes = statisticClassDao.listElclassStateByName(elClassName==null?"":elClassName.trim());
		// getPageSize()= getPageSize()==0?10:getPageSize();
		// elUsers = statisticClassDao.listClassView(department.getId(), elclass
		// .getId(), getPageNow(), getPageSize());
		//
		// count = statisticClassDao.listClassViewSize(department.getId(),
		// elclass
		// .getId());
		int typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
		String name = elclass == null ? "" : elclass.getName();


//		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
//		cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//		ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),true,"CLASS_USE_TYPE");

		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
			if(isExport()){
				classes = statisticClassDao.listElclassStateByName(name,cltypeTree,typeid);
				return "dep_class_view_EXCEL";  
			}
			classes = statisticClassDao.listElclassStateByName(name,cltypeTree,typeid,getPageNow(),getPageSize());
			count = statisticClassDao.listElclassStateByNamesize(name,cltypeTree,typeid);

		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "use",ElConstants.TREE_FIANL, true); 
			typeid = cltypeTree.getChild().size() == 0 ? 0 : typeid;
			int[] typeids=null;
			if(getRequest().getParameter("str")==null||typeid==1){
				typeids=new int[cltypeTree.getChild().size()];
				for(int i=0;i<cltypeTree.getChild().size();i++){
					typeids[i]=cltypeTree.getChild().get(i).getId();
				}			
			}else{
				typeids=new int[1];
				typeids[0]=typeid;
			}
			if(isExport()){
				classes = statisticClassDao.listElclassStateByName(name,cltypeTree,typeids);
				return "dep_class_view_EXCEL";
			}
			classes = statisticClassDao.listElclassStateByName(name,cltypeTree,typeids,getPageNow(),getPageSize());
			count = statisticClassDao.listElclassStateByNamesize(name,cltypeTree,typeids);
		}
//		int typeid = cltype == null ? cltypeTree.getId(): cltype.getId();


		return "dep_class_view3";
	} 

	/**岗位培训班统计,列表查看
	 * @return
	 * @throws ElException
	 */
	public String sta_class_view() throws ElException {
		//int typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
		//String name = elclass == null ? "" : elclass.getName();
		
		int staid = station == null? stationDao.getStationRoot().getId(): station.getId();
		String name = elclass == null ? "" : elclass.getName();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(station==null||station.getId()<=0){
			station=stTree;
		}else{
			station=stationDao.getStTreeById(staid);
		}
		
		classes = statisticClassDao.listElclassStateByName22(name,station,getPageNow(),getPageSize());
		count = statisticClassDao.listElclassStateByNamesize22(name,station);
		
		return "sta_class_view";
	}
	
	public String dep_classstudy_view() throws ElException {
		int id = elclass.getId();
		int userid = elUser.getId();
//		myClass = new MyClass();
//		myClass.setMyCourseB(studyClassDao.listMyClassCourse(id, userid,
//				CourseConstants.COURSE_STUDY_STATUS_BX));
//		myClass.setMyCourseX(studyClassDao.listMyClassCourse(id, userid,
//				CourseConstants.COURSE_STUDY_STATUS_XX));
		


		myClass = new MyClass();
		myClass.setMyCourseB(studyClassDao.listMyClassCourseStat(id, userid,  
				CourseConstants.COURSE_STUDY_STATUS_BX)); 
		myClass.setMyCourseX(studyClassDao.listMyClassCourseStat(id, userid,
				CourseConstants.COURSE_STUDY_STATUS_XX)); 
		if(Return!=null)
			Return = Return.replaceAll("xyzzyx", "&");
//		myClass.setElClass(classDao.getElClassById(elclass.getId()));
		CourseDao courseDao = new CourseDaoImpl();
		for(int i=0;i<myClass.getMyCourseB().size();i++){
			int courseid = myClass.getMyCourseB().get(i).getCourse().getId();
			Course c = courseDao.getCourseById(courseid);
			if(c.getExurl().contains("Course-")){
				int finish = courseDao.getUserSCInfo(userid+"",c.getExurl(),"completed");
				int all = courseDao.getSCItemInfo(c.getExurl())-1;
				myClass.getMyCourseB().get(i).setProcess((float)finish/(float)all*100);
			}
		}
		return "dep_classstudy_view";
	}

	public String class_searchlist() throws ElException {
		// classes = statisticClassDao.listAllClass();
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "class_searchlist";
	}

	/**
	 * 全局培训班通过率统计
	 */
	public String gclass_stat_list() throws ElException {
		classes = statisticClassDao.listClassByGlobal(1);
		return "gclass_stat_list";
	}

	public String gclass_dep_list() throws ElException {
		// classes = statisticClassDao.listClassByGlobal(1);
		departments = statisticClassDao.listDepPassPer(elclass.getId());
		return "gclass_dep_list";
	}

	public String user_class() throws ElException {
		myClasses = studyClassDao.listMyStudyClass(elUser.getId());

		return "user_class";
	}

	public String user_class_create() throws ElException {

		classes = statisticClassDao.listClassByCreater(elUser.getId());

		return "user_class_create";
	}
	//查看课程情况
	public String situation_course_view()throws ElException{
		return "";
	}
	/**培训班批次统计，批次列表
	 * @return
	 * @throws ElException
	 */
	public String stat_class_batch_list()throws ElException{
		int userid = getSessionIntValue(ElConstants.SESSION_ROLE)==1?-1:getSessionIntValue(ElConstants.SESSION_USERID);
		batchs = statisticClassDao.listBatchs(userid, getPageNow(), getPageSize());
		count =statisticClassDao.listBatchssize(userid);
		return "stat_class_batch_list";
	}
	/**批次的详情查看
	 * @return
	 * @throws ElException
	 */
	public String stat_class_batch_view()throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		}else{
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		batch = batchDao.getBatchById(batch.getId());  
		batch.setClasses(batchDao.getBatchElclass(batch.getId()));
		if(department==null||department.getId()<=0){
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		String clids = statisticClassDao.batchclassids(batch.getId());
		if(isExport()){ 
			elusers = statisticClassDao.classStudent(clids,department ,elUser);
			return "stat_class_batch_view_EXCEL";
		}
		elusers = statisticClassDao.classStudent(clids,department,elUser,getPageNow(), getPageSize());
		count =statisticClassDao.classStudentSize(clids,department,elUser);
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		return "stat_class_batch_view";
	}
	public String getElClassId() {
		return elClassId;
	}

	public void setElClassId(String elClassId) {
		this.elClassId = elClassId;
	}

	public String getElClassName() {
		return elClassName;
	}

	public void setElClassName(String elClassName) {
		this.elClassName = elClassName;
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

	public ElClType getCltypeTree() {
		return cltypeTree;
	}

	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public List<ELUser> getElusers() {
		return elusers;
	}

	public void setElusers(List<ELUser> elusers) {
		this.elusers = elusers;
	}

	public List<BaseDatat> getJingzhongs() {
		return jingzhongs;
	}

	public void setJingzhongs(List<BaseDatat> jingzhongs) {
		this.jingzhongs = jingzhongs;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

}
