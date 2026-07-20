package com.sopia.schedule.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.aqy.entities.TrainingStatus;
import com.sopia.common.AuthorityUtil;
import com.sopia.common.ElException;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.RemindUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.lable.common.LableCommon;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.dao.InsuranceCategoriesDao;
import com.sopia.pfms.entities.IC_column_qiuji_qiuhe;
import com.sopia.schedule.TagsUtil;
import com.sopia.schedule.ZDYTemplateUtil;
import com.sopia.schedule.dao.ClientDao;
import com.sopia.schedule.dao.ClientTemplateDao;
import com.sopia.schedule.dao.ModuleManageDao;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.dao.impl.ModuleManageDaoImpl;
import com.sopia.schedule.entities.AuditMark;
import com.sopia.schedule.entities.Client;
import com.sopia.schedule.entities.Clientlinkcontact;
import com.sopia.schedule.entities.Clientlinkman;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.Contactstuff;
import com.sopia.schedule.entities.CurrentUser;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.Eluser;
import com.sopia.schedule.entities.MapToJson;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.ModuleStatus;
import com.sopia.schedule.entities.ModuleZDY;
import com.sopia.schedule.entities.Tags;
import com.sopia.schedule.entities.TagsMark;
import com.sopia.schedule.entities.Tb_calculate;
import com.sopia.schedule.entities.UserSign;

public class ClientAction extends BaseAction {
	private Client client;
	private ClientDao clientDao;
	private InsuranceCategoriesDao ICDao;
	private ClientTemplateDao clientTemplateDao;
	private ModuleZDY moduleZDY;
	private TagsDao tagsDao;
	private HttpRequestDeviceUtils httpRequestDeviceUtils;//用于判断是否手机端登陆
	private String tablename;
	private String columnName;

	private Tags tags;
	private List<Tags> list_tags = new ArrayList<Tags>();
	private Map<String,List<Tags>> list_tags_relate = new HashMap<String,List<Tags>>();
	private int id;
	
	private List<Client> list_client;
	private List<Clientlinkman> list_clientlinkman = new ArrayList<Clientlinkman>();

	private List<Contact> list_contact;
	private List<Contactstuff> list_contactstuff = new ArrayList<Contactstuff>();

	List<Map<String, String>> list_designe = new ArrayList<Map<String, String>>();
	Map<String,List<Map<String, String>>> list_designe_relate = new HashMap<String,List<Map<String, String>>>();

	private String linkmanids;

	private Contact contact;
	private Contactstuff contactstuff;

	private InputStream inputStream;
	private InputStream inputStream1;
	private String filename;

	private Department depTree;
	private Department department;
	private int sub_department;

	private List<Clientlinkcontact> list_num_sum = new ArrayList<Clientlinkcontact>();

	private String checkRichtext;

	private ModuleManageDao moduleManageDao;

	private ModuleManage moduleManage;

	private List<Eluser> list_eluser = new ArrayList<Eluser>();

	List<ModuleManage> list_module = new ArrayList<ModuleManage>();
	private TagsMark tagsMark;
	private Eluser eluser;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private RoleDao roleDao;
	private List<ElRole> roles;

	private File path;

	private int rx;
	private int userid;
	private String realname;

	private String display_type;
	private String check_json_result;

	private String relate_tablename;
	private String relate_columnname;
	private String relate_tablename_calculate;// 用于计算的表名
	private String relate_columnname_calculate;// 用于计算的列名

	private List<IC_column_qiuji_qiuhe> columns_qiuji_qiuhe;
	private String qiujiRelateColumns;
	private String type;// 用于模块间计算==type='calculate'的时候
	private List<Tb_calculate> tb_calculates;
	private String tb_calculates_string;// 用于返回json数据的字符串
	private String ids;// 用来计算的两个id

	private String time_columns = "";
	private String yewu_columns = "";
	private String relateIds;
	private String ex_columnname;
	private String time1;// 计算业务进度的时候传入的开始时间和结束时间
	private String time2;

	private String username;// 相关负责人字段的姓名，只有在终审时才可以修改
	private String uid;// 相关负责人的id
	private int final_;// 终审页面

	private String update_user_ids;// 负责人的ids
	private String update_ids;// 批量修改负责人字段的ids
	private String updateType;// 部分更新还是全部更新

	private String parameters;
	private String parameters_;

	private File st;
	private String stFileName;
	private String fileName;
	private int viewType;
	private Map<String, String> view;

	private Map<String, String> map_;
	private double f;

	private String param;

	private int table_type;

	private String kk;// 当kk不为空的时候，即当前业务表参加表间计算

	private List<String> moduleids;// 模块列表
	private List<String> danjustatus;// 单据状态列表
	private List<ModuleStatus> moduleStatusList;
	private ModuleStatus moduleStatus;

	private int can_select_show_columns;// 如果是表间计算，那么可以选择显示哪些字段
	private String yewu_tablename;

	private String actionName;
	private String fromActionName;

	private String is_judge;

	private String fromtablename;

	private String radio;// 显示radio
	private String message;

	private String relate;// 添加相关字段

	private String areaList;// 省市县的json字符串
	private IndexDao indexDao;
	private String table_columns_is_chengshi;// 表中的城市字段
	
	private int danjuid;
	
	private CurrentUser currentUser;
	private UserSign userSign;//签名信息
	
	private int trainType;//培训状况；类型
	private int nocertificateno;//无证人数
	private int haspaymoney;//已缴费人数
	private int hasregister;//已注册人数
	private int hascertificateno;//有证人数
	private int trainCount;//总人数
	private TrainingStatus trainStatus;
	private List<TrainingStatus> nocertificatenoList;//无证人数
	private List<TrainingStatus> haspaymoneyList;//已缴费人数
	private List<TrainingStatus> hasregisterList;//已注册人数
	private List<TrainingStatus> hascertificatenoList;//有证人数
	private List<TrainingStatus> trainCountList;//总人数
	protected int count;
	
	private int isSixMonths;//六个月到期
	private int isThreeMonths;//三个月到期
	private int isOneMonths;//一个月到期
	private int isHalfMonths;//半个月到期
	private int isOneWeek;//一周
	private int isValid;//无效
	private List<TrainingStatus> isSixMonthsList;
	private List<TrainingStatus> isThreeMonthsList;
	private List<TrainingStatus> isOneMonthsList;
	private List<TrainingStatus> isHalfMonthsList;
	private List<TrainingStatus> isOneWeekList;
	private List<TrainingStatus> isValidList;
	
	private int fromcopy;
	
	

	public int getFromcopy() {
		return fromcopy;
	}

	public void setFromcopy(int fromcopy) {
		this.fromcopy = fromcopy;
	}

	public int getDanjuid() {
		return danjuid;
	}

	public void setDanjuid(int danjuid) {
		this.danjuid = danjuid;
	}

	public String getTable_columns_is_chengshi() {
		return table_columns_is_chengshi;
	}

	public void setTable_columns_is_chengshi(String table_columns_is_chengshi) {
		this.table_columns_is_chengshi = table_columns_is_chengshi;
	}

