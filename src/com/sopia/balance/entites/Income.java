package com.sopia.balance.entites;

import java.sql.Timestamp;

/**
 * 收支
 * @author Administrator
 *
 */
public class Income {

	
	private int  		id;//收支的id
	private Timestamp 	date;//收支时间
	private float		balance;//收支金额
	private int			typeflag;//类型id；收入还是支出1 收入 2 支出
	private	int			type;//具体类型  1充值 2 余额转移 3 手工增资 4 订单
	
	public	 String   getTypename(){
		
		if(this.type==1) return  "充值";
		if(this.type==2) return  "余额转移";
		if(this.type==3) return  "手工增资";
		if(this.type==4) return  "订单支付";
		else return  "其他";
		
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public  String  	getTypeflagname(){
		
		if(this.typeflag == 1 ) return  "收入";
		else if(this.typeflag == 2)return  "支出";
		else return  "不明";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public int getTypeflag() {
		return typeflag;
	}
	public void setTypeflag(int typeflag) {
		this.typeflag = typeflag;
	}
	
	
	
	

}
