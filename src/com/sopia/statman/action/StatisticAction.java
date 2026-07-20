package com.sopia.statman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.assistman.dao.OfflineDao;
import com.sopia.assistman.entities.Offline;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.ScoreOperate;
import com.sopia.common.ScoreSet;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseNote;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.statman.dao.StatisticDao;
import com.sopia.statman.entities.Resources;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyEprac;
import com.sopia.studyman.entities.MyExamPaper;

/**
 * Description: 其他部分统计（部门账号、线下培训等)对应struts: com/sopia/common/configs/statman.xml
 * Copyright (c) Department of Research and Development/wenyishun110@163.com.
 * All Rights Reserved.
 * 
 * @version 1.0 2011-9-4 上午12:09:17 by 闻益舜（wenyishun110@163.com）创建
 */
public class StatisticAction extends BaseAction {
	private Department depTree;
	private Department department;
	private ELUser elUser;

	private int sub_department;
	private List<ELUser> elUsers;
	private StatisticDao statisticDao;
	List<Offline> offlines;
	Offline offline;
	OfflineDao offlineDao;
	private Resources resources;
	private int elclassid;
	private List<ElClass> elClasss;

	public List<ElClass> getElClasss() {
		return elClasss;
	}

	public void setElClasss(List<ElClass> elClasss) {
		this.elClasss = elClasss;
	}

	public int getElclassid() {
		return elclassid;
	}

	public void setElclassid(int elclassid) {
		this.elclassid = elclassid;
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public String user_searchInit() throws ElException {
		int roleid=getSessionIntValue(ElConstants.SESSION_ROLE);
		if(roleid==1){
			depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		}else{
			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
		}
		return "user_searchInit";
	}

	public String user_searchlist() throws ElException {
//		elUsers = statisticDao.getStatUserByDep(department.getId(),
//				sub_department, elUser, getPageNow(), getPageSize());
		/*
		 * if(null!=elUsers) for (int i = 0; i < elUsers.size(); i++) { ELUser
		 * eu = elUsers.get(i); Company c = new Company(); Department dep =
		 * departmentDao.getSecondDep(eu.getDepartment().getId());
		 * c.setName(dep.getName()); elUsers.get(i).setCompany(c); }
		 */
//		count = statisticDao.getStatUserByDepCount(department.getId(),
//				sub_department, elUser);
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
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		sub_department = elUser == null ? 1 : sub_department;
		elUsers = statisticDao.getStatUserByDep(department,sub_department, elUser, getPageNow(), getPageSize());
		count = statisticDao.getStatUserByDepCount(department,sub_department, elUser);
		return "user_searchlist";
	}

	/**
	 * 人才搜索初始化
	 * 
	 * @return
	 * @throws ElException
	 */
	public String stat_talent_searchInit() throws ElException {
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "stat_talent_searchInit";
	}
/**
	public String stat_talent_searchlist() throws ElException {
		elUsers = statisticDao.getStatTalentByDep(department.getId(),
				sub_department, elUser, getPageNow(), getPageSize());
		count = statisticDao.getStatTalentByDepSize(department.getId(),
				sub_department, elUser);
		return "stat_talent_searchlist";
	}*/
	public String stat_talent_searchlist() throws ElException { 
		elclassid = elClasss  != null ? elClasss.get(0).getId() : 0 ; 
		
		elUsers = statisticDao.getStatTalentByDep(department.getId(),
				sub_department, elUser,elclassid, getPageNow(), getPageSize());
		count = statisticDao.getStatTalentByDepSize(department.getId(),
				sub_department, elUser,elclassid);
		return "stat_talent_searchlist"; 
	}
/**
	public String stat_talent_list() throws ElException {
		elUsers = statisticDao.getStatTalentByDep(department.getId(),
				sub_department, elUser);
		return "stat_talent_list";
	}*/
	public String stat_talent_list() throws ElException { 
		elclassid = elClasss  != null ? elClasss.get(0).getId() : 0 ; 
		elUsers = statisticDao.getStatTalentByDep(department.getId(),
				sub_department, elUser,elclassid);
		return "stat_talent_list";
	}


	public String user_view() throws ElException {
		elUser = userDao.getUserById(elUser.getId());
		statisticDao.setStatUser(elUser);
		return "user_view";
	}

	// /------部门统计
	public String dep_searchInit() throws ElException {
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "dep_searchInit";
	}

	public String dep_searchlist() throws ElException {
		// departments = statisticDao.listDepinfo(department.getId());
		department = statisticDao.getDepinfo(department.getId());
		// getPageSize()=getPageSize()==0?10:getPageSize();
		elUsers = statisticDao.getDepUserCredit(department.getId(),
				getPageNow(), getPageSize());
		count = statisticDao.getDepUserCreditSize(department.getId());
		return "dep_searchlist";
	}

	public String dep_stat_view() throws ElException {
		department = departmentDao.getDepById(department.getId());
		return "dep_stat_view";
	}

	// 学分积分点数统计
	public String score_dot_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		int subdep = ElConstants.SUBOP_YES;

		elUsers = ScoreOperate.getUserByDepId(depid, subdep, "dot",
				getPageNow(), getPageSize());
		count = ScoreOperate.getUserByDepIdSize(depid, subdep);
		return "score_dot_list";
	}

