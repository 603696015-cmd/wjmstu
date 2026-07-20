package com.sopia;

public class ElConstants {
	public static final int TREE_ROOT = 0;
	public static final int TREE_FIANL = -1;
	public static final int SUBOP_YES = 1;
	public static final int SUBOP_NO = 0;
	public static final int USER_OP_LIB = -2;
	// session相关
	/**
	 * session 用户id
	 */  
	public static final String SESSION_USERID = "userId";
	public static final String SESSION_USERNAME = "username";
	public static final String SESSION_REALNAME = "realname";
	public static final String SESSION_ROLE = "roleid";
	public static final String SESSION_ROLENAME = "roleName";
	public static final String SESSION_MYDEPARTMENT = "myDepartment";
	public static final String SESSION_MYSTATION = "myStation";
	public static final String SESSION_MYWORD = "myWord";
	public static final String SESSION_MYMESSAGE = "myMessage";
	public static final String SESSION_MYSCORE = "myScore";
	public static final String SESSION_MYSORT = "mySort";
	public static final String SESSION_STATION = "mystation";
	public static final String SESSION_AGE = "userage";
	public static final String SESSION_MYDEPNAME = "myDepName";
	public static final String SESSION_SHENFENZHENG = "shenfenzheng";
	public static final String SESSION_STUFFCODE="stuffcode";
	/** *课程*** */
	/**
	 * 标准课程
	 */
	public static final int COURSE_TYPE_BZKC = 0;
	public static final int COURSE_TYPE_WBKC = 1;//外部课程
	public static final int COURSE_TYPE_ZHWB = 2;//组合式外部课程
	public static final int COURSE_TYPE_DYSP = 3;//单一视频课程
	public static final int COURSE_TYPE_TBKT = 4;//同步课堂频课程
	public static final int COURSE_TYPE_SCORM = 5;//SCorm课程

	public static final int CPAGE_TYPE_TW = 0;//图文讲义
	public static final int CPAGE_TYPE_CSP = 1;//纯视频
	public static final int CPAGE_TYPE_SPJY = 2;//视频＋讲义
	public static final int CPAGE_TYPE_WBKC = 3;//外部课程
	public static final int CPAGE_TYPE_SPXX = 4;//商务汉语学习系统
	public static final int CPAGE_TYPE_KPXX = 5;//宽频学习
	public static final int CPAGE_TYPE_WBKPXX = 6;//外部宽频学习
	//20140325修改
	public static final int CPAGE_TYPE_CHSPXX=7;//词汇视频学习

	/** *培训班*** */
	public static final int SORT_UP = 1;
	public static final int SORT_DOWN = 0;
	public final static String optSplit = "-=SpEl=-";
	public final static String resSplit = "-=SpRe-";
	public final static String valSplit = "-=SpVl-";
	public final static String ruleSplit = "-=SpRule-";

