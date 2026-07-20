package com.sopia.knowledgeman.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.ScoreOperate;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeman.dao.KnowledgeDao;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.lucene.LuceneIndex;
import com.sopia.lucene.file.FileSeach;
import com.sopia.questionman.entities.StuffLib;

public class KnowledgeAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(KnowledgeAction.class);
	private KnowledgeType kltypeTree;
	private KnowledgeType kltype;
	private List<KnowledgeType> kltypes;

	private Department dtree;
	private Department depTree;
	private KnowledgeDao knowledgeDao;
	private DepartmentDao departmentDao;
	private List<Department> deps;
	private Knowledge knowledge;
	private List<Knowledge> knowledges;
	private List<Knowledge> tjknowledges;
	private List<Knowledge> rmknowledges;
	private List<Knowledge> zdknowledges;
	private Department dep;
	private int subdep;
	private int sub_operate;
	private IndexDataUtil indexDataUtil;
	private List<Department> departments;
	private List treeAllId;

	public List getTreeAllId() {
		return treeAllId;
	}

	public void setTreeAllId(List treeAllId) {
		this.treeAllId = treeAllId;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
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

	public Department getDep() {
		return dep;
	}

	public void setDep(Department dep) {
		this.dep = dep;
	}

	public Knowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	public List<Knowledge> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<Knowledge> knowledges) {
		this.knowledges = knowledges;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public KnowledgeDao getKnowledgeDao() {
		return knowledgeDao;
	}

	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}

	public KnowledgeType getKltypeTree() {
		return kltypeTree;
	}

	public void setKltypeTree(KnowledgeType kltypeTree) {
		this.kltypeTree = kltypeTree;
	}

	public KnowledgeType getKltype() {
		return kltype;
	}

	public void setKltype(KnowledgeType kltype) {
		this.kltype = kltype;
	}

	public Department getDtree() {
		return dtree;
	}

	public void setDtree(Department dtree) {
		this.dtree = dtree;
	}

	public String knowledgetype_list() throws ElException {
//		kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		//hwc
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else {
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		return "knowledgetype_list";
	}

	public String knowledgetype_addInit() throws ElException {
//		kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else {
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}   
		if(kltypeTree.getChild().size() == 0  && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的资源库");
			 return "error"; 
		}
//		dtree = departmentDao.getDepTree(0, -1, false);
		return "knowledgetype_add";
	}

	public String knowledgetype_add() throws ElException {
		knowledgeDao.addKltype(kltype);
		//更新资料库左右id
		((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).updatetlrid("knowledgetype");
		//刷新首页资料模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_KNOWLEDGETYPE,
				ElLoggerConstants.LOG_TYPE_ADD, kltype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,kltype.getId());
		return "knowledgetype_add_success";
	}

	private CourseTypeDao ctypeDao;

	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}

	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}

	public static Log getLogger() {
		return logger;
	}

	public String knowledgetype_alterInit() throws ElException {
		if(kltype==null||kltype.getId()<=0){	
			setElmessage("您需要查看的资源库不存在,请重新选择！");
			return "error";
		}
		kltype = knowledgeDao.getKltypeById(kltype.getId());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, kltype.getId(), false);
		} else {
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
			
//			kltypeTree = knowledgeDao.getKltypeTreeByPerOrShar(0, kltype
//					.getId(), false, String
//					.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
//					false, "KNOWLEDGE_OP_TYPE");
		}
//		dtree = departmentDao
//				.getDepTree(
//						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
//						-1, false);
		deps = knowledgeDao.listDepByKltypeId(kltype.getId());
		kltype.setOpusers(ctypeDao.getOpUsers("knowledge_op_type", kltype
				.getId()));
