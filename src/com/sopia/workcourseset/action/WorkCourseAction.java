package com.sopia.workcourseset.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.entities.Course;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.studyman.action.StudyQuiz;
import com.sopia.workcourseset.dao.WorkCourseDao;
import com.sopia.workcourseset.entity.WorkCourse;

public class WorkCourseAction  extends BaseAction{
	private static final Log logger = LogFactory.getLog(WorkCourseAction.class);
	private WorkCourseDao workCourseDao;
	private List<WorkCourse>  workCourses;
	private WorkCourse workCourse;
	private ElClass elClass;
	private List<Course>  workCourseByClass;
	
	private List<BaseDataType> workType;
	
	private int elclassId;
	private Course course;
	

	public int getElclassId() {
		return elclassId;
	}

	public void setElclassId(int elclassId) {
		this.elclassId = elclassId;
	}

	public ElClass getElClass() {
		return elClass;
	}

	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}

	public WorkCourse getWorkCourse() {
		return workCourse;
	}

	public void setWorkCourse(WorkCourse workCourse) {
		this.workCourse = workCourse;
	}

	public List<WorkCourse> getWorkCourses() {
		return workCourses;
	}

	public void setWorkCourses(List<WorkCourse> workCourses) {
		this.workCourses = workCourses;
	}

	public WorkCourseDao getWorkCourseDao() {
		return workCourseDao;
	}

	public void setWorkCourseDao(WorkCourseDao workCourseDao) {
		this.workCourseDao = workCourseDao;
	}

	public String work_course_set() throws ElException{
		elClass=workCourseDao.getElclassInfo(SystemConfOp.getValue(ElConstants.SD_ELCLASS));
		workCourses=workCourseDao.listWorkCourse(getPageNow(),getPageSize());
		return "work_course_set";
	}
	
	public String work_course_addInit() throws ElException{
		elClass=workCourseDao.getElclassInfo(SystemConfOp.getValue(ElConstants.SD_ELCLASS));
		workType = workCourseDao.getBaseTypeList();
		return "work_course_add";
	}
	
	public String work_course_add() throws ElException{
		workCourseDao.addWorkCourse(workCourse);
		return "work_course_set";
	}
	
	public String work_course_alterInit() throws ElException{
		elClass=workCourseDao.getElclassInfo(SystemConfOp.getValue(ElConstants.SD_ELCLASS));
		workCourse=workCourseDao.getInfoById(workCourse.getId());
		return "work_course_alter";
	}
	
	public String work_course_alter() throws ElException{
		workCourseDao.updateWorkCourse(workCourse);
		return "work_course_set";
	}
	
	public String elclass_course_select() throws ElException{
		System.out.println(elclassId);
		workCourseByClass = workCourseDao.listWorkCourseByClass(elclassId);
		return "elclass_course_select";
	}
	
	public String course_select() throws ElException{
		course = workCourseDao.CourseById(course.getId());
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			String d= "{\"id\":\"" + course.getId() + "\",\"name\":\"" + course.getName() + "\"}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("ajax部门查看错误",e);
		}
		return null;
	}
	

	public List<Course> getWorkCourseByClass() {
		return workCourseByClass;
	}

	public void setWorkCourseByClass(List<Course> workCourseByClass) {
		this.workCourseByClass = workCourseByClass;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<BaseDataType> getWorkType() {
		return workType;
	}

	public void setWorkType(List<BaseDataType> workType) {
		this.workType = workType;
	}

	
	
		

}
