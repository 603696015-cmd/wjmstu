package com.sopia.pfms.entities;

import java.sql.Date;
import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;

public class Policy {
	private int id;//主键id
	private int libId;//树id 
	private String libName;//树name
	private ELUser createId;//创建人
	private int commodityId;//保险产品 
	private String commodityName;//保险产品 
	private String IC_TABLENAME;//险种表名
	private int IC_U_ID;//险种id 
	private int valid;//状态 
	private Timestamp submitTime;//提交时间
	private Timestamp startTime;//生效时间
	private int electronicPolicy;//电子保单
	private String scanning;//扫描件
	private String huiyuandanwei;//会员单位
	
	public String getHuiyuandanwei() {
		return huiyuandanwei;
	}

	public void setHuiyuandanwei(String huiyuandanwei) {
		this.huiyuandanwei = huiyuandanwei;
	}

	public Policy(){ 
	}

	public Policy(int id){ 
		this.id = id;
	} 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLibId() {
		return libId;
	}
	public void setLibId(int libId) {
		this.libId = libId;
	} 
	public int getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	} 
	public int getValid() {
		return valid;
	}
	public String getValidName() {
		if(valid == 0)
			return "已保存";
		if(valid == 1)
			return "申请审核";
		if(valid == 2)
			return "初审未通过";
		if(valid == 3)
			return "初审通过"; 
		if(valid == 4)
			return "终审不通过"; 
		if(valid == 5)
			return "终审通过"; 
		if(valid == 6)
			return "已删除"; 
		
		return "未知";
	}
	public void setValid(int valid) {
		this.valid = valid;
	} 
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public int getElectronicPolicy() {
		return electronicPolicy;
	}
	public void setElectronicPolicy(int electronicPolicy) {
		this.electronicPolicy = electronicPolicy;
	}
	public String getScanning() {
		return scanning;
	}
	public void setScanning(String scanning) {
		this.scanning = scanning;
	}

	public String getIC_TABLENAME() {
		return IC_TABLENAME;
	}

	public void setIC_TABLENAME(String ic_tablename) {
		IC_TABLENAME = ic_tablename;
	}

	public int getIC_U_ID() {
		return IC_U_ID;
	}

	public void setIC_U_ID(int ic_u_id) {
		IC_U_ID = ic_u_id;
	}

	public ELUser getCreateId() {
		return createId;
	}

	public void setCreateId(ELUser createId) {
		this.createId = createId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getLibName() {
		return libName;
	}

	public void setLibName(String libName) {
		this.libName = libName;
	}
}
