package com.sopia.duman.entities;

public class Typelrid {
	private int lid;
	private int rid;
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public Typelrid() {
	}
	public Typelrid(int lid, int rid) {
		this.lid = lid;
		this.rid = rid;
	}
}
