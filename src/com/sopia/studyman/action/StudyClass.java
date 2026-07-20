package com.sopia.studyman.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.StringUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CoursePageDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.dao.impl.CourseDaoImpl;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.StationDao;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.UnitRanking;
import com.sopia.frontman.dao.FrontDao;
import com.sopia.newsandmess.entities.News;
import com.sopia.newversion.NewVersionUtil;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.dao.LineTrainRecordDao;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.LineTrainRecord;
import com.sopia.studyman.entities.LineTrainRecordStuff;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.studyman.entities.Schoolrolls;


public class StudyClass extends BaseAction {
	private static final Log logger = LogFactory.getLog(StudyClass.class);
	private StudyClassDao studyClassDao;
	private ELUser elUser;
	private List<MyClass> myClasses;
	private MyClass myClass;
	private EroomDao eroomDao;
	private ElClass elclass;
	private ClassDao classDao;
	private StudyQuizDao studyQuizDao;
	private ElClType cltypeTree;
	private ElClTypeDao elClTypeDao;
	private ElClType cltype; 
	private List<ElClass> elclasses;
	private List<Course> bxCourses;
	private List<Course> xxCourses;
	private String Return; 
	private LineTrainRecordDao linetrainDao; 
	private LineTrainRecord linetrainrecord; 
	private List<LineTrainRecord> listLineTrainrecord;
	
	private List<Schoolrolls> listSchoolrolls; 
	private Department depTree; 
	private String message; 
	private Integer state; 
	private String ids; 
	private Integer deptid; 
	private Integer  count; 
	private List<MyRoom> myrooms;
	private Course course;
	private StringBuffer explain;
	private int isCorrespond;//搜索是否符合申请的培训班  0 全部可申请的培训班  1 符合申请的培训班
	private Course courseBX;
	private Course courseXX;
	private List<News> zxtzggs;//最新通知公告
	private List<News> tjtzggs;//推荐通知公告
	private FrontDao frontDao;
	private int jingzhongIspass;
	private int dishiIspass;
	private int zhijiIspass;
	private int zhiwuIspass;
	private int eroomIspass;
	private int eroomepIspass;
	private int sexIspass;
	private int ageIspass;
	private int depIspass;
	private int classIspass;
	private int sumIspass=1;
	private File[] myFiles;
    private String[] myFileFileNames;
    private String[] myFileContentTypes;
    private LineTrainRecordStuff lineTrainRecordStuff;
    private InputStream inputStream;
    private String downFileName;
    private Department department;
    private int sub_department;//是否包含下级部门
    private List<BaseDatat> jingzhongs;
    private String  name;
    private String  username;
	private Timestamp       start;//搜索时间  开始时间 申请时间
	private Timestamp       end;//结束时间
	private Timestamp       peixun;//培训所在时间
	private int             allcredit;
	private int 			state1;
	private UserDao userDao;
	private StationDao stationDao;
	private UnitRanking unitRank;
	private Department unit; //单位
	private int type;
	private Map<String,Object> map;
	private List<MyCourse> studyCourseList;
	private HttpRequestDeviceUtils httpRequestDeviceUtils;
	private MyClass new_cla;//本年度最新一期培训班
	private MyClass nianjian_cla;//本年度最新一期培训班
	private boolean isexam;//是否可以进培训班关联的考场
	private MyRoom myroom;
	private List<ExamRoom> rooms;
	
	//培训班是否绑定考场
	private boolean isbindExamroom;
	
	private List<ElClass> tjclasses;
	private List<ElClass> zztjclasses;
	
