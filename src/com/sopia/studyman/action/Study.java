package com.sopia.studyman.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.attendance.dao.AttendanceDao;
import com.sopia.attendance.entity.WorkAttendance;
import com.sopia.classman.ClassConstants;
import com.sopia.common.ElException;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.IndexSystemConfigOp;
import com.sopia.common.JTM;
import com.sopia.common.JTMSystemConfOp;
import com.sopia.common.MD5;
import com.sopia.common.ScoreOperate;
import com.sopia.common.ScoreSet;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.impl.StationDaoImpl;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;
import com.sopia.newsandmess.dao.MessageDao;
import com.sopia.newsandmess.dao.impl.NewsDaoImpl;
import com.sopia.newsandmess.entities.Message;
import com.sopia.newsandmess.entities.News;
import com.sopia.pfms.dao.BaoxianProductDao;
import com.sopia.pfms.dao.PfmsNewsDao;
import com.sopia.pfms.entities.BaoxianProduct;
import com.sopia.schedule.dao.ScheduleGlobleDao;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;

public class Study extends BaseAction {
	private ELUser elUser;
	private StudyCourseDao studyCourseDao;
	private StudyQuizDao studyQuizDao;
	private PfmsNewsDao pfmsNewsDao;
	private BaoxianProductDao baoxianProductDao;
	private List<MyExamPaper> myExamPapers;
	private List<MyCourse> myCourses;
	private Department depTree;
	private List<Message> newMessage;
	private MessageDao messageDao;
	private List<MyRoom> myrooms;
	private List<MyRoom> myroomsbuy;
	private List<MyClass> myClasses;
	private StudyClassDao studyClassDao;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	private int count_kaoshi;
	private int count_pxb;
	private int count_msg;
	private int count_course;
	
	private List<News> newsList;
	private List<BaoxianProduct> baoxianProductList;
	private int count_news;
	private int count_baoxianProduct;
	
	private int count_chushen;
	private int count_zhongshen;
	private int count_needto_shenhe_news;
	
	private ScheduleGlobleDao scheduleGlobleDao;
	private List<Map<String,Object>> myPlan;//我的计划
	private List<Map<String,Object>> myLog;//我的日志
	private List<Map<String,Object>> myRC;//我的日程
	private List<Map<String,Object>> myDaibanshuwu;//我的代办事务 
	private List<Map<String,Object>> myNoPass;
	private List<Map<String,Object>> myDaiPass;
	private List<MyCourse> studyCourseList;
	private int chidao;
	private int zaotui;
	private int queqin;
	private WorkAttendance workAttendance;
	private AttendanceDao attendanceDao;
	private Department department;
	private int sub_department;
	
	private boolean tongzhigonggao;
	private boolean gongzuojihua;
	private boolean gongzuorizhi;
	private boolean richenganpai;
	private boolean daibanshiwu;
	private boolean gerenweishen;
	private boolean gerendaishen;
	private boolean gerenkaoqin;
	private boolean myallcourse;
	private boolean myexams;
	private boolean mybuyrooms;
	private boolean mytrainingcourses;
	
	private HttpRequestDeviceUtils httpRequestDeviceUtils;


	public List<MyRoom> getMyroomsbuy() {
		return myroomsbuy;
	}

	public void setMyroomsbuy(List<MyRoom> myroomsbuy) {
		this.myroomsbuy = myroomsbuy;
	}

	public boolean isMybuyrooms() {
		return mybuyrooms;
	}

	public void setMybuyrooms(boolean mybuyrooms) {
		this.mybuyrooms = mybuyrooms;
	}

	public List<MyCourse> getStudyCourseList() {
		return studyCourseList;
	}

	public void setStudyCourseList(List<MyCourse> studyCourseList) {
		this.studyCourseList = studyCourseList;
	}

	public boolean isMyexams() {
		return myexams;
	}

	public void setMyexams(boolean myexams) {
		this.myexams = myexams;
	}

	public boolean isMytrainingcourses() {
		return mytrainingcourses;
	}

	public void setMytrainingcourses(boolean mytrainingcourses) {
		this.mytrainingcourses = mytrainingcourses;
	}

	public boolean isMyallcourse() {
		return myallcourse;
	}

