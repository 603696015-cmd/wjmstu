package com.sopia.courseman.entities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;

public class ExamRoom {
	private static final Log logger = LogFactory.getLog(ExamRoom.class);
	private int id;
	private Course course;
	private ELUser creater;
	private String title;
	private String description;
	private String location;
	private Timestamp begintime;
	private Timestamp endtime;
	private int iscommon;
	private int userSize;
	private List<MyExamPaper> meps;
	private float passgrade;
	private float passgrade2;
	private MyExamPaper minMep;
	private MyExamPaper maxMep;
	private MyExamPaper myExamPaper;
	private int average ;
	private float score ;
	private EroomLib eroomLib;
	private List<ELUser> invigilators;//监考人员
	private List<ELUser> appraises;  //阅卷人员
	private List<ELUser> valids; //复核人员
	private List<ELUser> selectings; //选拔人员
	private ExamPaper examPaper;
	private List<ExamPaper> exampapers;
	private String mainimg;
//	private ExamPaper prac ;
//	private int practimes;
//	private int pracscore;
	private int type;
	private boolean hasuser;
	private int epsize;
	private int valid;//考场状态
	private int svalid;//选拨状态
	private int uvalid;//复核状态
	private int Usize;//复核人数
	private boolean isUvalid;//复核状态
	private int avalid;//1申请修改, 0修改通过,2修改不通过
	private List<MyRoom> myrooms;
	private int usersize;
	private int joinusersize;//参加人数
	private int LOEusersize;//缺考人数 
	private float avgscorejoin;//参考人员平均分 
	private float avgscore;//全部人员平均分的平均分
	private int passsize;//及格人数
	private int pass9_ ;//90分以上
	private int pass8_9;//	80-90分
	private int pass7_8;//70-80
	private int pass6_7;//	60-70
	private float pass_6_p;//60以下
	private float pass9__p ;//90分以上
	private float pass8_9_p;//	80-90分
	private float pass7_8_p;//70-80
	private float pass6_7_p;//	60-70
	private int pass_6;//60以下
	private int pass5_6;//50-60分
	private int pass4_5;//40-50分
	private int pass3_4;//30-40分
	private int pass2_3;//20-30分
	private int pass1_2;//10-20分
	private int pass0_1;//0-10分
	private List<String> supervisorrealname;//监考老师
	private ELClassRegistration elRegistration;
	private ExamRoomAuditDescribes auditdescribes;
	private int examcount;
	private int markingManner;
	private int passmanner;//记录方式
	private int queryManner;//查询方式 1.按考场  2.按考场试卷
	
	private int isMacBand;
	private int isIpLimit;
	private String ipStart;
	private String ipEnd;
	
	private int isBand;
	private int bandClassid;
	private String bandClassName;
	private int classid; 
	private ElClass elclass;
	private int isnormal;
	private boolean isUserid;
	private int isApplication;//是否可申请
	private EroomRegistration erRegistration;//是否可申请
	private int isuserApp;//该学员是否可申请
	private String isjoin;//是否参加该培训班
	private int joinway;//加入考场方式（报名1，分配1）

	private int isPastDue;//培训班是否已过期
	private String explain;//未通过说明
	
	private int planNumber;//可申请计划招收人数
	private int sqlw;//查询条件，如果是9查询已删除的考场
	private String eroomIds;
	private String depName;
	private String jingzhong;
	private String pwd;//考场密码
	private int pwdneed;
	private Timestamp pwdtime; 
	private int cacheepsize ;//试卷缓存-1不缓存，
	private int cacheeprefresh;//密码更改时刷新。
	private int epqsort;//考试中试卷的试题是否随机排序
	private ELUser appr_header;
	private int isPass;//考场是否通过
	private int isPassed;//考试分数是否通过
	
	private int firstLearnLaterExam;//先学后考
	private int standardLine;//智能辅导分达标线
	private int autoAssign;//自动分配
	private int cpid;		//章节id
	private int sortid;		//章节考场排序
	private int canExam;	//章节是否可以考试
	private String percent;
	
	private CoursePage cpage;
	private int islink;	//考场类型
//	private int ertype;
	
	//卫生局1015修改
	private int isxianzhikaopin;//是否限制考频次
	private int examsforday;//每天考试次数
	private double jiangeshijian;//每次考试建个时间
	private int eroomid;

//	public int getErtype() {
//		return ertype;
//	}
//	public void setErtype(int ertype) {
//		this.ertype = ertype;
//	}
	 
