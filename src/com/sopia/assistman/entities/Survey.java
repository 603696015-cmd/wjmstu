package com.sopia.assistman.entities;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;

public class Survey {
	private int id;
	private String title;
	private String description;
	private Timestamp begintime;
	private Timestamp endtime;
	private ELUser creater;
	private ExamPaper examPaper;
	private boolean stureadresult;
	private boolean canViewResult;
	
	
	public boolean getCanViewResult() {
		return canViewResult;
	}
	public void setCanViewResult(boolean canViewResult) {
		this.canViewResult = canViewResult;
	}
	public Survey() {
	}
	public Survey(int id,String title){
		this.id=id;
		this.title = title;
	}
	
	
	public boolean getStureadresult() {
		return stureadresult;
	}
	public void setStureadresult(boolean stureadresult) {
		this.stureadresult = stureadresult;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
	public ExamPaper getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	
	
}
