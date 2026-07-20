package com.sopia.forumman.entities;

import java.sql.Timestamp;

import com.sopia.ElConstants;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.ELUser;

public class Forum {
	private int id;
	private String title;
	private String description;
	private Timestamp createtime;
	private Timestamp modifytime;
	private ELUser creater;
	private ForumBlock fblock;
	private int hot;
	private int readtime;
	private int receipttime;
	private boolean valid;
	private java.sql.Date begintime;
	private java.sql.Date endtime;
	private float score;//得分
	
	
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public java.sql.Date getBegintime() {
		return begintime;
	}
	public void setBegintime(java.sql.Date begintime) {
		this.begintime = begintime;
	}
	public java.sql.Date getEndtime() {
		return endtime;
	}
	public void setEndtime(java.sql.Date endtime) {
		this.endtime = endtime;
	}
	public boolean getValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public Forum() {
		// TODO Auto-generated constructor stub
	}
	public Forum(int id,String title) {
		this.id = id;
		this.title =title;
	}
	
	public int getReceipttime() {
		return receipttime;
	}
	public void setReceipttime(int receipttime) {
		this.receipttime = receipttime;
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
	public String getDescription() {
		return description;
	}
	public String getDescription_() {
		return  SystemConfOp.toStuffUrl(description);
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getModifytime() {
		return modifytime;
	}
	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
	public ForumBlock getFblock() {
		return fblock;
	}
	public void setFblock(ForumBlock fblock) {
		this.fblock = fblock;
	}
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public String getHotName(){
		if(hot==ElConstants.HOT_TJ) return "精华";
		return "普通";
	}
	public int getReadtime() {
		return readtime;
	}
	public void setReadtime(int readtime) {
		this.readtime = readtime;
	}
	
}
