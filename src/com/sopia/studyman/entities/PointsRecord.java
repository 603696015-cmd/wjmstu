package com.sopia.studyman.entities; 

import com.sopia.classman.entities.ElClass;
import com.sopia.duman.entities.ELUser;

public class PointsRecord {  
	private ELUser user;
	private ElClass elclass;
	private float cscore;//培训班得分
	private float fscore;//非培训班得分
	private float addscore;//管理员增加得分
	private float totalscore;//总分
	
	
	
	
	public ELUser getUser() {
		return user;
	}
	public void setUser(ELUser user) {
		this.user = user;
	}
	public ElClass getElclass() {
		return elclass;
	}
	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}
	public float getCscore() {
		return cscore;
	}
	public void setCscore(float cscore) {
		this.cscore = cscore;
	}
	public float getFscore() {
		return fscore;
	}
	public void setFscore(float fscore) {
		this.fscore = fscore;
	}
	public float getAddscore() {
		return addscore;
	}
	public void setAddscore(float addscore) {
		this.addscore = addscore;
	}
	public float getTotalscore() {
		return totalscore;
	}
	public void setTotalscore(float totalscore) {
		this.totalscore = totalscore;
	}
}
