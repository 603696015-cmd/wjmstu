package com.sopia.common;

/**
 * 点数积分实体类
 * @author Administrator
 *
 */
public class ScoreSet {
//	一篇帖子被加为精华，奖励（1）分
	private int score_forum_jh ;
//	一篇知识文章被设为推荐，奖励（1）分
	private int score_knowledge_tj;
//	每申请学习一门课程，奖励（5分）
	private int score_course_apply;
//	每做一次练习，奖励（2）分
	private int score_prac_do;
//	每做一次模拟考试，奖励（5分）
	private int score_simp_do;
//	每发一条站内短信，奖励（1）分
	private int score_mess_send;
//	每做一张调查问卷，奖励（2）分
	private int score_survey_do;
//	每参加一次投票，奖励（1）分
	private int score_poll_do;
//	每做一张客观测评试卷，奖励（2）分
	private int score_ztroom_do;
//	每参加一次民主评议，奖励（10）分
	private int score_ktroom_do;
//	记一次课程小结，奖励（5）分
	private int score_note_do;
	
	

//	每间隔（60）分钟后，登陆一次将励（5）点
	private int dian_login_do;
//	发帖一篇，将励（10）点
	private int dian_forum_do;
//	回帖一篇，奖励（2）点
	private int dian_topic_do;
//	学习次数每增加一次，奖励（5）点
	private int dian_study_do;
//	学习时长每增加一小时，奖励（50）点
	private int dian_study_cp_do;

//	一篇帖子被删除，扣（5）点
	private int jian_forum_do;

//	一篇知识文章被删除，扣（5）点
	private int jian_knowledge_do;

//	长时间不登陆：每隔（48）小时不登陆，扣（2）点
	private int jian_login_do;

//	每被暂停一次考试，扣（20）点\
	private int jian_ep_zhanting;
//	每被强制交卷一次，扣（50）点
	private int jian_ep_qiangzhi;
	
	private int score_2_dian;
	private int xfscore_2_score;
	
	private boolean course_studied;
	
	private boolean course_quizpassed;
//	一篇帖子被加为精华，奖励（1）分
	private int score_forum_jh_m ;
//	一篇知识文章被设为推荐，奖励（1）分
	private int score_knowledge_tj_m;
//	每申请学习一门课程，奖励（5分）
	private int score_course_apply_m;
//	每做一次练习，奖励（2）分
	private int score_prac_do_m;
//	每做一次模拟考试，奖励（5分）
	private int score_simp_do_m;
//	每发一条站内短信，奖励（1）分
	private int score_mess_send_m;
//	每做一张调查问卷，奖励（2）分
	private int score_survey_do_m;
//	每参加一次投票，奖励（1）分
	private int score_poll_do_m;
//	每做一张客观测评试卷，奖励（2）分
	private int score_ztroom_do_m;
//	每参加一次民主评议，奖励（10）分
	private int score_ktroom_do_m;
//	记一次课程小结，奖励（5）分
	private int score_note_do_m;
	
	

//	每间隔（60）分钟后，登陆一次将励（5）点
	private int dian_login_do_m;
//	发帖一篇，将励（10）点
	private int dian_forum_do_m;
//	回帖一篇，奖励（2）点
	private int dian_topic_do_m;
//	学习次数每增加一次，奖励（5）点
	private int dian_study_do_m;
//	学习时长每增加一小时，奖励（50）点
	private int dian_study_cp_do_m;

//	一篇帖子被删除，扣（5）点
	private int jian_forum_do_m;

//	一篇知识文章被删除，扣（5）点
	private int jian_knowledge_do_m;

//	长时间不登陆：每隔（48）小时不登陆，扣（2）点
	private int jian_login_do_m;

//	每被暂停一次考试，扣（20）点\
	private int jian_ep_zhanting_m;
//	每被强制交卷一次，扣（50）点
	private int jian_ep_qiangzhi_m;
	
	private int score_2_dian_m;
	private int xfscore_2_score_m;
	
	private boolean course_studied_m;
	
