package com.sopia.schedule.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sopia.schedule.entities.Schedule;

import com.sopia.common.ElException;
 




public interface ScheduleDao {
	
	
	
	public void addSchedule(Schedule schedule) throws ElException;

	
	public void addScheduleToDb(Schedule schedule) throws ElException;
	
	//根据用户id查询我的日程
	public List<Schedule> selectMyAllSchedule(int userid)throws ElException;
	//分页查询我的日程
	public List<Schedule> selectMyAllSchedule(int userid,int pageNow,int pageSize)throws ElException;
	public int selectMyAllScheduleCount(int userid)throws ElException;
	//组合分页查询我的日程
	public List<Schedule> selectMyAllSchedule(Schedule schedule,int pageNow,int pageSize)throws ElException;
	public int selectMyAllScheduleCount(Schedule schedule)throws ElException;
	
	
	
	
	public void delScheduleById(int scheduleid)throws ElException;
	
	public Schedule selectScheduleById(int scheduleid)throws ElException;
	
	public void updateSchedule(Schedule schedule) throws ElException;
	
	public List<Schedule> searchScheduleByDepid(int depid,int pageNow,int pageSize) throws ElException;
	public int searchScheduleByDepidcount(int depid) throws ElException;
	public List<Schedule> searchScheduleByDepid(int lid,int rid,int pageNow,int pageSize) throws ElException;
	public int searchScheduleByDepidcount(int lid,int rid) throws ElException;
	
	
}
