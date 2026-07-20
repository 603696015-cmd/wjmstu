package com.sopia.knowledgeManage.action;

import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeManage.dao.KnowledgeTreeDao;
import com.sopia.knowledgeManage.entities.KnowledgeTree;
import com.sopia.schedule.action.xialajibie.SelectLevelAction;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

/**
 * 知识树的管理
 * @author Administrator
 *
 */
public class KnowledgeManageTreeAction extends BaseAction{
	private static final Log logger = LogFactory.getLog(KnowledgeManageTreeAction.class);
	private KnowledgeTreeDao knowledgeTreeDao;
	private KnowledgeTree knowledgeTree;
	private KnowledgeTree klTree;
	private List<KnowledgeTree> knowledgeTrees;
	private List<ELUser> elUsers;
	private IndexDataUtil indexDataUtil;
	private int sub_operate;
	private String optype;
	
	public String list_knowledgeTree_childs() throws ElException{
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			knowledgeTrees = knowledgeTreeDao.listKnowledgeTreeChildsByPId(knowledgeTree.getId());
			String d = "[";
			if (null != knowledgeTrees && knowledgeTrees.size() > 0) {
				for (int i = 0; i < knowledgeTrees.size(); i++) {
					KnowledgeTree dep = knowledgeTrees.get(i);
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
	
	public String listKnowledgeTree() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			knowledgeTree = knowledgeTreeDao.getknowledgeTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			knowledgeTree = knowledgeTreeDao.getknowledgeTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		return "listKnowledgeTree";
	}
	
	public String addKnowledgeTreeInit() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			knowledgeTree = knowledgeTreeDao.getknowledgeTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			knowledgeTree = knowledgeTreeDao.getknowledgeTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (knowledgeTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的知识类别");
			return "error";
		}
		return "addKnowledgeTreeInit";
	}
	
	public String addKnowledgeTree() throws ElException{
		// 先检测部门编号是否存在
		if (knowledgeTreeDao.checkKnowledgeTreeBh(klTree.getBh())) {
			setElmessage("该知识类别编号已经存在，请重新选择");
			return this.addKnowledgeTreeInit();
		}
		if (klTree.getParent()==null||klTree.getParent().getId()<=0) {
			setElmessage("请选择有效的上级节点");
			return this.addKnowledgeTreeInit();
		}
		if (klTree.getParent() == null) {
			// 因为ajax树有点缺陷
			klTree.setParent(new ElNode(klTree.getId()));
		}
		knowledgeTreeDao.addKnowledgeTree(klTree);
		((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
		.updatetlrid("KnowledgeTree");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_KNOWLEDGETREE,
				ElLoggerConstants.LOG_TYPE_ADD, klTree.getName(),
				ElLoggerConstants.LOG_RES_SUCC, klTree.getId());// **//**//
		return "addKnowledgeTree_success";
	}
	
	public String viewKnowledgeTree() throws ElException{
		if("ajax".equals(optype)){
			klTree = knowledgeTreeDao.getKnowledgeTreeById(klTree.getId());
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				String d= "{\"id\":\"" + klTree.getId() + "\",\"name\":\"" + klTree.getName()
								+ "\",\"bh\":\"" + klTree.getBh() + "\"}";
				localPrintWriter.println(d);
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				logger.error("ajax知识类别查看错误",e);
			}
			return null;
		}
		if(klTree==null||klTree.getId()<=0)
		{	
			setElmessage("您需要查看的知识类别不存在,请重新选择！");
			return this.listKnowledgeTree();
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			knowledgeTree = knowledgeTreeDao.getknowledgeTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			knowledgeTree = knowledgeTreeDao.getknowledgeTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		klTree = knowledgeTreeDao.getKnowledgeTreeById(klTree.getId());
		klTree.setOpusers(knowledgeTreeDao.getOpUsers("op", klTree.getId()));
		return "viewKnowledgeTree_success";
	}
	
	public String alterKnowledgeTreeInit() throws ElException{
		if(klTree==null||klTree.getId()<=0){
			setElmessage("请选择有效的节点");
			return "error";
		}
		klTree = knowledgeTreeDao.getKnowledgeTreeById(klTree.getId());
		klTree.setParent(knowledgeTreeDao.getKnowledgeTreeById(klTree.getParent().getId()));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			knowledgeTree = knowledgeTreeDao.getknowledgeTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			knowledgeTree = knowledgeTreeDao.getknowledgeTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			if(knowledgeTree!=null&&knowledgeTree.getChild()!=null)
				for (int i = 0; i < knowledgeTree.getChild().size(); i++) {
					if(klTree.getId()==knowledgeTree.getChild().get(i).getId()){
						setElmessage("被分配的节点（二级节点）不容许修改，选择子节点");
						return "error";
					}
				}
		}
		elUsers = knowledgeTreeDao.getEUsByKnowledgeTreeid(klTree.getId());
		klTree.setOpusers(knowledgeTreeDao.getOpUsers("op", klTree.getId()));
		if (knowledgeTree!=null&&knowledgeTree.getChild()!=null&&knowledgeTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的知识类别");
			return "error";
		}
		return "alterKnowledgeTreeInit";
	}
	
	public String alterKnowledgeTree() throws ElException{
		// 先检测部门编号是否存在
		// 先查出本部门的编号，因为要排除他
		KnowledgeTree d = knowledgeTreeDao.getKnowledgeTreeById(klTree.getId());
		if (!d.getBh().equals(klTree.getBh())) {
			if (knowledgeTreeDao.checkKnowledgeTreeBh(klTree.getBh())) {
				setElmessage("该知识类别编号已经存在，请重新选择。");
				return this.alterKnowledgeTreeInit();
			}
		}
		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
		if(ens.checkNodeisChild(klTree.getId(), klTree.getParent().getId(), "KnowledgeTree")){
			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
			return this.alterKnowledgeTreeInit();
		}
		knowledgeTreeDao.alterKnowledgeTree(klTree); // hec 注释
		//若父节点发生变化则去整理整个树的左右id
		if(klTree.getParent().getId()!=d.getParent().getId()){
			ens.updatetlrid("KnowledgeTree");
		}
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_DEP);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_KNOWLEDGETREE,
				ElLoggerConstants.LOG_TYPE_ALTER, klTree.getName(),
				ElLoggerConstants.LOG_RES_SUCC, klTree.getId());
		
