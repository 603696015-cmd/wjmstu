package com.sopia.wjm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.batchman.dao.BatchDao;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.AuthorityNewVersionUtil;
import com.sopia.common.ElException;
import com.sopia.common.office.ExcelOutPut;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CoursePageDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.EroomBatch;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.intelligentTutoringPoints.IntelligentTutoringPointsConstants;
import com.sopia.intelligentTutoringPoints.IntelligentTutoringPointsUtil;
import com.sopia.intelligentTutoringPoints.dao.IntelligentLoginDao;
import com.sopia.intelligentTutoringPoints.dao.IntelligentTutoringPointsDao;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLogin;
import com.sopia.intelligentTutoringPoints.entities.IntelligentTutoringPoints;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.peixunBatch.dao.PeixunBatchDao;
import com.sopia.peixunBatch.entities.PeixunBatch;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.statman.dao.StatisticQuizDao;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.wjm.WjmElconstants;
import com.sopia.wjm.dao.ClassificationDao;
import com.sopia.wjm.entities.Classification;
import com.sopia.wjm.entities.ELUserClassification;
/**
 * 
 * @author taomingke
 *
 */
public class ClassificationAction extends BaseAction{

	private static final Log logger = LogFactory
			.getLog(ClassificationAction.class);
	private IntelligentTutoringPointsDao intelligentTutoringPointsDao;
	private IntelligentTutoringPoints intelligentTutoringPoints;
	private List<Classification> classifications;
	private Classification classification;
	private ClassificationDao classificationDao;
	private EroomDao eroomDao;
	private ExamRoom examRoom;
	private ExamRoom dingjiExamRoom;
	private List<ExamRoom> examRooms;
	private int count;
	private EroomLib eroomLibTree;
	private int sublibs;
	private int roomid ;
	
	private String module;
	private String Return;
	private List<ElFunc> menus;
	private List<ElFunc> menus_three;
	private ElFunc menu;
	private RoleDao roleDao;
	private Department department;
	private Department depTree;
	private int sub_department;
	private NewsType ntypeTree;
	private NewsType ntype;
	private ELUser elUser;
	private boolean inDingjiRoom;
	private MyRoom myroom;
	private StudyQuizDao studyQuizDao;
	private PeixunBatchDao peixunBatchDao;
	private PeixunBatch peixunBatch;
	private List<ElClass> elclasses;
	private MyExamPaper myExamPaper;
	private BatchDao batchDao;
	private ClassDao classDao;
	private ElClass elClass;
	private List<MyCourse> myCourses;
	private CourseDao courseDao;
	private Course course;
	private List<MyCPage> myCPages;
	private StudyCourseDao studyCourseDao;
	private CoursePageDao coursePageDao;
	private List<MyClass> myclasses;
	private StatisticQuizDao statisticQuizDao;
	private MyCourse mycourse;
	private float intelligentPoints;//智能辅导分
	
	private Department searchDep;
	private ELUser searchUser;
	private List<ELUser> elUsers;
	private boolean exprot;
	private String returnIds;
	private ExamPaperDao examPaperDao;
	private int epid;
	private ELUserClassification elUserClassification;
	private boolean classificationInfo;
	private IntelligentLogin intelligentLogin;
	private Course nowCourse;
	private int time;
	
	private List<EroomBatch> erbatchs;
	private EroomBatch erbatch;
	private IntelligentLoginDao intelligentLoginDao;
	private int init;
	private int nowCourseid;
	private boolean precCourseOver;//上一门课程是否通过
	private boolean initCompliance;
	private int status;
	
	/**
	 * 初始定级标准
	 * 
	 * @return
	 * @throws ElException
	 */
	public String init_classification_standard() throws ElException {
		classifications = classificationDao.list_classification();
		int roomid = classificationDao.getRoomid();
		if(roomid!=0){
			examRoom = eroomDao.getExamRoomByid(roomid);
		}
		//获取定级系统考场
		return "init_classification_standard";
	}
	
	/**
	 * 设置定级标准
	 * @return
	 * @throws ElException
	 */
	public String setClassification() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		classificationDao.updateClassificationByName(classification);
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 设置定级考场
	 * @return
	 * @throws ElException
	 */
	public String updateRoomid() throws ElException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		classificationDao.updateRoomid(roomid);
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 选择定级考场
	 * @return
	 * @throws ElException
	 */
	public String examroom_alllist_wjm() throws ElException{
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
			examRoom.setClassid(-10);
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
		examRooms = eroomDao.listExamRoom(examRoom.getEroomLib(), sublibs, sqlw,
				examRoom, getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize(examRoom.getEroomLib(), sublibs, sqlw,
				examRoom);
		return "examroom_alllist_wjm";
	}
	
	//外经贸个人中心导航页面
	public String wjm_user_center_navigation() throws ElException{
		String sInit = getRequest().getParameter("init");
		if(sInit != null && sInit.equals("1")){
			init = 1;
		}
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		if(roomid==0){
			roomid = classificationDao.getRoomid();
			dingjiExamRoom = eroomDao.getExamRoomByid(roomid);
			dingjiExamRoom.setExamPaper(examPaperDao.getExamPaperByRoomId(roomid).get(0));
			//获取用户定级信息表
			elUserClassification = classificationDao.getElUserClassificationByUserid(elUser.getId(),roomid);
			if(elUserClassification==null){
				elUserClassification = new ELUserClassification();
				elUserClassification.setType(0);
				elUserClassification.setTime(0);
				elUserClassification.setStatus(0);
			}
		}
		
		Integer loginId = (Integer)getSession().getAttribute(IntelligentTutoringPointsConstants.SESSION_LOGINID);
		if(loginId!=null && loginId>0){
			intelligentLogin = intelligentLoginDao.getLoginInfoByLoginid(loginId);
		}
		if(intelligentLogin == null){
			intelligentLogin = new IntelligentLogin();
		}
		//判断是否定级  及  智能辅导分得分
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		if(peixunBatchDao.checkPeixunBatchIsAssignToUser(peixunBatch.getId(), elUser.getId())){
			inDingjiRoom = true;
			//设置正在学习的培训班信息
			peixunBatch.setNowClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),0));
			intelligentPoints = IntelligentTutoringPointsUtil.intelligentTutoringPoints(elUser.getId());
		}
		return "wjm_user_center_navigation";
	}
	
	//新版外经贸个人中心
	public String wjm_user_center_new() throws ElException{
		module =module==null? "wjm_user_center_index_new.action":module;
		Return = "studentman";
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		peixunBatch.setProcess(peixunBatchDao.getPeixunBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID)));
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		//培训批次中培训班的培训进度
		if(peixunBatchDao.checkPeixunBatchIsAssignToUser(peixunBatch.getId(), elUser.getId())){
			inDingjiRoom = true;
		}
		myclasses = myclasses == null?new ArrayList<MyClass>():myclasses;
		if(inDingjiRoom ){
			//更新培训批次中被分配的培训班的进度
			classDao.updateClassProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			//更新培训批次进度
			peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			myclasses = peixunBatchDao.getMyBatchDetail(elUser.getId());//我的培训班列表
			myclasses = myclasses == null?new ArrayList<MyClass>():myclasses;
			if(myclasses.size()>0){
				for(int i=0;i<myclasses.size();i++){
					if(myclasses.get(i).getExamRoom()!=null && myclasses.get(i).getExamRoom().getId()>0){//培训班存在考场
						if(myclasses.get(i).getSortid() > 1){//判断上一个培训班考试是否通过
							myclasses.get(i).setCanLearn(classDao.checkClassCanLearn(myclasses.get(i).getElClass().getId(),myclasses.get(i).getSortid()-1,getSessionIntValue(ElConstants.SESSION_USERID),peixunBatch.getId()));
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}else{//培训班考场不存在
						if(myclasses.get(i).getSortid() > 1){
							myclasses.get(i).setCanLearn(classDao.checkClassCanLearn(myclasses.get(i).getElClass().getId(),myclasses.get(i).getSortid()-1,getSessionIntValue(ElConstants.SESSION_USERID),peixunBatch.getId()));
//							myclasses.get(i).setCanLearn(myclasses.get(i-1).getProcessForElc()==100?true:false);//培训班考场不存在情况下，只需要判断培训班进度是否为100
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}
				}
			}
		}
		return "wjm_user_center_new";
	}
	//新版外经贸个人中心frame
	public String wjm_user_center_index_new() throws ElException{
		//用户当前学习等级的智能辅导分
		intelligentPoints = IntelligentTutoringPointsUtil.intelligentTutoringPoints(getSessionIntValue(ElConstants.SESSION_USERID));
		
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		if(roomid==0){
			roomid = classificationDao.getRoomid();
			dingjiExamRoom = eroomDao.getExamRoomByid(roomid);
			dingjiExamRoom.setExamPaper(examPaperDao.getExamPaperByRoomId(roomid).get(0));
			//获取用户定级信息表
			elUserClassification = classificationDao.getElUserClassificationByUserid(elUser.getId(),roomid);
			if(elUserClassification==null){
				elUserClassification = new ELUserClassification();
				elUserClassification.setType(0);
				elUserClassification.setTime(0);
				elUserClassification.setStatus(0);
			}
		}
		//获取培训批次
		//当前系统培训批次默认id为1
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		peixunBatch.setProcess(peixunBatchDao.getPeixunBatchProcess(peixunBatch.getId(),elUser.getId()));
		//修改判断是否定级条件
		//只需要peixunBatch分配给用户即可
		if(peixunBatchDao.checkPeixunBatchIsAssignToUser(peixunBatch.getId(), elUser.getId())){
			inDingjiRoom = true;
		}
		int classid=0;
		//判断该课程是否是当前正在学习的课程
		if(inDingjiRoom ){
			//设置正在学习的培训班信息
			peixunBatch.setNowClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),0));
			if(peixunBatch.getNowClass()!=null){
				if(elClass==null || elClass.getId()<=0){
					classid = peixunBatch.getNowClass().getId();
				}else{
					classid = elClass.getId();
				}
				elClass = classDao.getClassById(classid);
				MyClass mcl = new MyClass();
				
				int classroomid = eroomDao.getRoomidByClassid_cisco(elClass.getId());
				if(classroomid>0){
					mcl.setCanExam(classDao.checkClassCanExam(elUser.getId(),elClass.getId(),classroomid));
					mcl.setExamRoom(new ExamRoom(classroomid));
					mcl.setHasExam(1);
				}else{
					mcl.setHasExam(0);
				}
				elClass.setMyClass(mcl);
				if(course!=null && course.getId()>0){
					nowCourseid = course.getId();
				}else{
					nowCourseid = courseDao.getNowCourseid(elClass.getId(),elUser.getId());
				}
				//判断上一门课程是否通过
				int pracCourseid = courseDao.getPrecCourseid(elClass.getId(),elUser.getId(),nowCourseid);
				if(pracCourseid == nowCourseid){
					precCourseOver = true;
				}else{
					precCourseOver = courseDao.checkCourseIsPass(elClass.getId(), elUser.getId(), pracCourseid);
				}
				myCourses = courseDao.listMyCoursees_wjm(classid,elUser.getId());
				boolean temp = false;
				if(myCourses!=null){
					if(elClass!=null && elClass.getLearnByOrder() == 1){//培训班中课程必须按照顺序来学习
						for(MyCourse mycourse:myCourses){
							//设置MyCourse的canLearn属性，只要是用来前台是否显示图片为黑色
							//需要判断课程是否已经学完、课程对应的结业考场是否已经通过
							temp = classDao.checkcoursecanlearn(mycourse.getCourse().getId(),elClass.getId(),elUser.getId());
							mycourse.setCanLearn(temp == true ? 1 : 0);
							//如果课程学习完成了，设置课程考试结束时间和考试成绩
							//还未测试
							if(mycourse.isPassed()){
								mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUser.getId(),mycourse);
							}
						}
					}
				}
				examRoom = eroomDao.getExamRoom(nowCourseid, elClass.getId());//课程结业考场
				if(nowCourseid>0){
					course = courseDao.getCourseById(nowCourseid);
					if(mycourse == null)	mycourse = new MyCourse();//我的学习课程
					if(course!=null && course.getId()>0){
						//正在学习课程的章节列表
						mycourse.setCourse(course);
						List<ExamPaper> examPapers = examPaperDao.listEroomExamPaper(examRoom.getId());
						for (int i = 0; i < examPapers.size(); i++) {
							if (examPapers.get(i).getStatus() != 1) {
								if (!studyQuizDao.checkStudyExamPaper(elUser.getId(), examPapers.get(i)
										.getId(), examRoom.getId(), elClass.getId())) {
									// 添加该学员到 学员试卷表中
									studyQuizDao.addStudyExamPaper(elUser.getId(), examPapers.get(i)
											.getId(), examRoom.getId(), elClass.getId());
								}
							}
						}
						
						//设置课程结业考场的试卷信息
						epid = studyQuizDao.getExamRoomid(examRoom.getId(),elUser.getId());
						if(epid != 0){
							//设置试卷信息
							mycourse.setMyExamPaper(studyQuizDao.getMyExampaper(elUser.getId(), examRoom.getId(), epid));
						}
						//设置完成时间和分数
						mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUser.getId(),mycourse);
						//获取章节列表
						myCPages = studyCourseDao.listCpsbyCUid_wjm(course.getId(),//课程的章节（不关联考场）
								elUser.getId(),elClass.getId());
						
						//判断章节是否都通过，设置myroom是否可以考试  
						if(examRoom != null){
							if(myroom == null)	myroom = new MyRoom();//我的考试
							myroom.setExamroom(examRoom);
							//废弃
							//课程的结业考场是否能考试已交给判断课程是否通过
							myroom.setCanExam(courseDao.checkCpagesIsAllPass(elClass.getId(),elUser.getId(),course.getId()));
							mycourse.setMyRoom(myroom);
						}
						//设置课程是否通过
						mycourse.setPassed(courseDao.checkCourseIsPass(elClass.getId(),elUser.getId(), course.getId()));
						mycourse.setExamPass(eroomDao.getIsPass(elUser.getId(), examRoom.getId()));
						
						myCPages = myCPages == null ? new ArrayList<MyCPage>():myCPages;
						//判断每个章节是否可以学习，根据章节顺序sortid
						//并设置是否可学属性
						MyExamPaper myExamPaper = null;
						CoursePage coursePage = null; 
						List<ExamPaper> pageExamPapers = null;
						for(MyCPage mycp:myCPages){
							coursePage = mycp.getCpage();
							//章节试卷分配判断
							pageExamPapers =  examPaperDao.listEroomExamPaper(mycp.getExamRoom().getId());
							for (int i = 0; i < pageExamPapers.size(); i++) {
								if (pageExamPapers.get(i).getStatus() != 1) {
									if (!studyQuizDao.checkStudyExamPaper(elUser.getId(), pageExamPapers.get(i)
											.getId(), mycp.getExamRoom().getId(), elClass.getId())) {
										// 添加该学员到 学员试卷表中
										studyQuizDao.addStudyExamPaper(elUser.getId(), pageExamPapers.get(i)
												.getId(), mycp.getExamRoom().getId(), elClass.getId());
									}
								}
							}
							
							//章节获取考场试卷信息（多个考场）
							mycp = eroomDao.getBindingExamRooms(mycp,elUser.getId());
							
							//章节绑定的每个考场获取答卷信息==>按照最高分、时间降序后取第一条
							if(mycp.getExamRooms()!=null ){
								boolean canExam = false;
								for(int i=0;i<mycp.getExamRooms().size();i++){
									myExamPaper = studyQuizDao.getMyExampaper(elUser.getId(), mycp.getExamRooms().get(i).getId(), mycp.getExamRooms().get(i).getExamPaper().getId());
									mycp.getExamRooms().get(i).setMyExamPaper(myExamPaper);
									//多个章节考场，判断可考或者不可考
									if(mycp.getExamRooms().get(i).getSortid()>1){
										canExam = eroomDao.setExamRoomCanExam(mycp.getExamRooms().get(i),course.getId(),elUser.getId());
									}else{
										if(mycp.isPassed()){
											canExam = true;
										}
									}
									mycp.getExamRooms().get(i).setCanExam(canExam==true?1:0);
								}
							}
							
							//判断章节
							//根据上面的章节信息，判断该章节的前一章节"是否可跳过"，如果是，则直接打开本章节
							//如果是否  1：有前一章；判断前一章完成状态，完成了才能打开本章节，未完成：提示"上一章节未完成学习，还不能进入本章节的学习"
							//		    2：直接打开本章节
							CoursePage beginCPage = null;
							if(coursePage.getSkipable() == 0){//必须判断
								if(coursePage.getSortid()>1){
									beginCPage = coursePageDao.getBeginCPage(course.getId(),mycp.getCpage().getSortid()-1);//有前一章信息
									if(coursePageDao.checkPageCanlearn(mycp.getCpage().getSortid()-1,course.getId(),elUser.getId(),beginCPage.getId())){
										mycp.setCanLearn(1);
									}
								}else{
									mycp.setCanLearn(1);
								}
							}else{
								mycp.setCanLearn(1);
							}
							//如果章节学习完成了，设置章节考试结束时间和考试成绩
							if(mycp.isPassed()){
								mycp = statisticQuizDao.getFinishtimeByScorePage(course.getId(),elUser.getId(),mycp);
							}
						}
					}
				}
			}
			//设置完成的培训班信息
			peixunBatch.setDoneClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),1));
			//更新培训批次中被分配的培训班的进度、更新培训批次进度  已交给章节学习、考试来更新
