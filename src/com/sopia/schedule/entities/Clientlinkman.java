package com.sopia.schedule.entities;

import java.sql.Timestamp;

public class Clientlinkman {

	  private int id  ;//     NUMBER not null,
	  private String type   ;//     VARCHAR2(50),
	  private String name    ;//    VARCHAR2(50),
	  private String sex  ;//      VARCHAR2(5),
	  private String dep  ;//      VARCHAR2(20),
	  private String duty  ;//      VARCHAR2(50),
	  private String task   ;//     VARCHAR2(50),
	  private String worktel  ;//   VARCHAR2(20),
	  private String phone  ;//     VARCHAR2(20),
	  private String tax    ;//     VARCHAR2(50),
	  private String emainl  ;//    VARCHAR2(50),
	  private String hometel  ;//   VARCHAR2(20),
	  private String msnqq   ;//    VARCHAR2(50),
	 // private Timestamp BIRTHDAY   ;//   DATE,
	  private String birthday ;
	  private String hobby ;//     VARCHAR2(50)
	  private String remark ;
	  private int clientid;
	  
	  
	  
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getWorktel() {
		return worktel;
	}
	public void setWorktel(String worktel) {
		this.worktel = worktel;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getEmainl() {
		return emainl;
	}
	public void setEmainl(String emainl) {
		this.emainl = emainl;
	}
	public String getHometel() {
		return hometel;
	}
	public void setHometel(String hometel) {
		this.hometel = hometel;
	}
	public String getMsnqq() {
		return msnqq;
	}
	public void setMsnqq(String msnqq) {
		this.msnqq = msnqq;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getClientid() {
		return clientid;
	}
	public void setClientid(int clientid) {
		this.clientid = clientid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	  
	  
}
