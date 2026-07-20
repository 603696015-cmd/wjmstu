package com.sopia.shopping.entities;

import java.sql.Timestamp;

import com.sopia.classman.entities.ElClass;
import com.sopia.duman.entities.ELUser;

public class ClassOrder {
	private  int id;//订单id
	private  String sid;//订单id
	private  int countFree;//免费课程数
	private  int countCourse;//课程数量
	private Timestamp cdate;
	private Timestamp odate;
	private  ElClass elClass;  
	private int status ;
	private String sstatus ;//状态
	private int count ;//分配数量
	private float price;//单价
	private float zprice;//总价
	private int otherCourseCount;//别人制作的课程
	private ELUser user;
	
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public int getOtherCourseCount() {
		return otherCourseCount;
	}
	public void setOtherCourseCount(int otherCourseCount) {
		this.otherCourseCount = otherCourseCount;
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
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public int getCountFree() {
		return countFree;
	}
	public void setCountFree(int countFree) {
		this.countFree = countFree;
	}
	public int getCountCourse() {
		return countCourse;
	}
	public void setCountCourse(int countCourse) {
		this.countCourse = countCourse;
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
	public ElClass getElClass() {
		return elClass;
	}
	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSstatus() {
		return sstatus;
	}
	public void setSstatus(String sstatus) {
		this.sstatus = sstatus;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getZprice() {
		return zprice;
	}
	public void setZprice() {
		this.zprice = price*count;
	}
	
	public void setZprice(Float a) {
		this.zprice = a;
	}
	
	

}