//			//更新培训批次中被分配的培训班的进度
//			classDao.updateClassProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
//			//更新培训批次进度
//			peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			//获取被分配的培训班(附带学习进度)
			myclasses = peixunBatchDao.getMyBatchDetail(elUser.getId());//我的培训班列表
			
			myclasses = myclasses == null?new ArrayList<MyClass>():myclasses;
			int canExam = 0;
			boolean finish = false;
			if(myclasses.size()>0){
				for(int i=0;i<myclasses.size();i++){
					//设置等级培训班进度
					if(elClass.getId() == myclasses.get(i).getElClass().getId()){
						elClass.getMyClass().setProcess(myclasses.get(i).getProcessForElc());
					}
					//判断培训班是否有考场、培训班考场是否通过
					//根据myclasses.get(i).getExamRoom()是否为空来判断培训班是否有考场
					//设置培训班是否能考试==判断本等级是否已经全部学完（即培训班中的课程章节考试全部完成）+智能辅导分是否达标
					if(myclasses.get(i).getExamRoom()!=null && myclasses.get(i).getExamRoom().getId()>0){//培训班存在考场
						canExam = classDao.checkClassCanExam(elUser.getId(),myclasses.get(i).getElClass().getId(),myclasses.get(i).getExamRoom().getId());
						if(myclasses.get(i).getSortid() > 1){//判断上一个培训班考试是否通过
							myclasses.get(i).setCanLearn(classDao.checkClassCanLearn(myclasses.get(i).getElClass().getId(),myclasses.get(i).getSortid()-1,elUser.getId(),peixunBatch.getId()));
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}else{//培训班考场不存在
						if(myclasses.get(i).getSortid() > 1){
							myclasses.get(i).setCanLearn(myclasses.get(i-1).getProcessForElc()==100?true:false);//培训班考场不存在情况下，只需要判断培训班进度是否为100
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}
					finish = classDao.checkClassExamIsPass(myclasses.get(i).getElClass().getId(), elUser.getId());
					myclasses.get(i).setFinish(finish);
					myclasses.get(i).setCanExam(canExam);
				}
			}
		}
		return "wjm_user_center_index_new";
	}

	//20140423修改
   private int backuptoindex;//是否为返回首页
	
	public int getBackuptoindex() {
	return backuptoindex;
	}
	
	public void setBackuptoindex(int backuptoindex) {
		this.backuptoindex = backuptoindex;
	}

	//外经贸个人中心
	public String wjm_user_center() throws ElException{
		String param = "";//构建classid和courseid参数
		if(elClass!=null && elClass.getId()>0){
			param += "elClass.id=" + elClass.getId();
		}
		if(course!=null && course.getId()>0){
			if(!param.equals("")){
				param += "&course.id=" + course.getId();
			}else{
				param += "course.id=" + course.getId();
			}
		}
		if((elClass!=null && elClass.getId()>0) || (course!=null && course.getId()>0)){
			logger.info(backuptoindex);
			if(backuptoindex==1){
			module =module==null? "wjm_user_center_index.action?"+param:module;
			}else{
			module =module==null? "mystudy_course_view_wjm_front.action?"+param:module;
			}
		}else{
			module =module==null? "wjm_user_center_index.action":module;
//			module =module==null? "mystudy_course_view_wjm_front.action?":module;
		}
		logger.info(module);
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		peixunBatch.setProcess(peixunBatchDao.getPeixunBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID)));
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		//培训批次中培训班的培训进度
		if(peixunBatchDao.checkPeixunBatchIsAssignToUser(peixunBatch.getId(), elUser.getId())||classificationDao.isDingji(getSessionIntValue(ElConstants.SESSION_USERID))){
			inDingjiRoom = true;
		}
		if(inDingjiRoom ){
			//获取定的级别
			
			//更新培训批次中被分配的培训班的进度
			classDao.updateClassProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			//更新培训批次进度
			peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			int classid=0;
			//判断该课程是否是当前正在学习的课程
			int nowCourseid = 0;
			//设置正在学习的培训班信息
			peixunBatch.setNowClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),0));
			if(peixunBatch.getNowClass()!=null){
				if(elClass==null || elClass.getId()<=0){
					classid = peixunBatch.getNowClass().getId();
				}else{
					classid = elClass.getId();
				}
				elClass = classDao.getClassById(classid);
				//判断等级是否在定级前
				ELUserClassification elUserClassification = classificationDao.getElUserClassificationByUserid(getSessionIntValue(ElConstants.SESSION_USERID), roomid = classificationDao.getRoomid());
				if(elClass!=null && elClass.getName()!=null && !elClass.getName().equals("") && elClass.getName().compareTo(elUserClassification.getName())<0){
					initCompliance = true;
				}
				MyClass mcl = new MyClass();
				
				int classroomid = eroomDao.getRoomidByClassid_cisco(elClass.getId());
				List<ExamPaper> examPapers = examPaperDao.listEroomExamPaper(classroomid);
				for (int i = 0; i < examPapers.size(); i++) {
					if (examPapers.get(i).getStatus() != 1) {
						if (!studyQuizDao.checkStudyExamPaper(elUser.getId(), examPapers.get(i)
								.getId(), classroomid, elClass.getId())) {
							// 添加该学员到 学员试卷表中
							studyQuizDao.addStudyExamPaper(elUser.getId(), examPapers.get(i)
									.getId(), classroomid, elClass.getId());
						}
					}
				}
				if(classroomid>0){
					//将ab等级和在一起查看20140703
					 classname=intelligentTutoringPointsDao.getElClssName(elClass.getId());
						if(classname!=null&&!"".equals(classname)){
							classname= classname.substring(0,1);
							 logger.info(classname);
						}
						logger.info(classname);
					List<String> elClassIds = intelligentTutoringPointsDao.getElClssList(classname);
					mcl.setCanExam(classDao.checkClassCanExam_new(elUser.getId(),Integer.parseInt(elClassIds.get(0)),Integer.parseInt(elClassIds.get(1)),classroomid));
//					mcl.setCanExam(classDao.checkClassCanExam(elUser.getId(),elClass.getId(),classroomid));
					mcl.setExamRoom(new ExamRoom(classroomid));
					System.out.println(studyQuizDao.getExamRoomid(classroomid,elUser.getId()));
					mcl.getExamRoom().setExamPaper(new ExamPaper(studyQuizDao.getExamRoomid(classroomid,elUser.getId())));
					mcl.setHasExam(1);
				}else{
					mcl.setHasExam(0);
				}
				elClass.setMyClass(mcl);
				if(course!=null && course.getId()>0){
					nowCourseid = course.getId();
				}else{
					nowCourseid = courseDao.getNowCourseid(elClass.getId(),elUser.getId());
				}
				myCourses = courseDao.listMyCoursees_wjm(classid,elUser.getId());
				boolean temp = false;
				if(myCourses!=null){
					if(elClass!=null && elClass.getLearnByOrder() == 1){//培训班中课程必须按照顺序来学习
						for(MyCourse mycourse:myCourses){
							//设置MyCourse的canLearn属性，只要是用来前台是否显示图片为黑色
							//需要判断课程是否已经学完、课程对应的结业考场是否已经通过
							temp = classDao.checkcoursecanlearn(mycourse.getCourse().getId(),elClass.getId(),elUser.getId());
							mycourse.setCanLearn(temp == true ? 1 : 0);
							//如果课程学习完成了，设置课程考试结束时间和考试成绩
							//还未测试
							if(mycourse.isPassed()){
								mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUser.getId(),mycourse);
							}
						}
					}
				}
				examRoom = eroomDao.getExamRoom(nowCourseid, elClass.getId());//课程结业考场
				if(nowCourseid>0){
					course = courseDao.getCourseById(nowCourseid);
					if(mycourse == null)	mycourse = new MyCourse();//我的学习课程
					if(course!=null && course.getId()>0){
						//正在学习课程的章节列表
						mycourse.setCourse(course);
						
						//设置课程结业考场的试卷信息
						epid = studyQuizDao.getExamRoomid(examRoom.getId(),elUser.getId());
						if(epid != 0){
							//设置试卷信息
							mycourse.setMyExamPaper(studyQuizDao.getMyExampaper(elUser.getId(), examRoom.getId(), epid));
						}
						//设置完成时间和分数
						mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUser.getId(),mycourse);
						
						//判断章节是否都通过，设置myroom是否可以考试  
						if(examRoom != null){
							if(myroom == null)	myroom = new MyRoom();//我的考试
							myroom.setExamroom(examRoom);
							//废弃
							//课程的结业考场是否能考试已交给判断课程是否通过
							myroom.setCanExam(courseDao.checkCpagesIsAllPass(elClass.getId(),elUser.getId(),course.getId()));
							mycourse.setMyRoom(myroom);
						}
						//设置课程是否通过
						mycourse.setPassed(courseDao.checkCourseIsPass(elClass.getId(),elUser.getId(), course.getId()));
						mycourse.setExamPass(eroomDao.getIsPass(elUser.getId(), examRoom.getId()));
					}
				}
			}
			//设置完成的培训班信息
			peixunBatch.setDoneClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),1));
			//获取被分配的培训班(附带学习进度)
			myclasses = peixunBatchDao.getMyBatchDetail(elUser.getId());//我的培训班列表
			
			myclasses = myclasses == null?new ArrayList<MyClass>():myclasses;
			int canExam = 0;
			boolean finish = false;
			if(myclasses.size()>0){
				for(int i=0;i<myclasses.size();i++){
					//设置等级培训班进度
					if(elClass.getId() == myclasses.get(i).getElClass().getId()){
						elClass.getMyClass().setProcess(myclasses.get(i).getProcessForElc());
					}
					//判断培训班是否有考场、培训班考场是否通过
					//根据myclasses.get(i).getExamRoom()是否为空来判断培训班是否有考场
					//设置培训班是否能考试==判断本等级是否已经全部学完（即培训班中的课程章节考试全部完成）+智能辅导分是否达标
					if(myclasses.get(i).getExamRoom()!=null && myclasses.get(i).getExamRoom().getId()>0){//培训班存在考场
						//将ab等级和在一起查看20140703
						 classname=intelligentTutoringPointsDao.getElClssName(myclasses.get(i).getElClass().getId());
							if(classname!=null&&!"".equals(classname)){
								classname= classname.substring(0,1);
								 logger.info(classname);
							}
							logger.info(classname);
						List<String> elClassIds = intelligentTutoringPointsDao.getElClssList(classname);
//						canExam = classDao.checkClassCanExam(elUser.getId(),myclasses.get(i).getElClass().getId(),myclasses.get(i).getExamRoom().getId());
						canExam = classDao.checkClassCanExam_new(elUser.getId(),Integer.parseInt(elClassIds.get(0)),Integer.parseInt(elClassIds.get(1)),myclasses.get(i).getExamRoom().getId());
						if(myclasses.get(i).getSortid() > 1){//判断上一个培训班考试是否通过
							myclasses.get(i).setCanLearn(classDao.checkClassCanLearn(myclasses.get(i).getElClass().getId(),myclasses.get(i).getSortid()-1,elUser.getId(),peixunBatch.getId()));
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}else{//培训班考场不存在
						if(myclasses.get(i).getSortid() > 1){
							myclasses.get(i).setCanLearn(myclasses.get(i-1).getProcessForElc()==100?true:false);//培训班考场不存在情况下，只需要判断培训班进度是否为100
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}
					finish = classDao.checkClassExamIsPass(myclasses.get(i).getElClass().getId(), elUser.getId());
					myclasses.get(i).setFinish(finish);
					myclasses.get(i).setCanExam(canExam);
				}
			}
		}
		return "wjm_user_center";
	}
	//外经贸个人中心frame
	public String wjm_user_center_index() throws ElException{
		//用户当前学习等级的智能辅导分
		intelligentPoints = IntelligentTutoringPointsUtil.intelligentTutoringPoints(getSessionIntValue(ElConstants.SESSION_USERID));
		
		/**
		 * 董克2015年8月22日新加的
		 */
		Integer loginId = (Integer)getSession().getAttribute(IntelligentTutoringPointsConstants.SESSION_LOGINID);
		if(loginId!=null && loginId>0){
			intelligentLogin = intelligentLoginDao.getLoginInfoByLoginid(loginId);
		}
		
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		if(roomid==0){
			roomid = classificationDao.getRoomid();
			dingjiExamRoom = eroomDao.getExamRoomByid(roomid);
			dingjiExamRoom.setExamPaper(examPaperDao.getExamPaperByRoomId(roomid).get(0));
			//获取用户定级信息表
			elUserClassification = classificationDao.getElUserClassificationByUserid(elUser.getId(),roomid);
			if(elUserClassification==null){
				elUserClassification = new ELUserClassification();
				elUserClassification.setType(0);
				elUserClassification.setTime(0);
				elUserClassification.setStatus(0);
			}
		}
		//获取培训批次
		//当前系统培训批次默认id为1
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		peixunBatch.setProcess(peixunBatchDao.getPeixunBatchProcess(peixunBatch.getId(),elUser.getId()));
		//修改判断是否定级条件
		//只需要peixunBatch分配给用户即可
		if(peixunBatchDao.checkPeixunBatchIsAssignToUser(peixunBatch.getId(), elUser.getId())||classificationDao.isDingji(getSessionIntValue(ElConstants.SESSION_USERID))){
			inDingjiRoom = true;
		}
		int classid=0;
		//判断该课程是否是当前正在学习的课程
		int nowCourseid = 0;
		if(inDingjiRoom ){
			//设置正在学习的培训班信息
			peixunBatch.setNowClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),0));
			if(peixunBatch.getNowClass()!=null){
				if(elClass==null || elClass.getId()<=0){
					classid = peixunBatch.getNowClass().getId();
				}else{
					classid = elClass.getId();
				}
				elClass = classDao.getClassById(classid);
				//判断等级是否在定级前
				ELUserClassification elUserClassification = classificationDao.getElUserClassificationByUserid(getSessionIntValue(ElConstants.SESSION_USERID), roomid = classificationDao.getRoomid());
				if(elClass!=null && elClass.getName()!=null && !elClass.getName().equals("") && elClass.getName().compareTo(elUserClassification.getName())<0){
					initCompliance = true;
				}
				MyClass mcl = new MyClass();
				
				int classroomid = eroomDao.getRoomidByClassid_cisco(elClass.getId());
				if(classroomid>0){
					mcl.setCanExam(classDao.checkClassCanExam(elUser.getId(),elClass.getId(),classroomid));
					mcl.setExamRoom(new ExamRoom(classroomid));
					mcl.setHasExam(1);
				}else{
					mcl.setHasExam(0);
				}
				elClass.setMyClass(mcl);
				if(course!=null && course.getId()>0){
					nowCourseid = course.getId();
				}else{
					nowCourseid = courseDao.getNowCourseid(elClass.getId(),elUser.getId());
				}
				myCourses = courseDao.listMyCoursees_wjm(classid,elUser.getId());
				boolean temp = false;
				if(myCourses!=null){
					if(elClass!=null && elClass.getLearnByOrder() == 1){//培训班中课程必须按照顺序来学习
						for(MyCourse mycourse:myCourses){
							//设置MyCourse的canLearn属性，只要是用来前台是否显示图片为黑色
							//需要判断课程是否已经学完、课程对应的结业考场是否已经通过
							temp = classDao.checkcoursecanlearn(mycourse.getCourse().getId(),elClass.getId(),elUser.getId());
							mycourse.setCanLearn(temp == true ? 1 : 0);
							//如果课程学习完成了，设置课程考试结束时间和考试成绩
							//还未测试
							if(mycourse.isPassed()){
								mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUser.getId(),mycourse);
							}
						}
					}
				}
				examRoom = eroomDao.getExamRoom(nowCourseid, elClass.getId());//课程结业考场
				if(nowCourseid>0){
					course = courseDao.getCourseById(nowCourseid);
					if(mycourse == null)	mycourse = new MyCourse();//我的学习课程
					if(course!=null && course.getId()>0){
						//正在学习课程的章节列表
						mycourse.setCourse(course);
						List<ExamPaper> examPapers = examPaperDao.listEroomExamPaper(examRoom.getId());
						for (int i = 0; i < examPapers.size(); i++) {
							if (examPapers.get(i).getStatus() != 1) {
								if (!studyQuizDao.checkStudyExamPaper(elUser.getId(), examPapers.get(i)
										.getId(), examRoom.getId(), elClass.getId())) {
									// 添加该学员到 学员试卷表中
									studyQuizDao.addStudyExamPaper(elUser.getId(), examPapers.get(i)
											.getId(), examRoom.getId(), elClass.getId());
								}
							}
						}
						
						//设置课程结业考场的试卷信息
						epid = studyQuizDao.getExamRoomid(examRoom.getId(),elUser.getId());
						if(epid != 0){
							//设置试卷信息
							mycourse.setMyExamPaper(studyQuizDao.getMyExampaper(elUser.getId(), examRoom.getId(), epid));
						}
						//设置完成时间和分数
						mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUser.getId(),mycourse);
						//获取章节列表
						myCPages = studyCourseDao.listCpsbyCUid_wjm(course.getId(),
								elUser.getId(),elClass.getId());
						
						//判断章节是否都通过，设置myroom是否可以考试  
						if(examRoom != null){
							if(myroom == null)	myroom = new MyRoom();//我的考试
							myroom.setExamroom(examRoom);
							//废弃
							//课程的结业考场是否能考试已交给判断课程是否通过
							myroom.setCanExam(courseDao.checkCpagesIsAllPass(elClass.getId(),elUser.getId(),course.getId()));
							mycourse.setMyRoom(myroom);
						}
						//设置课程是否通过
						mycourse.setPassed(courseDao.checkCourseIsPass(elClass.getId(),elUser.getId(), course.getId()));
						mycourse.setExamPass(eroomDao.getIsPass(elUser.getId(), examRoom.getId()));
						
						myCPages = myCPages == null ? new ArrayList<MyCPage>():myCPages;
						//判断每个章节是否可以学习，根据章节顺序sortid
						//并设置是否可学属性
						MyExamPaper myExamPaper = null;
						CoursePage coursePage = null; 
						List<ExamPaper> pageExamPapers = null;
						for(MyCPage mycp:myCPages){
							coursePage = mycp.getCpage();
							//章节试卷分配判断
							pageExamPapers =  examPaperDao.listEroomExamPaper(mycp.getExamRoom().getId());
							for (int i = 0; i < pageExamPapers.size(); i++) {
								if (pageExamPapers.get(i).getStatus() != 1) {
									if (!studyQuizDao.checkStudyExamPaper(elUser.getId(), pageExamPapers.get(i)
											.getId(), mycp.getExamRoom().getId(), elClass.getId())) {
										// 添加该学员到 学员试卷表中
										studyQuizDao.addStudyExamPaper(elUser.getId(), pageExamPapers.get(i)
												.getId(), mycp.getExamRoom().getId(), elClass.getId());
									}
								}
							}
							//章节获取考场试卷信息（多个考场）
							mycp = eroomDao.getBindingExamRooms(mycp,elUser.getId());
							
							//章节绑定的每个考场获取答卷信息==>按照最高分、时间降序后取第一条
							if(mycp.getExamRooms()!=null ){
								boolean canExam = false;
								for(int i=0;i<mycp.getExamRooms().size();i++){
									myExamPaper = studyQuizDao.getMyExampaper(elUser.getId(), mycp.getExamRooms().get(i).getId(), mycp.getExamRooms().get(i).getExamPaper().getId());
									mycp.getExamRooms().get(i).setMyExamPaper(myExamPaper);
									//多个章节考场，判断可考或者不可考
									if(mycp.getExamRooms().get(i).getSortid()>1){
										canExam = eroomDao.setExamRoomCanExam(mycp.getExamRooms().get(i),course.getId(),elUser.getId());
									}else{
										if(mycp.getCpage().getIsNull()==1){
											canExam = true;
										}else{
											if(mycp.isPassed()){
												canExam = true;
											}
										}
									}
									mycp.getExamRooms().get(i).setCanExam(canExam==true?1:0);
								}
							}
							
							//判断章节
							//根据上面的章节信息，判断该章节的前一章节"是否可跳过"，如果是，则直接打开本章节
							//如果是否  1：有前一章；判断前一章完成状态，完成了才能打开本章节，未完成：提示"上一章节未完成学习，还不能进入本章节的学习"
							//		    2：直接打开本章节
							CoursePage beginCPage = null;
							if(coursePage.getSkipable() == 0){//必须判断
								if(coursePage.getSortid()>1){
									beginCPage = coursePageDao.getBeginCPage(course.getId(),mycp.getCpage().getSortid()-1);//有前一章信息
									if(coursePageDao.checkPageCanlearn(mycp.getCpage().getSortid()-1,course.getId(),elUser.getId(),beginCPage.getId())){
										mycp.setCanLearn(1);
									}
								}else{
									mycp.setCanLearn(1);
								}
							}else{
								mycp.setCanLearn(1);
							}
							//如果章节学习完成了，设置章节考试结束时间和考试成绩
							if(mycp.isPassed()){
								mycp = statisticQuizDao.getFinishtimeByScorePage(course.getId(),elUser.getId(),mycp);
							}
						}
					}
				}
			}
			//设置完成的培训班信息
			peixunBatch.setDoneClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),1));
			//更新培训批次中被分配的培训班的进度、更新培训批次进度  已交给章节学习、考试来更新
