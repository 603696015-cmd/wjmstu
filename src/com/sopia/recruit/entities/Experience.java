package com.sopia.recruit.entities;

import java.sql.Date;

public class Experience {
	private int id;
	private int userid;
	private int resumeid;
	private String companyname;
	private String xingzhi;
	private String guimo;
	private String hangyeleibie;
	private String bumen;
	private String zhiweileibie;
	private String zhiyemingcheng;
	private Date workstartdate;
	private Date workenddate;
	private String zhiweiyuexin;
	private String miaoshu;
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
	public int getResumeid() {
		return resumeid;
	}
	public void setResumeid(int resumeid) {
		this.resumeid = resumeid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getXingzhi() {
		return xingzhi;
	}
	public void setXingzhi(String xingzhi) {
		this.xingzhi = xingzhi;
	}
	public String getGuimo() {
		return guimo;
	}
	public void setGuimo(String guimo) {
		this.guimo = guimo;
	}
	public String getHangyeleibie() {
		return hangyeleibie;
	}
	public void setHangyeleibie(String hangyeleibie) {
		this.hangyeleibie = hangyeleibie;
	}
	public String getBumen() {
		return bumen;
	}
	public void setBumen(String bumen) {
		this.bumen = bumen;
	}
	public String getZhiweileibie() {
		return zhiweileibie;
	}
	public void setZhiweileibie(String zhiweileibie) {
		this.zhiweileibie = zhiweileibie;
	}
	public String getZhiyemingcheng() {
		return zhiyemingcheng;
	}
	public void setZhiyemingcheng(String zhiyemingcheng) {
		this.zhiyemingcheng = zhiyemingcheng;
	}
	public Date getWorkstartdate() {
		return workstartdate;
	}
	public void setWorkstartdate(Date workstartdate) {
		this.workstartdate = workstartdate;
	}
	public Date getWorkenddate() {
		return workenddate;
	}
	public void setWorkenddate(Date workenddate) {
		this.workenddate = workenddate;
	}
	public String getZhiweiyuexin() {
		return zhiweiyuexin;
	}
	public void setZhiweiyuexin(String zhiweiyuexin) {
		this.zhiweiyuexin = zhiweiyuexin;
	}
	public String getMiaoshu() {
		return miaoshu;
	}
	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}
}
