package com.sopia.courseman.dao;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseAuditDescribes;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.CourseRegistration;
import com.sopia.courseman.entities.CourseServer;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomRegistration;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.PracticePaper;
import com.sopia.courseman.entities.QuizPaper;
import com.sopia.courseman.entities.SimexamPaper;
import com.sopia.duman.entities.BaseDataTypeCourse;
import com.sopia.duman.entities.BaseDatatCourse;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyExamPaper;

public interface CourseDao {
	
	/**
	 * 复制课程
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public int courseCopy(int id) throws ElException;
	/**
	 * 创建新的课程 返回该Course(包括Id)
	 * @param course
	 * @return
	 * @throws ElException
	 */
	public void addCourse(Course course) throws ElException;

	public List<Course> listMyCourse(int create, int ctid, String name,
			int pageNow, int pageSize) throws ElException;
	  
	public int listMyCourseCount(int create, int ctid, String name)
			throws ElException;

	public List<Course> listAllCourseFromThis(int depid, String name, int ctid,
			int pageNow, int pageSize,int status) throws ElException;

	public List<Course> listAllCourseFromSuper(int depid, String name,
			int pageNow, int pageSize) throws ElException;

	public int listAllCourseSizeFromThis(int depid, String name, int ctid,int status)
			throws ElException;

	public int listAllCourseSizeFromSuper(int depid, String name)
			throws ElException;

	// public int listAllSize() throws ElException;
	public List<ELUser> listCanAssignUser(int cid, int depid)
			throws ElException;

	/**
	 * 已分配用户列表
	 * 
	 * @param cid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listAssignedUser(int depid, int cid, int state)
			throws ElException;
	
	public List<ELUser> listAssignedUser(int pageNow, int pageSize,int depid, int courseid, int state,
			List<Integer> userid, String starttime, String endtime,ELUser elUser,Department depTree,Station stTree,int role)
	throws ElException;
	public int listAssignedUserSize(int depid,
			int courseid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser,Department depTree,Station stTree,int role) throws ElException ;

	public Department listAssignedDep(int depid, int courseid, int state)
			throws ElException;

	public Department listCanAssignDep(int depid, int courseid)
			throws ElException;

	public void assignedUser(int cid, int userid, int status)
			throws ElException;
	
	public void assignedUser3(int cid,int userid,int cepingid) throws ElException;
	
	public void alterassignedUser(int cid, int userid, int status,boolean is)
	throws ElException;

	public void unassignedUser(int cid, int userid) throws ElException;

	public Course getCourseById(int id) throws ElException;
	public Course getCourseByName(String name) throws ElException;
	
	/**
	 * @author jiahaijiang
	 * 根据课程ID批量找出需要删除的课程
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public List<Course> getCourseById(String id) throws ElException;

	public void openCourse(int id) throws ElException;
	
	public void openCourse(int id , int status) throws ElException;
	
	public void openCourseAudit(CourseAuditDescribes courseAudit) throws ElException;

	public CourseAuditDescribes getCourseAudit(int courseid ) throws ElException;
	
	public void UCourseAuditContents(CourseAuditDescribes courseAudit ) throws ElException;

	public void courseDelete(int id, int deleter) throws ElException;

	/**
	 * @author jiahaijiang
	 * 批量删除
	 * @param ids
	 * @param deleter
	 * @throws ElException
	 */
	public void courseDelete(String ids, int deleter) throws ElException;
	
	public void courseDeleteOp(int id) throws ElException;

	public List<Course> listDeleteCourse(int pageNow, int pageSize)
			throws ElException;

	// public int listDeleteCourseSize() throws ElException;

	/**
	 * Description: 修改课程 
	*  @Version1.0 2012-7-24 上午09:44:27 by 闻益舜（wenyishun110@163.com）创建
	 * @param course
	 * @throws ElException
	 */
	public void alterCourse(Course course) throws ElException;
	
	/**
	 * Description: scorm课程的一些修噶
	* @Version1.0 2012-7-24 上午09:44:53 by 闻益舜（wenyishun110@163.com）创建
	 * @param course
	 * @throws ElException
	 */
	public void alterCourse_S(Course course) throws ElException;
	
