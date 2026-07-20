package com.sopia.lable.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.lable.LableUtil;
import com.sopia.lable.common.LableCommon;
import com.sopia.lable.dao.CustomLableDao;
import com.sopia.lable.dao.LableTreeDao;
import com.sopia.lable.entites.CirculationListLable;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.LableTree;
import com.sopia.lable.entites.SearchLable;
import com.sopia.lable.entites.Table;
import com.sopia.lable.entites.TableField;

/**
 * 自定义标签操作action
 * 
 * @author Administrator
 * 
 */
public class CustomLableAction extends BaseAction {
	private final String circulation = "lable_circulation";// 自定义循环标签表表名
	private List<Table> systableList;// 系统表信息集合
	private List<Table> usertableList;// 自定义表信息集合
	private CustomLableDao customLableDao;
	private CirculationListLable circulationListLable;// 自定义循环标签
	private List<Table> lableInTableList;// 自定义标签中的表信息
	private Lable lable;
	private List<TableField> tableFieldList;

	private List<Lable> listLable;

	private int type;
	private Lable searchL;

	// 搜索标签
	private final String searchlable = "lable_search";
	private SearchLable searchLable;
	private LableTreeDao lableTreeDao;
	private LableTree lableTree;
	private LableTree klTree;

	public Lable getSearchL() {
		return searchL;
	}

	public void setSearchL(Lable searchL) {
		this.searchL = searchL;
	}

	public LableTree getKlTree() {
		return klTree;
	}

	public void setKlTree(LableTree klTree) {
		this.klTree = klTree;
	}

	public LableTreeDao getLableTreeDao() {
		return lableTreeDao;
	}

	public void setLableTreeDao(LableTreeDao lableTreeDao) {
		this.lableTreeDao = lableTreeDao;
	}

	public LableTree getLableTree() {
		return lableTree;
	}

	public void setLableTree(LableTree lableTree) {
		this.lableTree = lableTree;
	}

	public SearchLable getSearchLable() {
		return searchLable;
	}