	public final static String SYSTEM_CONF_DATABASE_TYPE = "system.conf.database.type";
	public final static String SYSTEM_CONF_CTYPE_1 = "system.conf.ctype.1";
	public final static String SYSTEM_CONF_CTYPE_2 = "system.conf.ctype.2";
	public final static String SYSTEM_CONF_CTYPE_3 = "system.conf.ctype.3";
	public final static String SYSTEM_CONF_CTYPE_4 = "system.conf.ctype.4";
	public final static String SYSTEM_CONF_CTYPE_5 = "system.conf.ctype.5";
	public final static String SYSTEM_CONF_CTYPE_6 = "system.conf.ctype.6";
	public final static String SYSTEM_CONF_CTYPE_7 = "system.conf.ctype.7";
	public final static String SYSTEM_CONF_CTYPE_8 = "system.conf.ctype.8";
	public final static String SYSTEM_CONF_REGISTER = "system.conf.register";
	public final static String STUDY_COURSE_CTYPE_Z = "study.course.ctype.z";
	public final static String STUDY_COURSE_CTYPE_B = "study.course.ctype.b";
	public final static String STUDY_COURSE_CTYPE_X = "study.course.ctype.x";
	public final static String STUDY_COURSE_NEED_SH = "study.course.need.sh";
	public final static String STUDY_CLASS_NEED_SH = "study.class.need.sh";
	public final static String OPENMEETINGS_ADMIN_USER = "openmeetings.admin.user";
	public final static String OPENMEETINGS_URL = "openmeetings.url";
	public final static String OPENMEETINGS_ADMIN_PWD = "openmeetings.admin.pwd";
	public final static String STUFF_URL = "stuff_url";
	public final static String STUFF_URL_LOCAL = "stuff_url_local";
	public final static String STUFF_SIZE = "stuff_size";
	public final static String STUFF_OP = "stuff_op";
	public final static String SYSTEM_CONF_LOGIN_ADDIP = "system.conf.login.addip";//设置登入是否记录ip
	public final static String STUFF_ISFTOPIC = "stuff_isftopic";//帖子回复是否需要审核
	public final static String SYSTEM_CONF_REGISTERINFO_ISALL = "system.conf.registerinfo.isall";//注册信息是否都要验证
	public final static String SYSTEM_CONF_USERIMP_ISCHECK = "system.conf.userimp.ischeck";//学员导入是否需要验证
	public final static String SYSTEM_CONF_HTTPS_PORT = "system_conf_https_port";
	public final static String SYSTEM_CONF_HTTP_PORT = "system_conf_http_port";
	public final static String PRODUCT_NEED_SH = "product_need_sh";//产品发布是否需要审核
	public final static String SHEBEI_NEED_SH = "shebei_need_sh";//设备发布是否需要审核
	
	public final static String SHIPIN_NEED_ZH = "shipin_need_zh";//是否启用视频转换功能
	public final static String DUANDIAN_NEED_XC = "duandian_need_xc";//是否启用断点续传功能
	public final static String FILEUPLOAD_NEED_ZH = "fileupload_need_zh";//文档上传是否转换
	public final static String PRODUCT_FABU_CAN_ALTER = "product_fabu_can_alter";//产品发布后是否允许修改
	public final static String BAOXIANPRODUCT_NEED_SH = "product_baoxian_need_sh";//保险产品发布是否需要审核
	public final static String LINE_TRAINING_COURSE_ADD_NEED_SH = "line_training_course_add_need_sh";//添加线下培训是否需要审核
	public final static String SYSTEM_CONF_OFFICE_SIZE = "system.conf.office.size";
	public final static String SYSTEM_CONF_MSG_USERNAME = "system.conf.msg.username";//短信群发的用户名
	public final static String SYSTEM_CONF_MSG_PASSWORD = "system.conf.msg.password";//短信群发的密码
	public final static String SYSTEM_CONF_EMAIL_SMTP = "system.conf.email.smtp";//邮件服务器地址
	public final static String SYSTEM_CONF_EMAIL_UNAMES = "system.conf.email.unames";//邮件群发的用户名（可以多个，&符号隔开）
	public final static String SYSTEM_CONF_EMAIL_PWDS = "system.conf.email.pwds";//邮件群发的密码（可以多个，&符号隔开）
	public final static String SYSTEM_CONF_EMAIL_SENDCOUNT = "system.conf.email.sendcount";//每个账号一次发送的邮件数
	public final static String SYSTEM_CONF_ISEXAM = "system.conf.isexam";//每个账号一次发送的邮件数
	public final static String SYSTEM_CONF_OFFICE_HOME = "system.conf.office.home";//openOffice程序的路径
	public final static String SYSTEM_CONF_PDF2SWF_PATH = "system.conf.pdf2swf.path";//pdf2swf程序中转换执行文件的路径
	public final static String SYSTEM_CONF_YZCODE_OPEN = "system.conf.yzcode.open";//登录是否需要验证验证码
	public final static String SYSTEM_CONF_ALLOWMULTIPLESIGN = "system.conf.allowMultipleSign";//是否禁止多点登陆
	public final static String SYSTEM_CONF_IS_ENQUIRY_IN_TABLE = "system.conf.is_enquiry_in_table";//是否在全表内资料查询
	public final static String SYSTEM_CONF_INDEX_CLASSID = "index_classid"; //首页通过率设置
	public final static String SYSTEM__MAC_NEED = "system.mac_need";//是否限定ＭＡＣ
	public final static String SYSTEM_INTELLIGENTTUTORINGPOINTS = "sysconf.intelligentTutoringPoints";//智能辅导分达标分数
	public final static String SYSTEM_SIMILARITY = "sysconf.similarity";//语音识别相似度
	public final static String SYSTEM_WJM = "sysconf.wjm";//是否是外经贸项目
	public final static String SEARCH_NEED = "search_need";//是否全文检索
	