//		kltype.setUseusers(ctypeDao.getOpUsers("knowledge_use_type", kltype
//				.getId()));
		return "knowledgetype_alter";
	}

	private String optype;
	private ELUser elUser;
	private RoleDao roleDao;

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

	public String knowledgetype_delete_user() throws ElException {
		ctypeDao.deleteOpusers(optype, elUser.getId(), kltype.getId());
		roleDao.checkUserfunc(elUser.getId(), "knowledgetype_list",
				"KNOWLEDGE_OP_TYPE");
		roleDao.checkUserfunc(elUser.getId(), "knowledgetype_addInit",
				"KNOWLEDGE_OP_TYPE");
		roleDao.checkUserfunc(elUser.getId(), "admin", "KNOWLEDGE_OP_TYPE");

		return null;
	}

	public String knowledgetype_alter() throws ElException {
		//不能选择自己作为上级节点
//		if(kltype.getId()==kltype.getParent().getId()){
//			setElmessage("不能选择自己作为上级节点!");
//			return "error";
//		}
		//不能选择自己作为上级节点以及自己的子节点
		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
		if(ens.checkNodeisChild(kltype.getId(), kltype.getParent().getId(), "knowledgetype")){
			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
			return "error";
		}
		knowledgeDao.alterKltype(kltype);
//		if (null != kltype.getOpusers()) {
//			for (int i = 0; i < kltype.getOpusers().size(); i++) {
//				// 这样的写法不好，循环去操作数据会产生性能问题，由于时间关系先暂时参考试题库的代码这样处理（使用量不是很大的话也没什么问题）。
//				// 当出现性能问题时，可以把这段代码改掉，减少数据库的链接次数和做批量处理。备注：jiahaijiang
//				if (!ctypeDao.checkOpUsers("KNOWLEDGE_OP_TYPE", kltype
//						.getOpusers().get(i).getId(), kltype.getId()))
//					ctypeDao.addOpusers("KNOWLEDGE_OP_TYPE", kltype
//							.getOpusers().get(i).getId(), kltype.getId());
//				roleDao.setUserfunc(kltype.getOpusers().get(i).getId(),
//						"knowledgetype_list", 0);
//				roleDao.setUserfunc(kltype.getOpusers().get(i).getId(),
//						"knowledgetype_addInit", 0);
//				roleDao.setUserfunc(kltype.getOpusers().get(i).getId(),
//						"admin", 0);
//			}
//		}
//		if (null != kltype.getUseusers()) {
//			for (int i = 0; i < kltype.getUseusers().size(); i++) {
//				if (!ctypeDao.checkOpUsers("KNOWLEDGE_USE_TYPE", kltype
//						.getUseusers().get(i).getId(), kltype.getId()))
//					ctypeDao.addOpusers("KNOWLEDGE_USE_TYPE", kltype
//							.getUseusers().get(i).getId(), kltype.getId());
//			}
//		}
		//更新资料库左右id
		((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).updatetlrid("knowledgetype");
		//刷新首页资料模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		kltype=knowledgeDao.getKltypeById(kltype.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_KNOWLEDGETYPE,
				ElLoggerConstants.LOG_TYPE_ALTER, kltype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,kltype.getId());
		return "knowledgetype_alter_success";
	}

	public String myknowledge_list() throws ElException {
		// if(getPageSize()==0) getPageSize()=10;
//		String title = knowledge!=null?knowledge.getTitle():"";
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else {
//			kltypeTree = knowledgeDao
//					.getKltypeTreeByPerOrShar(
//							0,
//							0,
//							false,
//							String
//									.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
//							false, "KNOWLEDGE_USE_TYPE");		
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
//		String type = kltype!=null?String.valueOf(kltype.getId()):"";
		if(kltype==null||kltype.getId()<0){
			kltype=kltypeTree;
		}else{
			kltype=knowledgeDao.getKltypeById(kltype.getId());
		}
//		knowledges = knowledgeDao.listMyKlsNew(
//				getSessionIntValue(ElConstants.SESSION_USERID),type,title, getPageNow(),
//				getPageSize());
//		count = knowledgeDao
//				.listMyklsSizeNew(getSessionIntValue(ElConstants.SESSION_USERID),type,title);
		//hwc
//		int Ktid =kltypeTree.getId();
//		knowledges = knowledgeDao.listMyKlsNew(getSessionIntValue(ElConstants.SESSION_USERID),kltypeTree,Ktid,type,title, getPageNow(),getPageSize());
//		count = knowledgeDao.listMyklsSizeNew(getSessionIntValue(ElConstants.SESSION_USERID),kltypeTree,Ktid,type,title);
		knowledges = knowledgeDao.listMyKlsNew(getSessionIntValue(ElConstants.SESSION_USERID),kltype,knowledge, getPageNow(),getPageSize());
		count = knowledgeDao.listMyklsSizeNew(getSessionIntValue(ElConstants.SESSION_USERID),kltype,knowledge);
		return "myknowledge_list";
	}

	public String knowledge_addInit() throws ElException {
//		kltypes = knowledgeDao.listKltsByDepIdNew(getSessionIntValue(ElConstants.SESSION_USERID));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else {
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}   
		if(getSessionIntValue(ElConstants.SESSION_ROLE) !=1&&kltypeTree.getChild().size() == 0){    
			 setElmessage("没有可操作的资源库");
			 return "error"; 
		}
		return "knowledge_add";
	}

	public String knowledge_alterInit() throws ElException {
		kltypes = knowledgeDao
				.listKltsByDepId(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
		knowledge = knowledgeDao.getKlById(knowledge.getId());
		knowledge.setStuffs(knowledgeDao.listKstuff(knowledge.getId()));

		return "knowledge_alter";
	}

	InputStream inputStream;
	String filename;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String download_kstuff() throws ElException {
		try {
			stuff = knowledgeDao.getKStuffLib(stuff.getId());
			filename = stuff.getDescription();// stuff.getId()+"."
												// +stuff.getFileext();
			filename = filename.substring(filename.indexOf("elstuffs"));

			String path = ServletActionContext.getServletContext().getRealPath(
					filename);
			String fileext = filename.substring(filename.lastIndexOf("."));
			String filename1 = stuff.getTitle() + fileext;
			filename = new String(filename1.getBytes(), "ISO8859-1");
			try {
				inputStream = new FileInputStream(path);

			} catch (Exception e) {
				logger.error("文档下载失败", e);
				throw new ElException("下载资料出错", e);
			}
		} catch (Exception e) {
			logger.error("文档下载失败", e);
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		return "download_kstuff";
	}

	public String knowledge_alter() throws ElException {
		knowledgeDao.alterKl(knowledge);
		String staddr[] = getRequest().getParameterValues(
				"knowledge.stuffs.description");
		String sttitle[] = getRequest().getParameterValues(
				"knowledge.stuffs.title");
		String stid[] = getRequest().getParameterValues("knowledge.stuffs.id");
		// 修改部分
		int addfrom = 0;
		if (stid != null) {
			addfrom = stid.length;
			for (int i = 0; i < stid.length; i++) {
				knowledgeDao.alterKstuff(sttitle[i], getIntValue(stid[i]));
			}
		}
		if (null != staddr) {
			for (int i = addfrom; i < staddr.length; i++) {
				knowledgeDao
						.addKstuff(staddr[i], knowledge.getId(), sttitle[i]);
			}
		}
		//刷新首页资料模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		knowledge=knowledgeDao.getKlById(knowledge.getId());
		if(knowledge!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_KNOWLEDGE,
					ElLoggerConstants.LOG_TYPE_ALTER, knowledge.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC,knowledge.getId());
		}
		return "knowledge_alter_success";
	}

	private int getIntValue(String value) {
		if (value == null)
			return 0;
		if (("").equals(value.trim()))
			return 0;
		int valuei = 0;
		try {
			valuei = new Integer(value).intValue();
		} catch (Exception e) {
			logger.error("数字转换错误",e);
		}
		return valuei;
	}

	public String knowledge_add() throws Exception {
		if (null == knowledge.getKltype()) {
			setElmessage("请选择知识类别");
			kltypes = knowledgeDao
					.listKltsByDepId(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
			return "knowledge_add";
		}
		/*
		 * if
		 * (!knowledgeDao.checkKltype_dep(knowledge.getKltype().getId(),getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT))) {
		 * setElmessage("您没权限操作所选的类别"); kltypes =
		 * knowledgeDao.listKltsByDepId(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
		 * return "knowledge_add"; }
		 */
		knowledge.setOwner(new ELUser(
				getSessionIntValue(ElConstants.SESSION_USERID)));
		// TODO 知识自动发布
		knowledge.setValid(!SystemConfOp
				.getBooleanValue(ElConstants.KNOWLEDGE_NEED_SH));
		knowledgeDao.addKl(knowledge);
		String staddr[] = getRequest().getParameterValues(
				"knowledge.stuffs.description");
		String sttitle[] = getRequest().getParameterValues(
				"knowledge.stuffs.title");

		if (null != staddr) {
			for (int i = 0; i < staddr.length; i++) {
				knowledgeDao
						.addKstuff(staddr[i], knowledge.getId(), sttitle[i]);
			}
		}
		//刷新首页资料模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_KNOWLEDGE,
				ElLoggerConstants.LOG_TYPE_ADD, knowledge.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,knowledge.getId());
		if(SystemConfOp.getBooleanValue(ElConstants.SEARCH_NEED)){ //是否启用全文检索
			List<StuffLib> stuffs = knowledgeDao.listKstuff();
			String workpath = getRequest().getSession().getServletContext().getRealPath("/");//项目所在路径
			workpath = workpath.replaceAll("\\\\", "/")+"elstuffs/";
	//		LuceneIndex index = new LuceneIndex();
	//		for(int i=0;i<stuffs.size();i++){
				String wp = workpath;
				String[] s = stuffs.get(0).getDescription().split("/");
				String path = s[1];  //附件所在文件夹
				wp = wp+path;
				FileSeach.init(wp);
	//			index.writeToIndex(wp);
				System.out.println("wp="+wp);
	//		}
		}
		
		return "knowledge_add_success";
	}

	private StuffLib stuff;

	public String deleteKstuff() throws ElException {
		knowledgeDao.deleteKstuff(stuff.getId());
		return "deleteKstuff";
	}

	public String knowledge_delete() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else {	
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		
//			kltypeTree = knowledgeDao
//					.getKltypeTreeByPerOrShar(
//							0,
//							0,
//							false,
//							String
//									.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
//							false, "KNOWLEDGE_USE_TYPE");
		}
		knowledge=knowledgeDao.getKlById(knowledge.getId());
		knowledgeDao.deleteKl(knowledge.getId());
		//刷新首页资料模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_KNOWLEDGE,
				ElLoggerConstants.LOG_TYPE_DELETE, knowledge.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,knowledge.getId());
		knowledges = knowledgeDao.listMyKls(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
				getPageSize());
		count = knowledgeDao
				.listMyklsSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "knowledge_delete";
	}

	public String knowledge_listInit() throws ElException {
		depTree = departmentDao
				.getDepTree(
						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
						-1, false);
		return "knowledge_listInit";
	}

