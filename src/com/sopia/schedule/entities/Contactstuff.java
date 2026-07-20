package com.sopia.schedule.entities;

public class Contactstuff {
	private int id	;// number
	private int contactid		;// number
	private String stuffaddr		;// varchar2(500)
	private String title		;// varchar2(500)
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContactid() {
		return contactid;
	}
	public void setContactid(int contactid) {
		this.contactid = contactid;
	}
	public String getStuffaddr() {
		return stuffaddr;
	}
	public void setStuffaddr(String stuffaddr) {
		this.stuffaddr = stuffaddr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
