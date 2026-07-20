package com.sopia.bookinfo.entities;

import java.sql.Timestamp;

import oracle.sql.BLOB;

import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.ELUser;
/**
 * 图书类
 * @author Administrator
 *
 */
public class Bookinfo {
	private Integer id ;
	private ELUser user;
	private Integer userid;//用户编号
	private Integer typeid;//图书类型id
	private Integer version;//版次
	private Integer format;//开本
	private Integer page;//页数
	private Integer words;//字数
	private Integer click;//点击
	private Integer recommend;//推荐
	private Integer statuse;//状态 1 未通过 2 审核通过3 作废
	private String  author;//作者
	private String  name;//书名
	private String  paper;//纸张材质
	private String  spackage;//包装类型
	private String  readurl;//阅读网址
	private String  press;//出版社
	private String  picture;//封面图片
	private Timestamp  upddate;//修改日期
	private Timestamp  pressdate;//出版时间
	private Timestamp  printdate;//印刷时间
	private Timestamp  release;//发布日期
	private String  authorinfo;//作者简介
	private String  bookinfo;//图书简介
	private String  directoryinfo;//图书目录
	private Float  marketprice;//市场价
	private Float  vipprice;//会员价
	private BookType bookType;//
	private String  dename;//部门名称
	public String getStatuseName(){
		if(this.statuse==1) return "未通过";
		if(this.statuse==2) return "通过";
		else return "作废";
	}
	
	public String getMainimg_() {
		if(picture!=null&&(picture.indexOf("http://")==0||picture.indexOf("https://")==0))
			return picture;
		return  SystemConfOp.getStuffUrl()+picture;
	}
	public String getRecommendname(){
		
		if(this.recommend==1) return "未推荐";
		else return "推荐";
	}
	public String getDename() {
		return dename;
	}
	public void setDename(String dename) {
		this.dename = dename;
	}
	public BookType getBookType() {
		return bookType;
	}
	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getFormat() {
		return format;
	}
	public void setFormat(Integer format) {
		this.format = format;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getWords() {
		return words;
	}
	public void setWords(Integer words) {
		this.words = words;
	}
	public Integer getClick() {
		return click;
	}
	public void setClick(Integer click) {
		this.click = click;
	}
	public Integer getRecommend() {
		return recommend;
	}
	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}
	public Integer getStatuse() {
		return statuse;
	}
	public void setStatuse(Integer statuse) {
		this.statuse = statuse;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	public String getSpackage() {
		return spackage;
	}
	public void setSpackage(String spackage) {
		this.spackage = spackage;
	}
	public String getReadurl() {
		return readurl;
	}
	public void setReadurl(String readurl) {
		this.readurl = readurl;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Timestamp getUpddate() {
		return upddate;
	}
	public void setUpddate(Timestamp upddate) {
		this.upddate = upddate;
	}
	public Timestamp getPressdate() {
		return pressdate;
	}
	public void setPressdate(Timestamp pressdate) {
		this.pressdate = pressdate;
	}
	public Timestamp getPrintdate() {
		return printdate;
	}
	public void setPrintdate(Timestamp printdate) {
		this.printdate = printdate;
	}
	public Timestamp getRelease() {
		return release;
	}
	public void setRelease(Timestamp release) {
		this.release = release;
	}
	public String getAuthorinfo() {
		return authorinfo;
	}
	public void setAuthorinfo(String authorinfo) {
		this.authorinfo = authorinfo;
	}
	public String getBookinfo() {
		return bookinfo;
	}
	public void setBookinfo(String bookinfo) {
		this.bookinfo = bookinfo;
	}
	public String getDirectoryinfo() {
		return directoryinfo;
	}
	public void setDirectoryinfo(String directoryinfo) {
		this.directoryinfo = directoryinfo;
	}
	public float getMarketprice() {
		return marketprice;
	}
	public void setMarketprice(Float marketprice) {
		this.marketprice = marketprice;
	}
	public float getVipprice() {
		return vipprice;
	}
	public void setVipprice(Float vipprice) {
		this.vipprice = vipprice;
	}
	
	
	
}
