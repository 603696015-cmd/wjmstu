package com.sopia.statman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.statman.dao.StatisticscoreDao;
import com.sopia.studyman.entities.MyCourse;

public class StatmanscoseAciton extends BaseAction  {
	
	private Department depTree;
	private Department deprTree;
	private Integer deptid; //≤ø√≈ID
	private Department department;
	private StatisticscoreDao  statisticscoreDao;
    private int sub_department;
    private List<ELUser>  lu;
    private ELUser elUser;
    private List<MyCourse> myCourses;
    private List<Integer>  li;
    
    
    
	
    private  int  my ;
	
    
    public int getMy() {
		return my;
	}



	public void setMy(int my) {
		this.my = my;
	}



	public List<MyCourse> getMyCourses() {
		return myCourses;
	}



	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}



	public ELUser getElUser() {
		return elUser;
	}



	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}



	public List<ELUser> getLu() {
		return lu;
	}



	public void setLu(List<ELUser> lu) {
		this.lu = lu;
	}



	public int getSub_department() {
		return sub_department;
	}



	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}



	public Department getDepartment() {
		return department;
	}



	public void setDepartment(Department department) {
		this.department = department;
	}



	public StatisticscoreDao getStatisticscoreDao() {
		return statisticscoreDao;
	}



	public void setStatisticscoreDao(StatisticscoreDao statisticscoreDao) {
		this.statisticscoreDao = statisticscoreDao;
	}



	public Department getDepTree() {
		return depTree;
	}



	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}



	public Department getDeprTree() {
		return deprTree;
	}



	public void setDeprTree(Department deprTree) {
		this.deprTree = deprTree;
	}



	public Integer getDeptid() {
		return deptid;
	}



	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}



	public String  getstudentcouse() throws ElException{
		
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		}else{
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(this.getDeptid()==null||this.getDeptid()<=0){
			department = depTree;
		}else{
			department  = departmentDao.getDepById(this.getDeptid());
		}
		lu  =statisticscoreDao.studentscoer(department, sub_department, elUser,getPageNow(), getPageSize());
		count =statisticscoreDao.studentscoersize(department, sub_department, elUser);
		return  "getstudentcouse_success";
		
	}
	public String  getstudentcousebyuserid() throws ElException{
		int userid;
		if(my==1){
		
			 userid = elUser!=null?elUser.getId(): getSessionIntValue(ElConstants.SESSION_USERID);
			 
			
		}else{
			 userid = elUser.getId();
		}
		myCourses=statisticscoreDao.scoerinfo_list_byuserid(userid,getPageNow(),getPageSize());
		count=statisticscoreDao.scoerinfo_size_byuserid(userid);
		if(my==1){
			li=statisticscoreDao.allscoerinfo_list_byuserid(userid);
			return  "getmycouse_success";
		}else{
			return  "getstudentcousebyuserid_success";
		}
		
		
	}



	public List<Integer> getLi() {
		return li;
	}



	public void setLi(List<Integer> li) {
		this.li = li;
	}

}
