package com.sopia.peixunBatch.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.batchman.dao.BatchDao;
import com.sopia.classman.action.ClassAction;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;
import com.sopia.duman.entities.UnitRanking;
import com.sopia.peixunBatch.dao.PeixunBatchDao;
import com.sopia.peixunBatch.entities.PeixunBatch;
import com.sopia.studyman.dao.StudyQuizDao;

public class PeixunBatchAction extends BaseAction{
	
	/*** 培训批次列表*/
	private List<PeixunBatch> batchList;
	private PeixunBatchDao peixunBatchDao;
	private PeixunBatch peixunBatch;
	private List<PeixunBatch> batchElclassList;
	private List<ELUser> elusers;
	private String elclassId;
	private Station station;
	private Station stTree;
	private Department depTree;
	private Department deprTree;
	private Integer deptid;
	private int sublibs;//是否包含下级节点
	private String peixunBatchId;
	
	/*** 培训班id串*/
	private String ids;
	private String batchId;
	
	
	private List<ELUser> canAssignUsers;
	private List<ELUser> xassignedUsers;
	private List<ELUser> bassignedUsers;
	private List<ELUser> zassignedUsers;
	private int status;
	
	private int sub_department;
	
	
//----------------------------------
	private static final Log logger = LogFactory.getLog(ClassAction.class);
	private ElClTypeDao elClTypeDao;
	private ElClType cltype;
	private ElClType cltypeTree;
	
	private CourseTypeDao ctypeDao;
	private CourseType ctypeTree;
	private CourseType ctype;
	private CourseDao courseDao;
	private	EroomDao eroomDao;
	private List<Course> courses;

	
	private Department department;
	private ClassDao classDao;  
	// private CourseDao courseDao;
	private ElClass elclass;
	private List<ElClass> elclasses;
	private List<ELUser> users;
	int isAlter;
	private UnitRanking unitRank;//单位排名
	private List<UnitRanking> unitRanks;//单位排名
	private StudyQuizDao studyQuizDao;
	private boolean Ration;
	private List<Department> departments;
	private List<Department> departments1;
	//基础数据
	private List<BaseDataType> dataTypeList;
	private List<BaseDatat> baseDatatList;
	
	/*** 培训批次DAO*/
	private BatchDao batchDao;
	
	private String starttime;
	private String endtime;
	private ELUser elUser;
	

	public String getElclassId() {
		return elclassId;
	}

	public void setElclassId(String elclassId) {
		this.elclassId = elclassId;
	}

	public String add_batch() throws ElException{
		baseDatatList = peixunBatchDao.getBaseDatatByTypeid2();
		return "add_batch";
	}
	
	public String save_batch() throws ElException{
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		System.out.println("peixun="+peixunBatch);
		peixunBatchDao.save_batch(peixunBatch, userid);
		return my_add_batch();
	}

	public String my_add_batch() throws ElException{
		String name = peixunBatch == null ? "" : peixunBatch.getName();
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		batchList = peixunBatchDao.getBatchList( name, getPageNow(), getPageSize());
		
		count = peixunBatchDao.getBatchListSize(userid);
		
		return "my_add_batch";
	}
	
	public String batch_details() throws ElException{
//		peixunBatch = peixunBatchDao.getList(peixunBatch.getId());
		
		peixunBatch = peixunBatchDao.getPeixunBatchById(peixunBatch.getId());
		elclasses = peixunBatchDao.getElclassList(peixunBatch.getId());
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "use", -1,
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
		//int depid = department == null ? 1 : department.getId();
		int depid = department == null ? depTree.getId() : department.getId();
		if(department==null){
			department=new Department(1); 
		} 
		department.setId(depid);
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		int cid = (elclass == null) ? 0 : elclass.getId(); 
		
//		elusers = classDao.listAssignedUserIsAssign(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
//		count =classDao.listAssignedUserIsAssignSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		if(elUser == null){
			elUser = new ELUser();
			elUser.setIsAssign("0");
		}
		
