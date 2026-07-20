package com.sopia.courseman.entities;

import java.sql.Timestamp;

import com.sopia.questionman.entities.ExamPaper;

public class SimexamPaper {
	private int id;
	private Timestamp begintime;
	private Timestamp endtime;

	private Course course;
	private ExamPaper examPaper;

	public SimexamPaper() {
	}

	public SimexamPaper(int id) {

		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getBegintime() {
		return begintime;
	}

	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
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
