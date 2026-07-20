package com.sopia.common;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;

/**
 * 系统配置实体
 * @author Administrator
 *
 */
public class SystemConf {
	private int type;
	private String content;
//	private String typeName;
	private int ctype_z;
	private int ctype_b;
	private int ctype_x;
	private boolean study_course_need_sh;//选课
	private boolean study_class_need_sh;//选班
	
	private boolean coursemake_need_sh;//课制作
	private boolean zhenshu_need_sh;//证书
//	private boolean quiz_need_sh;//批卷
	private boolean knowledge_need_sh;//知识发布
	private boolean forum_need_sh;//帖子发布
	private boolean register_need_sh;//注册审核
//	private int bk_timeout;
	private String shouye_img;
	private String shouye_url;
	private String openmeetings_url;
	private String openmeetings_admin_user;
	private String openmeetings_admin_pwd;
	private String stuff_url;
	private String stuff_url_local;
	private int stuff_size;
	private int login_addip;
	private boolean	stuff_isftopic;
	private boolean register_isall;
	private boolean userimp_ischeck;
	private String https_port;
	private String http_port;
	
	private boolean product_need_sh;//发布产品是否需要审核
	private boolean baoxianProduct_need_sh;//保险产品发布是否需要审核
	private boolean shebei_need_sh;//设备发布是否需要审核
	
	private boolean shipin_need_zh;//是否开启视频转码功能
	private boolean duandian_need_xc;//是否启用断点续传功能
	private boolean fileupload_need_zh;//文档上传是否转换
	private boolean product_fabu_can_alter;//产品发布==审核后是否允许修改	允许:true	不允许:false
	private boolean line_training_course_add_need_sh;//添加线下培训是否需要审核
	private boolean mac_need;//登录是否限定ＭＡＣ
	private int intelligentTutoringPoints;//智能辅导分达标分数
	
	private int yzcode_open;//登录是否需要验证码验证
	private boolean allowMultipleSign;//是否禁止多点登陆
	private boolean is_enquiry_in_table;//是否在全表内资料查询
	
	private String model_working;	//运行模式
	private String publish_option;	//发布选项	
	private String list_page_number;	//生成列表分页数
	private String catalogue_place;		//生成的总目录	
	private String zdy_html;//自定义目录
	private boolean is_receive_by_judge;//收件人权限判断
	private String title_rule;			//栏目页规则
	
	private String login_max; 
	private String login_failure_max;
	private int index_classid;//首页通过率设置 
	private ElClass index_class; 
	
	private boolean release_question_need_sh;//发布问题是否需要审核
	private boolean answer_question_need_sh;//回答问题是否需要审核
	//发声提问文件和正确答案文件保存路径名
	private String public_begin;
	private String public_end;
	private String public_end2;
	
	private int similarity;//语音识别相似度
	private int wjm;//是否表示外经贸
	private int newShouye;//新首页布局  0==ELN系统,1==信息管理系统 
	
	private boolean search_need;//是否全文检索
	
	private String ktxzSwf;	//看图选择帮助swf
	private String kdhxzSwf;//看动画选择帮助swf
	private String tyxtSwf;	//听音选图帮助swf
	private String jsbySwf;	//角色扮演帮助swf
	private String tzSwf;	//拖拽帮助swf
	private String pxSwf;	//排序帮助swf
	
	private String ktxzEditorHtml;	//看图选择编辑器HTML
	private String kdhxzEditorHtml; //看动画选择编辑器HTML
	private String tyxtEditorHtml;	//听音选图编辑器HTML
	private String jsbyEditorHtml;	//角色扮演编辑器HTML
	private String tzEditorHtml;	//拖拽编辑器HTML
	private String pxEditorHtml;	//排序编辑器HTML
	
	//sd1223修改
	private String sd_elclass;//山东培训班
	//sd1230
	private int sd;//是否表示山东项目
	private int  login_max_sd;//山东最大登陆数
	
	
	
