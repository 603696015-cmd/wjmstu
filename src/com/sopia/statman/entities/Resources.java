package com.sopia.statman.entities;

import com.sopia.duman.entities.Department;

public class Resources {
	private Department department;//部门
	private int ccount;//课程总数
	private int ccount_status;//已开通课程数
	private int elcount;//培训班总数
	private int elcount_status;//已开通培训班数
	private int qcount;//题目总数
	private int qcount_status;//可使用题目数
	private int ecount;//试卷总数
	private int ecount_status;//可使用试卷数
	private int ercount;//考场总数
	private int ercount_status;//已开通考场数
	private int kcount;//资料总数
	private int kcount_status;//已审核资料数
	private int ncount;//新闻总数
	private int ncount_status;//已发布新闻数
	private int fcount;//帖子总数
	private int fcount_status;//已通过帖子数
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public int getQcount_status() {
		return qcount_status;
	}
	public void setQcount_status(int qcount_status) {
		this.qcount_status = qcount_status;
	}
	public int getEcount_status() {
		return ecount_status;
	}
	public void setEcount_status(int ecount_status) {
		this.ecount_status = ecount_status;
	}
	public int getCcount() {
		return ccount;
	}
	public void setCcount(int ccount) {
		this.ccount = ccount;
	}
	public int getCcount_status() {
		return ccount_status;
	}
	public void setCcount_status(int ccount_status) {
		this.ccount_status = ccount_status;
	}
	public int getElcount() {
		return elcount;
	}
	public void setElcount(int elcount) {
		this.elcount = elcount;
	}
	public int getElcount_status() {
		return elcount_status;
	}
	public void setElcount_status(int elcount_status) {
		this.elcount_status = elcount_status;
	}
	public int getQcount() {
		return qcount;
	}
	public void setQcount(int qcount) {
		this.qcount = qcount;
	}
	public int getEcount() {
		return ecount;
	}
	public void setEcount(int ecount) {
		this.ecount = ecount;
	}
	public int getErcount() {
		return ercount;
	}
	public void setErcount(int ercount) {
		this.ercount = ercount;
	}
	public int getErcount_status() {
		return ercount_status;
	}
	public void setErcount_status(int ercount_status) {
		this.ercount_status = ercount_status;
	}
	public int getKcount() {
		return kcount;
	}
	public void setKcount(int kcount) {
		this.kcount = kcount;
	}
	public int getKcount_status() {
		return kcount_status;
	}
	public void setKcount_status(int kcount_status) {
		this.kcount_status = kcount_status;
	}
	public int getNcount() {
		return ncount;
	}
	public void setNcount(int ncount) {
		this.ncount = ncount;
	}
	public int getNcount_status() {
		return ncount_status;
	}
	public void setNcount_status(int ncount_status) {
		this.ncount_status = ncount_status;
	}
	public int getFcount() {
		return fcount;
	}
	public void setFcount(int fcount) {
		this.fcount = fcount;
	}
	public int getFcount_status() {
		return fcount_status;
	}
	public void setFcount_status(int fcount_status) {
		this.fcount_status = fcount_status;
	}
}
