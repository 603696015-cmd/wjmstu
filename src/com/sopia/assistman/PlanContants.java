package com.sopia.assistman;

public class PlanContants {
	public static final String PLAN_ADD="plan.add";
	public static final String PLAN_ALTER="plan.alter";
	public static final String PLAN_LIST_BYUID="plan.list.byuid";
	public static final String PLAN_BYID="plan.byid";
	public static final String PLAN_STATUS_SET="plan.status.set";
	public static final String PLAN_VERIFY_ADD="plan.verify.add";
	public static final String PLAN_VERIFY_LIST="plan.verify.list";
	public static final String PLAN_VERIFIED_LIST="plan.verified.list";
	public static final String PLAN_SUPERVERIFIED_SET="plan.superverified.set";
	public static final String PLAN_CARRYOUT_LIST="plan.carryout.list";
	
	public static final String PLANSTAGE_ADD="planstage.add";
	public static final String PLANSTAGE_ALTER="planstage.alter";
	public static final String PLANSTAGE_DELETE="planstage.delete";
	public static final String PLANSTAGE_LIST_BYPID="planstage.list.bypid";
	public static final String PLANSTAGE_LIST_BYID="planstage.list.byid";
	public static final String PLANSTAGE_CARRYOUT="planstage.carryout";

	public static final String PLANSTUFF_ADD="planstuff.add";
	public static final String PLANSTUFF_DELETE="planstuff.delete";
	public static final String PLANSTUFF_LIST_BYPSID="planstuff.list.bypsid";
	
	//计划状态
	public static final int PLAN_STATUS_MAKING=0;//制作中
	public static final int PLAN_STATUS_SHWAITING=1;//审核等待中
	public static final int PLAN_STATUS_YES=2;//通过审核
	public static final int PLAN_STATUS_NO=3;//不通过审核
	public static final int PLAN_STATUS_FINISHED=4;//计划实施完成
	
}
