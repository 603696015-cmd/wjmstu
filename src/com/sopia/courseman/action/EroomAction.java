package com.sopia.courseman.action;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.assistman.dao.PollDao;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.dao.impl.ClassDaoImpl;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.UserExcelUtil;
import com.sopia.common.chart.ChartUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.quiz.EroomEpCache;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.CRE_note;
import com.sopia.courseman.entities.ClassPara;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ErPara;
import com.sopia.courseman.entities.EroomBatch;
import com.sopia.courseman.entities.EroomBatchLib;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.EroomRegistration;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.ExamRoomAuditDescribes;
import com.sopia.courseman.entities.Examprac;
import com.sopia.courseman.entities.QuestionRanking;
import com.sopia.courseman.entities.QuizPaper;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.newsandmess.dao.impl.MessageDaoImpl;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;
import com.sopia.statman.dao.StatisticQuizDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.dao.impl.StudyClassDaoImpl;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.studyman.entities.SimpleRemack;

public class EroomAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(EroomAction.class);
	private List<ExamRoom> examRooms;
	private MyRoom myroom;
	private ExamRoom examRoom;
	private ExamRoom eroom;
	private List<ExamPaper> examPapers;
	private EroomLib eroomLibTree;
	private EroomLib eroomLib;
	private EroomDao eroomDao;
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
	private ElClass elclass;
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
	private int roomid;
	private int standardLine;//智能辅导分达标线
	private int firstLearnLaterExam;//先学后考
	private List<QuestionRanking> questionRankings;
	private PollDao pollDao;
	private QuestionRanking questionRanking;
	
	private String[] thepiyu;//当前批语
	


	public String[] getThepiyu() {
		return thepiyu;
	}

	public void setThepiyu(String[] thepiyu) {
		this.thepiyu = thepiyu;
	}

	public QuestionRanking getQuestionRanking() {
		return questionRanking;
	}

	public void setQuestionRanking(QuestionRanking questionRanking) {
		this.questionRanking = questionRanking;
	}

	public List<QuestionRanking> getQuestionRankings() {
		return questionRankings;
	}

	public void setQuestionRankings(List<QuestionRanking> questionRankings) {
		this.questionRankings = questionRankings;
	}

	public PollDao getPollDao() {
		return pollDao;
	}

	public void setPollDao(PollDao pollDao) {
		this.pollDao = pollDao;
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

	public List<ClassPara> getClassPara() {
		return classPara;
	}

	public void setClassPara(List<ClassPara> classPara) {
		this.classPara = classPara;
	}

	public int getManner() {
		return manner;
	}

	public void setManner(int manner) {
		this.manner = manner;
	}

	public List<SimpleRemack> getSimpleRemacks() {
		return simpleRemacks;
	}

	public void setSimpleRemacks(List<SimpleRemack> simpleRemacks) {
		this.simpleRemacks = simpleRemacks;
	}

	public SimpleRemack getSimpleRemack() {
		return simpleRemack;
	}

	public void setSimpleRemack(SimpleRemack simpleRemack) {
		this.simpleRemack = simpleRemack;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
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

	public int[] getQBlockids() {
		return qBlockids;
	}

	public void setQBlockids(int[] blockids) {
		qBlockids = blockids;
	}

	public List<ErPara> getErepParas() {
		return erepParas;
	}

	public void setErepParas(List<ErPara> erepParas) {
		this.erepParas = erepParas;
	}

	public int getAjax() {
		return ajax;
	}

	public void setAjax(int ajax) {
		this.ajax = ajax;
	}

	public int[] getQids() {
		return qids;
	}

	public void setQids(int[] qids) {
		this.qids = qids;
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

	public ElClass getElClass() {
		return elClass;
	}

	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}

	public int getDBMethods() {
		return DBMethods;
	}

	public void setDBMethods(int methods) {
		DBMethods = methods;
	}

	public List<ElClass> getElClasss() {
		return elClasss;
	}

	public void setElClasss(List<ElClass> elClasss) {
		this.elClasss = elClasss;
	}

	public EroomRegistration getErRegistration() {
		return erRegistration;
	}

	public void setErRegistration(EroomRegistration erRegistration) {
		this.erRegistration = erRegistration;
	}

	public ExamRoomAuditDescribes getErAuditde() {
		return erAuditde;
	}

	public void setErAuditde(ExamRoomAuditDescribes erAuditde) {
		this.erAuditde = erAuditde;
	}

	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
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

	public EroomBatchLib getErbatchLib() {
		return erbatchLib;
	}

	public void setErbatchLib(EroomBatchLib erbatchLib) {
		this.erbatchLib = erbatchLib;
	}

	public EroomBatchLib getErbatchLibTree() {
		return erbatchLibTree;
	}

	public void setErbatchLibTree(EroomBatchLib erbatchLibTree) {
		this.erbatchLibTree = erbatchLibTree;
	}

	public List<ErPara> getErParas() {
		return erParas;
	}

	public void setErParas(List<ErPara> erParas) {
		this.erParas = erParas;
	}

	public String eroomlib_list() throws ElException {
		// eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);

			// qlbTree = questionDao.getQlibTree(
			// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
			// true);
		}
		return "eroomlib_list";
	}

	public String eroomlib_deleteInit() throws ElException {
		if (eroomLib.getId() == 1) {
			setElmessage("不能删除根类别");
			return "error";
		}
		eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		return "eroomlib_delete";
	}

	public String eroomlib_delete() throws ElException {
		// if (course_sourse == 1) {
		// // 并入上级
		//			
		// } else {
		// // 一起删除
		//
		// }
		if (eroomLib.getId() == 1) {
			setElmessage("不能删除根类别");
			return "error";
		}
		eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		if (course_sourse == 0) {
			// 并入上级
			eroomDao.updateElibParentid(eroomLib.getId(), eroomLib.getParent()
					.getId());
			eroomDao.updateExamroomParentid(eroomLib.getId(), eroomLib
					.getParent().getId());
		} else {
			// 一起删除
//			eroomDao.deleteElibAndSub(eroomLib.getId());
			//改成假删除
			eroomDao.deleteElibAndSubNot(eroomLib.getId());
		}
//		eroomDao.deleteEroomLib(eroomLib.getId());
		eroomDao.deleteeroomLibNot(eroomLib.getId());
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("eroom_lib");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOMLIB,
				ElLoggerConstants.LOG_TYPE_DELETE, eroomLib.getName(),
				ElLoggerConstants.LOG_RES_SUCC, eroomLib.getId());
		return "eroomlib_delete_success";
	}

	public String eroomlib_addInit() throws ElException {
		// eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);

		}
		if (eroomLibTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的考场库");
			return "error";
		}
		return "eroomlib_add";
	}

	public String eroomlib_add() throws ElException {
		eroomDao.addEroomLib(eroomLib);
		// if (null != eroomLib.getOpusers()) {
		// for (int i = 0; i < eroomLib.getOpusers().size(); i++) {
		// // + eroomLib.getOpusers().get(i).getId());
		// if (!eroomDao.checkOpUsers("op", eroomLib.getOpusers().get(i)
		// .getId(), eroomLib.getId()))
		// eroomDao.addOpusers("op", eroomLib.getOpusers().get(i)
		// .getId(), eroomLib.getId());
		// }
		// }
		// if (null != eroomLib.getUseusers()) {
		// for (int i = 0; i < eroomLib.getUseusers().size(); i++) {
		// // + eroomLib.getUseusers().get(i).getId());
		// if (!eroomDao.checkOpUsers("op", eroomLib.getUseusers().get(i)
		// .getId(), eroomLib.getId()))
		// eroomDao.addOpusers("op", eroomLib.getUseusers().get(i)
		// .getId(), eroomLib.getId());
		// }
		// }
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("eroom_lib");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOMLIB,
				ElLoggerConstants.LOG_TYPE_ADD, eroomLib.getName(),
				ElLoggerConstants.LOG_RES_SUCC, eroomLib.getId());
		return "eroomlib_add_success";
	}

	public String eroomlib_view() throws ElException {
		// eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		if(eroomLib==null||eroomLib.getId()<=0){	
			setElmessage("您需要查看的考场库不存在,请重新选择！");
			return "error";
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}

		eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		eroomLib.setOpusers(eroomDao.getOpUsers("op", eroomLib.getId()));
//		eroomLib.setUseusers(eroomDao.getOpUsers("op", eroomLib.getId()));
		return "eroomlib_view";
	}

	/**考场类别修改初始化
	 * Description: 
	* @Version1.0 2012-7-12 下午06:56:59 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String eroomlib_alterInit() throws ElException {
		eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		// eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
		// eroomLib
		
		// .getId(), false);

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		eroomLib.setOpusers(eroomDao.getOpUsers("op", eroomLib.getId()));
//		eroomLib.setUseusers(eroomDao.getOpUsers("op", eroomLib.getId()));
		return "eroomlib_alter";
	}

	public String erlib_delete_user() throws ElException {
		eroomDao.deleteOpusers(optype, elUser.getId(), eroomLib.getId());
		return null;
	}

	public String eroomlib_alter() throws ElException {
		if (eroomLib.getId() == 1) {
			eroomLib.setParent(new EroomLib(0));
		}
		if (eroomLib.getParent() == null) {// 如果是省厅管理员，编辑1级子节点的时候会出现null
			eroomLib.setParent(new ElNode(1));
		}
		eroomDao.alterEroomLib(eroomLib);
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("eroom_lib");
		// if (null != eroomLib.getOpusers()) {
		// for (int i = 0; i < eroomLib.getOpusers().size(); i++) {
		// // + eroomLib.getOpusers().get(i).getId());
		// if (!eroomDao.checkOpUsers("op", eroomLib.getOpusers().get(i)
		// .getId(), eroomLib.getId()))
		// eroomDao.addOpusers("op", eroomLib.getOpusers().get(i)
		// .getId(), eroomLib.getId());
		// }
		// }
		// if (null != eroomLib.getUseusers()) {
		// for (int i = 0; i < eroomLib.getUseusers().size(); i++) {
		// // + eroomLib.getUseusers().get(i).getId());
		// if (!eroomDao.checkOpUsers("op", eroomLib.getUseusers().get(i)
		// .getId(), eroomLib.getId()))
		// eroomDao.addOpusers("op", eroomLib.getUseusers().get(i)
		// .getId(), eroomLib.getId());
		// }
		// }
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOMLIB,
				ElLoggerConstants.LOG_TYPE_ALTER, eroomLib.getName(),
				ElLoggerConstants.LOG_RES_SUCC, eroomLib.getId());
		return "eroomlib_alter_success";
	}

	/**
	 * 一般考试场次
	 * 
	 * @return
	 * @throws ElException
	 */
	public String examroomwithoutcourse_list() throws ElException {
		// int libid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		// if(sublibs != 0){
		// // libid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
		// // : eroomLib.getId();
		// libid = eroomLib == null ? eroomLibTree.getId()
		// : eroomLib.getId();
		// }else{
		// libid =eroomLibTree.getId();
		// }
		// and er.valid != 9 不现实已删除状态
		// examRooms = eroomDao.listErWithoutCourse(eroomLibTree,
		// libid,getSessionIntValue(ElConstants.SESSION_ROLE),"",
		// getPageNow(), getPageSize());
		// count = eroomDao.listErWithoutCourseSize(eroomLibTree,
		// libid,getSessionIntValue(ElConstants.SESSION_ROLE),"");
		// examRooms = eroomDao.listErWithoutCourse(eroomLibTree, libid,
		// getSessionIntValue(ElConstants.SESSION_ROLE), "", examRoom,
		// getPageNow(), getPageSize());
		// count = eroomDao.listErWithoutCourseSize(eroomLibTree, libid,
		// getSessionIntValue(ElConstants.SESSION_ROLE), "", examRoom);
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
				" and er.valid not in (9)", examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
				" and er.valid not in (9)", examRoom);

		return "examroomwithoutcourse_list";
	}
	/**
	 * 更改考场状态
	 * @return
	 * @throws ElException
	 */
	public String eroomStatusUpdate() throws ElException {
		eroomDao.examRoomSh(examRoom.getId(), examRoom.getValid());
		if(examRoom.getValid()==1){
			return "eroomAssignJingzhongList";
		}else if(examRoom.getValid()==4||examRoom.getValid()==5){
			if(examRoom.getValid()==5){
				eroomDao.examRoomisNormal(examRoom.getId(), 1);
			}
			return "eroomAuditJingzhongList";
		}else{
			setElmessage("参数有误！");
			return "error";
		}
	}
	/**
	 * 考场分配给警钟列表页
	 * @return
	 * @throws ElException
	 */
	public String eroomAssignJingzhongList() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		}else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
				" and er.valid not in (9)", examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
				" and er.valid not in (9)", examRoom);
		return "eroomAssignJingzhongList";
	}
	/**
	 * 考场分配给警钟内容页
	 * @return
	 * @throws ElException
	 */
	public String eroomAssignJingzhong() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		List<Integer> checked=eroomDao.listEroomAllJingzhong(examRoom.getId());
		getRequest().setAttribute("checked", checked);
		String updateIsOk=getRequest().getParameter("updateOk");
		if("1".equals(updateIsOk)){
			setElmessage("保存成功!");
		}
		return "eroomAssignJingzhong";
	}
	/**
	 * 考场分配给警钟处理
	 * @return
	 * @throws ElException
	 */
	public String eroomAssignJingzhongProcess() throws ElException {
		String[] jzIds=getRequest().getParameterValues("jzIds");
		eroomDao.addEroomJingzhong(examRoom.getId(), jzIds);
		return "eroomAssignJingzhong";
	}
	/**
	 * 考场警钟审核列表页
	 * @return
	 * @throws ElException
	 */
	public String eroomAuditJingzhongList() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		}else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
				" and er.valid not in (9)", examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
				" and er.valid not in (9)", examRoom);
		return "eroomAuditJingzhongList";
	}
	/**
	 * 考场警钟审核内容页
	 * @return
	 * @throws ElException
	 */
	public String eroomAuditJingzhong() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		List<Integer> jzIds=eroomDao.listEroomAllJingzhong(examRoom.getId());
