package com.sopia.shopping.entities;

import java.sql.Timestamp;

/**
 * 购物车
 * @author Administrator
 *
 */
public class ShoppingCart {
	private int id;//编号
	private int userid;//用户ID
	private int Commodityid;//商品ID
	private int Commoditytype;//商品类型
	private int count;//购物车商品数
	private Timestamp addDate;//添加日期
	
	public Timestamp getAddDate() {
		return addDate;
	}
	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
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
	public int getCommodityid() {
		return Commodityid;
	}
	public void setCommodityid(int commodityid) {
		Commodityid = commodityid;
	}
	public int getCommoditytype() {
		return Commoditytype;
	}
	public void setCommoditytype(int commoditytype) {
		Commoditytype = commoditytype;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
