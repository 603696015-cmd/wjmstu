package com.sopia.assistman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.sopia.assistman.dao.QuestionnaireDao;
import com.sopia.assistman.entities.Questionnaire;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;

public class QuestionnaireDaoImpl implements QuestionnaireDao{


	public List<Questionnaire> myQuestionnaireList(Questionnaire questionnaire,
			int pagenow, int pagesize) throws ElException {
		return null;
	}

	public int myQuestionnaireListCount(Questionnaire questionnaire)
			throws ElException {
		return 0;
	}

}
