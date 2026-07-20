package com.sopia.pfms.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.entities.Area;
import com.sopia.pfms.entities.PfmsUser;

public interface IndexDao {
	
	/**
	 * 
	 * @param id
	 * @param show_some_user_note	显示简介：true为全部显示,false为部分显示
	 * @param type	部分显示下,type是"check"的时候表示截取滤掉HTML后的
	 * @return
	 * @throws ElException
	 */
	public abstract PfmsUser getUser(int id,boolean show_some_user_note) throws ElException; 
	
	public abstract int addPfmsUser(PfmsUser pfmsUser,int depid) throws ElException;
	
	public abstract void alterBaseInfo(PfmsUser pfmsUser,int id) throws ElException;
	
	public abstract void alterPassword(String newpassword,int id) throws ElException;
	
	public abstract void alterMemberProfile(PfmsUser pfmsUser,int id) throws ElException;
	
	public abstract void deleteUser(int id) throws ElException;
	
	public abstract void alterPfmsUserZhengshu(PfmsUser pfmsUser) throws ElException;
	
	public abstract List<ELUser> listUsers(ElNode dep, int subdep, ELUser eu,
			int pageNow, int pageSize ) throws ElException;
	
	public abstract int listUsersSize(ElNode dep, int subdep, ELUser eu)
	throws ElException;
	
	public abstract List<Area> areaList(String selected,String city_type) throws ElException;
	
	public abstract void alterPictures(PfmsUser pfmsUser,int userid) throws ElException;
	
	public abstract List<PfmsUser> listAllPfmsUsers(ElNode dep, int subdep) throws ElException;
	
	public List<PfmsUser> listFrontUsers(int pageNow, int pageSize) throws ElException;
	
	public void delUser(int id) throws ElException;
	
	public int getRoleId(int id) throws ElException;
	
	public void insert_into_pfmsUser(ELUser elUser) throws ElException;
	

}
