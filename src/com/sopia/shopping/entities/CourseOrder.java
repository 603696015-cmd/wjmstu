package com.sopia.shopping.entities;

import java.sql.Timestamp;

import com.sopia.courseman.entities.Course;
import com.sopia.duman.entities.ELUser;

public class CourseOrder {
	private String sid;
	private int id;
	private float price;
	private  int count;
	private  ELUser elUser;  

	private Timestamp cdate;
	private Timestamp odate;
	private float zprice;
	private Course course;
	private int status ;
	private String sstatus ;
	
	public String getSstatus() {
		return sstatus;
	}
	public void setSstatus(String sstatus) {
		this.sstatus = sstatus;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public float getZprice() {
		return zprice;
	}
	public void setZprice(float zprice) {
		this.zprice = zprice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public Timestamp getCdate() {
		return cdate;
	}
	public void setCdate(Timestamp cdate) {
		this.cdate = cdate;
	}
	public Timestamp getOdate() {
		return odate;
	}
	public void setOdate(Timestamp odate) {
		this.odate = odate;
	}
	

}
