package com.sopia.questionman.action;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ExcelUtil;
import com.sopia.common.ExportWord;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.StringUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionArt;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.simulation.util.SimulationUtil;

public class QuestionAction extends BaseAction {
	private QuestionLib questionLib;
	private QuestionLib qlbTree;
	private QuestionDao questionDao;
	private int sub_operate;
	private Question question;
	private List<Question> questions;
	private int sublibs;
	private File st;
	private String stFileName;
	private String sfContentType;
	private static final Log logger = LogFactory.getLog(QuestionAction.class);

//	private StuffLib qstuff;
//	private StuffLib qpstuff;
//	private StuffLib stuffTree;
//	private StuffLib stuffSharedTree;
//	private List<StuffLib> qstuffs;
	private String stfilename;
	private int st_type;
	private List<Question> questionlist;
	private List<QuestionLib> questionliblist;
	private int isCaiLiao;
	private boolean exprot;
	private boolean SelectExprot;
	private int qlbid;// 试题库id用户导出
	private int questionParid;
	private String chks;
	private boolean Leeren;//清空导出箱子Session
	private String optype;
	private ELUser elUser;
	RoleDao roleDao;
	private List<QuestionArt> questionarts;
	QuestionArt questionart;
	private String copy; 	//复制
//	private SystemConf sysconf;
//	InputStream inputStream;
//	private String downFileName;
//
//	public String getDownFileName() {
//		return downFileName;
//	}
//
//	public void setDownFileName(String downFileName) {
//		this.downFileName = downFileName;
//	}

	public String getCopy() {
		return copy;
	}

	public void setCopy(String copy) {
		this.copy = copy;
	}

	public String getChks() {
		return chks;
	}

	public void setChks(String chks) {
		this.chks = chks;
	}

	public int getQuestionParid() {
		return questionParid;
	}

	public void setQuestionParid(int questionParid) {
		this.questionParid = questionParid;
	}

	public int getIsCaiLiao() {
		return isCaiLiao;
	}

	public void setIsCaiLiao(int isCaiLiao) {
		this.isCaiLiao = isCaiLiao;
	}

	public int getQlbid() {
		return qlbid;
	}

	public void setQlbid(int qlbid) {
		this.qlbid = qlbid;
	}

	public boolean isExprot() {
		return exprot;
	}

	public void setExprot(boolean exprot) {
		this.exprot = exprot;
	}

	public List<QuestionLib> getQuestionliblist() {
		return questionliblist;
	}

	public void setQuestionliblist(List<QuestionLib> questionliblist) {
		this.questionliblist = questionliblist;
	}

	public List<Question> getQuestionlist() {
		return questionlist;
	}

	public void setQuestionlist(List<Question> questionlist) {
		this.questionlist = questionlist;
	}

	public String getStfilename() {
		return stfilename;
	}

	public void setStfilename(String stfilename) {
		this.stfilename = stfilename;
	}

	public int getSt_type() {
		return st_type;
	}

	public void setSt_type(int st_type) {
		this.st_type = st_type;
	}

//	public List<StuffLib> getQstuffs() {
//		return qstuffs;
//	}
//
//	public void setQstuffs(List<StuffLib> qstuffs) {
//		this.qstuffs = qstuffs;
//	}
//
//	public StuffLib getQstuff() {
//		return qstuff;
//	}
//
//	public void setQstuff(StuffLib qstuff) {
//		this.qstuff = qstuff;
//	}

	public File getSt() {
		return st;
	}

	public void setSt(File st) {
		this.st = st;
	}

	public String getStFileName() {
		return stFileName;
	}

	public void setStFileName(String stFileName) {
		this.stFileName = stFileName;
	}

	public String getSfContentType() {
		return sfContentType;
	}

	public void setSfContentType(String sfContentType) {
		this.sfContentType = sfContentType;
	}

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public int getSub_operate() {
		return sub_operate;
	}

	public void setSub_operate(int sub_operate) {
		this.sub_operate = sub_operate;
	}

	public String question_lib_list() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}

		return "question_lib_list";
	}

	public String question_lib_addInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}

		if (qlbTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的题库");
			return "error";
		}
		return "question_lib_add";
	}

	public String question_lib_add() throws ElException {
		if (questionLib.getParent() == null
				|| questionLib.getParent().getId() <= 0) {
			setElmessage("请选择上级试题库！");
			return "error";
		}
		questionDao.addQuestionLib(questionLib);
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("QUESTION_LIB");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_QUESTIONLIB,
						ElLoggerConstants.LOG_TYPE_ADD,questionLib.getName(),
						ElLoggerConstants.LOG_RES_SUCC,questionLib.getId());
		return "question_lib_add_success";
	}

	public String question_lib_view() throws ElException {
		questionLib = questionDao.getQLbById(questionLib.getId());
		if (questionLib.getId() <= 0) {
			setElmessage("您需要查看的题库不存在,请重新选择！");
			return "error";
		}
		questionLib.setOpusers(questionDao
				.getOpUsers("op", questionLib.getId()));
//		questionLib.setUseusers(questionDao.getOpUsers("op", questionLib
//				.getId()));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		return "question_lib_view";
	}

	public String question_lib_alterInit() throws ElException {
		questionLib = questionDao.getQLbById(questionLib.getId());
		// qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
		// getSessionIntValue(ElConstants.SESSION_USERID), questionLib
		// .getId(), false);
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
		// getSessionIntValue(ElConstants.SESSION_USERID), questionLib
		// .getId(), false);
		// else {
		// qlbTree = questionDao.getQlibTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op",
		// questionLib.getId(), false);
		// }
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}

		questionLib.setOpusers(questionDao
				.getOpUsers("op", questionLib.getId()));
