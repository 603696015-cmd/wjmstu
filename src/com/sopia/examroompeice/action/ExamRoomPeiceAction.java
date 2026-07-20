package com.sopia.examroompeice.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.bookinfo.dao.BookInfoDao;
import com.sopia.bookinfo.entities.BookTypeTree;
import com.sopia.bookinfo.entities.Bookinfo;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.CheckHtml;
import com.sopia.common.ElException;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.dao.CourseCommentDao;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.CRE_note;
import com.sopia.courseman.entities.ClassPara;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ErPara;
import com.sopia.courseman.entities.EroomBatch;
import com.sopia.courseman.entities.EroomBatchLib;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.EroomRegistration;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.ExamRoomAuditDescribes;
import com.sopia.courseman.entities.Examprac;
import com.sopia.courseman.entities.QuizPaper;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.examroompeice.dao.ExamRoomPeiceDao;
import com.sopia.examroompeice.entities.ExamRoomPeice;
import com.sopia.examroompeice.entities.ForumExamRoomClub;
import com.sopia.forumman.dao.ForumCourseDao;
import com.sopia.forumman.entities.ForumCourseClub;
import com.sopia.frontman.dao.FrontDao;
import com.sopia.knowledgeman.dao.KnowledgeDao;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.newsandmess.entities.News;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.dao.ProductDao;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.entities.Product;
import com.sopia.pfms.entities.ProductType;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.Question;
import com.sopia.schedule.dao.ScheduleGlobleDao;
import com.sopia.shopping.dao.ShoppingDao;
import com.sopia.statman.dao.ShoppingCartDao;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.studyman.entities.SimpleRemack;

public class ExamRoomPeiceAction  extends BaseAction{
	
	private static final Log logger = LogFactory.getLog(ExamRoomPeiceAction.class);
	private List<ExamRoomPeice> examRooms;
	private List<ExamRoom> examRoomes;
	private ExamRoomPeiceDao examRoomPeiceDao;
	private ExamRoomPeice examRoomPeice;
	private MyRoom myroom;
	private ExamRoom examRoom;
	private ExamRoom eroom;
	private List<ExamPaper> examPapers;
	private EroomLib eroomLibTree;
	private EroomLib eroomLib;
	private EroomDao eroomDao;
	private int   stype;//搜索分类
	private EroomLib cltypeTree;//培训班类型树
	private EroomLib cltype;
	private ElClTypeDao elClTypeDao;
	private int      pt;//价格类型
	private float wpeice;
	private String upd;	
	private int biaoshi;//判断是否需要修改
	private int setstatus;//需要修改的状态值
	private String ifadmin;
	private ELUser creater;
	
	private String optype;
	private ELUser elUser;
	private ExamPaper examPaper;
	private ExamPaper ep;
	private ExamPaperDao examPaperDao;
	private Course course;
	private ClassDao classDao;
	private CourseDao courseDao;
	private Department department;
	private int sub_department;
	private List<Examprac> exampracs;
	private Examprac examprac;
	private Department depTree;
	private List<ELUser> canAssignUsers;
	private List<ELUser> bassignedUsers;
	private StudyQuizDao studyQuizDao;
	private List<MyExamPaper> myExamPapers;
	private List<QuizPaper> quizPapers;
	private List<MyExamPaper> myExampapers;
	private MyExamPaper myExamPaper;
	private QuestionDao questionDao;
	private float[] thescore;
	private List<MyRoom> myrooms;
	private RoleDao roleDao;
	private List<Department> canAssignDeps;
	private List<Department> assignDeps;
	private List<ELUser> elusers;
	private CourseType ctypeTree;
	private CourseTypeDao ctypeDao;
	private CourseType ctype;
	private List<Course> courses;
	private EroomBatchLib erbatchLib;
	private int course_sourse;
	private int classId;
	private EroomBatchLib erbatchLibTree;
	private List<EroomBatch> erbatchs;
	private EroomBatch erbatch;
	private String starttime;
	private String endtime;
	private String classname;

	private Integer deptid;
	private String userids;
	private String choose;
	private String alterValid;
	private String deleteValid;
	private String fushenValid;
	private String huanyuanVlaid;
	private String Return;
	private ExamRoom elclass;
	private CRE_note cre_note;
	private List<CRE_note> crelist;
	private int sublibs;
	private List<ElRole> roles;

	private ExamRoomAuditDescribes erAuditdes;
	ExamRoomAuditDescribes erAuditde;
	private EroomRegistration erRegistration;
	private List<ElClass> elClasss;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	private int DBMethods;
	private ElClass elClass;
	private int isEroomName;
	private int isclassName;
	private int PageStatus;
	private int[] qids;
	private int[] qBlockids;
	private int ajax;
	private List<ErPara> erParas;
	private List<ErPara> erepParas;
	private File st;
	private String stFileName;
	private List<Question> questions;
	private SimpleRemack simpleRemack;
	private List<SimpleRemack> simpleRemacks;
	private int manner;//考场试卷移动方式 1：上移 2：下移
	private List<ClassPara> classPara;
	private Station stTree;
	private Station station;
	private String resultPage;
	private String roomids;
	
	
	private StudyClassDao 		studyClassDao;
	private CourseComment 		courseComment;
	private List<CourseComment> listcc;
	private CourseCommentDao 	courseCommentDao;
	private List<Course> 		bxCourses;
	private List<Course> 		xxCourses;
	private CourseComment 		userComment;//当前用户提交的评论
	private ShoppingCartDao 	shoppingCartDao;
	private int 				myclass;
	private int 				myclassorder;
	private boolean audit;//审核判定
	private ShoppingDao  	shoppingDao;
	
	
	private int isOnload; // 个人中心加载
	private List<MyClass> myClasses;
	private List<ExamRoom> elclasses;
	private List<ElClass> elclassesnot;
	private CoursePage coursePage;
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
	
	private List<Course> zxCourses;
	// private List<Course> rmCourses;



	private FrontDao frontDao;


