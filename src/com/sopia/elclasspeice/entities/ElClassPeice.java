package com.sopia.elclasspeice.entities;

import com.sopia.classman.entities.ElClass;

public class ElClassPeice {
	private int elclassid;
	private ElClass	elClass;//价格所关联的培训班
	private float  	elclassnowPrice;//现价、会员价
	private float  	elclassoldPrice;//原价、普通价
	private int   	status;//价格审核状态
	private int     userid;//审核者ID
	private String  userName;//审核者姓名
	private float   chajia;//差价
	
	
	public int getElclassid() {
		return elclassid;
	}
	public void setElclassid(int elclassid) {
		this.elclassid = elclassid;
	}
	
	public float getElclassnowPrice() {
		return elclassnowPrice;
	}
	public void setElclassnowPrice(float elclassnowPrice) {
		this.elclassnowPrice = elclassnowPrice;
	}
	public float getElclassoldPrice() {
		return elclassoldPrice;
	}
	public void setElclassoldPrice(float elclassoldPrice) {
		this.elclassoldPrice = elclassoldPrice;
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
	

	public ElClassPeice(){
		
	}
	
	public ElClassPeice(int elclassid){
		this.elclassid=elclassid;
	}
	public ElClass getElClass() {
		return elClass;
	}
	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}
}
