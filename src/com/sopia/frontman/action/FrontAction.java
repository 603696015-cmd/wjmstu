package com.sopia.frontman.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.Flink;
import com.sopia.FlinkDaoImpl;
import com.sopia.assistman.dao.PollDao;
import com.sopia.assistman.dao.impl.PollDaoImpl;
import com.sopia.assistman.entities.Poll;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.AuthorityUtil;
import com.sopia.common.CheckHtml;
import com.sopia.common.ComparatorUser;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.SendMail;
import com.sopia.common.SystemConf;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseRegistration;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.QuestionRanking;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.BaseDataTypeCourse;
import com.sopia.duman.entities.BaseDatatCourse;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.UnitRanking;
import com.sopia.forumman.dao.ForumAdminDao;
import com.sopia.forumman.entities.Forum;
import com.sopia.forumman.entities.ForumBlock;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.forumman.entities.Topic;
import com.sopia.freemarker.FreePage;
import com.sopia.frontman.dao.FrontDao;
import com.sopia.knowledgeman.dao.KnowledgeDao;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.lucene.LuceneSearch;
import com.sopia.lucene.file.FileResult;
import com.sopia.lucene.file.FileSeach;
import com.sopia.lucene.index.FileBean;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.newsandmess.dao.impl.MessageDaoImpl;
import com.sopia.newsandmess.entities.Message;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.newversion.NewVersionUtil;
import com.sopia.pfms.dao.BaoxianProductDao;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.dao.ProductDao;
import com.sopia.pfms.entities.BaoxianProduct;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.entities.Product;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.statman.dao.StatisticClassDao;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyCourse;

public class FrontAction extends BaseAction {
	private List<Course> zxCourses;
	// private List<Course> rmCourses;
	private List<News> rmCourses;
	private List<Course> phCourses;
	private Course course;
	private CourseType ctypeTree;
	private CourseTypeDao ctypeDao;
	private CourseType ctype1;
	private CourseType ctype2;
	private CourseType ctype3;
	private CourseType ctype4;
	private CourseType ctype5;
	private CourseType ctype6;
	private CourseType ctype7;
	private CourseType ctype8;

	private FrontDao frontDao;
	private List<News> tjNews;
	private List<News> zztjNews;
	private List<News> zxNews;
	private List<News> zxNewss;
	private List<News> zxNewss_tw_wb;// top位置图文加文本
	private List<News> zxNotices;
	private List<News> zxNotices1;
	private List<ELUser> phUsers;
	private List<Knowledge> zxKnows;
	private List<Knowledge> tjKnows;
	private List<Knowledge> tjKnows_tw_zd;// 推荐资源_图文_重点
	private List<Knowledge> tjKnows_wb_tj;// 推荐资源_文本_推荐
	private NewsType newsTree;
	private NewsType noticeTree;
	private NewsType newstype;
	private NewsDao newsDao;
	private int containsub;
	private News news;
	private List<Department> phDeps;

	private List<Knowledge> knowledges;
	private List<Knowledge> tjknowledges;
	private List<Knowledge> rmknowledges;
	private List<Knowledge> zdknowledges;
	private KnowledgeType kltypeTree;
	private KnowledgeDao knowledgeDao;
	private KnowledgeType kltype;
	private Knowledge knowledge;
	private StuffLib qstuff;
	private List<StuffLib> qstuffs;
	private List<ForumBlockType> fbtypes;
	private ForumAdminDao forumAdminDao;
	private List<Forum> jhforums;
	private List<Forum> rmforums;
	private List<Forum> zxforums;
	private List<Forum> tjforums;
	private List<Forum> zztjforums;
	private ForumBlock fblock;
	private Forum forum;
	private List<Topic> topics;
	private Topic topic;
	private ElClass elclass;
	private List<ElClass> elclasses;
	private ClassDao classDao;
	private EroomDao eroomDao;
	private StudyClassDao studyClassDao;
	private ElFunc menu;
	private RoleDao roleDao;
	private List<ElFunc> menus;
	private CourseDao courseDao;
	private StatisticClassDao statisticClassDao;
	private List<Forum> forums;
	private List<News> listNews;
	private List<News> zxlxxy;
	private List<News> zxlxxy_tw_zd;// 帮助中心_图文_重点
	private List<News> zxlxxy_wb_tj;// 帮助中心_文本_推荐
	private List<News> zxxzzx;// 下载中心
	private List<News> zxxzzx_tw_zd;// 下载中心_图文_重点
	private List<CourseType> ctls;
	private List<NewsType> ntls;
	private List<KnowledgeType> ktlist;
	private int topicOp;
	private ELUser elUser;
	private StringBuffer explain;
	private CourseRegistration coRegistration;
	private int registerstatus;
	private int isCorrespond;// 搜索是否符合申请的课程 0 全部可申请的课程 1 符合申请的课程
	private String httpsPath;
	private String httpPath;
	private String Return;
	private StudyQuizDao studyQuizDao;
	private List<Course> hotCourses;
	private List<Course> newCourses;
	private List<ElClass> newelclasss;
	private List<ExamRoom> newErooms;
	private News newspop;// 首页弹窗新闻
	private IndexDataUtil indexDataUtil;// 首页数据显示帮助类
	private int isLoginIp;
	private Department dept;
	private List<Department> deptsp;
	private String msg;

	private List<BaseDataTypeCourse> baseCourseTypeList;
	private String shihegangwei;// 适合岗位
	private String zhuanyeleibie;// 专业类别
	private String zhuanyejibie;// 专业级别
	private String shihebumen;// 适合部门
	private String neirongleixing;// 内容类型
	private String peixunleibie;// 培训类别
	private String shihexuewei;// 适合学位
	private String kechengxingzhi;// 课程性质
	private List<BaseDatatCourse> shihegangweis;
	private List<BaseDatatCourse> zhuanyeleibies;
	private List<BaseDatatCourse> zhuanyejibies;
	private List<BaseDatatCourse> shihebumens;
	private List<BaseDatatCourse> neirongleixings;
	private List<BaseDatatCourse> peixunleibies;
	private List<BaseDatatCourse> shihexueweis;
	private List<BaseDatatCourse> kechengxingzhis;
	private List<ElClass> classKs_pass;
	private int ctid;
	private List<Course> Courses;
	private List<ExamPaper> examPapers;
	private Department depTree;
	private List<Course> tjCourses;
	private List<UnitRanking> unitRanks;//单位排名
	private int number;
	private int nid;
	private int step;
	private int modelstatus;
	//卫生局0901修改
	private boolean needAllocation;
	private MyClass new_cla;//本年度最新一期培训班
	private int isChangeElclass;//是否已选择培训班
	private MyClass nianjian_cla;//年检培训班
	private List<MyCourse> studyCourseList;
	private int isBuyNianjianClass;
	private Map<String,Object> map;
	private String content_;//知识库知识截取字段
	private List<Forum> fiveForums;
	
	
	
	//投票
	private PollDao pollDao;
	private Poll poll;
	private List<Integer> answer;
	private QuestionRanking questionRanking;
	
