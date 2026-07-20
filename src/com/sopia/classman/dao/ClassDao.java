package com.sopia.classman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.classman.entities.ElclassAuditDescribes;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.ClassPara;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;
import com.sopia.statman.entities.MyClass;

public interface ClassDao {
	
	/**
	 * 复制培训班
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public int copyClass(int id) throws ElException;
	
	public int addClass(ElClass elclass) throws ElException;

	public ElClass getClassById(int userid, int id) throws ElException;

	public ElClass getClassById(int id) throws ElException;
	
	public ElClass getClassByName(String className) throws ElException;
	
	public void alterClass(ElClass elClass) throws ElException;

	public List<ElClass> listClasses(int userid, int cltid, String name, int pageNow,
			int pageSize) throws ElException;
	public List<ElClass> listClasses(int userid,int roleid,ElClType cltypeTree, int cltid, String name,
			int pageNow, int pageSize) throws ElException;
	public int listClassesCount(int userid,int roleid,ElClType cltypeTree, int cltid, String name) throws ElException;
	//培训班组合搜索
	public List<ElClass> listcombinationSearchClass(ElClass elClass,ElClType cltypeTree,String sqlw ,int pageNow,
			int pageSize) throws ElException;
	public int listcombinationSearchClassCount(ElClass elClass,ElClType cltypeTree,int pageNow,
			int pageSize) throws ElException;
	/**
	 * 培训班删除待确认列表大小
	 * @param deptid
	 * @param cltid
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public int listDeleteApplyClassSize(int deptid, int cltid, String name) throws ElException;
	
	public int listClassesSize(int userid, int cltid, String name) throws ElException;

	public List<ElClass> listClassFromSuper(int depid, String name,
			int pageNow, int pageSize) throws ElException;

	public List<ElClass> listClassFromThis(int depid, String name, int pageNow,
			int pageSize) throws ElException;

	/**
	 * 培训班课程列表
	 * @param classid 培训班id
	 * @param status  课程类别
	 * @return
	 * @throws ElException
	 */
	public List<Course> listClassCourses(int classid, int status)
			throws ElException;
	
	public List<Course> listStudyCourses(int classid,int userid) throws ElException;

	public List<Course> listAllClassCourse(int classid, int userid)
			throws ElException;

	/**添加班级课程
	 * @param classid
	 * @param courseid
	 * @param status
	 * @throws ElException
	 */
	public void addClassCourse(int classid, int courseid, int status)
			throws ElException;

	/**班级课程删除
	 * @param classid
	 * @param courseid
	 * @throws ElException
	 */
	public void deleteClassCourse(int classid, int courseid) throws ElException;

	/**获取班级课程信息
	 * @param classid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public Course getClassCourse(int classid, int courseid) throws ElException;

	public void alterClassCourseCredit(Course course, int classid)
			throws ElException;

	public void applyClassDelete(int classid, int userid) throws ElException;

	public List<ELUser> listCanAssignUsers(int classid, int depid)
			throws ElException;

	public List<ELUser> listAssignedUsers(int classid, int depid)
			throws ElException;

	public Department listAssignedDep(int depid, int classid)
			throws ElException;

	public void unassignDepsAll(int classid) throws ElException;

	public Department listCanAssignDep(int depid, int classid)
			throws ElException;

	public void assign2userAdd(int userid, int classid) throws ElException;
	
	public void assign2userAdd2(int userid, int classid) throws ElException ;

	public void assign2userDelete(int userid, int classid) throws ElException;

	public List<Department> listCanAssignDeps(int classid, int depid)
			throws ElException;

	public List<Department> listAssignedDeps(int classid, int depid)
			throws ElException;

	public void assign2depAdd(int depid, int classid) throws ElException;

	public void assign2depDelete(int depid, int classid) throws ElException;

	public List<ElClass> listCanApplyClassFromSuper(String name, int userid,
			int pageNow, int pageSize) throws ElException;

	public List<ElClass> listCanApplyClassFromThis(String name, int userid,
			int pageNow, int pageSize) throws ElException;

	public void applyClass(int classid, int userid) throws ElException;

	public List<ElClass> listApplyedClass(int depid, int pageNow, int pageSize)
			throws ElException;

	// public List<ElClass> listMyStudyClass(int userid, int pageNow, int
	// pageSize)
	// throws ElException;

	public ElClass getElClassById(int id) throws ElException;
	public ElClass getElClassByName(String name) throws ElException;
	public ElClass getElClassById_cisco(int id,int userid) throws ElException;

	public void setClassApplyStatus(int classid, int userid, int status)
			throws ElException;

	public void setClassStatus(int classid, int status)
			throws ElException;
	/*public void setClassStatus(int classid,int status)
	throws ElException;*/
	public void deleteClass(int classid) throws ElException;

