package com.sopia.duman.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.jdom.Parent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jit.attr.JitAcComp;
import com.jit.attr.jitCertVerify;
import com.jit.exception.GACertCRLException;
import com.jit.exception.GACertParseException;
import com.jit.exception.GACertSignException;
import com.jit.exception.GACertTimeException;
import com.jit.exception.GAIOException;
import com.jit.exception.PKILDAPException;
import com.jit.exception.ParameterException;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.dao.impl.ElClTypeDaoImpl;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.AuthorityNewVersionUtil;
import com.sopia.common.AuthorityUtil;
import com.sopia.common.CheckCard;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.IndexSystemConfigOp;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.MD5;
import com.sopia.common.OnlineUtil;
import com.sopia.common.ScoreOperate;
import com.sopia.common.SendMsgUtil;
import com.sopia.common.SystemConf;
import com.sopia.common.SystemConfOp;
import com.sopia.common.UserExcelUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.office.ExcelOutPut;
import com.sopia.common.register.EP;
import com.sopia.common.register.MACID;
import com.sopia.common.register.Register;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.dao.impl.CourseTypeDaoImpl;
import com.sopia.courseman.dao.impl.EroomDaoImpl;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.MacDao;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.dao.StationDao;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.dao.impl.StationDaoImpl;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.BaseDatat;
//import com.sopia.duman.entities.Company;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.ElGroup;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.MyLogin;
import com.sopia.duman.entities.Station;
import com.sopia.duman.entities.YzCode;
import com.sopia.forumman.dao.ForumAdminDao;
import com.sopia.forumman.dao.impl.ForumAdminDaoImpl;
import com.sopia.forumman.entities.ForumBlock;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.frontman.dao.FrontDao;
import com.sopia.intelligentTutoringPoints.IntelligentLoginUtil;
import com.sopia.intelligentTutoringPoints.IntelligentTutoringPointsConstants;
import com.sopia.intelligentTutoringPoints.IntelligentTutoringPointsUtil;
import com.sopia.knowledgeman.dao.KnowledgeDao;
import com.sopia.knowledgeman.dao.impl.KnowledgeDaoImpl;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.lable.common.LableCommon;
import com.sopia.newsandmess.dao.MessageDao;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.newsandmess.dao.impl.NewsDaoImpl;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.openmeetings.OmDao;
import com.sopia.openmeetings.Rooms;
import com.sopia.peixunBatch.dao.PeixunBatchDao;
import com.sopia.peixunBatch.entities.PeixunBatch;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.dao.impl.ExamPaperDaoImpl;
import com.sopia.questionman.dao.impl.QuestionDaoImpl;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.security.dao.SecurityDao;
import com.sopia.security.entity.SecurityBindIp;
import com.sopia.shopping.dao.ShoppingDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.wordman.dao.WordDao;
import com.sopia.wordman.dao.impl.WordDaoImpl;
import com.sopia.wordman.entities.Word;
import com.sun.star.container.ElementExistException;

public class UserAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(UserAction.class);
	private DepartmentDao departmentDao;
	private List<Department> departments;
	private Department department;
	//private Department depTree;
	private ELUser elUser;
	private ELUser user;
	private String yzCode;
	private int sub_department;
	private List<ELUser> elUsers;
	private ElRole role;
	private List<ElRole> roles;
	private RoleDao roleDao;
	private ElFunc funcTree;
	private ElFunc func;
	private File st;
	private String stFileName;
	private String sfContentType;
	private List<ElGroup> groups;
	private ElGroup group;
	private List<ELUser> assignedUsers;
	int roleid;
	private BaseDatat baseDatat;
	private List<BaseDatat> baseDatatList;
	private String Return;
	private int allTypeid;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	private List<BaseDatat> luntanjibies;
	private boolean exprot;
	private MyLogin myLogin;
	private List<MyLogin> myLogins;
	private int upOk;
	private String colId;
	private String jingzhong;
	private String zhiwu;
	private String zhiji;
	private String gangwei;
	private String dishi;
	private List<BaseDataType> baseDataTypeList;
	private int registerstatus;
	private String httpsPath;
	private int yzCodeIsNo;
	private Department depUserableTree;
	private ElFunc userFuncTree;
	private SecurityDao securityDao;
	private String ipAddress;
	private boolean toAll;// 开通全部
	private int isFromRegister;// 是否是注册页面过来
	private Station station;
	private List<Station> stations;
	private StationDao stationDao;
	private String stajiegou;
	private int userRole;
	private YzCode yzcode;
	private String macAddr;
	private MacDao macDao;
	private PeixunBatchDao peixunBatchDao;
	private WordDao wordDao;
	private List<Word> words;
	private Word word;
	private int userid;
	private int BasedbDel_flag;//删除标示符
	//sd0109
	private BaseDataType bdt;
	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

	public WordDao getWordDao() {
		return wordDao;
	}

	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}

	public PeixunBatchDao getPeixunBatchDao() {
		return peixunBatchDao;
	}

	public void setPeixunBatchDao(PeixunBatchDao peixunBatchDao) {
		this.peixunBatchDao = peixunBatchDao;
	}

	public MacDao getMacDao() {
		return macDao;
	}

	public void setMacDao(MacDao macDao) {
		this.macDao = macDao;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public YzCode getYzcode() {
		return yzcode;
	}

	public void setYzcode(YzCode yzcode) {
		this.yzcode = yzcode;
	}

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public String getStajiegou() {
		return stajiegou;
	}

	public void setStajiegou(String stajiegou) {
		this.stajiegou = stajiegou;
	}

	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public int getIsFromRegister() {
		return isFromRegister;
	}

	public void setIsFromRegister(int isFromRegister) {
		this.isFromRegister = isFromRegister;
	}

	public boolean isToAll() {
		return toAll;
	}

	public void setToAll(boolean toAll) {
		this.toAll = toAll;
	}

	public ELUser getUser() {
		return user;
	}

	public void setUser(ELUser user) {
		this.user = user;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public SecurityDao getSecurityDao() {
		return securityDao;
	}

	public void setSecurityDao(SecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public ElFunc getUserFuncTree() {
		return userFuncTree;
	}

	public void setUserFuncTree(ElFunc userFuncTree) {
		this.userFuncTree = userFuncTree;
	}

	// 2012 9 21
	private ShoppingDao shoppingDao;
	private ClassDao classDao;
	private List<ExamPaper> examPapers;
	private EroomDao eroomDao;
	private StudyQuizDao studyQuizDao;
	private StuffDao stuffDao;
	private KnowledgeDao knowledgeDao;
	private IndexDataUtil indexDataUtil;
	private IndexDao indexDao;
	private CourseTypeDao ctypeDao;
	private ElClTypeDao elClTypeDao;

	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public StuffDao getStuffDao() {
		return stuffDao;
	}

	public void setStuffDao(StuffDao stuffDao) {
		this.stuffDao = stuffDao;
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

	public int getYzCodeIsNo() {
		return yzCodeIsNo;
	}

	public void setYzCodeIsNo(int yzCodeIsNo) {
		this.yzCodeIsNo = yzCodeIsNo;
	}

	public String getHttpsPath() {
		return httpsPath;
	}

	public void setHttpsPath(String httpsPath) {
		this.httpsPath = httpsPath;
	}

	public int getRegisterstatus() {
		return registerstatus;
	}

	public void setRegisterstatus(int registerstatus) {
		this.registerstatus = registerstatus;
	}

	public List<BaseDataType> getBaseDataTypeList() {
		return baseDataTypeList;
	}

	public void setBaseDataTypeList(List<BaseDataType> baseDataTypeList) {
		this.baseDataTypeList = baseDataTypeList;
	}

	public String getColId() {
		return colId;
	}

	public void setColId(String colId) {
		this.colId = colId;
	}

	public String getJingzhong() {
		return jingzhong;
	}

	public void setJingzhong(String jingzhong) {
		this.jingzhong = jingzhong;
	}

	public String getZhiwu() {
		return zhiwu;
	}

	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}

	public String getZhiji() {
		return zhiji;
	}

	public void setZhiji(String zhiji) {
		this.zhiji = zhiji;
	}

	public String getGangwei() {
		return gangwei;
	}

	public void setGangwei(String gangwei) {
		this.gangwei = gangwei;
	}

	public String getDishi() {
		return dishi;
	}

	public void setDishi(String dishi) {
		this.dishi = dishi;
	}

	public int getUpOk() {
		return upOk;
	}

	public void setUpOk(int upOk) {
		this.upOk = upOk;
	}

	public List<MyLogin> getMyLogins() {
		return myLogins;
	}

	public void setMyLogins(List<MyLogin> myLogins) {
		this.myLogins = myLogins;
	}

	public MyLogin getMyLogin() {
		return myLogin;
	}

	public void setMyLogin(MyLogin myLogin) {
		this.myLogin = myLogin;
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

	public List<BaseDatat> getLuntanjibies() {
		return luntanjibies;
	}

	public void setLuntanjibies(List<BaseDatat> luntanjibies) {
		this.luntanjibies = luntanjibies;
	}

	public List<BaseDatat> getDishis() {
		return dishis;
	}

	public void setDishis(List<BaseDatat> dishis) {
		this.dishis = dishis;
	}

	public int getAllTypeid() {
		return allTypeid;
	}

	public void setAllTypeid(int allTypeid) {
		this.allTypeid = allTypeid;
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

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public List<ElGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<ElGroup> groups) {
		this.groups = groups;
	}

	public ElFunc getFuncTree() {
		return funcTree;
	}

	public void setFuncTree(ElFunc funcTree) {
		this.funcTree = funcTree;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
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

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
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

	public Department getDepUserableTree() {
		return depUserableTree;
	}

	public void setDepUserableTree(Department depUserableTree) {
		this.depUserableTree = depUserableTree;
	}
	
	public ElFunc getFunc() {
		return func;
	}

	public void setFunc(ElFunc func) {
		this.func = func;
	}

	public String getYzCode() {
		return yzCode;
	}

	public void setYzCode(String yzCode) {
		this.yzCode = yzCode;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	// public Department getDepTree() {
	// return depTree;
	// }
	//
	// public void setDepTree(Department depTree) {
	// this.depTree = depTree;
	// }

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

	public String getSfContentType() {
		return sfContentType;
	}

	public void setSfContentType(String sfContentType) {
		this.sfContentType = sfContentType;
	}

	public ElGroup getGroup() {
		return group;
	}

	public void setGroup(ElGroup group) {
		this.group = group;
	}

	public List<ELUser> getAssignedUsers() {
		return assignedUsers;
	}

	public void setAssignedUsers(List<ELUser> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	public FrontDao getFrontDao() {
		return frontDao;
	}

	public void setFrontDao(FrontDao frontDao) {
		this.frontDao = frontDao;
	}

	public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public boolean isExprot() {
		return exprot;
	}

	public void setExprot(boolean exprot) {
		this.exprot = exprot;
	}

	public Department getDeprTree() {
		return deprTree;
	}

	public void setDeprTree(Department deprTree) {
		this.deprTree = deprTree;
	}

	public KnowledgeDao getKnowledgeDao() {
		return knowledgeDao;
	}

	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	public IndexDao getIndexDao() {
		return indexDao;
	}

	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}

	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}

	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}

	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}

	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}

	public StationDao getStationDao() {
		return stationDao;
	}

	public void setStationDao(StationDao stationDao) {
		this.stationDao = stationDao;
	}

	public String systemInit() throws ElException {
		ELUser elUser = new ELUser();
		elUser.setUsername("admin");
		elUser.setPassword(MD5.crypt("1"));
		elUser.setRole(new ElRole(1));
		elUser.setRealname("超级管理员");
		elUser.setDepartment(new Department(0));
		// userDao.addUser(elUser) ;
		setElmessage("初始化成功！");
		return "null";
	}

	private MessageDao messageDao;

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	private String checkbox;
	
	public String checkIdcardnoIsExist() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		boolean b = userDao.checkShenfenzhengIsExsit(elUser.getShenfenzheng());
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsonsBoolean\":" + String.valueOf(b) + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public String checkmyNameIsExist() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		boolean b = userDao.checkUsername(elUser.getUsername());
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			String d = "{\"jsonsBoolean\":" + String.valueOf(b) + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String registerInitpki() throws ElException {
		department = departmentDao.getDepByBH(elUser.getDanwei());
		if (department == null) {
			department = new Department();
			department.setName("该pki的单位在数据库中找不到!");
		}
		// jingzhongs=userDao.getBaseDatatByTypeid(1);
		// zhiwus=userDao.getBaseDatatByTypeid(2);
		// zhijis=userDao.getBaseDatatByTypeid(3);
		// gangweis=userDao.getBaseDatatByTypeid(4);
		// dishis=userDao.getBaseDatatByTypeid(5);
		jingzhongs = userDao.getBaseDatatByTypeidc(1);
		zhiwus = userDao.getBaseDatatByTypeidc(2);
		zhijis = userDao.getBaseDatatByTypeidc(3);
		gangweis = userDao.getBaseDatatByTypeidc(4);
		dishis = userDao.getBaseDatatByTypeidc(5);
		return "register3";
		// return "register";
	}

	public String loginpki() throws ElException {
		try {
			// 从request 中获取客户端证书
			X509Certificate[] certs = (X509Certificate[]) getRequest()
					.getAttribute("javax.servlet.request.X509Certificate");
			if (certs == null) {
				certs = (X509Certificate[]) getRequest().getAttribute(
						"javax.net.ssl.peer_certificates");
			}
			if (certs == null) {
				// 没有读到证书提示
				// setElmessage("没有证书输入，请输入证书(PKI)");
				// elUser= new ELUser();
				// elUser.setDanwei("4400000000");
				// elUser.setRealname("4400000000");
				// elUser.setShenfenzheng("0003203203023003002");
				// elUser.setUsername("0003203203023003002");
				// elUser.setPassword("0003203203023003002");
				// elUser = new ELUser();
				// elUser.setRealname("小何");
				// elUser.setDanwei("440000200000");
				// elUser.setShenfenzheng("360430198605093317");
				// elUser.setUsername("360430198605093317");
				// elUser.setPassword("360430198605093317");
				// return this.registerInitpki();
				// return "registerInitpki";
				// return "pkiregister";
				setElmessage("没有获取到pki信息，请检测pki");
				return "error";
				// elUser = new ELUser();
				// elUser.setRealname("小何");
				// elUser.setDanwei("440000200000");
				// elUser.setShenfenzheng("360430198605093313");
				// elUser.setUsername("360430198605093313");
				// elUser.setPassword("360430198605093313");
				// return this.registerInitpki();
			} else {
				// 读到证书
				X509Certificate gaX509Cert = null;
				gaX509Cert = certs[0];
				try { // 内部测试PKI 先注释掉
					X509Certificate cert = certs[0];
					jitCertVerify ver = new jitCertVerify();
					// ver.setBaseDN("st=44,c=cn");
					ver.setParameter("10.40.28.102,10.40.28.118", "390,389");// 单ip、port情况
					// ver.setParameter("172.16.8.147,127.0.0.1","389,389");多ip、port情况
					ver.verify(cert, false, false);
					// setElmessage("pki 没问题呢！");
				} catch (PKILDAPException e) {
					setElmessage("错误：无法连接PKI目录服务器" + e.getMessage());
					return "error";
				} catch (ParameterException e) {
					setElmessage("错误:方法参数错误，参数为''或null" + e.getMessage());
					return "error";
				} catch (GACertParseException e) {
					setElmessage("错误:公安证书解析异常" + e.getMessage());
					return "error";
				} catch (GAIOException e) {
					setElmessage("错误:读取本地文件异常" + e.getMessage());
					return "error";
				} catch (GACertTimeException e) {
					setElmessage("错误:证书过期" + e.getMessage());
					return "error";
				} catch (GACertSignException e) {
					setElmessage("错误:证书签名无效" + e.getMessage());
					return "error";
				} catch (GACertCRLException e) {
					setElmessage("错误:证书被注销" + e.getMessage());
					return "error";
				}
				// 获取序列号
				X500Principal principal = gaX509Cert.getSubjectX500Principal();
				String name = principal.getName("RFC1779");
				String username = "";
				String realname = "";
				String danwei = "";
				// CN=9900020 000000000000000002, OU=00, OU=05, O=22, L=00,L=00,
				// ST=44, C=CN
				// 获取证书中包含的账号信息，姓名和身份证
				if (name != null) {
					try {
						name = name.substring(name.indexOf("CN="));
						realname = name.split(",")[0].split("=")[1].split(" ")[0]
								.trim();
						username = name.split(",")[0].split("=")[1].split(" ")[1]
								.trim();
						danwei = name.split(",")[6].split("=")[1].trim()
								+ name.split(",")[5].split("=")[1].trim()
								+ name.split(",")[4].split("=")[1].trim()
								+ name.split(",")[3].split("=")[1].trim()
								+ name.split(",")[2].split("=")[1].trim()
								+ name.split(",")[1].split("=")[1].trim();
					} catch (Exception e) {
						logger.error("解析pki相关信息失败,PKI-RFC1779:" + name, e);
						setElmessage("解析pki相关信息失败，请联系管理员!");
						return "error";
					}
				} else {
					setElmessage("在pki中未找到相关信息，请联系管理员!");
					return "error";
				}
				// 证书中包含的机构代码（单位编码）

				// 检测账号是否在本系统数据库中
				// 如果是15位身份证 ，转成18位
				// username=CheckCard.fixPersonIDCode(username);
				// if(username!=null&&!"".equals(username)){
				// username=username.toLowerCase();
				// }
				String username1 = "";
				if (username.length() == 15) { // 如果身份证号码等于15位的话
					username1 = CheckCard.fixPersonIDCode(username)
							.toLowerCase();
				} else if (username.length() == 18) {
					username1 = CheckCard.fixPersonIDCode15(username)
							.toLowerCase();
					;
				} else
					username1 = username;
				if (!userDao.checkUsername(username)
						&& !userDao.checkUsername(username1)) {
					// 没有。注册处理
					elUser = new ELUser();
					elUser.setRealname(realname);
					elUser.setDanwei(danwei);
					elUser.setShenfenzheng(username);
					elUser.setUsername(username);
					// 密码
					elUser.setPassword(username);
					// 获取单位名称
					// setElmessage("证书信息没记录在本系统中.");
					// return "pkiregister";
					return this.registerInitpki();
				} else {
					// 有，直接设置session，登陆
					// -----从ca服务器中获取权限列表，对应于本系统的角色权限。
					// JitAcComp jitaccomp = new JitAcComp();

					// -----从ca服务器中获取权限列表，对应于本系统的角色权限。

					elUser = userDao.query(username);
					if (elUser.getId() == 0) {
						elUser = userDao.query(username1);
					}
					if (OnlineUtil.checkUser(elUser.getId() + "")) {
						// 查出上次该用户的最后登录信息
						if (myLogin == null) {
							myLogin = new MyLogin();
						}
						String tempIpAddr = myLogin.getIpAddr();
						myLogin = userDao.getSessionUserLoginInfo(elUser
								.getId());
						getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
						getRequest().setAttribute("ispki", "yes");
						return "login_logout";
					}
					if (elUser.getValid()) {
						OnlineUtil.addOnlineUser(elUser.getId() + "",
								getSession());
						getSession().setAttribute(ElConstants.SESSION_USERID,
								elUser.getId());
						getSession().setAttribute(ElConstants.SESSION_USERNAME,
								elUser.getUsername());
						getSession().setAttribute(ElConstants.SESSION_REALNAME,
								elUser.getRealname());
						getSession().setAttribute(ElConstants.SESSION_ROLE,
								elUser.getRole().getId());
						getSession().setAttribute(ElConstants.SESSION_ROLENAME,
								elUser.getRole().getName());
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPARTMENT,
								elUser.getDepartment().getId());
						getSession().setAttribute(ElConstants.SESSION_MYSCORE,
								0);
						getSession().setAttribute(ElConstants.SESSION_STATION,
								0);
						getSession().setAttribute(ElConstants.SESSION_AGE,
								elUser.getAge());
						// 登录后 加显示 姓名 身份证 部门
						elUser = userDao.getUserById(elUser.getId());
						getSession().setAttribute(
								ElConstants.SESSION_SHENFENZHENG,
								elUser.getShenfenzheng());
						getSession().setAttribute(
								ElConstants.SESSION_MYDEPNAME,
								elUser.getDepartment().getName());

						// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
						getSession().setAttribute("isLogin", "true");
						// 记录用户登入信息-------
						if (myLogin == null) {
							myLogin = new MyLogin();
						}
						myLogin.setElUser(elUser);
						userDao.addUserLoginInfo(myLogin);
						// if (elUser.getRole().getId() != 1
						// && elUser.getRole().getId() != 2)
						// return "login_study";
						// else {
						// return "login_success";
						// }
						return "login_study";
					} else {
						setElmessage("账号还未开通，请与管理员联系！");
					}
				}
			}
		} catch (Exception e) {
			logger.error("pki登陆错误", e);
			return "error";
		}
		return "error";
	}

	public String pkiregister() throws ElException {
		Department dep = departmentDao.getDepByBH(elUser.getDanwei());
		if (dep.getId() <= 0) {
			// setElmessage("请确定您单位编号是否正确。如果无误，请与管理员联系");
			dep = new Department(1);
		}
		// 注册用户
		elUser.setDepartment(dep);
		elUser.setRole(new ElRole(4));
		elUser.setValid(true);
		elUser.setPassword(MD5.crypt(elUser.getPassword()));
		userDao.insert(elUser);
		elUser = userDao.query(elUser.getUsername());
		// if (elUser.getValid()) {
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
		// }
		getSession().setAttribute(ElConstants.SESSION_USERID, elUser.getId());
		getSession().setAttribute(ElConstants.SESSION_USERNAME,
				elUser.getUsername());
		getSession().setAttribute(ElConstants.SESSION_REALNAME,
				elUser.getRealname());
		getSession().setAttribute(ElConstants.SESSION_ROLE,
				elUser.getRole().getId());
		getSession().setAttribute(ElConstants.SESSION_ROLENAME,
				elUser.getRole().getName());
		getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,
				elUser.getDepartment().getId());
		getSession().setAttribute(ElConstants.SESSION_MYSCORE, 0);
		getSession().setAttribute(ElConstants.SESSION_STATION, 0);
		getSession().setAttribute(ElConstants.SESSION_AGE, elUser.getAge());
		//
		// if (elUser.getRole().getId() != 1 && elUser.getRole().getId() != 2)
		// return "login_study";
		// else {
		// return "login_success";
		// }
		// setElmessage("系统错误，请重试！");
		// return "error";
		return "login";
	}

	// public String login() throws ElException {
	// // if (getSession().getAttribute("yzCodey")!=null&&((String)
	// // getSession().getAttribute("yzCodey")).equals(yzCode)) {
	//
	// if(elUser==null){
	// setElmessage("请从正常入口进入！");
	// return "error";
	// }
	// if (userDao.check(elUser.getUsername(), MD5.crypt(elUser.getPassword())))
	// {
	// userDao.getFlowUser();
	//			
	// // Cookie cookie = new Cookie("my_application_cookie_name",
	// // elUser
	// // .getUsername());
	// // cookie.setPath("/");
	// // cookie.setMaxAge(-1);
	// // getResponse().addCookie(cookie);
	// getApplication().put("", "");
	// getSession().removeAttribute("yzCodey");
	// elUser = userDao.query(elUser.getUsername());
	// /*
	// * if ("checkbox".equals(checkbox)) { Cookie cookie = new
	// * Cookie("elearning.cookie.username", elUser .getUsername());
	// * cookie.setPath("/"); cookie.setMaxAge(365 * 24 * 60 * 60);
	// * getResponse().addCookie(cookie); }
	// */
	// /*
	// * if (elUser.getStation() == 0) { elUser =
	// * userDao.getUserById(elUser.getId()); return "myinfo_complete"; }
	// */
	// if(OnlineUtil.checkUser(elUser.getId()+""))
	// {
	// //查出上次该用户的最后登录信息
	// if(myLogin==null){
	// myLogin=new MyLogin();
	// }
	// String tempIpAddr=myLogin.getIpAddr();
	// myLogin=userDao.getSessionUserLoginInfo(elUser.getId());
	// getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
	// //返回系统设置的参数是否可注册
	// //registerstatus =
	// SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
	// return "login_logout";
	// }
	// /*if(OnlineUtil.DifferentUser((elUser.getRole().getId()+""))){
	// setElmessage("已经有和您相同角色的用户在其他地方登陆，您不能再登陆了！");
	// return "login";
	// }*/
	// if (elUser.getValid()) {
	// OnlineUtil.addOnlineUser(elUser.getId() + "", getSession());
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
	// //如果是超管，部门id为根
	// if(elUser.getRole().getId()==1){
	// getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,1);
	// }else{
	// getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,
	// elUser.getDepartment().getId());
	// }
	// // getSession().setAttribute(ElConstants.SESSION_MYMESSAGE,
	// // messageDao.getNewMesscount(elUser.getId()));
	// getSession().setAttribute(ElConstants.SESSION_MYSCORE, 0);
	// getSession().setAttribute(ElConstants.SESSION_STATION, 0);
	// getSession().setAttribute(ElConstants.SESSION_AGE,elUser.getAge());
	// //登录后 加显示 姓名 身份证 部门
	// elUser=userDao.getUserById(elUser.getId());
	// getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG,
	// elUser.getShenfenzheng());
	// getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,elUser.getDepartment().getName());
	// // jforum 整合
	// // Cookie cookie = new Cookie("jforumUserInfo", elUser
	// // .getUsername());
	// // cookie.setMaxAge(-1);
	// // cookie.setPath("/");// cookie只在同一应用服务器有效
	// // getResponse().addCookie(cookie);
	// ScoreOperate.setScore(
	// getSessionIntValue(ElConstants.SESSION_USERID),
	// ElConstants.DIAN_LOGIN_DO);
	// //存个值到session中 用来判断用户是刚刚登入，然后提示短消息
	// getSession().setAttribute("isLogin", "true");
	// //记录用户登入信息-------
	// if(myLogin==null){
	// myLogin=new MyLogin();
	// }
	// //判断是否需要记录ip
	// if(SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP)!=1){
	// myLogin.setIpAddr(null);
	// }
	// myLogin.setElUser(elUser);
	// userDao.addUserLoginInfo(myLogin);
	// if (elUser.getRole().getId() != 1
	// && elUser.getRole().getId() != 2)
	// return "login_study";
	// else {
	// return "login_success";
	// }
	// } else {
	// setElmessage("账号没开通，请与管理员联系！");
	// }
	//
	// } else {
	// setElmessage("用户名或密码有错！");
	// }
	//		
	// // } else { setElmessage("验证码错误！"); }
	//		 
	// //返回系统设置的参数是否可注册
	// registerstatus =
	// SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
	// //https路径
	// httpsPath=SystemConfOp.getHttpsPath(getRequest().getServerName(),
	// getRequest().getContextPath());
	// return "login";
	// }

	public String login() throws ElException {
		
		if(isFromRegister != 1){// 不是注册成功后的登录
			if(yzCodeIsNo != 1){// 不是从前台首页调用的action
				yzCodeIsNo = SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_YZCODE_OPEN);
				if(yzCodeIsNo == 1){
					if(yzCode == null || yzCode.equals("")){
						this.setElmessage("验证码不能为空,请填写验证码!!!");
						return "login";
					}else {
						if ((getSession().getAttribute("yzCodey")!=null&&!((String)
								 getSession().getAttribute("yzCodey")).equals(yzCode))) {
							this.setElmessage("验证码错误,请填写验证码!!!");
							return "login";
						}
					}
				}
			}
		}
