package com.sopia.answeringsystem.dao;

import java.util.List;

import com.sopia.answeringsystem.entities.Answer;
import com.sopia.answeringsystem.entities.AnsweringType;
import com.sopia.answeringsystem.entities.Ques;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public interface AnsweringSystemDao {
	
	public int addQues(Ques ques) throws ElException;
	public List<Ques> listMyQues(int userid,int pageNow,int pageSize,Ques ques) throws ElException;
	public int listMyQuesSize(int userid,Ques ques) throws ElException;
	
	public void deleteQues(int quesid) throws ElException;
	public Ques queryQuesById(int quesid) throws ElException;
	public void alterQuesById(Ques ques) throws ElException;
	
	public List<ELUser> listAnswerUser(String userids) throws ElException;
	
	public void addViewCountById(Ques ques) throws ElException;
	public List<Answer> listAnswersByQuesid(int quesid,int pageNow,int pageSize)  throws ElException;
	public int listAnswersSizeByQuesid(int quesid) throws ElException;
	
	public List<Ques> listQuesByAnsweringType(AnsweringType answeringType,int pageNow,int pageSize,Ques q) throws ElException;
	public int listQuesSizeByAnsweringType(AnsweringType answeringType,Ques q) throws ElException;
	
	public void alterQuesStatus(int quesid,int status) throws ElException;
	public void alterAnswerStatus(int answerid,int status) throws ElException;
	
	public List<Answer> listMyAnswers(int userid,int pageNow,int pageSize) throws ElException;
	public int listMyAnswersSize(int userid) throws ElException;
	
	public int getQuestionCount(int status) throws ElException;
	
	public List<Ques> listQuesesByDate() throws ElException;
	public List<Answer> listAnswersByDate() throws ElException;
	public List<Ques>listQuesesByStatusTow(int statusTow) throws ElException;
	
	public void addAnswer(Answer answer) throws ElException;
	public List<Ques> listQuestionByAnsweringType(ElNode tree,int pageNow,int pageSize,String status) throws ElException;
	public int listQuestionSizeByAnsweringType(ElNode tree,String status) throws ElException;
	
	public void setStatusTow(int quesid,int statusTow) throws ElException;
	
}
