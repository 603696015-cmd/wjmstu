package com.sopia.pfms.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.BeanGenerator.BeanSqlSplice;
import com.sopia.common.BeanGenerator.CglibBean;
import com.sopia.common.BeanGenerator.TableCreateBean;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.pfms.dao.BaoxianProductDao;
import com.sopia.pfms.dao.InsuranceCategoriesDao;
import com.sopia.pfms.dao.PolicyDao;
import com.sopia.pfms.dao.SheBeiDao;
import com.sopia.pfms.entities.BaoxianProduct;
import com.sopia.pfms.entities.IC_column_qiuji_qiuhe;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.entities.RelateColumnInformation;
import com.sopia.pfms.entities.InsuranceCategories;
import com.sopia.pfms.entities.Policy;
import com.sopia.pfms.entities.RelateTable;
import com.sopia.pfms.entities.SheBei;
import com.sopia.pfms.entities.TableColumn;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.entities.Eluser;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.Tags;

public class InsuranceCategoriesAction extends BaseAction {
	private InsuranceCategoriesDao ICDao;
	private PolicyDao policyDao;
	private Policy policy;
	private BaoxianProductDao baoxianProductDao;
	private BaoxianProduct baoxianProduct;
	private List<InsuranceCategories> ICList;
	private InsuranceCategories IC;
	private CglibBean IC_U_Bean;
	private TableCreateBean TC_Info;
	private List<TableCreateBean> TC_Infos;
	private BeanSqlSplice bsql;
	private File st;
	private String stFileName;
	private Map view;
	/**
	 * TypeView 说明页面显示类型 1 == 可提交的显示 2 == 可修改的显示 3 == 查看显示 4 == 预览
	 */
	private int TypeView;
	private int IC_U_ID;

	private String fileName;
	private String actionName;

	private String type;
	private List<RelateTable> relateTables;
	private RelateTable relateTable;
	private SheBeiDao sheBeiDao;
	private List<SheBei> shebeiList;
	private String tablename;

	private int userid;
	private Tags tags;
	private List<Tags> list_tags = new ArrayList<Tags>();
	private TagsDao tagsDao;
	List<Map<String, String>> list_designe = new ArrayList<Map<String, String>>();
	private String id;
	private List<RelateColumnInformation> relateColumns;
	private String result;

	private Department depTree;
	private Department department;
	private int sub_department;
	private List<Eluser> list_eluser = new ArrayList<Eluser>();

	private Eluser eluser;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private RoleDao roleDao;
	private List<ElRole> roles;
	private PfmsUser pfmsUser;

	private int is_qiuhe;// 是否求和
	private int is_qiuji;// 是否求积
	private List<IC_column_qiuji_qiuhe> columns_qiuji_qiuhe;
	private String qiujiRelateColumns;
	private String column_name;

	private List<ModuleManage> moduleTables;

	private List<TableColumn> tableColumns;

	private String change_value;// ajax做更改字段页面显示

	public String getChange_value() {
		return change_value;
	}

	public void setChange_value(String change_value) {
		this.change_value = change_value;
	}

	public List<TableColumn> getTableColumns() {
		return tableColumns;
	}

	public void setTableColumns(List<TableColumn> tableColumns) {
		this.tableColumns = tableColumns;
	}

	public List<ModuleManage> getModuleTables() {
		return moduleTables;
	}