//	public String knowledge_list() throws ElException { 
//		boolean depsub = false;
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
//			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
//		} else {
//			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
//		} 
//		int type;
//		kltype=kltype==null?new KnowledgeType():kltype;
//		if (subdep == 1) {
//			depsub = true;
//			type =  kltype.getId() == 0 ? -1:kltype.getId();
//		}else{type = -1;}
//		int depid = dep.getId();
//		
//		String name = (knowledge == null) ? "" : knowledge.getTitle();
//		// getPageSize()=getPageSize()<=0? 10:getPageSize();
//		if (type == -1) {
//			knowledges = knowledgeDao.listKls(depid, depsub, getPageNow(),
//					getPageSize(), name);
//			count = knowledgeDao.listKlsSize(depid, depsub, name);
//		} else {
//			knowledges = knowledgeDao.listKls(depid, type, depsub,
//					getPageNow(), getPageSize(), name);
//			count = knowledgeDao.listKlsSize(depid, type, depsub, name);
//		}
////		kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
//		return "knowledge_list";
//	}
	/**
	 * 资料推荐列表页
	 */
	public String knowledge_list() throws ElException { 
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else {
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		} 
		if(kltype==null||kltype.getId()<0){
			kltype=kltypeTree;
		}else{
			kltype=knowledgeDao.getKltypeById(kltype.getId());
		}
		knowledge=knowledge==null?new Knowledge():knowledge;
		knowledge.setStatus(1);//只显示已开通的资料
		knowledges = knowledgeDao.listMyKlsNew(0,kltype,knowledge, getPageNow(),getPageSize());
		count = knowledgeDao.listMyklsSizeNew(0,kltype,knowledge);
		return "knowledge_list";
	}
	/**
	 * 资料分配给部门
	 * @return
	 * @throws ElException
	 */
	public String knowledgeAssignDep() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
			depTree = departmentDao.getDepTree_level1(1, -1,true);
		} else {
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,true);
		}
		KnowledgeType ktype=knowledgeDao.getKnowledgeLibTree_index(-1, true);
		treeAllId=userDao.getTreeAllId(ktype,true);
		departments = knowledgeDao.listKnowledgeDepd();
		if(getRequest().getParameter("updateStatus")!=null&&getRequest().getParameter("updateStatus").equals("1")){
			setElmessage("提交成功！");
		}
		return "knowledgeAssignDep";
	}
	/**
	 * 资料分配给部门处理
	 * @return
	 * @throws ElException
	 */
	public String knowledgeAssignDepDo() throws ElException {
		String[] knowledges=getRequest().getParameterValues("chkNames");
		//先清空信息
		knowledgeDao.deleteKnowledgeDep();
		if(knowledges!=null){
			for (int i = 0; i < knowledges.length; i++) {
				//System.out.println(knowledges[i]);
				knowledgeDao.addKnowledgeDep(Integer.parseInt(knowledges[i]), 1);
			}
		}
		if(departments!=null){
			for (int i = 0; i < departments.size(); i++) {
				//System.out.println(departments.get(i).getId());
				knowledgeDao.addKnowledgeDep(departments.get(i).getId(), 2);
			}
		}
		return "knowledgeAssignDep";
	}

	public String knowledge_shlist() throws ElException {
		// getPageSize()=getPageSize()<=0? 10:getPageSize();
//		String type = kltype!=null?String.valueOf(kltype.getId()):"";;
//		String title = knowledge!=null?knowledge.getTitle():"";
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else {
//			kltypeTree = knowledgeDao
//					.getKltypeTreeByPerOrShar(
//							0,
//							0,
//							false,
//							String
//									.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
//							false, "KNOWLEDGE_USE_TYPE");
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
//		if (kltypeTree != null) {
//			knowledges = knowledgeDao.listShKlsByPerOrShar(kltypeTree,
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
//					getPageNow(), getPageSize(),type,title);
//			count = knowledgeDao.listShKlsByPerOrSharSize(kltypeTree,
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),type,title);
//		}
		if(kltype==null||kltype.getId()<0){
			kltype=kltypeTree;
		}else{
			kltype=knowledgeDao.getKltypeById(kltype.getId());
		}
		knowledges = knowledgeDao.listMyKlsNew(0,kltype,knowledge, getPageNow(),getPageSize());
		count = knowledgeDao.listMyklsSizeNew(0,kltype,knowledge);
		return "knowledge_shlist";
	}

	public String knowledge_sh() throws ElException {
		if (knowledges != null) {
			for (int i = 0; i < knowledges.size(); i++) {
				knowledgeDao.klShSet(knowledges.get(i).getId());
			}
			//刷新首页资料模块
			indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		}
		return "knowledge_shlist";
	}
	
	public String knowledge_del() throws ElException {
		if (knowledges != null) {
			for (int i = 0; i < knowledges.size(); i++) {
				knowledgeDao.deleteKl(knowledges.get(i).getId());
			}
			//刷新首页资料模块
			indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		}
		return "knowledge_shlist";
	}

	public String knowledge_mshlist() throws ElException {
		// getPageSize()=getPageSize()<=0? 10:getPageSize();
		knowledges = knowledgeDao.listShmKls(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
				getPageNow(), getPageSize());
		count = knowledgeDao
				.listShmKlsSize(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));

		return "knowledge_mshlist";
	}

	public String knowledge_msh() throws ElException {
		if (knowledges != null) {
			for (int i = 0; i < knowledges.size(); i++) {
				knowledgeDao.klShSet(knowledges.get(i).getId());
			}
			//刷新首页资料模块
			indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		}
		return "knowledge_mshlist";
	}

