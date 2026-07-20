package com.sopia.courseman.entities;

import java.util.List;

import com.sopia.questionman.entities.Question;

public class QuestionRanking {
	private Question question;//题目信息
	private int answerCount;//答题人次
	private int answerTo;//答对人次
	private int answerWrong;//答错人次(包括未答题人数)
	private float answerWrongRate;//答错率
	private int answerSum;//试题的答案数
	private List<QuestionSelect> answerInfo;//题目的答题选择情况
//	private float sqRate;
	public int getAnswerSum() {
		return answerSum;
	}
	public void setAnswerSum(int answerSum) {
		this.answerSum = answerSum;
	}
	public List<QuestionSelect> getAnswerInfo() {
		return answerInfo;
	}
	public void setAnswerInfo(List<QuestionSelect> answerInfo) {
		this.answerInfo = answerInfo;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	public int getAnswerTo() {
		return answerTo;
	}
	public void setAnswerTo(int answerTo) {
		this.answerTo = answerTo;
	}
	public int getAnswerWrong() {
		return answerWrong;
	}
	public void setAnswerWrong(int answerWrong) {
		this.answerWrong = answerWrong;
	}
	public float getAnswerWrongRate() {
		return answerWrongRate;
	}
	public float getAnswerWrongRate_() {
		String tempStr=answerWrongRate*100*100+"";
		return Float.parseFloat(tempStr.substring(0, tempStr.indexOf(".")))/100.0f;
	}
	public void setAnswerWrongRate(float answerWrongRate) {
		this.answerWrongRate = answerWrongRate;
	}
	/**
	 * 答题选择的比率
	 * @return
	 */
	public float getSqRate() {
		if(answerCount==0){
			return 0;
		}
		String tempStr=answerWrong/(float)answerCount*100*100+"";
		if(tempStr.indexOf(".")!=-1){
			return Float.parseFloat(tempStr.substring(0, tempStr.indexOf(".")))/100.0f;
		}else{
			return answerWrong/answerCount*100;
		}
	}
}
