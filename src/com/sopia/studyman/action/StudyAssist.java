package com.sopia.studyman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.assistman.dao.SurveyAndPollDao;
import com.sopia.assistman.entities.Poll;
import com.sopia.assistman.entities.Survey;
import com.sopia.common.ElException;
import com.sopia.common.ExamPaperUtil;
import com.sopia.common.ScoreOperate;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyKTRoomC;
import com.sopia.studyman.entities.MyPollQuesion;
import com.sopia.studyman.entities.MySurvyEP;
import com.sopia.studyman.entities.MyZTRoom;
import com.sopia.talentman.dao.TalentDao;
import com.sopia.talentman.entities.KTRoom;
import com.sopia.talentman.entities.KTRoomColl;
import com.sopia.talentman.entities.ZTRoom;

public class StudyAssist extends BaseAction {
	private List<Survey> surveys;
	private Survey survey;
	private SurveyAndPollDao surveyAndPollDao;
	private ExamPaper examPaper;
	private ExamPaperDao examPaperDao;
	private MySurvyEP mySurvyEP;
	private List<Poll> polls;
	private Poll poll;
	private QuestionDao questionDao;
	private MyPollQuesion mpollq;
	private List<KTRoomColl> trooms;
	private KTRoomColl troom;
	private KTRoom qtroom;
	private List<MyZTRoom> mytrooms;
	private List<MyKTRoomC> myktroomcs;
	private MyKTRoomC myktroomc;
	private TalentDao talentDao;
	private MyExamPaper myExamPaper;
	private List<MyZTRoom> myztrooms;
	private MyZTRoom myztroom;
	private ELUser elUser;

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}

	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}

	public List<KTRoomColl> getTrooms() {
		return trooms;
	}

	public void setTrooms(List<KTRoomColl> trooms) {
		this.trooms = trooms;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public MySurvyEP getMySurvyEP() {
		return mySurvyEP;
	}

	public void setMySurvyEP(MySurvyEP mySurvyEP) {
		this.mySurvyEP = mySurvyEP;
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

	public List<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	public SurveyAndPollDao getSurveyAndPollDao() {
		return surveyAndPollDao;
	}

	public void setSurveyAndPollDao(SurveyAndPollDao surveyAndPollDao) {
		this.surveyAndPollDao = surveyAndPollDao;
	}

	public String student_survey_list() throws ElException {
		surveys = surveyAndPollDao.listSurveyByDepid(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
				getPageNow(), getPageSize());
		count = surveyAndPollDao
				.listSurveyByDepidSize(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
		return "student_survey_list";
	}

	public String student_poll_list() throws ElException {
		polls = surveyAndPollDao.listPollByDepid(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
				getPageNow(), getPageSize());
		count = surveyAndPollDao
				.listPollByDepidSize(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
		return "student_poll_list";
	}

	public String student_poll_doInit() throws ElException {
		// TODO 投票制作
		poll = surveyAndPollDao.getPoll(poll.getId());
		poll.setQuestion(questionDao
				.getQuestionByid(poll.getQuestion().getId()));
		getRequest().setAttribute("question", poll.getQuestion());
		MyPollQuesion mpq = new MyPollQuesion();
		mpq
				.setTester(new ELUser(
						getSessionIntValue(ElConstants.SESSION_USERID)));
		mpq.setPoll(poll);
		if (surveyAndPollDao.pollDoCheck(mpq)) {
			setElmessage("您已经参与了");
			return "error";
		}
		return "student_poll_do";
	}

	public String student_poll_do() throws ElException {
		// TODO 投票制作
		mpollq.setTester(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		mpollq.setMyAnswer(ExamPaperUtil.getParamCombString(getRequest()));
		if (surveyAndPollDao.pollDoCheck(mpollq)) {
			setElmessage("您已经参与了");
			return "error";
		}
		surveyAndPollDao.pollDoSubmit(mpollq);
		ScoreOperate.setScore(getSessionIntValue(ElConstants.SESSION_USERID),
				ElConstants.SCORE_POLL_DO);
		return "assist_poll_result";
	}

	public String student_survey_doInit() throws ElException {
		survey = surveyAndPollDao.getSurvey(survey.getId());
		examPaper = examPaperDao
				.getEPAllInfoById(survey.getExamPaper().getId());
		MySurvyEP mep = new MySurvyEP();
		mep
				.setTester(new ELUser(
						getSessionIntValue(ElConstants.SESSION_USERID)));
		mep.setSurvey(survey);
		if (surveyAndPollDao.surveyDoCheck(mep)) {
			setElmessage("您已经参与了");
			return "error";
		}
		return "student_survey_do";
	}

	public String student_survey_do() throws ElException {

		mySurvyEP.setTester(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		mySurvyEP.setMyAnswer(ExamPaperUtil.getParamCombString(getRequest()));
		if (surveyAndPollDao.surveyDoCheck(mySurvyEP)) {
			setElmessage("您已经参与了");
			return "error";
		}
		surveyAndPollDao.surveyDoSubmit(mySurvyEP);
		ScoreOperate.setScore(getSessionIntValue(ElConstants.SESSION_USERID),
				ElConstants.SCORE_SURVEY_DO);
		return "assist_survey_result";
	}

	/* 人才库部分 */
	public String student_talent_troom_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myktroomcs = talentDao.listTroomByUid(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = talentDao
				.listTroomByUidSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "student_talent_troom_list";
	}

	// 测评查看
	public String student_talent_troom_view() throws ElException {
		// troom = talentDao.getTRoomById(troom.getId());
		troom = talentDao.getTRCbyId(troom.getId());
		troom.setTrooms(talentDao.listTroomByTRCId(troom.getId()));
		return "student_talent_troom_view";
	}

	public String student_talent_quiz() throws ElException {
		qtroom = talentDao.getTRoomById(qtroom.getId());
		if (!talentDao.hasInTRoom(
				getSessionIntValue(ElConstants.SESSION_USERID), qtroom.getId())) {
			talentDao.intoTroomEp(
					getSessionIntValue(ElConstants.SESSION_USERID), qtroom
							.getId());
			examPaper = examPaperDao.getEPAllInfoById(qtroom.getExampaper()
					.getId());
			myExamPaper = new MyExamPaper();
			ScoreOperate.setScore(
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.SCORE_KTROOM_DO);
		} else {
			setElmessage("已经完成答卷！");
			return "error";
		}
		return "student_talent_quiz";
	}

	public String student_talent_quiz_save() throws ElException {

		myExamPaper.setTester(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
//		myExamPaper.setMyAnswer(ExamPaperUtil.getParamCombString(getRequest()));
		talentDao.evalquizsave(myExamPaper);
		return null;
	}

	public String student_talent_quiz_submit() throws ElException {
		myExamPaper.setTester(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
//		myExamPaper.setMyAnswer(ExamPaperUtil.getParamCombString(getRequest()));
		talentDao.evalquizsubmit(myExamPaper);

		setElmessage("试卷提交成功！");
		return "student_talent_quiz_submit";
	}

	public String student_talent_mytroom_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myktroomcs = talentDao.listTroomByUid(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = talentDao
				.listTroomByUidSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "student_talent_mytroom_list";
	}

	public String student_talent_mytroom_result() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myktroomcs = talentDao.listTroomByUid(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = talentDao
				.listTroomByUidSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "student_talent_mytroom_result";
	}

	public String student_talent_mytroom_result_view() throws ElException {
		// myktroomcs = talentDao.getTRoomById(troom.getId());
		// mytroom =
		// talentDao.getMyTRoomByUidAndTRid(getSessionIntValue(ElConstants.SESSION_USERID),
		// troom.getId()) ;
		myktroomc = talentDao.getMkTroomByTid(troom.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID));
		myktroomc.setMyktrooms(talentDao.listMkTroomByTid(troom.getId(),
				getSessionIntValue(ElConstants.SESSION_USERID)));
		return "student_talent_mytroom_result_view";
	}

	/**
	 * 测评答卷查看。。
	 * 
	 * @return
	 * @throws ElException
	 */
	public String student_talent_mytroom_paper_view() throws ElException {
		myExamPaper = talentDao.getKtroomPaper(
				getSessionIntValue(ElConstants.SESSION_USERID), qtroom.getId());
		examPaper = examPaperDao.getExamPaperById(myExamPaper.getExamPaper()
				.getId());
//		if (null == myExamPaper.getMyAnswer()
//				|| "".equals(myExamPaper.getMyAnswer().trim())) {
//			examPaper = examPaperDao.getEPAllInfoById(examPaper.getId());
//		} else {
//			if (examPaper.getId() != 0) {
//				examPaper.setEpBlocks(new ArrayList<ExamPaperBlock>());
//				ExamPaperUtil.getAnswerExampaper(myExamPaper.getMyAnswer(),
//						examPaper, examPaperDao, questionDao,null);
//			}
//		}
		return "student_talent_mytroom_paper_view";
	}

	/**
	 * 主观评价场次
	 * 
	 * @return
	 */
	public String student_talent_ztroom_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myztrooms = talentDao.listMyZtroomByStuId(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = talentDao
				.listMyZTroomByStuIdSize(getSessionIntValue(ElConstants.SESSION_USERID));

		return "student_talent_ztroom_list";
	}

	public String student_talent_ztroom_view() throws ElException {
		myztroom = new MyZTRoom();
		myztroom.setZtroom(talentDao.getZtroomById(troom.getId()));
		return "student_talent_ztroom_view";
	}

	// 自我评价
	public String student_talent_troom_evalInit() throws ElException {/*
		myztroom.setZtroom(talentDao
				.getZtroomById(myztroom.getZtroom().getId()));
		myztroom.setEvaler(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));

		if (myztroom.getEvaltype() == TalentConstants.TALENT_EVAL_TYPE_ZJ) {
			myztroom.setTester(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID), "我"));
			myztroom.getTester().setMyZTRoom(
					talentDao.getMZTroomByTETId(myztroom));

		}
		if (myztroom.getEvaltype() == TalentConstants.TALENT_EVAL_TYPE_TS) {
			myztroom.setTesters(talentDao.listTSByTRid(myztroom.getZtroom()
					.getId(), getSessionIntValue(ElConstants.SESSION_USERID)));
			if (null != myztroom.getTesters()) {
				for (int i = 0; i < myztroom.getTesters().size(); i++) {
					myztroom.setTester(new ELUser(myztroom.getTesters().get(i)
							.getId()));
					myztroom.getTesters().get(i).setMyZTRoom(
							talentDao.getMZTroomByTETId(myztroom));
				}
			}
		}
		if (myztroom.getEvaltype() == TalentConstants.TALENT_EVAL_TYPE_XJ) {
			myztroom.setTesters(talentDao.listXJByTRid(myztroom.getZtroom()
					.getId(), getSessionIntValue(ElConstants.SESSION_USERID)));
			if (null != myztroom.getTesters()) {
				for (int i = 0; i < myztroom.getTesters().size(); i++) {
					myztroom.setTester(new ELUser(myztroom.getTesters().get(i)
							.getId()));
					myztroom.getTesters().get(i).setMyZTRoom(
							talentDao.getMZTroomByTETId(myztroom));
				}
			}
		}*/
		return "student_talent_troom_eval";
	}

	// 自我评价
	public String student_talent_troom_eval() throws ElException {
		ZTRoom zt = talentDao.getZtroomById(myztroom.getZtroom().getId());
		myztroom.setZtroom(zt);
		if (myztrooms != null) {
			for (int i = 0; i < myztrooms.size(); i++) {
				MyZTRoom mt = myztrooms.get(i);
				mt.setZtroom(zt);
				mt.setEvaler(new ELUser(
						getSessionIntValue(ElConstants.SESSION_USERID)));
				mt.setEvaltype(myztroom.getEvaltype());
				if (talentDao.checkevalTroom(mt)) {
					talentDao.alterevalTroom(mt);
				} else {
					talentDao.evalTroom(mt);
					ScoreOperate.setScore(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElConstants.SCORE_ZTROOM_DO);
				}
			}
		}
		return "student_talent_troom_eval";
	}

	public String student_talent_myztroom_list() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myztrooms = talentDao.listMyZtroomByStuId(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = talentDao
				.listMyZTroomByStuIdSize(getSessionIntValue(ElConstants.SESSION_USERID));

		return "student_talent_myztroom_list";
	}

	public String student_talent_myztroom_view() throws ElException {
		/*myztroom.setZtroom(talentDao
				.getZtroomById(myztroom.getZtroom().getId()));
		myztroom.setEvaler(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));

		if (myztroom.getEvaltype() == TalentConstants.TALENT_EVAL_TYPE_ZJ) {
			myztroom.setTester(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID), "我"));
			myztroom.getTester().setMyZTRoom(
					talentDao.getMZTroomByTETId(myztroom));

		}
		if (myztroom.getEvaltype() == TalentConstants.TALENT_EVAL_TYPE_TS) {
			myztroom.setTesters(talentDao.listTSByTRid(myztroom.getZtroom()
					.getId(), getSessionIntValue(ElConstants.SESSION_USERID)));
			if (null != myztroom.getTesters()) {
				for (int i = 0; i < myztroom.getTesters().size(); i++) {
					myztroom.setTester(new ELUser(myztroom.getTesters().get(i)
							.getId()));
					myztroom.getTesters().get(i).setMyZTRoom(
							talentDao.getMZTroomByTETId(myztroom));
				}
			}
		}
		if (myztroom.getEvaltype() == TalentConstants.TALENT_EVAL_TYPE_XJ) {
			myztroom.setTesters(talentDao.listXJByTRid(myztroom.getZtroom()
					.getId(), getSessionIntValue(ElConstants.SESSION_USERID)));
			if (null != myztroom.getTesters()) {
				for (int i = 0; i < myztroom.getTesters().size(); i++) {
					myztroom.setTester(new ELUser(myztroom.getTesters().get(i)
							.getId()));
					myztroom.getTesters().get(i).setMyZTRoom(
							talentDao.getMZTroomByTETId(myztroom));
				}
			}
		}*/
		return "student_talent_myztroom_view";
	}

	public String student_talent_myztroom_result() throws ElException {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myztrooms = talentDao.listMyZtroomByStuId(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = talentDao
				.listMyZTroomByStuIdSize(getSessionIntValue(ElConstants.SESSION_USERID));

		return "student_talent_myztroom_result";
	}

	// public String student_talent_mytroom_evalInit() throws ElException {
	// troom = talentDao.getTRoomById(troom.getId());
	// mytroom = mytroom == null ? new MyZTRoom() : mytroom;
	// mytroom.setTroom(troom);
	//
	// if (mytroom.getEvaltype() == TalentConstants.TALENT_EVAL_TYPE_ZJ) {
	// mytroom.setTester(new ELUser(
	// getSessionIntValue(ElConstants.SESSION_USERID), "我"));
	//
	// }
	// if (mytroom.getEvaltype() == TalentConstants.TALENT_EVAL_TYPE_TS) {
	// mytroom.setTesters(talentDao.listTSByTRid(troom.getId(),
	// getSessionIntValue(ElConstants.SESSION_USERID)));
	// }
	// if (mytroom.getEvaltype() == TalentConstants.TALENT_EVAL_TYPE_XJ) {
	// mytroom.setTesters(talentDao.listXJByTRid(troom.getId(),
	// getSessionIntValue(ElConstants.SESSION_USERID)));
	// }
	// return "student_talent_mytroom_eval";
	// }
	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

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

	public MyPollQuesion getMpollq() {
		return mpollq;
	}

	public void setMpollq(MyPollQuesion mpollq) {
		this.mpollq = mpollq;
	}

	public TalentDao getTalentDao() {
		return talentDao;
	}

	public void setTalentDao(TalentDao talentDao) {
		this.talentDao = talentDao;
	}

	public KTRoomColl getTroom() {
		return troom;
	}

	public void setTroom(KTRoomColl troom) {
		this.troom = troom;
	}

	// public MyZTRoom getMytroom() {
	// return mytroom;
	// }
	//
	// public void setMytroom(MyZTRoom mytroom) {
	// this.mytroom = mytroom;
	// }

	public List<MyZTRoom> getMytrooms() {
		return mytrooms;
	}

	public void setMytrooms(List<MyZTRoom> mytrooms) {
		this.mytrooms = mytrooms;
	}

	public KTRoom getQtroom() {
		return qtroom;
	}

	public void setQtroom(KTRoom qtroom) {
		this.qtroom = qtroom;
	}

	public List<MyKTRoomC> getMyktroomcs() {
		return myktroomcs;
	}

	public void setMyktroomcs(List<MyKTRoomC> myktroomcs) {
		this.myktroomcs = myktroomcs;
	}

	public MyKTRoomC getMyktroomc() {
		return myktroomc;
	}

	public void setMyktroomc(MyKTRoomC myktroomc) {
		this.myktroomc = myktroomc;
	}

	public List<MyZTRoom> getMyztrooms() {
		return myztrooms;
	}

	public void setMyztrooms(List<MyZTRoom> myztrooms) {
		this.myztrooms = myztrooms;
	}

	public MyZTRoom getMyztroom() {
		return myztroom;
	}

	public void setMyztroom(MyZTRoom myztroom) {
		this.myztroom = myztroom;
	}
}
