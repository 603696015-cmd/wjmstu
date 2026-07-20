package com.sopia.studyman.entities;

import java.util.List;

import com.sopia.duman.entities.ELUser;
import com.sopia.talentman.entities.KTRoomColl;

public class MyKTRoomC {
	private KTRoomColl troomcoll;
	private ELUser tester;
	private int totalscore;
	private int avgscore;
	private int quizcount;
	private List<MyKTRoom> myktrooms;
	public List<MyKTRoom> getMyktrooms() {
		return myktrooms;
	}
	public void setMyktrooms(List<MyKTRoom> myktrooms) {
		this.myktrooms = myktrooms;
	}
	public int getQuizcount() {
		return quizcount;
	}
	public void setQuizcount(int quizcount) {
		this.quizcount = quizcount;
	}
	public KTRoomColl getTroomcoll() {
		return troomcoll;
	}
	public void setTroomcoll(KTRoomColl troomcoll) {
		this.troomcoll = troomcoll;
	}
	public ELUser getTester() {
		return tester;
	}
	public void setTester(ELUser tester) {
		this.tester = tester;
	}
	public int getTotalscore() {
		return totalscore;
	}
	public void setTotalscore(int totalscore) {
		this.totalscore = totalscore;
	}
	public int getAvgscore() {
		avgscore = quizcount==0?0:totalscore/quizcount;
		return avgscore;
	}
	public void setAvgscore(int avgscore) {
		this.avgscore = avgscore;
	}
	
}