	public final static String COURSEMAKE_NEED_SH = "coursemake.need.sh";// 课程制作审核
	public final static String ZHENSHU_NEED_SH = "zhenshu.need.sh";// 证书
//	public final static String QUIZ_NEED_SH = "quiz.need.sh";// 批卷
	public final static String KNOWLEDGE_NEED_SH = "knowledge.need.sh";// 知识发布
	public final static String FORUM_NEED_SH = "forum.need.sh";// 帖子发布
	public final static String REGISTER_NEED_SH = "register.need.sh";// 注册审核
//	public final static String BK_TIMEOUT = "bk.timeout";
	public final static String SHOUYE_IMG = "shouye.img";
	public final static String SHOUYE_URL = "shouye.url";

	public final static String SYSTEM_CONF_LOGIN_MAX = "system_conf_login_max";//最大登陆数 
	public final static String SYSTEM_CONF_LOGIN_FAILURE_MAX = "system_conf_login_failure_max";//最大登陆失败数  0 是不启用
	
	
	public final static String MODEL_WORKING = "model_working";	//运行模式
	public final static String PUBLISH_OPTION = "publish_option";	//发布选项	
	public final static String LIST_PAGE_NUMBER = "list_page_number";	//生成列表分页数
	public final static String CATALOGUE_PLACE = "catalogue_place";	//生成的总目录	
	public final static String ZDYHTML = "zdy_html";
	public final static String SYSTEM_CONF_IS_RECEIVE_BY_JUDGE = "system_cong_is_receive_by_judge";//收件人权限判断
	public final static String SYSTEM_RELEASE_QUESTION_NEED_SH = "system_release_question_need_sh";
	public final static String SYSTEM_ANSWER_QUESTION_NEED_SH = "system_answer_question_need_sh";
	public final static String TITLE_RULE = "title_rule";		//栏目页规则
	
	public final static String PUBLICBEGIN = "public_begin";
	public final static String PUBLICEND = "public_end";
	public final static String PUBLICEND2 = "public_end2";
	public final static String NEWSHOUYE ="sysconf.newShouye";
	
	public final static String SYSTEM_KTXZ = "sysconf.ktxzSwf";//看图选择帮助swf
	public final static String SYSTEM_KDHXZ = "sysconf.kdhxzSwf";//看动画选择帮助swf
	public final static String SYSTEM_TYXT = "sysconf.tyxtSwf";//听音选图帮助swf
	public final static String SYSTEM_JSBY = "sysconf.jsbySwf";//角色扮演帮助swf
	public final static String SYSTEM_TZ = "sysconf.tzSwf";//拖拽帮助swf
	public final static String SYSTEM_PX = "sysconf.pxSwf";//排序帮助swf
	
	public final static String SYSTEM_KTXZEDITORHTML = "sysconf.ktxzEditorHtml";//看图选择编辑器HTML
	public final static String SYSTEM_KDHXZEDITORHTML = "sysconf.kdhxzEditorHtml";//看动画选择编辑器HTML
	public final static String SYSTEM_TYXTEDITORHTML = "sysconf.tyxtEditorHtml";//听音选图编辑器HTML
	public final static String SYSTEM_JSBYEDITORHTML = "sysconf.jsbyEditorHtml";//角色扮演编辑器HTML
	public final static String SYSTEM_TZEDITORHTML = "sysconf.tzEditorHtml";//拖拽编辑器HTML
	public final static String SYSTEM_PXEDITORHTML = "sysconf.pxEditorHtml";//排序编辑器HTML
	