	public IndexDao getIndexDao() {
		return indexDao;
	}

	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}

	public String getAreaList() {
		return areaList;
	}

	public void setAreaList(String areaList) {
		this.areaList = areaList;
	}

	public String getRelate() {
		return relate;
	}

	public void setRelate(String relate) {
		this.relate = relate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getFromtablename() {
		return fromtablename;
	}

	public void setFromtablename(String fromtablename) {
		this.fromtablename = fromtablename;
	}

	public String getIs_judge() {
		return is_judge;
	}

	public void setIs_judge(String is_judge) {
		this.is_judge = is_judge;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getYewu_tablename() {
		return yewu_tablename;
	}

	public void setYewu_tablename(String yewu_tablename) {
		this.yewu_tablename = yewu_tablename;
	}

	public int getCan_select_show_columns() {
		return can_select_show_columns;
	}

	public void setCan_select_show_columns(int can_select_show_columns) {
		this.can_select_show_columns = can_select_show_columns;
	}

	public List<ModuleStatus> getModuleStatusList() {
		return moduleStatusList;
	}

	public void setModuleStatusList(List<ModuleStatus> moduleStatusList) {
		this.moduleStatusList = moduleStatusList;
	}

	public ModuleStatus getModuleStatus() {
		return moduleStatus;
	}
	

	public void setModuleStatus(ModuleStatus moduleStatus) {
		this.moduleStatus = moduleStatus;
	}

	public List<String> getDanjustatus() {
		return danjustatus;
	}

	public void setDanjustatus(List<String> danjustatus) {
		this.danjustatus = danjustatus;
	}

	public List<String> getModuleids() {
		return moduleids;
	}

	public void setModuleids(List<String> moduleids) {
		this.moduleids = moduleids;
	}

	public String getKk() {
		return kk;
	}

	public void setKk(String kk) {
		this.kk = kk;
	}

	public int getTable_type() {
		return table_type;
	}

	public void setTable_type(int table_type) {
		this.table_type = table_type;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Map<String, String> getMap_() {
		return map_;
	}

	public void setMap_(Map<String, String> map_) {
		this.map_ = map_;
	}

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public Map<String, String> getView() {
		return view;
	}

	public void setView(Map<String, String> view) {
		this.view = view;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getUpdate_user_ids() {
		return update_user_ids;
	}

	public void setUpdate_user_ids(String update_user_ids) {
		this.update_user_ids = update_user_ids;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getUpdate_ids() {
		return update_ids;
	}

	public void setUpdate_ids(String update_ids) {
		this.update_ids = update_ids;
	}

	public int getFinal_() {
		return final_;
	}

	public void setFinal_(int final_) {
		this.final_ = final_;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getRelateIds() {
		return relateIds;
	}

	public void setRelateIds(String relateIds) {
		this.relateIds = relateIds;
	}

	public String getYewu_columns() {
		return yewu_columns;
	}

	public void setYewu_columns(String yewu_columns) {
		this.yewu_columns = yewu_columns;
	}

	public String getTime_columns() {
		return time_columns;
	}

	public void setTime_columns(String time_columns) {
		this.time_columns = time_columns;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getTb_calculates_string() {
		return tb_calculates_string;
	}

	public void setTb_calculates_string(String tb_calculates_string) {
		this.tb_calculates_string = tb_calculates_string;
	}

	public List<Tb_calculate> getTb_calculates() {
		return tb_calculates;
	}

	public void setTb_calculates(List<Tb_calculate> tb_calculates) {
		this.tb_calculates = tb_calculates;
	}

	public String getRelate_tablename_calculate() {
		return relate_tablename_calculate;
	}

	public void setRelate_tablename_calculate(String relate_tablename_calculate) {
		this.relate_tablename_calculate = relate_tablename_calculate;
	}

	public String getRelate_columnname_calculate() {
		return relate_columnname_calculate;
	}

	public void setRelate_columnname_calculate(
			String relate_columnname_calculate) {
		this.relate_columnname_calculate = relate_columnname_calculate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public InsuranceCategoriesDao getICDao() {
		return ICDao;
	}

	public void setICDao(InsuranceCategoriesDao dao) {
		ICDao = dao;
	}

	public String getQiujiRelateColumns() {
		return qiujiRelateColumns;
	}

	public void setQiujiRelateColumns(String qiujiRelateColumns) {
		this.qiujiRelateColumns = qiujiRelateColumns;
	}

	public List<IC_column_qiuji_qiuhe> getColumns_qiuji_qiuhe() {
		return columns_qiuji_qiuhe;
	}

	public void setColumns_qiuji_qiuhe(
			List<IC_column_qiuji_qiuhe> columns_qiuji_qiuhe) {
		this.columns_qiuji_qiuhe = columns_qiuji_qiuhe;
	}

	public Map<String, List<Tags>> getList_tags_relate() {
		return list_tags_relate;
	}

	public void setList_tags_relate(Map<String, List<Tags>> list_tags_relate) {
		this.list_tags_relate = list_tags_relate;
	}

	public String getRelate_tablename() {
		return relate_tablename;
	}

	public void setRelate_tablename(String relate_tablename) {
		this.relate_tablename = relate_tablename;
	}

	public String getRelate_columnname() {
		return relate_columnname;
	}

	public void setRelate_columnname(String relate_columnname) {
		this.relate_columnname = relate_columnname;
	}

	public String getCheck_json_result() {
		return check_json_result;
	}

	public void setCheck_json_result(String check_json_result) {
		this.check_json_result = check_json_result;
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

	public Eluser getEluser() {
		return eluser;
	}

	public void setEluser(Eluser eluser) {
		this.eluser = eluser;
	}

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public List<ModuleManage> getList_module() {
		return list_module;
	}

	public void setList_module(List<ModuleManage> list_module) {
		this.list_module = list_module;
	}

	public ModuleManageDao getModuleManageDao() {
		return moduleManageDao;
	}

	public void setModuleManageDao(ModuleManageDao moduleManageDao) {
		this.moduleManageDao = moduleManageDao;
	}

	/*
	 * actions
	 */
	public String addClientInit() throws ElException {

		return "addClientInitSuccess";
	}

	public String addClient() throws ElException {

		List<Clientlinkman> list_clientlinkman = new ArrayList<Clientlinkman>();
		Clientlinkman clientlinkman;
		int clientid;

		String type[] = getRequest().getParameterValues(
				"list_clientlinkman.type");
		String name[] = getRequest().getParameterValues(
				"list_clientlinkman.name");
		String sex[] = getRequest()
				.getParameterValues("list_clientlinkman.sex");
		String dep[] = getRequest()
				.getParameterValues("list_clientlinkman.dep");

		String duty[] = getRequest().getParameterValues(
				"list_clientlinkman.duty");
		String task[] = getRequest().getParameterValues(
				"list_clientlinkman.task");
		String worktel[] = getRequest().getParameterValues(
				"list_clientlinkman.worktel");
		String phone[] = getRequest().getParameterValues(
				"list_clientlinkman.phone");
		String tax[] = getRequest()
				.getParameterValues("list_clientlinkman.tax");

		String emainl[] = getRequest().getParameterValues(
				"list_clientlinkman.emainl");
		String hometel[] = getRequest().getParameterValues(
				"list_clientlinkman.hometel");
		String msnqq[] = getRequest().getParameterValues(
				"list_clientlinkman.msnqq");
		String birthday[] = getRequest().getParameterValues(
				"list_clientlinkman.birthday");
		String hobby[] = getRequest().getParameterValues(
				"list_clientlinkman.hobby");

		String remark[] = getRequest().getParameterValues(
				"list_clientlinkman.remark");

		for (int i = 0; name != null && i < name.length; i++) {
			clientlinkman = new Clientlinkman();

			clientlinkman.setType(type[i]);
			clientlinkman.setName(name[i]);
			clientlinkman.setSex(sex[i]);
			clientlinkman.setDep(dep[i]);

			clientlinkman.setDuty(duty[i]);
			clientlinkman.setTask(task[i]);
			clientlinkman.setWorktel(worktel[i]);
			clientlinkman.setPhone(phone[i]);
			clientlinkman.setTax(tax[i]);

			clientlinkman.setEmainl(emainl[i]);
			clientlinkman.setHometel(hometel[i]);
			clientlinkman.setMsnqq(msnqq[i]);
			clientlinkman.setBirthday(birthday[i]);
			clientlinkman.setHobby(hobby[i]);

			list_clientlinkman.add(clientlinkman);
		}

		client.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		clientid = clientDao.insert_client(client);
		if (list_clientlinkman.size() > 0 && clientid > 0)
			clientDao.insert_clientlinkman_list(list_clientlinkman, clientid);

		return "addClientSuccess";
	}

	public String myClient() throws ElException {
		if (client == null)
			client = new Client();
		client.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		list_client = clientDao.get_my_add_client(client, getPageNow(),
				getPageSize());
		count = clientDao.get_my_add_clientCount(client);

		return "myClientSuccess";
	}

	public String getClientView() throws ElException {
		client = clientDao.get_client_by_id(client.getId());
		list_clientlinkman = clientDao.get_clientlinkman_by_userid(client
				.getId());

		return "getClientViewSuccess";
	}

	public String delClient() throws ElException {

		clientDao.del_client_by_id(client.getId());
		clientDao.del_clientlinkman_by_clientid(client.getId());
		if (getRequest().getParameter("delmyclient") != null) {
			return "delMyClientSuccess";
		}
		return "delClientSuccess";
	}

	public String updateClient() throws ElException {
		String deleteid[] = linkmanids.split(",");

		List<Clientlinkman> list_clientlinkman = new ArrayList<Clientlinkman>();
		List<Clientlinkman> list_clientlinkman_insert = new ArrayList<Clientlinkman>();
		Clientlinkman clientlinkman;

		String type[] = getRequest().getParameterValues(
				"list_clientlinkman.type");
		String name[] = getRequest().getParameterValues(
				"list_clientlinkman.name");
		String sex[] = getRequest()
				.getParameterValues("list_clientlinkman.sex");
		String dep[] = getRequest()
				.getParameterValues("list_clientlinkman.dep");

		String duty[] = getRequest().getParameterValues(
				"list_clientlinkman.duty");
		String task[] = getRequest().getParameterValues(
				"list_clientlinkman.task");
		String worktel[] = getRequest().getParameterValues(
				"list_clientlinkman.worktel");
		String phone[] = getRequest().getParameterValues(
				"list_clientlinkman.phone");
		String tax[] = getRequest()
				.getParameterValues("list_clientlinkman.tax");

		String emainl[] = getRequest().getParameterValues(
				"list_clientlinkman.emainl");
		String hometel[] = getRequest().getParameterValues(
				"list_clientlinkman.hometel");
		String msnqq[] = getRequest().getParameterValues(
				"list_clientlinkman.msnqq");
		String birthday[] = getRequest().getParameterValues(
				"list_clientlinkman.birthday");
		String hobby[] = getRequest().getParameterValues(
				"list_clientlinkman.hobby");

		String remark[] = getRequest().getParameterValues(
				"list_clientlinkman.remark");
		String id[] = getRequest().getParameterValues("list_clientlinkman.id");

		// list_clientlinkman.size();
		for (int i = 0; name != null && i < name.length; i++) {
			clientlinkman = new Clientlinkman();

			clientlinkman.setType(type[i]);
			clientlinkman.setName(name[i]);
			clientlinkman.setSex(sex[i]);
			clientlinkman.setDep(dep[i]);

			clientlinkman.setDuty(duty[i]);
			clientlinkman.setTask(task[i]);
			clientlinkman.setWorktel(worktel[i]);
			clientlinkman.setPhone(phone[i]);
			clientlinkman.setTax(tax[i]);

			clientlinkman.setEmainl(emainl[i]);
			clientlinkman.setHometel(hometel[i]);
			clientlinkman.setMsnqq(msnqq[i]);
			clientlinkman.setBirthday(birthday[i]);
			clientlinkman.setHobby(hobby[i]);
			if (i < id.length) {
				clientlinkman.setId(Integer.valueOf(id[i]));
			} else
				clientlinkman.setId(-1);

			if (clientlinkman.getId() < 0)
				list_clientlinkman.add(clientlinkman);
			else {
				int k;
				if (deleteid.length == 1 && deleteid[0].equals(""))
					k = -1;// 无删除联系人
				else
					k = deleteid.length;// 有删除联系人
				// 删除要删除的联系人
				if (k > 0)// 有需要删除的人
				{
					for (int j = 0; k > 0 && j < deleteid.length; j++) {
						if (!deleteid[j].equals("")
								&& clientlinkman.getId() == Integer
										.valueOf(deleteid[j]))
							break;
						else
							k--;
					}
					if (k == 0) {
						list_clientlinkman.add(clientlinkman);
					}
				} else
				// 无需要删除的
				{
					list_clientlinkman.add(clientlinkman);
				}

			}
		}

		// 删除clientlinkman
		for (int i = 0; i < deleteid.length; i++) {
			if (!deleteid[i].equals(""))
				clientDao.del_clientlinkman_by_id(Integer.valueOf(deleteid[i]));
		}

		// updateclientlinkman
		for (int i = 0; i < list_clientlinkman.size(); i++) {
			if (list_clientlinkman.get(i).getId() > 0)// update
			{
				clientDao.update_clientlinkman(list_clientlinkman.get(i));
				// list_clientlinkman.remove(i);
			} else {
				list_clientlinkman_insert.add(list_clientlinkman.get(i));
			}
		}

		// insert
		clientDao.insert_clientlinkman_list(list_clientlinkman_insert, client
				.getId());

		// update client
		clientDao.update_client(client);

		return "updateClientSuccess";
	}

	public String updateClientInit() throws ElException {

		client = clientDao.get_client_by_id(client.getId());
		list_clientlinkman = clientDao.get_clientlinkman_by_userid(client
				.getId());
		return "updateClientInitSuccess";
	}

	public String deleteClientlinkman() throws ElException {
		clientDao.del_clientlinkman_by_id(Integer.valueOf(linkmanids));
		return " deleteClientlinkmanSuccess";
	}

	public String updateClientlinkman() throws ElException {
		return "updateClientlinkmanSuccess";
	}

	/*
	 * contact actions
	 */
	public String addContact() throws ElException {

		int contactid = 0;
		Contactstuff cs;

		String staddr[] = getRequest().getParameterValues(
				"knowledge.stuffs.description");
		String sttitle[] = getRequest().getParameterValues(
				"knowledge.stuffs.title");

		contact.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));

		contactid = clientDao.insert_contact(contact);

		if (null != staddr) {
			for (int i = 0; i < staddr.length; i++) {
				cs = new Contactstuff();
				cs.setContactid(contactid);
				cs.setStuffaddr(staddr[i]);
				cs.setTitle(sttitle[i]);
				clientDao.insert_contact_stuff(cs);
			}
		}

		return "addContactSuccess";
	}

	public String addContactInit() throws ElException {
		return "addContactInitSuccess";
	}

	public String myContact() throws ElException {
		if (contact == null)
			contact = new Contact();
		contact.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));

		list_contact = clientDao.select_my_contact(contact, getPageNow(),
				getPageSize());
		count = clientDao.select_my_contact_count(contact);

		return "myContactSuccess";
	}

	public String deleteContact() throws ElException {
		clientDao.delete_contact_by_id(contact.getId());
		return "deleteContactSuccess";
	}

	public String updateContactInit() throws ElException {

		contact = clientDao.get_contact_by_id(contact.getId());
		list_contactstuff = clientDao
				.get_contact_stuff_list_by_contactid(contact.getId());

		return "updateContactInitSuccess";
	}

	public String deleteContactstuff() throws ElException {
		contact.setId(contact.getId());
		clientDao.delete_contact_stuff_by_id(contactstuff.getId());
		return "deleteContactstuffSuccess";
	}

	public String updateContact() throws ElException {
		Contactstuff cs = new Contactstuff();
		int contactid = contact.getId();

		String staddr[] = getRequest().getParameterValues(
				"knowledge.stuffs.description");
		String sttitle[] = getRequest().getParameterValues(
				"knowledge.stuffs.title");

		clientDao.delete_contact_stuff_by_contactid(contact.getId());

		if (null != staddr) {
			for (int i = 0; i < staddr.length; i++) {
				cs.setContactid(contactid);
				cs.setStuffaddr(staddr[i]);
				cs.setTitle(sttitle[i]);
				clientDao.insert_contact_stuff(cs);
			}
		}

		clientDao.update_contact_by_id(contact);

		return "updateContactSuccess";
	}

	public String viewContact() throws ElException {

		contact = clientDao.get_contact_by_id(contact.getId());
		list_contactstuff = clientDao
				.get_contact_stuff_list_by_contactid(contact.getId());

		return "viewContactSuccess";
	}

	public String downContactstuff() throws ElException {
		try {
			contactstuff = clientDao.get_contactstuff_by_id(contactstuff
					.getId());
			filename = contactstuff.getStuffaddr();
			filename = filename.substring(filename.indexOf("elstuffs"));
			// 获取文件在服务器的具体路径
			String path = ServletActionContext.getServletContext().getRealPath(
					filename);
			String fileext = filename.substring(filename.lastIndexOf("."));
			String filename1 = contactstuff.getTitle() + fileext;
			System.out.println("filename1" + filename1);
			filename = new String(filename1.getBytes(), "ISO8859-1");
			System.out.println("filename" + filename1);
			try {
				System.out.println(path);
				inputStream = new FileInputStream(path);

			} catch (Exception e) {
				// logger.error("文档下载失败", e);
				throw new ElException("下载资料出错", e);
			}
		} catch (Exception e) {
			// logger.error("文档下载失败", e);
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}

		return "downContactstuff";
	}

	public String searchContact() throws ElException {
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
		// depTree = departmentDao.getDepTree_level1(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// if (department == null)
		// department = new Department();
		// } else {
		// depTree = departmentDao.getDepTree_level1(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// if (depTree.getChild().size() != 0) {
		// if (department == null)
		// department = new Department(depTree.getChild().get(0).getId());
		// } else {
		// setElmessage("您无可操作的节点！请联系管理员！");
		// return "error";
		// }
		// }
		// department = departmentDao.getDepById(department.getId());

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

		if (contact == null)
			contact = new Contact();
		list_contact = clientDao.get_contact_by_dep(contact, department,
				getPageNow(), getPageSize());
		count = clientDao.get_contact_by_dep_count(contact, department);

		return "searchContactSuccess";
	}

	public String searchClient() throws ElException {
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
		// depTree = departmentDao.getDepTree_level1(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// if (department == null)
		// department = new Department();
		// } else {
		// depTree = departmentDao.getDepTree_level1(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// if (depTree.getChild().size() != 0) {
		// if (department == null)
		// department = new Department(depTree.getChild().get(0).getId());
		// } else {
		// setElmessage("您无可操作的节点！请联系管理员！");
		// return "error";
		// }
		// }
		// department = departmentDao.getDepById(department.getId());

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

		if (client == null)
			client = new Client();
		list_client = clientDao.get_client_by_dep(client, department,
				getPageNow(), getPageSize());
		count = clientDao.get_client_by_dep_count(client, department);

		return "searchClientSuccess";
	}

	// 我负责的客户
	public String searchMyClient() throws ElException {
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
		// depTree = departmentDao.getDepTree_level1(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// if (department == null)
		// department = new Department();
		// } else {
		// depTree = departmentDao.getDepTree_level1(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// if (depTree.getChild().size() != 0) {
		// if (department == null)
		// department = new Department(depTree.getChild().get(0).getId());
		// } else {
		// setElmessage("您无可操作的节点！请联系管理员！");
		// return "error";
		// }
		// }
		// department = departmentDao.getDepById(department.getId());

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

		if (client == null) {
			client = new Client();
		}
		client.setPrincipalid(getSessionIntValue(ElConstants.SESSION_USERID));
		list_client = clientDao.get_client_my_by_dep(client, department,
				getPageNow(), getPageSize());
		count = clientDao.get_client_my_by_dep_count(client, department);
		return "searchMyClientSuccess";
	}

	public String testtags() throws ElException {

		// if(client==null) client=new Client();
		// client.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		// list_client=clientDao.get_my_add_client(client, getPageNow(),
		// getPageSize());
		// count=clientDao.get_my_add_clientCount(client);
		// contact = clientDao.get_contact_by_id(33);

		// moduleManageDao.add_module();

		return "testtagsSuccess";
	}

	// --------------------------------------------------------------------------------------------
	/*
	 * 客户联系自定义
	 */

	public String contactlinkmanDesigneAddInit() throws ElException {
//		areaList = TagsUtil.ToGson(indexDao.areaList(null, null));
		
		checkRichtext = tagsDao.checkRichtext(tablename);

		if (tagsDao.checkTable(tablename) == 1) {// 业务表
			String produce_table = tagsDao.getProduceTableByTable(tablename);
			if (produce_table != null && !produce_table.equals("")) {
				can_select_show_columns = 1;
			}
		}
		// if(checkRichtext.equals(""))
		// {
		// checkRichtext="{'实数','日期','大文本','整数','下拉选项','附件上传','图片','富文本'}";
		// }
		// else
		// {
		// checkRichtext="{'实数','日期','大文本','整数','下拉选项','附件上传','图片'}";
		// }
		return "contactlinkmanDesigneAddInitSuccess";
	}

	public String contactlinkmanDesigneAdd() throws ElException {
		// 获取 是否显示的属性值
		String[] str = getRequest().getParameterValues("display");
		String[] nowdate = getRequest().getParameterValues("timecheck");
		String[] date_number = getRequest().getParameterValues("date_number");
		String[] wbdefault = getRequest().getParameterValues("wbdefault");
		String[] wbwidth = getRequest().getParameterValues("wbwidth");
		String[] sum = getRequest().getParameterValues("sumcheck");
		String[] baifenbi = getRequest().getParameterValues("baifenbicheck");
		String[] calculate = getRequest().getParameterValues("calculatecheck");
		String[] relateIsShowComplete = getRequest().getParameterValues(
				"relateIsShowComplete");
		String[] show_time_jindu = getRequest().getParameterValues(
				"show_time_jinducheck");
		String[] is_qiuji = getRequest().getParameterValues("qiujicheck");
		String[] is_qiuhe = getRequest().getParameterValues("qiuhecheck");
		String[] zuoweiji = getRequest().getParameterValues("zuoweijicheck");
		String[] zuoweihe = getRequest().getParameterValues("zuoweihecheck");
		String[] qiuji_column_name = getRequest().getParameterValues(
				"qiuji_column_name");
		String[] qiuhe_column_name = getRequest().getParameterValues(
				"qiuhe_column_name");

		for (int i = 0; i < str.length; i++) {
			if (str[i].equals("add_display"))
				tags.setAdd_display(1);
			else if (str[i].equals("update_display"))
				tags.setUpdate_display(1);
			else if (str[i].equals("view_display"))
				tags.setView_display(1);
			else if (str[i].equals("list_display"))
				tags.setList_display(1);
			else if (str[i].equals("mutilsearch_display"))// 组合搜索
				tags.setMutilsearch_display(1);
			else if (str[i].equals("departsearch_display"))
				tags.setDepartsearch_display(1);
			else if (str[i].equals("required"))
				tags.setRequired(1);
		}

		if (qiuji_column_name != null) {
			tags.setQiuji_column_name(qiuji_column_name[0]);
		}
		if (qiuhe_column_name != null) {
			tags.setQiuhe_column_name(qiuhe_column_name[0]);
		}

		// 是否求和
		if (sum != null) {
			if (sum[0].equals("sum"))
				tags.setSum_display(1);
		}
		// 是否显示进度条
		if (baifenbi != null) {
			if (baifenbi[0].equals("baifenbi"))
				tags.setJindutiao(1);
		}
		// 是否可以模块间计算
		if (calculate != null) {
			if (calculate[0].equals("calculate"))
				tags.setIs_calculate(1);
		}
		// 是否完整显示
		if (relateIsShowComplete != null) {
			if (relateIsShowComplete[0].equals("relateIsShowComplete"))
				tags.setRelateIsShowComplete(1);
		}
		// 是否显示时间进度
		if (show_time_jindu != null) {
			if (show_time_jindu[0].equals("show_time_jindu"))
				tags.setShow_time_jindu(1);
		}
		// 是否求积
		if (is_qiuji != null) {
			if (is_qiuji[0].equals("is_qiuji"))
				tags.setIs_qiuji(1);
		}
		// 是否求和
		if (is_qiuhe != null) {
			if (is_qiuhe[0].equals("is_qiuhe"))
				tags.setIs_qiuhe(1);
		}
		// 是否作为积
		if (zuoweiji != null) {
			if (zuoweiji[0].equals("zuowei_ji"))
				tags.setZuowei_ji(1);
		}
		// 是否作为和
		if (zuoweihe != null) {
			if (zuoweihe[0].equals("zuowei_he"))
				tags.setZuowei_he(1);
		}

		if (tags.getDisplay_type().equals("日期")) {
			if (nowdate != null) {
				if (nowdate[0].equals("nowdate")) {
					if (date_number != null) {
						if (date_number[0].equals("0"))
							tags.setDefault_value("nowdate");
						else
							tags.setDefault_value("nowdate_" + date_number[0]);
					}
				}
			}
		}
		if (tags.getDisplay_type().equals("文本") ) {
			if (!wbwidth[0].equals("") || !wbdefault[0].equals(""))
				tags.setDefault_value(wbdefault[0] + "==" + wbwidth[0]);
		}
		if(tags.getDisplay_type().equals("城市")){
			tags.setDefault_value(wbdefault[0] );
		}
		if (tags.getDisplay_type().equals("单选")
				|| tags.getDisplay_type().equals("复选")
				|| tags.getDisplay_type().equals("下拉选项")) {
			tags.setDefault_value(tags.getDefault_value_2());
		}
		if (!tags.getDefault_value().equals("")) {
			tags.setDefault_value(tags.getDefault_value());
		}

		tablename = tags.getTable_name();

		// 验证字段是否已经存在
		if (tagsDao.checkColumnIsExistByTable(tablename, tags.getColumn_name())) {// 列名存在
			this.setElmessage("列名已经存在，请在列名后添加1、2、3等数字区分!!!");
			return "contactlinkmanDesigneAddInit";
		} else {
			// 判断该表是否是结果表,如果是结果表，添加字段的时候相应的过程表也要添加字段与之对应
			String result_column = "";
			String produce_column = "";
			if (tagsDao.checkTable(tags.getTable_name()) == 3) {
				result_column = tags.getColumn_name();
				tagsDao.insert_designe_field(0, tags);// 插入结果表
				
				// 插入过程表
				String produce_tablename = tagsDao
						.getProduceTableByResultTable(tags.getTable_name());
				tags.setTable_name(produce_tablename);
				produce_column = new ModuleManageDaoImpl().createNewColumn(
						result_column, produce_tablename);
				tags.setColumn_name(produce_column);
//				tags.setColumn_name(new ModuleManageDaoImpl().createNewColumn(
//						tags.getColumn_name(), produce_tablename));
				if (produce_tablename != null && !produce_tablename.equals("")){
					tagsDao.insert_designe_field(0, tags);
					//更新resulttable_producetable表中结果表和过程表字段关系
					tagsDao.update_resulttable_producetable(produce_tablename,produce_column,tablename,result_column);
				}

				// 添加过程表中字段
				// -----修改表alter table tablename add(name varchar2(20))
				// tagsDao.addUpdateProduceColumn(tags);

			} else if (tagsDao.checkTable(tags.getTable_name()) == 2) {// 过程表
				tagsDao.insert_designe_field(2, tags);
			} else {// 业务表
				tagsDao.insert_designe_field(0, tags);
			}
		}
		
		//添加字段备注信息
		if(tagsMark!=null&&tagsMark.getColumnname()!=null&&!tagsMark.getColumnname().equals("")){
			tagsDao.insert_tb_tags_mark(tagsMark);
		}
		
		//添加成功后，将静态页删除
		TagsUtil.deleteStaticHtml(tablename);

		return "contactlinkmanDesigneAddSuccess";
	}
	
	public String getCurrentUserinfo() throws ElException{
		currentUser = tagsDao.getCurrentUserByUserId(currentUser,getSessionIntValue(ElConstants.SESSION_USERID));
		check_json_result = TagsUtil.ToGsonObj(currentUser);
		
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String addContactTagsInit() throws ElException, UnsupportedEncodingException {
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		actionName = "addContactTagsInit";

		String time_ids = "";
		String yewu_ids = "";
		
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		fromtablename = moduleManageDao.getFromtablenameByTablename(tablename);

		currentUser = tagsDao.getCurrentUser(tablename);
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		if(currentUser != null )
			currentUser = tagsDao.getCurrentUserByUserId(currentUser,getSessionIntValue(ElConstants.SESSION_USERID));
		
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				uid = String
						.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
				username = userDao.getUserById(
						getSessionIntValue(ElConstants.SESSION_USERID))
						.getRealname();
			}
			if (list_tags.get(i).getDisplay_type().equals("百分比")) {
				if (list_tags.get(i).getTime_jindu_ids() != null) {
					time_ids = list_tags.get(i).getTime_jindu_ids();
					String[] str = time_ids.split(",");
					for (int j = 0; j < str.length; j++) {
						time_columns += tagsDao.getColumn_name_by_id(Integer
								.parseInt(str[j]))
								+ ",";
					}
					time_columns += tagsDao.getColumn_name_by_tableName("time",
							tablename);// 时间进度
				}
				if (list_tags.get(i).getYewu_jindu_ids() != null) {
					yewu_ids = list_tags.get(i).getYewu_jindu_ids();
					String[] str = yewu_ids.split(",");
					for (int j = 0; j < str.length; j++) {
						yewu_columns += tagsDao.getColumn_name_by_id(Integer
								.parseInt(str[j]))
								+ ",";
					}
					yewu_columns += tagsDao.getColumn_name_by_tableName("time",
							tablename);// 业务进度
				}
			}
			//手机登陆的判断，页面显示出现少一行的现象
			if(httpRequestDeviceUtils.isMobileDevice(getRequest())){
				if(list_tags!=null&&list_tags.size()>0){
				 if(list_tags.get(i).getId()==1171||list_tags.get(i).getId()==2053){
					 list_tags.remove(i);
				 }
				}
				
			}
			//获取kk未相关字段过程字段
			//如何区分过程表和业务表
			//当前将数据库中相关字段完整显示只有"相关物品"
			//TODO
			if(list_tags.get(i).getDisplay_type().equals("相关字段") && list_tags.get(i).getRelateIsShowComplete() == 1
					&&tagsDao.getProduceTableByTable(tablename) != null && !tagsDao.getProduceTableByTable(tablename).equals("")){
				kk = list_tags.get(i).getColumn_name();
			}
		}
		if(httpRequestDeviceUtils.isMobileDevice(getRequest()))
			return "addContactTagsInitSuccess_phone";
		return "addContactTagsInitSuccess";
	}

	public String addContactTags() throws ElException, UnsupportedEncodingException {
		String result_produce_tables = "";
		if (tablename.indexOf(",") >= 0) {
			result_produce_tables = tablename.split(",")[0] + ","
					+ tablename.split(",")[1];
			tablename = tablename.split(",")[2];
		}
		Map<String, String> hm = new HashMap<String, String>();
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		hm = TagsUtil.addToDb(list_tags, getRequest(), hm);
		
		// 客户登记簿模块唯一验证table_name=KHDJB,column_name=KHDJB_KHMC
		if (tablename != null && tablename.equals("KHDJB")) {
			if (getRequest().getParameter("KHDJB_KHMC") == null) {
				this.setElmessage("failure!!!");
				return "insert_failure";
			} else if (tagsDao.checkColumnValueIsExist(tablename, "KHDJB_KHMC",
					getRequest().getParameter("KHDJB_KHMC"))) {
				this.setElmessage("failure!!!");
				return "insert_failure";
			}
		}

		// returnId为单据id
		int returnId = tagsDao.insert_tableinfo_by_tablename(1, hm,
				getTablename(list_tags.get(0).getColumn_name()),
				getSessionIntValue(ElConstants.SESSION_USERID));
		
		//如果是图片的话，要同时插入tb_pic表
		if(returnId>0){
			tagsDao.insert_into_tb_pic(getTablename(list_tags.get(0).getColumn_name()),hm,returnId);
		}
		
		// 插入到过程表
		// 相关列
		System.out.println(parameters);
		if(!parameters.equals("")){
			for (int i = 0; i < list_tags.size(); i++) {
				if (list_tags.get(i).getDisplay_type().equals("相关字段")
						&& list_tags.get(i).getRelateIsShowComplete() == 1) {
					// 插入到过程表
					if (!result_produce_tables.equals("")) {
						list_tags = tagsDao
								.select_designe_field_by_tablename(result_produce_tables
										.split(",")[1]);

						String jisuan_type = "";
						for (int m = 0; m < list_tags.size(); m++) {
							String temp = tagsDao.checkColumnIsBiaojianjisuan(
									list_tags.get(m).getTable_name(), list_tags
											.get(m).getColumn_name());
							if (temp != null && !temp.equals("")) {
								String jisuan_type_ = temp.split(";")[1];// TB_MM_233==add
								String[] jisuans = jisuan_type_.split("==");
								String o = jisuans[1];
								if (jisuans != null) {
									jisuans = jisuans[0].split(",");
									for (int k = 0; k < jisuans.length; k++) {
										if (jisuans[k].indexOf(tablename) >= 0) {
											jisuan_type = o;
										}
									}
								}
							}

						}
						if (parameters.indexOf("===") >= 0) {
							for (int j = 0; j < parameters.split("===").length; j++) {
								tagsDao
										.addToProduce(
												jisuan_type,
												list_tags,
												result_produce_tables,
												parameters.split("===")[j],
												getSessionIntValue(ElConstants.SESSION_USERID),
												returnId);
							}
						} else {
							tagsDao.addToProduce(jisuan_type, list_tags,
									result_produce_tables, parameters,
									getSessionIntValue(ElConstants.SESSION_USERID),
									returnId);
						}
					}

					// 自动获取添加相关数据到过程表时
					if (relateIds != null && !relateIds.equals("")
							&& ex_columnname != null && !ex_columnname.equals("")) {
						String[] relateIds_array = relateIds.split(",");
						for (int j = 0; j < relateIds_array.length; j++) {
							tagsDao.addToTb_tags_relate(ex_columnname, Integer
									.parseInt(relateIds_array[j]), returnId);
						}
					}
				}
			}

			// 更新结果表has_init== id
			if (tagsDao.checkTable(tablename) == 3) {// 结果表
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getDisplay_type().equals("整数")
							|| list_tags.get(i).getDisplay_type().equals("实数")) {
						if (list_tags.get(i).getIs_calculate() == 1) {
							tagsDao.updateProduceHas_init(tablename, list_tags.get(
									i).getColumn_name(), returnId);
						}
					}
				}
			}
		}
		RemindUtil.load();
		
		if(fromcopy == 1){
			setElmessage(URLEncoder.encode(URLEncoder.encode("您填写的内容已提交,谢谢,点'确定'后返回当前页", "UTF-8"), "UTF-8"));
			System.out.println(elmessage);
			return "addContactTagsInit";
		}

		return "addContactTagsSuccess";
	}

	public String addContactTags_ajas() throws ElException {
		System.out.println(tablename);
		System.out.println(parameters);

		tagsDao.insert_tableinfo_by_tablename_relatecolumn(
				getSessionIntValue(ElConstants.SESSION_USERID), parameters,
				tablename);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getDesigneColumns() throws ElException {
		check_json_result = String
				.valueOf(tagsDao.getDesigneColumns(tablename));

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

	public String myContactTags() throws ElException,
			UnsupportedEncodingException, ParseException {
//		String ppp = (String)getRequest().getParameter("ppp");
//		if(ppp!=null&& ppp.equals("nihao")){
//			return "container";
//		}
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);//模块信息
		moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());//模块自定义信息
		//审核级别最小和最大
		ca_small = tagsDao.get_audit_by_tablename(tablename,1);
		ca_big = tagsDao.get_audit_by_tablename(tablename,2);
		ca = tagsDao.get_audit_by_tablename_userid(tablename, getSessionIntValue(ElConstants.SESSION_USERID));
		
		actionName = "myContactTags";
		// 是否显示和
		columns_qiuji_qiuhe = ICDao.getQiujiColumns(tablename);
		qiujiRelateColumns = TagsUtil.ToGson(columns_qiuji_qiuhe);
		
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

		Map<String, String> hm = new HashMap<String, String>();
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		String order = "";
		if (tags != null) {
			if (tags.getOrdercolumn() != null
					&& !tags.getOrdercolumn().equals("")) {
				order = " order by ";
				order += tags.getOrdercolumn() + " ";
				order += tags.getOrdersc();
			}
		}
		Map<String,Object> list_tags_and_hm = TagsUtil.addSearch(list_tags, getRequest());
		list_tags = (List<Tags>)list_tags_and_hm.get("list_tags");
		hm = (Map<String,String>)list_tags_and_hm.get("hm");

		userid = getSessionIntValue(ElConstants.SESSION_USERID);
		realname = tagsDao.get_eluser_realname_by_id(userid);

		// 查找sum
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getSum_display() == 1) {
				if (list_tags.get(i).getColumn_type().equals("number")) {
					list_tags.get(i).setSum_i(
							(int) tagsDao.select_sum(String.valueOf(userid),
									list_tags, tablename, list_tags.get(i)
											.getColumn_name(), hm, department));
				} else if (list_tags.get(i).getColumn_type().equals("float")) {
					list_tags.get(i).setSum_f(
							tagsDao.select_sum(String.valueOf(userid),
									list_tags, tablename, list_tags.get(i)
											.getColumn_name(), hm, department));
				}
			}
		}

		//初始化更新时间进度
		TagsUtil.updateTimeJindu(tablename,tagsDao);
		

		// 初始化更新业务进度
		TagsUtil.updateYewuJindu(tablename,tagsDao,list_tags);


		if (rx == 1) {
			list_designe = tagsDao.select_my_tableinfo_by_userid_order_1("", 1,
					list_tags, tablename, hm, userid, order, getPageNow(),
					getPageSize());
			count = tagsDao.select_my_tableinfo_by_userid_count_1(1, list_tags,
					hm, tablename, userid);
		} else {
			list_designe = tagsDao.select_my_tableinfo_by_userid_order("", 0,
					list_tags, tablename, hm, userid, order, getPageNow(),
					getPageSize());
			count = tagsDao.select_my_tableinfo_by_userid_count(0, list_tags,
					hm, tablename, userid);
		}

		return "myContactTagsSuccess";
	}