//		在线用户限制
		int loginMax = SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_MAX).equals("") || SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_MAX).equals("无记录")  ? 0 :SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX);
		if(loginMax != 0){
			if(loginMax <= userDao.getTheCurrentOnlineUsersSize()){ 
				setElmessage2("当前在线用户数已超过管理员设定的最大值，请稍后再登陆或与管理员联系！"); 
				return "login";
			}
		}
		if(elUser==null){
			setElmessage("请从正常入口进入！");
			return "error";
		}
		// 判断是否填写用户名
		if(elUser.getUsername()==null||"".equals(elUser.getUsername().trim())){
			setElmessage("请填写用户名！");
			return "login";
		}
		user = userDao.query(elUser.getUsername());
		if(!SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("0")){
			if(user.getId() != 0 && !SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("无记录") && 
					userDao.checkLogonFailureNumber(user.getId(), SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX))){ 
				setElmessage("该账号已被锁定，请半小时后再尝试登陆！"); 
				return "error";
			}
		}
		
		// 将用户名转成小写（库中存储着小写字符）
		elUser.setUsername(elUser.getUsername().trim().toLowerCase());
		String username="";
		if (elUser.getUsername().length() == 15) { 
			// 用户名15位转换成18位
			username=CheckCard.fixPersonIDCode(elUser.getUsername()).toLowerCase();
		} else if(elUser.getUsername().trim().length() == 18){
			// 用户名18位的话转成15位
			username=CheckCard.fixPersonIDCode15(elUser.getUsername()).toLowerCase();;
		}else{
			username = elUser.getUsername();
		}
		// 检测15位和18位和密码是否匹配
		if (userDao.check(elUser.getUsername(), MD5.crypt(elUser.getPassword()))||userDao.check(username, MD5.crypt(elUser.getPassword()))) {
			// 校验通过
			getSession().removeAttribute("yzCodey");
			// 获取用户信息
			elUser = userDao.query(elUser.getUsername().trim());
			if(elUser.getId()==0)// 登录账号不符合的时候查询转换后的账号
				elUser =userDao.query(username);
			// 检测是否已经在线
			if(OnlineUtil.checkUser(elUser.getId()+"")){
				// 查出上次该用户的最后登录信息
				if(myLogin==null){
					myLogin=new MyLogin();
				}
				String tempIpAddr=myLogin.getIpAddr();
				myLogin=userDao.getSessionUserLoginInfo(elUser.getId());
				getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
				return "login_logout";
			}  
			/*
			 * if(OnlineUtil.DifferentUser((elUser.getRole().getId()+""))){
			 * setElmessage("已经有和您相同角色的用户在其他地方登陆，您不能再登陆了！"); return "login"; }
			 */
			if (elUser.getValid()) {
				// 如果是超级管理员
				if(elUser.getRole().getId()==1){
					// 检测超级管理员的IP（为了安全，防止超级管理员账号在其他设置之外的ip登录）
//					SecurityBindIp securityBindIp = securityDao.getSecurityBindIpByRoleid(elUser.getRole().getId());
//					if(ipAddress != null && !ipAddress.equals("")){
//						String[] myIpArray = ipAddress.split("\\.");
//						String[] ipStratArray = null;
//						String[] ipEndArray = null;
//						String[] ip_startArray_per = null;
//						String[] ip_endArray_per = null;
//						boolean flag = true;
//						if(securityBindIp != null){
//							if(securityBindIp.getIp_start() != null && !securityBindIp.getIp_start().equals("")){
//								if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_ALLOWMULTIPLESIGN)){// 禁用多点登录
//									OnlineUtil.addOnlineUser(elUser.getId() + "", getSession());
//								}
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
								if(elUser.getRole().getId()==1){
									getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,1);
								}else{
									getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,
										elUser.getDepartment().getId());
								}
								getSession().setAttribute(ElConstants.SESSION_AGE,elUser.getAge());
								// 登录后 加显示 姓名 身份证 部门
								elUser=userDao.getUserById(elUser.getId());
				
				
								getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG, elUser.getShenfenzheng()); 
								
								getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,elUser.getDepartment().getName());
								// jforum 整合
								// Cookie cookie = new Cookie("jforumUserInfo", elUser
								// .getUsername());
								// cookie.setMaxAge(-1);
								// cookie.setPath("/");// cookie只在同一应用服务器有效
								// getResponse().addCookie(cookie);
								ScoreOperate.setScore(
										getSessionIntValue(ElConstants.SESSION_USERID),
										ElConstants.DIAN_LOGIN_DO);
								// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
								getSession().setAttribute("isLogin", "true");
								// 记录用户登入信息-------
								if(myLogin==null){
									myLogin=new MyLogin();
								}
								// 判断是否需要记录ip
								if(SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP)!=1){
									myLogin.setIpAddr(null);
								}
								myLogin.setElUser(elUser);
								userDao.addUserLoginInfo(myLogin);
								// 一下代码用于培训班分配给部门
								List<Integer>   depparentidList= new ArrayList<Integer>();
								depparentidList = shoppingDao.getdempParentid(elUser.getDepartment().getId());// 得到该用户所有上级部门id
								
								List<Integer>   depclassidList= new ArrayList<Integer>();
								for (Integer pid : depparentidList) {
									depclassidList = shoppingDao.getdepartmenttoclassbydepid(pid);// 循环所有父部门ID并找出该部门被分配的培训班集合
									for (Integer elclassid : depclassidList) {// 循环该集合进行培训班的绑定和分配
										classDao.assign2userAdd3(elUser.getId(),elclassid,ClassConstants.CLASS_SQFS_FP);
				
										 // 分配考场
										 examroom_classassignwcInit(elclassid,elUser.getId());
									}
								}
								return "login_study";
//							}
//						}
//					}
				}else{
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
					if(elUser.getRole().getId()==1){
						getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,1);
					}else{
						getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,
							elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser=userDao.getUserById(elUser.getId());
	
	
					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG, elUser.getShenfenzheng()); 
					
					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,elUser.getDepartment().getName());
					// jforum 整合
					// Cookie cookie = new Cookie("jforumUserInfo", elUser
					// .getUsername());
					// cookie.setMaxAge(-1);
					// cookie.setPath("/");// cookie只在同一应用服务器有效
					// getResponse().addCookie(cookie);
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if(myLogin==null){
						myLogin=new MyLogin();
					}
					// 判断是否需要记录ip
					if(SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP)!=1){
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);
					// 一下代码用于培训班分配给部门
					List<Integer>   depparentidList= new ArrayList<Integer>();
					depparentidList = shoppingDao.getdempParentid(elUser.getDepartment().getId());// 得到该用户所有上级部门id
					
					List<Integer>   depclassidList= new ArrayList<Integer>();
					for (Integer pid : depparentidList) {
						depclassidList = shoppingDao.getdepartmenttoclassbydepid(pid);// 循环所有父部门ID并找出该部门被分配的培训班集合
						for (Integer elclassid : depclassidList) {// 循环该集合进行培训班的绑定和分配
							classDao.assign2userAdd3(elUser.getId(),elclassid,ClassConstants.CLASS_SQFS_FP);
	
							 // 分配考场
							 examroom_classassignwcInit(elclassid,elUser.getId());
						}
					}
					return "login_study";
				}
			} else {
				setElmessage("账号没开通，请与管理员联系！");
			}
		} else {
			// 检测用户名是否存在
			if(!userDao.checkUsername(elUser.getUsername().trim())&&!userDao.checkUsername(username.trim())){
				setElmessage("用户名不存在");// 数据库中是18位，用15位登录，提示用户名不存在
			}else{
				elUser = userDao.query(elUser.getUsername()); 
				if(elUser.getId() != 0){
					myLogin = new MyLogin();
					myLogin.setElUser(elUser);
					myLogin.setLogintime(new Timestamp(System.currentTimeMillis()));
					userDao.insertLoingFailure(myLogin);
				}
				setElmessage("用户名或密码有错");	
			}
		}
		 
		// 返回系统设置的参数是否可注册
		registerstatus = SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
		// https路径
		httpsPath=SystemConfOp.getHttpsPath(getRequest().getServerName(), getRequest().getContextPath());
		
		
		return "login";
	}
	
	public String wjmlogin() throws ElException {
		if(isFromRegister != 1){// 不是注册成功后的登录
			if(yzCodeIsNo != 1){// 不是从前台首页调用的action
				yzCodeIsNo = SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_YZCODE_OPEN);
				if(yzCodeIsNo == 1){
					if(yzCode == null || yzCode.equals("")){
						this.setElmessage("验证码不能为空,请填写验证码!!!");
						return "wjmlogin";
					}else {
						if ((getSession().getAttribute("yzCodey")!=null&&!((String)
								 getSession().getAttribute("yzCodey")).equals(yzCode))) {
							this.setElmessage("验证码错误,请填写验证码!!!");
							return "wjmlogin";
						}
					}
				}
			}
		}
//		在线用户限制
		int loginMax = SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_MAX).equals("") || SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_MAX).equals("无记录")  ? 0 :SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX);
		if(loginMax != 0){
			if(loginMax <= userDao.getTheCurrentOnlineUsersSize()){ 
				setElmessage2("当前在线用户数已超过管理员设定的最大值，请稍后再登陆或与管理员联系！"); 
				return "wjmlogin";
			}
		}
		if(elUser==null){
			setElmessage("请从正常入口进入！");
			return "error";
		}
		// 判断是否填写用户名
		if(elUser.getUsername()==null||"".equals(elUser.getUsername().trim())){
			setElmessage("请填写用户名！");
			return "wjmlogin";
		}
		user = userDao.query(elUser.getUsername());
		if(!SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("0")){
			if(user.getId() != 0 && !SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("无记录") && 
					userDao.checkLogonFailureNumber(user.getId(), SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX))){ 
				setElmessage("该账号已被锁定，请半小时后再尝试登陆！"); 
				return "error";
			}
		}
		
		// 将用户名转成小写（库中存储着小写字符）
		elUser.setUsername(elUser.getUsername().trim().toLowerCase());
		String username="";
		if (elUser.getUsername().length() == 15) { 
			// 用户名15位转换成18位
			username=CheckCard.fixPersonIDCode(elUser.getUsername()).toLowerCase();
		} else if(elUser.getUsername().trim().length() == 18){
			// 用户名18位的话转成15位
			username=CheckCard.fixPersonIDCode15(elUser.getUsername()).toLowerCase();;
		}else{
			username = elUser.getUsername();
		}
		// 检测15位和18位和密码是否匹配
		if (userDao.check(elUser.getUsername(), MD5.crypt(elUser.getPassword()))||userDao.check(username, MD5.crypt(elUser.getPassword()))) {
			// 校验通过
			getSession().removeAttribute("yzCodey");
			// 获取用户信息
			elUser = userDao.query(elUser.getUsername().trim());
			
			//是否限定MAC
			if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM__MAC_NEED)&&elUser.getRole().getId()!=1){
				
				if(!macDao.isExistMac(macAddr.replace(":", "-"))){
					setElmessage("请使用电教室中规定的电脑进行登陆");
					return "error";
				}
			}
			if(elUser.getId()==0)// 登录账号不符合的时候查询转换后的账号
				elUser =userDao.query(username);
			// 检测是否已经在线
			if(OnlineUtil.checkUser(elUser.getId()+"")){
				// 查出上次该用户的最后登录信息
				if(myLogin==null){
					myLogin=new MyLogin();
				}
				String tempIpAddr=myLogin.getIpAddr();
				myLogin=userDao.getSessionUserLoginInfo(elUser.getId());
				getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
				return "login_logout";
			}  
			if (elUser.getValid()) {
				// 如果是超级管理员
				if(elUser.getRole().getId()==1){
					// 检测超级管理员的IP（为了安全，防止超级管理员账号在其他设置之外的ip登录）
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
								if(elUser.getRole().getId()==1){
									getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,1);
								}else{
									getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,
										elUser.getDepartment().getId());
								}
								getSession().setAttribute(ElConstants.SESSION_AGE,elUser.getAge());
								// 登录后 加显示 姓名 身份证 部门
								elUser=userDao.getUserById(elUser.getId());
				
				
								getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG, elUser.getShenfenzheng()); 
								
								getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,elUser.getDepartment().getName());
								ScoreOperate.setScore(
										getSessionIntValue(ElConstants.SESSION_USERID),
										ElConstants.DIAN_LOGIN_DO);
								// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
								getSession().setAttribute("isLogin", "true");
								// 记录用户登入信息-------
								if(myLogin==null){
									myLogin=new MyLogin();
								}
								// 判断是否需要记录ip
								if(SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP)!=1){
									myLogin.setIpAddr(null);
								}
								myLogin.setElUser(elUser);
								userDao.addUserLoginInfo(myLogin);
								
								// 一下代码用于培训班分配给部门
								List<Integer>   depparentidList= new ArrayList<Integer>();
								depparentidList = shoppingDao.getdempParentid(elUser.getDepartment().getId());// 得到该用户所有上级部门id
								
								List<Integer>   depclassidList= new ArrayList<Integer>();
								for (Integer pid : depparentidList) {
									depclassidList = shoppingDao.getdepartmenttoclassbydepid(pid);// 循环所有父部门ID并找出该部门被分配的培训班集合
									for (Integer elclassid : depclassidList) {// 循环该集合进行培训班的绑定和分配
										classDao.assign2userAdd3(elUser.getId(),elclassid,ClassConstants.CLASS_SQFS_FP);
				
										 // 分配考场
										 examroom_classassignwcInit(elclassid,elUser.getId());
									}
								}
								return "login_study";
				}else{
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
					if(elUser.getRole().getId()==1){
						getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,1);
					}else{
						getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,
							elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser=userDao.getUserById(elUser.getId());
	
	
					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG, elUser.getShenfenzheng()); 
					
					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,elUser.getDepartment().getName());
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if(myLogin==null){
						myLogin=new MyLogin();
					}
					// 判断是否需要记录ip
					if(SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP)!=1){
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);
					
					// 一下代码用于培训班分配给部门
					List<Integer>   depparentidList= new ArrayList<Integer>();
					depparentidList = shoppingDao.getdempParentid(elUser.getDepartment().getId());// 得到该用户所有上级部门id
					
					List<Integer>   depclassidList= new ArrayList<Integer>();
					for (Integer pid : depparentidList) {
						depclassidList = shoppingDao.getdepartmenttoclassbydepid(pid);// 循环所有父部门ID并找出该部门被分配的培训班集合
						for (Integer elclassid : depclassidList) {// 循环该集合进行培训班的绑定和分配
							classDao.assign2userAdd3(elUser.getId(),elclassid,ClassConstants.CLASS_SQFS_FP);
	
							 // 分配考场
							 examroom_classassignwcInit(elclassid,elUser.getId());
						}
					}
					return "login_study";
				}
			} else {
				setElmessage("账号没开通，请与管理员联系！");
			}
		} else {
			// 检测用户名是否存在
			if(!userDao.checkUsername(elUser.getUsername().trim())&&!userDao.checkUsername(username.trim())){
				setElmessage("用户名不存在");// 数据库中是18位，用15位登录，提示用户名不存在
			}else{
				elUser = userDao.query(elUser.getUsername()); 
				if(elUser.getId() != 0){
					myLogin = new MyLogin();
					myLogin.setElUser(elUser);
					myLogin.setLogintime(new Timestamp(System.currentTimeMillis()));
					userDao.insertLoingFailure(myLogin);
				}
				setElmessage("用户名或密码有错");	
			}
		}
		 
		// 返回系统设置的参数是否可注册
		registerstatus = SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
		// https路径
		httpsPath=SystemConfOp.getHttpsPath(getRequest().getServerName(), getRequest().getContextPath());
		
		
		return "wjmlogin";
	}
	/**
	 * 机构人员登录
	 */
	public String jg_login() throws ElException {
		System.out.println(department.getId());
		if(isFromRegister != 1){// 不是注册成功后的登录
			if(yzCodeIsNo != 1){// 不是从前台首页调用的action
				yzCodeIsNo = SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_YZCODE_OPEN);
				if(yzCodeIsNo == 1){
					if(yzCode == null || yzCode.equals("")){
						this.setElmessage("验证码不能为空,请填写验证码!!!");
						return "login";
					}else {
						if ((getSession().getAttribute("yzCodey")!=null&&!((String)
								 getSession().getAttribute("yzCodey")).equals(yzCode))) {
							this.setElmessage("验证码错误,请填写验证码!!!");
							return "login";
						}
					}
				}
			}
		}
