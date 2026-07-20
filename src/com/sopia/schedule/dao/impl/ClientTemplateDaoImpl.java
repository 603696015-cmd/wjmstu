package com.sopia.schedule.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.schedule.ZDYTemplateConstants;
import com.sopia.schedule.dao.ClientTemplateDao;
import com.sopia.schedule.entities.ModuleZDY;

public class ClientTemplateDaoImpl implements ClientTemplateDao {
	private static final Log logger = LogFactory
			.getLog(ClientTemplateDaoImpl.class);

	public ModuleZDY select_moduleZDY_by_moduleid(int moduleid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ModuleZDY moduleZDY = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ZDYTemplateConstants.TB_ZIDINGYI_JSP_QUERY_BY_MODULEID));
			ps.setInt(1, moduleid);
			rs = ps.executeQuery();
			if (rs.next()) {
				moduleZDY = new ModuleZDY();
				moduleZDY.setId(rs.getInt("id"));
				moduleZDY.setModuleid(rs.getInt("moduleid"));
				moduleZDY.setAddjsp(rs.getString("addjsp")==null?"":rs.getString("addjsp"));
				moduleZDY.setUpdatejsp(rs.getString("updatejsp")==null?"":rs.getString("updatejsp"));
				moduleZDY.setViewjsp(rs.getString("viewjsp")==null?"":rs.getString("viewjsp"));
				moduleZDY.setCssfile(rs.getString("cssfile")==null?"":rs.getString("cssfile"));
			}
		} catch (Exception e) {
			logger.error("根据模块id查询该模块上传模板信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return moduleZDY;
	}

	public void updateModuleZDYByModuleid(int moduleid, int uploadType,
			String stFileName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ZDYTemplateConstants.TB_ZIDINGYI_JSP_CALL_UPDATEMODULEZDY_BY_MODULEID));
			ps.setInt(1, moduleid);
			ps.setInt(2, uploadType);
			ps.setString(3, stFileName);
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据模块id修改添加页面或者修改页面或者查看页面的filename出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

}