//	// 判断有无业务进度，有，更新整个表的业务进度字段
//	public void updateYewuJindu(String tablename) throws ElException {
//		//有多组业务进度
//		String yewu_jindu_column = tagsDao.IfHasYewuJindu_column(0, tablename);
//		String[] yewu_array = null;
//		String[] yewu = null;
//		if(yewu_jindu_column != null && !yewu_jindu_column.equals("")){
//			yewu_array = yewu_jindu_column.split("=");
//			if(yewu_array!=null&&yewu_array.length>0){
//				for(int i=0;i<yewu_array.length;i++){
//					yewu = yewu_array[i].split(",");
//					String yewu_jindu = "";
//					String column = "";
//					for (int x = 0; x < yewu.length; x++) {// 将需要计算的字段id转换为字段名称
//						column = tagsDao.getColumn_name_by_id(Integer
//								.parseInt(yewu[x]));
//						if (x == yewu.length - 1){
//							yewu_jindu += column;
//						}
//						else{
//							yewu_jindu += column+ ",";
//						}
//						yewu[x] = column;
//					}
//					tagsDao.updateYewuJindu(tablename, yewu_jindu, list_tags);
//				}
//			}
//		}
//	}

//	public String myPassContactTags() throws ElException {
//		actionName = "myPassContactTags";
//		Map<String, String> hm = new HashMap<String, String>();
//
//		// list_tags = tagsDao
//		// .select_designe_field_by_tablename("tb_clientlinkman_tags");
//		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
//
//		// 组合搜索获取搜索条件
//		for (int i = 0; i < list_tags.size(); i++) {
//			// 相关字段
//			if (list_tags.get(i).getDisplay_type().equals("相关字段")) {
//				String str_relate = (String) getRequest().getParameter(
//						list_tags.get(i).getColumn_name());
//				if (str_relate != null && !str_relate.equals("")) {
//					String arr[] = list_tags.get(i).getDefault_value().split(
//							"==");// 如tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
//					// relate_type==目标表名==目标列名==列名 条件
//					hm.put("relate_type" + "==" + arr[0] + "==" + arr[1] + "=="
//							+ list_tags.get(i).getColumn_name(), str_relate);
//					list_tags.get(i).setValue2(str_relate);// 将搜索条件传回前端
//				}
//				continue;
//			}
//			String str = (String) getRequest().getParameter(
//					list_tags.get(i).getColumn_name());
//			if (str != null && !str.equals("")) {
//				hm.put(list_tags.get(i).getColumn_type() + "=="
//						+ list_tags.get(i).getColumn_name(), str);
//				list_tags.get(i).setValue(str);// 将搜索条件传回前端
//			}
//			// 日期
//			if (list_tags.get(i).getColumn_type().equals("date")) {
//				String str2 = (String) getRequest().getParameter(
//						list_tags.get(i).getColumn_name() + "_");
//				if (str2 != null && !str2.equals("")) {
//					hm.put(list_tags.get(i).getColumn_type() + "=="
//							+ list_tags.get(i).getColumn_name() + "_", str2);
//					list_tags.get(i).setValue2(str2);
//				}
//			}
//			// 数字
//			if (list_tags.get(i).getColumn_type().equals("number")
//					|| list_tags.get(i).getColumn_type().equals("float")) {
//				String str2 = (String) getRequest().getParameter(
//						list_tags.get(i).getColumn_name() + "_");
//				if (str2 != null && !str2.equals("")) {
//					hm.put(list_tags.get(i).getColumn_type() + "=="
//							+ list_tags.get(i).getColumn_name() + "_", str2);
//					list_tags.get(i).setValue2(str2);
//				}
//			}
//
//		}
//
//		// 判断有无时间进度，有，更新整个表的时间进度字段
//		String time_jindu_column = tagsDao.IfHasTimeJindu_column(tablename);
//		if (time_jindu_column != null && !time_jindu_column.equals("")
//				&& time_jindu_column.indexOf(",") > 0) {
//			String[] times = time_jindu_column.split(",");
//			String column = time_jindu_column.split(",")[0] + ",";
//			for (int i = 1; i < times.length; i++) {
//				if (i == times.length - 1)
//					column += tagsDao.getColumn_name_by_id(Integer
//							.parseInt(times[i]));
//				else
//					column += tagsDao.getColumn_name_by_id(Integer
//							.parseInt(times[i]))
//							+ ",";
//			}
//			// 更新
//			tagsDao.updateTimeJindu(tablename, column);
//		}
//
//		// 初始化更新业务进度
//		updateYewuJindu(tablename);
//
//		list_designe = tagsDao.select_my_pass_tableinfo(list_tags, tablename,
//				hm, getPageNow(), getPageSize());
//		count = tagsDao
//				.select_my_pass_tableinfo_count(list_tags, hm, tablename);
//		return "myPassContactTagsSuccess";
//	}
	public String myPassSearchContactTags() throws ElException,
			UnsupportedEncodingException {
		Enumeration params = getRequest().getParameterNames();
		String paramName = "";
		String paramvalue = "";
		while (params.hasMoreElements()) {
			paramName = (String) params.nextElement();
			if(paramName.indexOf("_")!=-1){
				paramvalue = new String(getRequest().getParameter(paramName).getBytes("ISO8859-1"),"UTF-8");
				break;
			}
		}
		actionName = "myPassSearchContactTags";
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());
		//审核级别最小和最大
		ca_big = tagsDao.get_audit_by_tablename(tablename,2);
		
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);//当前用户
		ELUser elUser = userDao.getUserById(userid);
		int depid = elUser.getDepartment().getId();//当前用户部门节点
		//获取所属三级部门的userid
//		List<Integer> userids = userDao.getSuoshuDepUserIdByDepid(userid,depid);
//		int userid_ = userDao.getSuoshuDepUserIdByDepid(userid,depid);
		
		if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_IS_ENQUIRY_IN_TABLE)){//无论哪个用户都查询本表全部数据
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		}else{
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)//根节点用户
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
						true);
			else {
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
						true);
//				if(userids.get(0) == -1 ){//二级节点用户、三级节点用户
//					depTree = departmentDao.getDepTree_level1(
//							getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//							true);
//				}else {
//					//四级节点用户
//					
//					depTree = departmentDao.getDepTree_level1(
//							userids.get(0), "op", -1,
//							true);
//				}
			}
		}
		

		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		
		actionName = "myPassSearchContactTags";
		Map<String, String> hm = new HashMap<String, String>();
		if(paramName != null && !paramName.equals("") && paramvalue != null && !paramvalue.equals("")){
			hm.put(paramName, paramvalue);
		}

		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		// 组合搜索获取搜索条件
		Map<String,Object> list_tags_and_hm = TagsUtil.addSearch(list_tags, getRequest());
		list_tags = (List<Tags>)list_tags_and_hm.get("list_tags");
		hm = (Map<String,String>)list_tags_and_hm.get("hm");
		
		//初始化更新时间进度
		TagsUtil.updateTimeJindu(tablename,tagsDao);

		// 初始化更新业务进度
		TagsUtil.updateYewuJindu(tablename,tagsDao,list_tags);
		
		String order = "";
		if (tags != null) {
			if (tags.getOrdercolumn() != null
					&& !tags.getOrdercolumn().equals("")) {
				order = " order by ";
				order += tags.getOrdercolumn() + " ";
				order += tags.getOrdersc();
			}
		}
		
		list_designe = tagsDao.select_my_pass_tableinfo(
				9,
				list_tags, tablename,
				hm, getPageNow(), getPageSize(),department,order);
		count = tagsDao
				.select_my_pass_tableinfo_count(
						9,
						list_tags, hm, tablename,department);
		if (list_designe.size() == 0)
			getRequest().setAttribute("message", "没有找到符合条件的数据，请修改搜索条件!!!");

		return "myPassSearchContactTagsSuccess";
	}

	public String deleteContactTags() throws ElException {
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				tagsDao.delete_from_tablename_by_id(tablename, id);
			}
		}

		return "deleteContactTagsSuccess";
	}

	public String deleteContactSearchTags() throws ElException {
		// 删除相关过程表中的相关数据
		// 如果相关数据审核通过，那么更新结果表中表间计算字段值，如果审核不通过，不更新
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				this.delete_from_produce_table(tablename, id);

				tagsDao.delete_from_tablename_by_id(tablename, id);
			}
		}
		
		if(final_ == 1){
			return "deleteContactFinalSearchTagsSuccess";
		}
		return "deleteContactSearchTagsSuccess";
	}

	public void delete_from_produce_table(String tablename, int id)
			throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		String produce_table = tagsDao.getProduceTableByTable(tablename);
		if (produce_table != null && !produce_table.equals("")) {// 过程表
			// 过程表中数据审核通过
			String result_table = tagsDao
					.getResultTableByProduceTable(produce_table);
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			Map<String, String> hm = new HashMap<String, String>();
			String column_in_result_table_ = "";
			String columns_in_produce_table_ = "";

			List<Tags> list_tags_relate = tagsDao
					.select_designe_field_by_tablename(produce_table);
			String sqlAppend = " and t.moduleid =  '" + tablename
					+ "' and t.danjuid = " + id;
			List<Map<String,String>> list_designe_relate = tagsDao.select_my_tableinfo_by_userid_order(
					sqlAppend, 1, list_tags_relate, produce_table, hm, userid,
					null, getPageNow(), getPageSize());

			for (int i = 0; i < list_designe_relate.size(); i++) {
				for (int j = 0; j < list_tags_relate.size(); j++) {
					// 判断该字段是否是表间计算的字段
					String column_jisuantype = tagsDao
							.checkColumnIsBiaojianjisuan(produce_table,
									list_tags_relate.get(j).getColumn_name());

					if (column_jisuantype != null
							&& !column_jisuantype.equals("")) {

						String column_in_result_table = tagsDao
								.getColumn_name_by_id(Integer
										.parseInt(column_jisuantype.split(";")[0]));// 结果表中表间计算字段
						column_in_result_table_ = column_in_result_table;
						String columns_in_produce_table = tagsDao
								.getColumnNameFromResultAndProduceTable(
										result_table, produce_table,
										column_in_result_table);
						columns_in_produce_table_ = columns_in_produce_table;
					}
				}
				// 更新过程表中值,将shenhestatus=0，即已创建
				tagsDao.updateProduceContent_(new HashMap<String, String>(),
						Integer.parseInt(list_designe_relate.get(i).get("id")),
						produce_table);
			}
			// 更新结果表中值
			// 相关的ids
			// 普通表
			for (int m = 0; m < list_tags.size(); m++) {
				if (list_tags.get(m).getDisplay_type().equals("相关字段")
						&& list_tags.get(m).getRelateIsShowComplete() == 1) {
					String ids_relate = tagsDao.getRelateIds(list_tags.get(m)
							.getTable_name(),
							list_tags.get(m).getColumn_name(), id);
					String[] array = new String[1];
					if (ids_relate != null && !ids_relate.equals("")) {
						array = new String[ids_relate.length()];
						array = ids_relate.split(",");
					}
					if (array.length > 0) {
						for (int j = 0; j < array.length; j++) {
							tagsDao
									.updateResultContent(
											tagsDao
													.getResultTableByProduceTable(produce_table),
											produce_table,
											column_in_result_table_,
											columns_in_produce_table_, Integer
													.parseInt(array[j]), id);// 更新结果表中表间计算字段
						}
					}
				}
			}
			// 将过程表中删除的相关数据删除
			for (int i = 0; i < list_designe_relate.size(); i++) {
				tagsDao.deleteProduceTableById(produce_table, Integer
						.parseInt(list_designe_relate.get(i).get("id")));
			}

		}
	}

	public String viewContactTags() throws ElException {
		actionName = "viewContactTags";
		currentUser = tagsDao.getCurrentUser(tablename);
		
		currentUser = tagsDao.getCurrentUserByUserId(currentUser,getSessionIntValue(ElConstants.SESSION_USERID));
		
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		list_tags = tagsDao.select_designe_field_content_by_id(list_tags,
				getTablename(list_tags.get(0).getColumn_name()), id);

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
			if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				uid = String.valueOf(tagsDao.getUserIdByTablenameAndId(
						tablename, id));
				username = userDao.getUserById(
						tagsDao.getUserIdByTablenameAndId(tablename, id))
						.getRealname();
			}
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
					list_tags_relate.put(list_tags.get(i).getColumn_name(), tagsDao
							.select_designe_field_by_tablename(list_tags.get(i)
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
					list_tags_relate.put(list_tags.get(i).getColumn_name(), 
							tagsDao.select_designe_field_by_Producetablename(
									tablename,
									tagsDao.getProduceTableByResultTable(list_tags
													.get(i).getDefault_value()
													.split("==")[0]
													.toUpperCase())));
					String sqlAppend = " and t.moduleid =  '" + tablename
							+ "' and t.danjuid = " + id;
					list_designe_relate.put(list_tags.get(i).getColumn_name(),
							tagsDao.select_my_tableinfo_by_relate(
									sqlAppend,
									1,
									list_tags_relate.get(list_tags.get(i).getColumn_name()),
									tagsDao.getProduceTableByResultTable(
											list_tags.get(i).getDefault_value().split("==")[0].toUpperCase()), 
											hm,
									userid, order, getPageNow(), getPageSize()));

				}
			}
		}


		return "viewContactTagsSuccess";
	}

	public String updateContactTagsInit() throws ElException {
		actionName = "updateContactTagsInit";
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		currentUser = tagsDao.getCurrentUser(tablename);
		
		currentUser = tagsDao.getCurrentUserByUserId(currentUser,getSessionIntValue(ElConstants.SESSION_USERID));

		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		list_tags = tagsDao.select_designe_field_content_by_id(list_tags,
				getTablename(list_tags.get(0).getColumn_name()), id);
		// id=id;
		String time_ids = "";
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				uid = String.valueOf(tagsDao.getUserIdByTablenameAndId(
						tablename, id));
				username = userDao.getUserById(
						tagsDao.getUserIdByTablenameAndId(tablename, id))
						.getRealname();
			}
			if (list_tags.get(i).getDisplay_type().equals("百分比")) {
				if (list_tags.get(i).getTime_jindu_ids() != null) {
					time_ids = list_tags.get(i).getTime_jindu_ids();
					String[] str = time_ids.split(",");
					for (int j = 0; j < str.length; j++) {
						time_columns += tagsDao.getColumn_name_by_id(Integer
								.parseInt(str[j]))
								+ ",";
					}
					time_columns += tagsDao.getColumn_name_by_tableName("time",
							tablename);// 时间进度
				}
			}
			if(list_tags.get(i).getDisplay_type().equals("相关字段") && list_tags.get(i).getRelateIsShowComplete() == 1
					&&tagsDao.getProduceTableByTable(tablename) != null && !tagsDao.getProduceTableByTable(tablename).equals("")){
				kk = list_tags.get(i).getColumn_name();
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
					array = new String[ids_relate.length()];
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

				//初始化更新时间进度
				TagsUtil.updateTimeJindu(list_tags.get(i).getDefault_value().split("==")[0].toUpperCase(),tagsDao);

				// 初始化更新业务进度
				TagsUtil.updateYewuJindu(list_tags.get(i).getDefault_value().split("==")[0].toUpperCase(),tagsDao,list_tags);

				// 普通表和结果表做不同处理
				// 普通表
				if (tagsDao.checkTable(list_tags.get(i).getDefault_value()
						.split("==")[0].toUpperCase()) == 1) {
					list_tags_relate.put(list_tags.get(i).getColumn_name(), tagsDao
							.select_designe_field_by_tablename(list_tags.get(i)
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
					list_tags_relate.put(list_tags.get(i).getColumn_name(), 
							tagsDao.select_designe_field_by_Producetablename(
									tablename,
									tagsDao.getProduceTableByResultTable(list_tags
													.get(i).getDefault_value()
													.split("==")[0]
													.toUpperCase())));
					String sqlAppend = " and t.moduleid =  '" + tablename
							+ "' and t.danjuid = " + id;
					list_designe_relate.put(list_tags.get(i).getColumn_name(),
							tagsDao.select_my_tableinfo_by_relate(
									sqlAppend,
									1,
									list_tags_relate.get(list_tags.get(i).getColumn_name()),
									tagsDao.getProduceTableByResultTable(
											list_tags.get(i).getDefault_value().split("==")[0].toUpperCase()), 
											hm,
									userid, order, getPageNow(), getPageSize()));

				}
			}
		}

		return "updateContactTagsInitSuccess";
	}

	public String updateContactTags() throws ElException {
		Map<String, String> hm = new HashMap<String, String>();
		String result_table = "";
		String produce_table = "";
		if (tablename.indexOf(",") >= 0) {
			result_table = tablename.split(",")[0];
			produce_table = tablename.split(",")[1];
			tablename = tablename.split(",")[2];
		} else {
			produce_table = tagsDao.getProduceTableByTable(tablename);
			result_table = tagsDao.getResultTableByProduceTable(produce_table);
		}

		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		hm = TagsUtil.addToDb1(list_tags, getRequest(), hm);
		
		id = Integer.valueOf(getRequest().getParameter("id"));
		tagsDao.update_designe_field_content_by_id(hm, tablename, id);
		
		//如果是图片的话，要同时修改tb_pic表
		if(id>0){
			tagsDao.update_tb_pic(tablename,hm,id);
		}

		// 更新过程表数据，主要是管理员手动添加的字段
		if (parameters != null && !parameters.equals("")) {
			Map<String, String> map = new HashMap<String, String>();
			String[] relates = parameters.split("===") != null ? parameters
					.split("===") : null;
			int id_ = 0;
			if (relates != null && relates.length>0) {
				for (int i = 0; i < relates.length; i++) {
					String relate = relates[i];
					for (int j = 0; j < relate.split(",").length; j++) {
						if (relate.split(",")[j].split("==")[0].equals("id"))
							id_ = Integer.parseInt(relate.split(",")[j]
									.split("==")[1]);
						else
							map.put(relate.split(",")[j].split("==")[0], relate
									.split(",")[j].split("==")[1]);
					}
					tagsDao.updateProduceContent(map, id_, produce_table);
				}
			}
		}

		// 添加增加的相关数据
		if (parameters_ != null && !parameters_.equals("")) {
			for (int i = 0; i < list_tags.size(); i++) {
				if (list_tags.get(i).getDisplay_type().equals("相关字段")
						&& list_tags.get(i).getRelateIsShowComplete() == 1) {
					// 插入到过程表
					if (tagsDao.getProduceTableByTable(tablename) != null
							&& !tagsDao.getProduceTableByTable(tablename)
									.equals("")) {
						list_tags = tagsDao
								.select_designe_field_by_tablename(produce_table);

						String jisuan_type = "";
						for (int m = 0; m < list_tags.size(); m++) {
							String temp = tagsDao.checkColumnIsBiaojianjisuan(
									list_tags.get(m).getTable_name(), list_tags
											.get(m).getColumn_name());
							if (temp != null && !temp.equals("")) {
								String jisuan_type_ = temp.split(";")[1];// TB_MM_233,TB_MM_235==add
								String[] jisuans = jisuan_type_.split("==");
								String o = jisuans[1];
								if (jisuans != null) {
									jisuans = jisuans[0].split(",");
									for (int k = 0; k < jisuans.length; k++) {
										if (jisuans[k].indexOf(tablename) >= 0) {
											jisuan_type = o;
										}
									}
								}
							}

						}
						if (parameters_.indexOf("===") >= 0) {
							for (int j = 0; j < parameters_.split("===").length; j++) {
								tagsDao.addToProduce(
												jisuan_type,
												list_tags,
												result_table + ","
														+ produce_table,
												parameters_.split("===")[j],
												getSessionIntValue(ElConstants.SESSION_USERID),
												id);
							}
						} else {
							tagsDao.addToProduce(
											jisuan_type,
											list_tags,
											result_table + "," + produce_table,
											parameters_,
											getSessionIntValue(ElConstants.SESSION_USERID),
											id);
						}
					}
				}
			}
		}

		if (produce_table != null && !produce_table.equals("")) {// 过程表
			// 过程表中数据审核通过
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			Map<String, String> hm_;
			Map<String, String> hm__;
			String column_in_result_table_ = "";
			String columns_in_produce_table_ = "";
			double f = 0.0;

			List<Tags> list_tags_relate = tagsDao
					.select_designe_field_by_tablename(produce_table);
			String sqlAppend = " and t.moduleid =  '" + tablename
					+ "' and t.danjuid = " + id;
			List<Map<String,String>> list_designe_relate = tagsDao.select_my_tableinfo_by_userid_order(
					sqlAppend, 1, list_tags_relate, produce_table,
					new HashMap<String, String>(), userid, null, getPageNow(),
					getPageSize());

			for (int i = 0; i < list_designe_relate.size(); i++) {
				hm_ = new HashMap<String, String>();
				hm__ = new HashMap<String, String>();

				for (int j = 0; j < list_tags_relate.size(); j++) {
					// 判断该字段是否是表间计算的字段
					String column_jisuantype = tagsDao
							.checkColumnIsBiaojianjisuan(produce_table,
									list_tags_relate.get(j).getColumn_name());

					if (column_jisuantype != null
							&& !column_jisuantype.equals("")) {

						String column_in_result_table = tagsDao
								.getColumn_name_by_id(Integer
										.parseInt(column_jisuantype.split(";")[0]));// 结果表中表间计算字段
						column_in_result_table_ = column_in_result_table;
						String columns_in_produce_table = tagsDao
								.getColumnNameFromResultAndProduceTable(
										result_table, produce_table,
										column_in_result_table);
						columns_in_produce_table_ = columns_in_produce_table;

						String jisuan_type = column_jisuantype;
						if (jisuan_type != null && !jisuan_type.equals("")) {
							String jisuan_type_ = column_jisuantype.split(";")[1];
							String[] jisuans = jisuan_type_.split("==");
							String o = jisuans[1];
							if (jisuans != null) {
								jisuans = jisuans[0].split(",");
								for (int k = 0; k < jisuans.length; k++) {
									if (jisuans[k].indexOf(tablename) >= 0) {
										jisuan_type = o;
									}
								}
							}
						}

						if (tagsDao.checkProduceTableShenheStatus(
								produce_table, Integer
										.parseInt(list_designe_relate.get(i)
												.get("id"))) == 1) {
							if (jisuan_type.equals("add")) {
								f = Double
										.parseDouble(list_designe_relate.get(i)
												.get(
														column_jisuantype
																.split(";")[2]));
							} else if (jisuan_type.equals("minus")) {
								f = Double
										.parseDouble(list_designe_relate.get(i)
												.get(
														column_jisuantype
																.split(";")[2]));
							}
						}

						hm_.put(columns_in_produce_table, String.valueOf(f));
						hm__.put(column_in_result_table_, String.valueOf(f));
					}
				}
				// 更新过程表中值
				tagsDao.updateProduceContent(hm_, Integer
						.parseInt(list_designe_relate.get(i).get("id")),
						produce_table);
			}
			// 更新结果表中值
			// 相关的ids
			// 普通表
			for (int m = 0; m < list_tags.size(); m++) {
				if (list_tags.get(m).getDisplay_type().equals("相关字段")
						&& list_tags.get(m).getRelateIsShowComplete() == 1) {
					String ids_relate = tagsDao.getRelateIds(list_tags.get(m)
							.getTable_name(),
							list_tags.get(m).getColumn_name(), id);
					String[] array = new String[1];
					if (ids_relate != null && !ids_relate.equals("")) {
						array = new String[ids_relate.length()];
						array = ids_relate.split(",");
					}
					if (array.length > 0) {
						for (int j = 0; j < array.length; j++) {
							tagsDao.updateResultContent(
											tagsDao.getResultTableByProduceTable(produce_table),
											produce_table,
											column_in_result_table_,
											columns_in_produce_table_, 
											Integer.parseInt(array[j]), id);// 更新结果表中表间计算字段
						}
					}
				}
			}

		}
		
		RemindUtil.load();

		return "updateContactTagsSuccess";
	}

	public String searchContactTags() throws ElException {
		//审核级别最小和最大
		ca_small = tagsDao.get_audit_by_tablename(tablename,1);
		ca_big = tagsDao.get_audit_by_tablename(tablename,2);
		ca = tagsDao.get_audit_by_tablename_userid(tablename, getSessionIntValue(ElConstants.SESSION_USERID));
		
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());
		
		actionName = "searchContactTags";
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

		Map<String, String> hm = new HashMap<String, String>();

		// list_tags = tagsDao
		// .select_designe_field_by_tablename("tb_clientlinkman_tags");
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		Map<String,Object> list_tags_and_hm = TagsUtil.addSearch(list_tags, getRequest());
		list_tags = (List<Tags>)list_tags_and_hm.get("list_tags");
		hm = (Map<String,String>)list_tags_and_hm.get("hm");

		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		realname = tagsDao.get_eluser_realname_by_id(userid);

		// 查找sum
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getSum_display() == 1) {
				if (list_tags.get(i).getColumn_type().equals("number")) {
					list_tags.get(i).setSum_i(
							(int) tagsDao.select_sum(null, list_tags,
									tablename, list_tags.get(i)
											.getColumn_name(), hm, department));
				} else if (list_tags.get(i).getColumn_type().equals("float")) {
					list_tags.get(i).setSum_f(
							tagsDao.select_sum(null, list_tags, tablename,
									list_tags.get(i).getColumn_name(), hm,
									department));
				}
			}
		}

		// list_designe = tagsDao.select_my_tableinfo_by_dep(list_tags,
		// getTablename(list_tags.get(0).getColumn_name()), hm,
		// department, getPageNow(), getPageSize());
		// count = tagsDao.select_my_tableinfo_by_dep_count(list_tags, hm,
		// getTablename(list_tags.get(0).getColumn_name()), department);

		String order = "";
		if (tags != null) {
			if (tags.getOrdercolumn() != null
					&& !tags.getOrdercolumn().equals("")) {
				order = " order by ";
				order += tags.getOrdercolumn() + " ";
				order += tags.getOrdersc();
			}
		}

		//初始化更新时间进度
		TagsUtil.updateTimeJindu(tablename,tagsDao);

		// 初始化更新业务进度
		TagsUtil.updateYewuJindu(tablename,tagsDao,list_tags);

		list_designe = tagsDao.select_my_tableinfo_by_dep(list_tags, tablename,
				hm, department, order, getPageNow(), getPageSize());
		count = tagsDao.select_my_tableinfo_by_dep_count(list_tags, hm,
				tablename, department);

		// 如果有表内计算，更新表内计算的字段值
		// 如果是过程表
		if (tagsDao.checkTable(tablename) == 2) {
			String result_table = tagsDao
					.getResultTableByProduceTable(tablename);
			if (!result_table.equals("")) {
				tagsDao.updateBiaoneijisuanContent_(result_table, tablename,
						list_tags, list_designe);
			}
			// 业务表或者结果表
		} else if (tagsDao.checkTable(tablename) == 1
				|| tagsDao.checkTable(tablename) == 3) {
			tagsDao.updateBiaoneijisuanContent(tablename, list_tags,
					list_designe);
		}
		list_designe = tagsDao.select_my_tableinfo_by_dep(list_tags, tablename,
				hm, department, order, getPageNow(), getPageSize());

		return "searchContactTagsSuccess";
	}

	public String finalsearchContactTags() throws ElException {
		
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());
		
		
		actionName = "finalsearchContactTags";
		// 只是查看操作还是......
		table_type = tagsDao.checkTable(tablename);

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
		
		

		Map<String, String> hm = new HashMap<String, String>();

		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		Map<String,Object> list_tags_and_hm = TagsUtil.addSearch(list_tags, getRequest());
		list_tags = (List<Tags>)list_tags_and_hm.get("list_tags");
		hm = (Map<String,String>)list_tags_and_hm.get("hm");
		
		if (moduleStatus != null) {
			if (moduleStatus.getModuleid() != null
					&& !moduleStatus.getModuleid().equals(""))
				hm.put("varchar2(500)==moduleid", moduleStatus
						.getModuleid());
			if (moduleStatus.getStatus() != null
					&& !moduleStatus.getStatus().equals("")) {
				hm.put("status", moduleStatus.getStatus());
			}
		}

		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		realname = tagsDao.get_eluser_realname_by_id(userid);

		// 查找sum
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getSum_display() == 1) {
				if (list_tags.get(i).getColumn_type().equals("number")) {
					list_tags.get(i).setSum_i(
							(int) tagsDao.select_sum(null, list_tags,
									tablename, list_tags.get(i)
											.getColumn_name(), hm, department));
				} else if (list_tags.get(i).getColumn_type().equals("float")) {
					list_tags.get(i).setSum_f(
							tagsDao.select_sum(null, list_tags, tablename,
									list_tags.get(i).getColumn_name(), hm,
									department));
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

		// 是否导出excel
		if (tags != null) {
			if (tags.getDownloadcontrol() != null
					&& !tags.getDownloadcontrol().equals("")) {
				list_designe = tagsDao.select_my_tableinfo_by_dep(list_tags,
						tablename, hm, department, order);
				// 写入 excel
				copyDataToExcel(list_designe, list_tags);
			}
		}

		//初始化更新时间进度
		TagsUtil.updateTimeJindu(tablename,tagsDao);

		// 初始化更新业务进度
		TagsUtil.updateYewuJindu(tablename,tagsDao,list_tags);

		//
		list_designe = tagsDao.select_my_tableinfo_by_dep(list_tags, tablename,
				hm, department, order, getPageNow(), getPageSize());
		count = tagsDao.select_my_tableinfo_by_dep_count(list_tags, hm,
				tablename, department);

		// 如果有表内计算，更新表内计算的字段值
		// 如果是过程表
		if (table_type == 2) {
			// 搜索条件
			moduleids = tagsDao.getModuleidsByProduceTable(tablename);
			String result_table = tagsDao
					.getResultTableByProduceTable(tablename);
			if (!result_table.equals("")) {
				tagsDao.updateBiaoneijisuanContent_(result_table, tablename,
						list_tags, list_designe);
			}
			// 业务表或者结果表
		} else if (tagsDao.checkTable(tablename) == 1
				|| tagsDao.checkTable(tablename) == 3) {
			tagsDao.updateBiaoneijisuanContent(tablename, list_tags,
					list_designe);
		}

		list_designe = tagsDao.select_my_tableinfo_by_dep(list_tags, tablename,
				hm, department, order, getPageNow(), getPageSize());

		return "finalsearchContactTagsSuccess";
	}
	
	
	/**
	 * 客户分析一览的查看
	 * @return
	 * @throws ElException
	 */
	public String myContactTags_() throws ElException {
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

		Map<String, String> hm = new HashMap<String, String>();

		list_tags = tagsDao.select_designe_field_by_tablename(tablename);


		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		realname = tagsDao.get_eluser_realname_by_id(userid);

		// 查找sum
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getSum_display() == 1) {
				if (list_tags.get(i).getColumn_type().equals("number")) {
					list_tags.get(i).setSum_i(
							(int) tagsDao.select_sum(null, list_tags,
									tablename, list_tags.get(i)
											.getColumn_name(), hm, department));
				} else if (list_tags.get(i).getColumn_type().equals("float")) {
					list_tags.get(i).setSum_f(
							tagsDao.select_sum(null, list_tags, tablename,
									list_tags.get(i).getColumn_name(), hm,
									department));
				}
			}
		}


		//初始化更新时间进度
		TagsUtil.updateTimeJindu(tablename,tagsDao);

		// 初始化更新业务进度
		TagsUtil.updateYewuJindu(tablename,tagsDao,list_tags);

		//
		list_designe = tagsDao.select_designe_by_tablename(id,list_tags, tablename,
				getPageNow(), getPageSize());
		count = tagsDao.select_designe_count_by_tablename(id,list_tags, tablename);

		// 如果有表内计算，更新表内计算的字段值
		// 如果是过程表
		if (table_type == 2) {
			// 搜索条件
			moduleids = tagsDao.getModuleidsByProduceTable(tablename);
			String result_table = tagsDao
					.getResultTableByProduceTable(tablename);
			if (!result_table.equals("")) {
				tagsDao.updateBiaoneijisuanContent_(result_table, tablename,
						list_tags, list_designe);
			}
			// 业务表或者结果表
		} else if (tagsDao.checkTable(tablename) == 1
				|| tagsDao.checkTable(tablename) == 3) {
			tagsDao.updateBiaoneijisuanContent(tablename, list_tags,
					list_designe);
		}

		list_designe = tagsDao.select_designe_by_tablename(id,list_tags, tablename,
				getPageNow(), getPageSize());

		return "myContactTags_";
	}
	
	/**
	 * 生产任务相关\委外加工相关详情查看
	 * @return
	 * @throws ElException
	 */
	public String myContactTags1_() throws ElException {
		
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

		Map<String, String> hm = new HashMap<String, String>();

		list_tags = tagsDao.select_designe_field_by_tablename(tablename);


		int userid = getSessionIntValue(ElConstants.SESSION_USERID);
		realname = tagsDao.get_eluser_realname_by_id(userid);

		// 查找sum
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getSum_display() == 1) {
				if (list_tags.get(i).getColumn_type().equals("number")) {
					list_tags.get(i).setSum_i(
							(int) tagsDao.select_sum(null, list_tags,
									tablename, list_tags.get(i)
											.getColumn_name(), hm, department));
				} else if (list_tags.get(i).getColumn_type().equals("float")) {
					list_tags.get(i).setSum_f(
							tagsDao.select_sum(null, list_tags, tablename,
									list_tags.get(i).getColumn_name(), hm,
									department));
				}
			}
		}


		//初始化更新时间进度
		TagsUtil.updateTimeJindu(tablename,tagsDao);

		// 初始化更新业务进度
		TagsUtil.updateYewuJindu(tablename,tagsDao,list_tags);
		
		
		list_designe = tagsDao.select_my_tableinfo_by_dep_(list_tags, tablename,
				hm, department, null, getPageNow(), getPageSize());
		count = tagsDao.select_my_tableinfo_by_dep_count_(list_tags, hm,
				tablename, department);

		// 如果有表内计算，更新表内计算的字段值
		// 如果是过程表
		if (table_type == 2) {
			// 搜索条件
			moduleids = tagsDao.getModuleidsByProduceTable(tablename);
			String result_table = tagsDao
					.getResultTableByProduceTable(tablename);
			if (!result_table.equals("")) {
				tagsDao.updateBiaoneijisuanContent_(result_table, tablename,
						list_tags, list_designe);
			}
			// 业务表或者结果表
		} else if (tagsDao.checkTable(tablename) == 1
				|| tagsDao.checkTable(tablename) == 3) {
			tagsDao.updateBiaoneijisuanContent(tablename, list_tags,
					list_designe);
		}

		list_designe = tagsDao.select_my_tableinfo_by_dep_(list_tags, tablename,
				hm, department, null, getPageNow(), getPageSize());

		return "myContactTags1_";
	}

	/////////////////////////////////////////
	private int is_enabled;//是否启用多级审核
	private String auditOrder;//审核级别
	private String audits;//json字符串
	private List<CustomAudit> cas;
	private CustomAudit ca_small;//最小审核级别
	private CustomAudit ca_big;//最大审核级别
	private CustomAudit ca;//当前审核级别
	private int custype;//查看审核流程
	private AuditMark auditMark;//备注
	private List<AuditMark> ams;//备注列表
	
	public int getIs_enabled() {
		return is_enabled;
	}
	public void setIs_enabled(int is_enabled) {
		this.is_enabled = is_enabled;
	}
	public String getAuditOrder() {
		return auditOrder;
	}
	public void setAuditOrder(String auditOrder) {
		this.auditOrder = auditOrder;
	}
	public List<AuditMark> getAms() {
		return ams;
	}
	public void setAms(List<AuditMark> ams) {
		this.ams = ams;
	}
	public AuditMark getAuditMark() {
		return auditMark;
	}
	public void setAuditMark(AuditMark auditMark) {
		this.auditMark = auditMark;
	}
	public List<CustomAudit> getCas() {
		return cas;
	}
	public void setCas(List<CustomAudit> cas) {
		this.cas = cas;
	}
	public int getCustype() {
		return custype;
	}
	public void setCustype(int custype) {
		this.custype = custype;
	}
	public CustomAudit getCa() {
		return ca;
	}
	public void setCa(CustomAudit ca) {
		this.ca = ca;
	}
	public CustomAudit getCa_small() {
		return ca_small;
	}
	public void setCa_small(CustomAudit ca_small) {
		this.ca_small = ca_small;
	}
	public CustomAudit getCa_big() {
		return ca_big;
	}
	public void setCa_big(CustomAudit ca_big) {
		this.ca_big = ca_big;
	}
	public String getAudits() {
		return audits;
	}
	public void setAudits(String audits) {
		this.audits = audits;
	}

	public String fieldAuditMark() throws ElException{
		return "fieldAuditMark";
	}
	
	public String select_audit_mark_by_entityid() throws ElException{
		//id,tablename
		ams = tagsDao.select_audit_mark_by_entityid(tablename,id,getPageNow(),getPageSize());
		count = tagsDao.select_audit_mark_size_by_entityid(tablename,id,getPageNow(),getPageSize());
		return "select_audit_mark_by_entityid";
	}
	
	
	
	// 自定义审核管理设置
	public String customAuditManageInit() throws ElException, UnsupportedEncodingException {
		actionName = "customAuditManageInit";
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		audits = TagsUtil.ToGson(tagsDao.get_audits_by_tablename(tablename));
		
		if(custype == 1){
			return "customAuditView";
		}
		return "customAuditManageInit";
	}
	//自定义审核管理
	@SuppressWarnings("unchecked")
	public String customAuditManage() throws ElException, UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		Enumeration params = request.getParameterNames();
		Map<String,Object> map = new HashMap<String,Object>();
		while (params.hasMoreElements()){
			String paramName = (String) params.nextElement();
			String paramvalue = request.getParameter(paramName);
			if(paramName.equals("tablename")){
				tablename = paramvalue;
			}
			if(paramvalue != null && !paramvalue.equals("")){
				map.put(paramName, paramvalue);
			}
			
		}
		System.out.println(map);
		String value = "";
		
		//插入自定义审核信息之前，删除存在的信息
		tagsDao.delete_audit_by_tablename(tablename);
