package com.sopia.studyman.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.jspsmart.upload.Request;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.OnlineUtil;
import com.sopia.common.ScoreOperate;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.CourseCommentDao;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CoursePageDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseNote;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.CourseServer;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.PracticePaper;
import com.sopia.courseman.entities.ScormCourse;
import com.sopia.duman.entities.ELUser;
import com.sopia.frontman.dao.FrontDao;
import com.sopia.intelligentTutoringPoints.IntelligentWeekUtil;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.openmeetings.OmDao;
import com.sopia.openmeetings.Rooms;
import com.sopia.peixunBatch.dao.PeixunBatchDao;
import com.sopia.peixunBatch.entities.PeixunBatch;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.impl.ExamPaperDaoImpl;
import com.sopia.questionman.dao.impl.QuestionDaoImpl;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.statman.dao.StatisticDao;
import com.sopia.statman.dao.StatisticQuizDao;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.dao.StudyScormDao;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyCourseRecord;
import com.sopia.studyman.entities.MyEprac;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.wjm.dao.ClassificationDao;
import com.sopia.wjm.entities.ELUserClassification;

public class StudyCourse extends BaseAction {
	private static final Log logger = LogFactory.getLog(StudyCourse.class);
	private List<MyCourse> myCourses;
	private MyCourse myCourse;
	private CourseTypeDao ctypeDao;
	private CourseType ctypeTree;
	private CourseType ctype;
	private String Return;

	private CourseDao courseDao;
	private ClassDao classDao;
	private Course course;
	private ELUser elUser;
	private StatisticDao statisticDao;
	private CoursePage coursePage;
	private CoursePageDao coursePageDao;
	private List<CoursePage> coursePages;
	private List<Course> courses;
	private ElClass elclass;

	private MyCPage myCPage;
	private List<MyCPage> myCPages;
	private int course_sourse;
	private List<CourseNote> cnotes;
	private CourseNote cnote;
	private StudyCourseDao studyCourseDao;
	private FrontDao frontDao;
	private NewsDao newsDao;
	private PracticePaper pracPaper;
	private ExamPaper examPaper;
	private Question question;
	private int status=0;
	private int studyCourseRecordId=0;
	private ScormCourse scormcourse;
	private int classid;
	private MyRoom myroom;
	
	private HttpRequestDeviceUtils httpRequestDeviceUtils;
	private List<CoursePage> cpages;
	private String mod;
	private List<StuffLib> stuffs;
	private int usercount;
	private CourseCommentDao courseCommentDao;
	private boolean initCompliance;
	
	private ExamPaperDao examPaperDao;
	private StudyQuizDao studyQuizDao;
	private ClassificationDao classificationDao;
	private MyCourse mycourse;
	private ExamRoom examRoom;
	private EroomDao eroomDao;
	private int epid;
	private StatisticQuizDao statisticQuizDao;
	
	
	public StatisticQuizDao getStatisticQuizDao() {
		return statisticQuizDao;
	}

	public void setStatisticQuizDao(StatisticQuizDao statisticQuizDao) {
		this.statisticQuizDao = statisticQuizDao;
	}

	public int getEpid() {
		return epid;
	}

	public void setEpid(int epid) {
		this.epid = epid;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public MyCourse getMycourse() {
		return mycourse;
	}

	public void setMycourse(MyCourse mycourse) {
		this.mycourse = mycourse;
	}

	public ExamPaperDao getExamPaperDao() {
		return examPaperDao;
	}

	public void setExamPaperDao(ExamPaperDao examPaperDao) {
		this.examPaperDao = examPaperDao;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public ClassificationDao getClassificationDao() {
		return classificationDao;
	}

	public void setClassificationDao(ClassificationDao classificationDao) {
		this.classificationDao = classificationDao;
	}

	public boolean isInitCompliance() {
		return initCompliance;
	}

	public void setInitCompliance(boolean initCompliance) {
		this.initCompliance = initCompliance;
	}

	public CourseCommentDao getCourseCommentDao() {
		return courseCommentDao;
	}

	public void setCourseCommentDao(CourseCommentDao courseCommentDao) {
		this.courseCommentDao = courseCommentDao;
	}

	public int getUsercount() {
		return usercount;
	}

	public void setUsercount(int usercount) {
		this.usercount = usercount;
	}

	public List<StuffLib> getStuffs() {
		return stuffs;
	}

	public void setStuffs(List<StuffLib> stuffs) {
		this.stuffs = stuffs;
	}

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}

	public List<CoursePage> getCpages() {
		return cpages;
	}

	public void setCpages(List<CoursePage> cpages) {
		this.cpages = cpages;
	}

	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}

	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}

	private PeixunBatchDao peixunBatchDao;
	
	public PeixunBatchDao getPeixunBatchDao() {
		return peixunBatchDao;
	}

	public void setPeixunBatchDao(PeixunBatchDao peixunBatchDao) {
		this.peixunBatchDao = peixunBatchDao;
	}

	public MyRoom getMyroom() {
		return myroom;
	}

	public void setMyroom(MyRoom myroom) {
		this.myroom = myroom;
	}

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public ScormCourse getScormcourse() {
		return scormcourse;
	}

	public void setScormcourse(ScormCourse scormcourse) {
		this.scormcourse = scormcourse;
	}
	
	private List<Integer> li;

	public List<Integer> getLi() {
		return li;
	}

	public void setLi(List<Integer> li) {
		this.li = li;
	}

	public int getStudyCourseRecordId() {
		return studyCourseRecordId;
	}

	public void setStudyCourseRecordId(int studyCourseRecordId) {
		this.studyCourseRecordId = studyCourseRecordId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	public PracticePaper getPracPaper() {
		return pracPaper;
	}

	public void setPracPaper(PracticePaper pracPaper) {
		this.pracPaper = pracPaper;
	}

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public FrontDao getFrontDao() {
		return frontDao;
	}

	public void setFrontDao(FrontDao frontDao) {
		this.frontDao = frontDao;
	}

	public StudyCourseDao getStudyCourseDao() {
		return studyCourseDao;
	}

	public void setStudyCourseDao(StudyCourseDao studyCourseDao) {
		this.studyCourseDao = studyCourseDao;
	}

	public List<CourseNote> getCnotes() {
		return cnotes;
	}

	public void setCnotes(List<CourseNote> cnotes) {
		this.cnotes = cnotes;
	}

	public CourseNote getCnote() {
		return cnote;
	}

	public void setCnote(CourseNote cnote) {
		this.cnote = cnote;
	}

	public int getCourse_sourse() {
		return course_sourse;
	}

	public void setCourse_sourse(int course_sourse) {
		this.course_sourse = course_sourse;
	}

	public MyCPage getMyCPage() {
		return myCPage;
	}

	public void setMyCPage(MyCPage myCPage) {
		this.myCPage = myCPage;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String listCanAppalyCourseInit() throws ElException {
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "listCanAppalyCourseInit_phone"; 
		}

		return "listCanAppalyCourseInit";
	}

	/**
	 * ѡ��
	 * 
	 * @return
	 * @throws ElException
	 */
	public String submitAppalyCourse() throws ElException {
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		// if (!studyCourseDao.checkMyCourse(new MyCourse(userid,
		// course.getId()))) {
		studyCourseDao.studyApplyCourse(userid, course.getId());
		setElmessage("ѡ�޳ɹ�");
		// ScoreOperate.setScore(userid, ElConstants.SCORE_COURSE_APPLY);
		// }
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
				.getId();
		// ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true, String.valueOf(userid), true,
				"COURSE_USE_TYPE");

		// if (course_sourse == 1)// �ϼ����ŷ����
		// {
		// courses = studyCourseDao.listAllCourseFromSuper(userid, depid,
		// course.getName(), ctid, getPageNow(), getPageSize());
		// count = studyCourseDao.listAllCourseSizeFromSuper(userid, depid,
		// course.getName(), ctid);
		// }
		// if (course_sourse == 2)// �����ŵ���Դ
		// {
		// courses = studyCourseDao.listAllCourseFromThis(userid, depid,
		// course.getName(), ctid, getPageNow(), getPageSize());
		// count = studyCourseDao.listAllCourseSizeFromThis(userid, depid,
		// course.getName(), ctid);
		String coursename = course != null ? course.getName() : "";
		courses = studyCourseDao.listAllCourseFromThis(ctypeTree, userid,
				depid, coursename, ctid, getPageNow(), getPageSize());
		count = studyCourseDao.listAllCourseSizeFromThis(ctypeTree, userid,
				depid, coursename, ctid);
		// }
		if (null != courses) {
			for (int i = 0; i < courses.size(); i++) {
				// courses.get(i).setUserInCourse(
				// studyCourseDao.checkMyCourse(new MyCourse(userid,
				// courses.get(i).getId())));
				courses.get(i).setValid(
						studyCourseDao.checkMyCourseValid(new MyCourse(userid,
								courses.get(i).getId())));
			}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "submitAppalyCourse_phone"; 
		}
		return "submitAppalyCourse";
	}

	public String listCanAppalyCourse() throws ElException {
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
				.getId();
		String coursename = course != null ? course.getName() : "";
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		// ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		String userids = "";
		String myUserId = String
				.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			List<ELUser> userList = newsDao
					.findUserByMyDeptid(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
			for (ELUser user : userList) {
				if (!userids.equals("")) {
					userids += ",";
				}
				userids += user.getId();
			}
			myUserId = userids;
			if (!userids.equals("")) {
				ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(
						ElConstants.TREE_ROOT, ElConstants.TREE_FIANL, true,
						myUserId, true, "COURSE_USE_TYPE");
			}
		}
		// if (course_sourse == 1)// �ϼ����ŷ����
		// {
		// courses = studyCourseDao.listAllCourseFromSuper(userid, depid,
		// coursename, ctid, getPageNow(), getPageSize());
		// count = studyCourseDao.listAllCourseSizeFromSuper(userid, depid,
		// coursename, ctid);
		// }
		// if (course_sourse == 2)// �����ŵ���Դ
		// {
		courses = studyCourseDao.listAllCourseFromThis(ctypeTree, userid,
				depid, coursename, ctid, getPageNow(), getPageSize());
		count = studyCourseDao.listAllCourseSizeFromThis(ctypeTree, userid,
				depid, coursename, ctid);
		// }

		if (null != courses) {
			for (int i = 0; i < courses.size(); i++) {
				// courses.get(i).setUserInCourse(
				// studyCourseDao.checkMyCourse(new MyCourse(userid,
				// courses.get(i).getId())));
				courses.get(i).setValid(
						studyCourseDao.checkMyCourseValid(new MyCourse(userid,
								courses.get(i).getId())));
			}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "listCanAppalyCourse_phone"; 
		}
		return "listCanAppalyCourse";
	}

	public String course_study_wbkc() throws ElException {
		// cserver = courseDao.getCourseServer(cserver.getId());
		int classid=0;
		if(course!=null){
			classid=course.getClassid();//�ȱ��洫��������ѵ��id
		}else{
			course=new Course();
		}
		course = courseDao.getCourseById(course.getId());
		course.setClassid(classid);//���õ��γ�
//		myCourse = studyCourseDao.getMyStudyCourse(
//				getSessionIntValue(ElConstants.SESSION_USERID), course.getId());
		myCourse = studyCourseDao.getMyStudyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId(),classid);
		if(OnlineUtil.checksStudyInfo(classid, course.getId(), 0, getSession()))
		{
			course_study_to= "course_study_wbkc" ;												////!!!!????
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "course_study_logout_phone"; 
			}
			return "course_study_logout";
		}
		OnlineUtil.setStudyInfo(classid, course.getId(), 0, getSession());
		//��¼1��ѧϰ��¼������ͳ��
