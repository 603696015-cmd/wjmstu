package com.sopia.pfms.entities;

import java.sql.Timestamp;

import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.ELUser;

public class PfmsUser {
	private int id;
	private int userId;
	private String respName;
	private String address;
	private String mobile;
	private String fex;
	private String email;
	private String note;//会员简介
	private ELUser user;
	
	private String yingyezhizhao;
	private String shuiwudengjizheng;
	private String zuzhijigoudaimazheng;
	private String farenshenfenzheng;
	private String zizhidengjizhengshu;
	private String xinyongdengjipingguzhengshu;
	private String qitazhengshu;
	private String huiyuanleixing;//会员类型
	
	
	private int is_qiye_huiyuan;//是否是企业会员
	private String huiyuandanwei;//会员单位
	
	private String province_city_county;//省市县
	private String dianpuName;//店铺名称
	
	private String banner;//BANNER图片
	private String logo;//店铺logo图片
	private String dianpujianjietupian;//店铺简介图片
	
	private String head;//会员头像
	private Timestamp createtime;//创建时间
	private Timestamp altertime;//修改时间
	
	private String tuijian;//推荐类型
	
	
	public String getTuijian() {
		return tuijian;
	}

	public void setTuijian(String tuijian) {
		this.tuijian = tuijian;
	}

	public Timestamp getAltertime() {
		return altertime;
	}

	public void setAltertime(Timestamp altertime) {
		this.altertime = altertime;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getBanner_(){
		if(banner!=null&&(banner.indexOf("http://")==0||banner.indexOf("https://")==0))
			return banner;
		return  SystemConfOp.getStuffUrl()+banner;
	}
	
	public String getLogo_(){
		if(logo!=null&&(logo.indexOf("http://")==0||logo.indexOf("https://")==0))
			return logo;
		return  SystemConfOp.getStuffUrl()+logo;
	}
	
	public String getDianpujianjietupian_(){
		if(dianpujianjietupian!=null&&(dianpujianjietupian.indexOf("http://")==0||dianpujianjietupian.indexOf("https://")==0))
			return dianpujianjietupian;
		return  SystemConfOp.getStuffUrl()+dianpujianjietupian;
	}
	
	public String getHead_(){
		if(head!=null&&(head.indexOf("http://")==0||head.indexOf("https://")==0))
			return head;
		return  SystemConfOp.getStuffUrl()+head;
	}
	
	


	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getDianpujianjietupian() {
		return dianpujianjietupian;
	}
	public void setDianpujianjietupian(String dianpujianjietupian) {
		this.dianpujianjietupian = dianpujianjietupian;
	}
	public String getDianpuName() {
		return dianpuName;
	}
	public void setDianpuName(String dianpuName) {
		this.dianpuName = dianpuName;
	}
	public String getProvince_city_county() {
		return province_city_county;
	}
	public void setProvince_city_county(String province_city_county) {
		this.province_city_county = province_city_county;
	}
	public String getHuiyuandanwei() {
		return huiyuandanwei;
	}
	public void setHuiyuandanwei(String huiyuandanwei) {
		this.huiyuandanwei = huiyuandanwei;
	}
	public int getIs_qiye_huiyuan() {
		return is_qiye_huiyuan;
	}
	public void setIs_qiye_huiyuan(int is_qiye_huiyuan) {
		this.is_qiye_huiyuan = is_qiye_huiyuan;
	}
	public String getHuiyuanleixing() {
		return huiyuanleixing;
	}
	public void setHuiyuanleixing(String huiyuanleixing) {
		this.huiyuanleixing = huiyuanleixing;
	}
	public String getYingyezhizhao() {
		return yingyezhizhao;
	}
	public void setYingyezhizhao(String yingyezhizhao) {
		this.yingyezhizhao = yingyezhizhao;
	}
	public String getShuiwudengjizheng() {
		return shuiwudengjizheng;
	}
	public void setShuiwudengjizheng(String shuiwudengjizheng) {
		this.shuiwudengjizheng = shuiwudengjizheng;
	}
	public String getZuzhijigoudaimazheng() {
		return zuzhijigoudaimazheng;
	}
	public void setZuzhijigoudaimazheng(String zuzhijigoudaimazheng) {
		this.zuzhijigoudaimazheng = zuzhijigoudaimazheng;
	}
	public String getFarenshenfenzheng() {
		return farenshenfenzheng;
	}
	public void setFarenshenfenzheng(String farenshenfenzheng) {
		this.farenshenfenzheng = farenshenfenzheng;
	}
	public String getZizhidengjizhengshu() {
		return zizhidengjizhengshu;
	}
	public void setZizhidengjizhengshu(String zizhidengjizhengshu) {
		this.zizhidengjizhengshu = zizhidengjizhengshu;
	}
	public String getXinyongdengjipingguzhengshu() {
		return xinyongdengjipingguzhengshu;
	}
	public void setXinyongdengjipingguzhengshu(String xinyongdengjipingguzhengshu) {
		this.xinyongdengjipingguzhengshu = xinyongdengjipingguzhengshu;
	}
	public String getQitazhengshu() {
		return qitazhengshu;
	}
	public void setQitazhengshu(String qitazhengshu) {
		this.qitazhengshu = qitazhengshu;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRespName() {
		return respName;
	}
	public void setRespName(String respName) {
		this.respName = respName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFex() {
		return fex;
	}
	public void setFex(String fex) {
		this.fex = fex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}


}