	public void setMyallcourse(boolean myallcourse) {
		this.myallcourse = myallcourse;
	}

	public boolean isGerenkaoqin() {
		return gerenkaoqin;
	}

	public void setGerenkaoqin(boolean gerenkaoqin) {
		this.gerenkaoqin = gerenkaoqin;
	}

	public boolean isGerenweishen() {
		return gerenweishen;
	}

	public void setGerenweishen(boolean gerenweishen) {
		this.gerenweishen = gerenweishen;
	}

	public boolean isGerendaishen() {
		return gerendaishen;
	}

	public void setGerendaishen(boolean gerendaishen) {
		this.gerendaishen = gerendaishen;
	}

	public boolean isTongzhigonggao() {
		return tongzhigonggao;
	}

	public void setTongzhigonggao(boolean tongzhigonggao) {
		this.tongzhigonggao = tongzhigonggao;
	}

	public boolean isGongzuojihua() {
		return gongzuojihua;
	}

	public void setGongzuojihua(boolean gongzuojihua) {
		this.gongzuojihua = gongzuojihua;
	}

	public boolean isGongzuorizhi() {
		return gongzuorizhi;
	}

	public void setGongzuorizhi(boolean gongzuorizhi) {
		this.gongzuorizhi = gongzuorizhi;
	}

	public boolean isRichenganpai() {
		return richenganpai;
	}

	public void setRichenganpai(boolean richenganpai) {
		this.richenganpai = richenganpai;
	}

	public boolean isDaibanshiwu() {
		return daibanshiwu;
	}

