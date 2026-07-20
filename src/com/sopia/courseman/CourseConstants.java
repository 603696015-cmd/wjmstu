package com.sopia.courseman;

public class CourseConstants {
	// 课程类别
	public static final String CTYPE_ADD = "ctype.add";
	public static final String CTYPE_ALTER = "ctype.alter";
	public static final String CTYPE_DELETE = "ctype.delete";
	public static final String CTYPE_QUERY_BYID = "ctype.query.byid";
	public static final String CTYPE_QUERY_CHILD = "ctype.query.child";
	public static final String CTYPE_QUERY_BYPID = "ctype.query.bypid";
	public static final String CTYPE_PARENT_SET = "ctype.parent.set";
	// 课程类别
	public static final String CTYPE_COURSE_QUERY_BYCTID = "ctype.course.query.byctid";
	public static final String CTYPE_COURSE_CTYPE_SET = "ctype.course.ctype.set";
	public static final String CTYPE_LRID="ctype.lrid";
	
	// 课程管理
	public static final String COURSE_ADD = "course.add";
	public static final String COURSE_MAN_MYLIST = "course.man.mylist";
	public static final String COURSE_MAN_MYLIST_SIZE = "course.man.mylist.size";
	public static final String COURSE_QUERY_BYID = "course.query.byid";
	public static final String COURSE_QUERY_BYNAME = "course.query.byname";
	public static final String COURSE_QUERY_BYIDS = "course.query.byids";
	public static final String COURSE_STATUS_SET = "course.status.set";
	public static final String COURSE_STATUS_SET_BYUSER = "course.status.set.byuser";
	public static final String COURSE_DELETE_LIST = "course.delete.list";
	public static final String COURSE_APPLY_THIS = "course.apply.this";
	public static final String COURSE_APPLY_SIZE_THIS = "course.apply.size.this";
	public static final String COURSE_APPLY_SUPER = "course.apply.super";
	public static final String COURSE_APPLY_SIZE_SUPER = "course.apply.size.super";
	public static final String COURSE_CANASSIGN_USERS = "course.canassign.users";
	public static final String COURSE_ASSIGNED_USERS = "course.assigned.users";
	public static final String COURSE_CANASSIGN_DEPS = "course.canassign.deps";
	public static final String COURSE_ASSIGNED_DEPS = "course.assigned.deps";
	public static final String COURSE_ALTER="course.alter";
	public static final String COURSE_ASSIGNE2USER="course.assigne2user";
	public static final String COURSE_ASSIGNE3USER="course.assigne3user";
	
	public static final String COURSE_USER_CHECK="course.user.check";
	public static final String COURSE_ASSIGNE2USER_DELETE="course.assigne2user.delete";
	public static final String COURSE_DEP_CHECK="course.dep.check";
	public static final String COURSE_DEP_ADD="course.dep.add";
	public static final String COURSE_DEP_DELETE="course.dep.delete";
	public static final String COURSE_SELECTED_LIST="course.selected.list";
	public static final String COURSE_SELECTED_SIZE="course.selected.size";
	public static final String COURSE_SELECTED_SET="course.selected.set";
	public static final String COURSE_HOT_SET="course.hot.set";
	public static final String COURSE_STUDY_DELETE="course.study.delete";
	public static final String COURSE_STUDY_DELETE_SIZE="course.study.delete.size";
	public static final String COURSE_STUDY_DELETE_OP="course.study.delete.op";
	public static final String COURSE_STUDY_DELETE_OP_YES_SC="course.study.delete.op.yes.sc";
	public static final String COURSE_STUDY_DELETE_OP_YES_SCP="course.study.delete.op.yes.scp";
	public static final String COURSE_STUDY_DELETE_OP_YES_CA="course.study.delete.op.yes.ca";
	
//	public static final int COURSE_STATUS_MAKEING = 0;
//	public static final int COURSE_STATUS_OPEN = 1;
//	public static final int COURSE_STATUS_OPEN_WAIT = 2;  //同步课堂申请开通状态
//	public static final int COURSE_STATUS_DELETE = 4;
//	public static final int COURSE_STATUS_DELETE_WAIT = 3;
//	public static final int COURSE_STATUS_DISAPPROVE_WAIT = 5;
//	public static final int COURSE_STATUS_DELETEDIS_WAIT = 6;
//	public static final int COURSE_STATUS_APPLYFOR_ALTER = 7; //课堂申请修改状态

	public static final int COURSE_STATUS_INMAKING = 0;							//制作中
	public static final int COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT = 1;		//初审等待中
	public static final int COURSE_STATUS_PRELIMINARYEXAMINATION_NOTGO = 2;		//初审不通过
	public static final int COURSE_STATUS_FINAL_WAIT = 3;						//终审等待中
	public static final int COURSE_STATUS_FINAL_NOTGO = 4;						//终审不通过
	public static final int COURSE_STATUS_HASOPENED = 5;						//已开通
	public static final int COURSE_STATUS_ALTER_WAIT = 6;						//修改等待中
	public static final int COURSE_STATUS_ALTER = 7;							//修改中
	public static final int COURSE_STATUS_DELETE_WAIT = 8;						//删除等待中
	public static final int COURSE_STATUS_DELETE = 9;							//已删除 
	
