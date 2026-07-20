package com.sopia.pfms.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.pfms.entities.DamageMember;

public interface DamageMemberDao {
	
	public abstract void addDamageMember(DamageMember damageMember) throws ElException;
	
	public abstract List<DamageMember> damageMemberList(Integer start,Integer size,DamageMember damageMember,Timestamp starttime,Timestamp endtime) throws ElException;
	public abstract int getCount(DamageMember damageMember,Timestamp starttime,Timestamp endtime) throws ElException;
	
	public abstract DamageMember showDamageMemberView(int id) throws ElException;
	
	public abstract void updateDamageMember(DamageMember damageMember) throws ElException;
	
	public abstract void deleteDamageMember(int id) throws ElException;
	

}
