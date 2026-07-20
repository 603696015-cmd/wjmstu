package com.sopia.assistman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.assistman.dao.PollDao;
import com.sopia.assistman.dao.QuestionnaireDao;
import com.sopia.assistman.entities.Poll;
import com.sopia.assistman.entities.Questionnaire;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.QuestionRanking;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionLib;

public class PollAction extends BaseAction {
	private PollDao pollDao;
	private QuestionDao questionDao;
	private QuestionLib qlbTree;
	private Poll poll;
	private List<Poll> polls;
	private List<Question> questions;
	private Question question;
	private int sublibs;
	
	private Department depTree;
	private Department department;
	private int sub_department;
	private List<ELUser> elusers;
	private ELUser elUser;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	private List<ELUser> canAssignUsers;
	private List<Integer> answer;
	private QuestionRanking questionRanking;
	private Station stTree;
	private Station station;
	private Questionnaire questionnaire;
	private List<Questionnaire> questionnaires;
	private QuestionnaireDao questionnaireDao;
	
	public QuestionnaireDao getQuestionnaireDao() {
		return questionnaireDao;
	}
	public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
		this.questionnaireDao = questionnaireDao;
	}
	public List<Questionnaire> getQuestionnaires() {
		return questionnaires;
	}
	public void setQuestionnaires(List<Questionnaire> questionnaires) {
		this.questionnaires = questionnaires;
	}
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
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
	public QuestionRanking getQuestionRanking() {
		return questionRanking;
	}
	public void setQuestionRanking(QuestionRanking questionRanking) {
		this.questionRanking = questionRanking;
	}
	public List<Integer> getAnswer() {
		return answer;
	}
	public void setAnswer(List<Integer> answer) {
		this.answer = answer;
	}
	public List<ELUser> getCanAssignUsers() {
		return canAssignUsers;
	}
	public void setCanAssignUsers(List<ELUser> canAssignUsers) {
		this.canAssignUsers = canAssignUsers;
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
	public int getSub_department() {
		return sub_department;
	}
	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}
	public List<ELUser> getElusers() {
		return elusers;
	}
	public void setElusers(List<ELUser> elusers) {
		this.elusers = elusers;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
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
	public List<Poll> getPolls() {
		return polls;
	}
	public void setPolls(List<Poll> polls) {
		this.polls = polls;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public QuestionDao getQuestionDao() {
		return questionDao;
	}
	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
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
	public int getSublibs() {
		return sublibs;
	}
	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}
	public QuestionLib getQlbTree() {
		return qlbTree;
	}
	public void setQlbTree(QuestionLib qlbTree) {
		this.qlbTree = qlbTree;
	}
	/**
	 * 添加投票初始化
	 * @return
	 * @throws ElException
	 */
	public String addPollInit() throws ElException {
		return "addPoll";
	}
	/**
	 * 添加投票
	 * @return
	 * @throws ElException
	 */
	public String addPoll() throws ElException {
		poll.setCreater(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		if(poll.getQuestion()==null||poll.getQuestion().getId()==0){
			setElmessage("找不到对应的试题！");
			return "error";
		}
		pollDao.addPoll(poll);
		return "PollList";
	}
	/**
	 * 试题查询
	 * @return
	 * @throws ElException
	 */
	public String questionSearch() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		} else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		QuestionLib qlib = null;
		if (question == null || question.getQlib() == null
				|| question.getQlib().getId() <= 0) {
			qlib = qlbTree;
		} else {
			if(getSessionIntValue(ElConstants.SESSION_ROLE)!= 1&&!((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).checkNode(question.getQlib().getId(), qlbTree, "question_lib")){
				setElmessage("您输入了无权操作的节点！");
				return "error";
			}
			qlib = questionDao.getQLbById(question.getQlib().getId());
		}
		if(question == null){
			question = new Question();
			question.setStatus(-1);
			sublibs=1;
		}
		if(question.getQtype()==-1||question.getQtype()==0){
			question.setQtype(-3);
		}
		questions = questionDao.listMyQuestions(qlib, sublibs,
				question, getPageNow(), getPageSize());
		for (int i = 0; i < questions.size(); i++) {
			if(questions.get(i).getTitle().length()>45){
				questions.get(i).setTitle(questions.get(i).getTitle().substring(0,45)+"...");
			}
		}
		count = questionDao.listMyQuestionsSize(qlib, sublibs, question);
		return "questionSearch";
	}
	/**
	 * ajax查询试题信息
	 * @return
	 * @throws ElException
	 */
	public String questionSelect() throws ElException {
		question=questionDao.getQbyId(question.getId());
		printMsg("{'id':'"+question.getId()+"','title':'"+question.getTitle()+"','subject':'"+question.getSubject()+"'}");
		return null;
	}
	/**
	 * 我创建的投票列表页
	 * @return
	 * @throws ElException
	 */
	public String pollList() throws ElException {
		polls=pollDao.myPollList(poll,getPageNow(),getPageSize());
		count=pollDao.myPollListCount(poll);
		return "pollList";
	}
	/**
	 * 修改投票初始化
	 * @return
	 * @throws ElException
	 */
	public String alterPollInit() throws ElException {
		poll=pollDao.getPoolById(poll.getId());
		return "alterPoll";
	}
	/**
	 * 修改投票
	 * @return
	 * @throws ElException
	 */
	public String alterPoll() throws ElException {
		pollDao.updatePoll(poll);
		return "pollList";
	}
	/**
	 * 删除投票
	 * @param poll
	 * @throws ElException
	 */
	public String deletePoll() throws ElException{
		pollDao.deletePoll(poll.getId());
		return "pollList";
	}
	/**
	 * 查看投票
	 * @param poll
	 * @throws ElException
	 */
	public String pollInfo() throws ElException{
		poll=pollDao.getPoolById(poll.getId());
		return "pollInfo";
	}
	/**
	 * 投票分配人员列表页
	 * @return
	 * @throws ElException
	 */
	public String pollassignSearchlist() throws ElException {
		int depid = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		} else {
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
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		if(department==null||department.getId()<=0){
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		if (sub_department == 1) {
			department.setLower(true);
		}
		poll=pollDao.getPoolById(poll.getId());
		elusers = userDao.getDistributionStudents(department,station, depid, elUser,
				getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(),
				getPageSize());
		count = userDao.getDistributionStudentsCount(department, station,depid,
				elUser, getSessionIntValue(ElConstants.SESSION_ROLE));
		for (int i = 0; i < elusers.size(); i++) {
			if(pollDao.checkPollUser(poll.getId(), elusers.get(i).getId())){
				elusers.get(i).setIntroom(true);
			}else{
				elusers.get(i).setIntroom(false);
			}
		}
		
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		gangweis = userDao.getBaseDatatByTypeid(4);
		dishis = userDao.getBaseDatatByTypeid(5);
		return "pollassignSearchlist";
	}
	/**
	 * 投票分配
	 * @return
	 * @throws ElException
	 */
	public String pollassign() throws ElException {
		if (null != canAssignUsers&&poll!=null){
			for (int i = 0; i < canAssignUsers.size(); i++) {
				if(!pollDao.checkPollUser(poll.getId(), canAssignUsers.get(i).getId())){
					pollDao.addPollUser(poll.getId(), canAssignUsers.get(i).getId());
				}
			}
		}
		return "pollassignSearchlist";
	}
	/**
	 * 取消分配
	 * @return
	 * @throws ElException
	 */
	public String pollunassign() throws ElException {
		if (null != canAssignUsers&&poll!=null){
			for (int i = 0; i < canAssignUsers.size(); i++) {
				pollDao.deletePollUser(poll.getId(), canAssignUsers.get(i).getId());
			}
		}
		return "pollassignSearchlist";
	}
	/**
	 * 分配给全部搜索结果
	 * @return
	 * @throws ElException
	 */
	public String pollassignAll() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		} else {
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
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		if(department==null||department.getId()<=0){
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		if (sub_department == 1) {
			department.setLower(true);
		}
		elusers = userDao.getDistributionStudents(department, station,1, elUser,
				getSessionIntValue(ElConstants.SESSION_ROLE),99999,1);
		for (int i = 0; i < elusers.size(); i++) {
			if(!pollDao.checkPollUser(poll.getId(), elusers.get(i).getId())){
				pollDao.addPollUser(poll.getId(), elusers.get(i).getId());
			}
		}
		
		elusers = userDao.getDistributionStudents(department,station, 1, elUser,
				getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(),
				getPageSize());
		count = userDao.getDistributionStudentsCount(department, station,1,
				elUser, getSessionIntValue(ElConstants.SESSION_ROLE));
		for (int i = 0; i < elusers.size(); i++) {
			if(pollDao.checkPollUser(poll.getId(), elusers.get(i).getId())){
				elusers.get(i).setIntroom(true);
			}else{
				elusers.get(i).setIntroom(false);
			}
		}
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		gangweis = userDao.getBaseDatatByTypeid(4);
		dishis = userDao.getBaseDatatByTypeid(5);
		return "pollassignSearchlist";
	}
	/**
	 * 投票审核列表页
	 * @param poll
	 * @throws ElException
	 */
	public String pollShList() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		} else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		sublibs = department == null ? 1 : sublibs;
		if(department==null||department.getId()<=0){
			department=depTree;
		}else{
			department=departmentDao.getDepById(department.getId());
		}
		polls=pollDao.pollShList(department, sublibs, poll,getPageNow(),getPageSize());
		count=pollDao.pollShListCount(department, sublibs, poll);
		return "pollShList";
	}
	/**
	 * 投票审核
	 * @return
	 * @throws ElException
	 */
	public String pollSh() throws ElException{
		pollDao.updatePollStatus(poll.getId(), poll.getStatus());
		if(poll.getStatus()==1){//提交审核
			return "pollList";
		}else{
			return "pollShList";
		}
	}
	/**
	 * 学员投票列表
	 * @return
	 * @throws ElException
	 */
	public String studyPollList() throws ElException{
		polls=pollDao.studyPollList(getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		return "studyPollList";
	}
	/**
	 * 学员投票
	 * @return
	 * @throws ElException
	 */
	public String studyPoll() throws ElException{
		poll=pollDao.getPoolById(poll.getId());
		return "studyPoll";
	}
	/**
	 * 学员投票执行
	 * @return
	 * @throws ElException
	 */
	public String studyPollDo() throws ElException{
		poll=pollDao.getPoolById(poll.getId());
		if(answer!=null){
			int userid=getSessionIntValue(ElConstants.SESSION_USERID);
			if(!pollDao.checkUserIsPoll(poll.getId(), userid)){
				for (int i = 0; i < answer.size(); i++) {
					pollDao.addPollQuizinfo(poll.getId(), userid, answer.get(i));
				}
				pollDao.updateUserIsPoll(poll.getId(), userid);
			}else{
				setElmessage("您已经投过票了！");
				return "error";
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
	 * 我创建的问卷列表页
	 * @return
	 * @throws ElException
	 */
	public String questionnaireList() throws ElException {
		questionnaires=questionnaireDao.myQuestionnaireList(questionnaire,getPageNow(),getPageSize());
		count=questionnaireDao.myQuestionnaireListCount(questionnaire);
		return "questionnaireList";
	}
}
