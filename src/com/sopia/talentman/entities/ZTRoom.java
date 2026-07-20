package com.sopia.talentman.entities;

import java.sql.Timestamp;

import com.sopia.ElConstants;
import com.sopia.duman.entities.ELUser;

public class ZTRoom {
	private int id;
	private String title;
	private String description;
	private Timestamp begintime;
	private Timestamp endtime;

	private String norm;
	private String norms[];
	private ELUser creater;
	public ZTRoom() {
	}
	public ZTRoom(int id,String title) {
		this.id= id;
		this.title = title;
	}
	
	public ELUser getCreater() {
		return creater;
	}

	public void setCreater(ELUser creater) {
		this.creater = creater;
	}

	public String[] getNorms() {
		if(null !=norm){
			norms= norm.split(ElConstants.optSplit);
		}
		return norms;
	}

	public void setNorms(String[] norms) {
		this.norms = norms;
	}
	
	public String getNorm() {
		if(null!=norms){ 
			norm="";
			for (int i = 0; i < norms.length; i++) {
				norm += norms[i]+ElConstants.optSplit;
			}
		}
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
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

	public Timestamp getBegintime() {
		return begintime;
	}

	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

}
