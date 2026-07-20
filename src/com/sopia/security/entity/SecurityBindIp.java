package com.sopia.security.entity;

public class SecurityBindIp {
	
	private int id;
	private int roleid;
	private int is_bind;
	private String ip_start;
	private String ip_end;
	
	private String[] ip_start_array;
	private String[] ip_end_array;
	
	public SecurityBindIp(){
	}
	
	public SecurityBindIp(int roleid){
		this.roleid = roleid;
	}
	
	public SecurityBindIp(int roleid,int is_bind){
		this.roleid = roleid;
		this.is_bind = is_bind;
	}
	
	public String[] getIp_start_array() {
		return ip_start_array;
	}
	public void setIp_start_array(String[] ip_start_array) {
		this.ip_start_array = ip_start_array;
	}
	public String[] getIp_end_array() {
		return ip_end_array;
	}
	public void setIp_end_array(String[] ip_end_array) {
		this.ip_end_array = ip_end_array;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public int getIs_bind() {
		return is_bind;
	}
	public void setIs_bind(int is_bind) {
		this.is_bind = is_bind;
	}
	public String getIp_start() {
		return ip_start;
	}
	public void setIp_start(String ip_start) {
		this.ip_start = ip_start;
	}
	public String getIp_end() {
		return ip_end;
	}
	public void setIp_end(String ip_end) {
		this.ip_end = ip_end;
	}
	
	

}
