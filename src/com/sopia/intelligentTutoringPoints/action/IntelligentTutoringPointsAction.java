package com.sopia.intelligentTutoringPoints.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.anychart.JsonUtil;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.office.ExcelOutPut;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.intelligentTutoringPoints.IntelligentTutoringPointsUtil;
import com.sopia.intelligentTutoringPoints.dao.IntelligentTutoringPointsDao;
import com.sopia.intelligentTutoringPoints.entities.IntelligentAcademic;
import com.sopia.intelligentTutoringPoints.entities.IntelligentAcademicCourse;
import com.sopia.intelligentTutoringPoints.entities.IntelligentClass;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLearnWeek;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLogin;
import com.sopia.intelligentTutoringPoints.entities.IntelligentProportion;
import com.sopia.intelligentTutoringPoints.entities.IntelligentRecoding;
import com.sopia.intelligentTutoringPoints.entities.IntelligentTutoringPoints;
import com.sopia.peixunBatch.dao.PeixunBatchDao;
import com.sopia.peixunBatch.entities.PeixunBatch;
import com.sopia.statman.entities.MyClass;
import com.sopia.wjm.dao.ClassificationDao;
import com.sopia.wjm.entities.Classification;


/**
 * 智能辅导分Action
 * @author TMK
 *
 */
public class IntelligentTutoringPointsAction extends BaseAction{
	private static final Log logger = LogFactory.getLog(IntelligentTutoringPointsAction.class);
	private IntelligentTutoringPointsDao intelligentTutoringPointsDao;
	private PeixunBatchDao peixunBatchDao;
	private ClassDao classDao;
	private Department searchDep;
	private ELUser searchUser;
	private List<ELUser> elUsers;
	private int count;
	private ELUser elUser;
	private IntelligentTutoringPoints intelligentTutoringPoints;
	private ElClass elclass;
	private boolean exprot;
	
	private PeixunBatch peixunBatch;
	private boolean inDingjiRoom;
	private List<MyClass> myclasses;
	private List<IntelligentLogin>  loginInfos;
	private int loginDays;
	private List<IntelligentLearnWeek> weekInfos;
	private String weekTime;
	private float hour;
	private ElClass elClass;
	private IntelligentProportion proportion;
	private IntelligentRecoding recoding;
	private List<IntelligentAcademic> academicInfos;
	private List<IntelligentAcademicCourse> academicCourseInfos;
	private Course course;
	private CoursePage coursePage;
	private List<Classification> classifications;
	private ClassificationDao classificationDao;
	private CourseDao courseDao;
	private List<Course> courses;
	private List<CoursePage> coursePages;
	private int beginWeek ;
	
	private String json ;
	
