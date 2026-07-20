package com.sopia.classman.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.classman.entities.ElclassAuditDescribes;
import com.sopia.common.ElException;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.UserExcelUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.ClassPara;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ErPara;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.ExamRoomAuditDescribes;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.dao.StationDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElGroup;
import com.sopia.duman.entities.Station;
import com.sopia.duman.entities.UnitRanking;
import com.sopia.newsandmess.dao.impl.MessageDaoImpl;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.dao.impl.StudyQuizDaoImpl;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.workcourseset.dao.WorkCourseDao;
import com.sopia.workcourseset.entity.WorkCourse;

/**
 * @author luocw
 * 
 */


@SuppressWarnings("unchecked")
public class ClassAction extends BaseAction {
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

	private ClassDao classDao;  
	// private CourseDao courseDao;
	private ElClass elclass;
	private List<ExamRoom> examRooms;
	private ExamRoom examRoom;
	private ElclassAuditDescribes ecAudit;
	private List<ElGroup> group1;
	private List<ElGroup> group2;
	private RoleDao roleDao;
	private ElClass elClass;
	private List<ElClass> elclasses;
	private List<ElClass> elClasss;
	private int classSource;
	private List<MyClass> myClasses;
	private MyClass myClasse;
	private List<Course> bxCourses;
	private List<Course> xxCourses;
	private List<Course> zxCourses;
	private List<Course> myCourses;
	private int status;
	private int astatus;
	private Course course;
	private List<ELUser> canAssignUsers;
	private List<ELUser> assignedUsers;
	private List<Department> canAssignDeps;
	private List<Department> assignedDeps;
	private String courseId;
	private String elclassId;
	private String suggestcredit;	//建议学分
	private String setcredit;		//设置学分
	private String orderid;			//学习序号
	private String firstLearn;		//先学后考
	private String getcredit;		//学分获得方式
	private String batchId;			//培训批次ID	
	private String ids;
	private boolean export;
	private Department dep;
	private String startTime_2;
	private String endTime_2; 
	private String Return;
	private ExamRoom eroombd; //结业考试课程绑定考场
	private List<MyRoom> myrooms;
	private ExamRoomAuditDescribes erAuditdes;
	private int PageStatus;
	private int PageStatusint;
	private ELClassRegistration elRegistration;
	private String[] jztj;//警种条件 
	private String[] dstj;//地市条件
	private String[] zwtj;//职务条件
	private String[] zjtj;//职级条件
	private String[] gwtj;//岗位条件
	private String[] treeType;//部门
	private String bmtj;//部门
	private List<BaseDatat> baseDatatList; //基础数据库集合 
	private BaseDatat baseDatat; //基础数据库
	private Department department;
	private int sub_department;
	private int sublibs;
	
	private List<BaseDatat> allJingzhongOplist;
	private List<BaseDatat> myJingzhongOplist;
	private String searbm ="";
	private String searbase ="";
	private List treeAllId; 
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	private int DBMethods; 
	private ExamRoom eroom;  
	private List<ExamPaper> examPapers;
	private int isEroomName;  
	private int isclassName; 
	private List<ErPara> erParas;
	private List<ErPara> erepParas;
	private int ajax;
	private int state;
	private File st;
	private String stFileName;
	private int staid;
	private StationDao stationDao;
	private int classid;
	private Station station;
	private List<ELUser> users;
	int isAlter;
	private UnitRanking unitRank;//单位排名
	private List<UnitRanking> unitRanks;//单位排名
	private StudyQuizDao studyQuizDao;
	private boolean Ration;
	private List<Department> departments;
	private List<Department> departments1;
	private Station stTree;
	
	//sd1227
	private WorkCourseDao workCourseDao;
	private List<WorkCourse> workCourseUser;//启用的职业人群
	private String optype;
	
	
	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public List<WorkCourse> getWorkCourseUser() {
		return workCourseUser;
	}

	public void setWorkCourseUser(List<WorkCourse> workCourseUser) {
		this.workCourseUser = workCourseUser;
	}

	public WorkCourseDao getWorkCourseDao() {
		return workCourseDao;
	}