	public static final int COURSE_ISLINK_BIAOZHUN = 0;
	public static final int COURSE_ISLINK_WAIBU = 1;
	public static final int COURSE_ISLINK_ZHUHEWAIBU = 2;
	public static final int COURSE_ISLINK_DANYISP = 3;
	public static final int COURSE_ISLINK_TBKT = 4;
	public static final int COURSE_ISLINK_SCORM = 5;
	public static final int COURSE_ISLINK_XIANXIA = 6;
	
	public static final int COURSE_STUDY_STATUS_BX = 0;
	public static final int COURSE_STUDY_STATUS_XX = 1;
	public static final int COURSE_STUDY_STATUS_ZX=2;
	// 课程网页管理
	public static final String CPAGE_ADD = "cpage.add";
	public static final String CPAGE_ALTER = "cpage.alter";
	public static final String CPAGE_QUERY_MAX_SORTID = "cpage.query.max.sortid";
	public static final String CPAGE_QUERY_LIST_BYCID = "cpage.query.list.bycid";
	public static final String CPAGE_QUERY_BYID = "cpage.query.byid";
	public static final String CPAGE_QUERY_FIRST_BYCID = "cpage.query.first.bycid";
	public static final String CPAGE_DELETE="cpage.delete";
	public static final String CPAGE_BIGSORT_SET="cpage.bigsort.set";
	public static final String CPAGE_QUERY_CIDANDSID="cpage.query.cidandsid";
	public static final int CPAGE_PROPERTY_Z=0;
	public static final int CPAGE_PROPERTY_J=1;
	public static final int CPAGE_TYPE_TW=0;
	public static final int CPAGE_TYPE_CSP=1;
	public static final int CPAGE_TYPE_JYSP=2;
	public static final int CPAGE_TYPE_WB=3;
	public static final int CPAGE_TYPE_SPXX=4;
	public static final int CPAGE_TYPE_KPXX=5;//宽频学习
	public static final int CPAGE_TYPE_WBKPXX=6;//外部宽频学习
	//20140325修改
	public static final int CPAGE_TYPE_CHSPXX=7;//词汇视频学习
	
	public static final String PPAPER_QUERY_BYCIDANDPID="ppaper.query.bycidandpid";
	public static final String PPAPER_QUERY_BYID="ppaper.query.byid";
	public static final String PPAPER_CHECK_INCP ="ppaper.check.incp";
	public static final String PPAPER_ADD="ppaper.add";
	public static final String PPAPER_MSORTID_INCP="ppaper.msortid.incp";
	public static final String PPAPER_DELETE="ppaper.delete";
	public static final String PPAPER_BIGSORT_SET="ppaper.bigsort.set";
	
	public static final String SPAPER_DELETE="spaper.delete";
	public static final String SPAPER_ADD="spaper.add";
	public static final String SPAPER_CHECK_INSP="spaper.check.insp";
	public static final String SPAPER_QUERY_BYCID="spaper.query.bycid";
	public static final String SPAPER_QUERY_BYID="spaper.query.byid";
	public static final String SPAPER_REQUIZ="spaper.requiz";
	public static final String SPAPER_READ_LIST="spaper.read.list";
	
	public static final String QPAPER_CHECK_INQP="qpaper.check.inqp";
	public static final String QPAPER_ADD="qpaper.add";
	public static final String QPAPER_DELETE="qpaper.delete";
	public static final String QPAPER_QUERY_BYCID="qpaper.query.bycid";
	public static final String QPAPER_READ_BYRID="qpaper.read.byrid";
	public static final String QPAPER_READ_BYRID_SIZE="qpaper.read.byrid.size";
	public static final String QPAPER_REQUIZ="qpaper.requiz";
	// 考试场次
	public static final String EROOM_ADD="eroom.add";
	public static final String EROOM_DELETE="eroom.delete";
	public static final String EROOM_ALTER="eroom.alter";
	public static final String EROOM_QUERY_BYCID="eroom.query.bycid";
	public static final String EROOM_QUERY_BYUID="eroom.query.byuid";
	public static final String EROOM_QUERY_BYUID_SIZE="eroom.query.byuid.size";
	public static final String EROOM_QUERY_BYDEPID="eroom.query.bydepid";
	public static final String EROOM_QUERY_BYDEPID_SIZE="eroom.query.bydepid.size";
	public static final String EROOM_QUERY_BYID="eroom.query.byid";
	public static final String EROOM_QUERY_CAN_ASSIGNUSER="eroom.query.can.assignuser";
	public static final String EROOM_QUERY_ASSIGNEDUSER="eroom.query.assigneduser";
// public static final String
// EROOM_ASSIGNEDUSER_DELETE="eroom.assigneduser.delete";
// public static final String EROOM_ASSIGNEDUSER_ADD="eroom.assigneduser.add";
// public static final String
// EROOM_ASSIGNEDUSER_CHECK="eroom.assigneduser.check";
	public static final String EROOM_QUERY_BYCIDANDT="eroom.query.bycidandt";
	public static final String EROOM_WHITHOUT_COURSE="eroom.whithout.course";
	
