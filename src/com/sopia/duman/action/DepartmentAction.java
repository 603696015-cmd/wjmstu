package com.sopia.duman.action;

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
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

public class DepartmentAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(DepartmentAction.class);
	private List<Department> departments;
	private Department department;
	private Department depTree;
	// private Department depTree_;
	private int sub_operate;
	private List<ELUser> elUsers;

	private ELUser elUser;
	private String optype;
	private List<Department> depsp;
	private IndexDataUtil indexDataUtil;
	
	private int roleid;
	
	
	private int depid;
	private String check_json_result;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	
	public List<BaseDatat> getJingzhongs() {
		return jingzhongs;
	}

	public void setJingzhongs(List<BaseDatat> jingzhongs) {
		this.jingzhongs = jingzhongs;
	}

	public List<BaseDatat> getZhiwus() {
		return zhiwus;
	}

	public void setZhiwus(List<BaseDatat> zhiwus) {
		this.zhiwus = zhiwus;
	}

	public List<BaseDatat> getZhijis() {
		return zhijis;
	}

	public void setZhijis(List<BaseDatat> zhijis) {
		this.zhijis = zhijis;
	}

	public List<BaseDatat> getGangweis() {
		return gangweis;
	}

	public void setGangweis(List<BaseDatat> gangweis) {
		this.gangweis = gangweis;
	}

	public List<BaseDatat> getDishis() {
		return dishis;
	}

	public void setDishis(List<BaseDatat> dishis) {
		this.dishis = dishis;
	}

	public int getSub_operate() {
		return sub_operate;
	}

	public void setSub_operate(int sub_operate) {
		this.sub_operate = sub_operate;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	/***************************************************************************
	 * 部门列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public String dep_list() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
			// true);
			
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
			// true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		roleid = getSessionIntValue(ElConstants.SESSION_ROLE);
		depsp = departmentDao.getDepByIssp();
		return "dep_list";
	}

	/**
	 * 部门添加初始化
	 * 
	 * @return
	 * @throws ElException
	 */
	public String dep_addInit() throws ElException {
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// else {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }
		jingzhongs = userDao.getBaseDatatByTypeidc(1);
		zhiwus = userDao.getBaseDatatByTypeidc(2);
		zhijis = userDao.getBaseDatatByTypeidc(3);
		gangweis = userDao.getBaseDatatByTypeidc(4);
		dishis = userDao.getBaseDatatByTypeidc(5);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
			// true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			// depTree = departmentDao.getDepTree(
			// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
			// true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (depTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的部门类别");
			return "error";
		}
		return "dep_add";
	}

	/**
	 * 部门添加
	 * 
	 * @return
	 * @throws ElException
	 */
	public String dep_add() throws ElException {
		// 先检测部门编号是否存在
		if (departmentDao.checkDepBh(department.getBh())) {
			setElmessage("该单位编号已经存在，请重新选择。");
			return this.dep_addInit();
		}
		if (department.getParent()==null||department.getParent().getId()<=0) {
			setElmessage("请选择有效的上级节点");
			return this.dep_addInit();
		}
		if (department.getParent() == null) {
			// 因为ajax树有点缺陷
			department.setParent(new ElNode(department.getId()));
		}
		departmentDao.addDep(department);
		((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
		.updatetlrid("department");
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_DEP);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_DEPARTMENT,
				ElLoggerConstants.LOG_TYPE_ADD, department.getName(),
				ElLoggerConstants.LOG_RES_SUCC, department.getId());// **//**//

		return "dep_add_success";
	}

	public String dep_update() throws ElException {
		sub_operate = departmentDao.updateDep(department);
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
				.updatetlrid("department");
		return "dep_view";
	}

	public String dep_updateuser() throws ElException {
		if (department.getId() == 0) {
			setElmessage("没有可操作的部门类别");
			return "error";
		}
		sub_operate = departmentDao.updateDepUser(department);
		return "dep_view";
	}

	public String list_dep_childs() throws ElException {

		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			departments = departmentDao.listdepChildsByPId(department.getId());
			String d = "[";
			if (null != departments && departments.size() > 0) {
				for (int i = 0; i < departments.size(); i++) {
					Department dep = departments.get(i);
					String name = dep.getName();
					if (name != null)
						name = name.replaceAll("\"", "\\\\\"");
					d += "{\"id\":\"" + dep.getId() + "\",\"name\":\"" + name
							+ "\",\"bh\":\"" + dep.getBh() + "\",\"ccnt\":\""
							+ dep.getClassCount() + "\",\"lid\":\""
							+ dep.getLid() + "\",\"rid\":\""
							+ dep.getRid() + "\"},";
				}
				d = d.length() > 0 ? d.substring(0, d.length() - 1) : d;
				d += "]";
			} else
				d += "]";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("获取下级部门错误",e);
		}
		return null;
	}

	public String depDisplayByName() throws ElException {
		if (department == null) {
			department = new Department();
			department.setName("");
		}
		departments = departmentDao.listDepartmentsByName(department.getName(),
				getPageNow(), getPageSize());
		count = departmentDao.getDepartmentCount(department.getName());
		return "depDisplayPage";
	}

	public String dep_view() throws ElException {
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// else {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }
		if("ajax".equals(optype)){
			department = departmentDao.getDepById(department.getId());
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				String d= "{\"id\":\"" + department.getId() + "\",\"name\":\"" + department.getName()
								+ "\",\"bh\":\"" + department.getBh() + "\"}";
				localPrintWriter.println(d);
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				logger.error("ajax部门查看错误",e);
			}
			return null;
		}
		if(department==null||department.getId()<=0)
		{	
			setElmessage("您需要查看的部门不存在,请重新选择！");
			return "error";
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		department = departmentDao.getDepById(department.getId());
		department.setOpusers(departmentDao
				.getOpUsers("op", department.getId()));
//		department.setUseusers(departmentDao.getOpUsers("use", department
//				.getId()));
		return "dep_view";
	}

	public String dep_alterInit() throws ElException {
		if(department==null||department.getId()<=0){
			setElmessage("请选择有效的节点");
			return "error";
		}
		department = departmentDao.getDepById(department.getId());
		department.setParent(departmentDao.getDepById(department.getParent().getId()));
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			// depTree = departmentDao.getDepTree(
//			// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
//			// department.getId(), false);
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -3,
//					false);// 第2个参数是不显示的id及下级节点(现在需求是全部显示)
//		else {
//			// depTree = departmentDao.getDepTree(
//			// getSessionIntValue(ElConstants.SESSION_USERID), "op",
//			// department.getId(), false);
//			depTree = departmentDao.getDepTree(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					false);// 第2个参数是不显示的id及下级节点(现在需求是全部显示)
//		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
			if(depTree!=null&&depTree.getChild()!=null)
				for (int i = 0; i < depTree.getChild().size(); i++) {
					if(department.getId()==depTree.getChild().get(i).getId()){
						setElmessage("被分配的节点（二级节点）不容许修改，选择子节点");
						return "error";
					}
				}
		}
		elUsers = userDao.getEUsByDepid(department.getId());
		department.setOpusers(departmentDao
				.getOpUsers("op", department.getId()));
