package com.sopia.studyman.entities; 

public class Integra {
	//---考试成绩加分 
	private int kc_courseXF;//已获学分的课程数 
	private float kc_scoresAVG;//考试平均分
	private float score_kc_scoresAVG;//得分
	//---学时加分 
	private float xs_period;//已完成学时数 
	private float xs_exceed;//超过数
	private float score_xs_exceed;//得分
	//---练习加分 
	private int lx_course;//已做练习的课程数 
	private float score_lx_course;//得分 
	//---模考加分
	private int mk_Model;//已做模考的课程数   
	private float score_mk_Model;//得分 
	//---学分加分
	private int xf_credits; //已获学分数
	private int xf_beyond;//超出数
	private float score_xf_beyond;//得分 
	//---笔记得分
	private int bj_course;//已做笔记的课程数
	private float score_bj_course;//得分
	//---上传得分 
	private int sc_release;//已发布文章数 
	private int sc_audit;//已审核文章数
	private float score_sc_audit;//得分
	//---被推荐得分 
	private int btj_article;//被推荐的文章数 
	private float score_btj_article;//得分
	//---被下载得分 
	private int bxz_audit;//已审核文章数 
	private int bxz_people;//下载人次
	private float score_bxz_people;//得分
	//---下载得分 
	private int xz_audit;//下载文章数 
	private float score_xz_audit;//得分
	//---发帖得分 
	private int ft_post;//发帖数 
	private int ft_pass;//通过数
	private float score_ft_pass;//得分
	//---发言得分 
	private int fy_speech;//发言次数
	private float score_fy_speech;//得分
	//---精华帖得分 
	private int jh_jht;//精华帖数量
	private float score_jh_jht;//得分
	//---登陆加分 
	private int dl_login;//登陆次数 
	private float score_dl_login;//得分 
	
	private float variables ;
	
	
	
