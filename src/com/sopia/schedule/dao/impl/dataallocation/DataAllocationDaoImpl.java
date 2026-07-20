package com.sopia.schedule.dao.impl.dataallocation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.schedule.CustomReportConstants;
import com.sopia.schedule.DataAllocationUtil;
import com.sopia.schedule.dao.dataallocation.DataAllocationDao;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.Tags;
import com.sopia.schedule.entities.dataallocation.DataAllocation;

public class DataAllocationDaoImpl implements DataAllocationDao{
	private static final Log logger = LogFactory.getLog(DataAllocationDaoImpl.class);
	

	public List<Map<String, String>> listDataAllocation(List<Tags> list_tags,
			ModuleManage moduleManage, ElNode department, int pageNow, int pageSize,String tablename,String order)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;
		
		List<Map<String, String>>  list_designe = new ArrayList<Map<String,String>>();
		Map<String, String> map = null;
		
		String sql = "";
		String sqlcolumn = "";
		
		String sqlorder = "  order by id desc ";

		if (order != null) {
			if (!order.equals("")) {
				sqlorder = order;
			}
		}
		
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1) {

				sqlcolumn += ",";
				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
				if (String.valueOf(sqlcolumn.charAt(sqlcolumn.length() - 1))
						.equals(","))
					sqlcolumn = sqlcolumn.substring(0, sqlcolumn
							.lastIndexOf(","));
			}

		}
		
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from (select a.*,rownum rn from (" +
					"select  t.id,t.status ,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name "+sqlcolumn+
					" from "+tablename+" t,eluser e,department d"+
			" join ("
			+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", department, true)
			+ ") dep on dep.id=d.id  where  userid in (select id from eluser where valid=1 and t.userid=e.id  and  e.depid=d.id )" + sqlorder    
			+" ) a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			while(rs.next()){
				map = new HashMap<String,String>();
				
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getDepartsearch_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(
											Math.ceil(rs.getFloat(list_tags
													.get(i).getColumn_name())))
									.equals("0.0") ? "" : String.valueOf(Math
									.ceil(rs.getFloat(list_tags.get(i)
											.getColumn_name()))));

						}
						if (list_tags.get(i).getColumn_type().equals("float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(
											rs.getFloat(list_tags.get(i)
													.getColumn_name())).equals(
											"0.0") ? "" : String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						} else {
							if (list_tags.get(i).getDisplay_type().equals(
									"相关字段")) {
								String returnvalue = "";
								String id = rs.getString(list_tags.get(i)
										.getColumn_name());// 获取相关字段值，序列id值
								if (id == null)
									continue;
								if (id.equals(""))
									continue;
								String str = list_tags.get(i)
										.getDefault_value();
								String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
								String sql_relate = " select " + arr[1]
										+ " from " + arr[0] + " where id in ("
										+ id + ")";
								ct2 = DBConnection.getConnection();
								ps2 = ct2.prepareStatement(sql_relate);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									returnvalue += rs2.getString(1);
									returnvalue += "<br>";
								}
								DBConnection.closeConnectInfo(ct2, ps2, rs2);

								map.put(list_tags.get(i).getColumn_name(),
										returnvalue);

							} else if (list_tags.get(i).getDisplay_type()
									.equals("相关负责人")) {
								String returnvalue = "";
								String id = rs.getString(list_tags.get(i)
										.getColumn_name());// 获取相关字段值，序列id值
								if (id == null)
									continue;
								if (id.equals(""))
									continue;
								String sql_relate = " select  realname "
										+ " from eluser where id in (" + id
										+ ")";
								ct2 = DBConnection.getConnection();
								ps2 = ct2.prepareStatement(sql_relate);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									returnvalue += rs2.getString(1);
									returnvalue += "<br>";
								}
								DBConnection.closeConnectInfo(ct2, ps2, rs2);

								map.put(list_tags.get(i).getColumn_name(),
										returnvalue);

							} else {
								map.put(list_tags.get(i).getColumn_name(), rs
										.getString(list_tags.get(i)
												.getColumn_name()));
							}
						}
					}
				}
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));
				//查询分配信息
				map.put("dataallocation", String.valueOf(selectDataAllocationCountByEntityIdAndModuleId(rs.getInt("id"),moduleManage.getId())));
				list_designe.add(map);
			}
			
		}catch (Exception e) {
			logger.error("查询数据分配出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list_designe;
	}
	
	public List<Map<String, String>> listDataApplication(int userid,List<Tags> list_tags,
			ModuleManage moduleManage, ElNode department, int pageNow, int pageSize,String tablename,String order)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;
		
		List<Map<String, String>>  list_designe = new ArrayList<Map<String,String>>();
		Map<String, String> map = null;
		
		String sql = "";
		String sqlcolumn = "";
		
		String sqlorder = "  order by id desc ";

		if (order != null) {
			if (!order.equals("")) {
				sqlorder = order;
			}
		}
		
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1) {

				sqlcolumn += ",";
				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
				if (String.valueOf(sqlcolumn.charAt(sqlcolumn.length() - 1))
						.equals(","))
					sqlcolumn = sqlcolumn.substring(0, sqlcolumn
							.lastIndexOf(","));
			}

		}
		
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from (select a.*,rownum rn from (" +
					"select  t.id,t.status ,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name "+sqlcolumn+
					" from "+tablename+" t,eluser e,department d"+
			" join ("
			+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", department, true)
			+ ") dep on dep.id=d.id where  userid in (select id from eluser where valid=1 and t.userid=e.id  and  e.depid=d.id ) and t.status = 9  " + sqlorder 
			+" ) a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			while(rs.next()){
				map = new HashMap<String,String>();
				
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getDepartsearch_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(
											Math.ceil(rs.getFloat(list_tags
													.get(i).getColumn_name())))
									.equals("0.0") ? "" : String.valueOf(Math
									.ceil(rs.getFloat(list_tags.get(i)
											.getColumn_name()))));

						}
						if (list_tags.get(i).getColumn_type().equals("float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(
											rs.getFloat(list_tags.get(i)
													.getColumn_name())).equals(
											"0.0") ? "" : String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						} else {
							if (list_tags.get(i).getDisplay_type().equals(
									"相关字段")) {
								String returnvalue = "";
								String id = rs.getString(list_tags.get(i)
										.getColumn_name());// 获取相关字段值，序列id值
								if (id == null)
									continue;
								if (id.equals(""))
									continue;
								String str = list_tags.get(i)
										.getDefault_value();
								String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
								String sql_relate = " select " + arr[1]
										+ " from " + arr[0] + " where id in ("
										+ id + ")";
								ct2 = DBConnection.getConnection();
								ps2 = ct2.prepareStatement(sql_relate);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									returnvalue += rs2.getString(1);
									returnvalue += "<br>";
								}
								DBConnection.closeConnectInfo(ct2, ps2, rs2);

								map.put(list_tags.get(i).getColumn_name(),
										returnvalue);

							} else if (list_tags.get(i).getDisplay_type()
									.equals("相关负责人")) {
								String returnvalue = "";
								String id = rs.getString(list_tags.get(i)
										.getColumn_name());// 获取相关字段值，序列id值
								if (id == null)
									continue;
								if (id.equals(""))
									continue;
								String sql_relate = " select  realname "
										+ " from eluser where id in (" + id
										+ ")";
								ct2 = DBConnection.getConnection();
								ps2 = ct2.prepareStatement(sql_relate);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									returnvalue += rs2.getString(1);
									returnvalue += "<br>";
								}
								DBConnection.closeConnectInfo(ct2, ps2, rs2);

								map.put(list_tags.get(i).getColumn_name(),
										returnvalue);

							} else {
								map.put(list_tags.get(i).getColumn_name(), rs
										.getString(list_tags.get(i)
												.getColumn_name()));
							}
						}
					}
				}
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));
				//查询申请信息
				map = selectDateApplicationCountByEntityIdAndModuleidAndUserId(map,rs.getInt("id"),moduleManage.getId(),userid);
				list_designe.add(map);
			}
			
		}catch (Exception e) {
			logger.error("查询数据申请出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list_designe;
	}
	
	public Map<String,String> selectDateApplicationCountByEntityIdAndModuleidAndUserId(Map<String,String> map,int entityid,int moduleid,int userid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select * from tb_data_allocation where  entityid=" + entityid + " and moduleid=" + moduleid + " and userid=" + userid;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				if(rs.getInt("status") == 5){
					map.put("tdastatus", "审核中");
				}else if(rs.getInt("status") == 4){
					map.put("tdastatus", "未申请");
				}else if(rs.getInt("status") == 3){
					map.put("tdastatus", "未通过");
				}else if(rs.getInt("status") == 2){
					map.put("tdastatus", "已审核");
				}else if(rs.getInt("status") == 1){
					map.put("tdastatus", "已分配");
				}
				map.put("application", String.valueOf(rs.getInt("status")));
				map.put("userid", String.valueOf(rs.getInt("userid")));
			}else{
				map.put("application", String.valueOf(0));
			}
		}catch (Exception e) {
			logger.error("数据申请查状态和申请出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return map;
	}
	
	public int selectDataAllocationCountByEntityIdAndModuleId(int entityid,int moduleid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from tb_data_allocation where status=2 and  entityid=" + entityid + " and moduleid=" + moduleid;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			logger.error("查询分配人数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public int listDataAllocationSize(List<Tags> list_tags, ModuleManage moduleManage,
			ElNode department, int pageNow, int pageSize,String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from "+tablename ;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			logger.error("查询数据分配条数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	public int listDataApplicationSize(int userid,List<Tags> list_tags, ModuleManage moduleManage,
			ElNode department, int pageNow, int pageSize,String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from "+tablename + " where status = 9";
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			logger.error("查询数据分配条数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public List<ELUser> listUsers(int sub_department,ElNode department, ELUser eluser,int pageNow, int pageSize,int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		List<ELUser> elusers = new ArrayList<ELUser>();
		ELUser user = null;
		DataAllocation dataAllocation = null;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			boolean is_sub = sub_department==1?true:false;
			if(eluser!=null){
				if(eluser.getUsername()!=null&&!eluser.getUsername().equals("")){
					sqlAppend += " and eusername like '%" + eluser.getUsername().trim() + "%' ";
				}
				if(eluser.getRealname()!=null&&!eluser.getRealname().equals("")){
					sqlAppend += " and erealname like '%" + eluser.getRealname().trim() + "%' ";
				}
				if(eluser.getSex()!=null&&!eluser.getSex().equals("")){
					sqlAppend += " and esex = '" + eluser.getSex().trim() + "' ";
				}
				if (eluser.getShengri() != null) {
					sqlAppend += " and to_char(eshengri,'yyyy-MM-dd') >= '" + eluser.getShengri() + "' ";
				}
				if (eluser.getShengri_end() != null) {
					sqlAppend += " and to_char(eshengri,'yyyy-MM-dd') <= '" + eluser.getShengri_end() + "' ";
				}
				if(eluser.getDishi() != 0){
					sqlAppend += " and edishi = " + eluser.getDishi() + " ";
				}
				if(eluser.getZhiji() != 0){
					sqlAppend += " and ezhiji = " + eluser.getZhiji() + " ";
				}
				if(eluser.getZhiwu() != 0){
					sqlAppend += " and ezhiwu = " + eluser.getZhiwu() + " ";
				}
				if(eluser.getJingzhong() != 0){
					sqlAppend += " and ejingzhong = " + eluser.getJingzhong() + " ";
				}
				if(eluser.getIsAllocated() != 3){
					if(eluser.getIsAllocated() == 1){
						sqlAppend += " and tda.allocationtype = 1 ";
					}else if(eluser.getIsAllocated() == 0){
						sqlAppend += " and tda.allocationtype is null ";
					}
				}
				if(eluser.getIsApplicated() != 0){
					if(eluser.getIsApplicated() == 2){
						sqlAppend += " and tda.status = 2 ";
					}else{
						sqlAppend += " and tda.status != 2 ";
					}
				}
			}
			
			sql = "select b.*,rn from (select a.*,rownum rn from (select mm.*,tda.id as tdaid,tda.allocationtype as tdaallocationtype,tda.status as tdastatus,tda.begintime as tdabegintime,tda.endtime as tdaendtime from (select " +
					"e.id as eid,e.realname as erealname,d.id as did,d.name as dname,e.username as eusername," +
					"r.id as rid,r.name as rname,e.sex as esex,e.shenfenzheng as eshenfenzheng,e.shengri as eshengri," +
					"e.zhiji as ezhiji,e.dishi as edishi,e.zhiwu as ezhiwu,e.jingzhong as ejingzhong,"+
					"nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) eage " +
					"from eluser e,elrole r, ("+
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", department, is_sub) +
					") d where e.depid=d.id and e.role=r.id) mm left join tb_data_allocation tda on mm.eid=tda.userid and tda.entityid="+id+" where 1=1 "+sqlAppend+") a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				user = new ELUser();
				user.setId(rs.getInt("eid"));
				user.setRealname(rs.getString("erealname"));
				user
				.setDepartment(new Department(rs.getInt("did"), rs
						.getString("dname")));
				user.setUsername(rs.getString("eusername"));
				user.setJingzhong(rs.getInt("ejingzhong"));
				user.setRole(new ElRole(rs.getInt("rid"), rs.getString("rname")));
				user.setSex(rs.getString("esex"));
				user.setShenfenzheng(String.valueOf(getAge(rs.getString("eshenfenzheng"))));
				user.setAge(rs.getInt("eage"));
				dataAllocation = new DataAllocation();
				dataAllocation.setId(rs.getInt("tdaid"));
				dataAllocation.setAllocationtype(rs.getInt("tdaallocationtype"));
				dataAllocation.setStatus(rs.getInt("tdastatus"));
				dataAllocation.setBegintime(rs.getTimestamp("tdabegintime"));
				dataAllocation.setEndtime(rs.getTimestamp("tdaendtime"));
				user.setDataAllocation(dataAllocation);
				elusers.add(user);
			}
			
		}catch (Exception e) {
			logger.error("查询人员分配列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elusers;
	}

	public int listUsersSize(int sub_department,ElNode department, ELUser eluser,int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		int count = 0;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			boolean is_sub = sub_department==1?true:false;
			if(eluser!=null){
				if(eluser.getUsername()!=null&&!eluser.getUsername().equals("")){
					sqlAppend += " and eusername like '%" + eluser.getUsername().trim() + "%' ";
				}
				if(eluser.getRealname()!=null&&!eluser.getRealname().equals("")){
					sqlAppend += " and erealname like '%" + eluser.getRealname().trim() + "%' ";
				}
				if(eluser.getSex()!=null&&!eluser.getSex().equals("")){
					sqlAppend += " and esex = '" + eluser.getSex().trim() + "' ";
				}
				if (eluser.getShengri() != null) {
					sqlAppend += " and to_char(eshengri,'yyyy-MM-dd') >= '" + eluser.getShengri() + "' ";
				}
				if (eluser.getShengri_end() != null) {
					sqlAppend += " and to_char(eshengri,'yyyy-MM-dd') <= '" + eluser.getShengri_end() + "' ";
				}
				if(eluser.getDishi() != 0){
					sqlAppend += " and edishi = " + eluser.getDishi() + " ";
				}
				if(eluser.getZhiji() != 0){
					sqlAppend += " and ezhiji = " + eluser.getZhiji() + " ";
				}
				if(eluser.getZhiwu() != 0){
					sqlAppend += " and ezhiwu = " + eluser.getZhiwu() + " ";
				}
				if(eluser.getJingzhong() != 0){
					sqlAppend += " and ejingzhong = " + eluser.getJingzhong() + " ";
				}
				if(eluser.getIsAllocated() != 3){
					if(eluser.getIsAllocated() == 1){
						sqlAppend += " and tda.allocationtype = 1 ";
					}else if(eluser.getIsAllocated() == 0){
						sqlAppend += " and tda.allocationtype is null ";
					}
				}
				if(eluser.getIsApplicated() != 0){
					if(eluser.getIsApplicated() == 2){
						sqlAppend += " and tda.status = 2 ";
					}else{
						sqlAppend += " and tda.status != 2 ";
					}
				}
			}
			
			sql = "select count(1) from (select " +
					"e.id as eid,e.realname as erealname,d.id as did,d.name as dname,e.username as eusername," +
					"r.id as rid,r.name as rname,e.sex as esex,e.shenfenzheng as eshenfenzheng,e.shengri as eshengri," +
					"e.zhiji as ezhiji,e.dishi as edishi,e.zhiwu as ezhiwu,e.jingzhong as ejingzhong,"+
					"nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) eage from eluser e,elrole r ,(" +
			((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
			.generateSQLByTree("department", department, is_sub) + 
					") d where e.depid=d.id and e.role=r.id  ) mm left join tb_data_allocation tda on mm.eid=tda.userid and tda.entityid="+id+" where 1=1" + sqlAppend;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			logger.error("查询人员分配用户数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	/////////////////////////////////
	private static int getAge(String IDCardNum) {
		if (IDCardNum == null) {
			return -1;
		}
		int year, month, day, idLength = IDCardNum.length();
		Calendar cal1 = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		if (idLength == 18) {
			year = Integer.parseInt(IDCardNum.substring(6, 10));
			month = Integer.parseInt(IDCardNum.substring(10, 12));
			day = Integer.parseInt(IDCardNum.substring(12, 14));
		} else if (idLength == 15) {
			year = Integer.parseInt(IDCardNum.substring(6, 8)) + 1900;
			month = Integer.parseInt(IDCardNum.substring(8, 10));
			day = Integer.parseInt(IDCardNum.substring(10, 12));
		} else {
			return -1;
		}
		cal1.set(year, month, day);
		return getYearDiff(today, cal1);
	}
	
	private static int getYearDiff(Calendar cal, Calendar cal1) {
		int m = (cal.get(cal.MONTH)) - (cal1.get(cal1.MONTH));
		int y = (cal.get(cal.YEAR)) - (cal1.get(cal1.YEAR));
		return (y * 12 + m) / 12;
	}

	public void insertDataAllocation(int id, int userid,
			ModuleManage moduleManage, DataAllocation dataAllocation)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "{call into_tda(?,?,?,?,?,?,?)}";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, userid);
			ps.setInt(3, moduleManage.getId());
			if(dataAllocation == null){
				ps.setInt(4, 5);
				ps.setTimestamp(5, null);
				ps.setTimestamp(6, null);
				ps.setInt(7, 2);
			}else{
				ps.setInt(4, 2);
				ps.setTimestamp(5, dataAllocation.getBegintime());
				ps.setTimestamp(6, dataAllocation.getEndtime());
				ps.setInt(7, 1);
			}
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.error("插入分配信息表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void deleteDataAllocationAll(int id, 
			ModuleManage moduleManage)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "delete from tb_data_allocation where entityid=?  and moduleid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, moduleManage.getId());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.error("插入分配信息前删除原有信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void deleteDataAllocation(int id, int userid,
			ModuleManage moduleManage)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "delete from tb_data_allocation where entityid=? and userid=? and moduleid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, userid);
			ps.setInt(3, moduleManage.getId());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.error("取消分配信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void updateDataAllocation(int id, int userid,
			ModuleManage moduleManage,int type,DataAllocation dataAllocation)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "{call update_data_allocation(?,?,?,?,?,?)}";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, userid);
			ps.setInt(3, moduleManage.getId());
			ps.setInt(4, type);
			if(dataAllocation == null){
				ps.setTimestamp(5, null);
				ps.setTimestamp(6, null);
			}else{
				ps.setTimestamp(5, dataAllocation.getBegintime());
				ps.setTimestamp(6, dataAllocation.getEndtime());
			}
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.error("审核通过或者不通过出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<Map<String, String>> listMyGetDataAllocation(
			List<Tags> list_tags, ModuleManage moduleManage, int pageNow,
			int pageSize, String tablename,String ids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;
		
		List<Map<String, String>>  list_designe = new ArrayList<Map<String,String>>();
		Map<String, String> map = null;
		
		String sql = "";
		String sqlcolumn = "";
		
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getList_display() == 1) {

				sqlcolumn += ",";
				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
				if (String.valueOf(sqlcolumn.charAt(sqlcolumn.length() - 1))
						.equals(","))
					sqlcolumn = sqlcolumn.substring(0, sqlcolumn
							.lastIndexOf(","));
			}

		}
		
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from (select a.*,rownum rn from (select mm.*,tda.status as tdastatus,to_char(tda.begintime,'yyyy-mm-dd') as tdabegintime,to_char(tda.endtime,'yyyy-mm-dd') as tdaendtime from (" +
					"select  t.id,t.status ,t.userid,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name "+sqlcolumn+
					" from "+tablename+" t,eluser e,department d"+
			" where  userid in (select id from eluser where valid=1 and t.userid=e.id  and  e.depid=d.id )  and t.id in (" + ids + ") ) mm join tb_data_allocation tda on mm.id=tda.entityid where entityid in ( "+ids + ") "
			+" ) a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			while(rs.next()){
				map = new HashMap<String,String>();
				
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getDepartsearch_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(
											Math.ceil(rs.getFloat(list_tags
													.get(i).getColumn_name())))
									.equals("0.0") ? "" : String.valueOf(Math
									.ceil(rs.getFloat(list_tags.get(i)
											.getColumn_name()))));

						}
						if (list_tags.get(i).getColumn_type().equals("float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(
											rs.getFloat(list_tags.get(i)
													.getColumn_name())).equals(
											"0.0") ? "" : String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						} else {
							if (list_tags.get(i).getDisplay_type().equals(
									"相关字段")) {
								String returnvalue = "";
								String id = rs.getString(list_tags.get(i)
										.getColumn_name());// 获取相关字段值，序列id值
								if (id == null)
									continue;
								if (id.equals(""))
									continue;
								String str = list_tags.get(i)
										.getDefault_value();
								String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
								String sql_relate = " select " + arr[1]
										+ " from " + arr[0] + " where id in ("
										+ id + ")";
								ct2 = DBConnection.getConnection();
								ps2 = ct2.prepareStatement(sql_relate);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									returnvalue += rs2.getString(1);
									returnvalue += "<br>";
								}
								DBConnection.closeConnectInfo(ct2, ps2, rs2);

								map.put(list_tags.get(i).getColumn_name(),
										returnvalue);

							} else if (list_tags.get(i).getDisplay_type()
									.equals("相关负责人")) {
								String returnvalue = "";
								String id = rs.getString(list_tags.get(i)
										.getColumn_name());// 获取相关字段值，序列id值
								if (id == null)
									continue;
								if (id.equals(""))
									continue;
								String sql_relate = " select  realname "
										+ " from eluser where id in (" + id
										+ ")";
								ct2 = DBConnection.getConnection();
								ps2 = ct2.prepareStatement(sql_relate);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									returnvalue += rs2.getString(1);
									returnvalue += "<br>";
								}
								DBConnection.closeConnectInfo(ct2, ps2, rs2);

								map.put(list_tags.get(i).getColumn_name(),
										returnvalue);

							} else {
								map.put(list_tags.get(i).getColumn_name(), rs
										.getString(list_tags.get(i)
												.getColumn_name()));
							}
						}
					}
				}
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));
				map.put("userid", rs.getString("userid"));
				//将分配信息一起放于map
				map.put("tdastatus", rs.getString("tdastatus"));
				map.put("tdabegintime", rs.getString("tdabegintime")==null?"":rs.getString("tdabegintime"));
				map.put("tdaendtime", rs.getString("tdaendtime")==null?"":rs.getString("tdaendtime"));
				
				list_designe.add(map);
			}
			
		}catch (Exception e) {
			logger.error("获取已分配的数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list_designe;
	}

	public int listMyGetDataAllocationSize(List<Tags> list_tags,
			ModuleManage moduleManage, int pageNow, int pageSize,
			String tablename,String ids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from " + tablename + " t,eluser e,department d where userid in (select id from eluser where valid=1 and t.userid=e.id  and  e.depid=d.id ) and t.id in (" + ids + ") ";
			
			
			sql = "select count(*) from (" +
					"select  t.id,t.status ,t.userid,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name "+
					" from "+tablename+" t,eluser e,department d"+
			" where  userid in (select id from eluser where valid=1 and t.userid=e.id  and  e.depid=d.id )  and t.id in (" + ids + ") ) mm join tb_data_allocation tda on mm.id=tda.entityid where entityid in ( "+ids + ") ";
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			logger.error("获取已分配的数据条数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	public String myGetDataAllocationIds(ModuleManage moduleManage,int userid, String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		String sql = "";
		String ids = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select entityid from tb_data_allocation where moduleid= " + moduleManage.getId() + " and userid = " + userid;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ids += rs.getInt(1) + ",";
			}
			if(ids!=null&&!ids.equals("")){
				ids = ids.substring(0, ids.lastIndexOf(","));
			}
			
		}catch (Exception e) {
			logger.error("获得已分配数据ids出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ids;
	}

	public DataAllocation select_dataAllocation_by_moduleid_userid_entityid(
			ModuleManage moduleManage, int userid, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		DataAllocation dataAllocaiton = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select * from tb_data_allocation where moduleid= " + moduleManage.getId() + " and userid = " + userid + " and entityid = " + id;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()){
				dataAllocaiton = new DataAllocation();
				dataAllocaiton.setId(rs.getInt("id"));
				dataAllocaiton.setBegintime(rs.getTimestamp("begintime"));
				dataAllocaiton.setEndtime(rs.getTimestamp("endtime"));
				dataAllocaiton.setEntityid(rs.getInt("entityid"));
				dataAllocaiton.setAllocationtype(rs.getInt("allocationtype"));
				dataAllocaiton.setModuleid(rs.getInt("moduleid"));
				dataAllocaiton.setStatus(rs.getInt("status"));
				dataAllocaiton.setUserid(rs.getInt("userid"));
			}
			
		}catch (Exception e) {
			logger.error("根据数据id、用户id、模块id查询分配信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dataAllocaiton;
	}



}
