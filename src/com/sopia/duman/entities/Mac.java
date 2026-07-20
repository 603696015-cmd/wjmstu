package com.sopia.duman.entities;

import java.sql.Date;
import java.sql.Timestamp;

public class Mac {
	private int id;
	private String macaddres;  //Mac地址
	private Timestamp addtime;	//添加时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMacaddres() {
		return macaddres;
	}
	public void setMacaddres(String macaddres) {
		this.macaddres = macaddres;
	}
	public Timestamp getAddtime() {
		return addtime;
	}
	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}
	
}
