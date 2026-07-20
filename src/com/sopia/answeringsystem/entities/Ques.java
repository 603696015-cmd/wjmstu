package com.sopia.answeringsystem.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.duman.entities.ELUser;

/**
 * 问答系统==问题
 * @author Administrator
 *
 */
public class Ques {
	private int id;
	private String name;//标题
	private String content;//简介
	private int status;//状态
	private int statusTow;//状态 //头条、重点、热门、推荐、幻灯  == 0、1、2、3、4
	//（已创建、审核未通过、已发布、已解决、已过期）
	//(0、1、2、3、4)
	private Timestamp validTime;//有效期
	private int fabuUserid;//发布者id
	private ELUser fabuUser;//发布者
	private Timestamp fabuTime;//发布时间
	private int viewCount;//浏览次数
	private int answerCount;//回复数
	private int answeringTypeid;//类别id
	private AnsweringType answeringType;//类别
	private String answerUserids;//指定回答人
	private List<ELUser> answerUsers;//指定回答人
	private List<Answer> answers ;//该问题的回答
	
	public Ques(){}
	public Ques(int id,String name){
		this.id = id;
		this.name = name;
	}
	public String getStatusTow_(){
		if(this.statusTow == 0){
			return "头条";
		}else if(this.statusTow == 1){
			return "重点";
		}else if(this.statusTow == 2){
			return "热门";
		}else if(this.statusTow == 3){
			return "推荐";
		}else if(this.statusTow == 4){
			return "幻灯";
		}else{
			return "普通";
		}
	}
	public String getStatus_(){
		if(this.status == 0){
			return "已创建";
		}else if(this.status == 1){
			return "审核未通过";
		}else if(this.status == 2){
			return "已发布";
		}else if(this.status == 3){
			return "已解决";
		}else if(this.status == 4){
			return "已过期";
		}else{
			return "未知状态";
		}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getValidTime() {
		return validTime;
	}
	public void setValidTime(Timestamp validTime) {
		this.validTime = validTime;
	}
	public int getFabuUserid() {
		return fabuUserid;
	}
	public void setFabuUserid(int fabuUserid) {
		this.fabuUserid = fabuUserid;
	}
	public ELUser getFabuUser() {
		return fabuUser;
	}
	public void setFabuUser(ELUser fabuUser) {
		this.fabuUser = fabuUser;
	}
	public Timestamp getFabuTime() {
		return fabuTime;
	}
	public void setFabuTime(Timestamp fabuTime) {
		this.fabuTime = fabuTime;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public int getAnsweringTypeid() {
		return answeringTypeid;
	}
	public void setAnsweringTypeid(int answeringTypeid) {
		this.answeringTypeid = answeringTypeid;
	}
	public AnsweringType getAnsweringType() {
		return answeringType;
	}
	public void setAnsweringType(AnsweringType answeringType) {
		this.answeringType = answeringType;
	}
	public String getAnswerUserids() {
		return answerUserids;
	}
	public void setAnswerUserids(String answerUserids) {
		this.answerUserids = answerUserids;
	}
	public List<ELUser> getAnswerUsers() {
		return answerUsers;
	}
	public void setAnswerUsers(List<ELUser> answerUsers) {
		this.answerUsers = answerUsers;
	}
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	public int getStatusTow() {
		return statusTow;
	}
	public void setStatusTow(int statusTow) {
		this.statusTow = statusTow;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	
}
