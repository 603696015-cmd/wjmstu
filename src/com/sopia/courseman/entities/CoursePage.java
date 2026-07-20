package com.sopia.courseman.entities;

import java.util.Date;
import java.util.List;


import com.sopia.common.SystemConfOp;
import com.sopia.courseman.CourseConstants;
import com.sopia.openmeetings.Rooms;
import com.sopia.questionman.entities.StuffLib;

public class CoursePage {
	private int id;
	private Course course;
	private String title;
	private int property;// 章节
	private int type;// 图文，讲义等
	private String file;
	private String page;
	private String pagesimple;
	private Date createtime;
	private Date modifytime;
	private int sortid;
	private int during;
	private int skipable;
	private String page_url;
	private String page_url_Encoder;
	private int queryTime;
	private int getcredit;// 结业方式：1 学完 2 考过 3 学完且考过
	private String identifier;
	private String during_s;
	private String prerequisites;

	private List<PracticePaper> pracPapers;
	private PracticePaper pracp;
	private List<ExamRoom> examRooms;
	private ExamRoom examRoom;
	private int islive; // 是否直播 1直播 0不直播
	private int isfree; // 是否免费 1免费 0收费
	private List<StuffLib> stuffs; // 附件
	private Rooms room;
	private String stufftitle;
	private String stuffaddr;
	private int rn;

	// html5修改
	private String html5;
	private String html5_;

	private String pic_g; // 闪动图
	private String pic_l; // 亮图
	private String pic_h; // 灰图
	
	private int isNull;//是否为空

	public int getIsNull() {
		return isNull;
	}

	public void setIsNull(int isNull) {
		this.isNull = isNull;
	}

	public String getPic_g() {
		if(pic_g == null){
			return "images/defaultPicG.gif";
		}else{
			if(pic_g.indexOf("http://")==0||pic_g.indexOf("https://")==0){
				return pic_g;
			}else{
				return  SystemConfOp.getStuffUrl()+pic_g;
			}
		}
	}

	public String getPic_l() {
		if(pic_l == null){
			return "images/defaultPicL.png";
		}else{
			if(pic_l.indexOf("http://")==0||pic_l.indexOf("https://")==0){
				return pic_l;
			}else{
				return  SystemConfOp.getStuffUrl()+pic_l;
			}
		}
	}

	public String getPic_h() {
		if(pic_h == null){
			return "images/defaultPicH.png";
		}else{
			if(pic_h.indexOf("http://")==0||pic_h.indexOf("https://")==0){
				return pic_h;
			}else{
				return  SystemConfOp.getStuffUrl()+pic_h;
			}
		}
	}


	public void setPic_g(String pic_g) {
		this.pic_g = pic_g;
	}


	public void setPic_l(String pic_l) {
		this.pic_l = pic_l;
	}


	public void setPic_h(String pic_h) {
		this.pic_h = pic_h;
	}

	public int getRn() {
		return rn;
	}

	public void setRn(int rn) {
		this.rn = rn;
	}

	public String getHtml5() {
		return html5;
	}

	public String getHtml5_() {
		String pageUrl = "";
		// 加密
//		BASE64Encoder b = new BASE64Encoder();
//		if (html5 != null
//				&& (html5.indexOf("http://") == 0 || html5.indexOf("https://") == 0)) {
//			pageUrl = html5;
//		} else {
//			pageUrl = SystemConfOp.getStuffUrl() + html5;
//		}
//		return b.encode(pageUrl.getBytes());
//	}
		return pageUrl;
	}

	public void setHtml5(String html5) {
		this.html5 = html5;
	}

	public String getStufftitle() {
		return stufftitle;
	}

	public void setStufftitle(String stufftitle) {
		this.stufftitle = stufftitle;
	}

	public String getStuffaddr() {
		return stuffaddr;
	}

	public void setStuffaddr(String stuffaddr) {
		this.stuffaddr = stuffaddr;
	}

	public Rooms getRoom() {
		return room;
	}

	public void setRoom(Rooms room) {
		this.room = room;
	}

	public List<StuffLib> getStuffs() {
		return stuffs;
	}

