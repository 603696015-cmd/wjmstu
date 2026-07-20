package com.sopia.duman.entities;

import java.util.Map;

/**
 * 自定义用户管理模块实体
 * @author Administrator
 *
 */
public class ELUserDemo {
	/**
	 * 系统自带字段
	 */
	private int id;			// id
	private String username;// 帐号
	private String password;// 密码
	private String realname;// 姓名
	private String shenfenzheng;//身份证
	private int depid;//部门id
	private int roleid;//角色id
	private int staid;//岗位id
	private Department department;//部门实体
	private ElRole role;//角色实体
	private Station station;//岗位实体
	private boolean valid; //是否开通
	
	/**
	 * 数据字典
	 * 数据存于BASEDATAT、BASEDATATYPE
	 */
	private int jingzhong;
	private int zhiwu;
	private int zhiji;
	private int dishi;
	
	/**
	 * 自定义字段
	 */
	private Map<String,Object> demoColumns;

	public ELUserDemo(){}
	public ELUserDemo(int id,String realname){
		this.id = id;
		this.realname = realname;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getShenfenzheng() {
		return shenfenzheng;
	}

	public void setShenfenzheng(String shenfenzheng) {
		this.shenfenzheng = shenfenzheng;
	}

	public int getDepid() {
		return depid;
	}

	public void setDepid(int depid) {
		this.depid = depid;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	public int getStaid() {
		return staid;
	}

	public void setStaid(int staid) {
		this.staid = staid;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public int getJingzhong() {
		return jingzhong;
	}

	public void setJingzhong(int jingzhong) {
		this.jingzhong = jingzhong;
	}

	public int getZhiwu() {
		return zhiwu;
	}

	public void setZhiwu(int zhiwu) {
		this.zhiwu = zhiwu;
	}

	public int getZhiji() {
		return zhiji;
	}

	public void setZhiji(int zhiji) {
		this.zhiji = zhiji;
	}

	public int getDishi() {
		return dishi;
	}

	public void setDishi(int dishi) {
		this.dishi = dishi;
	}

	public Map<String, Object> getDemoColumns() {
		return demoColumns;
	}

	public void setDemoColumns(Map<String, Object> demoColumns) {
		this.demoColumns = demoColumns;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public ElRole getRole() {
		return role;
	}
	public void setRole(ElRole role) {
		this.role = role;
	}
	
	

}
