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
import com.sopia.intelligentTutoringPoints.dao.IntelligentProportionDao;

public class IntelligentProportionDaoImpl implements IntelligentProportionDao {
	private static final Log logger = LogFactory.getLog(IntelligentProportionDaoImpl.class);

	public void intelligentProportion(int userid, int myExamPaperid,
			int examPaperid, int blockid, int questionid, int classid,
			int courseid, int pageid, int roomid,int qtype) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call intelligent_proportion_pro(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			ps.setInt(1, userid);
			ps.setInt(2, myExamPaperid);
			ps.setInt(3, examPaperid);
			ps.setInt(4, blockid);
			ps.setInt(5, questionid);
			ps.setInt(6, classid);
			ps.setInt(7, courseid);
			ps.setInt(8, pageid);
			ps.setInt(9, roomid);
			ps.setDouble(10, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONPROCESS));
			ps.setDouble(11, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONPROCESSPER));
			ps.setDouble(12, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONTIME));
			ps.setDouble(13, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONTIMEPER));
			ps.setInt(14, qtype);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("¸´ÌýÊ§°Ü", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void intelligentRecoding(int userid, int myExamPaperid,
			int examPaperid, int blockid, int questionid, int classid,
			int courseid, int pageid, int roomid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call intelligent_recoding_pro(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			ps.setInt(1, userid);
			ps.setInt(2, myExamPaperid);
			ps.setInt(3, examPaperid);
			ps.setInt(4, blockid);
			ps.setInt(5, questionid);
			ps.setInt(6, classid);
			ps.setInt(7, courseid);
			ps.setInt(8, pageid);
			ps.setInt(9, roomid);
			ps.setDouble(10, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGPROCESS));
			ps.setDouble(11, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGPROCESSPER));
			ps.setDouble(12, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGTIME));
			ps.setDouble(13, IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGTIMEPER));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("Â¼ÒôÊ§°Ü", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

}
