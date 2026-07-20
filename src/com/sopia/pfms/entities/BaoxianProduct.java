package com.sopia.pfms.entities;

import java.sql.Timestamp;

import com.sopia.common.SystemConfOp;


public class BaoxianProduct {
	private int id;
	private String name;//产品名称
	private String jianjie;//产品简介
	private String chanpinjiancheng;//产品简称
	private String jieshao;//产品介绍
	private int suoshulanmu;//所属栏目
	private String key;//产品关键词
	private int chanpinbianhao;//产品编号
	private double shichangjia;//市场价
	private double huiyuanjia;//会员价
	private String fabuzhe;//产品发布者
	
	private String fabuzhesuozaidanwei;//发布者所在单位
	private Timestamp fabushijian;//发布时间
	private Timestamp xiugaishijian;//修改时间
	private String xiugaizhe;//修改者
	private String chanpintupian;//产品图片
	private int dianjishujinri;//点击数今日
	private int dianjishubenzhou;//点击数本周
	private int dianjishubenyue;//点击数本月
	private int dianjishuzongji;//点击数总计
	private String fuwurexian;//服务热线
	
	private String jutitiaokuan;//具体条款
	private String chuwaizeren;//除外责任
	private String kehugaozhishu;//客户告知书
	private String chanpinliangdian;//产品亮点
	private int userId;//用户编号
	private int shenhezhuangtai;//审核状态
	
	private Suoshulanmu lanmu ;//所属栏目对象
	private Shenhezhuangtai shenhezhuangtai_entity;//审核状态对象
	
	private ProductType ptype;//栏目树
	
	private InsuranceCategories insuranceCategories;//对应险种
	private int insuranceCategoryId;//险种ID
	
	private String logo;//保险公司logo
	private String chanpintese;
	private String zhengzhantuijian;
	
	public String getZhengzhantuijian() {
		return zhengzhantuijian;
	}

	public void setZhengzhantuijian(String zhengzhantuijian) {
		this.zhengzhantuijian = zhengzhantuijian;
	}

	public String getJianjie_() {
		return SystemConfOp.toStuffUrl(jianjie);
	}
	
	public String getJutitiaokuan_(){
		if(jutitiaokuan!=null&&(jutitiaokuan.indexOf("http://")==0||jutitiaokuan.indexOf("https://")==0))
			return jutitiaokuan;
		return  SystemConfOp.getStuffUrl()+jutitiaokuan;
	}
	
	public String getChanpintupian_(){
		if(chanpintupian!=null&&(chanpintupian.indexOf("http://")==0||chanpintupian.indexOf("https://")==0))
			return chanpintupian;
		return  SystemConfOp.getStuffUrl()+chanpintupian;
	}
	
	public String getChuwaizeren_(){
		if(chuwaizeren!=null&&(chuwaizeren.indexOf("http://")==0||chuwaizeren.indexOf("https://")==0))
			return chuwaizeren;
		return  SystemConfOp.getStuffUrl()+chuwaizeren;
	}
	
	public String getKehugaozhishu_(){
		if(kehugaozhishu!=null&&(kehugaozhishu.indexOf("http://")==0||kehugaozhishu.indexOf("https://")==0))
			return kehugaozhishu;
		return  SystemConfOp.getStuffUrl()+kehugaozhishu;
	}
	
	public String getLogo_(){
		if(logo!=null&&(logo.indexOf("http://")==0||logo.indexOf("https://")==0))
			return logo;
		return  SystemConfOp.getStuffUrl()+logo;
	}
	
	public String getChanpintese() {
		return chanpintese;
	}

	public void setChanpintese(String chanpintese) {
		this.chanpintese = chanpintese;
	}

	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public int getInsuranceCategoryId() {
		return insuranceCategoryId;
	}
	public void setInsuranceCategoryId(int insuranceCategoryId) {
		this.insuranceCategoryId = insuranceCategoryId;
	}
	public InsuranceCategories getInsuranceCategories() {
		return insuranceCategories;
	}
	public void setInsuranceCategories(InsuranceCategories insuranceCategories) {
		this.insuranceCategories = insuranceCategories;
	}
	public ProductType getPtype() {
		return ptype;
	}
	public void setPtype(ProductType ptype) {
		this.ptype = ptype;
	}
	public Shenhezhuangtai getShenhezhuangtai_entity() {
		return shenhezhuangtai_entity;
	}
	public void setShenhezhuangtai_entity(Shenhezhuangtai shenhezhuangtai_entity) {
		this.shenhezhuangtai_entity = shenhezhuangtai_entity;
	}
	public Suoshulanmu getLanmu() {
		return lanmu;
	}
	public void setLanmu(Suoshulanmu lanmu) {
		this.lanmu = lanmu;
	}
	public int getShenhezhuangtai() {
		return shenhezhuangtai;
	}
	public void setShenhezhuangtai(int shenhezhuangtai) {
		this.shenhezhuangtai = shenhezhuangtai;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJianjie() {
		return jianjie;
	}
	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}
	public String getJieshao() {
		return jieshao;
	}
	public void setJieshao(String jieshao) {
		this.jieshao = jieshao;
	}

