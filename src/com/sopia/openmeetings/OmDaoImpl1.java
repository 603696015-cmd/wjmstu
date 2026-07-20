package com.sopia.openmeetings;

import java.sql.Connection;
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

public class OmDaoImpl1 implements OmDao {
	private static final Log logger = LogFactory.getLog(OmDaoImpl.class);
	public void alterOmRoom(Rooms rooms) throws ElException {
		// TODO Auto-generated method stub
		
	}
	public boolean moderatorHasLogin(int roomid) throws ElException {
		// TODO Auto-generated method stub
		return false;
	}
	public void addOmRoom(Rooms rooms) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = OmUtil.getConnection();

			ps = ct
					.prepareStatement("insert into rooms(comment,"
							+ "name,"
							+ "roomtypes_id,"
							+ "starttime,"
							+ "updatetime,"
							+ "deleted"
							+ ",ispublic,"
							+ "numberOfPartizipants, appointMent,isdemoroom,ismoderatedroom) values( '"
							+ rooms.getComment() + "', '" + rooms.getName()
							+ "', '" + rooms.getRoomtype() + "', '"
							+ rooms.getStarttime() + "','"
							+ rooms.getUpdatetime()
							+ "','false',1, 8, 0 , 0 , 0 )");
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
					.prepareStatement("select rooms_id,comment ,"
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
			ps = ct.prepareStatement("select rooms_id,comment," + "name,"
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
public String getUserByLogin(String username) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String  r = "";
		try {
			ct = OmUtil.getConnection();
			ps = ct.prepareStatement("select user_id from users where login = ? ");
			ps.setString(1,  username);
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
					.prepareStatement("insert omroom_assign(userid,rooms_id,	rooms_type) values(?,?,?) ");
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.setInt(3, type);
			ps.executeUpdate();
			ELUser eu = new UserDaoImpl().getUserById(userid);
			updateUser(eu);
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void unassign2users(int userid, int roomid)
			throws ElException {
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
	public List<Rooms>  listMyRooms(int userid, int typeid) throws ElException {
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
//			OmUtil.closeConnectInfo(ct, ps, rs);
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return rooms;
	}
	private void updateUser(ELUser elUser)throws ElException{
		String username=elUser.getUsername();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = OmUtil.getConnection();
			ps = ct.prepareStatement("select password from users where login= ? ");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()) {
				if(MD5.createPassPhrase(elUser.getPassword()).equals(rs.getString(1))){
					
				}
				else{
					updatePassword(username,  elUser.getPassword());
				}
			}else{
				addNewUser(username,  elUser.getPassword(),elUser.getRealname());
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			OmUtil.closeConnectInfo(ct, ps, rs);
		}
	}
	private void addNewUser(String username,String password,String realname)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			
			ct = OmUtil.getConnection();
			//ct.setAutoCommit(false);
			
			ps = ct.prepareStatement("insert into users(" +
					"  adresses_id , age , availible, firstname , lastlogin , lastname ,"+
					"lasttrans ,level_id , login , password , regdate , status ,title_id , starttime " +
					", updatetime , deleted ,  resethash)" +
					"values(  1 , '2010-07-17 00:00:00',  0 , '"+realname+"', '2010-07-19 19:10:02', '', " +
					" 0 , 1 , '"+username+"', '"+MD5.createPassPhrase(password)+"', '2010-07-17 15:45:01',  1 ,  1 , '2010-07-17 15:45:01', " +
					"'2010-07-19 18:47:02', 'false',  '270f854fadf1673b61f2a2053169e55c' ) ");
//			ps.setString(1, username);
			ps.executeUpdate();
//			if ("mssql".equals(SystemConfOp
//					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
//				ps = ct.prepareStatement("SELECT IDENT_CURRENT('users') AS id");
//				rs = ps.executeQuery();
//
//			} else if ("mysql".equals(SystemConfOp
//					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
//			}
			int userid = 0;
			if(rs.next()){
				userid = rs.getInt(1);
			}
			ps = ct.prepareStatement("INSERT INTO organisation_users  VALUES ("+userid+",  1 , "+userid+", '2010-07-17 15:45:01', '2010-07-17 15:45:01', 'false', '')");
			ps.executeUpdate();
//			ps = ct.prepareStatement("INSERT INTO organisation_users  VALUES ("+userid+",  1 , "+userid+", '2010-07-17 15:45:01', '2010-07-17 15:45:01', 'false', '')");
//			ps.executeUpdate();
			//ct.commit();
			//ct.setAutoCommit(true);
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			try {
				ct.rollback();
			} catch (Exception ee) {
				logger.error("回滚失败!");
			}
			throw new ElException(e);
		} finally {
			OmUtil.closeConnectInfo(ct, ps, rs);
		}
	}
	private void updatePassword(String username,String password) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			
			ct = OmUtil.getConnection();
			//ct.setAutoCommit(false);
			
			ps = ct.prepareStatement("update users set password = '"+MD5.createPassPhrase(password)+"' where login ='"+username+"' ");
			ps.executeUpdate();
			//ct.commit();
			//ct.setAutoCommit(true);
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			try {
				ct.rollback();
			} catch (Exception ee) {
				logger.error("回滚失败!");
			}
			throw new ElException(e);
		} finally {
			OmUtil.closeConnectInfo(ct, ps, rs);
		}
	}
	public boolean setModeratorHasLoginOut(int roomid, int becomemoderator)
			throws ElException {
		// TODO Auto-generated method stub
		return false;
	}
}

