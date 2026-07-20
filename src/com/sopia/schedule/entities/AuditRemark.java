package com.sopia.schedule.entities;

import java.sql.Timestamp;

/**
 * 审核备注表==系统
 * @author Administrator
 *
 */
public class AuditRemark {
	private int id;
	private int moduleId;//模块id
	private String auditName;//审核数据名称
	private Timestamp operattime;//审核操作时间
	private String auditUserName;//审核人
	private int auditLevel;//对应审级
	private String auditResult;//审核结果
	private String remark;//审核备注
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public Timestamp getOperattime() {
		return operattime;
	}
	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public int getAuditLevel() {
		return auditLevel;
	}
	public void setAuditLevel(int auditLevel) {
		this.auditLevel = auditLevel;
	}
	public String getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