	public float getVariables() {
		return variables;
	}
	public void setVariables(float variables) {
		this.variables = variables;
	}
	public float getScore_kc_scoresAVG() {
		return score_kc_scoresAVG;
	}
	public void setScore_kc_scoresAVG(float score_kc_scoresAVG) {
		this.score_kc_scoresAVG = score_kc_scoresAVG;
	}
	public float getScore_xs_exceed() {
		return score_xs_exceed;
	}
	public void setScore_xs_exceed(float score_xs_exceed) {
		this.score_xs_exceed = score_xs_exceed;
	}
	public float getScore_lx_course() {
		return score_lx_course;
	}
	public void setScore_lx_course(float score_lx_course) {
		this.score_lx_course = score_lx_course;
	}
	public float getScore_mk_Model() {
		return score_mk_Model;
	}
	public void setScore_mk_Model(float score_mk_Model) {
		this.score_mk_Model = score_mk_Model;
	}
	public float getScore_xf_beyond() {
		return score_xf_beyond;
	}
	public void setScore_xf_beyond(float score_xf_beyond) {
		this.score_xf_beyond = score_xf_beyond;
	}
	public float getScore_bj_course() {
		return score_bj_course;
	}
	public void setScore_bj_course(float score_bj_course) {
		this.score_bj_course = score_bj_course;
	}
	public float getScore_sc_audit() {
		return score_sc_audit;
	}
	public void setScore_sc_audit(float score_sc_audit) {
		this.score_sc_audit = score_sc_audit;
	}
	public float getScore_btj_article() {
		return score_btj_article;
	}
	public void setScore_btj_article(float score_btj_article) {
		this.score_btj_article = score_btj_article;
	}
	public float getScore_bxz_people() {
		return score_bxz_people;
	}
	public void setScore_bxz_people(float score_bxz_people) {
		this.score_bxz_people = score_bxz_people;
	}
	public float getScore_xz_audit() {
		return score_xz_audit;
	}
	public void setScore_xz_audit(float score_xz_audit) {
		this.score_xz_audit = score_xz_audit;
	}
	public float getScore_ft_pass() {
		return score_ft_pass;
	}
	public void setScore_ft_pass(float score_ft_pass) {
		this.score_ft_pass = score_ft_pass;
	}
	public float getScore_fy_speech() {
		return score_fy_speech;
	}
	public void setScore_fy_speech(float score_fy_speech) {
		this.score_fy_speech = score_fy_speech;
	}
	public float getScore_jh_jht() {
		return score_jh_jht;
	}
	public void setScore_jh_jht(float score_jh_jht) {
		this.score_jh_jht = score_jh_jht;
	}
	public float getScore_dl_login() {
		return score_dl_login;
	}
	public void setScore_dl_login(float score_dl_login) {
		this.score_dl_login = score_dl_login;
	}
	public int getKc_courseXF() {
		return kc_courseXF;
	}
	public void setKc_courseXF(int kc_courseXF) {
		this.kc_courseXF = kc_courseXF;
	}
	public float getKc_scoresAVG() {
		return kc_scoresAVG;
	}
	public void setKc_scoresAVG(float kc_scoresAVG) {
		this.kc_scoresAVG = kc_scoresAVG;
	}
	public float getXs_period() {
		return xs_period;
	}
	public void setXs_period(float xs_period) {
		this.xs_period = xs_period;
	}
	public float getXs_exceed() {
		return xs_exceed;
	}
	public void setXs_exceed(float xs_exceed) {
		this.xs_exceed = xs_exceed;
	}
	public int getLx_course() {
		return lx_course;
	}
	public void setLx_course(int lx_course) {
		this.lx_course = lx_course;
	}
	public int getMk_Model() {
		return mk_Model;
	}
	public void setMk_Model(int mk_Model) {
		this.mk_Model = mk_Model;
	}
	public int getXf_credits() {
		return xf_credits;
	}
	public void setXf_credits(int xf_credits) {
		this.xf_credits = xf_credits;
	}
	public int getXf_beyond() {
		return xf_beyond;
	}
	public void setXf_beyond(int xf_beyond) {
		this.xf_beyond = xf_beyond;
	}
	public int getBj_course() {
		return bj_course;
	}
	public void setBj_course(int bj_course) {
		this.bj_course = bj_course;
	}
	public int getSc_release() {
		return sc_release;
	}
	public void setSc_release(int sc_release) {
		this.sc_release = sc_release;
	}
	public int getSc_audit() {
		return sc_audit;
	}
	public void setSc_audit(int sc_audit) {
		this.sc_audit = sc_audit;
	}
	public int getBtj_article() {
		return btj_article;
	}
	public void setBtj_article(int btj_article) {
		this.btj_article = btj_article;
	}
	public int getBxz_audit() {
		return bxz_audit;
	}
	public void setBxz_audit(int bxz_audit) {
		this.bxz_audit = bxz_audit;
	}
	public int getBxz_people() {
		return bxz_people;
	}
	public void setBxz_people(int bxz_people) {
		this.bxz_people = bxz_people;
	}
	public int getXz_audit() {
		return xz_audit;
	}
	public void setXz_audit(int xz_audit) {
		this.xz_audit = xz_audit;
	}
	public int getFt_post() {
		return ft_post;
	}
	public void setFt_post(int ft_post) {
		this.ft_post = ft_post;
	}
	public int getFt_pass() {
		return ft_pass;
	}
	public void setFt_pass(int ft_pass) {
		this.ft_pass = ft_pass;
	}
	public int getFy_speech() {
		return fy_speech;
	}
	public void setFy_speech(int fy_speech) {
		this.fy_speech = fy_speech;
	}
	public int getJh_jht() {
		return jh_jht;
	}
	public void setJh_jht(int jh_jht) {
		this.jh_jht = jh_jht;
	}
	public int getDl_login() {
		return dl_login;
	}
	public void setDl_login(int dl_login) {
		this.dl_login = dl_login;
	}
}