	public void setDaibanshiwu(boolean daibanshiwu) {
		this.daibanshiwu = daibanshiwu;
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

	public List<Map<String, Object>> getMyDaibanshuwu() {
		return myDaibanshuwu;
	}

	public void setMyDaibanshuwu(List<Map<String, Object>> myDaibanshuwu) {
		this.myDaibanshuwu = myDaibanshuwu;
	}

	public WorkAttendance getWorkAttendance() {
		return workAttendance;
	}

	public void setWorkAttendance(WorkAttendance workAttendance) {
		this.workAttendance = workAttendance;
	}

	public int getChidao() {
		return chidao;
	}

	public void setChidao(int chidao) {
		this.chidao = chidao;
	}

	public int getZaotui() {
		return zaotui;
	}

	public void setZaotui(int zaotui) {
		this.zaotui = zaotui;
	}

	public int getQueqin() {
		return queqin;
	}

	public void setQueqin(int queqin) {
		this.queqin = queqin;
	}

	public AttendanceDao getAttendanceDao() {
		return attendanceDao;
	}

	public void setAttendanceDao(AttendanceDao attendanceDao) {
		this.attendanceDao = attendanceDao;
	}

	public List<Map<String, Object>> getMyDaiPass() {
		return myDaiPass;
	}

	public void setMyDaiPass(List<Map<String, Object>> myDaiPass) {
		this.myDaiPass = myDaiPass;
	}

	public List<Map<String, Object>> getMyNoPass() {
		return myNoPass;
	}

	public void setMyNoPass(List<Map<String, Object>> myNoPass) {
		this.myNoPass = myNoPass;
	}

	public List<Map<String, Object>> getMyRC() {
		return myRC;
	}

	public void setMyRC(List<Map<String, Object>> myRC) {
		this.myRC = myRC;
	}

	public List<Map<String, Object>> getMyLog() {
		return myLog;
	}

	public void setMyLog(List<Map<String, Object>> myLog) {
		this.myLog = myLog;
	}

	public ScheduleGlobleDao getScheduleGlobleDao() {
		return scheduleGlobleDao;
	}

	public void setScheduleGlobleDao(ScheduleGlobleDao scheduleGlobleDao) {
		this.scheduleGlobleDao = scheduleGlobleDao;
	}

	public List<Map<String, Object>> getMyPlan() {
		return myPlan;
	}

	public void setMyPlan(List<Map<String, Object>> myPlan) {
		this.myPlan = myPlan;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public List<BaoxianProduct> getBaoxianProductList() {
		return baoxianProductList;
	}

	public void setBaoxianProductList(List<BaoxianProduct> baoxianProductList) {
		this.baoxianProductList = baoxianProductList;
	}

	public int getCount_news() {
		return count_news;
	}

	public void setCount_news(int count_news) {
		this.count_news = count_news;
	}

	public int getCount_baoxianProduct() {
		return count_baoxianProduct;
	}

	public void setCount_baoxianProduct(int count_baoxianProduct) {
		this.count_baoxianProduct = count_baoxianProduct;
	}

	public int getCount_chushen() {
		return count_chushen;
	}

	public void setCount_chushen(int count_chushen) {
		this.count_chushen = count_chushen;
	}

	public int getCount_zhongshen() {
		return count_zhongshen;
	}

	public void setCount_zhongshen(int count_zhongshen) {
		this.count_zhongshen = count_zhongshen;
	}

	public int getCount_needto_shenhe_news() {
		return count_needto_shenhe_news;
	}

	public void setCount_needto_shenhe_news(int count_needto_shenhe_news) {
		this.count_needto_shenhe_news = count_needto_shenhe_news;
	}

	public int getCount_kaoshi() {
		return count_kaoshi;
	}

	public void setCount_kaoshi(int count_kaoshi) {
		this.count_kaoshi = count_kaoshi;
	}

	public int getCount_pxb() {
		return count_pxb;
	}

	public void setCount_pxb(int count_pxb) {
		this.count_pxb = count_pxb;
	}

	public int getCount_msg() {
		return count_msg;
	}

	public void setCount_msg(int count_msg) {
		this.count_msg = count_msg;
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

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public StudyCourseDao getStudyCourseDao() {
		return studyCourseDao;
	}

	public void setStudyCourseDao(StudyCourseDao studyCourseDao) {
		this.studyCourseDao = studyCourseDao;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	// 基本信息修改。。。。
	public String student_mypwdalterInit() throws ElException {
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "student_mypwdalter_phone"; 
		}
		return "student_mypwdalter";
	}

	public String student_mypwdalter() throws ElException {
		int uid = getSessionIntValue(ElConstants.SESSION_USERID);
		String oldPwd = getRequest().getParameter("oldPwd");
		if (oldPwd==null||"".equals(oldPwd)) {
			setElmessage("请输入原密码");
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "student_mypwdalter_phone"; 
			}
			return "student_mypwdalter";
		}
		if (!userDao.checkPwd(uid, MD5.crypt(oldPwd))) {
			setElmessage("原密码错误");
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "student_mypwdalter_phone"; 
			}
			return "student_mypwdalter";
		} else {
			if (oldPwd==null||"".equals(getRequest().getParameter("newPwd"))) {
				setElmessage("请输入新密码");
				boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
				if(b==true){
					return "student_mypwdalter_phone"; 
				}
				return "student_mypwdalter";
			}
			elUser = new ELUser(uid);
			elUser.setPassword(MD5.crypt(getRequest().getParameter("newPwd")));
			userDao.alterMyPwd(elUser);
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "student_mypwdalter_success_phone"; 
		}
		return "student_mypwdalter_success";
	}
	public String student_myalterInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs=userDao.getBaseDatatByTypeid(1);
			zhiwus=userDao.getBaseDatatByTypeid(2);
			zhijis=userDao.getBaseDatatByTypeid(3);
			//gangweis=userDao.getBaseDatatByTypeid(4);
			dishis=userDao.getBaseDatatByTypeid(5);
		}else{
			jingzhongs=userDao.getBaseDatatByTypeidc(1);
			zhiwus=userDao.getBaseDatatByTypeidc(2);
			zhijis=userDao.getBaseDatatByTypeidc(3);
			//gangweis=userDao.getBaseDatatByTypeidc(4);
			dishis=userDao.getBaseDatatByTypeidc(5);
		}
		elUser =   userDao.getUserById_cisco(getSessionIntValue(ElConstants.SESSION_USERID));
//		elUser = userDao
//				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));

