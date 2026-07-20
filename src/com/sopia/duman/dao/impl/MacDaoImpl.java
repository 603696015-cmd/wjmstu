package com.sopia.duman.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.duman.dao.MacDao;
import com.sopia.duman.entities.Mac;

public class MacDaoImpl implements MacDao{

	public List<Mac> getAllMac(int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Mac> macs = new ArrayList<Mac>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*,rownum rn  from (select * from mac_address) t where rownum<=?) where rn>=?");
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				Mac m = new Mac();
				m.setId(rs.getInt("id"));
				m.setMacaddres(rs.getString("mac"));
				m.setAddtime(rs.getTimestamp("addtime"));
				macs.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return macs;
	}

	public int getAllMacCount() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from mac_address");
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return count;
	}

	public void addMac(Mac mac) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into mac_address(mac,addtime) values(?,?)");
			ps.setString(1, mac.getMacaddres());
			ps.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void delMac(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from mac_address where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public boolean isExistMac(String macaddr) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean exist = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from mac_address where mac=?");
			ps.setString(1, macaddr);
			rs = ps.executeQuery();
			if(rs.next()){
				exist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return exist;
	}

}