	public void alterCourseStatus(int id,int status) throws ElException;

	// ========================试卷练习管理-==

	public boolean checkDep2course(int depid, int course) throws ElException;

	public List<Department> listCanAssignDeps(int depid, int courseid)
			throws ElException;

	public List<Department> listAssignedDeps(int depid, int courseid)
			throws ElException;

	public void assignDeps(int courseid, int depid) throws ElException;

	public void unassignDeps(int courseid, int depid) throws ElException;

	public void unassignDepsAll(int courseid) throws ElException;

//	public void addExamRoom(ExamRoom examRoom) throws ElException;

	
//	public List<ExamRoom> listExamRoom(int courseid) throws ElException;
//
//	public List<ExamRoom> listMyExamRoom(int userid, String title, int pageNow,
//			int pageSize) throws ElException;
//
//	public int listMyExamRoomSize(int userid, String title) throws ElException;
//
//	public List<ExamRoom> listMyDepExamRoom(int depid, String title,
//			int pageNow, int pageSize) throws ElException;
//
//	public int listMyDepExamRoomSize(int depid, String title)
//			throws ElException;

//	public ExamRoom getExamRoomByid(int id) throws ElException;
//
//	public List<ELUser> listCanAssignToRoomUsers(int roomid) throws ElException;
//	public List<ELUser> listCanAssignToRoomUsers_bk(int roomid,int bkroomid) throws ElException;
//
//	public List<ELUser> listAssignToRoomUsers(int roomid) throws ElException;

	// public void assignToRoom(int userid, int roomid)
	// throws ElException;

	// public void unassignToRoom(int userid, int courseid) throws ElException;

	// public boolean checkUinR(int userid, int roomid) throws ElException;

	public List<ExamRoom> listERbyCidandTitle(int cid, String title)
			throws ElException;

	public List<MyExamPaper> listReadSimPapers(int courseid, int pageNow,
			int pageSize) throws ElException;

	public int listReadSimPapersSize(int courseid) throws ElException;

	public void reSimquiz(int id) throws ElException;

	public List<ExamRoom> listMyExamroom(int userid) throws ElException;
	
	public List<ExamRoom> listMyExamroomPages(int userid ,int role , int pageNow, int pageSize) throws ElException;
	
	public int listMyExamroomPage(int userid,int role) throws ElException;

	// public List<MyExamPaper> listMepByRid(int roomid)throws ElException;
	public List<MyExamPaper> listMyEpsByRid(int roomid,int userid) throws ElException;
//	public int listMyEpsByRidSize(int roomid) throws ElException;

	public void setTesterStatus(int status, int roomid, int userid)
			throws ElException;

	public List<MyCourse> listselectedCourse(int status, int pageNow,
			int pageSize) throws ElException;

	public int listselectedCourseSize(int status) throws ElException;
	
	public List<MyCourse> listselectedCourse(CourseType ctypeTree,int ctid,int status, int pageNow,
			int pageSize) throws ElException;
	
	public int listselectedCourseSize(CourseType ctypeTree,int ctid,int status) throws ElException;

	public void setSelectedCoruse(int status, MyCourse myCourse)
			throws ElException;

	public void courseHotSet(int id, int hot) throws ElException;

	public List<MyCourse> listStudycoursedelete(int pageNow, int pageSize)
			throws ElException;

	public int listStudycoursedeleteSize() throws ElException;

	public void Studycoursedelete_Op(MyCourse mc) throws ElException;

	public void Studycoursedelete_Unop(MyCourse mc) throws ElException;

	public List<Course> listShCourse(int depid,int pageNow, int pageSize) throws ElException;
	
	public int listShCourseSize(int depid,int pageNow, int pageSize) throws ElException;

	public void shCourse(int courseid, int status) throws ElException;

	public void setisNormal(int courseid, int isNormal) throws ElException;
	
	public void setaStatus(int courseid, int isNormal) throws ElException;

	public List<CourseServer> listCourseServer() throws ElException;

	public void addCourseServer(CourseServer courseServer) throws ElException;