	private List<News> zxNotices;
	private ForumCourseDao  forumcourseDao;



	private KnowledgeType kltypeTree;
	private KnowledgeDao knowledgeDao;
	private KnowledgeType kltype;





	private List<CourseType> ctls;

	private int shoppingCount;

	
	private int mycourse;//判断是否用有该课程，0未拥有，1 已用有
	private int mycourseorder;//判断是否已有该课程订单；
	private String  name ;//前台总体查询的名称
	private int     nametype;//前台查询的类别
	private BookTypeTree bookTypeTree;
	private String sbookinfo;
	private List<Bookinfo> listb;
	private BookInfoDao bookInfoDao; 
	
	
	
	
	
	
	//会员服务中心
	private IndexDao indexDao; 
	private PfmsUser pfmsUser;
	private int	  id;
	private Product product;
	private ProductDao productDao;
	private ProductType ptypeTree;
	private int shopId;
	
	
	
	

	private ScheduleGlobleDao scheduleGlobleDao;
	
	
	
	

	//前台中心
	private List<ForumCourseClub>   listList;
	private List<ForumExamRoomClub>   classlistList;
	private String type;
	
	private int mybuyroom;
	
	private HttpRequestDeviceUtils httpRequestDeviceUtils;
	
	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}

	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}

	public String piece_applyfor_examroom() throws ElException, UnsupportedEncodingException{
		
//		如果价格状态存在，则修改价格
		if(pt==1||pt==2){
			examRoomPeice_change();
			pt=0;
		}
		//如果表示=1 则申请审核价格
		if(biaoshi==1){
			examRoomPeiceDao.examRoomPeice_Submit(examRoom.getId());
//			elClassPeiceDao.elClassPeice_Submit(elClass.getId());
		}
		
		int typeid = 1;
		int role = getSessionIntValue(ElConstants.SESSION_ROLE);
		String name = examRoom == null ? "" : examRoom.getTitle();
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		sublibs = 1;
		String myUserId = Integer.toString(getSessionIntValue(ElConstants.SESSION_USERID));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		String sqlw=" and er.valid not in (9)";
		if (examRoom == null) {
			examRoom = new ExamRoom();
			examRoom.setSvalid(-1);
			examRoom.setValid(-1);
			// examRoom.setEroomLib(new EroomLib(eroomLibTree.getId()));
			examRoom.setClassid(-1);
			sublibs = 1;
		} else {
			examRoom.setSvalid(-1);
			if(examRoom.getSqlw()==9){
				sqlw=" and er.valid in (9)";
			}
		}
		if (examRoom.getEroomLib() == null
				|| examRoom.getEroomLib().getId() <= 0) {
			examRoom.setEroomLib(eroomLibTree);
		} else {
			examRoom.setEroomLib(eroomDao.getEroomLibById(examRoom
					.getEroomLib().getId()));
		}
		
		examRooms = examRoomPeiceDao.getExamRoomList(examRoom.getEroomLib(), examRoom, sublibs, "0,1,2,3,4,5,6,7,8", sqlw, getPageNow(), getPageSize(), name, myUserId, stype, role);   
//		eroomDao.listExamRoom(examRoom.getEroomLib(), sublibs, sqlw,
//				examRoom, getPageNow(), getPageSize());
		count = examRoomPeiceDao.getMyAllSize(examRoom.getEroomLib(), examRoom, sublibs, "0,1,2,3,4,5,6,7,8", sqlw, getPageNow(), getPageSize(), name, myUserId, stype, role);   
//		count = eroomDao.listExamRoomSize(examRoom.getEroomLib(), sublibs, sqlw,
//				examRoom);
		return "peice_applyfor_examroom_success";
	}
	
	public String peice_audit_examroom() throws ElException{
		
//		如果价格状态存在，则修改价格
		if(pt==1||pt==2){
			examRoomPeice_change();
			pt=0;
		}
//		//如果表示=1 则申请审核价格
//		if(biaoshi==1){
//			examRoomPeiceDao.examRoomPeice_Submit(examRoom.getId());
////			elClassPeiceDao.elClassPeice_Submit(elClass.getId());
//		}
		if(examRoom!=null&&examRoom.getId()>0&&setstatus!=0){//如果setstatus!=0 则审核价格
			int myUserId = getSessionIntValue(ElConstants.SESSION_USERID);
			examRoomPeiceDao.examRoomPeice_audit(examRoom.getId(), myUserId, setstatus);
//			elClassPeiceDao.elClassPeice_audit(elClass.getId(), myUserId,setstatus);     
			examRoom=null;
		}
		
		int typeid = 1;
		int role = getSessionIntValue(ElConstants.SESSION_ROLE);
		String name = examRoom == null ? "" : examRoom.getTitle();
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		sublibs = 1;
		String myUserId = Integer.toString(getSessionIntValue(ElConstants.SESSION_USERID));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		String sqlw=" and er.valid not in (9)";
		if (examRoom == null) {
			examRoom = new ExamRoom();
			examRoom.setSvalid(-1);
			examRoom.setValid(-1);
			// examRoom.setEroomLib(new EroomLib(eroomLibTree.getId()));
			examRoom.setClassid(-1);
			sublibs = 1;
		} else {
			examRoom.setSvalid(-1);
			if(examRoom.getSqlw()==9){
				sqlw=" and er.valid in (9)";
			}
		}
		if (examRoom.getEroomLib() == null
				|| examRoom.getEroomLib().getId() <= 0) {
			examRoom.setEroomLib(eroomLibTree);
		} else {
			examRoom.setEroomLib(eroomDao.getEroomLibById(examRoom
					.getEroomLib().getId()));
		}
		
		examRooms = examRoomPeiceDao.getExamRoomList(examRoom.getEroomLib(), examRoom, sublibs, "0,1,2,3,4,5,6,7,8", sqlw, getPageNow(), getPageSize(), name, myUserId, stype, role);   
//		eroomDao.listExamRoom(examRoom.getEroomLib(), sublibs, sqlw,
//				examRoom, getPageNow(), getPageSize());
		count = examRoomPeiceDao.getMyAllSize(examRoom.getEroomLib(), examRoom, sublibs, "0,1,2,3,4,5,6,7,8", sqlw, getPageNow(), getPageSize(), name, myUserId, stype, role);   
//		count = eroomDao.listExamRoomSize(examRoom.getEroomLib(), sublibs, sqlw,
//				examRoom);
		return "peice_audit_examroom_success";
	}

	
	/**
	 * 考场定价修改
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ELException 
	 */
	public void examRoomPeice_change()throws ElException{
		examRoomPeiceDao.examRoomPeice_change(wpeice, examRoom.getId(),  pt,getSessionIntValue(ElConstants.SESSION_USERID));
//		elClassPeiceDao.elClassPeice_change(wpeice, elClass.getId(), pt,getSessionIntValue(ElConstants.SESSION_USERID));
		

	}
	
	
