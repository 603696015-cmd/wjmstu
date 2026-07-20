package com.sopia.bookman.entities;

import java.sql.Timestamp;

import com.sopia.common.CheckHtml;
import com.sopia.duman.entities.ELUser;

public class Book {
	private int id;
	private String title;
	private String pubhouse;//出版社
	private String content;//内容简介
	private String writer;//作者
	private ELUser owner;//推荐者
	private BookType ntype;
	private String mainimg;//封面图片
	private Timestamp releasetime;//推荐时间、
	private Timestamp modifytime;//修改时间
	private Timestamp pubtime;//出版时间
	private int hot ;
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	//、、、、、、
	public Book() {
	}
	public Book(int id,String title) {
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
	public BookType getNtype() {
		return ntype;
	}
	public void setNtype(BookType ntype) {
		this.ntype = ntype;
	}
	public String getMainimg() {
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
	public String getPubhouse() {
		return pubhouse;
	}
	public void setPubhouse(String pubhouse) {
		this.pubhouse = pubhouse;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Timestamp getModifytime() {
		return modifytime;
	}
	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}
	public Timestamp getPubtime() {
		return pubtime;
	}
	public void setPubtime(Timestamp pubtime) {
		this.pubtime = pubtime;
	}
	
}
