package com.sopia.schedule.entities;

public class Contact {
	private int id     ;//	number
	private String theme	  ;//	(50)
	private String type	  ;//	varchar2(20)
	private String content  ;//		varchar2(500)
	private String time	  ;//	date
	private String money	  ;//	varchar2(20)
	private String re_client	  ;//	varchar2(100)
	private String createtime;
	private int userid;
	private String username;
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public int getId() {
		return id;
	}
	public void setId(String id)
	{
		this.id=Integer.valueOf(id);
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getRe_client() {
		return re_client;
	}
	public void setRe_client(String re_client) {
		this.re_client = re_client;
	}
	
	
	
	
}