	public void alterCourseServer(CourseServer courseServer) throws ElException;

	public void deleteCourseServer(int id) throws ElException;

	public CourseServer getCourseServer(int id) throws ElException;

	/**
	 * 查询练习列表
	 * @param course 课程id
	 * @param cpid   章节id
	 * @return
	 * @throws ElException
	 */
	public List<PracticePaper> getPracticePaperByCid(int course, int cpid)
			throws ElException;

	public void addPracticePaper(PracticePaper pracPaper) throws ElException;

	public void deletePracticePaper(int id) throws ElException;

	public boolean checkPpInCourse(PracticePaper practicePaper)
			throws ElException;

	public void practicepaper_sort(PracticePaper pp, int upordown)
			throws ElException;

	public PracticePaper getPracticePaperById(int id) throws ElException;

	public void addSimexampaper(SimexamPaper simexamPaper) throws ElException;

	public void deleteSimexampaper(int id) throws ElException;

	public List<SimexamPaper> getSimexampaperByCid(int course)
			throws ElException;
	public SimexamPaper getSimexamPaperById(int id) throws ElException;

	public boolean checkSpInCourse(SimexamPaper simexamPaper)
			throws ElException;

	public void addQuizpaper(QuizPaper quizPaper)
			throws ElException;

	public void deleteQuizpaper(int id)
			throws ElException;

	public List<QuizPaper> getQuizpaperByCid(int course) throws ElException;

	public boolean checkQpInCourse(int exampaper, int courseid)
			throws ElException;
	/**
	 * 我创建的课程 根据有权限的课程类型树查找出课程
	 * @author jiahaijiang
	 * @param ctypeTree 有权限的课程树
	 * @param creater
	 * @param ctid
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Course> listMyCourse(CourseType ctypeTree,int creater, int ctid, String name,int role,
			int pageNow, int pageSize) throws ElException ;
	
	public List<Course> listMyCourse(CourseType ctypeTree, int ctid, String name,int role ,
			int pageNow, int pageSize) throws ElException ;

	 /**
	 * 我创建的课程 根据有权限的课程类型树查找出课程合计
	 * @author jiahaijiang
	 * @param ctypeTree 有权限的课程树
	 * @param creater
	 * @param ctid
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listMyCourseCount(CourseType ctypeTree,int creater, int ctid, String name,int role) throws ElException; 
	
	public int listMyCourseCount(CourseType ctypeTree,int ctid, String name) throws ElException; 
	
	/**
	 * 查询有课程类型权限的课程
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param depid
	 * @param name
	 * @param ctid
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Course> listAllCourseFromThis(CourseType ctypeTree,int depid,int role, Course course, int ctid,
			int pageNow, int pageSize,String status,String sqlw) throws ElException;	
	public List<Course> listAllCourseFromThisStatus(CourseType ctypeTree,int depid,int role, String name, int ctid,
					int pageNow, int pageSize,String status) throws ElException;
	
	
	public List<Course> listAllCourseFromThis(CourseType ctypeTree,int depid, String name, int ctid,
			int pageNow, int pageSize,int status) throws ElException;
	/**
	 * 查询有课程类型权限的课程合计
	 * @author jiahaijiang 
	 * @param ctypeTree
	 * @param depid
	 * @param name
	 * @param ctid
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */

	/**
	 * 查询有课程类型权限的课程合计
	 * @author jiahaijiang 
	 * @param ctypeTree
	 * @param depid
	 * @param name
	 * @param ctid
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int listAllCourseSizeFromThis(CourseType ctypeTree,int depid, String name, int ctid,int status) throws ElException ;
	public int listAllCourseSizeFromThis(CourseType ctypeTree,int depid , int role, Course course, int ctid,String status,String sqlw) throws ElException ;
	public int listAllCourseSizeFromThisStatus(CourseType ctypeTree,int depid , int role, String name, int ctid,String status) throws ElException ;
	/**
	 * 查询有权限的培训班关联相关课程类型
	 * 培训班选择课程用
	 * @author luocw
	 */
	public List<Course> listAllSelectCourse(CourseType ctypeTree,int depid, String name, int ctid,
			int pageNow, int pageSize,int status,int classId ,int role) throws ElException;
	/**
	 * 查询有权限的培训班关联相关课程类型合计
	 * 培训班选择课程用
	 * @author luocw
	 */
	public int listAllSelectCourseSize(CourseType ctypeTree,int depid, String name, int ctid,int status,int classId,int role) throws ElException ;
	
