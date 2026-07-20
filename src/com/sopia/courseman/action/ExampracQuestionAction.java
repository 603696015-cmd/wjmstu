package com.sopia.courseman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.courseman.dao.ExampracQuestionDao;
import com.sopia.courseman.entities.QuestionRanking;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionLib;

public class ExampracQuestionAction extends BaseAction {
	private QuestionDao questionDao;
	private QuestionLib qlbTree;
	private Question question;
	private ExampracQuestionDao exampracQuestionDao;
	private List<QuestionRanking> questionRankings;
	private QuestionRanking questionRanking;
	public QuestionRanking getQuestionRanking() {
		return questionRanking;
	}
	public void setQuestionRanking(QuestionRanking questionRanking) {
		this.questionRanking = questionRanking;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<QuestionRanking> getQuestionRankings() {
		return questionRankings;
	}
	public void setQuestionRankings(List<QuestionRanking> questionRankings) {
		this.questionRankings = questionRankings;
	}
	public ExampracQuestionDao getExampracQuestionDao() {
		return exampracQuestionDao;
	}
	public void setExampracQuestionDao(ExampracQuestionDao exampracQuestionDao) {
		this.exampracQuestionDao = exampracQuestionDao;
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
	/**
	 * 试题搜索初始化
	 * @return
	 * @throws ElException
	 */
	public String eprac_question_seachInit() throws ElException{
		return "eprac_question_seach";
	}
	/**
	 * 选择试题库
	 * @return
	 * @throws ElException
	 */
	public String eprac_question_select() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID), 1, false);
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", 1,
					false);
		}
		return "eprac_question_select";
	}
	/**
	 * 错题排行榜
	 * @return
	 * @throws ElException
	 */
	public String eprac_question_list() throws ElException{
		questionRankings=exampracQuestionDao.listQuestionRanking(question,getPageNow(),getPageSize());
		count=exampracQuestionDao.listQuestionRankingSize(question);
		return "eprac_question_list";
	}
	/**
	 * 练习试题统计详细信息
	 * @return
	 * @throws ElException
	 */
	public String eprac_question_info() throws ElException{
		questionRanking=exampracQuestionDao.getEpracQuestionInfo(question.getId());
		return "eprac_question_info";
	}
}
