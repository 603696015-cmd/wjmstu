package com.sopia.statman.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.ErblockDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.EroomBatch;
import com.sopia.courseman.entities.EroomBatchLib;
import com.sopia.courseman.entities.EroomBlock;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.statman.dao.StatisticCourseDao;
import com.sopia.statman.dao.StatisticQuizDao;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyBatchRoom;
import com.sopia.studyman.entities.MyEprac;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;

/**
 * Description: 考试统计对应struts: com/sopia/common/configs/stat_quiz.xml Copyright
 * (c) Department of Research and Development/wenyishun110@163.com. All Rights
 * Reserved.
 * 
 * @version 1.0 2011-9-4 上午12:08:46 by 闻益舜（wenyishun110@163.com）创建
 */
public class StatisticQuiz extends BaseAction {
	private Department depTree;
	private Department department;
	private List<Department> departments;
	private List<Department> departments1;
	private ELUser elUser;

	private int sub_department;
	private List<ELUser> elUsers;
	private List<Course> courses;
	private StatisticQuizDao statisticQuizDao;
	private StatisticCourseDao statisticCourseDao;
	private List<ExamRoom> examRooms;
	private ExamRoom examRoom;
	private List<ExamPaper> examPapers;
	private List<MyExamPaper> myExamPapers;
	private ExamPaper examPaper;
	private List<EroomBatch> erbatchs;
	private EroomBatch erbatch;
	private EroomBatchLib erbatchlib;
	private boolean exprot;
	private MyRoom myroom;
	private List<BaseDatat> jzs;
	private List<BaseDatat> jzs1;
	private List<BaseDatat> jzs2;
	private List<EroomBlock> erblocks;
	private EroomBlock erblock;
	private ErblockDao erblockDao;
	/**
	 * 查看我的结业考试试卷。
	 * 
	 * @return
	 * @throws ElException
	 */
	private MyExamPaper myExamPaper;
	private ExamPaperDao examPaperDao;
	/**
	 * 练习统计
	 * 
	 * @return
	 */
	private Examprac examprac;
	private List<MyEprac> myexampracs;
	private MyEprac myeprac;
	private QuestionDao questionDao;
	private StudyQuizDao studyQuizDao;
	private EroomLib eroomLibTree;
	private EroomDao eroomDao;
	private int batchstat;
	
	//个人学分查询  wsj20131202
	private List<MyClass> myClasses;
	private int myBxScoreCount;
	private int myXxScoreCount;
	private int myTScoreCount;
	private List<ElClass> elClasses;
	
	public List<MyClass> getMyClasses() {
		return myClasses;
	}

	public void setMyClasses(List<MyClass> myClasses) {
		this.myClasses = myClasses;
	}

	public int getMyBxScoreCount() {
		return myBxScoreCount;
	}

	public void setMyBxScoreCount(int myBxScoreCount) {
		this.myBxScoreCount = myBxScoreCount;
	}

	public int getMyXxScoreCount() {
		return myXxScoreCount;
	}

	public void setMyXxScoreCount(int myXxScoreCount) {
		this.myXxScoreCount = myXxScoreCount;
	}

	public int getMyTScoreCount() {
		return myTScoreCount;
	}

	public void setMyTScoreCount(int myTScoreCount) {
		this.myTScoreCount = myTScoreCount;
	}

	public List<ElClass> getElClasses() {
		return elClasses;
	}

	public void setElClasses(List<ElClass> elClasses) {
		this.elClasses = elClasses;
	}

	public int getBatchstat() {
		return batchstat;
	}

	public void setBatchstat(int batchstat) {
		this.batchstat = batchstat;
	}

	public EroomLib getEroomLibTree() {
		return eroomLibTree;
	}

	public void setEroomLibTree(EroomLib eroomLibTree) {
		this.eroomLibTree = eroomLibTree;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public String user_quizresult() throws ElException {

		return "user_quizresult";
	}

	public String dep_quiz_info() throws ElException {
		courses = statisticCourseDao.listCourseByDepid(department.getId());

		return "dep_quiz_info";
	}

	public String quiz_searchInit() throws ElException {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);

		return "quiz_searchInit";
	}

