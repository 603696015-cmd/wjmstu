package com.sopia.intelligentTutoringPoints.dao;

import com.sopia.common.ElException;

/**
 * 学习成绩智能辅导分
 * @author TMK
 *
 */
public interface IntelligentAcademicDao {
	/**
	 * 章节考试
	 * @param userid
	 * @param roomid
	 * @param classid
	 * @param courseid
	 * @param pageid
	 * @throws ElException
	 */
	public void intelligentAcademic(int userid,int roomid,int classid,int courseid,int pageid,int myexampaperid) throws ElException;

	/**
	 * 课程考试
	 * @param userid
	 * @param roomid
	 * @param classid
	 * @param courseid
	 * @param myexampaperid
	 * @param classtype
	 * @throws ElException
	 */
	public void intelligentAcademicCourse(int userid,int roomid,int classid,int courseid,int myexampaperid,int classtype) throws ElException;
}
