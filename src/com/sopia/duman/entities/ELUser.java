package com.sopia.duman.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.attendance.entity.AttendanceCount;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.CheckCard;
import com.sopia.common.DateUtility;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.duman.dao.UserDao;
import com.sopia.knowledgeman.entities.DownloadInfo;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.schedule.entities.dataallocation.DataAllocation;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.PointsRecord;
import com.sopia.wjm.entities.QuizpaperLogInfo;

/**
 * @author Administrator
 * 
 */
public class ELUser {
	private static final Log logger = LogFactory.getLog(ELUser.class);
	private int id;
	private String username;// 帐号、
	private String password;// 密码
	private String confirmPassword;
	private String realname;// 姓名
	private String sex;// 性别、 
	private String email;//add by jiahaijiang
	
	// ---
	private ElRole role;
	private Department department;
	private boolean valid; 
	private boolean introom;
	private int age ;
	private int admin;
	private int active;
	private int luntanjibie;
	private int age_ ;
	private int ispassed;
	
	private int staid;//岗位id
	private DataAllocation dataAllocation;//分配信息
	private Station station;
	private String cepingjindu;
	private int isAllocated;//是否分配   1分配
	private int isApplicated;//是否申请  1申请
	private int nov;
	
	private String specialty;//专业
	private String school;//毕业院校 
	private int Education;//学历
	private String xuewei;//学位
	private String minzu;//民族
	private String jiguan;//籍贯
	private Date canjiagongzuoshijian;//参加工作时间
	private Date rusishijian;//入司时间
	private String zhengzhimianmao;//政治面貌
	private String pinyinjianxie;//拼音简写
	private String chushengdi;//出生地
	private Date xianrenzhishijian;//现任职时间
	private String xianyuangongzu;//现员工组
	
	private String xianzhiwei;//现职位
	private String zhideng;//职等
	private String xueli;//学历
	
	
	private int selectlevelid;//下拉选项id
	private int valids;
	public String getValidsName() {
		if(valids == 0){
			return "关闭";
		}else if(valids == 1){
			return "开通";
		}else if(valids == 2){
			return "准开通";
		}else if(valids == 3){
			return "初审通过";
		}else{ 
			return "未知类型";
		} 
	}
	
	private int isAlter;//是否已修改
	private String peixunleibie;//培训类别
	private String shifouzaizhi;//是否在职
	
	//北京二次---------------------------

	private String userno;// 编号
	private String address;// 地址  
	private String edubg;// 学历（多选一）、
	private String major;// 专业、
	private String studyDir;// 研究方向、
	private String gradchool;// 毕业院校、
	private Date graddate_by;// 毕业时间（DATA）、
	private Date jobdate;// 参加工作时间（DATA）、
	private String protitle;// 职称（多选一）、
	private String jobdesc;// 工作简历、
	private String majorc;// 专业证书
	private String headPhoto;
	private String studentno;//学员编号
	private String danweihao;//单位编号
	
//	学习经历 
//	工作经历
	private String renyuanleibie;//人员类别
	private Date zhiwupinrenriqi;//职务聘任日期
	private String zhichengleibie;//职称类别
	private String zhichengjibie;//职称级别
	private Date zhichengquderiqi;//职称取得日期
	private String zhichenghao;//职称号
//	其他
	private String beizhu;//备注
	//----
	private String kuaijihao;//会计号
	private String zhengjianleixing;//会计号
	private String zhengjianhao;//会计号
	private String chushengriqi;
	private String kuaijizhengfazhengriqi;//会计证发证日期
	private String kuaijizhengfazhengjiguan;//会计证发证机关
	private String kuaijizhengyouxiaoqi;//会计证 有效期；
	private String kaishikuaijishijian;//开始从事会计工作时间
	private String zhengzhi;//政治面貌
	private String kuaijizhuanyejishuzhiwu;	//会计专业技术职务
	private String kuaijizhuanyejishuzhiwuriqi;//会计专业技术职务聘任时间
	private Date biyeshijian;//毕业时间
	private String suoxuezhuanye;//所学专业
	private String phone;// 电话 
	private String feixuewei;//学位
	private String feixueli;//学历
	private String feibiyeyuanxiao;//毕业院校
	private String feibiyeshijian;//毕业时间
	private String feisuoxuezhuanye;//所学专业
	private String lianxifangshi;//单位电话
	private String danweiaddress;//单位地址
	private String suozaigangwei;//现从事会计工作岗位
	private String kuaijixingzhengzhiwu;//会计行政职务
	private String suozaidixingzhengqu;//现所在地行政区划
	private String zhuanyezigeleixing;//专业技术资格类型: 
	private String zhuanyezigejibie;//	专业技术资格级别:  
	private String zhuanyezigehuoqufangshi;//	专业技术资格取得方式:  无  评审 考试 考评  
	private String zhuanyezigehuoquriqi;//	专业技术资格取得时间:      
	private String zhuanyezigezhengshu;//	专业技术资格证书号或批文号:    
	private String zhucekuaijishi;//	是否注册会计师:
	private String zhucepinggushi;//	是否注册评估师:    否 是 
	private String zhuceshuiwushi;//	是否注册税务师:   
	private String gaoduanrencai;//是否高端人才:         
	private String gaoduanrencaileixing;//	高端人才类型:       
	private String gaoduanrencairiqi;//	高端人才资格取得时间:      
	private String danweileixing;
	private int dot; 
	private int score;
	private int xfscore;
	private String xuehao;//学号
	private DownloadInfo downloadInfo;  
	private PointsRecord precord;//积分记录
	private ElClass elclass;//积分记录
	