		if(peixunBatch!=null){
			elusers = peixunBatchDao.listAssignedUser(getPageNow(), getPageSize(),peixunBatch.getId());
			count =peixunBatchDao.listAssignedUserSize(getPageNow(), getPageSize(),peixunBatch.getId());
			}
		return "batch_details";
	}
	
	public String batch_alter() throws ElException{
		
//		int id = peixunBatch==null?Integer.valueOf(peixunBatchId):peixunBatch.getId();
			
//		peixunBatch = peixunBatchDao.getList(peixunBatch.getId());
		peixunBatch = peixunBatchDao.getPeixunBatchById(peixunBatch.getId());
		elclasses = peixunBatchDao.getElclassList(peixunBatch.getId());
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "use", -1,
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
		//int depid = department == null ? 1 : department.getId();
		int depid = department == null ? depTree.getId() : department.getId();
		if(department==null){
			department=new Department(1); 
		} 
		department.setId(depid);
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		int cid = (elclass == null) ? 0 : elclass.getId(); 
		
//		elusers = classDao.listAssignedUserIsAssign(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
//		count =classDao.listAssignedUserIsAssignSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		if(elUser == null){
			elUser = new ELUser();
			elUser.setIsAssign("0");
		}
		if(peixunBatch!=null){
		elusers = peixunBatchDao.listAssignedUser(getPageNow(), getPageSize(),peixunBatch.getId());
		count =peixunBatchDao.listAssignedUserSize(getPageNow(), getPageSize(),peixunBatch.getId());
		}
		return "batch_alter";
	}
	
	
	public String batch_elclass_details() throws ElException{
		
		String  id = peixunBatch.getElclassId();
		
		batchElclassList = peixunBatchDao.getBatchElClssList(Integer.valueOf(id));
		
		return "batch_elclass_details";
	}
	
	public String batch_eluser_details() throws ElException{
		int id = peixunBatch.getId();
		elusers = peixunBatchDao.getBatchElUserList(id);
		return "batch_eluser_details";
	}
	
	public String delete_batch() throws ElException{
		int id = peixunBatch.getId();
		peixunBatchDao.delete_batch(Integer.valueOf(id));
		return my_add_batch();
	}
	
	

	
	public String batch_assigntoUsersInit() throws ElException {
		if(peixunBatchId==null){
			peixunBatchId = peixunBatch.getId()+"";
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
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		
		int cid = (peixunBatch == null) ? 0 : peixunBatch.getId();
//		
//		elusers = courseDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,deprTree,station,getSessionIntValue(ElConstants.SESSION_ROLE));
//		count =courseDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,deprTree,station,getSessionIntValue(ElConstants.SESSION_ROLE));
		elusers = peixunBatchDao.listUsers(department, station, sub_department, elUser,
				getPageNow(), getPageSize(),Integer.valueOf(peixunBatchId));
		count = peixunBatchDao.listUsersSize(department, station,sub_department, elUser,Integer.valueOf(peixunBatchId));
//		jingzhongs=userDao.getBaseDatatByTypeid(1);
//		zhiwus=userDao.getBaseDatatByTypeid(2);
//		zhijis=userDao.getBaseDatatByTypeid(3);
//		gangweis=userDao.getBaseDatatByTypeid(4);
//		dishis=userDao.getBaseDatatByTypeid(5);
		return "batch_assigntoUsersInit";
	}
	
	
	public String batch_eluser_add() throws ElException{
		if(peixunBatchId != null){
			String[] idArray = ids.split(",");
			for (int i = 0; i < idArray.length; i++) {
				peixunBatchDao.addBatchEluser(Integer.valueOf(peixunBatchId),Integer.valueOf(idArray[i]));
			}
		}
		
		return "batch_assigntoUsersInit";
	}
	
	
	public String batch_elclass_List() throws ElException{
		System.out.println(peixunBatchId);
		if(peixunBatchId==null){
			peixunBatchId = peixunBatch.getId()+"";
		}
//		cltype=new ElClType();
//		cltype.setId();
//		if(elclass == null)
//			elclass=new ElClass();
//		elclass.setCltype(cltype);
//		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);//获取培训班级树
//		}else{
//			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "OP",ElConstants.TREE_FIANL, true);
//		}
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
 			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
 		}else{
 			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
 		} 
		
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
 		String sqlw="0,1,2,3,4,5,6,7,8";
 		if(elclass!=null&&elclass.getSqlw()==9){
 			sqlw="9";
 		}
		sublibs = elclass == null ? 1 : sublibs;
//		elclasses=peixunBatchDao.listcombinationSearchClass(elclass, cltypeTree,"-1", getPageNow(), getPageSize(),Integer.valueOf(peixunBatchId));//获取所有培训班信息
//		count=peixunBatchDao.listcombinationSearchClassCount(elclass, cltypeTree, getPageNow(), getPageSize(),Integer.valueOf(peixunBatchId));//获取培训班的记录数
 		elclasses = peixunBatchDao.getClassList(cltype, elclass,sublibs,sqlw,"0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize(),Integer.valueOf(peixunBatchId));
		count = peixunBatchDao.getClassListSize(cltype, elclass,sublibs,sqlw,Integer.valueOf(peixunBatchId));
		return "batch_elclass_List";
	}
	
	
	public String batch_elclass_add() throws ElException{
		if(peixunBatchId != null){
			String[] idArray = ids.split(",");
			for (int i = 0; i < idArray.length; i++) {
				peixunBatchDao.addBatchClass(Integer.valueOf(peixunBatchId),Integer.valueOf(idArray[i]));
			}
		}
		
		return "batch_elclass_List";
	}
	
	
	
	public String batch_elusers() throws ElException {
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
					getSessionIntValue(ElConstants.SESSION_USERID), "use", -1,
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
		//int depid = department == null ? 1 : department.getId();
		int depid = department == null ? depTree.getId() : department.getId();
		if(department==null){
			department=new Department(1); 
		} 
		department.setId(depid);
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		int cid = (elclass == null) ? 0 : elclass.getId(); 
		
//		elusers = classDao.listAssignedUserIsAssign(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
//		count =classDao.listAssignedUserIsAssignSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		if(elUser == null){
			elUser = new ELUser();
			elUser.setIsAssign("0");
		}
		
		elusers = classDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,depTree,stTree);
		count =classDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,depTree,stTree);