	private HttpRequestDeviceUtils httpRequestDeviceUtils;
	private Department department;
	private DepartmentDao depDao;
	private StuffDao stuffDao;
	
	
	public StuffDao getStuffDao() {
		return stuffDao;
	}
	public void setStuffDao(StuffDao stuffDao) {
		this.stuffDao = stuffDao;
	}
	public DepartmentDao getDepDao() {
		return depDao;
	}
	public void setDepDao(DepartmentDao depDao) {
		this.depDao = depDao;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public int getModelstatus() {
		return modelstatus;
	}
	public void setModelstatus(int modelstatus) {
		this.modelstatus = modelstatus;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public StudyClassDao getStudyClassDao() {
		return studyClassDao;
	}
	public void setStudyClassDao(StudyClassDao studyClassDao) {
		this.studyClassDao = studyClassDao;
	}
	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<UnitRanking> getUnitRanks() {
		return unitRanks;
	}

	public void setUnitRanks(List<UnitRanking> unitRanks) {
		this.unitRanks = unitRanks;
	}

	public List<Course> getTjCourses() {
		return tjCourses;
	}

	public void setTjCourses(List<Course> tjCourses) {
		this.tjCourses = tjCourses;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}


	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}

	public List<Course> getCourses() {
		return Courses;
	}

	public void setCourses(List<Course> courses) {
		Courses = courses;
	}

	public int getCtid() {
		return ctid;
	}

	public void setCtid(int ctid) {
		this.ctid = ctid;
	}

	public List<BaseDataTypeCourse> getBaseCourseTypeList() {
		return baseCourseTypeList;
	}

	public void setBaseCourseTypeList(
			List<BaseDataTypeCourse> baseCourseTypeList) {
		this.baseCourseTypeList = baseCourseTypeList;
	}

	public String getShihegangwei() {
		return shihegangwei;
	}

	public void setShihegangwei(String shihegangwei) {
		this.shihegangwei = shihegangwei;
	}

	public String getZhuanyeleibie() {
		return zhuanyeleibie;
	}

	public void setZhuanyeleibie(String zhuanyeleibie) {
		this.zhuanyeleibie = zhuanyeleibie;
	}

	public String getZhuanyejibie() {
		return zhuanyejibie;
	}

	public void setZhuanyejibie(String zhuanyejibie) {
		this.zhuanyejibie = zhuanyejibie;
	}

	public String getShihebumen() {
		return shihebumen;
	}

	public void setShihebumen(String shihebumen) {
		this.shihebumen = shihebumen;
	}

	public String getNeirongleixing() {
		return neirongleixing;
	}

	public void setNeirongleixing(String neirongleixing) {
		this.neirongleixing = neirongleixing;
	}

	public String getPeixunleibie() {
		return peixunleibie;
	}

	public void setPeixunleibie(String peixunleibie) {
		this.peixunleibie = peixunleibie;
	}

	public String getShihexuewei() {
		return shihexuewei;
	}

	public void setShihexuewei(String shihexuewei) {
		this.shihexuewei = shihexuewei;
	}

	public String getKechengxingzhi() {
		return kechengxingzhi;
	}

	public void setKechengxingzhi(String kechengxingzhi) {
		this.kechengxingzhi = kechengxingzhi;
	}

	public List<BaseDatatCourse> getShihegangweis() {
		return shihegangweis;
	}

	public void setShihegangweis(List<BaseDatatCourse> shihegangweis) {
		this.shihegangweis = shihegangweis;
	}

	public List<BaseDatatCourse> getZhuanyeleibies() {
		return zhuanyeleibies;
	}

	public void setZhuanyeleibies(List<BaseDatatCourse> zhuanyeleibies) {
		this.zhuanyeleibies = zhuanyeleibies;
	}

	public List<BaseDatatCourse> getZhuanyejibies() {
		return zhuanyejibies;
	}

	public void setZhuanyejibies(List<BaseDatatCourse> zhuanyejibies) {
		this.zhuanyejibies = zhuanyejibies;
	}

	public List<BaseDatatCourse> getShihebumens() {
		return shihebumens;
	}

	public void setShihebumens(List<BaseDatatCourse> shihebumens) {
		this.shihebumens = shihebumens;
	}

	public List<BaseDatatCourse> getNeirongleixings() {
		return neirongleixings;
	}

	public void setNeirongleixings(List<BaseDatatCourse> neirongleixings) {
		this.neirongleixings = neirongleixings;
	}

	public List<BaseDatatCourse> getPeixunleibies() {
		return peixunleibies;
	}

	public void setPeixunleibies(List<BaseDatatCourse> peixunleibies) {
		this.peixunleibies = peixunleibies;
	}

	public List<BaseDatatCourse> getShihexueweis() {
		return shihexueweis;
	}

	public void setShihexueweis(List<BaseDatatCourse> shihexueweis) {
		this.shihexueweis = shihexueweis;
	}

	public List<BaseDatatCourse> getKechengxingzhis() {
		return kechengxingzhis;
	}

	public void setKechengxingzhis(List<BaseDatatCourse> kechengxingzhis) {
		this.kechengxingzhis = kechengxingzhis;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Department> getDeptsp() {
		return deptsp;
	}

	public void setDeptsp(List<Department> deptsp) {
		this.deptsp = deptsp;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public int getIsLoginIp() {
		return isLoginIp;
	}

	public void setIsLoginIp(int isLoginIp) {
		this.isLoginIp = isLoginIp;
	}

	private List<BaoxianProduct> baoxianProductList;
	private List<PfmsUser> pfmsUserList;
	private List<Product> productList;
	private BaoxianProductDao baoxianProductDao;
	private IndexDao indexDao;
	private ProductDao productDao;

	public List<BaoxianProduct> getBaoxianProductList() {
		return baoxianProductList;
	}

	public void setBaoxianProductList(List<BaoxianProduct> baoxianProductList) {
		this.baoxianProductList = baoxianProductList;
	}

	public List<PfmsUser> getPfmsUserList() {
		return pfmsUserList;
	}

	public void setPfmsUserList(List<PfmsUser> pfmsUserList) {
		this.pfmsUserList = pfmsUserList;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public BaoxianProductDao getBaoxianProductDao() {
		return baoxianProductDao;
	}

	public void setBaoxianProductDao(BaoxianProductDao baoxianProductDao) {
		this.baoxianProductDao = baoxianProductDao;
	}

	public IndexDao getIndexDao() {
		return indexDao;
	}

	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public News getNewspop() {
		return newspop;
	}

	public void setNewspop(News newspop) {
		this.newspop = newspop;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	public List<ElClass> getNewelclasss() {
		return newelclasss;
	}

	public void setNewelclasss(List<ElClass> newelclasss) {
		this.newelclasss = newelclasss;
	}

	public List<ExamRoom> getNewErooms() {
		return newErooms;
	}

	public void setNewErooms(List<ExamRoom> newErooms) {
		this.newErooms = newErooms;
	}

	public List<Course> getNewCourses() {
		return newCourses;
	}

	public void setNewCourses(List<Course> newCourses) {
		this.newCourses = newCourses;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public int getIsCorrespond() {
		return isCorrespond;
	}

	public void setIsCorrespond(int isCorrespond) {
		this.isCorrespond = isCorrespond;
	}

	public int getRegisterstatus() {
		return registerstatus;
	}

	public void setRegisterstatus(int registerstatus) {
		this.registerstatus = registerstatus;
	}

	public StringBuffer getExplain() {
		return explain;
	}

	public void setExplain(StringBuffer explain) {
		this.explain = explain;
	}

	public int getTopicOp() {
		return topicOp;
	}

	public void setTopicOp(int topicOp) {
		this.topicOp = topicOp;
	}

	public List<KnowledgeType> getKtlist() {
		return ktlist;
	}

	public void setKtlist(List<KnowledgeType> ktlist) {
		this.ktlist = ktlist;
	}

	public List<NewsType> getNtls() {
		return ntls;
	}

	public void setNtls(List<NewsType> ntls) {
		this.ntls = ntls;
	}

	public List<CourseType> getCtls() {
		return ctls;
	}

	public void setCtls(List<CourseType> ctls) {
		this.ctls = ctls;
	}

	public List<News> getZxlxxy() {
		return zxlxxy;
	}

	public void setZxlxxy(List<News> zxlxxy) {
		this.zxlxxy = zxlxxy;
	}

	public List<News> getListNews() {
		return listNews;
	}

	public void setListNews(List<News> listNews) {
		this.listNews = listNews;
	}

	public List<Forum> getForums() {
		return forums;
	}

	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

	public StatisticClassDao getStatisticClassDao() {
		return statisticClassDao;
	}

	public void setStatisticClassDao(StatisticClassDao statisticClassDao) {
		this.statisticClassDao = statisticClassDao;
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

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public List<ForumBlockType> getFbtypes() {
		return fbtypes;
	}

	public void setFbtypes(List<ForumBlockType> fbtypes) {
		this.fbtypes = fbtypes;
	}

	public ForumAdminDao getForumAdminDao() {
		return forumAdminDao;
	}

	public void setForumAdminDao(ForumAdminDao forumAdminDao) {
		this.forumAdminDao = forumAdminDao;
	}

	public StuffLib getQstuff() {
		return qstuff;
	}

	public void setQstuff(StuffLib qstuff) {
		this.qstuff = qstuff;
	}

	public List<StuffLib> getQstuffs() {
		return qstuffs;
	}

	public void setQstuffs(List<StuffLib> qstuffs) {
		this.qstuffs = qstuffs;
	}

	public List<Knowledge> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<Knowledge> knowledges) {
		this.knowledges = knowledges;
	}

	public List<Knowledge> getTjknowledges() {
		return tjknowledges;
	}

	public void setTjknowledges(List<Knowledge> tjknowledges) {
		this.tjknowledges = tjknowledges;
	}

	public List<Knowledge> getRmknowledges() {
		return rmknowledges;
	}

	public void setRmknowledges(List<Knowledge> rmknowledges) {
		this.rmknowledges = rmknowledges;
	}

	public List<Knowledge> getZdknowledges() {
		return zdknowledges;
	}

	public void setZdknowledges(List<Knowledge> zdknowledges) {
		this.zdknowledges = zdknowledges;
	}

	public KnowledgeType getKltypeTree() {
		return kltypeTree;
	}

	public void setKltypeTree(KnowledgeType kltypeTree) {
		this.kltypeTree = kltypeTree;
	}

	public KnowledgeDao getKnowledgeDao() {
		return knowledgeDao;
	}

	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}

	public KnowledgeType getKltype() {
		return kltype;
	}

	public void setKltype(KnowledgeType kltype) {
		this.kltype = kltype;
	}

	public Knowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	public List<Department> getPhDeps() {
		return phDeps;
	}

	public void setPhDeps(List<Department> phDeps) {
		this.phDeps = phDeps;
	}

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public NewsType getNewsTree() {
		return newsTree;
	}

	public void setNewsTree(NewsType newsTree) {
		this.newsTree = newsTree;
	}

	public NewsType getNoticeTree() {
		return noticeTree;
	}

	public void setNoticeTree(NewsType noticeTree) {
		this.noticeTree = noticeTree;
	}

	public List<Knowledge> getZxKnows() {
		return zxKnows;
	}

	public void setZxKnows(List<Knowledge> zxKnows) {
		this.zxKnows = zxKnows;
	}

	public List<ELUser> getPhUsers() {
		return phUsers;
	}

	public void setPhUsers(List<ELUser> phUsers) {
		this.phUsers = phUsers;
	}

	public List<Course> getZxCourses() {
		return zxCourses;
	}

	public void setZxCourses(List<Course> zxCourses) {
		this.zxCourses = zxCourses;
	}

	public FrontDao getFrontDao() {
		return frontDao;
	}

	public void setFrontDao(FrontDao frontDao) {
		this.frontDao = frontDao;
	}

	private List<NewsType> ntypes;

	public List<NewsType> getNtypes() {
		return ntypes;
	}

	public void setNtypes(List<NewsType> ntypes) {
		this.ntypes = ntypes;
	}

	private SystemConf sysconf;

	public SystemConf getSysconf() {
		return sysconf;
	}

	public void setSysconf(SystemConf sysconf) {
		this.sysconf = sysconf;
	}

	private List<Flink> flinks;
	private List<Knowledge> tpKnows;
	private List<Knowledge> spKnows;
	private List<Knowledge> wzKnows;
	private List<Knowledge> wdKnows;
	private Knowledge spKnow;
	private Knowledge wzKnow;
	private Knowledge wdKnow;
	private Course course1;
	private Course course2;
	private Course course3;
	private List<News> xydts;// 学院锟斤拷态
	private List<News> szlls;// 师锟斤拷f
	private List<News> zxzxs;// 教学公告
	private List<News> zxzxs_tw_zd;// 教学公告_图文_重点
	private List<News> zxzxs_wb_tj;// 教学公告_文本_推荐
	private List<News> jrrds;// 锟斤拷锟斤拷锟饺碉拷

	public List<News> getXydts() {
		return xydts;
	}

	public void setXydts(List<News> xydts) {
		this.xydts = xydts;
	}

	public List<News> getSzlls() {
		return szlls;
	}

	public void setSzlls(List<News> szlls) {
		this.szlls = szlls;
	}

	public List<News> getZxzxs() {
		return zxzxs;
	}

	public void setZxzxs(List<News> zxzxs) {
		this.zxzxs = zxzxs;
	}

	public List<News> getJrrds() {
		return jrrds;
	}

	public void setJrrds(List<News> jrrds) {
		this.jrrds = jrrds;
	}

	// public String index() throws ElException {
	// // this.frontDao.updateFlow();
	// this.xydts = this.frontDao.listNewsByTidhot(11, 0, 10, 1);//--/
	// if (this.xydts != null)
	// for (int i = 0; i < this.xydts.size(); ++i) {
	// String name = ((News)this.xydts.get(i)).getTitle();
	// if (i == 0)
	// ((News)this.xydts.get(i)).setTitle((name.length() > 11) ?
	// name.substring(0, 10) : name);
	// else
	// ((News)this.xydts.get(i)).setTitle(
	// (name.length() > 18) ? name.substring(0, 17) + "..." : name);
	// }
	// this.szlls = this.frontDao.listNewsByTidhot(11, 0, 11, 1);
	// if (this.szlls != null)
	// for (int i = 0; i < this.szlls.size(); ++i) {
	// String name = ((News)this.szlls.get(i)).getTitle();
	// if (i == 0)
	// ((News)this.szlls.get(i)).setTitle(
	// (name.length() > 11) ? name.substring(0, 10) :
	// name);
	// else
	// ((News)this.szlls.get(i)).setTitle(
	// (name.length() > 18) ? name.substring(0, 17) + "..." :
	// name);
	// }
	// //教学公告
	// this.zxzxs = this.frontDao.listNewsByTidhot(5, 0, 12, 1);
	// if (this.zxzxs != null) {
	// for (int i = 0; i < this.zxzxs.size(); ++i) {
	// String name = ((News)this.zxzxs.get(i)).getTitle();
	// if (i == 0)
	// ((News)this.zxzxs.get(i)).setTitle(
	// (name.length() > 11) ? name.substring(0, 9) + "..." :
	// name);
	// else {
	// ((News)this.zxzxs.get(i)).setTitle(
	// (name.length() > 14) ? name.substring(0, 13) + "..." :
	// name);
	// }
	// }
	// for (int i = 0; i < this.zxzxs.size(); ++i) {
	// String name = ((News)this.zxzxs.get(i)).getContent();
	// name = CheckHtml.getString(name);
	// ((News)this.zxzxs.get(i)).setContent(
	// (name.length() > 21) ? name.substring(0, 19) + "..." :
	// name);
	// }
	// }
	// //教学公告_图文_重点
	// this.zxzxs_tw_zd = this.frontDao.listNewsByTidhot(1, 0, 12, 3);
	// if (this.zxzxs_tw_zd != null) {
	// for (int i = 0; i < this.zxzxs_tw_zd.size(); ++i) {
	// String name = ((News)this.zxzxs_tw_zd.get(i)).getTitle();
	// String Content = ((News)this.zxzxs.get(i)).getContent();
	// ((News)this.zxzxs_tw_zd.get(i)).setTitle(
	// (name.length() > 11) ? name.substring(0, 9) + "..." : name);
	// Content = CheckHtml.getString(Content);
	// ((News)this.zxzxs_tw_zd.get(i)).setContent(
	// (Content.length() > 21) ? Content.substring(0, 19) + "..." : Content);
	// }
	// }
	// //教学公告_文本_推荐
	// this.zxzxs_wb_tj = this.frontDao.listNewsByTidhot(4, 0, 12, 1);
	// if (this.zxzxs_wb_tj != null) {
	// for (int i = 0; i < this.zxzxs_wb_tj.size(); ++i) {
	// String name = ((News)this.zxzxs_wb_tj.get(i)).getTitle();
	// ((News)this.zxzxs_wb_tj.get(i)).setTitle(
	// (name.length() > 14) ? name.substring(0, 13) + "..." :
	// name);
	// }
	// }
	//		    
	//		    
	// this.zxNews = this.frontDao.listNewsByTid(3, 0, 1, true, "");//--/
	// //top居中的头条
	// this.zxNewss = this.frontDao.listNewsByTidhot(2, 0, 1, 4);
	// if (this.zxNewss != null) {
	// for (int i = 0; i < this.zxNewss.size(); ++i) {
	// String name = ((News)this.zxNewss.get(i)).getTitle();
	// ((News)this.zxNewss.get(i)).setTitle(
	// (name.length() > 20) ? name.substring(0, 20) + "..." :
	// name);
	// }
	// }
	// for (int i = 0; i < this.zxNewss.size(); ++i) {
	// String name = ((News)this.zxNewss.get(i)).getContent();
	// name = CheckHtml.getString(name);
	// ((News)this.zxNewss.get(i)).setContent(
	// (name.length() > 56) ? name.substring(0, 53) + "..." :
	// name);
	// }
	// // top居中的头条 (一图文 3文本)
	// this.zxNewss_tw_wb = this.frontDao.listNewsByTidhot(4, 0, 1, 4);
	// if (this.zxNewss_tw_wb != null) {
	// for (int i = 0; i < this.zxNewss_tw_wb.size(); ++i) {
	// String name = ((News)this.zxNewss_tw_wb.get(i)).getTitle();
	// if (i == 0)
	// ((News)this.zxNewss_tw_wb.get(i)).setTitle(
	// (name.length() > 20) ? name.substring(0, 20) + "..." :
	// name);
	// else {
	// ((News)this.zxNewss_tw_wb.get(i)).setTitle(
	// (name.length() > 14) ? name.substring(0, 13) + "..." :
	// name);
	// }
	// String Content = ((News)this.zxNewss_tw_wb.get(i)).getContent();
	// Content = CheckHtml.getString(Content);
	// ((News)this.zxNewss_tw_wb.get(i)).setContent(
	// (Content.length() > 56) ? Content.substring(0, 53) + "..." : Content);
	// }
	// }
	//		    
	//		    
	// //推荐交流文章
	// this.rmforums = this.forumAdminDao.listForumsByRm(0, 6);
	// if (this.rmforums != null) {
	// for (int i = 0; i < this.rmforums.size(); ++i) {
	// String name = ((Forum)this.rmforums.get(i)).getTitle();
	// ((Forum)this.rmforums.get(i)).setTitle(
	// (name.length() > 15) ? name.substring(0, 15) + "..." :
	// name);
	// }
	// }
	// //最新交流文章
	// this.zxforums = this.forumAdminDao.listForumsByZx(7, 0);
	// if (this.zxforums != null) {
	// for (int i = 0; i < this.zxforums.size(); ++i) {
	// String name = ((Forum)this.zxforums.get(i)).getTitle();
	// ((Forum)this.zxforums.get(i)).setTitle(
	// (name.length() > 15) ? name.substring(0, 15) + "..." :
	// name);
	// }
	// }
	// //最新课程
	// this.zxCourses = this.frontDao.listCourseByType(11, 0, 1, true);
	// if (this.zxCourses != null)
	// for (int i = 0; i < this.zxCourses.size(); ++i) {
	// String name = ((Course)this.zxCourses.get(i)).getName();
	// ((Course)this.zxCourses.get(i)).setName(
	// (name.length() > 15) ? name.substring(0, 14) + "..." :
	// name);
	// }
	// this.zxKnows = this.frontDao.listZxKnows(8, 0);
	// for (int i = 0; i < this.zxKnows.size(); ++i) {
	// String name = ((Knowledge)this.zxKnows.get(i)).getTitle();
	// ((Knowledge)this.zxKnows.get(i)).setTitle(
	// (name.length() > 13) ? name.substring(0, 11) + "..." : name);
	// name = ((Knowledge)this.zxKnows.get(i)).getContent();
	// name = CheckHtml.getString(name);
	// ((Knowledge)this.zxKnows.get(i)).setContent(
	// (name.length() > 21) ? name.substring(0, 20) + "..." :
	// name);
	// }
	// //推荐资源
	// this.tjKnows = this.frontDao.listHotKnows(8, 0, 1);
	// for (int i = 0; i < this.tjKnows.size(); ++i) {
	// String name = ((Knowledge)this.tjKnows.get(i)).getTitle();
	// String Content = ((Knowledge)this.tjKnows.get(i)).getContent();
	// Content = CheckHtml.getString(Content);
	// ((Knowledge)this.tjKnows.get(i)).setTitle(
	// (name.length() > 10) ? name.substring(0, 10) + "...": name);
	// ((Knowledge)this.tjKnows.get(i)).setContent(
	// (Content.length() > 20) ? Content.substring(0, 20) + "..." : Content);
	// }
	// //推荐资源_New (第一条 图文 hot 为重点)
	// this.tjKnows_tw_zd = this.frontDao.listHotKnows(1, 0, 3);
	// for (int i = 0; i < this.tjKnows_tw_zd.size(); ++i) {
	// String name = ((Knowledge)this.tjKnows_tw_zd.get(i)).getTitle();
	// String Content = ((Knowledge)this.tjKnows_tw_zd.get(i)).getContent();
	// Content = CheckHtml.getString(Content);
	// ((Knowledge)this.tjKnows_tw_zd.get(i)).setTitle(
	// (name.length() > 10) ? name.substring(0, 10) + "...": name);
	// ((Knowledge)this.tjKnows_tw_zd.get(i)).setContent(
	// (Content.length() > 20) ? Content.substring(0, 20) + "..." : Content);
	// }
	// //推荐资源_New (剩余4条 图文 hot 为推荐)
	// this.tjKnows_wb_tj = this.frontDao.listHotKnows(5, 0, 1);
	// for (int i = 0; i < this.tjKnows_wb_tj.size(); ++i) {
	// String name = ((Knowledge)this.tjKnows_wb_tj.get(i)).getTitle();
	// String Content = ((Knowledge)this.tjKnows_wb_tj.get(i)).getContent();
	// Content = CheckHtml.getString(Content);
	// ((Knowledge)this.tjKnows_wb_tj.get(i)).setTitle(
	// (name.length() > 15) ? name.substring(0, 15) + "...": name);
	// // ((Knowledge)this.tjKnows_wb_tj.get(i)).setContent(
	// // (Content.length() > 20) ? Content.substring(0, 20) + "..." : Content);
	// }
	//		    
	// //帮助中心
	// this.zxlxxy = this.frontDao.listHotNnows(5, 0, 13, 1);
	//
	// for (int i = 0; i < this.zxlxxy.size(); ++i) {
	// String name = ((News)this.zxlxxy.get(i)).getTitle();
	// String Content = (((News)this.zxlxxy.get(i)).getContent() == null) ? "" :
	// ((News)this.zxlxxy.get(i)).getContent();
	// Content = CheckHtml.getString(Content);
	// ((News)this.zxlxxy.get(i)).setTitle(
	// (name.length() > 10) ? name.substring(0, 10) + "...": name);
	// ((News)this.zxlxxy.get(i)).setContent(
	// (Content.length() > 20) ? Content.substring(0, 20) + "..." : Content);
	// }
	// //帮助中心_New （图文——重点）
	// this.zxlxxy_tw_zd = this.frontDao.listHotNnows(1, 0, 13, 3);
	// for (int i = 0; i < this.zxlxxy_tw_zd.size(); ++i) {
	// String name = ((News)this.zxlxxy_tw_zd.get(i)).getTitle();
	// String Content = (((News)this.zxlxxy_tw_zd.get(i)).getContent() == null)
	// ? "" : ((News)this.zxlxxy_tw_zd.get(i)).getContent();
	// Content = CheckHtml.getString(Content);
	// ((News)this.zxlxxy_tw_zd.get(i)).setTitle(
	// (name.length() > 10) ? name.substring(0, 10) + "...": name);
	// ((News)this.zxlxxy_tw_zd.get(i)).setContent(
	// (Content.length() > 20) ? Content.substring(0, 20) + "..." : Content);
	// }
	// //帮助中心_New （文本——推荐）
	// this.zxlxxy_wb_tj = this.frontDao.listHotNnows(4, 0, 13, 1);
	// for (int i = 0; i < this.zxlxxy_wb_tj.size(); ++i) {
	// String name = ((News)this.zxlxxy_wb_tj.get(i)).getTitle();
	// String Content = (((News)this.zxlxxy_wb_tj.get(i)).getContent() == null)
	// ? "" : ((News)this.zxlxxy_wb_tj.get(i)).getContent();
	// Content = CheckHtml.getString(Content);
	// ((News)this.zxlxxy_wb_tj.get(i)).setTitle(
	// (name.length() > 15) ? name.substring(0, 15) + "...": name);
	// // ((News)this.zxlxxy_tw_zd.get(i)).setContent(
	// // (Content.length() > 20) ? Content.substring(0, 20) + "..." : Content);
	// }
	//		    
	// //下载中心_文本 4条 推荐
	// this.zxxzzx = this.frontDao.listNewsByTidhot(4, 0, 14, 1);
	// for (int i = 0; i < this.zxxzzx.size(); ++i) {
	// String name = ((News)this.zxxzzx.get(i)).getTitle();
	// String Content = (((News)this.zxxzzx.get(i)).getContent() == null) ? "" :
	// ((News)this.zxxzzx.get(i)).getContent();
	// Content = CheckHtml.getString(Content);
	// ((News)this.zxxzzx.get(i)).setTitle(
	// (name.length() > 15) ? name.substring(0, 15) + "...": name);
	// ((News)this.zxxzzx.get(i)).setContent(
	// (Content.length() > 20) ? Content.substring(0, 20) + "..." : Content);
	// }
	// //下载中心_图文重点 1条
	// this.zxxzzx_tw_zd = this.frontDao.listNewsByTidhot(1, 0, 14, 3);
	// for (int i = 0; i < this.zxxzzx_tw_zd.size(); ++i) {
	// String name = ((News)this.zxxzzx_tw_zd.get(i)).getTitle();
	// String Content = (((News)this.zxxzzx_tw_zd.get(i)).getContent() == null)
	// ? "" : ((News)this.zxxzzx_tw_zd.get(i)).getContent();
	// Content = CheckHtml.getString(Content);
	// ((News)this.zxxzzx_tw_zd.get(i)).setTitle(
	// (name.length() > 10) ? name.substring(0, 10) + "...": name);
	// ((News)this.zxxzzx_tw_zd.get(i)).setContent(
	// (Content.length() > 20) ? Content.substring(0, 20) + "..." : Content);
	// }
	//		    
	//		    
	// //推荐课程
	// // this.phCourses = this.frontDao.listCourseByHot(11, 0, 1);
	// // if (this.phCourses != null)
	// // for (int i = 0; i < this.phCourses.size(); ++i) {
	// // String name = ((Course)this.phCourses.get(i)).getName();
	// // ((Course)this.phCourses.get(i)).setName(
	// // (name.length() > 18) ? name.substring(0, 17) + "..." :
	// // name);
	// // }
	//		    
	// //热门课程
	// this.hotCourses = this.frontDao.listCourseByHot(4, 0, 2);//2热门
	// if (this.hotCourses != null)
	// for (int i = 0; i < this.hotCourses.size(); ++i) {
	// String name = ((Course)this.hotCourses.get(i)).getName();
	// ((Course)this.hotCourses.get(i)).setName(
	// (name.length() > 11) ? name.substring(0, 10) + "..." :
	// name);
	// }
	// //最新课程
	// this.newCourses = this.frontDao.listCourseByNewTime(4, 0, 1);// 1可申请
	// 0不可申请
	// if (this.newCourses != null)
	// for (int i = 0; i < this.newCourses.size(); ++i) {
	// String name = ((Course)this.newCourses.get(i)).getName();
	// ((Course)this.newCourses.get(i)).setName(
	// (name.length() > 11) ? name.substring(0, 10) + "..." :
	// name);
	// }
	// //在线培训班
	// this.newelclasss = this.frontDao.listClassByNewTime(4, 0, 1);// 1可申请
	// 0不可申请
	// if (this.newelclasss != null)
	// for (int i = 0; i < this.newelclasss.size(); ++i) {
	// String name = ((ElClass)this.newelclasss.get(i)).getName();
	// ((ElClass)this.newelclasss.get(i)).setName(
	// (name.length() > 11) ? name.substring(0, 10) + "..." :
	// name);
	// }
	// //在线考场
	// this.newErooms = this.frontDao.listExamRoomByNewTime(4, 0, 1);// 1可申请
	// 0不可申请
	// if (this.newErooms != null)
	// for (int i = 0; i < this.newErooms.size(); ++i) {
	// String name = ((ExamRoom)this.newErooms.get(i)).getTitle();
	// ((ExamRoom)this.newErooms.get(i)).setTitle(
	// (name.length() > 11) ? name.substring(0, 10) + "..." :
	// name);
	// }
	//		    
	// //获取弹窗新闻
	// News newspop=newsDao.getNewsInPop();//--/
	// getRequest().setAttribute("newspop", newspop);
	// //返回系统设置的参数是否可注册
	// registerstatus =
	// SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
	// //返回系统设置的参数https端口，pki路径
	// //httpsPath="https://"+ getRequest().getServerName() + ":"+port +
	// getRequest().getContextPath() + "/";
	// httpsPath=SystemConfOp.getHttpsPath(getRequest().getServerName(),
	// getRequest().getContextPath());
	// httpPath=SystemConfOp.getHttpPath(getRequest().getServerName(),
	// getRequest().getContextPath());
	// //判断是否登入记录ip
	// //获取登入是否记录ip
	// int
	// isLoginIp=SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP);
	// if(isLoginIp==1){
	// return "index_";
	// }
	//			
	// return "index";
	// }

	// public String index() throws ElException{
	// this.frontDao.updateFlow();
	// 新闻类别说明：10：新闻动态 11：经验交流 12：教学公告 13：帮助中心
	// 新闻热度说明：0：普通 1：推荐 2:热门 3：重点 4：头条
	// 获取 推荐 的 新闻动态
	// this.xydts = this.frontDao.listNewsByTidhot(11, 0,
	// ElConstants.NTYPE_XWDT, ElConstants.HOT_TJ);// --/
	// if (this.xydts != null){
	// for (int i = 0; i < this.xydts.size(); ++i) {
	// String name = this.xydts.get(i).getTitle();
	// if (i == 0){
	// this.xydts.get(i).setTitle((name.length() > 11) ? name.substring(0,10) :
	// name);
	// }else{
	// this.xydts.get(i).setTitle((name.length() > 18) ? name.substring(0,17)+
	// "..." : name);
	// }
	// }
	// }
	// 获取 推荐 的 经验交流
	// this.szlls = this.frontDao.listNewsByTidhot(11, 0,
	// ElConstants.NTYPE_JYJL, ElConstants.HOT_TJ);
	// if (this.szlls != null){
	// for (int i = 0; i < this.szlls.size(); ++i) {
	// String name = this.szlls.get(i).getTitle();
	// if (i == 0){
	// this.szlls.get(i).setTitle((name.length() > 11) ? name.substring(0,10) :
	// name);
	// }else{
	// this.szlls.get(i).setTitle((name.length() > 18) ? name.substring(0,17)+
	// "..." : name);
	// }
	// }
	// }
	// //获取 推荐 的 教学公告
	// this.zxzxs = this.frontDao.listNewsByTidhot(5, 0, ElConstants.NTYPE_JXGG,
	// ElConstants.HOT_TJ);
	// if (this.zxzxs != null) {
	// for (int i = 0; i < this.zxzxs.size(); ++i) {
	// String name = this.zxzxs.get(i).getTitle();
	// if (i == 0){
	// this.zxzxs.get(i).setTitle((name.length() > 11) ? name.substring(0,9)+
	// "..." : name);
	// }else {
	// this.zxzxs.get(i).setTitle((name.length() > 14) ? name.substring(0,13)+
	// "..." : name);
	// }
	// String content = this.zxzxs.get(i).getContent();
	// content = CheckHtml.getString(content);
	// this.zxzxs.get(i).setContent((content.length() > 21) ?
	// content.substring(0, 19)+ "..." : content);
	// }
	// }
	// // 教学公告_图文_重点
	// this.zxzxs_tw_zd = this.frontDao.listNewsByTidhot(1, 0,
	// ElConstants.NTYPE_JXGG, ElConstants.HOT_ZD);
	// if (this.zxzxs_tw_zd != null) {
	// for (int i = 0; i < this.zxzxs_tw_zd.size(); ++i) {
	// String name = this.zxzxs_tw_zd.get(i).getTitle();
	// String Content = this.zxzxs.get(i).getContent();
	// this.zxzxs_tw_zd.get(i).setTitle((name.length() > 11) ? name.substring(0,
	// 9)+ "..." : name);
	// Content = CheckHtml.getString(Content);
	// this.zxzxs_tw_zd.get(i).setContent((Content.length() > 21) ?
	// Content.substring(0, 19)+ "..." : Content);
	// }
	// }
	// // 教学公告_文本_推荐
	// this.zxzxs_wb_tj = this.frontDao.listNewsByTidhot(4, 0,
	// ElConstants.NTYPE_JXGG, ElConstants.HOT_TJ);
	// if (this.zxzxs_wb_tj != null) {
	// for (int i = 0; i < this.zxzxs_wb_tj.size(); ++i) {
	// String name = this.zxzxs_wb_tj.get(i).getTitle();
	// this.zxzxs_wb_tj.get(i).setTitle((name.length() > 14) ? name.substring(0,
	// 13)+ "..." : name);
	// }
	// }
	// //获取根节点下的最新新闻（首页上的flash用到）
	// this.zxNews = this.frontDao.listNewsByTid(3, 0, 1, true, "");
	// // top居中的头条(根类别下)
	// this.zxNewss = this.frontDao.listNewsByTidhot(2, 0, 1,
	// ElConstants.HOT_TT);
	// if (this.zxNewss != null) {
	// for (int i = 0; i < this.zxNewss.size(); ++i) {
	// String name = this.zxNewss.get(i).getTitle();
	// this.zxNewss.get(i).setTitle((name.length() > 20) ? name.substring(0,
	// 20)+ "..." : name);
	// String content = ((News) this.zxNewss.get(i)).getContent();
	// content = CheckHtml.getString(content);
	// this.zxNewss.get(i).setContent((content.length() > 56) ?
	// content.substring(0, 53)+ "..." : content);
	// }
	// }
	// // top居中的头条 (一图文 3文本)(根节点下)
	// this.zxNewss_tw_wb = this.frontDao.listNewsByTidhot(4, 0, 1,
	// ElConstants.HOT_TT);
	// if (this.zxNewss_tw_wb != null) {
	// for (int i = 0; i < this.zxNewss_tw_wb.size(); ++i) {
	// String name = this.zxNewss_tw_wb.get(i).getTitle();
	// if (i == 0){
	// this.zxNewss_tw_wb.get(i).setTitle((name.length() > 20) ?
	// name.substring(0,20)+ "..." : name);
	// }else {
	// this.zxNewss_tw_wb.get(i).setTitle((name.length() > 14) ?
	// name.substring(0,13)+ "..." : name);
	// }
	// String Content = this.zxNewss_tw_wb.get(i).getContent();
	// Content = CheckHtml.getString(Content);
	// this.zxNewss_tw_wb.get(i).setContent((Content.length() > 56) ?
	// Content.substring(0, 53)+ "..." : Content);
	// }
	// }

	// 获取帖子信息
	// // 推荐交流文章
	// this.rmforums = this.forumAdminDao.listForumsByRm(0, 6);
	// if (this.rmforums != null) {
	// for (int i = 0; i < this.rmforums.size(); ++i) {
	// String name = this.rmforums.get(i).getTitle();
	// this.rmforums.get(i).setTitle((name.length() > 15) ? name.substring(0,
	// 15)+ "..." : name);
	// }
	// }
	// // 最新交流文章
	// this.zxforums = this.forumAdminDao.listForumsByZx(7, 0);
	// if (this.zxforums != null) {
	// for (int i = 0; i < this.zxforums.size(); ++i) {
	// String name = this.zxforums.get(i).getTitle();
	// this.zxforums.get(i).setTitle((name.length() > 15) ? name.substring(0,
	// 15)+ "..." : name);
	// }
	// }
	// 最新课程
	// this.zxCourses = this.frontDao.listCourseByType(11, 0, 1, true);
	// if (this.zxCourses != null){
	// for (int i = 0; i < this.zxCourses.size(); ++i) {
	// String name = this.zxCourses.get(i).getName();
	// this.zxCourses.get(i).setName((name.length() > 15) ? name.substring(0,
	// 14)+ "..." : name);
	// }
	// }
	// //最新资源
	// this.zxKnows = this.frontDao.listZxKnows(8, 0);
	// if(this.zxKnows!=null){
	// for (int i = 0; i < this.zxKnows.size(); ++i) {
	// String name = this.zxKnows.get(i).getTitle();
	// this.zxKnows.get(i).setTitle((name.length() > 13) ? name.substring(0,
	// 11)+ "..." : name);
	// name = this.zxKnows.get(i).getContent();
	// name = CheckHtml.getString(name);
	// this.zxKnows.get(i).setContent((name.length() > 21) ? name.substring(0,
	// 20)+ "..." : name);
	// }
	// }
	// // 推荐资源
	// this.tjKnows = this.frontDao.listHotKnows(8, 0, 1);
	// if(this.tjKnows!=null){
	// for (int i = 0; i < this.tjKnows.size(); ++i) {
	// String name = this.tjKnows.get(i).getTitle();
	// String Content = this.tjKnows.get(i).getContent();
	// Content = CheckHtml.getString(Content);
	// this.tjKnows.get(i).setTitle((name.length() > 10) ? name.substring(0,
	// 10)+ "..." : name);
	// this.tjKnows.get(i).setContent((Content.length() > 20) ?
	// Content.substring(0,20)+ "..." : Content);
	// }
	// }
	// // 推荐资源_New (第一条 图文 hot 为重点)
	// this.tjKnows_tw_zd = this.frontDao.listHotKnows(1, 0, 3);
	// if(this.tjKnows_tw_zd!=null){
	// for (int i = 0; i < this.tjKnows_tw_zd.size(); ++i) {
	// String name = this.tjKnows_tw_zd.get(i).getTitle();
	// String Content = this.tjKnows_tw_zd.get(i).getContent();
	// Content = CheckHtml.getString(Content);
	// this.tjKnows_tw_zd.get(i).setTitle((name.length() > 10) ?
	// name.substring(0, 10)+ "..." : name);
	// this.tjKnows_tw_zd.get(i).setContent((Content.length() > 20) ?
	// Content.substring(0, 20) + "...": Content);
	// }
	// }
	// // 推荐资源_New (剩余4条 图文 hot 为推荐)
	// this.tjKnows_wb_tj = this.frontDao.listHotKnows(5, 0, 1);
	// if(this.tjKnows_wb_tj!=null){
	// for (int i = 0; i < this.tjKnows_wb_tj.size(); ++i) {
	// String name = this.tjKnows_wb_tj.get(i).getTitle();
	// String Content = this.tjKnows_wb_tj.get(i).getContent();
	// Content = CheckHtml.getString(Content);
	// this.tjKnows_wb_tj.get(i).setTitle((name.length() > 15) ?
	// name.substring(0, 15)+ "..." : name);
	// }
	// }
	// // 帮助中心 推荐
	// this.zxlxxy = this.frontDao.listHotNnows(5, 0, ElConstants.NTYPE_BZZX,
	// ElConstants.HOT_TJ);
	// if(this.zxlxxy!=null){
	// for (int i = 0; i < this.zxlxxy.size(); ++i) {
	// String name = this.zxlxxy.get(i).getTitle();
	// String Content = this.zxlxxy.get(i).getContent() == null ? "":
	// this.zxlxxy.get(i).getContent();
	// Content = CheckHtml.getString(Content);
	// this.zxlxxy.get(i).setTitle((name.length() > 10) ? name.substring(0, 10)+
	// "..." : name);
	// this.zxlxxy.get(i).setContent((Content.length() > 20) ?
	// Content.substring(0,20)+ "..." : Content);
	// }
	// }
	// // 帮助中心_New （图文——重点）
	// this.zxlxxy_tw_zd = this.frontDao.listHotNnows(1, 0,
	// ElConstants.NTYPE_BZZX, ElConstants.HOT_ZD);
	// if(this.zxlxxy_tw_zd!=null){
	// for (int i = 0; i < this.zxlxxy_tw_zd.size(); ++i) {
	// String name = this.zxlxxy_tw_zd.get(i).getTitle();
	// String Content = this.zxlxxy_tw_zd.get(i).getContent() == null ? "":
	// this.zxlxxy_tw_zd.get(i).getContent();
	// Content = CheckHtml.getString(Content);
	// this.zxlxxy_tw_zd.get(i).setTitle((name.length() > 10) ?
	// name.substring(0, 10)+ "..." : name);
	// this.zxlxxy_tw_zd.get(i).setContent((Content.length() > 20) ?
	// Content.substring(0,20)+ "..." : Content);
	// }
	// }
	// // 帮助中心_New （文本——推荐）
	// this.zxlxxy_wb_tj = this.frontDao.listHotNnows(4, 0,
	// ElConstants.NTYPE_BZZX, ElConstants.HOT_TJ);
	// if(this.zxlxxy_wb_tj!=null){
	// for (int i = 0; i < this.zxlxxy_wb_tj.size(); ++i) {
	// String name = ((News) this.zxlxxy_wb_tj.get(i)).getTitle();
	// String Content = this.zxlxxy_wb_tj.get(i).getContent() == null ? "":
	// this.zxlxxy_wb_tj.get(i).getContent();
	// Content = CheckHtml.getString(Content);
	// this.zxlxxy_wb_tj.get(i).setTitle((name.length() > 15) ?
	// name.substring(0, 15)+ "..." : name);
	// }
	// }
	// // 下载中心_文本 4条 推荐
	// this.zxxzzx = this.frontDao.listNewsByTidhot(4, 0,
	// ElConstants.NTYPE_XXZX, ElConstants.HOT_TJ);
	// if(this.zxxzzx!=null){
	// for (int i = 0; i < this.zxxzzx.size(); ++i) {
	// String name = ((News) this.zxxzzx.get(i)).getTitle();
	// String Content = this.zxxzzx.get(i).getContent() == null ? "":
	// this.zxxzzx.get(i).getContent();
	// Content = CheckHtml.getString(Content);
	// this.zxxzzx.get(i).setTitle((name.length() > 15) ? name.substring(0, 15)+
	// "..." : name);
	// this.zxxzzx.get(i).setContent((Content.length() > 20) ?
	// Content.substring(0,20)+ "..." : Content);
	// }
	// }
	// // 下载中心_图文重点 1条
	// this.zxxzzx_tw_zd = this.frontDao.listNewsByTidhot(1, 0,
	// ElConstants.NTYPE_XXZX, ElConstants.HOT_ZD);
	// if(this.zxxzzx_tw_zd!=null){
	// for (int i = 0; i < this.zxxzzx_tw_zd.size(); ++i) {
	// String name = this.zxxzzx_tw_zd.get(i).getTitle();
	// String Content = this.zxxzzx_tw_zd.get(i).getContent() == null ? "":
	// this.zxxzzx_tw_zd.get(i).getContent();
	// Content = CheckHtml.getString(Content);
	// this.zxxzzx_tw_zd.get(i).setTitle((name.length() > 10) ?
	// name.substring(0, 10)+ "..." : name);
	// this.zxxzzx_tw_zd.get(i).setContent((Content.length() > 20) ?
	// Content.substring(0,20)+ "..." : Content);
	// }
	// }
	// 推荐课程
	// this.phCourses = this.frontDao.listCourseByHot(11, 0, 1);
	// if (this.phCourses != null)
	// for (int i = 0; i < this.phCourses.size(); ++i) {
	// String name = ((Course)this.phCourses.get(i)).getName();
	// ((Course)this.phCourses.get(i)).setName(
	// (name.length() > 18) ? name.substring(0, 17) + "..." :
	// name);
	// }

	// // 热门课程
	// this.hotCourses = this.frontDao.listCourseByHot(4, 0, 2);// 2热门
	// if (this.hotCourses != null){
	// for (int i = 0; i < this.hotCourses.size(); ++i) {
	// String name = this.hotCourses.get(i).getName();
	// this.hotCourses.get(i).setName((name.length() > 11) ? name.substring(0,
	// 10)+ "..." : name);
	// }
	// }
	// // 最新课程
	// this.newCourses = this.frontDao.listCourseByNewTime(4, 0, 1);// 1可申请
	// 0不可申请
	// if (this.newCourses != null){
	// for (int i = 0; i < this.newCourses.size(); ++i) {
	// String name = this.newCourses.get(i).getName();
	// this.newCourses.get(i).setName((name.length() > 11) ? name.substring(0,
	// 10)+ "..." : name);
	// }
	// }
	// // 在线培训班
	// this.newelclasss = this.frontDao.listClassByNewTime(4, 0, 1);// 1可申请
	// 0不可申请
	// if (this.newelclasss != null){
	// for (int i = 0; i < this.newelclasss.size(); ++i) {
	// String name = this.newelclasss.get(i).getName();
	// this.newelclasss.get(i).setName((name.length() > 11) ? name.substring(0,
	// 10)+ "..." : name);
	// }
	// }
	// // 在线考场
	// this.newErooms = this.frontDao.listExamRoomByNewTime(4, 0, 1);// 1可申请
	// 0不可申请
	// if (this.newErooms != null){
	// for (int i = 0; i < this.newErooms.size(); ++i) {
	// String name = this.newErooms.get(i).getTitle();
	// this.newErooms.get(i).setTitle((name.length() > 11) ? name.substring(0,
	// 10)+ "..." : name);
	// }
	// }
	// // 获取弹窗新闻
	// newspop = newsDao.getNewsInPop();
	// getRequest().setAttribute("newspop", newspop);
	// // 返回系统设置的参数是否可注册
	// registerstatus =
	// SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
	// // 返回系统设置的参数https端口，pki路径
	// httpsPath = SystemConfOp.getHttpsPath(getRequest().getServerName(),
	// getRequest().getContextPath());
	// httpPath = SystemConfOp.getHttpPath(getRequest().getServerName(),
	// getRequest().getContextPath());
	// // 获取登入是否记录ip
	// int isLoginIp =
	// SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP);
	// if (isLoginIp == 1) {
	// return "index_";
	// }
	// return "index";
	// }

	public String index() throws ElException {
		// 获取数据
		// 新闻
		Object id = getSession().getAttribute(ElConstants.SESSION_USERID);
		if(id!=null){
			elUser = userDao.getUserById((Integer)id);
			getRequest().setAttribute("name", elUser.getRealname());
		}
		xydts = this.indexDataUtil.getIndexNewsList("xydts");
		szlls = this.indexDataUtil.getIndexNewsList("szlls");
		zxzxs = this.indexDataUtil.getIndexNewsList("zxzxs");
		zxzxs_tw_zd = this.indexDataUtil.getIndexNewsList("zxzxs_tw_zd");
		zxzxs_wb_tj = this.indexDataUtil.getIndexNewsList("zxzxs_wb_tj");
		zxNews = this.indexDataUtil.getIndexNewsList("zxNews");
		zxNewss = this.indexDataUtil.getIndexNewsList("zxNewss");
		zxNewss_tw_wb = this.indexDataUtil.getIndexNewsList("zxNewss_tw_wb");
		zxlxxy = this.indexDataUtil.getIndexNewsList("zxlxxy");
		zxlxxy_tw_zd = this.indexDataUtil.getIndexNewsList("zxlxxy_tw_zd");
		zxlxxy_wb_tj = this.indexDataUtil.getIndexNewsList("zxlxxy_wb_tj");
		zxxzzx = this.indexDataUtil.getIndexNewsList("zxxzzx");
		zxxzzx_tw_zd = this.indexDataUtil.getIndexNewsList("zxxzzx_tw_zd");
		// 帖子
		rmforums = this.indexDataUtil.getIndexForumList("rmforums");
		zxforums = this.indexDataUtil.getIndexForumList("zxforums");
		
		// 资料
		zxKnows = this.indexDataUtil.getIndexKnowledgeList("zxKnows");
		tjKnows = this.indexDataUtil.getIndexKnowledgeList("tjKnows");
		tjKnows_tw_zd = this.indexDataUtil
				.getIndexKnowledgeList("tjKnows_tw_zd");
		tjKnows_wb_tj = this.indexDataUtil
				.getIndexKnowledgeList("tjKnows_wb_tj");
		// 课程
		zxCourses = this.indexDataUtil.getIndexCourseList("zxCourses");
		hotCourses = this.indexDataUtil.getIndexCourseList("hotCourses");
		newCourses = this.indexDataUtil.getIndexCourseList("newCourses");
		// 考场
		newErooms = this.indexDataUtil.getIndexExamroomList("newErooms");
		// 培训班

		// 保险大厅
		baoxianProductList = baoxianProductDao.getSixFrontBaoxianProductList(4,
				1);
		// 会员中心
		pfmsUserList = indexDao.listFrontUsers(4, 1);
		// 产品中心
		productList = productDao.getFrontProductList(4, 1);

		newelclasss = this.indexDataUtil.getIndexElclassList("newelclasss");
		// 获取弹窗新闻
		newspop = this.indexDataUtil.getIndexPopNews();
		//获得bid为76的版块前五条
		fiveForums = forumAdminDao.listForumsList_wsj(76, 5, 0);
//		// 获取数据
//		// 新闻
//		xydts = this.indexDataUtil.getIndexNewsList("xydts");
//		szlls = this.indexDataUtil.getIndexNewsList("szlls");
//		zxzxs = this.indexDataUtil.getIndexNewsList("zxzxs");
//		zxzxs_tw_zd = this.indexDataUtil.getIndexNewsList("zxzxs_tw_zd");
//		zxzxs_wb_tj = this.indexDataUtil.getIndexNewsList("zxzxs_wb_tj");
//		zxNews = this.indexDataUtil.getIndexNewsList("zxNews");
//		zxNewss = this.indexDataUtil.getIndexNewsList("zxNewss");
//		zxNewss_tw_wb = this.indexDataUtil.getIndexNewsList("zxNewss_tw_wb");
//		zxlxxy = this.indexDataUtil.getIndexNewsList("zxlxxy");
//		zxlxxy_tw_zd = this.indexDataUtil.getIndexNewsList("zxlxxy_tw_zd");
//		zxlxxy_wb_tj = this.indexDataUtil.getIndexNewsList("zxlxxy_wb_tj");
//		zxxzzx = this.indexDataUtil.getIndexNewsList("zxxzzx");
//		zxxzzx_tw_zd = this.indexDataUtil.getIndexNewsList("zxxzzx_tw_zd");
//		// 帖子
//		rmforums = this.indexDataUtil.getIndexForumList("rmforums");
//		zxforums = this.indexDataUtil.getIndexForumList("zxforums");
//		// 资料
//		zxKnows = this.indexDataUtil.getIndexKnowledgeList("zxKnows");
//		tjKnows = this.indexDataUtil.getIndexKnowledgeList("tjKnows");
//		tjKnows_tw_zd = this.indexDataUtil
//				.getIndexKnowledgeList("tjKnows_tw_zd");
//		tjKnows_wb_tj = this.indexDataUtil
//				.getIndexKnowledgeList("tjKnows_wb_tj");
//		// 课程
//		zxCourses = this.indexDataUtil.getIndexCourseList("zxCourses");
//		hotCourses = this.indexDataUtil.getIndexCourseList("hotCourses");
//		newCourses = this.indexDataUtil.getIndexCourseList("newCourses");
//		// 考场
//		newErooms = this.indexDataUtil.getIndexExamroomList("newErooms");
//		// 培训班
//
//		// 保险大厅
//		baoxianProductList = baoxianProductDao.getSixFrontBaoxianProductList(4,
//				1);
//		// 会员中心
//		pfmsUserList = indexDao.listFrontUsers(4, 1);
//		// 产品中心
//		productList = productDao.getFrontProductList(4, 1);
//
//		newelclasss = this.indexDataUtil.getIndexElclassList("newelclasss");
//		// 获取弹窗新闻
//		newspop = this.indexDataUtil.getIndexPopNews();
		// 返回系统设置的参数是否可注册
		
		//投票
		//poll=frontDao.getPoolMaxId();
		
		//系统培训班
		MyClass  cla = studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
		if(cla!=null){
			ExamRoom room  = null;
			if(cla.getElClass().getId()!=0){
				int roomid = eroomDao.getRoomidByClassid_cisco(cla.getElClass().getId());
				if(cla.getElClass().getId()!=0){
					room = eroomDao.getExamRoomByid(roomid);
					cla.getElClass().setExamRooms(eroomDao.listExamRoomByClass_cisco(cla.getElClass().getId(),getSessionIntValue(ElConstants.SESSION_USERID)));
					for(int i=0;i<cla.getElClass().getExamRooms().size();i++){
						cla.getElClass().getExamRooms().get(i).setIsPass(eroomDao.getIsPass(getSessionIntValue(ElConstants.SESSION_USERID),cla.getElClass().getExamRooms().get(i).getId()));
					}
				}
					
				else{
					room = new ExamRoom();
					room.setId(0);
				}
			}
			//判断首页显示哪个图片
			//如果当前用户未买培训班，则显示第一幅图片，如果已购买培训班，则显示第二幅图片，如果已达到考试条件，则显示第三幅图片，如果考试通过，则显示第四幅图片
			//系统培训班
			MyClass myClass = studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
			boolean flag = classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID), myClass.getElClass().getId());
			if(!flag){//未购买培训班
				step = 1;
			}else{
				step = 2;//购买培训班
//				if(cla.getElClass().getExamRooms().get(0).getm == 1){
//					step = 3;
//				}
				if(myClass.getElClass().getClasstype()==2){ 
					//检测是否通过自主培训班
					studyClassDao.setMyPassclass_at(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
				}else{ 
					//检测是否通过培训班
					studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
				}
				int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),myClass.getElClass().getId());
				if(status==2){//已达到考试条件（即培训班获得了证书）
					step = 3;
				}
				if(cla.getElClass().getExamRooms()!=null&&cla.getElClass().getExamRooms().size()>0){
					if(cla.getElClass().getExamRooms().get(0).getIsPassed() == 1){//考试通过，可以查看证书
						step = 4;
					}
				}
			}
		}
		
		registerstatus = SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
		// 返回系统设置的参数https端口，pki路径
		httpsPath = SystemConfOp.getHttpsPath(getRequest().getServerName(),
				getRequest().getContextPath());
		httpPath = SystemConfOp.getHttpPath(getRequest().getServerName(),
				getRequest().getContextPath());
		// 获取登入是否记录ip
		int isLoginIp = SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP);
	//--------------------------卫生局0901修改 ---------------------------------------------------------
		//用户是否选择培训班
		if(getSessionValue(ElConstants.SESSION_USERNAME)!=null){
		isChangeElclass = studyClassDao.getIsChangeclass(getSessionIntValue(ElConstants.SESSION_USERID));
		}
		//系统培训班(拿证培训班)
		MyClass cla_ = studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
		
		
		
		//最新一期培训班，比较createtime
		//首先获取系统年份，再查询系统中年份为当前年份的培训班
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		isBuyNianjianClass=studyClassDao.isNianjianClass(getSessionIntValue(ElConstants.SESSION_USERID));
		MyClass new_cla_ = studyClassDao.getNaZhengClass(year);
		MyClass nianjian_cla_ = studyClassDao.getNianjianClass(year);
		
		if(isBuyNianjianClass==0){
		int classid=0;
		//以下为对最新一期培训班的处理
		if(new_cla_!=null){
		classid = new_cla_.getElClass().getId();
		}
		if(classid!=0){
//			int roomid = eroomDao.getRoomidByClassid_cisco(classid);
//			if(classid!=0 && roomid!=0){
//				room = eroomDao.getExamRoomByid(roomid);
//				new_cla_.getElClass().setExamRooms(eroomDao.listExamRoomByClass_cisco(classid,getSessionIntValue(ElConstants.SESSION_USERID)));
//				for(int i=0;i<new_cla_.getElClass().getExamRooms().size();i++){
//					new_cla_.getElClass().getExamRooms().get(i).setIsPass(eroomDao.getIsPass(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getExamRooms().get(i).getId()));
//				}
//			}
//				
//			else{
//				room = new ExamRoom();
//				room.setId(0);
//			}
			//检查拿证培训班或者年检培训班，用户是否已经购买
			boolean cla_check = false;
			if(cla_!=null){
				cla_check = studyClassDao.checkClassIsUser(cla_.getElClass().getId(), getSessionIntValue(ElConstants.SESSION_USERID));
			}
			boolean new_check = false;
			if(new_cla_!=null){
				new_check = studyClassDao.checkClassIsUser(new_cla_.getElClass().getId(), getSessionIntValue(ElConstants.SESSION_USERID));
			}
			if(cla_check || new_check){
				needAllocation = true;
				//培训班中的课程
				//查询培训班课程分配表
				studyCourseList = studyClassDao.getCourses(classid,getSessionIntValue(ElConstants.SESSION_USERID));
			}
			//培训班是否通过
			int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),classid);
			if(status==2){
				new_cla_.setPassed(true);
			}else{
				new_cla_.setPassed(false);
			}
			
		}
		
		
		
		studyCourseList = studyCourseList == null?new ArrayList<MyCourse>():studyCourseList;
		//学时及比例
		map = NewVersionUtil.getCourseProcess(studyCourseList);
		
		
//		MyClass myClass = studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
//		boolean flag = false;
		boolean flag1 = false;
		if(new_cla_!=null){
			flag1 = classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID), new_cla_.getElClass().getId());
		}
		boolean flag2 = false;
		if(cla_!=null){
			flag2 = classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID), cla_.getElClass().getId());
		}
