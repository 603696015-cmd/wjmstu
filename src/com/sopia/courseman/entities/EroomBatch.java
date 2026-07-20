package com.sopia.courseman.entities;

import java.util.List;

import com.sopia.duman.entities.ELUser;



public class EroomBatch {
	private int id;
	private String title;
	private String description; 
//	private EroomBatchLib batchlib;
	private ELUser creater ;
	private List<ExamRoom> erooms;
	private int usersize;
	private float avgscore;//全部人员平均分的平均分
	private int passsize;//及格人数
	private int pass9_ ;//90分以上
	private int pass8_9;//	80-90分
	private int pass7_8;//70-80
	private int pass6_7;//	60-70
	private int pass_6;//60以下
	private int userSize;
	private float process;
	
	public float getProcess() {
		return process;
	}
	public void setProcess(float process) {
		this.process = process;
	}
	public int getUserSize() {
		return userSize;
	}
	public void setUserSize(int userSize) {
		this.userSize = userSize;
	}
	public int getUsersize() {
		return usersize;
	}
	public void setUsersize(int usersize) {
		this.usersize = usersize;
	}
	public float getAvgscore() {
		return avgscore;
	}
	public void setAvgscore(float avgscore) {
		this.avgscore = avgscore;
	}
	public int getPasssize() {
		return passsize;
	}
	public void setPasssize(int passsize) {
		this.passsize = passsize;
	}
	public int getPass9_() {
		return pass9_;
	}
	public void setPass9_(int pass9_) {
		this.pass9_ = pass9_;
	}
	public int getPass8_9() {
		return pass8_9;
	}
	public void setPass8_9(int pass8_9) {
		this.pass8_9 = pass8_9;
	}
	public int getPass7_8() {
		return pass7_8;
	}
	public void setPass7_8(int pass7_8) {
		this.pass7_8 = pass7_8;
	}
	public int getPass6_7() {
		return pass6_7;
	}
	public void setPass6_7(int pass6_7) {
		this.pass6_7 = pass6_7;
	}
	public int getPass_6() {
		return pass_6;
	}
	public void setPass_6(int pass_6) {
		this.pass_6 = pass_6;
	}
	public EroomBatch( int id ,String title) {
		this.id = id;
		this.title = title;
	}
	public EroomBatch() {
	}
	public EroomBatch(int id) {
		this.id = id;
	}
	public String getName() {
		return title;
	}
	public void setName(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
//	public EroomBatchLib getBatchlib() {
//		return batchlib;
//	}
//	public void setBatchlib(EroomBatchLib batchlib) {
//		this.batchlib = batchlib;
//	}
	public List<ExamRoom> getErooms() {
		return erooms;
	}
	public void setErooms(List<ExamRoom> erooms) {
		this.erooms = erooms;
	}
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
}
