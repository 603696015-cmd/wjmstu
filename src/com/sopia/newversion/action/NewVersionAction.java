package com.sopia.newversion.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.struts2.ServletActionContext;

import com.kf.finger;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.AuthorityNewVersionUtil;
import com.sopia.common.AuthorityUtil;
import com.sopia.common.BasetNameUtil;
import com.sopia.common.CheckCard;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.IndexSystemConfigOp;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.JTM;
import com.sopia.common.JTMSystemConfOp;
import com.sopia.common.MD5;
import com.sopia.common.NewSystemConfOp;
import com.sopia.common.OnlineUtil;
import com.sopia.common.ScoreOperate;
import com.sopia.common.SystemConfOp;
import com.sopia.common.UserExcelUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.office.ExcelOutPut;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.FuncDao;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.MyLogin;
import com.sopia.duman.entities.Station;
import com.sopia.duman.entities.YzCode;
import com.sopia.forumman.dao.ForumAdminDao;
import com.sopia.forumman.entities.Forum;
import com.sopia.forumman.entities.ForumBlock;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.forumman.entities.Topic;
import com.sopia.intelligentTutoringPoints.IntelligentLoginUtil;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLogin;
import com.sopia.knowledgeManage.KnowledgeManageConstants;
import com.sopia.knowledgeManage.dao.KnowledgeManageDao;
import com.sopia.knowledgeManage.entities.Kledge;
import com.sopia.knowledgeman.dao.KnowledgeDao;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.mail.MailSenderInfo;
import com.sopia.mail.SimpleMailSender;
import com.sopia.newsandmess.dao.MessageDao;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.newsandmess.dao.impl.NewsDaoImpl;
import com.sopia.newsandmess.entities.Message;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.newversion.NewVersionUtil;
import com.sopia.newversion.dao.NewVersionDao;
import com.sopia.peixunBatch.dao.PeixunBatchDao;
import com.sopia.peixunBatch.entities.PeixunBatch;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.schedule.TagsUtil;
import com.sopia.schedule.dao.ScheduleGlobleDao;
import com.sopia.shopping.dao.ShoppingDao;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.wjm.action.ClassificationAction;
import com.sopia.wjm.dao.ClassificationDao;
import com.sopia.workcourseset.dao.WorkCourseDao;
import com.sopia.workcourseset.entity.WorkCourse;