	//我创建的多媒体教室
	public List<Course> MyMultis(CourseType ctypeTree,int creater, int ctid, String name,
			int pageNow, int pageSize) throws ElException ;
	//我创建的多媒体教室数量
	public int listMyMultisCount(CourseType ctypeTree,int creater, int ctid, String name) throws ElException;
	//组合搜索课程
	public List<Course> listCombinationCourse(CourseType ctypeTree,Course course,int role,int pageNow,int pageSize)throws ElException;
	public int listCombinationCourseCount(CourseType ctypeTree,Course course,int role,int pageNow,int pageSize)throws ElException;
	public List<CourseType> getCourseType()throws ElException;
	
	public List<Course> readlistInitlistMyCourse(CourseType ctypeTree,int ctid,int role , String name,
			int pageNow, int pageSize) throws ElException;

	public int readlistInitlistMyCourseCount(CourseType ctypeTree, int ctid,int role , String name)
			throws ElException;
	
	public List<Course> examroom_listAllCourseFromThis(CourseType ctypeTree,int depid,int role, String name, int ctid,
			int pageNow, int pageSize,int status) throws ElException;
	public int examroom_listAllCourseSizeFromThis(CourseType ctypeTree,int depid,int role , String name, int ctid,int status) throws ElException;
	/**
	 * 分配课程
	 * @param cid	课程id
	 * @param userid	用户id
	 * @param status	0：必修 1：选修
	 * @param startTime	分配课程后 课程开始时间
	 * @param finishTime 分配课程后 课程结束时间
	 * @throws ElException
	 */
	public void assignedUser(int cid, int userid, int status,Timestamp startTime,Timestamp finishTime,int roomid) throws ElException;
	
	public void assignedUser2(int cid, int userid, int status,Timestamp startTime,Timestamp finishTime,int roomid,int classid,int jieyeid) throws ElException;
	
	/**
	 * 根据课程id获取所对应的所有场次
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getRoomsByCourseid(int courseid) throws ElException;
	/**
	 * 分配课程
	 * @param cid	课程id
	 * @param userid	用户id
	 * @param status	0：必修 1：选修
	 * @param startTime	分配课程后 课程开始时间
	 * @param finishTime 分配课程后 课程结束时间
	 * @throws ElException
	 */
	public void assignedUser(int cid, int userid, int status,Timestamp startTime,Timestamp finishTime)
	throws ElException;
	
	public List<Course> getClassByCourseid(int classid) throws ElException;
	/**
	 * 监考大厅list
	 */
	public List<ExamRoom> listMyExamroomPages(int userid ,int role ,ExamRoom examRoom,int pageNow, int pageSize) throws ElException;
	/**
	 * 监考大厅list数量
	 * @param userid
	 * @param role
	 * @param examRoom
	 * @return
	 * @throws ElException
	 */
	public int listMyExamroomPage(int userid ,int role ,ExamRoom examRoom) throws ElException;
	/**
	 * 添加章节的练习
	 * @param pracPaper
	 * @throws ElException
	 */
	public void addPracticePaper2(PracticePaper pracPaper) throws ElException;
	/**
	 * 获取课程的所有章节
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<CoursePage> getCourseAllCpage(int courseid) throws ElException;
	/**
	 * 清除用户答卷
	 * @param sqiId
	 * @throws ElException
	 */
	public void rsetStudyExamPaper( int sqiId) throws ElException;
	/**
	 * 根据课程类别删除课程
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteCourseByTypeid(Connection ct,int typeid) throws ElException;
	/**
	 * 根据课程类别更新课程状态
	 * 
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteCourseByTypeidNot(int typeid) throws ElException;
	/**
	 * 课程申请条件验证
	 * @param eroomid
	 * @return
	 * @throws ElException
	 */
	public boolean checkCourseRegistration(int eroomid)throws ElException;
	/**
	 * 增加课程申请条件
	 * @param coRegistration
	 * @throws ElException
	 */
	public void addCourseRegistration(CourseRegistration coRegistration) throws ElException; 
	/**
	 * 更新课程申请条件
	 * @param erRegistration
	 * @throws ElException
	 */
	public void alterCourseRegistration(CourseRegistration eoRegistration) throws ElException;
	/**
	 * 查询课程申请条件
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public CourseRegistration getCourseRegistration(int courseid) throws ElException; 
	/**
	 * 校验该课程内是否有userid学员
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkCourseIsUser(int classid,int userid)throws ElException;	
	/**
	 * 参加课程的人数
	 * @param eroomid
	 * @return
	 * @throws ElException
	 */
	public int getJoinNumber(int eroomid)throws ElException;
	