		//depTree = departmentDao.getDepTree(1, -1, true);
		//判断注册信息是否都要验证
//		String resultPage="student_myalter";
//		if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)){
//			getRequest().setAttribute("isAll", "yes");
//			//resultPage="student_myalter";
//		}else{
//			getRequest().setAttribute("isAll", "no");
//			//resultPage="student_myalter_noall";
//		}
		//return "student_myalter";
//		return resultPage;
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "student_myalter_cisco_phone"; 
		}
		return "student_myalter_cisco";
	}

	public String student_myalter() throws ElException {
//		if(userDao.checkUserShenfenzheng(elUser.getShenfenzheng(),elUser.getId())){
//			jingzhongs=userDao.getBaseDatatByTypeid(1);
//			zhiwus=userDao.getBaseDatatByTypeid(2);
//			zhijis=userDao.getBaseDatatByTypeid(3);
//			gangweis=userDao.getBaseDatatByTypeid(4);
//			dishis=userDao.getBaseDatatByTypeid(5);
//			setElmessage("您所填的身份证已被其他人使用，请重新输入！");
//			//判断注册信息是否都要验证
//			if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)){
//				getRequest().setAttribute("isAll", "yes");
//			}else{
//				getRequest().setAttribute("isAll", "no");
//			}
//			elUser=userDao.getUserById(elUser.getId());
//			return "student_myalter";//返回修改页面
//		}
//		elUser.setId(getSessionIntValue(ElConstants.SESSION_USERID));
//		userDao.alterMyInfo(elUser);
		elUser.setPassword(MD5.crypt(elUser.getPassword()));
		userDao.update_cisco(elUser);
		elUser = userDao.getUserById_cisco(elUser.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "student_myalter_success_phone"; 
		}
		return "student_myalter_success";
	}

	public String student_myinfo() throws ElException {
//		elUser = userDao
//				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
//		StationDaoImpl staDao  =new StationDaoImpl();
//		Station sta = staDao.getStById(elUser.getStaid());
//		String grantTime = new SimpleDateFormat("yyyy")
//		.format(elUser.getShengri_());
//		Calendar ca = Calendar.getInstance();
//		int year = ca.get(Calendar.YEAR);
//		int sr = Integer.parseInt(grantTime);
//		int age = year-sr;
//		elUser.setAge(age);
//		elUser.setXianzhiwei(sta.getName());
		
		elUser = userDao
		.getUserById_cisco(getSessionIntValue(ElConstants.SESSION_USERID));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "student_myinfo_cisco_phone"; 
		}
		return "student_myinfo_cisco";
//		return "student_myinfo";
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public List<MyExamPaper> getMyExamPapers() {
		return myExamPapers;
	}

	public void setMyExamPapers(List<MyExamPaper> myExamPapers) {
		this.myExamPapers = myExamPapers;
	}

	public List<MyCourse> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}

	public List<MyClass> getMyClasses() {
		return myClasses;
	}

	public void setMyClasses(List<MyClass> myClasses) {
		this.myClasses = myClasses;
	}

	public StudyClassDao getStudyClassDao() {
		return studyClassDao;
	}

	public void setStudyClassDao(StudyClassDao studyClassDao) {
		this.studyClassDao = studyClassDao;
	}

	public List<MyRoom> getMyrooms() {
		return myrooms;
	}

	public void setMyrooms(List<MyRoom> myrooms) {
		this.myrooms = myrooms;
	}

	/**
	 * 个人中心首页
	 * @return
	 * @throws ElException
	 */
	public String study_index() throws ElException {
		int userid=getSessionIntValue(ElConstants.SESSION_USERID);
		// 学员登录显示 个人预览，超级管理员登入显示 待审核概览
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			int eroom_end = 0;// 待审核考场
			int class_end = 0;// 待审核培训班
			int news_end = 0;// 待审核新闻
			eroom_end = studyQuizDao.getEroomEndCount();
			getRequest().setAttribute("eroom_end", eroom_end);
			class_end = studyClassDao.getClassEndCount();
			getRequest().setAttribute("class_end", class_end);
			news_end = new NewsDaoImpl().getNewsEndCount();
			getRequest().setAttribute("news_end", news_end);
			//
			int message_no = 0;
			message_no = messageDao
					.getMessNoCount(userid);
			getRequest().setAttribute("message_no", message_no);
		} else {
			int message_no = 0;
			int message_yes = 0;
			int eroom_no = 0;
			int eroom_all = 0;
			int class_yes = 0;
			int class_all = 0;
			// 未读短消息条数，已读短消息条数
			// 未读
			message_no = messageDao
					.getMessNoCount(userid);
			getRequest().setAttribute("message_no", message_no);
			// 已读
			message_yes = messageDao
					.getMessYesCount(userid);
			getRequest().setAttribute("message_yes", message_yes);
			// 未开始的考场，全部考场
//			eroom_no = studyQuizDao
//					.getEroomNoCount(userid);
//			getRequest().setAttribute("eroom_no", eroom_no);
//			eroom_all = studyQuizDao
//					.getEroomAllCount(userid);
//			getRequest().setAttribute("eroom_all", eroom_all);
//			// 已结业培训班，全部培训班
//			class_yes = studyClassDao
//					.getClassYesCount(userid);
//			getRequest().setAttribute("class_yes", class_yes);
//			class_all = studyClassDao
//					.getClassAllCount(userid);
//			getRequest().setAttribute("class_all", class_all);
		}
		// 判断 是否第1次登入
		if ("true".equals(getSessionValue("isLogin"))) {
			getRequest().setAttribute("isLogin", 1);
			getSession().removeAttribute("isLogin");// 销毁
			// 获取该用户的弹窗信息
			messageDao.listSetUserInPop(userid);
			String popIds = messageDao
					.getUserPopList(userid);
			getRequest().setAttribute("popIds", popIds);
			// 调用存储过程来处理练习分配给的部门 自动分配给学员
//			 studyQuizDao.study_depAssign(userid,
//			 getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
			// 调用存储过程来处理考场分配给的工种 自动分配给学员
//			 studyQuizDao.study_examJingzhongAssign(userid);
		} else {
			getRequest().setAttribute("isLogin", 0);
		}
