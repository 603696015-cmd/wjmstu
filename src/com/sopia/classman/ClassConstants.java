package com.sopia.classman;

public class ClassConstants {
	 
	/***培训班****/
	public static final String CLTYPE_ADD="classtype.add";
	public static final String CLTYPE_ALTER="classtype.alter";
	public static final String CLTYPE_DELETE ="classtype.delete";
	public static final String CLTYPE_QUERY_BYID="classtype.query.byid";
	public static final String CLTYPE_QUERY_BYPARENT="classtype.query.byparentid";
	public static final String CLTYPE_LIRID="classtype.lirid";
	//班级
	public static final String CLASS_ADD="class.add";
	public static final String CLASS_ALTER="class.alter";
	public static final String CLASS_MAN_MYLIST="class.man.mylist";
	public static final String CLASS_MAN_MYLISTSIZE="class.man.mylistSize";
	public static final String CLASS_PURVIEW_MYLIST="class.purview.mylist";
	public static final String CLASS_PURVIEW_MYLISTSIZE="class.purview.mylistSize";
	public static final String CLASS_MAN_MYSHLIST="class.man.myShlist";
	public static final String CLASS_MAN_MYSHLISTSIZE="class.man.myShlistSize";
	public static final String CLASS_MAN_BYUIDANDID="class.man.byuidandid";
	public static final String CLASS_APPLY_DELETE ="class.apply.delete";
	public static final String CLASS_STAT_CLASSLIST ="class.stat.classlist";
	public static final String CLASS_STAT_CLASSLISTSIZE ="class.stat.classlistSize";
	
	//班级课程
	public static final String CLASS_COURSE="class.course";
	public static final String CLASS_COURSE_PAGE="class.course.page";
	public static final String CLASS_COURSE_PAGESIZE="class.course.pageSize";
	public static final String CLASS_COURSE_ALL="class.course.all";
	public static final String CLASS_COURSE_ADD="class.course.add";
	public static final String CLASS_COURSE_DELETE="class.course.delete";
	public static final String CLASS_COURSE_CREDIT_ALTER ="class.coruse.credit.alter";
	//班级分配
	public static final String CLASS_ASSIGN_SUPER ="class.assign.super";
	public static final String CLASS_ASSIGN_THIS ="class.assign.this";
	public static final String CLASS_CANASSIGN_USER = "class.canassgin.user";
	public static final String CLASS_ASSIGNED_USER = "class.assigned.user";
	public static final String CLASS_ASSIGN2USER_ADD="class.assign2user.add";
	public static final String CLASS_ASSIGN2USER_ADD2="class.assign2user.add2";
	public static final String CLASS_ASSIGN2USER_COURSE_BYCLID="class.assign2user.course.byclid";
	public static final String CLASS_ASSIGN2USER_DELETE="class.assign2user.delete";
	public static final String CLASS_CANASSIGN_DEPS="class.canassgin.deps";
	public static final String CLASS_ASSIGNED_DEPS="class.assigned.deps";
	public static final String CLASS_ASSIGN2DEP_ADD="class.assign2dep.add";
	public static final String CLASS_ASSIGN2DEP_DELETE="class.assign2dep.delete";
	public static final String CLASS_APPLYED = "class.applyed";
	public static final String CLASS_GRADUATE_APPLY_LIST = "class.graduate.apply.list";
	public static final String CLASS_GRADUATE_APPLY_LIST_SIZE = "class.graduate.apply.list.size";
	//学员端
	public static final String CLASS_APPLY_THIS ="class.apply.this";
	public static final String CLASS_APPLY_SELECT ="class.apply.select";
	public static final String CLASS_APPLY ="class.apply";
	public static final String CLASS_APPLY_ALREADY ="class.apply.already";
	public static final int CLASS_APPLY_STATUS_WAIT= 1 ;
	public static final int CLASS_APPLY_STATUS_YES= 2 ;
	public static final int CLASS_APPLY_STATUS_NO= 3 ;
//	public static final String CLASS_MY_STUDY="class.my.study";
//	public static final String CLASS_MY_STUDY_CANG="class.my.study.cang";
	public static final String CLASS_BYUID="class.byid";
	public static final String CLASS_BYUNAME="class.byname";
	public static final String CLASS_BYUID_CISCO = "class.byid_cisco";
	public static final String CLASS_APPLY_STATUS_SET = "class.apply.status.set";
	public static final String CLASS_APPLY_STATUS_SET_NO = "class.apply.status.set.no";
	public static final String CLASS_DELTE_APPLY_LIST="class.delete.apply.list";
	public static final String CLASS_DELTE_APPLY_LIST_SIZE="class.delete.apply.listSize";
	//常量
//	public static final String CLASSTYPE_STATUS_DELETE_YES="1";
//	public static final String CLASSTYPE_STATUS_DELETE_NO="0";
//	public static final String CLASSTYPE_STATUS_DELETE_ALL="";
	
//	/** 未通过 */
//	public static final int CLASS_STATUS_NOCHECK=4;
//	/** 已创建 */
//	public static final int CLASS_STATUS_CREATE=3;
//	/** 审核 */
//	public static final int CLASS_STATUS_CHECK=2;		
//	/** 开通 */
//	public static final int CLASS_STATUS_OPEN_YES=1;	
//	/** 关闭 */
//	public static final int CLASS_STATUS_OPEN_NO=0;		
//	/** 已删除 */
//	public static final int CLASS_STATUS_DELETE=-1;		
//	/** 删除等待中 */
//	public static final int CLASS_STATUS_DELETE_WAIT=-2;

	public static final int CLASS_STATUS_INMAKING = 0;							//制作中
	public static final int CLASS_STATUS_PRELIMINARYEXAMINATION_WAIT = 1;		//初审等待中
	public static final int CLASS_STATUS_PRELIMINARYEXAMINATION_NOTGO = 2;		//初审不通过
	public static final int CLASS_STATUS_FINAL_WAIT = 3;						//终审等待中
	public static final int CLASS_STATUS_FINAL_NOTGO = 4;						//终审不通过
	public static final int CLASS_STATUS_HASOPENED = 5;							//已开通
	public static final int CLASS_STATUS_ALTER_WAIT = 6;						//修改等待中
	public static final int CLASS_STATUS_ALTER = 7;								//修改中
	public static final int CLASS_STATUS_DELETE_WAIT = 8;						//删除等待中
	public static final int CLASS_STATUS_DELETE = 9;							//已删除 
	public static final int CLASS_STATUS_CLOSE= 10;								//关闭 
	public static final int CLASS_STATUS_SUSPENDED= 11;							//暂停
	
//	public static final int CLASS_COURSE_STATUS_XX= 1 ;
//	public static final int CLASS_COURSE_STATUS_BX= 0 ;
	public static final String CLASS_STATUS_SET = "class.status.set";
	/***培训班课程学分设置****/
	public static final String CLASS_COURSE_CREDIT = "class.course.credit";
	

	public static final int CLASS_SQFS_FP = 0;//分配
	public static final int CLASS_SQFS_SQ = 1;//申请
 }
