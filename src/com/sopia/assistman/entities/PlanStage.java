package com.sopia.assistman.entities;

import java.sql.Date;
import java.util.List;

public class PlanStage {
	private int id;
	private String content;
	private Date planfinishdate;
	
	private int plandays;
	private int realdays;
	private Date realfinishdate;
	private Plan plan;
	private List<PlanStuff> planStuffs;
	
	public PlanStage() {
	}
	public List<PlanStuff> getPlanStuffs() {
		return planStuffs;
	}
	public void setPlanStuffs(List<PlanStuff> planStuffs) {
		this.planStuffs = planStuffs;
	}
	public PlanStage(int id,String content) {
		this.id = id;
		this.content = content;
	}
	
	
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPlandays() {
		return plandays;
	}
	public void setPlandays(int plandays) {
		this.plandays = plandays;
	}
	public int getRealdays() {
		return realdays;
	}
	public void setRealdays(int realdays) {
		this.realdays = realdays;
	}
	public Date getPlanfinishdate() {
		return planfinishdate;
	}
	public void setPlanfinishdate(Date planfinishdate) {
		this.planfinishdate = planfinishdate;
	}
	public Date getRealfinishdate() {
		return realfinishdate;
	}
	public void setRealfinishdate(Date realfinishdate) {
		this.realfinishdate = realfinishdate;
	}
	
}
