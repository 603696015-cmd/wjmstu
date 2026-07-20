package com.sopia.courseman.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.CpageExcelUtil;
import com.sopia.common.ElException;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.JTM;
import com.sopia.common.JTMSystemConfOp;
import com.sopia.common.OnlineUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CoursePageDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseAuditDescribes;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.CourseRegistration;
import com.sopia.courseman.entities.CourseRemarks;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.PracticePaper;
import com.sopia.courseman.entities.QuizPaper;
import com.sopia.courseman.entities.ScormCourse;
import com.sopia.courseman.entities.SimexamPaper;
import com.sopia.duman.entities.BaseDataTypeCourse;
import com.sopia.duman.entities.BaseDatatCourse;
import com.sopia.duman.entities.ELUser;
import com.sopia.forumman.dao.ForumAdminDao;
import com.sopia.forumman.entities.Forum;
import com.sopia.forumman.entities.ForumBlock;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.openmeetings.OmDao;
import com.sopia.openmeetings.Rooms;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.dao.StudyScormDao;
import com.sopia.studyman.entities.MyExamPaper;

public class CourseMakeAction extends BaseAction {
	private Course course;
	private CourseRemarks cRemarks;
	private CoursePage coursePage;
	private CoursePageDao coursePageDao;
	private List<CoursePage> coursePages;
	private CourseDao courseDao;
	private CourseTypeDao ctypeDao;
	private CourseType ctypeTree;
	private List<ExamPaper> exampapers;
	private ExamPaperLib eplTree;
	private ExamPaperDao examPaperDao;
	private int sublibs;
	private ExamPaper examPaper;
	private List<ExamRoom> examRooms;
	private ExamRoom examRoom;
	private List<MyExamPaper> myExampapers;
	private MyExamPaper myExamPaper;
	private StudyQuizDao studyQuizDao;
	private QuestionDao questionDao;
	private EroomLib eroomLibTree;
	private List<ExamPaper> examPapers;

	private PracticePaper pracPaper;
	private List<PracticePaper> pracPapers;
	private SimexamPaper simPaper;
	private List<SimexamPaper> simPapers;
	private QuizPaper quizPaper;
	private List<QuizPaper> quizPapers;
	private String params;
	private int[] thescore;
	private OmDao omDao;
	private CourseAuditDescribes courseAudit;
	private CourseRegistration coRegistration;
	private List<ElClass> elClasss;
	private int isEroomName;  
	private int isclassName;  
	private String Return;
	private ScormCourse scormcourse;
	private String shihegangwei;//适合岗位
	private String zhuanyeleibie;//专业类别
	private String zhuanyejibie;//专业级别
	private String shihebumen;//适合部门
	private String neirongleixing;//内容类型
	private String peixunleibie;//培训类别
	private String shihexuewei;//适合学位
	private String kechengxingzhi;//课程性质
	private List<BaseDatatCourse> shihegangweis;
	private List<BaseDatatCourse> zhuanyeleibies;
	private List<BaseDatatCourse> zhuanyejibies;
	private List<BaseDatatCourse> shihebumens;
	private List<BaseDatatCourse> neirongleixings;
	private List<BaseDatatCourse> peixunleibies;
	private List<BaseDatatCourse> shihexueweis;
	private List<BaseDatatCourse> kechengxingzhis;
	private List<BaseDataTypeCourse> baseCourseTypeList;
	private EroomDao eroomDao;
	private int isBand;
	private String roomids;
	private int stid;//附件id
	
	//论坛版块
	private List<ForumBlockType> fbtypes;
	private ForumAdminDao forumAdminDao;
	private List<Forum> jhforums;
	private List<Forum> rmforums;
	private List<Forum> zxforums;
	private ForumBlock fblock;
	private Forum forum;
	
	
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

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public int getStid() {
		return stid;
	}

	public void setStid(int stid) {
		this.stid = stid;
	}

	public String getRoomids() {
		return roomids;
	}

	public void setRoomids(String roomids) {
		this.roomids = roomids;
	}

	public int getIsBand() {
		return isBand;
	}