	public List<ElClass> getTjclasses() {
		return tjclasses;
	}
	public void setTjclasses(List<ElClass> tjclasses) {
		this.tjclasses = tjclasses;
	}
	public List<ElClass> getZztjclasses() {
		return zztjclasses;
	}
	public void setZztjclasses(List<ElClass> zztjclasses) {
		this.zztjclasses = zztjclasses;
	}
	public boolean isIsbindExamroom() {
		return isbindExamroom;
	}
	public void setIsbindExamroom(boolean isbindExamroom) {
		this.isbindExamroom = isbindExamroom;
	}
	public List<ExamRoom> getRooms() {
		return rooms;
	}
	public void setRooms(List<ExamRoom> rooms) {
		this.rooms = rooms;
	}
	public MyRoom getMyroom() {
		return myroom;
	}
	public void setMyroom(MyRoom myroom) {
		this.myroom = myroom;
	}
	public boolean isIsexam() {
		return isexam;
	}
	public void setIsexam(boolean isexam) {
		this.isexam = isexam;
	}
	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}
	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}
	
	private int initcompliance;
	private CoursePageDao coursePageDao;
	
	public CoursePageDao getCoursePageDao() {
		return coursePageDao;
	}

	public void setCoursePageDao(CoursePageDao coursePageDao) {
		this.coursePageDao = coursePageDao;
	}

	public int getInitcompliance() {
		return initcompliance;
	}

	public void setInitcompliance(int initcompliance) {
		this.initcompliance = initcompliance;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Department getUnit() {
		return unit;
	}

	public void setUnit(Department unit) {
		this.unit = unit;
	}

	public UnitRanking getUnitRank() {
		return unitRank;
	}

	public void setUnitRank(UnitRanking unitRank) {
		this.unitRank = unitRank;
	}

	public int getEroomepIspass() {
		return eroomepIspass;
	}

	public void setEroomepIspass(int eroomepIspass) {
		this.eroomepIspass = eroomepIspass;
	}

	public File[] getMyFiles() {
		return myFiles;
	}

	public void setMyFiles(File[] myFiles) {
		this.myFiles = myFiles;
	}

	public String[] getMyFileFileNames() {
		return myFileFileNames;
	}

	public void setMyFileFileNames(String[] myFileFileNames) {
		this.myFileFileNames = myFileFileNames;
	}

	public String[] getMyFileContentTypes() {
		return myFileContentTypes;
	}

	public void setMyFileContentTypes(String[] myFileContentTypes) {
		this.myFileContentTypes = myFileContentTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Timestamp getPeixun() {
		return peixun;
	}

	public void setPeixun(Timestamp peixun) {
		this.peixun = peixun;
	}

	public int getAllcredit() {
		return allcredit;
	}

	public void setAllcredit(int allcredit) {
		this.allcredit = allcredit;
	}

	public int getState1() {
		return state1;
	}

	public void setState1(int state1) {
		this.state1 = state1;
	}

	public List<BaseDatat> getJingzhongs() {
		return jingzhongs;
	}

	public void setJingzhongs(List<BaseDatat> jingzhongs) {
		this.jingzhongs = jingzhongs;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDownFileName() {
		return downFileName;
	}

	public void setDownFileName(String downFileName) {
		this.downFileName = downFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public LineTrainRecordStuff getLineTrainRecordStuff() {
		return lineTrainRecordStuff;
	}

	public void setLineTrainRecordStuff(LineTrainRecordStuff lineTrainRecordStuff) {
		this.lineTrainRecordStuff = lineTrainRecordStuff;
	}

	public File[] getMyFile() {
		return myFiles;
	}

	public void setMyFile(File[] myFiles) {
		this.myFiles = myFiles;
	}

	public String[] getMyFileFileName() {
		return myFileFileNames;
	}

	public void setMyFileFileName(String[] myFileFileNames) {
		this.myFileFileNames = myFileFileNames;
	}

	public String[] getMyFileContentType() {
		return myFileContentTypes;
	}

	public void setMyFileContentType(String[] myFileContentTypes) {
		this.myFileContentTypes = myFileContentTypes;
	}

	public int getSumIspass() {
		return sumIspass;
	}

	public void setSumIspass(int sumIspass) {
		this.sumIspass = sumIspass;
	}

	public int getJingzhongIspass() {
		return jingzhongIspass;
	}

	public void setJingzhongIspass(int jingzhongIspass) {
		this.jingzhongIspass = jingzhongIspass;
	}

	public int getDishiIspass() {
		return dishiIspass;
	}

	public void setDishiIspass(int dishiIspass) {
		this.dishiIspass = dishiIspass;
	}

	public int getZhijiIspass() {
		return zhijiIspass;
	}

	public void setZhijiIspass(int zhijiIspass) {
		this.zhijiIspass = zhijiIspass;
	}

	public int getZhiwuIspass() {
		return zhiwuIspass;
	}

	public void setZhiwuIspass(int zhiwuIspass) {
		this.zhiwuIspass = zhiwuIspass;
	}

	public int getEroomIspass() {
		return eroomIspass;
	}

	public void setEroomIspass(int eroomIspass) {
		this.eroomIspass = eroomIspass;
	}

	public int getSexIspass() {
		return sexIspass;
	}

	public void setSexIspass(int sexIspass) {
		this.sexIspass = sexIspass;
	}

	public int getAgeIspass() {
		return ageIspass;
	}

	public void setAgeIspass(int ageIspass) {
		this.ageIspass = ageIspass;
	}

	public int getDepIspass() {
		return depIspass;
	}

	public void setDepIspass(int depIspass) {
		this.depIspass = depIspass;
	}

	public int getClassIspass() {
		return classIspass;
	}

	public void setClassIspass(int classIspass) {
		this.classIspass = classIspass;
	}

	public List<News> getZxtzggs() {
		return zxtzggs;
	}

	public void setZxtzggs(List<News> zxtzggs) {
		this.zxtzggs = zxtzggs;
	}

	public List<News> getTjtzggs() {
		return tjtzggs;
	}

	public void setTjtzggs(List<News> tjtzggs) {
		this.tjtzggs = tjtzggs;
	}

	public FrontDao getFrontDao() {
		return frontDao;
	}

	public void setFrontDao(FrontDao frontDao) {
		this.frontDao = frontDao;
	}

	public int getIsCorrespond() {
		return isCorrespond;
	}

	public void setIsCorrespond(int isCorrespond) {
		this.isCorrespond = isCorrespond;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<MyRoom> getMyrooms() {
		return myrooms;
	}

	public void setMyrooms(List<MyRoom> myrooms) {
		this.myrooms = myrooms;
	}

	public StudyClassDao getStudyClassDao() {
		return studyClassDao;
	}

	public void setStudyClassDao(StudyClassDao studyClassDao) {
		this.studyClassDao = studyClassDao;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public List<MyClass> getMyClasses() {
		return myClasses;
	}

	public void setMyClasses(List<MyClass> myClasses) {
		this.myClasses = myClasses;
	}

	public MyClass getMyClass() {
		return myClass;
	}

	public void setMyClass(MyClass myClass) {
		this.myClass = myClass;
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
	

	public List<Schoolrolls> getListSchoolrolls() {
		return listSchoolrolls;
	}

	public void setListSchoolrolls(List<Schoolrolls> listSchoolrolls) {
		this.listSchoolrolls = listSchoolrolls;
	}

	public LineTrainRecord getLinetrainrecord() {
		return linetrainrecord;
	}

	public void setLinetrainrecord(LineTrainRecord linetrainrecord) {
		this.linetrainrecord = linetrainrecord;
	}
	

	public LineTrainRecordDao getLinetrainDao() {
		return linetrainDao;
	}

	public void setLinetrainDao(LineTrainRecordDao linetrainDao) {
		this.linetrainDao = linetrainDao;
	}
	

	public List<LineTrainRecord> getListLineTrainrecord() {
		return listLineTrainrecord;
	}

	public void setListLineTrainrecord(List<LineTrainRecord> listLineTrainrecord) {
		this.listLineTrainrecord = listLineTrainrecord;
	}

	/**
	 * 学员在学班级列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String myelclass_list() throws ElException {
//		myClasses = studyClassDao.listMyStudyClass(getSessionIntValue(ElConstants.SESSION_USERID));
//		if (null != myClasses) {
//			for (int i = 0; i < myClasses.size(); i++) {
//				studyClassDao.setMyPassclass(
//						getSessionIntValue(ElConstants.SESSION_USERID),
//						myClasses.get(i).getElClass().getId());
//			}
//		}
		myClasses = studyClassDao.listMyStudyClass(getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(), getPageSize());
		count = studyClassDao.listMyStudyClassSize(getSessionIntValue(ElConstants.SESSION_USERID));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "myelclass_list_phone"; 
		}

		return "myelclass_list";
	}

	/**
	 * 学员申请培训班级列表
	 * 
	 * @return
	 * @throws ElException
	 */  
	public String class_listbytypeid() throws ElException { 
		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true); 
		//depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		int cltid = cltype == null ? cltypeTree.getId(): cltype.getId();
		//初始化类别id 
		if(cltype==null){
			cltype=new ElClType(cltid);
		}
		if(elclass!=null && elclass.getName().equals("填写培训班名称....")){
			elclass.setName("");
		}
		elclasses = studyClassDao.getApplyForeElclass(cltypeTree, cltid,elclass, getSessionIntValue(ElConstants.SESSION_ROLE),"",getPageNow(),getPageSize());// "" 不用填写值
		count = studyClassDao.getApplyForeElclassSize(cltypeTree, cltid,elclass, getSessionIntValue(ElConstants.SESSION_ROLE),"");  
		for(int i = 0;i < elclasses.size(); i++){  
//			列表页不需要申请报名。 注释掉 
			elclasses.get(i).getElRegistration().setJoinNumber(classDao.getJoinNumber(elclasses.get(i).getId())); 
//			if(studyClassDao.checkClassIsUser(elclasses.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID))){//是否已报名
//				elclasses.get(i).setIsjoin("true");
//			}else{
//				elclasses.get(i).setIsjoin("false");
//			}
			if(elclasses.get(i).getElRegistration().getIsAudit()==1){
				if(studyClassDao.checkStudyClassApply(elclasses.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID))){
					elclasses.get(i).setIsjoin("true");
				}else{
					elclasses.get(i).setIsjoin("false");
				}
			}else{
				if(studyClassDao.checkClassIsUser(elclasses.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID))){
					elclasses.get(i).setIsjoin("true");
				}else{
					elclasses.get(i).setIsjoin("false");
				}
			}
			if(checkIsuserApp(elclasses.get(i), elUser)){//如果返回false证明有某条不符合条件
				elclasses.get(i).setIsuserApp(1); 
			}else{
				elclasses.get(i).setIsuserApp(2); 
			}
			if(elclasses.get(i)!= null){
				String x =StringUtil.shortStr(elclasses.get(i).getDescription(), 80, "...");
				elclasses.get(i).setDescription(x==null||"".equals(x)?"无说明...":x);
			}
		}
		//最新通知公告
	    this.zxtzggs=this.frontDao.listZxNews(8,1);
	    //最新推荐通知公告
	    this.tjtzggs=this.frontDao.listZxNews(8, 1, 1);
		//return "class_listbytypeid";
	    //本栏目推荐培训班
	    tjclasses = studyClassDao.getTjElclass(cltid,1); // 1 为推荐培训班
	    zztjclasses = studyClassDao.getTjElclass(1);
	    getRequest().setAttribute("isAll","yes");
	    boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "class_listbytypeid_isPass_phone"; 
		}

	    return "class_listbytypeid_isPass";
	}
	/**
	 * 学员申请培训班级通过列表
	 * 
	 * @return
	 * @throws ElException
	 */  
	public String class_listbytypeid_isPass() throws ElException { 
		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true); 
		//depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		int cltid = cltype == null ? cltypeTree.getId(): cltype.getId();
		//初始化类别id 
		if(cltype==null){
			cltype=new ElClType(cltid);
		}
		if(elclass!=null && elclass.getName().equals("填写培训班名称....")){
			elclass.setName("");
		}
		elclasses = studyClassDao.getApplyForeElclass(cltypeTree, cltid,elclass, getSessionIntValue(ElConstants.SESSION_ROLE)," and elr.registrationStartTime < sysdate and elr.registrationStopTime > sysdate ",999999999,1); //不限制条数， 用于获取到可申请的培训班
		if(elclasses.size() != 0){
			String classids = "";
			for(int i = 0;i < elclasses.size(); i++){   //获取通过的培训班
				if(checkIsuserApp(elclasses.get(i), elUser)){//如果返回false证明有某条不符合条件 
					if(classids.equals(""))
						classids = classids + elclasses.get(i).getId(); 
					else
						classids = classids +","+ elclasses.get(i).getId();  
				} 
			}
			if(!classids.equals("")){
				elclasses = studyClassDao.getApplyForeElclass(cltypeTree, cltid,elclass, getSessionIntValue(ElConstants.SESSION_ROLE)," and elc.id in ("+classids+") ",getPageNow(),getPageSize());
				count = studyClassDao.getApplyForeElclassSize(cltypeTree, cltid,elclass, getSessionIntValue(ElConstants.SESSION_ROLE)," and elc.id in ("+classids+") ");  
				for(int i = 0;i < elclasses.size(); i++){   
//					elclasses.get(i).getElRegistration().setJoinNumber(classDao.getJoinNumber(elclasses.get(i).getId())+""); 
//					if(studyClassDao.checkClassIsUser(elclasses.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID))){//是否已报名
//						elclasses.get(i).setIsjoin("true");
//					}else{
//						elclasses.get(i).setIsjoin("false");
//					}
					if(elclasses.get(i).getElRegistration().getIsAudit()==1){
						if(studyClassDao.checkStudyClassApply(elclasses.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID))){
							elclasses.get(i).setIsjoin("true");
						}else{
							//elclasses.get(i).setIsjoin("false");
							if(studyClassDao.checkClassIsUser(elclasses.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID))){
								elclasses.get(i).setIsjoin("true_assign");
							}else{
								elclasses.get(i).setIsjoin("false");
							}
						}
					}else{
						if(studyClassDao.checkClassIsUser(elclasses.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID))){
							elclasses.get(i).setIsjoin("true");
						}else{
							elclasses.get(i).setIsjoin("false");
						}
					}
					elclasses.get(i).setIsuserApp(1);
					String x =StringUtil.shortStr(elclasses.get(i).getDescription(), 80, "...");
					elclasses.get(i).setDescription(x==null||"".equals(x)?"无说明...":x);
				}
			}else{
				elclasses = null;
				count = 0;
			}
		}else{
			elclasses = null;
			count = 0;
		}
		if("ajax".equals(Return)){
			printMsg("{'count':"+count+"}");
			return null;
		}
	    //最新通知公告
	    this.zxtzggs=this.frontDao.listZxNews(8,1);
	    //最新推荐通知公告
	    this.tjtzggs=this.frontDao.listZxNews(8, 1, 1);
	    boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "class_listbytypeid_isPass_phone"; 
		}

		return "class_listbytypeid_isPass";
	}
	/**
	 * 学员申请培训班级列表
	 * 
	 * @return
	 * @throws ElException
	 */  
	public String class_view2() throws ElException { 
		//cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true); 
		elclass = studyClassDao.getApplyForeElclassById(elclass.getId());  
		if(elclass.getElRegistration() == null){ 
			setElmessage("培训班类型以改变为分配式，不能进入申请式查看！");
			return "error";
		}
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		elclass.getElRegistration().setJoinNumber(classDao.getJoinNumber(elclass.getId()));
		if(elclass.getElRegistration()!= null){
			if(checkIsuserApp(elclass, elUser)){//如果返回false证明有某条不符合条件 
				elclass.setIsuserApp(1);
			}else{
				elclass.setExplain(explain.toString());//不通过说明
				elclass.setIsuserApp(2);
			}
			if(elclass.getElRegistration().getIsAudit()==1){
				elclass.getElRegistration().setApplyNumber(classDao.getClassApplyNumber(elclass.getId()));
				if(studyClassDao.checkStudyClassApply(elclass.getId(), getSessionIntValue(ElConstants.SESSION_USERID))){
					elclass.setIsjoin("true");
				}else{
					//elclass.setIsjoin("false");
					if(studyClassDao.checkClassIsUser(elclass.getId(), getSessionIntValue(ElConstants.SESSION_USERID))){
						elclass.setIsjoin("true_assign");
					}else{
						elclass.setIsjoin("false");
					}
				}
			}else{
				elclass.getElRegistration().setApplyNumber(elclass.getElRegistration().getJoinNumber());
				if(studyClassDao.checkClassIsUser(elclass.getId(), getSessionIntValue(ElConstants.SESSION_USERID))){
					elclass.setIsjoin("true");
				}else{
					elclass.setIsjoin("false");
				}
			}
		}
		bxCourses = classDao.listClassCourses(Integer.valueOf(elclass.getId()), CourseConstants.COURSE_STUDY_STATUS_BX);  
		xxCourses = classDao.listClassCourses(Integer.valueOf(elclass.getId()),CourseConstants.COURSE_STUDY_STATUS_XX);
		//最新通知公告
	    this.zxtzggs=this.frontDao.listZxNews(8,1);
	    //最新推荐通知公告
	    this.tjtzggs=this.frontDao.listZxNews(8, 1, 1);
	    
	    //本栏目推荐培训班
	    tjclasses = studyClassDao.getTjElclass(elclass.getCltype().getId(),1); // 1 为推荐培训班
	    zztjclasses = studyClassDao.getTjElclass(1);
	    
	    boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "class_view2_phone"; 
		}

		return "class_view2";
	}
	 
	
	/**
	 * 选班
	 * 
	 * @return
	 * @throws ElException
	 */ 
	public String submitAppalyClass() throws ElException {
		int userid = getSessionIntValue(ElConstants.SESSION_USERID); 
		//查出该培训班可申请信息
		ELClassRegistration ecr=classDao.getClassRegistration(elclass.getId());
		if(ecr.getIsAudit()==1){//如果是需要审核的
			//studyClassDao.updateStudyClassStatus(userid, elclass.getId(), -1);
			if(!studyClassDao.checkStudyClassApply(elclass.getId(), userid)){
				studyClassDao.addStudyClassApply(elclass.getId(), userid);
			}
		}else{
			if(!classDao.checkElclassIsUsers(userid, elclass.getId())){
				classDao.assign2userAdd3(userid,elclass.getId(),ClassConstants.CLASS_SQFS_SQ);
			}
			//把人员分配到该培训班中所有考场
			//1.获取该培训班中所有被绑定的考场
			//2.获取每个考场中所有的试卷
			//3.对每张试卷进行分配人员
			List<ExamRoom> eroomList=eroomDao.listExamRoomByClass(elclass.getId());
			List<ExamPaper> examPapers=null;
			for (int i = 0; i < eroomList.size(); i++) {
				examPapers = eroomDao.getEroomepwithusizes(eroomList.get(i).getId());//获取该考场中的所有试卷信息
				for (int j = 0; j < examPapers.size(); j++) {
//					if (!studyQuizDao.hasInQuizPaper(userid, eroomList.get(i).getId(), // 检测是否已经进入考场
//							examPapers.get(j).getId(),elclass.getId())) {
//						studyQuizDao.intoQuizPaper(userid, eroomList.get(i).getId(),
//								examPapers.get(j).getId(), elclass.getId());
//					}
					//判断试卷是否已被删除
					if(examPapers.get(j).getStatus()!=1){
						//检测该学员是否分配了该试卷
						if(!studyQuizDao.checkStudyExamPaper(userid,
								examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId())){
							//添加该学员到 学员试卷表中
							studyQuizDao.addStudyExamPaper(userid,
								examPapers.get(j).getId(),eroomList.get(i).getId(),elclass.getId());
						}
					}
				}
				if (!eroomDao.checkuser2eroom(eroomList.get(i).getId(),  // 检查用户有没有分配到该考场
						userid, elclass.getId())) {
					eroomDao.adduser2eroom( eroomList.get(i).getId(),
							userid, 1, elclass.getId(),CourseConstants.EXAMROOM_SQFS_SQ);
				}
			}
		}
		elclass = classDao.getClassById(elclass.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "Return_phone"; 
		}

		return Return;
	}
	
	/**
	 * 学员查看班级详情
	 * 
	 * @return   
	 * @throws ElException
	 */