	//针对外经贸
	private ElClass nowClass;//当前正在学习的等级
	private MyClass myNowClass;
	private float intelligentPoints;//智能辅导分
	private QuizpaperLogInfo log;
	private CoursePage coursePage;
	private Course course;
	private MyClass myClass;
	
	//二次开发（混合课程）
	
	private Timestamp baoming;
	
	//机构注册
	private int huiyuanleixing;//会员类型
	private int luntanbankuai;//论坛版块
	private String bankuaimingcheng;//版块名称
	
	//sd1230
	private Date begintime;
	private Date begintime_end;
	private  int myscore;//考场考试成绩
   //sd0110
	private int [] jingzhongIds;
	private String workTypeName;//职业类别名称
	private int flag;//判断部门之外的选择项是否选中
	private String personTypeName;//人群类别
	
	//20140826 外经贸指纹识别
	private String fingerInfo;
	
	
	public String getFingerInfo() {
		return fingerInfo;
	}

	public void setFingerInfo(String fingerInfo) {
		this.fingerInfo = fingerInfo;
	}

	public String getPersonTypeName() {
		return personTypeName;
	}

	public void setPersonTypeName(String personTypeName) {
		this.personTypeName = personTypeName;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	


	public String getWorkTypeName() {
		return workTypeName;
	}

	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}

	public int[] getJingzhongIds() {
		return jingzhongIds;
	}

	public void setJingzhongIds(int[] jingzhongIds) {
		this.jingzhongIds = jingzhongIds;
	}

	public int getMyscore() {
		return myscore;
	}

	public void setMyscore(int myscore) {
		this.myscore = myscore;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public int getLuntanbankuai() {
		return luntanbankuai;
	}

	public void setLuntanbankuai(int luntanbankuai) {
		this.luntanbankuai = luntanbankuai;
	}

	public String getBankuaimingcheng() {
		return bankuaimingcheng;
	}

	public void setBankuaimingcheng(String bankuaimingcheng) {
		this.bankuaimingcheng = bankuaimingcheng;
	}

	public int getHuiyuanleixing() {
		return huiyuanleixing;
	}

	public void setHuiyuanleixing(int huiyuanleixing) {
		this.huiyuanleixing = huiyuanleixing;
	}

	public MyClass getMyClass() {
		return myClass;
	}

	public void setMyClass(MyClass myClass) {
		this.myClass = myClass;
	}

	public Timestamp getBaoming() {
		return baoming;
	}

	public void setBaoming(Timestamp baoming) {
		this.baoming = baoming;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CoursePage getCoursePage() {
		return coursePage;
	}

	public void setCoursePage(CoursePage coursePage) {
		this.coursePage = coursePage;
	}

	public QuizpaperLogInfo getLog() {
		return log;
	}

	public void setLog(QuizpaperLogInfo log) {
		this.log = log;
	}

	public MyClass getMyNowClass() {
		return myNowClass;
	}

	public void setMyNowClass(MyClass myNowClass) {
		this.myNowClass = myNowClass;
	}

	public float getIntelligentPoints() {
		return intelligentPoints;
	}

	public void setIntelligentPoints(float intelligentPoints) {
		this.intelligentPoints = intelligentPoints;
	}

	public ElClass getNowClass() {
		return nowClass;
	}

	public void setNowClass(ElClass nowClass) {
		this.nowClass = nowClass;
	}

	public PointsRecord getPrecord() {
		return precord;
	}

	public void setPrecord(PointsRecord precord) {
		this.precord = precord;
	}

	public ElClass getElclass() {
		return elclass;
	}

	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}

	public DownloadInfo getDownloadInfo() {
		return downloadInfo;
	}

	public void setDownloadInfo(DownloadInfo downloadInfo) {
		this.downloadInfo = downloadInfo;
	}

	public String getXuehao() {
		return xuehao;
	}

	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}

	public int getDot() {
		return dot;
	}

