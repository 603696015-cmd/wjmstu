package com.sopia.schedule.action;

import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;

import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.schedule.dao.impl.ScheduleDaoImpl;
import com.sopia.schedule.dao.ScheduleDao;
import com.sopia.schedule.entities.Schedule;

public class ScheduleAction extends BaseAction{
	private Schedule schedule;
	List<Schedule> schedules_list= new ArrayList<Schedule>();
	private ScheduleDao scheduleDao;
	
	private Department depTree;
	private Department department;
	
	private String ismodify;

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	
	public String addScheduleTable ()throws ElException
	{
		
		
		return "add_jsp";
	}
	
	public String addScheduleToDb ()throws ElException
	{
		
		
//		System.out.print("\n"+schedule.getTopic()+"::"+schedule.getDatetime());
		//schedule.set
		
		schedule.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		//System.out.print("\nid:"+schedule.getUserid()+"\n");
		scheduleDao.addSchedule(schedule);
		return "addScheduleSuccess";
	}
	
	public String listSchedule()throws ElException
	{
//		int userid;
//		userid=getSessionIntValue(ElConstants.SESSION_USERID);
		//System.out.print("\nid:"+getSessionIntValue(ElConstants.SESSION_USERID)+"\n");
		//schedules_list=scheduleDao.selectMyAllSchedule(getSessionIntValue(ElConstants.SESSION_USERID));
		
		//----分页查询
		//System.out.print("\n:pagenow:"+getPageNow()+":pagesize:"+getPageSize());
//		schedules_list=scheduleDao.selectMyAllSchedule(userid,getPageNow(),getPageSize());
//		count = scheduleDao.selectMyAllScheduleCount(userid);
		
		//-------------分页组合查询
		if(schedule == null) schedule = new Schedule();
		schedule.setId(getSessionIntValue(ElConstants.SESSION_USERID));
		schedules_list=scheduleDao.selectMyAllSchedule(schedule,getPageNow(),getPageSize());
		count = scheduleDao.selectMyAllScheduleCount(schedule);
		
		return "listSchedule";
	}
	
	public String delSchedule()throws ElException
	{
		int scheduleid;
		scheduleid=schedule.getId();
		scheduleDao.delScheduleById(scheduleid);
		return "delScheduleSuccess";
	}
	
	public String getScheduleById() throws ElException
	{ 
		schedule=scheduleDao.selectScheduleById(schedule.getId());
		
		if(ismodify==null) ismodify="view";
		if(ismodify.equals("modify"))
				return "updateSchedule";
		else
			return "getScheduleSuccess";
	}
	
	
	
	
	public String searchSchedule() throws ElException
	{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department != null) 
			department  = departmentDao.getDepById(department.getId());
		else
			department = new Department();
		if(department.getId()>0)
		{
			//schedules_list=scheduleDao.searchScheduleByDepid(department.getId(),getPageNow(),getPageSize());
			schedules_list=scheduleDao.searchScheduleByDepid(department.getLid(),department.getRid(),getPageNow(),getPageSize());
			//count=scheduleDao.searchScheduleByDepidcount(department.getId());
			count=scheduleDao.searchScheduleByDepidcount(department.getLid(),department.getRid());
		}
		
		//System.out.print("\nid:"+department.getId()+";lid:"+department.getLid()+";rid:"+department.getRid()+"\n");
		
		return "search_jsp";
	}
	
	
	//-----------------------setters   and   getters---------------------------------------
	public String updateSchedule() throws ElException
	{
		scheduleDao.updateSchedule(schedule);
		//return "updatesuccess";
		return "updateSuccessAndList";
	}
	

	public ScheduleDao getScheduleDao() {
		return scheduleDao;
	}

	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	public List<Schedule> getSchedules_list() {
		return schedules_list;
	}

	public void setSchedules_list(List<Schedule> schedules_list) {
		this.schedules_list = schedules_list;
	}

	public String getIsmodify() {
		return ismodify;
	}

	public void setIsmodify(String ismodify) {
		this.ismodify = ismodify;
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


	
}