//		questionLib.setUseusers(questionDao.getOpUsers("op", questionLib
//				.getId()));
		return "question_lib_alter";
	}

	public String qlib_delete_user() throws ElException {
		questionDao.deleteOpusers(optype, elUser.getId(), questionLib.getId());
		roleDao.checkUserfunc(elUser.getId(), "question_lib_list",
				"questionlib_op_user");
		roleDao.checkUserfunc(elUser.getId(), "question_lib_addInit",
				"questionlib_op_user");
		roleDao.checkUserfunc(elUser.getId(), "question_listInit",
				"questionlib_op_user");
		roleDao.checkUserfunc(elUser.getId(), "question_addInit",
				"questionlib_op_user");
		roleDao.checkUserfunc(elUser.getId(), "admin", "questionlib_op_user");
		return null;
	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public QuestionArt getQuestionart() {
		return questionart;
	}

	public void setQuestionart(QuestionArt questionart) {
		this.questionart = questionart;
	}

	public String questionartlist() throws ElException {
		questionart = questionart == null ? new QuestionArt() : questionart;
		String title = questionart.getTitle() == null ? "" : questionart
				.getTitle();
		questionarts = questionDao
				.listQarts(title, getPageNow(), getPageSize());
		count = questionDao.listQartsSize(title);
		return "questionartlist";
	}

	public String qanswer_search() throws ElException {
		questionart = questionart == null ? new QuestionArt() : questionart;
		String title = questionart.getTitle() == null ? "" : questionart
				.getTitle();
		questionarts = questionDao
				.listQarts(title, getPageNow(), getPageSize());
		count = questionDao.listQartsSize(title);
		return "qanswer_searchlist";
	}

	public String questionartdelete() throws ElException {
		questionart = questionart == null ? new QuestionArt() : questionart;
		questionDao.deleteQart(questionart.getId());
		String title = questionart.getTitle() == null ? "" : questionart
				.getTitle();
		questionarts = questionDao
				.listQarts(title, getPageNow(), getPageSize());
		count = questionDao.listQartsSize(title);
		return "questionartlist";
	}

	public String questionartaddinit() throws ElException {
		return "questionartadd";
	}

	public String questionartadd() throws ElException {
		questionDao.addQart(questionart);
		questionart = questionart == null ? new QuestionArt() : questionart;
		String title = questionart.getTitle() == null ? "" : questionart
				.getTitle();
		questionarts = questionDao
				.listQarts(title, getPageNow(), getPageSize());
		count = questionDao.listQartsSize(title);
		questionart = questionDao.getQart(questionart.getId());
		return "questionartlist";
	}

	public String questionartalterinit() throws ElException {
		questionart = questionDao.getQart(questionart.getId());
		return "questionartalter";
	}

	public String questionartalter() throws ElException {
		questionDao.alterQart(questionart);
		questionart = questionart == null ? new QuestionArt() : questionart;
		String title = questionart.getTitle() == null ? "" : questionart
				.getTitle();
		questionarts = questionDao
				.listQarts(title, getPageNow(), getPageSize());
		count = questionDao.listQartsSize(title);
		questionart = questionDao.getQart(questionart.getId());
		return "questionartlist";
	}

	public String question_lib_alter() throws ElException {
		if (questionLib.getId() == 1) {
			questionLib.setParent(new QuestionLib(0));
		}
		if(questionLib.getParent()==null){
			questionLib.setParent(new ElNode(1));
		}
		questionDao.alterQLB(questionLib);
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("QUESTION_LIB");
		if (null != questionLib.getOpusers()) {
			for (int i = 0; i < questionLib.getOpusers().size(); i++) {
				if (!questionDao.checkOpUsers("op", questionLib.getOpusers()
						.get(i).getId(), questionLib.getId()))// 2.操作人id
					// 3.题库id
					questionDao.addOpusers("op", questionLib.getOpusers()
							.get(i).getId(), questionLib.getId());
//				roleDao.setUserfunc(questionLib.getOpusers().get(i).getId(),
//						"question_lib_list", 0);
//				roleDao.setUserfunc(questionLib.getOpusers().get(i).getId(),
//						"question_lib_addInit", 0);
//				roleDao.setUserfunc(questionLib.getOpusers().get(i).getId(),
//						"question_listInit", 0);
//				roleDao.setUserfunc(questionLib.getOpusers().get(i).getId(),
//						"question_addInit", 0);
//				roleDao.setUserfunc(questionLib.getOpusers().get(i).getId(),
//						"admin", 0);
			}
		}
		if (null != questionLib.getUseusers()) {
			for (int i = 0; i < questionLib.getUseusers().size(); i++) {
				if (!questionDao.checkOpUsers("op", questionLib.getUseusers()
						.get(i).getId(), questionLib.getId()))
					questionDao.addOpusers("op", questionLib.getUseusers()
							.get(i).getId(), questionLib.getId());
			}
		}
		questionLib = questionDao.getQLbById(questionLib.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_QUESTIONLIB,
				ElLoggerConstants.LOG_TYPE_ALTER,questionLib.getName(),
				ElLoggerConstants.LOG_RES_SUCC,questionLib.getId());
		return "question_lib_alter_success";
	}

	public String question_lib_deleteInit() throws ElException {
		if (questionLib.getId() == 1) {
			setElmessage("不能删除根类别");
			return "error";
		}
		questionLib = questionDao.getQLbById(questionLib.getId());
		return "question_lib_delete";
	}

	public String question_lib_delete() throws ElException {
		if (questionLib.getId() == 1) {
			setElmessage("不能删除根类别");
			return "error";
		}
		questionLib = questionDao.getQLbById(questionLib.getId());
		int id = questionLib.getId();
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);// getSessionIntValue(ElConstants.SESSION_USERID)
		if (sub_operate == 0) {
			// 并入上级试题库
			// questionDao.deleteQLBasUnit(questionLib.getId(),
			// getSessionIntValue(ElConstants.SESSION_USERID));

			QuestionLib qlb = questionDao.getQLbById(id);
//			qlb.setChild(questionDao.listChild(id, userid));
//			for (int i = 0; i < qlb.getChild().size(); i++) {
//				QuestionLib qlbi = qlb.getChild().get(i);
//				questionDao.setQLBparent(qlbi.getId(), qlb.getParent().getId(),userid);
//			}
			questionDao.setQLBparent2(qlb.getId(), qlb.getParent().getId());
			questionDao.setQLparent(qlb.getId(), qlb.getParent().getId());
//			questionDao.deleteQLB(id, userid);
			questionDao.deleteQLibNot(id);
		} else {
			// 与本试题库同时删除
			// questionDao.deleteQLBasSub(questionLib.getId(),
			// getSessionIntValue(ElConstants.SESSION_USERID));

//			List<QuestionLib> child = questionDao.listChild(id, userid);
//			deleteChild(userid, child);
//			questionDao.deleteQLB(id, userid);
			//questionDao.deleteQlibAndSub(questionLib.getId());
			questionDao.deleteQlibAndSubNot(questionLib.getId());
		}
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("QUESTION_LIB");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_QUESTIONLIB,
				ElLoggerConstants.LOG_TYPE_DELETE,questionLib.getName(),
				ElLoggerConstants.LOG_RES_SUCC,questionLib.getId());
		return "question_lib_delete_success";
	}

	private void deleteChild(int userid, List<QuestionLib> child)
			throws ElException {
		for (int i = 0; i < child.size(); i++) {
			questionDao.deleteQLB(child.get(i).getId(), userid);
			deleteChild(userid, questionDao.listChild(child.get(i).getId(),
					userid));
		}
	}
	
	public String question_add_type() throws ElException {
		return "question_add_type";
	}

	// ==================课程管理================================
	public String question_addInit() throws ElException {
		// qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// ElConstants.TREE_FIANL, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (question == null) {
			question = new Question();
			question.setQtype(1);
		}
		if (question.getQlib() == null) {
			question.setQlib(new QuestionLib(1));
		} else if (question.getQlib().getId() == 0) {
			question.getQlib().setId(1);
		}

		if (qlbTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的题库");
			return "error";
		}
		// 初始化
		int qtype = question.getQtype();
		int qlibid = question.getQlib().getId();
		question = new Question();
		question.setQtype(qtype);
		question.setQlib(new QuestionLib(qlibid));
		if (question.getQtype() == 7) {
			return "question_view_cailiao_add";
		}
		return "question_add";
	}

	/**
	 * Description: 试题添加
	* @Version1.0 2012-7-15 上午11:48:17 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String question_add() throws ElException {
		//源试题id
		int qid = question.getId();
		if (question.getAnswer() != null) {
			int indexOf = question.getAnswer().indexOf(",");
			int length = question.getAnswer().trim().length();
			//
			if (indexOf != -1 && indexOf == length - 1) {
				// 去掉最后1个逗号
				question.setAnswer(question.getAnswer()
						.substring(0, length - 1));
			}
		}
		question.setEluser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		if (null != question.getParent() && 0 != question.getParent().getId())
			question.setSortid(1 + questionDao.getMaxQsort(question.getParent()
					.getId()));
		// 标题默认和内容一样
		// question.setTitle(question.getContent());
		if(question.getQtype()==8){//打字题
			question.setTitle(StringUtil.qshortTitle(question.getContent(),50));
			question.setFwsize(question.getContent().length());
		}
		if(question.getQtype()==9){
			question.setContent(question.getMailContent());
			question.setTitle(question.getMailTitle());
		}
		if(question.getQtype()==10){
			question.setContent(question.getAnswers()[0]);
			question.setTitle(question.getContent());
		}
		
		//当试题类型为阅读类型并且等级为2 拼音模式则自动转换
		if(question.getQtype() == 115 && question.getQlevel() == 2){
			String str2= question.getTitle().replaceAll("\\s*", "").trim();
			String arr [] = str2.split("。");
			StringBuffer sb = new StringBuffer();
			
			for (String string : arr) {
				String res = SimulationUtil.pinyinHtml(string.trim());
				sb.append(res+"。");
				sb.append("&nbsp;&nbsp;");
			}
			question.setTitle(sb.toString());
		}
		
//		//判断标题长度
//		if(question.getTitle().length()>2000){
//			setElmessage("题干的长度不能超过2000");
//			return "error";
//		}
		//添加前先检测是否有重复题了
		if(question.getQtype()!=7&&questionDao.checkQuestionIsRepeat(question)){
			setElmessage("您添加的试题已经存在，请重新添加。");
			return "error";
		}
		questionDao.addQuestion(question);
		//判断是否材料题
		if(qid >0 && question.getQtype() == 7){
			//通过材料题id获取材料题小题
			List<Question> list = questionDao.getQChildbyPid(qid);
			for(int i = 0; i < list.size(); i++){
				Question q  = list.get(i);
				q.setParent(question);
				//复制材料题的小题
				questionDao.addQuestion(q);
			}
		}
		questionDao.setQuestionStatus(question.getId(), 2);
		if (isCaiLiao == 1) {
			return "question_alterInit";
		}
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_QUESTION,
				ElLoggerConstants.LOG_TYPE_ADD,ElLogger.shortString(question.getTitle()),
				ElLoggerConstants.LOG_RES_SUCC,question.getId());	
		// return "question_add_success";
		return "question_list";
	}

	/**
	 * Description:试题修改action 
	* @Version1.0 2012-7-8 下午04:08:54 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String question_alterInit() throws ElException {
		question = questionDao.getQbyId(question.getId());
		if(question!=null&&question.getQlib()!=null){
			questionLib = questionDao.getQLbById(question.getQlib().getId());
		}
		// qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// ElConstants.TREE_FIANL, true);
	
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		String isC = getRequest().getParameter("isCaiLiao");
		if ("1".equals(isC)) {
			if (question != null && question.getQtype() == 7) {
				question
						.setChilds(questionDao.getQChildbyPid(question.getId()));
			}
			if("1".equals(copy)){
				question.setContent(question.getContent() + "_副本");
			}
			return "question_view_cailiao_alter";
		}
		if("1".equals(copy)){
			question.setContent(question.getContent() + "_副本");
		}
		return "question_alter";
	}

	/**
	 * Description:试题修改 
	* @Version1.0 2012-7-15 上午11:47:39 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String question_alter() throws ElException {
		if (question.getAnswer() != null) {
			int indexOf = question.getAnswer().indexOf(",");
			int length = question.getAnswer().trim().length();
			//
			if (indexOf != -1 && indexOf == length - 1) {
				// 去掉最后1个逗号
				question.setAnswer(question.getAnswer()
						.substring(0, length - 1));
			}
		}
		// 默认标题和内容一样
		if(question.getQtype()==8){//打字题
			question.setTitle(StringUtil.qshortTitle(question.getContent(),50));
			question.setFwsize(question.getContent().length());
		}
		if(question.getQtype()==9){
			question.setContent(question.getMailContent());
			question.setTitle(question.getMailTitle());
		}
		if(question.getQtype()==10){
			question.setContent(question.getAnswers()[0]);
			question.setTitle(question.getContent());
		}
		//判断标题长度
		if(question.getTitle().length()>2000){
			setElmessage("题干的长度不能超过2000");
			return "error";
		}
		//编辑前先检测是否有重复题了
		if(question.getQtype()!=7&&questionDao.checkQuestionIsRepeat(question)){
			setElmessage("您添加的试题已经存在，请重新添加。");
			return "error";
		}
		questionDao.alterQuestion(question);
		question=questionDao.getQbyId(question.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_QUESTION,
				ElLoggerConstants.LOG_TYPE_ALTER,ElLogger.shortString(question.getTitle()),
				ElLoggerConstants.LOG_RES_SUCC,question.getId());
		// return "question_alter_success";
		return "question_list";
	}

	public String question_listInit() throws ElException {
		// qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// ElConstants.TREE_FIANL, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (qlbTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的题库");
			return "error";
		}
		return "question_listInit";
	}

	/*
	 * public String question_list() throws ElException { // qlbTree =
	 * questionDao.getQlibTree(ElConstants.TREE_ROOT, //
	 * getSessionIntValue(ElConstants.SESSION_USERID), //
	 * ElConstants.TREE_FIANL, true); if
	 * (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) qlbTree =
	 * questionDao.getQlibTree(ElConstants.TREE_ROOT,
	 * getSessionIntValue(ElConstants.SESSION_USERID), ElConstants.TREE_FIANL,
	 * true); else { qlbTree = questionDao.getQlibTree(
	 * getSessionIntValue(ElConstants.SESSION_USERID), "op", -1, true); } String
	 * title = question == null ? "" : question.getTitle(); if
	 * (question.getQlib() == null) { setElmessage("请选择题库！"); return "error"; }
	 * int qlib = question == null ? questionDao.getQLbRoot().getId() :
	 * question.getQlib().getId(); int qtype = question == null ? 0 :
	 * question.getQtype(); // getPageSize() = getPageSize() == 0 ? 10 :
	 * getPageSize(); if (sublibs == 1) { questions =
	 * questionDao.listMyQuestions(title, qlib, qtype, true, getPageNow(),
	 * getPageSize()); count = questionDao.listMyQuestionsSize(title, qlib,
	 * qtype, true); } else { questions = questionDao.listMyQuestions(title,
	 * qlib, qtype, false, getPageNow(), getPageSize()); count =
	 * questionDao.listMyQuestionsSize(title, qlib, qtype, false); } return
	 * "question_list"; }
	 */
	// public String question_list() throws ElException {
	// // qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
	// // getSessionIntValue(ElConstants.SESSION_USERID),
	// // ElConstants.TREE_FIANL, true);
	// // getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
	// String title = question == null ? "" : question.getTitle();
	// if (question.getQlib() == null) {
	// setElmessage("请选择题库！");
	// return "error";
	// }
	// int qlib = question == null ? questionDao.getQLbRoot().getId()
	// : question.getQlib().getId();
	// int qtype = question == null ? 0 : question.getQtype();
	// getSession().setAttribute("Exclquestion", question);
	// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
	// qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
	// getSessionIntValue(ElConstants.SESSION_USERID),
	// ElConstants.TREE_FIANL, true);
	// if (sublibs == 1) {
	// questions = questionDao.listMyQuestions(title, qlib, qtype,
	// true, getPageNow(), getPageSize());
	// count = questionDao.listMyQuestionsSize(title, qlib, qtype,
	// true);
	// } else {
	// questions = questionDao.listMyQuestions(title, qlib, qtype,
	// false, getPageNow(), getPageSize());
	// count = questionDao.listMyQuestionsSize(title, qlib, qtype,
	// false);
	// }
	// } else {
	// qlbTree = questionDao.getQlibTree(
	// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
	// true);
	// questions = questionDao.question_list_listMyQuestions(qlbTree,
	// title, qlib, qtype, false, getPageNow(), getPageSize());
	// count = questionDao.question_list_listMyQuestionsSize(qlbTree,
	// title, qlib, qtype, false);
	// }
	// return "question_list";
	// } 

