package com.sopia.pfms.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.pfms.dao.DamageMemberDao;
import com.sopia.pfms.entities.DamageMember;

public class DamageMemberDaoImpl implements DamageMemberDao {
	private static final Log logger = LogFactory.getLog(DamageMemberDaoImpl.class);

	public void addDamageMember(DamageMember damageMember) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(
					"insert into damageMember (id,name,sex,personId,birthday," +
					"workCompany,hometown,picture,fabushijian)" +
					" values (damageMember_sequence.nextval,?,?,?,?,?,?,?,sysdate)"
					);
			ps.setString(1, damageMember.getName());
			ps.setString(2, damageMember.getSex());
			ps.setString(3, damageMember.getPersonId());
			ps.setTimestamp(4, damageMember.getBirthday());
			ps.setString(5, damageMember.getWorkCompany());
			ps.setString(6, damageMember.getHometown());
			ps.setString(7, damageMember.getPicture());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加定损员失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<DamageMember> damageMemberList(Integer start,Integer size,DamageMember damageMember,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		List<DamageMember> damageMemberList = new ArrayList<DamageMember>();
		try {
			ct = DBConnection.getConnection();
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			if(damageMember != null){
				if(!damageMember.getName().equals(""))
					sqlAppend = sqlAppend + " and name like '%"+damageMember.getName() +"%'";
				if(!damageMember.getSex().equals(""))
					sqlAppend = sqlAppend + " and sex = '"+damageMember.getSex() +"'"; 
				if(!damageMember.getPersonId().equals(""))
					sqlAppend = sqlAppend + " and personId like '%"+damageMember.getPersonId() +"%'"; 
			} 
			if(start == null || size == null){
				sql = "select * from damageMember";
				ps = ct.prepareStatement(sql);
			}else{
				sql = 
					"select b.* from " +
						" (select a.*,rownum rn from " +
							" (select * from damageMember where 1=1" + sqlAppend + 
							" order by fabushijian desc) a " +
						" where rownum<=?) b" +
					" where rn>=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, size);
				ps.setInt(2, start);
			}
			
			rs = ps.executeQuery();
			while (rs.next()) {
				damageMember = new DamageMember();
				damageMember.setId(rs.getInt("id"));
				damageMember.setName(rs.getString("name"));
				damageMember.setSex(rs.getString("sex"));
				damageMember.setPersonId(rs.getString("personId"));
				damageMember.setBirthday(rs.getTimestamp("birthday"));
				damageMember.setFabushijian(rs.getTimestamp("fabushijian"));
				damageMember.setHometown(rs.getString("hometown"));
				damageMember.setWorkCompany(rs.getString("workCompany"));
				damageMember.setPicture(rs.getString("picture"));
				damageMemberList.add(damageMember);
			}
		} catch (Exception e) {
			logger.error("我的定损员列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return damageMemberList;
	}

	public int getCount(DamageMember damageMember,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql ="";
		String sqlAppend ="";
		try {
			ct = DBConnection.getConnection();
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			if(damageMember != null){
				if(!damageMember.getName().equals(""))
					sqlAppend = sqlAppend + " and name like '%"+damageMember.getName() +"%'";
				if(!damageMember.getSex().equals(""))
					sqlAppend = sqlAppend + " and sex = '"+damageMember.getSex() +"'"; 
				if(!damageMember.getPersonId().equals(""))
					sqlAppend = sqlAppend + " and personId = '"+damageMember.getPersonId() +"'"; 
			} 
			
			sql = "select count(1) from damageMember where 1=1 "+sqlAppend;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的定损员列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public DamageMember showDamageMemberView(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		DamageMember damageMember;
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from damageMember where id=?";
//			Map<String,Object> params = new HashMap<String,Object>();
//			params.put("id", id);
//			damageMember = new Reflection().getObject(sql, DamageMember.class, params, ct, ps, rs);
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				damageMember = new DamageMember();
				damageMember.setBirthday(rs.getTimestamp("birthday"));
				damageMember.setFabushijian(rs.getTimestamp("fabushijian"));
				damageMember.setHometown(rs.getString("hometown"));
				damageMember.setId(rs.getInt("id"));
				damageMember.setName(rs.getString("name"));
				damageMember.setPersonId(rs.getString("personId"));
				damageMember.setPicture(rs.getString("picture"));
				damageMember.setSex(rs.getString("sex"));
				damageMember.setWorkCompany(rs.getString("workCompany"));
				return damageMember;
			}
		} catch (Exception e) {
			logger.error("显示设备失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return null;
	}

	public void updateDamageMember(DamageMember damageMember)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = 
				"update damageMember set " +
					"name = ?,sex=?,personId=?,birthday=?,workCompany=?," +
					"hometown=?,picture=?" + 
					" where id=?";
			ps = ct.prepareStatement(sql);
			
			ps.setString(1, damageMember.getName());
			ps.setString(2, damageMember.getSex());
			ps.setString(3, damageMember.getPersonId());
			ps.setTimestamp(4, damageMember.getBirthday());
			ps.setString(5, damageMember.getWorkCompany());
			ps.setString(6, damageMember.getHometown());
			ps.setString(7, damageMember.getPicture());
			ps.setInt(8, damageMember.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void deleteDamageMember(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(
					"delete from damageMember where id=?"
					);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除定损员失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

}
