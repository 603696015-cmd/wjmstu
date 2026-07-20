package com.sopia.courseman.entities;

import java.sql.Blob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.CourseConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.openmeetings.Rooms;
import com.sopia.peice.entities.Peice;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyPractice;

public class Course {
	private static final Log logger = LogFactory.getLog(Course.class);
	private int id;
	private String name;
	private CourseType ctype;
	private ELUser creater;
	private String description;
//	private float passgrade;
	private int credit;
	private int defalutcredit;
	private int status; 
	private int astatus; 
	private Timestamp createtime;
	private Timestamp modifytime;
	private MyExamPaper myExamPaper;
	private CourseAuditDescribes courseAudit;
	private String mainimg;
	private int islink;
	private String exurl;
	private int during ;
	private int querytime;
	private String teacherinfo ; 
	private String teacherName;
	private String examName;
	private int teacherId;
	private String kj_appendix;
	private String jy_appendix;
	private int creditmod;//0学习完获得，1 进度x学分
	private int notenumber;
	private Timestamp notedate;
	private int cpagesize ;
	private String studyplan ;
	private int hot ;
	private int userCount;
	private int userPassedCount;
	//冗余属性，用于培训班设置学分时用 add by luocw
	private int suggestcredit;	//建议学分
	private int setcredit;		//设置学分
	private int getcredit;		//学分获得方式
	
	private int valid;//申请课程状态
	private String validText;//申请课程状态名称
	private int courseCss;//课程样式。  1选修  0必修
	
	private Department department;
	
	private List<MyPractice> myPracs;
	private boolean userInCourse;
	private Rooms room;
	private ExamRoom eroom;
	private ELUser owner;
	private Timestamp roomstart;
	private Timestamp roomend;
	private java.sql.Date begintime;
	private java.sql.Date endtime;
	/**SCORM课件ID**/
	private String scormId;
	private int xx_status;
	
	private int classid;//课程所属的培训班
	private String className;
	private int isDel;
	private int type;//必修为0，选修为1
	

	private int isApplication;//是否可申请
	private CourseRegistration coRegistration;//是否可申请
	private int isuserApp;//该学员是否可申请
	private String isjoin;//是否参加该课程
	private int joinway;//加入课程方式（报名1，分配1）

	private int isPastDue;//课程是否已过期
	private String explain;//未通过说明
	private int courseForm;//课程格式
	private int isLogout;//判断是否要注销
	
	private String shihegangwei;//适合岗位
	private String zhuanyeleibie;//专业类别
	private String zhuanyejibie;//专业级别
	private String shihebumen;//适合部门
	private String neirongleixing;//内容类型
	private String peixunleibie;//培训类别
	private String shihexuewei;//适合学位
	private String kechengxingzhi;//课程性质
	private String lecturerMainimg;//讲师图片
	private Map<String,String> statusMap;
	private String status_type;
	
	//周攀
	private int peice;//课程价格状态
	private Peice price;//课程价格
	
	
	private int jieye;
	
	private String weidu;//维度
	
	private int isLX;
	private float LX_score;
	private int isMK;
	private float MK_score;
	private int isBJ;
	private float BJ_score;
	
	private int orderid ; //学习顺序
	private int firstLearn;//先学后考
	private ExamRoom examRoom;
	
	//手机版课程地址
	private String html5;
	//是否需要审核
	private String isAudit;
	

	
	//对应论坛版块
	private int forumid;
	
	//20140418课程详情
	private String courseDetail;
	
	
	
