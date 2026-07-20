package com.sopia.questionman.entities;

import java.sql.Timestamp;

public class StuffQuery  {
	private String title;//资源名
	private int stuffSizeStart;//文件大小范围
	private int stuffSizeEnd;//文件大小范围
	private Timestamp createTimeStart;//创建时间范围
	private Timestamp createTimeEnd;//创建时间范围
	private Timestamp modifyTimeStart;//修改时间范围
	private Timestamp modifyTimeEnd;//修改时间范围
	private String stuffExt;//文件格式
	private String key;//关键词
	private int parentid; //所属类别id
	
	
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStuffSizeStart() {
		return stuffSizeStart;
	}
	public void setStuffSizeStart(int stuffSizeStart) {
		this.stuffSizeStart = stuffSizeStart;
	}
	public int getStuffSizeEnd() {
		return stuffSizeEnd;
	}
	public void setStuffSizeEnd(int stuffSizeEnd) {
		this.stuffSizeEnd = stuffSizeEnd;
	}
	public Timestamp getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(Timestamp createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public Timestamp getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(Timestamp createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public Timestamp getModifyTimeStart() {
		return modifyTimeStart;
	}
	public void setModifyTimeStart(Timestamp modifyTimeStart) {
		this.modifyTimeStart = modifyTimeStart;
	}
	public Timestamp getModifyTimeEnd() {
		return modifyTimeEnd;
	}
	public void setModifyTimeEnd(Timestamp modifyTimeEnd) {
		this.modifyTimeEnd = modifyTimeEnd;
	}
	public String getStuffExt() {
		return stuffExt;
	}
	public void setStuffExt(String stuffExt) {
		this.stuffExt = stuffExt;
	}
}
