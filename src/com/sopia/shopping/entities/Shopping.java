package com.sopia.shopping.entities;

import com.sopia.duman.entities.ELUser;

/**
 * 消费信息
 * @author Administrator
 *
 */
public class Shopping {
	
	private   float   price;//已消费
	private	  float   allprice;//总金额
	private   ELUser  user;
	private   int	  count;//总人数
	private   float   balance;//总余额
	
	
	
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getAllprice() {
		return allprice;
	}
	public void setAllprice(float allprice) {
		this.allprice = allprice;
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	

}
