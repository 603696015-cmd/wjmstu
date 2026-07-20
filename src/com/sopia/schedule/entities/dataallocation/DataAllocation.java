package com.sopia.schedule.entities.dataallocation;

import java.sql.Timestamp;

/**
 * 数据分配
 * @author Administrator
 *
 */
public class DataAllocation {
	private int id;
	private int entityid;			//数据id
	private int userid;				//用户id
	private int moduleid;			//模块id
	private int status;				//状态
	private Timestamp begintime;	//开始时间
	private Timestamp endtime;		//结束时间
	private int allocationtype;			//分配方式
	
	
	public int getAllocationtype() {
		return allocationtype;
	}
	public void setAllocationtype(int allocationtype) {
		this.allocationtype = allocationtype;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEntityid() {
		return entityid;
	}
	public void setEntityid(int entityid) {
		this.entityid = entityid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getModuleid() {
		return moduleid;
	}
	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	
	
}
