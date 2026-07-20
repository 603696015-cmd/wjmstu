package com.sopia.classman.entities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.ClassConstants;
import com.sopia.common.SystemConfOp;
import com.sopia.common.getFloat;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElGroup;
import com.sopia.elclasspeice.entities.ElClassPeice;
import com.sopia.statman.entities.MyClass;

public class ElClass {
	private static final Log logger = LogFactory.getLog(ElClass.class);
	private int id;
	private String name;
	private String description;
	private String certificatename;
	private ElClType cltype;
	private ELUser creater;
	private float fee;
	private float fee2;
	private int optionalcredit;
	private int status;
	private int astatus;
	private int global; 
	private Date createtime;
	private Date modifytime;
	private List<Course> bxCourse;
	private List<Course> xxCourse;
	private List<Course> zxCourse;
	private int bxCount;
	private int xxCount;
	private int bxCredit;
	private int xxCredit;
	private int userCount;
	private int userPassedCount;
	private ELUser student;
	private Date applyDate;
	private String mainimg;
	private ElGroup group1;
	private ElGroup group2;
	private Timestamp diplomatime;
	/*** 培训班是否已经申请 */
	private int isSelect;  //add by lcw 附加属性，不需要提交至数据库
	/*** 培训班必修课 课程数/学分数 */
	private String bxStr;  //add by lcw 附加属性，不需要提交至数据库
	/*** 培训班选修课 课程数/学分数 */
	private String xxStr;  //add by lcw 附加属性，不需要提交至数据库
	/*** 培训班人数 */
	private int studentCount;  //add by lcw 附加属性，不需要提交至数据库
	private ELUser owner;
	private java.sql.Date begintime;
	private java.sql.Date endtime;
	
	
	private Timestamp starttime;
	private Timestamp finishtime;
	private int classtype;//0为常规，1为简易。
	private int classSize;
	private int operation;//培训班内是否有可操作考场 
	private int isnormal;

	private List<ELUser> valids; 	//复核人员
	private ELClassRegistration elRegistration;
	private int isApplication;		//是否可申请
	private int isuserApp;			//该学员是否可申请
	private String isjoin;			//是否参加该培训班
	private String isUvalid;		//是否有培训班人员
	private int uvalid;				//培训班复核状态
	private int isPastDue;			//培训班是否已过期
	private int planNumber;//可申请计划招收人数
	private String explain;//未通过说明  
	private int sqlw;//查询条件，如果是9查询已删除的培训班
	// 周攀 10月7 添加
	private int isPast;				//是否在学习时间
	private String depName;
	private String jingzhong;
	private List<ExamRoom> examRooms;
	
	private int isExists; //是否存在
	
	private int credit_bx ;//必修学分
	private int credit_xx ;//选修学分
	private float Kc_scoresAVG; //考试平均分
	private int Xf_Count;//学分总数
	private float Xs_Count;//学时总数
	
	//培训班价格
	private int elclasspeice;//培训班价格状态
	private ElClassPeice price;//培训班价格
	private int learnByOrder ; //是否顺序学习
	private int year ; //培训班的年份
	private int nazhengornianjian ; //培训班是拿证培训班还是年检培训班0代表拿证、1代表年检
	private int firstlearnlaterexam;//是否先学后考
	
	private int sortid;
	
	private BaseDatat baseData;
	private MyClass myClass;
	
	private double process;//学习进度
	private int finishCount;//完成的人数（进度为100）
	private int zeroCount;//未完成的人数（进度为0）
	private int otherCount;//其他人数（进度为0-100）
	private int count;//总人数
	
	//wsj20131202 部门学分统计
	private Department dep;
	private int totalCredit;//总学分
	private float avgCredit;
	
	private int hot;
	