	public void setSearchLable(SearchLable searchLable) {
		this.searchLable = searchLable;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Lable> getListLable() {
		return listLable;
	}

	public void setListLable(List<Lable> listLable) {
		this.listLable = listLable;
	}

	public List<TableField> getTableFieldList() {
		return tableFieldList;
	}

	public void setTableFieldList(List<TableField> tableFieldList) {
		this.tableFieldList = tableFieldList;
	}

	public Lable getLable() {
		return lable;
	}

	public void setLable(Lable lable) {
		this.lable = lable;
	}

	public String getCirculation() {
		return circulation;
	}

	public CirculationListLable getCirculationListLable() {
		return circulationListLable;
	}

	public void setCirculationListLable(
			CirculationListLable circulationListLable) {
		this.circulationListLable = circulationListLable;
	}

	public List<Table> getLableInTableList() {
		return lableInTableList;
	}

	public void setLableInTableList(List<Table> lableInTableList) {
		this.lableInTableList = lableInTableList;
	}

	public List<Table> getSystableList() {
		return systableList;
	}

	public void setSystableList(List<Table> systableList) {
		this.systableList = systableList;
	}

	public List<Table> getUsertableList() {
		return usertableList;
	}

	public void setUsertableList(List<Table> usertableList) {
		this.usertableList = usertableList;
	}

	public CustomLableDao getCustomLableDao() {
		return customLableDao;
	}

	public void setCustomLableDao(CustomLableDao customLableDao) {
		this.customLableDao = customLableDao;
	}
	
	/**
	 * 标签复制
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String copylableToDb() throws ElException, UnsupportedEncodingException{
		String lname = lable.getName()==null?"":lable.getName();
		boolean flag = false;
		if(lable == null || (lable.getType()!=1 && lable.getType()!=2 && lable.getType()!=3)){
			this.setElmessage("参数错误!");
			return "error";
		}
		if(lable.getType()==3){//搜索标签
			searchLable = customLableDao.getLableByTablenameAndName_search(lable.getName());
			if(searchLable!=null){
				//复制   名称为name+'-复制'+8未随机字符
				searchLable.setName(searchLable.getName() + "-复制" + LableUtil.UUID8(8));
				//判断复制后的标签的名称在数据库中是否已经存在
				flag = customLableDao.checkNameIsExist(searchLable.getName(),circulation);
				if(!flag){
					this.setElmessage("该标签名已经存在,添加失败");
					return "error";
				}
				customLableDao.insertDB_copy_search(searchLable);
				
				this.setElmessage("复制标签'"+lname+"'成功,复制的标签为'"+searchLable.getName()+"'");
			}
		}else{//列表或者分页标签
			lable = customLableDao.getLableByTablenameAndName_loop(lable.getName());
			if(lable!=null){
				lable.setName(lable.getName() + "-复制"+LableUtil.UUID8(8));
				flag = customLableDao.checkNameIsExist(lable.getName(),searchlable);
				if(!flag){
					this.setElmessage("该标签名已经存在,添加失败");
					return "error";
				}
				customLableDao.insertDB_copy_loop(lable);
			}
			this.setElmessage("复制标签'"+lname+"'成功,复制的标签为'"+lable.getName()+"'");
		}
		setElmessage(URLEncoder.encode(URLEncoder.encode(this.getElmessage(), "UTF-8"), "UTF-8"));
		//
		return "copylableToDb_success";
	}

	/**
	 * 循环列表标签查询表信息页面
	 * 
	 * @return
	 * @throws ElException
	 */
	public String lable_cirulationListLableinfoAddInit() throws ElException {
		lableTree = lableTreeDao.getLableTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		// 1，先查出来常用表中的信息
		systableList = customLableDao.lable_getsystable();
		// 2，查出来自定义表中的信息
		usertableList = customLableDao.lable_getusertable();
		// 3，查询该自定义标签中已使用的表， 及表字段 信息
		lableInTableList = customLableDao.lable_getlabletableandfield(
				circulationListLable.getName(), circulation);
		lable = circulationListLable;
		lable = customLableDao.lable_getlablesqllable(circulation, lable);
		circulationListLable.setName(lable.getName());
		circulationListLable.setOrder(lable.getOrder());
		circulationListLable.setTablestr(lable.getTablestr());
		circulationListLable.setPageSize(lable.getPageSize());
		circulationListLable.setFieldstr(lable.getFieldstr());
		circulationListLable.setLable(lable.getLable());
		circulationListLable.setSqlCondition(lable.getSqlCondition());
		circulationListLable.setType(lable.getType());
		circulationListLable.setKeyword(lable.getKeyword());
		circulationListLable.setLableTree(lableTreeDao.getLableTreeById(lable.getLabletreeid()));
		return "lable_cirulationListLableAddInit_success";
	}

	/**
	 * 返回循环列表标签添加页面
	 * 
	 * @return
	 */
	public String lable_cirulationListLableNameAddInit() {
		// 返回页面
		return "lable_cirulationListLableNameAddInit_success";

	}

	/**
	 * 添加循环标签名称
	 * 
	 * @return
	 * @throws ElException
	 */
	public String lable_cirulationListLableNameAdd() throws ElException {
		lableTree = lableTreeDao.getLableTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);

		// 验证循环标签名是否重复
		if (LableCommon.check_lableName(circulationListLable.getName(),
				circulation, "name")) {
			// 如果重复
			setElmessage("标签名已被占用，请重新输入");
			return lable_cirulationListLableNameAddInit();

		} else {// 如果不重复， 添加该标签并进入到循环列表标签查询表信息页面
			customLableDao.lable_addlable(circulationListLable.getName(), type);
			return lable_cirulationListLableinfoAddInit();
		}

	}