	public List<ElClass> listDeleteApplyClass(int depid, int typeid, String name, int pageNow,
			int pageSize) throws ElException;

	public List<MyClass> listGraduateClass(int userid, int pageNow, int pageSize)
			throws ElException;

	public int listGraduateClassSize(int userid) throws ElException;

	public void graduateClassApplay(int userid, int classid, int status)
			throws ElException;

	// public List<MyClass> listMyCanCraduateClass(int userid) throws
	// ElException;
	public void autoSetCourse(int classid, int status, int userid)
			throws ElException;

	public ElClass getResentClass() throws ElException;

	public List<Department> listDepPassPer(int classid, int pageNow,
			int pageSize) throws ElException;

	/**
	 * 根据不同状态获取培训班列表
	 * @param sessionIntValue
	 * @param typeid
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
//	public List<ElClass> getClassesList(int dept, int typeid,
//			String name, int status, int pageNow, int pageSize) throws ElException;

	/**
	 * 根据不同状态获取培训班数量
	 * @param sessionIntValue
	 * @param typeid
	 * @return
	 */
//	public int getClassesSize(int sessionIntValue, int typeid, String name, int status) throws ElException;
	
	/**
	 * 根据不同状态获取有权限的培训班列表
	 * @param tree
	 * @param deptId
	 * @param typeid
	 * @param name
	 * @param status
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getClassesList(ElClType tree, int deptId, int typeid,
			ElClass elclass, String status,int role,String sqlw, int pageNow, int pageSize) throws ElException;
	
	public List<ElClass> getClassesList2(ElClType tree, int deptId, int typeid,
			ElClass elclass,String status,int role,String sqlw, int pageNow, int pageSize) throws ElException;

	public List<ElClass> getClassesList3(ElClType tree, int deptId, int typeid,
			ElClass elclass, String status,int role,String sqlw, int pageNow, int pageSize) throws ElException;
	/**
	 * 根据不同状态获取有权限的培训班数量
	 * @param ctypeTree
	 * @param deptId
	 * @param typeid
	 * @param name
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int getClassesSize(ElClType tree, int deptId, int typeid, ElClass elclass, String status,int role) throws ElException;

	public int getClassesSize2(ElClType tree, int deptid, int cltid,ElClass elclass,String status ,int role) throws ElException;
	
	public int getClassesSize3(ElClType tree, int deptid, int cltid,ElClass elclass,String status ,int role) throws ElException;

	public void shClass(int courseid, int status) throws ElException;
	
	public void shUvalid(int courseid, int status) throws ElException;

	public void setaStatus(int courseid, int status) throws ElException;

	public void setisNormal(int classid, int isNormal) throws ElException;

	public List<ElClass> getStatClassesList(int deptid, int typeid, String name,
			int pageNow, int pageSize) throws ElException;

	public int getStatClassesSize(int deptid, int typeid, String name) throws ElException;

	/**
	 * 审批不通过，删除培训班申请记录
	 * @param classid
	 * @param userid
	 * @throws ElException
	 */
	public void setClassApplyStatusNo(int classid, int userid) throws ElException;

	/**
	 * 更新培训班和课程关联表学分
	 * @param map
	 * @throws ElException
	 */
	public void updateCourseRelation(Map map) throws ElException;