	public int getSuoshulanmu() {
		return suoshulanmu;
	}
	public void setSuoshulanmu(int suoshulanmu) {
		this.suoshulanmu = suoshulanmu;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getChanpinbianhao() {
		return chanpinbianhao;
	}
	public void setChanpinbianhao(int chanpinbianhao) {
		this.chanpinbianhao = chanpinbianhao;
	}
	public double getShichangjia() {
		return shichangjia;
	}
	public void setShichangjia(double shichangjia) {
		this.shichangjia = shichangjia;
	}
	public double getHuiyuanjia() {
		return huiyuanjia;
	}
	public void setHuiyuanjia(double huiyuanjia) {
		this.huiyuanjia = huiyuanjia;
	}
	public String getFabuzhe() {
		return fabuzhe;
	}
	public void setFabuzhe(String fabuzhe) {
		this.fabuzhe = fabuzhe;
	}
	public String getFabuzhesuozaidanwei() {
		return fabuzhesuozaidanwei;
	}
	public void setFabuzhesuozaidanwei(String fabuzhesuozaidanwei) {
		this.fabuzhesuozaidanwei = fabuzhesuozaidanwei;
	}

	public Timestamp getFabushijian() {
		return fabushijian;
	}
	public void setFabushijian(Timestamp fabushijian) {
		this.fabushijian = fabushijian;
	}
	public Timestamp getXiugaishijian() {
		return xiugaishijian;
	}
	public void setXiugaishijian(Timestamp xiugaishijian) {
		this.xiugaishijian = xiugaishijian;
	}
	public String getXiugaizhe() {
		return xiugaizhe;
	}
	public void setXiugaizhe(String xiugaizhe) {
		this.xiugaizhe = xiugaizhe;
	}
	public String getChanpintupian() {
		return chanpintupian;
	}
	public void setChanpintupian(String chanpintupian) {
		this.chanpintupian = chanpintupian;
	}
	public int getDianjishujinri() {
		return dianjishujinri;
	}
	public void setDianjishujinri(int dianjishujinri) {
		this.dianjishujinri = dianjishujinri;
	}
	public int getDianjishubenzhou() {
		return dianjishubenzhou;
	}
	public void setDianjishubenzhou(int dianjishubenzhou) {
		this.dianjishubenzhou = dianjishubenzhou;
	}
	public int getDianjishubenyue() {
		return dianjishubenyue;
	}
	public void setDianjishubenyue(int dianjishubenyue) {
		this.dianjishubenyue = dianjishubenyue;
	}
	public int getDianjishuzongji() {
		return dianjishuzongji;
	}
	public void setDianjishuzongji(int dianjishuzongji) {
		this.dianjishuzongji = dianjishuzongji;
	}
	public String getFuwurexian() {
		return fuwurexian;
	}
	public void setFuwurexian(String fuwurexian) {
		this.fuwurexian = fuwurexian;
	}
	public String getJutitiaokuan() {
		return jutitiaokuan;
	}
	public void setJutitiaokuan(String jutitiaokuan) {
		this.jutitiaokuan = jutitiaokuan;
	}
	public String getChuwaizeren() {
		return chuwaizeren;
	}
	public void setChuwaizeren(String chuwaizeren) {
		this.chuwaizeren = chuwaizeren;
	}
	public String getKehugaozhishu() {
		return kehugaozhishu;
	}
	public void setKehugaozhishu(String kehugaozhishu) {
		this.kehugaozhishu = kehugaozhishu;
	}
	public String getChanpinliangdian() {
		return chanpinliangdian;
	}
	public void setChanpinliangdian(String chanpinliangdian) {
		this.chanpinliangdian = chanpinliangdian;
	}
	public String getChanpinjiancheng() {
		return chanpinjiancheng;
	}
	public void setChanpinjiancheng(String chanpinjiancheng) {
		this.chanpinjiancheng = chanpinjiancheng;
	}

}
