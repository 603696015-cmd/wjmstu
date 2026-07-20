package com.sopia.courseman.entities;
  
import java.sql.Timestamp;

import com.sopia.duman.entities.ELUser; 
 
public class ExamRoomAuditDescribes {
	private int id;  
	private ExamRoom examroom;  
	private ELUser user;  
	private Timestamp subimttime;  
	private Timestamp feedbacktime;  
	private String title;  
	private int status;  
	private String content;  
	private String replycontent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamRoom getExamroom() {
		return examroom;
	}
	public void setExamroom(ExamRoom examroom) {
		this.examroom = examroom;
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public Timestamp getSubimttime() {
		return subimttime;
	}
	public void setSubimttime(Timestamp subimttime) {
		this.subimttime = subimttime;
	}
	public Timestamp getFeedbacktime() {
		return feedbacktime;
	}
	public void setFeedbacktime(Timestamp feedbacktime) {
		this.feedbacktime = feedbacktime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReplycontent() {
		return replycontent;
	}
	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	} 
	
}