	public void setModuleTables(List<ModuleManage> moduleTables) {
		this.moduleTables = moduleTables;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getQiujiRelateColumns() {
		return qiujiRelateColumns;
	}

	public void setQiujiRelateColumns(String qiujiRelateColumns) {
		this.qiujiRelateColumns = qiujiRelateColumns;
	}

	public RelateTable getRelateTable() {
		return relateTable;
	}

	public void setRelateTable(RelateTable relateTable) {
		this.relateTable = relateTable;
	}

	public List<IC_column_qiuji_qiuhe> getColumns_qiuji_qiuhe() {
		return columns_qiuji_qiuhe;
	}

	public void setColumns_qiuji_qiuhe(
			List<IC_column_qiuji_qiuhe> columns_qiuji_qiuhe) {
		this.columns_qiuji_qiuhe = columns_qiuji_qiuhe;
	}

	public int getIs_qiuhe() {
		return is_qiuhe;
	}

	public void setIs_qiuhe(int is_qiuhe) {
		this.is_qiuhe = is_qiuhe;
	}

	public int getIs_qiuji() {
		return is_qiuji;
	}

	public void setIs_qiuji(int is_qiuji) {
		this.is_qiuji = is_qiuji;
	}

	public PfmsUser getPfmsUser() {
		return pfmsUser;
	}

	public void setPfmsUser(PfmsUser pfmsUser) {
		this.pfmsUser = pfmsUser;
	}

	public List<Eluser> getList_eluser() {
		return list_eluser;
	}

	public void setList_eluser(List<Eluser> list_eluser) {
		this.list_eluser = list_eluser;
	}

	public Eluser getEluser() {
		return eluser;
	}

	public void setEluser(Eluser eluser) {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<RelateColumnInformation> getRelateColumns() {
		return relateColumns;
	}

	public void setRelateColumns(List<RelateColumnInformation> relateColumns) {
		this.relateColumns = relateColumns;
	}

	public TagsDao getTagsDao() {
		return tagsDao;
	}

	public void setTagsDao(TagsDao tagsDao) {
		this.tagsDao = tagsDao;
	}

	public List<Map<String, String>> getList_designe() {
		return list_designe;
	}

	public void setList_designe(List<Map<String, String>> list_designe) {
		this.list_designe = list_designe;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Tags getTags() {
		return tags;
	}

	public void setTags(Tags tags) {
		this.tags = tags;
	}

	public List<Tags> getList_tags() {
		return list_tags;
	}

	public void setList_tags(List<Tags> list_tags) {
		this.list_tags = list_tags;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<SheBei> getShebeiList() {
		return shebeiList;
	}

	public void setShebeiList(List<SheBei> shebeiList) {
		this.shebeiList = shebeiList;
	}

	public SheBeiDao getSheBeiDao() {
		return sheBeiDao;
	}

	public void setSheBeiDao(SheBeiDao sheBeiDao) {
		this.sheBeiDao = sheBeiDao;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<RelateTable> getRelateTables() {
		return relateTables;
	}

	public void setRelateTables(List<RelateTable> relateTables) {
		this.relateTables = relateTables;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getTypeView() {
		return TypeView;
	}

	public void setTypeView(int typeView) {
		TypeView = typeView;
	}

	public File getSt() {
		return st;
	}

	public void setSt(File st) {
		this.st = st;
	}

	public String getStFileName() {
		return stFileName;
	}

	public void setStFileName(String stFileName) {
		this.stFileName = stFileName;
	}

	public BeanSqlSplice getBsql() {
		return bsql;
	}

	public void setBsql(BeanSqlSplice bsql) {
		this.bsql = bsql;
	}

	public List<TableCreateBean> getTC_Infos() {
		return TC_Infos;
	}

	public void setTC_Infos(List<TableCreateBean> infos) {
		TC_Infos = infos;
	}

	public TableCreateBean getTC_Info() {
		return TC_Info;
	}

	public void setTC_Info(TableCreateBean info) {
		TC_Info = info;
	}

	public CglibBean getIC_U_Bean() {
		return IC_U_Bean;
	}

	public void setIC_U_Bean(CglibBean bean) {
		IC_U_Bean = bean;
	}

	public String IC_ListInit() throws Exception {
		IC = IC == null ? new InsuranceCategories() : IC;
		ICList = ICDao.getICList(IC, getPageNow(), getPageSize());
		count = ICDao.getICListSize(IC);
		return "IC_List";
	}

	public String IC_addInit() throws Exception {
		return "IC_add";
	}

	public String IC_add() throws Exception {
		if (IC.getName() == null || IC.getName().equals("")
				|| IC.getTableName() == null || IC.getTableName().equals("")) {
			setElmessage("险种名称和险种表名是不能为空");
			return "IC_add";
		}
		// 判断表名长度不能过长
		if (IC != null && IC.getTableName() != null
				&& !IC.getTableName().equals("")
				&& IC.getTableName().length() > 15) {
			this.setElmessage("您添加的数据库表名过长，请重新添加!!!");
			return "error";
		}
		if (ICDao.CheckIC(IC)) { // 验证表名是否存在
			setElmessage("险种表名已存在！");
			return "IC_add";
		}

		IC
				.setFounder(new ELUser(
						getSessionIntValue(ElConstants.SESSION_USERID)));// 创建人
		IC.setId(ICDao.addIC(IC));// 险种表增加险种数据返回id
		BeanSqlSplice bsql = new BeanSqlSplice(IC.getId(), IC.getTableName_());
		ICDao.createIC_Date(bsql);// 创建险种详情表
		return "IC_add_success";
	}

	public String IC_Delete() throws Exception {
		IC = ICDao.getByICId(IC.getId());
		ICDao.IC_U_Delete(IC);// 删除险种
		return "IC_ListInit";
	}

	public String IC_U_columns_manageInit() throws Exception {
		if (IC == null || IC.getId() <= 0) {
			setElmessage("非法路口");
			return "error";
		}
		IC = ICDao.getByICId(IC.getId());
		TC_Info = new TableCreateBean(IC.getTableName());
		TC_Infos = TC_Info.getTSBs_();
		bsql = new BeanSqlSplice();
		return "IC_U_columns_manage";
	}

	public String IC_U_InfoInit() throws Exception {
		if (IC == null || IC.getId() <= 0) {
			setElmessage("非法路口");
			return "error";
		}
		TypeView = 1;// 1 == 可提交的显示
		IC = ICDao.getByICId(IC.getId());
		TC_Info = new TableCreateBean(IC.getTableName());
		TC_Infos = TC_Info.getTSBs_();
		view = TC_Info.getTSBs_viewNAME();

		columns_qiuji_qiuhe = ICDao.getQiujiColumns(IC.getTableName());
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		qiujiRelateColumns = gson.toJson(columns_qiuji_qiuhe);

		// 会员价
		baoxianProduct = baoxianProductDao.getBaoxianProductByid(baoxianProduct
				.getId());

		return "IC_U_Info";
	}

	public String IC_U_Info_AlertInit() throws Exception {
		if (policy == null || policy.getId() <= 0) {
			setElmessage("非法路口");
			return "error";
		}
		TypeView = 2;// 2 == 可修改的显示
		policy = policyDao.getPolicyById(policy.getId());
		TC_Info = new TableCreateBean(policy.getIC_TABLENAME());
		TC_Info.setTableName_ID(policy.getIC_U_ID());

		TC_Infos = TC_Info.getTSBs_value();
		view = TC_Info.getTSBs_viewNAME();

		IC = ICDao.getByICTableName(policy.getIC_TABLENAME());
		baoxianProduct = baoxianProductDao.getBaoxianProductByid(policy
				.getCommodityId());
		return "IC_U_Info_Alert";
	}

	public String IC_U_Info_ViewInit() throws Exception {
		if (policy == null || policy.getId() <= 0) {
			setElmessage("非法路口");
			return "error";
		}
		TypeView = 3;// 3 == 查看显示
		policy = policyDao.getPolicyById(policy.getId());
		IC.setTableName(policy.getIC_TABLENAME());
		TC_Info = new TableCreateBean(policy.getIC_TABLENAME());
		TC_Info.setTableName_ID(policy.getIC_U_ID());

		TC_Infos = TC_Info.getTSBs_value();
		view = TC_Info.getTSBs_viewNAME();
		return "IC_U_Info_View";
	}

	public String IC_U_Info_View_Init() throws Exception {
		if (IC == null || IC.getId() <= 0) {
			setElmessage("非法路口");
			return "error";
		}
		TypeView = 4;// 4 == 预览
		IC = ICDao.getByICId(IC.getId());
		// 验证是否已上传jsp文件
		File f = new File(J2EEFileUtil.getRealPath("/")
				+ "pfms\\InsuranceCategories\\IC_U_demo\\" + IC.getTableName()
				+ ".jsp");
		if (!f.exists()) {
			this.setElmessage("对不起，您还未上传相应的模板，请先上传模板!!!");
			return "error";
		}
		TC_Info = new TableCreateBean(IC.getTableName());

		TC_Infos = TC_Info.getTSBs_();
		view = TC_Info.getTSBs_viewNAME();
		return "IC_U_Info_View_";
	}

	public String IC_U_AddColumn() throws Exception {
		if (IC == null || IC.getId() <= 0) {
			setElmessage("非法路口");
			return "error";
		}
		IC = ICDao.getByICId(IC.getId());
		TC_Info = new TableCreateBean(IC.getTableName());
		TC_Infos = TC_Info.getTSBs_();
		if (bsql.getColumn_name_().equals("KS_null")) {
			setElmessage("列名不能为空");
			return "IC_U_columns_manage";
		}
		if (ICDao.CheckIC_U_Column(IC.getTableName(), bsql.getColumn_name_())) { // 验证列名是否存在
			setElmessage("险种详情表列名已存在！");
			return "IC_U_columns_manage";
		}
		if (bsql.getParametersType().equals("BLOB")
				&& ICDao.CheckIC_U_Column_Blob(IC.getTableName())) { // 验证该表是否存在BLOB
			setElmessage("一个险种只能存在一个BLOB类型数据，不能再添加BLOB类型！");
			return "IC_U_columns_manage";
		}

		bsql.setTableName(IC.getTableName());
		ICDao.addIC_U_Column(bsql);
		TC_Info = new TableCreateBean(IC.getTableName());// 新插入数据 重新查一遍
		TC_Infos = TC_Info.getTSBs_();
		return "IC_U_columns_manage";
	}

	public String IC_U_UpdateDemo() throws ElException, Exception {
		if (st == null) {
			return "IC_U_UpdateDemo";
		}
		if (st.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "IC_U_UpdateDemo";
		} else {
			String ext = J2EEFileUtil.getExtention(stFileName);
			if (!ext.equals("jsp")) {
				setElmessage("模板文件格式只能为jsp！");
				return "IC_U_UpdateDemo";
			}
			// 校验里面标签中的字段有没有重复的
			try {
				String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 )
				Pattern pattern = Pattern.compile("iname=\"KS_[A-Z]+\"");  
				int size = 0;
//				Map<String,String> map = new HashMap<String,String>();
				List<String> list = new ArrayList<String>();
				if (st.isFile() && st.exists()) {
					InputStreamReader read = new InputStreamReader(
							new FileInputStream(st), encoding);
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTXT = null;
					while ((lineTXT = bufferedReader.readLine()) != null) {
						if(lineTXT.toString().trim().indexOf("iname") != -1){
							Matcher matcher = pattern.matcher(lineTXT.toString().trim());
							while(matcher.find()){
								list.add(matcher.group().split("\"")[1]);
//								map.put(matcher.group().split("\"")[1], matcher.group().split("\"")[1]);
								size++;
							}
						}
					}
					read.close();
					String value = "";
					int count = 0;
					for(int i=0;i<list.size();i++){
						value = list.get(i);
						for(int j=0;j<list.size();j++){
							if(value.equals(list.get(j))){
								count++;
							}
						}
						if(count>=2){
							this.setElmessage("对不起，您上传的jsp文件有重复的标签，请修改后再上传!!!<br/>" +
									"请查看" + value + "是否有多个!!!");
							return "error";
						}
						count = 0;
					}
//					if(size != map.keySet().size()){
//						this.setElmessage("对不起，您上传的jsp文件有重复的标签，请修改后再上传!!!");
//						return "error";
//					}
				} else {
					this.setElmessage("文件不存在!!!");
					return "error";
				}
			} catch (Exception e) {
				System.out.println("读取文件内容操作出错");
				e.printStackTrace();

			}
			IC = ICDao.getByICId(IC.getId());
			ICDao.updateDemoORCss(IC.getId(), IC.getTableName(), "url");

			J2EEFileUtil.upload_xianzhong(st, ext,
					"pfms\\InsuranceCategories\\IC_U_demo", IC.getTableName()
							+ "");
		}
		return "IC_U_UpdateDemo";
	}

	public String IC_U_UpdateDemo_CSS() throws ElException, Exception {
		if (st == null) {
			return "IC_U_UpdateDemo_CSS";
		}
		if (st.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "IC_U_UpdateDemo_CSS";
		} else {
			String ext = J2EEFileUtil.getExtention(stFileName);
			if (!ext.equals("css")) {
				setElmessage("模板样式文件格式只能为css！");
				return "IC_U_UpdateDemo_CSS";
			}
			IC = ICDao.getByICId(IC.getId());
			ICDao.updateDemoORCss(IC.getId(), IC.getTableName(), "css");
			J2EEFileUtil.upload(st, ext,
					"pfms\\InsuranceCategories\\IC_U_demo", IC.getTableName()
							+ "");
		}
		return "IC_U_UpdateDemo_CSS";
	}

	public String IC_U_Demo_downloadInit() throws Exception {
		try {
			getInputStream();
		} catch (Exception e) {
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		return "IC_U_Demo_download";
	}

	public InputStream getInputStream() throws ElException {
		InputStream is = null;
		String path = ServletActionContext.getServletContext().getRealPath(
				"pfms\\InsuranceCategories\\IC_U_demo\\" + fileName);
		try {
			System.out.println(path);
			is = new FileInputStream(path);
		} catch (Exception e) {
			throw new ElException("下载资料出错", e);
		}
		return is;
	}

	public String IC_U_addOrAlter() throws Exception {
		if (IC == null || IC.getId() <= 0) {
			setElmessage("非法路口");
			return "error";
		}
		baoxianProduct = baoxianProductDao.getBaoxianProductByid(baoxianProduct
				.getId());
		IC = ICDao.getByICId(IC.getId());
		TC_Info = new TableCreateBean(IC.getTableName());
		HttpServletRequest request = ServletActionContext.getRequest();
		Enumeration params = request.getParameterNames();

		String sql = "";
		String sqlName = "";
		String sqlValue = "";
		String sqlBlobInfo = "";
		int ic_u_id = 0;
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			// String paramvalue = request.getParameter(paramName);
			String[] paramvalue_array = request.getParameterValues(paramName);
			String paramvalue = "";
			for (int i = 0; i < paramvalue_array.length; i++) {
				if (i == paramvalue_array.length - 1)
					paramvalue += paramvalue_array[i];
				else
					paramvalue += paramvalue_array[i] + ",";
			}
			if (paramName.length() > IC.getTableName().length()) {
				String objName = paramName.substring(0, IC.getTableName()
						.length());
				String[] sqlName_ = (paramName.substring(IC.getTableName()
						.length() + 1, paramName.length())).split("-");
				if (IC_U_ID != 0) {// 不为0 是修改
					if (objName.equals(IC.getTableName())) {
						if (sqlName_[1] != null && sqlName_[1].equals("riqi")) {
							sqlName = sqlName + sqlName_[0] + " = to_date('"
									+ paramvalue
									+ "','yyyy-mm-dd hh24:mi:ss'),";
						} else if (sqlName_[1] != null
								&& sqlName_[1].equals("BLOB")) {
							sqlName = sqlName + sqlName_[0]
									+ " = empty_blob(),";
							sqlBlobInfo = sqlName_[0] + "-blob-" + paramvalue;
						} else {
							sqlName = sqlName + sqlName_[0] + " = '"
									+ paramvalue + "',";
						}
					}
				} else {// 为0 是新增
					if (objName.equals(IC.getTableName())) {
						sqlName = sqlName + sqlName_[0] + ",";
						if (sqlName_[1] != null && sqlName_[1].equals("riqi")) {
							sqlValue = sqlValue + "to_date('" + paramvalue
									+ "','yyyy-mm-dd hh24:mi:ss'),";
						} else if (sqlName_[1] != null
								&& sqlName_[1].equals("BLOB")) {
							sqlValue = sqlValue + "empty_blob(),";
							sqlBlobInfo = sqlName_[0] + "-blob-" + paramvalue;
						} else {
							sqlValue = sqlValue + "'" + paramvalue + "',";
						}
					}
				}
			}
		}
		if (IC_U_ID != 0) {// 不为0 是修改
			sql = "update " + IC.getTableName() + " set "
					+ sqlName.substring(0, sqlName.length() - 1)
					+ " where id = " + IC_U_ID;
			ICDao.addOrUpdateIC_U_Date(sql, IC.getTableName(), IC_U_ID + "",
					sqlBlobInfo);
			ic_u_id = IC_U_ID;
			policyDao.alterPolicy(policy);
		} else {// 为0 是新增
			sql = "insert into " + IC.getTableName() + "("
					+ sqlName.substring(0, sqlName.length() - 1) + ") values ("
					+ sqlValue.substring(0, sqlValue.length() - 1) + ")";
			ic_u_id = ICDao.addOrUpdateIC_U_Date(sql, IC.getTableName(), "add",
					sqlBlobInfo); // 添加险种保单信息 返回id
			// 构建保单信息
			policy = new Policy();
			policy.setLibId(baoxianProduct.getLanmu().getId());
			policy.setCommodityId(baoxianProduct.getId());
			policy.setIC_TABLENAME(IC.getTableName());
			policy.setIC_U_ID(ic_u_id);
			policy.setCreateId(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			policyDao.addPolicy(policy);
		}

		return "IC_U_addOrAlter_success";
	}

	@SuppressWarnings("unchecked")
	public String searchRelateTableInit() throws ElException {
		String resultPage = "";
		if (type != null) {
			if (type.equals("biaodi")) {
				relateTables = (List<RelateTable>) ICDao.searchRelateTables(
						null, type, getPageNow(), getPageSize());
				count = ICDao.searchRelateTablesSize(type);
			} else {
				relateTables = ICDao.searchRelateTables(null, type, 0, 0);
			}
			resultPage = "searchRelateTableInit";
		} else {// 模块间计算
			moduleTables = (List<ModuleManage>) ICDao.searchRelateTables(
					tablename, null, getPageNow(), getPageSize());
			count = ICDao.searchRelateTablesSize(null);
			resultPage = "searchRelateTable_moduleManage_init";
		}
		return resultPage;
	}

	public String relateColumnsInit() throws ElException {
		List<String> tableNames = new ArrayList<String>();
		if (IC != null) {
			if (IC.getRead_auto_toubaoren() != null
					&& !IC.getRead_auto_toubaoren().equals("")) {
				tableNames.add(IC.getRead_auto_toubaoren());
			}
			if (IC.getRead_auto_beibaoren() != null
					&& !IC.getRead_auto_beibaoren().equals("")) {
				tableNames.add(IC.getRead_auto_beibaoren());
			}
			if (IC.getRead_auto_biaodi() != null
					&& !IC.getRead_auto_biaodi().equals("")) {
				tableNames.add(IC.getRead_auto_biaodi());
			}
		}

		relateTables = new ArrayList<RelateTable>();
		for (String tableName : tableNames) {
			relateTables.add(ICDao.getRelateTableByTableName(tableName));
		}

		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree_level1(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// else
		// {
		// depTree = departmentDao.getDepTree_level1(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }
		// if (department == null || department.getId() <= 0)
		// {
		// sub_department = 1;
		// department = depTree;
		// } else
		// department = departmentDao.getDepById(department.getId());

		return "relateColumnsInit";
	}

	public String relateColumnsInit2() throws ElException {
		relateTable = ICDao.getRelateTableByTableName(tablename);
		return "relateColumnsInit2";
	}

	public String searchShebei() throws ElException,
			UnsupportedEncodingException {
		IC = ICDao.getByICTableName(tablename);
		if (IC == null) {
			IC = new InsuranceCategories();
		}

		Map<String, String> hm = new HashMap<String, String>();
		list_tags = tagsDao.select_designe_field_by_tablename(IC
				.getRead_auto_biaodi());

		for (int i = 0; i < list_tags.size(); i++) {
			// 相关字段
			if (list_tags.get(i).getDisplay_type().equals("相关字段")) {
				String str_relate = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name());
				if (str_relate != null && !str_relate.equals("")) {
					String arr[] = list_tags.get(i).getDefault_value().split(
							"==");// 如tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
					// relate_type==目标表名==目标列名==列名 条件
					hm.put("relate_type" + "==" + arr[0] + "==" + arr[1] + "=="
							+ list_tags.get(i).getColumn_name(), str_relate);
					list_tags.get(i).setValue2(str_relate);// 将搜索条件传回前端
				}
				continue;
			}// 相关负责人
			else if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				String str_relate = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name());
				String str_rx = (String) getRequest().getParameter("rx");
				if (str_relate != null && str_rx != null)
					str_relate = new String(getRequest().getParameter(
							list_tags.get(i).getColumn_name()).getBytes(
							"ISO8859-1"), "utf-8");
				if (str_relate != null && !str_relate.equals("")) {
					// String
					// arr[]=list_tags.get(i).getDefault_value().split("==");//如tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
					// relate_type==目标表名==目标列名==列名 条件
					hm.put("relate_type" + "==eluser==realname=="
							+ list_tags.get(i).getColumn_name(), str_relate);
					list_tags.get(i).setValue2(str_relate);// 将搜索条件传回前端
				}
				continue;
			}
			String str = (String) getRequest().getParameter(
					list_tags.get(i).getColumn_name());
			if (str != null && !str.equals("")) {
				hm.put(list_tags.get(i).getColumn_type() + "=="
						+ list_tags.get(i).getColumn_name(), str);
				list_tags.get(i).setValue(str);// 将搜索条件传回前端
			}
			// 日期
			if (list_tags.get(i).getColumn_type().equals("date")) {
				String str2 = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name() + "_");
				if (str2 != null && !str2.equals("")) {
					hm.put(list_tags.get(i).getColumn_type() + "=="
							+ list_tags.get(i).getColumn_name() + "_", str2);
					list_tags.get(i).setValue2(str2);
				}
			}
			// 数字
			if (list_tags.get(i).getColumn_type().equals("number")
					|| list_tags.get(i).getColumn_type().equals("float")) {
				String str2 = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name() + "_");
				if (str2 != null && !str2.equals("")) {
					hm.put(list_tags.get(i).getColumn_type() + "=="
							+ list_tags.get(i).getColumn_name() + "_", str2);
					list_tags.get(i).setValue2(str2);
				}
			}
		}

		String order = "";
		if (tags != null) {
			if (tags.getOrdercolumn() != null
					&& !tags.getOrdercolumn().equals("")) {
				order = " order by ";
				order += tags.getOrdercolumn() + " ";
				order += tags.getOrdersc();
			}
		}

		userid = getSessionIntValue(ElConstants.SESSION_USERID);
		list_designe = tagsDao.select_my_tableinfo_by_userid_order("", 1,
				list_tags, IC.getRead_auto_biaodi(), hm, userid, order,
				getPageNow(), getPageSize());
		count = tagsDao.select_my_tableinfo_by_userid_count(1, list_tags, hm,
				IC.getRead_auto_biaodi(), userid);
		// shebeiList =
		// sheBeiDao.searchShebei(getSessionIntValue(ElConstants.SESSION_USERID),tablename,getPageNow(),
		// getPageSize());
		// count =
		// sheBeiDao.searchShebeiSize(getSessionIntValue(ElConstants.SESSION_USERID),tablename);
		return "searchShebei";
	}

	public String getInformationByAuto() throws ElException {
		// 将绑定表的数据id拆分
		String[] tables = null;
		int[] infor_ids = null;
		if (id != null && !id.equals("")) {
			String[] array = id.split(";");
			tables = new String[array.length];
			infor_ids = new int[array.length];
			for (int i = 0; i < array.length; i++) {
				tables[i] = array[i].split(":")[0];
				infor_ids[i] = Integer.parseInt(array[i].split(":")[1]);
			}

		}

		result = "";
		// 通过关联表获取字段对应关系
		relateColumns = ICDao.getrelateColumns(tablename);

		for (int x = 0; x < relateColumns.size(); x++) {
			for (int y = 0; y < tables.length; y++) {
				if (tables[y].equals(relateColumns.get(x).getRelateTableName())) {
					relateColumns.get(x).setId(infor_ids[y]);
				}
			}
		}

		// 通过找到的关联字段找出对应的值
		for (RelateColumnInformation relateColumn : relateColumns) {
			relateColumn.setRelateColumnValue(ICDao
					.getrelateColumnValueByRelateColumnName(relateColumn
							.getId(), relateColumn.getRelateTableName(),
							relateColumn.getRelateColumnName()));
		}

		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();

		result = gson.toJson(relateColumns);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"result\":" + result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 填写保单页面自动获取数据
	 * 
	 * @return
	 * @throws ElException
	 */
	public String searchRelateId() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		int nid = department == null ? depTree.getId()
				: (department.getId() == 0 ? 1 : department.getId());
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());

		// if(tablename.toUpperCase().equals("ELUSER")){
		//			
		// }else if(tablename.toUpperCase().equals("PFMSUSER")){
		//			
		// }

		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);

		list_eluser = tagsDao.select_eluser_by_dep(0, tablename, eluser,
				department, getPageNow(), getPageSize(), nid);
		count = tagsDao.select_eluser_by_dep_count(0, tablename, eluser,
				department, getPageNow(), getPageSize(), nid);
		return "searchRelateId";
	}

