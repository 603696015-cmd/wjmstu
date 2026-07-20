package com.sopia.assistman.entities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.sopia.assistman.PlanContants;
import com.sopia.duman.entities.ELUser;

public class Plan {
	private int id;
	
	private String name ;
	private String content ;
	private ELUser manager;
	private String contact ;
	private String participator;
	private Timestamp createtime;
//	private Date starttime; 
	private Date planfinishdate;
	private List<PlanStage> planStages;
	private int status;
	private int plandays;
	private int realdays;
	private Date realfinishdate;
	private int totaldays ;
	private float stagePro;
	public float getStagePro() {
		return stagePro;
	}
	public void setStagePro(float stagePro) {
		this.stagePro = stagePro;
	}
	public int getTotaldays() {
		return totaldays;
	}
	public void setTotaldays(int totaldays) {
		this.totaldays = totaldays;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<PlanStage> getPlanStages() {
		return planStages;
	}
	public void setPlanStages(List<PlanStage> planStages) {
		this.planStages = planStages;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Plan() {
	}
	public Plan(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getParticipator() {
		return participator;
	}
	public void setParticipator(String participator) {
		this.participator = participator;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
 
	public ELUser getManager() {
		return manager;
	}
	public void setManager(ELUser manager) {
		this.manager = manager;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
//	public Date getStarttime() {
//		return starttime;
//	}
//	public void setStarttime(Date starttime) {
//		this.starttime = starttime;
//	}
	public String getStatusName(){
		if(status == PlanContants.PLAN_STATUS_MAKING) return "制作中";
		if(status == PlanContants.PLAN_STATUS_SHWAITING) return "审核中";
		if(status == PlanContants.PLAN_STATUS_YES) return "通过审核";
		if(status == PlanContants.PLAN_STATUS_NO) return "未通过审核";
		return "错误状态";
		
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
	public Date getRealfinishdate() {
		return realfinishdate;
	}
	public void setRealfinishdate(Date realfinishdate) {
		this.realfinishdate = realfinishdate;
	}
	public Date getPlanfinishdate() {
		return planfinishdate;
	}
	public void setPlanfinishdate(Date planfinishdate) {
		this.planfinishdate = planfinishdate;
	}
	
}
