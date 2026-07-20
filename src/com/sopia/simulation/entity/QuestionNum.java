package com.sopia.simulation.entity;

import java.util.List;

public class QuestionNum {
	private String parts;
	private String serialNum;
	private List<Integer>num;
	private List<String> strNums;
	private String readId;
	public String getParts() {
		return parts;
	}
	public void setParts(String parts) {
		this.parts = parts;
	}
	public List<Integer> getNum() {
		return num;
	}
	public void setNum(List<Integer> nums) {
		this.num = nums;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public List<String> getStrNums() {
		return strNums;
	}
	public void setStrNums(List<String> strNums) {
		this.strNums = strNums;
	}
	public String getReadId() {
		return readId;
	}
	public void setReadId(String readId) {
		this.readId = readId;
	}

	
	
	
	
}
