package com.sopia.simulation.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.simulation.dao.SimulationDao;
import com.sopia.simulation.entity.SimulationResult;

public class SimulationDaoImpl implements SimulationDao{
	private static final Log logger = LogFactory.getLog(SimulationDaoImpl.class);
	@Override
	public void addSimulation(SimulationResult sr)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String insertSql = "insert into simulation_result(userid, result, score, name, paper_id, stu_no,create_time)values(?,?,?,?,?,?,?)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(insertSql);
			ps.setInt(1,  sr.getUserId());
			ps.setString(2, sr.getResult());
			ps.setString(3, sr.getScore());
			ps.setString(4, sr.getName());
			ps.setInt(5, sr.getPaperId());
			ps.setString(6, sr.getStuNo());
			ps.setTimestamp(7, sr.getCreateTime());
			
		    ps.executeUpdate();
		} catch (Exception e) {
			logger.error("插入用户模拟考试试卷问题", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	@Override
	public SimulationResult getSimlationResultById(String userid, String paperId) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		SimulationResult sr = new SimulationResult();
		try {
			String sql = "select id,userid, result, score, name, paper_id, stu_no,create_time from simulation_result where userid = ? and paperId = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setString(1, userid);
			ps.setString(2, paperId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				sr.setId(rs.getInt(1));
				sr.setUserId(rs.getInt(2));
				sr.setResult(rs.getString(3));
				sr.setScore(rs.getString(4));
				sr.setName(rs.getString(5));
				sr.setPaperId(rs.getInt(6));
				sr.setStuNo(rs.getString(7));
				sr.setCreateTime(rs.getTimestamp(8));
			}
			
		} catch (Exception e) {
			logger.error("插入用户模拟考试试卷问题", e);
			throw new Exception(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sr;
	}

}
