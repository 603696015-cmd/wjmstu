package com.sopia.shopping.entities;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;

public class Order {
	private int id;//订单id
	private int userid;//用户id
	private Timestamp  orderdate;//订购日期
	private Timestamp  buydate;// 支付日期
	private String  tel;//电话
	private String note;//备注
	private  float sumpeice;//总价
	
	private String username;//用户姓名
	private int status;//订单状态
	private String	shoujianren;//收件人姓名
	private ELUser  user;
	
	
	public String getShoujianren() {
		return shoujianren;
	}

	public void setShoujianren(String shoujianren) {
		this.shoujianren = shoujianren;
	}

	public ELUser getUser() {
		return user;
	}

	public void setUser(ELUser user) {
		this.user = user;
	}

	public String getStatusname(){
		if(this.status==0)return "已订购";
		if(this.status==2)return "已支付";
		if(this.status==3)return "已发货";
		else{
			return "已收货"	;
		}
		
		
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Timestamp getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Timestamp orderdate) {
		this.orderdate = orderdate;
	}
	public Timestamp getBuydate() {
		return buydate;
	}
	public void setBuydate(Timestamp buydate) {
		this.buydate = buydate;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public float getSumpeice() {
		return sumpeice;
	}
	public void setSumpeice(float sumpeice) {
		this.sumpeice = sumpeice;
	}
	
	
}
