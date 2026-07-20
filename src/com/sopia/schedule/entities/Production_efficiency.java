package com.sopia.schedule.entities;

public class Production_efficiency {
	private double sccb;//生产成本
	private String month;
	private String type;
	private String relate;
	private double cz;//产值
	private double xx;//效益
	
	public double getXx() {
		return xx;
	}
	public void setXx(double xx) {
		this.xx = xx;
	}
	public double getCz() {
		return cz;
	}
	public void setCz(double cz) {
		this.cz = cz;
	}
	public double getSccb() {
		return sccb;
	}
	public void setSccb(double sccb) {
		this.sccb = sccb;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRelate() {
		return relate;
	}
	public void setRelate(String relate) {
		this.relate = relate;
	}
	
	

}
