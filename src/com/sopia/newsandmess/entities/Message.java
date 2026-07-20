package com.sopia.newsandmess.entities;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.StuffLib;

public class Message {
	private int mess_id;
	private String mess_title;	
	private String mess_content;
	private boolean is_read;	
	private Date mess_time ;
	private ELUser mess_from;	
	private ELUser mess_to;
	private int auditType;
	private int isreply;//回复状态
	private int sendmanner;//发送方式
	private String[] emailFile;//邮件群发的附件路径
	private String[] emailFilename;//邮件群发的附件名
	private List<StuffLib> stuffs;//附件
	private int forumid;//帖子回复 查看
	
	public List<StuffLib> getStuffs() {
		return stuffs;
	}
	public void setStuffs(List<StuffLib> stuffs) {
		this.stuffs = stuffs;
	}
	public String[] getEmailFilename() {
		return emailFilename;
	}
	public void setEmailFilename(String[] emailFilename) {
		this.emailFilename = emailFilename;
	}
	public String[] getEmailFile() {
		return emailFile;
	}
	public void setEmailFile(String[] emailFile) {
		this.emailFile = emailFile;
	}
	public int getSendmanner() {
		return sendmanner;
	}
	public void setSendmanner(int sendmanner) {
		this.sendmanner = sendmanner;
	}
	public int getAuditType() {
		return auditType;
	}
	public String getAuditName() {
		return mess_content.substring(mess_content.indexOf("[")+1, mess_content.indexOf("]"));
	}
	public void setAuditType(int auditType) {
		this.auditType = auditType;
	}
	public int getMess_id() {
		return mess_id;
	}
	public void setMess_id(int mess_id) {
		this.mess_id = mess_id;
	}
	public String getMess_title() {
		return mess_title;
	}
	public void setMess_title(String mess_title) {
		this.mess_title = mess_title;
	}
	public String getMess_content() {
		return mess_content;
	}
	public void setMess_content(String mess_content) {
		this.mess_content = mess_content;
	}
	public boolean getIs_read() {
		return is_read;
	}
	public void setIs_read(boolean is_read) {
		this.is_read = is_read;
	}
	public Date getMess_time() {
		return mess_time;
	}
	public String getMess_timeFmt() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(mess_time);
	}
	public void setMess_time(Date mess_time) {
		this.mess_time = mess_time;
	}
	public ELUser getMess_from() {
		return mess_from;
	}
	public void setMess_from(ELUser mess_from) {
		this.mess_from = mess_from;
	}
	public ELUser getMess_to() {
		return mess_to;
	}
	public void setMess_to(ELUser mess_to) {
		this.mess_to = mess_to;
	}
	public int getIsreply() {
		return isreply;
	}
	public String getIsreplyName() {
		if(isreply==1){
			return "已回复";
		}
		return "未回复";
		
	}
	public void setIsreply(int isreply) {
		this.isreply = isreply;
	}
	public int getForumid() {
		return forumid;
	}
	public void setForumid(int forumid) {
		this.forumid = forumid;
	}
	
	
}