//	public String knowledge_delete_man() throws ElException {
//		if (null != knowledges) {
//			for (int i = 0; i < knowledges.size(); i++) {
//				int knowid = knowledges.get(i).getId();
//				Knowledge k = knowledgeDao.getKlById(knowid);
//				knowledgeDao.deleteKl(knowid);
//				int userid = k.getOwner().getId();
//				ScoreOperate.setScore(userid, ElConstants.JIAN_KNOWLEDGE_DO);
//			}
//		}
//		boolean depsub = false;
//		if (subdep == 1) {
//			depsub = true;
//		}
//		int depid = dep.getId();
//		int type = kltype.getId();
//		String name = (knowledge == null) ? "" : knowledge.getTitle();
//		// getPageSize()=getPageSize()<=0? 10:getPageSize();
//		if (type == -1) {
//			knowledges = knowledgeDao.listKls(depid, depsub, getPageNow(),
//					getPageSize(), name);
//			count = knowledgeDao.listKlsSize(depid, depsub, name);
//		} else {
//			knowledges = knowledgeDao.listKls(depid, type, depsub,
//					getPageNow(), getPageSize(), name);
//			count = knowledgeDao.listKlsSize(depid, type, depsub, name);
//		}
//		kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
//		//刷新首页资料模块
//		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
//		return "knowledge_list";
//	}

	public String knowledge_delete_man() throws ElException {
		if (null != knowledges) {
			for (int i = 0; i < knowledges.size(); i++) {
				int knowid = knowledges.get(i).getId();
				Knowledge k = knowledgeDao.getKlById(knowid);
				knowledgeDao.deleteKl(knowid);
				int userid = k.getOwner().getId();
				ScoreOperate.setScore(userid, ElConstants.JIAN_KNOWLEDGE_DO);
			}
		}
		//刷新首页资料模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		return "knowledge_list";
	}
	
