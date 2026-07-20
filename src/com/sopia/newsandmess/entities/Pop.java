package com.sopia.newsandmess.entities;
import java.sql.Timestamp;
import java.util.Date;

import com.sopia.duman.entities.ELUser;

public class Pop {
	private int id;
	private String popTitle;	
	private String popContent;
	private Timestamp createtime;		//发布时间
	private ELUser create;				//创建人(发送人)
	private int sendmanner;				//弹窗发送的方式(按人员，按部门，按考场，按培训班)
	private String sendvalue;			//发送方式所关联的值
	private int status;					//状态(0:取消 1:有效)
	private String sendvalueName;		//发送方式所关联的值的名称
	public String getSendvalueName() {
		return sendvalueName;
	}
	public void setSendvalueName(String sendvalueName) {
		this.sendvalueName = sendvalueName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPopTitle() {
		return popTitle;
	}
	public void setPopTitle(String popTitle) {
		this.popTitle = popTitle;
	}
	public String getPopContent() {
		return popContent;
	}
	public void setPopContent(String popContent) {
		this.popContent = popContent;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public ELUser getCreate() {
		return create;
	}
	public void setCreate(ELUser create) {
		this.create = create;
	}
	public int getSendmanner() {
		return sendmanner;
	}
	public String getSendmannerName() {
		switch (sendmanner) {
		case 0:
			return "按人员";
		case 1:
			return "按部门";
		case 2:
			return "按考场";
		case 3:
			return "按培训班";
		default:
			return "未知";
		}
	}
	public void setSendmanner(int sendmanner) {
		this.sendmanner = sendmanner;
	}
	public String getSendvalue() {
		return sendvalue;
	}
	public void setSendvalue(String sendvalue) {
		this.sendvalue = sendvalue;
	}
	public int getStatus() {
		return status;
	}
	public String getStatusName() {
		switch (status) {
		case 0:
			return "无效";
		case 1:
			return "有效";
		default:
			return "未知";
		}
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