	///--------------------积分系统 -学员得分明细  2012-5-31
	//------学习加分项 名字定义规则（LEARNING(学习)_KS（考试）_CJ（成绩））
	//考试成绩加分   	加分规则（考试平均分*0）
	public final static String LEARNING_KS_CJ = "learning.ks.cj";
	//学时加分		加分规则（超过数 * 3）
	public final static String LEARNING_XS_XS = "learning.xs.xs";
	//练习加分		加分规则（已做练习的课程数*0）
	public final static String LEARNING_LX_LX = "learning.lx.lx";
	//模考加分		加分规则（已做模考的课程数*0）
	public final static String LEARNING_MK_MK = "learning.mk.mk";
	//学分加分		加分规则（超过数 * 5）
	public final static String LEARNING_XF_XF = "learning.xf.xf";
	//笔记得分		加分规则（已做笔记的课程数*0）
	public final static String LEARNING_BJ_BJ = "learning.bj.bj";
	//上传得分		加分规则（已审核文章数 *  5）
	public final static String LEARNING_SC_SC = "learning.sc.sc";
	//被推荐得分		加分规则（）
	public final static String LEARNING_BTJ_BTJ = "learning.btj.btj";
	//被下载得分		加分规则（下载人次*0）
	public final static String LEARNING_BXZ_BXZ = "learning.bxz.bxz";
	//下载得分		加分规则（下载文章数*0）
	public final static String LEARNING_XZ_XZ = "learning.xz.xz";
	//发帖得分		加分规则（通过数*  0.5）
	public final static String LEARNING_FT_FT = "learning.ft.ft";
	//发言得分		加分规则（发言次数*  0.1）
	public final static String LEARNING_FY_FY = "learning.fy.fy";
	//精华帖得分 		加分规则（精华帖数量*  5）
	public final static String LEARNING_JH_JH = "learning.jh.jh";
	//登陆加分    	加分规则（登陆*0.1）
	public final static String LEARNING_DL_DL = "learning.dl.dl";
	
	// 一篇帖子被加为精华，奖励（1）分
	public final static String SCORE_FORUM_JH = "score.forum.jh";
	// 一篇知识文章被设为推荐，奖励（1）分
	public final static String SCORE_KNOWLEDGE_TJ = "score.knowledge.tj";
	// 每申请学习一门课程，奖励（5分）
	public final static String SCORE_COURSE_APPLY = "score.course.apply";
	// 每做一次练习，奖励（2）分
	public final static String SCORE_PRAC_DO = "score.prac.do";
	// 每做一次模拟考试，奖励（5分）
	public final static String SCORE_SIMP_DO = "score.simp.do";
	// 每发一条站内短信，奖励（1）分
	public final static String SCORE_MESS_SEND = "score.mess.send";
	// 每做一张调查问卷，奖励（2）分
	public final static String SCORE_SURVEY_DO = "score.survey.do";
	// 每参加一次投票，奖励（1）分
	public final static String SCORE_POLL_DO = "score.poll.do";
	// 每做一张客观测评试卷，奖励（2）分
	public final static String SCORE_ZTROOM_DO = "score.ztroom.do";
	// 每参加一次民主评议，奖励（10）分
	public final static String SCORE_KTROOM_DO = "score.ktroom.do";
	// 记一次课程小结，奖励（5）分
	public final static String SCORE_NOTE_DO = "score.note.do";

