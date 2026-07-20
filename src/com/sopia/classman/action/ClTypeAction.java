package com.sopia.classman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;

/**
 * 培训班类别管理
 * @author luocw
 *
 * @date Sep 12, 2011
 */
public class ClTypeAction extends BaseAction {
	private ElClTypeDao elClTypeDao;
	private ElClType cltype;
	private ElClType cltypeTree;
	private List<ElRole> roles;
	private RoleDao roleDao;
	
	private ELUser elUser;
	private String optype;
	private int class_sourse;

	public int getClass_sourse() {
		return class_sourse;
	}

	public void setClass_sourse(int class_sourse) {
		this.class_sourse = class_sourse;
	}

	public ElClType getCltypeTree() {
		return cltypeTree;
	}

	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}

	public String cltype_list() throws ElException {
//		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, false);
//		}else{
//			cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//					ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),false,"CLASS_OP_TYPE");
//		} 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "OP",ElConstants.TREE_FIANL, true);
		}
		return "cltype_list";
	}

	public String cltype_addInit() throws ElException {
//		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);

//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, false);
//		}else{
//			cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//					ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),false,"CLASS_OP_TYPE");
//		}
		
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		if (cltypeTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的培训班库");
			return "error";
		}
		return "cltype_add";
	}

	public String cltype_add() throws ElException {
		elClTypeDao.addCltype(cltype);
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("elclasstype");
		cltype=elClTypeDao.getClTypeById(cltype.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASSLIB,
				ElLoggerConstants.LOG_TYPE_ADD, cltype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,cltype.getId());
		return "cltype_add_success";
	}

	public String cltype_view() throws ElException {
//		cltype = elClTypeDao.getClTypeById(cltype.getId());
//		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,
//				ElConstants.TREE_FIANL, true);
		if(cltype==null||cltype.getId()<=0){	
			setElmessage("您需要查看的培训班类别不存在,请重新选择！");
			return "error";
		}
		cltype = elClTypeDao.getClTypeById(cltype.getId());
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, false);
//		}else{
//			cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//					ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),false,"CLASS_OP_TYPE"); 
//		}
		
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		cltype.setOpusers(elClTypeDao.getOpUsers("CLASS_OP_TYPE", cltype.getId()));
//		cltype.setUseusers(elClTypeDao.getOpUsers("CLASS_USE_TYPE", cltype.getId()));
		return "cltype_view";
	}
	
	public String cltype_delete_user() throws ElException {
		elClTypeDao.deleteOpusers(optype, elUser.getId(), cltype.getId());
		roleDao.checkUserfunc( elUser.getId(),"elclass_list","CLASS_OP_TYPE");
		roleDao.checkUserfunc( elUser.getId(),"elclass_addInit","CLASS_OP_TYPE");
		roleDao.checkUserfunc( elUser.getId(),"admin","CLASS_OP_TYPE");
		
		return null;
	}

	/**
	 * 培训班类别修改初始化
	 * @return
	 * @throws ElException
	 */
	public String cltype_alterInit() throws ElException {
		//超级管理员查询所有课程类别，否则按照管理权限查询
		cltype = elClTypeDao.getClTypeById(cltype.getId());
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, false);
//		}else{
//			cltypeTree = elClTypeDao.getCltypeTreeByPerOrShar(ElConstants.TREE_ROOT,
//					ElConstants.TREE_FIANL, true,getSessionIntValue(ElConstants.SESSION_USERID),false,"CLASS_OP_TYPE");
//		}
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		cltype.setOpusers(elClTypeDao.getOpUsers("CLASS_OP_TYPE", cltype.getId()));
//		cltype.setUseusers(elClTypeDao.getOpUsers("CLASS_USE_TYPE", cltype.getId()));
		
		return "cltype_alter";
	}

	public String cltype_alter() throws ElException {
//		elClTypeDao.alterCltype(cltype);
//		return "cltype_alter_success";
		if (cltype.getId() == 1) {
			cltype.setParent(new ElClType(0));
		}
		if(cltype.getParent()==null){
			cltype.setParent(new ElNode(1));
		}
		elClTypeDao.alterCltype(cltype);
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("elclasstype");
		cltype=elClTypeDao.getClTypeById(cltype.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASSLIB,
				ElLoggerConstants.LOG_TYPE_ALTER, cltype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,cltype.getId());
//		if (null != cltype.getOpusers()) {
//			for (int i = 0; i < cltype.getOpusers().size(); i++) {
//				//这样的写法不好，循环去操作数据会产生性能问题，由于时间关系先暂时参考试题库的代码这样处理（使用量不是很大的话也没什么问题）。
//				//当出现性能问题时，可以把这段代码改掉，减少数据库的链接次数和做批量处理。备注：luocw,类似于jiahj做法，后面改进
//				if (!elClTypeDao.checkOpUsers("CLASS_OP_TYPE", cltype.getOpusers().get(i).getId(), cltype.getId()))
//					elClTypeDao.addOpusers("CLASS_OP_TYPE", cltype.getOpusers().get(i).getId(), cltype.getId());
//					roleDao.setUserfunc(cltype.getOpusers().get(i).getId(),"elclass_list", 0);
//					roleDao.setUserfunc(cltype.getOpusers().get(i).getId(),"elclass_addInit", 0);
////					roleDao.setUserfunc(cltype.getOpusers().get(i).getId(),"elclass_listInit", 0);
////					roleDao.setUserfunc(cltype.getOpusers().get(i).getId(),"elclass_addInit", 0);
//					roleDao.setUserfunc(cltype.getOpusers().get(i).getId(),"admin", 0);
//				}
//		}
//		if (null != cltype.getUseusers()) {
//			for (int i = 0; i < cltype.getUseusers().size(); i++) {
//				if (!elClTypeDao.checkOpUsers("CLASS_USE_TYPE", cltype.getUseusers()
//						.get(i).getId(), cltype.getId()))
//					elClTypeDao.addOpusers("CLASS_USE_TYPE", cltype.getUseusers()
//							.get(i).getId(), cltype.getId());
//			}
//		}
		return "cltype_alter_success";
	}
	public String cltype_deleteInit() throws ElException {
		if(cltype.getId()==1){
			setElmessage("不能删除根类别!");
			return "error";
		}
		cltype = elClTypeDao.getClTypeById(cltype.getId());
		return "cltype_delete";
	}
	/**
	 * 删除培训班类别
	 * @return
	 * @throws ElException
	 */
	public String cltype_delete() throws ElException{
		//elClTypeDao.deleteCltype(cltype);
		if(cltype.getId()==1){
			setElmessage("不能删除根类别!");
			return "error";
		}
		if (class_sourse == 0) {
			//并入上级班级库
			cltype=elClTypeDao.getClTypeById(cltype.getId());
			elClTypeDao.setCtypeparent(cltype.getId(), cltype.getParent().getId());
			elClTypeDao.setClassparent(cltype.getId(), cltype.getParent().getId());
//			elClTypeDao.deleteCltype(cltype);
			elClTypeDao.deleteCltypeNot(cltype.getId());
		}else{
			//与本班级库同时删除
//			elClTypeDao.deleteCtypeAndSub(cltype.getId());
			elClTypeDao.deleteCtypeAndSubNot(cltype.getId());
		}
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("elclasstype");
		cltype=elClTypeDao.getClTypeById(cltype.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_CLASSLIB,
				ElLoggerConstants.LOG_TYPE_DELETE, cltype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,cltype.getId());
		return "cltype_delete_success";
	}
	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}

	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}

	public ElClType getCltype() {
		return cltype;
	}

	public void setCltype(ElClType cltype) {
		this.cltype = cltype;
	}

	public List<ElRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}
	

}
