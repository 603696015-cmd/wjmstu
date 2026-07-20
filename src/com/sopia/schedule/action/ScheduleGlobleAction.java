package com.sopia.schedule.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.schedule.dao.ScheduleGlobleDao;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.entities.Gzrz;
import com.sopia.schedule.entities.Kehu;
import com.sopia.schedule.entities.Production_efficiency;
import com.sopia.schedule.entities.Tags;
import com.sopia.schedule.entities.Wupin;
import com.sopia.schedule.entities.Xiangmu;

public class ScheduleGlobleAction extends BaseAction{
	
	private ScheduleGlobleDao scheduleGlobleDao;
	private TagsDao tagsDao;
	private String tablename;
	private List<Kehu> KHDA_KHJDList;
	
	private Timestamp starttime;
	private Timestamp endtime;
	private double shouru_heji;//收入合计
	private double zhichu_heji;//支出合计
	private String sk_tablename;
	private String fk_tablename;
	private int type;
	private List<Tags> list_tags = new ArrayList<Tags>();
	private List<Map<String, String>> list_designe = new ArrayList<Map<String, String>>();
	private Tags tags;
	
	
	private List<Gzrz> gzrzList;
	private String orderBy;
	private String ordersc;
	
	private int userid;
	private String realname;
	
	private List<Wupin> wupinList;
	private List<String> cangkuList;
	private List<Map<String,String>> moduleList;
	private int merger;
	
	private Department depTree;
	private Department department;
	private int sub_department;
	private List<Map<String,Object>> list_map;
	private Xiangmu xiangmu;
	
	private ELUser elUser;
	
	private int id;
	private Map<String, List<Map<String, Object>>> map_list_map;
	///////////////////////////////////
	
	private List<Map<String,Object>> kehu_ayalysis;
	
	private Map<String,List<Production_efficiency>> production_efficiency;
	
	
	private List<Map<String,Object>> material_requirements;
	private double zongjia = 0;

	
	public double getZongjia() {
		return zongjia;
	}

	public void setZongjia(double zongjia) {
		this.zongjia = zongjia;
	}

	public List<Map<String, Object>> getMaterial_requirements() {
		return material_requirements;
	}

	public void setMaterial_requirements(
			List<Map<String, Object>> material_requirements) {
		this.material_requirements = material_requirements;
	}

	public Map<String, List<Production_efficiency>> getProduction_efficiency() {
		return production_efficiency;
	}

	public void setProduction_efficiency(
			Map<String, List<Production_efficiency>> production_efficiency) {
		this.production_efficiency = production_efficiency;
	}