	// 每间隔（60）分钟后，登陆一次将励（5）点
	public final static String DIAN_LOGIN_DO = "dian.login.do";
	// 发帖一篇，将励（10）点
	public final static String DIAN_FORUM_DO = "dian.forum.do";
	// 回帖一篇，奖励（2）点
	public final static String DIAN_TOPIC_DO = "dian.topic.do";
	// 学习次数每增加一次，奖励（5）点
	public final static String DIAN_STUDY_DO = "dian.study.do";
	// 学习时长每增加一小时，奖励（50）点
	public final static String DIAN_STUDY_CP_DO = "dian.study.cp.do";
	// 一篇帖子被删除，扣（5）点
	public final static String JIAN_FORUM_DO = "jian.forum.do";
	// 一篇知识文章被删除，扣（5）点
	public final static String JIAN_KNOWLEDGE_DO = "jian.knowledge.do";

	// 长时间不登陆：每隔（48）小时不登陆，扣（2）点
	public final static String JIAN_LOGIN_DO = "jian.login.do";
	// 每被暂停一次考试，扣（20）点\
	public final static String JIAN_EP_ZHANTING = "jian.ep.zhanting";
	// 每被强制交卷一次，扣（50）点
	public final static String JIAN_EP_QIANGZHI = "jian.ep.qiangzhi";
	public final static String SCORE_2_DIAN = "score.2.dian";
	public final static String XFSCORE_2_SCORE = "xfscore.2.score";
	public final static String XFCOURSE_QUIZPASSED = "course.quizpassed";
	public final static String XFCOURSE_STUDIED = "course.studied";

	//新闻类型
	/**
	 * 新闻动态  3
	 */
	public static final int NSTYLE_XWDT=3;//新闻动态
	/**
	 * 经验交流  6
	 */
	public static final int NSTYLE_JYJL=6;//经验交流
	/**
	 * 教学公告  8
	 */
	public static final int NSTYLE_JXGG=8;//教学公告
	/**
	 * 帮助中心  7
	 */
	public static final int NSTYLE_BZZX=7;//帮助中心
	/**
	 * 下载中心  11
	 */
	public static final int NSTYLE_XXZX=11;//下载中心
	
	//新闻类别
	/**
	 * 新闻动态  10
	 */
	public static final int NTYPE_XWDT=10;//新闻动态
	/**
	 * 经验交流  11
	 */
	public static final int NTYPE_JYJL=11;//经验交流
	/**
	 * 教学公告  12
	 */
	public static final int NTYPE_JXGG=12;//教学公告
	/**
	 * 帮助中心  13
	 */
	public static final int NTYPE_BZZX=13;//帮助中心
	/**
	 * 下载中心  14
	 */
	public static final int NTYPE_XXZX=14;//下载中心
	
	//新闻热度
	/**
	 * 普通  0
	 */
	public static final int HOT_PT = 0;
	/**
	 * 推荐  1
	 */
	public static final int HOT_TJ = 1;
	/**
	 * 热门  2
	 */
	public static final int HOT_RM = 2;
	/**
	 * 重点  3
	 */
	public static final int HOT_ZD = 3;
	/**
	 * 头条  4
	 */
	public static final int HOT_TT = 4;
	
	//首页显示模块
	public static final int INDEX_MODEL_ALL=-1;//首页所有模块
	public static final int INDEX_MODEL_NEWS=1;//首页新闻模块
	public static final int INDEX_MODEL_FORUM=2;//首页帖子模块
	public static final int INDEX_MODEL_KNOWLEDGE=3;//首页资料模块
	public static final int INDEX_MODEL_COURSE=4;//首页课程模块
	public static final int INDEX_MODEL_EXAMROOM=5;//首页考场模块
	public static final int INDEX_MODEL_ELCLASS=6;//首页培训班模块
	public static final int INDEX_MODEL_DEP=7;//首页培训班模块
	public static final int INDEX_MODEL_USERINFO=8;//首页个人模块
	public static final int INDEX_MODEL_ST=9;//首页培训班模块
	
	public static final String ELEARNING_COOKIE_USERNAME = "elearning.cookie.username";
	
	public static final String CLASS_ELNODESQL ="elnodesql";



	public final static String SYSTEM_VIEW_TITLE = "system.view.title";
	public final static String SYSTEM_VIEW_BOTTON_ONE = "system.view.botton.one";
	public final static String SYSTEM_VIEW_BOTTON_TWO = "system.view.botton.two";
	
