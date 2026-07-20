package com.sopia.courseman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.QuestionRanking;
import com.sopia.questionman.entities.Question;

public interface ExampracQuestionDao {
	/**
	 * 练习试题统计 错题排行
	 * @param question
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<QuestionRanking> listQuestionRanking(Question question,int pageNow,int pageSize) throws ElException;
	/**
	 * 获取练习试题统计题量
	 * @param question
	 * @return
	 * @throws ElException
	 */
	public int listQuestionRankingSize(Question question) throws ElException;
	/**
	 * 统计练习中该试题的答案选择情况
	 * @param qid
	 * @return
	 * @throws ElException
	 */
	public QuestionRanking getEpracQuestionInfo(int qid) throws ElException;
}
