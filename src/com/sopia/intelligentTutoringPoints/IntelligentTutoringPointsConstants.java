package com.sopia.intelligentTutoringPoints;
/**
 * 智能辅导分常量
 * @author TMK
 *
 */
//已废弃
public class IntelligentTutoringPointsConstants {
	//智能辅导总分
	public final static double TOTAL_SCORE = 100.00;

	//学习频次30分
	public final static double LEARNING_FREQUENCY = 30.00;
	
	//学习习惯40分
	public final static double STUDY_HABITS = 40.00;
	
	//学习成绩30分
	public final static double ACADEMIC = 30.00;
	
	public final static String SESSION_LOGINID = "loginId";
	
	
	public final static String PEIXUNBATCHDAO = "peixunBatchDao";
	//登录dao
	public final static String INTELLIGENT_LOGIN = "intelligentLoginDao";
	//周学习时间dao、等级学习总时间dao
	public final static String INTELLIGENT_WEEK = "intelligentWeekDao";
	//学习习惯dao
	public final static String INTELLIGENT_PROPORTION = "intelligentProportionDao";
	//学习成绩dao
	public final static String INTELLIGENT_ACADEMIC = "intelligentAcademicDao";
	//智能辅导分dao
	public final static String INTELLIGENT_TUTORING_POINTS = "intelligentTutoringPointsDao";
	
	
	
	//智能辅导分各个得分或者减分点存储的表
	//单次登录得分表
	public final static String LOGINTABLE = "intelligent_login";
	//用户登录总表
	public final static String LOGINTABLEALL = "intelligent_login_t";
	//单次学习时间表
	public final static String LOGINTABLEWEEK = "intelligent_week";
	//周学习时间表
	public final static String LOGINTABLEWEEKALL = "intelligent_week_t";
	//周学习时间总表
	public final static String LOGINTABLEWEEKTOTAL = "intelligent_week_t_t";
	//等级总学习时间表
	public final static String LOGINTABLECLASSTOTAL = "intelligent_class_t";
	//复听比例表
	public final static String INTELLIGENTPROPORTION = "intelligent_proportion";
	//复听总表
	public final static String INTELLIGENTPROPORTIONTOTAL = "intelligent_proportion_t";
	//录音比例表
	public final static String INTELLIGENTRECODING = "intelligent_recoding";
	//录音总表
	public final static String INTELLIGENTRECODINGTOTAL = "intelligent_recoding_t";
	//单次章节考试记录表
	public final static String INTELLIGENTACADEMIC = "intelligent_academic";
	//章节考试记录统计表
	public final static String INTELLIGENTACADEMICALL = "intelligent_academic_t";
	//章节考试得分表
	public final static String INTELLIGENTACADEMICTOTAL = "intelligent_academic_t_t";
	//单次课程考试记录表
	public final static String INTELLIGENTACADEMICCOURSE = "intelligent_academic_course";
	//课程考试记录统计表
	public final static String INTELLIGENTACADEMICCOURSEALL = "intelligent_academic_course_t";
	//课程考试得分表
	public final static String INTELLIGENTACADEMICCOURSETOTAL = "intelligent_academic_cou_t_t";
	
	public final static int FROM1ATO3B = 0;
	public final static int FROM4ATO6B = 1;
}
