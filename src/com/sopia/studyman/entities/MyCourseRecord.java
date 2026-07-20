package com.sopia.studyman.entities;

import java.sql.Timestamp;

import com.sopia.classman.entities.ElClass;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.duman.entities.ELUser;

public class MyCourseRecord {
	private int id;
	private Course course;
	private ElClass elClass;
	private CoursePage coursePage;
	private ELUser eluser;
	private Timestamp begintime;
	private Timestamp endtime;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public ElClass getElClass() {
		return elClass;
	}
	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}
	public CoursePage getCoursePage() {
		return coursePage;
	}
	public void setCoursePage(CoursePage coursePage) {
		this.coursePage = coursePage;
	}
	public ELUser getEluser() {
		return eluser;
	}
	public void setEluser(ELUser eluser) {
		this.eluser = eluser;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
//	public MyCourseRecord(int courseid, int classid, int cpid,int userid) {
//		this.course = new Course(courseid);
//		this.elClass = new ElClass(classid);
//		this.coursePage = new CoursePage(cpid);
//		this.eluser = new ELUser(userid);
//	}
	public MyCourseRecord() {
	}
}