public class NewVersionAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(NewVersionAction.class);
	private NewVersionDao newVersionDao;
	private String module;
	private ELUser elUser;
	private ElFunc menu;
	private RoleDao roleDao;
	private List<ElFunc> menus;
	private List<ElFunc> menus_two;
	private List<ElFunc> menus_three;
	private List<ElFunc> menus_three_cycz;// 常用操作
	private ElFunc menu_cycz;// 常用操作
	private String Return;
	private ElFunc func;
	private FuncDao funcDao;
	private String stuff_url;
	private ElFunc funcTree;
	private ElRole role;

	private int isFromRegister;
	private int yzCodeIsNo;
	private String yzCode;
	private MyLogin myLogin;
	private ShoppingDao shoppingDao;
	private ClassDao classDao;
	private List<ExamPaper> examPapers;
	private EroomDao eroomDao;
	private StudyQuizDao studyQuizDao;

	private ScheduleGlobleDao scheduleGlobleDao;
	private List<Map<String, Object>> myDaiPass;
	private int count_news;
	private Department department;
	private Department depTree;
	private int sub_department;
	private boolean gerendaishen;
	private boolean daibanshiwu;
	private List<Map<String, Object>> myDaibanshuwu;// 我的代办事务
	private MessageDao messageDao;
	private int message_no;
	private int message_yes;
	private StudyClassDao studyClassDao;
	private int eroom_no;
	private int eroom_all;
	private int class_yes;
	private int class_all;
	private KnowledgeManageDao knowledgeManageDao;
	private List<Kledge> kledges;
	private List<Message> newMessage;
	private int count_msg;
	private NewsType ntypeTree;
	private NewsType ntype;
	private List<News> newses;
	private NewsDao newsDao;
	private boolean myallcourse;
	private boolean myexams;
	private boolean mytrainingcourses;
	private boolean gongzuojihua;
	private boolean gongzuorizhi;
	private boolean gerenweishen;
	private List<MyRoom> myrooms;
	private ExamRoom room;
	private List<MyClass> myClasses;
	private List<MyCourse> studyCourseList;
	private StudyCourseDao studyCourseDao;
	private ForumAdminDao forumAdminDao;
	private List<Forum> forums;

	private List<Map<String, Object>> myPlan;// 我的计划
	private List<Map<String, Object>> myLog;// 我的日志
	private List<Map<String, Object>> myNoPass;// 个人未审核

	private String isFromAdmin;// 区分是个人中心登录还是管理中心登录
	private boolean needAllocation;

	private MyClass cla;// 拿证培训班
	private MyClass new_cla;// 本年度最新一期培训班
	private MyClass nianjian_cla;// 年检培训班
	private int isBuyNianjianClass;
	private int isChangeElclass;// 是否已选择培训班
	private Map<String, Object> map;
	private int step;

	private MyClass myClass;// 拿证培训班
	// wsj1018修改
	private int sumBX;// 必修总学分
	private int hasSumXX;// 已获得选修总学分
	private int hasSumBX;// 已获得必修学分
	private int sumScore;// 应获得总学分
	private int hasSumScore;// 已获得总学分
	private double scoreProcess;// 学分比例
	private MyClass myClassAll;// 所有课程
	private Course courseBX;
	private Course courseXX;

	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;

	private YzCode yzcode;
	private IntelligentLogin intelligentLogin;
	private Station stTree;
	private Station station;
	private boolean exprot;
	private List<ELUser> elUsers;
	private List<ElRole> roles;
	private int upOk;

	private List<Topic> topics;

	private HttpRequestDeviceUtils httpRequestDeviceUtils;
	private File st;
	private String stFileName;
	private List<ForumBlockType> fbtypes;
	private String yzCodey;
	private String txtEmail;
	private IndexDao indexDao;
	private int type;
	private StuffDao stuffDao;
	private KnowledgeDao knowledgeDao;
	private IndexDataUtil indexDataUtil;
	private CourseTypeDao ctypeDao;
	private ElClTypeDao elClTypeDao;
	private ForumBlock fblock;
	//wjm0211修改
	private PeixunBatchDao peixunBatchDao;
	private ClassificationAction classificationAction;
	private PeixunBatch peixunBatch;
	private CourseDao courseDao;
	private ClassificationDao classificationDao;
	// 山东项目
	private int classid_sd;
	private int courseid_sd;
	private int eroomid_sd;
	private int epid_sd;
	private WorkCourseDao workCourseDao;
	private List<WorkCourse> workCourses;
	// sd0110
	private BaseDataType baseType;
	private StringBuffer des;// 人群说明

	private String funcName;
	
	//2040827指纹识别
	private finger fingerUtil;
	

	public finger getFingerUtil() {
		return fingerUtil;
	}

	public void setFingerUtil(finger fingerUtil) {
		this.fingerUtil = fingerUtil;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public StringBuffer getDes() {
		return des;
	}

	public void setDes(StringBuffer des) {
		this.des = des;
	}

	public BaseDataType getBaseType() {
		return baseType;
	}

	public void setBaseType(BaseDataType baseType) {
		this.baseType = baseType;
	}

	public ForumBlock getFblock() {
		return fblock;
	}

	public void setFblock(ForumBlock fblock) {
		this.fblock = fblock;
	}

	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}

	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}

	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}

	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	public StuffDao getStuffDao() {
		return stuffDao;
	}

	public void setStuffDao(StuffDao stuffDao) {
		this.stuffDao = stuffDao;
	}

	public KnowledgeDao getKnowledgeDao() {
		return knowledgeDao;
	}

	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}

	public IndexDao getIndexDao() {
		return indexDao;
	}

	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(String txtEmail) {
		this.txtEmail = txtEmail;
	}

	public String getYzCodey() {
		return yzCodey;
	}

	public void setYzCodey(String yzCodey) {
		this.yzCodey = yzCodey;
	}

	public List<ForumBlockType> getFbtypes() {
		return fbtypes;
	}

	public void setFbtypes(List<ForumBlockType> fbtypes) {
		this.fbtypes = fbtypes;
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

	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}

	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}

	public int getUpOk() {
		return upOk;
	}

	public void setUpOk(int upOk) {
		this.upOk = upOk;
	}

	public List<ElRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public boolean isExprot() {
		return exprot;
	}

	public void setExprot(boolean exprot) {
		this.exprot = exprot;
	}

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

	public String cisco_registerInit() throws ElException {
		jingzhongs = userDao.getBaseDatatByTypeidc(1);
		zhiwus = userDao.getBaseDatatByTypeidc(2);
		zhijis = userDao.getBaseDatatByTypeidc(3);
		gangweis = userDao.getBaseDatatByTypeidc(4);
		dishis = userDao.getBaseDatatByTypeidc(5);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "cisco_registerInit_phone";
		}
		return "cisco_registerInit";
	}

	public String wjm_registerInit() throws ElException {
		jingzhongs = userDao.getBaseDatatByTypeidc(1);
		zhiwus = userDao.getBaseDatatByTypeidc(2);
		zhijis = userDao.getBaseDatatByTypeidc(3);
		gangweis = userDao.getBaseDatatByTypeidc(4);
		dishis = userDao.getBaseDatatByTypeidc(5);

		return "wjm_registerInit";
	}

	/**
	 * 机构会员注册init
	 * 
	 * @return
	 * @throws ElException
	 */
	public String jg_registerInit() throws Exception {
		jingzhongs = userDao.getBaseDatatByTypeidc(1);
		zhiwus = userDao.getBaseDatatByTypeidc(2);
		zhijis = userDao.getBaseDatatByTypeidc(3);
		gangweis = userDao.getBaseDatatByTypeidc(4);
		dishis = userDao.getBaseDatatByTypeidc(5);
		fbtypes = forumAdminDao.listFbtypes();

		int codeLength = 6; // 验证码长度
		yzCodey = "";
		for (int i = 0; i < codeLength; i++) {
			int m = (int) (Math.random() * 9);
			yzCodey += m;
		}
		// yzCodey = (String) getSession().getAttribute("yzCodey");
		getSession().setAttribute("code", yzCodey);

		return "jg_registerInit";
	}

	/**
	 * 发送验证码到邮箱
	 * 
	 * @return
	 * @throws ElException
	 */
	public void sendcode() throws Exception {
		System.out.println("yzCodey=" + yzCodey);
		System.out.println(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_EMAIL_SMTP));
		System.out.println(SystemConfOp.getValue(
				ElConstants.SYSTEM_CONF_EMAIL_UNAMES).split("&")[0]);
		System.out.println(SystemConfOp.getValue(
				ElConstants.SYSTEM_CONF_EMAIL_PWDS).split("&")[0]);
		MailSenderInfo mailInfo = new MailSenderInfo();
		// mailInfo.setMailServerHost(SystemConfOp.getValue(ElConstants.SYSTEM_CONF_EMAIL_SMTP));
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		// mailInfo.setUserName(SystemConfOp.getValue(ElConstants.SYSTEM_CONF_EMAIL_UNAMES).split("&")[0]);
		mailInfo.setUserName("lg743211948@163.com");
		// mailInfo.setPassword(SystemConfOp.getValue(ElConstants.SYSTEM_CONF_EMAIL_PWDS).split("&")[0]);//您的邮箱密码
		mailInfo.setPassword("lgl224752@");// 您的邮箱密码
		// mailInfo.setFromAddress(SystemConfOp.getValue(ElConstants.SYSTEM_CONF_EMAIL_UNAMES).split("&")[0]);
		mailInfo.setFromAddress("lg743211948@163.com");
		mailInfo.setToAddress(txtEmail);
		mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");
		mailInfo.setContent("您的验证码是：" + yzCodey);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		// sms.sendHtmlMail(mailInfo);//发送html格式
	}

	/**
	 * 机构注册
	 * 
	 * @return
	 * @throws ElException
	 */
	public String jg_register() throws Exception {
		System.out.println("所属论坛：" + elUser.getLuntanbankuai());
		System.out.println("论坛名称：" + elUser.getBankuaimingcheng());
		System.out.println("会员类型：" + elUser.getUsertype());
		System.out.println("专注领域：" + elUser.getJingzhong_());
		System.out.println("地市：" + elUser.getDishi_());
		elUser.setEmail(txtEmail);
		elUser.setPassword(MD5.crypt("111111"));
		elUser.setRole(new ElRole(2));
		type = 1;
		String resultPage = "";
		if (type == 2) {// 立即注册
			resultPage = "register_error12";
		} else if (type == 1) {// 部门管理员注册
			resultPage = "register_error1";
		} else {// 普通注册
			resultPage = "register_error";
		}
		// 判断注册信息是否都要验证
		// if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)){
		// //resultPage="register_error";
		// }else{
		// //resultPage="register_error4";
		// }
		if (SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER) == 1) {
			// 对用户名是身份证号码的情况进行检查
			String userName = elUser.getUsername().trim().toLowerCase();

			if (userDao.checkUsername(elUser.getUsername())) {
				setElmessage("您注册的用户名已存在，请重新输入用户名！");
				// return "register_error";
				// 判断注册信息是否都要验证
				if (SystemConfOp
						.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
					getRequest().setAttribute("isAll", "yes");
					// return "register2";
				} else {
					getRequest().setAttribute("isAll", "no");
					// return "register4";
				}
				return resultPage;
			}

			if (elUser.getUsertype() == 0)
				elUser.setUsertype(3);
			if (type == 2) {
				// 立即注册
				if (elUser.getUsertype() == 1 || elUser.getUsertype() == 2) {// 培训机构或企业用户
					// 插入新建的部门、素材、知识、课程、考场、培训班节点
					int returnId = 0;
					department.setParent(new ElNode(Integer
							.valueOf((String) getRequest()
									.getParameter("depid"))));
					department.setManager(new ELUser(1));
					returnId = departmentDao.addDep1(department);
					department.setId(returnId);
					// 更新左右id
					((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.updatetlrid("department");
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_DEPARTMENT,
							ElLoggerConstants.LOG_TYPE_ADD, department
									.getName(), ElLoggerConstants.LOG_RES_SUCC,
							department.getId());

					// 插入企业会员表
					PfmsUser pfmsUser = new PfmsUser();
					pfmsUser.setUser(elUser);
					int eluserid = indexDao.addPfmsUser(pfmsUser, Integer
							.valueOf((String) getRequest()
									.getParameter("depid")));
					// 分配该用户该部门节点
					departmentDao
							.addOpusers("op", eluserid, department.getId());

					// 自动创建素材库节点，赋值素材库节点该用户
					StuffLib qstuff = new StuffLib();
					qstuff.setTitle(department.getName());
					qstuff.setOwner(new ELUser(eluserid));
					qstuff.setLength(100 * 1024 * 1024L);
					qstuff.setParent(new StuffLib(0));
					qstuff.setType(5);
					int returnStuffId = stuffDao.addQstuff(qstuff);
					J2EEFileUtil.createFolder("/elstuffs/"
							+ department.getName());

					userDao.userGrantOnQlibTree(new String[] { String
							.valueOf(returnStuffId) }, eluserid, "stuf");

					// 添加资料库节点，并且分配给该用户//id,name,parentid,isshared
					KnowledgeType kltype = new KnowledgeType();
					kltype.setName(department.getName());
					kltype.setDescription("");
					kltype.setParent(new ElNode(1));
					kltype.setManager(new ELUser(0));
					kltype.setIsshared(0);
					int kltypeid = knowledgeDao.addKltype(kltype);
					// 更新资料库左右id
					((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.updatetlrid("knowledgetype");
					// 刷新首页资料模块
					indexDataUtil
							.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_KNOWLEDGETYPE,
							ElLoggerConstants.LOG_TYPE_ADD, kltype.getName(),
							ElLoggerConstants.LOG_RES_SUCC, kltype.getId());

					userDao.userGrantOnQlibTree(new String[] { String
							.valueOf(kltypeid) }, eluserid, "klty");

					CourseType ctype = new CourseType();
					ctype.setName(department.getName());
					ctype.setParent(new ElNode(1));
					ctype.setIsshared(0);
					ctype.setDescription("");
					ctype.setMainimg("");
					int ctypeid = ctypeDao.addCtype(ctype);
					ctype = ctypeDao.getCtypeById(ctype.getId());
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.updatetlrid("course_type");
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_COURSELIB,
							ElLoggerConstants.LOG_TYPE_ADD, ctype.getName(),
							ElLoggerConstants.LOG_RES_SUCC, ctype.getId());
					// 课程
					userDao.userGrantOnQlibTree(new String[] { String
							.valueOf(ctypeid) }, eluserid, "ctyp");

					// 考场
					EroomLib eroomLib = new EroomLib();
					eroomLib.setName(department.getName());
					eroomLib.setParent(new ElNode(1));
					eroomLib.setDescription("");
					int eroomid = eroomDao.addEroomLib(eroomLib);
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.updatetlrid("eroom_lib");
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_EROOMLIB,
							ElLoggerConstants.LOG_TYPE_ADD, eroomLib.getName(),
							ElLoggerConstants.LOG_RES_SUCC, eroomLib.getId());
					userDao.userGrantOnQlibTree(new String[] { String
							.valueOf(eroomid) }, eluserid, "eroo");

					// 培训班
					ElClType cltype = new ElClType();
					cltype.setName(department.getName());
					cltype.setParent(new ElNode(1));
					cltype.setDescription("");
					cltype.setIsshared(0);
					int cltypeid = elClTypeDao.addCltype(cltype);
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.updatetlrid("elclasstype");
					cltype = elClTypeDao.getClTypeById(cltype.getId());
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASSLIB,
							ElLoggerConstants.LOG_TYPE_ADD, cltype.getName(),
							ElLoggerConstants.LOG_RES_SUCC, cltype.getId());
					userDao.userGrantOnQlibTree(new String[] { String
							.valueOf(cltypeid) }, eluserid, "clty");
				} else if (elUser.getUsertype() == 3) {// 个人用户
					elUser.setRole(new ElRole(2));
					elUser.setValid(!SystemConfOp
							.getBooleanValue(ElConstants.REGISTER_NEED_SH));
					department.setId(Integer.valueOf((String) getRequest()
							.getParameter("depid")));
					elUser.setDepartment(department);
					String tempPassword = elUser.getPassword();//
					elUser.setPassword(MD5.crypt(elUser.getPassword()));
					userDao.insert(elUser);
				}
			} else if (type == 1) {
				// 判断有没有当前月的部门节点,没有节点，创建节点；有节点，获取节点

				// 获取当前时间的年月日
				Calendar cal = Calendar.getInstance();
				String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
				if (Integer.parseInt(month) < 10
						&& Integer.parseInt(month) >= 1) {
					month = "0" + Integer.parseInt(month);
				}
				String value = String.valueOf(cal.get(Calendar.YEAR)) + month;

				int depid = departmentDao.checkDepForMonth(value);

				// 部门管理员注册
				elUser.setRole(new ElRole(2));
				elUser.setValid(!SystemConfOp
						.getBooleanValue(ElConstants.REGISTER_NEED_SH));

				// 根节点下方自动创建一个部门节点
				int returnId = 0;
				department.setParent(new ElNode(depid));
				department.setManager(new ELUser(1));
				// departments = departmentDao.listdeps();
				// for(int i=0;i<departments.size() ;i++){
				// if(!departments.get(i).getName().equals(department.getName())){
				// if(i == departments.size() - 1){
				// department.setParent(new ElNode(1));
				// department.setManager(new ELUser(1));
				// returnId = departmentDao.addDep1(department);
				// }
				// }
				// }
				returnId = departmentDao.addDep1(department);
				department.setId(returnId);
				// 更新左右id
				((ElNodeSQL) SpringContextUtil
						.getBean(ElConstants.CLASS_ELNODESQL))
						.updatetlrid("department");
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_DEPARTMENT,
						ElLoggerConstants.LOG_TYPE_ADD, department.getName(),
						ElLoggerConstants.LOG_RES_SUCC, department.getId());
				// 部门设置进去
				// department=departmentDao.getDepByBH(elUser.getDanwei());
				department = departmentDao.getDepById(department.getId());
				elUser.setDepartment(department);
				String tempPassword = elUser.getPassword();//
				elUser.setPassword(MD5.crypt("111111"));
				elUser.setRealname(department.getName() + "管理员");
				elUser.setSex("男");
				int eluserid = userDao.insert1(elUser);
				elUser.setId(eluserid);
				// 添加企业用户
				indexDao.insert_into_pfmsUser(elUser);
				// 分配该用户该部门节点
				departmentDao.addOpusers("op", eluserid, department.getId());

				// 自动创建素材库节点，赋值素材库节点该用户
				int stuffid = stuffDao.checkStuffForMonth(value, eluserid);
				J2EEFileUtil.createFolder("/elstuffs/" + value);

				StuffLib qstuff = new StuffLib();
				qstuff.setTitle(department.getName());
				qstuff.setOwner(new ELUser(eluserid));
				qstuff.setLength(100 * 1024 * 1024L);
				qstuff.setParent(new StuffLib(stuffid));
				qstuff.setType(5);
				int returnStuffId = stuffDao.addQstuff(qstuff);
				J2EEFileUtil.createFolder("/elstuffs/" + value + "/"
						+ department.getName());

				userDao.userGrantOnQlibTree(new String[] { String
						.valueOf(returnStuffId) }, eluserid, "stuf");
				// stuffDao.addStuffOpusers(eluserid, returnStuffId);

				// 添加资料库节点，并且分配给该用户//id,name,parentid,isshared
				int KnowledgeTypeid = knowledgeDao
						.checkKnowledgeForMonth(value);

				KnowledgeType kltype = new KnowledgeType();
				kltype.setName(department.getName());
				kltype.setDescription("");
				kltype.setParent(new ElNode(KnowledgeTypeid));
				kltype.setManager(new ELUser(0));
				kltype.setIsshared(0);
				int kltypeid = knowledgeDao.addKltype(kltype);
				// 更新资料库左右id
				((ElNodeSQL) SpringContextUtil
						.getBean(ElConstants.CLASS_ELNODESQL))
						.updatetlrid("knowledgetype");
				// 刷新首页资料模块
				indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_KNOWLEDGETYPE,
						ElLoggerConstants.LOG_TYPE_ADD, kltype.getName(),
						ElLoggerConstants.LOG_RES_SUCC, kltype.getId());

				userDao.userGrantOnQlibTree(new String[] { String
						.valueOf(kltypeid) }, eluserid, "klty");
				// 论坛
				fblock = new ForumBlock();
				fblock.setFbtype(new ForumBlockType(elUser.getLuntanbankuai()));
				fblock.setTitle(elUser.getBankuaimingcheng());
				fblock.setManager(new ELUser(eluserid));
				forumAdminDao.addFblock(fblock);
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_FORUMBLOCK,
						ElLoggerConstants.LOG_TYPE_ADD, fblock.getTitle(),
						ElLoggerConstants.LOG_RES_SUCC, fblock.getId());

				// 课程
				CourseType ctype = new CourseType();
				ctype.setName(department.getName());
				ctype.setParent(new ElNode(1));
				ctype.setIsshared(0);
				ctype.setDescription("");
				ctype.setMainimg("");
				int ctypeid = ctypeDao.addCtype(ctype);
				ctype = ctypeDao.getCtypeById(ctype.getId());
				((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.updatetlrid("course_type");
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_COURSELIB,
						ElLoggerConstants.LOG_TYPE_ADD, ctype.getName(),
						ElLoggerConstants.LOG_RES_SUCC, ctype.getId());

				userDao.userGrantOnQlibTree(new String[] { String
						.valueOf(ctypeid) }, eluserid, "ctyp");
				// 新闻
				NewsType ntype = new NewsType();
				ntype.setName(department.getName());
				ntype.setParent(new ElNode(1));
				ntype.setDescription("");
				ntype.setIsshared(0);
				int ntypeid = newsDao.addNewstype2(ntype);
				ntype = newsDao.getNtypeByid(ntypeid);
				((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.updatetlrid("newstype");
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_NEWSTYPE,
						ElLoggerConstants.LOG_TYPE_ADD, ntype.getName(),
						ElLoggerConstants.LOG_RES_SUCC, ntype.getId());// **//**//
				userDao.userGrantOnQlibTree(new String[] { String
						.valueOf(ntypeid) }, eluserid, "news");

				// 考场
				EroomLib eroomLib = new EroomLib();
				eroomLib.setName(department.getName());
				eroomLib.setParent(new ElNode(1));
				eroomLib.setDescription("");
				int eroomid = eroomDao.addEroomLib(eroomLib);
				((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.updatetlrid("eroom_lib");
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_EROOMLIB,
						ElLoggerConstants.LOG_TYPE_ADD, eroomLib.getName(),
						ElLoggerConstants.LOG_RES_SUCC, eroomLib.getId());
				userDao.userGrantOnQlibTree(new String[] { String
						.valueOf(eroomid) }, eluserid, "eroo");

				// 培训班
				ElClType cltype = new ElClType();
				cltype.setName(department.getName());
				cltype.setParent(new ElNode(1));
				cltype.setDescription("");
				cltype.setIsshared(0);
				int cltypeid = elClTypeDao.addCltype(cltype);
				((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.updatetlrid("elclasstype");
				cltype = elClTypeDao.getClTypeById(cltype.getId());
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_CLASSLIB,
						ElLoggerConstants.LOG_TYPE_ADD, cltype.getName(),
						ElLoggerConstants.LOG_RES_SUCC, cltype.getId());
				userDao.userGrantOnQlibTree(new String[] { String
						.valueOf(cltypeid) }, eluserid, "clty");

				if (!SystemConfOp.getBooleanValue(ElConstants.REGISTER_NEED_SH)) {// 如果不需要注册审核
					elUser = userDao.query(elUser.getUsername());
					if (elUser.getValid()) {
						// getSession().setAttribute(ElConstants.SESSION_USERID,
						// elUser.getId());
						// getSession().setAttribute(ElConstants.SESSION_USERNAME,
						// elUser.getUsername());
						// getSession().setAttribute(ElConstants.SESSION_REALNAME,
						// elUser.getRealname());
						// getSession().setAttribute(ElConstants.SESSION_ROLE,
						// elUser.getRole().getId());
						// getSession().setAttribute(ElConstants.SESSION_ROLENAME,
						// elUser.getRole().getName());
						// getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,
						// elUser.getDepartment().getId());
						// getSession().setAttribute(ElConstants.SESSION_AGE,elUser.getAge());
						// 直接登录
						elUser.setPassword(tempPassword);
						return "login_jg";
						// ScoreOperate.setScore(
						// getSessionIntValue(ElConstants.SESSION_USERID),
						// ElConstants.DIAN_LOGIN_DO);
					} else {
						return "register_success";
					}
					// ScoreOperate.setScore(
					// getSessionIntValue(ElConstants.SESSION_USERID),
					// ElConstants.DIAN_LOGIN_DO);
					// return "register_success";
				}
			} else {
				// 注册用户
				elUser.setRole(new ElRole(4));
				elUser.setValid(!SystemConfOp
						.getBooleanValue(ElConstants.REGISTER_NEED_SH));
				// 部门设置进去
				department = departmentDao.getDepByBH(elUser.getDanwei());
				elUser.setDepartment(department);
				String tempPassword = elUser.getPassword();//
				elUser.setPassword(MD5.crypt(elUser.getPassword()));
				userDao.insert(elUser);
				if (!SystemConfOp.getBooleanValue(ElConstants.REGISTER_NEED_SH)) {// 如果不需要注册审核
					elUser = userDao.query(elUser.getUsername());
					if (elUser.getValid()) {
						// getSession().setAttribute(ElConstants.SESSION_USERID,
						// elUser.getId());
						// getSession().setAttribute(ElConstants.SESSION_USERNAME,
						// elUser.getUsername());
						// getSession().setAttribute(ElConstants.SESSION_REALNAME,
						// elUser.getRealname());
						// getSession().setAttribute(ElConstants.SESSION_ROLE,
						// elUser.getRole().getId());
						// getSession().setAttribute(ElConstants.SESSION_ROLENAME,
						// elUser.getRole().getName());
						// getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,
						// elUser.getDepartment().getId());
						// getSession().setAttribute(ElConstants.SESSION_AGE,elUser.getAge());
						// 直接登录
						elUser.setPassword(tempPassword);
						return "login";
						// ScoreOperate.setScore(
						// getSessionIntValue(ElConstants.SESSION_USERID),
						// ElConstants.DIAN_LOGIN_DO);
					} else {
						return "register_success";
					}
					// ScoreOperate.setScore(
					// getSessionIntValue(ElConstants.SESSION_USERID),
					// ElConstants.DIAN_LOGIN_DO);
					// return "register_success";
				}
			}

		} else {
			setElmessage("系统关闭了注册功能，请与管理员联系");
			// return "register_error";
			return "error";
		}
		return "jg_register";
	}

	// 北京市卫生局注册
	public String cisco_register() throws ElException {
		String resultPage = "cisco_register";
		// 判断注册信息是否都要验证
		if (SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER) == 1) {
			// 对用户名是身份证号码的情况进行检查
			// String userName = elUser.getUsername().trim().toLowerCase();

			// if ("".equals(CheckCard.IDCardValidate(userName))) {//
			// 如果用户名是有效身份证
			// boolean isExistUserName = false;
			//
			// // 如果用户的身份证号码是15位，判断数据库中是否存在该人15位和18位的身份证号码
			// if (userName.length() == 15) {
			// isExistUserName = userDao.checkUsername(userName) ? true
			// : userDao.checkUsername(CheckCard
			// .fixPersonIDCode(userName));
			// } else {// 如果用户的身份证号码是18位，判断数据库中是否存在该人15位和18位的身份证号码
			// isExistUserName = userDao.checkUsername(userName) ? true
			// : userDao.checkUsername(CheckCard
			// .fixPersonIDCode15(userName));
			// }
			// if (isExistUserName) {
			// setElmessage("您注册的用户名已存在，请重新输入用户名！");
			// if (SystemConfOp
			// .getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
			// getRequest().setAttribute("isAll", "yes");
			// } else {
			// getRequest().setAttribute("isAll", "no");
			// }
			// return resultPage;
			// }
			// }

			// if (userDao.checkUsername(elUser.getUsername())) {
			// setElmessage("您注册的用户名已存在，请重新输入用户名！");
			// // 判断注册信息是否都要验证
			// if (SystemConfOp
			// .getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
			// getRequest().setAttribute("isAll", "yes");
			// } else {
			// getRequest().setAttribute("isAll", "no");
			// }
			// return resultPage;
			// }
			// if (userDao.checkUserShenfenzheng(elUser.getShenfenzheng(),
			// elUser
			// .getId())) {
			// setElmessage("您所填的身份证已被其他人使用，请重新输入！");
			// // 判断注册信息是否都要验证
			// // if (SystemConfOp
			// // .getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL))
			// {
			// // getRequest().setAttribute("isAll", "yes");
			// // } else {
			// // getRequest().setAttribute("isAll", "no");
			// // }
			// return resultPage;// 返回注册页面
			// }
			// 注册用户
			elUser.setValid(!SystemConfOp
					.getBooleanValue(ElConstants.REGISTER_NEED_SH));
			String tempPassword = elUser.getPassword();//
			elUser.setPassword(MD5.crypt(elUser.getPassword()));
			userDao.insert_cisco(elUser);
			if (!SystemConfOp.getBooleanValue(ElConstants.REGISTER_NEED_SH)) {// 如果不需要注册审核
				elUser = userDao.query(elUser.getUsername());
				if (!SystemConfOp.getBooleanValue(ElConstants.REGISTER_NEED_SH)) {
					elUser.setPassword(tempPassword);
					elUser.setUsername(elUser.getUsername());
					boolean b = httpRequestDeviceUtils
							.isMobileDevice(getRequest());
					if (b == true) {
						return "cisco_user_center_login_phone";
					}
					return "cisco_user_center_login";
				} else {
					boolean b = httpRequestDeviceUtils
							.isMobileDevice(getRequest());
					if (b == true) {
						return "cisco_register_success_phone";
					}
					return "cisco_register_success";
				}
			}

		} else {
			setElmessage("系统关闭了注册功能，请与管理员联系");
			return "error";
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "cisco_register_phone";
		}
		return "cisco_register";
	}

	// 外联用户登录
	public String wailian_user_center_login() throws ElException {
		System.out.println(elUser.getUsername() + "   ==   "
				+ elUser.getPassword());
		if (isFromRegister != 1) {// 不是注册成功后的登录
			if (yzCodeIsNo != 1) {// 不是从前台首页调用的action
				yzCodeIsNo = SystemConfOp
						.getIntValue(ElConstants.SYSTEM_CONF_YZCODE_OPEN);
				if (yzCodeIsNo == 1) {
					if (yzCode == null || yzCode.equals("")) {
						this.setElmessage("验证码不能为空,请填写验证码!!!");
						return "cisco_user_center_login";
					} else {
						if ((getSession().getAttribute("yzCodey") != null && !((String) getSession()
								.getAttribute("yzCodey")).equals(yzCode))) {
							this.setElmessage("验证码错误,请填写验证码!!!");
							return "cisco_user_center_login";
						}
					}
				}
			}
		}
		if (elUser == null) {
			setElmessage("请从正常入口进入！");
			return "error";
		}

		// 判断是否填写用户名
		if (elUser.getUsername() == null
				|| "".equals(elUser.getUsername().trim())) {
			setElmessage("请填写用户名！");
			return "cisco_user_center_login";
		}
		// 将用户名转成小写（库中存储着小写字符）
		elUser.setUsername(elUser.getUsername().trim().toLowerCase());
		String username = "";
		if (elUser.getUsername().length() == 15) {
			// 用户名15位转换成18位
			username = CheckCard.fixPersonIDCode(elUser.getUsername())
					.toLowerCase();
		} else if (elUser.getUsername().trim().length() == 18) {
			// 用户名18位的话转成15位
			username = CheckCard.fixPersonIDCode15(elUser.getUsername())
					.toLowerCase();
			;
		} else {
			username = elUser.getUsername();
		}
		// 检测15位和18位和密码是否匹配
		if (userDao
				.check(elUser.getUsername(), MD5.crypt(elUser.getPassword()))
				|| userDao.check(username, MD5.crypt(elUser.getPassword()))) {
			// 校验通过
			getSession().removeAttribute("yzCodey");
			// 获取用户信息
			elUser = userDao.query(elUser.getUsername().trim());
			if (elUser.getRole().getId() != 232) {
				setElmessage("您不是外联单位的用户，不能使用该登录口！");
				return "error";
			}
			if (elUser.getId() == 0)// 登录账号不符合的时候查询转换后的账号
				elUser = userDao.query(username);
			// 检测是否已经在线
			if (OnlineUtil.checkUser(elUser.getId() + "")) {
				// 查出上次该用户的最后登录信息
				if (myLogin == null) {
					myLogin = new MyLogin();
				}
				String tempIpAddr = myLogin.getIpAddr();
				myLogin = userDao.getSessionUserLoginInfo(elUser.getId());
				getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
				return "login_logout";
			}
			if (elUser.getValid()) {
				// 如果是超级管理员
				if (elUser.getRole().getId() == 1) {
					getSession().setAttribute(ElConstants.SESSION_USERID,
							elUser.getId());
					getSession().setAttribute(ElConstants.SESSION_USERNAME,
							elUser.getUsername().trim());
					getSession().setAttribute(ElConstants.SESSION_REALNAME,
							elUser.getRealname());
					getSession().setAttribute(ElConstants.SESSION_ROLE,
							elUser.getRole().getId());
					getSession().setAttribute(ElConstants.SESSION_ROLENAME,
							elUser.getRole().getName());
					// 如果是超管，部门id为根
					if (elUser.getRole().getId() == 1) {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT, 1);
					} else {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,
							elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser = userDao.getUserById(elUser.getId());

					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
							elUser.getShenfenzheng());

					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
							elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if (myLogin == null) {
						myLogin = new MyLogin();
					}
					// 判断是否需要记录ip
					if (SystemConfOp
							.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);

					// // 一下代码用于培训班分配给部门
					// List<Integer> depparentidList = new ArrayList<Integer>();
					// depparentidList = shoppingDao.getdempParentid(elUser
					// .getDepartment().getId());// 得到该用户所有上级部门id
					//
					// List<Integer> depclassidList = new ArrayList<Integer>();
					// for (Integer pid : depparentidList) {
					// depclassidList = shoppingDao
					// .getdepartmenttoclassbydepid(pid);//
					// 循环所有父部门ID并找出该部门被分配的培训班集合
					// for (Integer elclassid : depclassidList) {//
					// 循环该集合进行培训班的绑定和分配
					// classDao.assign2userAdd3(elUser.getId(), elclassid,
					// ClassConstants.CLASS_SQFS_FP);
					//
					// // 分配考场
					// examroom_classassignwcInit(elclassid, elUser
					// .getId());
					// }
					// }

					// if (isFromAdmin != null && isFromAdmin.equals("1")) {
					// return "user_admin_login_success";
					// }

					return "wailian_user_center_login_success";
				} else {
					getSession().setAttribute(ElConstants.SESSION_USERID,
							elUser.getId());
					getSession().setAttribute(ElConstants.SESSION_USERNAME,
							elUser.getUsername().trim());
					getSession().setAttribute(ElConstants.SESSION_REALNAME,
							elUser.getRealname());
					getSession().setAttribute(ElConstants.SESSION_ROLE,
							elUser.getRole().getId());
					getSession().setAttribute(ElConstants.SESSION_ROLENAME,
							elUser.getRole().getName());
					// 如果是超管，部门id为根
					if (elUser.getRole().getId() == 1) {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT, 1);
					} else {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,
							elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser = userDao.getUserById(elUser.getId());

					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
							elUser.getShenfenzheng());

					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
							elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if (myLogin == null) {
						myLogin = new MyLogin();
					}
					// 判断是否需要记录ip
					if (SystemConfOp
							.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);

					// // 一下代码用于培训班分配给部门
					// List<Integer> depparentidList = new ArrayList<Integer>();
					// depparentidList = shoppingDao.getdempParentid(elUser
					// .getDepartment().getId());// 得到该用户所有上级部门id
					//
					// List<Integer> depclassidList = new ArrayList<Integer>();
					// for (Integer pid : depparentidList) {
					// depclassidList = shoppingDao
					// .getdepartmenttoclassbydepid(pid);//
					// 循环所有父部门ID并找出该部门被分配的培训班集合
					// for (Integer elclassid : depclassidList) {//
					// 循环该集合进行培训班的绑定和分配
					// classDao.assign2userAdd3(elUser.getId(), elclassid,
					// ClassConstants.CLASS_SQFS_FP);
					//
					// // 分配考场
					// examroom_classassignwcInit(elclassid, elUser
					// .getId());
					// }
					// }
					//
					// if (isFromAdmin != null && isFromAdmin.equals("1")) {
					// return "user_admin_login_success";
					// }

					return "wailian_user_center_login_success";
				}
			} else {
				setElmessage("账号没开通，请与管理员联系！");
			}
		} else {
			// 检测用户名是否存在
			if (!userDao.checkUsername(elUser.getUsername().trim())
					&& !userDao.checkUsername(username.trim())) {
				setElmessage("用户名不存在");// 数据库中是18位，用15位登录，提示用户名不存在
			} else {
				setElmessage("用户名或密码有错");
			}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "wailian_user_center_login_phone";
		}
		return "wailian_user_center_login";
	}

	// 外联用户个人中心
	public String wailian_user_center() throws ElException {
		module = module == null ? "wailian_user_center_index.action" : module;
		Return = "studentman";
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "wailian_user_center_phone";
		}
		return "wailian_user_center";
	}

	// 外联用户个人中心frame
	public String wailian_user_center_index() throws ElException {
		menus = AuthorityNewVersionUtil.getListElFuncByRoleid(String
				.valueOf(getSessionIntValue(ElConstants.SESSION_ROLE)));
		menus_three = new ArrayList<ElFunc>();
		List<ElFunc> child = null;
		for (int i = 0; i < menus.size(); i++) {
			menu = menus.get(i);
			if (menu != null && menu.getChild() != null) {
				child = menu.getChild();
				if (child != null) {
					menus_three.addAll(child);
				}
			}
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
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());

		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		// 论坛信息
		// forums =
		// forumAdminDao.newVersionGetForums(getPageNow(),getPageSize());
		forums = forumAdminDao.listForumsByUid(
				getSessionIntValue(ElConstants.SESSION_USERID), "", 5,
				getPageSize());
		topics = forumAdminDao.myListTopic(
				getSessionIntValue(ElConstants.SESSION_USERID), 6,
				getPageSize());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);

		}
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1
				: ntype.getId());
		// 新闻信息
		newses = newsDao.listFabuNewses(6, ntypeTree, nid, getPageNow(),
				getPageSize());
		// 知识
		kledges = knowledgeManageDao.listKledgeAll(department,
				KnowledgeManageConstants.STATUS_ALL, getPageNow(),
				getPageSize());
		// 短消息
		newMessage = messageDao.listMessNewAll(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		// 未读
		message_no = messageDao
				.getMessNoCount(getSessionIntValue(ElConstants.SESSION_USERID));
		// 已读
		message_yes = messageDao
				.getMessYesCount(getSessionIntValue(ElConstants.SESSION_USERID));

		// //用户是否选择培训班
		// if(getSessionValue(ElConstants.SESSION_USERNAME)!=null){
		// isChangeElclass =
		// studyClassDao.getIsChangeclass(getSessionIntValue(ElConstants.SESSION_USERID));
		// }
		//		
		// //系统培训班(拿证培训班)
		// MyClass cla_ =
		// studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
		//		
		//		
		//		
		// //最新一期培训班，比较createtime
		// //首先获取系统年份，再查询系统中年份为当前年份的培训班
		// Calendar cal = Calendar.getInstance();
		// int year = cal.get(Calendar.YEAR);
		// isBuyNianjianClass=studyClassDao.isNianjianClass(getSessionIntValue(ElConstants.SESSION_USERID));
		// MyClass new_cla_ = studyClassDao.getNaZhengClass(year);
		// MyClass nianjian_cla_ = studyClassDao.getNianjianClass(year);
		//		
		// if(isBuyNianjianClass==0){
		// int classid=0;
		// //以下为对最新一期培训班的处理
		// classid = new_cla_.getElClass().getId();
		//		
		// if(classid!=0){
		// // int roomid = eroomDao.getRoomidByClassid_cisco(classid);
		// // if(classid!=0 && roomid!=0){
		// // room = eroomDao.getExamRoomByid(roomid);
		// //
		// new_cla_.getElClass().setExamRooms(eroomDao.listExamRoomByClass_cisco(classid,getSessionIntValue(ElConstants.SESSION_USERID)));
		// // for(int i=0;i<new_cla_.getElClass().getExamRooms().size();i++){
		// //
		// new_cla_.getElClass().getExamRooms().get(i).setIsPass(eroomDao.getIsPass(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getExamRooms().get(i).getId()));
		// // }
		// // }
		// //
		// // else{
		// // room = new ExamRoom();
		// // room.setId(0);
		// // }
		// //检查拿证培训班或者年检培训班，用户是否已经购买
		// boolean cla_check = false;
		// if(cla_!=null){
		// cla_check = studyClassDao.checkClassIsUser(cla_.getElClass().getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		// }
		// boolean new_check = false;
		// if(new_cla_!=null){
		// new_check =
		// studyClassDao.checkClassIsUser(new_cla_.getElClass().getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		// }
		// if(cla_check || new_check){
		// needAllocation = true;
		// //培训班中的课程
		// //查询培训班课程分配表
		// studyCourseList =
		// studyClassDao.getCourses(classid,getSessionIntValue(ElConstants.SESSION_USERID));
		// }
		// //培训班是否通过
		// int
		// status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),classid);
		// if(status==2){
		// new_cla_.setPassed(true);
		// }else{
		// new_cla_.setPassed(false);
		// }
		//			
		// }
		//		
		//		
		//		
		// studyCourseList = studyCourseList == null?new
		// ArrayList<MyCourse>():studyCourseList;
		// //学时及比例
		// map = NewVersionUtil.getCourseProcess(studyCourseList);
		//		
		//		
		// // MyClass myClass =
		// studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
		// // boolean flag = false;
		// boolean flag1 = false;
		// if(new_cla_!=null){
		// flag1 =
		// classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID),
		// new_cla_.getElClass().getId());
		// }
		// boolean flag2 = false;
		// if(cla_!=null){
		// flag2 =
		// classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID),
		// cla_.getElClass().getId());
		// }
		// // int temp = 0;
		// if(flag1 || flag2){//购买拿证培训班或者最新一期培训班
		// if(flag1){//购买最新一期培训班
		// // temp = 1;
		// if(!flag1){//未购买培训班
		// step = 1;
		// }else{
		// step = 2;//购买培训班
		// if(new_cla_.getElClass().getClasstype()==2){
		// //检测是否通过自主培训班
		// studyClassDao.setMyPassclass_at(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getId());
		// }else{
		// //检测是否通过培训班
		// studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getId());
		// }
		// int
		// status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getId());
		// if(status==2){//已达到考试条件（即培训班获得了证书）
		// step = 3;
		// }
		// if(isBuyNianjianClass==0){
		// if(new_cla_.getElClass().getExamRooms()!=null&&new_cla_.getElClass().getExamRooms().size()>0){
		// if(new_cla_.getElClass().getExamRooms().get(0).getIsPassed() ==
		// 1){//考试通过，可以查看证书
		// step = 4;
		// }
		// }}else{
		// if(nianjian_cla_.getElClass().getExamRooms()!=null&&nianjian_cla_.getElClass().getExamRooms().size()>0){
		// if(nianjian_cla_.getElClass().getExamRooms().get(0).getIsPassed() ==
		// 1){//考试通过，可以查看证书
		// step = 4;
		// }
		// }
		// }
		// }
		//				
		// }else{
		// if(!flag2){//未购买培训班
		// step = 1;
		// }else{
		// step = 2;//购买培训班
		// if(cla_.getElClass().getClasstype()==2){
		// //检测是否通过自主培训班
		// studyClassDao.setMyPassclass_at(getSessionIntValue(ElConstants.SESSION_USERID),cla_.getElClass().getId());
		// }else{
		// //检测是否通过培训班
		// studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),cla_.getElClass().getId());
		// }
		// int
		// status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),cla_.getElClass().getId());
		// if(status==2){//已达到考试条件（即培训班获得了证书）
		// step = 3;
		// }
		// if(cla_.getElClass().getExamRooms()!=null&&cla_.getElClass().getExamRooms().size()>0){
		// if(cla_.getElClass().getExamRooms().get(0).getIsPassed() ==
		// 1){//考试通过，可以查看证书
		// step = 4;
		// }
		// }
		// }
		// }
		// }}
		// else{
		// int classid=0;
		// //以下为对最新一期培训班的处理
		// if(isBuyNianjianClass==0){
		// classid = new_cla_.getElClass().getId();
		// }else{
		// classid = nianjian_cla_.getElClass().getId();
		// }
		//			
		// if(classid!=0){
		// // int roomid = eroomDao.getRoomidByClassid_cisco(classid);
		// // if(classid!=0 && roomid!=0){
		// // room = eroomDao.getExamRoomByid(roomid);
		// //
		// new_cla_.getElClass().setExamRooms(eroomDao.listExamRoomByClass_cisco(classid,getSessionIntValue(ElConstants.SESSION_USERID)));
		// // for(int i=0;i<new_cla_.getElClass().getExamRooms().size();i++){
		// //
		// new_cla_.getElClass().getExamRooms().get(i).setIsPass(eroomDao.getIsPass(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getExamRooms().get(i).getId()));
		// // }
		// // }
		// //
		// // else{
		// // room = new ExamRoom();
		// // room.setId(0);
		// // }
		// //检查拿证培训班或者年检培训班，用户是否已经购买
		// boolean cla_check = false;
		// if(cla_!=null){
		// cla_check = studyClassDao.checkClassIsUser(cla_.getElClass().getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		// }
		// boolean new_check = false;
		// if(new_cla_!=null){
		// new_check =
		// studyClassDao.checkClassIsUser(new_cla_.getElClass().getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		// }
		// if(cla_check || new_check){
		// needAllocation = true;
		// //培训班中的课程
		// //查询培训班课程分配表
		// studyCourseList =
		// studyClassDao.getCourses(classid,getSessionIntValue(ElConstants.SESSION_USERID));
		// }
		// //培训班是否通过
		// int
		// status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),classid);
		// if(status==2){
		// new_cla_.setPassed(true);
		// }else{
		// new_cla_.setPassed(false);
		// }
		//				
		// }
		//			
		//			
		//			
		// studyCourseList = studyCourseList == null?new
		// ArrayList<MyCourse>():studyCourseList;
		// //学时及比例
		// map = NewVersionUtil.getCourseProcess(studyCourseList);
		//			
		//			
		// // MyClass myClass =
		// studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
		// // boolean flag = false;
		// boolean flag1 = false;
		// if(new_cla_!=null){
		// flag1 =
		// classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID),
		// new_cla_.getElClass().getId());
		// }
		// boolean flag2 = false;
		// if(cla_!=null){
		// flag2 =
		// classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID),
		// cla_.getElClass().getId());
		// }
		// // int temp = 0;
		// if(flag1 || flag2){//购买拿证培训班或者最新一期培训班
		// if(flag1){//购买最新一期培训班
		// // temp = 1;
		// if(!flag1){//未购买培训班
		// step = 1;
		// }else{
		// step = 2;//购买培训班
		// if(new_cla_.getElClass().getClasstype()==2){
		// //检测是否通过自主培训班
		// studyClassDao.setMyPassclass_at(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getId());
		// }else{
		// //检测是否通过培训班
		// studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getId());
		// }
		// int status=0;
		// if(isBuyNianjianClass==0){
		// status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getId());
		// }else{
		// status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),nianjian_cla_.getElClass().getId());
		// }
		// if(status==2){//已达到考试条件（即培训班获得了证书）
		// step = 3;
		// }
		// if(isBuyNianjianClass==0){
		// if(new_cla_.getElClass().getExamRooms()!=null&&new_cla_.getElClass().getExamRooms().size()>0){
		// if(new_cla_.getElClass().getExamRooms().get(0).getIsPassed() ==
		// 1){//考试通过，可以查看证书
		// step = 4;
		// }
		// }}else{
		// if(nianjian_cla_.getElClass().getExamRooms()!=null&&nianjian_cla_.getElClass().getExamRooms().size()>0){
		// if(nianjian_cla_.getElClass().getExamRooms().get(0).getIsPassed() ==
		// 1){//考试通过，可以查看证书
		// step = 4;
		// }
		// }
		// }
		// }
		//					
		// }else{
		// if(!flag2){//未购买培训班
		// step = 1;
		// }else{
		// step = 2;//购买培训班
		// if(cla_.getElClass().getClasstype()==2){
		// //检测是否通过自主培训班
		// studyClassDao.setMyPassclass_at(getSessionIntValue(ElConstants.SESSION_USERID),cla_.getElClass().getId());
		// }else{
		// //检测是否通过培训班
		// studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),cla_.getElClass().getId());
		// }
		// int
		// status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),cla_.getElClass().getId());
		// if(status==2){//已达到考试条件（即培训班获得了证书）
		// step = 3;
		// }
		// if(cla_.getElClass().getExamRooms()!=null&&cla_.getElClass().getExamRooms().size()>0){
		// if(cla_.getElClass().getExamRooms().get(0).getIsPassed() ==
		// 1){//考试通过，可以查看证书
		// step = 4;
		// }
		// }
		// }
		// }
		// }
		// }
		// nianjian_cla = nianjian_cla_;
		// new_cla = new_cla_;
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "wailian_user_center_index_phone";
		}

		return "wailian_user_center_index";
	}

	// 外联结束

	// 外经贸注册
	public String wjm_register() throws ElException {
		String resultPage = "wjm_register";
		// 判断注册信息是否都要验证
		if (SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER) == 1) {
			// 对用户名是身份证号码的情况进行检查
			// String userName = elUser.getUsername().trim().toLowerCase();

			// if ("".equals(CheckCard.IDCardValidate(userName))) {//
			// 如果用户名是有效身份证
			// boolean isExistUserName = false;
			//
			// // 如果用户的身份证号码是15位，判断数据库中是否存在该人15位和18位的身份证号码
			// if (userName.length() == 15) {
			// isExistUserName = userDao.checkUsername(userName) ? true
			// : userDao.checkUsername(CheckCard
			// .fixPersonIDCode(userName));
			// } else {// 如果用户的身份证号码是18位，判断数据库中是否存在该人15位和18位的身份证号码
			// isExistUserName = userDao.checkUsername(userName) ? true
			// : userDao.checkUsername(CheckCard
			// .fixPersonIDCode15(userName));
			// }
			// if (isExistUserName) {
			// setElmessage("您注册的用户名已存在，请重新输入用户名！");
			// if (SystemConfOp
			// .getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
			// getRequest().setAttribute("isAll", "yes");
			// } else {
			// getRequest().setAttribute("isAll", "no");
			// }
			// return resultPage;
			// }
			// }

			// if (userDao.checkUsername(elUser.getUsername())) {
			// setElmessage("您注册的用户名已存在，请重新输入用户名！");
			// // 判断注册信息是否都要验证
			// if (SystemConfOp
			// .getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
			// getRequest().setAttribute("isAll", "yes");
			// } else {
			// getRequest().setAttribute("isAll", "no");
			// }
			// return resultPage;
			// }
			// if (userDao.checkUserShenfenzheng(elUser.getShenfenzheng(),
			// elUser
			// .getId())) {
			// setElmessage("您所填的身份证已被其他人使用，请重新输入！");
			// // 判断注册信息是否都要验证
			// // if (SystemConfOp
			// // .getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL))
			// {
			// // getRequest().setAttribute("isAll", "yes");
			// // } else {
			// // getRequest().setAttribute("isAll", "no");
			// // }
			// return resultPage;// 返回注册页面
			// }
			// 注册用户
			elUser.setValid(!SystemConfOp
					.getBooleanValue(ElConstants.REGISTER_NEED_SH));
			String tempPassword = elUser.getPassword();//
			elUser.setPassword(MD5.crypt(elUser.getPassword()));
			userDao.insert_cisco(elUser);
			if (!SystemConfOp.getBooleanValue(ElConstants.REGISTER_NEED_SH)) {// 如果不需要注册审核
				elUser = userDao.query(elUser.getShenfenzheng());
				if (!SystemConfOp.getBooleanValue(ElConstants.REGISTER_NEED_SH)) {
					elUser.setPassword(tempPassword);
					elUser.setUsername(elUser.getUsername());
					return "wjm_user_center_login";
				} else {
					return "wjm_register_success";
				}
			}

		} else {
			setElmessage("系统关闭了注册功能，请与管理员联系");
			return "error";
		}
		return "wjm_register";
	}

	// 北京卫生局新版个人中心登录
	public String cisco_user_center_login() throws ElException {
		// System.out.println(elUser.getUsername() + " == " +
		// elUser.getPassword());
		if (isFromRegister != 1) {// 不是注册成功后的登录
			if (yzCodeIsNo != 1) {// 不是从前台首页调用的action
				yzCodeIsNo = SystemConfOp
						.getIntValue(ElConstants.SYSTEM_CONF_YZCODE_OPEN);
				if (yzCodeIsNo == 1) {
					if (yzCode == null || yzCode.equals("")) {
						this.setElmessage("验证码不能为空,请填写验证码!!!");
						return "cisco_user_center_login";
					} else {
						if ((getSession().getAttribute("yzCodey") != null && !((String) getSession()
								.getAttribute("yzCodey")).equals(yzCode))) {
							this.setElmessage("验证码错误,请填写验证码!!!");
							return "cisco_user_center_login";
						}
					}
				}
			}
		}
		if (elUser == null) {
			setElmessage("请从正常入口进入！");
			return "error";
		}

		// 判断是否填写用户名
		if (elUser.getUsername() == null
				|| "".equals(elUser.getUsername().trim())) {
			setElmessage("请填写用户名！");
			return "cisco_user_center_login";
		}
		// 将用户名转成小写（库中存储着小写字符）
		elUser.setUsername(elUser.getUsername().trim().toLowerCase());
		String username = "";
		if (elUser.getUsername().length() == 15) {
			// 用户名15位转换成18位
			username = CheckCard.fixPersonIDCode(elUser.getUsername())
					.toLowerCase();
		} else if (elUser.getUsername().trim().length() == 18) {
			// 用户名18位的话转成15位
			username = CheckCard.fixPersonIDCode15(elUser.getUsername())
					.toLowerCase();
			;
		} else {
			username = elUser.getUsername();
		}
		// 检测15位和18位和密码是否匹配
		if (userDao
				.check(elUser.getUsername(), MD5.crypt(elUser.getPassword()))
				|| userDao.check(username, MD5.crypt(elUser.getPassword()))) {
			// 校验通过
			getSession().removeAttribute("yzCodey");
			// 获取用户信息
			elUser = userDao.query(elUser.getUsername().trim());
			
			//20141014检测上次是否正确退出
			userDao.checkUserIsExittime(elUser.getId());
			if (elUser.getId() == 0)// 登录账号不符合的时候查询转换后的账号
				elUser = userDao.query(username);
			// 检测是否已经在线
			if (OnlineUtil.checkUser(elUser.getId() + "")) {
				// 查出上次该用户的最后登录信息
				if (myLogin == null) {
					myLogin = new MyLogin();
				}
				String tempIpAddr = myLogin.getIpAddr();
				myLogin = userDao.getSessionUserLoginInfo(elUser.getId());
				getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
				return "login_logout";
			}
			if (elUser.getValid()) {
				// 如果是超级管理员
				if (elUser.getRole().getId() == 1) {
					getSession().setAttribute(ElConstants.SESSION_USERID,
							elUser.getId());
					getSession().setAttribute(ElConstants.SESSION_USERNAME,
							elUser.getUsername().trim());
					getSession().setAttribute(ElConstants.SESSION_REALNAME,
							elUser.getRealname());
					getSession().setAttribute(ElConstants.SESSION_ROLE,
							elUser.getRole().getId());
					getSession().setAttribute(ElConstants.SESSION_ROLENAME,
							elUser.getRole().getName());
					// 如果是超管，部门id为根
					if (elUser.getRole().getId() == 1) {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT, 1);
					} else {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,
							elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser = userDao.getUserById(elUser.getId());

					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
							elUser.getShenfenzheng());

					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
							elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if (myLogin == null) {
						myLogin = new MyLogin();
					}
					// 判断是否需要记录ip
					if (SystemConfOp
							.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);

					// // 一下代码用于培训班分配给部门
					// List<Integer> depparentidList = new ArrayList<Integer>();
					// depparentidList = shoppingDao.getdempParentid(elUser
					// .getDepartment().getId());// 得到该用户所有上级部门id
					//
					// List<Integer> depclassidList = new ArrayList<Integer>();
					// for (Integer pid : depparentidList) {
					// depclassidList = shoppingDao
					// .getdepartmenttoclassbydepid(pid);//
					// 循环所有父部门ID并找出该部门被分配的培训班集合
					// for (Integer elclassid : depclassidList) {//
					// 循环该集合进行培训班的绑定和分配
					// classDao.assign2userAdd3(elUser.getId(), elclassid,
					// ClassConstants.CLASS_SQFS_FP);
					//
					// // 分配考场
					// examroom_classassignwcInit(elclassid, elUser
					// .getId());
					// }
					// }

					// if (isFromAdmin != null && isFromAdmin.equals("1")) {
					// return "user_admin_login_success";
					// }

					return "cisco_user_center_login_success";
				} else {
					getSession().setAttribute(ElConstants.SESSION_USERID,
							elUser.getId());
					getSession().setAttribute(ElConstants.SESSION_USERNAME,
							elUser.getUsername().trim());
					getSession().setAttribute(ElConstants.SESSION_REALNAME,
							elUser.getRealname());
					getSession().setAttribute(ElConstants.SESSION_ROLE,
							elUser.getRole().getId());
					getSession().setAttribute(ElConstants.SESSION_ROLENAME,
							elUser.getRole().getName());
					// 如果是超管，部门id为根
					if (elUser.getRole().getId() == 1) {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT, 1);
					} else {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,
							elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser = userDao.getUserById(elUser.getId());

					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
							elUser.getShenfenzheng());

					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
							elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if (myLogin == null) {
						myLogin = new MyLogin();
					}
					// 判断是否需要记录ip
					if (SystemConfOp
							.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);

					// // 一下代码用于培训班分配给部门
					// List<Integer> depparentidList = new ArrayList<Integer>();
					// depparentidList = shoppingDao.getdempParentid(elUser
					// .getDepartment().getId());// 得到该用户所有上级部门id
					//
					// List<Integer> depclassidList = new ArrayList<Integer>();
					// for (Integer pid : depparentidList) {
					// depclassidList = shoppingDao
					// .getdepartmenttoclassbydepid(pid);//
					// 循环所有父部门ID并找出该部门被分配的培训班集合
					// for (Integer elclassid : depclassidList) {//
					// 循环该集合进行培训班的绑定和分配
					// classDao.assign2userAdd3(elUser.getId(), elclassid,
					// ClassConstants.CLASS_SQFS_FP);
					//
					// // 分配考场
					// examroom_classassignwcInit(elclassid, elUser
					// .getId());
					// }
					// }
					//
					// if (isFromAdmin != null && isFromAdmin.equals("1")) {
					// return "user_admin_login_success";
					// }

					return "cisco_user_center_login_success";
				}
			} else {
				setElmessage("账号没开通，请与管理员联系！");
			}
		} else {
			// 检测用户名是否存在
			if (!userDao.checkUsername(elUser.getUsername().trim())
					&& !userDao.checkUsername(username.trim())) {
				setElmessage("用户名不存在");// 数据库中是18位，用15位登录，提示用户名不存在
			} else {
				setElmessage("用户名或密码有错");
			}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "cisco_user_center_login_phone";
		}
		return "cisco_user_center_login";
	}

	
	
	
	
	//-------------------------------------------wjm_admin_login------------------------------------------------
	
	
	public String wjm_admin_login() throws ElException {
		// System.out.println(elUser.getUsername() + " == " +
		// elUser.getPassword());
		if (isFromRegister != 1) {// 不是注册成功后的登录
			if (yzCodeIsNo != 1) {// 不是从前台首页调用的action
				yzCodeIsNo = SystemConfOp
						.getIntValue(ElConstants.SYSTEM_CONF_YZCODE_OPEN);
				if (yzCodeIsNo == 1) {
					if (yzCode == null || yzCode.equals("")) {
						this.setElmessage("验证码不能为空,请填写验证码!!!");
						return "wjm_admin_login";
					} else {
						if ((getSession().getAttribute("yzCodey") != null && !((String) getSession()
								.getAttribute("yzCodey")).equals(yzCode))) {
							this.setElmessage("验证码错误,请填写验证码!!!");
							return "wjm_admin_login";
						}
					}
				}
			}
		}
		if (elUser == null) {
			setElmessage("请从正常入口进入！");
			return "error";
		}

		// 判断是否填写用户名
		if (elUser.getUsername() == null
				|| "".equals(elUser.getUsername().trim())) {
			setElmessage("请填写用户名！");
			return "wjm_admin_login";
		}
		// 将用户名转成小写（库中存储着小写字符）
		elUser.setUsername(elUser.getUsername().trim().toLowerCase());
		String username = "";
		if (elUser.getUsername().length() == 15) {
			// 用户名15位转换成18位
			username = CheckCard.fixPersonIDCode(elUser.getUsername())
					.toLowerCase();
		} else if (elUser.getUsername().trim().length() == 18) {
			// 用户名18位的话转成15位
			username = CheckCard.fixPersonIDCode15(elUser.getUsername())
					.toLowerCase();
			;
		} else {
			username = elUser.getUsername();
		}
		// 检测15位和18位和密码是否匹配
		if (userDao
				.check(elUser.getUsername(), MD5.crypt(elUser.getPassword()))
				|| userDao.check(username, MD5.crypt(elUser.getPassword()))) {
			// 校验通过
			getSession().removeAttribute("yzCodey");
			// 获取用户信息
			elUser = userDao.query(elUser.getUsername().trim());
			//20141014检测上次是否正确退出
			userDao.checkUserIsExittime(elUser.getId());
			if(elUser!=null&&elUser.getRole().getId()==4){
				setElmessage("这是教师账号专用的登录口，请您从学生登录口登录！");
				return "wjm_admin_login";
			}
			if (elUser.getId() == 0)// 登录账号不符合的时候查询转换后的账号
				elUser = userDao.query(username);
			// 检测是否已经在线
			if (OnlineUtil.checkUser(elUser.getId() + "")) {
				// 查出上次该用户的最后登录信息
				if (myLogin == null) {
					myLogin = new MyLogin();
				}
				String tempIpAddr = myLogin.getIpAddr();
				myLogin = userDao.getSessionUserLoginInfo(elUser.getId());
				getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
				return "login_logout";
			}
			if (elUser.getValid()) {
				// 如果是超级管理员
				if (elUser.getRole().getId() == 1) {
					getSession().setAttribute(ElConstants.SESSION_USERID,
							elUser.getId());
					getSession().setAttribute(ElConstants.SESSION_USERNAME,
							elUser.getUsername().trim());
					getSession().setAttribute(ElConstants.SESSION_REALNAME,
							elUser.getRealname());
					getSession().setAttribute(ElConstants.SESSION_ROLE,
							elUser.getRole().getId());
					getSession().setAttribute(ElConstants.SESSION_ROLENAME,
							elUser.getRole().getName());
					// 如果是超管，部门id为根
					if (elUser.getRole().getId() == 1) {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT, 1);
					} else {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,
							elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser = userDao.getUserById(elUser.getId());

					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
							elUser.getShenfenzheng());

					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
							elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if (myLogin == null) {
						myLogin = new MyLogin();
					}
					// 判断是否需要记录ip
					if (SystemConfOp
							.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);

					// // 一下代码用于培训班分配给部门
					// List<Integer> depparentidList = new ArrayList<Integer>();
					// depparentidList = shoppingDao.getdempParentid(elUser
					// .getDepartment().getId());// 得到该用户所有上级部门id
					//
					// List<Integer> depclassidList = new ArrayList<Integer>();
					// for (Integer pid : depparentidList) {
					// depclassidList = shoppingDao
					// .getdepartmenttoclassbydepid(pid);//
					// 循环所有父部门ID并找出该部门被分配的培训班集合
					// for (Integer elclassid : depclassidList) {//
					// 循环该集合进行培训班的绑定和分配
					// classDao.assign2userAdd3(elUser.getId(), elclassid,
					// ClassConstants.CLASS_SQFS_FP);
					//
					// // 分配考场
					// examroom_classassignwcInit(elclassid, elUser
					// .getId());
					// }
					// }

					// if (isFromAdmin != null && isFromAdmin.equals("1")) {
					// return "user_admin_login_success";
					// }

					return "wjm_admin_login_success";
				} else {
					getSession().setAttribute(ElConstants.SESSION_USERID,
							elUser.getId());
					getSession().setAttribute(ElConstants.SESSION_USERNAME,
							elUser.getUsername().trim());
					getSession().setAttribute(ElConstants.SESSION_REALNAME,
							elUser.getRealname());
					getSession().setAttribute(ElConstants.SESSION_ROLE,
							elUser.getRole().getId());
					getSession().setAttribute(ElConstants.SESSION_ROLENAME,
							elUser.getRole().getName());
					// 如果是超管，部门id为根
					if (elUser.getRole().getId() == 1) {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT, 1);
					} else {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,
							elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser = userDao.getUserById(elUser.getId());

					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
							elUser.getShenfenzheng());

					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
							elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if (myLogin == null) {
						myLogin = new MyLogin();
					}
					// 判断是否需要记录ip
					if (SystemConfOp
							.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);

					// // 一下代码用于培训班分配给部门
					// List<Integer> depparentidList = new ArrayList<Integer>();
					// depparentidList = shoppingDao.getdempParentid(elUser
					// .getDepartment().getId());// 得到该用户所有上级部门id
					//
					// List<Integer> depclassidList = new ArrayList<Integer>();
					// for (Integer pid : depparentidList) {
					// depclassidList = shoppingDao
					// .getdepartmenttoclassbydepid(pid);//
					// 循环所有父部门ID并找出该部门被分配的培训班集合
					// for (Integer elclassid : depclassidList) {//
					// 循环该集合进行培训班的绑定和分配
					// classDao.assign2userAdd3(elUser.getId(), elclassid,
					// ClassConstants.CLASS_SQFS_FP);
					//
					// // 分配考场
					// examroom_classassignwcInit(elclassid, elUser
					// .getId());
					// }
					// }
					//
					// if (isFromAdmin != null && isFromAdmin.equals("1")) {
					// return "user_admin_login_success";
					// }

					return "wjm_admin_login_success";
				}
			} else {
				setElmessage("账号没开通，请与管理员联系！");
			}
		} else {
			// 检测用户名是否存在
			if (!userDao.checkUsername(elUser.getUsername().trim())
					&& !userDao.checkUsername(username.trim())) {
				setElmessage("用户名不存在");// 数据库中是18位，用15位登录，提示用户名不存在
			} else {
				setElmessage("用户名或密码有错");
			}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "wjm_admin_login_phone";
		}
		return "wjm_admin_login";
	}
