package com.sopia.lineTrainingCourse.entities;

import java.sql.Timestamp;

import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.StuffLib;

/**
 * 线下培训班基本信息
 * @author Administrator
 *
 */
public class LineTrainingCourse {
	
	private int id;
	private String name;				//培训名称
	private int person_number_plan;		//计划招收人数
	private Timestamp createtime;		//发布时间
	private String place;				//地点
	private Timestamp train_begintime;	//培训开始时间
	private Timestamp train_endtime;	//培训结束时间
	private Timestamp sign_begintime;	//报名开始时间
	private Timestamp sign_endtime;		//报名结束时间
	private String key;					//关键字
	private String sign_table_name;		//报名表下载
	private double fee_price;			//收费价格
	private int train_type_id;			//培训类别编号
	private String contact;				//联系方式
	private String contact_name;		//联系人
	private String picture;				//对应图片
	private String jianjie;				//简介
	private int is_open;				//是否已开通		[0：未开通;1：开通]
	private double credit;				//学分
	private int userId;					//培训班创建人
	private int has_signed_number;		//已报人数
	private int isPastDue;				//是否过期       [0:报名时间未到，1：报名时间已过，2：可以报名]
	
	private LineTrainingCourseAssign assign;
	
	private TrainType trainType;
	private ELUser elUser;
	
	private TrainTypeTree ptype;
	
	private int stuff_id;
	private StuffLib stuff;

	public int getStuff_id() {
		return stuff_id;
	}

	public void setStuff_id(int stuff_id) {
		this.stuff_id = stuff_id;
	}

	public StuffLib getStuff() {
		return stuff;
	}

	public void setStuff(StuffLib stuff) {
		this.stuff = stuff;
	}

	public TrainTypeTree getPtype() {
		return ptype;
	}

	public void setPtype(TrainTypeTree ptype) {
		this.ptype = ptype;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public LineTrainingCourseAssign getAssign() {
		return assign;
	}

	public void setAssign(LineTrainingCourseAssign assign) {
		this.assign = assign;
	}

	public int getHas_signed_number() {
		return has_signed_number;
	}

	public void setHas_signed_number(int has_signed_number) {
		this.has_signed_number = has_signed_number;
	}

	public int getIsPastDue() {
		if(new Timestamp(System.currentTimeMillis()).getTime() - sign_begintime.getTime()<0){
			isPastDue = 0;
		}else if(new Timestamp(System.currentTimeMillis()).getTime() - sign_endtime.getTime() >0){
			isPastDue = 1;
		}else{
			isPastDue = 2;
		}
		return isPastDue;
	}

	public void setIsPastDue(int isPastDue) {
		this.isPastDue = isPastDue;
	}

	public String getIs_open_chinese(){
		if(is_open == 0){
			return "未通过";
		}else{
			return "已通过";
		}
	}
	
	public String getJianjie_() {
		return SystemConfOp.toStuffUrl(jianjie);
	}
	
	public String getPicture_(){
		if(picture!=null&&(picture.indexOf("http://")==0||picture.indexOf("https://")==0))
			return picture;
		return  SystemConfOp.getStuffUrl()+picture;
	}
	
	public TrainType getTrainType() {
		return trainType;
	}
	public void setTrainType(TrainType trainType) {
		this.trainType = trainType;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public int getIs_open() {
		return is_open;
	}
	public void setIs_open(int is_open) {
		this.is_open = is_open;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPerson_number_plan() {
		return person_number_plan;
	}
	public void setPerson_number_plan(int person_number_plan) {
		this.person_number_plan = person_number_plan;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public Timestamp getTrain_begintime() {
		return train_begintime;
	}
	public void setTrain_begintime(Timestamp train_begintime) {
		this.train_begintime = train_begintime;
	}
	public Timestamp getTrain_endtime() {
		return train_endtime;
	}
	public void setTrain_endtime(Timestamp train_endtime) {
		this.train_endtime = train_endtime;
	}
	public Timestamp getSign_begintime() {
		return sign_begintime;
	}
	public void setSign_begintime(Timestamp sign_begintime) {
		this.sign_begintime = sign_begintime;
	}
	public Timestamp getSign_endtime() {
		return sign_endtime;
	}
	public void setSign_endtime(Timestamp sign_endtime) {
		this.sign_endtime = sign_endtime;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSign_table_name() {
		return sign_table_name;
	}
	public void setSign_table_name(String sign_table_name) {
		this.sign_table_name = sign_table_name;
	}
	public double getFee_price() {
		return fee_price;
	}
	public void setFee_price(double fee_price) {
		this.fee_price = fee_price;
	}
	public int getTrain_type_id() {
		return train_type_id;
	}
	public void setTrain_type_id(int train_type_id) {
		this.train_type_id = train_type_id;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getJianjie() {
		return jianjie;
	}
	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}
	
	

}