//		//初始化考场信息
//		myrooms = studyQuizDao.onloadUcenterStudy(
//				userid, 5,getPageSize());
//		count_kaoshi = studyQuizDao
//				.onloadUcenterStudyCount(userid);
//		//初始化培训班信息
//		myClasses = studyClassDao.OnloacUcenterMyclass(
//				userid, 5,getPageSize());
//		count_pxb = studyClassDao
//				.OnloacUcenterMyclassCount(userid);
		//初始化短消息
		newMessage = messageDao.listMessNew(
				userid, 10,getPageSize());
		count_msg = messageDao.getMessNoCount(userid);
		
		
//		//去除表格
		tongzhigonggao = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_TONGZHIGONGGAO);
		gongzuojihua = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA);
		gongzuorizhi = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI);
		richenganpai = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_RICHENGANPAI);
		daibanshiwu = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU);
		gerenweishen = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN);
		gerendaishen = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENDAISHEN);
		gerenkaoqin = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENKAOQIN);
		myallcourse = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYALLCOURSES);
		myexams = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYEXAMS);
		mybuyrooms = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYBUYROOMS);
		mytrainingcourses = IndexSystemConfigOp
		.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYTRAININGCOURSES);
		
		newsList = pfmsNewsDao.newsPersonerList(IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_TONGZHIGONGGAO),
				IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_TONGZHIGONGGAO_LENGTH));
		count_news = pfmsNewsDao.newsPersonerCount();
		
