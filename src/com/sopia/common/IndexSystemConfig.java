package com.sopia.common;

public class IndexSystemConfig {
	
	//首页配置
	private boolean show_tongzhigonggao;//通知公告
	private boolean show_daibanshiwu;//待办事务
	private boolean show_gongzuojihua;//工作计划
	private boolean show_gongzuorizhi;//工作日志
	private boolean show_richenganpai;//日程安排
	private boolean show_gerenkaoqin;//个人考勤
	private boolean show_gerenweishen;//个人未审
	private boolean show_gerendaishen;//个人待审
	private boolean show_myallcourses;//我的全部课程
	private boolean show_myexams;//我的非购买考试
	private boolean show_mybuyrooms;//购买的考场
	private boolean show_mytrainingcourses;//我的培训班
	
	private int tongzhigonggao_length;
	private int daibanshiwu_length;
	private int gongzuojihua_length;
	private int gongzuorizhi_length;
	private int richenganpai_length;
	private int myallcourses_length;
	private int myexams_length;
	private int mybuyrooms_length;
	private int mytrainingcourses_length;
//	private int gerenweishen_length;
//	private int gerendaishen_length;
	
	
	public boolean isShow_tongzhigonggao() {
		return show_tongzhigonggao;
	}
	public void setShow_tongzhigonggao(boolean show_tongzhigonggao) {
		this.show_tongzhigonggao = show_tongzhigonggao;
	}
	public boolean isShow_daibanshiwu() {
		return show_daibanshiwu;
	}
	public void setShow_daibanshiwu(boolean show_daibanshiwu) {
		this.show_daibanshiwu = show_daibanshiwu;
	}
	public boolean isShow_gongzuojihua() {
		return show_gongzuojihua;
	}
	public void setShow_gongzuojihua(boolean show_gongzuojihua) {
		this.show_gongzuojihua = show_gongzuojihua;
	}
	public boolean isShow_gongzuorizhi() {
		return show_gongzuorizhi;
	}
	public void setShow_gongzuorizhi(boolean show_gongzuorizhi) {
		this.show_gongzuorizhi = show_gongzuorizhi;
	}
	public boolean isShow_richenganpai() {
		return show_richenganpai;
	}
	public void setShow_richenganpai(boolean show_richenganpai) {
		this.show_richenganpai = show_richenganpai;
	}
	public boolean isShow_gerenkaoqin() {
		return show_gerenkaoqin;
	}
	public void setShow_gerenkaoqin(boolean show_gerenkaoqin) {
		this.show_gerenkaoqin = show_gerenkaoqin;
	}
	public boolean isShow_gerenweishen() {
		return show_gerenweishen;
	}
	public void setShow_gerenweishen(boolean show_gerenweishen) {
		this.show_gerenweishen = show_gerenweishen;
	}
	public boolean isShow_gerendaishen() {
		return show_gerendaishen;
	}
	public void setShow_gerendaishen(boolean show_gerendaishen) {
		this.show_gerendaishen = show_gerendaishen;
	}
	public int getTongzhigonggao_length() {
		return tongzhigonggao_length;
	}
	public void setTongzhigonggao_length(int tongzhigonggao_length) {
		this.tongzhigonggao_length = tongzhigonggao_length;
	}
	public int getDaibanshiwu_length() {
		return daibanshiwu_length;
	}
	public void setDaibanshiwu_length(int daibanshiwu_length) {
		this.daibanshiwu_length = daibanshiwu_length;
	}
	public int getGongzuojihua_length() {
		return gongzuojihua_length;
	}
	public void setGongzuojihua_length(int gongzuojihua_length) {
		this.gongzuojihua_length = gongzuojihua_length;
	}
	public int getGongzuorizhi_length() {
		return gongzuorizhi_length;
	}
	public void setGongzuorizhi_length(int gongzuorizhi_length) {
		this.gongzuorizhi_length = gongzuorizhi_length;
	}
	public int getRichenganpai_length() {
		return richenganpai_length;
	}
	public void setRichenganpai_length(int richenganpai_length) {
		this.richenganpai_length = richenganpai_length;
	}
//	public int getGerenweishen_length() {
//		return gerenweishen_length;
//	}
//	public void setGerenweishen_length(int gerenweishen_length) {
//		this.gerenweishen_length = gerenweishen_length;
//	}
//	public int getGerendaishen_length() {
//		return gerendaishen_length;
//	}
//	public void setGerendaishen_length(int gerendaishen_length) {
//		this.gerendaishen_length = gerendaishen_length;
//	}
	public boolean isShow_myallcourses() {
		return show_myallcourses;
	}
	public void setShow_myallcourses(boolean show_myallcourses) {
		this.show_myallcourses = show_myallcourses;
	}
	public boolean isShow_myexams() {
		return show_myexams;
	}
	public void setShow_myexams(boolean show_myexams) {
		this.show_myexams = show_myexams;
	}
	public int getMyexams_length() {
		return myexams_length;
	}
	public void setMyexams_length(int myexams_length) {
		this.myexams_length = myexams_length;
	}
	public boolean isShow_mytrainingcourses() {
		return show_mytrainingcourses;
	}
	public void setShow_mytrainingcourses(boolean show_mytrainingcourses) {
		this.show_mytrainingcourses = show_mytrainingcourses;
	}
	public int getMytrainingcourses_length() {
		return mytrainingcourses_length;
	}
	public void setMytrainingcourses_length(int mytrainingcourses_length) {
		this.mytrainingcourses_length = mytrainingcourses_length;
	}
	public int getMyallcourses_length() {
		return myallcourses_length;
	}
	public void setMyallcourses_length(int myallcourses_length) {
		this.myallcourses_length = myallcourses_length;
	}
	public boolean isShow_mybuyrooms() {
		return show_mybuyrooms;
	}
	public void setShow_mybuyrooms(boolean show_mybuyrooms) {
		this.show_mybuyrooms = show_mybuyrooms;
	}
	public int getMybuyrooms_length() {
		return mybuyrooms_length;
	}
	public void setMybuyrooms_length(int mybuyrooms_length) {
		this.mybuyrooms_length = mybuyrooms_length;
	}
	
	
	

}