		return "alterKnowledgeTree_success";
	}
	
	public String deleteKnowledgeTreeInit() throws ElException{
		if(klTree.getId()==1){
			setElmessage("不能删除根下拉选项");
			return "error";
		}
		klTree = knowledgeTreeDao.getKnowledgeTreeById(klTree.getId());
		return "deleteKnowledgeTreeInit_success";
	}
	
	public String deleteKnowledgeTree() throws ElException{
		if(klTree.getId()==1){
			setElmessage("不能删除根知识类别");
			return "error";
		}
		if (sub_operate == 0) {
			// 并入上级部门(首先获取该节点的父节点，然后更新该节点的子节点的父节点为该节点的父节点，然后更新该节点下的人员到该节点的父节点,最后删除该节点)
			klTree = knowledgeTreeDao.getKnowledgeTreeById(klTree.getId());
			knowledgeTreeDao.deleteDep(klTree.getId(), klTree.getParent().getId());
		} else {
			// 与本部门同时删除
			knowledgeTreeDao.deleteKnowledgeTreeAndSubNot(klTree.getId());
		}
		klTree = knowledgeTreeDao.getKnowledgeTreeById(klTree.getId());
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
		.updatetlrid("knowledgeTree");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_KNOWLEDGETREE,
				ElLoggerConstants.LOG_TYPE_DELETE, klTree.getName(),
				ElLoggerConstants.LOG_RES_SUCC, klTree.getId());
		
		return "deleteKnowledgeTree_success";
	}
	
	
	/////////////////////
	//gets  AND  sets
	public KnowledgeTree getKnowledgeTree() {
		return knowledgeTree;
	}
	public void setKnowledgeTree(KnowledgeTree knowledgeTree) {
		this.knowledgeTree = knowledgeTree;
	}
	public KnowledgeTree getKlTree() {
		return klTree;
	}
	public void setKlTree(KnowledgeTree klTree) {
		this.klTree = klTree;
	}

	public KnowledgeTreeDao getKnowledgeTreeDao() {
		return knowledgeTreeDao;
	}

	public void setKnowledgeTreeDao(KnowledgeTreeDao knowledgeTreeDao) {
		this.knowledgeTreeDao = knowledgeTreeDao;
	}

	public List<KnowledgeTree> getKnowledgeTrees() {
		return knowledgeTrees;
	}

	public void setKnowledgeTrees(List<KnowledgeTree> knowledgeTrees) {
		this.knowledgeTrees = knowledgeTrees;
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

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	

}
