package com.sopia.schedule.entities;

import com.google.gson.annotations.Expose;

/**
 * 用户签名信息
 * @author Administrator
 *
 */
public class UserSign {
	@Expose
	private int moduleid;	//模块id
	@Expose
	private int entityid;	//实体id
	@Expose
	private int signuserid;	//用户签名id
	public int getModuleid() {
		return moduleid;
	}
	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}
	public int getEntityid() {
		return entityid;
	}
	public void setEntityid(int entityid) {
		this.entityid = entityid;
	}
	public int getSignuserid() {
		return signuserid;
	}
	public void setSignuserid(int signuserid) {
		this.signuserid = signuserid;
	}
	
	
}
