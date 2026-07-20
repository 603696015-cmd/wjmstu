package com.sopia.duman.entities;



import com.sopia.classman.entities.ElClass;
import com.sopia.common.getFloat;

public class UnitRanking {    
	private int ranking;		//排名	
	private ElClass elclass;	//培训班id	 		
	private Department unit;	//单位		
	private double passing;		//通过率
	private float basedScore; 	//基础综合得分
	private float DegreeScore;	//学历层次得分
	private float TitleScore;	//职称级别得分
	private float TotalScore;	//总分
	private float AddCent;		//加分
	private float FinalScore;	//最终得分
	
	//-----详情页面需用属性
	//学历详情
	private int xl_dz_;//大专以下
	private int xl_dz;//大专
	private int xl_bk;//本科
	private int xl_ss;//硕士
	private int xl_bs;//博士
	
	//职称级别
	private int zc_w;//无
	private int zc_cj;//初级
	private int zc_zj;//中级
	private int zc_gj;//高级  正高级


	public int getXl_dz_() {
		return xl_dz_;
	}
	public float getScore_Xl_dz_() {
		return xl_dz_*0.5f;
	}
	public void setXl_dz_(int xl_dz_) {
		this.xl_dz_ = xl_dz_;
	}

	public int getXl_dz() {
		return xl_dz;
	}
	public float getScore_Xl_dz() {
		return xl_dz*1.0f;
	}

	public void setXl_dz(int xl_dz) {
		this.xl_dz = xl_dz;
	}

	public int getXl_bk() {
		return xl_bk;
	}
	public float getScore_Xl_bk() {
		return xl_bk*2.0f;
	}
	public void setXl_bk(int xl_bk) {
		this.xl_bk = xl_bk;
	}

	public int getXl_ss() {
		return xl_ss;
	}
	public float getScore_Xl_ss() {
		return xl_ss*3.0f;
	}
	public void setXl_ss(int xl_ss) {
		this.xl_ss = xl_ss;
	}

	public int getXl_bs() {
		return xl_bs;
	}
	public float getScore_Xl_bs() {
		return xl_bs*4.0f;
	}
	public void setXl_bs(int xl_bs) {
		this.xl_bs = xl_bs;
	}
	
	public float getScore_Xl_TOTAL() {
		return getScore_Xl_bs()+getScore_Xl_ss()+getScore_Xl_bk()+getScore_Xl_dz()+getScore_Xl_dz_();
	}	
	public int getXl_TOTAL() {
		return getXl_bs()+getXl_ss()+getXl_bk()+getXl_dz()+getXl_dz_();
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public ElClass getElclass() {
		return elclass;
	}

	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}

	public Department getUnit() {
		return unit;
	} 
	public void setUnit(Department unit) {
		this.unit = unit;
	}

	public double getPassing() {
		return passing;
	}

	public void setPassing(double passing) {
		this.passing = passing;
	}

	public float getBasedScore() {
		return getFloat.GetFloat(basedScore);
	}

	public void setBasedScore(float basedScore) {
		this.basedScore = basedScore;
	}

	public float getDegreeScore() { 
		return getFloat.GetFloat(DegreeScore);
	}

	public void setDegreeScore(float degreeScore) {
		DegreeScore = degreeScore;
	}

	public float getTitleScore() {
		return getFloat.GetFloat(TitleScore);
	}

	public void setTitleScore(float titleScore) {
		TitleScore = titleScore;
	}

	public float getTotalScore() {
		return getFloat.GetFloat(TotalScore);
	}

	public void setTotalScore(float totalScore) {
		TotalScore = totalScore;
	}

	public float getAddCent() {
		return AddCent;
	}

	public void setAddCent(float addCent) {
		AddCent = addCent;
	}

	public float getFinalScore() {
		return getFloat.GetFloat(FinalScore);
	}

	public void setFinalScore(float finalScore) {
		FinalScore = finalScore;
	}

	public UnitRanking() {
		
	}

	public UnitRanking(Department unit) {
		this.unit = unit;
	}
	public UnitRanking(ElClass elclass,Department unit) {
		this.elclass = elclass;
		this.unit = unit;
	}

	public int getZc_cj() {
		return zc_cj;
	}
	public float getScore_Zc_cj() {
		return zc_cj*1.0f;
	}
	public void setZc_cj(int zc_cj) {
		this.zc_cj = zc_cj;
	}

	public int getZc_zj() {
		return zc_zj;
	}
	public float getScore_Zc_zj() {
		return zc_zj*2.0f;
	}
	public void setZc_zj(int zc_zj) {
		this.zc_zj = zc_zj;
	}

	public int getZc_gj() {
		return zc_gj;
	}
	public float getScore_Zc_gj() {
		return zc_gj*4.0f;
	}
	public void setZc_gj(int zc_gj) {
		this.zc_gj = zc_gj;
	}
	public int getZc_w() {
		return zc_w;
	}
	public float getScore_Zc_w() {
		return zc_w*0.0f;
	}
	public void setZc_w(int zc_w) {
		this.zc_w = zc_w;
	}

	public float getScore_Zc_TOTAL() {
		return getScore_Zc_cj()+getScore_Zc_zj()+getScore_Zc_gj();
	}
	public int getZc_TOTAL() {
		return getZc_cj()+getZc_zj()+getZc_gj()+getZc_w();
	}

}
