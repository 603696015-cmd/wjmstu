package com.sopia.intelligentTutoringPoints.entities;

import com.sopia.classman.entities.ElClass;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;

/**
 * 智能辅导分之章节考试
 * @author Administrator
 *
 */
public class IntelligentAcademic {
	private int userid;		//用户ID
	private double totalscore;	//得分
	private int classid;	//培训班ID
	private int courseid;	//课程ID
	private int pageid;		//章节ID
	private ElClass elClass;
	private Course course;
	private CoursePage coursePage;
	public IntelligentAcademic(){}
	public IntelligentAcademic(int userid,int classid,int courseid,int pageid,double totalscore){
		this.userid = userid;
		this.classid = classid;
		this.courseid = courseid;
		this.pageid = pageid;
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
	public CoursePage getCoursePage() {
		return coursePage;
	}
	public void setCoursePage(CoursePage coursePage) {
		this.coursePage = coursePage;
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
	public int getPageid() {
		return pageid;
	}
	public void setPageid(int pageid) {
		this.pageid = pageid;
	}
	public double getTotalscore() {
		return totalscore;
	}
	public void setTotalscore(double totalscore) {
		this.totalscore = totalscore;
	}

	
	
}
