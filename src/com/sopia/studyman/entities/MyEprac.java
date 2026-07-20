package com.sopia.studyman.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.entities.ELUser;

public class MyEprac {
	private int id;
	private ELUser tester;
	private Examprac prac;
	private int ispassed;
	private int mySort;
	private int myScore;
	private int status;// 考生考试状态，0：未参加、1考试中、2已提交
	private int epsize;

	private int times;
	private float maxscore;
	private float totalscore;
	private float avgscore;
	private long starttime;
	private int valid;
	private Timestamp endtime;
	private List<MyExamPaper> myExamPapers;
	private String pracStatus;//练习状态
	private float passScore;//通过成绩
	private int passCount;//学员通过次数
	private int classid ;
	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public int getPassCount() {
		return passCount;
	}

	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}

	public float getPassScore() {
		return passScore;
	}

	public void setPassScore(float passScore) {
		this.passScore = passScore;
	}

	public String getPracStatus() {
		return pracStatus;
	}

	public void setPracStatus(String pracStatus) {
		this.pracStatus = pracStatus;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public float getMaxscore() {
		return maxscore;
	}

	public void setMaxscore(float maxscore) {
		this.maxscore = maxscore;
	}

	public float getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(float totalscore) {
		this.totalscore = totalscore;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public List<MyExamPaper> getMyExamPapers() {
		return myExamPapers;
	}

	public void setMyExamPapers(List<MyExamPaper> myExamPapers) {
		this.myExamPapers = myExamPapers;
	}

	public int getEpsize() {
		return epsize;
	}

	public void setEpsize(int epsize) {
		this.epsize = epsize;
	}

	public int getIspassed() {
		return ispassed;
	}

	public void setIspassed(int ispassed) {
		this.ispassed = ispassed;
	}

	public int getMySort() {
		return mySort;
	}

	public void setMySort(int mySort) {
		this.mySort = mySort;
	} 

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ELUser getTester() {
		return tester;
	}

	public void setTester(ELUser tester) {
		this.tester = tester;
	}
	public String getStatusName() {
		if (status == 1)
			return "考试中";
		if (status == 2)
			return "正常交卷";
		if (status == 3)
			return "已批改";
		if (status == 4)
			return "暂停";
		if (status == -1)
			return "场次审核中";
		return "未开始";
	}
	public int getMyScore() {
		return myScore;
	}
	public void setMyScore(int myScore) {
		this.myScore = myScore;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Examprac getPrac() {
		return prac;
	}

	public void setPrac(Examprac prac) {
		this.prac = prac;
	}

	public long getStarttime() {
		return starttime;
	}

	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}

	public float getAvgscore() {
		return avgscore;
	}

	public void setAvgscore(float avgscore) {
		this.avgscore = avgscore;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

//	public int getPractimes() {
//		return practimes;
//	}
//
//	public void setPractimes(int practimes) {
//		this.practimes = practimes;
//	}
//
//	public int getPracscore() {
//		return pracscore;
//	}
//
//	public void setPracscore(int pracscore) {
//		this.pracscore = pracscore;
//	}
	
}
