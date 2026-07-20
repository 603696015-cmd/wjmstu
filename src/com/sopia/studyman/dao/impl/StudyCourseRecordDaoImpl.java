package com.sopia.studyman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.studyman.StudyConstants;
import com.sopia.studyman.dao.StudyCourseRecordDao;
import com.sopia.studyman.entities.MyCourseRecord;

public class StudyCourseRecordDaoImpl implements StudyCourseRecordDao {
	private static final Log logger=LogFactory.getLog(StudyCourseRecordDaoImpl.class);
	/**
	 * 添加学员课程学习记录
	 * @param myCourseRecord
	 * @throws ElException
	 */
//	public void addStudyCourseRecord(MyCourseRecord myCourseRecord) throws ElException{
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("insert into study_course_record(scid,cpid,userid,begintime,status) values(?,?,?,?,?)");
//			ps.setInt(1, myCourseRecord.getMyCourse().getCourse().getId());
//			ps.setInt(2, myCourseRecord.getCoursePage().getId());
//			ps.setInt(3, myCourseRecord.getEluser().getId());
//			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
//			ps.setInt(5, 1);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("添加学员课程学习记录出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}
	/**
	 * 更新学员课程学习记录的状态和退出时间
	 * @param status
	 * @param endtime 退出时间（如果退出时间等于开始时间，那么这条数据记录有误，可能是服务器重启造成）
	 * @throws ElException
	 */
	public void updateStudyCourseRecordStatus(int scid,int cpid,int userid,int status,Timestamp endtime) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update study_course_record set status=?,endtime=? where scid=? and cpid=? and userid=? and max(endtime)");
			ps.setInt(1, status);
			ps.setTimestamp(2, endtime);
			ps.setInt(3, scid);
			ps.setInt(4, cpid);
			ps.setInt(5, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新学员课程学习记录的状态和退出时间出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
}
