package com.sopia.attendance.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.attendance.dao.AttendanceDao;
import com.sopia.attendance.entity.Attendance;
import com.sopia.attendance.entity.AttendanceCount;
import com.sopia.attendance.entity.WorkAttendance;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;

public class AttendanceDaoImpl implements AttendanceDao {
	private static final Log logger = LogFactory
			.getLog(AttendanceDaoImpl.class);

	public List<ELUser> listUsers(ElNode dep, int subdep, ELUser eu,
			int pageNow, int pageSize,String ordercolumn,String ordersc) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql_ordercolumn = "";
		
		if(ordercolumn != null && !ordercolumn.equals("")){
			sql_ordercolumn = " order by " + ordercolumn;
			if(ordersc != null && !ordersc.equals("")){
				sql_ordercolumn += " " + ordersc;
			}
		}
		try {
			boolean consub = subdep == 1 ? true : false;
			String username = "";
			String realname = "";
			String sex = "";
			Date shengri = null;
			Date shengri_end = null;
			int valid = -2;
			int roleid = -2;
			StringBuffer basesql = new StringBuffer(
					"select * from (select t.*,rownum rn from(select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_,eu.mac  from ELUSER eu join (");
			basesql.append(((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", dep, consub));
			basesql
					.append(") dep on eu.depid = dep.id left join elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ? " + sql_ordercolumn);
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();

				if (null != eu.getSex())
					sex = eu.getSex().trim();
				if (eu.getShengri() != null) {
					basesql.append(" and eu.shengri >=?)");
					shengri = eu.getShengri();
				}
				if (eu.getShengri_end() != null) {
					basesql.append(" and eu.shengri <= ?");
					shengri_end = eu.getShengri_end();
				}
				if (eu.getValid2() != 0) {
					if (eu.getValid2() == 1) {
						basesql.append(" and eu.valid= ?");
						valid = 1;
					} else {
						basesql.append(" and eu.valid= ?");
						valid = 0;
					}
				}
				if (eu.getRole() != null && eu.getRole().getId() > 0) {
					basesql.append(" and eu.role =  ?");
					roleid = eu.getRole().getId();
				}
			}
			basesql.append(")t where rownum <=? ) where rn >=?");
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(basesql.toString());
			ps.setString(1, "%" + username + "%");
			ps.setString(2, "%" + realname + "%");
			ps.setString(3, "%" + sex + "%");
			int idx = 4;
			if (shengri != null) {
				ps.setDate(idx, shengri);
				idx++;
			}
			if (shengri_end != null) {
				ps.setDate(idx, shengri_end);
				idx++;
			}
			if (valid != -2) {
				ps.setInt(idx, valid);
				idx++;
			}
			if (roleid != -2) {
				ps.setInt(idx, roleid);
				idx++;
			}
			ps.setInt(idx, pageNow);
			ps.setInt(idx + 1, pageSize);
			// ps = ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
			// .generatePSByTree(
			// "select * from (select t1.*,rownum rn from(",
			// basesql.toString(),
			// ")t1 where rownum <=? ) where rn >=?", "dep", dep,
			// consub, params, ct,pageNow,pageSize);
			// ps = ct.prepareStatement(sql);

			// ps.setInt(1, pageNow);
			// ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
				elUser.setDepartment(new Department(rs.getInt(5), rs
						.getString(6)));
				elUser.setValid(rs.getBoolean(7));
				elUser.setSex(rs.getString(9));
				elUser.setJingzhong(rs.getInt(10));
				elUser.setShengri(rs.getDate(11));
				elUser.setAge(rs.getInt(12));
				elUser.setMac(rs.getString("mac"));
				
				this.setAttendanceCount(elUser);
				
				
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public int listUsersSize(ElNode dep, int subdep, ELUser eu)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			boolean consub = subdep == 1 ? true : false;
			String username = "";
			String realname = "";
			String sex = "";
			Date shengri = null;
			Date shengri_end = null;
			int valid = -2;
			int roleid = -2;
			StringBuffer basesql = new StringBuffer(
					"select count(eu.id) from ELUSER eu join (");
			basesql.append(((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", dep, consub));
			basesql
					.append(") dep on eu.depid = dep.id left join elrole er on er.id = eu.role where eu.username like ? and eu.realname like ? and eu.sex like ?");
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();

				if (null != eu.getSex())
					sex = eu.getSex().trim();
				// if (null != eu.getJingzhong())
				// jz = eu.getJingzhong().trim();
				if (eu.getShengri() != null) {
					basesql.append(" and eu.shengri >=?)");
					shengri = eu.getShengri();
				}
				if (eu.getShengri_end() != null) {
					basesql.append(" and eu.shengri <= ?");
					shengri_end = eu.getShengri_end();
				}
				if (eu.getValid2() != 0) {
					if (eu.getValid2() == 1) {
						basesql.append(" and eu.valid= ?");
						valid = 1;
					} else {
						basesql.append(" and eu.valid= ?");
						valid = 0;
					}
				}
				if (eu.getRole() != null && eu.getRole().getId() > 0) {
					basesql.append(" and eu.role =  ?");
					roleid = eu.getRole().getId();
				}
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(basesql.toString());
			ps.setString(1, "%" + username + "%");
			ps.setString(2, "%" + realname + "%");
			ps.setString(3, "%" + sex + "%");
			int idx = 4;
			if (shengri != null) {
				ps.setDate(idx, shengri);
				idx++;
			}
			if (shengri_end != null) {
				ps.setDate(idx, shengri_end);
				idx++;
			}
			if (valid != -2) {
				ps.setInt(idx, valid);
				idx++;
			}
			if (roleid != -2) {
				ps.setInt(idx, roleid);
				idx++;
			}
			rs = ps.executeQuery();
			if (rs.next())
				s = rs.getInt(1);
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	public void addAttendance(Attendance attendance) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String temp = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into attendance (worktime,outworktime,weekdaytime,holidays) " +
					" values (?,?,?,?)";
			ps = ct.prepareStatement(sql);
			
//			ps.setInt(1, attendance.getUserid());
			ps.setTimestamp(1, attendance.getWorktime());
			ps.setTimestamp(2, attendance.getOutworktime());
			if(attendance.getWeekdaytime() != null && attendance.getWeekdaytime().length>1){
				temp = attendance.getWeekdaytime()[0] + "," + attendance.getWeekdaytime()[1];
			}else {
				temp = attendance.getWeekdaytime()[0];
			}
			ps.setString(3, temp);
			ps.setString(4, attendance.getHolidays());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加考勤设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public static Timestamp getFormatValue(Timestamp date){
		
		return date;
	}
	
	public void updateAttendance(Attendance attendance) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String temp = "";
		
		
		try {
			ct = DBConnection.getConnection();
			sql = "update attendance set worktime=?,outworktime=?,weekdaytime=?,holidays=?";
			ps = ct.prepareStatement(sql);
			
			ps.setTimestamp(1, attendance.getWorktime());
			ps.setTimestamp(2, attendance.getOutworktime());
			if(attendance.getWeekdaytime() != null && attendance.getWeekdaytime().length>1){
				if(attendance.getWeekdaytime().length>1)
					temp = attendance.getWeekdaytime()[0] + "," + attendance.getWeekdaytime()[1];
				else 
					temp = attendance.getWeekdaytime()[0];
			}
			ps.setString(3, temp);
			ps.setString(4, attendance.getHolidays());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改考勤设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}


	public Attendance getAttendance() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		Attendance attendance = null;
		String[] array = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select * from attendance ";
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				attendance = new Attendance();
				attendance.setId(rs.getInt("id"));
				attendance.setHolidays(rs.getString("holidays"));
				attendance.setOutworktime(rs.getTimestamp("outworktime"));
				attendance.setWorktime(rs.getTimestamp("worktime"));
				
				String wdt = rs.getString("weekdaytime");
				if(wdt != null && !wdt.equals("")){
					array = new String[wdt.split(",").length];
					for(int i=0;i<array.length;i++){
						array[i] = wdt.split(",")[i];
					}
				}
				attendance.setWeekdaytime(array);
			}
			
		} catch (Exception e) {
			logger.error("查询考勤设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return attendance;
	}

	public String getMacAddressByUserId(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String mac = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select mac from eluser where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mac = rs.getString("mac");
			}
			
		} catch (Exception e) {
			logger.error("根据id查询mac地址失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mac;
	}

	public void updateMacAddressByUserId(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update eluser set mac = ? where id= ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, elUser.getMac());
			ps.setInt(2, elUser.getId());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改userid的mac地址失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public WorkAttendance getWorkAttendanceByUserIdAndDate(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		WorkAttendance workAttendance = null;
		
//		//获取当前时间的年月日
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String date = String.valueOf(cal.get(Calendar.DATE));
		if(Integer.parseInt(month) <10 && Integer.parseInt(month)>=1){
			month = "0" + Integer.parseInt(month);
		}
		if(Integer.parseInt(date) <10 && Integer.parseInt(date)>=1){
			date = "0" + Integer.parseInt(date);
		}
		String value = String.valueOf( cal.get(Calendar.YEAR)) + "/" + month  +"/" + date ;
		
		
		try {
			ct = DBConnection.getConnection();
			sql = "select * from workAttendance where userid=? and to_char(riqi,'yyyy/mm/dd') = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setString(2, value);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				workAttendance = new WorkAttendance();
				workAttendance.setSigndaotime(rs.getTimestamp("signdaotime"));
				workAttendance.setSigntuitime(rs.getTimestamp("signtuitime"));
				workAttendance.setId(rs.getInt("id"));
				workAttendance.setMark(rs.getString("mark"));
				workAttendance.setResult(rs.getString("result"));
				workAttendance.setRelateleave(rs.getString("relateleave"));
				workAttendance.setRelateout(rs.getString("relateout"));
				workAttendance.setRelateretroactive(rs.getString("relateretroactive"));
				workAttendance.setLeaveType(rs.getString("leaveType"));
				workAttendance.setRiqi(rs.getTimestamp("riqi"));
			}
			
		} catch (Exception e) {
			logger.error("根据userid和当前时间获取考勤信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return workAttendance;
	}
	
	public WorkAttendance getWorkAttendanceById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		WorkAttendance workAttendance = null;
		
		try {
			ct = DBConnection.getConnection();
			sql = "select * from workAttendance where id = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				workAttendance = new WorkAttendance();
				workAttendance.setSigndaotime(rs.getTimestamp("signdaotime"));
				workAttendance.setSigntuitime(rs.getTimestamp("signtuitime"));
				workAttendance.setId(rs.getInt("id"));
				workAttendance.setMark(rs.getString("mark"));
				workAttendance.setResult(rs.getString("result"));
				workAttendance.setRelateleave(rs.getString("relateleave"));
				workAttendance.setRelateout(rs.getString("relateout"));
				workAttendance.setRelateretroactive(rs.getString("relateretroactive"));
				workAttendance.setLeaveType(rs.getString("leaveType"));
				workAttendance.setRiqi(rs.getTimestamp("riqi"));
			}
			
		} catch (Exception e) {
			logger.error("获取考勤信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return workAttendance;
	}
	
	public WorkAttendance getAttendanceById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		WorkAttendance workAttendance = null;
		
		try {
			ct = DBConnection.getConnection();
			sql = "select * from workAttendance where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				workAttendance = new WorkAttendance();
				workAttendance.setSigndaotime(rs.getTimestamp("signdaotime"));
				workAttendance.setSigntuitime(rs.getTimestamp("signtuitime"));
				workAttendance.setId(rs.getInt("id"));
				workAttendance.setMark(rs.getString("mark"));
				workAttendance.setResult(rs.getString("result"));
				workAttendance.setRelateleave(rs.getString("relateleave"));
				workAttendance.setRelateout(rs.getString("relateout"));
				workAttendance.setRelateretroactive(rs.getString("relateretroactive"));
				workAttendance.setLeaveType(rs.getString("leaveType"));
				workAttendance.setRiqi(rs.getTimestamp("riqi"));
			}
			
		} catch (Exception e) {
			logger.error("根据id获取考勤信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return workAttendance;
	}
	
	public boolean checkTimeIn(WorkAttendance workAttendance,int type) throws ElException{
		Calendar cal = Calendar.getInstance();
		Map<String,Integer> map = new HashMap<String,Integer>();
		int date = cal.get(Calendar.DATE);
		boolean flag = false;
		int relateid = 0; 
		if(type == 3){
			if(workAttendance.getRelateleave() != null && !workAttendance.getRelateleave().equals(""))
				relateid = Integer.parseInt(workAttendance.getRelateleave());
			//QXJGL_QJKSSJ  QXJGL_QJJSSJ  
			map = this.getDateByRelateId(relateid,"QXJGL",type);
		}else if(type == 6){
			if(workAttendance.getRelateout() != null && !workAttendance.getRelateout().equals(""))
			//WCGL_KSSJ   WCGL_JSSJ
				relateid = Integer.parseInt(workAttendance.getRelateout());
			map = this.getDateByRelateId(relateid,"WCGL",type);
		}else if(type == 7){
			if(workAttendance.getRelateretroactive() != null && !workAttendance.getRelateretroactive().equals(""))
				relateid = Integer.parseInt(workAttendance.getRelateretroactive());
			map = this.getDateByRelateId(relateid,"BQGL",type);
		}
		
		if(type == 3 || type == 6){
			if(date >= map.get("KSSJ") && date <= map.get("JSSJ")){
				flag = true;
			}
		}else if(type == 7){
			if(date == map.get("BQSJ")){
				flag = true;
			}
		}
		return flag;
	}
	
	public Map<String,Integer> getDateByRelateId(int relateid,String tablename,int type) throws ElException{
		Map<String,Integer> map = new HashMap<String,Integer>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			
			if(type == 3){
				sql = "select QXJGL_QJKSSJ,QXJGL_QJJSSJ from  " + tablename + " where id=" + relateid;
			}else if(type == 6){
				sql = "select WCGL_KSSJ,WCGL_JSSJ from  " + tablename + " where id=" + relateid;
			}else if(type == 7){
				sql = "select BQGL_BQDQSJ from  " + tablename + " where id=" + relateid;
			}
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				if(type == 3 || type == 6){
					map.put("KSSJ", rs.getTimestamp(1).getDate());
					map.put("JSSJ", rs.getTimestamp(2).getDate());
				}else if(type == 7){
					map.put("BQSJ", rs.getTimestamp(1).getDate());
				}
			}
			
		} catch (Exception e) {
			logger.error("获取开始时间和结束时间失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return map;
	}

	public int addWorkAttendance(WorkAttendance workAttendance,int userid,int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlcolumn = "";
		int returnValue = 0;
		
//		Calendar cal = Calendar.getInstance();
//		String value = String.valueOf( cal.get(Calendar.YEAR)) + "年" + String.valueOf( cal.get(Calendar.MONTH) + 1) +"月" + String.valueOf( cal.get(Calendar.DATE)) + "日";
		
//		String sqlvalue = "" + userid + ",";
		try {
			ct = DBConnection.getConnection();
			
			if(type == 1){
				sqlcolumn += "signdaotime";
			}else if(type == 2){
				sqlcolumn += "signtuitime";
			}else if(type == 3){
				sqlcolumn = "relateleave";
			}else if(type == 4){
				sqlcolumn = "mark";
			}else if(type == 6){
				sqlcolumn = "relateout";
			}else if(type == 7){
				sqlcolumn = "relateretroactive";
			}
			
			if(type == 1 || type == 2 || type == 4){
				sql = "insert into workattendance ("+sqlcolumn+",riqi,userid) " +
				" values (?,?,"+userid+")";
			}else if(type == 3 | type == 6 || type == 7)  {
				sql = "insert into workattendance ("+sqlcolumn+",riqi,result,userid) " +
				" values (?,?,'"+workAttendance.getResult()+"',"+userid+")";
			}
			ps = ct.prepareStatement(sql);
			if(type == 1){
				ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			}else if(type == 2){
				ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			}else if(type == 3){
				ps.setString(1, workAttendance.getRelateleave());
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			}else if(type == 4){
				ps.setString(1, workAttendance.getMark());
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			}else if(type == 6){
				ps.setString(1, workAttendance.getRelateout());
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			}else if(type == 7){
				ps.setString(1, workAttendance.getRelateretroactive());
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			}
			
			ps.executeUpdate();
			
			sql = "select workattendance_sequence.currval from dual";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				returnValue = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("插入考勤表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}

	public void updateWorkAttendance(int userid, WorkAttendance workAttendance, int type)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlset = "";
		Attendance attendance = new Attendance();
		String value = "";
		
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		
		try {
			ct = DBConnection.getConnection();
			if(type == 1){
				sqlset = " signdaotime " + "= ? " ;
			}else if(type == 2){
				sqlset = " signtuitime " + "= ? " ;
			}else if(type == 3){
				sqlset = " relateleave " + "= ? ,result='" + workAttendance.getResult()+"' ";
			}else if(type == 6){
				sqlset = " relateout " + "= ? ,result='" + workAttendance.getResult()+"' ";
			}else if(type == 7){
				if(workAttendance.getRelateretroactive_type() != null 
						&& !workAttendance.getRelateretroactive_type().equals("")){
					if(workAttendance.getRelateretroactive_type().equals("签到"))
						sqlset = " relateretroactive " + "= ? ,result='" + workAttendance.getResult()+"',signdaotime=? ";
					else if(workAttendance.getRelateretroactive_type().equals("签退")){
						sqlset = " relateretroactive " + "= ? ,result='" + workAttendance.getResult()+"',signtuitime=? ";
					}
				}
			}else if(type == 4){
				sqlset = " mark = ? " ;
			}else if(type == 5){//结果自动计算
				sqlset = " result = ? " ;
				sql = "select * from workattendance where id= "+ workAttendance.getId();
				ps = ct.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs.next()){
					workAttendance.setSigndaotime(rs.getTimestamp("signdaotime"));
					workAttendance.setSigntuitime(rs.getTimestamp("signtuitime"));
				}
				
				sql = "select * from attendance ";
				ps = ct.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs.next()){
					attendance.setWorktime(rs.getTimestamp("worktime"));
					attendance.setOutworktime(rs.getTimestamp("outworktime"));
					attendance.setHolidays(rs.getString("holidays"));
					attendance.setWeekdaytime(new String[]{rs.getString("weekdaytime").split(",")[0],rs.getString("weekdaytime").split(",")[1]});
				}
				value = this.checkResult(week,workAttendance,attendance);
			}
			sql = "update workattendance set " + sqlset + " where  id=?";
			ps = ct.prepareStatement(sql);
			if(type == 1 || type == 2){
				ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				ps.setInt(2, workAttendance.getId());
			}else if(type == 3){
				ps.setString(1, workAttendance.getRelateleave());
				ps.setInt(2, workAttendance.getId());
			}else if(type == 4){
				ps.setString(1, workAttendance.getMark());
				ps.setInt(2, workAttendance.getId());
			}else if(type == 5){
				ps.setString(1, value);
				ps.setInt(2, workAttendance.getId());
			}else if(type == 6){
				ps.setString(1, workAttendance.getRelateout());
				ps.setInt(2, workAttendance.getId());
			}else if(type == 7){
				ps.setString(1, workAttendance.getRelateretroactive());
				if(workAttendance.getRelateretroactive_type() != null 
						&& !workAttendance.getRelateretroactive_type().equals("")){
					if(workAttendance.getRelateretroactive_type().equals("签到"))
						ps.setTimestamp(2, workAttendance.getSigndaotime());
					else if(workAttendance.getRelateretroactive_type().equals("签退")){
						ps.setTimestamp(2, workAttendance.getSigntuitime());
					}
				}
				ps.setInt(3, workAttendance.getId());
			}
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据id，userid,type更新考勤表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	//获取时分秒
	public static long getSFM(Timestamp time){
		long value = time.getHours() * 3600 + time.getMinutes() * 60 + time.getSeconds();
		return value;
		
	}
	
	
	public String checkResult(int week,WorkAttendance workAttendance,Attendance attendance) throws ElException{
		String value = "";
		//判断结果状态
		if(workAttendance.getRelateleave() != null || workAttendance.getRelateout() != null || workAttendance.getRelateretroactive() != null){
			//判断是否有请假条
			if(workAttendance.getRelateleave() != null && !workAttendance.getRelateleave().equals("")){
				value = "请假";
				return value;
			}
			//判断是否有外出单
			if(workAttendance.getRelateout() != null && !workAttendance.getRelateout().equals("")){
				value = "外出";
				return value;
			}
			//判断是否有补签单
			if(workAttendance.getRelateretroactive() != null && !workAttendance.getRelateretroactive().equals("")){
				value = "补签";
				return value;
			}
		}else {
			//判断是否是周末
			if(attendance.getWeekdaytime() != null && !attendance.getWeekdaytime().equals("")){
				for(int i=0;i<attendance.getWeekdaytime().length;i++){
					if(week == Integer.parseInt(attendance.getWeekdaytime()[i]) + 5){
						value = "周末";
						return value;
					}
				}
			}
			
			//判断是否迟到
			if(workAttendance.getSigndaotime() != null && attendance.getWorktime() != null){
				//取时分秒
//				if(workAttendance.getSigndaotime().getTime()>attendance.getWorktime().getTime()){
//					value = "迟到";
//					return value;
//				}
				if(getSFM(workAttendance.getSigndaotime())>getSFM(attendance.getWorktime())){
					value = "迟到";
					return value;
				}
			}
			//判断是否早退
			if(workAttendance.getSigntuitime() != null && attendance.getOutworktime() != null){
//				if(workAttendance.getSigntuitime().getTime()<attendance.getOutworktime().getTime()){
//					value = "早退";
//					return value;
//				}
				if(getSFM(workAttendance.getSigntuitime())<getSFM(attendance.getOutworktime())){
					value = "早退";
					return value;
				}
			}
			if((	workAttendance.getSigndaotime() == null || 
					getSFM(workAttendance.getSigndaotime())>getSFM(attendance.getWorktime()) )
					&& 
					workAttendance.getSigntuitime() == null || 
					getSFM(workAttendance.getSigntuitime())<getSFM(attendance.getOutworktime())){
				value = "迟到且早退";
				return value;
			}
			//判断是否正常
			if(workAttendance.getSigndaotime() != null && workAttendance.getSigntuitime() != null
					&& attendance.getWorktime() != null && attendance.getOutworktime() != null){
//				if(workAttendance.getSigndaotime().getTime()<attendance.getWorktime().getTime()
//						&& workAttendance.getSigntuitime().getTime()>attendance.getOutworktime().getTime()){
//					value = "正常";
//					return value;
//				}
				if(getSFM(workAttendance.getSigndaotime())<getSFM(attendance.getWorktime())
						&& getSFM(workAttendance.getSigntuitime())>getSFM(attendance.getOutworktime())){
					value = "正常";
					return value;
				}
			}
			
			
			//判断是否是节假日
			if(attendance.getHolidays() != null && !attendance.getHolidays().equals("")){
				if(workAttendance.getSigndaotime() != null){
					Timestamp date_ = workAttendance.getSigndaotime();
					SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd");
					String sign = myfmt.format(date_).toString();
//					String sign = String.valueOf(workAttendance.getSigndaotime().getYear())
//									+ (workAttendance.getSigndaotime().getMonth() + 1<10?"0"+String.valueOf(workAttendance.getSigndaotime().getMonth() + 1):String.valueOf(workAttendance.getSigndaotime().getMonth() + 1))
//									+ (workAttendance.getSigndaotime().getDate()<10?"0"+String.valueOf(workAttendance.getSigndaotime().getDate()):String.valueOf(workAttendance.getSigndaotime().getDate()));
					String[] str = attendance.getHolidays().split("==");
					String holiday = "";
//					String holiday_ = "";
//					String[] holiday_i_array;
					for(int i=0;i<str.length;i++){
						holiday = str[i];
						if(holiday != null && !holiday.equals("")){
							holiday = holiday.split(" ")[0];
//							holiday_i_array = holiday.split(" ")[0].split("-");
//							for(int j=0;j<holiday_i_array.length;j++){
//								holiday_  +=  holiday_i_array[j];
//							}
						}
						if(sign.equals(holiday)){
							value = "节假日";
							return value;
						}
					}
				}
			}
		}
		
		return value;
	}

	public List<WorkAttendance> getWorkAttendanceByUserId(int userid,int pageNow, int pageSize,
			Timestamp starttime,Timestamp endtime)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		List<WorkAttendance> workAttendanceList = new ArrayList<WorkAttendance>();
		WorkAttendance workAttendance = null;
		try {
			ct = DBConnection.getConnection();
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(riqi,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"' ";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(riqi,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"' ";
			
			sql = "select b.*,rn from (select a.*,rownum rn from (select * from workattendance where userid=? " + sqlAppend + 
					") a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				workAttendance = new WorkAttendance();
				workAttendance.setSigndaotime(rs.getTimestamp("signdaotime"));
				workAttendance.setSigntuitime(rs.getTimestamp("signtuitime"));
				workAttendance.setId(rs.getInt("id"));
				workAttendance.setMark(rs.getString("mark"));
				workAttendance.setResult(rs.getString("result"));
				workAttendance.setRelateleave(rs.getString("relateleave"));
				workAttendance.setRelateout(rs.getString("relateout"));
				workAttendance.setRelateretroactive(rs.getString("relateretroactive"));
				workAttendance.setLeaveType(rs.getString("leaveType"));
				workAttendance.setRiqi(rs.getTimestamp("riqi"));
				workAttendanceList.add(workAttendance);
			}
			
		} catch (Exception e) {
			logger.error("查询考勤列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return workAttendanceList;

	}

	public int getWorkAttendanceSizeByUserId(int userid,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		int resultValue = 0;
		try {
			ct = DBConnection.getConnection();
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(riqi,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"' ";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(riqi,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"' ";
			
			
			sql = "select count(1) from workattendance where userid=?" + sqlAppend ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				resultValue = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("查询考勤列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return resultValue;
	}

	public List<WorkAttendance> getWorkAttendanceQuery(ElNode dep, int subdep,int pageNow, int pageSize,Timestamp starttime,Timestamp endtime )
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		List<WorkAttendance> workAttendanceList = new ArrayList<WorkAttendance>();
		try {
			boolean consub = subdep == 1 ? true : false;
			
			ct = DBConnection.getConnection();
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(riqi,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"' ";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(riqi,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"' ";
			
			sql = "select b.*,rn from (select a.*,rownum rn from (select w.* from workattendance w " +
					" join  eluser e on w.userid=e.id  " +
					" join (" +
			((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
			.generateSQLByTree("department", dep, consub) + ") dep on e.depid=dep.id " + 
					"  " + sqlAppend + 
					" order by riqi desc) a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			
			WorkAttendance workAttendance ;
			while(rs.next()){
				workAttendance = new WorkAttendance(rs.getInt("userid"));
				workAttendance.setSigndaotime(rs.getTimestamp("signdaotime"));
				workAttendance.setSigntuitime(rs.getTimestamp("signtuitime"));
				workAttendance.setId(rs.getInt("id"));
				workAttendance.setMark(rs.getString("mark"));
				workAttendance.setResult(rs.getString("result"));
				workAttendance.setRelateleave(rs.getString("relateleave"));
				workAttendance.setLeaveType(rs.getString("leaveType"));
				workAttendance.setRiqi(rs.getTimestamp("riqi"));
				workAttendanceList.add(workAttendance);
			}
			
		} catch (Exception e) {
			logger.error("查询考勤列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return workAttendanceList;
	}

	public int getWorkAttendanceSizeQuery(ElNode dep, int subdep,Timestamp starttime,Timestamp endtime)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		int resultValue = 0;
		try {
			boolean consub = subdep == 1 ? true : false;
			
			ct = DBConnection.getConnection();
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(riqi,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"' ";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(riqi,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"' ";
			
			sql = "select count(1) from workattendance w" +
					" join eluser e on w.userid=e.id " +
					" join (" +
			((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
			.generateSQLByTree("department", dep, consub) +  " ) dep on e.depid=dep.id " + 
					"  " + sqlAppend ;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				resultValue = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("查询考勤列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return resultValue;
	}

	public void setAttendanceCount(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		AttendanceCount ac = null;
		String result = "";
		try {
			ct = DBConnection.getConnection();
			
			sql = "select count(1) as chidao_count from workattendance where userid = ? and result = '迟到' " +
					" union all " + 
					" select count(1) as zaotui_count from workattendance where userid = ? and result = '早退' " + 
					" union all " + 
					" select count(1) as queqin_count from workattendance where userid = ? and result = '缺勤' ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elUser.getId());
			ps.setInt(2, elUser.getId());
			ps.setInt(3, elUser.getId());
			rs = ps.executeQuery();
			
			while(rs.next()){
				result += rs.getInt(1) + ",";
				
			}
			result = result.substring(0, result.lastIndexOf(","));
			if(result.indexOf(",")>=0){
				ac = new AttendanceCount();
				ac.setChidaoCount(Integer.parseInt(result.split(",")[0]));
				ac.setZaotuiCount(Integer.parseInt(result.split(",")[1]));
				ac.setQueqinCount(Integer.parseInt(result.split(",")[2]));
				elUser.setAttendanceCount(ac);
			}
			
		} catch (Exception e) {
			logger.error("每个用户设置考勤信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	/**
	 * 每天23：00系统自动插入当天未进行操作的考勤信息
	 */
	public void insertWorkAttendance() throws ElException {
		System.out.println("-----------系统自动insert开始-----------");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		
		//获取当前时间的年月日
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String date = String.valueOf(cal.get(Calendar.DATE));
		if(Integer.parseInt(month) <10 && Integer.parseInt(month)>=1){
			month = "0" + Integer.parseInt(month);
		}
		if(Integer.parseInt(date) <10 && Integer.parseInt(date)>=1){
			date = "0" + Integer.parseInt(date);
		}
		//----------2013****----------日期
		String value = String.valueOf( cal.get(Calendar.YEAR)) + "/" + month  +"/" + date ;
		long now = cal.get(Calendar.HOUR_OF_DAY) * 3600 + cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);
		
		List<ELUser> elUsers = new ArrayList<ELUser>();
		ELUser eu = null;
		List<WorkAttendance> workAttendanceList = new ArrayList<WorkAttendance>();
		WorkAttendance workAttendance = null;
		
		Attendance attendance = this.getAttendance();
		
		try {
			ct = DBConnection.getConnection();
			
			sql = "select eu.id from eluser eu left join department d on eu.depid=d.id " +
					" left join elrole er on eu.role=er.id ";
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				eu = new ELUser(rs.getInt("id"));
				elUsers.add(eu);
			}
			
			sql = "select id,userid,signdaotime,signtuitime,result from workAttendance  where to_char(riqi,'yyyy/mm/dd') = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, value);
			rs = ps.executeQuery();
			while(rs.next()){
				workAttendance = new WorkAttendance();
				workAttendance.setId(rs.getInt("id"));
				workAttendance.setUserid(rs.getInt("userid"));
				workAttendance.setSigndaotime(rs.getTimestamp("signdaotime"));
				workAttendance.setSigntuitime(rs.getTimestamp("signtuitime"));
				workAttendance.setResult(rs.getString("result"));
				workAttendanceList.add(workAttendance);
			}
			
			
			//遍历插入
			if(workAttendanceList.size() == 0){//当天还没有用户考勤
				for(ELUser e:elUsers){
					System.out.println("-----------系统自动insert开始,用户id为"+e.getId()+"------------");
					this.insertWorkAttendanceByUserId(e.getId());
				}
			}else{//有用户考勤
				for(ELUser e:elUsers){
					for (int i=0;i<workAttendanceList.size();i++){
						if(e.getId() != workAttendanceList.get(i).getUserid()){//该用户当天没有进行考勤记录
							if(i == workAttendanceList.size() - 1){
								System.out.println("-----------系统自动insert开始,用户id为"+e.getId()+"------------");
								this.insertWorkAttendanceByUserId(e.getId());
							}
						}else {//该用户当天有考勤
							if(workAttendanceList.get(i).getResult()!= null && !workAttendanceList.get(i).getResult().equals("")){
								System.out.println("-----------系统自动update开始,用户id为"+e.getId()+"------------");
								if(workAttendanceList.get(i).getSigndaotime() == null && 
										workAttendanceList.get(i).getSigntuitime() == null){//没有签到时间和签退时间
									this.updateWorkAttendanceByUserId(e.getId(),"缺勤",workAttendanceList.get(i).getId());
								}else if(workAttendanceList.get(i).getSigndaotime() != null && 
										workAttendanceList.get(i).getSigntuitime() == null){//有签到没签退
									if(this.getSFM(workAttendanceList.get(i).getSigndaotime())>this.getSFM(attendance.getWorktime())){
										this.updateWorkAttendanceByUserId(e.getId(),"迟到且早退",workAttendanceList.get(i).getId());
									}else if(this.getSFM(workAttendanceList.get(i).getSigndaotime())<this.getSFM(attendance.getWorktime())){
										this.updateWorkAttendanceByUserId(e.getId(),"早退",workAttendanceList.get(i).getId());
									}
								}else if(workAttendanceList.get(i).getSigndaotime() == null && 
										workAttendanceList.get(i).getSigntuitime() != null){//有签退没签到
									if(this.getSFM(workAttendanceList.get(i).getSigntuitime())>this.getSFM(attendance.getOutworktime())){
										this.updateWorkAttendanceByUserId(e.getId(),"迟到",workAttendanceList.get(i).getId());
									}else if(this.getSFM(workAttendanceList.get(i).getSigntuitime())<this.getSFM(attendance.getOutworktime())){
										this.updateWorkAttendanceByUserId(e.getId(),"迟到且早退",workAttendanceList.get(i).getId());
									}
								}
							}
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			logger.error("定时，系统自动insert失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		System.out.println("-----------系统自动insert结束-----------");
		
	}
	
	public void updateWorkAttendanceByUserId(int userid,String result,int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		//获取当前时间的年月日
		Calendar cal = Calendar.getInstance();
		String value = String.valueOf( cal.get(Calendar.YEAR)) + "/" + String.valueOf( cal.get(Calendar.MONTH) + 1) +"/" + String.valueOf( cal.get(Calendar.DATE)) ;
		try {
			ct = DBConnection.getConnection();
			
			sql = " update workattendance set result = ? where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, result);
			ps.setInt(2, id);
			ps.executeUpdate();
			
			
		} catch (Exception e) {
			logger.error("定时，系统自动更新一条记录失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void insertWorkAttendanceByUserId(int userid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		//获取当前时间的年月日
		Calendar cal = Calendar.getInstance();
		String value = String.valueOf( cal.get(Calendar.YEAR)) + "/" + String.valueOf( cal.get(Calendar.MONTH) + 1) +"/" + String.valueOf( cal.get(Calendar.DATE)) ;
		try {
			ct = DBConnection.getConnection();
			
			sql = " insert into workattendance (userid,riqi,result) " +
					" values (?,?,?) ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis() - 1));
			ps.setString(3, "缺勤");
			ps.executeUpdate();
			
			
		} catch (Exception e) {
			logger.error("定时，系统自动插入一条记录失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Map<String, Object> checkIsSign(WorkAttendance workAttendance,
			String tablename) throws ElException {
		HashMap<String,Object> map = new HashMap<String,Object>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			
			sql = " select BQGL_BQDQSJ,BQGL_BQLX from " + tablename + " where id = " + workAttendance.getRelateretroactive();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				map.put("BQGL_BQDQSJ", rs.getTimestamp(1));
				map.put("BQGL_BQLX", rs.getString(2));
			}
			
		} catch (Exception e) {
			logger.error("补签失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return map;
	}

	public Map<String, Integer> getKqyl(int userid,int pageNow, int pageSize) throws ElException {
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int chidao = 0;//迟到
		int zaotui = 0;//早退
		int queqin = 0;//缺勤
		int chidao_zaotui = 0;//迟到且早退
		int qingjia = 0;//请假
		int waichu = 0;//外出
		int jiaban = 0;//加班
		int qingjiatianshu = 0;//请假天数
		String result ;
		String relateid ;
		try {
			ct = DBConnection.getConnection();
			
			if(userid !=0){
				sql = "select result,relateleave from workattendance where userid =" + userid ;
			}else if(userid ==0){
				sql = "select result,relateleave from workattendance " ;
			}
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				result = rs.getString("result");
				relateid = rs.getString("relateleave");
				if(result != null && !result.equals("")){
					if(result.equals("迟到")){
						chidao++;
					}else if(result.equals("早退")){
						zaotui++;
					}else if(result.equals("缺勤")){
						queqin++;
					}else if(result.equals("迟到且早退")){
						chidao_zaotui++;
					}else if(result.equals("请假")){
						qingjia++;
					}else if(result.equals("外出")){
						waichu++;
					}else if(result.equals("加班")){
						jiaban++;
					}
				}
				
				if(relateid != null && !relateid.equals("")){//计算请假天数
					if(userid != 0){
						sql = "select QXJGL_QJTS from QXJGL where userid =" + userid + " and id in (" +relateid+ ") ";
						ps = ct.prepareStatement(sql);
						rs = ps.executeQuery();
						while(rs.next()){
							qingjiatianshu += rs.getInt(1);
						}
					}
				}
			}
			map.put("chidao", chidao);
			map.put("zaotui", zaotui);
			map.put("queqin", queqin);
			map.put("chidao_zaotui", chidao_zaotui);
			map.put("qingjia", qingjia);
			map.put("waichu", waichu);
			map.put("jiaban", jiaban);
			map.put("chidaotianshu", qingjiatianshu);
			
		} catch (Exception e) {
			logger.error("考勤一览失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return map;
	}

	public int getKaoqinCount(int userid, String type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int value = 0;
		try {
			ct = DBConnection.getConnection();
			sql = " select count(1) from workattendance where userid=" + userid +" " + " and result = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, type);
			rs = ps.executeQuery();
			if (rs.next()) {
				value = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("考勤信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}

	public void updateWorkAttendanceResult(WorkAttendance workAttendance)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			
			
			sql = "update workattendance set result = ? where id=?" ;
			ps = ct.prepareStatement(sql);
			ps.setString(1, workAttendance.getResult());
			ps.setInt(2, workAttendance.getId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改考勤失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void updateWorkAttendanceById(int id, int type, String value)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlSet = "";
		try {
			ct = DBConnection.getConnection();
			
			if(type == 1){
				sqlSet = " relateleave = " + value;
			}else if(type == 2){
				sqlSet = " relateout = " + value;
			}else if(type == 3){
				sqlSet = " relateretroactive = " + value;
			}
			sql = "update workattendance set "+sqlSet+" where id=?" ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("根据id修改考勤信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public String getSignTuiResult() throws ElException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addKaoqinInfo(String result, int type, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlcolumn = "";
		
		try {
			ct = DBConnection.getConnection();
			
			if(type == 1){
				sqlcolumn += "signdaotime";
			}else if(type == 2){
				sqlcolumn += "signtuitime";
			}
			
			if(type == 1 || type == 2 ){
				sql = "insert into workattendance ("+sqlcolumn+",riqi,userid,result) " +
				" values (?,?,"+userid+",'"+result+"')";
			}
			ps = ct.prepareStatement(sql);
			if(type == 1){
				ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			}else if(type == 2){
				ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			}
			
			ps.executeUpdate();
			
//			sql = "select workattendance_sequence.currval from dual";
//			ps = ct.prepareStatement(sql);
//			rs = ps.executeQuery();
//			if(rs.next()){
//				returnValue = rs.getInt(1);
//			}
			
		} catch (Exception e) {
			logger.error("插入考勤表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void updateKaoqinInfo(String result, int type, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlcolumn = "";
		
		try {
			ct = DBConnection.getConnection();
			
			if(type == 2){
				sqlcolumn += "signtuitime";
			}
			
			if(type == 1 || type == 2 ){
				sql = "update workattendance set " + sqlcolumn + "=?,result=?";
			}
			ps = ct.prepareStatement(sql);
			if(type == 2){
				ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				ps.setString(2, result);
			}
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改考勤表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

}