//		jingzhongs = new ArrayList<BaseDatat>(jzIds.size());
//		for(int i=0;i<jzIds.size();i++){
//			jingzhongs.add(userDao.getBaseDatatById(jzIds.get(i)));
//		}
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		List<Integer> checked=eroomDao.listEroomAllJingzhong(examRoom.getId());
		getRequest().setAttribute("checked", checked);
		String updateIsOk=getRequest().getParameter("updateOk");
		if("1".equals(updateIsOk)){
			setElmessage("操作成功!");
		}
		return "eroomAuditJingzhong";
	}
	/**
	 * 考场警钟审核处理
	 * @return
	 * @throws ElException
	 */
	public String eroomAuditJingzhongProcess() throws ElException {
		String[] jzIds=getRequest().getParameterValues("jzIds");
		//eroomDao.updateEroomJingzhong(examRoom.getId(), jzIds, examRoom.getValid());
		eroomDao.addEroomJingzhong(examRoom.getId(), jzIds);
		return "eroomAuditJingzhong";
	}
	
	public String examroom_deletelist() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		int libid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
				: eroomLib.getId();

		examRooms = eroomDao.listErWithoutCourse(eroomLibTree, libid,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.valid != 9", getPageNow(), getPageSize());
		count = eroomDao.listErWithoutCourseSize(eroomLibTree, libid,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.valid != 9");
		return "examroom_deletelist";
	}

	public String examroom_deletes() throws ElException {
		if (null != examRooms)
			for (int i = 0; i < examRooms.size(); i++) {
				eroomDao.deleteExamRoom(examRooms.get(i).getId());
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_EROOM,
						ElLoggerConstants.LOG_TYPE_DELETE, examRooms.get(i)
								.getTitle(), ElLoggerConstants.LOG_RES_SUCC,
						examRooms.get(i).getId());
			}
		return "examroom_deletelist";
	}

	/**
	 * 考核阅卷
	 * 
	 * @return
	 * @throws ElException
	 */
	public String examroomwithoutcourse_readlist() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		// examRooms = eroomDao.listExamRoomRead(
		// getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		// getPageSize());
		// count = eroomDao
		// .listExamRoomReadsize(getSessionIntValue(ElConstants.SESSION_USERID));
		examRooms = eroomDao.listExamRoomRead(
				getSessionIntValue(ElConstants.SESSION_USERID),
				" and er.iscommon=1 and er.valid != 9 ", examRoom,getSessionIntValue(ElConstants.SESSION_ROLE),
				getPageNow(), getPageSize());
		count = eroomDao.listExamRoomReadsize(
				getSessionIntValue(ElConstants.SESSION_USERID),
				" and er.iscommon=1 and er.valid != 9 ", examRoom,getSessionIntValue(ElConstants.SESSION_ROLE));
		return "examroomwithoutcourse_readlist";
	}

	/**
	 * 结业阅卷
	 * 
	 * @return
	 * @throws ElException
	 */
	public String examroomwithoutcourse_elclass_readlist() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		examRooms = eroomDao.listExamRoomRead(
				getSessionIntValue(ElConstants.SESSION_USERID),
				" and er.iscommon=0 and er.valid != 9  ", getPageNow(),
				getPageSize());
		count = eroomDao.listExamRoomReadsize(
				getSessionIntValue(ElConstants.SESSION_USERID),
				" and er.iscommon=0 and er.valid != 9 ");
		return "examroomwithoutcourse_elclass_readlist";
	}

	private StatisticQuizDao statisticQuizDao;

	public StatisticQuizDao getStatisticQuizDao() {
		return statisticQuizDao;
	}

	public void setStatisticQuizDao(StatisticQuizDao statisticQuizDao) {
		this.statisticQuizDao = statisticQuizDao;
	}

	/**阅卷列表页（列出试卷数量并非每次答卷记录）
	 * Description: 
	* @Version1.0 2012-7-3 下午05:48:07 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String exampaperreadlist() throws ElException {
		// getPageSize()= getPageSize()==0?10:getPageSize();
		// myExampapers = eroomDao.listReadPapers(examRoom.getId(),
		// getPageNow(),
		// getPageSize());
//		List<MyRoom> mr = statisticQuizDao.listquiz_detail_view(examRoom
//				.getId());
		List<MyRoom> mr = statisticQuizDao.listquiz_detail_view(examRoom.getId(),myroom,getPageNow(),getPageSize());
		count=statisticQuizDao.listquiz_detail_viewSize(examRoom.getId(),myroom);
		if (null != mr) {
			for (int i = 0; i < mr.size(); i++) {
				mr.get(i).setMyExamPapers(
						statisticQuizDao.list_detail_view_quizpaper(examRoom
								.getId(), mr.get(i).getTester().getId()));
			}
			if(mr.size()>0){
				int isLeader=studyQuizDao.study_isLeader(getSessionIntValue(ElConstants.SESSION_USERID), examRoom.getId());
				for (int i = 0; i < mr.get(0).getMyExamPapers().size(); i++) {
					examPaper=mr.get(0).getMyExamPapers().get(i).getExamPaper();
					int n=studyQuizDao.getEroomAllQuizcount(examRoom.getId(), examPaper.getId());
					if(getSessionIntValue(ElConstants.SESSION_ROLE)==1||isLeader==1){
						examPaper.setQuizcount(n);
					}else{
						int m=studyQuizDao.getUserReadexampaperCount(examRoom.getId(), examPaper.getId(), getSessionIntValue(ElConstants.SESSION_USERID));
						examPaper.setQuizcount(n-m);
					}
				}
			}
		}
		// count = eroomDao.listReadPapersSize(examRoom.getId());
		examRoom=eroomDao.getExamRoomByid(examRoom.getId());
		examRoom.setMyrooms(mr);
		return "exampaperreadlist";
	}
	/**按照试卷和考场，账号找出答卷（历次答卷）列表
	 * Description: 
	* @Version1.0 2012-7-3 下午05:49:14 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String exampaperreadlist_meps() throws ElException {
		myExamPapers =
						statisticQuizDao.list_read_quizpaper(examRoom.getId(), examPaper.getId(),elUser.getId());
		return "exampaperreadlist_meps";
	}
	public String listStudyRoomRecord() throws ElException {
		if (myroom == null)
			myroom = new MyRoom();
		// myroom.setMyRoomRecord(studyQuizDao.listStudyRoomRecordSqinfo(elUser
		// .getId(), examRoom.getId()));
		return "listStudyRoomRecord";
	}

	public String requiz() throws ElException {
		if (myExampapers != null)
			for (int i = 0; i < myExampapers.size(); i++) {
				eroomDao.requiz(myExampapers.get(i).getId());
			}
		return "exampaperreadlist";
	}

	public String exampaperread() throws ElException {
		if (myExamPaper == null || myExamPaper.getId() <= 0) {
			setElmessage("没找到考试记录，请确定是否正确！");
			return "error";
		}
		myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
		examPaper = studyQuizDao.getMyExamPaper(myExamPaper.getId());

		// examPaper = examPaperDao.getExamPaperById(myExamPaper.getExamPaper()
		// .getId());// myExamPaper.getExamPaper();
		// if (null == myExamPaper.getMyAnswer()
		// || "".equals(myExamPaper.getMyAnswer().trim())) {
		// examPaper = examPaperDao.getEPAllInfoById(examPaper.getId());
		// } else {
		// examPaper.setEpBlocks(new ArrayList<ExamPaperBlock>());
		// ELUser user = userDao
		// .getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		// ExamPaperUtil.getAnswerExampaper(myExamPaper.getMyAnswer(),
		// examPaper, examPaperDao, questionDao, user.getShengri());
		// }
		return "exampaperread";
	}

	/**
	 * 阅卷提交
	 * 
	 * @return
	 * @throws ElException
	 */
	// public String exampaperread_submit() throws ElException {
	// float score1 = 0;
	// float score_essay=0;
	// float score_office=0;
	// if (null != thescore&&thescoreEssay!=null&&thescoreOffice!=null){
	// //由于主观题要把设置的分数显示到答卷上，所有要分开处理
	// for (int i = 0; i < thescore.length; i++) {//此处处理客观题
	// score1 += thescore[i];
	// }
	// for (int i = 0; i < thescoreEssay.length; i++) {
	// score_essay += thescoreEssay[i];
	// //设置问答题分值到该试卷的对应题目上
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsEssay[i],thescoreEssay[i]);
	// }
	// //设置问答题块的总分
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), essayBlockId,
	// score_essay);
	// for (int i = 0; i < thescoreOffice.length; i++) {
	// score_office += thescoreOffice[i];
	// //设置office题分值到该试卷的对应题目上
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsOffice[i],thescoreOffice[i]);
	// }
	// //设置office题块的总分
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), officeBlockId,
	// score_office);
	// }
	// score1+=score_essay+score_office;
	// studyQuizDao.setFinalScore(myExamPaper.getId(), score1);//hdl
	// 此方法没动过，score1的值计算出来还是原来的值（页面上所有试题的总分）
	// setElmessage("批改成功！");
	// return "exampaperread_submit";
	// }
	/**
	 * 阅卷提交
	 * 
	 * @return
	 * @throws ElException
	 */
	// public String exampaperread_submit() throws ElException {
	// float score_sum = 0;
	// float score_essay=0;
	// float score_office=0;
	// float score_yesOrNo=0;
	// float score_select1=0;
	// float score_select2=0;
	// float score_blank=0;
	// float score_dazi=0;
	// float score_mail=0;
	// float score_search=0;
	// float score_cl=0;
	// if (thescoreEssay!=null){
	// for (int i = 0; i < thescoreEssay.length; i++) {
	// score_essay += thescoreEssay[i];
	// //设置问答题分值到该试卷的对应题目上
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsEssay[i],thescoreEssay[i]);
	// }
	// //设置问答题块的总分
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), essayBlockId,
	// score_essay);
	// }
	// if (thescoreOffice!=null){
	// for (int i = 0; i < thescoreOffice.length; i++) {
	// score_office += thescoreOffice[i];
	// //设置office题分值到该试卷的对应题目上
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsOffice[i],thescoreOffice[i]);
	// }
	// //设置office题块的总分
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), officeBlockId,
	// score_office);
	// }
	// if (thescoreYesOrNo!=null){//判断题
	// for (int i = 0; i < thescoreYesOrNo.length; i++) {
	// score_yesOrNo += thescoreYesOrNo[i];
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsYesOrNo[i],thescoreYesOrNo[i]);
	// }
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), yesOrNoBlockId,
	// score_yesOrNo);
	// }
	// if (thescoreSelect1!=null){//单选题
	// for (int i = 0; i < thescoreSelect1.length; i++) {
	// score_select1 += thescoreSelect1[i];
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsSelect1[i],thescoreSelect1[i]);
	// }
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), select1BlockId,
	// score_select1);
	// }
	// if (thescoreSelect2!=null){//多选题
	// for (int i = 0; i < thescoreSelect2.length; i++) {
	// score_select2 += thescoreSelect2[i];
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsSelect2[i],thescoreSelect2[i]);
	// }
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), select2BlockId,
	// score_select2);
	// }
	// if (thescoreBlank!=null){//填空题
	// for (int i = 0; i < thescoreBlank.length; i++) {
	// score_blank += thescoreBlank[i];
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsBlank[i],thescoreBlank[i]);
	// }
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), blankBlockId,
	// score_blank);
	// }
	// if (thescoreDazi!=null){//打字题
	// for (int i = 0; i < thescoreDazi.length; i++) {
	// score_dazi += thescoreDazi[i];
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsDazi[i],thescoreDazi[i]);
	// }
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), daziBlockId,
	// score_dazi);
	// }
	// if (thescoreMail!=null){//邮件题
	// for (int i = 0; i < thescoreMail.length; i++) {
	// score_mail += thescoreMail[i];
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsMail[i],thescoreMail[i]);
	// }
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), mailBlockId,
	// score_mail);
	// }
	// if (thescoreSearch!=null){//搜索题
	// for (int i = 0; i < thescoreSearch.length; i++) {
	// score_search += thescoreSearch[i];
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsSearch[i],thescoreSearch[i]);
	// }
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), searchBlockId,
	// score_search);
	// }
	// if(this.cltIds!=null){//材料题
	// int k=0;
	// for (int i = 0; i < cltIds.length; i++) {
	// String[] tempLen=getRequest().getParameterValues("cl_"+i);
	// if(tempLen!=null&&tempLen.length>0){
	// float tempScore=0;
	// for (int j = 0; j < tempLen.length; j++) {
	// tempScore+=Float.parseFloat(tempLen[j]);
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),qidsCl[k],Float.parseFloat(tempLen[j]));
	// k++;
	// }
	// score_cl+=tempScore;
	// studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),cltIds[i],tempScore);
	// }
	// }
	// studyQuizDao.setStudyBlocksScore(myExamPaper.getId(), clBlockId,
	// score_cl);
	// }
	// score_sum=score_essay+score_office+score_yesOrNo+score_select1+score_select2+score_blank+score_dazi+score_mail+score_search+score_cl;
	// //studyQuizDao.setFinalScore(myExamPaper.getId(), score_sum);//hdl
	// 此方法没动过，score1的值计算出来还是原来的值（页面上所有试题的总分）
	// setElmessage("批改成功！");
	// return "exampaperread_submit";
	// }
	/**
	 * 阅卷提交
	 * 
	 * @return
	 * @throws ElException
	 */
	public String exampaperread_submit() throws ElException {
		myExamPaper=studyQuizDao.getMyEpById(myExamPaper.getId());
		int isLeader=studyQuizDao.study_isLeader(getSessionIntValue(ElConstants.SESSION_USERID), myExamPaper.getExamRoom().getId());
		StringBuffer logInfo=new StringBuffer("");
		System.out.println(thepiyu.length+"=========1167");
		if (thescore != null) {
			Question tempq=null;
			for (int i = 0; i < thescore.length; i++) {
				//先获取该题的原分值
				tempq=new Question(qids[i]);
				tempq.setEpblock(new ExamPaperBlock(qBlockids[i]));
				tempq=studyQuizDao.getQuestionByREBid(myExamPaper.getId(),tempq);
				//判断该题的分值是否有改变
				if(tempq.getMyScore()!=thescore[i]){
					String tempStr="";
					if(tempq.getMyScore()<thescore[i]){
						tempStr="加了"+(thescore[i]-tempq.getMyScore());
					}else{
						tempStr="减了"+(tempq.getMyScore()-thescore[i]);
					}
					if(tempq.getTitle().length()>15){
						tempq.setTitle(tempq.getTitle().substring(0,15)+"...");
					}
					logInfo.append("大题【"+tempq.getEpblock().getTitle()+"("+qBlockids[i]+")】的【"+tempq.getTitle()+"("+tempq.getId()+")】题"+tempStr+"分.\n");
	                			
				}
				//非超管和组长不改变学员实际分值
				if(getSessionIntValue(ElConstants.SESSION_ROLE)==1||isLeader==1){
					// 设置分值到该试卷的对应题目上
					studyQuizDao.setStudyQuestionScore(myExamPaper.getId(),
							qids[i],qBlockids[i], thescore[i]);
					// 设置批语到该试卷的对应题目上
					studyQuizDao.setStudyQuestionPiyu(myExamPaper.getId(),
							qids[i],qBlockids[i], thepiyu[i]);
				}
				//从exam_rappraises表里取出isLeader字段，判断是不是普通阅卷人，然后将阅卷人id，答卷id，大题id，小题id，分值等信息保存到multiUserPapers表中
				int userId =getSessionIntValue(ElConstants.SESSION_USERID);
				int sqid=myExamPaper.getId();
				int Blockid=qBlockids[i];
				int Qid=qids[i];
				float Score=thescore[i];				
				//System.out.println("阅卷人id："+userId+"\t 答卷id是："+sqid+"\t 大题id:"+Blockid+"\t 小题id："+Qid+"\t 分数值："+Score);
				if(!studyQuizDao.checkStudy_score(userId,sqid,Blockid,Qid)){
					studyQuizDao.study_scoreAdd(userId,sqid,Blockid,Qid,Score);
				}else{
					studyQuizDao.study_scoreUpdate(userId,sqid,Blockid,Qid,Score);
				}
				
			}
		}
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1||isLeader==1){
			// 调用存储过程来设定版块,材料题等其他分值
			studyQuizDao.study_marking(myExamPaper.getId());
			//设置考场及试卷状态
			studyQuizDao.setStudyEroomStatus(myExamPaper.getExamRoom().getId(), myExamPaper.getTester().getId());
		}
		setElmessage("批改成功！");
		//记录阅卷系统日志
		if(logInfo.length()>0){
			logInfo.insert(0, "考场【"+myExamPaper.getExamRoom().getTitle()+"("+myExamPaper.getExamRoom().getId()+")】中的学员【"+myExamPaper.getTester().getRealname()+"("+myExamPaper.getTester().getId()+")】的答卷【"+myExamPaper.getExamPaper().getTitle()+"("+myExamPaper.getExamPaper().getId()+")】的阅卷详情：\n");
		}
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_ADD,
				logInfo.toString(),
				ElLoggerConstants.LOG_RES_SUCC);
		//return "exampaperread_submit";
		return "exampaperread_next";
	}
	/**
	 * 读取下一份答卷
	 * @return
	 * @throws ElException
	 */
	public String exampaperread_next() throws ElException {
		myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
		int myEpid=0;
		int isLeader=studyQuizDao.study_isLeader(getSessionIntValue(ElConstants.SESSION_USERID), myExamPaper.getExamRoom().getId());
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1||isLeader==1){
			myEpid=studyQuizDao.getStudyExamPaper(myExamPaper.getId(),myExamPaper.getExamRoom().getId(), myExamPaper.getExamPaper().getId());
		}else{
			myEpid=studyQuizDao.getStudyExamPaper(myExamPaper.getId(),myExamPaper.getExamRoom().getId(), myExamPaper.getExamPaper().getId(),getSessionIntValue(ElConstants.SESSION_USERID));
		}
		if(myEpid>0){
			myExamPaper.setId(myEpid);
			return "exampaper_read";
		}else{
			setElmessage("已作答的试卷都批阅完了！");
			return "error";
		}
	}

	/**
	 * Description: 一般阅卷
	 * 
	 * @Version1.0 2011-9-26 下午09:49:27 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String exampaper_read() throws ElException {
		if (myExamPaper == null || myExamPaper.getId() <= 0) {
			setElmessage("没找到考试记录，请确定是否正确！");
			return "error";
		}
		myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
		int isLeader=studyQuizDao.study_isLeader(getSessionIntValue(ElConstants.SESSION_USERID), myExamPaper.getExamRoom().getId());
		elUser = userDao.getUserById(myExamPaper.getTester().getId());
		//查询未批阅答卷数
		int n=studyQuizDao.getEroomAllQuizcount(myExamPaper.getExamRoom().getId(), myExamPaper.getExamPaper().getId());
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1||isLeader==1){
			examPaper = studyQuizDao.getMyExamPaper(myExamPaper.getId());
			elUser.setIsLeader(1);
			myExamPaper.getExamPaper().setQuizcount(n);//临时存储用
		}else{
			examPaper = studyQuizDao.getMyExamPaper(myExamPaper.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
			int m=studyQuizDao.getUserReadexampaperCount(myExamPaper.getExamRoom().getId(), examPaper.getId(), getSessionIntValue(ElConstants.SESSION_USERID));
			myExamPaper.getExamPaper().setQuizcount(n-m);//临时存储用
		}
		examPaper.setUserage(elUser.getAge());
//		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){//如果是阅卷组长和超级管理员
//			elUser.setIsLeader(1);
//		}else{
//			elUser.setIsLeader(studyQuizDao.study_isLeader(getSessionIntValue(ElConstants.SESSION_USERID), myExamPaper.getExamRoom().getId()));
//		}
		//查询页面需要的阅卷人和分数
		
		return "exampaper_read";
	}

	public String exampaperreadInit() throws ElException {
		return "exampaperreadInit";
	}

	public String exampaperread_roomlist() throws ElException {
		examRooms = courseDao.listERbyCidandTitle(course.getId(), examRoom
				.getTitle());
		return "exampaperread_roomlist";
	}

	public String erwithout_addInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		//初始化默认警种（创建者警种）
		elUser=userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));

		BaseDatat bdt=userDao.getBaseDatatById(elUser.getJingzhong());
	//	getRequest().setAttribute("userJingzhong", bdt.getBasevalue());
		Course c = courseDao.getCourseById(course.getId());
		String classidStr = getRequest().getParameter("course.classid");
		int classid = 0;
		if (classidStr != null) {
			classid = Integer.parseInt(classidStr);
		}
		elclass = classDao.getClassById(classid);
		examRoom = examRoom == null ? new ExamRoom() : examRoom;
		examRoom.setClassid(classid);
		if (elclass != null && c != null)
			examRoom.setTitle(elclass.getName() + "的课程" + c.getName() + "的考场");
		if (elclass == null && c != null)
			examRoom.setTitle("课程" + c.getName() + "的考场");

		if (eroomLibTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的考场类别");
			return "error";
		}
		return "erwithout_add";
	}
	/**
	 * 添加问卷初始化
	 * @return
	 * @throws ElException
	 */
	public String addQuestionnaireInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		//初始化默认警种（创建者警种）
		elUser=userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));

		BaseDatat bdt=userDao.getBaseDatatById(elUser.getJingzhong());
	//	getRequest().setAttribute("userJingzhong", bdt.getBasevalue());
		if (eroomLibTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的考场类别");
			return "error";
		}
		return "addQuestionnaireInit";
	}

	/**考场添加
	 * @return
	 * @throws ElException
	 */
	public String erwithout_add() throws ElException {
		examRoom.setCourse(course);
		examRoom.setCreater(new ELUser(
				(getSessionIntValue(ElConstants.SESSION_USERID))));
		// 处理ip段
		String[] ipStart = getRequest().getParameterValues("ipStart");
		String[] ipEnd = getRequest().getParameterValues("ipEnd");
		StringBuffer ipStartbu = new StringBuffer("");
		StringBuffer ipEndbu = new StringBuffer("");
		if (ipStart != null) {
			for (int i = 0; i < ipStart.length; i++) {
				ipStartbu.append(ipStart[i] + "_");
			}
			// 去掉最后一个下划线
			ipStartbu.deleteCharAt(ipStartbu.length() - 1);
		}
		if (ipEnd != null) {
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndbu.append(ipEnd[i] + "_");
			}
			// 去掉最后一个下划线
			ipEndbu.deleteCharAt(ipEndbu.length() - 1);
		}
		// 赋值存到数据库
		examRoom.setIpStart(ipStartbu.toString());
		examRoom.setIpEnd(ipEndbu.toString());
		if (course.getId() != -1) {// 代表是结业考试信息的
			examRoom.setIscommon(0);
		}
		if (course.getId() == -1) {
			course.setClassid(-1);
		}
		examRoom.setClassid(course.getClassid());
		// if(examRoom.getErtype()==0){
		// examRoom.setExamcount(1);
		// }
		eroomDao.addExamRoom(examRoom);
		if (examRoom.getIsApplication() == 1 && erRegistration != null) {// 是否为可申请
			erRegistration.setEroom(examRoom);
			if (examRooms != null)
				erRegistration.setExamRoom(examRooms);
			if (elClasss != null)
				erRegistration.setElclass(elClasss);
			if (!eroomDao.checkElclassRegistration(examRoom.getId())) {
				eroomDao.addEroomRegistration(erRegistration);
			} else {
				eroomDao.alterEroomRegistration(erRegistration);
			}
		}
		if (null != examRoom.getInvigilators()) {//监考人员
			for (int i = 0; i < examRoom.getInvigilators().size(); i++) {
				if (!eroomDao.checkEroomUsers("rinvigilators", examRoom
						.getInvigilators().get(i).getId(), examRoom.getId()))
					eroomDao
							.addEroomusers("rinvigilators", examRoom
									.getInvigilators().get(i).getId(), examRoom
									.getId());
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"myExamroom_list", 0);
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getAppraises()) {//阅卷人员
			//int isHeader =examRoom.getAppr_header().getId();
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
//				roleDao.setUserfunc(examRoom.getAppraises().get(i).getId(),
//						"examroomwithoutcourse_readlist", 0);
//				roleDao.setUserfunc(examRoom.getAppraises().get(i).getId(),
//						"admin", 0);
				
				
			}
		}
		if (null != examRoom.getValids()) {//复核人员
			for (int i = 0; i < examRoom.getValids().size(); i++) {
				if (!eroomDao.checkEroomUsers("valids", examRoom.getValids()
						.get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("valids", examRoom.getValids()
							.get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"examroom_validlist", 0);
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getSelectings()) {
			for (int i = 0; i < examRoom.getSelectings().size(); i++) {
				if (!eroomDao.checkEroomUsers("selectings", examRoom
						.getSelectings().get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("selectings", examRoom
							.getSelectings().get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getSelectings().get(i).getId(),
//						"examroom_selectings", 0);
//				roleDao.setUserfunc(examRoom.getSelectings().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examPapers) {
			int sortid=0;
			for (int i = 0; i < examPapers.size(); i++) {
				ExamPaper ep = examPapers.get(i);
				if (ep != null && ep.getId() != 0)
					if (!eroomDao.checkEroomeps(examRoom.getId(), ep.getId())
							&& ep.getId() != 0)
						// 因为注释掉练习
						// ep.setPrac(new ExamPaper(0));
						// eroomDao.addEroomeps(examRoom.getId(), ep.getId(), ep
						// .getPrac().getId(), ep.getPractimes(), ep
						// .getPracscore(), ep.getPassgrade(), ep
						// .getStuview());
						// eroomDao.addEroomeps(examRoom.getId(), ep.getId(), ep
						// .getPrac().getId(), ep.getPractimes(), ep
						// .getPracscore(), ep.getPassgrade(), ep
						// .getStuview(),ep.getQuizlook(),ep.getScorelook());
						sortid++;
						eroomDao.addEroomeps(examRoom.getId(), ep.getId(), 0,
								ep.getPractimes(), ep.getPracscore(), ep
										.getPassgrade(), ep.getStuview(), ep
										.getQuizlook(), ep.getScorelook(), ep
										.getQuizcount(), ep.getPassmanner(),sortid);
					//	studyQuizDao.addStudyExamPaper(userid, epid, roomid, classid)
			}
		}
		if (course.getId() == -1) {// 考核考场
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_ADD, examRoom.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
//			return "examroom_alllist";
			return "examroom_view";
		}
		if (course.getClassid() == 0) {// 课程考场
			course = courseDao.getCourseById(course.getId());
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_COURSE,
					ElLoggerConstants.LOG_TYPE_ADD, course.getName() + "的考场("
							+ examRoom.getTitle() + ")",
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
			return "examroom_listbyc";
		}

		// 培训班考场
		elclass = classDao.getClassById(course.getClassid());
		int cid = course.getClassid();
		course = courseDao.getCourseById(course.getId());
		course.setClassid(cid);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASS,
				ElLoggerConstants.LOG_TYPE_ADD,
				elclass.getName() + "->" + course.getName() + "的考场("
						+ examRoom.getTitle() + ")",
				ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		return "examroom_choose_listbycInit";
	}

	
	public String erwithout_alterInit() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		// if (examRoom.getValid() == 1) {
		// setElmessage("场次已开通不能再修改");
		// return "error";
		// }
		course = examRoom.getCourse();
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
//		examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
//				.getId()));
		
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
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		//初始化默认警种（创建者警种）
		erRegistration = eroomDao.getEroomRegistration(examRoom.getId());
		// 处理ip段显示
		List<String> ipStratList = new ArrayList<String>();
		List<String> ipEndList = new ArrayList<String>();
		if (examRoom.getIpStart() != null && examRoom.getIpEnd() != null) {
			String[] ipStart = examRoom.getIpStart().split("_");
			String[] ipEnd = examRoom.getIpEnd().split("_");
			for (int i = 0; i < ipStart.length; i++) {
				ipStratList.add(ipStart[i]);
			}
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndList.add(ipEnd[i]);
			}
			getRequest().setAttribute("ipStratList", ipStratList);
			getRequest().setAttribute("ipEndList", ipEndList);
		}
		if(examRoom.getClassid()>0){
			elclass = classDao.getClassById(examRoom.getClassid());
		}
		return "erwithout_alter";
	}
	
	public String questionnaire_alterInit() throws ElException {
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
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		//初始化默认警种（创建者警种）
		erRegistration = eroomDao.getEroomRegistration(examRoom.getId());
		// 处理ip段显示
		List<String> ipStratList = new ArrayList<String>();
		List<String> ipEndList = new ArrayList<String>();
		if (examRoom.getIpStart() != null && examRoom.getIpEnd() != null) {
			String[] ipStart = examRoom.getIpStart().split("_");
			String[] ipEnd = examRoom.getIpEnd().split("_");
			for (int i = 0; i < ipStart.length; i++) {
				ipStratList.add(ipStart[i]);
			}
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndList.add(ipEnd[i]);
			}
			getRequest().setAttribute("ipStratList", ipStratList);
			getRequest().setAttribute("ipEndList", ipEndList);
		}
		if(examRoom.getClassid()>0){
			elclass = classDao.getClassById(examRoom.getClassid());
		}
		return "questionnaire_alterInit";
	}

	public String erwithout_prima_alterInit() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		// if (examRoom.getValid() == 1) {
		// setElmessage("场次已开通不能再修改");
		// return "error";
		// }
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
		examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
				.getId()));
		examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
		examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		return "erwithout_prima_alter";
	}

	/**
	 * 删除考场
	 * @return
	 * @throws ElException
	 */
	public String erwithout_delete()throws ElException{
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		eroomDao.deleteExamRoom(examRoom.getId()); //删除考场
		eroomDao.deleteEroomepByErid(examRoom.getId());//删除考场--试卷对应表中的数据
		eroomDao.deleteErEpUsersByErid(examRoom.getId());//删除试卷分配表中的数据
		eroomDao.deleteErUserByErid(examRoom.getId());//删除考场分配表中的数据
		return "erwithout_delete";
	}
	
	public String erwithout_alter() throws ElException {
		// course = eroomDao.getCourseById(course.getId());
		// examRoom.setCourse(course);
		// if (examRoom.getValid() == 1) {
		// setElmessage("场次已开通不能再修改");
		// return "error";
		// }
		
		if (examRoom.getValid() == 2) {
			examRoom.setValid(0);
		}
		// 处理ip段
		String[] ipStart = getRequest().getParameterValues("ipStart");
		String[] ipEnd = getRequest().getParameterValues("ipEnd");
		if (ipStart != null && ipEnd != null) {
			StringBuffer ipStartbu = new StringBuffer("");
			StringBuffer ipEndbu = new StringBuffer("");
			for (int i = 0; i < ipStart.length; i++) {
				ipStartbu.append(ipStart[i] + "_");
			}
			// 去掉最后一个下划线
			ipStartbu.deleteCharAt(ipStartbu.length() - 1);
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndbu.append(ipEnd[i] + "_");
			}
			// 去掉最后一个下划线
			ipEndbu.deleteCharAt(ipEndbu.length() - 1);
			// 赋值存到数据库
			examRoom.setIpStart(ipStartbu.toString());
			examRoom.setIpEnd(ipEndbu.toString());
		}
		// if(examRoom.getErtype()==0){
		// examRoom.setExamcount(1);
		// }
		eroomDao.alterExamRoom(examRoom);
		if (examRoom.getIsApplication() == 1 && erRegistration != null) {// 是否为可申请
			erRegistration.setEroom(examRoom);

//			EroomRegistration erReg = eroomDao.getEroomRegistration(examRoom
//					.getId());
			erRegistration.setEroom(examRoom);
			if (examRooms != null) {
				erRegistration.setExamRoom(examRooms);
			}
//			else {
//				if (isEroomName == 0) {
//				} else {
//					erRegistration.setExamRoom(erReg.getExamRoom());
//				}
//			}
			if (elClasss != null) {
				erRegistration.setElclass(elClasss);
			}
//			else {
//				if (isclassName == 0) {
//				} else {
//					erRegistration.setElclass(erReg.getElclass());
//				}
//			}
			if (!eroomDao.checkElclassRegistration(examRoom.getId())) {
				eroomDao.addEroomRegistration(erRegistration);
			} else {
				eroomDao.alterEroomRegistration(erRegistration);
			}
		}
		if (null != examRoom.getInvigilators()) {
			for (int i = 0; i < examRoom.getInvigilators().size(); i++) {
				if (!eroomDao.checkEroomUsers("rinvigilators", examRoom
						.getInvigilators().get(i).getId(), examRoom.getId()))
					eroomDao
							.addEroomusers("rinvigilators", examRoom
									.getInvigilators().get(i).getId(), examRoom
									.getId());
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"myExamroom_list", 0);
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getAppraises()) {
			for (int i = 0; i < examRoom.getAppraises().size(); i++) {				
//				if (examRoom.getAppraises().get(i).getId()!=examRoom.getAppr_header().getId()){
//					eroomDao.UpdateEroomusers("rappraises", examRoom.getAppraises().get(i).getId(), examRoom.getId(), 0);//当前没有选中的阅卷组长的状态置为0
//						
//				}
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
//				roleDao.setUserfunc(examRoom.getAppraises().get(i).getId(),
//						"examroomwithoutcourse_readlist", 0);
//				roleDao.setUserfunc(examRoom.getAppraises().get(i).getId(),
//						"admin", 0);
			}
//			if(examRoom.getAppr_header()!=null)
//				eroomDao.UpdateEroomusers("rappraises", examRoom.getAppr_header().getId(), examRoom.getId(), 1);//当前选中的阅卷组长的状态置为1
			
		}
		if (null != examRoom.getValids()) {
			for (int i = 0; i < examRoom.getValids().size(); i++) {
				if (!eroomDao.checkEroomUsers("valids", examRoom.getValids()
						.get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("valids", examRoom.getValids()
							.get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"examroom_validlist", 0);
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examPapers) {
			int sortid=0;
			for (int i = 0; i < examPapers.size(); i++) {
				ExamPaper ep = examPapers.get(i);
				if (ep != null && ep.getId() != 0) {
					// 因为注释掉练习
					// ep.setPrac(new ExamPaper(0));
					if (!eroomDao.checkEroomeps(examRoom.getId(), examPapers
							.get(i).getId())) {
						// eroomDao.addEroomeps(examRoom.getId(), examPapers
						// .get(i).getId(), ep.getPrac().getId(), ep
						// .getPractimes(), ep.getPracscore(), ep
						// .getPassgrade(), ep.getStuview());
						sortid++;
						eroomDao.addEroomeps(examRoom.getId(), examPapers
								.get(i).getId(), 0, ep.getPractimes(), ep
								.getPracscore(), ep.getPassgrade(), ep
								.getStuview(), ep.getQuizlook(), ep
								.getScorelook(), ep.getQuizcount(), ep
								.getPassmanner(),sortid);
					} else {
						// eroomDao.alterEroomeps(examRoom.getId(), examPapers
						// .get(i).getId(), ep.getPrac().getId(), ep
						// .getPractimes(), ep.getPracscore(), ep
						// .getPassgrade(), ep.getStuview());
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
		// return "examroomwithoutcourse_list";

		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_ALTER, examRoom.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		if (Return != null && Return.equals("error")) {
			setElmessage("场次修改成功");
			return "error";
		}
		if(examRoom!=null&&examRoom.getClassid()!=0&&examRoom.getCourse()!=null&&examRoom.getCourse().getId()>0)
			return "examroom_choose_listbycInit";
		return "examroom_view";
//		return "examroom_alllist";// 返回到考场列表页
		// return "examroom_mylist";// 返回到我创建的考场列表页
	}
	
	
	public String questionnaire_alter() throws ElException {
		
		if (examRoom.getValid() == 2) {
			examRoom.setValid(0);
		}
		// 处理ip段
		String[] ipStart = getRequest().getParameterValues("ipStart");
		String[] ipEnd = getRequest().getParameterValues("ipEnd");
		if (ipStart != null && ipEnd != null) {
			StringBuffer ipStartbu = new StringBuffer("");
			StringBuffer ipEndbu = new StringBuffer("");
			for (int i = 0; i < ipStart.length; i++) {
				ipStartbu.append(ipStart[i] + "_");
			}
			// 去掉最后一个下划线
			ipStartbu.deleteCharAt(ipStartbu.length() - 1);
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndbu.append(ipEnd[i] + "_");
			}
			// 去掉最后一个下划线
			ipEndbu.deleteCharAt(ipEndbu.length() - 1);
			// 赋值存到数据库
			examRoom.setIpStart(ipStartbu.toString());
			examRoom.setIpEnd(ipEndbu.toString());
		}
		eroomDao.alterExamRoom(examRoom);
		if (examRoom.getIsApplication() == 1 && erRegistration != null) {// 是否为可申请
			erRegistration.setEroom(examRoom);
			erRegistration.setEroom(examRoom);
			if (examRooms != null) {
				erRegistration.setExamRoom(examRooms);
			}
			if (elClasss != null) {
				erRegistration.setElclass(elClasss);
			}
			if (!eroomDao.checkElclassRegistration(examRoom.getId())) {
				eroomDao.addEroomRegistration(erRegistration);
			} else {
				eroomDao.alterEroomRegistration(erRegistration);
			}
		}
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
					// ep.setPrac(new ExamPaper(0));
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
		// return "examroomwithoutcourse_list";

		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_ALTER, examRoom.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		if (Return != null && Return.equals("error")) {
			setElmessage("场次修改成功");
			return "error";
		}
		if(examRoom!=null&&examRoom.getClassid()!=0&&examRoom.getCourse()!=null&&examRoom.getCourse().getId()>0)
			return "examroom_choose_listbycInit";
		return "questionnaire_view";
//		return "examroom_alllist";// 返回到考场列表页
		// return "examroom_mylist";// 返回到我创建的考场列表页
	}
	
	public String eroomEpsort() throws ElException {
		eroomDao.updateEroomEpSortid(examRoom.getId(),examPaper.getId(), examPaper.getSortid(), manner);
		return "erwithout_alterInit";
	}

	public String erwithout_prima_alter() throws ElException {
		// course = eroomDao.getCourseById(course.getId());
		// examRoom.setCourse(course);
		// if (examRoom.getValid() == 1) {
		// setElmessage("场次已开通不能再修改");
		// return "error";
		// }
		if (examRoom.getValid() == 2) {
			examRoom.setValid(0);
		}
		eroomDao.alterExamRoom(examRoom);
		if (null != examRoom.getInvigilators()) {
			for (int i = 0; i < examRoom.getInvigilators().size(); i++) {
				if (!eroomDao.checkEroomUsers("rinvigilators", examRoom
						.getInvigilators().get(i).getId(), examRoom.getId()))
					eroomDao
							.addEroomusers("rinvigilators", examRoom
									.getInvigilators().get(i).getId(), examRoom
									.getId());
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"myExamroom_list", 0);
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getAppraises()) {
			for (int i = 0; i < examRoom.getAppraises().size(); i++) {
				if (!eroomDao.checkEroomUsers("rappraises", examRoom
						.getAppraises().get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("rappraises", examRoom
							.getAppraises().get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getAppraises().get(i).getId(),
//						"examroomwithoutcourse_readlist", 0);
//				roleDao.setUserfunc(examRoom.getAppraises().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getValids()) {
			for (int i = 0; i < examRoom.getValids().size(); i++) {
				if (!eroomDao.checkEroomUsers("valids", examRoom.getValids()
						.get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("valids", examRoom.getValids()
							.get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"examroom_validlist", 0);
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examPapers) {
			for (int i = 0; i < examPapers.size(); i++) {
				ExamPaper ep = examPapers.get(i);
				if (ep != null && ep.getId() != 0) {
					if (!eroomDao.checkEroomeps(examRoom.getId(), examPapers
							.get(i).getId()))
						eroomDao.addEroomeps(examRoom.getId(), examPapers
								.get(i).getId(), ep.getPrac().getId(), ep
								.getPractimes(), ep.getPracscore(), ep
								.getPassgrade(), ep.getStuview());
					else
						eroomDao.alterEroomeps(examRoom.getId(), examPapers
								.get(i).getId(), ep.getPrac().getId(), ep
								.getPractimes(), ep.getPracscore(), ep
								.getPassgrade(), ep.getStuview());
				}
			}
		}
		examRoom.setId(examRoom.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_ALTER, examRoom.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		return "erwithout_prima_alter";
	}

	public String erwithout_view() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
		examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
				.getId()));
		examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
		examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
		// 查出考场所有的选拔人员
		examRoom.setSelectings(eroomDao.getEroomUsers("selectings", examRoom
				.getId()));
		myrooms = eroomDao.listEroomtesters(examRoom.getId(), getPageNow(),
				getPageSize());
		count = eroomDao.listEroomtesterssize(examRoom.getId());
		erRegistration = eroomDao.getEroomRegistration(examRoom.getId());
		if(erRegistration!=null){
			erRegistration.toErParams();
			erRegistration.toErepParams();
			erRegistration.toClassParams();
		}
		// 处理ip段显示
		List<String> ipStratList = new ArrayList<String>();
		List<String> ipEndList = new ArrayList<String>();
		if (examRoom.getIpStart() != null && examRoom.getIpEnd() != null) {
			String[] ipStart = examRoom.getIpStart().split("_");
			String[] ipEnd = examRoom.getIpEnd().split("_");
			for (int i = 0; i < ipStart.length; i++) {
				ipStratList.add(ipStart[i]);
			}
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndList.add(ipEnd[i]);
			}
			getRequest().setAttribute("ipStratList", ipStratList);
			getRequest().setAttribute("ipEndList", ipEndList);
		}
		//是否具有提交审核权限
		examRoom.setPass0_1(roleDao.checkRolefunc(getSessionIntValue(ElConstants.SESSION_ROLE),"examroom_prima_shlist")?1:0);
		//是否具有核准权限
		examRoom.setPass1_2(roleDao.checkRolefunc(getSessionIntValue(ElConstants.SESSION_ROLE),"examroom_shlist")?1:0);
		return "erwithout_view";
	}
	
	
	public String questionnaire_view() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
		examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
				.getId()));
		examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
		examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
		// 查出考场所有的选拔人员
		examRoom.setSelectings(eroomDao.getEroomUsers("selectings", examRoom
				.getId()));
		myrooms = eroomDao.listEroomtesters(examRoom.getId(), getPageNow(),
				getPageSize());
		count = eroomDao.listEroomtesterssize(examRoom.getId());
		erRegistration = eroomDao.getEroomRegistration(examRoom.getId());
		if(erRegistration!=null){
			erRegistration.toErParams();
			erRegistration.toErepParams();
			erRegistration.toClassParams();
		}
		// 处理ip段显示
		List<String> ipStratList = new ArrayList<String>();
		List<String> ipEndList = new ArrayList<String>();
		if (examRoom.getIpStart() != null && examRoom.getIpEnd() != null) {
			String[] ipStart = examRoom.getIpStart().split("_");
			String[] ipEnd = examRoom.getIpEnd().split("_");
			for (int i = 0; i < ipStart.length; i++) {
				ipStratList.add(ipStart[i]);
			}
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndList.add(ipEnd[i]);
			}
			getRequest().setAttribute("ipStratList", ipStratList);
			getRequest().setAttribute("ipEndList", ipEndList);
		}
		//是否具有提交审核权限
		examRoom.setPass0_1(roleDao.checkRolefunc(getSessionIntValue(ElConstants.SESSION_ROLE),"examroom_prima_shlist")?1:0);
		//是否具有核准权限
		examRoom.setPass1_2(roleDao.checkRolefunc(getSessionIntValue(ElConstants.SESSION_ROLE),"examroom_shlist")?1:0);
		return "questionnaire_view";
	}

	public String eroom_delete_user() throws ElException {
		eroomDao.deleteEroomusers(optype, elUser.getId(), examRoom.getId());
		roleDao.checkUserfunc(elUser.getId(), "myExamroom_list",
				"exam_rinvigilators");
		roleDao.checkUserfunc(elUser.getId(), "examroomwithoutcourse_readlist",
				"exam_rappraises");
		roleDao.checkUserfunc(elUser.getId(), "examroom_validlist",
				"exam_valids");
		roleDao.checkUserfunc(elUser.getId(), "admin", "exam_rinvigilators");

		return null;
	}

	public String eroom_delete_ep() throws ElException {
		eroomDao.udpateEroomepStatus(examRoom.getId(), examPaper.getId(),1);
		eroomDao.udpateStudyepStatus(examRoom.getId(), examPaper.getId(),1);// yg设置考场及试卷状态
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_DELETE, examRoom.getTitle()
						+ " 考场删除了试卷 " + examPaper.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		return null;
	}

	/**
	 * 恢复考场试卷的删除
	 * 
	 * @return
	 * @throws ElException
	 */
	public String eroom_huifu_ep() throws ElException {
		eroomDao.udpateEroomepStatus(examRoom.getId(), examPaper.getId(), 0);
		eroomDao.udpateStudyepStatus(examRoom.getId(), examPaper.getId(),0);
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_ALTER, examRoom.getTitle()
						+ " 考场恢复了试卷 " + examPaper.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		return null;
	}

	public String eroom_delete_eplx() throws ElException {
		eroomDao.deleteEroomepsLx(examRoom.getId(), examPaper.getId());
		return null;
	}

	public String examroom_validlist() throws ElException {
		// examRooms = eroomDao.listExamRoomValid(
		// getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"
		// and er.valid != 9", getPageNow(),
		// getPageSize());
		// count = eroomDao
		// .listExamRoomValidsize(getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"
		// and er.valid != 9");
		examRooms = eroomDao.listExamRoomValid(
				getSessionIntValue(ElConstants.SESSION_USERID),
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.valid != 9", examRoom, getPageNow(), getPageSize());
		count = eroomDao.listExamRoomValidsize(
				getSessionIntValue(ElConstants.SESSION_USERID),
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.valid != 9", examRoom);
		return "examroom_validlist";
	}

	public String examroom_selectinglist() throws ElException {
		// examRooms = eroomDao.listExamRoomSelectings(
		// getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"
		// and er.valid != 9 and er.type = 1", getPageNow(),
		// getPageSize());
		// count = eroomDao
		// .listExamRoomSelectingsSize(getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),"
		// and er.valid != 9 and er.type = 1");
		examRooms = eroomDao.listExamRoomSelectings(
				getSessionIntValue(ElConstants.SESSION_USERID),
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.valid != 9 and er.type = 1", examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.listExamRoomSelectingsSize(
				getSessionIntValue(ElConstants.SESSION_USERID),
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.valid != 9 and er.type = 1", examRoom);
		return "examroom_selectinglist";
	}

	public String examroom_validview() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
		examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
				.getId()));
		examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
		examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
		// 查出考场所有的选拔人员
		examRoom.setSelectings(eroomDao.getEroomUsers("selectings", examRoom
				.getId()));
		myrooms = eroomDao.listEroomtesters(examRoom.getId(), getPageNow(),
				getPageSize());
		count = eroomDao.listEroomtesterssize(examRoom.getId());
		// 处理ip段显示
		List<String> ipStratList = new ArrayList<String>();
		List<String> ipEndList = new ArrayList<String>();
		if (examRoom.getIpStart() != null && examRoom.getIpEnd() != null) {
			String[] ipStart = examRoom.getIpStart().split("_");
			String[] ipEnd = examRoom.getIpEnd().split("_");
			for (int i = 0; i < ipStart.length; i++) {
				ipStratList.add(ipStart[i]);
			}
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndList.add(ipEnd[i]);
			}
			getRequest().setAttribute("ipStratList", ipStratList);
			getRequest().setAttribute("ipEndList", ipEndList);
		}
		return "examroom_validview";
	}

	public String examroom_valid() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		// if (examRoom.getValid() == 1) {//状态判断错误了吧
		if (examRoom.getValid() == 5) {
			setElmessage("考场已经开通了！");
			return "error";
		}
		eroomDao.examRoomUvalid(examRoom.getId(), 1);

		// if(eroomDao.checkEroomIsUsers("valids", examRoom.getId())){//如果有复核人员,
		// 在复核时 连带一起申请审核
		if (examRoom.getType() == 1 && examRoom.getSvalid() == 0)
			eroomDao.examRoomS(examRoom.getId(), 1);// 选拨练习流程
//			eroomDao.examRoomS(examRoom.getId(), 5);// 选拨练习流程（省去审核）
		else
			eroomDao.examRoomSh(examRoom.getId(), 1);// 正常考试流程
//			eroomDao.examRoomSh(examRoom.getId(),5);// 正常考试流程（省去审核）
//			eroomDao.examRoomisNormal(examRoom.getId(),1);//开通（省去审核）
		// }

		// return "examroom_validview";
		if (Return != null && Return.equals("elclass_examroom_validlist")) {
			return Return;
		} else if (Return != null
				&& Return.equals("examroomwithoutcourse_list")) {
			return Return;
		} else if (Return != null
				&& Return.equals("examroom_alllist")) {
			return Return;
		}else if(Return !=null && Return.equals("questionnaireList")){
			return Return;
		}else{
			return "examroom_view" ;
		}
//		return "examroom_validlist";
	}

	public String course_examroom_valid() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		if (examRoom.getValid() == 1) {
			setElmessage("考场已经开通了！");
			return "error";
		}
		eroomDao.examRoomUvalid(examRoom.getId(), 1);
		if (Return != null) {
			return Return;
		}
		return "error";
	}

	public String examroom_shlist() throws ElException {
		// int erid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		// if(sublibs != 0){
		// erid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId() <= 0 ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId();
		// }else{
		// erid = eroomLibTree.getId();
		// }
		// examRooms = eroomDao.listExamRoomSh(eroomLibTree, getPageNow(),
		// getPageSize(), erid,getSessionIntValue(ElConstants.SESSION_ROLE));
		// count = eroomDao.listExamRoomShSize(eroomLibTree,
		// erid,getSessionIntValue(ElConstants.SESSION_ROLE));
		// examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (0)", examRoom,
		// getPageNow(), getPageSize());
		// count = eroomDao.listMyDepExamRoomSize(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (0)", examRoom);
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
//		sublibs = examRoom == null ? 1 : sublibs;
		
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
		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
				sqlw, examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
				sqlw, examRoom);
		
//		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
//				" and er.valid not in (0,9)", examRoom, getPageNow(),
//				getPageSize());
//		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
//				" and er.valid not in (0,9)", examRoom);
		return "examroom_shlist";
	}
	
	public String questionnaire_shlist() throws ElException {
		// int erid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom2(eroomLib, sublibs,
				" and er.valid not in (0,9)", examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.listExamRoomSize2(eroomLib, sublibs,
				" and er.valid not in (0,9)", examRoom);
		return "questionnaire_shlist";
	}

	public String examroom_selectings_shlist() throws ElException {
		int erid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if (sublibs != 0) {
			erid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
					: eroomLib.getId() <= 0 ? eroomDao.getEroomLibRoot()
							.getId() : eroomLib.getId();
		} else {
			erid = eroomLibTree.getId();
		}
		examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, erid,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.svalid not in (0,1,2,4,6,7,8,9) and er.type = 1",
				examRoom, getPageNow(), getPageSize());
		count = eroomDao.listMyDepExamRoomSize(eroomLibTree, erid,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.svalid not in (0,1,2,4,6,7,8,9) and er.type = 1",
				examRoom);
		return "examroom_selectings_shlist";
	}

	/**
	 * 考场时间重叠列表
	 * 
	 * @return
	 */
	public String eroom_timeover_list() throws ElException {
		examRoom = eroomDao.getExamRoomByid2(examRoom.getId());
		examRooms = eroomDao.getExamRoomTimeoverList(examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.getExamRoomTimeoverListCount(examRoom);
		return "eroom_timeover_list";
	}
	
	public String questionnaire_timeover_list() throws ElException {
		examRoom = eroomDao.getExamRoomByid2(examRoom.getId());
		examRooms = eroomDao.getExamRoomTimeoverList(examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.getExamRoomTimeoverListCount(examRoom);
		return "questionnaire_timeover_list";
	}

	public String examroom_prima_shlist() throws ElException {
		// int erid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		// if(sublibs !=0){
		// erid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId() <= 0 ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId();
		// }else{
		// erid=eroomLibTree.getId();
		// }
		// examRooms = eroomDao.listMyDepExamRoom(eroomLibTree,
		// erid,getSessionIntValue(ElConstants.SESSION_ROLE)," and er.valid not
		// in (0,2,3,5,6,7,8,9)",
		// getPageNow(), getPageSize());
		// count = eroomDao.listMyDepExamRoomSize(eroomLibTree,
		// erid,getSessionIntValue(ElConstants.SESSION_ROLE)," and er.valid not
		// in (0,2,3,5,6,7,8,9)");
		// examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (0,2,3,5,6,7,8,9)", examRoom,
		// getPageNow(), getPageSize());
		// count = eroomDao.listMyDepExamRoomSize(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (0,2,3,5,6,7,8,9)", examRoom);
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
				" and er.valid not in (0,2,3,5,6,7,8,9)", examRoom,
				getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
				" and er.valid not in (0,2,3,5,6,7,8,9)", examRoom);
		return "examroom_prima_shlist";
	}
	
	public String questionnaire_prima_shlist() throws ElException {
		// int erid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
	//	examRoom = new ExamRoom();
	//	examRoom.setClassid(-2);
		examRooms = eroomDao.listExamRoom2(eroomLib, sublibs,
				" and er.valid not in (0,2,3,5,6,7,8,9)", examRoom,
				getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize2(eroomLib, sublibs,
				" and er.valid not in (0,2,3,5,6,7,8,9)", examRoom);
		return "questionnaire_prima_shlist";
	}

	public String examroom_selectings_prima_shlist() throws ElException {
		int erid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if (sublibs != 0) {
			erid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
					: eroomLib.getId() <= 0 ? eroomDao.getEroomLibRoot()
							.getId() : eroomLib.getId();
		} else {
			erid = eroomLibTree.getId();
		}
		examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, erid,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.svalid not in (0,2,3,5,6,7,8,9) and er.type = 1",
				examRoom, getPageNow(), getPageSize());
		count = eroomDao.listMyDepExamRoomSize(eroomLibTree, erid,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.svalid not in (0,2,3,5,6,7,8,9) and er.type = 1",
				examRoom);
		return "examroom_selectings_prima_shlist";
	}

	public String examroom_applyfor_revision() throws ElException {
		// int erid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		// if(sublibs != 0){
		// erid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId() <= 0 ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId();
		// }else{
		// erid=eroomLibTree.getId();
		// }
		// examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (0,1,2,4,6,7,8,9)", examRoom,
		// getPageNow(), getPageSize());
		// count = eroomDao.listMyDepExamRoomSize(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (0,1,2,4,6,7,8,9)", examRoom);
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
				" and er.valid not in (0,1,2,3,4,6,7,8,9)", examRoom,
				getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
				" and er.valid not in (0,1,2,3,4,6,7,8,9)", examRoom);
		return "examroom_applyfor_revision";
	}

	public String examroom_suspended_recovery() throws ElException {
		int erid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
				: eroomLib.getId() <= 0 ? eroomDao.getEroomLibRoot().getId()
						: eroomLib.getId();
		if (erid == 0) {
			erid = 1;
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, erid,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.valid not in (0,1,2,4,6,7,8,9)", examRoom,
				getPageNow(), getPageSize());
		count = eroomDao.listMyDepExamRoomSize(eroomLibTree, erid,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.valid not in (0,1,2,4,6,7,8,9)", examRoom);
		return "examroom_suspended_recovery";
	}

	public String examroom_application_delete() throws ElException {
		// int erid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		// if(sublibs != 0){
		// erid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId() <= 0 ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId();
		// }else{
		// erid=eroomLibTree.getId();
		// }
		// examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (9)", examRoom, getPageNow(),
		// getPageSize());
		// count = eroomDao.listMyDepExamRoomSize(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (9)", examRoom);
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
				" and er.valid not in (9)", examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
				" and er.valid not in (9)", examRoom);
		return "examroom_application_delete";
	}

	public String examroom_application_delete_shlist() throws ElException {
		// int erid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		// if(sublibs != 0){
		// erid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId() <= 0 ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId();
		// }else{
		// erid=eroomLibTree.getId();
		// }
		// examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (0,1,2,4,5,6,7,9)", examRoom,
		// getPageNow(), getPageSize());
		// count = eroomDao.listMyDepExamRoomSize(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (0,1,2,4,5,6,7,9)", examRoom);
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
				" and er.valid not in (0,1,2,3,4,5,6,7,9)", examRoom,
				getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
				" and er.valid not in (0,1,2,3,4,5,6,7,9)", examRoom);
		return "examroom_application_delete_shlist";
	}

	public String examroom_sh_np() throws ElException {
		eroomDao.examRoomSh(examRoom.getId(), 2);
		return "examroom_shlist";
	}

	public String examroom_sh_view() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
		examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
				.getId()));
		examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
		// 查出考场所有的选拔人员
		examRoom.setSelectings(eroomDao.getEroomUsers("selectings", examRoom
				.getId()));
		examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
		myrooms = eroomDao.listEroomtesters(examRoom.getId(), getPageNow(),
				getPageSize());
		count = eroomDao.listEroomtesterssize(examRoom.getId());

		// erAuditdes =
		// eroomDao.getExamRoomAuditDescribesByRoomid(examRoom.getId());
		// 处理ip段显示
		List<String> ipStratList = new ArrayList<String>();
		List<String> ipEndList = new ArrayList<String>();
		if (examRoom.getIpStart() != null && examRoom.getIpEnd() != null) {
			String[] ipStart = examRoom.getIpStart().split("_");
			String[] ipEnd = examRoom.getIpEnd().split("_");
			for (int i = 0; i < ipStart.length; i++) {
				ipStratList.add(ipStart[i]);
			}
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndList.add(ipEnd[i]);
			}
			getRequest().setAttribute("ipStratList", ipStratList);
			getRequest().setAttribute("ipEndList", ipEndList);
		}
		if (examRoom.getIsApplication() == 1) {
			erRegistration = eroomDao.getEroomRegistration(examRoom.getId());
		}
		return "examroom_sh_view";
	}

	public String examroom_sh_s() throws ElException {
		int svalid = examRoom.getSvalid();

		if (svalid == 2 || svalid == 0) {// 当初审不通过和退回修改（0）时 需要清空复核状态
			eroomDao.examRoomUvalid(examRoom.getId(), 0);// 复核清空
		}
		eroomDao.examRoomS(examRoom.getId(), svalid);
		return Return;
	}

	public String examroom_sh_p() throws ElException {
		int valid = examRoom.getValid();
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		if (fushenValid != null && fushenValid.equals("true")) {
			eroomDao.examRoomUvalid(examRoom.getId(), 1);// 复核初审一起通过
		}
		if (valid == 1) {
			// 申请初审
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID, examRoom.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		} else if (valid == 2) {
			// 初审不通过
			new MessageDaoImpl().insertMessInApply(examRoom.getTitle(),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID,
					getSessionIntValue(ElConstants.SESSION_USERID), 2);
		} else if (valid == 3) {
			// 初审通过
			new MessageDaoImpl().insertMessInApply(examRoom.getTitle(),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID,
					getSessionIntValue(ElConstants.SESSION_USERID), 1);
			// 也相当于申请终审
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID2, examRoom.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		} else if (valid == 4) {
			// 终审不通过
			new MessageDaoImpl().insertMessInApply(examRoom.getTitle(),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID2,
					getSessionIntValue(ElConstants.SESSION_USERID), 4);
		} else if (valid == 5) {
			// 终审通过
			new MessageDaoImpl().insertMessInApply(examRoom.getTitle(),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID2,
					getSessionIntValue(ElConstants.SESSION_USERID), 3);
			examPapers = eroomDao.getEroomeps(examRoom.getId());
			if (examPapers != null) {// 开通考场是给考场学员发送短信息。
				for (ExamPaper eps : examPapers) {// 一个考场多张试卷
					elusers = studyQuizDao.geteRoomUserByUid(examRoom.getId(),
							eps.getId(), examRoom.getClassid());
					new MessageDaoImpl().insertMessInUser(examRoom.getTitle(),
							ElLoggerConstants.LOG_MOD_EROOM,
							getSessionIntValue(ElConstants.SESSION_USERID),
							elusers, examRoom.getBegintime(), examRoom
									.getEndtime(), examRoom.getId());
				}
			}
		} else if (valid == 6) {// 申请修改业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_APPUPDATE, examRoom.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		} else if (valid == 0 && Return.equals("examroom_alter_list")) { // 申请修改通过业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_APPUPDATE, examRoom.getTitle()
							+ " 申请修改通过", ElLoggerConstants.LOG_RES_SUCC,
					examRoom.getId());
		} else if (valid != 0 && Return.equals("examroom_alter_list")) { // 申请修改不通过业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_APPUPDATE, examRoom.getTitle()
							+ " 申请修改不通过", ElLoggerConstants.LOG_RES_SUCC,
					examRoom.getId());
		} else if (valid == 8) { // 申请删除业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_UNDELETE, examRoom.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		} else if (valid == 9
				&& Return.equals("examroom_application_delete_shlist")) { // 申请删除通过业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_UNDELETE, examRoom.getTitle()
							+ "申请删除通过", ElLoggerConstants.LOG_RES_SUCC,
					examRoom.getId());
		} else if (valid != 9
				&& Return.equals("examroom_application_delete_shlist")) { // 申请删除不通过业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_UNDELETE, examRoom.getTitle()
							+ "申请删除不通过", ElLoggerConstants.LOG_RES_SUCC,
					examRoom.getId());
		} else if (valid == 11) { // 申请暂停业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_APPUPDSUSPENDED, examRoom
							.getTitle()
							+ "申请暂停", ElLoggerConstants.LOG_RES_SUCC, examRoom
							.getId());
		}

		if (valid != 8 && valid != 9 && fushenValid == null
				&& huanyuanVlaid == null) {// 人员复查未完成,可以申请删除，可以删除
			if (examRoom.getUvalid() != 1) {// 人员复查未完成,可以申请删除，可以删除
				setElmessage("人员复查未完成！");
				return "error";
			}
		}
		if (valid == 2 || valid == 0) {// 当初审不通过和退回修改（0）时 需要清空复核状态
			eroomDao.examRoomUvalid(examRoom.getId(), 0);// 复核清空
		}
		if (valid == 5) {// 
			eroomDao.examRoomisNormal(examRoom.getId(), 1);
		}
		// 当申请修改时过与申请删除时， 用avalid字段保存原来的状态，如果审核未通过， 返回avalid状态还原给valid
		if (alterValid != null && alterValid.equals("true")) { // 修改通过
			eroomDao.examRoomavalid(examRoom.getId(), examRoom.getValid());
		}
		if (deleteValid != null && deleteValid.equals("true")) {
			eroomDao.examRoomavalid(examRoom.getId(), examRoom.getValid());
		}
		eroomDao.examRoomSh(examRoom.getId(), valid);
		if(Return==null||"".equals(Return))
			return "examroom_view" ;
		return Return;// 返回终审集合页面
	}
	
	
	public String questionnaire_sh_p() throws ElException {
		int valid = examRoom.getValid();
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		if (fushenValid != null && fushenValid.equals("true")) {
			eroomDao.examRoomUvalid(examRoom.getId(), 1);// 复核初审一起通过
		}
		if (valid == 1) {
			// 申请初审
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID, examRoom.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		} else if (valid == 2) {
			// 初审不通过
			new MessageDaoImpl().insertMessInApply(examRoom.getTitle(),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID,
					getSessionIntValue(ElConstants.SESSION_USERID), 2);
		} else if (valid == 3) {
			// 初审通过
			new MessageDaoImpl().insertMessInApply(examRoom.getTitle(),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID,
					getSessionIntValue(ElConstants.SESSION_USERID), 1);
			// 也相当于申请终审
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID2, examRoom.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		} else if (valid == 4) {
			// 终审不通过
			new MessageDaoImpl().insertMessInApply(examRoom.getTitle(),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID2,
					getSessionIntValue(ElConstants.SESSION_USERID), 4);
		} else if (valid == 5) {
			// 终审通过
			new MessageDaoImpl().insertMessInApply(examRoom.getTitle(),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_VALID2,
					getSessionIntValue(ElConstants.SESSION_USERID), 3);
			examPapers = eroomDao.getEroomeps(examRoom.getId());
			if (examPapers != null) {// 开通考场是给考场学员发送短信息。
				for (ExamPaper eps : examPapers) {// 一个考场多张试卷
					elusers = studyQuizDao.geteRoomUserByUid(examRoom.getId(),
							eps.getId(), examRoom.getClassid());
					new MessageDaoImpl().insertMessInUser(examRoom.getTitle(),
							ElLoggerConstants.LOG_MOD_EROOM,
							getSessionIntValue(ElConstants.SESSION_USERID),
							elusers, examRoom.getBegintime(), examRoom
									.getEndtime(), examRoom.getId());
				}
			}
		} else if (valid == 6) {// 申请修改业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_APPUPDATE, examRoom.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		} else if (valid == 0 && Return.equals("examroom_alter_list")) { // 申请修改通过业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_APPUPDATE, examRoom.getTitle()
							+ " 申请修改通过", ElLoggerConstants.LOG_RES_SUCC,
					examRoom.getId());
		} else if (valid != 0 && Return.equals("examroom_alter_list")) { // 申请修改不通过业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_APPUPDATE, examRoom.getTitle()
							+ " 申请修改不通过", ElLoggerConstants.LOG_RES_SUCC,
					examRoom.getId());
		} else if (valid == 8) { // 申请删除业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_UNDELETE, examRoom.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		} else if (valid == 9
				&& Return.equals("examroom_application_delete_shlist")) { // 申请删除通过业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_UNDELETE, examRoom.getTitle()
							+ "申请删除通过", ElLoggerConstants.LOG_RES_SUCC,
					examRoom.getId());
		} else if (valid != 9
				&& Return.equals("examroom_application_delete_shlist")) { // 申请删除不通过业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_UNDELETE, examRoom.getTitle()
							+ "申请删除不通过", ElLoggerConstants.LOG_RES_SUCC,
					examRoom.getId());
		} else if (valid == 11) { // 申请暂停业务日志
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_APPUPDSUSPENDED, examRoom
							.getTitle()
							+ "申请暂停", ElLoggerConstants.LOG_RES_SUCC, examRoom
							.getId());
		}

		if (valid != 8 && valid != 9 && fushenValid == null
				&& huanyuanVlaid == null) {// 人员复查未完成,可以申请删除，可以删除
			if (examRoom.getUvalid() != 1) {// 人员复查未完成,可以申请删除，可以删除
				setElmessage("人员复查未完成！");
				return "error";
			}
		}
		if (valid == 2 || valid == 0) {// 当初审不通过和退回修改（0）时 需要清空复核状态
			eroomDao.examRoomUvalid(examRoom.getId(), 0);// 复核清空
		}
		if (valid == 5) {// 
			eroomDao.examRoomisNormal(examRoom.getId(), 1);
		}
		// 当申请修改时过与申请删除时， 用avalid字段保存原来的状态，如果审核未通过， 返回avalid状态还原给valid
		if (alterValid != null && alterValid.equals("true")) { // 修改通过
			eroomDao.examRoomavalid(examRoom.getId(), examRoom.getValid());
		}
		if (deleteValid != null && deleteValid.equals("true")) {
			eroomDao.examRoomavalid(examRoom.getId(), examRoom.getValid());
		}
		eroomDao.examRoomSh(examRoom.getId(), valid);
		if(Return==null||"".equals(Return))
			return "questionnaire_view" ;
		return Return;// 返回终审集合页面
	}

	public String examroom_validuserlist() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());
		if (examPapers == null || examPapers.size() == 0) {
			setElmessage("对不起该考场中没有试卷，也没有考生！请与管理员联系！");
			return "error";
		}
		// if (examPapers.size() == 1) {
		// examPaper = examPapers.get(0);
		// return "examroom_assignuserlist";
		// }
		examRoom.setExampapers(examPapers);
		return "examroom_assign";
	}

	public String examroom_selectingslist() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());
		if (examPapers == null || examPapers.size() == 0) {
			setElmessage("对不起该考场中没有试卷，也没有考生！请与管理员联系！");
			return "error";
		}
		if (examPapers.size() == 1) {
			examPaper = examPapers.get(0);
			return "examroom_selectings";
		}
		examRoom.setExampapers(examPapers);
		return "examroom_selectassign";
	}

	public String examroom_selectings() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());// hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
		elusers = eroomDao.listroom2userbyurid(examPaper.getId(), examRoom
				.getId());
		ep = eroomDao.getEroomeps(examRoom.getId(), examPaper.getId());
		examPaper.setPracscore(ep.getPracscore());
		examPaper.setPractimes(ep.getPractimes());
		return "examroom_selectings";
	}

	// public String examroom_selectings() throws ElException {
	// examRoom = eroomDao.getExamRoomByid(examRoom.getId());
	// examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
	// if (examPapers == null || examPapers.size() == 0) {
	// setElmessage("对不起该考场中没有试卷，也没有考生！请与管理员联系！");
	// return "error";
	// }
	// if (examPapers.size() == 1) {
	// examPaper = examPapers.get(0);
	// return "examroom_selectings";
	// }
	// examRoom.setExampapers(examPapers);
	// return "examroom_assign";
	// }

	public String examroom_alter_list() throws ElException {
		// int erid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId() <= 0 ? eroomDao.getEroomLibRoot().getId()
		// : eroomLib.getId();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		// examRooms = eroomDao.listMyDepExamRoomAvalid(eroomLibTree,
		// erid,getSessionIntValue(ElConstants.SESSION_ROLE),
		// getPageNow(), getPageSize());
		// count = eroomDao.listMyDepExamRoomSizeAvalid(eroomLibTree,
		// erid,getSessionIntValue(ElConstants.SESSION_ROLE));
		// examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (0,1,2,4,5,7,8,9)", examRoom,
		// getPageNow(), getPageSize());
		// count = eroomDao.listMyDepExamRoomSize(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// " and er.valid not in (0,1,2,4,5,7,8,9)", examRoom);
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
				" and er.valid not in (0,1,2,4,5,7,8,9)", examRoom,
				getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
				" and er.valid not in (0,1,2,4,5,7,8,9)", examRoom);
		return "examroom_alter_list";
	}

	public String examroom_sh_alter_Init() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
		examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
				.getId()));
		examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
		examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
		myrooms = eroomDao.listEroomtesters(examRoom.getId(), getPageNow(),
				getPageSize());
		count = eroomDao.listEroomtesterssize(examRoom.getId());
		// erAuditdes =
		// eroomDao.getExamRoomAuditDescribesByRoomid(examRoom.getId());
		return "examroom_sh_alter_Init";
	}

	public String examroom_sh_alter() throws ElException {
		eroomDao.examRoomavalid(examRoom.getId(), examRoom.getAvalid());// 申请修改id
		eroomDao.examRoomSh(examRoom.getId(), examRoom.getAvalid());// 审核id
		erAuditde = eroomDao
				.getExamRoomAuditDescribesByRoomid(examRoom.getId());
		erAuditde.setReplycontent(erAuditdes.getReplycontent());
		erAuditdes = eroomDao.getExamRoomAuditDescribesByRoomid(examRoom
				.getId());
		erAuditde.setContent(erAuditdes.getContent());
		eroomDao.UExamRoomAuditContents(erAuditde);
		return "examroom_sh_alter";
	}

	/**
	 * 监考
	 * 
	 * @return
	 * @throws ElException
	 */
	public String myExamroom_man() throws ElException {
		// examRooms =
		// eroomDao.listMyExamroom((Integer)getSession().getAttribute("userId"));
		// 查出考场信息
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		if (myroom == null) {
			myroom = new MyRoom();
			myroom.setStatus(-2);
		}
		Timestamp begintime=myroom==null?null:myroom.getBegintime();
		Timestamp endtime=myroom==null?null:myroom.getEndtime();
		// 查出该考场中的所有学员信息
		// myrooms = eroomDao.listEroomjks(examRoom.getId(), getPageNow(),
		// getPageSize());
		myrooms = eroomDao.listEroomjks(examRoom.getId(), elUser, myroom
				.getStatus(), getPageNow(), getPageSize(),begintime,endtime);
		// count = eroomDao.listEroomjksize(examRoom.getId());
		count = eroomDao.listEroomjksize(examRoom.getId(), elUser, myroom
				.getStatus());
		if (null != myrooms) {
			for (int i = 0; i < myrooms.size(); i++) {
				myrooms.get(i).setMyExamPapers(
						courseDao.listMyEpsByRid(examRoom.getId(), myrooms.get(
								i).getTester().getId()));
			}
		}
		// count = courseDao.listMyEpsByRidSize(examRoom.getId());
		// 处理ip段显示
		if (examRoom.getIsIpLimit() == 1) {
			List<String> ipStratList = new ArrayList<String>();
			List<String> ipEndList = new ArrayList<String>();
			if (examRoom.getIpStart() != null && examRoom.getIpEnd() != null) {
				String[] ipStart = examRoom.getIpStart().split("_");
				String[] ipEnd = examRoom.getIpEnd().split("_");
				for (int i = 0; i < ipStart.length; i++) {
					ipStratList.add(ipStart[i]);
				}
				for (int i = 0; i < ipEnd.length; i++) {
					ipEndList.add(ipEnd[i]);
				}
			}
			getRequest().setAttribute("ipStratList", ipStratList);
			getRequest().setAttribute("ipEndList", ipEndList);
		}
		return "myExamroom_man";
	}
	/**k考场密码重置
	 * @return
	 * @throws ElException
	 */
	public String eroom_pwdalter() throws ElException {
		ExamRoom er = eroomDao.getExamRoomByid(examRoom.getId())	;
		if( er.getCacheeprefresh()==1){
//			EroomEpCache.refresh(examRoom.getId());
			eroomDao.eroom_epcacherefresh(examRoom.getId());
		}
		eroomDao.eroom_pwdalter(examRoom.getPwd(),examRoom.getPwdtime(),examRoom.getId());
		return "eroom_pwdalter";
	}

	public String delMacAddr() throws ElException {
		// 解除用户mac地址
		eroomDao.updateMacAddr(elUser.getId(), examRoom.getId());
		return "delMacAddr";
	}

	public String examroom_addtime() throws ElException {
		if (null != myExamPapers) {
			// 个人 加时
			for (int i = 0; i < myExamPapers.size(); i++) {
				eroomDao.testerAddTime(myExamPapers.get(i).getId(),
						course_sourse);
			}
		} else {// 全场加时
			// eroomDao.testersAddTime(examRoom.getId(), course_sourse);
		}
		return "myExamroom_man";
	}

	public String setTesterSuspend() throws ElException {
		if (null != myExamPapers) {
			for (int i = 0; i < myExamPapers.size(); i++) {
				int userid = myExamPapers.get(i).getId();
				courseDao.setTesterStatus(4, examRoom.getId(), userid);
			}
		}
		return "myExamroom_man";
	}

	/**重考
	 * @return
	 * @throws ElException
	 */
	public String setTesterReinstate() throws ElException {
		if (null != myExamPapers) {
			for (int i = 0; i < myExamPapers.size(); i++) {
				int id = myExamPapers.get(i).getId();
				courseDao.setTesterStatus(0, examRoom.getId(), id);
				// 清空考试次数
//				studyQuizDao.setQuizPaperExamCountO(id);
				myExamPaper = studyQuizDao.getMyEpById(id);
				//重新设置考场分数
				studyQuizDao.submitQuizPaper(myExamPaper);
				//设置答卷状态
				myExamPaper.setStatus(0);
				studyQuizDao.setQuizPaperStatus(myExamPaper);
				//重设考场等状态
				studyQuizDao.setStudyEroomStatus(examRoom.getId(), myExamPaper.getTester().getId());
			}
		}
		// 清空考试次数
		// studyQuizDao.setQuizPaperExamCount(id)
		return "myExamroom_man";
	}
	/**续考
	 * @return
	 * @throws ElException
	 */
	public String setTesterContinue() throws ElException {
		if (null != myExamPapers) {
			for (int i = 0; i < myExamPapers.size(); i++) {
				int id = myExamPapers.get(i).getId();
				myExamPaper = studyQuizDao.getMyEpById(id);
				//设置答卷状态
				myExamPaper.setStatus(0);
				studyQuizDao.setQuizPaperStatus(myExamPaper);
				//重设考场等状态
				studyQuizDao.setStudyEroomStatus(examRoom.getId(), myExamPaper.getTester().getId());
			}
		}
		// 清空考试次数
		// studyQuizDao.setQuizPaperExamCount(id)
		return "myExamroom_man";
	}
	//强制交卷
	public String setTesterSubmit() throws ElException {
		if (null != myExamPapers) {
			for (int i = 0; i < myExamPapers.size(); i++) {
				int mepId = myExamPapers.get(i).getId();
				//courseDao.setTesterStatus(2, examRoom.getId(), mepId);
				myExamPaper=studyQuizDao.getMyEpById(mepId);
				studyQuizDao.submitQuizPaper(myExamPaper);//调用存储过程设置学分
				//设置考场及试卷状态
				studyQuizDao.setStudyEroomStatus(myExamPaper.getExamRoom().getId(), myExamPaper.getTester().getId());
			}
		}
		return "myExamroom_man";
	}

	public String examprac_addinit() throws ElException {
		return "examprac_add";
	}

	public String examprac_add() throws ElException {
		examprac.setUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		eroomDao.addexamprac(examprac);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EPRAC,
				ElLoggerConstants.LOG_TYPE_ADD, examprac.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examprac.getId());// **//**//
		return "examprac_list";
	}

	public String examprac_alterinit() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		return "examprac_alter";
	}

	public String examprac_alter() throws ElException {
		eroomDao.alterexamprac(examprac);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EPRAC,
				ElLoggerConstants.LOG_TYPE_ALTER, examprac.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examprac.getId());
		return "examprac_list";
	}

	public String examprac_list() throws ElException {
		exampracs = eroomDao.listexamprac(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = eroomDao
				.listexampracsize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "examprac_list";
	}

	public String examprac_validlist() throws ElException {
		exampracs = eroomDao.listexampracvalid(getPageNow(), getPageSize());
		count = eroomDao.listexampracvalidsize();
		return "examprac_validlist";
	}

	/**
	 * 删除练习(假)
	 * 
	 * @return
	 * @throws ElException
	 */
	public String examprac_del() throws ElException {
		String pracids = getRequest().getParameter("pracids");
		if (pracids != null) {
			String[] pracArray = pracids.split(",");
			for (int i = 0; i < pracArray.length; i++) {
				eroomDao.exampracSh(Integer.parseInt(pracArray[i]), 4);// 状态4为已删除
				examprac = eroomDao.getexamprac(Integer.parseInt(pracArray[i]));
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_EPRAC,
						ElLoggerConstants.LOG_TYPE_DELETE, examprac.getTitle(),
						ElLoggerConstants.LOG_RES_SUCC, examprac.getId());
			}
		}
		return "examprac_validlist";
	}

	public String examprac_suspended_recovery() throws ElException {
		exampracs = eroomDao.listexampracvalid(getPageNow(), getPageSize());
		count = eroomDao.listexampracvalidsize();
		return "examprac_suspended_recovery";
	}

	public String examprac_validview() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		// elusers = eroomDao.listassignedepracusers(examprac.getId());
		elusers = eroomDao.listassignedepracusers(examprac.getId(),
				getPageNow(), getPageSize());
		count = eroomDao.listassignedepracusersSize(examprac.getId());
		return "examprac_validview";
	}

	public String examprac_validpass() throws ElException {
		eroomDao.exampracSh(examprac.getId(), 1);
		String pageResult = getRequest().getParameter("pageResult");
		if (pageResult != null && "2".equals(pageResult)) {
			return "examprac_sh_list";
		}
		return "examprac_validview";
	}

	public String examprac_validunpass() throws ElException {
		eroomDao.exampracSh(examprac.getId(), 2);
		String pageResult = getRequest().getParameter("pageResult");
		if (pageResult != null && "2".equals(pageResult)) {
			return "examprac_sh_list";
		}
		return "examprac_validview";
	}

	public String examprac_validsuspended() throws ElException {
		eroomDao.exampracSh(examprac.getId(), 3);
		String pageResult = getRequest().getParameter("pageResult");
		if (pageResult != null && "1".equals(pageResult)) {
			return "examprac_validlist";
		} else if (pageResult != null && "2".equals(pageResult)) {
			return "examprac_sh_list";
		}
		return "examprac_suspended_recovery";
	}

	public String examprac_validrecovery() throws ElException {
		eroomDao.exampracSh(examprac.getId(), 1);
		String pageResult = getRequest().getParameter("pageResult");
		if (pageResult != null && "1".equals(pageResult)) {
			return "examprac_validlist";
		} else if (pageResult != null && "2".equals(pageResult)) {
			return "examprac_sh_list";
		}
		return "examprac_suspended_recovery";
	}

	public String examprac_delete() throws ElException {
//		eroomDao.deleteexamprac(examprac.getId());
//		examprac = eroomDao.getexamprac(examprac.getId());
//		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//				ElLoggerConstants.LOG_MOD_EPRAC,
//				ElLoggerConstants.LOG_TYPE_DELETE, examprac.getTitle(),
//				ElLoggerConstants.LOG_RES_SUCC, examprac.getId());
		//假删除
		if(examprac != null){
			if(examprac.getId() > 0){
				eroomDao.exampracSh(examprac.getId(), 4);// 状态4为已删除
				examprac = eroomDao.getexamprac(examprac.getId());
				ElLogger.busilogger(
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_EPRAC,
						ElLoggerConstants.LOG_TYPE_DELETE, examprac.getTitle(),
						ElLoggerConstants.LOG_RES_SUCC, examprac.getId());
			}
		}
		return "examprac_list";
	}

	public String examprac_assign_list() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		// elusers = eroomDao.listassignedepracusers(examprac.getId());
		elusers = eroomDao.listassignedepracusers(examprac.getId(),
				getPageNow(), getPageSize());
		count = eroomDao.listassignedepracusersSize(examprac.getId());
		return "examprac_assign_list";
	}

	public String examprac_assign_addinit() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		return "examprac_assign_addinit";
	}

	public String examprac_assign_addlist() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "use", -1,
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
		}
		if (department == null || department.getId() <= 0) {
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		elusers = userDao.listUsers(department,station, sub_department, elUser,
				getPageNow(), getPageSize());
		count = userDao.listUsersSize(department,station, sub_department, elUser);
		roles = roleDao.listRoles();
		// elusers = userDao.getUserByDepId(department.getId(), sub_department,
		// elUser, getPageNow(), getPageSize());
		if (elusers != null) {
			for (int i = 0; i < elusers.size(); i++) {
				elusers.get(i).setIntroom(
						eroomDao.checkepracuser(examprac.getId(), elusers
								.get(i).getId()));
			}
		}
		// count = userDao.getUserByDepIdSize(department.getId(),
		// sub_department,
		// elUser);
		return "examprac_assign_addlist";
	}

	/**
	 * hdl
	 * 
	 * @return
	 * @throws ElException
	 */
	public String examprac_assign_addList() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		// elusers = userDao.getUserByDepId(department.getId(), sub_department,
		// elUser, getPageNow(), getPageSize());
		// if (elusers != null) {
		// for (int i = 0; i < elusers.size(); i++) {
		// elusers.get(i).setIntroom(
		// eroomDao.checkepracuser(examprac.getId(), elusers
		// .get(i).getId()));
		// }
		// }
		// count = userDao.getUserByDepIdSize(department.getId(),
		// sub_department,
		// elUser);
		return "examprac_assign_addlist";
	}

	public String examprac_assign_add() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		if (elusers != null) {
			for (int i = 0; i < elusers.size(); i++) {
				if (!eroomDao.checkepracuser(examprac.getId(), elusers.get(i)
						.getId()))
					eroomDao.addepracuser(examprac.getId(), elusers.get(i)
							.getId());
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
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (department == null || department.getId() <= 0) {
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		elusers = userDao.listUsers(department,station, sub_department, elUser,
				getPageNow(), getPageSize());
		count = userDao.listUsersSize(department, station,sub_department, elUser);
		roles = roleDao.listRoles();
		// elusers = userDao.getUserByDepId(department.getId(), sub_department,
		// elUser, getPageNow(), getPageSize());
		if (elusers != null) {
			for (int i = 0; i < elusers.size(); i++) {
				elusers.get(i).setIntroom(
						eroomDao.checkepracuser(examprac.getId(), elusers
								.get(i).getId()));
			}
		}
		// elusers = userDao.getUserByDepId(department.getId(), sub_department,
		// elUser, getPageNow(), getPageSize());
		// if (elusers != null) {
		// for (int i = 0; i < elusers.size(); i++) {
		// elusers.get(i).setIntroom(
		// eroomDao.checkepracuser(examprac.getId(), elusers
		// .get(i).getId()));
		// }
		// }
		// count = userDao.getUserByDepIdSize(department.getId(),
		// sub_department,
		// elUser);
		return "examprac_assign_addlist";
	}

	public String examprac_assign_adds() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		// elusers = userDao.getUserByDepId(department.getId(), sub_department,
		// elUser);
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
		}
		if (department == null || department.getId() <= 0) {
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		elusers = userDao.listUsers(department, sub_department, elUser);

		if (elusers != null) {
			for (int i = 0; i < elusers.size(); i++) {
				if (!eroomDao.checkepracuser(examprac.getId(), elusers.get(i)
						.getId())) {
					eroomDao.addepracuser(examprac.getId(), elusers.get(i)
							.getId());
					ELUser eu = userDao.getUserById(elusers.get(i).getId());
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_EPRAC,
							ElLoggerConstants.LOG_TYPE_ADD, examprac.getTitle()
									+ " 练习添加了人员 " + eu.getRealname(),
							ElLoggerConstants.LOG_RES_SUCC, examprac.getId());
				}
			}
		}
		elusers = userDao.listUsers(department,station, sub_department, elUser,
				getPageNow(), getPageSize());
		count = userDao.listUsersSize(department, station,sub_department, elUser);
		roles = roleDao.listRoles();
		if (elusers != null) {
			for (int i = 0; i < elusers.size(); i++) {
				elusers.get(i).setIntroom(
						eroomDao.checkepracuser(examprac.getId(), elusers
								.get(i).getId()));
			}
		}
		// elusers = userDao.getUserByDepId(department.getId(), sub_department,
		// elUser, getPageNow(), getPageSize());
		// if (elusers != null) {
		// for (int i = 0; i < elusers.size(); i++) {
		// elusers.get(i).setIntroom(
		// eroomDao.checkepracuser(examprac.getId(), elusers
		// .get(i).getId()));
		// }
		// }
		// count = userDao.getUserByDepIdSize(department.getId(),
		// sub_department,
		// elUser);
		return "examprac_assign_addlist";
	}

	public String examprac_assign_delete() throws ElException {
		eroomDao.deleteepracuser(examprac.getId(), elUser.getId());
		examprac = eroomDao.getexamprac(examprac.getId());
		elUser = userDao.getUserById(elUser.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EPRAC,
				ElLoggerConstants.LOG_TYPE_DELETE, examprac.getTitle()
						+ " 练习删除了人员 " + elUser.getRealname(),
				ElLoggerConstants.LOG_RES_SUCC, examprac.getId());
		return "examprac_assign_list";
	}

	public String examprac_assigndeplist() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		depTree = departmentDao.getDepTree(1, -1, true);
		canAssignDeps = eroomDao.listpracCanAssign2dep(examprac.getId());
		assignDeps = eroomDao.listpracAssigned2dep(examprac.getId());
		return "examprac_assigndeplist";
	}

	public String examprac_assigndep_add() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		if (null != canAssignDeps) {
			for (int i = 0; i < canAssignDeps.size(); i++) {
				eroomDao.pracassign2dep(examprac.getId(), canAssignDeps.get(i)
						.getId());
				List<ELUser> users = eroomDao.listUsersBydep(canAssignDeps.get(
						i).getId());
				if (null != users) {
					for (int j = 0; j < users.size(); j++) {
						if (!eroomDao.checkepracuser(examprac.getId(), users
								.get(j).getId()))
							eroomDao.addepracuser(examprac.getId(), users
									.get(j).getId());
					}
				}
			}
		}
		return "examprac_assigndeplist";
	}

	public String examprac_assigndep_delete() throws ElException {
		examprac = eroomDao.getexamprac(examprac.getId());
		if (null != assignDeps) {
			for (int i = 0; i < assignDeps.size(); i++) {
				eroomDao.pracunassign2dep(examprac.getId(), assignDeps.get(i)
						.getId());
				List<ELUser> users = eroomDao.listUsersBydep(assignDeps.get(i)
						.getId());
				if (null != users) {
					for (int j = 0; j < users.size(); j++) {
						eroomDao.deleteepracuser(examprac.getId(), users.get(j)
								.getId());
					}
				}
			}
		}
		return "examprac_assigndeplist";
	}

	public String examroom_addInit() throws ElException {
//		choose = choose;
		course = courseDao.getCourseById(course.getId());
		quizPapers = courseDao.getQuizpaperByCid(course.getId());// eroomDao.getQuizpaperByCid(course.getId());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		return "examroom_add";
	}

	public String examroom_add() throws ElException {
		course = courseDao.getCourseById(course.getId());
		examRoom.setCourse(course);
		examRoom.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		// 处理ip段
		String[] ipStart = getRequest().getParameterValues("ipStart");
		String[] ipEnd = getRequest().getParameterValues("ipEnd");
		StringBuffer ipStartbu = new StringBuffer("");
		StringBuffer ipEndbu = new StringBuffer("");
		if (ipStart != null) {
			for (int i = 0; i < ipStart.length; i++) {
				ipStartbu.append(ipStart[i] + "_");
			}
			// 去掉最后一个下划线
			ipStartbu.deleteCharAt(ipStartbu.length() - 1);
		}
		if (ipEnd != null) {
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndbu.append(ipEnd[i] + "_");
			}
			// 去掉最后一个下划线
			ipEndbu.deleteCharAt(ipEndbu.length() - 1);
		}
		// 赋值存到数据库
		examRoom.setIpStart(ipStartbu.toString());
		examRoom.setIpEnd(ipEndbu.toString());
		eroomDao.addExamRoom(examRoom);
		if (null != examRoom.getInvigilators()) {
			for (int i = 0; i < examRoom.getInvigilators().size(); i++) {
				if (!eroomDao.checkEroomUsers("rinvigilators", examRoom
						.getInvigilators().get(i).getId(), examRoom.getId()))
					eroomDao
							.addEroomusers("rinvigilators", examRoom
									.getInvigilators().get(i).getId(), examRoom
									.getId());
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"myExamroom_list", 0);
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getAppraises()) {
			for (int i = 0; i < examRoom.getAppraises().size(); i++) {
				if (!eroomDao.checkEroomUsers("rappraises", examRoom
						.getAppraises().get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("rappraises", examRoom
							.getAppraises().get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getAppraises().get(i).getId(),
//						"examroomwithoutcourse_readlist", 0);
//				roleDao.setUserfunc(examRoom.getAppraises().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getValids()) {
			for (int i = 0; i < examRoom.getValids().size(); i++) {
				if (!eroomDao.checkEroomUsers("valids", examRoom.getValids()
						.get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("valids", examRoom.getValids()
							.get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"examroom_validlist", 0);
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examPapers) {// 添加试卷
			for (int i = 0; i < examPapers.size(); i++) {
				ExamPaper ep = examPapers.get(i);
				if (ep != null && ep.getId() != 0){
					ep.setPassgrade(examRoom.getPassgrade());
					if (!eroomDao.checkEroomeps(examRoom.getId(), ep.getId())
							&& ep.getId() != 0)
						eroomDao.addEroomeps(examRoom.getId(), ep.getId(), 0,
								ep.getPractimes(), ep.getPracscore(), ep
										.getPassgrade(), ep.getStuview());
				}
			}
		}
		if (choose.equals("true")) {
			setElmessage("添加考场成功！");
			return "error";
		}
		return "examroom_add_success";
	}
	
	/**
	 * 添加调查问卷
	 * @return
	 * @throws ElException
	 */
	
	public String addQuestionnaire() throws ElException {
		Course course = new Course(-1);
		examRoom.setCourse(course);
		examRoom.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		// 处理ip段
		String[] ipStart = getRequest().getParameterValues("ipStart");
		String[] ipEnd = getRequest().getParameterValues("ipEnd");
		StringBuffer ipStartbu = new StringBuffer("");
		StringBuffer ipEndbu = new StringBuffer("");
		if (ipStart != null) {
			for (int i = 0; i < ipStart.length; i++) {
				ipStartbu.append(ipStart[i] + "_");
			}
			// 去掉最后一个下划线
			ipStartbu.deleteCharAt(ipStartbu.length() - 1);
		}
		if (ipEnd != null) {
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndbu.append(ipEnd[i] + "_");
			}
			// 去掉最后一个下划线
			ipEndbu.deleteCharAt(ipEndbu.length() - 1);
		}
		// 赋值存到数据库
		examRoom.setIpStart(ipStartbu.toString());
		examRoom.setIpEnd(ipEndbu.toString());
		examRoom.setClassid(-3);//问卷调查
		examRoom.setIslink(1);
		eroomDao.addExamRoom(examRoom);
		if (null != examRoom.getInvigilators()) {
			for (int i = 0; i < examRoom.getInvigilators().size(); i++) {
				if (!eroomDao.checkEroomUsers("rinvigilators", examRoom
						.getInvigilators().get(i).getId(), examRoom.getId()))
					eroomDao
							.addEroomusers("rinvigilators", examRoom
									.getInvigilators().get(i).getId(), examRoom
									.getId());
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"myExamroom_list", 0);
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getAppraises()) {
			for (int i = 0; i < examRoom.getAppraises().size(); i++) {
				if (!eroomDao.checkEroomUsers("rappraises", examRoom
						.getAppraises().get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("rappraises", examRoom
							.getAppraises().get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getAppraises().get(i).getId(),
//						"examroomwithoutcourse_readlist", 0);
//				roleDao.setUserfunc(examRoom.getAppraises().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getValids()) {
			for (int i = 0; i < examRoom.getValids().size(); i++) {
				if (!eroomDao.checkEroomUsers("valids", examRoom.getValids()
						.get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("valids", examRoom.getValids()
							.get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"examroom_validlist", 0);
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examPapers) {// 添加试卷
			for (int i = 0; i < examPapers.size(); i++) {
				ExamPaper ep = examPapers.get(i);
				if (ep != null && ep.getId() != 0){
					ep.setPassgrade(examRoom.getPassgrade());
					if (!eroomDao.checkEroomeps(examRoom.getId(), ep.getId())
							&& ep.getId() != 0)
						eroomDao.addEroomeps(examRoom.getId(), ep.getId(), 0,
								ep.getPractimes(), ep.getPracscore(), ep
										.getPassgrade(), ep.getStuview());
				}
			}
		}
		return "addQuestionnaire";
	}

	public String examroom_alterInit() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
		examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
				.getId()));
		examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
		examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
		quizPapers = courseDao.getQuizpaperByCid(examRoom.getCourse().getId());
		if (null != quizPapers)
			for (int i = 0; i < quizPapers.size(); i++) {
				quizPapers.get(i).setErHasEp(
						eroomDao.checkEroomeps(examRoom.getId(), quizPapers
								.get(i).getExamPaper().getId()));
			}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		// --//
		if (examRoom.getExampapers() != null
				&& examRoom.getExampapers().size() >= 1) {
			if (examPaper == null) {
				examPaper = new ExamPaper();
			}
			examPaper.setId(examRoom.getExampapers().get(0).getId());
		}
		// 处理ip段显示
		List<String> ipStratList = new ArrayList<String>();
		List<String> ipEndList = new ArrayList<String>();
		if (examRoom.getIpStart() != null && examRoom.getIpEnd() != null) {
			String[] ipStart = examRoom.getIpStart().split("_");
			String[] ipEnd = examRoom.getIpEnd().split("_");
			for (int i = 0; i < ipStart.length; i++) {
				ipStratList.add(ipStart[i]);
			}
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndList.add(ipEnd[i]);
			}
			getRequest().setAttribute("ipStratList", ipStratList);
			getRequest().setAttribute("ipEndList", ipEndList);
		}
		return "examroom_alter";
	}

	public String examroom_alter() throws ElException {
		course = courseDao.getCourseById(course.getId());
		examRoom.setCourse(course);
		// 处理ip段
		String[] ipStart = getRequest().getParameterValues("ipStart");
		String[] ipEnd = getRequest().getParameterValues("ipEnd");
		if (ipStart != null && ipEnd != null) {
			StringBuffer ipStartbu = new StringBuffer("");
			StringBuffer ipEndbu = new StringBuffer("");
			for (int i = 0; i < ipStart.length; i++) {
				ipStartbu.append(ipStart[i] + "_");
			}
			// 去掉最后一个下划线
			ipStartbu.deleteCharAt(ipStartbu.length() - 1);
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndbu.append(ipEnd[i] + "_");
			}
			// 去掉最后一个下划线
			ipEndbu.deleteCharAt(ipEndbu.length() - 1);
			// 赋值存到数据库
			examRoom.setIpStart(ipStartbu.toString());
			examRoom.setIpEnd(ipEndbu.toString());
		}
		eroomDao.alterExamRoom(examRoom);
		if (null != examRoom.getInvigilators()) {
			for (int i = 0; i < examRoom.getInvigilators().size(); i++) {
				if (!eroomDao.checkEroomUsers("rinvigilators", examRoom
						.getInvigilators().get(i).getId(), examRoom.getId()))
					eroomDao
							.addEroomusers("rinvigilators", examRoom
									.getInvigilators().get(i).getId(), examRoom
									.getId());
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"myExamroom_list", 0);
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getAppraises()) {
			for (int i = 0; i < examRoom.getAppraises().size(); i++) {
				if (!eroomDao.checkEroomUsers("rappraises", examRoom
						.getAppraises().get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("rappraises", examRoom
							.getAppraises().get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"examroomwithoutcourse_readlist", 0);
//				roleDao.setUserfunc(examRoom.getInvigilators().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != examRoom.getValids()) {
			for (int i = 0; i < examRoom.getValids().size(); i++) {
				if (!eroomDao.checkEroomUsers("valids", examRoom.getValids()
						.get(i).getId(), examRoom.getId()))
					eroomDao.addEroomusers("valids", examRoom.getValids()
							.get(i).getId(), examRoom.getId());
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"examroom_validlist", 0);
//				roleDao.setUserfunc(examRoom.getValids().get(i).getId(),
//						"admin", 0);
			}
		}
		// if (null != examPapers) {
		// for (int i = 0; i < examPapers.size(); i++) {
		// ExamPaper ep = examPapers.get(i);
		// if (ep != null && ep.getId() != 0) {
		// if (!eroomDao.checkEroomeps(examRoom.getId(), examPapers
		// .get(i).getId()))
		// eroomDao.addEroomeps(examRoom.getId(), examPapers
		// .get(i).getId(), 0, ep.getPractimes(), ep
		// .getPracscore(), ep.getPassgrade(), ep
		// .getStuview());
		// else
		// eroomDao.alterEroomeps(examRoom.getId(), examPapers
		// .get(i).getId(), 0, ep.getPractimes(), ep
		// .getPracscore(), ep.getPassgrade(), ep
		// .getStuview());
		// }
		// }
		// }

		if (null != examPapers && examPaper != null) {
			for (int i = 0; i < examPapers.size(); i++) {
				ExamPaper ep = examPapers.get(i);
				if (ep != null && ep.getId() != 0) {

					eroomDao.alterEroomeps(examRoom.getId(), examPapers.get(i)
							.getId(), 0, ep.getPractimes(), ep.getPracscore(),
							ep.getPassgrade(), ep.getStuview(), examPaper
									.getId());

				}
			}
		}
		return "examroom_alter_success";
	}

	public String examroom_assignInit() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		// examPapers =
		// eroomDao.getEroomepwithusizes(examRoom.getId());//如果改成考核考试那样，那么试卷id从页面获取
		if (examPapers == null) {
			examPapers = new ArrayList<ExamPaper>();
		}
		examPapers.add(new ExamPaper(examPaper.getId()));
		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());
		if (examPapers == null || examPapers.size() == 0) {
			setElmessage("对不起该考场中没有试卷，不能分配考生！请返回为他添加试卷");
			return "error";
		}
		// 获取培训班id
//		int classid = 0;
//		if (course != null) {
//			classid = course.getClassid();
//		}
		// //注入对象
		// course.setClassid(classid);
		if (examPapers.size() == 1) {
			examPaper = examPapers.get(0);
			return "examroom_assignSearchInit";
		}
		examRoom.setExampapers(examPapers);
		return "examroom_assign";
	}

	public String examroom_assignwcInit() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		//examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		examPapers = eroomDao.getEroomEps(examRoom.getId());
		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());
		if (examPapers == null || examPapers.size() == 0) {
			setElmessage("对不起该考场中没有试卷，不能分配考生！请返回为他添加试卷");
			return "error";
		}
		// if (examPapers.size() == 1 && course.getId() == -1) {
		// examPaper = examPapers.get(0);
		// return "examroom_assignwcSearchInit";
		// }
		examRoom.setExampapers(examPapers);
		if (course.getId() == -1) {
			return "examroom_assignwc";
		}
		return "examroom_assign";
	}
	
	public String questionnaire_assignwcInit() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		//examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		examPapers = eroomDao.getEroomEps(examRoom.getId());
		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());
		if (examPapers == null || examPapers.size() == 0) {
			setElmessage("对不起该问卷中没有试卷，不能分配考生！请返回为他添加试卷");
			return "error";
		}
		// if (examPapers.size() == 1 && course.getId() == -1) {
		// examPaper = examPapers.get(0);
		// return "examroom_assignwcSearchInit";
		// }
		examRoom.setExampapers(examPapers);
		if (course.getId() == -1) {
			return "questionnaire_assignwc";
		}
		return "questionnaire_assign";
	}

	public String examroom_assignwcSearchInit() throws ElException {
		// examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		// examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree(1, -1, true);
		// else {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }
		// return "examroom_assignwcSearch";
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree(1, -1, true);
		// else {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }
		return "examroom_assignSearchlist";
	}

	/**
	 * 试卷分配人员
	 * 
	 * @return
	 * @throws ElException
	 */
	public String examroom_assignwc() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		if (null != canAssignUsers)
			for (int i = 0; i < canAssignUsers.size(); i++) {
				// if
				// (!studyQuizDao.hasInQuizPaper(canAssignUsers.get(i).getId(),
				// examRoom.getId(), examPaper.getId())) {
				// studyQuizDao.intoQuizPaper(canAssignUsers.get(i).getId(),
				// examRoom.getId(), examPaper.getId());
				// }
				// if (!eroomDao.checkuser2eroom(examRoom.getId(),
				// canAssignUsers
				// .get(i).getId())) {
				// eroomDao.adduser2eroom(examRoom.getId(), canAssignUsers
				// .get(i).getId());
				// }

				// if
				// (!studyQuizDao.hasInQuizPaper(canAssignUsers.get(i).getId(),
				// examRoom.getId(), examPaper.getId(), examRoom
				// .getClassid())) {// 检测是否已经进入考场
				// //添加该学员到 学员考场记录表中
				// int
				// recordid=studyQuizDao.addStudyRoomRecord(canAssignUsers.get(i).getId(),
				// examRoom.getId());
				// //添加学员答卷信息(记录id加进去)
				// studyQuizDao.intoQuizPaper(canAssignUsers.get(i).getId(),
				// examRoom// 添加study_quizinfo信息（考试信息）
				// .getId(), examPaper.getId(), examRoom
				// .getClassid());
				// }
				// 检测学员是否分配到考场
				if (!eroomDao.checkuser2eroom(examRoom.getId(), canAssignUsers
						.get(i).getId(), examRoom.getClassid())) {
					eroomDao.adduser2eroom(examRoom.getId(), canAssignUsers
							.get(i).getId(), 1, examRoom.getClassid(),
							CourseConstants.EXAMROOM_FPFS_SQ);
				}
				// 检测该学员是否分配了该试卷
				if (!studyQuizDao.checkStudyExamPaper(canAssignUsers.get(i)
						.getId(), examPaper.getId(), examRoom.getId(), examRoom
						.getClassid())) {
					// 添加该学员到 学员试卷表中
					studyQuizDao.addStudyExamPaper(canAssignUsers.get(i)
							.getId(), examPaper.getId(), examRoom.getId(),
							examRoom.getClassid());
				}
			}
		// examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		// elusers = userDao.getUserByDepId(department.getId(), sub_department,
		// elUser, getPageNow(), getPageSize());
		// if (elusers != null) {
		// for (int i = 0; i < elusers.size(); i++) {
		// elusers.get(i).setIntroom(
		// studyQuizDao.hasInQuizPaper(elusers.get(i).getId(),
		// examRoom.getId(), examPaper.getId()));
		// }
		// }
		// count = userDao.getUserByDepIdSize(department.getId(),
		// sub_department,
		// elUser);

		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_ADD, examRoom.getTitle() + "（添加学员）",
				ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		if (ajax == 1) {
			return null;
		}
		examroom_assignSearchlist();
		return "examroom_assignwcSearchlist";
	}

	public String examroom_assignwcSelectings() throws ElException {// 选拨式
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		if (null != canAssignUsers)
			for (int i = 0; i < canAssignUsers.size(); i++) {
				// if
				// (!studyQuizDao.hasInQuizPaper(canAssignUsers.get(i).getId(),
				// examRoom.getId(), examPaper.getId())) {
				// studyQuizDao.intoQuizPaper(canAssignUsers.get(i).getId(),
				// examRoom.getId(), examPaper.getId());
				// }
				// if (!eroomDao.checkuser2eroom(examRoom.getId(),
				// canAssignUsers
				// .get(i).getId())) {
				// eroomDao.adduser2eroom(examRoom.getId(), canAssignUsers
				// .get(i).getId());
				// }
				// if
				// (!studyQuizDao.hasInQuizPaper(canAssignUsers.get(i).getId(),
				// examRoom// 检测是否已经进入考场
				// .getId(), examPaper.getId(), examRoom
				// .getClassid())) {
				// studyQuizDao.intoQuizPaper(canAssignUsers.get(i).getId(),
				// examRoom// 添加study_quizinfo信息（考试信息）
				// .getId(), examPaper.getId(), examRoom
				// .getClassid());
				// }
				// 检测该学员是否分配了该试卷
				if (!studyQuizDao.checkStudyExamPaper(canAssignUsers.get(i)
						.getId(), examPaper.getId(), examRoom.getId(), examRoom
						.getClassid())) {
					// 添加该学员到 学员试卷表中
					studyQuizDao.addStudyExamPaper(canAssignUsers.get(i)
							.getId(), examPaper.getId(), examRoom.getId(),
							examRoom.getClassid());
				}
				if (!eroomDao.checkuser2eroom(examRoom.getId(), canAssignUsers
						.get(i).getId(), examRoom.getClassid())) {
					eroomDao.adduser2eroom(examRoom.getId(), canAssignUsers
							.get(i).getId(), 1, examRoom.getClassid(),
							CourseConstants.EXAMROOM_FPFS_SQ);
				}
			}
		examroom_assignSearchlist();
		return "examroom_selectings";
	}
	/**
	 * 按培训班搜索学员
	 * 
	 * @return
	 * @throws ElException
	 */
	public String elclass_seachUser() throws ElException {
		if (classPara!=null) {
			// 当前用户
			elUser = new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
			// elUser.setRole(new
			// ElRole(getSessionIntValue(ElConstants.SESSION_ROLE)));
			elUser.setDepartment(new Department(department.getId()));
			elusers = eroomDao.listUserOnClassSeach(classPara, examRoom.getId(),
					examPaper.getId(), elUser,getPageNow(), getPageSize());
			count = eroomDao.listUserOnClassSeachSize(classPara, elUser);
			String jsons = "";
			if (elusers != null)
				for (int i = 0; i < elusers.size(); i++) {
					ELUser u = elusers.get(i);
					jsons += "{'id':'" + u.getId() + "','username':'"
							+ u.getUsername() + "','realname':'"
							+ u.getRealname() + "','depname':'"
							+ u.getDepartment().getName() + "','rolename':'"
							+ u.getRole().getName() + "','sex':'" + u.getSex()
							+ "','jz':'" + u.getJingzhong_() + "','age':'"
							+ u.getAGE() + "','assign':'" + u.getIsAssign()
							+ "','joinway':'" + u.getJoinway_()
							+ "','joinwayInt':'" + u.getJoinwayInt() + "'},";
				}
			if (jsons.length() > 0)
				jsons = "[" + jsons.substring(0, jsons.length() - 1) + "]";
			else
				jsons = "[]";
			printMsg("{'count':" + count + ",'users':" + jsons + "}");
		} else {
			// setElmessage("没有选择考场");
			// return "error";
			printMsg("err1");
		}
		// jingzhongs=userDao.getBaseDatatByTypeid(1);
		// zhiwus=userDao.getBaseDatatByTypeid(2);
		// zhijis=userDao.getBaseDatatByTypeid(3);
		// gangweis=userDao.getBaseDatatByTypeid(4);
		// dishis=userDao.getBaseDatatByTypeid(5);
		return null;
		// return "examroom_assignwcSearchlist";
	}
	/**
	 * 按考场搜索学员
	 * 
	 * @return
	 * @throws ElException
	 */
	public String examroom_seachUser() throws ElException {
		if ((erParas != null && examRoom.getQueryManner() == 1)
				|| (erepParas != null && examRoom.getQueryManner() == 2)) {
			if (examRoom.getQueryManner() == 2) {// 判断是否是按考场试卷查询的
				erParas = erepParas;
			}
			// 当前用户
			elUser = new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
			// elUser.setRole(new
			// ElRole(getSessionIntValue(ElConstants.SESSION_ROLE)));
			elUser.setDepartment(new Department(department.getId()));
			elusers = eroomDao.listUserOnEroomSeach(erParas, examRoom.getId(),
					examPaper.getId(), examRoom.getQueryManner(), elUser,
					getPageNow(), getPageSize());
			count = eroomDao.listUserOnEroomSeachSize(erParas, examRoom
					.getQueryManner(), elUser);
			String jsons = "";
			if (elusers != null)
				for (int i = 0; i < elusers.size(); i++) {
					ELUser u = elusers.get(i);
					jsons += "{'id':'" + u.getId() + "','username':'"
							+ u.getUsername() + "','realname':'"
							+ u.getRealname() + "','depname':'"
							+ u.getDepartment().getName() + "','rolename':'"
							+ u.getRole().getName() + "','sex':'" + u.getSex()
							+ "','jz':'" + u.getJingzhong_() + "','age':'"
							+ u.getAGE() + "','assign':'" + u.getIsAssign()
							+ "','joinway':'" + u.getJoinway_()
							+ "','joinwayInt':'" + u.getJoinwayInt() + "'},";
				}
			if (jsons.length() > 0)
				jsons = "[" + jsons.substring(0, jsons.length() - 1) + "]";
			else
				jsons = "[]";
			printMsg("{'count':" + count + ",'users':" + jsons + "}");
		} else {
			// setElmessage("没有选择考场");
			// return "error";
			printMsg("err1");
		}
		// jingzhongs=userDao.getBaseDatatByTypeid(1);
		// zhiwus=userDao.getBaseDatatByTypeid(2);
		// zhijis=userDao.getBaseDatatByTypeid(3);
		// gangweis=userDao.getBaseDatatByTypeid(4);
		// dishis=userDao.getBaseDatatByTypeid(5);
		return null;
		// return "examroom_assignwcSearchlist";
	}

	/**
	 * 分配给所有学员(ajax)
	 * 
	 * @return
	 * @throws ElException
	 */
	public String examroom_assignwcsAjax() throws ElException {
		if (examRoom.getQueryManner() == 2) {
			erParas = erepParas;
		}
		elUser = new ELUser(getSessionIntValue(ElConstants.SESSION_USERID));
		// elUser.setRole(new
		// ElRole(getSessionIntValue(ElConstants.SESSION_ROLE)));
		elUser.setDepartment(new Department(department.getId()));
		if(examRoom.getQueryManner()==3){
			if(null!=classPara)
				elusers = eroomDao.listUserOnClassSeach(classPara, elUser);
		}else{
			if(null!=erParas)
				elusers = eroomDao.listUserOnEroomSeach(erParas, examRoom
					.getQueryManner(), elUser);
		}
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		for (ELUser el : elusers) {
			// 检测该学员是否分配了该试卷
			if (!studyQuizDao.checkStudyExamPaper(el.getId(),
					examPaper.getId(), examRoom.getId(), examRoom.getClassid())) {
				// 添加该学员到 学员试卷表中
				studyQuizDao.addStudyExamPaper(el.getId(), examPaper.getId(),
						examRoom.getId(), examRoom.getClassid());
			}
			if (!eroomDao.checkuser2eroom(examRoom.getId(), el.getId(),
					examRoom.getClassid())) {
				eroomDao
						.adduser2eroom(examRoom.getId(), el.getId(), 1,
								examRoom.getClassid(),
								CourseConstants.EXAMROOM_FPFS_SQ);
			}
		}
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_ADD, examRoom.getTitle()
						+ "（分配所有学员）", ElLoggerConstants.LOG_RES_SUCC, examRoom
						.getId());
		return null;
	}

	/**
	 * 分配给所有学员
	 * 
	 * @return
	 * @throws ElException
	 */
	public String examroom_assignwcs() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			depTree = departmentDao.getDepTree(1, -1, true);
		} else {
			depTree = departmentDao.getDepTree(
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
		}

		int depid = 1;
//		if (department == null) {
//			if (depTree.getId() == -2)
//				depid = -2;
//			else{
//				department = new Department(
//						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
//				depid = department.getId();
//			}
//		} else {
//			depid = department.getId();
//		}
//		if (sub_department == 1) {
//			depTree.setLower(true);
//		}
		if (department == null||department.getId()<0) {
			department=depTree;
		} else {
			department=departmentDao.getDepById(department.getId());
		}
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		if (sub_department == 1) {
			department.setLower(true);
		}

		elusers = userDao.getDistributionStudents(department, station,depid, elUser,
				getSessionIntValue(ElConstants.SESSION_ROLE), 99999, 1);
		// elusers = userDao.getDistributionStudents(depTree, depid, elUser,
		// getSessionIntValue(ElConstants.SESSION_ROLE));
		elUser = elUser == null ? new ELUser() : elUser;

		for (ELUser el : elusers) {
			// studyQuizDao.intoQuizPaper(el.getId(),
			// examRoom.getId(), examPaper.getId());
			// if (!studyQuizDao.hasInQuizPaper(el.getId(),
			// examRoom.getId(), examPaper.getId())) {
			// studyQuizDao.intoQuizPaper(el.getId(),
			// examRoom.getId(), examPaper.getId());
			// }
			// if (!studyQuizDao.hasInQuizPaper(el.getId(), examRoom//
			// 检测是否已经进入考场
			// .getId(), examPaper.getId(), examRoom.getClassid())) {
			// studyQuizDao.intoQuizPaper(el.getId(), examRoom//
			// 添加study_quizinfo信息（考试信息）
			// .getId(), examPaper.getId(), examRoom.getClassid());
			// }
			// 检测该学员是否分配了该试卷
			if (!studyQuizDao.checkStudyExamPaper(el.getId(),
					examPaper.getId(), examRoom.getId(), examRoom.getClassid())) {
				// 添加该学员到 学员试卷表中
				studyQuizDao.addStudyExamPaper(el.getId(), examPaper.getId(),
						examRoom.getId(), examRoom.getClassid());
			}
			if (!eroomDao.checkuser2eroom(examRoom.getId(), el.getId(),
					examRoom.getClassid())) {
				eroomDao
						.adduser2eroom(examRoom.getId(), el.getId(), 1,
								examRoom.getClassid(),
								CourseConstants.EXAMROOM_FPFS_SQ);
			}
		}

		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_ADD, examRoom.getTitle()
						+ "（分配所有学员）", ElLoggerConstants.LOG_RES_SUCC, examRoom
						.getId());
		examroom_assignSearchlist();
		return "examroom_assignwcSearchlist";
	}

	public String examroom_unassignwc() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		if (examPapers.size() == 1) {
			examPaper = examPapers.get(0);
		}
		// studyQuizDao.deleteQuiz(uid, roomid)
		if (null != canAssignUsers)
			for (int i = 0; i < canAssignUsers.size(); i++) {
				studyQuizDao.deleteQuiz(canAssignUsers.get(i).getId(), examRoom
						.getId(), examPaper.getId());
			}
		// return "examroom_assignuserlist";
		// return examroom_assignSearchInit();
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_DELETE, examRoom.getTitle()
						+ "（删除学员）", ElLoggerConstants.LOG_RES_SUCC, examRoom
						.getId());
		examroom_assignSearchlist();
		if (getReturn() != null && getReturn().equals("examroom_selectings")) {
			return getReturn();
		}
		if (ajax == 1) {
			return null;
		}
		return "examroom_assignwcSearchlist";
	}

	public String examroom_assigndepInit() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());
		if (examPapers == null || examPapers.size() == 0) {
			setElmessage("对不起该考场中没有试卷，不能分配给部门！请返回为它添加试卷");
			return "error";
		}
		if (examPapers.size() == 1) {
			examPaper = examPapers.get(0);
			return "examroom_assigndeplist";
		}
		examRoom.setExampapers(examPapers);
		return "examroom_assign";
	}

	public String examroom_assigndeplist() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree(1, -1, true);
		else {
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		canAssignDeps = eroomDao.listCanAssign2dep(examRoom.getId(), examPaper
				.getId());
		assignDeps = eroomDao.listAssigned2dep(examRoom.getId(), examPaper
				.getId());
		return "examroom_assigndeplist";
	}

	public String examroom_assigndep_add() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		if (null != canAssignDeps) {
			for (int i = 0; i < canAssignDeps.size(); i++) {
				eroomDao.assign2dep(examRoom.getId(), canAssignDeps.get(i)
						.getId(), examPaper.getId());
				List<ELUser> users = eroomDao.listUsersBydep(canAssignDeps.get(
						i).getId());
				if (null != users) {
					for (int j = 0; j < users.size(); j++) {
						// if
						// (!studyQuizDao.hasInQuizPaper(users.get(j).getId(),
						// examRoom.getId(), examPaper.getId())) {
						// studyQuizDao.intoQuizPaper(users.get(j).getId(),
						// examRoom.getId(), examPaper.getId());
						// }
						// if (!eroomDao.checkuser2eroom(examRoom.getId(), users
						// .get(j).getId())) {
						// eroomDao.adduser2eroom(examRoom.getId(), users.get(
						// j).getId());
						// }
						// if
						// (!studyQuizDao.hasInQuizPaper(users.get(j).getId(),
						// examRoom// 检测是否已经进入考场
						// .getId(), examPaper.getId(), examRoom
						// .getClassid())) {
						// studyQuizDao.intoQuizPaper(users.get(j).getId(),
						// examRoom// 添加study_quizinfo信息（考试信息）
						// .getId(), examPaper.getId(),
						// examRoom.getClassid());
						// // 把考场id摄入class_course表
						// }
						// 检测该学员是否分配了该试卷
						if (!studyQuizDao.checkStudyExamPaper(users.get(j)
								.getId(), examPaper.getId(), examRoom.getId(),
								examRoom.getClassid())) {
							// 添加该学员到 学员试卷表中
							studyQuizDao.addStudyExamPaper(
									users.get(j).getId(), examPaper.getId(),
									examRoom.getId(), examRoom.getClassid());
						}
						if (!eroomDao.checkuser2eroom(examRoom.getId(), users
								.get(j).getId(), examRoom.getClassid())) {
							eroomDao.adduser2eroom(examRoom.getId(), users.get(
									j).getId(), 1, examRoom.getClassid(),
									CourseConstants.EXAMROOM_FPFS_SQ);
						}
					}
				}
			}
		}
		return "examroom_assigndeplist";
	}

	public String examroom_assigndep_delete() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		if (null != assignDeps) {
			for (int i = 0; i < assignDeps.size(); i++) {
				eroomDao.unassign2dep(examRoom.getId(), assignDeps.get(i)
						.getId(), examPaper.getId());
				List<ELUser> users = eroomDao.listUsersBydep(assignDeps.get(i)
						.getId());
				if (null != users) {
					for (int j = 0; j < users.size(); j++) {
						studyQuizDao.deleteQuiz(users.get(j).getId(), examRoom
								.getId(), examPaper.getId());
					}
				}
			}
		}
		return "examroom_assigndeplist";
	}

	/**
	 * 考场试卷 人员详情
	 * 
	 * @return
	 * @throws ElException
	 */
	public String examroom_assignuserlist() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		elusers = eroomDao.listroom2userbyurid(examPaper.getId(), examRoom
				.getId(), getPageNow(), getPageSize());
		count = eroomDao.listroom2userbyuridSize(examPaper.getId(), examRoom
				.getId());
		return "examroom_assignuserlist";
	}

	public String examroom_assignuser_deletes() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		if (null != elusers)
			for (int j = 0; j < elusers.size(); j++) {
				studyQuizDao.deleteQuiz(elusers.get(j).getId(), examRoom
						.getId(), examPaper.getId());
			}
		if (getReturn() != null && getReturn().equals("examroom_selectings")) {
			return getReturn();
		}
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_DELETE, examRoom.getTitle()
						+ "（删除学员）", ElLoggerConstants.LOG_RES_SUCC, examRoom
						.getId());
		return "examroom_assignuserlist";
	}

	// 考场组合搜索
	public String combinationSearchExamroomInit() throws ElException {
		return "combinationSearchExamroomInit";
	}

	public String combinationSearchExamroom() throws ElException {
		/*
		 * int userid = getSessionIntValue(ElConstants.SESSION_USERID); String
		 * title = examRoom == null ? "" : examRoom.getTitle() == null ? "" :
		 * examRoom.getTitle().trim(); if (examRoom != null) { examRooms =
		 * eroomDao.listMyExamRoom(userid, examRoom, getPageNow(),
		 * getPageSize()); } else { examRooms = eroomDao.listMyExamRoom(userid,
		 * title, getPageNow(), getPageSize()); } count =
		 * eroomDao.listMyExamRoomSize(userid, title);
		 */
		// int Lid = examRoom.getEroomLib().getId();
		// if (getRequest().getParameter("str") == null) {
		// getSession().setAttribute("csel", examRoom);
		// } else {
		// EroomLib el = examRoom.getEroomLib();
		// examRoom = (ExamRoom) getSession().getAttribute("csel");
		// // examRoom.setEroomLib(eroomLib)
		// }
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		// examRooms = eroomDao.combinationSearchExamroom(examRoom,
		// eroomLibTree,
		// getSessionIntValue(ElConstants.SESSION_ROLE), Lid,
		// getPageNow(), getPageSize());
		// count = eroomDao.combinationSearchExamroomCount(examRoom,
		// eroomLibTree,
		// getSessionIntValue(ElConstants.SESSION_ROLE), Lid);
		if (examRoom.getEroomLib() == null
				|| examRoom.getEroomLib().getId() <= 0) {
			examRoom.setEroomLib(eroomLibTree);
		} else {
			examRoom.setEroomLib(eroomDao.getEroomLibById(examRoom
					.getEroomLib().getId()));
		}
		sublibs = 1;
		examRoom.setClassid(-2);
		examRooms = eroomDao.listExamRoom(examRoom.getEroomLib(), sublibs, "",
				examRoom, getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize(examRoom.getEroomLib(), sublibs, "",
				examRoom);
		return "combinationSearchExamroom";
	}
	//培训班分配考场
	public String assignRoomInit() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		examRoom = examRoom == null?new ExamRoom():examRoom;
		if (examRoom.getEroomLib() == null
				|| examRoom.getEroomLib().getId() <= 0) {
			examRoom.setEroomLib(eroomLibTree);
		} else {
			examRoom.setEroomLib(eroomDao.getEroomLibById(examRoom
					.getEroomLib().getId()));
		}
		sublibs = 1;
		examRooms = eroomDao.listExamRoom(examRoom.getEroomLib(), sublibs, "",
				null, getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize(examRoom.getEroomLib(), sublibs, "",
				null);
		return "assignRoomInit";
	}
	public String assignRoom() throws ElException{
//		String[] array = null;
//		if(roomids!=null&&!roomids.equals("")){
//			array = roomids.split(",");
//			if(array!=null&&array.length>0){
//				for(int i=0;i<array.length;i++){
//					eroomDao.assignRoom(Integer.parseInt(array[i]),elClass.getId());
//				}
//			}
//		}
		eroomDao.assignRoom(roomid,elClass.getId(),firstLearnLaterExam,standardLine);
		return "assignRoom";
	}

	Department bassignedDep;
	Department canAssignDep;
	private ElClTypeDao elClTypeDao;
	private ElClType cltype;
	private ElClType cltypeTree;

	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}

	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}

	public ElClType getCltype() {
		return cltype;
	}

	public void setCltype(ElClType cltype) {
		this.cltype = cltype;
	}

	public ElClType getCltypeTree() {
		return cltypeTree;
	}

	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}

	public String examroom_assignSearchInit() throws ElException {
		// examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		// examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		// // if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree(1, -1, true);
		// // else {
		// depTree =
		// departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID),
		// "op", -1,true);
		// // }
		// return "examroom_assignSearch";
//		if("-2".equals(getRequest().getParameter("deptid"))){
//			setElmessage("请选择有效部门！！");
//			return "error";
//		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "use", -1,
					true);
		}