//------------------------------shopping----------------------------------------------------------------
	public String  examRoomShoppping() throws ElException{
		
		List<EroomLib>   listid  = new ArrayList<EroomLib>();
		//首先得到二级课程目录类别
		listid=examRoomPeiceDao.getexamroomerjijiedian();
//			forumcourseDao.getclasserjijiedian();
		cltypeTree = eroomDao.getEroomLibTree(1,ElConstants.TREE_FIANL, true); 
		classlistList = new ArrayList<ForumExamRoomClub>(); //初始化课程中心总集合
		if(listid!=null){
		for (EroomLib typeid : listid) {
			ForumExamRoomClub f = new ForumExamRoomClub();
			f.setEroomLib(typeid);
			//设置10条最新考场
			f.setZuixinexamRoom(examRoomPeiceDao.getApplyForexamRoom(cltypeTree, typeid.getId(), examRoom, 1, " ", 10, 1));
//			f.setZuixinelClass(forumcourseDao.getApplyForeElclass(cltypeTree, typeid.getId(),elclass, 1,"  ",10,1));
			//设置最热的三条考场信息
			f.setHotelexamRoom(examRoomPeiceDao.getApplyForexamRoom(cltypeTree, typeid.getId(), examRoom, 1, " ", 3, 1));
//			f.setHotelClass(forumcourseDao.getApplyForeElclass(cltypeTree, typeid.getId(),elclass, 1,"  ",3,1));
			String ss =  "";
			for (ExamRoom ecl :f.getHotelexamRoom()) {
				//截取长度
				ss = ecl.getDescription();
				if(ss != null){
					if(ss.length() > 126){
						ss = ss.substring(0, 123)+ "...";
					}
				}else{
					ss = "";
				}
				//截取长度
				ecl.setDescription(ss) ;
//				ecl.setDescription((ecl.getDescription().length() > 126) ? ecl.getDescription().substring(0, 123)+ "..." : ecl.getDescription()) ;
				
			}
			classlistList.add(f);//
		}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "examRoomShopppingindex_phone_success";
		}
		return "examRoomShopppingindex_success";
	}
	
	
	
	
	/**
	 * 学员申请培训班级列表
	 * 
	 * @return
	 * @throws ElException
	 */  
	public String newexamroom_view() throws ElException { 
//		eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
//				ElConstants.TREE_FIANL, true);
//		examRoom = eroomDao.getApplyForeEroomById(examRoom.getId());
////		examRoom.getErRegistration().setJoinNumber(
////				eroomDao.getJoinNumber(examRoom.getId()));
//		elUser = userDao
//				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
//		if (checkIsuserApp(examRoom, elUser)) {// 如果返回false证明有某条不符合条件
//			examRoom.setIsuserApp(1);
//		} else {
//			examRoom.setIsuserApp(2);
//			examRoom.setExplain(explain.toString());// 不通过说明
//		}
//		if(examRoom.getErRegistration().getIsAudit()==1){
//			//设置已报名人数
//			examRoom.getErRegistration().setApplyNumber(eroomDao.getStudyApplyCount(examRoom.getId()));
//			if (studyQuizDao.checkStudyRoomApply(examRoom.getId(),
//					getSessionIntValue(ElConstants.SESSION_USERID))) {
//				examRoom.setIsjoin("true");
//			} else {
//				//examRoom.setIsjoin("false");
//				if (eroomDao.checkuser2eroom(examRoom.getId(),
//						getSessionIntValue(ElConstants.SESSION_USERID), -1)) {// 是否已报名//-1单纯的考场
//					examRoom.setIsjoin("true_assign");
//				} else {
//					examRoom.setIsjoin("false");
//				}
//			}
//		}else{
//			//设置已报名人数
//			examRoom.getErRegistration().setApplyNumber(examRoom.getErRegistration().getJoinNumber());
//			if (eroomDao.checkuser2eroom(examRoom.getId(),
//					getSessionIntValue(ElConstants.SESSION_USERID), -1)) {// 是否已报名//-1单纯的考场
//				examRoom.setIsjoin("true");
//			} else {
//				examRoom.setIsjoin("false");
//			}
//		}
//		if(examRoom.getErRegistration().getIsselectep()==1){
//			examRoom.setExampapers(eroomDao.getEroomEps(examRoom.getId()));
//		}
//		// 最新通知公告
//		this.zxtzggs = this.frontDao.listZxNews(8, 1);
//		// 最新推荐通知公告
//		this.tjtzggs = this.frontDao.listZxNews(8, 1, 1);
//		audit=SystemConfOp.getBooleanValue(ElConstants.STUFF_ISFTOPIC);
		//得到考场信息
//		elclass =shoppingDao.getApplyForeElclassById(elclass.getId());  
//		ExamRoomPeice examRoomPeice =new ExamRoomPeice();
		examRoomPeice = examRoomPeiceDao.getApplyForeExamRoomPeiceById(examRoom.getId());
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		if(examRoomPeiceDao.checkUserRoom(examRoom.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))mybuyroom=1;
//		//2得到评论星级信息
////		courseComment=courseCommentDao.getCourseCommentPoint(elclass.getId(),ctype);
////		//3得到用户评论信息
////		
////		listcc=courseCommentDao.getCourseAllComment(elclass.getId(),ctype,getPageNow6(), getPageSize6());
////		count=courseCommentDao.getCourseAllCommentSize(elclass.getId(),ctype);
////		if(shoppingCartDao.checkUserClass(elclass.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))myclass=1;
////		if(shoppingCartDao.checkUserClassOrder(elclass.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))myclassorder=1;
////		bxCourses = classDao.listClassCourses(Integer.valueOf(elclass.getId()), CourseConstants.COURSE_STUDY_STATUS_BX);  
////		xxCourses = classDao.listClassCourses(Integer.valueOf(elclass.getId()),CourseConstants.COURSE_STUDY_STATUS_XX);
		return "newclass_view2";
	}
	
	
	public  String   forum_getAllclass() throws ElException{
		eroomLibTree =eroomDao.getEroomLibTree(1,ElConstants.TREE_FIANL, true); 
//			elClTypeDao.getCltypeTree(1,ElConstants.TREE_FIANL, true); 
		depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
//		
		int cltid = eroomLib == null ? eroomLibTree.getId(): eroomLib.getId();
//		//初始化类别id 
		if(eroomLib==null){
			eroomLib=new EroomLib(cltid);
		}
		int typeid = 1;
		int role = getSessionIntValue(ElConstants.SESSION_ROLE);
		String name = examRoom == null ? "" : examRoom.getTitle();
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		sublibs = 1;
		String myUserId = Integer.toString(getSessionIntValue(ElConstants.SESSION_USERID));
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
//					ElConstants.TREE_FIANL, true);
//		else {
//			eroomLibTree = eroomDao.getEroomLibTree(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op",
//					ElConstants.TREE_FIANL, true);
//		}
//		
		if(examRoom!=null && examRoom.getTitle()!=null && examRoom.getTitle().equals("填写考场名称....")){
//			elclass.setName("");
			examRoom.setTitle("");
		}
		elclasses = examRoomPeiceDao.getApplyForexamRoom(eroomLibTree, cltid, examRoom, 1, "",getPageNow(),getPageSize());
//			forumcourseDao.getApplyForeElclass(cltypeTree, cltid,elclass, 1,"",getPageNow(),getPageSize());
		for (ExamRoom ecl : elclasses) {
			//截取长度
			if(ecl.getDescription()!=null){
			ecl.setDescription((ecl.getDescription().length() > 126) ? ecl.getDescription().substring(0, 123)+ "..." : ecl.getDescription()) ;
			}
		}
		count = examRoomPeiceDao.getApplyForeElclasssize(eroomLibTree, cltid, examRoom, 1, "");
//			forumcourseDao.getApplyForeElclasssize(cltypeTree, cltid, elclass, 1, "");
		  //最新通知公告
	    this.zxtzggs=this.frontDao.listZxNews(8,1);
	    //最新推荐通知公告
	    this.tjtzggs=this.frontDao.listZxNews(8, 1, 1);
		return  "forum_getAllclass_success";
	}
	
