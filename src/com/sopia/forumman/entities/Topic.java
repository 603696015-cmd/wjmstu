package com.sopia.forumman.entities;

import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser;

public class Topic {
	private int id;
//	private String title;
	private String content;
	private Forum forum ;
//	private Topic topic;
	private ELUser creater;
	private Timestamp createtime;
	private int valid;
	private int disvalid;
	public int getDisvalid() {
		return disvalid;
	}
	public void setDisvalid(int disvalid) {
		this.disvalid = disvalid;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public Topic() {
	}
	public Topic(int id,String content) {
		this.id = id;
		this.content = content;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Forum getForum() {
		return forum;
	}
	public void setForum(Forum forum) {
		this.forum = forum;
	}
//	public Topic getTopic() {
//		return topic;
//	}
//	public void setTopic(Topic topic) {
//		this.topic = topic;
//	}
//	public ELUser getOwner() {
//		return owner;
//	}
//	public void setOwner(ELUser owner) {
//		this.owner = owner;
//	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}

	
}