//		cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(
//				ElConstants.TREE_ROOT, ElConstants.TREE_FIANL, true,
//				getSessionIntValue(ElConstants.SESSION_USERID), true,
//				"CLASS_OP_TYPE");

		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		ep = eroomDao.getEroomeps(examRoom.getId(), examPaper.getId());
		examPaper.setPracscore(ep.getPracscore());
		examPaper.setPractimes(ep.getPractimes());
		//int depid = deptid != null ? deptid : 1;
		int depid = deptid != null ? deptid : depTree.getId();
		int cid = (course == null) ? 0 : course.getId();
		int classid = 0;
		if (course != null) {
			classid = course.getClassid();
			ClassDao classDao = new ClassDaoImpl();
			ElClass tempClass = classDao.getClassById(classid);
			course.setClassName(tempClass.getName());
		}
		if (elUser != null && elUser.getIsQualified()!=null
				&& !elUser.getIsQualified().equals("")) {
			elUser.setPractimes(ep.getPractimes());
			elUser.setPracscore(ep.getPracscore());
		}
		//department=
		if(depid>0){
			department=departmentDao.getDepById(depid);
		}else{
			department=depTree;
		}
		elusers = eroomDao.listAssignedUser(getPageNow(),
				getPageSize(),
				depid, // 如果像考核考试那样 ，那么examPaper.getId()从页面获取
				cid, CourseConstants.COURSE_STUDY_STATUS_XX, null, this
						.getStarttime(), this.getEndtime(),
				this.getClassname(), examRoom.getId(), examPaper.getId(),
				elUser, cltype, cltypeTree, classid, department);
		count = eroomDao.listAssignedUserSize(depid, cid,
				CourseConstants.COURSE_STUDY_STATUS_XX, null, this
						.getStarttime(), this.getEndtime(),
				this.getClassname(), examRoom.getId(), examPaper.getId(),
				elUser, cltype, cltypeTree, classid, department);
		elUser = elUser == null ? new ELUser() : elUser;
		// 获取课程id，查出该课程所有所在班级
		// List<ElClass> classList=eroomDao.getStudyCourseInClass(cid);
		// getRequest().setAttribute("classList", classList);

		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		gangweis = userDao.getBaseDatatByTypeid(4);
		dishis = userDao.getBaseDatatByTypeid(5);
		// department.setId(depid);
		return "examroom_listAssignedUser";
	}

	public String examroom_assignSearchAll() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		int depid = deptid != null ? deptid
				: getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		int cid = (course == null) ? 0 : course.getId();
		cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(
				ElConstants.TREE_ROOT, ElConstants.TREE_FIANL, true,
				getSessionIntValue(ElConstants.SESSION_USERID), true,
				"CLASS_OP_TYPE");
		// elusers = eroomDao.listAssignedUser(99999, 1, depid, cid,
		// CourseConstants.COURSE_STUDY_STATUS_XX, null, this
		// .getStarttime(), this.getEndtime(),
		// this.getClassname(), examRoom.getId(), examPaper.getId(),
		// elUser, cltype, cltypeTree);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "use", -1,
					true);
		}
		elusers = eroomDao.listAssignedUser(99999,
				1,
				depid, // 如果像考核考试那样 ，那么examPaper.getId()从页面获取
				cid, CourseConstants.COURSE_STUDY_STATUS_XX, null, this
						.getStarttime(), this.getEndtime(),
				this.getClassname(), examRoom.getId(), examPaper.getId(),
				elUser, cltype, cltypeTree, examRoom.getClassid(), depTree);
		if (null != elusers)
			for (int i = 0; i < elusers.size(); i++) {
				// if (!studyQuizDao.hasInQuizPaper(elusers.get(i).getId(),
				// examRoom.getId(), examPaper.getId())) {
				// studyQuizDao.intoQuizPaper(elusers.get(i).getId(), examRoom
				// .getId(), examPaper.getId());
				// }
				// if (!eroomDao.checkuser2eroom(examRoom.getId(),
				// elusers.get(i)
				// .getId())) {
				// eroomDao.adduser2eroom(examRoom.getId(), elusers.get(i)
				// .getId());
				// }
				// if (!studyQuizDao.hasInQuizPaper(elusers.get(i).getId(),
				// examRoom// 检测是否已经进入考场
				// .getId(), examPaper.getId(), course
				// .getClassid())) {
				// studyQuizDao.intoQuizPaper(elusers.get(i).getId(), examRoom//
				// 添加study_quizinfo信息（考试信息）
				// .getId(), examPaper.getId(), course.getClassid());
				// // 把考场id摄入class_course表
				// }
				// 检测该学员是否分配了该试卷
				if (!studyQuizDao.checkStudyExamPaper(elusers.get(i).getId(),
						examPaper.getId(), examRoom.getId(), course
								.getClassid())) {
					// 添加该学员到 学员试卷表中
					studyQuizDao.addStudyExamPaper(elusers.get(i).getId(),
							examPaper.getId(), examRoom.getId(), course
									.getClassid());
				}
				if (!eroomDao.checkuser2eroom(examRoom.getId(), elusers.get(i)
						.getId(), course.getClassid())) {
					eroomDao.adduser2eroom(examRoom.getId(), elusers.get(i)
							.getId(), 1, course.getClassid(),
							CourseConstants.EXAMROOM_FPFS_SQ);
				}
			}
		return examroom_assignSearchInit();
	}

	public Department getBassignedDep() {
		return bassignedDep;
	}

	public void setBassignedDep(Department bassignedDep) {
		this.bassignedDep = bassignedDep;
	}

	public Department getCanAssignDep() {
		return canAssignDep;
	}

	public void setCanAssignDep(Department canAssignDep) {
		this.canAssignDep = canAssignDep;
	}

	public String examroom_assignSearchlist() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		int depid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		} else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "use", -1,
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
		}
