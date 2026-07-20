package com.sopia.lineTrainingCourse.entities;

import com.sopia.duman.entities.ELUser;

/**
 * 线下培训班分配实体
 * @author Administrator
 *
 */
public class LineTrainingCourseAssign {
	
	private int id;
	private int approval_status;				//审核状态		[0：未审核；1：审核通过]
	private int line_training_course_id;		//线下培训班id
	private int is_get_certificate;				//是否获得证书	[0：未获得；1：获得]
	private double score;						//成绩
	private int pay_status;						//缴费状态		[0：未缴费；1：已缴费]
	private int allocation_type;				//分配方式		[0：系统管理员进行分配；1：报名]
	private int userId;							//分配用户id
	private String accessory;					//相关附件
	
	private ELUser elUser;						//被分配用户信息
	private LineTrainingCourse lineTrainingCourse;
	
	public LineTrainingCourse getLineTrainingCourse() {
		return lineTrainingCourse;
	}
	public void setLineTrainingCourse(LineTrainingCourse lineTrainingCourse) {
		this.lineTrainingCourse = lineTrainingCourse;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAllocation_type() {
		return allocation_type;
	}
	public void setAllocation_type(int allocation_type) {
		this.allocation_type = allocation_type;
	}
	public int getLine_training_course_id() {
		return line_training_course_id;
	}
	public void setLine_training_course_id(int line_training_course_id) {
		this.line_training_course_id = line_training_course_id;
	}
	public int getIs_get_certificate() {
		return is_get_certificate;
	}
	public void setIs_get_certificate(int is_get_certificate) {
		this.is_get_certificate = is_get_certificate;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getPay_status() {
		return pay_status;
	}
	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}
	public String getAccessory() {
		return accessory;
	}
	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApproval_status() {
		return approval_status;
	}
	public void setApproval_status(int approval_status) {
		this.approval_status = approval_status;
	}
	
	

}