	/**
	 * 智能辅导分统计
	 * @return
	 * @throws ElException
	 */
	public String intelligentStatistics() throws ElException{
		PeixunBatch peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		ElClass elclass = null;
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=user.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			elUsers = intelligentTutoringPointsDao.intelligentUsers(searchDep,searchUser,-1,-1);
			if(elUsers!=null){
				for(ELUser user:elUsers){
					//获取当前正在学习的等级
					elclass = peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),user.getId(),0);
					if(elclass!=null && elclass.getId()>0){
						user.setNowClass(elclass);
						//获取智能辅导分
						user.setIntelligentPoints(IntelligentTutoringPointsUtil.intelligentTutoringPoints(user.getId()));
					}
				}
			}
			try {
				String titles[] = { "部门", "姓名",
						"性别", "当前级别", "智能辅导分" };
				String attrs[] = { "department.name", "realname", "sex", "nowClass.name",
						"intelligentPoints" };
				new ExcelOutPut().writeExcel("用户智能辅导分", getResponse()
						.getOutputStream(), titles, ELUser.class.getName(),
						elUsers, attrs);
			} catch (Exception e) {
				logger.error("导出用户智能辅导分Excel错误", e);
			}
			return null;
		}
		if (searchDep != null && searchDep.getId() > 0) {
			searchDep = departmentDao.getDepById(searchDep.getId());
		}
			
		elUsers = intelligentTutoringPointsDao.intelligentUsers(searchDep,searchUser,getPageNow(),getPageSize());
		count = intelligentTutoringPointsDao.intelligentCount(searchDep,searchUser,getPageNow(),getPageSize());
		elclass = null;
		if(elUsers!=null){
			for(ELUser user:elUsers){
				//获取当前正在学习的等级
				elclass = peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),user.getId(),0);
				if(elclass!=null && elclass.getId()>0){
					user.setNowClass(elclass);
					//获取智能辅导分
					user.setIntelligentPoints(IntelligentTutoringPointsUtil.intelligentTutoringPoints(user.getId()));
				}
			}
		}
		try {
			json = JsonUtil.simpleListToJsonStr(elUsers, null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return "intelligentStatistics";
	}
	
	/**
	 * 查看个人智能辅导分
	 * @return
	 * @throws ElException
	 */
	public String viewIntelligentByUserid() throws ElException{
		if(elUser == null || elUser.getId()<=0 || elclass==null || elclass.getId()<=0){
			this.setElmessage("参数错误");
			return "error";
		}
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		//设置完成和正在学习的培训班信息
		peixunBatch.setNowClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),0));
		peixunBatch.setDoneClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),1));
		intelligentTutoringPoints = intelligentTutoringPointsDao.getDifferentPoints(elUser.getId(),elclass.getId());
		return "viewIntelligentByUserid";
	}
	
	/**
	 * 登录详情
	 * @return
	 * @throws ElException
	 */
	public String loginInfo() throws ElException{
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		loginInfos = intelligentTutoringPointsDao.getLoginInfos(elUser.getId(),elClass.getId(),getPageNow(),getPageSize());
		count = intelligentTutoringPointsDao.getLoginInfosCount(elUser.getId(),elClass.getId());
		loginDays = intelligentTutoringPointsDao.getLoginInfosDays(elUser.getId(),elClass.getId());
		intelligentTutoringPoints = intelligentTutoringPointsDao.getDifferentPoints(elUser.getId(),elClass.getId());
		return "loginInfo";
	}

	/**
	 * 周学习时间详情
	 * @return
	 * @throws ElException
	 */
	public String weekInfo() throws ElException{
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		weekInfos = intelligentTutoringPointsDao.getWeekInfos(elUser.getId(),elClass.getId(),getPageNow(),getPageSize());
		count = intelligentTutoringPointsDao.getWeekInfosCount(elUser.getId(),elClass.getId());
		beginWeek = getPageSize() - 1;
		double weekSeconds = 0.00;
		//获取周学习时间  秒
		if(weekInfos!=null ){
			for(IntelligentLearnWeek week:weekInfos){
				weekSeconds += week.getLearnTime();
			}
		}
		if(weekSeconds<60){
			//小于1分钟显示秒（保留两位小数）
			weekTime = new DecimalFormat("#.00").format(weekSeconds) + " 秒";
		}else{
			if(weekSeconds<3600){
				//小于1小时显示分（保留两位小数）
				weekSeconds = (double) (Math.round(weekSeconds / 60 * 100)/100.0);
				weekTime = weekSeconds + " 分";
			}else{
				//大于1小时显示小时（保留两位小数）
				weekSeconds = (double) (Math.round(weekSeconds / 3600 * 100)/100.0);
				weekTime = weekSeconds + " 小时";
			}
		}
		intelligentTutoringPoints = intelligentTutoringPointsDao.getDifferentPoints(elUser.getId(),elClass.getId());
		return "weekInfo";
	}
	
	/**
	 * 等级学习时间详情
	 * @return
	 * @throws ElException
	 */
	public String classInfo() throws ElException{
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		loginInfos = intelligentTutoringPointsDao.getLoginInfos(elUser.getId(),elClass.getId(),getPageNow(),getPageSize());
		count = intelligentTutoringPointsDao.getLoginInfosCount(elUser.getId(),elClass.getId());
		hour = intelligentTutoringPointsDao.getClassInfoHour(elUser.getId(),elClass.getId());
		intelligentTutoringPoints = intelligentTutoringPointsDao.getDifferentPoints(elUser.getId(),elClass.getId());
		return "classInfo";
	}
	
	/**
	 * 复听数量
	 * @return
	 * @throws ElException
	 */
	public String proportionQInfo() throws ElException{
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		proportion = intelligentTutoringPointsDao.getProportion(elUser.getId(),elClass.getId());
		return "proportionQInfo";
	}
	/**
	 * 复听次数
	 * @return
	 * @throws ElException
	 */
	public String proportionTInfo() throws ElException{
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		proportion = intelligentTutoringPointsDao.getProportion(elUser.getId(),elClass.getId());
		return "proportionTInfo";
	}
	/**
	 * 录音数量
	 * @return
	 * @throws ElException
	 */
	public String recodingQInfo() throws ElException{
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		recoding = intelligentTutoringPointsDao.getRecoding(elUser.getId(),elClass.getId());
		return "recodingQInfo";
	}
	/**
	 * 录音次数
	 * @return
	 * @throws ElException
	 */
	public String recodingTInfo() throws ElException{
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		recoding = intelligentTutoringPointsDao.getRecoding(elUser.getId(),elClass.getId());
		return "recodingTInfo";
	}
	/**
	 * 模块智能辅导分
	 * @return
	 * @throws ElException
	 */
	public String academicInfo() throws ElException{
		//获取等级列表
		classifications = classificationDao.list_classification();
		if(classifications!=null){
			for(Classification c:classifications){
				c.setElClass(classDao.getClassByName(c.getName()));
			}
		}
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		course = course == null?new Course(-1):course;
		coursePage = coursePage == null?new CoursePage(-1):coursePage;
		academicInfos = intelligentTutoringPointsDao.getAcademicInfos(elUser.getId(),elClass.getId(),getPageNow(),getPageSize(),course,coursePage);
		count = intelligentTutoringPointsDao.getAcademicInfosCount(elUser.getId(),elClass.getId(),course,coursePage);
		return "academicInfo";
	}
	/**
	 * 单元智能辅导分
	 * @return
	 * @throws ElException
	 */
	public String academicCourseInfo() throws ElException{
		//获取等级列表
		classifications = classificationDao.list_classification();
		if(classifications!=null){
			for(Classification c:classifications){
				c.setElClass(classDao.getClassByName(c.getName()));
			}
		}
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		course = course == null?new Course(-1):course;
		academicCourseInfos = intelligentTutoringPointsDao.getAcademicCourseInfos(elUser.getId(),elClass.getId(),getPageNow(),getPageSize(),course,coursePage);
		count = intelligentTutoringPointsDao.getAcademicCourseInfosCount(elUser.getId(),elClass.getId(),course,coursePage);
		return "academicCourseInfo";
	}
	
	public String createSelect2Elements() throws ElException{
		courses = courseDao.getCoursesByClassid(elClass.getId());
		return "createSelect2Elements";
	}
	
	public String createSelect3Elements() throws ElException{
		coursePages = courseDao.getPagesByCourseid(course.getId());
		return "createSelect3Elements";
	}
	
	//gets  AND   sets
	
	public IntelligentTutoringPointsDao getIntelligentTutoringPointsDao() {
		return intelligentTutoringPointsDao;
	}

	public void setIntelligentTutoringPointsDao(
			IntelligentTutoringPointsDao intelligentTutoringPointsDao) {
		this.intelligentTutoringPointsDao = intelligentTutoringPointsDao;
	}
	public Department getSearchDep() {
		return searchDep;
	}
	public void setSearchDep(Department searchDep) {
		this.searchDep = searchDep;
	}
	public ELUser getSearchUser() {
		return searchUser;
	}
	public void setSearchUser(ELUser searchUser) {
		this.searchUser = searchUser;
	}
	public List<ELUser> getElUsers() {
		return elUsers;
	}
	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public PeixunBatchDao getPeixunBatchDao() {
		return peixunBatchDao;
	}
	public void setPeixunBatchDao(PeixunBatchDao peixunBatchDao) {
		this.peixunBatchDao = peixunBatchDao;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public IntelligentTutoringPoints getIntelligentTutoringPoints() {
		return intelligentTutoringPoints;
	}

	public void setIntelligentTutoringPoints(
			IntelligentTutoringPoints intelligentTutoringPoints) {
		this.intelligentTutoringPoints = intelligentTutoringPoints;
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

	public boolean isExprot() {
		return exprot;
	}

	public void setExprot(boolean exprot) {
		this.exprot = exprot;
	}

	public PeixunBatch getPeixunBatch() {
		return peixunBatch;
	}

	public void setPeixunBatch(PeixunBatch peixunBatch) {
		this.peixunBatch = peixunBatch;
	}

	public boolean isInDingjiRoom() {
		return inDingjiRoom;
	}

	public void setInDingjiRoom(boolean inDingjiRoom) {
		this.inDingjiRoom = inDingjiRoom;
	}

	public List<MyClass> getMyclasses() {
		return myclasses;
	}

	public void setMyclasses(List<MyClass> myclasses) {
		this.myclasses = myclasses;
	}

	public List<IntelligentLogin> getLoginInfos() {
		return loginInfos;
	}

	public void setLoginInfos(List<IntelligentLogin> loginInfos) {
		this.loginInfos = loginInfos;
	}

	public ElClass getElClass() {
		return elClass;
	}

	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}

	public List<IntelligentLearnWeek> getWeekInfos() {
		return weekInfos;
	}

	public void setWeekInfos(List<IntelligentLearnWeek> weekInfos) {
		this.weekInfos = weekInfos;
	}

	public int getLoginDays() {
		return loginDays;
	}

	public void setLoginDays(int loginDays) {
		this.loginDays = loginDays;
	}



	public String getWeekTime() {
		return weekTime;
	}

	public void setWeekTime(String weekTime) {
		this.weekTime = weekTime;
	}

	public float getHour() {
		return hour;
	}

	public void setHour(float hour) {
		this.hour = hour;
	}

	public IntelligentProportion getProportion() {
		return proportion;
	}

	public void setProportion(IntelligentProportion proportion) {
		this.proportion = proportion;
	}

	public IntelligentRecoding getRecoding() {
		return recoding;
	}

	public void setRecoding(IntelligentRecoding recoding) {
		this.recoding = recoding;
	}

	public List<IntelligentAcademic> getAcademicInfos() {
		return academicInfos;
	}

	public void setAcademicInfos(List<IntelligentAcademic> academicInfos) {
		this.academicInfos = academicInfos;
	}

	public List<IntelligentAcademicCourse> getAcademicCourseInfos() {
		return academicCourseInfos;
	}

	public void setAcademicCourseInfos(
			List<IntelligentAcademicCourse> academicCourseInfos) {
		this.academicCourseInfos = academicCourseInfos;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CoursePage getCoursePage() {
		return coursePage;
	}

	public void setCoursePage(CoursePage coursePage) {
		this.coursePage = coursePage;
	}

	public List<Classification> getClassifications() {
		return classifications;
	}

	public void setClassifications(List<Classification> classifications) {
		this.classifications = classifications;
	}

	public ClassificationDao getClassificationDao() {
		return classificationDao;
	}

	public void setClassificationDao(ClassificationDao classificationDao) {
		this.classificationDao = classificationDao;
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

	public List<CoursePage> getCoursePages() {
		return coursePages;
	}

	public void setCoursePages(List<CoursePage> coursePages) {
		this.coursePages = coursePages;
	}

	public int getBeginWeek() {
		return beginWeek;
	}

	public void setBeginWeek(int beginWeek) {
		this.beginWeek = beginWeek;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
}