//		if (department == null) {
//			if (depTree.getId() == -2) {
//				depid = -2;
//				department = new Department(-2);
//			} else {
//				department = new Department(
//						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
//			}
//		} else {
//			depid = department.getId();
//		}
		if(department==null||department.getId()<=0){
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		if (sub_department == 1) {
			department.setLower(true);
		}
		if (DBMethods == 0) {// 按人员信息搜索
			elusers = userDao.getDistributionStudents(department, station,depid, elUser,
					getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(),
					getPageSize());
			count = userDao.getDistributionStudentsCount(department,station, depid,
					elUser, getSessionIntValue(ElConstants.SESSION_ROLE));
		} else if (DBMethods == 1) { // 按培训班信息搜索
			if (elClasss == null) {
				setElmessage("没有选择培训班");
				return "error";
			}
			elClass = classDao.getClassById(elClasss.get(0).getId());
			elusers = classDao.gettoClassInfoselectUser(depTree, department,
					"study_room", examRoom.getId(), elClass.getId(), elUser,
					this.getStarttime(), this.getEndtime(), getPageNow(),
					getPageSize());
			count = classDao.gettoClassInfoselectUserSize(depTree, department,
					"study_room", examRoom.getId(), elClass.getId(), elUser,
					this.getStarttime(), this.getEndtime());
		} else if (DBMethods == 2) {// 按考场信息搜索
			if (examRooms == null || examRooms.size() == 0) {
				setElmessage("没有选择考场");
				return "error";
			}
			elusers = classDao.gettoEroomInfoselectUser(depTree, department,
					"study_room", examRoom.getId(), examRooms.get(0).getId(),
					elUser, this.getStarttime(), this.getEndtime(),
					getPageNow(), getPageSize());
			count = classDao.gettoEroomInfoselectUserSize(depTree, department,
					"study_room", examRoom.getId(), examRooms.get(0).getId(),
					elUser, this.getStarttime(), this.getEndtime());
			eroom = eroomDao.getExamRoomByid(examRooms.get(0).getId());
			examPapers = eroomDao.getEroomeps(examRooms.get(0).getId());
		}

		if (elusers != null) {
			for (int i = 0; i < elusers.size(); i++) {
				// elusers.get(i).setIntroom(
				// studyQuizDao.hasInQuizPaper(elusers.get(i).getId(),
				// examRoom.getId(), examPaper.getId()));
				elusers.get(i).setIntroom(
				// studyQuizDao.hasInQuizPaper(elusers.get(i).getId(),
						// examRoom.getId(), examPaper.getId(), examRoom
						// .getClassid())
						studyQuizDao.checkStudyExamPaper(
								elusers.get(i).getId(), examPaper.getId(),
								examRoom.getId(), examRoom.getClassid()));
				// 设置学员的参加方式
				elusers.get(i).setJoinwayInt(
						studyQuizDao.getStudyEroomJoinway(elusers.get(i)
								.getId(), examRoom.getId()));
			}
		}
		elUser = elUser == null ? new ELUser() : elUser;
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		gangweis = userDao.getBaseDatatByTypeid(4);
		dishis = userDao.getBaseDatatByTypeid(5);
		//department.setId(depid);
		return "examroom_assignSearchlist";
	}

	public String examroom_assignSelectingsList() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		int depid;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			// depTree = departmentDao.getDepTree(1, -1, true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
			depid = department == null ? 1 : department.getId();
		} else {
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
			// true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			depid = department == null ? -2 : department.getId();
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}

		if (department == null) {
			department = new Department(1);
		}
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		if (sub_department == 1) {
			depTree.setLower(true);
		}
		if (depid == 0) {
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
				depid = 1;
			} else {
				depid = -2;
			}
		}
		elusers = userDao.getDistributionStudents(depTree, station,depid, elUser,
				getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(),
				getPageSize());
		count = userDao.getDistributionStudentsCount(depTree,station, depid, elUser,
				getSessionIntValue(ElConstants.SESSION_ROLE));
		if (elusers != null) {
			for (int i = 0; i < elusers.size(); i++) {
				// elusers.get(i).setIntroom(
				// studyQuizDao.hasInQuizPaper(elusers.get(i).getId(),
				// examRoom.getId(), examPaper.getId()));
				elusers.get(i).setIntroom(
				// studyQuizDao.hasInQuizPaper(elusers.get(i).getId(),
						// examRoom.getId(), examPaper.getId(), examRoom
						// .getClassid())
						studyQuizDao.checkStudyExamPaper(
								elusers.get(i).getId(), examPaper.getId(),
								examRoom.getId(), examRoom.getClassid()));
			}
		}
		elUser = elUser == null ? new ELUser() : elUser;
		elUser = elUser == null ? new ELUser() : elUser;
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		gangweis = userDao.getBaseDatatByTypeid(4);
		dishis = userDao.getBaseDatatByTypeid(5);
		department.setId(depid);
		return "examroom_assignSelectings";
	}

	public String examroom_assignSelectings() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		int depid;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			// depTree = departmentDao.getDepTree(1, -1, true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
			depid = department == null ? 1 : department.getId();
		} else {
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
			// true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			depid = department == null ? -2 : department.getId();
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		if (department == null) {
			department = new Department(1);
		}
		if (sub_department == 1) {
			depTree.setLower(true);
		}
		if (depid == 0) {
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
				depid = 1;
			} else {
				depid = -2;
			}
		}
		elusers = userDao.getDistributionStudents(depTree,station, depid, elUser,
				getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(),
				getPageSize());
		count = userDao.getDistributionStudentsCount(depTree, station,depid, elUser,
				getSessionIntValue(ElConstants.SESSION_ROLE));
		if (elusers != null) {
			for (int i = 0; i < elusers.size(); i++) {
				// elusers.get(i).setIntroom(
				// studyQuizDao.hasInQuizPaper(elusers.get(i).getId(),
				// examRoom.getId(), examPaper.getId()));
				elusers.get(i).setIntroom(
				// studyQuizDao.hasInQuizPaper(elusers.get(i).getId(),
						// examRoom.getId(), examPaper.getId(), examRoom
						// .getClassid())
						studyQuizDao.checkStudyExamPaper(
								elusers.get(i).getId(), examPaper.getId(),
								examRoom.getId(), examRoom.getClassid()));
			}
		}
		elUser = elUser == null ? new ELUser() : elUser;
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		gangweis = userDao.getBaseDatatByTypeid(4);
		dishis = userDao.getBaseDatatByTypeid(5);
		department.setId(depid);
		return "examroom_assignSelectings";
	}

	public String examroom_assignuser() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());// 获取考场信息
		
		examRooms = courseDao.getRoom(course.getId());
		String user[] = this.getUserids().split(",");
		for(int i=0;i<examRooms.size();i++){
			List<Integer> userid = studyQuizDao.getuserid(examRooms.get(i).getId());
			
			for(int j=0;j<userid.size();j++){
				int uid = userid.get(j);
				for(int s=0;s<user.length;s++){
					
					if(uid==Integer.valueOf(user[s])){
						ELUser eluser = userDao.getUserById(uid);
						this.setElmessage(eluser.getRealname()+"学员在该课程中已经分配了考场，请不要重复分配");
						return "error";
					}
					
				}
			}
			
		}
		// examPapers =
		// eroomDao.getEroomepwithusizes(examRoom.getId());//获取该考场中的试卷信息
		// if (examPapers.size() == 1) {
		// examPaper = examPapers.get(0);
		// }
		// if(examPaper.getId()==0){
		// return examroom_assignSearchInit();
		// }
		// 由于现在改的和考核考试差不多，所以试卷从页面中传过来，所以上面的注掉
		
		
		for (int i = 0; i < user.length; i++) {
			// if (!studyQuizDao.hasInQuizPaper(Integer.valueOf(user[i]),
			// examRoom//检测是否已经进入考场
			// .getId(), examPaper.getId())) {
			// studyQuizDao.intoQuizPaper(Integer.valueOf(user[i]),
			// examRoom//添加study_quizinfo信息（考试信息）
			// .getId(), examPaper.getId());
			// }
			// if (!eroomDao.checkuser2eroom(examRoom.getId(),
			// Integer//检查用户有没有分配到该考场
			// .valueOf(user[i]))) {
			// eroomDao.adduser2eroom(examRoom.getId(), Integer
			// .valueOf(user[i]));
			// }
			// if (!studyQuizDao.hasInQuizPaper(Integer.valueOf(user[i]),
			// examRoom// 检测是否已经进入考场
			// .getId(), examPaper.getId(), course.getClassid())) {
			// studyQuizDao.intoQuizPaper(Integer.valueOf(user[i]), examRoom//
			// 添加study_quizinfo信息（考试信息）
			// .getId(), examPaper.getId(), course.getClassid());
			// }
			// 检测该学员是否分配了该试卷
			if (!studyQuizDao.checkStudyExamPaper(Integer.valueOf(user[i]),
					examPaper.getId(), examRoom.getId(), course.getClassid())) {
				// 添加该学员到 学员试卷表中
				studyQuizDao.addStudyExamPaper(Integer.valueOf(user[i]),
						examPaper.getId(), examRoom.getId(), course
								.getClassid());
			}
			if (!eroomDao.checkuser2eroom(examRoom.getId(), Integer// 检查用户有没有分配到该考场
					.valueOf(user[i]), course.getClassid())) {
				eroomDao.adduser2eroom(examRoom.getId(), Integer
						.valueOf(user[i]), 1, course.getClassid(),
						CourseConstants.EXAMROOM_FPFS_SQ);
			}
		}
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_ADD, examRoom.getTitle() + "（添加学员）",
				ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		return examroom_assignSearchInit();
	}

	public String examroom_assign() throws ElException {
		// examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		//		
		// if (null != canAssignUsers)
		// for (int i = 0; i < canAssignUsers.size(); i++) {
		// if (!studyQuizDao.hasInQuizPaper(canAssignUsers.get(i).getId(),
		// examRoom.getId(), examPaper.getId())) {
		// studyQuizDao.intoQuizPaper(canAssignUsers.get(i).getId(),
		// examRoom.getId(), examPaper.getId());
		// }
		// if (!eroomDao.checkuser2eroom(examRoom.getId(), canAssignUsers
		// .get(i).getId())) {
		// eroomDao.adduser2eroom(examRoom.getId(), canAssignUsers
		// .get(i).getId());
		// }
		// }
		// examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		// elusers = userDao.getUserByDepId(department.getId(), sub_department,
		// elUser, getPageNow(), getPageSize());
		// if (elusers != null) {
		// for (int i = 0; i < elusers.size(); i++) {
		// elusers.get(i).setIntroom(
		// studyQuizDao.hasInQuizPaper(elusers.get(i).getId(),
		// examRoom.getId(), examPaper.getId()));
		// }
		// }
		// count = userDao.getUserByDepIdSize(department.getId(),
		// sub_department,
		// elUser);
		// return "examroom_assignSearchlist";
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		if (examPapers.size() == 1) {
			examPaper = examPapers.get(0);
		}
		if (null != bassignedUsers)
			for (int i = 0; i < bassignedUsers.size(); i++) {
				// if
				// (!studyQuizDao.hasInQuizPaper(bassignedUsers.get(i).getId(),
				// examRoom.getId(), examPaper.getId())) {
				// studyQuizDao.intoQuizPaper(bassignedUsers.get(i).getId(),
				// examRoom.getId(), examPaper.getId());
				// }
				// if (!eroomDao.checkuser2eroom(examRoom.getId(),
				// bassignedUsers
				// .get(i).getId())) {
				// eroomDao.adduser2eroom(examRoom.getId(), bassignedUsers
				// .get(i).getId());
				// }
				// if
				// (!studyQuizDao.hasInQuizPaper(bassignedUsers.get(i).getId(),
				// examRoom// 检测是否已经进入考场
				// .getId(), examPaper.getId(), examRoom
				// .getClassid())) {
				// studyQuizDao.intoQuizPaper(bassignedUsers.get(i).getId(),
				// examRoom// 添加study_quizinfo信息（考试信息）
				// .getId(), examPaper.getId(), examRoom
				// .getClassid());
				// }
				// 检测该学员是否分配了该试卷
				if (!studyQuizDao.checkStudyExamPaper(bassignedUsers.get(i)
						.getId(), examPaper.getId(), examRoom.getId(), examRoom
						.getClassid())) {
					// 添加该学员到 学员试卷表中
					studyQuizDao.addStudyExamPaper(bassignedUsers.get(i)
							.getId(), examPaper.getId(), examRoom.getId(),
							examRoom.getClassid());
				}
				if (!eroomDao.checkuser2eroom(examRoom.getId(), bassignedUsers
						.get(i).getId(), examRoom.getClassid())) {
					eroomDao.adduser2eroom(examRoom.getId(), bassignedUsers
							.get(i).getId(), 1, examRoom.getClassid(),
							CourseConstants.EXAMROOM_FPFS_SQ);
				}
			}
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_ADD, examRoom.getTitle() + "（添加学员）",
				ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		return examroom_assignSearchInit();
	}

	public String examroom_unassignuser() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		if (examPapers.size() == 1) {
			examPaper = examPapers.get(0);
		}
		// studyQuizDao.deleteQuiz(uid, roomid)
		// if (null != bassignedUsers)
		String user[] = this.getUserids().split(",");
		for (int i = 0; i < user.length; i++) {
			studyQuizDao.deleteQuiz(Integer.valueOf(user[i]), examRoom.getId(),
					examPaper.getId());
		}
		// return "examroom_assignuserlist";
		return examroom_assignSearchInit();
	}

	public String examroom_unassign() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		if (examPapers.size() == 1) {
			examPaper = examPapers.get(0);
		}
		// studyQuizDao.deleteQuiz(uid, roomid)
		// if (null != bassignedUsers)
		for (int i = 0; i < canAssignUsers.size(); i++) {
			studyQuizDao.deleteQuiz(canAssignUsers.get(i).getId(), examRoom
					.getId(), examPaper.getId());
		}
		// return "examroom_assignuserlist";
		return examroom_assignSearchInit();
	}

	public String examroom_assign_bkInit() throws ElException {/*
																 * examRooms =
																 * eroomDao
																 * .listErWithoutCourse(
																 * getSessionIntValue(ElConstants.SESSION_USERID),
																 * 0, eroomDao
																 * .listErWithoutCourseSize(getSessionIntValue(ElConstants.SESSION_USERID)));
																 * examRoom =
																 * eroomDao.getExamRoomByid(examRoom.getId());
																 * if (null !=
																 * examRoom_bk &&
																 * examRoom_bk.getId() >
																 * 0) {
																 * canAssignUsers =
																 * eroomDao.listCanAssignToRoomUsers_bk(examRoom
																 * .getId(),examRoom_bk.getId());
																 * bassignedUsers =
																 * eroomDao.listAssignToRoomUsers(examRoom.getId()); }
																 */
		return "examroom_assign_bk";
	}

	public String examroom_my_delete() throws ElException {
		eroomDao.deleteExamRoom(examRoom.getId());
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EROOM,
				ElLoggerConstants.LOG_TYPE_DELETE, examRoom.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		return "examroom_mylist";
	}

	/**
	 * 监考大厅考场列表页
	 * @return
	 * @throws ElException
	 */
	public String myExamroom_list() throws ElException {

		// examRooms = courseDao.listMyExamroomPages(
		// getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE),
		// getPageNow(),
		// getPageSize());
		// count = courseDao
		// .listMyExamroomPage(getSessionIntValue(ElConstants.SESSION_USERID),getSessionIntValue(ElConstants.SESSION_ROLE));
		examRooms = courseDao.listMyExamroomPages(
				getSessionIntValue(ElConstants.SESSION_USERID),
				getSessionIntValue(ElConstants.SESSION_ROLE), examRoom,
				getPageNow(), getPageSize());
		count = courseDao.listMyExamroomPage(
				getSessionIntValue(ElConstants.SESSION_USERID),
				getSessionIntValue(ElConstants.SESSION_ROLE), examRoom);

		return "myExamroom_list";
	}
	/**
	 * 监考大厅考场列表页
	 * @return
	 * @throws ElException
	 */
	public String eroomStudyInfo() throws ElException {
		elusers = eroomDao.listEroomStudyInfo(examRoom.getId(), myroom.getStatus(), getPageNow(), getPageSize());
		count = eroomDao.listEroomStudyInfoSize(examRoom.getId(), myroom.getStatus());
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		return "eroomStudyInfo";
	}

	public String examroom_myalllist() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		// int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		// String title = examRoom == null ? "" : examRoom.getTitle().trim();

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		int libid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
				: eroomLib.getId();

		examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, libid,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.valid != 9", getPageNow(), getPageSize());
		count = eroomDao.listMyDepExamRoomSize(eroomLibTree, libid,
				getSessionIntValue(ElConstants.SESSION_ROLE),
				" and er.valid != 9");

		return "examroom_myalllist";
	}

	// 我创建的场次
	public String examroom_mylist() throws ElException {
		if (examRoom == null) {
			examRoom = new ExamRoom();
			examRoom.setValid(-1);
			examRoom.setEroomLib(new EroomLib(1));
			examRoom.setClassid(-1);
		}
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		String title = examRoom == null ? "" : examRoom.getTitle() == null ? ""
				: examRoom.getTitle().trim();
		if (examRoom != null) {
			examRooms = eroomDao.listMyExamRoom(userid, examRoom, getPageNow(),
					getPageSize());
			count = eroomDao.listMyExamRoomCount(userid, examRoom);
		} else {
			examRooms = eroomDao.listMyExamRoom(userid, title, getPageNow(),
					getPageSize());
			count = eroomDao.listMyExamRoomSize(userid, title);
		}
		// count = eroomDao.listMyExamRoomSize(userid, title);
		// count = eroomDao.listMyExamRoomCount(userid, examRoom);

		//
		/*
		 * examRooms =
		 * eroomDao.combinationSearchExamroom(examRoom,eroomLibTree,getSessionIntValue(ElConstants.SESSION_ROLE),
		 * Lid,getPageNow(),getPageSize());
		 * count=eroomDao.combinationSearchExamroomCount(examRoom,eroomLibTree,getSessionIntValue(ElConstants.SESSION_ROLE),Lid);
		 */

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		return "examroom_mylist";
	}

	// 全部的考场
	public String examroom_alllist() throws ElException {
		// int erid = 1;
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
		// if(sublibs != 0){//搜索当前节点
		// erid = examRoom != null && examRoom.getEroomLib() != null ?
		// examRoom.getEroomLib().getId(): 1;
		// }else{
		// erid=eroomLibTree.getId();
		// }
		// examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// "",examRoom, getPageNow(), getPageSize());
		// count = eroomDao.listMyDepExamRoomSize(eroomLibTree, erid,
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// "", examRoom);
		examRooms = eroomDao.listExamRoom(examRoom.getEroomLib(), sublibs, sqlw,
				examRoom, getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize(examRoom.getEroomLib(), sublibs, sqlw,
				examRoom);
		return "examroom_alllist";
	}
	
	/**
	 * 查询创建的问卷列表
	 * @return
	 * @throws ElException
	 */
	public String questionnaireList() throws ElException {
		// int erid = 1;
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
		//	examRoom.setClassid(-2);
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
		examRooms = eroomDao.listExamRoom2(examRoom.getEroomLib(), sublibs, sqlw,
				examRoom, getPageNow(), getPageSize());
		for(int i=0;i<examRooms.size();i++){
			int userid = examRooms.get(i).getCreater().getId();
			elUser = userDao.getUserById2(userid);
			String depname = departmentDao.getDepById(elUser.getDepartment().getId()).getName();
			elUser.setDanwei(depname);
			examRooms.get(i).setCreater(elUser);
		}
		count = eroomDao.listExamRoomSize2(examRoom.getEroomLib(), sublibs, sqlw,
				examRoom);
		return "questionnaireList";
	}
	
	
	/**
	 * 问卷统计
	 * @return
	 * @throws ElException
	 */
	public String questionnaire_searchList() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom2(eroomLib, sublibs,
				" and er.valid not in (0,9)", examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.listExamRoomSize2(eroomLib, sublibs,
				" and er.valid not in (0,9)", examRoom);
		for(int i=0;i<examRooms.size();i++){
			int size = eroomDao.getStudyQueSize(examRooms.get(i).getId());
			examRooms.get(i).setUserSize(size);//已参加人数
			double percent = (double)size / (double)examRooms.get(i).getUsersize();
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			nt.format(percent);
			examRooms.get(i).setPercent(nt.format(percent));
		}
		return "questionnaire_searchList";
	}

	/**
	 * 问卷结果统计
	 * @return
	 * @throws ElException
	 */
	public String questionnaireResult() throws ElException{
		int epid = eroomDao.getEpidByRoomid(examRoom.getId());
		List<Integer> questionids = eroomDao.getQuestionids(epid);
		questionRankings = new ArrayList<QuestionRanking>();
	//	List<Subject> subs = new ArrayList<Subject>();
		for(int i=0;i<questionids.size();i++){
			String subs = new String();
			questionRanking=pollDao.questionnaireResult(questionids.get(i));
			String sub[] = questionRanking.getQuestion().getSubject().split("-=SpEl=-");
			questionRanking.getQuestion().getQtype();
			char c = 'A';
			for(int s=0;s<sub.length;s++){
			//	Subject subject = new Subject();
				String subject = c+":"+sub[s];
				subs += "  "+subject;
				c++;
			}
			questionRanking.getQuestion().setSubjects(subs);
			questionRankings.add(questionRanking);
		}
		
		return "questionnaireResult";
	}
	
	public String examroom_listbyc() throws ElException {
		String classidStr = getRequest().getParameter("classId");
		int classid = 0;
		if (classidStr != null) {
			classid = Integer.parseInt(classidStr);
		}
		course = courseDao.getCourseById(course.getId());
		// examRooms = eroomDao.listExamRoom(course.getId());
		examRooms = eroomDao.listExamRoom2(course.getId(), classid);
		return "examroom_listbyc";
	}

	public String examroom_choose_listbycInit() throws ElException {
		String classidStr = getRequest().getParameter("classId");
		int classid = 0;
		if (classidStr != null) {
			classid = Integer.parseInt(classidStr);
		}
		if(course.getId()>0){
			course = courseDao.getCourseById(course.getId());
		}
		// examRooms = eroomDao.listExamRoom(course.getId());
		examRooms = eroomDao.listExamRoom2(course.getId(), classid);
		examRoom = eroomDao.getClassBindingCourseByRoomId(getClassId(), course
				.getId());
		classId = getClassId();
		examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());// 获取该考场中的试卷信息
		if (examPapers != null && examPapers.size() == 1) {
			examPaper = examPapers.get(0);
		}
		elclass = classDao.getClassById(classid);
		//是否具有审核权限
		elclass.setXxCount(roleDao.checkRolefunc(getSessionIntValue(ElConstants.SESSION_ROLE),"elclass_sh_list")?1:0);
		return "examroom_choose_listbycInit";
	}

	public String examroom_audit() throws ElException {// 申请考场修改审核

		int avalid = examRoom == null ? -1 : examRoom.getAvalid();
		if (avalid == -1)
			return "examroom_audit_success";
		erAuditdes = eroomDao.getExamRoomAuditDescribesByRoomid(examRoom
				.getId());
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
		examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
				.getId()));
		examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
		examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
		myrooms = eroomDao.listEroomtesters(examRoom.getId(), getPageNow(),
				getPageSize());
		count = eroomDao.listEroomtesterssize(examRoom.getId());
		return "examroom_audit";
	}

	public String examroom_modify_application() throws ElException {
		eroomDao.examRoomavalid(examRoom.getId(), examRoom.getAvalid());// 申请考场修改状态
		erAuditdes.setExamroom(new ExamRoom(examRoom.getId()));
		erAuditdes.setUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_ROLE)));
		ExamRoomAuditDescribes erAuditde = eroomDao
				.getExamRoomAuditDescribesByRoomid(examRoom.getId());
		if (erAuditde.getId() != 0) {
			erAuditdes.setId(erAuditde.getId());
			erAuditdes.setReplycontent(erAuditde.getReplycontent());
			eroomDao.UExamRoomAuditContents(erAuditdes);
		} else {
			eroomDao.openExamRoomAudit(erAuditdes);
		}
		return "examroom_modify_application";
	}

	public String examroom_modify_throughthe() throws ElException {
		eroomDao.examRoomavalid(examRoom.getId(), examRoom.getAvalid());// 审核考场修改状态
		return "examroom_modify_throughthe";
	}

	// /* 考试场次管理=-=------------------

	public String examroom_listInit() throws ElException {
		return "examroom_listInit";
	}

	public String examroom_list() throws ElException {
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		// int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
		// .getId();

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			ctypeTree = ctypeDao.getCourseLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}

