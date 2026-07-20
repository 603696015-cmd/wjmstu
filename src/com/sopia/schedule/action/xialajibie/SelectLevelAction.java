package com.sopia.schedule.action.xialajibie;

import java.io.IOException;
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
import com.sopia.duman.action.DepartmentAction;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.schedule.TagsUtil;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.dao.xialajibie.SelectLevelDao;
import com.sopia.schedule.entities.xialajibie.SelectLevel;
/**
 * 下拉级别管理
 * @author Administrator
 *
 */
public class SelectLevelAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(SelectLevelAction.class);
	private SelectLevelDao selectLevelDao;
	private TagsDao tagsDao;
	private IndexDataUtil indexDataUtil;
	private SelectLevel selectLevel;
	private SelectLevel selectLevelTree;
	private List<SelectLevel> selectLevels;
	private int sub_operate;
	private List<ELUser> elUsers;
	private int showDialog;
	private String optype;
	

	public String list_selectlevel_childs() throws ElException {

		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			selectLevels = selectLevelDao.listdepChildsByPId(selectLevel.getId());
			String d = "[";
			if (null != selectLevels && selectLevels.size() > 0) {
				for (int i = 0; i < selectLevels.size(); i++) {
					SelectLevel dep = selectLevels.get(i);
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
	
	/**
	 * 下拉选项列表
	 * @return
	 * @throws ElException
	 */
	public String list_selectLevel() throws ElException{
		String resultPage = "";
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			selectLevelTree = selectLevelDao.getSelectLevelTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			selectLevelTree = selectLevelDao.getSelectLevelTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(showDialog == 1){
			resultPage = "list_selectLevel_showDialog_success";
		}else{
			resultPage = "list_selectLevel_success";
		}
		return resultPage;
	}
	
	/**
	 * 添加下拉选项
	 * @return
	 * @throws ElException
	 */
	public String select_level_addInit() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			selectLevelTree = selectLevelDao.getSelectLevelTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			selectLevelTree = selectLevelDao.getSelectLevelTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (selectLevelTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的下拉选项类别");
			return "error";
		}
		return "select_level_addInit_success";
	}
	
	public String select_level_add() throws ElException{
		// 先检测部门编号是否存在
		if (selectLevelDao.checkSelectLevelBh(selectLevel.getBh())) {
			setElmessage("该下拉选项编号已经存在，请重新选择");
			return this.select_level_addInit();
		}
		if (selectLevel.getParent()==null||selectLevel.getParent().getId()<=0) {
			setElmessage("请选择有效的上级节点");
			return this.select_level_addInit();
		}
		if (selectLevel.getParent() == null) {
			// 因为ajax树有点缺陷
			selectLevel.setParent(new ElNode(selectLevel.getId()));
		}
		selectLevelDao.addSelectLevel(selectLevel);
		((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
		.updatetlrid("selectLevel");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_SELECTLEVEL,
				ElLoggerConstants.LOG_TYPE_ADD, selectLevel.getName(),
				ElLoggerConstants.LOG_RES_SUCC, selectLevel.getId());// **//**//
		return "select_level_add_success";
	}
	
	public String select_level_view() throws ElException{
		if("ajax".equals(optype)){
			selectLevel = selectLevelDao.getDepById(selectLevel.getId());
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				String d= "{\"id\":\"" + selectLevel.getId() + "\",\"name\":\"" + selectLevel.getName()
								+ "\",\"bh\":\"" + selectLevel.getBh() + "\"}";
				localPrintWriter.println(d);
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				logger.error("ajax下拉选项查看错误",e);
			}
			return null;
		}
		if(selectLevel==null||selectLevel.getId()<=0)
		{	
			setElmessage("您需要查看的下拉选项不存在,请重新选择！");
			return this.list_selectLevel();
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			selectLevelTree = selectLevelDao.getSelectLevelTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			selectLevelTree = selectLevelDao.getSelectLevelTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		selectLevel = selectLevelDao.getDepById(selectLevel.getId());
		selectLevel.setOpusers(selectLevelDao.getOpUsers("op", selectLevel.getId()));
		return "select_level_view_success";
	}
	
	public String select_level_alterInit() throws ElException{
		if(selectLevel==null||selectLevel.getId()<=0){
			setElmessage("请选择有效的节点");
			return "error";
		}
		selectLevel = selectLevelDao.getDepById(selectLevel.getId());
		selectLevel.setParent(selectLevelDao.getDepById(selectLevel.getParent().getId()));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			selectLevelTree = selectLevelDao.getSelectLevelTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			selectLevelTree = selectLevelDao.getSelectLevelTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			if(selectLevelTree!=null&&selectLevelTree.getChild()!=null)
				for (int i = 0; i < selectLevelTree.getChild().size(); i++) {
					if(selectLevel.getId()==selectLevelTree.getChild().get(i).getId()){
						setElmessage("被分配的节点（二级节点）不容许修改，选择子节点");
						return "error";
					}
				}
		}
		elUsers = selectLevelDao.getEUsBySelectLevelid(selectLevel.getId());
		selectLevel.setOpusers(selectLevelDao.getOpUsers("op", selectLevel.getId()));
		if (selectLevelTree!=null&&selectLevelTree.getChild()!=null&&selectLevelTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的下拉选项");
			return "error";
		}
		return "select_level_alterInit_success";
	}
	
	public String select_level_alter() throws ElException{
		// 先检测部门编号是否存在
		// 先查出本部门的编号，因为要排除他
		SelectLevel d = selectLevelDao.getDepById(selectLevel.getId());
		if (!d.getBh().equals(selectLevel.getBh())) {
			if (selectLevelDao.checkSelectLevelBh(selectLevel.getBh())) {
				setElmessage("该下拉选项编号已经存在，请重新选择。");
				return this.select_level_alterInit();
			}
		}
		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
		if(ens.checkNodeisChild(selectLevel.getId(), selectLevel.getParent().getId(), "selectLevel")){
			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
			return this.select_level_alterInit();
		}
		selectLevelDao.alterSelectLevel(selectLevel); // hec 注释
		//若父节点发生变化则去整理整个树的左右id
		if(selectLevel.getParent().getId()!=d.getParent().getId()){
			ens.updatetlrid("selectLevel");
		}
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_DEP);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_SELECTLEVEL,
				ElLoggerConstants.LOG_TYPE_ALTER, selectLevel.getName(),
				ElLoggerConstants.LOG_RES_SUCC, selectLevel.getId());
		
		return "select_level_alter_success";
	}
	
	public String select_level_deleteInit() throws ElException{
		if(selectLevel.getId()==1){
			setElmessage("不能删除根下拉选项");
			return "error";
		}
		selectLevel = selectLevelDao.getDepById(selectLevel.getId());
		return "select_level_deleteInit_success";
	}
	
	public String select_level_delete() throws ElException{
		if(selectLevel.getId()==1){
			setElmessage("不能删除根下拉选项");
			return "error";
		}
		if (sub_operate == 0) {
			// 并入上级部门(首先获取该节点的父节点，然后更新该节点的子节点的父节点为该节点的父节点，然后更新该节点下的人员到该节点的父节点,最后删除该节点)
			selectLevel = selectLevelDao.getDepById(selectLevel.getId());
			selectLevelDao.deleteDep(selectLevel.getId(), selectLevel.getParent().getId());
		} else {
			// 与本部门同时删除
			selectLevelDao.deleteSelectLevelAndSubNot(selectLevel.getId());
		}
		selectLevel = selectLevelDao.getDepById(selectLevel.getId());
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
		.updatetlrid("selectLevel");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_SELECTLEVEL,
				ElLoggerConstants.LOG_TYPE_DELETE, selectLevel.getName(),
				ElLoggerConstants.LOG_RES_SUCC, selectLevel.getId());
		return "select_level_delete_success";
	}
	
	
	public String checkJibieshu() throws ElException{
		int icount = selectLevelDao.checkJibieshu(selectLevel.getId());
		
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + icount + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getselectLevelListByParentId() throws ElException{
		selectLevels = tagsDao.getSelectLevelById(selectLevel.getId());
		String json = TagsUtil.ToGson(selectLevels);
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + json + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getParentid() throws ElException{
		selectLevel = selectLevelDao.getDepById(selectLevel.getId());
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + selectLevel.getParentid() + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	//////////////////////////
	//get(),set()
	public SelectLevelDao getSelectLevelDao() {
		return selectLevelDao;
	}

	public void setSelectLevelDao(SelectLevelDao selectLevelDao) {
		this.selectLevelDao = selectLevelDao;
	}

	public SelectLevel getSelectLevel() {
		return selectLevel;
	}

	public void setSelectLevel(SelectLevel selectLevel) {
		this.selectLevel = selectLevel;
	}

	public SelectLevel getSelectLevelTree() {
		return selectLevelTree;
	}

	public void setSelectLevelTree(SelectLevel selectLevelTree) {
		this.selectLevelTree = selectLevelTree;
	}

	public List<SelectLevel> getSelectLevels() {
		return selectLevels;
	}

	public void setSelectLevels(List<SelectLevel> selectLevels) {
		this.selectLevels = selectLevels;
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

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public int getShowDialog() {
		return showDialog;
	}

	public void setShowDialog(int showDialog) {
		this.showDialog = showDialog;
	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public TagsDao getTagsDao() {
		return tagsDao;
	}

	public void setTagsDao(TagsDao tagsDao) {
		this.tagsDao = tagsDao;
	}

}