//			//更新培训批次中被分配的培训班的进度
//			classDao.updateClassProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
//			//更新培训批次进度
//			peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			//获取被分配的培训班(附带学习进度)
			myclasses = peixunBatchDao.getMyBatchDetail(elUser.getId());//我的培训班列表
			
			myclasses = myclasses == null?new ArrayList<MyClass>():myclasses;
			int canExam = 0;
			boolean finish = false;
			if(myclasses.size()>0){
				for(int i=0;i<myclasses.size();i++){
					//设置等级培训班进度
					if(elClass.getId() == myclasses.get(i).getElClass().getId()){
						elClass.getMyClass().setProcess(myclasses.get(i).getProcessForElc());
					}
					//判断培训班是否有考场、培训班考场是否通过
					//根据myclasses.get(i).getExamRoom()是否为空来判断培训班是否有考场
					//设置培训班是否能考试==判断本等级是否已经全部学完（即培训班中的课程章节考试全部完成）+智能辅导分是否达标
					if(myclasses.get(i).getExamRoom()!=null && myclasses.get(i).getExamRoom().getId()>0){//培训班存在考场
						canExam = classDao.checkClassCanExam(elUser.getId(),myclasses.get(i).getElClass().getId(),myclasses.get(i).getExamRoom().getId());
						if(myclasses.get(i).getSortid() > 1){//判断上一个培训班考试是否通过
							myclasses.get(i).setCanLearn(classDao.checkClassCanLearn(myclasses.get(i).getElClass().getId(),myclasses.get(i).getSortid()-1,elUser.getId(),peixunBatch.getId()));
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}else{//培训班考场不存在
						if(myclasses.get(i).getSortid() > 1){
							myclasses.get(i).setCanLearn(myclasses.get(i-1).getProcessForElc()==100?true:false);//培训班考场不存在情况下，只需要判断培训班进度是否为100
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}
					finish = classDao.checkClassExamIsPass(myclasses.get(i).getElClass().getId(), elUser.getId());
					myclasses.get(i).setFinish(finish);
					myclasses.get(i).setCanExam(canExam);
				}
			}
		}
		//董克2015-8-22修改  wjm_user_center_index
		return "wjm_user_center_index";
	}
	
	/**
	 * 进入培训班列表页面
	 * @return
	 * @throws ElException
	 */
	public String mystudy_class_view_wjm() throws ElException{
		//用户当前学习等级的智能辅导分
		intelligentPoints = IntelligentTutoringPointsUtil.intelligentTutoringPoints(getSessionIntValue(ElConstants.SESSION_USERID));
		
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		//获取培训批次
		//当前系统培训批次默认id为1
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		//修改判断是否定级条件
		//只需要peixunBatch分配给用户即可
		if(peixunBatchDao.checkPeixunBatchIsAssignToUser(peixunBatch.getId(), elUser.getId())){
			inDingjiRoom = true;
		}
		if(inDingjiRoom ){
			//设置正在学习的培训班信息
			peixunBatch.setNowClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),0));
			
			//设置完成的培训班信息
			peixunBatch.setDoneClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),1));
			//更新培训批次中被分配的培训班的进度、更新培训批次进度  已交给章节学习、考试来更新