	private int stuViewResult;//是否允许查询结果（调查问卷）   1 允许 0 不允许
	
	
	public int getExamsforday() {
		return examsforday;
	}
	public void setExamsforday(int examsforday) {
		this.examsforday = examsforday;
	}
	public double getJiangeshijian() {
		return jiangeshijian;
	}
	public void setJiangeshijian(double jiangeshijian) {
		this.jiangeshijian = jiangeshijian;
	}
	public int getIsxianzhikaopin() {
		return isxianzhikaopin;
	}
	public void setIsxianzhikaopin(int isxianzhikaopin) {
		this.isxianzhikaopin = isxianzhikaopin;
	}
	public int getIslink() {
		return islink;
	}
	public void setIslink(int islink) {
		this.islink = islink;
	}
	public int getIsPassed() {
		return isPassed;
	}
	public void setIsPassed(int isPassed) {
		this.isPassed = isPassed;
	}
	/**
	 * @return the appr_header
	 */
	public ELUser getAppr_header() {
		return appr_header;
	}
	/**
	 * @param appr_header the appr_header to set
	 */
	public void setAppr_header(ELUser appr_header) {
		this.appr_header = appr_header;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getPwdneed() {
		return pwdneed;
	}
	public void setPwdneed(int pwdneed) {
		this.pwdneed = pwdneed;
	}
	public Timestamp getPwdtime() {
		return pwdtime;
	}
	public void setPwdtime(Timestamp pwdtime) {
		this.pwdtime = pwdtime;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getJingzhong() {
		return jingzhong;
	}
	public void setJingzhong(String jingzhong) {
		this.jingzhong = jingzhong;
	}
	public String getEroomIds() {
		return eroomIds;
	}
	public void setEroomIds(String eroomIds) {
		this.eroomIds = eroomIds;
	}
	public int getQueryManner() {
		return queryManner;
	}
	public void setQueryManner(int queryManner) {
		this.queryManner = queryManner;
	}
	public int getPlanNumber() {
		return planNumber;
	}
	public void setPlanNumber(int planNumber) {
		this.planNumber = planNumber;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	} 
	public static int compareTo(String date1,String date2){ 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
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
		/*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
		Timestamp sys = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		String systime = df.format(sys);

		Timestamp Start = new Timestamp(this.erRegistration.getRegistrationStartTime().getTime());//开始时间 
		String StartTime = df.format(Start);
		Timestamp Stop = new Timestamp(this.erRegistration.getRegistrationStopTime().getTime());//结束时间 
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
		} */
		Date d = new Date(); 
		boolean isStart = !d.before(erRegistration.getRegistrationStartTime());
		boolean isStop = !d.after(erRegistration.getRegistrationStopTime());;
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
	public int getIsApplication() {
		return isApplication;
	}
	public void setIsApplication(int isApplication) {
		this.isApplication = isApplication;
	}
	public int getIsnormal() {
		return isnormal;
	}
	public void setIsnormal(int isnormal) {
		this.isnormal = isnormal;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public String getBandClassName() {
		return bandClassName;
	}
	public void setBandClassName(String bandClassName) {
		this.bandClassName = bandClassName;
	}
	public int getIsBand() {
		return isBand;
	}
	public void setIsBand(int isBand) {
		this.isBand = isBand;
	}
	public int getBandClassid() {
		return bandClassid;
	}
	public void setBandClassid(int bandClassid) {
		this.bandClassid = bandClassid;
	}
	public int getIsMacBand() {
		return isMacBand;
	}
	public void setIsMacBand(int isMacBand) {
		this.isMacBand = isMacBand;
	}
	public int getIsIpLimit() {
		return isIpLimit;
	}
	public void setIsIpLimit(int isIpLimit) {
		this.isIpLimit = isIpLimit;
	}
	public String getIpStart() {
		return ipStart;
	}
	public void setIpStart(String ipStart) {
		this.ipStart = ipStart;
	}
	public String getIpEnd() {
		return ipEnd;
	}
	public void setIpEnd(String ipEnd) {
		this.ipEnd = ipEnd;
	}
	public ExamRoomAuditDescribes getAuditdescribes() {
		return auditdescribes;
	}
	public void setAuditdescribes(ExamRoomAuditDescribes auditdescribes) {
		this.auditdescribes = auditdescribes;
	}
	public List<String> getSupervisorrealname() {
		return supervisorrealname;
	}
	public void setSupervisorrealname(List<String> supervisorrealname) {
		this.supervisorrealname = supervisorrealname;
	}
	public int getUsersize() {
		return usersize;
	}
	public void setUsersize(int usersize) {
		this.usersize = usersize;
	}  
	public int getPasssize() {
		return passsize;
	}
	public void setPasssize(int passsize) {
		this.passsize = passsize;
	}
	public int getPass9_() {
		return pass9_;
	}
	public void setPass9_(int pass9_) {
		this.pass9_ = pass9_;
	}
	public int getPass8_9() {
		return pass8_9;
	}
	public void setPass8_9(int pass8_9) {
		this.pass8_9 = pass8_9;
	}
	public int getPass7_8() {
		return pass7_8;
	}
	public void setPass7_8(int pass7_8) {
		this.pass7_8 = pass7_8;
	}
	public int getPass6_7() {
		return pass6_7;
	}
	public void setPass6_7(int pass6_7) {
		this.pass6_7 = pass6_7;
	}
	public int getPass_6() {
		return pass_6;
	}
	public void setPass_6(int pass_6) {
		this.pass_6 = pass_6;
	}
	public List<MyRoom> getMyrooms() {
		return myrooms;
	}
	public void setMyrooms(List<MyRoom> myrooms) {
		this.myrooms = myrooms;	
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) { 
		this.valid = valid;
	}
	public String getValidName() {
		if(valid==0) return "制作中";
//		else if(valid==1) return "初审等待中";
//		else if(valid==2) return "初审不通过";
//		else if(valid==3) return "终审等待中";
//		else if(valid==4) return "终审不通过";
		else if(valid==1) return "申请等待中";
		else if(valid==2) return "待修改";
		else if(valid==3) return "审核等待中";
		else if(valid==4) return "审核不通过";
		else if(valid==5) return "已开通"; 
		else if(valid==6) return "修改等待中"; 
		else if(valid==7) return "修改中";  
		else if(valid==8) return "删除等待中"; 
		else if(valid==9) return "已删除";   
		else if(valid==10) return "关闭";   
		else if(valid==11) return "暂停";   
		else return "未知类型";
	}
	public String getSvalidName() {
		if(svalid==0) return "制作中";
		else if(svalid==1) return "申请等待中";
		else if(svalid==2) return "待修改";
		else if(svalid==3) return "审核等待中";
		else if(svalid==4) return "审核不通过";
		else if(svalid==5) return "已开通";  
		else return "未知类型";
	}
	public String getUvalidName() {
		if(uvalid==1) return "人员已复核";
		else if(uvalid==0) return "人员未复核"; 
		else return "未知类型";
	}
	public int getEpsize() {
		return epsize;
	}
	public void setEpsize(int epsize) {
		this.epsize = epsize;
	}
	public boolean isHasuser() {
		return hasuser;
	}
	public void setHasuser(boolean hasuser) {
		this.hasuser = hasuser;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeName() {
		if (type == 1)
			return "选拔式";
		if (type == 2)
			return "可申请";
		return "分配式";
	}
	

//	public int getPractimes() {
//		return practimes;
//	}
//	public void setPractimes(int practimes) {
//		this.practimes = practimes;
//	}
//	public int getPracscore() {
//		return pracscore;
//	}
//	public void setPracscore(int pracscore) {
//		this.pracscore = pracscore;
//	}
	public List<ExamPaper> getExampapers() {
		return exampapers;
	}
	public void setExampapers(List<ExamPaper> exampapers) {
		this.exampapers = exampapers;
	}
	public EroomLib getEroomLib() {
		return eroomLib;
	}
	public void setEroomLib(EroomLib eroomLib) {
		this.eroomLib = eroomLib;
	}
	public int getAverage() {
		return average;
	}
	public void setAverage(int average) {
		this.average = average;
	}
	public MyExamPaper getMinMep() {
		if(null!=meps){
			sortMep(meps);
			return meps.get(0);
		}
		return minMep;
	}
	public void sortMep(List<MyExamPaper> meps){
		for (int i = 0; i < meps.size(); i++) {
			for (int j = i; j < meps.size(); j++) {
				if(meps.get(i).getId()>meps.get(j).getId()){
					MyExamPaper temp = meps.get(i);
					meps.set(i, meps.get(j));
					meps.set(j, temp);
				}
			}
		}
		
	}
	public void setMinMep(MyExamPaper minMep) {
		this.minMep = minMep;
	}
	public MyExamPaper getMaxMep() {
		if(null!=meps){
			sortMep(meps);
			if(meps.size()>0)
			return meps.get(meps.size()-1);
		}
		return maxMep;
	}
	public void setMaxMep(MyExamPaper maxMep) {
		this.maxMep = maxMep;
	}
	public List<MyExamPaper> getMeps() {
		return meps;
	}
	public void setMeps(List<MyExamPaper> meps) {
		this.meps = meps;
	}
	public int getUserSize() {
		return userSize;
	}
	public void setUserSize(int userSize) {
		this.userSize = userSize;
	}
	public ExamRoom() {
	}
	public ExamRoom(int id){
		this.id = id;
	}
	public ExamRoom(int id,String title) {
		this.id =id;
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
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Timestamp getBegintime() {
		return begintime;
	}
	public String getBegintimeFmt() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(begintime);
	}
	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public String getEndtimeFmt() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endtime);
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public int getIscommon() {
		return iscommon;
	}
	public void setIscommon(int iscommon) {
		this.iscommon = iscommon;
	}
	public float getPassgrade() {
		return passgrade;
	}
	public void setPassgrade(float passgrade) {
		this.passgrade = passgrade;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public List<ELUser> getInvigilators() {
		return invigilators;
	}
	public void setInvigilators(List<ELUser> invigilators) {
		this.invigilators = invigilators;
	}
	public List<ELUser> getAppraises() {
		return appraises;
	}
	public void setAppraises(List<ELUser> appraises) {
		this.appraises = appraises;
	}
//	public ExamPaper getPrac() {
//		return prac;
//	}
//	public void setPrac(ExamPaper prac) {
//		this.prac = prac;
//	}
	public List<ELUser> getValids() {
		return valids;
	}
	public void setValids(List<ELUser> valids) {
		this.valids = valids;
	}
	public int getUvalid() {
		return uvalid;
	}
	public void setUvalid(int uvalid) {
		this.uvalid = uvalid;
	}
	public int getJoinusersize() {
		return joinusersize;
	}
	public void setJoinusersize(int joinusersize) {
		this.joinusersize = joinusersize;
	}
	public int getLOEusersize() {
		return LOEusersize;
	}
	public void setLOEusersize(int eusersize) {
		LOEusersize = eusersize;
	}
	public float getAvgscorejoin() {
		return avgscorejoin;
	}
	public void setAvgscorejoin(float avgscorejoin) {
		this.avgscorejoin = avgscorejoin;
	}
	public float getAvgscore() {
		return avgscore;
	}
	public void setAvgscore(float avgscore) {
		this.avgscore = avgscore;
	}
	public int getAvalid() {
		return avalid;
	}
	public void setAvalid(int avalid) {
		this.avalid = avalid;
	}
	public List<ELUser> getSelectings() {
		return selectings;
	}
	public void setSelectings(List<ELUser> selectings) {
		this.selectings = selectings;
	}
	public float getPass_6_p() {
		return pass_6_p;
	}
	public void setPass_6_p(float pass_6_p) {
		this.pass_6_p = pass_6_p;
	}
	public float getPass9__p() {
		return pass9__p;
	}
	public void setPass9__p(float pass9__p) {
		this.pass9__p = pass9__p;
	}
	public float getPass8_9_p() {
		return pass8_9_p;
	}
	public void setPass8_9_p(float pass8_9_p) {
		this.pass8_9_p = pass8_9_p;
	}
	public float getPass7_8_p() {
		return pass7_8_p;
	}
	public void setPass7_8_p(float pass7_8_p) {
		this.pass7_8_p = pass7_8_p;
	}
	public float getPass6_7_p() {
		return pass6_7_p;
	}
	public void setPass6_7_p(float pass6_7_p) {
		this.pass6_7_p = pass6_7_p;
	}
	public String getPass_6_ps() {
		return  float2dot(pass_6_p)  ;
	}
	public String getPass9__ps() {
		return float2dot(pass9__p);
	}
	public String getPass8_9_ps() {
		return float2dot(pass8_9_p);
	}
	public String getPass7_8_ps() {
		return float2dot(pass7_8_p);
	}
	public String getPass6_7_ps() {
		return float2dot(pass6_7_p);
	} 
	public String float2dot(float f){
		String s = f+"";
		if(s.indexOf(".")>=0){
			String s1 = s.substring(s.indexOf(".")+1);
			if(s1.length()>2)
				s = s.substring(0,s.indexOf("."))+"."+s1.substring(0,2);
			else
				s = s.substring(0,s.indexOf("."))+"."+s1 ;
		}
		return s;
	}
	public ElClass getElclass() {
		return elclass;
	}
	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}
	public int getSvalid() {
		return svalid;
	}
	public void setSvalid(int svalid) {
		this.svalid = svalid;
	}
	public boolean isUserid() {
		return isUserid;
	}
	public void setUserid(boolean isUserid) {
		this.isUserid = isUserid;
	}
	public float getPassgrade2() {
		return passgrade2;
	}
	public void setPassgrade2(float passgrade2) {
		this.passgrade2 = passgrade2;
	}
	public EroomRegistration getErRegistration() {
		return erRegistration;
	}
	public void setErRegistration(EroomRegistration erRegistration) {
		this.erRegistration = erRegistration;
	} 
	public int getJoinway() {
		return joinway;
	}
	public void setJoinway(int joinway) {
		this.joinway = joinway;
	} 
	public int getExamcount() {
		return examcount;
	}
	public void setExamcount(int examcount) {
		this.examcount = examcount;
	}
	public int getMarkingManner() {
		return markingManner;
	}
	public void setMarkingManner(int markingManner) {
		this.markingManner = markingManner;
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
	public int getUsize() {
		return Usize;
	}
	public void setUsize(int usize) {
		Usize = usize;
	}
	public int getPassmanner() {
		return passmanner;
	}
	public void setPassmanner(int passmanner) {
		this.passmanner = passmanner;
	}
	public int getSqlw() {
		return sqlw;
	}
	public void setSqlw(int sqlw) {
		this.sqlw = sqlw;
	}
	public int getPass5_6() {
		return pass5_6;
	}
	public void setPass5_6(int pass5_6) {
		this.pass5_6 = pass5_6;
	}
	public int getPass4_5() {
		return pass4_5;
	}
	public void setPass4_5(int pass4_5) {
		this.pass4_5 = pass4_5;
	}
	public int getPass3_4() {
		return pass3_4;
	}
	public void setPass3_4(int pass3_4) {
		this.pass3_4 = pass3_4;
	}
	public int getPass2_3() {
		return pass2_3;
	}
	public void setPass2_3(int pass2_3) {
		this.pass2_3 = pass2_3;
	}
	public int getPass1_2() {
		return pass1_2;
	}
	public void setPass1_2(int pass1_2) {
		this.pass1_2 = pass1_2;
	}
	public int getPass0_1() {
		return pass0_1;
	}
	public void setPass0_1(int pass0_1) {
		this.pass0_1 = pass0_1;
	}
	public int getCacheepsize() {
		return cacheepsize;
	}
	public void setCacheepsize(int cacheepsize) {
		this.cacheepsize = cacheepsize;
	}
	public int getCacheeprefresh() {
		return cacheeprefresh;
	}
	public void setCacheeprefresh(int cacheeprefresh) {
		this.cacheeprefresh = cacheeprefresh;
	}
	public int getEpqsort() {
		return epqsort;
	}
	public void setEpqsort(int epqsort) {
		this.epqsort = epqsort;
	}
	public boolean isUvalid() {
		return isUvalid;
	}
	public void setUvalid(boolean isUvalid) {
		this.isUvalid = isUvalid;
	}
	public int getIsPass() {
		return isPass;
	}
	public void setIsPass(int isPass) {
		this.isPass = isPass;
	}
	public ELClassRegistration getElRegistration() {
		return elRegistration;
	}
	public void setElRegistration(ELClassRegistration elRegistration) {
		this.elRegistration = elRegistration;
	}
	public static Log getLogger() {
		return logger;
	}
	public int getFirstLearnLaterExam() {
		return firstLearnLaterExam;
	}
	public void setFirstLearnLaterExam(int firstLearnLaterExam) {
		this.firstLearnLaterExam = firstLearnLaterExam;
	}
	public int getStandardLine() {
		return standardLine;
	}
	public void setStandardLine(int standardLine) {
		this.standardLine = standardLine;
	}
	public int getAutoAssign() {
		return autoAssign;
	}
	public void setAutoAssign(int autoAssign) {
		this.autoAssign = autoAssign;
	}
	public int getCpid() {
		return cpid;
	}
	public void setCpid(int cpid) {
		this.cpid = cpid;
	}
	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}
	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}
	public ExamPaper getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public int getCanExam() {
		return canExam;
	}
	public void setCanExam(int canExam) {
		this.canExam = canExam;
	}
	public CoursePage getCpage() {
		return cpage;
	}
	public void setCpage(CoursePage cpage) {
		this.cpage = cpage;
	}
	public int getStuViewResult() {
		return stuViewResult;
	}
	public void setStuViewResult(int stuViewResult) {
		this.stuViewResult = stuViewResult;
	}
	public int getEroomid() {
		return eroomid;
	}
	public void setEroomid(int eroomid) {
		this.eroomid = eroomid;
	}
	
	
}
