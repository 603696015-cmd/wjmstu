package com.sopia.studyman.dao;

import java.sql.Timestamp;

import com.sopia.common.ElException;
import com.sopia.studyman.entities.MyCourseRecord;

public interface StudyCourseRecordDao {
	/**
	 * 添加学员课程学习记录
	 * @param myCourseRecord
	 * @throws ElException
	 */
//	public void addStudyCourseRecord(MyCourseRecord myCourseRecord) throws ElException;
	/**
	 * 更新学员课程学习记录的状态和退出时间
	 * @param status
	 * @param endtime 退出时间（如果退出时间等于开始时间，那么这条数据记录有误，可能是服务器重启造成）
	 * @throws ElException
	 */
	public void updateStudyCourseRecordStatus(int scid,int cpid,int userid,int status,Timestamp endtime) throws ElException;
}