//			//更新培训批次中被分配的培训班的进度
//			classDao.updateClassProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
//			//更新培训批次进度
//			peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			//获取被分配的培训班(附带学习进度)
			myclasses = peixunBatchDao.getMyBatchDetail(elUser.getId());//我的培训班列表
			
			myclasses = myclasses == null?new ArrayList<MyClass>():myclasses;
			int canExam = 0;
			boolean finish = false;
			if(myclasses.size()>0){
				for(int i=0;i<myclasses.size();i++){
					//判断培训班是否有考场、培训班考场是否通过
					//根据myclasses.get(i).getExamRoom()是否为空来判断培训班是否有考场
					//设置培训班是否能考试==判断本等级是否已经全部学完（即培训班中的课程章节考试全部完成）+智能辅导分是否达标
					if(myclasses.get(i).getExamRoom()!=null && myclasses.get(i).getExamRoom().getId()>0){//培训班存在考场
						List<ExamPaper> examPapers = examPaperDao.listEroomExamPaper(myclasses.get(i).getExamRoom().getId());
						for (int j = 0; j < examPapers.size(); j++) {
							if (examPapers.get(j).getStatus() != 1) {
								if (!studyQuizDao.checkStudyExamPaper(elUser.getId(), examPapers.get(j)
										.getId(), myclasses.get(i).getExamRoom().getId(), myclasses.get(i).getElClass().getId())) {
									// 添加该学员到 学员试卷表中
									studyQuizDao.addStudyExamPaper(elUser.getId(), examPapers.get(j)
											.getId(), myclasses.get(i).getExamRoom().getId(), myclasses.get(i).getElClass().getId());
								}
							}
						}
						//设置class的试卷
						myclasses.get(i).getExamRoom().setExamPaper(new ExamPaper(studyQuizDao.getExamRoomid(myclasses.get(i).getExamRoom().getId(),elUser.getId())));
						canExam = classDao.checkClassCanExam(elUser.getId(),myclasses.get(i).getElClass().getId(),myclasses.get(i).getExamRoom().getId());
						if(myclasses.get(i).getSortid() > 1){//判断上一个培训班考试是否通过
							myclasses.get(i).setCanLearn(classDao.checkClassCanLearn(myclasses.get(i).getElClass().getId(),myclasses.get(i).getSortid()-1,elUser.getId(),peixunBatch.getId()));
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}else{//培训班考场不存在
						if(myclasses.get(i).getSortid() > 1){
							myclasses.get(i).setCanLearn(classDao.checkClassCanLearn(myclasses.get(i).getElClass().getId(),myclasses.get(i).getSortid()-1,elUser.getId(),peixunBatch.getId()));
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}
					finish = classDao.checkClassExamIsPass(myclasses.get(i).getElClass().getId(), elUser.getId());
					myclasses.get(i).setFinish(finish);
					myclasses.get(i).setCanExam(canExam);
				}
			}
		}
		return "mystudy_class_view_wjm";
	}
	/**
	 * 进入培训班学习页面(即课程列表页面)
	 * @return
	 * @throws ElException
	 */
	public String mystudy_course_view_wjm() throws ElException{
		intelligentPoints = IntelligentTutoringPointsUtil.intelligentTutoringPoints(getSessionIntValue(ElConstants.SESSION_USERID));
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		//设置完成和正在学习的培训班信息
		peixunBatch.setNowClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),0));
		peixunBatch.setDoneClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),1));
		//获取我的课程列表
		int classid = elClass.getId();
		elClass = classDao.getClassById(classid);
		if(classid>0){
			//判断等级是否在定级前
			ELUserClassification elUserClassification = classificationDao.getElUserClassificationByUserid(getSessionIntValue(ElConstants.SESSION_USERID), roomid = classificationDao.getRoomid());
			if(elClass!=null && elClass.getName().compareTo(elUserClassification.getName())<0){
				initCompliance = true;
			}
		}
		if(classid<=0){
			this.setElmessage("参数错误");
			return "error";
		}
		
		myCourses = courseDao.listMyCoursees_wjm(classid,getSessionIntValue(ElConstants.SESSION_USERID));
		boolean temp = false;
		if(myCourses!=null){
			if(elClass!=null && elClass.getLearnByOrder() == 1){//培训班中课程必须按照顺序来学习
				for(MyCourse mycourse:myCourses){
					//课程结业考场
					examRoom = eroomDao.getExamRoom(mycourse.getCourse().getId(), elClass.getId());
					List<ExamPaper> examPapers = examPaperDao.listEroomExamPaper(examRoom.getId());
					for (int i = 0; i < examPapers.size(); i++) {
						if (examPapers.get(i).getStatus() != 1) {
							if (!studyQuizDao.checkStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), examPapers.get(i)
									.getId(), examRoom.getId(), elClass.getId())) {
								// 添加该学员到 学员试卷表中
								studyQuizDao.addStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), examPapers.get(i)
										.getId(), examRoom.getId(), elClass.getId());
							}
						}
					}
					//设置课程结业考场的试卷信息
					epid = studyQuizDao.getExamRoomid(examRoom.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
					if(epid != 0){
						//设置试卷信息
						mycourse.getExamRoom().setExamPaper(new ExamPaper(epid));
					}
					//设置MyCourse的canLearn属性，只要是用来前台是否显示图片为黑色
					//判断是否定级初始化学完
					temp = courseDao.getCourseInitCompliance(mycourse.getCourse().getId(),elClass.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
					if(!temp){
						//需要判断课程是否已经学完、课程对应的结业考场是否已经通过
						temp = classDao.checkcoursecanlearn(mycourse.getCourse().getId(),elClass.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
					}
					mycourse.setCanLearn(temp == true ? 1 : 0);
					//如果课程学习完成了，设置课程考试结束时间和考试成绩
					//还未测试
					if(mycourse.isPassed()){
						mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),getSessionIntValue(ElConstants.SESSION_USERID),mycourse);
					}
				}
			}
		}
		return "mystudy_course_view_wjm";
	}
	
	/**
	 * 进入章节列表（包含章节列表和一个单元测试）
	 * @return
	 * @throws ElException
	 */
	public String mystudy_page_view_wjm() throws ElException{
		if(course == null || course.getId()<=0 || elClass == null || elClass.getId()<=0 || peixunBatch==null || peixunBatch.getId()<0){
			this.setElmessage("参数错误");
			return "error";
		}
		intelligentPoints = IntelligentTutoringPointsUtil.intelligentTutoringPoints(getSessionIntValue(ElConstants.SESSION_USERID));
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		//设置完成和正在学习的培训班信息
		peixunBatch.setNowClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),0));
		peixunBatch.setDoneClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),1));
		
		examRoom = eroomDao.getExamRoom(course.getId(), elClass.getId());//课程结业考场
		
		course = courseDao.getCourseById(course.getId());	//课程
		elClass = classDao.getClassById(elClass.getId());	//等级
		//判断等级是否在定级前
		ELUserClassification elUserClassification = classificationDao.getElUserClassificationByUserid(getSessionIntValue(ElConstants.SESSION_USERID), roomid = classificationDao.getRoomid());
		if(elClass!=null && elClass.getName().compareTo(elUserClassification.getName())<0){
			initCompliance = true;
		}
		if(mycourse == null)	mycourse = new MyCourse();//我的学习课程
		if(course!=null){
			mycourse.setCourse(course);
			List<ExamPaper> examPapers = examPaperDao.listEroomExamPaper(examRoom
					.getId());
			for (int i = 0; i < examPapers.size(); i++) {
				if (examPapers.get(i).getStatus() != 1) {
					if (!studyQuizDao.checkStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), examPapers.get(i)
							.getId(), examRoom.getId(), elClass.getId())) {
						// 添加该学员到 学员试卷表中
						studyQuizDao.addStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), examPapers.get(i)
								.getId(), examRoom.getId(), elClass.getId());
					}
				}
			}
			
			//设置课程结业考场的试卷信息
			epid = studyQuizDao.getExamRoomid(examRoom.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			if(epid != 0){
				//设置试卷信息
				mycourse.setMyExamPaper(studyQuizDao.getMyExampaper(getSessionIntValue(ElConstants.SESSION_USERID), examRoom.getId(), epid));
			}
			//设置完成时间和分数
			mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),getSessionIntValue(ElConstants.SESSION_USERID),mycourse);
			//设置课程是否通过
			mycourse.setPassed(courseDao.checkCourseIsPass(elClass.getId(),getSessionIntValue(ElConstants.SESSION_USERID), course.getId()));
		}
		//获取章节列表
		myCPages = studyCourseDao.listCpsbyCUid_wjm(course.getId(),//课程的章节（不关联考场）
				getSessionIntValue(ElConstants.SESSION_USERID),elClass.getId());
		
		//判断章节是否都通过，设置myroom是否可以考试  
		if(examRoom != null){
			if(myroom == null)	myroom = new MyRoom();//我的考试
			myroom.setExamroom(examRoom);
			//废弃
			//课程的结业考场是否能考试已交给判断课程是否通过
			myroom.setCanExam(courseDao.checkCpagesIsAllPass(elClass.getId(),getSessionIntValue(ElConstants.SESSION_USERID),course.getId()));
		}
		myCPages = myCPages == null ? new ArrayList<MyCPage>():myCPages;
		//判断每个章节是否可以学习，根据章节顺序sortid
		//并设置是否可学属性
		MyExamPaper myExamPaper = null;
		CoursePage coursePage = null; 
		List<ExamPaper> pageExamPapers = null;
		for(MyCPage mycp:myCPages){
			coursePage = mycp.getCpage();
			//章节获取考场试卷信息（多个考场）
			mycp = eroomDao.getBindingExamRooms(mycp,getSessionIntValue(ElConstants.SESSION_USERID));
			
			//章节绑定的每个考场获取答卷信息==>按照最高分、时间降序后取第一条
			if(mycp.getExamRooms()!=null ){
				coursePage = mycp.getCpage();
				//章节试卷分配判断
				pageExamPapers =  examPaperDao.listEroomExamPaper(mycp.getExamRoom().getId());
				for (int i = 0; i < pageExamPapers.size(); i++) {
					if (pageExamPapers.get(i).getStatus() != 1) {
						if (!studyQuizDao.checkStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), pageExamPapers.get(i)
								.getId(), mycp.getExamRoom().getId(), elClass.getId())) {
							// 添加该学员到 学员试卷表中
							studyQuizDao.addStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), pageExamPapers.get(i)
									.getId(), mycp.getExamRoom().getId(), elClass.getId());
						}
					}
				}
				//章节获取考场试卷信息（多个考场）
				mycp = eroomDao.getBindingExamRooms(mycp,elUser.getId());
				
				//章节绑定的每个考场获取答卷信息==>按照最高分、时间降序后取第一条
				if(mycp.getExamRooms()!=null ){
					boolean canExam = false;
					for(int i=0;i<mycp.getExamRooms().size();i++){
						myExamPaper = studyQuizDao.getMyExampaper(getSessionIntValue(ElConstants.SESSION_USERID), mycp.getExamRooms().get(i).getId(), mycp.getExamRooms().get(i).getExamPaper().getId());
						mycp.getExamRooms().get(i).setMyExamPaper(myExamPaper);
						//多个章节考场，判断可考或者不可考
						if(mycp.getExamRooms().get(i).getSortid()>1){
							canExam = eroomDao.setExamRoomCanExam(mycp.getExamRooms().get(i),course.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
						}else{
							if(mycp.isPassed()){
								canExam = true;
							}
						}
						mycp.getExamRooms().get(i).setCanExam(canExam==true?1:0);
					}
				}
			}
			
			//判断章节
			//根据上面的章节信息，判断该章节的前一章节"是否可跳过"，如果是，则直接打开本章节
			//如果是否  1：有前一章；判断前一章完成状态，完成了才能打开本章节，未完成：提示"上一章节未完成学习，还不能进入本章节的学习"
			//		    2：直接打开本章节
			CoursePage beginCPage = null;
			if(coursePage.getSkipable() == 0){//必须判断
				if(coursePage.getSortid()>1){
					beginCPage = coursePageDao.getBeginCPage(course.getId(),mycp.getCpage().getSortid()-1);//有前一章信息
					if(coursePageDao.checkPageCanlearn(mycp.getCpage().getSortid()-1,course.getId(),getSessionIntValue(ElConstants.SESSION_USERID),beginCPage.getId())){
						mycp.setCanLearn(1);
					}
				}else{
					mycp.setCanLearn(1);
				}
			}else{
				mycp.setCanLearn(1);
			}
			//如果章节学习完成了，设置章节考试结束时间和考试成绩
			if(mycp.isPassed()){
				mycp = statisticQuizDao.getFinishtimeByScorePage(course.getId(),getSessionIntValue(ElConstants.SESSION_USERID),mycp);
			}
			
		}
		
		
		//20141008判断是否考试次数易购
		for(int i=0;i<myCPages.size();i++){
			for(int j=0;j<myCPages.get(i).getExamRooms().size();j++){
				int myexampaperid = studyQuizDao.getMypaperIdByRidanUid(
						getSessionIntValue(ElConstants.SESSION_USERID), myCPages.get(i).getExamRooms().get(j).getId(), myCPages.get(i).getExamRooms().get(j).getExamPaper().getId());
				System.out.println(myexampaperid);
				myCPages.get(i).setIsExceedNumberExam(myexampaperid);
			}
			
		}
		return "mystudy_page_view_wjm";
	}
	
	private String classname;
	
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	/**
	 * 外经贸个人中心查看智能辅导分
	 * @return
	 * @throws ElException
	 */
	public String showIntelligent() throws ElException{
		intelligentPoints = IntelligentTutoringPointsUtil.intelligentTutoringPoints(getSessionIntValue(ElConstants.SESSION_USERID));
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		//设置完成和正在学习的培训班信息
		peixunBatch.setNowClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),0));
		peixunBatch.setDoneClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),1));
		menus = AuthorityNewVersionUtil.getListElFuncByRoleid(String.valueOf(getSessionIntValue(ElConstants.SESSION_ROLE)));
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
		
			
		if(elClass!=null && elClass.getId()>0){
			//将ab等级和在一起查看20140703
			 classname=intelligentTutoringPointsDao.getElClssName(elClass.getId());
				if(classname!=null&&!"".equals(classname)){
					classname= classname.substring(0,1);
					 logger.info(classname);
				}
				logger.info(classname);
			List<String> elClassIds = intelligentTutoringPointsDao.getElClssList(classname);
			IntelligentTutoringPoints in1=intelligentTutoringPointsDao.getDifferentPoints(getSessionIntValue(ElConstants.SESSION_USERID),Integer.parseInt(elClassIds.get(0)));
			IntelligentTutoringPoints in2=intelligentTutoringPointsDao.getDifferentPoints(getSessionIntValue(ElConstants.SESSION_USERID),Integer.parseInt(elClassIds.get(1)));
//			intelligentTutoringPoints = intelligentTutoringPointsDao.getDifferentPoints_new(getSessionIntValue(ElConstants.SESSION_USERID),elClass.getId(),classname);
			//保留两位小数
			DecimalFormat df = new DecimalFormat(".00");
			intelligentTutoringPoints = intelligentTutoringPointsDao.getDifferentPoints(getSessionIntValue(ElConstants.SESSION_USERID),elClass.getId());
			intelligentTutoringPoints.setScoreLogin(Float.parseFloat(df.format((in1.getScoreLogin()+in2.getScoreLogin())/2)));
			intelligentTutoringPoints.setScoreWeek(Float.parseFloat(df.format((in1.getScoreWeek()+in2.getScoreWeek())/2)));
			intelligentTutoringPoints.setScoreClass(Float.parseFloat(df.format((in1.getScoreClass()+in2.getScoreClass())/2)));
			intelligentTutoringPoints.setScoreProportion(Float.parseFloat(df.format((in1.getScoreProportion()+in2.getScoreProportion())/2)));
			intelligentTutoringPoints.setScoreRecoding(Float.parseFloat(df.format((in1.getScoreRecoding()+in2.getScoreRecoding())/2)));
			intelligentTutoringPoints.setScoreAcademic(Float.parseFloat(df.format((in1.getScoreAcademic()+in2.getScoreAcademic())/2)));
			intelligentTutoringPoints.setScoreAcademicCourse(Float.parseFloat(df.format((in1.getScoreAcademicCourse()+in2.getScoreAcademicCourse())/2)));
			intelligentTutoringPoints.setScoreProportionQ(Float.parseFloat(df.format((in1.getScoreProportionQ()+in2.getScoreProportionQ())/2)));
			intelligentTutoringPoints.setScoreProportionT(Float.parseFloat(df.format((in1.getScoreProportionT()+in2.getScoreProportionT())/2)));
			intelligentTutoringPoints.setScoreRecodingQ(Float.parseFloat(df.format((in1.getScoreRecodingQ()+in2.getScoreRecodingQ())/2)));
			intelligentTutoringPoints.setScoreRecodingT(Float.parseFloat(df.format((in1.getScoreRecodingT()+in2.getScoreRecodingT())/2)));
//			intelligentTutoringPoints.setTotalScore(Float.parseFloat(df.format((in1.getTotalScore()+in2.getTotalScore())/2)));
			intelligentTutoringPoints.setTotalScore(Float.parseFloat(df.format(intelligentTutoringPoints.getScoreLogin()+intelligentTutoringPoints.getScoreWeek()+intelligentTutoringPoints.getScoreClass()+
					intelligentTutoringPoints.getScoreAcademic()+intelligentTutoringPoints.getScoreAcademicCourse()+intelligentTutoringPoints.getScoreProportionQ()+
					intelligentTutoringPoints.getScoreProportionT()+intelligentTutoringPoints.getScoreRecodingQ()+intelligentTutoringPoints.getScoreRecodingT())));
		
			
		//scoreLogin scoreWeek  scoreClass   scoreProportionQ   scoreProportionT   scoreRecodingQ  scoreRecodingT  scoreAcademic
		//scoreAcademicCourse
		}else{
			if(peixunBatch!=null && peixunBatch.getNowClass()!=null&&peixunBatch.getNowClass().getId()>0){
				intelligentTutoringPoints = intelligentTutoringPointsDao.getDifferentPoints(getSessionIntValue(ElConstants.SESSION_USERID),peixunBatch.getNowClass().getId());
			}
		}
		return "showIntelligent";
	}
	
	/**培训批次的分配
	 * @return
	 * @throws ElException
	 * @throws IOException 
	 */
	public String assign_batch() throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_USERID) == 0){
			this.setElmessage("还未登录");
			return "error";
		}
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		//wjm0211修改
		//用户定级批次分配
		//获取培训批次
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		
		if(peixunBatch!=null && peixunBatch.getId()>0){
			//加入到培训批次和培训批次中所有的培训班
			peixunBatchDao.addBatchEluser(Integer.valueOf(peixunBatch.getId()),getSessionIntValue(ElConstants.SESSION_USERID));
			//培训批次中每个培训班中的每门课程分配给用户
			peixunBatchDao.addBatchClass_course(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID),ClassConstants.CLASS_SQFS_FP);
		}