//		studyCourseDao.addStudyCourseRecord(course.getId(),classid,0,getSessionIntValue(ElConstants.SESSION_USERID));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_wbkc_phone";
		}
		return "course_study_wbkc";
	}

	private OmDao omDao;

	public OmDao getOmDao() {
		return omDao;
	}

	public void setOmDao(OmDao omDao) {
		this.omDao = omDao;
	}

	/**
	 * Description:ͬ������ѧϰ��� 
	* @Version1.0 2012-7-22 ����02:58:25 by ����˴��wenyishun110@163.com������
	 * @return
	 * @throws ElException
	 */
	public String course_study_tbkt() throws ElException {
		// cserver = courseDao.getCourseServer(cserver.getId());
		int classid=0;
		if(course!=null){
			classid=course.getClassid();//�ȱ��洫��������ѵ��id
		}else{
			course=new Course();
		}
		course = courseDao.getCourseById(course.getId());
		course.setClassid(classid);///�û�ҳ���ȡ
//		if(course.getRoomstart().after(new Date() )){//��������ѵ�����棬���������ÿ�ʼ����ʱ���
//			setElmessage("�γ�û����ʼʱ��!");
//			return "error";
//		}
//		if(course.getRoomend().before(new Date() )){
//			setElmessage("�γ��Ѿ�����!");
//			return "error";
//		} 
		if ( course.getTeacherId() != getSessionIntValue(ElConstants.SESSION_USERID)) {//���ǽ�ʦ
			if (!omDao.moderatorHasLogin(course.getRoom().getId())) {//��ʦδ����
				setElmessage("��������δ���룡���Ժ�");
				boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
				if(b==true){
					return "error_phone"; 
				}

				return "error";
			} 
		} else{
			omDao.setModeratorHasLoginOut(course.getRoom().getId(), 1);
			getSession().setAttribute("roomid", course.getRoom().getId()); 
			getSession().setAttribute("teacherId", getSessionIntValue(ElConstants.SESSION_USERID));//��ʦid
		}
//		myCourse = studyCourseDao.getMyStudyCourse(
//				getSessionIntValue(ElConstants.SESSION_USERID), course.getId());
		myCourse = studyCourseDao.getMyStudyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId(),classid);
		if(OnlineUtil.checksStudyInfo(course.getClassid(), course.getId(), 0, getSession()))
		{
			course_study_to= "course_study_tbkt" ;
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "course_study_logout_phone"; 
			}
			return "course_study_logout";
		}
		OnlineUtil.setStudyInfo(course.getClassid(), course.getId(), 0, getSession());
		course.setClassid(classid);///�û�ҳ���ȡ
		//��¼1��ѧϰ��¼������ͳ��
//		studyCourseDao.addStudyCourseRecord(course.getId(),classid,0,getSessionIntValue(ElConstants.SESSION_USERID));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_tbkt_phone"; 
		}
		return "course_study_tbkt";
	}
	/**
	 * Description:scorm �γ�ѧϰ��� 
	* @Version1.0 2012-7-22 ����02:58:47 by ����˴��wenyishun110@163.com������
	 * @return
	 * @throws ElException
	 */
	public String course_study_scorm() throws ElException {
		int classid=0;
		if(course!=null){
			classid=course.getClassid();//�ȱ��洫��������ѵ��id
		}else{
			course=new Course();
		}
		course = courseDao.getCourseById(course.getId());
		course.setClassid(classid);///�û�ҳ���ȡ
		StudyScormDao ssd =((StudyScormDao)SpringContextUtil.getBean("studyScormDao"));
		String userid =getSessionValue(ElConstants.SESSION_USERID)+"";
		ssd.registerCourse(userid, course.getExurl(),classid+"");
		scormcourse = ssd.intoCourse(course.getExurl(), userid,classid+"", scormcourse!=null?scormcourse.getScoid():null, scormcourse!=null?scormcourse.getNowScoid():null, scormcourse!=null?scormcourse.getNavitype():null);
		
		myCourse = studyCourseDao.getMyStudyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId(),classid);
		if(OnlineUtil.checksStudyInfo(course.getClassid(), course.getId(), 0, getSession()))
		{
			course_study_to= "course_study_scorm" ;
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "course_study_logout_phone"; 
			}
			return "course_study_logout";
		}
		OnlineUtil.setStudyInfo(course.getClassid(), course.getId(), 0, getSession());
		course.setClassid(classid);///�û�ҳ���ȡ
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_scorm_phone"; 
		}
		return "course_study_scorm";
	}
	public String course_study_zhwb_page() throws ElException {
		int classid=0;
		if(course!=null){
			classid=course.getClassid();//�ȱ��洫��������ѵ��id
		}

		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		int courseid = course == null ? 0 : course.getId();
		int cpid = coursePage == null || coursePage.getId() <= 0 ? studyCourseDao
				.getMyLastCpage(userid, courseid)
				: coursePage.getId();
		if (cpid <= 0)
			cpid = coursePageDao.getFirstCpId(courseid);
		coursePage = coursePageDao.getCp(cpid);
		course = courseDao.getCourseById(courseid);
		if (null != coursePage) {
			if (!studyCourseDao.checkMyCPage(new MyCPage(
					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
							.getId()),classid)) {
				studyCourseDao.intoMyCPage(new MyCPage(
						getSessionIntValue(ElConstants.SESSION_USERID),
						coursePage.getId()),classid);
			}
//			myCPage = studyCourseDao.getMyCPage(
//					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
//							.getId());
			myCPage = studyCourseDao.getMyCPage(
					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
							.getId(),classid);
			myCPage.setCpage(coursePage);
			myCPage.setCpages(coursePages);
			myCPages = studyCourseDao.listCpsbyCUid(course.getId(),
					getSessionIntValue(ElConstants.SESSION_USERID),classid);
			if (null != myCPages)
				for (int i = 0; i < myCPages.size(); i++) {
					// myCPages
					// .get(i)
					// .setMyPracs(
					// courseDao
					// .listMyPracpapers(
					// getSessionIntValue(ElConstants.SESSION_USERID),
					// course.getId(), myCPages
					// .get(i).getCpage()
					// .getId()));
				}
			// course.setMyPracs(courseDao.listMyPracpapers(
			// getSessionIntValue(ElConstants.SESSION_USERID), course
			// .getId(), 0));
			ScoreOperate.setScore(
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.DIAN_STUDY_DO);
		}
		course.setClassid(classid);//���õ��γ�
//		myCourse = studyCourseDao.getMyStudyCourse(
//				getSessionIntValue(ElConstants.SESSION_USERID), course.getId());
		myCourse = studyCourseDao.getMyStudyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId(),classid);
		if (myCPages == null || myCPages.size() == 0)
			setElmessage("�γ�������");
		if(OnlineUtil.checksStudyInfo(course.getClassid(), course.getId(), coursePage.getId(), getSession()))
		{
			course_study_to= "course_study_zhwb_page" ;
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "course_study_logout_phone"; 
			}
			return "course_study_logout";
		}
		OnlineUtil.setStudyInfo(course.getClassid(), course.getId(), coursePage.getId(), getSession());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_zhwb_page_phone"; 
		}
		return "course_study_zhwb_page";
	}

	public String course_study_zhwb() throws ElException {
		int classid=0;
		if(course!=null){
			classid=course.getClassid();//�ȱ��洫��������ѵ��id
		}else{
			course=new Course();
		}
		// cserver = courseDao.getCourseServer(cserver.getId());
		int cpid = coursePage == null || coursePage.getId() <= 0 ? coursePageDao
				.getFirstCpId(course.getId())
				: coursePage.getId();
		coursePage = coursePageDao.getCp(cpid);
		course = courseDao.getCourseById(course.getId());
		course.setClassid(classid);
		if (null != coursePage) {
			if (!studyCourseDao.checkMyCPage(new MyCPage(getSessionIntValue(ElConstants.SESSION_USERID), coursePage.getId()))) {
				studyCourseDao.intoMyCPage(new MyCPage(
						getSessionIntValue(ElConstants.SESSION_USERID),
						coursePage.getId()));
			}
//			myCPage = studyCourseDao.getMyCPage(
//					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
//							.getId());
			myCPage = studyCourseDao.getMyCPage(
					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
							.getId(),classid);
			myCPage.setCpage(coursePage);
			myCPage.setCpages(coursePages);
			myCPages = studyCourseDao.listCpsbyCUid(course.getId(),
					getSessionIntValue(ElConstants.SESSION_USERID));
			if (null != myCPages)
				for (int i = 0; i < myCPages.size(); i++) {
					// myCPages
					// .get(i)
					// .setMyPracs(
					// studyCourseDao
					// .listMyPracpapers(
					// getSessionIntValue(ElConstants.SESSION_USERID),
					// course.getId(), myCPages
					// .get(i).getCpage()
					// .getId()));
				}
			// course.setMyPracs(studyCourseDao.listMyPracpapers(
			// getSessionIntValue(ElConstants.SESSION_USERID), course
			// .getId(), 0));
			// ScoreOperate.setScore(
			// getSessionIntValue(ElConstants.SESSION_USERID),
			// ElConstants.DIAN_STUDY_DO);
		}
//		myCourse = studyCourseDao.getMyStudyCourse(
//				getSessionIntValue(ElConstants.SESSION_USERID), course.getId());
		myCourse = studyCourseDao.getMyStudyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId(),classid);
		if (myCPages == null || myCPages.size() == 0)
			setElmessage("�γ�������");
		if(OnlineUtil.checksStudyInfo(course.getClassid(), course.getId(), coursePage.getId(), getSession()))
		{
			course_study_to= "course_study_zhwb" ;
			return "course_study_logout";
		}
		OnlineUtil.setStudyInfo(course.getClassid(), course.getId(), coursePage.getId(), getSession());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_zhwb_phone"; 
		}
		return "course_study_zhwb";
	}
	public String course_study_save() throws ElException {
		myCourse.setUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		//�������� ��Ҫ���зֿ�ͳ��,�û�ȡ��ѵ��id
		String classidStr=getRequest().getParameter("classid");
		int classid=0;
		if(classidStr!=null){
			classid=Integer.parseInt(classidStr);
		}
		if(OnlineUtil.checksStudyInfo(classid, myCourse.getCourse().getId()	, 0, getSession())){
			try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.println("{\"message\":\"session_err\"}");
			localPrintWriter.flush();
			localPrintWriter.close();
			} catch (Exception e) {
				logger.error("�γ�ѧϰ�������",e);
			}
			return null ;
		}
		studyCourseDao.saveMyCourse(myCourse,classid);
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.println("{\"message\":\"ѧϰ����ɹ���\"}");
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("�γ�ѧϰ�������",e);
		}
		return null;
	}
	
	public String course_study_save2() throws ElException {
		myCourse.setUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		//�������� ��Ҫ���зֿ�ͳ��,�û�ȡ��ѵ��id
		String classidStr=getRequest().getParameter("classid");
		int classid=0;
		if(classidStr!=null){
			classid=Integer.parseInt(classidStr);
		}
		//studyCourseDao.saveMyCourse(myCourse,classid);
		if(OnlineUtil.checksStudyInfo(classid, myCourse.getCourse().getId()	, 0, getSession())){
			try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.println("{\"message\":\"session_err\"}");
			localPrintWriter.flush();
			localPrintWriter.close();
			} catch (Exception e) {
				logger.error("�γ�ѧϰ�������2",e);
			}
			return null ;
		}
		studyCourseDao.setStudyCoursePasstime2(myCourse,classid);
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.println("{\"message\":\"ѧϰ����ɹ���\"}");
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("�γ�ѧϰ�������2",e);
		}
		return null;
	}
	
	
	public String course_study_bzkc_index() throws ElException {
		int classid=0;
		if(course!=null){
			classid=course.getClassid();//�ȱ��洫��������ѵ��id
		}
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		int courseid = course == null ? 0 : course.getId();
//		int cpid = coursePage == null || coursePage.getId() <= 0 ? studyCourseDao
//				.getMyLastCpage(userid, courseid)
		int cpid = coursePage == null || coursePage.getId() <= 0 ? studyCourseDao
				.getMyLastCpage(userid, courseid,classid)
				: coursePage.getId();
		int ccpid=0;
		if (cpid <= 0){
			cpid = coursePageDao.getFirstCpId(courseid);
			ccpid=-1;
		}
		coursePage = coursePageDao.getCp(cpid);
		course = courseDao.getCourseById(courseid);
		//�ڴ�ע�����е��½�
		//�Ȳ�ÿγ̵������½�
		List<CoursePage> clist=courseDao.getCourseAllCpage(courseid);
		for(int i=0;i<clist.size();i++){
			if (!studyCourseDao.checkMyCPage(new MyCPage(
					getSessionIntValue(ElConstants.SESSION_USERID),clist.get(i).getId()),classid)) {
//				studyCourseDao.intoMyCPage(new MyCPage(
//						getSessionIntValue(ElConstants.SESSION_USERID),
//						coursePage.getId()));
				studyCourseDao.intoMyCPage(new MyCPage(getSessionIntValue(ElConstants.SESSION_USERID),clist.get(i).getId()),classid);
			}
		}
		
		if (null != coursePage) {
			//
//			if (!studyCourseDao.checkMyCPage(new MyCPage(
//					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
//							.getId()),classid)) {
////				studyCourseDao.intoMyCPage(new MyCPage(
////						getSessionIntValue(ElConstants.SESSION_USERID),
////						coursePage.getId()));
//				studyCourseDao.intoMyCPage(new MyCPage(getSessionIntValue(ElConstants.SESSION_USERID),coursePage.getId()),classid);
//			}
//			myCPage = studyCourseDao.getMyCPage(
//					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
//							.getId());
			myCPage = studyCourseDao.getMyCPage(
					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
							.getId(),classid);
			myCPage.setCpage(coursePage);
			myCPage.setCpages(coursePages);
			myCPages = studyCourseDao.listCpsbyCUid(courseid,
					getSessionIntValue(ElConstants.SESSION_USERID),classid);
			if (null != myCPages)
				for (int i = 0; i < myCPages.size(); i++) {
					// myCPages
					// .get(i)
					// .setMyPracs(
					// studyCourseDao
					// .listMyPracpapers(
					// getSessionIntValue(ElConstants.SESSION_USERID),
					// course.getId(), myCPages
					// .get(i).getCpage()
					// .getId()));
				}
			// course.setMyPracs(studyCourseDao.listMyPracpapers(
			// getSessionIntValue(ElConstants.SESSION_USERID), course
			// .getId(), 0));
			ScoreOperate.setScore(
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.DIAN_STUDY_DO);
		} else {
			setElmessage("û�ҵ��γ��½�,��ȷ�Ͽγ��Ƿ�������ַ�Ƿ���ȷ��");
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "error_phone"; 
			}

			return "error";
		}
		
		//course = courseDao.getCourseById(course.getId());
		course.setClassid(classid);//���õ��γ�
//		myCourse = studyCourseDao.getMyStudyCourse(
//				getSessionIntValue(ElConstants.SESSION_USERID), courseid);
		myCourse = studyCourseDao.getMyStudyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), courseid,classid);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			if(ccpid==-1){
				return "course_study_bzkc_phone";
			}
			return "course_study_bzkc_index_phone";
		}
		if(ccpid==-1){
			return "course_study_bzkc";
		}
		return "course_study_bzkc_index";
	}
	
	public String displayStudyCpageInfo() throws ElException {
//		myCPages=studyCourseDao.getStudyCpageInfo(course.getId(),getSessionIntValue(ElConstants.SESSION_USERID),course.getClassid());
		myCPages = studyCourseDao.listCpsbyCUid_wjm(course.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID),course.getClassid());
		myCourse=studyCourseDao.getMyStudyCourse(getSessionIntValue(ElConstants.SESSION_USERID), course.getId(),course.getClassid());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "displayStudyCpageInfo_phone";
		}
		return "displayStudyCpageInfo";
	}
	
	public String course_study_bzkc() throws ElException {
		int classid=0;
		if(course!=null){
			classid=course.getClassid();//�ȱ��洫��������ѵ��id
		}
		
		//�������� ��Ҫ���зֿ�ͳ��,�û�ȡ��ѵ��id
		String classidStr=getRequest().getParameter("classid");
		//int classid=0;
		if(classidStr!=null){
			classid=Integer.parseInt(classidStr);
		}

		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		int courseid = course == null ? 0 : course.getId();
//		int cpid = coursePage == null || coursePage.getId() <= 0 ? studyCourseDao
//				.getMyLastCpage(userid, courseid)
//				: coursePage.getId();
		int cpid = coursePage == null || coursePage.getId() <= 0 ? studyCourseDao
				.getMyLastCpage(userid, courseid,classid)
				: coursePage.getId();
		if (cpid <= 0)
			cpid = coursePageDao.getFirstCpId(courseid);
		coursePage = coursePageDao.getCp(cpid);
		course = courseDao.getCourseById(courseid);
		if (null != coursePage) {
			if (!studyCourseDao.checkMyCPage(new MyCPage(
					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
							.getId()),classid)) {
//				studyCourseDao.intoMyCPage(new MyCPage(
//						getSessionIntValue(ElConstants.SESSION_USERID),
//						coursePage.getId()));
				studyCourseDao.intoMyCPage(new MyCPage(getSessionIntValue(ElConstants.SESSION_USERID),coursePage.getId()),classid);
				
			}
//			myCPage = studyCourseDao.getMyCPage(
//					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
//							.getId());
			myCPage = studyCourseDao.getMyCPage(
					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
							.getId(),classid);
			myCPage.setCpage(coursePage);
			myCPage.setCpages(coursePages);
//			myCPages = studyCourseDao.listCpsbyCUid(courseid,
//					getSessionIntValue(ElConstants.SESSION_USERID));
			myCPages = studyCourseDao.listCpsbyCUid(courseid,getSessionIntValue(ElConstants.SESSION_USERID),classid);//--//
			if (null != myCPages)
				for (int i = 0; i < myCPages.size(); i++) {
					// myCPages
					// .get(i)
					// .setMyPracs(
					// studyCourseDao
					// .listMyPracpapers(
					// getSessionIntValue(ElConstants.SESSION_USERID),
					// course.getId(), myCPages
					// .get(i).getCpage()
					// .getId()));
				}
			// course.setMyPracs(studyCourseDao.listMyPracpapers(
			// getSessionIntValue(ElConstants.SESSION_USERID), course
			// .getId(), 0));
			ScoreOperate.setScore(
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.DIAN_STUDY_DO);
		} else {
			setElmessage("û�ҵ��γ��½�,��ȷ�Ͽγ��Ƿ�������ַ�Ƿ���ȷ��");
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "error_phone"; 
			}

			return "error";
		}
		course.setClassid(classid);//���õ��γ�
//		myCourse = studyCourseDao.getMyStudyCourse(
//				getSessionIntValue(ElConstants.SESSION_USERID), courseid);
		myCourse = studyCourseDao.getMyStudyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), courseid,classid);
		//����γ��½ڽ�ҵ��ʽ���
		int getcredit=coursePage.getGetcredit();
		int ispassed=myCPage.getPassed2();
		if(getcredit==1){
			//ѧ��
			//���ô���
		}else if(getcredit==2){
			//����
			if(ispassed==1){
				//����,���ý��100% 
				//studyCourseDao.saveMyCPage(myCPage,classid,3,coursePage.getDuring());
			}else{
				//ҳ����ֹͣ
				status=1;
			}
		}else{
			//ѧ���ҿ���
			//ÿ�α����ȼ�30��������ȼ�1��
			//��ѯ��ǰ��ѧϰ��ȣ�����50%�� ��ֹͣ
			//if(ispassed==0){
//				if(myCPage.getPasstime()>=coursePage.getDuring()*60/2){
//					status=1;
//				}
			//}
			status=2;//�ý�ҵ��ʽʵ�ʽ�Ȳ���
		}
		if(OnlineUtil.checksStudyInfo(classid, courseid, cpid, getSession()))
		{
			course_study_to= "course_study_bzkc" ;
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "course_study_logout_phone"; 
			}
			return "course_study_logout";
		}
		OnlineUtil.setStudyInfo(classid, courseid, cpid, getSession());
		//�ϵ���ѧ
		studyCourseDao.cPageFinishSet(userid, cpid, classid);
		//��¼1��ѧϰ��¼������ͳ��
//		studyCourseDao.addStudyCourseRecord(courseid,classid,cpid,userid);
		//studyCourseDao.updateStudyCourseRecordStatus(courseid, classid, cpid, userid, 0, new Timestamp(System.currentTimeMillis()));
		if (coursePage != null && coursePage.getType() == 1) {
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "course_study_csp_phone"; 
			}
			return "cpage_study_csp";
		}
		if (coursePage != null && coursePage.getType() == 3) {
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				System.out.println("�ֻ�˿γ�ѧϰ");
				return "cpage_study_wbkc_phone"; 
			}
			return "cpage_study_wbkc";
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "cpage_study_twspjy_phone"; 
		}
		return "cpage_study_twspjy";
	}
	
	public String course_study_to;
	public String course_study_logout() throws ElException {
		int roomid=0;
		int teacherId=0;
		if(getSession().getAttribute("roomid")!=null){
			roomid = Integer.parseInt(getSession().getAttribute("roomid").toString()); //roomid
			teacherId = Integer.parseInt(getSession().getAttribute("teacherId").toString());//��ʦid
		}
		
		//�������� ��Ҫ���зֿ�ͳ��,�û�ȡ��ѵ��id
		if(course.getIsLogout()==1){
			OnlineUtil.removeStudyInfo(getSession());
		}
		if(course_study_to==null||course_study_to.trim().equals("")){ 
			if(getSession().getAttribute("roomid")!=null){
				if(teacherId == getSessionIntValue(ElConstants.SESSION_USERID)){ 
					omDao.setModeratorHasLoginOut(roomid, 0);
					getSession().removeAttribute("roomid");
					getSession().removeAttribute("teacherId");
				}
			}
			return null;
		}
		course_study_to = course_study_to+"?coursePage.id="+coursePage.getId()+"&course.id="+course.getId()+"&course.classid="+course.getClassid()+"&course.isLogout="+course.getIsLogout();
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_logout_phone"; 
		}
		return "course_study_logout";
	}
	/**
	 * @return
	 * @throws ElException
	 */
	public String cpage_study_save() throws ElException {
		//�������� ��Ҫ���зֿ�ͳ��,�û�ȡ��ѵ��id
		String classidStr=getRequest().getParameter("classid");
		int classid=0;
		if(classidStr!=null){
			classid=Integer.parseInt(classidStr);
		}
		int ispassed=0;
		if (null != myCPage) {
			coursePage = coursePageDao.getCp(myCPage.getCpage().getId());
			// int during = 0;
			if (null != coursePage) {
				// during = coursePage.getDuring() * 60;
			} else {
				try {
					getResponse().setContentType("text/html;charset=UTF-8");
					PrintWriter localPrintWriter = getResponse().getWriter();
					localPrintWriter.println("{\"message\":\"�γ���ҳ��Ϣ����ȷ��\"}");
					localPrintWriter.flush();
					localPrintWriter.close();
				} catch (Exception e) {
					logger.error("�γ�ѧϰ�������2",e);
				}
				 return null;
			}
			// if (during >= (myCPage.getPasstime() + 5)) {
			// ����ѧϰ
			int courseid = coursePage.getCourse().getId();
			int cpid = coursePage.getId();
			myCPage.setUser(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			//studyCourseDao.saveMyCPage(myCPage);
			if(OnlineUtil.checksStudyInfo(classid, courseid, cpid, getSession())){
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				localPrintWriter.println("{\"message\":\"session_err\"}");
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				logger.error("�γ�ѧϰ�������",e);
			}
			return null;
			}
			//����ҵ��ʽ��ѧ���ҿ�����ô�ഫ��ֵ��ȥ��������ֵΪ1��û����ֵΪ0
			myCPage = studyCourseDao.getMyCPage(getSessionIntValue(ElConstants.SESSION_USERID), coursePage.getId(),classid);
			//studyCourseDao.saveMyCPage(myCPage,classid);
			int getcredit=coursePage.getGetcredit();
			ispassed=myCPage.getPassed2();
			if(getcredit==1){
				studyCourseDao.saveMyCPage(myCPage,classid,1,60);
			}else if(getcredit==2){
				
			}else{
				if(ispassed==1){
					//����,���ý��100% 
					//studyCourseDao.saveMyCPage(myCPage,classid,3,coursePage.getDuring());
					//�ж��Ƿ�ѧ��
					int passtime=coursePage.getDuring()*60;
					int passtime2=myCPage.getPasstime2();
					if(passtime2>=passtime-30){
						studyCourseDao.saveMyCPage(myCPage,classid,1,passtime2);
					}
				}else{
					//studyCourseDao.saveMyCPage(myCPage,classid,0,60);
//					if(myCPage.getPasstime()+30>=coursePage.getDuring()*60/2){
//						status=1;
//					}
				}
			}
		} 
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			//localPrintWriter.println("{\"message\":\"ѧϰ����ɹ���\"}");
			if(status==1){
				localPrintWriter.println("{\"message\":\"session_err2\"}");
			}else{
				localPrintWriter.println("{\"message\":\"ѧϰ����ɹ���\",\"isPassed\":\""+ispassed+"\"}");
			}
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("�γ�ѧϰ�������",e);
		} 
		return null;
	}
	
	
	public String cpage_study_save2() throws ElException {
		//�������� ��Ҫ���зֿ�ͳ��,�û�ȡ��ѵ��id
		String classidStr=getRequest().getParameter("classid");
		int classid=0;
		if(classidStr!=null){
			classid=Integer.parseInt(classidStr);
		}
		if (null != myCPage) {
			coursePage = coursePageDao.getCp(myCPage.getCpage().getId());
			// int during = 0;
			if (null != coursePage) {
				// during = coursePage.getDuring() * 60;
			} else {
				try {
					getResponse().setContentType("text/html;charset=UTF-8");
					PrintWriter localPrintWriter = getResponse().getWriter();
					localPrintWriter.println("{\"message\":\"�γ���ҳ��Ϣ����ȷ��\"}");
					localPrintWriter.flush();
					localPrintWriter.close();
				} catch (Exception e) {
					logger.error("�γ�ѧϰ�������2",e);
				}
				 return null;
			}
			// if (during >= (myCPage.getPasstime() + 5)) {
			// ����ѧϰ
			int courseid = coursePage.getCourse().getId();
			int cpid = coursePage.getId();
			myCPage.setUser(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			//studyCourseDao.saveMyCPage(myCPage);
			if(OnlineUtil.checksStudyInfo(classid, courseid, cpid, getSession())){
				try {
					getResponse().setContentType("text/html;charset=UTF-8");
					PrintWriter localPrintWriter = getResponse().getWriter();
					localPrintWriter.println("{\"message\":\"session_err\"}");
					localPrintWriter.flush();
					localPrintWriter.close();
				} catch (Exception e) {
					logger.error("�γ�ѧϰ�������2",e);
				}
				return null;	
			}
			//studyCourseDao.saveMyCPage(myCPage,classid); 
			studyCourseDao.setStudyCpagePasstime2(myCPage,classid);
			if(myCourse==null){
				myCourse=new MyCourse();
			}
			myCourse.setUser(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
			myCourse.setCourse(new Course(courseid));
			studyCourseDao.setStudyCoursePasstime2(myCourse, classid);
		}
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.println("{\"message\":\"ѧϰ����ɹ���\"}");
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("�γ�ѧϰ�������2",e);
		} 
		return null;
	}
	
	public String pracPaperinto() throws ElException {
		//long starttime = System.currentTimeMillis();
		pracPaper = courseDao.getPracticePaperById(pracPaper.getId());
		examPaper = new ExamPaperDaoImpl().getEPAllInfoById(pracPaper.getExamPaper().getId());
		//return "exampaper_preview";
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "pracPaper_view_phone"; 
		}
		return "pracPaper_view";
	}
	
	public String pracPaperquestioninitinto() throws ElException {
		question=new QuestionDaoImpl().getQuestionByid(question.getId());
		if (question == null || 0 == question.getId()) {
			setElmessage("û�ҵ�����Ҫ�����⡣��ȷ���������Ƿ��������ӳ���ε��Ծ��У�");
			return "error";
		}
		if (question.getQtype() == 8) {
//			question.setRulestring(studyQuizDao.getQRulestrByREBid(myExamPaper
//					.getId(), question));
//			if(question.getRulestring()==null||"".equals(question.getRulestring().trim()))
//			{
//				setElmessage("���������ֹ���δ�趨");
//				return "error";
//			}
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "quizquestion_dazi_phone"; 
			}
			return "quizquestion_dazi";
		} else if (question.getQtype() == 9) {
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "quizquestion_email_phone"; 
			}
			return "quizquestion_email";
		} else if (question.getQtype() == 10) {
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "quizquestion_search_phone"; 
			}
			return "quizquestion_search";
		} else {
			setElmessage("���ʼ��⣬���������������¿����������������������ⲻ���¿���������");
			return "error";
		}
	}

	public String course_study_dysp() throws ElException {//��Ҫ��ȡclassid���´�
		int classid=0;
		int courseid =0;
		if(course!=null){
			classid=course.getClassid();//�ȱ��洫��������ѵ��id
			courseid = course.getId();
		}
		// coursePage = coursePageDao.getCp(coursePage.getId());
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		int cpid = coursePage == null || coursePage.getId() <= 0 ? studyCourseDao
				.getMyLastCpage(userid, courseid)
				: coursePage.getId();
		if (cpid <= 0)
			cpid = coursePageDao.getFirstCpId(courseid);
		coursePage = coursePageDao.getCp(cpid);
		course = courseDao.getCourseById(courseid);
		if (null != coursePage) {
			if (!studyCourseDao.checkMyCPage(new MyCPage(userid, coursePage
					.getId()),classid)) {
				studyCourseDao.intoMyCPage(new MyCPage(userid, coursePage
						.getId()),classid);
			}
//			myCPage = studyCourseDao.getMyCPage(userid, coursePage.getId());
			myCPage = studyCourseDao.getMyCPage(
					getSessionIntValue(ElConstants.SESSION_USERID), coursePage
							.getId(),classid);
			myCPage.setCpage(coursePage);
			myCPage.setCpages(coursePages);
			myCPages = studyCourseDao.listCpsbyCUid(course.getId(), userid,classid);
			if (null != myCPages)
				for (int i = 0; i < myCPages.size(); i++) {
					// myCPages
					// .get(i)
					// .setMyPracs(
					// studyCourseDao
					// .listMyPracpapers(
					// getSessionIntValue(ElConstants.SESSION_USERID),
					// course.getId(), myCPages
					// .get(i).getCpage()
					// .getId()));
				}
			// course.setMyPracs(studyCourseDao.listMyPracpapers(
			// getSessionIntValue(ElConstants.SESSION_USERID), course
			// .getId(), 0));
			ScoreOperate.setScore(userid, ElConstants.DIAN_STUDY_DO);
		}
		//myCourse = studyCourseDao.getMyStudyCourse(userid, course.getId());
		myCourse = studyCourseDao.getMyStudyCourse(userid, course.getId(),classid);
		if (myCPages == null || myCPages.size() == 0) {
			setElmessage("�γ�������");
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "error_phone"; 
			}

			return "error";
		}
		
		course.setClassid(classid);//���õ��γ�
		//��һ��Ƶ����Ҫ�½ڼ�¼