	public void setStuffs(List<StuffLib> stuffs) {
		this.stuffs = stuffs;
	}

	public int getIslive() {
		return islive;
	}

	public void setIslive(int islive) {
		this.islive = islive;
	}

	public int getIsfree() {
		return isfree;
	}

	public void setIsfree(int isfree) {
		this.isfree = isfree;
	}

	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}

	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public PracticePaper getPracp() {
		return pracp;
	}

	public void setPracp(PracticePaper pracp) {
		this.pracp = pracp;
	}

	public List<PracticePaper> getPracPapers() {
		return pracPapers;
	}

	public void setPracPapers(List<PracticePaper> pracPapers) {
		this.pracPapers = pracPapers;
	}

	public String getPage_url() {
		return page_url;
	}

	public String getPage_url_() {
		if (page_url != null
				&& (page_url.indexOf("http://") == 0 || page_url
						.indexOf("https://") == 0))
			return page_url;
		return SystemConfOp.getStuffUrl() + page_url;
	}

	public String getPage_url_Encoder() {
		String pageUrl = "";
		// 加密
//		BASE64Encoder b = new BASE64Encoder();
////		if (page_url != null
////				&& (page_url.indexOf("http://") == 0 || page_url
////						.indexOf("https://") == 0)) {
//			pageUrl = page_url;
////		} else {
////			pageUrl = SystemConfOp.getStuffUrl() + page_url;
////		}
//		return b.encode(pageUrl.getBytes());
		return pageUrl;
	}

	public void setPage_url(String page_url) {
		this.page_url = page_url;
	}

	public CoursePage() {
	}

	public CoursePage(int id) {
		this.id = id;
	}

	public CoursePage(int id, String title) {
		this.id = id;
		this.title = title;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public String getPropertyName() {
		if (property == CourseConstants.CPAGE_PROPERTY_Z)
			return "章";
		else
			return "节";
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeName() {
		if (type == CourseConstants.CPAGE_TYPE_CSP)
			return "纯视频";
		if (type == CourseConstants.CPAGE_TYPE_JYSP)
			return "讲义+视频";
		if (type == CourseConstants.CPAGE_TYPE_TW)
			return "图文+讲义";
		if (type == CourseConstants.CPAGE_TYPE_WB)
			return "外部课程";
		if (type == CourseConstants.CPAGE_TYPE_SPXX)
			return "视频学习";
		if (type == CourseConstants.CPAGE_TYPE_KPXX)
			return "宽频学习";
		if (type == CourseConstants.CPAGE_TYPE_WBKPXX)
			return "外部宽频学习";
		if (type == CourseConstants.CPAGE_TYPE_CHSPXX)
			return "词汇视频学习";
		// if (type == CourseConstants.CPAGE_TYPE_SPXX2)
		// return "视频学习2";
		return "未知类型";
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getPage() {
		return page;
	}

	public String getPage_() {

		return SystemConfOp.toStuffUrl(page);
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPagesimple() {
		return pagesimple;
	}

	public void setPagesimple(String pagesimple) {
		this.pagesimple = pagesimple;
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

	public int getSortid() {
		return sortid;
	}

	public void setSortid(int sortid) {
		this.sortid = sortid;
	}

	public int getDuring() {
		return during;
	}

	public void setDuring(int during) {
		this.during = during;
	}

	public int getSkipable() {
		return skipable;
	}

	public void setSkipable(int skipable) {
		this.skipable = skipable;
	}

	public int getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(int queryTime) {
		this.queryTime = queryTime;
	}

	public int getGetcredit() {
		return getcredit;
	}

	public String getGetcreditName() {
		if (this.getcredit == 1 || this.getcredit == 0) {
			return "学完";
		} else if (this.getcredit == 2) {
			return "考过";
		} else if (this.getcredit == 3) {
			return "学完且考过";
		} else {
			return "未知";
		}
	}

	public void setGetcredit(int getcredit) {
		this.getcredit = getcredit;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDuring_s() {
		return during_s;
	}

	public void setDuring_s(String during_s) {
		this.during_s = during_s;
	}

	public String getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

}
