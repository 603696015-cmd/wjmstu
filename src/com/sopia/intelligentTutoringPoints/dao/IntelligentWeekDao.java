package com.sopia.intelligentTutoringPoints.dao;

import com.sopia.common.ElException;

/**
 * 智能辅导分周学习时间dao
 * @author TMK
 *
 */
public interface IntelligentWeekDao {
	
	/**
	 * 开始学习（周）
	 * @param userid
	 * @param classid
	 * @param courseid
	 * @param pageid
	 * @param studyCourseRecordId
	 * @throws ElException
	 */
	public void intelligentLearnWeekBegin(int userid,int classid,int courseid,int pageid,int studyCourseRecordId) throws ElException;
	
	/**
	 * 结束学习（周）
	 * @param userid
	 * @param classid
	 * @param courseid
	 * @param pageid
	 * @param studyCourseRecordId
	 * @throws ElException
	 */
	public void intelligentLearnWeekEnd(int userid,int classid,int courseid,int pageid,int studyCourseRecordId) throws ElException;

}
