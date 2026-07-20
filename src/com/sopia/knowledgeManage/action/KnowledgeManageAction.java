package com.sopia.knowledgeManage.action;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;


import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.dao.impl.StationDaoImpl;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.knowledgeManage.KnowledgeManageConstants;
import com.sopia.knowledgeManage.KnowledgeManageUtil;
import com.sopia.knowledgeManage.dao.KnowledgeManageDao;
import com.sopia.knowledgeManage.dao.KnowledgeTreeDao;
import com.sopia.knowledgeManage.entities.Competence;
import com.sopia.knowledgeManage.entities.Kledge;
import com.sopia.knowledgeManage.entities.KnowledgeTree;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.entities.AuditMark;

public class KnowledgeManageAction extends BaseAction{
	private static final Log logger = LogFactory.getLog(KnowledgeManageAction.class);
	
	private KnowledgeManageDao knowledgeManageDao;
	private KnowledgeTreeDao knowledgeTreeDao;
	private UserDao userDao;
	private RoleDao roleDao;
	private KnowledgeTree klTree;
	private KnowledgeTree knowledgeTree;
	private Kledge kledge;
	private String ids;
	private int listType;
	private Department depTree;
	private Department department;
	private List<Department> departments;
	private int sub_department;
	private List<ELUser> elUsers;
	private ELUser elUser;
	private List<ElRole> roles;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	
	private String userids_view;
	private String userids_copy;
	private String userids_update;
	private String userids_download;
	private String userids_delete;
	private String departments_view;
	private String departments_copy;
	private String departments_update;
	private String departments_download;
	private String departments_delete;
	
	private List<Kledge> kledges;
	private int count;
	
	private Competence competence;
	private AuditMark auditMark;
	private String tablename;
	private int id;
	private TagsDao tagsDao;
	private List<AuditMark> ams;//备注列表
	private int fromView;
	
	private List treeAllId;
	private int competenceType;
	private int fromAdd;
	private Station stTree;
	private Station station;
	private List<Kledge> tjkledges;
	private List<Kledge> zztjkledges;
	private Kledge knowledge;
	
	
	////////////////
	//actions
	
