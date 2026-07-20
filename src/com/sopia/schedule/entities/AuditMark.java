package com.sopia.schedule.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;

import com.sopia.common.ElException;
import com.sopia.schedule.dao.impl.TagsDaoImpl;

public class AuditMark {
	
	private int id;
	private String moduleid;
	private int entityid;
	private String audit_mark;
	private String username;
	private Timestamp audittime;
	private int status;
	private String status_chinese;
	private String auditName;
	private String auditName_chinese;
	
	public String getAuditName_chinese() {
		return auditName_chinese;
	}
	public void setAuditName_chinese(String auditName_chinese) {
		this.auditName_chinese = auditName_chinese;
	}
	public String getStatus_chinese() {
		return status_chinese;
	}
	public void setStatus_chinese(String status_chinese) {
		this.status_chinese = status_chinese;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getAudittime() {
		return audittime;
	}
	public void setAudittime(Timestamp audittime) {
		this.audittime = audittime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	public int getEntityid() {
		return entityid;
	}
	public void setEntityid(int entityid) {
		this.entityid = entityid;
	}
	public String getAudit_mark() {
		return audit_mark;
	}
	public void setAudit_mark(String audit_mark) {
		this.audit_mark = audit_mark;
	}
	
	
}