	public int getSd() {
		return sd;
	}
	public void setSd(int sd) {
		this.sd = sd;
	}
	public String getKtxzEditorHtml() {
		return ktxzEditorHtml;
	}
	public void setKtxzEditorHtml(String ktxzEditorHtml) {
		this.ktxzEditorHtml = ktxzEditorHtml;
	}
	public String getKdhxzEditorHtml() {
		return kdhxzEditorHtml;
	}
	public void setKdhxzEditorHtml(String kdhxzEditorHtml) {
		this.kdhxzEditorHtml = kdhxzEditorHtml;
	}
	public String getTyxtEditorHtml() {
		return tyxtEditorHtml;
	}
	public void setTyxtEditorHtml(String tyxtEditorHtml) {
		this.tyxtEditorHtml = tyxtEditorHtml;
	}
	public String getJsbyEditorHtml() {
		return jsbyEditorHtml;
	}
	public void setJsbyEditorHtml(String jsbyEditorHtml) {
		this.jsbyEditorHtml = jsbyEditorHtml;
	}
	public String getTzEditorHtml() {
		return tzEditorHtml;
	}
	public void setTzEditorHtml(String tzEditorHtml) {
		this.tzEditorHtml = tzEditorHtml;
	}
	public String getPxEditorHtml() {
		return pxEditorHtml;
	}
	public void setPxEditorHtml(String pxEditorHtml) {
		this.pxEditorHtml = pxEditorHtml;
	}
	public String getKtxzSwf() {
		return ktxzSwf;
	}
	public void setKtxzSwf(String ktxzSwf) {
		this.ktxzSwf = ktxzSwf;
	}
	public String getKdhxzSwf() {
		return kdhxzSwf;
	}
	public void setKdhxzSwf(String kdhxzSwf) {
		this.kdhxzSwf = kdhxzSwf;
	}
	public String getTyxtSwf() {
		return tyxtSwf;
	}
	public void setTyxtSwf(String tyxtSwf) {
		this.tyxtSwf = tyxtSwf;
	}
	public String getJsbySwf() {
		return jsbySwf;
	}
	public void setJsbySwf(String jsbySwf) {
		this.jsbySwf = jsbySwf;
	}
	public String getPxSwf() {
		return pxSwf;
	}
	public void setPxSwf(String pxSwf) {
		this.pxSwf = pxSwf;
	}
	public String getTzSwf() {
		return tzSwf;
	}
	public void setTzSwf(String tzSwf) {
		this.tzSwf = tzSwf;
	}
	public boolean isSearch_need() {
		return search_need;
	}
	public void setSearch_need(boolean search_need) {
		this.search_need = search_need;
	}
	public int getWjm() {
		return wjm;
	}
	public void setWjm(int wjm) {
		this.wjm = wjm;
	}
	public int getSimilarity() {
		return similarity;
	}
	public void setSimilarity(int similarity) {
		this.similarity = similarity;
	}
	public int getIntelligentTutoringPoints() {
		return intelligentTutoringPoints;
	}
	public void setIntelligentTutoringPoints(int intelligentTutoringPoints) {
		this.intelligentTutoringPoints = intelligentTutoringPoints;
	}
	public String getPublic_end2() {
		return public_end2;
	}
	public void setPublic_end2(String public_end2) {
		this.public_end2 = public_end2;
	}
	public boolean isRelease_question_need_sh() {
		return release_question_need_sh;
	}
	public void setRelease_question_need_sh(boolean release_question_need_sh) {
		this.release_question_need_sh = release_question_need_sh;
	}
	public boolean isAnswer_question_need_sh() {
		return answer_question_need_sh;
	}
	public void setAnswer_question_need_sh(boolean answer_question_need_sh) {
		this.answer_question_need_sh = answer_question_need_sh;
	}
	//积分配置
	private float learning_KSCJ; //考试成绩
	private float learning_XS; //学时加分
	private float learning_LX; //练习加分
	private float learning_MK; //模考加分
	private float learning_XF; //学分加分
	private float learning_BJ; //笔记得分
	private float learning_SC; //上传得分
	private float learning_BTJ; //被推荐得分
	private float learning_BXZ; //被下载得分
	private float learning_XZ; //下载得分
	private float learning_FT; //发帖得分
	private float learning_FY; //发言得分
	private float learning_JH; //精华帖得分
	private float learning_DL; //登陆加分
	
	
	public float getLearning_KSCJ() {
		return learning_KSCJ;
	}

	public void setLearning_KSCJ(float learning_KSCJ) {
		this.learning_KSCJ = learning_KSCJ;
	}

	public float getLearning_XS() {
		return learning_XS;
	}

	public void setLearning_XS(float learning_XS) {
		this.learning_XS = learning_XS;
	}

	public float getLearning_LX() {
		return learning_LX;
	}

	public void setLearning_LX(float learning_LX) {
		this.learning_LX = learning_LX;
	}

