package com.sopia.balance.entites;

import java.sql.Timestamp;
import java.util.Date;

import com.sopia.duman.entities.ELUser;

public class RechargeInfo {
	private  int  id;
	private  int  type;
	private  ELUser  user;//增资操作者
	private  String  username;//增资操作者姓名
	private  Timestamp Rechargedate;
	private  float  Addbalance;
	private  ELUser Rechargeuserid;//增资用户
	private  String  reusername;//增资用户姓名
	private  String  reuserid;//增资用户账号
	
	public String getReuserid() {
		return reuserid;
	}
	public void setReuserid(String reuserid) {
		this.reuserid = reuserid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getReusername() {
		return reusername;
	}
	public void setReusername(String reusername) {
		this.reusername = reusername;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public Timestamp getRechargedate() {
		return Rechargedate;
	}
	public void setRechargedate(Timestamp rechargedate) {
		Rechargedate = rechargedate;
	}
	public float getAddbalance() {
		return Addbalance;
	}
	public void setAddbalance(float addbalance) {
		Addbalance = addbalance;
	}
	public ELUser getRechargeuserid() {
		return Rechargeuserid;
	}
	public void setRechargeuserid(ELUser rechargeuserid) {
		Rechargeuserid = rechargeuserid;
	}
	
}
