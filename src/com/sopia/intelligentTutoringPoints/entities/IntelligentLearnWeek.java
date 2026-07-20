package com.sopia.intelligentTutoringPoints.entities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sopia.duman.entities.ELUser;
/**
 * 智能辅导分之周学习时长
 * @author Administrator
 *
 */
public class IntelligentLearnWeek {
	private int id;
	private int userid;
	private Date begintime;//学习开始时间
	private Date endtime;//学习结束时间
	private int learnTime;//学习时长 （INT）
	private String today;//学习在哪天20130816
	
	private double totalScore;//总分
	private double score;//每次学习得分
	
	private int classid;//培训班id
	private int courseid;//课程id
	private int pageid;//章节id
	private String weekBegin;//本周周开始时间20130816
	private String weekEnd;//本周结束时间
	private ELUser elUser;
	public IntelligentLearnWeek(){
		
	}
	public IntelligentLearnWeek(int userid,double totalscore,int classid,int courseid,int pageid,String weekBegin,String weekEnd,int learnTime){
		this.userid = userid;
		this.totalScore = totalscore;
		this.classid = classid;
		this.courseid = courseid;
		this.pageid = pageid;
		this.weekBegin = weekBegin;
		this.weekEnd = weekEnd;
		this.learnTime = learnTime;
	}
	public Date getBegintime() throws ParseException{
		if(weekBegin!=null&&!weekBegin.equals("")){
			return new SimpleDateFormat("yyyyMMdd").parse(weekBegin);
		}else{
			return null;
		}
	}
	public Date getEndtime() throws ParseException{
		if(weekEnd!=null&&!weekEnd.equals("")){
			return new SimpleDateFormat("yyyyMMdd").parse(weekEnd);
		}else{
			return null;
		}
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public String getWeekBegin() {
		return weekBegin;
	}
	public void setWeekBegin(String weekBegin) {
		this.weekBegin = weekBegin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getLearnTime() {
		return learnTime;
	}
	public void setLearnTime(int learnTime) {
		this.learnTime = learnTime;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public int getPageid() {
		return pageid;
	}
	public void setPageid(int pageid) {
		this.pageid = pageid;
	}
	public String getWeekEnd() {
		return weekEnd;
	}
	public void setWeekEnd(String weekEnd) {
		this.weekEnd = weekEnd;
	}
	

	
}
