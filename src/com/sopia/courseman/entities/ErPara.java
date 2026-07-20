package com.sopia.courseman.entities;

import com.sopia.common.ElNodeSQL;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;

public class ErPara {
	private ExamRoom examRoom;
	private int isPassed;//是否通过
	private int examCount;//考试次数
	private String examCountTerm;//考试次数条件(> = <)
	private float avgScore;//平均成绩
	private String avgScoreTerm;//平均成绩条件
	private float maxScore;//最高分
	private String maxScoreTerm;//最高分条件
	private String linkTerm;//考场与考场之间的连接条件
	private ELUser elUser;//部门id
	private float examScore;//考场成绩
	private String examScoreTerm;//考场成绩条件
	private ExamPaper examPaper;
	private int queryManner;//查询方式 1.按考场  2.按考场试卷
	public int getQueryManner() {
		return queryManner;
	}
	public void setQueryManner(int queryManner) {
		this.queryManner = queryManner;
	}
	public ExamPaper getExamPaper() {
		return examPaper;
	}
	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	public float getExamScore() {
		return examScore;
	}
	public void setExamScore(float examScore) {
		this.examScore = examScore;
	}
	public String getExamScoreTerm() {
		return examScoreTerm;
	}
	public void setExamScoreTerm(String examScoreTerm) {
		this.examScoreTerm = examScoreTerm;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public ExamRoom getExamRoom() {
		return examRoom;
	}
	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}
	public int getIsPassed() {
		return isPassed;
	}
	public void setIsPassed(int isPassed) {
		this.isPassed = isPassed;
	}
	public int getExamCount() {
		return examCount;
	}
	public void setExamCount(int examCount) {
		this.examCount = examCount;
	}
	public String getExamCountTerm() {
		return examCountTerm;
	}
	public void setExamCountTerm(String examCountTerm) {
		this.examCountTerm = examCountTerm;
	}
	public float getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(float avgScore) {
		this.avgScore = avgScore;
	}
	public String getAvgScoreTerm() {
		return avgScoreTerm;
	}
	public void setAvgScoreTerm(String avgScoreTerm) {
		this.avgScoreTerm = avgScoreTerm;
	}
	public float getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
	}
	public String getMaxScoreTerm() {
		return maxScoreTerm;
	}
	public void setMaxScoreTerm(String maxScoreTerm) {
		this.maxScoreTerm = maxScoreTerm;
	}
	public String getLinkTerm() {
		return linkTerm;
	}
	public void setLinkTerm(String linkTerm) {
		this.linkTerm = linkTerm;
	}
	public String getTermSql(){
		String tempStr="";
		if(this.getIsPassed()==2){
			tempStr=" and ispassed=0";
		}else if(this.getIsPassed()==1){
			tempStr=" and ispassed=1";
		}
		//return " (select euu.* from (select * from study_room where roomid="+this.getExamRoom().getId()+tempStr+" and eroomcount"+this.getExamCountTerm()+this.getExamCount()+" and avgscore"+this.getAvgScoreTerm()+this.getAvgScore()+" and maxscore"+this.getMaxScoreTerm()+this.getMaxScore()+") sr left join (select eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,el.id elid,el.name elname,eu.sex,eu.jingzhong,eu.shengri from eluser eu left join department dep on eu.depid=dep.id left join elrole el on eu.role=el.id) euu on sr.userid=euu.euid) ";
		//return " (select euu.* from (select * from study_room where roomid="+this.getExamRoom().getId()+tempStr+" and eroomcount"+this.getExamCountTerm()+this.getExamCount()+" and avgscore"+this.getAvgScoreTerm()+this.getAvgScore()+" and maxscore"+this.getMaxScoreTerm()+this.getMaxScore()+") sr left join (select eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,el.id elid,el.name elname,eu.sex,eu.jingzhong,eu.shengri,dep.lid,dep.rid from eluser eu left join department dep on eu.depid=dep.id left join elrole el on eu.role=el.id) euu on sr.userid=euu.euid where "+ElNodeSQL.getWhereSql(this.getElUser().getDepartment().getId(),this.getElUser().getId(),"euu")+") ";
		if(this.queryManner==1){
			return " (select euu.* from (select * from study_room where roomid="+this.getExamRoom().getId()+tempStr+" and myscore"+this.getExamScoreTerm()+this.getExamScore()+") sr left join (select eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,el.id elid,el.name elname,eu.sex,eu.jingzhong,eu.shengri,dep.lid,dep.rid from eluser eu left join department dep on eu.depid=dep.id left join elrole el on eu.role=el.id) euu on sr.userid=euu.euid where "+ElNodeSQL.getWhereSql_use(this.getElUser().getDepartment().getId(),this.getElUser().getId(),"euu")+") ";
		}else{
			return " (select euu.* from (select * from study_exampaper where roomid="+this.getExamRoom().getId()+tempStr+" and epid="+this.getExamPaper().getId()+" and quizcount"+this.getExamCountTerm()+this.getExamCount()+" and avgscore"+this.getAvgScoreTerm()+this.getAvgScore()+" and maxscore"+this.getMaxScoreTerm()+this.getMaxScore()+") sr left join (select eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,el.id elid,el.name elname,eu.sex,eu.jingzhong,eu.shengri,dep.lid,dep.rid from eluser eu left join department dep on eu.depid=dep.id left join elrole el on eu.role=el.id) euu on sr.userid=euu.euid where "+ElNodeSQL.getWhereSql_use(this.getElUser().getDepartment().getId(),this.getElUser().getId(),"euu")+") ";
		}
	}
	public String getTermSimpleSql(){
		String tempStr="";
		if(this.getIsPassed()==2){
			tempStr=" and ispassed=0";
		}else if(this.getIsPassed()==1){
			tempStr=" and ispassed=1";
		}
		//return " (select euu.* from (select * from study_room where roomid="+this.getExamRoom().getId()+tempStr+" and eroomcount"+this.getExamCountTerm()+this.getExamCount()+" and avgscore"+this.getAvgScoreTerm()+this.getAvgScore()+" and maxscore"+this.getMaxScoreTerm()+this.getMaxScore()+") sr left join (select eu.id euid from eluser eu left join department dep on eu.depid=dep.id left join elrole el on eu.role=el.id) euu on sr.userid=euu.euid) ";
		//return " (select euu.* from (select * from study_room where roomid="+this.getExamRoom().getId()+tempStr+" and eroomcount"+this.getExamCountTerm()+this.getExamCount()+" and avgscore"+this.getAvgScoreTerm()+this.getAvgScore()+" and maxscore"+this.getMaxScoreTerm()+this.getMaxScore()+") sr left join (select eu.id euid,dep.lid,dep.rid from eluser eu left join department dep on eu.depid=dep.id left join elrole el on eu.role=el.id) euu on sr.userid=euu.euid where "+ElNodeSQL.getWhereSql(this.getElUser().getDepartment().getId(),this.getElUser().getId(),"euu")+") ";
		if(this.queryManner==1){
			return " (select euu.* from (select * from study_room where roomid="+this.getExamRoom().getId()+tempStr+" and myscore"+this.getExamScoreTerm()+this.getExamScore()+") sr left join (select eu.id euid,dep.lid,dep.rid from eluser eu left join department dep on eu.depid=dep.id left join elrole el on eu.role=el.id) euu on sr.userid=euu.euid where "+ElNodeSQL.getWhereSql_use(this.getElUser().getDepartment().getId(),this.getElUser().getId(),"euu")+") ";
		}else{
			return " (select euu.* from (select * from study_exampaper where roomid="+this.getExamRoom().getId()+tempStr+" and epid="+this.getExamPaper().getId()+" and quizcount"+this.getExamCountTerm()+this.getExamCount()+" and avgscore"+this.getAvgScoreTerm()+this.getAvgScore()+" and maxscore"+this.getMaxScoreTerm()+this.getMaxScore()+") sr left join (select eu.id euid,dep.lid,dep.rid from eluser eu left join department dep on eu.depid=dep.id left join elrole el on eu.role=el.id) euu on sr.userid=euu.euid where "+ElNodeSQL.getWhereSql_use(this.getElUser().getDepartment().getId(),this.getElUser().getId(),"euu")+") ";
		}
	}
}