	/**
	 * 添加循环标签sql条件
	 * 
	 * @return
	 * @throws ElException
	 */
	public String lable_lable_cirulationListLableSQLAdd() throws ElException {
		// 保存标签设置
		circulationListLable.setSqlCondition(circulationListLable
				.getSqlCondition() == null ? "" : circulationListLable
				.getSqlCondition());
		circulationListLable
				.setPageSize(circulationListLable.getPageSize() == null ? 0
						: circulationListLable.getPageSize());
		circulationListLable.setKeyword(circulationListLable.getKeyword()==null?"":circulationListLable.getKeyword());
		customLableDao.lable_updlablesql(circulation, circulationListLable);
		// 查询完整的sql语句并显示
		lable = circulationListLable;
		lable = customLableDao.lable_getlablesqllable(circulation, lable);
		
		//添加类别id和关键词
		if(klTree==null||klTree.getParent()==null||klTree.getParent().getId()<=0){
			this.setElmessage("请选择上级类别");
			return "error";
		}
		customLableDao.updateLableTreeid(circulationListLable.getName(),klTree.getParent().getId(),circulation);
		
		circulationListLable.setName(lable.getName());
		circulationListLable.setOrder(lable.getOrder());
		circulationListLable.setTablestr(lable.getTablestr());
		circulationListLable.setPageSize(lable.getPageSize());
		circulationListLable.setFieldstr(lable.getFieldstr());
		circulationListLable.setLable(lable.getLable());
		circulationListLable.setSqlCondition(lable.getSqlCondition());
		circulationListLable.setType(lable.getType());
		circulationListLable.setKeyword(lable.getKeyword());
		if (circulationListLable != null) {
			// 判断是否存在有排序语句
			if (!"".equals(circulationListLable.getOrder())) {// 如果排序语句不为“”时
				// 获取排序状态
				if (circulationListLable.getOrder().indexOf("desc") != -1) {
					circulationListLable.setOrderstatus("desc");

				} else if (circulationListLable.getOrder().indexOf("asc") != -1) {
					circulationListLable.setOrderstatus("asc");
				}
				if (circulationListLable.getOrder().indexOf(".") != -1) {
					// 如果能找到“.”说明有排序语句
					// 剔除排序字符，得到纯排序字段
					// 将得到的排序字段分割“，”转化成数组
					String arr[] = LableCommon.lablecommon_delepaixu(
							circulationListLable.getOrder(), ",").split(",");
					// 用该数组得到字段中文名称等信息
					circulationListLable.setField(customLableDao
							.lable_getTableFieldByField(arr));
					// 将最后一个逗号用空格替换掉
					circulationListLable.setOrder(LableCommon
							.lablecommon_getorder(circulationListLable
									.getOrder(), ",", " "));
				}
			}
			if (circulationListLable.getType() == 1) {
				circulationListLable.setSql(LableCommon.lablecommon_getsql(
						circulationListLable.getTablestr(),
						circulationListLable.getFieldstr(),
						circulationListLable.getSqlCondition(),
						circulationListLable.getOrder(), circulationListLable
								.getPageSize()));

			} else if (circulationListLable.getType() == 2) {
				circulationListLable.setSql(LableCommon.lablecommon_pagegetsql(
						circulationListLable.getTablestr(),
						circulationListLable.getFieldstr(),
						circulationListLable.getSqlCondition(),
						circulationListLable.getOrder(), circulationListLable
								.getPageSize()));

			}
		}
		return "lable_lable_cirulationListLableSQLAdd_success";
	}

	/**
	 * 添加html标签,保存sql语句
	 * 
	 * @return
	 * @throws ElException
	 */
	public String lable_cirualtionListLableHTML() throws ElException {

		// 查询完整的sql语句并显示
		lable = circulationListLable;
		lable = customLableDao.lable_getlablesqllable(circulation, lable);
		circulationListLable.setName(lable.getName());
		circulationListLable.setOrder(lable.getOrder());
		circulationListLable.setTablestr(lable.getTablestr());
		circulationListLable.setPageSize(lable.getPageSize());
		circulationListLable.setFieldstr(lable.getFieldstr());
		circulationListLable.setLable(circulationListLable.getLable());
		circulationListLable.setSqlCondition(lable.getSqlCondition());
		circulationListLable.setType(lable.getType());
		if (circulationListLable != null) {
			// 判断是否存在有排序语句
			if (!"".equals(circulationListLable.getOrder())) {// 如果排序语句不为“”时
				// 获取排序状态
				if (circulationListLable.getOrder().indexOf("desc") != -1) {
					circulationListLable.setOrderstatus("desc");

				} else if (circulationListLable.getOrder().indexOf("asc") != -1) {
					circulationListLable.setOrderstatus("asc");
				}
				if (circulationListLable.getOrder().indexOf(".") != -1) {
					// 如果能找到“.”说明有排序语句
					// 剔除排序字符，得到纯排序字段
					// 将得到的排序字段分割“，”转化成数组
					String arr[] = LableCommon.lablecommon_delepaixu(
							circulationListLable.getOrder(), ",").split(",");
					// 用该数组得到字段中文名称等信息
					circulationListLable.setField(customLableDao
							.lable_getTableFieldByField(arr));
					// 将最后一个逗号用空格替换掉
					circulationListLable.setOrder(LableCommon
							.lablecommon_getorder(circulationListLable
									.getOrder(), ",", " "));
				}
			}
			// 得到完整SQL语句
			if (circulationListLable.getType() == 1) {
				circulationListLable.setSql(LableCommon.lablecommon_getsql(
						circulationListLable.getTablestr(),
						circulationListLable.getFieldstr(),
						circulationListLable.getSqlCondition(),
						circulationListLable.getOrder(), circulationListLable
								.getPageSize()));

			} else if (circulationListLable.getType() == 2) {
				circulationListLable.setSql(LableCommon.lablecommon_pagegetsql(
						circulationListLable.getTablestr(),
						circulationListLable.getFieldstr(),
						circulationListLable.getSqlCondition(),
						circulationListLable.getOrder(), circulationListLable
								.getPageSize()));

			}

		}
		customLableDao.lable_updlableHTML(circulation, circulationListLable);
		return "lable_cirualtionListLableHTML_success";

	}

