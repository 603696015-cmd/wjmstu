package com.sopia.assistman.dao;

import java.util.List;

import com.sopia.assistman.entities.Poll;
import com.sopia.assistman.entities.Questionnaire;
import com.sopia.common.ElException;

public interface QuestionnaireDao {
	/**
	 * 查询创建的问卷列表
	 * @param questionnaire
	 * @param pagenow
	 * @param pagesize
	 * @return
	 * @throws ElException
	 */
	public List<Questionnaire> myQuestionnaireList(Questionnaire questionnaire,int pagenow,int pagesize)throws ElException;
	/**
	 * 查询创建的问卷数量
	 * @param questionnaire
	 * @return
	 * @throws ElException
	 */
	public int myQuestionnaireListCount(Questionnaire questionnaire)throws ElException;
}