//		在线用户限制
		int loginMax = SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_MAX).equals("") || SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_MAX).equals("无记录")  ? 0 :SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX);
		if(loginMax != 0){
			if(loginMax <= userDao.getTheCurrentOnlineUsersSize()){ 
				setElmessage2("当前在线用户数已超过管理员设定的最大值，请稍后再登陆或与管理员联系！"); 
				return "login";
			}
		}
		if(elUser==null){
			setElmessage("请从正常入口进入！");
			return "error";
		}
		// 判断是否填写用户名
		if(elUser.getUsername()==null||"".equals(elUser.getUsername().trim())){
			setElmessage("请填写用户名！");
			return "login";
		}
		user = userDao.query(elUser.getUsername());
		if(!SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("0")){
			if(user.getId() != 0 && !SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("无记录") && 
					userDao.checkLogonFailureNumber(user.getId(), SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX))){ 
				setElmessage("该账号已被锁定，请半小时后再尝试登陆！"); 
				return "error";
			}
		}
		//是否是本部门人员
		if(user.getDepartment().getId()!=department.getId()){
			setElmessage("您不是这个学院的用户，请确认!");
			return "error";
		}
		
		// 将用户名转成小写（库中存储着小写字符）
		elUser.setUsername(elUser.getUsername().trim().toLowerCase());
		String username="";
		if (elUser.getUsername().length() == 15) { 
			// 用户名15位转换成18位
			username=CheckCard.fixPersonIDCode(elUser.getUsername()).toLowerCase();
		} else if(elUser.getUsername().trim().length() == 18){
			// 用户名18位的话转成15位
			username=CheckCard.fixPersonIDCode15(elUser.getUsername()).toLowerCase();;
		}else{
			username = elUser.getUsername();
		}
		// 检测15位和18位和密码是否匹配
		if (userDao.check(elUser.getUsername(), MD5.crypt(elUser.getPassword()))||userDao.check(username, MD5.crypt(elUser.getPassword()))) {
			// 校验通过
			getSession().removeAttribute("yzCodey");
			// 获取用户信息
			elUser = userDao.query(elUser.getUsername().trim());
			if(elUser.getId()==0)// 登录账号不符合的时候查询转换后的账号
				elUser =userDao.query(username);
			// 检测是否已经在线
			if(OnlineUtil.checkUser(elUser.getId()+"")){
				// 查出上次该用户的最后登录信息
				if(myLogin==null){
					myLogin=new MyLogin();
				}
				String tempIpAddr=myLogin.getIpAddr();
				myLogin=userDao.getSessionUserLoginInfo(elUser.getId());
				getRequest().setAttribute("myLogin.ipAddr", tempIpAddr);
				return "login_logout";
			}  
			/*
			 * if(OnlineUtil.DifferentUser((elUser.getRole().getId()+""))){
			 * setElmessage("已经有和您相同角色的用户在其他地方登陆，您不能再登陆了！"); return "login"; }
			 */
			if (elUser.getValid()) {
				// 如果是超级管理员
				if(elUser.getRole().getId()==1){
					// 检测超级管理员的IP（为了安全，防止超级管理员账号在其他设置之外的ip登录）
//					SecurityBindIp securityBindIp = securityDao.getSecurityBindIpByRoleid(elUser.getRole().getId());
//					if(ipAddress != null && !ipAddress.equals("")){
//						String[] myIpArray = ipAddress.split("\\.");
//						String[] ipStratArray = null;
//						String[] ipEndArray = null;
//						String[] ip_startArray_per = null;
//						String[] ip_endArray_per = null;
//						boolean flag = true;
//						if(securityBindIp != null){
//							if(securityBindIp.getIp_start() != null && !securityBindIp.getIp_start().equals("")){
//								if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_ALLOWMULTIPLESIGN)){// 禁用多点登录
//									OnlineUtil.addOnlineUser(elUser.getId() + "", getSession());
//								}
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
								if(elUser.getRole().getId()==1){
									getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,1);
								}else{
									getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,
										elUser.getDepartment().getId());
								}
								getSession().setAttribute(ElConstants.SESSION_AGE,elUser.getAge());
								// 登录后 加显示 姓名 身份证 部门
								elUser=userDao.getUserById(elUser.getId());
				
				
								getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG, elUser.getShenfenzheng()); 
								
								getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,elUser.getDepartment().getName());
								// jforum 整合
								// Cookie cookie = new Cookie("jforumUserInfo", elUser
								// .getUsername());
								// cookie.setMaxAge(-1);
								// cookie.setPath("/");// cookie只在同一应用服务器有效
								// getResponse().addCookie(cookie);
								ScoreOperate.setScore(
										getSessionIntValue(ElConstants.SESSION_USERID),
										ElConstants.DIAN_LOGIN_DO);
								// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
								getSession().setAttribute("isLogin", "true");
								// 记录用户登入信息-------
								if(myLogin==null){
									myLogin=new MyLogin();
								}
								// 判断是否需要记录ip
								if(SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP)!=1){
									myLogin.setIpAddr(null);
								}
								myLogin.setElUser(elUser);
								userDao.addUserLoginInfo(myLogin);
								// 一下代码用于培训班分配给部门
								List<Integer>   depparentidList= new ArrayList<Integer>();
								depparentidList = shoppingDao.getdempParentid(elUser.getDepartment().getId());// 得到该用户所有上级部门id
								
								List<Integer>   depclassidList= new ArrayList<Integer>();
								for (Integer pid : depparentidList) {
									depclassidList = shoppingDao.getdepartmenttoclassbydepid(pid);// 循环所有父部门ID并找出该部门被分配的培训班集合
									for (Integer elclassid : depclassidList) {// 循环该集合进行培训班的绑定和分配
										classDao.assign2userAdd3(elUser.getId(),elclassid,ClassConstants.CLASS_SQFS_FP);
				
										 // 分配考场
										 examroom_classassignwcInit(elclassid,elUser.getId());
									}
								}
								return "login_study";
