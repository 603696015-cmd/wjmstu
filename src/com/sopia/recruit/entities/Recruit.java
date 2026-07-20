package com.sopia.recruit.entities;

import java.sql.Date;

import com.sopia.duman.entities.ELUser;

public class Recruit {
	private int id;
	private ELUser elUser;
	private String title;//标题
	private String content;//内容
	private String xingzhi;//期望工作性质
	private String didian;//期望工作地点
	private String zhiye;//期望从事职业
	private String hangye;//期望从事行业
	private String yuexin;//期望月薪
	private int status;//现工作状态
	private String hunyinzhuangkuang;//婚姻状况
	private String haiwaigongzuojingli;//海外工作经历
	private String hukou;//户口所在地
	private String juzhuchengshi;//居住城市
	private String youbian;//邮编
	private String lianxifangshi;//联系方式
	private String gerenzhuye;//个人主页
	private String biaoti;//标题
	private String neirong;//内容
	private Date startdate;//开始时间
	private Date enddate;//结束时间
	private String school;//学校
	private String xueli;//学历
	
	private Language language;//外语能力
	private Experience experience;//项目经验
	
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public Experience getExperience() {
		return experience;
	}
	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getXueli() {
		return xueli;
	}
	public void setXueli(String xueli) {
		this.xueli = xueli;
	}
	public String getBiaoti() {
		return biaoti;
	}
	public void setBiaoti(String biaoti) {
		this.biaoti = biaoti;
	}
	public String getNeirong() {
		return neirong;
	}
	public void setNeirong(String neirong) {
		this.neirong = neirong;
	}
	public String getGerenzhuye() {
		return gerenzhuye;
	}
	public void setGerenzhuye(String gerenzhuye) {
		this.gerenzhuye = gerenzhuye;
	}
	public String getLianxifangshi() {
		return lianxifangshi;
	}
	public void setLianxifangshi(String lianxifangshi) {
		this.lianxifangshi = lianxifangshi;
	}
	public String getYoubian() {
		return youbian;
	}
	public void setYoubian(String youbian) {
		this.youbian = youbian;
	}
	public String getJuzhuchengshi() {
		return juzhuchengshi;
	}
	public void setJuzhuchengshi(String juzhuchengshi) {
		this.juzhuchengshi = juzhuchengshi;
	}
	public String getHukou() {
		return hukou;
	}
	public void setHukou(String hukou) {
		this.hukou = hukou;
	}
	public String getHaiwaigongzuojingli() {
		return haiwaigongzuojingli;
	}
	public void setHaiwaigongzuojingli(String haiwaigongzuojingli) {
		this.haiwaigongzuojingli = haiwaigongzuojingli;
	}
	public String getHunyinzhuangkuang() {
		return hunyinzhuangkuang;
	}
	public void setHunyinzhuangkuang(String hunyinzhuangkuang) {
		this.hunyinzhuangkuang = hunyinzhuangkuang;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getXingzhi() {
		return xingzhi;
	}
	public void setXingzhi(String xingzhi) {
		this.xingzhi = xingzhi;
	}
	public String getDidian() {
		return didian;
	}
	public void setDidian(String didian) {
		this.didian = didian;
	}
	public String getZhiye() {
		return zhiye;
	}
	public void setZhiye(String zhiye) {
		this.zhiye = zhiye;
	}
	public String getHangye() {
		return hangye;
	}
	public void setHangye(String hangye) {
		this.hangye = hangye;
	}
	public String getYuexin() {
		return yuexin;
	}
	public void setYuexin(String yuexin) {
		this.yuexin = yuexin;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
