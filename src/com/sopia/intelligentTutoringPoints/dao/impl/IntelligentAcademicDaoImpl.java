package com.sopia.intelligentTutoringPoints.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.IntelligentSystemConfOp;
import com.sopia.intelligentTutoringPoints.dao.IntelligentAcademicDao;

public class IntelligentAcademicDaoImpl implements IntelligentAcademicDao {
	private static final Log logger = LogFactory.getLog(IntelligentAcademicDaoImpl.class);
	
	public void intelligentAcademic(int userid, int roomid,int classid,int courseid,int pageid,int myexampaperid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call intelligent_academic_pro(?,?,?,?,?,?,?,?)}");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.setInt(3, classid);
			ps.setInt(4, courseid);
			ps.setInt(5, pageid);
			ps.setInt(6, myexampaperid);
			ps.setDouble(7, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMPAGE));
			ps.setDouble(8, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMPAGEPER));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("ÕÂ½Ú¿¼ÊÔÊ§°Ü", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void intelligentAcademicCourse(int userid, int roomid, int classid,
			int courseid, int myexampaperid,int classtype) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call intelligent_academic_cou_pro(?,?,?,?,?,?,?,?,?)}");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.setInt(3, classid);
			ps.setInt(4, courseid);
			ps.setInt(5, myexampaperid);
			ps.setInt(6, classtype);
			ps.setDouble(7, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMCOURSE));
			ps.setDouble(8, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMCOURSE1TO3PER));
			ps.setDouble(9, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMCOURSE4TO6PER));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("¿Î³Ì¿¼ÊÔÊ§°Ü", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

}