//							}
//						}
//					}
				}else{
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
					if(elUser.getRole().getId()==1){
						getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,1);
					}else{
						getSession().setAttribute(ElConstants.SESSION_MYDEPARTMENT,
							elUser.getDepartment().getId());
					}
					getSession().setAttribute(ElConstants.SESSION_AGE,elUser.getAge());
					// 登录后 加显示 姓名 身份证 部门
					elUser=userDao.getUserById(elUser.getId());
	
	
					getSession().setAttribute(ElConstants.SESSION_SHENFENZHENG, elUser.getShenfenzheng()); 
					
					getSession().setAttribute(ElConstants.SESSION_MYDEPNAME,elUser.getDepartment().getName());
					// jforum 整合
					// Cookie cookie = new Cookie("jforumUserInfo", elUser
					// .getUsername());
					// cookie.setMaxAge(-1);
					// cookie.setPath("/");// cookie只在同一应用服务器有效
					// getResponse().addCookie(cookie);
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.DIAN_LOGIN_DO);
					// 存个值到session中 用来判断用户是刚刚登入，然后提示短消息
					getSession().setAttribute("isLogin", "true");
					// 记录用户登入信息-------
					if(myLogin==null){
						myLogin=new MyLogin();
					}
					// 判断是否需要记录ip
					if(SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP)!=1){
						myLogin.setIpAddr(null);
					}
					myLogin.setElUser(elUser);
					userDao.addUserLoginInfo(myLogin);
					// 一下代码用于培训班分配给部门
					List<Integer>   depparentidList= new ArrayList<Integer>();
					depparentidList = shoppingDao.getdempParentid(elUser.getDepartment().getId());// 得到该用户所有上级部门id
					
					List<Integer>   depclassidList= new ArrayList<Integer>();
					for (Integer pid : depparentidList) {
						depclassidList = shoppingDao.getdepartmenttoclassbydepid(pid);// 循环所有父部门ID并找出该部门被分配的培训班集合
						for (Integer elclassid : depclassidList) {// 循环该集合进行培训班的绑定和分配
							classDao.assign2userAdd3(elUser.getId(),elclassid,ClassConstants.CLASS_SQFS_FP);
	
							 // 分配考场
							 examroom_classassignwcInit(elclassid,elUser.getId());
						}
					}
					return "login_study";
				}
			} else {
				setElmessage("账号没开通，请与管理员联系！");
			}
		} else {
			// 检测用户名是否存在
			if(!userDao.checkUsername(elUser.getUsername().trim())&&!userDao.checkUsername(username.trim())){
				setElmessage("用户名不存在");// 数据库中是18位，用15位登录，提示用户名不存在
			}else{
				elUser = userDao.query(elUser.getUsername()); 
				if(elUser.getId() != 0){
					myLogin = new MyLogin();
					myLogin.setElUser(elUser);
					myLogin.setLogintime(new Timestamp(System.currentTimeMillis()));
					userDao.insertLoingFailure(myLogin);
				}
				setElmessage("用户名或密码有错");	
			}
		}
		 
		// 返回系统设置的参数是否可注册
		registerstatus = SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
		// https路径
		httpsPath=SystemConfOp.getHttpsPath(getRequest().getServerName(), getRequest().getContextPath());
		
		
		return "login";
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

		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());

	}

	public String login_logout() throws ElException {
		// if (getSession().getAttribute("yzCodey")!=null&&((String)
		// getSession().getAttribute("yzCodey")).equals(yzCode)) {

		if ("yes".equals(getRequest().getParameter("ispki"))
				|| userDao.check(elUser.getUsername(), MD5.crypt(elUser
						.getPassword()))) {
			elUser = userDao.query(elUser.getUsername());
			OnlineUtil.removeOnlineUser(elUser.getId() + "");
			// 注销时间 相当于退出时间
			userDao.updateSessionUserExittime(elUser.getId());
			// myLogin=userDao.getSessionUserLoginInfo(elUser.getId());
		} else {
			setElmessage("用户名或密码有错！");
			return "login_logout";
		}
		return "login";
	}

	public String registerInit() throws ElException {
		// depTree = departmentDao.getDepTree(1, -1, true);
		// phCourses = frontDao.listPhCourse(0, 8);
		// for (int i = 0; i < phCourses.size(); i++) {
		// String name = phCourses.get(i).getName();
		// phCourses.get(i).setName(
		// name.length() > 11 ? name.substring(0, 9) + "" : name);
		// }
		elUser = elUser == null ? new ELUser() : elUser;
		// return "register"; //原来的页面路径
		// jingzhongs=userDao.getBaseDatatByTypeid(1);
		// zhiwus=userDao.getBaseDatatByTypeid(2);
		// zhijis=userDao.getBaseDatatByTypeid(3);
		// gangweis=userDao.getBaseDatatByTypeid(4);
		// dishis=userDao.getBaseDatatByTypeid(5);
		jingzhongs = userDao.getBaseDatatByTypeidc(1);
		zhiwus = userDao.getBaseDatatByTypeidc(2);
		zhijis = userDao.getBaseDatatByTypeidc(3);
		gangweis = userDao.getBaseDatatByTypeidc(4);
		dishis = userDao.getBaseDatatByTypeidc(5);
		// 判断注册信息是否都要验证
		if (SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
			getRequest().setAttribute("isAll", "yes");
			// return "register2";
		} else {
			getRequest().setAttribute("isAll", "no");
			// return "register4";
		}
		if (type == 2) {
			return "register2";
		} else if (type == 1) {
			return "register1";
		}
		// return "register2";//全部改为register
		return "register";
	}

	public String userRegister2() throws ElException {
		// depTree = departmentDao.getDepTree(1, -1,true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1
				|| (getRequest().getParameter("isreg") != null && Integer
						.parseInt(getRequest().getParameter("isreg")) == 1)) {
			stTree = stationDao.getStTree_level1(1, -1, true);
		} else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		// depTree = departmentDao.getDepTree_level1(1, -1,true);
		return "userRegister2";
	}
	
	public String userRegister() throws ElException {
		// depTree = departmentDao.getDepTree(1, -1,true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1
				|| (getRequest().getParameter("isreg") != null && Integer
						.parseInt(getRequest().getParameter("isreg")) == 1)) {
			depTree = departmentDao.getDepTree_level1(1, -1, true);
		} else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		// depTree = departmentDao.getDepTree_level1(1, -1,true);
		return "userRegister";
	}

	public String register() throws Exception {
		// depTree = departmentDao.getDepTree(1, -1, true);
		// depTree = departmentDao.getDepTree(1, -1, true);
		// jingzhongs=userDao.getBaseDatatByTypeid(1);
		// zhiwus=userDao.getBaseDatatByTypeid(2);
		// zhijis=userDao.getBaseDatatByTypeid(3);
		// gangweis=userDao.getBaseDatatByTypeid(4);
		// dishis=userDao.getBaseDatatByTypeid(5);
		jingzhongs = userDao.getBaseDatatByTypeidc(1);
		zhiwus = userDao.getBaseDatatByTypeidc(2);
		zhijis = userDao.getBaseDatatByTypeidc(3);
		gangweis = userDao.getBaseDatatByTypeidc(4);
		dishis = userDao.getBaseDatatByTypeidc(5);
		luntanjibies = userDao.getBaseDatatByTypeidc(6);
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
			// if (null == elUser.getDepartment() || elUser.getDanwei() == null
			// || "".equals(elUser.getDanwei().trim())) {
			// setElmessage("填写单位编号！");
			// //return "register_error";
			// return resultPage;
			// }
			// if (departmentDao.getDepByBH(elUser.getDanwei()).getId() <= 0) {
			// setElmessage("本系统没找到您输入的单位编号，请确定您单位编号是否正确。如果无误，请与管理员联系");
			// //return "register_error";
			// return resultPage;
			// }
			// 对用户名是身份证号码的情况进行检查
			String userName = elUser.getUsername().trim().toLowerCase();

			if ("".equals(CheckCard.IDCardValidate(userName))) {// 如果用户名是有效身份证
				boolean isExistUserName = false;

				// 如果用户的身份证号码是15位，判断数据库中是否存在该人15位和18位的身份证号码
				if (userName.length() == 15) {
					isExistUserName = userDao.checkUsername(userName) ? true
							: userDao.checkUsername(CheckCard
									.fixPersonIDCode(userName));
				} else {// 如果用户的身份证号码是18位，判断数据库中是否存在该人15位和18位的身份证号码
					isExistUserName = userDao.checkUsername(userName) ? true
							: userDao.checkUsername(CheckCard
									.fixPersonIDCode15(userName));
				}
				if (isExistUserName) {
					setElmessage("您注册的用户名已存在，请重新输入用户名！");
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
			}

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
			if (userDao.checkUserShenfenzheng(elUser.getShenfenzheng(), elUser
					.getId())) {
				setElmessage("您所填的身份证已被其他人使用，请重新输入！");
				// 判断注册信息是否都要验证
				if (SystemConfOp
						.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
					getRequest().setAttribute("isAll", "yes");
				} else {
					getRequest().setAttribute("isAll", "no");
				}
				return resultPage;// 返回注册页面
			}
			// if ("".equals(elUser.getUsername())) {
			// setElmessage("请输入用户名！");
			// //return "register_error";
			// return resultPage;
			// }
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
				elUser.setPassword(MD5.crypt(elUser.getPassword()));
				elUser.setRealname(department.getName() + "管理员");
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
		return "register_success";
	}

	public String register1() throws Exception {
		// depTree = departmentDao.getDepTree(1, -1, true);
		// depTree = departmentDao.getDepTree(1, -1, true);
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		gangweis = userDao.getBaseDatatByTypeid(4);
		dishis = userDao.getBaseDatatByTypeid(5);
		String resultPage = "register_error";
		// 检测身份证不能和系统里现有的相同
		boolean isExist15 = false;
		// 检测身份证不能和系统里现有的相同
		boolean isExist18 = false;
		String userCarNo = elUser.getShenfenzheng().trim().toLowerCase();
		// 如果用户的身份证号码是15位，判断数据库中是否存在该人15位和18位的身份证号码
		if (userCarNo.length() == 15) {
			isExist15 =userDao.checkUserShenfenzheng(userCarNo,elUser.getId())?true:userDao.checkUserShenfenzheng(CheckCard.fixPersonIDCode(userCarNo),elUser.getId());
		} else {// 如果用户的身份证号码是18位，判断数据库中是否存在该人15位和18位的身份证号码
			isExist18 =userDao.checkUserShenfenzheng(userCarNo,elUser.getId())?true:userDao.checkUserShenfenzheng(CheckCard.fixPersonIDCode15(userCarNo),elUser.getId());
		}
		// 判断注册信息是否都要验证
		// if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)){
		// //resultPage="register_error";
		// }else{
		// //resultPage="register_error4";
		// }
		if (SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER) == 1) {
			// if (null == elUser.getDepartment() || elUser.getDanwei() == null
			// || "".equals(elUser.getDanwei().trim())) {
			// setElmessage("填写单位编号！");
			// //return "register_error";
			// return resultPage;
			// }
			// if (departmentDao.getDepByBH(elUser.getDanwei()).getId() <= 0) {
			// setElmessage("本系统没找到您输入的单位编号，请确定您单位编号是否正确。如果无误，请与管理员联系");
			// //return "register_error";
			// return resultPage;
			// }
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
			// if(userDao.checkUserShenfenzheng(elUser.getShenfenzheng(),elUser.getId())){
			if (isExist15 || isExist18) {
				setElmessage("您所填的身份证已被其他人使用，请重新输入！");
				// 判断注册信息是否都要验证
				if (SystemConfOp
						.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
					getRequest().setAttribute("isAll", "yes");
				} else {
					getRequest().setAttribute("isAll", "no");
				}
				return resultPage;// 返回注册页面
			}
			// if ("".equals(elUser.getUsername())) {
			// setElmessage("请输入用户名！");
			// //return "register_error";
			// return resultPage;
			// }
			// 注册用户
			elUser.setRole(new ElRole(2));
			elUser.setValid(!SystemConfOp
					.getBooleanValue(ElConstants.REGISTER_NEED_SH));
			// 根节点下方自动创建一个部门节点
			int returnId = 0;
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
			department.setParent(new ElNode(1));
			department.setManager(new ELUser(1));
			returnId = departmentDao.addDep1(department);
			department.setId(returnId);
			// 部门设置进去
			// department=departmentDao.getDepByBH(elUser.getDanwei());
			department = departmentDao.getDepById(department.getId());
			elUser.setDepartment(department);
			String tempPassword = elUser.getPassword();//
			elUser.setPassword(MD5.crypt(elUser.getPassword()));
			int eluserid = userDao.insert(elUser);
			// 分配该用户该部门节点
			departmentDao.addOpusers("op", eluserid, department.getId());

			// 自动创建素材库节点，赋值素材库节点该用户
			StuffLib qstuff = new StuffLib();
			qstuff.setTitle(department.getName());
			qstuff.setOwner(new ELUser(eluserid));
			qstuff.setLength(10 * 1024 * 1024L);
			qstuff.setParent(new StuffLib(0));
			qstuff.setType(5);
			int returnStuffId = stuffDao.addQstuff(qstuff);
			J2EEFileUtil.createFolder("/elstuffs/" + department.getName());

			stuffDao.addStuffOpusers(eluserid, returnStuffId);

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
			}
		} else {
			setElmessage("系统关闭了注册功能，请与管理员联系");
			// return "register_error";
			return "error";
		}
		return "register_success";
	}

	public String myinfo_completeinit() throws ElException {
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		return "myinfo_complete";
	}

	public String myinfo_complete() throws ElException {
		userDao.alterMyInfo(elUser);
		userDao.setStation(elUser.getId(), 1);
		elUser = userDao.getUserById(elUser.getId());
		getSession().setAttribute(ElConstants.SESSION_STATION, 1);
		setElmessage("账号没开通，请与管理员联系！");
		return "study_index";
	}

	public String getpasswordinit() throws ElException {

		return "getpassword";
	}

	public String getpassword() throws ElException {
		if (elUser == null) {
			setElmessage("请将信息填写完整！");
			return "getpassword_err";
		}
		// if (!userDao.checkSfzandusername(elUser.getUsername(), elUser
		// .getUserno())) {
		// setElmessage("您填写的信息不正确！");
		// return "getpassword_err";
		// }
		elUser = userDao.query(elUser.getUsername());

		return "getpassword_succ";
	}

	public String logout() throws ElException {
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		
		
		//20141008增加管理员判断
		Integer roleid=(Integer)getSession().getAttribute(ElConstants.SESSION_ROLE);
		if(roleid!=4){
			if (userid == 0) {// 证明session中的用户已经销毁了
				return "logout_admin";
			}
			getSession().removeAttribute(ElConstants.SESSION_USERID);
			getSession().removeAttribute(ElConstants.SESSION_USERNAME);
			getSession().removeAttribute(ElConstants.SESSION_REALNAME);
			getSession().removeAttribute(ElConstants.SESSION_ROLE);
			getSession().removeAttribute(ElConstants.SESSION_ROLENAME);
			getSession().removeAttribute(ElConstants.SESSION_MYDEPARTMENT);
			getSession().removeAttribute(ElConstants.SESSION_AGE);
			OnlineUtil.removeStudyInfo(getSession());
			//更新智能辅导分-》登录
			IntelligentLoginUtil.intelligentLoginOut(userid);
			// userDao.updateFlowUser();
			// 记录用户退出信息
			userDao.updateSessionUserExittime(userid);
			// Cookie cookie = new Cookie(jforumSSOCookieNameUser, "");
			// cookie.setMaxAge(0); // delete the cookie.
			// getResponse().addCookie(cookie);
			// Cookie cookie = new Cookie("jforumUserInfo", "");
			// cookie.setMaxAge(0); // delete the cookie.
			// cookie.setPath("/");
			// getResponse().addCookie(cookie);
			// Cookie cookie = new Cookie("elearning.cookie.username", null);
			// cookie.setPath("/");
			// cookie.setMaxAge(0);
			// ServletActionContext.getResponse().addCookie(cookie);
			OnlineUtil.removeOnlineUser(userid + "");
			Cookie cookie = new Cookie("elearning.cookie.username", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			ServletActionContext.getResponse().addCookie(cookie);
			// 返回，然后关闭浏览器
			// return "winClose";
			return "logout_admin";
		}else{
			if (userid == 0) {// 证明session中的用户已经销毁了
				return "logout";
			}
			getSession().removeAttribute(ElConstants.SESSION_USERID);
			getSession().removeAttribute(ElConstants.SESSION_USERNAME);
			getSession().removeAttribute(ElConstants.SESSION_REALNAME);
			getSession().removeAttribute(ElConstants.SESSION_ROLE);
			getSession().removeAttribute(ElConstants.SESSION_ROLENAME);
			getSession().removeAttribute(ElConstants.SESSION_MYDEPARTMENT);
			getSession().removeAttribute(ElConstants.SESSION_AGE);
			OnlineUtil.removeStudyInfo(getSession());
			//更新智能辅导分-》登录
			IntelligentLoginUtil.intelligentLoginOut(userid);
			// userDao.updateFlowUser();
			// 记录用户退出信息
			userDao.updateSessionUserExittime(userid);
			// Cookie cookie = new Cookie(jforumSSOCookieNameUser, "");
			// cookie.setMaxAge(0); // delete the cookie.
			// getResponse().addCookie(cookie);
			// Cookie cookie = new Cookie("jforumUserInfo", "");
			// cookie.setMaxAge(0); // delete the cookie.
			// cookie.setPath("/");
			// getResponse().addCookie(cookie);
			// Cookie cookie = new Cookie("elearning.cookie.username", null);
			// cookie.setPath("/");
			// cookie.setMaxAge(0);
			// ServletActionContext.getResponse().addCookie(cookie);
			OnlineUtil.removeOnlineUser(userid + "");
			Cookie cookie = new Cookie("elearning.cookie.username", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			ServletActionContext.getResponse().addCookie(cookie);
			// 返回，然后关闭浏览器
			// return "winClose";
			return "logout";
		}
		
	}

	private List<Course> phCourses;
	private FrontDao frontDao;

	public List<Course> getPhCourses() {
		return phCourses;
	}

	public void setPhCourses(List<Course> phCourses) {
		this.phCourses = phCourses;
	}
	
	public String account_addInit_cisco() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs = userDao.getBaseDatatByTypeid(1);
			zhiwus = userDao.getBaseDatatByTypeid(2);
			zhijis = userDao.getBaseDatatByTypeid(3);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5);
			// 论坛级别
			luntanjibies = userDao.getBaseDatatByTypeid(6);
		} else {
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			jingzhongs = userDao.getBaseDatatByTypeid(1, userid);
			zhiwus = userDao.getBaseDatatByTypeid(2, userid);
			zhijis = userDao.getBaseDatatByTypeid(3, userid);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5, userid);
			luntanjibies = userDao.getBaseDatatByTypeid(6, userid);
			// 论坛级别
		}
		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		return "account_addInit_cisco";
	}
	public String account_add_cisco() throws ElException{
		elUser.setPassword(MD5.crypt(elUser.getPassword()));
		if(elUser.getDepartment().getId()==0){
			elUser.setDepartment(new Department(6290));
		}
		if(elUser.getXianzhiwei()==null||elUser.getXianzhiwei().equals("")){
			elUser.setXianzhiwei("111");
		}
		int ii = userDao.insert_cisco(elUser);
		elUser.setId(ii);
		return "account_add_cisco";
	}

	public String account_addInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs = userDao.getBaseDatatByTypeid(1);
			zhiwus = userDao.getBaseDatatByTypeid(2);
			zhijis = userDao.getBaseDatatByTypeid(3);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5);
			// 论坛级别
			luntanjibies = userDao.getBaseDatatByTypeid(6);
		} else {
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			jingzhongs = userDao.getBaseDatatByTypeid(1, userid);
			zhiwus = userDao.getBaseDatatByTypeid(2, userid);
			zhijis = userDao.getBaseDatatByTypeid(3, userid);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5, userid);
			luntanjibies = userDao.getBaseDatatByTypeid(6, userid);
			// 论坛级别
		}
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		elUser = elUser == null ? new ELUser() : elUser;
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// else {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }
		// roleDao.listRoles(1) 当用户角色不为1(超级管理员)时，不现实超级管理员类型，让用户创建;
		// roles = getSessionIntValue(ElConstants.SESSION_ROLE) !=
		// 1?roleDao.listRoles(1):roleDao.listRoles();
		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		// if(depTree.getChild().size() == 0 &&
		// getSessionIntValue(ElConstants.SESSION_ROLE) != 1){
		// setElmessage("没有可操作的部门类别");
		// return "error";
		// }
		// 判断注册信息是否都要验证
		String resultPage = "account_add";
		if (SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
			getRequest().setAttribute("isAll", "yes");// 都要验证
			// resultPage="account_add";
		} else {
			getRequest().setAttribute("isAll", "no");// 不需要都验证
			// resultPage="account_add_noall";
		}
		// return "account_add";
		return resultPage;
	}
	
	public String account_addInit2() throws ElException {
//		company = departmentDao.getCompanyById((Integer) getSession()
//				.getAttribute("myCompany"));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			if(depTree.getChild().size() == 0){ 
				setElmessage("没有可操作的部门节点，不能指定新添加用户到某个部门下，请联系超级管理员！");
			}
		}

		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			roles = roleDao.listRoles();  
		}else{ 
			roles = new ArrayList<ElRole>(); 
			role = roleDao.getRoleByName("准开通");
			if(role != null && role.getId() != 0){
				roles.add(role);
			}
			role = null;
			role = roleDao.getRoleByName("初审通过");
			if(role != null && role.getId() != 0){
				roles.add(role);
			}
		}
		elUser=new ELUser();
		return "account_add2";
	}
	
	public String account_add2() throws ElException { 
		// HttpServletRequest requset = ServletActionContext.getRequest();
		if (userDao.checkUsername(elUser.getUsername())) {
			setElmessage("您添加的用户名已存在，请重新输入用户名！");
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				depTree = departmentDao.getDepTree(
						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
						-1, true);
			else {
				depTree = departmentDao.getDepTree(
						getSessionIntValue(ElConstants.SESSION_USERID), "op",
						-1, true);
				
			}
			roles = roleDao.listRoles();	

			return "register_error";
		}
		if ("".equals(elUser.getUsername())) {
			setElmessage("请输入用户名！");
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				depTree = departmentDao.getDepTree(
						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
						-1, true);
			else {
				depTree = departmentDao.getDepTree(
						getSessionIntValue(ElConstants.SESSION_USERID), "op",
						-1, true);
				if(depTree.getChild().size() == 0){ 
					setElmessage("没有可操作的部门节点，不能指定新添加用户到某个部门下，请联系超级管理员！");
					return "error";
				}

			}
			roles = roleDao.listRoles();	
			return "register_error";
		} 		
		elUser.setValid(true);
		elUser.setPassword(MD5.crypt(elUser.getPassword()));
	//	elUser.setCompany(new Company(elUser.getDepartment().getId()));
		userDao.insert2(elUser);

		return "account_add_success2";
	}

	/**
	 * Description: 用户导入初始化（按指定部门）
	 * 
	 * @Version1.0 2012-7-13 上午10:10:59 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String account_importBydepInit() throws ElException {
		if (elUser == null || elUser.getDepartment() == null
				|| elUser.getDepartment().getId() <= -2) {
			setElmessage("请指定用户将要导入有效部门");
			return "error";
		}
		department = departmentDao.getDepById((elUser == null || elUser
				.getDepartment() == null) ? 1 : elUser.getDepartment().getId());
		return "account_importBydep";
	}
	//五矿导入用户
	public String account_importBydepInit2() throws ElException {
		if (elUser == null || elUser.getDepartment() == null
				|| elUser.getDepartment().getId() <= -2) {
			setElmessage("请指定用户将要导入有效部门");
			return "error";
		}
		department = departmentDao.getDepById((elUser == null || elUser
				.getDepartment() == null) ? 1 : elUser.getDepartment().getId());
		return "account_importBydep";
	}

	/**
	 * Description：指定部门的用户批量导入
	 * 
	 * @Version1.0 2012-7-13 上午10:12:29 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	// public String account_importBydep() throws ElException {
	// department = departmentDao.getDepById((elUser == null || elUser
	// .getDepartment()== null ) ? 1 : elUser.getDepartment().getId());
	// if (null != st) {
	// if (!J2EEFileUtil.getExtention(stFileName).toLowerCase().equals(
	// "xls")) {
	// setElmessage("您需要导入的文件格式不正确，请重新选择！");
	// return "account_importBydep";
	// }
	// if (st.length() > 10 * 1024 * 1024) {
	// setElmessage("您上传的文件过大！");
	// return "account_importBydep";
	// } else {
	// //UserExcelUtil.writeUser(st,elUser.getDepartment().getId());
	// String isOk=UserExcelUtil.writeUser2(st,elUser.getDepartment().getId());
	// if(!"true".equals(isOk)&&!"".equals(isOk)){
	// setElmessage(isOk);
	// // ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
	// // ElLoggerConstants.LOG_MOD_ELUSER,
	// // ElLoggerConstants.LOG_TYPE_ADD,ElLogger.shortString(isOk),
	// // ElLoggerConstants.LOG_RES_ERR,0);
	// return "account_importBydep";
	//					
	// }
	// }
	// } else {
	// setElmessage("请输入上传文件");
	// return "account_importBydep";
	// }
	// //return "account_import_success";
	// return "account_list";
	// }
	public String account_importInit() throws ElException {

		return "account_import";
	}
	
	public String account_importInit2() throws ElException {

		return "account_import2";
	}
	public String account_importInit3() throws ElException {

		return "account_import3";
	}
	
	public String sta_accountImportInit() throws ElException {

		return "account_import";
	}
	
	public String company_importInit() throws ElException {

		return "company_import";
	}


	/**
	 * 导入前的检测
	 * 
	 * @return
	 * @throws Exception
	 */
	public String accountImportCheck() throws Exception {
		department = departmentDao.getDepById((elUser == null || elUser
				.getDepartment() == null) ? 0 : elUser.getDepartment().getId());
		String resultPage = "account_importBydep";
		if (department == null || department.getId() == 0) {
			resultPage = "account_import";
		}
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
				String isOk = UserExcelUtil.checkWriteUser(st, department
						.getId());
				// if(!"true".equals(isOk)&&!"".equals(isOk)){//返回
				setElmessage(isOk);
				// 复制此文件到服务器临时保存
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String filename = "user_" + userid + "_"
						+ System.currentTimeMillis();
				J2EEFileUtil.upload(st, "xls", "/importtemp/", filename);
				stFileName = filename + ".xls";
				if (elUser == null) {
					elUser = new ELUser();
					elUser.setDepartment(new Department(0));
				} else {
					// elUser.setDepartment(department);
				}
				return "accountImportInfo";
				// }
			}
		} else {
			setElmessage("请输入上传文件");
			return resultPage;
		}
	}
	/**
	 * 企业用户导入
	 */
	public String companyImportCheck() throws Exception {
		
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
				String isOk = UserExcelUtil.checkCompanyUser(st);
				// if(!"true".equals(isOk)&&!"".equals(isOk)){//返回
				setElmessage(isOk);
				// 复制此文件到服务器临时保存
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String filename = "user_" + userid + "_"
						+ System.currentTimeMillis();
				J2EEFileUtil.upload(st, "xls", "/importtemp/", filename);
				stFileName = filename + ".xls";
				if (elUser == null) {
					elUser = new ELUser();
					elUser.setDepartment(new Department(0));
				} else {
					// elUser.setDepartment(department);
				}
				return "accountImportInfo";
				// }
			}
		} else {
			setElmessage("请输入上传文件");
			return resultPage;
		}
	}
	
	public String company_import() throws ElException {
		if (stFileName != null) {
			File xls = new File(ServletActionContext.getServletContext()
					.getRealPath("/")
					+ "/importtemp/" + stFileName);
			if (xls.exists()) {
				String isOk = "";
				isOk = UserExcelUtil.writeCompanyUser(xls);
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
		return "account_list";
	}
	
	
	
	/**
	 * 导入前的检测（五矿导入用户）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String accountImportCheck3() throws Exception {
		
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
				String isOk = UserExcelUtil.checkWriteUser2(st);
				// if(!"true".equals(isOk)&&!"".equals(isOk)){//返回
				setElmessage(isOk);
				// 复制此文件到服务器临时保存
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String filename = "user_" + userid + "_"
						+ System.currentTimeMillis();
				J2EEFileUtil.upload(st, "xls", "/importtemp/", filename);
				stFileName = filename + ".xls";
				if (elUser == null) {
					elUser = new ELUser();
					elUser.setDepartment(new Department(0));
				} else {
					// elUser.setDepartment(department);
				}
				return "accountImportInfo";
				// }
			}
		} else {
			setElmessage("请输入上传文件");
			return resultPage;
		}
	}
	
	
	
	/**
	 * 导入前的检测（部门）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String accountImportCheck2() throws Exception {
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
				String isOk = UserExcelUtil.checkWriteUser(st);
				// if(!"true".equals(isOk)&&!"".equals(isOk)){//返回
				setElmessage(isOk);
				// 复制此文件到服务器临时保存
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String filename = "user_" + userid + "_"
						+ System.currentTimeMillis();
				J2EEFileUtil.upload(st, "xls", "/importtemp/", filename);
				stFileName = filename + ".xls";
				if (elUser == null) {
					elUser = new ELUser();
					elUser.setDepartment(new Department(0));
				} else {
					// elUser.setDepartment(department);
				}
				return "accountImportInfo";
				// }
			}
		} else {
			setElmessage("请输入上传文件");
			return resultPage;
		}
	}
	
	
	/**
	 * 导入前的检测(岗位导入)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sta_accountImportCheck() throws Exception {
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
				String isOk = UserExcelUtil.checkWriteSta(st);
				// if(!"true".equals(isOk)&&!"".equals(isOk)){//返回
				setElmessage(isOk);
				// 复制此文件到服务器临时保存
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String filename = "user_" + userid + "_"
						+ System.currentTimeMillis();
				J2EEFileUtil.upload(st, "xls", "/importtemp/", filename);
				stFileName = filename + ".xls";
				if (elUser == null) {
					elUser = new ELUser();
					elUser.setDepartment(new Department(0));
				} else {
					// elUser.setDepartment(department);
				}
				return "accountImportInfo";
				// }
			}
		} else {
			setElmessage("请输入上传文件");
			return resultPage;
		}
	}

	
	// public String account_import() throws ElException {
	// if (null != st) {
	// if (!J2EEFileUtil.getExtention(stFileName).toLowerCase().equals(
	// "xls")) {
	// setElmessage("您需要导入的文件格式不正确，请重新选择！");
	// return "account_import";
	// }
	// if (st.length() > 10 * 1024 * 1024) {
	// setElmessage("您上传的文件过大！");
	// return "account_import";
	// } else {
	// //UserExcelUtil.writeUser(st);
	// String isOk=UserExcelUtil.writeUser2(st);
	// if(!"true".equals(isOk)&&!"".equals(isOk)){//返回
	// setElmessage(isOk);
	// return "account_import";
	// }
	// }
	// } else {
	// setElmessage("请输入上传文件");
	// return "account_import";
	// }
	// //return "account_import_success";
	// return "account_list";
	// }
	public String account_import() throws ElException {
		if (stFileName != null) {
			File xls = new File(ServletActionContext.getServletContext()
					.getRealPath("/")
					+ "/importtemp/" + stFileName);
			if (xls.exists()) {
				department = departmentDao.getDepById((elUser == null || elUser
						.getDepartment() == null) ? 0 : elUser.getDepartment()
						.getId());
				String isOk = "";
				if (department != null && department.getId() > 0) {
					isOk = UserExcelUtil.writeUser2(xls, department.getId());
				} else {
					isOk = UserExcelUtil.writeUser2(xls);
				}
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
		return "account_list";
	}
	
	public String sta_account_import() throws ElException {
		if (stFileName != null) {
			File xls = new File(ServletActionContext.getServletContext()
					.getRealPath("/")
					+ "/importtemp/" + stFileName);
			if (xls.exists()) {
				String isOk = "";
				StationDaoImpl staDao = new StationDaoImpl();
				DepartmentDaoImpl depDao = new DepartmentDaoImpl();
				List<Department> deps = staDao.getDepInSta(1);
				Department dep = new Department();
				for(int i=0; i<deps.size();i++){
					if(!staDao.checkStaName(deps.get(i).getBh())){
					//	dep = depDao.getDepByBH(deps.get(i).getBh());
						String sjbh = depDao.getBhByParentid(deps.get(i).getParentid());
						int staParid = staDao.getStationIdByBh(sjbh);
						staDao.addSt(deps.get(i),staParid);
					}
					//
				}
					isOk = UserExcelUtil.writeSta(xls);
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
		return "account_list";
	}
	
	/**
	 * 部门导入
	 */
	public String account_import2() throws ElException {
		if (stFileName != null) {
			File xls = new File(ServletActionContext.getServletContext()
					.getRealPath("/")
					+ "/importtemp/" + stFileName);
			if (xls.exists()) {
				
				String isOk = "";
				isOk = UserExcelUtil.writeUser3(xls);
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
		return "account_list";
	}
	
	
	
	public String account_import3() throws ElException {
		if (stFileName != null) {
			File xls = new File(ServletActionContext.getServletContext()
					.getRealPath("/")
					+ "/importtemp/" + stFileName);
			if (xls.exists()) {
				String isOk = "";
				isOk = UserExcelUtil.writeUser22(xls);
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
		return "account_list";
	}

	public String account_add() throws ElException {
		// HttpServletRequest requset = ServletActionContext.getRequest();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs = userDao.getBaseDatatByTypeid(1);
			zhiwus = userDao.getBaseDatatByTypeid(2);
			zhijis = userDao.getBaseDatatByTypeid(3);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5);
			luntanjibies = userDao.getBaseDatatByTypeid(6);
		} else {
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			jingzhongs = userDao.getBaseDatatByTypeid(1, userid);
			zhiwus = userDao.getBaseDatatByTypeid(2, userid);
			zhijis = userDao.getBaseDatatByTypeid(3, userid);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5, userid);
			luntanjibies=userDao.getBaseDatatByTypeid(6,userid);
		}
		// 对用户名是身份证号码的情况进行检查
		String userName = elUser.getUsername().trim().toLowerCase();

		if ("".equals(CheckCard.IDCardValidate(userName))) {// 如果用户名是有效身份证
			boolean isExistUserName = false;

			// 如果用户的身份证号码是15位，判断数据库中是否存在该人15位和18位的身份证号码
			if (userName.length() == 15) {
				isExistUserName = userDao.checkUsername(userName) ? true
						: userDao.checkUsername(CheckCard
								.fixPersonIDCode(userName));
			} else {// 如果用户的身份证号码是18位，判断数据库中是否存在该人15位和18位的身份证号码
				isExistUserName = userDao.checkUsername(userName) ? true
						: userDao.checkUsername(CheckCard
								.fixPersonIDCode15(userName));
			}
			if (isExistUserName) {
				setElmessage("您注册的用户名已存在，请重新输入用户名！");
				roles = roleDao.listRoles(
						getSessionIntValue(ElConstants.SESSION_ROLE),
						getSessionIntValue(ElConstants.SESSION_USERID));
				if (SystemConfOp
						.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
					getRequest().setAttribute("isAll", "yes");// 都要验证
				} else {
					getRequest().setAttribute("isAll", "no");// 不需要都验证
				}
				return "register_error";// 返回添加页面

			}
		}

		if (userDao.checkUsername(elUser.getUsername())) {
			setElmessage("您添加的用户名已被其他人使用，请重新输入！");
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
			// true);
			// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
			// -1, true);
			// else {
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_USERID), "op",
			// -1, true);
			// }
			// roles = roleDao.listRoles();
			roles = roleDao.listRoles(
					getSessionIntValue(ElConstants.SESSION_ROLE),
					getSessionIntValue(ElConstants.SESSION_USERID));
			if (SystemConfOp
					.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
				getRequest().setAttribute("isAll", "yes");// 都要验证
			} else {
				getRequest().setAttribute("isAll", "no");// 不需要都验证
			}
			return "register_error";// 返回添加页面
		}
		// 检测身份证不能和系统里现有的相同
		boolean isExist15 = false;
		boolean isExist18 = false;
		String userCarNo = elUser.getShenfenzheng().trim().toLowerCase();

		// 如果用户的身份证号码是15位，判断数据库中是否存在该人15位和18位的身份证号码
		if (userCarNo.length() == 15) {
			isExist15 = userDao.checkUserShenfenzheng(userCarNo, elUser
					.getId()) ? true : userDao.checkUserShenfenzheng(
					CheckCard.fixPersonIDCode(userCarNo), elUser.getId());
		} else {// 如果用户的身份证号码是18位，判断数据库中是否存在该人15位和18位的身份证号码
			isExist18 = userDao.checkUserShenfenzheng(userCarNo, elUser
					.getId()) ? true : userDao.checkUserShenfenzheng(
					CheckCard.fixPersonIDCode15(userCarNo), elUser.getId());
		}

		if (isExist15 || isExist18) {
			setElmessage("您添加的用户所用的身份证已存在，请重新输入！");
			// roles = roleDao.listRoles();
			roles = roleDao.listRoles(
					getSessionIntValue(ElConstants.SESSION_ROLE),
					getSessionIntValue(ElConstants.SESSION_USERID));
			if (SystemConfOp
					.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
				getRequest().setAttribute("isAll", "yes");// 都要验证
			} else {
				getRequest().setAttribute("isAll", "no");// 不需要都验证
			}
			return "register_error";// 返回添加页面
		}
		if ("".equals(elUser.getUsername())) {
			setElmessage("请输入用户名！");
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
			// true);
			// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
			// -1, true);
			// else {
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_USERID), "op",
			// -1, true);
			// }
			// roles = roleDao.listRoles();
			roles = roleDao.listRoles(
					getSessionIntValue(ElConstants.SESSION_ROLE),
					getSessionIntValue(ElConstants.SESSION_USERID));
			if (SystemConfOp
					.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
				getRequest().setAttribute("isAll", "yes");// 都要验证
			} else {
				getRequest().setAttribute("isAll", "no");// 不需要都验证
			}
			return "register_error";
		}
		elUser.setPassword(MD5.crypt(elUser.getPassword()));
		// elUser.setValid(true);//页面有传过来
		// elUser.setJy(0);
		elUser.setDanwei(departmentDao.getDepById(elUser.getDepartment().getId()).getName());
		userDao.insert(elUser);
		// if (elUser.getRole().getId() == 6) {
		// ForumAdminDaoImpl ai = new ForumAdminDaoImpl();
		// ForumBlock fb = new ForumBlock();
		// fb.setTitle("专家--" + elUser.getRealname());
		// fb.setManager(elUser);
		// fb.setFbtype(new ForumBlockType(1));
		// ai.addFblock(fb);
		// }

		// ForumUtil.addUser(elUser);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_ELUSER,
				ElLoggerConstants.LOG_TYPE_ADD, elUser.getUsername(),
				ElLoggerConstants.LOG_RES_SUCC, elUser.getId());
		
		return "account_search";
	}

	private Department depTree;
	private Department deprTree;
	private Station stTree;

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

	public String account_searchInit() throws ElException {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
			// true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);

		else {
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
			// true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		department = department == null ? new Department(1) : department;
		roles = roleDao.listRoles();
		if (depTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的部门类别");
			return "error";
		}
		return "account_searchInit";
	}

	public String account_search() throws ElException {
		userid = getSessionIntValue(ElConstants.SESSION_USERID);
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
			//若需要岗位作为查询条件之一，则删除以下代码
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
			/////////
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
		elUsers = userDao.listUsers(department, station, sub_department, elUser,
				getPageNow(), getPageSize());
		count = userDao.listUsersSize(department, station,sub_department, elUser);
		roles = roleDao.listRoles();
		return "account_result";
	}

	/*
	 * public String account_search() throws ElException { // getPageSize() =
	 * getPageSize() == 0 ? 10 : getPageSize(); if(0 < roleid && role==null){
	 * role = roleDao.getRoleById(roleid); } role = role ==null?new
	 * ElRole():role; roleid = role.getId();
	 * 
	 * 
	 * if(department==null){ //department=new Department(1); //department=new
	 * Department(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
	 * if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){ department=new
	 * Department(1); }else{ department=new Department(-2); deprTree =
	 * departmentDao.getDepTree( getSessionIntValue(ElConstants.SESSION_USERID),
	 * "op", -1, true); } elUser=new ELUser(); elUser.setValid(true);
	 * elUser.setNov(1); sub_department=1; }else{ if(department.getId()==-2){ //
	 * department.setId(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
	 * deprTree = departmentDao.getDepTree(
	 * getSessionIntValue(ElConstants.SESSION_USERID), "op", -1, true); } } if
	 * (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) // depTree =
	 * departmentDao.getDepTree( //
	 * getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, // true);
	 * depTree = departmentDao.getDepTree_level1(
	 * getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true); else { //
	 * depTree = departmentDao.getDepTree( //
	 * getSessionIntValue(ElConstants.SESSION_USERID), "op", -1, // true);
	 * depTree = departmentDao.getDepTree_level1(
	 * getSessionIntValue(ElConstants.SESSION_USERID), "op", -1, true); }
	 * if(elUser!=null){ if(elUser.getValid2()==0){ elUser.setValid(true);
	 * elUser.setNov(1); }else if(elUser.getValid2()==1){ elUser.setValid(true);
	 * elUser.setNov(0); }else{ elUser.setValid(false); elUser.setNov(0); } } if
	 * (roleid == 0) { //按部门id获取所有角色的用户。在getUserByDepId方法上使用ctrl+t
	 * 找到UserDao实现类。在UserDaoImpl.java if (department.getId() == -2 &&
	 * getSessionIntValue(ElConstants.SESSION_ROLE) != 1) { if(exprot ==
	 * true){//导出 // elUsers =
	 * userDao.getUserByUserId(getSessionIntValue(ElConstants.SESSION_USERID), //
	 * elUser, 10000000,0); elUsers =
	 * userDao.getUserByUserId3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
	 * elUser, 10000000,1); getResponse().reset();
	 * getResponse().setHeader("Content-disposition","attachment;
	 * filename=user.xls");
	 * getResponse().setContentType("application/vnd.ms-excel"); try { String
	 * titles[] = {"用户名","密码(不能修改此列,新增用户密码不能填写，密码默认是123456)","序号","姓名" ,"性别",
	 * "地市","身份证", "职级" ,"职务","警种","部门编号"}; String attrs[]=
	 * {"username","password","xuhao","realname","sex","dishi_","shenfenzheng",
	 * "zhiji_" ,"zhiwu_","jingzhong_","department.bh"}; new
	 * ExcelOutPut().writeExcel("用户表",getResponse().getOutputStream(),titles,
	 * ELUser.class.getName(), elUsers, attrs); } catch (Exception e) { } //
	 * return "account_search_Excel"; return null; } elUsers =
	 * userDao.getUserByUserId3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
	 * elUser, getPageNow(), getPageSize()); count =
	 * userDao.getUserByUserIdSize3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
	 * elUser); // elUsers =
	 * userDao.getUserByUserId(getSessionIntValue(ElConstants.SESSION_USERID), //
	 * elUser, getPageNow(), getPageSize()); // count =
	 * userDao.getUserByUserIdSize(getSessionIntValue(ElConstants.SESSION_USERID), //
	 * elUser); } else { if(exprot == true){//导出 elUser.setNov(1); elUsers =
	 * userDao.getUserByDepId2(department.getId(), sub_department, elUser);
	 * getResponse().reset();
	 * getResponse().setHeader("Content-disposition","attachment;
	 * filename=user.xls");
	 * getResponse().setContentType("application/vnd.ms-excel"); try { String
	 * titles[] = {"用户名","密码(不能修改此列,新增用户密码不能填写，密码默认是123456)","序号","姓名" ,"性别",
	 * "地市","身份证", "职级" ,"职务","警种","部门编号"}; String attrs[]=
	 * {"username","password","xuhao","realname","sex","dishi_","shenfenzheng",
	 * "zhiji_" ,"zhiwu_","jingzhong_","department.bh"}; new
	 * ExcelOutPut().writeExcel("用户表",getResponse().getOutputStream(),titles,
	 * ELUser.class.getName(), elUsers, attrs); } catch (Exception e) { } return
	 * null; // return "account_search_Excel"; } elUsers =
	 * userDao.getUserByDepId2(department.getId(), sub_department, elUser,
	 * getPageNow(), getPageSize()); count =
	 * userDao.getUserByDepIdSize2(department.getId(), sub_department, elUser); } }
	 * else { //按部门id获取所有角色的用户。在getUserByDepId方法上使用ctrl+t
	 * 找到UserDao实现类。在UserDaoImpl.java if (department.getId() == -2 &&
	 * getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {//部门id可能为-2吗？（根节点为1）
	 * if(exprot == true){//导出 // elUsers =
	 * userDao.getUserByUserId(getSessionIntValue(ElConstants.SESSION_USERID), //
	 * elUser, 10000000,0); elUsers =
	 * userDao.getUserByUserId3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
	 * elUser, 10000000,1); return "account_search_Excel"; } elUsers =
	 * userDao.getUserByUserId3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
	 * elUser, getPageNow(), getPageSize()); count =
	 * userDao.getUserByUserIdSize3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
	 * elUser); // elUsers =
	 * userDao.getUserByUserId(getSessionIntValue(ElConstants.SESSION_USERID), //
	 * elUser, getPageNow(), getPageSize()); // count =
	 * userDao.getUserByUserIdSize(getSessionIntValue(ElConstants.SESSION_USERID), //
	 * elUser); } else { //按部门id获取指定角色的用。 // elUsers = userDao //
	 * .getUserByDepId(department.getId(), sub_department, elUser, // roleid,
	 * getPageNow(), getPageSize()); // count =
	 * userDao.getUserByDepIdSize(department.getId(), // sub_department, elUser,
	 * roleid); if(exprot == true){//导出 elUsers = userDao
	 * .getUserByDepId2(department.getId(), sub_department, elUser, roleid);
	 * return "account_search_Excel"; } elUsers = userDao
	 * .getUserByDepId2(department.getId(), sub_department, elUser, roleid,
	 * getPageNow(), getPageSize()); count =
	 * userDao.getUserByDepIdSize2(department.getId(), sub_department, elUser,
	 * roleid); } } // depTree = departmentDao.getDepTree( //
	 * getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true); roles =
	 * roleDao.listRoles(); return "account_result"; }
	 */

	public String displayNoValidUser() throws ElException {
		if (department == null) {
			department = departmentDao.getDepById(1);
		}
		if(station == null){
			station = stationDao.getStById(1);
		}
		if (elUser == null) {
			elUser = new ELUser();
			elUser.setValid2(2);
		}
		elUsers = userDao.listUsers(department,station, 1, elUser, getPageNow(),
				getPageSize());
		count = userDao.listUsersSize(department, station,1, elUser);
		// }
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		return "noValidUser_list";
	}

	public String assignUser() throws ElException {
		// 1获取传过来的id
		// 2获取传过来的状态
		String userids = getRequest().getParameter("userids");
		String status = getRequest().getParameter("status");
		String resultPage = getRequest().getParameter("resultPage");
		if (userids != null) {
			String[] useridss = userids.split(",");
			for (int i = 0; i < useridss.length; i++) {
				if (status != null && Integer.parseInt(status) == 1) {
					// 开通
					userDao.updateValid(Integer.parseInt(useridss[i]), 1);
				} else {
					// 关闭
					if (Integer.parseInt(useridss[i]) != getSessionIntValue(ElConstants.SESSION_USERID)) {// 不能关闭自己
						userDao.updateValid(Integer.parseInt(useridss[i]), 0);
					}
				}
			}
		}
		// return "account_result2";
		if ("2".equals(resultPage)) {
			return this.displayNoValidUser();
		}
		if (type == 1) {
			return "userlist";
		}
		return this.account_search();
	}
	public String assignUser2() throws ElException {
		//1获取传过来的id
		//2获取传过来的状态
		String userids=getRequest().getParameter("userids");
		String status=getRequest().getParameter("status");
		String resultPage=getRequest().getParameter("resultPage");
		//System.out.println(userids);
		//System.out.println(status);
		if(userids!=null){
			String[] useridss=userids.split(",");
			for (int i = 0; i < useridss.length; i++) {
				if(status!=null&&Integer.parseInt(status)==1){
					//开通
					userDao.updateValid(Integer.parseInt(useridss[i]), 1);
					user = userDao.getUserById(Integer.parseInt(useridss[i]));
					if(user.getRole().getName().equals("准开通") || user.getRole().getName().equals("初审通过")){
						userDao.alterUserRole(user, 4);
					}
				}else{
					//关闭
					userDao.updateValid(Integer.parseInt(useridss[i]), 0);
				}
			}
		} 
		return this.account_search2();
	}

	public String delUser() throws ElException {
		// 1获取传过来的id
		String userids = getRequest().getParameter("userids");
		if (userids != null) {
			String[] useridss = userids.split(",");
			for (int i = 0; i < useridss.length; i++) {
				if (Integer.parseInt(useridss[i]) != getSessionIntValue(ElConstants.SESSION_USERID)) {// 不能删除自己
					// 然后判断是否真删除
			//		if (userDao.checkElUserIsUse(Integer.parseInt(useridss[i]))) {
						// 假删除(就是关闭)
			//			userDao.updateValid(Integer.parseInt(useridss[i]), 0);
			//		} else {
						// 真删除
						userDao.delete(Integer.parseInt(useridss[i]));
			//		}
				}
			}
		}
		return this.account_search();
	}
	
	public String delUser2() throws ElException {
		// 1获取传过来的id
		String userids = getRequest().getParameter("userids");
		if (userids != null) {
			String[] useridss = userids.split(",");
			for (int i = 0; i < useridss.length; i++) {
				if (Integer.parseInt(useridss[i]) != getSessionIntValue(ElConstants.SESSION_USERID)) {// 不能删除自己
					// 然后判断是否真删除
			//		if (userDao.checkElUserIsUse(Integer.parseInt(useridss[i]))) {
						// 假删除(就是关闭)
			//			userDao.updateValid(Integer.parseInt(useridss[i]), 0);
			//		} else {
						// 真删除
						userDao.delete(Integer.parseInt(useridss[i]));
			//		}
				}
			}
		}
		return this.account_search2();
	}

	public String account_view() throws ElException {
		elUser = userDao.getUserById(elUser.getId());
		StationDaoImpl staDao  =new StationDaoImpl();
		Station sta = staDao.getStById(elUser.getStaid());
		int age = 0;
		if(elUser.getShengri_()!=null){
			String grantTime = new SimpleDateFormat("yyyy")
			.format(elUser.getShengri_());
			Calendar ca = Calendar.getInstance();
			int year = ca.get(Calendar.YEAR);
			int sr = Integer.parseInt(grantTime);
			age = year-sr;
		}
		elUser.setAge(age);
		elUser.setXianzhiwei(sta.getName());
		Station station = stationDao.getStById(elUser.getStation().getId());
		String s = null;
		for(int i=0;i<10;i++){
			if(station.getParent().getId()!=1){
				s = stationDao.getStById(station.getParent().getId()).getName();
				station = stationDao.getStById(station.getParent().getId());
			}else{
				break;
			}
			if(stajiegou!=null){
				stajiegou = s+"-"+stajiegou;
			}else{
				stajiegou = s;
			}
			
		}
		return "account_view";
	}
	
	public String account_view2() throws ElException {
		elUser = userDao.getUserById2(elUser.getId());
		return "account_view2";
	} 
	public String account_view_cisco() throws ElException {
		elUser = userDao.getUserById_cisco(elUser.getId());
		return "account_view_cisco";
	} 

	public String account_alterInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs = userDao.getBaseDatatByTypeid(1);
			zhiwus = userDao.getBaseDatatByTypeid(2);
			zhijis = userDao.getBaseDatatByTypeid(3);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5);
			luntanjibies = userDao.getBaseDatatByTypeid(6);
		} else {
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			jingzhongs = userDao.getBaseDatatByTypeid(1, userid);
			zhiwus = userDao.getBaseDatatByTypeid(2, userid);
			zhijis = userDao.getBaseDatatByTypeid(3, userid);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5, userid);
			luntanjibies = userDao.getBaseDatatByTypeid(6, userid);
		}
		if (elUser.getId() == getSessionIntValue(ElConstants.SESSION_USERID)) {
			return "noright";
		}
		elUser = userDao.getUserById(elUser.getId());
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// else {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }
		// roles = roleDao.listRoles();
		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		// 判断注册信息是否都要验证
		String resultPage = "account_alter";
		if (SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
			getRequest().setAttribute("isAll", "yes");
			// resultPage="account_alter";
		} else {
			getRequest().setAttribute("isAll", "no");
			// resultPage="account_alter_noall";
		}
		// return "account_alter";
		Station station = stationDao.getStById(elUser.getStation().getId());
		String s = null;
		for(int i=0;i<10;i++){
			if(station.getParent().getId()!=1){
				s = stationDao.getStById(station.getParent().getId()).getName();
				station = stationDao.getStById(station.getParent().getId());
			}else{
				break;
			}
			if(stajiegou!=null){
				stajiegou = s+"-"+stajiegou;
			}else{
				stajiegou = s;
			}
			
		}
		return resultPage;
	}
	
	public String account_alterInit2() throws ElException { 
		userRole = getSessionIntValue(ElConstants.SESSION_ROLE);
		if(userRole != 1){
			elUser = userDao.getUserById2(elUser.getId());
			if(!elUser.getRole().getName().equals("准开通")  && !elUser.getRole().getName().equals("初审通过")){	
				setElmessage(" 此用户信息经系统管理员审核，您不能再修改了！");
				return "error";	
			}  
		} 
		if (elUser.getId() == (Integer) getSession().getAttribute("userId")) {
			return "noright";
		}
		elUser = userDao.getUserById2(elUser.getId());
	//	company = departmentDao.getCompanyById(getSessionIntValue(ElConstants.SESSION_MYCOMPANY)); 
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}  
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			roles = roleDao.listRoles(); 
		}else{ 
			roles = new ArrayList<ElRole>(); 
			role = roleDao.getRoleByName("准开通");
			if(role != null && role.getId() != 0){
				roles.add(role);
			}
			role = null;
			role = roleDao.getRoleByName("初审通过");
			if(role != null && role.getId() != 0){
				roles.add(role);
			}
		}
		return "account_alter2";
	}
	
	public String account_alterInit_cisco() throws ElException { 
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs = userDao.getBaseDatatByTypeid(1);
			zhiwus = userDao.getBaseDatatByTypeid(2);
			zhijis = userDao.getBaseDatatByTypeid(3);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5);
			luntanjibies = userDao.getBaseDatatByTypeid(6);
		} else {
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			jingzhongs = userDao.getBaseDatatByTypeid(1, userid);
			zhiwus = userDao.getBaseDatatByTypeid(2, userid);
			zhijis = userDao.getBaseDatatByTypeid(3, userid);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5, userid);
			luntanjibies = userDao.getBaseDatatByTypeid(6, userid);
		}
		if (elUser.getId() == getSessionIntValue(ElConstants.SESSION_USERID)) {
			return "noright";
		}
		elUser = userDao.getUserById_cisco(elUser.getId());
		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		return "account_alterInit_cisco";
	}
	public String account_alter_cisco() throws ElException{
		System.out.println(elUser.getStation().getId());
		//elUser.setPassword(MD5.crypt(elUser.getPassword()));
		userDao.update_cisco(elUser);
		if (elUser.getPassword() != null
				&& !"".equals(elUser.getPassword().trim())) {
			elUser.setPassword(MD5.crypt(elUser.getPassword()));
			userDao.alterMyPwd(elUser);
		}
		elUser = userDao.getUserById_cisco(elUser.getId()); 
		this.upOk=1;
		return "account_alter_cisco_success";
	}
	
	public String account_alter2() throws ElException {
		if (elUser.getId() == getSessionIntValue(ElConstants.SESSION_USERID)) {
			return "noright";
		}   
		userDao.update2(elUser);
//		elUser = userDao.getUserById(elUser.getId());
//		if(!userDao.checkHasFblock(elUser.getId()))
//		if (elUser.getRole().getId() == 6) {
//			ForumAdminDaoImpl ai = new ForumAdminDaoImpl();
//			ForumBlock fb = new ForumBlock();
//			fb.setTitle("专家--" + elUser.getRealname());
//			fb.setManager(elUser);
//			fb.setFbtype(new ForumBlockType(1));
//			ai.addFblock(fb);
//		} 
		if(elUser.getPassword()!=null&&!"".equals(elUser.getPassword().trim()))
		{
			elUser.setPassword(MD5.crypt(elUser.getPassword()));
			userDao.alterMyPwd(elUser);
		}
		elUser = userDao.getUserById2(elUser.getId()); 
		this.upOk=1;
		return "account_alter_success2";
	}
	
	public String account_headerAlterInit() throws ElException {
		elUser = userDao.getUserById(elUser.getId());
		return "account_headerAlter";
	}
	public String account_headerAlter() throws ElException, Exception {
		if (st.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "account_headerAlter";
		} else {
			String ext = J2EEFileUtil.getExtention(stFileName);
			int id = elUser.getId();
			elUser = new ELUser(id);
			elUser.setHeadPhoto(ext);
			userDao.alterUserHead(elUser);
			J2EEFileUtil.upload_was(st, ext, "elheaders", id + "");
//			J2EEFileUtil.upload(st, ext, "elheaders", id + "");
		} 
		return "account_view2";
	}

	public String account_alter() throws ElException {
		if (elUser.getId() == getSessionIntValue(ElConstants.SESSION_USERID)) {
			return "noright";
		}
		// HttpServletRequest requset = ServletActionContext.getRequest();
		// if ("1".equals(requset.getParameter("is_teacher"))) {
		// elUser.setRole(new ElRole(3));
		// }
		// if(userDao.checkUserShenfenzheng(elUser.getShenfenzheng(),elUser.getId())){//原来的代码
		if (userDao.checkUserShenfenzhengIsUniqune(elUser.getUsername(), elUser
				.getId())) {// 当输入框中的用户名在数据库中存在时，提示用户重新输入
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
				jingzhongs = userDao.getBaseDatatByTypeid(1);
				zhiwus = userDao.getBaseDatatByTypeid(2);
				zhijis = userDao.getBaseDatatByTypeid(3);
				gangweis = userDao.getBaseDatatByTypeid(4);
				dishis = userDao.getBaseDatatByTypeid(5);
				luntanjibies = userDao.getBaseDatatByTypeid(6);
			} else {
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				jingzhongs = userDao.getBaseDatatByTypeid(1, userid);
				zhiwus = userDao.getBaseDatatByTypeid(2, userid);
				zhijis = userDao.getBaseDatatByTypeid(3, userid);
				gangweis = userDao.getBaseDatatByTypeid(4);
				dishis = userDao.getBaseDatatByTypeid(5, userid);
				luntanjibies = userDao.getBaseDatatByTypeid(6, userid);
			}
			setElmessage("您修改的用户所用的身份证已被其他人使用，请重新输入！");
			// roles = roleDao.listRoles();
			roles = roleDao.listRoles(
					getSessionIntValue(ElConstants.SESSION_ROLE),
					getSessionIntValue(ElConstants.SESSION_USERID));
			// 判断注册信息是否都要验证
			if (SystemConfOp
					.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL)) {
				getRequest().setAttribute("isAll", "yes");
			} else {
				getRequest().setAttribute("isAll", "no");
			}
			elUser = userDao.getUserById(elUser.getId());
			return "account_alter";// 返回修改页面
		}
		elUser.setDanwei(departmentDao.getDepById(elUser.getDepartment().getId()).getName());
		userDao.update(elUser);
		if (elUser.getPassword() != null
				&& !"".equals(elUser.getPassword().trim())) {
			elUser.setPassword(MD5.crypt(elUser.getPassword()));
			userDao.alterMyPwd(elUser);
		}
		elUser = userDao.getUserById(elUser.getId());
		// if (!userDao.checkHasFblock(elUser.getId()))
		// if (elUser.getRole().getId() == 6) {
		// ForumAdminDaoImpl ai = new ForumAdminDaoImpl();
		// ForumBlock fb = new ForumBlock();
		// fb.setTitle("专家--" + elUser.getRealname());
		// fb.setManager(elUser);
		// fb.setFbtype(new ForumBlockType(1));
		// ai.addFblock(fb);
		// }
		// return "account_alter_success";
		// getRequest().setAttribute("upOk", 1);//用来提示 修改成功！
		// this.upOk=1;
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_ELUSER,
				ElLoggerConstants.LOG_TYPE_ALTER, elUser.getUsername(),
				ElLoggerConstants.LOG_RES_SUCC, elUser.getId());
		return "account_view";
	}

	public String account_deleteInit() throws ElException {
		if (elUser.getId() == getSessionIntValue(ElConstants.SESSION_USERID)) {
			return "noright";
		}
		elUser = userDao.getUserById(elUser.getId());
		return "account_delete";
	}

	public String account_delete() throws ElException {
		if (elUser.getId() == getSessionIntValue(ElConstants.SESSION_USERID)) {
			return "noright";
		}
		userDao.delete(elUser.getId());
		elUser = userDao.getUserById(elUser.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_ELUSER,
				ElLoggerConstants.LOG_TYPE_DELETE, elUser.getUsername(),
				ElLoggerConstants.LOG_RES_SUCC, elUser.getId());
		return "account_delete_success";
	}

	public String role_list() throws ElException {
		// roles = roleDao.listRoles();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			roles = roleDao.listRoles();
		} else {
			roles = roleDao
					.listMyRoles(getSessionIntValue(ElConstants.SESSION_USERID));
		}

		return "role_list";
	}

	public String role_delete() throws ElException {
		// roles = roleDao.listRoles();
		//sd1230
		if(SystemConfOp.getIntValue(ElConstants.SYSTEM_SD) != 1){
		if (role.getId() <= 7) {
			setElmessage("对不起，系统默认的角色不能删除");
			roles = roleDao.listRoles();
			return "role_delete_error";
		} else {
			roleDao.deleteRole(role.getId());
		}
		}else{
			if (role.getId()==1 || role.getId()==4) {
				setElmessage("对不起，系统默认的角色不能删除");
				roles = roleDao.listRoles();
				return "role_delete_error";
			} else {
				roleDao.deleteRole(role.getId());
			}
		}
		role = roleDao.getRoleById(role.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_ROLE,
				ElLoggerConstants.LOG_TYPE_DELETE, role.getName(),
				ElLoggerConstants.LOG_RES_SUCC, role.getId());
		return "role_list";
	}

	public String role_addInit() throws ElException {

		return "role_add";
	}

	public String role_alterInit() throws ElException {
		role = roleDao.getRoleById(role.getId());
		return "role_alter";
	}

	public String role_alter() throws ElException {
		roleDao.alterRole(role);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_ROLE, ElLoggerConstants.LOG_TYPE_ADD,
				role.getName(), ElLoggerConstants.LOG_RES_SUCC, role.getId());
		return "role_list";
	}

	public String rolefunc_addInit() throws ElException {
		// 非超级管理员只能分配自己所具有的功能给其他角色
		// funcTree = roleDao.getFuncTree();
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
		return "rolefunc_add";
	}

	public String rolefunc_add() throws ElException {
		roleDao.addRoleFunc(role);
		AuthorityUtil.load();
		AuthorityNewVersionUtil.load();
		return "role_list";
	}

	// 用户直接权限分配（不经过角色）
	public String userRole() throws ElException {
		// funcTree = roleDao.getFuncTree();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			funcTree = roleDao.getFuncTree();
		} else {
			funcTree = roleDao
					.getFuncTreeByRoleId(getSessionIntValue(ElConstants.SESSION_ROLE));
		}
		if (funcTree != null && funcTree.getChild() != null) {
			funcTree.setCount(funcTree.getChild().size());
		}
		// role = roleDao.getRoleById(1);
		elUser = userDao.getUserById(elUser.getId());
		return "userRole_Select";
	}

	public String userRole_Select() throws ElException {

		roleDao.addUserRoleFunc(role, elUser.getId());
		// AuthorityUtil.load();

		return "userRole";
	}

	public String func_list() throws ElException {
		funcTree = roleDao.getFuncTree();

		return "func_list";
	}

	public String func_addInit() throws ElException {
		funcTree = roleDao.getFuncTree();

		return "func_add";
	}

	public String func_delete() throws ElException {
		// funcTree = roleDao.getFuncTree();
		roleDao.deleteFunc(func.getId());
		return "func_list";
	}

	public String func_add() throws ElException {
		roleDao.addFunc(func);
		AuthorityUtil.load();
		return "func_list";
	}

	public String func_alterInit() throws ElException {
		func = roleDao.getFuncById(func.getId());
		funcTree = roleDao.getFuncTree();
		return "func_alter";
	}

	public String func_alter() throws ElException {
		roleDao.alterFunc(func);
		AuthorityUtil.load();
		return "func_list";
	}

	public String role_add() throws ElException {
		// roleDao.addRole(role);
		role.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		role.setId(roleDao.addRole(role));
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_ROLE, ElLoggerConstants.LOG_TYPE_ADD,
				role.getName(), ElLoggerConstants.LOG_RES_SUCC, role.getId());
		return "role_add_success";
	}

	public String group_list() throws ElException {
		// 组管理
		groups = roleDao.listGroups();
		return "group_list";
	}

	public String group_addInit() throws ElException {

		return "group_add";
	}

	public String group_add() throws ElException {
		roleDao.addGroup(group);
		return "group_list";
	}

	public String group_alterInit() throws ElException {
		group = roleDao.getGroupById(group.getId());
		return "group_alter";
	}

	public String group_alter() throws ElException {
		roleDao.alterGroup(group);
		return "group_list";
	}

	public String group_delete() throws ElException {
		roleDao.deleteGroup(group.getId());
		return "group_list";
	}

	/*
	 * public String group_assign2userInit() throws ElException { // 组管理 groups =
	 * roleDao.listGroups(); return "group_assign2user"; }
	 */
	public String group_assign_list() throws ElException {
		// 组管理
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		assignedUsers = roleDao.listAssignUsers(group.getId(), getPageNow(),
				getPageSize());
		count = roleDao.listAssignUsersSize(group.getId());
		return "group_assign_list";
	}

	public String group_assign_search() throws ElException {
		group = roleDao.getGroupById(group.getId());
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}

		return "group_assign_search";
	}

	public String group_assign_search_list() throws ElException {
		// group = roleDao.getGroupById(group.getId());
		// // getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		// elUsers = userDao.getUserByDepId(department.getId(), sub_department,
		// elUser, getPageNow(), getPageSize());
		// if (elUsers != null) {
		// for (int i = 0; i < elUsers.size(); i++) {
		// if (roleDao.checkUserIngroup(elUsers.get(i).getId(), group
		// .getId())) {
		// elUsers.get(i).setIntroom(true);
		// }
		// }
		// }
		// count = userDao.getUserByDepIdSize(department.getId(),
		// sub_department,
		// elUser);
		// // depTree = departmentDao.getDepTree(
		// // getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// else {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }

		return "group_assign_search_list";
	}

	public String group_assign_add() throws ElException {
		// group = roleDao.getGroupById(group.getId());
		// if (elUsers != null) {
		// for (int i = 0; i < elUsers.size(); i++) {
		// if (!roleDao.checkUserIngroup(elUsers.get(i).getId(), group
		// .getId())) {
		// roleDao.groupAssign2User(elUsers.get(i).getId(), group
		// .getId());
		// }
		// }
		// }
		// // getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		// elUsers = userDao.getUserByDepId(department.getId(), sub_department,
		// elUser, getPageNow(), getPageSize());
		// if (elUsers != null) {
		// for (int i = 0; i < elUsers.size(); i++) {
		// if (roleDao.checkUserIngroup(elUsers.get(i).getId(), group
		// .getId())) {
		// elUsers.get(i).setIntroom(true);
		// }
		// }
		// }
		// count = userDao.getUserByDepIdSize(department.getId(),
		// sub_department,
		// elUser);
		// // depTree = departmentDao.getDepTree(
		// // getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// else {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }

		return "group_assign_search_list";
	}

	public String group_assign_delete() throws ElException {
		if (elUser != null) {
			roleDao.groupUnAssign2User(elUser.getId(), group.getId());
		}
		return "group_assign_list";
	}

	public String student_headerAlterInit() throws ElException {

		return "student_headerAlter";
	}
	public String student_headerAlter() throws ElException, Exception {
		if (st.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "account_headerAlter";
		} else {
			String ext = J2EEFileUtil.getExtention(stFileName);
			int id = getSessionIntValue(ElConstants.SESSION_USERID);
			elUser = new ELUser(id);
			elUser.setHeadPhoto(ext);
			userDao.alterUserHead(elUser);
			J2EEFileUtil.upload_was(st, ext, "elheaders", id + "");
//			J2EEFileUtil.upload(st, ext, "elheaders", id + "");
		} 
		return "student_myinfo2";
	} 

