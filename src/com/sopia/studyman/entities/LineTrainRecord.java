package com.sopia.studyman.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 线下培训记录
 * @author jiahaijiang
 *
 */
public class LineTrainRecord implements Serializable{

	public LineTrainRecord(){};
	public LineTrainRecord(Integer trainid) {
		this.trainid = trainid;
	}
	/**
	 * @author jiahaijiang
	 *
	 */
	private static final long serialVersionUID = 4069076267267438035L;
	
	/*
	 * 主键ID
	 */
	private Integer trainid;
	
	/*
	 * 培训名称
	 */
	private String trainname;
	
	/*
	 * 提交时间
	 */
	private Date submittime;
	
	/*
	 * 证书名称
	 */
	private String certificate;
	
	/*
	 * 培训开始时间
	 */
	private Date trainstarttime;
	
	/*
	 * 培训结束时间
	 */
	private Date trainendtime;
	
	/*
	 * 培训时长
	 */
	private String trainlength;
	
	/*
	 * 状态
	 */
	private Integer state;
	
	/*
	 * 状态名称
	 */
	private String stateName;
	
	/*
	 * 备注
	 */
	private String remark;
	
	/*
	 * 创建者ID
	 */
	private Integer createuserid;
	
	/*
	 * 创建者名称
	 */
	private String createname;
	
	private int    credit;
	
	
	/*
	 * 附件列表
	 */
	private List<LineTrainRecordStuff> lineTrainRecordStuffs;
	
	
	
	
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public List<LineTrainRecordStuff> getLineTrainRecordStuffs() {
		return lineTrainRecordStuffs;
	}

	public void setLineTrainRecordStuffs(
			List<LineTrainRecordStuff> lineTrainRecordStuffs) {
		this.lineTrainRecordStuffs = lineTrainRecordStuffs;
	}

	public Integer getTrainid() {
		return trainid;
	}

	public void setTrainid(Integer trainid) {
		this.trainid = trainid;
	}

	public String getTrainname() {
		return trainname;
	}

	public void setTrainname(String trainname) {
		this.trainname = trainname;
	}

	public Date getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public Date getTrainstarttime() {
		return trainstarttime;
	}

	public void setTrainstarttime(Date trainstarttime) {
		this.trainstarttime = trainstarttime;
	}

	public Date getTrainendtime() {
		return trainendtime;
	}

	public void setTrainendtime(Date trainendtime) {
		this.trainendtime = trainendtime;
	}

	public String getTrainlength() {
		return trainlength;
	}

	public void setTrainlength(String trainlength) {
		this.trainlength = trainlength;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateName() {
		if(this.getState()==1){
			stateName="已创建";
		}else if(this.getState()==2){
			stateName="审核等待中";
		}else if(this.getState()==3){
			stateName="未通过";
		}else if(this.getState()==4){
			stateName="已审核";
		}
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(Integer createuserid) {
		this.createuserid = createuserid;
	}

	public String getCreatename() {
		return createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

}