	/**
	 * 项目核算
	 * @return
	 * @throws ElException
	 */
	public String xiangmu_accounting() throws ElException{
		tablename="XMDA";
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else
		{
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		
		if (department == null || department.getId() <= 0)
		{
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		list_map = scheduleGlobleDao.getAccounting(tablename,getPageNow(), getPageSize(),xiangmu,department);
		count = scheduleGlobleDao.getAccountingCount(tablename,xiangmu,department);
		for(Map<String,Object> map:list_map){
			map.put("xiangmushouru", scheduleGlobleDao.getHeByTablenameColumn("SK","SK_SKJE","SK_XMMC",(Integer)map.get("id")));
			map.put("xiangmuzhichu", scheduleGlobleDao.getHeByTablenameColumn("FK","FK_FKJE","FK_XMMC",(Integer)map.get("id")));
		}
		
		Map<String,Object> m = new HashMap<String,Object>();
		double all_xiangmushouru = 0.0;
		double all_xiangmuzhichu = 0.0;
		double all_qitashouru = 0.0;
		double all_xiangguanzhichu = 0.0;
		for(Map<String,Object> map:list_map){
			all_xiangmushouru += (Double)map.get("xiangmushouru");
			all_xiangmuzhichu += (Double)map.get("xiangmuzhichu");
			all_qitashouru += (Double)map.get("qitashouru");
			all_xiangguanzhichu += (Double)map.get("xiangguanzhichu");
		}
		
		m.put("xiangmumingcheng", "合计");
		m.put("xiangmushouru", all_xiangmushouru);
		m.put("xiangmuzhichu", all_xiangmuzhichu);
		m.put("qitashouru", all_qitashouru);
		m.put("xiangguanzhichu", all_xiangguanzhichu);
		list_map.add(m);
		return "xiangmu_accounting";
	}
	
	/**
	 * 项目核算==>查看
	 * @return
	 * @throws ElException
	 */
	public String viewRelateDanju() throws ElException{
		//id
		//收款单==SK;付款单==FK;应收单==YS；应付单==YF;其他收入==QTSR;费用支出==FYZC
		
		map_list_map = scheduleGlobleDao.viewRelateDanju(id);
		
		return "viewRelateDanju";
	}
	
	/**
	 * 客户分析一览
	 */
	public String kehu_analysis() throws ElException{
		//tablename==KHDA
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else
		{
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		
		if (department == null || department.getId() <= 0)
		{
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(getRequest().getParameter("kehuname") != null && !((String)getRequest().getParameter("kehuname")).equals("")){
			map.put("kehuname", (String)getRequest().getParameter("kehuname"));
		}
		if(getRequest().getParameter("jieduan") != null && !((String)getRequest().getParameter("jieduan")).equals("")){
			map.put("jieduan", (String)getRequest().getParameter("jieduan"));
		}
		if(getRequest().getParameter("diqu") != null && !((String)getRequest().getParameter("diqu")).equals("")){
			map.put("diqu", (String)getRequest().getParameter("diqu"));
		}
		if(getRequest().getParameter("xingzhi") != null && !((String)getRequest().getParameter("xingzhi")).equals("")){
			map.put("xingzhi", (String)getRequest().getParameter("xingzhi"));
		}
		if(getRequest().getParameter("lirun_begin") != null && !((String)getRequest().getParameter("lirun_begin")).equals("")){
			map.put("lirun_begin", Double.parseDouble((String)getRequest().getParameter("lirun_begin")));
		}
		if(getRequest().getParameter("lirun_end") != null && !((String)getRequest().getParameter("lirun_end")).equals("")){
			map.put("lirun_end", Double.parseDouble((String)getRequest().getParameter("lirun_end")));
		}
		if(getRequest().getParameter("shoukuan_begin") != null && !((String)getRequest().getParameter("shoukuan_begin")).equals("")){
			map.put("shoukuan_begin", Double.parseDouble((String)getRequest().getParameter("shoukuan_begin")));
		}
		if(getRequest().getParameter("shoukuan_end") != null && !((String)getRequest().getParameter("shoukuan_end")).equals("")){
			map.put("shoukuan_end", Double.parseDouble((String)getRequest().getParameter("shoukuan_end")));
		}
		
		kehu_ayalysis = scheduleGlobleDao.getKehuAnalysis(tablename,getPageNow(), getPageSize(),map,department);
		count = scheduleGlobleDao.getKehuAnalysisSize(tablename,map,department);
		return "kehu_analysis";
	}
	
	/**
	 * 客户结果查询
	 * @return
	 * @throws ElException
	 */
	public String kehujieguo_query() throws ElException{
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
		
//		KHDA_KHJDList = scheduleGlobleDao.getKHDA_KHJDList(tablename);
		KHDA_KHJDList = KHDA_KHJDList == null?new ArrayList<Kehu>():KHDA_KHJDList;
		
		KHDA_KHJDList.add(new Kehu("登记客户"));
		KHDA_KHJDList.add(new Kehu("新建客户"));
		KHDA_KHJDList.add(new Kehu("初步联系"));
		KHDA_KHJDList.add(new Kehu("联系中客户"));
		KHDA_KHJDList.add(new Kehu("意向客户"));
		KHDA_KHJDList.add(new Kehu("正式客户"));
		KHDA_KHJDList.add(new Kehu("老客户"));
		KHDA_KHJDList.add(new Kehu("VIP客户"));
		
		
		List<Kehu> kehuList = scheduleGlobleDao.getKehuList(tablename,department);
		kehuList = kehuList == null?new ArrayList<Kehu>():kehuList;
		int numberAll = 0;
		double KHDA_YQJEAll = 0.0;
		double KHDA_SJJEAll = 0.0;
		
		double value = 0.0;
		for(Kehu kehu_:KHDA_KHJDList){
			if(kehu_.getKHDA_KHJD() != null ){
				if(!kehu_.getKHDA_KHJD().equals("登记客户")){
					for(Kehu kehu:kehuList){
						if(kehu.getKHDA_KHJD() != null ){
							if(kehu_.getKHDA_KHJD().equals(kehu.getKHDA_KHJD()) ){
								kehu_.setNumber(kehu_.getNumber() + 1);
								kehu_.setKHDA_YQJE(kehu_.getKHDA_YQJE() + kehu.getKHDA_YQJE());
								kehu_.setKHDA_SJJE(kehu_.getKHDA_SJJE() + kehu.getKHDA_SJJE());
							}
						}
					}
					KHDA_YQJEAll += kehu_.getKHDA_YQJE();
					KHDA_SJJEAll += kehu_.getKHDA_SJJE();
					numberAll += kehu_.getNumber();
				}else {
					//登记客户//tablename==KHDJB
					kehu_.setNumber(scheduleGlobleDao.getKehuCountByDengji("KHDJB"));
					numberAll += kehu_.getNumber();
				}
			}
		}
		

		
		for(Kehu kehu_:KHDA_KHJDList){
			if(numberAll == 0){
				kehu_.setBili1(0);
			}else {
				int a = kehu_.getNumber();
				int b = numberAll;
				kehu_.setBili1(Double.valueOf(new DecimalFormat("#.00").format((double)a/b * 100)));
			}
			if(KHDA_YQJEAll == 0.0){
				kehu_.setBili2(0);
			}else {
				BigDecimal bg = new BigDecimal(kehu_.getKHDA_YQJE() / KHDA_YQJEAll * 100); 
				value = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				kehu_.setBili2(value);
			}
			if(KHDA_SJJEAll == 0.0){
				kehu_.setBili3(0);
			}else {
				BigDecimal bg = new BigDecimal(kehu_.getKHDA_SJJE() / KHDA_SJJEAll * 100); 
				value = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				kehu_.setBili3(value);
			}
		}
		
		//合计
		Kehu kehu = new Kehu();
		kehu.setKHDA_KHJD("合计");
		kehu.setKHDA_SJJE(KHDA_SJJEAll);
		kehu.setKHDA_YQJE(KHDA_YQJEAll);
		kehu.setNumber(numberAll);
		kehu.setBili1(100);
		kehu.setBili2(100);
		kehu.setBili3(100);
		KHDA_KHJDList.add(kehu);
		
		System.out.println(KHDA_KHJDList.size());
		return "kehujieguo_query";
	}
	
	/**
	 * 财务一览表
	 * @return
	 * @throws ElException
	 */
	public String caiwu_query() throws ElException{
		//starttime、endtime
		sk_tablename = "SK";
		fk_tablename = "FK";
		
		shouru_heji = scheduleGlobleDao.gethejiByTablename(sk_tablename,starttime,endtime);
		zhichu_heji = scheduleGlobleDao.gethejiByTablename(fk_tablename,starttime,endtime);
		
		
		return "caiwu_query";
	}
	
	/**
	 * 财务一览表详情
	 * @return
	 * @throws ElException
	 */
	public String caiwu_view() throws ElException{
		System.out.println(type);
		Map<String, String> hm = new HashMap<String, String>();
		list_tags = tagsDao
			.select_designe_field_by_tablename(tablename);
		
		
		String order="";
		if(tags!=null)
		{
			if(tags.getOrdercolumn()!=null&&!tags.getOrdercolumn().equals(""))
			{
				order =" order by ";
				order +=tags.getOrdercolumn()+" ";
				order +=tags.getOrdersc();
			}
		}
		
		// 组合搜索获取搜索条件
		for (int i = 0; i < list_tags.size(); i++)
		{
			//相关字段
			if(list_tags.get(i).getDisplay_type().equals("相关字段"))
			{
				String str_relate = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name());
				if (str_relate != null && !str_relate.equals(""))
				{
					String arr[]=list_tags.get(i).getDefault_value().split("==");//如tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
					//relate_type==目标表名==目标列名==列名 条件
					hm.put("relate_type" + "=="+arr[0]+"=="+arr[1]+"=="
							+ list_tags.get(i).getColumn_name(), str_relate);
					list_tags.get(i).setValue2(str_relate);//将搜索条件传回前端
				}
				continue;
			}
			//相关负责人
			else if(list_tags.get(i).getDisplay_type().equals("相关负责人"))
			{
				String str_relate = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name());
				String str_rx = (String) getRequest().getParameter("rx");
				if(str_relate!=null&&str_rx!=null)
				 str_relate =(String)getRequest().getParameter(list_tags.get(i).getColumn_name());
				if (str_relate != null && !str_relate.equals(""))
				{
				//	String arr[]=list_tags.get(i).getDefault_value().split("==");//如tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
					//relate_type==目标表名==目标列名==列名 条件
					hm.put("relate_type" + "==eluser==realname=="
							+ list_tags.get(i).getColumn_name(), str_relate);
					list_tags.get(i).setValue2(str_relate);//将搜索条件传回前端
				}
				continue;
			}
			String str = (String) getRequest().getParameter(
					list_tags.get(i).getColumn_name());
			if (str != null && !str.equals(""))
			{
				hm.put(list_tags.get(i).getColumn_type() + "=="
						+ list_tags.get(i).getColumn_name(), str);
				list_tags.get(i).setValue(str);//将搜索条件传回前端
			}
			//日期
			if (list_tags.get(i).getColumn_type().equals("date"))
			{
				String str2 = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name() + "_");
				if (str2 != null && !str2.equals(""))
				{
					hm.put(list_tags.get(i).getColumn_type() + "=="
							+ list_tags.get(i).getColumn_name() + "_", str2);
					list_tags.get(i).setValue2(str2);
				}
			}
			//数字
			if(list_tags.get(i).getColumn_type().equals("number")||
					list_tags.get(i).getColumn_type().equals("float"))
			{
				String str2 = (String) getRequest().getParameter(
						list_tags.get(i).getColumn_name() + "_");
				if (str2 != null && !str2.equals(""))
				{
					hm.put(list_tags.get(i).getColumn_type() + "=="
							+ list_tags.get(i).getColumn_name() + "_", str2);
					list_tags.get(i).setValue2(str2);
				}
			}
			
			
		}
		
		userid = getSessionIntValue(ElConstants.SESSION_USERID);
		realname = tagsDao.get_eluser_realname_by_id(userid);
		
		
		list_designe = scheduleGlobleDao.select_my_tableinfo_by_userid_order("",0,list_tags,
				tablename, hm, userid,order,
				getPageNow(), getPageSize(),type);
		count = scheduleGlobleDao.select_my_tableinfo_by_userid_count(0,list_tags, hm,
				tablename, userid,type);
		return "caiwu_view";
	}
	
	/**
	 * 日志评分统计
	 * @return
	 * @throws ElException
	 */
	public String log_statistics() throws ElException{
		//tablename
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else
		{
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		
		if (department == null || department.getId() <= 0)
		{
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		gzrzList = scheduleGlobleDao.getGzrzList(elUser,starttime,endtime,tablename,orderBy,ordersc,department,getPageNow(), getPageSize());
		count = scheduleGlobleDao.getGzrzListCount(elUser,starttime,endtime,tablename,department);
		for(Gzrz g:gzrzList){
			if(g.getLeader() != 0 && g.getByme() != 0)
			g.setCha1(g.getLeader() - g.getByme());//自我评分-部门评分
//			g.setCha2(Math.abs(g.getByme() - g.getLeader()));//自我评分-领导评分
//			g.setCha3(Math.abs(g.getLeader() - g.getBydep()));//领导评分-部门评分
		}
		return "log_statistics";
	}
	
	public String wupin_center() throws ElException{
		Map<String,Object> map = new HashMap<String,Object>();
		if(getRequest().getParameter("moduleName") != null && !((String)getRequest().getParameter("moduleName")).equals("请选择模块")){
			map.put("moduleName", (String)getRequest().getParameter("moduleName"));
		}
		if(getRequest().getParameter("status") != null && !((String)getRequest().getParameter("status")).equals("请选择状态")){
			map.put("status", (String)getRequest().getParameter("status"));
		}
		if(getRequest().getParameter("cangkuname") != null && !((String)getRequest().getParameter("cangkuname")).equals("请选择仓库")){
			map.put("cangkuname", (String)getRequest().getParameter("cangkuname"));
		}
		if(getRequest().getParameter("wupinname") != null && !((String)getRequest().getParameter("wupinname")).equals("")){
			map.put("wupinname", (String)getRequest().getParameter("wupinname"));
		}
		if(starttime != null){
			map.put("starttime", starttime);
		}
		if(endtime != null){
			map.put("endtime", endtime);
		}
			
			
		cangkuList = scheduleGlobleDao.getCangkuList(tablename);
//		moduleList = scheduleGlobleDao.getModuleList(tablename);
		moduleList = scheduleGlobleDao.getModuleMap(tablename);
		//tablename==WPGCB2
		if(merger == 1){//合并仓库
			wupinList = scheduleGlobleDao.getWupinList(tablename,getPageNow(), getPageSize(),new String(),map);
			count = scheduleGlobleDao.getWupinListSize(tablename,new String(),map);
		}else {
			wupinList = scheduleGlobleDao.getWupinList(tablename,getPageNow(), getPageSize(),null,map);
			count = scheduleGlobleDao.getWupinListSize(tablename,null,map);
			
		}
		Wupin wupin = scheduleGlobleDao.getQiuheWupin(tablename);
		
		wupin.setWupinname("合计");
		wupinList.add(wupin);
		
		return "wupin_center";
	}
	
	/**
	 * 首页配置
	 * @return
	 * @throws ElException
	 */
	public String indexSetUpInit() throws ElException{
		return "indexSetUpInit_success";
	}
	
	/**
	 * 生产效益一览表
	 * @return
	 * @throws ElException
	 */
	public String production_efficiency() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else
		{
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		
		if (department == null || department.getId() <= 0)
		{
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(starttime != null){
			map.put("starttime", starttime);
		}
		if(endtime != null){
			map.put("endtime", endtime);
		}
		
		production_efficiency = scheduleGlobleDao.getProduction_efficiency(getPageNow(), getPageSize(),map);
		count = scheduleGlobleDao.getProduction_efficiency_size(map);
		return "production_efficiency";
	}
	
	/**
	 * 物料需求一览表
	 * @return
	 * @throws ElException
	 */
	public String material_requirements() throws ElException{
		material_requirements = scheduleGlobleDao.getMaterial_requirements(getPageNow(), getPageSize());
		count =  scheduleGlobleDao.getMaterial_requirements_size();
		
		for(Map<String,Object> map:material_requirements){
			zongjia += (Double)map.get("dpzj");
		}
		return "material_requirements";
	}
	
	public ScheduleGlobleDao getScheduleGlobleDao() {
		return scheduleGlobleDao;
	}

	public void setScheduleGlobleDao(ScheduleGlobleDao scheduleGlobleDao) {
		this.scheduleGlobleDao = scheduleGlobleDao;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<Kehu> getKHDA_KHJDList() {
		return KHDA_KHJDList;
	}

	public void setKHDA_KHJDList(List<Kehu> list) {
		KHDA_KHJDList = list;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public double getShouru_heji() {
		return shouru_heji;
	}

	public void setShouru_heji(double shouru_heji) {
		this.shouru_heji = shouru_heji;
	}

	public double getZhichu_heji() {
		return zhichu_heji;
	}

	public void setZhichu_heji(double zhichu_heji) {
		this.zhichu_heji = zhichu_heji;
	}

	public String getSk_tablename() {
		return sk_tablename;
	}

	public void setSk_tablename(String sk_tablename) {
		this.sk_tablename = sk_tablename;
	}

	public String getFk_tablename() {
		return fk_tablename;
	}

	public void setFk_tablename(String fk_tablename) {
		this.fk_tablename = fk_tablename;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public TagsDao getTagsDao() {
		return tagsDao;
	}

	public void setTagsDao(TagsDao tagsDao) {
		this.tagsDao = tagsDao;
	}

	public List<Gzrz> getGzrzList() {
		return gzrzList;
	}

	public void setGzrzList(List<Gzrz> gzrzList) {
		this.gzrzList = gzrzList;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Tags getTags() {
		return tags;
	}

	public void setTags(Tags tags) {
		this.tags = tags;
	}

	public String getOrdersc() {
		return ordersc;
	}

	public void setOrdersc(String ordersc) {
		this.ordersc = ordersc;
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

	public List<Wupin> getWupinList() {
		return wupinList;
	}

	public void setWupinList(List<Wupin> wupinList) {
		this.wupinList = wupinList;
	}


	public List<String> getCangkuList() {
		return cangkuList;
	}

	public void setCangkuList(List<String> cangkuList) {
		this.cangkuList = cangkuList;
	}

	public int getMerger() {
		return merger;
	}

	public void setMerger(int merger) {
		this.merger = merger;
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

	public List<Map<String, Object>> getList_map() {
		return list_map;
	}

	public void setList_map(List<Map<String, Object>> list_map) {
		this.list_map = list_map;
	}

	public Xiangmu getXiangmu() {
		return xiangmu;
	}

	public void setXiangmu(Xiangmu xiangmu) {
		this.xiangmu = xiangmu;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public List<Map<String, String>> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<Map<String, String>> moduleList) {
		this.moduleList = moduleList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<String, List<Map<String, Object>>> getMap_list_map() {
		return map_list_map;
	}

	public void setMap_list_map(Map<String, List<Map<String, Object>>> map_list_map) {
		this.map_list_map = map_list_map;
	}

	public List<Map<String, Object>> getKehu_ayalysis() {
		return kehu_ayalysis;
	}

	public void setKehu_ayalysis(List<Map<String, Object>> kehu_ayalysis) {
		this.kehu_ayalysis = kehu_ayalysis;
	}



	
}
