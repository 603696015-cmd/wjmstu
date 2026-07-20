package com.sopia;

import java.util.ArrayList;
import java.util.List;

import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.IndexSystemConfig;
import com.sopia.common.IndexSystemConfigOp;
import com.sopia.common.IntelligentSystemConf;
import com.sopia.common.IntelligentSystemConfOp;
import com.sopia.common.JTMSystemConf;
import com.sopia.common.JTMSystemConfOp;
import com.sopia.common.NewSystemConf;
import com.sopia.common.NewSystemConfOp;
import com.sopia.common.ScoreSet;
import com.sopia.common.SystemConf;
import com.sopia.common.SystemConfOp;
import com.sopia.common.ZdyStaticHtmlSystemConf;
import com.sopia.common.ZdyStaticHtmlSystemConfOp;
import com.sopia.common.logger.ElLog;
import com.sopia.common.logger.ElLogger;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.entities.CourseServer;
import com.sopia.courseman.entities.CourseType;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsType;
import common.Logger;

public class SystemAction extends BaseAction {
	private ScoreSet scoreset;
	private int registerstatus;
	private CourseType ctypeTree;
	private CourseTypeDao ctypeDao;
	private List<CourseType> ctypes;
	private SystemConf sysconf;
	private IndexSystemConfig indexSysConf;
	private IntelligentSystemConf intelligentSysConf;
	private JTMSystemConf jtmSysConf;
	private ZdyStaticHtmlSystemConf zdyStaticHtmlSysConf;
	private NewSystemConf newSysConf;
	private NewsDao newsDao;
	private NewsType ntypeTree;
	private NewsType ntype;
	
	private int message;
	private int number;//发布最新添加
	private int start;
	private int end;
	private ClassDao classDao;
	private List<ElClass> elClasss;
	private int nid;
	private int all;
	private int publish_option;
	
	
	public IntelligentSystemConf getIntelligentSysConf() {
		return intelligentSysConf;
	}

	public void setIntelligentSysConf(IntelligentSystemConf intelligentSysConf) {
		this.intelligentSysConf = intelligentSysConf;
	}

	public int getPublish_option() {
		return publish_option;
	}

	public void setPublish_option(int publish_option) {
		this.publish_option = publish_option;
	}

	public int getAll() {
		return all;
	}

