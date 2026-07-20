package com.sopia.studyman.entities;

import java.io.Serializable;

/**
 * 学籍查询
 * @author jiahaijiang
 *
 */
public class Schoolrolls implements Serializable{

	/**
	 * @author jiahaijiang
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;//用户ID
	
	private String realname;//姓名
	
	private String username;//账号
	
	private String deptname;//部门
	
	private Integer completeClass=Integer.valueOf(0);//完成培训班数量
	
	private Integer completeExam=Integer.valueOf(0);//通过考试数量
	
	private Integer completeLineTrain=Integer.valueOf(0);//线下培训记录数

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public Integer getCompleteClass() {
		return completeClass;
	}

	public void setCompleteClass(Integer completeClass) {
		this.completeClass = completeClass;
	}

	public Integer getCompleteExam() {
		return completeExam;
	}

	public void setCompleteExam(Integer completeExam) {
		this.completeExam = completeExam;
	}

	public Integer getCompleteLineTrain() {
		return completeLineTrain;
	}

	public void setCompleteLineTrain(Integer completeLineTrain) {
		this.completeLineTrain = completeLineTrain;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	

}
