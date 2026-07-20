package com.sopia.schedule.dao;

import java.util.ArrayList;
import java.util.List;

import com.sopia.schedule.entities.LogStuff;
import com.sopia.schedule.entities.Logfile;
import com.sopia.schedule.entities.Schedule;

import com.sopia.common.ElException;
 




public interface LogDao {
	
	
	/*
	 * 添加日志
	 */
	//public void addLog(Logfile log) throws ElException;
	public int addLog(Logfile log) throws ElException;
	
	/*
	 * 添加附件
	 */
	public void addLogStuff(int logid,String stuffaddr,String title) throws ElException;
	
	/*
	 * 查询我的日志
	 * 通过用户id
	 * 返回一个Logfile 列表
	 */
	public List<Logfile> selectMyLogsByUserId(int userid,int pageNow,int pageSize)throws ElException;
	public int selectMyLogsByUserIdCount(int userid)throws ElException;
	
	public List<Logfile> selectMyLogsByUserId(Logfile log,int pageNow,int pageSize)throws ElException;
	public int selectMyLogsByUserIdCount(Logfile log)throws ElException;
	
	/*
	 * 删除日志通过日志id
	 */
	public void delLogByUserId(int id)throws ElException;
	
	/*
	 * 通过log_id同时删除附件
	 */
	public void delLogStuffByLogId(int logid) throws ElException;
	
	/*
	 * 通过id删除附件
	 */
	public void delLogStuffById(int id) throws ElException;
	
	/*
	 * 通过log_id获取附件数量
	 */
	public int getNumOfLogStuffByLogId(int logid) throws ElException;
	
	
	/*
	 * 通过日志id查询日志
	 */
	public Logfile getLogByLogId(int id) throws ElException;
	
	/*
	 * 查询日志附件
	 */
	public List<LogStuff> getListLogStuff(int logid) throws ElException;
	
	/*
	 * 通过id修改日志
	 */
	public void updateLogById(Logfile log)throws ElException;
	
	/*
	 * 部门查询
	 */
	public List<Logfile> searchLogByDepid(int lid,int rid,int pageNow,int pageSize) throws ElException;
	public int searchLogByDepidCount(int lid,int rid) throws ElException;
	
	
	/*
	 * 部门组合查询
	 */
	public List<Logfile> searchLogByDepid(Logfile log,int lid,int rid,int pageNow,int pageSize) throws ElException;
	public int searchLogByDepidCount(Logfile log,int lid,int rid) throws ElException;
	
	
	
	
	
}