//	public String question_list() throws ElException {
//		if (questions != null && SelectExprot) {// 选择导出
//			HttpServletRequest requset = ServletActionContext.getRequest();
//			HttpSession session = requset.getSession();
//			List ques = (List) session.getAttribute("SelectImpQues");
//			ques = ques != null && ques.size() != 0 ? ques : new ArrayList<List>();
//			for(int i = 0 ; i < questions.size();i++){
//				if(!ques.contains(questions.get(i).getId())){
//					ques.add(questions.get(i).getId());					
//				}
//			} 
//			session.setAttribute("SelectImpQues", ques);
//		}
//
//		String title = question == null ? "" : question.getTitle();
//		int qlib = question == null ? questionDao.getQLbRoot().getId()
//				: question.getQlib().getId();
//		setQlbid(qlib);// 用于导出
//		if (exprot) { // 导出  
//			if (qlib == 1 || qlib == 0) { 
//				setElmessage("不能以根目录做导出！");
//				return "error";
//			}
//		}
//		int qtype = question == null ? 0 : question.getQtype();
//		getSession().setAttribute("Exclquestion", question);
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
//			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
//					getSessionIntValue(ElConstants.SESSION_USERID),
//					ElConstants.TREE_FIANL, true);
//			if (sublibs == 1) {
//				if (exprot) { // 导出  
//					questionlist = questionDao.listMyQuestions(title, qlib,
//							qtype, true);
//					// questionlist = questionDao.getQuestionList(question);
//					return "question_exportExcel";
//				}
//				questions = questionDao.listMyQuestions(title, qlib, qtype,
//						true, getPageNow(), getPageSize());
//				count = questionDao.listMyQuestionsSize(title, qlib, qtype,
//						true);
//			} else {
//				if (exprot) { // 导出
//					questionlist = questionDao.listMyQuestions(title, qlib,
//							qtype, false);
//					return "question_exportExcel";
//				}
//				questions = questionDao.listMyQuestions(title, qlib, qtype,
//						false, getPageNow(), getPageSize());
//				count = questionDao.listMyQuestionsSize(title, qlib, qtype,
//						false);
//			}
//			questions = questionDao.listMyQuestions(title, qlib, qtype, true,
//					getPageNow(), getPageSize());
//			count = questionDao.listMyQuestionsSize(title, qlib, qtype, true);
//		} else {
//			qlbTree = questionDao.getQlibTree(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//			if (exprot) { // 导出
//				questionlist = questionDao.question_list_listMyQuestions(
//						qlbTree, title, qlib, qtype, false);
//				return "question_exportExcel";
//			}
//			questions = questionDao.question_list_listMyQuestions(qlbTree,
//					title, qlib, qtype, false, getPageNow(), getPageSize());
//			count = questionDao.question_list_listMyQuestionsSize(qlbTree,
//					title, qlib, qtype, false);
//		}
//		return "question_list";
//	}
//原来的
//	public String question_list() throws ElException {
//		int qlib = 1; 
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
//			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,getSessionIntValue(ElConstants.SESSION_USERID),ElConstants.TREE_FIANL, true);
//		} else {
//			qlbTree = questionDao.getQlibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true); 
//			if (qlbTree == null || qlbTree.getChild().size() == 0) { 
//				setElmessage("没有可操作的功能权限！");
//				return "error";
//			}
//			qlib = qlbTree.getChild().get(0).getId();
//		} 
//		if (questions != null && SelectExprot) {// 导出箱
//			HttpServletRequest requset = ServletActionContext.getRequest();
//			HttpSession session = requset.getSession();
//			List ques = (List) session.getAttribute("SelectImpQues");
//			ques = ques != null && ques.size() != 0 ? ques : new ArrayList<List>();
//			for(int i = 0 ; i < questions.size();i++){
//				if(!ques.contains(questions.get(i).getId())){
//					ques.add(questions.get(i).getId());					
//				}
//			} 
//			session.setAttribute("SelectImpQues", ques);
//		} 
//		if (sublibs != 0) {
//			qlib = question == null ? questionDao.getQLbRoot().getId()
//					: question.getQlib().getId();
//		}
//		
//		setQlbid(qlib);// 用于导出
//		if (exprot) { // 导出  
//			if (qlib == 1 || qlib == 0) { 
//				setElmessage("不能以根目录做导出！");
//				return "error";
//			}
//		} 
//		getSession().setAttribute("Exclquestion", question);
//
//		if (exprot && questions == null) { // 导出  //questions == null导出箱为空。
//			questionlist = questionDao.listMyQuestions(question, qlib,true);   
//			return "question_exportExcel";
//		}
//		if(elUser != null && elUser.getRealname() != null && !elUser.getRealname().equals("")){
//			question.setEluser(elUser);
//		}
//		questions = questionDao.listMyQuestions(question, qlib,true, getPageNow(), getPageSize());
//		count = questionDao.listMyQuestionsSize(question, qlib,true); 
//		return "question_list";
//	}
	
	/**试题列表
	 * @return
	 * @throws ElException
	 */
	public String question_list() throws ElException {
//		int qlib = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		} else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
//			if (qlbTree == null || qlbTree.getChild().size() == 0) {
//				setElmessage("没有可操作的试题库节点！");
//				return "error";
//			}
//			qlib = qlbTree.getChild().get(0).getId();
		}
		if (questions != null && SelectExprot) {// 导出箱
			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession();
			List ques = (List) session.getAttribute("SelectImpQues");
			ques = ques != null && ques.size() != 0 ? ques
					: new ArrayList<List>();
			for (int i = 0; i < questions.size(); i++) {
				if (!ques.contains(questions.get(i).getId())) {
					ques.add(questions.get(i).getId());
				}
			}
			session.setAttribute("SelectImpQues", ques);
		}

