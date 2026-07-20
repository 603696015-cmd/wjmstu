package com.sopia.duman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.Mac;
public interface MacDao {
	
	public List<Mac> getAllMac(int pageNow, int pageSize) throws ElException;
	
	public int getAllMacCount()throws ElException;
	
	public void addMac(Mac mac)throws ElException;
	
	public void delMac(int id)throws ElException;
	
	public boolean isExistMac(String macaddr)throws ElException;
}
