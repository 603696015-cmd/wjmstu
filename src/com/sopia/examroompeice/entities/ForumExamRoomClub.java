package com.sopia.examroompeice.entities;

import java.util.List;

import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;

public class ForumExamRoomClub {
	private   	EroomLib  		eroomLib;
	private   	List<ExamRoom>  	zuixinexamRoom;
	private		List<ExamRoom>	hotelexamRoom;
	public EroomLib getEroomLib() {
		return eroomLib;
	}
	public void setEroomLib(EroomLib eroomLib) {
		this.eroomLib = eroomLib;
	}
	public List<ExamRoom> getZuixinexamRoom() {
		return zuixinexamRoom;
	}
	public void setZuixinexamRoom(List<ExamRoom> zuixinexamRoom) {
		this.zuixinexamRoom = zuixinexamRoom;
	}
	public List<ExamRoom> getHotelexamRoom() {
		return hotelexamRoom;
	}
	public void setHotelexamRoom(List<ExamRoom> hotelexamRoom) {
		this.hotelexamRoom = hotelexamRoom;
	}

}