//	public String knowledge_hotset() throws ElException {
//		if (null != knowledges) {
//			for (int i = 0; i < knowledges.size(); i++) {
//				int knowid = knowledges.get(i).getId();
//				Knowledge k = knowledgeDao.getKlById(knowid);
//				knowledgeDao.setKlhotSet(knowid, knowledge.getHot());
//				// int userid = k.getOwner().getId();
//				// ScoreOperate.setScore(userid,
//				// ElConstants.SCORE_KNOWLEDGE_TJ);
//			}
//		}
//		boolean depsub = false;
//		if (subdep == 1) {
//			depsub = true;
//		}
//		int depid = dep.getId();
//		int type = kltype.getId();
//		String name = (knowledge == null) ? "" : knowledge.getTitle();
//		// getPageSize()=getPageSize()<=0? 10:getPageSize();
//		if (type == -1) {
//			knowledges = knowledgeDao.listKls(depid, depsub, getPageNow(),
//					getPageSize(), name);
//			count = knowledgeDao.listKlsSize(depid, depsub, name);
//		} else {
//			knowledges = knowledgeDao.listKls(depid, type, depsub,
//					getPageNow(), getPageSize(), name);
//			count = knowledgeDao.listKlsSize(depid, type, depsub, name);
//		}
//		kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
//		//刷新首页资料模块
//		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
//		return "knowledge_list";
//	}
	
	public String knowledge_hotset() throws ElException {
		if (null != knowledges) {
			for (int i = 0; i < knowledges.size(); i++) {
				int knowid = knowledges.get(i).getId();
				Knowledge k = knowledgeDao.getKlById(knowid);
				knowledgeDao.setKlhotSet(knowid, knowledge.getHot());
				// int userid = k.getOwner().getId();
				// ScoreOperate.setScore(userid,
				// ElConstants.SCORE_KNOWLEDGE_TJ);
			}
		}
		//刷新首页资料模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		return "knowledge_list";
	}
	//组合搜索