//		setQlbid(qlib);// 用于导出
//		if (exprot) { // 导出
//			if (question == null || question.getQlib() == null
//					|| question.getQlib().getId() <= 1) {
//				setElmessage("不能以根目录做导出！");
//				return "error";
//			}
//		}
//		getSession().setAttribute("Exclquestion", question);
		
		if (elUser != null && elUser.getRealname() != null
				&& !elUser.getRealname().equals("")) {
			question.setEluser(elUser);
		}
		QuestionLib q = null;
		if (question == null || question.getQlib() == null
				|| question.getQlib().getId() <= 0) {
			q = qlbTree;
			//sublibs = 1;
		} else {
			if(getSessionIntValue(ElConstants.SESSION_ROLE)!= 1&&!((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).checkNode(question.getQlib().getId(), qlbTree, "question_lib")){
				setElmessage("您输入了无权操作的节点！");
				return "error";
			}
			q = questionDao.getQLbById(question.getQlib().getId());
		}
		if(question == null){
			question = new Question();
			question.setStatus(-1);
			sublibs=1;
		}
		if(q!=null){
			int qlibid = 0;
			if(q.getId()>0)
				qlibid = q.getId();
			else{
				List<QuestionLib> qlibs_= q.getChild();
				if(qlibs_!=null){
					for (int i = 0; i < qlibs_.size(); i++) {
						if(qlibs_.get(i).getId()>0)
						{
							qlibid = qlibs_.get(i).getId();
							break;
						}
					}
				}
			}
			question.setQlib(new QuestionLib(qlibid));
		}
//		question.setQlib(q);
		//导出试题。
		if (exprot && questionlist == null) { // 导出 //questions == null导出箱为空。
			if (q.getId() <= 1) {
				setElmessage("不能以根目录做导出！");
				return "error";
			}
			try {
				questionlist = questionDao.listMyQuestions(q, sublibs,
						question );
				getResponse().reset(); 		
				getResponse().setHeader("Content-disposition","attachment; filename=question_exportExcel.xls"); 
				getResponse().setContentType("application/vnd.ms-excel");
				OutputStream os = getResponse().getOutputStream();
				//导出试题的处理类
				ExportWord.writeExcel(os,questionlist,q.getId());  
			 	os.close();
			} catch (Exception e) {
				logger.error("导出试题失败",e);
				setElmessage("试题导出失败");
				return "error";
			}
			
			return null;
		}
		questions = questionDao.listMyQuestions(q, sublibs,
				question, getPageNow(), getPageSize());
		count = questionDao.listMyQuestionsSize(q, sublibs, question);
		return "question_list";
	}

	public String question_select_impExecelInit() throws ElException{ 
			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession(); 
			List ques = (List) session.getAttribute("SelectImpQues"); 
			if(ques != null && ques.size()!= 0){
				String ids = "";
				for(int i = 0 ; i < ques.size() ;i++){
					if(ids.equals(""))
						ids = ques.get(i)+"";
					else
						ids = ids +","+ques.get(i);
				}
				if(exprot){
					int qlib = 1;
					setQlbid(qlib);// 用于导出
					questionlist = questionDao.getselectQbyIds(ids);
					return "question_exportExcel";
				}else{					
					questions = questionDao.getselectQbyIds(ids, getPageNow(), getPageSize());
					count = questionDao.getselectQbyIdsSize(ids);
				}
			} 
		return "question_select_impExecel";
	}
	public String question_delete_impExecel() throws ElException{  
		if(questions == null ){ 
			return "question_select_impExecel";
		}
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		List ques = (List) session.getAttribute("SelectImpQues");   
		ques = ques != null && ques.size() != 0 ? ques : new ArrayList<Question>();

		if(Leeren){//清空
			session.removeValue("SelectImpQues");
			return "question_select_impExecel";
		}
		for(int i = 0 ; i < questions.size() ;i++){ 
			if(ques.contains(questions.get(i).getId())){ 			
				ques.remove((Object)questions.get(i).getId()); 
			} 
		}    
		session.removeValue("SelectImpQues");
		session.setAttribute("SelectImpQues", ques); 
	return "question_select_impExecel";
	}
	public String question_listInC() throws ElException {
		String title = question == null ? "" : question.getTitle();
//		int qlib = question == null ? questionDao.getQLbRoot().getId()
//				: question.getQlib().getId();
//		setQlbid(qlib);// 用于导出
//		if (qlib == 0) {
//			qlib = 1;
//		}
		int qtype = question == null ? 0 : question.getQtype();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
//			questions = questionDao.listMyQuestions2(title, qlib, qtype, true,
//					getPageNow(), getPageSize());
//			count = questionDao.listMyQuestionsSize2(title, qlib, qtype, true);
		} else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