	public String getHotName(){
		if(hot==ElConstants.HOT_RM) return "热门";
		if(hot==ElConstants.HOT_TJ) return "推荐";
		if(hot==ElConstants.HOT_ZD) return "重点";
		
		return "普通";
	}
	
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public Department getDep() {
		return dep;
	}
	public void setDep(Department dep) {
		this.dep = dep;
	}
	public int getTotalCredit() {
		return totalCredit;
	}
	public void setTotalCredit(int totalCredit) {
		this.totalCredit = totalCredit;
	}
	public float getAvgCredit() {
		return avgCredit;
	}
	public void setAvgCredit(float avgCredit) {
		this.avgCredit = avgCredit;
	}
	public double getProcess() {
		return process;
	}
	public void setProcess(double process) {
		this.process = process;
	}
	public int getFinishCount() {
		return finishCount;
	}
	public void setFinishCount(int finishCount) {
		this.finishCount = finishCount;
	}
	public int getZeroCount() {
		return zeroCount;
	}
	public void setZeroCount(int zeroCount) {
		this.zeroCount = zeroCount;
	}
	public int getOtherCount() {
		return otherCount;
	}
	public void setOtherCount(int otherCount) {
		this.otherCount = otherCount;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public MyClass getMyClass() {
		return myClass;
	}
	public void setMyClass(MyClass myClass) {
		this.myClass = myClass;
	}
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getNazhengornianjian() {
		return nazhengornianjian;
	}
	public void setNazhengornianjian(int nazhengornianjian) {
		this.nazhengornianjian = nazhengornianjian;
	}
	public int getLearnByOrder() {
		return learnByOrder;
	}
	public void setLearnByOrder(int learnByOrder) {
		this.learnByOrder = learnByOrder;
	}
	public int getElclasspeice() {
		return elclasspeice;
	}
	public void setElclasspeice(int elclasspeice) {
		this.elclasspeice = elclasspeice;
	}
	public ElClassPeice getPrice() {
		return price;
	}
	public void setPrice(ElClassPeice price) {
		this.price = price;
	}
	public float getXs_Count() {
		return Xs_Count;
	}
	public int getXf_Count() {
		return Xf_Count;
	}
	public float getKc_scoresAVG() {
		return Kc_scoresAVG;
	}
	public int getCredit_bx() {
		return credit_bx;
	}
	public void setCredit_bx(int credit_bx) {
		this.credit_bx = credit_bx;
	}
	public int getCredit_xx() {
		return credit_xx;
	}
	public void setCredit_xx(int credit_xx) {
		this.credit_xx = credit_xx;
	}
	public int getIsExists() {
		return isExists;
	}
	public void setIsExists(int isExists) {
		this.isExists = isExists;
	}
	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}
	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}
	public int getIsPast() { 
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
//		Timestamp sys = new Timestamp(System.currentTimeMillis());//获取系统当前时间
//		String systime = df.format(sys);
//
//		Timestamp Start = new Timestamp(this.elRegistration.getRegistrationStartTime().getTime());//开始时间 
//		String StartTime = df.format(Start);
//		Timestamp Stop = new Timestamp(this.elRegistration.getRegistrationStopTime().getTime());//结束时间 
//		String StopTime = df.format(Stop);
//		int StartCompare = compareTo(systime,StartTime);
//		int StopCompare = compareTo(StopTime,systime); 
//		boolean isStart = true;
//		boolean isStop = true;
//		if(StartCompare != -1){ 
//			isStart = true;
//		}else {
//			isStart = false; 
//		}
//		if(StopCompare == 1) { 
//			isStop = true;
//		}else{
//			isStop = false; 
//		} 
		Date d = new Date(); 
		boolean isStop = d.after(finishtime);
		 if(isStop){
			isPast = 2;//时间已过
		}else {
			isPast = 1;//可以购买
		}
		return isPast;
	}
	public void setIsPast(int isPast) {
		this.isPast = isPast;
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
	public int getSqlw() {
		return sqlw;
	}
	public void setSqlw(int sqlw) {
		this.sqlw = sqlw;
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
			logger.error("日期转换出错",e);
		} 
		return date; 
	}  

	public int getIsPastDue() { 
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
//		Timestamp sys = new Timestamp(System.currentTimeMillis());//获取系统当前时间
//		String systime = df.format(sys);
//
//		Timestamp Start = new Timestamp(this.elRegistration.getRegistrationStartTime().getTime());//开始时间 
//		String StartTime = df.format(Start);
//		Timestamp Stop = new Timestamp(this.elRegistration.getRegistrationStopTime().getTime());//结束时间 
//		String StopTime = df.format(Stop);
//		int StartCompare = compareTo(systime,StartTime);
//		int StopCompare = compareTo(StopTime,systime); 
//		boolean isStart = true;
//		boolean isStop = true;
//		if(StartCompare != -1){ 
//			isStart = true;
//		}else {
//			isStart = false; 
//		}
//		if(StopCompare == 1) { 
//			isStop = true;
//		}else{
//			isStop = false; 
//		} 
		Date d = new Date(); 
		boolean isStart = !d.before(elRegistration.getRegistrationStartTime());
		boolean isStop = !d.after(elRegistration.getRegistrationStopTime());;
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
	public int getUvalid() {
		return uvalid;
	}
	public void setUvalid(int uvalid) {
		this.uvalid = uvalid;
	}
	public String getIsUvalid() {
		return isUvalid;
	}
	public void setIsUvalid(String isUvalid) {
		this.isUvalid = isUvalid;
	}
	public String getIsjoin() {
		return isjoin;
	}
	public void setIsjoin(String isjoin) {
		this.isjoin = isjoin;
	}
	public ELClassRegistration getElRegistration() {
		return elRegistration;
	}
	public void setElRegistration(ELClassRegistration elRegistration) {
		this.elRegistration = elRegistration;
	}
	public int getIsnormal() {
		return isnormal;
	}
	public void setIsnormal(int isnormal) {
		this.isnormal = isnormal;
	} 
	public int getClassSize() {
		return classSize;
	}
	public void setClassSize(int classSize) {
		this.classSize = classSize;
	}
	public int getClasstype() {
		return classtype;
	}
	public void setClasstype(int classtype) {
		this.classtype = classtype;
	}
	public Timestamp getStarttime() {
		return starttime;
	}
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}
	public Timestamp getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(Timestamp finishtime) {
		this.finishtime = finishtime;
	}
	public String getStarttimeFmt() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(starttime);
	}
	public String getFinishtimeFmt() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(finishtime);
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
	public String getBxStr() {
		return bxStr;
	}
	public void setBxStr(String bxStr) {
		this.bxStr = bxStr;
	}
	public String getXxStr() {
		return xxStr;
	}
	public void setXxStr(String xxStr) {
		this.xxStr = xxStr;
	}
	//	private float passper;
	public Timestamp getDiplomatime() {
		return diplomatime;
	}
	public void setDiplomatime(Timestamp diplomatime) {
		this.diplomatime = diplomatime;
	}
	public ElGroup getGroup1() {
		return group1;
	}
	public void setGroup1(ElGroup group1) {
		this.group1 = group1;
	}
	public ElGroup getGroup2() {
		return group2;
	}
	public void setGroup2(ElGroup group2) {
		this.group2 = group2;
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
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public List<Course> getBxCourse() {
		return bxCourse;
	}
	public void setBxCourse(List<Course> bxCourse) {
		this.bxCourse = bxCourse;
	}
	public List<Course> getXxCourse() {
		return xxCourse;
	}
	public void setXxCourse(List<Course> xxCourse) {
		this.xxCourse = xxCourse;
	}
	public int getBxCount() {
		return bxCount;
	}
	public void setBxCount(int bxCount) {
		this.bxCount = bxCount;
	}
	public int getXxCount() {
		return xxCount;
	}
	public void setXxCount(int xxCount) {
		this.xxCount = xxCount;
	}
	public int getBxCredit() {
		return bxCredit;
	}
	public void setBxCredit(int bxCredit) {
		this.bxCredit = bxCredit;
	}
	public int getXxCredit() {
		return xxCredit;
	}
	public void setXxCredit(int xxCredit) {
		this.xxCredit = xxCredit;
	}
	public ElClass() {
	}
	public ElClass(int id) {
		this.id =id;
	}
	public ElClass(int id,String name)
	{
		this.id = id;
		this.name=name;
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
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCertificatename() {
		return certificatename;
	}
	public void setCertificatename(String certificatename) {
		this.certificatename = certificatename;
	}
	public ElClType getCltype() {
		return cltype;
	}
	public void setCltype(ElClType cltype) {
		this.cltype = cltype;
	}
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
	public float getFee() {
		return fee;
	}
	public void setFee(float fee) {
		this.fee = fee;
	}
	public float getFee2() {
		return fee2;
	}
	public void setFee2(float fee2) {
		this.fee2 = fee2;
	}
	public int getOptionalcredit() {
		return optionalcredit;
	}
	public void setOptionalcredit(int optionalcredit) {
		this.optionalcredit = optionalcredit;
	}
	public int getStatus() {
		return status;
	}
	public String getStatusName() { 
		if(status==ClassConstants.CLASS_STATUS_INMAKING) return "制作中";
//		else if(status==ClassConstants.CLASS_STATUS_PRELIMINARYEXAMINATION_WAIT) return "初审等待中";
//		else if(status==ClassConstants.CLASS_STATUS_PRELIMINARYEXAMINATION_NOTGO) return "初审不通过";
//		else if(status==ClassConstants.CLASS_STATUS_FINAL_WAIT) return "终审等待中";
//		else if(status==ClassConstants.CLASS_STATUS_FINAL_NOTGO) return "终审不通过";
		else if(status==ClassConstants.CLASS_STATUS_PRELIMINARYEXAMINATION_WAIT) return "申请等待中";
		else if(status==ClassConstants.CLASS_STATUS_PRELIMINARYEXAMINATION_NOTGO) return "待修改";
		else if(status==ClassConstants.CLASS_STATUS_FINAL_WAIT) return "审核等待中";
		else if(status==ClassConstants.CLASS_STATUS_FINAL_NOTGO) return "审核不通过";
		else if(status==ClassConstants.CLASS_STATUS_HASOPENED) return "已开通"; 
		else if(status==ClassConstants.CLASS_STATUS_ALTER_WAIT) return "修改等待中"; 
		else if(status==ClassConstants.CLASS_STATUS_ALTER) return "修改中";  
		else if(status==ClassConstants.CLASS_STATUS_DELETE_WAIT) return "删除等待中"; 
		else if(status==ClassConstants.CLASS_STATUS_DELETE) return "已删除";   
		else if(status==ClassConstants.CLASS_STATUS_CLOSE) return "关闭";     
		else if(status==ClassConstants.CLASS_STATUS_SUSPENDED) return "暂停";   
		else return "未知类型";  
	}
	public void setStatus(int status) {
		this.status = status;
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
	public ELUser getStudent() {
		return student;
	}
	public void setStudent(ELUser student) {
		this.student = student;
	}
	public int getUserPassedCount() {
		return userPassedCount;
	}
	public void setUserPassedCount(int userPassedCount) {
		this.userPassedCount = userPassedCount;
	}
	public List<Course> getZxCourse() {
		return zxCourse;
	}
	public void setZxCourse(List<Course> zxCourse) {
		this.zxCourse = zxCourse;
	}
	public int getGlobal() {
		return global;
	}
	public void setGlobal(int global) {
		this.global = global;
	}
	public float getPassper() {
		java.text.DecimalFormat myformat=new java.text.DecimalFormat( " #0.00 " );  
		return Float.parseFloat(myformat.format(userCount==0?0:userPassedCount*100.0f/userCount));
	}
	public int getIsSelect() {
		return isSelect;
	}
	public void setIsSelect(int isSelect) {
		this.isSelect = isSelect;
	}
	public int getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}
	public int getAstatus() {
		return astatus;
	}
	public void setAstatus(int astatus) {
		this.astatus = astatus;
	}
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	public List<ELUser> getValids() {
		return valids;
	}
	public void setValids(List<ELUser> valids) {
		this.valids = valids;
	}
	public int getIsApplication() {
		return isApplication;
	}
	public void setIsApplication(int isApplication) {
		this.isApplication = isApplication;
	}
	public int getIsuserApp() {
		return isuserApp;
	}
	public void setIsuserApp(int isuserApp) {
		this.isuserApp = isuserApp;
	}
	public int getPlanNumber() {
		return planNumber;
	}
	public void setPlanNumber(int planNumber) {
		this.planNumber = planNumber;
	} 
	public void setKc_scoresAVG(float kc_scoresAVG) {
		Kc_scoresAVG = getFloat.GetFloat(kc_scoresAVG);
	}
	public void setXf_Count(int xf_Count) {
		Xf_Count = xf_Count;
	}
	public void setXs_Count(float xs_Count) {
		Xs_Count = xs_Count;
	}
	public BaseDatat getBaseData() {
		return baseData;
	}
	public void setBaseData(BaseDatat baseData) {
		this.baseData = baseData;
	}
	
	public String getWjmName(){
		if(this.name!=null && !this.name.equals("")){
			
		}
		return "";
	}
	public int getFirstlearnlaterexam() {
		return firstlearnlaterexam;
	}
	public void setFirstlearnlaterexam(int firstlearnlaterexam) {
		this.firstlearnlaterexam = firstlearnlaterexam;
	}
	
//	public void setPassper(float passper) {
//		this.passper = passper;
//	}
	
}
