package com.sopia.examroompeice.entities;

import com.sopia.courseman.entities.ExamRoom;

public class ExamRoomPeice {
	private int examroomid;
	private ExamRoom examRoom;//价格所关联的培训班
	private float  	examroomnowPrice;//现价、会员价
	private float  	examroomoldPrice;//原价、普通价
	private int   	status;//价格审核状态
	private int     userid;//审核者ID
	private String  userName;//审核者姓名
	private float   chajia;//差价

	
	
	
	
//------------------------------------set and get------------------------------------------------------------
	public int getExamroomid() {
		return examroomid;
	}
	public void setExamroomid(int examroomid) {
		this.examroomid = examroomid;
	}
	public ExamRoom getExamRoom() {
		return examRoom;
	}
	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}
	public float getExamroomnowPrice() {
		return examroomnowPrice;
	}
	public void setExamroomnowPrice(float examroomnowPrice) {
		this.examroomnowPrice = examroomnowPrice;
	}
	public float getExamroomoldPrice() {
		return examroomoldPrice;
	}
	public void setExamroomoldPrice(float examroomoldPrice) {
		this.examroomoldPrice = examroomoldPrice;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public float getChajia() {
		return chajia;
	}
	public void setChajia(float chajia) {
		this.chajia = chajia;
	}

	
	
}