	public String lable_getalllable() throws ElException, UnsupportedEncodingException {
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		
		lableTree = lableTreeDao.getLableTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		if (klTree == null || klTree.getId() <= 0) {
			klTree = lableTree;
		} else
			klTree = lableTreeDao.getLableTreeById(klTree.getId());

		listLable = customLableDao.lable_getalllable(getPageNow(),
				getPageSize(), klTree,searchL);
		count = customLableDao.lable_getalllableSize(klTree,searchL);
		return "lable_getalllable_success";
	}

	/**
	 * 返回搜索标签添加页面
	 * 
	 * @return
	 */
	public String lable_searchLableNameAddInit() {
		// 返回页面
		return "lable_searchLableNameAddInit_success";

	}

	/**
	 * 添加搜索标签名称
	 * 
	 * @return
	 * @throws ElException
	 */
	public String lable_searchLableNameAdd() throws ElException {
		// 验证循环标签名是否重复
		if (LableCommon.check_lableName(searchLable.getName(), searchlable,
				"name")) {
			// 如果重复
			setElmessage("标签名已被占用，请重新输入");
			return lable_searchLableNameAddInit();

		} else {// 如果不重复， 添加该标签并进入到循环列表标签查询表信息页面
			customLableDao.lable_addsearchlable(searchLable.getName(), 2);
			return lable_searchLableinfoAddInit();
		}

	}

	/**
	 * 搜索标签查询表信息页面
	 * 
	 * @return
	 * @throws ElException
	 */
	public String lable_searchLableinfoAddInit() throws ElException {
		lableTree = lableTreeDao.getLableTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		// 1，先查出来常用表中的信息
		systableList = customLableDao.lable_getsystable();
		// 2，查出来自定义表中的信息
		usertableList = customLableDao.lable_getusertable();
		// 3，查询该自定义标签中已使用的表， 及表字段 信息
		lableInTableList = customLableDao.lable_getlabletableandfield(
				searchLable.getName(), searchlable);
		lable = searchLable;
		lable = customLableDao.lable_getlablesqllable(searchlable, lable);
		searchLable.setName(lable.getName());
		searchLable.setOrder(lable.getOrder());
		searchLable.setTablestr(lable.getTablestr());
		searchLable.setPageSize(lable.getPageSize());
		searchLable.setFieldstr(lable.getFieldstr());
		searchLable.setLable(lable.getLable());
		searchLable.setSqlCondition(lable.getSqlCondition());
		searchLable.setType(lable.getType());
		searchLable.setKeyword(lable.getKeyword());
		searchLable.setLableTree(lableTreeDao.getLableTreeById(lable.getLabletreeid()));
		return "lable_searchLableinfoAddInit_success";
	}

