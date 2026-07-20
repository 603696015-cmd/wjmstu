package com.sopia.studyman.entities;

import java.sql.Timestamp;

import com.sopia.assistman.entities.Survey;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;

public class MySurvyEP {
	private int id;
	private String myAnswer;
	private ELUser tester;
	private Survey survey;
	private ExamPaper examPaper;
	private Timestamp endtime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMyAnswer() {
		return myAnswer;
	}
	public void setMyAnswer(String myAnswer) {
		this.myAnswer = myAnswer;
	}
	public ELUser getTester() {
		return tester;
	}
	public void setTester(ELUser tester) {
		this.tester = tester;
	}
	public Survey getSurvey() {
		return survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	public ExamPaper getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

}
