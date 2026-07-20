package com.sopia.questionman.action;


import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.dao.QuestionDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.ExampaperRandom;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.studyman.entities.MyExamPaper;

public class ExamPaperAction extends BaseAction {

	private ExamPaperLib eplTree;
	private QuestionLib qlbTree;
	private Question question;
	private List<Question> questions;
	private ExamPaperDao examPaperDao;
	private QuestionDao questionDao;
	private ExamPaperLib examPaperLib;
	private int sub_operate;
	private ExamPaper examPaper;
	private List<ExamPaper> examPapers;
	private List<ExamPaper> examPapers1;
	private ExamPaperBlock epBlock;
	private List<ExamPaperBlock> epBlocks;
	private int sublibs;
	private ExampaperRandom epRandom;
	private List<ExampaperRandom> epRandoms;
	private ExampaperRandom epRandom1;
	private List<ExampaperRandom> epRandoms1;
	private List<MyExamPaper> myExampapers;
	private CourseType ctype;
	private CourseDao courseDao;
	private CourseTypeDao ctypeDao;
	private CourseType ctypeTree;
	private List<Course> courses;
	private List<ExamRoom> examRooms;
	private List<ExamPaperLib> eplTrees;

	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}

	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}

	public List<MyExamPaper> getMyExampapers() {
		return myExampapers;
	}

	public CourseType getCtype() {
		return ctype;
	}

	public void setCtype(CourseType ctype) {
		this.ctype = ctype;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}

	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}

	public CourseType getCtypeTree() {
		return ctypeTree;
	}

	public void setCtypeTree(CourseType ctypeTree) {
		this.ctypeTree = ctypeTree;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void setMyExampapers(List<MyExamPaper> myExampapers) {
		this.myExampapers = myExampapers;
	}

	public ExampaperRandom getEpRandom() {
		return epRandom;
	}

	public void setEpRandom(ExampaperRandom epRandom) {
		this.epRandom = epRandom;
	}

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public ExamPaperLib getExamPaperLib() {
		return examPaperLib;
	}

	public void setExamPaperLib(ExamPaperLib examPaperLib) {
		this.examPaperLib = examPaperLib;
	}

	public String exampaperLib_addInit() throws ElException {
		// eplTree = examPaperDao.epLibTree(0,
		// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		/*
		 * if (AuthorityUtil.checkAuthor(
		 * getSessionIntValue(ElConstants.SESSION_ROLE), "exampaperLib_list",
		 * 0)) eplTree = examPaperDao.epLibTree(0,
		 * getSessionIntValue(ElConstants.SESSION_USERID), -1, true); if
		 * (eplTree == null) { eplTree = examPaperDao.epLibTree("op",
		 * getSessionIntValue(ElConstants.SESSION_USERID), -1, true); } else {
		 * eplTree.getChild().add( examPaperDao.epLibTree("op",
		 * getSessionIntValue(ElConstants.SESSION_USERID), -1, true)); }
		 */
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		if (eplTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的试卷库");
			return "error";
		}
		return "exampaperLib_add";
	}

	public String exampaperLib_list() throws ElException {
		// eplTree = examPaperDao.epLibTree(0,
		// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		// if (AuthorityUtil.checkAuthor(
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// "exampaperLib_list", 0))
		// eplTree = examPaperDao.epLibTree(0,
		// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		// if (eplTree == null) {
		// eplTree = examPaperDao.epLibTree("op",
		// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		// } else {
		// eplTree.getChild().add(
		// examPaperDao.epLibTree("op",
		// getSessionIntValue(ElConstants.SESSION_USERID), -1,
		// true));
		// }
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}

		return "exampaperLib_list";
	}

	public String exampaperLib_add() throws ElException {
		if (examPaperLib.getParent() == null
				|| examPaperLib.getParent().getId() <= 0) {
			setElmessage("请选择上级试卷库！");
			return "error";
		}
		examPaperDao.addepLib(examPaperLib);
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("exampaperlib");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EXAMPAPERLIB,
				ElLoggerConstants.LOG_TYPE_ADD, examPaperLib.getName(),
				ElLoggerConstants.LOG_RES_SUCC, examPaperLib.getId());
		return "exampaperLib_add_success";
	}

	public String exampaperLib_view() throws ElException {
		// if(
		// examPaperLib.getParent()==null||examPaperLib.getParent().getId()<=0){
		// setElmessage("请选择试卷库！");
		// return "error";
		// }
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		if (examPaperLib == null || examPaperLib.getId() <= 0) {
			setElmessage("您需要查看的试卷库不存在,请重新选择！");
			return "error";
		}
		examPaperLib = examPaperDao.getEpLById(examPaperLib.getId());
		examPaperLib.setOpusers(examPaperDao.getOpUsers("op", examPaperLib
				.getId()));
		// examPaperLib.setUseusers(examPaperDao.getOpUsers("op", examPaperLib
		// .getId()));
		//		
		return "exampaperLib_view";
	}

	public String exampaperLib_alterInit() throws ElException {
		examPaperLib = examPaperDao.getEpLById(examPaperLib.getId());
		// eplTree = examPaperDao.epLibTree(0,
		// getSessionIntValue(ElConstants.SESSION_USERID), examPaperLib
		// .getId(), false);
		/*
		 * if (AuthorityUtil.checkAuthor(
		 * getSessionIntValue(ElConstants.SESSION_ROLE), "exampaperLib_list",
		 * 0)) eplTree = examPaperDao.epLibTree(0,
		 * getSessionIntValue(ElConstants.SESSION_USERID), examPaperLib.getId(),
		 * false); if (eplTree == null) { eplTree = examPaperDao.epLibTree("op",
		 * getSessionIntValue(ElConstants.SESSION_USERID), examPaperLib.getId(),
		 * false); } else { eplTree.getChild().add( examPaperDao.epLibTree("op",
		 * getSessionIntValue(ElConstants.SESSION_USERID), examPaperLib.getId(),
		 * false)); }
		 */
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		examPaperLib.setOpusers(examPaperDao.getOpUsers("op", examPaperLib
				.getId()));
		// examPaperLib.setUseusers(examPaperDao.getOpUsers("op", examPaperLib
		// .getId()));

		return "exampaperLib_alter";
	}

	private String optype;
	private ELUser elUser;

	public String eplib_delete_user() throws ElException {
		examPaperDao
				.deleteOpusers(optype, elUser.getId(), examPaperLib.getId());
		roleDao.checkUserfunc(elUser.getId(), "exampaperLib_list",
				"exampaperlib_OP_user");
		roleDao.checkUserfunc(elUser.getId(), "exampaperLib_addInit",
				"exampaperlib_OP_user");
		roleDao.checkUserfunc(elUser.getId(), "exampaper_listInit",
				"exampaperlib_OP_user");
		roleDao.checkUserfunc(elUser.getId(), "exampaper_addInit",
				"exampaperlib_OP_user");
		roleDao.checkUserfunc(elUser.getId(), "admin", "exampaperlib_OP_user");
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

	private RoleDao roleDao;

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public String exampaperLib_alter() throws ElException {
		if (examPaperLib.getId() == 1) {
			examPaperLib.setParent(new ExamPaperLib(0));
		}
		if (examPaperLib.getParent() == null) {
			examPaperLib.setParent(new ElNode(1));
		}
		examPaperDao.alterEpl(examPaperLib,
				getSessionIntValue(ElConstants.SESSION_USERID));
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("exampaperlib");
		if (null != examPaperLib.getOpusers()) {
			for (int i = 0; i < examPaperLib.getOpusers().size(); i++) {
				if (!examPaperDao.checkOpUsers("op", examPaperLib.getOpusers()
						.get(i).getId(), examPaperLib.getId()))
					examPaperDao.addOpusers("op", examPaperLib.getOpusers()
							.get(i).getId(), examPaperLib.getId());
//				roleDao.setUserfunc(examPaperLib.getOpusers().get(i).getId(),
//						"exampaperLib_list", 0);
//				roleDao.setUserfunc(examPaperLib.getOpusers().get(i).getId(),
//						"admin", 0);
//				roleDao.setUserfunc(examPaperLib.getOpusers().get(i).getId(),
//						"exampaperLib_addInit", 0);
//				roleDao.setUserfunc(examPaperLib.getOpusers().get(i).getId(),
//						"exampaper_listInit", 0);
//				roleDao.setUserfunc(examPaperLib.getOpusers().get(i).getId(),
//						"exampaper_addInit", 0);
			}
		}
		if (null != examPaperLib.getUseusers()) {
			for (int i = 0; i < examPaperLib.getUseusers().size(); i++) {
				if (!examPaperDao.checkOpUsers("op", examPaperLib.getUseusers()
						.get(i).getId(), examPaperLib.getId()))
					examPaperDao.addOpusers("op", examPaperLib.getUseusers()
							.get(i).getId(), examPaperLib.getId());
			}
		}
		examPaperLib = examPaperDao.getEpLById(examPaperLib.getId());
		if (examPaperLib != null) {
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EXAMPAPERLIB,
					ElLoggerConstants.LOG_TYPE_ALTER, examPaperLib.getName(),
					ElLoggerConstants.LOG_RES_SUCC, examPaperLib.getId());
		}
		return "exampaperLib_alter_success";
	}

	public String exampaperLib_deleteInit() throws ElException {
		if (examPaperLib.getId() == 1) {
			setElmessage("不能删除根类别");
			return "error";
		}
		examPaperLib = examPaperDao.getEpLById(examPaperLib.getId());
		return "exampaperLib_delete";
	}

	public String exampaperLib_delete() throws ElException {
		if (examPaperLib.getId() == 1) {
			setElmessage("不能删除根类别");
			return "error";
		}
		examPaperLib = examPaperDao.getEpLById(examPaperLib.getId());
		if (sub_operate == 0) {
			// 并入上级试题库
			// examPaperDao.deleteEplasUnit(examPaperLib.getId(),
			// (Integer) getSession().getAttribute("userId"));
			int id = examPaperLib.getId();
			// int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			ExamPaperLib epl = examPaperDao.getEpLById(id);
			epl.setChild(examPaperDao.listEpChild(id));
			// for (int i = 0; i < epl.getChild().size(); i++) {
			// // examPaperDao.setEplparent(epl.getChild().get(i).getId(), epl
			// // .getParent().getId(), userid);
			// examPaperDao.setEplparent2(epl.getChild().get(i).getId(),
			// epl.getParent().getId());//设置试卷库的子节点
			// }
			examPaperDao.setEplparent2(epl.getId(), epl.getParent().getId());// 设置试卷库的子节点
			// 设置该试卷库的试卷父节点
			examPaperDao.setEpparent(epl.getId(), epl.getParent().getId());// 设置试卷库的子节点
			try {
				// examPaperDao.deleteEpl(id);
				examPaperDao.deleteEplNot(id);
			} catch (Exception e) {
				setElmessage("考场正在使用的试卷不能被删除！");
				return "error";
			}
		} else {
			// 与本试题库同时删除
			// examPaperDao.deleteEplasSub(examPaperLib.getId(),
			// (Integer) getSession().getAttribute("userId"));
			// int id = examPaperLib.getId();
			// int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			// List<ExamPaperLib> child = examPaperDao.listEpChild(id);
			// deleteChild(userid, child);
			//			
			// try {
			// examPaperDao.deleteEpl(id);
			// } catch (Exception e) {
			// setElmessage("考场正在使用的试卷不能被删除！");
			// return "error";
			// }
			// 查出该类别的左右id，然后查出所有子类别，然后循环根据id删除子类别，删除所有类别下的课程最后删除该类别
			// examPaperDao.deleteEpAndSub(examPaperLib.getId());
			examPaperDao.deleteEpAndSubNot(examPaperLib.getId());
		}
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("exampaperlib");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EXAMPAPERLIB,
				ElLoggerConstants.LOG_TYPE_DELETE, examPaperLib.getName(),
				ElLoggerConstants.LOG_RES_SUCC, examPaperLib.getId());
		return "exampaperLib_delete_success";
	}

	private void deleteChild(int userid, List<ExamPaperLib> child)
			throws ElException {
		for (int i = 0; i < child.size(); i++) {
			examPaperDao.deleteEpl(child.get(i).getId());
			deleteChild(userid, examPaperDao.listEpChild(child.get(i).getId()));
		}
	}

	// ---------------------试卷管理-------------
	public String exampaper_addInit() throws ElException {
		// eplTree = examPaperDao.epLibTree(0,
		// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		if (eplTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的试卷库");
			return "error";
		}
		return "exampaper_add";
	}

	public String exampaper_add() throws ElException {
		examPaper.setElUser(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		if (examPaperDao.getExamPaperId(examPaper.getTitle(), examPaper
				.getElUser().getId()) == -1) {
			examPaperDao.addExamPaper(examPaper);
			// examPaper.setId(examPaperDao.getExamPaperId(examPaper.getTitle(),
			// examPaper.getElUser().getId()));
		} else {
			// eplTree = examPaperDao.epLibTree(0,
			// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				eplTree = examPaperDao.epLibTree(0,
						getSessionIntValue(ElConstants.SESSION_USERID), -1,
						true);
			else {
				eplTree = examPaperDao.epLibTree("op",
						getSessionIntValue(ElConstants.SESSION_USERID), -1,
						true);
			}

			setElmessage("您已经创建了一个同样标题的试卷了，请重新输入名！");
			return "exampaper_add";
		}
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EXAMPAPER,
				ElLoggerConstants.LOG_TYPE_ADD, examPaper.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examPaper.getId());
		return "exampaper_add_success";
	}

	public String exampaper_all_alterinit() throws ElException {
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		eplTrees = examPaperDao.getExampaperlib();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		epBlocks = examPaperDao.listEpBlockByEpid(examPaper.getId());
		if (null != epBlocks)
			for (int i = 0; i < epBlocks.size(); i++) {
				ExamPaperBlock block = epBlocks.get(i);
				if (block.getRandom() == 0) {
					block.setQuestions(examPaperDao
							.listEpBlockQusetionsByBepbId(block.getId()));
				} else {
					block
							.setEpRandom(examPaperDao.listEpbRandom(block
									.getId()));
				}
			}
		examPaper.setEpBlocks(epBlocks);
		return "exampaper_all_alter";
	}

	public String exampaper_view() throws ElException {
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		return "exampaper_view";
	}

	public String exampaper_alterInit() throws ElException {
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		eplTrees = examPaperDao.getExampaperlib();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}

		return "exampaper_alter";
	}

	public String exampaper_alter() throws ElException {
		examPaperDao.alterExamPaper(examPaper);
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EXAMPAPER,
				ElLoggerConstants.LOG_TYPE_ALTER, examPaper.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examPaper.getId());
		return "exampaper_view";
	}

	public String exampaper_listInit() throws ElException {
		// eplTree = examPaperDao.epLibTree(0,
		// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		if (eplTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的试卷库");
			return "error";
		}
		return "exampaper_listInit";
	}

	// 注释原因： 以优化exampaper_list 可删除
	// public String exampaper_list() throws ElException {
	// // eplTree = examPaperDao.epLibTree(0,
	// // getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
	// int eplid = examPaper == null || examPaper.getEpl() == null ? 0
	// : examPaper.getEpl().getId();
	// String title = examPaper == null ? "" : examPaper.getTitle();
	//		
	// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
	// eplTree = examPaperDao.epLibTree(0,
	// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
	// if (sublibs == 1 || eplid== 1) {// 包含下级类别
	// examPapers = examPaperDao.listEpsByEplId(eplid, title, true,getPageNow(),
	// getPageSize());
	// count = examPaperDao.listEpsByEpIdSize(eplid, title, true);
	// } else {
	// examPapers = examPaperDao.listEpsByEplId(eplid, title,
	// false,getPageNow(), getPageSize());
	// count = examPaperDao.listEpsByEpIdSize(eplid, title, false);
	// }
	// }else {
	// eplTree = examPaperDao.epLibTree("op",
	// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
	// // if (sublibs == 1) {// 包含下级类别
	// // examPapers = examPaperDao.exampaper_list_listEpsByEplId(eplTree,eplid,
	// title, true,getPageNow(), getPageSize());
	// // count = examPaperDao.exampaper_list_listEpsByEpIdSize(eplTree,eplid,
	// title, true);
	// // } else {
	// examPapers = examPaperDao.exampaper_list_listEpsByEplId(eplTree,eplid,
	// title, false,getPageNow(), getPageSize());
	// count = examPaperDao.exampaper_list_listEpsByEpIdSize(eplTree,eplid,
	// title, false);
	// // }
	// }
	//
	// // getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
	//
	// return "exampaper_list";
	// }
	public String exampaper_list() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		} else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		ExamPaperLib epl = null;
		if (examPaper == null || examPaper.getEpl() == null
				|| examPaper.getEpl().getId() <= 0) {
			epl = eplTree;
			// sublibs = 1;
		} else {
			if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1
					&& !((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.checkNode(examPaper.getEpl().getId(), eplTree,
									"exampaperlib")) {
				setElmessage("您输入了无权操作的节点！");
				return "error";
			}
			epl = examPaperDao.getEpLById(examPaper.getEpl().getId());
		}
		sublibs = examPaper == null ? 1 : sublibs;
		examPapers = examPaperDao.listEpsByEplId(epl, sublibs, examPaper,
				getPageNow(), getPageSize(), 1);
		count = examPaperDao.listEpsByEpIdSize(epl, sublibs, examPaper, 1);
		return "exampaper_list";
	}

	// 试卷已创建状态
	public String exampaper_update_status() throws ElException {
		String result = examPaperDao.checkExampaper(examPaper.getId());
		if ("yes".equals(result)) {
			examPaperDao.setExamPaperStatus(examPaper.getId(), 0); // 0已创建
			
			//试卷创建完成，更新试卷的总题目数量
			examPaperDao.setExamPaperQuestionTotalCount(examPaper.getId());
			
			examPaper = examPaperDao.getExamPaperById(examPaper.getId());
			// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			// eplTree = examPaperDao.epLibTree(0,
			// getSessionIntValue(ElConstants.SESSION_USERID), -1,
			// true);
			// } else {
			// eplTree = examPaperDao.epLibTree("op",
			// getSessionIntValue(ElConstants.SESSION_USERID), -1,
			// true);
			// }
			// ExamPaperLib epl = null;
			// if (examPaper == null || examPaper.getEpl() == null
			// || examPaper.getEpl().getId() <= 0) {
			// epl = eplTree;
			// sublibs = 1;
			// } else {
			// if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1
			// && !((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
			// .checkNode(examPaper.getEpl().getId(), eplTree,
			// "exampaperlib")) {
			// setElmessage("您输入了无权操作的节点！");
			// return "error";
			// }
			// epl = examPaperDao.getEpLById(examPaper.getEpl().getId());
			// }

			// examPapers = examPaperDao.listEpsByEplId(epl, sublibs, examPaper,
			// getPageNow(), getPageSize(), 1);
			// count = examPaperDao.listEpsByEpIdSize(epl, sublibs, examPaper,
			// 1);
			//刷新缓存
			List<ExamPaperBlock> questions = examPaperDao.listEpBlockByEpid(examPaper.getId());
			String questionIds = "";
			for (ExamPaperBlock examPaperBlock : questions) {
				questionIds +=examPaperBlock.getId()+",";
			}
			if(!questionIds.equals("")){
				examPaperDao.addPaperData(examPaper.getId(),questionIds);
				//加入缓存
				SystemConfOp.getCache().put(""+examPaper.getId(), questionIds);
			}
			
			return "exampaper_update_status";
		} else {
			setElmessage("<div style='font-size:13px;font-weight:normal'>"
					+ result + "</div>");
			return "error";
		}
	}

	public String course_exam_readlistInit() throws ElException {

		// int ctid = ctype == null ? ctypeDao.getCtypeRoot().getId() : ctype
		// .getId();

		// ctypeTree = ctypeDao.getCtypeTreeByPerOrShar(ElConstants.TREE_ROOT,
		// ElConstants.TREE_FIANL, true,
		// String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
		// true, "COURSE_USE_TYPE");

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			ctypeTree = ctypeDao.getCourseLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}

		int ctid = ctype == null ? ctypeTree.getId() : ctype.getId();

		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		courses = courseDao.readlistInitlistMyCourse(ctypeTree, ctid,
				getSessionIntValue(ElConstants.SESSION_ROLE), "", getPageNow(),
				getPageSize());
		count = courseDao.readlistInitlistMyCourseCount(ctypeTree, ctid,
				getSessionIntValue(ElConstants.SESSION_ROLE), "");
		return "course_exam_readlist";
	}

	private EroomDao eroomDao;

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public String exampaper_delete() throws ElException {
		if (null != examPapers1)
			// examPaperDao.deleteEps(examPapers1);
			for (int i = 0; i < examPapers1.size(); i++) {
				try {
					examPaperDao.deleteExamPaper(examPapers1.get(i).getId());
					ExamPaper tempexamPaper = examPaperDao
							.getExamPaperById(examPapers1.get(i).getId());
					if (tempexamPaper != null) {
						ElLogger.busilogger(
								getSessionIntValue(ElConstants.SESSION_USERID),
								ElLoggerConstants.LOG_MOD_EXAMPAPER,
								ElLoggerConstants.LOG_TYPE_DELETE,
								tempexamPaper.getTitle(),
								ElLoggerConstants.LOG_RES_SUCC, tempexamPaper
										.getId());
					}
				} catch (Exception e) {
					if (i + 1 == examPapers1.size()) {
						setElmessage("存在正在考场使用的试卷不能被删除！");
						return "error";
					}
				}
			}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		} else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		ExamPaperLib epl = null;
		if (examPaper == null || examPaper.getEpl() == null
				|| examPaper.getEpl().getId() <= 0) {
			epl = eplTree;
			sublibs = 1;
		} else {
			epl = examPaperDao.getEpLById(examPaper.getEpl().getId());
		}

		examPapers = examPaperDao.listEpsByEplId(epl, sublibs, examPaper,
				getPageNow(), getPageSize(), 1);
		count = examPaperDao.listEpsByEpIdSize(epl, sublibs, examPaper, 1);
		// if (sublibs == 1) {// 包含下级类别
		// examPapers = examPaperDao.listEpsByEplId(
		// examPaper.getEpl().getId(), examPaper.getTitle(), true,
		// getPageNow(), getPageSize());
		// count = examPaperDao.listEpsByEpIdSize(examPaper.getEpl().getId(),
		// examPaper.getTitle(), true);
		//
		// } else {
		// examPapers = examPaperDao.listEpsByEplId(
		// examPaper.getEpl().getId(), examPaper.getTitle(), false,
		// getPageNow(), getPageSize());
		// count = examPaperDao.listEpsByEpIdSize(examPaper.getEpl().getId(),
		// examPaper.getTitle(), false);
		//
		// }
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// eplTree = examPaperDao.epLibTree(0,
		// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		// else {
		// eplTree = examPaperDao.epLibTree("op",
		// getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		// }
		return "exampaper_list";
	}

	public String exampaper_delete_status() throws ElException {
		if (null != examPapers1)
			// examPaperDao.deleteEps(examPapers1);
			for (int i = 0; i < examPapers1.size(); i++) {
				examPaperDao.setExamPaperStatus(examPapers1.get(i).getId(), 1);
				ExamPaper tempexamPaper = examPaperDao
						.getExamPaperById(examPapers1.get(i).getId());
				if (tempexamPaper != null) {
					ElLogger.busilogger(
							getSessionIntValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_EXAMPAPER,
							ElLoggerConstants.LOG_TYPE_DELETE, tempexamPaper
									.getTitle(),
							ElLoggerConstants.LOG_RES_SUCC, tempexamPaper
									.getId());
				}
			}

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		} else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		ExamPaperLib epl = null;
		if (examPaper == null || examPaper.getEpl() == null
				|| examPaper.getEpl().getId() <= 0) {
			epl = eplTree;
			sublibs = 1;
		} else {
			examPaper.setEpl(examPaperDao
					.getEpLById(examPaper.getEpl().getId()));
		}

		examPapers = examPaperDao.listEpsByEplId(epl, sublibs, examPaper,
				getPageNow(), getPageSize(), 1);
		count = examPaperDao.listEpsByEpIdSize(epl, sublibs, examPaper, 1);
		return "exampaper_list";
	}

	// =====================大题管理=================
	public String exampaperblock_list() throws ElException {
		int id = examPaper.getId();
		// examPaper = examPaperDao.getExamPaperById(id);
		// if (!examPaper.getRandom())
		examPaper.setEpBlocks(examPaperDao.listEpBlockByEpid(id));
		// else
		// examPaper.setEpBlocks(examPaperDao.listEpBlockByEpidRandom(id));
		ExamPaper  ExamPaperForGetQuestionAndRealScore = examPaperDao.getQuestionScoreAndRealScore(id);
		examPaper.setEp_questionscore(ExamPaperForGetQuestionAndRealScore.getEp_questionscore());
		examPaper.setEp_realscore(ExamPaperForGetQuestionAndRealScore.getEp_realscore());
		return "exampaperblock_list";
	}

	public String exampaperblock_details_list() throws ElException {
		int id = examPaper.getId();
		// examPaper = examPaperDao.getExamPaperById(id);
		// if (!examPaper.getRandom())
		examPaper.setEpBlocks(examPaperDao.listEpBlockByEpid(id));
		// else
		// examPaper.setEpBlocks(examPaperDao.listEpBlockByEpidRandom(id));
		return "exampaperblock_details_list";
	}

	public String exampaperblock_upsort() throws ElException {
		examPaperDao.sortEpBlock(examPaper.getId(), epBlock.getSortid(),
				ElConstants.SORT_UP);
		return "exampaperblock_list";
	}

	public String exampaperblock_downsort() throws ElException {
		examPaperDao.sortEpBlock(examPaper.getId(), epBlock.getSortid(),
				ElConstants.SORT_DOWN);
		return "exampaperblock_list";
	}

	public String exampaperblock_addInit() throws ElException {
		// examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		return "exampaperblock_add";
	}

	public String exampaperblock_add() throws ElException {
		epBlock.setExamPaper(examPaper);
		examPaperDao.addExamPaperBlock(epBlock);
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EXAMPAPERBLOCK,
				ElLoggerConstants.LOG_TYPE_ADD, examPaper.getTitle()
						+ " 试卷添加了大题 " + epBlock.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC, examPaper.getId());
		return "exampaperblock_add_success";
	}

	public String exampaperblock_rulealterinit() throws ElException {
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		return "exampaperblock_rulealter";
	}

	public String exampaperblock_rulealter() throws ElException {
		examPaperDao.alterEpBlockrule(epBlock.getId(), epBlock.getRulestring());
		// 评分规则业务日志记录
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EXAMPAPER,
				ElLoggerConstants.LOG_TYPE_ALTER, "修改了 "
						+ epBlock.getExamPaper().getTitle() + "("
						+ epBlock.getExamPaper().getId() + ") 试卷大题 "
						+ epBlock.getTitle() + "(" + epBlock.getId()
						+ ") 的评分规则.", ElLoggerConstants.LOG_RES_SUCC, epBlock
						.getId());
		return "exampaperblock_rulealter";
	}

	public String exampaperblock_alterInit() throws ElException {
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		return "exampaperblock_alter";
	}

	public String exampaperblock_alter() throws ElException {
		examPaperDao.alterExamPaperBlock(epBlock);
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		if (epBlock != null) {
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EXAMPAPERBLOCK,
					ElLoggerConstants.LOG_TYPE_ALTER, epBlock.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, epBlock.getId());
		}
		// epBlock = examPaperDao.getEpbById(epBlock.getId());
		return "exampaperblock_view";
	}

	public String exampaperblock_delete() throws ElException {
		// if (null != epBlocks)
		// for (int i = 0; i < epBlocks.size(); i++) {
		examPaperDao.deleteEpb(epBlock.getId());
		// }
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		if (epBlock != null) {
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EXAMPAPERBLOCK,
					ElLoggerConstants.LOG_TYPE_DELETE, epBlock.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC, epBlock.getId());
		}
		return "exampaperblock_list";
	}

	public String exampaperblock_view() throws ElException {
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		return "exampaperblock_view";
	}

	// /=======================大题试题管理
	public String exampaperblockquestion_list() throws ElException {
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		if (0 == epBlock.getRandom()) {
			epBlock = examPaperDao.getEpbWithQuestionsById(epBlock.getId());
			return "exampaperblockquestion_list";
		} else {
			epRandoms = examPaperDao.listEpbRandom(epBlock.getId());
			epBlock = examPaperDao.getEpbById(epBlock.getId());
		}
		return "exampaperblockquestion_randomlist";
	}

	public String exampaperblockquestion_details_list() throws ElException {
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		if (0 == epBlock.getRandom()) {
			epBlock = examPaperDao.getEpbWithQuestionsById(epBlock.getId());
			return "exampaperblockquestion_details_list";
		} else {
			epRandoms = examPaperDao.listEpbRandom(epBlock.getId());
			epBlock = examPaperDao.getEpbById(epBlock.getId());
		}
		return "exampaperblockquestion_details_randomlist";
	}

	public String exampaperblockquestion_deleteRandom() throws ElException {
		examPaperDao.deleteEpbRandom(epRandom.getId());
		return "exampaperblockquestion_list";
	}

	public String exampaperblockquestion_rulealterinit() throws ElException {
		question = examPaperDao.getEpBlockQusetionsByBepbId(epBlock.getId(),
				question.getId());
		return "exampaperblockquestion_rulealter";
	}

	public String exampaperblockquestion_rulealter() throws ElException {
		examPaperDao.alterEpBlockQusetionrule(epBlock.getId(),
				question.getId(), question.getRulestring());
		// 评分规则业务日志记录
		question = questionDao.getQbyId(question.getId());
		if (question.getTitle().length() > 15) {
			question.setTitle(question.getTitle().substring(0, 15));
		}
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_EXAMPAPER,
				ElLoggerConstants.LOG_TYPE_ALTER, "修改了 "
						+ epBlock.getExamPaper().getTitle() + "("
						+ epBlock.getExamPaper().getId() + ") 试卷大题 "
						+ epBlock.getTitle() + "(" + epBlock.getId() + ") 中小题"
						+ question.getTitle() + "(" + question.getId()
						+ ") 的评分规则.", ElLoggerConstants.LOG_RES_SUCC, question
						.getId());
		return "exampaperblockquestion_rulealter";
	}

	public String exampaperblockquestion_addSearchInit() throws ElException {
		// if (AuthorityUtil.checkAuthor(
		// getSessionIntValue(ElConstants.SESSION_ROLE),
		// "question_lib_list", 0))
		// qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// ElConstants.TREE_FIANL, true);
		// if (qlbTree == null) {
		// qlbTree = questionDao.getQlibTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// } else {
		// qlbTree.getChild().add(
		// questionDao.getQlibTree(
		// getSessionIntValue(ElConstants.SESSION_USERID),
		// "op", -1, true));
		// }
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			qlbTree = questionDao.getQlibTree(ElConstants.TREE_ROOT,
					getSessionIntValue(ElConstants.SESSION_USERID), 1, false);
		else {
			qlbTree = questionDao.getQlibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", 1,
					false);
		}
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		if (epBlock.getRandom() == 1) {
			return "exampaperblockquestion_addSearchRandom";
		}
		return "exampaperblockquestion_addSearch";
	}

	/**
	 * 随机试卷大题设置
	 * 
	 * @return
	 * @throws ElException
	 */
	public String exampaperblockquestion_addRandomInit() throws ElException {
		int qlibid = question.getQlib().getId();
		int fwsize = epBlock.getFwsize();
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		if (sublibs == 0) {
			if (epBlock.getType() != 8)
				epRandom1 = examPaperDao.getEPRandomBy(qlibid, epBlock
						.getType(), sub_operate);
			else
				epRandom1 = examPaperDao.getEPRandomBy(qlibid, epBlock
						.getType(), sub_operate, fwsize);
		} else {
			epRandoms1 = examPaperDao.getEPRandomsBy(qlibid, epBlock.getType(),
					fwsize);
		}
		question.setQlib(questionDao.getQLbById(qlibid));
		return "exampaperblockquestion_addRandom";
	}

	/**
	 * 随机试卷大题设置
	 * 
	 * @return
	 * @throws ElException
	 */
	public String exampaperblockquestion_addRandom() throws ElException {
		int qlibid = question.getQlib().getId();
		if (sublibs == 0) {
			epRandom1 = examPaperDao.getEPRandomBy(qlibid, epBlock.getType(),
					sub_operate);
			if (epRandom.compareTo(epRandom1)) {
				if (!examPaperDao.checkEpbRandom(epRandom.getQlib().getId(),
						epRandom.getEpBlock().getId())) {
					examPaperDao.addEpbRandom(epRandom);
				} else {
					examPaperDao.updateEpbRandom(epRandom);
				}
			} else {
				setElmessage(epRandom.getErrorMessage());
				setEpBlock(examPaperDao.getEpbById(epBlock.getId()));
				return "exampaperblockquestion_addRandom";
			}
			question.setQlib(questionDao.getQLbById(qlibid));
		} else {
			if (epRandoms != null)
				for (int i = 0; i < epRandoms.size(); i++) {
					ExampaperRandom er = epRandoms.get(i);
					epRandom1 = examPaperDao.getEPRandomBy(
							er.getQlib().getId(), epBlock.getType(), 1);
					er.setEpBlock(epRandom.getEpBlock());
					// er.setQlib(epRandom.getQlib() );
					er.setSuboperate(1);
					if (er.compareTo(epRandom1)) {
						// examPaperDao.addEpbRandom(er);
						if (!examPaperDao.checkEpbRandom(er.getQlib().getId(),
								er.getEpBlock().getId())) {
							examPaperDao.addEpbRandom(er);
						} else {
							examPaperDao.updateEpbRandom(er);
						}
					} else {
						setElmessage(er.getErrorMessage());
						setEpBlock(examPaperDao.getEpbById(epBlock.getId()));
						return null;
					}
				}
		}
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		return "exampaperblockquestion_addRandom_success";
	}

	public String exampaperblockquestion_alterRandomInit() throws ElException {
		epRandom = examPaperDao.getEPRandomById(epRandom.getId());
		epRandom1 = examPaperDao.getEPRandomBy(epRandom.getQlib().getId(),
				epRandom.getEpBlock().getType(), epRandom.getSuboperate());
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		// return "exampaperblockquestion_alterRandom";
		return "exampaperblockquestion_alterRandom2";
	}

	public String exampaperblockquestion_alterRandom() throws ElException {
		examPaperDao.alterEpbRandom(epRandom);
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		return "exampaperblockquestion_alterRandom_success";
	}

	public String exampaperblockquestion_addSearchList() throws ElException {
		// if(question.getQtype()==12) question.setQtype(0);
		boolean b = sublibs == 1 ? true : false;
		// String where = question.getQtype() ==8 ? " and q.fwsize>=
		// "+epBlock.getFwsize()
		// :"";
		questions = questionDao.listMyQuestions_wjm(question.getTitle(), question
				.getQlib().getId(), question.getQtype(), b, "and q.status !=1",
				getPageNow(), getPageSize());
		count = questionDao.listMyQuestionsSize_wjm(question.getTitle(), question
				.getQlib().getId(), question.getQtype(), b, "and q.status !=1");

		// if (sublibs == 1) {
		// questions = questionDao.listMyQuestions(question.getTitle(),
		// question.getQlib().getId(), question.getQtype(), true,
		// "and q.status !=1", getPageNow(), getPageSize());
		// count = questionDao.listMyQuestionsSize(question.getTitle(),
		// question.getQlib().getId(), question.getQtype(), true,
		// "and q.status !=1");
		// } else {
		// questions = questionDao.listMyQuestions(question.getTitle(),
		// question.getQlib().getId(), question.getQtype(), false,
		// "and q.status !=1", getPageNow(), getPageSize());
		// count = questionDao.listMyQuestionsSize(question.getTitle(),
		// question.getQlib().getId(), question.getQtype(), false,
		// "and q.status !=1");
		// }
		for (int i = 0; i < questions.size(); i++) {
			questions.get(i).setEqbHave(
					examPaperDao.haveTheQuestion(epBlock.getId(), questions
							.get(i).getId()));
		}
		setEpBlock(examPaperDao.getEpbById(epBlock.getId()));
		return "exampaperblockquestion_addSearchList";
	}

	public String exampaperblockquestion_add() throws ElException {
		// if (null != questions)
		// for (int i = 0; i < questions.size(); i++) {
		setEpBlock(examPaperDao.getEpbById(epBlock.getId()));
		if (epBlock.getType() != 12) {
			if (examPaperDao.checkQuestionSize(epBlock.getId())) {
				if (!examPaperDao.haveTheQuestion(epBlock.getId(), question
						.getId())) {
					examPaperDao.addEpbQuestion(epBlock.getId(), question
							.getId(), epBlock.getEachscore());
				}
			} else {
				setElmessage("您添加的试题已达到该大题的最大数量，刚刚1道题没有执行添加");
			}
		} else {
			if (!examPaperDao
					.haveTheQuestion(epBlock.getId(), question.getId())) {
				examPaperDao.addEpbQuestion(epBlock.getId(), question.getId(),
						epBlock.getEachscore());
			}
		}
		return "exampaperblockquestion_add";
	}

	public String exampaperblockquestion_delete() throws ElException {
		// if (null != questions) {
		// for (int i = 0; i < questions.size(); i++) {
		examPaperDao.deleteEpbQuestion(question.getId(), epBlock.getId());

		// }
		// }
		epBlock = examPaperDao.getEpbWithQuestionsById(epBlock.getId());

		return "exampaperblockquestion_delete";
	}

	public String exampaperblockquestion_upsort() throws ElException {
		examPaperDao.sortEpbQs(epBlock.getId(), question.getSortid(),
				ElConstants.SORT_UP);
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		// if (!epBlock.getExamPaper().getRandom())
		// epBlock = examPaperDao.getEpbWithQuestionsById(epBlock.getId());
		return "exampaperblockquestion_sort";
	}

	public String exampaperblockquestion_downsort() throws ElException {
		examPaperDao.sortEpbQs(epBlock.getId(), question.getSortid(),
				ElConstants.SORT_DOWN);
		epBlock = examPaperDao.getEpbById(epBlock.getId());
		// if (!epBlock.getExamPaper().getRandom())
		// epBlock = examPaperDao.getEpbWithQuestionsById(epBlock.getId());
		// else {
		//
		// }
		return "exampaperblockquestion_sort";
	}

	// -=------------

	public String exampaper_preview() throws ElException {
		examPaper = examPaperDao.getEPAllInfoById(examPaper.getId());
		return "exampaper_preview";
	}

	public String exampaper_preview_1b1() throws ElException {
		examPaper = examPaperDao.getEPAllInfoById(examPaper.getId());
		return "exampaper_preview_1b1";
	}

	public String epread_quizlist() throws ElException {
		// getPageSize() = getPageSize() == 0 ? getPageSize() = 10 :
		// getPageSize();
		myExampapers = examPaperDao.listEprquiz(examPaper.getId(),
				getPageNow(), getPageSize());
		count = examPaperDao.listEprquizSize(examPaper.getId());

		return "epread_quizlist";
	}

	// 复制试卷
	public String exampaper_copy() throws ElException {
		int status = examPaperDao.copyExampaper(examPaper.getId());
		if (status > 0) {
			examPaper.setId(status);
			return "exampaper_copy";
		} else {
			setElmessage("复制试卷错误！");
			return "erro";
		}
	}

	public ExamPaperLib getEpLibTree() {
		return eplTree;
	}

	public void setEplTree(ExamPaperLib eplTree) {
		this.eplTree = eplTree;
	}

	public ExamPaperDao getExamPaperDao() {
		return examPaperDao;
	}

	public void setExamPaperDao(ExamPaperDao examPaperDao) {
		this.examPaperDao = examPaperDao;
	}

	public int getSub_operate() {
		return sub_operate;
	}

	public void setSub_operate(int sub_operate) {
		this.sub_operate = sub_operate;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}

	public List<ExamPaper> getExamPapers1() {
		return examPapers1;
	}

	public void setExamPapers1(List<ExamPaper> examPapers1) {
		this.examPapers1 = examPapers1;
	}

	public ExamPaperBlock getEpBlock() {
		return epBlock;
	}

	public void setEpBlock(ExamPaperBlock epBlock) {
		this.epBlock = epBlock;
	}

	public QuestionLib getQlbTree() {
		return qlbTree;
	}

	public void setQlbTree(QuestionLib qlbTree) {
		this.qlbTree = qlbTree;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
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

	public List<ExamPaperBlock> getEpBlocks() {
		return epBlocks;
	}

	public void setEpBlocks(List<ExamPaperBlock> epBlocks) {
		this.epBlocks = epBlocks;
	}

	public ExampaperRandom getEpRandom1() {
		return epRandom1;
	}

	public void setEpRandom1(ExampaperRandom epRandom1) {
		this.epRandom1 = epRandom1;
	}

	public List<ExampaperRandom> getEpRandoms() {
		return epRandoms;
	}

	public void setEpRandoms(List<ExampaperRandom> epRandoms) {
		this.epRandoms = epRandoms;
	}

	public ExamPaperLib getEplTree() {
		return eplTree;
	}

	public String exampaper_details() throws ElException {
		examPaper = examPaperDao.getExamPaperById(examPaper.getId());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eplTree = examPaperDao.epLibTree(0,
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		else {
			eplTree = examPaperDao.epLibTree("op",
					getSessionIntValue(ElConstants.SESSION_USERID), -1, true);
		}
		epBlocks = examPaperDao.listEpBlockByEpid(examPaper.getId());
		if (null != epBlocks)
			for (int i = 0; i < epBlocks.size(); i++) {
				ExamPaperBlock block = epBlocks.get(i);
				if (block.getRandom() == 0) {
					block.setQuestions(examPaperDao
							.listEpBlockQusetionsByBepbId(block.getId()));
				} else {
					block
							.setEpRandom(examPaperDao.listEpbRandom(block
									.getId()));
				}
			}
		examPaper.setEpBlocks(epBlocks);
		return "exampaper_details";
	}
	
	public String exampaper_quizpaperviewall() throws ElException {

		examPaper = examPaperDao.getEPAllInfoById(examPaper.getId());

		
		return "exampaper_quizpaperviewall";
	}

	public List<ExamPaperLib> getEplTrees() {
		return eplTrees;
	}

	public void setEplTrees(List<ExamPaperLib> eplTrees) {
		this.eplTrees = eplTrees;
	}

	public List<ExampaperRandom> getEpRandoms1() {
		return epRandoms1;
	}

	public void setEpRandoms1(List<ExampaperRandom> epRandoms1) {
		this.epRandoms1 = epRandoms1;
	}
	
}
