package com.sopia.studyman.entities;

import java.sql.Timestamp;

import com.sopia.assistman.entities.Poll;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.Question;

public class MyPollQuesion {
	private int id;
	private String myAnswer;
	private ELUser tester;
	private Question question;
	private Timestamp endtime;
	private Poll poll;
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
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	
}