//		baoxianProductList = baoxianProductDao.getSixFrontBaoxianProductList(getPageNow4(),getPageSize4());
//		count_baoxianProduct = baoxianProductDao.getFrontBaoxianProductCount();
//		
//		count_chushen = baoxianProductDao.getChushenCount();
//		count_zhongshen = baoxianProductDao.getZhongshenCount();
//		count_needto_shenhe_news = pfmsNewsDao.getNeedToShenheNewsCount();
		
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}

		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		//我的全部课程
		if(myallcourse){
			//我的测评课程
			boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
			if(open_jtm){
				elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
				
				String cer = JTM.getJTM_cer(String.valueOf(elUser.getId()));
				String JTM_URL = JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_MY_CEPINGCOURSES_URL)+
				"?userid="+elUser.getId()+
				"&jobid="+elUser.getStaid()+
				"&cer="+cer;
				
				Content c = null;
				String returnValue = "";
				try {
					c = Request.Get(JTM_URL).addHeader("Content-Type", "text/html; charset=UTF-8").execute().returnContent();
					System.out.println(c.asString());
					returnValue = c.asString();
					
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String[] arr = null;
				if(returnValue!=null&&!returnValue.equals("")){
					arr = returnValue.trim().split("\\|");
					if(arr[0].equals("true")){
						String[] courses = arr[1].split(",");
						if(courses!=null&&courses.length>0){
							//插入前，不能将userid对应的测评课程删除
							//因为存在每次学习的开始时间，删除的话再插入，每次开始时间都从0开始。
//							studyCourseDao.deleteCePingCoursesByUseridAndClassid(getSessionIntValue(ElConstants.SESSION_USERID),-4);
							for(int i=0;i<courses.length;i++){
								//添加测评课程到课程分配表
								studyCourseDao.insertCepingCourse(getSessionIntValue(ElConstants.SESSION_USERID),Integer.parseInt(courses[i]));
							}
						}
					}
				}
			}
			studyCourseList = studyCourseDao.study_index_listMyAllCourse(getSessionIntValue(ElConstants.SESSION_USERID), 
					IndexSystemConfigOp.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYALLCOURSES_LENGTH));
		}
		//我的非购买考试
		if(myexams){
			myrooms = studyQuizDao.study_index_listErsWithoutC(getSessionIntValue(ElConstants.SESSION_USERID),
					IndexSystemConfigOp.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYEXAMS_LENGTH),true);
		}
		//我购买的考试
		if(mybuyrooms){
			myroomsbuy = studyQuizDao.study_index_listErsWithoutC(getSessionIntValue(ElConstants.SESSION_USERID),
					IndexSystemConfigOp.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYBUYROOMS_LENGTH),false);
		}
		//我的培训班
		if(mytrainingcourses){
			myClasses = studyClassDao.study_index_listMyStudyClass(getSessionIntValue(ElConstants.SESSION_USERID),
					IndexSystemConfigOp.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYTRAININGCOURSES));
		}
		
		//获取我的计划
		myPlan = scheduleGlobleDao.getMyPlan(getSessionIntValue(ElConstants.SESSION_USERID),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA),
				IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GONGZUOJIHUA_LENGTH));
		
		myLog = scheduleGlobleDao.getMyLog(getSessionIntValue(ElConstants.SESSION_USERID),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI),
				IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GONGZUORIZHI_LENGTH));
		
		myRC = scheduleGlobleDao.getMyRC(getSessionIntValue(ElConstants.SESSION_USERID),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_RICHENGANPAI),
				IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_RICHENGANPAI_LENGTH));
		
		myDaibanshuwu=scheduleGlobleDao.getMyDaibanshuwu(getSessionIntValue(ElConstants.SESSION_USERID),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU),
				IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_DAIBANSHIWU_LENGTH));
		
		
		myNoPass = scheduleGlobleDao.getNoPass(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),
				getPageNow3(),getPageSize3(),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN),department);
		count_news = scheduleGlobleDao.getNoPassSize(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN),department);
		
		myDaiPass = scheduleGlobleDao.getdaiPass(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),
				getPageNow4(),getPageSize4(),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENDAISHEN),department);
		count_baoxianProduct = scheduleGlobleDao.getdaiPassSize(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENDAISHEN),department);
		
		chidao = attendanceDao.getKaoqinCount(getSessionIntValue(ElConstants.SESSION_USERID),"迟到");
		zaotui = attendanceDao.getKaoqinCount(getSessionIntValue(ElConstants.SESSION_USERID),"早退");
		queqin = attendanceDao.getKaoqinCount(getSessionIntValue(ElConstants.SESSION_USERID),"缺勤");
		workAttendance = attendanceDao.getWorkAttendanceByUserIdAndDate(getSessionIntValue(ElConstants.SESSION_USERID));
		workAttendance = workAttendance == null?new WorkAttendance():workAttendance;
		