	public void setDot(int dot) {
		this.dot = dot;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getXfscore() {
		return xfscore;
	}

	public void setXfscore(int xfscore) {
		this.xfscore = xfscore;
	}

	public String getShifouzaizhi() {
		return shifouzaizhi;
	}

	public void setShifouzaizhi(String shifouzaizhi) {
		this.shifouzaizhi = shifouzaizhi;
	}

	public String getPeixunleibie() {
		return peixunleibie;
	}

	public void setPeixunleibie(String peixunleibie) {
		this.peixunleibie = peixunleibie;
	}

	public int getIsAlter() {
		return isAlter;
	}

	public void setIsAlter(int isAlter) {
		this.isAlter = isAlter;
	}
	public String getIsAlterName() {
		if(isAlter == 1 || isAlter == 0 )
			return "未修改";
		if(isAlter == 2)
			return "已修改";
		return "未知";
	}
	private int isLock;//是否锁定
	

	public int getIsLock() {
		return isLock;
	}

	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}
	public String getIsLockName() {
		if(SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX)!=0){
		if(isLock == 0)
			return "解锁";
		if(isLock == 1)
			return "锁定";
		}else{			
			return "";
		}
		return "未知";
	}

	public int getValids() {
		return valids;
	}

	public void setValids(int valids) {
		this.valids = valids;
	}

	public int getNov() {
		return nov;
	}

	public void setNov(int nov) {
		this.nov = nov;
	}

	public int getSelectlevelid() {
		return selectlevelid;
	}

	public void setSelectlevelid(int selectlevelid) {
		this.selectlevelid = selectlevelid;
	}

	public String getXueli() {
		return xueli;
	}

	public void setXueli(String xueli) {
		this.xueli = xueli;
	}

	public String getZhideng() {
		return zhideng;
	}

	public void setZhideng(String zhideng) {
		this.zhideng = zhideng;
	}

	public String getXianzhiwei() {
		return xianzhiwei;
	}

	public void setXianzhiwei(String xianzhiwei) {
		this.xianzhiwei = xianzhiwei;
	}

	public String getXuewei() {
		return xuewei;
	}

	public void setXuewei(String xuewei) {
		this.xuewei = xuewei;
	}

	public String getMinzu() {
		return minzu;
	}

	public void setMinzu(String minzu) {
		this.minzu = minzu;
	}

	public String getJiguan() {
		return jiguan;
	}

	public void setJiguan(String jiguan) {
		this.jiguan = jiguan;
	}

	public Date getCanjiagongzuoshijian() {
		return canjiagongzuoshijian;
	}

	public void setCanjiagongzuoshijian(Date canjiagongzuoshijian) {
		this.canjiagongzuoshijian = canjiagongzuoshijian;
	}

	public Date getRusishijian() {
		return rusishijian;
	}

	public void setRusishijian(Date rusishijian) {
		this.rusishijian = rusishijian;
	}

	public String getZhengzhimianmao() {
		return zhengzhimianmao;
	}

	public void setZhengzhimianmao(String zhengzhimianmao) {
		this.zhengzhimianmao = zhengzhimianmao;
	}

	public String getPinyinjianxie() {
		return pinyinjianxie;
	}

	public void setPinyinjianxie(String pinyinjianxie) {
		this.pinyinjianxie = pinyinjianxie;
	}

	public String getChushengdi() {
		return chushengdi;
	}

	public void setChushengdi(String chushengdi) {
		this.chushengdi = chushengdi;
	}


	public Date getXianrenzhishijian() {
		return xianrenzhishijian;
	}

	public void setXianrenzhishijian(Date xianrenzhishijian) {
		this.xianrenzhishijian = xianrenzhishijian;
	}

	public String getXianyuangongzu() {
		return xianyuangongzu;
	}

	public void setXianyuangongzu(String xianyuangongzu) {
		this.xianyuangongzu = xianyuangongzu;
	}

	public int getIsAllocated() {
		return isAllocated;
	}

	public void setIsAllocated(int isAllocated) {
		this.isAllocated = isAllocated;
	}

	public int getIsApplicated() {
		return isApplicated;
	}

	public void setIsApplicated(int isApplicated) {
		this.isApplicated = isApplicated;
	}

	public String getCepingjindu() {
		return cepingjindu;
	}

	public void setCepingjindu(String cepingjindu) {
		this.cepingjindu = cepingjindu;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
	
	public DataAllocation getDataAllocation() {
		return dataAllocation;
	}

	public void setDataAllocation(DataAllocation dataAllocation) {
		this.dataAllocation = dataAllocation;
	}

	public int getSex_int() {
		if(sex.equals("男"))
			return 1;
		if(sex.equals("女"))
			return 0;
		
		return 0;
	}
	
	public int getStaid() {
		return staid;
	}

	public void setStaid(int staid) {
		this.staid = staid;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getEducation() {
		return Education;
	}

	public void setEducation(int education) {
		Education = education;
	}
	
	public String getEducationName(){
		if(Education == 0){
			return "大专";
		}else if(Education == 1){
			return "本科";
		}else if(Education == 2){
			return "研究生";
		}else if(Education == 3){
			return "博士生";
		}else if(Education == 4){
			return "高中";
		}else if(Education == 5){
			return "中学";
		}else{
			return "未知";
		}
	}
	
	public String getCepingjinduName(){
		if(cepingjindu!=null&&!cepingjindu.equals("")){
			if(cepingjindu.equals("11111")){
				return "已完成";
			}else{
				return "未完成";
			}
		}else{
			return "未完成";
		}
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	//序号	地市	单位 身份证号	 出生日期	职级	职务	警种	岗位
	//非警员
	private String xuhao;
	//private String dishi;
	private String danwei;
	private String shenfenzheng;
	private Date shengri;
	private Date shengri_end;
	//private String zhiji;
	//private String zhiwu;
	//private String jingzhong;
	private String gangwei;
	private int jy;
	//要换成整形
	private int dishi;
	private int zhiji;
	private int zhiwu;
	private int jingzhong;
	
	private int xx_time;
	private int xx_credit;
	private int ct_credit;
	private int ct_time;
	private Date graddate;
	private List<MyCourse> myCourses; 
	private MyCourse myCourse;
	private int courseSize;
	private int classSize;
	private int practimes;
	private float pracscore;
	private String isQualified;//合格
//	private int nov;
	private int isApplication;//是否可申请
	private String joinway;//参加方式
	private int joinwayInt;//参加方式（int）
	private String isjoin;//是否参加该培训班
	private String movephone;
	private int valid2;//1:'开通',2:'关闭',0:'全部'

	//分配人员（按培训班查询）
	private int btotalscore; //总学分
	private int btotalscore_; //总学分_
	private int bxscore; 	//必修学分
	private int bxscore_; 	//必修学分_
	private int xxscore; 	//选修学分
	private int xxscore_; 	//选修学分_
	private String isPass; 		//是否通过	
	private String isAssign;	//是否分配 
	 
	//分配人员（按考场查询）
	private int KcBtotalscore;  //总学分
	private int KcBtotalscore_; //总学分_
	private String isKcPass; 	//是否通过	
	private List<String> Kcsq; 	//试卷信息
	private List<String> Kcsq_; //试卷信息	
	private List<String> Kclxcs; 	//练习次数
	private List<String> Kclxcs_; //练习次数	
	private List<String> epids; //试卷信息	
	
	private int age_start;//按年龄段查询（开始）
	private int age_end;  //按年龄段查询（结束）
	private int isLeader;//是否阅卷组长
	
	/**
	 * @return the isLeader
	 */
	public int getIsLeader() {
		return isLeader;
	}

	/**
	 * @param isLeader the isLeader to set
	 */
	public void setIsLeader(int isLeader) {
		this.isLeader = isLeader;
	}
	private PfmsUser pfmsUser;//企业会员
	private String  touxiang;
	
	private String mac;//考勤设置mac绑定
	private AttendanceCount attendanceCount;
	private Map<String,Integer> kqyl;
	
	private int usertype;
	
	
	public ELUser(int id,String realname,String username,String sex,String shenfenzheng,String movephone,String danwei){
		this.id = id;
		this.realname = realname;
		this.username = username;
		this.sex = sex;
		this.shenfenzheng = shenfenzheng;
		this.movephone = movephone;
		this.danwei = danwei;
	}
	public String getMainimg_() {
		if(touxiang!=null&&(touxiang.indexOf("http://")==0||touxiang.indexOf("https://")==0))
			return touxiang;
		return  SystemConfOp.getStuffUrl()+touxiang;
	}
	public PfmsUser getPfmsUser() {
		return pfmsUser;
	}

	public void setPfmsUser(PfmsUser pfmsUser) {
		this.pfmsUser = pfmsUser;
	}

	public String getTouxiang() {
		return touxiang;
	}

	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}

	public int getAge_start() {
		return age_start;
	}
	
	/**
	 * 获取年龄离现在的日期
	 * @return
	 */
	public Date getAge_startSr() {
		//int year=Calendar.getInstance().get(Calendar.YEAR);
		Date date =  new Date(System.currentTimeMillis());
		date.setYear(date.getYear()-this.getAge_start());
		return date;
	}

	public void setAge_start(int age_start) {
		this.age_start = age_start;
	}

	public int getAge_end() {
		return age_end;
	}
	/**
	 * 获取年龄离现在的日期
	 * @return
	 */
	public Date getAge_endSr() {
		Date date =  new Date(System.currentTimeMillis());
		date.setYear(date.getYear()-this.getAge_end());
		return date;
	}

	public void setAge_end(int age_end) {
		this.age_end = age_end;
	}

	public int getValid2() {
		return valid2;
	}

	public void setValid2(int valid2) {
		this.valid2 = valid2;
	}

	public String getMovephone() {
		return movephone;
	}

	public void setMovephone(String movephone) {
		this.movephone = movephone;
	}

	public String getJoinway() {
		return joinway;
	}

	public void setJoinway(String joinway) {
		this.joinway = joinway;
	}

	public int getJoinwayInt() {
		return joinwayInt;
	}
	
	public String getJoinway_() {
		if(joinwayInt==1){
			return "申请";
		}else if(joinwayInt==0){
			return "分配";
		}
		return "";
	}
	public String getUsertypeName(){
		if(usertype==1){
			return "培训机构";
		}else if(usertype==2){
			return "讲师";
		}else if(usertype==3){
			return "学校";
		}else if(usertype==4){
			return "企业";
		}
		return "";
	}

	public void setJoinwayInt(int joinwayInt) {
		this.joinwayInt = joinwayInt;
	}

	public int getIsApplication() {
		return isApplication;
	}

	public void setIsApplication(int isApplication) {
		this.isApplication = isApplication;
	}

//	public int getNov() {
//		return nov;
//	}
//
//	public void setNov(int nov) {
//		this.nov = nov;
//	}

	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}

	public String getIsAssign() {
		return isAssign;
	}

	public void setIsAssign(String isAssign) {
		this.isAssign = isAssign;
	}

	public List<MyCourse> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}

	public int getJy() {
		return jy;
	}

	public void setJy(int jy) {
		this.jy = jy;
	}

	public ELUser() {
	}

	public ELUser(int id) {
		this.id = id;
	}

	public ELUser(int id, String realname) {
		this.id = id;
		this.realname = realname;
	}
	

	/*
	 * public String getRoleName() { switch (role) {
	 * 
	 * case 1: roleName="超级管理员"; break; case 2: roleName="部门管理员"; break; case 3:
	 * roleName="教师"; break; case 4: roleName="学员"; break;
	 * 
	 * default: roleName="未知角色"; break; } return roleName; }
	 */
	/*
	 * public void setRoleName(String roleName) { this.roleName = roleName; }
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
//		if(username!=null&&!"".equals(username)){
//			username=CheckCard.fixPersonIDCode(username.toLowerCase()).toLowerCase();
//		}
		if(username!=null&&!"".equals(username)){
			username=username.toLowerCase();
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	/*
	 * public int getRole() { return role; } public void setRole(int role) {
	 * this.role = role; }
	 */
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getValidName() {
		return valid ? "开通" : "关闭";
	}

	public ElRole getRole() {
		return role;
	}

	public void setRole(ElRole role) {
		this.role = role;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public boolean getIntroom() {
		return introom;
	}

	public void setIntroom(boolean introom) {
		this.introom = introom;
	}

	public String getXuhao() {
		return xuhao;
	}

	public void setXuhao(String xuhao) {
		this.xuhao = xuhao;
	}

//	public String getDishi() {
//		return dishi;
//	} 
	
	public String getDishi_() {
		return getBasevalue(dishi);
	}

//	public void setDishi(String dishi) {
//		this.dishi = dishi;
//	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public String getShenfenzheng() {
		if(shenfenzheng!=null&&!"".equals(shenfenzheng)){
			shenfenzheng=shenfenzheng.toLowerCase();
		}
//		if(shenfenzheng!=null&&!"".equals(shenfenzheng)){
//			shenfenzheng=CheckCard.fixPersonIDCode(shenfenzheng.toLowerCase()).toLowerCase();
//		}
		return shenfenzheng;
	}

	public void setShenfenzheng(String shenfenzheng) {
		this.shenfenzheng = shenfenzheng;
	}

	public Date getShengri() {
		return shengri;
	}
	
	public Date getShengri_() {
		if(null==shengri){
			if(this.getShenfenzheng()!=null||"".equals(this.getShenfenzheng())){
				//判断身份证的位数
				if(this.getShenfenzheng().length()==15){
					
				}else if(this.getShenfenzheng().length()==18){
					//6-14 date8=date8.substring(0,4)+"-"+date8.substring(4,6)+"-"+date8.substring(6,8);
					String shengr=this.getShenfenzheng().substring(6,14);
					shengr=shengr.substring(0,4)+"-"+shengr.substring(4,6)+"-"+shengr.substring(6,8);
					try {
						java.util.Date date= new SimpleDateFormat("yyyy-mm-dd").parse(shengr);
						Date dat=new Date(date.getTime());
						return dat;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						logger.error("生日转换错误",e);
					}
				}
			}
			return shengri;
		}
		return shengri;
	}

	public void setShengri(Date shengri) {
		this.shengri = shengri;
	}

