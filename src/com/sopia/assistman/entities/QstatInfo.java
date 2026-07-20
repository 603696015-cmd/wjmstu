package com.sopia.assistman.entities;

public class QstatInfo {
	private int id;
	private String title;
	private String options[];
	private String answers;
	private int answerCount[];
	private int totalCount;
	private float answerPer[];
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getOptions() {
		return options;
	}
	public void setOptions(String[] options) {
		this.options = options;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public int[] getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int[] answerCount) {
		this.answerCount = answerCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public float[] getAnswerPer() {
		return answerPer;
	}
	public void setAnswerPer(float[] answerPer) {
		this.answerPer = answerPer;
	}
}
