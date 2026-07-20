package com.sopia.pfms.entities;

import java.sql.Timestamp;

import com.sopia.common.SystemConfOp;

public class SheBei {
	
	private int id;
	private int userId;
	private String name;
	private String fabuzhe;
	private String fabuzhesuozaidanwei;
	private String shebeileixing;	//设备类型
	private String shebeilleibie;
	private int toubaozhuangtai;//投保状态
	private int shenhezhuangtai;//审核状态
	private Timestamp fabushijian;
	private Timestamp xiugaishijian;
	
	private String xinghao;
	private String shebeidizhi;
	private String postalcode;
	private String shebeiusezhenghao;
	private int shebeiuseyouxiaoqi;
	private String shebeiusequyu;
	private String shebeiuseleixing;
	private String shebeidengji;
	private String shengchanchangjia;
	private Timestamp chuchangriqi;
	private int jianyanqixian;
	private Timestamp jianyanriqi;
	private String shebeijianjie;
	
	private String shebeitese;
	private String beizhu;
	private Timestamp kaishishijian;
	private Timestamp jieshushijian;
	private int dengjibianhao;
	
	private Toubaozhuangtai toubaozhuangtai_entity;
	private Shenhezhuangtai shenhezhuangtai_entity;
	
	public String getShebeijianjie_() {
		return SystemConfOp.toStuffUrl(shebeijianjie);
	}
	
	public int getDengjibianhao() {
		return dengjibianhao;
	}
	public void setDengjibianhao(int dengjibianhao) {
		this.dengjibianhao = dengjibianhao;
	}
	public Timestamp getKaishishijian() {
		return kaishishijian;
	}
	public void setKaishishijian(Timestamp kaishishijian) {
		this.kaishishijian = kaishishijian;
	}
	public Timestamp getJieshushijian() {
		return jieshushijian;
	}
	public void setJieshushijian(Timestamp jieshushijian) {
		this.jieshushijian = jieshushijian;
	}
	public Timestamp getFabushijian() {
		return fabushijian;
	}
	public void setFabushijian(Timestamp fabushijian) {
		this.fabushijian = fabushijian;
	}
	public String getFabuzhesuozaidanwei() {
		return fabuzhesuozaidanwei;
	}
	public void setFabuzhesuozaidanwei(String fabuzhesuozaidanwei) {
		this.fabuzhesuozaidanwei = fabuzhesuozaidanwei;
	}
	public Timestamp getXiugaishijian() {
		return xiugaishijian;
	}
	public void setXiugaishijian(Timestamp xiugaishijian) {
		this.xiugaishijian = xiugaishijian;
	}
	public Toubaozhuangtai getToubaozhuangtai_entity() {
		return toubaozhuangtai_entity;
	}
	public void setToubaozhuangtai_entity(Toubaozhuangtai toubaozhuangtai_entity) {
		this.toubaozhuangtai_entity = toubaozhuangtai_entity;
	}
	public Shenhezhuangtai getShenhezhuangtai_entity() {
		return shenhezhuangtai_entity;
	}
	public void setShenhezhuangtai_entity(Shenhezhuangtai shenhezhuangtai_entity) {
		this.shenhezhuangtai_entity = shenhezhuangtai_entity;
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
	public String getFabuzhe() {
		return fabuzhe;
	}
	public void setFabuzhe(String fabuzhe) {
		this.fabuzhe = fabuzhe;
	}
	public String getShebeilleibie() {
		return shebeilleibie;
	}
	public void setShebeilleibie(String shebeilleibie) {
		this.shebeilleibie = shebeilleibie;
	}
	
	public String getShebeileixing() {
		return shebeileixing;
	}
	public void setShebeileixing(String shebeileixing) {
		this.shebeileixing = shebeileixing;
	}
	public int getToubaozhuangtai() {
		return toubaozhuangtai;
	}
	public void setToubaozhuangtai(int toubaozhuangtai) {
		this.toubaozhuangtai = toubaozhuangtai;
	}
	public int getShenhezhuangtai() {
		return shenhezhuangtai;
	}
	public void setShenhezhuangtai(int shenhezhuangtai) {
		this.shenhezhuangtai = shenhezhuangtai;
	}
	public String getXinghao() {
		return xinghao;
	}
	public void setXinghao(String xinghao) {
		this.xinghao = xinghao;
	}
	public String getShebeidizhi() {
		return shebeidizhi;
	}
	public void setShebeidizhi(String shebeidizhi) {
		this.shebeidizhi = shebeidizhi;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getShebeiusezhenghao() {
		return shebeiusezhenghao;
	}
	public void setShebeiusezhenghao(String shebeiusezhenghao) {
		this.shebeiusezhenghao = shebeiusezhenghao;
	}

	public int getShebeiuseyouxiaoqi() {
		return shebeiuseyouxiaoqi;
	}
	public void setShebeiuseyouxiaoqi(int shebeiuseyouxiaoqi) {
		this.shebeiuseyouxiaoqi = shebeiuseyouxiaoqi;
	}
	public String getShebeiusequyu() {
		return shebeiusequyu;
	}
	public void setShebeiusequyu(String shebeiusequyu) {
		this.shebeiusequyu = shebeiusequyu;
	}
	public String getShebeiuseleixing() {
		return shebeiuseleixing;
	}
	public void setShebeiuseleixing(String shebeiuseleixing) {
		this.shebeiuseleixing = shebeiuseleixing;
	}
	public String getShebeidengji() {
		return shebeidengji;
	}
	public void setShebeidengji(String shebeidengji) {
		this.shebeidengji = shebeidengji;
	}
	public String getShengchanchangjia() {
		return shengchanchangjia;
	}
	public void setShengchanchangjia(String shengchanchangjia) {
		this.shengchanchangjia = shengchanchangjia;
	}
	
	
	public int getJianyanqixian() {
		return jianyanqixian;
	}
	public void setJianyanqixian(int jianyanqixian) {
		this.jianyanqixian = jianyanqixian;
	}
	public Timestamp getChuchangriqi() {
		return chuchangriqi;
	}
	public void setChuchangriqi(Timestamp chuchangriqi) {
		this.chuchangriqi = chuchangriqi;
	}
	public Timestamp getJianyanriqi() {
		return jianyanriqi;
	}
	public void setJianyanriqi(Timestamp jianyanriqi) {
		this.jianyanriqi = jianyanriqi;
	}
	public String getShebeijianjie() {
		return shebeijianjie;
	}
	public void setShebeijianjie(String shebeijianjie) {
		this.shebeijianjie = shebeijianjie;
	}
	public String getShebeitese() {
		return shebeitese;
	}
	public void setShebeitese(String shebeitese) {
		this.shebeitese = shebeitese;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	
	

}