//	/**
//	 * 培训班是否满足申请要求
//	 * 
//	 * @author
//	 * @return
//	 * @throws ElException
//	 */
//	public boolean checkIsuserApp(ExamRoom eroom, ELUser eluser)
//			throws ElException {
//		boolean IsuserApp = true;
//		boolean jz = true;
//		boolean ds = true;
//		boolean zj = true;
//		boolean zw = true;
//		boolean gw = true;
//		boolean nl = true;
//		boolean xb = true;
//		boolean bm = true;
//		boolean erooms = true;
//		boolean eroomeps = true;
//		boolean elclass = true;
//		explain = new StringBuffer();
//		if (eroom.getErRegistration().getDslist() == null) {// 地市不限
//			ds = true;
//		} else {
//			if (eroom.getErRegistration().getDslist() != null
//					&& elUser.getDishi() > 0
//					&& eroom.getErRegistration().getDslist().contains(
//							elUser.getDishi() + "")) {
//				ds = true;// dslist不为空 uds不为空 dslist 里没有该地市
//			} else {
//				explain.append("地市 ");
//				dishiIspass = 1;
//				sumIspass = -1;
//				ds = false;// dslist不为空 uds为空 或者 dslist 里没有该地市
//			}
//		}
//		if (eroom.getErRegistration().getJzlist() == null) {
//			jz = true;// 不限
//		} else {
//			if (eroom.getErRegistration().getJzlist() != null
//					&& elUser.getJingzhong() > 0
//					&& eroom.getErRegistration().getJzlist().contains(
//							elUser.getJingzhong() + "")) {
//				jz = true;
//			} else {
//				explain.append("警种 ");
//				jingzhongIspass = 1;
//				sumIspass = -1;
//				jz = false;
//			}
//		}
//		if (eroom.getErRegistration().getZjlist() == null) {
//			zj = true;// 不限
//		} else {
//			if (eroom.getErRegistration().getZjlist() != null
//					&& elUser.getZhiji() > 0
//					&& eroom.getErRegistration().getZjlist().contains(
//							elUser.getZhiji() + "")) {
//				zj = true;
//			} else {
//				explain.append("职级 ");
//				zhijiIspass = 1;
//				sumIspass = -1;
//				zj = false;
//			}
//		}
//		if (eroom.getErRegistration().getZwlist() == null) {
//			zw = true;// 不限
//		} else {
//			if (eroom.getErRegistration().getZwlist() != null
//					&& elUser.getZhiwu() > 0
//					&& eroom.getErRegistration().getZwlist().contains(
//							elUser.getZhiwu() + "")) {
//				zw = true;
//			} else {
//				explain.append("职务 ");
//				zhiwuIspass = 1;
//				sumIspass = -1;
//				zw = false;
//			}
//		}
//		if (eroom.getErRegistration().getGwlist() == null) {
//			gw = true;
//		} else {
//			if (eroom.getErRegistration().getGwlist() != null
//					&& elUser.getGangwei() != null
//					&& eroom.getErRegistration().getGwlist().contains(
//							elUser.getGangwei())) {
//				gw = true;
//			} else {
//				explain.append("岗位 ");
//				gw = false;
//			}
//		}
//		// 年龄段
//		if (eroom.getErRegistration().getStartAge() == 0
//				&& eroom.getErRegistration().getStopAge() == 0) {
//			nl = true;
//		} else {
//			if (eluser.getAGE() > eroom.getErRegistration().getStartAge()
//					&& eroom.getErRegistration().getStopAge() > eluser.getAGE()) {
//				nl = true;
//			} else {
//				explain.append("年龄 ");
//				ageIspass = 1;
//				sumIspass = -1;
//				nl = false;
//			}
//		}
//		// 性别
//		if (eroom.getErRegistration().getSex().equals("不限")) {
//			xb = true;
//		} else if (eroom.getErRegistration().getSex().equals(eluser.getSex())) {
//			xb = true;
//		} else {
//			explain.append("性别 ");
//			sexIspass = 1;
//			sumIspass = -1;
//			xb = false;
//		}
//
//		// 部门
//		if (eroom.getErRegistration().getTreeType() == null) {// 部门不限
//			bm = true;
//		} else {
//			// if (eroom.getErRegistration().getTreeType() != null
//			// && elUser.getDepartment() != null
//			// && eroom.getErRegistration().getTreeTypelist().contains(
//			// elUser.getDepartment().getId() + "")) {
//			// bm = true;
//			// } else {
//			// explain.append("部门 ");
//			// depIspass = 1;
//			// sumIspass = -1;
//			// bm = false;
//			// }
//			// 检测部门条件是否通过
//			if (eroom.getErRegistration().getTreeType() != null
//					&& elUser.getDepartment() != null
//					&& userDao.checkUserIsInDep(elUser.getId(), eroom
//							.getErRegistration().getTreeType())) {
//				bm = true;
//			} else {
//				explain.append("部门 ");
//				depIspass = 1;
//				sumIspass = -1;
//				bm = false;
//			}
//		}
//		// 考场
//			erooms = eroom.getErRegistration().checkErpapspassed(elUser.getId());
//			if(!erooms){
//				explain.append("考场");
//				eroomIspass = 1;
//				sumIspass = -1;
//			}
//		//考场试卷
//			eroomeps = eroom.getErRegistration().checkEreppapspassed(elUser.getId());
//			if(!eroomeps){
//				explain.append("考场试卷");
//				eroomepIspass = 1;
//				sumIspass = -1;
//			}
////		if (eroom.getErRegistration().getExamRooms() == null
////				|| eroom.getErRegistration().getExamRooms().equals("")
////				|| eroom.getErRegistration().getExamRooms().equals("0")) {// 考场不限
////			erooms = true;
////		} else {
////			String sqlWhere = "";
////			if (eroom.getErRegistration().getEroomScreeningWay() == 1) {
////				sqlWhere = " and ispassed  = 1";
////			} else if (eroom.getErRegistration().getEroomScreeningWay() == 2) {
////				sqlWhere = " and ispassed  = 0";
////			}
////			if (!eroom.getErRegistration().getExamRooms().equals("")
////					&& eroomDao.checkEroomIspassed(eroom.getErRegistration()
////							.getExamRooms(),
////							getSessionIntValue(ElConstants.SESSION_USERID),
////							sqlWhere)) {
////				erooms = true;
////			} else {
////				explain.append("考场");
////				eroomIspass = 1;
////				sumIspass = -1;
////				erooms = false;
////			}
////		}
//		// 培训班
//			elclass = eroom.getErRegistration().checkClasspapspassed(elUser.getId());
//			if(!elclass){
//				explain.append("培训班");
//				classIspass = 1;
//				sumIspass = -1;
//			}
////		if (eroom.getErRegistration().getElclasss() == null
////				|| eroom.getErRegistration().getElclasss().equals("")
////				|| eroom.getErRegistration().getElclasss().equals("0")) {// 培训班不限
////			elclass = true;
////		} else {
////
////			String sqlWhere = "";
////			if (eroom.getErRegistration().getClassScreeningWay() == 1) {
////				sqlWhere = "and certificateno is not null";
////			} else if (eroom.getErRegistration().getClassScreeningWay() == 2) {
////				sqlWhere = "and certificateno is null";
////			}
////
////			if (!eroom.getErRegistration().getElclasss().equals("")
////					&& eroomDao.checkElclassIspassed(eroom.getErRegistration()
////							.getElclasss(),
////							getSessionIntValue(ElConstants.SESSION_USERID),
////							sqlWhere)) {
////				elclass = true;
////			} else {
////				explain.append("培训班");
////				classIspass = 1;
////				sumIspass = -1;
////				elclass = false;
////			}
////		}
//
//		if (jz && ds && zj && zw && gw && nl && xb && bm && erooms &&eroomeps && elclass) { //  
//			IsuserApp = true;
//		} else {
//			IsuserApp = false;
//		}
//		return IsuserApp;
	
