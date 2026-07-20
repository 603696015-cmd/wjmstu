package com.sopia.schedule.entities;

import java.sql.Timestamp;

/**
 * 物品中心
 * @author Administrator
 *
 */
public class Wupin {
	
	private String wupinname;//物品名称
	private String cangku;//仓库
	private Timestamp time;//时间
	private int xiaoshoushuliang;//销售数量
	private double xiaoshouzongjia;//销售总价
	private int xiaoshoutuihuoshuliang;//销售退货数量
	private double xiaoshoutuihuozongjia;//销售退货总价
	private int caigoushuliang;//采购数量
	private double caigouzongjia;//采购总价
	private int caigoutuihuoshuliang;//采购退货数量
	private double caigoutuihuozongjia;//采购退货总价
	private int chukushuliang;//出库数量
	private int rukushuliang;//入库数量
	private int cunliang;//存量
	private double shichangjia;//市场价
	private double zongjia;//总价
	
	public Wupin(){
		
	}
	public Wupin(String wupinname){
		this.wupinname = wupinname;
	}
	public String getWupinname() {
		return wupinname;
	}
	public void setWupinname(String wupinname) {
		this.wupinname = wupinname;
	}
	public String getCangku() {
		return cangku;
	}
	public void setCangku(String cangku) {
		this.cangku = cangku;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getXiaoshoushuliang() {
		return xiaoshoushuliang;
	}
	public void setXiaoshoushuliang(int xiaoshoushuliang) {
		this.xiaoshoushuliang = xiaoshoushuliang;
	}
	public double getXiaoshouzongjia() {
		return xiaoshouzongjia;
	}
	public void setXiaoshouzongjia(double xiaoshouzongjia) {
		this.xiaoshouzongjia = xiaoshouzongjia;
	}
	public int getXiaoshoutuihuoshuliang() {
		return xiaoshoutuihuoshuliang;
	}
	public void setXiaoshoutuihuoshuliang(int xiaoshoutuihuoshuliang) {
		this.xiaoshoutuihuoshuliang = xiaoshoutuihuoshuliang;
	}
	public double getXiaoshoutuihuozongjia() {
		return xiaoshoutuihuozongjia;
	}
	public void setXiaoshoutuihuozongjia(double xiaoshoutuihuozongjia) {
		this.xiaoshoutuihuozongjia = xiaoshoutuihuozongjia;
	}
	public int getCaigoushuliang() {
		return caigoushuliang;
	}
	public void setCaigoushuliang(int caigoushuliang) {
		this.caigoushuliang = caigoushuliang;
	}
	public double getCaigouzongjia() {
		return caigouzongjia;
	}
	public void setCaigouzongjia(double caigouzongjia) {
		this.caigouzongjia = caigouzongjia;
	}
	public int getCaigoutuihuoshuliang() {
		return caigoutuihuoshuliang;
	}
	public void setCaigoutuihuoshuliang(int caigoutuihuoshuliang) {
		this.caigoutuihuoshuliang = caigoutuihuoshuliang;
	}
	public double getCaigoutuihuozongjia() {
		return caigoutuihuozongjia;
	}
	public void setCaigoutuihuozongjia(double caigoutuihuozongjia) {
		this.caigoutuihuozongjia = caigoutuihuozongjia;
	}
	public int getChukushuliang() {
		return chukushuliang;
	}
	public void setChukushuliang(int chukushuliang) {
		this.chukushuliang = chukushuliang;
	}
	public int getRukushuliang() {
		return rukushuliang;
	}
	public void setRukushuliang(int rukushuliang) {
		this.rukushuliang = rukushuliang;
	}
	public int getCunliang() {
		return cunliang;
	}
	public void setCunliang(int cunliang) {
		this.cunliang = cunliang;
	}
	public double getShichangjia() {
		return shichangjia;
	}
	public void setShichangjia(double shichangjia) {
		this.shichangjia = shichangjia;
	}
	public double getZongjia() {
		return zongjia;
	}
	public void setZongjia(double zongjia) {
		this.zongjia = zongjia;
	}
	

}