	public void setAll(int all) {
		this.all = all;
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public List<ElClass> getElClasss() {
		return elClasss;
	}

	public void setElClasss(List<ElClass> elClasss) {
		this.elClasss = elClasss;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public NewSystemConf getNewSysConf() {
		return newSysConf;
	}

	public void setNewSysConf(NewSystemConf newSysConf) {
		this.newSysConf = newSysConf;
	}

	public ZdyStaticHtmlSystemConf getZdyStaticHtmlSysConf() {
		return zdyStaticHtmlSysConf;
	}

	public void setZdyStaticHtmlSysConf(ZdyStaticHtmlSystemConf zdyStaticHtmlSysConf) {
		this.zdyStaticHtmlSysConf = zdyStaticHtmlSysConf;
	}

	public JTMSystemConf getJtmSysConf() {
		return jtmSysConf;
	}

	public void setJtmSysConf(JTMSystemConf jtmSysConf) {
		this.jtmSysConf = jtmSysConf;
	}

	public NewsType getNtype() {
		return ntype;
	}

	public void setNtype(NewsType ntype) {
		this.ntype = ntype;
	}

	public NewsType getNtypeTree() {
		return ntypeTree;
	}

	public void setNtypeTree(NewsType ntypeTree) {
		this.ntypeTree = ntypeTree;
	}

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public IndexSystemConfig getIndexSysConf() {
		return indexSysConf;
	}

	public void setIndexSysConf(IndexSystemConfig indexSysConf) {
		this.indexSysConf = indexSysConf;
	}

	public SystemConf getSysconf() {
		return sysconf;
	}

	public void setSysconf(SystemConf sysconf) {
		this.sysconf = sysconf;
	}

	public String system_alterInit() throws ElException {
		int type = sysconf == null ? 1 : sysconf.getType();
		sysconf = departmentDao.getSystemConfByType(type);
		return "system_alter";
	}

	public String system_alter() throws ElException {
		departmentDao.alterSystemconf(sysconf);
		return "system_alter_success";
	}

	public String dot_setInit() throws ElException {
		scoreset = new ScoreSet();
		scoreset.setDian_forum_do(SystemConfOp
				.getIntValue(ElConstants.DIAN_FORUM_DO));
		scoreset.setDian_login_do(SystemConfOp
				.getIntValue(ElConstants.DIAN_LOGIN_DO));
		scoreset.setDian_study_cp_do(SystemConfOp
				.getIntValue(ElConstants.DIAN_STUDY_CP_DO));
		scoreset.setDian_study_do(SystemConfOp
				.getIntValue(ElConstants.DIAN_STUDY_DO));
		scoreset.setDian_topic_do(SystemConfOp
				.getIntValue(ElConstants.DIAN_TOPIC_DO));
		return "dot_set";
	}

	public String dot_set() throws ElException {
		SystemConfOp.setProperty(ElConstants.DIAN_FORUM_DO, scoreset
				.getDian_forum_do());
		SystemConfOp.setProperty(ElConstants.DIAN_LOGIN_DO, scoreset
				.getDian_login_do());
		SystemConfOp.setProperty(ElConstants.DIAN_STUDY_CP_DO, scoreset
				.getDian_study_cp_do());
		SystemConfOp.setProperty(ElConstants.DIAN_STUDY_DO, scoreset
				.getDian_study_do());
		SystemConfOp.setProperty(ElConstants.DIAN_TOPIC_DO, scoreset
				.getDian_topic_do());
		return "dot_set";
	}

	public String score_setInit() throws ElException {
		scoreset = new ScoreSet();
		scoreset.setScore_course_apply(SystemConfOp
				.getIntValue(ElConstants.SCORE_COURSE_APPLY));
		scoreset.setScore_forum_jh(SystemConfOp
				.getIntValue(ElConstants.SCORE_FORUM_JH));
		scoreset.setScore_knowledge_tj(SystemConfOp
				.getIntValue(ElConstants.SCORE_KNOWLEDGE_TJ));
		scoreset.setScore_ktroom_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_KTROOM_DO));
		scoreset.setScore_mess_send(SystemConfOp
				.getIntValue(ElConstants.SCORE_MESS_SEND));
		scoreset.setScore_note_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_NOTE_DO));
		scoreset.setScore_poll_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_POLL_DO));
		scoreset.setScore_prac_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_PRAC_DO));
		scoreset.setScore_simp_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_SIMP_DO));
		scoreset.setScore_survey_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_SURVEY_DO));
		scoreset.setScore_ztroom_do(SystemConfOp
				.getIntValue(ElConstants.SCORE_ZTROOM_DO));
		return "score_set";
	}

	public String score_set() throws ElException {
		SystemConfOp.setProperty(ElConstants.SCORE_COURSE_APPLY, scoreset
				.getScore_course_apply());
		SystemConfOp.setProperty(ElConstants.SCORE_FORUM_JH, scoreset
				.getScore_forum_jh());
		SystemConfOp.setProperty(ElConstants.SCORE_KNOWLEDGE_TJ, scoreset
				.getScore_knowledge_tj());
		SystemConfOp.setProperty(ElConstants.SCORE_KTROOM_DO, scoreset
				.getScore_ktroom_do());
		SystemConfOp.setProperty(ElConstants.SCORE_MESS_SEND, scoreset
				.getScore_mess_send());
		SystemConfOp.setProperty(ElConstants.SCORE_NOTE_DO, scoreset
				.getScore_note_do());
		SystemConfOp.setProperty(ElConstants.SCORE_POLL_DO, scoreset
				.getScore_poll_do());
		SystemConfOp.setProperty(ElConstants.SCORE_PRAC_DO, scoreset
				.getScore_prac_do());
		SystemConfOp.setProperty(ElConstants.SCORE_SIMP_DO, scoreset
				.getScore_simp_do());
		SystemConfOp.setProperty(ElConstants.SCORE_SURVEY_DO, scoreset
				.getScore_survey_do());
		SystemConfOp.setProperty(ElConstants.SCORE_ZTROOM_DO, scoreset
				.getScore_ztroom_do());
		return "score_set";

	}

	public String jian_setInit() throws ElException {
		scoreset = new ScoreSet();
		scoreset.setJian_ep_qiangzhi(-SystemConfOp
				.getIntValue(ElConstants.JIAN_EP_QIANGZHI));
		scoreset.setJian_ep_zhanting(-SystemConfOp
				.getIntValue(ElConstants.JIAN_EP_ZHANTING));
		scoreset.setJian_forum_do(-SystemConfOp
				.getIntValue(ElConstants.JIAN_FORUM_DO));
		scoreset.setJian_knowledge_do(-SystemConfOp
				.getIntValue(ElConstants.JIAN_KNOWLEDGE_DO));
		scoreset.setJian_login_do(-SystemConfOp
				.getIntValue(ElConstants.JIAN_LOGIN_DO));
		return "jian_set";
	}

	public String jian_set() throws ElException {
		SystemConfOp.setProperty(ElConstants.JIAN_EP_QIANGZHI, -scoreset
				.getJian_ep_qiangzhi());
		SystemConfOp.setProperty(ElConstants.JIAN_EP_ZHANTING, -scoreset
				.getJian_ep_zhanting());
		SystemConfOp.setProperty(ElConstants.JIAN_FORUM_DO, -scoreset
				.getJian_forum_do());
		SystemConfOp.setProperty(ElConstants.JIAN_KNOWLEDGE_DO, -scoreset
				.getJian_knowledge_do());
		SystemConfOp.setProperty(ElConstants.JIAN_LOGIN_DO, -scoreset
				.getJian_login_do());
		return "jian_set";
	}

	public String scorechange_setInit() throws ElException {
		scoreset = new ScoreSet();
		scoreset.setScore_2_dian(SystemConfOp
				.getIntValue(ElConstants.SCORE_2_DIAN));
		scoreset.setXfscore_2_score(SystemConfOp
				.getIntValue(ElConstants.XFSCORE_2_SCORE));
		return "scorechange_set";
	}

	public String scorechange_set() throws ElException {
		SystemConfOp.setProperty(ElConstants.SCORE_2_DIAN, scoreset
				.getScore_2_dian());
		SystemConfOp.setProperty(ElConstants.XFSCORE_2_SCORE, scoreset
				.getXfscore_2_score());

		return "scorechange_set";
	}

	public String xfscore_setInit() throws ElException {
		scoreset = new ScoreSet();
		scoreset.setCourse_quizpassed(SystemConfOp
				.getBooleanValue(ElConstants.XFCOURSE_QUIZPASSED));
		scoreset.setCourse_studied(SystemConfOp
				.getBooleanValue(ElConstants.XFCOURSE_STUDIED));
		return "xfscore_set";
	}

	public String xfscore_set() throws ElException {
		SystemConfOp.setProperty(ElConstants.XFCOURSE_QUIZPASSED, scoreset
				.getCourse_quizpassed());
		SystemConfOp.setProperty(ElConstants.XFCOURSE_STUDIED, scoreset
				.getCourse_studied());
		return "xfscore_set";
	}

	public String registersetInit() throws ElException {
		registerstatus = SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
		sysconf = sysconf == null ? new SystemConf() : sysconf;
		sysconf.setRegister_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.REGISTER_NEED_SH));
		//获取登入是否记录ip
		sysconf.setLogin_addip(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP));
		return "registerset";
	}

	public String registerset() throws ElException {
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_REGISTER,
				registerstatus + "");

		registerstatus = SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
		SystemConfOp.setProperty(ElConstants.REGISTER_NEED_SH, sysconf
				.getRegister_need_sh());
		//设置登入是否记录ip
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_LOGIN_ADDIP, sysconf
				.getLogin_addip());
		setElmessage("设置成功！");
		return "registerset";
	}

	public String coursetype_setInit() throws ElException {
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		ctypes = new ArrayList<CourseType>();
		ctypes.add(new CourseType(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_CTYPE_1)));
		ctypes.add(new CourseType(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_CTYPE_2)));
		ctypes.add(new CourseType(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_CTYPE_3)));
		ctypes.add(new CourseType(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_CTYPE_4)));
		ctypes.add(new CourseType(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_CTYPE_5)));
		ctypes.add(new CourseType(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_CTYPE_6)));
		ctypes.add(new CourseType(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_CTYPE_7)));
		ctypes.add(new CourseType(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_CTYPE_8)));
		return "coursetype_set";
	}

	public String coursetype_set() throws ElException {
		// ctypeDao.ctsSet(ctypes);
		if (null != ctypes)
			for (int i = 0; i < ctypes.size(); i++) {
				if (ctypes.get(i) != null)
					SystemConfOp.setProperty("system.conf.ctype." + (i + 1),
							ctypes.get(i).getId() + "");
			}
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		setElmessage("首页课程类别设置成功！");
		return "coursetype_set";
	}
	
	//智能辅导分设置
	public String intelligentsetInit() throws ElException{
		intelligentSysConf = new IntelligentSystemConf();
		intelligentSysConf.setScoreLogin(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORELOGIN));
		intelligentSysConf.setScoreLoginPer(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORELOGINPER));
		intelligentSysConf.setScoreLoginNot3dayPer(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORELOGINNOT3DAYPER));
		intelligentSysConf.setScoreWeek(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREWEEK));
		intelligentSysConf.setScoreWeekPer(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREWEEKPER));
		intelligentSysConf.setScoreClass(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORECLASS));
		intelligentSysConf.setScoreClassPer(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORECLASSPER));
		intelligentSysConf.setScoreProportionProcess(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONPROCESS));
		intelligentSysConf.setScoreProportionProcessPer(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONPROCESSPER));
		intelligentSysConf.setScoreProportionTime(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONTIME));
		intelligentSysConf.setScoreProportionTimePer(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONTIMEPER));
		intelligentSysConf.setScoreRecodingProcess(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGPROCESS));
		intelligentSysConf.setScoreRecodingProcessPer(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGPROCESSPER));
		intelligentSysConf.setScoreRecodingTime(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGTIME));
		intelligentSysConf.setScoreRecodingTimePer(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGTIMEPER));
		intelligentSysConf.setScoreExamPage(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMPAGE));
		intelligentSysConf.setScoreExamPagePer(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMPAGEPER));
		intelligentSysConf.setScoreExamCourse(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMCOURSE));
		intelligentSysConf.setScoreExamCourse1TO3Per(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMCOURSE1TO3PER));
		intelligentSysConf.setScoreExamCourse4TO6Per(IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMCOURSE4TO6PER));
		return "intelligentsetInit";
	}
	public String intelligentset() throws ElException{
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCORELOGIN, intelligentSysConf.getScoreLogin());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCORELOGINPER, intelligentSysConf.getScoreLoginPer());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCORELOGINNOT3DAYPER, intelligentSysConf.getScoreLoginNot3dayPer());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREWEEK, intelligentSysConf.getScoreWeek());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREWEEKPER, intelligentSysConf.getScoreWeekPer());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCORECLASS, intelligentSysConf.getScoreClass());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCORECLASSPER, intelligentSysConf.getScoreClassPer());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREPROPORTIONPROCESS, intelligentSysConf.getScoreProportionProcess());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREPROPORTIONPROCESSPER, intelligentSysConf.getScoreProportionProcessPer());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREPROPORTIONTIME, intelligentSysConf.getScoreProportionTime());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREPROPORTIONTIMEPER, intelligentSysConf.getScoreProportionTimePer());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCORERECODINGPROCESS, intelligentSysConf.getScoreRecodingProcess());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCORERECODINGPROCESSPER, intelligentSysConf.getScoreRecodingProcessPer());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCORERECODINGTIME, intelligentSysConf.getScoreRecodingTime());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCORERECODINGTIMEPER, intelligentSysConf.getScoreRecodingTimePer());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREEXAMPAGE, intelligentSysConf.getScoreExamPage());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREEXAMPAGEPER, intelligentSysConf.getScoreExamPagePer());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREEXAMCOURSE, intelligentSysConf.getScoreExamCourse());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREEXAMCOURSE1TO3PER, intelligentSysConf.getScoreExamCourse1TO3Per());
		IntelligentSystemConfOp.setProperty(ElConstants.SYSTEM_SCOREEXAMCOURSE4TO6PER, intelligentSysConf.getScoreExamCourse4TO6Per());
		setElmessage("设置成功！");
		try {
			NewSystemConfOp.load();
		} catch (Exception e) {
			setElmessage("智能辅导分设置失败");
			return "error";
		}
		return "intelligentset";
	}
	
	//新首页布局
	public String newOthersetInit() throws ElException{
		newSysConf = new NewSystemConf();
		newSysConf.setNewShouye(NewSystemConfOp.getIntValue(ElConstants.SYSTEM_NEWINDEXCONFIG_NEWSHOUYE));
		return "newOthersetInit";
	}
	public String newOtherset() throws ElException{
		NewSystemConfOp.setProperty(ElConstants.SYSTEM_NEWINDEXCONFIG_NEWSHOUYE, newSysConf.getNewShouye());
		setElmessage("设置成功！");
		try {
			NewSystemConfOp.load();
		} catch (Exception e) {
			setElmessage("新首页布局设置失败");
			return "error";
		}
		return "newOtherset";
	}

	public String othersetInit() throws ElException {
		sysconf = new SystemConf();
		// ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		sysconf.setCtype_b(SystemConfOp
				.getIntValue(ElConstants.STUDY_COURSE_CTYPE_B));
		sysconf.setCtype_x(SystemConfOp
				.getIntValue(ElConstants.STUDY_COURSE_CTYPE_X));
		sysconf.setCtype_z(SystemConfOp
				.getIntValue(ElConstants.STUDY_COURSE_CTYPE_Z));

		sysconf.setStudy_course_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.STUDY_COURSE_NEED_SH));
		sysconf.setStudy_class_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.STUDY_CLASS_NEED_SH));
		sysconf.setCoursemake_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.COURSEMAKE_NEED_SH));
		sysconf.setForum_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.FORUM_NEED_SH));
		sysconf.setKnowledge_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.KNOWLEDGE_NEED_SH));
		// sysconf.setQuiz_need_sh(SystemConfOp
		// .getBooleanValue(ElConstants.QUIZ_NEED_SH));
		// sysconf.setRegister_need_sh(SystemConfOp
		// .getBooleanValue(ElConstants.REGISTER_NEED_SH));
		sysconf.setZhenshu_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.ZHENSHU_NEED_SH));
		// sysconf.setBk_timeout(SystemConfOp.getIntValue(ElConstants.BK_TIMEOUT));
		sysconf.setShouye_img(SystemConfOp.getValue(ElConstants.SHOUYE_IMG));
		sysconf.setShouye_url(SystemConfOp.getValue(ElConstants.SHOUYE_URL));
		sysconf.setOpenmeetings_url(SystemConfOp
				.getValue(ElConstants.OPENMEETINGS_URL));
		sysconf.setOpenmeetings_admin_user(SystemConfOp
				.getValue(ElConstants.OPENMEETINGS_ADMIN_USER));
		sysconf.setOpenmeetings_admin_pwd(SystemConfOp
				.getValue(ElConstants.OPENMEETINGS_ADMIN_PWD));
		sysconf.setStuff_url(SystemConfOp.getValue(ElConstants.STUFF_URL));
		sysconf.setStuff_url_local(SystemConfOp.getValue(ElConstants.STUFF_URL_LOCAL));
		sysconf.setStuff_size( SystemConfOp.getIntValue(ElConstants.STUFF_SIZE));
		sysconf.setStuff_isftopic(SystemConfOp.getBooleanValue(ElConstants.STUFF_ISFTOPIC));
		registerstatus = SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
		sysconf = sysconf == null ? new SystemConf() : sysconf;
		sysconf.setRegister_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.REGISTER_NEED_SH));
		//获取登入是否记录ip
		sysconf.setLogin_addip(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP));
		//注册信息是否都要验证
		sysconf.setRegister_isall(SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL));
		//学员导入是否需要验证
		sysconf.setUserimp_ischeck(SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK));
		//https所用端口
		sysconf.setHttps_port(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_HTTPS_PORT));
		//http所用端口
		sysconf.setHttp_port(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_HTTP_PORT));
		//最大登陆数
		sysconf.setLogin_max(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_LOGIN_MAX));
		//最大登陆失败次数  0表示不开启次功能
		sysconf.setLogin_failure_max(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX));
		//发布产品是否需要审核
		sysconf.setProduct_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.PRODUCT_NEED_SH));
		//发布保险产品是否需要审核
		sysconf.setBaoxianProduct_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.BAOXIANPRODUCT_NEED_SH));
		//发布设备是否需要审核
		sysconf.setShebei_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.SHEBEI_NEED_SH));
		//是否启用视频转换功能
		sysconf.setShipin_need_zh(SystemConfOp
				.getBooleanValue(ElConstants.SHIPIN_NEED_ZH));
		//是否启用断点续传功能
		sysconf.setDuandian_need_xc(SystemConfOp
				.getBooleanValue(ElConstants.DUANDIAN_NEED_XC));
		//文档上传是否转换
		sysconf.setFileupload_need_zh(SystemConfOp
				.getBooleanValue(ElConstants.FILEUPLOAD_NEED_ZH));
		//产品发布后是否允许修改
		sysconf.setProduct_fabu_can_alter(SystemConfOp
				.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER));
		//添加线下培训是否需要审核
		sysconf.setLine_training_course_add_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.LINE_TRAINING_COURSE_ADD_NEED_SH));
		//是否全文检索
		sysconf.setSearch_need(SystemConfOp.getBooleanValue(ElConstants.SEARCH_NEED));
		//office题上传大小设置
		sysconf.setOfficeSize(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_OFFICE_SIZE));
		sysconf.setExam(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_ISEXAM));
		sysconf.setOffice_home(SystemConfOp.getValue(ElConstants.SYSTEM_CONF_OFFICE_HOME));
		sysconf.setPdf2swf_path(SystemConfOp.getValue(ElConstants.SYSTEM_CONF_PDF2SWF_PATH));
		sysconf.setYzcode_open(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_YZCODE_OPEN));////登录是否需要验证验证码
		sysconf.setAllowMultipleSign(SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_ALLOWMULTIPLESIGN));////是否禁止多点登陆
		sysconf.setIs_enquiry_in_table(SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_IS_ENQUIRY_IN_TABLE));////是否在全表内资料查询
		sysconf.setZdy_html(SystemConfOp
				.getValue(ElConstants.ZDYHTML));
		sysconf.setNewShouye(SystemConfOp.getIntValue(ElConstants.NEWSHOUYE));//新首页布局
		//System.out.println(sysconf.getNewShouye());
		//外经贸
		sysconf.setPublic_begin(SystemConfOp.getValue(ElConstants.PUBLICBEGIN));
		sysconf.setPublic_end(SystemConfOp.getValue(ElConstants.PUBLICEND));
		sysconf.setPublic_end2(SystemConfOp.getValue(ElConstants.PUBLICEND2));
		
		sysconf.setKtxzSwf(SystemConfOp.getValue(ElConstants.SYSTEM_KTXZ));
		sysconf.setKdhxzSwf(SystemConfOp.getValue(ElConstants.SYSTEM_KDHXZ));
		sysconf.setTyxtSwf(SystemConfOp.getValue(ElConstants.SYSTEM_TYXT));
		sysconf.setJsbySwf(SystemConfOp.getValue(ElConstants.SYSTEM_JSBY));
		sysconf.setTzSwf(SystemConfOp.getValue(ElConstants.SYSTEM_TZ));
		sysconf.setPxSwf(SystemConfOp.getValue(ElConstants.SYSTEM_PX));
		
		sysconf.setKtxzEditorHtml(SystemConfOp.getValue(ElConstants.SYSTEM_KTXZEDITORHTML));
		sysconf.setKdhxzEditorHtml(SystemConfOp.getValue(ElConstants.SYSTEM_KDHXZEDITORHTML));
		sysconf.setTyxtEditorHtml(SystemConfOp.getValue(ElConstants.SYSTEM_TYXTEDITORHTML));
		sysconf.setJsbyEditorHtml(SystemConfOp.getValue(ElConstants.SYSTEM_JSBYEDITORHTML));
		sysconf.setTzEditorHtml(SystemConfOp.getValue(ElConstants.SYSTEM_TZEDITORHTML));
		sysconf.setPxEditorHtml(SystemConfOp.getValue(ElConstants.SYSTEM_PXEDITORHTML));
		
		//设置首页通过率设置 
		if(!SystemConfOp.getValue(ElConstants.SYSTEM_CONF_INDEX_CLASSID).equals("无记录")
				&&SystemConfOp.getValue(ElConstants.SYSTEM_CONF_INDEX_CLASSID)!= null){ 
			sysconf.setIndex_classid(Integer.valueOf(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_INDEX_CLASSID)).intValue()); 
			sysconf.setIndex_class(classDao.getClassById(sysconf.getIndex_classid()));
			elClasss = new ArrayList<ElClass>();
			elClasss.add(sysconf.getIndex_class());
		}
		sysconf.setIs_receive_by_judge(SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_IS_RECEIVE_BY_JUDGE));//收件人权限判断
		sysconf.setRelease_question_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_RELEASE_QUESTION_NEED_SH));//发布问题是否需要审核
		sysconf.setAnswer_question_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_ANSWER_QUESTION_NEED_SH));//回答问题是否需要审核
		sysconf.setMac_need(SystemConfOp.getBooleanValue(ElConstants.SYSTEM__MAC_NEED));//登录是否限定MAC
		sysconf.setIntelligentTutoringPoints(SystemConfOp.getIntValue(ElConstants.SYSTEM_INTELLIGENTTUTORINGPOINTS));//智能辅导分达标分数
		sysconf.setSimilarity(SystemConfOp.getIntValue(ElConstants.SYSTEM_SIMILARITY));//语音识别相似度
		sysconf.setWjm(SystemConfOp.getIntValue(ElConstants.SYSTEM_WJM));//是否表示外经贸
		//sd1223修改
		sysconf.setSd_elclass(SystemConfOp.getValue(ElConstants.SD_ELCLASS));//山东培训班
		//sd1230
		sysconf.setSd(SystemConfOp.getIntValue(ElConstants.SYSTEM_SD));//是否表示外经贸
		//最大登陆数
		sysconf.setLogin_max_sd(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_MAX_SD));
		return "otherset";
	}

	public String otherset() throws ElException {
		SystemConfOp.setProperty(ElConstants.STUDY_COURSE_CTYPE_B, sysconf
				.getCtype_b());
		SystemConfOp.setProperty(ElConstants.STUDY_COURSE_CTYPE_Z, sysconf
				.getCtype_z());
		SystemConfOp.setProperty(ElConstants.STUDY_COURSE_CTYPE_X, sysconf
				.getCtype_x());
		SystemConfOp.setProperty(ElConstants.STUDY_COURSE_NEED_SH, sysconf
				.getStudy_course_need_sh());
		SystemConfOp.setProperty(ElConstants.STUDY_CLASS_NEED_SH, sysconf
				.getStudy_class_need_sh());
		SystemConfOp.setProperty(ElConstants.COURSEMAKE_NEED_SH, sysconf
				.getCoursemake_need_sh());
		SystemConfOp.setProperty(ElConstants.FORUM_NEED_SH, sysconf
				.getForum_need_sh());
		SystemConfOp.setProperty(ElConstants.KNOWLEDGE_NEED_SH, sysconf
				.getKnowledge_need_sh());
		// SystemConfOp.setProperty(ElConstants.QUIZ_NEED_SH, sysconf
		// .getQuiz_need_sh());
		SystemConfOp.setProperty(ElConstants.REGISTER_NEED_SH, sysconf
				.getRegister_need_sh());
		SystemConfOp.setProperty(ElConstants.ZHENSHU_NEED_SH, sysconf
				.getZhenshu_need_sh());
		// SystemConfOp.setProperty(ElConstants.BK_TIMEOUT, sysconf
		// .getBk_timeout());
		SystemConfOp.setProperty(ElConstants.ZHENSHU_NEED_SH, sysconf
				.getZhenshu_need_sh());
		// SystemConfOp.setProperty(ElConstants.BK_TIMEOUT, sysconf
		// .getBk_timeout());
		// SystemConfOp.setProperty(ElConstants.SHOUYE_IMG, sysconf
		// .getShouye_img());
		// SystemConfOp.setProperty(ElConstants.SHOUYE_URL, sysconf
		// .getShouye_url());
		SystemConfOp.setProperty(ElConstants.OPENMEETINGS_URL, sysconf
				.getOpenmeetings_url());
		SystemConfOp.setProperty(ElConstants.OPENMEETINGS_ADMIN_USER, sysconf
				.getOpenmeetings_admin_user());
		SystemConfOp.setProperty(ElConstants.OPENMEETINGS_ADMIN_PWD, sysconf
				.getOpenmeetings_admin_pwd());
		SystemConfOp.setProperty(ElConstants.STUFF_URL, sysconf
				.getStuff_url());
		SystemConfOp.setProperty(ElConstants.STUFF_URL_LOCAL, sysconf
				.getStuff_url_local());
		SystemConfOp.setProperty(ElConstants.STUFF_SIZE, sysconf
				.getStuff_size());
		// ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		//帖子回复是否需要审核
		SystemConfOp.setProperty(ElConstants.STUFF_ISFTOPIC, sysconf.getStuff_isftopic());
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_REGISTER,
				registerstatus + "");
		registerstatus = SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
		SystemConfOp.setProperty(ElConstants.REGISTER_NEED_SH, sysconf
				.getRegister_need_sh());
		//设置登入是否记录ip
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_LOGIN_ADDIP, sysconf
				.getLogin_addip());
		//设置注册信息是否都要验证
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL, sysconf
				.getRegister_isall());
		//设置学员导入是否需要验证
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK, sysconf
				.getUserimp_ischeck());
		//设置https所用端口
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_HTTPS_PORT, sysconf
				.getHttps_port());
		//设置http所用端口
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_HTTP_PORT, sysconf
				.getHttp_port());
		//设置发布产品是否需要审核
		SystemConfOp.setProperty(ElConstants.PRODUCT_NEED_SH, sysconf
				.isProduct_need_sh());
		//设置发布保险产品是否需要审核
		SystemConfOp.setProperty(ElConstants.BAOXIANPRODUCT_NEED_SH, sysconf
				.isBaoxianProduct_need_sh());
		//设置发布设备是否需要审核
		SystemConfOp.setProperty(ElConstants.SHEBEI_NEED_SH, sysconf
				.isShebei_need_sh());
		
		//设置是否启用视频转换功能
		SystemConfOp.setProperty(ElConstants.SHIPIN_NEED_ZH, sysconf
				.isShipin_need_zh());
		//设置是否启用断点续传功能
		SystemConfOp.setProperty(ElConstants.DUANDIAN_NEED_XC, sysconf
				.isDuandian_need_xc());
		//设置文档上传是否转换
		SystemConfOp.setProperty(ElConstants.FILEUPLOAD_NEED_ZH, sysconf
				.isFileupload_need_zh());
		//是否全文检索
		SystemConfOp.setProperty(ElConstants.SEARCH_NEED, sysconf.isSearch_need());
		
		//产品发布后是否允许修改
		SystemConfOp.setProperty(ElConstants.PRODUCT_FABU_CAN_ALTER, sysconf
				.isProduct_fabu_can_alter());
		//添加线下培训是否需要审核
		SystemConfOp.setProperty(ElConstants.LINE_TRAINING_COURSE_ADD_NEED_SH,sysconf
				.isLine_training_course_add_need_sh());
		
		//office题上传大小设置
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_OFFICE_SIZE, sysconf
				.getOfficeSize());
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_ISEXAM, sysconf
				.getExam());
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_OFFICE_HOME, sysconf
				.getOffice_home());
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_PDF2SWF_PATH, sysconf
				.getPdf2swf_path());
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_YZCODE_OPEN, sysconf
				.getYzcode_open());//登录是否需要验证验证码
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_ALLOWMULTIPLESIGN, sysconf
				.isAllowMultipleSign());//是否禁止多点登陆
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_IS_ENQUIRY_IN_TABLE, sysconf
				.isIs_enquiry_in_table());//是否在全表内资料查询
		SystemConfOp.setProperty(ElConstants.ZDYHTML, sysconf
				.getZdy_html());
		//外经贸
		SystemConfOp.setProperty(ElConstants.PUBLICBEGIN,sysconf.getPublic_begin());
		SystemConfOp.setProperty(ElConstants.PUBLICEND, sysconf.getPublic_end());
		SystemConfOp.setProperty(ElConstants.PUBLICEND2, sysconf.getPublic_end2());
		
		SystemConfOp.setProperty(ElConstants.SYSTEM_KTXZ, sysconf.getKtxzSwf());
		SystemConfOp.setProperty(ElConstants.SYSTEM_KDHXZ, sysconf.getKdhxzSwf());
		SystemConfOp.setProperty(ElConstants.SYSTEM_TYXT, sysconf.getTyxtSwf());
		SystemConfOp.setProperty(ElConstants.SYSTEM_JSBY, sysconf.getJsbySwf());
		SystemConfOp.setProperty(ElConstants.SYSTEM_TZ, sysconf.getTzSwf());
		SystemConfOp.setProperty(ElConstants.SYSTEM_PX, sysconf.getPxSwf());
		
		SystemConfOp.setProperty(ElConstants.SYSTEM_KTXZEDITORHTML, sysconf.getKtxzEditorHtml());
		SystemConfOp.setProperty(ElConstants.SYSTEM_KDHXZEDITORHTML, sysconf.getKdhxzEditorHtml());
		SystemConfOp.setProperty(ElConstants.SYSTEM_TYXTEDITORHTML, sysconf.getTyxtEditorHtml());
		SystemConfOp.setProperty(ElConstants.SYSTEM_JSBYEDITORHTML, sysconf.getJsbyEditorHtml());
		SystemConfOp.setProperty(ElConstants.SYSTEM_TZEDITORHTML, sysconf.getTzEditorHtml());
		SystemConfOp.setProperty(ElConstants.SYSTEM_PXEDITORHTML, sysconf.getPxEditorHtml());
		
		//设置最大登陆数
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_LOGIN_MAX, sysconf
				.getLogin_max());
		//设置最大登陆失败次数
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX, sysconf
				.getLogin_failure_max());
		//设置首页通过率设置
		if(elClasss != null){
			sysconf.setIndex_classid(elClasss.get(0).getId());
			SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_INDEX_CLASSID, sysconf
					.getIndex_classid());
			sysconf.setIndex_class(classDao.getClassById(sysconf.getIndex_classid())); 
		}
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_IS_RECEIVE_BY_JUDGE, sysconf
				.isIs_receive_by_judge());//收件人权限判断
		SystemConfOp.setProperty(ElConstants.SYSTEM_RELEASE_QUESTION_NEED_SH, sysconf
				.isRelease_question_need_sh());//发布问题是否需要审核
		SystemConfOp.setProperty(ElConstants.SYSTEM_ANSWER_QUESTION_NEED_SH, sysconf
				.isAnswer_question_need_sh());//回答问题是否需要审核
		SystemConfOp.setProperty(ElConstants.SYSTEM__MAC_NEED, sysconf.isMac_need());
		SystemConfOp.setProperty(ElConstants.SYSTEM_INTELLIGENTTUTORINGPOINTS,sysconf.getIntelligentTutoringPoints());//智能辅导分达标线
		SystemConfOp.setProperty(ElConstants.SYSTEM_SIMILARITY,sysconf.getSimilarity());//语音识别相似度
		SystemConfOp.setProperty(ElConstants.SYSTEM_WJM,sysconf.getWjm());//是否表示外经贸
		SystemConfOp.setProperty(ElConstants.NEWSHOUYE,sysconf.getNewShouye());//新首页布局
		//sd1223修改
		SystemConfOp.setProperty(ElConstants.SD_ELCLASS, sysconf.getSd_elclass());
		//sd1230
		SystemConfOp.setProperty(ElConstants.SYSTEM_SD,sysconf.getSd());//是否表山山东
		//设置最大登陆数
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_LOGIN_MAX_SD, sysconf
				.getLogin_max_sd());
		setElmessage("设置成功！");
		try {
			SystemConfOp.load();
		} catch (Exception e) {
			setElmessage("系统设置失败");
			return "error";
		}
		
		return "otherset";
	}
	
	public String otherset_delete_index_class() throws ElException {
		//设置首页通过率设置 
		SystemConfOp.setProperty(ElConstants.SYSTEM_CONF_INDEX_CLASSID, 0); 
		setElmessage("设置成功！");
		try {
			SystemConfOp.load();
		} catch (Exception e) {
			setElmessage("系统设置失败");
			return "error";
		}
		return null;
	}

	/**
	 * 系统帮助
	 * 
	 * @return
	 * @throws ElException
	 */
	public String system_view() throws ElException {
		int type = sysconf == null ? 1 : sysconf.getType();
		sysconf = departmentDao.getSystemConfByType(type);
		return "system_view";
	}

	private List<Flink> flinks;
	private Flink flink;
	private FlinkDaoImpl flinkdao = new FlinkDaoImpl();

	public List<Flink> getFlinks() {
		return flinks;
	}

	public void setFlinks(List<Flink> flinks) {
		this.flinks = flinks;
	}

	public Flink getFlink() {
		return flink;
	}

	public void setFlink(Flink flink) {
		this.flink = flink;
	}

	public String flink_addInit() throws Exception {
		return "flink_add";
	}

	public String flink_add() throws ElException {
		flinkdao.addFlink(flink);
		return "flink_add_success";
	}

	public String flink_view() throws ElException {
		flink = flinkdao.getFlinkById(flink.getId());
		return "flink_view";
	}

	public String flink_alterInit() throws ElException {
		flink = flinkdao.getFlinkById(flink.getId());
		return "flink_alter";
	}

	public String flink_alter() throws ElException {
		flinkdao.alterFlink(flink);
		return "flink_alter_success";
	}

	public String flink_upSort() throws ElException {
		flinkdao.upSort(flink.getId());
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		flinks = flinkdao.listFLink(getPageNow(), getPageSize());
		count = flinkdao.flinkSize();
		// int count1 = count;
		//			
		// if (count % pageSize == 0) {
		// count = count / pageSize;
		// } else {
		// count = count / pageSize + 1;
		// }
		// setPagInation(new PagInation(count, pageNow, count1));

		return "flink_upSort";
	}

	public String flink_list() throws Exception {
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		flinks = flinkdao.listFLink(getPageNow(), getPageSize());
		count = flinkdao.flinkSize();
		return "flink_list";
	}

	public String flink_delete() throws ElException {
		flinkdao.flinkDelete(flink.getId());
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		flinks = flinkdao.listFLink(getPageNow(), getPageSize());
		count = flinkdao.flinkSize();
		// int count1 = count;
		//			
		// if (count % pageSize == 0) {
		// count = count / pageSize;
		// } else {
		// count = count / pageSize + 1;
		// }
		// setPagInation(new PagInation(count, pageNow, count1));

		return "flink_delete";
	}

	private List<CourseServer> cservers;
	private CourseServer cserver;
	private CourseDao courseDao;

	public String course_server_list() throws ElException {
		cservers = courseDao.listCourseServer();

		return "course_server_list";
	}

	public String course_server_add() throws ElException {

		courseDao.addCourseServer(cserver);
		return "course_server_list";
	}

	public String course_server_delete() throws ElException {

		courseDao.deleteCourseServer(cserver.getId());
		return "course_server_list";
	}

	public String course_server_alterInit() throws ElException {

		cserver = courseDao.getCourseServer(cserver.getId());
		return "course_server_alter";
	}

	public String course_server_alter() throws ElException {
		courseDao.alterCourseServer(cserver);

		return "course_server_list";
	}
	//业务日志列表
	private ElLog ellog;
	private List<ElLog> elLogs;
	private ELUser eluser;
	public ElLog getEllog() {
		return ellog;
	}

	public void setEllog(ElLog ellog) {
		this.ellog = ellog;
	}

	public List<ElLog> getElLogs() {
		return elLogs;
	}

	public void setElLogs(List<ElLog> elLogs) {
		this.elLogs = elLogs;
	}

	public ELUser getEluser() {
		return eluser;
	}

	public void setEluser(ELUser eluser) {
		this.eluser = eluser;
	}
	/**
	 * 查看日志详情
	 * @return
	 * @throws ElException
	 */
	public String busilogInfo()throws ElException{
		ellog=ElLogger.getBusiInfoById(ellog.getId());
		return "busilogInfo";
	}

	public String busilog_list()throws ElException{
		elLogs= ElLogger.busi_list(eluser, ellog,getPageNow(),getPageSize());
		count  = ElLogger.busi_listsize(eluser, ellog);
		ellog =ellog==null?new ElLog() :ellog;
		return "busilog_list";
	}
	public String syslog_list()throws ElException{
		elLogs= ElLogger.sys_list(eluser, ellog,getPageNow(),getPageSize());
		count  = ElLogger.sys_listsize(eluser, ellog);
		ellog =ellog==null?new ElLog() :ellog;
		return "syslog_list";
	}
	
	
	/**
	 * 首页配置
	 * @return
	 */
	
	
	public String indexOthersetInit() throws ElException{
		indexSysConf = new IndexSystemConfig();
		indexSysConf.setShow_tongzhigonggao(IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_TONGZHIGONGGAO));
		indexSysConf.setShow_daibanshiwu(IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU));
		indexSysConf.setShow_gongzuojihua(IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA));
		indexSysConf.setShow_gongzuorizhi(IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI));
		indexSysConf.setShow_richenganpai(IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_RICHENGANPAI));
		indexSysConf.setShow_gerenkaoqin(IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENKAOQIN));
		indexSysConf.setShow_gerenweishen(IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN));
		indexSysConf.setShow_gerendaishen(IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENDAISHEN));
		indexSysConf.setShow_myallcourses(IndexSystemConfigOp
				.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYALLCOURSES));
		indexSysConf.setShow_myexams(IndexSystemConfigOp.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYEXAMS));
		indexSysConf.setShow_mybuyrooms(IndexSystemConfigOp.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYBUYROOMS));
		indexSysConf.setShow_mytrainingcourses(IndexSystemConfigOp.getBooleanValue(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYTRAININGCOURSES));
		
		indexSysConf.setTongzhigonggao_length(IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_TONGZHIGONGGAO_LENGTH));
		indexSysConf.setDaibanshiwu_length(IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_DAIBANSHIWU_LENGTH));
		indexSysConf.setGongzuojihua_length(IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GONGZUOJIHUA_LENGTH));
		indexSysConf.setGongzuorizhi_length(IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GONGZUORIZHI_LENGTH));
		indexSysConf.setRichenganpai_length(IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_RICHENGANPAI_LENGTH));
		indexSysConf.setMyallcourses_length(IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYALLCOURSES_LENGTH));
		indexSysConf.setMyexams_length(IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYEXAMS_LENGTH));
		indexSysConf.setMybuyrooms_length(IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYBUYROOMS_LENGTH));
		indexSysConf.setMytrainingcourses_length(IndexSystemConfigOp
				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_MYTRAININGCOURSES));
