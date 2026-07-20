package com.sopia.studyman.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.ELUser;

public class MyRoom {
	private int id;
	private ELUser tester;
	private ExamRoom examroom;
	private int ispassed;//0:未通过 1:已通过 3:未开始
	private int mySort;
	private float myScore;
	private int status;// 考生考场状态，0.缺考 1.未做完 2.已做完 3.批阅中 4.已批阅 -1.需要审核的状态，审核后状态为0
	private int epsize;
	private int valid;
//	private int practimes;
//	private int pracscore;
	private String macAddress;
	private String ipAddress;
	private Timestamp begintime;
	private Timestamp endtime;
	private List<MyExamPaper> myExamPapers;
	private MyExamPaper myExamPaper;
	private int mycount;
	private int minstatus;
	private boolean canExam;
	
	
	public MyExamPaper getMyExamPaper() {
		return myExamPaper;
	}
	public void setMyExamPaper(MyExamPaper myExamPaper) {
		this.myExamPaper = myExamPaper;
	}
	public boolean isCanExam() {
		return canExam;
	}
	public void setCanExam(boolean canExam) {
		this.canExam = canExam;
	}
	public int getMinstatus() {
		return minstatus;
	}
	public void setMinstatus(int minstatus) {
		this.minstatus = minstatus;
	}
	public int getMycount() {
		return mycount;
	}
	public void setMycount(int mycount) {
		this.mycount = mycount;
	}
//	private List<MyRoomRecord> myRoomRecord;
//	private int srrcount;//已考次数
//	public int getSrrcount() {
//		return srrcount;
//	}
//	public void setSrrcount(int srrcount) {
//		this.srrcount = srrcount;
//	}
//	public List<MyRoomRecord> getMyRoomRecord() {
//		return myRoomRecord;
//	}
//	public void setMyRoomRecord(List<MyRoomRecord> myRoomRecord) {
//		this.myRoomRecord = myRoomRecord;
//	}
	public MyRoom() {
		// TODO Auto-generated constructor stub
	}
	public MyRoom(int id){
		this.id = id;
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
	/**
	 * 获取考场状态（0.缺考 1.未做完 2.已做完 3.批阅中 4.已批阅）
	 * @return
	 */
	public String getStatusName() {
		if (status == 1)
			return "未做完";
		if (status == 2)
			return "已做完";
		if (status == 3)
			return "批阅中";
		if (status == 4)
			return "已批阅";
//		if (status == -1)
//			return "审核中";
		return "缺考";
	}
	
//	public String getStatusName() {
//		if (status == 1)
//			return "考试中";
//		if (status == 2)
//			return "正常交卷";
//		if (status == 3)
//			return "已批改";
//		if (status == 4)
//			return "暂停";
//		if (status == -1)
//			return "场次审核中";
//		return "未开始";
//	}


	public ExamRoom getExamroom() {
		return examroom;
	}

	public void setExamroom(ExamRoom examroom) {
		this.examroom = examroom;
	}

	public float getMyScore() {
		return myScore;
	}
	public void setMyScore(float myScore) {
		this.myScore = myScore;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getBegintime() {
		return begintime;
	}
	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}
	/**
	 * @return the endtime
	 */
	public Timestamp getEndtime() {
		return endtime;
	}
	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
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