	public Kledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(Kledge knowledge) {
		this.knowledge = knowledge;
	}
	public Station getStTree() {
		return stTree;
	}
	public void setStTree(Station stTree) {
		this.stTree = stTree;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	//添加知识
	public String addKledgeInit() throws ElException{
		kledge = kledge == null?new Kledge():kledge;
		kledge.setFabuUser(userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)));
		return "addKledgeInit";
	}
	//查看知识
	public String viewKledge() throws ElException{
		kledge = knowledgeManageDao.getKledgeById(kledge.getId());
		//权限
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		//获取查询部门树sql
		String  sql = "";
		
			
		sql = this.getSqlByDep(kledge.getId(), KnowledgeManageConstants.VIEW_COMPETENCETYPE)	;
		kledge.setCt_view(knowledgeManageDao.getCompetenceByUseridOrDepid(elUser,
				KnowledgeManageConstants.VIEW_TABLE,
				KnowledgeManageConstants.VIEW_COMPETENCETYPE,sql));
		sql = this.getSqlByDep(kledge.getId(), KnowledgeManageConstants.UPDATE_COMPETENCETYPE)	;
		kledge.setCt_update(knowledgeManageDao.getCompetenceByUseridOrDepid(elUser,
				KnowledgeManageConstants.UPDATE_TABLE,
				KnowledgeManageConstants.UPDATE_COMPETENCETYPE,sql));
		sql = this.getSqlByDep(kledge.getId(), KnowledgeManageConstants.DELETE_COMPETENCETYPE)	;
		kledge.setCt_delete(knowledgeManageDao.getCompetenceByUseridOrDepid(elUser,
				KnowledgeManageConstants.DELETE_TABLE,
				KnowledgeManageConstants.DELETE_COMPETENCETYPE,sql));
		sql = this.getSqlByDep(kledge.getId(), KnowledgeManageConstants.COPY_COMPETENCETYPE)	;
		kledge.setCt_copy(knowledgeManageDao.getCompetenceByUseridOrDepid(elUser,
				KnowledgeManageConstants.COPY_TABLE,
				KnowledgeManageConstants.COPY_COMPETENCETYPE,sql));
		sql = this.getSqlByDep(kledge.getId(), KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE)	;
		kledge.setCt_download(knowledgeManageDao.getCompetenceByUseridOrDepid(elUser,
				KnowledgeManageConstants.DOWNLOAD_TABLE,
				KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE,sql));
		
		kledge.setFabuUser(userDao.getUserById(kledge.getFabuuserid()));
		kledge.setXiugaiUser(userDao.getUserById(kledge.getXiugaiuserid()));
		
//		kledge.setCt_views(knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.VIEW_TABLE, KnowledgeManageConstants.VIEW_COMPETENCETYPE));
//		kledge.setCt_updates(knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.UPDATE_TABLE, KnowledgeManageConstants.UPDATE_COMPETENCETYPE));
//		kledge.setCt_deletes(knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.DELETE_TABLE, KnowledgeManageConstants.DELETE_COMPETENCETYPE));
//		kledge.setCt_copys(knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.COPY_TABLE, KnowledgeManageConstants.COPY_COMPETENCETYPE));
//		kledge.setCt_downloads(knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.DOWNLOAD_TABLE, KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE));
		return "viewKledge";
	}
	//删除知识
	public String deleteKledge() throws ElException{
		String resultPage = "";
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		if(fromView == 1){//来自查看页面的删除
			knowledgeManageDao.deleteKledgeById(kledge.getId());
			return resultPage;
		}
		
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					knowledgeManageDao.deleteKledgeById(id);
				}
			}
		}
		
		return resultPage;
	}
	//复制
	public String copyKnowledgeManage() throws ElException{
		int id = knowledgeManageDao.knowledgeManageCopy(kledge.getId(),getSessionIntValue(ElConstants.SESSION_USERID));
		if(id > 0){
			kledge.setId(id);
		}else{
			setElmessage("复制知识错误！");
			return "erro";
		}
		return "copyKnowledgeManage";
	}
	//修改知识init
	public String updateKledgeInit() throws ElException{
		kledge = knowledgeManageDao.getKledgeById(kledge.getId());
		kledge.setXiugaiUser(userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)));
		kledge.setXiugaitime(new Timestamp(System.currentTimeMillis()));
		kledge.setFabuUser(userDao.getUserById(kledge.getFabuuserid()));
		
		List<Competence> ct = null;
		int ccount = 0;
		int icc = 0;
		kledge = this.getCts(ct, kledge, icc, ccount, KnowledgeManageConstants.VIEW_COMPETENCETYPE);
		kledge = this.getCts(ct, kledge, icc, ccount, KnowledgeManageConstants.UPDATE_COMPETENCETYPE);
		kledge = this.getCts(ct, kledge, icc, ccount, KnowledgeManageConstants.DELETE_COMPETENCETYPE);
		kledge = this.getCts(ct, kledge, icc, ccount, KnowledgeManageConstants.COPY_COMPETENCETYPE);
		kledge = this.getCts(ct, kledge, icc, ccount, KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE);
		
		// 部门树
		kledge.setDep_view(departmentDao.getDepTree_level_competence(kledge.getId(), KnowledgeManageConstants.VIEW_TABLE, -1,true));
		kledge.setDep_update(departmentDao.getDepTree_level_competence(kledge.getId(), KnowledgeManageConstants.UPDATE_TABLE, -1,true));
		kledge.setDep_delete(departmentDao.getDepTree_level_competence(kledge.getId(), KnowledgeManageConstants.DELETE_TABLE, -1,true));
		kledge.setDep_copy(departmentDao.getDepTree_level_competence(kledge.getId(), KnowledgeManageConstants.COPY_TABLE, -1,true));
		kledge.setDep_download(departmentDao.getDepTree_level_competence(kledge.getId(), KnowledgeManageConstants.DOWNLOAD_TABLE, -1,true));
		return "updateKledgeInit";
	}
	public String updateKledge() throws ElException{
		String resultPage = "";
		kledge = kledge == null?new Kledge():kledge;
		kledge.setXiugaitime(new Timestamp(System.currentTimeMillis()));
		kledge.setXiugaiUser(userDao.getUserById(kledge.getXiugaiuserid()==0?getSessionIntValue(ElConstants.SESSION_USERID):kledge.getXiugaiuserid()));
		int id = knowledgeManageDao.updateKledge(kledge);
		if(id<=0){
			this.setElmessage("修改知识失败!");
			return "error";
		}
		//插入前删除用户和部门信息
		knowledgeManageDao.deleteCompetenceById(id);
		//按照人员分配
		this.update_allocationByUser(id, userids_view,KnowledgeManageConstants.VIEW_TABLE,KnowledgeManageConstants.VIEW_COMPETENCETYPE);
		this.update_allocationByUser(id, userids_update,KnowledgeManageConstants.UPDATE_TABLE,KnowledgeManageConstants.UPDATE_COMPETENCETYPE);
		this.update_allocationByUser(id, userids_delete,KnowledgeManageConstants.DELETE_TABLE,KnowledgeManageConstants.DELETE_COMPETENCETYPE);
		this.update_allocationByUser(id, userids_copy,KnowledgeManageConstants.COPY_TABLE,KnowledgeManageConstants.COPY_COMPETENCETYPE);
		this.update_allocationByUser(id, userids_download,KnowledgeManageConstants.DOWNLOAD_TABLE,KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE);
		
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}	
		}
		return resultPage;
	}
	//申请修改
	public String myaddApplyUpate() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					knowledgeManageDao.changeStatus(id, KnowledgeManageConstants.STATUS_2);
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//申请删除
	public String myaddApplyDel() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					knowledgeManageDao.changeStatus(id, KnowledgeManageConstants.STATUS_3);
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//个人提交审核
	public String commitBySelf() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					knowledgeManageDao.changeStatus(id, KnowledgeManageConstants.STATUS_5);
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//初审通过
	public String verifypass() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					auditMark.setEntityid(id);
					auditMark.setModuleid(auditMark.getModuleid().toUpperCase());
					knowledgeManageDao.changeStatus(id, KnowledgeManageConstants.STATUS_6);
					//添加备注信息
					knowledgeManageDao.addMark(id,auditMark,KnowledgeManageConstants.STATUS_6);
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//初审不通过
	public String verifynopass() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					auditMark.setEntityid(id);
					auditMark.setModuleid(auditMark.getModuleid().toUpperCase());
					knowledgeManageDao.changeStatus(id, KnowledgeManageConstants.STATUS_7);
					//添加备注信息
					knowledgeManageDao.addMark(id,auditMark,KnowledgeManageConstants.STATUS_7);
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//终审通过
	public String verifypassFinal() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					auditMark.setEntityid(id);
					auditMark.setModuleid(auditMark.getModuleid().toUpperCase());
					knowledgeManageDao.changeStatus(id, KnowledgeManageConstants.STATUS_9);
					//添加备注信息
					knowledgeManageDao.addMark(id,auditMark,KnowledgeManageConstants.STATUS_9);
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//终审不通过
	public String verifynopassFinal() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					auditMark.setEntityid(id);
					auditMark.setModuleid(auditMark.getModuleid().toUpperCase());
					knowledgeManageDao.changeStatus(id, KnowledgeManageConstants.STATUS_10);
					//添加备注信息
					knowledgeManageDao.addMark(id,auditMark,KnowledgeManageConstants.STATUS_10);
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//允许修改
	public String myaddAllowUpate() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					knowledgeManageDao.changeStatus(id, KnowledgeManageConstants.STATUS_0);
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//允许删除
	public String myaddAllowDel() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					knowledgeManageDao.deleteKledgeById(id);//即删除
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//不允许修改
	public String myaddNoAllowUpate() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					knowledgeManageDao.changeStatus(id, KnowledgeManageConstants.STATUS_9);
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//不允许删除
	public String myaddNoAllowDel() throws ElException{
		String resultPage = "";
		String[] ids_array = null;
		int id = 0;
		if(ids !=null && !ids.equals("")){
			ids_array = ids.split(",");
			if(ids_array!=null){
				for(int i=0;i<ids_array.length;i++){
					id = Integer.parseInt(ids_array[i]);
					knowledgeManageDao.changeStatus(id, KnowledgeManageConstants.STATUS_9);
				}
			}
		}
		if(listType!=0){
			if(listType == 1){//我添加的
				resultPage = "listMyKledge";
			}else if(listType == 2){//初审
				resultPage = "kledgeChushen";
			}else if(listType == 3){//终审
				resultPage = "kledgeZhongshen";
			}
		}
		return resultPage;
	}
	//选择知识类别
	public String selectKnowledgeTreeInit() throws ElException{
		knowledgeTree = knowledgeTreeDao.getknowledgeTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		return "selectKnowledgeTreeInit";
	}
	//按人员授权
	public String competenceByUserInit() throws ElException{
		sub_department = 1;
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		if (department == null || department.getId() <= 0) {
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		elUsers = userDao.listUsers(department,station, sub_department, elUser,
				getPageNow(), getPageSize());
		count = userDao.listUsersSize(department,station, sub_department, elUser);
//		elUsers = userDao.getDistributionStudents(department, depid, elUser,
//				getSessionIntValue(ElConstants.SESSION_ROLE), getPageNow(),
//				getPageSize());
//		count = userDao.getDistributionStudentsCount(department, depid,
//				elUser, getSessionIntValue(ElConstants.SESSION_ROLE));
		elUser = elUser == null ? new ELUser() : elUser;
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		gangweis = userDao.getBaseDatatByTypeid(4);
		dishis = userDao.getBaseDatatByTypeid(5);
		roles = roleDao.listRoles();
		return "competenceByUserInit";
	}
	//按部门授权
	public String depGrant() throws ElException{
		System.out.println(listType);
		String[] chkstr = this.getRequest().getParameterValues("chkNames");
		//删除权限表中原有的数据
		departmentDao.deleteCompetenceUserOpGrant(kledge.getId(),KnowledgeManageUtil.getCompetenceTableByCompetenceType(competenceType));
		if (chkstr != null) {
			departments = new ArrayList<Department>();
			for (int i = 0; i < chkstr.length; i++) {
				departments.add(new Department(Integer.parseInt(chkstr[i])));
			}
		}
		if (departments != null) {
			ElNodeSQL elnodesql = (ElNodeSQL) SpringContextUtil
					.getBean(ElConstants.CLASS_ELNODESQL);
			depTree = departmentDao.getDepTree_level_competence(kledge.getId(), KnowledgeManageUtil.getCompetenceTableByCompetenceType(competenceType), -1,true);
			for (int i = 0; i < departments.size(); i++) {
				departmentDao.addCompetenceOpusers(KnowledgeManageUtil.getCompetenceTableByCompetenceType(competenceType),competenceType, kledge.getId(), departments
						.get(i).getId());
			}

		}
		return "depGrant";
	}
	//按部门授权
	public String competenceByDepartmentInit() throws ElException{
		String resultPage = "";
		if(fromAdd == 1){
			resultPage = "competenceByDepartmentInit_add";
		}else {
			resultPage = "competenceByDepartmentInit";
		}
//		depTree = departmentDao.getDepTree_level1(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//				true);
//		if (department == null || department.getId() <= 0) {
//			sub_department = 1;
//			department = depTree;
//		} else
//			department = departmentDao.getDepById(department.getId());
//		
//		departments = departmentDao.getDepTree_level1(getSessionIntValue(ElConstants.SESSION_USERID), "op",
//				-1, false).getChild();
//		treeAllId = userDao.getTreeAllId(department, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT),
					-1, true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					-1, true);
		}
		if(kledge!=null&&kledge.getId()>0){
			String table = KnowledgeManageUtil.getCompetenceTableByCompetenceType(competenceType);
			departments = departmentDao.getDepTree_level_competence(kledge.getId(), table,
					-1, false).getChild();
		}
		return resultPage;
	}
	//添加知识
	public String addKledge() throws ElException{
		kledge = kledge == null?new Kledge():kledge;
		kledge.setFabutime(new Timestamp(System.currentTimeMillis()));
		int id = knowledgeManageDao.addKledge(kledge);
		if(id<=0){
			this.setElmessage("添加知识失败!");
			return "error";
		}
		
		//按照部门或者人员分配
		this.add_allocationByUserOrDep(id, departments_view, userids_view,KnowledgeManageConstants.VIEW_TABLE,KnowledgeManageConstants.VIEW_COMPETENCETYPE);
		this.add_allocationByUserOrDep(id, departments_update, userids_update,KnowledgeManageConstants.UPDATE_TABLE,KnowledgeManageConstants.UPDATE_COMPETENCETYPE);
		this.add_allocationByUserOrDep(id, departments_delete, userids_delete,KnowledgeManageConstants.DELETE_TABLE,KnowledgeManageConstants.DELETE_COMPETENCETYPE);
		this.add_allocationByUserOrDep(id, departments_copy, userids_copy,KnowledgeManageConstants.COPY_TABLE,KnowledgeManageConstants.COPY_COMPETENCETYPE);
		this.add_allocationByUserOrDep(id, departments_download, userids_download,KnowledgeManageConstants.DOWNLOAD_TABLE,KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE);
		
		return "addKledge_success";
	}
	//我添加的知识
	public String listMyKledge() throws ElException{
		kledges = knowledgeManageDao.listMyKledge(getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(),getPageSize());
		count = knowledgeManageDao.listMyKledgeSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "listMyKledge";
	}
	//知识初审
	public String kledgeChushen() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		kledges = knowledgeManageDao.listKledgeAll(department,KnowledgeManageConstants.STATUS_ALL,getPageNow(),getPageSize());
		count = knowledgeManageDao.listKledgeAllSize(department,KnowledgeManageConstants.STATUS_ALL);
		return "kledgeChushen";
	}
	//知识终审
	public String kledgeZhongshen() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		kledges = knowledgeManageDao.listKledgeAll(department,KnowledgeManageConstants.STATUS_ALL,getPageNow(),getPageSize());
		count = knowledgeManageDao.listKledgeAllSize(department,KnowledgeManageConstants.STATUS_ALL);
		return "kledgeZhongshen";
	}
	public String kledge_hotset()throws ElException{
		knowledgeManageDao.kledgeHotSet(knowledge.getId(),kledge.getHot());
		return "kledge_hotset";
	}
	//知识查询
	public String kledgeSearch() throws ElException{
		String ids = "";
		String ids1 = "";
		String ids2 = "";
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			kledges = knowledgeManageDao.listKledgeAll(department,KnowledgeManageConstants.STATUS_9,getPageNow(),getPageSize());
			count = knowledgeManageDao.listKledgeAllSize(department,KnowledgeManageConstants.STATUS_9);
		}else {//本人获得授权的知识+本人所在部门获取授权的知识
			//获取本人获得授权的知识ids、本部门获得授权的知识ids
			elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
			ids1 = knowledgeManageDao.getKledgeByCompetence(elUser.getId());
			Department dep = departmentDao.getDepById(elUser.getDepartment().getId());
			ids2 = knowledgeManageDao.getKledgeByCompetence1(dep,departmentDao);

			if(ids1!=null&&!ids1.equals("")){
				if(String.valueOf(ids1.charAt(ids1.length()-1)).equals(",")){
					ids1 = ids1.substring(0,ids1.lastIndexOf(","));
				}
				ids = ids + ids1;
			}
			if(ids2!=null&&!ids2.equals("")){
				if(ids!=null&&!ids.equals("")){
					ids = ids + "," + ids2;
				}else{
					ids = ids2;
				}
			}
			
			
			
			kledges = knowledgeManageDao.listKledgeSearch(ids,getPageNow(),getPageSize());
			count = knowledgeManageDao.listKledgeSearchSize(ids);
		}
		return "kledgeSearch";
	}
	
	////////////////////////////////////
	public String getSqlByDep(int kledgeid,int competenceType) throws ElException{
		String sql = "";
		List<Department> deps = departmentDao.getDepTree_level_competence(kledge.getId(), KnowledgeManageUtil.getCompetenceTableByCompetenceType(competenceType),
				-1, false).getChild();
		sql = KnowledgeManageUtil.getSqlByDeps(deps);
		return sql;
	}
	//修改时获取该事实权限的人数
	public Kledge getCts(List<Competence> ct,Kledge kledge,int icc,int ccount,int type) throws ElException{
		Map<String,String> map = null;
		String userids = "";
		String[] array = null;
		switch (type) {
		case 1:
			map = knowledgeManageDao.getCompetencesSizeByDepid(kledge.getId(),KnowledgeManageConstants.VIEW_TABLE, KnowledgeManageConstants.VIEW_COMPETENCETYPE);
			ccount = Integer.parseInt(map.get("count"));
			userids = map.get("userids");
			if(userids!=null&&!userids.equals(""))	{
				array = userids.split(",");
			}
			//userids去除相同的
			
			ct = knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.VIEW_TABLE, KnowledgeManageConstants.VIEW_COMPETENCETYPE);
			for(int i=0;i<ct.size();i++){
				if(ct.get(i).getEu()!=null){
					//按照人员授权的
					if(!KnowledgeManageUtil.checkUseridIsIn(array,ct.get(i).getEu().getId())){
						icc += 1;
					}
				}
			}
			kledge.setCt_views(ct);
			kledge.setCount_view(icc+ccount);
			break;
		case 2:
			map = knowledgeManageDao.getCompetencesSizeByDepid(kledge.getId(),KnowledgeManageConstants.UPDATE_TABLE, KnowledgeManageConstants.UPDATE_COMPETENCETYPE);
			ccount = Integer.parseInt(map.get("count"));
			userids = map.get("userids");
			if(userids!=null&&!userids.equals(""))	{
				array = userids.split(",");
			}
			//userids去除相同的
			
			ct = knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.UPDATE_TABLE, KnowledgeManageConstants.UPDATE_COMPETENCETYPE);
			for(int i=0;i<ct.size();i++){
				if(ct.get(i).getEu()!=null){
					//按照人员授权的
					if(!KnowledgeManageUtil.checkUseridIsIn(array,ct.get(i).getEu().getId())){
						icc += 1;
					}
				}
			}
			kledge.setCt_updates(ct);
			kledge.setCount_update(icc+ccount);
			break;
		case 3:
			map = knowledgeManageDao.getCompetencesSizeByDepid(kledge.getId(),KnowledgeManageConstants.DELETE_TABLE, KnowledgeManageConstants.DELETE_COMPETENCETYPE);
			ccount = Integer.parseInt(map.get("count"));
			userids = map.get("userids");
			if(userids!=null&&!userids.equals(""))	{
				array = userids.split(",");
			}
			//userids去除相同的
			
			ct = knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.DELETE_TABLE, KnowledgeManageConstants.DELETE_COMPETENCETYPE);
			for(int i=0;i<ct.size();i++){
				if(ct.get(i).getEu()!=null){
					//按照人员授权的
					if(!KnowledgeManageUtil.checkUseridIsIn(array,ct.get(i).getEu().getId())){
						icc += 1;
					}
				}
			}
			kledge.setCt_deletes(ct);
			kledge.setCount_delete(icc+ccount);
			break;
		case 4:
			map = knowledgeManageDao.getCompetencesSizeByDepid(kledge.getId(),KnowledgeManageConstants.COPY_TABLE, KnowledgeManageConstants.COPY_COMPETENCETYPE);
			ccount = Integer.parseInt(map.get("count"));
			userids = map.get("userids");
			if(userids!=null&&!userids.equals(""))	{
				array = userids.split(",");
			}
			//userids去除相同的
			
			ct = knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.COPY_TABLE, KnowledgeManageConstants.COPY_COMPETENCETYPE);
			for(int i=0;i<ct.size();i++){
				if(ct.get(i).getEu()!=null){
					//按照人员授权的
					if(!KnowledgeManageUtil.checkUseridIsIn(array,ct.get(i).getEu().getId())){
						icc += 1;
					}
				}
			}
			kledge.setCt_copys(ct);
			kledge.setCount_copy(icc+ccount);
			break;
		case 5:
			map = knowledgeManageDao.getCompetencesSizeByDepid(kledge.getId(),KnowledgeManageConstants.DOWNLOAD_TABLE, KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE);
			ccount = Integer.parseInt(map.get("count"));
			userids = map.get("userids");
			if(userids!=null&&!userids.equals(""))	{
				array = userids.split(",");
			}
			//userids去除相同的
			
			ct = knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.DOWNLOAD_TABLE, KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE);
			for(int i=0;i<ct.size();i++){
				if(ct.get(i).getEu()!=null){
					//按照人员授权的
					if(!KnowledgeManageUtil.checkUseridIsIn(array,ct.get(i).getEu().getId())){
						icc += 1;
					}
				}
			}
			kledge.setCt_downloads(ct);
			kledge.setCount_download(icc+ccount);
			break;