	/**考场统计,考场列表
	 * @return
	 * @throws ElException
	 */
	public String quiz_searchlist() throws ElException {
//		int depid = department == null ? 1 : department.getId(); 
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);

//		department = new Department(depid);
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else { 
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		examRoom=examRoom==null?new ExamRoom():examRoom;
		if (examRoom.getEroomLib() == null || examRoom.getEroomLib().getId() <= 0) {
			examRoom.setEroomLib(eroomLibTree);
		} else {
			if(getSessionIntValue(ElConstants.SESSION_ROLE)!= 1&&!((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).checkNode(examRoom.getEroomLib().getId(), eroomLibTree, "eroom_Lib")){
				setElmessage("您输入了无权操作的节点！");
				return "error";
			}
			examRoom.setEroomLib(eroomDao.getEroomLibById(examRoom.getEroomLib().getId()));
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
		examRooms = statisticQuizDao.listquziseach(examRoom,examRoom.getEroomLib(),getSessionIntValue(ElConstants.SESSION_ROLE),depTree,getPageNow(),getPageSize()); 
		count = statisticQuizDao.listquziseachCount(examRoom,examRoom.getEroomLib(),getSessionIntValue(ElConstants.SESSION_ROLE),depTree);
		return "quiz_searchlist";
	}
	/**
	 * Description: 模块成绩汇总 
	 * @Version1.0 2012-7-17 下午02:06:08 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String quizblock_detail_view() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department==null||department.getId()<=0){
			department = depTree;
		}else{
			if(getSessionIntValue(ElConstants.SESSION_ROLE)!= 1&&!((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).checkNode(department.getId(), depTree, "department")){
				setElmessage("您输入了无权操作的节点！");
				return "error";
			}
			department  = departmentDao.getDepById(department.getId());
		}
		erblock = erblockDao.getErblock(erblock.getId());
		erblock.setErepblocks(erblockDao.listErepblock(erblock.getId()));
		if(examRoom==null)
			examRoom= erblock.getEroom();
		else
			examRoom.setId(erblock.getEroom().getId());
		String ids = "";
		if(batchstat==1){
			ids = statisticQuizDao .getEroomsByErbid(erbatch.getId());
			erbatch = statisticQuizDao.getQuizBatch_detail_paper_view(depTree,erbatch.getId());
		}else{
			ids = examRoom.getId()+"";
			examRoom = statisticQuizDao.getQuiz_detail_paper_view(depTree,examRoom.getId());
		}
		examRoom.setBegintime(myroom!=null?myroom.getBegintime():null);
		examRoom.setEndtime(myroom!=null?myroom.getEndtime():null);
		List<MyRoom> mr = statisticQuizDao.listquiz_detail_view(department,examRoom,ids,getPageNow(),getPageSize());
		count = statisticQuizDao.listquiz_detail_view_size(department,examRoom,ids);
		if (null != mr) {//页面显示源
			for (int i = 0; i < mr.size(); i++) {
				mr.get(i).setMyExamPapers(
						statisticQuizDao.list_detail_view_quizblock(erblock, mr.get(i).getTester().getId())); 
			}
		}
//		Timestamp begintime=examRoom.getBegintime();
//		Timestamp endtime=examRoom.getEndtime();
		examRoom = statisticQuizDao.getQuiz_detail_paper_view(department,examRoom.getId());
		examRoom.setMyrooms(mr);
//		examRoom.setBegintime(begintime);
//		examRoom.setEndtime(endtime);
		return "quizblock_detail_view";
	}
	/**
	 * Description: 模块成绩汇总 
	 * @Version1.0 2012-7-17 下午02:06:08 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String quizblock_detail_viewExcel() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department==null||department.getId()<=0){
			department = depTree;
		}else{
			if(getSessionIntValue(ElConstants.SESSION_ROLE)!= 1&&!((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).checkNode(department.getId(), depTree, "department")){
				setElmessage("您输入了无权操作的节点！");
				return "error";
			}
			department  = departmentDao.getDepById(department.getId());
		}
		erblock = erblockDao.getErblock(erblock.getId());
		erblock.setErepblocks(erblockDao.listErepblock(erblock.getId()));
		if(examRoom==null)
			examRoom= erblock.getEroom();
		else
			examRoom.setId(erblock.getEroom().getId());
		String ids = "";
		if(batchstat==1){
			ids = statisticQuizDao .getEroomsByErbid(erbatch.getId());
			erbatch = statisticQuizDao.getQuizBatch_detail_paper_view(depTree,erbatch.getId());
		}else{
			ids = examRoom.getId()+"";
			examRoom = statisticQuizDao.getQuiz_detail_paper_view(depTree,examRoom.getId());
		}
		examRoom.setBegintime(myroom!=null?myroom.getBegintime():null);
		examRoom.setEndtime(myroom!=null?myroom.getEndtime():null);
		List<MyRoom> mr = statisticQuizDao.listquiz_detail_view_Page(department,0,examRoom,ids);
		if (null != mr) {//页面显示源
			for (int i = 0; i < mr.size(); i++) {
				mr.get(i).setMyExamPapers(
						statisticQuizDao.list_detail_view_quizblock(erblock, mr.get(i).getTester().getId())); 
			}
		}
		examRoom = statisticQuizDao.getQuiz_detail_paper_view(department,examRoom.getId());
		examRoom.setMyrooms(mr);
		return "quizblock_detail_viewExcel";
	}
	/**
	 * Description: 成绩汇总 
	 * @Version1.0 2012-7-17 下午02:06:08 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String quiz_detail_view() throws ElException {
		// examRoom.setUserSize(statisticQuizDao.getERUserByDepidAndERid(examRoom
		// .getId(), department.getId())); 
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
//		else {
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department==null||department.getId()<=0){
			department = depTree;
		}else{
			if(getSessionIntValue(ElConstants.SESSION_ROLE)!= 1&&!((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).checkNode(department.getId(), depTree, "department")){
				setElmessage("您输入了无权操作的节点！");
				return "error";
			}
			department  = departmentDao.getDepById(department.getId());
		}
//		int depid = department != null ? department.getId(): 0;
//		List<MyRoom> mr = statisticQuizDao.listquiz_detail_view_Page(depTree,depid,examRoom.getId(),
//				getSessionIntValue(ElConstants.SESSION_ROLE),200,0);//,getPageNow(),getPageSize()  因为排名，暂时注释分页
////		count = statisticQuizDao.listquiz_detail_view_Count(depTree,depid,examRoom.getId(),getSessionIntValue(ElConstants.SESSION_ROLE));  //因为排名，暂时注释分页
//		List<MyRoom> newmr=statisticQuizDao.listquiz_detail_view_Page(depTree,depid,examRoom.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),200,0);
//		if (null != mr) {//页面显示源
//			for (int i = 0; i < mr.size(); i++) {
//				mr.get(i).setMyExamPapers(
//						statisticQuizDao.list_detail_view_quizpaper(examRoom
//								.getId(), mr.get(i).getTester().getId())); 
//			}
//		}
//		if (null != newmr) {//导出源
//			for (int i = 0; i < newmr.size(); i++) { 
//				newmr.get(i).setMyExamPapers(
//						statisticQuizDao.list_detail_view_quizpaper(examRoom
//								.getId(), newmr.get(i).getTester().getId()));
//			}
//		}
//		getSession().setAttribute("mrExcel",newmr);
//		List<MyRoom> mr = statisticQuizDao.listquiz_detail_view_Page(department,0,examRoom.getId(),
//				getSessionIntValue(ElConstants.SESSION_ROLE));//,getPageNow(),getPageSize()不该分页吧
		String ids = "";
		if(batchstat==1){
			ids = statisticQuizDao .getEroomsByErbid(erbatch.getId());
			erbatch = statisticQuizDao.getQuizBatch_detail_paper_view(depTree,erbatch.getId());
		}else{
			ids = examRoom.getId()+"";
			examRoom = statisticQuizDao.getQuiz_detail_paper_view(depTree,examRoom.getId());
		}
		List<MyRoom> mr = statisticQuizDao.listquiz_detail_view(department,examRoom,ids,getPageNow(),getPageSize());
		count = statisticQuizDao.listquiz_detail_view_size(department,examRoom,ids);
//		count = statisticQuizDao.listquiz_detail_view_Count(department,0,examRoom.getId(),getSessionIntValue(ElConstants.SESSION_ROLE));
//		List<MyRoom> newmr=statisticQuizDao.listquiz_detail_view_Page(depTree,0,examRoom.getId(),getSessionIntValue(ElConstants.SESSION_ROLE),200,0);
		if (null != mr) {//页面显示源
			for (int i = 0; i < mr.size(); i++) {
				mr.get(i).setMyExamPapers(
						statisticQuizDao.list_detail_view_quizpaper(mr.get(i).getExamroom()
								.getId(), mr.get(i).getTester().getId())); 
			}
		}
		Timestamp begintime=examRoom.getBegintime();
		Timestamp endtime=examRoom.getEndtime();
		examRoom = statisticQuizDao.getQuiz_detail_paper_view(department,examRoom.getId());
		examRoom.setMyrooms(mr);
		examRoom.setBegintime(begintime);
		examRoom.setEndtime(endtime);
		return "quiz_detail_view";
	}
	public String quiz_detail_viewExcel()throws ElException{
//		List<MyRoom> mr=(List<MyRoom>)getSession().getAttribute("mrExcel");
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department==null||department.getId()<=0){
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		String ids = "";
		if(batchstat==1){
			ids = statisticQuizDao .getEroomsByErbid(erbatch.getId());
			erbatch = statisticQuizDao.getQuizBatch_detail_paper_view(depTree,erbatch.getId());
		}else{
			ids = examRoom.getId()+"";
			examRoom = statisticQuizDao.getQuiz_detail_paper_view(depTree,examRoom.getId());
		}
		List<MyRoom> mr = statisticQuizDao.listquiz_detail_view_Page(department,0,examRoom,ids );//,getPageNow(),getPageSize()  因为排名，暂时注释分页
		//examRoom=examRoom==null?new ExamRoom():examRoom;
		if (null != mr) {//页面显示源
			for (int i = 0; i < mr.size(); i++) {
				mr.get(i).setMyExamPapers(
						statisticQuizDao.list_detail_view_quizpaper(mr.get(i).getExamroom()
								.getId(), mr.get(i).getTester().getId())); 
			}
		}
		examRoom = statisticQuizDao.getQuiz_detail_paper_view(department,examRoom.getId());
		examRoom.setMyrooms(mr);
	//getSession().removeAttribute("mrExcel");
		return "quiz_detail_viewExcel";
	}
	
	public String studyRoomRecordList() throws ElException{
//		if(myroom==null)myroom=new MyRoom();
//		myroom.setMyRoomRecord(studyQuizDao.listStudyRoomRecordSqinfo(elUser.getId(), examRoom.getId()));
		return "studyRoomRecordList";
	}
	
	public String quiz_paper_detail_view() throws ElException {
		// examRoom.setUserSize(statisticQuizDao.getERUserByDepidAndERid(examRoom
		// .getId(), department.getId()));
		examRoom.setMeps(statisticQuizDao.listquizpaper_detail_view(examRoom
				.getId(), elUser.getId()));
		return "quiz_paper_detail_view";
	}
	/**
	 * Description:各类试卷统计 
	* @Version1.0 2012-7-17 下午01:54:37 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String quiz_detail_paper_view() throws ElException {
		// examRoom.setUserSize(statisticQuizDao.getERUserByDepidAndERid(examRoom
		// .getId(), department.getId()));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		String ids = "";
		if(batchstat==1){
			ids = statisticQuizDao .getEroomsByErbid(erbatch.getId());
			erbatch = statisticQuizDao.getQuizBatch_detail_paper_view(depTree,erbatch.getId());
		}else{
			ids = examRoom.getId()+"";
			examRoom = statisticQuizDao.getQuiz_detail_paper_view(depTree,examRoom.getId());
		}
		//examRoom = statisticQuizDao.getQuiz_detail_paper_view(new Department(1),examRoom.getId());
		myExamPapers = statisticQuizDao.listquiz_detail_paper_view(ids, departments1,depTree) ;
		return "quiz_detail_paper_view";
	}
	/**
	 * Description: 各类试卷统计导出
	* @Version1.0 2012-7-18 上午09:10:05 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String quiz_detail_paper_view_Excel() throws ElException {
		// examRoom.setUserSize(statisticQuizDao.getERUserByDepidAndERid(examRoom
		// .getId(), department.getId()));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		String ids = "";
		if(batchstat==1){
			ids = statisticQuizDao .getEroomsByErbid(erbatch.getId());
		}else
			ids = examRoom.getId()+"";
		examRoom = statisticQuizDao.getQuiz_detail_paper_view(new Department(1),examRoom.getId());
		myExamPapers = statisticQuizDao.listquiz_detail_paper_view(ids, departments1,depTree) ;
		return "quiz_detail_paper_view_Excel";
	}
	
	/**
	 * Description: 考核各单位情况汇总表
	* @Version1.0 2012-7-17 下午03:13:35 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String quiz_stat_view() throws ElException {
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
//		else {
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		}
//		int depid = department != null ? department.getId(): 0;
//		if(depid == 0){
//			setElmessage("您没有此节点访问权限 !");
//			return "error";
//		}
//		boolean sub = sub_department == 0 ? false : true;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		String ids = "";
		if(batchstat==1){
			ids = statisticQuizDao .getEroomsByErbid(erbatch.getId());
		}else
			ids = examRoom.getId()+"";
		examRoom = statisticQuizDao.getQuiz_detail_paper_view(new Department(1),examRoom.getId());
		int erid = examRoom.getId();
		if(departments1 ==null){
			if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
				departments1 = new ArrayList<Department>();
				Department dep = departmentDao.getDepById(1);
				departments1.add(dep);
			}else{
				departments1 = depTree!=null?depTree.getChild():null;
			}
		}
		if(departments1!=null){
			for (int i = 0; i < departments1.size(); i++) {
				Department d = departmentDao.getDepById(departments1.get(i).getId());
				departments1.get(i).setName(d.getName());
				departments1.get(i).setLid(d.getLid());
				departments1.get(i).setRid(d.getRid());
				//departments1.get(i).setExamRoom ( statisticQuizDao.geteroom_gk(departments1.get(i).getId(), erid, true));
				departments1.get(i).setExamRoom ( statisticQuizDao.geteroom_gk_2(departments1.get(i).getId(), ids, true));
			}
		}
//		else{
//			departments1 = new ArrayList<Department>();
//			Department dep = departmentDao.getDepById(1);
//			//dep.setExamRoom( statisticQuizDao.geteroom_gk(1, erid, false));
//			dep.setExamRoom( statisticQuizDao.geteroom_gk_2(1, erid, true));
//			departments1.add(dep);
//		}
		// examRoom = statisticQuizDao.getERbyId(examRoom.getId());
		// department = departmentDao.getDepById(department.getId());
		// if (sub_department == 1) {
		// depTree = departmentDao.getDepTree(department.getId(), -1, true);
		// examRoom.setMeps(new ArrayList<MyExamPaper>());
		// setERuserSub(examRoom, depTree);
		// } else {
		// examRoom.setUserSize(statisticQuizDao.getERUserByDepidAndERid(examRoom
		// .getId(), department.getId()));
		// examRoom.setMeps(statisticQuizDao.listMyEPbyDidAndRid(examRoom.getId(),
		// department.getId()));
		// } 
		examRoom.setId(erid);
		if(isExprot()){//导出excle表
			return "quiz_stat_view_EXCEL";
		} 
		return "quiz_stat_view";

	} 
	
	/**
	 * Description: 考核各单位情况汇总
	* @Version1.0 2012-7-18 上午08:23:34 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String quiz_stat_eval() throws ElException {  
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(departments1 ==null){
			if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
				departments1 = new ArrayList<Department>();
				Department dep = departmentDao.getDepById(1);
				departments1.add(dep);
			}else{
				departments1 = depTree!=null?depTree.getChild():null;
			}
		}
		String ids = "";
		if(batchstat==1){
			ids = statisticQuizDao .getEroomsByErbid(erbatch.getId());
		}else
			ids = examRoom.getId()+"";
		myExamPapers = statisticQuizDao.listEroomExampapers(ids,depTree);
		if(examPapers==null){
			examPapers = new ArrayList<ExamPaper>();
			if(null!=myExamPapers)
				for (int i = 0; i < myExamPapers.size(); i++) {
					examPapers.add(myExamPapers.get(i).getExamPaper());
				}
		}
		if(examPapers!=null)
			for (int i = 0; i < examPapers.size(); i++) {
				ExamPaper ep =examPapers.get(i);
				for (int j = 0; j < myExamPapers.size(); j++) {
					ExamPaper ep1 = myExamPapers.get(j).getExamPaper();
					if(ep.getId()==ep1.getId()){
						examPapers.get(i).setTitle(ep1.getTitle());
						myExamPapers.get(j).setStatus(5);
					}
				}
			}
		if(departments1 != null){
			departments=new ArrayList<Department>();
			for (int i = 0; i < departments1.size(); i++) {
				departments.add(statisticQuizDao.listEroomEval2(ids,examPapers,departments1.get(i).getId(),true));
				departments.get(i).setName(departmentDao.getDepById(departments1.get(i).getId()).getName());
			}
			//排序
			departments=statisticQuizDao.getDepSortByRatio(departments);
		} 
		if(isExprot()){//导出excle表
			return "quiz_stat_eval_EXCEL";
		}
		if(departments1!=null){
			for (int i = 0; i < departments1.size(); i++) {
				Department d = departmentDao.getDepById(departments1.get(i).getId());
				departments1.get(i).setName(d.getName());
				departments1.get(i).setLid(d.getLid());
				departments1.get(i).setRid(d.getRid());
			}
		}
		return "quiz_stat_eval";

	} 
	/**
	 * Description: 考核各工种情况汇总
	* @Version1.0 2012-7-18 下午03:11:52 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String quiz_stat_eval_jz() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		jzs = userDao.getBaseDatatByTypeid(1,getPageNow(),getPageSize());//1工种 
		String ids = "";
		if(batchstat==1){
			ids = statisticQuizDao .getEroomsByErbid(erbatch.getId());
		}else
			ids = examRoom.getId()+"";
		myExamPapers = statisticQuizDao.listEroomExampapers(ids,depTree);
		if(examPapers==null){
			examPapers = new ArrayList<ExamPaper>();
			if(null!=myExamPapers)
				for (int i = 0; i < myExamPapers.size(); i++) {
					examPapers.add(myExamPapers.get(i).getExamPaper());
				}
		}
		if(examPapers!=null)
			for (int i = 0; i < examPapers.size(); i++) {
				ExamPaper ep =examPapers.get(i);
				for (int j = 0; j < myExamPapers.size(); j++) {
					ExamPaper ep1 = myExamPapers.get(j).getExamPaper();
					if(ep.getId()==ep1.getId()){
						examPapers.get(i).setTitle(ep1.getTitle());
						myExamPapers.get(j).setStatus(5);
					}
				}
			}
		jzs2 = jzs2==null? jzs :jzs2;
		if(jzs2 != null){
			jzs1 = new ArrayList<BaseDatat>();
			for (int i = 0; i < jzs2.size(); i++) {
				jzs1.add(statisticQuizDao.listEroomEval_jz(ids,examPapers,jzs2.get(i).getId(),depTree,true));
				jzs1.get(i).setBasevalue(userDao.getBaseDatatById(jzs2.get(i).getId()).getBasevalue());
			}
			//排序
//			departments=statisticQuizDao.getDepSortByRatio(departments);
		} 
		if(jzs != null){
			for (int i = 0; i < jzs.size(); i++) {
				for (int j = 0; j < jzs2.size(); j++) {
					if(jzs.get(i).getId()==jzs2.get(j).getId())
					jzs.get(i).setSelected(1);
				}
			}
		} 
		if(isExprot()){//导出excle表
			return "quiz_stat_eval_jz_EXCEL";
		}
		return "quiz_stat_eval_jz";

	} 
	public String sim_searchInit() throws ElException {
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "sim_searchInit";
	}

	public String sim_searchlist() throws ElException {
		if (department.getId() != 0)
			department = departmentDao.getDepById(department.getId());
		if (sub_department == 1) {
			depTree = departmentDao.getDepTree(department.getId(), -1, true);
			examPapers = statisticQuizDao.listSimEpByDid(department.getId(),
					examPaper);
		} else {
			examPapers = statisticQuizDao.listSimEpByDid(department.getId(),
					examPaper);
		}
		return "sim_searchlist";
	}

	/*
	 * private void setSimEPSub(ExamPaper ep, Department dep) throws ElException { //
	 * ep.setUserCount(ep.getUserCount() // +
	 * statisticQuizDao.getSimUserBydepid(ep.getId(), //
	 * dep.getId(),examPaper.getCourse().getId())); // ep.getMeps().addAll( //
	 * statisticQuizDao.listSimEps(dep.getId(), //
	 * ep.getId(),ep.getCourse().getId())); // for (int i = 0; i <
	 * dep.getChild().size(); i++) { // setSimEPSub(ep, dep.getChild().get(i)); // } }
	 */

	public String sim_stat_view() throws ElException {
		// examPaper = statisticQuizDao.getEPbyEpidAndCid(examPaper.getId(),
		// examPaper.getCourse().getId());
		// company = departmentDao.getCompanyById((Integer) getSession()
		// .getAttribute("myCompany"));
		// if (department.getId() != 0)
		// department = departmentDao
		// .getDepartmentById(department.getId());
		// if (sub_department == 1) {
		// depTree = departmentDao.getDepTree((Integer) getSession()
		// .getAttribute("myCompany"), department.getId(), -1, true);
		// examPaper.setMeps(new ArrayList<MyExamPaper>());
		// setSimEPSub(examPaper, depTree);
		//
		// } else {
		// examPaper.setMeps(statisticQuizDao.listSimEps(department.getId(),
		// examPaper.getId(),examPaper.getCourse().getId()));
		// examPaper.setUserCount(statisticQuizDao.getSimUserBydepid(examPaper
		// .getId(), department.getId(),examPaper.getCourse().getId()));
		// }
		return "sim_stat_view";
	}

	public String sim_detail_view() throws ElException {
		// examPaper = statisticQuizDao.getEPbyEpidAndCid(examPaper.getId(),
		// examPaper.getCourse().getId());
		// company = departmentDao.getCompanyById((Integer) getSession()
		// .getAttribute("myCompany"));
		// if (department.getId() != 0)
		// department = departmentDao
		// .getDepartmentById(department.getId());
		// if (sub_department == 1) {
		// depTree = departmentDao.getDepTree((Integer) getSession()
		// .getAttribute("myCompany"), department.getId(), -1, true);
		// examPaper.setMeps(new ArrayList<MyExamPaper>());
		// setSimEPSub(examPaper, depTree);
		//
		// } else {
		// examPaper.setMeps(statisticQuizDao.listSimEps(department.getId(),
		// examPaper.getId(),examPaper.getCourse().getId()));
		// examPaper.setUserCount(statisticQuizDao.getSimUserBydepid(examPaper
		// .getId(), department.getId(),examPaper.getCourse().getId()));
		// }
		return "sim_detail_view";
	}

	public String quizpaper_view() throws ElException {
		if (myExamPaper == null || myExamPaper.getId() <= 0) {
			setElmessage("没有找到考试记录，请确认它的存在！");
			return "error";
		}
		// myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
		// examPaper = examPaperDao.getExamPaperById(myExamPaper.getExamPaper()
		// .getId());
		myExamPaper = studyQuizDao.getMyEpById(myExamPaper.getId());
		elUser = userDao.getUserById(elUser.getId());
		examPaper = studyQuizDao.getMyExamPaper(myExamPaper.getId());
		examPaper.setUserage(elUser.getAge());
		// if (null == myExamPaper.getMyAnswer()
		// || "".equals(myExamPaper.getMyAnswer().trim())) {
		// examPaper = examPaperDao.getEPAllInfoById(examPaper.getId());
		// } else {
		// examPaper.setEpBlocks(new ArrayList<ExamPaperBlock>());
		// ExamPaperUtil.getAnswerExampaper(myExamPaper.getMyAnswer(),
		// examPaper, examPaperDao, questionDao, elUser.getShengri());
		// }
//		return "quizpaper_view";
		return "quizpaperviewall";
	}

	public String stat_examprac_list() throws ElException {
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		//int roleid=getSessionIntValue(ElConstants.SESSION_ROLE);
//		if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
//			depTree = departmentDao.getDepTree_level1(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//		}else{
//			depTree = departmentDao.getDepTree_level1(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
//		}
//		int depid = department == null ? 0 : department.getId();
//		boolean sub = sub_department == 1 ? true : false;
//		if(department==null){
//			sub=true;
//			depid=1;
//			department=new Department(1);
//		}
//		myexampracs = statisticQuizDao.listexamprac(depid, examprac, sub,
//				getPageNow(), getPageSize());
//		count = statisticQuizDao.listexampracsize(depid, examprac, sub);
//		if(department==null||department.getId()<=0){
//			department=depTree;
//		}else{
//			department=departmentDao.getDepById(department.getId());
//		}
//		sub_department = examprac == null ? 1 : sub_department;
//		myexampracs = statisticQuizDao.listexamprac(department, examprac, sub_department,
//				getPageNow(), getPageSize());
//		count = statisticQuizDao.listexampracSize(department, examprac, sub_department);
		myexampracs = statisticQuizDao.listexamprac(examprac,getPageNow(), getPageSize());
		count = statisticQuizDao.listexampracSize(examprac);
		return "stat_examprac_list";
	}

//	public String stat_examprac_gk() throws ElException {
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
//		else {
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		} 
//		int depid = department == null ? 0 : department.getId();
//		boolean sub = sub_department == 1 ? true : false;
//		int eprid = examprac.getId();
//		examprac = statisticQuizDao.getexamprac_gk(depid, eprid, sub);
//		examprac.setId(eprid);
//		if(isExprot()){
//			return "stat_examprac_gk_EXCEL";			
//		}
//		return "stat_examprac_gk";
//	}
	
	public String stat_examprac_gk() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		int eprid = examprac.getId();
		if(departments1!=null){
			for (int i = 0; i < departments1.size(); i++) {
				//departments1.get(i).setName(departmentDao.getDepById(departments1.get(i).getId()).getName());
				Department d = departmentDao.getDepById(departments1.get(i).getId());
				departments1.get(i).setName(d.getName());
				departments1.get(i).setLid(d.getLid());
				departments1.get(i).setRid(d.getRid());
				departments1.get(i).setExamprac(statisticQuizDao.getexamprac_gk2(departments1.get(i).getId(), eprid, true));
			}
		}else{
			if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
				departments1 = new ArrayList<Department>();
				Department dep = departmentDao.getDepById(1);
				//examprac = statisticQuizDao.getexamprac_gk(1, eprid, true);
				//examprac = statisticQuizDao.getexamprac_gk2(1, eprid, true);
				dep.setExamprac(statisticQuizDao.getexamprac_gk2(1, eprid, true));
				departments1.add(dep);
			}
		}
		examprac.setId(eprid);
		if(isExprot()){//导出excle表
			return "stat_examprac_gk_EXCEL";
		}
		return "stat_examprac_gk";//quiz_stat_view

	}

	public String stat_examprac_detail() throws ElException { 
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
			}
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
//		int depid = department == null ? 0 : department.getId();
//		boolean sub = sub_department == 1 ? true : false;
		if(department==null||department.getId()<=0){
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		boolean sub=true;
		if(isExprot()){
			//myexampracs = statisticQuizDao.listexamprac(department.getId(), examprac.getId(),sub);
			myexampracs = statisticQuizDao.listexamprac(department, examprac.getId(), sub, 9999, 1);
			return "stat_examprac_detail_EXCEL";		
		}
//		myexampracs = statisticQuizDao.listexamprac(depid, examprac.getId(), sub, getPageNow(), getPageSize());
//		count = statisticQuizDao.listexampracsize(depid, examprac.getId(), sub);
//		myexampracs = statisticQuizDao.listexamprac2(department.getId(), examprac.getId(), sub, getPageNow(), getPageSize());
//		count = statisticQuizDao.listexampracsize2(department.getId(), examprac.getId(), sub);
		myexampracs = statisticQuizDao.listexamprac(department, examprac.getId(), sub, getPageNow(), getPageSize());
		count = statisticQuizDao.listexampracSize(department, examprac.getId(), sub);
		return "stat_examprac_detail";
	}

	public String stat_examprac_detail_list() throws ElException {
		myeprac = studyQuizDao.getmyexamprac(elUser.getId(), examprac.getId());
		myExamPapers = studyQuizDao.listMpracExampapers(examprac.getId(),
				elUser.getId(), getPageNow(), getPageSize());

		return "stat_examprac_detail_list";
	}

	public String stat_examprac_eval() throws ElException {
//		if(isExprot()){ //Export EXCEL
//			if(departments1 != null){
//				departments = statisticQuizDao.listPracEval(examprac.getId(),departments1);
//			}else{
//				departments = null;
//			}
//			return "stat_examprac_eval_EXCEL";
//		}
//		if(departments1 != null){
//			departments = statisticQuizDao.listPracEval(examprac.getId(),departments1);
//		}else{
//			departments = null;
//		}
//		department = department==null?new Department(1):department;
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(departments==null){
			departments=new ArrayList<Department>();
			//departments1.add(new Department(depTree.getId()));
		}
		for (int i = 0; i < departments.size(); i++) {
			department=departmentDao.getDepById(departments.get(i).getId());
			//departments1.get(i).setLid(department.getLid());
			//departments1.get(i).setRid(department.getRid());
			department=statisticQuizDao.listPracEval(examprac.getId(),department);
			departments.set(i, department);
		}
		//排序
		departments = statisticQuizDao.getDepSortByRatio(departments);
		if(isExprot()){ //Export EXCEL
			return "stat_examprac_eval_EXCEL";
		}
		return "stat_examprac_eval";
	}

	/**
	 * Description: 批次统计
	 * 
	 * @Version1.0 2011-9-8 上午10:18:30 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String stat_eroom_batch_list() throws ElException {
//		int libid = erbatchlib == null ? 1 : erbatchlib.getId();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		int roleid = getSessionIntValue(ElConstants.SESSION_USERID);
		int userid = roleid==1?-1:getSessionIntValue(ElConstants.SESSION_USERID);
		erbatchs = statisticQuizDao.listErbatchs(userid,depTree, getPageNow(),
				getPageSize());
		count = statisticQuizDao.listErbatchsSize(userid,depTree);
//		int depid = department == null ? 1 : department.getId();

//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
//		else {
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		} 
//		department = department == null? new Department(1):department;
		return "stat_eroom_batch_list";
	}
	/**模块统计
	 * @return
	 * @throws ElException
	 */
	public String stat_eroom_block_list() throws ElException {
//		int libid = erbatchlib == null ? 1 : erbatchlib.getId();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		int roleid = getSessionIntValue(ElConstants.SESSION_USERID);
		int userid = roleid==1?-1:getSessionIntValue(ElConstants.SESSION_USERID);
		erblocks = statisticQuizDao.listErblocks(userid,depTree, getPageNow(),
				getPageSize());
		count = statisticQuizDao.listErblocksSize(userid,depTree);
		return "stat_eroom_block_list";
	}
	/**
	 * Description:批次概况
	 * 
	 * @Version1.0 2011-9-8 上午10:18:18 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String stat_eroom_batch_gk() throws ElException {
		int brid = erbatch.getId(); 
		if(isExprot()){ 
			examRooms = statisticQuizDao.listErbRoomsPage(brid); 
			erbatch = statisticQuizDao.getErbatch_gk(brid);
			return "stat_eroom_batch_gk_EXCEL";
		}
		examRooms = statisticQuizDao.listErbRoomsPage(brid, getPageNow(), getPageSize());
		count = statisticQuizDao.listErbRoomsCount(brid);
		erbatch = statisticQuizDao.getErbatch_gk(brid);
		erbatch.setId(brid);
		return "stat_eroom_batch_gk";
	}

	/*public String stat_eroom_batch_view() throws ElException {
		// examRoom.setUserSize(statisticQuizDao.getERUserByDepidAndERid(examRoom
		// .getId(), department.getId()));
		List<MyRoom> mr = statisticQuizDao.listErbquiz_detail_view_Page(erbatch.getId(),getPageNow(),getPageSize());
		count = statisticQuizDao.listErbquiz_detail_view_Count(erbatch.getId());
		if(isExprot()){
			mr = statisticQuizDao.listErbquiz_detail_view_Page(erbatch.getId());
		}
		if (null != mr) {
			for (int i = 0; i < mr.size(); i++) {
				mr.get(i).setMyExamPapers(
						statisticQuizDao.list_detail_view_quizpaper(mr.get(i)
								.getExamroom().getId(), mr.get(i).getTester()
								.getId()));
			}
		}
		examRoom = new ExamRoom();
		examRoom.setMyrooms(mr);
		if(isExprot()){
			return "stat_eroom_batch_view_EXCEL";
		}
		return "stat_eroom_batch_view";
	}*/
	List<MyBatchRoom> mybatchrooms;
	public List<MyBatchRoom> getMybatchrooms() {
		return mybatchrooms;
	}

	public void setMybatchrooms(List<MyBatchRoom> mybatchrooms) {
		this.mybatchrooms = mybatchrooms;
	}

