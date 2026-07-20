package com.sopia.lable.action;

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
import com.sopia.lable.dao.LableTreeDao;
import com.sopia.lable.entites.LableTree;

/**
 * 自定义类别树
 * @author Administrator
 *
 */
public class LableTreeAction extends BaseAction{
	private static final Log logger = LogFactory.getLog(LableTreeAction.class);
	
	private LableTreeDao lableTreeDao;
	private IndexDataUtil indexDataUtil;
	private List<LableTree> lableTrees;
	private LableTree lableTree;
	private LableTree klTree;
	private int sub_operate;
	private List<ELUser> elUsers;
	private String optype;
	
	/////////////actions
	public String list_lableTree_childs() throws ElException {

		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			lableTrees = lableTreeDao.listdepChildsByPId(lableTree.getId());
			String d = "[";
			if (null != lableTrees && lableTrees.size() > 0) {
				for (int i = 0; i < lableTrees.size(); i++) {
					LableTree dep = lableTrees.get(i);
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
			logger.error("获取lable树错误",e);
		}
		return null;
	}
	
	/**
	 * 树列表
	 * @return
	 * @throws ElException
	 */
	public String list_lableTree() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			lableTree = lableTreeDao.getLableTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			lableTree = lableTreeDao.getLableTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		return "list_lableTree";
	}
	public String addLableTreeInit() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			lableTree = lableTreeDao.getLableTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			lableTree = lableTreeDao.getLableTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (lableTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的自定义标签类别");
			return "error";
		}
		return "addLableTreeInit";
	}
	
	public String addLableTree() throws ElException{
		// 先检测部门编号是否存在
		if (lableTreeDao.checkLableTreeBh(klTree.getBh())) {
			setElmessage("该自定义标签类别编号已经存在，请重新选择");
			return this.addLableTreeInit();
		}
		if (klTree.getParent()==null||klTree.getParent().getId()<=0) {
			setElmessage("请选择有效的上级节点");
			return this.addLableTreeInit();
		}
		if (klTree.getParent() == null) {
			// 因为ajax树有点缺陷
			klTree.setParent(new ElNode(klTree.getId()));
		}
		lableTreeDao.addLableTree(klTree);
		((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
		.updatetlrid("lableTree");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_LABLETREE,
				ElLoggerConstants.LOG_TYPE_ADD, klTree.getName(),
				ElLoggerConstants.LOG_RES_SUCC, klTree.getId());// **//**//
		return "addLableTree_success";
	}
	
	public String viewLableTree() throws ElException{
		if("ajax".equals(optype)){
			klTree = lableTreeDao.getLableTreeById(klTree.getId());
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				String d= "{\"id\":\"" + klTree.getId() + "\",\"name\":\"" + klTree.getName()
								+ "\",\"bh\":\"" + klTree.getBh() + "\"}";
				localPrintWriter.println(d);
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				logger.error("ajax自定义标签类别查看错误",e);
			}
			return null;
		}
		if(klTree==null||klTree.getId()<=0)
		{	
			setElmessage("您需要查看的自定义标签类别不存在,请重新选择！");
			return this.list_lableTree();
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			lableTree = lableTreeDao.getLableTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			lableTree = lableTreeDao.getLableTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		klTree = lableTreeDao.getLableTreeById(klTree.getId());
//		lTree.setOpusers(lableTreeDao.getOpUsers("op", lTree.getId()));
		return "viewLableTree_success";
	}
	
	public String alterLableTreeInit() throws ElException{
		if(klTree==null||klTree.getId()<=0){
			setElmessage("请选择有效的节点");
			return "error";
		}
		klTree = lableTreeDao.getLableTreeById(klTree.getId());
		klTree.setParent(lableTreeDao.getLableTreeById(klTree.getParent().getId()));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			lableTree = lableTreeDao.getLableTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			lableTree = lableTreeDao.getLableTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			if(lableTree!=null&&lableTree.getChild()!=null)
				for (int i = 0; i < lableTree.getChild().size(); i++) {
					if(klTree.getId()==lableTree.getChild().get(i).getId()){
						setElmessage("被分配的节点（二级节点）不容许修改，选择子节点");
						return "error";
					}
				}
		}
//		elUsers = knowledgeTreeDao.getEUsByKnowledgeTreeid(klTree.getId());
//		klTree.setOpusers(knowledgeTreeDao.getOpUsers("op", klTree.getId()));
		if (lableTree!=null&&lableTree.getChild()!=null&&lableTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的自定义标签类别");
			return "error";
		}
		return "alterLableTreeInit";
	}
	
	public String alterLableTree() throws ElException{
		// 先检测部门编号是否存在
		// 先查出本部门的编号，因为要排除他
		LableTree d = lableTreeDao.getLableTreeById(klTree.getId());
		if (!d.getBh().equals(klTree.getBh())) {
			if (lableTreeDao.checkLableTreeBh(klTree.getBh())) {
				setElmessage("该自定义标签类别编号已经存在，请重新选择。");
				return this.alterLableTreeInit();
			}
		}
		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
		if(ens.checkNodeisChild(klTree.getId(), klTree.getParent().getId(), "lableTree")){
			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
			return this.alterLableTreeInit();
		}
		lableTreeDao.alterLableTree(klTree); // hec 注释
		//若父节点发生变化则去整理整个树的左右id
		if(klTree.getParent().getId()!=d.getParent().getId()){
			ens.updatetlrid("lableTree");
		}
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_DEP);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_LABLETREE,
				ElLoggerConstants.LOG_TYPE_ALTER, klTree.getName(),
				ElLoggerConstants.LOG_RES_SUCC, klTree.getId());
		
		return "alterLableTree_success";
	}
	
	public String deleteLableTreeInit() throws ElException{
		if(klTree.getId()==1){
			setElmessage("不能删除根自定义标签类别");
			return "error";
		}
		klTree = lableTreeDao.getLableTreeById(klTree.getId());
		return "deleteLableTreeInit";
	}
	
	public String deleteLableTree() throws ElException{
		if(klTree.getId()==1){
			setElmessage("不能删除根自定义标签类别");
			return "error";
		}
		if (sub_operate == 0) {
			// 并入上级部门(首先获取该节点的父节点，然后更新该节点的子节点的父节点为该节点的父节点，然后更新该节点下的人员到该节点的父节点,最后删除该节点)
			klTree = lableTreeDao.getLableTreeById(klTree.getId());
			lableTreeDao.deleteDep(klTree.getId(), klTree.getParent().getId());
		} else {
			// 与本部门同时删除
			lableTreeDao.deleteLableTreeAndSubNot(klTree.getId());
		}
		klTree = lableTreeDao.getLableTreeById(klTree.getId());
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
		.updatetlrid("lableTree");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_LABLETREE,
				ElLoggerConstants.LOG_TYPE_DELETE, klTree.getName(),
				ElLoggerConstants.LOG_RES_SUCC, klTree.getId());
		
		return "deleteLableTree_success";
	}
	
	
	///////////////////
	
	
	
	
	public LableTreeDao getLableTreeDao() {
		return lableTreeDao;
	}
	public void setLableTreeDao(LableTreeDao lableTreeDao) {
		this.lableTreeDao = lableTreeDao;
	}
	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}
	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}
	public List<LableTree> getLableTrees() {
		return lableTrees;
	}
	public void setLableTrees(List<LableTree> lableTrees) {
		this.lableTrees = lableTrees;
	}
	public LableTree getLableTree() {
		return lableTree;
	}
	public void setLableTree(LableTree lableTree) {
		this.lableTree = lableTree;
	}
	public int getSub_operate() {
		return sub_operate;
	}
	public void setSub_operate(int sub_operate) {
		this.sub_operate = sub_operate;
	}
	public List<ELUser> getElUsers() {
		return elUsers;
	}
	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
	}

	public LableTree getKlTree() {
		return klTree;
	}

	public void setKlTree(LableTree klTree) {
		this.klTree = klTree;
	}
	
	
	
	

}
