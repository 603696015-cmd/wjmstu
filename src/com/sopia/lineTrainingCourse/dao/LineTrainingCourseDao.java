package com.sopia.lineTrainingCourse.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.lineTrainingCourse.entities.LineTrainingCourse;
import com.sopia.lineTrainingCourse.entities.LineTrainingCourseAssign;
import com.sopia.lineTrainingCourse.entities.TrainType;

public interface LineTrainingCourseDao {
	
	/**
	 * 
	 * @param dep
	 * @param lineTrainingCourse
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws ElException
	 */
	public abstract List<LineTrainingCourse> lineTrainingCourseList(String frontOrBack,Department dep, LineTrainingCourse lineTrainingCourse,int userid,int pageNow, int pageSize,Timestamp starttime,Timestamp endtime ) throws ElException;
	
	/**
	 * 
	 * @param dep
	 * @param lineTrainingCourse
	 * @param userid
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws ElException
	 */
	public abstract int lineTrainingCourseListSize(String frontOrBack,Department dep, LineTrainingCourse lineTrainingCourse,int userid ,Timestamp starttime,Timestamp endtime) throws ElException;
	
	/**
	 * 
	 * @return
	 * @throws ElException
	 */
	public List<TrainType> getTrainTypes() throws ElException;
	
	/**
	 * 
	 * @param line_training_course_add_need_sh
	 * @param userid
	 * @param lineTrainingCourse
	 * @throws ElException
	 */
	public void addLineTrainingCourse(boolean line_training_course_add_need_sh,int userid,LineTrainingCourse lineTrainingCourse) throws ElException;
	
	/**
	 * 
	 * @return
	 * @throws ElException
	 */
	public LineTrainingCourse getLineTrainingCourseById(String type,int id) throws ElException;
	
	
	/**
	 * 
	 * @param lineTrainingCourse
	 * @throws ElException
	 */
	public void updateLineTrainingCourseById(LineTrainingCourse lineTrainingCourse) throws ElException;
	
	
	public boolean checkIs_open(int id) throws ElException;
	
	public void deleteLineTrainingCourse(int id) throws ElException;
	
	public void openLineTrainingCourse(int id) throws ElException;
	
	public void NotOpenLineTrainingCourse(int id) throws ElException;
	
	public String signByPerson(String option,LineTrainingCourse lineTrainingCourse,int userid) throws ElException;
	public String removePerson(String option,LineTrainingCourse lineTrainingCourse,int userid) throws ElException;
	
	public List<LineTrainingCourseAssign> getAssignList(Department department,LineTrainingCourse lineTrainingCourse,LineTrainingCourseAssign assign,int pageNow, int pageSize) throws ElException;
	
	public int getAssignListSize(Department department,LineTrainingCourse lineTrainingCourse,LineTrainingCourseAssign assign) throws ElException;
	
	public void option_in_shenhePage(LineTrainingCourseAssign assign,String fieldName,int status) throws ElException;
	
	public List<LineTrainingCourseAssign> getAssignList_result_entry(Department department,LineTrainingCourse lineTrainingCourse,LineTrainingCourseAssign assign,int pageNow, int pageSize) throws ElException;
	
	public int getAssignListSize_result_entry(Department department,LineTrainingCourse lineTrainingCourse,LineTrainingCourseAssign assign) throws ElException;
	
	public void changeScore(int id,double score) throws ElException;
	
	public void changeCredit(int id,double credit) throws ElException;
	public List<LineTrainingCourseAssign> getCredit_get(int line_training_course_id) throws ElException;
	
	public void change_is_get_certificate(int change_is_get_certificate,int id) throws ElException;
	
	
	public List<LineTrainingCourse> personal_lineTrainingCourseList(LineTrainingCourse lineTrainingCourse,int userid,int pageNow, int pageSize,Timestamp starttime,Timestamp endtime) throws ElException;
	public int personal_lineTrainingCourseListSize(LineTrainingCourse lineTrainingCourse,int userid,Timestamp starttime,Timestamp endtime) throws ElException;

	public LineTrainingCourse getLineTrainingCourseById_personal(int userid,int id) throws ElException;
	
	public void updateLineTrainingCourseAssignById(LineTrainingCourseAssign assign) throws ElException;
	
	public List<Integer> check_is_signed(int assign_id) throws ElException;
	
	public List<ELUser> getDistributionStudents(LineTrainingCourse lineTrainingCourse,Department depTree, int depid,
			ELUser eu, int role, int pageNow, int pageSize) throws ElException;
			
	public int getDistributionStudentsCount(LineTrainingCourse lineTrainingCourse,Department depTree, int depid,
			ELUser eu, int role) throws ElException;
	
	public boolean check_signed(int courseid,int userid) throws ElException;
	public int check_joinWay(int courseid,int userid) throws ElException;
}
