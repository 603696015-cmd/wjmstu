package com.sopia.attendance.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sopia.attendance.entity.Attendance;
import com.sopia.attendance.entity.WorkAttendance;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public interface AttendanceDao {
	
	public List<ELUser> listUsers(ElNode dep, int subdep, ELUser eu,
			int pageNow, int pageSize,String ordercolumn,String ordersc) throws ElException;
	
	public int listUsersSize(ElNode dep, int subdep, ELUser eu)
	throws ElException;
	
	/**
	 * 添加考勤设置
	 * @param attendance
	 * @throws ElException
	 */
	public void addAttendance(Attendance attendance) throws ElException;
	
	/**
	 * 修改考勤设置
	 * @param attendance
	 * @throws ElException
	 */
	public void updateAttendance(Attendance attendance) throws ElException;
	
	/**
	 * 获取考勤设置信息
	 * @return
	 * @throws ElException
	 */
	public Attendance getAttendance() throws ElException;
	
	
	/**
	 * 根据userid获取用户的mac地址
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public String getMacAddressByUserId(int userid) throws ElException;
	
	/**
	 * 修改userid的mac地址
	 * @param userid
	 * @throws ElException
	 */
	public void updateMacAddressByUserId(ELUser elUser) throws ElException;
	
	/**
	 * 根据userid和当前时间获取考勤信息
	 * @param userid
	 * @param date
	 * @return
	 * @throws ElException
	 */
	public WorkAttendance getWorkAttendanceByUserIdAndDate(int userid) throws ElException;
	
	/**
	 * 根据id获取考勤信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public WorkAttendance getAttendanceById(int id) throws ElException;
	
	/**
	 * 根据id修改考勤信息
	 * @param id
	 * @param type
	 * @param value
	 * @throws ElException
	 */
	public void updateWorkAttendanceById(int id,int type,String value) throws ElException;
	
	public WorkAttendance getWorkAttendanceById(int id) throws ElException;
	/**
	 * 插入考勤表
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int addWorkAttendance(WorkAttendance workAttendance,int userid,int type) throws ElException;
	
	/**
	 * 根据id，userid,type更新考勤表
	 * @param userid
	 * @param workAttendance
	 * @param type
	 * @throws ElException
	 */
	public void updateWorkAttendance(int userid,WorkAttendance workAttendance,int type) throws ElException;
	
	/**
	 * 查询考勤列表
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<WorkAttendance> getWorkAttendanceByUserId(int userid,int pageNow, int pageSize,Timestamp starttime,Timestamp endtime) throws ElException;
	
	/**
	 * 考勤一览
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public Map<String,Integer> getKqyl(int userid,int pageNow, int pageSize) throws ElException;
	
	/**
	 * 查询考勤列表Size
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getWorkAttendanceSizeByUserId(int userid,Timestamp starttime,Timestamp endtime) throws ElException;
	
	/**
	 * 考勤查询
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<WorkAttendance> getWorkAttendanceQuery(ElNode dep, int subdep,int pageNow, int pageSize,Timestamp starttime,Timestamp endtime) throws ElException;
	
	/**
	 * 考勤查询size
	 * @return
	 * @throws ElException
	 */
	public int getWorkAttendanceSizeQuery(ElNode dep, int subdep,Timestamp starttime,Timestamp endtime) throws ElException;
	
	/**
	 * 每个用户设置考勤信息
	 * @param elUser
	 * @throws ElException
	 */
	public void setAttendanceCount(ELUser elUser) throws ElException;
	
	/**
	 * 定时，系统自动insert
	 * @throws ElException
	 */
	public void insertWorkAttendance() throws ElException;
	
	public Map<String,Object> checkIsSign(WorkAttendance workAttendance,String tablename) throws ElException;
	
	
	public int getKaoqinCount(int userid,String type) throws ElException;
	
	public void updateWorkAttendanceResult(WorkAttendance workAttendance) throws ElException;
	
	/**
	 * 签退时获取结果
	 * @return
	 * @throws ElException
	 */
	public String getSignTuiResult() throws ElException;
	
	/**
	 * 添加考勤信息
	 * @param result
	 * @param type
	 * @param userid
	 * @throws ElException
	 */
	public void addKaoqinInfo(String result,int type,int userid) throws ElException;
	
	/**
	 * 修改考勤信息
	 * @param result
	 * @param type
	 * @param userid
	 * @throws ElException
	 */
	public void updateKaoqinInfo(String result,int type,int userid) throws ElException;
}
