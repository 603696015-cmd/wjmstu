package com.sopia.talentman.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.duman.entities.ELUser;

public class KTRoomColl {
	private int id;
	private String title;
	private String description;
	private Timestamp createtime;
	
	private ELUser creater;
	private List<KTRoom> trooms;
	public KTRoomColl() {
	}
	public KTRoomColl(int id){
		this.id = id;
	}
	public KTRoomColl(int id,String title) {
		this.id=id;this.title=title;
	}
	
	public List<KTRoom> getTrooms() {
		return trooms;
	}
	public void setTrooms(List<KTRoom> trooms) {
		this.trooms = trooms;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
