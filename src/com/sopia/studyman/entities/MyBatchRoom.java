package com.sopia.studyman.entities;

import java.util.List;

import com.sopia.courseman.entities.EroomBatch;
import com.sopia.duman.entities.ELUser;

public class MyBatchRoom {
	private ELUser tester;
	private EroomBatch eroomBatch;
	private int ispassed;
	private int mySort;
	private float myScore;
	private int status;// 考生考试状态，0：未参加、1考试中、2已提交
	private int epsize;
	private List<MyRoom> myRooms;
	private List<MyExamPaper> myExampapers;
	public List<MyExamPaper> getMyExampapers() {
		return myExampapers;
	}

	public void setMyExampapers(List<MyExamPaper> myExampapers) {
		this.myExampapers = myExampapers;
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


	public EroomBatch getEroomBatch() {
		return eroomBatch;
	}

	public void setEroomBatch(EroomBatch eroomBatch) {
		this.eroomBatch = eroomBatch;
	}

	public List<MyRoom> getMyRooms() {
		return myRooms;
	}

	public void setMyRooms(List<MyRoom> myRooms) {
		this.myRooms = myRooms;
	}

	public float getMyScore() {
		return myScore;
	}
	public void setMyScore(float myScore) {
		this.myScore = myScore;
	}

  
}
