package com.sopia.assistman.dao;

import java.util.List;

import com.sopia.assistman.entities.Plan;
import com.sopia.assistman.entities.PlanStage;
import com.sopia.assistman.entities.PlanStuff;
import com.sopia.assistman.entities.PlanVerify;
import com.sopia.common.ElException;

public interface PlanDao {
	public int addPlan (Plan plan) throws ElException;
	public List<Plan> listPlansByUid(int userid,int pageNow,int pageSize)throws ElException;
	public Plan getPlanByid(int id)throws ElException;
	
	public void alterPlan(Plan plan) throws ElException;
	
	public List<PlanStage> listPlanStageBYPid(int id) throws ElException;
	public PlanStage getpStageById(int id) throws ElException;
	public void deletePlanStage(int id) throws ElException;
	public void addPlanStage( PlanStage planStage) throws ElException;
	public void addPlanStageStuff(PlanStuff planStuff) throws ElException ;
	public void deletePlanStageStuff(int id) throws ElException;
	public List<PlanStuff> listPStuffByPsId(int id)throws ElException;
	public void alterPlanStage(PlanStage planStage) throws ElException;
	public void planStageCarryout(PlanStage planStage) throws ElException;
//	public void planStatusSet(int plid,int status,int userid)throws ElException;
	public void planVerifySet(int plid,int status,int userid,int role) throws ElException;
	public List<Plan> listVerfiyPlans(int depid,int pageNow, int pageSize)throws ElException;
	public List<PlanVerify> getPlanVerfiysByPid(int pid) throws ElException;
	public List<Plan> listPlansByDepid(int depid, int pageNow, int pageSize)
	throws ElException;
}