//		int temp = 0;
		if(flag1 || flag2){//购买拿证培训班或者最新一期培训班
			if(flag1){//购买最新一期培训班
//				temp = 1;
				if(!flag1){//未购买培训班
					step = 1;
				}else{
					step = 2;//购买培训班
					if(new_cla_.getElClass().getClasstype()==2){ 
						//检测是否通过自主培训班
						studyClassDao.setMyPassclass_at(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getId());
					}else{ 
						//检测是否通过培训班
						studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getId());
					}
					int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getId());
					if(status==2){//已达到考试条件（即培训班获得了证书）
						step = 3;
					}
					if(isBuyNianjianClass==0){
					if(new_cla_.getElClass().getExamRooms()!=null&&new_cla_.getElClass().getExamRooms().size()>0){
						if(new_cla_.getElClass().getExamRooms().get(0).getIsPassed() == 1){//考试通过，可以查看证书
							step = 4;
						}
					}}else{
						if(nianjian_cla_.getElClass().getExamRooms()!=null&&nianjian_cla_.getElClass().getExamRooms().size()>0){
							if(nianjian_cla_.getElClass().getExamRooms().get(0).getIsPassed() == 1){//考试通过，可以查看证书
								step = 4;
							}
						}
					}
				}
				
			}else{
				if(!flag2){//未购买培训班
					step = 1;
				}else{
					step = 2;//购买培训班
					if(cla_.getElClass().getClasstype()==2){ 
						//检测是否通过自主培训班
						studyClassDao.setMyPassclass_at(getSessionIntValue(ElConstants.SESSION_USERID),cla_.getElClass().getId());
					}else{ 
						//检测是否通过培训班
						studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),cla_.getElClass().getId());
					}
					int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),cla_.getElClass().getId());
					if(status==2){//已达到考试条件（即培训班获得了证书）
						step = 3;
					}
					if(cla_.getElClass().getExamRooms()!=null&&cla_.getElClass().getExamRooms().size()>0){
						if(cla_.getElClass().getExamRooms().get(0).getIsPassed() == 1){//考试通过，可以查看证书
							step = 4;
						}
					}
				}
			}
		}}//分隔点
		else{
			int classid=0;
			//以下为对最新一期培训班的处理
			if(isBuyNianjianClass==0){
				if(new_cla_!=null){
				classid = new_cla_.getElClass().getId();
				}
			}else{
				if(nianjian_cla_!=null){
				classid = nianjian_cla_.getElClass().getId();
				}
			}
			
			if(classid!=0){
//				int roomid = eroomDao.getRoomidByClassid_cisco(classid);
//				if(classid!=0 && roomid!=0){
//					room = eroomDao.getExamRoomByid(roomid);
//					new_cla_.getElClass().setExamRooms(eroomDao.listExamRoomByClass_cisco(classid,getSessionIntValue(ElConstants.SESSION_USERID)));
//					for(int i=0;i<new_cla_.getElClass().getExamRooms().size();i++){
//						new_cla_.getElClass().getExamRooms().get(i).setIsPass(eroomDao.getIsPass(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getExamRooms().get(i).getId()));
//					}
//				}
//					
//				else{
//					room = new ExamRoom();
//					room.setId(0);
//				}
				//检查拿证培训班或者年检培训班，用户是否已经购买
				boolean cla_check = false;
				if(nianjian_cla_!=null){
					cla_check = studyClassDao.checkClassIsUser(nianjian_cla_.getElClass().getId(), getSessionIntValue(ElConstants.SESSION_USERID));
				}
				boolean new_check = false;
				if(nianjian_cla_!=null){
					new_check = studyClassDao.checkClassIsUser(nianjian_cla_.getElClass().getId(), getSessionIntValue(ElConstants.SESSION_USERID));
				}
				if(cla_check || new_check){
					needAllocation = true;
					//培训班中的课程
					//查询培训班课程分配表
					studyCourseList = studyClassDao.getCourses(classid,getSessionIntValue(ElConstants.SESSION_USERID));
				}
				//培训班是否通过
				int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),classid);
				if(status==2){
					nianjian_cla_.setPassed(true);
				}else{
					nianjian_cla_.setPassed(false);
				}
				
			}
			
			
			
			studyCourseList = studyCourseList == null?new ArrayList<MyCourse>():studyCourseList;
			//学时及比例
			map = NewVersionUtil.getCourseProcess(studyCourseList);
			
			
