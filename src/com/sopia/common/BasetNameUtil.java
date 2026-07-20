package com.sopia.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.classman.dao.impl.ElClTypeDaoImpl;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.ElFunc;

public class BasetNameUtil {
	private static final Log logger = LogFactory.getLog(ElClTypeDaoImpl.class);

	/**
	 * 根据id获取基础数据类别
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public BaseDataType getBaseTypeById(int id) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		BaseDataType bt=null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name,remack from basedatatype where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				bt=new BaseDataType();
				bt.setId(rs.getInt("id"));
				bt.setName(rs.getString("name"));
				bt.setRemack(rs.getString("remack"));
			}
		} catch (Exception e) {
			logger.error("根据id获取基础数据类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bt;
	}
}
