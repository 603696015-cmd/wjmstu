package com.sopia.assistman.entities;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.Question;

public class Questionnaire {
	private int id;
	private String title;
	private String description;///
	private Timestamp begintime;///
	private Timestamp endtime;///
	private ELUser creater;
//	private Question question;
	private boolean stureadresult;///
	private boolean canViewResult;///
	private int stuViewResult;//问卷结果是否可以查看
	
	private String remack;
	private Timestamp createtime;
	private int status;
	private ExamPaper exampaper;
	public ExamPaper getExampaper() {
		return exampaper;
	}
	public void setExampaper(ExamPaper exampaper) {
		this.exampaper = exampaper;
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
	public boolean isStureadresult() {
		return stureadresult;
	}
	public void setStureadresult(boolean stureadresult) {
		this.stureadresult = stureadresult;
	}
	public boolean isCanViewResult() {
		return canViewResult;
	}
	public void setCanViewResult(boolean canViewResult) {
		this.canViewResult = canViewResult;
	}
	public int getStuViewResult() {
		return stuViewResult;
	}
	public void setStuViewResult(int stuViewResult) {
		this.stuViewResult = stuViewResult;
	}
	public String getRemack() {
		return remack;
	}
	public void setRemack(String remack) {
		this.remack = remack;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
