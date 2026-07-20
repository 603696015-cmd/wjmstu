package com.sopia.courseman.entities;

import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import java.sql.Timestamp;


public class Examprac {
	private int id;
	private String title;
	private String description;
	private ExamPaper examPaper;
	private Timestamp begintime;
	private Timestamp endtime;
	private ELUser user;
	private int valid;
	
	private int usersize;
	private int totalnumber;
	private float passreta;//及格率
	private float avgnumber;//平均次
	private float personalscores;//个人练习总分 
	private float avgscore;//全部人员平均分的平均分
	private int passsize;//及格人数
	private int pass9_ ;//90分以上
	private int pass8_9;//	80-90分	(都是级下 不级上,就是说包括80分，不包括90分)
	private int pass7_8;//70-80
	private int pass6_7;//	60-70
	private int pass_6;//60以下  //
	private int pass5_6;//50-60分
	private int pass4_5;//40-50分
	private int pass3_4;//30-40分
	private int pass2_3;//20-30分
	private int pass1_2;//10-20分
	private int pass0_1;//0-10分

	private float avgscorejoin;//已练习人员平均分
	private float passreta2;//已练习人员及格率
	private int pracCount;//可练习的次数
	private int passgrade;//通过成绩(%)
	
	public int getPassgrade() {
		return passgrade;
	}
	public void setPassgrade(int passgrade) {
		this.passgrade = passgrade;
	}
	public int getPracCount() {
		return pracCount;
	}
	public void setPracCount(int pracCount) {
		this.pracCount = pracCount;
	}
	public float getAvgscorejoin() {
		return avgscorejoin;
	}
	public void setAvgscorejoin(float avgscorejoin) {
		this.avgscorejoin = avgscorejoin;
	}
	public float getPassreta2() {
		return passreta2;
	}
	public void setPassreta2(float passreta2) {
		this.passreta2 = passreta2;
	}
	public String getValidName() {
		if(valid==1) return "场次已开通";
		else if(valid==0) return "场次审核中";
		else if(valid==2) return "场次未通过";
		else if(valid==3) return "场次已暂停";
		else if(valid==4) return "场次已删除";
		else return "未知类型";
	}
	public Examprac() {
	}
	public Examprac(int id,String title){
		this.id = id;
		this.title  = title;
	}
	public int getUsersize() {
		return usersize;
	}
	public void setUsersize(int usersize) {
		this.usersize = usersize;
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
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
	public ExamPaper getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	public Timestamp getBegintime() {
		return begintime;
	}
	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public float getAvgscore() {
		return avgscore;
	}
	public void setAvgscore(float avgscore) {
		this.avgscore = avgscore;
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
	public float getPassreta() {
		return passreta;
	}
	public void setPassreta(float passreta) {
		this.passreta = passreta;
	}
	public float getAvgnumber() {
		return avgnumber;
	}
	public void setAvgnumber(float avgnumber) {
		this.avgnumber = avgnumber;
	}
	public float getPersonalscores() {
		return personalscores;
	}
	public void setPersonalscores(float personalscores) {
		this.personalscores = personalscores;
	}
	public int getTotalnumber() {
		return totalnumber;
	}
	public void setTotalnumber(int totalnumber) {
		this.totalnumber = totalnumber;
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
}