//			MyClass myClass = studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID));
//			boolean flag = false;
			boolean flag1 = false;
			if(nianjian_cla_!=null){
				flag1 = classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID), nianjian_cla_.getElClass().getId());
			}
			boolean flag2 = false;
			if(nianjian_cla_!=null){
				flag2 = classDao.checkElclassIsUsers(getSessionIntValue(ElConstants.SESSION_USERID), nianjian_cla_.getElClass().getId());
			}
//			int temp = 0;
			if(flag1 || flag2){//购买拿证培训班或者最新一期培训班
				if(flag1){//购买最新一期培训班
//					temp = 1;
					if(!flag1){//未购买培训班
						step = 1;
					}else{
						step = 2;//购买培训班
						if(nianjian_cla_.getElClass().getClasstype()==2){ 
							//检测是否通过自主培训班
							studyClassDao.setMyPassclass_at(getSessionIntValue(ElConstants.SESSION_USERID),nianjian_cla_.getElClass().getId());
						}else{ 
							//检测是否通过培训班
							studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),nianjian_cla_.getElClass().getId());
						}
						int status=0;
						if(isBuyNianjianClass==0){
							status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),new_cla_.getElClass().getId());
						}else{
							status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),nianjian_cla_.getElClass().getId());
						}
						if(status==2){//已达到考试条件（即培训班获得了证书）
							step = 3;
						}
						if(isBuyNianjianClass==0){
						if(new_cla_.getElClass().getExamRooms()!=null&&new_cla_.getElClass().getExamRooms().size()>0){
							if(new_cla_.getElClass().getExamRooms().get(0).getIsPassed() == 1){//考试通过，可以查看证书
								step = 4;
							}
						}}else{
							if(nianjian_cla_.getElClass().getExamRooms()!=null&&nianjian_cla_.getElClass().getExamRooms().size()>0){
								if(nianjian_cla_.getElClass().getExamRooms().get(0).getIsPassed() == 1){//考试通过，可以查看证书
									step = 4;
								}
							}
						}
					}
					
				}else{
					if(!flag2){//未购买培训班
						step = 1;
					}else{
						step = 2;//购买培训班
						if(nianjian_cla_.getElClass().getClasstype()==2){ 
							//检测是否通过自主培训班
							studyClassDao.setMyPassclass_at(getSessionIntValue(ElConstants.SESSION_USERID),nianjian_cla_.getElClass().getId());
						}else{ 
							//检测是否通过培训班
							studyClassDao.setMyPassclass(getSessionIntValue(ElConstants.SESSION_USERID),nianjian_cla_.getElClass().getId());
						}
						int status=studyClassDao.getStudyClassStatus(getSessionIntValue(ElConstants.SESSION_USERID),nianjian_cla_.getElClass().getId());
						if(status==2){//已达到考试条件（即培训班获得了证书）
							step = 3;
						}
						if(nianjian_cla_.getElClass().getExamRooms()!=null&&nianjian_cla_.getElClass().getExamRooms().size()>0){
							if(nianjian_cla_.getElClass().getExamRooms().get(0).getIsPassed() == 1){//考试通过，可以查看证书
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
		System.out.println(b);
		 if(b==true){
			 System.out.println("===============手机端访问====================");
			 if (isLoginIp == 1) {
					return "phone_index_";
				}
				if (!SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_ISEXAM))
					return "phone_index";
				return "login_phone";
		 }
		if (isLoginIp == 1) {
			return "index_";
		}
		if (!SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_ISEXAM))
			return "index";
		return "login";
	}
	
	 public String index2() throws ElException {
		    this.frontDao.updateFlow();
		    //最新资讯 显示更新时间最大的8条新闻。 
			this.zxNews = this.frontDao.listNewsByTidNewTime(6, 0, 1);
			for (int i = 0; i < this.zxNews.size(); i++) {
				String name = this.zxNews.get(i).getTitle();
				this.zxNews.get(i).setTitle(
						name.length() > 22 ? name.substring(0, 20) + "" : name); 
			}
			//最新课程 显示发布时间最大的8门课程的标题
			this.phCourses = this.frontDao.listCourseByNewTime(8, 0, -1);//-1不带是否可申请条件
			for (int i = 0; i < this.phCourses.size(); i++) {
				String name = this.phCourses.get(i).getName();
				this.phCourses.get(i).setName(
						name.length() > 12 ? name.substring(0,11) + "" : name);
			}
		    
		    
		    //推荐交流文章
		    this.rmforums = this.forumAdminDao.listForumsByRm(0, 6);
		    if (this.rmforums != null) {
			      for (int i = 0; i < this.rmforums.size(); ++i) {
			        String name = ((Forum)this.rmforums.get(i)).getTitle();
			        ((Forum)this.rmforums.get(i)).setTitle(
			          (name.length() > 15) ? name.substring(0, 15) + "..." : 
			          name);
			      }
			    }
		    //单位排行榜初始化  只初始化系统配置的培训班排行榜。 如果全部初始化  会照成前台页面卡的情况。
		    //可以另做一个初始化 按钮 解决未全部培训班初始化的情况
		    elclass =  classDao.getElClassById(SystemConfOp.getIntValue((ElConstants.SYSTEM_CONF_INDEX_CLASSID)));     
			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
			if(depTree != null){ //部门比较  departments1来自页面查看按钮的部门    开放页面 查看 按钮 需要把下面的depTree 换成departments1即可 
				for (int i = 0; i < depTree.getChild().size(); i++) { 
					Department dep = new Department();
					dep.setId(depTree.getChild().get(i).getId());
					dep.setUserCount(classDao.getClassEval_CountNumberOfPeople(depTree.getChild().get(i), elclass));//总人数
					dep.setUserCount_(classDao.getClassEval_Pass_CountNumberOfPeople(depTree.getChild().get(i), elclass));//通过人数   
					dep.setRatioPassing(dep.getUserCount() == 0 ? 0 :getDou2(dep.getUserCount_(), dep.getUserCount()));//通过率
					if(!classDao.CheckElclassDepPassing(depTree.getChild().get(i), elclass)){
						classDao.addElclassDepPassing(depTree.getChild().get(i).getId(), elclass.getId(), dep.getRatioPassing());//保存改通过率
					} 
				} 
			}
		    //单位排行榜departments  
			phDeps = this.frontDao.getElclassDepPassing_phDeps(SystemConfOp.getIntValue((ElConstants.SYSTEM_CONF_INDEX_CLASSID)),10,0); 
		     
		    //推荐课程  显示设置为推荐的、更新时间最大的8门课程的标题，
		    this.tjCourses = this.frontDao.listCourseByHot(8, 0, 1);
		    if (this.tjCourses != null)
		      for (int i = 0; i < this.tjCourses.size(); ++i) {
		        String name = ((Course)this.tjCourses.get(i)).getName();
		        ((Course)this.tjCourses.get(i)).setName(
						name.length() > 12 ? name.substring(0,11) + "" : name); 
		      }
		    
		    //获取弹窗新闻
			News newspop=newsDao.getNewsInPop();//--/
			getRequest().setAttribute("newspop", newspop);
			//返回系统设置的参数是否可注册
			registerstatus = SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
			//返回系统设置的参数https端口，pki路径
			//httpsPath="https://"+ getRequest().getServerName() + ":"+port + getRequest().getContextPath() + "/";
			httpsPath=SystemConfOp.getHttpsPath(getRequest().getServerName(), getRequest().getContextPath());
			httpPath=SystemConfOp.getHttpPath(getRequest().getServerName(), getRequest().getContextPath());
			//System.out.println(httpsPath);
			//判断是否登入记录ip
			//获取登入是否记录ip
			int isLoginIp=SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP);
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				if(isLoginIp==1){
					return "index__phone";
				}
			    return "index_phone"; 
			}

			if(isLoginIp==1){
				return "index_";
			}
		    return "index";
//		    return "index_html";
	}
	 
	 public String index_jg() throws ElException {
		 
		 if(department!=null&&department.getId()!=0){
			int userid = userDao.getUserIdByDepid(department.getId()); 
			this.frontDao.updateFlow();
		    //最新资讯 显示更新时间最大的8条新闻。 
			this.zxNews = this.frontDao.listNewsByTidNewTime(6, 0, 1,department.getId());
			for (int i = 0; i < this.zxNews.size(); i++) {
				String name = this.zxNews.get(i).getTitle();
				this.zxNews.get(i).setTitle(
						name.length() > 22 ? name.substring(0, 20) + "" : name); 
			}
			//最新课程 显示发布时间最大的8门课程的标题
			this.phCourses = this.frontDao.listCourseByNewTime(8, 0, -1,department.getId());//-1不带是否可申请条件
			for (int i = 0; i < this.phCourses.size(); i++) {
				String name = this.phCourses.get(i).getName();
				this.phCourses.get(i).setName(
						name.length() > 12 ? name.substring(0,11) + "" : name);
			}
		    
		    
		    //推荐交流文章
		    this.rmforums = this.forumAdminDao.listForumsByRm(0, 6,department.getId());
		    if (this.rmforums != null) {
			      for (int i = 0; i < this.rmforums.size(); ++i) {
			        String name = ((Forum)this.rmforums.get(i)).getTitle();
			        ((Forum)this.rmforums.get(i)).setTitle(
			          (name.length() > 15) ? name.substring(0, 15) + "..." : 
			          name);
			      }
			    }
		    //资料中心
		    knowledges = knowledgeDao.listMyKlsNew(department.getId(), 10,1);
		    
		    //单位排行榜初始化  只初始化系统配置的培训班排行榜。 如果全部初始化  会照成前台页面卡的情况。
		    //可以另做一个初始化 按钮 解决未全部培训班初始化的情况
		    elclass =  classDao.getElClassById(SystemConfOp.getIntValue((ElConstants.SYSTEM_CONF_INDEX_CLASSID)));     
			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
			if(depTree != null){ //部门比较  departments1来自页面查看按钮的部门    开放页面 查看 按钮 需要把下面的depTree 换成departments1即可 
				for (int i = 0; i < depTree.getChild().size(); i++) { 
					Department dep = new Department();
					dep.setId(depTree.getChild().get(i).getId());
					dep.setUserCount(classDao.getClassEval_CountNumberOfPeople(depTree.getChild().get(i), elclass));//总人数
					dep.setUserCount_(classDao.getClassEval_Pass_CountNumberOfPeople(depTree.getChild().get(i), elclass));//通过人数   
					dep.setRatioPassing(dep.getUserCount() == 0 ? 0 :getDou2(dep.getUserCount_(), dep.getUserCount()));//通过率
					if(!classDao.CheckElclassDepPassing(depTree.getChild().get(i), elclass)){
						classDao.addElclassDepPassing(depTree.getChild().get(i).getId(), elclass.getId(), dep.getRatioPassing());//保存改通过率
					} 
				} 
			}
		    //单位排行榜departments  
			phDeps = this.frontDao.getElclassDepPassing_phDeps(SystemConfOp.getIntValue((ElConstants.SYSTEM_CONF_INDEX_CLASSID)),10,0); 
		     
		    //推荐课程  显示设置为推荐的、更新时间最大的8门课程的标题，
		    this.tjCourses = this.frontDao.listCourseByHot(8, 0, 1,department.getId());
		    if (this.tjCourses != null)
		      for (int i = 0; i < this.tjCourses.size(); ++i) {
		        String name = ((Course)this.tjCourses.get(i)).getName();
		        ((Course)this.tjCourses.get(i)).setName(
						name.length() > 12 ? name.substring(0,11) + "" : name); 
		      }
		    
		    //培训班
		    this.newelclasss = this.frontDao.listClassByNewTime(4, 0, 0,department.getId());// 1可申请
		    //考场
			this.newErooms = this.frontDao.listExamRoomByNewTime(4, 0, 0,department.getId());// 1可申请
			
		    //获取弹窗新闻
			News newspop=newsDao.getNewsInPop();//--/
			getRequest().setAttribute("newspop", newspop);
			//返回系统设置的参数是否可注册
			registerstatus = SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
			//返回系统设置的参数https端口，pki路径
			//httpsPath="https://"+ getRequest().getServerName() + ":"+port + getRequest().getContextPath() + "/";
			httpsPath=SystemConfOp.getHttpsPath(getRequest().getServerName(), getRequest().getContextPath());
			httpPath=SystemConfOp.getHttpPath(getRequest().getServerName(), getRequest().getContextPath());
			//System.out.println(httpsPath);
			//判断是否登入记录ip
			//获取登入是否记录ip
			int isLoginIp=SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP);
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		 }
	    
	    return "index_jg";
//		    return "index_html";
	}
	  

	public String searchforumList() throws ElException {
		if (getRequest().getParameter("fbtid") != null) {
			Forum fm = new Forum();
			ForumBlock forumBlock = new ForumBlock();
			forumBlock.setFbtype(new ForumBlockType());
			forumBlock.getFbtype().setId(
					Integer.parseInt(getRequest().getParameter("fbtid")));
			fm.setFblock(forumBlock);
			fm.setTitle(this.forum.getTitle());
			this.forum = fm;
		}
		zxforums = forumAdminDao.searchlistForums(getPageNow(), getPageSize(),
				forum, "zxforums");
		count = forumAdminDao.searchlistForumsSize(forum);
		fbtypes = forumAdminDao.listFbtypes();
		if (null != fbtypes) {
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(
						forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		jhforums = forumAdminDao.searchlistForumsByJh(0, 7);
		rmforums = forumAdminDao.searchlistForumsByRm(0, 7);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forum_search_phone"; 
		}

		return "forum_search";
	}

	public String coursetype() throws ElException {
		zxCourses = frontDao.listCourseByHot(12, 0, 1);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "coursetype_phone"; 
		}

		return "coursetype";
	}
	public String gclass_depph_list() throws ElException {
		elclass = classDao.getElClassById(elclass.getId());
		zxNews = frontDao.listNewsByTid(0, 10, 2, true, "");
		zxNotices = frontDao.listNewsByTid(0, 10, 3, true, "");
		if (null != zxNews)
			for (int i = 0; i < zxNews.size(); i++) {
				String title = zxNews.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 11 ? title
						.substring(0, 9)
						+ "" : title);
				zxNews.get(i).setTitle(title);
			}
		if (null != zxNotices)
			for (int i = 0; i < zxNotices.size(); i++) {
				String title = zxNotices.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 11 ? title
						.substring(0, 9)
						+ "" : title);
				zxNotices.get(i).setTitle(title);
			}
		phDeps = this.frontDao.getElclassDepPassing_phDeps(elclass.getId(),300,0); //300 指只输出300条数据
		unitRanks = studyQuizDao.getUnitRanks(elclass.getId()); 
//将修改为新的单位积分算法
		
		depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		
		if(unitRanks!=null){
			
			for (int i = 0; i < depTree.getChild().size(); i++) { 
				Department dep = new Department();
				for (UnitRanking u : unitRanks) {
					if(depTree.getChild().get(i).getId()==u.getUnit().getId()){
						
							
							dep.setUserCount(classDao.getClassEval_CountNumberOfPeople(depTree.getChild().get(i), elclass));//总人数
							dep.setUserCount_(classDao.getClassEval_Pass_CountNumberOfPeople(depTree.getChild().get(i), elclass));//通过人数 
							dep.setUserGaojiCount(classDao.getClassEval_CountNumberOfGaojiPeople(depTree.getChild().get(i), elclass));//高级职称人数
							dep.setUserGaojiPassCount(classDao.getClassEval_Pass_CountNumberOfGaojiPeople(depTree.getChild().get(i), elclass));//高级职称的通过人数
							String xiaji = departmentDao.getByIdXiaJi(u.getUnit().getId());//获取单位下级id串 
						xiaji = xiaji.length() > 0 ? xiaji + ","+u.getUnit().getId() : u.getUnit().getId()+"";
						UnitRanking unitRank1 = studyQuizDao.getDegreeScoreDetails(elclass.getId(), xiaji);
						u.setDegreeScore(unitRank1.getScore_Xl_TOTAL()/dep.getUserCount());
						UnitRanking unitRank2 = studyQuizDao.getTitleScoreDetails(elclass.getId(), xiaji);
						u.setTitleScore(unitRank2.getScore_Zc_TOTAL()/dep.getUserCount());
						u.setTotalScore(u.getBasedScore()+u.getDegreeScore()+u.getTitleScore());
						u.setFinalScore(u.getBasedScore()+u.getDegreeScore()+u.getTitleScore()+u.getAddCent());
						continue;
					}
				}
				
			}
		}
		ComparatorUser  c = new ComparatorUser();
		Collections.sort(unitRanks,c);
		int i =1;
		for (UnitRanking u : unitRanks) {
			u.setRanking(i);
			i++;
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "gclass_depph_list_phone"; 
		}

		return "gclass_depph_list";
	}