//---------------------------------------------------wjm_admin登陆结束---------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 外经贸个人中心登录
	public String wjm_user_center_login() throws ElException {
		System.out.println(elUser.getUsername() + "   ==   "
				+ elUser.getPassword());
		
		
		if (isFromRegister != 1) {// 不是注册成功后的登录
			if (yzCodeIsNo != 1) {// 不是从前台首页调用的action
				yzCodeIsNo = SystemConfOp
						.getIntValue(ElConstants.SYSTEM_CONF_YZCODE_OPEN);
				if (yzCodeIsNo == 1) {
					if (yzCode == null || yzCode.equals("")) {
						this.setElmessage("验证码不能为空,请填写验证码!!!");
						return "cisco_user_center_login";
					} else {
						if ((getSession().getAttribute("yzCodey") != null && !((String) getSession()
								.getAttribute("yzCodey")).equals(yzCode))) {
							this.setElmessage("验证码错误,请填写验证码!!!");
							return "cisco_user_center_login";
						}
					}
				}
			}
		}
		if (elUser == null) {
			setElmessage("请从正常入口进入！");
			return "error";
		}
		
		//20140827修改增加指纹识别登录
		if(elUser.getFingerInfo()!=null&&!"".equals(elUser.getFingerInfo().trim())){
			List<ELUser> els=userDao.getUserByFingerInfo();
			for(int i=0;i<els.size();i++){
				logger.info(fingerUtil.MatchTemplateEx(elUser.getFingerInfo(), els.get(i).getFingerInfo()));
				if(fingerUtil.MatchTemplateEx(elUser.getFingerInfo(), els.get(i).getFingerInfo())>100){
					
					logger.info(els.get(i).getId());
					elUser=userDao.getUserById_wjm(els.get(i).getId());
					if (elUser.getValid()) {
	 					// 如果是超级管理员
	 					if (elUser.getRole().getId() == 1) {
	 						getSession().setAttribute(ElConstants.SESSION_USERID,
	 								elUser.getId());
	 						getSession().setAttribute(ElConstants.SESSION_USERNAME,
	 								elUser.getUsername().trim());
	 						getSession().setAttribute(ElConstants.SESSION_REALNAME,
	 								elUser.getRealname());
	 						getSession().setAttribute(ElConstants.SESSION_ROLE,
	 								elUser.getRole().getId());
	 						getSession().setAttribute(ElConstants.SESSION_ROLENAME,
	 								elUser.getRole().getName());
	 						// 如果是超管，部门id为根
	 						if (elUser.getRole().getId() == 1) {
	 							getSession().setAttribute(
	 									ElConstants.SESSION_MYDEPARTMENT, 1);
	 						} else {
	 							getSession().setAttribute(
	 									ElConstants.SESSION_MYDEPARTMENT,
	 									elUser.getDepartment().getId());
	 						}
	 						getSession().setAttribute(ElConstants.SESSION_AGE,
	 								elUser.getAge());
	 						// 登录后 加显示 姓名 身份证 部门
	 						elUser = userDao.getUserById(elUser.getId());

	 						getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
	 								elUser.getShenfenzheng());

	 						getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
	 								elUser.getDepartment().getName());
	 						ScoreOperate.setScore(
	 								getSessionIntValue(ElConstants.SESSION_USERID),
	 								ElConstants.DIAN_LOGIN_DO);
	 						// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
	 						getSession().setAttribute("isLogin", "true");
	 						// 记录用户登入信息-------
	 						if (myLogin == null) {
	 							myLogin = new MyLogin();
	 						}
	 						// 判断是否需要记录ip
	 						if (SystemConfOp
	 								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
	 							myLogin.setIpAddr(null);
	 						}
	 						myLogin.setElUser(elUser);
	 						userDao.addUserLoginInfo(myLogin);
	 						
	 						// // 一下代码用于培训班分配给部门
	 						// List<Integer> depparentidList = new ArrayList<Integer>();
	 						// depparentidList = shoppingDao.getdempParentid(elUser
	 						// .getDepartment().getId());// 得到该用户所有上级部门id
	 						//
	 						// List<Integer> depclassidList = new ArrayList<Integer>();
	 						// for (Integer pid : depparentidList) {
	 						// depclassidList = shoppingDao
	 						// .getdepartmenttoclassbydepid(pid);//
	 						// 循环所有父部门ID并找出该部门被分配的培训班集合
	 						// for (Integer elclassid : depclassidList) {//
	 						// 循环该集合进行培训班的绑定和分配
	 						// classDao.assign2userAdd3(elUser.getId(), elclassid,
	 						// ClassConstants.CLASS_SQFS_FP);
	 						//
	 						// // 分配考场
	 						// examroom_classassignwcInit(elclassid, elUser
	 						// .getId());
	 						// }
	 						// }

	 						// if (isFromAdmin != null && isFromAdmin.equals("1")) {
	 						// return "user_admin_login_success";
	 						// }
	 						//wjm0211修改
	 						//用户定级批次分配
	 						if(!classificationDao.isDingji(getSessionIntValue(ElConstants.SESSION_USERID))){
	 						//获取培训批次
	 						peixunBatch = peixunBatchDao.getPeixunBatchById(1);
	 						
	 						if(peixunBatch!=null && peixunBatch.getId()>0){
	 							//加入到培训批次和培训批次中所有的培训班
	 							peixunBatchDao.addBatchEluser(Integer.valueOf(peixunBatch.getId()),getSessionIntValue(ElConstants.SESSION_USERID));
	 							//培训批次中每个培训班中的每门课程分配给用户
	 							peixunBatchDao.addBatchClass_course(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),ClassConstants.CLASS_SQFS_FP);
	 						}
//	 						//判断分数段在哪个阶段，若分数为36，分数处于2B，则将2A及2A以前的培训班进度改为100%,培训班的所有课程进度改为100%
//	 						float myscore = myExamPaper.getMyScore();
//	 						//根据保存定级信息表查出所有培训班，然后比较定级
	 						//classification.name == '2A'
	 						List<ElClass> elclasses = peixunBatchDao.getElclassList(peixunBatch.getId());
	 						float process = 100.00f;
	 						boolean flag = false;//标识是否更新了培训班进度
	 						for(ElClass el:elclasses){
	 							//0211修改wjm定级考试
//	 							if(el.getName().compareTo(classification.getName())<0){
	 							//0402修改暂时定为6A
	 							if(el.getName().compareTo("4A")<0){
//	 							if(el.getName().compareTo("6A")<0){
	 								//更新培训班进度为100
	 								if(classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID), el.getId())){
	 									//则将2A及2A以前的培训班进度改为100%
	 									classDao.updateClassProcessByClassid(el.getId(),process,getSessionIntValue(ElConstants.SESSION_USERID));
	 									//培训班的所有课程进度改为100%
	 									courseDao.updateCourseProcessByClassid(el.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
	 									flag = true;
	 								}
	 							}
	 						}
	 						if(flag){
	 							peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
	 						}
	 						classificationDao.addExceptionData_new(getSessionIntValue(ElConstants.SESSION_USERID), classificationDao.getRoomid(), 1);
	 						}
	 						intelligentLogin = IntelligentLoginUtil
	 						.intelligentLogin(elUser.getId());
	 						return "wjm_user_center_login_success";
	 					} else {
	 						getSession().setAttribute(ElConstants.SESSION_USERID,
	 								elUser.getId());
	 						getSession().setAttribute(ElConstants.SESSION_USERNAME,
	 								elUser.getUsername().trim());
	 						getSession().setAttribute(ElConstants.SESSION_REALNAME,
	 								elUser.getRealname());
	 						getSession().setAttribute(ElConstants.SESSION_ROLE,
	 								elUser.getRole().getId());
	 						getSession().setAttribute(ElConstants.SESSION_ROLENAME,
	 								elUser.getRole().getName());
	 						// 如果是超管，部门id为根
	 						if (elUser.getRole().getId() == 1) {
	 							getSession().setAttribute(
	 									ElConstants.SESSION_MYDEPARTMENT, 1);
	 						} else {
	 							getSession().setAttribute(
	 									ElConstants.SESSION_MYDEPARTMENT,
	 									elUser.getDepartment().getId());
	 						}
	 						getSession().setAttribute(ElConstants.SESSION_AGE,
	 								elUser.getAge());
	 						// 登录后 加显示 姓名 身份证 部门
	 						elUser = userDao.getUserById(elUser.getId());

	 						getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
	 								elUser.getShenfenzheng());

	 						getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
	 								elUser.getDepartment().getName());
	 						ScoreOperate.setScore(
	 								getSessionIntValue(ElConstants.SESSION_USERID),
	 								ElConstants.DIAN_LOGIN_DO);
	 						// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
	 						getSession().setAttribute("isLogin", "true");
	 						// 记录用户登入信息-------
	 						if (myLogin == null) {
	 							myLogin = new MyLogin();
	 						}
	 						// 判断是否需要记录ip
	 						if (SystemConfOp
	 								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
	 							myLogin.setIpAddr(null);
	 						}
	 						myLogin.setElUser(elUser);
	 						userDao.addUserLoginInfo(myLogin);
	 						intelligentLogin = IntelligentLoginUtil
	 								.intelligentLogin(elUser.getId());
	 						// // 一下代码用于培训班分配给部门
	 						// List<Integer> depparentidList = new ArrayList<Integer>();
	 						// depparentidList = shoppingDao.getdempParentid(elUser
	 						// .getDepartment().getId());// 得到该用户所有上级部门id
	 						//
	 						// List<Integer> depclassidList = new ArrayList<Integer>();
	 						// for (Integer pid : depparentidList) {
	 						// depclassidList = shoppingDao
	 						// .getdepartmenttoclassbydepid(pid);//
	 						// 循环所有父部门ID并找出该部门被分配的培训班集合
	 						// for (Integer elclassid : depclassidList) {//
	 						// 循环该集合进行培训班的绑定和分配
	 						// classDao.assign2userAdd3(elUser.getId(), elclassid,
	 						// ClassConstants.CLASS_SQFS_FP);
	 						//
	 						// // 分配考场
	 						// examroom_classassignwcInit(elclassid, elUser
	 						// .getId());
	 						// }
	 						// }
	 						//
	 						// if (isFromAdmin != null && isFromAdmin.equals("1")) {
	 						// return "user_admin_login_success";
	 						// }
	 						//wjm0211修改
	 						//用户定级批次分配
	 						if(!classificationDao.isDingji(getSessionIntValue(ElConstants.SESSION_USERID))){
	 						//获取培训批次
	 						peixunBatch = peixunBatchDao.getPeixunBatchById(1);
	 						
	 						if(peixunBatch!=null && peixunBatch.getId()>0){
	 							//加入到培训批次和培训批次中所有的培训班
	 							peixunBatchDao.addBatchEluser(Integer.valueOf(peixunBatch.getId()),getSessionIntValue(ElConstants.SESSION_USERID));
	 							//培训批次中每个培训班中的每门课程分配给用户
	 							peixunBatchDao.addBatchClass_course(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),ClassConstants.CLASS_SQFS_FP);
	 						}
