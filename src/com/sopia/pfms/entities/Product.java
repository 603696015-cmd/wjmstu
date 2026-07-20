package com.sopia.pfms.entities;
import java.sql.Timestamp;

import com.sopia.common.SystemConfOp;

public class Product {
	private int id;//编号
	private String name;//产品名称
	private String jianjie;//简介
	private String jieshao;//介绍
	private int suoshulanmu;//所属栏目
	private String key;//关键字
	private String chanpinbianhao;//商品编号
	private double shichangjia;//市场价
	private double huiyuanjia;//会员价
	private String fabuzhe;//发布者
	private String fabuzhesuozaidanwei;//发布者所在单位
	private Timestamp fabushijian;//发布时间
	private Timestamp xiugaishijian;//修改时间
	private String xiugaizhe;//修改者
	private String chanpintupian;//产品图片
	private int shuliang;//数量
	private int baojingshu;//报工数
	private int dianjishujinri;//今日点击数
	private int dianjishubenzhou;//本周点击数
	private int dianjishubenyue;//本月点击数
	private int dianjishuzongji;//总计点击数
	private String shangpinxinghao;//商品型号
	private String shangpinguige;//商品规格
	private String shengchanshang;//生产商
	private String shangpinshangbiao;//商品商标
	private int userId;//用户id
	private int shenhezhuangtai;//审核状态  (已创建、审核通过、审核未通过)  对应 (1、2、3)
	
	private Suoshulanmu lanmu = new Suoshulanmu();//所属栏目对象
	private Shenhezhuangtai shenhezhuangtai_entity;//审核对象
	
	private ProductType ptype;//栏目树
	
	
	private String dianneituijian;//店内推荐		(普通推荐、店内推荐)默认为店内推荐
	private String zhengzhantuijian;//整站推荐	(普通、推荐、重点、热门、幻灯)
	
	private PfmsUser pfmsUser;//会员信息
	private String productCompanyName;//产品公司名称
	
	public String getJianjie_() {
		return SystemConfOp.toStuffUrl(jianjie);
	}
	public String getChanpintupian_(){
		if(chanpintupian!=null&&(chanpintupian.indexOf("http://")==0||chanpintupian.indexOf("https://")==0))
			return chanpintupian;
		return  SystemConfOp.getStuffUrl()+chanpintupian;
	}
	
	public String getProductCompanyName() {
		return productCompanyName;
	}
	public void setProductCompanyName(String productCompanyName) {
		this.productCompanyName = productCompanyName;
	}
	public PfmsUser getPfmsUser() {
		return pfmsUser;
	}
	public void setPfmsUser(PfmsUser pfmsUser) {
		this.pfmsUser = pfmsUser;
	}
	public String getDianneituijian() {
		return dianneituijian;
	}
	public void setDianneituijian(String dianneituijian) {
		this.dianneituijian = dianneituijian;
	}
	public String getZhengzhantuijian() {
		return zhengzhantuijian;
	}
	public void setZhengzhantuijian(String zhengzhantuijian) {
		this.zhengzhantuijian = zhengzhantuijian;
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

	public String getChanpinbianhao() {
		return chanpinbianhao;
	}
	public void setChanpinbianhao(String chanpinbianhao) {
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
	public int getShuliang() {
		return shuliang;
	}
	public void setShuliang(int shuliang) {
		this.shuliang = shuliang;
	}
	public int getBaojingshu() {
		return baojingshu;
	}
	public void setBaojingshu(int baojingshu) {
		this.baojingshu = baojingshu;
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
	public String getShangpinxinghao() {
		return shangpinxinghao;
	}
	public void setShangpinxinghao(String shangpinxinghao) {
		this.shangpinxinghao = shangpinxinghao;
	}
	public String getShangpinguige() {
		return shangpinguige;
	}
	public void setShangpinguige(String shangpinguige) {
		this.shangpinguige = shangpinguige;
	}
	public String getShengchanshang() {
		return shengchanshang;
	}
	public void setShengchanshang(String shengchanshang) {
		this.shengchanshang = shengchanshang;
	}
	public String getShangpinshangbiao() {
		return shangpinshangbiao;
	}
	public void setShangpinshangbiao(String shangpinshangbiao) {
		this.shangpinshangbiao = shangpinshangbiao;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	

	
	
	
	

}