//		String audit_userid = "";
//		String[] audit_userid_array = null;
		for(String key:map.keySet()){
			if(key.indexOf("sub_title_")>=0 || key.indexOf("sub_userid_")>=0 
					|| key.indexOf("sub_bz_")>=0 || key.indexOf("sub_level_")>=0){
				 Pattern pattern = Pattern.compile("\\d+");  
				 Matcher matcher = pattern.matcher(key);  
				 while (matcher.find()) {  
					 if(value.equals("") || (!value.equals("") && value.indexOf(matcher.group(0))<0)){
						 value +=  matcher.group(0)+",";  
						//插入tb_audit自定义审核表
//						 if((String)map.get("sub_userid_"+matcher.group(0)) != null &&
//								 !((String)map.get("sub_userid_"+matcher.group(0))).equals("")){
//							 audit_userid_array = ((String)map.get("sub_userid_"+matcher.group(0))).split(",");
//							 for(int i=0;i<audit_userid_array.length;i++){
//								 audit_userid = audit_userid_array[i];
//								 tagsDao.insert_tb_auditByTablename(tablename,(String)map.get("sub_level_"+matcher.group(0))
//										 ,(String)map.get("sub_title_"+matcher.group(0)),audit_userid
//										 ,(String)map.get("sub_bz_"+matcher.group(0))==null?"":(String)map.get("sub_bz_"+matcher.group(0)));
//							 }
						 tagsDao.insert_tb_auditByTablename(tablename,(String)map.get("sub_level_"+matcher.group(0))
								 ,(String)map.get("sub_title_"+matcher.group(0)),(String)map.get("sub_userid_"+matcher.group(0))
								 ,(String)map.get("sub_bz_"+matcher.group(0))==null?"":(String)map.get("sub_bz_"+matcher.group(0)));
//						 }
					 }
				 } 
			}
		}
		//修改模块管理表是否开启审核流程属性
		moduleManageDao.update_module_by_tablename(tablename,is_enabled);
		setElmessage(URLEncoder.encode(URLEncoder.encode("设置成功!!!", "UTF-8"), "UTF-8"));
		return "customAuditManage";
	}
	
	//自定义审核列表
	//customAuditListContactTags    tablename=KHDA
	public String customAuditListContactTags() throws ElException{
		actionName = "customAuditListContactTags";
		cas = tagsDao.get_audits_by_tablename(tablename);
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());
		if(cas == null || cas.size() == 0){
			this.setElmessage("对不起，您还未设置自定义审核流程，请先设置!!!");
			return "error";
		}
//		String uid = tagsDao.get_audit_by_auditOrderAndTablename(auditOrder,tablename);
		//验证当前用户是否有审核权限
		if(getSessionIntValue(ElConstants.SESSION_ROLE) != 1){
			if(!tagsDao.checkUserInAudit(tablename,getSessionIntValue(ElConstants.SESSION_USERID),null)){
				//无审核权限
				this.setElmessage("对不起，您无审核权限，请联系管理员");
				return "error";
			}
		}
		
		//审核级别最小和最大
		ca_small = tagsDao.get_audit_by_tablename(tablename,1);
		ca_big = tagsDao.get_audit_by_tablename(tablename,2);
		ca = tagsDao.get_audit_by_tablename_userid(tablename, getSessionIntValue(ElConstants.SESSION_USERID));
		
		
		// 只是查看操作还是......
		table_type = tagsDao.checkTable(tablename);

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
		
		

		Map<String, String> hm = new HashMap<String, String>();

		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		//为何要加
