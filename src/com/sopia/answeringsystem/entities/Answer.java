package com.sopia.answeringsystem.entities;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;

/**
 * 问答系统==回复答案
 * @author Administrator
 *
 */
public class Answer {
	private int id;
	private int questionId;//问题id
	private Ques ques;//问题
	private String answerContent;//回复内容
	private Timestamp answerTime;//回复时间
	private int answerUserid;//回复userid
	private ELUser answerUser;//回复者
	private int status;//回复状态
	//回复的状态（已创建、已审核、未采纳、最佳答案）  (0、1、2、3)
	
	public String getStatus_(){
		if(this.status == 0){
			return "已创建";
		}else if(this.status == 1){
			return "已审核";
		}else if(this.status == 2){
			return "未采纳";
		}else if(this.status == 3){
			return "最佳答案";
		}else{
			return "未知状态";
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public Timestamp getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Timestamp answerTime) {
		this.answerTime = answerTime;
	}

	public int getAnswerUserid() {
		return answerUserid;
	}

	public void setAnswerUserid(int answerUserid) {
		this.answerUserid = answerUserid;
	}

	public ELUser getAnswerUser() {
		return answerUser;
	}

	public void setAnswerUser(ELUser answerUser) {
		this.answerUser = answerUser;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Ques getQues() {
		return ques;
	}

	public void setQues(Ques ques) {
		this.ques = ques;
	}
	
	
	
}
