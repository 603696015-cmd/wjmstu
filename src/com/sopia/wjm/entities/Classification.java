package com.sopia.wjm.entities;

import com.sopia.classman.entities.ElClass;

/**
 * 级别实体
 * @author Administrator
 *
 */
public class Classification {
	private String name;		//级别名称
	private int scorebegin;		//级别开始成绩
	private int scoreend;		//级别结束成绩
	private ElClass elClass;
	
	public Classification(){}
	public Classification(String name){
		this.name = name;
	}
	public Classification(String name,int scorebegin,int scoreend){
		this.name = name;
		this.scorebegin = scorebegin;
		this.scoreend = scoreend;
	}
	
	public ElClass getElClass() {
		return elClass;
	}
	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScorebegin() {
		return scorebegin;
	}
	public void setScorebegin(int scorebegin) {
		this.scorebegin = scorebegin;
	}
	public int getScoreend() {
		return scoreend;
	}
	public void setScoreend(int scoreend) {
		this.scoreend = scoreend;
	}
	
	
}
