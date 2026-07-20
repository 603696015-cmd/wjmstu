package com.sopia.studyman.entities;

import com.sopia.duman.entities.ELUser;
import com.sopia.talentman.entities.KTRoom;

public class MyKTRoom {
	private ELUser tester;
	private KTRoom troom;
	
	private int myScore;

	public ELUser getTester() {
		return tester;
	}

	public void setTester(ELUser tester) {
		this.tester = tester;
	}

	public KTRoom getTroom() {
		return troom;
	}

	public void setTroom(KTRoom troom) {
		this.troom = troom;
	}

	public int getMyScore() {
		return myScore;
	}
	public void setMyScore(int myScore) {
		this.myScore = myScore;
	}
	
}