	/**
	 * 首页配置
	 */
	public final static String SYSTEM_INDEXCONFIG_SHOW_TONGZHIGONGGAO = "system.indexConfig.show_tongzhigonggao";//通知公告
	public final static String SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU = "system.indexConfig.show_daibanshiwu";//待办事务
	public final static String SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA = "system.indexConfig.show_gongzuojihua";//工作计划
	public final static String SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI = "system.indexConfig.show_gongzuorizhi";//工作日志
	public final static String SYSTEM_INDEXCONFIG_SHOW_RICHENGANPAI = "system.indexConfig.show_richenganpai";//日程安排
	public final static String SYSTEM_INDEXCONFIG_SHOW_GERENKAOQIN = "system.indexConfig.show_gerenkaoqin";//个人考勤
	public final static String SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN = "system.indexConfig.show_gerenweishen";//个人未审
	public final static String SYSTEM_INDEXCONFIG_SHOW_GERENDAISHEN = "system.indexConfig.show_gerendaishen";//个人待审
	public final static String SYSTEM_INDEXCONFIG_SHOW_MYALLCOURSES = "system.indexConfig.show_myallcourses";//我的全部课程
	public final static String SYSTEM_INDEXCONFIG_SHOW_MYEXAMS = "system.indexConfig.show_myexams";//我的非购买考试
	public final static String SYSTEM_INDEXCONFIG_SHOW_MYBUYROOMS = "system.indexConfig.show_mybuyrooms";//购买的考场
	public final static String SYSTEM_INDEXCONFIG_SHOW_MYTRAININGCOURSES = "system.indexConfig.show_mytrainingcourses";//我的培训班
	
	public final static String SYSTEM_INDEXCONFIG_TONGZHIGONGGAO_LENGTH = "system.indexConfig.tongzhigonggao_length";//通知公告显示条数
	public final static String SYSTEM_INDEXCONFIG_DAIBANSHIWU_LENGTH = "system.indexConfig.daibanshiwu_length";//待办事务显示条数
	public final static String SYSTEM_INDEXCONFIG_GONGZUOJIHUA_LENGTH = "system.indexConfig.gongzuojihua_length";//工作计划显示条数
	public final static String SYSTEM_INDEXCONFIG_GONGZUORIZHI_LENGTH = "system.indexConfig.gongzuorizhi_length";//工作日志显示条数
	public final static String SYSTEM_INDEXCONFIG_RICHENGANPAI_LENGTH = "system.indexConfig.richenganpai_length";//日程安排显示条数
	public final static String SYSTEM_INDEXCONFIG_GERENWEISHEN_LENGTH = "system.indexConfig.gerenweishen_length";//个人未审显示条数
	public final static String SYSTEM_INDEXCONFIG_GERENDAISHEN_LENGTH = "system.indexConfig.gerendaishen_length";//个人待审显示条数
	public final static String SYSTEM_INDEXCONFIG_MYALLCOURSES_LENGTH = "system.indexConfig.myallcourses_length";//我的课程显示条数
	public final static String SYSTEM_INDEXCONFIG_MYEXAMS_LENGTH = "system.indexConfig.myexams_length";//我的非购买考试显示条数
	public final static String SYSTEM_INDEXCONFIG_MYBUYROOMS_LENGTH = "system.indexConfig.mybyrooms_length";//购买考场显示条数
	public final static String SYSTEM_INDEXCONFIG_MYTRAININGCOURSES = "system.indexConfig.mytrainingcourses_length";//我的培训班显示条数
	
