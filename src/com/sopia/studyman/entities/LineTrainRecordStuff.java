package com.sopia.studyman.entities;

import java.io.Serializable;

public class LineTrainRecordStuff implements Serializable{
	private Integer id;
	private LineTrainRecord lineTrainRecord;
	private String title;
	private String stuffAddr;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LineTrainRecord getLineTrainRecord() {
		return lineTrainRecord;
	}
	public void setLineTrainRecord(LineTrainRecord lineTrainRecord) {
		this.lineTrainRecord = lineTrainRecord;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStuffAddr() {
		return stuffAddr;
	}
	public void setStuffAddr(String stuffAddr) {
		this.stuffAddr = stuffAddr;
	}
}