	/**
	 * 培训班选择的课程大小
	 * @param classid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int listClassCoursesPageSize(int classid, int status) throws ElException;

	/**
	 * 培训班选择的课程分页
	 * @param classid
	 * @param status
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Course> listClassCoursesPage(int classid, int status,  int pageNow, int pageSize) throws ElException;

	/**
	 * 培训批次选择培训班列表
	 * @param cltypeTree
	 * @param deptId
	 * @param typeid
	 * @param name
	 * @param classStatusOpenYes
	 * @param pageNow
	 * @param pageSize
	 * @param valueOf
	 * @return
	 */
	public List<ElClass> getBatchClassesList(ElClType cltypeTree, int deptId, int typeid, String name,
			int status, int pageNow, int pageSize, int batchId) throws ElException;

	/**
	 * 培训批次选择培训班列表大小
	 * @param tree
	 * @param deptid
	 * @param cltid
	 * @param name
	 * @param status
	 * @param batchId
	 * @return
	 * @throws ElException
	 */
	public int getBatchClassesSize(ElClType tree, int deptid, int cltid, String name, int status, int batchId) throws ElException;
	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int depid,
			int classid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser,int sub_department,Department depTree,Station staTree) throws ElException ;
	public int listAssignedUserSize(int depid,
			int classid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser,int sub_department,Department depTree,Station staTree) throws ElException;
	
	public List<ELUser> listAssignedUserIsAssign(int pageNow, int pageSize, int depid,
			int classid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser) throws ElException ;
	public int listAssignedUserIsAssignSize(int depid,
			int classid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser) throws ElException;
	
	/**
	 * 培训班学分排行榜(左树右表查询有问题)
	 * @param pageNow
	 * @param pageSize
	 * @param depid
	 * @param classid
	 * @param state
	 * @param userid
	 * @param starttime
	 * @param endtime
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
//	public List<ELUser> classStudent(int pageNow, int pageSize, int depid,
//			int classid, int state, List<Integer> userid, String starttime,
//			String endtime, ELUser elUser) throws ElException ;
	
//	public List<ELUser> classStudent(int depid,	int classid, int state, List<Integer> userid, String starttime,
//			String endtime, ELUser elUser) throws ElException;
	/**
	 * 培训班学分排行榜大小
	 * @param depid
	 * @param classid
	 * @param state
	 * @param userid
	 * @param starttime
	 * @param endtime
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int classStudentSize(int depid,
			int classid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser) throws ElException;
	/**
	 * 添加培训班(增加了开始和结束时间)
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int addClass2(ElClass elclass) throws ElException;
	/**
	 * 添加培训班申请记录
	 * @param elRegistration
	 * @return
	 * @throws ElException
	 */
	public void addClassRegistration(ELClassRegistration elRegistration) throws ElException;
	public void alterClassRegistration(ELClassRegistration elRegistration) throws ElException;
	public ELClassRegistration getClassRegistration(int classid) throws ElException;
	/**
	 * 添加培训班课程（增加开始、结束时间）
	 * @param classid
	 * @param courseid
	 * @param status
	 * @throws ElException
	 */
	
	public void addClassCourse2(int classid, int courseid, int status,Timestamp starttime,Timestamp finishtime) throws ElException;
	
	public void setClassAudit(ElclassAuditDescribes classAudit) throws ElException;
	
	public void UClassAuditContents(ElclassAuditDescribes classAudit) throws ElException;
	
	public ElclassAuditDescribes getClassAudit(int classid) throws ElException ;
	
	/**
	 * 分配培训班
	 * @param userid
	 * @param classid
	 * @throws ElException
	 */
	public void assign2userAdd3(int userid, int classid,int joinway) throws ElException;
	/**
	 * 删除培训班课程(假删除)
	 * @param classid
	 * @param courseid
	 * @throws ElException
	 */
	public void deleteClassCourse2(int classid, int courseid) throws ElException;
	/**
	 * 培训班课程列表(不显示已删除的)
	 * @param classid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Course> listClassCourses2(int classid, int status) throws ElException;
	/**
	 * 恢复培训班课程
	 * @param classid
	 * @param courseid
	 * @throws ElException
	 */
	public void restorationClassCourse(int classid, int courseid) throws ElException;
	public boolean checkElclassUsers(String type, int userid, int depid)throws ElException;
	public boolean checkElclassRegistration(int classid)throws ElException;
	public void addElclassusers(String type, int userid, int depid)throws ElException;
	public List<ELUser> getElclassUsers(String type, int classid)throws ElException;
	/**
	 * 检查培训班复核人员
	 * @param type
	 * @param userid
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public boolean checkElclassUvalid(int classid)throws ElException;
	/**
	 * 获取下级部门
	 * @param tree
	 * @param cltid
	 * @return
	 */
	public String getXJBM(Department tree, int cltid);
	/**
	 * 获取培训班人员id
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getelClassUser(int classid)throws ElException;
	/**
	 * 获取参加了培训班的人数
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getJoinNumber(int classid)throws ElException;
	/**
	 * 根据培训班条件搜索学员 
	 * @param dep
	 * @param table(study_room    study_class)
	 * @param tid
	 * @param classid
	 * @param elUser
	 * @param starttime
	 * @param endtime
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> gettoClassInfoselectUser(Department depTree,Department dep,String table ,int tid, int classid,ELUser elUser, String starttime,String endtime,int pageNow, int pageSize) throws ElException ;
	public int gettoClassInfoselectUserSize(Department depTree,Department dep,String table ,int tid, int classid,ELUser elUser, String starttime,String endtime) throws ElException;
	
	/**
	 * 根据考场条件搜索学员
	 * @param dep
	 * @param cid
	 * @param eroomid
	 * @param elUser
	 * @param starttime
	 * @param endtime
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> gettoEroomInfoselectUser(Department depTree,Department dep,String table ,int tid, int eroomid, ELUser elUser, String starttime,String endtime,int pageNow, int pageSize) throws ElException ;
	public int gettoEroomInfoselectUserSize(Department depTree,Department dep,String table,int id, int eroomid, ELUser elUser, String starttime,String endtime) throws ElException;

	/**
	 * 验证该培训班是否已存在userid用户
	 * @param classid
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public boolean checkElclassIsUsers(int userid, int classid)throws ElException;
	/**
	 * 获取与当前培训班时间重叠的培训班
	 * @param elclass
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getClassTimeoverList(ElClass elclass, int pageNow, int pageSize) throws ElException;
	/**
	 * 获取与当前培训班时间重叠的培训班数量
	 * @param elclass
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getClassTimeoverListCount(ElClass elclass) throws ElException ;
	/**
	 * 获取培训班信息以及创建者信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public ElClass getClassById2(int id) throws ElException;
	/**
	 * 更改培训班的状态
	 * @param classid
	 * @param status
	 * @param isApplication
	 * @throws ElException
	 */
	public void shClass(int classid, int status,int isApplication) throws ElException;
	/**
	 * 获取培训班list
	 * @param tree
	 * @param elclass
	 * @param sublibs
	 * @param status
	 * @param sqlw
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getClassList(ElNode tree,ElClass elclass,int sublibs, String status,String sqlw, int pageNow, int pageSize)
			throws ElException;
	/**
	 * 获取培训班list数量
	 * @param tree
	 * @param elclass
	 * @param sublibs
	 * @param status
	 * @param sqlw
	 * @return
	 * @throws ElException
	 */
	public int getClassListSize(ElNode tree,ElClass elclass,int sublibs, String status)
			throws ElException;
	/**
	 * 培训班统计查询（学分排序）
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> classStudent(int classid,ElNode tree,ELUser elUser,int pageNow, int pageSize) throws ElException;
	/**
	 * 培训班统计查询学员数量
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int classStudentSize(int classid,ElNode tree,ELUser elUser) throws ElException;
	/**
	 * 培训班统计查询学员（学分排序）（不分页、用于导出）
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> classStudent(int classid,ElNode tree,ELUser elUser) throws ElException;
	/**
	 * 获取可申请且人员要审核的培训班里面的需要审核的人员
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<MyClass> getClassNoAuditUser(int classid,int pageNow, int pageSize,ELUser eu,Department dep ,int status)throws ElException;
	/**
	 * 获取可申请且人员要审核的培训班里面的需要审核的人员数量
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getClassNoAuditUserSize(int classid,ELUser eu,Department dep ,int status)throws ElException;
	/**
	 * 获取已报培训班的人数
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getClassApplyNumber(int classid)throws ElException;
	/**
	 * 获取培训班计划招收人数
	 * @param elcid 
	 * @return
	 * @throws ElException
	 */
	public int getElclassPlanNumber(int elcid) throws ElException;
	/**
	 * 获取培训班人数
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public int getClassUserSize(int classid) throws ElException;
	/**
	 * 删除学员培训班报名记录
	 * @param roomid
	 * @param userid
	 * @throws ElException
	 */
	public void deleteStudyClassApply(int classid, int userid) throws ElException;
	/**
	 * 根据学员培训班相关信息搜索学员(分页)
	 * @param oldClassParas
	 * @param roomid
	 * @param epid
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnClassSeach(List<ClassPara> oldClassParas, ELUser elUser ) throws ElException;
	/**
	 * 根据学员培训班相关信息搜索学员(分页)
	 * @param oldClassParas
	 * @param roomid
	 * @param epid
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnClassSeach(List<ClassPara> oldClassParas,
			int classid,ELUser elUser, int pageNow,int pageSize) throws ElException;
	/**
	 * 根据学员培训班相关信息搜索学员数量
	 * @param oldErParas
	 * @param queryManner
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int listUserOnClassSeachSize(List<ClassPara> oldClassParas,
			ELUser elUser) throws ElException;
	/**
	 * 根据用户id  获取用已经参加的培训班
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getClassByUserid(int userid,int courseid, int pageNow, int pageSize) throws ElException ;
	/**
	 * 验证该培训是否的某课程是否考过（是否拿到学分）
	 * @param courseid
	 * @param elclassid
	 * @return
	 * @throws ElException
	 */	
	public List<ElClass> CheckClassIsKs_passCourse(int courseid,int userid) throws ElException ;
	/**
	 * 根据用户id  获取用已经参加的培训班
	 * @param userid
	 * @param elclassid
	 * @return
	 * @throws ElException
	 */
	public int getClassByUseridSize(int userid,int courseid) throws ElException ;
	/**
	 * 自主培训班添加课程
	 * @param classid
	 * @param courseid
	 * @param userid
	 * @param status
	 * @param starttime
	 * @param finishtime
	 * @throws ElException
	 */
	public void addClassCourse_AT(int classid, int courseid,int userid, int status,Timestamp starttime,Timestamp finishtime,int setcredit )
	throws ElException;
	/**
	 * 获取某部门的某个培训班的通过率
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public double getElclassDepPassing(Department department,ElClass elclass) throws ElException;	
	
	public List<ELUser> getClassUser(int classid) throws ElException ;
	public List<ELUser> getPoints_RecordUsers(int classid) throws ElException;
	
	/**
	 * 积分排名
	 * @param depid
	 * @param classid
	 * @param starttime
	 * @param endtime
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getElclassRecordRankinglist( int depid,int classid, String starttime,
			String endtime, ELUser elUser ,int pageNow, int pageSize) throws ElException ;
	public int getElclassRecordRankingSize( int depid,int classid, String starttime,
			String endtime, ELUser elUser) throws ElException;
	
	/**
	 * （培训班概况比较用到）
	 * 获取某部门下某培训班总人数 
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int getClassEval_CountNumberOfPeople(Department department,ElClass elclass) throws ElException;
	
	/**
	 * （培训班概况比较用到）
	 * 获取某部门下某培训班通过人数
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int getClassEval_Pass_CountNumberOfPeople(Department department,ElClass elclass) throws ElException;
	
	/**
	 * （培训班概况比较用到）
	 * 获取某部门下某培训班总高级人数 
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int getClassEval_CountNumberOfGaojiPeople(Department department,ElClass elclass) throws ElException ;
	
	/**
	 * （培训班概况比较用到）
	 * 获取某部门下某培训班通过的高级职称人数
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int getClassEval_Pass_CountNumberOfGaojiPeople(Department department,ElClass elclass) throws ElException;
	/**
	 * 验证某部门的某个培训班的通过率是否存在
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public boolean CheckElclassDepPassing(Department department,ElClass elclass) throws ElException ;
	/**
	 * 增加某部门的某个培训班的通过率
	 * @param depid
	 * @param elclassid
	 * @param passing
	 * @throws ElException
	 */
	public void addElclassDepPassing(int depid,int elclassid, double passing) throws ElException;
	/**
	 * 修改某部门的某个培训班的通过率
	 * @param depid
	 * @param elclassid
	 * @param passing
	 * @throws ElException
	 */
	public void alterElclassDepPassing(int depid,int elclassid, double passing) throws ElException;
	/*
	 * 培训班概况用设置通过率进行排序
	 */
	public List<Department> getDepSortByRatioPassing_(List<Department> deps);
	
	/**
	 * 根据no、classid获取userid
	 * @param no
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getUseridByCertificateNo(int no,int classid) throws ElException;
	
	/**
	 * 判断课程学习序号是否已经存在
	 * @param course
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public boolean checkOrderidIsExist(Course course,ElClass elclass) throws ElException;
	
	/**
	 * 判断当前课程是否可以学习
	 * @param courseid
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkcoursecanlearn(int courseid,int classid,int userid) throws ElException;
	
	/**
	 * 判断课程是否学完
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudyCourseIsPassed(int userid,int courseid) throws ElException;
	
	/**
	 * 取消培训班绑定考场
	 * @param classid
	 * @param roomid
	 * @throws ElException
	 */
	public void quitAssignRoom(int classid,int roomid) throws ElException;
	
	/**
	 * 修改培训班进度
	 * @param classid
	 * @param process
	 * @param userid
	 * @throws ElException
	 */
	public void updateClassProcessByClassid(int classid,float process,int userid) throws ElException;
	/**
	 * 判断培训班是否可学
	 * @param sortid
	 * @param userid
	 * @param batchid
	 * @return
	 * @throws ElException
	 */
	public boolean checkClassCanLearn(int classid,int sortid,int userid,int batchid) throws ElException;
	/**
	 * 判断培训班结业考场是否通过
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkClassExamIsPass(int classid, int userid)
	throws ElException;
	
	/**
	 * 更新培训班进度
	 * @param batchid
	 * @throws ElException
	 */
	public void updateClassProcess(int batchid,int userid) throws ElException;
	
	/**
	 * 获取等级考试用户
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public String getUsersByUserids(int classid) throws ElException;
	
	/**
	 * 判断培训班是否可以考试
	 * @param userid
	 * @param classid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int checkClassCanExam(int userid,int classid,int roomid) throws ElException;
	/**
	 * 培训班对应的考场roomid
	 */
	public ExamRoom elclassRoom(int classid)
	throws ElException;
	
	/**
	 * 等级完成情况统计分析
	 * @param classid
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public int getFinishCountInformation(int classid,int type) throws ElException;
	
	/**
	 * 根据classid查询用户完成情况
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getStudentInfoByClassid(int classid,int pageNow,int pageSize) throws ElException;
	public int getStudentInfoSizeByClassid(int classid) throws ElException;
	
//---------------sd1230-------------------------------xiugai	
	/**
	 * 培训班统计查询（学分排序）
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> classStudent_sd(int classid,ElNode tree,ELUser elUser,int pageNow, int pageSize) throws ElException;
	/**
	 * 培训班统计查询学员数量
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int classStudentSize_sd(int classid,ElNode tree,ELUser elUser) throws ElException;
	/**
	 * 培训班统计查询学员（学分排序）（不分页、用于导出）
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> classStudent_sd(int classid,ElNode tree,ELUser elUser) throws ElException;
	
	public void elclassHotSet(int classid,int hot)throws ElException;
	
	/**
	 * 判断培训班是否可以考试20140703
	 * @param userid
	 * @param classid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int checkClassCanExam_new(int userid,int classid,int classid2,int roomid) throws ElException;
	
}