	public String score_dot_view() throws ElException {
		elUser = userDao.getUserById(elUser.getId());
		ScoreSet scoreSet = new ScoreSet();
		int userid = elUser.getId();
		scoreSet.setDian_forum_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.DIAN_FORUM_DO));
		scoreSet.setDian_login_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.DIAN_LOGIN_DO));
		scoreSet.setDian_study_cp_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.DIAN_STUDY_CP_DO));
		scoreSet.setDian_study_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.DIAN_STUDY_DO));
		scoreSet.setDian_topic_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.DIAN_TOPIC_DO));
		// elUser.setScoreset(scoreSet);
		return "score_dot_view";
	}

	public String score_score_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		int subdep = ElConstants.SUBOP_YES;

		elUsers = ScoreOperate.getUserByDepId(depid, subdep, "score",
				getPageNow(), getPageSize());
		count = ScoreOperate.getUserByDepIdSize(depid, subdep);
		return "score_score_list";
	}

	public String score_score_view() throws ElException {
		elUser = userDao.getUserById(elUser.getId());
		ScoreSet scoreSet = new ScoreSet();
		int userid = elUser.getId();
		scoreSet.setScore_course_apply(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_COURSE_APPLY));
		scoreSet.setScore_forum_jh(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_FORUM_JH));
		scoreSet.setScore_knowledge_tj(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_KNOWLEDGE_TJ));
		scoreSet.setScore_ktroom_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_KTROOM_DO));
		scoreSet.setScore_mess_send(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_MESS_SEND));
		scoreSet.setScore_note_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_NOTE_DO));
		scoreSet.setScore_poll_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_POLL_DO));
		scoreSet.setScore_prac_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_PRAC_DO));
		scoreSet.setScore_simp_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_SIMP_DO));
		scoreSet.setScore_survey_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_SURVEY_DO));
		scoreSet.setScore_ztroom_do(ScoreOperate.getScoreByOp(userid,
				ElConstants.SCORE_ZTROOM_DO));
		// elUser.setScoreset(scoreSet);
		return "score_score_view";
	}

	public String score_xfscore_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		int subdep = ElConstants.SUBOP_YES;

		elUsers = ScoreOperate.getUserByDepId(depid, subdep, "xfscore",
				getPageNow(), getPageSize());
		count = ScoreOperate.getUserByDepIdSize(depid, subdep);
		return "score_xfscore_list";
	}

	public String score_zhscore_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT);
		int subdep = ElConstants.SUBOP_YES;

		elUsers = ScoreOperate.getUserByDepId(depid, subdep,
				"(xfscore+score/10+dot/100)", getPageNow(), getPageSize());
		count = ScoreOperate.getUserByDepIdSize(depid, subdep);
		return "score_zhscore_list";
	}

	
	public String offline_stat_seachinit() throws ElException {

		return "offline_stat_seach";
	}

	public String offline_stat_seach() throws ElException {
		offlines = statisticDao.listStatOfflines(offline.getName(), offline
				.getBegintime(), offline.getEndtime(), getPageNow(),
				getPageSize());
		count = statisticDao.listStatOfflinesSize(offline.getName(), offline
				.getBegintime(), offline.getEndtime());
		return "offline_stat_seachlist";
	}

	public String offline_stat_detail() throws ElException {
		offline = statisticDao.getStatOffline(offline.getId());
		return "offline_stat_detail";
	}

	public String offline_stat_userdetail() throws ElException {
		offline = statisticDao.getStatOffline(offline.getId());
		elUsers = statisticDao.listStatOffline2Users(offline.getId());
		return "offline_stat_userdetail";
	}

	public String usercredit_stat_searchinit() throws ElException {
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);

		return "usercredit_stat_search";
	}

	public String usercredit_stat_search() throws ElException {
		elUsers = statisticDao.getStatUcreditUserByDep(department.getId(),
				sub_department, elUser, getPageNow(), getPageSize());
		// if (null != elUsers)
		// for (int i = 0; i < elUsers.size(); i++) {
		// elUsers.get(i).setMyCnotes(
		// studyCourseDao.listMyCnotes(elUsers.get(i).getId()));
		// }
		count = statisticDao.getStatUcreditUserByDepCount(department.getId(),
				sub_department, elUser);

		return "usercredit_stat_searchlist";
	}
//	//资源统计
//	public String resourcesstatistics() throws ElException {
//		resources=statisticDao.getResourceStatistic();
//		return "resourcesstatistics";
//	}
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

	public StatisticDao getStatisticDao() {
		return statisticDao;
	}

	public void setStatisticDao(StatisticDao statisticDao) {
		this.statisticDao = statisticDao;
	}

	public List<Offline> getOfflines() {
		return offlines;
	}

	public void setOfflines(List<Offline> offlines) {
		this.offlines = offlines;
	}

	public Offline getOffline() {
		return offline;
	}

	public void setOffline(Offline offline) {
		this.offline = offline;
	}

	public OfflineDao getOfflineDao() {
		return offlineDao;
	}

	public void setOfflineDao(OfflineDao offlineDao) {
		this.offlineDao = offlineDao;
	}
}