//	 						//判断分数段在哪个阶段，若分数为36，分数处于2B，则将2A及2A以前的培训班进度改为100%,培训班的所有课程进度改为100%
//	 						float myscore = myExamPaper.getMyScore();
//	 						//根据保存定级信息表查出所有培训班，然后比较定级
	 						//classification.name == '2A'
	 						List<ElClass> elclasses = peixunBatchDao.getElclassList(peixunBatch.getId());
	 						float process = 100.00f;
	 						boolean flag = false;//标识是否更新了培训班进度
	 						for(ElClass el:elclasses){
	 							//0211修改wjm定级考试
//	 							if(el.getName().compareTo(classification.getName())<0){
	 							if(el.getName().compareTo("4A")<0){
	 							//暂时定为6A
//	 							if(el.getName().compareTo("6A")<0){
	 								//更新培训班进度为100
	 								if(classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID), el.getId())){
	 									//则将2A及2A以前的培训班进度改为100%
	 									classDao.updateClassProcessByClassid(el.getId(),process,getSessionIntValue(ElConstants.SESSION_USERID));
	 									//培训班的所有课程进度改为100%
	 									courseDao.updateCourseProcessByClassid(el.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
	 									flag = true;
	 								}
	 							}
	 						}
	 						if(flag){
	 							peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
	 						}
	 						classificationDao.addExceptionData_new(getSessionIntValue(ElConstants.SESSION_USERID), classificationDao.getRoomid(), 1);
	 						}
	 						intelligentLogin = IntelligentLoginUtil
	 						.intelligentLogin(elUser.getId());
	 						return "wjm_user_center_login_success";
	 					}
	 				}else {
	 					setElmessage("账号没开通，请与管理员联系！");
	 				}
				}
				
			}
			//如果都不存在
				setElmessage("请重按手指！");
				return "wjm_user_center_login";
 		}else{
 		// 判断是否填写用户名
 			if (elUser.getUsername() == null
 					|| "".equals(elUser.getUsername().trim())) {
 				setElmessage("请填写用户名！");
 				return "wjm_user_center_login";
 			}
 			// 将用户名转成小写（库中存储着小写字符）
 			elUser.setUsername(elUser.getUsername().trim().toLowerCase());
 			String username = "";
 			if (elUser.getUsername().length() == 15) {
 				// 用户名15位转换成18位
 				username = CheckCard.fixPersonIDCode(elUser.getUsername())
 						.toLowerCase();
 			} else if (elUser.getUsername().trim().length() == 18) {
 				// 用户名18位的话转成15位
 				username = CheckCard.fixPersonIDCode15(elUser.getUsername())
 						.toLowerCase();
 				;
 			} else {
 				username = elUser.getUsername();
 			}
 			// 检测15位和18位和密码是否匹配
 			if (userDao
 					.check(elUser.getUsername(), MD5.crypt(elUser.getPassword()))
 					|| userDao.check(username, MD5.crypt(elUser.getPassword()))) {
 				// 校验通过
 				getSession().removeAttribute("yzCodey");
 				// 获取用户信息
 				elUser = userDao.query(elUser.getUsername().trim());
 				if (elUser.getId() == 0)// 登录账号不符合的时候查询转换后的账号
 					elUser = userDao.query(username);
 				// 检测是否已经在线
 				if (OnlineUtil.checkUser(elUser.getId() + "")) {
 					// 查出上次该用户的最后登录信息
 					if (myLogin == null) {
 						myLogin = new MyLogin();
 					}
 					String tempIpAddr = myLogin.getIpAddr();
 					myLogin = userDao.getSessionUserLoginInfo(elUser.getId());
 					getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
 					return "login_logout";
 				}
 				if (elUser.getValid()) {
 					// 如果是超级管理员
 					if (elUser.getRole().getId() == 1) {
 						getSession().setAttribute(ElConstants.SESSION_USERID,
 								elUser.getId());
 						getSession().setAttribute(ElConstants.SESSION_USERNAME,
 								elUser.getUsername().trim());
 						getSession().setAttribute(ElConstants.SESSION_REALNAME,
 								elUser.getRealname());
 						getSession().setAttribute(ElConstants.SESSION_ROLE,
 								elUser.getRole().getId());
 						getSession().setAttribute(ElConstants.SESSION_ROLENAME,
 								elUser.getRole().getName());
 						// 如果是超管，部门id为根
 						if (elUser.getRole().getId() == 1) {
 							getSession().setAttribute(
 									ElConstants.SESSION_MYDEPARTMENT, 1);
 						} else {
 							getSession().setAttribute(
 									ElConstants.SESSION_MYDEPARTMENT,
 									elUser.getDepartment().getId());
 						}
 						getSession().setAttribute(ElConstants.SESSION_AGE,
 								elUser.getAge());
 						// 登录后 加显示 姓名 身份证 部门
 						elUser = userDao.getUserById(elUser.getId());

 						getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
 								elUser.getShenfenzheng());

 						getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
 								elUser.getDepartment().getName());
 						ScoreOperate.setScore(
 								getSessionIntValue(ElConstants.SESSION_USERID),
 								ElConstants.DIAN_LOGIN_DO);
 						// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
 						getSession().setAttribute("isLogin", "true");
 						// 记录用户登入信息-------
 						if (myLogin == null) {
 							myLogin = new MyLogin();
 						}
 						// 判断是否需要记录ip
 						if (SystemConfOp
 								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
 							myLogin.setIpAddr(null);
 						}
 						myLogin.setElUser(elUser);
 						userDao.addUserLoginInfo(myLogin);
 						
 						// // 一下代码用于培训班分配给部门
 						// List<Integer> depparentidList = new ArrayList<Integer>();
 						// depparentidList = shoppingDao.getdempParentid(elUser
 						// .getDepartment().getId());// 得到该用户所有上级部门id
 						//
 						// List<Integer> depclassidList = new ArrayList<Integer>();
 						// for (Integer pid : depparentidList) {
 						// depclassidList = shoppingDao
 						// .getdepartmenttoclassbydepid(pid);//
 						// 循环所有父部门ID并找出该部门被分配的培训班集合
 						// for (Integer elclassid : depclassidList) {//
 						// 循环该集合进行培训班的绑定和分配
 						// classDao.assign2userAdd3(elUser.getId(), elclassid,
 						// ClassConstants.CLASS_SQFS_FP);
 						//
 						// // 分配考场
 						// examroom_classassignwcInit(elclassid, elUser
 						// .getId());
 						// }
 						// }

 						// if (isFromAdmin != null && isFromAdmin.equals("1")) {
 						// return "user_admin_login_success";
 						// }
 						//wjm0211修改
 						//用户定级批次分配
 						if(!classificationDao.isDingji(getSessionIntValue(ElConstants.SESSION_USERID))){
 						//获取培训批次
 						peixunBatch = peixunBatchDao.getPeixunBatchById(1);
 						
 						if(peixunBatch!=null && peixunBatch.getId()>0){
 							//加入到培训批次和培训批次中所有的培训班
 							peixunBatchDao.addBatchEluser(Integer.valueOf(peixunBatch.getId()),getSessionIntValue(ElConstants.SESSION_USERID));
 							//培训批次中每个培训班中的每门课程分配给用户
 							peixunBatchDao.addBatchClass_course(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),ClassConstants.CLASS_SQFS_FP);
 						}
// 						//判断分数段在哪个阶段，若分数为36，分数处于2B，则将2A及2A以前的培训班进度改为100%,培训班的所有课程进度改为100%
// 						float myscore = myExamPaper.getMyScore();
// 						//根据保存定级信息表查出所有培训班，然后比较定级
 						//classification.name == '2A'
 						List<ElClass> elclasses = peixunBatchDao.getElclassList(peixunBatch.getId());
 						float process = 100.00f;
 						boolean flag = false;//标识是否更新了培训班进度
 						for(ElClass el:elclasses){
 							//0211修改wjm定级考试
// 							if(el.getName().compareTo(classification.getName())<0){
 							//0402修改暂时定为6A
 							if(el.getName().compareTo("4A")<0){
// 							if(el.getName().compareTo("6A")<0){
 								//更新培训班进度为100
 								if(classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID), el.getId())){
 									//则将2A及2A以前的培训班进度改为100%
 									classDao.updateClassProcessByClassid(el.getId(),process,getSessionIntValue(ElConstants.SESSION_USERID));
 									//培训班的所有课程进度改为100%
 									courseDao.updateCourseProcessByClassid(el.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
 									flag = true;
 								}
 							}
 						}
 						if(flag){
 							peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
 						}
 						classificationDao.addExceptionData_new(getSessionIntValue(ElConstants.SESSION_USERID), classificationDao.getRoomid(), 1);
 						}
 						intelligentLogin = IntelligentLoginUtil
 						.intelligentLogin(elUser.getId());
 						return "wjm_user_center_login_success";
 					} else {
 						getSession().setAttribute(ElConstants.SESSION_USERID,
 								elUser.getId());
 						getSession().setAttribute(ElConstants.SESSION_USERNAME,
 								elUser.getUsername().trim());
 						getSession().setAttribute(ElConstants.SESSION_REALNAME,
 								elUser.getRealname());
 						getSession().setAttribute(ElConstants.SESSION_ROLE,
 								elUser.getRole().getId());
 						getSession().setAttribute(ElConstants.SESSION_ROLENAME,
 								elUser.getRole().getName());
 						// 如果是超管，部门id为根
 						if (elUser.getRole().getId() == 1) {
 							getSession().setAttribute(
 									ElConstants.SESSION_MYDEPARTMENT, 1);
 						} else {
 							getSession().setAttribute(
 									ElConstants.SESSION_MYDEPARTMENT,
 									elUser.getDepartment().getId());
 						}
 						getSession().setAttribute(ElConstants.SESSION_AGE,
 								elUser.getAge());
 						// 登录后 加显示 姓名 身份证 部门
 						elUser = userDao.getUserById(elUser.getId());

 						getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
 								elUser.getShenfenzheng());

 						getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
 								elUser.getDepartment().getName());
 						ScoreOperate.setScore(
 								getSessionIntValue(ElConstants.SESSION_USERID),
 								ElConstants.DIAN_LOGIN_DO);
 						// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
 						getSession().setAttribute("isLogin", "true");
 						// 记录用户登入信息-------
 						if (myLogin == null) {
 							myLogin = new MyLogin();
 						}
 						// 判断是否需要记录ip
 						if (SystemConfOp
 								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
 							myLogin.setIpAddr(null);
 						}
 						myLogin.setElUser(elUser);
 						userDao.addUserLoginInfo(myLogin);
 						intelligentLogin = IntelligentLoginUtil
 								.intelligentLogin(elUser.getId());
 						// // 一下代码用于培训班分配给部门
 						// List<Integer> depparentidList = new ArrayList<Integer>();
 						// depparentidList = shoppingDao.getdempParentid(elUser
 						// .getDepartment().getId());// 得到该用户所有上级部门id
 						//
 						// List<Integer> depclassidList = new ArrayList<Integer>();
 						// for (Integer pid : depparentidList) {
 						// depclassidList = shoppingDao
 						// .getdepartmenttoclassbydepid(pid);//
 						// 循环所有父部门ID并找出该部门被分配的培训班集合
 						// for (Integer elclassid : depclassidList) {//
 						// 循环该集合进行培训班的绑定和分配
 						// classDao.assign2userAdd3(elUser.getId(), elclassid,
 						// ClassConstants.CLASS_SQFS_FP);
 						//
 						// // 分配考场
 						// examroom_classassignwcInit(elclassid, elUser
 						// .getId());
 						// }
 						// }
 						//
 						// if (isFromAdmin != null && isFromAdmin.equals("1")) {
 						// return "user_admin_login_success";
 						// }
 						//wjm0211修改
 						//用户定级批次分配 2017年9月22日 15:11dk修改
 						if(!classificationDao.isDingji(getSessionIntValue(ElConstants.SESSION_USERID))){
 							System.out.println("所有新用户定级为1A");
 							peixunBatch = peixunBatchDao.getPeixunBatchById(1);
 							
 							if(peixunBatch!=null && peixunBatch.getId()>0){
 								//加入到培训批次和培训批次中所有的培训班
 								peixunBatchDao.addBatchEluser(Integer.valueOf(peixunBatch.getId()),getSessionIntValue(ElConstants.SESSION_USERID));
 								//培训批次中每个培训班中的每门课程分配给用户
 								peixunBatchDao.addBatchClass_course(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),ClassConstants.CLASS_SQFS_FP);
 							}
