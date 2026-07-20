package com.sopia.wjm.entities;
/**
 * 用户定级信息
 * @author TMK
 *
 */
public class ELUserClassification {
	private int userid;		//用户ID
	private int roomid;		//定级考场ID
	private String name;	//定级等级名称
	private int type;		//是否已定级		1==是、0否
	private int time;		//第几次定级
	private int status;		//是否异常
	
	public ELUserClassification(){
		
	}
	public ELUserClassification(int time){
		this.time = time;
	}
	public ELUserClassification(int time,int status){
		this.time = time;
		this.status = status ; 
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getRoomid() {
		return roomid;
	}
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	
	
}