//			questions = questionDao.question_list_listMyQuestions2(qlbTree,
//					title, qlib, qtype, false, getPageNow(), getPageSize());
//			count = questionDao.question_list_listMyQuestionsSize2(qlbTree,
//					title, qlib, qtype, false);
		}
		//判断是否有节点传入，如果没有节点传入 就将此人可操作的树传入
		if(question==null||question.getQlib()==null||question.getQlib().getId()<=0)
			questionLib = qlbTree;
		else//有节点传入是查询此节点信息作为条件传入
			questionLib = questionDao.getQLbById(question.getQlib().getId()	);
		questions = questionDao.question_list_listMyQuestions2(questionLib,
				title, 0, qtype, true, getPageNow(), getPageSize());
		count = questionDao.question_list_listMyQuestionsSize2(questionLib,
				title, 0, qtype, true);
		return "question_cailiao_list";
	}

	public String addQuestionToC() throws ElException {
		String[] questions = chks.split(",");
		for (int i = 0; i < questions.length; i++) {
			question = questionDao.getQbyId(Integer.parseInt(questions[i]));
			question.setEluser(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			question.setSortid(1 + questionDao.getMaxQsort(questionParid));
			question.setParent(new Question(questionParid));
			questionDao.addQuestion(question);
		}
		return "question_alterInit";
	}
	public String question_alter_scorepre() throws ElException {
		questionDao.alterQuestionScorepre(question);
		return null;
	}

	public String question_delete() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (null != questions)
			// questionDao.deleteQuestions(questions);
			for (int i = 0; i < questions.size(); i++) {
				questionDao.deleteQuestion(questions.get(i).getId());
				Question tempquestion=questionDao.getQbyId(questions.get(i).getId());
				if(tempquestion!=null){
					ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_QUESTION,
							ElLoggerConstants.LOG_TYPE_DELETE,tempquestion.getTitle(),
							ElLoggerConstants.LOG_RES_SUCC,tempquestion.getId());
				}
			}
		if (sublibs == 1) {
			questions = questionDao.listMyQuestions(question.getTitle(),
					question.getQlib().getId(), question.getQtype(), true,
					getPageNow(), getPageSize());
			count = questionDao.listMyQuestionsSize(question.getTitle(),
					question.getQlib().getId(), question.getQtype(), true);
		} else {
			questions = questionDao.listMyQuestions(question.getTitle(),
					question.getQlib().getId(), question.getQtype(), false,
					getPageNow(), getPageSize());
			count = questionDao.listMyQuestionsSize(question.getTitle(),
					question.getQlib().getId(), question.getQtype(), false);
		}
		return "question_delete";
	}
	
	/**
	 * Description: 试题创建完成
	* @Version1.0 2012-7-15 上午11:47:25 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String question_update_status() throws ElException {
		//查询材料大的小题累计的百分比
		StringBuffer erro = new StringBuffer();
		if (null != questions)
			for (int i = 0; i < questions.size(); i++) {
				Question q = questionDao.getQuestionByid(questions.get(i).getId());
				if (q.getStatus() == 2) {
					if(q.getQtype() == 7){
						int sum  = questionDao.minorproblem_Sum_scroe(q.getId());
						if(sum != 100){
							erro.append("<b>"+q.getTitle()+"</b>材料题的小题的百分比不正确<br />");
							continue;
						}
					}
					questionDao.setQuestionStatus(q.getId(), 0);// 0,已创建
					Question tempquestion = questionDao.getQbyId(q.getId());
					if (tempquestion != null) {
						ElLogger.busilogger(
								getSessionIntValue(ElConstants.SESSION_USERID),
								ElLoggerConstants.LOG_MOD_QUESTION,
								ElLoggerConstants.LOG_TYPE_DELETE,ElLogger.shortString( tempquestion
										.getTitle()),
								ElLoggerConstants.LOG_RES_SUCC, tempquestion
										.getId());
					}
				}
			}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		} else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			if (qlbTree == null || qlbTree.getChild().size() == 0) {
				setElmessage("没有可操作的功能权限！");
				return "error";
			}
		}
		QuestionLib q = null;
		if (question == null || question.getQlib() == null
				|| question.getQlib().getId() <= 0) {
			q = qlbTree;
			sublibs = 1;
		} else {
			q = questionDao.getQLbById(question.getQlib().getId());
		}

		questions = questionDao.listMyQuestions(q, sublibs,
				question, getPageNow(), getPageSize());
		count = questionDao.listMyQuestionsSize(q, sublibs, question);
		if(!"".equals(erro.toString())){
			erro.append("上述材料题小题设置有问题，请重新设定！");
			setElmessage("<div style='font-size:13px;font-weight:normal'>"+erro.toString()+"</div>");
			return "error";
		}
		return "question_update";
	}
	
	/**
	 * Description:试题作废 
	* @Version1.0 2012-7-15 上午11:47:11 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String question_delete_status() throws ElException {
		if (null != questions)
			for (int i = 0; i < questions.size(); i++) {
				questionDao.setQuestionStatus(questions.get(i).getId(), 1);// 1,已删除
				Question tempquestion=questionDao.getQbyId(questions.get(i).getId());
				if(tempquestion!=null){
//					ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//							ElLoggerConstants.LOG_MOD_QUESTION,
//							ElLoggerConstants.LOG_TYPE_DELETE,tempquestion.getTitle(),
//							ElLoggerConstants.LOG_RES_SUCC,tempquestion.getId());
				}
			}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true);
		} else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			if (qlbTree == null || qlbTree.getChild().size() == 0) {
				setElmessage("没有可操作的功能权限！");
				return "error";
			}
		}
		QuestionLib q = null;
		if (question == null || question.getQlib() == null
				|| question.getQlib().getId() <= 0) {
			q = qlbTree;
			sublibs = 1;
		} else {
			q = questionDao.getQLbById(question.getQlib().getId());
		}

		questions = questionDao.listMyQuestions(q, sublibs,
				question, getPageNow(), getPageSize());
		count = questionDao.listMyQuestionsSize(q, sublibs, question);
		return "question_delete";
	}

	/**
	 * Description:试题查看 
	* @Version1.0 2012-7-15 上午11:46:45 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String question_view() throws ElException {
		question = questionDao.getQbyId(question.getId());
		if (question != null && question.getQtype() == 7) {
			question.setChilds(questionDao.getQChildbyPid(question.getId()));
		}
		return "question_view";
	}

	public String question_view_status() throws ElException {
		question = questionDao.getQbyId(question.getId());
		if (question != null && question.getQtype() == 7) {
			question.setChilds(questionDao.getQChildbyPid(question.getId()));
		}
		return "question_view_status";
	}

	public String questionchild_add_type() throws ElException {
		return "questionchild_add_type";
	}

	public String questionchild_addInit() throws ElException {
		question.setParent(questionDao.getQbyId(question.getParent().getId()));
		return "questionchild_add";
	}

	public String questionchild_add() throws ElException {
		question.setEluser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		question.setSortid(1 + questionDao.getMaxQsort(question.getParent()
				.getId()));
		questionDao.addQuestion(question);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_QUESTION,
				ElLoggerConstants.LOG_TYPE_ADD,question.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,question.getId());
		return "questionchild_add_success";
	}

	public String questionchild_alterInit() throws ElException {
		question = questionDao.getQbyId(question.getId());
		question.setParent(questionDao.getQbyId(question.getParent().getId()));
		return "questionchild_alter";
	}

	public String questionchild_alter() throws ElException {
		// 标题和内容一样
//		question.setTitle(question.getTitle_cn(question.getContent()));
		questionDao.alterQuestion(question);
		question=questionDao.getQbyId(question.getId());
		if(question!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_QUESTION,
					ElLoggerConstants.LOG_TYPE_ALTER,question.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC,question.getId());
		}
		// return "questionchild_alter_success";
		if (isCaiLiao == 1) {
			return "question_alterInit";
		}
		return "question_list";
	}

	public String questionchild_view() throws ElException {
		question = questionDao.getQbyId(question.getId());
		question.setParent(questionDao.getQbyId(question.getParent().getId()));
		return "questionchild_view";
	}

	public String questionchild_delete() throws ElException {
		// question = questionDao.getQbyId(question.getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
//		questionDao.questionChildDelete(question.getId());
//		question=questionDao.getQbyId(question.getId());
		if(question!=null){
			questionDao.questionChildDelete(question.getId());
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_QUESTION,
					ElLoggerConstants.LOG_TYPE_DELETE,question.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC,question.getId());
		}
		// return "question_view";
		if (isCaiLiao == 1) {
			return "question_alterInit";
		}
		return "question_view";
	}

	public String questionchild_upSort() throws ElException {
		// question = questionDao.getQbyId(question.getId(),
		// getSessionIntValue(ElConstants.SESSION_USERID));
		questionDao.questionChildSort(question.getParent().getId(), question
				.getSortid(), ElConstants.SORT_UP);

		return "question_view";
	}

	public String questionchild_downSort() throws ElException {
		questionDao.questionChildSort(question.getParent().getId(), question
				.getSortid(), ElConstants.SORT_DOWN);
		return "question_view";
	}

//	public SystemConf getSysconf() {
//		return sysconf;
//	}
//
//	public void setSysconf(SystemConf sysconf) {
//		this.sysconf = sysconf;
//	}

//	public String qstuff_delete_user() throws ElException {
//		questionDao.deleteStuffOpusers(elUser.getId(), qpstuff.getId());
//		return null;
//	}

//	public String question_stuff_mylist() throws ElException {
//		// if (0 == getPageSize())
//		// getPageSize() = 10;
//		qstuffs = questionDao.getStuffs(qstuff,
//				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
//				getPageSize());
//		count = questionDao.getStuffsCount(qstuff,
//				getSessionIntValue(ElConstants.SESSION_USERID));
//		return "question_stuff_mylist";
//	}
	
//	public InputStream getInputStream() {
//		return ServletActionContext.getServletContext().getResourceAsStream("/" + downFileName);
//	}
	
//	public InputStream getInputStream() {
//		return inputStream;
//	}
//
//	public void setInputStream(InputStream inputStream) {
//		this.inputStream = inputStream;
//	}

	/**
	 * 文件下载
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
//	public String question_stuffDownload() throws ElException, Exception {
//		try {
//			//qstuff = questionDao.getStuffbyId(qstuff.getId(), 0);//第2个参数为文件上传者（0代表不限）
//			qstuff = questionDao.getStuffbyId2(qstuff.getId(), 0);
//			this.downFileName="/elstuffs/" + qstuff.getPath() + "." + qstuff.getFileext();
//			String path = ServletActionContext.getServletContext().getRealPath(downFileName);
//			//downFileName = qstuff.getId()+"."+qstuff.getFileext();
//			downFileName = qstuff.getTitle()+"."+qstuff.getFileext();
//			downFileName = new String(downFileName.getBytes(), "ISO8859-1");
//			try {
//				inputStream= new FileInputStream(path);
//			} catch (Exception e) {
//				//logger.error("文档下载失败", e);
//				throw new ElException("下载素材出错", e);
//			}
//		} catch (Exception e) {
//			//logger.error("文档下载失败", e);
//			setElmessage("文件不存在或其他原因导致文件不能下载！");
//			return "error";
//		}
//		return "fileDownload";
//	}
	
	public String execute(){
		return "success";
	}

//	public String question_stuffdelete() throws ElException, Exception {
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
//			String isDel=getRequest().getParameter("isDel");//判断是否真删除
//			if (null != qstuffs) {
//				for (int i = 0; i < qstuffs.size(); i++) {
//					int id = qstuffs.get(i).getId();
//					// int userid =
//					// getSessionIntValue(ElConstants.SESSION_USERID);
//					if(null!=isDel&&"1".equals(isDel)){
//						deleteF2(id);
//					}else{
//						deleteF(id);
//					}
//					
//				}
//			}
//		} else {
//			setElmessage("您无权限删除系统资源！");
//			return "error";
//		}
//		// qstuffs = questionDao.getStuffs(qstuff,
//		// getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
//		// getPageSize());
//		return "question_stuffList";
//	}

//	public String question_stuffList() throws ElException {
//		sysconf = new SystemConf();
//		sysconf.setStuff_size(SystemConfOp.getIntValue(ElConstants.STUFF_SIZE));
//		if (!SystemConfOp.getBooleanValue(ElConstants.STUFF_OP)&&!"无记录".equals(SystemConfOp.getValue(ElConstants.STUFF_URL))
//				&& !"".equals(SystemConfOp.getValue(ElConstants.STUFF_URL)
//						.trim())) {
//			if(!questionDao.checkUrlIsLocal(SystemConfOp.getValue(ElConstants.STUFF_URL), getRequest().getContextPath(),getRequest().getServerName())){
//				ELUser u = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
//				stfilename = SystemConfOp.getValue(ElConstants.STUFF_URL)
//						+ "/question_stuffList.action?username="+u.getUsername()+
//						"&password="+u.getPassword()+"&stuffcode="+getSession().getId();
////						+ getSessionIntValue(ElConstants.SESSION_USERID);
//				return "to_stuff_url";
//			}
//		}
//		qpstuff = qpstuff == null || qpstuff.getId() == 0 ? new StuffLib(0, "根")
//				: questionDao.getStuffbyId(qpstuff.getId(), 0);
//		sublibs = SystemConfOp.getIntValue(ElConstants.STUFF_SIZE);
//		if (qpstuff != null && qpstuff.getType() == 7) {
//			questionDao.setStuffParent(qpstuff);
//			qstuffs = ZipUtil.listStuffs(ServletActionContext
//					.getServletContext().getRealPath("/elstuffs/")
//					+ qpstuff.getPath() + "/");
//			// stuffTree = questionDao
//			// .getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
//			// stuffSharedTree = questionDao.listFolderShared();
//			if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
//				stuffTree = questionDao
//						.getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
//				stuffSharedTree = questionDao.listFolderShared();
//			} else {
//				stuffTree = questionDao.getStuffFolderTree();
//				stuffSharedTree = questionDao.listFolderShared();
//			}
//			return "question_stuffList";
//		} else {
//			// if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
//			// qstuffs =
//			// questionDao.listMyStuffs(qpstuff,getSessionIntValue(ElConstants.SESSION_USERID));
//			// } else
//			// qpstuff = qpstuff == null || qpstuff.getId() == 0 ? new
//			// StuffLib(0,
//			// "根") : questionDao.getStuffbyId(qpstuff.getId(), 0);
//			// questionDao.setStuffParent(qpstuff);
//			qpstuff.setUsers(questionDao.getStuffOpUsers(qpstuff.getId()));
//			if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
//				stuffTree = questionDao
//						.getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
//				stuffSharedTree = questionDao.listFolderShared();
//				if (qpstuff != null && qpstuff.getId() != 0) {
//					qstuffs = questionDao.listStuffs(qpstuff, "", "");
//					qpstuff.setParent(new StuffLib(0, ""));
//					if (st_type == 1)
//						questionDao.setStuffParent(qpstuff, stuffSharedTree
//								.getChilds());
//					else
//						questionDao.setStuffParent(qpstuff, stuffTree
//								.getChilds());
//					stuffSharedTree = questionDao.listFolderShared();
//				} else
//					qstuffs = stuffTree.getChilds();
//			} else {
//				stuffTree = questionDao.getStuffFolderTree();
//				stuffSharedTree = questionDao.listFolderShared();
//				qstuffs = questionDao.listStuffs(qpstuff, "", "");
//				if (st_type == 1) {
//					qpstuff.setParent(new StuffLib(0, ""));
//					questionDao.setStuffParent(qpstuff, stuffSharedTree
//							.getChilds());
//				} else
//					questionDao.setStuffParent(qpstuff);
//
//			}
//			return "question_stuffList";
//		}
//	}

//	public String question_stuffuseradd() throws ElException, Exception {
//		if (null != qpstuff.getUsers()) {
//			for (int i = 0; i < qpstuff.getUsers().size(); i++) {
//				if (!questionDao.checkStuffOpUsers(qpstuff.getUsers().get(i)
//						.getId(), qpstuff.getId()))
//					questionDao.addStuffOpusers(qpstuff.getUsers().get(i)
//							.getId(), qpstuff.getId());
//			}
//		}
//		return "question_stuffuseradd_succ";
//	}

//	public String question_stuffshared() throws ElException, Exception {
//		questionDao.setStuffShared(qpstuff.getId(), qpstuff.getShared());
//		return "question_stuffshared_succ";
//	}

//	public String question_stuffsizeset() throws ElException {
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
//			setElmessage("无权限设置文件限制！");
//			return "error";
//		}
//		SystemConfOp.setProperty(ElConstants.STUFF_SIZE, sysconf
//				.getStuff_size());
//		try {
//			SystemConfOp.load();
//		} catch (Exception e) {
//			setElmessage("系统设置失败");
//			return "error";
//		}
//
//		return "question_stuffsizeset_succ";
//	}

//	public String question_stuffwjjsizeset() throws ElException, Exception {
//		// if(0!=questionDao.getStuffOpStatus(qpstuff.getId(),
//		// getSessionIntValue(ElConstants.SESSION_USERID),
//		// getSessionIntValue(ElConstants.SESSION_ROLE),2)){
//		// setElmessage("您无权限设置该文件夹！");
//		// return "error";
//		// }
//		// 需求已改为 只有超级管理员可以修改文件夹大小
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
//			setElmessage("您无权限设置该文件夹！");
//			return "error";
//		}
//		qpstuff.setLength(qpstuff.getLength() * 1024 * 1024L);
//		long psize = questionDao.getStuffParentSize(qpstuff.getId());
//		long mysize = qpstuff.getLength();
//		qstuff = questionDao.getStuffbyId(qpstuff.getId(), 0);
//		long chsize = questionDao
//				.getStuffChildsSize(qstuff.getParent().getId())
//				- qstuff.getLength();
//
//		if (chsize >= 0 && psize >= 0 && (chsize + mysize > psize)) {
//			setElmessage("该文件夹容量过了，请重新设置！");
//			return "error";
//		}
//		questionDao.setStuffsize(qpstuff.getId(), qpstuff.getLength());
//		return "question_stuffwjjsizeset_succ";
//	}
//
//	private void deleteF(int id) throws ElException, Exception {
//		StuffLib qst = questionDao.getStuffbyId(id, 0);
//		String title = qst.getTitle();
//		if (qst.getType() == 5) {
//			List<StuffLib> list = questionDao.listStuffs(qst, "", "");
//			if (list != null) {
//				for (int j = 0; j < list.size(); j++) {
//					deleteF(list.get(j).getId());
//				}
//			}
//			questionDao.setStuffParent(qst);//只是设置了父对象，在这里 不知道有什么用...
//			//J2EEFileUtil.deleteFolder("/elstuffs/" + qst.getPath() + "");
//			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//					ElLoggerConstants.LOG_MOD_STUFF,
//					ElLoggerConstants.LOG_TYPE_DELETE, "删除文件夹:" + title,
//					ElLoggerConstants.LOG_RES_SUCC);
//			questionDao.deleteQs(id, 0);
//			//--------questionDao.get
//		} else {
//			StuffLib qpst = qst.getParent();
//			questionDao.setStuffParent(qpst);
//			//J2EEFileUtil.deleteFile("/elstuffs/" + qpst.getPath(), id + "",qst.getFileext());
//			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//					ElLoggerConstants.LOG_MOD_STUFF,
//					ElLoggerConstants.LOG_TYPE_DELETE, "删除文件夹:" + title,
//					ElLoggerConstants.LOG_RES_SUCC);
//			questionDao.deleteQs(id, 0);
//		}
//	}
	/**
	 * 真删除
	 * @param id
	 * @throws ElException
	 * @throws Exception
	 */