//	public String student_headerAlter() throws ElException, Exception {
		// if (st.length() > 10 * 1024 * 1024) {
		// setElmessage("您上传的文件过大！");
		// return "student_headerAlter";
		// } else {
		// String ext = J2EEFileUtil.getExtention(stFileName);
		// int id = getSessionIntValue(ElConstants.SESSION_USERID);
		// elUser = new ELUser(id);
		// // elUser.setHeadPhoto(ext);
		// userDao.alterUserHead(elUser);
		// J2EEFileUtil.upload(st, ext, "elheaders", id + "");
		// }

//		return "student_myinfo";
//	}

	private QuestionLib qlbTree;// 试题库树
	private List treeAllId;
	private String treeType;
	private CourseType ctypeTree;// 课程类型树
	private ExamPaperLib eplTree;// 试卷库树
	private ElClType cltypeTree;// 培训班类型树
	private EroomLib eroomLibTree;// 考场树
	private StuffLib stuffTree;// 素材树
	private Word wordsTree;//词汇树
	private NewsType ntypeTree;
	private KnowledgeType kltypeTree;
	private List<ForumBlockType> fbtypes;

	public Word getWordsTree() {
		return wordsTree;
	}

	public void setWordsTree(Word wordsTree) {
		this.wordsTree = wordsTree;
	}

	public QuestionLib getQlbTree() {
		return qlbTree;
	}

	public void setQlbTree(QuestionLib qlbTree) {
		this.qlbTree = qlbTree;
	}

	public List getTreeAllId() {
		return treeAllId;
	}

	public void setTreeAllId(List treeAllId) {
		this.treeAllId = treeAllId;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public CourseType getCtypeTree() {
		return ctypeTree;
	}

	public void setCtypeTree(CourseType ctypeTree) {
		this.ctypeTree = ctypeTree;
	}

	public ExamPaperLib getEplTree() {
		return eplTree;
	}

	public void setEplTree(ExamPaperLib eplTree) {
		this.eplTree = eplTree;
	}

	public ElClType getCltypeTree() {
		return cltypeTree;
	}

	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}

	public EroomLib getEroomLibTree() {
		return eroomLibTree;
	}

	public void setEroomLibTree(EroomLib eroomLibTree) {
		this.eroomLibTree = eroomLibTree;
	}

	public StuffLib getStuffTree() {
		return stuffTree;
	}

	public void setStuffTree(StuffLib stuffTree) {
		this.stuffTree = stuffTree;
	}

	public NewsType getNtypeTree() {
		return ntypeTree;
	}

	public void setNtypeTree(NewsType ntypeTree) {
		this.ntypeTree = ntypeTree;
	}

	public KnowledgeType getKltypeTree() {
		return kltypeTree;
	}

	public void setKltypeTree(KnowledgeType kltypeTree) {
		this.kltypeTree = kltypeTree;
	}

	public List<ForumBlockType> getFbtypes() {
		return fbtypes;
	}

	public void setFbtypes(List<ForumBlockType> fbtypes) {
		this.fbtypes = fbtypes;
	}

	public String showUserGrant() throws ElException {
		QuestionDao questionDao = new QuestionDaoImpl();
		CourseTypeDao ctypeDao = new CourseTypeDaoImpl();
		ExamPaperDao examPaperDao = new ExamPaperDaoImpl();
		ElClTypeDao elClTypeDao = new ElClTypeDaoImpl();
		EroomDao eroomDao = new EroomDaoImpl();
		NewsDao newsDao = new NewsDaoImpl();
		KnowledgeDao knowledgeDao = new KnowledgeDaoImpl();
		ForumAdminDao forumAdminDao = new ForumAdminDaoImpl();
		WordDao wordDao = new WordDaoImpl();
		elUser = userDao.getUserById(elUser.getId());// 获取用户信息
		// 题库树
		if (elUser.getRole().getId() == 1) {
		} else {
			qlbTree = questionDao.getQlibTree(elUser.getId(), "op", -1, true);
		}
		// 课程树
		ctypeTree = ctypeDao.getCourseLibTree(elUser.getId(), "op",
				ElConstants.TREE_FIANL, true);
		// 试卷库树
		eplTree = examPaperDao.epLibTree("op", elUser.getId(), -1, true);
		// 培训班类型树
		cltypeTree = elClTypeDao.getClassLibTree(elUser.getId(), "op",
				ElConstants.TREE_FIANL, true);
		// 考场树
		eroomLibTree = eroomDao.getEroomLibTree(elUser.getId(), "op",
				ElConstants.TREE_FIANL, true);
		//词汇树
		wordsTree = wordDao.getWordsTree(elUser.getId(), "op", ElConstants.TREE_FIANL, true);
		// 部门树
		if (departmentDao == null) {
			departmentDao = new DepartmentDaoImpl();
		}
		depTree = departmentDao.getDepTree_level1(elUser.getId(), "op", -1,
				true);

		depUserableTree = departmentDao.getDepTree_level1(elUser.getId(),
				"use", -1, true);
		//岗位树
		if(stationDao == null){
			stationDao = new StationDaoImpl();
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					elUser.getId(),"op", -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					elUser.getId(), "use", -1,
					true);
		}
		// 素材树
		stuffTree = ((StuffDao) SpringContextUtil.getBean("stuffDao"))
				.getStuffFolderTree(elUser.getId());
		// 新闻数
		// NewsType newsTypeTree=new NewsDaoImpl().getNtypeTree(userid, op,
		// stopid, containStop)
		ntypeTree = newsDao.getNtypeTree(elUser.getId(), "op",
				ElConstants.TREE_FIANL, true);
		// 知识
		kltypeTree = knowledgeDao.getKnowledgeLibTree(elUser.getId(), "op",
				ElConstants.TREE_FIANL, true);
		// 论坛--只有可使用
		fbtypes = forumAdminDao.listFbtypes();
		if (null != fbtypes) {
			List<ForumBlock> list = null;
			for (int i = 0; i < fbtypes.size(); i++) {
				list = forumAdminDao.fblockByPerOrShare(fbtypes.get(i).getId(),
						elUser.getId(), false);
				fbtypes.get(i).setFblocks(list);
			}
		}
		// 该用户的功能权限树
		funcTree = roleDao.getFuncTreeByRoleId(elUser.getRole().getId());
		/*
		 * new ElFunc(0); funcTree.setName("权限");
		 * funcTree.setChild(roleDao.getMenus(0,elUser.getRole().getId(),elUser.getId(),true));//
		 */
		role = roleDao.getRoleById(elUser.getRole().getId());

		// 用户功能树
		userFuncTree = roleDao.getFuncTreeByUserid(elUser.getId());
		return "showUserGrant";
	}

	/**
	 * 用户授权初始化
	 * 
	 * @return
	 */
	public String userGrantManageInit() throws ElException {
		// 获取树的类型
		// String treeType=getRequest().getParameter("treeType");
		// 1.获取session中的用户id
		// 2.根据此id查询出该用户信息以及所能管理的树显示到页面
		System.out.println(treeType);
		elUser = userDao.getUserById(elUser.getId());
		if ("qlib".equals(treeType)) {
			if (qlbTree == null) {
				qlbTree = new QuestionLib();
			}
			QuestionDao questionDao = new QuestionDaoImpl();
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
				qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElConstants.TREE_FIANL, true);
			} else {
				qlbTree = questionDao.getQlibTree(
						getSessionIntValue(ElConstants.SESSION_USERID), "op",
						-1, true);
			}
			QuestionLib userTree = questionDao.getQlibTree(elUser.getId(),
					"op", -1, true);
			// treeAllId=new ArrayList(0);
			treeAllId = userDao.getTreeAllId(userTree, true);// 用户已经授权的树的所有id，用来判断用户不可再次授权（解决给了父id授权后还可以给子id授权（此功能已用另一需求解决））(还解决了根据此树来设定checkbox默认值)
			// for (Object integer : treeAllId) {
			// }
		} else if ("ctyp".equals(treeType)) {
			if (ctypeTree == null) {
				ctypeTree = new CourseType();
			}
			CourseTypeDao ctypeDao = new CourseTypeDaoImpl();
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,
						ElConstants.TREE_FIANL, true);
			else {
				ctypeTree = ctypeDao.getCourseLibTree(
						getSessionIntValue(ElConstants.SESSION_USERID), "op",
						ElConstants.TREE_FIANL, true);
			}
			CourseType userTree = ctypeDao.getCourseLibTree(elUser.getId(),
					"op", ElConstants.TREE_FIANL, true);
			treeAllId = userDao.getTreeAllId(userTree, true);
		} else if ("elib".equals(treeType)) {
			if (eplTree == null) {
				eplTree = new ExamPaperLib();
			}
			ExamPaperDao examPaperDao = new ExamPaperDaoImpl();
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				eplTree = examPaperDao.epLibTree(0,
						getSessionIntValue(ElConstants.SESSION_USERID), -1,
						true);
			else {
				eplTree = examPaperDao.epLibTree("op",
						getSessionIntValue(ElConstants.SESSION_USERID), -1,
						true);
			}
			ExamPaperLib userTree = examPaperDao.epLibTree("op",
					elUser.getId(), -1, true);
			treeAllId = userDao.getTreeAllId(userTree, true);
		} else if ("clty".equals(treeType)) {
			if (cltypeTree == null) {
				cltypeTree = new ElClType();
			}
			ElClTypeDao elClTypeDao = new ElClTypeDaoImpl();
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
				cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,
						ElConstants.TREE_FIANL, true);
			} else {
				cltypeTree = elClTypeDao.getClassLibTree(
						getSessionIntValue(ElConstants.SESSION_USERID), "op",
						ElConstants.TREE_FIANL, true);
			}
			ElClType userTree = elClTypeDao.getClassLibTree(elUser.getId(),
					"op", ElConstants.TREE_FIANL, true);
			treeAllId = userDao.getTreeAllId(userTree, true);
		} else if ("eroo".equals(treeType)) {
			if (eroomLibTree == null) {
				eroomLibTree = new EroomLib();
			}
			EroomDao eroomDao = new EroomDaoImpl();
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
				eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
						ElConstants.TREE_FIANL, true);
			} else {
				eroomLibTree = eroomDao.getEroomLibTree(
						getSessionIntValue(ElConstants.SESSION_USERID), "op",
						ElConstants.TREE_FIANL, true);
			}
			EroomLib userTree = eroomDao.getEroomLibTree(elUser.getId(), "op",
					ElConstants.TREE_FIANL, true);
			treeAllId = userDao.getTreeAllId(userTree, true);
		} else if ("depl".equals(treeType)) {
			// if(depTree==null){
			// depTree=new Department();
			// }
			// departmentDao=new DepartmentDaoImpl();
			// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			// depTree =
			// departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
			// -1,true);
			// }
			// else {
			// depTree =
			// departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID),
			// "op", -1,true);
			// }
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
						-1, true);
			else {
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_USERID), "op",
						-1, true);
			}
			// Department userTree = departmentDao.getDepTree(elUser.getId(),
			// "op", -1,true);