//		//判断分数段在哪个阶段，若分数为36，分数处于2B，则将2A及2A以前的培训班进度改为100%,培训班的所有课程进度改为100%
		float myscore = myExamPaper.getMyScore();
//		//根据保存定级信息表查出所有培训班，然后比较定级
		//classification.name == '2A'
		List<ElClass> elclasses = peixunBatchDao.getElclassList(peixunBatch.getId());
		float process = 100.00f;
		boolean flag = false;//标识是否更新了培训班进度
		for (ElClass el : elclasses) {
			// 0211修改wjm定级考试
			if (el.getName().compareTo(classification.getName()) < 0) {
				// 暂时定为6A
				if (el.getName().compareTo("4A") < 0) {
					if (el.getName().compareTo("6A") < 0) {
						// 更新培训班进度为100
						if (classDao.checkElclassIsUsers(
								getSessionIntValue(ElConstants.SESSION_USERID),
								el.getId())) {
							// 则将2A及2A以前的培训班进度改为100%
							classDao
									.updateClassProcessByClassid(
											el.getId(),
											process,
											getSessionIntValue(ElConstants.SESSION_USERID));
							// 培训班的所有课程进度改为100%
							courseDao
									.updateCourseProcessByClassid(
											el.getId(),
											getSessionIntValue(ElConstants.SESSION_USERID));
							flag = true;
						}
					}
				}
			}
		}
		if(flag){
			peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
		}
		
		PrintWriter localPrintWriter;
		try {
			localPrintWriter =resp.getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 获取用户定级信息表
	 * @return
	 * @throws ElException
	 * @throws IOException
	 */
	public String getElUserClassificationByUserid() throws ElException,IOException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		
		elUserClassification = classificationDao.getElUserClassificationByUserid(getSessionIntValue(ElConstants.SESSION_USERID),roomid);
		elUserClassification=elUserClassification==null?new ELUserClassification(0):elUserClassification;
		out.print(elUserClassification.getTime());
		
		out.flush();
		out.close();
		return null;
	}
	/**
	 * 保存用户定级信息
	 * @return
	 * @throws ElException
	 * @throws IOException
	 */
	public String addOrUpdateElUserClassificationByUserid() throws ElException,IOException{
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		classificationDao.updateElUserClassificationByUserid(getSessionIntValue(ElConstants.SESSION_USERID),roomid,classification.getName(),time);
		
		out.flush();
		out.close();
		return null;
	}
	
