package com.sopia.schedule.entities;

import java.sql.Timestamp;

public class Client {
	 private int id      ;//          NUMBER not null,
	  private String name      ;//          VARCHAR2(100),
	  private String tel   ;//              VARCHAR2(20),
	  private String tax ;//                VARCHAR2(50),
	  private String url      ;//           VARCHAR2(100),
	  private String email         ;//      VARCHAR2(50),
	  private String city       ;//        VARCHAR2(50),
	  private String addr        ;//        VARCHAR2(200),
	  private String  postcode        ;//    VARCHAR2(20),
	  private String clientfrom    ;//      VARCHAR2(20),
	  private String companytype     ;//    VARCHAR2(20),
	  private String industry     ;//       VARCHAR2(20),
	  private String mainbusiness   ;//     VARCHAR2(100),
	  private String companysize     ;//    VARCHAR2(20),
	 // private Timestamp startbusiness   ;//    DATE,
	  private String startbusiness   ;
	  private String registeredcapital ;//  VARCHAR2(50),
	  private String legal      ;//         VARCHAR2(50),
	  private String bank       ;//         VARCHAR2(100),
	  private String bankaccount    ;//     VARCHAR2(100),
	  private String duty         ;//       VARCHAR2(50),
	//  private Timestamp  createdate ;//        DATE,
	  private String createdate ;
	  private String superclient    ;//     VARCHAR2(100),
	  private String remark      ;//        VARCHAR2(500),
	  private int userid         ;//     NUMBER,
	  private int principalid   ;//      NUMBER
	  private String status;
	  
	  private String username;
	  private int count;
	  
	  private String begintime;
	  private String endtime;
	  
	  
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getClientfrom() {
		return clientfrom;
	}
	public void setClientfrom(String clientfrom) {
		this.clientfrom = clientfrom;
	}
	public String getCompanytype() {
		return companytype;
	}
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getMainbusiness() {
		return mainbusiness;
	}
	public void setMainbusiness(String mainbusiness) {
		this.mainbusiness = mainbusiness;
	}
	public String getCompanysize() {
		return companysize;
	}
	public void setCompanysize(String companysize) {
		this.companysize = companysize;
	}
	public String getStartbusiness() {
		return startbusiness;
	}
	public void setStartbusiness(String startbusiness) {
		this.startbusiness = startbusiness;
	}
	public String getRegisteredcapital() {
		return registeredcapital;
	}
	public void setRegisteredcapital(String registeredcapital) {
		this.registeredcapital = registeredcapital;
	}
	public String getLegal() {
		return legal;
	}
	public void setLegal(String legal) {
		this.legal = legal;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankaccount() {
		return bankaccount;
	}
	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getSuperclient() {
		return superclient;
	}
	public void setSuperclient(String superclient) {
		this.superclient = superclient;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getPrincipalid() {
		return principalid;
	}
	public void setPrincipalid(int principalid) {
		this.principalid = principalid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	  
	  
	  
	  
	
}