//		indexSysConf.setGerenweishen_length(IndexSystemConfigOp
//				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GERENWEISHEN_LENGTH));
//		indexSysConf.setGerendaishen_length(IndexSystemConfigOp
//				.getIntValue(ElConstants.SYSTEM_INDEXCONFIG_GERENDAISHEN_LENGTH));
		
		return "indexOtherset";
	}
	
	public String indexOtherset() throws ElException{
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_TONGZHIGONGGAO, indexSysConf
				.isShow_tongzhigonggao());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_DAIBANSHIWU, indexSysConf
				.isShow_daibanshiwu());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUOJIHUA, indexSysConf
				.isShow_gongzuojihua());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GONGZUORIZHI, indexSysConf
				.isShow_gongzuorizhi());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_RICHENGANPAI, indexSysConf
				.isShow_richenganpai());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENKAOQIN, indexSysConf
				.isShow_gerenkaoqin());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENWEISHEN, indexSysConf
				.isShow_gerenweishen());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_GERENDAISHEN, indexSysConf
				.isShow_gerendaishen());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYALLCOURSES, indexSysConf
				.isShow_myallcourses());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYEXAMS, indexSysConf
				.isShow_myexams());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYBUYROOMS, indexSysConf
				.isShow_mybuyrooms());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_SHOW_MYTRAININGCOURSES, indexSysConf
				.isShow_mytrainingcourses());
		
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_TONGZHIGONGGAO_LENGTH, indexSysConf
				.getTongzhigonggao_length());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_DAIBANSHIWU_LENGTH, indexSysConf
				.getDaibanshiwu_length());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_GONGZUOJIHUA_LENGTH, indexSysConf
				.getGongzuojihua_length());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_GONGZUORIZHI_LENGTH, indexSysConf
				.getGongzuorizhi_length());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_RICHENGANPAI_LENGTH, indexSysConf
				.getRichenganpai_length());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_MYALLCOURSES_LENGTH, indexSysConf
				.getMyallcourses_length());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_MYEXAMS_LENGTH, indexSysConf
				.getMyexams_length());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_MYBUYROOMS_LENGTH, indexSysConf
				.getMybuyrooms_length());
		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_MYTRAININGCOURSES, indexSysConf
				.getMytrainingcourses_length());