//	public String getZhiji() {
//		return zhiji;
//	}
	
	public String getZhiji_() {
		return getBasevalue(zhiji);
	}

//	public void setZhiji(String zhiji) {
//		this.zhiji = zhiji;
//	}

//	public String getZhiwu() {
//		return zhiwu;
//	}
	
	public String getZhiwu_() {
		return getBasevalue(zhiwu);
	}

//	public void setZhiwu(String zhiwu) {
//		this.zhiwu = zhiwu;
//	}
//
//	public String getJingzhong() {
//		return jingzhong;
//	}
	
	public String getJingzhong_() {
		return getBasevalue(jingzhong);
	}

//	public void setJingzhong(String jingzhong) {
//		this.jingzhong = jingzhong;
//	}

	public String getGangwei() {
		return gangwei;
	}
	
//	public String getGangwei_() {
//		return getBasevalue(gangwei);
//	}

	public void setGangwei(String gangwei) {
		this.gangwei = gangwei;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public int getXx_time() {
		return xx_time;
	}

	public void setXx_time(int xx_time) {
		this.xx_time = xx_time;
	}

	public int getXx_credit() {
		return xx_credit;
	}

	public void setXx_credit(int xx_credit) {
		this.xx_credit = xx_credit;
	}

	public int getCt_credit() {
		return ct_credit;
	}

	public void setCt_credit(int ct_credit) {
		this.ct_credit = ct_credit;
	}

	public int getCt_time() {
		return ct_time;
	}

	public void setCt_time(int ct_time) {
		this.ct_time = ct_time;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public Date getShengri_end() {
		return shengri_end;
	}
	public int getAGE(){ 
		return new DateUtility().GetAge(shengri);
	}

	public void setShengri_end(Date shengri_end) {
		this.shengri_end = shengri_end;
	}
	public String[] getJingzhongs(){
		
		return "派出所民警==治安警==巡警==防暴警==交警==刑警==戒毒所、看守所、拘留所民警==乘警==其他警种".split("==");
	}  
	public int getCourseSize() {
		return courseSize;
	}

	public void setCourseSize(int courseSize) {
		this.courseSize = courseSize;
	}

	public int getClassSize() {
		return classSize;
	}

	public void setClassSize(int classSize) {
		this.classSize = classSize;
	}

	public Date getGraddate() {
		return graddate;
	}

	public void setGraddate(Date graddate) {
		this.graddate = graddate;
	}

	public int getPractimes() {
		return practimes;
	}

	public void setPractimes(int practimes) {
		this.practimes = practimes;
	}

	public float getPracscore() {
		return pracscore;
	}

	public void setPracscore(float pracscore) {
		this.pracscore = pracscore;
	}

	public String getIsjoin() {
		return isjoin;
	}

	public void setIsjoin(String isjoin) {
		this.isjoin = isjoin;
	}
 
//	public String getBasevalue(String str){ 
//		if(isInteger(str)){//判断是否整形
//			int dishiInt=Integer.parseInt(str);
//			try {
//				BaseDatat base = new BaseDatat(); 
//				base = ((UserDao)SpringContextUtil.getBean("userDao")).getBaseDatatById(dishiInt); 
//				if(base != null){ 
//					return base.getBasevalue();
//				} 
//			} catch (ElException e) {
//				// TODO Auto-generated catch block
//			}
//		}
//		return str;
//	}
	
	public String getBasevalue(int key){ 
		try {
			BaseDatat base = ((UserDao)SpringContextUtil.getBean("userDao")).getBaseDatatById(key); 
			if(base != null){ 
				return base.getBasevalue();
			} 
		} catch (ElException e) {
			// TODO Auto-generated catch block
			logger.error("获取基础数据错误",e);
		}
		return key+"";
	}
	
	public boolean isInteger(String str){
		try{
			Integer.parseInt(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public ELUser(int id, String username, String realname) {
		this.id = id;
		this.username = username;
		this.realname = realname;
	}
 
	public int getBtotalscore() {
		return btotalscore;
	}

	public void setBtotalscore(int btotalscore) {
		this.btotalscore = btotalscore;
	}

	public int getBtotalscore_() {
		return btotalscore_;
	}

	public void setBtotalscore_(int btotalscore_) {
		this.btotalscore_ = btotalscore_;
	}

	public int getBxscore() {
		return bxscore;
	}

	public void setBxscore(int bxscore) {
		this.bxscore = bxscore;
	}

	public int getBxscore_() {
		return bxscore_;
	}

	public void setBxscore_(int bxscore_) {
		this.bxscore_ = bxscore_;
	}

	public int getXxscore() {
		return xxscore;
	}

	public void setXxscore(int xxscore) {
		this.xxscore = xxscore;
	}

	public int getXxscore_() {
		return xxscore_;
	}

	public void setXxscore_(int xxscore_) {
		this.xxscore_ = xxscore_;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	public int getKcBtotalscore() {
		return KcBtotalscore;
	}

	public void setKcBtotalscore(int kcBtotalscore) {
		KcBtotalscore = kcBtotalscore;
	}

	public int getKcBtotalscore_() {
		return KcBtotalscore_;
	}

	public void setKcBtotalscore_(int kcBtotalscore_) {
		KcBtotalscore_ = kcBtotalscore_;
	}

	public String getIsKcPass() {
		return isKcPass;
	}

	public void setIsKcPass(String isKcPass) {
		this.isKcPass = isKcPass;
	}

	public List<String> getKcsq() {
		return Kcsq;
	}

	public void setKcsq(List<String> kcsq) {
		Kcsq = kcsq;
	}

	public List<String> getKcsq_() {
		return Kcsq_;
	}

	public void setKcsq_(List<String> kcsq_) {
		Kcsq_ = kcsq_;
	}

	public List<String> getKclxcs() {
		return Kclxcs;
	}

	public void setKclxcs(List<String> kclxcs) {
		Kclxcs = kclxcs;
	}

	public List<String> getKclxcs_() {
		return Kclxcs_;
	}

	public void setKclxcs_(List<String> kclxcs_) {
		Kclxcs_ = kclxcs_;
	}

	public List<String> getEpids() {
		return epids;
	}

	public void setEpids(List<String> epids) {
		this.epids = epids;
	}

	public int getDishi() {
		return dishi;
	}

	public void setDishi(int dishi) {
		this.dishi = dishi;
	}

	public int getZhiji() {
		return zhiji;
	}

	public void setZhiji(int zhiji) {
		this.zhiji = zhiji;
	}

	public int getZhiwu() {
		return zhiwu;
	}

	public void setZhiwu(int zhiwu) {
		this.zhiwu = zhiwu;
	}

	public int getJingzhong() {
		return jingzhong;
	}

	public void setJingzhong(int jingzhong) {
		this.jingzhong = jingzhong;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public AttendanceCount getAttendanceCount() {
		return attendanceCount;
	}

	public void setAttendanceCount(AttendanceCount attendanceCount) {
		this.attendanceCount = attendanceCount;
	}

	public Map<String, Integer> getKqyl() {
		return kqyl;
	}

	public void setKqyl(Map<String, Integer> kqyl) {
		this.kqyl = kqyl;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public int getLuntanjibie() {
		return luntanjibie;
	}

	public void setLuntanjibie(int luntanjibie) {
		this.luntanjibie = luntanjibie;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEdubg() {
		return edubg;
	}

	public void setEdubg(String edubg) {
		this.edubg = edubg;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getStudyDir() {
		return studyDir;
	}

	public void setStudyDir(String studyDir) {
		this.studyDir = studyDir;
	}

	public String getGradchool() {
		return gradchool;
	}

	public void setGradchool(String gradchool) {
		this.gradchool = gradchool;
	}

	public Date getGraddate_by() {
		return graddate_by;
	}

	public void setGraddate_by(Date graddate_by) {
		this.graddate_by = graddate_by;
	}

	public Date getJobdate() {
		return jobdate;
	}

	public void setJobdate(Date jobdate) {
		this.jobdate = jobdate;
	}

	public String getProtitle() {
		return protitle;
	}

	public void setProtitle(String protitle) {
		this.protitle = protitle;
	}

	public String getJobdesc() {
		return jobdesc;
	}

	public void setJobdesc(String jobdesc) {
		this.jobdesc = jobdesc;
	}

	public String getMajorc() {
		return majorc;
	}

	public void setMajorc(String majorc) {
		this.majorc = majorc;
	}

	public String getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}

	public String getStudentno() {
		return studentno;
	}

	public void setStudentno(String studentno) {
		this.studentno = studentno;
	}

	public String getDanweihao() {
		return danweihao;
	}

	public void setDanweihao(String danweihao) {
		this.danweihao = danweihao;
	}

	public String getRenyuanleibie() {
		return renyuanleibie;
	}

	public void setRenyuanleibie(String renyuanleibie) {
		this.renyuanleibie = renyuanleibie;
	}

	public Date getZhiwupinrenriqi() {
		return zhiwupinrenriqi;
	}

	public void setZhiwupinrenriqi(Date zhiwupinrenriqi) {
		this.zhiwupinrenriqi = zhiwupinrenriqi;
	}

	public String getZhichengleibie() {
		return zhichengleibie;
	}

	public void setZhichengleibie(String zhichengleibie) {
		this.zhichengleibie = zhichengleibie;
	}

	public String getZhichengjibie() {
		return zhichengjibie;
	}

	public void setZhichengjibie(String zhichengjibie) {
		this.zhichengjibie = zhichengjibie;
	}

	public Date getZhichengquderiqi() {
		return zhichengquderiqi;
	}

	public void setZhichengquderiqi(Date zhichengquderiqi) {
		this.zhichengquderiqi = zhichengquderiqi;
	}

	public String getZhichenghao() {
		return zhichenghao;
	}

	public void setZhichenghao(String zhichenghao) {
		this.zhichenghao = zhichenghao;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getKuaijihao() {
		return kuaijihao;
	}

	public void setKuaijihao(String kuaijihao) {
		this.kuaijihao = kuaijihao;
	}

	public String getZhengjianleixing() {
		return zhengjianleixing;
	}

	public void setZhengjianleixing(String zhengjianleixing) {
		this.zhengjianleixing = zhengjianleixing;
	}

	public String getZhengjianhao() {
		return zhengjianhao;
	}

	public void setZhengjianhao(String zhengjianhao) {
		this.zhengjianhao = zhengjianhao;
	}

	public String getChushengriqi() {
		return chushengriqi;
	}

	public void setChushengriqi(String chushengriqi) {
		this.chushengriqi = chushengriqi;
	}

	public String getKuaijizhengfazhengriqi() {
		return kuaijizhengfazhengriqi;
	}

	public void setKuaijizhengfazhengriqi(String kuaijizhengfazhengriqi) {
		this.kuaijizhengfazhengriqi = kuaijizhengfazhengriqi;
	}

	public String getKuaijizhengfazhengjiguan() {
		return kuaijizhengfazhengjiguan;
	}

	public void setKuaijizhengfazhengjiguan(String kuaijizhengfazhengjiguan) {
		this.kuaijizhengfazhengjiguan = kuaijizhengfazhengjiguan;
	}

	public String getKuaijizhengyouxiaoqi() {
		return kuaijizhengyouxiaoqi;
	}

	public void setKuaijizhengyouxiaoqi(String kuaijizhengyouxiaoqi) {
		this.kuaijizhengyouxiaoqi = kuaijizhengyouxiaoqi;
	}

	public String getKaishikuaijishijian() {
		return kaishikuaijishijian;
	}

	public void setKaishikuaijishijian(String kaishikuaijishijian) {
		this.kaishikuaijishijian = kaishikuaijishijian;
	}

	public String getZhengzhi() {
		return zhengzhi;
	}

	public void setZhengzhi(String zhengzhi) {
		this.zhengzhi = zhengzhi;
	}

	public String getKuaijizhuanyejishuzhiwu() {
		return kuaijizhuanyejishuzhiwu;
	}

	public void setKuaijizhuanyejishuzhiwu(String kuaijizhuanyejishuzhiwu) {
		this.kuaijizhuanyejishuzhiwu = kuaijizhuanyejishuzhiwu;
	}

	public String getKuaijizhuanyejishuzhiwuriqi() {
		return kuaijizhuanyejishuzhiwuriqi;
	}

	public void setKuaijizhuanyejishuzhiwuriqi(String kuaijizhuanyejishuzhiwuriqi) {
		this.kuaijizhuanyejishuzhiwuriqi = kuaijizhuanyejishuzhiwuriqi;
	}

	public Date getBiyeshijian() {
		return biyeshijian;
	}

	public void setBiyeshijian(Date biyeshijian) {
		this.biyeshijian = biyeshijian;
	}

	public String getSuoxuezhuanye() {
		return suoxuezhuanye;
	}

	public void setSuoxuezhuanye(String suoxuezhuanye) {
		this.suoxuezhuanye = suoxuezhuanye;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFeixuewei() {
		return feixuewei;
	}

	public void setFeixuewei(String feixuewei) {
		this.feixuewei = feixuewei;
	}

	public String getFeixueli() {
		return feixueli;
	}

	public void setFeixueli(String feixueli) {
		this.feixueli = feixueli;
	}

	public String getFeibiyeyuanxiao() {
		return feibiyeyuanxiao;
	}

	public void setFeibiyeyuanxiao(String feibiyeyuanxiao) {
		this.feibiyeyuanxiao = feibiyeyuanxiao;
	}

	public String getFeibiyeshijian() {
		return feibiyeshijian;
	}

	public void setFeibiyeshijian(String feibiyeshijian) {
		this.feibiyeshijian = feibiyeshijian;
	}

	public String getFeisuoxuezhuanye() {
		return feisuoxuezhuanye;
	}

	public void setFeisuoxuezhuanye(String feisuoxuezhuanye) {
		this.feisuoxuezhuanye = feisuoxuezhuanye;
	}

	public String getLianxifangshi() {
		return lianxifangshi;
	}

	public void setLianxifangshi(String lianxifangshi) {
		this.lianxifangshi = lianxifangshi;
	}

	public String getDanweiaddress() {
		return danweiaddress;
	}

	public void setDanweiaddress(String danweiaddress) {
		this.danweiaddress = danweiaddress;
	}

	public String getSuozaigangwei() {
		return suozaigangwei;
	}

	public void setSuozaigangwei(String suozaigangwei) {
		this.suozaigangwei = suozaigangwei;
	}

	public String getKuaijixingzhengzhiwu() {
		return kuaijixingzhengzhiwu;
	}

	public void setKuaijixingzhengzhiwu(String kuaijixingzhengzhiwu) {
		this.kuaijixingzhengzhiwu = kuaijixingzhengzhiwu;
	}

	public String getSuozaidixingzhengqu() {
		return suozaidixingzhengqu;
	}

	public void setSuozaidixingzhengqu(String suozaidixingzhengqu) {
		this.suozaidixingzhengqu = suozaidixingzhengqu;
	}

	public String getZhuanyezigeleixing() {
		return zhuanyezigeleixing;
	}

	public void setZhuanyezigeleixing(String zhuanyezigeleixing) {
		this.zhuanyezigeleixing = zhuanyezigeleixing;
	}

	public String getZhuanyezigejibie() {
		return zhuanyezigejibie;
	}

	public void setZhuanyezigejibie(String zhuanyezigejibie) {
		this.zhuanyezigejibie = zhuanyezigejibie;
	}

	public String getZhuanyezigehuoqufangshi() {
		return zhuanyezigehuoqufangshi;
	}

	public void setZhuanyezigehuoqufangshi(String zhuanyezigehuoqufangshi) {
		this.zhuanyezigehuoqufangshi = zhuanyezigehuoqufangshi;
	}

	public String getZhuanyezigehuoquriqi() {
		return zhuanyezigehuoquriqi;
	}

	public void setZhuanyezigehuoquriqi(String zhuanyezigehuoquriqi) {
		this.zhuanyezigehuoquriqi = zhuanyezigehuoquriqi;
	}

	public String getZhuanyezigezhengshu() {
		return zhuanyezigezhengshu;
	}

	public void setZhuanyezigezhengshu(String zhuanyezigezhengshu) {
		this.zhuanyezigezhengshu = zhuanyezigezhengshu;
	}

	public String getZhucekuaijishi() {
		return zhucekuaijishi;
	}

	public void setZhucekuaijishi(String zhucekuaijishi) {
		this.zhucekuaijishi = zhucekuaijishi;
	}

	public String getZhucepinggushi() {
		return zhucepinggushi;
	}

	public void setZhucepinggushi(String zhucepinggushi) {
		this.zhucepinggushi = zhucepinggushi;
	}

	public String getZhuceshuiwushi() {
		return zhuceshuiwushi;
	}

	public void setZhuceshuiwushi(String zhuceshuiwushi) {
		this.zhuceshuiwushi = zhuceshuiwushi;
	}

	public String getGaoduanrencai() {
		return gaoduanrencai;
	}

	public void setGaoduanrencai(String gaoduanrencai) {
		this.gaoduanrencai = gaoduanrencai;
	}

	public String getGaoduanrencaileixing() {
		return gaoduanrencaileixing;
	}

	public void setGaoduanrencaileixing(String gaoduanrencaileixing) {
		this.gaoduanrencaileixing = gaoduanrencaileixing;
	}

	public String getGaoduanrencairiqi() {
		return gaoduanrencairiqi;
	}

	public void setGaoduanrencairiqi(String gaoduanrencairiqi) {
		this.gaoduanrencairiqi = gaoduanrencairiqi;
	}

	public String getDanweileixing() {
		return danweileixing;
	}

	public void setDanweileixing(String danweileixing) {
		this.danweileixing = danweileixing;
	}

	public int getAge_() {
		return age_;
	}

	public void setAge_(int age_) {
		this.age_ = age_;
	}

	public int getIspassed() {
		return ispassed;
	}

	public void setIspassed(int ispassed) {
		this.ispassed = ispassed;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public MyCourse getMyCourse() {
		return myCourse;
	}

	public void setMyCourse(MyCourse myCourse) {
		this.myCourse = myCourse;
	}

	public Date getBegintime_end() {
		return begintime_end;
	}

	public void setBegintime_end(Date begintime_end) {
		this.begintime_end = begintime_end;
	}

	
}
