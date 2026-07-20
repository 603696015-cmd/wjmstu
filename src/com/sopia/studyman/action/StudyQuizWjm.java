package com.sopia.studyman.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.ExamPaperUtil;
import com.sopia.common.IndexSystemConfigOp;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.UserExcelUtil;
import com.sopia.common.quiz.EpQStatus;
import com.sopia.common.quiz.EroomEpCache;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.Examprac;
import com.sopia.courseman.entities.SimexamPaper;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.MyLogin;
import com.sopia.forumman.entities.Forum;
import com.sopia.frontman.dao.FrontDao;
import com.sopia.knowledgeman.dao.KnowledgeDao;
import com.sopia.knowledgeman.entities.DownloadInfo;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.newsandmess.dao.MessageDao;
import com.sopia.newsandmess.entities.Message;
import com.sopia.newsandmess.entities.News;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionArt;
import com.sopia.schedule.dao.ScheduleDao;
import com.sopia.schedule.dao.ScheduleGlobleDao;
import com.sopia.statman.dao.StatisticDao;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.StudyConstants;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.dao.impl.UtilMacAddress;
import com.sopia.studyman.entities.Integra;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyEprac;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyPractice;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.studyman.entities.PointsRecord;
import com.sopia.wjm.dao.ClassificationDao;
import com.sopia.wjm.entities.Classification;

public class StudyQuizWjm extends BaseAction {
	private static final Log logger = LogFactory.getLog(StudyQuizWjm.class);

	private List<MyExamPaper> myExamPapers;
	private List<MyExamPaper> myExamPapers_xbs;
	private StudyQuizDao studyQuizDao;
	private ClassDao classDao;
	private ExamPaper examPaper;
	private ExamPaperDao examPaperDao;
	private StatisticDao statisticDao;
	private QuestionDao questionDao;
	private MyExamPaper myExamPaper;
	private List<SimexamPaper> simExamPapers;
	private List<Message> newMessage;
	private MyPractice myPractice;
	// private List<ExamRoom> examRooms;
	// private ExamRoom examRoom;
	private FrontDao frontDao;
	private List<Question> questions;
	private Question question;
	private List<QuestionArt> questionarts;
	private QuestionArt questionart;
	private MyRoom myroom;
	private List<MyCourse> myCourses;
	private StudyCourseDao studyCourseDao;
	private List<ExamPaper> exampapers;
	private List<MyEprac> myexampracs;
	private MyEprac myeprac;
	private Examprac examprac;
	private EroomDao eroomDao;
	private Course course;
	private List<Course> courses;
	private CourseDao courseDao;
	private List<MyCPage> myCPages;
	private List<MyRoom> myrooms;
	private List<MyRoom> myrooms_xbs;// 选拨式考场
	private ExamRoom examRoom;
	private List<ExamRoom> examRooms;
	private File st;
	private String stFileName;
	private String sfContentType;
	private InputStream inputStream;
	private String filename;
	
	private MyCPage myCPage;
	private int isOnload; // 个人中心加载
	private List<MyClass> myClasses;
	private List<ElClass> elclasses;
	private List<ElClass> elclassesnot;
	private StudyClassDao studyClassDao;
	private EroomLib eroomLibTree;
	private ELUser elUser;
	private EroomLib eroomLib;
	private List<ExamPaper> examPapers;
	private CoursePage coursePage;
	private String Return;
	private StringBuffer explain;
	private int isCorrespond;// 搜索是否符合申请的考场 0 全部可申请的考场 1 符合申请的考场
	private List<News> zxtzggs;// 最新通知公告
	private List<News> tjtzggs;// 推荐通知公告
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
	private int sumIspass = 1;
	private String ipAddr;
	private int recordId;
	
	private ScheduleGlobleDao scheduleGlobleDao;
	private Department department;
	private Department depTree;
	private int sub_department;
	/**
	 * 课程练习 Description:  2011-12-8 上午09:26:14 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	Examprac courseprac;

	private String macAddr;
	private String lastqids;
	private int userid;
	// private MyRoomRecord myRoomRecord;
	//
	// public MyRoomRecord getMyRoomRecord() {
	// return myRoomRecord;
	// }
	//
	// public void setMyRoomRecord(MyRoomRecord myRoomRecord) {
	// this.myRoomRecord = myRoomRecord;
	// }
	
	
	private ElClass elclass;
	private Integra integra;
	private int status;
	private KnowledgeDao knowledgeDao;
	private PointsRecord precord;
	private  List<MyLogin> myLogins;
	private  List<Forum> forums;
	private  List<DownloadInfo> downloadInfos;
	private List<Knowledge> knowledges;
	private List<ELUser> elUsers;
	private Knowledge knowledge;
	
	private ClassificationDao classificationDao;
	private int roomid;
	private Classification classification;
	
	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public ClassificationDao getClassificationDao() {
		return classificationDao;
	}

	public void setClassificationDao(ClassificationDao classificationDao) {
		this.classificationDao = classificationDao;
	}

	public Knowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public List<Knowledge> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<Knowledge> knowledges) {
		this.knowledges = knowledges;
	}

	public List<DownloadInfo> getDownloadInfos() {
		return downloadInfos;
	}

	public void setDownloadInfos(List<DownloadInfo> downloadInfos) {
		this.downloadInfos = downloadInfos;
	}

	public List<Forum> getForums() {
		return forums;
	}

	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

	public List<MyLogin> getMyLogins() {
		return myLogins;
	}

	public void setMyLogins(List<MyLogin> myLogins) {
		this.myLogins = myLogins;
	}

	public PointsRecord getPrecord() {
		return precord;
	}

	public void setPrecord(PointsRecord precord) {
		this.precord = precord;
	}

	public KnowledgeDao getKnowledgeDao() {
		return knowledgeDao;
	}

	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integra getIntegra() {
		return integra;
	}

	public void setIntegra(Integra integra) {
		this.integra = integra;
	}

	public ElClass getElclass() {
		return elclass;
	}

	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}

	public String getLastqids() {
		return lastqids;
	}

	public void setLastqids(String lastqids) {
		this.lastqids = lastqids;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
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

	public int getSumIspass() {
		return sumIspass;
	}

	public void setSumIspass(int sumIspass) {
		this.sumIspass = sumIspass;
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

	public StringBuffer getExplain() {
		return explain;
	}

	public void setExplain(StringBuffer explain) {
		this.explain = explain;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}

	public EroomLib getEroomLib() {
		return eroomLib;
	}

	public void setEroomLib(EroomLib eroomLib) {
		this.eroomLib = eroomLib;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public EroomLib getEroomLibTree() {
		return eroomLibTree;
	}

	public void setEroomLibTree(EroomLib eroomLibTree) {
		this.eroomLibTree = eroomLibTree;
	}

	public CoursePage getCoursePage() {
		return coursePage;
	}

	public void setCoursePage(CoursePage coursePage) {
		this.coursePage = coursePage;
	}

	public StudyClassDao getStudyClassDao() {
		return studyClassDao;
	}

	public void setStudyClassDao(StudyClassDao studyClassDao) {
		this.studyClassDao = studyClassDao;
	}

	public List<MyClass> getMyClasses() {
		return myClasses;
	}

	public void setMyClasses(List<MyClass> myClasses) {
		this.myClasses = myClasses;
	}

	public int getIsOnload() {
		return isOnload;
	}

	public void setIsOnload(int isOnload) {
		this.isOnload = isOnload;
	}

	/**
	 * 我的必修课
	 * 
	 * @return
	 * @throws ElException
	 */
	public String myquiz_list() throws ElException {
		
		
		// myExamPapers = studyQuizDao.listMyQuiz(
		// getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		// getPageSize());
		// count = studyQuizDao
		// .listMyQuizSize(getSessionIntValue(ElConstants.SESSION_USERID));
		// myExamPapers = studyQuizDao.listMyQuiz2(
		// getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		// getPageSize());
		// myExamPapers = studyQuizDao.listMyQuiz3(
		// getSessionIntValue(ElConstants.SESSION_USERID), 0,
		// getPageNow(), getPageSize());
//		myExamPapers = studyQuizDao.listMyQuiz4(
//				getSessionIntValue(ElConstants.SESSION_USERID), 0,
//				getPageNow(), getPageSize());
		myrooms = studyQuizDao.listMyQuiz(
				getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(), getPageSize());
//		myExamPapers_xbs = studyQuizDao.listMyQuiz3(
//				getSessionIntValue(ElConstants.SESSION_USERID), 1,
//				getPageNow(), getPageSize());
		// count = studyQuizDao.listMyQuizSize(
		// getSessionIntValue(ElConstants.SESSION_USERID), 0, "");
		count = studyQuizDao.listMyQuizSize2(
				getSessionIntValue(ElConstants.SESSION_USERID), 0, "");
		
		return "myquiz_list";
	}

	public String myquiz_list_xbs() throws ElException {
		// myExamPapers = studyQuizDao.listMyQuiz(
		// getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		// getPageSize());
		// count = studyQuizDao
		// .listMyQuizSize(getSessionIntValue(ElConstants.SESSION_USERID));
		// myExamPapers = studyQuizDao.listMyQuiz2(
		// getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		// getPageSize());
		myExamPapers = studyQuizDao.listMyQuiz3(
				getSessionIntValue(ElConstants.SESSION_USERID), 1,
				getPageNow(), getPageSize());
		count = studyQuizDao.listMyQuizSize(
				getSessionIntValue(ElConstants.SESSION_USERID), 1, "");
		return "myquiz_list_xbs";
	}

	public String myprac_list() throws ElException {

		myCourses = studyCourseDao.listMyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyCourseDao
				.listMyCourseSize(getSessionIntValue(ElConstants.SESSION_USERID));

		return "myprac_list";
	}

	public String mysim_list() throws ElException {

		myCourses = studyCourseDao.listMyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyCourseDao
				.listMyCourseSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "mysim_list";
	}

	public String practice_listInit() throws ElException {
		// coursePage = coursePageDao.getCp(coursePage.getId());
		course = courseDao.getCourseById(course.getId());
		// if (null != coursePage) {
		// if (!studyQuizDao.checkMyCPage(new MyCPage(
		// getSessionIntValue(ElConstants.SESSION_USERID), coursePage
		// .getId()))) {
		// studyQuizDao.intoMyCPage(new MyCPage(
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// coursePage.getId()));
		// }
		// myCPage = studyQuizDao.getMyCPage(
		// getSessionIntValue(ElConstants.SESSION_USERID), coursePage
		// .getId());
		// myCPage.setCpage(coursePage);
		// myCPage.setCpages(coursePages);
		// myCPages = studyQuizDao.listCpsbyCUid(course.getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		if (null != myCPages)
			for (int i = 0; i < myCPages.size(); i++) {
				myCPages.get(i).setMyPracs(
						studyQuizDao.listMyPracpapers(
								getSessionIntValue(ElConstants.SESSION_USERID),
								course.getId(), myCPages.get(i).getCpage()
										.getId()));
			}
		course.setMyPracs(studyQuizDao.listMyPracpapers(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId(),
				0));
		// }
		// if (myCPages == null || myCPages.size() == 0)
		// setElmessage("课程无内容");
		// course = courseDao.getCourseById(course.getId());
		// exampapers = courseDao.getPracticePaperByCid(course.getId());
		// if(null!=exampapers&&exampapers.size()>0)
		// examPaper =
		// examPaperDao.getEPAllInfoById(exampapers.get(examPaper.getId()).getId());
		return "practice_list";
	}

	/**
	 * 课程练习进入
	 * 
	 * @return
	 * @throws ElException
	 */
	public String practice_paper() throws ElException {
		int classid = course.getClassid();
		// int coursePageId=
		course = courseDao.getCourseById(course.getId());
		// if (!studyQuizDao.checkPpaperIsFinish(myPractice.getPpaper().getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID))) {
		// studyQuizDao.intoPpaper(myPractice.getPpaper().getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		// ScoreOperate.setScore(
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// ElConstants.SCORE_PRAC_DO);
		// }
		//
		// // exampapers = courseDao.getPracticePaperByCid(course.getId());
		// // if(null!=exampapers&&exampapers.size()>0)
		//
		// examPaper = examPaperDao.getEPAllInfoById(examPaper.getId());
		// return "practice_paper";
		long starttime = System.currentTimeMillis();
		course = courseDao.getCourseById(course.getId());
		if (!studyQuizDao.checkPpaperIsFinish(myPractice.getPpaper().getId(),
				getSessionIntValue(ElConstants.SESSION_USERID),classid)) {
			// studyQuizDao.intoPpaper(myPractice.getPpaper().getId(),
			// getSessionIntValue(ElConstants.SESSION_USERID));
//			ScoreOperate.setScore(
//					getSessionIntValue(ElConstants.SESSION_USERID),
//					ElConstants.SCORE_PRAC_DO);
			myExamPaper = new MyExamPaper();
			myExamPaper.setExamPaper(examPaper);
			MyEprac m = new MyEprac();
			m.setTester(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			courseprac = new Examprac(myPractice.getPpaper().getId(), "");
			m.setPrac(courseprac);
			m.setStarttime(starttime);
			m.setClassid(classid);
			studyQuizDao.intomycourseprac(m);
			examPaper = examPaperDao.getEPAllInfoById(myExamPaper
					.getExamPaper().getId());
			myExamPaper.setId(m.getId());
			if (null != examPaper && null != examPaper.getEpBlocks())
				for (int j = 0; j < examPaper.getEpBlocks().size(); j++) {
					ExamPaperBlock ebp = examPaper.getEpBlocks().get(j);
					if (null != ebp.getQuestions())
						for (int k = 0; k < ebp.getQuestions().size(); k++) {
							if (!studyQuizDao.checkcpracQuestion(m.getId(), ebp
									.getQuestions().get(k)))
								studyQuizDao.insertcpracQuestion(m.getId(), ebp
										.getQuestions().get(k));
						}
				}
		} else {
			myExamPaper = studyQuizDao.getmycprac(myPractice.getPpaper()
					.getId(), getSessionIntValue(ElConstants.SESSION_USERID),classid);
			myExamPaper.setExamPaper(examPaper);
			studyQuizDao.deletecpracBlQuestion(myExamPaper.getId());
			examPaper = examPaperDao.getEPAllInfoById(myExamPaper
					.getExamPaper().getId());
			if (null != examPaper && null != examPaper.getEpBlocks())
				for (int j = 0; j < examPaper.getEpBlocks().size(); j++) {
					ExamPaperBlock ebp = examPaper.getEpBlocks().get(j);
					if (null != ebp.getQuestions())
						for (int k = 0; k < ebp.getQuestions().size(); k++) {
							if (!studyQuizDao.checkcpracQuestion(myExamPaper
									.getId(), ebp.getQuestions().get(k)))
								studyQuizDao.insertcpracQuestion(myExamPaper
										.getId(), ebp.getQuestions().get(k));
						}
				}
		}
		myExamPaper.setClassId(classid);
//		if (coursePage != null) {
//			coursePage = ((CoursePageDaoImpl) SpringContextUtil
//					.getBean("coursePageDao")).getCp(coursePage.getId());
//		}
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		//更改试卷状态 为不可编辑
		studyQuizDao.addCpracPaper_record(myExamPaper);
		if(myExamPaper!=null&&myExamPaper.getExamPaper()!=null){
			examPaperDao.updateExampaperIseditor(myExamPaper.getExamPaper().getId());
		}
		return "courseprac1b1";

	}

	/**
	 * 课程练习 答案.. （已废弃）
	 * 
	 * @return
	 * @throws ElException
	 */
	public String practice_result() throws ElException {
		ELUser user = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		examPaper.setEpBlocks(new ArrayList<ExamPaperBlock>());
		ExamPaperUtil.getAnswerExampaper(ExamPaperUtil
				.getParamCombString(getRequest()), examPaper, examPaperDao,
				questionDao, user.getShengri());
		// 总分
		// 得分
		return "practice_result";
	}

	/**
	 * 课程练习答卷的提交
	 * 
	 * @return
	 * @throws ElException
	 */
	public String courseprac_submit() throws ElException {
		if (null != questions) {
			for (int i = 0; i < questions.size(); i++) {
				if (!studyQuizDao.checkcpracQuestion(myExamPaper.getId(),
						questions.get(i)))
					studyQuizDao.insertcpracQuestion(myExamPaper.getId(),
							questions.get(i));
				else {
					Question q = questionDao.getQbyId(questions.get(i).getId());
					if (q.getQtype() < 8 || q.getQtype() == 11)
						studyQuizDao.updatecpracQuestion(myExamPaper.getId(),
								questions.get(i));
				}
			}
		}
		studyQuizDao.submitcpracPaper(myExamPaper);
		//examPaper = studyQuizDao.getMycpracPaper(myExamPaper.getId());
//		if (coursePage != null && coursePage.getId() > 0) {
//			// 判断章节的结业方式
//			coursePage = ((CoursePageDao)SpringContextUtil.getBean("coursePageDao")).getCp(coursePage.getId());
//			MyCPage myCPage = studyCourseDao.getMyCPage(
//					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
//							.getId(), course.getClassid());
//			// 先查出分数看看是否比以前的高，如果有换 然后看看是否有考过，如果有考过再查查结业方式，如果为考过，那么处理...
//			float sumScore = examPaper.getMep_tscore();
//			float myScore = myCPage.getMyscore();
//			if (sumScore > myScore) {
//				// 替换现有的成绩
//				studyCourseDao.updateStudyCpageCcore(myCPage, sumScore, course
//						.getClassid());
//			}
//			int ispassed = myCPage.getPassed2();// 用来判断以前是否已经通过
//			if (ispassed != 1) {
//				float passgrade = myCPage.getPracp().getPassgrade();// 练习的达标%比
//				float ep_tscore = myCPage.getPracp().getExamPaper()
//						.getEp_tscore();// 试卷总分
//				float yes_ = ep_tscore * (passgrade / 100);
//				if (sumScore > yes_) {
//					// 通过了，判断结业方式
//					int getcredit = coursePage.getGetcredit();
//					if (getcredit == 1) {
//						// 只设置通过passed2=1
//						studyCourseDao.updateStudyCpagePassed2(myCPage, course
//								.getClassid());
//					} else if (getcredit == 2) {
//						// 设置通过passed=1,然后把进度条填满
//						studyCourseDao.updateStudyCpagePassed2(myCPage, course
//								.getClassid());
//						int passtime = coursePage.getDuring() * 60;
//						studyCourseDao.saveMyCPage(myCPage,
//								course.getClassid(), 1, passtime);
//					} else {
//						// studyCourseDao.saveMyCPage(myCPage,classid,0,60);
//						studyCourseDao.updateStudyCpagePassed2(myCPage, course
//								.getClassid());
//						// 判断有没有学完
//						int passtime = coursePage.getDuring() * 60;
//						int passtime2 = myCPage.getPasstime2();
//						if (passtime2 >= passtime - 30) {
//							studyCourseDao.saveMyCPage(myCPage, course
//									.getClassid(), 1, passtime);
//						}
//					}
//				}
//			}
//		}
		return "courseprac_result1b1";
	}

	/**
	 * 课程练习答卷查看
	 * 
	 * @return
	 * @throws ElException
	 */
	public String courseprac_view() throws ElException {
		examPaper = studyQuizDao.getMycpracPaper(myExamPaper.getId());
		myExamPaper = studyQuizDao.getmycprac(myExamPaper.getId());
		examPaper.setUserage(getSessionIntValue(ElConstants.SESSION_AGE));
//		return "courseprac_view";
		return "courseprac_viewall";
	}

	public String cpracquestioninit() throws ElException {
		question = studyQuizDao.getQuestionBycprac(myExamPaper.getId(),
				question);
		if (question == null || 0 == question.getId()) {
			setElmessage("没找到你需要的试题。请确定该试题是否存在你相应场次的试卷中！");
			return "error";
		}
		if (question.getQtype() == 8) {
			if (null != question && null != question.getStuAnswer()
					&& !"".equals(question.getStuAnswer().trim())) {
				setElmessage("您所做答的打字题已提交，不容许重新作答！");
				return "qerror";
			}
			question.setRulestring(studyQuizDao.getQRulestrByREBid(myExamPaper
					.getId(), question));
			question.setAge(UserExcelUtil.getAgeBySfz(getSessionValue(ElConstants.SESSION_SHENFENZHENG)));///////
			return "cpracquestion_dazi";
		} else if (question.getQtype() == 9) {
			if (null != question && null != question.getStuAnswer()
					&& !"".equals(question.getStuAnswer().trim())) {
				recordId = 1;
				setElmessage("您所做答的邮件题已提交，不容许重新作答！");
				return "qerror";
			}
			return "cpracquestion_email";
		} else if (question.getQtype() == 10) {
			if (null != question && null != question.getStuAnswer()
					&& !"".equals(question.getStuAnswer().trim())) {
				setElmessage("您所做答的搜索题已提交，不容许重新作答！");
				return "qerror";
			}
			return "cpracquestion_search";
		} else {
			setElmessage("除邮件题，打字题和搜索题可新开窗口作答外其他类型试题不能新开窗口作答");
			return "error";
		}
		// return "cpracquestion";
	}

	public String cpracquestion_email() throws ElException {
		question = studyQuizDao.getQuestionBycprac(myExamPaper.getId(),
				question);
		return "cpracquestion_email";
	}

	public String cpracquestion_search() throws ElException {
		question = studyQuizDao.getQuestionBycprac(myExamPaper.getId(),
				question);
		questionart = questionart == null ? new QuestionArt() : questionart;
		String title = questionart.getTitle() == null ? "" : questionart
				.getTitle();
		questionarts = questionDao
				.listQarts(title, getPageNow(), getPageSize());
		if (null != questionarts)
			for (int i = 0; i < questionarts.size(); i++) {
				String name = questionarts.get(i).getContent();
				if (null != name)
					questionarts.get(i).setContent(
							name.length() > 121 ? name.substring(0, 120)
									+ "..." : name);
			}
		count = questionDao.listQartsSize(title);
		return "cpracquestion_search_list";
	}

	public String cpracquestion_submit() throws ElException {
		//
		if (question == null) {
			setElmessage("试题不在了!");
			return "error";
		}
//		try {
//			question = question == null ? new Question() : question;
//			// question.setStuAnswer(new String(getRequest().getParameter(
//			// "question.stuAnswer").getBytes("ISO-8859-1"), "UTF-8"));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		if (question.getQtype() == 8) {
			Question q = studyQuizDao.getQuestionBycprac(myExamPaper.getId(),
					question);
			question.setStuAnswer(getRequest().getParameter("stuanswer"));
			if (null == q.getStuAnswer() || "".equals(q.getStuAnswer().trim()))
				studyQuizDao.updatecpracQuestion(myExamPaper.getId(), question);
			else {
				setElmessage("您所做答的打字题已提交，不容许重新作答！");
				return "error";
			}
			return "cpracquestion_dazi_succ";
		}
		studyQuizDao.updatecpracQuestion(myExamPaper.getId(), question);
		if (question.getQtype() == 9) {
			return "cpracquestion_email_succ";
		} else {
			questionart = questionDao.getQart(questionart.getId());
			return "cpracquestion_search_succ";
		}
	}