/**	public String gclass_depph_list() throws ElException {
		elclass = classDao.getElClassById(elclass.getId());
		zxNews = frontDao.listNewsByTid(0, 10, 2, true, "");
		zxNotices = frontDao.listNewsByTid(0, 10, 3, true, "");
		if (null != zxNews)
			for (int i = 0; i < zxNews.size(); i++) {
				String title = zxNews.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 11 ? title
						.substring(0, 9)
						+ "" : title);
				zxNews.get(i).setTitle(title);
			}
		if (null != zxNotices)
			for (int i = 0; i < zxNotices.size(); i++) {
				String title = zxNotices.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 11 ? title
						.substring(0, 9)
						+ "" : title);
				zxNotices.get(i).setTitle(title);
			}
		phDeps = statisticClassDao.listDepPassPer(elclass.getId());
		return "gclass_depph_list";
	}*/

	private NewsType ntypeTree;

	public NewsType getNtypeTree() {
		return ntypeTree;
	}

	public void setNtypeTree(NewsType ntypeTree) {
		this.ntypeTree = ntypeTree;
	}

	private NewsType ntype;

	public NewsType getNtype() {
		return ntype;
	}

	public void setNtype(NewsType ntype) {
		this.ntype = ntype;
	}

	public String newsIndex() throws ElException {
		modelstatus = SystemConfOp.getIntValue(ElConstants.MODEL_WORKING);
		boolean consub = true;
		ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,	ElConstants.TREE_FIANL, true);
		int nid = ntype==null ?newsDao.getNtypeRoot().getId():(ntype.getId()==0?1:ntype.getId());
		zxNews = newsDao.getNewsByUidByPerOrShar("",nid,ntypeTree,3, getPageNow(), getPageSize());
		count = newsDao.getNewsCountByUidByPerOrShar("",nid,ntypeTree,3);
		//zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
		zxNotices = frontDao.listNewsByTid_list(0, 6, 2, true, "");
		ntls=frontDao.getNewsType();
		fiveForums = forumAdminDao.listForumsList_wsj(76, 5, 0);
		if (null != zxNotices)
			for (int i = 0; i < zxNotices.size(); i++) {
				String title1 = zxNotices.get(i).getTitle();
				title1 = (title1 == null) ? "" : (title1.length() > 15 ? title1
						.substring(0, 13)
						+ "" : title1);
				zxNotices.get(i).setTitle(title1);
			}
		ctypeTree = ctypeDao.getCtypeTree(1, 0, true);
		if(news==null){
			news=new News();
		}
		if(news.getNtype()==null){
			NewsType nt=new NewsType();
			nt.setId(1);
			news.setNtype(nt);
			
		}
		news.setNtype(newsDao.getNtypeByid(news.getNtype().getId()));
		
		tjNews = newsDao.getTjNews(ntype.getId(),1);  //新闻热度1 为推荐
		zztjNews = newsDao.getTjNews(1);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "newsIndex_phone";
		}
		return "newsIndex";
		
		
	}
	public String newsIndex2() throws ElException {
		//int modelstatus = SystemConfOp.getIntValue(ElConstants.MODEL_WORKING); 
			//	int listnumber = SystemConfOp.getIntValue(ElConstants.LIST_PAGE_NUMBER);
			//int nid = ntype==null ?newsDao.getNtypeRoot().getId():(ntype.getId()==0?1:ntype.getId());
			int nid;
			ntls = newsDao.getAllNewsType();
			int listnumber = this.getNumber();
			for(int n=0;n<ntls.size();n++){
				nid = ntls.get(n).getId();
				for(int s=0;s<listnumber;s++){
					int pageNow = getPageNow();
					int pageSize = getPageSize();
					pageNow = pageNow*s+10;
					pageSize = pageSize+s*10;
					boolean consub = true;
					String title = news == null ? "" : (news.getTitle() == null ? "" : news
							.getTitle().trim());
					ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,	ElConstants.TREE_FIANL, true);
					zxNews = newsDao.getNewsByUidByPerOrShar("",nid,ntypeTree,3, pageNow, pageSize);
					count = newsDao.getNewsCountByUidByPerOrShar("",nid,ntypeTree,3);
					zxNotices = frontDao.listNewsByTid_list(0, 6, 2, true, "");
					ntls=frontDao.getNewsType();
					if (null != zxNotices)
						for (int i = 0; i < zxNotices.size(); i++) {
							String title1 = zxNotices.get(i).getTitle();
							title1 = (title1 == null) ? "" : (title1.length() > 15 ? title1
									.substring(0, 13)
									+ "" : title1);
							zxNotices.get(i).setTitle(title1);
						}
					ctypeTree = ctypeDao.getCtypeTree(1, 0, true);
					if(news==null){
						news=new News();
					}
					if(news.getNtype()==null){
						NewsType nt=new NewsType();
						nt.setId(1);
						news.setNtype(nt);
						
					}
					news.setNtype(newsDao.getNtypeByid(news.getNtype().getId()));
					
					int count1 = listnumber*10;
					Map root = new HashMap();
					root.put("newsList", zxNews);
					root.put("count1", count1);
					root.put("pageNow", pageNow);
					root.put("pageSize", pageSize);	
					int pageCount = 0;
					pageSize=10;
					if (count1 % pageSize == 0) {
						pageCount = count1 / pageSize;
					} else {
						pageCount = count1 / pageSize + 1;
					}
					List li = new LinkedList();
					if(pageCount>0){
						for(int i=0;i<pageCount;i++){
							li.add(i);
						}
					}
					root.put("li", li);
					root.put("pageCount", pageCount);
					root.put("pN", s);
					root.put("nid", nid);
					ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,	ElConstants.TREE_FIANL, true);
					root.put("ntypeTree", ntypeTree);
					root.put("nt", ntypeTree.getChild());
					String htmlName = "newsList_"+s+"_"+nid+".html";
					String ftl = "ftl/newsindex.ftl";
					FreePage frp = new FreePage();
					this.msg = frp.pageIn(root,htmlName,ftl);
					
				}
			}
				
			//	return "newsIndexView";
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "newsIndex_phone"; 
			}

				return "newsIndex";
		}
	
	public String newsIndex3() throws ElException {
			int nid = this.getNid();
			int listnumber = this.getNumber();
				for(int s=0;s<listnumber;s++){
					int pageNow = getPageNow();
					int pageSize = getPageSize();
					pageNow = pageNow*s+10;
					pageSize = pageSize+s*10;
					boolean consub = true;
					String title = news == null ? "" : (news.getTitle() == null ? "" : news
							.getTitle().trim());
					ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,	ElConstants.TREE_FIANL, true);
					zxNews = newsDao.getNewsByUidByPerOrShar("",nid,ntypeTree,3, pageNow, pageSize);
					count = newsDao.getNewsCountByUidByPerOrShar("",nid,ntypeTree,3);
					zxNotices = frontDao.listNewsByTid_list(0, 6, 2, true, "");
					ntls=frontDao.getNewsType();
					if (null != zxNotices)
						for (int i = 0; i < zxNotices.size(); i++) {
							String title1 = zxNotices.get(i).getTitle();
							title1 = (title1 == null) ? "" : (title1.length() > 15 ? title1
									.substring(0, 13)
									+ "" : title1);
							zxNotices.get(i).setTitle(title1);
						}
					ctypeTree = ctypeDao.getCtypeTree(1, 0, true);
					if(news==null){
						news=new News();
					}
					if(news.getNtype()==null){
						NewsType nt=new NewsType();
						nt.setId(1);
						news.setNtype(nt);
						
					}
					news.setNtype(newsDao.getNtypeByid(news.getNtype().getId()));
					
					int count1 = listnumber*10;
					Map root = new HashMap();
					root.put("newsList", zxNews);
					root.put("count1", count1);
					root.put("pageNow", pageNow);
					root.put("pageSize", pageSize);	
					int pageCount = 0;
					pageSize=10;
					if (count1 % pageSize == 0) {
						pageCount = count1 / pageSize;
					} else {
						pageCount = count1 / pageSize + 1;
					}
					List li = new LinkedList();
					if(pageCount>0){
						for(int i=0;i<pageCount;i++){
							li.add(i);
						}
					}
					root.put("li", li);
					root.put("pageCount", pageCount);
					root.put("pN", s);
					root.put("nid", nid);
					ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,	ElConstants.TREE_FIANL, true);
					root.put("ntypeTree", ntypeTree);
					root.put("nt", ntypeTree.getChild());
					String htmlName = "newsList_"+s+"_"+nid+".html";
					String ftl = "ftl/newsindex.ftl";
					FreePage frp = new FreePage();
					this.msg = frp.pageIn(root,htmlName,ftl);
					
				}
				boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
				if(b==true){
					return "newsIndex_phone"; 
				}

				return "newsIndex";
		}

	/**
	 * 锟斤拷训锟斤拷询
	 * 
	 * @return
	 * @throws ElException
	 */
	public String sjywnewsIndex() throws ElException {
		boolean consub = true;
		newstype = newsDao.getNtypeByid(2);
		List<NewsType> nts = newsDao.getNtypesByPid(2);
		if (newstype != null && nts != null) {
			for (int i = 0; i < nts.size(); i++) {
				zxNews = frontDao.listNewsByTid(0, 6, nts.get(i).getId(),
						consub, "");
				int size = i == 2 ? 18 : 15;
				for (int j = 0; j < zxNews.size(); j++) {
					String title = zxNews.get(j).getTitle();
					title = (title == null) ? ""
							: (title.length() > size ? title.substring(0, size)
									+ "" : title);
					zxNews.get(j).setTitle(title);
				}
				nts.get(i).setNewses(zxNews);
			}
			newstype.setChild(nts);
		}
		/*
		 * zxNews = frontDao.listNewsByTid(getPageNow(), getPageSize(),
		 * news.getNtype().getId(), consub); count =
		 * frontDao.listNewsByTidCount(news.getNtype().getId(), consub);
		 * news.setNtype(newsDao.getNtById(news.getNtype().getId()));
		 */

		phCourses = frontDao.listPhCourse(0, 6);
		for (int i = 0; i < phCourses.size(); i++) {
			String name = phCourses.get(i).getName();
			phCourses.get(i).setName(
					name.length() > 11 ? name.substring(0, 9) + "" : name);
		}
		ntypes = new ArrayList<NewsType>();// newsDao.getNtypesByPid(1);
		ntypes.add(new NewsType(23, "图锟斤拷锟斤拷讯"));
		ntypes.add(new NewsType(12, "锟斤拷锟竭凤拷锟斤拷"));
		ntypes.add(new NewsType(24, "锟斤拷锟斤拷锟斤拷锟斤拷"));
		ntypes.add(new NewsType(25, "锟斤拷锟斤拷芙锟�"));
		ntypes.add(new NewsType(26, "锟斤拷平锟斤拷展"));
		ntypes.add(new NewsType(13, "锟斤拷锟脚讹拷态"));

		for (int i = 0; i < ntypes.size(); i++) {
			List<News> ns = null;
			if (i != 0) {
				ns = frontDao.listNewsByTid(0, 10, ntypes.get(i).getId(), true,
						"");
				for (int j = 0; j < ns.size(); j++) {
					String name = ns.get(j).getTitle();
					ns.get(j).setTitle(
							name.length() > 13 ? name.substring(0, 12) + ""
									: name);
				}
			} else {
				ns = frontDao.listNewsByTid(0, 4, ntypes.get(i).getId(), true,
						"");
				for (int j = 0; j < ns.size(); j++) {
					String name = ns.get(j).getTitle();
					ns.get(j).setTitle(
							name.length() > 8 ? name.substring(0, 7) + ""
									: name);
				}
			}
			ntypes.get(i).setNewses(ns);
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "sjywnewsIndex_phone"; 
		}

		return "sjywnewsIndex";
	}

	public String sjjdnewsIndex() throws ElException {
		boolean consub = true;
		newstype = newsDao.getNtypeByid(3);
		List<NewsType> nts = newsDao.getNtypesByPid(3);
		if (newstype != null && nts != null) {
			for (int i = 0; i < nts.size(); i++) {
				zxNews = frontDao.listNewsByTid(0, 6, nts.get(i).getId(),
						consub, "");
				int size = 18;
				for (int j = 0; j < zxNews.size(); j++) {
					String title = zxNews.get(j).getTitle();
					title = (title == null) ? ""
							: (title.length() > size ? title.substring(0, size)
									+ "" : title);
					zxNews.get(j).setTitle(title);
				}
				nts.get(i).setNewses(zxNews);
			}
			newstype.setChild(nts);
		}
		/*
		 * zxNews = frontDao.listNewsByTid(getPageNow(), getPageSize(),
		 * news.getNtype().getId(), consub); count =
		 * frontDao.listNewsByTidCount(news.getNtype().getId(), consub);
		 * news.setNtype(newsDao.getNtById(news.getNtype().getId()));
		 */

		phCourses = frontDao.listPhCourse(0, 6);
		for (int i = 0; i < phCourses.size(); i++) {
			String name = phCourses.get(i).getName();
			phCourses.get(i).setName(
					name.length() > 11 ? name.substring(0, 9) + "" : name);
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "sjjdnewsIndex_phone"; 
		}

		return "sjjdnewsIndex";
	}

	public String pxzxnewsIndex() throws ElException {
		boolean consub = true;
		newstype = newsDao.getNtypeByid(6);
		List<NewsType> nts = newsDao.getNtypesByPid(6);
		if (newstype != null && nts != null) {
			for (int i = 0; i < nts.size(); i++) {
				zxNews = frontDao.listNewsByTid(0, 6, nts.get(i).getId(),
						consub, "");
				int size = 18;
				if (nts.get(i).getId() == 20 || nts.get(i).getId() == 22) {
					size = 15;
				}
				for (int j = 0; j < zxNews.size(); j++) {
					String title = zxNews.get(j).getTitle();
					title = (title == null) ? ""
							: (title.length() > size ? title.substring(0, size)
									+ "" : title);
					zxNews.get(j).setTitle(title);
				}
				nts.get(i).setNewses(zxNews);
			}
			newstype.setChild(nts);
		}
		/*
		 * zxNews = frontDao.listNewsByTid(getPageNow(), getPageSize(),
		 * news.getNtype().getId(), consub); count =
		 * frontDao.listNewsByTidCount(news.getNtype().getId(), consub);
		 * news.setNtype(newsDao.getNtById(news.getNtype().getId()));
		 */

		phCourses = frontDao.listPhCourse(0, 6);
		for (int i = 0; i < phCourses.size(); i++) {
			String name = phCourses.get(i).getName();
			phCourses.get(i).setName(
					name.length() > 11 ? name.substring(0, 9) + "" : name);
		}
		ntypes = new ArrayList<NewsType>();// newsDao.getNtypesByPid(1);
		ntypes.add(new NewsType(23, "图锟斤拷锟斤拷讯"));
		ntypes.add(new NewsType(12, "锟斤拷锟竭凤拷锟斤拷"));
		ntypes.add(new NewsType(24, "锟斤拷锟斤拷锟斤拷锟斤拷"));
		ntypes.add(new NewsType(25, "锟斤拷锟斤拷芙锟�"));
		ntypes.add(new NewsType(26, "锟斤拷平锟斤拷展"));
		ntypes.add(new NewsType(13, "锟斤拷锟脚讹拷态"));

		for (int i = 0; i < ntypes.size(); i++) {
			List<News> ns = null;
			if (i != 0) {
				ns = frontDao.listNewsByTid(0, 10, ntypes.get(i).getId(), true,
						"");
				for (int j = 0; j < ns.size(); j++) {
					String name = ns.get(j).getTitle();
					ns.get(j).setTitle(
							name.length() > 13 ? name.substring(0, 12) + ""
									: name);
				}
			} else {
				ns = frontDao.listNewsByTid(0, 4, ntypes.get(i).getId(), true,
						"");
				for (int j = 0; j < ns.size(); j++) {
					String name = ns.get(j).getTitle();
					ns.get(j).setTitle(
							name.length() > 8 ? name.substring(0, 7) + ""
									: name);
				}
			}
			ntypes.get(i).setNewses(ns);
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "pxzxnewsIndex_phone"; 
		}

		return "pxzxnewsIndex";
	}

	public String newsIndexView() throws ElException {
			if(news==null||news.getId()==0){
				setElmessage("找不到资源!");
				boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
				if(b==true){
					return "error_phone"; 
				}

				return "error";
			}
			fiveForums = forumAdminDao.listForumsList_wsj(76, 5, 0);
			newsDao.updateNewsBrowseforById(news.getId());
			news = newsDao.getNewsById(news.getId());
			news.setStuffs(newsDao.listKstuff(news.getId()));
			zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
			ntls=frontDao.getNewsType();
			if (null != zxNotices)
				for (int i = 0; i < zxNotices.size(); i++) {
					String title = zxNotices.get(i).getTitle();
					title = (title == null) ? "" : (title.length() > 16 ? title
							.substring(0, 15)
							+ "" : title);
					zxNotices.get(i).setTitle(title);
				}
			ctypeTree = ctypeDao.getCtypeTree(1, 0, true);
			kltypeTree = knowledgeDao.getKltypeTree(1, 0, true);
			ntypeTree = newsDao.getNtypeTree(1, 0, true);
			
			tjNews = newsDao.getTjNews(news.getNtype().getId(),1);  //新闻热度1 为推荐
			zztjNews = newsDao.getTjNews(1);
			
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			
			if(b==true){
				System.out.println("新闻详细页面");
				return "newsIndexView_phone";
			}
			return "newsIndexView";
		
	}
	
	public String newsIndexView2() throws ElException {
		int modelstatus = SystemConfOp.getIntValue(ElConstants.MODEL_WORKING);
		
		if(news==null||news.getId()==0){
			setElmessage("找不到资源!");
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "error_phone"; 
			}

			return "error";
		}
		newsDao.updateNewsBrowseforById(news.getId());
		news = newsDao.getNewsById(news.getId());
		news.setStuffs(newsDao.listKstuff(news.getId()));
		zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
		ntls=frontDao.getNewsType();
		if (null != zxNotices)
			for (int i = 0; i < zxNotices.size(); i++) {
				String title = zxNotices.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 16 ? title
						.substring(0, 15)
						+ "" : title);
				zxNotices.get(i).setTitle(title);
			}
		ctypeTree = ctypeDao.getCtypeTree(1, 0, true);
		kltypeTree = knowledgeDao.getKltypeTree(1, 0, true);
		ntypeTree = newsDao.getNtypeTree(1, 0, true);
		newsDao.updateNewsIsHtmlById(news.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "newsIndexView_phone"; 
		}

		return "newsIndexView";
	
	}

	public String courseIndex() throws ElException {
		// boolean consub = true;
		elUser = new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
		int depid = 1;
		// int courseId = course == null? 0 : course.getCtype().getId();
		int ctid = course.getCtype() == null ? ctypeDao.getCtypeRoot().getId()
				: course.getCtype().getId();
		// String name = course == null ? "" : course.getName();
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		if (getSession().getAttribute("roleid") == null) {
			getSession().setAttribute("roleid", 7); // 为null时设个默认值7给他
		}
		if (course != null && course.getName() != null
				&& course.getName().equals("填写课程名称....")) {
			course.setName("");
		}
		zxCourses = courseDao.listAllCourseFromThis(ctypeTree, depid,
				getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
				getPageNow(), getPageSize(), "5", " and c.isapplication = 1");
		count = courseDao.listAllCourseSizeFromThis(ctypeTree, depid,
				getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
				"5", " and c.isapplication = 1");
		kltypeTree = knowledgeDao.getKltypeTree(1, 0, true);
		ctls = courseDao.getCourseType();
		zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
		if (null != zxNotices)
			for (int i = 0; i < zxNotices.size(); i++) {
				String title = zxNotices.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 16 ? title
						.substring(0, 15)
						+ "" : title);
				zxNotices.get(i).setTitle(title);
			}
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		for (int i = 0; zxCourses.size() > i; i++) {
			// 列表页不需要申请报名。 注释掉
			zxCourses.get(i).setCoRegistration(
					courseDao.getCourseRegistration(zxCourses.get(i).getId()));
			zxCourses.get(i).getCoRegistration().setJoinNumber(
					courseDao.getJoinNumber(zxCourses.get(i).getId()) + "");
			if (courseDao.checkCourseIsUser(zxCourses.get(i).getId(),
					getSessionIntValue(ElConstants.SESSION_USERID))) {// 是否已报名
				zxCourses.get(i).setIsjoin("true");
			} else {
				zxCourses.get(i).setIsjoin("false");
			}
			if (checkIsuserApp(zxCourses.get(i), elUser)) {// 如果返回false证明有某条不符合条件
				zxCourses.get(i).setIsuserApp(1);
			} else {
				zxCourses.get(i).setIsuserApp(2);
			}
		}
		tjCourses = courseDao.getTjCourses(ctid,1); //1 为推荐
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseIndex_phone"; 
		}

		return "courseIndex";
	}

	public String courseIndex_isPass() throws ElException {
		// boolean consub = true;
		int depid = 1;
		// int courseId = course == null? 0 : course.getCtype().getId();
		int ctid = course.getCtype() == null ? ctypeDao.getCtypeRoot().getId()
				: course.getCtype().getId();
		// String name = course == null ? "" : course.getName();
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		if (getSession().getAttribute("roleid") == null) {
			getSession().setAttribute("roleid", 7); // 为null时设个默认值7给他
		}
		if (course != null && course.getName() != null
				&& course.getName().equals("填写课程名称....")) {
			course.setName("");
		}
		zxCourses = courseDao.listAllCourseFromThis(ctypeTree, depid,
				getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
				1000000, 1, "5", " and c.isapplication = 1");
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		String courseids = "";
		if (zxCourses.size() != 0) {
			for (int i = 0; i < zxCourses.size(); i++) {
				zxCourses.get(i).setCoRegistration(
						courseDao.getCourseRegistration(zxCourses.get(i)
								.getId()));
				if (checkIsuserApp(zxCourses.get(i), elUser)) {// 如果返回false证明有某条不符合条件
					if (courseids.equals(""))
						courseids = courseids + zxCourses.get(i).getId();
					else
						courseids = courseids + "," + zxCourses.get(i).getId();
				}
			}
			String coursesql = "";
			if (!courseids.equals("")) {
				coursesql = " and c.id in (" + courseids + ")";
			}
			zxCourses = courseDao.listAllCourseFromThis(ctypeTree, depid,
					getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
					getPageNow(), getPageSize(), "5",
					" and c.isapplication = 1 " + coursesql);
			count = courseDao.listAllCourseSizeFromThis(ctypeTree, depid,
					getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
					"5", " and c.isapplication = 1 " + coursesql);
			kltypeTree = knowledgeDao.getKltypeTree(1, 0, true);
			ctls = courseDao.getCourseType();
			zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
			if (null != zxNotices)
				for (int i = 0; i < zxNotices.size(); i++) {
					String title = zxNotices.get(i).getTitle();
					title = (title == null) ? "" : (title.length() > 16 ? title
							.substring(0, 15)
							+ "" : title);
					zxNotices.get(i).setTitle(title);
				}
			for (int i = 0; zxCourses.size() > i; i++) {
				zxCourses.get(i).setCoRegistration(
						courseDao.getCourseRegistration(zxCourses.get(i)
								.getId()));
				zxCourses.get(i).getCoRegistration().setJoinNumber(
						courseDao.getJoinNumber(zxCourses.get(i).getId()) + "");
				if (courseDao.checkCourseIsUser(zxCourses.get(i).getId(),
						getSessionIntValue(ElConstants.SESSION_USERID))) {// 是否已报名
					zxCourses.get(i).setIsjoin("true");
				} else {
					zxCourses.get(i).setIsjoin("false");
				}
				zxCourses.get(i).setIsuserApp(1);
			}
		} else {
			zxCourses = null;
			count = 0;
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseIndex_isPass_phone"; 
		}

		return "courseIndex_isPass";
	}

	/**
	 * 选课 hwc
	 * 
	 * @return
	 * @throws ElException
	 */
	public String submitAppalyCourses() throws ElException {
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		if (!courseDao.checkCourseIsUser(course.getId(), userid)) {
			courseDao.assignedUser(course.getId(), userid, 0, new Timestamp(
					System.currentTimeMillis()), new Timestamp(System
					.currentTimeMillis()), 0);
		}
		courseDao.setisNormal(course.getId(), 1);// 学员端显示。
		course = courseDao.getCourseById(course.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseIndexView_phone"; 
		}

		return "courseIndexView";
	}

	List<ExamRoom> examRooms;
	ExamRoom examRoom;
	ExamPaperLib examPaperLib;
	ExamPaperDao examPaperDao;

	public ExamPaperDao getExamPaperDao() {
		return examPaperDao;
	}

	public void setExamPaperDao(ExamPaperDao examPaperDao) {
		this.examPaperDao = examPaperDao;
	}

	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}

	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}

	public String quizpaperIndex() throws ElException {
		int libid = examPaperLib == null || examPaperLib.getId() <= 0 ? 1
				: examPaperLib.getId();
		// getPageSize()=getPageSize()==0?10:getPageSize();
		examRooms = frontDao.listExamRooms(libid, getPageNow(), getPageSize());
		count = frontDao.listExamRoomsSize(libid);
		phCourses = frontDao.listPhCourse(0, 6);
		for (int i = 0; i < phCourses.size(); i++) {
			String name = phCourses.get(i).getName();
			phCourses.get(i).setName(
					name.length() > 11 ? name.substring(0, 9) + "" : name);
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "quizpaperIndex_phone"; 
		}

		return "quizpaperIndex";
	}

	public String quizpaperIndexView() throws ElException {
		if (examRoom == null || examRoom.getId() <= 0) {
			setElmessage("没锟揭碉拷锟斤拷应锟斤拷锟斤拷");
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "error_phone"; 
			}

			return "error";
		}
		// examRoom = courseDao.getExamRoomByid(examRoom.getId());
		phCourses = frontDao.listPhCourse(0, 6);
		for (int i = 0; i < phCourses.size(); i++) {
			String name = phCourses.get(i).getName();
			phCourses.get(i).setName(
					name.length() > 11 ? name.substring(0, 9) + "" : name);
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "quizpaperIndexView_phone"; 
		}

		return "quizpaperIndexView";
	}

	private List<CourseType> ctypes;

	public String coursetypeIndex() throws ElException {
		boolean consub = true;
		// if(containsub==1) consub = true;
		zxCourses = frontDao.listCourseByType(0, 12, 1, consub);
		for (int i = 0; i < zxCourses.size(); i++) {
			String name = zxCourses.get(i).getName();
			zxCourses.get(i).setName(
					name.length() > 11 ? name.substring(0, 9) + "" : name);
		}
		ctypes = ctypeDao.getCtypeChilds(1);
		if (null != ctypes) {
			for (int i = 0; i < ctypes.size(); i++) {
				CourseType ct = ctypes.get(i);
				ct.setChild(new ArrayList<CourseType>());
				List<CourseType> ctypess = ctypeDao.getCtypeChilds(ct.getId());
				if (ctypess != null)
					for (int j = 0; j < ctypess.size(); j++) {
						if (j < 2) {
							List<Course> cs = frontDao.listCourseByType(0, 6,
									ctypess.get(j).getId(), consub);
							for (int k = 0; k < cs.size(); k++) {
								String name = cs.get(k).getName();
								cs.get(k).setName(
										name.length() > 11 ? name.substring(0,
												9)
												+ "" : name);
							}
							ctypess.get(j).setCourses(cs);
							ct.getChild().add(ctypess.get(j));
						} else {
							break;
						}
					}
				// ctypes.get(i).setCourses(cs);
			}
		}
		phCourses = frontDao.listPhCourse(0, 6);
		for (int i = 0; i < phCourses.size(); i++) {
			String name = phCourses.get(i).getName();
			phCourses.get(i).setName(
					name.length() > 11 ? name.substring(0, 9) + "" : name);
		}
		// rmCourses = frontDao.listCourseByHot(0, 7, ElConstants.HOT_RM);
		// for (int i = 0; i < rmCourses.size(); i++) {
		// String name = rmCourses.get(i).getName();
		// rmCourses.get(i).setName(
		// name.length() > 13 ? name.substring(0, 11) + "" : name);
		// }
		rmCourses = frontDao.listNewsByTid(0, 6, 19, true, "");
		for (int i = 0; i < rmCourses.size(); i++) {
			String name = rmCourses.get(i).getTitle();
			rmCourses.get(i).setTitle(
					name.length() > 12 ? name.substring(0, 11) + "" : name);
		}
		flinks = new FlinkDaoImpl().listFLink(0, 30);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "coursetypeIndex_phone"; 
		}

		return "coursetypeIndex";
	}

	/**
	 * 锟轿筹拷8目
	 * 
	 * @return
	 * @throws ElException
	 */
	public String coursetypelist() throws ElException {
		boolean consub = true;
		// if(containsub==1) consub = true;
		int ctid = ctype1 == null ? 1 : ctype1.getId();
		ctype1 = ctypeDao.getCtypeById(ctid);
		zxCourses = frontDao.listCourseByType(0, 12, ctid, consub);
		for (int i = 0; i < zxCourses.size(); i++) {
			String name = zxCourses.get(i).getName();
			zxCourses.get(i).setName(
					name.length() > 11 ? name.substring(0, 9) + "" : name);
		}
		ctypes = ctypeDao.getCtypeChilds(ctid);
		if (null != ctypes)
			for (int i = 0; i < ctypes.size(); i++) {
				List<Course> cs = frontDao.listCourseByType(0, 12, ctypes
						.get(i).getId(), consub);
				for (int j = 0; j < cs.size(); j++) {
					String name = cs.get(j).getName();
					cs.get(j).setName(
							name.length() > 11 ? name.substring(0, 9) + ""
									: name);
				}
				ctypes.get(i).setCourses(cs);
			}
		phCourses = frontDao.listPhCourse(0, 6);
		for (int i = 0; i < phCourses.size(); i++) {
			String name = phCourses.get(i).getName();
			phCourses.get(i).setName(
					name.length() > 11 ? name.substring(0, 9) + "" : name);
		}
		/*
		 * rmCourses = frontDao.listCourseByHot(0, 7, ElConstants.HOT_RM); for
		 * (int i = 0; i < rmCourses.size(); i++) { String name =
		 * rmCourses.get(i).getName(); rmCourses.get(i).setName( name.length() >
		 * 13 ? name.substring(0, 11) + "" : name); }
		 */
		rmCourses = frontDao.listNewsByTid(0, 6, 19, true, "");
		for (int i = 0; i < rmCourses.size(); i++) {
			String name = rmCourses.get(i).getTitle();
			rmCourses.get(i).setTitle(
					name.length() > 12 ? name.substring(0, 11) + "" : name);
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "coursetypelist_phone"; 
		}

		return "coursetypelist";
	}

	public String courseIndexView() throws ElException {
		course = courseDao.getCourseById(course.getId());
	//	ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
	//			ElConstants.TREE_FIANL, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)  
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		kltypeTree = knowledgeDao.getKltypeTree(1, 0, true);
		zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
		ctls = courseDao.getCourseType();
		if (null != zxNotices)
			for (int i = 0; i < zxNotices.size(); i++) {
				String title = zxNotices.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 16 ? title
						.substring(0, 15)
						+ "" : title);
				zxNotices.get(i).setTitle(title);
			}
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		if (course.getIsApplication() == 1) {
			coRegistration = courseDao.getCourseRegistration(course.getId());
			coRegistration.setJoinNumber(courseDao
					.getJoinNumber(course.getId())
					+ "");

			course.setCoRegistration(coRegistration);
			if (checkIsuserApp(course, elUser)) {// 如果返回false证明有某条不符合条件
				course.setIsuserApp(1);
			} else {
				course.setExplain(explain.toString());// 不通过说明
				course.setIsuserApp(2);
			}
			if (courseDao.checkCourseIsUser(course.getId(),
					getSessionIntValue(ElConstants.SESSION_USERID))) {
				course.setIsjoin("true");
			} else {
				course.setIsjoin("false");
			}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseIndexView_phone"; 
		}

		return "courseIndexView";
	}

	private ElClTypeDao elClTypeDao;
	private ElClType cltypeTree;

	public String class_listbytitle() throws ElException {
		String name = (elclass == null) ? "" : elclass.getName();
		elclasses = frontDao.listClassByName(getPageNow(), getPageSize(), name);
		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		count = frontDao.listClassByNameSize(name);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "class_listbytitle_phone"; 
		}

		return "class_listbytitle";
	}

	public String class_view() throws ElException {
		elclass = classDao.getElClassById(elclass.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "class_view_phone"; 
		}

		return "class_view";
	}

	public String course_listbytitle() throws ElException {
		String name = (course == null) ? "" : course.getName();
		/*
		 * zxCourses = frontDao .listCourseByName(getPageNow(), getPageSize(),
		 * course);
		 * 
		 * count = frontDao.listCourseByNameSize(name);
		 */
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		zxCourses = frontDao.listCourseByName(getPageNow(), getPageSize(),
				course, ctypeTree);
		count = frontDao.listCourseByNameSize(getPageNow(), getPageSize(),
				course, ctypeTree);
		phCourses = frontDao.listPhCourse(0, 6);
		ctls = courseDao.getCourseType();
		for (int i = 0; i < phCourses.size(); i++) {
			name = phCourses.get(i).getName();
			phCourses.get(i).setName(
					name.length() > 11 ? name.substring(0, 9) + "" : name);
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_listbytitle_phone"; 
		}

		return "course_listbytitle";
	}
	
	public String knowledge_center() throws ElException {
		// if (getPageSize() == 0)
		// getPageSize() = 10;
		// TODO 锟铰硷拷锟叫憋拷
		if (getRequest().getParameter("str") == null) {
			knowledges = knowledgeDao.listKlsByType(0, getPageNow(),
					getPageSize());
			count = knowledgeDao.listKlsByTypeSize(0);
		} else {
			knowledges = knowledgeDao.listKlsByType(knowledge, getPageNow(),
					getPageSize());
			count = knowledgeDao.listKlsByTypeCount(knowledge, getPageNow(),
					getPageSize());
		}
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		// zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
		zxNotices = frontDao.listNewsByTid_list(0, 6, 2, true, "");
		ktlist = knowledgeDao.listKnowledgeType();
		if (null != zxNotices)
			for (int i = 0; i < zxNotices.size(); i++) {
				String title = zxNotices.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 16 ? title
						.substring(0, 15)
						+ "" : title);
				zxNotices.get(i).setTitle(title);
			}
	//	tjknowledges = knowledgeDao.getTjKls();
	//	zdknowledges = 
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "knowledge_center_phone"; 
		}
		sysconf = new SystemConf();
		sysconf.setSearch_need(false);
		return "knowledge_center";
	}

	/**
	 * 前台资料中心
	 */
	public String knowledge_center_list() throws ElException {
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
//		if (userid == 0) {
//			setElmessage("请先登录！");
//			return "no_user";
//		}
//		elUser = userDao.getUserById(userid);
//		if (elUser == null) {
//			setElmessage("找不到登陆用户！");
//			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
//			if(b==true){
//				return "error_phone"; 
//			}
//
//			return "error";
//		}
		// 检测是否有查看资源的权限
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1
//				|| elUser.getDepartment().getId() == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
//		} else if (knowledgeDao.checkUserKnowledgeDep(userid)) {
//			kltypeTree = knowledgeDao.getKnowledgeLibTree_index(-1, true);
//		} else {
//			kltype = new KnowledgeType(-2);
//			setElmessage("没有可查看的资源目录！");
//		}
		if (kltype == null || kltype.getId() < 0) {
			kltype = kltypeTree;
		} else {
			kltype = knowledgeDao.getKltypeById(kltype.getId());
		}
		knowledges = knowledgeDao.listMyKlsNew(0, kltype, knowledge,
				getPageNow(), getPageSize());
		count = knowledgeDao.listMyklsSizeNew(0, kltype, knowledge);
		tjknowledges = knowledgeDao.getTjKls(kltype.getId(),1);//1 为推荐
		zdknowledges = knowledgeDao.getTjKls(1);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "knowledge_center_list_phone";
		}
		return "knowledge_center_list";
	}

	public String stuff_listbyTitle() throws ElException {
		// listNews=frontDao.SearchNews(news, getPageNow(), getPageSize());
		// count = frontDao.SearchNewsCountByTid(news);
		ntls = frontDao.getNewsType();
		newsTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		listNews = newsDao.SearchNews(news, newsTree, getPageNow(),
				getPageSize());
		count = newsDao.SearchNewsCount(news, newsTree, getPageNow(),
				getPageSize());
		// ntypeTree = newsDao.getNtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true,"-1",true,"newstype_op_type");
		// ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		ntypeTree = newsTree;
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "stuff_listbyTitle_phone"; 
		}

		return "stuff_listbyTitle";

	}

	// public String knowledge_center_list() throws ElException {
	//
	// if(getRequest().getParameter("str")==null||kltype!=null){
	// knowledges = knowledgeDao.listKlsByType(kltype.getId(), getPageNow(),
	// getPageSize());
	// count = knowledgeDao.listKlsByTypeSize(kltype.getId());
	// }else{
	// knowledges=knowledgeDao.listKlsByType(knowledge, getPageNow(),
	// getPageSize());
	// count = knowledgeDao.listKlsByTypeCount(knowledge, getPageNow(),
	// getPageSize());
	// }
	// //kltype = knowledgeDao.getKltypeById(kltype.getId());
	// ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
	// ElConstants.TREE_FIANL, true);
	// kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
	// //zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
	// zxNotices = frontDao.listNewsByTid_list(0, 6, 2, true, "");
	// tjknowledges = knowledgeDao.listKlByHot2(ElConstants.HOT_TJ, 0, 8);
	// rmknowledges = knowledgeDao.listKlByReadTime(0, 8);
	// ktlist=knowledgeDao.listKnowledgeType();
	// /*if (kltype.getId() == 2) {
	// if (null != knowledges)
	// for (int i = 0; i < knowledges.size(); i++) {
	// String title = knowledges.get(i).getTitle();
	// title = (title == null) ? "" : (title.length() > 12 ? title
	// .substring(0, 11)
	// + "" : title);
	// knowledges.get(i).setTitle(title);
	// }
	// }*/
	// if (null != zxNotices)
	// for (int i = 0; i < zxNotices.size(); i++) {
	// String title = zxNotices.get(i).getTitle();
	// title = (title == null) ? "" : (title.length() > 16 ? title
	// .substring(0, 15)
	// + "" : title);
	// zxNotices.get(i).setTitle(title);
	// }
	// return "knowledge_center_list";
	// }

	public String knowledge_center_listbytitle() throws ElException {
		LuceneSearch search = new LuceneSearch();
		String searcname = knowledge.getName();
		if(sysconf.isSearch_need()){//是否全文检索
		//	search.searchKeyword(knowledge.getTitle());
			try {
				knowledge.setTitle(searcname);
				Sort sort = new Sort(new SortField("name", SortField.STRING,true));
				List<FileBean> data = FileSeach.seachFile(knowledge.getTitle(), 0, 1,sort).getBeans();
				for(FileBean file : data){
					System.out.println("以下是检索结果："+file.getName()+"-----"+file.getPath());
					tjknowledges = knowledgeDao.listKlByStuffAddr(file.getName());
			//		for(int i=0;i<knowledges.size();i++){
			//			knowledges.add(knowledges.get(i));
			//		}
					rmknowledges = knowledgeDao.listKlsByTitle_list(knowledge.getTitle(),getPageNow(), getPageSize());
					rmknowledges.addAll(tjknowledges);
					Set<Knowledge> list = new HashSet<Knowledge>();
					for(int i=0;i<rmknowledges.size();i++){
						Knowledge kl = rmknowledges.get(i);
						list.add(kl);
					}
					knowledges = new ArrayList<Knowledge>();
					 for (Iterator<Knowledge> it = list.iterator(); it.hasNext();){  
						 knowledge = it.next();
						 knowledges.add(knowledge); 
					 } 
					System.out.println(knowledges.size());
				}
			//	sortClass sort = new sortClass();  
			//	Collections.sort(knowledges,sort);
				Collections.sort(knowledges,new Comparator<Knowledge>(){
					public int compare(Knowledge k1, Knowledge k2){
						Date d1 = k1.getCreatetime();
						Date d2 = k2.getCreatetime();
						return d2.compareTo(d1);
					}
				} );
				
				count = knowledges.size();
				if(count<getPageNow()&&count<=getPageSize()){
					knowledges = knowledges.subList(0, count);
				}else if(getPageSize()<count&&count<=getPageNow()){
				//	if(getPageSize()==1){
						knowledges = knowledges.subList(getPageSize()-1, count);
				//	}else{
				//		knowledges = knowledges.subList(getPageSize()-2, count-1);
				//	}
					
				}else{
					knowledges = knowledges.subList(getPageSize()-1, getPageNow());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			knowledge.setName(searcname);
			return "knowledge_center_listbytitle";
		}
		if (getRequest().getParameter("str") == null || kltype != null) {
			// knowledges = knowledgeDao.listKlsByTitle(knowledge.getTitle(),
			// getPageNow(), getPageSize());
			knowledges = knowledgeDao.listKlsByTitle_list(knowledge.getTitle(),
					getPageNow(), getPageSize());
			count = knowledgeDao.listKlsByTitleSize(knowledge.getTitle());
		} else {
			// knowledges=knowledgeDao.listKlsByType(knowledge, getPageNow(),
			// getPageSize());
			knowledges = knowledgeDao.listKlsByType_list(knowledge,
					getPageNow(), getPageSize());
			count = knowledgeDao.listKlsByTypeCount(knowledge, getPageNow(),
					getPageSize());
		}
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		tjknowledges = knowledgeDao.listKlByHot2(ElConstants.HOT_TJ, 0, 8);
		rmknowledges = knowledgeDao.listKlByReadTime(0, 8);
		ktlist = knowledgeDao.listKnowledgeType();
		if (null != tjknowledges)
			for (int i = 0; i < tjknowledges.size(); i++) {
				String name = tjknowledges.get(i).getTitle();
				tjknowledges.get(i).setTitle(
						name.length() > 13 ? name.substring(0, 9) + "" : name);
			}

		if (null != rmknowledges)
			for (int i = 0; i < rmknowledges.size(); i++) {
				String name = rmknowledges.get(i).getTitle();
				rmknowledges.get(i).setTitle(
						name.length() > 13 ? name.substring(0, 9) + "" : name);
			}
		// zdknowledges = knowledgeDao.listKlByHot(ElConstants.HOT_ZD,0,10);
		return "knowledge_center_listbytitle";
	}

	public String knowledge_center_view() throws ElException {
		if (knowledge == null || knowledge.getId() == 0) {
			setElmessage("找不到资源!");
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "error_phone"; 
			}

			return "error";
		}
		knowledgeDao.setKlReadtime(knowledge.getId());// 阅读+1
		knowledge = knowledgeDao.getKlById(knowledge.getId());
		if(knowledge != null){
			int fromchange = 0;
			//得到stuff的id、ext
			String url = knowledge.getSwf();
			String ext = "";
			int stuffid = 0;
			if(url != null && !url.equals("")){
				ext = url.substring(url.lastIndexOf(".")+1,url.length());
				stuffid = Integer.valueOf(url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".")));
			}
			if(ext.equals("swf")){//需要判断swf是否由office文档转换而来
				fromchange = stuffDao.getFromchange(stuffid);
				knowledge.setFromchange(fromchange);
			}
		}
		String s="";
		if(null!=knowledge.getContent_()) s=CheckHtml.getString(knowledge.getContent_()) ;
		content_ = s.length()>90?s.substring(0, 90)+".....":knowledge.getContent_();
		// ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		// kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		elUser = userDao.getUserById(userid);
		if (elUser == null || elUser.getId() == 0) {
			setElmessage("用户没有登录！");
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "error_phone"; 
			}

			return "error";
		}
		// 检测是否有查看资源的权限
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1
//				|| elUser.getDepartment().getId() == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
//		} else if (knowledgeDao.checkUserKnowledgeDep(userid)) {
//			kltypeTree = knowledgeDao.getKnowledgeLibTree_index(-1, true);
//			// 检查该资源是否有权查看
//			if (!knowledgeDao.checkKnowledgeDepK(knowledge.getKltype().getId())) {
//				knowledge = null;
//				setElmessage("该资源无权查看！");
//			}  
//		} else {
//			kltype = new KnowledgeType(-2);
//			knowledge = null;
//			setElmessage("没有可查看的资源目录！");
//		}
		// zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
		if (knowledge != null)
			knowledge.setStuffs(knowledgeDao.listKstuff(knowledge.getId()));
		// tjknowledges = knowledgeDao.listKlByHot2(ElConstants.HOT_TJ, 0, 8);
		// rmknowledges = knowledgeDao.listKlByReadTime(0, 8);
		// ktlist=knowledgeDao.listKnowledgeType();
		// if (null != zxNotices)
		// for (int i = 0; i < zxNotices.size(); i++) {
		// String title = zxNotices.get(i).getTitle();
		// title = (title == null) ? "" : (title.length() > 16 ? title
		// .substring(0, 15)
		// + "" : title);
		// zxNotices.get(i).setTitle(title);
		// }
		
		tjknowledges = knowledgeDao.getTjKls(knowledge.getKltype().getId(),1);//1 为推荐
		zdknowledges = knowledgeDao.getTjKls(1);
		
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "knowledge_center_view_phone"; 
		}

		return "knowledge_center_view";
	}

	public String forumIndex() throws ElException, UnsupportedEncodingException {
		// fbtypes = forumAdminDao.listFbtypesWithBlocks();
		fbtypes = forumAdminDao.listFbtypes();
		if (null != fbtypes) {
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(
						forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		zxforums = forumAdminDao.listForumsByZx2(0, 7);
		jhforums = forumAdminDao.listForumsByJh2(0, 7);
		rmforums = forumAdminDao.listForumsByRm(0, 7);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forumIndex_phone";
		}
		return "forumIndex";
	}

	public String forumListByBlockid() throws ElException {
		// if (getPageSize() == 0)
		// getPageSize() = 15;
		if (getRequest().getParameter("fbtid") != null) {
			String newid = getRequest().getParameter("fbtid").substring(2,
					getRequest().getParameter("fbtid").length());
			ForumBlock forumBlock = new ForumBlock();
			forumBlock.setId(Integer.parseInt(newid));
			this.fblock = forumBlock;
		}
		/*
		 * zxforums = forumAdminDao.listForumsByBid(fblock.getId(),
		 * getPageNow(), getPageSize()); count =
		 * forumAdminDao.listForumsByBidSize(fblock.getId());
		 */
		// zxforums =
		// forumAdminDao.listForumsByBid(fblock.getId(),forum==null?"":forum.getTitle()==null?"":forum.getTitle(),
		// getPageNow(),
		// getPageSize());
		zxforums = forumAdminDao.listForumsByBid_list(fblock.getId(),
				forum == null ? "" : forum.getTitle() == null ? "" : forum
						.getTitle(), getPageNow(), getPageSize());
		count = forumAdminDao.listForumsByBidCount(fblock.getId(),
				forum == null ? "" : forum.getTitle() == null ? "" : forum
						.getTitle());
		fblock = forumAdminDao.getFblockById(fblock.getId());

		// jhforums = forumAdminDao.listForumsByJhBid2(fblock.getId(), 0, 8);
		// rmforums = forumAdminDao.listForumsByRmBid(fblock.getId(), 0, 8);
		jhforums = forumAdminDao.listForumsByJhBid2_list(fblock.getId(), 0, 8);
		rmforums = forumAdminDao.listForumsByRmBid_list(fblock.getId(), 0, 8);

		if (jhforums != null) {
			for (int i = 0; i < jhforums.size(); i++) {
				String title = jhforums.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 15 ? title
						.substring(0, 14)
						+ ".." : title);
				jhforums.get(i).setTitle(title);
			}
		}
		if (rmforums != null) {
			for (int i = 0; i < rmforums.size(); i++) {
				String title = rmforums.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 15 ? title
						.substring(0, 14)
						+ ".." : title);
				rmforums.get(i).setTitle(title);
			}
		}
		fbtypes = forumAdminDao.listFbtypes();
		if (null != fbtypes) {
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(
						forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		//本栏目推荐帖子
		tjforums = forumAdminDao.getTjForums(fblock.getId(),1);//1为推荐
		//整站推荐帖子
		zztjforums = forumAdminDao.getTjForums(1);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forumListByBlockid_phone"; 
		}

		return "forumListByBlockid";
	}

	public String forumView() throws ElException {
		forum = forumAdminDao.getForumsByid(forum.getId());
		department = departmentDao.getDepById(forum.getCreater().getDepartment().getId());
		// fbtypes = forumAdminDao.listFbtypesWithBlocks();
		fbtypes = forumAdminDao.listFbtypes();
		if (null != fbtypes) {
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(
						forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		// if (getPageSize() == 0)
		// getPageSize() = 10;
		topics = forumAdminDao.listTopicByFid(forum.getId(), getPageNow(),
				getPageSize(), 1);
		count = forumAdminDao.listTopicByIdSize(forum.getId(), 1);
		forumAdminDao.readtimeAdd(forum.getId());// readtime=readtime+1
		boolean isAudit = SystemConfOp
				.getBooleanValue(ElConstants.STUFF_ISFTOPIC);
		getRequest().setAttribute("isAudit", isAudit);
		// 版块信息
//		fblock = forumAdminDao.getFblockById(forum.getFblock().getId());
		//本栏目推荐帖子
		tjforums = forumAdminDao.getTjForums(forum.getFblock().getId(),1);//1为推荐
		//整站推荐帖子
		zztjforums = forumAdminDao.getTjForums(1);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forumView_phone"; 
		}

		return "forumView";
	}

	/**
	 * 更新回帖的状态
	 * 
	 * @return
	 * @throws ElException
	 */
	public String upTopicValid() throws ElException {
		// 根据操作获取要更新的状态
		int valid = 0;
		String resultPage = "displayMyTopicList";
		if (topicOp == 1) {
			// 申请删除的操作,状态改为2：删除等待中
			valid = 2;
			resultPage = "displayMyTopicList";
		} else if (topicOp == 2) {
			// 回帖审核通过，状态改为1:已发布
			valid = 1;
			// forumAdminDao.getFblockById(id)
			System.out.println(topic.getForum().getCreater().getId());//帖子发布者
			forumAdminDao.receipttimeAdd(topic.getForum().getId());// 回复的贴数加1：注意删除的时候要减1
			new MessageDaoImpl().insertMessInApply(topic.getForum().getTitle(),ElLoggerConstants.LOG_MOD_FORUM, ElLoggerConstants.LOG_TYPE_OETHER,topic.getForum().getCreater().getId(), 5,topic.getForum().getId());
			resultPage = "TopicList";
		} else if (topicOp == 3) {
			// 回复数量要减1
			// 判断状态，如果为已发布，那边回复数减1
			topic = forumAdminDao.getTopicById(topic.getId());
			if (topic.getDisvalid() == 1) {
				forumAdminDao.receipttimeDel1(topic.getForum().getId());
			}
			// 删除回帖
			forumAdminDao.deleteTopic(topic.getId());
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "TopicList_phone"; 
			}

			return "TopicList";
		}
		forumAdminDao.upTopicValid(topic.getId(), valid);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "resultPage_phone"; 
		}

		return resultPage;
	}

	/**
	 * 我的所有回帖
	 * 
	 * @return
	 * @throws ElException
	 */
	public String displayMyTopicList() throws ElException {
		topics = forumAdminDao.myListTopic(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = forumAdminDao
				.myListTopicCount(getSessionIntValue(ElConstants.SESSION_USERID));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "displayMyTopicList_phone"; 
		}

		return "displayMyTopicList";
	}

	public String forum_topicAdd() throws ElException {
		System.out.println(topic.getForum().getCreater().getId());
		topic.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		// 判断设置的是否需要审核
		boolean isAudit = SystemConfOp
				.getBooleanValue(ElConstants.STUFF_ISFTOPIC);
		if (isAudit) {
			forumAdminDao.addTopic(topic);
		} else {
			forumAdminDao.addTopic2(topic);
			forumAdminDao.receipttimeAdd(topic.getForum().getId());
			new MessageDaoImpl().insertMessInApply(topic.getForum().getTitle(),ElLoggerConstants.LOG_MOD_FORUM, ElLoggerConstants.LOG_TYPE_OETHER,topic.getForum().getCreater().getId(), 5,topic.getForum().getId());
		}
		// forumAdminDao.receipttimeAdd(topic.getForum().getId());//回复的贴数加1：注意删除的时候要减1
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forumView_phone"; 
		}

		return "forumView";
	}

	public String forum_topicDelete() throws ElException {
		topic.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		// 判断状态，如果为已发布，那边回复数减1
		topic = forumAdminDao.getTopicById(topic.getId());
		if (topic.getDisvalid() == 1) {
			forumAdminDao.receipttimeDel1(topic.getForum().getId());
		}
		forumAdminDao.deleteTopic(topic.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forumView_phone"; 
		}

		return "forumView";
	}

	public String forumAddInit() throws ElException {
		if (fblock == null) {
			fblock = new ForumBlock(1, "");
		}
		// fbtypes = forumAdminDao.listFbtypesWithBlocks();
		fbtypes = forumAdminDao.listFbtypes();

		if (null != fbtypes) {
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(
						forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		fblock = forumAdminDao.getFblockById(fblock.getId());
		jhforums = forumAdminDao.listForumsByJhBid(fblock.getId(), 0, 10);
		rmforums = forumAdminDao.listForumsByRmBid(fblock.getId(), 0, 10);

		if (jhforums != null) {
			for (int i = 0; i < jhforums.size(); i++) {
				String title = jhforums.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 15 ? title
						.substring(0, 14)
						+ ".." : title);
				jhforums.get(i).setTitle(title);
			}
		}
		if (rmforums != null) {
			for (int i = 0; i < rmforums.size(); i++) {
				String title = rmforums.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 15 ? title
						.substring(0, 14)
						+ ".." : title);
				rmforums.get(i).setTitle(title);
			}
		}
		if (SystemConfOp.getBooleanValue(ElConstants.FORUM_NEED_SH))
			getRequest().setAttribute("forumvalid", true);
		else
			getRequest().setAttribute("forumvalid", false);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forumAdd_phone"; 
		}
		return "forumAdd";
	}

	/**
	 * 发布帖子
	 * 
	 * @return
	 * @throws ElException
	 */
	public String forumAdd() throws ElException {
		if (null != forum) {
			if (forum.getFblock() == null || forum.getFblock().getId() == 0) {
				setElmessage("没有选择帖子类别！");
				boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
				if(b==true){
					return "error_phone"; 
				}

				return "error";
			}
			if (!SystemConfOp.getBooleanValue(ElConstants.FORUM_NEED_SH)) {
				forum.setValid(true);
			}
			forum.setCreater(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			forumAdminDao.addForum(forum);
			if (SystemConfOp.getBooleanValue(ElConstants.FORUM_NEED_SH)) {
				setElmessage("失败！");
				boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
				if(b==true){
					return "forumAdd_success_phone"; 
				}

				return "forumAdd_success";
			}
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_FORUM,
					ElLoggerConstants.LOG_TYPE_ADD, forum.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, forum.getId());
		}
		// 刷新首页帖子模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_FORUM);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forumAdd_success_phone"; 
		}
		return "forumAdd_success";
	}

	private String module;

	public String admin() throws ElException {
		if ("studentman".equals(module)) {
			return "study";// SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_ISEXAM)&&
		}
		module = module == null || module.trim().equals("") ? "commonman"
				: module;
		Return = module;
		menu = roleDao.getMenu(module,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		menus = roleDao.getMenus(0,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		// String funccode = menu.getParams().indexOf(".jsp") >= 0 ? menu
		// .getParams() : (menu.getParams().indexOf(".action") >= 0 ? menu
		// .getParams().substring(0, menu.getParams().indexOf(".action"))
		// : menu.getParams());
		if (menu.getChild().size() > 0
				&& menu.getChild().get(0).getChild().size() > 0)
			module =
			// AuthorityUtil.checkAuthor(
			// getSessionIntValue(ElConstants.SESSION_ROLE), funccode,
			// getSessionIntValue(ElConstants.SESSION_USERID)) ? menu
			// .getParams() :
			menu.getChild().get(0).getChild().get(0).getFunccode() + ".action";
		if (menu.getChild().get(0).getChild().get(0).getParams() != null
				&& !menu.getChild().get(0).getChild().get(0).getParams()
						.equals("")) {
			module += "?"
					+ menu.getChild().get(0).getChild().get(0).getParams();
		}
		// else{
		// menu = roleDao.getMenu("studentman",
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		// module = "study_index.action";
		// }
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "admin_phone"; 
		}

		return "admin";
	}

	/**
	 * 新版管理中心页面上方点击触发
	 * 
	 * @return
	 * @throws ElException
	 */
	public String admin_newversion() throws ElException {
		module = module == null || module.trim().equals("") ? "commonman"
				: module;
		Return = module;
		menu = roleDao.getMenu(module,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		menus = roleDao.getMenus_newversion(0,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		if (menu.getChild().size() > 0
				&& menu.getChild().get(0).getChild().size() > 0)
			module = menu.getChild().get(0).getChild().get(0).getFunccode()
					+ ".action";
		if (menu.getChild().get(0).getChild().get(0).getParams() != null
				&& !menu.getChild().get(0).getChild().get(0).getParams()
						.equals("")) {
			module += "?"
					+ menu.getChild().get(0).getChild().get(0).getParams();
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "admin_phone"; 
		}

		return "admin";
	}

	public String study() throws ElException {
		menu = roleDao.getMenu("studentman",
				getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		menus = roleDao.getMenus(0,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		module = module == null ? "study_index.action" : module;
		Return = "studentman";
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			if (!SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_ISEXAM))
				return "study_phone";
			return "exam_phone";
		}

		if (!SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_ISEXAM))
			return "study";
		
		return "exam";
	}

	/**
	 * 培训班是否满足申请要求
	 * 
	 * @author
	 * @return
	 * @throws ElException
	 */
	public boolean checkIsuserApp(Course cou, ELUser eluser) throws ElException {
		boolean IsuserApp = true;
		boolean jz = true;
		boolean ds = true;
		boolean zj = true;
		boolean zw = true;
		boolean gw = true;
		boolean nl = true;
		boolean xb = true;
		boolean bm = true;
		boolean erooms = true;
		boolean elClass = true;
		explain = new StringBuffer();
		if (cou.getCoRegistration().getDslist() == null) {// 地市不限
			ds = true;
		} else {
			if (cou.getCoRegistration().getDslist() != null
					&& elUser.getDishi() > 0
					&& cou.getCoRegistration().getDslist().contains(
							elUser.getDishi())) {
				ds = true;// dslist不为空 uds不为空 dslist 里没有该地市
			} else {
				explain.append("地市 ");
				ds = false;// dslist不为空 uds为空 或者 dslist 里没有该地市
			}
		}
		if (cou.getCoRegistration().getJzlist() == null) {
			jz = true;// 不限
		} else {
			if (cou.getCoRegistration().getJzlist() != null
					&& elUser.getJingzhong() > 0
					&& cou.getCoRegistration().getJzlist().contains(
							elUser.getJingzhong())) {
				jz = true;
			} else {
				explain.append("工种 ");
				jz = false;
			}
		}
		if (cou.getCoRegistration().getZjlist() == null) {
			zj = true;// 不限
		} else {
			if (cou.getCoRegistration().getZjlist() != null
					&& elUser.getZhiji() > 0
					&& cou.getCoRegistration().getZjlist().contains(
							elUser.getZhiji())) {
				zj = true;
			} else {
				explain.append("职级 ");
				zj = false;
			}
		}
		if (cou.getCoRegistration().getZwlist() == null) {
			zw = true;// 不限
		} else {
			if (cou.getCoRegistration().getZwlist() != null
					&& elUser.getZhiwu() > 0
					&& cou.getCoRegistration().getZwlist().contains(
							elUser.getZhiwu())) {
				zw = true;
			} else {
				explain.append("职务 ");
				zw = false;
			}
		}
		if (cou.getCoRegistration().getGwlist() == null) {
			gw = true;
		} else {
			if (cou.getCoRegistration().getGwlist() != null
					&& elUser.getGangwei() != null
					&& cou.getCoRegistration().getGwlist().contains(
							elUser.getGangwei())) {
				gw = true;
			} else {
				explain.append("岗位 ");
				gw = false;
			}
		}
		// 年龄段
		if (cou.getCoRegistration().getStartAge() == 0
				&& cou.getCoRegistration().getStopAge() == 0) {
			nl = true;
		} else {
			if (eluser.getAGE() > cou.getCoRegistration().getStartAge()
					&& cou.getCoRegistration().getStopAge() > eluser.getAGE()) {
				nl = true;
			} else {
				explain.append("年龄 ");
				nl = false;
			}
		}
		// 性别
		if (cou.getCoRegistration().getSex() == null
				|| cou.getCoRegistration().getSex().equals("不限")) {
			xb = true;
		} else if (cou.getCoRegistration().getSex().equals(eluser.getSex())) {
			xb = true;
		} else {
			explain.append("性别 ");
			xb = false;
		}

		// 部门
		if (cou.getCoRegistration().getTreeType() == null) {// 部门不限
			bm = true;
		} else {
			if (cou.getCoRegistration().getTreeTypes() != null
					&& elUser.getDepartment() != null
					&& cou.getCoRegistration().getTreeTypelist().contains(
							elUser.getDepartment().getId() + "")) {
				bm = true;
			} else {
				explain.append("部门 ");
				bm = false;
			}
		}

		// 考场
		if (cou.getCoRegistration().getExamRoom() == null
				|| cou.getCoRegistration().getExamRoom().size() == 0) {// 考场不限
			erooms = true;
		} else {
			String sqlWhere = "";
			if (cou.getCoRegistration().getEroomScreeningWay() == 1) {
				sqlWhere = " and ispassed  = 1";
			} else if (cou.getCoRegistration().getEroomScreeningWay() == 2) {
				sqlWhere = " and ispassed  = 0";
			}
			if (!cou.getCoRegistration().getExamRooms().equals("")
					&& eroomDao.checkEroomIspassed(cou.getCoRegistration()
							.getExamRooms(),
							getSessionIntValue(ElConstants.SESSION_USERID),
							sqlWhere)) {
				erooms = true;
			} else {
				explain.append("考场");
				erooms = false;
			}
		}
		// 培训班
		if (cou.getCoRegistration().getElclass() == null
				|| cou.getCoRegistration().getElclass().size() == 0) {// 培训班不限
			elClass = true;
		} else {
			String sqlWhere = "";
			if (cou.getCoRegistration().getClassScreeningWay() == 1) {
				sqlWhere = "and certificateno is not null";
			} else if (cou.getCoRegistration().getClassScreeningWay() == 2) {
				sqlWhere = "and certificateno is null";
			}
			if (!cou.getCoRegistration().getElclasss().equals("")
					&& eroomDao.checkElclassIspassed(cou.getCoRegistration()
							.getElclasss(),
							getSessionIntValue(ElConstants.SESSION_USERID),
							sqlWhere)) {
				elClass = true;
			} else {
				explain.append("培训班");
				elClass = false;
			}
		}

		if (jz && ds && zj && zw && gw && nl && xb && bm && erooms && elClass) { //  
			IsuserApp = true;
		} else {
			IsuserApp = false;
		}
		return IsuserApp;
	}

	public String courseman() throws ElException {
		menu = roleDao.getMenu("courseman",
				getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		menus = roleDao.getMenus(0,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseman_phone"; 
		}

		return "courseman";
	}

	public List<News> getZxNews() {
		return zxNews;
	}

	public void setZxNews(List<News> zxNews) {
		this.zxNews = zxNews;
	}

	public List<News> getZxNotices() {
		return zxNotices;
	}

	public void setZxNotices(List<News> zxNotices) {
		this.zxNotices = zxNotices;
	}

	public List<Course> getPhCourses() {
		return phCourses;
	}

	public void setPhCourses(List<Course> phCourses) {
		this.phCourses = phCourses;
	}

	public List<Knowledge> getTjKnows() {
		return tjKnows;
	}

	public void setTjKnows(List<Knowledge> tjKnows) {
		this.tjKnows = tjKnows;
	}

	public int getContainsub() {
		return containsub;
	}

	public void setContainsub(int containsub) {
		this.containsub = containsub;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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

	public CourseType getCtype1() {
		return ctype1;
	}

	public void setCtype1(CourseType ctype1) {
		this.ctype1 = ctype1;
	}

	public CourseType getCtype2() {
		return ctype2;
	}

	public void setCtype2(CourseType ctype2) {
		this.ctype2 = ctype2;
	}

	public CourseType getCtype3() {
		return ctype3;
	}

	public void setCtype3(CourseType ctype3) {
		this.ctype3 = ctype3;
	}

	public CourseType getCtype4() {
		return ctype4;
	}

	public void setCtype4(CourseType ctype4) {
		this.ctype4 = ctype4;
	}

	public CourseType getCtype5() {
		return ctype5;
	}

	public void setCtype5(CourseType ctype5) {
		this.ctype5 = ctype5;
	}

	public CourseType getCtype6() {
		return ctype6;
	}

	public void setCtype6(CourseType ctype6) {
		this.ctype6 = ctype6;
	}

	public CourseType getCtype7() {
		return ctype7;
	}

	public void setCtype7(CourseType ctype7) {
		this.ctype7 = ctype7;
	}

	public CourseType getCtype8() {
		return ctype8;
	}

	public void setCtype8(CourseType ctype8) {
		this.ctype8 = ctype8;
	}

	public List<Forum> getJhforums() {
		return jhforums;
	}

	public void setJhforums(List<Forum> jhforums) {
		this.jhforums = jhforums;
	}

	public List<Forum> getRmforums() {
		return rmforums;
	}

	public void setRmforums(List<Forum> rmforums) {
		this.rmforums = rmforums;
	}

	public List<Forum> getZxforums() {
		return zxforums;
	}

	public void setZxforums(List<Forum> zxforums) {
		this.zxforums = zxforums;
	}

	public ForumBlock getFblock() {
		return fblock;
	}

	public void setFblock(ForumBlock fblock) {
		this.fblock = fblock;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public ElClass getElclass() {
		return elclass;
	}

	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
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

	public List<ElFunc> getMenus() {
		return menus;
	}

	public void setMenus(List<ElFunc> menus) {
		this.menus = menus;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public List<News> getRmCourses() {
		return rmCourses;
	}

	public void setRmCourses(List<News> rmCourses) {
		this.rmCourses = rmCourses;
	}

	public NewsType getNewstype() {
		return newstype;
	}

	public void setNewstype(NewsType newstype) {
		this.newstype = newstype;
	}

	public List<CourseType> getCtypes() {
		return ctypes;
	}

	public void setCtypes(List<CourseType> ctypes) {
		this.ctypes = ctypes;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public ExamPaperLib getExamPaperLib() {
		return examPaperLib;
	}

	public void setExamPaperLib(ExamPaperLib examPaperLib) {
		this.examPaperLib = examPaperLib;
	}

	public List<News> getZxNewss() {
		return zxNewss;
	}

	public void setZxNewss(List<News> zxNewss) {
		this.zxNewss = zxNewss;
	}

	public List<Flink> getFlinks() {
		return flinks;
	}

	public void setFlinks(List<Flink> flinks) {
		this.flinks = flinks;
	}

	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}

	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}

	public ElClType getCltypeTree() {
		return cltypeTree;
	}

	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<News> getZxNotices1() {
		return zxNotices1;
	}

	public void setZxNotices1(List<News> zxNotices1) {
		this.zxNotices1 = zxNotices1;
	}

	public List<Knowledge> getTpKnows() {
		return tpKnows;
	}

	public void setTpKnows(List<Knowledge> tpKnows) {
		this.tpKnows = tpKnows;
	}

	public List<Knowledge> getSpKnows() {
		return spKnows;
	}

	public void setSpKnows(List<Knowledge> spKnows) {
		this.spKnows = spKnows;
	}

	public List<Knowledge> getWzKnows() {
		return wzKnows;
	}

	public void setWzKnows(List<Knowledge> wzKnows) {
		this.wzKnows = wzKnows;
	}

	public List<Knowledge> getWdKnows() {
		return wdKnows;
	}

	public void setWdKnows(List<Knowledge> wdKnows) {
		this.wdKnows = wdKnows;
	}

	public Knowledge getSpKnow() {
		return spKnow;
	}

	public void setSpKnow(Knowledge spKnow) {
		this.spKnow = spKnow;
	}

	public Knowledge getWzKnow() {
		return wzKnow;
	}

	public void setWzKnow(Knowledge wzKnow) {
		this.wzKnow = wzKnow;
	}

	public Knowledge getWdKnow() {
		return wdKnow;
	}

	public void setWdKnow(Knowledge wdKnow) {
		this.wdKnow = wdKnow;
	}

	public Course getCourse1() {
		return course1;
	}

	public void setCourse1(Course course1) {
		this.course1 = course1;
	}

	public Course getCourse2() {
		return course2;
	}

	public void setCourse2(Course course2) {
		this.course2 = course2;
	}

	public Course getCourse3() {
		return course3;
	}

	public void setCourse3(Course course3) {
		this.course3 = course3;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public CourseRegistration getCoRegistration() {
		return coRegistration;
	}

	public void setCoRegistration(CourseRegistration coRegistration) {
		this.coRegistration = coRegistration;
	}

	public String getHttpsPath() {
		return httpsPath;
	}

	public void setHttpsPath(String httpsPath) {
		this.httpsPath = httpsPath;
	}

	public String getHttpPath() {
		return httpPath;
	}

	public void setHttpPath(String httpPath) {
		this.httpPath = httpPath;
	}

	public List<Course> getHotCourses() {
		return hotCourses;
	}

	public void setHotCourses(List<Course> hotCourses) {
		this.hotCourses = hotCourses;
	}

	public List<Knowledge> getTjKnows_tw_zd() {
		return tjKnows_tw_zd;
	}

	public void setTjKnows_tw_zd(List<Knowledge> tjKnows_tw_zd) {
		this.tjKnows_tw_zd = tjKnows_tw_zd;
	}

	public List<Knowledge> getTjKnows_wb_tj() {
		return tjKnows_wb_tj;
	}

	public void setTjKnows_wb_tj(List<Knowledge> tjKnows_wb_tj) {
		this.tjKnows_wb_tj = tjKnows_wb_tj;
	}

	public List<News> getZxlxxy_tw_zd() {
		return zxlxxy_tw_zd;
	}

	public void setZxlxxy_tw_zd(List<News> zxlxxy_tw_zd) {
		this.zxlxxy_tw_zd = zxlxxy_tw_zd;
	}

	public List<News> getZxlxxy_wb_tj() {
		return zxlxxy_wb_tj;
	}

	public void setZxlxxy_wb_tj(List<News> zxlxxy_wb_tj) {
		this.zxlxxy_wb_tj = zxlxxy_wb_tj;
	}

	public List<News> getZxzxs_tw_zd() {
		return zxzxs_tw_zd;
	}

	public void setZxzxs_tw_zd(List<News> zxzxs_tw_zd) {
		this.zxzxs_tw_zd = zxzxs_tw_zd;
	}

	public List<News> getZxzxs_wb_tj() {
		return zxzxs_wb_tj;
	}

	public void setZxzxs_wb_tj(List<News> zxzxs_wb_tj) {
		this.zxzxs_wb_tj = zxzxs_wb_tj;
	}

	public List<News> getZxNewss_tw_wb() {
		return zxNewss_tw_wb;
	}

	public void setZxNewss_tw_wb(List<News> zxNewss_tw_wb) {
		this.zxNewss_tw_wb = zxNewss_tw_wb;
	}

	public List<News> getZxxzzx() {
		return zxxzzx;
	}

	public void setZxxzzx(List<News> zxxzzx) {
		this.zxxzzx = zxxzzx;
	}

	public List<News> getZxxzzx_tw_zd() {
		return zxxzzx_tw_zd;
	}

	public void setZxxzzx_tw_zd(List<News> zxxzzx_tw_zd) {
		this.zxxzzx_tw_zd = zxxzzx_tw_zd;
	}

	public String course_libraryList() throws ElException {
		ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		// ctypeTree =
		// ctypeDao.getCourseLibTreeNoShowNode(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL,
		// true,130); //不现实130节点
		for (int i = 0; i < ctypeTree.getChild().size(); i++) {
			if (ctypeTree.getChild().get(i).getId() == 130) {
				ctypeTree.getChild().remove(i);
				break;
			}
		}
		if (course != null && course.getCtype() != null) {
			ctid = course.getCtype().getId();
		} else {
			ctid = ctypeTree.getId();
		}
		Courses = courseDao.getCourseAll(ctypeTree, ctid, course, "",
				getPageNow(), getPageSize());
		count = courseDao.getCourseAllSize(ctypeTree, ctid, course, "");
		for (int i = 0; i < Courses.size(); i++) {
			String name = Courses.get(i).getDescription();
			if (name != null) {
				Courses.get(i).setDescription(
						name.length() > 85 ? name.substring(0, 85) + "" : name
								+ "...");
			}
		}
		// 查基础数据类别
		baseCourseTypeList = courseDao.getAllBaseDataTypeCourse();

		shihegangweis = courseDao.getBaseDatatCourseByTypeid(1);
		zhuanyeleibies = courseDao.getBaseDatatCourseByTypeid(2);
		zhuanyejibies = courseDao.getBaseDatatCourseByTypeid(3);
		shihebumens = courseDao.getBaseDatatCourseByTypeid(4);
		neirongleixings = courseDao.getBaseDatatCourseByTypeid(5);
		peixunleibies = courseDao.getBaseDatatCourseByTypeid(6);
		shihexueweis = courseDao.getBaseDatatCourseByTypeid(7);
		kechengxingzhis = courseDao.getBaseDatatCourseByTypeid(8);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_libraryList_phone"; 
		}

		return "course_libraryList";
	}

	public String course_libraryView() throws ElException {
		course = courseDao.getCourseById(course.getId());
		ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		// 查基础数据类别
		baseCourseTypeList = courseDao.getAllBaseDataTypeCourse();

		shihegangweis = courseDao.getBaseDatatCourseByTypeid(1);
		zhuanyeleibies = courseDao.getBaseDatatCourseByTypeid(2);
		zhuanyejibies = courseDao.getBaseDatatCourseByTypeid(3);
		shihebumens = courseDao.getBaseDatatCourseByTypeid(4);
		neirongleixings = courseDao.getBaseDatatCourseByTypeid(5);
		peixunleibies = courseDao.getBaseDatatCourseByTypeid(6);
		shihexueweis = courseDao.getBaseDatatCourseByTypeid(7);
		kechengxingzhis = courseDao.getBaseDatatCourseByTypeid(8);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_libraryView_phone"; 
		}

		return "course_libraryView";
	}

	public String course_ImmediatelyElectiveElclass() throws ElException {
		elclasses = classDao.getClassByUserid(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId(),
				getPageNow(), getPageSize());
		classKs_pass = classDao.CheckClassIsKs_passCourse(course.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID));
		count = classDao.getClassByUseridSize(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_ImmediatelyElectiveElclass_phone"; 
		}

		return "course_ImmediatelyElectiveElclass";
	}

	public List<ElClass> getClassKs_pass() {
		return classKs_pass;
	}

	public void setClassKs_pass(List<ElClass> classKs_pass) {
		this.classKs_pass = classKs_pass;
	}

	public String course_elclass_add() throws ElException {
		course = courseDao.getCourseById(course.getId());

		// classDao.addClassCourse2(elclass.getId(), course.getId(),
		// course.getCourseCss(),elclass.getStarttime(),elclass.getFinishtime());
		// 自主培训班添加课程
		elclass = classDao.getClassById(elclass.getId());
		classDao.addClassCourse_AT(elclass.getId(), course.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID), course
						.getCourseCss(), elclass.getStarttime(), elclass
						.getFinishtime(), course.getCredit());

		int id = eroomDao.getCourseByRoomId(course.getId());
		if (id != 0) { // 有考场index
			eroomDao.updateExamroom(course.getId(), elclass.getId(), id);
			// 更新关联表状态 表名为CLASS_COURSE_AT ，此表为自主培训班表。
			eroomDao.setClassBindingCourse(elclass.getId(), course.getId(), id,
					CourseConstants.COURSE_TABLENAME_CLASS_COURSE_AT);
			examPapers = examPaperDao.getExamPaperByRoomId(id);
			for (int i = 0; i < examPapers.size(); i++) {// 把用户加入到该考场中的多张试卷中。
				if (!studyQuizDao.hasInQuizPaper(
						getSessionIntValue(ElConstants.SESSION_USERID), id// 检测是否已经进入考场
						, examPapers.get(i).getId(), elclass.getId())) {
					studyQuizDao.intoQuizPaper(
							getSessionIntValue(ElConstants.SESSION_USERID), id// 添加study_quizinfo信息（考试信息）
							, examPapers.get(i).getId(), elclass.getId());
					// 把考场id摄入class_course表
				}
			}
			if (!eroomDao.checkuser2eroom(id,
					getSessionIntValue(ElConstants.SESSION_USERID)// 检查用户有没有分配到该考场
					, elclass.getId())) {
				eroomDao.adduser2eroom(id,
						getSessionIntValue(ElConstants.SESSION_USERID), 1,
						elclass.getId(), CourseConstants.EXAMROOM_FPFS_SQ);
			}

			// 更新sqiid
			eroomDao.updateStudySqiidInit(id, course.getId(), elclass.getId());
			elclass = classDao.getClassById(elclass.getId());
			course = courseDao.getCourseById(course.getId());
			examRoom = eroomDao.getExamRoomByid(id);

			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ALTER,
					getSessionValue(ElConstants.SESSION_USERNAME)
							+ getSessionValue(ElConstants.SESSION_ROLENAME)
							+ " 为培训班增加课程为(" + course.getName() + ")该考场为("
							+ examRoom.getTitle() + ")",
					ElLoggerConstants.LOG_RES_SUCC);
		} else {
			elclass = classDao.getClassById(elclass.getId());
			course = courseDao.getCourseById(course.getId());
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ALTER,
					getSessionValue(ElConstants.SESSION_USERNAME)
							+ getSessionValue(ElConstants.SESSION_ROLENAME)
							+ " 为培训班增加课程为(" + course.getName() + ")课程内无考场",
					ElLoggerConstants.LOG_RES_SUCC);
		}
		// return "course_libraryView";
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "myelclass_view_phone"; 
		}

		return "myelclass_view";
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public String map() throws ElException {

		// 获取新闻树

		ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		int nid = ntype == null ? newsDao.getNtypeRoot().getId() : (ntype
				.getId() == 0 ? 1 : ntype.getId());
		if (news == null) {
			news = new News();
		}
		if (news.getNtype() == null) {
			NewsType nt = new NewsType();
			nt.setId(1);
			news.setNtype(nt);

		}
		news.setNtype(newsDao.getNtypeByid(news.getNtype().getId()));
		// 知识库导航
		// 检测是否有查看资源的权限
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1
				|| elUser.getDepartment().getId() == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else if (knowledgeDao.checkUserKnowledgeDep(userid)) {
			kltypeTree = knowledgeDao.getKnowledgeLibTree_index(-1, true);
		} else {
			kltype = new KnowledgeType(-2);
			setElmessage("没有可查看的资源目录！");
		}

		// 论坛导航
		fbtypes = forumAdminDao.listFbtypes();
		if (null != fbtypes) {
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(
						forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		zxforums = forumAdminDao.listForumsByZx2(0, 7);
		jhforums = forumAdminDao.listForumsByJh2(0, 7);
		rmforums = forumAdminDao.listForumsByRm(0, 7);
		
		if (SystemConfOp
				.getIntValue(ElConstants.MODEL_WORKING)==3) {
			getRequest().setAttribute("model", "3");//绑定自定义模板
			// resultPage="account_add";
		} else if(SystemConfOp
				.getIntValue(ElConstants.MODEL_WORKING)==2) {
			getRequest().setAttribute("model", "2");// 
		}else if(SystemConfOp
				.getIntValue(ElConstants.MODEL_WORKING)==1){
			getRequest().setAttribute("model", "1");
		}else if(SystemConfOp
				.getIntValue(ElConstants.MODEL_WORKING)==0){
			getRequest().setAttribute("model","0");
		}
		return "map";
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
	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}
	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}
	public PollDao getPollDao() {
		return pollDao;
	}
	public void setPollDao(PollDao pollDao) {
		this.pollDao = pollDao;
	}
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	
	
	//投票
	public String indexPoll() throws ElException{
	poll=frontDao.getPoolMaxId();
	Integer userid=getSessionIntValue(ElConstants.SESSION_USERID);
	if(answer!=null){
		
		if(userid != null){
		if(!pollDao.checkUserIsPoll(poll.getId(), userid)){
			for (int i = 0; i < answer.size(); i++) {
				pollDao.addPollQuizinfo(poll.getId(), userid, answer.get(i));
			}
			frontDao.addUserIsPoll(poll.getId(), userid);
			setElmessage("您的投票已提交，谢谢您的参与！");
			return "success1";
		}else{
			setElmessage("您已经参加过投票，不能重复投票！");
			return "error1";
		}
	}
//	if(poll.getStuViewResult()==1){
//		return "pollResult";
//	}
//	return "studyPollList";
	}else{
		if(!pollDao.checkUserIsPoll(poll.getId(), 0)){
			for (int i = 0; i < answer.size(); i++) {
				pollDao.addPollQuizinfo(poll.getId(), 0, answer.get(i));
			}
			frontDao.addUserIsPoll(poll.getId(), userid);
			setElmessage("您的投票已提交，谢谢您的参与！");
			return "success1";
		}else{
			setElmessage("您已经参加过投票，不能重复投票！");
			return "";
		}
	}
	if(poll.getStuViewResult()==1){
		return "pollResult";
	}
	return "studyPollList";
	}
	
	/**
	 * 投票结果统计
	 * @return
	 * @throws ElException
	 */
	public String pollResult() throws ElException{
		poll=pollDao.getPoolById(poll.getId());
		questionRanking=pollDao.pollResult(poll);
		return "pollResult";
	}
	
	/**
	 * 学员投票列表
	 * @return
	 * @throws ElException
	 */
	public String studyPollList() throws ElException{
//		polls=pollDao.studyPollList(getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		return "studyPollList";
	}
	
	//-----------------外联个人回帖---------------------------
	public String myTopicList() throws ElException{
//		topics = forumAdminDao.myListTopic_(getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
//				getPageSize(), 1);
////		.myListTopic(
////				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
////				getPageSize());
//		count = forumAdminDao.myListTopicCount_(getSessionIntValue(ElConstants.SESSION_USERID), 1);
////				.myListTopicCount(getSessionIntValue(ElConstants.SESSION_USERID));
		topics = forumAdminDao.myListTopic(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = forumAdminDao
				.myListTopicCount(getSessionIntValue(ElConstants.SESSION_USERID));
		return "myTopicList";
	}
	
	public String forumView_() throws ElException {
		forum = forumAdminDao.getForumsByid(forum.getId());
		// fbtypes = forumAdminDao.listFbtypesWithBlocks();
		fbtypes = forumAdminDao.listFbtypes();
		if (null != fbtypes) {
			for (int i = 0; i < fbtypes.size(); i++) {
				fbtypes.get(i).setFblocks(
						forumAdminDao.listFbsByFbtid(fbtypes.get(i).getId()));
			}
		}
		// if (getPageSize() == 0)
		// getPageSize() = 10;
		topics = forumAdminDao.listTopicByFid(forum.getId(), getPageNow(),
				getPageSize(), 1);
		count = forumAdminDao.listTopicByIdSize(forum.getId(), 1);
		forumAdminDao.readtimeAdd(forum.getId());// readtime=readtime+1
		boolean isAudit = SystemConfOp
				.getBooleanValue(ElConstants.STUFF_ISFTOPIC);
		getRequest().setAttribute("isAudit", isAudit);
		// 版块信息
//		fblock = forumAdminDao.getFblockById(forum.getFblock().getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forumView_phone"; 
		}

		return "forumView";
	}
	
//---------------------wsj1026修改--------------------------------------------------
	public String courseIndexView_wsj() throws ElException {
		course = courseDao.getCourseById(course.getId());
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		kltypeTree = knowledgeDao.getKltypeTree(1, 0, true);
		zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
		ctls = courseDao.getCourseType();
		if (null != zxNotices)
			for (int i = 0; i < zxNotices.size(); i++) {
				String title = zxNotices.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 16 ? title
						.substring(0, 15)
						+ "" : title);
				zxNotices.get(i).setTitle(title);
			}
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		if (course.getIsApplication() == 1) {
			coRegistration = courseDao.getCourseRegistration(course.getId());
			coRegistration.setJoinNumber(courseDao
					.getJoinNumber(course.getId())
					+ "");

			course.setCoRegistration(coRegistration);
			if (checkIsuserApp(course, elUser)) {// 如果返回false证明有某条不符合条件
				course.setIsuserApp(1);
			} else {
				course.setExplain(explain.toString());// 不通过说明
				course.setIsuserApp(2);
			}
			if (courseDao.checkCourseIsUser(course.getId(),
					getSessionIntValue(ElConstants.SESSION_USERID))) {
				course.setIsjoin("true");
			} else {
				course.setIsjoin("false");
			}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseIndexView_phone"; 
		}

		return "courseIndexView_wsj";
	}
	
	public String courseIndex_wsj() throws ElException {
		// boolean consub = true;
		int depid = 1;
		// int courseId = course == null? 0 : course.getCtype().getId();
		int ctid = course.getCtype() == null ? ctypeDao.getCtypeRoot().getId()
				: course.getCtype().getId();
		// String name = course == null ? "" : course.getName();
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		if (getSession().getAttribute("roleid") == null) {
			getSession().setAttribute("roleid", 7); // 为null时设个默认值7给他
		}
		if (course != null && course.getName() != null
				&& course.getName().equals("填写课程名称....")) {
			course.setName("");
		}
		zxCourses = courseDao.listAllCourseFromThis(ctypeTree, depid,
				getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
				getPageNow(), getPageSize(), "5", " and c.isapplication = 1");
		count = courseDao.listAllCourseSizeFromThis(ctypeTree, depid,
				getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
				"5", " and c.isapplication = 1");
		kltypeTree = knowledgeDao.getKltypeTree(1, 0, true);
		ctls = courseDao.getCourseType();
		zxNotices = frontDao.listNewsByTid(0, 6, 2, true, "");
		if (null != zxNotices)
			for (int i = 0; i < zxNotices.size(); i++) {
				String title = zxNotices.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 16 ? title
						.substring(0, 15)
						+ "" : title);
				zxNotices.get(i).setTitle(title);
			}
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		for (int i = 0; zxCourses.size() > i; i++) {
			// 列表页不需要申请报名。 注释掉
			zxCourses.get(i).setCoRegistration(
					courseDao.getCourseRegistration(zxCourses.get(i).getId()));
			zxCourses.get(i).getCoRegistration().setJoinNumber(
					courseDao.getJoinNumber(zxCourses.get(i).getId()) + "");
			if (courseDao.checkCourseIsUser(zxCourses.get(i).getId(),
					getSessionIntValue(ElConstants.SESSION_USERID))) {// 是否已报名
				zxCourses.get(i).setIsjoin("true");
			} else {
				zxCourses.get(i).setIsjoin("false");
			}
			if (checkIsuserApp(zxCourses.get(i), elUser)) {// 如果返回false证明有某条不符合条件
				zxCourses.get(i).setIsuserApp(1);
			} else {
				zxCourses.get(i).setIsuserApp(2);
			}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseIndex_phone"; 
		}

		return "courseIndex_wsj";
	}

