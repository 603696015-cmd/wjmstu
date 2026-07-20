package com.sopia.assistman.dao;

import java.util.List;

import com.sopia.assistman.entities.Poll;
import com.sopia.assistman.entities.QstatInfo;
import com.sopia.assistman.entities.Survey;
import com.sopia.common.ElException;
import com.sopia.studyman.entities.MyPollQuesion;
import com.sopia.studyman.entities.MySurvyEP;

public interface SurveyAndPollDao {
	public void addSurvey(Survey survey) throws ElException;

	public void alterSurvey(Survey survey) throws ElException;

	public List<Survey> listMySurvey(int userid, int pageNow, int pageSize)
			throws ElException;

	public int listMySurveySize(int userid) throws ElException;

	public void deleteSurvey(int id) throws ElException;

	public Survey getSurvey(int id) throws ElException;

	public List<Survey> listSurveyByDepid(int depid, int pageNow, int pageSize)
			throws ElException;

	public void surveyDoSubmit(MySurvyEP mep) throws ElException;

	public int listSurveyByDepidSize(int depid) throws ElException;

	public boolean surveyDoCheck(MySurvyEP mep) throws ElException;

	public List<QstatInfo> listQstatinfoBySurid(int id) throws ElException;

	public void addPoll(Poll poll) throws ElException;

	public void alterPoll(Poll poll) throws ElException;

	public List<Poll> listMyPoll(int userid, int pageNow, int pageSize)
			throws ElException;

	public QstatInfo getQstatinfoByPollid(int id) throws ElException;

	public int listMyPollSize(int userid) throws ElException;

	public Poll getPoll(int id) throws ElException;

	public void pollDoSubmit(MyPollQuesion mpq) throws ElException;

	public boolean pollDoCheck(MyPollQuesion mpq) throws ElException;

	public void deletePoll(int id) throws ElException;

	public List<Poll> listPollByDepid(int depid, int pageNow, int pageSize)
			throws ElException;

	public int listPollByDepidSize(int depid) throws ElException;
}