//			 treeAllId=userDao.getTreeAllId(depTree,true);
			departments = departmentDao.getDepTree_level1(elUser.getId(), "op",
					-1, false).getChild();
		} else if ("use".equals(treeType)) {
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
						-1, true);
			else {
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_USERID), "use",
						-1, true);
			}
			departments = departmentDao.getDepTree_level1(elUser.getId(),
					"use", -1, false).getChild();
		}else if("st".equals(treeType)){
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				stTree = stationDao.getStTree_level1(
						getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
						true);
			else {
				stTree = stationDao.getStTree_level1(
						getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
						true);
			}
			stations = stationDao.getStTree_level1(elUser.getId(), "op", -1, false).getChild();
		}else if("wd".equals(treeType)){
			if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
				wordsTree = wordDao.getWordsTree(getSessionIntValue(ElConstants.SESSION_MYWORD),-1,true);
			}else{
				wordsTree = wordDao.getWordsTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
			}
	//		words = wordDao.getWordsTree(elUser.getId(),"op",-1,false).getChild();
			word  = wordDao.wdLibTree("op",elUser.getId(), -1, true);
			treeAllId = userDao.getTreeAllId(word, true);
		} else if ("stuf".equals(treeType)) {
			// if(stuffTree==null){
			// stuffTree=new StuffLib();
			// }
			// QuestionDao questionDao=new QuestionDaoImpl();
			if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
				stuffTree = ((StuffDao) SpringContextUtil.getBean("stuffDao"))
						.getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
			} else {
				stuffTree = ((StuffDao) SpringContextUtil.getBean("stuffDao"))
						.getStuffFolderTree();
			}
			StuffLib userTree = ((StuffDao) SpringContextUtil
					.getBean("stuffDao")).getStuffFolderTree(elUser.getId());
			treeAllId = userDao.getTreeAllId(userTree, true);
		} else if ("news".equals(treeType)) {
			if (ntypeTree == null) {
				ntypeTree = new NewsType();
			}
			NewsDao newsDao = new NewsDaoImpl();
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
						ElConstants.TREE_FIANL, true);
			else {
				ntypeTree = newsDao.getNtypeTree(
						getSessionIntValue(ElConstants.SESSION_USERID), "op",
						ElConstants.TREE_FIANL, true);
			}
			NewsType userTree = newsDao.getNtypeTree(elUser.getId(), "op",
					ElConstants.TREE_FIANL, true);
			treeAllId = userDao.getTreeAllId(userTree, true);
		} else if ("klty".equals(treeType)) {
			if (kltypeTree == null) {
				kltypeTree = new KnowledgeType();
			}
			KnowledgeDao knowledgeDao = new KnowledgeDaoImpl();
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
			else {
				kltypeTree = knowledgeDao.getKnowledgeLibTree(
						getSessionIntValue(ElConstants.SESSION_USERID), "op",
						ElConstants.TREE_FIANL, true);
			}
			KnowledgeType userTree = knowledgeDao.getKnowledgeLibTree(elUser
					.getId(), "op", ElConstants.TREE_FIANL, true);
			treeAllId = userDao.getTreeAllId(userTree, true);
		} else if ("bmsq".equals(treeType)) {
			ForumAdminDao forumAdminDao = new ForumAdminDaoImpl();
			fbtypes = forumAdminDao.listFbtypes();// 获取所有版块类别
			if (null != fbtypes) {
				List<ForumBlock> list = new ArrayList<ForumBlock>();
				List<ForumBlock> list2 = null;
				for (int i = 0; i < fbtypes.size(); i++) {
					// list2 =
					// forumAdminDao.fblockByPerOrShare(fbtypes.get(i).getId(),getSessionIntValue(ElConstants.SESSION_USERID),
					// false);
					// if(list2==null||list2.size()==0){
					// fbtypes.remove(i);
					// i--;
					// continue;
					// }
					// list =
					// forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId());
					if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
						list = forumAdminDao.listFbsByFbtid(fbtypes.get(i)
								.getId());
					} else {
						list = forumAdminDao.fblockByPerOrShare(fbtypes.get(i)
								.getId(),
								getSessionIntValue(ElConstants.SESSION_USERID),
								false);
					}
					list2 = forumAdminDao.fblockByPerOrShare(fbtypes.get(i)
							.getId(), elUser.getId(), false);

					// 比较2个集合的相同id
					for (int j = 0; j < list.size(); j++) {
						for (int j2 = 0; j2 < list2.size(); j2++) {
							if (list.get(j).getId() == list2.get(j2).getId()) {
								list.get(j).setIsChecked(1);
							}
						}
					}
					fbtypes.get(i).setFblocks(list);
				}
			}
			// KnowledgeType userTree =
			// knowledgeDao.getKnowledgeLibTree(elUser.getId(),
			// "op",ElConstants.TREE_FIANL, true);
			// treeAllId=userDao.getTreeAllId(userTree,true);
		}
		// 接下来判断是否存在以授权的树
		return "userGrantManageInit";
	}

	public String userGrant() throws ElException {
		// 1.获取所要操作的对象相关信息
		// 2.获取传过来的树类型及id集合
		// 3.对其进行授权，然后跳转到成功页面
		String[] chkstr = this.getRequest().getParameterValues("chkNames");
		// userDao.userGrantOnQlibTree(chkstr, elUser.getId(),treeAllId);
		// userDao.userGrantOnQlibTree(chkstr, elUser.getId(),treeType);
		// return this.showUserGrant();
		if ("depl".equals(treeType)) {
			departmentDao.deleteUserOpGrant(elUser.getId());
			if (chkstr != null) {
				departments = new ArrayList<Department>();
				for (int i = 0; i < chkstr.length; i++) {
					departments
							.add(new Department(Integer.parseInt(chkstr[i])));
				}
			}
			if (departments != null) {
				ElNodeSQL elnodesql = (ElNodeSQL) SpringContextUtil
						.getBean(ElConstants.CLASS_ELNODESQL);
				depTree = departmentDao.getDepTree_level1(elUser.getId(),
						"use", -1, true);
				for (int i = 0; i < departments.size(); i++) {
					departmentDao.addOpusers("op", elUser.getId(), departments
							.get(i).getId());
					// 判断可使用的树是否包含可操作里面的节点，如果不包含就自己加入到可使用的树中
					if (!elnodesql.checkNode(departments.get(i).getId(),
							depTree, "department")) {
						departmentDao.addOpusers("use", elUser.getId(),
								departments.get(i).getId());
					}
				}

				department = departmentDao.getDepTree_level1(elUser.getId(),
						"op", -1, true);
				depTree = departmentDao.getDepTree_level1(elUser.getId(),
						"use", -1, true);
				for (int i = 0; i < depTree.getChild().size(); i++) {
					// 判断可操作的树是否有节点包含（排除相等的情况，所以下面用到循环比较）可使用树里面的节点，如果包含就得删除可使用的树中那个节点，否则重复
					int j = 0;
					for (j = 0; j < department.getChild().size(); j++) {
						if (depTree.getChild().get(i).getId() == department
								.getChild().get(j).getId()) {
							break;
						}
					}
					if (j == department.getChild().size()) {
						if (elnodesql.checkNode(depTree.getChild().get(i)
								.getId(), department, "department")) {
							departmentDao.deleteUserOpOrUseGrant(
									elUser.getId(), "use", depTree.getChild()
											.get(i).getId());
						}
					}
				}
			}
		} else if("st".equals(treeType)){
			stationDao.deleteUserOpGrant(elUser.getId());
		//	departmentDao.deleteUserOpGrant(elUser.getId());
			if (chkstr != null) {
				stations = new ArrayList<Station>();
				for (int i = 0; i < chkstr.length; i++) {
					stations
							.add(new Station(Integer.parseInt(chkstr[i])));
				}
			}
			if (stations != null) {
				ElNodeSQL elnodesql = (ElNodeSQL) SpringContextUtil
						.getBean(ElConstants.CLASS_ELNODESQL);
				stTree = stationDao.getStTree_level1(elUser.getId(),
						"use", -1, true);
				for (int i = 0; i < stations.size(); i++) {
					stationDao.addOpusers("op", elUser.getId(), stations
							.get(i).getId());
				//	departmentDao.addOpusers("op", elUser.getId(), departments
				//			.get(i).getId());
					// 判断可使用的树是否包含可操作里面的节点，如果不包含就自己加入到可使用的树中
					if (!elnodesql.checkNode(stations.get(i).getId(),
							stTree, "station")) {
						stationDao.addOpusers("use", elUser.getId(),
								stations.get(i).getId());
					}
				}
				station = stationDao.getStTree_level1(elUser.getId(), "op", -1,true);
			//	department = departmentDao.getDepTree_level1(elUser.getId(),
			//			"op", -1, true);
				stTree = stationDao.getStTree_level1(elUser.getId(), "use", -1, true);
			//	depTree = departmentDao.getDepTree_level1(elUser.getId(),
			//			"use", -1, true);
				for (int i = 0; i < stTree.getChild().size(); i++) {
					// 判断可操作的树是否有节点包含（排除相等的情况，所以下面用到循环比较）可使用树里面的节点，如果包含就得删除可使用的树中那个节点，否则重复
					int j = 0;
					for (j = 0; j < station.getChild().size(); j++) {
						if (stTree.getChild().get(i).getId() == station
								.getChild().get(j).getId()) {
							break;
						}
					}
					if (j == station.getChild().size()) {
						if (elnodesql.checkNode(stTree.getChild().get(i)
								.getId(), station, "station")) {
							stationDao.deleteUserOpOrUseGrant(elUser.getId(), "use", stTree.getChild().get(i).getId());
						//	departmentDao.deleteUserOpOrUseGrant(
						//			elUser.getId(), "use", depTree.getChild()
						//					.get(i).getId());
						}
					}
				}
			}
		}else if ("use".equals(treeType)) {
			departmentDao.deleteUserUseGrant(elUser.getId());
			if (chkstr != null) {
				departments = new ArrayList<Department>();
				for (int i = 0; i < chkstr.length; i++) {
					departments
							.add(new Department(Integer.parseInt(chkstr[i])));
				}
			}
			ElNodeSQL elnodesql = (ElNodeSQL) SpringContextUtil
					.getBean(ElConstants.CLASS_ELNODESQL);
			department = departmentDao.getDepTree_level1(elUser.getId(), "op",
					-1, true);
			if (departments != null) {
				for (int i = 0; i < departments.size(); i++) {
					int j = 0;
					for (j = 0; j < department.getChild().size(); j++) {
						if (departments.get(i).getId() == department.getChild()
								.get(j).getId()) {
							break;
						}
					}
					if (j == department.getChild().size()) {
						if (!elnodesql.checkNode(departments.get(i).getId(),
								department, "department")) {
							departmentDao.addOpusers("use", elUser.getId(),
									departments.get(i).getId());
						}
					}
					// departmentDao.addOpusers("use", elUser.getId(),
					// departments.get(i).getId());
				}
			}
			// 判断可使用的树是否包含可操作里面的节点，如果包含，当去掉可使用的树中的节点时，就不让去掉
			// departmentDao.deleteUserOpGrant(elUser.getId());

			depTree = departmentDao.getDepTree_level1(elUser.getId(), "use",
					-1, true);
			departments = department.getChild();
			for (int i = 0; i < departments.size(); i++) {
				// departmentDao.addOpusers("op", elUser.getId(),
				// departments.get(i).getId());
				// 判断可使用的树是否包含可操作里面的节点，如果不包含就自己加入到可使用的树中
				if (!elnodesql.checkNode(departments.get(i).getId(), depTree,
						"department")) {
					departmentDao.addOpusers("use", elUser.getId(), departments
							.get(i).getId());
				}
			}
		} else {
			userDao.userGrantOnQlibTree(chkstr, elUser.getId(), treeType);
		}
		return "userGrant";
	}

	public String userLoginInfo_list() throws ElException {
		myLogins = userDao.getAllUserLoginInfo(myLogin, getPageNow(),
				getPageSize());
		count = userDao.getAllUserLoginInfoCount(myLogin);
		return "userLoginInfo_list";
	}

	public String delUserLoginInfo() throws ElException {
		userDao.delUserLoginInfo(myLogin);
		return "userLoginInfo_list";
	}

	public String eluser_addBasedbInit() throws ElException {
		// 查基础数据类别
		baseDataTypeList = userDao.getAllBaseDataType(getPageNow(),
				getPageSize());
		return "eluser_addBasedbInit";
	}

	public String eluser_addBasedb() throws ElException {
		// 判断名称是否重复
		if (userDao.checkBase(baseDatat.getBasevalue(), baseDatat.getTypeid())) {
			setElmessage("该类别的该名称在数据库中已经存在！");
			return "error";
		}
		// 判断编号是否重复
		if (userDao.checkBaseBh(baseDatat.getBh(), baseDatat.getTypeid())) {
			setElmessage("该类别的该编号在数据库中已经存在！");
			return "error";
		}
		baseDatat.setElUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		userDao.addBaseDb(baseDatat);
		return "eluser_BasedbList";
	}

	public String baseDb_jingzhong_up() throws ElException {
		// 更新警种字段
		userDao.updateBaseDb_jingzhong();
		if (baseDatat == null) {
			baseDatat = new BaseDatat();
			baseDatat.setTypeid(1);
		}
		return "eluser_BasedbList";
	}

	public String eluser_BasedbList() throws ElException {
		if (baseDatat == null) {
			baseDatat = new BaseDatat();
			baseDatat.setTypeid(-1);
			allTypeid = 1;
		}
		// baseDatatList=userDao.getBaseDatatByTypeid(baseDatat.getTypeid(),getPageNow(),getPageSize());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			baseDatatList = userDao.getBaseDatatByTypeid2(
					baseDatat.getTypeid(), getPageNow(), getPageSize());
			count = userDao.getBaseDatatByTypeidCount(baseDatat.getTypeid());
		} else {
			baseDatatList = userDao.getBaseDatatByTypeid2(
					getSessionIntValue(ElConstants.SESSION_USERID), baseDatat
							.getTypeid(), getPageNow(), getPageSize());
			count = userDao.getBaseDatatByTypeidCount(
					getSessionIntValue(ElConstants.SESSION_USERID), baseDatat
							.getTypeid());
		}

		// 查基础数据类别
		baseDataTypeList = userDao.getAllBaseDataType();
		return "eluser_BasedbList";
	}

	public String eluser_BasedbSort() throws ElException {
		if (baseDatat != null) {
			if (baseDatat.getSortManner() == 1) {
				userDao.sortBaseDbs(baseDatat.getTypeid(), baseDatat
						.getSortid(), 1);
			} else {
				userDao.sortBaseDbs(baseDatat.getTypeid(), baseDatat
						.getSortid(), 0);
			}
		}
		return "eluser_BasedbList";
	}

	@SuppressWarnings("unchecked")
	public String update_BasedbType() throws ElException {
		Map map = new HashMap();
		try {
			int colid = Integer.valueOf(colId.trim());
			map.put("colId", colid);
			if (colid == 1) {
				if (StringUtils.isBlank(jingzhong)) {
					map.put("name", "null");
				} else {
					map.put("name", jingzhong.trim());
				}
			} else if (colid == 2) {
				if (StringUtils.isBlank(zhiwu)) {
					map.put("name", "null");
				} else {
					map.put("name", zhiwu.trim());
				}
			} else if (colid == 3) {
				if (StringUtils.isBlank(zhiji)) {
					map.put("name", "null");
				} else {
					map.put("name", zhiji.trim());
				}
			} else if (colid == 4) {
				if (StringUtils.isBlank(gangwei)) {
					map.put("name", "null");
				} else {
					map.put("name", gangwei.trim());
				}
			} else if (colid == 5) {
				if (StringUtils.isBlank(dishi)) {
					map.put("name", "null");
				} else {
					map.put("name", dishi.trim());
				}
			}

			// map.put("courseId", Integer.valueOf(courseId.trim()));
		} catch (Exception e) {
			logger.error("修改基础数据类别错误", e);
			throw new ElException(e);
		}
		if (!map.get("name").toString().equals("null")) {
			userDao.updateBasedbType(map);
		}
		// classDao.updateCourseRelation(map);
		return null;
	}

	public String eluser_BasedbDel() throws ElException {
		baseDatat = userDao.getBaseDatatById(baseDatat.getId());
		String col = "";
		String col1 = "";
		switch (baseDatat.getBaseType().getId()) {
		case 1:
			col = "jingzhong";
			col1 = "警种";
			break;
		case 2:
			col = "zhiwu";
			col1 = "职务";
			break;
		case 3:
			col = "zhiji";
			col1 = "职级";
			break;
		case 4:
			col = "gangwei";
			col1 = "岗位";
			break;
		case 5:
			col = "dishi";
			col1 = "地市";
			break;
		default:
			col = "";
			col1 = "未知";
			break;
		}
		System.out.println("BasedbDel_flag=="+BasedbDel_flag);
		if(BasedbDel_flag==1){
			userDao.delBaseDb(baseDatat.getId());
		}else{
			if (!col.equals("") && !userDao.checkHasUser(baseDatat.getId(), col))
				userDao.delBaseDb(baseDatat.getId());
			else {
				setElmessage("不能删除此" + col1 + ",有用户在" + col1 + "之下,请删除或更改此" + col1
						+ "下的用户!");
				return "error";
			}
		}
		
		return "eluser_BasedbList";
	}

	/**
	 * Description: 基础数据批量导入
	 * 
	 * @Version1.0 2012-7-26 下午07:10:52 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String eluser_BasedbImp() throws ElException {
		userDao.impBaseDb();
		setElmessage("success");
		return this.eluser_BasedbList();
	}

	public String eluser_alterBasedbInit() throws ElException {
		baseDatat = userDao.getBaseDatatById(baseDatat.getId());
		return "eluser_alterBasedb";
	}

	public String eluser_alterBasedb() throws ElException {
		// 判断名称是否重复（除去自己）
		if (userDao.checkBase(baseDatat.getBasevalue(), baseDatat.getTypeid(),
				baseDatat.getId())) {
			setElmessage("该类别的该名称在数据库中已经存在！");
			return "error";
		}
		// 判断编号是否重复（除去自己）
		if (userDao.checkBaseBh(baseDatat.getBh(), baseDatat.getTypeid(),
				baseDatat.getId())) {
			setElmessage("该类别的该编号在数据库中已经存在！");
			return "error";
		}
		userDao.updateBaseDb(baseDatat);
		return "eluser_BasedbList";
	}

	/*public String systemGrantInfo() throws Exception {
		String path = Register.class.getResource("").toString();
		path = path.substring(6, path.length() - 43);
		path += "/WEB-INF/config/";
		String registerInfo = EP.unepFromFile(path + "/license.inc");
		String sss[] = registerInfo.split("=.=");
		long startD = Long.valueOf(sss[1]);
		long timeOut = Long.valueOf(sss[2]);
		Calendar ca = Calendar.getInstance();// 当前时间
		ca.setTimeInMillis(startD);// 授权开始时间
		ca.add(ca.YEAR, 3);// 开始后3年的时间
		if ((startD + timeOut) >= ca.getTimeInMillis()) {// 如果授权结束时间超过开始时间3年
			// setElmessage("当前版本：授权使用，永久授权。");
			setElmessage("授权使用。<br/>授权方式：永久授权。<br/>授权截止时间：不限。");
		} else {
			// String grantTime=new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss").format(new Timestamp(startD+timeOut));
			String grantTime = new SimpleDateFormat("yyyy年MM月dd日")
					.format(new Timestamp(startD + timeOut));
			// setElmessage("当前版本：授权使用，授权到 "+grantTime);
			setElmessage("授权使用。<br/>授权方式：有效期授权。<br/>授权截止时间：" + grantTime + "。");
		}
		return "error";
	}*/
	
	//北京二次开发修改
	public String account_search2() throws ElException { 
		if(0 < roleid && role==null){  
			role = roleDao.getRoleById(roleid);	
			}	
		roleid = role == null ? 0 : role.getId();  
		if(department==null){ 
			department=new Department(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
			elUser=new ELUser();
			elUser.setValid(true);
			elUser.setNov(1); 
			sub_department=1;
		}else{
			if(department.getId()==-2){ 
				deprTree = departmentDao.getDepTree(
						getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
						true);
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
		if(elUser!=null){
			if(elUser.getValid2()==0){
				elUser.setValid(true);
				elUser.setNov(1);
			}else if(elUser.getValid2()==1){
				elUser.setValid(true);
				elUser.setNov(0);
			}else{
				elUser.setValid(false);
				elUser.setNov(0);
			} 
			elUser.setRole(new ElRole(getSessionIntValue(ElConstants.SESSION_ROLE)));
		}
		if (roleid == 0) {   
			if (department.getId() == -2
					&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {//部门id可能为-2吗？（根节点为1） 
				if(exprot == true){//导出 
					elUsers = userDao.getUserByUserId3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
							  elUser, 10000000,1);
					return "account_search_Excel";
				}
				if(toAll ==true){//开通全部
					elUsers = userDao.getUserByUserId3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
							elUser, 10000000,1); 
					for (int i = 0; i < elUsers.size(); i++) {  
						userDao.updateValid(elUsers.get(i).getId(), 1);//开通
						user = userDao.getUserById(elUsers.get(i).getId());
						if(user.getRole().getName().equals("准开通") || user.getRole().getName().equals("初审通过")){//如果为这两种角色。 就把角色改成学员
							userDao.alterUserRole(user, 4);
						} 
					}
					toAll = false;
				}
				elUsers = userDao.getUserByUserId3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
						elUser, getPageNow(), getPageSize());
				count = userDao.getUserByUserIdSize3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,  
						elUser);  
				
			} else {
				if(exprot == true){//导出
					elUser.setNov(1);
					elUsers = userDao.getUserByDepId2(department.getId(),
							sub_department, elUser);
					return "account_search_Excel";
				}
				if(toAll ==true){//开通全部
					elUsers = userDao.getUserByDepId2(department.getId(),
							sub_department, elUser, 10000000,1);
					for (int i = 0; i < elUsers.size(); i++) {  
						userDao.updateValid(elUsers.get(i).getId(), 1);//开通
						user = userDao.getUserById(elUsers.get(i).getId()); 
						if(user.getRole().getName().equals("准开通") || user.getRole().getName().equals("初审通过")){//如果为这两种角色。 就把角色改成学员
							userDao.alterUserRole(user, 4);
						} 
					}
					toAll = false;
				}
				elUsers = userDao.getUserByDepId2(department.getId(),
						sub_department, elUser, getPageNow(), getPageSize());
				count = userDao.getUserByDepIdSize2(department.getId(),
						sub_department, elUser);
			}
		} else { 
			if (department.getId() == -2
					&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {//部门id可能为-2吗？（根节点为1） 
				if(exprot == true){//导出 
					elUsers = userDao.getUserByUserId3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
							  elUser, 10000000,1);
					return "account_search_Excel";
				}
				if(toAll ==true){//开通全部
					elUsers = userDao.getUserByUserId3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
							  elUser, 10000000,1); 
					for (int i = 0; i < elUsers.size(); i++) {  
						userDao.updateValid(elUsers.get(i).getId(), 1);//开通
						user = userDao.getUserById(elUsers.get(i).getId());
						if(user.getRole().getName().equals("准开通") || user.getRole().getName().equals("初审通过")){//如果为这两种角色。 就把角色改成学员
							userDao.alterUserRole(user, 4);
						} 
					}
					toAll = false;
				}
				elUsers = userDao.getUserByUserId3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,
						  elUser, getPageNow(), getPageSize());
					count = userDao.getUserByUserIdSize3(deprTree,department.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),roleid,  
							elUser); 
			} else { 
			if(exprot == true){//导出
				elUsers = userDao
				.getUserByDepId2(department.getId(), sub_department, elUser,
						roleid); 
				return "account_search_Excel";
			} 
			if(toAll ==true){//开通全部
				elUsers = userDao.getUserByDepId2(department.getId(), sub_department, elUser,roleid,10000000,1); 
				for (int i = 0; i < elUsers.size(); i++) {  
					userDao.updateValid(elUsers.get(i).getId(), 1);//开通
					user = userDao.getUserById(elUsers.get(i).getId());
					if(user.getRole().getName().equals("准开通") || user.getRole().getName().equals("初审通过")){//如果为这两种角色。 就把角色改成学员
						userDao.alterUserRole(user, 4);
					} 
				}
				toAll = false;
			}
			elUsers = userDao.getUserByDepId2(department.getId(), 
					sub_department, elUser,	roleid, getPageNow(), getPageSize());
			count = userDao.getUserByDepIdSize2(department.getId(),
					sub_department, elUser, roleid);
//			elUsers = userDao.getUserByDepId2(department.getId(),
//					sub_department, elUser, getPageNow(), getPageSize());
//			count = userDao.getUserByDepIdSize2(department.getId(),
//					sub_department, elUser);
			}
		} 
		roles = roleDao.listRoles();
		return "account_result2";
	}

	public String unlock() throws ElException {
		if(SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("0")){ 
			setElmessage("此功能未开启，欲开启此功能请到 使用配置 “最大登陆失败次数” 进行配置！");
			return "error";
		} 
		//1获取传过来的id 
		String userids=getRequest().getParameter("userids"); 
		String resultPage=getRequest().getParameter("resultPage"); 
		if(userids!=null){
			String[] useridss=userids.split(",");
			for (int i = 0; i < useridss.length; i++) { 
				if(userDao.checkLogonFailureNumber(Integer.parseInt(useridss[i]), SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX))){
					userDao.deleteLoingFailure(Integer.parseInt(useridss[i]));
				}
			}
		} 
		return this.account_search2();
	}

	public String toLockfor() throws ElException {
		if(SystemConfOp.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX).equals("0")){ 
			setElmessage("此功能未开启，欲开启此功能请到 使用配置 “最大登陆失败次数” 进行配置！");
			return "error";
		} 
		//1获取传过来的id 
		String userids=getRequest().getParameter("userids"); 
		String resultPage=getRequest().getParameter("resultPage"); 
		if(userids!=null){
			String[] useridss=userids.split(",");
			for (int i = 0; i < useridss.length; i++) { 
				if(!userDao.checkLogonFailureNumber(Integer.parseInt(useridss[i]), SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX))){
					myLogin = new MyLogin();
					int userid = Integer.parseInt(useridss[i]);
					if(getSessionIntValue(ElConstants.SESSION_USERID)!=userid){
						myLogin.setElUser(new ELUser(Integer.parseInt(useridss[i]))); 	
						int lognumber = 0;
						for(int j = 0 ; j < SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX); j++){					
							Timestamp d = new Timestamp(System.currentTimeMillis()); 
							myLogin.setLogintime(d);
							lognumber = lognumber+1;
							myLogin.setLognumber(lognumber);
							userDao.insertLoingFailure2(myLogin);
						}
					}
				}
			}
		} 
		return this.account_search2();
	}
	
	public String MustOpenAssignUser() throws ElException {
		//1获取传过来的id
		//2获取传过来的状态
		String userids=getRequest().getParameter("userids");
		String status=getRequest().getParameter("status");
		String resultPage=getRequest().getParameter("resultPage");
		if(userids!=null){
			String[] useridss=userids.split(",");
			for (int i = 0; i < useridss.length; i++) { 
				//准开通
				userDao.updateValid(Integer.parseInt(useridss[i]), 2); 
				int role = userDao.getEURoleByName("准开通");
				if(role == 0){ 
					setElmessage("获取 准开通 角色失败，没有 准开通 角色");
					return "error";
				}
				userDao.setEURole(Integer.parseInt(useridss[i]), role);
			}
		}
		//return "account_result2";
		if("2".equals(resultPage)){
			return this.displayNoValidUser();
		}
		return this.account_search2();
	}
	
	public String ApplicationAssignUser() throws ElException {
		//1获取传过来的id
		//2获取传过来的状态
		String userids=getRequest().getParameter("userids");
		String status=getRequest().getParameter("status");
		String resultPage=getRequest().getParameter("resultPage"); 
		if(userids!=null){
			String[] useridss=userids.split(",");
			for (int i = 0; i < useridss.length; i++) { 
				//准开通
				userDao.updateValid(Integer.parseInt(useridss[i]), 3); 
				int role = userDao.getEURoleByName("初审通过");
				if(role == 0){ 
					setElmessage("获取 初审通过 角色失败，没有 初审通过 角色");
					return "error";
				}
				userDao.setEURole(Integer.parseInt(useridss[i]), role);
			}
		}
		//return "account_result2";
		if("2".equals(resultPage)){
			return this.displayNoValidUser();
		}
		return this.account_search2();
	}
	
	
	public String chooseMenu() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			funcTree = roleDao.getFuncTree();
		} else {
			funcTree = roleDao.getFuncTreeByRoleId(getSessionIntValue(ElConstants.SESSION_ROLE));
		}
		if (funcTree != null && funcTree.getChild() != null) {
			funcTree.setCount(funcTree.getChild().size());
		}
