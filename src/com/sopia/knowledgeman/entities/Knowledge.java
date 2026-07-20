package com.sopia.knowledgeman.entities;

import java.util.Date;
import java.util.List;

import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.StuffLib;

public class Knowledge {
	private int id;
	private String title;
	private String content;
	private ELUser owner;
	private Date createtime;
	private Date modifytime;
	private KnowledgeType kltype;
	private String mainimg;
	private String wendang;
	private int hot ;
	private int readtime ;
	private boolean valid ;
	private List<StuffLib> stuffs;
	private java.sql.Date begintime;
	private java.sql.Date endtime;
	private int status;
	private int award;//奖    推荐指数
	private int score; //得分
	private float scoreF; //得分
	private int counts;
	private String swf;//在线预览文件路径
	private String name;
	private int fromchange;//文档在线预览是否来自转换（swf）
	
	public int getFromchange() {
		return fromchange;
	}
	public void setFromchange(int fromchange) {
		this.fromchange = fromchange;
	}
	public String getSwf() {
		return swf;
	}
	public void setSwf(String swf) {
		this.swf = swf;
	}
	
	public String getSwf_() {
		return  SystemConfOp.getStuffUrl()+swf;
	}
	public int getAward() {
		return award;
	}
	public void setAward(int award) {
		this.award = award;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public float getScoreF() {
		return scoreF;
	}
	public void setScoreF(float scoreF) {
		this.scoreF = scoreF;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public int getStatus() {
		return status;
	}
	public String getStatusName() {
		switch (status) {
		case 1:
			return "已发布";
		case 0:
			return "未审核";
		default:
			break;
		}
		return "未知";
	}
	public void setStatus(int status) {
		this.status = status;
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
	public List<StuffLib> getStuffs() {
		return stuffs;
	}
	public void setStuffs(List<StuffLib> stuffs) {
		this.stuffs = stuffs;
	}
	public boolean getValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public int getReadtime() {
		return readtime;
	}
	public void setReadtime(int readtime) {
		this.readtime = readtime;
	}
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public Knowledge() {
	}
	public Knowledge(int id ,String title,String content,String mainimg) {
		this.id = id;
		this.title = title;
		this.content =content;
		this.mainimg =mainimg;
	}

	public Knowledge(int id ,String title) {
		this.id = id;
		this.title = title; 
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
		this.title = title.trim().equals(",")?"":title;
	}
	public String getShotTitle(){
		
		return title==null?"":(title.length()>18?title.substring(0,16)+"...":title);
	}
	public String getContent() {
		return content;
	}
	public String getContent_() {
		return  SystemConfOp.toStuffUrl(content);
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
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	public KnowledgeType getKltype() {
		return kltype;
	}
	public void setKltype(KnowledgeType kltype) {
		this.kltype = kltype;
	}
	public String getDescString() {
		if(null!=content) content=CheckHtml.getString(content) ;
		String s = (null==content||"".equals(content.trim()))? "无简介~":(content.length()>160? content.substring(0,160)+"...":content);
		return s;
	}
	public String getHotName(){
		if(hot==ElConstants.HOT_RM) return "热门";
		if(hot==ElConstants.HOT_TJ) return "推荐";
		if(hot==ElConstants.HOT_ZD) return "重点";
		return "普通";
	}
	public String getMainimg() {
		return mainimg;
	}
	public String getMainimg_() {
		return  SystemConfOp.getStuffUrl()+mainimg;
	}
	public void setMainimg(String mainimg) {
		this.mainimg = mainimg;
	}
	public String getWendang() {
		return wendang;
	}
	public void setWendang(String wendang) {
		this.wendang = wendang;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
