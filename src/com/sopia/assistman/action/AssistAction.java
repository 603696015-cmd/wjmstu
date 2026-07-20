package com.sopia.assistman.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.assistman.PlanContants;
import com.sopia.assistman.dao.PlanDao;
import com.sopia.assistman.dao.SurveyAndPollDao;
import com.sopia.assistman.entities.Plan;
import com.sopia.assistman.entities.PlanStage;
import com.sopia.assistman.entities.PlanStuff;
import com.sopia.assistman.entities.PlanVerify;
import com.sopia.assistman.entities.Poll;
import com.sopia.assistman.entities.QstatInfo;
import com.sopia.assistman.entities.Survey;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.questionman.entities.StuffLib;

public class AssistAction extends BaseAction {
	private Plan plan;
	private List<Plan> plans;
	private List<PlanStage> planStages;
	private PlanStage planStage;
	private File st;
	private String stFileName;
	private String sfContentType;
	private PlanStuff planStuff;
	private String fileName;
	private StuffLib qstuff;
	private PlanDao planDao;
	private List<PlanVerify> planVerifys;
	private PlanVerify planVerify;

	private Survey survey;
	private List<Survey> surveys;
	private SurveyAndPollDao surveyAndPollDao;
	private ExamPaperLib eplTree;
	private ExamPaperDao examPaperDao;
	private int sublibs;
	private ExamPaper examPaper;
	private List<ExamPaper> examPapers;
	private List<QstatInfo> qstatInfos;
	private QstatInfo qstatInfo;
	private Question question;
	private List<Question> questions;
	private QuestionDao questionDao;
	private StuffDao stuffDao;
	private QuestionLib qlbTree;
	private Poll poll;
	private List<Poll> polls;
	
	
	public List<Poll> getPolls() {
		return polls;
	}