//		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_GERENWEISHEN_LENGTH, indexSysConf
//				.getGerenweishen_length());
//		IndexSystemConfigOp.setProperty(ElConstants.SYSTEM_INDEXCONFIG_GERENDAISHEN_LENGTH, indexSysConf
//				.getGerendaishen_length());
		
		setElmessage("设置成功！");
		try {
			IndexSystemConfigOp.load();
		} catch (Exception e) {
			setElmessage("首页配置失败");
			return "error";
		}
		return "indexOtherset";
	}
	
	/**
	 * JTM接口URL配置
	 * @return
	 * @throws ElException
	 */
	public String JTMOthersetInit() throws ElException{
		jtmSysConf = new JTMSystemConf();
		jtmSysConf.setOpen_jtm(JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM));
		jtmSysConf.setMy_EvaluationInit_URL(JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_MY_EVALUATION_URL));
		jtmSysConf.setReportEvalInit_URL(JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_REPORT_EVAL_URL));
		jtmSysConf.setPeoplePostInit_URL(JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_PEOPLEPOST_URL));
		jtmSysConf.setMy_ReportInit_URL(JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_MY_REPORT_URL));
		jtmSysConf.setCourses_synchronization_URL(JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_COURSES_AYSCHRONIZATION_URL));
		jtmSysConf.setMyCepingCourses_URL(JTMSystemConfOp.getValue(ElConstants.SYSTEM_JTM_MY_CEPINGCOURSES_URL));
		return "JTMOthersetInit";
	}
	
	public String JTMOtherset() throws ElException{
		JTMSystemConfOp.setProperty(ElConstants.SYSTEM_JTM_OPEN_JTM, jtmSysConf.isOpen_jtm());
		JTMSystemConfOp.setProperty(ElConstants.SYSTEM_JTM_MY_EVALUATION_URL, jtmSysConf.getMy_EvaluationInit_URL());
		JTMSystemConfOp.setProperty(ElConstants.SYSTEM_JTM_PEOPLEPOST_URL, jtmSysConf.getPeoplePostInit_URL());
		JTMSystemConfOp.setProperty(ElConstants.SYSTEM_JTM_REPORT_EVAL_URL, jtmSysConf.getReportEvalInit_URL());
		JTMSystemConfOp.setProperty(ElConstants.SYSTEM_JTM_MY_REPORT_URL, jtmSysConf.getMy_ReportInit_URL());
		JTMSystemConfOp.setProperty(ElConstants.SYSTEM_JTM_COURSES_AYSCHRONIZATION_URL, jtmSysConf.getCourses_synchronization_URL());
		JTMSystemConfOp.setProperty(ElConstants.SYSTEM_JTM_MY_CEPINGCOURSES_URL, jtmSysConf.getMyCepingCourses_URL());
		setElmessage("设置成功！");
		try {
			JTMSystemConfOp.load();
		} catch (Exception e) {
			setElmessage("JTM接口URL配置失败");
			return "error";
		}
		return "JTMOtherset";
	}
	
	/**
	 * 自定义模块静态页配置
	 * @return
	 * @throws ElException
	 */
	public String zdyStaticHtmlOthersetInit() throws ElException{
		zdyStaticHtmlSysConf = new ZdyStaticHtmlSystemConf();
		zdyStaticHtmlSysConf.setOpen_all(ZdyStaticHtmlSystemConfOp.getBooleanValue(ElConstants.SYSTEM_ZDY_STATIC_HTML_ALL));
		zdyStaticHtmlSysConf.setOpen_addContactTagsInit(ZdyStaticHtmlSystemConfOp.getBooleanValue(ElConstants.SYSTEM_ZDY_STATIC_HTML_ADDCONTACTTAGSINIT));
		zdyStaticHtmlSysConf.setOpen_updateContactTagsInit(ZdyStaticHtmlSystemConfOp.getBooleanValue(ElConstants.SYSTEM_ZDY_STATIC_HTML_UPDATECONTACTTAGSINIT));
		zdyStaticHtmlSysConf.setOpen_viewContactTags(ZdyStaticHtmlSystemConfOp.getBooleanValue(ElConstants.SYSTEM_ZDY_STATIC_HTML_VIEWCONTACTTAGS));
		return "zdyStaticHtmlOthersetInit";
	}
	
	public String zdyStaticHtmlOtherset() throws ElException{
		ZdyStaticHtmlSystemConfOp.setProperty(ElConstants.SYSTEM_ZDY_STATIC_HTML_ALL, zdyStaticHtmlSysConf.isOpen_all());
		ZdyStaticHtmlSystemConfOp.setProperty(ElConstants.SYSTEM_ZDY_STATIC_HTML_ADDCONTACTTAGSINIT, zdyStaticHtmlSysConf.isOpen_addContactTagsInit());
		ZdyStaticHtmlSystemConfOp.setProperty(ElConstants.SYSTEM_ZDY_STATIC_HTML_UPDATECONTACTTAGSINIT, zdyStaticHtmlSysConf.isOpen_updateContactTagsInit());
		ZdyStaticHtmlSystemConfOp.setProperty(ElConstants.SYSTEM_ZDY_STATIC_HTML_VIEWCONTACTTAGS, zdyStaticHtmlSysConf.isOpen_viewContactTags());
		setElmessage("设置成功！");
		try {
			ZdyStaticHtmlSystemConfOp.load();
		} catch (Exception e) {
			setElmessage("自定义模块静态页配置失败");
			return "error";
		}
		return "zdyStaticHtmlOtherset";
	} 
	
	public ScoreSet getScoreset() {

		return scoreset;
	}

	public void setScoreset(ScoreSet scoreset) {
		this.scoreset = scoreset;
	}

	public int getRegisterstatus() {
		return registerstatus;
	}

	public void setRegisterstatus(int registerstatus) {
		this.registerstatus = registerstatus;
	}

	public CourseType getCtypeTree() {
		return ctypeTree;
	}

	public void setCtypeTree(CourseType ctypeTree) {
		this.ctypeTree = ctypeTree;
	}

	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}

	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}

	public List<CourseType> getCtypes() {
		return ctypes;
	}

	public void setCtypes(List<CourseType> ctypes) {
		this.ctypes = ctypes;
	}

	public FlinkDaoImpl getFlinkdao() {
		return flinkdao;
	}

	public void setFlinkdao(FlinkDaoImpl flinkdao) {
		this.flinkdao = flinkdao;
	}

	public List<CourseServer> getCservers() {
		return cservers;
	}

	public void setCservers(List<CourseServer> cservers) {
		this.cservers = cservers;
	}

	public CourseServer getCserver() {
		return cserver;
	}

	public void setCserver(CourseServer cserver) {
		this.cserver = cserver;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}
	public String htmlsetInit() throws ElException {
		sysconf = new SystemConf();
		// ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true);
		sysconf.setCtype_b(SystemConfOp
				.getIntValue(ElConstants.STUDY_COURSE_CTYPE_B));
		sysconf.setCtype_x(SystemConfOp
				.getIntValue(ElConstants.STUDY_COURSE_CTYPE_X));
		sysconf.setCtype_z(SystemConfOp
				.getIntValue(ElConstants.STUDY_COURSE_CTYPE_Z));

		sysconf.setStudy_course_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.STUDY_COURSE_NEED_SH));
		sysconf.setStudy_class_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.STUDY_CLASS_NEED_SH));
		sysconf.setCoursemake_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.COURSEMAKE_NEED_SH));
		sysconf.setForum_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.FORUM_NEED_SH));
		sysconf.setKnowledge_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.KNOWLEDGE_NEED_SH));
		// sysconf.setQuiz_need_sh(SystemConfOp
		// .getBooleanValue(ElConstants.QUIZ_NEED_SH));
		// sysconf.setRegister_need_sh(SystemConfOp
		// .getBooleanValue(ElConstants.REGISTER_NEED_SH));
		sysconf.setZhenshu_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.ZHENSHU_NEED_SH));
		// sysconf.setBk_timeout(SystemConfOp.getIntValue(ElConstants.BK_TIMEOUT));
		sysconf.setShouye_img(SystemConfOp.getValue(ElConstants.SHOUYE_IMG));
		sysconf.setShouye_url(SystemConfOp.getValue(ElConstants.SHOUYE_URL));
		sysconf.setOpenmeetings_url(SystemConfOp
				.getValue(ElConstants.OPENMEETINGS_URL));
		sysconf.setOpenmeetings_admin_user(SystemConfOp
				.getValue(ElConstants.OPENMEETINGS_ADMIN_USER));
		sysconf.setOpenmeetings_admin_pwd(SystemConfOp
				.getValue(ElConstants.OPENMEETINGS_ADMIN_PWD));
		sysconf.setStuff_url(SystemConfOp.getValue(ElConstants.STUFF_URL));
		sysconf.setStuff_size( SystemConfOp.getIntValue(ElConstants.STUFF_SIZE));
		sysconf.setStuff_isftopic(SystemConfOp.getBooleanValue(ElConstants.STUFF_ISFTOPIC));
		registerstatus = SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_REGISTER);
		sysconf = sysconf == null ? new SystemConf() : sysconf;
		sysconf.setRegister_need_sh(SystemConfOp
				.getBooleanValue(ElConstants.REGISTER_NEED_SH));
		//获取登入是否记录ip
		sysconf.setLogin_addip(SystemConfOp
				.getIntValue(ElConstants.SYSTEM_CONF_LOGIN_ADDIP));
		//注册信息是否都要验证
		sysconf.setRegister_isall(SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_REGISTERINFO_ISALL));
		//学员导入是否需要验证
		sysconf.setUserimp_ischeck(SystemConfOp
				.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK));
		//https所用端口
		sysconf.setHttps_port(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_HTTPS_PORT));
		//http所用端口
		sysconf.setHttp_port(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_HTTP_PORT));
		//最大登陆数
		sysconf.setLogin_max(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_LOGIN_MAX));
		//最大登陆失败次数  0表示不开启次功能
		sysconf.setLogin_failure_max(SystemConfOp
				.getValue(ElConstants.SYSTEM_CONF_LOGIN_FAILURE_MAX));
		
		sysconf.setCatalogue_place(SystemConfOp.getValue(ElConstants.CATALOGUE_PLACE));
		sysconf.setModel_working(SystemConfOp.getValue(ElConstants.MODEL_WORKING));
		sysconf.setPublish_option(SystemConfOp.getValue(ElConstants.PUBLISH_OPTION));
		sysconf.setTitle_rule(SystemConfOp.getValue(ElConstants.TITLE_RULE));
		sysconf.setList_page_number(SystemConfOp.getValue(ElConstants.LIST_PAGE_NUMBER));
		
		
		return "htmlsetInit";
	}
	
	public String htmlset() throws ElException{
		SystemConfOp.setProperty(ElConstants.CATALOGUE_PLACE, sysconf.getCatalogue_place());
		SystemConfOp.setProperty(ElConstants.MODEL_WORKING, sysconf.getModel_working());
		SystemConfOp.setProperty(ElConstants.PUBLISH_OPTION, sysconf.getPublish_option());
		SystemConfOp.setProperty(ElConstants.LIST_PAGE_NUMBER, sysconf.getList_page_number());
		SystemConfOp.setProperty(ElConstants.TITLE_RULE, sysconf.getTitle_rule());
		
		setElmessage("设置成功！");
		publish_option = SystemConfOp.getIntValue(ElConstants.PUBLISH_OPTION);
		try {
			SystemConfOp.load();
		} catch (Exception e) {
			setElmessage("系统设置失败");
			return "error";
		}
		return "htmlset";
	}
	
	public String publishInit() throws ElException{
		publish_option = SystemConfOp.getIntValue(ElConstants.PUBLISH_OPTION);
		ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,	ElConstants.TREE_FIANL, true);
		List<NewsType> child = ntypeTree.getChild();
		int nid = ntype==null ?newsDao.getNtypeRoot().getId():(ntype.getId()==0?1:ntype.getId());
		return "publishInit";
	}
	
	public String pubNew()throws ElException{
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String contextPath = request.getContextPath();
//		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/"; 
		message = this.getMessage();
		List<News> news = new ArrayList<News>();
		if(message!=5&message!=6){
			if(message==1){
				news = newsDao.getNewsByNum(this.getNumber());
				
			}
			if(message==2){
				start = this.getStart();
				end = this.getEnd();
				news = newsDao.getNewsByids(start,end);
			}
			if(message==3){
				news = newsDao.getNewsByNtypeId(ntype.getId());
			}
			if(message==4){
				int ishtml = this.getAll();
				if(ishtml==0){
					//已生成静态页 ishtml=1;未生成静态页 ishtml=0;
					news  =newsDao.getNewsByIsHtml(0);
					for(int i=0;i<news.size();i++){
						newsDao.updateNewsIsHtmlById(news.get(i).getId());
					}
					
				}
				if(ishtml==1){
					news = newsDao.getAllNews();
					for(int i=0;i<news.size();i++){
						newsDao.updateNewsIsHtmlById(news.get(i).getId());
					}
				}
				
			}
			int modelstatus = SystemConfOp.getIntValue(ElConstants.MODEL_WORKING);
			if(modelstatus==3){
				for(int i=0;i<news.size();i++){
					//	String htmlName = "newsIndexView_"+news.get(i).getId()+"_"+news.get(i).getNtid();
						String htmlName = "newsIndexView_"+news.get(i).getId();
						String dirName = "newsindexview";
						String path = "newsIndexView2.shtml?news.id="+news.get(i).getId()+"&ntype.id="+news.get(i).getNtid();
						CreatorHtml.callHtml(path,htmlName,dirName);
					}
			}else{
				for(int i=0;i<news.size();i++){
					//	String htmlName = "newsIndexView_"+news.get(i).getId()+"_"+news.get(i).getNtid();
						String htmlName = "newsIndexView_"+news.get(i).getId();
						String dirName = "newsindexview";
						String path = "newsIndexView2.shtml?news.id="+news.get(i).getId()+"&ntype.id="+news.get(i).getNtid();
						CreatorHtml.callHtml(path,htmlName,dirName);
					}
			}
			
		}
		
		if(message==5){
			number = this.getNumber();
			return "newsindex2";
		}
		if(message==6){
			number = this.getNumber();
			nid = this.getNid();
			return "newsindex3";
		}
		return "success";
	}
	
	
	/**
	 * 积分配置初始化
	 * @return
	 * @throws ElException
	 */
	public String integralInit() throws ElException {
		sysconf = new SystemConf(); 
		sysconf.setLearning_KSCJ(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_KS_CJ));
		sysconf.setLearning_XS(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_XS_XS));
		sysconf.setLearning_LX(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_LX_LX));
		sysconf.setLearning_MK(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_MK_MK));
		sysconf.setLearning_XF(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_XF_XF));
		sysconf.setLearning_BJ(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_BJ_BJ));
		sysconf.setLearning_SC(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_SC_SC));
		sysconf.setLearning_BTJ(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_BTJ_BTJ));
		sysconf.setLearning_BXZ(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_BXZ_BXZ));
		sysconf.setLearning_XZ(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_XZ_XZ));
		sysconf.setLearning_FT(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_FT_FT));
		sysconf.setLearning_FY(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_FY_FY));
		sysconf.setLearning_JH(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_JH_JH));
		sysconf.setLearning_DL(SystemConfOp
				.getFloatValue(ElConstants.LEARNING_DL_DL));
		return "integral";
	} 
	
	/**
	 * 积分设置
	 * @return
	 * @throws ElException
	 */
	public String integral() throws ElException {
		SystemConfOp.setProperty(ElConstants.LEARNING_KS_CJ,sysconf.getLearning_KSCJ());//考试成绩加分
		SystemConfOp.setProperty(ElConstants.LEARNING_XS_XS,sysconf.getLearning_XS());//学时加分
		SystemConfOp.setProperty(ElConstants.LEARNING_LX_LX,sysconf.getLearning_LX());//练习加分
		SystemConfOp.setProperty(ElConstants.LEARNING_MK_MK,sysconf.getLearning_MK());//模考加分
		SystemConfOp.setProperty(ElConstants.LEARNING_XF_XF,sysconf.getLearning_XF());//学分加分
		SystemConfOp.setProperty(ElConstants.LEARNING_BJ_BJ,sysconf.getLearning_BJ());//笔记得分
		SystemConfOp.setProperty(ElConstants.LEARNING_SC_SC,sysconf.getLearning_SC());//上传得分
		SystemConfOp.setProperty(ElConstants.LEARNING_BTJ_BTJ,sysconf.getLearning_BTJ());//被推荐得分
		SystemConfOp.setProperty(ElConstants.LEARNING_BXZ_BXZ,sysconf.getLearning_BXZ());//被下载得分
		SystemConfOp.setProperty(ElConstants.LEARNING_XZ_XZ,sysconf.getLearning_XZ());//下载得分
		SystemConfOp.setProperty(ElConstants.LEARNING_FT_FT,sysconf.getLearning_FT());//发帖得分
		SystemConfOp.setProperty(ElConstants.LEARNING_FY_FY,sysconf.getLearning_FY());//发言得分
		SystemConfOp.setProperty(ElConstants.LEARNING_JH_JH,sysconf.getLearning_JH());//精华帖得分
		SystemConfOp.setProperty(ElConstants.LEARNING_DL_DL,sysconf.getLearning_DL());//登陆加分
		setElmessage("积分系统设置成功！");
		try {
			SystemConfOp.load();
		} catch (Exception e) {
			setElmessage("积分系统设置失败");
			return "error";
		} 
		return "integralInit";
	} 
	/**
	 * 恢复默认配置
	 * @return
	 * @throws ElException
	 */
	public String integral_RestoreDefault() throws ElException {
		SystemConfOp.setProperty(ElConstants.LEARNING_KS_CJ,0.0f);//考试成绩加分
		SystemConfOp.setProperty(ElConstants.LEARNING_XS_XS,3.0f);//学时加分
		SystemConfOp.setProperty(ElConstants.LEARNING_LX_LX,0.0f);//练习加分
		SystemConfOp.setProperty(ElConstants.LEARNING_MK_MK,0.0f);//模考加分
		SystemConfOp.setProperty(ElConstants.LEARNING_XF_XF,5.0f);//学分加分
		SystemConfOp.setProperty(ElConstants.LEARNING_BJ_BJ,0.0f);//笔记得分
		SystemConfOp.setProperty(ElConstants.LEARNING_SC_SC,5.0f);//上传得分
		SystemConfOp.setProperty(ElConstants.LEARNING_BTJ_BTJ,0.0f);//被推荐得分
		SystemConfOp.setProperty(ElConstants.LEARNING_BXZ_BXZ,0.0f);//被下载得分
		SystemConfOp.setProperty(ElConstants.LEARNING_XZ_XZ,0.0f);//下载得分
		SystemConfOp.setProperty(ElConstants.LEARNING_FT_FT,0.5f);//发帖得分
		SystemConfOp.setProperty(ElConstants.LEARNING_FY_FY,0.1f);//发言得分
		SystemConfOp.setProperty(ElConstants.LEARNING_JH_JH,5.0f);//精华帖得分
		SystemConfOp.setProperty(ElConstants.LEARNING_DL_DL,0.1f);//登陆加分
		setElmessage("积分系统恢复默认配置成功！");
		try {
			SystemConfOp.load();
		} catch (Exception e) {
			setElmessage("积分系统恢复默认配置失败");
			return "error";
		} 
		return "integralInit";
	}
}
