package com.sopia.forumman.entities;

import java.util.List;

import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;

public class ForumClassClub {
	private   	ElClType  		elClType;
	private   	List<ElClass>  	zuixinelClass;
	private		List<ElClass>	hotelClass;
	public ElClType getElClType() {
		return elClType;
	}
	public void setElClType(ElClType elClType) {
		this.elClType = elClType;
	}
	public List<ElClass> getZuixinelClass() {
		return zuixinelClass;
	}
	public void setZuixinelClass(List<ElClass> zuixinelClass) {
		this.zuixinelClass = zuixinelClass;
	}
	public List<ElClass> getHotelClass() {
		return hotelClass;
	}
	public void setHotelClass(List<ElClass> hotelClass) {
		this.hotelClass = hotelClass;
	}
	
	
	
}
