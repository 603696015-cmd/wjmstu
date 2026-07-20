package com.sopia.questionman.entities;


public class QuestionArt {
	private int id;
	private String title;
	private String content;
	private String qexplain;

	public String getQexplain() {
		return qexplain;
	}

	public void setQexplain(String qexplain) {
		this.qexplain = qexplain;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