	private boolean course_quizpassed_m;
	public boolean getCourse_studied() {
		return course_studied;
	}
	public void setCourse_studied(boolean course_studied) {
		this.course_studied = course_studied;
	}
	public boolean getCourse_quizpassed() {
		return course_quizpassed;
	}
	public void setCourse_quizpassed(boolean course_quizpassed) {
		this.course_quizpassed = course_quizpassed;
	}
	public int getScore_2_dian() {
		return score_2_dian;
	}
	public void setScore_2_dian(int score_2_dian) {
		this.score_2_dian = score_2_dian;
	}
	public int getXfscore_2_score() {
		return xfscore_2_score;
	}
	public void setXfscore_2_score(int xfscore_2_score) {
		this.xfscore_2_score = xfscore_2_score;
	}
	public int getScore_forum_jh() {
		return score_forum_jh;
	}
	public void setScore_forum_jh(int score_forum_jh) {
		this.score_forum_jh = score_forum_jh;
	}
	public int getScore_knowledge_tj() {
		return score_knowledge_tj;
	}
	public void setScore_knowledge_tj(int score_knowledge_tj) {
		this.score_knowledge_tj = score_knowledge_tj;
	}
	public int getScore_course_apply() {
		return score_course_apply;
	}
	public void setScore_course_apply(int score_course_apply) {
		this.score_course_apply = score_course_apply;
	}
	public int getScore_prac_do() {
		return score_prac_do;
	}
	public void setScore_prac_do(int score_prac_do) {
		this.score_prac_do = score_prac_do;
	}
	public int getScore_simp_do() {
		return score_simp_do;
	}
	public void setScore_simp_do(int score_simp_do) {
		this.score_simp_do = score_simp_do;
	}
	public int getScore_mess_send() {
		return score_mess_send;
	}
	public void setScore_mess_send(int score_mess_send) {
		this.score_mess_send = score_mess_send;
	}
	public int getScore_survey_do() {
		return score_survey_do;
	}
	public void setScore_survey_do(int score_survey_do) {
		this.score_survey_do = score_survey_do;
	}
	public int getScore_poll_do() {
		return score_poll_do;
	}
	public void setScore_poll_do(int score_poll_do) {
		this.score_poll_do = score_poll_do;
	}
	public int getScore_ztroom_do() {
		return score_ztroom_do;
	}
	public void setScore_ztroom_do(int score_ztroom_do) {
		this.score_ztroom_do = score_ztroom_do;
	}
	public int getScore_ktroom_do() {
		return score_ktroom_do;
	}
	public void setScore_ktroom_do(int score_ktroom_do) {
		this.score_ktroom_do = score_ktroom_do;
	}
	public int getScore_note_do() {
		return score_note_do;
	}
	public void setScore_note_do(int score_note_do) {
		this.score_note_do = score_note_do;
	}
	public int getDian_login_do() {
		return dian_login_do;
	}
	public void setDian_login_do(int dian_login_do) {
		this.dian_login_do = dian_login_do;
	}
	public int getDian_forum_do() {
		return dian_forum_do;
	}
	public void setDian_forum_do(int dian_forum_do) {
		this.dian_forum_do = dian_forum_do;
	}
	public int getDian_topic_do() {
		return dian_topic_do;
	}
	public void setDian_topic_do(int dian_topic_do) {
		this.dian_topic_do = dian_topic_do;
	}
	public int getDian_study_do() {
		return dian_study_do;
	}
	public void setDian_study_do(int dian_study_do) {
		this.dian_study_do = dian_study_do;
	}
	public int getDian_study_cp_do() {
		return dian_study_cp_do;
	}
	public void setDian_study_cp_do(int dian_study_cp_do) {
		this.dian_study_cp_do = dian_study_cp_do;
	}
	public int getJian_forum_do() {
		return jian_forum_do;
	}
	public void setJian_forum_do(int jian_forum_do) {
		this.jian_forum_do = jian_forum_do;
	}
	public int getJian_knowledge_do() {
		return jian_knowledge_do;
	}
	public void setJian_knowledge_do(int jian_knowledge_do) {
		this.jian_knowledge_do = jian_knowledge_do;
	}
	public int getJian_login_do() {
		return jian_login_do;
	}
	public void setJian_login_do(int jian_login_do) {
		this.jian_login_do = jian_login_do;
	}
	public int getJian_ep_zhanting() {
		return jian_ep_zhanting;
	}
	public void setJian_ep_zhanting(int jian_ep_zhanting) {
		this.jian_ep_zhanting = jian_ep_zhanting;
	}
	public int getJian_ep_qiangzhi() {
		return jian_ep_qiangzhi;
	}
	public void setJian_ep_qiangzhi(int jian_ep_qiangzhi) {
		this.jian_ep_qiangzhi = jian_ep_qiangzhi;
	}
	public int getScore_forum_jh_m() {
		return score_forum_jh_m;
	}
	public void setScore_forum_jh_m(int score_forum_jh_m) {
		this.score_forum_jh_m = score_forum_jh_m;
	}
	public int getScore_knowledge_tj_m() {
		return score_knowledge_tj_m;
	}
	public void setScore_knowledge_tj_m(int score_knowledge_tj_m) {
		this.score_knowledge_tj_m = score_knowledge_tj_m;
	}
	public int getScore_course_apply_m() {
		return score_course_apply_m;
	}
	public void setScore_course_apply_m(int score_course_apply_m) {
		this.score_course_apply_m = score_course_apply_m;
	}
	public int getScore_prac_do_m() {
		return score_prac_do_m;
	}
	public void setScore_prac_do_m(int score_prac_do_m) {
		this.score_prac_do_m = score_prac_do_m;
	}
	public int getScore_simp_do_m() {
		return score_simp_do_m;
	}
	public void setScore_simp_do_m(int score_simp_do_m) {
		this.score_simp_do_m = score_simp_do_m;
	}
	public int getScore_mess_send_m() {
		return score_mess_send_m;
	}
	public void setScore_mess_send_m(int score_mess_send_m) {
		this.score_mess_send_m = score_mess_send_m;
	}
	public int getScore_survey_do_m() {
		return score_survey_do_m;
	}
	public void setScore_survey_do_m(int score_survey_do_m) {
		this.score_survey_do_m = score_survey_do_m;
	}
	public int getScore_poll_do_m() {
		return score_poll_do_m;
	}
	public void setScore_poll_do_m(int score_poll_do_m) {
		this.score_poll_do_m = score_poll_do_m;
	}
	public int getScore_ztroom_do_m() {
		return score_ztroom_do_m;
	}
	public void setScore_ztroom_do_m(int score_ztroom_do_m) {
		this.score_ztroom_do_m = score_ztroom_do_m;
	}
	public int getScore_ktroom_do_m() {
		return score_ktroom_do_m;
	}
	public void setScore_ktroom_do_m(int score_ktroom_do_m) {
		this.score_ktroom_do_m = score_ktroom_do_m;
	}
	public int getScore_note_do_m() {
		return score_note_do_m;
	}
	public void setScore_note_do_m(int score_note_do_m) {
		this.score_note_do_m = score_note_do_m;
	}
	public int getDian_login_do_m() {
		return dian_login_do_m;
	}
	public void setDian_login_do_m(int dian_login_do_m) {
		this.dian_login_do_m = dian_login_do_m;
	}
	public int getDian_forum_do_m() {
		return dian_forum_do_m;
	}
	public void setDian_forum_do_m(int dian_forum_do_m) {
		this.dian_forum_do_m = dian_forum_do_m;
	}
	public int getDian_topic_do_m() {
		return dian_topic_do_m;
	}
	public void setDian_topic_do_m(int dian_topic_do_m) {
		this.dian_topic_do_m = dian_topic_do_m;
	}
	public int getDian_study_do_m() {
		return dian_study_do_m;
	}
	public void setDian_study_do_m(int dian_study_do_m) {
		this.dian_study_do_m = dian_study_do_m;
	}
	public int getDian_study_cp_do_m() {
		return dian_study_cp_do_m;
	}
	public void setDian_study_cp_do_m(int dian_study_cp_do_m) {
		this.dian_study_cp_do_m = dian_study_cp_do_m;
	}
	public int getJian_forum_do_m() {
		return jian_forum_do_m;
	}
	public void setJian_forum_do_m(int jian_forum_do_m) {
		this.jian_forum_do_m = jian_forum_do_m;
	}
	public int getJian_knowledge_do_m() {
		return jian_knowledge_do_m;
	}
	public void setJian_knowledge_do_m(int jian_knowledge_do_m) {
		this.jian_knowledge_do_m = jian_knowledge_do_m;
	}
	public int getJian_login_do_m() {
		return jian_login_do_m;
	}
	public void setJian_login_do_m(int jian_login_do_m) {
		this.jian_login_do_m = jian_login_do_m;
	}
	public int getJian_ep_zhanting_m() {
		return jian_ep_zhanting_m;
	}
	public void setJian_ep_zhanting_m(int jian_ep_zhanting_m) {
		this.jian_ep_zhanting_m = jian_ep_zhanting_m;
	}
	public int getJian_ep_qiangzhi_m() {
		return jian_ep_qiangzhi_m;
	}
	public void setJian_ep_qiangzhi_m(int jian_ep_qiangzhi_m) {
		this.jian_ep_qiangzhi_m = jian_ep_qiangzhi_m;
	}
	public int getScore_2_dian_m() {
		return score_2_dian_m;
	}
	public void setScore_2_dian_m(int score_2_dian_m) {
		this.score_2_dian_m = score_2_dian_m;
	}
	public int getXfscore_2_score_m() {
		return xfscore_2_score_m;
	}
	public void setXfscore_2_score_m(int xfscore_2_score_m) {
		this.xfscore_2_score_m = xfscore_2_score_m;
	}
	public boolean isCourse_studied_m() {
		return course_studied_m;
	}
	public void setCourse_studied_m(boolean course_studied_m) {
		this.course_studied_m = course_studied_m;
	}
	public boolean isCourse_quizpassed_m() {
		return course_quizpassed_m;
	}
	public void setCourse_quizpassed_m(boolean course_quizpassed_m) {
		this.course_quizpassed_m = course_quizpassed_m;
	}
	
}