/**	public String myelclass_view() throws ElException {
		int id = elclass.getId();
		int userid =  getSessionIntValue(ElConstants.SESSION_USERID);
		myClass = new MyClass(); 
		myClass.setMyCourseB(studyClassDao.listMyClassCourseStat (id,userid ,CourseConstants.COURSE_STUDY_STATUS_BX ));
		myClass.setMyCourseX(studyClassDao.listMyClassCourseStat (id, userid ,CourseConstants.COURSE_STUDY_STATUS_XX ));
		myClass.setElClass(classDao.getElClassById(elclass.getId()));
		//检测是否通过培训班
		studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		if(status==2){
			myClass.setPassed(true);
		}else{
			myClass.setPassed(false);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String now=sdf.format(new Date());
		getRequest().setAttribute("now", now);
		return "myelclass_view";
	}*/
	public String myelclass_view_wjm() throws ElException{
		if(type == 1){
			studyCourseList = studyClassDao.getCourses_wjm(elclass.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			studyCourseList = studyCourseList == null?new ArrayList<MyCourse>():studyCourseList;
			if(studyCourseList.size()>0){
				for(MyCourse myCourse:studyCourseList){
					myCourse.setFirstCpid(coursePageDao.getFirstCpId(myCourse.getCourse().getId()));
				}
			}
			//学时及比例
			map = NewVersionUtil.getCourseProcess(studyCourseList);
		}
		return "myelclass_view_wjm";
	}
	//培训班详情
	public String myelclass_view() throws ElException { 
		elclass = classDao.getClassById(elclass.getId());
		int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID)
				: elUser.getId();
		myClass = new MyClass(); 
		String sqlBX = ""; 
		String roomid = "";
		
		if(courseBX != null && courseBX.getCourseForm() != -1){			
			sqlBX = " and c.courseForm = "+courseBX.getCourseForm();
		} 
		if(elclass.getClasstype() == 2){//自主培训
			roomid = eroomDao.getBindingCourseByRoomId(elclass.getId(),0,CourseConstants.COURSE_TABLENAME_CLASS_COURSE_AT);
			myClass.setMyCourseB(studyClassDao.listMyClassCourseStat3(elclass.getId(),userid,roomid
				,CourseConstants.COURSE_TABLENAME_CLASS_COURSE_AT,CourseConstants.COURSE_STUDY_STATUS_BX,sqlBX));
			myClass.setMyCourseX(studyClassDao.listMyClassCourseStat3(elclass.getId(), userid,roomid
					,CourseConstants.COURSE_TABLENAME_CLASS_COURSE_AT,CourseConstants.COURSE_STUDY_STATUS_XX,sqlBX));
		}else{ 
			roomid = eroomDao.getBindingCourseByRoomId(elclass.getId(),0,CourseConstants.COURSE_TABLENAME_CLASS_COURSE);
			myClass.setMyCourseB(studyClassDao.listMyClassCourseStat3(elclass.getId(),userid,roomid
				,CourseConstants.COURSE_TABLENAME_CLASS_COURSE,CourseConstants.COURSE_STUDY_STATUS_BX,sqlBX));
			myClass.setMyCourseX(studyClassDao.listMyClassCourseStat3(elclass.getId(), userid,roomid
				,CourseConstants.COURSE_TABLENAME_CLASS_COURSE,CourseConstants.COURSE_STUDY_STATUS_XX,sqlBX));
			//myClass.setMyCourseX(studyClassDao.listMyClassCourseStat3(elclass.getId(), userid,roomid,CourseConstants.COURSE_TABLENAME_CLASS_COURSE,CourseConstants.COURSE_STUDY_STATUS_XX,sqlBX));
		//	myClass.setMyCourseB(studyClassDao.listMyClassCourseStat(elclass.getId(), userid,  
		//			CourseConstants.COURSE_STUDY_STATUS_BX)); 
		//	myClass.setMyCourseX(studyClassDao.listMyClassCourseStat(elclass.getId(), userid,
		//			CourseConstants.COURSE_STUDY_STATUS_XX)); 
		}
		CourseDao courseDao = new CourseDaoImpl();
		for(int i=0;i<myClass.getMyCourseB().size();i++){
			int courseid = myClass.getMyCourseB().get(i).getCourse().getId();
			Course c = courseDao.getCourseById(courseid);
			if(c!=null && c.getExurl()!=null && !c.getExurl().equals("") && c.getExurl().contains("Course-")){
				int finish = courseDao.getUserSCInfo(userid+"",c.getExurl(),"completed");
				int all = courseDao.getSCItemInfo(c.getExurl())-1;
				myClass.getMyCourseB().get(i).setProcess((float)finish/(float)all*100);
				int passtime = courseDao.getSCPasstime(courseid,elclass.getId());
				myClass.getMyCourseB().get(i).setPasstime2(passtime/60);
			}
		}
		
		myClass.setElClass(classDao.getElClassById_cisco(elclass.getId(),getSessionIntValue(ElConstants.SESSION_USERID)));
		//考试平均成绩  getKC_CJ_AVG_  只算以考成绩的平均分
		myClass.getElClass().setKc_scoresAVG(studyQuizDao.getKC_CJ_AVG_(userid, elclass.getId(), elclass.getClasstype() == 2 ?"CLASS_COURSE_AT":"CLASS_COURSE"));
		//学分总数
		myClass.getElClass().setXf_Count(studyQuizDao.getXF_credits(userid, elclass.getId(), elclass.getClasstype() == 2 ?"CLASS_COURSE_AT":"CLASS_COURSE"));
		//学时总数
		List XS = new  ArrayList();
		XS = studyQuizDao.getXs_period(userid, elclass.getId(), elclass.getClasstype() == 2 ?"CLASS_COURSE_AT":"CLASS_COURSE"); 
		if(XS.size() == 0){ //如果没有记录就初始化 0
			XS.add(0);
		}   
		myClass.getElClass().setXs_Count(Float.parseFloat(XS.get(0).toString()));
		
		if(myClass.getElClass().getClasstype()==2){ 
			//检测是否通过自主培训班
			studyClassDao.setMyPassclass_at(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		}else{ 
			//检测是否通过培训班
			studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		}
		//获取学员-培训班的状态
		int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		if(status==2){
			myClass.setPassed(true);
			//elclass_recordInit(elclass.getId());//个人积分初始化
		}else{
			myClass.setPassed(false);
		}
		//更新该学员所在单位的排名
		elclass_Unit_recordInit(elclass.getId());
		//返回一个当前时间
		//Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String now=sdf.format(new Date());
		getRequest().setAttribute("now", now);
		
		rooms = eroomDao.listExamRoomByClass_cisco(elclass.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
		elclass.setExamRooms(rooms);
		for(int i=0;i<rooms.size();i++){
			rooms.get(i).setIsPass(eroomDao.getIsPass(getSessionIntValue(ElConstants.SESSION_USERID),rooms.get(i).getId()));
		}
		myroom = studyClassDao.myRoom(elclass.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
		
		
		if(type == 1){
			studyCourseList = studyClassDao.getCourses(elclass.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			studyCourseList = studyCourseList == null?new ArrayList<MyCourse>():studyCourseList;
			if(studyCourseList.size()>0){
				for(MyCourse myCourse:studyCourseList){
					myCourse.setFirstCpid(coursePageDao.getFirstCpId(myCourse.getCourse().getId()));
				}
			}
			//学时及比例
			map = NewVersionUtil.getCourseProcess(myClass.getMyCourseB());
			int isnopass= studyClassDao.isNoPassBX(getSessionIntValue(ElConstants.SESSION_USERID),elclass.getId());
			int countforxx = studyClassDao.getcountXFforXX(getSessionIntValue(ElConstants.SESSION_USERID),elclass.getId());
			if(isnopass==0&&countforxx>=elclass.getOptionalcredit()){
				isexam=true;
			}
			System.out.println("isexam======"+isexam);
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			System.out.println("前台培训班详情===="+b);
			if(b==true){
				return "myelclass_view_front_phone";
			}
			isbindExamroom = studyClassDao.isBindEroom(elclass.getId());
			System.out.println("isbindExamroom===="+isbindExamroom);
			return "myelclass_view_front";
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		System.out.println("b===="+b);
		if(b==true){
			return "myelclass_view_phone";
		}
		isbindExamroom = studyClassDao.isBindEroom(elclass.getId());
		System.out.println("isbindExamroom====>"+isbindExamroom);
		return "myelclass_view";
	}
	
	/**
	 * 单位积分初始化。
	 * @param classid
	 * @throws ElException
	 */
	private void elclass_Unit_recordInit(int classid)throws ElException{  
		
		elclass = classDao.getClassById(elclass.getId());   
		elUser = userDao.getUserById2(getSessionIntValue(ElConstants.SESSION_USERID)); 
		if(elUser.getDepartment().getId() != 1){ 
			unit = departmentDao.getUnitByUserDepid(elUser.getDepartment().getId());//用户所在单位   (只限于2级节点)
			String xiaji = departmentDao.getByIdXiaJi(unit.getId());//下级部门id串 
			xiaji = xiaji.length() > 0 ? xiaji + ","+unit.getId() : unit.getId()+"";
			//初始化最新单位信息
			unitRank = new UnitRanking(elclass,unit);
			unitRank.setPassing(classDao.getElclassDepPassing(unit, elclass));//通过率 
			unitRank.setBasedScore(studyQuizDao.getBasedScore(elclass.getId(), xiaji));		//基础综合得分 ＝ 本单位个人积分总和除以（单位总人数－高级职称人数） 全部修改为新的积分规则基础综合得分＝（本单位个人积分总和－高级职称人数的总积分）除以（单位总人数－高级职称人数）下同
			unitRank.setDegreeScore(studyQuizDao.getDegreeScore(elclass.getId(), xiaji));	//学历层次得分 ＝ 学历积分总和除以（单位总人数－高级职称人数）
			unitRank.setTitleScore(studyQuizDao.getTitleScore(elclass.getId(), xiaji));		//职称级别得分 ＝ 职称积分总和除以（单位总人数－高级职称人数） 
			unitRank.setTotalScore(unitRank.getBasedScore() + unitRank.getDegreeScore() + unitRank.getTitleScore());//总分
			unitRank.setFinalScore(unitRank.getTotalScore() + unitRank.getAddCent());//最终得分 
			if(studyQuizDao.checkUnitRank(elclass.getId(), unit.getId())){//更新
				UnitRanking ur = new UnitRanking();
				ur = studyQuizDao.getUnitRank(elclass.getId(), unit.getId());
				unitRank.setAddCent(ur.getAddCent());//加分
				studyQuizDao.UpdateUnitRank(unitRank);
			}else{//增加记录
				unitRank.setAddCent(0.0f);//加分
				studyQuizDao.insertUnitRank(unitRank);
			}  
		}
	}
	
	/**
	 * 学员查看岗位班级详情
	 * 
	 * @return     
	 * @throws ElException
	 */
	public String mystaelclass_view() throws ElException {
		
		int userid =  getSessionIntValue(ElConstants.SESSION_USERID);
		ELUser user = userDao.getUserById(userid);
		int classid = stationDao.getClassid2(user.getStaid());
		
		myClass = new MyClass(); 
		myClass.setMyCourseB(studyClassDao.listMyClassCourseStat (classid,userid ,CourseConstants.COURSE_STUDY_STATUS_BX ));
		myClass.setMyCourseX(studyClassDao.listMyClassCourseStat (classid, userid ,CourseConstants.COURSE_STUDY_STATUS_XX ));
		myClass.setElClass(classDao.getElClassById(classid));
		//检测是否通过培训班
		studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		if(status==2){
			myClass.setPassed(true);
		}else{
			myClass.setPassed(false);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String now=sdf.format(new Date());
		getRequest().setAttribute("now", now);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "mystaelclass_view_phone"; 
		}

		return "mystaelclass_view";
	}
	
	/**
	 * 管理员查看学员班级详情
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_view() throws ElException {
		int id = elclass.getId();
		int userid = elUser.getId();
		myClass = new MyClass(); 
		myClass.setMyCourseB(studyClassDao.listMyClassCourseStat (id,userid ,CourseConstants.COURSE_STUDY_STATUS_BX ));
		myClass.setMyCourseX(studyClassDao.listMyClassCourseStat (id, userid ,CourseConstants.COURSE_STUDY_STATUS_XX ));
		myClass.setElClass(classDao.getElClassById(elclass.getId()));
		//检测是否通过培训班
		studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		if(status==2){
			myClass.setPassed(true);
		}else{
			myClass.setPassed(false);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String now=sdf.format(new Date());
		getRequest().setAttribute("now", now);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "myelclass_view_phone"; 
		}

		return "myelclass_view";
	}
	/*public String myelclass_view() throws ElException {
		int id = elclass.getId();
		int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID)
				: elUser.getId();
		//int userid=elUser.getId();
		// studyClassDao.autoSetCourse(id,
		// CourseConstants.COURSE_STUDY_STATUS_BX,
		// userid);
		// studyClassDao.autoSetCourse(id,
		// CourseConstants.COURSE_STUDY_STATUS_ZX,
		// userid);
		// studyClassDao.autoSetCourse(id,
		// CourseConstants.COURSE_STUDY_STATUS_XX,
		// userid);

		myClass = new MyClass(); 
		//int roomid = eroomDao.getClassBindingCourseByRoomId(id);
		String roomid = eroomDao.getBindingCourseByRoomId(id,0);
		String sqlBX = "";
		if(courseBX != null && courseBX.getCourseForm() != -1){			
			sqlBX = " and c.courseForm = "+courseBX.getCourseForm();
		}
		myClass.setMyCourseB(studyClassDao.listMyClassCourseStat3(id,userid,roomid,CourseConstants.COURSE_STUDY_STATUS_BX,sqlBX));
		
//		myClass.setMyCourseZ(studyClassDao.listMyClassCourse(id, userid,
//				CourseConstants.COURSE_STUDY_STATUS_ZX));
		roomid = eroomDao.getBindingCourseByRoomId(id,1);  
		myClass.setMyCourseX(studyClassDao.listMyClassCourseStat3(id, userid,roomid,CourseConstants.COURSE_STUDY_STATUS_XX,sqlBX));
		// myClass = studyClassDao.getElClassById(elclass.getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		myClass.setElClass(classDao.getElClassById(elclass.getId()));
		//检测是否通过培训班
		studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		//获取学员-培训班的状态
		int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
		if(status==2){
			myClass.setPassed(true);
		}else{
			myClass.setPassed(false);
		}
		//返回一个当前时间
		//Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String now=sdf.format(new Date());
		getRequest().setAttribute("now", now);
		return "myelclass_view";
	}*/

	/**
	 * 可申请结业培训班
	 * 
	 * @return
	 * @throws ElException
	 */
	public String graduate_applyInit() throws ElException {
		myClasses = studyClassDao
				.listCanGraduateClass(getSessionIntValue(ElConstants.SESSION_USERID));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "graduate_apply_phone"; 
		}

		return "graduate_apply";
	}

	public String myclass_course_result() throws ElException {
		myClass = new MyClass();
		int id = elclass.getId();
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		myClass.setMyCourseB(studyClassDao.listMyClassCourse(id, userid,
				CourseConstants.COURSE_STUDY_STATUS_BX));
		myClass.setMyCourseX(studyClassDao.listMyClassCourse(id, userid,
				CourseConstants.COURSE_STUDY_STATUS_XX));
		
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "myclass_course_result_phone"; 
		}

		return "myclass_course_result";
	}

	/**
	 * 提交培训班结业申请
	 * 
	 * @return
	 * @throws ElException
	 */
	public String graduate_apply() throws ElException {
		studyClassDao
				.graduateClassApplay(
						getSessionIntValue(ElConstants.SESSION_USERID), elclass
								.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "graduate_apply_phone"; 
		}

		return "graduate_apply";
	}
	// 基本信息修改。。。。
	public String mydiploma_result_p() throws ElException {
//		myClasses = studyClassDao
//				.listMyStudyClass(getSessionIntValue(ElConstants.SESSION_USERID));
		myClasses = studyClassDao.listMyGraduatedClass(
				getSessionIntValue(ElConstants.SESSION_USERID),
				ClassConstants.CLASS_APPLY_STATUS_YES,getPageNow(), getPageSize());
		count = studyClassDao.listMyGraduatedClassSize(
				getSessionIntValue(ElConstants.SESSION_USERID),
				ClassConstants.CLASS_APPLY_STATUS_YES);
		
		//培训班关联考场----cisco项目
//		if(myClasses!=null&&myClasses.size()>0){
//			for(int i=0;i<myClasses.size();i++){
//				myClasses.get(i).getElClass().setExamRooms(eroomDao.listExamRoomByClass_cisco(myClasses.get(i).getElClass().getId(),getSessionIntValue(ElConstants.SESSION_USERID)));
//				//获取学员-培训班的状态
//				int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),myClasses.get(i).getElClass().getId());
//				if(status==2){
//					myClasses.get(i).setPassed(true);
//				}else{
//					myClasses.get(i).setPassed(false);
//				}
//			}
//		}
//		if (null != myClasses) {
//			for (int i = 0; i < myClasses.size(); i++) {//检测用户是否通过培训班
//				//由于已经获取证书的 ，要保持现状，而且此处不注掉 每次会证书编号加1（不加不行呀，我改存储过程算了...）
//				studyClassDao.setMyPassclass(
//						getSessionIntValue(ElConstants.SESSION_USERID),
//						myClasses.get(i).getElClass().getId());
//			}
//		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "mydiploma_result_p_phone"; 
		}

		return "mydiploma_result_p";
	}
	public String mydiploma_view() throws ElException {
		elUser=new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
		myClass = studyClassDao.getCraduateClass2(elUser.getId(), elclass.getId());
		 //发证日期（即个人通过考试时间）
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "mydiploma_view_phone"; 
		}
		return "mydiploma_view";
	}
	/**
	 * 管理员查看学员证书
	 * @return
	 * @throws ElException
	 */
	public String diploma_view() throws ElException {
//		 elUser =elUser==null?new ELUser(getSessionIntValue(ElConstants.SESSION_USERID),
//		 getSessionValue(ElConstants.SESSION_REALNAME)):elUser;
		 myClass = studyClassDao.getCraduateClass2(elUser.getId(), elclass.getId());
		 boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "mydiploma_view_phone"; 
			}
		return "mydiploma_view";
	}

	public String mydiploma_result_np() throws ElException {
		myClasses = studyClassDao.listMyGraduatedClass(
				getSessionIntValue(ElConstants.SESSION_USERID),
				ClassConstants.CLASS_APPLY_STATUS_NO);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "mydiploma_result_np_phone"; 
		}
		return "mydiploma_result_np";
	}
	/**
	 * 查看学员概况信息
	 * @return
	 * @throws ElException
	 */
	public String studyOverviewInfo() throws ElException {
		int userid= elUser.getId();
		elUser = userDao.getUserById(userid);
		int eroom_ok = 0;
		int eroom_all = 0;
		int class_yes = 0;
		int class_all = 0;
		// 未开始的考场，全部考场
		eroom_ok = studyQuizDao
				.getEroomPassedCount(userid);
		getRequest().setAttribute("eroom_ok", eroom_ok);
		eroom_all = studyQuizDao
				.getEroomAllCount(userid);
		getRequest().setAttribute("eroom_all", eroom_all);
		// 已结业培训班，全部培训班
		class_yes = studyClassDao
				.getClassYesCount(userid);
		getRequest().setAttribute("class_yes", class_yes);
		class_all = studyClassDao
				.getClassAllCount(userid);
		getRequest().setAttribute("class_all", class_all);
		//培训班信息
		myClasses = studyClassDao.listMyGraduatedClass(userid,
				ClassConstants.CLASS_APPLY_STATUS_YES);
//		if (null != myClasses) {
//			for (int i = 0; i < myClasses.size(); i++) {//检测用户是否通过培训班
//				studyClassDao.setMyPassclass(userid,myClasses.get(i).getElClass().getId());
//			}
//		}
		//考场信息
		myrooms = studyQuizDao.listErsWithoutC(userid, 0,999999,1);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "mydiploma_result_np_phone"; 
		}
		return "studyOverviewInfo";
	}
	
	/**
	 * 个人学籍
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	public String findenrollment() throws ElException {
		int userid= elUser!=null?elUser.getId():getSessionIntValue(ElConstants.SESSION_USERID);
		//证书列表
		myClasses = studyClassDao.listMyGraduatedClass(userid,
				ClassConstants.CLASS_APPLY_STATUS_YES);
		//考试成绩
		myrooms = studyQuizDao.listErsWithoutC(userid, 999999,1);
//		if (null != myClasses) {
//			for (int i = 0; i < myClasses.size(); i++) {//检测用户是否通过培训班
//				studyClassDao.setMyPassclass(userid,myClasses.get(i).getElClass().getId());
//			}
//		}
		this.setListLineTrainrecord(linetrainDao.findRecordList(userid,4));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "findenrollment_phone"; 
		}
		return "findenrollment";
	}
	/**
	 * 管理员查看学员学籍
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	public String findenrollment_info() throws ElException {
		int userid= elUser.getId();
		//证书列表
		myClasses = studyClassDao.listMyGraduatedClass(userid,
				ClassConstants.CLASS_APPLY_STATUS_YES);
		//考试成绩
		myrooms = studyQuizDao.listErsWithoutC(userid, 999999,1);
		
		this.setListLineTrainrecord(linetrainDao.findRecordList(userid,4));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "findenrollment_info_phone"; 
		}
		return "findenrollment_info";
	}
	
	/**
	 *  查询有权限的人员完成培训班数量，通过考试数量，线下培训记录数
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	public String schoolrolls()throws ElException {
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//		int depid=this.getDeptid()!=null?this.getDeptid():getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
//		if(elUser==null){
//			this.elUser=new ELUser();
//			if(depid>0){
//				Department department=new Department();
//				department.setId(depid);
//				elUser.setDepartment(department);
//			}
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
		//listSchoolrolls = linetrainDao.getSchoolrollsList(this.getDeptid()!=null?this.getDeptid():getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),getPageNow(), getPageSize());
		//count = linetrainDao.getSchoolrollsListSize(this.getDeptid()!=null?this.getDeptid():getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
		
		
//		listSchoolrolls = linetrainDao.getSchoolrollsList(0,elUser,getPageNow(), getPageSize());
//		count = linetrainDao.getSchoolrollsSize(0,elUser);
		if(this.getDeptid()==null||this.getDeptid()<=0){
			department = depTree;
		}else{
			department  = departmentDao.getDepById(this.getDeptid());
		}
		sub_department = elUser == null? 1 : sub_department;
		listSchoolrolls = linetrainDao.getSchoolrollsList(department,elUser,sub_department,getPageNow(), getPageSize());
		count = linetrainDao.getSchoolrollsSize(department,elUser,sub_department);
		getRequest().setAttribute("findRecordCount", linetrainDao.findRecordListCount(getSessionIntValue(ElConstants.SESSION_USERID),4));
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "schoolrolls_phone"; 
		}
		return "schoolrolls";
	}
	/**
	 *  添加线下培训记录初始化
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	public String lineTrainRecord_addInt()throws ElException{
		if(this.getLinetrainrecord()!=null&&this.getLinetrainrecord().getTrainid()!=null){
			this.setLinetrainrecord(linetrainDao.findRecordByIds(this.getLinetrainrecord().getTrainid()));
			//获取线下培训附件集合
			this.getLinetrainrecord().setLineTrainRecordStuffs(linetrainDao.listRecordStuffByTrainid(this.getLinetrainrecord().getTrainid()));
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "lineTrainRecord_addInt_phone"; 
		}
		return "lineTrainRecord_addInt";
	}
	
	/**
	 *  保存线下培训记录
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	public String lineTrainRecord_add()throws ElException{
		if(this.getLinetrainrecord().getTrainid()!=null){
			this.setMessage("培训记录修改成功！");
		}else{
			this.setMessage("培训记录添加成功！");
		}
		this.getLinetrainrecord().setCreateuserid(getSessionIntValue(ElConstants.SESSION_USERID));
		LineTrainRecord record=linetrainDao.saveUpdateRecord(this.getLinetrainrecord());
		LineTrainRecordStuff recordStuff=null;
		if(myFiles!=null){
			for (int i = 0; i < myFiles.length; i++) {
				recordStuff=this.getLinetrainrecord().getLineTrainRecordStuffs().get(i);
				recordStuff.setLineTrainRecord(record);
				recordStuff.setStuffAddr(J2EEFileUtil.getExtention(myFileFileNames[i]));//先传入扩展名
				record.getLineTrainRecordStuffs().set(i, linetrainDao.saveUpdateRecordStuff(recordStuff));
				try {
					J2EEFileUtil.upload(myFiles[i],J2EEFileUtil.getExtention(myFileFileNames[i]), "stufffiles/"+getSessionIntValue(ElConstants.SESSION_USERID), record.getLineTrainRecordStuffs().get(i).getId()+"");
				} catch (Exception e) {
					logger.error("线下培训文件上传错误",e);
				}
			}
		}
		this.setLinetrainrecord(record);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "lineTrainRecord_add_phone"; 
		}
		return "lineTrainRecord_add";
	}
	/**
	 * 线下培训记录附件删除
	 * @return
	 * @throws ElException
	 */
	public String lineTrainRecordStuff_delete() throws ElException{
		lineTrainRecordStuff=linetrainDao.getLineTrainRecordStuffById(lineTrainRecordStuff.getId());
		//删除文件
		try {
			J2EEFileUtil.deleteFile("stufffiles/"+getSessionIntValue(ElConstants.SESSION_USERID), lineTrainRecordStuff.getId()+"", J2EEFileUtil.getExtention(lineTrainRecordStuff.getStuffAddr()));
			//删除数据
			linetrainDao.deleteLineTrainRecordStuffById(lineTrainRecordStuff.getId());
			this.setMessage("删除成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("线下培训删除文件错误",e);
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "lineTrainRecord_addInt_phone"; 
		}
		return "lineTrainRecord_addInt";
	}
	/**
	 *  线下培训记录列表
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	public String lineTrainRecord_list()throws ElException{
		this.setListLineTrainrecord(linetrainDao.findRecordList(getSessionIntValue(ElConstants.SESSION_USERID),null));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "lineTrainRecord_list_phone"; 
		}
		return "lineTrainRecord_list";
	}
	/**
	 * 删除线下培训记录
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	public String lineTrainRecord_delete()throws ElException{
		linetrainDao.deleteRecord(this.getIds());
		this.setListLineTrainrecord(linetrainDao.findRecordList(getSessionIntValue(ElConstants.SESSION_USERID),null));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "lineTrainRecord_list_phone"; 
		}
		return "lineTrainRecord_list";
	}
	/**
	 * 管理员查看学员线下培训信息
	 * @return
	 * @throws ElException
	 */
	public String lineTrainRecordLook()throws ElException{
		if(this.getLinetrainrecord()!=null&&this.getLinetrainrecord().getTrainid()!=null){
			this.setLinetrainrecord(linetrainDao.findRecordByIds(this.getLinetrainrecord().getTrainid()));
			//获取线下培训附件集合
			this.getLinetrainrecord().setLineTrainRecordStuffs(linetrainDao.listRecordStuffByTrainid(this.getLinetrainrecord().getTrainid()));
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "lineTrainRecordInfo_phone"; 
		}
		return "lineTrainRecordInfo";
	}
	/**
	 * 线下培训信息附件下载
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String lineTrainRecordStuffDownload() throws ElException, Exception {
		try {
			lineTrainRecordStuff=linetrainDao.getLineTrainRecordStuffById(lineTrainRecordStuff.getId());
			linetrainrecord=linetrainDao.findRecordByIds(lineTrainRecordStuff.getLineTrainRecord().getTrainid());
			downFileName = new String(lineTrainRecordStuff.getStuffAddr().getBytes(), "ISO8859-1");
			String FileName="stufffiles/"+linetrainrecord.getCreateuserid()+"/"+lineTrainRecordStuff.getStuffAddr();
			String path = ServletActionContext.getServletContext().getRealPath(FileName);
			try {
				inputStream = new FileInputStream(path);
			} catch (Exception e) {
				// logger.error("文档下载失败", e);
				throw new ElException("下载素材出错", e);
			}
		} catch (Exception e) {
			// logger.error("文档下载失败", e);
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "fileDownload_phone"; 
		}
		return "fileDownload";
	}
	/**
	 * 申请，通过，不通过
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	public String updateState()throws ElException{
		linetrainDao.updateState(this.getIds(), this.getState());
		//如果是申请 怎返回申请列表
		if(this.getState()==2){
			this.setListLineTrainrecord(linetrainDao.findRecordList(getSessionIntValue(ElConstants.SESSION_USERID),null));
			return lineTrainRecord_list();
		}else{
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//			this.setListLineTrainrecord(linetrainDao.findManagementRecordList(this.getDeptid()!=null?this.getDeptid():getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),2,getPageNow(), getPageSize()));
//			count=linetrainDao.findManagementRecordListSize(this.getDeptid()!=null?this.getDeptid():getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), 2);
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
						true);
			else {
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
						true);
			}
			if(this.getDeptid()==null||this.getDeptid()<=0){
				department = depTree;
			}else{
				department  = departmentDao.getDepById(this.getDeptid());
			}
			this.setListLineTrainrecord(linetrainDao.findManagementRecordList(department,2,getPageNow(), getPageSize()));
			count=linetrainDao.findManagementRecordListSize(department, 2);
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "linetrainingmanagement_phone"; 
			}
			return "linetrainingmanagement";
		}
	}
	
	/**
	 * 线下培训管理
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	public String linetrainingmanagement() throws ElException{
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(this.getDeptid()==null||this.getDeptid()<=0){
			department = depTree;
		}else{
			department  = departmentDao.getDepById(this.getDeptid());
		}
//		this.setListLineTrainrecord(linetrainDao.findManagementRecordList(this.getDeptid()!=null?this.getDeptid():getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),2,getPageNow(), getPageSize()));
//		count=linetrainDao.findManagementRecordListSize(this.getDeptid()!=null?this.getDeptid():getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), 2);
		this.setListLineTrainrecord(linetrainDao.findManagementRecordList(department,2,getPageNow(), getPageSize()));
		count=linetrainDao.findManagementRecordListSize(department, 2);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "linetrainingmanagement_phone"; 
		}
		return "linetrainingmanagement";
	}

	/**
	 * 培训班是否满足申请要求
	 * @author  
	 * @return
	 * @throws ElException
	 */
	public boolean checkIsuserApp(ElClass elclass , ELUser eluser)throws ElException{  
		boolean IsuserApp =true;
		boolean jz =true;
		boolean ds =true;
		boolean zj =true;
		boolean zw =true;
		boolean gw =true;
		boolean nl =true;
		boolean xb =true;
		boolean bm =true;
		boolean erooms =true;
		boolean elClass =true;
		boolean eroomeps = true;
		explain = new StringBuffer();
		if(elclass.getElRegistration().getDslist()==null){//地市不限
			ds =true;
		}else{
			if(elclass.getElRegistration().getDslist()!=null&&elUser.getDishi()>0&&elclass.getElRegistration().getDslist().contains(elUser.getDishi()+"")){ 
				ds = true;//dslist不为空 uds不为空  dslist 里没有该地市
			}else{
				 explain.append("地市 ");
				 dishiIspass=1;
				 sumIspass=-1;
				ds = false;//dslist不为空 uds为空 或者 dslist 里没有该地市
			}	
		}
		if(elclass.getElRegistration().getJzlist()== null){
			jz =true;//不限
		}else{
			if(elclass.getElRegistration().getJzlist()!= null && elUser.getJingzhong()>0 && elclass.getElRegistration().getJzlist().contains(elUser.getJingzhong()+"")){
				jz = true;
			}else{
				 explain.append("警种 ");
				 jingzhongIspass=1;
				 sumIspass=-1;
				jz = false;
			}
		}
		if(elclass.getElRegistration().getZjlist()==null){
			zj =true;//不限
		}else{
			if(elclass.getElRegistration().getZjlist()!=null&&elUser.getZhiji()>0&&elclass.getElRegistration().getZjlist().contains(elUser.getZhiji()+"")){
				zj = true;
			}else{
				explain.append("职级 ");
				zhijiIspass=1;
				sumIspass=-1;
				zj = false;
			}	
		}
		if(elclass.getElRegistration().getZwlist()==null){
			zw =true;//不限
		}else{
			if(elclass.getElRegistration().getZwlist()!=null&&elUser.getZhiwu()>0&&elclass.getElRegistration().getZwlist().contains(elUser.getZhiwu()+"")){
				zw = true;
			}else{
				explain.append("职务 ");
				zhiwuIspass=1;
				sumIspass=-1;
				zw = false;
			}
		}
		if(elclass.getElRegistration().getGwlist()==null){
			gw = true;
		}else{			
			if(elclass.getElRegistration().getGwlist()!=null&&elUser.getGangwei()!=null&&elclass.getElRegistration().getGwlist().contains(elUser.getGangwei())){
				gw = true;
			}else{
				explain.append("岗位 ");
				gw = false;	
			}
		}
		//年龄段
		if(elclass.getElRegistration().getStartAge() == 0 && elclass.getElRegistration().getStopAge() == 0){
			nl = true;
		}else{
			if(eluser.getAGE()>elclass.getElRegistration().getStartAge() && elclass.getElRegistration().getStopAge()>eluser.getAGE()){
				nl = true;
			}else{
				explain.append("年龄 ");
				ageIspass=1;
				sumIspass=-1;
				nl = false;	
			}			
		}
		//性别
		if("不限".equals(elclass.getElRegistration().getSex())){
			xb = true;
		}else if(eluser.getSex().equals(elclass.getElRegistration().getSex())){
			xb = true;
		}else{
			explain.append("性别 ");
			sexIspass=1;
			sumIspass=-1;
			xb = false;	
		}
		 
		//部门 
		if(elclass.getElRegistration().getTreeType()==null){//部门不限
			bm =true;
		}else{
//			if(elclass.getElRegistration().getTreeTypes()!=null&&
//					elUser.getDepartment()!=null&&
//					elclass.getElRegistration().getTreeTypelist().contains(elUser.getDepartment().getId()+"")){     
//				bm = true; 
//			}else{
//				explain.append("部门 ");
//				depIspass=1;
//				sumIspass=-1;
//				bm = false; 
//			}
			if(elclass.getElRegistration().getTreeTypes()!=null&&
					elUser.getDepartment()!=null&&
					userDao.checkUserIsInDep(elUser.getId(), elclass.getElRegistration().getTreeType())){     
				bm = true;
			}else{
				explain.append("部门 ");
				depIspass=1;
				sumIspass=-1;
				bm = false;
			}
		}
		//考场 
//		if(elclass.getElRegistration().getExamRoomids() == null || elclass.getElRegistration().getExamRoomids().equals("")){//考场不限
//			erooms =true;
//		}else{
//
//			String sqlWhere = "";
//			if(elclass.getElRegistration().getEroomScreeningWay()==1){
//				sqlWhere = " and ispassed  = 1";
//			}else if(elclass.getElRegistration().getEroomScreeningWay()==2) {
//				sqlWhere = " and ispassed  = 0";
//			}
//			if(!elclass.getElRegistration().getExamRoomids().equals("")&& 
//					eroomDao.checkEroomIspassed(elclass.getElRegistration().getExamRoomids(), getSessionIntValue(ElConstants.SESSION_USERID),sqlWhere)){ 
//				erooms = true; 
//			}else{
//				explain.append("考场");
//				eroomIspass=1;
//				sumIspass=-1;
//				erooms = false; 
//			}	
//		}
		erooms = elclass.getElRegistration().checkErpapspassed(elUser.getId());
		if(!erooms){
			explain.append("考场");
			eroomIspass = 1;
			sumIspass = -1;
		}
		eroomeps = elclass.getElRegistration().checkEreppapspassed(elUser.getId());
		if(!eroomeps){
			explain.append("考场试卷");
			eroomepIspass = 1;
			sumIspass = -1;
		}
		//培训班 
//		if(elclass.getElRegistration().getElclasss()==null || elclass.getElRegistration().getElclasss().size() == 0){//培训班不限
//			elClass = true;
//		}else{ 
//			String sqlWhere = "";
//			if(elclass.getElRegistration().getClassScreeningWay()==1){
//				sqlWhere = "and certificateno is not null";
//			}else if(elclass.getElRegistration().getClassScreeningWay()==2) {
//				sqlWhere = "and certificateno is null";
//			}
//			if(!elclass.getElRegistration().getElclassids().equals("")&& 
//					eroomDao.checkElclassIspassed(elclass.getElRegistration().getElclassids(), getSessionIntValue(ElConstants.SESSION_USERID),sqlWhere)){ 
//				elClass = true; 
//			}else{
//				explain.append("培训班");
//				classIspass=1;
//				sumIspass=-1;
//				elClass = false; 
//			}	
//		}
		elClass = elclass.getElRegistration().checkClasspapspassed(elUser.getId());
		if(!elClass){
			explain.append("培训班");
			classIspass = 1;
			sumIspass = -1;
		}
		if(jz&&ds&&zj&&zw&&gw&&nl&&xb&&bm&&erooms&&eroomeps&&elClass){ //  
			IsuserApp = true;
		}else{
			IsuserApp = false;
		}
		return IsuserApp;
	}
	
	/*
	 * 删除
	 */
	public String deleteCourse() throws ElException
	{
		int classid=elclass.getId();
		int userid=getSessionIntValue((ElConstants.SESSION_USERID));
		int courseid=course.getId();
		studyClassDao.delete_CLASS_COURSE_AT(classid, userid, courseid);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "deleteCourseSuccess_phone"; 
		}
		return "deleteCourseSuccess";
	}
	/**
	 * 获取培训班没拿证的详细信息
	 * @return
	 * @throws ElException
	 */
	public String classNoPassRemack() throws ElException{
		myClass = studyClassDao.getStudyClassNoPassRemack(getSessionIntValue(ElConstants.SESSION_USERID), elclass.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "classNoPassRemack_phone"; 
		}
		return "classNoPassRemack";
	}
	
	//选择培训班
	
	public String changeElclass() throws ElException{
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		MyClass new_cla_ = studyClassDao.getNaZhengClass(year);
		MyClass nianjian_cla_ = studyClassDao.getNianjianClass(year);
		new_cla = new_cla_;
		nianjian_cla = nianjian_cla_;
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "changeElclass_phone"; 
		}
		return "changeElclass";
	}
	
	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public List<ElClass> getElclasses() {
		return elclasses;
	}

	public void setElclasses(List<ElClass> elclasses) {
		this.elclasses = elclasses;
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

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public StringBuffer getExplain() {
		return explain;
	}

	public void setExplain(StringBuffer explain) {
		this.explain = explain;
	}

	public Course getCourseBX() {
		return courseBX;
	}

	public void setCourseBX(Course courseBX) {
		this.courseBX = courseBX;
	}

	public Course getCourseXX() {
		return courseXX;
	}

	public void setCourseXX(Course courseXX) {
		this.courseXX = courseXX;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public StationDao getStationDao() {
		return stationDao;
	}

	public void setStationDao(StationDao stationDao) {
		this.stationDao = stationDao;
	}

	public List<MyCourse> getStudyCourseList() {
		return studyCourseList;
	}

	public void setStudyCourseList(List<MyCourse> studyCourseList) {
		this.studyCourseList = studyCourseList;
	}
 
	
	public MyClass getNew_cla() {
		return new_cla;
	}

	public void setNew_cla(MyClass new_cla) {
		this.new_cla = new_cla;
	}
	public MyClass getNianjian_cla() {
		return nianjian_cla;
	}
	public void setNianjian_cla(MyClass nianjian_cla) {
		this.nianjian_cla = nianjian_cla;
	}

}
