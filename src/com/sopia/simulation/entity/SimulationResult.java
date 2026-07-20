package com.sopia.simulation.entity;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 考试结果实体类
 * @author zahj
 *
 */
public class SimulationResult implements Serializable{

	private int id;
	
	/**
	 * 用户ID
	 */
	private int userId;
	
	/**
	 * 用户考试记录id
	 */
	private String result;
	
	/**
	 * 用户考试分数
	 */
	private String score;
	
	/**
	 * 用户姓名
	 */
	private String name;
	
	/**
	 * 考试试卷id
	 */
	private int paperId;
	
	/**
	 * 学生编号
	 */
	private String stuNo;
	
	/**
	 * 创建时间时间搓
	 */
	private Timestamp createTime;
	
	private Timestamp updateTime;

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
//	public static void main(String[] args) {
//		System.out.println(SimulationUtil.dateToString(System.currentTimeMillis()));
//	}
	
}
