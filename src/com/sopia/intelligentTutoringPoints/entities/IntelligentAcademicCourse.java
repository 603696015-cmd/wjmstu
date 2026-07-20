package com.sopia.intelligentTutoringPoints.entities;

import com.sopia.classman.entities.ElClass;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;

/**
 * 智能辅导分之课程考试
 * @author TMK
 *
 */
public class IntelligentAcademicCourse {
	private int userid;		//用户ID
	private double totalscore;	//得分
	private int classid;	//培训班ID
	private int courseid;	//课程ID
	private ElClass elClass;
	private Course course;
	public IntelligentAcademicCourse(){}
	public IntelligentAcademicCourse(int userid,int classid,int courseid,double totalscore){
		this.userid = userid;
		this.classid = classid;
		this.courseid = courseid;
		this.totalscore = totalscore;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public double getTotalscore() {
		return totalscore;
	}
	public void setTotalscore(double totalscore) {
		this.totalscore = totalscore;
	}
	public ElClass getElClass() {
		return elClass;
	}
	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	
}