//	//学员申请重新定级
//	public String userApplication() throws ElException{
//		HttpServletResponse resp=ServletActionContext.getResponse();
//		resp.setContentType("text/plain;charset=UTF-8");
//		
//		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
//		//定级考场id
//		roomid = classificationDao.getRoomid();
//		
//		//申请重新定级
//		classificationDao.updateEluserClassification(userid,roomid,ElConstants.SYSTEM_CLASSIFICATION_NO);
//		
//		PrintWriter localPrintWriter;
//		try {
//			localPrintWriter =resp.getWriter();
//			localPrintWriter.flush();
//			localPrintWriter.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
	
	/**
	 * 学生学习查询
	 */
	public String studentInquiry() throws ElException{
		PeixunBatch peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		ElClass elclass = null;
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=user.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			elUsers = classificationDao.getStudents(searchDep,searchUser,-1,-1,null);
			if(elUsers!=null){
				for(ELUser user:elUsers){
					//获取当前正在学习的等级
					elclass = peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),user.getId(),0);
					if(elclass!=null && elclass.getId()>0){
						user.setNowClass(elclass);
						user.setMyNowClass(elclass.getMyClass());
					}
				}
			}
			try {
				String titles[] = { "部门", "姓名",
						"性别", "当前级别", "学习进度" };
				String attrs[] = { "department.name", "realname", "sex", "nowClass.name",
						"myNowClass.process" };
				new ExcelOutPut().writeExcel("学生学习查询", getResponse()
						.getOutputStream(), titles, ELUser.class.getName(),
						elUsers, attrs);
			} catch (Exception e) {
				logger.error("导出学生学习查询Excel错误", e);
			}
			return null;
		}
		if (searchDep != null && searchDep.getId() > 0) {
			searchDep = departmentDao.getDepById(searchDep.getId());
		}
		if(classification!=null && classification.getName()!=null&&!classification.getName().equals("")){
			elclass = classDao.getElClassByName(classification.getName());
			returnIds = classDao.getUsersByUserids(elclass.getId());
//			String returnIds1 = "";
//			String[] userids = null;
//			if(returnIds!=null && !returnIds.equals("")){
//				userids = returnIds.split(",");
//				for(int i=0;i<userids.length;i++){
//					elclass = peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),Integer.parseInt(userids[i]),0);
//					if(elclass!=null && elclass.getId()>0){
//						returnIds1 += userids[i] + ",";
//					}
//				}
//				returnIds = returnIds1;
//			}
		}
