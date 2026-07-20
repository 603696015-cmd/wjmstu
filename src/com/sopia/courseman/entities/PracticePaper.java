package com.sopia.courseman.entities;

import com.sopia.questionman.entities.ExamPaper;


public class PracticePaper{
	private int id;
	private int sortid;
	private Course course;
	private CoursePage cpage;
	private int skipable;
	private ExamPaper examPaper;
	private String title;
	private float passgrade;//设置通过成绩
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPassgrade() {
		return passgrade;
	}
	public void setPassgrade(float passgrade) {
		this.passgrade = passgrade;
	}
	public PracticePaper() {
	}
	public PracticePaper(int id) {
		this.id =id;
	}
	public PracticePaper(int id, String title) {
		this.id = id;
		this.title = title;
	}
	public ExamPaper getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public CoursePage getCpage() {
		return cpage;
	}
	public void setCpage(CoursePage cpage) {
		this.cpage = cpage;
	}
	public int getSkipable() {
		return skipable;
	}
	public void setSkipable(int skipable) {
		this.skipable = skipable;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	
}