	// 考试管理
	public String myrecentquiz_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myExamPapers = studyQuizDao.listMyRecentQuiz(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyQuizDao
				.listMyRecentQuizSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "myrecentquiz_list";
	}

	public String quizpaperinto() throws ElException {
		// course = courseDao.getCourseById(course.getId());
		// ExamRoom er = studyQuizDao.getExamRoomByUandC(course.getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		/*
		 * if (examRoom == null || examRoom.getId() <= 0) {
		 * setElmessage("没找到相关考试场次"); return "error"; }
		 * studyQuizDao.intoQuizPaper(getSessionIntValue(ElConstants.SESSION_USERID),
		 * examRoom.getId());
		 */
		return "quizpaper";
	}

	// public String quizpaperinit() throws ElException {
	// String iscommonStr = getRequest().getParameter("iscommon");
	// int iscommon = -1;
	// if (iscommonStr != null) {
	// iscommon = Integer.parseInt(iscommonStr);
	// }
	// if (iscommon == 0) {
	// myroom = studyQuizDao.getMyErsWithoutC(
	// myroom.getExamroom().getId(),
	// getSessionIntValue(ElConstants.SESSION_USERID), iscommon);
	// } else {
	// myroom = studyQuizDao.getMyErsWithoutC(
	// myroom.getExamroom().getId(),
	// getSessionIntValue(ElConstants.SESSION_USERID));
	// }
	//
	// myroom.setMyExamPapers(studyQuizDao.listMypaperByRidanUid(
	// getSessionIntValue(ElConstants.SESSION_USERID), myroom
	// .getExamroom().getId()));
	//
	// //页面处理考试是否能够进去(for)
	// for (int i = 0; i < myroom.getMyExamPapers().size(); i++) {
	// myExamPaper =
	// studyQuizDao.getMyEpById(myroom.getMyExamPapers().get(i).getId());
	// if (myExamPaper.getExamRoom().getEndtime().before(new Date())) {
	// //setElmessage("考试时间已经过了！");
	// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
	// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("考试时间已经过了！");
	// }
	// if (myExamPaper.getExamRoom().getBegintime().after(new Date())) {
	// //setElmessage("考试时间还未开始！");
	// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
	// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("考试时间还未开始！");
	// }
	// //判断考试次数 是否大于可考次数
	// if(myExamPaper.getMyexamcount()>=myExamPaper.getExamRoom().getExamcount()){
	// //setElmessage("您的考试次数已经足够！请等候成绩！");
	// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
	// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("您的考试次数已经足够！");
	// }
	//			
	// //判断是否已经考过，如果考过 就不用再考了
	// if(myExamPaper.getIspassed()==1){
	// //setElmessage("您的考试成绩已经达标，不用再考！");
	// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
	// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("您的考试成绩已经达标！");
	// }
	//			
	// if (myExamPaper.getExamRoom().getType() == 1 && examRoom.getValid() != 5)
	// {
	// if (myExamPaper.getPractimes() < myExamPaper.getExamPaper()
	// .getPractimes()) {
	// //setElmessage("您的考前练习次数不够!");
	// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
	// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("您的考前练习次数不够！");
	// }
	// if (myExamPaper.getPracscore() < myExamPaper.getExamPaper()
	// .getPracscore()) {
	// //setElmessage("您的考前练习最高分不够!");
	// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
	// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("您的考前练习最高分不够！");
	// }
	// }
	// if (myExamPaper.getStatus() == 3) {
	// //setElmessage("您的试卷已经批阅!");
	// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
	// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("您的试卷已经批阅！");
	// }
	// }
	// return "quizpaperinit";
	// }
	/**
	 * 查看考场历史记录
	 */
	public String myquizpaperlist() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		// if (examRoom.getEndtime().before(new Date())) {
		// setElmessage("考试时间已经过了！");
		// return "error";
		// }
		// if (examRoom.getBegintime().after(new Date())) {
		// setElmessage("考试时间还未开始！");
		// return "error";
		// }
		myExamPapers = studyQuizDao.listMyExampapers(
				getSessionIntValue(ElConstants.SESSION_USERID), examRoom
						.getId(), examPaper.getId());
		myroom = studyQuizDao.getMyErsWithoutR(examRoom.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID), -1);
		return "myquizpaperlist";
	}
	/**
	 * 查看学员答卷信息(考场历史记录)
	 * @return
	 * @throws ElException
	 */
	public String lookStudyQuizpaperlist() throws ElException {
		int userid = elUser.getId();
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		myExamPapers = studyQuizDao.listMyExampapers(userid, examRoom.getId(), examPaper.getId());
		myroom = studyQuizDao.getMyErsWithoutR(examRoom.getId(),userid, -1);
		return "lookStudyQuizpaperlist";
	}
	/**考场密码检测
	 * @return
	 * @throws ElException
	 */
	public String quizpaperinit_pwd() throws ElException {
		examRoom = eroomDao.getExamRoomByid(myroom.getExamroom().getId());
		if(!examRoom.getPwd().equals(myroom.getExamroom().getPwd())){
			setElmessage("密码错误，请联系管理员，重新输入!");
			return "quizpaperinit_pwd";
		}
		if(new Date().after(examRoom.getPwdtime())){
			setElmessage("密码期限已过，请联系管理员，重新输入!");
			return "quizpaperinit_pwd";
		}
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		int joinway = studyQuizDao.getStudyEroomJoinway(userid, examRoom
				.getId());
		if (joinway == 2) {// 也可用来检测 study_room是否有此学员
			if(examRoom.getIsApplication()==2){
				//自动分配
				if (!eroomDao.checkuser2eroom(examRoom.getId(),userid, examRoom.getClassid())) {
					eroomDao.adduser2eroom(examRoom.getId(), userid, 0, examRoom.getClassid(),
							CourseConstants.EXAMROOM_QJFS_SQ);
				}
			}else{
				setElmessage("考场未分配给该学员!");
				return "error";
			}
		}
		if (joinway == 0) {// 分配过来的
			examPapers = studyQuizDao.listStudyExamPaper(userid, examRoom
					.getId());
		} else {
			examPapers = examPaperDao.listEroomExamPaper(myroom.getExamroom()
					.getId());
		}
		for (int i = 0; i < examPapers.size(); i++) {
			if (examPapers.get(i).getStatus() != 1) {
				if (!studyQuizDao.checkStudyExamPaper(userid, examPapers.get(i)
						.getId(), examRoom.getId(), examRoom.getClassid())) {
					// 添加该学员到 学员试卷表中
					studyQuizDao.addStudyExamPaper(userid, examPapers.get(i)
							.getId(), examRoom.getId(), examRoom.getClassid());
				}
			}
		}
		String iscommonStr = getRequest().getParameter("iscommon");
		int iscommon = examRoom.getIscommon();
		if (iscommonStr != null) {
			iscommon = Integer.parseInt(iscommonStr);
		}
		myroom = studyQuizDao.getMyErsWithoutR(myroom.getExamroom().getId(),
				getSessionIntValue(ElConstants.SESSION_USERID), iscommon);
		// 设置考场的已考次数
		if (myroom.getExamroom() == null) {
			setElmessage("该考场还未给你分配试卷！！！");
			return "error";
		}
		myroom.setMyExamPapers(studyQuizDao.listMypaperByRidanUid(userid,
				examRoom.getId(), 0));

		// 页面处理考试是否能够进去(for)
		for (int i = 0; i < myroom.getMyExamPapers().size(); i++) {
			myExamPaper = studyQuizDao.getMyEpById(myroom.getMyExamPapers()
					.get(i).getId());
			if (examRoom.getEndtime().before(new Date())) {
				myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
				myroom.getMyExamPapers().get(i).setExamIsCenterRemack(
						"考试时间已经过了！");
			}
			if (examRoom.getBegintime().after(new Date())) {
				myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
				myroom.getMyExamPapers().get(i).setExamIsCenterRemack(
						"考试时间还未开始！");
			}
			// 判断试卷是否已被删除
			if (myroom.getMyExamPapers().get(i).getIsdel() == 1) {
				myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
				myroom.getMyExamPapers().get(i).setExamIsCenterRemack(
						"该试卷已被管理员删除！");
			}
		}
		return "quizpaperinit_pwd_succ";
	}
	/**
	 * 考场
	 * @return
	 * @throws ElException
	 */
	public String quizpaperinit() throws ElException {
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		
		String classid = getRequest().getParameter("classid");
		if(classid!=null&&!classid.equals("")){
			ExamRoom em = eroomDao.getExamRoomByid_cisco(myroom.getExamroom().getId(),Integer.parseInt(classid));
			if(em!=null){
				if(em.getFirstLearnLaterExam() == 1){//考场先学后考
					//查询用户该考场对应的培训班中的课程是否全部完成
					if(!courseDao.checkCoursesIsAllPass(em.getId(),Integer.parseInt(classid),userid)){
						this.setElmessage("培训班中还有课程未完成，请先完成所有课程的学习");
						return "error";
					}
				}
				if(em.getStandardLine()!=0){//智能辅导分达标线
					//TODO
				}
			}else{
				this.setElmessage("培训班未关联考场");
				return "error";
			}
		}
		if(course!=null && course.getFirstLearn() == 1){//先学后考
			if(course.getGetcredit() == 3){//学完且考过
				//判断该课程是否学习进度达到100%即passed == 1
				boolean flag = classDao.checkStudyCourseIsPassed(userid, course.getId());
				if(!flag){
					this.setElmessage("对不起，本课程的结业方式是学完且考过，您当前还没有学完，不能参加结业考试");
					return "error";
				}
			}
		}
		
		// 现在分配试卷的方式已经改变，所有此地开始分配试卷
		// 1.获取该学员所分配的试卷（如果是申请过来的，那么获取考场所有试卷）
		// 2.然后把试卷分配给该学员
		examRoom = eroomDao.getExamRoomByid(myroom.getExamroom().getId());
		if(examRoom.getPwdneed()==1){
			
			return "quizpaperinit_pwd";
		}
		if (examRoom == null) {
			setElmessage("未找到考场！");
			return "error";
		}
		if(examRoom.getAutoAssign() == 1){//考场是自动分配考场===>自动分配用户
			//自动分配   考场加试卷
			if (!eroomDao.checkuser2eroom(examRoom.getId(),userid, examRoom.getClassid())) {
				eroomDao.adduser2eroom(examRoom.getId(), userid, 0, -1,
						CourseConstants.EXAMROOM_FPFS_SQ);
			}
		}
		
		
		int joinway = studyQuizDao.getStudyEroomJoinway(userid, examRoom
				.getId());
		if (joinway == 2) {// 也可用来检测 study_room是否有此学员
			//判断考试是否全警类型
			if(examRoom.getIsApplication()==2){
				//自动分配
				if (!eroomDao.checkuser2eroom(examRoom.getId(),userid, examRoom.getClassid())) {
					eroomDao.adduser2eroom(examRoom.getId(), userid, 0, examRoom.getClassid(),
							CourseConstants.EXAMROOM_QJFS_SQ);
				}
			}else{
				setElmessage("考场未分配给该学员!");
				return "error";
			}
		}
		//if (joinway == 0) {// 分配过来的
		if(examRoom.getIsApplication()!=2){
			examPapers = studyQuizDao.listStudyExamPaper(userid, examRoom
					.getId());
		} else {
			examPapers = examPaperDao.listEroomExamPaper(myroom.getExamroom()
					.getId());
		}
		for (int i = 0; i < examPapers.size(); i++) {
			// if
			// (!studyQuizDao.hasInQuizPaper(getSessionIntValue(ElConstants.SESSION_USERID),
			// examRoom.getId(), // 检测是否已经进入考场
			// examPapers.get(i).getId(),examRoom.getClassid(),0)) {
			// studyQuizDao.intoQuizPaper(getSessionIntValue(ElConstants.SESSION_USERID),
			// examRoom.getId(),
			// examPapers.get(i).getId(), examRoom.getClassid(),0);
			// }
			if (examPapers.get(i).getStatus() != 1) {
				if (!studyQuizDao.checkStudyExamPaper(userid, examPapers.get(i)
						.getId(), examRoom.getId(), examRoom.getClassid())) {
					// 添加该学员到 学员试卷表中
					studyQuizDao.addStudyExamPaper(userid, examPapers.get(i)
							.getId(), examRoom.getId(), examRoom.getClassid());
				}
			}
		}
		
		String iscommonStr = getRequest().getParameter("iscommon");
		// int iscommon = -1;
		int iscommon = examRoom.getIscommon();
		if (iscommonStr != null) {
			iscommon = Integer.parseInt(iscommonStr);
		}
		// if (iscommon == 0) {
		// myroom = studyQuizDao.getMyErsWithoutC(
		// myroom.getExamroom().getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID), iscommon);
		myroom = studyQuizDao.getMyErsWithoutR(myroom.getExamroom().getId(),
				getSessionIntValue(ElConstants.SESSION_USERID), iscommon);
		// } else {
		// myroom = studyQuizDao.getMyErsWithoutC(
		// myroom.getExamroom().getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		// myroom = studyQuizDao.getMyErsWithoutR(
		// myroom.getExamroom().getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID), 0);
		// }
		// 设置考场的已考次数
		// myroom.setSrrcount(studyQuizDao.getMyEroomRecordCount(examRoom.getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID)));
		if (myroom.getExamroom() == null) {
			setElmessage("该考场还未给你分配试卷！！！");
			return "error";
		}
		myroom.setMyExamPapers(studyQuizDao.listMypaperByRidanUid(userid,
				examRoom.getId(), 0));

		// 页面处理考试是否能够进去(for)
		for (int i = 0; i < myroom.getMyExamPapers().size(); i++) {
			myExamPaper = studyQuizDao.getMyEpById(myroom.getMyExamPapers()
					.get(i).getId());
			// if (myExamPaper.getStatus() == 2) {
			// // setElmessage("您的考试次数已经足够！请等候成绩！");
			// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
			// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("已经作答！");
			// }
			if (examRoom.getEndtime().before(new Date())) {
				// setElmessage("考试时间已经过了！");
				myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
				myroom.getMyExamPapers().get(i).setExamIsCenterRemack(
						"考试时间已经过了！");
			}
			if (examRoom.getBegintime().after(new Date())) {
				// setElmessage("考试时间还未开始！");
				myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
				myroom.getMyExamPapers().get(i).setExamIsCenterRemack(
						"考试时间还未开始！");
			}
			// 判断试卷是否已被删除
			if (myroom.getMyExamPapers().get(i).getIsdel() == 1) {
				myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
				myroom.getMyExamPapers().get(i).setExamIsCenterRemack(
						"该试卷已被管理员删除！");
			}
			// 判断考试次数 是否大于可考次数...
			// //判断是否已经考过，如果考过 就不用再考了
			// if(myExamPaper.getIspassed()==1){
			// //setElmessage("您的考试成绩已经达标，不用再考！");
			// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
			// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("您的考试成绩已经达标！");
			// }
			//			
			// if (myExamPaper.getExamRoom().getType() == 1 &&
			// examRoom.getValid() != 5) {
			// if (myExamPaper.getPractimes() < myExamPaper.getExamPaper()
			// .getPractimes()) {
			// //setElmessage("您的考前练习次数不够!");
			// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
			// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("您的考前练习次数不够！");
			// }
			// if (myExamPaper.getPracscore() < myExamPaper.getExamPaper()
			// .getPracscore()) {
			// //setElmessage("您的考前练习最高分不够!");
			// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
			// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("您的考前练习最高分不够！");
			// }
			// }
			// if (myExamPaper.getStatus() == 3) {
			// //setElmessage("您的试卷已经批阅!");
			// myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
			// myroom.getMyExamPapers().get(i).setExamIsCenterRemack("您的试卷已经批阅！");
			// }
		}
		return "quizpaperinit";
	}
	
	
	
	/**
	 * 考场
	 * @return
	 * @throws ElException
	 */
	public String quizpaperinit2() throws ElException {
		// 现在分配试卷的方式已经改变，所有此地开始分配试卷
		// 1.获取该学员所分配的试卷（如果是申请过来的，那么获取考场所有试卷）
		// 2.然后把试卷分配给该学员
		examRoom = eroomDao.getExamRoomByid(myroom.getExamroom().getId());
		if (examRoom == null) {
			setElmessage("未找到考场！");
			return "error";
		}
		if(examRoom.getPwdneed()==1){
			
			return "quizpaperinit_pwd";
		}
		
	//	int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		
		userid = this.getUserid();
		int joinway = studyQuizDao.getStudyEroomJoinway(userid, examRoom
				.getId());
		if (joinway == 2) {// 也可用来检测 study_room是否有此学员
			//判断考试是否全警类型
			if(examRoom.getIsApplication()==2){
				//自动分配
				if (!eroomDao.checkuser2eroom(examRoom.getId(),userid, examRoom.getClassid())) {
					eroomDao.adduser2eroom(examRoom.getId(), userid, 0, examRoom.getClassid(),
							CourseConstants.EXAMROOM_QJFS_SQ);
				}
			}else{
				setElmessage("考场未分配给该学员!");
				return "error";
			}
		}
		//if (joinway == 0) {// 分配过来的
		if(examRoom.getIsApplication()!=2){
			examPapers = studyQuizDao.listStudyExamPaper(userid, examRoom
					.getId());
		} else {
			examPapers = examPaperDao.listEroomExamPaper(myroom.getExamroom()
					.getId());
		}
		for (int i = 0; i < examPapers.size(); i++) {
			if (examPapers.get(i).getStatus() != 1) {
				if (!studyQuizDao.checkStudyExamPaper(userid, examPapers.get(i)
						.getId(), examRoom.getId(), examRoom.getClassid())) {
					// 添加该学员到 学员试卷表中
					studyQuizDao.addStudyExamPaper(userid, examPapers.get(i)
							.getId(), examRoom.getId(), examRoom.getClassid());
				}
			}
		}
		String iscommonStr = getRequest().getParameter("iscommon");
		int iscommon = examRoom.getIscommon();
		if (iscommonStr != null) {
			iscommon = Integer.parseInt(iscommonStr);
		}
		
		myroom = studyQuizDao.getMyErsWithoutR(myroom.getExamroom().getId(),
				getSessionIntValue(ElConstants.SESSION_USERID), iscommon);
		if (myroom.getExamroom() == null) {
			setElmessage("该考场还未给你分配试卷！！！");
			return "error";
		}
		myroom.setMyExamPapers(studyQuizDao.listMypaperByRidanUid(userid,
				examRoom.getId(), 0));

		// 页面处理考试是否能够进去(for)
		for (int i = 0; i < myroom.getMyExamPapers().size(); i++) {
			myExamPaper = studyQuizDao.getMyEpById(myroom.getMyExamPapers()
					.get(i).getId());
			
			if (examRoom.getEndtime().before(new Date())) {
				// setElmessage("考试时间已经过了！");
				myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
				myroom.getMyExamPapers().get(i).setExamIsCenterRemack(
						"考试时间已经过了！");
			}
			if (examRoom.getBegintime().after(new Date())) {
				// setElmessage("考试时间还未开始！");
				myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
				myroom.getMyExamPapers().get(i).setExamIsCenterRemack(
						"考试时间还未开始！");
			}
			// 判断试卷是否已被删除
			if (myroom.getMyExamPapers().get(i).getIsdel() == 1) {
				myroom.getMyExamPapers().get(i).setExamIsCenter(-1);
				myroom.getMyExamPapers().get(i).setExamIsCenterRemack(
						"该试卷已被管理员删除！");
			}
		}
		return "quizpaperinit2";
	
	}
	public String qpracInit() throws ElException {
		if (myExamPaper == null) {
			examRoom = eroomDao.getExamRoomByid(myroom.getExamroom().getId());
		} else {
			myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
			examRoom = myExamPaper.getExamRoom();
		}
	
		String study_macAddr = studyQuizDao.getStudyMacAdr(
				getSessionIntValue(ElConstants.SESSION_USERID), examRoom
						.getId());
		if ("no".equals(study_macAddr)) {
			setElmessage("no!!!");
			return "error";
		}
		getRequest().setAttribute("study_macAddr", study_macAddr);
		// 第1次访问该试卷，更新mac进行绑定
		// 把用户ip更新到数据库
		return "qpracInit";
	}

	/**
	 * 考场
	 * @return
	 * @throws ElException
	 */
	public String lookStudyEroom() throws ElException {
		// 现在分配试卷的方式已经改变，所有此地开始分配试卷
		// 1.获取该学员所分配的试卷（如果是申请过来的，那么获取考场所有试卷）
		// 2.然后把试卷分配给该学员
		examRoom = eroomDao.getExamRoomByid(myroom.getExamroom().getId());
		if (examRoom == null) {
			setElmessage("未找到考场！");
			return "error";
		}
		int userid = elUser.getId();
		int joinway = studyQuizDao.getStudyEroomJoinway(userid, examRoom
				.getId());
		if (joinway == 2) {// 也可用来检测 study_room是否有此学员
			setElmessage("考场未分配给该学员!");
			return "error";
		}
		if (joinway == 1) {// 申请过来的
			examPapers = examPaperDao.listEroomExamPaper(myroom.getExamroom()
					.getId());
		} else {
			examPapers = studyQuizDao.listStudyExamPaper(userid, examRoom
					.getId());
		}
		for (int i = 0; i < examPapers.size(); i++) {
			if (examPapers.get(i).getStatus() != 1) {
				if (!studyQuizDao.checkStudyExamPaper(userid, examPapers.get(i)
						.getId(), examRoom.getId(), examRoom.getClassid())) {
					// 添加该学员到 学员试卷表中
					studyQuizDao.addStudyExamPaper(userid, examPapers.get(i)
							.getId(), examRoom.getId(), examRoom.getClassid());
				}
			}
		}
		String iscommonStr = getRequest().getParameter("iscommon");
		int iscommon = examRoom.getIscommon();
		if (iscommonStr != null) {
			iscommon = Integer.parseInt(iscommonStr);
		}
		myroom = studyQuizDao.getMyErsWithoutR(myroom.getExamroom().getId(),
				userid, iscommon);
		if (myroom.getExamroom() == null) {
			setElmessage("该考场还未给你分配试卷！！！");
			return "error";
		}
		myroom.setMyExamPapers(studyQuizDao.listMypaperByRidanUid(userid,
				examRoom.getId(), 0));
		return "lookStudyEroom";
	}

	public String quizpaperinit_byepid() throws ElException {
		myExamPaper = new MyExamPaper();
		myExamPaper.setId(studyQuizDao.getMypaperIdByRidanUid(
				getSessionIntValue(ElConstants.SESSION_USERID), examRoom
						.getId(), examPaper.getId()));
		if (myExamPaper.getId() <= 0) {
			setElmessage("考试次数已经足够!");
			return "error";
		}
//		myExamPaper.setBegintime(new Timestamp(System.currentTimeMillis()));
		// String macAddr = getRequest().getParameter("macAddr");
		// String ipAddr = getRequest().getParameter("ipAddr");
		// getRequest().setAttribute("ipAddr", ipAddr);
		// getRequest().setAttribute("macAddr", macAddr);
//		return "quizpaper";
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		examPaper = examPaper == null ? new ExamPaper():examPaper;
		if(examPaper.getShowType() == 0){//一屏一题
			return this.quizpaper();
		}else if(examPaper.getShowType() == 10){//知识竞赛
			return this.quizpaper_contest();
		}else{//一屏一卷
			return "";
		}
	}
	/**
	 * 知识竞赛
	 * @return
	 * @throws ElException
	 */
	public String quizpaper_contest() throws ElException{
		myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());

		if (myExamPaper == null || myExamPaper.getId() == 0) {
			setElmessage("这个答卷不存在，请与管理员联系（重新分配考试）!!!");
			return "error";
		}
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		if (myExamPaper.getTester().getId() != userid) {
			setElmessage("这个答卷不是你的答卷，请从正常渠道进入！");
			return "error";
		}
		if (myExamPaper.getExamRoom().getEndtime().before(new Date())) {
			setElmessage("考试时间已经过了！");
			return "error";
		}
		if (myExamPaper.getExamRoom().getBegintime().after(new Date())) {
			setElmessage("考试时间还未开始！");
			return "error";
		}
		if (myExamPaper.getStatus() == 2) {
			setElmessage("您的试卷已经提交，等待批阅中!");
			return "error";
		}

		if (myExamPaper.getStatus() == 3) {
			setElmessage("您的试卷已经批阅!");
			return "error";
		}
		examRoom = myExamPaper.getExamRoom();
		// String ipAddr = "";
		// 判断ip段是否限定
		if (examRoom.getIsIpLimit() == 1) {
			UtilMacAddress uma = new UtilMacAddress();
			// 1.获取客户端传过来的ip
			// ipAddr = getRequest().getParameter("ipAddr");
			// 把用户ip更新到数据库
			// studyQuizDao.updateStudyIpAddr(
			// getSessionIntValue(ElConstants.SESSION_USERID),
			// myExamPaper.getExamRoom().getId(), myExamPaper
			// .getClassId(), ipAddr);
			if (examRoom.getIpStart() != null
					&& examRoom.getIpEnd() != null) {
				// 获取开始ip和结束ip
				String[] ipStrat = examRoom.getIpStart().split("_");
				String[] ipEnd = examRoom.getIpEnd().split("_");
				boolean isIpOk = false;
				for (int i = 0; i < ipStrat.length; i++) {
					// 处理请求过来的ip是否在限定段
					isIpOk = uma.checkIpAddr(ipAddr, ipStrat[i], ipEnd[i]);
					if (isIpOk) {
						break;
					}
				}
				if (isIpOk == false) {
					setElmessage("您的ip地址不在有效段，不能进入考试，如有疑惑请联系管理员!!!");
					return "error";
				}
			}
		}
		// 看看是否有设定绑定mac
		if (examRoom.getIsMacBand() == 1) {
			// 获取客户端过来的mac
			// String macAddr = getRequest().getParameter("macAddr");
			if (macAddr == null || "".equals(macAddr)) {
				// 获取mac地址失败
				setElmessage("mac地址获取失败，请与管理员联系！！！");
				return "error";
			}

			// 根据用户id，考场id以及班级id得到该学员该场考试所绑定的mac
			String study_macAddr = studyQuizDao.getStudyMacAdr(
					getSessionIntValue(ElConstants.SESSION_USERID),
					myExamPaper.getExamRoom().getId());
			if ("no".equals(study_macAddr)) {
				setElmessage("no!!!");
				return "error";
			}
			if (study_macAddr == null || "".equals(study_macAddr)) {
				// 第1次访问该试卷，更新mac进行绑定
				studyQuizDao.updateStudyMacAddr(
						getSessionIntValue(ElConstants.SESSION_USERID),
						myExamPaper.getExamRoom().getId(), macAddr);
			} else {
				// 判断mac地址是否相等
				if (!study_macAddr.equals(macAddr)) {
					// 地址不对
					// setElmessage("不能在另一地点登入考试，如有问题请联系管理员!!!");
					setElmessage("对不起，您不能更换电脑进行考试!!!");
					return "error";
				}
			}
		}
		// 把用户ip更新到数据库
		if (examRoom.getIsIpLimit() == 1) {
			studyQuizDao.updateStudyIpAddr(
					getSessionIntValue(ElConstants.SESSION_USERID),
					myExamPaper.getExamRoom().getId(), ipAddr);
		}
		
		if (myExamPaper.getStatus() == 0 || myExamPaper.getStatus() == 1) {
			studyQuizDao.setquizinfo(myExamPaper.getId());
			myExamPaper.setStatus(1);
			studyQuizDao.setQuizPaperStatus(myExamPaper);
			// 答卷状态有改变，更新考场等相关状态
			studyQuizDao.setStudyEroomStatus(myExamPaper.getExamRoom().getId(),
					userid);
			//检测并校正已考次数
			studyQuizDao.setStudyExampaperQuizcount(userid, myExamPaper.getExamRoom().getId(), myExamPaper.getExamPaper().getId());
			//更改试卷状态 为不可编辑
			examPaperDao.updateExampaperIseditor(myExamPaper.getExamPaper().getId());
			//添加考试记录
			recordId=studyQuizDao.addStudyQuizinfoRecord(myExamPaper.getId(), "study_quizinfo_record");
		}
		
		examPaper = studyQuizDao.getMyExamPaper_(myExamPaper.getId());
		
		EpQStatus.addExampaper(examPaper.getId());
			
		//}
		long xx = (myExamPaper.getExamRoom().getEndtime().getTime() - System
				.currentTimeMillis()) / 1000;
		if (xx < examPaper.getDuring() * 60) {
			examPaper.setDuring((int) xx / 60);
		}
		return "quizpaper_contest";
	}

	public String quizpaper() throws ElException {// 2222222222222
		// //1.获取客户端传过来的ip
		// String ipAddr=getRequest().getParameter("ipAddr");
		// //2.根据ip获取mac
		// UtilMacAddress uma=new UtilMacAddress();
		// //String macAddr=uma.getMACAddress(ipAddr);
		// //获取客户端过来的mac
		// String macAddr=getRequest().getParameter("macAddr");
		// if(macAddr==null||"".equals(macAddr)){
		// //获取mac地址失败
		// setElmessage("mac地址获取失败，请与管理员联系！！！");
		// return "error";
		// }
		// 判断mac
		// 1.判断该考场是否设定绑定mac
		// 2.判断数据库中的该学员是否已绑定mac地址
		// 3.判断该考场是否设定ip段限定
		// 4.判断ip段
		myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());

		if (myExamPaper == null || myExamPaper.getId() == 0) {
			setElmessage("这个答卷不存在，请与管理员联系（重新分配考试）!!!");
			return "error";
		}
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		if (myExamPaper.getTester().getId() != userid) {
			setElmessage("这个答卷不是你的答卷，请从正常渠道进入！");
			return "error";
		}
		if (myExamPaper.getExamRoom().getEndtime().before(new Date())) {
			setElmessage("考试时间已经过了！");
			return "error";
		}
		if (myExamPaper.getExamRoom().getBegintime().after(new Date())) {
			setElmessage("考试时间还未开始！");
			return "error";
		}
		// float epscore=myExamPaper.getExamPaper().getEp_tscore();//试卷总分
		// float erscore=myExamPaper.getExamRoom().getPassgrade();//试卷的达标分
		// 判断考试次数 是否大于可考次数
		// if(myExamPaper.getMyexamcount()>=myExamPaper.getExamRoom().getExamcount()){
		// //setElmessage("您的试卷已提交！请等候成绩！");
		// setElmessage("您的考试次数已经足够！请等候成绩！");
		// return "error";
		// }

		// 判断是否已经考过，如果考过 就不用再考了
		// if(myExamPaper.getIspassed()==1){
		// setElmessage("您的考试成绩已经达标，不用再考！");
		// return "error";
		// }

		// if (myExamPaper.getExamRoom().getType() == 1 && examRoom.getValid()
		// != 5) {
		// if (myExamPaper.getPractimes() < myExamPaper.getExamPaper()
		// .getPractimes()) {
		// setElmessage("您的考前练习次数不够!");
		// return "error";
		// }
		// if (myExamPaper.getPracscore() < myExamPaper.getExamPaper()
		// .getPracscore()) {
		// setElmessage("您的考前练习最高分不够!");
		// return "error";
		// }
		// }
		if (myExamPaper.getStatus() == 2) {
			setElmessage("您的试卷已经提交，等待批阅中!");
			return "error";
		}

		if (myExamPaper.getStatus() == 3) {
			setElmessage("您的试卷已经批阅!");
			return "error";
		}

