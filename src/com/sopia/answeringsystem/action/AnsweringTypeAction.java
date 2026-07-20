package com.sopia.answeringsystem.action;

import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.answeringsystem.dao.AnsweringTypeDao;
import com.sopia.answeringsystem.entities.AnsweringType;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeManage.entities.KnowledgeTree;

public class AnsweringTypeAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(AnsweringTypeAction.class);
	
	private AnsweringTypeDao answeringTypeDao;
	private List<AnsweringType> answeringTypeTrees;
	private AnsweringType answeringTypeTree;
	private AnsweringType answeringType;
	private String optype;
	private List<ELUser> elUsers;
	private IndexDataUtil indexDataUtil;
	private int sub_operate;
	
	//点击获取子节点
	public String list_answeringTypeTree_childs() throws ElException{
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			answeringTypeTrees = answeringTypeDao.listAnsweringTypeTreeChildsByPId(answeringTypeTree.getId());
			String d = "[";
			if (null != answeringTypeTrees && answeringTypeTrees.size() > 0) {
				for (int i = 0; i < answeringTypeTrees.size(); i++) {
					AnsweringType dep = answeringTypeTrees.get(i);
					String name = dep.getName();
					if (name != null)
						name = name.replaceAll("\"", "\\\\\"");
					d += "{\"id\":\"" + dep.getId() + "\",\"name\":\"" + name
							+ "\",\"lid\":\""
							+ dep.getLid() + "\",\"rid\":\""
							+ dep.getRid() + "\",\"bh\":\"" + dep.getBh() + "\",\"ccnt\":\""
							+ dep.getClassCount() + "\"},";
				}
				d = d.length() > 0 ? d.substring(0, d.length() - 1) : d;
				d += "]";
			} else
				d += "]";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("获取下级下拉选项错误",e);
		}
		return null;
	}
	//类别管理列表
	public String listAnsweringTypeTree() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		return "listAnsweringTypeTree";
	}
	//初始化添加问答类别
	public String addAnsweringTypeTreeInit() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (answeringType.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的问答类别");
			return "error";
		}
		return "addAnsweringTypeTreeInit";
	}
	public String addAnsweringTypeTree() throws ElException{
		// 先检测部门编号是否存在
		if (answeringTypeDao.checkAnsweringTypeTreeBh(answeringTypeTree.getBh())) {
			setElmessage("该问答类别编号已经存在，请重新选择");
			return this.addAnsweringTypeTreeInit();
		}
		if (answeringTypeTree.getParent()==null||answeringTypeTree.getParent().getId()<=0) {
			setElmessage("请选择有效的上级节点");
			return this.addAnsweringTypeTreeInit();
		}
		if (answeringTypeTree.getParent() == null) {
			// 因为ajax树有点缺陷
			answeringTypeTree.setParent(new ElNode(answeringTypeTree.getId()));
		}
		answeringTypeDao.addAnsweringTypeTree(answeringTypeTree);
		((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
		.updatetlrid("AnsweringType");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_ANSWERINGTYPE,
				ElLoggerConstants.LOG_TYPE_ADD, answeringTypeTree.getName(),
				ElLoggerConstants.LOG_RES_SUCC, answeringTypeTree.getId());// **//**//
		return "addAnsweringTypeTree_success";
	}
	//查看问答类别
	public String viewAnsweringTypeTree() throws ElException{
		if("ajax".equals(optype)){
			answeringTypeTree = answeringTypeDao.getAnsweringTypeTreeById(answeringTypeTree.getId());
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				String d= "{\"id\":\"" + answeringTypeTree.getId() + "\",\"name\":\"" + answeringTypeTree.getName()
								+ "\",\"bh\":\"" + answeringTypeTree.getBh() + "\"}";
				localPrintWriter.println(d);
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				logger.error("ajax问答类别查看错误",e);
			}
			return null;
		}
		if(answeringTypeTree==null||answeringTypeTree.getId()<=0)
		{	
			setElmessage("您需要查看的问答类别不存在,请重新选择！");
			return this.listAnsweringTypeTree();
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		answeringTypeTree = answeringTypeDao.getAnsweringTypeTreeById(answeringTypeTree.getId());
		answeringTypeTree.setOpusers(answeringTypeDao.getOpUsers("op", answeringTypeTree.getId()));
		return "viewAnsweringTypeTree";
	}
	//修改问答类别初始化
	public String alterAnsweringTypeTreeInit() throws ElException{
		if(answeringTypeTree==null||answeringTypeTree.getId()<=0){
			setElmessage("请选择有效的节点");
			return "error";
		}
		answeringTypeTree = answeringTypeDao.getAnsweringTypeTreeById(answeringTypeTree.getId());
		answeringTypeTree.setParent(answeringTypeDao.getAnsweringTypeTreeById(answeringTypeTree.getParent().getId()));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			answeringType = answeringTypeDao.getAnsweringTypeTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			if(answeringTypeTree!=null&&answeringTypeTree.getChild()!=null)
				for (int i = 0; i < answeringTypeTree.getChild().size(); i++) {
					if(answeringTypeTree.getId()==answeringTypeTree.getChild().get(i).getId()){
						setElmessage("被分配的节点（二级节点）不容许修改，选择子节点");
						return "error";
					}
				}
		}
		elUsers = answeringTypeDao.getEUsByAnsweringTypeTreeid(answeringTypeTree.getId());
		answeringTypeTree.setOpusers(answeringTypeDao.getOpUsers("op", answeringTypeTree.getId()));
		if (answeringTypeTree!=null&&answeringTypeTree.getChild()!=null&&answeringTypeTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的问答类别");
			return "error";
		}
		return "alterAnsweringTypeTreeInit";
	}
	//修改问答类别
	public String alterAnsweringTypeTree() throws ElException{
		// 先检测部门编号是否存在
		// 先查出本部门的编号，因为要排除他
		AnsweringType d = answeringTypeDao.getAnsweringTypeTreeById(answeringTypeTree.getId());
		if (!d.getBh().equals(answeringTypeTree.getBh())) {
			if (answeringTypeDao.checkAnsweringTypeTreeBh(answeringTypeTree.getBh())) {
				setElmessage("该问答类别编号已经存在，请重新选择。");
				return this.alterAnsweringTypeTreeInit();
			}
		}
		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
		if(ens.checkNodeisChild(answeringTypeTree.getId(), answeringTypeTree.getParent().getId(), "answeringType")){
			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
			return this.alterAnsweringTypeTreeInit();
		}
		answeringTypeDao.alterAnsweringTypeTree(answeringTypeTree); // hec 注释
		//若父节点发生变化则去整理整个树的左右id
		if(answeringTypeTree.getParent().getId()!=d.getParent().getId()){
			ens.updatetlrid("answeringType");
		}
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_DEP);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_ANSWERINGTYPE,
				ElLoggerConstants.LOG_TYPE_ALTER, answeringTypeTree.getName(),
				ElLoggerConstants.LOG_RES_SUCC, answeringTypeTree.getId());
		return "alterAnsweringTypeTree_success";
	}
	//删除问答类别初始化
	public String deleteAnsweringTypeTreeInit() throws ElException{
		if(answeringTypeTree.getId()==1){
			setElmessage("不能删除根下拉选项");
			return "error";
		}
		answeringTypeTree = answeringTypeDao.getAnsweringTypeTreeById(answeringTypeTree.getId());
		return "deleteAnsweringTypeTreeInit";
	}
	//删除问答类别
	public String deleteAnsweringTypeTree() throws ElException{
		if(answeringTypeTree.getId()==1){
			setElmessage("不能删除根问答类别");
			return "error";
		}
		if (sub_operate == 0) {
			// 并入上级部门(首先获取该节点的父节点，然后更新该节点的子节点的父节点为该节点的父节点，然后更新该节点下的人员到该节点的父节点,最后删除该节点)
			answeringTypeTree = answeringTypeDao.getAnsweringTypeTreeById(answeringTypeTree.getId());
			answeringTypeDao.deleteDep(answeringTypeTree.getId(), answeringTypeTree.getParent().getId());
		} else {
			// 与本部门同时删除
			answeringTypeDao.deleteAnsweringTypeTreeAndSubNot(answeringTypeTree.getId());
		}
		answeringTypeTree = answeringTypeDao.getAnsweringTypeTreeById(answeringTypeTree.getId());
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
		.updatetlrid("answeringType");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_ANSWERINGTYPE,
				ElLoggerConstants.LOG_TYPE_DELETE, answeringTypeTree.getName(),
				ElLoggerConstants.LOG_RES_SUCC, answeringTypeTree.getId());
		
		return "deleteAnsweringTypeTree_success";
	}

	
	
	public AnsweringTypeDao getAnsweringTypeDao() {
		return answeringTypeDao;
	}

	public void setAnsweringTypeDao(AnsweringTypeDao answeringTypeDao) {
		this.answeringTypeDao = answeringTypeDao;
	}
	public AnsweringType getAnsweringTypeTree() {
		return answeringTypeTree;
	}
	public void setAnsweringTypeTree(AnsweringType answeringTypeTree) {
		this.answeringTypeTree = answeringTypeTree;
	}
	public List<AnsweringType> getAnsweringTypeTrees() {
		return answeringTypeTrees;
	}
	public void setAnsweringTypeTrees(List<AnsweringType> answeringTypeTrees) {
		this.answeringTypeTrees = answeringTypeTrees;
	}
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
	}
	public List<ELUser> getElUsers() {
		return elUsers;
	}
	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}
	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}
	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}
	public int getSub_operate() {
		return sub_operate;
	}
	public void setSub_operate(int sub_operate) {
		this.sub_operate = sub_operate;
	}
	public AnsweringType getAnsweringType() {
		return answeringType;
	}
	public void setAnsweringType(AnsweringType answeringType) {
		this.answeringType = answeringType;
	}


	
	
	
}