// 							//判断分数段在哪个阶段，若分数为36，分数处于2B，则将2A及2A以前的培训班进度改为100%,培训班的所有课程进度改为100%
// 							float myscore = myExamPaper.getMyScore();
// 							//根据保存定级信息表查出所有培训班，然后比较定级
 							//classification.name == '2A'
 							List<ElClass> elclasses = peixunBatchDao.getElclassList(peixunBatch.getId());
 							float process = 100.00f;
 							boolean flag = false;//标识是否更新了培训班进度
 							for(ElClass el:elclasses){
 								//0211修改wjm定级考试
// 								if(el.getName().compareTo(classification.getName())<0){
 								if(el.getName().compareTo("1A")<0){
 								//暂时定为6A
// 								if(el.getName().compareTo("6A")<0){
 									//更新培训班进度为100 过滤B的培训班
 									//if(!el.getName().endsWith("B")){
 										if(classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID), el.getId())){
 											//则将2A及2A以前的培训班进度改为100%
 											classDao.updateClassProcessByClassid(el.getId(),process,getSessionIntValue(ElConstants.SESSION_USERID));
 											//培训班的所有课程进度改为100%
 											courseDao.updateCourseProcessByClassid(el.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
 											flag = true;
 										}
 									//}
 								}
 							}
 							if(flag){
 								peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
 							}
 							classificationDao.addExceptionData_new(getSessionIntValue(ElConstants.SESSION_USERID), classificationDao.getRoomid(), 1);
 							/*
 						//获取培训批次
 						peixunBatch = peixunBatchDao.getPeixunBatchById(1);
 						
 						if(peixunBatch!=null && peixunBatch.getId()>0){
 							//加入到培训批次和培训批次中所有的培训班
 							peixunBatchDao.addBatchEluser(Integer.valueOf(peixunBatch.getId()),getSessionIntValue(ElConstants.SESSION_USERID));
 							//培训批次中每个培训班中的每门课程分配给用户
 							peixunBatchDao.addBatchClass_course(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),ClassConstants.CLASS_SQFS_FP);
 						}
// 						//判断分数段在哪个阶段，若分数为36，分数处于2B，则将2A及2A以前的培训班进度改为100%,培训班的所有课程进度改为100%
// 						float myscore = myExamPaper.getMyScore();
// 						//根据保存定级信息表查出所有培训班，然后比较定级
 						//classification.name == '2A'
 						List<ElClass> elclasses = peixunBatchDao.getElclassList(peixunBatch.getId());
 						float process = 100.00f;
 						boolean flag = false;//标识是否更新了培训班进度
 						for(ElClass el:elclasses){
 							//0211修改wjm定级考试
// 							if(el.getName().compareTo(classification.getName())<0){
 							if(el.getName().compareTo("4A")<0){
 							//暂时定为6A
// 							if(el.getName().compareTo("6A")<0){
 								//更新培训班进度为100
 								if(classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID), el.getId())){
 									//则将2A及2A以前的培训班进度改为100%
 									classDao.updateClassProcessByClassid(el.getId(),process,getSessionIntValue(ElConstants.SESSION_USERID));
 									//培训班的所有课程进度改为100%
 									courseDao.updateCourseProcessByClassid(el.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
 									flag = true;
 								}
 							}
 						}
 						if(flag){
 							peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
 						}
 						classificationDao.addExceptionData_new(getSessionIntValue(ElConstants.SESSION_USERID), classificationDao.getRoomid(), 1);
 						*/}
 						intelligentLogin = IntelligentLoginUtil
 						.intelligentLogin(elUser.getId());
 						return "wjm_user_center_login_success";
 					}
 				}else {
 					setElmessage("账号没开通，请与管理员联系！");
 				}
 			} else {
 				// 检测用户名是否存在
 				if (!userDao.checkUsername(elUser.getUsername().trim())
 						&& !userDao.checkUsername(username.trim())) {
 					setElmessage("用户名不存在");// 数据库中是18位，用15位登录，提示用户名不存在
 				} else {
 					setElmessage("用户名或密码有错");
 				}
 			}
		}
		
		
		
		return "wjm_user_center_login";
	}

	
	
	
	
	
	
	
	
	
	
	

	// 北京卫生局个人中心
	public String cisco_user_center() throws ElException {
		module = module == null ? "cisco_user_center_index.action" : module;
		Return = "studentman";
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "cisco_user_center_phone";
		}
		return "cisco_user_center";
	}

	// 北京卫生局个人中心frame
	public String cisco_user_center_index() throws ElException {
		// menus = AuthorityNewVersionUtil.getListElFuncByRoleid(String
		// .valueOf(getSessionIntValue(ElConstants.SESSION_ROLE)));
		// menus_three = new ArrayList<ElFunc>();
		// List<ElFunc> child = null;
		// for (int i = 0; i < menus.size(); i++) {
		// menu = menus.get(i);
		// if (menu != null && menu.getChild() != null) {
		// child = menu.getChild();
		// if (child != null) {
		// menus_three.addAll(child);
		// }
		// }
		// }

		menus = AuthorityNewVersionUtil.getListElFuncByRoleid(String
				.valueOf(getSessionIntValue(ElConstants.SESSION_ROLE)));
		menus_three = new ArrayList<ElFunc>();
		menus_three_cycz = new ArrayList<ElFunc>();
		List<ElFunc> child = null;
		for (int i = 1; i < menus.size(); i++) {
			menu = menus.get(i);
			if (menu != null && menu.getChild() != null) {
				child = menu.getChild();
				if (child != null) {
					menus_three.addAll(child);
				}
			}
		}

		List<ElFunc> child_cycz = null;
		menu_cycz = menus.get(0);
		if (menu_cycz != null && menu_cycz.getChild() != null) {
			child_cycz = menu_cycz.getChild();
			if (child_cycz != null) {
				menus_three_cycz.addAll(child_cycz);
			}
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
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());

		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		// 论坛信息
		forums = forumAdminDao.newVersionGetForums(getPageNow(), getPageSize());

		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
		ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		// } else {
		// ntypeTree = newsDao.getNtypeTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op",
		// ElConstants.TREE_FIANL, true);
		//
		// }
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1
				: ntype.getId());
		// 新闻信息
		newses = newsDao.listFabuNewses(6, ntypeTree, nid, getPageNow(),
				getPageSize());
		// 知识
		kledges = knowledgeManageDao.listKledgeAll(department,
				KnowledgeManageConstants.STATUS_ALL, getPageNow(),
				getPageSize());
		// 短消息
		newMessage = messageDao.listMessNewAll(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		// 未读
		message_no = messageDao
				.getMessNoCount(getSessionIntValue(ElConstants.SESSION_USERID));
		// 已读
		message_yes = messageDao
				.getMessYesCount(getSessionIntValue(ElConstants.SESSION_USERID));

		// 用户是否选择培训班
		if (getSessionValue(ElConstants.SESSION_USERNAME) != null) {
			isChangeElclass = studyClassDao
					.getIsChangeclass(getSessionIntValue(ElConstants.SESSION_USERID));
		}

		// 系统培训班(拿证培训班)
		MyClass cla_ = studyClassDao
				.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));

		// 最新一期培训班，比较createtime
		// 首先获取系统年份，再查询系统中年份为当前年份的培训班
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		isBuyNianjianClass = studyClassDao
				.isNianjianClass(getSessionIntValue(ElConstants.SESSION_USERID));
		MyClass new_cla_ = studyClassDao.getNaZhengClass(year);
		MyClass nianjian_cla_ = studyClassDao.getNianjianClass(year);
		if (isBuyNianjianClass == 0) {
			int classid = 0;
			// 以下为对最新一期培训班的处理
			if (new_cla_ != null) {
				classid = new_cla_.getElClass().getId();
				myClassAll = new MyClass();
				String sqlBX = "";
				String roomid = "";
				if (courseBX != null && courseBX.getCourseForm() != -1) {
					sqlBX = " and c.courseForm = " + courseBX.getCourseForm();
				}

				if (classid != 0) {
					// int roomid = eroomDao.getRoomidByClassid_cisco(classid);
					// if(classid!=0 && roomid!=0){
					// room = eroomDao.getExamRoomByid(roomid);
					// new_cla_.getElClass().setExamRooms(eroomDao.listExamRoomByClass_cisco(classid,getSessionIntValue(ElConstants.SESSION_USERID)));
					// for(int
					// i=0;i<new_cla_.getElClass().getExamRooms().size();i++){
					// new_cla_.getElClass().getExamRooms().get(i).setIsPass(eroomDao.getIsPass(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getExamRooms().get(i).getId()));
					// }
					// }
					//				
					// else{
					// room = new ExamRoom();
					// room.setId(0);
					// }
					// 检查拿证培训班或者年检培训班，用户是否已经购买
					boolean cla_check = false;
					if (cla_ != null) {
						cla_check = studyClassDao.checkClassIsUser(cla_
								.getElClass().getId(),
								getSessionIntValue(ElConstants.SESSION_USERID));
					}
					boolean new_check = false;
					if (new_cla_ != null) {
						new_check = studyClassDao.checkClassIsUser(new_cla_
								.getElClass().getId(),
								getSessionIntValue(ElConstants.SESSION_USERID));
					}
					if (cla_check || new_check) {
						needAllocation = true;
						// 培训班中的课程
						// 查询培训班课程分配表
						int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID)
								: elUser.getId();
						studyCourseList = studyClassDao.getCourses(classid,
								getSessionIntValue(ElConstants.SESSION_USERID));
						roomid = eroomDao.getBindingCourseByRoomId(classid, 0,
								CourseConstants.COURSE_TABLENAME_CLASS_COURSE);
						myClassAll
								.setMyCourseB(studyClassDao
										.listMyClassCourseStat3(
												classid,
												userid,
												roomid,
												CourseConstants.COURSE_TABLENAME_CLASS_COURSE,
												CourseConstants.COURSE_STUDY_STATUS_BX,
												sqlBX));
						myClassAll
								.setMyCourseX(studyClassDao
										.listMyClassCourseStat3(
												classid,
												userid,
												roomid,
												CourseConstants.COURSE_TABLENAME_CLASS_COURSE,
												CourseConstants.COURSE_STUDY_STATUS_XX,
												sqlBX));
					}
					// 培训班是否通过
					int status = studyClassDao.getStudyClassStatus(
							getSessionIntValue(ElConstants.SESSION_USERID),
							classid);
					if (status == 2) {
						new_cla_.setPassed(true);
					} else {
						new_cla_.setPassed(false);
					}

				}

				studyCourseList = studyCourseList == null ? new ArrayList<MyCourse>()
						: studyCourseList;
				// 学时及比例
				map = NewVersionUtil.getCourseProcess(studyCourseList);
				sumBX = studyClassDao.countScoreBX(classid);
				hasSumBX = studyClassDao
						.getScoreBX(
								getSessionIntValue(ElConstants.SESSION_USERID),
								classid);
				hasSumXX = studyClassDao
						.getcountXFforXX(
								getSessionIntValue(ElConstants.SESSION_USERID),
								classid);
				if (isChangeElclass != 0) {
					sumScore = sumBX
							+ new_cla_.getElClass().getOptionalcredit();
					hasSumScore = hasSumBX + hasSumXX;
					DecimalFormat df = new DecimalFormat("0.0");
					if (hasSumScore + "" != null && sumScore + "" != null
							&& sumScore != 0) {
						scoreProcess = Double
								.parseDouble(df
										.format(((hasSumBX + hasSumXX) * 100 / sumScore)));
					}
				}

				// MyClass myClass =
				// studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
				// boolean flag = false;
				boolean flag1 = false;
				if (new_cla_ != null) {
					flag1 = classDao.checkElclassIsUsers(
							getSessionIntValue(ElConstants.SESSION_USERID),
							new_cla_.getElClass().getId());
				}
				boolean flag2 = false;
				if (cla_ != null) {
					flag2 = classDao.checkElclassIsUsers(
							getSessionIntValue(ElConstants.SESSION_USERID),
							cla_.getElClass().getId());
				}
				// int temp = 0;
				if (flag1 || flag2) {// 购买拿证培训班或者最新一期培训班
					if (flag1) {// 购买最新一期培训班
						// temp = 1;
						if (!flag1) {// 未购买培训班
							step = 1;
						} else {
							step = 2;// 购买培训班
							if (new_cla_.getElClass().getClasstype() == 2) {
								// 检测是否通过自主培训班
								studyClassDao
										.setMyPassclass_at(
												getSessionIntValue(ElConstants.SESSION_USERID),
												new_cla_.getElClass().getId());
							} else {
								// 检测是否通过培训班
								studyClassDao
										.setMyPassclass(
												getSessionIntValue(ElConstants.SESSION_USERID),
												new_cla_.getElClass().getId());
							}
							int status = studyClassDao
									.getStudyClassStatus(
											getSessionIntValue(ElConstants.SESSION_USERID),
											new_cla_.getElClass().getId());
							if (status == 2) {// 已达到考试条件（即培训班获得了证书）
								step = 3;
							}
							if (isBuyNianjianClass == 0) {
								if (new_cla_.getElClass().getExamRooms() != null
										&& new_cla_.getElClass().getExamRooms()
												.size() > 0) {
									if (new_cla_.getElClass().getExamRooms()
											.get(0).getIsPassed() == 1) {// 考试通过，可以查看证书
										step = 4;
									}
								}
							} else {
								if (nianjian_cla_.getElClass().getExamRooms() != null
										&& nianjian_cla_.getElClass()
												.getExamRooms().size() > 0) {
									if (nianjian_cla_.getElClass()
											.getExamRooms().get(0)
											.getIsPassed() == 1) {// 考试通过，可以查看证书
										step = 4;
									}
								}
							}
						}

					} else {
						if (!flag2) {// 未购买培训班
							step = 1;
						} else {
							step = 2;// 购买培训班
							if (cla_.getElClass().getClasstype() == 2) {
								// 检测是否通过自主培训班
								studyClassDao
										.setMyPassclass_at(
												getSessionIntValue(ElConstants.SESSION_USERID),
												cla_.getElClass().getId());
							} else {
								// 检测是否通过培训班
								studyClassDao
										.setMyPassclass(
												getSessionIntValue(ElConstants.SESSION_USERID),
												cla_.getElClass().getId());
							}
							int status = studyClassDao
									.getStudyClassStatus(
											getSessionIntValue(ElConstants.SESSION_USERID),
											cla_.getElClass().getId());
							if (status == 2) {// 已达到考试条件（即培训班获得了证书）
								step = 3;
							}
							if (cla_.getElClass().getExamRooms() != null
									&& cla_.getElClass().getExamRooms().size() > 0) {
								if (cla_.getElClass().getExamRooms().get(0)
										.getIsPassed() == 1) {// 考试通过，可以查看证书
									step = 4;
								}
							}
						}
					}
				}
			}
		}// 分隔点
		else {
			int classid = 0;
			// 以下为对最新一期培训班的处理
			if (isBuyNianjianClass == 0) {
				if (new_cla_ != null) {
					classid = new_cla_.getElClass().getId();
				}
			} else {
				if (nianjian_cla_ != null) {
					classid = nianjian_cla_.getElClass().getId();

				}
			}

			if (classid != 0) {
				myClassAll = new MyClass();
				String sqlBX = "";
				String roomid = "";
				if (courseBX != null && courseBX.getCourseForm() != -1) {
					sqlBX = " and c.courseForm = " + courseBX.getCourseForm();
				}
				// int roomid = eroomDao.getRoomidByClassid_cisco(classid);
				// if(classid!=0 && roomid!=0){
				// room = eroomDao.getExamRoomByid(roomid);
				// new_cla_.getElClass().setExamRooms(eroomDao.listExamRoomByClass_cisco(classid,getSessionIntValue(ElConstants.SESSION_USERID)));
				// for(int
				// i=0;i<new_cla_.getElClass().getExamRooms().size();i++){
				// new_cla_.getElClass().getExamRooms().get(i).setIsPass(eroomDao.getIsPass(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getExamRooms().get(i).getId()));
				// }
				// }
				//					
				// else{
				// room = new ExamRoom();
				// room.setId(0);
				// }
				// 检查拿证培训班或者年检培训班，用户是否已经购买
				boolean cla_check = false;
				if (nianjian_cla_ != null) {
					cla_check = studyClassDao.checkClassIsUser(nianjian_cla_
							.getElClass().getId(),
							getSessionIntValue(ElConstants.SESSION_USERID));
				}
				boolean new_check = false;
				if (nianjian_cla_ != null) {
					new_check = studyClassDao.checkClassIsUser(nianjian_cla_
							.getElClass().getId(),
							getSessionIntValue(ElConstants.SESSION_USERID));
				}
				if (cla_check || new_check) {
					needAllocation = true;
					// 培训班中的课程
					// 查询培训班课程分配表
					int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID)
							: elUser.getId();
					studyCourseList = studyClassDao.getCourses(classid,
							getSessionIntValue(ElConstants.SESSION_USERID));
					roomid = eroomDao.getBindingCourseByRoomId(classid, 0,
							CourseConstants.COURSE_TABLENAME_CLASS_COURSE);
					myClassAll
							.setMyCourseB(studyClassDao
									.listMyClassCourseStat3(
											classid,
											userid,
											roomid,
											CourseConstants.COURSE_TABLENAME_CLASS_COURSE,
											CourseConstants.COURSE_STUDY_STATUS_BX,
											sqlBX));
					myClassAll
							.setMyCourseX(studyClassDao
									.listMyClassCourseStat3(
											classid,
											userid,
											roomid,
											CourseConstants.COURSE_TABLENAME_CLASS_COURSE,
											CourseConstants.COURSE_STUDY_STATUS_XX,
											sqlBX));
				}
				// 培训班是否通过
				int status = studyClassDao
						.getStudyClassStatus(
								getSessionIntValue(ElConstants.SESSION_USERID),
								classid);
				if (status == 2) {
					nianjian_cla_.setPassed(true);
				} else {
					nianjian_cla_.setPassed(false);
				}

			}

			studyCourseList = studyCourseList == null ? new ArrayList<MyCourse>()
					: studyCourseList;
			// 学时及比例
			map = NewVersionUtil.getCourseProcess(studyCourseList);
			if (nianjian_cla_ != null) {
				sumBX = studyClassDao.countScoreBX(classid);
				hasSumBX = studyClassDao
						.getScoreBX(
								getSessionIntValue(ElConstants.SESSION_USERID),
								classid);
				hasSumXX = studyClassDao
						.getcountXFforXX(
								getSessionIntValue(ElConstants.SESSION_USERID),
								classid);
				sumScore = sumBX
						+ nianjian_cla_.getElClass().getOptionalcredit();
				hasSumScore = hasSumBX + hasSumXX;
				DecimalFormat df = new DecimalFormat("0.0");
				if (hasSumScore + "" != null && sumScore + "" != null) {
					scoreProcess = Double.parseDouble(df.format(hasSumScore
							/ sumScore * 100));
				}
			}

			// MyClass myClass =
			// studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
			// boolean flag = false;
			boolean flag1 = false;
			if (nianjian_cla_ != null) {
				flag1 = classDao.checkElclassIsUsers(
						getSessionIntValue(ElConstants.SESSION_USERID),
						nianjian_cla_.getElClass().getId());
			}
			boolean flag2 = false;
			if (nianjian_cla_ != null) {
				flag2 = classDao.checkElclassIsUsers(
						getSessionIntValue(ElConstants.SESSION_USERID),
						nianjian_cla_.getElClass().getId());
			}
			// int temp = 0;
			if (flag1 || flag2) {// 购买拿证培训班或者最新一期培训班
				if (flag1) {// 购买最新一期培训班
					// temp = 1;
					if (!flag1) {// 未购买培训班
						step = 1;
					} else {
						step = 2;// 购买培训班
						if (nianjian_cla_.getElClass().getClasstype() == 2) {
							// 检测是否通过自主培训班
							studyClassDao
									.setMyPassclass_at(
											getSessionIntValue(ElConstants.SESSION_USERID),
											nianjian_cla_.getElClass().getId());
						} else {
							// 检测是否通过培训班
							studyClassDao
									.setMyPassclass(
											getSessionIntValue(ElConstants.SESSION_USERID),
											nianjian_cla_.getElClass().getId());
						}
						int status = 0;
						if (isBuyNianjianClass == 0) {
							status = studyClassDao
									.getStudyClassStatus(
											getSessionIntValue(ElConstants.SESSION_USERID),
											new_cla_.getElClass().getId());
						} else {
							status = studyClassDao
									.getStudyClassStatus(
											getSessionIntValue(ElConstants.SESSION_USERID),
											nianjian_cla_.getElClass().getId());
						}
						if (status == 2) {// 已达到考试条件（即培训班获得了证书）
							step = 3;
						}
						if (isBuyNianjianClass == 0) {
							if (new_cla_.getElClass().getExamRooms() != null
									&& new_cla_.getElClass().getExamRooms()
											.size() > 0) {
								if (new_cla_.getElClass().getExamRooms().get(0)
										.getIsPassed() == 1) {// 考试通过，可以查看证书
									step = 4;
								}
							}
						} else {
							if (nianjian_cla_.getElClass().getExamRooms() != null
									&& nianjian_cla_.getElClass()
											.getExamRooms().size() > 0) {
								if (nianjian_cla_.getElClass().getExamRooms()
										.get(0).getIsPassed() == 1) {// 考试通过，可以查看证书
									step = 4;
								}
							}
						}
					}

				} else {
					if (!flag2) {// 未购买培训班
						step = 1;
					} else {
						step = 2;// 购买培训班
						if (nianjian_cla_.getElClass().getClasstype() == 2) {
							// 检测是否通过自主培训班
							studyClassDao
									.setMyPassclass_at(
											getSessionIntValue(ElConstants.SESSION_USERID),
											nianjian_cla_.getElClass().getId());
						} else {
							// 检测是否通过培训班
							studyClassDao
									.setMyPassclass(
											getSessionIntValue(ElConstants.SESSION_USERID),
											nianjian_cla_.getElClass().getId());
						}
						int status = studyClassDao.getStudyClassStatus(
								getSessionIntValue(ElConstants.SESSION_USERID),
								nianjian_cla_.getElClass().getId());
						if (status == 2) {// 已达到考试条件（即培训班获得了证书）
							step = 3;
						}
						if (nianjian_cla_.getElClass().getExamRooms() != null
								&& cla_.getElClass().getExamRooms().size() > 0) {
							if (cla_.getElClass().getExamRooms().get(0)
									.getIsPassed() == 1) {// 考试通过，可以查看证书
								step = 4;
							}
						}
					}
				}
			}
		}
		nianjian_cla = nianjian_cla_;
		new_cla = new_cla_;
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "cisco_user_center_index_phone";
		}

		return "cisco_user_center_index";
	}

	/**
	 * 新版个人中心登录、新版管理中心登录
	 * 
	 * @return
	 * @throws ElException
	 */
	public String user_center_login() throws ElException {
		if (isFromRegister != 1) {// 不是注册成功后的登录
			if (yzCodeIsNo != 1) {// 不是从前台首页调用的action
				yzCodeIsNo = SystemConfOp
						.getIntValue(ElConstants.SYSTEM_CONF_YZCODE_OPEN);
				if (yzCodeIsNo == 1) {
					if (yzCode == null || yzCode.equals("")) {
						this.setElmessage("验证码不能为空,请填写验证码!!!");
						return "login";
					} else {
						if ((getSession().getAttribute("yzCodey") != null && !((String) getSession()
								.getAttribute("yzCodey")).equals(yzCode))) {
							this.setElmessage("验证码错误,请填写验证码!!!");
							return "login";
						}
					}
				}
			}
		}
		if (elUser == null) {
			setElmessage("请从正常入口进入！");
			return "error";
		}

		// 判断是否填写用户名
		if (elUser.getUsername() == null
				|| "".equals(elUser.getUsername().trim())) {
			setElmessage("请填写用户名！");
			return "user_center_login_failure";
		}
		// 将用户名转成小写（库中存储着小写字符）
		elUser.setUsername(elUser.getUsername().trim().toLowerCase());
		String username = "";
		if (elUser.getUsername().length() == 15) {
			// 用户名15位转换成18位
			username = CheckCard.fixPersonIDCode(elUser.getUsername())
					.toLowerCase();
		} else if (elUser.getUsername().trim().length() == 18) {
			// 用户名18位的话转成15位
			username = CheckCard.fixPersonIDCode15(elUser.getUsername())
					.toLowerCase();
			;
		} else {
			username = elUser.getUsername();
		}
		// 检测15位和18位和密码是否匹配
		if (userDao
				.check(elUser.getUsername(), MD5.crypt(elUser.getPassword()))
				|| userDao.check(username, MD5.crypt(elUser.getPassword()))) {
			// 校验通过
			getSession().removeAttribute("yzCodey");
			// 获取用户信息
			elUser = userDao.query(elUser.getUsername().trim());
			if (elUser.getId() == 0)// 登录账号不符合的时候查询转换后的账号
				elUser = userDao.query(username);
			// 检测是否已经在线
			if (OnlineUtil.checkUser(elUser.getId() + "")) {
				// 查出上次该用户的最后登录信息
				if (myLogin == null) {
					myLogin = new MyLogin();
				}
				String tempIpAddr = myLogin.getIpAddr();
				myLogin = userDao.getSessionUserLoginInfo(elUser.getId());
				getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
				return "login_logout";
			}
			if (elUser.getValid()) {
				// 如果是超级管理员
				if (elUser.getRole().getId() == 1) {
					getSession().setAttribute(ElConstants.SESSION_USERID,
							elUser.getId());
					getSession().setAttribute(ElConstants.SESSION_USERNAME,
							elUser.getUsername().trim());
					getSession().setAttribute(ElConstants.SESSION_REALNAME,
							elUser.getRealname());
					getSession().setAttribute(ElConstants.SESSION_ROLE,
							elUser.getRole().getId());
					getSession().setAttribute(ElConstants.SESSION_ROLENAME,
							elUser.getRole().getName());
					// 如果是超管，部门id为根
					if (elUser.getRole().getId() == 1) {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT, 1);
					} else {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,
							elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser = userDao.getUserById(elUser.getId());

					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
							elUser.getShenfenzheng());

					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
							elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if (myLogin == null) {
						myLogin = new MyLogin();
					}
					// 判断是否需要记录ip
					if (SystemConfOp
							.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);

					// 一下代码用于培训班分配给部门
					List<Integer> depparentidList = new ArrayList<Integer>();
					depparentidList = shoppingDao.getdempParentid(elUser
							.getDepartment().getId());// 得到该用户所有上级部门id

					List<Integer> depclassidList = new ArrayList<Integer>();
					for (Integer pid : depparentidList) {
						depclassidList = shoppingDao
								.getdepartmenttoclassbydepid(pid);// 循环所有父部门ID并找出该部门被分配的培训班集合
						for (Integer elclassid : depclassidList) {// 循环该集合进行培训班的绑定和分配
							classDao.assign2userAdd3(elUser.getId(), elclassid,
									ClassConstants.CLASS_SQFS_FP);

							// 分配考场
							examroom_classassignwcInit(elclassid, elUser
									.getId());
						}
					}

					if (isFromAdmin != null && isFromAdmin.equals("1")) {
						return "user_admin_login_success";
					}

					return "user_center_login_success";
				} else {
					getSession().setAttribute(ElConstants.SESSION_USERID,
							elUser.getId());
					getSession().setAttribute(ElConstants.SESSION_USERNAME,
							elUser.getUsername().trim());
					getSession().setAttribute(ElConstants.SESSION_REALNAME,
							elUser.getRealname());
					getSession().setAttribute(ElConstants.SESSION_ROLE,
							elUser.getRole().getId());
					getSession().setAttribute(ElConstants.SESSION_ROLENAME,
							elUser.getRole().getName());
					// 如果是超管，部门id为根
					if (elUser.getRole().getId() == 1) {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT, 1);
					} else {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,
							elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser = userDao.getUserById(elUser.getId());

					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
							elUser.getShenfenzheng());

					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
							elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if (myLogin == null) {
						myLogin = new MyLogin();
					}
					// 判断是否需要记录ip
					if (SystemConfOp
							.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);

					// 一下代码用于培训班分配给部门
					List<Integer> depparentidList = new ArrayList<Integer>();
					depparentidList = shoppingDao.getdempParentid(elUser
							.getDepartment().getId());// 得到该用户所有上级部门id

					List<Integer> depclassidList = new ArrayList<Integer>();
					for (Integer pid : depparentidList) {
						depclassidList = shoppingDao
								.getdepartmenttoclassbydepid(pid);// 循环所有父部门ID并找出该部门被分配的培训班集合
						for (Integer elclassid : depclassidList) {// 循环该集合进行培训班的绑定和分配
							classDao.assign2userAdd3(elUser.getId(), elclassid,
									ClassConstants.CLASS_SQFS_FP);

							// 分配考场
							examroom_classassignwcInit(elclassid, elUser
									.getId());
						}
					}

					if (isFromAdmin != null && isFromAdmin.equals("1")) {
						return "user_admin_login_success";
					}

					return "user_center_login_success";
				}
			} else {
				setElmessage("账号没开通，请与管理员联系！");
			}
		} else {
			// 检测用户名是否存在
			if (!userDao.checkUsername(elUser.getUsername().trim())
					&& !userDao.checkUsername(username.trim())) {
				setElmessage("用户名不存在");// 数据库中是18位，用15位登录，提示用户名不存在
			} else {
				setElmessage("用户名或密码有错");
			}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "user_center_login_phone";
		}
		return "user_center_login";
	}

	/**
	 * 管理中心
	 * 
	 * @return
	 * @throws ElException
	 */
	public String user_admin() throws ElException {
		// 检查除了个人中心菜单是否有其他菜单
		if (funcDao.getCountRemoveUserCenter(
				getSessionIntValue(ElConstants.SESSION_USERID),
				getSessionIntValue(ElConstants.SESSION_ROLE)) > 0) {
			menu = roleDao.getMenu("stuffman",
					getSessionIntValue(ElConstants.SESSION_ROLE),
					getSessionIntValue(ElConstants.SESSION_USERID));
			menus = roleDao.getMenus_newversion(0,
					getSessionIntValue(ElConstants.SESSION_ROLE),
					getSessionIntValue(ElConstants.SESSION_USERID));
			module = module == null ? "addModuleManageInit.action" : module;
			Return = "stuffman";
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if (b == true) {
				return "user_admin_phone";
			}
			return "user_admin";
		} else {
			setElmessage("对不起，您不是管理员!");
			return "user_admin_login";
		}
	}

	/**
	 * 个人中心
	 * 
	 * @return
	 * @throws ElException
	 */
	public String user_center() throws ElException {
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		menus = AuthorityNewVersionUtil.getListElFuncByRoleid(String
				.valueOf(getSessionIntValue(ElConstants.SESSION_ROLE)));
		menus_three = new ArrayList<ElFunc>();
		menus_three_cycz = new ArrayList<ElFunc>();
		List<ElFunc> child = null;
		for (int i = 1; i < menus.size(); i++) {
			menu = menus.get(i);
			if (menu != null && menu.getChild() != null) {
				child = menu.getChild();
				if (child != null) {
					menus_three.addAll(child);
				}
			}
		}

		List<ElFunc> child_cycz = null;
		menu_cycz = menus.get(0);
		if (menu_cycz != null && menu_cycz.getChild() != null) {
			child_cycz = menu_cycz.getChild();
			if (child_cycz != null) {
				menus_three_cycz.addAll(child_cycz);
			}
		}

		if (elUser.getRole().getId() == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(elUser.getId(), "op", -1,
					true);
		}
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());

		int newShowye = NewSystemConfOp
				.getIntValue(ElConstants.SYSTEM_NEWINDEXCONFIG_NEWSHOUYE);
		if (newShowye == 0) {// ELN系统
			eroom_no = studyQuizDao
					.getEroomNoCount(getSessionIntValue(ElConstants.SESSION_USERID));
			eroom_all = studyQuizDao
					.getEroomAllCount(getSessionIntValue(ElConstants.SESSION_USERID));
			class_yes = studyClassDao
					.getClassYesCount(getSessionIntValue(ElConstants.SESSION_USERID));
			class_all = studyClassDao
					.getClassAllCount(getSessionIntValue(ElConstants.SESSION_USERID));

			myDaibanshuwu = scheduleGlobleDao
					.getMyDaibanshuwu(
							getSessionIntValue(ElConstants.SESSION_USERID),
							IndexSystemConfigOp
									.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU),
							IndexSystemConfigOp
									.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_DAIBANSHIWU_LENGTH));

			myallcourse = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYALLCOURSES);
			myexams = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYEXAMS);
			mytrainingcourses = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYTRAININGCOURSES);
			// 我的考试
			if (myexams) {
				myrooms = studyQuizDao
						.study_index_listErsWithoutC(
								getSessionIntValue(ElConstants.SESSION_USERID),
								IndexSystemConfigOp
										.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYEXAMS_LENGTH),
								true);
			}
			// 我的培训班
			if (mytrainingcourses) {
				myClasses = studyClassDao
						.study_index_listMyStudyClass(
								getSessionIntValue(ElConstants.SESSION_USERID),
								IndexSystemConfigOp
										.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYTRAININGCOURSES));
			}
			// 我的全部课程
			if (myallcourse) {
				// 我的测评课程
				boolean open_jtm = JTMSystemConfOp
						.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
				if (open_jtm) {
					elUser = userDao
							.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));

					String cer = JTM.getJTM_cer(String.valueOf(elUser.getId()));
					String JTM_URL = JTMSystemConfOp
							.getValue(ElConstants.SYSTEM_JTM_MY_CEPINGCOURSES_URL)
							+ "?userid="
							+ elUser.getId()
							+ "&jobid="
							+ elUser.getStaid() + "&cer=" + cer;

					Content c = null;
					String returnValue = "";
					try {
						c = Request.Get(JTM_URL).addHeader("Content-Type",
								"text/html; charset=UTF-8").execute()
								.returnContent();
						System.out.println(c.asString());
						returnValue = c.asString();

					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					String[] arr = null;
					if (returnValue != null && !returnValue.equals("")) {
						arr = returnValue.trim().split("\\|");
						if (arr[0].equals("true")) {
							String[] courses = arr[1].split(",");
							if (courses != null && courses.length > 0) {
								// 插入前，不能将userid对应的测评课程删除
								// 因为存在每次学习的开始时间，删除的话再插入，每次开始时间都从0开始。
								for (int i = 0; i < courses.length; i++) {
									// 添加测评课程到课程分配表
									studyCourseDao
											.insertCepingCourse(
													getSessionIntValue(ElConstants.SESSION_USERID),
													Integer
															.parseInt(courses[i]));
								}
							}
						}
					}
				}
				studyCourseList = studyCourseDao
						.study_index_listMyAllCourse(
								getSessionIntValue(ElConstants.SESSION_USERID),
								IndexSystemConfigOp
										.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYALLCOURSES_LENGTH));
			}
		} else if (newShowye == 1) {// 信息管理系统
			gongzuojihua = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA);
			gongzuorizhi = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI);
			daibanshiwu = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU);
			gerenweishen = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN);
			// 我的计划
			myPlan = scheduleGlobleDao
					.getMyPlan(
							getSessionIntValue(ElConstants.SESSION_USERID),
							IndexSystemConfigOp
									.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA),
							IndexSystemConfigOp
									.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GONGZUOJIHUA_LENGTH));
			// 我的日志
			myLog = scheduleGlobleDao
					.getMyLog(
							getSessionIntValue(ElConstants.SESSION_USERID),
							IndexSystemConfigOp
									.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI),
							IndexSystemConfigOp
									.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GONGZUORIZHI_LENGTH));
			myDaibanshuwu = scheduleGlobleDao
					.getMyDaibanshuwu(
							getSessionIntValue(ElConstants.SESSION_USERID),
							IndexSystemConfigOp
									.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU),
							IndexSystemConfigOp
									.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_DAIBANSHIWU_LENGTH));
			myNoPass = scheduleGlobleDao
					.getNoPass(
							getSessionIntValue(ElConstants.SESSION_ROLE),
							getSessionIntValue(ElConstants.SESSION_USERID),
							-1,
							-1,
							IndexSystemConfigOp
									.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN),
							department);
		}
		// 消息弹窗
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			message_no = messageDao.getMessNoCount(userid);
			getRequest().setAttribute("message_no", message_no);
		} else {
			// 未读短消息条数，已读短消息条数
			// 未读
			message_no = messageDao.getMessNoCount(userid);
			getRequest().setAttribute("message_no", message_no);
			// 已读
			message_yes = messageDao.getMessYesCount(userid);
			getRequest().setAttribute("message_yes", message_yes);
			// 未开始的考场，全部考场
			// eroom_no = studyQuizDao
			// .getEroomNoCount(userid);
			// getRequest().setAttribute("eroom_no", eroom_no);
			// eroom_all = studyQuizDao
			// .getEroomAllCount(userid);
			// getRequest().setAttribute("eroom_all", eroom_all);
			// // 已结业培训班，全部培训班
			// class_yes = studyClassDao
			// .getClassYesCount(userid);
			// getRequest().setAttribute("class_yes", class_yes);
			// class_all = studyClassDao
			// .getClassAllCount(userid);
			// getRequest().setAttribute("class_all", class_all);
		}
		// 判断 是否第1次登入
		if ("true".equals(getSessionValue("isLogin"))) {
			getRequest().setAttribute("isLogin", 1);
			getSession().removeAttribute("isLogin");// 销毁
			// 获取该用户的弹窗信息
			messageDao.listSetUserInPop(userid);
			String popIds = messageDao.getUserPopList(userid);
			getRequest().setAttribute("popIds", popIds);
			// 调用存储过程来处理练习分配给的部门 自动分配给学员
			// studyQuizDao.study_depAssign(userid,
			// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
			// 调用存储过程来处理考场分配给的工种 自动分配给学员
			// studyQuizDao.study_examJingzhongAssign(userid);
		} else {
			getRequest().setAttribute("isLogin", 0);
		}

		// // 未读
		// message_no = messageDao.getMessNoCount(elUser.getId());
		// // 已读
		// message_yes = messageDao.getMessYesCount(elUser.getId());
		module = module == null ? "user_center_index.action" : module;
		Return = "studentman";
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "user_center_phone";
		}
		return "user_center";
	}

	/**
	 * 个人中心iframe
	 * 
	 * @return
	 * @throws ElException
	 */
	public String user_center_index() throws ElException {

		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		// 论坛信息
		forums = forumAdminDao.newVersionGetForums(getPageNow(), getPageSize());

		if (elUser.getRole().getId() == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(elUser.getId(), "op",
					ElConstants.TREE_FIANL, true);

		}
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1
				: ntype.getId());
		// 新闻信息
		newses = newsDao.listFabuNewses(6, ntypeTree, nid, getPageNow(),
				getPageSize());
		// 知识
		kledges = knowledgeManageDao.listKledgeAll(department,
				KnowledgeManageConstants.STATUS_ALL, getPageNow(),
				getPageSize());
		// 短消息
		newMessage = messageDao.listMessNewAll(elUser.getId(), getPageNow(),
				getPageSize());

		int newShowye = NewSystemConfOp
				.getIntValue(ElConstants.SYSTEM_NEWINDEXCONFIG_NEWSHOUYE);
		if (newShowye == 0) {// ELN系统
			myDaibanshuwu = scheduleGlobleDao
					.getMyDaibanshuwu(
							getSessionIntValue(ElConstants.SESSION_USERID),
							IndexSystemConfigOp
									.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU),
							IndexSystemConfigOp
									.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_DAIBANSHIWU_LENGTH));

			myallcourse = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYALLCOURSES);
			myexams = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYEXAMS);
			mytrainingcourses = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYTRAININGCOURSES);
			// 我的考试
			if (myexams) {
				myrooms = studyQuizDao
						.study_index_listErsWithoutC(
								getSessionIntValue(ElConstants.SESSION_USERID),
								IndexSystemConfigOp
										.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYEXAMS_LENGTH),
								true);
			}
			// 我的培训班
			if (mytrainingcourses) {
				myClasses = studyClassDao
						.study_index_listMyStudyClass(
								getSessionIntValue(ElConstants.SESSION_USERID),
								IndexSystemConfigOp
										.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYTRAININGCOURSES));
			}
			// 我的全部课程
			if (myallcourse) {
				// 我的测评课程
				boolean open_jtm = JTMSystemConfOp
						.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
				if (open_jtm) {
					elUser = userDao
							.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));

					String cer = JTM.getJTM_cer(String.valueOf(elUser.getId()));
					String JTM_URL = JTMSystemConfOp
							.getValue(ElConstants.SYSTEM_JTM_MY_CEPINGCOURSES_URL)
							+ "?userid="
							+ elUser.getId()
							+ "&jobid="
							+ elUser.getStaid() + "&cer=" + cer;

					Content c = null;
					String returnValue = "";
					try {
						c = Request.Get(JTM_URL).addHeader("Content-Type",
								"text/html; charset=UTF-8").execute()
								.returnContent();
						System.out.println(c.asString());
						returnValue = c.asString();

					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					String[] arr = null;
					if (returnValue != null && !returnValue.equals("")) {
						arr = returnValue.trim().split("\\|");
						if (arr[0].equals("true")) {
							String[] courses = arr[1].split(",");
							if (courses != null && courses.length > 0) {
								// 插入前，不能将userid对应的测评课程删除
								// 因为存在每次学习的开始时间，删除的话再插入，每次开始时间都从0开始。
								for (int i = 0; i < courses.length; i++) {
									// 添加测评课程到课程分配表
									studyCourseDao
											.insertCepingCourse(
													getSessionIntValue(ElConstants.SESSION_USERID),
													Integer
															.parseInt(courses[i]));
								}
							}
						}
					}
				}
				studyCourseList = studyCourseDao
						.study_index_listMyAllCourse(
								getSessionIntValue(ElConstants.SESSION_USERID),
								IndexSystemConfigOp
										.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYALLCOURSES_LENGTH));
			}
		} else if (newShowye == 1) {// 信息管理系统
			gongzuojihua = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA);
			gongzuorizhi = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI);
			daibanshiwu = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU);
			gerenweishen = IndexSystemConfigOp
					.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN);
			// 我的计划
			myPlan = scheduleGlobleDao
					.getMyPlan(
							getSessionIntValue(ElConstants.SESSION_USERID),
							IndexSystemConfigOp
									.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA),
							IndexSystemConfigOp
									.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GONGZUOJIHUA_LENGTH));
			// 我的日志
			myLog = scheduleGlobleDao
					.getMyLog(
							getSessionIntValue(ElConstants.SESSION_USERID),
							IndexSystemConfigOp
									.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI),
							IndexSystemConfigOp
									.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GONGZUORIZHI_LENGTH));
			myDaibanshuwu = scheduleGlobleDao
					.getMyDaibanshuwu(
							getSessionIntValue(ElConstants.SESSION_USERID),
							IndexSystemConfigOp
									.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU),
							IndexSystemConfigOp
									.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_DAIBANSHIWU_LENGTH));
			myNoPass = scheduleGlobleDao
					.getNoPass(
							getSessionIntValue(ElConstants.SESSION_ROLE),
							getSessionIntValue(ElConstants.SESSION_USERID),
							-1,
							-1,
							IndexSystemConfigOp
									.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN),
							department);
		}

		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "user_center_index_phone";
		}
		return "user_center_index";
	}

	/**
	 * 分配学员进入培训班对应的考场和试卷
	 * 
	 * @param course
	 * @param userid
	 * @throws ElException
	 */
	public void examroom_classassignwcInit(int classid, int userid)
			throws ElException {

		List<ExamRoom> examRooms = shoppingDao.getroomlistbyclassid(classid);// 根据培训班id找出该课程的考场

		if (examRooms != null) {
			for (ExamRoom examRoom : examRooms) {
				examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
				if (examPapers == null || examPapers.size() == 0) {
					// 如果该考场没有试卷则什么都不做
				} else {// 否则 判断试卷有没有被添加进去
					if (!eroomDao.checkuser2eroom(examRoom.getId(), userid,
							examRoom.getClassid())) {
						eroomDao.adduser2eroom(examRoom.getId(), userid, 1,
								examRoom.getClassid(),
								CourseConstants.EXAMROOM_FPFS_SQ);
					}
					for (ExamPaper examPaper : examPapers) {
						if (!studyQuizDao.checkStudyExamPaper(userid, examPaper
								.getId(), examRoom.getId(), examRoom
								.getClassid())) {
							// 添加该学员到 学员试卷表中
							studyQuizDao.addStudyExamPaper(userid, examPaper
									.getId(), examRoom.getId(), examRoom
									.getClassid());
						}
					}
				}
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_EROOM,
						ElLoggerConstants.LOG_TYPE_ADD, examRoom.getTitle()
								+ "（添加学员）", ElLoggerConstants.LOG_RES_SUCC,
						examRoom.getId());

			}
		}

	}

	/**
	 * 查询子功能菜单
	 * 
	 * @return
	 * @throws ElException
	 */
	public String listChildFunc() throws ElException {
		List<ElFunc> childs = funcDao.listChildFunc(func.getId());
		String check_json_result = TagsUtil.ToGson(childs);
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getFuncById_newVersion() throws ElException {
		ElFunc fc = funcDao.getFuncById(func.getId());
		String check_json_result = TagsUtil.ToGsonObj(fc);
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断该功能菜单是否可以选择
	 * 
	 * @return
	 * @throws ElException
	 */
	public String can_selected() throws ElException {
		ElFunc fc = funcDao.getFuncById(func.getId());
		String funcCode = fc.getFunccode();
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		int role = getSessionIntValue(ElConstants.SESSION_ROLE);
		String check_json_result = "1";
		if (role != 1 && !AuthorityUtil.checkAuthor(role, funcCode, userid)) {
			check_json_result = "0";
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 选择功能菜单时时能选择一个功能菜单
	 * 
	 * @return
	 * @throws ElException
	 */
	public String rolefunc_addInit_only_can_check_one() throws ElException {
		// 非超级管理员只能分配自己所具有的功能给其他角色
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			funcTree = roleDao.getFuncTree();
		} else {
			funcTree = roleDao
					.getFuncTreeByRoleId(getSessionIntValue(ElConstants.SESSION_ROLE));
		}
		if (funcTree != null && funcTree.getChild() != null) {
			funcTree.setCount(funcTree.getChild().size());
		}
		role = roleDao.getRoleById(role.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "rolefunc_addInit_only_can_check_one_phone";
		}
		return "rolefunc_addInit_only_can_check_one";
	}

	// public String newversion() throws ElException{
	// String funccode = (String)getRequest().getAttribute("funccode");
	// String params = "";
	// Enumeration names = getRequest().getParameterNames();
	// if(names!=null){
	// String param = "";
	// String value = "";
	// while(names.hasMoreElements()){
	// param = (String) names.nextElement();
	// if(!"funccode".equals(param)){
	// if(param!=null&&!param.equals("")){
	// value = getRequest().getParameter(param);
	// if(value!=null&&!value.equals("")){
	// params += param + "=" + value + "&";
	// }
	// }
	// }
	// }
	// }
	// String action = "";
	// if(funccode!=null&&!funccode.equals("")){
	// if(params!=null&&!params.equals("")){
	// if(String.valueOf(params.charAt(params.length()-1)).equals("&")){
	// params = params.substring(0,params.lastIndexOf("&"));
	// }
	// action = funccode + ".action?" + params ;
	// }else{
	// action = funccode + ".action?" ;
	// }
	// }
	// getRequest().setAttribute("funccode", action);
	// return "newversion";
	// }

	public String showallWeishen() throws ElException {
		myNoPass = scheduleGlobleDao
				.getNoPass(
						getSessionIntValue(ElConstants.SESSION_ROLE),
						getSessionIntValue(ElConstants.SESSION_USERID),
						-1,
						-1,
						IndexSystemConfigOp
								.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN),
						department);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "showallWeishen_phone";
		}
		return "showallWeishen";
	}

	// 北京卫生局身份证或者证书查询
	public String frontZhengshuSearch() throws ElException {
		String idCard = getRequest().getParameter("shenfenzheng2")
				.toLowerCase();// 身份证号
		String zhengshu = getRequest().getParameter("zhengshuhao2");// 证书号
		String[] array = new String[5];
		ELUser eu = null;
		if (idCard != null && !idCard.equals("")) {// 有身份证

			eu = userDao.getEluserByShenfenzhang(idCard);
			myClasses = studyClassDao.listMyGraduatedClass(eu.getId(),
					ClassConstants.CLASS_APPLY_STATUS_YES);
		}
		myClasses = myClasses == null ? new ArrayList<MyClass>() : myClasses;

		if (zhengshu != null && !zhengshu.equals("")) {// 有证书号
			// array = zhengshu.split(" ");
			// 证书号找到用户，然后再找到学籍
			// 判断是否同上一个人是同一个人
			// 2013 1083 0001 ==> 1
			//			
			// //判断是否分配了年检培训班
			// Calendar cal = Calendar.getInstance();
			// int year = cal.get(Calendar.YEAR);
			// MyClass new_cla_ = studyClassDao.getNaZhengClass(year);
			// MyClass nianjian_cla_ = studyClassDao.getNianjianClass(year);
			// boolean new_check = false;
			// if(new_cla_!=null){
			// new_check =
			// studyClassDao.checkClassIsUser(new_cla_.getElClass().getId(),
			// getSessionIntValue(ElConstants.SESSION_USERID));
			// }
			// // cla =
			// studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
			//			
			// if(!new_check){
			// this.setElmessage("还未购买年检培训班!");
			// return "error";
			// }
			for (int i = 0; i < zhengshu.length() / 4; i++) {
				array[i] = zhengshu.substring(i * 4, (i + 1) * 4);
				System.out.println("----" + array[i]);
			}
			if (zhengshu.length() == 12) {
				int year = Integer.valueOf(array[0]);
				int classid = Integer.valueOf(array[1]);
				int cerificateNo = NewVersionUtil.getCerificateNo(array[2]);
				System.out.println(cerificateNo);
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String shenfenzheng = getSessionValue(ElConstants.SESSION_SHENFENZHENG);
				myClass = studyClassDao.getZhengShuByNo(year, classid,
						cerificateNo);
				if (userid != 0) {
					if (idCard != null && !idCard.equals("")
							&& !shenfenzheng.equals(idCard)) {
						this.setElmessage("请输入本人身份证号查询");
						return "error";
					}
					int flag = studyClassDao.getZhengShuByNo(year, classid,
							cerificateNo, userid);

					if (flag == 0) {
						this.setElmessage("请输入正确的证书号或本人证书号查询");
						return "error";
					}
				}
			} else {
				this.setElmessage("证书号位数为12位，请检查所输入的位数是否正确！");
				return "error";
			}
			// int uid =
			// classDao.getUseridByCertificateNo(cerificateNo,new_cla_.getElClass().getId());
			// if(uid == 0){
			// this.setElmessage("还未达到查看证书的条件!");
			// return "error";
			// }
			// List<MyClass> cls = null;
			// eu = eu == null?new ELUser(0):eu;
			// if(uid != eu.getId()){
			// cls =
			// studyClassDao.listMyGraduatedClass(uid,ClassConstants.CLASS_APPLY_STATUS_YES);
			// }
			// myClasses.addAll(cls==null?new ArrayList<MyClass>():cls);
		}

		if (zhengshu != null && !zhengshu.equals("") && idCard != null
				&& !idCard.equals("")) {// 有证书号,身份证
			// array = zhengshu.split(" ");
			// 证书号找到用户，然后再找到学籍
			// 判断是否同上一个人是同一个人
			// 2013 1083 0001 ==> 1
			//			
			// //判断是否分配了年检培训班
			// Calendar cal = Calendar.getInstance();
			// int year = cal.get(Calendar.YEAR);
			// MyClass new_cla_ = studyClassDao.getNaZhengClass(year);
			// MyClass nianjian_cla_ = studyClassDao.getNianjianClass(year);
			// boolean new_check = false;
			// if(new_cla_!=null){
			// new_check =
			// studyClassDao.checkClassIsUser(new_cla_.getElClass().getId(),
			// getSessionIntValue(ElConstants.SESSION_USERID));
			// }
			// // cla =
			// studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
			//			
			// if(!new_check){
			// this.setElmessage("还未购买年检培训班!");
			// return "error";
			// }
			for (int i = 0; i < zhengshu.length() / 4; i++) {
				array[i] = zhengshu.substring(i * 4, (i + 1) * 4);
				System.out.println("----" + array[i]);
			}
			if (zhengshu.length() == 12) {
				int year = Integer.valueOf(array[0]);
				int classid = Integer.valueOf(array[1]);
				int cerificateNo = NewVersionUtil.getCerificateNo(array[2]);
				System.out.println(cerificateNo);
				// int userid=getSessionIntValue(ElConstants.SESSION_USERID);
				// String shenfenzheng =
				// getSessionValue(ElConstants.SESSION_SHENFENZHENG);
				myClass = studyClassDao.getZhengShuByNoIdCard(year, classid,
						cerificateNo, idCard);
			} else {
				this.setElmessage("证书号位数为12位，请检查所输入的位数是否正确！");
				return "error";
			}
			// int uid =
			// classDao.getUseridByCertificateNo(cerificateNo,new_cla_.getElClass().getId());
			// if(uid == 0){
			// this.setElmessage("还未达到查看证书的条件!");
			// return "error";
			// }
			// List<MyClass> cls = null;
			// eu = eu == null?new ELUser(0):eu;
			// if(uid != eu.getId()){
			// cls =
			// studyClassDao.listMyGraduatedClass(uid,ClassConstants.CLASS_APPLY_STATUS_YES);
			// }
			// myClasses.addAll(cls==null?new ArrayList<MyClass>():cls);
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if (b == true) {
			return "frontZhengshuSearch_phone";
		}
		return "frontZhengshuSearch";
	}

	// -----------短信验证--------------------------------------
	public String sendSMSCode() throws ElException {
		String movephone = elUser.getMovephone();
		String yzcode = randomNum();
		String content = "您此次注册的验证码是：" + randomNum();
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			String msg = "{\"tishi\":\""
					+ userDao.sendMsg(movephone, content, yzcode) + "\"}";
			System.out.println(msg);
			localPrintWriter.println(msg);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("发送失败", e);
		}
		return null;
	}

	public String checkSMSCode() throws ElException {
		String movephone = elUser.getMovephone();
		// String yzcode = randomNum();
		String yzCode = yzcode.getYzCode();
		// String content = "您此次注册的验证码是："+randomNum();
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			String msg = "{\"flag\":\"" + userDao.checkMsg(movephone, yzCode)
					+ "\"}";
			System.out.println(msg);
			localPrintWriter.println(msg);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("发送失败", e);
		}
		return null;
	}

	public static String randomNum() {
		String arr = "";
		int[] a = new int[4];
		for (int i = 0; i < 4; i++) {
			a[i] = (int) (Math.random() * 10);
			arr = arr + a[i];
		}
		return arr;
	}

	// ///////////////////////
	// gets() sets()
	public NewVersionDao getNewVersionDao() {
		return newVersionDao;
	}

	public void setNewVersionDao(NewVersionDao newVersionDao) {
		this.newVersionDao = newVersionDao;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public ElFunc getMenu() {
		return menu;
	}

	public void setMenu(ElFunc menu) {
		this.menu = menu;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<ElFunc> getMenus() {
		return menus;
	}

	public void setMenus(List<ElFunc> menus) {
		this.menus = menus;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public int getIsFromRegister() {
		return isFromRegister;
	}

	public void setIsFromRegister(int isFromRegister) {
		this.isFromRegister = isFromRegister;
	}

	public int getYzCodeIsNo() {
		return yzCodeIsNo;
	}

	public void setYzCodeIsNo(int yzCodeIsNo) {
		this.yzCodeIsNo = yzCodeIsNo;
	}

	public String getYzCode() {
		return yzCode;
	}

	public void setYzCode(String yzCode) {
		this.yzCode = yzCode;
	}

	public MyLogin getMyLogin() {
		return myLogin;
	}

	public void setMyLogin(MyLogin myLogin) {
		this.myLogin = myLogin;
	}

	public ShoppingDao getShoppingDao() {
		return shoppingDao;
	}

	public void setShoppingDao(ShoppingDao shoppingDao) {
		this.shoppingDao = shoppingDao;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
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

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public ElFunc getFunc() {
		return func;
	}

	public void setFunc(ElFunc func) {
		this.func = func;
	}

	public FuncDao getFuncDao() {
		return funcDao;
	}

	public void setFuncDao(FuncDao funcDao) {
		this.funcDao = funcDao;
	}

	public List<ElFunc> getMenus_two() {
		return menus_two;
	}

	public void setMenus_two(List<ElFunc> menus_two) {
		this.menus_two = menus_two;
	}

	public List<ElFunc> getMenus_three() {
		return menus_three;
	}

	public void setMenus_three(List<ElFunc> menus_three) {
		this.menus_three = menus_three;
	}

	public String getStuff_url() {
		return stuff_url;
	}

	public void setStuff_url(String stuff_url) {
		this.stuff_url = stuff_url;
	}

	public ElFunc getFuncTree() {
		return funcTree;
	}

	public void setFuncTree(ElFunc funcTree) {
		this.funcTree = funcTree;
	}

	public ElRole getRole() {
		return role;
	}

	public void setRole(ElRole role) {
		this.role = role;
	}

	public ScheduleGlobleDao getScheduleGlobleDao() {
		return scheduleGlobleDao;
	}

	public void setScheduleGlobleDao(ScheduleGlobleDao scheduleGlobleDao) {
		this.scheduleGlobleDao = scheduleGlobleDao;
	}

	public int getCount_news() {
		return count_news;
	}

	public void setCount_news(int count_news) {
		this.count_news = count_news;
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

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public List<Map<String, Object>> getMyDaiPass() {
		return myDaiPass;
	}

	public void setMyDaiPass(List<Map<String, Object>> myDaiPass) {
		this.myDaiPass = myDaiPass;
	}

	public boolean isGerendaishen() {
		return gerendaishen;
	}

	public void setGerendaishen(boolean gerendaishen) {
		this.gerendaishen = gerendaishen;
	}

	public boolean isDaibanshiwu() {
		return daibanshiwu;
	}

	public void setDaibanshiwu(boolean daibanshiwu) {
		this.daibanshiwu = daibanshiwu;
	}

	public List<Map<String, Object>> getMyDaibanshuwu() {
		return myDaibanshuwu;
	}

	public void setMyDaibanshuwu(List<Map<String, Object>> myDaibanshuwu) {
		this.myDaibanshuwu = myDaibanshuwu;
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public int getMessage_no() {
		return message_no;
	}

	public void setMessage_no(int message_no) {
		this.message_no = message_no;
	}

	public int getMessage_yes() {
		return message_yes;
	}

	public void setMessage_yes(int message_yes) {
		this.message_yes = message_yes;
	}

	public List<Message> getNewMessage() {
		return newMessage;
	}

	public void setNewMessage(List<Message> newMessage) {
		this.newMessage = newMessage;
	}

	public int getCount_msg() {
		return count_msg;
	}

	public void setCount_msg(int count_msg) {
		this.count_msg = count_msg;
	}

	public String getIsFromAdmin() {
		return isFromAdmin;
	}

	public void setIsFromAdmin(String isFromAdmin) {
		this.isFromAdmin = isFromAdmin;
	}

	public int getEroom_no() {
		return eroom_no;
	}

	public void setEroom_no(int eroom_no) {
		this.eroom_no = eroom_no;
	}

	public int getEroom_all() {
		return eroom_all;
	}

	public void setEroom_all(int eroom_all) {
		this.eroom_all = eroom_all;
	}

	public int getClass_yes() {
		return class_yes;
	}

	public void setClass_yes(int class_yes) {
		this.class_yes = class_yes;
	}

	public int getClass_all() {
		return class_all;
	}

	public void setClass_all(int class_all) {
		this.class_all = class_all;
	}

	public StudyClassDao getStudyClassDao() {
		return studyClassDao;
	}

	public void setStudyClassDao(StudyClassDao studyClassDao) {
		this.studyClassDao = studyClassDao;
	}

	public KnowledgeManageDao getKnowledgeManageDao() {
		return knowledgeManageDao;
	}

	public void setKnowledgeManageDao(KnowledgeManageDao knowledgeManageDao) {
		this.knowledgeManageDao = knowledgeManageDao;
	}

	public List<Kledge> getKledges() {
		return kledges;
	}

	public void setKledges(List<Kledge> kledges) {
		this.kledges = kledges;
	}

	public List<News> getNewses() {
		return newses;
	}

	public void setNewses(List<News> newses) {
		this.newses = newses;
	}

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public NewsType getNtypeTree() {
		return ntypeTree;
	}

	public void setNtypeTree(NewsType ntypeTree) {
		this.ntypeTree = ntypeTree;
	}

	public NewsType getNtype() {
		return ntype;
	}

	public void setNtype(NewsType ntype) {
		this.ntype = ntype;
	}

	public boolean isMyallcourse() {
		return myallcourse;
	}

	public void setMyallcourse(boolean myallcourse) {
		this.myallcourse = myallcourse;
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

	public List<MyRoom> getMyrooms() {
		return myrooms;
	}

	public void setMyrooms(List<MyRoom> myrooms) {
		this.myrooms = myrooms;
	}

	public List<MyClass> getMyClasses() {
		return myClasses;
	}

	public void setMyClasses(List<MyClass> myClasses) {
		this.myClasses = myClasses;
	}

	public List<MyCourse> getStudyCourseList() {
		return studyCourseList;
	}

	public void setStudyCourseList(List<MyCourse> studyCourseList) {
		this.studyCourseList = studyCourseList;
	}

	public StudyCourseDao getStudyCourseDao() {
		return studyCourseDao;
	}

	public void setStudyCourseDao(StudyCourseDao studyCourseDao) {
		this.studyCourseDao = studyCourseDao;
	}

	public ForumAdminDao getForumAdminDao() {
		return forumAdminDao;
	}

	public void setForumAdminDao(ForumAdminDao forumAdminDao) {
		this.forumAdminDao = forumAdminDao;
	}

	public List<Forum> getForums() {
		return forums;
	}

	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

	public List<Map<String, Object>> getMyPlan() {
		return myPlan;
	}

	public void setMyPlan(List<Map<String, Object>> myPlan) {
		this.myPlan = myPlan;
	}

	public List<Map<String, Object>> getMyLog() {
		return myLog;
	}

	public void setMyLog(List<Map<String, Object>> myLog) {
		this.myLog = myLog;
	}

	public List<Map<String, Object>> getMyNoPass() {
		return myNoPass;
	}

	public void setMyNoPass(List<Map<String, Object>> myNoPass) {
		this.myNoPass = myNoPass;
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

	public boolean isGerenweishen() {
		return gerenweishen;
	}

	public void setGerenweishen(boolean gerenweishen) {
		this.gerenweishen = gerenweishen;
	}

	public MyClass getCla() {
		return cla;
	}

	public void setCla(MyClass cla) {
		this.cla = cla;
	}

	public boolean isNeedAllocation() {
		return needAllocation;
	}

	public void setNeedAllocation(boolean needAllocation) {
		this.needAllocation = needAllocation;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public ExamRoom getRoom() {
		return room;
	}

	public void setRoom(ExamRoom room) {
		this.room = room;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
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

	public MyClass getNew_cla() {
		return new_cla;
	}

	public void setNew_cla(MyClass new_cla) {
		this.new_cla = new_cla;
	}

	public YzCode getYzcode() {
		return yzcode;
	}

	public void setYzcode(YzCode yzcode) {
		this.yzcode = yzcode;
	}

	public IntelligentLogin getIntelligentLogin() {
		return intelligentLogin;
	}

	public void setIntelligentLogin(IntelligentLogin intelligentLogin) {
		this.intelligentLogin = intelligentLogin;
	}

	public MyClass getNianjian_cla() {
		return nianjian_cla;
	}

	public void setNianjian_cla(MyClass nianjian_cla) {
		this.nianjian_cla = nianjian_cla;
	}

	public int getIsBuyNianjianClass() {
		return isBuyNianjianClass;
	}

	public void setIsBuyNianjianClass(int isBuyNianjianClass) {
		this.isBuyNianjianClass = isBuyNianjianClass;
	}

	public int getIsChangeElclass() {
		return isChangeElclass;
	}

	public void setIsChangeElclass(int isChangeElclass) {
		this.isChangeElclass = isChangeElclass;
	}

	public MyClass getMyClass() {
		return myClass;
	}

	public void setMyClass(MyClass myClass) {
		this.myClass = myClass;
	}

	/**
	 * 外经贸个人中心个人信息查看
	 */

	public String wjm_student_myinfo() throws ElException {
		// elUser = userDao
		// .getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		// StationDaoImpl staDao =new StationDaoImpl();
		// Station sta = staDao.getStById(elUser.getStaid());
		// String grantTime = new SimpleDateFormat("yyyy")
		// .format(elUser.getShengri_());
		// Calendar ca = Calendar.getInstance();
		// int year = ca.get(Calendar.YEAR);
		// int sr = Integer.parseInt(grantTime);
		// int age = year-sr;
		// elUser.setAge(age);
		// elUser.setXianzhiwei(sta.getName());

		elUser = userDao
				.getUserById_cisco(getSessionIntValue(ElConstants.SESSION_USERID));
		return "wjm_student_myinfo";
		// return "student_myinfo";
	}

	public String wjm_student_myalterInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs = userDao.getBaseDatatByTypeid(1);
			zhiwus = userDao.getBaseDatatByTypeid(2);
			zhijis = userDao.getBaseDatatByTypeid(3);
			// gangweis=userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5);
		} else {
			jingzhongs = userDao.getBaseDatatByTypeidc(1);
			zhiwus = userDao.getBaseDatatByTypeidc(2);
			zhijis = userDao.getBaseDatatByTypeidc(3);
			// gangweis=userDao.getBaseDatatByTypeidc(4);
			dishis = userDao.getBaseDatatByTypeidc(5);
		}
		elUser = userDao
				.getUserById_wjm(getSessionIntValue(ElConstants.SESSION_USERID));
		// elUser = userDao
		// .getUserById(getSessionIntValue(ElConstants.SESSION_USERID));

		// depTree = departmentDao.getDepTree(1, -1, true);
		// 判断注册信息是否都要验证
		// String resultPage="student_myalter";
		// if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)){
		// getRequest().setAttribute("isAll", "yes");
		// //resultPage="student_myalter";
		// }else{
		// getRequest().setAttribute("isAll", "no");
		// //resultPage="student_myalter_noall";
		// }
		// return "student_myalter";
		// return resultPage;
		return "wjm_student_myalterInit";
	}

	/**
	 * 外经贸个人信息修改
	 */
	public String wjm_student_myalter() throws ElException {
		// if(userDao.checkUserShenfenzheng(elUser.getShenfenzheng(),elUser.getId())){
		// jingzhongs=userDao.getBaseDatatByTypeid(1);
		// zhiwus=userDao.getBaseDatatByTypeid(2);
		// zhijis=userDao.getBaseDatatByTypeid(3);
		// gangweis=userDao.getBaseDatatByTypeid(4);
		// dishis=userDao.getBaseDatatByTypeid(5);
		// setElmessage("您所填的身份证已被其他人使用，请重新输入！");
		// //判断注册信息是否都要验证
		// if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)){
		// getRequest().setAttribute("isAll", "yes");
		// }else{
		// getRequest().setAttribute("isAll", "no");
		// }
		// elUser=userDao.getUserById(elUser.getId());
		// return "student_myalter";//返回修改页面
		// }
		// elUser.setId(getSessionIntValue(ElConstants.SESSION_USERID));
		// userDao.alterMyInfo(elUser);

		// elUser.setPassword(MD5.crypt(elUser.getPassword()));
		userDao.update_cisco(elUser);
		elUser = userDao.getUserById_cisco(elUser.getId());
		return "wjm_student_myalter_success";
	}

	/**
	 * 外经贸用户列表（查询条件不带岗位）
	 */
	public String wjm_account_search() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// stTree = stationDao.getStTree_level1(
		// getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
		// true);
		// else {
		// stTree = stationDao.getStTree_level1(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		// if (station == null || station.getId() <= 0) {
		// sub_department = 1;
		// station = stTree;
		// } else
		// station = stationDao.getStById(station.getId());
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=user.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			elUsers = userDao.listUsers(department, sub_department, elUser);
			try {
				String titles[] = { "用户名", "密码(不能修改此列,新增用户密码不能填写，密码默认是123456)",
						"序号", "姓名", "性别", "地市", "身份证", "职级", "职务", "警种", "部门编号" };
				String attrs[] = { "username", "password", "xuhao", "realname",
						"sex", "dishi_", "shenfenzheng", "zhiji_", "zhiwu_",
						"jingzhong_", "department.bh" };
				new ExcelOutPut().writeExcel("用户表", getResponse()
						.getOutputStream(), titles, ELUser.class.getName(),
						elUsers, attrs);
			} catch (Exception e) {
				logger.error("导出账号列表错误", e);
			}
			return null;
		}
		elUsers = userDao.wjm_listUsers(department, sub_department, elUser,
				getPageNow(), getPageSize());
		count = userDao.wjm_listUsersSize(department, sub_department, elUser);
		roles = roleDao.listRoles();

		return "account_result";
	}

	/**
	 * 外经贸用户列表(带岗位；查询条件需要岗位时，可使用这个方法)
	 */
	public String wjm_account_search2() throws ElException {
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
			stTree = stationDao
					.getStTree_level1(
							getSessionIntValue(ElConstants.SESSION_MYSTATION),
							-1, true);
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
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=user.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			elUsers = userDao.listUsers(department, sub_department, elUser);
			try {
				String titles[] = { "用户名", "密码(不能修改此列,新增用户密码不能填写，密码默认是123456)",
						"序号", "姓名", "性别", "地市", "身份证", "职级", "职务", "警种", "部门编号" };
				String attrs[] = { "username", "password", "xuhao", "realname",
						"sex", "dishi_", "shenfenzheng", "zhiji_", "zhiwu_",
						"jingzhong_", "department.bh" };
				new ExcelOutPut().writeExcel("用户表", getResponse()
						.getOutputStream(), titles, ELUser.class.getName(),
						elUsers, attrs);
			} catch (Exception e) {
				logger.error("导出账号列表错误", e);
			}
			return null;
		}
		elUsers = userDao.wjm_listUsers(department, station, sub_department,
				elUser, getPageNow(), getPageSize());
		count = userDao.wjm_listUsersSize(department, station, sub_department,
				elUser);
		roles = roleDao.listRoles();

		return "account_result";
	}

	public String wjm_account_addInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs = userDao.getBaseDatatByTypeid(1);
			zhiwus = userDao.getBaseDatatByTypeid(2);
			zhijis = userDao.getBaseDatatByTypeid(3);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5);
			// 论坛级别
			// luntanjibies = userDao.getBaseDatatByTypeid(6);
		} else {
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			jingzhongs = userDao.getBaseDatatByTypeid(1, userid);
			zhiwus = userDao.getBaseDatatByTypeid(2, userid);
			zhijis = userDao.getBaseDatatByTypeid(3, userid);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5, userid);
			// luntanjibies = userDao.getBaseDatatByTypeid(6, userid);
			// 论坛级别
		}
		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		elUser = new ELUser();
		elUser.setValid(!SystemConfOp
				.getBooleanValue(ElConstants.REGISTER_NEED_SH));// 开通 关闭
		return "wjm_account_addInit";
	}

	/**
	 * 外经贸添加用户
	 */
	public String wjm_account_add() throws ElException {
		if (userDao.checkUsername(elUser.getUsername())) {
			setElmessage("该学号已存在！");
			return "error";
		}
		elUser.setPassword(MD5.crypt(elUser.getPassword()));
		int ii = userDao.insert_cisco(elUser);
		elUser.setId(ii);
		return "wjm_account_add";
	}

	/**
	 * 外经贸查看用户信息
	 */
	public String wjm_account_view() throws ElException {
		elUser = userDao.getUserById_wjm(elUser.getId());
		return "wjm_account_view";
	}

	public String wjm_account_alterInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs = userDao.getBaseDatatByTypeid(1);
			zhiwus = userDao.getBaseDatatByTypeid(2);
			zhijis = userDao.getBaseDatatByTypeid(3);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5);
			// luntanjibies = userDao.getBaseDatatByTypeid(6);
		} else {
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			jingzhongs = userDao.getBaseDatatByTypeid(1, userid);
			zhiwus = userDao.getBaseDatatByTypeid(2, userid);
			zhijis = userDao.getBaseDatatByTypeid(3, userid);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5, userid);
			// luntanjibies = userDao.getBaseDatatByTypeid(6, userid);
		}
		if (elUser.getId() == getSessionIntValue(ElConstants.SESSION_USERID)) {
			return "noright";
		}
		elUser = userDao.getUserById_wjm(elUser.getId());
		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		return "wjm_account_alterInit";
	}

	/**
	 * 外经贸修改用户信息
	 */
	public String wjm_account_alter() throws ElException {
		// elUser.setPassword(MD5.crypt(elUser.getPassword()));
		// userDao.update_cisco(elUser);
		userDao.update_wjm(elUser);
		if(elUser.getFingerInfo()!=null&&!"".equals(elUser.getFingerInfo().trim())){
		int flag_=userDao.insertFingerInfo(elUser);
		}
		if (elUser.getPassword() != null
				&& !"".equals(elUser.getPassword().trim())) {
			elUser.setPassword(MD5.crypt(elUser.getPassword()));
			userDao.alterMyPwd(elUser);
		}
		elUser = userDao.getUserById_wjm(elUser.getId());
		this.upOk = 1;
		return "wjm_account_alter_success";
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public int getSumBX() {
		return sumBX;
	}

	public void setSumBX(int sumBX) {
		this.sumBX = sumBX;
	}

	public int getHasSumXX() {
		return hasSumXX;
	}

	public void setHasSumXX(int hasSumXX) {
		this.hasSumXX = hasSumXX;
	}

	public int getHasSumBX() {
		return hasSumBX;
	}

	public void setHasSumBX(int hasSumBX) {
		this.hasSumBX = hasSumBX;
	}

	public int getSumScore() {
		return sumScore;
	}

	public void setSumScore(int sumScore) {
		this.sumScore = sumScore;
	}

	public int getHasSumScore() {
		return hasSumScore;
	}

	public void setHasSumScore(int hasSumScore) {
		this.hasSumScore = hasSumScore;
	}

	public double getScoreProcess() {
		return scoreProcess;
	}

	public void setScoreProcess(double scoreProcess) {
		this.scoreProcess = scoreProcess;
	}

	public MyClass getMyClassAll() {
		return myClassAll;
	}

	public void setMyClassAll(MyClass myClassAll) {
		this.myClassAll = myClassAll;
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

	public String wjm_account_importInit() throws ElException {

		return "wjm_account_importInit";
	}

	/**
	 * 导入前的检测（外经贸导入用户）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String wjm_accountImportCheck() throws Exception {

		String resultPage = "account_importBydep";

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
				String isOk = UserExcelUtil.wjm_checkWriteUser(st);
				// if(!"true".equals(isOk)&&!"".equals(isOk)){//返回
				setElmessage(isOk);
				// 复制此文件到服务器临时保存
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String filename = "user_" + userid + "_"
						+ System.currentTimeMillis();
				J2EEFileUtil.upload(st, "xls", "/importtemp/", filename);
				stFileName = filename + ".xls";
				return "accountImportInfo";
			}
		} else {
			setElmessage("请输入上传文件");
			return resultPage;
		}
	}

	public String wjm_account_import() throws ElException {
		if (stFileName != null) {
			File xls = new File(ServletActionContext.getServletContext()
					.getRealPath("/")
					+ "/importtemp/" + stFileName);
			if (xls.exists()) {
				String isOk = "";
				isOk = UserExcelUtil.wjm_writeUser(xls);
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
		// return "account_list";
		return "account_import_success";
	}

	// sd1231
	// 山东项目注册初始化
	public String sd_registerInit() throws ElException {
		elUser = elUser == null ? new ELUser() : elUser;
		System.out.println(elUser.getGangwei());
		// 判断注册信息是否都要验证
		if (SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
			getRequest().setAttribute("isAll", "yes");
			// return "register2";
		} else {
			getRequest().setAttribute("isAll", "no");
			// return "register4";
		}
		if (elUser != null && elUser.getGangwei() != null) {
			jingzhongs = userDao.getBaseDatatByTypeidc_sd(Integer
					.parseInt(elUser.getGangwei()));
			BasetNameUtil bnu = new BasetNameUtil();
			baseType = bnu.getBaseTypeById(Integer
					.parseInt(elUser.getGangwei()));
		}

		gangweis = userDao.getBaseDatatByTypeidc(4);
		dishis = userDao.getBaseDatatByTypeidc(5);
		return "sd_registerInit";
	}

	// 山东项目注册
	public String sd_register() throws ElException {
		String resultPage = "sd_register";
		// 判断注册信息是否都要验证
		if (SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER) == 1) {
			// 对用户名是身份证号码的情况进行检查
			// String userName = elUser.getUsername().trim().toLowerCase();

			// if ("".equals(CheckCard.IDCardValidate(userName))) {//
			// 如果用户名是有效身份证
			// boolean isExistUserName = false;
			//
			// // 如果用户的身份证号码是15位，判断数据库中是否存在该人15位和18位的身份证号码
			// if (userName.length() == 15) {
			// isExistUserName = userDao.checkUsername(userName) ? true
			// : userDao.checkUsername(CheckCard
			// .fixPersonIDCode(userName));
			// } else {// 如果用户的身份证号码是18位，判断数据库中是否存在该人15位和18位的身份证号码
			// isExistUserName = userDao.checkUsername(userName) ? true
			// : userDao.checkUsername(CheckCard
			// .fixPersonIDCode15(userName));
			// }
			// if (isExistUserName) {
			// setElmessage("您注册的用户名已存在，请重新输入用户名！");
			// if (SystemConfOp
			// .getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
			// getRequest().setAttribute("isAll", "yes");
			// } else {
			// getRequest().setAttribute("isAll", "no");
			// }
			// return resultPage;
			// }
			// }

			// if (userDao.checkUsername(elUser.getUsername())) {
			// setElmessage("您注册的用户名已存在，请重新输入用户名！");
			// // 判断注册信息是否都要验证
			// if (SystemConfOp
			// .getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
			// getRequest().setAttribute("isAll", "yes");
			// } else {
			// getRequest().setAttribute("isAll", "no");
			// }
			// return resultPage;
			// }
			// if (userDao.checkUserShenfenzheng(elUser.getShenfenzheng(),
			// elUser
			// .getId())) {
			// setElmessage("您所填的身份证已被其他人使用，请重新输入！");
			// // 判断注册信息是否都要验证
			// // if (SystemConfOp
			// // .getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL))
			// {
			// // getRequest().setAttribute("isAll", "yes");
			// // } else {
			// // getRequest().setAttribute("isAll", "no");
			// // }
			// return resultPage;// 返回注册页面
			// }
			// 注册用户
			elUser.setValid(!SystemConfOp
					.getBooleanValue(ElConstants.REGISTER_NEED_SH));
			String tempPassword = elUser.getPassword();//
			elUser.setPassword(MD5.crypt(elUser.getPassword()));
			userDao.insert_sd(elUser);
			if (!SystemConfOp.getBooleanValue(ElConstants.REGISTER_NEED_SH)) {// 如果不需要注册审核
				elUser = userDao.query(elUser.getUsername());
				if (!SystemConfOp.getBooleanValue(ElConstants.REGISTER_NEED_SH)) {
					elUser.setPassword(tempPassword);
					elUser.setUsername(elUser.getUsername());
					return "sd_user_center_login";
				} else {
					return "sd_register_success";
				}
			}

		} else {
			setElmessage("系统关闭了注册功能，请与管理员联系");
			return "error";
		}
		return "sd_register";
	}

	// 山东项目新版个人中心登录
	public String sd_user_center_login() throws ElException {
		// System.out.println(elUser.getUsername() + " == " +
		// elUser.getPassword());
		if (isFromRegister != 1) {// 不是注册成功后的登录
			if (yzCodeIsNo != 1) {// 不是从前台首页调用的action
				yzCodeIsNo = SystemConfOp
						.getIntValue(ElConstants.SYSTEM_CONF_YZCODE_OPEN);
				if (yzCodeIsNo == 1) {
					if (yzCode == null || yzCode.equals("")) {
						this.setElmessage("验证码不能为空,请填写验证码!!!");
						return "sd_user_center_login";
					} else {
						if ((getSession().getAttribute("yzCodey") != null && !((String) getSession()
								.getAttribute("yzCodey")).equals(yzCode))) {
							this.setElmessage("验证码错误,请填写验证码!!!");
							return "sd_user_center_login";
						}
					}
				}
			}
		}
		if (elUser == null) {
			setElmessage("请从正常入口进入！");
			return "error";
		}

		// 判断是否填写用户名
		if (elUser.getUsername() == null
				|| "".equals(elUser.getUsername().trim())) {
			setElmessage("请填写用户名！");
			return "sd_user_center_login";
		}
		// 将用户名转成小写（库中存储着小写字符）
		elUser.setUsername(elUser.getUsername().trim().toLowerCase());
		String username = "";
		if (elUser.getUsername().length() == 15) {
			// 用户名15位转换成18位
			username = CheckCard.fixPersonIDCode(elUser.getUsername())
					.toLowerCase();
		} else if (elUser.getUsername().trim().length() == 18) {
			// 用户名18位的话转成15位
			username = CheckCard.fixPersonIDCode15(elUser.getUsername())
					.toLowerCase();
			;
		} else {
			username = elUser.getUsername();
		}
		// 检测15位和18位和密码是否匹配
		if (userDao
				.check(elUser.getUsername(), MD5.crypt(elUser.getPassword()))
				|| userDao.check(username, MD5.crypt(elUser.getPassword()))) {
			// 校验通过
			getSession().removeAttribute("yzCodey");
			// 获取用户信息
			elUser = userDao.query(elUser.getUsername().trim());
			if (elUser.getId() == 0)// 登录账号不符合的时候查询转换后的账号
				elUser = userDao.query(username);
			// 检测是否已经在线
			if (OnlineUtil.checkUser(elUser.getId() + "")) {
				// 查出上次该用户的最后登录信息
				if (myLogin == null) {
					myLogin = new MyLogin();
				}
				String tempIpAddr = myLogin.getIpAddr();
				myLogin = userDao.getSessionUserLoginInfo(elUser.getId());
				getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
				return "login_logout";
			}
			if (elUser.getValid()) {
				// 如果是超级管理员
				if (elUser.getRole().getId() == 1) {
					getSession().setAttribute(ElConstants.SESSION_USERID,
							elUser.getId());
					getSession().setAttribute(ElConstants.SESSION_USERNAME,
							elUser.getUsername().trim());
					getSession().setAttribute(ElConstants.SESSION_REALNAME,
							elUser.getRealname());
					getSession().setAttribute(ElConstants.SESSION_ROLE,
							elUser.getRole().getId());
					getSession().setAttribute(ElConstants.SESSION_ROLENAME,
							elUser.getRole().getName());
					// 如果是超管，部门id为根
					if (elUser.getRole().getId() == 1) {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT, 1);
					} else {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,
							elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser = userDao.getUserById(elUser.getId());

					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
							elUser.getShenfenzheng());

					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
							elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if (myLogin == null) {
						myLogin = new MyLogin();
					}
					// 判断是否需要记录ip
					if (SystemConfOp
							.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);

					// // 一下代码用于培训班分配给部门
					// List<Integer> depparentidList = new ArrayList<Integer>();
					// depparentidList = shoppingDao.getdempParentid(elUser
					// .getDepartment().getId());// 得到该用户所有上级部门id
					//
					// List<Integer> depclassidList = new ArrayList<Integer>();
					// for (Integer pid : depparentidList) {
					// depclassidList = shoppingDao
					// .getdepartmenttoclassbydepid(pid);//
					// 循环所有父部门ID并找出该部门被分配的培训班集合
					// for (Integer elclassid : depclassidList) {//
					// 循环该集合进行培训班的绑定和分配
					// classDao.assign2userAdd3(elUser.getId(), elclassid,
					// ClassConstants.CLASS_SQFS_FP);
					//
					// // 分配考场
					// examroom_classassignwcInit(elclassid, elUser
					// .getId());
					// }
					// }

					// if (isFromAdmin != null && isFromAdmin.equals("1")) {
					// return "user_admin_login_success";
					// }
					if (elUser.getRole().getId() == 1 || elUser.getRole().getId() == 2) {
						logger.info(SystemConfOp
								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX_SD));
						return "study";
					} else {
						int loginCount = userDao.loginCount();
						logger.info(SystemConfOp
								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX_SD));
						if(SystemConfOp
								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX_SD)!=0){
						if(loginCount>(SystemConfOp
								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX_SD))){
							this.setElmessage("当前登录人数已达到规定最大数，请稍后重试或联系管理员");
							return "sd_user_center_login";
						}
						}
						return "sd_user_center_login_success";
					}
				} else {
					getSession().setAttribute(ElConstants.SESSION_USERID,
							elUser.getId());
					getSession().setAttribute(ElConstants.SESSION_USERNAME,
							elUser.getUsername().trim());
					getSession().setAttribute(ElConstants.SESSION_REALNAME,
							elUser.getRealname());
					getSession().setAttribute(ElConstants.SESSION_ROLE,
							elUser.getRole().getId());
					getSession().setAttribute(ElConstants.SESSION_ROLENAME,
							elUser.getRole().getName());
					// 如果是超管，部门id为根
					if (elUser.getRole().getId() == 1) {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT, 1);
					} else {
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,
							elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser = userDao.getUserById(elUser.getId());

					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
							elUser.getShenfenzheng());

					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,
							elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if (myLogin == null) {
						myLogin = new MyLogin();
					}
					// 判断是否需要记录ip
					if (SystemConfOp
							.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP) != 1) {
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);

					// // 一下代码用于培训班分配给部门
					// List<Integer> depparentidList = new ArrayList<Integer>();
					// depparentidList = shoppingDao.getdempParentid(elUser
					// .getDepartment().getId());// 得到该用户所有上级部门id
					//
					// List<Integer> depclassidList = new ArrayList<Integer>();
					// for (Integer pid : depparentidList) {
					// depclassidList = shoppingDao
					// .getdepartmenttoclassbydepid(pid);//
					// 循环所有父部门ID并找出该部门被分配的培训班集合
					// for (Integer elclassid : depclassidList) {//
					// 循环该集合进行培训班的绑定和分配
					// classDao.assign2userAdd3(elUser.getId(), elclassid,
					// ClassConstants.CLASS_SQFS_FP);
					//
					// // 分配考场
					// examroom_classassignwcInit(elclassid, elUser
					// .getId());
					// }
					// }
					//
					// if (isFromAdmin != null && isFromAdmin.equals("1")) {
					// return "user_admin_login_success";
					// }

					if (elUser.getRole().getId() == 1 || elUser.getRole().getId() == 2) {
						logger.info(SystemConfOp
								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX_SD));
						return "study";
					} else {
						int loginCount = userDao.loginCount();
						logger.info(SystemConfOp
								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX_SD));
						if(SystemConfOp
								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX_SD)!=0){
						if(loginCount>(SystemConfOp
								.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX_SD))){
							this.setElmessage("当前登录人数已达到规定最大数，请稍后重试或联系管理员");
							return "error";
						}
						}
						return "sd_user_center_login_success";
					}
				}
			} else {
				setElmessage("账号没开通，请与管理员联系！");
			}
		} else {
			// 检测用户名是否存在
			if (!userDao.checkUsername(elUser.getUsername().trim())
					&& !userDao.checkUsername(username.trim())) {
				setElmessage("用户名不存在");// 数据库中是18位，用15位登录，提示用户名不存在
			} else {
				setElmessage("用户名或密码有错");
			}
		}
		return "sd_user_center_login";
	}

	/**
	 * 个人中心
	 * 
	 * @return
	 * @throws ElException
	 */
	public String sd_user_center() throws ElException {

		module = module == null ? "sd_user_center_index.action" : module;
		Return = "studentman";
		workCourses = workCourseDao
				.listWorkCourse2(getPageNow(), getPageSize());
		des = new StringBuffer();
		for (int i = 0; i < workCourses.size(); i++) {
			des.append(
					i + 1 + "," + workCourses.get(i).getWork_anniu_name() + ":"
							+ workCourses.get(i).getDescription() + ";")
					.toString();
		}
		return "sd_user_center";
	}

	/**
	 * 个人中心iframe
	 * 
	 * @return
	 * @throws ElException
	 */
	public String sd_user_center_index() throws ElException {
		// menus = newVersionDao.getMenus(0,
		// NewVersionConstants.QITAIYEMIAN_FUNC_ID,
		// NewVersionConstants.GENRENZHONGXIN_ID,
		// getSessionIntValue(ElConstants.SESSION_ROLE));
		menus = AuthorityNewVersionUtil.getListElFuncByRoleid(String
				.valueOf(getSessionIntValue(ElConstants.SESSION_ROLE)));
		menus_three = new ArrayList<ElFunc>();
		List<ElFunc> child = null;
		for (int i = 0; i < menus.size(); i++) {
			menu = menus.get(i);
			if (menu != null && menu.getChild() != null) {
				child = menu.getChild();
				if (child != null) {
					menus_three.addAll(child);
				}
			}
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
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());

		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		String starttime = "2013/12/1 14:40:08";
		String endtime = "2021/12/5 14:40:08";

		ElClass cl = workCourseDao.getElclassInfo(SystemConfOp
				.getValue(ElConstants.SD_ELCLASS));
		if (cl != null && elUser != null && elUser.getGangwei() != null) {
			classid_sd = cl.getId();
			courseid_sd = workCourseDao.getCourseid(Integer.parseInt(elUser
					.getGangwei()));
			eroomid_sd = workCourseDao.getEroomid(courseid_sd, cl.getId());
			epid_sd = workCourseDao.getEpid(eroomid_sd);

		}
		// 判断用户是否参加培训班
		if (!userDao.isCheckElClass(elUser)) {
			userDao.insert_sc(elUser);
			userDao.insert_se(elUser, epid_sd, eroomid_sd);
			userDao.insert_sce(elUser, courseid_sd, starttime, endtime);
			userDao.insert_sr(elUser, eroomid_sd);

		}

		// 检测用户是否通过培训班
		studyClassDao.setMyPassclass(
				getSessionIntValue(ElConstants.SESSION_USERID), cl.getId());

		int status = studyClassDao.getStudyClassStatus(
				getSessionIntValue(ElConstants.SESSION_USERID), cl.getId());
		if (status == 2) {// 已达到考试条件（即培训班获得了证书）
			step = 3;
		}

		// 论坛信息
		forums = forumAdminDao.newVersionGetForums(getPageNow(), getPageSize());

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);

		}
		// int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1
		// : ntype.getId());
		// //新闻信息
		// newses = newsDao.listFabuNewses(6,ntypeTree,nid,getPageNow(),
		// getPageSize());
		// //知识
		// kledges =
		// knowledgeManageDao.listKledgeAll(department,KnowledgeManageConstants.STATUS_ALL,getPageNow(),getPageSize());
		// //短消息
		// newMessage = messageDao.listMessNewAll(
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// getPageNow(),getPageSize());
		// //未读
		// message_no = messageDao
		// .getMessNoCount(getSessionIntValue(ElConstants.SESSION_USERID));
		// // 已读
		// message_yes = messageDao
		// .getMessYesCount(getSessionIntValue(ElConstants.SESSION_USERID));
		// int newShowye = NewSystemConfOp
		// .getIntValue(ElConstants.SYSTEM_NEWINDEXCONFIG_NEWSHOUYE);
		// if (newShowye == 0) {// ELN系统
		// eroom_no =
		// studyQuizDao.getEroomNoCount(getSessionIntValue(ElConstants.SESSION_USERID));
		// eroom_all =
		// studyQuizDao.getEroomAllCount(getSessionIntValue(ElConstants.SESSION_USERID));
		// class_yes =
		// studyClassDao.getClassYesCount(getSessionIntValue(ElConstants.SESSION_USERID));
		// class_all =
		// studyClassDao.getClassAllCount(getSessionIntValue(ElConstants.SESSION_USERID));
		//			
		//			
		// myDaibanshuwu=scheduleGlobleDao.getMyDaibanshuwu(getSessionIntValue(ElConstants.SESSION_USERID),
		// IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU),
		// IndexSystemConfigOp
		// .getIntValue(ElConstants.SYSTEM_INDEXCONFIG_DAIBANSHIWU_LENGTH));
		//			
		//			
		// myallcourse = IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYALLCOURSES);
		// myexams = IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYEXAMS);
		// mytrainingcourses = IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYTRAININGCOURSES);
		// //我的考试
		// if(myexams){
		// myrooms =
		// studyQuizDao.study_index_listErsWithoutC(getSessionIntValue(ElConstants.SESSION_USERID),
		// IndexSystemConfigOp.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYEXAMS_LENGTH),true);
		// }
		// //我的培训班
		// if(mytrainingcourses){
		// myClasses =
		// studyClassDao.study_index_listMyStudyClass(getSessionIntValue(ElConstants.SESSION_USERID),
		// IndexSystemConfigOp.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYTRAININGCOURSES));
		// }
		// //我的全部课程
		// if(myallcourse){
		// //我的测评课程
		// boolean open_jtm =
		// JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
		// if(open_jtm){
		// elUser =
		// userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		//					
		// String cer = JTM.getJTM_cer(String.valueOf(elUser.getId()));
		// String JTM_URL =
		// JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_MY_CEPINGCOURSES_URL)+
		// "?userid="+elUser.getId()+
		// "&jobid="+elUser.getStaid()+
		// "&cer="+cer;
		//					
		// Content c = null;
		// String returnValue = "";
		// try {
		// c = Request.Get(JTM_URL).addHeader("Content-Type", "text/html;
		// charset=UTF-8").execute().returnContent();
		// System.out.println(c.asString());
		// returnValue = c.asString();
		//						
		// } catch (ClientProtocolException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//					
		// String[] arr = null;
		// if(returnValue!=null&&!returnValue.equals("")){
		// arr = returnValue.trim().split("\\|");
		// if(arr[0].equals("true")){
		// String[] courses = arr[1].split(",");
		// if(courses!=null&&courses.length>0){
		// //插入前，不能将userid对应的测评课程删除
		// //因为存在每次学习的开始时间，删除的话再插入，每次开始时间都从0开始。
		// for(int i=0;i<courses.length;i++){
		// //添加测评课程到课程分配表
		// studyCourseDao.insertCepingCourse(getSessionIntValue(ElConstants.SESSION_USERID),Integer.parseInt(courses[i]));
		// }
		// }
		// }
		// }
		// }
		// studyCourseList =
		// studyCourseDao.study_index_listMyAllCourse(getSessionIntValue(ElConstants.SESSION_USERID),
		// IndexSystemConfigOp.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYALLCOURSES_LENGTH));
		// }
		// } else if (newShowye == 1) {// 信息管理系统
		// gongzuojihua = IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA);
		// gongzuorizhi = IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI);
		// daibanshiwu = IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU);
		// gerenweishen = IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN);
		// //我的计划
		// myPlan =
		// scheduleGlobleDao.getMyPlan(getSessionIntValue(ElConstants.SESSION_USERID),
		// IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA),
		// IndexSystemConfigOp
		// .getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GONGZUOJIHUA_LENGTH));
		// //我的日志
		// myLog =
		// scheduleGlobleDao.getMyLog(getSessionIntValue(ElConstants.SESSION_USERID),
		// IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI),
		// IndexSystemConfigOp
		// .getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GONGZUORIZHI_LENGTH));
		// myDaibanshuwu=scheduleGlobleDao.getMyDaibanshuwu(getSessionIntValue(ElConstants.SESSION_USERID),
		// IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU),
		// IndexSystemConfigOp
		// .getIntValue(ElConstants.SYSTEM_INDEXCONFIG_DAIBANSHIWU_LENGTH));
		// myNoPass =
		// scheduleGlobleDao.getNoPass(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),
		// -1,-1,
		// IndexSystemConfigOp
		// .getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN),department);
		// }
		return "sd_user_center_index";
	}

	/**
	 * 
	 * 山东导航页
	 */
	public String sd_navigation() throws ElException {
		workCourses = workCourseDao
				.listWorkCourse2(getPageNow(), getPageSize());
		return "sd_navigation";
	}

	/**
	 * 
	 * ceshi
	 */
	public String three_menu() throws ElException {
		// workCourses=workCourseDao.listWorkCourse2(getPageNow(),getPageSize());
		funcName = getRequest().getParameter("funcname");
		System.out.println(getSessionIntValue(ElConstants.SESSION_ROLE));
		System.out.println(funcName);
		menus_three = AuthorityNewVersionUtil.getListElFuncByName(funcName,
				getSessionIntValue(ElConstants.SESSION_ROLE));
		// getRequest().setAttribute("funcName", funcName);
		return "three_menu";
	}

	public int getClassid_sd() {
		return classid_sd;
	}

	public void setClassid_sd(int classid_sd) {
		this.classid_sd = classid_sd;
	}

	public int getCourseid_sd() {
		return courseid_sd;
	}

	public void setCourseid_sd(int courseid_sd) {
		this.courseid_sd = courseid_sd;
	}

	public int getEroomid_sd() {
		return eroomid_sd;
	}

	public void setEroomid_sd(int eroomid_sd) {
		this.eroomid_sd = eroomid_sd;
	}

	public int getEpid_sd() {
		return epid_sd;
	}

	public void setEpid_sd(int epid_sd) {
		this.epid_sd = epid_sd;
	}

	public WorkCourseDao getWorkCourseDao() {
		return workCourseDao;
	}

	public void setWorkCourseDao(WorkCourseDao workCourseDao) {
		this.workCourseDao = workCourseDao;
	}

	public List<WorkCourse> getWorkCourses() {
		return workCourses;
	}

	public void setWorkCourses(List<WorkCourse> workCourses) {
		this.workCourses = workCourses;
	}

	public List<ElFunc> getMenus_three_cycz() {
		return menus_three_cycz;
	}

	public void setMenus_three_cycz(List<ElFunc> menus_three_cycz) {
		this.menus_three_cycz = menus_three_cycz;
	}

	public ElFunc getMenu_cycz() {
		return menu_cycz;
	}

	public void setMenu_cycz(ElFunc menu_cycz) {
		this.menu_cycz = menu_cycz;
	}

	public PeixunBatchDao getPeixunBatchDao() {
		return peixunBatchDao;
	}

	public void setPeixunBatchDao(PeixunBatchDao peixunBatchDao) {
		this.peixunBatchDao = peixunBatchDao;
	}

	public ClassificationAction getClassificationAction() {
		return classificationAction;
	}

	public void setClassificationAction(ClassificationAction classificationAction) {
		this.classificationAction = classificationAction;
	}

	public PeixunBatch getPeixunBatch() {
		return peixunBatch;
	}

	public void setPeixunBatch(PeixunBatch peixunBatch) {
		this.peixunBatch = peixunBatch;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public ClassificationDao getClassificationDao() {
		return classificationDao;
	}

	public void setClassificationDao(ClassificationDao classificationDao) {
		this.classificationDao = classificationDao;
	}
}