	public float getLearning_MK() {
		return learning_MK;
	}

	public void setLearning_MK(float learning_MK) {
		this.learning_MK = learning_MK;
	}

	public float getLearning_XF() {
		return learning_XF;
	}

	public void setLearning_XF(float learning_XF) {
		this.learning_XF = learning_XF;
	}

	public float getLearning_BJ() {
		return learning_BJ;
	}

	public void setLearning_BJ(float learning_BJ) {
		this.learning_BJ = learning_BJ;
	}

	public float getLearning_SC() {
		return learning_SC;
	}

	public void setLearning_SC(float learning_SC) {
		this.learning_SC = learning_SC;
	}

	public float getLearning_BTJ() {
		return learning_BTJ;
	}

	public void setLearning_BTJ(float learning_BTJ) {
		this.learning_BTJ = learning_BTJ;
	}

	public float getLearning_BXZ() {
		return learning_BXZ;
	}

	public void setLearning_BXZ(float learning_BXZ) {
		this.learning_BXZ = learning_BXZ;
	}

	public float getLearning_XZ() {
		return learning_XZ;
	}

	public void setLearning_XZ(float learning_XZ) {
		this.learning_XZ = learning_XZ;
	}

	public float getLearning_FT() {
		return learning_FT;
	}

	public void setLearning_FT(float learning_FT) {
		this.learning_FT = learning_FT;
	}

	public float getLearning_FY() {
		return learning_FY;
	}

	public void setLearning_FY(float learning_FY) {
		this.learning_FY = learning_FY;
	}

	public float getLearning_JH() {
		return learning_JH;
	}

	public void setLearning_JH(float learning_JH) {
		this.learning_JH = learning_JH;
	}

	public float getLearning_DL() {
		return learning_DL;
	}

	public void setLearning_DL(float learning_DL) {
		this.learning_DL = learning_DL;
	}

	public ElClass getIndex_class() {
		return index_class;
	}

	public void setIndex_class(ElClass index_class) {
		this.index_class = index_class;
	}

	public int getIndex_classid() {
		return index_classid;
	}

	public void setIndex_classid(int index_classid) {
		this.index_classid = index_classid;
	}
	