//		//可报名考场数量
//		//long l = System.currentTimeMillis();
//		int eroom_appcount=((EroomDao)SpringContextUtil.getBean("eroomDao")).getEroomAppcount(userid, getSessionIntValue(ElConstants.SESSION_ROLE));
//		getRequest().setAttribute("eroom_appcount", eroom_appcount);
//		//可报名培训班数量
//		int class_appcount=studyClassDao.getClassAppcount(userid, getSessionIntValue(ElConstants.SESSION_ROLE));;
//		getRequest().setAttribute("class_appcount", class_appcount);
//		//System.err.println(l-System.currentTimeMillis());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "study_index_phone"; 
		}
		return "study_index";
	}
	/**
	 * 考试中心首页
	 * @return
	 * @throws ElException
	 */
	public String exam_index() throws ElException {
		int userid=getSessionIntValue(ElConstants.SESSION_USERID);
		// 学员登录显示 个人预览，超级管理员登入显示 待审核概览
		// 判断 是否第1次登入
		//初始化考场信息
		myrooms = studyQuizDao.onloadUcenterStudy(
				userid, 5,getPageSize());
		count_kaoshi = studyQuizDao
				.onloadUcenterStudyCount(userid);
		//可报名考场数量
		int eroom_appcount=((EroomDao)SpringContextUtil.getBean("eroomDao")).getEroomAppcount(userid, getSessionIntValue(ElConstants.SESSION_ROLE));
		getRequest().setAttribute("eroom_appcount", eroom_appcount);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "exam_index_phone"; 
		}
		return "exam_index";
	}
	/**
	 * 查看自己概况信息
	 * @return
	 * @throws ElException
	 */
	public String myOverviewInfo() throws ElException {
		int userid= getSessionIntValue(ElConstants.SESSION_USERID);
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
		//考场信息
		myrooms = studyQuizDao.listErsWithoutC(userid, 0,999999,1);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "myOverviewInfo_phone"; 
		}
		return "myOverviewInfo";
	}

	public String myscore_dot_view() throws ElException {
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		elUser = new ELUser();
		ScoreSet scoreSet = new ScoreSet();
		scoreSet.setDian_forum_do(SystemConfOp
				.getIntValue(ElConstants.DIAN_FORUM_DO));
		scoreSet.setDian_login_do(SystemConfOp
				.getIntValue(ElConstants.DIAN_LOGIN_DO));
		scoreSet.setDian_study_cp_do(SystemConfOp
				.getIntValue(ElConstants.DIAN_STUDY_CP_DO));
		scoreSet.setDian_study_do(SystemConfOp
				.getIntValue(ElConstants.DIAN_STUDY_DO));
		scoreSet.setDian_topic_do(SystemConfOp
				.getIntValue(ElConstants.DIAN_TOPIC_DO));
		scoreSet.setJian_ep_qiangzhi(-SystemConfOp
				.getIntValue(ElConstants.JIAN_EP_QIANGZHI));
		scoreSet.setJian_ep_zhanting(-SystemConfOp
				.getIntValue(ElConstants.JIAN_EP_ZHANTING));
		scoreSet.setJian_forum_do(-SystemConfOp
				.getIntValue(ElConstants.JIAN_FORUM_DO));
		scoreSet.setJian_knowledge_do(-SystemConfOp
				.getIntValue(ElConstants.JIAN_KNOWLEDGE_DO));
		scoreSet.setJian_login_do(-SystemConfOp
				.getIntValue(ElConstants.JIAN_LOGIN_DO));

		scoreSet.setDian_forum_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.DIAN_FORUM_DO));

		scoreSet.setDian_login_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.DIAN_LOGIN_DO));
		scoreSet.setDian_study_cp_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.DIAN_STUDY_CP_DO));
		scoreSet.setDian_study_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.DIAN_STUDY_DO));
		scoreSet.setDian_topic_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.DIAN_TOPIC_DO));
		scoreSet.setJian_ep_qiangzhi_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.JIAN_EP_QIANGZHI));
		scoreSet.setJian_ep_zhanting_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.JIAN_EP_ZHANTING));
		scoreSet.setJian_forum_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.JIAN_FORUM_DO));
		scoreSet.setJian_knowledge_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.JIAN_KNOWLEDGE_DO));
		scoreSet.setJian_login_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.JIAN_LOGIN_DO));
		// elUser.setScoreset(scoreSet);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "myscore_dot_view_phone"; 
		}
		return "myscore_dot_view";
	}

	public String myscore_score_view() throws ElException {
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		elUser = new ELUser();
		ScoreSet scoreSet = new ScoreSet();
		scoreSet.setScore_course_apply(SystemConfOp
				.getIntValue(ElConstants.SCORE_COURSE_APPLY));
		scoreSet.setScore_forum_jh(SystemConfOp
				.getIntValue(ElConstants.SCORE_FORUM_JH));
		scoreSet.setScore_knowledge_tj(SystemConfOp
				.getIntValue(ElConstants.SCORE_KNOWLEDGE_TJ));
		scoreSet.setScore_ktroom_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_KTROOM_DO));
		scoreSet.setScore_mess_send(SystemConfOp
				.getIntValue(ElConstants.SCORE_MESS_SEND));
		scoreSet.setScore_note_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_NOTE_DO));
		scoreSet.setScore_poll_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_POLL_DO));
		scoreSet.setScore_prac_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_PRAC_DO));
		scoreSet.setScore_simp_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_SIMP_DO));
		scoreSet.setScore_survey_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_SURVEY_DO));
		scoreSet.setScore_ztroom_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_ZTROOM_DO));

		scoreSet.setScore_course_apply_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_COURSE_APPLY));
		scoreSet.setScore_forum_jh_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_FORUM_JH));
		scoreSet.setScore_knowledge_tj_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_KNOWLEDGE_TJ));
		scoreSet.setScore_ktroom_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_KTROOM_DO));
		scoreSet.setScore_mess_send_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_MESS_SEND));
		scoreSet.setScore_note_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_NOTE_DO));
		scoreSet.setScore_poll_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_POLL_DO));
		scoreSet.setScore_prac_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_PRAC_DO));
		scoreSet.setScore_simp_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_SIMP_DO));
		scoreSet.setScore_survey_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_SURVEY_DO));
		scoreSet.setScore_ztroom_do_m(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_ZTROOM_DO));
		// elUser.setScoreset(scoreSet);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "myscore_score_view_phone"; 
		}
		return "myscore_score_view";
	}

	public String myCredit_result() throws ElException {

		myCourses = studyCourseDao
				.listMyCreditCourse(getSessionIntValue(ElConstants.SESSION_USERID));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "myCredit_result_phone"; 
		}
		return "myCredit_result";
	}

	public List<Message> getNewMessage() {
		return newMessage;
	}

	public void setNewMessage(List<Message> newMessage) {
		this.newMessage = newMessage;
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public PfmsNewsDao getPfmsNewsDao() {
		return pfmsNewsDao;
	}

	public void setPfmsNewsDao(PfmsNewsDao pfmsNewsDao) {
		this.pfmsNewsDao = pfmsNewsDao;
	}

	public BaoxianProductDao getBaoxianProductDao() {
		return baoxianProductDao;
	}

	public void setBaoxianProductDao(BaoxianProductDao baoxianProductDao) {
		this.baoxianProductDao = baoxianProductDao;
	}

	public int getCount_course() {
		return count_course;
	}

	public void setCount_course(int count_course) {
		this.count_course = count_course;
	}
	
	//北京二次开发修改	
	public String student_myinfo2() throws ElException {
		elUser = userDao
				.getUserById2(getSessionIntValue(ElConstants.SESSION_USERID));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "student_myinfo2_phone"; 
		}
		return "student_myinfo2";
	}
	
	public String student_myalterInit2() throws ElException {
//		if(!getSessionValue(ElConstants.SESSION_ROLENAME).equals("准开通")&&
//				getSessionIntValue(ElConstants.SESSION_ROLE) != 2 ){	
//			setElmessage(" 您的个人信息已经过管理员审核，不能自己修改！");
//			return "error";	
//		}else{			
			elUser = userDao.getUserById2(getSessionIntValue(ElConstants.SESSION_USERID));
//		}
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "student_myalter2_phone"; 
			}
		return "student_myalter2";
	}
	public String student_myalter2() throws ElException {
		elUser.setId(getSessionIntValue(ElConstants.SESSION_USERID));
		userDao.alterMyInfo2(elUser);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "student_myalter_success2_phone"; 
		}
		return "student_myalter_success2";
	}
//北京二次开发 End	

	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}

	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}
}
