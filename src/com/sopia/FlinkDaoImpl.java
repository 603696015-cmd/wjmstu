package com.sopia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;

public class FlinkDaoImpl  {
	private static final Log logger = LogFactory.getLog(FlinkDaoImpl.class);


	public List<Flink> listFLink(int pageNow, int pageSize) throws ElException {
		List<Flink> listFLink = new ArrayList<Flink>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select id,flname,fldesc,fhref,sort,row_number() over(order by sort desc ) rownum  from FLINK ) t where t.rownum between ? and ?");
//			ps.setInt(1, pageNow * pageSize);
//			ps.setInt(2, pageSize);
			ps.setInt(1, pageNow * pageSize);
			ps.setInt(2, pageSize * pageNow+pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Flink f = new Flink();
				f.setId(rs.getInt(1));
				f.setFlname(rs.getString(2));
				f.setFldesc(rs.getString(3));
				f.setFhref(rs.getString(4));
				f.setSort(rs.getInt(5));
				listFLink.add(f);
			}
		} catch (Exception e) {
			logger.error("人才库-专家列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return listFLink;
	}

	public void addFlink(Flink flink) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into  FLINK (flname,fldesc,fhref) values(?,?,?)");
			ps.setString(1, flink.getFlname());
			ps.setString(2, flink.getFldesc());
			ps.setString(3, flink.getFhref());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-专家列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterFlink(Flink flink) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update FLINK set flname=?, fldesc = ?,fhref = ? where id = ?");
			ps.setString(1, flink.getFlname());
			ps.setString(2, flink.getFldesc());
			ps.setString(3, flink.getFhref());
			ps.setInt(4, flink.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-专家列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Flink getFlinkById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Flink f = new Flink();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from  FLINK where id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				f.setId(rs.getInt(1));
				f.setFlname(rs.getString(2));
				f.setFldesc(rs.getString(3));
				f.setFhref(rs.getString(4));
				f.setSort(rs.getInt(5));
			}
		} catch (Exception e) {
			logger.error("人才库-专家列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return f;
	}

	public int getSortByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select sort from  FLINK where id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("人才库-专家列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void upSort(int id ) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update FLINK set sort=? where id = ?");
			ps.setInt(1, getSortByid(id)+1);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-专家列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void flinkDelete(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from  FLINK where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-专家列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}


	public int flinkSize() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from  FLINK where 1 = ?");
			ps.setInt(1, 1);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("人才库-专家列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

}