//	private void deleteF2(int id) throws ElException, Exception {
//		StuffLib qst = questionDao.getStuffbyId(id, 0);
//		String title = qst.getTitle();
//		if (qst.getType() == 5) {
//			List<StuffLib> list = questionDao.listStuffs(qst, "", "");
//			if (list != null) {
//				for (int j = 0; j < list.size(); j++) {
//					deleteF2(list.get(j).getId());
//				}
//			}
//			questionDao.setStuffParent(qst);//只是设置了父对象，在这里 不知道有什么用...
//			J2EEFileUtil.deleteFolder("/elstuffs/" + qst.getPath() + "");
//
//			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//					ElLoggerConstants.LOG_MOD_STUFF,
//					ElLoggerConstants.LOG_TYPE_DELETE, "删除文件夹:" + title,
//					ElLoggerConstants.LOG_RES_SUCC);
//			questionDao.deleteQs(id, 0);
//			//--------questionDao.get
//		} else {
//			StuffLib qpst = qst.getParent();
//			questionDao.setStuffParent(qpst);
//			J2EEFileUtil.deleteFile("/elstuffs/" + qpst.getPath(), id + "",qst.getFileext());
//
//			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//					ElLoggerConstants.LOG_MOD_STUFF,
//					ElLoggerConstants.LOG_TYPE_DELETE, "删除文件夹:" + title,
//					ElLoggerConstants.LOG_RES_SUCC); 
//			questionDao.deleteQs(id, 0);
//		}
//	}