	/**
	 * 获取已申请的课程
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Course> registeredCourse(int userid, int pageNow,int pageSize)throws ElException;
	public int registeredCourseSize(int userid)throws ElException;
	
	/**
	 * 查看我的课列表
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<Course> mytbcourses(int userid)throws ElException;
	/**
	 * 获取课程列表
	 * @param ctypeTree
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseFromThisStatus(CourseType ctypeTree, String name,int pageNow,
			int pageSize, String status) throws ElException;
	
	/**
	 * 获取课程列表
	 * @param ctypeTree
	 * @param course
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseFromThisStatus1(CourseType ctypeTree, Course course,int pageNow,
			int pageSize, String status) throws ElException;
	/**
	 * 获取课程列表数量
	 * @param ctypeTree
	 * @param name
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int listCourseSizeFromThisStatus(CourseType ctypeTree, String name, String status) throws ElException;
	/**
	 * 获取课程列表数量
	 * @param ctypeTree
	 * @param course
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int listCourseSizeFromThisStatus1(CourseType ctypeTree, Course course, String status) throws ElException;
	/**
	 * 根据id删除课程信息
	 * @param id
	 * @throws ElException
	 */
	public void deleteCourseByid(int id) throws ElException;
	/**
	 * 检测课程是否被用过
	 * @param courseid
	 * @throws ElException
	 */
	public boolean checkCourseIsUse(int courseid) throws ElException;
	/**
	 * 根据课程和章节id检测该章节是否有练习
	 * @param courseid
	 * @param cpageid
	 * @throws ElException
	 */
	public boolean checkCpageIsPrac(int courseid,int cpageid) throws ElException;
	/**
	 * 检测课程章节的完整性
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public String checkCoursePage(int courseid) throws ElException;
	/**
	 * 获取考场学员数
	 * @param roomid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int getExamStudy(int roomid,int status) throws ElException;
	
	//课程基础数据库 
	/**
	 * 根据类别查询数据(分页)
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatatCourse> getBaseCourseByTypeid(int typeid,int pageNow,int pageSize) throws ElException;
	/**
	 * 根据类别查询数据数量
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public int getBaseCourseByTypeidCount(int typeid) throws ElException;
	
	/**
	 * 获取所有基础数据类别
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<BaseDataTypeCourse> getAllBaseDataTypeCourse() throws ElException ;
	
	/**
	 * 获取所有基础数据类别
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<BaseDataTypeCourse> getAllBaseDataTypeCourse(int pageNow,int pageSize) throws ElException ;
	
	/**
	 * 添加基础数据
	 * @param bd
	 * @throws ElException
	 */
	public void addBaseCourseDb(BaseDatatCourse bd) throws ElException;
	/** 
	 * 根据id查询数据
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public BaseDatatCourse getBaseDatatCourseById(int id) throws ElException ;	
	/**
	 * 编辑基础数据
	 * @param bd
	 * @throws ElException
	 */
	public void updateBaseDbCourse(BaseDatatCourse bd) throws ElException;
	/**
	 * 删除基础数据
	 * @param id
	 * @throws ElException
	 */
	public void delBaseDbCourse(int id) throws ElException; 
	