	public void setIsBand(int isBand) {
		this.isBand = isBand;
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

	public List<BaseDataTypeCourse> getBaseCourseTypeList() {
		return baseCourseTypeList;
	}

	public void setBaseCourseTypeList(List<BaseDataTypeCourse> baseCourseTypeList) {
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

	public ScormCourse getScormcourse() {
		return scormcourse;
	}

	public void setScormcourse(ScormCourse scormcourse) {
		this.scormcourse = scormcourse;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public int getIsEroomName() {
		return isEroomName;
	}

	public void setIsEroomName(int isEroomName) {
		this.isEroomName = isEroomName;
	}

	public int getIsclassName() {
		return isclassName;
	}

	public void setIsclassName(int isclassName) {
		this.isclassName = isclassName;
	}

	public List<ElClass> getElClasss() {
		return elClasss;
	}

	public void setElClasss(List<ElClass> elClasss) {
		this.elClasss = elClasss;
	}

	public CourseAuditDescribes getCourseAudit() {
		return courseAudit;
	}

	public void setCourseAudit(CourseAuditDescribes courseAudit) {
		this.courseAudit = courseAudit;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public List<CoursePage> getCoursePages() {
		return coursePages;
	}

	public void setCoursePages(List<CoursePage> coursePages) {
		this.coursePages = coursePages;
	}

	public CoursePageDao getCoursePageDao() {
		return coursePageDao;
	}

	public void setCoursePageDao(CoursePageDao coursePageDao) {
		this.coursePageDao = coursePageDao;
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
	public EroomLib getEroomLibTree() {
		return eroomLibTree;
	}
	public void setEroomLibTree(EroomLib eroomLibTree) {
		this.eroomLibTree = eroomLibTree;
	}

	public String coursepage_addInit() throws ElException {
		course = courseDao.getCourseById(coursePage.getCourse().getId());
		// if (course.getIslink() == CourseConstants.COURSE_ISLINK_WAIBU) {
		// setElmessage("外部课程不能有章节管理");
		// return "error";
		// }
		// TODO 别忘掉改回来
		// if (course.getStatus() != CourseConstants.COURSE_STATUS_MAKEING) {
		// setElmessage("只能在课程“制作中”的状态下才能添加章节");
		// return "error";
		// }
		course = courseDao.getCourseById(course.getId());
		return "coursepage_addInit";
	}

	public String coursepage_add() throws ElException {
		course = courseDao.getCourseById(coursePage.getCourse().getId());
		// if (course.getIslink() == CourseConstants.COURSE_ISLINK_WAIBU) {
		// setElmessage("外部课程不能有章节管理");
		// return "error";
		// }
		// if (course.getStatus() != CourseConstants.COURSE_STATUS_MAKEING) {
		// setElmessage("只能在课程“制作中”的状态下才能添加章节");
		// return "error";
		// }
		// TODO 别忘掉改回来
		//coursePageDao.addCoursePage(coursePage);
		if(coursePage.getGetcredit()==0){
			coursePage.setGetcredit(1);
		}
		String staddr[] = getRequest().getParameterValues("coursePage.stuffs.description");
		String sttitle[] = getRequest().getParameterValues("coursePage.stuffs.title");
		
		if(null!=staddr){
			for(int i=0;i<staddr.length;i++){
				coursePageDao.addStuff(staddr[i],sttitle[i],coursePage.getId());
			}
		}
		Rooms room = coursePage.getRoom()==null?new Rooms():coursePage.getRoom();
		if(coursePage.getIslive()==1){
			//添加同步课堂类型
			room.setRoomtype(2);
			room.setComment(coursePage.getTitle());
			room.setName(coursePage.getTitle());
			omDao.addOmRoom(room);
			
		}
		coursePage.setRoom(room);
		coursePageDao.addCoursePage2(coursePage);
		return "coursepage_add_success";
	}

	public String coursepage_list() throws ElException {
		course = courseDao.getCourseById(course.getId());
		/*
		 * if (course.getIslink() == CourseConstants.COURSE_ISLINK_WAIBU) {
		 * setElmessage("外部课程不能有章节管理"); return "error"; } //TODO 别忘掉改回来
		 */coursePages = coursePageDao.listCps(course.getId());
		 //TMK修改
		 //章节绑定多个考场
		 if(coursePages!= null){
			 for(int i=0;i<coursePages.size();i++){
				 coursePages.get(i).setExamRooms(eroomDao.getCPageRooms(coursePages.get(i).getId(),coursePages.get(i).getCourse().getId()));
			 }
		 }
		//pracPapers = courseDao.getPracticePaperByCid(course.getId(), 0);// courseDao.getPracticePaperByCid(course.getId(),
		// 0);
		return "coursepage_list";
	}
	

	/**
	 * 章节内容导入
	 * 
	 * @return
	 * @throws ElException
	 */
	public String coursepage_importInit() throws ElException {
		course = courseDao.getCourseById(course.getId());
		return "coursepage_import";
	}

	/**
	 * 章节内容导入
	 * 
	 * @return
	 * @throws ElException
	 */
	private File st;
	private String stFileName;
	private String sfContentType;

	public String coursepage_import() throws ElException, Exception {
		if (null != st) {
			if (!J2EEFileUtil.getExtention(stFileName).equals("xls")) {
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return "coursepage_import";
			}
			if (st.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "coursepage_import";
			} else {
				CpageExcelUtil.writeCPage(st, course.getId());
			}
		} else {
			setElmessage("请输入上传文件");
			return "coursepage_import";
		}
		return "coursepage_list";
	}

	public String coursepage_alterInit() throws ElException {
		coursePage = coursePageDao.getCp(coursePage.getId());
		course = courseDao.getCourseById(coursePage.getCourse().getId());
		coursePage.setStuffs(coursePageDao.getStuffs(coursePage.getId()));
		return "coursepage_alter";
	}

	public String coursepage_alter() throws ElException {
		//coursePageDao.alterCp(coursePage);
		if(coursePage.getGetcredit()==0){
			coursePage.setGetcredit(1);
		}
		String stid[] = getRequest().getParameterValues("coursePage.stuffs.id");
		String staddr[] = getRequest().getParameterValues("coursePage.stuffs.description");
		String sttitle[] = getRequest().getParameterValues("coursePage.stuffs.title");
		if(stid!=null){
			for(int i=0;i<stid.length;i++){
				int id = Integer.parseInt(stid[i]);
				coursePageDao.alterStuff(id,staddr[i],sttitle[i]);
			}
			
		}
		if(coursePage.getStuffaddr()!=null){
			staddr = getRequest().getParameterValues("coursePage.stuffaddr");
			sttitle = getRequest().getParameterValues("coursePage.stufftitle");
			for(int i=0;i<staddr.length;i++){
				coursePageDao.addStuff(staddr[i], sttitle[i], coursePage.getId());
			}
			
		}
		coursePageDao.alterCp2(coursePage);
		coursePage = coursePageDao.getCp(coursePage.getId());
		return "coursepage_alter_success";
	}
	
	/**
	 * 删除附件
	 * @return
	 * @throws ElException
	 */
	public String coursePageStuff_delete()throws ElException{
		coursePageDao.deleteStuffByid(stid);
		return "coursepage_alterInit";
	}

	public String course_openInit() throws ElException {
		course = courseDao.getCourseById(course.getId());
		if (course.getStatus() == CourseConstants.COURSE_STATUS_HASOPENED) {
			setElmessage("课程已开通了");
		}
		if (course.getStatus() == CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT) {
			setElmessage("课程初审等待中(等待管理员开通，若长时间未开通，请联系管理员)");
		}
		if (course.getStatus() == CourseConstants.COURSE_STATUS_FINAL_WAIT) {
			setElmessage("课程终审等待中(等待管理员开通，若长时间未开通，请联系管理员)");
		}
		courseAudit = courseDao.getCourseAudit(course.getId());
		return "course_open";
	}

	public String course_open() throws ElException {
//		int status = course.getStatus(); 
		Course c = courseDao.getCourseById(course.getId()); 
//		if (c.getStatus() == CourseConstants.COURSE_STATUS_HASOPENED) {
//			course = c;
//			setElmessage("课程已开通了");
//			return "course_open";
//		}
//		if (c.getCpagesize() <= 0
//				&& c.getIslink() != CourseConstants.COURSE_ISLINK_WAIBU&& c.getIslink() != CourseConstants.COURSE_ISLINK_TBKT) {
//			course = c;
//			setElmessage("课程申请开通失败！标准课程，组合外部课程，单一视频都需要有章节");
//			return "course_open";
//		}
		//检测标准课程，单一视频都需要有章节
		if (c.getCpagesize() <= 0
				&&( c.getIslink() == CourseConstants.COURSE_ISLINK_BIAOZHUN || c.getIslink() == CourseConstants.COURSE_ISLINK_DANYISP)) {
			//course = c;
			setElmessage("课程开通失败！标准课程，单一视频都需要有章节");
			//return "course_open";
			return "error";
		}
		//检测标准课程章节的完整性（比如是考过就需要有练习）
		if(c.getIslink() == CourseConstants.COURSE_ISLINK_BIAOZHUN){
			String msg=courseDao.checkCoursePage(course.getId());
			if(msg!=null&&!"".equals(msg)){
				setElmessage(msg);
				return "error";
			}
		}
//		CourseAuditDescribes cad = courseDao.getCourseAudit(course.getId());
		courseDao.openCourse(course.getId(),CourseConstants.COURSE_STATUS_HASOPENED);
//		if(courseAudit != null){//申请说明
//			courseAudit.setCourse(course); 
//			courseAudit.setCreater(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
//			if(cad == null){//如果为空。 为第一次 。 第一次为增加数据。
//				courseDao.openCourseAudit(courseAudit);
//			}else{
//				courseAudit.setId(cad.getId()); 
//				courseAudit.setReplycontent(cad.getReplycontent());//把原来的还给更新的
//				courseDao.UCourseAuditContents(courseAudit);
//			} 
//		}
		//return "course_open_succ";
//		return "course_list";
		return "course_view";
	}

	public String course_preview() throws ElException {
		course = courseDao.getCourseById(course.getId());
		if (course.getIslink() == CourseConstants.COURSE_ISLINK_WAIBU) {
			return "course_preview_waibu";
		}
		if (course.getIslink() == CourseConstants.COURSE_ISLINK_BIAOZHUN) {
			return "course_preview_biaozhun";
		}
		if (course.getIslink() == CourseConstants.COURSE_ISLINK_DANYISP) {
			coursePages = coursePageDao.listCps(course.getId());
			if(coursePages==null||coursePages.size()==0){
				setElmessage("课程无章节，无法预览，请添加章节。");
				return "error";
			}
			return "course_preview_danyisp";
		}
		if (course.getIslink() == CourseConstants.COURSE_ISLINK_ZHUHEWAIBU) {
			int cpid = coursePage == null || coursePage.getId() <= 0 ? coursePageDao
					.getFirstCpId(course.getId())
					: coursePage.getId();
			coursePage = coursePageDao.getCp(cpid);
			course = courseDao.getCourseById(course.getId());
			coursePages = coursePageDao.listCps(course.getId());
			return "course_preview_zhuhewaibu";
		}
		if (course.getIslink() == CourseConstants.COURSE_ISLINK_TBKT) {
			return "course_preview_tbkt";
		}
		if (course.getIslink() == CourseConstants.COURSE_ISLINK_SCORM) {
			return "course_preview_scorm";
		}
		if(course.getIslink() == CourseConstants.COURSE_ISLINK_XIANXIA){
			setElmessage("线下培训课程没有在线学习的内容！");
			return "error";
		}
		setElmessage("课程类型错误--未知的课程类型");
		return "error";
	}
	/**
	 * 章节预览
	 */
	public String coursepage_preview() throws ElException {
		coursePage = coursePageDao.getCp(coursePage.getId());
		if(coursePage.getIsfree()==0){    //收费章节
			setElmessage("该章节不提供预览");
			return "error";
		}
		if(coursePage.getIslive()==1){ //同步章节
			return "coursePage_preview_tongbu";
		}
		if(coursePage.getIslive()==0){ //标准章节
			return "coursePage_preview_biaozhun";
		}
	//	if (course.getIslink() == CourseConstants.COURSE_ISLINK_WAIBU) {
	//		return "course_preview_waibu";
	//	}
	//	if (course.getIslink() == CourseConstants.COURSE_ISLINK_BIAOZHUN) {
	//		return "course_preview_biaozhun";
	//	}
	//	if (course.getIslink() == CourseConstants.COURSE_ISLINK_DANYISP) {
	//		coursePages = coursePageDao.listCps(course.getId());
	//		if(coursePages==null||coursePages.size()==0){
	//			setElmessage("课程无章节，无法预览，请添加章节。");
	//			return "error";
	//		}
	//		return "course_preview_danyisp";
	//	}
	//	if (course.getIslink() == CourseConstants.COURSE_ISLINK_ZHUHEWAIBU) {
	//		int cpid = coursePage == null || coursePage.getId() <= 0 ? coursePageDao
	//				.getFirstCpId(course.getId())
	//				: coursePage.getId();
	//		coursePage = coursePageDao.getCp(cpid);
	//		course = courseDao.getCourseById(course.getId());
	//		coursePages = coursePageDao.listCps(course.getId());
	//		return "course_preview_zhuhewaibu";
	//	}
	//	if (course.getIslink() == CourseConstants.COURSE_ISLINK_TBKT) {
	//		return "course_preview_tbkt";
	//	}
	//	if (course.getIslink() == CourseConstants.COURSE_ISLINK_SCORM) {
	//		return "course_preview_scorm";
	//	}
		setElmessage("章节类型错误--未知的章节类型");
		return "error";
	}
	/**scorm课件预览
	 * @return
	 * @throws ElException
	 */
	public String course_preview_scorm() throws ElException {
		course = courseDao.getCourseById(course.getId());
		StudyScormDao ssd =((StudyScormDao)SpringContextUtil.getBean("studyScormDao"));
		ssd.registerCourse("preview", course.getExurl(),"preview");
		scormcourse = ssd.intoCourse(course.getExurl(), "preview","preview", scormcourse!=null?scormcourse.getScoid():null, scormcourse!=null?scormcourse.getNowScoid():null, scormcourse!=null?scormcourse.getNavitype():null);
		return "course_preview_scorm";
	}
	public String course_preview_zhuhewaibu() throws ElException {
		int cpid = coursePage == null || coursePage.getId() <= 0 ? coursePageDao
				.getFirstCpId(course.getId())
				: coursePage.getId();
		coursePage = coursePageDao.getCp(cpid);
		course = courseDao.getCourseById(course.getId());
		coursePages = coursePageDao.listCps(course.getId());
		return "course_preview_zhuhewaibu_page";
	}

	public String course_preview_biaozhun() throws ElException {
		int cpid = coursePage == null || coursePage.getId() <= 0 ? coursePageDao
				.getFirstCpId(course.getId())
				: coursePage.getId();
		if(cpid ==0){ 
			setElmessage("未能进行课程预览！课程无章节");
			return "error";
		}
		coursePage = coursePageDao.getCp(cpid);
		course = courseDao.getCourseById(course.getId());
		coursePages = coursePageDao.listCps(course.getId());
		if (coursePage.getType() == CourseConstants.CPAGE_TYPE_TW
				|| coursePage.getType() == CourseConstants.CPAGE_TYPE_JYSP) {
			return "course_preview_biaozhun_twspjy";
		}
		if (coursePage.getType() == CourseConstants.CPAGE_TYPE_CSP) {
			return "course_preview_biaozhun_csp";
		}
		if (coursePage.getType() == CourseConstants.CPAGE_TYPE_WB) {
			return "course_preview_biaozhun_wb";
		}
		setElmessage("课程预览出错！未知类型的课程章节");
		return "error";
	}
	/**
	 * 标准章节预览
	 * @return
	 * @throws ElException
	 */
	public String coursePage_preview_biaozhun() throws ElException {
		int cpid = coursePage.getId();
		if(cpid ==0){ 
			setElmessage("未能进行章节预览！无章节");
			return "error";
		}
		coursePage = coursePageDao.getCp(cpid);
		System.out.println(coursePage.getPage_url_());
		if (coursePage.getType() == CourseConstants.CPAGE_TYPE_TW
				|| coursePage.getType() == CourseConstants.CPAGE_TYPE_JYSP) {
			return "course_preview_biaozhun_twspjy";
		}
		if (coursePage.getType() == CourseConstants.CPAGE_TYPE_CSP) {
			return "course_preview_biaozhun_csp";
		}
		if (coursePage.getType() == CourseConstants.CPAGE_TYPE_WB) {
			return "course_preview_biaozhun_wb";
		}
		setElmessage("课程预览出错！未知类型的课程章节");
		return "error";
	}

	public String coursepage_upsort() throws ElException {
		coursePageDao.sortCps(course.getId(), coursePage.getSortid(),
				ElConstants.SORT_UP);
		coursePages = coursePageDao.listCps(course.getId());
		return "coursepage_list";
	}

	public String coursepage_downsort() throws ElException {
		coursePageDao.sortCps(course.getId(), coursePage.getSortid(),
				ElConstants.SORT_DOWN);
		coursePages = coursePageDao.listCps(course.getId());
		return "coursepage_list";
	}

	public String coursepage_delete() throws ElException {
		course = courseDao.getCourseById(course.getId());
		// if (course.getStatus() != CourseConstants.COURSE_STATUS_MAKEING) {
		// setElmessage("只能在课程“制作中”的状态下才能添加章节");
		// return "error";
		// }
		// TODO 别忘掉改回来
		if (null != coursePages)
			for (int i = 0; i < coursePages.size(); i++) {
				coursePageDao.deleteCp(coursePages.get(i).getId());
			}
		coursePages = coursePageDao.listCps(course.getId());
		return "coursepage_list";
	}

	public String course_view() throws ElException {
		course = courseDao.getCourseById(course.getId());
		coursePages = coursePageDao.listCps(course.getId());
		return "course_view";
	}

	public String course_alterInit() throws ElException {
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
		course = courseDao.getCourseById(course.getId());
		if(course.getIsApplication() == 1){
			coRegistration = courseDao.getCourseRegistration(course.getId());
		}
//		course.setRoom(omDao.getOmRoom(course.getRoom().getId()));  //modify by luocws
//		ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//				ElConstants.TREE_FIANL, true,
//				String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
//				true, "COURSE_USE_TYPE");
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		//查基础数据类别 
		baseCourseTypeList=courseDao.getAllBaseDataTypeCourse();

		shihegangweis = courseDao.getBaseDatatCourseByTypeid(1);
		zhuanyeleibies = courseDao.getBaseDatatCourseByTypeid(2);
		zhuanyejibies = courseDao.getBaseDatatCourseByTypeid(3);
		shihebumens = courseDao.getBaseDatatCourseByTypeid(4);
		neirongleixings = courseDao.getBaseDatatCourseByTypeid(5);
		peixunleibies = courseDao.getBaseDatatCourseByTypeid(6);
		shihexueweis = courseDao.getBaseDatatCourseByTypeid(7);
		kechengxingzhis = courseDao.getBaseDatatCourseByTypeid(8); 
		baseCourseTypeList=courseDao.getAllBaseDataTypeCourse();

		return "course_alter";
	}

	
	public String course_alter() throws ElException, UnsupportedEncodingException {
		//获取课程维度
		String[] weidu_array = getRequest().getParameterValues("weidu");
		String weidu = "";
		if(weidu_array!=null&&weidu_array.length>0){
			for(int i=0;i<weidu_array.length;i++){
				if(i == weidu_array.length-1){
					weidu += weidu_array[i];
				}else{
					weidu += weidu_array[i] + ",";
				}
			}
		}
		
		course.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		Course c = courseDao.getCourseById(course.getId());
//		if (c.getIslink() != CourseConstants.COURSE_ISLINK_WAIBU&&c.getIslink() != CourseConstants.COURSE_ISLINK_TBKT) {
//			course.setDuring(coursePageDao.getCDuringAScpage(c.getId()));
//		}
		if(null==c){
			setElmessage("为null的课程对象");
			return "error";
		}
		if (c.getIslink() == CourseConstants.COURSE_ISLINK_BIAOZHUN) {
			course.setDuring(coursePageDao.getCDuringAScpage(c.getId()));
		}
		Rooms room = course.getRoom() == null ? new Rooms() : course.getRoom();
		if (course.getIslink() == 4) {
			if (room.getId() == 0) {
				// 添加同步课堂类型
				room.setRoomtype(2);
				room.setComment(course.getDescription());
				room.setName(course.getName());
				omDao.addOmRoom(room);
			}else{
//				room = omDao.getOmRoom(room.getId());
				room.setRoomtype(2);
				room.setComment(course.getDescription());
				room.setName(course.getName());
				omDao.alterOmRoom(room);
			}
		}
		course.setRoom(room);
		course.setWeidu(weidu);
		courseDao.alterCourse(course); 
		
		boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
		if(open_jtm){
			course = courseDao.getCourseById(course.getId());
			if(course.getName()==null||course.getName().equals("")){
				this.setElmessage("课程名称不能为空");
				return "error";
			}
			//修改维度信息到JTM
			String cer = JTM.getJTM_cer(String.valueOf(course.getId()));
			boolean addSuccess = false;
			String JTM_URL = JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_COURSES_AYSCHRONIZATION_URL)+
			"?courseid="+course.getId()+
			"&coursename="+URLEncoder.encode(course.getName(), "GB2312")+
			"&url=http://www.google.com/"+
			"&dimid="+weidu+
			"&cer="+cer;
			
			Content cc = null;
			try {
				cc = Request.Get(JTM_URL).addHeader("Content-Type", "text/html; charset=UTF-8").execute().returnContent();
				System.out.println(cc.asString());
				String returnValue = cc.asString();
				addSuccess = (returnValue!=null&&returnValue.equals("true"))?true:false;
				
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!addSuccess){
				this.setElmessage("修改维度信息到JTM出错!");
				return "error";
			}
		}

		if(course.getIsApplication()==1 && coRegistration != null) {//是否为可申请
			coRegistration.setCourse(course); 
			if(examRooms != null)
				coRegistration.setExamRoom(examRooms);
			if(elClasss != null)
				coRegistration.setElclass(elClasss);
			if(!courseDao.checkCourseRegistration(course.getId())){
				courseDao.addCourseRegistration(coRegistration);
			}else{
				courseDao.alterCourseRegistration(coRegistration);
			}
		} 
		
		if(course.getIsApplication()==1 && coRegistration != null) {//是否为可申请 
			CourseRegistration coReg = courseDao.getCourseRegistration(course.getId());
			coRegistration.setCourse(course); 
			if(examRooms != null){
				coRegistration.setExamRoom(examRooms);
			}else{
				if(isEroomName == 0){ 
				}else{
					coRegistration.setExamRoom(coReg.getExamRoom());
				}
			}  
			if(elClasss != null){
				coRegistration.setElclass(elClasss);
			} else{
				if(isclassName == 0){ 
				}else{
					coRegistration.setElclass(coReg.getElclass());
				}
			}
			if(!courseDao.checkCourseRegistration(course.getId())){
				courseDao.addCourseRegistration(coRegistration);
			}else{
				courseDao.alterCourseRegistration(coRegistration);
			} 
		}
		if(c!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_COURSE,
					ElLoggerConstants.LOG_TYPE_ALTER,c.getName(),
					ElLoggerConstants.LOG_RES_SUCC,c.getId());
		}
		return	"course_view";//返回课件制作首页
//		return "course_alter_success";
	}

	public String course_applyfor_alterInit()throws ElException{  
		courseDao.alterCourseStatus(course.getId(), CourseConstants.COURSE_STATUS_ALTER_WAIT);//申请状态
		return "course_applyfor_alterInit";
	}	
	
	public String course_applyfor_alter_success(){
		
		return "null";
	}
	
	// /================ 考试练习管理==========
	public String practicepaper_list() throws ElException {
		course = courseDao.getCourseById(course.getId());
		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());
		if (null == coursePage) {
			coursePage = new CoursePage(0, "");
		}

		pracPapers = courseDao.getPracticePaperByCid(pracPaper.getCourse().getId(), pracPaper.getCpage().getId());
		
		return "practicepaper_list";
	}
	
	/**
	 * 章节编辑考场
	 * @return
	 * @throws ElException
	 */
	public String practicepaper_list_room() throws ElException{
		course = courseDao.getCourseById(course.getId());
		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());
		if (null == coursePage) {
			coursePage = new CoursePage(0, "");
		}
		if(pracPaper == null){
			pracPaper = new PracticePaper();
		}
		pracPaper.setCourse(course);
		//章节的考场可以有多个
		examRooms = courseDao.getEroomListByCP(course.getId(),coursePage.getId());
		List<ExamPaper> eps = null;
		if(examRooms != null){//考场对应的试卷
			for(int i=0;i<examRooms.size();i++){
				if(examRooms.get(i) != null){//考场对应的试卷
					if(examRooms.get(i).getIsBand() == 1){
						isBand = 1;
					}
					eps = eroomDao.getEroomepwithusizes(examRooms.get(i).getId());// 获取该考场中的试卷信息
					if (eps != null ) {
						examRooms.get(i).setExampapers(eps);
					}
				}
			}
		}
		return "practicepaper_list_room";
	}
	
	/**
	 * 设置绑定
	 * @return
	 * @throws ElException
	 */
	public String cpage_choose_examroom() throws ElException{
		course = courseDao.getCourseById(course.getId());
		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());
		String[] rooms = null;
		if(roomids!=null){
			rooms = roomids.split(",");
			if(rooms!=null){
				for(int i=0;i<rooms.length;i++){
					courseDao.setBand(Integer.parseInt(rooms[i]),course.getId(),coursePage.getId());
				}
			}
		}
		return "cpage_choose_examroom";
	}

