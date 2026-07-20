package com.sopia.shopping.entities;

import java.sql.Timestamp;

/**
 * 商品类
 * @author Administrator
 *
 */
public class Commodity {
	private  int id;//货物序列
	private  int userid;//用户id
	private  int commodityid;//商品id
	private  int commoditytype;//商品类型
	private  Timestamp adddate;//添加日期
	private  int count;//数量
	private  float oldp;//原价
	private  float nowp;//现价
	private  String commodityName;//商品名字
	private  float allp;//总价 
	
	
	public String getCommoditytypeName() { 
		if(this.commoditytype==1)return "课程";
		else if(this.commoditytype==2)return "培训班";
		else if(this.commoditytype==3)return "图书";
		else if(this.commoditytype==5)return "考场";
		else return "商品";
	
	}
	
	public float getAllp() {
		return allp;
	}
	public void setAllp(float allp) {
		this.allp = allp;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
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
		return commodityid;
	}
	public void setCommodityid(int commodityid) {
		this.commodityid = commodityid;
	}
	public int getCommoditytype() {
		return commoditytype;
	}
	public void setCommoditytype(int commoditytype) {
		this.commoditytype = commoditytype;
	}
	public Timestamp getAdddate() {
		return adddate;
	}
	public void setAdddate(Timestamp adddate) {
		this.adddate = adddate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public float getOldp() {
		return oldp;
	}
	public void setOldp(float oldp) {
		this.oldp = oldp;
	}
	public float getNowp() {
		return nowp;
	}
	public void setNowp(float nowp) {
		this.nowp = nowp;
	}
	
	
	

}