	/**
	 * 根据类别查询数据
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatatCourse> getBaseDatatCourseByTypeid(int typeid) throws ElException;
	/**
	 * 课程库
	 * @param sqlWhere (可添加and条件 具体条件查看sql语句)
	 * @throws ElException
	 */
	public List<Course> getCourseAll (CourseType ctypeTree,int ctid,Course course,String sqlWhere,int pageNow, int pageSize) throws ElException;
	public int getCourseAllSize (CourseType ctypeTree,int ctid,Course course,String sqlWhere) throws ElException;

	/**
	 * 根据courseid得到roomid（分配学员时使用）
	 */
	
	public List<ExamRoom> getRoom(int courseid) throws ElException;
	/**
	 * 基础数据排序
	 * @param typeid
	 * @param sortid
	 * @param upordown
	 * @throws ElException
	 */
	public void sortBaseDbsCourse(int typeid, int sortid, int upordown) throws ElException;
	
	
	/**
	 * 更新课程维度信息
	 * @param course
	 * @throws ElException
	 */
	public void updateCourseWeiduById(Course course) throws ElException;
	
	public int getUserSCInfo(String userid, String courseid, String status) throws ElException;
	public int getSCItemInfo(String courseid)throws ElException;
	
	
	public int getSCPasstime(int courseid,int classid) throws ElException;
	
	/**
	 * 判断考场对应的培训班中的课程用户是否都已经学完
	 * @param roomid
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkCoursesIsAllPass(int roomid,int classid,int userid) throws ElException;
	
	/**
	 * 获取章节考场
	 * @param courseid
	 * @param cpid
	 * @return
	 * @throws ElException
	 */
	public ExamRoom getEroomByCP(int courseid,int cpid) throws ElException;
	
	/**
	 * 获取章节考场（多个考场）
	 * @param courseid
	 * @param cpid
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getEroomListByCP(int courseid,int cpid) throws ElException;
	
	/**
	 * 设置绑定
	 * @param roomid
	 * @param cpid
	 * @throws ElException
	 */
	public void setBand(int roomid,int courseid,int cpid) throws ElException;
	
	
	/**
	 * 对应培训班下用户的课程
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> listMyCoursees_wjm(int classid,int userid) throws ElException;
	/**
	 * 判断该课程对应的章节用户是否都通过了
	 * @param classid
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public boolean checkCpagesIsAllPass(int classid,int userid,int courseid) throws ElException;
	
	/**
	 * 判断课程是否通过（学完）
	 * @param classid
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public boolean checkCourseIsPass(int classid,int userid,int courseid) throws ElException;
	
	/**
	 * 获取正在学习的课程id
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getNowCourseid(int classid,int userid) throws ElException;
	
	/**
	 * 获取上一门课程id
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getPrecCourseid(int classid,int userid,int nowCourseid) throws ElException;
	
	/**
	 * 查询培训班课程列表
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<Course> getCoursesByClassid(int classid) throws ElException;
	/**
	 * 查询课程章节列表
	 * @param course
	 * @return
	 * @throws ElException
	 */
	public List<CoursePage> getPagesByCourseid(int course) throws ElException ;
	/**
	 * 根据课程id获得该课程对应所有章节的附件
	 */
	public List<StuffLib> getCpageStuffsByCoursid(int id)throws ElException;
	
	/**
	 * 定级结束后更新定的等级之前的培训班进度为100
	 * @param classid
	 * @param userid
	 * @throws ElException
	 */
	public void updateCourseProcessByClassid(int classid,int userid) throws ElException;
	
	/**
	 * 判断课程是否初始化进度100
	 * @param courseid
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean getCourseInitCompliance(int courseid,int classid,int userid) throws ElException;

	/**
	 * 我添加的课程
	 */
	public List<Course> myListAllCourse(CourseType ctypeTree,int depid,int role, String name, int ctid,
			int pageNow, int pageSize,String status,int userid) throws ElException;
	public int myListAllCourseSize(CourseType ctypeTree,int depid , int role, String name, int ctid,String status,int userid) throws ElException ;
	
	public List<Course> getTjCourses(int ctypeid,int hot)throws ElException;
}
