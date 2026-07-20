package com.sopia.schedule.action.dataallocation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.schedule.DataAllocationUtil;
import com.sopia.schedule.dao.ClientTemplateDao;
import com.sopia.schedule.dao.ModuleManageDao;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.dao.dataallocation.DataAllocationDao;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.ModuleZDY;
import com.sopia.schedule.entities.Tags;
import com.sopia.schedule.entities.dataallocation.DataAllocation;

/**
 * 数据分配action
 * @author Administrator
 *
 */
public class DataAllocationAction extends BaseAction{
	private DataAllocationDao dataAllocationDao;
	private ModuleManageDao moduleManageDao;
	private TagsDao tagsDao;
	private DepartmentDao departmentDao;
	private UserDao userDao;
	private DataAllocation dataAllocation;
	private List<DataAllocation> dataAllocationList;
	private ModuleManage moduleManage;
	private String tablename;
	private List<Tags> list_tags;
	private List<Map<String,String>> list_designe;
	private int count;
	private Department depTree;
	private Department department;
	private int sub_department;
	private int id;
	private String ids;
	private List<ELUser> elusers;
	private ELUser eluser;
	private String userids;
	private int userid;
	private int type;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	private Map<String,List<Map<String, String>>> list_designe_relate;
	private Map<String,List<Tags>> list_tags_relate;
	private Tags tags;
	private String actionName;
	
	private ClientTemplateDao clientTemplateDao;
	private ModuleZDY moduleZDY;
	

	
	///////////////////////////////////////
	//actions
	/**
	 * 数据分配
	 */
	public String dataAllocationInit() throws ElException{
		actionName = "dataAllocationInit";
		
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
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
		
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		
		String order = "";
		if (tags != null) {
			if (tags.getOrdercolumn() != null
					&& !tags.getOrdercolumn().equals("")) {
				order = " order by ";
				order += tags.getOrdercolumn() + " ";
				order += tags.getOrdersc();
			}
		}
		
		if(list_tags!=null&&list_tags.size()>0){
			list_designe = dataAllocationDao.listDataAllocation(list_tags,moduleManage,department,getPageNow(), getPageSize(),tablename,order);
			count = dataAllocationDao.listDataAllocationSize(list_tags,moduleManage,department,getPageNow(), getPageSize(),tablename);
		}
		list_designe = list_designe==null?new ArrayList<Map<String,String>>():list_designe;
		return "dataAllocationInit_success";
	}
	
	/**
	 * 人员分配
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String dataAllocationFenpeiInit() throws ElException, UnsupportedEncodingException{
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());//模块自定义信息
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
		
		elusers = dataAllocationDao.listUsers(sub_department,department,eluser,getPageNow(), getPageSize(),id);
		count = dataAllocationDao.listUsersSize(sub_department,department,eluser,id);
		
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		zhijis = userDao.getBaseDatatByTypeid(3);
		gangweis = userDao.getBaseDatatByTypeid(4);
		dishis = userDao.getBaseDatatByTypeid(5);
		return "dataAllocationFenpeiInit_success";
	}
	
	/**
	 * 填写开始时间和结束时间页面
	 * @return
	 * @throws ElException
	 */
	public String addLearnTimeInit() throws ElException{
		return "addLearnTimeInit_success";
	}
	
	/**
	 * 分配人员操作
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String dataAllocationFenpei() throws ElException, UnsupportedEncodingException{
		//moduleManage,id,userids,dataAllocation
		String[] ids = null;
		if(userids!=null&&!userids.equals("")){
			//删除原有信息
//			dataAllocationDao.deleteDataAllocationAll(id,moduleManage);
			ids = userids.split(",");
			if(ids!=null&&ids.length>0){
				for(int i=0;i<ids.length;i++){
					dataAllocationDao.insertDataAllocation(id,Integer.parseInt(ids[i]),moduleManage,dataAllocation);
				}
				setElmessage(URLEncoder.encode(URLEncoder.encode("分配成功!!!", "UTF-8"), "UTF-8"));
			}
		}
		return "dataAllocationFenpei_success";
	}
	
	/**
	 * 取消分配人员操作
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String dataAllocationUnFenpei() throws ElException, UnsupportedEncodingException{
		//moduleManage,id,userids,dataAllocation
		String[] ids = null;
		if(userids!=null&&!userids.equals("")){
			ids = userids.split(",");
			if(ids!=null&&ids.length>0){
				for(int i=0;i<ids.length;i++){
					dataAllocationDao.deleteDataAllocation(id,Integer.parseInt(ids[i]),moduleManage);
				}
				setElmessage(URLEncoder.encode(URLEncoder.encode("取消分配成功!!!", "UTF-8"), "UTF-8"));
			}
		}
		return "dataAllocationUnFenpei_success";
	}
	
	/**
	 * 审核通过
	 * @return
	 * @throws ElException
	 */
	public String dataAllocationAudit() throws ElException, UnsupportedEncodingException{
		//moduleManage,id,userids
		String[] ids = null;
		if(userids!=null&&!userids.equals("")){
			ids = userids.split(",");
			if(ids!=null&&ids.length>0){
				for(int i=0;i<ids.length;i++){
					dataAllocationDao.updateDataAllocation(id,Integer.parseInt(ids[i]),moduleManage,1,dataAllocation);
				}
				setElmessage(URLEncoder.encode(URLEncoder.encode("审核通过成功!!!", "UTF-8"), "UTF-8"));
			}
		}
		return "dataAllocationAudit_success";
	}
	