//		if (moduleStatus != null) {
//			if (moduleStatus.getModuleid() != null
//					&& !moduleStatus.getModuleid().equals(""))
//				hm.put("varchar2(500)==moduleid", moduleStatus
//						.getModuleid());
//			if (moduleStatus.getStatus() != null
//					&& !moduleStatus.getStatus().equals("")) {
//				hm.put("status", moduleStatus.getStatus());
//			}
//		}
		Map<String,Object> list_tags_and_hm = TagsUtil.addSearch(list_tags, getRequest());
		list_tags = (List<Tags>)list_tags_and_hm.get("list_tags");
		hm = (Map<String,String>)list_tags_and_hm.get("hm");

		realname = tagsDao.get_eluser_realname_by_id(getSessionIntValue(ElConstants.SESSION_USERID));

		// 查找sum
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getSum_display() == 1) {
				if (list_tags.get(i).getColumn_type().equals("number")) {
					list_tags.get(i).setSum_i(
							(int) tagsDao.select_sum(null, list_tags,
									tablename, list_tags.get(i)
											.getColumn_name(), hm, department));
				} else if (list_tags.get(i).getColumn_type().equals("float")) {
					list_tags.get(i).setSum_f(
							tagsDao.select_sum(null, list_tags, tablename,
									list_tags.get(i).getColumn_name(), hm,
									department));
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

		//初始化更新时间进度
		TagsUtil.updateTimeJindu(tablename,tagsDao);

		// 初始化更新业务进度
		TagsUtil.updateYewuJindu(tablename,tagsDao,list_tags);
		

		//
		list_designe = tagsDao.select_my_audits_by_dep(getSessionIntValue(ElConstants.SESSION_ROLE),
				ca,getSessionIntValue(ElConstants.SESSION_USERID),
				list_tags, tablename,
				hm, department, order, getPageNow(), getPageSize());
		count = tagsDao.select_my_audits_by_dep_count(getSessionIntValue(ElConstants.SESSION_ROLE),
				ca,getSessionIntValue(ElConstants.SESSION_USERID),
				list_tags, hm,
				tablename, department);

		// 如果有表内计算，更新表内计算的字段值
		// 如果是过程表
		if (table_type == 2) {
			// 搜索条件
			moduleids = tagsDao.getModuleidsByProduceTable(tablename);
			String result_table = tagsDao
					.getResultTableByProduceTable(tablename);
			if (!result_table.equals("")) {
				tagsDao.updateBiaoneijisuanContent_(result_table, tablename,
						list_tags, list_designe);
			}
			// 业务表或者结果表
		} else if (tagsDao.checkTable(tablename) == 1
				|| tagsDao.checkTable(tablename) == 3) {
			tagsDao.updateBiaoneijisuanContent(tablename, list_tags,
					list_designe);
		}

		list_designe = tagsDao.select_my_audits_by_dep(getSessionIntValue(ElConstants.SESSION_ROLE),
				ca,getSessionIntValue(ElConstants.SESSION_USERID),
				list_tags, tablename,
				hm, department, order, getPageNow(), getPageSize());
		return "finalsearchContactTagsSuccess";
	}
	
	/////////////////////////////////////////

	public String downloadStuff() throws ElException {

		try {
			String contStr = (String) getRequest().getParameter("down");
			String cont = new String(contStr.getBytes("iso-8859-1"), "UTF-8");
			String str[] = cont.split("==");

			// String
			// contactstuff = clientDao.get_contactstuff_by_id(contactstuff
			// .getId());
			filename = str[1];
			// filename = filename.substring(filename.indexOf("elstuffs"));
			// 获取文件在服务器的具体路径
			String path = ServletActionContext.getServletContext().getRealPath(
					filename);
			String fileext = filename.substring(filename.lastIndexOf("."));
			String filename1 = str[0] + fileext;
			System.out.println("filename1" + filename1);
			filename = new String(filename1.getBytes(), "ISO8859-1");
			System.out.println("filename" + filename1);
			try {
				System.out.println(path);
				inputStream = new FileInputStream(path);

			} catch (Exception e) {
				// logger.error("文档下载失败", e);
				throw new ElException("下载资料出错", e);
			}
		} catch (Exception e) {
			// logger.error("文档下载失败", e);
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		return "downloadStuffSuccess";
	}

	public String downloadExcel() throws ElException {
		System.out.println(tablename);
		try {
			String contStr = (String) getRequest().getParameter("down");
			String cont = new String(contStr.getBytes("iso-8859-1"), "UTF-8");

			String path = "d:\\source\\" + tablename + "\\" + tablename
					+ ".xls";
			File file = new File(path);
			System.out.println(file.getName() + ";;" + file.getPath());
			String fileext = "xls";
			String filename1 = cont + "." + fileext;
			System.out.println("filename1" + filename1);
			filename = new String(filename1.getBytes(), "ISO8859-1");
			System.out.println("filename" + filename1);
			try {
				System.out.println(path);
				inputStream = new FileInputStream(file);
				System.out.println(inputStream);

			} catch (Exception e) {
				// logger.error("文档下载失败", e);
				throw new ElException("下载资料出错", e);
			}
		} catch (Exception e) {
			// logger.error("文档下载失败", e);
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		return "downloadExcelSuccess";
	}

	public String designeContactTagsInit() throws ElException, UnsupportedEncodingException {
		if(elmessage != null && !elmessage.equals("")){
			String str = URLDecoder.decode(elmessage,"UTF-8");
			this.setElmessage(str);
		}
		/*
		 * list_tags =
		 * tagsDao.select_designe_field_by_tablename("tb_clientlinkman_tags");
		 */
		tablename = (String) getRequest().getParameter("tablename");
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		// 获取moduleManage
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		
		currentUser = tagsDao.getCurrentUser(tablename);
		return "designeContactTagsInitSuccess";
	}

	public String deleteDesigneTags() throws ElException, UnsupportedEncodingException {
		List<String> fieldValues = tagsDao.selectColumnValues(tablename,
				columnName);
		for (String str : fieldValues) {
			if (str != null) {
				this.setElmessage("您要删除的列已经有数据，不能删除!!!") ;
				return "error";
			} else {
				continue;
			}
		}
		tagsDao.deleteDesigneField(tablename, columnName, id);
		setElmessage(URLEncoder.encode(URLEncoder.encode("删除成功!!!", "UTF-8"), "UTF-8"));
		return "deleteDesigneTags_success";
	}

	public String designeContactTags() throws ElException {
		tablename = getRequest().getParameter("tablename");

		List<Tags> list = new ArrayList<Tags>();
		Tags tag = null;
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		for (int j = 0; j < list_tags.size(); j++) {

			tag = new Tags();
			tag.setId(list_tags.get(j).getId());
			tag.setCannot_modify(list_tags.get(j).getCannot_modify());
			String[] sn = getRequest().getParameterValues(
					"sn_" + list_tags.get(j).getId());
			tag.setSn(Integer.valueOf(sn[0]));
			if (list_tags.get(j).getCannot_modify() != 1) {
				String[] str = getRequest().getParameterValues(
						"display_" + list_tags.get(j).getId());
				if (str != null) {
					for (int i = 0; i < str.length; i++) {

						if (str[i].equals("add_display"))
							tag.setAdd_display(1);
						else if (str[i].equals("update_display"))
							tag.setUpdate_display(1);
						else if (str[i].equals("view_display"))
							tag.setView_display(1);
						else if (str[i].equals("list_display"))
							tag.setList_display(1);
						else if (str[i].equals("mutilsearch_display"))// 组合搜索
							tag.setMutilsearch_display(1);
						else if (str[i].equals("departsearch_display"))// 部门查询
							tag.setDepartsearch_display(1);
						else if (str[i].equals("required"))// 部门查询
							tag.setRequired(1);
					}
				} else {
					tag.setAdd_display(0);
					tag.setUpdate_display(0);
					tag.setView_display(0);
					tag.setList_display(0);
					tag.setMutilsearch_display(0);
					tag.setDepartsearch_display(0);
				}
			}
			list.add(tag);
		}
		list.size();
		tagsDao.manage_designe_field(list);
		
		if(currentUser == null){
			currentUser = new CurrentUser();
			currentUser.setTablename(tablename);
			currentUser.setUser_add(Integer.parseInt(getRequest().getParameter("user_add")== null?"0":getRequest().getParameter("user_add")));
			currentUser.setUser_update(Integer.parseInt(getRequest().getParameter("user_update")==null?"0":getRequest().getParameter("user_update")));
			currentUser.setUser_view(Integer.parseInt(getRequest().getParameter("user_view")==null?"0":getRequest().getParameter("user_view")));
			tagsDao.updateTb_userByTablename(currentUser);
		}
		return "designeContactTagsSuccess";
	}

	
	public String addShenheren() throws ElException{
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
		int nid = department == null ? depTree.getId()
				: (department.getId() == 0 ? 1 : department.getId());
		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());

		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);
		int is_judge_for_user = 1;
		list_eluser = tagsDao.select_eluser_by_dep(is_judge_for_user, "ELUSER",
				eluser, department, getPageNow(), getPageSize(), nid);
		count = tagsDao.select_eluser_by_dep_count(is_judge_for_user, "ELUSER",
				eluser, department, getPageNow(), getPageSize(), nid);
		return "getRelateEluserInfoSuccess";
	}

	public String getRelateEluserInfo() throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		int is_judge_for_user = 0;
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				is_judge_for_user = list_tags.get(i).getIs_judge_for_user();
			}
		}
		getRequest().setAttribute("is_judge_for_user", is_judge_for_user);

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

		roles = roleDao.listRoles(getSessionIntValue(ElConstants.SESSION_ROLE),
				getSessionIntValue(ElConstants.SESSION_USERID));
		jingzhongs = userDao.getBaseDatatByTypeid(1);
		zhiwus = userDao.getBaseDatatByTypeid(2);

		list_eluser = tagsDao.select_eluser_by_dep(is_judge_for_user, "ELUSER",
				eluser, department, getPageNow(), getPageSize(), nid);
		count = tagsDao.select_eluser_by_dep_count(is_judge_for_user, "ELUSER",
				eluser, department, getPageNow(), getPageSize(), nid);
		return "getRelateEluserInfoSuccess";
	}

	/*
	 * //---------初审
	 */

	public String verifypassContactTags() throws ElException {
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				auditMark.setEntityid(id);
				clientDao.verify_pass_contact_by_id(tablename,id,6,getSessionIntValue(ElConstants.SESSION_USERID),
						userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)).getDepartment().getId(),
						auditMark,auditOrder);
			}
		}
		return "verifypassContactTagsSuccess";
	}

	public String verifynopassContactTags() throws ElException {
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				auditMark.setEntityid(id);
				clientDao.verify_no_pass_contact_by_id(tablename,id,7,getSessionIntValue(ElConstants.SESSION_USERID),
						userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)).getDepartment().getId(),
						auditMark,auditOrder);
			}
		}
		return "verifynopassContactTagsSuccess";
	}

	// 提交初审-状态由已创建 转为 初审等待中
	public String commitverifypassContactTags() throws ElException {
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				tagsDao.commit_verity_pass_tags(tablename, id);
				
				String produce_table = tagsDao.getProduceTableByTable(tablename);
				// 终审通过，将该表的数量在结果表中修改,遍历过程表该字段求和更新结果表字段值
				// 验证表类型
				if (produce_table != null && !produce_table.equals("")) {// 过程表
					Map<String, String> hm = new HashMap<String, String>();
					List<Tags> list_tags_relate = tagsDao
					.select_designe_field_by_tablename(produce_table);
					String sqlAppend = " and t.moduleid =  '" + tablename
							+ "' and t.danjuid = " + id;
					List<Map<String,String>> list_designe_relate = tagsDao.select_my_tableinfo_by_userid_order(
							sqlAppend, 1, list_tags_relate, produce_table, hm, userid,
							null, getPageNow(), getPageSize());

					for (int j = 0; j < list_designe_relate.size(); j++) {
						// 更新过程表中值,将shenhestatus=2，即初审等待中
						tagsDao.updateProduceContent_(null, Integer
								.parseInt(list_designe_relate.get(j).get("id")),
								produce_table);

					}
				}
			}
		}

		return "commitverifypassContactTagsSuccess";
	}
	
	public String check_auditOrderIsExist() throws ElException{
		boolean result = false;
		result = tagsDao.check_auditOrderIsExist(tablename, auditOrder);
		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String deleteauditOrderUserId() throws ElException{
		tagsDao.deleteauditOrderUserId(tablename, auditOrder,userid);

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

	//自定义审核
	public String verifypassTags() throws ElException {
		ca_small = tagsDao.get_audit_by_tablename(tablename,1);
		ca_big = tagsDao.get_audit_by_tablename(tablename,2);
		
		//auditOrder_审核级别
		String auditOrder_ = "";
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1 || //超管或者审核级别最大
				ca_big.getAuditOrder().equals(auditOrder)){
			tagsDao.verify_pass_tags(tablename, id,9,
					getSessionIntValue(ElConstants.SESSION_USERID),
					userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)).getDepartment().getId(),
					auditMark,ca_big.getAuditName());
		}else{
			int status = tagsDao.get_status_by_tablename_id(tablename,id);
			if(status == 0){
				//已创建
				this.setElmessage("对不起，该记录还未提交审核，无法审核!!!");
				return "error";
			}
//			else if(status == 5){
//				//初审等待中
//				this.setElmessage("对不起，该记录为初审等待中，无法审核!!!");
//				return "error";
//			}else if(status == 7){
//				//初审不通过
//				this.setElmessage("对不起，该记录为初审不通过，无法审核!!!");
//				return "error";
//			}
			else if(status == 9){
				//终审通过
				this.setElmessage("对不起，该记录为终审通过，无法审核!!!");
				return "error";
			}else if(status == 10){
				//终审不通过
				this.setElmessage("对不起，该记录为终审不通过，无法审核!!!");
				return "error";
			}
			//判断当前用户是否有该审核权限
			if(!tagsDao.checkUserInAudit(tablename,getSessionIntValue(ElConstants.SESSION_USERID),auditOrder)){
				//无审核权限
				this.setElmessage("对不起，您无审核权限，请联系管理员");
				return "error";
			}
			//验证审核级别比当前审核级别高的情况下，不能进行审核
			//比如数据状态为二级审核通过，当前审核级别为一级审核通过
			if(status>Integer.parseInt(auditOrder) * 2 + 10 + 1){
				this.setElmessage("对不起，该数据审核状态高于当前审核级别，不能进行审核!!!");
				return "error";
			}
			//判断上一个审核级别是否已经审核
			CustomAudit ca = tagsDao.get_audit_by_tablename_userid(tablename, getSessionIntValue(ElConstants.SESSION_USERID));
			auditOrder_ = ca.getAuditOrder();
			if(ca_small.getAuditOrder() != null && auditOrder_ != null 
					&&!ca_small.getAuditOrder().equals(auditOrder_)){//当前审核级别不是最小的
				//判断数据状态
				//上一级审核不通过或未审核
				if(status== Integer.parseInt(auditOrder_) * 2 + 10 - 1){
					this.setElmessage("对不起，上一级审核不通过或者还未审核!!!");
					return "error";
				}
			}
			tagsDao.verify_pass_tags(tablename, id,Integer.parseInt(auditOrder_)*2+10,
					getSessionIntValue(ElConstants.SESSION_USERID),
					userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)).getDepartment().getId(),
					auditMark,ca.getAuditName());
			
			
		}
		return "verifypassTagsSuccess";
	}

	
	public String verifynopassTags() throws ElException {
		ca_small = tagsDao.get_audit_by_tablename(tablename,1);
		ca_big = tagsDao.get_audit_by_tablename(tablename,2);
		
		
		String auditOrder_ = "";
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1 || //超管或者审核级别最大
				ca_big.getAuditOrder().equals(auditOrder)){
			tagsDao.verify_nopass_tags(tablename, id,10,
					getSessionIntValue(ElConstants.SESSION_USERID),
					userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)).getDepartment().getId(),
					auditMark,ca_big.getAuditName());
		}else{
			int status = tagsDao.get_status_by_tablename_id(tablename,id);
			if(status == 0){
				//已创建
				this.setElmessage("对不起，该记录还未提交审核，无法审核!!!");
				return "error";
//			}else if(status == 5){
//				//初审等待中
//				this.setElmessage("对不起，该记录为初审等待中，无法审核!!!");
//				return "error";
//			}else if(status == 7){
//				//初审不通过
//				this.setElmessage("对不起，该记录为初审不通过，无法审核!!!");
//				return "error";
			}else if(status == 9){
				//终审通过
				this.setElmessage("对不起，该记录为终审通过，无法审核!!!");
				return "error";
			}else if(status == 10){
				//终审不通过
				this.setElmessage("对不起，该记录为终审不通过，无法审核!!!");
				return "error";
			}	
			//判断当前用户是否有该审核权限
			if(!tagsDao.checkUserInAudit(tablename,getSessionIntValue(ElConstants.SESSION_USERID),auditOrder)){
				//无审核权限
				this.setElmessage("对不起，您无审核权限，请联系管理员");
				return "error";
			}
			//验证审核级别比当前审核级别高的情况下，不能进行审核
			//比如数据状态为二级审核通过，当前审核级别为一级审核通过
			if(status>Integer.parseInt(auditOrder) * 2 + 10 + 1){
				this.setElmessage("对不起，该数据审核状态高于当前审核级别，不能进行审核!!!");
				return "error";
			}
			//判断上一个审核级别是否已经审核
			CustomAudit ca = tagsDao.get_audit_by_tablename_userid(tablename, getSessionIntValue(ElConstants.SESSION_USERID));
			auditOrder_ = ca.getAuditOrder();
			
			if(ca_small.getAuditOrder() != null && auditOrder_ != null 
					&&!ca_small.getAuditOrder().equals(auditOrder_)){//当前审核级别不是最小的
				//判断数据状态
				if(status== Integer.parseInt(auditOrder_) * 2 + 10 - 1){
					this.setElmessage("对不起，上一级审核不通过或者还未审核!!!");
					return "error";
				}
			}
			tagsDao.verify_nopass_tags(tablename, id,Integer.parseInt(auditOrder_)*2+10+1,
					getSessionIntValue(ElConstants.SESSION_USERID),
					userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)).getDepartment().getId(),
					auditMark,ca.getAuditName());
		}
		return "verifynopassTagsSuccess";
	}

	//终审
	public String verifypassFinalTags() throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		String produce_table = tagsDao.getProduceTableByTable(tablename);
		id = 0;
		String[] arr = null;
		if(ids!=null&&!"".equals(ids)){
			arr = ids.split(",");
			for(int s=0;s<arr.length;s++){
				id = Integer.parseInt(arr[s]);
				auditMark.setEntityid(id);
				tagsDao.verify_pass_final_tags(tablename,id,9,getSessionIntValue(ElConstants.SESSION_USERID),
						userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)).getDepartment().getId(),
						auditMark,auditOrder);
				
				// 终审通过，将该表的数量在结果表中修改,遍历过程表该字段求和更新结果表字段值
				// 验证表类型
				if (produce_table != null && !produce_table.equals("")) {// 过程表
					// 过程表中数据审核通过
					String result_table = tagsDao
							.getResultTableByProduceTable(produce_table);
					int userid = getSessionIntValue(ElConstants.SESSION_USERID);
					Map<String, String> hm = new HashMap<String, String>();
					Map<String, String> hm_;
					Map<String, String> hm__;
					String column_in_result_table_ = "";
					String columns_in_produce_table_ = "";
					double f = 0.0;

					List<Tags> list_tags_relate = tagsDao
							.select_designe_field_by_tablename(produce_table);
					String sqlAppend = " and t.moduleid =  '" + tablename
							+ "' and t.danjuid = " + id;
					List<Map<String,String>> list_designe_relate = tagsDao.select_my_tableinfo_by_userid_order(
							sqlAppend, 1, list_tags_relate, produce_table, hm, userid,
							null, getPageNow(), getPageSize());

					for (int i = 0; i < list_designe_relate.size(); i++) {
						hm_ = new HashMap<String, String>();
						hm__ = new HashMap<String, String>();
						for (int j = 0; j < list_tags_relate.size(); j++) {
							// 判断该字段是否是表间计算的字段
							String column_jisuantype = tagsDao
									.checkColumnIsBiaojianjisuan(produce_table,
											list_tags_relate.get(j).getColumn_name());

							if (column_jisuantype != null
									&& !column_jisuantype.equals("")) {

								String column_in_result_table = tagsDao
										.getColumn_name_by_id(Integer
												.parseInt(column_jisuantype.split(";")[0]));// 结果表中表间计算字段
								column_in_result_table_ = column_in_result_table;
								String columns_in_produce_table = tagsDao
										.getColumnNameFromResultAndProduceTable(
												result_table, produce_table,
												column_in_result_table);
								columns_in_produce_table_ = columns_in_produce_table;

								String jisuan_type = column_jisuantype;
								if (jisuan_type != null && !jisuan_type.equals("")) {
									String jisuan_type_ = column_jisuantype.split(";")[1];
									String[] jisuans = jisuan_type_.split("==");
									String o = jisuans[1];
									if (jisuans != null) {
										jisuans = jisuans[0].split(",");
										for (int k = 0; k < jisuans.length; k++) {
											if (jisuans[k].indexOf(tablename) >= 0) {
												jisuan_type = o;
											}
										}
									}
								}

								if (jisuan_type.equals("add")) {
									f = Double.parseDouble(list_designe_relate.get(i)
											.get(column_jisuantype.split(";")[2]));
								} else if (jisuan_type.equals("minus")) {
									f = Double.parseDouble(list_designe_relate.get(i)
											.get(column_jisuantype.split(";")[2]));
								}
								hm_.put(columns_in_produce_table, String.valueOf(f));
								hm__.put(column_in_result_table_, String.valueOf(f));
							}
						}
						// 更新过程表中值
						tagsDao.updateProduceContent(hm_, Integer
								.parseInt(list_designe_relate.get(i).get("id")),
								produce_table);

					}
					// 更新结果表中值
					// 相关的ids
					// 普通表
					for (int m = 0; m < list_tags.size(); m++) {
						if (list_tags.get(m).getDisplay_type().equals("相关字段")
								&& list_tags.get(m).getRelateIsShowComplete() == 1) {
							String ids_relate = tagsDao.getRelateIds(list_tags.get(m)
									.getTable_name(),
									list_tags.get(m).getColumn_name(), id);
							String[] array = null;
							if (ids_relate != null && !ids_relate.equals("")) {
								array = new String[ids_relate.length()];
								array = ids_relate.split(",");
							}
							if (array != null && array.length > 0) {
								for (int j = 0; j < array.length; j++) {
									tagsDao
											.updateResultContent(
													tagsDao
															.getResultTableByProduceTable(produce_table),
													produce_table,
													column_in_result_table_,
													columns_in_produce_table_, Integer
															.parseInt(array[j]), id);// 更新结果表中表间计算字段
								}
							}
						}
					}

				}
			}
		}

		return "verifypassFinalTagsSuccess";
	}

	public String verifynopassFinalTags() throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		String produce_table = tagsDao.getProduceTableByTable(tablename);
		
		id = 0;
		String[] arr = null;
		if(ids!=null&&!"".equals(ids)){
			arr = ids.split(",");
			for(int s=0;s<arr.length;s++){
				id = Integer.parseInt(arr[s]);
				auditMark.setEntityid(id);
				tagsDao.verify_nopass_final_tags(tablename,id,10,getSessionIntValue(ElConstants.SESSION_USERID),
						userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID)).getDepartment().getId(),
						auditMark,auditOrder);
				
				// 终审通过，将该表的数量在结果表中修改,遍历过程表该字段求和更新结果表字段值
				// 验证表类型
				if (produce_table != null && !produce_table.equals("")) {// 过程表
					// 过程表中数据审核通过
					String result_table = tagsDao
							.getResultTableByProduceTable(produce_table);
					int userid = getSessionIntValue(ElConstants.SESSION_USERID);
					Map<String, String> hm = new HashMap<String, String>();
					Map<String, String> hm_;
					Map<String, String> hm__;
					String column_in_result_table_ = "";
					String columns_in_produce_table_ = "";
					double f = 0.0;

					List<Tags> list_tags_relate = tagsDao
							.select_designe_field_by_tablename(produce_table);
					String sqlAppend = " and t.moduleid =  '" + tablename
							+ "' and t.danjuid = " + id;
					List<Map<String,String>> list_designe_relate = tagsDao.select_my_tableinfo_by_userid_order(
							sqlAppend, 1, list_tags_relate, produce_table, hm, userid,
							null, getPageNow(), getPageSize());

					for (int i = 0; i < list_designe_relate.size(); i++) {
						hm_ = new HashMap<String, String>();
						hm__ = new HashMap<String, String>();
						for (int j = 0; j < list_tags_relate.size(); j++) {
							// 判断该字段是否是表间计算的字段
							String column_jisuantype = tagsDao
									.checkColumnIsBiaojianjisuan(produce_table,
											list_tags_relate.get(j).getColumn_name());

							if (column_jisuantype != null
									&& !column_jisuantype.equals("")) {

								String column_in_result_table = tagsDao
										.getColumn_name_by_id(Integer
												.parseInt(column_jisuantype.split(";")[0]));// 结果表中表间计算字段
								column_in_result_table_ = column_in_result_table;
								String columns_in_produce_table = tagsDao
										.getColumnNameFromResultAndProduceTable(
												result_table, produce_table,
												column_in_result_table);
								columns_in_produce_table_ = columns_in_produce_table;

								String jisuan_type = column_jisuantype;
								if (jisuan_type != null && !jisuan_type.equals("")) {
									String jisuan_type_ = column_jisuantype.split(";")[1];
									String[] jisuans = jisuan_type_.split("==");
									String o = jisuans[1];
									if (jisuans != null) {
										jisuans = jisuans[0].split(",");
										for (int k = 0; k < jisuans.length; k++) {
											if (jisuans[k].indexOf(tablename) >= 0) {
												jisuan_type = o;
											}
										}
									}
								}

								if (jisuan_type.equals("add")) {
									f = Double.parseDouble(list_designe_relate.get(i)
											.get(column_jisuantype.split(";")[2]));
								} else if (jisuan_type.equals("minus")) {
									f = Double.parseDouble(list_designe_relate.get(i)
											.get(column_jisuantype.split(";")[2]));
								}
								hm_.put(columns_in_produce_table, String.valueOf(f));
								hm__.put(column_in_result_table_, String.valueOf(f));
							}
						}
						// 更新过程表中值
						tagsDao.updateProduceContent(null, Integer
								.parseInt(list_designe_relate.get(i).get("id")),
								produce_table);

					}
					// 更新结果表中值
					// 相关的ids
					// 普通表
					for (int m = 0; m < list_tags.size(); m++) {
						if (list_tags.get(m).getDisplay_type().equals("相关字段")
								&& list_tags.get(m).getRelateIsShowComplete() == 1) {
							String ids_relate = tagsDao.getRelateIds(list_tags.get(m)
									.getTable_name(),
									list_tags.get(m).getColumn_name(), id);
							String[] array = null;
							if (ids_relate != null && !ids_relate.equals("")) {
								array = new String[ids_relate.length()];
								array = ids_relate.split(",");
							}
							if (array != null && array.length > 0) {
								for (int j = 0; j < array.length; j++) {
									tagsDao
											.updateResultContent(
													tagsDao
															.getResultTableByProduceTable(produce_table),
													produce_table,
													column_in_result_table_,
													columns_in_produce_table_, Integer
															.parseInt(array[j]), id);// 更新结果表中表间计算字段
								}
							}
						}
					}

				}
			}
		}
		
		
		return "verifynopassFinalTagsSuccess";
	}

	public String myaddApplyUpate() throws ElException {
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				tagsDao.apply_update(tablename, id);
			}
		}
		return "myaddApplyUpateSuccess";
	}

	public String myaddApplyDel() throws ElException {
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				tagsDao.apply_del(tablename, id);
			}
		}
		return "myaddApplyDelSuccess";
	}

	public String myaddAllowUpate() throws ElException {
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				tagsDao.allow_update(tablename, id);
			}
		}
		return "myaddAllowUpateSuccess";
	}

	public String myaddAllowDel() throws ElException {
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				tagsDao.allow_del(tablename, id);// id为单据id
			}
		}

		
		return "myaddAllowDelSuccess";
	}
	
	/**
	 * 清空
	 * @return
	 * @throws ElException
	 */
	public String allDel()throws ElException{
		tagsDao.allow_del(tablename);
		return "allDelSuccess";
	}

	public String myaddNoAllowUpate() throws ElException {
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				tagsDao.noallow_update(tablename, id);
			}
		}
		return "myaddNoAllowUpateSuccess";
	}

	public String myaddNoAllowDel() throws ElException {
		id = 0;
		String[] array = null;
		if(ids!=null&&!"".equals(ids)){
			array = ids.split(",");
			for(int i=0;i<array.length;i++){
				id = Integer.parseInt(array[i]);
				tagsDao.noallow_del(tablename, id);
			}
		}
		return "myaddNoAllowDelSuccess";
	}

	// ---------------------------------
	public String searchClientContactTags() throws ElException {
		String[] linktype;
		String value = "";

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

		list_tags = tagsDao
				.select_designe_field_by_tablename("tb_clientlinkman_tags");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getColumn_name().equals(
					"tb_clientlinkman_tags_41"))// 联系类型
			{
				value = list_tags.get(i).getDefault_value();
				// linktype=value.split("==");
				break;// 跳出循环
			}
		}
		list_num_sum = clientDao.contact_client_search_by_contact_type(value,
				department);

		return "searchClientContactTagsSuccess";
	}

	public String contactRelateClient() throws ElException {
		// list_client=clientDao.contact_get_relate_client();
		return "contactRelateClientSuccess";
	}

	/*
	 * 客户管理自定义
	 */
	public String clientDesigneAddInit() throws ElException {
		return "clientDesigneAddInitSuccess";
	}

	public String clientDesigneAdd() throws ElException {
		// 如果表名为空，则添加表名
		if (tags.getTable_name() == null || tags.getTable_name().equals("")) {
			tags.setTable_name("tb_client_tags");
		}

		// 获取 是否显示的属性值
		String[] str = getRequest().getParameterValues("display");
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals("add_display"))
				tags.setAdd_display(1);
			else if (str[i].equals("update_display"))
				tags.setUpdate_display(1);
			else if (str[i].equals("view_display"))
				tags.setView_display(1);
			else if (str[i].equals("list_display"))
				tags.setList_display(1);
			else if (str[i].equals("mutilsearch_display"))// 组合搜索
				tags.setMutilsearch_display(1);
			else if (str[i].equals("departsearch_display"))
				tags.setDepartsearch_display(1);
		}
		if (tags.getName_display() == null || tags.getName_display().equals(""))
			return "contactlinkmanDesigneAddError";

		tagsDao.insert_designe_field(0, tags);

		return "clientDesigneAddSuccess";
	}

	public String designeClientTagsInit() throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename("tb_client_tags");
		return "designeClientTagsInitSuccess";
	}

	public String designeClientTags() throws ElException {
		List<Tags> list = new ArrayList<Tags>();
		Tags tag = null;
		list_tags = tagsDao.select_designe_field_by_tablename("tb_client_tags");
		for (int j = 0; j < list_tags.size(); j++) {

			tag = new Tags();
			tag.setId(list_tags.get(j).getId());
			tag.setCannot_modify(list_tags.get(j).getCannot_modify());
			String[] sn = getRequest().getParameterValues(
					"sn_" + list_tags.get(j).getId());
			tag.setSn(Integer.valueOf(sn[0]));
			if (list_tags.get(j).getCannot_modify() != 1) {
				String[] str = getRequest().getParameterValues(
						"display_" + list_tags.get(j).getId());
				if (str != null) {
					for (int i = 0; i < str.length; i++) {

						if (str[i].equals("add_display"))
							tag.setAdd_display(1);
						else if (str[i].equals("update_display"))
							tag.setUpdate_display(1);
						else if (str[i].equals("view_display"))
							tag.setView_display(1);
						else if (str[i].equals("list_display"))
							tag.setList_display(1);
						else if (str[i].equals("mutilsearch_display"))// 组合搜索
							tag.setMutilsearch_display(1);
						else if (str[i].equals("departsearch_display"))// 部门查询
							tag.setDepartsearch_display(1);
					}
				} else {
					tag.setAdd_display(0);
					tag.setUpdate_display(0);
					tag.setView_display(0);
					tag.setList_display(0);
					tag.setMutilsearch_display(0);
					tag.setDepartsearch_display(0);
				}
			}
			list.add(tag);
		}
		list.size();
		tagsDao.manage_designe_field(list);
		return "designeClientTagsSuccess";
	}

	public String addClientTagsInit() throws ElException {

		// tags.setTable_name();
		list_tags = tagsDao.select_designe_field_by_tablename("tb_client_tags");
		// list_tags.size();

		return "addClientTagsInitSuccess";
	}

	public String addClientTags() throws ElException {

		// String
		// theme=(String)getRequest().getParameter("tb_clientlinkman_tags_35");
		// list_tags = (List<Tags>)getRequest().getAttribute("list_tags");
		Map<String, String> hm = new HashMap<String, String>();
		list_tags = tagsDao
				.select_designe_field_by_tablename("tb_clientlinkman_tags");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getAdd_display() == 1) {

				/*
				 * 
				 * String str = (String) getRequest().getParameter(
				 * list_tags.get(i).getColumn_name()); if (str != null &&
				 * !str.equals("")) hm.put(list_tags.get(i).getColumn_type() +
				 * "==" + list_tags.get(i).getColumn_name(), str);
				 * 
				 */
				if (list_tags.get(i).getDisplay_type().equals("附件上传")) {
					String str = (String) getRequest().getParameter(
							list_tags.get(i).getColumn_name());
					String str2 = (String) getRequest().getParameter(
							list_tags.get(i).getColumn_name() + "_");

					if (str2 != null && !str2.equals("") && str != null
							&& !str.equals("")) {
						hm.put(list_tags.get(i).getColumn_type() + "=="
								+ list_tags.get(i).getColumn_name(), str + "=="
								+ str2);
						// list_tags.get(i).setValue2(str2);
					}
				} else if (list_tags.get(i).getDisplay_type().equals("图片")) {
					String height = (String) getRequest().getParameter(
							list_tags.get(i).getColumn_name() + "_h");
					String width = (String) getRequest().getParameter(
							list_tags.get(i).getColumn_name() + "_w");
					String addr = (String) getRequest().getParameter(
							list_tags.get(i).getColumn_name());

					if (height.equals("") || height == null)
						height = "0";
					if (width.equals("") || width == null)
						width = "0";

					// System.out.println();
					if (addr != null && !addr.equals("")) {
						hm.put(list_tags.get(i).getColumn_type() + "=="
								+ list_tags.get(i).getColumn_name(), height
								+ "==" + width + "==" + addr);
					}

				} else {
					String str = (String) getRequest().getParameter(
							list_tags.get(i).getColumn_name());
					if (str != null && !str.equals(""))
						hm.put(list_tags.get(i).getColumn_type() + "=="
								+ list_tags.get(i).getColumn_name(), str);
				}

			}

		}

		tagsDao.insert_tableinfo_by_tablename(1, hm, getTablename(list_tags
				.get(0).getColumn_name()),
				getSessionIntValue(ElConstants.SESSION_USERID));

		return "addClientTagsSuccess";
	}

	public String relateColumn() throws ElException {
		System.out.println(tablename);
		System.out.println(columnName);
		list_tags = moduleManageDao.select_designe_field_by_tablename(null,
				tablename);

		String search_control = getRequest().getParameter("control");
		String is_judge = getRequest().getParameter("is_judge");
		if (search_control == null)
			search_control = "0";
		if (is_judge == null) {
			if(tags != null){
				if (tags.getIs_judge() != 0) {
					is_judge = String.valueOf(tags.getIs_judge());
				}
				else {
					is_judge = "0";
				}
			}else {
				is_judge = "0";
			}
		}
		String columnname = getRequest().getParameter("columnname");
		getRequest().setAttribute("columnname", columnname);
		getRequest().setAttribute("search_control", search_control);

		getRequest().setAttribute("is_judge", is_judge);

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

		Map<String, String> hm = new HashMap<String, String>();

		// 组合搜索获取搜索条件
		for (int i = 0; i < list_tags.size(); i++) {
			String str = (String) getRequest().getParameter(
					list_tags.get(i).getColumn_name());
			if (str != null && !str.equals("")) {
				hm.put(list_tags.get(i).getColumn_type() + "=="
						+ list_tags.get(i).getColumn_name(), str);
				list_tags.get(i).setValue(str);// 将搜索条件传回前端
			}
			// 日期格式
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

		int userid = getSessionIntValue(ElConstants.SESSION_USERID);

		columnName = columnName==null?"":columnName;
		tags = tagsDao.select_designe_field_by_id(tagsDao
				.select_designe_field_id_by_columnName(columnName));
		if (is_judge.equals("1")) {// 权限判断
			list_designe = tagsDao.select_my_tableinfo_by_dep_principal(tags,
					list_tags,// department,
					tablename, hm, department, // tablename, hm,
					Integer.valueOf(search_control), userid, // search_control,
																// principalid,
					getPageNow(), getPageSize());
			count = tagsDao.select_my_tableinfo_by_dep_principal_count(tags,
					list_tags, hm, // ,
					tablename, department, // tablename, department
					Integer.valueOf(search_control), userid);// search_control,
																// principalid)
		} else if (is_judge.equals("0")) {// 无权限判断
			list_designe = tagsDao
					.select_my_tableinfo_by_dep_principal_with_judge(tags,
							list_tags, tablename, hm, department, Integer
									.valueOf(search_control), userid,
							getPageNow(), getPageSize());
			count = tagsDao
					.select_my_tableinfo_by_dep_principal_count_with_judge(
							tags, list_tags, hm, // ,
							tablename, department, // tablename, department
							Integer.valueOf(search_control), userid);// search_control,
																		// principalid)
		}

		return "relateColumnSuccess";
	}

	public String relateEluser() throws ElException {
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

		return "relateEluserSuccess";
	}

	public String getProduceTableByTable() throws ElException {
		String result = "";
		result = String.valueOf(tagsDao.getProduceTableByTable(tablename));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String delRelateTags() throws ElException {

		String relateid = getRequest().getParameter("relateid");
		String relatename = getRequest().getParameter("relatename");
		columnName = relatename;
		String relatetablename = getRequest().getParameter("relatetablename");
		// actionName
		// id
		tablename = relatetablename;// 业务表

		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		String produce_table = tagsDao.getProduceTableByTable(tablename);
		// 终审通过，将该表的数量在结果表中修改,遍历过程表该字段求和更新结果表字段值
		// 验证表类型
		String column_in_result_table_ = "";
		String columns_in_produce_table_ = "";
		if (produce_table != null && !produce_table.equals("")) {// 过程表
			// 过程表中数据审核通过
			String result_table = tagsDao
					.getResultTableByProduceTable(produce_table);
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			Map<String, String> hm = new HashMap<String, String>();

			List<Tags> list_tags_relate = tagsDao
					.select_designe_field_by_tablename(produce_table);
			String sqlAppend = " and t.moduleid =  '" + tablename
					+ "' and t.danjuid = " + id;
			List<Map<String,String>> list_designe_relate =  tagsDao.select_my_tableinfo_by_userid_order(
					sqlAppend, 1, list_tags_relate, produce_table, hm, userid,
					null, getPageNow(), getPageSize());

			for (int i = 0; i < list_designe_relate.size(); i++) {
				for (int j = 0; j < list_tags_relate.size(); j++) {
					// 判断该字段是否是表间计算的字段
					String column_jisuantype = tagsDao
							.checkColumnIsBiaojianjisuan(produce_table,
									list_tags_relate.get(j).getColumn_name());

					if (column_jisuantype != null
							&& !column_jisuantype.equals("")) {
						String column_in_result_table = tagsDao
								.getColumn_name_by_id(Integer
										.parseInt(column_jisuantype.split(";")[0]));// 结果表中表间计算字段
						column_in_result_table_ = column_in_result_table;
						String columns_in_produce_table = tagsDao
								.getColumnNameFromResultAndProduceTable(
										result_table, produce_table,
										column_in_result_table);
						columns_in_produce_table_ = columns_in_produce_table;
					}
				}
			}
		}

		tagsDao.delete_relate_by_relateid_and_conlumnname(
				column_in_result_table_, columns_in_produce_table_, relatename,
				tablename, Integer.valueOf(relateid), id);

		// 更新业务表中表内求和字段的值
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getBiaojianqiuhe_check() == 1) {
				tagsDao.updateYewuBiaojianqiuheContent(tablename, list_tags
						.get(i), id);
				break;
			}
		}
		return "delRelateTagsSuccess";
	}

	public String updatePrincipal() throws ElException {
		String principalid = getRequest().getParameter("principalid");
		tagsDao.update_principal(tablename, id, Integer.valueOf(principalid));
		return "updatePrincipalSuccess";
	}

	public String updateDesigneTagsInit() throws ElException {
		tags = tagsDao.select_designe_field_by_id(id);
		
		//城市字段特殊处理
		if(tags.getDefault_value() != null && !tags.getDefault_value().equals("")){
			if(tagsDao.checkColumnIsDateById(Integer.parseInt(tagsDao.getIdByColumnName(tags.getTable_name(), tags.getColumn_name())),"城市")){
				tags.setDefault_value(tags.getDefault_value().replaceAll(" ", "=="));
			}
		}
		System.out.println(tags.getDefault_value());
		return "updateDesigneTagsInitSuccess";
	}

	public String updateDesigneTags() throws ElException {
		// 获取 是否显示的属性值
		String[] str = getRequest().getParameterValues("display");
		String[] nowdate = getRequest().getParameterValues("timecheck");
		String[] wbdefault = getRequest().getParameterValues("wbdefault");
		String[] wbwidth = getRequest().getParameterValues("wbwidth");
		String[] display_type = getRequest().getParameterValues("display_type");
		String[] sum_display = getRequest().getParameterValues("sumcheck");

		String[] date_number = getRequest().getParameterValues("date_number");

		tags.setDisplay_type(display_type[0]);

		for (int i = 0; i < str.length; i++) {
			if (str[i].equals("add_display"))
				tags.setAdd_display(1);
			else if (str[i].equals("update_display"))
				tags.setUpdate_display(1);
			else if (str[i].equals("view_display"))
				tags.setView_display(1);
			else if (str[i].equals("list_display"))
				tags.setList_display(1);
			else if (str[i].equals("mutilsearch_display"))// 组合搜索
				tags.setMutilsearch_display(1);
			else if (str[i].equals("departsearch_display"))
				tags.setDepartsearch_display(1);
			else if (str[i].equals("required"))
				tags.setRequired(1);
		}
		if (sum_display != null) {
			if (sum_display[0].equals("sum"))
				tags.setSum_display(1);
		}

		if (tags.getDisplay_type() != null
				&& tags.getDisplay_type().equals("文本")) {
			if (!wbwidth[0].equals("") || !wbdefault[0].equals(""))
				tags.setDefault_value(wbdefault[0] + "==" + wbwidth[0]);
		}
		if (tags.getDisplay_type() != null
				&& tags.getDisplay_type().equals("城市")) {
			if (!wbwidth[0].equals(""))
				tags.setDefault_value(wbdefault[0] );
		}
		if (tags.getDisplay_type() != null
				&& tags.getDisplay_type().equals("日期")) {
			if (nowdate != null) {
				if (nowdate[0].equals("nowdate")) {
					if (date_number != null) {
						if (date_number[0].equals("0"))
							tags.setDefault_value("nowdate");
						else
							tags.setDefault_value("nowdate_" + date_number[0]);
					}
				}
			}
		}
		if (wbdefault != null) {
			if (wbdefault[0] != null) {
				if (tags.getDefault_value() == null
						|| tags.getDefault_value().equals("")) {
					tags.setDefault_value(wbdefault[0]);
				}
			}
		}
		tagsDao.update_designe_field(tags);
		System.out.println(tags.getTable_name());
		
		if(tagsMark!=null&&tagsMark.getColumnname()!=null&&!tagsMark.getColumnname().equals("")){
			tagsDao.update_tb_tags_mark(tagsMark);
		}
		return "updateDesigneTagsSuccess";
	}

	public String importExcelDataInit() throws ElException {
		return "importExcelDataInitSuccess";
	}

	public String importExcelData() throws ElException {
		//导入变灰还未处理完全
		//TODO
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		if (path == null) {
			this.setElmessage("请选择Excel文件!!!");
			return "importExcelDataError";
		}
		copyDataFromExcelToDb(path, tablename, list_tags);

		return "importExcelDataSuccess";
	}

	// ---------------------------------------------------------------------------------------------------
	/*
	 * 模块复制添加
	 */
	public String addModuleManageInit() throws ElException {
		return "addModuleManageInitSuccess";
	}

	public String addModuleManage() throws ElException {
		String resultPage = "";
		// 验证表是否已经存在
		if (moduleManage.getTablename() != null
				&& !moduleManage.getTablename().equals("")) {
			if (moduleManageDao.checkTableIsExist(moduleManage.getTablename()) != 0) {
				this.setElmessage("表已经存在，请在表名后添加1、2、3等数字区分!!!");
				resultPage = "addModuleManageInitSuccess";
			} else {
				moduleManageDao.add_module(moduleManage);
				resultPage = "addModuleManageSuccess";
			}
		}
		
		return resultPage;
	}

	public String myModuleManage() throws ElException {
		int userid = getSessionIntValue(ElConstants.SESSION_USERID);

		list_module = moduleManageDao.select_mymodule(moduleManage,
				getPageNow(), getPageSize());
		count = moduleManageDao.select_mymodule_count(moduleManage);
		for (int i = 0; i < list_module.size(); i++) {
			list_module.get(i).setList_my_charge_search(
					moduleManageDao.select_my_charge_by_tablename(list_module
							.get(i).getTablename(), userid));
		}
		return "myModuleManageSuccess";
	}

	public String updateModuleManageInit() throws ElException {
		moduleManage = moduleManageDao
				.select_module_by_id(moduleManage.getId());
		return "updateModuleManageInitSuccess";
	}

	public String updateModuleManage() throws ElException {
		moduleManageDao.update_module_by_id(moduleManage);
		return "updateModuleManageSuccess";
	}

	public String getModule() throws ElException {

		list_module = moduleManageDao.select_mymodule(getPageNow(),
				getPageSize());
		count = moduleManageDao.select_mymodule_size(getPageNow(),
				getPageSize());
		return "getModuleSuccess";
	}

	public String getModuleColumn() throws ElException {

		list_tags = moduleManageDao.select_designe_field_by_tablename(type,
				tablename);
		return "getModuleColumnSuccess";
	}

	// ---------------------------------------------------------------------------------------------------
	/*
	 * some methods
	 */

	public String getTablename(String str) {
		// str.subString(0, str.lastIndexOf("_"));
		return str.substring(0, str.lastIndexOf("_"));
	}

	// write into excel
	public boolean copyDataToExcel(List<Map<String, String>> list_designe,
			List<Tags> list_tags) {
		String filename = list_tags.get(0).getTable_name() + ".xls";
		String path = "d:\\source\\" + list_tags.get(0).getTable_name() + "\\";

		if (!(new File(path).isDirectory())) {
			new File(path).mkdirs();
		}

		try {
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(path
					+ filename));
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet("info", 0);

			for (int i = 0, k = 0; i < list_tags.size(); i++) {
				if (!list_tags.get(i).getDisplay_type().equals("附件上传")
						&& !list_tags.get(i).getDisplay_type().equals("图片")
						&& !list_tags.get(i).getDisplay_type().equals("富文本")) {
					sheet.setColumnView(0, 10);
					// 列， 行，值
					sheet.addCell(new Label(i - k, 0, list_tags.get(i)
							.getName_display()));
				} else {
					k++;
				}
			}

			for (int i = 0; i < list_designe.size(); i++) {
				for (int j = 0, k = 0; j < list_tags.size(); j++) {
					if (!list_tags.get(j).getDisplay_type().equals("附件上传")
							&& !list_tags.get(j).getDisplay_type().equals("图片")
							&& !list_tags.get(j).getDisplay_type()
									.equals("富文本")) {
						// 列，行，值
						sheet.addCell(new Label(j - k, i + 1, list_designe.get(
								i).get(list_tags.get(j).getColumn_name())));
					} else {
						k++;
					}
				}
			}

			// 写入数据并关闭文件
			book.write();
			book.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// copy data from excel
	public boolean copyDataFromExcelToDb(File file, String tablename,
			List<Tags> list_tags) {
		Workbook book = null;
		Sheet sheet = null;
		int rows;
		int cols;
		try {
			InputStream is = new FileInputStream(file);
			book = Workbook.getWorkbook(is);
			is.close();
			System.out.println(book.getSheets().length);
			sheet = book.getSheet(0);

			rows = sheet.getRows(); // "总行数:"
			cols = sheet.getColumns(); // "总列数:"

			// 验证excel数据格式是否正确
			int k = 0, i = 0;
			for (i = 0; i < list_tags.size(); i++) {
				if (!list_tags.get(i).getDisplay_type().equals("附件上传")
						&& !list_tags.get(i).getDisplay_type().equals("图片")
						&& !list_tags.get(i).getDisplay_type().equals("富文本")) {
					// 列 ，行
					if (!list_tags.get(i).getName_display().equals(
							sheet.getCell(i - k, 0).getContents())) {
						break;
					}
				} else {
					k++;
				}
			}
			if (i < list_tags.size())
				return false;
			// 验证通过 并写入数据
			int userid = getSessionIntValue(ElConstants.SESSION_USERID);
			for (i = 1; i < rows; i++) {
				Map<String, String> hm = new HashMap<String, String>();
				k = 0;
				for (int j = 0; j < list_tags.size(); j++) {
					if (!list_tags.get(j).getDisplay_type().equals("附件上传")
							&& !list_tags.get(j).getDisplay_type().equals("图片")
							&& !list_tags.get(j).getDisplay_type()
									.equals("富文本")) {
						if (!list_tags.get(j).getDisplay_type().equals("相关字段")
								&& !list_tags.get(j).getDisplay_type().equals(
										"相关负责人")) {
							String str = sheet.getCell(j - k, i).getContents();
							if (!str.equals("")) {
								hm.put(list_tags.get(j).getColumn_type() + "=="
										+ list_tags.get(j).getColumn_name(),
										str);
							}
						}
					} else {
						k++;
					}
				}
				tagsDao.insert_tableinfo_by_tablename_status9(1, hm, tablename, userid);
			}

			book.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public String checkColumnIsExistByTable() throws ElException {
		boolean result = false;
		result = tagsDao.checkColumnIsExistByTable(tablename, columnName);
		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String checkSelectColumnIsRight() throws ElException {
		String result = "";
		result = tagsDao.checkSelectColumnIsRight(tablename, columnName);
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
	
	

	public String check_relate_has_in_table() throws ElException {
		boolean result = false;
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		for (Tags tag : list_tags) {
			if (tag.getDisplay_type().equals("相关负责人")) {
				result = true;
			}
		}

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String selectQiujiRelateColumn_zidingyi() throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		return "selectQiujiRelateColumn_zidingyi";
	}

	public String getQiuheResult() throws ElException {
		double result = 0.0;
		result = tagsDao.getQiuheResult(tablename, columnName);

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String check_column_is_calculate_in_module() throws ElException {
		tb_calculates = tagsDao.getTb_calculateByTableNameAndColumnName(
				tablename, columnName);

		check_json_result = TagsUtil.ToGson(tb_calculates);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String checkTable() throws ElException {
		int result = 0;
		result = tagsDao.checkTable(tablename);

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public String getBiaojianqiuheValue() throws ElException {
		String result = tagsDao.getBiaojianqiuheValue(tablename);

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

	public String select_columnname_by_tablename_chengshi() throws ElException {
		String result  = tagsDao
		.select_columnname_by_tablename_chengshi(tablename);

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
	
	public String getShengshixian() throws ElException {
		String result  = tagsDao
		.getShengshixian(id,tablename,columnName);

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
	
	public String getDefaultValue_shengshixian() throws ElException {
		String result  = tagsDao
		.getDefaultValue_shengshixian(tablename,columnName);

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

	public String getValue() throws ElException {
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
		// list_tags = tagsDao
		// .select_designe_field_by_tablename("tb_clientlinkman_tags");
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		list_designe = tagsDao.select_my_tableinfo_by_userid_order("", 1,
				list_tags, tablename, hm, userid, order, getPageNow(),
				getPageSize());
		count = tagsDao.select_my_tableinfo_by_userid_count(1, list_tags, hm,
				tablename, userid);
		return "getValue";
	}

	public String calculate() throws ElException {
		String result = "";
		result = String.valueOf(tagsDao.calculate(ids));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String checkIfHasTwoDateField() throws ElException {
		int result = 0;

		result = tagsDao.checkIfHasTwoDateField(tablename);

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String select_time_begin_end() throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename_time(type,
				tablename);
		return "select_time_begin_end";
	}

	public String getColumnNameById_ajax() throws ElException {
		String result = "";
		result = String.valueOf(tagsDao.getColumn_name_by_id(id));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getNameDisplayById() throws ElException {
		String result = "";
		result = String.valueOf(tagsDao.getNameDisplayById(id));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getIdByColumnName() throws ElException {
		String result = "";
		result = String.valueOf(tagsDao
				.getIdByColumnName(tablename, columnName));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getModuleNameByTablename() throws ElException {
		String result = "";
		result = String.valueOf(tagsDao.getModuleNameByTablename(tablename));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getModuleShujuNameByTablename() throws ElException {
		String result = "";
		result = String.valueOf(tagsDao
				.getModuleShujuNameByTablename(tablename));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String IfHasYewuJindu_column() throws ElException {
		String result = tagsDao.IfHasYewuJindu_column(id, tablename);

		// if(!result.equals("") && result.split(",").length>0){
		// String[] array = result.split(",");
		// result = "";
		// for(int i=0;i<array.length;i++){
		// if(i == array.length - 1)
		// result += tagsDao.getNameDisplayById(Integer.parseInt(array[i]));
		// else
		// result += tagsDao.getNameDisplayById(Integer.parseInt(array[i])) +
		// ",";
		// }
		// String columnName =
		// tagsDao.getColumn_name_by_id(Integer.parseInt(array[0]));
		// result +=
		// ","+tagsDao.getModuleNameByTablename(columnName.substring(0,
		// columnName.lastIndexOf("_")));
		// }

		result = String.valueOf(result);

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String checkColumnIsJutileixingById() throws ElException {
		boolean result = tagsDao.checkColumnIsDateById(id, type);

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getRelateInfoByIds() throws ElException, ParseException {
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
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
		double result_zuihou = 0.0;
		if(relateIds != null && !relateIds.equals("")){
			list_designe = tagsDao.select_my_tableinfo_by_userid_order(
					" and  t.id in " + relateIds, 1, list_tags, tablename, hm,
					userid, order, 0, 0);

			String yewu_jindu = tagsDao.getYewu_jindu_by_columnName(columnName);// 根据列名获取需要计算的字段id

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
			// for(int x = 0;x<yewu.length;x++){//将需要计算的字段id转换为字段名称
			// yewu[x] = tagsDao.getColumn_name_by_id(Integer.parseInt(yewu[x]));
			// }
			Map<String, String> map = new HashMap<String, String>();
			Map<String, String> map1 = new HashMap<String, String>();
			List<Map<String, String>> calculate = new ArrayList<Map<String, String>>();// 存放需要计算的字段

			for (int i = 0; i < list_designe.size(); i++) {
				map = list_designe.get(i);
				int j = 0;
				for (Object key : map.keySet()) {
					if (yewu_jindu.indexOf((String) key) >= 0) {
						if (tagsDao.getDisplay_type_by_columnName((String) key)
								.equals("百分比")) {
							map1.put("百分比", (String) map.get(key));
						} else if (tagsDao.getDisplay_type_by_columnName(
								(String) key).equals("日期")) {
							j++;
							map1.put("日期" + j, (String) map.get(key));
						}
					}
				}
				calculate.add(map1);
				map1 = new HashMap<String, String>();
			}

			String time_result = "";
			double beifenbi_result = 0.0;
			List<Double> result_calculate = new ArrayList<Double>();
			for (int i = 0; i < calculate.size(); i++) {
				map1 = calculate.get(i);
				for (Object key : map1.keySet()) {
					if (((String) key).indexOf("日期") >= 0) {
						time_result += (String) map1.get(key) + ",";
					} else if (((String) key).indexOf("百分比") >= 0) {
						beifenbi_result = Double
								.parseDouble((String) map1.get(key));
					}
				}

				if (!time_result.equals("") && time_result.indexOf(",") >= 0 && time_result.indexOf("null") < 0) {
					String[] time_result_array = time_result.split(",");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					
					if(time1 != null && !time1.equals("") && time2 != null && !time2.equals("")){
						Date time_1 = time1.indexOf(":") >= 0 ? dateFormat.parse(time1)
								: dateFormat.parse(time1 + " 00:00:00");
						Date time_2 = time2.indexOf(":") >= 0 ? dateFormat.parse(time2)
								: dateFormat.parse(time2 + " 00:00:00");

						Date toDate1 = dateFormat.parse(time_result_array[0]
								+ " 00:00:00");
						Date temp = toDate1;
						Date toDate2 = dateFormat.parse(time_result_array[1]
								+ " 00:00:00");
						// 获取比较小的时间
						if (toDate1.getTime() > toDate2.getTime()) {
							toDate1 = toDate2;
							toDate2 = temp;
						}
						double time_cha = toDate2.getTime() - toDate1.getTime();
						result_calculate.add(beifenbi_result * time_cha);

						if (i == calculate.size() - 1) {
							for (int x = 0; x < result_calculate.size(); x++) {
								result_zuihou += result_calculate.get(x);
							}
							result_zuihou = result_zuihou
									/ (Math.abs(time_2.getTime() - time_1.getTime()));
							if(result_zuihou>100)	result_zuihou = 100;
							BigDecimal bg = new BigDecimal(result_zuihou);
							result_zuihou = bg.setScale(2, BigDecimal.ROUND_HALF_UP)
									.doubleValue();
						}
					}else {
						
						result_zuihou = 0;
					}
					

				}

			}
		}
		

		check_json_result = String.valueOf(result_zuihou);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String access_ALL() throws ElException {
		if (ids != null && !ids.equals("")) {
			String[] ids_array = ids.split(",");
			for (int i = 0; i < ids_array.length; i++) {
				if (Integer.parseInt(ids_array[i]) != 0)
					tagsDao.accessById(tags, Integer.parseInt(ids_array[i]));
			}
		}
		return "access_ALL";
	}

	public String updateManagePerson() throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		for (Tags tag : list_tags) {
			if (tag.getDisplay_type().equals("相关负责人")) {
				columnName = tag.getColumn_name();
			}
		}

		// 更新后的负责人ids update_user_ids
		// 数据行ids update_ids
		// 全部更新还是部分更新 updateType
		if (update_ids != null && !update_ids.equals("")
				&& update_user_ids != null && !update_user_ids.equals("")) {
			String[] update_ids_array = update_ids.split(",");
			for (int i = 0; i < update_ids_array.length; i++) {
				if (Integer.valueOf(update_ids_array[i]) != 0)
					tagsDao.update_fuzeren_zidingyi(updateType, tablename,
							Integer.parseInt(update_ids_array[i]),
							update_user_ids, columnName);
			}
		}
		return "updateManagePerson";
	}

	// ===========================================================
	// 查看功能代码详情
	public String viewFunInformation() throws ElException {
		return "viewFunInformation";
	}

	// 表间计算选择表内关联字段
	public String jisuan_select_relate() throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename_time(type,
				tablename);
		return "jisuan_select_relate";
	}

	public String getRelateListByTablenameAndIds() throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		list_designe = tagsDao.getRelateListByTablenameAndIds(list_tags, ids,
				tablename, id);

		Map<String, String> map = new HashMap<String, String>();
		String result = "[";
		for (int i = 0; i < list_designe.size(); i++) {
			map = list_designe.get(i);
			if (i == list_designe.size() - 1)
				result += MapToJson.getJsObject(map).toString();
			else
				result += MapToJson.getJsObject(map).toString() + ",";
		}
		result += "]";
		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getColumnByColumnName() throws ElException {
		String result = "";
		result = String.valueOf(tagsDao.getColumnByColumnName(tablename,
				columnName));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String biaoneijisuan() throws ElException {
		IC_column_qiuji_qiuhe ixqq = tagsDao.biaoneijisuan(tablename,
				columnName);

		check_json_result = TagsUtil.ToGsonObj(ixqq);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// public String checkColumnIsCalculate() throws ElException{
	// check_json_result = tagsDao.checkColumnIsBiaojianjisuan(tablename,
	// columnName);
	//		
	// getResponse().setContentType("text/html;charset=UTF-8");
	// PrintWriter localPrintWriter;
	// try {
	// localPrintWriter = getResponse().getWriter();
	// String d = "{\"check_json_result\":'" + check_json_result + "'}";
	// localPrintWriter.println(d);
	// localPrintWriter.flush();
	// localPrintWriter.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	// public String addToProduce() throws ElException{
	// list_tags = tagsDao
	// .select_designe_field_by_tablename(tablename.split(",")[1]);
	//		
	// tagsDao.addToProduce(list_tags,tablename,parameters,getSessionIntValue(ElConstants.SESSION_USERID));
	//		
	//		
	// getResponse().setContentType("text/html;charset=UTF-8");
	// PrintWriter localPrintWriter;
	// try {
	// localPrintWriter = getResponse().getWriter();
	// String d = "{\"check_json_result\":'" + check_json_result + "'}";
	// localPrintWriter.println(d);
	// localPrintWriter.flush();
	// localPrintWriter.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	public String getProduceTableByResultTable() throws ElException {
		String result = "";
		result = String
				.valueOf(tagsDao.getProduceTableByResultTable(tablename));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String updateResultTableBiaojianValue() throws ElException {
		tagsDao.updateResultTableBiaojianValue(parameters, tablename, id);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getRelate_thing_id() throws ElException {
		check_json_result = String.valueOf(tagsDao
				.getRelate_thing_id(tablename));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String TB_UpdateDemo() throws ElException, Exception {
		if (st == null) {
			return "TB_UpdateDemo";
		}
		if (st.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "TB_UpdateDemo";
		} else {
			String ext = J2EEFileUtil.getExtention(stFileName);
			if (!ext.equals("jsp")) {
				setElmessage("模板文件格式只能为jsp！");
				return "TB_UpdateDemo";
			}
			moduleManage = moduleManageDao.select_module_by_id(moduleManage
					.getId());
			moduleManageDao.updateDemoORCss(moduleManage.getId(), moduleManage
					.getTablename(), "url");
			J2EEFileUtil.upload(st, ext, "admin\\etcperfect\\demo",
					moduleManage.getTablename() + "");
		}
		return "TB_UpdateDemo";
	}

	public String TB_UpdateDemo_CSS() throws ElException, Exception {
		if (st == null) {
			return "TB_UpdateDemo_CSS";
		}
		if (st.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "TB_UpdateDemo_CSS";
		} else {
			String ext = J2EEFileUtil.getExtention(stFileName);
			if (!ext.equals("css")) {
				setElmessage("样式文件格式只能为css！");
				return "TB_UpdateDemo_CSS";
			}
			moduleManage = moduleManageDao.select_module_by_id(moduleManage
					.getId());
			moduleManageDao.updateDemoORCss(moduleManage.getId(), moduleManage
					.getTablename(), "css");
			J2EEFileUtil.upload(st, ext, "admin\\etcperfect\\demo",
					moduleManage.getTablename() + "");
		}
		return "TB_UpdateDemo_CSS";
	}

	public String TB_Demo_downloadInit() throws Exception {
		try {
			getInputStream1();
		} catch (Exception e) {
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		return "TB_Demo_download";
	}

	public InputStream getInputStream1() throws ElException {
		InputStream is = null;
		String path = ServletActionContext.getServletContext().getRealPath(
				"admin\\etcperfect\\demo\\" + fileName);
		try {
			System.out.println(path);
			is = new FileInputStream(path);
		} catch (Exception e) {
			throw new ElException("下载资料出错", e);
		}
		return is;
	}

	public String getProduceColumns() throws ElException {
		tablename = tagsDao.getProduceTableByResultTable(tablename);

		String showColumnIds = tagsDao.getShowColumns(yewu_tablename);

		check_json_result = String.valueOf(tagsDao.getProduceColumns(tablename,
				showColumnIds));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getBiaojianqiuheResultTableAndColumn() throws ElException {
		check_json_result = String.valueOf(tagsDao
				.getBiaojianqiuheResultTableAndColumn(tablename));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getResult_table_column() throws ElException {
		check_json_result = String.valueOf(tagsDao.getResult_table_column(
				tablename, columnName));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getRelateId() throws ElException {
		check_json_result = String.valueOf(tagsDao.getRelateId(columnName, id));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getColumn_name_by_id() throws ElException {
		check_json_result = String.valueOf(tagsDao.getColumn_name_by_id(id));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getProduceColumnByResultColumn() throws ElException {
		check_json_result = String.valueOf(tagsDao
				.getProduceColumnByResultColumn(tablename, param));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getFromtablenameByTablename() throws ElException {
		check_json_result = String.valueOf(moduleManageDao
				.getFromtablenameByTablename(tablename));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getColumnNameByFromtablenameAndColumnName()
			throws ElException {
		check_json_result = String.valueOf(tagsDao
				.getColumnNameByFromtablenameAndColumnName(tablename,
						columnName));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getStatusByIdAndTablename() throws ElException{
		check_json_result = String.valueOf(tagsDao.get_status_by_tablename_id(tablename,id));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getApplicationByIdAndTablename() throws ElException{
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		check_json_result = String.valueOf(tagsDao.getApplicationByIdAndTablename(moduleManage.getId(),getSessionIntValue(ElConstants.SESSION_USERID),id));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getAutoColumns() throws ElException {
		check_json_result = String.valueOf(tagsDao.getAutoColumns(tablename));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getColumnByRelateColumnAndTable() throws ElException{
		check_json_result = String.valueOf(tagsDao.getColumnByRelateColumnAndTable(tablename,columnName));

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getById() throws ElException {
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		list_tags = tagsDao.select_designe_field_content_by_id(list_tags,
				getTablename(list_tags.get(0).getColumn_name()), id);


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
		
		check_json_result = "";
		for (int i = 0; i < list_tags.size(); i++) {
			if (i == list_tags.size() - 1){
				check_json_result += list_tags.get(i).getColumn_name() + "=="
				+ list_tags.get(i).getValue() + "=="
				+ list_tags.get(i).getDisplay_type();
			}
			else{
				check_json_result += list_tags.get(i).getColumn_name() + "=="
				+ list_tags.get(i).getValue() + "=="
				+ list_tags.get(i).getDisplay_type() + "_-_";				
			}
		}

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getListByTablename() throws ElException {
		radio = "1";
		// 是否显示和
		columns_qiuji_qiuhe = ICDao.getQiujiColumns(tablename);
		qiujiRelateColumns = TagsUtil.ToGson(columns_qiuji_qiuhe);

		Map<String, String> hm = new HashMap<String, String>();
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		String order = "";
		if (tags != null) {
			if (tags.getOrdercolumn() != null
					&& !tags.getOrdercolumn().equals("")) {
				order = " order by ";
				order += tags.getOrdercolumn() + " ";
				order += tags.getOrdersc();
			}
		}

		// 组合搜索获取搜索条件
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
			}
			// 相关负责人
			else if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				String str_relate = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name());
				String str_rx = (String) getRequest().getParameter("rx");
				if (str_relate != null && str_rx != null)
					str_relate = (String) getRequest().getParameter(
							list_tags.get(i).getColumn_name());
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

		userid = getSessionIntValue(ElConstants.SESSION_USERID);
		realname = tagsDao.get_eluser_realname_by_id(userid);

		list_designe = tagsDao.select_my_tableinfo_by_userid_order("", 0,
				list_tags, tablename, hm, userid, order, getPageNow(),
				getPageSize());
		count = tagsDao.select_my_tableinfo_by_userid_count(0, list_tags, hm,
				tablename, userid);

		return "getListByTablename";
	}

	public String getQiujiAndHeInfo() throws ElException {
		String result = "";
		result = String.valueOf(tagsDao
				.getQiujiAndHeInfo(tablename, columnName));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String checkColumnRelateIsResultTable() throws ElException{
		boolean result = false;
		result = tagsDao.checkColumnRelateIsResultTable(tablename, columnName);
		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getColumnValueByAuto() throws ElException, IOException {
		String result = "";
		result = String.valueOf(tagsDao.getColumnValueByAuto(tablename,
				columnName, id, yewu_tablename,danjuid));

		check_json_result = String.valueOf(result);

		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":'" + check_json_result + "'}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getZidongbuqiValue() throws ElException {
		String result  = tagsDao.getZidongbuqiValue(tablename,columnName);
		
		check_json_result = String.valueOf(result);
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":\"" + result.trim() + "\"}";
			System.out.println(result.trim());
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//模板添加、修改、查看自定义///////////////////////////////////
	public String addContactTagsInitZDY() throws ElException {
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		if (moduleManage != null && moduleManage.getOndemo() == 1) {// 启用模板
			moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());
			//判断是否已经长传JSP模板
			if(!ZDYTemplateUtil.checkIfUploadByUploadType(tablename,ZDYTemplateUtil.ADD,moduleZDY.getAddjsp())){
				this.setElmessage("您还未上传添加页面的JSP,请先上传!!!");
				return "error";
			}
		}else{
			this.setElmessage("该模块还未设置开通自定义模板,请先开通!!!");
			return "error";
		}
		
		actionName = "addContactTagsInit_ZDY";


		String time_ids = "";
		String yewu_ids = "";
		
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);

		fromtablename = moduleManageDao.getFromtablenameByTablename(tablename);

		currentUser = tagsDao.getCurrentUser(tablename);
		if(currentUser != null )
			currentUser = tagsDao.getCurrentUserByUserId(currentUser,getSessionIntValue(ElConstants.SESSION_USERID));
		
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				uid = String
						.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
				username = userDao.getUserById(
						getSessionIntValue(ElConstants.SESSION_USERID))
						.getRealname();
			}
			if (list_tags.get(i).getDisplay_type().equals("百分比")) {
				if (list_tags.get(i).getTime_jindu_ids() != null) {
					time_ids = list_tags.get(i).getTime_jindu_ids();
					String[] str = time_ids.split(",");
					for (int j = 0; j < str.length; j++) {
						time_columns += tagsDao.getColumn_name_by_id(Integer
								.parseInt(str[j]))
								+ ",";
					}
					time_columns += tagsDao.getColumn_name_by_tableName("time",
							tablename);// 时间进度
				}
				if (list_tags.get(i).getYewu_jindu_ids() != null) {
					yewu_ids = list_tags.get(i).getYewu_jindu_ids();
					String[] str = yewu_ids.split(",");
					for (int j = 0; j < str.length; j++) {
						yewu_columns += tagsDao.getColumn_name_by_id(Integer
								.parseInt(str[j]))
								+ ",";
					}
					yewu_columns += tagsDao.getColumn_name_by_tableName("time",
							tablename);// 业务进度
				}
			}
			if(list_tags.get(i).getDisplay_type().equals("相关字段") && list_tags.get(i).getRelateIsShowComplete() == 1
					&&tagsDao.getProduceTableByTable(tablename) != null && !tagsDao.getProduceTableByTable(tablename).equals("")){
//				kk = tagsDao.getProduceTableByTable(tablename);
				kk = list_tags.get(i).getColumn_name();
			}
		}
		
		return "addContactTagsInitSuccess_ondemo";
	}
	
	public String updateContactTagsInitZDY() throws ElException {
		
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		if (moduleManage != null && moduleManage.getOndemo() == 1) {// 启用模板
			moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());
			//判断是否已经长传JSP模板
			if(!ZDYTemplateUtil.checkIfUploadByUploadType(tablename,ZDYTemplateUtil.ADD,moduleZDY.getUpdatejsp())){
				this.setElmessage("您还未上传修改页面的JSP,请先上传!!!");
				return "error";
			}
		}else{
			this.setElmessage("该模块还未设置开通自定义模板,请先开通!!!");
			return "error";
		}
		
		
		actionName = "updateContactTagsInit_ZDY";
		System.out.println(actionName);
		
		currentUser = tagsDao.getCurrentUser(tablename);
		
		currentUser = tagsDao.getCurrentUserByUserId(currentUser,getSessionIntValue(ElConstants.SESSION_USERID));

		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		list_tags = tagsDao.select_designe_field_content_by_id(list_tags,
				getTablename(list_tags.get(0).getColumn_name()), id);
		// id=id;
		String time_ids = "";
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				uid = String.valueOf(tagsDao.getUserIdByTablenameAndId(
						tablename, id));
				username = userDao.getUserById(
						tagsDao.getUserIdByTablenameAndId(tablename, id))
						.getRealname();
			}
			if (list_tags.get(i).getDisplay_type().equals("百分比")) {
				if (list_tags.get(i).getTime_jindu_ids() != null) {
					time_ids = list_tags.get(i).getTime_jindu_ids();
					String[] str = time_ids.split(",");
					for (int j = 0; j < str.length; j++) {
						time_columns += tagsDao.getColumn_name_by_id(Integer
								.parseInt(str[j]))
								+ ",";
					}
					time_columns += tagsDao.getColumn_name_by_tableName("time",
							tablename);// 时间进度
				}
			}
			if(list_tags.get(i).getDisplay_type().equals("相关字段") && list_tags.get(i).getRelateIsShowComplete() == 1
					&&tagsDao.getProduceTableByTable(tablename) != null && !tagsDao.getProduceTableByTable(tablename).equals("")){
//				kk = tagsDao.getProduceTableByTable(tablename);
				kk = list_tags.get(i).getColumn_name();
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
					array = new String[ids_relate.length()];
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

				//初始化更新时间进度
				TagsUtil.updateTimeJindu(list_tags.get(i).getDefault_value().split("==")[0].toUpperCase(),tagsDao);

				// 初始化更新业务进度
				TagsUtil.updateYewuJindu(list_tags.get(i).getDefault_value().split("==")[0].toUpperCase(),tagsDao,list_tags);

				// 普通表和结果表做不同处理
				// 普通表
				if (tagsDao.checkTable(list_tags.get(i).getDefault_value()
						.split("==")[0].toUpperCase()) == 1) {
					list_tags_relate.put(list_tags.get(i).getColumn_name(), tagsDao
							.select_designe_field_by_tablename(list_tags.get(i)
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
							getPageNow(), getPageSize())) ;
				}

				// 结果表
				else {
					// 查出过程表
					list_tags_relate.put(list_tags.get(i).getColumn_name(), tagsDao
							.select_designe_field_by_Producetablename(
									tablename,
									tagsDao
											.getProduceTableByResultTable(list_tags
													.get(i).getDefault_value()
													.split("==")[0]
													.toUpperCase()))) ;
					String sqlAppend = " and t.moduleid =  '" + tablename
							+ "' and t.danjuid = " + id;
					list_designe_relate.put(list_tags.get(i).getColumn_name(), tagsDao
					.select_my_tableinfo_by_userid_order(
							sqlAppend,
							1,
							list_tags_relate.get(list_tags.get(i).getColumn_name()),
							tagsDao
									.getProduceTableByResultTable(list_tags
											.get(i).getDefault_value()
											.split("==")[0]
											.toUpperCase()), hm,
							userid, order, getPageNow(), getPageSize())) ;

//					map_ = new HashMap<String, String>();
//					for (int x = 0; x < list_designe_relate.size(); x++) {
//						for (int j = 0; j < list_tags_relate.size(); j++) {
//							for (String key : list_designe_relate.get(x)
//									.keySet()) {
//								if (list_tags_relate.get(j)
//										.getBiaojianqiuhe_check() == 1
//										&& key
//												.equals(tagsDao
//														.getColumn_name_by_id(Integer
//																.parseInt(list_tags_relate
//																		.get(j)
//																		.getBiaojianqiuhe_column())))) {
//									f += Double
//											.parseDouble(list_designe_relate
//													.get(x)
//													.get(
//															tagsDao
//																	.getColumn_name_by_id(Integer
//																			.parseInt(list_tags_relate
//																					.get(
//																							j)
//																					.getBiaojianqiuhe_column()))));
//									map_
//											.put(
//													"1",
//													tagsDao
//															.getColumn_name_by_id(Integer
//																	.parseInt(list_tags_relate
//																			.get(
//																					j)
//																			.getBiaojianqiuhe_column())));
//									System.out.println(map_);
//								}
//							}
//						}
//					}

				}
			}
		}


		return "updateContactTagsInitSuccess_ondemo";
	}
	
	public String viewContactTagsZDY() throws ElException {
		moduleManage = moduleManageDao.select_module_by_TableName(tablename);
		if (moduleManage != null && moduleManage.getOndemo() == 1) {// 启用模板
			moduleZDY = clientTemplateDao.select_moduleZDY_by_moduleid(moduleManage.getId());
			//判断是否已经长传JSP模板
			if(!ZDYTemplateUtil.checkIfUploadByUploadType(tablename,ZDYTemplateUtil.ADD,moduleZDY.getViewjsp())){
				this.setElmessage("您还未上传查看页面的JSP,请先上传!!!");
				return "error";
			}
		}else{
			this.setElmessage("该模块还未设置开通自定义模板,请先开通!!!");
			return "error";
		}
		
		actionName = "viewContactTags_ZDY";
		
		currentUser = tagsDao.getCurrentUser(tablename);
		
		currentUser = tagsDao.getCurrentUserByUserId(currentUser,getSessionIntValue(ElConstants.SESSION_USERID));
		
		list_tags = tagsDao.select_designe_field_by_tablename(tablename);
		list_tags = tagsDao.select_designe_field_content_by_id(list_tags,
				getTablename(list_tags.get(0).getColumn_name()), id);

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
			if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				uid = String.valueOf(tagsDao.getUserIdByTablenameAndId(
						tablename, id));
				username = userDao.getUserById(
						tagsDao.getUserIdByTablenameAndId(tablename, id))
						.getRealname();
			}
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

				//初始化更新时间进度
				TagsUtil.updateTimeJindu(list_tags.get(i).getDefault_value().split("==")[0].toUpperCase(),tagsDao);

				// 初始化更新业务进度
				TagsUtil.updateYewuJindu(list_tags.get(i).getDefault_value().split("==")[0].toUpperCase(),tagsDao,list_tags);

				// 普通表和结果表做不同处理
				// 普通表
				if (tagsDao.checkTable(list_tags.get(i).getDefault_value()
						.split("==")[0].toUpperCase()) == 1) {
					list_tags_relate.put(list_tags.get(i).getColumn_name(), tagsDao
							.select_designe_field_by_tablename(list_tags.get(i)
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
					list_tags_relate.put(list_tags.get(i).getColumn_name(), tagsDao
							.select_designe_field_by_Producetablename(
									tablename,
									tagsDao
											.getProduceTableByResultTable(list_tags
													.get(i).getDefault_value()
													.split("==")[0]
													.toUpperCase()))) ;
					String sqlAppend = " and t.moduleid =  '" + tablename
							+ "' and t.danjuid = " + id;
					list_designe_relate.put(list_tags.get(i).getColumn_name(), tagsDao
							.select_my_tableinfo_by_userid_order(
									sqlAppend,
									1,
									list_tags_relate.get(list_tags.get(i).getColumn_name()),
									tagsDao
											.getProduceTableByResultTable(list_tags
													.get(i).getDefault_value()
													.split("==")[0]
													.toUpperCase()), hm,
									userid, order, getPageNow(), getPageSize()));

//					for (int x = 0; x < list_designe_relate.size(); x++) {
//						for (int j = 0; j < list_tags_relate.size(); j++) {
//							for (String key : list_designe_relate.get(x)
//									.keySet()) {
//								if (list_tags_relate.get(j)
//										.getBiaojianqiuhe_check() == 1
//										&& key
//												.equals(tagsDao
//														.getColumn_name_by_id(Integer
//																.parseInt(list_tags_relate
//																		.get(j)
//																		.getBiaojianqiuhe_column())))) {
//									f += Double
//											.parseDouble(list_designe_relate
//													.get(x)
//													.get(
//															tagsDao
//																	.getColumn_name_by_id(Integer
//																			.parseInt(list_tags_relate
//																					.get(
//																							j)
//																					.getBiaojianqiuhe_column()))));
//								}
//							}
//						}
//					}
				}
			}
		}


		return "viewContactTagsSuccess_ondemo";
	}
	
	
	//////////////////////////////////////////////////////////////
	/*
	 * getters and setters
	 */

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ClientDao getClientDao() {
		return clientDao;
	}

	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public List<Client> getList_client() {
		return list_client;
	}

	public void setList_client(List<Client> list_client) {
		this.list_client = list_client;
	}

	public List<Clientlinkman> getList_clientlinkman() {
		return list_clientlinkman;
	}

	public void setList_clientlinkman(List<Clientlinkman> list_clientlinkman) {
		this.list_clientlinkman = list_clientlinkman;
	}

	public String getLinkmanids() {
		return linkmanids;
	}

	public void setLinkmanids(String linkmanids) {
		this.linkmanids = linkmanids;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Contactstuff getContactstuff() {
		return contactstuff;
	}

	public void setContactstuff(Contactstuff contactstuff) {
		this.contactstuff = contactstuff;
	}

	public List<Contact> getList_contact() {
		return list_contact;
	}

	public void setList_contact(List<Contact> list_contact) {
		this.list_contact = list_contact;
	}

	public List<Contactstuff> getList_contactstuff() {
		return list_contactstuff;
	}

	public void setList_contactstuff(List<Contactstuff> list_contactstuff) {
		this.list_contactstuff = list_contactstuff;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<Map<String, String>> getList_designe() {
		return list_designe;
	}

	public void setList_designe(List<Map<String, String>> list_designe) {
		this.list_designe = list_designe;
	}

	public Map<String, List<Map<String, String>>> getList_designe_relate() {
		return list_designe_relate;
	}

	public void setList_designe_relate(
			Map<String, List<Map<String, String>>> list_designe_relate) {
		this.list_designe_relate = list_designe_relate;
	}

	public Tags getTags() {
		return tags;
	}

	public void setTags(Tags tags) {
		this.tags = tags;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Clientlinkcontact> getList_num_sum() {
		return list_num_sum;
	}

	public void setList_num_sum(List<Clientlinkcontact> list_num_sum) {
		this.list_num_sum = list_num_sum;
	}

	public String getCheckRichtext() {
		return checkRichtext;
	}

	public void setCheckRichtext(String checkRichtext) {
		this.checkRichtext = checkRichtext;
	}

	public ModuleManage getModuleManage() {
		return moduleManage;
	}

	public void setModuleManage(ModuleManage moduleManage) {
		this.moduleManage = moduleManage;
	}

	public List<Eluser> getList_eluser() {
		return list_eluser;
	}

	public void setList_eluser(List<Eluser> list_eluser) {
		this.list_eluser = list_eluser;
	}

	public int getRx() {
		return rx;
	}

	public void setRx(int rx) {
		this.rx = rx;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getDisplay_type() {
		return display_type;
	}

	public void setDisplay_type(String display_type) {
		this.display_type = display_type;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getParameters_() {
		return parameters_;
	}

	public void setParameters_(String parameters_) {
		this.parameters_ = parameters_;
	}

	public String getEx_columnname() {
		return ex_columnname;
	}

	public void setEx_columnname(String ex_columnname) {
		this.ex_columnname = ex_columnname;
	}

	public CurrentUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(CurrentUser currentUser) {
		this.currentUser = currentUser;
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

	public TagsMark getTagsMark() {
		return tagsMark;
	}

	public void setTagsMark(TagsMark tagsMark) {
		this.tagsMark = tagsMark;
	}

	public UserSign getUserSign() {
		return userSign;
	}

	public void setUserSign(UserSign userSign) {
		this.userSign = userSign;
	}

	public String getFromActionName() {
		return fromActionName;
	}

	public void setFromActionName(String fromActionName) {
		this.fromActionName = fromActionName;
	}

	public void setInputStream1(InputStream inputStream1) {
		this.inputStream1 = inputStream1;
	}
	
	public void del_check() throws ElException{
		getResponse().setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter localPrintWriter = getResponse().getWriter();
		
			if (ids != null && !ids.equals("")) {
				String[] ids_array = ids.split(",");
				for (int i = 0; i < ids_array.length; i++) {
					if (Integer.parseInt(ids_array[i]) != 0)
						tagsDao.allow_del(tablename, Integer.parseInt(ids_array[i]));
				}
			}
			localPrintWriter.print("删除成功！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String del_search() throws ElException{
		if (ids != null && !ids.equals("")) {
			String[] ids_array = ids.split(",");
			for (int i = 0; i < ids_array.length; i++) {
				if (Integer.parseInt(ids_array[i]) != 0)
					tagsDao.allow_del(tags.getTable_name(), Integer.parseInt(ids_array[i]));
			}
		}
		return "del_search";
	}
	
	//-------wsj20131118修改-----------------------------------------
	/**
	 * 更新培训记录
	 */
	public String updateTrainStatus() throws ElException{
		updateTrain();
		return "updateTrainStatus";
	}
	
	public boolean updateTrain(){
		try {
			clientDao.updateTrainStatus();
		} catch (ElException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 培训状况一览
	 */
	public String trainStatusList() throws ElException{
		trainCount = clientDao.trainStatusListCount();
		 nocertificateno = clientDao.nocertificatenoListCount();
		 haspaymoney = clientDao.haspaymoneyListCount();
	     hasregister = clientDao.hasregisterListCount();
		 hascertificateno = clientDao.hascertificatenoListCount();
		return "trainStatusList";
	}
	
	public String nocertificateno() throws ElException{
		nocertificatenoList = clientDao.nocertificatenoList(trainStatus, getPageNow(), getPageSize());
		count = clientDao.nocertificatenoListCount();
		return "nocertificateno";
	}
	
	public String hascertificateno() throws ElException{
		hascertificatenoList = clientDao.hascertificatenoList(trainStatus, getPageNow(), getPageSize());
		count = clientDao.hascertificatenoListCount();
		return "hascertificateno";
	}
	
	public String hasregister() throws ElException{
		hasregisterList = clientDao.hasregisterList(trainStatus, getPageNow(), getPageSize());
		count = clientDao.hasregisterListCount();
		return "hasregister";
	}
	
	public String haspaymoney() throws ElException{
		haspaymoneyList = clientDao.haspaymoneyList(trainStatus, getPageNow(), getPageSize());
		count = clientDao.haspaymoneyListCount();
		return "haspaymoney";
	}
	
	/**
	 * 培训状况进度一览
	 */
	public String trainStatusPlan() throws ElException{
		 isSixMonths  = clientDao.isSixMonthsListCount();
		 isThreeMonths = clientDao.isThreeMonthsListCount();
		 isOneMonths = clientDao.isOneMonthsListCount();
		 isHalfMonths = clientDao.isHalfMonthsListCount();
		 isOneWeek = clientDao.isOneMonthsListCount();
		 isValid = clientDao.isValidListCount();
		return "trainStatusPlan";
	}

	
	public String isSixMonths() throws ElException{
		isSixMonthsList = clientDao.isSixMonthsList(trainStatus, getPageNow(), getPageSize());
		count = clientDao.isSixMonthsListCount();
		return "isSixMonths";
	}
	
	public String isThreeMonths() throws ElException{
		isThreeMonthsList = clientDao.isThreeMonthsList(trainStatus, getPageNow(), getPageSize());
		count = clientDao.isThreeMonthsListCount();
		return "isThreeMonths";
	}
	
	public String isOneMonths() throws ElException{
		isOneMonthsList = clientDao.isOneMonthsList(trainStatus, getPageNow(), getPageSize());
		count = clientDao.isOneMonthsListCount();
		return "isOneMonths";
	}
	
	public String isHalfMonths() throws ElException{
		isHalfMonthsList = clientDao.isHalfMonthsList(trainStatus, getPageNow(), getPageSize());
		count = clientDao.isHalfMonthsListCount();
		return "isHalfMonths";
	}
	
	public String isOneWeek() throws ElException{
		isOneWeekList = clientDao.isOneWeekList(trainStatus, getPageNow(), getPageSize());
		count = clientDao.isOneWeekListCount();
		return "isOneWeek";
	}
	
	public String isValid() throws ElException{
		isValidList = clientDao.isValidList(trainStatus, getPageNow(), getPageSize());
		count = clientDao.isValidListCount();
		return "isValid";
	}

	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}

	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}

	public int getTrainType() {
		return trainType;
	}

	public void setTrainType(int trainType) {
		this.trainType = trainType;
	}

	public int getNocertificateno() {
		return nocertificateno;
	}

	public void setNocertificateno(int nocertificateno) {
		this.nocertificateno = nocertificateno;
	}

	public int getHaspaymoney() {
		return haspaymoney;
	}

	public void setHaspaymoney(int haspaymoney) {
		this.haspaymoney = haspaymoney;
	}

	public int getHasregister() {
		return hasregister;
	}

	public void setHasregister(int hasregister) {
		this.hasregister = hasregister;
	}

	public int getHascertificateno() {
		return hascertificateno;
	}

	public void setHascertificateno(int hascertificateno) {
		this.hascertificateno = hascertificateno;
	}

	public int getTrainCount() {
		return trainCount;
	}

	public void setTrainCount(int trainCount) {
		this.trainCount = trainCount;
	}

	public TrainingStatus getTrainStatus() {
		return trainStatus;
	}

	public void setTrainStatus(TrainingStatus trainStatus) {
		this.trainStatus = trainStatus;
	}

	public List<TrainingStatus> getNocertificatenoList() {
		return nocertificatenoList;
	}

	public void setNocertificatenoList(List<TrainingStatus> nocertificatenoList) {
		this.nocertificatenoList = nocertificatenoList;
	}

	public List<TrainingStatus> getHaspaymoneyList() {
		return haspaymoneyList;
	}

	public void setHaspaymoneyList(List<TrainingStatus> haspaymoneyList) {
		this.haspaymoneyList = haspaymoneyList;
	}

	public List<TrainingStatus> getHasregisterList() {
		return hasregisterList;
	}

	public void setHasregisterList(List<TrainingStatus> hasregisterList) {
		this.hasregisterList = hasregisterList;
	}

	public List<TrainingStatus> getHascertificatenoList() {
		return hascertificatenoList;
	}

	public void setHascertificatenoList(List<TrainingStatus> hascertificatenoList) {
		this.hascertificatenoList = hascertificatenoList;
	}

	public List<TrainingStatus> getTrainCountList() {
		return trainCountList;
	}

	public void setTrainCountList(List<TrainingStatus> trainCountList) {
		this.trainCountList = trainCountList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getIsSixMonths() {
		return isSixMonths;
	}

	public void setIsSixMonths(int isSixMonths) {
		this.isSixMonths = isSixMonths;
	}

	public int getIsThreeMonths() {
		return isThreeMonths;
	}

	public void setIsThreeMonths(int isThreeMonths) {
		this.isThreeMonths = isThreeMonths;
	}

	public int getIsOneMonths() {
		return isOneMonths;
	}

	public void setIsOneMonths(int isOneMonths) {
		this.isOneMonths = isOneMonths;
	}

	public int getIsHalfMonths() {
		return isHalfMonths;
	}

	public void setIsHalfMonths(int isHalfMonths) {
		this.isHalfMonths = isHalfMonths;
	}

	public int getIsOneWeek() {
		return isOneWeek;
	}

	public void setIsOneWeek(int isOneWeek) {
		this.isOneWeek = isOneWeek;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public List<TrainingStatus> getIsSixMonthsList() {
		return isSixMonthsList;
	}

	public void setIsSixMonthsList(List<TrainingStatus> isSixMonthsList) {
		this.isSixMonthsList = isSixMonthsList;
	}

	public List<TrainingStatus> getIsThreeMonthsList() {
		return isThreeMonthsList;
	}

	public void setIsThreeMonthsList(List<TrainingStatus> isThreeMonthsList) {
		this.isThreeMonthsList = isThreeMonthsList;
	}

	public List<TrainingStatus> getIsOneMonthsList() {
		return isOneMonthsList;
	}

	public void setIsOneMonthsList(List<TrainingStatus> isOneMonthsList) {
		this.isOneMonthsList = isOneMonthsList;
	}

	public List<TrainingStatus> getIsHalfMonthsList() {
		return isHalfMonthsList;
	}

	public void setIsHalfMonthsList(List<TrainingStatus> isHalfMonthsList) {
		this.isHalfMonthsList = isHalfMonthsList;
	}

	public List<TrainingStatus> getIsOneWeekList() {
		return isOneWeekList;
	}

	public void setIsOneWeekList(List<TrainingStatus> isOneWeekList) {
		this.isOneWeekList = isOneWeekList;
	}

	public List<TrainingStatus> getIsValidList() {
		return isValidList;
	}

	public void setIsValidList(List<TrainingStatus> isValidList) {
		this.isValidList = isValidList;
	}
	

}
