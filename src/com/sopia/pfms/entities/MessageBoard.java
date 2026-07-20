package com.sopia.pfms.entities;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;


public class MessageBoard {
	
	private int id ;//评论编号
	private int userid  ;//评论者id
	private int status ;//评论状态
	private int shopid ;//店铺id==被评论者id
	private int productId;//被评论的商品编号
	private int commentpoint ;//星级
	private Timestamp commentdate;//评论时间
	private String content ;//评论内容
	private int one;//一星
	private int two;//二星
	private int three;//三星
	private int four;//四星
	private int five;//五星
	private ELUser user;//用户
	private float  avg;//平均分
	private int    count;//评论总数
	private int type ;//评论类型
	
	private PfmsUser pfmsUser;//评论者
	
	
	
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public PfmsUser getPfmsUser() {
		return pfmsUser;
	}
	public void setPfmsUser(PfmsUser pfmsUser) {
		this.pfmsUser = pfmsUser;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public int getOne() {
		return one;
	}
	public void setOne(int one) {
		this.one = one;
	}
	public int getTwo() {
		return two;
	}
	public void setTwo(int two) {
		this.two = two;
	}
	public int getThree() {
		return three;
	}
	public void setThree(int three) {
		this.three = three;
	}
	public int getFour() {
		return four;
	}
	public void setFour(int four) {
		this.four = four;
	}
	public int getFive() {
		return five;
	}
	public void setFive(int five) {
		this.five = five;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getShopid() {
		return shopid;
	}
	public void setShopid(int shopid) {
		this.shopid = shopid;
	}
	public int getCommentpoint() {
		return commentpoint;
	}
	public void setCommentpoint(int commentpoint) {
		this.commentpoint = commentpoint;
	}
	public Timestamp getCommentdate() {
		return commentdate;
	}
	public void setCommentdate(Timestamp commentdate) {
		this.commentdate = commentdate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