//	}
	
	
	
	
	
	public String serchExamRoom() throws ElException{
		
			cltypeTree =eroomDao.getEroomLibTree(1,ElConstants.TREE_FIANL, true); 
	//		elClTypeDao.getCltypeTree(1,ElConstants.TREE_FIANL, true); 
			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
//			
			int cltid = cltype == null ? cltypeTree.getId(): cltype.getId();
			//初始化类别id 
			if(cltype==null){
				cltype=new EroomLib(cltid);
			}
		int typeid = 1;
		int role = getSessionIntValue(ElConstants.SESSION_ROLE);
		String name = examRoom == null ? "" : examRoom.getTitle();
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		sublibs = 1;
		String myUserId = Integer.toString(getSessionIntValue(ElConstants.SESSION_USERID));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
			
			elclass = new ExamRoom();
			if(name==null || name.equals("填写名称....")){
			
				elclass.setTitle("");
			}
			else{
				elclass.setTitle(name);
				
			}
			elclasses = examRoomPeiceDao.getApplyForexamRoom(cltypeTree, cltid, examRoom, 1, " ", getPageNow(),getPageSize()); 
//				forumcourseDao.getApplyForeElclass(cltypeTree, cltid,elclass, 1,"  ",getPageNow(),getPageSize()); //不限制条数， 用于获取到可申请的培训班
			for (ExamRoom ecl : elclasses) {
				//截取长度
				if(ecl.getDescription()!=null){
				ecl.setDescription((ecl.getDescription().length() > 126) ? ecl.getDescription().substring(0, 123)+ "..." : ecl.getDescription()) ;
				}
			}
			count = examRoomPeiceDao.getApplyForeElclasssize(cltypeTree, cltid, examRoom, 1, " ");
//				forumcourseDao.getApplyForeElclasssize(cltypeTree, cltid, elclass, 1, "");
			  //最新通知公告
		    this.zxtzggs=this.frontDao.listZxNews(8,1);
		    //最新推荐通知公告
		    this.tjtzggs=this.frontDao.listZxNews(8, 1, 1);
		return "serchExamRoom";
	}
