package com.sopia.statman.entities;

import com.sopia.duman.entities.Department;

/**
 * 流量统计实体
 * @author Administrator
 *
 */
public class Flow {
	private Department department;//部门
	private int registerUserCount;//注册用户数
	private int loginUserCount;//当前登录用户数
	private int dayLoginUserCount;//今天登录用户数
	private int dayLoginUserSum;//今天登录用户人次
	private int weekLoginUserCount;//本周登录用户数
	private int weekLoginUserSum;//本周登录用户人次
	private int monthLoginUserCount;//本月登录用户数
	private int monthLoginUserSum;//本月登录用户人次
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public int getRegisterUserCount() {
		return registerUserCount;
	}
	public void setRegisterUserCount(int registerUserCount) {
		this.registerUserCount = registerUserCount;
	}
	public int getLoginUserCount() {
		return loginUserCount;
	}
	public void setLoginUserCount(int loginUserCount) {
		this.loginUserCount = loginUserCount;
	}
	public int getDayLoginUserCount() {
		return dayLoginUserCount;
	}
	public void setDayLoginUserCount(int dayLoginUserCount) {
		this.dayLoginUserCount = dayLoginUserCount;
	}
	public int getDayLoginUserSum() {
		return dayLoginUserSum;
	}
	public void setDayLoginUserSum(int dayLoginUserSum) {
		this.dayLoginUserSum = dayLoginUserSum;
	}
	public int getWeekLoginUserCount() {
		return weekLoginUserCount;
	}
	public void setWeekLoginUserCount(int weekLoginUserCount) {
		this.weekLoginUserCount = weekLoginUserCount;
	}
	public int getWeekLoginUserSum() {
		return weekLoginUserSum;
	}
	public void setWeekLoginUserSum(int weekLoginUserSum) {
		this.weekLoginUserSum = weekLoginUserSum;
	}
	public int getMonthLoginUserCount() {
		return monthLoginUserCount;
	}
	public void setMonthLoginUserCount(int monthLoginUserCount) {
		this.monthLoginUserCount = monthLoginUserCount;
	}
	public int getMonthLoginUserSum() {
		return monthLoginUserSum;
	}
	public void setMonthLoginUserSum(int monthLoginUserSum) {
		this.monthLoginUserSum = monthLoginUserSum;
	}
}
