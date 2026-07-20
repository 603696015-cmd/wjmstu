package com.sopia.duman.entities;

import java.util.List;

import com.sopia.common.ElNode;

public class Station extends ElNode{
	
	private String name;
	private String description;
	private int credit;//学分
	private int bixiu;
	private int xuanxiu;
	private List<Station> child;
	private int issp;//是否二级页面
	private ELUser manager;
	private String address;
	private String postalcode;
	private String phone;
	private String fax;
	private String email;
	private int lid;
	private int rid;
	private String bh;
	
	private int classCount;
	private List<ELUser> opusers;
	private int count;
	private int brc;
	private int bscore;
	private String leibie;
	private int depid;
	private String cengji;
	private int classid;
	
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public String getCengji() {
		return cengji;
	}
	public void setCengji(String cengji) {
		this.cengji = cengji;
	}
	public int getDepid() {
		return depid;
	}
	public void setDepid(int depid) {
		this.depid = depid;
	}
	public String getLeibie() {
		return leibie;
	}
	public void setLeibie(String leibie) {
		this.leibie = leibie;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getBrc() {
		return brc;
	}
	public void setBrc(int brc) {
		this.brc = brc;
	}
	public int getBscore() {
		return bscore;
	}
	public void setBscore(int bscore) {
		this.bscore = bscore;
	}
	public List<ELUser> getOpusers() {
		return opusers;
	}
	public void setOpusers(List<ELUser> opusers) {
		this.opusers = opusers;
	}
	public int getClassCount() {
		return classCount;
	}
	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public ELUser getManager() {
		return manager;
	}
	public void setManager(ELUser manager) {
		this.manager = manager;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public int getBixiu() {
		return bixiu;
	}
	public void setBixiu(int bixiu) {
		this.bixiu = bixiu;
	}
	public int getXuanxiu() {
		return xuanxiu;
	}
	public void setXuanxiu(int xuanxiu) {
		this.xuanxiu = xuanxiu;
	}
	public List<Station> getChild() {
		return child;
	}
	public void setChild(List<Station> child) {
		this.child = child;
	}
	public int getIssp() {
		return issp;
	}
	public void setIssp(int issp) {
		this.issp = issp;
	}
	
	public Station() {
	}

	public Station(int id) {
		super(id);
	}

	public Station(int id, String name) {
		super(id);
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
}