/*---->>修改前2-23	public String stat_eroom_batch_view() throws ElException {
		mybatchrooms =  statisticQuizDao.listErbquiz_detail_view_Page(erbatch.getId(),getPageNow(),getPageSize());
		count = statisticQuizDao.listErbquiz_detail_view_Count(erbatch.getId());
		myExamPapers = statisticQuizDao.listErbExampapers(erbatch.getId());
		if(isExprot()){
			mybatchrooms = statisticQuizDao.listErbquiz_detail_view_Page(erbatch.getId());
		}
		erbatch =eroomDao.getErbatchById(erbatch.getId());
//		if (null != mybatchrooms) {
//			for (int i = 0; i <mybatchrooms.size(); i++) {
//				mybatchrooms.get(i).setMyRooms(
//						statisticQuizDao.list_detail_view_quizpaper(mr.get(i)
//								.getExamroom().getId(), mr.get(i).getTester()
//								.getId()));
//			}
//		}
//		examRoom = new ExamRoom();
//		examRoom.setMyrooms(mr);
		if(isExprot()){
			return "stat_eroom_batch_view_EXCEL";
		}
		return "stat_eroom_batch_view";
	}
	*/
	//---->>修改后2-23
	List<MyRoom> myrooms;
	public List<MyRoom> getMyrooms() {
		return myrooms;
	}

	public void setMyrooms(List<MyRoom> myrooms) {
		this.myrooms = myrooms;
	}

	public String stat_eroom_batch_view() throws ElException {
		myrooms =  statisticQuizDao.listErbquiz_detail_view_Page(erbatch.getId(),getPageNow(),getPageSize());
		count = statisticQuizDao.listErbquiz_detail_view_Count(erbatch.getId());
		myExamPapers = statisticQuizDao.listErbExampapers(erbatch.getId());
		if(isExprot()){
			myrooms = statisticQuizDao.listErbquiz_detail_view_Page(erbatch.getId());
		}
		erbatch =eroomDao.getErbatchById(erbatch.getId());
		if(isExprot()){
			return "stat_eroom_batch_view_EXCEL";
		}
		return "stat_eroom_batch_view";
	}
	public String stat_eroom_batch_eval() throws ElException {
		if(isExprot()){
			departments = statisticQuizDao.listErBEval(erbatch.getId(), departments1);
			return "stat_eroom_batch_eval_EXCEL";
		}
		if(departments1 != null ){ 
			departments = statisticQuizDao.listErBEval(erbatch.getId(), departments1); 
		}else{
			departments = null;
		}
		myExamPapers = statisticQuizDao.listErbExampapers(erbatch.getId());
		department = department==null?new Department(1):department;
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		
		return "stat_eroom_batch_eval";

	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public StatisticQuizDao getStatisticQuizDao() {
		return statisticQuizDao;
	}

	public void setStatisticQuizDao(StatisticQuizDao statisticQuizDao) {
		this.statisticQuizDao = statisticQuizDao;
	}

	public StatisticCourseDao getStatisticCourseDao() {
		return statisticCourseDao;
	}

	public void setStatisticCourseDao(StatisticCourseDao statisticCourseDao) {
		this.statisticCourseDao = statisticCourseDao;
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

	public List<MyExamPaper> getMyExamPapers() {
		return myExamPapers;
	}

	public void setMyExamPapers(List<MyExamPaper> myExamPapers) {
		this.myExamPapers = myExamPapers;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}

	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}

	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}

	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}

	public ExamPaperDao getExamPaperDao() {
		return examPaperDao;
	}

	public void setExamPaperDao(ExamPaperDao examPaperDao) {
		this.examPaperDao = examPaperDao;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public Examprac getExamprac() {
		return examprac;
	}

	public void setExamprac(Examprac examprac) {
		this.examprac = examprac;
	}

	public List<MyEprac> getMyexampracs() {
		return myexampracs;
	}

	public void setMyexampracs(List<MyEprac> myexampracs) {
		this.myexampracs = myexampracs;
	}

	public MyEprac getMyeprac() {
		return myeprac;
	}

	public void setMyeprac(MyEprac myeprac) {
		this.myeprac = myeprac;
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

	public EroomBatchLib getErbatchlib() {
		return erbatchlib;
	}

	public void setErbatchlib(EroomBatchLib erbatchlib) {
		this.erbatchlib = erbatchlib;
	}

	public List<Department> getDepartments1() {
		return departments1;
	}

	public void setDepartments1(List<Department> departments1) {
		this.departments1 = departments1;
	}

	public boolean isExprot() {
		return exprot;
	}

	public void setExprot(boolean exprot) {
		this.exprot = exprot;
	}

	public MyRoom getMyroom() {
		return myroom;
	}

	public void setMyroom(MyRoom myroom) {
		this.myroom = myroom;
	}

	public List<BaseDatat> getJzs() {
		return jzs;
	}

	public void setJzs(List<BaseDatat> jzs) {
		this.jzs = jzs;
	}

	public List<BaseDatat> getJzs1() {
		return jzs1;
	}

	public void setJzs1(List<BaseDatat> jzs1) {
		this.jzs1 = jzs1;
	}

	public List<BaseDatat> getJzs2() {
		return jzs2;
	}

	public void setJzs2(List<BaseDatat> jzs2) {
		this.jzs2 = jzs2;
	}

	public List<EroomBlock> getErblocks() {
		return erblocks;
	}

	public void setErblocks(List<EroomBlock> erblocks) {
		this.erblocks = erblocks;
	}

	public EroomBlock getErblock() {
		return erblock;
	}

	public void setErblock(EroomBlock erblock) {
		this.erblock = erblock;
	}

	public ErblockDao getErblockDao() {
		return erblockDao;
	}

	public void setErblockDao(ErblockDao erblockDao) {
		this.erblockDao = erblockDao;
	}
}