	public String selectQiujiRelateColumn() throws ElException {
		relateTable = ICDao.getRelateTableByTableName(tablename);
		return "selectQiujiRelateColumn";
	}

	public String checkColumnNameIsQiuji() throws ElException {
		result = ICDao.checkColumnNameIsQiuji(column_name, tablename);
		result = String.valueOf(result);
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"result\":" + result + "}";
			System.out.println(d);
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String changeValueByCname() throws ElException {
		ICDao.changeValueByCname(column_name, tablename, change_value);
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String updateColumnInit() throws Exception {
		TC_Info = ICDao.getTSB(tablename, column_name);
		bsql = new BeanSqlSplice();
		return "updateColumnInit";
	}

	public String updateColumn() throws Exception {
		if (IC == null || IC.getId() <= 0) {
			setElmessage("非法路口");
			return "error";
		}
		IC = ICDao.getByICId(IC.getId());
		if (column_name.equals("KS_null")) {
			setElmessage("列名不能为空");
			return "updateColumnInit";
		}

		// if(!array[0].equals(array[1])){
		// if(ICDao.CheckIC_U_Column(IC.getTableName(), array[0])){ //验证列名是否存在
		// setElmessage("险种详情表列名已存在！");
		// column_name = array[1];
		// return "updateColumnInit";
		// }
		// }else {
		// column_name = array[0];
		// ICDao.updateColumn(IC.getTableName(),column_name,change_value);
		// }
		ICDao.updateColumn(IC.getTableName(), column_name, change_value);
		return "updateColumn_success";
	}

	public InsuranceCategoriesDao getICDao() {
		return ICDao;
	}

	public void setICDao(InsuranceCategoriesDao dao) {
		ICDao = dao;
	}

	public List<InsuranceCategories> getICList() {
		return ICList;
	}

	public void setICList(List<InsuranceCategories> list) {
		ICList = list;
	}

	public InsuranceCategories getIC() {
		return IC;
	}

	public void setIC(InsuranceCategories ic) {
		IC = ic;
	}

	public Map getView() {
		return view;
	}

	public void setView(Map view) {
		this.view = view;
	}

	public PolicyDao getPolicyDao() {
		return policyDao;
	}

	public void setPolicyDao(PolicyDao policyDao) {
		this.policyDao = policyDao;
	}

	public BaoxianProductDao getBaoxianProductDao() {
		return baoxianProductDao;
	}

	public void setBaoxianProductDao(BaoxianProductDao baoxianProductDao) {
		this.baoxianProductDao = baoxianProductDao;
	}

	public BaoxianProduct getBaoxianProduct() {
		return baoxianProduct;
	}

	public void setBaoxianProduct(BaoxianProduct baoxianProduct) {
		this.baoxianProduct = baoxianProduct;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public int getIC_U_ID() {
		return IC_U_ID;
	}

	public void setIC_U_ID(int ic_u_id) {
		IC_U_ID = ic_u_id;
	}

}
