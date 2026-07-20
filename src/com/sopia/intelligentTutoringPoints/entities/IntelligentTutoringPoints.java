package com.sopia.intelligentTutoringPoints.entities;
/**
 * 智能辅导分实体
 * @author TMK
 *
 */
public class IntelligentTutoringPoints {
	private int userid;				//用户ID
	private float totalScore;		//智能辅导总分
	private int classid;			//培训班ID
	private float scoreLogin;		//登录得分
	private float scoreWeek;		//周学习时间得分
	private float scoreClass;		//培训班学习总时间得分
	private float scoreProportion;	//复听得分
	private float scoreProportionQ;	//复听数量得分
	private float scoreProportionT;	//复听次数得分
	private float scoreRecoding;	//录音得分
	private float scoreRecodingQ;	//录音数量得分
	private float scoreRecodingT;	//复听次数得分
	private float scoreAcademic;	//章节考试得分
	private float scoreAcademicCourse;//课程考试得分
	
	public float getScoreProportionQ() {
		return scoreProportionQ;
	}
	public void setScoreProportionQ(float scoreProportionQ) {
		this.scoreProportionQ = scoreProportionQ;
	}
	public float getScoreProportionT() {
		return scoreProportionT;
	}
	public void setScoreProportionT(float scoreProportionT) {
		this.scoreProportionT = scoreProportionT;
	}
	public float getScoreRecodingQ() {
		return scoreRecodingQ;
	}
	public void setScoreRecodingQ(float scoreRecodingQ) {
		this.scoreRecodingQ = scoreRecodingQ;
	}
	public float getScoreRecodingT() {
		return scoreRecodingT;
	}
	public void setScoreRecodingT(float scoreRecodingT) {
		this.scoreRecodingT = scoreRecodingT;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public float getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public float getScoreLogin() {
		return scoreLogin;
	}
	public void setScoreLogin(float scoreLogin) {
		this.scoreLogin = scoreLogin;
	}
	public float getScoreWeek() {
		return scoreWeek;
	}
	public void setScoreWeek(float scoreWeek) {
		this.scoreWeek = scoreWeek;
	}
	public float getScoreClass() {
		return scoreClass;
	}
	public void setScoreClass(float scoreClass) {
		this.scoreClass = scoreClass;
	}
	public float getScoreProportion() {
		return scoreProportion;
	}
	public void setScoreProportion(float scoreProportion) {
		this.scoreProportion = scoreProportion;
	}
	public float getScoreRecoding() {
		return scoreRecoding;
	}
	public void setScoreRecoding(float scoreRecoding) {
		this.scoreRecoding = scoreRecoding;
	}
	public float getScoreAcademic() {
		return scoreAcademic;
	}
	public void setScoreAcademic(float scoreAcademic) {
		this.scoreAcademic = scoreAcademic;
	}
	public float getScoreAcademicCourse() {
		return scoreAcademicCourse;
	}
	public void setScoreAcademicCourse(float scoreAcademicCourse) {
		this.scoreAcademicCourse = scoreAcademicCourse;
	}
	
	
	
	

}
