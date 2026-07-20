package com.sopia.aqy.entities;

import java.sql.Date;

public class TrainingStatus {
	
	private int id;
	private  String realname;
	private  String sex;
	private  String xueli;
	private  String shenfenzhenghao;
	private  String mobliephone;
	private  float score;
	private  int certificateno;
	private  Date examdate;
	private  String isregister;
	private  String ispaymoney;
	private  String iscertificate;
	private  Date certificatestart;
	private  Date certificateend;
	private  String depname;
	private  int valid;
	private  int state;
	
	private int nocertificateno;//无证人数
	private int haspaymoney;//已缴费人数
	private int hasregister;//已注册人数
	private int hascertificateno;//有证人数
	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getXueli() {
		return xueli;
	}
	public void setXueli(String xueli) {
		this.xueli = xueli;
	}
	public String getShenfenzhenghao() {
		return shenfenzhenghao;
	}
	public void setShenfenzhenghao(String shenfenzhenghao) {
		this.shenfenzhenghao = shenfenzhenghao;
	}
	public String getMobliephone() {
		return mobliephone;
	}
	public void setMobliephone(String mobliephone) {
		this.mobliephone = mobliephone;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getCertificateno() {
		return certificateno;
	}
	public void setCertificateno(int certificateno) {
		this.certificateno = certificateno;
	}
	public Date getExamdate() {
		return examdate;
	}
	public void setExamdate(Date examdate) {
		this.examdate = examdate;
	}
	
	public String getIsregister() {
		return isregister;
	}
	public void setIsregister(String isregister) {
		this.isregister = isregister;
	}
	public String getIspaymoney() {
		return ispaymoney;
	}
	public void setIspaymoney(String ispaymoney) {
		this.ispaymoney = ispaymoney;
	}
	public String getIscertificate() {
		return iscertificate;
	}
	public void setIscertificate(String iscertificate) {
		this.iscertificate = iscertificate;
	}
	public Date getCertificatestart() {
		return certificatestart;
	}
	public void setCertificatestart(Date certificatestart) {
		this.certificatestart = certificatestart;
	}
	public Date getCertificateend() {
		return certificateend;
	}
	public void setCertificateend(Date certificateend) {
		this.certificateend = certificateend;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getNocertificateno() {
		return nocertificateno;
	}
	public void setNocertificateno(int nocertificateno) {
		this.nocertificateno = nocertificateno;
	}
	public int getHaspaymoney() {
		return haspaymoney;
	}
	public void setHaspaymoney(int haspaymoney) {
		this.haspaymoney = haspaymoney;
	}
	public int getHasregister() {
		return hasregister;
	}
	public void setHasregister(int hasregister) {
		this.hasregister = hasregister;
	}
	public int getHascertificateno() {
		return hascertificateno;
	}
	public void setHascertificateno(int hascertificateno) {
		this.hascertificateno = hascertificateno;
	}
    

}