//		jingzhongs=userDao.getBaseDatatByTypeid(1);
//		zhiwus=userDao.getBaseDatatByTypeid(2);
//		zhijis=userDao.getBaseDatatByTypeid(3);
//		gangweis=userDao.getBaseDatatByTypeid(4);
//		dishis=userDao.getBaseDatatByTypeid(5);
		return "batch_elusers";
	}
	
	
	public String delete_elclass() throws ElException{
		peixunBatch.getElclass().getId();
		peixunBatchDao.delete_elclass(peixunBatch.getElclass().getId());
		return "batch_alter";
	}
	
	
	
	public String batch_addNotes() throws ElException {
//		if (examRoom != null && examRoom.getId() != 0) {
//			examRoom = eroomDao.getExamRoomByid(examRoom.getId());
//		}
//		if (course != null && course.getId() != 0) {
//			course = courseDao.getCourseById(course.getId());
//		}
//		if (elclass != null && elclass.getId() != 0) {
//			elclass = classDao.getClassById(elclass.getId());
//		}
		return "batch_addNotes";
	}
	
	
	/**
	 * 培训批次
	 * @return
	 */
	public String my_batchs() throws ElException{
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		batchList = peixunBatchDao.getMyBatchList(userid, getPageNow(), getPageSize());
		count = peixunBatchDao.getMyBatchDetailSize(userid);
		//返回一个当前时间
		//Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String now=sdf.format(new Date());
		getRequest().setAttribute("now", now);
		return "my_batchs";
	}
	
	
