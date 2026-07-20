package com.sopia.openmeetings;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;

public interface OmDao {
	public void addOmRoom(Rooms rooms) throws ElException;
	public void alterOmRoom(Rooms rooms) throws ElException;
	public List<Rooms> listOmRoom(int roomtypes_id) throws ElException;

	public Rooms getOmRoom(int rooms_id) throws ElException;

	public List<ELUser> getRoomsUser(int roomid) throws ElException;

	public boolean checkZUserInTr(int userid, int roomid) throws ElException;

	public void assign2users(int userid, int roomid, int type)
			throws ElException;

	public void unassign2users(int userid, int roomid) throws ElException;
	public String  getUserByLogin(String username) throws ElException;

	public List<Rooms>  listMyRooms(int userid, int typeid) throws ElException;
	
	public boolean moderatorHasLogin(int roomid) throws ElException;
	/**
	 * 控制讲师是否进入
	 * @param roomid
	 * @param becomemoderator （1进入，0未进入）
	 * @return
	 * @throws ElException
	 */ 
	public boolean setModeratorHasLoginOut(int roomid, int becomemoderator) throws ElException;
}