	public String getZdy_html() {
		return zdy_html;
	}
	public void setZdy_html(String zdy_html) {
		this.zdy_html = zdy_html;
	}
	public String getModel_working() {
		return model_working;
	}
	public void setModel_working(String model_working) {
		this.model_working = model_working;
	}
	public String getPublish_option() {
		return publish_option;
	}
	public void setPublish_option(String publish_option) {
		this.publish_option = publish_option;
	}
	public String getList_page_number() {
		return list_page_number;
	}
	public void setList_page_number(String list_page_number) {
		this.list_page_number = list_page_number;
	}
	public String getCatalogue_place() {
		return catalogue_place;
	}
	public void setCatalogue_place(String catalogue_place) {
		this.catalogue_place = catalogue_place;
	}
	public String getTitle_rule() {
		return title_rule;
	}
	public void setTitle_rule(String title_rule) {
		this.title_rule = title_rule;
	}
	public boolean isIs_enquiry_in_table() {
		return is_enquiry_in_table;
	}
	public void setIs_enquiry_in_table(boolean is_enquiry_in_table) {
		this.is_enquiry_in_table = is_enquiry_in_table;
	}
	public boolean isAllowMultipleSign() {
		return allowMultipleSign;
	}
	public void setAllowMultipleSign(boolean allowMultipleSign) {
		this.allowMultipleSign = allowMultipleSign;
	}
	public int getYzcode_open() {
		return yzcode_open;
	}
	public void setYzcode_open(int yzcode_open) {
		this.yzcode_open = yzcode_open;
	}
	public boolean isProduct_need_sh() {
		return product_need_sh;
	}
	public void setProduct_need_sh(boolean product_need_sh) {
		this.product_need_sh = product_need_sh;
	}
	public boolean isBaoxianProduct_need_sh() {
		return baoxianProduct_need_sh;
	}
	public void setBaoxianProduct_need_sh(boolean baoxianProduct_need_sh) {
		this.baoxianProduct_need_sh = baoxianProduct_need_sh;
	}
	public boolean isShebei_need_sh() {
		return shebei_need_sh;
	}
	public void setShebei_need_sh(boolean shebei_need_sh) {
		this.shebei_need_sh = shebei_need_sh;
	}
	public boolean isProduct_fabu_can_alter() {
		return product_fabu_can_alter;
	}
	public void setProduct_fabu_can_alter(boolean product_fabu_can_alter) {
		this.product_fabu_can_alter = product_fabu_can_alter;
	}
	public boolean isLine_training_course_add_need_sh() {
		return line_training_course_add_need_sh;
	}
	public void setLine_training_course_add_need_sh(
			boolean line_training_course_add_need_sh) {
		this.line_training_course_add_need_sh = line_training_course_add_need_sh;
	}
	private int officeSize;
	private boolean exam;
	private String office_home;
	private String pdf2swf_path;
	public String getOffice_home() {
		return office_home;
	}
	public void setOffice_home(String office_home) {
		this.office_home = office_home;
	}
	public String getPdf2swf_path() {
		return pdf2swf_path;
	}
	public void setPdf2swf_path(String pdf2swf_path) {
		this.pdf2swf_path = pdf2swf_path;
	}
	public int getOfficeSize() {
		return officeSize;
	}
	public void setOfficeSize(int officeSize) {
		this.officeSize = officeSize;
	}
	public String getHttp_port() {
		return http_port;
	}
	public void setHttp_port(String http_port) {
		this.http_port = http_port;
	}
	public String getHttps_port() {
		return https_port;
	}
	public void setHttps_port(String https_port) {
		this.https_port = https_port;
	}
	public boolean getRegister_isall() {
		return register_isall;
	}
	public void setRegister_isall(boolean register_isall) {
		this.register_isall = register_isall;
	}
	public boolean getUserimp_ischeck() {
		return userimp_ischeck;
	}
	public void setUserimp_ischeck(boolean userimp_ischeck) {
		this.userimp_ischeck = userimp_ischeck;
	}
	public boolean getStuff_isftopic() {
		return stuff_isftopic;
	}
	public void setStuff_isftopic(boolean stuff_isftopic) {
		this.stuff_isftopic = stuff_isftopic;
	}
	public int getLogin_addip() {
		return login_addip;
	}
	public void setLogin_addip(int login_addip) {
		this.login_addip = login_addip;
	}
	public int getStuff_size() {
		return stuff_size;
	}
	public void setStuff_size(int stuff_size) {
		this.stuff_size = stuff_size;
	}
	public String getStuff_url() {
		return stuff_url;
	}
	public void setStuff_url(String stuff_url) {
		this.stuff_url = stuff_url;
	}
	//	public int getBk_timeout() {
//		return bk_timeout;
//	}
//	public void setBk_timeout(int bk_timeout) {
//		this.bk_timeout = bk_timeout;
//	}
	public int getCtype_z() {
		return ctype_z;
	}
	public void setCtype_z(int ctype_z) {
		this.ctype_z = ctype_z;
	}
	public int getCtype_b() {
		return ctype_b;
	}
	public void setCtype_b(int ctype_b) {
		this.ctype_b = ctype_b;
	}
	public int getCtype_x() {
		return ctype_x;
	}
	public void setCtype_x(int ctype_x) {
		this.ctype_x = ctype_x;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTypeName() {
		switch (type) {
		case 1:
			return "个人中心";
		case 2:
			return "课程中心";
		case 3:
			return "在线考试";
		case 4:
			return "学习通知";
		case 5:
			return "积分办法";
		case 6:
			return "联系我们";
		case 7:
			return "学习帮助";
		case 8:
			return "联系我们";
		default:
			return "未知类别";
		}
//		return typeName;
	}
//	public void setTypeName(String typeName) {
//		this.typeName = typeName;
//	}
	public boolean getStudy_course_need_sh() {
		return study_course_need_sh;
	}
	public void setStudy_course_need_sh(boolean study_course_need_sh) {
		this.study_course_need_sh = study_course_need_sh;
	}
	public boolean getStudy_class_need_sh() {
		return study_class_need_sh;
	}
	public void setStudy_class_need_sh(boolean study_class_need_sh) {
		this.study_class_need_sh = study_class_need_sh;
	}
	public boolean getCoursemake_need_sh() {
		return coursemake_need_sh;
	}
	public void setCoursemake_need_sh(boolean coursemake_need_sh) {
		this.coursemake_need_sh = coursemake_need_sh;
	}
	public boolean getZhenshu_need_sh() {
		return zhenshu_need_sh;
	}
	public void setZhenshu_need_sh(boolean zhenshu_need_sh) {
		this.zhenshu_need_sh = zhenshu_need_sh;
	}
//	public boolean getQuiz_need_sh() {
//		return quiz_need_sh;
//	}
//	public void setQuiz_need_sh(boolean quiz_need_sh) {
//		this.quiz_need_sh = quiz_need_sh;
//	}
	public boolean getKnowledge_need_sh() {
		return knowledge_need_sh;
	}
	public void setKnowledge_need_sh(boolean knowledge_need_sh) {
		this.knowledge_need_sh = knowledge_need_sh;
	}
	public boolean getForum_need_sh() {
		return forum_need_sh;
	}
	public void setForum_need_sh(boolean forum_need_sh) {
		this.forum_need_sh = forum_need_sh;
	}
	public boolean getRegister_need_sh() {
		return register_need_sh;
	}
	public void setRegister_need_sh(boolean register_need_sh) {
		this.register_need_sh = register_need_sh;
	}
	public String getShouye_img() {
		return shouye_img;
	}
	public void setShouye_img(String shouye_img) {
		this.shouye_img = shouye_img;
	}
	public String getShouye_url() {
		return shouye_url;
	}
	public void setShouye_url(String shouye_url) {
		this.shouye_url = shouye_url;
	}
	public String getOpenmeetings_url() {
		return openmeetings_url;
	}
	public void setOpenmeetings_url(String openmeetings_url) {
		this.openmeetings_url = openmeetings_url;
	}
	public String getOpenmeetings_admin_user() {
		return openmeetings_admin_user;
	}
	public void setOpenmeetings_admin_user(String openmeetings_admin_user) {
		this.openmeetings_admin_user = openmeetings_admin_user;
	}
	public String getOpenmeetings_admin_pwd() {
		return openmeetings_admin_pwd;
	}
	public void setOpenmeetings_admin_pwd(String openmeetings_admin_pwd) {
		this.openmeetings_admin_pwd = openmeetings_admin_pwd;
	}
	public String getStuff_url_local() {
		return stuff_url_local;
	}
	public void setStuff_url_local(String stuff_url_local) {
		this.stuff_url_local = stuff_url_local;
	}
	public boolean getExam() {
		return exam;
	}
	public void setExam(boolean exam) {
		this.exam = exam;
	}
	public String getLogin_max() {
		return login_max;
	}
	public void setLogin_max(String login_max) {
		this.login_max = login_max;
	}
	public String getLogin_failure_max() {
		return login_failure_max;
	}
	public void setLogin_failure_max(String login_failure_max) {
		this.login_failure_max = login_failure_max;
	}
	public boolean isIs_receive_by_judge() {
		return is_receive_by_judge;
	}
	public void setIs_receive_by_judge(boolean is_receive_by_judge) {
		this.is_receive_by_judge = is_receive_by_judge;
	}
	
	public String getPublic_begin() {
		return public_begin;
	}
	public void setPublic_begin(String public_begin) {
		this.public_begin = public_begin;
	}
	public String getPublic_end() {
		return public_end;
	}
	public void setPublic_end(String public_end) {
		this.public_end = public_end;
	}
	public boolean isMac_need() {
		return mac_need;
	}
	public void setMac_need(boolean mac_need) {
		this.mac_need = mac_need;
	}
	public boolean isShipin_need_zh() {
		return shipin_need_zh;
	}
	public void setShipin_need_zh(boolean shipin_need_zh) {
		this.shipin_need_zh = shipin_need_zh;
	}
	public boolean isDuandian_need_xc() {
		return duandian_need_xc;
	}
	public void setDuandian_need_xc(boolean duandian_need_xc) {
		this.duandian_need_xc = duandian_need_xc;
	}
	public boolean isFileupload_need_zh() {
		return fileupload_need_zh;
	}
	public void setFileupload_need_zh(boolean fileupload_need_zh) {
		this.fileupload_need_zh = fileupload_need_zh;
	}
	public int getNewShouye() {
		return newShouye;
	}
	public void setNewShouye(int newShouye) {
		this.newShouye = newShouye;
	}
	public String getSd_elclass() {
		return sd_elclass;
	}
	public void setSd_elclass(String sd_elclass) {
		this.sd_elclass = sd_elclass;
	}
	public int getLogin_max_sd() {
		return login_max_sd;
	}
	public void setLogin_max_sd(int login_max_sd) {
		this.login_max_sd = login_max_sd;
	}
	
	
}
