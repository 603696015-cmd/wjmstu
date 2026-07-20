package com.sopia.intelligentTutoringPoints.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.IntelligentSystemConfOp;
import com.sopia.intelligentTutoringPoints.dao.IntelligentWeekDao;

public class IntelligentWeekDaoImpl implements IntelligentWeekDao {
	private static final Log logger = LogFactory.getLog(IntelligentWeekDaoImpl.class);

	public void intelligentLearnWeekBegin(int userid, int classid,
			int courseid, int pageid,int studyCourseRecordId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "{call intelligentweeklearnbegin(?,?,?,?,?)} ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, courseid);
			ps.setInt(4, pageid);
			ps.setInt(5,studyCourseRecordId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("开始学习（周）失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void intelligentLearnWeekEnd(int userid, int classid, int courseid,
			int pageid,int studyCourseRecordId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "{call intelligentweeklearnend(?,?,?,?,?,?,?,?,?)} ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, courseid);
			ps.setInt(4, pageid);
			ps.setInt(5,studyCourseRecordId);
			ps.setDouble(6,IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREWEEK));
			ps.setDouble(7,IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREWEEKPER));
			ps.setDouble(8,IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORECLASS));
			ps.setDouble(9,IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORECLASSPER));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("结束学习（周）失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

}
