package com.sopia.pfms.entities;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;

public class InsuranceCategories { 
	private int id;   //id
	private String name;  //险种名称
	private String tableName; //险种表名
	private String description; //险种表名
	private ELUser founder;//创建人
	private Timestamp createTime;//创建时间
	private String demourl; //模板地址
	private String democss; //模板样式地址
	private String read_auto_toubaoren;//投保人数据读取表
	private String read_auto_beibaoren;//被保人数据读取表
	private String read_auto_biaodi;//标地数据读取表
	
	public String getRead_auto_toubaoren() {
		return read_auto_toubaoren;
	}

	public void setRead_auto_toubaoren(String read_auto_toubaoren) {
		this.read_auto_toubaoren = read_auto_toubaoren;
	}

	public String getRead_auto_beibaoren() {
		return read_auto_beibaoren;
	}

	public void setRead_auto_beibaoren(String read_auto_beibaoren) {
		this.read_auto_beibaoren = read_auto_beibaoren;
	}

	public String getRead_auto_biaodi() {
		return read_auto_biaodi;
	}

	public void setRead_auto_biaodi(String read_auto_biaodi) {
		this.read_auto_biaodi = read_auto_biaodi;
	}

	public String getDemourl() {
		return demourl;
	}

	public void setDemourl(String demourl) {
		this.demourl = demourl;
	}

	public InsuranceCategories(){ 
	}
	
	public InsuranceCategories(int id){
		this.id = id;
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
	public String getTableName() {
		if(tableName != null)
			tableName = tableName.toUpperCase();
		return tableName;
	}
	public String getTableName_() {
		return "IC_U_"+tableName.toUpperCase();
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	} 
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public ELUser getFounder() {
		return founder;
	}

	public void setFounder(ELUser founder) {
		this.founder = founder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDemocss() {
		return democss;
	}

	public void setDemocss(String democss) {
		this.democss = democss;
	} 
}
