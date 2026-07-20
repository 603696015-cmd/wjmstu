package com.sopia.schedule.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.sopia.schedule.entities.Gzrz;
import com.sopia.schedule.entities.Kehu;
import com.sopia.schedule.entities.Production_efficiency;
import com.sopia.schedule.entities.Tags;
import com.sopia.schedule.entities.Wupin;
import com.sopia.schedule.entities.Xiangmu;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public interface ScheduleGlobleDao {
	
	List<Kehu> getKHDA_KHJDList(String tablename) throws ElException;
	
	List<Kehu> getKehuList(String tablename,ElNode department) throws ElException;
	
	int getKehuCountByDengji(String tablename) throws ElException;
	
	/**
	 * 收款、付款模块根据tablename查询合计金额
	 * @param tablename
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws ElException
	 */
	double gethejiByTablename(String tablename,Timestamp starttime,Timestamp endtime) throws ElException;

	
	List<Gzrz> getGzrzList(ELUser elUser,Timestamp starttime,Timestamp endtime,String tablename,String orderBy,String ordersc,ElNode department,int pageNow,int pageSize) throws ElException;
	int getGzrzListCount(ELUser elUser,Timestamp starttime,Timestamp endtime,String tablename,ElNode department) throws ElException;
	public List<Map<String, String>> select_my_tableinfo_by_userid_order(String sqlAppend,int type,
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			int userid, String order,int pageNow, int pageSize,int type_) throws ElException;
	
	public int select_my_tableinfo_by_userid_count(int type,List<Tags> list_tags,
			Map<String, String> hm, String tablename, int userid,int type_)
			throws ElException;
	
	public List<Wupin> getWupinList(String tablename,int pageNow, int pageSize,String groupBy,Map<String,Object> map) throws ElException;
	public int getWupinListSize(String tablename,String groupBy,Map<String,Object> map) throws ElException;
	
	public Wupin getQiuheWupin(String tablename) throws ElException;
	
	public List<String> getCangkuList(String tablename) throws ElException;
	public List<String> getModuleList(String tablename) throws ElException;
	public List<Map<String,String>> getModuleMap(String tablename) throws ElException;
	public List<Map<String,Object>> getAccounting(String tablename,int pageNow, int pageSize,Xiangmu xiangmu,ElNode department) throws ElException;
	public int getAccountingCount(String tablename,Xiangmu xiangmu,ElNode department) throws ElException;
	public double getHeByTablenameColumn(String tablename,String columnName,String column,int id) throws ElException;
	
	
	public List<Map<String,Object>> getMyPlan(int userid,boolean is_show,int number) throws ElException;
	public List<Map<String,Object>> getMyLog(int userid,boolean is_show,int number) throws ElException;
	public List<Map<String,Object>> getMyRC(int userid,boolean is_show,int number) throws ElException;
	public List<Map<String,Object>> getMyDaibanshuwu(int userid,boolean is_show,int number) throws ElException;
	
	public List<Map<String,Object>> getNoPass(int roleid,int userid,int pageNow,int pageSize,boolean is_show,ElNode department) throws ElException;
	public int getNoPassSize(int roleid,int userid,boolean is_show,ElNode department) throws ElException;
	public List<Map<String,Object>> getdaiPass(int roleid,int userid,int pageNow,int pageSize,boolean is_show,ElNode department) throws ElException;
	public int getdaiPassSize(int roleid,int userid,boolean is_show,ElNode department) throws ElException;
	
	public Map<String, List<Map<String, Object>>> viewRelateDanju(int id) throws ElException;
	
	
	public List<Map<String,Object>> getKehuAnalysis(String tablename,int pageNow,int pageSize,Map map,ElNode department) throws ElException;
	public int getKehuAnalysisSize(String tablename ,Map map ,ElNode department) throws ElException;
	
	
	public Map<String,List<Production_efficiency>> getProduction_efficiency(int pageNow, int pageSize,Map<String,Object> map) throws ElException;
	public int getProduction_efficiency_size(Map<String,Object> map) throws ElException;
	
	public List<Map<String,Object>> getMaterial_requirements(int pageNow,int pageSize) throws ElException;
	public int getMaterial_requirements_size()throws ElException;
}
