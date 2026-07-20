package com.sopia.assistman.entities;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.Question;

public class Poll {
	private int id;
	private String title;
	private String description;///
	private Timestamp begintime;///
	private Timestamp endtime;///
	private ELUser creater;
	private Question question;
	private boolean stureadresult;///
	private boolean canViewResult;///
	private int stuViewResult;//投票结果是否可以查看
	
	private String remack;
	private Timestamp createtime;
	private int status;
	//下面4个属性用于搜索
	private Timestamp createtimeStart;//创建时间开始
	private Timestamp createtimeEnd;//创建时间结束
	private Timestamp endtimeStart;//结束时间开始
	private Timestamp endtimeEnd;//结束时间结束
	
	private int isApply;//是否参与投票
	
	private int hot;//热度
	
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public int getIsApply() {
		return isApply;
	}
	public void setIsApply(int isApply) {
		this.isApply = isApply;
	}
	public Timestamp getCreatetimeStart() {
		return createtimeStart;
	}
	public void setCreatetimeStart(Timestamp createtimeStart) {
		this.createtimeStart = createtimeStart;
	}
	public Timestamp getCreatetimeEnd() {
		return createtimeEnd;
	}
	public void setCreatetimeEnd(Timestamp createtimeEnd) {
		this.createtimeEnd = createtimeEnd;
	}
	public Timestamp getEndtimeStart() {
		return endtimeStart;
	}
	public void setEndtimeStart(Timestamp endtimeStart) {
		this.endtimeStart = endtimeStart;
	}
	public Timestamp getEndtimeEnd() {
		return endtimeEnd;
	}
	public void setEndtimeEnd(Timestamp endtimeEnd) {
		this.endtimeEnd = endtimeEnd;
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
	public String getStatusName() {
		switch (status) {
		case 0:
			return "已创建";
		case 1:
			return "审核等待中";
		case 2:
			return "已开通";
		case 3:
			return "未开通";
		default:
			break;
		}
		return "未知";
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean getCanViewResult() {
		return canViewResult;
	}
	public void setCanViewResult(boolean canViewResult) {
		this.canViewResult = canViewResult;
	}
	public Poll() {
	}
	public Poll(int id,String title){
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
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
	public int getStuViewResult() {
		return stuViewResult;
	}
	public void setStuViewResult(int stuViewResult) {
		this.stuViewResult = stuViewResult;
	}
}