	public void setPolls(List<Poll> polls) {
		this.polls = polls;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public QuestionLib getQlbTree() {
		return qlbTree;
	}

	public void setQlbTree(QuestionLib qlbTree) {
		this.qlbTree = qlbTree;
	}

	public List<QstatInfo> getQstatInfos() {
		return qstatInfos;
	}

	public void setQstatInfos(List<QstatInfo> qstatInfos) {
		this.qstatInfos = qstatInfos;
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

	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}

	public SurveyAndPollDao getSurveyAndPollDao() {
		return surveyAndPollDao;
	}

	public void setSurveyAndPollDao(
			SurveyAndPollDao surveyAndPollDao) {
		this.surveyAndPollDao = surveyAndPollDao;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public List<PlanVerify> getPlanVerifys() {
		return planVerifys;
	}

	public void setPlanVerifys(List<PlanVerify> planVerifys) {
		this.planVerifys = planVerifys;
	}

	// private int source;
	public PlanDao getPlanDao() {
		return planDao;
	}

	public void setPlanDao(PlanDao planDao) {
		this.planDao = planDao;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public String assist_plan_addInit() throws ElException {
		return "assist_plan_add";
	}

	/**
	 * 培训计划制作第一步
	 * 
	 * @return
	 * @throws ElException
	 */
	public String assist_plan_add() throws ElException {
		try {
			if (null != plan) {
				plan.setManager(new ELUser(
						getSessionIntValue(ElConstants.SESSION_USERID)));
				plan.setId(planDao.addPlan(plan));
			}
		} catch (ElException e) {
			setElmessage("计划没有添加成功！");
			return "assist_plan_add";
		}
		return "assist_planstage_addInit";
	}

	public String assist_planstage_addInit() throws ElException {
		plan = planDao.getPlanByid(plan.getId());
		plan.setPlanStages(planDao.listPlanStageBYPid(plan.getId()));
		return "assist_planstage_add";
	}

	public String assist_planstage_alterInit() throws ElException {
		plan = planDao.getPlanByid(plan.getId());
		plan.setPlanStages(planDao.listPlanStageBYPid(plan.getId()));
		return "assist_planstage_alter";
	}

	public String assist_planstage_alter() throws ElException {
		if (null != planStage)
			planDao.alterPlanStage(planStage);
		plan = planDao.getPlanByid(plan.getId());
		plan.setPlanStages(planDao.listPlanStageBYPid(plan.getId()));
		setElmessage("修改成功！");
		return "assist_planstage_alter";
	}

	/**
	 * 培训计划第二步
	 * 
	 * @return
	 * @throws ElException
	 */
	public String assist_planstage_add() throws ElException {

		for (int i = 0; i < planStages.size(); i++) {
			planStages.get(i).setPlan(plan);
			planDao.addPlanStage(planStages.get(i));
		}
		return "assist_planstage_add_success";
	}

	public String assist_planstage_delete() throws ElException {
		if (null != planStage)
			planDao.deletePlanStage(planStage.getId());
		plan = planDao.getPlanByid(plan.getId());
		plan.setPlanStages(planDao.listPlanStageBYPid(plan.getId()));
		return "assist_planstage_delete";
	}

	/**
	 * 培训计划列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String assist_plan_list() throws ElException {
		plans = planDao.listPlansByUid(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		return "assist_plan_list";
	}

	public String assist_plan_apply() throws ElException {

		if (null != plan) {
			// if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
			// planDao.planStatusSet(plan.getId(),
			// PlanContants.PLAN_STATUS_YES,getSessionIntValue(ElConstants.SESSION_USERID));
			// }else
			// planDao.planStatusSet(plan.getId(),
			// PlanContants.PLAN_STATUS_SHWAITING,getSessionIntValue(ElConstants.SESSION_USERID));
			// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			planDao.planVerifySet(plan.getId(),
					PlanContants.PLAN_STATUS_YES,
					getSessionIntValue(ElConstants.SESSION_USERID),
					getSessionIntValue(ElConstants.SESSION_ROLE));
			// else {
			// planDao.planVerifySet(plan.getId(),
			// PlanContants.PLAN_STATUS_SHWAITING,
			// getSessionIntValue(ElConstants.SESSION_USERID),
			// getSessionIntValue(ElConstants.SESSION_ROLE));
			//
			// }
		}
		return "assist_plan_list";
	}
	public String assist_plan_view() throws ElException {
		plan = planDao.getPlanByid(plan.getId());
		plan.setPlanStages(planDao.listPlanStageBYPid(plan.getId()));
		return "assist_plan_view";
	}
	public String assist_plan_stuffaddInit() throws ElException {
		plan = planDao.getPlanByid(plan.getId());
		planStage = planDao.getpStageById(planStage.getId());
		return "assist_plan_stuffadd";
	}
	public String assist_plan_stuffadd () throws ElException, Exception {
		if (null != st) {
			if (st.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "assist_plan_stuffadd";
			} else {
				String ext = J2EEFileUtil.getExtention(stFileName);
				if (null != stFileName)
					qstuff.setTitle(stFileName.substring(0, stFileName
							.lastIndexOf(".")));
				else
					qstuff.setTitle("未命名");

				qstuff.setFileext(ext);
				qstuff.setOwner(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
				qstuff.setLength(st.length());
				int id = stuffDao.addQstuff(qstuff);
				qstuff.setId(id);
				planStuff.setStuff(qstuff);
				planDao.addPlanStageStuff(planStuff);
				J2EEFileUtil.upload(st, ext, "elstuffs", id + "");
				
			}
		} else {
			setElmessage("请输入上传文件");
			return "assist_plan_stuffadd";

		}
		return "assist_plan_stuffadd_success";
	}
	public String assist_plan_stufflist() throws ElException {
		plan = planDao.getPlanByid(plan.getId());
		planStage = planDao.getpStageById(planStage.getId());
		planStage.setPlanStuffs(planDao.listPStuffByPsId(planStage.getId()));
		return "assist_plan_stufflist";
	}
	public String assist_plan_viewstufflist() throws ElException {
		plan = planDao.getPlanByid(plan.getId());
		planStage = planDao.getpStageById(planStage.getId());
		planStage.setPlanStuffs(planDao.listPStuffByPsId(planStage.getId()));
		return "assist_plan_viewstufflist";
	}
	public String assist_plan_stuffdelete() throws ElException,Exception {
		plan = planDao.getPlanByid(plan.getId());
		planStage.setPlanStuffs(planDao.listPStuffByPsId(planStage.getId()));
		//删除计划资料
		planDao.deletePlanStageStuff(planStuff.getId());
		//删除资料
		int id = planStuff.getStuff().getId();
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		
		qstuff = stuffDao.getStuffbyId(id, userid);
		
		J2EEFileUtil.deleteFile("elstuffs", id + "", qstuff
				.getFileext());
		stuffDao.deleteQs(planStuff.getStuff().getId(), userid);
		
		return "assist_plan_stuffdelete";
	}
	public InputStream getInputStream() throws ElException {
		InputStream is = null;
		String path=ServletActionContext.getServletContext().getRealPath("/elstuffs/"+fileName);
		try {
			is = new FileInputStream(path);
		} catch (Exception e) {
			throw new ElException("下载资料出错",e);
		}
        return is;
	}
	public String assist_plan_stuff_download() throws ElException {
		try {
			getInputStream();
		} catch (Exception e) {
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		return "assist_plan_stuff_download";
	}
	public String assist_plan_alterInit() throws ElException {
		plan = planDao.getPlanByid(plan.getId());
		return "assist_plan_alter";
	}

	public String assist_plan_alter() throws ElException {
		planDao.alterPlan(plan);
		return "assist_plan_alter_success";
	}

	public String assist_plan_verifylist() throws ElException {
		plans = planDao.listVerfiyPlans(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), getPageNow(), getPageSize());
		return "assist_plan_verifylist";
	}

	public String assist_plan_carryoutInit() throws ElException {

		plan = planDao.getPlanByid(plan.getId());
		plan.setPlanStages(planDao.listPlanStageBYPid(plan.getId()));
		return "assist_plan_carryout";
	}

	public String assist_plan_verifyview() throws ElException {
		plan = planDao.getPlanByid(plan.getId());
		planVerifys = planDao.getPlanVerfiysByPid(plan.getId());
		return "assist_plan_verifyview";
	}

	public String assist_plan_verify() throws ElException {
		planDao.planVerifySet(plan.getId(), planVerify.getStatus(),
				getSessionIntValue(ElConstants.SESSION_USERID),
				getSessionIntValue(ElConstants.SESSION_ROLE));
		plan = planDao.getPlanByid(plan.getId());
		planVerifys = planDao.getPlanVerfiysByPid(plan.getId());
		return "assist_plan_verify_success";
	}

	public String assist_plan_viewlist() throws ElException {
		plans = planDao.listPlansByDepid(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), getPageNow(), getPageSize());
		return "assist_plan_viewlist";
	}

	public String assist_plan_viewview() throws ElException {
		plan = planDao.getPlanByid(plan.getId());
		plan.setPlanStages(planDao.listPlanStageBYPid(plan.getId()));
		int totaldays = 0;
		int stagePro = 0;
		if (null != plan.getPlanStages())
			for (int i = 0; i < plan.getPlanStages().size(); i++) {
				totaldays += plan.getPlanStages().get(i).getRealdays();
				if (null != plan.getPlanStages().get(i).getRealfinishdate()) {
					stagePro++;
				}
			}
		plan.setTotaldays(totaldays);
		plan.setStagePro(stagePro * 1.00f / plan.getPlanStages().size());

		return "assist_plan_viewview";
	}

	public String assist_plan_carryout() throws ElException {
		if (null != planStages) {
			for (int j = 0; j < planStages.size(); j++) {
				planDao.planStageCarryout(planStages.get(j));
			}
		}
		plan = planDao.getPlanByid(plan.getId());
		plan.setPlanStages(planDao.listPlanStageBYPid(plan.getId()));
		return "assist_plan_carryout";
	}

	/***************************************************************************
	 * 问卷调查
	 */
	public String assist_survey_addInit() throws ElException {
		return "assist_survey_add";
	}

	public String assist_survey_epsearchInit() throws ElException { 
//		eplTree = examPaperDao.epLibTree(0,
//				getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		
		return "assist_survey_epsearchInit";
	}

	public String assist_survey_epsearchlist() throws ElException {
//		if (sublibs == 1) {// 包含下级类别
//			if(getSessionIntValue(ElConstants.SESSION_ROLE)==1){
//				examPapers = examPaperDao.listEpsByEplId(examPaper.getEpl()
//						.getId(), examPaper.getTitle(),  true," and ep.status != 1 ", getPageNow(),
//						getPageSize());
//				count = examPaperDao.listEpsByEpIdSize(examPaper.getEpl()
//						.getId(), examPaper.getTitle(),  true," and ep.status != 1 ");
//			}else{
//				eplTree = examPaperDao.epLibTree("op",
//						getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
//				examPapers = examPaperDao.listEpsByEplId(eplTree,examPaper.getEpl()
//						.getId(), examPaper.getTitle(),  true," and ep.status != 1 ", getPageNow(),
//						getPageSize());
//				count = examPaperDao.listEpsByEpIdSize(eplTree,examPaper.getEpl()
//						.getId(), examPaper.getTitle(),  true," and ep.status != 1 ");
//			}
			
//			examPaperDao.li
//			examPapers = examPaperDao.listEpsByEplId(eplid, title, true,getPageNow(), getPageSize());
//			count = examPaperDao.listEpsByEpIdSize(eplid, title, true);

//		} else {
//			examPapers = examPaperDao.listEpsByEplId(examPaper.getEpl()
//					.getId(), examPaper.getTitle(),  false," and ep.status != 1 ", getPageNow(),
//					getPageSize());
//			count = examPaperDao.listEpsByEpIdSize(examPaper.getEpl()
//					.getId(), examPaper.getTitle(),  false," and ep.status != 1 ");
//
//		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		} else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		ExamPaperLib epl = null;
		if (examPaper == null || examPaper.getEpl() == null
				|| examPaper.getEpl().getId() <= 0) {
			epl = eplTree;
			sublibs = 1;
		} else {
			if(getSessionIntValue(ElConstants.SESSION_ROLE)!= 1&&!((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).checkNode(examPaper.getEpl().getId(), eplTree, "exampaperlib")){
				setElmessage("您输入了无权操作的节点！");
				return "error";
			}
			epl = examPaperDao
					.getEpLById(examPaper.getEpl().getId());
		}
		examPapers = examPaperDao.listEpsByEplId(epl, sublibs,
				examPaper, getPageNow(), getPageSize(),0);
		count = examPaperDao.listEpsByEpIdSize(epl, sublibs, examPaper,0);
		return "assist_survey_epsearchlist";
	}

	public String assist_survey_add() throws ElException {
		survey.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		surveyAndPollDao.addSurvey(survey);
		return "assist_survey_add_success";
	}

	public String assist_survey_list() throws ElException {
		surveys = surveyAndPollDao.listMySurvey(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		count = surveyAndPollDao.listMySurveySize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "assist_survey_list";
	}
	public String assist_survey_result() throws ElException {
		survey = surveyAndPollDao.getSurvey(survey.getId());
		survey.setCanViewResult(false);
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==4){
			if(survey.getStureadresult())
				survey.setCanViewResult(true);
		}else{
			survey.setCanViewResult(true);
		}
		if(survey.getCanViewResult()){
			qstatInfos = surveyAndPollDao.listQstatinfoBySurid(survey.getId());
		}
		return "assist_survey_result";
	}
	public String assist_survey_delete() throws ElException {
		if(null!=survey)
		surveyAndPollDao.deleteSurvey(survey.getId());
		return "assist_survey_list";
	}
	public String assist_survey_alterInit() throws ElException {
		survey = surveyAndPollDao.getSurvey(survey.getId());
		return "assist_survey_alter";
	}

	public String assist_survey_alter() throws ElException {
		surveyAndPollDao.alterSurvey(survey);
		
		return "assist_survey_alter_success";
	}
	//投票：
	public String assist_poll_addInit() throws ElException {
		return "assist_poll_add";
	}

	public String assist_poll_add() throws ElException {
		poll.setCreater(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		surveyAndPollDao.addPoll(poll);
		return "assist_poll_add_success";
	}
	public String assist_poll_qsearchInit() throws ElException {
		qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
				getSessionIntValue(ElConstants.SESSION_USERID),
				ElConstants.TREE_FIANL, true);
		return "assist_poll_qsearchInit";
	}
	public String assist_poll_qsearchlist() throws ElException {
		if (sublibs == 1)  {
			questions = questionDao.listMyQuestions(question.getTitle(), question
							.getQlib().getId(), question.getQtype(), true, getPageNow(),
					getPageSize());
			count = questionDao.listMyQuestionsSize(question.getTitle() , question
							.getQlib().getId(), question.getQtype(),true);
		}
		 else{
			questions = questionDao.listMyQuestions(question.getTitle(), question
							.getQlib().getId(), question.getQtype(), false, getPageNow(),
					getPageSize());
			count = questionDao.listMyQuestionsSize(question.getTitle(), question
							.getQlib().getId(), question.getQtype(),false);
		 }
		return "assist_poll_qsearchlist";
	}
	public String assist_poll_list() throws ElException {
		polls = surveyAndPollDao.listMyPoll(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		count = surveyAndPollDao.listMyPollSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "assist_poll_list";
	}
	public String assist_poll_result() throws ElException {
		poll = surveyAndPollDao.getPoll(poll.getId());
		poll.setCanViewResult(false);
		if(getSessionIntValue(ElConstants.SESSION_ROLE)==4){
			if(poll.getStureadresult())
				poll.setCanViewResult(true);
		}else{
			poll.setCanViewResult(true);
		}
		if(poll.getCanViewResult()){
//			qstatInfos = surveyAndPollDao.listQstatinfoBySurid(poll.getId());
			qstatInfo = surveyAndPollDao.getQstatinfoByPollid(poll.getId());
		}
		return "assist_poll_result";
	}
	public String assist_poll_delete() throws ElException {
		if(null!=poll)
		surveyAndPollDao.deletePoll(poll.getId());
		return "assist_poll_list";
	}
	public String assist_poll_alterInit() throws ElException {
		poll = surveyAndPollDao.getPoll(poll.getId());
		return "assist_poll_alter";
	}

	public String assist_poll_alter() throws ElException {
		surveyAndPollDao.alterPoll(poll);
		
		return "assist_poll_alter_success";
	}
	public List<PlanStage> getPlanStages() {
		return planStages;
	}

	public void setPlanStages(List<PlanStage> planStages) {
		this.planStages = planStages;
	}

	public List<Plan> getPlans() {
		return plans;
	}

	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

	public PlanStage getPlanStage() {
		return planStage;
	}

	public void setPlanStage(PlanStage planStage) {
		this.planStage = planStage;
	}

	public PlanVerify getPlanVerify() {
		return planVerify;
	}

	public void setPlanVerify(PlanVerify planVerify) {
		this.planVerify = planVerify;
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
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

	public PlanStuff getPlanStuff() {
		return planStuff;
	}

	public void setPlanStuff(PlanStuff planStuff) {
		this.planStuff = planStuff;
	}

	public StuffLib getQstuff() {
		return qstuff;
	}

	public void setQstuff(StuffLib qstuff) {
		this.qstuff = qstuff;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public QstatInfo getQstatInfo() {
		return qstatInfo;
	}

	public void setQstatInfo(QstatInfo qstatInfo) {
		this.qstatInfo = qstatInfo;
	}

	public StuffDao getStuffDao() {
		return stuffDao;
	}

	public void setStuffDao(StuffDao stuffDao) {
		this.stuffDao = stuffDao;
	}
}
