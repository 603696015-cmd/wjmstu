package com.sopia.openmeetings;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

public class OmDaoImpl implements OmDao {
	private static final Log logger = LogFactory.getLog(OmDaoImpl.class);

	public void addOmRoom(Rooms rooms) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = OmUtil.getConnection();

			ps = ct
					.prepareStatement("insert into rooms(comment_field,"
							+ "name,"
							+ "roomtypes_id,"
							+ "starttime,"
							+ "updatetime,"
							+ "deleted"
							+ ",ispublic,"
							+ "numberOfPartizipants, appointMent,isdemoroom,ismoderatedroom) values( '"
							+ rooms.getComment() + "', '" + rooms.getName()
							+ "', '" + rooms.getRoomtype()
							+ "',  ?, ?,'false',1, 1008, 0 , 0 , 0 )");
			ps.setDate(1, rooms.getStarttime());
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next())
				rooms.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			OmUtil.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterOmRoom(Rooms rooms) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = OmUtil.getConnection();
			String sql = "update rooms set comment_field='"
				+ rooms.getComment() + "'," + "name='" + rooms.getName()
				+ "'," + "roomtypes_id='" + rooms.getRoomtype() + "',"
				+ "starttime=?," + "updatetime= ? where rooms_id = "
				+ rooms.getId();
			System.out.println(sql);
			ps = ct.prepareStatement(sql);
			ps.setDate(1, rooms.getStarttime());
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			OmUtil.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Rooms> listOmRoom(int roomtypes_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Rooms> rooms = new ArrayList<Rooms>();
		try {
			ct = OmUtil.getConnection();
			ps = ct
					.prepareStatement("select rooms_id,comment_field ,"
							+ "name,"
							+ "roomtypes_id,"
							+ "starttime,"
							+ "updatetime,"
							+ "numberOfPartizipants from rooms where roomtypes_id = ? and deleted = 'false'");
			ps.setInt(1, roomtypes_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Rooms r = new Rooms();
				r.setId(rs.getInt(1));
				r.setComment(rs.getString(2));
				r.setName(rs.getString(3));
				r.setRoomtype(rs.getInt(4));
				r.setStarttime(rs.getDate(5));
				r.setUpdatetime(rs.getDate(6));
				rooms.add(r);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			OmUtil.closeConnectInfo(ct, ps, rs);
		}
		return rooms;
	}

	public Rooms getOmRoom(int rooms_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Rooms r = new Rooms();
		try {
			ct = OmUtil.getConnection();
			ps = ct.prepareStatement("select rooms_id,comment_field," + "name,"
					+ "roomtypes_id," + "starttime," + "updatetime,"
					+ "numberOfPartizipants from rooms where rooms_id = ? ");
			ps.setInt(1, rooms_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				r.setId(rs.getInt(1));
				r.setComment(rs.getString(2));
				r.setName(rs.getString(3));
				r.setRoomtype(rs.getInt(4));
				r.setStarttime(rs.getDate(5));
				r.setUpdatetime(rs.getDate(6));
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			OmUtil.closeConnectInfo(ct, ps, rs);
		}
		return r;
	}

	public boolean moderatorHasLogin(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean b = false;
		try {
			ct = OmUtil.getConnection();
			ps = ct
					.prepareStatement(" select * from soaplogin where room_id=? and becomemoderator =1 ");
			ps.setInt(1, roomid);

			rs = ps.executeQuery();
			if (rs.next()) {
				b = true;
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			// OmUtil.closeConnectInfo(ct, ps, rs);
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}
	/**
	 * 控制讲师是否进入
	 * @param roomid
	 * @param becomemoderator （1进入，0未进入）
	 * @return
	 * @throws ElException
	 */ 
	public boolean setModeratorHasLoginOut(int roomid, int becomemoderator) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean b = false;
		try {
			ct = OmUtil.getConnection();
			ps = ct.prepareStatement(" update soaplogin set becomemoderator = ? where room_id = ?");
			ps.setInt(1, becomemoderator); 
			ps.setInt(2, roomid); 
			ps.executeUpdate(); 
		} catch (Exception e) {
			logger.error("更新讲师进入出错！", e);
			throw new ElException(e);
		} finally {
			// OmUtil.closeConnectInfo(ct, ps, rs);
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	public String getUserByLogin(String username) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String r = "";
		try {
			ct = OmUtil.getConnection();
			ps = ct
					.prepareStatement("select user_id from users where login = ? ");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				r = rs.getString(1);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			OmUtil.closeConnectInfo(ct, ps, rs);
		}
		return r;
	}

	public boolean checkZUserInTr(int userid, int roomid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select * from omroom_assign where userid= ? and rooms_id =?  ");
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public List<ELUser> getRoomsUser(int roomid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		List<ELUser> assignedUsers = new ArrayList<ELUser>();

		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select tra.userid,eu.realname,eu.depid,dep.name,eu.username from "
							+ "omroom_assign tra,eluser eu,department dep where eu.depid=dep.id and eu.id = tra.userid and tra.rooms_id =?  ");
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(5));
				eu.setDepartment(new Department(rs.getInt(3), rs.getString(4)));
				assignedUsers.add(eu);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return assignedUsers;
	}

	public void assign2users(int userid, int roomid, int type)
			throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into omroom_assign(userid,rooms_id,rooms_type) values(?,?,?) ");
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.setInt(3, type);
			ps.executeUpdate();
			// ELUser eu = new UserDaoImpl().getUserById(userid);
			// updateUser(eu);
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void unassign2users(int userid, int roomid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from omroom_assign where userid=? and rooms_id =? ");
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Rooms> listMyRooms(int userid, int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Rooms> rooms = new ArrayList<Rooms>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select rooms_id from omroom_assign where userid=? and rooms_type = ? ");
			ps.setInt(1, userid);
			ps.setInt(2, typeid);

			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				rooms.add(getOmRoom(id));
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			// OmUtil.closeConnectInfo(ct, ps, rs);
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return rooms;
	}
}