	public String getCourseDetail() {
		return courseDetail;
	}
	public void setCourseDetail(String courseDetail) {
		this.courseDetail = courseDetail;
	}
	public String getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}
	public ExamRoom getExamRoom() {
		return examRoom;
	}
	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}
	
	public String getHtml5() {
		return html5;
	}
	public String getHtml5_() {
		if(html5!=null&&(html5.indexOf("http://")==0||html5.indexOf("https://")==0))
			return html5;
		return  SystemConfOp.getStuffUrl()+html5;
	}
	public void setHtml5(String html5) {
		this.html5 = html5;
	}
	public int getFirstLearn() {
		return firstLearn;
	}
	public void setFirstLearn(int firstLearn) {
		this.firstLearn = firstLearn;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public String getIsLXName() {
		if(isLX == 1)return "是"; 
		return "否";
	}
	public String getIsMKName() {
		if(isMK == 1)return "是"; 
		return "否";
	}
	public String getIsBJName() {
		if(isBJ == 1)return "是"; 
		return "否";
	}
	
	public int getIsLX() {
		return isLX;
	}
	public void setIsLX(int isLX) {
		this.isLX = isLX;
	}
	public float getLX_score() {
		return LX_score;
	}
	public void setLX_score(float lx_score) {
		LX_score = lx_score;
	}
	public int getIsMK() {
		return isMK;
	}
	public void setIsMK(int isMK) {
		this.isMK = isMK;
	}
	public float getMK_score() {
		return MK_score;
	}
	public void setMK_score(float mk_score) {
		MK_score = mk_score;
	}
	public int getIsBJ() {
		return isBJ;
	}
	public void setIsBJ(int isBJ) {
		this.isBJ = isBJ;
	}
	public float getBJ_score() {
		return BJ_score;
	}
	public void setBJ_score(float bj_score) {
		BJ_score = bj_score;
	}
	public String getWeidu() {
		return weidu;
	}

	public void setWeidu(String weidu) {
		this.weidu = weidu;
	}

	public String getClassIdName(){
		if (classid == -4)
			return "测评课程";
		else if (classid == -2)
			return "岗位必修";
		else if (classid == -3)
			return "岗位选修";
		else if (classid == 1)
			return "培训班";
		else if(classid == 0)
			return "单独分配";
		else
			return "其他";
	}

	public int getJieye() {
		return jieye;
	}

	public void setJieye(int jieye) {
		this.jieye = jieye;
	}

	public int getPeice() {
		return peice;
	}

	public void setPeice(int peice) {
		this.peice = peice;
	}

	public Peice getPrice() {
		return price;
	}

	public void setPrice(Peice price) {
		this.price = price;
	}

	public String getStatus_type() {
		return status_type;
	}

	public void setStatus_type(String status_type) {
		this.status_type = status_type;
	}

	public Map<String, String> getStatusMap() {
		statusMap = new HashMap<String,String>();
		
		statusMap.put(""+CourseConstants.COURSE_STATUS_INMAKING, "制作中");
		statusMap.put(""+CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT, "初审等待中");
		statusMap.put(""+CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_NOTGO, "初审不通过");
		statusMap.put(""+CourseConstants.COURSE_STATUS_FINAL_WAIT, "终审等待中");
		statusMap.put(""+CourseConstants.COURSE_STATUS_FINAL_NOTGO, "终审不通过");
		statusMap.put(""+CourseConstants.COURSE_STATUS_HASOPENED, "正常使用");
		statusMap.put(""+CourseConstants.COURSE_STATUS_ALTER_WAIT, "修改等待中");
		statusMap.put(""+CourseConstants.COURSE_STATUS_ALTER, "修改中");
		statusMap.put(""+CourseConstants.COURSE_STATUS_DELETE_WAIT, "删除等待中");
		statusMap.put(""+CourseConstants.COURSE_STATUS_DELETE, "作废");
		return statusMap;
	}
	
	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}
	public int getIsLogout() {
		return isLogout;
	}
	public void setIsLogout(int isLogout) {
		this.isLogout = isLogout;
	}
	public int getIsApplication() {
		return isApplication;
	}
	public void setIsApplication(int isApplication) {
		this.isApplication = isApplication;
	}
	public CourseRegistration getCoRegistration() {
		return coRegistration;
	}
	public void setCoRegistration(CourseRegistration coRegistration) {
		this.coRegistration = coRegistration;
	}
	public int getIsuserApp() {
		return isuserApp;
	}
	public void setIsuserApp(int isuserApp) {
		this.isuserApp = isuserApp;
	}
	public String getIsjoin() {
		return isjoin;
	}
	public void setIsjoin(String isjoin) {
		this.isjoin = isjoin;
	}
	public int getJoinway() {
		return joinway;
	}
	public void setJoinway(int joinway) {
		this.joinway = joinway;
	}
	public static int compareTo(String date1,String date2){ 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		int date = 0; 
		try { 
			Date d1 = sdf.parse(date1);
			Date d2 = sdf.parse(date2); 
			date = d1.compareTo(d2); 
		} catch (java.text.ParseException e) { 
			logger.error("日期转换错误",e);
		} 
		return date; 
	}  
	public int getIsPastDue() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
		Timestamp sys = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		String systime = df.format(sys);

		Timestamp Start = new Timestamp(this.coRegistration.getRegistrationStartTime().getTime());//开始时间 
		String StartTime = df.format(Start);
		Timestamp Stop = new Timestamp(this.coRegistration.getRegistrationStopTime().getTime());//结束时间 
		String StopTime = df.format(Stop);
		int StartCompare = compareTo(systime,StartTime);
		int StopCompare = compareTo(StopTime,systime); 
		boolean isStart = true;
		boolean isStop = true;
		if(StartCompare != -1){ 
			isStart = true;
		}else {
			isStart = false; 
		}
		if(StopCompare == 1) { 
			isStop = true;
		}else{
			isStop = false; 
		} 
		if(!isStart){
			isPastDue = 0;//时间未到
		}else if(!isStop){
			isPastDue = 2;//时间已过
		}else if(isStart && isStop){
			isPastDue = 1;//可以报名
		}
		return isPastDue;
	}
	public void setIsPastDue(int isPastDue) {
		this.isPastDue = isPastDue;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getXx_status() {
		return xx_status;
	}
	public void setXx_status(int xx_status) {
		this.xx_status = xx_status;
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
	public ELUser getOwner() {
		return owner;
	}
	public void setOwner(ELUser owner) {
		this.owner = owner;
	}
	public Rooms getRoom() {
		return room;
	}
	public void setRoom(Rooms room) {
		this.room = room;
	}
	public boolean getUserInCourse() {
		return userInCourse;
	}
	public void setUserInCourse(boolean userInCourse) {
		this.userInCourse = userInCourse;
	}
	public List<MyPractice> getMyPracs() {
		return myPracs;
	}
	public void setMyPracs(List<MyPractice> myPracs) {
		this.myPracs = myPracs;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public Course() {
	}
	public Course(int id) {
		this.id = id;
	}
	public Course(int id ,String name){
		this.id = id;
		this.name =name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name.trim().equals(",")?"":name;
	}
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public float getPassgrade() {
//		return passgrade;
//	}
//	public void setPassgrade(float passgrade) {
//		this.passgrade = passgrade;
//	}
// 
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getModifytime() {
		return modifytime;
	}
	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}
	public CourseType getCtype() {
		return ctype;
	}
	public void setCtype(CourseType ctype) {
		this.ctype = ctype;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getValidName() {
//		if(status==CourseConstants.COURSE_STATUS_OPEN) return "课程开通";
//		else if(status==CourseConstants.COURSE_STATUS_OPEN_WAIT) return "课程开通等待中";
//		else if(status==CourseConstants.COURSE_STATUS_DELETE) return "课程删除";
//		else if(status==CourseConstants.COURSE_STATUS_DELETE_WAIT) return "删除等待中";
//		else if(status==CourseConstants.COURSE_STATUS_DELETE_WAIT) return "审核等待中";
//		else if(status==CourseConstants.COURSE_STATUS_DISAPPROVE_WAIT) return "审核不批准";
//		else if(status==CourseConstants.COURSE_STATUS_DELETEDIS_WAIT) return "删除不批准";
//		else if(status==CourseConstants.COURSE_STATUS_APPLYFOR_ALTER) return "修改审核等待中";
//		else return "制作中";
		if(status==CourseConstants.COURSE_STATUS_INMAKING) return "制作中";
		else if(status==CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT) return "初审等待中";
		else if(status==CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_NOTGO) return "初审不通过";
		else if(status==CourseConstants.COURSE_STATUS_FINAL_WAIT) return "终审等待中";
		else if(status==CourseConstants.COURSE_STATUS_FINAL_NOTGO) return "终审不通过";
		else if(status==CourseConstants.COURSE_STATUS_HASOPENED) return "正常使用"; 
		else if(status==CourseConstants.COURSE_STATUS_ALTER_WAIT) return "修改等待中"; 
		else if(status==CourseConstants.COURSE_STATUS_ALTER) return "修改中";  
		else if(status==CourseConstants.COURSE_STATUS_DELETE_WAIT) return "删除等待中"; 
		else if(status==CourseConstants.COURSE_STATUS_DELETE) return "作废";   
		else return "未知类型"; 
	}
	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}
	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}
	public String getMainimg() {
		return mainimg;
	}
	public String getMainimg_() {
		if(mainimg!=null&&(mainimg.indexOf("http://")==0||mainimg.indexOf("https://")==0))
			return mainimg;
		return  SystemConfOp.getStuffUrl()+mainimg;
	}
	public void setMainimg(String mainimg) {
		this.mainimg = mainimg;
	}
	public String getDescString() {
		if(null!=description) description=CheckHtml.getString(description) ;
		String s = (null==description||"".equals(description.trim()))? "无简介~":(description.length()>160? description.substring(0,160)+"...":description);
		return s;
	}
	public int getUserPassedCount() {
		return userPassedCount;
	}
	public void setUserPassedCount(int userPassedCount) {
		this.userPassedCount = userPassedCount;
	}
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public String getHotName(){
		if(hot==ElConstants.HOT_RM) return "热门";
		if(hot==ElConstants.HOT_TJ) return "推荐";
		if(hot==ElConstants.HOT_ZD) return "重点";
		
		return "普通";
	}
	public int getIslink() {
		return islink;
	}
	public void setIslink(int islink) {
		this.islink = islink;
	}
	public String getIslinkName() {
		if(islink==0){
			return "标准课程";
		}
		if(islink==1){
			return "外部课程";
		}
		if(islink==2){
			return "组合式外部课程";
		}
		if(islink==3){
			return "单一视频课程";
		}
		if(islink==4){
			return "同步课程";
		}
		if(islink==5){
			return "Scorm1.2课程";
		}if(islink==6){
			return "线下课程";
		}
		return "未知类型课程";
	}
	public String getCreditmodName() {
		if(creditmod==0){
			return "学习完得学分";
		}
		if(creditmod==1){
			return "进度X学分";
		}
		return "未知方式";
	}
	public String getExurl() {
		return exurl;
	}
	public String getExurl_() {
		if(exurl!=null&&(exurl.indexOf("http://")==0||exurl.indexOf("https://")==0))
			return exurl;
		return  SystemConfOp.getStuffUrl()+exurl;
	}
	public void setExurl(String exurl) {
		this.exurl = exurl;
	}
	public int getDuring() {
		return during;
	}
	public void setDuring(int during) {
		this.during = during;
	}
	public int getDefalutcredit() {
		return defalutcredit;
	}
	public void setDefalutcredit(int defalutcredit) {
		this.defalutcredit = defalutcredit;
	}
	public int getQuerytime() {
		return querytime;
	}
	public void setQuerytime(int querytime) {
		this.querytime = querytime;
	}
	public String getTeacherinfo() {
		return teacherinfo;
	}
	public void setTeacherinfo(String teacherinfo) {
		this.teacherinfo = teacherinfo;
	}
	public String getStudyplan() {
		return studyplan;
	}
	public void setStudyplan(String studyplan) {
		this.studyplan = studyplan;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getKj_appendix() {
//		kj_appendix = kj_appendix==null ? "assist_plan_stuff_download.action?fileName=":(
//				kj_appendix.indexOf("//")<0?"assist_plan_stuff_download.action?fileName="+kj_appendix:kj_appendix);
		return kj_appendix;
	}
	public void setKj_appendix(String kj_appendix) {
		this.kj_appendix = kj_appendix;
	}
	public String getJy_appendix() {
//		jy_appendix = jy_appendix==null ? "assist_plan_stuff_download.action?fileName=":(
//				jy_appendix.indexOf("//")<0?"assist_plan_stuff_download.action?fileName="+jy_appendix:jy_appendix);
	return jy_appendix;
	}
	public void setJy_appendix(String jy_appendix) {
		this.jy_appendix = jy_appendix;
	}
	public int getCreditmod() {
		return creditmod;
	}
	public void setCreditmod(int creditmod) {
		this.creditmod = creditmod;
	}
	public int getCpagesize() {
		return cpagesize;
	}
	public void setCpagesize(int cpagesize) {
		this.cpagesize = cpagesize;
	}
	public int getNotenumber() {
		return notenumber;
	}
	public void setNotenumber(int notenumber) {
		this.notenumber = notenumber;
	}
	public Timestamp getNotedate() {
		return notedate;
	}
	public void setNotedate(Timestamp notedate) {
		this.notedate = notedate;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public int getSuggestcredit() {
		return suggestcredit;
	}
	public void setSuggestcredit(int suggestcredit) {
		this.suggestcredit = suggestcredit;
	}
	public int getSetcredit() {
		return setcredit;
	}
	public void setSetcredit(int setcredit) {
		this.setcredit = setcredit;
	}
	public int getGetcredit() {
		return getcredit;
	}
	public void setGetcredit(int getcredit) {
		this.getcredit = getcredit;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public String getValidText() {
		if(this.getValid()==1){
			validText="申请通过";
		}else if(this.getValid()==2){
			validText="申请不通过";
		}else if(this.getValid()==2){
			validText="已经申请";
		}
		return validText;
	}
	public void setValidText(String validText) {
		this.validText = validText;
	}
	public Timestamp getRoomstart() {
		return roomstart;
	}
	public void setRoomstart(Timestamp roomstart) {
		this.roomstart = roomstart;
	}
	public Timestamp getRoomend() {
		return roomend;
	}
	public void setRoomend(Timestamp roomend) {
		this.roomend = roomend;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public String getScormId() {
		return scormId;
	}
	public void setScormId(String scormId) {
		this.scormId = scormId;
	}
	public CourseAuditDescribes getCourseAudit() {
		return courseAudit;
	}
	public void setCourseAudit(CourseAuditDescribes courseAudit) {
		this.courseAudit = courseAudit;
	}
	public ExamRoom getEroom() {
		return eroom;
	}
	public void setEroom(ExamRoom eroom) {
		this.eroom = eroom;
	}
	public int getAstatus() {
		return astatus;
	}
	public void setAstatus(int astatus) {
		this.astatus = astatus;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public int getCourseForm() {
		return courseForm;
	}
	public void setCourseForm(int courseForm) {
		this.courseForm = courseForm;
	}

	public String[] getCourseForms(){ 
		return "其他==视频==图片==文本==音频==PPT==三分屏==Flash==综合".split("==");
	}  
	public String getCourseFormName() {
		if(courseForm == 0){
			return "其他";
		}else if(courseForm == 1){
			return "视频";
		}else if(courseForm == 2){
			return "图片";
		}else if(courseForm == 3){
			return "文本";
		}else if(courseForm == 4){
			return "音频";
		}else if(courseForm == 5){
			return "PPT";
		}else if(courseForm == 6){
			return "三分屏";
		}else if(courseForm == 7){
			return "Flash";
		}else if(courseForm == 8){
			return "综合";
		}else{
			return "未知";
		}
	}

	public String getShihegangwei() {
		return shihegangwei;
	}

	public void setShihegangwei(String shihegangwei) {
		this.shihegangwei = shihegangwei;
	}

	public String getZhuanyeleibie() {
		return zhuanyeleibie;
	}

	public void setZhuanyeleibie(String zhuanyeleibie) {
		this.zhuanyeleibie = zhuanyeleibie;
	}

	public String getZhuanyejibie() {
		return zhuanyejibie;
	}

	public void setZhuanyejibie(String zhuanyejibie) {
		this.zhuanyejibie = zhuanyejibie;
	}

	public String getShihebumen() {
		return shihebumen;
	}

	public void setShihebumen(String shihebumen) {
		this.shihebumen = shihebumen;
	}

	public String getNeirongleixing() {
		return neirongleixing;
	}

	public void setNeirongleixing(String neirongleixing) {
		this.neirongleixing = neirongleixing;
	}

	public String getPeixunleibie() {
		return peixunleibie;
	}

	public void setPeixunleibie(String peixunleibie) {
		this.peixunleibie = peixunleibie;
	}

	public String getShihexuewei() {
		return shihexuewei;
	}

	public void setShihexuewei(String shihexuewei) {
		this.shihexuewei = shihexuewei;
	}

	public String getKechengxingzhi() {
		return kechengxingzhi;
	}

	public void setKechengxingzhi(String kechengxingzhi) {
		this.kechengxingzhi = kechengxingzhi;
	}

	public int getCourseCss() {
		return courseCss;
	}

	public void setCourseCss(int courseCss) {
		this.courseCss = courseCss;
	}

	public String getLecturerMainimg() {
		return lecturerMainimg;
	}

	public void setLecturerMainimg(String lecturerMainimg) {
		this.lecturerMainimg = lecturerMainimg;
	}
	public int getForumid() {
		return forumid;
	}
	public void setForumid(int forumid) {
		this.forumid = forumid;
	}
	public static Log getLogger() {
		return logger;
	}  
}