	public String practicepaper_delete() throws ElException {
		if (null != pracPapers)
			for (int i = 0; i < pracPapers.size(); i++) {
				courseDao.deletePracticePaper(pracPapers.get(i).getId());
			}
		course = courseDao.getCourseById(course.getId());
		pracPapers = courseDao.getPracticePaperByCid(pracPaper.getCourse()
				.getId(), pracPaper.getCpage().getId());
		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());

		//return "practicepaper_delete";
		return "practicepaper_list";
	}

	public String practicepaper_addSearchInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		
		course = courseDao.getCourseById(course.getId());				
		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());	
		if (null == coursePage) {
			coursePage = new CoursePage(0, "");
		}
		if(coursePage!=null && coursePage.getTitle()!=null && !coursePage.equals("")){//设置章节练习对应的考场title
			coursePage.setTitle(course.getName()  + coursePage.getTitle()  + "的考场");
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}

		//return "practicepaper_addInit";
		return "practicepaper_addInit2";
	}
	
	/**
	 * 章节修改绑定考场
	 * @return
	 * @throws ElException
	 */
	public String practicepaper_alterSearchInit_room() throws ElException{
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		course = examRoom.getCourse();
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
		
		examRoom.setAppraises(eroomDao.getEroomUsers_ZuZhang("rappraises", examRoom
				.getId()));
		
		
		
		examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
		examRoom.setSelectings(eroomDao.getEroomUsers("selectings", examRoom
				.getId()));
		examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		return "practicepaper_alterSearchInit_room";
	}
	public String practicepaper_alterSearch_room() throws ElException, ParseException{
//		course = courseDao.getCourseById(course.getId());				
//		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());	
		//20140325修改增加时间
		examRoom.setBegintime(new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse("1000-01-01").getTime()));
		examRoom.setEndtime(new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse("3000-01-01").getTime()));
		eroomDao.alterExamRoom(examRoom);
		if (null != examRoom.getInvigilators()) {
			for (int i = 0; i < examRoom.getInvigilators().size(); i++) {
				if (!eroomDao.checkEroomUsers("rinvigilators", examRoom
						.getInvigilators().get(i).getId(), examRoom.getId()))
					eroomDao
							.addEroomusers("rinvigilators", examRoom
									.getInvigilators().get(i).getId(), examRoom
									.getId());
			}
		}
		if (null != examRoom.getAppraises()) {
			for (int i = 0; i < examRoom.getAppraises().size(); i++) {				
				//先判断是否为组长
				if(examRoom.getAppr_header()==null){
					examRoom.setAppr_header(new ELUser(0));
				}
				if(examRoom.getAppraises().get(i).getId()==examRoom.getAppr_header().getId()){
					if (!eroomDao.checkEroomUsers("rappraises", examRoom
							.getAppraises().get(i).getId(), examRoom.getId())){
							eroomDao.addEroomusers("rappraises", examRoom
									.getAppraises().get(i).getId(), examRoom.getId(),1);
					}else{
						eroomDao.UpdateEroomusers("rappraises", examRoom.getAppraises().get(i).getId(), examRoom.getId(), 1);
					}
				}else{
					if (!eroomDao.checkEroomUsers("rappraises", examRoom
							.getAppraises().get(i).getId(), examRoom.getId())){
						eroomDao.addEroomusers("rappraises", examRoom
							.getAppraises().get(i).getId(), examRoom.getId());
					}else{
						eroomDao.UpdateEroomusers("rappraises", examRoom.getAppraises().get(i).getId(), examRoom.getId(), 0);
					}
				}
			}
			
		}
		if (null != examRoom.getValids()) {
			for (int i = 0; i < examRoom.getValids().size(); i++) {
				if (!eroomDao.checkEroomUsers("valids", examRoom.getValids()
						.get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("valids", examRoom.getValids()
							.get(i).getId(), examRoom.getId());
			}
		}
		if (null != examPapers) {
			int sortid=0;
			for (int i = 0; i < examPapers.size(); i++) {
				ExamPaper ep = examPapers.get(i);
				if (ep != null && ep.getId() != 0) {
					// 因为注释掉练习
					if (!eroomDao.checkEroomeps(examRoom.getId(), examPapers
							.get(i).getId())) {
						sortid++;
						eroomDao.addEroomeps(examRoom.getId(), examPapers
								.get(i).getId(), 0, ep.getPractimes(), ep
								.getPracscore(), ep.getPassgrade(), ep
								.getStuview(), ep.getQuizlook(), ep
								.getScorelook(), ep.getQuizcount(), ep
								.getPassmanner(),sortid);
					} else {
						if (ep.getPrac() == null) {
							ep.setPrac(new ExamPaper(0));
						}
						sortid++;
						eroomDao.alterEroomeps(examRoom.getId(), examPapers
								.get(i).getId(), ep.getPrac().getId(), ep
								.getPractimes(), ep.getPracscore(), ep
								.getPassgrade(), ep.getStuview(), ep
								.getQuizlook(), ep.getScorelook(), ep
								.getQuizcount(), ep.getPassmanner());
					}
				}
			}
		}
		return "practicepaper_alterSearch_room";
	}
	/**
	 * 章节绑定考场Init
	 * @return
	 * @throws ElException
	 */
	public String practicepaper_addSearchInit_room() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		
		course = courseDao.getCourseById(course.getId());				//课程
		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());	//课程分配的试卷
		if (null == coursePage) {
			coursePage = new CoursePage(0, "");
		}
		if(coursePage!=null && coursePage.getTitle()!=null && !coursePage.equals("")){//设置章节练习对应的考场title
			coursePage.setTitle(course.getName()  + coursePage.getTitle()  + "的考场");
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		return "practicepaper_addSearchInit_room";
	}
	
	/**
	 * 章节绑定考场
	 * @return
	 * @throws ElException
	 * @throws ParseException 
	 */
	public String practicepaper_addSearch_room() throws ElException, ParseException{
		course = courseDao.getCourseById(course.getId());				//课程
		examRoom.setCreater(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		examRoom.setCourse(course);
		examRoom.setBegintime(new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse("1000-01-01").getTime()));
		examRoom.setEndtime(new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse("3000-01-01").getTime()));
		eroomDao.addExamRoom(examRoom);
		if (null != examRoom.getInvigilators()) {//监考人员
			for (int i = 0; i < examRoom.getInvigilators().size(); i++) {
				if (!eroomDao.checkEroomUsers("rinvigilators", examRoom
						.getInvigilators().get(i).getId(), examRoom.getId()))
					eroomDao
							.addEroomusers("rinvigilators", examRoom
									.getInvigilators().get(i).getId(), examRoom
									.getId());
			}
		}
		if (null != examRoom.getAppraises()) {//阅卷人员
			if(examRoom.getAppr_header()==null){
				examRoom.setAppr_header(new ELUser(0));
			}
			for (int i = 0; i < examRoom.getAppraises().size(); i++) {
				if (!eroomDao.checkEroomUsers("rappraises", examRoom
						.getAppraises().get(i).getId(), examRoom.getId())){
					if(examRoom.getAppraises().get(i).getId()==examRoom.getAppr_header().getId()){
						eroomDao.addEroomusers("rappraises", examRoom
								.getAppraises().get(i).getId(), examRoom.getId(),1);
					}else{
						eroomDao.addEroomusers("rappraises", examRoom
							.getAppraises().get(i).getId(), examRoom.getId());
					}
				}
			}
		}
		if (null != examRoom.getValids()) {//复核人员
			for (int i = 0; i < examRoom.getValids().size(); i++) {
				if (!eroomDao.checkEroomUsers("valids", examRoom.getValids()
						.get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("valids", examRoom.getValids()
							.get(i).getId(), examRoom.getId());
			}
		}
		if (null != examPapers) {
			int sortid=0;
			for (int i = 0; i < examPapers.size(); i++) {
				ExamPaper ep = examPapers.get(i);
				if (ep != null && ep.getId() != 0)
					if (!eroomDao.checkEroomeps(examRoom.getId(), ep.getId())
							&& ep.getId() != 0)
						sortid++;
						eroomDao.addEroomeps(examRoom.getId(), ep.getId(), 0,
								ep.getPractimes(), ep.getPracscore(), ep
										.getPassgrade(), ep.getStuview(), ep
										.getQuizlook(), ep.getScorelook(), ep
										.getQuizcount(), ep.getPassmanner(),sortid);
			}
		}
		return "practicepaper_addSearch_room";
	}
	
	
	/**
	 * 章节绑定课程上移
	 * @return
	 * @throws ElException
	 */
	public String cpage_upsort() throws ElException{
		//examRoom.id,examRoom.sortid
		coursePageDao.sortRoom(examRoom.getId(), examRoom.getSortid(),
				ElConstants.SORT_UP,course.getId(),pracPaper.getCpage().getId());
		return "cpage_upsort";
	}
	/**
	 * 章节绑定课程下移
	 * @return
	 * @throws ElException
	 */
	public String cpage_downsort() throws ElException{
		coursePageDao.sortRoom(examRoom.getId(), examRoom.getSortid(),
				ElConstants.SORT_DOWN,course.getId(),pracPaper.getCpage().getId());
		return "cpage_downsort";
	}

	public String practicepaper_add_view() throws ElException {
		examPaper = examPaperDao.getExamPaperById(pracPaper.getExamPaper()
				.getId());
		return "practicepaper_add_view";
	}

	public String practicepaper_addInit() throws ElException {
		/*course = courseDao.getCourseById(course.getId());
		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());
		if (null == coursePage) {
			coursePage = new CoursePage(0, "");
		}
		if (sublibs == 1) {// 包含下级类别
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), true,
					getPageNow(), getPageSize());
		} else {
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), false,
					getPageNow(), getPageSize());
		}
		if (null != exampapers)
			for (int i = 0; i < exampapers.size(); i++) {
				pracPaper.setExamPaper(exampapers.get(i));
				exampapers.get(i).setCourseHasEp(
						courseDao.checkPpInCourse(pracPaper));
			}
		return "practicepaper_add";*/
//		course = courseDao.getCourseById(course.getId());
//		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());
//		if (null == coursePage) {
//			coursePage = new CoursePage(0, "");
//		}
//		if (sublibs == 1) {// 包含下级类别
//			exampapers = examPaperDao.listEpsByEplId(
//					examPaper.getEpl().getId(), examPaper.getTitle(), true,
//					getPageNow(), getPageSize());
//			count=examPaperDao.listEpsByEpIdSize(examPaper.getEpl().getId(), examPaper.getTitle(), true);
//		} else {
//			exampapers = examPaperDao.listEpsByEplId(
//					examPaper.getEpl().getId(), examPaper.getTitle(), false,
//					getPageNow(), getPageSize());
//			count=examPaperDao.listEpsByEpIdSize(examPaper.getEpl().getId(), examPaper.getTitle(), false);
//		}
//		if (null != exampapers)
//			for (int i = 0; i < exampapers.size(); i++) {
//				pracPaper.setExamPaper(exampapers.get(i));
//				exampapers.get(i).setCourseHasEp(
//						courseDao.checkPpInCourse(pracPaper));
//			}
		

		int eplid = examPaper == null || examPaper.getEpl() == null ? 0
				: examPaper.getEpl().getId();
		String title = examPaper == null ? "" : examPaper.getTitle(); 
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
//			if (sublibs == 1 || eplid== 1) {// 包含下级类别
//				exampapers = examPaperDao.listEpsByEplId(eplid, title, true,getPageNow(), getPageSize());
//				count = examPaperDao.listEpsByEpIdSize(eplid, title, true); 
//			} else { 
//				exampapers = examPaperDao.listEpsByEplId(eplid, title, false,getPageNow(), getPageSize());
//				count = examPaperDao.listEpsByEpIdSize(eplid, title, false); 
//			}
		}else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true); 
			exampapers = examPaperDao.exampaper_list_listEpsByEplId(eplTree,eplid, title, false,getPageNow(), getPageSize());
				count = examPaperDao.exampaper_list_listEpsByEpIdSize(eplTree,eplid, title, false);  
		}
		return "practicepaper_add";
	}

	public String parcticepaper_upsort() throws ElException {
		pracPaper = courseDao.getPracticePaperById(pracPaper.getId());
		if (null != pracPaper) {
			courseDao.practicepaper_sort(pracPaper, ElConstants.SORT_UP);
			params = "?course.id=" + pracPaper.getCourse().getId()
					+ "&pracPaper.course.id=" + pracPaper.getCourse().getId()
					+ "&pracPaper.cpage.id=" + pracPaper.getCpage().getId();
		}
		return "practicepaper_list";
	}

	public String parcticepaper_downsort() throws ElException {
		pracPaper = courseDao.getPracticePaperById(pracPaper.getId());
		if (null != pracPaper) {
			courseDao.practicepaper_sort(pracPaper, ElConstants.SORT_DOWN);
			params = "?course.id=" + pracPaper.getCourse().getId()
					+ "&pracPaper.course.id=" + pracPaper.getCourse().getId()
					+ "&pracPaper.cpage.id=" + pracPaper.getCpage().getId();
		}
		return "practicepaper_list";
	}

	public String practicepaper_add() throws ElException {
		course = courseDao.getCourseById(course.getId());
		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());
		if (null == coursePage) {
			coursePage = new CoursePage(0, "");
		}
		if (!courseDao.checkPpInCourse(pracPaper))
			courseDao.addPracticePaper(pracPaper);
		if (sublibs == 1) {// 包含下级类别
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), true,
					getPageNow(), getPageSize());
		} else {
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), false,
					getPageNow(), getPageSize());
		}
		if (null != exampapers)
			for (int i = 0; i < exampapers.size(); i++) {
				pracPaper.setExamPaper(exampapers.get(i));
				exampapers.get(i).setCourseHasEp(
						courseDao.checkPpInCourse(pracPaper));
			}
		return "practicepaper_add";
	}
	
	public String practicepaper_add2() throws ElException {
		course = courseDao.getCourseById(course.getId());
		coursePage = coursePageDao.getCp(pracPaper.getCpage().getId());
		if (null == coursePage) {
			coursePage = new CoursePage(0, "");
		}
		if(pracPaper.getCourse()==null){
			pracPaper.setCourse(course);
		}
		if (!courseDao.checkPpInCourse(pracPaper)){
			//courseDao.addPracticePaper(pracPaper);
			courseDao.addPracticePaper2(pracPaper);
		}else{
			setElmessage("改练习所用的试卷已被该课程其他练习添加!!!");
			return "error";
		}
		
		return "practicepaper_add2";
	}

	public String simexampaper_add_view() throws ElException {
		examPaper = examPaperDao.getExamPaperById(simPaper.getExamPaper()
				.getId());
		return "simexampaper_add_view";
	}

	// =====================模考管理
	public String simexampaper_list() throws ElException {
		course = courseDao.getCourseById(course.getId());
		simPapers = courseDao.getSimexampaperByCid(course.getId());
		return "simexampaper_list";
	}

	public String simexampaper_delete() throws ElException {
		if (null != simPapers)
			for (int i = 0; i < simPapers.size(); i++) {
				courseDao.deleteSimexampaper(simPapers.get(i).getId());
			}
		course = courseDao.getCourseById(course.getId());
		simPapers = courseDao.getSimexampaperByCid(course.getId());

		return "simexampaper_delete";
	}

	public String simexampaper_addSearchInit() throws ElException {
		eplTree = examPaperDao.epLibTree(0,
				getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		return "simexampaper_addInit";
	}

	public String simexampaper_addInit() throws ElException {
		course = courseDao.getCourseById(course.getId());
		if (sublibs == 1) {// 包含下级类别
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), true,
					getPageNow(), getPageSize());
		} else {
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), false,
					getPageNow(), getPageSize());
		}
		if (null != exampapers)
			for (int i = 0; i < exampapers.size(); i++) {
				simPaper = new SimexamPaper();
				simPaper.setCourse(course);
				simPaper.setExamPaper(exampapers.get(i));
				exampapers.get(i).setCourseHasEp(
						courseDao.checkSpInCourse(simPaper));
			}
		return "simexampaper_add";
	}

	public String simexampaper_add() throws ElException {
		course = courseDao.getCourseById(course.getId());
		if (!courseDao.checkSpInCourse(simPaper))
			courseDao.addSimexampaper(simPaper);
		if (sublibs == 1) {// 包含下级类别
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), true,
					getPageNow(), getPageSize());
		} else {
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), false,
					getPageNow(), getPageSize());
		}
		if (null != exampapers)
			for (int i = 0; i < exampapers.size(); i++) {
				simPaper = new SimexamPaper();
				simPaper.setCourse(course);
				simPaper.setExamPaper(exampapers.get(i));
				exampapers.get(i).setCourseHasEp(
						courseDao.checkSpInCourse(simPaper));
			}
		return "simexampaper_add";
	}

	// ======考试管理-----
	public String quizpaper_list() throws ElException {
		course = courseDao.getCourseById(course.getId());
		quizPapers = courseDao.getQuizpaperByCid(course.getId());
		return "quizpaper_list";
	}

	public String quizpaper_delete() throws ElException {
		if (null != quizPapers)
			for (int i = 0; i < quizPapers.size(); i++) {
				courseDao.deleteQuizpaper(quizPapers.get(i).getId());
			}
		course = courseDao.getCourseById(course.getId());
		quizPapers = courseDao.getQuizpaperByCid(course.getId());
		return "quizpaper_delete";
	}

	public String quizpaper_addSearchInit() throws ElException {
//		eplTree = examPaperDao.epLibTree(0,
//				getSessionIntValue(ElConstants.SESSION_USERID), -1, true);

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		return "quizpaper_addInit";
	}

	public String quizpaper_addInit() throws ElException {
		course = courseDao.getCourseById(course.getId());
		/*if (sublibs == 1) {// 包含下级类别
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), true,
					getPageNow(), getPageSize());
			this.count=examPaperDao.listEpsByEplIdCount(
					examPaper.getEpl().getId(), examPaper.getTitle(), true,
					getPageNow(), getPageSize());
		} else {
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), false,
					getPageNow(), getPageSize());
			this.count=examPaperDao.listEpsByEplIdCount(
					examPaper.getEpl().getId(), examPaper.getTitle(), false,
					getPageNow(), getPageSize());
		}*/
//		if (sublibs == 1) {// 包含下级类别
//			exampapers = examPaperDao.listEpsByEplId(
//					examPaper.getEpl().getId(), examPaper.getTitle(), true,
//					getPageNow(), getPageSize());
//			count=examPaperDao.listEpsByEpIdSize(examPaper.getEpl().getId(), examPaper.getTitle(), true);
//		} else {
//			exampapers = examPaperDao.listEpsByEplId(
//					examPaper.getEpl().getId(), examPaper.getTitle(), false,
//					getPageNow(), getPageSize());
//			count=examPaperDao.listEpsByEpIdSize(examPaper.getEpl().getId(), examPaper.getTitle(), false);
//
//		}
		if (null != exampapers)
			for (int i = 0; i < exampapers.size(); i++) {
				
				if(pracPaper!=null){
					pracPaper.setExamPaper(exampapers.get(i));
					exampapers.get(i).setCourseHasEp(
							courseDao.checkPpInCourse(pracPaper));
				}
			}
		/*for (int i = 0; i < exampapers.size(); i++) {
			exampapers.get(i).setCourseHasEp(
					courseDao.checkQpInCourse(exampapers.get(i).getId(), course
							.getId()));
		}*/
		return "quizpaper_add";
	}

	public String quizpaper_add() throws ElException {
		course = courseDao.getCourseById(course.getId());
		if (null != exampapers)
			for (int i = 0; i < exampapers.size(); i++) {
				if (!courseDao.checkQpInCourse(exampapers.get(i).getId(),
						course.getId())) {
					quizPaper = new QuizPaper();
					quizPaper.setExamPaper(exampapers.get(i));
					quizPaper.setCourse(course);
					courseDao.addQuizpaper(quizPaper);
				}
			}
		if (sublibs == 1) {// 包含下级类别
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), true,
					getPageNow(), getPageSize());
		} else {
			exampapers = examPaperDao.listEpsByEplId(
					examPaper.getEpl().getId(), examPaper.getTitle(), false,
					getPageNow(), getPageSize());
		}
		if (null != exampapers)
			for (int i = 0; i < exampapers.size(); i++) {
				exampapers.get(i).setCourseHasEp(
						courseDao.checkQpInCourse(exampapers.get(i).getId(),
								course.getId()));
			}
		return "quizpaper_add";
	}

	public String cpage_view() throws ElException {
		if(coursePage!=null&&coursePage.getId()!=0)
		coursePage = coursePageDao.getCp(coursePage.getId());
		return "cpage_view";
	}

	public String simpaperreadlist() throws ElException {
		// getPageSize()=getPageSize()==0?10:getPageSize();
		myExampapers = courseDao.listReadSimPapers(course.getId(),
				getPageNow(), getPageSize());
		count = courseDao.listReadSimPapersSize(course.getId());
		return "simpaperreadlist";
	}

	public String reSimquiz() throws ElException {
		if (myExampapers != null)
			for (int i = 0; i < myExampapers.size(); i++) {
				courseDao.reSimquiz(myExampapers.get(i).getId());
			}
		return "simpaperreadlist";
	}

	public String simpaperreadInit() throws ElException {
		myExamPaper = studyQuizDao.getMySimEpByUandR(myExamPaper.getTester()
				.getId(), myExamPaper.getExamPaper().getId(), course.getId());
		examPaper = examPaperDao.getExamPaperById(myExamPaper.getExamPaper()
				.getId());// myExamPaper.getExamPaper();
		// if (null == myExamPaper.getMyAnswer()
		// || "".equals(myExamPaper.getMyAnswer().trim())) {
		// examPaper = examPaperDao.getEPAllInfoById(examPaper.getId());
		// } else {
		// examPaper.setEpBlocks(new ArrayList<ExamPaperBlock>());
		// ELUser user =
		// userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		// ExamPaperUtil.getAnswerExampaper(myExamPaper.getMyAnswer(),
		// examPaper, examPaperDao, questionDao,user.getShengri() );
		// }
		return "simpaperreadInit";
	}

	public String simpaperread_submit() throws ElException {
		int score1 = 0;
		for (int i = 0; i < thescore.length; i++) {
			score1 += thescore[i];
		}

		studyQuizDao.setSimFinalScore(course.getId(), myExamPaper
				.getExamPaper().getId(), myExamPaper.getTester().getId(),
				score1);
		// studentDao.setFinalScore(myExamPaper.getExamRoom().getId(),
		// myExamPaper.getTester().getId(), score1);
		return "simpaperread_submit";
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

	public List<ExamPaper> getExampapers() {
		return exampapers;
	}

	public void setExampapers(List<ExamPaper> exampapers) {
		this.exampapers = exampapers;
	}

	public ExamPaperLib getEplTree() {
		return eplTree;
	}

	public void setEplTree(ExamPaperLib eplTree) {
		this.eplTree = eplTree;
	}

	public ExamPaperDao getExamPaperDao() {
		return examPaperDao;
	}

	public void setExamPaperDao(ExamPaperDao examPaperDao) {
		this.examPaperDao = examPaperDao;
	}

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
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

	public List<MyExamPaper> getMyExampapers() {
		return myExampapers;
	}

	public void setMyExampapers(List<MyExamPaper> myExampapers) {
		this.myExampapers = myExampapers;
	}

	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}

	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public int[] getThescore() {
		return thescore;
	}

	public void setThescore(int[] thescore) {
		this.thescore = thescore;
	}

	public PracticePaper getPracPaper() {
		return pracPaper;
	}

	public void setPracPaper(PracticePaper pracPaper) {
		this.pracPaper = pracPaper;
	}

	public List<PracticePaper> getPracPapers() {
		return pracPapers;
	}

	public void setPracPapers(List<PracticePaper> pracPapers) {
		this.pracPapers = pracPapers;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public SimexamPaper getSimPaper() {
		return simPaper;
	}

	public void setSimPaper(SimexamPaper simPaper) {
		this.simPaper = simPaper;
	}

	public List<SimexamPaper> getSimPapers() {
		return simPapers;
	}

	public void setSimPapers(List<SimexamPaper> simPapers) {
		this.simPapers = simPapers;
	}

	public QuizPaper getQuizPaper() {
		return quizPaper;
	}

	public void setQuizPaper(QuizPaper quizPaper) {
		this.quizPaper = quizPaper;
	}

	public List<QuizPaper> getQuizPapers() {
		return quizPapers;
	}

	public void setQuizPapers(List<QuizPaper> quizPapers) {
		this.quizPapers = quizPapers;
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

	public void setSfContentType(String sfContentType) {
		this.sfContentType = sfContentType;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public OmDao getOmDao() {
		return omDao;
	}

	public void setOmDao(OmDao omDao) {
		this.omDao = omDao;
	}

	public CourseRemarks getCRemarks() {
		return cRemarks;
	}

	public void setCRemarks(CourseRemarks remarks) {
		cRemarks = remarks;
	}

	public CourseRegistration getCoRegistration() {
		return coRegistration;
	}

	public void setCoRegistration(CourseRegistration coRegistration) {
		this.coRegistration = coRegistration;
	}
	
	public String course_scorm_catalog()throws ElException{
		course = courseDao.getCourseById(course.getId());
		StudyScormDao ssd =((StudyScormDao)SpringContextUtil.getBean("studyScormDao"));
		ssd.registerCourse("preview", course.getExurl(),"preview");
		scormcourse = ssd.intoCourse(course.getExurl(), "preview","preview", scormcourse!=null?scormcourse.getScoid():null, scormcourse!=null?scormcourse.getNowScoid():null, scormcourse!=null?scormcourse.getNavitype():null);
		return "course_scorm_catalog";
		}

	public String course_scorm_study()throws ElException{
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		String classidStr=getRequest().getParameter("classid");
		int classid=course.getClassid()>0?course.getClassid():0;
		if(classidStr!=null){
			classid=OnlineUtil.getIntValue(classidStr);
		}
		course = courseDao.getCourseById(course.getId());
		StudyScormDao ssd =((StudyScormDao)SpringContextUtil.getBean("studyScormDao"));
		ssd.registerCourse(userid+"", course.getExurl(),classid+"");
		scormcourse = ssd.intoCourse(course.getExurl(), userid+"",classid+"", scormcourse!=null?scormcourse.getScoid():null, scormcourse!=null?scormcourse.getNowScoid():null, scormcourse!=null?scormcourse.getNavitype():null);
		for(int i=0;i<scormcourse.getScoList().size();i++){
			System.out.println(scormcourse.getScoList().get(i).getLessonStatusName());
		}
		//return "course_scorm_study";
		return "course_scorm_catalog";
	}
}
