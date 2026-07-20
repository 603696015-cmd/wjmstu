package com.sopia.assistman.entities;

import java.sql.Timestamp;

import com.sopia.assistman.PlanContants;
import com.sopia.duman.entities.ELUser;

public class PlanVerify {
	private int id;
	private Plan plan;
	private ELUser user;
	private int status;
	private Timestamp verifydate;
	private boolean superverify;
	
	public PlanVerify() {
	}
	public PlanVerify(int id){
		this.id = id;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getVerifydate() {
		return verifydate;
	}
	public void setVerifydate(Timestamp verifydate) {
		this.verifydate = verifydate;
	}
	public boolean isSuperverify() {
		return superverify;
	}
	public void setSuperverify(boolean superverify) {
		this.superverify = superverify;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatusName(){
		if(status == PlanContants.PLAN_STATUS_MAKING) return "制作中";
		if(status == PlanContants.PLAN_STATUS_SHWAITING) return "审核中";
		if(status == PlanContants.PLAN_STATUS_YES) return "通过审核";
		if(status == PlanContants.PLAN_STATUS_NO) return "未通过审核";
		return "错误状态";
		
	}
}
