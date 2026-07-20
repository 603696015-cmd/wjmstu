package com.sopia.pfms.entities;

public class Suoshulanmu {;

	private int id;
	private String lanmu;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLanmu() {
		return lanmu;
	}
	public void setLanmu(String lanmu) {
		this.lanmu = lanmu;
	}
	
	public Suoshulanmu(){
		
	}

	public Suoshulanmu(int id,String lanmu){
		this.id = id;
		this.lanmu = lanmu;
	}
	public Suoshulanmu(int id){
		this.id = id; 
	}


}
