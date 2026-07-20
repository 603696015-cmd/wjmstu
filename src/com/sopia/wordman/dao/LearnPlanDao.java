package com.sopia.wordman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.MyLogin;
import com.sopia.wordman.entities.LearnPlan;

public interface LearnPlanDao {

	public void addLearnPlan(LearnPlan learnplan) throws ElException;
	
	public List<LearnPlan> getallPlan(int pagenow,int pagesize,int userid)throws ElException;
	
	public int getCount(int userid)throws ElException;
	
	public LearnPlan getPlanById(int id)throws ElException;
	
	public void alterPlan(LearnPlan learnplan)throws ElException;
	
	public List<LearnPlan> getallPlanXS(int pagenow,int pagesize,int userid)throws ElException; 
	
	public List<MyLogin> getMyloginInfo(int pagenow,int pagesize,LearnPlan learnplan)throws ElException;
	
	public int getLoginInfoCount(LearnPlan learnplan)throws ElException;
}