//培训班上下移动
	public String elclass_upsort() throws ElException {
		peixunBatchDao.sortCps(elclass.getId(), elclass.getSortid(),
				ElConstants.SORT_UP,peixunBatch.getId());
		//coursePages = coursePageDao.listCps(course.getId());
		//elclasses = peixunBatchDao.getElclassList(peixunBatch.getId());
		return "batch_alter";
	}

	public String elclass_downsort() throws ElException {
		peixunBatchDao.sortCps(elclass.getId(), elclass.getSortid(),
				ElConstants.SORT_DOWN,peixunBatch.getId());
		//elclasses = peixunBatchDao.getElclassList(peixunBatch.getId());
	//coursePages = coursePageDao.listCps(course.getId());
		return "batch_alter";
	}
	
	
//----------------set and  get-----------------------------------------
	public PeixunBatchDao getPeixunBatchDao() {
		return peixunBatchDao;
	}

	public void setPeixunBatchDao(PeixunBatchDao peixunBatchDao) {
		this.peixunBatchDao = peixunBatchDao;
	}

	public PeixunBatch getPeixunBatch() {
		return peixunBatch;
	}

	public void setPeixunBatch(PeixunBatch peixunBatch) {
		this.peixunBatch = peixunBatch;
	}

	public List<PeixunBatch> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<PeixunBatch> batchList) {
		this.batchList = batchList;
	}

	public List<PeixunBatch> getBatchElclassList() {
		return batchElclassList;
	}

	public void setBatchElclassList(List<PeixunBatch> batchElclassList) {
		this.batchElclassList = batchElclassList;
	}

	public List<ELUser> getElusers() {
		return elusers;
	}

	public void setElusers(List<ELUser> elusers) {
		this.elusers = elusers;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
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

	public List<ELUser> getCanAssignUsers() {
		return canAssignUsers;
	}

	public void setCanAssignUsers(List<ELUser> canAssignUsers) {
		this.canAssignUsers = canAssignUsers;
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

	public List<ELUser> getZassignedUsers() {
		return zassignedUsers;
	}

	public void setZassignedUsers(List<ELUser> zassignedUsers) {
		this.zassignedUsers = zassignedUsers;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public ElClass getElclass() {
		return elclass;
	}

	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}

	public List<ELUser> getUsers() {
		return users;
	}

	public void setUsers(List<ELUser> users) {
		this.users = users;
	}

	public int getIsAlter() {
		return isAlter;
	}

	public void setIsAlter(int isAlter) {
		this.isAlter = isAlter;
	}

	public UnitRanking getUnitRank() {
		return unitRank;
	}

	public void setUnitRank(UnitRanking unitRank) {
		this.unitRank = unitRank;
	}

	public List<UnitRanking> getUnitRanks() {
		return unitRanks;
	}

	public void setUnitRanks(List<UnitRanking> unitRanks) {
		this.unitRanks = unitRanks;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public boolean isRation() {
		return Ration;
	}

	public void setRation(boolean ration) {
		Ration = ration;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<Department> getDepartments1() {
		return departments1;
	}

	public void setDepartments1(List<Department> departments1) {
		this.departments1 = departments1;
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

	public static Log getLogger() {
		return logger;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public List<ElClass> getElclasses() {
		return elclasses;
	}

	public void setElclasses(List<ElClass> elclasses) {
		this.elclasses = elclasses;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public BatchDao getBatchDao() {
		return batchDao;
	}

	public void setBatchDao(BatchDao batchDao) {
		this.batchDao = batchDao;
	}

	public String getPeixunBatchId() {
		return peixunBatchId;
	}

	public void setPeixunBatchId(String peixunBatchId) {
		this.peixunBatchId = peixunBatchId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<BaseDataType> getDataTypeList() {
		return dataTypeList;
	}

	public void setDataTypeList(List<BaseDataType> dataTypeList) {
		this.dataTypeList = dataTypeList;
	}

	public List<BaseDatat> getBaseDatatList() {
		return baseDatatList;
	}

	public void setBaseDatatList(List<BaseDatat> baseDatatList) {
		this.baseDatatList = baseDatatList;
	}

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}
	
	

}