//		if(returnIds!=null && !returnIds.equals("")){
//			returnIds = returnIds.substring(0,returnIds.lastIndexOf(","));
//		}
		String startDate = this.getRequest().getParameter("start_date");
		String endDate = this.getRequest().getParameter("end_date");
		
		Map<String,Object> query = new HashMap<String,Object>();
		if(startDate != null && endDate!=null && !startDate.equals("") && !endDate.equals("")){
			query.put("start_date", startDate);
			query.put("end_date", endDate);
			
			this.getRequest().setAttribute("start_date", startDate);
			this.getRequest().setAttribute("end_date", endDate);
		}
		
		elUsers = classificationDao.getStudents(searchDep,searchUser,query,getPageNow(),getPageSize(),returnIds);
		count = classificationDao.getStudentsCount(searchDep,searchUser,query,getPageNow(),getPageSize(),returnIds);
		elclass = null;
		if(elUsers!=null){
			for(ELUser user:elUsers){
				//获取当前正在学习的等级
				elclass = peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),user.getId(),0);
				if(elclass!=null && elclass.getId()>0){
					user.setNowClass(elclass);
				}
			}
		}
		return "studentInquiry";
	}
	public String studentClassInfoPersonal() throws ElException{
		if(elUser==null || elUser.getId()<=0){
			elUser = new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
		}
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=myclass.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			myclasses = peixunBatchDao.getMyBatchDetail(elUser.getId());//我的培训班列表
			try {
				String titles[] = { "等级名称", "学习进度" };
				String attrs[] = { "elClass.name", "processForElc" };
				new ExcelOutPut().writeExcel("等级学习查询", getResponse()
						.getOutputStream(), titles, MyClass.class.getName(),
						myclasses, attrs);
			} catch (Exception e) {
				logger.error("导出学生学习查询Excel错误", e);
			}
			return null;
		}
		if(roomid==0){
			roomid = classificationDao.getRoomid();
		}
		MyRoom myroom = eroomDao.getMyRoom(roomid,elUser.getId());
		//定级考场中存在该用户&&考场分配表中用户已经考过(不是指考试通过)status==2
		if(myroom != null){
			if(eroomDao.checkuser2eroom(myroom.getId(), elUser.getId()) && myroom.getStatus() == 2 ){
				inDingjiRoom = true;
			}
		}
		if(!inDingjiRoom){
			this.setElmessage("该用户还未定级,无法查看");
			return "error";
		}
		myclasses = peixunBatchDao.getMyBatchDetail(elUser.getId());//我的培训班列表
		return "studentClassInfoPersonal";
	}
	/**
	 * 等级学习查询
	 * @return
	 * @throws ElException
	 */
	public String studentClassInfo() throws ElException{
		elclasses = peixunBatchDao.listElClasses();//系统培训班学习情况
		elclasses = elclasses == null ?new ArrayList<ElClass>():elclasses;
		int count = 0;
		double process = 0.0;
		for(int i=0;i<elclasses.size();i++){
			//获取各项人数
			elclasses.get(i).setFinishCount(classDao.getFinishCountInformation(elclasses.get(i).getId(),1));
			elclasses.get(i).setZeroCount(classDao.getFinishCountInformation(elclasses.get(i).getId(),2));
			elclasses.get(i).setOtherCount(classDao.getFinishCountInformation(elclasses.get(i).getId(),3));
			count = elclasses.get(i).getFinishCount() + elclasses.get(i).getZeroCount() + elclasses.get(i).getOtherCount();
			elclasses.get(i).setCount(count);
			process = (double)elclasses.get(i).getFinishCount()/(double)elclasses.get(i).getCount();
			elclasses.get(i).setProcess((double) (Math.round(process*100)/100.0));
		}
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=elclasses.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			try {
				String titles[] = { "等级名称", "学习进度","0%","100%","其他" };
				String attrs[] = { "name", "process","zeroCount","FinishCount","otherCount" };
				new ExcelOutPut().writeExcel("系统等级学习查询", getResponse()
						.getOutputStream(), titles, ElClass.class.getName(),
						elclasses, attrs);
			} catch (Exception e) {
				logger.error("导出学习查询Excel错误", e);
			}
			return null;
		}
		return "studentClassInfo";
	}
	public String studentInfoByClassid() throws ElException{
		elUsers = classDao.getStudentInfoByClassid(elClass.getId(),getPageNow(),getPageSize());
		count = classDao.getStudentInfoSizeByClassid(elClass.getId());
		elUsers = elUsers == null ? new ArrayList<ELUser>():elUsers;
//		for(int i=0;i<elUsers.size();i++){
//			//设置完成时间和分数
//			mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUsers.get(i).getId(),mycourse);
//			
//		}
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=elusers.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			try {
				elUsers = classDao.getStudentInfoByClassid(elClass.getId(),-1,-1);
				String titles[] = { "用户名", "姓名","部门","学习进度" };
				String attrs[] = { "username", "realname","department.name","myClass.process" };
				new ExcelOutPut().writeExcel("等级用户查询", getResponse()
						.getOutputStream(), titles, ELUser.class.getName(),
						elUsers, attrs);
			} catch (Exception e) {
				logger.error("导出等级用户查询Excel错误", e);
			}
			return null;
		}
		return "studentInfoByClassid";
	}
	/**
	 * 单元学习查询
	 * @return
	 * @throws ElException
	 */
	public String studentCourseInfo() throws ElException{
		if(elUser==null || elUser.getId()<=0){
			elUser = new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
		}
		if(elClass==null || elClass.getId()<=0){
			PeixunBatch peixunBatch = peixunBatchDao.getPeixunBatchById(1);
			elClass = peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),0);
		}
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=mycourse.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			myCourses = courseDao.listMyCoursees_wjm(elClass.getId(),elUser.getId());
			for(MyCourse mycourse:myCourses){
				//如果课程学习完成了，设置课程考试结束时间和考试成绩
				if(mycourse.isPassed()){
					mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUser.getId(),mycourse);
				}
			}
			try {
				String titles[] = { "单元", "学习进度","完成时间","测验成绩" };
				String attrs[] = { "course.name", "process","endtime","myRoom.myScore" };
				new ExcelOutPut().writeExcel("单元学习查询", getResponse()
						.getOutputStream(), titles, MyCourse.class.getName(),
						myCourses, attrs);
			} catch (Exception e) {
				logger.error("导出单元学习查询Excel错误", e);
			}
			return null;
		}
		elClass = classDao.getClassById(elClass.getId());
		myCourses = courseDao.listMyCoursees_wjm(elClass.getId(),elUser.getId());
		for(MyCourse mycourse:myCourses){
			//如果课程学习完成了，设置课程考试结束时间和考试成绩
			if(mycourse.isPassed()){
				mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUser.getId(),mycourse);
			}
		}
		return "studentCourseInfo";
	}
	/**
	 * 模块学习查询
	 * @return
	 * @throws ElException
	 */
	public String studentCpageInfo() throws ElException{
		if(elUser==null || elUser.getId()<=0){
			elUser = new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
		}
		if(elClass==null || elClass.getId()<=0){
			PeixunBatch peixunBatch = peixunBatchDao.getPeixunBatchById(1);
			elClass = peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),0);
		}
		if(course==null || course.getId()<=0){
			this.setElmessage("课程ID不能为空");
			return "error";
		}
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=mycpage.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			List<ExamRoom> rooms = new ArrayList<ExamRoom>();
			myCPages = studyCourseDao.listCpsbyCUid_wjm(course.getId(),//课程的章节（不关联考场）
					elUser.getId(),elClass.getId());
			MyExamPaper myExamPaper = null;
			CoursePage cpage = null;
			for(MyCPage mycp:myCPages){
				mycp = eroomDao.getBindingExamRooms(mycp,elUser.getId());
				if(cpage == null || cpage.getId()!=mycp.getCpage().getId()){
					cpage = mycp.getCpage();
				}
				if(mycp.getExamRooms()!=null ){
					for(int i=0;i<mycp.getExamRooms().size();i++){
						myExamPaper = studyQuizDao.getMyExampaper(
								elUser.getId(), mycp.getExamRooms().get(i).getId(), mycp.getExamRooms().get(i).getExamPaper().getId());
						mycp.getExamRooms().get(i).setMyExamPaper(myExamPaper);
						mycp.getExamRooms().get(i).setCpage(cpage);
					}
				}
				if(mycp.isPassed()){
					mycp = statisticQuizDao.getFinishtimeByScorePage(course.getId(),elUser.getId(),mycp);
				}
				
				rooms.addAll(mycp.getExamRooms());
			}
			try {
				String titles[] = { "章节", "考场","完成时间","成绩"};
				String attrs[] = { "cpage.title", "title","myExamPaper.endtime","myExamPaper.myScore" };
				new ExcelOutPut().writeExcel("模块学习查询", getResponse()
						.getOutputStream(), titles, ExamRoom.class.getName(),
						rooms, attrs);
			} catch (Exception e) {
				logger.error("导出模块学习查询Excel错误", e);
			}
			return null;
		}
		//获取章节列表
		myCPages = studyCourseDao.listCpsbyCUid_wjm(course.getId(),//课程的章节（不关联考场）
				elUser.getId(),elClass.getId());
		MyExamPaper myExamPaper = null;
		for(MyCPage mycp:myCPages){
			//章节获取考场试卷信息（多个考场）
			mycp = eroomDao.getBindingExamRooms(mycp,elUser.getId());
			
			//章节绑定的每个考场获取答卷信息==>按照最高分、时间降序后取第一条
			if(mycp.getExamRooms()!=null ){
				for(int i=0;i<mycp.getExamRooms().size();i++){
					myExamPaper = studyQuizDao.getMyExampaper(
							elUser.getId(), mycp.getExamRooms().get(i).getId(), mycp.getExamRooms().get(i).getExamPaper().getId());
					mycp.getExamRooms().get(i).setMyExamPaper(myExamPaper);
				}
			}
			//如果章节学习完成了，设置章节考试结束时间和考试成绩
			if(mycp.isPassed()){
				mycp = statisticQuizDao.getFinishtimeByScorePage(course.getId(),elUser.getId(),mycp);
			}
		}
		return "studentCpageInfo";
	}
	
	/**
	 * 章节考试记录查询
	 * @return
	 * @throws ElException
	 */
	public String pageexamInfoIquiry() throws ElException{
		if (searchDep != null && searchDep.getId() > 0) {
			searchDep = departmentDao.getDepById(searchDep.getId());
		}
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=user.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			elUsers = classificationDao.getPageexamInfo(searchDep,searchUser,-1,-1,WjmElconstants.MEAN_PAGE);
			if(elUsers!=null){
				for(ELUser user:elUsers){
					if(user.getLog()!=null && user.getLog().getPageid()>0){
						user.setCoursePage(coursePageDao.getCp(user.getLog().getPageid()));
					}
				}
			}
			try {
				String titles[] = { "部门", "姓名","单元","模块（练习）","开始时间","结束时间","时长","得分" };
				String attrs[] = { "department.name", "realname","course.name","coursePage.title","log.begintime","log.endtime","log.passtime","log.score" };
				new ExcelOutPut().writeExcel("章节考试记录", getResponse()
						.getOutputStream(), titles, ELUser.class.getName(),
						elUsers, attrs);
			} catch (Exception e) {
				logger.error("导出章节考试记录Excel错误", e);
			}
			return null;
		}
		elUsers = classificationDao.getPageexamInfo(searchDep,searchUser,getPageNow(),getPageSize(),WjmElconstants.MEAN_PAGE);
		count = classificationDao.getPageexamInfoCount(searchDep,searchUser,getPageNow(),getPageSize(),WjmElconstants.MEAN_PAGE);
		if(elUsers!=null){
			for(ELUser user:elUsers){
				if(user.getLog()!=null && user.getLog().getPageid()>0){
					user.setCoursePage(coursePageDao.getCp(user.getLog().getPageid()));
				}
			}
		}
		return "pageexamInfoIquiry";
	}
	/**
	 * 课程考试记录
	 * @return
	 * @throws ElException
	 */
	public String courseexamInfoIquiry() throws ElException{
		if (searchDep != null && searchDep.getId() > 0) {
			searchDep = departmentDao.getDepById(searchDep.getId());
		}
		if (exprot == true) {// 导出
			getResponse().reset();
			getResponse().setHeader("Content-disposition",
					"attachment; filename=user.xls");
			getResponse().setContentType("application/vnd.ms-excel");
			elUsers = classificationDao.getPageexamInfo(searchDep,searchUser,-1,-1,WjmElconstants.MEAN_COURSE);
			try {
				String titles[] = { "部门", "姓名","单元","开始时间","结束时间","时长","得分" };
				String attrs[] = { "department.name", "realname","course.name","log.begintime","log.endtime","log.passtime","log.score" };
				new ExcelOutPut().writeExcel("章节考试记录", getResponse()
						.getOutputStream(), titles, ELUser.class.getName(),
						elUsers, attrs);
			} catch (Exception e) {
				logger.error("导出课程考试记录Excel错误", e);
			}
			return null;
		}
		elUsers = classificationDao.getPageexamInfo(searchDep,searchUser,getPageNow(),getPageSize(),WjmElconstants.MEAN_COURSE);
		count = classificationDao.getPageexamInfoCount(searchDep,searchUser,getPageNow(),getPageSize(),WjmElconstants.MEAN_COURSE);
		return "courseexamInfoIquiry";
	}
	
	
	
	//////////////////////////以下为考场批次
	//外经贸模拟考试
	public String simulationExam() throws ElException{
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		if(userid<=0){
			this.setElmessage("未登录,请先登录");
			return "error";
		}
		//考场批次、考场、试卷分配给用户
		classificationDao.checkUserIsAssignToErbatch(userid);
		//获取考场批次
		erbatchs = eroomDao.listErbatch(getPageNow(), getPageSize());
		if(erbatchs!=null && erbatchs.size()>0){
			for (EroomBatch erbatch:erbatchs){
				erbatch.setProcess(classificationDao.getErbatchProcess(erbatch.getId(),userid));
			}
		}
		return "simulationExam";
	}
	//根据考场批次ID获取所有考场
	public String listEroomsByErbatchid() throws ElException{
		if(erbatch==null || erbatch.getId()<=0){
			this.setElmessage("参数错误");
			return "error";
		}
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		examRooms = classificationDao.listEroomsByErbatchid(erbatch.getId(),userid,getPageNow(),getPageSize());
		count = classificationDao.listEroomsSizeByErbatchid(erbatch.getId(),userid);
		return "listEroomsByErbatchid";
	}
	
	//学拼音
	public String learnPinyin() throws ElException{
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		elClass = classDao.getClassById(elClass.getId());
		if(elClass!=null && elClass.getId()>0){
			classificationDao.addPinyinClass(userid,elClass.getId());
		}
		return "learnPinyin";
	}
	//学汉子
	public String learnHanzi() throws ElException{
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		elClass = classDao.getClassById(elClass.getId());
		if(elClass!=null && elClass.getId()>0){
			classificationDao.addPinyinClass(userid,elClass.getId());
		}	
		return "learnHanzi";
	}
	
	
	
	//wjm20140327修改
	/**
	 * 进入培训班学习页面(即课程列表页面)
	 * @return
	 * @throws ElException
	 */
	public String mystudy_course_view_wjm_front() throws ElException{
		peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		peixunBatch.setProcess(peixunBatchDao.getPeixunBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID)));
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		//培训批次中培训班的培训进度
		if(peixunBatchDao.checkPeixunBatchIsAssignToUser(peixunBatch.getId(), elUser.getId())){
			inDingjiRoom = true;
		}
		if(inDingjiRoom ){
			//获取定的级别
			
			//更新培训批次中被分配的培训班的进度
			classDao.updateClassProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			//更新培训批次进度
			peixunBatchDao.updateBatchProcess(peixunBatch.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			int classid=0;
			//判断该课程是否是当前正在学习的课程
			int nowCourseid = 0;
			//设置正在学习的培训班信息
			peixunBatch.setNowClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),0));
			if(peixunBatch.getNowClass()!=null){
				if(elClass==null || elClass.getId()<=0){
					classid = peixunBatch.getNowClass().getId();
				}else{
					classid = elClass.getId();
				}
				elClass = classDao.getClassById(classid);
				//判断等级是否在定级前
				ELUserClassification elUserClassification = classificationDao.getElUserClassificationByUserid(getSessionIntValue(ElConstants.SESSION_USERID), roomid = classificationDao.getRoomid());
				if(elClass!=null && elClass.getName()!=null && !elClass.getName().equals("") && elClass.getName().compareTo(elUserClassification.getName())<0){
					initCompliance = true;
				}
				MyClass mcl = new MyClass();
				
				int classroomid = eroomDao.getRoomidByClassid_cisco(elClass.getId());
				List<ExamPaper> examPapers = examPaperDao.listEroomExamPaper(classroomid);
				for (int i = 0; i < examPapers.size(); i++) {
					if (examPapers.get(i).getStatus() != 1) {
						if (!studyQuizDao.checkStudyExamPaper(elUser.getId(), examPapers.get(i)
								.getId(), classroomid, elClass.getId())) {
							// 添加该学员到 学员试卷表中
							studyQuizDao.addStudyExamPaper(elUser.getId(), examPapers.get(i)
									.getId(), classroomid, elClass.getId());
						}
					}
				}
				if(classroomid>0){
					mcl.setCanExam(classDao.checkClassCanExam(elUser.getId(),elClass.getId(),classroomid));
					mcl.setExamRoom(new ExamRoom(classroomid));
					System.out.println(studyQuizDao.getExamRoomid(classroomid,elUser.getId()));
					mcl.getExamRoom().setExamPaper(new ExamPaper(studyQuizDao.getExamRoomid(classroomid,elUser.getId())));
					mcl.setHasExam(1);
				}else{
					mcl.setHasExam(0);
				}
				elClass.setMyClass(mcl);
				if(course!=null && course.getId()>0){
					nowCourseid = course.getId();
				}else{
					nowCourseid = courseDao.getNowCourseid(elClass.getId(),elUser.getId());
				}
				myCourses = courseDao.listMyCoursees_wjm(classid,elUser.getId());
				boolean temp = false;
				if(myCourses!=null){
					if(elClass!=null && elClass.getLearnByOrder() == 1){//培训班中课程必须按照顺序来学习
						for(MyCourse mycourse:myCourses){
							//设置MyCourse的canLearn属性，只要是用来前台是否显示图片为黑色
							//需要判断课程是否已经学完、课程对应的结业考场是否已经通过
							temp = classDao.checkcoursecanlearn(mycourse.getCourse().getId(),elClass.getId(),elUser.getId());
							mycourse.setCanLearn(temp == true ? 1 : 0);
							//如果课程学习完成了，设置课程考试结束时间和考试成绩
							//还未测试
							if(mycourse.isPassed()){
								mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUser.getId(),mycourse);
							}
						}
					}
				}
				examRoom = eroomDao.getExamRoom(nowCourseid, elClass.getId());//课程结业考场
				if(nowCourseid>0){
					course = courseDao.getCourseById(nowCourseid);
					if(mycourse == null)	mycourse = new MyCourse();//我的学习课程
					if(course!=null && course.getId()>0){
						//正在学习课程的章节列表
						mycourse.setCourse(course);
						
						//设置课程结业考场的试卷信息
						epid = studyQuizDao.getExamRoomid(examRoom.getId(),elUser.getId());
						if(epid != 0){
							//设置试卷信息
							mycourse.setMyExamPaper(studyQuizDao.getMyExampaper(elUser.getId(), examRoom.getId(), epid));
						}
						//设置完成时间和分数
						mycourse = statisticQuizDao.getFinishtimeByScore(elClass.getId(),elUser.getId(),mycourse);
						
						//判断章节是否都通过，设置myroom是否可以考试  
						if(examRoom != null){
							if(myroom == null)	myroom = new MyRoom();//我的考试
							myroom.setExamroom(examRoom);
							//废弃
							//课程的结业考场是否能考试已交给判断课程是否通过
							myroom.setCanExam(courseDao.checkCpagesIsAllPass(elClass.getId(),elUser.getId(),course.getId()));
							mycourse.setMyRoom(myroom);
						}
						//设置课程是否通过
						mycourse.setPassed(courseDao.checkCourseIsPass(elClass.getId(),elUser.getId(), course.getId()));
						mycourse.setExamPass(eroomDao.getIsPass(elUser.getId(), examRoom.getId()));
					}
				}
			}
			//设置完成的培训班信息
			peixunBatch.setDoneClass(peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),elUser.getId(),1));
			//获取被分配的培训班(附带学习进度)
			myclasses = peixunBatchDao.getMyBatchDetail(elUser.getId());//我的培训班列表
			
			myclasses = myclasses == null?new ArrayList<MyClass>():myclasses;
			int canExam = 0;
			boolean finish = false;
			if(myclasses.size()>0){
				for(int i=0;i<myclasses.size();i++){
					//设置等级培训班进度
					if(elClass.getId() == myclasses.get(i).getElClass().getId()){
						elClass.getMyClass().setProcess(myclasses.get(i).getProcessForElc());
					}
					//判断培训班是否有考场、培训班考场是否通过
					//根据myclasses.get(i).getExamRoom()是否为空来判断培训班是否有考场
					//设置培训班是否能考试==判断本等级是否已经全部学完（即培训班中的课程章节考试全部完成）+智能辅导分是否达标
					if(myclasses.get(i).getExamRoom()!=null && myclasses.get(i).getExamRoom().getId()>0){//培训班存在考场
						canExam = classDao.checkClassCanExam(elUser.getId(),myclasses.get(i).getElClass().getId(),myclasses.get(i).getExamRoom().getId());
						if(myclasses.get(i).getSortid() > 1){//判断上一个培训班考试是否通过
							myclasses.get(i).setCanLearn(classDao.checkClassCanLearn(myclasses.get(i).getElClass().getId(),myclasses.get(i).getSortid()-1,elUser.getId(),peixunBatch.getId()));
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}else{//培训班考场不存在
						if(myclasses.get(i).getSortid() > 1){
							myclasses.get(i).setCanLearn(myclasses.get(i-1).getProcessForElc()==100?true:false);//培训班考场不存在情况下，只需要判断培训班进度是否为100
						}else{
							myclasses.get(i).setCanLearn(true);
						}
					}
					finish = classDao.checkClassExamIsPass(myclasses.get(i).getElClass().getId(), elUser.getId());
					myclasses.get(i).setFinish(finish);
					myclasses.get(i).setCanExam(canExam);
				}
			}
		}
		return "mystudy_course_view_wjm_front";
	}