//-----------------------------set and get-------------------------------------
	
	


	public MyRoom getMyroom() {
		return myroom;
	}

	public void setMyroom(MyRoom myroom) {
		this.myroom = myroom;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public ExamRoom getEroom() {
		return eroom;
	}

	public void setEroom(ExamRoom eroom) {
		this.eroom = eroom;
	}

	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}

	public EroomLib getEroomLibTree() {
		return eroomLibTree;
	}

	public void setEroomLibTree(EroomLib eroomLibTree) {
		this.eroomLibTree = eroomLibTree;
	}

	public EroomLib getEroomLib() {
		return eroomLib;
	}

	public void setEroomLib(EroomLib eroomLib) {
		this.eroomLib = eroomLib;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	public ExamPaper getEp() {
		return ep;
	}

	public void setEp(ExamPaper ep) {
		this.ep = ep;
	}

	public ExamPaperDao getExamPaperDao() {
		return examPaperDao;
	}

	public void setExamPaperDao(ExamPaperDao examPaperDao) {
		this.examPaperDao = examPaperDao;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
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

	public List<Examprac> getExampracs() {
		return exampracs;
	}

	public void setExampracs(List<Examprac> exampracs) {
		this.exampracs = exampracs;
	}

	public Examprac getExamprac() {
		return examprac;
	}

	public void setExamprac(Examprac examprac) {
		this.examprac = examprac;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public List<ELUser> getCanAssignUsers() {
		return canAssignUsers;
	}

	public void setCanAssignUsers(List<ELUser> canAssignUsers) {
		this.canAssignUsers = canAssignUsers;
	}

	public List<ELUser> getBassignedUsers() {
		return bassignedUsers;
	}

	public void setBassignedUsers(List<ELUser> bassignedUsers) {
		this.bassignedUsers = bassignedUsers;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
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

	public List<QuizPaper> getQuizPapers() {
		return quizPapers;
	}

	public void setQuizPapers(List<QuizPaper> quizPapers) {
		this.quizPapers = quizPapers;
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

	public float[] getThescore() {
		return thescore;
	}

	public void setThescore(float[] thescore) {
		this.thescore = thescore;
	}

	public List<MyRoom> getMyrooms() {
		return myrooms;
	}

	public void setMyrooms(List<MyRoom> myrooms) {
		this.myrooms = myrooms;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<Department> getCanAssignDeps() {
		return canAssignDeps;
	}

	public void setCanAssignDeps(List<Department> canAssignDeps) {
		this.canAssignDeps = canAssignDeps;
	}

	public List<Department> getAssignDeps() {
		return assignDeps;
	}

	public void setAssignDeps(List<Department> assignDeps) {
		this.assignDeps = assignDeps;
	}

	public List<ELUser> getElusers() {
		return elusers;
	}

	public void setElusers(List<ELUser> elusers) {
		this.elusers = elusers;
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

	public CourseType getCtype() {
		return ctype;
	}

	public void setCtype(CourseType ctype) {
		this.ctype = ctype;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public EroomBatchLib getErbatchLib() {
		return erbatchLib;
	}

	public void setErbatchLib(EroomBatchLib erbatchLib) {
		this.erbatchLib = erbatchLib;
	}

	public int getCourse_sourse() {
		return course_sourse;
	}

	public void setCourse_sourse(int course_sourse) {
		this.course_sourse = course_sourse;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public EroomBatchLib getErbatchLibTree() {
		return erbatchLibTree;
	}

	public void setErbatchLibTree(EroomBatchLib erbatchLibTree) {
		this.erbatchLibTree = erbatchLibTree;
	}

	public List<EroomBatch> getErbatchs() {
		return erbatchs;
	}

	public void setErbatchs(List<EroomBatch> erbatchs) {
		this.erbatchs = erbatchs;
	}

	public EroomBatch getErbatch() {
		return erbatch;
	}

	public void setErbatch(EroomBatch erbatch) {
		this.erbatch = erbatch;
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

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	public String getAlterValid() {
		return alterValid;
	}

	public void setAlterValid(String alterValid) {
		this.alterValid = alterValid;
	}

	public String getDeleteValid() {
		return deleteValid;
	}

	public void setDeleteValid(String deleteValid) {
		this.deleteValid = deleteValid;
	}

	public String getFushenValid() {
		return fushenValid;
	}

	public void setFushenValid(String fushenValid) {
		this.fushenValid = fushenValid;
	}

	public String getHuanyuanVlaid() {
		return huanyuanVlaid;
	}

	public void setHuanyuanVlaid(String huanyuanVlaid) {
		this.huanyuanVlaid = huanyuanVlaid;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	

	public ExamRoom getElclass() {
		return elclass;
	}

	public void setElclass(ExamRoom elclass) {
		this.elclass = elclass;
	}

	public CRE_note getCre_note() {
		return cre_note;
	}

	public void setCre_note(CRE_note cre_note) {
		this.cre_note = cre_note;
	}

	public List<CRE_note> getCrelist() {
		return crelist;
	}

	public void setCrelist(List<CRE_note> crelist) {
		this.crelist = crelist;
	}

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public List<ElRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}

	public ExamRoomAuditDescribes getErAuditdes() {
		return erAuditdes;
	}

	public void setErAuditdes(ExamRoomAuditDescribes erAuditdes) {
		this.erAuditdes = erAuditdes;
	}

	public ExamRoomAuditDescribes getErAuditde() {
		return erAuditde;
	}

	public void setErAuditde(ExamRoomAuditDescribes erAuditde) {
		this.erAuditde = erAuditde;
	}

	public EroomRegistration getErRegistration() {
		return erRegistration;
	}

	public void setErRegistration(EroomRegistration erRegistration) {
		this.erRegistration = erRegistration;
	}

	public List<ElClass> getElClasss() {
		return elClasss;
	}

	public void setElClasss(List<ElClass> elClasss) {
		this.elClasss = elClasss;
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

	public int getDBMethods() {
		return DBMethods;
	}

	public void setDBMethods(int methods) {
		DBMethods = methods;
	}

	public ElClass getElClass() {
		return elClass;
	}

	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
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

	public int getPageStatus() {
		return PageStatus;
	}

	public void setPageStatus(int pageStatus) {
		PageStatus = pageStatus;
	}

	public int[] getQids() {
		return qids;
	}

	public void setQids(int[] qids) {
		this.qids = qids;
	}

	public int[] getQBlockids() {
		return qBlockids;
	}

	public void setQBlockids(int[] blockids) {
		qBlockids = blockids;
	}

	public int getAjax() {
		return ajax;
	}

	public void setAjax(int ajax) {
		this.ajax = ajax;
	}

	public List<ErPara> getErParas() {
		return erParas;
	}

	public void setErParas(List<ErPara> erParas) {
		this.erParas = erParas;
	}

	public List<ErPara> getErepParas() {
		return erepParas;
	}

	public void setErepParas(List<ErPara> erepParas) {
		this.erepParas = erepParas;
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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public SimpleRemack getSimpleRemack() {
		return simpleRemack;
	}

	public void setSimpleRemack(SimpleRemack simpleRemack) {
		this.simpleRemack = simpleRemack;
	}

	public List<SimpleRemack> getSimpleRemacks() {
		return simpleRemacks;
	}

	public void setSimpleRemacks(List<SimpleRemack> simpleRemacks) {
		this.simpleRemacks = simpleRemacks;
	}

	public int getManner() {
		return manner;
	}

	public void setManner(int manner) {
		this.manner = manner;
	}

	public List<ClassPara> getClassPara() {
		return classPara;
	}

	public void setClassPara(List<ClassPara> classPara) {
		this.classPara = classPara;
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

	public String getResultPage() {
		return resultPage;
	}

	public void setResultPage(String resultPage) {
		this.resultPage = resultPage;
	}

	public String getRoomids() {
		return roomids;
	}

	public void setRoomids(String roomids) {
		this.roomids = roomids;
	}

	public static Log getLogger() {
		return logger;
	}

	public ExamRoomPeiceDao getExamRoomPeiceDao() {
		return examRoomPeiceDao;
	}

	public void setExamRoomPeiceDao(ExamRoomPeiceDao examRoomPeiceDao) {
		this.examRoomPeiceDao = examRoomPeiceDao;
	}

	public int getStype() {
		return stype;
	}

	public void setStype(int stype) {
		this.stype = stype;
	}

	

	public int getPt() {
		return pt;
	}

	public void setPt(int pt) {
		this.pt = pt;
	}

	public float getWpeice() {
		return wpeice;
	}

	public void setWpeice(float wpeice) {
		this.wpeice = wpeice;
	}

	public String getUpd() {
		return upd;
	}

	public void setUpd(String upd) {
		this.upd = upd;
	}

	public int getBiaoshi() {
		return biaoshi;
	}

	public void setBiaoshi(int biaoshi) {
		this.biaoshi = biaoshi;
	}

	public int getSetstatus() {
		return setstatus;
	}

	public void setSetstatus(int setstatus) {
		this.setstatus = setstatus;
	}

	public String getIfadmin() {
		return ifadmin;
	}

	public void setIfadmin(String ifadmin) {
		this.ifadmin = ifadmin;
	}

	public ELUser getCreater() {
		return creater;
	}

	public void setCreater(ELUser creater) {
		this.creater = creater;
	}

	public List<ExamRoomPeice> getExamRooms() {
		return examRooms;
	}

	public void setExamRooms(List<ExamRoomPeice> examRooms) {
		this.examRooms = examRooms;
	}

	public List<ExamRoom> getExamRoomes() {
		return examRoomes;
	}

	public void setExamRoomes(List<ExamRoom> examRoomes) {
		this.examRoomes = examRoomes;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	public ShoppingDao getShoppingDao() {
		return shoppingDao;
	}


	public void setShoppingDao(ShoppingDao shoppingDao) {
		this.shoppingDao = shoppingDao;
	}


	public boolean isAudit() {
		return audit;
	}


	public void setAudit(boolean audit) {
		this.audit = audit;
	}


	public CourseComment getUserComment() {
		return userComment;
	}


	public void setUserComment(CourseComment userComment) {
		this.userComment = userComment;
	}


	public ShoppingCartDao getShoppingCartDao() {
		return shoppingCartDao;
	}


	public void setShoppingCartDao(ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}


	public int getMyclass() {
		return myclass;
	}


	public void setMyclass(int myclass) {
		this.myclass = myclass;
	}


	public int getMyclassorder() {
		return myclassorder;
	}


	public void setMyclassorder(int myclassorder) {
		this.myclassorder = myclassorder;
	}


	


	public StudyClassDao getStudyClassDao() {
		return studyClassDao;
	}


	public void setStudyClassDao(StudyClassDao studyClassDao) {
		this.studyClassDao = studyClassDao;
	}


	public CourseComment getCourseComment() {
		return courseComment;
	}


	public void setCourseComment(CourseComment courseComment) {
		this.courseComment = courseComment;
	}


	public List<CourseComment> getListcc() {
		return listcc;
	}


	public void setListcc(List<CourseComment> listcc) {
		this.listcc = listcc;
	}


	public CourseCommentDao getCourseCommentDao() {
		return courseCommentDao;
	}


	public void setCourseCommentDao(CourseCommentDao courseCommentDao) {
		this.courseCommentDao = courseCommentDao;
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

	public ExamRoomPeice getExamRoomPeice() {
		return examRoomPeice;
	}

	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}

	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}

	public void setExamRoomPeice(ExamRoomPeice examRoomPeice) {
		this.examRoomPeice = examRoomPeice;
	}

	public int getIsOnload() {
		return isOnload;
	}

	public void setIsOnload(int isOnload) {
		this.isOnload = isOnload;
	}

	public List<MyClass> getMyClasses() {
		return myClasses;
	}

	public void setMyClasses(List<MyClass> myClasses) {
		this.myClasses = myClasses;
	}

	

	public EroomLib getCltypeTree() {
		return cltypeTree;
	}

	public void setCltypeTree(EroomLib cltypeTree) {
		this.cltypeTree = cltypeTree;
	}

	public EroomLib getCltype() {
		return cltype;
	}

	public void setCltype(EroomLib cltype) {
		this.cltype = cltype;
	}


	public List<ExamRoom> getElclasses() {
		return elclasses;
	}

	public void setElclasses(List<ExamRoom> elclasses) {
		this.elclasses = elclasses;
	}

	public List<ElClass> getElclassesnot() {
		return elclassesnot;
	}

	public void setElclassesnot(List<ElClass> elclassesnot) {
		this.elclassesnot = elclassesnot;
	}

	public CoursePage getCoursePage() {
		return coursePage;
	}

	public void setCoursePage(CoursePage coursePage) {
		this.coursePage = coursePage;
	}

	public StringBuffer getExplain() {
		return explain;
	}

	public void setExplain(StringBuffer explain) {
		this.explain = explain;
	}

	public int getIsCorrespond() {
		return isCorrespond;
	}

	public void setIsCorrespond(int isCorrespond) {
		this.isCorrespond = isCorrespond;
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

	public int getEroomepIspass() {
		return eroomepIspass;
	}

	public void setEroomepIspass(int eroomepIspass) {
		this.eroomepIspass = eroomepIspass;
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

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public ScheduleGlobleDao getScheduleGlobleDao() {
		return scheduleGlobleDao;
	}

	public void setScheduleGlobleDao(ScheduleGlobleDao scheduleGlobleDao) {
		this.scheduleGlobleDao = scheduleGlobleDao;
	}
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public ProductDao getProductDao() {
		return productDao;
	}
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	public ProductType getPtypeTree() {
		return ptypeTree;
	}
	public void setPtypeTree(ProductType ptypeTree) {
		this.ptypeTree = ptypeTree;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public IndexDao getIndexDao() {
		return indexDao;
	}
	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}
	public PfmsUser getPfmsUser() {
		return pfmsUser;
	}
	public void setPfmsUser(PfmsUser pfmsUser) {
		this.pfmsUser = pfmsUser;
	}
	public List<ForumCourseClub> getListList() {
		return listList;
	}
	public void setListList(List<ForumCourseClub> listList) {
		this.listList = listList;
	}
	public BookInfoDao getBookInfoDao() {
		return bookInfoDao;
	}
	public void setBookInfoDao(BookInfoDao bookInfoDao) {
		this.bookInfoDao = bookInfoDao;
	}
	public BookTypeTree getBookTypeTree() {
		return bookTypeTree;
	}
	public void setBookTypeTree(BookTypeTree bookTypeTree) {
		this.bookTypeTree = bookTypeTree;
	}
	public String getSbookinfo() {
		return sbookinfo;
	}
	public void setSbookinfo(String sbookinfo) {
		this.sbookinfo = sbookinfo;
	}
	public List<Bookinfo> getListb() {
		return listb;
	}
	public void setListb(List<Bookinfo> listb) {
		this.listb = listb;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNametype() {
		return nametype;
	}
	public void setNametype(int nametype) {
		this.nametype = nametype;
	}
	public int getMycourse() {
		return mycourse;
	}
	public void setMycourse(int mycourse) {
		this.mycourse = mycourse;
	}
	public int getMycourseorder() {
		return mycourseorder;
	}
	public void setMycourseorder(int mycourseorder) {
		this.mycourseorder = mycourseorder;
	}
	public int getShoppingCount() {
		return shoppingCount;
	}
	public void setShoppingCount(int shoppingCount) {
		this.shoppingCount = shoppingCount;
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
	public List<News> getZxNotices() {
		return zxNotices;
	}
	public void setZxNotices(List<News> zxNotices) {
		this.zxNotices = zxNotices;
	}
	public ForumCourseDao getForumcourseDao() {
		return forumcourseDao;
	}
	public void setForumcourseDao(ForumCourseDao forumcourseDao) {
		this.forumcourseDao = forumcourseDao;
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
	public List<CourseType> getCtls() {
		return ctls;
	}
	public void setCtls(List<CourseType> ctls) {
		this.ctls = ctls;
	}

	public List<ForumExamRoomClub> getClasslistList() {
		return classlistList;
	}

	public void setClasslistList(List<ForumExamRoomClub> classlistList) {
		this.classlistList = classlistList;
	}

	public int getMybuyroom() {
		return mybuyroom;
	}

	public void setMybuyroom(int mybuyroom) {
		this.mybuyroom = mybuyroom;
	}
	

}
