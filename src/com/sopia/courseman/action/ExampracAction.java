package com.sopia.courseman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.dao.ExampracDao;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyExamPaper;

public class ExampracAction extends BaseAction {
	private List<Examprac> exampracs;
	private ExampracDao exampracDao;
	private Department depTree;
	private Examprac examprac;
	private EroomDao eroomDao;
	private List treeAllId;
	private int statusValue;
	private UserDao userDao;
	private ELUser elUser;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	private List<MyExamPaper> myExamPapers;

	public List<MyExamPaper> getMyExamPapers() {
		return myExamPapers;
	}

	public void setMyExamPapers(List<MyExamPaper> myExamPapers) {
		this.myExamPapers = myExamPapers;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

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

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}

	public List getTreeAllId() {
		return treeAllId;
	}

	public void setTreeAllId(List treeAllId) {
		this.treeAllId = treeAllId;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public Examprac getExamprac() {
		return examprac;
	}

	public void setExamprac(Examprac examprac) {
		this.examprac = examprac;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public List<Examprac> getExampracs() {
		return exampracs;
	}

	public void setExampracs(List<Examprac> exampracs) {
		this.exampracs = exampracs;
	}

	public ExampracDao getExampracDao() {
		return exampracDao;
	}

	public void setExampracDao(ExampracDao exampracDao) {
		this.exampracDao = exampracDao;
	}

	/**
	 * 练习分配给部门的菜单列表页
	 * @return
	 * @throws ElException
	 */
	public String examprac_deplist() throws ElException {
		exampracs = exampracDao.listExampracDep(getPageNow(), getPageSize());
		count = exampracDao.listExampracDepSize();
		return "examprac_deplist";
	}
	/**
	 * 练习分配给部门的详情页
	 * @return
	 * @throws ElException
	 */
	public String examprac_depInfo() throws ElException {
		depTree = departmentDao.getExampracDepTree(examprac.getId(), -1, true);
		examprac=eroomDao.getexamprac(examprac.getId());
		//部门总人数
		//1.得到部门树的1级所有部门id
//		if(depTree!=null){
//			for (int i = 0; i < depTree.getChild().size(); i++) {
//				count+=userDao.getUserByDepIdSize(depTree.getChild().get(i).getId(), 1, null);
//			}
//		}
		return "examprac_depInfo";
	}
	/**
	 * 练习分配给部门 部门树初始化
	 * @return
	 * @throws ElException
	 */
	public String examprac_doDepInit() throws ElException {
		departmentDao=new DepartmentDaoImpl();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
		}
		else {
			depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
		}
		Department userTree = departmentDao.getExampracDepTree(examprac.getId(), -1, true);
		treeAllId=userDao.getTreeAllId(userTree,true); 
		return "examprac_doDepInit";
	}
	/**
	 * 练习分配给部门
	 * @return
	 * @throws ElException
	 */
	public String examprac_doDep() throws ElException {
		String [] chkstr= this.getRequest().getParameterValues("chkNames");
		//在此先删除此练习已分配的部门
		exampracDao.delExamprac_dep(examprac.getId());
		if(chkstr==null){
			return "examprac_depInfo";
		}
		for (int i = 0; i < chkstr.length; i++) {
			exampracDao.addExamprac_dep(examprac.getId(), Integer.parseInt(chkstr[i]));
		}
		return "examprac_depInfo";
	}
	/**
	 * 练习审核菜单列表页
	 * @return
	 * @throws ElException
	 */
	public String examprac_sh_list() throws ElException {
		exampracs = exampracDao.listExampracDep(getPageNow(), getPageSize());
		count = exampracDao.listExampracDepSize();
		return "examprac_sh_list";
	}
	/**
	 * 练习答卷的组合搜索初始化
	 * @return
	 * @throws ElException
	 */
	public String examprac_quiz_seachInit() throws ElException {
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		zhiwus=userDao.getBaseDatatByTypeid(2);
		zhijis=userDao.getBaseDatatByTypeid(3);
		gangweis=userDao.getBaseDatatByTypeid(4);
		dishis=userDao.getBaseDatatByTypeid(5);
		return "examprac_quiz_seachInit";
	}
	/**
	 * 所有练习的简单信息
	 * @return
	 * @throws ElException
	 */
	public String examprac_simple_list() throws ElException {
		exampracs = exampracDao.listExampracAll(getPageNow(), getPageSize());
		count = exampracDao.listExampracAllSize();
		return "examprac_simple_list";
	}
	/**
	 * 练习答卷概况统计
	 * @return
	 * @throws ElException
	 */
	public String examprac_quiz_Overview() throws ElException {
		examprac=exampracDao.getExampracQuizOverview(examprac, elUser);
		return "examprac_quiz_Overview";
	}
	/**
	 * 练习答卷概况详情
	 * @return
	 * @throws ElException
	 */
	public String examprac_quiz_Detail() throws ElException {
		myExamPapers=exampracDao.getExampracQuizDetail(examprac, elUser,getPageNow(), getPageSize());
		count=exampracDao.getExampracQuizDetailSize(examprac, elUser);
		return "examprac_quiz_Detail";
	}
}