//		department.setUseusers(departmentDao.getOpUsers("use", department
//				.getId()));
		if (depTree!=null&&depTree.getChild()!=null&&depTree.getChild().size() == 0
				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
			setElmessage("没有可操作的部门类别");
			return "error";
		}
		return "dep_alter";
	}

	public String dep_alter() throws ElException {
		// 先检测部门编号是否存在
		// 先查出本部门的编号，因为要排除他
		Department d = departmentDao.getDepById(department.getId());
		if (!d.getBh().equals(department.getBh())) {
			if (departmentDao.checkDepBh(department.getBh())) {
				setElmessage("该单位编号已经存在，请重新选择。");
				return this.dep_alterInit();
			}
		}
//		if (getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT) == department
//				.getId()) {
//			Department dep = departmentDao.getDepById(department.getId());
//			department.setParent(dep.getParent());
//		}
//		if (department.getParent() == null) {// 省厅管理员1级节点修改时可能出现此情况
//			department.setParent(new Department(1));
//		}
		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
		if(ens.checkNodeisChild(department.getId(), department.getParent().getId(), "department")){
			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
			return this.dep_alterInit();
		}
		departmentDao.alterDep(department); // hec 注释
		//若父节点发生变化则去整理整个树的左右id
		if(department.getParent().getId()!=d.getParent().getId()){
			ens.updatetlrid("department");
		}
//		userDao.setEURole(department.getManager().getId(), 2);
//		if (null != department.getOpusers()) {
//			for (int i = 0; i < department.getOpusers().size(); i++) {
//						+ department.getOpusers().get(i).getId());
//				if (!departmentDao.checkOpUsers("op", department.getOpusers()
//						.get(i).getId(), department.getId()))
//					departmentDao.addOpusers("op", department.getOpusers().get(
//							i).getId(), department.getId());
//				roleDao.setUserfunc(department.getOpusers().get(i).getId(),
//						"dep_list", 0);
//				roleDao.setUserfunc(department.getOpusers().get(i).getId(),
//						"admin", 0);
//				roleDao.setUserfunc(department.getOpusers().get(i).getId(),
//						"account_searchInit", 0);
//			}
//		}
//		if (null != department.getUseusers()) {
//			for (int i = 0; i < department.getUseusers().size(); i++) {
//						+ department.getUseusers().get(i).getId());
//				if (!departmentDao.checkOpUsers("use", department.getUseusers()
//						.get(i).getId(), department.getId()))
//					departmentDao.addOpusers("use", department.getUseusers()
//							.get(i).getId(), department.getId());
//			}
//		}
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_DEP);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_DEPARTMENT,
				ElLoggerConstants.LOG_TYPE_ALTER, department.getName(),
				ElLoggerConstants.LOG_RES_SUCC, department.getId());
		return "dep_alter_success";
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

	public String dep_delete_user() throws ElException {
		departmentDao.deleteOpusers(optype, elUser.getId(), department.getId());
		roleDao.checkUserfunc(elUser.getId(), "account_searchInit",
				"department_op_user");
		roleDao.checkUserfunc(elUser.getId(), "dep_list", "department_op_user");
		roleDao.checkUserfunc(elUser.getId(), "admin", "department_op_user");
		roleDao.checkUserfunc(elUser.getId(), "account_searchInit",
				"department_op_user");
		// roleDao.checkUserfunc(getSessionIntValue(ElConstants.SESSION_USERID),"dep_list","department_op_user");
		// roleDao.checkUserfunc(getSessionIntValue(ElConstants.SESSION_USERID),"admin","department_op_user");

		return null;
	}

	public RoleDao roleDao;

	public String dep_deleteInit() throws ElException {
		if(department.getId()==1){
			setElmessage("不能删除根部门");
			return "error";
		}
		if (getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT) == department
				.getId()) {
			return "noright";
		}
		department = departmentDao.getDepById(department.getId());
		return "dep_delete";
	}

	public String dep_delete() throws ElException {
		if(department.getId()==1){
			setElmessage("不能删除根部门");
			return "error";
		}
		if (getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT) == department
				.getId()) {
			return "noright";
		}
		if (sub_operate == 0) {
			// 并入上级部门(首先获取该节点的父节点，然后更新该节点的子节点的父节点为该节点的父节点，然后更新该节点下的人员到该节点的父节点,最后删除该节点)
			department = departmentDao.getDepById(department.getId());
			// departmentDao.deleteDep(department.getId());department.parent.id
			departmentDao.deleteDep(department.getId(), department.getParent()
					.getId());
		} else {
			// 与本部门同时删除
//			departmentDao.deleteDepAndSub(department.getId());
			departmentDao.deleteDepAndSubNot(department.getId());
		}
		department = departmentDao.getDepById(department.getId());
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
		.updatetlrid("department");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_DEPARTMENT,
				ElLoggerConstants.LOG_TYPE_DELETE, department.getName(),
				ElLoggerConstants.LOG_RES_SUCC, department.getId());
		return "dep_delete_success";
	}
	
	
	public String checkDepidIsThreeNode() throws ElException{
		boolean result = false;
		result = departmentDao.checkDepidIsThreeNode(depid);
		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":\"" + check_json_result + "\"}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	// public Department getDepTree_() {
	// return depTree_;
	// }
	//
	// public void setDepTree_(Department depTree_) {
	// this.depTree_ = depTree_;
	// }

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public int getDepid() {
		return depid;
	}

	public void setDepid(int depid) {
		this.depid = depid;
	}

	public String getCheck_json_result() {
		return check_json_result;
	}

	public void setCheck_json_result(String check_json_result) {
		this.check_json_result = check_json_result;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

}