//	public String question_stuffaddInit() throws ElException {
//
//		return "question_stuffadd";
//	}
////
//	public String question_stuffadd() throws ElException, Exception {
//		sublibs = SystemConfOp.getIntValue(ElConstants.STUFF_SIZE);
//		if (null != st) {
//			if (qstuff == null || qstuff.getParent() == null
//					|| qstuff.getParent().getId() == 0) {
//				setElmessage("请不要在根目录下上传文件。");
//				qstuffs = questionDao.listStuffs(qpstuff, "", "");
//				qpstuff = qpstuff == null || qpstuff.getId() == 0 ? new StuffLib(
//						0, "根")
//						: questionDao.getStuffbyId(qpstuff.getId(), 0);
//				questionDao.setStuffParent(qpstuff);
//				stuffTree = questionDao
//						.getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
//				stuffSharedTree = questionDao.listFolderShared(); 
//				
//				ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//						ElLoggerConstants.LOG_MOD_STUFF,
//						ElLoggerConstants.LOG_TYPE_ADD,"请不要在根目录下上传文件。",
//						ElLoggerConstants.LOG_RES_ERR); 
//				return "question_stuffadd";
//			}
//			if (st.length() > SystemConfOp.getIntValue(ElConstants.STUFF_SIZE) * 1024 * 1024
//					&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
//				// qstuffs = questionDao.listStuffs(qpstuff, "", "");
//				// qpstuff = qpstuff == null || qpstuff.getId() == 0 ? new
//				// StuffLib(
//				// 0, "根")
//				// : questionDao.getStuffbyId(qpstuff.getId(), 0);
//				// questionDao.setStuffParent(qpstuff);
//				// stuffTree = questionDao
//				// .getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
//				// stuffSharedTree = questionDao.listFolderShared();
//				question_stuffList();
//				ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//						ElLoggerConstants.LOG_MOD_STUFF,
//						ElLoggerConstants.LOG_TYPE_ADD,"上传的文件过大！",
//						ElLoggerConstants.LOG_RES_ERR); 
//				setElmessage("您上传的文件过大！");
//				return "question_stuffadd";
//			} else {
//				long psize = questionDao.getStuffbyId(
//						qstuff.getParent().getId(), 0).getLength();
//				long mysize = st.length();
//				// qpstuff = questionDao.getStuffbyId(qpstuff.getId(), 0);
//				long chsize = questionDao.getStuffChildsSize(qstuff.getParent()
//						.getId());
//				if (chsize >= 0 && psize >= 0 && (chsize + mysize > psize)) {
//					qstuffs = questionDao.listStuffs(qpstuff, "", "");
//					qpstuff = qpstuff == null || qpstuff.getId() == 0 ? new StuffLib(
//							0, "根")
//							: questionDao.getStuffbyId(qpstuff.getId(), 0);
//					questionDao.setStuffParent(qpstuff);
//					stuffTree = questionDao
//							.getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
//					stuffSharedTree = questionDao.listFolderShared(); 
//					question_stuffList();
//					ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//							ElLoggerConstants.LOG_MOD_STUFF,
//							ElLoggerConstants.LOG_TYPE_ADD,"文件夹已满，请与系统管理员联系！",
//							ElLoggerConstants.LOG_RES_ERR); 
//					setElmessage("文件夹已满，请与系统管理员联系！");
//					return "question_stuffadd";
//				} else {
//					qstuff = qstuff != null ? qstuff : new StuffLib();
//					String ext = J2EEFileUtil.getExtention(stFileName);
//					if (null != stFileName)
//						qstuff.setTitle(stFileName.substring(0, stFileName
//								.lastIndexOf(".")));
//					else
//						qstuff.setTitle("未命名");
//					if (ext != null && ext.equals("zip")) {
//						qstuff.setType(6);
//					}
//
//					qstuff.setFileext(ext);
//					qstuff.setOwner(new ELUser(
//							getSessionIntValue(ElConstants.SESSION_USERID)));
//					qstuff.setLength(st.length());
//					int id = questionDao.addQstuff(qstuff);
//					qpstuff = qstuff.getParent();
//					questionDao.setStuffParent(qpstuff);
//					J2EEFileUtil.upload(st, ext, "/elstuffs/"
//							+ qpstuff.getPath(), id + "");
//				}
//			}
//		} else {
//			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//					ElLoggerConstants.LOG_MOD_STUFF,
//					ElLoggerConstants.LOG_TYPE_ADD,"未输入上传文件！",
//					ElLoggerConstants.LOG_RES_ERR); 
//			setElmessage("请输入上传文件");
//			stuffTree = questionDao
//					.getStuffFolderTree(getSessionIntValue(ElConstants.SESSION_USERID));
//			stuffSharedTree = questionDao.listFolderShared();
//			qstuffs = questionDao.listStuffs(qpstuff, "", "");
//			qpstuff = qpstuff == null || qpstuff.getId() == 0 ? new StuffLib(0,
//					"根") : questionDao.getStuffbyId(qpstuff.getId(), 0);
//			questionDao.setStuffParent(qpstuff);
//			return "question_stuffadd";
//
//		}
//		return "question_stuffadd_success";
//	}
//
//	public String question_stuffwjjadd() throws ElException, Exception {
//		if (1 == questionDao.getStuffOpStatus(qstuff.getId(),
//				getSessionIntValue(ElConstants.SESSION_USERID),
//				getSessionIntValue(ElConstants.SESSION_ROLE), 1)) {
//
//			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//					ElLoggerConstants.LOG_MOD_STUFF,
//					ElLoggerConstants.LOG_TYPE_ADD,"您无权在根目录下创建文件夹!",
//					ElLoggerConstants.LOG_RES_ERR); 
//			setElmessage("您无权在根目录下创建文件夹！");
//			return "error";
//		}
//		qstuff.setType(5);
//		qstuff.setLength(qstuff.getLength() * 1024 * 1024L);
//		long chsize = questionDao
//				.getStuffChildsSize(qstuff.getParent().getId());
//		long mysize = qstuff.getLength();
//		qstuff.setOwner(new ELUser(
//				getSessionIntValue(ElConstants.SESSION_USERID)));
//		questionDao.addQstuff(qstuff);
//		questionDao.setStuffParent(qstuff);
//		long psize = questionDao.getStuffParentSize(qstuff.getId());
//		if (psize >= 0 && chsize >= 0 && (chsize + mysize > psize)) {
//			questionDao.setStuffsize(qstuff.getId(),
//					(psize - chsize) > 0 ? psize - chsize : 0);
//		}
//		J2EEFileUtil.createFolder("/elstuffs/" + qstuff.getPath());
//		return "question_stuffwjjadd_succ";
//	}
//
//	public String question_stuffunzip() throws ElException, Exception {
//		qpstuff = questionDao.getStuffbyId(qpstuff.getId(), 0);
//		questionDao.setStuffParent(qpstuff);
//		String folder = ServletActionContext.getServletContext().getRealPath(
//				"/elstuffs/")
//				+ qpstuff.getPath() + "/";
//		StuffLib qstuff1 = new StuffLib();
//		StuffLib qstuff2 = questionDao.getStuffbyId(qstuff.getId(), 0);
//		qstuff1.setTitle(qstuff2.getTitle());
//		qstuff1.setParent(qpstuff);
//		qstuff1.setType(7);
//		qstuff1.setOwner(new ELUser(
//				getSessionIntValue(ElConstants.SESSION_USERID)));
//		questionDao.addQstuff(qstuff1);
//		questionDao.setStuffParent(qstuff1);
//		J2EEFileUtil.createFolder("/elstuffs/" + qstuff1.getPath());
//		try {
//			ZipUtil.unZip(new File(folder + qstuff.getId() + ".zip"), folder
//					+ "/" + qstuff1.getId() + "/");
//
//		} catch (FileNotFoundException e) { 
//			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
//					ElLoggerConstants.LOG_MOD_STUFF,
//					ElLoggerConstants.LOG_TYPE_GET, "文件不存在了!",
//					ElLoggerConstants.LOG_RES_ERR);
//			setElmessage("文件不存在了!");
//			return "error";
//		}
//		return "question_stuffunzip_succ";
//	}
//
//	public String question_stuffalterInit() throws ElException, Exception {
//		qstuff = questionDao.getStuffbyId(qstuff.getId(),
//				getSessionIntValue(ElConstants.SESSION_USERID));
//
//		return "question_stuffalter";
//	}
//
//	public String question_stuffalter() throws ElException, Exception {
//		questionDao.alter(qstuff);
//		return "question_stuffalter_success";
//	}

	public String questionlib_importInit() throws ElException {
		return "questionlib_import";
	}

	public String questionlib_import() throws ElException, Exception {
		if (null != st) {
			if (!J2EEFileUtil.getExtention(stFileName).equals("xls")) { 
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return "questionlib_import";
			}
			if (st.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "questionlib_import";
			} else {
				ExcelUtil.writeQlib(st,
						getSessionIntValue(ElConstants.SESSION_USERID));

			}
			((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("QUESTION_LIB");
		} else {
			setElmessage("请输入上传文件");
			return "questionlib_import";

		}
		return "questionlib_import_success";
	}

	public String question_importInit() throws ElException {
		return "question_import";
	}

	public String question_import() throws ElException, Exception {
		if (null != st) {
			if (!J2EEFileUtil.getExtention(stFileName).equals("xls")) {
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return "question_import";
			}
			if (st.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "question_import";
			} else {
				/*
				 * ExcelUtil.writeQuestion(st, question.getQtype(),
				 * getSessionIntValue(ElConstants.SESSION_USERID));
				 */
				// ExcelUtil.writeQuestion(st,
				// getSessionIntValue(ElConstants.SESSION_USERID));
				String isOk = ExcelUtil.writeQuestion2(st,
						getSessionIntValue(ElConstants.SESSION_USERID));
				if (!"true".equals(isOk)&&!"".equals(isOk)) {
					// 返回错误页面
					setElmessage(isOk);
					return "question_import";
				}
			}
		} else {
			setElmessage("请输入上传文件");
			return "question_import";
		}
		//return "question_import_success";
		return "question_list";
	}

	public String question_importByqlibInit() throws ElException {
		if(questionLib==null||questionLib.getId()<=0){
			setElmessage("请选择试题导入的节点！");
			return "error";
		}
//		questionLib = questionDao.getQLbById(questionLib == null ? 1
//				: questionLib.getId());
		questionLib = questionDao.getQLbById(questionLib.getId());
		if (getRequest().getParameter("newqlibid") != null) {
			getSession().setAttribute("newqlibid", questionLib.getId());
		}
		return "question_importByqlib";
	}
	/**试题导入校验
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String question_importByqlibcheck() throws ElException, Exception {
		questionLib = questionDao.getQLbById(questionLib.getId());
		if (null != st) {
			if (!J2EEFileUtil.getExtention(stFileName).equals("xls")) {
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return "question_importByqlib";
			}
			if (st.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "question_importByqlib";
			} else {
				int userid = getSessionIntValue(ElConstants.SESSION_USERID);
				String isOk = ExcelUtil.writeQues2(st, questionLib.getId(),
						userid,false);
				//备份文件
				String filename = "question_"+userid+ "_"+System.currentTimeMillis();
				J2EEFileUtil.upload(st, "xls", "/importtemp/"
						, filename);
				stfilename = filename+".xls";
//				if(!"".equals(isOk)){
					setElmessage(isOk);
//				}
			}
		} else {
			setElmessage("请输入上传文件");
			return "question_importByqlib";
		}
		return "question_importByqlibcheck";
	}
	/**
	 * Description: 指定题库目录的试题导入
	* @Version1.0 2012-7-15 上午11:42:32 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 * @throws Exception
	 */
	public String question_importByqlib() throws ElException, Exception {
		questionLib = questionDao.getQLbById(questionLib.getId());
//		if (null != st) {
//			if (!J2EEFileUtil.getExtention(stFileName).equals("xls")) {
//				setElmessage("您需要导入的文件格式不正确，请重新选择！");
//				return "question_importByqlib";
//			}
//			if (st.length() > 10 * 1024 * 1024) {
//				setElmessage("您上传的文件过大！");
//				return "question_importByqlib";
//			} else {
		if(stfilename!=null){
			File xls = new File(ServletActionContext.getServletContext().getRealPath("/")+"/importtemp/"+stfilename);
			if(xls.exists()){
				String isOk = "";
//				if (getSession().getAttribute("newqlibid") == null) {
					// ExcelUtil.writeQues(st, questionLib.getId(),
					// getSessionIntValue(ElConstants.SESSION_USERID));
					isOk = ExcelUtil.writeQues2(xls, questionLib.getId(),
							getSessionIntValue(ElConstants.SESSION_USERID),true);
//				} else {
//					// ExcelUtil.writeQues(st,
//					// getSessionIntValue(ElConstants.SESSION_USERID));
//					isOk = ExcelUtil.writeQues2(st, questionLib.getId(),
//							getSessionIntValue(ElConstants.SESSION_USERID));
//				}
				((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).updatetlrid("QUESTION_LIB");
//				if ("false".equals(isOk)&&!"".equals(isOk)) {
//					// 返回错误页面
//					setElmessage("批量导入未完成，请检查试题格式!");
//					return "question_importByqlib";
//				}
//				if ("false".equals(isOk)) {
//					// 返回错误页面
//					setElmessage("批量导入未完成，请检查试题格式!");
//					return "question_importByqlib";
//				}else{
					if(!"".equals(isOk)){
						if("false".equals(isOk)){
							setElmessage("批量导入失败，请检查试题格式!");
						}else{
							ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
									ElLoggerConstants.LOG_MOD_QUESTION,
									ElLoggerConstants.LOG_TYPE_IMPORT,isOk,
									ElLoggerConstants.LOG_RES_SUCC,questionLib.getId());
	//						return "question_importByqlib";
							setElmessage(isOk);
						}
					}else{
						setElmessage("批量导入全部成功!");
					}
//				}
				//xls.delete();
				//xls.deleteOnExit();
				return "question_import_result";
			} else {
				setElmessage("请输入上传文件");
				return "question_importByqlib";
			}
//			}
//			((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("QUESTION_LIB");
		} else {
			setElmessage("请输入上传文件");
			return "question_importByqlib";
		}
		//return "question_import_success";
		//return "question_list";
	}

	/*
		 * if (st_type == 1) { if (null == stfilename) { setElmessage("请填写文件名！");
		 * qstuffs = questionDao.getStuffs(qstuff,
		 * getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		 * getPageSize()); count = questionDao.getStuffsCount(qstuff,
		 * getSessionIntValue(ElConstants.SESSION_USERID));
		 * 
		 * return "question_stuffadd"; } if (!J2EEFileUtil.fileIsexists(stfilename)) {
		 * setElmessage("您输入的文件不存在！"); qstuffs = questionDao.getStuffs(qstuff,
		 * getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		 * getPageSize()); count = questionDao.getStuffsCount(qstuff,
		 * getSessionIntValue(ElConstants.SESSION_USERID));
		 * 
		 * return "question_stuffadd"; } String ext =
		 * J2EEFileUtil.getExtention(stfilename); if (null != stfilename)
		 * qstuff.setTitle(stfilename.substring(0, stfilename .lastIndexOf(".")));
		 * else qstuff.setTitle("未命名");
		 * 
		 * qstuff.setFileext(ext); qstuff.setOwner(new ELUser(
		 * getSessionIntValue(ElConstants.SESSION_USERID)));
		 * qstuff.setLength(st.length()); int id = questionDao.addQstuff(qstuff);
		 * J2EEFileUtil.rename(stfilename, id + "." + ext); } if (st_type == 0) {
		 */
	
	//	public StuffLib getQpstuff() {
	//		return qpstuff;
	//	}
	//
	//	public void setQpstuff(StuffLib qpstuff) {
	//		this.qpstuff = qpstuff;
	//	}
	//
	//	public StuffLib getStuffTree() {
	//		return stuffTree;
	//	}
	//
	//	public void setStuffTree(StuffLib stuffTree) {
	//		this.stuffTree = stuffTree;
	//	}
	//
	//	public StuffLib getStuffSharedTree() {
	//		return stuffSharedTree;
	//	}
	//
	//	public void setStuffSharedTree(StuffLib stuffSharedTree) {
	//		this.stuffSharedTree = stuffSharedTree;
	//	}
	
		// 导出试题
		public String question_exportExcel() throws ElException {
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
						getSessionIntValue(ElConstants.SESSION_USERID),
						ElConstants.TREE_FIANL, true);
			else {
				qlbTree = questionDao.getQlibTree(
						getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
						true);
			}
			if (getSession().getAttribute("Exclquestion") != null) {
				if ((Question) getSession().getAttribute("Exclquestion") != null) {
					question = (Question) getSession().getAttribute("Exclquestion");
				}
			}
			if (getRequest().getParameter("ww") != null) {
				questionlist = (List<Question>) getSession().getAttribute(
						"questionsexecl");
			} else {
				questionlist = questionDao.getQuestionList(question);
			}
			return "question_exportExcel";
		}

	// 导出试题库目录
	public String question_lib_exportExcel() throws ElException {
		questionliblist = questionDao.getQuestionlibList();
		return "question_lib_exportExcel";
	}
	
	/**
	 * 添加试卷大题时查看题量
	 * @return
	 * @throws ElException
	 */
	public String questionCountInfo() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID),
					ElConstants.TREE_FIANL, true,sublibs,question.getQtype());
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true,sublibs,question.getQtype());
		}
		return "questionCountInfo";
	}

	// --------------------Action的javabean的set/get方法-------------------------
	public QuestionLib getQuestionLib() {
		return questionLib;
	}

	public void setQuestionLib(QuestionLib questionLib) {
		this.questionLib = questionLib;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public QuestionLib getQlbTree() {
		return qlbTree;
	}

	public void setQlbTree(QuestionLib qlbTree) {
		this.qlbTree = qlbTree;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<QuestionArt> getQuestionarts() {
		return questionarts;
	}

	public void setQuestionarts(List<QuestionArt> questionarts) {
		this.questionarts = questionarts;
	}

	/*
	 * if (st_type == 1) { if (null == stfilename) { setElmessage("请填写文件名！");
	 * qstuffs = questionDao.getStuffs(qstuff,
	 * getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
	 * getPageSize()); count = questionDao.getStuffsCount(qstuff,
	 * getSessionIntValue(ElConstants.SESSION_USERID));
	 * 
	 * return "question_stuffadd"; } if (!J2EEFileUtil.fileIsexists(stfilename)) {
	 * setElmessage("您输入的文件不存在！"); qstuffs = questionDao.getStuffs(qstuff,
	 * getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
	 * getPageSize()); count = questionDao.getStuffsCount(qstuff,
	 * getSessionIntValue(ElConstants.SESSION_USERID));
	 * 
	 * return "question_stuffadd"; } String ext =
	 * J2EEFileUtil.getExtention(stfilename); if (null != stfilename)
	 * qstuff.setTitle(stfilename.substring(0, stfilename .lastIndexOf(".")));
	 * else qstuff.setTitle("未命名");
	 * 
	 * qstuff.setFileext(ext); qstuff.setOwner(new ELUser(
	 * getSessionIntValue(ElConstants.SESSION_USERID)));
	 * qstuff.setLength(st.length()); int id = questionDao.addQstuff(qstuff);
	 * J2EEFileUtil.rename(stfilename, id + "." + ext); } if (st_type == 0) {
	 */

//	public StuffLib getQpstuff() {
//		return qpstuff;
//	}
//
//	public void setQpstuff(StuffLib qpstuff) {
//		this.qpstuff = qpstuff;
//	}
//
//	public StuffLib getStuffTree() {
//		return stuffTree;
//	}
//
//	public void setStuffTree(StuffLib stuffTree) {
//		this.stuffTree = stuffTree;
//	}
//
//	public StuffLib getStuffSharedTree() {
//		return stuffSharedTree;
//	}
//
//	public void setStuffSharedTree(StuffLib stuffSharedTree) {
//		this.stuffSharedTree = stuffSharedTree;
//	}

	public boolean isSelectExprot() {
		return SelectExprot;
	}

	public void setSelectExprot(boolean selectExprot) {
		SelectExprot = selectExprot;
	}

	public boolean isLeeren() {
		return Leeren;
	}

	public void setLeeren(boolean leeren) {
		Leeren = leeren;
	}
}
