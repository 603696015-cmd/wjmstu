package com.sopia.studyman.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.ElConstants;
import com.sopia.duman.entities.ELUser;
import com.sopia.talentman.entities.ZTRoom;

public class MyZTRoom {
	private ELUser evaler;
	private ELUser tester;
//	private KTRoom troom;
	private ZTRoom ztroom;
	private int evaltype;
	private String evaldetail;
	private String[] evaldetails;
	private Timestamp evaltime;
	private List<ELUser> testers;
//	private int myScore;
	private int zjScore;
	private int tsScore;
	private int sjscore;
	
//	public int getMyScore() {
//		return myScore;
//	}
//	public void setMyScore(int myScore) {
//		this.myScore = myScore;
//	}
	public int getZjScore() {
		return zjScore;
	}
	public void setZjScore(int zjScore) {
		this.zjScore = zjScore;
	}
	public int getTsScore() {
		return tsScore;
	}
	public void setTsScore(int tsScore) {
		this.tsScore = tsScore;
	}
	public int getSjscore() {
		return sjscore;
	}
	public void setSjscore(int sjscore) {
		this.sjscore = sjscore;
	}
	public List<ELUser> getTesters() {
		return testers;
	}
	public void setTesters(List<ELUser> testers) {
		this.testers = testers;
	}
	public ELUser getTester() {
		return tester;
	}
	public void setTester(ELUser tester) {
		this.tester = tester;
	}

	public ZTRoom getZtroom() {
		return ztroom;
	}
	public void setZtroom(ZTRoom ztroom) {
		this.ztroom = ztroom;
	}
	public int getEvaltype() {
		return evaltype;
	}
	public void setEvaltype(int evaltype) {
		this.evaltype = evaltype;
	}
	public String getEvaldetail() {
		if(null!=evaldetails){ 
			evaldetail="";
			for (int i = 0; i < evaldetails.length; i++) {
				evaldetail += evaldetails[i]+ElConstants.optSplit;
			}
		}
		return evaldetail;
	}
	public void setEvaldetail(String evaldetail) {
		this.evaldetail = evaldetail;
	}
	public String[] getEvaldetails() {
		if(null!=evaldetail){
			evaldetails = evaldetail.split(ElConstants.optSplit);
		}
		return evaldetails;
	}
	public void setEvaldetails(String[] evaldetails) {
		this.evaldetails = evaldetails;
	}
	public Timestamp getEvaltime() {
		return evaltime;
	}
	public void setEvaltime(Timestamp evaltime) {
		this.evaltime = evaltime;
	}
	public ELUser getEvaler() {
		return evaler;
	}
	public void setEvaler(ELUser evaler) {
		this.evaler = evaler;
	}
	
	
}