	/**
	 * 添加搜索标签 sql 条件，一页条数等信息
	 * 
	 * @return
	 * @throws ElException
	 */
	public String lable_searchLablesearchAdd() throws ElException {
		// 保存标签设置
		searchLable.setSqlCondition(searchLable.getSqlCondition() == null ? ""
				: searchLable.getSqlCondition());
		searchLable.setPageSize(searchLable.getPageSize() == 0 ? 10
				: searchLable.getPageSize());
		searchLable.setKeyword(searchLable.getKeyword()==null?"":searchLable.getKeyword());
		customLableDao.lable_updsearchlablesql(searchlable, searchLable);
		//添加类别id和关键词
		if(klTree==null||klTree.getParent()==null||klTree.getParent().getId()<=0){
			this.setElmessage("请选择上级类别");
			return "error";
		}
		customLableDao.updateLableTreeid(searchLable.getName(),klTree.getParent().getId(),searchlable);
		// 获取以前的标签设置内容
		searchLable = customLableDao.lable_getlablesearchlable(searchlable,
				searchLable.getName());
		return "lable_searchLablesearchAdd_success";
	}

	/**
	 * 保存搜索框设置
	 * 
	 * @return
	 * @throws ElException
	 */
	public String lable_searchLablesearchsetAdd() throws ElException {
		searchLable.setSearchlable(searchLable.getSearchlable() == null ? ""
				: searchLable.getSearchlable());
		// 持久化
		customLableDao.lable_updsearchlablesearchset(searchlable, searchLable);
		// 获取以前的标签设置内容
		searchLable = customLableDao.lable_getlablesearchlable(searchlable,
				searchLable.getName());
		if (searchLable != null) {
			// 判断是否存在有排序语句
			if (!"".equals(searchLable.getOrder())) {// 如果排序语句不为“”时
				// 获取排序状态
				if (searchLable.getOrder().indexOf("desc") != -1) {
					searchLable.setOrderstatus("desc");

				} else if (searchLable.getOrder().indexOf("asc") != -1) {
					searchLable.setOrderstatus("asc");
				}
				if (searchLable.getOrder().indexOf(".") != -1) {
					// 如果能找到“.”说明有排序语句
					// 剔除排序字符，得到纯排序字段
					// 将得到的排序字段分割“，”转化成数组
					String arr[] = LableCommon.lablecommon_delepaixu(
							searchLable.getOrder(), ",").split(",");
					// 用该数组得到字段中文名称等信息
					searchLable.setField(customLableDao
							.lable_getTableFieldByField(arr));
					// 将最后一个逗号用空格替换掉
					searchLable.setOrder(LableCommon.lablecommon_getorder(
							searchLable.getOrder(), ",", " "));
				}
			}

			searchLable.setSql(LableCommon.lablecommon_pagegetsql(searchLable
					.getTablestr(), searchLable.getFieldstr(), searchLable
					.getSqlCondition(), searchLable.getOrder(), searchLable
					.getPageSize()));

		}
		return "lable_searchLablesearchsetAdd_success";
	}

	/**
	 * 添加html标签,保存sql语句
	 * 
	 * @return
	 * @throws ElException
	 */
	public String lable_searchLableHTML() throws ElException {

		// 查询完整的sql语句并显示
		SearchLable searchLable1 = customLableDao.lable_getlablesearchlable(
				searchlable, searchLable.getName());
		if (searchLable != null) {
			// 判断是否存在有排序语句
			if (!"".equals(searchLable1.getOrder())) {// 如果排序语句不为“”时
				// 获取排序状态
				if (searchLable1.getOrder().indexOf("desc") != -1) {
					searchLable1.setOrderstatus("desc");

				} else if (searchLable1.getOrder().indexOf("asc") != -1) {
					searchLable1.setOrderstatus("asc");
				}
				if (searchLable1.getOrder().indexOf(".") != -1) {
					// 如果能找到“.”说明有排序语句
					// 剔除排序字符，得到纯排序字段
					// 将得到的排序字段分割“，”转化成数组
					String arr[] = LableCommon.lablecommon_delepaixu(
							searchLable1.getOrder(), ",").split(",");
					// 用该数组得到字段中文名称等信息
					searchLable1.setField(customLableDao
							.lable_getTableFieldByField(arr));
					// 将最后一个逗号用空格替换掉
					searchLable1.setOrder(LableCommon.lablecommon_getorder(
							searchLable1.getOrder(), ",", " "));
				}
			}
			// 得到完整SQL语句

			searchLable.setSql(LableCommon.lablecommon_pagegetsql(searchLable1
					.getTablestr(), searchLable1.getFieldstr(), searchLable1
					.getSqlCondition(), searchLable1.getOrder(), searchLable1
					.getPageSize()));

		}
		customLableDao.lable_updlableHTML(searchlable, searchLable);
		return "lable_searchLableHTML_success";

	}

}