//		int ctid = ctype == null ? 1 : ctype.getId();

		// if (course_sourse == 1)// 上级部门分配的
		// {
		// courses = courseDao.listAllCourseFromSuper(depid, course.getName(),
		// getPageNow(), getPageSize());
		// count = courseDao.listAllCourseSizeFromSuper(depid, course
		// .getName());
		// }
		// if (course_sourse == 2)// 本部门的资源
		// {
		// courses = courseDao.listAllCourseFromThis(depid, course.getName(),
		// ctid, getPageNow(), getPageSize(),
		// CourseConstants.COURSE_STATUS_OPEN);
		// count = courseDao.listAllCourseSizeFromThis(depid,
		// course.getName(), ctid, CourseConstants.COURSE_STATUS_OPEN);
		// }
		// getPageSize()=getPageSize()==0?10:getPageSize();
		// ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		// ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true,
		// String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
		// true, "COURSE_USE_TYPE");
		String name = course == null ? "" : course.getName();

//		courses = courseDao.examroom_listAllCourseFromThis(ctypeTree, depid,
//				getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,
//				getPageNow(), getPageSize(),
//				CourseConstants.COURSE_STATUS_HASOPENED);
//		count = courseDao.examroom_listAllCourseSizeFromThis(ctypeTree, depid,
//				getSessionIntValue(ElConstants.SESSION_ROLE), name, ctid,
//				CourseConstants.COURSE_STATUS_HASOPENED);
		if(ctype==null||ctype.getId()<=0){
		  ctype=ctypeTree;
		}else{
		  ctype=ctypeDao.getCtypeById(ctype.getId());
		}
		courses = courseDao.listCourseFromThisStatus(ctype,name,getPageNow(), getPageSize(),"0,1,2,3,4,5,6,7,8,9");
		count = courseDao.listCourseSizeFromThisStatus(ctype,name,"0,1,2,3,4,5,6,7,8,9");
		return "examroom_list";
	}

	/**
	 * Description: 考试批次管理
	 * 
	 * @Version1.0 2011-9-5 上午11:03:53 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 */

	public String eroom_batchlib_list() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			erbatchLibTree = eroomDao.getErbatchLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			erbatchLibTree = eroomDao.getErbatchLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);

		}

		return "eroom_batchlib_list";
	}

	public String eroom_batchlib_deleteInit() throws ElException {
		erbatchLib = eroomDao.getErbatchLibById(erbatchLib.getId());
		return "eroom_batchlib_delete";
	}

	public String eroom_batchlib_delete() throws ElException {
		// if (course_sourse == 1) {
		// // 并入上级
		//			
		// } else {
		// // 一起删除
		//
		// }
		if (erbatchLib.getId() == 1) {
			setElmessage("不能删除根类别");
			return "error";
		}
		eroomDao.deleteErbatchLib(erbatchLib.getId());
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("Erbatch_lib");
		return "eroom_batchlib_delete_success";
	}

	public String eroom_batchlib_addInit() throws ElException {
		// eroom_batchLibTree =
		// eroomDao.geteroom_batchLibTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			erbatchLibTree = eroomDao.getErbatchLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);

		else {
			erbatchLibTree = eroomDao.getErbatchLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);

		}
		return "eroom_batchlib_add";
	}

	public String eroom_batchlib_add() throws ElException {
		eroomDao.addErbatchLib(erbatchLib);
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("erbatch_lib");
		if (null != erbatchLib.getOpusers()) {
			for (int i = 0; i < erbatchLib.getOpusers().size(); i++) {
				// + erbatchLib.getOpusers().get(i).getId());
				if (!eroomDao.checkErblOpUsers("op", erbatchLib.getOpusers()
						.get(i).getId(), erbatchLib.getId()))
					eroomDao.addErblOpusers("op", erbatchLib.getOpusers()
							.get(i).getId(), erbatchLib.getId());
			}
		}
		if (null != erbatchLib.getUseusers()) {
			for (int i = 0; i < erbatchLib.getUseusers().size(); i++) {
				// + erbatchLib.getUseusers().get(i).getId());
				if (!eroomDao.checkErblOpUsers("op", erbatchLib.getUseusers()
						.get(i).getId(), erbatchLib.getId()))
					eroomDao.addErblOpusers("op", erbatchLib.getUseusers()
							.get(i).getId(), erbatchLib.getId());
			}
		}
		return "eroom_batchlib_add_success";
	}

	public String eroom_batchlib_view() throws ElException {
		erbatchLibTree = eroomDao.getErbatchLibTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		erbatchLib = eroomDao.getErbatchLibById(erbatchLib.getId());
		erbatchLib
				.setOpusers(eroomDao.getErblOpUsers("op", erbatchLib.getId()));
		erbatchLib.setUseusers(eroomDao.getErblOpUsers("op", erbatchLib
				.getId()));
		return "eroom_batchlib_view";
	}

	public String eroom_batchlib_alterInit() throws ElException {
		erbatchLib = eroomDao.getErbatchLibById(erbatchLib.getId());
		// erbatchLibTree = eroomDao.getErbatchLibTree(ElConstants.TREE_ROOT,
		// erbatchLib.getId(), false);
		erbatchLibTree = eroomDao.getErbatchLibTree(ElConstants.TREE_ROOT, -3,
				false);// 第2个参数是不显示的id及下级节点(现在需求是全部显示)
		erbatchLib
				.setOpusers(eroomDao.getErblOpUsers("op", erbatchLib.getId()));
		erbatchLib.setUseusers(eroomDao.getErblOpUsers("op", erbatchLib
				.getId()));
		return "eroom_batchlib_alter";
	}

	public String erblib_delete_user() throws ElException {
		eroomDao.deleteErblOpusers(optype, elUser.getId(), erbatchLib.getId());
		return null;
	}

	public String eroom_batchlib_alter() throws ElException {
		if (erbatchLib.getId() == 1) {
			erbatchLib.setParent(new EroomBatchLib(0));
		}
		eroomDao.alterErbatchLib(erbatchLib);
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("Erbatch_lib");
		if (null != erbatchLib.getOpusers()) {
			for (int i = 0; i < erbatchLib.getOpusers().size(); i++) {
				// + erbatchLib.getOpusers().get(i).getId());
				if (!eroomDao.checkErblOpUsers("op", erbatchLib.getOpusers()
						.get(i).getId(), erbatchLib.getId()))
					eroomDao.addErblOpusers("op", erbatchLib.getOpusers()
							.get(i).getId(), erbatchLib.getId());
			}
		}
		if (null != erbatchLib.getUseusers()) {
			for (int i = 0; i < erbatchLib.getUseusers().size(); i++) {
				// + erbatchLib.getUseusers().get(i).getId());
				if (!eroomDao.checkErblOpUsers("op", erbatchLib.getUseusers()
						.get(i).getId(), erbatchLib.getId()))
					eroomDao.addErblOpusers("op", erbatchLib.getUseusers()
							.get(i).getId(), erbatchLib.getId());
			}
		}
		return "eroom_batchlib_alter_success";
	}

	public String eroom_batch_list() throws ElException {
		erbatchs = eroomDao.listErbatch(getPageNow(), getPageSize());
		count = eroomDao.listErbatchCount();
		return "eroom_batch_list";
	}

	public String eroom_batch_delete() throws ElException {
		eroomDao.deleteErbatch(erbatch.getId());
		return "eroom_batch_list";
	}

	public String eroom_batch_addInit() throws ElException {
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			erbatchLibTree = eroomDao.getErbatchLibTree(ElConstants.TREE_ROOT,
//					ElConstants.TREE_FIANL, true);
//
//		else {
//			erbatchLibTree = eroomDao.getErbatchLibTree(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op",
//					ElConstants.TREE_FIANL, true);
//
//		}
		return "eroom_batch_add";
	}

	public String eroom_batch_add() throws ElException {
		erbatch.setCreater(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		eroomDao.addErbatch(erbatch);
		if (null != erbatch.getErooms())
			for (int i = 0; i < erbatch.getErooms().size(); i++) {
				if (!eroomDao.checkErbatchRoom(erbatch.getErooms().get(i)
						.getId(), erbatch.getId()))
					eroomDao.addErbatchRoom(erbatch.getErooms().get(i).getId(),
							erbatch.getId());

			}
		return "eroom_batch_list";
	}

	public String eroom_batch_view() throws ElException {
		erbatch = eroomDao.getErbatchById(erbatch.getId());
		if (null != erbatch) {
			erbatch.setErooms(eroomDao.listErbatchRooms(erbatch.getId()));
		}
		return "eroom_batch_view";
	}

	public String eroom_batch_alterInit() throws ElException {
		erbatch = eroomDao.getErbatchById(erbatch.getId());
		erbatch.setErooms(eroomDao.listErbatchRooms(erbatch.getId()));

//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			erbatchLibTree = eroomDao.getErbatchLibTree(ElConstants.TREE_ROOT,
//					ElConstants.TREE_FIANL, true);
//
//		else {
//			erbatchLibTree = eroomDao.getErbatchLibTree(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op",
//					ElConstants.TREE_FIANL, true);
//
//		}
		return "eroom_batch_alter";
	}

	private String input_name;

	public String eroom_batch_alter() throws ElException {
		eroomDao.alterErbatch(erbatch);
		if (null != erbatch.getErooms())
			for (int i = 0; i < erbatch.getErooms().size(); i++) {
				if (!eroomDao.checkErbatchRoom(erbatch.getErooms().get(i)
						.getId(), erbatch.getId()))
					eroomDao.addErbatchRoom(erbatch.getErooms().get(i).getId(),
							erbatch.getId());

			}
		return "eroom_batch_list";
	}

	public String eroom_batch2room_delete() throws ElException {
		eroomDao.deleteErbatchRoom(examRoom.getId(), erbatch.getId());
		return null;
	}

	public String eroom_batch_room() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		return "eroom_batch_room";
	}

	public String eroom_batch_room_list() throws ElException {
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
//		String title = examRoom == null ? "" : examRoom.getTitle().trim();
//		examRooms = eroomDao.listMyDepExamRoom(depid, title, getPageNow(),
//				getPageSize());
//		count = eroomDao.listMyDepExamRoomSize(depid, title);
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
		examRooms = eroomDao.listExamRoom(examRoom.getEroomLib(), sublibs, sqlw,
				examRoom, getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize(examRoom.getEroomLib(), sublibs, sqlw,
				examRoom);
		return "eroom_batch_room_list";
	}

	public String class_choose_examroom() throws ElException {
		// 更新关联表状态
		eroomDao.setClassBindingCourse(getClassId(), course.getId(), examRoom
				.getId());
		classId = getClassId();
		// 保存该考场的绑定信息
		if (eroomDao.checkExamRoomIsBand(course.getId(), classId)) {
			// 解除绑定
			eroomDao.cancelExamRoomBandClass(course.getId(), classId);
		}
		// 绑定
		eroomDao.updateExamRoomInBandClassid(examRoom.getId(), classId);
		// 更新sqiid
//		eroomDao
//				.updateStudySqiidInit(examRoom.getId(), course.getId(), classId);
		// 绑定的时候如果是申请式的，那么获取该培训班中所有已经分配的人员，然后分配到该考场的所有试卷中
		// 1.获取该考场的所有试卷
		// 2.获取该培训班的所有人员
		// 3.分配
		if (elclass.getIsApplication() == 1) {
			List<ExamPaper> examPapers = eroomDao.getEroomepwithusizes(examRoom
					.getId());// 获取该考场中的所有试卷信息
			List<ELUser> userList = new StudyClassDaoImpl()
					.listStudyByClass(classId);
			for (ELUser user : userList) {
				for (int j = 0; j < examPapers.size(); j++) {
					// if (!studyQuizDao.hasInQuizPaper(user.getId(),
					// examRoom.getId(), // 检测是否已经进入考场
					// examPapers.get(j).getId(),classId)) {
					// studyQuizDao.intoQuizPaper(user.getId(),
					// examRoom.getId(),
					// examPapers.get(j).getId(), classId);
					// }
					//判断试卷是否已被删除
					if(examPapers.get(j).getStatus()!=1){
						// 检测该学员是否分配了该试卷
						if (!studyQuizDao.checkStudyExamPaper(user.getId(),
								examPapers.get(j).getId(), examRoom.getId(),
								classId)) {
							// 添加该学员到 学员试卷表中
							studyQuizDao.addStudyExamPaper(user.getId(), examPapers
									.get(j).getId(), examRoom.getId(), classId);
						}
					}
				}
				if (!eroomDao.checkuser2eroom(examRoom.getId(), // 检查用户有没有分配到该考场
						user.getId(), classId)) {
					eroomDao.adduser2eroom(examRoom.getId(), user.getId(), 1,
							classId, CourseConstants.EXAMROOM_FPFS_SQ);
				}
			}
		}
		if (classId == 0) {
			return "examroom_listbyc";
		}
		//if (Return != null) {
			elclass = classDao.getClassById(classId);
			course = courseDao.getCourseById(course.getId());
			examRoom = eroomDao.getExamRoomByid(examRoom.getId());
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_ALTER, elclass.getName()
							+ " -> " + course.getName() + " 修改绑定考场为("
							+ examRoom.getTitle() + ")",
					ElLoggerConstants.LOG_RES_SUCC);
		//	return Return;
		//}
		//return "class_choose_examroom";
		return "examroom_choose_listbycInit";
	}

	public String examroom_prima_sh_alterInit() throws ElException {
		// 更新关联表状态
		examRoom.setId(examRoom.getId());
		return "examroom_prima_sh_alter";
	}

	Question question;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String dazi_test() throws ElException {
		if (question == null) {
			question = new Question();
			question
					.setStuAnswer("22-=SpEl=-300-=SpEl=-1000-=SpEl=-dsf");
			question
					.setRulestring("50-=SpRule-50-=SpRule-10-=SpRule-0:30:30:60:70:31:40:40:70:80:41:60:20:50:65:");
			// question
			// .setRulestring("30-=SpRule-20-=SpRule-10-=SpRule-0:100:50:60");
			question.setScore(100);
		}
		if (getRequest().getParameter("course_sourse") != null) {
			this.course_sourse = getRequest().getParameter("course_sourse")
					.trim().equals("") ? 0 : Integer.parseInt(getRequest()
					.getParameter("course_sourse").trim());
		} else {
			this.course_sourse = 25;
		}
		classname = eroomDao.getDztest(question, course_sourse);
		return "dazi_test";
	}

	public String dazi_test_sub() throws ElException {
		try {
			String title = "";
			if (question.getMystatus() == 1) {
				int vmax = Integer.parseInt(question.getStuAnswers()[2])
						/ Integer.parseInt(question.getRules()[2]) + 10;
				vmax = vmax % 2 == 0 ? vmax : vmax + 1;
				String[] vs = new String[vmax / 2];
				int[] as = new int[question.getDazirule().length];
				for (int i = 0; i < question.getDazirule().length; i++) {
					as[i] = Integer.parseInt(question.getDazirule()[i][0])
							+ (int) (Math.random() * (Integer.parseInt(question
									.getDazirule()[i][1]) - Integer
									.parseInt(question.getDazirule()[i][0])));
				}
				double[][] myss = new double[as.length][vmax / 2];
				for (int i = 0; i < vmax; i = i + 2) {
					vs[i / 2] = i + "";
					int t = Integer.parseInt(question.getRules()[2]);
					int r = t * i;
					title = "速度增长（固定打字时间）("+t+"分钟)";
					// r = ((int)(t* Math.random())) *i;
					question.setStuAnswer(question.getStuAnswers()[0]
							+ ElConstants.optSplit + r + ElConstants.optSplit
							+ question.getStuAnswers()[2] + ElConstants.optSplit
							+ question.getStuAnswers()[3]
							+ ElConstants.optSplit);
					for (int j = 0; j < as.length; j++) {
						classname = eroomDao.getDztest(question, as[j]);
						myss[j][i / 2] = Double.parseDouble(classname
								.split(":")[0]);
					}
				}
				String ss[] = new String[as.length];
				for (int i = 0; i < ss.length; i++) {
					ss[i] = as[i] + "岁";
				}
				ChartUtil.dazioutput(title, getResponse().getOutputStream(),"速度",
						ss, vs, myss);
			} else if (question.getMystatus()==0) {
				int r = Integer.parseInt(question.getStuAnswers()[1]);
				int t = Integer.parseInt(question.getRules()[2]);
				String[] ts = new String[t];
				int[] as = new int[question.getDazirule().length];
				for (int i = 0; i < question.getDazirule().length; i++) {
					as[i] = Integer.parseInt(question.getDazirule()[i][0])
							+ (int) (Math.random() * (Integer.parseInt(question
									.getDazirule()[i][1]) - Integer
									.parseInt(question.getDazirule()[i][0])));
				}
				double[][] myss = new double[as.length][t];
				for (int i = 1; i < t+1; i ++) {
					ts[i-1] = i + "";
//					int t = Integer.parseInt(question.getRules()[2]);
//					int r = ts[i] * i;
					title = "时间增长（固定打对字数）("+r+"字)";
					int v = r /i;
					// r = ((int)(t* Math.random())) *i;
					question.setStuAnswer(question.getStuAnswers()[0]
							+ ElConstants.optSplit + r + ElConstants.optSplit
							+ v + ElConstants.optSplit
							+ question.getStuAnswers()[3]
							+ ElConstants.optSplit);
					for (int j = 0; j < as.length; j++) {
						classname = eroomDao.getDztest(question, as[j]);
						myss[j][i -1] = Double.parseDouble(classname
								.split(":")[0]);
					}
				}
				String ss[] = new String[as.length];
				for (int i = 0; i < ss.length; i++) {
					ss[i] = as[i] + "岁";
				}
				ChartUtil.dazioutput(title, getResponse().getOutputStream(),"时间",
						ss, ts, myss);
			}else {

				int v = Integer.parseInt(question.getStuAnswers()[2]);
				int t = Integer.parseInt(question.getRules()[2]);
				String[] ts = new String[t];
				int[] as = new int[question.getDazirule().length];
				for (int i = 0; i < question.getDazirule().length; i++) {
					as[i] = Integer.parseInt(question.getDazirule()[i][0])
							+ (int) (Math.random() * (Integer.parseInt(question
									.getDazirule()[i][1]) - Integer
									.parseInt(question.getDazirule()[i][0])));
				}
				double[][] myss = new double[as.length][t];
				for (int i = 1; i < t+1; i ++) {
					ts[i-1] = i + "";
//					int t = Integer.parseInt(question.getRules()[2]);
//					int r = ts[i] * i;
					title = "时间增长（固定打字速度）("+v+"字/分钟)";
					int r = v * i;
					// r = ((int)(t* Math.random())) *i;
					question.setStuAnswer(question.getStuAnswers()[0]
							+ ElConstants.optSplit + r + ElConstants.optSplit
							+ v + ElConstants.optSplit
							+ question.getStuAnswers()[3]
							+ ElConstants.optSplit);
					for (int j = 0; j < as.length; j++) {
						classname = eroomDao.getDztest(question, as[j]);
						myss[j][i -1] = Double.parseDouble(classname
								.split(":")[0]);
					}
				}
				String ss[] = new String[as.length];
				for (int i = 0; i < ss.length; i++) {
					ss[i] = as[i] + "岁";
				}
				ChartUtil.dazioutput(title, getResponse().getOutputStream(),"时间",
						ss, ts, myss);
			
			}
		} catch (Exception e) {
			logger.error("打字测试，生成图表错误",e);
			throw new ElException(e);
		}

		return null;
	}

	public String course_erwithout_view() throws ElException {
		elclass = classDao.getClassById(elclass.getId());
		course = courseDao.getCourseById(course.getId());
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examRoom.setInvigilators(eroomDao.getEroomUsers("rinvigilators",
				examRoom.getId()));
		examRoom.setAppraises(eroomDao.getEroomUsers("rappraises", examRoom
				.getId()));
		examRoom.setValids(eroomDao.getEroomUsers("valids", examRoom.getId()));
		examRoom.setExampapers(eroomDao.getEroomeps(examRoom.getId()));
		if (eroomDao.getEroomUsers("valids", examRoom.getId()).size() != 0) {
			examRoom.setUsersize(1);
		} else {
			examRoom.setUsersize(-1);
		}
		myrooms = eroomDao.listEroomtesters(examRoom.getId(), getPageNow(),
				getPageSize());
		count = eroomDao.listEroomtesterssize(examRoom.getId());
		// 处理ip段显示
		List<String> ipStratList = new ArrayList<String>();
		List<String> ipEndList = new ArrayList<String>();
		if (examRoom.getIpStart() != null && examRoom.getIpEnd() != null) {
			String[] ipStart = examRoom.getIpStart().split("_");
			String[] ipEnd = examRoom.getIpEnd().split("_");
			for (int i = 0; i < ipStart.length; i++) {
				ipStratList.add(ipStart[i]);
			}
			for (int i = 0; i < ipEnd.length; i++) {
				ipEndList.add(ipEnd[i]);
			}
			getRequest().setAttribute("ipStratList", ipStratList);
			getRequest().setAttribute("ipEndList", ipEndList);
		}

		if (Return.equals("falsa")) {
			return "course_erwithout_view_details";
		}
		if (Return.equals("true")) {
			return "course_erwithout_view";
		}
		if (Return.equals("delete")) {
			return "course_erwithout_view_delete";
		}
		if (Return.equals("fuxuan")) {// 复核选拨
			return "course_erwithout_view_fuxuan";
		}
		return "course_erwithout_view_details";
	}

	/*
	 * 备注
	 */
	public String CRE_noteInit() throws ElException {
		crelist = eroomDao.getById(examRoom.getId(), "考场备注");

		return "CRE_note";
	}

	public String CRE_notelistInit() throws ElException {
		if (examRoom != null && examRoom.getId() != 0 && course != null
				&& course.getId() != 0 && elclass != null
				&& elclass.getId() != 0) {
			crelist = eroomDao.getById(elclass.getId(), course.getId(),
					examRoom.getId(), "结业考场备注");
		} else {
			if (examRoom != null && examRoom.getId() != 0) {
				crelist = eroomDao.getById(examRoom.getId(), "考场备注");
			}
			if (course != null && course.getId() != 0) {
				crelist = eroomDao.getById(course.getId(), "课程备注");
			}
			if (elclass != null && elclass.getId() != 0) {
				crelist = eroomDao.getById(elclass.getId(), "培训班备注");
			}
		}
		if (examRoom != null && examRoom.getId() != 0) {
			examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		}
		if (course != null && course.getId() != 0) {
			course=courseDao.getCourseById(course.getId());
		}
		if (elclass != null && elclass.getId() != 0) {
			elclass=classDao.getClassById(elclass.getId());
		}
		return "CRE_notelist";
	}

	public String CRE_addNotes() throws ElException {
		if (examRoom != null && examRoom.getId() != 0) {
			examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		}
		if (course != null && course.getId() != 0) {
			course = courseDao.getCourseById(course.getId());
		}
		if (elclass != null && elclass.getId() != 0) {
			elclass = classDao.getClassById(elclass.getId());
		}
		return "CRE_addNotes";
	}

	public String CRE_note_addInit() throws ElException {
		if (examRoom != null && examRoom.getId() != 0) {
			examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		}
		if (course != null && course.getId() != 0) {
			course=courseDao.getCourseById(course.getId());
		}
		if (elclass != null && elclass.getId() != 0) {
			elclass=classDao.getClassById(elclass.getId());
		}
		return "CRE_note_add";
	}

	public String CRE_note_add() throws ElException {
		cre_note.setUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		cre_note
				.setEroom((examRoom != null && examRoom.getId() != 0) ? new ExamRoom(
						examRoom.getId())
						: new ExamRoom(0));
		cre_note
				.setCourse((course != null && course.getId() != 0) ? new Course(
						course.getId())
						: new Course(0));
		cre_note
				.setElclass((elclass != null && elclass.getId() != 0) ? new ElClass(
						elclass.getId())
						: new ElClass(0));

		if (examRoom != null && examRoom.getId() != 0 && course != null
				&& course.getId() != 0 && elclass != null
				&& elclass.getId() != 0) {
			cre_note.setType("结业考场备注");
		} else {
			if (examRoom != null && examRoom.getId() != 0) {
				cre_note.setType("考场备注");
			}
			if (course != null && course.getId() != 0) {
				cre_note.setType("课程备注");
			}
			if (elclass != null && elclass.getId() != 0) {
				cre_note.setType("培训班备注");
			}
		}
		eroomDao.addCRE_note(cre_note);

//		if (!Return.equals("")) {
//			return Return;
//		}
		if(true){
			return "CRE_notelistInit";
		}
		// if(Return.equals("close")){
		setElmessage(cre_note.getType() + " 添加成功");
		return "error";
		// }
		// return Return;
	}
	//复制考场
	public String copy_Eroom() throws ElException {
		int status = eroomDao.copyEroom(examRoom.getId());
		if(status > 0){
			examRoom.setId(status);
			return "copy_Eroom";
		}else{
			setElmessage("复制考场错误！");
			return "erro";
		}
	}
	/**
	 * 考场试卷剔除人员初始化
	 * @return
	 * @throws ElException
	 */
	public String eroomEpDeleteUserInit() throws ElException{
		return "importUserDelEroomEp";
	}
	
	/**
	 * 考场试卷剔除人员
	 * @return
	 * @throws ElException
	 */
	public String eroomEpDeleteUser() throws ElException{
		if (null != st) {
			if (!J2EEFileUtil.getExtention(stFileName).toLowerCase().equals(
					"xls")) {
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return "importUserDelEroomEp";
			}
			if (st.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "importUserDelEroomEp";
			} else {
				String msg=UserExcelUtil.writeUserDeleteEroomEp(st,examRoom.getId(),examPaper.getId());
				setElmessage(msg);
				return "importUserDelEroomEp";
			}
		} else {
			setElmessage("请输入上传文件");
			return "importUserDelEroomEp";
		}
	}
	
	/**
	 * 考场试卷导入人员初始化
	 * @return
	 * @throws ElException
	 */
	public String eroomEpWriteUserInit() throws ElException{
		return "importUserToEroomEp";
	}
	
	/**
	 * 考场试卷导入人员
	 * @return
	 * @throws ElException
	 */
	public String eroomEpWriteUser() throws ElException{
		if (null != st) {
			if (!J2EEFileUtil.getExtention(stFileName).toLowerCase().equals(
					"xls")) {
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return "importUserToEroomEp";
			}
			if (st.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "importUserToEroomEp";
			} else {
				String msg=UserExcelUtil.writeUserToEroomEp(st,examRoom.getId(),examPaper.getId());
				setElmessage(msg);
				return "importUserToEroomEp";
			}
		} else {
			setElmessage("请输入上传文件");
			return "importUserToEroomEp";
		}
	}
	/**
	 * 可申请的考场人员审核
	 * @return
	 * @throws ElException
	 */
	public String examroom_userAudit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		sublibs = examRoom == null ? 1 : sublibs;
		examRooms = eroomDao.listExamRoom(eroomLib, sublibs,
				" and er.valid in (5) and erg.isAudit=1 ", examRoom, getPageNow(),
				getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, sublibs,
				" and er.valid in (5) and erg.isAudit=1 ", examRoom);
		return "examroom_userAudit";
	}

	/**
	 * 查看考场人员详情(可申请考场人员审核时有调用)
	 * @return
	 * @throws ElException
	 */
	public String examroom_auditUserlist() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		int status = -1;
		if(elUser != null)
			status = elUser.getActive();
		if(department==null||department.getId()<=0){
			department=depTree;
			sublibs=1;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		boolean consub = sublibs==1?true:false;
		myrooms = eroomDao.listEroomNoAuditUser(  elUser,  examRoom,department,consub,status, getPageNow(),
				getPageSize());
		count = eroomDao.listEroomNoAuditUserSize( elUser,  examRoom,department,consub,status);
		return "examroom_auditUserlist";
	}
	/**
	 * 更新学员考场报名状态(通过和不通过操作)
	 * @return
	 * @throws ElException
	 */
	public String updateStudyRoomStatus() throws ElException {
		if(myroom.getStatus()==3){//通过
			//判断是否已经报满了
			int planNumber=eroomDao.getEroomPlanNumber(examRoom.getId());
			int eroomNumber=eroomDao.getEroomUserSize(examRoom.getId());
			if(eroomNumber>=planNumber){
				setElmessage("考场人数已满！");
				return "error";
			}
			//判断该申请考场是否是自主选择试卷
			examRoom=eroomDao.getApplyForeEroomById(examRoom.getId());
			if(examRoom.getErRegistration().getIsselectep()==1){
				examPapers = examPaperDao.listEroomExamPaper(examRoom.getId(),elUser.getId());
				eroomDao.udpateStudyepStatus(elUser.getId(), examRoom.getId());
			}else{
				examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
				for (int i = 0; i < examPapers.size(); i++) {
					// 判断试卷是否已被删除
					if (examPapers.get(i).getStatus() != 1) {
						// 检测该学员是否分配了该试卷
						if (!studyQuizDao.checkStudyExamPaper(elUser.getId(), examPapers.get(i)
								.getId(), examRoom.getId(), -1)) {
							// 添加该学员到 学员试卷表中
							studyQuizDao.addStudyExamPaper(elUser.getId(), examPapers.get(i)
									.getId(), examRoom.getId(), -1);
						}
					}
				}
			}
			if (!eroomDao.checkuser2eroom(examRoom.getId(), elUser.getId(), -1)) {// -1为考核考试
				eroomDao.adduser2eroom(examRoom.getId(), elUser.getId(), 0, -1,
						CourseConstants.EXAMROOM_SQFS_SQ);
			}
		}else{
			//状态为2：不通过
			//判断是否有作答过 ，没有就踢出考场，有就提示不能不通过
			int n=studyQuizDao.getStudyEroomAllCount(elUser.getId(),examRoom.getId());
			if(n==0){
				eroomDao.deleteuser2eroom(examRoom.getId(), elUser.getId());
			}else{
				setElmessage("该学员已经有参加考试，操作执行失败！");
				return "error";
			}
		}
		studyQuizDao.udpateStudyRoomApplyStatus(examRoom.getId(), elUser.getId(),myroom.getStatus());
		return "examroom_auditUserlist";
	}
	/**
	 * 批量更新学员考场报名状态(通过和不通过操作)
	 * @return
	 * @throws ElException
	 */
	public String updateStudysRoomStatus() throws ElException {
		String[] checkbox=getRequest().getParameterValues("elusers.id");
		if(checkbox==null){
			setElmessage("没有选择用户！");
			return "error";
		}
		examRoom=eroomDao.getApplyForeEroomById(examRoom.getId());
		int m=0;
		for (int i = 0; i < checkbox.length; i++) {
			if(myroom.getStatus()==3){//通过
				//判断是否已经报满了
				int planNumber=eroomDao.getEroomPlanNumber(examRoom.getId());
				int eroomNumber=eroomDao.getEroomUserSize(examRoom.getId());
				if(eroomNumber>=planNumber){
					setElmessage("刚刚报进去"+i+"人，考场人数已满！");
					return "error";
				}
//				examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
				//判断该申请考场是否是自主选择试卷
				
				if(examRoom.getErRegistration().getIsselectep()==1){
					examPapers = examPaperDao.listEroomExamPaper(examRoom.getId(),Integer.parseInt(checkbox[i]));
					eroomDao.udpateStudyepStatus(Integer.parseInt(checkbox[i]), examRoom.getId());
				}else{
					examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
					for (int j = 0; j < examPapers.size(); j++) {
						// 判断试卷是否已被删除
						if (examPapers.get(j).getStatus() != 1) {
							// 检测该学员是否分配了该试卷
							if (!studyQuizDao.checkStudyExamPaper(Integer.parseInt(checkbox[i]), examPapers.get(j)
									.getId(), examRoom.getId(), -1)) {
								// 添加该学员到 学员试卷表中
								studyQuizDao.addStudyExamPaper(Integer.parseInt(checkbox[i]), examPapers.get(j)
										.getId(), examRoom.getId(), -1);
							}
						}
					}
				}
				if (!eroomDao.checkuser2eroom(examRoom.getId(), Integer.parseInt(checkbox[i]), -1)) {// -1为考核考试
					eroomDao.adduser2eroom(examRoom.getId(), Integer.parseInt(checkbox[i]), 0, -1,
							CourseConstants.EXAMROOM_SQFS_SQ);
				}
			}else{
				//状态为2：不通过
				//判断是否有作答过 ，没有就踢出考场，有就提示不能不通过
				int n=studyQuizDao.getStudyEroomAllCount(Integer.parseInt(checkbox[i]),examRoom.getId());
				if(n==0){
					eroomDao.deleteuser2eroom(examRoom.getId(), Integer.parseInt(checkbox[i]));
				}else{
//					setElmessage("该学员已经有作答过试卷，操作执行失败！");
//					return "error";
					m++;
				}
			}
			studyQuizDao.udpateStudyRoomApplyStatus(examRoom.getId(), Integer.parseInt(checkbox[i]),myroom.getStatus());
			if(m>0){
				setElmessage("有"+m+"个学员操作执行失败,原因是该学员已经参加考试！");
				return "error";
			}
		}
		return "examroom_auditUserlist";
	}
	/**
	 * 删除学员考场报名记录
	 * @return
	 * @throws ElException
	 */
	public String deleteStudyRoomApply() throws ElException {
		//删除学员考场报名记录
		eroomDao.deleteStudyRoomApply(examRoom.getId(), elUser.getId());
		//删除学员考场相关信息
		examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		for (int i = 0; i < examPapers.size(); i++) {
			studyQuizDao.deleteQuiz(elUser.getId(), examRoom
					.getId(), examPapers.get(i).getId());
		}
		return "examroom_auditUserlist";
	}
	/**
	 * 删除学员考场报名记录(批量)
	 * @return
	 * @throws ElException
	 */
	public String deleteStudysRoomApply() throws ElException {
		String[] checkbox=getRequest().getParameterValues("elusers.id");
		if(checkbox==null){
			setElmessage("没有选择用户！");
			return "error";
		}
		for (int i = 0; i < checkbox.length; i++) {
			//删除学员考场报名记录
			eroomDao.deleteStudyRoomApply(examRoom.getId(),Integer.parseInt(checkbox[i]));
			//删除学员考场相关信息
			examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
			for (int j = 0; j < examPapers.size(); j++) {
				studyQuizDao.deleteQuiz(Integer.parseInt(checkbox[i]), examRoom
						.getId(), examPapers.get(j).getId());
			}
		}
		return "examroom_auditUserlist";
	}
	/**
	 * 添加考场（培训班）不通过原因初始化
	 * @return
	 * @throws ElException
	 */
	public String addSimpleRemackInit() throws ElException {
		return "addSimpleRemack";
	}
	/**
	 * 添加考场（培训班）不通过原因
	 * @return
	 * @throws ElException
	 */
	public String addSimpleRemack() throws ElException {
		if(simpleRemack!=null){
			simpleRemack.setCreater(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
			if(simpleRemack.getUserids()!=null&&!simpleRemack.getUserids().equals("")){
				String[] userids=simpleRemack.getUserids().split(",");
				for (int i = 0; i < userids.length; i++) {
					simpleRemack.getToUser().setId(Integer.parseInt(userids[i]));
					eroomDao.addSimpleRemack(simpleRemack);
				}
			}else{
				eroomDao.addSimpleRemack(simpleRemack);
			}
		}
		return null;
	}
	/**
	 * 查看该学员该考场（培训班）不通过备注列表
	 * @return
	 * @throws ElException
	 */
	public String listSimpleRemack() throws ElException {
		simpleRemacks=eroomDao.listSimpleRemack(simpleRemack, getPageNow(), getPageSize());
		return "listSimpleRemack";
	}
	/**
	 * 查询某考场里面的所以试题
	 * @return
	 */
	public String eroomQuestionList() throws ElException{
		examRoom=eroomDao.getExamRoomByid(examRoom.getId());
		if(question!=null&&question.getId()>0){
			try {
				question.setTitle(URLDecoder.decode(question.getTitle(), "UTF-8"));
			} catch (Exception e) {
				
			}
		}
		questions=questionDao.listEroomQuestion(examRoom.getId(), question,getPageNow(),getPageSize());
		count=questionDao.listEroomQuestionSize(examRoom.getId(), question);
		return "eroomQuestionList";
	}
	/**
	 * 设置学员试题分数并且重新阅卷
	 * @return
	 * @throws ElException
	 */
	public String setStudyQuestionScore() throws ElException{
		myExamPapers = questionDao.listSqidByRidQid(examRoom.getId(), question.getId());
		for (int i = 0; i < myExamPapers.size(); i++) {
			//更新学员题目的成绩
			questionDao.updateStudyQuestionScore(question.getId(), myExamPapers.get(i).getId(), question.getMyScore());
			//调用手工阅卷存储过程，重新设置学分
			studyQuizDao.study_marking(myExamPapers.get(i).getId());
			myExamPaper=studyQuizDao.getMyEpById(myExamPapers.get(i).getId());
			//设置考场及试卷状态
			studyQuizDao.setStudyEroomStatus(myExamPaper.getExamRoom().getId(), myExamPaper.getTester().getId());
		}
		try {
			question.setTitle(URLEncoder.encode( URLEncoder.encode(question.getTitle(), "UTF-8"),"UTF-8"));
		} catch (Exception e) {
		}
		return "eroomQuestionList";
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

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public EroomLib getEroomLibTree() {
		return eroomLibTree;
	}

	public void setEroomLibTree(EroomLib eroomLibTree) {
		this.eroomLibTree = eroomLibTree;
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

	public List<Examprac> getExampracs() {
		return exampracs;
	}

	public void setExampracs(List<Examprac> exampracs) {
		this.exampracs = exampracs;
	}

	public List<ELUser> getElusers() {
		return elusers;
	}

	public void setElusers(List<ELUser> elusers) {
		this.elusers = elusers;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
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

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public List<QuizPaper> getQuizPapers() {
		return quizPapers;
	}

	public void setQuizPapers(List<QuizPaper> quizPapers) {
		this.quizPapers = quizPapers;
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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public int getCourse_sourse() {
		return course_sourse;
	}

	public void setCourse_sourse(int course_sourse) {
		this.course_sourse = course_sourse;
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

	public String getInput_name() {
		return input_name;
	}

	public void setInput_name(String input_name) {
		this.input_name = input_name;
	}

	public ExamRoomAuditDescribes getErAuditdes() {
		return erAuditdes;
	}

	public void setErAuditdes(ExamRoomAuditDescribes erAuditdes) {
		this.erAuditdes = erAuditdes;
	}

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
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

	public ExamPaper getEp() {
		return ep;
	}

	public void setEp(ExamPaper ep) {
		this.ep = ep;
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

	public MyRoom getMyroom() {
		return myroom;
	}

	public void setMyroom(MyRoom myroom) {
		this.myroom = myroom;
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

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public ExamRoom getEroom() {
		return eroom;
	}

	public void setEroom(ExamRoom eroom) {
		this.eroom = eroom;
	}

	public List<ElRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}

	public String getResultPage() {
		return resultPage;
	}

	public void setResultPage(String resultPage) {
		this.resultPage = resultPage;
	}

	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public int getStandardLine() {
		return standardLine;
	}

	public void setStandardLine(int standardLine) {
		this.standardLine = standardLine;
	}

	public int getFirstLearnLaterExam() {
		return firstLearnLaterExam;
	}

	public void setFirstLearnLaterExam(int firstLearnLaterExam) {
		this.firstLearnLaterExam = firstLearnLaterExam;
	}

	public String examroom_importScoreInit()throws ElException{
		return "examroom_importScoreInit";
	}
	
	/**
	 * 导入前的检测
	 * 
	 * @return
	 * @throws Exception
	 */
	public String examroomScoreImportCheck() throws Exception {
		
		String resultPage = "coursemes_import";
	
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
				String isOk = UserExcelUtil.checkWriteExamRoom(st,course.getId(),course.getClassid(),examRoom.getId());
				// if(!"true".equals(isOk)&&!"".equals(isOk)){//返回
				setElmessage(isOk);
				// 复制此文件到服务器临时保存
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String filename = "user_" + userid + "_"
						+ System.currentTimeMillis();
				J2EEFileUtil.upload(st, "xls", "/importtemp/", filename);
				stFileName = filename + ".xls";
				return "accountImportInfo";
				// }
			}
		} else {
			setElmessage("请输入上传文件");
			return resultPage;
		}
	}
	
	public String examRoomScoreImport() throws ElException {
		if (stFileName != null) {
			File xls = new File(ServletActionContext.getServletContext()
					.getRealPath("/")
					+ "/importtemp/" + stFileName);
			if (xls.exists()) {
				String isOk = "";
				isOk = UserExcelUtil.writeExamRoomScore(xls,course.getId(),course.getClassid(),examRoom.getId());
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
		return "elclass_course";
	}

}