//	public String combinationSearch() throws ElException {
//		String type = kltype!=null?String.valueOf(kltype.getId()):"";;
//		//String title = knowledge!=null?knowledge.getTitle():"";
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
//			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
//		} else {	
//			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
//		
////			kltypeTree = knowledgeDao
////					.getKltypeTreeByPerOrShar(
////							0,
////							0,
////							false,
////							String
////									.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
////							false, "KNOWLEDGE_USE_TYPE");
//		}
//		/*knowledges = knowledgeDao.listMyKlsNew(
//				getSessionIntValue(ElConstants.SESSION_USERID),type,title, getPageNow(),
//				getPageSize());*/
//		//kltypes=knowledgeDao.listKnowledgeType();
//		getRequest().setAttribute("csstr", "combinationSearchknowledge");
//		int Ktid =kltypeTree.getId();
//		knowledges = knowledgeDao.listCombinationKlsNew(kltypeTree,Ktid,getSessionIntValue(ElConstants.SESSION_USERID),type,knowledge, getPageNow(),
//				getPageSize());
////		count = knowledgeDao.listCombinationKlsNewCount(kltypeTree,Ktid,getSessionIntValue(ElConstants.SESSION_USERID),type,knowledge, getPageNow(),
////				getPageSize());
//		count = knowledgeDao.listCombinationKlsNewCount2(kltypeTree,Ktid,getSessionIntValue(ElConstants.SESSION_USERID),type,knowledge);
//		return "combinationSearch";
//	} 
	/**
	 * 资料组合搜索
	 */
	public String combinationSearch() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else {
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		if(kltype==null||kltype.getId()<0){
			kltype=kltypeTree;
		}else{
			kltype=knowledgeDao.getKltypeById(kltype.getId());
		}
		knowledges = knowledgeDao.listMyKlsNew(0,kltype,knowledge, getPageNow(),getPageSize());
		count = knowledgeDao.listMyklsSizeNew(0,kltype,knowledge);
		return "combinationSearch";
	}
	
	public String combinationSearchknowledgeInit()throws ElException{
		kltypes=knowledgeDao.listKnowledgeType();
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else {
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
//			kltypeTree = knowledgeDao
//					.getKltypeTreeByPerOrShar(
//							0,
//							0,
//							false,
//							String
//									.valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
//							false, "KNOWLEDGE_USE_TYPE");
		}
		return "combinationSearchknowledgeInit";
	}
	public List<KnowledgeType> getKltypes() {
		return kltypes;
	}

	public void setKltypes(List<KnowledgeType> kltypes) {
		this.kltypes = kltypes;
	}

	public List<Department> getDeps() {
		return deps;
	}

	public void setDeps(List<Department> deps) {
		this.deps = deps;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public int getSubdep() {
		return subdep;
	}

	public void setSubdep(int subdep) {
		this.subdep = subdep;
	}

	public List<Knowledge> getTjknowledges() {
		return tjknowledges;
	}

	public void setTjknowledges(List<Knowledge> tjknowledges) {
		this.tjknowledges = tjknowledges;
	}

	public List<Knowledge> getRmknowledges() {
		return rmknowledges;
	}

	public void setRmknowledges(List<Knowledge> rmknowledges) {
		this.rmknowledges = rmknowledges;
	}

	public List<Knowledge> getZdknowledges() {
		return zdknowledges;
	}

	public void setZdknowledges(List<Knowledge> zdknowledges) {
		this.zdknowledges = zdknowledges;
	}

	public StuffLib getStuff() {
		return stuff;
	}

	public void setStuff(StuffLib stuff) {
		this.stuff = stuff;
	}
	/**
	 * 删除知识库初始化
	 * @return
	 * @throws ElException 
	 */
	public String knowledgetype_deleteInit() throws ElException{
		if (kltype==null||kltype.getId() == 1) {
			setElmessage("不能删除根类别");
			return "error";
		}
		kltype = knowledgeDao.getKltypeById(kltype.getId());
		return "knowledgetype_delete";
	}
	
	/**
	 * 删除知识库
	 * @return
	 * @throws ElException 
	 */
	public String knowledgetype_delete() throws ElException{
		if (kltype==null||kltype.getId() == 1) {
			setElmessage("不能删除根类别");
			return "error";
		}
//		int id = kltype.getId();
//		int userid = getSessionIntValue(ElConstants.SESSION_USERID);// getSessionIntValue(ElConstants.SESSION_USERID)
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			kltypeTree = knowledgeDao.getKltypeTree(0, -1, true);
		} else {
			kltypeTree = knowledgeDao.getKnowledgeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
	    String KTtypeIds = knowledgeDao.KTTypeById(kltypeTree, kltype.getId());//获取本节点及其所有子节点
	    String [] ktTypeIdss=KTtypeIds.split(",");
		if (sub_operate == 0) {
			// 并入上级试题库		
			kltype = knowledgeDao.getKltypeById(kltype.getId());
		    int parentId=kltype.getParent().getId();//获取父节点id
		    KnowledgeType parentKltype = knowledgeDao.getKltypeById(parentId); //获取父节点
		    for (int i = 1; i < ktTypeIdss.length; i++) {
		    	int tempId=Integer.parseInt(ktTypeIdss[i]);//循环得到子节点id
		    	KnowledgeType tempKnowType=knowledgeDao.getKltypeById(tempId);
		    	if(tempKnowType.getParent().getId()==kltype.getId()){//只需要更新他的下级节点
		    		tempKnowType.setParent(parentKltype);
		    		knowledgeDao.alterKltype(tempKnowType);
		    		//还要更新下面的资源
		    		//List<Integer> list= knowledgeDao.getKlByKltype(tempKnowType.getId());
		    		//knowledgeDao.updateKnowledgePid(tempId, parentId);
		    	}
			}
		    //knowledgeDao.deleteKl(kltype.getId());此方法是删除knowledge对象
		    knowledgeDao.updateKnowledgePid(kltype.getId(), parentId);
		} else {
			for (int i = 1; i < ktTypeIdss.length; i++) {
		    	int tempId=Integer.parseInt(ktTypeIdss[i]);//循环得到子节点id
//		    	KnowledgeType tempKnowType=knowledgeDao.getKltypeById(tempId);
		    	knowledgeDao.deleteKltypedep(tempId);
			}
		}
		knowledgeDao.deleteKltypedep(kltype.getId());
		//更新资料库左右id
		((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).updatetlrid("knowledgetype");
		//刷新首页资料模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		kltype=knowledgeDao.getKltypeById(kltype.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_KNOWLEDGETYPE,
				ElLoggerConstants.LOG_TYPE_DELETE, kltype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,kltype.getId());
		return "knowledgetype_delete_success";
	}
}
