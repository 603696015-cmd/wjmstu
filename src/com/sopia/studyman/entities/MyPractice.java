package com.sopia.studyman.entities;

import java.sql.Timestamp;

import com.sopia.courseman.entities.PracticePaper;
import com.sopia.duman.entities.ELUser;

public class MyPractice {
	private PracticePaper ppaper;
	private ELUser user;
	private float myScore;
	private Timestamp lasttime;
	public PracticePaper getPpaper() {
		return ppaper;
	}
	public void setPpaper(PracticePaper ppaper) {
		this.ppaper = ppaper;
	}
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public float getMyScore() {
		return myScore;
	}
	public void setMyScore(float myScore) {
		this.myScore = myScore;
	}
	public Timestamp getLasttime() {
		return lasttime;
	}
	public void setLasttime(Timestamp lasttime) {
		this.lasttime = lasttime;
	}
	
}