//		role = roleDao.getRoleById(role.getId());
		return "chooseMenu";
	}
	
	public String elf_view() throws ElException {
		     func = roleDao.getFuncById(func.getId());
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				String d= "{\"id\":\"" + func.getId() + "\",\"name\":\"" + func.getName()
								+ "\"}";
				localPrintWriter.println(d);
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				logger.error("ajax菜单查看错误",e);
			}
			return null;
		}
	
	
	//---------------------sd1230-----------------------------------
	public String student_listInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
			// true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);

		else {
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
			// true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		department = department == null ? new Department(1) : department;
		roles = roleDao.listRoles();
		if (depTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的部门类别");
			return "error";
		}
		return "student_list";
	}
	
	public String student_list() throws ElException{
		userid = getSessionIntValue(ElConstants.SESSION_USERID);
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
			//若需要岗位作为查询条件之一，则删除以下代码
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
			/////////
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
		elUsers = userDao.listUsers_sd(department, station, sub_department, elUser,
				getPageNow(), getPageSize());
		count = userDao.listUsersSize_sd(department, station,sub_department, elUser);
		roles = roleDao.listRoles();
		
		return "student_list";
	}
	
	public String student_addInit() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs = userDao.getBaseDatatByTypeid(1);
			zhiwus = userDao.getBaseDatatByTypeid(2);
			zhijis = userDao.getBaseDatatByTypeid(3);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5);
			// 论坛级别
			luntanjibies = userDao.getBaseDatatByTypeid(6);
		} else {
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			jingzhongs = userDao.getBaseDatatByTypeid(1, userid);
			zhiwus = userDao.getBaseDatatByTypeid(2, userid);
			zhijis = userDao.getBaseDatatByTypeid(3, userid);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5, userid);
			luntanjibies = userDao.getBaseDatatByTypeid(6, userid);
			// 论坛级别
		}
		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		return "student_add";
	}
	
	public String student_add() throws ElException{
		elUser.setPassword(MD5.crypt(elUser.getPassword()));
		if(elUser.getDepartment().getId()==0){
			elUser.setDepartment(new Department(6290));
		}
		if(elUser.getXianzhiwei()==null||elUser.getXianzhiwei().equals("")){
			elUser.setXianzhiwei("111");
		}
		int ii = userDao.insert_cisco(elUser);
		elUser.setId(ii);
		return "student_add";
	}
	public String student_alterInit() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			jingzhongs = userDao.getBaseDatatByTypeid(1);
			zhiwus = userDao.getBaseDatatByTypeid(2);
			zhijis = userDao.getBaseDatatByTypeid(3);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5);
			luntanjibies = userDao.getBaseDatatByTypeid(6);
		} else {
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			jingzhongs = userDao.getBaseDatatByTypeid(1, userid);
			zhiwus = userDao.getBaseDatatByTypeid(2, userid);
			zhijis = userDao.getBaseDatatByTypeid(3, userid);
			gangweis = userDao.getBaseDatatByTypeid(4);
			dishis = userDao.getBaseDatatByTypeid(5, userid);
			luntanjibies = userDao.getBaseDatatByTypeid(6, userid);
		}
		if (elUser.getId() == getSessionIntValue(ElConstants.SESSION_USERID)) {
			return "noright";
		}
		elUser = userDao.getUserById_cisco(elUser.getId());
		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		
		return "student_alter";
	}
	
	public String student_alter() throws ElException{
//		System.out.println(elUser.getStation().getId());
		//elUser.setPassword(MD5.crypt(elUser.getPassword()));
		userDao.update_cisco(elUser);
		if (elUser.getPassword() != null
				&& !"".equals(elUser.getPassword().trim())) {
			elUser.setPassword(MD5.crypt(elUser.getPassword()));
			userDao.alterMyPwd(elUser);
		}
		elUser = userDao.getUserById_cisco(elUser.getId()); 
		this.upOk=1;
		return "student_alter_success";
	}
	
	public String student_view() throws ElException {
		elUser = userDao.getUserById_cisco(elUser.getId());
		return "student_view";
	} 
	
	
	public String delUser_stu() throws ElException {
		// 1获取传过来的id
		String userids = getRequest().getParameter("userids");
		if (userids != null) {
			String[] useridss = userids.split(",");
			for (int i = 0; i < useridss.length; i++) {
				if (Integer.parseInt(useridss[i]) != getSessionIntValue(ElConstants.SESSION_USERID)) {// 不能删除自己
					// 然后判断是否真删除
			//		if (userDao.checkElUserIsUse(Integer.parseInt(useridss[i]))) {
						// 假删除(就是关闭)
			//			userDao.updateValid(Integer.parseInt(useridss[i]), 0);
			//		} else {
						// 真删除
						userDao.delete(Integer.parseInt(useridss[i]));
			//		}
				}
			}
		}
		return this.student_list();
	}
	
	//sd0109
	public String eluser_BaseTypeList() throws ElException {

		// 查基础数据类别
		baseDataTypeList = userDao.getAllBaseDataType();
		count = userDao.getBaseTypeCount();
		return "eluser_BaseTypeList";
	}
	
	
	public String eluser_addBaseTypeInit() throws ElException {
		return "eluser_addBaseTypeInit";
	}

	public String eluser_addBaseType() throws ElException {
//		// 判断名称是否重复
//		if (userDao.checkBase(baseDatat.getBasevalue(), baseDatat.getTypeid())) {
//			setElmessage("该类别的该名称在数据库中已经存在！");
//			return "error";
//		}
		
		
		userDao.addBaseType(bdt);
		return "eluser_BaseTypeList";
	}
	
	
	public String eluser_BaseTypeDel() throws ElException {
			userDao.delBaseType(bdt.getId());
		
		return "eluser_BaseTypeList";
	}


	public int getBasedbDel_flag() {
		return BasedbDel_flag;
	}

	public void setBasedbDel_flag(int basedbDel_flag) {
		BasedbDel_flag = basedbDel_flag;
	}

	public BaseDataType getBdt() {
		return bdt;
	}

	public void setBdt(BaseDataType bdt) {
		this.bdt = bdt;
	}

}