	//JTM接口URL
	public static final String SYSTEM_JTM_OPEN_JTM = "system_jtm_open_jtm";//是否开启JTM
	public static final String SYSTEM_JTM_MY_EVALUATION_URL = "system_jtm_my_evaluation_url";//我的测评接口
	public static final String SYSTEM_JTM_PEOPLEPOST_URL = "system_jtm_peoplePost_url";//人岗匹配接口
	public static final String SYSTEM_JTM_MY_REPORT_URL = "system_jtm_my_report_url";//查看个人报告
	public static final String SYSTEM_JTM_REPORT_EVAL_URL = "system_jtm_report_eval_url";//个人量身评价
	public static final String SYSTEM_JTM_COURSES_AYSCHRONIZATION_URL = "system_jtm_courses_aynchronization_url";//课程同步
	public static final String SYSTEM_JTM_MY_CEPINGCOURSES_URL = "system_jtm_my_cepingCourses_url";//我的测评课程
	
	//新首页布局
	public static final String SYSTEM_NEWINDEXCONFIG_NEWSHOUYE = "system_newindexconfig_newshouye";
	
	//自定义模块
	public static final String SYSTEM_ZDY_STATIC_HTML_ALL = "system_zdy_static_html_all";//总概
	public static final String SYSTEM_ZDY_STATIC_HTML_ADDCONTACTTAGSINIT = "system_zdy_static_html_addContactTagsInit";//添加页面
	public static final String SYSTEM_ZDY_STATIC_HTML_UPDATECONTACTTAGSINIT = "system_zdy_static_html_updateContactTagsInit";//修改页面
	public static final String SYSTEM_ZDY_STATIC_HTML_VIEWCONTACTTAGS = "system_zdy_static_html_viewContactTags";//查看页面
	
	
	public static final int SYSTEM_CLASSIFICATION_YES = 1;//已定级
	public static final int SYSTEM_CLASSIFICATION_NO = 0;//未定级
	
	
	//智能辅导分设置
	public final static String SYSTEM_SCORELOGIN = "system.scoreLogin";
	public final static String SYSTEM_SCORELOGINPER = "system.scoreLogin.per";
	public final static String SYSTEM_SCORELOGINNOT3DAYPER = "system.scoreLoginNot3day.per";
	public final static String SYSTEM_SCOREWEEK = "system.scoreWeek";
	public final static String SYSTEM_SCOREWEEKPER = "system.scoreWeek.per";
	public final static String SYSTEM_SCORECLASS = "system.scoreClass";
	public final static String SYSTEM_SCORECLASSPER = "system.scoreClass.per";
	public final static String SYSTEM_SCOREPROPORTIONPROCESS = "system.scoreProportionProcess";
	public final static String SYSTEM_SCOREPROPORTIONPROCESSPER = "system.scoreProportionProcess.per";
	public final static String SYSTEM_SCOREPROPORTIONTIME = "system.scoreProportionTime";
	public final static String SYSTEM_SCOREPROPORTIONTIMEPER = "system.scoreProportionTime.per";
	public final static String SYSTEM_SCORERECODINGPROCESS = "system.scoreRecodingProcess";
	public final static String SYSTEM_SCORERECODINGPROCESSPER = "system.scoreRecodingProcess.per";
	public final static String SYSTEM_SCORERECODINGTIME = "system.scoreRecodingTime";
	public final static String SYSTEM_SCORERECODINGTIMEPER = "system.scoreRecodingTime.per";
	public final static String SYSTEM_SCOREEXAMPAGE = "system.scoreExamPage";
	public final static String SYSTEM_SCOREEXAMPAGEPER = "system.scoreExamPage.per";
	public final static String SYSTEM_SCOREEXAMCOURSE = "system.scoreExamCourse";
	public final static String SYSTEM_SCOREEXAMCOURSE1TO3PER = "system.scoreExamCourse1TO3.per";
	public final static String SYSTEM_SCOREEXAMCOURSE4TO6PER = "system.scoreExamCourse4TO6.per";
	//sd1223修改
	public final static String SD_ELCLASS = "sd.elclass";//山东培训班
	//sd1230
	public final static String SYSTEM_SD = "sysconf.sd";//是否是山东项目
	public final static String SYSTEM_CONF_LOGIN_MAX_SD = "system_conf_login_max_sd";//山东最大登陆数 
	
	//外经贸词汇导入例句分隔符 wjm0220
	public final static String vocsplit="-=WKMVOC=-";


}