//-----------------------------------------------------------------------------------------------------------------------
	/////////////////////////////
	//gets   sets
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

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}

	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public EroomLib getEroomLibTree() {
		return eroomLibTree;
	}

	public void setEroomLibTree(EroomLib eroomLibTree) {
		this.eroomLibTree = eroomLibTree;
	}

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public List<ElFunc> getMenus() {
		return menus;
	}

	public void setMenus(List<ElFunc> menus) {
		this.menus = menus;
	}

	public List<ElFunc> getMenus_three() {
		return menus_three;
	}

	public void setMenus_three(List<ElFunc> menus_three) {
		this.menus_three = menus_three;
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


	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public boolean isInDingjiRoom() {
		return inDingjiRoom;
	}

	public void setInDingjiRoom(boolean inDingjiRoom) {
		this.inDingjiRoom = inDingjiRoom;
	}

	public MyRoom getMyroom() {
		return myroom;
	}

	public void setMyroom(MyRoom myroom) {
		this.myroom = myroom;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public PeixunBatchDao getPeixunBatchDao() {
		return peixunBatchDao;
	}

	public void setPeixunBatchDao(PeixunBatchDao peixunBatchDao) {
		this.peixunBatchDao = peixunBatchDao;
	}

	public PeixunBatch getPeixunBatch() {
		return peixunBatch;
	}

	public void setPeixunBatch(PeixunBatch peixunBatch) {
		this.peixunBatch = peixunBatch;
	}

	public List<ElClass> getElclasses() {
		return elclasses;
	}

	public void setElclasses(List<ElClass> elclasses) {
		this.elclasses = elclasses;
	}

	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}

	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}

	public BatchDao getBatchDao() {
		return batchDao;
	}

	public void setBatchDao(BatchDao batchDao) {
		this.batchDao = batchDao;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public ElClass getElClass() {
		return elClass;
	}

	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}

	public List<MyCourse> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
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

	public List<MyCPage> getMyCPages() {
		return myCPages;
	}

	public void setMyCPages(List<MyCPage> myCPages) {
		this.myCPages = myCPages;
	}

	public StudyCourseDao getStudyCourseDao() {
		return studyCourseDao;
	}

	public void setStudyCourseDao(StudyCourseDao studyCourseDao) {
		this.studyCourseDao = studyCourseDao;
	}

	public CoursePageDao getCoursePageDao() {
		return coursePageDao;
	}

	public void setCoursePageDao(CoursePageDao coursePageDao) {
		this.coursePageDao = coursePageDao;
	}

	public List<MyClass> getMyclasses() {
		return myclasses;
	}

	public void setMyclasses(List<MyClass> myclasses) {
		this.myclasses = myclasses;
	}

	public StatisticQuizDao getStatisticQuizDao() {
		return statisticQuizDao;
	}

	public void setStatisticQuizDao(StatisticQuizDao statisticQuizDao) {
		this.statisticQuizDao = statisticQuizDao;
	}

	public MyCourse getMycourse() {
		return mycourse;
	}

	public void setMycourse(MyCourse mycourse) {
		this.mycourse = mycourse;
	}

	public float getIntelligentPoints() {
		return intelligentPoints;
	}

	public void setIntelligentPoints(float intelligentPoints) {
		this.intelligentPoints = intelligentPoints;
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

	public boolean isExprot() {
		return exprot;
	}

	public void setExprot(boolean exprot) {
		this.exprot = exprot;
	}

	public String getReturnIds() {
		return returnIds;
	}

	public void setReturnIds(String returnIds) {
		this.returnIds = returnIds;
	}

	public ExamPaperDao getExamPaperDao() {
		return examPaperDao;
	}

	public void setExamPaperDao(ExamPaperDao examPaperDao) {
		this.examPaperDao = examPaperDao;
	}

	public int getEpid() {
		return epid;
	}

	public void setEpid(int epid) {
		this.epid = epid;
	}

	public IntelligentTutoringPointsDao getIntelligentTutoringPointsDao() {
		return intelligentTutoringPointsDao;
	}

	public void setIntelligentTutoringPointsDao(
			IntelligentTutoringPointsDao intelligentTutoringPointsDao) {
		this.intelligentTutoringPointsDao = intelligentTutoringPointsDao;
	}

	public IntelligentTutoringPoints getIntelligentTutoringPoints() {
		return intelligentTutoringPoints;
	}

	public void setIntelligentTutoringPoints(
			IntelligentTutoringPoints intelligentTutoringPoints) {
		this.intelligentTutoringPoints = intelligentTutoringPoints;
	}

	public ELUserClassification getElUserClassification() {
		return elUserClassification;
	}

	public void setElUserClassification(ELUserClassification elUserClassification) {
		this.elUserClassification = elUserClassification;
	}

	public boolean isClassificationInfo() {
		return classificationInfo;
	}

	public void setClassificationInfo(boolean classificationInfo) {
		this.classificationInfo = classificationInfo;
	}

	public IntelligentLogin getIntelligentLogin() {
		return intelligentLogin;
	}

	public void setIntelligentLogin(IntelligentLogin intelligentLogin) {
		this.intelligentLogin = intelligentLogin;
	}

	public Course getNowCourse() {
		return nowCourse;
	}

	public void setNowCourse(Course nowCourse) {
		this.nowCourse = nowCourse;
	}

	public ExamRoom getDingjiExamRoom() {
		return dingjiExamRoom;
	}

	public void setDingjiExamRoom(ExamRoom dingjiExamRoom) {
		this.dingjiExamRoom = dingjiExamRoom;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
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

	public IntelligentLoginDao getIntelligentLoginDao() {
		return intelligentLoginDao;
	}

	public void setIntelligentLoginDao(IntelligentLoginDao intelligentLoginDao) {
		this.intelligentLoginDao = intelligentLoginDao;
	}

	public int getInit() {
		return init;
	}

	public void setInit(int init) {
		this.init = init;
	}

	public int getNowCourseid() {
		return nowCourseid;
	}

	public void setNowCourseid(int nowCourseid) {
		this.nowCourseid = nowCourseid;
	}

	public boolean isPrecCourseOver() {
		return precCourseOver;
	}

	public void setPrecCourseOver(boolean precCourseOver) {
		this.precCourseOver = precCourseOver;
	}

	public boolean isInitCompliance() {
		return initCompliance;
	}

	public void setInitCompliance(boolean initCompliance) {
		this.initCompliance = initCompliance;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}





}
