package com.sopia.schedule.entities;

import com.google.gson.annotations.Expose;
import com.sopia.common.ElException;
import com.sopia.duman.dao.impl.UserDaoImpl;

public class CustomAudit {
	private String tablename;//±Ì√˚
	@Expose
	private String auditOrder;//…Û∫ÀÀ≥–Ú
	@Expose
	private String auditName;//…Û∫À√˚≥∆
	@Expose
	private String username ;
	@Expose
	private String auditUser;//…Û∫À»À
	@Expose
	private String mark;//±∏◊¢
	public CustomAudit(String auditName){
		String[] userids = null;
		this.username = "";
		try {
			if(auditName != null && !auditName.equals("")){
				userids = auditName.split(",");
				for(int i = 0;i<userids.length;i++){
					this.username += new UserDaoImpl().getUserById(Integer.parseInt(userids[i])).getRealname() + ",";
				}
				if(this.username != null && !this.username.equals("")){
					this.username = this.username.substring(0,this.username.lastIndexOf(","));
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ElException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getAuditOrder() {
		return auditOrder;
	}
	public void setAuditOrder(String auditOrder) {
		this.auditOrder = auditOrder;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	

}
