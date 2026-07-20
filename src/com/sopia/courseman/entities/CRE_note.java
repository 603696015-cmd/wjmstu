package com.sopia.courseman.entities;

import java.sql.Timestamp;

import com.sopia.classman.entities.ElClass;
import com.sopia.duman.entities.ELUser;

public class CRE_note {

	  private int id;         
	  private ElClass elclass;    
	  private Course course;    
	  private ExamRoom eroom;    
	  private ELUser user;   
	  private String type; 
	  private String operate;    
	  private Timestamp createtime;  
	  private String  phone;    
	  private String content;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ElClass getElclass() {
		return elclass;
	}
	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public ExamRoom getEroom() {
		return eroom;
	}
	public void setEroom(ExamRoom eroom) {
		this.eroom = eroom;
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	} 
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}    
}