	// 课程类别
	public static final String EROOMLIB_ADD = "eroomlib.add";
	public static final String EROOMLIB_ALTER = "eroomlib.alter";
	public static final String EROOMLIB_DELETE = "eroomlib.delete";
	public static final String EROOMLIB_QUERY_BYID = "eroomlib.query.byid";
	public static final String EROOMLIB_QUERY_CHILD = "eroomlib.query.child";
	public static final String EROOMLIB_QUERY_BYPID = "eroomlib.query.bypid";
	public static final String EROOMLIB_PARENT_SET = "eroomlib.parent.set";
	public static final String EROOMLIB_EROOM_QUERY_BYCTID = "eroomlib.eroom.query.byrlibid";
	public static final String EROOMLIB_EROOM_EROOMLIB_SET = "eroomlib.eroom.eroomlib.set";
	public static final String EROOMLIB_LRID="eroomlib.lrid";

	//词汇类别
	public static final String WORDLIB_QUERY_BYPID="word.query.bypid";
	public static final String WORDLIB_ADD = "wordslib.add";
	public static final String WORDLIB_ALTER = "wordslib.alter";
	
	public static final String EXAMPRAC_LIST="examprac.list";
	//考场批次
	public static final String ERBATCHLIB_ADD = "erbatchlib.add";
	public static final String ERBATCHLIB_ALTER = "erbatchlib.alter";
	public static final String ERBATCHLIB_DELETE = "erbatchlib.delete";
	public static final String ERBATCHLIB_QUERY_BYID = "erbatchlib.query.byid";
	public static final String ERBATCHLIB_QUERY_CHILD = "erbatchlib.query.child";
	public static final String ERBATCHLIB_QUERY_BYPID = "erbatchlib.query.bypid";
	public static final String ERBATCHLIB_PARENT_SET = "erbatchlib.parent.set";
	public static final String ERBATCHLIB_ERBATCH_QUERY_BYCTID = "erbatchlib.erbatch.query.byrlibid";
	public static final String ERBATCHLIB_ERBATCH_ERBATCHLIB_SET = "erbatchlib.erbatch.erbatchlib.set";
	public static final String ERBATCHLIB_LRID="erbatchlib.lrid";

	public static final String ERBATCH_ADD = "erbatch.add";
	public static final String ERBATCH_ALTER = "erbatch.alter";
	public static final String ERBATCH_DELETE = "erbatch.delete";
	public static final String ERBATCH_BYID = "erbatch.query.byid";
	public static final String ERBATCH_LIST = "erbatch.query.list";
	public static final String ERBATCH_EROOM_LIST = "erbatch.eroom.list";
	public static final String ERBATCH_EROOM_ADD = "erbatch.eroom.add";
	public static final String ERBATCH_EROOM_CHECK = "erbatch.eroom.check";
	public static final String ERBATCH_EROOM_DELETE = "erbatch.eroom.delete";
	
	//考场状态 
	public static final int EXAMROOM_STATUS_PERSONNELALREADYCHECK = 1;			//人员已复核

	public static final int EXAMROOM_STATUS_INMAKING = 0;						//制作中
	public static final int EXAMROOM_STATUS_PRELIMINARYEXAMINATION_WAIT = 1;	//初审等待中
	public static final int EXAMROOM_STATUS_PRELIMINARYEXAMINATION_NOTGO = 2;	//初审不通过
	public static final int EXAMROOM_STATUS_FINAL_WAIT = 3;						//终审等待中
	public static final int EXAMROOM_STATUS_FINAL_NOTGO = 4;					//终审不通过
	public static final int EXAMROOM_STATUS_HASOPENED = 5;						//已开通
	public static final int EXAMROOM_STATUS_ALTER_WAIT = 6;						//修改等待中
	public static final int EXAMROOM_STATUS_ALTER = 7;							//修改中
	public static final int EXAMROOM_STATUS_DELETE_WAIT = 8;					//删除等待中
	public static final int EXAMROOM_STATUS_DELETE = 9;							//已删除 
	

	public static final int EXAMROOM_SQFS_SQ = 1;							//报名申请
	public static final int EXAMROOM_FPFS_SQ = 0;							//分配
	public static final int EXAMROOM_QJFS_SQ = 2;							//全工类型考场，自动分配
	
	public static final String COURSE_TABLENAME_CLASS_COURSE ="CLASS_COURSE";            //普通培训班
	public static final String COURSE_TABLENAME_CLASS_COURSE_AT ="CLASS_COURSE_AT";          	//自主培训班
	
	
	
	
	


	
}

