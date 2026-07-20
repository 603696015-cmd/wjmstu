package com.sopia.schedule.entities;

import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

/**
 * 工作日志综合查询
 * @author Administrator
 *
 */
public class Gzrz {
	private String name;
	private String dep;
	private double byme;
	private double bydep;
	private double leader;
	private ELUser elUser;
	private Department department;
	
	private double cha1;
	private double cha2;
	private double cha3;
	
	public double getCha1() {
		return cha1;
	}
	public void setCha1(double cha1) {
		this.cha1 = cha1;
	}
	public double getCha2() {
		return cha2;
	}
	public void setCha2(double cha2) {
		this.cha2 = cha2;
	}
	public double getCha3() {
		return cha3;
	}
	public void setCha3(double cha3) {
		this.cha3 = cha3;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public double getByme() {
		return byme;
	}
	public void setByme(double byme) {
		this.byme = byme;
	}
	public double getBydep() {
		return bydep;
	}
	public void setBydep(double bydep) {
		this.bydep = bydep;
	}
	public double getLeader() {
		return leader;
	}
	public void setLeader(double leader) {
		this.leader = leader;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
}
