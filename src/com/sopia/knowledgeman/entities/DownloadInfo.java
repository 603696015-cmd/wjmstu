package com.sopia.knowledgeman.entities;

import java.util.Date;
 
 
public class DownloadInfo {
	private int id; // 主键id
	private int type; // 类别
	private int typeid;// 类别id 假如是knowledge 下载信息。 那么这就是knowledge  id
	private int userid; // 下载人信息 
	private int lognumber; // 当天下载次数
	private int isaddcent; // 是否加分
	private String downloadFileName;//下载文件名
	private Date downloadTime; //下载时间  
	

	public String getHotName(){
		if(type == 1) return "knowledge"; 
		return "未定义";
	} 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	public Date getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}
	public int getLognumber() {
		return lognumber;
	}
	public void setLognumber(int lognumber) {
		this.lognumber = lognumber;
	}
	public int getIsaddcent() {
		return isaddcent;
	}
	public void setIsaddcent(int isaddcent) {
		this.isaddcent = isaddcent;
	}
}