//			ct = knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.DOWNLOAD_TABLE, KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE);
//			for(int i=0;i<ct.size();i++){
//				if(ct.get(i).getEu()!=null){
//					icc += 1;
//				}
//			}
//			ct = knowledgeManageDao.getCompetencesById(kledge.getId(), KnowledgeManageConstants.DOWNLOAD_TABLE, KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE);
//			ccount = knowledgeManageDao.getCompetencesSizeByDepid(kledge.getId(),KnowledgeManageConstants.DOWNLOAD_TABLE, KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE);
//			kledge.setCt_downloads(ct);
//			kledge.setCount_download(kledge.getCt_downloads()!=null?(kledge.getCt_downloads().size()-icc+ccount):0);
//			break;
		default:
			break;
		}
		
		return kledge;
	}
	//添加页面按照部门或人员分配
	public void add_allocationByUserOrDep(int id,String departments_type,String users_type,String tablename,int type) throws ElException{
		this.allocationByDep(id, departments_type,tablename,type);
		this.allocationByUser(id, users_type,tablename,type);
	}
	//修改按照人员分配
	public void update_allocationByUser(int id,String users_type,String tablename,int type) throws ElException{
		this.allocationByUser(id, users_type,tablename,type);
	}
	//按照部门分配
	public void allocationByDep(int id,String departments_type,String tablename,int type) throws ElException{
		String[] departmentids_array = null;
		if(departments_type!=null&&!departments_type.equals("")){
			departmentids_array = departments_type.split(",");
			if(departmentids_array!=null){
				for(int i=0;i<departmentids_array.length;i++){
					knowledgeManageDao.addCompetenceByDepartmentid(tablename,id,Integer.parseInt(departmentids_array[i]),type);
				}
			}
		}
	}
	//按照人员分配
	public void allocationByUser(int id,String users_type,String tablename,int type) throws ElException{
		String[] userids_array = null;
		if(users_type!=null&&!users_type.equals("")){
			userids_array = users_type.split(",");
			if(userids_array!=null){
				for(int i=0;i<userids_array.length;i++){
					knowledgeManageDao.addCompetenceByUserid(tablename, id,Integer.parseInt(userids_array[i]),type);
				}
			}
		}
	}
	//////////////////////////////////////////////
	public String getKnowledgeMark() throws ElException{
		ams = knowledgeManageDao.getKnowledgeMark(tablename,id,getPageNow(),getPageSize());
		count = knowledgeManageDao.getKnowledgeMarkSize(tablename,id);
		return "getKnowledgeMark";
	}
	
	
	
	////////////////////////
	///gets   sets
	public KnowledgeManageDao getKnowledgeManageDao() {
		return knowledgeManageDao;
	}
	public void setKnowledgeManageDao(KnowledgeManageDao knowledgeManageDao) {
		this.knowledgeManageDao = knowledgeManageDao;
	}
	public KnowledgeTree getKlTree() {
		return klTree;
	}
	public void setKlTree(KnowledgeTree klTree) {
		this.klTree = klTree;
	}
	public Kledge getKledge() {
		return kledge;
	}
	public void setKledge(Kledge kledge) {
		this.kledge = kledge;
	}
	public KnowledgeTreeDao getKnowledgeTreeDao() {
		return knowledgeTreeDao;
	}
	public void setKnowledgeTreeDao(KnowledgeTreeDao knowledgeTreeDao) {
		this.knowledgeTreeDao = knowledgeTreeDao;
	}
	public KnowledgeTree getKnowledgeTree() {
		return knowledgeTree;
	}
	public void setKnowledgeTree(KnowledgeTree knowledgeTree) {
		this.knowledgeTree = knowledgeTree;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public Department getDepTree() {
		return depTree;
	}
	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public int getSub_department() {
		return sub_department;
	}
	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}
	public List<ELUser> getElUsers() {
		return elUsers;
	}
	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public List<ElRole> getRoles() {
		return roles;
	}
	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
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
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	public String getUserids_view() {
		return userids_view;
	}
	public void setUserids_view(String userids_view) {
		this.userids_view = userids_view;
	}
	public String getUserids_copy() {
		return userids_copy;
	}
	public void setUserids_copy(String userids_copy) {
		this.userids_copy = userids_copy;
	}
	public String getUserids_update() {
		return userids_update;
	}
	public void setUserids_update(String userids_update) {
		this.userids_update = userids_update;
	}
	public String getUserids_download() {
		return userids_download;
	}
	public void setUserids_download(String userids_download) {
		this.userids_download = userids_download;
	}
	public String getUserids_delete() {
		return userids_delete;
	}
	public void setUserids_delete(String userids_delete) {
		this.userids_delete = userids_delete;
	}
	public String getDepartments_view() {
		return departments_view;
	}
	public void setDepartments_view(String departments_view) {
		this.departments_view = departments_view;
	}
	public String getDepartments_copy() {
		return departments_copy;
	}
	public void setDepartments_copy(String departments_copy) {
		this.departments_copy = departments_copy;
	}
	public String getDepartments_update() {
		return departments_update;
	}
	public void setDepartments_update(String departments_update) {
		this.departments_update = departments_update;
	}
	public String getDepartments_download() {
		return departments_download;
	}
	public void setDepartments_download(String departments_download) {
		this.departments_download = departments_download;
	}
	public String getDepartments_delete() {
		return departments_delete;
	}
	public void setDepartments_delete(String departments_delete) {
		this.departments_delete = departments_delete;
	}
	public List<Kledge> getKledges() {
		return kledges;
	}
	public void setKledges(List<Kledge> kledges) {
		this.kledges = kledges;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Competence getCompetence() {
		return competence;
	}
	public void setCompetence(Competence competence) {
		this.competence = competence;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public int getListType() {
		return listType;
	}
	public void setListType(int listType) {
		this.listType = listType;
	}
	public AuditMark getAuditMark() {
		return auditMark;
	}
	public void setAuditMark(AuditMark auditMark) {
		this.auditMark = auditMark;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TagsDao getTagsDao() {
		return tagsDao;
	}
	public void setTagsDao(TagsDao tagsDao) {
		this.tagsDao = tagsDao;
	}
	public List<AuditMark> getAms() {
		return ams;
	}
	public void setAms(List<AuditMark> ams) {
		this.ams = ams;
	}
	public int getFromView() {
		return fromView;
	}
	public void setFromView(int fromView) {
		this.fromView = fromView;
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
	public List getTreeAllId() {
		return treeAllId;
	}
	public void setTreeAllId(List treeAllId) {
		this.treeAllId = treeAllId;
	}
	public int getCompetenceType() {
		return competenceType;
	}
	public void setCompetenceType(int competenceType) {
		this.competenceType = competenceType;
	}
	public int getFromAdd() {
		return fromAdd;
	}
	public void setFromAdd(int fromAdd) {
		this.fromAdd = fromAdd;
	}
	public List<Kledge> getTjkledges() {
		return tjkledges;
	}
	public void setTjkledges(List<Kledge> tjkledges) {
		this.tjkledges = tjkledges;
	}
	public List<Kledge> getZztjkledges() {
		return zztjkledges;
	}
	public void setZztjkledges(List<Kledge> zztjkledges) {
		this.zztjkledges = zztjkledges;
	}

}
