package com.sopia.studyman.dao;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.ScormCourse;

public interface StudyScormDao {
	 
	/**注册scorm课程学习
	 * @param userID
	 * @param courseID
	 * @throws ElException
	 */
	public void registerCourse(String userID,String courseID,String classID) throws ElException;
	
	/**scorm课程学习
	 * @param courseID
	 * @param userID
	 * @param requestedSCO
	 * @param buttonType
	 * @param exitFlag
	 * @return
	 * @throws ElException
	 */
	public ScormCourse intoCourse(String courseID, String userID,String classID,
			String requestedSCO,String nowSCO, String buttonType)throws ElException;
}