//		if (myExamPaper != null) {
			// 获取考场信息，
			examRoom = myExamPaper.getExamRoom();
			// String ipAddr = "";
			// 判断ip段是否限定
			if (examRoom.getIsIpLimit() == 1) {
				UtilMacAddress uma = new UtilMacAddress();
				// 1.获取客户端传过来的ip
				// ipAddr = getRequest().getParameter("ipAddr");
				// 把用户ip更新到数据库
				// studyQuizDao.updateStudyIpAddr(
				// getSessionIntValue(ElConstants.SESSION_USERID),
				// myExamPaper.getExamRoom().getId(), myExamPaper
				// .getClassId(), ipAddr);
				if (examRoom.getIpStart() != null
						&& examRoom.getIpEnd() != null) {
					// 获取开始ip和结束ip
					String[] ipStrat = examRoom.getIpStart().split("_");
					String[] ipEnd = examRoom.getIpEnd().split("_");
					boolean isIpOk = false;
					for (int i = 0; i < ipStrat.length; i++) {
						// 处理请求过来的ip是否在限定段
						isIpOk = uma.checkIpAddr(ipAddr, ipStrat[i], ipEnd[i]);
						if (isIpOk) {
							break;
						}
					}
					if (isIpOk == false) {
						setElmessage("您的ip地址不在有效段，不能进入考试，如有疑惑请联系管理员!!!");
						return "error";
					}
				}
			}
			// 看看是否有设定绑定mac
			if (examRoom.getIsMacBand() == 1) {
				// 获取客户端过来的mac
				// String macAddr = getRequest().getParameter("macAddr");
				if (macAddr == null || "".equals(macAddr)) {
					// 获取mac地址失败
					setElmessage("mac地址获取失败，请与管理员联系！！！");
					return "error";
				}

				// Timestamp time =
				// studyQuizDao.getMyComputerYesTime(myExamPaper,
				// getSessionIntValue(ElConstants.SESSION_USERID), macAddr);
				// if(time!=null){
				// Timestamp(System.currentTimeMillis()));
				// if(System.currentTimeMillis()<time.getTime()){
				// setElmessage("该电脑已有其他学员在该考场中使用，请使用其他电脑！");
				// return "error";
				// }
				// }

				// 根据用户id，考场id以及班级id得到该学员该场考试所绑定的mac
				String study_macAddr = studyQuizDao.getStudyMacAdr(
						getSessionIntValue(ElConstants.SESSION_USERID),
						myExamPaper.getExamRoom().getId());
				if ("no".equals(study_macAddr)) {
					setElmessage("no!!!");
					return "error";
				}
				if (study_macAddr == null || "".equals(study_macAddr)) {
					// 第1次访问该试卷，更新mac进行绑定
					studyQuizDao.updateStudyMacAddr(
							getSessionIntValue(ElConstants.SESSION_USERID),
							myExamPaper.getExamRoom().getId(), macAddr);
				} else {
					// 判断mac地址是否相等
					if (!study_macAddr.equals(macAddr)) {
						// 地址不对
						// setElmessage("不能在另一地点登入考试，如有问题请联系管理员!!!");
						setElmessage("对不起，您不能更换电脑进行考试!!!");
						return "error";
					}
				}
			}
			// 把用户ip更新到数据库
			if (examRoom.getIsIpLimit() == 1) {
				studyQuizDao.updateStudyIpAddr(
						getSessionIntValue(ElConstants.SESSION_USERID),
						myExamPaper.getExamRoom().getId(), ipAddr);
			}
//		}
		// if (myExamPaper != null) {
		// // 获取考场信息
		// examRoom = myExamPaper.getExamRoom();
		// if (examRoom.getIsMacBand() == 1) {
		// studyQuizDao.updateStudyMacAddr(
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// myExamPaper.getExamRoom().getId(), macAddr);
		// }
		// if (examRoom.getIsIpLimit() == 1) {
		// studyQuizDao.updateStudyIpAddr(
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// myExamPaper.getExamRoom().getId(), ipAddr);
		// }
		// }

		// if (myExamPaper.getStatus() == 2 || myExamPaper.getStatus() == 3)
		// {//2：等待批阅 3：已经批阅
		// setElmessage("您的试卷已提交！请等候成绩！");
		// return "error";
		// }
		/*
		 * if (myExamPaper.getExamRoom().getType() == 1 && examRoom.getValid() !=
		 * 5) { if (myExamPaper.getPractimes() < myExamPaper.getExamPaper()
		 * .getPractimes()) { setElmessage("您的考前练习次数不够!"); return "error"; } if
		 * (myExamPaper.getPracscore() < myExamPaper.getExamPaper()
		 * .getPracscore()) { setElmessage("您的考前练习最高分不够!"); return "error"; } }
		 * if (myExamPaper.getStatus() == 3) { setElmessage("您的试卷已经批阅!"); return
		 * "error"; }
		 */
		if (myExamPaper.getStatus() == 0 || myExamPaper.getStatus() == 1) {
			// 清除答卷
			// courseDao.rsetStudyExamPaper(myExamPaper.getId());
			/*if(examRoom.getCacheepsize()==-1){
				if (myroom == null || myroom.getExamroom() == null) {
					examPaper = examPaperDao.getEPAllInfoById(myExamPaper
							.getExamPaper().getId());
				} else {
					examPaper = examPaperDao.getEPAllInfoById(myExamPaper
							.getExamPaper().getId(), myroom.getExamroom().getId());
				}
			}else{
				examPaper = EroomEpCache.getExamPaper(examRoom.getId(), myExamPaper.getExamPaper().getId()	);
			}
			EpQStatus.addExampaper(examPaper.getId());
			int epqsort=examRoom.getEpqsort();
			int[] arr=null;
			if (null != examPaper && null != examPaper.getEpBlocks())
				for (int j = 0; j < examPaper.getEpBlocks().size(); j++) {
					ExamPaperBlock ebp = examPaper.getEpBlocks().get(j);
					if (null != ebp.getQuestions()) {
						if(epqsort==1){
							//产生一个随机数组来打乱试题的排序
							arr=ExamPaperUtil.getRandomArray(ebp.getQuestions().size());
						}
						for (int k = 0; k < ebp.getQuestions().size(); k++) {
							Question qj = ebp.getQuestions().get(k);
							if(epqsort==1){
								qj.setSortid(arr[k]);
							}
							if (!studyQuizDao.checkStudyQuestion(myExamPaper
									.getId(), qj)
									&& !studyQuizDao.checkStudyQuestionSort(
											myExamPaper.getId(), qj))
								studyQuizDao.insertStudyQuestion(myExamPaper
										.getId(), qj);
							if (qj.getQtype() == 7 && qj.getChilds() != null) {
								for (int i = 0; i < qj.getChilds().size(); i++) {
									qj.getChilds().get(i).setEpblock(
											qj.getEpblock());
									if (!studyQuizDao.checkStudyQuestion(
											myExamPaper.getId(), qj.getChilds()
													.get(i))
											)
										studyQuizDao.insertStudyQuestion(
												myExamPaper.getId(), qj
														.getChilds().get(i));
//									&& !studyQuizDao
//									.checkStudyQuestionSort(
//											myExamPaper.getId(),
//											qj.getChilds().get(i))
								}
							}
						}
					}
				}*/
			studyQuizDao.setquizinfo(myExamPaper.getId());
			myExamPaper.setStatus(1);
			studyQuizDao.setQuizPaperStatus(myExamPaper);
			// 答卷状态有改变，更新考场等相关状态
			studyQuizDao.setStudyEroomStatus(myExamPaper.getExamRoom().getId(),
					userid);
			//检测并校正已考次数
			studyQuizDao.setStudyExampaperQuizcount(userid, myExamPaper.getExamRoom().getId(), myExamPaper.getExamPaper().getId());
			//更改试卷状态 为不可编辑
			examPaperDao.updateExampaperIseditor(myExamPaper.getExamPaper().getId());
			//添加考试记录
			recordId=studyQuizDao.addStudyQuizinfoRecord(myExamPaper.getId(), "study_quizinfo_record");
			//在学员考场表中记录该考生的最后开始考试时间，用于统计
//			studyQuizDao.updateStudyExamBegintime(userid, myExamPaper.getExamRoom().getId(), new Timestamp(System.currentTimeMillis()));
//			myExamPaper.getExamPaper().setStuview(examPaper.getStuview());
		}// else {
		examPaper = studyQuizDao.getMyExamPaper_(myExamPaper.getId());
		
		EpQStatus.addExampaper(examPaper.getId());
			
		//}
		long xx = (myExamPaper.getExamRoom().getEndtime().getTime() - System
				.currentTimeMillis()) / 1000;
		// xx = xx -examPaper.getDuring()*60;
		if (xx < examPaper.getDuring() * 60) {
			examPaper.setDuring((int) xx / 60);
		}
		// studyQuizDao.getMyExamPaper(myExamPaper.getExamRoom()
		// .getId(), myExamPaper.getExamPaper().getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		// 页面需要显示用户的相关信息
