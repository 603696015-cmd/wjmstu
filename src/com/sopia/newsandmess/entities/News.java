package com.sopia.newsandmess.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.StuffLib;

public class News {
	private int id;
	private String title;
	private String content;
	private ELUser owner;
	private NewsType ntype;
	private NewsStyle nstyle;
	private String mainimg;
	private Timestamp releasetime;
	private int hot ;

	private List<StuffLib> stuffs;//附件
	private Integer status;
	private java.sql.Date begintime;
	private java.sql.Date endtime;
	private int browsefor;
	//第2状态：status_tow
	//1.制作中(新创建)
	//2.初审中
	//3.初审未通过
	//4.终审等待中
	//5.终审未通过
	//6.已发布（开通）
	//7.删除等待中(不通过就还原成先前状态)
	private int status_tow;
	private int astatus_tow;
	private int ntid;
	public int getNtid() {
		return ntid;
	}

	public void setNtid(int ntid) {
		this.ntid = ntid;
	}

	public NewsStyle getNstyle() {
		return nstyle;
	}

	public void setNstyle(NewsStyle nstyle) {
		this.nstyle = nstyle;
	}

	public String getMainimg_() {
		return SystemConfOp.getStuffUrl() + mainimg;
	}
	
	public String getContent_() {
		return SystemConfOp.toStuffUrl(content);
	}

	public int getBrowsefor() {
		return browsefor;
	}
	public void setBrowsefor(int browsefor) {
		this.browsefor = browsefor;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public int getHot() {
		return hot;
	}
	public String getHotName() {
		switch (hot) {
		case ElConstants.HOT_PT:
			return "普通";
		case ElConstants.HOT_TJ:
			return "推荐";
		case ElConstants.HOT_RM:
			return "热门";
		case ElConstants.HOT_ZD:
			return "重点";
		case ElConstants.HOT_TT:
			return "头条";

		default:
			return "未知状态";
		}
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public News() {
	}
	public News(int id,String title) {
		this.id = id;
		this.title  = title;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ELUser getOwner() {
		return owner;
	}
	public void setOwner(ELUser owner) {
		this.owner = owner;
	}
	public Timestamp getReleasetime() {
		return releasetime;
	}
	public void setReleasetime(Timestamp releasetime) {
		this.releasetime = releasetime;
	}
	public NewsType getNtype() {
		return ntype;
	}
	public void setNtype(NewsType ntype) {
		this.ntype = ntype;
	}
	public String getMainimg() {//--//- 
		return mainimg;
	}
	public void setMainimg(String mainimg) {
		this.mainimg = mainimg;
	}
	public String getDescString() {
		if(null!=content) content=CheckHtml.getString(content) ;
		String s = (null==content||"".equals(content.trim()))? "无简介~":(content.length()>160? content.substring(0,160)+"...":content);
		return s;
	}
	public int getStatus_tow() {
		return status_tow;
	}
	//1.制作中(新创建)
	//2.初审中
	//3.初审未通过
	//4.终审等待中
	//5.终审未通过
	//6.已发布（开通）
	//7.删除等待中(不通过就还原成先前状态)
	public String getStatus_tow_() {
		switch (status_tow) {
		case 1:
			return "制作中";
		case 2:
			//return "初审中";
			return "申请等待中";
		case 3:
			//return "初审未通过";
			return "待修改";
		case 4:
			//return "终审等待中";
			return "审核等待中";
		case 5:
			//return "终审未通过";
			return "审核不通过";
		case 6:
			return "已发布";
		case 7:
			return "删除等待中";
		default:
			return "未知状态";
		}
	}
	public void setStatus_tow(int status_tow) {
		this.status_tow = status_tow;
	}
	public int getAstatus_tow() {
		return astatus_tow;
	}
	public void setAstatus_tow(int astatus_tow) {
		this.astatus_tow = astatus_tow;
	}

	public List<StuffLib> getStuffs() {
		return stuffs;
	}

	public void setStuffs(List<StuffLib> stuffs) {
		this.stuffs = stuffs;
	}
	
}