	/**
	 * 审核不通过
	 * @return
	 * @throws ElException
	 */
	public String dataAllocationUnAudit() throws ElException, UnsupportedEncodingException{
		//moduleManage,id,userids
		String[] ids = null;
		if(userids!=null&&!userids.equals("")){
			ids = userids.split(",");
			if(ids!=null&&ids.length>0){
				for(int i=0;i<ids.length;i++){
					dataAllocationDao.updateDataAllocation(id,Integer.parseInt(ids[i]),moduleManage,2,dataAllocation);
				}
				setElmessage(URLEncoder.encode(URLEncoder.encode("审核不通过成功!!!", "UTF-8"), "UTF-8"));
			}
		}
		return "dataAllocationUnAudit_success";
	}
	
	/**
	 * 我获得分配的数据
	 * @return
	 * @throws ElException
	 */
	public String myGetDataAllocationInit() throws ElException{
		actionName = "myGetDataAllocationInit";
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());//模块自定义信息
		if(list_tags!=null&&list_tags.size()>0){
			//获取分配的ids
			String ids = dataAllocationDao.myGetDataAllocationIds(moduleManage,getSessionIntValue(ElConstants.SESSION_USERID),tablename);
			if(ids!=null&&!ids.equals("")){
				list_designe = dataAllocationDao.listMyGetDataAllocation(list_tags,moduleManage,getPageNow(), getPageSize(),tablename,ids);
				count = dataAllocationDao.listMyGetDataAllocationSize(list_tags,moduleManage,getPageNow(), getPageSize(),tablename,ids);
			}
		}
		list_designe = list_designe==null?new ArrayList<Map<String,String>>():list_designe;
		return "myGetDataAllocationInit_success";
	}
	
	/**
	 * 查看
	 * @return
	 * @throws ElException
	 */
	public String data_view() throws ElException{
		actionName = "data_view";
		//id,tablename,userid
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		
		//系统时间是否在查看时间范围内
		dataAllocation = dataAllocationDao.select_dataAllocation_by_moduleid_userid_entityid(moduleManage,getSessionIntValue(ElConstants.SESSION_USERID),id);
		if(dataAllocation!=null){
			if(!DataAllocationUtil.checkNowDataIsInDataAllocation(dataAllocation)){
				this.setElmessage("不在有效时间段范围内，不能查看!!!");
				return "error";
			}
		}
		
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		list_tags = tagsDao.select_designe_field_content_by_id(list_tags,tablename, id);
		
		
		return "data_view_success";
	}
	/**
	 * 学习
	 * @return
	 * @throws ElException
	 */
	public String data_learn() throws ElException{
		actionName = "data_learn";
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		list_tags_relate = new HashMap<String,List<Tags>>();
		list_designe_relate = new HashMap<String,List<Map<String, String>>>();
		//id,tablename,userid
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		
		//系统时间是否在查看时间范围内
		dataAllocation = dataAllocationDao.select_dataAllocation_by_moduleid_userid_entityid(moduleManage,getSessionIntValue(ElConstants.SESSION_USERID),id);
		if(dataAllocation!=null){
			if(!DataAllocationUtil.checkNowDataIsInDataAllocation(dataAllocation)){
				this.setElmessage("不在有效时间段范围内，不能学习!!!");
				return "error";
			}
		}
		
		Map<String, String> hm = new HashMap<String, String>();
		String order = "";
		if (tags != null) {
			if (tags.getOrdercolumn() != null
					&& !tags.getOrdercolumn().equals("")) {
				order = " order by ";
				order += tags.getOrdercolumn() + " ";
				order += tags.getOrdersc();
			}
		}
		// 相关列
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDisplay_type().equals("相关字段")
					&& list_tags.get(i).getRelateIsShowComplete() == 1) {
				String ids_relate = tagsDao
						.getRelateIds(list_tags.get(i).getTable_name(),
								list_tags.get(i).getColumn_name(), id);
				String result = "";
				String[] array = new String[1];
				if (ids_relate != null && !ids_relate.equals(",")) {
					array = new String[ids_relate.split(",").length];
					array = ids_relate.split(",");
				}
				result += "(";
				if (array.length > 0) {
					for (int j = 0; j < array.length; j++) {
						if (j == array.length - 1)
							result += array[j];
						else
							result += array[j] + ",";
					}
				}
				result += ")";


				// 普通表和结果表做不同处理
				// 普通表
				if (tagsDao.checkTable(list_tags.get(i).getDefault_value()
						.split("==")[0].toUpperCase()) == 1) {
					list_tags_relate.put(list_tags.get(i).getColumn_name(), tagsDao.select_designe_field_by_tablename(list_tags.get(i)
									.getDefault_value().split("==")[0]
									.toUpperCase())) ;
					String sqlAppend = " and t.id in (select relateid from tb_tags_relate where columnname = '"
							+ list_tags.get(i).getColumn_name().toUpperCase()
							+ "' and relateid in " + result + "    )";
					list_designe_relate.put(list_tags.get(i).getColumn_name(), tagsDao
							.select_my_tableinfo_by_userid_order(sqlAppend, 1,
									list_tags_relate.get(list_tags.get(i).getColumn_name()), list_tags.get(i)
											.getDefault_value().split("==")[0]
											.toUpperCase(), hm, userid, order,
									getPageNow(), getPageSize()));
				}

				// 结果表
				else {
					// 查出过程表
					list_tags_relate.put(list_tags.get(i).getColumn_name(), tagsDao.select_designe_field_by_Producetablename(tablename,tagsDao.getProduceTableByResultTable(list_tags.get(i).getDefault_value().split("==")[0].toUpperCase()))) ;
					String sqlAppend = " and t.moduleid =  '" + tablename
							+ "' and t.danjuid = " + id;
					list_designe_relate.put(list_tags.get(i).getColumn_name(), tagsDao
							.select_my_tableinfo_by_userid_order(sqlAppend,1,list_tags_relate.get(list_tags.get(i).getColumn_name()),tagsDao.getProduceTableByResultTable(list_tags.get(i).getDefault_value().split("==")[0].toUpperCase()), hm,
									userid, order, getPageNow(), getPageSize()));
				}
			}
		}
		return "data_learn_success";
	}
	
	// 判断有无业务进度，有，更新整个表的业务进度字段
	public void updateYewuJindu(String tablename) throws ElException {
		String yewu_jindu_column = tagsDao.IfHasYewuJindu_column(id, tablename);
		if (yewu_jindu_column != null && !yewu_jindu_column.equals("")
				&& yewu_jindu_column.indexOf(",") > 0) {
			String yewu_jindu = yewu_jindu_column;
			if (yewu_jindu == null && yewu_jindu.equals("")
					&& yewu_jindu.indexOf(",") <= 0)
				yewu_jindu = "";
			String[] yewu = yewu_jindu.split(",");
			yewu_jindu = "";
			for (int x = 0; x < yewu.length; x++) {// 将需要计算的字段id转换为字段名称
				if (x == yewu.length - 1)
					yewu_jindu += tagsDao.getColumn_name_by_id(Integer
							.parseInt(yewu[x]));
				else
					yewu_jindu += tagsDao.getColumn_name_by_id(Integer
							.parseInt(yewu[x]))
							+ ",";
			}
			for (int x = 0; x < yewu.length; x++) {// 将需要计算的字段id转换为字段名称
				yewu[x] = tagsDao.getColumn_name_by_id(Integer
						.parseInt(yewu[x]));
			}
			tagsDao.updateYewuJindu(tablename, yewu_jindu, list_tags);
		}
	}
	
	/**
	 * 数据申请初始化
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String dataApplicationInit() throws ElException, UnsupportedEncodingException{
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());//模块自定义信息
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		
		actionName = "dataApplicationInit";
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
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
		
		String order = "";
		if (tags != null) {
			if (tags.getOrdercolumn() != null
					&& !tags.getOrdercolumn().equals("")) {
				order = " order by ";
				order += tags.getOrdercolumn() + " ";
				order += tags.getOrdersc();
			}
		}
		
		if(list_tags!=null&&list_tags.size()>0){
			list_designe = dataAllocationDao.listDataApplication(getSessionIntValue(ElConstants.SESSION_USERID),list_tags,moduleManage,department,getPageNow(), getPageSize(),tablename,order);
			count = dataAllocationDao.listDataApplicationSize(getSessionIntValue(ElConstants.SESSION_USERID),list_tags,moduleManage,department,getPageNow(), getPageSize(),tablename);
		}
		list_designe = list_designe==null?new ArrayList<Map<String,String>>():list_designe;
		
		return "dataApplicationInit_success";
	}
	
	/**
	 * 数据申请
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String dataApplication() throws ElException, UnsupportedEncodingException{
		actionName = "dataApplication";
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		id=0;
		String[] array = null;
		if(ids!=null && !ids.equals("")){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				//ids,tablename
				//验证是否已经被超级管理员后台分配
				dataAllocation = dataAllocationDao.select_dataAllocation_by_moduleid_userid_entityid(moduleManage,getSessionIntValue(ElConstants.SESSION_USERID),id);
				if(dataAllocation!=null){
					this.setElmessage("您申请的数据已经分配给您,不需要再申请!");
					return "error";
				}
				
				dataAllocationDao.insertDataAllocation(id, getSessionIntValue(ElConstants.SESSION_USERID), moduleManage, dataAllocation);
			}
		}
		
		setElmessage(URLEncoder.encode(URLEncoder.encode("申请成功!!!", "UTF-8"), "UTF-8"));
		return "dataApplication_success";
	}
	
	
	
	///////////////////////////////////////
	//gets   sets
	public DataAllocationDao getDataAllocationDao() {
		return dataAllocationDao;
	}

	public void setDataAllocationDao(DataAllocationDao dataAllocationDao) {
		this.dataAllocationDao = dataAllocationDao;
	}

	public ModuleManageDao getModuleManageDao() {
		return moduleManageDao;
	}

	public void setModuleManageDao(ModuleManageDao moduleManageDao) {
		this.moduleManageDao = moduleManageDao;
	}

	public DataAllocation getDataAllocation() {
		return dataAllocation;
	}

	public void setDataAllocation(DataAllocation dataAllocation) {
		this.dataAllocation = dataAllocation;
	}

	public List<DataAllocation> getDataAllocationList() {
		return dataAllocationList;
	}

	public void setDataAllocationList(List<DataAllocation> dataAllocationList) {
		this.dataAllocationList = dataAllocationList;
	}

	public ModuleManage getModuleManage() {
		return moduleManage;
	}

	public void setModuleManage(ModuleManage moduleManage) {
		this.moduleManage = moduleManage;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public TagsDao getTagsDao() {
		return tagsDao;
	}

	public void setTagsDao(TagsDao tagsDao) {
		this.tagsDao = tagsDao;
	}

	public List<Tags> getList_tags() {
		return list_tags;
	}

	public void setList_tags(List<Tags> list_tags) {
		this.list_tags = list_tags;
	}

	public List<Map<String, String>> getList_designe() {
		return list_designe;
	}

	public void setList_designe(List<Map<String, String>> list_designe) {
		this.list_designe = list_designe;
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

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ELUser> getElusers() {
		return elusers;
	}

	public void setElusers(List<ELUser> elusers) {
		this.elusers = elusers;
	}

	public ELUser getEluser() {
		return eluser;
	}

	public void setEluser(ELUser eluser) {
		this.eluser = eluser;
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

	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	

	public Map<String, List<Map<String, String>>> getList_designe_relate() {
		return list_designe_relate;
	}

	public void setList_designe_relate(
			Map<String, List<Map<String, String>>> list_designe_relate) {
		this.list_designe_relate = list_designe_relate;
	}


	public Map<String, List<Tags>> getList_tags_relate() {
		return list_tags_relate;
	}

	public void setList_tags_relate(Map<String, List<Tags>> list_tags_relate) {
		this.list_tags_relate = list_tags_relate;
	}

	public Tags getTags() {
		return tags;
	}

	public void setTags(Tags tags) {
		this.tags = tags;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public ClientTemplateDao getClientTemplateDao() {
		return clientTemplateDao;
	}

	public void setClientTemplateDao(ClientTemplateDao clientTemplateDao) {
		this.clientTemplateDao = clientTemplateDao;
	}

	public ModuleZDY getModuleZDY() {
		return moduleZDY;
	}

	public void setModuleZDY(ModuleZDY moduleZDY) {
		this.moduleZDY = moduleZDY;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	
	
	

}