//		if(OnlineUtil.checksStudyInfo(classid, courseid, cpid, getSession()))
//		{
//			course_study_to= "course_study_dysp" ;
//			return "course_study_logout";
//		}
//		OnlineUtil.setStudyInfo(classid, courseid, cpid, getSession());
		if(OnlineUtil.checksStudyInfo(classid, courseid, 0, getSession()))
		{
			course_study_to= "course_study_dysp" ;
			boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
			if(b==true){
				return "course_study_logout_phone"; 
			}
			return "course_study_logout";
		}
		OnlineUtil.setStudyInfo(classid, courseid, 0, getSession());
		//��¼1��ѧϰ��¼������ͳ��
//		studyCourseDao.addStudyCourseRecord(courseid,classid,0,userid);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_dysp_phone"; 
		}
		return "course_study_dysp";
	}

	/**�γ�ѧϰ����1.08�汾
	 * @return
	 * @throws ElException
	 */
	public String course_studysave() throws ElException {
		myCPage.setUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		//�������� ��Ҫ���зֿ�ͳ��,�û�ȡ��ѵ��id
		if(myCPage!=null){
			if(OnlineUtil.checksStudyInfo(myCPage.getStudyinfo_time(), getSession()))
			{
				//����γ̺��½ڵ�ѧϰʱ��
				studyCourseDao.saveMyCourseStudy(myCPage);
				//����ѧϰ��¼��ѧϰʱ��
				studyCourseDao.saveStudyCourseRecordPasstime(this.studyCourseRecordId, myCPage.getPasstime());
				printMsg("{'msg':'succ'}");
			}else{
				printMsg("{'msg':'error'}");
				//����ѧԱѧϰ��¼�˳�ʱ��
				this.updateStudyCourseRecordEndtime();
			}
		}else
			printMsg("{'msg':'error1'}");
		return null;
	}

	/**ѧϰ�˳�
	 * @return
	 * @throws ElException
	 */
	public String course_studyexit() throws ElException {
		if(myCPage!=null){
			myCPage.setUser(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			myCPage.setPasstime(myCPage.getPasstime()-2);
			if(OnlineUtil.checksStudyInfo(myCPage.getStudyinfo_time(), getSession())){
				studyCourseDao.saveMyCourseStudy(myCPage);
				OnlineUtil.removeStudyInfo(getSession());
				//����ѧϰ��¼��ѧϰʱ��
				studyCourseDao.saveStudyCourseRecordPasstime(this.studyCourseRecordId, myCPage.getPasstime());
				//�����һ�½�Ϊ���½�
				//������һ�½��û�ѧϰ
				studyCourseDao.updateStudyNextCpage(myCPage);
			}
			//����ѧԱѧϰ��¼�˳�ʱ��
			this.updateStudyCourseRecordEndtime();
		}
		return null;
	}
	/**�γ���Ϣ��ȡ
	 * @return
	 * @throws ElException
	 */
	public String course_studyinfo() throws ElException {
		if(myCPage!=null){
			myCourse = studyCourseDao.getMyStudyCourse(getSessionIntValue(ElConstants.SESSION_USERID), myCPage.getCourseid(),myCPage.getClassid());
			if(myCourse!=null){
				printMsg("{'process':'"+myCourse.getProcess_()+"'}");
			}
			else
				printMsg("error");
		}
		return null;
	}
	/**
	 * Description:�γ�ѧϰ��� 
	* @Version1.0 2012-7-22 ����02:55:53 by ����˴��wenyishun110@163.com������
	 * @return
	 * @throws ElException
	 */
	public String course_study() throws ElException {
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		
//		try {
//			Thread.sleep(500);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		//�������� ��Ҫ���зֿ�ͳ��,�û�ȡ��ѵ��id
  		String classidStr=getRequest().getParameter("classid");
  		if(classidStr == null || !classidStr.equals("")){
  			//course.classid
  			classidStr = getRequest().getParameter("course.classid");
  		}
//		int classid=course.getClassid()>0?course.getClassid():0;
  		//classid = this.getClassid();
  		if(classidStr!=null){
			classid=OnlineUtil.getIntValue(classidStr);
		}
  		
  		elclass = classDao.getClassById(classid);
  		if(elclass.getLearnByOrder() == 1){//˳��ѧϰ
  			//�ж�֮ǰ�Ŀγ��Ƿ��Ѿ�ѧ��
  			//ѧ��ǰһ�ſγ̣����ܽ�����һ�ſγ̵�ѧϰ
  			//˳��ѧϰ	==������courseid��classid��usereid == �� �жϵ�ǰ�γ���һ�ſγ��Ƿ��Ѿ�ѧϰ��ɣ�
  	  		//ѧϰ��ɺ󣬲���ѧϰ��ǰ�γ�
  			if(!initCompliance){//������ͨ��
  				if(!classDao.checkcoursecanlearn(course.getId(),classid,getSessionIntValue(ElConstants.SESSION_USERID))){
  	  	  			this.setElmessage("�Բ��𣬵�ǰ��ѵ��Ҫ��˳�����ѧϰ����û�������һ�ſγ̵�ѧϰ");
  				if(b==true){
  					return "error_phone"; 
  				}
  	  	  			return "error";
  	  	  		}
  			}
  		}
  		
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		//�ж��Ƿ���Ҫע��
		int isLogout=course == null ? 0 : course.getIsLogout();
		if(isLogout==1){
			int passtime=myCPage==null?0:myCPage.getPasstime();//��ʱ����
			myCPage=OnlineUtil.getSessionMycpage(getSession());
			myCPage.setUser(new ELUser(userid));
			myCPage.setPasstime(passtime<2?0:passtime-2);
			studyCourseDao.saveMyCourseStudy(myCPage);
			//OnlineUtil.removeStudyInfo(getSession());
			//����ѧϰ��¼��ѧϰʱ��
			studyCourseDao.saveStudyCourseRecordPasstime(myCPage.getStudyinfo_rid(), myCPage.getPasstime());
			OnlineUtil.removeStudyInfo(getSession());
		}
		
		
		
		myCourse = studyCourseDao.getMyStudyCourse(
				userid, course.getId(),classid);
		course = courseDao.getCourseById(course.getId());
		//���øÿγ���������ѵ��
		course.setClassid(classid);
		if(myCourse.getUser()!=null){
		if (!studyCourseDao.checkMyCourse(myCourse)) {
			// studyCourseDao.intoMyCourse(myc);
			setElmessage("��ûѡ��ÿγ�,�뵽��<a href='studentman.action' style='color:red;'>�ҵ�ѧϰ</a>����ѡ����ſγ�");
			return "course_noselected";
		}}
		//����Ƿ���ѧϰ��Ϣ��
		if(OnlineUtil.checksStudyInfo(getSession()))
		{
			//course_study_to= "course_study" ;
			if(b==true){
				return "course_study_logout_phone";
			}
			return "course_study_logout";
		}
		int cpid = 0;
		int ccpid=0;
		//�ж��Ƿ��׼�γ�
		if (course.getIslink() == ElConstants.COURSE_TYPE_BZKC) {
			//����½ڵ������Ϣ
			ccpid = coursePage == null || coursePage.getId() <= 0?-1:0;
			cpid = coursePage == null || coursePage.getId() <= 0 ? studyCourseDao
					.getMyLastCpage(userid, course.getId(),classid)
					: coursePage.getId();
			if (cpid <= 0){
				cpid = coursePageDao.getFirstCpId(course.getId());
				ccpid=-2;
			}
			coursePage = coursePageDao.getCp(cpid);
			//�ж��½�
			//���������½���Ϣ���жϸ��½ڵ�ǰһ�½�"�Ƿ�����"������ǣ���ֱ�Ӵ򿪱��½�
			//����Ƿ�  1����ǰһ�£��ж�ǰһ�����״̬������˲��ܴ򿪱��½ڣ�δ��ɣ���ʾ"��һ�½�δ���ѧϰ�������ܽ��뱾�½ڵ�ѧϰ"
			//		    2��ֱ�Ӵ򿪱��½�
			CoursePage beginCPage = null;
			if(coursePage!=null && coursePage.getSortid()!=1){//��ǰһ����Ϣ
				beginCPage = coursePageDao.getBeginCPage(coursePage.getCourse().getId(),coursePage.getSortid()-1);//ǰһ����Ϣ
				if(!initCompliance){//������ͨ��
					if(!coursePageDao.checkPageCanlearn(coursePage.getSortid()-1,coursePage.getCourse().getId(),getSessionIntValue(ElConstants.SESSION_USERID),beginCPage.getId())){
						//����Ϊǰһ��sortid��courseid��userid
						//�����  �γ��½ڱ�COURSE_PAGE���½ڷ����STUDY_CPAGE
						this.setElmessage("��һ�½�δ���ѧϰ�������ܽ��뱾�½ڵ�ѧϰ");
						if(b==true){
							return "error_phone"; 
						}
						return "error";
						
					}
				}
			}
			//ͬ���½�
			if(coursePage.getIslive()==1){
				course.setIslink( ElConstants.COURSE_TYPE_TBKT);
				course.setRoom(new Rooms(coursePage.getRoom().getId()));
			}
			
			
			//����ǵ�һ�ν����׼�γ��ڴ�ע�����е��½�
//			if(ccpid==-2){//���ǵ�������ӵĿγ��½ڣ����Դ��ж�ע��
				//�Ȳ�ÿγ̵������½�
				List<CoursePage> clist=courseDao.getCourseAllCpage(course.getId());
				for(int i=0;i<clist.size();i++){
					if (!studyCourseDao.checkMyCPage(new MyCPage(
							userid,clist.get(i).getId()),classid)) {
						studyCourseDao.intoMyCPage(new MyCPage(userid,clist.get(i).getId()),classid);
					}
				}
//			}
			if (null != coursePage) {
				myCPage = studyCourseDao.getMyCPage(
						userid, coursePage
								.getId(),classid);
				myCPage.setCpage(coursePage);
//				myCPages = studyCourseDao.listCpsbyCUid(course.getId(),//�½ڹ�����ϰ
//						userid,classid);
				myCPages = studyCourseDao.listCpsbyCUid_wjm(course.getId(),//�γ̵��½ڣ�����������
						userid,classid);
				
			} else {
				setElmessage("û�ҵ��γ��½�,��ȷ�Ͽγ��Ƿ�������ַ�Ƿ���ȷ��");
				if(b==true){
					return "error_phone"; 
				}
				return "error";
			}
		if(ccpid==-1){
				//���ر�׼�γ���ҳ
			if(b==true){
				return "course_study_bzkc_index_phone";
			}
				return "course_study_bzkc_index";
			}
		}else if(course.getIslink() == ElConstants.COURSE_TYPE_TBKT){
			if (course.getTeacherId() != userid) {//���ǽ�ʦ
				if (!omDao.moderatorHasLogin(course.getRoom().getId())) {//��ʦδ����
					setElmessage("��������δ���룡���Ժ�");
					if(b==true){
						return "error_phone"; 
					}
					return "error";
				} 
			}
		}
//		if(OnlineUtil.checksStudyInfo(classid,course.getId(), cpid, getSession()))
//		{
//			setElmessage("���Ѿ����˸ÿγ̣�������ͬʱ����������!");
//			return "error";
//		}	
		//��¼1��ѧϰ��¼������ͳ��
		studyCourseRecordId=studyCourseDao.addStudyCourseRecord(course.getId(),classid,cpid,userid);
		OnlineUtil.setStudyInfo(classid, course.getId(), cpid,studyCourseRecordId, getSession());
		if (course.getIslink() == ElConstants.COURSE_TYPE_WBKC) {//�ⲿ�γ�
			OnlineUtil.setStudyInfo(classid, course.getId(), cpid, getSession());
			if(b==true){
				return "course_study_wbkc_phone";
			}
			return "course_study_wbkc";
		}
//		else if (course.getIslink() == ElConstants.COURSE_TYPE_ZHWB)
//			// return "course_linkc_studyindex";
//			return "course_study_zhwb";
		else if (course.getIslink() == ElConstants.COURSE_TYPE_DYSP){//��һ��Ƶ
			myCPages = studyCourseDao.listCpsbyCUid(course.getId(),
					userid,classid);
//			OnlineUtil.setStudyInfo(classid, course.getId(), cpid, getSession());
			if(b==true){
				return "course_study_dysp_phone";
			}
			return "course_study_dysp";
		}else if (course.getIslink() == ElConstants.COURSE_TYPE_TBKT){//ͬ������
			omDao.setModeratorHasLoginOut(course.getRoom().getId(), 1);
			getSession().setAttribute("roomid", course.getRoom().getId()); 
			getSession().setAttribute("teacherId", userid);//��ʦid
//			OnlineUtil.setStudyInfo(classid, course.getId(), cpid, getSession());
			if(b==true){
				return "course_study_tbkt_phone";
			}
			return "course_study_tbkt";
		}else if(course.getIslink()==ElConstants.COURSE_TYPE_SCORM){//scorm
			userid = getSessionIntValue(ElConstants.SESSION_USERID);
			classidStr=getRequest().getParameter("classid");
			classid=course.getClassid()>0?course.getClassid():0;
			if(classidStr!=null){
				classid=OnlineUtil.getIntValue(classidStr);
			}
			course = courseDao.getCourseById(course.getId());
			StudyScormDao ssd =((StudyScormDao)SpringContextUtil.getBean("studyScormDao"));
			ssd.registerCourse(userid+"", course.getExurl(),classid+"");
			scormcourse = ssd.intoCourse(course.getExurl(), userid+"",classid+"", scormcourse!=null?scormcourse.getScoid():null, scormcourse!=null?scormcourse.getNowScoid():null, scormcourse!=null?scormcourse.getNavitype():null);
			
			
////			OnlineUtil.setStudyInfo(classid, course.getId(), cpid, getSession());
//			StudyScormDao ssd =((StudyScormDao)SpringContextUtil.getBean("studyScormDao"));
//			ssd.registerCourse(userid+"", course.getExurl(),classid+"");
//			scormcourse = ssd.intoCourse(course.getExurl(), userid+"",classid+"", scormcourse!=null?scormcourse.getScoid():null, scormcourse!=null?scormcourse.getNowScoid():null, scormcourse!=null?scormcourse.getNavitype():null);
			if(b==true){
				return "course_study_scorm_phone";
			}
			return "course_study_scorm";
		}else{//��׼�γ�
			//����γ��½ڽ�ҵ��ʽ���
//				int getcredit=coursePage.getGetcredit();
//				int ispassed=myCPage.getPassed2();
//				if(getcredit==1){
//					//ѧ��
//					//���ô���
//				}else if(getcredit==2){
//					//����
//					if(ispassed==1){
//						//����,���ý��100% 
//						//studyCourseDao.saveMyCPage(myCPage,classid,3,coursePage.getDuring());
//					}else{
//						//ҳ����ֹͣ
//						status=1;
//					}
//				}else{
//					//ѧ���ҿ���
//					//ÿ�α����ȼ�30��������ȼ�1��
//					//��ѯ��ǰ��ѧϰ��ȣ�����50%�� ��ֹͣ
//					//if(ispassed==0){
////						if(myCPage.getPasstime()>=coursePage.getDuring()*60/2){
////							status=1;
////						}
//					//}
//					status=2;//�ý�ҵ��ʽʵ�ʽ�Ȳ���
//				}
			//�ϵ���ѧ
//			studyCourseDao.cPageFinishSet(userid, cpid, classid);
//			studyCourseDao.updateStudyCourseRecordStatus(course.getId(), classid, cpid, userid, 0, new Timestamp(System.currentTimeMillis()));
//			OnlineUtil.setStudyInfo(classid, course.getId(), cpid, getSession());
			if(b==true){
				if (coursePage != null && coursePage.getType() == 1) {//����Ƶ�γ�
					return "cpage_study_csp_phone";
				}
				if (coursePage != null && coursePage.getType() == 3) {//�ⲿ�γ��½�
					System.out.println("�ֻ�˿γ��½�");
					return "cpage_study_wbkc_phone";
				}
				if (coursePage != null && coursePage.getType() == 4) {//������ѧϰϵͳ
					return "cpage_study_spxx1_phone";
				}
				if (coursePage != null && coursePage.getType() == 5) {//������ѧϰϵͳ
					return "cpage_study_spxx_phone";
				}
				if (coursePage != null && coursePage.getType() == 6) {//�ⲿ��Ƶѧϰ
					return "cpage_study_wbspxx_phone";
				}
				return "cpage_study_twspjy_phone";//ͼ�ģ���Ƶ���Ƶ�γ̡�
			}
			if (coursePage != null && coursePage.getType() == 1) {//����Ƶ�γ�
				if(!studyCourseDao.isRecord(userid,course.getId())){
					System.out.println("ִ�д����");
					studyCourseDao.insertOneRecord(userid, course.getId());
					System.out.println("ִ�д����1");
				}
				return "cpage_study_csp";
			}
			if (coursePage != null && coursePage.getType() == 3) {//�ⲿ�γ��½�
				if(!studyCourseDao.isRecord(userid,course.getId())){
					System.out.println("ִ�д����");
					studyCourseDao.insertOneRecord(userid, course.getId());
					System.out.println("ִ�д����1");
				}
				return "cpage_study_wbkc";
			}
			if (coursePage != null && coursePage.getType() == 4) {//������ѧϰϵͳ
				elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
				examRoom = eroomDao.getExamRoom(course.getId(), elclass.getId());//�γ̽�ҵ����
				
				MyExamPaper myExamPaper = null;
				myCPage.setCourseid((course!=null && course.getId()>0)?course.getId():0);
				//�½ڻ�ȡ�����Ծ���Ϣ�����������
				myCPage = eroomDao.getBindingExamRooms(myCPage,elUser.getId());
				
				//�½ڰ󶨵�ÿ��������ȡ�����Ϣ==>������߷֡�ʱ�併���ȡ��һ��
				if(myCPage.getExamRooms()!=null ){
					boolean canExam = false;
					for(int i=0;i<myCPage.getExamRooms().size();i++){
						myExamPaper = studyQuizDao.getMyExampaper(elUser.getId(), myCPage.getExamRooms().get(i).getId(), myCPage.getExamRooms().get(i).getExamPaper().getId());
						myCPage.getExamRooms().get(i).setMyExamPaper(myExamPaper);
						//����½ڿ������жϿɿ����߲��ɿ�
						if(myCPage.getExamRooms().get(i).getSortid()>1){
							canExam = eroomDao.setExamRoomCanExam(myCPage.getExamRooms().get(i),course.getId(),elUser.getId());
						}else{
							if(myCPage.isPassed()){
								canExam = true;
							}
						}
						myCPage.getExamRooms().get(i).setCanExam(canExam==true?1:0);
					}
				}
				
				if(examRoom!=null && examRoom.getId()>0){
					//�жϵȼ��Ƿ��ڶ���ǰ
					ELUserClassification elUserClassification = classificationDao.getElUserClassificationByUserid(getSessionIntValue(ElConstants.SESSION_USERID), classificationDao.getRoomid());
					if(elclass!=null && elclass.getName().compareTo(elUserClassification.getName())<0){
						initCompliance = true;
					}
					myCPages = studyCourseDao.listCpsbyCUid_wjm(course.getId(),
							elUser.getId(),elclass.getId());
					myCPages = myCPages == null ? new ArrayList<MyCPage>():myCPages;
					//�ж�ÿ���½��Ƿ����ѧϰ������½�˳��sortid
					//�������Ƿ��ѧ����
					
					CoursePage coursePage = null; 
					for(MyCPage mycp:myCPages){
						coursePage = mycp.getCpage();
						//�ж��½�
						//���������½���Ϣ���жϸ��½ڵ�ǰһ�½�"�Ƿ�����"������ǣ���ֱ�Ӵ򿪱��½�
						//����Ƿ�  1����ǰһ�£��ж�ǰһ�����״̬������˲��ܴ򿪱��½ڣ�δ��ɣ���ʾ"��һ�½�δ���ѧϰ�������ܽ��뱾�½ڵ�ѧϰ"
						//		    2��ֱ�Ӵ򿪱��½�
						CoursePage beginCPage = null;
						if(coursePage.getSkipable() == 0){//�����ж�
							if(coursePage.getSortid()>1){
								beginCPage = coursePageDao.getBeginCPage(course.getId(),mycp.getCpage().getSortid()-1);//��ǰһ����Ϣ
								if(coursePageDao.checkPageCanlearn(mycp.getCpage().getSortid()-1,course.getId(),elUser.getId(),beginCPage.getId())){
									mycp.setCanLearn(1);
								}
							}else{
								mycp.setCanLearn(1);
							}
						}else{
							mycp.setCanLearn(1);
						}
					}
					
					if(mycourse == null)	mycourse = new MyCourse();//�ҵ�ѧϰ�γ�
					if(course!=null && course.getId()>0 ){
						//����ѧϰ�γ̵��½��б�
						mycourse.setCourse(course);
						List<ExamPaper> examPapers = examPaperDao.listEroomExamPaper(examRoom.getId());
						for (int i = 0; i < examPapers.size(); i++) {
							if (examPapers.get(i).getStatus() != 1) {
								if (!studyQuizDao.checkStudyExamPaper(elUser.getId(), examPapers.get(i)
										.getId(), examRoom.getId(), elclass.getId())) {
									// ��Ӹ�ѧԱ�� ѧԱ�Ծ����
									studyQuizDao.addStudyExamPaper(elUser.getId(), examPapers.get(i)
											.getId(), examRoom.getId(), elclass.getId());
								}
							}
						}
						
						//���ÿγ̽�ҵ�������Ծ���Ϣ
						epid = studyQuizDao.getExamRoomid(examRoom.getId(),elUser.getId());
						if(epid != 0){
							//�����Ծ���Ϣ
							mycourse.setMyExamPaper(studyQuizDao.getMyExampaper(elUser.getId(), examRoom.getId(), epid));
						}
						//�������ʱ��ͷ���
						//mycourse = statisticQuizDao.getFinishtimeByScore(elclass.getId(),elUser.getId(),mycourse);
						
						//�ж��½��Ƿ�ͨ������myroom�Ƿ���Կ���  
						if(examRoom != null){
							if(myroom == null)	myroom = new MyRoom();//�ҵĿ���
							myroom.setExamroom(examRoom);
							//����
							//�γ̵Ľ�ҵ�����Ƿ��ܿ����ѽ����жϿγ��Ƿ�ͨ��
							myroom.setCanExam(courseDao.checkCpagesIsAllPass(elclass.getId(),elUser.getId(),course.getId()));
							mycourse.setMyRoom(myroom);
						}
						//���ÿγ��Ƿ�ͨ��
						mycourse.setPassed(courseDao.checkCourseIsPass(elclass.getId(),elUser.getId(), course.getId()));
						mycourse.setExamPass(eroomDao.getIsPass(elUser.getId(), examRoom.getId()));
					}
					return "cpage_study_spxx";
				}else{
					return "cpage_study_spxx_1";
				}
				
			}
//			if (coursePage != null && coursePage.getType() == 5) {//������ѧϰϵͳ2
//				return "cpage_study_spxx";
//			}
			if (coursePage != null && coursePage.getType() == 5) {//��Ƶѧϰ
				return "cpage_study_kpxx";
			}
			//�ⲿ����ѧϰ
			if (coursePage != null && coursePage.getType() == 6) {//��Ƶѧϰ
				return "cpage_study_wbkpxx";
			}
			//20140325�޸�
			if (coursePage != null && coursePage.getType() == 7) {//�ʻ���Ƶѧϰ
//				
				elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
				examRoom = eroomDao.getExamRoom(course.getId(), elclass.getId());//�γ̽�ҵ����
				
				MyExamPaper myExamPaper = null;
				myCPage.setCourseid((course!=null && course.getId()>0)?course.getId():0);
				//�½ڻ�ȡ�����Ծ���Ϣ�����������
				myCPage = eroomDao.getBindingExamRooms(myCPage,elUser.getId());
				
				//�½ڰ󶨵�ÿ��������ȡ�����Ϣ==>������߷֡�ʱ�併���ȡ��һ��
				if(myCPage.getExamRooms()!=null ){
					boolean canExam = false;
					for(int i=0;i<myCPage.getExamRooms().size();i++){
						myExamPaper = studyQuizDao.getMyExampaper(elUser.getId(), myCPage.getExamRooms().get(i).getId(), myCPage.getExamRooms().get(i).getExamPaper().getId());
						myCPage.getExamRooms().get(i).setMyExamPaper(myExamPaper);
						//����½ڿ������жϿɿ����߲��ɿ�
						if(myCPage.getExamRooms().get(i).getSortid()>1){
							canExam = eroomDao.setExamRoomCanExam(myCPage.getExamRooms().get(i),course.getId(),elUser.getId());
						}else{
							if(myCPage.isPassed()){
								canExam = true;
							}
						}
						myCPage.getExamRooms().get(i).setCanExam(canExam==true?1:0);
					}
				}
				
				if(examRoom!=null && examRoom.getId()>0){
					//�жϵȼ��Ƿ��ڶ���ǰ
					ELUserClassification elUserClassification = classificationDao.getElUserClassificationByUserid(getSessionIntValue(ElConstants.SESSION_USERID), classificationDao.getRoomid());
					if(elclass!=null && elclass.getName().compareTo(elUserClassification.getName())<0){
						initCompliance = true;
					}
					myCPages = studyCourseDao.listCpsbyCUid_wjm(course.getId(),
							elUser.getId(),elclass.getId());
					myCPages = myCPages == null ? new ArrayList<MyCPage>():myCPages;
					//�ж�ÿ���½��Ƿ����ѧϰ������½�˳��sortid
					//�������Ƿ��ѧ����
					
					CoursePage coursePage = null; 
					for(MyCPage mycp:myCPages){
						coursePage = mycp.getCpage();
						//�ж��½�
						//���������½���Ϣ���жϸ��½ڵ�ǰһ�½�"�Ƿ�����"������ǣ���ֱ�Ӵ򿪱��½�
						//����Ƿ�  1����ǰһ�£��ж�ǰһ�����״̬������˲��ܴ򿪱��½ڣ�δ��ɣ���ʾ"��һ�½�δ���ѧϰ�������ܽ��뱾�½ڵ�ѧϰ"
						//		    2��ֱ�Ӵ򿪱��½�
						CoursePage beginCPage = null;
						if(coursePage.getSkipable() == 0){//�����ж�
							if(coursePage.getSortid()>1){
								beginCPage = coursePageDao.getBeginCPage(course.getId(),mycp.getCpage().getSortid()-1);//��ǰһ����Ϣ
								if(coursePageDao.checkPageCanlearn(mycp.getCpage().getSortid()-1,course.getId(),elUser.getId(),beginCPage.getId())){
									mycp.setCanLearn(1);
								}
							}else{
								mycp.setCanLearn(1);
							}
						}else{
							mycp.setCanLearn(1);
						}
					}
					
					if(mycourse == null)	mycourse = new MyCourse();//�ҵ�ѧϰ�γ�
					if(course!=null && course.getId()>0 ){
						//����ѧϰ�γ̵��½��б�
						mycourse.setCourse(course);
						List<ExamPaper> examPapers = examPaperDao.listEroomExamPaper(examRoom.getId());
						for (int i = 0; i < examPapers.size(); i++) {
							if (examPapers.get(i).getStatus() != 1) {
								if (!studyQuizDao.checkStudyExamPaper(elUser.getId(), examPapers.get(i)
										.getId(), examRoom.getId(), elclass.getId())) {
									// ��Ӹ�ѧԱ�� ѧԱ�Ծ����
									studyQuizDao.addStudyExamPaper(elUser.getId(), examPapers.get(i)
											.getId(), examRoom.getId(), elclass.getId());
								}
							}
						}
						
						//���ÿγ̽�ҵ�������Ծ���Ϣ
						epid = studyQuizDao.getExamRoomid(examRoom.getId(),elUser.getId());
						if(epid != 0){
							//�����Ծ���Ϣ
							mycourse.setMyExamPaper(studyQuizDao.getMyExampaper(elUser.getId(), examRoom.getId(), epid));
						}
						//�������ʱ��ͷ���
						//mycourse = statisticQuizDao.getFinishtimeByScore(elclass.getId(),elUser.getId(),mycourse);
						
						//�ж��½��Ƿ�ͨ������myroom�Ƿ���Կ���  
						if(examRoom != null){
							if(myroom == null)	myroom = new MyRoom();//�ҵĿ���
							myroom.setExamroom(examRoom);
							//����
							//�γ̵Ľ�ҵ�����Ƿ��ܿ����ѽ����жϿγ��Ƿ�ͨ��
							myroom.setCanExam(courseDao.checkCpagesIsAllPass(elclass.getId(),elUser.getId(),course.getId()));
							mycourse.setMyRoom(myroom);
						}
						//���ÿγ��Ƿ�ͨ��
						mycourse.setPassed(courseDao.checkCourseIsPass(elclass.getId(),elUser.getId(), course.getId()));
						mycourse.setExamPass(eroomDao.getIsPass(elUser.getId(), examRoom.getId()));
					}
				}
				return "cpage_study_chspxx";
			}
			return "cpage_study_twspjy";//ͼ�ģ���Ƶ���Ƶ�γ̡�
		}

	}
	
	
	/**
	 * ��Ͽγ�ѧϰ���
	 * @return
	 * @throws ElException
	 */
	public String mixcourse_studyInit()throws ElException{
		course = courseDao.getCourseById(course.getId());
		//�γ̶�Ӧ�½�
		cpages = courseDao.getPagesByCourseid(course.getId());
		return "mixcourse_studyInit";
	}
	
	public String cpagelist() throws ElException{
		course = courseDao.getCourseById(course.getId());
		//�γ̶�Ӧ�½�
		cpages = courseDao.getPagesByCourseid(course.getId());
		//�½ڶ�Ӧ�ĸ���
		stuffs = courseDao.getCpageStuffsByCoursid(course.getId());
		//�õ��û�����
		usercount = courseCommentDao.getEluserByCourseid(course.getId()).size();
		usercount = usercount-1;
		if(mod.equals("course")){
			return "cpagelist";
		}
		if(mod.equals("stuff")){
			return "cpagestuff";
		}
		return "error";
	}

	public String courseNote_addInit() throws ElException {
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseNote_add_phone"; 
		}
		return "courseNote_add";
	}

	public String newcourseNote_addInit() throws ElException {
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "newcourseNote_add_phone"; 
		}
		return "newcourseNote_add";
	}

	public String courseNote_add() throws ElException {
		course = courseDao.getCourseById(course.getId());
		cnote.setCourse(course);
		cnote.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		// ScoreOperate.setScore(getSessionIntValue(ElConstants.SESSION_USERID),
		// ElConstants.SCORE_NOTE_DO);
		// cnote.setScore(course_study_notescore(course, cnote));
		studyCourseDao.addCnote(cnote);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseNote_add_success_phone"; 
		}
		return "courseNote_add_success";
	}

	public String courseNote_list() throws ElException {
		cnotes = studyCourseDao.listCnotes(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseNote_list_phone"; 
		}
		return "courseNote_list";
	}

	public String newcourseNote_list() throws ElException {
		cnotes = studyCourseDao.listCnotes(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "newcourseNote_list_phone"; 
		}
		return "newcourseNote_list";
	}

	public String newcourseNote_delete() throws ElException {
		studyCourseDao.deleteCnote(cnote.getId());
		cnotes = studyCourseDao.listCnotes(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "newcourseNote_list_phone"; 
		}
		return "newcourseNote_list";
	}

	public String courseNote_delete() throws ElException {
		studyCourseDao.deleteCnote(cnote.getId());
		cnotes = studyCourseDao.listCnotes(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "courseNote_delete_phone"; 
		}
		return "courseNote_delete";
	}

	public String course_study_notelist() throws ElException {
		course = courseDao.getCourseById(course.getId());
		cnotes = studyCourseDao.listCnotes(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_notelist_phone"; 
		}
		return "course_study_notelist";
	}

	public String course_study_noteAddInit() throws ElException {
		course = courseDao.getCourseById(course.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_noteAdd_phone"; 
		}
		return "course_study_noteAdd";
	}

	private float course_study_notescore(Course course, CourseNote cnote) {
		// ���ʼ�����/��׼�ʼ�����X �γ�ѧ�֡��ٽ�����
		int cnote_size = cnote == null ? 0 : (cnote.getContent() == null ? 0
				: cnote.getContent().length());
		int c_size = course.getNotenumber();
		float zishu = c_size == 0 ? 1
				: (cnote_size * 1.0f / c_size * 1.0f > 1 ? 1 : cnote_size
						* 1.0f / c_size * 1.0f);
		long chijiao = (course.getNotedate().getTime() - System
				.currentTimeMillis())
				/ (1000 * 60 * 60 * 24l);
		chijiao = chijiao >= 0 ? 0 : chijiao;
		float score = zishu * course.getCredit() + chijiao;
		return score < 0 ? 0f : score;
	}

	public String course_study_noteAdd() throws ElException {
		course = courseDao.getCourseById(course.getId());
		cnote.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		cnote.setScore(0);
		studyCourseDao.addCnote(cnote);
		// ScoreOperate.setScore(getSessionIntValue(ElConstants.SESSION_USERID),
		// ElConstants.SCORE_NOTE_DO);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_notelist_phone"; 
		}
		return "course_study_notelist";
	}

	public String course_study_noteAlterInit() throws ElException {
		course = courseDao.getCourseById(course.getId());
		cnote = studyCourseDao.getCnoteByid(cnote.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_noteAlter_phone"; 
		}
		return "course_study_noteAlter";
	}

	public String course_study_noteAlter() throws ElException {
		course = courseDao.getCourseById(course.getId());
		studyCourseDao.alterCnote(cnote);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_notelist_phone"; 
		}
		return "course_study_notelist";
	}

	public String course_study_notesubmit() throws ElException {
		course = courseDao.getCourseById(course.getId());
		cnote = studyCourseDao.getCnoteByid(cnote.getId());
		cnote.setScore(course_study_notescore(course, cnote));
		studyCourseDao.submitCnotes(cnote);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_notelist_phone"; 
		}
		return "course_study_notelist";
	}

	public String course_study_noteDelete() throws ElException {
		studyCourseDao.deleteCnote(cnote.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_study_notelist_phone"; 
		}
		return "course_study_notelist";
	}

	public String mynotecourselist() throws ElException {
		myCourses = studyCourseDao.listMyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyCourseDao
				.listMyCourseSize(getSessionIntValue(ElConstants.SESSION_USERID));
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "mynotecourselist_phone"; 
		}
		return "mynotecourselist";
	}

	public String mycourselist() throws ElException {
		myCourses = studyCourseDao.listMyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyCourseDao
				.listMyCourseSize(getSessionIntValue(ElConstants.SESSION_USERID));
		//����һ����ǰʱ��
		//Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"); 
		String now=sdf.format(new Date());
		getRequest().setAttribute("now", now);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "mycourselist_phone"; 
		}
		return "mycourselist";
	}

	public String mycourseAlllist() throws ElException {
		myCourses = studyCourseDao.listMyAllCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = studyCourseDao
				.listMyAllCourseSize(getSessionIntValue(ElConstants.SESSION_USERID));
//		li=studyCourseDao.listMyAllCourse(getSessionIntValue(ElConstants.SESSION_USERID));
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		for(int i=0;i<myCourses.size();i++){
			int courseid = myCourses.get(i).getCourse().getId();
			Course c = courseDao.getCourseById(courseid);
			if(c.getExurl()!=null&&c.getExurl().contains("Course-")){
				int finish = courseDao.getUserSCInfo(userid+"",c.getExurl(),"completed");
				int all = courseDao.getSCItemInfo(c.getExurl())-1;
				myCourses.get(i).setProcess((float)finish/(float)all*100);
				int courseduring = myCourses.get(i).getCourse().getDuring();
				double time = (courseduring*((float)finish/(float)all));
				int passtime = (int)time;
				myCourses.get(i).setPasstime(passtime);
			}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "mycoursealllist_phone"; 
		}
		return "mycoursealllist";
	}
	
	
	public String myobcourse() throws ElException{
		classid = this.getClassid();
		myCourses = studyCourseDao.listMyCourseByClassid(getSessionIntValue(ElConstants.SESSION_USERID),classid,getPageNow(),getPageSize());
		count = studyCourseDao.listMyObCourseSize(getSessionIntValue(ElConstants.SESSION_USERID), classid);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "myobcourse_phone"; 
		}
		return "myobcourse";
	}
	

	public String mycourselistbystatus() throws ElException {

		myCourses = studyCourseDao.listMyCourse(
				getSessionIntValue(ElConstants.SESSION_USERID), myCourse
						.getStatus(), getPageNow(), getPageSize());
		count = studyCourseDao.listMyCourseSize(
				getSessionIntValue(ElConstants.SESSION_USERID), myCourse
						.getStatus());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "mycourselistbystatus_phone"; 
		}
		return "mycourselistbystatus";
	}

	public String course_phlist() throws ElException {
		courses = studyCourseDao.listPhCourse(getPageNow(), getPageSize());
		count = studyCourseDao.listPhCourseSize();
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_phlist_phone"; 
		}
		return "course_phlist";
	}

	public String submitAppalyCourse_front() throws ElException {
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		if (null != course)
			if (!studyCourseDao.checkMyCourse(new MyCourse(userid, course
					.getId()))) {
				studyCourseDao.studyApplyCourse(userid, course.getId());
				ScoreOperate.setScore(userid, ElConstants.SCORE_COURSE_APPLY);
			}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "submitAppalyCourse_front_phone"; 
		}
		return "submitAppalyCourse_front";
	}

	public String study_course_delete() throws ElException {
		if (studyCourseDao.study_course_delete_check(
				getSessionIntValue(ElConstants.SESSION_USERID), course.getId())) {
			setElmessage("�ÿγ�����ɾ����ˣ�");
		} else
			studyCourseDao.study_course_delete(
					getSessionIntValue(ElConstants.SESSION_USERID), course
							.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "mycourselist_phone"; 
		}
		return "mycourselist";
	}

	public String course_tandsp() throws ElException {
		course = courseDao.getCourseById(course.getId());
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_tandsp_phone"; 
		}
		return "course_tandsp";
	}

	/**
	 * �μ�������
	 * 
	 * @return
	 */
	public String course_appendix_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		String name = (course == null) ? "" : course.getName();
		courses = frontDao.listCourseByName(getPageNow(), getPageSize(), name);
		count = frontDao.listCourseByNameSize(name);
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "course_appendix_list_phone"; 
		}
		return "course_appendix_list";
	}
	
	/**����½�ѧϰ״̬
	 * @return
	 * @throws ElException
	 */
	public String course_checkPass() throws ElException {
		myCPage.setUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		//�������� ��Ҫ���зֿ�ͳ��,�û�ȡ��ѵ��id
//		if(myCPage!=null){
//			if(OnlineUtil.checksStudyInfo(myCPage.getStudyinfo_time(), getSession()))
//			{
//				//����γ̺��½ڵ�ѧϰʱ��
//				studyCourseDao.saveMyCourseStudy(myCPage);
//				//����ѧϰ��¼��ѧϰʱ��
//				studyCourseDao.saveStudyCourseRecordPasstime(this.studyCourseRecordId, myCPage.getPasstime());
//				printMsg("{'msg':'succ'}");
//			}else{
//				printMsg("{'msg':'error'}");
//				//����ѧԱѧϰ��¼�˳�ʱ��
//				this.updateStudyCourseRecordEndtime();
//			}
//		}else
//			printMsg("{'msg':'error1'}");
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		int flag = studyCourseDao.checkPass(userid, myCPage);
		//��ȡ����½ڵ�id��sortid
		ExamRoom examRoom = eroomDao.getFinishExamRoom(userid,course.getId(), myCPage);
		System.out.println("{'msg':"+flag+",'finishCpExamSortid':"+examRoom.getSortid()+",'finishCpExamid':"+examRoom.getId()+"}");
		printMsg("{'msg':"+flag+",'finishCpExamSortid':"+examRoom.getSortid()+",'finishCpExamid':"+examRoom.getId()+"}");
		return null;
	}
	
	public String course_checkExamRoom() throws ElException {
		myCPage.setUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		myCPages = studyCourseDao.myCPages(userid,myCPage);
		if(myCPages.size()!=0&&myCPages.get(0).getExamPaper().getId()!=0){
			printMsg("{'epid':'"+myCPages.get(0).getExamPaper().getId()+"','roomid':'"+myCPages.get(0).getExamRoom().getId()+"','cpid':'"+myCPages.get(0).getCpid()+"','flag':'1'}");
			System.out.println("{'epid':'"+myCPages.get(0).getExamPaper().getId()+"','roomid':'"+myCPages.get(0).getExamRoom().getId()+"','cpid':'"+myCPages.get(0).getCpid()+"','flag':'1'}");
		}else{
			//ֻ��Ҫ��ȡ��һ���½�id����
			int nextCpid = studyCourseDao.getNextCpid(myCPage.getClassid(),myCPage.getCourseid(),myCPage.getCpid());
			printMsg("{'cpid':'"+nextCpid+"','flag':'0'}");
			System.out.println("{'cpid':'"+nextCpid+"','flag':'0'}");
		}
		
		return null;
	}
	
	/**
	 * ���ܸ�����--��ѧϰ��ʼ
	 * @return
	 * @throws ElException
	 */
	public String intelligent_learn_begin() throws ElException{
		myCPage = myCPage == null?new MyCPage():myCPage;
		myCPage.setUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		if(myCPage.getClassid()>0 && myCPage.getCourseid()>0 && myCPage.getCpid()>0){
			IntelligentWeekUtil.learnBegin(userid, myCPage.getClassid(), myCPage.getCourseid(), myCPage.getCpid(),this.studyCourseRecordId);
		}
		return null;
	}
	/**
	 * ���ܸ�����--��ѧϰ����
	 * @return
	 * @throws ElException
	 */
	public String intelligent_learn_end() throws ElException{
		myCPage = myCPage == null?new MyCPage():myCPage;
		myCPage.setUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		if(myCPage.getClassid()>0 && myCPage.getCourseid()>0 && myCPage.getCpid()>0){
			IntelligentWeekUtil.learnEnd(userid, myCPage.getClassid(), myCPage.getCourseid(), myCPage.getCpid(),this.studyCourseRecordId);
		}
		//��ǰϵͳ��ѵ���Ĭ��idΪ1
		PeixunBatch peixunBatch = peixunBatchDao.getPeixunBatchById(1);
//		//������ѵ��Ľ��
//		classDao.updateClassProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
		//������ѵ��ν��
		peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
		return null;
	}


	private List<CourseServer> cservers;

	// private CourseServer cserver;

	// public CourseServer getCserver() {
	// return cserver;
	// }
	//
	// public void setCserver(CourseServer cserver) {
	// this.cserver = cserver;
	// }

	public List<CourseServer> getCservers() {
		return cservers;
	}

	public void setCservers(List<CourseServer> cservers) {
		this.cservers = cservers;
	}

	public String cserver_list() throws ElException {
		cservers = courseDao.listCourseServer();
		return "cserver_list";
	}

	public List<MyCourse> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}

	public CoursePage getCoursePage() {
		return coursePage;
	}

	public void setCoursePage(CoursePage coursePage) {
		this.coursePage = coursePage;
	}

	public CoursePageDao getCoursePageDao() {
		return coursePageDao;
	}

	public void setCoursePageDao(CoursePageDao coursePageDao) {
		this.coursePageDao = coursePageDao;
	}

	public List<CoursePage> getCoursePages() {
		return coursePages;
	}

	public void setCoursePages(List<CoursePage> coursePages) {
		this.coursePages = coursePages;
	}

	public List<MyCPage> getMyCPages() {
		return myCPages;
	}

	public void setMyCPages(List<MyCPage> myCPages) {
		this.myCPages = myCPages;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public MyCourse getMyCourse() {
		return myCourse;
	}

	public void setMyCourse(MyCourse myCourse) {
		this.myCourse = myCourse;
	}

	public StatisticDao getStatisticDao() {
		return statisticDao;
	}

	public void setStatisticDao(StatisticDao statisticDao) {
		this.statisticDao = statisticDao;
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

	public String getCourse_study_to() {
		return course_study_to;
	}

	public void setCourse_study_to(String course_study_to) {
		this.course_study_to = course_study_to;
	}
	/**
	 * ����ѧԱѧϰ��¼�˳�ʱ���״̬
	 * @return
	 * @throws ElException
	 */
	public String updateStudyCourseRecordEndtime() throws ElException{
		//studyCourseDao.updateStudyCourseRecordStatus(myCPage.getCourseid(), myCPage.getClassid(), myCPage.getCpid(), getSessionIntValue(ElConstants.SESSION_USERID), 0, new Timestamp(System.currentTimeMillis()));
		studyCourseDao.updateStudyCourseRecordStatusByid(this.studyCourseRecordId, 0, new Timestamp(System.currentTimeMillis()));
		return null;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
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
//------------------wsj1103�޸�-----------------------------------------------
	/**
	 * Description:�γ�ѧϰ��� 
	* @Version1.0 2012-7-22 ����02:55:53 by ����˴��wenyishun110@163.com������
	 * @return
	 * @throws ElException
	 */
	public String course_study_wsj() throws ElException {
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		
//		try {
//			Thread.sleep(500);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		//�������� ��Ҫ���зֿ�ͳ��,�û�ȡ��ѵ��id
  		String classidStr=getRequest().getParameter("classid");
  		if(classidStr == null || !classidStr.equals("")){
  			//course.classid
  			classidStr = getRequest().getParameter("course.classid");
  		}
//		int classid=course.getClassid()>0?course.getClassid():0;
  		//classid = this.getClassid();
  		if(classidStr!=null){
			classid=OnlineUtil.getIntValue(classidStr);
		}
  		
//  		elclass = classDao.getClassById(classid);
//  		if(elclass.getLearnByOrder() == 1){//˳��ѧϰ
//  			//�ж�֮ǰ�Ŀγ��Ƿ��Ѿ�ѧ��
//  			//ѧ��ǰһ�ſγ̣����ܽ�����һ�ſγ̵�ѧϰ
//  			//˳��ѧϰ	==������courseid��classid��usereid == �� �жϵ�ǰ�γ���һ�ſγ��Ƿ��Ѿ�ѧϰ��ɣ�
//  	  		//ѧϰ��ɺ󣬲���ѧϰ��ǰ�γ�
//  			if(!initCompliance){//������ͨ��
//  				if(!classDao.checkcoursecanlearn(course.getId(),classid,getSessionIntValue(ElConstants.SESSION_USERID))){
//  	  	  			this.setElmessage("�Բ��𣬵�ǰ��ѵ��Ҫ��˳�����ѧϰ����û�������һ�ſγ̵�ѧϰ");
//  				if(b==true){
//  					return "error_phone"; 
//  				}
//  	  	  			return "error";
//  	  	  		}
//  			}
//  		}
//  		
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		//�ж��Ƿ���Ҫע��
		int isLogout=course == null ? 0 : course.getIsLogout();
		if(isLogout==1){
			int passtime=myCPage==null?0:myCPage.getPasstime();//��ʱ����
			myCPage=OnlineUtil.getSessionMycpage(getSession());
			myCPage.setUser(new ELUser(userid));
			myCPage.setPasstime(passtime<2?0:passtime-2);
			studyCourseDao.saveMyCourseStudy(myCPage);
			//OnlineUtil.removeStudyInfo(getSession());
			//����ѧϰ��¼��ѧϰʱ��
			studyCourseDao.saveStudyCourseRecordPasstime(myCPage.getStudyinfo_rid(), myCPage.getPasstime());
			OnlineUtil.removeStudyInfo(getSession());
		}
		
		
		
//		myCourse = studyCourseDao.getMyStudyCourse(
//				userid, course.getId(),classid);
		course = courseDao.getCourseById(course.getId());
		//���øÿγ���������ѵ��
//		course.setClassid(classid);
//		if (!studyCourseDao.checkMyCourse(myCourse)) {
//			// studyCourseDao.intoMyCourse(myc);
//			setElmessage("��ûѡ��ÿγ�,�뵽��<a href='studentman.action' style='color:red;'>�ҵ�ѧϰ</a>����ѡ����ſγ�");
//			return "course_noselected";
//		}
		//����Ƿ���ѧϰ��Ϣ��
		if(OnlineUtil.checksStudyInfo(getSession()))
		{
			//course_study_to= "course_study" ;
			if(b==true){
				return "course_study_logout_phone";
			}
			return "course_study_logout";
		}
		int cpid = 0;
		int ccpid=0;
		//�ж��Ƿ��׼�γ�
		if (course.getIslink() == ElConstants.COURSE_TYPE_BZKC) {
			//����½ڵ������Ϣ
			ccpid = coursePage == null || coursePage.getId() <= 0?-1:0;
			cpid = coursePage == null || coursePage.getId() <= 0 ? studyCourseDao
					.getMyLastCpage(userid, course.getId(),classid)
					: coursePage.getId();
			if (cpid <= 0){
				cpid = coursePageDao.getFirstCpId(course.getId());
				ccpid=-2;
			}
			coursePage = coursePageDao.getCp(cpid);
			//�ж��½�
			//���������½���Ϣ���жϸ��½ڵ�ǰһ�½�"�Ƿ�����"������ǣ���ֱ�Ӵ򿪱��½�
			//����Ƿ�  1����ǰһ�£��ж�ǰһ�����״̬������˲��ܴ򿪱��½ڣ�δ��ɣ���ʾ"��һ�½�δ���ѧϰ�������ܽ��뱾�½ڵ�ѧϰ"
			//		    2��ֱ�Ӵ򿪱��½�
			CoursePage beginCPage = null;
			if(coursePage!=null && coursePage.getSortid()!=1){//��ǰһ����Ϣ
				beginCPage = coursePageDao.getBeginCPage(coursePage.getCourse().getId(),coursePage.getSortid()-1);//ǰһ����Ϣ
				if(!initCompliance){//������ͨ��
					if(!coursePageDao.checkPageCanlearn(coursePage.getSortid()-1,coursePage.getCourse().getId(),getSessionIntValue(ElConstants.SESSION_USERID),beginCPage.getId())){
						//����Ϊǰһ��sortid��courseid��userid
						//�����  �γ��½ڱ�COURSE_PAGE���½ڷ����STUDY_CPAGE
						this.setElmessage("��һ�½�δ���ѧϰ�������ܽ��뱾�½ڵ�ѧϰ");
						if(b==true){
							return "error_phone"; 
						}
						return "error";
						
					}
				}
			}
			//ͬ���½�
			if(coursePage.getIslive()==1){
				course.setIslink( ElConstants.COURSE_TYPE_TBKT);
				course.setRoom(new Rooms(coursePage.getRoom().getId()));
			}
			
			
			//����ǵ�һ�ν����׼�γ��ڴ�ע�����е��½�
//			if(ccpid==-2){//���ǵ�������ӵĿγ��½ڣ����Դ��ж�ע��
				//�Ȳ�ÿγ̵������½�
				List<CoursePage> clist=courseDao.getCourseAllCpage(course.getId());
				for(int i=0;i<clist.size();i++){
					if (!studyCourseDao.checkMyCPage(new MyCPage(
							userid,clist.get(i).getId()),classid)) {
						studyCourseDao.intoMyCPage(new MyCPage(userid,clist.get(i).getId()),classid);
					}
				}
//			}
			if (null != coursePage) {
				myCPage = studyCourseDao.getMyCPage(
						userid, coursePage
								.getId(),classid);
				myCPage.setCpage(coursePage);
//				myCPages = studyCourseDao.listCpsbyCUid(course.getId(),//�½ڹ�����ϰ
//						userid,classid);
				myCPages = studyCourseDao.listCpsbyCUid_wjm(course.getId(),//�γ̵��½ڣ�����������
						userid,classid);
				
			} else {
				setElmessage("û�ҵ��γ��½�,��ȷ�Ͽγ��Ƿ�������ַ�Ƿ���ȷ��");
				if(b==true){
					return "error_phone"; 
				}
				return "error";
			}
		if(ccpid==-1){
				//���ر�׼�γ���ҳ
			if(b==true){
				return "course_study_bzkc_index_phone";
			}
				return "course_study_bzkc_index";
			}
		}else if(course.getIslink() == ElConstants.COURSE_TYPE_TBKT){
			if (course.getTeacherId() != userid) {//���ǽ�ʦ
				if (!omDao.moderatorHasLogin(course.getRoom().getId())) {//��ʦδ����
					setElmessage("��������δ���룡���Ժ�");
					if(b==true){
						return "error_phone"; 
					}
					return "error";
				} 
			}
		}
//		if(OnlineUtil.checksStudyInfo(classid,course.getId(), cpid, getSession()))
//		{
//			setElmessage("���Ѿ����˸ÿγ̣�������ͬʱ����������!");
//			return "error";
//		}	
		//��¼1��ѧϰ��¼������ͳ��
		studyCourseRecordId=studyCourseDao.addStudyCourseRecord(course.getId(),classid,cpid,userid);
		OnlineUtil.setStudyInfo(classid, course.getId(), cpid,studyCourseRecordId, getSession());
		if (course.getIslink() == ElConstants.COURSE_TYPE_WBKC) {//�ⲿ�γ�
			OnlineUtil.setStudyInfo(classid, course.getId(), cpid, getSession());
			if(b==true){
				return "course_study_wbkc_phone";
			}
			return "course_study_wbkc";
		}
//		else if (course.getIslink() == ElConstants.COURSE_TYPE_ZHWB)
//			// return "course_linkc_studyindex";
//			return "course_study_zhwb";
		else if (course.getIslink() == ElConstants.COURSE_TYPE_DYSP){//��һ��Ƶ
			myCPages = studyCourseDao.listCpsbyCUid(course.getId(),
					userid,classid);
//			OnlineUtil.setStudyInfo(classid, course.getId(), cpid, getSession());
			if(b==true){
				return "course_study_dysp_phone";
			}
			return "course_study_dysp";
		}else if (course.getIslink() == ElConstants.COURSE_TYPE_TBKT){//ͬ������
			omDao.setModeratorHasLoginOut(course.getRoom().getId(), 1);
			getSession().setAttribute("roomid", course.getRoom().getId()); 
			getSession().setAttribute("teacherId", userid);//��ʦid
//			OnlineUtil.setStudyInfo(classid, course.getId(), cpid, getSession());
			if(b==true){
				return "course_study_tbkt_phone";
			}
			return "course_study_tbkt";
		}else if(course.getIslink()==ElConstants.COURSE_TYPE_SCORM){//scorm
			userid = getSessionIntValue(ElConstants.SESSION_USERID);
			classidStr=getRequest().getParameter("classid");
			classid=course.getClassid()>0?course.getClassid():0;
			if(classidStr!=null){
				classid=OnlineUtil.getIntValue(classidStr);
			}
			course = courseDao.getCourseById(course.getId());
			StudyScormDao ssd =((StudyScormDao)SpringContextUtil.getBean("studyScormDao"));
			ssd.registerCourse(userid+"", course.getExurl(),classid+"");
			scormcourse = ssd.intoCourse(course.getExurl(), userid+"",classid+"", scormcourse!=null?scormcourse.getScoid():null, scormcourse!=null?scormcourse.getNowScoid():null, scormcourse!=null?scormcourse.getNavitype():null);
			
			
////			OnlineUtil.setStudyInfo(classid, course.getId(), cpid, getSession());
//			StudyScormDao ssd =((StudyScormDao)SpringContextUtil.getBean("studyScormDao"));
//			ssd.registerCourse(userid+"", course.getExurl(),classid+"");
//			scormcourse = ssd.intoCourse(course.getExurl(), userid+"",classid+"", scormcourse!=null?scormcourse.getScoid():null, scormcourse!=null?scormcourse.getNowScoid():null, scormcourse!=null?scormcourse.getNavitype():null);
			if(b==true){
				return "course_study_scorm_phone";
			}
			return "course_study_scorm";
		}else{//��׼�γ�
			//����γ��½ڽ�ҵ��ʽ���
//				int getcredit=coursePage.getGetcredit();
//				int ispassed=myCPage.getPassed2();
//				if(getcredit==1){
//					//ѧ��
//					//���ô���
//				}else if(getcredit==2){
//					//����
//					if(ispassed==1){
//						//����,���ý��100% 
//						//studyCourseDao.saveMyCPage(myCPage,classid,3,coursePage.getDuring());
//					}else{
//						//ҳ����ֹͣ
//						status=1;
//					}
//				}else{
//					//ѧ���ҿ���
//					//ÿ�α����ȼ�30��������ȼ�1��
//					//��ѯ��ǰ��ѧϰ��ȣ�����50%�� ��ֹͣ
//					//if(ispassed==0){
////						if(myCPage.getPasstime()>=coursePage.getDuring()*60/2){
////							status=1;
////						}
//					//}
//					status=2;//�ý�ҵ��ʽʵ�ʽ�Ȳ���
//				}
			//�ϵ���ѧ
//			studyCourseDao.cPageFinishSet(userid, cpid, classid);
//			studyCourseDao.updateStudyCourseRecordStatus(course.getId(), classid, cpid, userid, 0, new Timestamp(System.currentTimeMillis()));
//			OnlineUtil.setStudyInfo(classid, course.getId(), cpid, getSession());
			if(b==true){
				if (coursePage != null && coursePage.getType() == 1) {//����Ƶ�γ�
					return "cpage_study_csp_phone";
				}
				if (coursePage != null && coursePage.getType() == 3) {//�ⲿ�γ��½�
					System.out.println("�ֻ�˿γ��½�");
					return "cpage_study_wbkc_phone";
				}
				if (coursePage != null && coursePage.getType() == 4) {//������ѧϰϵͳ
					return "cpage_study_spxx1_phone";
				}
				if (coursePage != null && coursePage.getType() == 5) {//������ѧϰϵͳ
					return "cpage_study_spxx_phone";
				}
				if (coursePage != null && coursePage.getType() == 6) {//������ѧϰϵͳ
					return "cpage_study_wbspxx_phone";
				}
				return "cpage_study_twspjy_phone";//ͼ�ģ���Ƶ���Ƶ�γ̡�
			}
			if (coursePage != null && coursePage.getType() == 1) {//����Ƶ�γ�
				return "cpage_study_csp";
			}
			if (coursePage != null && coursePage.getType() == 3) {//�ⲿ�γ��½�
				return "cpage_study_wbkc";
			}
			if (coursePage != null && coursePage.getType() == 4) {//������ѧϰϵͳ
				return "cpage_study_spxx";
			}
			if (coursePage != null && coursePage.getType() == 5) {//������ѧϰϵͳ2
				return "cpage_study_spxx";
			}
			return "cpage_study_twspjy";//ͼ�ģ���Ƶ���Ƶ�γ̡�
		}

	}
	
}