	public void setWorkCourseDao(WorkCourseDao workCourseDao) {
		this.workCourseDao = workCourseDao;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Station getStTree() {
		return stTree;
	}

	public void setStTree(Station stTree) {
		this.stTree = stTree;
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

	public boolean isRation() {
		return Ration;
	}

	public void setRation(boolean ration) {
		Ration = ration;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
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

	public int getIsAlter() {
		return isAlter;
	}

	public void setIsAlter(int isAlter) {
		this.isAlter = isAlter;
	}

	public List<ELUser> getUsers() {
		return users;
	}

	public void setUsers(List<ELUser> users) {
		this.users = users;
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

	public int getPageStatus() { 
		return PageStatus;
	} 

	public void setPageStatus(int pageStatus) {
		PageStatus = pageStatus;
	}

	public int getPageStatusint() {
		return PageStatusint;
	}

	public void setPageStatusint(int pageStatusint) {
		PageStatusint = pageStatusint;
	}

	public String[] getJztj() {
		return jztj;
	}

	public void setJztj(String[] jztj) {
		this.jztj = jztj;
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
	public void setTreeType(String[] treeType) {
		this.treeType = treeType;
	}

	public List getJztjs() {
		return jztjs;
	}

	public void setJztjs(List jztjs) {
		this.jztjs = jztjs;
	}

	public List<BaseDatat> getBaseDatatList() {
		return baseDatatList;
	}

	public void setBaseDatatList(List<BaseDatat> baseDatatList) {
		this.baseDatatList = baseDatatList;
	}

	public BaseDatat getBaseDatat() {
		return baseDatat;
	}

	public void setBaseDatat(BaseDatat baseDatat) {
		this.baseDatat = baseDatat;
	}

	public String getSearbm() {
		return searbm;
	}

	public void setSearbm(String searbm) {
		this.searbm = searbm;
	}

	public String getSearbase() {
		return searbase;
	}

	public void setSearbase(String searbase) {
		this.searbase = searbase;
	}

	public List<ElClass> getElClasss() {
		return elClasss;
	}

	public void setElClasss(List<ElClass> elClasss) {
		this.elClasss = elClasss;
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

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public StationDao getStationDao() {
		return stationDao;
	}

	public void setStationDao(StationDao stationDao) {
		this.stationDao = stationDao;
	}

	public int getStaid() {
		return staid;
	}

	public void setStaid(int staid) {
		this.staid = staid;
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

	public String[] getTreeType() {
		return treeType;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getAjax() {
		return ajax;
	}

	public void setAjax(int ajax) {
		this.ajax = ajax;
	}

	public int getIsclassName() {
		return isclassName;
	}

	public void setIsclassName(int isclassName) {
		this.isclassName = isclassName;
	}

	public int getIsEroomName() {
		return isEroomName;
	}

	public void setIsEroomName(int isEroomName) {
		this.isEroomName = isEroomName;
	}

	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}

	public ExamRoom getEroom() {
		return eroom;
	}

	public void setEroom(ExamRoom eroom) {
		this.eroom = eroom;
	}

	public int getDBMethods() {
		return DBMethods;
	}

	public void setDBMethods(int methods) {
		DBMethods = methods;
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

	public List getTreeAllId() {
		return treeAllId;
	}

	public void setTreeAllId(List treeAllId) {
		this.treeAllId = treeAllId;
	}

	public List<BaseDatat> getAllJingzhongOplist() {
		return allJingzhongOplist;
	}

	public void setAllJingzhongOplist(List<BaseDatat> allJingzhongOplist) {
		this.allJingzhongOplist = allJingzhongOplist;
	}

	public List<BaseDatat> getMyJingzhongOplist() {
		return myJingzhongOplist;
	}

	public void setMyJingzhongOplist(List<BaseDatat> myJingzhongOplist) {
		this.myJingzhongOplist = myJingzhongOplist;
	}

	public String[] getDstj() {
		return dstj;
	}

	public void setDstj(String[] dstj) {
		this.dstj = dstj;
	}

	public ELClassRegistration getElRegistration() {
		return elRegistration;
	}

	public void setElRegistration(ELClassRegistration elRegistration) {
		this.elRegistration = elRegistration;
	}

	public ExamRoom getEroombd() {
		return eroombd;
	}

	public void setEroombd(ExamRoom eroombd) {
		this.eroombd = eroombd;
	}

	public List<MyRoom> getMyrooms() {
		return myrooms;
	}

	public void setMyrooms(List<MyRoom> myrooms) {
		this.myrooms = myrooms;
	}

	public String getStartTime_2() {
		return startTime_2;
	}

	public void setStartTime_2(String startTime_2) {
		this.startTime_2 = startTime_2;
	}

	public String getEndTime_2() {
		return endTime_2;
	}

	public void setEndTime_2(String endTime_2) {
		this.endTime_2 = endTime_2;
	}

	public Department getDep() {
		return dep;
	}

	public void setDep(Department dep) {
		this.dep = dep;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Course> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<Course> myCourses) {
		this.myCourses = myCourses;
	}

	public List<Course> getBxCourses() {
		return bxCourses;
	}

	public void setBxCourses(List<Course> bxCourses) {
		this.bxCourses = bxCourses;
	}

	public List<Course> getXxCourses() {
		return xxCourses;
	}

	public void setXxCourses(List<Course> xxCourses) {
		this.xxCourses = xxCourses;
	}

	public int getClassSource() {
		return classSource;
	}

	public void setClassSource(int classSource) {
		this.classSource = classSource;
	}

	public List<ElClass> getElclasses() {
		return elclasses;
	}

	public void setElclasses(List<ElClass> elclasses) {
		this.elclasses = elclasses;
	}

	public ElClass getElclass() {
		return elclass;
	}

	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public ElClType getCltypeTree() {
		return cltypeTree;
	}

	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}
	
	public List<ErPara> getErParas() {
		return erParas;
	}

	public void setErParas(List<ErPara> erParas) {
		this.erParas = erParas;
	}
	

	public String getFirstLearn() {
		return firstLearn;
	}

	public void setFirstLearn(String firstLearn) {
		this.firstLearn = firstLearn;
	}

	/**
	 * 添加培训班初始化
	 * @return
	 * @throws ElException
	 */
	public String elclass_addInit() throws ElException {  
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
//		}
//		else {
//			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
//		}
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
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		//初始化默认警种（创建者警种）
		elUser=userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		BaseDatat bdt=userDao.getBaseDatatById(elUser.getJingzhong());
//		getRequest().setAttribute("userJingzhong", bdt.getBasevalue());
//		int typeid = cltype == null ? cltypeTree.getId(): cltype.getId(); 
		group1 = roleDao.listGroupsBytype(1);
		group2 = roleDao.listGroupsBytype(2);
 
		if(cltypeTree.getChild().size() == 0 && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的培训班类别");
			 return "error"; 
		}
		return "elclass_add";
	}
	

	/**
	 * 添加岗位培训班初始化
	 * @return
	 * @throws ElException
	 */
	public String sta_elclass_addInit() throws ElException {  
		
		staid = this.getStaid();
		station = stationDao.getStById(staid);
		classid = stationDao.getClassid2(staid);
		if(classid!=0){
			return "elclass_alterInit";
		}
		if (StringUtils.isNotBlank(elclassId)) {
			elclass=classDao.getClassById(Integer.valueOf(elclassId));
		}
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		//初始化默认警种（创建者警种）
		elUser=userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		BaseDatat bdt=userDao.getBaseDatatById(elUser.getJingzhong());
		getRequest().setAttribute("userJingzhong", bdt.getBasevalue());
		group1 = roleDao.listGroupsBytype(1);
		group2 = roleDao.listGroupsBytype(2);
 
		if(cltypeTree.getChild().size() == 0 && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的培训班类别");
			 return "error"; 
		}
		return "elclass_add";
	}

	
	public ELClassRegistration setRegistration (ELClassRegistration elRegistration ,String[] jztj,String[] dstj,String[] zwtj,String[] zjtj,String[] gwtj,String[] treeType){
		//警种
		if(jztj != null && jztj.length>=1){
			String jz = "";
			for(int i =0 ;i<jztj.length;i++){
				if(i == jztj.length-1){
					jz = jz+jztj[i].toString();
					break;
				}else{
					jz = jztj[i].toString()+"-"+jz;					
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
//		if(treeType != null && treeType.length>=1){
//			String bm = "";
//			for(int i =0 ;i<treeType.length;i++){
//				if(i == treeType.length-1){
//					bm = bm+treeType[i].toString();
//					break;
//				}else{
//					bm = treeType[i].toString()+"-"+bm;					
//				}
//			}
//			elRegistration.setTreeType(bm);
//		}
		return elRegistration;
	}
	/**
	 * 获取到elRegistration时把String 还原 String[]
	 * @param elRegistration
	 * @return
	 */

	private List jztjs ;
	public ELClassRegistration setRegistration (ELClassRegistration elRegistration){
		//警种
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
		//警种
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
	 * 添加培训班
	 * @return
	 * @throws ElException
	 */
	public String elclass_add() throws ElException {  
		staid = this.getStaid();
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
			if(staid!=0){
				stationDao.updateSta(staid, id);
			//	return "sta_elclass_view";
				return "elclass_newassign2user_add";
			}
//			return "elclass_alllist";
			return "elclass_view";
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

	public String optionDep() throws ElException{

//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
//		}
//		else {
//			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
//		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
//		depTree = departmentDao.getDepTree(1, -1,true); 
		assignedDeps = new ArrayList<Department>();
		if(!searbm.equals("")){
			String[] searbms = searbm.split(",");
			for(int i = 0 ;i<searbms.length;i++){
				assignedDeps.add(departmentDao.getDepById(Integer.parseInt(searbms[i])));
			} 
		}
//		treeAllId = new ArrayList();
//		if(!searbm.equals("")){
//			String[] searbms = searbm.split(",");
//			for(int i = 0 ;i<searbms.length;i++){
//				treeAllId.add(Integer.parseInt(searbms[i]));
//			} 
//		}
		return "optionDep";
	} 
	
	public String optionBaseDatat() throws ElException{ 
		//baseDatatList = userDao.getBaseDatatByTypeid(baseDatat.getTypeid(),getPageNow(),getPageSize());//1警种
		baseDatatList = userDao.getBaseDatatByTypeid(baseDatat.getTypeid());
		if(!searbase.equals("")){
			String[] searBase = searbase.split(",");
			for(int i = 0 ;i<baseDatatList.size();i++){
				for(int x = 0 ;x<searBase.length;x++)
				if(baseDatatList.get(i).getId() == Integer.parseInt(searBase[x])){
					baseDatatList.get(i).setSelected(1);
				} 
			} 
		} 
		return "optionBaseDatat";
	}
	
	/**
	 * 我的培训班
	 * @return
	 * @throws ElException
	 */
	public String elclass_list() throws ElException {
		int typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
		String name = elClass == null ? "" : elClass.getName();
		
		
// 		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT, ElConstants.TREE_FIANL, true); 
		//cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				//ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),true,"CLASS_USE_TYPE");
 		//hwc

 		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
 			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
 		}else{
 			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
 		}
// 		int typeid = cltype == null ? cltypeTree.getId(): cltype.getId();
 		
 		
 		
 		elclasses = classDao.listClasses(getSessionIntValue(ElConstants.SESSION_USERID), typeid, name, getPageNow(), getPageSize());
		count = classDao.listClassesSize( 
				getSessionIntValue(ElConstants.SESSION_USERID), typeid, name);
		return "elclass_list";
	}
	
	/**
	 * 培训班列表
	 * @return
	 * @throws ElException
	 */
	public String elclass_alllist() throws ElException {
//		int typeid = 1;
//		
//		String name = elClass == null ? "" : elClass.getName(); 
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		staid = this.getStaid();
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
 		String sqlw="0,1,2,3,4,5,6,7,8";
 		if(elClass!=null&&elClass.getSqlw()==9){
 			sqlw="9";
 		}
		sublibs = elClass == null ? 1 : sublibs;
 		elclasses = classDao.getClassList(cltype, elClass,sublibs,sqlw,"0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype, elClass,sublibs,sqlw);
		if(staid!=0){
			return "sta_elclass_alllist";
		}
		return "elclass_alllist";
	}
	
	public String elclass_hotsetlist() throws ElException {
		staid = this.getStaid();
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
 		if(elClass!=null&&elClass.getSqlw()==9){
 			sqlw="9";
 		}
		sublibs = elClass == null ? 1 : sublibs;
 		elclasses = classDao.getClassList(cltype, elClass,sublibs,sqlw,"0,1,2,3,4,5,6,7,8", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype, elClass,sublibs,sqlw);
		if(staid!=0){
			return "sta_elclass_alllist";
		}
		return "elclass_hotsetlist";
	}
	
	public String elclass_hotset() throws ElException {
		if (null != elclass)
		classDao.elclassHotSet(elclass.getId(),elclass.getHot());
		//刷新首页课程模块
	//	indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		return "elclass_alllist";
	}
	
	/**
	 * 培训班终审列表
	 * @return
	 * @throws ElException
	 */
	public String elclass_sh_list() throws ElException {
//		int typeid = 1;
//		if(sublibs != 0){
//			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		}
//		if(typeid == 0){
//			typeid = 1;
//		}
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
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
		return "elclass_sh_list";
	}
	
	/**
	 * 培训班时间重叠列表
	 * @return
	 */
	public String elclass_timeover_list() throws ElException {
		elclass=classDao.getClassById2(elclass.getId());
		elclasses=classDao.getClassTimeoverList(elclass, getPageNow(), getPageSize());
		count=classDao.getClassTimeoverListCount(elclass);
		return "elclass_timeover_list";
	}
	
	/**
	 * 培训班初审列表
	 * @return
	 * @throws ElException
	 */
	public String elclass_primash_list() throws ElException {
//		int typeid = 1;
//		if(sublibs != 0){
//			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		}
//		if(typeid == 0){
//			typeid = 1;
//		}
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}
		else
		{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
//		typeid=cltypeTree.getId();
//		elclasses = classDao.getClassesList(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8,9",
//				getSessionIntValue(ElConstants.SESSION_ROLE),"1", getPageNow(), getPageSize());
//		count = classDao.getClassesSize(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8,9"
//				,getSessionIntValue(ElConstants.SESSION_ROLE));
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
		elclasses = classDao.getClassList(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8","1", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		return "elclass_primash_list";
	}	
	/**
	 * 培训班暂停与恢复
	 * @return
	 * @throws ElException
	 */
	public String elclass_suspended_recovery() throws ElException {
		int typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}
		else
		{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		elclasses = classDao.getClassesList(cltypeTree, depid, typeid, elClass,"5,11",
				getSessionIntValue(ElConstants.SESSION_ROLE),"1", getPageNow(), getPageSize());
		count = classDao.getClassesSize(cltypeTree, depid, typeid, elClass,"5,11"
				,getSessionIntValue(ElConstants.SESSION_ROLE));
		return "elclass_suspended_recovery";
	}
	/**
	 * 培训班申请修改列表
	 * @return
	 * @throws ElException
	 */
	public String elclass_applyAlter_list() throws ElException {
//		int typeid = 1;
//		if(sublibs != 0){
//			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		}
//		if(typeid == 0){
//			typeid = 1;
//		}
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		} 
//		elclasses = classDao.getClassesList(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8",
//				getSessionIntValue(ElConstants.SESSION_ROLE),"5", getPageNow(), getPageSize());
//		count = classDao.getClassesSize(cltypeTree, depid, typeid, elClass,"0,1,2,3,4,5,6,7,8"
//				,getSessionIntValue(ElConstants.SESSION_ROLE));
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
		elclasses = classDao.getClassList(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8","5", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		return "elclass_applyAlter_list";
	}
	/**
	 * 培训班审核申请
	 * @return
	 * @throws ElException
	 */
	public String elclass_sh_apply() throws ElException {
		classDao.shClass(elclass.getId(), ecAudit.getStatus()); 
		//2. 插入备注内容
		ElclassAuditDescribes eA= classDao.getClassAudit(elclass.getId());  
		ecAudit.setClassid(elclass.getId()); 
		ecAudit.setUser(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
			if(eA == null){//如果为空。 为第一次 。 第一次为增加数据。
				classDao.setClassAudit(ecAudit);
			}else{
				ecAudit.setId(eA.getId()); 
				ecAudit.setReplycontent(eA.getReplycontent());//把原来的还给更新的
				classDao.UClassAuditContents(ecAudit);
			}  
		ecAudit= classDao.getClassAudit(elclass.getId());  
		return "elclass_list";
	}
	
	/**
	 * 培训班审核
	 * @return
	 * @throws ElException
	 */
	public String elclass_sh() throws ElException {
		staid = this.getStaid();
		StringBuffer msg = new StringBuffer();
		//判断如果状态为1就是创建完成
		if(state == 1){
			// 获取培训班的必修课列表
			List<Course> cList = classDao.listClassCourses(elclass.getId(),0);
			if(cList!=null)
			for(int i = 0; i < cList.size();i++){
				//判断如果结业方式不等于考过
				if(cList.get(i).getGetcredit() != 2){
					//课程长为0就不能创建
					if(cList.get(i).getDuring() == 0&&cList.get(i).getIslink()!=ElConstants.COURSE_TYPE_SCORM){
						msg.append("<br>该培训班["+cList.get(i).getName()+"]必修课课程时长不能等于0</br>");
					}
				}
			}
			// 获取培训班的选修课
			List<Course> cList2 = classDao.listClassCourses(elclass.getId(),1);
			//全部选修课的设置学分
			int sumscroe = 0;
			if(cList2!=null)
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
//			if(cList==null||cList.size()<=0){
//				msg.append("<br>该培训班至少需要一门必修课</br>");
//			}
			elclass = classDao.getClassById(elclass.getId());
			if(elclass.getClasstype()==0){
				if(cList2==null||cList2.size()<=0){
					msg.append("<br>该培训班至少需要一门选修课</br>");
				}
			}
			//判断如果结业条件少于全部选修课的设置学分
			if(elclass.getOptionalcredit() > sumscroe){
				msg.append("<br>该培训班结业条件不能大于修课的设置学分</br>");
			}
			if(!"".equals(msg.toString())){
				setElmessage(msg.toString());
				return "error";
			}
		}
		//如果要走审核流程，把下面这个判断去掉
//		if(status==1){
//			//把创建完成 改成直接开通
//			classDao.shUvalid(elclass.getId(), 1);
//			status=5;
//		}
		if(staid!=0){
			classDao.shUvalid(elclass.getId(), 1);
			status = 5;
			Return = "elclass_suspended_recovery";
		}else{
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
		if(sublibs==1){
			return "elclass_view";
		//	return "elclass_newassign2user_add";
		}
		return Return;
	}
	
	public String elclass_view_man() throws ElException {
		staid = this.getStaid();
		elclass = classDao.getClassById(elclass.getId());
		elclass.setElRegistration(classDao.getClassRegistration(elclass.getId())); 
		elRegistration = setRegistration(elclass.getElRegistration());
		if(elRegistration!=null){
			elRegistration.toErParams();
			elRegistration.toErepParams();
			elRegistration.toClassParams();
		}
		elclass.setBxCourse(classDao.listClassCourses(elclass.getId(), 0));
		elclass.setXxCourse(classDao.listClassCourses(elclass.getId(), 1));
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		}else{
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
		int depid = 1;
		if(department==null||department.getId()<=0){
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		if(elUser == null){
			elUser = new ELUser();
			elUser.setIsAssign("0");
		}
		elusers = classDao.listAssignedUser(getPageNow(), getPageSize(),depid,elclass.getId(),CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,1,depTree,stTree);
		count =classDao.listAssignedUserSize(depid,elclass.getId(),CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,1,depTree,stTree);
		
		//是否具有提交申请权限
		elclass.setBxCount(roleDao.checkRolefunc(getSessionIntValue(ElConstants.SESSION_ROLE),"elclass_primash_list")?1:0);
		//是否具有审核权限
		elclass.setXxCount(roleDao.checkRolefunc(getSessionIntValue(ElConstants.SESSION_ROLE),"elclass_sh_list")?1:0);
		if(staid!=0){
			return "sta_elclass_view_man";
		}
		return "elclass_view_man";
	}

	/**
	 * 修改初始化
	 * @return
	 * @throws ElException
	 */
	public String elclass_alterInit() throws ElException {
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
//		}
//		else {
//			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
//		}
		//elclass = classDao.getClassById(getSessionIntValue(ElConstants.SESSION_USERID), elclass.getId());//类型报空指针异常
		elclass = classDao.getClassById(elclass.getId());
		staid = this.getStaid();
//		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		
//		cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//				ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),true,"CLASS_USE_TYPE");
		
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		elclass.setElRegistration(classDao.getClassRegistration(elclass.getId())); 
		elRegistration = setRegistration(elclass.getElRegistration());
		elclass.setValids(classDao.getElclassUsers("valids", elclass.getId()));
		if(staid!=0){
			return "sta_elclass_alter";
		}  
		return "elclass_alter";
	}
	/**
	 * 复制培训班
	 * @return
	 * @throws ElException
	 */
	public String elclass_copy() throws ElException {
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
	 * 培训班基本信息修改
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_alter() throws ElException { 
		elclass.setCreater(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		//ELClassRegistration elR = classDao.getClassRegistration(elclass.getId()); 
		classDao.alterClass(elclass); 
		if(examRooms != null){
			elRegistration.setExamRoom(examRooms);
		}
//		else{  
//			if(isEroomName == 0){ 
//			}else{
//				elRegistration.setExamRoom(elR.getExamRoom());				
//			}
//		}  
		if(elClasss != null){
			elRegistration.setElclasss(elClasss);
		}
//		else{
//			if(isclassName == 0){ 
//			}else{
//				elRegistration.setElclasss(elR.getElclasss());
//			}
//		}
		if(elclass.getIsApplication()==1 && elRegistration != null) {//是否为可申请
			elRegistration.setElclass(elclass);
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
//		return "elclass_alter_success";
		//return "elclass_addInit";
		elclass=classDao.getClassById(elclass.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_ALTER,elclass.getName(),
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
//		return "elclass_alterInit";
		staid = this.getStaid();
		if(staid!=0){
			return "sta_elclass_view";
		}
		return "elclass_view";
	}

	/**
	 * 培训班分配初始化
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_assignlistInit() throws ElException { 
		return "elclass_assignlistInit";
	}

	/**
	 * 可分配培训班
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_assignlist() throws ElException {
		int deptId = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
//		String name = elClass == null ? "" : elClass.getName();
		
		
//		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
//		cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//				ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),true,"CLASS_USE_TYPE");
//		int typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
		

		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		int typeid = cltype == null ? cltypeTree.getId(): cltype.getId();
		
		elclasses = classDao.getClassesList(cltypeTree, deptId, typeid, elClass,""+ClassConstants.CLASS_STATUS_HASOPENED+"",getSessionIntValue(ElConstants.SESSION_ROLE),"-1", getPageNow(), getPageSize());
		count = classDao.getClassesSize(cltypeTree, deptId, typeid, elClass,""+ClassConstants.CLASS_STATUS_HASOPENED+"" ,getSessionIntValue(ElConstants.SESSION_ROLE));
		
		return "elclass_assignlist";
	}

	public String elclass_uvalidlist() throws ElException {   

		int typeid = 1;
		if(sublibs != 0){
			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
		}
		if(typeid == 0){
			typeid = 1;
		}
		int deptId = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);  
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		} 
		elclasses = classDao.getClassesList2(cltypeTree, deptId, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE),"0,2", getPageNow(), getPageSize());
		count = classDao.getClassesSize2(cltypeTree, deptId, typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE));
		return "elclass_uvalidlist";
	}
	/**
	 * 培训班人员分配
	 * @return
	 * @throws ElException
	 */
	public String elclass_assignlist2() throws ElException {//第二种流程分配   
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
		return "elclass_assignlist2";
	}
				  
	public String elclass_examroom_validlist() throws ElException {   
		if(examRoom == null ){
			examRoom = new ExamRoom();
			examRoom.setClassid(1);
			examRoom.setType(-1);
			examRoom.setValid(-1);
		} 
		examRooms = eroomDao.listExamRoomValid(
				getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE)," and er.valid != 9 and er.iscommon = 0 ",examRoom, getPageNow(),
				getPageSize());
		count = eroomDao
				.listExamRoomValidsize(getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"and er.iscommon = 0 and er.valid != 9 ",examRoom);
		return "elclass_examroom_validlist";
	}  
	
	public String elclass_examroom_selectinglist() throws ElException { 
		if(examRoom == null ){
			examRoom = new ExamRoom();
			examRoom.setClassid(1);
			examRoom.setValid(-1); 
		}
		examRoom.setSvalid(-1); 
		examRooms = eroomDao.listExamRoomSelectings(
				getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"and er.iscommon = 0 and er.valid != 9 and er.type = 1 and er.svalid = 5  ",examRoom, getPageNow(),
				getPageSize());
		count = eroomDao
				.listExamRoomSelectingsSize(getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"and er.iscommon = 0 and er.valid != 9 and er.type = 1 and er.svalid = 5  ",examRoom);
		return "elclass_examroom_selectinglist";
	}

	public String elclass_examroom_selecting_prima_shlist() throws ElException { 
		if(examRoom == null ){
			examRoom = new ExamRoom();
			examRoom.setClassid(1);
			examRoom.setSvalid(-1);
		}
		examRoom.setValid(-1);
		examRooms = eroomDao.listExamRoomSelectings(
				getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"and er.iscommon = 0 and er.valid != 9 and er.type = 1 and er.uvalid = 1 and er.svalid not in (0,2,3,5,6,7,8,9) ",examRoom, getPageNow(),
				getPageSize());
		count = eroomDao
				.listExamRoomSelectingsSize(getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"and er.iscommon = 0 and er.valid != 9 and er.type = 1 and er.uvalid = 1",examRoom);
		return "elclass_examroom_selecting_prima_shlist";
	}
	
	public String elclass_examroom_selecting_shlist() throws ElException { 
		if(examRoom == null ){
			examRoom = new ExamRoom();
			examRoom.setClassid(1);
			examRoom.setSvalid(-1);
		}
		examRoom.setValid(-1);
		examRooms = eroomDao.listExamRoomSelectings(
				getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"and er.iscommon = 0 and er.valid != 9 and er.type = 1 and er.uvalid = 1 and er.svalid not in (0,1,2,4,6,7,8,9)",examRoom, getPageNow(),
				getPageSize());
		count = eroomDao
				.listExamRoomSelectingsSize(getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"and er.iscommon = 0 and er.valid != 9 and er.type = 1 and er.uvalid = 1 and er.svalid not in (0,1,2,4,6,7,8,9)",examRoom);
		return "elclass_examroom_selecting_shlist";
	}
	/**
	 * 处理修改申请
	 * @return
	 * @throws ElException
	 */
	public String elclass_alter_list() throws ElException {//第二种流程  
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
//		elclasses = classDao.getClassesList(cltypeTree, deptId, typeid,elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE),"6", getPageNow(), getPageSize());
//		count = classDao.getClassesSize(cltypeTree, deptId, typeid ,elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE));
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
		elclasses = classDao.getClassList(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8","6", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		return "elclass_alter_list";
	}
	/**
	 * 申请删除
	 * @return
	 * @throws ElException
	 */
	public String elclass_applyDelete_list() throws ElException {//第二种流程  
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
//		elclasses = classDao.getClassesList(cltypeTree, deptId, typeid,elClass,"0,1,2,3,4,5,6,7,8" ,getSessionIntValue(ElConstants.SESSION_ROLE),"0,1,2,3,4,5,6,7", getPageNow(), getPageSize());
//		count = classDao.getClassesSize(cltypeTree, deptId, typeid ,elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE));
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
		elclasses = classDao.getClassList(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8","0,1,2,3,4,5,6,7", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		return "elclass_applyDelete_list";
	}
	
	public String elclass_alter_sh() throws ElException{
		classDao.shClass(elclass.getId(), status); 
		return "elclass_alter_sh";
	}
	/**
	 * 培训班课程列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_course() throws ElException {
		staid = this.getStaid();
//		myCourses = classDao.listAllClassCourse(elclass.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
		elclass = classDao.getClassById(elclass.getId());
		bxCourses = classDao.listClassCourses(elclass.getId(),CourseConstants.COURSE_STUDY_STATUS_BX);
		xxCourses = classDao.listClassCourses(elclass.getId(),CourseConstants.COURSE_STUDY_STATUS_XX);
//		zxCourses = classDao.listClassCourses(elclass.getId(),CourseConstants.COURSE_STUDY_STATUS_ZX);
		if(staid!=0){
			return "sta_elclass_course";
		}
		return "elclass_course";
	}
	
	/**
	 * 培训班课程选择列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_selectList() throws ElException {
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		
		//ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true,String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),true,"COURSE_OP_TYPE");
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();
		String name = course == null ? "" : course.getName();
		
		courses = courseDao.listAllSelectCourse(ctypeTree,depid, name, ctid,getPageNow(), getPageSize(),CourseConstants.COURSE_STATUS_HASOPENED,Integer.valueOf(elclassId),getSessionIntValue(ElConstants.SESSION_ROLE));
		count = courseDao.listAllSelectCourseSize(ctypeTree,depid, name, ctid,CourseConstants.COURSE_STATUS_HASOPENED,Integer.valueOf(elclassId),getSessionIntValue(ElConstants.SESSION_ROLE));
		return "elclass_course_selectList";
	}
	
	
	public String elclass_course_modify() throws ElException { 
		Map map = new HashMap();
		try {
			map.put("elclassId", Integer.valueOf(elclassId.trim()));
			if (StringUtils.isBlank(suggestcredit)) {
				map.put("suggestcredit", 0);
			} else {
				map.put("suggestcredit", Integer.valueOf(suggestcredit.trim()));
			}
			if (StringUtils.isBlank(setcredit)) {
				map.put("setcredit", 0);
			} else {
				map.put("setcredit", Integer.valueOf(setcredit.trim()));
			}
			if (StringUtils.isBlank(getcredit)) {
				map.put("getcredit", 0);
			} else {
				map.put("getcredit", Integer.valueOf(getcredit.trim()));
			}
			if (StringUtils.isBlank(startTime_2)) {
				map.put("startTime_2", 0);
			} else {
				map.put("startTime_2", startTime_2.trim());
			}
			if (StringUtils.isBlank(endTime_2)) {
				map.put("endTime_2", 0);
			} else {
				map.put("endTime_2", endTime_2.trim());
			}
			if (StringUtils.isBlank(orderid)) {
				map.put("orderid", 0);
			} else {
				map.put("orderid", Integer.valueOf(orderid.trim()));
			}
			if (StringUtils.isBlank(firstLearn)) {
				map.put("firstLearn", 0);
			} else {
				map.put("firstLearn", Integer.valueOf(firstLearn.trim()));
			}
			map.put("courseId", Integer.valueOf(courseId.trim()));
		} catch (Exception e) {
			logger.error("培训班课程修改失败",e);
		}
		classDao.updateCourseRelation(map);
		elclass=classDao.getClassById(Integer.valueOf(elclassId.trim()));
		if(elclass!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ALTER,elclass.getName()+"(修改课程)",
					ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		}
		if(status==1)
			return "elclass_course_bx";
		else
			return "elclass_course_xx";
		//return null;
	}
	
	/**
	 * 培训班必修课程列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_bx() throws ElException {
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
	public String elclass_details_bx() throws ElException {
		if(StringUtils.isNotBlank(elclassId)){
			bxCourses = classDao.listClassCourses(Integer.valueOf(elclassId), CourseConstants.COURSE_STUDY_STATUS_BX);
			elclass=classDao.getClassById(Integer.valueOf(elclassId)); 
			for(int i = 0 ; i < bxCourses.size() ; i++){
				if(eroomDao.checkuserClassBindingCourse(Integer.valueOf(elclassId), bxCourses.get(i).getId())){
					examRoom = eroomDao.getClassBindingCourseByRoomId(Integer.valueOf(elclassId), bxCourses.get(i).getId());
					if(examRoom != null){
					examRoom = eroomDao.getExamRoomByid(examRoom.getId()); 
						if(examRoom != null){
							bxCourses.get(i).setEroom(examRoom); 
							bxCourses.get(i).setDuring(examRoom.getId());//借用during字段用于显示examRoom.id。
						}
					}
				}
			}
		} 
		return "elclass_details_bx";
	}
	public String elclass_details_bx_sh() throws ElException {
		if(StringUtils.isNotBlank(elclassId)){
			bxCourses = classDao.listClassCourses(Integer.valueOf(elclassId), CourseConstants.COURSE_STUDY_STATUS_BX);
			elclass=classDao.getClassById(Integer.valueOf(elclassId)); 
			for(int i = 0 ; i < bxCourses.size() ; i++){
				if(eroomDao.checkuserClassBindingCourse(Integer.valueOf(elclassId), bxCourses.get(i).getId())){
					examRoom = eroomDao.getClassBindingCourseByRoomId(Integer.valueOf(elclassId), bxCourses.get(i).getId());
					if(examRoom != null){
					examRoom = eroomDao.getExamRoomByid(examRoom.getId()); 
						if(examRoom != null){
							bxCourses.get(i).setEroom(examRoom); 
		 					bxCourses.get(i).setDuring(examRoom.getId());//借用during字段用于显示examRoom.id。
						}
					}
				}
			}
		} 
//		PageStatus = PageStatus;
		return "elclass_details_bx_sh";
	}
	/**
	 * 培训班主修课程列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_zx() throws ElException {
		if(StringUtils.isNotBlank(elclassId)){
			zxCourses = classDao.listClassCourses(Integer.valueOf(elclassId),CourseConstants.COURSE_STUDY_STATUS_ZX); 
		}
		return "elclass_course_zx";
	}
	
	/**
	 * 培训班选修课程列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_xx() throws ElException {
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
	/**培训班课程信息修改
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_alterinit() throws ElException {
		int classid = StringUtils.isNotBlank(elclassId)?Integer.valueOf(elclassId):0;
		course = classDao.getClassCourse(classid, course.getId());
		elclass = classDao.getClassById(classid);
		return "elclass_course_alter";
	}
	
	public String elclass_details_xx() throws ElException {
		if(StringUtils.isNotBlank(elclassId))
		xxCourses = classDao.listClassCourses(Integer.valueOf(elclassId),CourseConstants.COURSE_STUDY_STATUS_XX);
		elclass=classDao.getClassById(Integer.valueOf(elclassId)); 
		return "elclass_details_xx";
	}
	
	public String elclass_details_xx_sh() throws ElException { 
		if(StringUtils.isNotBlank(elclassId)){
			xxCourses = classDao.listClassCourses(Integer.valueOf(elclassId),CourseConstants.COURSE_STUDY_STATUS_XX);
			elclass=classDao.getClassById(Integer.valueOf(elclassId));  
			for(int i = 0 ; i < xxCourses.size() ; i++){
				if(eroomDao.checkuserClassBindingCourse(Integer.valueOf(elclassId), xxCourses.get(i).getId())){
					examRoom = eroomDao.getClassBindingCourseByRoomId(Integer.valueOf(elclassId), xxCourses.get(i).getId());
					if(examRoom != null){
					examRoom = eroomDao.getExamRoomByid(examRoom.getId()); 
						if(examRoom != null){
							xxCourses.get(i).setEroom(examRoom); 
		 					xxCourses.get(i).setDuring(examRoom.getId());//借用during字段用于显示examRoom.id。
						}
					}
				}
			}
//			PageStatus = PageStatus;
		}
		return "elclass_details_xx_sh";
	}
	/**
	 * 培训班课程添加
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_add() throws ElException {
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
	
	/**
	 * 必修课培训班课程删除
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_bx_del() throws ElException {
		//classDao.deleteClassCourse(Integer.valueOf(elclassId), Integer.valueOf(courseId));
		if(course==null){
			return "elclass_course_xx";
		}else{
			if(course.getIsDel()==-1){
				//假
				classDao.deleteClassCourse2(Integer.valueOf(elclassId), Integer.valueOf(courseId));//假删除
			}else if(course.getIsDel()==1){
				classDao.deleteClassCourse(Integer.valueOf(elclassId), Integer.valueOf(courseId));
			}
		}

//		course = courseDao.getCourseById(Integer.valueOf(elclassId));
//		elclass  = classDao.getClassById(Integer.valueOf(courseId));
		course = courseDao.getCourseById(Integer.valueOf(courseId));
		elclass  = classDao.getClassById(Integer.valueOf(elclassId));
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_DELETE, elclass.getName()+" -> 必修课程【"+course.getName()+"】被删除",
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		return "elclass_course_bx";
	}
	
	/**
	 * 选修课培训班课程删除
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_xx_del() throws ElException {
		//classDao.deleteClassCourse(Integer.valueOf(elclassId), Integer.valueOf(courseId));
		if(course==null){
			return "elclass_course_xx";
		}else{
			if(course.getIsDel()==-1){
				//假
				classDao.deleteClassCourse2(Integer.valueOf(elclassId), Integer.valueOf(courseId));//假删除
			}else if(course.getIsDel()==1){
				classDao.deleteClassCourse(Integer.valueOf(elclassId), Integer.valueOf(courseId));
			}
		}

//		course = courseDao.getCourseById(Integer.valueOf(elclassId));
//		elclass  = classDao.getClassById(Integer.valueOf(courseId));
		course = courseDao.getCourseById(Integer.valueOf(courseId));
		elclass  = classDao.getClassById(Integer.valueOf(elclassId));
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_DELETE, elclass.getName()+" -> 选修课程【"+course.getName()+"】被删除",
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		return "elclass_course_xx";
	}
	/**
	 * 培训班课程恢复
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_xx_restoration() throws ElException {
		classDao.restorationClassCourse(Integer.valueOf(elclassId), Integer.valueOf(courseId));
		if(course.getType()==0){
			return "elclass_course_bx";
		}
		return "elclass_course_xx";
	}
	/**
	 * 主修课培训班课程删除
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_zx_del() throws ElException {
		//classDao.deleteClassCourse(Integer.valueOf(elclassId), Integer.valueOf(courseId));
		if(course==null){
			return "elclass_course_zx";
		}else{
			if(course.getIsDel()==-1){
				//假
				classDao.deleteClassCourse2(Integer.valueOf(elclassId), Integer.valueOf(courseId));//假删除
			}else if(course.getIsDel()==1){
				classDao.deleteClassCourse(Integer.valueOf(elclassId), Integer.valueOf(courseId));
			}
		}
		course = courseDao.getCourseById(Integer.valueOf(courseId));
		elclass  = classDao.getClassById(Integer.valueOf(elclassId));
		if(course!=null&&elclass!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_DELETE, elclass.getName()+" -> 必修课程【"+course.getName()+"】被删除",
					ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		}
		return "elclass_course_zx";
	}

	/**
	 * 培训班课程删除
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_delete() throws ElException {
		if (null != bxCourses)
			for (int i = 0; i < bxCourses.size(); i++) {
				classDao.deleteClassCourse(elclass.getId(), bxCourses
						.get(i).getId());
			}
		if (null != xxCourses)
			for (int i = 0; i < xxCourses.size(); i++) {
				classDao.deleteClassCourse(elclass.getId(), xxCourses
						.get(i).getId());
			}
		if (null != zxCourses)
			for (int i = 0; i < zxCourses.size(); i++) {
				classDao.deleteClassCourse(elclass.getId(), zxCourses
						.get(i).getId());
			}
		/*
		 * myCourses = classDao.listAllClassCourse(elclass.getId(),
		 * getSessionIntValue(ElConstants.SESSION_USERID)); bxCourses =
		 * classDao.listClassCourses(elclass.getId(),
		 * ClassConstants.CLASS_COURSE_STATUS_BX); xxCourses =
		 * classDao.listClassCourses(elclass.getId(),
		 * ClassConstants.CLASS_COURSE_STATUS_XX);
		 */
		return "elclass_course";
	}

//	/**
//	 * 培训班课程添加
//	 * 
//	 * @return
//	 * @throws ElException
//	 */
//	public String elclass_course_add() throws ElException {
//		if (null != myCourses)
//			for (int i = 0; i < myCourses.size(); i++) {
//				classDao.addClassCourse(elclass.getId(), myCourses.get(i)
//						.getId(), status);
//			}
//
//		/*
//		 * myCourses = classDao.listAllClassCourse(elclass.getId(),
//		 * getSessionIntValue(ElConstants.SESSION_USERID)); bxCourses =
//		 * classDao.listClassCourses(elclass.getId(),
//		 * ClassConstants.CLASS_COURSE_STATUS_BX); xxCourses =
//		 * classDao.listClassCourses(elclass.getId(),
//		 * ClassConstants.CLASS_COURSE_STATUS_XX);
//		 */
//		return "elclass_course";
//	}
//
//	/**
//	 * 培训班课程删除
//	 * 
//	 * @return
//	 * @throws ElException
//	 */
//	public String elclass_course_delete() throws ElException {
//		if (null != bxCourses)
//			for (int i = 0; i < bxCourses.size(); i++) {
//				classDao.deleteClassCourse(elclass.getId(), bxCourses
//						.get(i).getId());
//			}
//		if (null != xxCourses)
//			for (int i = 0; i < xxCourses.size(); i++) {
//				classDao.deleteClassCourse(elclass.getId(), xxCourses
//						.get(i).getId());
//			}
//		if (null != zxCourses)
//			for (int i = 0; i < zxCourses.size(); i++) {
//				classDao.deleteClassCourse(elclass.getId(), zxCourses
//						.get(i).getId());
//			}
//		/*
//		 * myCourses = classDao.listAllClassCourse(elclass.getId(),
//		 * getSessionIntValue(ElConstants.SESSION_USERID)); bxCourses =
//		 * classDao.listClassCourses(elclass.getId(),
//		 * ClassConstants.CLASS_COURSE_STATUS_BX); xxCourses =
//		 * classDao.listClassCourses(elclass.getId(),
//		 * ClassConstants.CLASS_COURSE_STATUS_XX);
//		 */
//		return "elclass_course";
//	}

	/**
	 * 培训班课程学分设置
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_credit() throws ElException {
		bxCourses = classDao.listClassCourses(elclass.getId(),
				CourseConstants.COURSE_STUDY_STATUS_BX);
		xxCourses = classDao.listClassCourses(elclass.getId(),
				CourseConstants.COURSE_STUDY_STATUS_XX);
		zxCourses = classDao.listClassCourses(elclass.getId(),
				CourseConstants.COURSE_STUDY_STATUS_ZX);
		return "elclass_course_credit";
	}

	/**
	 * 培训班课程学分设置
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_course_credit_alter() throws ElException {
		classDao.alterClassCourseCredit(course, elclass.getId());
		setElmessage("修改成功!");
		return "elclass_course_credit_alter_success";
	}

	/**
	 * 培训班删除申请初始化
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_applydeleteInit() throws ElException {
		elclass = classDao
				.getClassById(
						getSessionIntValue(ElConstants.SESSION_USERID), elclass
								.getId());
		return "elclass_applydelete";
	}

	/**
	 * 培训班删除申请
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_applydelete() throws ElException {

		classDao.applyClassDelete(elclass.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID));
		elclass=classDao.getClassById(elclass.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_APPDELETE,elclass.getName(),
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		return "elclass_applydelete_success";
	}

	/**
	 * 班级分配到用户
	 * 
	 * @return
	 * @throws ElException
	 */
	Department canAssignDep;
	Department assignedDep;

	public Department getCanAssignDep() {
		return canAssignDep;
	}

	public void setCanAssignDep(Department canAssignDep) {
		this.canAssignDep = canAssignDep;
	}

	public Department getAssignedDep() {
		return assignedDep;
	}

	public void setAssignedDep(Department assignedDep) {
		this.assignedDep = assignedDep;
	}
	private Integer deptid;
	private List<ELUser> elusers;
	private String starttime;
	private String endtime;
	private ELUser elUser;
	private List<ClassPara> classPara;
	
	public List<ClassPara> getClassPara() {
		return classPara;
	}

	public void setClassPara(List<ClassPara> classPara) {
		this.classPara = classPara;
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

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
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

	public String elclass_assign2userInit() throws ElException { 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		}else{
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
		int depid = 1;
//		if(department==null){ 
//			if(depTree.getId() == -2){
//				depid = -2;
//				department = new Department(-2); 
//			}else{
//				department=new Department(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
//			}
//		}else{ 
//			depid = department.getId();
//		}
		if(department==null||department.getId()<=0){
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		int cid = (elclass == null) ? 0 : elclass.getId();
		if(DBMethods == 0){//按人员信息搜索
			elusers = classDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,department,station);
			count =classDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,department,station);
		}else if(DBMethods == 1){ //按培训班信息搜索
			if( elClasss == null || elClasss.size() == 0){ 
				setElmessage("没有选择培训班");
				return "error";
			}
			elClass = classDao.getClassById(elClasss.get(0).getId());
			elusers = classDao.gettoClassInfoselectUser(depTree,department,"study_class",cid, elClass.getId(), elUser,this.getStarttime(),this.getEndtime(),getPageNow(), getPageSize());
			count = classDao.gettoClassInfoselectUserSize(depTree,department,"study_class",cid,elClass.getId(), elUser,this.getStarttime(),this.getEndtime()); 
		}else if(DBMethods == 2){//按考场信息搜索
			if( examRooms == null || examRooms.size()== 0 ){ 
				setElmessage("没有选择考场");
				return "error";
			}
			examRoom = eroomDao.getExamRoomByid(examRooms.get(0).getId());
			elusers = classDao.gettoEroomInfoselectUser(depTree,department,"study_class",cid,examRoom.getId(), elUser,this.getStarttime(),this.getEndtime(),getPageNow(), getPageSize());
			count = classDao.gettoEroomInfoselectUserSize(depTree,department,"study_class",cid,examRoom.getId(), elUser,this.getStarttime(),this.getEndtime());
			examPapers = eroomDao.getEroomeps(examRoom.getId()); 
		}
 
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		zhiwus=userDao.getBaseDatatByTypeid(2);
		zhijis=userDao.getBaseDatatByTypeid(3);
		gangweis=userDao.getBaseDatatByTypeid(4);
		dishis=userDao.getBaseDatatByTypeid(5);
		elclass = classDao.getClassById(cid);
		return "elclass_assign2userInit";
	} 
	public String elclass_assign2user_toEroomInfoInit() throws ElException { 
		stTree = stationDao.getStTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
				true);
		depTree = departmentDao.getDepTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		int depid = department == null ? 1 : department.getId(); 
		if(department==null){
			department=new Department(1); 
		} 
		department.setId(depid);
		
		int cid = (elclass == null) ? 0 : elclass.getId();
		elusers = classDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,depTree,stTree);
		count =classDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,sub_department,depTree,stTree);
  
		return "elclass_assign2userInit";
	}
	public String elclass_check_students() throws ElException {
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
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		zhiwus=userDao.getBaseDatatByTypeid(2);
		zhijis=userDao.getBaseDatatByTypeid(3);
		gangweis=userDao.getBaseDatatByTypeid(4);
		dishis=userDao.getBaseDatatByTypeid(5);
		return "elclass_check_students";
	}
	public String elclass_assign2userInit2() throws ElException { 
		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		stTree = stationDao.getStTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
				true);
		int cid = (elclass == null) ? 0 : elclass.getId();
		elusers = classDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,1,depTree,stTree);//1包含下级
		count =classDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,1,depTree,stTree);
		
		return "elclass_assign2userInit2";
	}
	public String elclass_Modify_applicationInit() throws ElException { 
		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);  
		int cid = (elclass == null) ? 0 : elclass.getId();
		elusers = classDao.listAssignedUserIsAssign(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		count =classDao.listAssignedUserIsAssignSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		ecAudit= classDao.getClassAudit(elclass.getId()); 
        elClass = classDao.getElClassById(elclass.getId());
		return "elclass_Modify_applicationInit";
	} 
	
	public String elclass_Modify_application() throws ElException { 
		//1. 修改培训班状态
		classDao.setClassStatus(elclass.getId(), ecAudit.getStatus());
		//2. 插入备注内容
		ElclassAuditDescribes eA= classDao.getClassAudit(elclass.getId());  
		ecAudit.setClassid(elclass.getId()); 
		ecAudit.setUser(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
			if(eA == null){//如果为空。 为第一次 。 第一次为增加数据。
				classDao.setClassAudit(ecAudit);
			}else{
				ecAudit.setId(eA.getId());  
				classDao.UClassAuditContents(ecAudit);
			}  
		ecAudit= classDao.getClassAudit(elclass.getId());  
		return "elclass_Modify_application";
	}
	/**
	 *  培训班分配给全部搜索结果
	 * @return
	 * @throws ElException
	 */
	public String elclass_assignUserAll() throws ElException { 
		if (examRoom.getQueryManner() == 2) {
			erParas = erepParas;
		}
//		if(erParas!=null){
			
			elclass  = classDao.getClassById(elclass.getId());
			//当前用户
			elUser=new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
			elUser.setDepartment(new Department(department.getId()));
			if(examRoom.getQueryManner()==3){
				if(null!=classPara)
					elusers = classDao.listUserOnClassSeach(classPara, elUser);
			}else{
				if(null!=erParas)
					elusers=eroomDao.listUserOnEroomSeach(erParas,examRoom.getQueryManner(), elUser);
			}
//		}
		if(null!=elusers)
		for (ELUser user:elusers) {
			if(!classDao.checkElclassIsUsers(user.getId(),elclass.getId())){
				classDao.assign2userAdd3(user.getId(),elclass.getId(),ClassConstants.CLASS_SQFS_FP);//得先判断
			}
		}
		
		//如果是申请式的培训班，该培训班的结业课程的考场自动分配人员
		//把人员分配到该培训班中所有考场
		//1.获取该培训班中所有被绑定的考场
		//2.获取每个考场中所有的试卷
		//3.对每张试卷进行分配人员
		if(elclass.getIsApplication()==1){
			StudyQuizDao studyQuizDao=new StudyQuizDaoImpl();
			List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elclass.getId());
			List<ExamPaper> examPapers=null;
			for (int i = 0; i < eroomList.size(); i++) {
				examPapers = eroomDao.getEroomepwithusizes(eroomList.get(i).getId());//获取该考场中的所有试卷信息
				for (ELUser user:elusers) {
					if(user.getIsAssign().equals("未分配")){
						for (int j = 0; j < examPapers.size(); j++) {
//							if (!studyQuizDao.hasInQuizPaper(user.getId(), eroomList.get(i).getId(), // 检测是否已经进入考场
//									examPapers.get(j).getId(),elclass.getId())) {
//								studyQuizDao.intoQuizPaper(user.getId(), eroomList.get(i).getId(),
//										examPapers.get(j).getId(), elclass.getId());
//							}
							//判断试卷是否已被删除
							if(examPapers.get(j).getStatus()!=1){
								//检测该学员是否分配了该试卷
								if(!studyQuizDao.checkStudyExamPaper(user.getId(),
										examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId())){
									//添加该学员到 学员试卷表中
									studyQuizDao.addStudyExamPaper(user.getId(),
										examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId());
								}
							}
						}
						if (!eroomDao.checkuser2eroom(eroomList.get(i).getId(),  // 检查用户有没有分配到该考场
								user.getId(), elclass.getId())) {
							eroomDao.adduser2eroom( eroomList.get(i).getId(),
									user.getId(), 1, elclass.getId(),CourseConstants.EXAMROOM_SQFS_SQ);
						}
					}
				}
			}
		}

		
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_ADD, elclass.getName()+"(分配所有学员)",
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		return null;
	}
	/**
	 * 按培训班搜索学员
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_seachUser_class() throws ElException {
		if (classPara!=null) {
			// 当前用户
			elUser = new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
			// elUser.setRole(new
			// ElRole(getSessionIntValue(ElConstants.SESSION_ROLE)));
			elUser.setDepartment(new Department(department.getId()));
			elusers = classDao.listUserOnClassSeach(classPara, elclass.getId(),
					elUser,getPageNow(), getPageSize());
			count = classDao.listUserOnClassSeachSize(classPara, elUser);
			String jsons = "";
			if (elusers != null)
				for (int i = 0; i < elusers.size(); i++) {
					ELUser u = elusers.get(i);
					jsons += "{'id':'" + u.getId() + "','username':'"
							+ u.getUsername() + "','realname':'"
							+ u.getRealname() + "','depname':'"
							+ u.getDepartment().getName() + "','rolename':'"
							+ u.getRole().getName() + "','sex':'" + u.getSex()
							+ "','jz':'" + u.getJingzhong_() + "','age':'"
							+ u.getAGE() + "','assign':'" + u.getIsAssign()
							+ "','joinway':'" + u.getJoinway_()
							+ "','joinwayInt':'" + u.getJoinwayInt() + "'},";
				}
			if (jsons.length() > 0)
				jsons = "[" + jsons.substring(0, jsons.length() - 1) + "]";
			else
				jsons = "[]";
			printMsg("{'count':" + count + ",'users':" + jsons + "}");
		} else {
			// setElmessage("没有选择考场");
			// return "error";
			printMsg("err1");
		}
		// jingzhongs=userDao.getBaseDatatByTypeid(1);
		// zhiwus=userDao.getBaseDatatByTypeid(2);
		// zhijis=userDao.getBaseDatatByTypeid(3);
		// gangweis=userDao.getBaseDatatByTypeid(4);
		// dishis=userDao.getBaseDatatByTypeid(5);
		return null;
		// return "examroom_assignwcSearchlist";
	}
	/**
	 * 按考场搜索学员
	 * @return
	 * @throws ElException
	 */
	public String examroom_seachUser_class() throws ElException{
		if((erParas!=null && examRoom.getQueryManner() == 1)
			|| (erepParas != null && examRoom.getQueryManner() == 2)){
			//当前用户
			if (examRoom.getQueryManner() == 2) {// 判断是否是按考场试卷查询的
				erParas = erepParas;
			}
			elUser=new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
			//elUser.setRole(new ElRole(getSessionIntValue(ElConstants.SESSION_ROLE)));
			elUser.setDepartment(new Department(department.getId()));
			//elusers=eroomDao.listUserOnEroomSeach(erParas,elclass.getId(),elUser,getPageNow(),getPageSize());
			//count=eroomDao.listUserOnEroomSeachSize(erParas,elUser);@@@@@@@@@@
			elusers=eroomDao.listUserOnEroomSeach(erParas,elclass.getId(),examRoom.getQueryManner(),elUser,getPageNow(),getPageSize());
			count=eroomDao.listUserOnEroomSeachSize(erParas,examRoom.getQueryManner(),elUser);
			String jsons="";
			if(elusers!=null)
			for (int i = 0; i < elusers.size(); i++) {
				ELUser u = elusers.get(i);
				jsons+="{'id':'"+u.getId()+"','username':'"+u.getUsername()+
				"','realname':'"+u.getRealname()+"','depname':'"+u.getDepartment().getName()+
				"','rolename':'"+u.getRole().getName()+"','sex':'"+u.getSex()+"','jz':'"+u.getJingzhong_()+"','age':'"+u.getAGE()+"','assign':'"+u.getIsAssign()+"','joinway':'"+u.getJoinway_()+"','joinwayInt':'"+u.getJoinwayInt()+"'},";
			}
			if(jsons.length()>0)
				jsons= "["+jsons.substring(0,jsons.length()-1)+"]";
			else
				jsons="[]";
			printMsg("{'count':"+count+",'users':"+jsons+"}");
		}else{
			//setElmessage("没有选择考场");
			//return "error";
			printMsg("err1");
		}
		return null;
	}
	
	public String elclass_newassign2user_addAll() throws ElException { 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		}else{
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
		int depid = 1 ; 
//		if(department==null){ 
//			if(depTree.getId() == -2)
//				depid = -2; 
//			else
//				department=new Department(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
//		}else{ 
//			depid = department.getId();
//		}
		if(department==null||department.getId()<=0){
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		int cid = (elclass == null) ? 0 : elclass.getId();
		if(DBMethods == 0){//按人员信息搜索 
			elusers = classDao.listAssignedUser(9999999, 1,depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,1,department,station);//1包含下级
		}else if(DBMethods == 1){ //按培训班信息搜索
			if( elClasss == null || elClasss.size() == 0){ 
				setElmessage("没有选择培训班");
				return "error";
			}
			elClass = classDao.getClassById(elClasss.get(0).getId());
			elusers = classDao.gettoClassInfoselectUser(depTree,department,"study_class",cid, elClass.getId(), elUser,this.getStarttime(),this.getEndtime(),9999999, 1); 
		}else if(DBMethods == 2){//按考场信息搜索
			if( examRooms == null || examRooms.size()== 0 ){ 
				setElmessage("没有选择考场");
				return "error";
			}
			examRoom = eroomDao.getExamRoomByid(examRooms.get(0).getId());
			elusers = classDao.gettoEroomInfoselectUser(depTree,department,"study_class",cid,examRoom.getId(), elUser,this.getStarttime(),this.getEndtime(),9999999, 1); 
		}
		for (ELUser user:elusers) {
			//classDao.assign2userAdd(user.getId(),elclass.getId());
			if(user.getIsAssign().equals("未分配")){
				//classDao.assign2userAdd(Integer.valueOf(users[0]),elclass.getId());
				classDao.assign2userAdd3(user.getId(),elclass.getId(),ClassConstants.CLASS_SQFS_FP);//得先判断
			} 
		}
		elclass=classDao.getClassById(elclass.getId());
		//如果是申请式的培训班，该培训班的结业课程的考场自动分配人员
		//把人员分配到该培训班中所有考场
		//1.获取该培训班中所有被绑定的考场
		//2.获取每个考场中所有的试卷
		//3.对每张试卷进行分配人员
		if(elclass.getIsApplication()==1){
			StudyQuizDao studyQuizDao=new StudyQuizDaoImpl();
			List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elclass.getId());
			List<ExamPaper> examPapers=null;
			for (int i = 0; i < eroomList.size(); i++) {
				examPapers = eroomDao.getEroomepwithusizes(eroomList.get(i).getId());//获取该考场中的所有试卷信息
				for (ELUser user:elusers) {
					if(user.getIsAssign().equals("未分配")){
						for (int j = 0; j < examPapers.size(); j++) {
//							if (!studyQuizDao.hasInQuizPaper(user.getId(), eroomList.get(i).getId(), // 检测是否已经进入考场
//									examPapers.get(j).getId(),elclass.getId())) {
//								studyQuizDao.intoQuizPaper(user.getId(), eroomList.get(i).getId(),
//										examPapers.get(j).getId(), elclass.getId());
//							}
							//判断试卷是否已被删除
							if(examPapers.get(j).getStatus()!=1){
								//检测该学员是否分配了该试卷
								if(!studyQuizDao.checkStudyExamPaper(user.getId(),
										examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId())){
									//添加该学员到 学员试卷表中
									studyQuizDao.addStudyExamPaper(user.getId(),
										examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId());
								}
							}
						}
						if (!eroomDao.checkuser2eroom(eroomList.get(i).getId(),  // 检查用户有没有分配到该考场
								user.getId(), elclass.getId())) {
							eroomDao.adduser2eroom( eroomList.get(i).getId(),
									user.getId(), 1, elclass.getId(),CourseConstants.EXAMROOM_SQFS_SQ);
						}
					}
				}
			}
		}

		elclass  = classDao.getClassById(elclass.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_ADD, elclass.getName()+"(分配所有学员)",
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		return elclass_assign2userInit();
	}
	private String userids;
	
	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public String elclass_newassign2user_add() throws ElException {
		staid = this.getStaid();
		if(staid!=0){
			List<ELUser> users = stationDao.listUser(staid);
			for(int i=0;i<users.size();i++){
				if(!classDao.checkElclassIsUsers(users.get(i).getId(), elclass.getId())){
					classDao.assign2userAdd3(users.get(i).getId(),elclass.getId(),ClassConstants.CLASS_SQFS_FP);
				}
				
			}
			if(elclass.getIsApplication()==1){
				StudyQuizDao studyQuizDao=new StudyQuizDaoImpl();
				List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elclass.getId());
				List<ExamPaper> examPapers=null;
				for (int i = 0; i < eroomList.size(); i++) {
					examPapers = eroomDao.getEroomepwithusizes(eroomList.get(i).getId());//获取该考场中的所有试卷信息
					for (int k = 0; k < users.size(); k++) {
							for (int j = 0; j < examPapers.size(); j++) {
								//判断试卷是否已被删除
								if(examPapers.get(j).getStatus()!=1){
									//检测该学员是否分配了该试卷
									if(!studyQuizDao.checkStudyExamPaper(users.get(k).getId(),
											examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId())){
										//添加该学员到 学员试卷表中
										studyQuizDao.addStudyExamPaper(users.get(k).getId(),
												examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId());
									}
								}
							
							if (!eroomDao.checkuser2eroom(eroomList.get(i).getId(),  // 检查用户有没有分配到该考场
									users.get(k).getId(), elclass.getId())) {
								eroomDao.adduser2eroom( eroomList.get(i).getId(),
										users.get(k).getId(), 1, elclass.getId(),CourseConstants.EXAMROOM_SQFS_SQ);
							}
						}
					}
				}
				
			}
			
			elclass  = classDao.getClassById(elclass.getId());
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD, elclass.getName()+"(分配学员)",
					ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		//	return "elclass_sh";
			return "sta_elclass_view_man";
		}else{
			String user[] = this.getUserids().split(",");
			for (int i = 0; i < user.length; i++) {
				String users[] =user[i].split(":");
				if(users[1].equals("未分配")){
					//classDao.assign2userAdd(Integer.valueOf(users[0]),elclass.getId());
					classDao.assign2userAdd3(Integer.valueOf(users[0]),elclass.getId(),ClassConstants.CLASS_SQFS_FP);
				}
			}
			//如果是申请式的培训班，该培训班的结业课程的考场自动分配人员
			//把人员分配到该培训班中所有考场
			//1.获取该培训班中所有被绑定的考场
			//2.获取每个考场中所有的试卷
			//3.对每张试卷进行分配人员
			if(elclass.getIsApplication()==1){
				StudyQuizDao studyQuizDao=new StudyQuizDaoImpl();
				List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elclass.getId());
				List<ExamPaper> examPapers=null;
				for (int i = 0; i < eroomList.size(); i++) {
					examPapers = eroomDao.getEroomepwithusizes(eroomList.get(i).getId());//获取该考场中的所有试卷信息
					for (int k = 0; k < user.length; k++) {
						String users[] =user[k].split(":");
						if(users[1].equals("未分配")){
							//classDao.assign2userAdd3(Integer.valueOf(users[0]),elclass.getId(),ClassConstants.CLASS_SQFS_FP);//这里分配了 上面的可以注掉了
						
							for (int j = 0; j < examPapers.size(); j++) {
//								if (!studyQuizDao.hasInQuizPaper(Integer.valueOf(users[0]), eroomList.get(i).getId(), // 检测是否已经进入考场
//										examPapers.get(j).getId(),elclass.getId())) {
//									studyQuizDao.intoQuizPaper(Integer.valueOf(users[0]), eroomList.get(i).getId(),
//											examPapers.get(j).getId(), elclass.getId());
//								}
								//判断试卷是否已被删除
								if(examPapers.get(j).getStatus()!=1){
									//检测该学员是否分配了该试卷
									if(!studyQuizDao.checkStudyExamPaper(Integer.valueOf(users[0]),
											examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId())){
										//添加该学员到 学员试卷表中
										studyQuizDao.addStudyExamPaper(Integer.valueOf(users[0]),
												examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId());
									}
								}
							}
							if (!eroomDao.checkuser2eroom(eroomList.get(i).getId(),  // 检查用户有没有分配到该考场
									Integer.valueOf(users[0]), elclass.getId())) {
								eroomDao.adduser2eroom( eroomList.get(i).getId(),
										Integer.valueOf(users[0]), 1, elclass.getId(),CourseConstants.EXAMROOM_SQFS_SQ);
							}
						}
					}
				}
			}
		}
		

		elclass  = classDao.getClassById(elclass.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_ADD, elclass.getName()+"(分配学员)",
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		if(ajax==1){
			return null;
		}
		return elclass_assign2userInit();
	}
//	public String elclass_newassign2user_add2() throws ElException {
//		String user[] = this.getUserids().split(",");
//		for (int i = 0; i < user.length; i++) {
//			String users[] =user[i].split(":");
//			if(users[1].equals("未分配")){
//				//classDao.assign2userAdd2(Integer.valueOf(users[0]),elclass.getId());
//				classDao.assign2userAdd3(Integer.valueOf(users[0]),elclass.getId(),ClassConstants.CLASS_SQFS_FP); 
//			}
//		}
//		return elclass_assign2userInit2();
//	}
	/**
	 * 培训班移除学员
	 */
	public String elclass_newassign2user_delete() throws ElException {
		String user[] = this.getUserids().split(",");
		for (int i = 0; i < user.length; i++) {
			String users[] =user[i].split(":");
			if(users[1].equals("已分配")){
			   //删除培训班学员以及学员的课程学习信息
			   classDao.assign2userDelete(Integer.valueOf(users[0]),elclass.getId());
			   //删除培训班中所有的绑定的考场里面的学员
			   List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elclass.getId());
			   List<ExamPaper> examPapers=null;
			   for (int j = 0; j < eroomList.size(); j++) {
				   examPapers = eroomDao.getEroomepwithusizes(eroomList.get(j).getId());//获取该考场中的所有试卷信息
				   for (int k = 0; k < examPapers.size(); k++) {
					   //删除答卷，以免数据亢余
					   ((StudyQuizDao)SpringContextUtil.getBean("studyQuizDao")).deleteQuiz(Integer.valueOf(users[0]),
							   eroomList.get(j).getId(), examPapers.get(k).getId());
				   }
			   }
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
		return elclass_assign2userInit();
	}

	/**
	 * 班级分配到用户添加
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_assign2user_add() throws ElException {
		if (null != canAssignUsers)
			for (int i = 0; i < canAssignUsers.size(); i++) {
				classDao.assign2userAdd(canAssignUsers.get(i).getId(),
						elclass.getId());
			}
		canAssignDep = classDao.listCanAssignDep(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), elclass
						.getId());
		assignedDep = classDao.listAssignedDep(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), elclass
						.getId());
		return "elclass_assign2user";
	}

	/**
	 * 班级分配到用户删除
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_assign2user_delete() throws ElException {
		if (null != assignedUsers)
			for (int i = 0; i < assignedUsers.size(); i++) {
				classDao.assign2userDelete(assignedUsers.get(i).getId(),
						elclass.getId());
			}
		canAssignDep = classDao.listCanAssignDep(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), elclass
						.getId());
		assignedDep = classDao.listAssignedDep(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), elclass
						.getId());
		
//		canAssignUsers = classDao.listCanAssignUsers(elclass.getId(),
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
//		assignedUsers = classDao.listAssignedUsers(elclass.getId(),
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
		return "elclass_assign2user";
	}

	/**
	 * 可分配培训班的部门
	 * 
	 * @return
	 * @throws ElException
	 */
	private Department depTree;
	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public String elclass_assign2depInit() throws ElException { 
		canAssignDeps = classDao.listCanAssignDeps(elclass.getId(),
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
//		depTree = departmentDao.getDepTree(1, ElConstants.TREE_FIANL, true);

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree(1, -1, true);
		else {
			depTree = departmentDao.getDepTree( getSessionIntValue(ElConstants.SESSION_USERID), "op", -1, true);
		}
		assignedDeps = classDao.listAssignedDeps(elclass.getId(),
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
		return "elclass_assign2dep";
	}

	/**
	 * 分配培训班到部门
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_assign2dep_add() throws ElException {
		if (elclass != null) {
			classDao.unassignDepsAll(elclass.getId());
		}
		if (null != canAssignDeps) {
			for (int i = 0; i < canAssignDeps.size(); i++) {
				classDao.assign2depAdd(canAssignDeps.get(i).getId(),
						elclass.getId());
			}
		}
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
//				ElConstants.TREE_FIANL, true);
//		assignedDeps = classDao.listAssignedDeps(elclass.getId(),
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
		return "elclass_assign2dep";
	}

	/**
	 * 删除部门培训班分配
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_assign2dep_delete() throws ElException {
		if (null != assignedDeps) {
			for (int i = 0; i < assignedDeps.size(); i++) {
				classDao.assign2depDelete(assignedDeps.get(i).getId(),
						elclass.getId());
			}
		}
//		canAssignDeps = classDao.listCanAssignDeps(elclass.getId(),
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
//		assignedDeps = classDao.listAssignedDeps(elclass.getId(),
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
		return "elclass_assign2dep";
	}

	/**
	 * 可申请培训班列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String listCanApplyClass() throws ElException {
		elclasses = classDao.listCanApplyClassFromThis(elclass.getName(),
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		return "listCanApplyClass";
	}

	/**
	 * 学员申请培训班
	 * 
	 * @return
	 * @throws ElException
	 */
	public String applyClass() throws ElException {
		classDao.applyClass(elclass.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID));
		elclasses = classDao.listCanApplyClassFromThis(elclass.getName(),
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		return "listCanApplyClass";
	}

	public String submitAppalyClass_front() throws ElException {
		classDao.applyClass(elclass.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID));
		elclasses = classDao.listCanApplyClassFromThis(elclass.getName(),
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		return "submitAppalyClass_front";
	}

	/**
	 * 班级审批列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_selectInit() throws ElException {
		elclasses = classDao.listApplyedClass(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), getPageNow(), getPageSize());
		return "elclass_select";
	}

	/**
	 * 审核培训班
	 * 
	 * @return
	 * @throws ElException
	 */
	public String applyedClass_op() throws ElException {
		if (null != elclass && null != elclass.getStudent()) {
			if (ClassConstants.CLASS_APPLY_STATUS_YES == status)
				classDao.setClassApplyStatus(elclass.getId(), elclass
						.getStudent().getId(),
						ClassConstants.CLASS_APPLY_STATUS_YES);
			else {
//当培训班审核不通过时，直接删除申请记录，简单处理  modify by lcw 				
//				classDao.setClassApplyStatus(elclass.getId(), elclass
//						.getStudent().getId(),
//						ClassConstants.CLASS_APPLY_STATUS_NO);
				classDao.setClassApplyStatusNo(elclass.getId(), elclass.getStudent().getId());
			}
		}
		elclasses = classDao.listApplyedClass(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), getPageNow(), getPageSize());
		return "elclass_select";
	}

	/**
	 * 学员在学班级列表
	 * 
	 * @return
	 * @throws ElException
	 */
	// public String myelclass_list() throws ElException {
	// elclasses = classDao.listMyStudyClass(
	// getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
	// return "myelclass_list";
	// }

	/**
	 * 申请结业列表
	 * 
	 * @return
	 * @throws ElException
	 */
	/*
	 * public String graduate_applyInit() throws ElException {
	 * 
	 * 
	 * return "graduate_apply"; }
	 */
	/**
	 * 培训班删除申请初始化
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_delete_applyInit() throws ElException {
		/*elclass = classDao
				.getClassById(
						getSessionIntValue(ElConstants.SESSION_USERID), elclass
								.getId());*/
		elclass=classDao.getClassById(elclass.getId());
		return "elclass_delete_apply";
	}

	/**
	 * 培训班删除申请成功
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_delete_apply() throws ElException {

		/*classDao.setClassStatus(elclass.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID),
				ClassConstants.CLASS_STATUS_DELETE_WAIT);*/
		classDao.setClassStatus(elclass.getId(),
				ClassConstants.CLASS_STATUS_DELETE_WAIT);
		elclass=classDao.getClassById(elclass.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_APPDELETE,elclass.getName(),
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		return "elclass_delete_apply_success";
	}

	/**
	 * 培训班删除申请列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_delete_apply_list() throws ElException {
//		int typeid = 1;
//		if(sublibs != 0){
//			typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		}
//		if(typeid == 0){
//			typeid = 1;
//		}
//		String name = elClass == null ? "" : elClass.getName();
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		} 
//		elclasses = classDao.getClassesList(cltypeTree, getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE),"8", getPageNow(), getPageSize());
//		count = classDao.getClassesSize(cltypeTree, getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), typeid, elClass,"0,1,2,3,4,5,6,7,8",getSessionIntValue(ElConstants.SESSION_ROLE));
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = elClass == null ? 1 : sublibs;
		elclasses = classDao.getClassList(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8","8", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		return "elclass_delete_apply_list";
	}

	/**
	 * 培训班删除申请处理
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_delete_apply_op() throws ElException {
		if(status == 2){
			//classDao.setClassStatus(elclass.getId(), getSessionIntValue(ElConstants.SESSION_USERID), status);
			classDao.setClassStatus(elclass.getId(), status);
		}else{
			classDao.deleteClass(elclass.getId());
		}
		elclass=classDao.getClassById(elclass.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_DELETE,elclass.getName(),
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		return elclass_delete_apply_list();
	}
	
	/**
	 * 培训班审核删除处理
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_delete_sh() throws ElException {
		classDao.deleteClass(elclass.getId());
		elclass=classDao.getClassById(elclass.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_DELETE,elclass.getName(),
				ElLoggerConstants.LOG_RES_SUCC,elclass.getId());
		return elclass_sh_list();
	}
	
	public String elclass_delete_sh_NOInit() throws ElException {
		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		int cid = (elclass == null) ? 0 : elclass.getId(); 
		elusers = classDao.listAssignedUserIsAssign(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		count =classDao.listAssignedUserIsAssignSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser); 
		ecAudit= classDao.getClassAudit(elclass.getId());  
		return "elclass_delete_sh_NOInit";
	}
	
	public String elclass_delete_sh_Init() throws ElException {
		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		stTree = stationDao.getStTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
				true);
		int cid = (elclass == null) ? 0 : elclass.getId();
		elusers = classDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,1,depTree,stTree);//1包含下级
		count =classDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser,1,depTree,stTree); //1包含下级
		ecAudit= classDao.getClassAudit(elclass.getId());  
		return "elclass_delete_sh_Init";
	}
	
	public String elclass_delete_sh_NO() throws ElException {
		//1. 修改培训班状态 　 
		classDao.shClass(elclass.getId(), ecAudit.getStatus()); 
		courses = courseDao.getClassByCourseid(elclass.getId());
		if(courses != null && ecAudit.getStatus() == 1){//开通培训班是 便开通培训班下所有课程
			for(int i = 0 ;i < courses.size() ; i++){ 
				courseDao.shCourse(courses.get(i).getId(), 1);//开通课程状态为1
			}
		}
		//2. 插入备注内容
		ElclassAuditDescribes eA= classDao.getClassAudit(elclass.getId());  
		ecAudit.setClassid(elclass.getId()); 
		ecAudit.setUser(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
			if(eA == null){//如果为空。 为第一次 。 第一次为增加数据。
				classDao.setClassAudit(ecAudit);
			}else{
				ecAudit.setId(eA.getId()); 
				ecAudit.setContent(eA.getContent());//把原来的还给更新的
				classDao.UClassAuditContents(ecAudit);
			}  
		ecAudit= classDao.getClassAudit(elclass.getId());  
		return "elclass_delete_sh_NO";
	}
	public String elclass_graduate_apply_list() throws ElException {
		myClasses = classDao.listGraduateClass(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		count = classDao
				.listGraduateClassSize(getSessionIntValue(ElConstants.SESSION_USERID));

		return "elclass_graduate_apply_list";
	}

	public String elclass_graduate_apply_op() throws ElException {
		// myClasses =
		// classDao.listGraduateClass(getSessionIntValue(ElConstants.SESSION_USERID),
		// getPageNow(), getPageSize());
		// count =
		// classDao.listGraduateClassSize(getSessionIntValue(ElConstants.SESSION_USERID));
		classDao.graduateClassApplay(myClasse.getUser().getId(), myClasse
				.getElClass().getId(), status);
		return "elclass_graduate_apply_op";
	}
	
	/**
	 * 培训班统计列表
	 * @return
	 * @throws ElException
	 */
	public String class_stat_list() throws ElException {
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		int typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
		String name = elClass == null ? "" : elClass.getName();
		
		elclasses = classDao.getStatClassesList(depid, typeid, name, getPageNow(), getPageSize());
		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		count = classDao.getStatClassesSize(depid, typeid, name);
		return "class_stat_list";
	}
	
	/**
	 * 培训班统计
	 * @return
	 * @throws ElException
	 */
	public String class_student() throws ElException {
//		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
//		int id = dep != null ?new dep.setId(depid): 1;
		//depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		//depTree = departmentDao.getDepTree_level1(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		}else{
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		int cid = (elclass == null) ? 0 : elclass.getId();
//		elusers = classDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
//		count =classDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		elClass = classDao.getElClassById(cid);  
		
//		int id = dep != null ? dep.getId():depid;
//		dep = new Department();
//		dep.setId(depid);
		
		//elusers = classDao.classStudent(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		//count =classDao.classStudentSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		if(deptid==null||deptid<=0){
			dep=depTree;
		}else{
			dep=departmentDao.getDepById(deptid);
		}
		if(isExport()){ 
			//elusers = classDao.classStudent(id,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
			elusers = classDao.classStudent(cid,dep ,elUser);
			return "class_student_EXCEL";
		}
		elusers = classDao.classStudent(cid,dep,elUser,getPageNow(), getPageSize());
		count =classDao.classStudentSize(cid,dep,elUser);
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		return "class_student";
	}
	/**
	 * 培训班组合搜索初始化
	 */
	public String combinationSearchclassInit()throws ElException {
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "OP",ElConstants.TREE_FIANL, true);
		}
		return "combinationSearchclassInit";
	}
	/**
	 * 培训班组合搜索
	 */
	public String combinationSearchClass() throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "OP",ElConstants.TREE_FIANL, true);
		}
//		if(elClass == null){
//			elClass = new ElClass();  
//			elClass.setCltype(new ElClType(1));
//			elClass.setOwner(new ELUser(0,""));
//			elClass.setStatus(-1);
//		}
//		elclasses=classDao.listcombinationSearchClass(elClass, cltypeTree,"9", getPageNow(), getPageSize());
//		count=classDao.listcombinationSearchClassCount(elClass, cltypeTree, getPageNow(), getPageSize());
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		sublibs = 1;
		elclasses = classDao.getClassList(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8,9","9", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8,9");
		return "combinationSearchclass";
	}
	
	public String elclass_details() throws ElException { 
		if (StringUtils.isNotBlank(elclassId)) { 
			elclass=classDao.getClassById(Integer.valueOf(elclassId));
		} 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
//		int typeid = cltype == null ? cltypeTree.getId(): cltype.getId();
		 
		group1 = roleDao.listGroupsBytype(1);
		group2 = roleDao.listGroupsBytype(2);
		return "elclass_details";
	}
	
	public String elclass_details_sh() throws ElException { 
		if (StringUtils.isNotBlank(elclassId)) { 
			elclass=classDao.getClassById(Integer.valueOf(elclassId));
		} 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
//		int typeid = cltype == null ? cltypeTree.getId(): cltype.getId();
		 
		elRegistration = classDao.getClassRegistration(Integer.valueOf(elclassId));
		group1 = roleDao.listGroupsBytype(1);
		group2 = roleDao.listGroupsBytype(2);
//		PageStatus = PageStatus;
		return "elclass_details_sh";
	}
	/**
	 * 培训班导入人员初始化
	 * @return
	 * @throws ElException
	 */
	public String classWriteUserInit() throws ElException{
		return "importUserToClass";
	}
	
	/**
	 * 培训班导入人员
	 * @return
	 * @throws ElException
	 */
	public String classWriteUser() throws ElException{
		if (null != st) {
			if (!J2EEFileUtil.getExtention(stFileName).toLowerCase().equals(
					"xls")) {
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return "importUserToClass";
			}
			if (st.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "importUserToClass";
			} else {
				String msg=UserExcelUtil.writeUserToClass(st,elClass.getId());
				setElmessage(msg);
				return "importUserToClass";
			}
		} else {
			setElmessage("请输入上传文件");
			return "importUserToClass";
		}
	}
	
	/**
	 * 可申请培训班审核列表页
	 */
	public String elclass_userAudit() throws ElException {
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
		sublibs = elClass == null ? 1 : sublibs;
		if(elClass==null){
			elClass=new ElClass();
			elClass.setStatus(-1);
		}
		elClass.setElRegistration(new ELClassRegistration());
		elClass.getElRegistration().setIsAudit(1);//为了查询出可申请且需要审核的培训班
		elclasses = classDao.getClassList(cltype,elClass,sublibs,"5","-1", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,sublibs,"5");
		return "elclass_userAudit";
	}
	/**
	 * 查看培训班人员详情(可申请培训班人员审核时有调用)
	 * @return
	 * @throws ElException
	 */
	public String elclass_auditUserlist() throws ElException {
		elClass = classDao.getClassById(elClass.getId());
		int status =elUser==null?-1:elUser.getActive(); //状态
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
			sublibs=1;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		
		myClasses = classDao.getClassNoAuditUser(elClass.getId(), getPageNow(),getPageSize(),elUser,department,status);
		count = classDao.getClassNoAuditUserSize(elClass.getId(),elUser,department,status);
		return "elclass_auditUserlist";
	}
	/**
	 * 更新学员培训班报名状态
	 * @return
	 * @throws ElException
	 */
	public String updateStudyClassStatus() throws ElException {
		if(elUser==null||elUser.getId()<=0||elClass==null||elClass.getId()<=0){
			setElmessage("参数有误!");
			return "error";
		}
		if(elClass.getStatus()==3){//通过
			//判断是否已经报满了
			int planNumber=classDao.getElclassPlanNumber(elClass.getId());
			int eroomNumber=classDao.getClassUserSize(elClass.getId());
			if(eroomNumber>=planNumber){
				setElmessage("培训班人数已满！");
				return "error";
			}
			if(!classDao.checkElclassIsUsers(elUser.getId(), elClass.getId())){
				classDao.assign2userAdd3(elUser.getId(),elClass.getId(),ClassConstants.CLASS_SQFS_SQ);
			}
			//把人员分配到该培训班中所有考场
			//1.获取该培训班中所有被绑定的考场
			//2.获取每个考场中所有的试卷
			//3.对每张试卷进行分配人员
			List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elClass.getId());
			List<ExamPaper> examPapers=null;
			for (int i = 0; i < eroomList.size(); i++) {
				examPapers = eroomDao.getEroomepwithusizes(eroomList.get(i).getId());//获取该考场中的所有试卷信息
				for (int j = 0; j < examPapers.size(); j++) {
					//判断试卷是否已被删除
					if(examPapers.get(j).getStatus()!=1){
						//检测该学员是否分配了该试卷
						if(!((StudyQuizDao)SpringContextUtil.getBean("studyQuizDao")).checkStudyExamPaper(elUser.getId(),
								examPapers.get(j).getId(),eroomList.get(i).getId(),elClass.getId())){
							//添加该学员到 学员试卷表中
							((StudyQuizDao)SpringContextUtil.getBean("studyQuizDao")).addStudyExamPaper(elUser.getId(),
								examPapers.get(j).getId(),eroomList.get(i).getId(),elClass.getId());
						}
					}
				}
				if (!eroomDao.checkuser2eroom(eroomList.get(i).getId(),  // 检查用户有没有分配到该考场
						elUser.getId(), elClass.getId())) {
					eroomDao.adduser2eroom( eroomList.get(i).getId(),
							elUser.getId(), 1, elClass.getId(),CourseConstants.EXAMROOM_SQFS_SQ);
				}
			}
		}
		((StudyClassDao)SpringContextUtil.getBean("studyClassDao")).udpateStudyClassApplyStatus(elClass.getId(),elUser.getId(), elClass.getStatus());
		return "elclass_auditUserlist";
	}
	/**
	 * （批量）更新学员培训班报名状态
	 * @return
	 * @throws ElException
	 */
	public String updateStudysClassStatus() throws ElException {
		if(elClass==null||elClass.getId()<=0){
			setElmessage("参数有误!");
			return "error";
		}
		String[] checkbox=getRequest().getParameterValues("elusers.id");
		if(checkbox==null){
			setElmessage("没有选择用户！");
			return "error";
		}
		for (int i = 0; i < checkbox.length; i++) {
			if(elClass.getStatus()==3){//通过
				//判断是否已经报满了
				int planNumber=classDao.getElclassPlanNumber(elClass.getId());
				int eroomNumber=classDao.getClassUserSize(elClass.getId());
				if(eroomNumber>=planNumber){
					setElmessage("刚刚报进去"+i+"人，培训班人数已满！");
					return "error";
				}
				if(!classDao.checkElclassIsUsers(Integer.parseInt(checkbox[i]), elClass.getId())){
					classDao.assign2userAdd3(Integer.parseInt(checkbox[i]),elClass.getId(),ClassConstants.CLASS_SQFS_SQ);
				}
				//把人员分配到该培训班中所有考场
				//1.获取该培训班中所有被绑定的考场
				//2.获取每个考场中所有的试卷
				//3.对每张试卷进行分配人员
				List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elClass.getId());
				List<ExamPaper> examPapers=null;
				for (int k = 0; k < eroomList.size(); k++) {
					examPapers = eroomDao.getEroomepwithusizes(eroomList.get(k).getId());//获取该考场中的所有试卷信息
					for (int j = 0; j < examPapers.size(); j++) {
						//判断试卷是否已被删除
						if(examPapers.get(j).getStatus()!=1){
							//检测该学员是否分配了该试卷
							if(!((StudyQuizDao)SpringContextUtil.getBean("studyQuizDao")).checkStudyExamPaper(Integer.parseInt(checkbox[i]),
									examPapers.get(j).getId(),eroomList.get(k).getId(),elClass.getId())){
								//添加该学员到 学员试卷表中
								((StudyQuizDao)SpringContextUtil.getBean("studyQuizDao")).addStudyExamPaper(Integer.parseInt(checkbox[i]),
									examPapers.get(j).getId(),eroomList.get(k).getId(),elClass.getId());
							}
						}
					}
					if (!eroomDao.checkuser2eroom(eroomList.get(k).getId(),  // 检查用户有没有分配到该考场
							Integer.parseInt(checkbox[i]), elClass.getId())) {
						eroomDao.adduser2eroom( eroomList.get(k).getId(),
								Integer.parseInt(checkbox[i]), 1, elClass.getId(),CourseConstants.EXAMROOM_SQFS_SQ);
					}
				}
			}
			((StudyClassDao)SpringContextUtil.getBean("studyClassDao")).udpateStudyClassApplyStatus(elClass.getId(),Integer.parseInt(checkbox[i]), elClass.getStatus());
		}
		return "elclass_auditUserlist";
	}
	/**
	 * 删除学员培训班报名记录
	 * @return
	 * @throws ElException
	 */
	public String deleteStudyClassApplyStatus() throws ElException {
		 classDao.deleteStudyClassApply(elClass.getId(), elUser.getId());
		 //删除培训班学员以及学员的课程学习信息
	     classDao.assign2userDelete(elUser.getId(),elClass.getId());
	     //删除培训班中所有的绑定的考场里面的学员
	     List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elClass.getId());
	     List<ExamPaper> examPapers=null;
	     for (int j = 0; j < eroomList.size(); j++) {
		     examPapers = eroomDao.getEroomepwithusizes(eroomList.get(j).getId());//获取该考场中的所有试卷信息
	  	   for (int k = 0; k < examPapers.size(); k++) {
	  		   //删除答卷，以免数据亢余
			   ((StudyQuizDao)SpringContextUtil.getBean("studyQuizDao")).deleteQuiz(elUser.getId(),
					   eroomList.get(j).getId(), examPapers.get(k).getId());
		    }
	    }
		return "elclass_auditUserlist";
	}
	/**
	 * 删除学员培训班报名记录(批量)
	 * @return
	 * @throws ElException
	 */
	public String deleteStudysClassApplyStatus() throws ElException {
		if(elClass==null||elClass.getId()<=0){
			setElmessage("参数有误!");
			return "error";
		}
		String[] checkbox=getRequest().getParameterValues("elusers.id");
		if(checkbox==null){
			setElmessage("没有选择用户！");
			return "error";
		}
		for (int i = 0; i < checkbox.length; i++) {
			 classDao.deleteStudyClassApply(elClass.getId(), Integer.parseInt(checkbox[i]));
			 //删除培训班学员以及学员的课程学习信息
		     classDao.assign2userDelete(Integer.parseInt(checkbox[i]),elClass.getId());
		     //删除培训班中所有的绑定的考场里面的学员
		     List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elClass.getId());
		     List<ExamPaper> examPapers=null;
		     for (int j = 0; j < eroomList.size(); j++) {
			   examPapers = eroomDao.getEroomepwithusizes(eroomList.get(j).getId());//获取该考场中的所有试卷信息
		  	   for (int k = 0; k < examPapers.size(); k++) {
		  		   //删除答卷，以免数据亢余
				   ((StudyQuizDao)SpringContextUtil.getBean("studyQuizDao")).deleteQuiz(Integer.parseInt(checkbox[i]),
						   eroomList.get(j).getId(), examPapers.get(k).getId());
			    }
		     }
		}
		return "elclass_auditUserlist";
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

	public List<ELUser> getCanAssignUsers() {
		return canAssignUsers;
	}

	public void setCanAssignUsers(List<ELUser> canAssignUsers) {
		this.canAssignUsers = canAssignUsers;
	}

	public List<Department> getCanAssignDeps() {
		return canAssignDeps;
	}

	public void setCanAssignDeps(List<Department> canAssignDeps) {
		this.canAssignDeps = canAssignDeps;
	}

	public List<ELUser> getAssignedUsers() {
		return assignedUsers;
	}

	public void setAssignedUsers(List<ELUser> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	public List<Department> getAssignedDeps() {
		return assignedDeps;
	}

	public void setAssignedDeps(List<Department> assignedDeps) {
		this.assignedDeps = assignedDeps;
	}

	public List<Course> getZxCourses() {
		return zxCourses;
	}

	public void setZxCourses(List<Course> zxCourses) {
		this.zxCourses = zxCourses;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<MyClass> getMyClasses() {
		return myClasses;
	}

	public void setMyClasses(List<MyClass> myClasses) {
		this.myClasses = myClasses;
	}

	public int getStatus() {
		return status;
	}

	public MyClass getMyClasse() {
		return myClasse;
	}

	public void setMyClasse(MyClass myClasse) {
		this.myClasse = myClasse;
	}

	public ElClass getElClass() {
		return elClass;
	}

	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
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

	public String getElclassId() {
		return elclassId;
	}

	public void setElclassId(String elclassId) {
		this.elclassId = elclassId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public String getSuggestcredit() {
		return suggestcredit;
	}

	public void setSuggestcredit(String suggestcredit) {
		this.suggestcredit = suggestcredit;
	}

	public String getSetcredit() {
		return setcredit;
	}

	public void setSetcredit(String setcredit) {
		this.setcredit = setcredit;
	}

	public String getGetcredit() {
		return getcredit;
	}

	public void setGetcredit(String getcredit) {
		this.getcredit = getcredit;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public ElclassAuditDescribes getEcAudit() {
		return ecAudit;
	}

	public void setEcAudit(ElclassAuditDescribes ecAudit) {
		this.ecAudit = ecAudit;
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

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}
	public ExamRoomAuditDescribes getErAuditdes() {
		return erAuditdes;
	}

	public void setErAuditdes(ExamRoomAuditDescribes erAuditdes) {
		this.erAuditdes = erAuditdes;
	}
	//培训班课程考场审核
	public String examroom_class_shInit() throws ElException { 
		if(eroomDao.checkuserClassBindingCourse(Integer.valueOf(elclassId), Integer.valueOf(courseId))){
			examRoom = eroomDao.getClassBindingCourseByRoomId(Integer.valueOf(elclassId), Integer.valueOf(courseId));
			if(examRoom != null){ 
				examRoom = eroomDao.getExamRoomByid(examRoom.getId());
				examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
						examRoom.getId()));
				examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
						.getId()));
				examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
				examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
				myrooms = eroomDao.listEroomtesters(examRoom.getId(), getPageNow(),
						getPageSize());
				count = eroomDao.listEroomtesterssize(examRoom.getId());

				erAuditdes = eroomDao.getExamRoomAuditDescribesByRoomid(examRoom.getId()); 
			}else{
				setElmessage("没有该考场");
				return "error";
			}
		}else{ 
			setElmessage("未找到绑定的考场！请确认该课程是否绑定考场！");
			return "error";
		}
		return "examroom_class_shInit";
	}
	
	public String elclass_record_rankinglist() throws ElException {   
		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);    
		depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true); 
		elClass = classDao.getElClassById(SystemConfOp.getIntValue((ElConstants.SYSTEM_CONF_INDEX_CLASSID)));  
		//初始化改培训班的积分记录
		List<ELUser> uprs = new ArrayList<ELUser>();
		users = classDao.getClassUser(elClass.getId());//
		uprs = classDao.getPoints_RecordUsers(elClass.getId());//
//		if(users.size() != uprs.size()){//当数据内容相同是证明以初始化过。 不需要再初始化。
//			elclass_recordInit(elclass.getId()) ;
//		}
		elusers = classDao.getElclassRecordRankinglist(depid, elClass.getId(), this.getStarttime(),this.getEndtime(), elUser,getPageNow(), getPageSize()); 
		count =classDao.getElclassRecordRankingSize(depid, elClass.getId(), this.getStarttime(),this.getEndtime(), elUser); 
		return "elclass_record_rankinglist";
	}

	public String elclass_UnitRanking_PointsInit() throws ElException {  
		if(isAlter == 1){
			float AddCent = unitRank.getAddCent();
			unitRank = studyQuizDao.getUnitRank(unitRank.getElclass().getId(), unitRank.getUnit().getId());
			unitRank.setAddCent(AddCent);
			unitRank.setFinalScore(AddCent + unitRank.getTotalScore());
			studyQuizDao.UpdateUnitRank(unitRank);
			elclass = unitRank.getElclass();
		}
		unitRanks = studyQuizDao.getUnitRanks(SystemConfOp.getIntValue((ElConstants.SYSTEM_CONF_INDEX_CLASSID))); 
	
		//将修改为新的单位积分算法
		
		depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		
		if(unitRanks!=null){
			elClass = classDao.getElClassById(unitRanks.get(0).getElclass().getId());
			for (int i = 0; i < depTree.getChild().size(); i++) { 
				Department dep = new Department();
				for (UnitRanking u : unitRanks) {
					if(depTree.getChild().get(i).getId()==u.getUnit().getId()){
						
							
							
						dep.setUserCount(classDao.getClassEval_CountNumberOfPeople(depTree.getChild().get(i), elClass));//总人数
						dep.setUserCount_(classDao.getClassEval_Pass_CountNumberOfPeople(depTree.getChild().get(i), elClass));//通过人数 
						dep.setUserGaojiCount(classDao.getClassEval_CountNumberOfGaojiPeople(depTree.getChild().get(i), elClass));//高级职称人数
						dep.setUserGaojiPassCount(classDao.getClassEval_Pass_CountNumberOfGaojiPeople(depTree.getChild().get(i), elClass));//高级职称的通过人数
						u.setPassing(dep.getUserCount() == 0 ? 0 :getDou2(dep.getUserCount_()-dep.getUserGaojiPassCount(), dep.getUserCount()-dep.getUserGaojiCount()));//通过率
						String xiaji = departmentDao.getByIdXiaJi(u.getUnit().getId());//获取单位下级id串 
						xiaji = xiaji.length() > 0 ? xiaji + ","+u.getUnit().getId() : u.getUnit().getId()+"";
						UnitRanking unitRank1 = studyQuizDao.getDegreeScoreDetails(elClass.getId(), xiaji);
						u.setDegreeScore(unitRank1.getScore_Xl_TOTAL()/dep.getUserCount());
						UnitRanking unitRank2 = studyQuizDao.getTitleScoreDetails(elClass.getId(), xiaji);
						u.setTitleScore(unitRank2.getScore_Zc_TOTAL()/dep.getUserCount());
						
						u.setTotalScore(u.getBasedScore()+u.getDegreeScore()+u.getTitleScore());
						u.setFinalScore(u.getBasedScore()+u.getDegreeScore()+u.getTitleScore()+u.getAddCent());
						continue;
					}
				}
				
			}
		}
		return "elclass_UnitRanking_Points"; 
	}

	public Double getDou2(int i ,int j)throws ElException { 
		float result =(float)i/j;  
		java.text.DecimalFormat format = (java.text.DecimalFormat)java.text.DecimalFormat.getInstance();  
		format.applyPattern("##.###");  
		String fr=Float.parseFloat(format.format(result))*100+"";  
		if(fr.length()>4){  
		    fr=fr.substring(0,4);  
		}   
		return Double.parseDouble(fr);
	}
	public String class_quiz_stat_eval() throws ElException {  
		if(Ration){ //修改通过率
			classDao.alterElclassDepPassing(department.getId(), elclass.getId(), department.getRatioPassing_()); 
		}
		depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		elClass = classDao.getElClassById(elclass.getId());     
		if(depTree != null){ //部门比较  departments1来自页面查看按钮的部门    开放页面 查看 按钮 需要把下面的depTree 换成departments1即可
			departments=new ArrayList<Department>();
			for (int i = 0; i < depTree.getChild().size(); i++) { 
				Department dep = new Department();
				dep.setId(depTree.getChild().get(i).getId());
				dep.setUserCount(classDao.getClassEval_CountNumberOfPeople(depTree.getChild().get(i), elclass));//总人数
				dep.setUserCount_(classDao.getClassEval_Pass_CountNumberOfPeople(depTree.getChild().get(i), elclass));//通过人数 
				dep.setUserGaojiCount(classDao.getClassEval_CountNumberOfGaojiPeople(depTree.getChild().get(i), elclass));//高级职称人数
				dep.setUserGaojiPassCount(classDao.getClassEval_Pass_CountNumberOfGaojiPeople(depTree.getChild().get(i), elclass));//高级职称的通过人数
				dep.setRatioPassing(dep.getUserCount() == 0 ? 0 :getDou2(dep.getUserCount_()-dep.getUserGaojiPassCount(), dep.getUserCount()-dep.getUserGaojiCount()));//通过率
				if(!classDao.CheckElclassDepPassing(depTree.getChild().get(i), elclass)){
					classDao.addElclassDepPassing(depTree.getChild().get(i).getId(), elclass.getId(), dep.getRatioPassing());//保存改通过率
				}
				double passing = 0;
				passing = classDao.getElclassDepPassing(depTree.getChild().get(i), elclass);
				if( passing == 0.0){//设置通过率  （假如为0 说明第一次没有设置过通过率，所有用计算出来的通过率进行计算排序）
					dep.setRatioPassing_(dep.getRatioPassing());
					classDao.alterElclassDepPassing(depTree.getChild().get(i).getId(), elclass.getId(), dep.getRatioPassing());//修改通过率
				}else{
					dep.setRatioPassing_(passing);
				}
				dep.setName(departmentDao.getDepById(depTree.getChild().get(i).getId()).getName()); 
				departments.add(dep);
			}
			//排序
			departments=classDao.getDepSortByRatioPassing_(departments);
		}else{
			departments1 = null;
		}
		department = department==null?new Department(1):department; 
//		
//		if(isExprot()){//导出excle表
//			return "quiz_stat_eval_EXCEL";
//		}
		return "class_quiz_stat_eval"; 
	}
	public String elclass_UnitRanking_DegreeScoreInit() throws ElException {   
		String xiaji = departmentDao.getByIdXiaJi(unitRank.getUnit().getId());//获取单位下级id串 
		xiaji = xiaji.length() > 0 ? xiaji + ","+unitRank.getUnit().getId() : unitRank.getUnit().getId()+"";
		int UnitId =  unitRank.getUnit().getId();
		unitRank = studyQuizDao.getDegreeScoreDetails(unitRank.getElclass().getId(), xiaji);
		unitRank.setUnit(new Department(UnitId));
		
		return "elclass_UnitRanking_DegreeScore";
	}
	public String elclass_UnitRanking_TitleScoreInit() throws ElException {   
		String xiaji = departmentDao.getByIdXiaJi(unitRank.getUnit().getId());//获取单位下级id串 
		xiaji = xiaji.length() > 0 ? xiaji + ","+unitRank.getUnit().getId() : unitRank.getUnit().getId()+"";
		int UnitId =  unitRank.getUnit().getId();
		unitRank = studyQuizDao.getTitleScoreDetails(unitRank.getElclass().getId(), xiaji);
		unitRank.setUnit(new Department(UnitId));
		return "elclass_UnitRanking_TitleScore";
	}
	//培训班分配考场列表
	public String elclass_assignRoom() throws ElException{
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
		sublibs = elClass == null ? 1 : sublibs;
		elclasses = classDao.getClassList(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8","0,2", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,sublibs,"0,1,2,3,4,5,6,7,8");
		if(elclasses!=null&&elclasses.size()>0){
			for(int i=0;i<elclasses.size();i++){
				elclasses.get(i).setExamRooms(eroomDao.listExamRoomByClass_cisco(elclasses.get(i).getId(),getSessionIntValue(ElConstants.SESSION_USERID)));
			}
		}
		return "elclass_assignRoom";
	}
	//取消培训班绑定考场
	public String quitAssignRoom() throws ElException{
		classDao.quitAssignRoom(elClass.getId(),examRoom.getId());
		return "quitAssignRoom";
	}
	public String checkOrderidIsExist() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		boolean flag =classDao.checkOrderidIsExist(course,elclass);
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = String.valueOf(flag) ;
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String elclass_xianxiacourse_import() throws ElException{
		return "elclass_xianxiacourse_import";
	}
	
	/**
	 * 导入前的检测
	 * 
	 * @return
	 * @throws Exception
	 */
	public String xianxiaCourseImportCheck() throws Exception {
		
		String resultPage = "coursemes_import";
	
		if (null != st) {
			if (!J2EEFileUtil.getExtention(stFileName).toLowerCase().equals(
					"xls")) {
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return resultPage;
			}
			if (st.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return resultPage;
			} else {
				// UserExcelUtil.writeUser(st);
				// String isOk=UserExcelUtil.writeUser2(st);
				String isOk = UserExcelUtil.checkWriteCourse(st,course.getId(),elclass.getId());
				// if(!"true".equals(isOk)&&!"".equals(isOk)){//返回
				setElmessage(isOk);
				// 复制此文件到服务器临时保存
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String filename = "user_" + userid + "_"
						+ System.currentTimeMillis();
				J2EEFileUtil.upload(st, "xls", "/importtemp/", filename);
				stFileName = filename + ".xls";
				return "accountImportInfo";
				// }
			}
		} else {
			setElmessage("请输入上传文件");
			return resultPage;
		}
	}
	
	public String xianxiaCourseImport() throws ElException {
		if (stFileName != null) {
			File xls = new File(ServletActionContext.getServletContext()
					.getRealPath("/")
					+ "/importtemp/" + stFileName);
			if (xls.exists()) {
				String isOk = "";
				isOk = UserExcelUtil.writeCourse(xls,course.getId(),elclass.getId());
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_QUESTION,
						ElLoggerConstants.LOG_TYPE_IMPORT, isOk,
						ElLoggerConstants.LOG_RES_SUCC);
				xls.delete();
				xls.deleteOnExit();
			} else {
				setElmessage("请输入上传文件");
				return "account_import";
			}
		} else {
			setElmessage("请输入上传文件");
			return "account_import";
		}
		return "elclass_course";
	}
	
	
	//sd1227修改
	//sd0110
	private Map<String , List<BaseDatat>>  listMap;
	

	public Map<String, List<BaseDatat>> getListMap() {
		return listMap;
	}

	public void setListMap(Map<String, List<BaseDatat>> listMap) {
		this.listMap = listMap;
	}
	/**
	 * 培训班统计
	 * @return
	 * @throws ElException
	 */
	public String trainPassList() throws ElException {
//		int depid = deptid!=null?deptid:getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
//		int id = dep != null ?new dep.setId(depid): 1;
		//depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		//depTree = departmentDao.getDepTree_level1(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		if(SystemConfOp.getValue(ElConstants.SD_ELCLASS).trim()!=null)
		elclass = workCourseDao.getElclassInfo(SystemConfOp.getValue(ElConstants.SD_ELCLASS).trim());
//		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
//		}else{
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		}
		
		
		
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
			depTree = departmentDao.getDepTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		}else{
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
//		dep=depTree;
		int cid = (elclass == null) ? 0 : elclass.getId();
//		elusers = classDao.listAssignedUser(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
//		count =classDao.listAssignedUserSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		elClass = classDao.getElClassById(cid);  
		
//		int id = dep != null ? dep.getId():depid;
//		dep = new Department();
//		dep.setId(depid);
		
		//elusers = classDao.classStudent(getPageNow(), getPageSize(),depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		//count =classDao.classStudentSize(depid,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
		
			
		if(deptid==null||deptid<=0){
			dep=depTree;
		}else{
			dep=departmentDao.getDepById(deptid);
		}
		
		if(getSessionIntValue(ElConstants.SESSION_ROLE)!=1){
			if(deptid==null||deptid<=0){
				dep=departmentDao.getDepById(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
				System.out.println("depart="+dep.getId());
			}else{
				dep=departmentDao.getDepById(deptid);
				System.out.println("depart="+dep.getId());
			}
			
		}
//		logger.info(dep.getId());
		if(isExport()){ 
			//elusers = classDao.classStudent(id,cid,CourseConstants.COURSE_STUDY_STATUS_XX,null,this.getStarttime(),this.getEndtime(),elUser);
			elusers = classDao.classStudent_sd(cid,dep ,elUser);
			return "class_student_EXCEL";
		}

		elusers = classDao.classStudent_sd(cid,dep,elUser,getPageNow(), getPageSize());
		if(elUser!=null&&elUser.getJingzhongs()!=null){
		}
		count =classDao.classStudentSize_sd(cid,dep,elUser);
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		listMap = workCourseDao.getBaseTypeAndDataList();
		workCourseUser= workCourseDao.listWorkCourseUser();
		if(elUser!=null&&elUser.getFlag()==1){
			//elUser.setGangwei("0");
			elUser.setJingzhong(elUser.getJingzhong());
			setDeptid(deptid);
			logger.info(deptid);
		}
		return "trainPassList";
	}
	
	//sd0217 修改
	private List<BaseDatat> workType;
	private int typeid;
	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public List<BaseDatat> getWorkType() {
		return workType;
	}

	public void setWorkType(List<BaseDatat> workType) {
		this.workType = workType;
	}

	public String trainPassList_two() throws ElException {
		workType=userDao.getBaseDatatByTypeid(typeid);
		return "trainPassList_two";
	}
	
}
