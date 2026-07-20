package com.sopia.statman.entities;

import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyExamPaper;


/**
 * 查询装载实体
 * @author Administrator
 *
 */
public class Queryobj {
	private Course course;
	private CoursePage coursePage;
	private ELUser elUser;
	private MyExamPaper myExamPaper;
	private String tableName;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}
	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}
}
