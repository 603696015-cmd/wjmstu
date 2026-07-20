package com.sopia.duman.action;

import java.sql.Date;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.common.ElException;
import com.sopia.duman.dao.MacDao;
import com.sopia.duman.entities.Mac;
public class MacAction extends BaseAction{
	
	private List<Mac> macs;
	private MacDao macDao;
	protected int count;
	private Mac mac;
	private int id;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Mac getMac() {
		return mac;
	}


	public void setMac(Mac mac) {
		this.mac = mac;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public MacDao getMacDao() {
		return macDao;
	}


	public void setMacDao(MacDao macDao) {
		this.macDao = macDao;
	}


	public List<Mac> getMacs() {
		return macs;
	}


	public void setMacs(List<Mac> macs) {
		this.macs = macs;
	}


	public String mac_list()throws ElException{
		macs = macDao.getAllMac(getPageNow(), getPageSize());
		count = macDao.getAllMacCount();
		return "mac_list";
		
	}
	public String mac_addInit() throws ElException{
		return "mac_addInit";
	}
	
	public String mac_add() throws ElException{
		macDao.addMac(mac);
		return "mac_list";
	}
	
	public String mac_del() throws ElException{
		macDao.delMac(id);
		return "mac_del";
	}
}
