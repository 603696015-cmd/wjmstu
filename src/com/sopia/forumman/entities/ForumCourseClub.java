package com.sopia.forumman.entities;

import java.util.List;

import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;

public class ForumCourseClub {
	private  CourseType   name ;
	private  List<Course>  zuixincours;
	private  List<Course>  hotcours;
	public CourseType getName() {
		return name;
	}
	public void setName(CourseType name) {
		this.name = name;
	}
	public List<Course> getZuixincours() {
		return zuixincours;
	}
	public void setZuixincours(List<Course> zuixincours) {
		this.zuixincours = zuixincours;
	}
	public List<Course> getHotcours() {
		return hotcours;
	}
	public void setHotcours(List<Course> hotcours) {
		this.hotcours = hotcours;
	}
	
	

}
