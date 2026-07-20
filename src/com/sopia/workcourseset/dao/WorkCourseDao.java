package com.sopia.workcourseset.dao;

import java.util.List;
import java.util.Map;

import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.workcourseset.entity.WorkCourse;


public interface WorkCourseDao {
	
	public List<WorkCourse> listWorkCourse(int pageNow, int pageSize) throws ElException;
	public List<WorkCourse> listWorkCourse2(int pageNow, int pageSize) throws ElException;

	public ElClass getElclassInfo(String name)throws ElException;
	public WorkCourse getInfoById(int id) throws ElException ;

	public List<Course> listWorkCourseByClass(int elclassId)throws ElException;

	public Course CourseById(int id) throws ElException;
	public void updateWorkCourse(WorkCourse wc) throws ElException;
	public void addWorkCourse(WorkCourse wc) throws ElException;
	public ExamRoom courseByRoom(int classid,int courseid) throws ElException;
	//根据课程类型和培训班id得到相关数据
	public int getCourseid(int type) throws ElException;
	//根据userid得到相关考场 试卷数据
	public int getEroomid(int courseid,int classid) throws ElException;
	public int getEpid(int roomid) throws ElException;
	
	public List<BaseDataType> getBaseTypeList() throws ElException;
	//sd0110
	public Map<String,List<BaseDatat>> getBaseTypeAndDataList() throws ElException;
	public List<WorkCourse> listWorkCourseUser() throws ElException;
	
	

}