//		elUser = userDao
//				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		// 设定该答卷的有效时间
		// if (examRoom.getIsMacBand() == 1) {
		// Timestamp time = new Timestamp((System.currentTimeMillis()));//系统时间
		// time.setMinutes(time.getMinutes()+examPaper.getDuring());
		// myExamPaper.setExamyestime(time);
		// studyQuizDao.updateStudyExamTime(myExamPaper);
		//			
		// }

		return "quizpaper1b1";
	}
	public String quizpaper_blockquestions() throws ElException {
		int bid = question.getEpblock().getId();
		questions = studyQuizDao.listQuizQuestions(myExamPaper.getId(), bid,getPN());
		ExamPaperBlock epb = examPaperDao.getEpbById(bid);
		epb.setQuestions(questions);
		question.setEpblock(epb);
		return "quizpaper_blockquestions";
	}
	/**
	 * 显示一道题目
	 * @return
	 * @throws ElException
	 */
	public String quizpaper_oneQuestion() throws ElException{
		int bid = question.getEpblock().getId();
		//获取试卷的第一大题的第一小题
		question = studyQuizDao.getQuestionBySortBid(myExamPaper.getId(), question);
		ExamPaperBlock epb = examPaperDao.getEpbById(bid);
		
		question.setEpblock(epb);
		return "quizpaper_oneQuestion";
	}
	/**答卷查看页
	 * @return
	 * @throws ElException
	 */
	public String myquizpapergradeview() throws ElException {
		if (myExamPaper == null || myExamPaper.getId() == 0) {
			setElmessage("这个答卷不存在，请与管理员联系（重新分配考试）");
			return "error";
		}
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
		examRoom = eroomDao.getExamRoomByid(myExamPaper.getExamRoom().getId());
		myExamPapers = studyQuizDao.listMypaperByRidanUid(userid,
				examRoom.getId(), 0);
		myroom = studyQuizDao.getMyErsWithoutR(examRoom.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID), examRoom.getIscommon());
		for (int i = 0; i < myExamPapers.size(); i++) {
//			myExamPaper = studyQuizDao.getMyEpById(myExamPapers
//					.get(i).getId());
			 
			if (examRoom.getEndtime().before(new Date())) {
				myExamPapers.get(i).setExamIsCenter(-1);
				myExamPapers.get(i).setExamIsCenterRemack(
						"考试时间已经过了！");
			}
			if (examRoom.getBegintime().after(new Date())) {
				myExamPapers.get(i).setExamIsCenter(-1);
				myExamPapers.get(i).setExamIsCenterRemack(
						"考试时间还未开始！");
			}
			// 判断试卷是否已被删除
			if (myExamPapers.get(i).getIsdel() == 1) {
				myExamPapers.get(i).setExamIsCenter(-1);
				myExamPapers.get(i).setExamIsCenterRemack(
						"该试卷已被管理员删除！");
			}
		}
		return "myquizpapergradeview";
	}
	/**答卷提交
	 * @return
	 * @throws ElException
	 */
	public String quizpaper_submit() throws ElException {
//		long l = System.currentTimeMillis();
		if (myExamPaper == null || myExamPaper.getId() == 0) {
			setElmessage("这个答卷不存在，请与管理员联系（重新分配考试）");
			return "error";
		}
//		int stuview = myExamPaper.getExamPaper().getStuview();
		int myExamPaperid = myExamPaper.getId();
		MyExamPaper m = studyQuizDao.getMyEpById(myExamPaperid);
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		if (m.getTester().getId() != userid) {
			setElmessage("这个答卷不是你的答卷，请从正常渠道进入！");
			return "error";
		}
		if (m.getStatus() == 2 || m.getStatus() == 3) {
			setElmessage("您的试卷已提交，请不要重复提交！");
			return "error";
		}
		if (null != questions) {
			for (int i = 0; i < questions.size(); i++) {
				if (!studyQuizDao.checkStudyQuestion(m.getId(), questions
						.get(i)))
					studyQuizDao.insertStudyQuestion(m.getId(), questions
							.get(i));
				// else {
				// 修改答案（需要除去 email,offices,search,typing）
				// Question q = questionDao.getQbyId(questions.get(i).getId());
				// if (q.getQtype() < 8 || q.getQtype() == 11) {
				// studyQuizDao.updateStudyQuestion(m.getId(), questions
				// .get(i));
				// }
				// }
			}
		}
		try {
//			System.out.println(lastqids);
			if(lastqids!=null){
				String s[] = lastqids.split(",");
				if(s!=null)
				for (int i = 0; i < s.length; i++) {
					if(null!=s[i]&&!"".equals(s[i].trim()))
					{
						Question q =  getQuestionByIdinfo(s[i]);//questions.get(Integer.valueOf(s[i]));
						studyQuizDao.updateStudyQuestion(m.getId(),q);
					}
				}
			}
		} catch (Exception e) {
			logger.error("保存最后一题错误",e);
		}
		// 提交前判断是否达到可考次数了，没有的话 设状态为0，达到 的话 保持现状
		// myExamPaper.setStatus(0);
//		System.out.println("1:"+(System.currentTimeMillis()-l));
		studyQuizDao.submitQuizPaper(myExamPaper);// 调用存储过程设置学分
//		System.out.println("2:"+(System.currentTimeMillis()-l));
		// 设置考场及试卷状态
		studyQuizDao.setStudyEroomStatus(m.getExamRoom().getId(), userid);
		//更新考试记录状态和结束时间
		studyQuizDao.updateStudyQuizinfoRecordStatus(this.recordId, 0, new Timestamp(System.currentTimeMillis()), "study_quizinfo_record");
		//在学员考场表中记录该考生的最后开始考试时间，用于统计
//		studyQuizDao.updateStudyExamBegintime(userid, m.getExamRoom().getId(), new Timestamp(System.currentTimeMillis()));
		// 提交成功了，考试次数加1
		// studyQuizDao.setQuizPaperExamCount(myExamPaper.getId());
//		if (stuview == 1) {// 提交答卷后，允许当场显示答卷
//			return "myquizpaperview";
//		} else {
//			setElmessage("试卷提交成功！");
//			return "myquizpapergradeview";
//		}
		// }
		// setElmessage("试卷提交成功！");
		examPaper = studyQuizDao.getMyExamPaperInfo(myExamPaperid);
		myExamPaper= studyQuizDao.getMyEpById(myExamPaperid);
		float mepKscore = studyQuizDao.getMyExamPapermepKscore(myExamPaperid);
		examPaper.setMepKscore(mepKscore);
		examPaper.setMepZscore(myExamPaper.getMyScore()-mepKscore);
//		return "quizpaper_submit";
//		System.out.println("3:"+(System.currentTimeMillis()-l));
		
		
		roomid = m.getExamRoom().getId();
		//如果是章节的考场，则改变我的章节的信息
		eroomDao.updateMyCPage(userid,roomid);
		
		
		if(roomid == classificationDao.getRoomid()){//这个考试是定级考试
			MyRoom myroom = eroomDao.getMyRoom(roomid,userid);
			//可以不用判断是否已分配和是否已考试
			//获取用户考试的等级
			List<Classification> classifications = classificationDao.list_classification();
			String dingjiname = "";
			for(Classification ca:classifications){
				if(ca.getScorebegin()<=myroom.getMyScore() && ca.getScoreend()>=myroom.getMyScore()){
					dingjiname = ca.getName();
					break;
				}
			}
			if(dingjiname!=null && !dingjiname.equals("")){
				this.setElmessage("恭喜您，您当前的等级是" + dingjiname);
				classification = new Classification(dingjiname);
			}
		}
		
		return "quizpaper_submit_succ";
	}
	
	/**答卷提交
	 * @return
	 * @throws ElException
	 */
	public String quizpaper_submit_contest() throws ElException {
//		long l = System.currentTimeMillis();
		if (myExamPaper == null || myExamPaper.getId() == 0) {
			setElmessage("这个答卷不存在，请与管理员联系（重新分配考试）");
			return "error";
		}
//		int stuview = myExamPaper.getExamPaper().getStuview();
		int myExamPaperid = myExamPaper.getId();
		MyExamPaper m = studyQuizDao.getMyEpById(myExamPaperid);
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		if (m.getTester().getId() != userid) {
			setElmessage("这个答卷不是你的答卷，请从正常渠道进入！");
			return "error";
		}
		if (m.getStatus() == 2 || m.getStatus() == 3) {
			setElmessage("您的试卷已提交，请不要重复提交！");
			return "error";
		}
		if (null != questions) {
			for (int i = 0; i < questions.size(); i++) {
				if (!studyQuizDao.checkStudyQuestion(m.getId(), questions
						.get(i)))
					studyQuizDao.insertStudyQuestion(m.getId(), questions
							.get(i));
			}
		}
		try {
			if(lastqids!=null){
				String s[] = lastqids.split(",");
				if(s!=null)
				for (int i = 0; i < s.length; i++) {
					if(null!=s[i]&&!"".equals(s[i].trim()))
					{
						Question q =  getQuestionByIdinfo(s[i]);
						studyQuizDao.updateStudyQuestion(m.getId(),q);//试卷答题小题计算分值
					}
				}
			}
		} catch (Exception e) {
			logger.error("保存最后一题错误",e);
		}
		// 提交前判断是否达到可考次数了，没有的话 设状态为0，达到 的话 保持现状
		// myExamPaper.setStatus(0);
//		System.out.println("1:"+(System.currentTimeMillis()-l));
		studyQuizDao.submitQuizPaper(myExamPaper);// 调用存储过程设置学分
//		System.out.println("2:"+(System.currentTimeMillis()-l));
		// 设置考场及试卷状态
		studyQuizDao.setStudyEroomStatus(m.getExamRoom().getId(), userid);
		//更新考试记录状态和结束时间
		studyQuizDao.updateStudyQuizinfoRecordStatus(this.recordId, 0, new Timestamp(System.currentTimeMillis()), "study_quizinfo_record");
		//在学员考场表中记录该考生的最后开始考试时间，用于统计
//		studyQuizDao.updateStudyExamBegintime(userid, m.getExamRoom().getId(), new Timestamp(System.currentTimeMillis()));
		// 提交成功了，考试次数加1
		// studyQuizDao.setQuizPaperExamCount(myExamPaper.getId());
//		if (stuview == 1) {// 提交答卷后，允许当场显示答卷
//			return "myquizpaperview";
//		} else {
//			setElmessage("试卷提交成功！");
//			return "myquizpapergradeview";
//		}
		// }
		// setElmessage("试卷提交成功！");
		examPaper = studyQuizDao.getMyExamPaperInfo(myExamPaperid);
		myExamPaper= studyQuizDao.getMyEpById(myExamPaperid);
		float mepKscore = studyQuizDao.getMyExamPapermepKscore(myExamPaperid);
		examPaper.setMepKscore(mepKscore);
		examPaper.setMepZscore(myExamPaper.getMyScore()-mepKscore);
//		return "quizpaper_submit";
//		System.out.println("3:"+(System.currentTimeMillis()-l));
		
		
		roomid = m.getExamRoom().getId();
		//如果是章节的考场，则改变我的章节的信息
		eroomDao.updateMyCPage(userid,roomid);
		
		return "quizpaper_submit_succ";
	}
	private Question getQuestionByIdinfo(String idinfo){
		Question q = new Question(); 
		try {//nowbid+"-"+pnowsort+"-"+sortid
			String info[] = idinfo.split("-");
			HttpServletRequest req = getRequest();
			q.setId(Integer.valueOf(req.getParameter("questions_"+info[0]+"_"+info[1]+"_"+info[2]+"_id")));
			q.setEpblock(new ExamPaperBlock(Integer.valueOf(req.getParameter("questions_"+info[0]+"_"+info[1]+"_"+info[2]+"_epblock_id"))));
			q.setStuAnswers(req.getParameterValues("questions_"+info[0]+"_"+info[1]+"_"+info[2]+"_stuAnswers"));
			q.setOpstatus(1);
		} catch (Exception e) {
			logger.error("获取试题出错");
		}
		return q;
	}
	public String quizquestion_save() throws ElException {
		try {
			question.setStuAnswer(question.getStuAnswer()==null?"":URLDecoder.decode(question.getStuAnswer(),
					"UTF-8"));
			if(question.getQtype()==6){
				question.setStuAnswer(question.getStuAnswer().replaceAll("ahned", "&"));
				question.setStuAnswer(question.getStuAnswer().replaceAll("pjliuas", "+"));
			}
		} catch (Exception e) {
			logger.error("试题学习保存错误",e);
		}
//		if (!studyQuizDao.checkStudyQuestion(myExamPaper.getId(), question)&& !studyQuizDao.checkStudyQuestionSort(
//				myExamPaper.getId(), question))
//			studyQuizDao.insertStudyQuestion(myExamPaper.getId(), question);
//		else {
			// 修改答案（需要除去 email,offices,search,typing）
			Question q = questionDao.getQbyId(question.getId());
			if(q!=null){
				if (q.getQtype() < 8 || q.getQtype() == 11 || q.getQtype() == 15) {
					studyQuizDao.updateStudyQuestion(myExamPaper.getId(), question);
				} else {
					studyQuizDao.updateStudyQuestionOther(myExamPaper.getId(),
							question);
				}
			}
			printMsg("success");
//		}
		return null;
		// }
		// setElmessage("试卷提交成功！");
		// return "quizpaper_submit";
	}

	public String quizquestiondazi_save() throws ElException {

		// if (!studyQuizDao.checkStudyQuestion(myExamPaper.getId(), question))
		// studyQuizDao.insertStudyQuestion(myExamPaper.getId(), question);
		// else {
		// 修改答案（需要除去 email,offices,search,typing）
		// Question q = questionDao.getQbyId(question.getId());
		// if (q.getQtype() < 8 || q.getQtype() == 11) {
		question = question == null ? new Question() : question;
		try {
//			logger.error("question.stuAnswer"
//					+ getRequest().getParameter("question.stuAnswer"));
			question.setStuAnswer(getRequest().getParameter("stuanswer"));// new
			// String(getRequest().getParameter(
			// "question.stuAnswer").getBytes("ISO-8859-1"), "UTF-8")

		} catch (Exception e) {
			logger.error("獲取答案出錯", e);
		}

		studyQuizDao.updateStudyQuestion(myExamPaper.getId(), question);
		// }
		// }
		return null;
		// }
		// setElmessage("试卷提交成功！");
		// return "quizpaper_submit";
	}

	/**
	 * 
	 * 查看我的结业考试试卷。
	 * 
	 * @return
	 * @throws ElException
	 */
	public String myquizpaperview_wjm() throws ElException {
		int myExamPaperid = myExamPaper.getId();
		myExamPaper = studyQuizDao.getMyEpById(myExamPaperid);
		if(getSessionIntValue(ElConstants.SESSION_USERID)!=(myExamPaper!=null?(myExamPaper.getTester()!=null?myExamPaper.getTester().getId():0):0)){
			setElmessage("此答卷不是您的，您无权查看！");
			return "error";
		}
		// 判断状态，如果为考试中 不可查看答卷
		if (myExamPaper.getStatus() == 1) {
			setElmessage("正在考试，不可查看答卷！");
			return "error";
		}
		// 判断阅卷方式
		if (myExamPaper.getExamRoom().getMarkingManner() == 1
				&& myExamPaper.getStatus() != 3) {
			setElmessage("该试卷得批阅后才能查看答卷！");
			return "error";
		}
		// examPaper = examPaperDao.getExamPaperById(myExamPaper.getExamPaper()
		// .getId());
		// if (null == myExamPaper.getMyAnswer()
		// || "".equals(myExamPaper.getMyAnswer().trim())) {
		// examPaper = examPaperDao.getEPAllInfoById(examPaper.getId());
		// } else {
		// examPaper.setEpBlocks(new ArrayList<ExamPaperBlock>());
		// ELUser user = userDao
		// .getUserById(getSessionIntValue(ElConstants.SESSION_USERID));`
		// ExamPaperUtil.getAnswerExampaper(myExamPaper.getMyAnswer(),
		// examPaper, examPaperDao, questionDao, user.getShengri());
		// }
		
		examPaper = studyQuizDao.getMyExamPaper(myExamPaperid);
		examPaper.setUserage(getSessionIntValue(ElConstants.SESSION_AGE));
		float mepKscore = studyQuizDao.getMyExamPapermepKscore(myExamPaperid);
		examPaper.setMepKscore(mepKscore);
		examPaper.setMepZscore(myExamPaper.getMyScore()-mepKscore);
//		return "myquizpaperview";
		return "myquizpaper1b1view_wjm";
//		return "myquizpaperviewall";
	}

	public String quizquestioninit() throws ElException {// 1111111111111111111111111
		myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
		examPaper = studyQuizDao.getMyExamPaper(myExamPaper.getId());
		question = studyQuizDao.getQuestionByREBid(myExamPaper.getId(),
				question);
		if (question == null || 0 == question.getId()) {
			setElmessage("没找到你需要的试题。请确定该试题是否存在你相映场次的试卷中！");
			return "error";
		}
		if (question.getQtype() == 8) {
//			if (null != question && null != question.getStuAnswer()
//					&& !"".equals(question.getStuAnswer().trim())) {
			if(null != question &&question.getOpstatus()!=0){
				setElmessage("您所做答的打字题已提交，不容许重新作答！");
				return "qerror";
			}
			question.setRulestring(studyQuizDao.getQRulestrByREBid(myExamPaper
					.getId(), question));
			if (question.getRulestring() == null
					|| "".equals(question.getRulestring().trim())) {
				setElmessage("打字题评分规则未设定");
				return "error";
			}
			question.setAge(UserExcelUtil.getAgeBySfz(getSessionValue(ElConstants.SESSION_SHENFENZHENG)));///////
			return "quizquestion_dazi";
		} else if (question.getQtype() == 9) {
//			if (null != question && null != question.getStuAnswer()
//					&& !"".equals(question.getStuAnswer().trim())) {
			if(null != question &&question.getOpstatus()!=0){
				setElmessage("您所做答的邮件题已提交，不容许重新作答！");
				recordId = 1;
				return "qerror";
			}
			return "quizquestion_email";
		} else if (question.getQtype() == 10) {
//			if (null != question && null != question.getStuAnswer()
//					&& !"".equals(question.getStuAnswer().trim())) {
			if(null != question &&question.getOpstatus()!=0){
				setElmessage("您所做答的搜索题已提交，不容许重新作答！");
				return "qerror";
			}
			return "quizquestion_search";
		} else {
			setElmessage("除邮件题，打字题和搜索题可新开窗口作答外其他类型试题不能新开窗口作答");
			return "error";
		}
		// return "quizquestion";
	}

	/**
	 * var variables:URLVariables = new URLVariables(); variables.stuanswer
	 * =stuanswer;// "stuanswer"; _request.data = variables;
	 */
	public String quizquestion_submit() throws ElException {
		if (question == null) {
			setElmessage("试题不在了!请与管理员联系！");
			return "error";
		}
		if (question.getQtype() == 8) {
//			question = question == null ? new Question() : question;
			try {
				// question.setStuAnswer(new String(getRequest().getParameter(
				// "question.stuAnswer").getBytes("ISO-8859-1"), "UTF-8"));
				question.setStuAnswer(getRequest().getParameter("stuanswer"));

			} catch (Exception e) {
				logger.error("獲取答案出錯", e);
			}
//			Question q = studyQuizDao.getQuestionByREBid(myExamPaper.getId(),
//					question);
			// if (null == q.getStuAnswer() ||
			// "".equals(q.getStuAnswer().trim()))
			question.setOpstatus(1);
			studyQuizDao.updateStudyQuestion(myExamPaper.getId(), question);
			// else {
			// setElmessage("您所做答的打字题已提交，不容许重新作答！");
			// return "error";
			// }
			return "quizquestion_dazi_succ";
		}
		question.setOpstatus(1);
		studyQuizDao.updateStudyQuestion(myExamPaper.getId(), question);
		if (question.getQtype() == 9) {
			return "quizquestion_email_succ";
		} else {
			questionart = questionDao.getQart(questionart.getId());
			return "quizquestion_search_succ";
		}
	}

	public String quizquestion_email() throws ElException {
		myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
		examPaper = examPaperDao.getExamPaperById(myExamPaper.getExamPaper()
				.getId());
		question = studyQuizDao.getQuestionByREBid(myExamPaper.getId(),
				question);
		// questionart = questionart == null ? new QuestionArt() : questionart;
		// String title = questionart.getTitle() == null ? "" : questionart
		// .getTitle();
		// questionarts = questionDao
		// .listQarts(title, getPageNow(), getPageSize());
		// count = questionDao.listQartsSize(title);
		return "quizquestion_email";
	}

	public String quizquestion_search() throws ElException {
		myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
		examPaper = examPaperDao.getExamPaperById(myExamPaper.getExamPaper()
				.getId());
		question = studyQuizDao.getQuestionByREBid(myExamPaper.getId(),
				question);
		questionart = questionart == null ? new QuestionArt() : questionart;
		String title = questionart.getTitle() == null ? "" : questionart
				.getTitle();
		questionarts = questionDao
				.listQarts(title, getPageNow(), getPageSize());
		if (null != questionarts)
			for (int i = 0; i < questionarts.size(); i++) {
				String name = questionarts.get(i).getContent();
				if (null != name)
					questionarts.get(i).setContent(
							name.length() > 121 ? name.substring(0, 120)
									+ "..." : name);
			}
		count = questionDao.listQartsSize(title);
		return "quizquestion_search_list";
	}

	public String quiz_searchanswer() throws ElException {
		questionart = questionart == null ? new QuestionArt() : questionart;
		String title = questionart.getTitle() == null ? "" : questionart
				.getTitle();
		questionarts = questionDao
				.listQarts(title, getPageNow(), getPageSize());
		count = questionDao.listQartsSize(title);
		return "quiz_searchanswer";
	}

	public String quizpaper_save() throws ElException {
		// myExamPaper.setMyAnswer(ExamPaperUtil.getParamCombString(getRequest()));
		// studyQuizDao.saveQuizPaper(myExamPaper);
		// setElmessage("试卷保存成功！");
		return "quizpaper_save";
	}

	public String getquizstatus() throws ElException {
		try {
			studyQuizDao.saveQuizPaperPasstime(myExamPaper);
			myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
		} catch (Exception e) {
			logger.error("考生检测状态", e);
			message = "up_file_err";
		}
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.println("{\"myExamPaper\":{\"id\":\""
					+ myExamPaper.getId() + "\",\"jiashi\":\""
					+ myExamPaper.getJiashi() + "\",\"status\":\""
					+ myExamPaper.getStatus() + "\"}}");
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("获取试卷状态，保存时间错误",e);
		}
		// message = "xxxxxxdafd===";
		// return "success";
		return null;
	}

	private Examprac examqprac;

	public String examqprac() throws ElException {
		long starttime = System.currentTimeMillis();
		if (myExamPaper != null && myExamPaper.getId() > 0) {
			myExamPaper = studyQuizDao.getmyexamqpracbyid(myExamPaper.getId());
			// examPaper = studyQuizDao.getMyqpracPaper(myExamPaper.getId());
			// } else {
			// examqprac = eroomDao.getexamqprac(examqprac.getId());
			if (myExamPaper == null || myExamPaper.getId() <= 0
					|| myExamPaper.getExamPaper() == null
					|| myExamPaper.getExamPaper().getId() <= 0) {
				setElmessage("没找到相应的考前练习");
				return "error";
			}
			MyEprac m = new MyEprac();
			m.setTester(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			examqprac = new Examprac(myExamPaper.getId(), "");
			m.setPrac(examqprac);
			m.setStarttime(starttime);
			studyQuizDao.intomyexamqprac(m);
			// myExamPaper = studyQuizDao.getmyexamqprac(
			// getSessionIntValue(ElConstants.SESSION_USERID), examqprac
			// .getId(), starttime);
			examPaper = examPaperDao.getEPAllInfoById(myExamPaper
					.getExamPaper().getId());
			myExamPaper.setId(m.getId());
			if (null != examPaper && null != examPaper.getEpBlocks())
				for (int j = 0; j < examPaper.getEpBlocks().size(); j++) {
					ExamPaperBlock ebp = examPaper.getEpBlocks().get(j);
					if (null != ebp.getQuestions())
						for (int k = 0; k < ebp.getQuestions().size(); k++) {
							if (!studyQuizDao.checkqpracQuestion(m.getId(), ebp
									.getQuestions().get(k)))
								studyQuizDao.insertqpracQuestion(m.getId(), ebp
										.getQuestions().get(k));
						}
				}
		}
		//更改试卷状态 为不可编辑
		if(myExamPaper!=null&&myExamPaper.getExamPaper()!=null){
			examPaperDao.updateExampaperIseditor(myExamPaper.getExamPaper().getId());
		}
		return "examqprac1b1";
	}

	public String examqpracinto() throws ElException {
		long starttime = System.currentTimeMillis();
		// examqprac = eroomDao.getexamqprac(examqprac.getId());
		MyEprac m = new MyEprac();
		m.setTester(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		m.setPrac(examqprac);
		m.setStarttime(starttime);
		studyQuizDao.intomyexamqprac(m);
		myExamPaper = studyQuizDao.getmyexamqprac(
				getSessionIntValue(ElConstants.SESSION_USERID), examqprac
						.getId(), starttime);
		examPaper = examPaperDao.getEPAllInfoById(examqprac.getExamPaper()
				.getId());
		if (null != examPaper && null != examPaper.getEpBlocks())
			for (int j = 0; j < examPaper.getEpBlocks().size(); j++) {
				ExamPaperBlock ebp = examPaper.getEpBlocks().get(j);
				if (null != ebp.getQuestions())
					for (int k = 0; k < ebp.getQuestions().size(); k++) {
						Question qj = ebp.getQuestions().get(k);
						if (!studyQuizDao.checkqpracQuestion(myExamPaper
								.getId(), qj))
							studyQuizDao.insertqpracQuestion(myExamPaper
									.getId(), qj);
						if (qj.getQtype() == 7 && qj.getChilds() != null) {
							for (int i = 0; i < qj.getChilds().size(); i++) {
								qj.getChilds().get(i).setEpblock(
										qj.getEpblock());
								if (!studyQuizDao.checkqpracQuestion(
										myExamPaper.getId(), qj.getChilds()
												.get(i)))
									studyQuizDao.insertqpracQuestion(
											myExamPaper.getId(), qj.getChilds()
													.get(i));
							}
						}

					}
			}
		return "examqprac";
	}

	public String examqprac_submit() throws ElException {
		if (null != questions) {
			for (int i = 0; i < questions.size(); i++) {
				if (!studyQuizDao.checkqpracQuestion(myExamPaper.getId(),
						questions.get(i)))
					studyQuizDao.insertqpracQuestion(myExamPaper.getId(),
							questions.get(i));
				else {
					Question q = questionDao.getQbyId(questions.get(i).getId());
					if (q.getQtype() < 8 || q.getQtype() == 11)
						studyQuizDao.updateqpracQuestion(myExamPaper.getId(),
								questions.get(i));
				}
			}
		}
		studyQuizDao.submitqpracPaper(myExamPaper);
		// return "examqprac_result";
		return "examqprac_result1b1";
	}

	/**???????
	 * @return
	 * @throws ElException
	 */
	public String examqprac_view() throws ElException {
		examPaper = studyQuizDao.getMyqpracPaper(myExamPaper.getId());

		return "examqprac_view";
	}

	public String qpracquestioninit() throws ElException {
		question = studyQuizDao.getQuestionByqprac(myExamPaper.getId(),
				question);
		if (question == null || 0 == question.getId()) {
			setElmessage("没找到你需要的试题。请确定该试题是否存在你相映场次的试卷中！");
			return "error";
		}
		if (question.getQtype() == 8) {
			// if (null != question && null != question.getStuAnswer()
			// && !"".equals(question.getStuAnswer().trim())) {
			// setElmessage("您所做答的打字题已提交，不容许重新作答！");
			// return "error";
			// }
			question.setRulestring(studyQuizDao.getQRulestrByREBid(myExamPaper
					.getId(), question));
			return "qpracquestion_dazi";
		} else if (question.getQtype() == 9) {

			return "qpracquestion_email";
		} else if (question.getQtype() == 10) {

			return "qpracquestion_search";
		} else {
			setElmessage("除邮件题，打字题和搜索题可新开窗口作答外其他类型试题不能新开窗口作答");
			return "error";
		}
		// return "qpracquestion";
	}

	public String qpracquestion_email() throws ElException {
		question = studyQuizDao.getQuestionByqprac(myExamPaper.getId(),
				question);
		return "qpracquestion_email";
	}

	public String qpracquestion_search() throws ElException {
		question = studyQuizDao.getQuestionByqprac(myExamPaper.getId(),
				question);
		questionart = questionart == null ? new QuestionArt() : questionart;
		String title = questionart.getTitle() == null ? "" : questionart
				.getTitle();
		questionarts = questionDao
				.listQarts(title, getPageNow(), getPageSize());
		if (null != questionarts)
			for (int i = 0; i < questionarts.size(); i++) {
				String name = questionarts.get(i).getContent();
				if (null != name)
					questionarts.get(i).setContent(
							name.length() > 121 ? name.substring(0, 120)
									+ "..." : name);
			}
		count = questionDao.listQartsSize(title);
		return "qpracquestion_search_list";
	}

	public String qpracquestion_submit() throws ElException {
		//
		if (question == null) {
			setElmessage("试题不在了!");
			return "error";
		}
		// try {
		// question = question == null ? new Question() : question;
		// question.setStuAnswer(new String(getRequest().getParameter(
		// "question.stuAnswer").getBytes("ISO-8859-1"), "UTF-8"));
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		if (question.getQtype() == 8) {
			Question q = studyQuizDao.getQuestionByqprac(myExamPaper.getId(),
					question);
			question.setStuAnswer(getRequest().getParameter("stuanswer"));
			if (null == q.getStuAnswer() || "".equals(q.getStuAnswer().trim()))
				studyQuizDao.updateqpracQuestion(myExamPaper.getId(), question);
			else {
				setElmessage("您所做答的打字题已提交，不容许重新作答！");
				return "error";
			}
			return "qpracquestion_dazi_succ";
		}
		studyQuizDao.updateqpracQuestion(myExamPaper.getId(), question);
		if (question.getQtype() == 9) {
			return "qpracquestion_email_succ";
		} else {
			questionart = questionDao.getQart(questionart.getId());
			return "qpracquestion_search_succ";
		}
	}

	/**
	 * 我的结业考试成绩
	 * 
	 * @return
	 * @throws ElException
	 */
	public String myquiz_result() throws ElException {
		// myExamPapers = studyQuizDao.listMyQuiz2(
		// getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		// getPageSize());
		// myExamPapers = studyQuizDao.listMyQuiz3(
		// getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		// getPageSize());
		// count = studyQuizDao
		// .listMyQuizSize(getSessionIntValue(ElConstants.SESSION_USERID));
		myExamPapers = studyQuizDao.listMyQuiz4(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyQuizDao
				.listMyQuizSize2(getSessionIntValue(ElConstants.SESSION_USERID));
		return "myquiz_result";
	}

	public String listErsWithoutC() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myrooms = studyQuizDao.listErsWithoutC(
				getSessionIntValue(ElConstants.SESSION_USERID), 0,
				getPageNow(), getPageSize());
		// myrooms_xbs = studyQuizDao.listErsWithoutC(
		// getSessionIntValue(ElConstants.SESSION_USERID), 1,
		// getPageNow(), getPageSize());
		count = studyQuizDao.listErsWithoutCSize(
				getSessionIntValue(ElConstants.SESSION_USERID), 0);

		return "listErsWithoutC";
	}
	
	public String questionnaire_partakeList() throws ElException {
		myrooms = studyQuizDao.listQuesWithoutC(
				getSessionIntValue(ElConstants.SESSION_USERID), 0,
				getPageNow(), getPageSize());
		for(int i=0;i<myrooms.size();i++){
			examRoom = eroomDao.getExamRoomByid(myrooms.get(i).getExamroom().getId());
			myrooms.get(i).setMyExamPaper(studyQuizDao.listMypaperByRidanUid(getSessionIntValue(ElConstants.SESSION_USERID),examRoom.getId(), 0).get(0));
		}
		count = studyQuizDao.listQuesWithoutCSize(
				getSessionIntValue(ElConstants.SESSION_USERID), 0);

		return "questionnaire_partakeList";
	}
	
	//购买的考场
	public String listBuyErooms() throws ElException{
		myrooms = studyQuizDao.listBuyErooms(
				getSessionIntValue(ElConstants.SESSION_USERID), 0,
				getPageNow(), getPageSize());
		// myrooms_xbs = studyQuizDao.listErsWithoutC(
		// getSessionIntValue(ElConstants.SESSION_USERID), 1,
		// getPageNow(), getPageSize());
		count = studyQuizDao.listBuyEroomsSize(
				getSessionIntValue(ElConstants.SESSION_USERID), 0);
		return "listBuyErooms";
	}

	public String listErsWithoutC_xbs() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myrooms = studyQuizDao.listErsWithoutC(
				getSessionIntValue(ElConstants.SESSION_USERID), 1,
				getPageNow(), getPageSize());
		count = studyQuizDao.listErsWithoutCSize(
				getSessionIntValue(ElConstants.SESSION_USERID), 1);

		return "listErsWithoutC_xbs";
	}

	private MessageDao messageDao;

	// public String onloadUcenter() throws ElException { // hwc
	// setIsOnload(1);
	// // myrooms =
	// //
	// studyQuizDao.onloadUcenterStudy(getSessionIntValue(ElConstants.SESSION_USERID));
	// //
	// // myClasses = studyClassDao
	// // .OnloacUcenterMyclass(getSessionIntValue(ElConstants.SESSION_USERID));
	// // newMessage = messageDao
	// // .listMessNew(getSessionIntValue(ElConstants.SESSION_USERID));
	//
	// // myrooms =
	// //
	// studyQuizDao.onloadUcenterStudy(getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(),getPageSize());
	// //
	// count=studyQuizDao.onloadUcenterStudyCount(getSessionIntValue(ElConstants.SESSION_USERID));
	// // 学员登录显示 个人预览，超级管理员登入显示 待审核概览
	// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
	// int eroom_end = 0;// 待审核考场
	// int class_end = 0;// 待审核培训班
	// int news_end = 0;// 待审核新闻
	// eroom_end = studyQuizDao.getEroomEndCount();
	// getRequest().setAttribute("eroom_end", eroom_end);
	// class_end = studyClassDao.getClassEndCount();
	// getRequest().setAttribute("class_end", class_end);
	// news_end = new NewsDaoImpl().getNewsEndCount();
	// getRequest().setAttribute("news_end", news_end);
	// //
	// int message_no = 0;
	// message_no = messageDao
	// .getMessNoCount(getSessionIntValue(ElConstants.SESSION_USERID));
	// getRequest().setAttribute("message_no", message_no);
	// } else {
	// int message_no = 0;
	// int message_yes = 0;
	// int eroom_no = 0;
	// int eroom_all = 0;
	// int class_yes = 0;
	// int class_all = 0;
	// // 未读短消息条数，已读短消息条数
	// // 未读
	// message_no = messageDao
	// .getMessNoCount(getSessionIntValue(ElConstants.SESSION_USERID));
	// getRequest().setAttribute("message_no", message_no);
	// // 已读
	// message_yes = messageDao
	// .getMessYesCount(getSessionIntValue(ElConstants.SESSION_USERID));
	// getRequest().setAttribute("message_yes", message_yes);
	// // 未开始的考场，全部考场
	// eroom_no = studyQuizDao
	// .getEroomNoCount(getSessionIntValue(ElConstants.SESSION_USERID));
	// getRequest().setAttribute("eroom_no", eroom_no);
	// eroom_all = studyQuizDao
	// .getEroomAllCount(getSessionIntValue(ElConstants.SESSION_USERID));
	// getRequest().setAttribute("eroom_all", eroom_all);
	// // 已结业培训班，全部培训班
	// class_yes = studyClassDao
	// .getClassYesCount(getSessionIntValue(ElConstants.SESSION_USERID));
	// getRequest().setAttribute("class_yes", class_yes);
	// class_all = studyClassDao
	// .getClassAllCount(getSessionIntValue(ElConstants.SESSION_USERID));
	// getRequest().setAttribute("class_all", class_all);
	// }
	// // 判断 是否第1次登入
	// if (getSessionValue("isLogin") == null) {
	// }
	// if ("true".equals(getSessionValue("isLogin"))) {
	// getRequest().setAttribute("isLogin", 1);
	// getSession().removeAttribute("isLogin");// 销毁
	// // 获取该用户的弹窗信息
	// String popIds = messageDao
	// .getUserPopList(getSessionIntValue(ElConstants.SESSION_USERID));
	// getRequest().setAttribute("popIds", popIds);
	// // 调用存储过程来处理练习分配给的部门 自动分配给学员
	// studyQuizDao.study_depAssign(getSessionIntValue(ElConstants.SESSION_USERID),
	// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
	// } else {
	// //
	// getRequest().setAttribute("isLogin", 0);
	// }
	// return "onloadUcenter";
	// }

	public String onloadUcenter_kaoshi() throws ElException {
		// setIsOnload(1);
		myrooms = studyQuizDao.onloadUcenterStudy(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyQuizDao.onloadUcenterStudyCount(getSessionIntValue(ElConstants.SESSION_USERID));
		String jsons="";
		if(myrooms!=null)
		for (int i = 0; i < myrooms.size(); i++) {
			myroom = myrooms.get(i);
			jsons+="{'id':'"+myroom.getExamroom().getId()+"','title':'"+myroom.getExamroom().getTitle()+
			"','begintime':'"+myroom.getExamroom().getBegintimeFmt()+"','endtime':'"+myroom.getExamroom().getEndtimeFmt()+
			"','isApplication':'"+myroom.getExamroom().getIsApplication()+"','valid':'"+myroom.getExamroom().getValid()+
			"','mycount':'"+myroom.getMycount()+
			"','examcount':'"+myroom.getExamroom().getExamcount()+
			"','minstatus':'"+myroom.getMinstatus()+"'},";
		}
		if(jsons.length()>0)
			jsons= "["+jsons.substring(0,jsons.length()-1)+"]";
		else
			jsons="[]";
		printMsg("{'count':"+count+",'rooms':"+jsons+"}");
		//return "onloadUcenter_kaoshi";
		return null;
	}

	public String onloadUcenter_pxb() throws ElException {
		// setIsOnload(1);
		myClasses = studyClassDao.OnloacUcenterMyclass(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyClassDao
				.OnloacUcenterMyclassCount(getSessionIntValue(ElConstants.SESSION_USERID));
		String jsons="";
		if(myClasses!=null)
		for (int i = 0; i < myClasses.size(); i++) {
			MyClass myClass = myClasses.get(i);
			jsons+="{'id':'"+myClass.getElClass().getId()+"','title':'"+myClass.getElClass().getName()+
			"','begintime':'"+myClass.getElClass().getStarttimeFmt()+"','endtime':'"+myClass.getElClass().getFinishtimeFmt()+
			"','isApplication':'"+myClass.getElClass().getIsApplication()+"','status':'"+myClass.getElClass().getStatus()+"'},";
		}
		if(jsons.length()>0)
			jsons= "["+jsons.substring(0,jsons.length()-1)+"]";
		else
			jsons="[]";
		printMsg("{'count':"+count+",'myClasss':"+jsons+"}");
		//return "onloadUcenter_pxb";
		return null;
	}

	public String onloadUcenter_message() throws ElException {
		// setIsOnload(1);
		newMessage = messageDao.listMessNew(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		// count=messageDao.messToCount(getSessionIntValue(ElConstants.SESSION_USERID));
		count = messageDao
				.getMessNoCount(getSessionIntValue(ElConstants.SESSION_USERID));
		String jsons="";
		if(newMessage!=null)
		for (int i = 0; i < newMessage.size(); i++) {
			Message message = newMessage.get(i);
			jsons+="{'id':'"+message.getMess_id()+"','title':'"+message.getMess_title()+
			"','messtime':'"+message.getMess_timeFmt()+"'},";
		}
		if(jsons.length()>0)
			jsons= "["+jsons.substring(0,jsons.length()-1)+"]";
		else
			jsons="[]";
		printMsg("{'count':"+count+",'messages':"+jsons+"}");
		//return "onloadUcenter_message";
		return null;
	}
	//个人未审核
	public String onloadUcenter_gerenweishenhe() throws ElException{
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
		
		List<Map<String,Object>> listmap = null;
		Map<String,Object> map = null;
		listmap = scheduleGlobleDao.getNoPass(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),
				getPageNow(),getPageSize(),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN),department);
		count = scheduleGlobleDao.getNoPassSize(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN),department);
		String jsons="";
		if(listmap!=null)
		for (int i = 0; i < listmap.size(); i++) {
			map = listmap.get(i);
			jsons += "{'moduleName':'"+map.get("moduleName")+"'"+
			",'tablename':'"+map.get("tablename")+"','count':'"+map.get("count")+"'},";
		}
		if(jsons.length()>0)
			jsons= "["+jsons.substring(0,jsons.length()-1)+"]";
		else
			jsons="[]";
		printMsg("{'count':"+count+",'gerenweishenhe':"+jsons+"}");
		return null;
	}
	//个人待审核
	public String onloadUcenter_gerendaishenhe() throws ElException{
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
		
		List<Map<String,Object>> listmap = null;
		Map<String,Object> map = null;
		listmap = scheduleGlobleDao.getdaiPass(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),
				getPageNow(),getPageSize(),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENDAISHEN),department);
		count = scheduleGlobleDao.getdaiPassSize(getSessionIntValue(ElConstants.SESSION_ROLE),getSessionIntValue(ElConstants.SESSION_USERID),
				IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENDAISHEN),department);
		String jsons="";
		if(listmap!=null)
		for (int i = 0; i < listmap.size(); i++) {
			map = listmap.get(i);
			jsons += "{'moduleName':'"+map.get("moduleName")+"'"+
			",'tablename':'"+map.get("tablename")+"','count':'"+map.get("count")+"'},";
		}
		if(jsons.length()>0)
			jsons= "["+jsons.substring(0,jsons.length()-1)+"]";
		else
			jsons="[]";
		printMsg("{'count':"+count+",'gerendaishenhe':"+jsons+"}");
		return null;
	}
	
	public String onloadUcenter_course() throws ElException{
		myCourses = studyCourseDao.listMyAllCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyCourseDao
				.listMyAllCourseSize(getSessionIntValue(ElConstants.SESSION_USERID));
		String jsons="";
		if(myCourses!=null)
		for (int i = 0; i < myCourses.size(); i++) {
			MyCourse course = myCourses.get(i);
			jsons += "{'id':'"+course.getCourse().getId()+"','name':'"+course.getCourse().getName()+"'"+
			",'classIdName':'"+course.getCourse().getClassIdName()+"','classid':'"+course.getCourse().getClassid()+"'},";
//			Message message = newMessage.get(i);
//			jsons+="{'id':'"+message.getMess_id()+"','title':'"+message.getMess_title()+
//			"','messtime':'"+message.getMess_timeFmt()+"'},";
		}
		if(jsons.length()>0)
			jsons= "["+jsons.substring(0,jsons.length()-1)+"]";
		else
			jsons="[]";
		printMsg("{'count':"+count+",'myCourses':"+jsons+"}");
		//return "onloadUcenter_message";
		return null;
	}

	public String choose_class_results() throws ElException {
		elclasses = studyClassDao.registeredElclass(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyClassDao
				.registeredElclassSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "choose_class_results";
	}

	public String choose_course_results() throws ElException {
		courses = courseDao.registeredCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = courseDao
				.registeredCourseSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "choose_course_results";
	}

	public String quizpapwithoutC_result_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myrooms = studyQuizDao.listErsWithoutC_result(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		// count = studyQuizDao
		// .listErsWithoutCSize(getSessionIntValue(ElConstants.SESSION_USERID),0);
		count = studyQuizDao
				.listErsWithoutCSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "quizpapwithoutC_result_list";
	}

	public String quizpapwithoutC_result_list_detail() throws ElException {
		// myExamPapers = studyQuizDao.listErsWithoutC_result_detail(
		// getSessionIntValue(ElConstants.SESSION_USERID), examRoom
		// .getId());
		// myroom = studyQuizDao.getMyErsWithoutC(examRoom.getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		String iscommonStr = getRequest().getParameter("iscommon");
		int iscommon = -1;
		if (iscommonStr != null) {
			iscommon = Integer.parseInt(iscommonStr);
		}
		if (iscommon == 0) {
			myExamPapers = studyQuizDao.listErsWithoutC_result_detail(
					getSessionIntValue(ElConstants.SESSION_USERID), examRoom
							.getId(), iscommon);
			myroom = studyQuizDao.getMyErsWithoutC(examRoom.getId(),
					getSessionIntValue(ElConstants.SESSION_USERID), iscommon);
		} else {
			myExamPapers = studyQuizDao.listErsWithoutC_result_detail(
					getSessionIntValue(ElConstants.SESSION_USERID), examRoom
							.getId());
			myroom = studyQuizDao.getMyErsWithoutC(examRoom.getId(),
					getSessionIntValue(ElConstants.SESSION_USERID));
		}
		return "quizpapwithoutC_result_list_detail";
	}

	public String listcanapplyrooms() throws ElException {
		examRooms = studyQuizDao.listcanapplyrooms(1, getPageNow(),
				getPageSize());
		if (examRooms != null)
			for (int i = 0; i < examRooms.size(); i++) {
				examRooms
						.get(i)
						.setHasuser(
								eroomDao
										.checkuser2eroom(
												examRooms.get(i).getId(),
												getSessionIntValue(ElConstants.SESSION_USERID)));
			}
		count = studyQuizDao.listcanapplyroomsSize(1);

		return "listcanapplyrooms";
	}

	public String applyroomsinit() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		List<ExamPaper> examPapers = eroomDao.getEroomepwithusizes(examRoom
				.getId());
		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());
		if (examPapers == null || examPapers.size() == 0) {
			setElmessage("对不起该考场中没有试卷，暂不能申请，请与管理员联系！");
			return "error";
		}
		if (examPapers.size() > 0) {
			for (int i = 0; i < examPapers.size(); i++) {
				examPaper = examPapers.get(i);
				// if (!studyQuizDao.hasInQuizPaper(
				// getSessionIntValue(ElConstants.SESSION_USERID), examRoom
				// .getId(), examPaper.getId())) {
				// studyQuizDao.intoQuizPaper(
				// getSessionIntValue(ElConstants.SESSION_USERID),
				// examRoom.getId(), examPaper.getId());
				// }
				// if (!eroomDao.checkuser2eroom(examRoom.getId(),
				// getSessionIntValue(ElConstants.SESSION_USERID))) {
				// eroomDao.adduser2eroom(examRoom.getId(),
				// getSessionIntValue(ElConstants.SESSION_USERID),
				// StudyConstants.STUDY_EROOM_STATUS_VALIDING);
				// }
				// 检测学员是否分配到考场
				if (!eroomDao.checkuser2eroom(examRoom.getId(),
						getSessionIntValue(ElConstants.SESSION_USERID),
						examRoom.getClassid())) {
					eroomDao.adduser2eroom(examRoom.getId(),
							getSessionIntValue(ElConstants.SESSION_USERID), 1,
							examRoom.getClassid(),
							CourseConstants.EXAMROOM_SQFS_SQ);
				}
				// 判断试卷是否已被删除
				if (examPaper.getStatus() != 1) {
					// 检测该学员是否分配了该试卷
					if (!studyQuizDao.checkStudyExamPaper(
							getSessionIntValue(ElConstants.SESSION_USERID),
							examPaper.getId(), examRoom.getId(), examRoom
									.getClassid())) {
						// 添加该学员到 学员试卷表中
						studyQuizDao.addStudyExamPaper(
								getSessionIntValue(ElConstants.SESSION_USERID),
								examPaper.getId(), examRoom.getId(), examRoom
										.getClassid());
					}
				}
			}
			return "applyrooms_success";
		}
		return "applyrooms_epaper_list";
	}

	public String applyrooms_epaper_list() throws ElException {
		List<ExamPaper> examPapers = eroomDao.getEroomepwithusizes(examRoom
				.getId());
		if (null != examPapers)
			for (int i = 0; i < examPapers.size(); i++) {
				examPapers.get(i).setCourseHasEp(
						studyQuizDao.hasInQuizPaper(
								getSessionIntValue(ElConstants.SESSION_USERID),
								examRoom.getId(), examPapers.get(i).getId()));
			}
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examRoom.setExampapers(examPapers);
		return "applyrooms_epaper_list";
	}

	public String applyrooms_epaper_delete() throws ElException {
		studyQuizDao.deleteQuiz(getSessionIntValue(ElConstants.SESSION_USERID),
				examRoom.getId(), examPaper.getId());
		return "applyrooms_epaper_list";
	}

	public String applyrooms_epaper() throws ElException {
		if (!studyQuizDao.hasInQuizPaper(
				getSessionIntValue(ElConstants.SESSION_USERID), examRoom
						.getId(), examPaper.getId())) {
			studyQuizDao.intoQuizPaper(
					getSessionIntValue(ElConstants.SESSION_USERID), examRoom
							.getId(), examPaper.getId());
		}
		if (!eroomDao.checkuser2eroom(examRoom.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID))) {
			eroomDao.adduser2eroom(examRoom.getId(),
					getSessionIntValue(ElConstants.SESSION_USERID),
					StudyConstants.STUDY_EROOM_STATUS_VALIDING);
		}

		return "applyrooms_epaper_list";
	}

	public String myexamprac_list() throws ElException {
		// try {
		// SSLSocketFactory sf=new SSLSocketFactoryImpl();
		// SocketFactory sff=SSLSocketFactory.getDefault();
		// SSLSocket sss=(SSLSocket) sf.createSocket("192.168.9.150", 8080);
		// sss.getSession().invalidate();
		// //sf.createSocket().
		// SSLContext context = SSLContext.getInstance( "SSL");
		// Enumeration en= context.getClientSessionContext().getIds();
		// while (en.hasMoreElements()) {
		// Object elem = en.nextElement();
		// byte[Integer.parseInt(elem+"")]));
		// }
		// en= context.getServerSessionContext().getIds();
		// while (en.hasMoreElements()) {
		// Object elem = en.nextElement();
		// byte[Integer.parseInt(elem+"")]));
		// }
		//		 	
		// byte[getSession().getId()]));
		// } catch (NoSuchAlgorithmException e) {
		// // TODO Auto-generated catch block
		// } catch (UnknownHostException e) {
		// // TODO Auto-generated catch block
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// }
		myexampracs = studyQuizDao.listmyexamprac(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyQuizDao
				.listmyexampracsize(getSessionIntValue(ElConstants.SESSION_USERID));
		// 返回一个当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String now = sdf.format(new Date());
		getRequest().setAttribute("now", now);
		return "myexamprac_list";
	}

	/**一般练习
	 * @return
	 * @throws ElException
	 */
	public String examprac() throws ElException {
		long starttime = System.currentTimeMillis();
		if (myExamPaper != null && myExamPaper.getId() > 0) {
			myExamPaper = studyQuizDao.getmyexampracbyid(myExamPaper.getId());
			examPaper = studyQuizDao.getMyPracPaper(myExamPaper.getId());
		} else {
			examprac = eroomDao.getexamprac(examprac.getId());
			MyEprac m = new MyEprac();
			m.setTester(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			m.setPrac(examprac);
			m.setStarttime(starttime);
			studyQuizDao.intomyexamprac(m);
			myExamPaper = studyQuizDao.getmyexamprac(
					getSessionIntValue(ElConstants.SESSION_USERID), examprac
							.getId(), starttime);
			examPaper = examPaperDao.getEPAllInfoById(examprac.getExamPaper()
					.getId());
			if (null != examPaper && null != examPaper.getEpBlocks())
				for (int j = 0; j < examPaper.getEpBlocks().size(); j++) {
					ExamPaperBlock ebp = examPaper.getEpBlocks().get(j);
					if (null != ebp.getQuestions())
						for (int k = 0; k < ebp.getQuestions().size(); k++) {
							if (!studyQuizDao.checkPracQuestion(myExamPaper
									.getId(), ebp.getQuestions().get(k)))
								studyQuizDao.insertPracQuestion(myExamPaper
										.getId(), ebp.getQuestions().get(k));
						}
				}
		}

		return "examprac1b1";
	}

	public String exampracinto() throws ElException {
		long starttime = System.currentTimeMillis();
		examprac = eroomDao.getexamprac(examprac.getId());
		MyEprac m = new MyEprac();
		m.setTester(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		m.setPrac(examprac);
		m.setStarttime(starttime);
		studyQuizDao.intomyexamprac(m);

		myExamPaper = studyQuizDao.getmyexamprac(
				getSessionIntValue(ElConstants.SESSION_USERID), examprac
						.getId(), starttime);
		examPaper = examPaperDao.getEPAllInfoById(examprac.getExamPaper()
				.getId());
		if (null != examPaper && null != examPaper.getEpBlocks())
			for (int j = 0; j < examPaper.getEpBlocks().size(); j++) {
				ExamPaperBlock ebp = examPaper.getEpBlocks().get(j);
				if (null != ebp.getQuestions())
					for (int k = 0; k < ebp.getQuestions().size(); k++) {
						Question qj = ebp.getQuestions().get(k);
						if (!studyQuizDao.checkPracQuestion(
								myExamPaper.getId(), qj))
							studyQuizDao.insertPracQuestion(
									myExamPaper.getId(), qj);
						if (qj.getQtype() == 7 && qj.getChilds() != null) {
							for (int i = 0; i < qj.getChilds().size(); i++) {
								qj.getChilds().get(i).setEpblock(
										qj.getEpblock());
								if (!studyQuizDao.checkPracQuestion(myExamPaper
										.getId(), qj.getChilds().get(i)))
									studyQuizDao.insertPracQuestion(myExamPaper
											.getId(), qj.getChilds().get(i));
							}
						}

					}
			}
		return "examprac";
	}

	/**一般练习提交
	 * @return
	 * @throws ElException
	 */
	public String examprac_submit() throws ElException {
		if (null != questions) {
			for (int i = 0; i < questions.size(); i++) {
				// 检测该试卷该题目是否有练习过，有就更新，没有就添加
				if (!studyQuizDao.checkPracQuestion(myExamPaper.getId(),
						questions.get(i)))
					studyQuizDao.insertPracQuestion(myExamPaper.getId(),
							questions.get(i));
				else {
					Question q = questionDao.getQbyId(questions.get(i).getId());
					if (q.getQtype() < 8 || q.getQtype() == 11)
						studyQuizDao.updatePracQuestion(myExamPaper.getId(),
								questions.get(i));
				}
			}
		}
		studyQuizDao.submitPracPaper(myExamPaper);// 调用存储过程来设置得分
		// return "examprac_result";
		return "examprac_result1b1";
	}

	/**练习答卷查看
	 * @return
	 * @throws ElException
	 */
	public String examprac_view() throws ElException {
		examPaper = studyQuizDao.getMyPracPaper(myExamPaper.getId());
		myExamPaper = studyQuizDao.getmyexamprac(myExamPaper.getId());
		examPaper.setUserage(getSessionIntValue(ElConstants.SESSION_AGE));
//		return "examprac_view";
		return "examprac_viewall";
	}

	public String pracquestioninit() throws ElException {
		question = studyQuizDao
				.getQuestionByPrac(myExamPaper.getId(), question);
		if (question == null || 0 == question.getId()) {
			setElmessage("没找到你需要的试题。请确定该试题是否存在你相映场次的试卷中！");
			return "error";
		}
		if (question.getQtype() == 8) {
			if (null != question && null != question.getStuAnswer()
					&& !"".equals(question.getStuAnswer().trim())) {
				setElmessage("您所做答的打字题已提交，不容许重新作答！");
				return "qerror";
			}
			question.setRulestring(studyQuizDao.getQRulestrByREBid(myExamPaper
					.getId(), question));
			question.setAge(UserExcelUtil.getAgeBySfz(getSessionValue(ElConstants.SESSION_SHENFENZHENG)));///////
			return "pracquestion_dazi";
		} else if (question.getQtype() == 9) {
			if (null != question && null != question.getStuAnswer()
					&& !"".equals(question.getStuAnswer().trim())) {
				setElmessage("您所做答的邮件题已提交，不容许重新作答！");
				recordId = 1;
				return "qerror";
			}
			return "pracquestion_email";
		} else if (question.getQtype() == 10) {
			if (null != question && null != question.getStuAnswer()
					&& !"".equals(question.getStuAnswer().trim())) {
				setElmessage("您所做答的搜索题已提交，不容许重新作答！");
				return "qerror";
			}
			return "pracquestion_search";
		} else {
			setElmessage("除邮件题，打字题和搜索题可新开窗口作答外其他类型试题不能新开窗口作答");
			return "error";
		}
		// return "pracquestion";
	}

	public String pracquestion_email() throws ElException {
		question = studyQuizDao
				.getQuestionByPrac(myExamPaper.getId(), question);
		return "pracquestion_email";
	}

	public String pracquestion_search() throws ElException {
		question = studyQuizDao
				.getQuestionByPrac(myExamPaper.getId(), question);
		questionart = questionart == null ? new QuestionArt() : questionart;
		String title = questionart.getTitle() == null ? "" : questionart
				.getTitle();
		questionarts = questionDao
				.listQarts(title, getPageNow(), getPageSize());
		if (null != questionarts)
			for (int i = 0; i < questionarts.size(); i++) {
				String name = questionarts.get(i).getContent();
				if (null != name)
					questionarts.get(i).setContent(
							name.length() > 121 ? name.substring(0, 120)
									+ "..." : name);
			}
		count = questionDao.listQartsSize(title);
		return "pracquestion_search_list";
	}

	public String pracquestion_submit() throws ElException {
		//
		try {
			if (question == null) {
				setElmessage("试题不在了!");
				return "error";
			}
			// + new String(getRequest()
			// .getParameter("question.stuAnswer").getBytes(
			// "ISO-8859-1"), "UTF-8"));
//			question = question == null ? new Question() : question;
			// question.setAnswer(getRequest().getParameter("stuanswer")) ;
			// String(getRequest().getParameter("stuanswer").getBytes("ISO-8859-1"),"UTF-8"));
			if (question.getQtype() == 8) {
				// question.setStuAnswer(new String(getRequest().getParameter(
				// "question.stuAnswer").getBytes("ISO-8859-1"), "UTF-8"));
				question.setStuAnswer(getRequest().getParameter("stuanswer"));
				Question q = studyQuizDao.getQuestionByPrac(
						myExamPaper.getId(), question);
				// if (null == q.getStuAnswer() ||
				// "".equals(q.getStuAnswer().trim()))
				question.setStuAnswer(getRequest().getParameter("stuanswer"));
				studyQuizDao.updatePracQuestion(myExamPaper.getId(), question);
				// else {
				// setElmessage("您所做答的打字题已提交，不容许重新作答！");
				// return "error";
				// }
				return "pracquestion_dazi_succ";
			}
			studyQuizDao.updatePracQuestion(myExamPaper.getId(), question);
			if (question.getQtype() == 9) {
				return "pracquestion_email_succ";
			} else {
				questionart = questionDao.getQart(questionart.getId());
				return "pracquestion_search_succ";
			}
		} catch (Exception e) {
			logger.error("提交试题出错", e);
			setElmessage("提交试题出错！");
		}
		return "error";
	}

	public String examprac_detail_list() throws ElException {
		myeprac = studyQuizDao.getmyexamprac(
				getSessionIntValue(ElConstants.SESSION_USERID), examprac
						.getId());
		myExamPapers = studyQuizDao.listMpracExampapers(examprac.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		return "examprac_detail_list";
	}

	public String download_office_stuff() throws ElException {
		try {
			filename = filename.substring(filename.indexOf("/elstuffs"));

			String path = ServletActionContext.getServletContext().getRealPath(
					filename);
			filename = new String(filename.getBytes(), "ISO8859-1");
			try {
				inputStream = new FileInputStream(path);
			} catch (Exception e) {
				throw new ElException("下载资料出错", e);
			}
		} catch (Exception e) {
			setElmessage("文件不存在,请与管理员联系！");
			return "error";
		}
		return "download_office_stuff";
	}

	/*
	 * public String download_office_stuff() throws ElException { try {
	 * 
	 * filename = filename.substring(filename.indexOf("/files"));
	 * 
	 * String path = ServletActionContext.getServletContext().getRealPath(
	 * filename); filename = new String(filename.getBytes(), "ISO8859-1");
	 * FileInputStream(path); // byte[] b = new byte[4096]; if (null !=
	 * inputStream) { int size = inputStream.available() ;// / 4096 + 1; //
	 * inputStream.read(b);
	 * getResponse().getOutputStream().write(inputStream.read( )); }
	 * getResponse().setHeader("Content-disposition", "attachment; filename=" +
	 * filename); } else { setElmessage("文件不存在,请与管理员联系！"); return "error"; } }
	 * catch (Exception e) { throw new ElException("下载资料出错", e); } } catch
	 * (Exception e) { setElmessage("文件不存在,请与管理员联系！"); return "error"; } return
	 * null; }
	 */
	private String message;
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String upload_office_stuff() throws ElException {
		int offsize=SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_OFFICE_SIZE);
		try {
			// ServletActionContext.getResponse().setContentType(
			// "text/html;charset=utf-8");
			// PrintWriter out = ServletActionContext.getResponse().getWriter();
			if (null != st) {
				if (st.length() > offsize * 1024 * 1024) {//if (st.length() > 10 * 1024 * 1024) {
					// out.print("您上传的文件过大！");
					message = "up_file_toobig";
					// return "upload_office_stuff";
				} else {
					String ext = J2EEFileUtil.getExtention(stFileName);
					J2EEFileUtil.upload(st, ext, "/elstuffs/" + path + "/"
							+ getSessionIntValue(ElConstants.SESSION_USERID),
							filename);
					// out.print("文档上传成功！");
					message = "up_file_succ";
				}
			} else {
				// out.print("请输入上传文件");
				message = "up_file_input";
			}
			// out.flush();
			// out.close();

		} catch (Exception e) {
			logger.error("考生上传答案文件失败", e);
			message = "up_file_err";
		}
		// // return message;
		// return "success";
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			String qid = filename.substring(filename.lastIndexOf("_") + 1,
					filename.length());
			localPrintWriter.println("{\"message\":\"" + message
					+ "\",\"qid\":\"" + qid + "\",\"offsize\":\""+offsize+"\"}");
			// localPrintWriter.println("{\"qid\":\"" + qid + "\"}");
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("考试材料题文件上传错误",e);
		}
		return null;
	}

	// 模考管理
	public String simpaperlist() throws ElException {
		course = courseDao.getCourseById(course.getId());
		simExamPapers = studyQuizDao.listMySimEp(course.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID));
		exampapers = new ArrayList<ExamPaper>();
		for (int i = 0; i < simExamPapers.size(); i++) {
			exampapers.add(examPaperDao.getExamPaperById(simExamPapers.get(i)
					.getExamPaper().getId()));
		}
		return "simpaperlist";
	}

	public String simpaper() throws ElException {/*
													 * course =
													 * courseDao.getCourseById(course.getId());
													 * if
													 * (studyQuizDao.checkSimPaper(
													 * getSessionIntValue(ElConstants.SESSION_USERID),
													 * examPaper .getId(), 2,
													 * course.getId())) {
													 * setElmessage("您的试卷已提交！请等候成绩！");
													 * return "error"; } if
													 * (studyQuizDao.checkSimPaper(
													 * getSessionIntValue(ElConstants.SESSION_USERID),
													 * examPaper .getId(), 3,
													 * course.getId())) {
													 * studyQuizDao.resimpaper(
													 * getSessionIntValue(ElConstants.SESSION_USERID),
													 * course .getId(),
													 * examPaper.getId()); } if
													 * (!studyQuizDao.hasInSimPaper(
													 * getSessionIntValue(ElConstants.SESSION_USERID),
													 * examPaper .getId(),
													 * course.getId())) {
													 * studyQuizDao.intoSimPaper(
													 * getSessionIntValue(ElConstants.SESSION_USERID),
													 * examPaper .getId(),
													 * course.getId());
													 * examPaper =
													 * examPaperDao.getEPAllInfoById(examPaper.getId());
													 * myExamPaper = new
													 * MyExamPaper();
													 * ScoreOperate.setScore(
													 * getSessionIntValue(ElConstants.SESSION_USERID),
													 * ElConstants.SCORE_SIMP_DO); }
													 * else { myExamPaper =
													 * studyQuizDao.getMySimEpByUandR(
													 * getSessionIntValue(ElConstants.SESSION_USERID),
													 * examPaper .getId(),
													 * course.getId());
													 * examPaper =
													 * examPaperDao.getExamPaperById(myExamPaper
													 * .getExamPaper().getId()); //
													 * if(myExamPaper.getMyAnswer()==null||"".equals(myExamPaper.getMyAnswer().trim())) //
													 * examPaper =
													 * examPaperDao.getEPAllInfoById(examPaper.getId()); //
													 * myExamPaper.getExamPaper();
													 * 
													 * if (null ==
													 * myExamPaper.getMyAnswer() ||
													 * "".equals(myExamPaper.getMyAnswer().trim())) {
													 * examPaper =
													 * examPaperDao.getEPAllInfoById(examPaper.getId()); }
													 * else {
													 * examPaper.setEpBlocks(new
													 * ArrayList<ExamPaperBlock>());
													 * ELUser user = userDao
													 * .getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
													 * ExamPaperUtil
													 * .getAnswerExampaper(myExamPaper.getMyAnswer(),
													 * examPaper, examPaperDao,
													 * questionDao, user
													 * .getShengri()); }
													 * 
													 * myExamPaper.setTester(new
													 * ELUser(
													 * getSessionIntValue(ElConstants.SESSION_USERID)));
													 * myExamPaper.setCourse(course);
													 * myExamPaper.setExamPaper(examPaper); //
													 * studyQuizDao.submitSimPaper(myExamPaper); //
													 * setElmessage("已提交试卷"); //
													 * return "error"; }
													 */
		return "simpaper";
	}

	/**
	 * 模考提交
	 * 
	 * @return
	 * @throws ElException
	 */
	public String simpaper_submit() throws ElException {
		/*
		 * myExamPaper.setExamPaper(examPaper); myExamPaper.setTester(new
		 * ELUser( getSessionIntValue(ElConstants.SESSION_USERID)));
		 * myExamPaper.setMyAnswer(ExamPaperUtil.getParamCombString(getRequest()));
		 * studyQuizDao.submitSimPaper(myExamPaper); // if
		 * (SystemConfOp.getBooleanValue(ElConstants.QUIZ_NEED_SH)) { // TODO
		 * 模考自动阅卷呢 examPaper =
		 * examPaperDao.getExamPaperById(myExamPaper.getId());
		 * examPaper.setEpBlocks(new ArrayList<ExamPaperBlock>()); ELUser user =
		 * userDao .getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		 * ExamPaperUtil.getAnswerExampaper(myExamPaper.getMyAnswer(),
		 * examPaper, examPaperDao, questionDao, user.getShengri());
		 * studyQuizDao.setSimFinalScore(myExamPaper.getCourse().getId(),
		 * myExamPaper.getExamPaper().getId(),
		 * getSessionIntValue(ElConstants.SESSION_USERID), examPaper
		 * .getMep_tscore());
		 */
		return "mysimpaperview";
		// }
		// setElmessage("试卷提交成功！");
		// return "simpaper_submit";
	}

	/**
	 * 模考试卷保存
	 * 
	 * @return
	 * @throws ElException
	 */
	public String simpaper_save() throws ElException {
		/*
		 * myExamPaper.setExamPaper(examPaper); myExamPaper.setTester(new
		 * ELUser( getSessionIntValue(ElConstants.SESSION_USERID)));
		 * myExamPaper.setMyAnswer(ExamPaperUtil.getParamCombString(getRequest()));
		 * studyQuizDao.saveSimPaper(myExamPaper); setElmessage("试卷保存成功！");
		 */
		return "simpaper_save";
	}

	// 成绩
	/**
	 * 模考成绩列表
	 */
	public String mysimexam_result() throws ElException {
		myExamPapers = studyQuizDao
				.listSimResult(getSessionIntValue(ElConstants.SESSION_USERID));
		// TODO 模拟考
		return "mysimexam_result";
	}

	/**
	 * 模考答卷查看
	 * 
	 * @return
	 * @throws ElException
	 */
	public String mysimpaperview() throws ElException {
		/*
		 * myExamPaper = studyQuizDao.getMySimEpByUandR(
		 * getSessionIntValue(ElConstants.SESSION_USERID), examPaper .getId(),
		 * course.getId()); examPaper =
		 * examPaperDao.getExamPaperById(examPaper.getId()); if (null ==
		 * myExamPaper.getMyAnswer() ||
		 * "".equals(myExamPaper.getMyAnswer().trim())) { examPaper =
		 * examPaperDao.getEPAllInfoById(examPaper.getId()); } else {
		 * examPaper.setEpBlocks(new ArrayList<ExamPaperBlock>()); ELUser user =
		 * userDao .getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		 * ExamPaperUtil.getAnswerExampaper(myExamPaper.getMyAnswer(),
		 * examPaper, examPaperDao, questionDao, user.getShengri()); }
		 */
		return "mysimpaperview";
	}

	public ExamPaperDao getExamPaperDao() {
		return examPaperDao;
	}

	public void setExamPaperDao(ExamPaperDao examPaperDao) {
		this.examPaperDao = examPaperDao;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public FrontDao getFrontDao() {
		return frontDao;
	}

	public void setFrontDao(FrontDao frontDao) {
		this.frontDao = frontDao;
	}

	public MyPractice getMyPractice() {
		return myPractice;
	}

	public void setMyPractice(MyPractice myPractice) {
		this.myPractice = myPractice;
	}

	public List<SimexamPaper> getSimExamPapers() {
		return simExamPapers;
	}

	public void setSimExamPapers(List<SimexamPaper> simExamPapers) {
		this.simExamPapers = simExamPapers;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public List<MyCourse> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}

	public StudyCourseDao getStudyCourseDao() {
		return studyCourseDao;
	}

	public void setStudyCourseDao(StudyCourseDao studyCourseDao) {
		this.studyCourseDao = studyCourseDao;
	}

	public List<MyRoom> getMyrooms() {
		return myrooms;
	}

	public void setMyrooms(List<MyRoom> myrooms) {
		this.myrooms = myrooms;
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

	public String getSfContentType() {
		return sfContentType;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public List<MyEprac> getMyexampracs() {
		return myexampracs;
	}

	public void setMyexampracs(List<MyEprac> myexampracs) {
		this.myexampracs = myexampracs;
	}

	public void setSfContentType(String sfContentType) {
		this.sfContentType = sfContentType;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}

	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}

	public List<Message> getNewMessage() {
		return newMessage;
	}

	public void setNewMessage(List<Message> newMessage) {
		this.newMessage = newMessage;
	}

	public StatisticDao getStatisticDao() {
		return statisticDao;
	}

	public void setStatisticDao(StatisticDao statisticDao) {
		this.statisticDao = statisticDao;
	}

	public List<MyExamPaper> getMyExamPapers() {
		return myExamPapers;
	}

	public void setMyExamPapers(List<MyExamPaper> myExamPapers) {
		this.myExamPapers = myExamPapers;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public List<MyCPage> getMyCPages() {
		return myCPages;
	}

	public void setMyCPages(List<MyCPage> myCPages) {
		this.myCPages = myCPages;
	}

	public List<ExamPaper> getExampapers() {
		return exampapers;
	}

	public void setExampapers(List<ExamPaper> exampapers) {
		this.exampapers = exampapers;
	}

	public Examprac getExamprac() {
		return examprac;
	}

	public void setExamprac(Examprac examprac) {
		this.examprac = examprac;
	}

	public List<QuestionArt> getQuestionarts() {
		return questionarts;
	}

	public void setQuestionarts(List<QuestionArt> questionarts) {
		this.questionarts = questionarts;
	}

	public QuestionArt getQuestionart() {
		return questionart;
	}

	public void setQuestionart(QuestionArt questionart) {
		this.questionart = questionart;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public MyRoom getMyroom() {
		return myroom;
	}

	public void setMyroom(MyRoom myroom) {
		this.myroom = myroom;
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

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public MyEprac getMyeprac() {
		return myeprac;
	}

	public void setMyeprac(MyEprac myeprac) {
		this.myeprac = myeprac;
	}

	public Examprac getExamqprac() {
		return examqprac;
	}

	public void setExamqprac(Examprac examqprac) {
		this.examqprac = examqprac;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public List<MyRoom> getMyrooms_xbs() {
		return myrooms_xbs;
	}

	public void setMyrooms_xbs(List<MyRoom> myrooms_xbs) {
		this.myrooms_xbs = myrooms_xbs;
	}

	public List<MyExamPaper> getMyExamPapers_xbs() {
		return myExamPapers_xbs;
	}

	public void setMyExamPapers_xbs(List<MyExamPaper> myExamPapers_xbs) {
		this.myExamPapers_xbs = myExamPapers_xbs;
	}

	public List<ElClass> getElclassesnot() {
		return elclassesnot;
	}

	public void setElclassesnot(List<ElClass> elclassesnot) {
		this.elclassesnot = elclassesnot;
	}

	/**
	 * 学员申请培训班级列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String exam_listbytitle() throws ElException {
		eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		int libid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
				: eroomLib.getId();
		// 初始化类别
		if (eroomLib == null) {
			eroomLib = new EroomLib(1);
		}
		if (examRoom != null && examRoom.getTitle() != null
				&& examRoom.getTitle().equals("填写考场名称....")) {
			examRoom.setTitle("");
		}
		examRooms = eroomDao.getApplyForeEroom(eroomLibTree, libid, examRoom,
				getSessionIntValue(ElConstants.SESSION_ROLE), "", getPageNow(),
				getPageSize());
		count = eroomDao.getApplyForeEroomSize(eroomLibTree, libid, examRoom,
				getSessionIntValue(ElConstants.SESSION_ROLE), "");
		for (int i = 0;  i< examRooms.size() ; i++) {
//			examRooms.get(i).getErRegistration().setJoinNumber(
//					eroomDao.getJoinNumber(examRooms.get(i).getId()) + "");
//			if (eroomDao.checkuser2eroom(examRooms.get(i).getId(),
//					getSessionIntValue(ElConstants.SESSION_USERID), -1)) {// 是否已报名//-1单纯的考场
//				examRooms.get(i).setIsjoin("true");
//			} else {
//				examRooms.get(i).setIsjoin("false");
//			}
			if(examRooms.get(i).getErRegistration().getIsAudit()==1){
				if (studyQuizDao.checkStudyRoomApply(examRooms.get(i).getId(),
						getSessionIntValue(ElConstants.SESSION_USERID))) {
					examRooms.get(i).setIsjoin("true");
				} else {
					examRooms.get(i).setIsjoin("false");
				}
			}else{
				examRooms.get(i).getErRegistration().setJoinNumber(
						eroomDao.getJoinNumber(examRooms.get(i).getId())
								);
				if (eroomDao.checkuser2eroom(examRooms.get(i).getId(),
						getSessionIntValue(ElConstants.SESSION_USERID), -1)) {// 是否已报名//-1单纯的考场
					examRooms.get(i).setIsjoin("true");
				} else {
					examRooms.get(i).setIsjoin("false");
				}
			}
			if(examRooms.get(i)!=null){
				String x =StringUtil.shortStr(examRooms.get(i).getDescription(), 80, "...");
				examRooms.get(i).setDescription(x==null||"".equals(x)?"无说明...":x);
			}
			if (checkIsuserApp(examRooms.get(i), elUser)) {// 如果返回false证明有某条不符合条件
				examRooms.get(i).setIsuserApp(1);
			} else {
				examRooms.get(i).setIsuserApp(2);
				if (isCorrespond == 1) {
					examRooms.remove(i);
					count = count - 1;
					i--;
				}
			}
		}
		// 最新通知公告
		this.zxtzggs = this.frontDao.listZxNews(8, 1);
		// 最新推荐通知公告
		this.tjtzggs = this.frontDao.listZxNews(8, 1, 1);
//		return "exam_listbytitle";
		getRequest().setAttribute("isAll","yes");
		return "exam_listbytitle_isPass";
	}

	/**
	 * 学员申请培训班级列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String exam_listbytitle_isPass() throws ElException {
		eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		int libid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
				: eroomLib.getId();
		// 初始化类别
		if (eroomLib == null) {
			eroomLib = new EroomLib(1);
		}
		if (examRoom != null && examRoom.getTitle() != null
				&& examRoom.getTitle().equals("填写考场名称....")) {
			examRoom.setTitle("");
		}
		examRooms = eroomDao
				.getApplyForeEroom(
						eroomLibTree,
						libid,
						examRoom,
						getSessionIntValue(ElConstants.SESSION_ROLE),
						" and elr.registrationStartTime < sysdate and elr.registrationStopTime > sysdate ",
						999999, 1);
		if (examRooms.size() != 0) {
			String eroomids = "";
			for (int i = 0; examRooms.size() > i; i++) {
				if (checkIsuserApp(examRooms.get(i), elUser)) {// 如果返回false证明有某条不符合条件
					if (eroomids.equals(""))
						eroomids = eroomids + examRooms.get(i).getId();
					else
						eroomids = eroomids + "," + examRooms.get(i).getId();
				}
			}
			if (!eroomids.equals("")) {
				examRooms = eroomDao.getApplyForeEroom(eroomLibTree, libid,
						examRoom, getSessionIntValue(ElConstants.SESSION_ROLE),
						" and er.id in (" + eroomids + ")", getPageNow(),
						getPageSize());
				count = eroomDao.getApplyForeEroomSize(eroomLibTree, libid,
						examRoom, getSessionIntValue(ElConstants.SESSION_ROLE),
						" and er.id in (" + eroomids + ")");

				for (int i = 0; examRooms.size() > i; i++) {
//					examRooms.get(i).getErRegistration().setJoinNumber(
//							eroomDao.getJoinNumber(examRooms.get(i).getId())
//									+ "");
//					if (eroomDao.checkuser2eroom(examRooms.get(i).getId(),
//							getSessionIntValue(ElConstants.SESSION_USERID), -1)) {// 是否已报名//-1单纯的考场
//						examRooms.get(i).setIsjoin("true");
//					} else {
//						examRooms.get(i).setIsjoin("false");
//					}
					if(examRooms.get(i).getErRegistration().getIsAudit()==1){
						if (studyQuizDao.checkStudyRoomApply(examRooms.get(i).getId(),
								getSessionIntValue(ElConstants.SESSION_USERID))) {
							examRooms.get(i).setIsjoin("true");
						} else {
							//examRooms.get(i).setIsjoin("false");
							//检测是否后台有分配
							if (eroomDao.checkuser2eroom(examRooms.get(i).getId(),
									getSessionIntValue(ElConstants.SESSION_USERID), -1)) {// 是否已报名//-1单纯的考场
								examRooms.get(i).setIsjoin("true_assign");
							} else {
								examRooms.get(i).setIsjoin("false");
							}
						}
					}else{
						examRooms.get(i).getErRegistration().setJoinNumber(
								eroomDao.getJoinNumber(examRooms.get(i).getId())
										);
						if (eroomDao.checkuser2eroom(examRooms.get(i).getId(),
								getSessionIntValue(ElConstants.SESSION_USERID), -1)) {// 是否已报名//-1单纯的考场
							examRooms.get(i).setIsjoin("true");
						} else {
							examRooms.get(i).setIsjoin("false");
						}
					}
					examRooms.get(i).setIsuserApp(1);
					String x =StringUtil.shortStr(examRooms.get(i).getDescription(), 80, "...");
					examRooms.get(i).setDescription(x==null||"".equals(x)?"无说明...":x);
				}
			} else {
				examRooms = null;
				count = 0;
			}
		} else {
			examRooms = null;
			count = 0;
		}
		if("ajax".equals(Return)){
			printMsg("{'count':"+count+"}");
			return null;
		}
		// 最新通知公告
		this.zxtzggs = this.frontDao.listZxNews(8, 1);
		// 最新推荐通知公告
		this.tjtzggs = this.frontDao.listZxNews(8, 1, 1);
		return "exam_listbytitle_isPass";
	}

	/**
	 * 学员申请考场列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String exam_view() throws ElException {
		eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		examRoom = eroomDao.getApplyForeEroomById(examRoom.getId());
		if (examRoom.getErRegistration() == null) {
			setElmessage("考场类型以改变为分配式，不能进入申请式查看！");
			return "error";
		}
		examRoom.getErRegistration().setJoinNumber(
				eroomDao.getJoinNumber(examRoom.getId()));
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		if (checkIsuserApp(examRoom, elUser)) {// 如果返回false证明有某条不符合条件
			examRoom.setIsuserApp(1);
		} else {
			examRoom.setIsuserApp(2);
			examRoom.setExplain(explain.toString());// 不通过说明
		}
		if(examRoom.getErRegistration().getIsAudit()==1){
			//设置已报名人数
			examRoom.getErRegistration().setApplyNumber(eroomDao.getStudyApplyCount(examRoom.getId()));
			if (studyQuizDao.checkStudyRoomApply(examRoom.getId(),
					getSessionIntValue(ElConstants.SESSION_USERID))) {
				examRoom.setIsjoin("true");
			} else {
				//examRoom.setIsjoin("false");
				if (eroomDao.checkuser2eroom(examRoom.getId(),
						getSessionIntValue(ElConstants.SESSION_USERID), -1)) {// 是否已报名//-1单纯的考场
					examRoom.setIsjoin("true_assign");
				} else {
					examRoom.setIsjoin("false");
				}
			}
		}else{
			//设置已报名人数
			examRoom.getErRegistration().setApplyNumber(examRoom.getErRegistration().getJoinNumber());
			if (eroomDao.checkuser2eroom(examRoom.getId(),
					getSessionIntValue(ElConstants.SESSION_USERID), -1)) {// 是否已报名//-1单纯的考场
				examRoom.setIsjoin("true");
			} else {
				examRoom.setIsjoin("false");
			}
		}
		if(examRoom.getErRegistration().getIsselectep()==1){
			examRoom.setExampapers(eroomDao.getEroomEps(examRoom.getId()));
		}
		// 最新通知公告
		this.zxtzggs = this.frontDao.listZxNews(8, 1);
		// 最新推荐通知公告
		this.tjtzggs = this.frontDao.listZxNews(8, 1, 1);
		return "exam_view";
	}

	/**
	 * 添加申请加入考场学员
	 * 
	 * @return
	 * @throws ElException
	 */
	public String submitAppalyExamRoom() throws ElException {
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		//查出该考场相关信息
		examRoom=eroomDao.getApplyForeEroomById(examRoom.getId());
		//examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		if(examRoom.getErRegistration().getIsselectep()==0){
			examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		}
		if (examPapers == null || examPapers.size() == 0) {
			setElmessage("考场未安排试卷，不能报名该考场！请与管理员联系！");
			return "error";
		}
		if(examRoom.getErRegistration().getIsAudit()==0){//不需要审核
			for (int i = 0; i < examPapers.size(); i++) {
				// if (!studyQuizDao.hasInQuizPaper(userid, examRoom.getId(),
				// examPapers.get(i).getId(), -1)) {// 检测是否已经进入考场
				// studyQuizDao.intoQuizPaper(userid,
				// examRoom.getId(),examPapers.get(i)
				// .getId(), -1);// 添加study_quizinfo信息（考试信息）
				// // 把考场id摄入class_course表
				// }
				// 判断试卷是否已被删除
				if (examPapers.get(i).getStatus() != 1) {
					// 检测该学员是否分配了该试卷
					if (!studyQuizDao.checkStudyExamPaper(userid, examPapers.get(i)
							.getId(), examRoom.getId(), -1)) {
						// 添加该学员到 学员试卷表中
						studyQuizDao.addStudyExamPaper(userid, examPapers.get(i)
								.getId(), examRoom.getId(), -1);
					}
				}
			}
			if (!eroomDao.checkuser2eroom(examRoom.getId(), userid, -1)) {// -1为考核考试
				eroomDao.adduser2eroom(examRoom.getId(), userid, 0, -1,
						CourseConstants.EXAMROOM_SQFS_SQ);
			}
		}else{
			if (!studyQuizDao.checkStudyRoomApply(examRoom.getId(), userid)) {
				//先添加到报名记录表
				studyQuizDao.addStudyRoomApply(examRoom.getId(), userid);
			}
			if(examRoom.getErRegistration().getIsselectep()==1){
				//自主选择试卷，先把试卷存到学员试卷表中 ，状态为删除，等开通的时候更新状态
				for (int i = 0; i < examPapers.size(); i++) {
					// 检测该学员是否分配了该试卷
					if (!studyQuizDao.checkStudyExamPaper(userid, examPapers.get(i)
							.getId(), examRoom.getId(), -1)) {
						// 添加该学员到 学员试卷表中
						studyQuizDao.addStudyExamPaper(userid, examPapers.get(i)
								.getId(), examRoom.getId(), -1,1);
					}
				}
			}
		}
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		return Return;
	}

	/**
	 * 选考场结果
	 * 
	 * @return
	 * @throws ElException
	 */
	public String choose_exam_results() throws ElException {
		examRooms = studyClassDao.registeredEroom(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
//		for (int i = 0; i < examRooms.size(); i++) {
//			if (!eroomDao.checkuser2eroom(examRooms.get(i).getId(),
//					getSessionIntValue(ElConstants.SESSION_USERID), -1)) {// 是否已报名//-1单纯的考场
//				examRooms.get(i).setIsjoin("false");
//			}
//		}
		count = studyClassDao
				.registeredEroomSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "choose_exam_results";
	}

	/**
	 * 培训班是否满足申请要求
	 * 
	 * @author
	 * @return
	 * @throws ElException
	 */
	public boolean checkIsuserApp(ExamRoom eroom, ELUser eluser)
			throws ElException {
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
		boolean eroomeps = true;
		boolean elclass = true;
		explain = new StringBuffer();
		if (eroom.getErRegistration().getDslist() == null) {// 地市不限
			ds = true;
		} else {
			if (eroom.getErRegistration().getDslist() != null
					&& elUser.getDishi() > 0
					&& eroom.getErRegistration().getDslist().contains(
							elUser.getDishi() + "")) {
				ds = true;// dslist不为空 uds不为空 dslist 里没有该地市
			} else {
				explain.append("地市 ");
				dishiIspass = 1;
				sumIspass = -1;
				ds = false;// dslist不为空 uds为空 或者 dslist 里没有该地市
			}
		}
		if (eroom.getErRegistration().getJzlist() == null) {
			jz = true;// 不限
		} else {
			if (eroom.getErRegistration().getJzlist() != null
					&& elUser.getJingzhong() > 0
					&& eroom.getErRegistration().getJzlist().contains(
							elUser.getJingzhong() + "")) {
				jz = true;
			} else {
				explain.append("警种 ");
				jingzhongIspass = 1;
				sumIspass = -1;
				jz = false;
			}
		}
		if (eroom.getErRegistration().getZjlist() == null) {
			zj = true;// 不限
		} else {
			if (eroom.getErRegistration().getZjlist() != null
					&& elUser.getZhiji() > 0
					&& eroom.getErRegistration().getZjlist().contains(
							elUser.getZhiji() + "")) {
				zj = true;
			} else {
				explain.append("职级 ");
				zhijiIspass = 1;
				sumIspass = -1;
				zj = false;
			}
		}
		if (eroom.getErRegistration().getZwlist() == null) {
			zw = true;// 不限
		} else {
			if (eroom.getErRegistration().getZwlist() != null
					&& elUser.getZhiwu() > 0
					&& eroom.getErRegistration().getZwlist().contains(
							elUser.getZhiwu() + "")) {
				zw = true;
			} else {
				explain.append("职务 ");
				zhiwuIspass = 1;
				sumIspass = -1;
				zw = false;
			}
		}
		if (eroom.getErRegistration().getGwlist() == null) {
			gw = true;
		} else {
			if (eroom.getErRegistration().getGwlist() != null
					&& elUser.getGangwei() != null
					&& eroom.getErRegistration().getGwlist().contains(
							elUser.getGangwei())) {
				gw = true;
			} else {
				explain.append("岗位 ");
				gw = false;
			}
		}
		// 年龄段
		if (eroom.getErRegistration().getStartAge() == 0
				&& eroom.getErRegistration().getStopAge() == 0) {
			nl = true;
		} else {
			if (eluser.getAGE() > eroom.getErRegistration().getStartAge()
					&& eroom.getErRegistration().getStopAge() > eluser.getAGE()) {
				nl = true;
			} else {
				explain.append("年龄 ");
				ageIspass = 1;
				sumIspass = -1;
				nl = false;
			}
		}
		// 性别
		if (eroom.getErRegistration().getSex().equals("不限")) {
			xb = true;
		} else if (eroom.getErRegistration().getSex().equals(eluser.getSex())) {
			xb = true;
		} else {
			explain.append("性别 ");
			sexIspass = 1;
			sumIspass = -1;
			xb = false;
		}

		// 部门
		if (eroom.getErRegistration().getTreeType() == null) {// 部门不限
			bm = true;
		} else {
			// if (eroom.getErRegistration().getTreeType() != null
			// && elUser.getDepartment() != null
			// && eroom.getErRegistration().getTreeTypelist().contains(
			// elUser.getDepartment().getId() + "")) {
			// bm = true;
			// } else {
			// explain.append("部门 ");
			// depIspass = 1;
			// sumIspass = -1;
			// bm = false;
			// }
			// 检测部门条件是否通过
			if (eroom.getErRegistration().getTreeType() != null
					&& elUser.getDepartment() != null
					&& userDao.checkUserIsInDep(elUser.getId(), eroom
							.getErRegistration().getTreeType())) {
				bm = true;
			} else {
				explain.append("部门 ");
				depIspass = 1;
				sumIspass = -1;
				bm = false;
			}
		}
		// 考场
			erooms = eroom.getErRegistration().checkErpapspassed(elUser.getId());
			if(!erooms){
				explain.append("考场");
				eroomIspass = 1;
				sumIspass = -1;
			}
		//考场试卷
			eroomeps = eroom.getErRegistration().checkEreppapspassed(elUser.getId());
			if(!eroomeps){
				explain.append("考场试卷");
				eroomepIspass = 1;
				sumIspass = -1;
			}
//		if (eroom.getErRegistration().getExamRooms() == null
//				|| eroom.getErRegistration().getExamRooms().equals("")
//				|| eroom.getErRegistration().getExamRooms().equals("0")) {// 考场不限
//			erooms = true;
//		} else {
//			String sqlWhere = "";
//			if (eroom.getErRegistration().getEroomScreeningWay() == 1) {
//				sqlWhere = " and ispassed  = 1";
//			} else if (eroom.getErRegistration().getEroomScreeningWay() == 2) {
//				sqlWhere = " and ispassed  = 0";
//			}
//			if (!eroom.getErRegistration().getExamRooms().equals("")
//					&& eroomDao.checkEroomIspassed(eroom.getErRegistration()
//							.getExamRooms(),
//							getSessionIntValue(ElConstants.SESSION_USERID),
//							sqlWhere)) {
//				erooms = true;
//			} else {
//				explain.append("考场");
//				eroomIspass = 1;
//				sumIspass = -1;
//				erooms = false;
//			}
//		}
		// 培训班
			elclass = eroom.getErRegistration().checkClasspapspassed(elUser.getId());
			if(!elclass){
				explain.append("培训班");
				classIspass = 1;
				sumIspass = -1;
			}
//		if (eroom.getErRegistration().getElclasss() == null
//				|| eroom.getErRegistration().getElclasss().equals("")
//				|| eroom.getErRegistration().getElclasss().equals("0")) {// 培训班不限
//			elclass = true;
//		} else {
//
//			String sqlWhere = "";
//			if (eroom.getErRegistration().getClassScreeningWay() == 1) {
//				sqlWhere = "and certificateno is not null";
//			} else if (eroom.getErRegistration().getClassScreeningWay() == 2) {
//				sqlWhere = "and certificateno is null";
//			}
//
//			if (!eroom.getErRegistration().getElclasss().equals("")
//					&& eroomDao.checkElclassIspassed(eroom.getErRegistration()
//							.getElclasss(),
//							getSessionIntValue(ElConstants.SESSION_USERID),
//							sqlWhere)) {
//				elclass = true;
//			} else {
//				explain.append("培训班");
//				classIspass = 1;
//				sumIspass = -1;
//				elclass = false;
//			}
//		}

		if (jz && ds && zj && zw && gw && nl && xb && bm && erooms &&eroomeps && elclass) { //  
			IsuserApp = true;
		} else {
			IsuserApp = false;
		}
		return IsuserApp;
	}

	public List<ElClass> getElclasses() {
		return elclasses;
	}

	public void setElclasses(List<ElClass> elclasses) {
		this.elclasses = elclasses;
	}

	public int getIsCorrespond() {
		return isCorrespond;
	}

	public void setIsCorrespond(int isCorrespond) {
		this.isCorrespond = isCorrespond;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getEroomepIspass() {
		return eroomepIspass;
	}

	public void setEroomepIspass(int eroomepIspass) {
		this.eroomepIspass = eroomepIspass;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public ScheduleGlobleDao getScheduleGlobleDao() {
		return scheduleGlobleDao;
	}

	public void setScheduleGlobleDao(ScheduleGlobleDao scheduleGlobleDao) {
		this.scheduleGlobleDao = scheduleGlobleDao;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	// public String study_room_record_list() throws ElException{
	// myroom.setExamroom(eroomDao.getExamRoomByid(myroom.getExamroom().getId()));
	// myroom.setMyRoomRecord(studyQuizDao.listStudyRoomRecord(getSessionIntValue(ElConstants.SESSION_USERID),
	// myroom.getExamroom().getId()));
	// if(myroom.getMyRoomRecord().size()==0){
	// List<MyRoomRecord> mrrList=new ArrayList<MyRoomRecord>();
	// mrrList.add(studyQuizDao.addStudyRoomRecord(getSessionIntValue(ElConstants.SESSION_USERID),
	// myroom.getExamroom().getId()));
	// myroom.setMyRoomRecord(mrrList);
	// }
	// return "study_room_record_list";
	// }
	//	
	// public String study_room_record_add() throws ElException{
	// studyQuizDao.addStudyRoomRecord(getSessionIntValue(ElConstants.SESSION_USERID),
	// myroom.getExamroom().getId());
	// return "study_room_record_list";
	// }

	public String MyIntegraInit() throws ElException {  
		int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID) : elUser.getId();
		elclass = classDao.getClassById(elclass.getId()); 
		float variables = 0.0f;
		integra = new Integra();
		//获取学员-培训班的状态
		status=studyClassDao.getStudyClassStatus(userid,elclass.getId()); 
		//---考试成绩加分   
		integra.setKc_courseXF(studyQuizDao.getKc_courseXF(userid, elclass.getId(), elclass.getClasstype() == 2 ?"CLASS_COURSE_AT":"CLASS_COURSE"));//已获学分的课程数 
		integra.setKc_scoresAVG(studyQuizDao.getKC_CJ_AVG(userid, elclass.getId(), elclass.getClasstype() == 2 ?"CLASS_COURSE_AT":"CLASS_COURSE"));//考试平均分
		//---学时加分 
		List XS = new  ArrayList();
		XS = studyQuizDao.getXs_period(userid, elclass.getId(), elclass.getClasstype() == 2 ?"CLASS_COURSE_AT":"CLASS_COURSE");
		if(XS.size() == 0){ //如果没有记录就初始化 0
			XS.add(0);//get(0)
			XS.add(0);//get(1)
		}
		integra.setXs_period(Float.parseFloat(XS.get(0).toString()));//已完成学时数  
		integra.setXs_exceed(Float.parseFloat(XS.get(1).toString()));//超过数  计算方程式（((课程已学时间s-课程规定时间s)/60)/60） 取一位小数
		//---练习加分 
		integra.setLx_course(studyQuizDao.getLX_course(userid, elclass.getId()));//已做练习的课程数 
		//---模考加分
		integra.setMk_Model(studyQuizDao.getMk_Model(userid, elclass.getId(), elclass.getClasstype() == 2 ?"CLASS_COURSE_AT":"CLASS_COURSE"));//已做模考的课程数  
		//---学分加分
		integra.setXf_credits(studyQuizDao.getXF_credits(userid, elclass.getId(), elclass.getClasstype() == 2 ?"CLASS_COURSE_AT":"CLASS_COURSE")); //已获学分数
		integra.setXf_beyond(integra.getXf_credits() >= 24 ? integra.getXf_credits() - 24 : 0);//超出数  已获取学分 - 24
		//---笔记得分
		integra.setBj_course(studyQuizDao.getBj_course(userid, elclass.getId(), elclass.getClasstype() == 2 ?"CLASS_COURSE_AT":"CLASS_COURSE"));//已做笔记的课程数
		if(status == 2){//拿证才能加分
			//---考试成绩加分   
			variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_KS_CJ);
			integra.setScore_kc_scoresAVG(integra.getKc_scoresAVG()*variables);
			//---学时加分 
			variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_XS_XS);
			integra.setScore_xs_exceed(integra.getXs_exceed()*variables); 
			//---练习加分 
			variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_LX_LX);
			integra.setScore_lx_course(integra.getLx_course()*variables); 
			//---模考加分
			variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_MK_MK);
			integra.setScore_mk_Model(integra.getMk_Model()*variables); 
			//---学分加分
			variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_XF_XF);
			integra.setScore_xf_beyond(integra.getXf_beyond()*variables);
			//---笔记得分
			variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_BJ_BJ);
			integra.setScore_bj_course(integra.getBj_course()*variables);
		}
		
		//---上传得分 
		integra.setSc_release(studyQuizDao.getSc_releaseORaudit(userid, 0));//已发布文章数 
		integra.setSc_audit(studyQuizDao.getSc_releaseORaudit(userid, 1));//已审核文章数
		variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_SC_SC);
		integra.setScore_sc_audit(integra.getSc_audit()*variables);
		
		//---被推荐得分 
		integra.setBtj_article(studyQuizDao.getBtj_article(userid, 1));//被推荐的文章数   1为推荐
		variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_BTJ_BTJ);
		integra.setScore_btj_article(integra.getBtj_article()*variables);
		
		//---被下载得分   
		integra.setBxz_audit(studyQuizDao.getBxz_audit(userid, 1));//已审核文章数  1为已审核
		integra.setBxz_people(studyQuizDao.getBxz_people(userid));//下载人次   无下载记录 ， 需要重新做下载记录
		variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_BXZ_BXZ);
		integra.setScore_bxz_people(integra.getBxz_people()*variables);
		
		//---下载得分 
		integra.setXz_audit(studyQuizDao.getXz_audit(userid));//下载文章数    
		variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_XZ_XZ);
		int max = knowledgeDao.getDownloadInfoIsAddCent(1, getSessionIntValue(ElConstants.SESSION_USERID)); //等分文章数
		integra.setScore_xz_audit(max*variables);
		
		//---发帖得分 
		integra.setFt_post(studyQuizDao.getFt_postORpass(userid, 0));//发帖数 
		integra.setFt_pass(studyQuizDao.getFt_postORpass(userid, 1));//通过数
		variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_FT_FT);
		integra.setScore_ft_pass(integra.getFt_pass()*variables);
		
		//---发言得分 
		integra.setFy_speech(studyQuizDao.getFy_speech(userid));//发言次数   
		variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_FY_FY);
		integra.setScore_fy_speech(integra.getFy_speech()*variables);
		
		//---精华帖得分 
		integra.setJh_jht(studyQuizDao.getJh_jht(userid, 1));//精华帖数量  1为精华帖
		variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_JH_JH);
		integra.setScore_jh_jht(integra.getJh_jht()*variables);
		
		//---登陆加分 
		integra.setDl_login(studyQuizDao.getDl_login(userid));//登陆次数
		variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_DL_DL);
		integra.setScore_dl_login(integra.getDl_login()*variables);
		
		//保存加分记录
		precord = new PointsRecord();
		precord.setElclass(elclass);
		precord.setUser(new ELUser(userid));
		precord.setCscore(status != 2?0:integra.getScore_kc_scoresAVG()+integra.getScore_xs_exceed()+integra.getScore_lx_course()+integra.getScore_mk_Model()+integra.getScore_xf_beyond()+integra.getScore_bj_course());
		precord.setFscore(integra.getScore_sc_audit()+integra.getScore_btj_article()+integra.getScore_xz_audit()+integra.getScore_ft_pass()+integra.getScore_fy_speech()+integra.getScore_jh_jht()+integra.getScore_dl_login());
		
		if(studyQuizDao.checkPointsRecord(elclass.getId(), userid)){//存在
			studyQuizDao.alterPointsRecord(precord);
		}else{//不存在
			studyQuizDao.intoPointsRecord(precord);
		} 
		return "MyIntegra";
	}
	public String MyIntegra_LX_MK_BJ_viewInit() throws ElException {   
		elclass = classDao.getClassById(elclass.getId()); 
		int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID) : elUser.getId();
		courses = knowledgeDao.getLX_MK_BJ_Integra_viewList(userid, elclass.getId(), elclass.getClasstype() == 2 ?"CLASS_COURSE_AT":"CLASS_COURSE"); 
		return "MyIntegra_LX_MK_BJ_view";
	}
	public String MyIntegra_Dl_viewInit() throws ElException {  
		int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID) : elUser.getId();
		myLogins = knowledgeDao.getDL_Integra_viewList(userid,getPageNow(), getPageSize());
		count = knowledgeDao.getDL_Integra_viewListSize(userid);
		//---登陆总分 
		integra = new Integra();
		integra.setDl_login(studyQuizDao.getDl_login(userid));//登陆需加分次数
		float variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_DL_DL);
		integra.setScore_dl_login(integra.getDl_login()*variables);
		return "MyIntegra_Dl_view";
	} 
	//发帖精华
	public String MyIntegra_FTJH_viewInit() throws ElException {  
		int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID) : elUser.getId();
		forums = knowledgeDao.getFTJH_Integra_viewList(userid,getPageNow(), getPageSize());
		count = knowledgeDao.getFTJH_Integra_viewListSize(userid);
		return "MyIntegra_FTJH_view";
	} 
	public String MyIntegra_XZ_viewInit() throws ElException {  
		int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID) : elUser.getId();
		downloadInfos = knowledgeDao.getXZ_Integra_viewList(userid,getPageNow(), getPageSize());
		count = knowledgeDao.getXZ_Integra_viewListSize(userid);
		//---下载得分 
		integra = new Integra();  
		float variables = SystemConfOp.getFloatValue(ElConstants.LEARNING_XZ_XZ);
		int max = knowledgeDao.getDownloadInfoIsAddCent(1, getSessionIntValue(ElConstants.SESSION_USERID)); 
		integra.setScore_xz_audit(max*variables);
		return "MyIntegra_XZ_view";
	}
	public String MyIntegra_knowledge_viewInit() throws ElException { 
		int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID) : elUser.getId();
		knowledges = knowledgeDao.getKl_Integra_viewList(userid,getPageNow(), getPageSize());
		count = knowledgeDao.getKl_Integra_viewListSize(userid);
		return "MyIntegra_knowledge_view";
	}
	public String MyIntegra_BXZ_viewInit() throws ElException { 
		int userid = elUser == null ? getSessionIntValue(ElConstants.SESSION_USERID) : elUser.getId();
		knowledges = knowledgeDao.getBXZ_Integra_viewList(userid,getPageNow(), getPageSize());
		count = knowledgeDao.getBXZ_Integra_viewListSize(userid);
		return "MyIntegra_BXZ_view";
	}
	public String MyIntegra_BXZ_XQ_viewInit() throws ElException {  
		elUsers = knowledgeDao.getBXZ_XQ_Integra_viewList(knowledge.getId(),getPageNow(), getPageSize());
		count = knowledgeDao.getBXZ_XQ_Integra_viewListSize(knowledge.getId());
		return "MyIntegra_BXZ_XQ_view";
	}

	public MyCPage getMyCPage() {
		return myCPage;
	}

	public void setMyCPage(MyCPage myCPage) {
		this.myCPage = myCPage;
	}
}
