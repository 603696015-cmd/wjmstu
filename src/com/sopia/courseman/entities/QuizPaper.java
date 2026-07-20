package com.sopia.courseman.entities;

import com.sopia.questionman.entities.ExamPaper;

public class QuizPaper {
	private int id;
	private Course course;
	private ExamPaper examPaper;
	private boolean erHasEp;
	public boolean isErHasEp() {
		return erHasEp;
	}
	public void setErHasEp(boolean erHasEp) {
		this.erHasEp = erHasEp;
	}
	public QuizPaper() {
	}
	public QuizPaper(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public ExamPaper getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	
}