//------------------------结束---------------------------------
//--------------wsj浏览进入入口---------------------------------
	public String index_wsj() throws ElException {
		 boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			System.out.println("浏览入口判断==="+b);
			 if(b==true){
				 System.out.println("===============手机端访问====================");
				 return "phone_index";
			 }
		return "index";
	}
	//--------------wsj浏览进入入口结束---------------------------------	
	public List<Integer> getAnswer() {
		return answer;
	}
	public void setAnswer(List<Integer> answer) {
		this.answer = answer;
	}
	public QuestionRanking getQuestionRanking() {
		return questionRanking;
	}
	public void setQuestionRanking(QuestionRanking questionRanking) {
		this.questionRanking = questionRanking;
	}
	public boolean isNeedAllocation() {
		return needAllocation;
	}
	public void setNeedAllocation(boolean needAllocation) {
		this.needAllocation = needAllocation;
	}
	public MyClass getNew_cla() {
		return new_cla;
	}
	public void setNew_cla(MyClass new_cla) {
		this.new_cla = new_cla;
	}
	public int getIsChangeElclass() {
		return isChangeElclass;
	}
	public void setIsChangeElclass(int isChangeElclass) {
		this.isChangeElclass = isChangeElclass;
	}
	public MyClass getNianjian_cla() {
		return nianjian_cla;
	}
	public void setNianjian_cla(MyClass nianjian_cla) {
		this.nianjian_cla = nianjian_cla;
	}
	public List<MyCourse> getStudyCourseList() {
		return studyCourseList;
	}
	public void setStudyCourseList(List<MyCourse> studyCourseList) {
		this.studyCourseList = studyCourseList;
	}
	public int getIsBuyNianjianClass() {
		return isBuyNianjianClass;
	}
	public void setIsBuyNianjianClass(int isBuyNianjianClass) {
		this.isBuyNianjianClass = isBuyNianjianClass;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public String getContent_() {
		return content_;
	}
	public void setContent_(String content_) {
		this.content_ = content_;
	}
	public List<Forum> getFiveForums() {
		return fiveForums;
	}
	public void setFiveForums(List<Forum> fiveForums) {
		this.fiveForums = fiveForums;
	}
	public List<News> getTjNews() {
		return tjNews;
	}
	public void setTjNews(List<News> tjNews) {
		this.tjNews = tjNews;
	}
	public List<News> getZztjNews() {
		return zztjNews;
	}
	public void setZztjNews(List<News> zztjNews) {
		this.zztjNews = zztjNews;
	}
	public List<Forum> getTjforums() {
		return tjforums;
	}
	public void setTjforums(List<Forum> tjforums) {
		this.tjforums = tjforums;
	}
	public List<Forum> getZztjforums() {
		return zztjforums;
	}
	public void setZztjforums(List<Forum> zztjforums) {
		this.zztjforums = zztjforums;
	}

	
}