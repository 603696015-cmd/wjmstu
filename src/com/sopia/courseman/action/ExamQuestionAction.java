package com.sopia.courseman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.common.ElException;
import com.sopia.courseman.dao.ExamQuestionDao;
import com.sopia.courseman.entities.QuestionRanking;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.Question;

public class ExamQuestionAction extends BaseAction {
	private QuestionDao questionDao;
	private Question question;
	private ExamQuestionDao examQuestionDao;
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
	public QuestionDao getQuestionDao() {
		return questionDao;
	}
	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}
	public ExamQuestionDao getExamQuestionDao() {
		return examQuestionDao;
	}
	public void setExamQuestionDao(ExamQuestionDao examQuestionDao) {
		this.examQuestionDao = examQuestionDao;
	}
	/**
	 * 试题搜索初始化
	 * @return
	 * @throws ElException
	 */
	public String exam_question_seachInit() throws ElException{
		return "exam_question_seach";
	}
	/**
	 * 错题排行榜
	 * @return
	 * @throws ElException
	 */
	public String exam_question_list() throws ElException{
		questionRankings=examQuestionDao.listQuestionRanking(question,getPageNow(),getPageSize());
		count=examQuestionDao.listQuestionRankingSize(question);
		return "exam_question_list";
	}
	/**
	 * 练习试题统计详细信息
	 * @return
	 * @throws ElException
	 */
	public String exam_question_info() throws ElException{
		questionRanking=examQuestionDao.getExamQuestionInfo(question.getId());
		return "exam_question_info";
	}
}
