package com.sopia.pfms.entities;

import java.sql.Timestamp;

import com.sopia.common.SystemConfOp;

public class DamageMember {
	
	private int id;
	private String name;//姓名
	private String sex;//性别
	private String personId;//身份证
	private Timestamp birthday;//出生日期
	private String workCompany;
	private String hometown;
	private String picture;
	private Timestamp fabushijian;
	
	public DamageMember(){
		
	}
	
	public String getPicture_(){
		if(picture!=null&&(picture.indexOf("http://")==0||picture.indexOf("https://")==0))
			return picture;
		return  SystemConfOp.getStuffUrl()+picture;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getSex_() {
		if(this.sex.equals("男"))
			return 0;
		if(this.sex.equals("女"))
			return 1;
		return 2;		
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Timestamp getBirthday() {
		return birthday;
	}
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}
	public String getWorkCompany() {
		return workCompany;
	}
	public void setWorkCompany(String workCompany) {
		this.workCompany = workCompany;
	}
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Timestamp getFabushijian() {
		return fabushijian;
	}
	public void setFabushijian(Timestamp fabushijian) {
		this.fabushijian = fabushijian;
	}
	
	

}
