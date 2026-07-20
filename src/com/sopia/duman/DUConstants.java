package com.sopia.duman;

public class DUConstants {
	/**部门管理**/
	public static final String DEP_ADD="dep.add";
	public static final String DEP_ALTER="dep.alter";
	public static final String DEP_DELETE="dep.delete";
	public static final String DEP_DELETE_USER_SET="dep.delete.user.set";
	public static final String DEP_DELETE_COURSE_SET="dep.delete.course.set";
	public static final String DEP_DELETE_CLASS_SET="dep.delete.class.set";
	public static final String DEP_DELETE_KLTYPE_SET="dep.delete.kltype.set";
	public static final String DEP_DELETE_DEP_SET="dep.delete.dep.set";
	public static final String DEP_QUERY_ROOT="dep.query.root";
	
	public static final String DEP_QUERY_BYID="dep.query.byid";
	public static final String DEP_QUERY_LRID_BYID="dep.query.lrid.byid";
	public static final String DEP_QUERY_PARENT_BYID="dep.query.parent.byid";
	public static final String DEP_QUERY_BYPIDANDCID="dep.query.bypidandcid";
	public static final String DEP_PARENT_SET ="dep.parent.set";
	public static final String DEP_QUERY_SUBS="dep.query.subs";
	public static final String DEP_QUERY_DSUBS="dep.query.dsubs";
	/**部门管理**/
	/**
	 * 账号管理
	 */
	public static final String USER_QUERY_BYID = "user.query.byid";
	public static final String USER_QUERY_BYNAME = "user.query.byname";
	public static final String USER_QUERY_BYUN="user.query.un";
	public static final String USER_QUERY_BYDEPID="user.query.bydepid";
	public static final String USER_QUERY_BYSELECTLEVELID="user.query.byselectlevelid";
	public static final String USER_QUERY_BYDEPIDANDOS="user.query.bydepidandos";
	public static final String USER_QUERY_SIZE_BYDEPIDANDOS="user.query.size.bydepidandos";
	public static final String USER_QUERY_SUBS_BYDEPIDANDOS="user.query.subs.bydepidandos";
	public static final String USER_QUERY_SUBS_SIZE_BYDEPIDANDOS="user.query.subs.size.bydepidandos";
	
	public static final String USER_QUERY_BYDEPIDANDOS_ROLE="user.query.bydepidandos.role";
	public static final String USER_QUERY_SIZE_BYDEPIDANDOS_ROLE="user.query.size.bydepidandos.role";
	public static final String USER_QUERY_SUBS_BYDEPIDANDOS_ROLE="user.query.subs.bydepidandos.role";
	public static final String USER_QUERY_SUBS_SIZE_BYDEPIDANDOS_ROLE="user.query.subs.size.bydepidandos.role";

	public static final String USER_QUERY_VBYDEPIDANDOS="user.query.vbydepidandos";
	public static final String USER_QUERY_VSIZE_BYDEPIDANDOS="user.query.vsize.bydepidandos";
	public static final String USER_QUERY_VSUBS_BYDEPIDANDOS="user.query.vsubs.bydepidandos";
	public static final String USER_QUERY_VSUBS_SIZE_BYDEPIDANDOS="user.query.vsubs.size.bydepidandos";
	public static final String USER_ROLE_SET = "user.role.set";
	public static final String USER_ROLE_DEPMANGER_BACK= "user.role.depmanger.back";
	public static final String USER_ADD="user.add";
	public static final String USER_ALTER="user.alter";
	public static final String USER_DELETE="user.delete";
	public static final String USER_CHECK_PWD="user.check.pwd";
	public static final String USER_MYINFO_ALTER="user.myinfo.alter";
	public static final String USER_MYINFO_PWD_ALTER="user.myinfo.pwd.alter";
	public static final String USER_CHECK_PWD_BYID="user.check.pwd.byid";
	public static final String USER_QUERY_BYROLE="user.query.byrole";
	public static final String USER_CHECK_UN="user.check.un";
	
	public static final int USER_ROLE_STU=4;
	public static final int USER_ROLE_TEA=3;
	public static final int USER_ROLE_DEPMAN=2;
	public static final int USER_ROLE_MAN=1	;
	
//	public static final String USER_ROLE_AUTHOR_CHECK="user.role.author.check";
	public static final String USER_ROLE_ADD="user.role.add";
	public static final String USER_ROLE_ALTER="user.role.alter";
	public static final String USER_ROLE_DELETE="user.role.delete";
	public static final String USER_ROLE_SET_BYRID="user.role.set.byrid";
	public static final String USER_ROLEFUNC_DELETE_BYRID="user.rolefunc.delete.byrid";
	
	public static final String USER_ROLE_LIST="user.role.list";
	public static final String USER_ROLE_BYID="user.role.byid";
	public static final String USER_FUNC_ADD="user.func.add";
	public static final String USER_FUNC_DELETE_BYID="user.func.delete.byid";
	public static final String USER_ROLEFUNC_DELETE_BYID="user.rolefunc.delete.byid";
	
	public static final String USER_FUNC_ALTER="user.func.alter";
	public static final String USER_FUNC_CHILD="user.func.child";
	public static final String USER_FUNC_BYID="user.func.byid";
	public static final String USER_ROLEFUNC_ADD="user.rolefunc.add";
	public static final String USER_ROLEFUNC_DELETE="user.rolefunc.delete";
	

	public static final String USER_USERFUNC_DELETE="user.userfunc.delete";
	public static final String USER_USERFUNC_ADD="user.userfunc.add";
	
	public static final String USER_ROLEFUNC_LIST_BYRID="user.rolefunc.list.byrid";
	public static final String USER_FUNC_LIST="user.func.list";
	public static final String USER_FUNC_UNCLIST="user.func.unclist";
	public static final String SYSTEM_ALTER="system.alter"; 
	public static final String SYSTEM_BYTYPE="system.bytype"; 
	
	public static final String USER_GROUP_ASSIGN_LIST="user.group.assign.list";
	
	/**
	 * 岗位管理
	 */
	public static final String ST_QUERY_ROOT="st.query.root";
	public static final String ST_ADD="st.add";
	public static final String ST_ALTER="st.alter";
	public static final String ST_QUERY_LRID_BYID="st.query.lrid.byid";
	public static final String ST_QUERY_SUBS="st.query.subs";
}
