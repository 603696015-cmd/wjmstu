package com.sopia.courseman.dao;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.CRE_note;
import com.sopia.courseman.entities.ClassPara;
import com.sopia.courseman.entities.ErPara;
import com.sopia.courseman.entities.EroomBatch;
import com.sopia.courseman.entities.EroomBatchLib;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.EroomRegistration;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.ExamRoomAuditDescribes;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.Question;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.studyman.entities.SimpleRemack;

/**
 * @author Administrator
 *
 */

public interface EroomDao {

	/**
	 * 复制考场
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public int copyEroom(int id) throws ElException;

	/**
	 * 复制考场设定培训班及 课程
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public int copyEroom(int id,int classid,int courseid) throws ElException;
	/**
	 * 培训班课程考场列表
	 * @param courseid 课程id
	 * @param classid  培训班id
	 * @return
	 * @throws ElException
	 */
	public ExamRoom getExamRoom(int courseid,int classid) throws ElException;
	/**
	 * 所有课程类别列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public List<EroomLib> getEroomLibChilds(int parentid) throws ElException;

	/**
	 * 得到指定id的课程类别
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public EroomLib getEroomLibById(int id) throws ElException;

	/**
	 * 修改课程类别
	 * 
	 * @param EroomLib
	 * @throws ElException
	 */
	public void alterEroomLib(EroomLib EroomLib) throws ElException;

	/**
	 * 添加课程类别
	 * 
	 * @param EroomLib
	 * @throws ElException
	 */
	public int addEroomLib(EroomLib EroomLib) throws ElException;

	/**
	 * 删除课程类别
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void deleteEroomLib(int id) throws ElException;

	public void deleteEroomLibAndSub(int id) throws ElException;

	public EroomLib getEroomLibRoot() throws ElException;

	public EroomLib getEroomLibTree(int from, int stop, boolean containStop)
			throws ElException;

	public EroomLib getEroomLibTree(int userid, String op, int stop,
			boolean containStop) throws ElException;

	public void addOpusers(String type, int userid, int depid)
			throws ElException;

	public void deleteOpusers(String type, int userid, int depid)
			throws ElException;

	public boolean checkOpUsers(String type, int userid, int depid)
			throws ElException;

	public List<ELUser> getOpUsers(String type, int depid) throws ElException;

	public List<ExamRoom> listErWithoutCourse(int userid, int pn, int ps)
			throws ElException;
	
	public List<ExamRoom> listErWithoutCourse(EroomLib eroomLibTree,int userid, int role ,String sqlW, int pn, int ps)
	throws ElException;//权限树获取数据 hwc

	public int listErWithoutCourseSize(EroomLib eroomLibTree,int userid , int role ,String sqlW) throws ElException;//权限树获取数据 hwc
	
	public List<ExamRoom> listErWithoutCourse(int[] erids,EroomLib eroomLibTree, int pageNow,
			int pageSize) throws ElException;

	public int listErWithoutCourseSize(int[] erids,EroomLib eroomLibTree, int pageNow,
			int pageSize) throws ElException;
	public int listErWithoutCourseSize(int userid) throws ElException;

	public void addExamRoom(ExamRoom examRoom) throws ElException;

	public void deleteExamRoom(int id) throws ElException;

	public void alterExamRoom(ExamRoom examRoom) throws ElException;

	public ExamRoom getExamRoomByid(int id) throws ElException;
	public ExamRoom getExamRoomByid_cisco(int id,int classid) throws ElException;

	public List<ELUser> listCanAssignToRoomUsers(int roomid) throws ElException;

	public List<ELUser> listCanAssignToRoomUsers_bk(int roomid, int bkroomid)
			throws ElException;

	public List<ELUser> listAssignToRoomUsers(int roomid) throws ElException;

	public List<ExamRoom> listExamRoom(int courseid) throws ElException;

	public List<ExamRoom> listMyExamRoom(int userid, String title, int pageNow,
			int pageSize) throws ElException;
	public List<ExamRoom> listMyExamRoom(int userid, ExamRoom examRoom, int pageNow,
			int pageSize) throws ElException;
	public int listMyExamRoomSize(int userid, String title) throws ElException;

	public List<ExamRoom> listMyDepExamRoom(int depid, String title,
			int pageNow, int pageSize) throws ElException;
	
	public List<ExamRoom> listMyDepExamRoom(EroomLib eroomLibTree,int erlibid, int role ,String sqlW, int pageNow,
			int pageSize) throws ElException ; 
	
	public List<ExamRoom> listMyDepExamRoomAvalid(EroomLib eroomLibTree,int erlibid, int role , int pageNow,
			int pageSize) throws ElException ;

	public int listMyDepExamRoomSize(int depid, String title)
			throws ElException;

	public int listMyDepExamRoomSize(EroomLib eroomLibTree,int erlibid , int role,String sqlW) throws ElException;
	public int listMyDepExamRoomSizeAvalid(EroomLib eroomLibTree,int erlibid , int role) throws ElException;

	public List<Examprac> listexamprac(int useid, int begin, int end)
			throws ElException;

	public List<Examprac> listexampracvalid(int begin, int end)
			throws ElException;

	public int listexampracvalidsize() throws ElException;

	public void exampracSh(int roomid, int valid) throws ElException;

	public void examRoomavalid(int roomid, int avalid) throws ElException;
	
	public void examRoomisNormal(int roomid, int isNormal ) throws ElException;

	public int listexampracsize(int userid) throws ElException;

	public void addexamprac(Examprac examprac) throws ElException;

	public void alterexamprac(Examprac examprac) throws ElException;

	public void deleteexamprac(int id) throws ElException;

	public Examprac getexamprac(int id) throws ElException;

	public List<ELUser> listassignedepracusers(int eprid) throws ElException;

	public List<ELUser> listcanassignepracusers(int eprid) throws ElException;

	public boolean checkepracuser(int eprid, int userid) throws ElException;

	public void addepracuser(int eprid, int userid) throws ElException;

	public void deleteepracuser(int eprid, int userid) throws ElException;

	
	public void addEroomusers(String type, int userid, int depid)
			throws ElException;
	/**
	 * 添加考场阅卷组长
	 */
	public void addEroomusers(String type, int userid, int roomid,int isHeader)
	throws ElException;

	/**
	 * 修改考场阅卷组长
	 */
	public void UpdateEroomusers(String type, int userid, int roomid, int isLeader)
			throws ElException; 

	public List<ELUser> getEroomUsers_ZuZhang(String type, int roomid)
	throws ElException;

	public void deleteEroomusers(String type, int userid, int depid)
			throws ElException;

	public boolean checkEroomUsers(String type, int userid, int depid)
			throws ElException;
	
	public boolean checkEroomIsUsers(String type, int depid)
	throws ElException;

	public List<ELUser> getEroomUsers(String type, int depid)
			throws ElException;

	public void addEroomeps(int roomid, int epid, int pracid, int practime,
			float pracscore, float shouldpass, int stuview) throws ElException;
	
	public ExamPaper getEroomeps(int roomid, int epid) throws ElException;

	public void alterEroomeps(int roomid, int epid, int pracid, int practime,
			float pracscore, float shouldpass, int stuview) throws ElException;
	
	/**
	 * 更新考场试卷
	 * @param roomid
	 * @param epid
	 * @param pracid
	 * @param practime
	 * @param pracscore
	 * @param passgrade
	 * @param stuview
	 * @param pid
	 * @throws ElException
	 */
	public void alterEroomeps(int roomid, int epid, int pracid, int practime,
			float pracscore, float passgrade, int stuview,int pid) throws ElException;

//	/**
//	 * 删除考场试卷（假）
//	 * @param roomid
//	 * @param epid
//	 * @throws ElException
//	 */
//	public void deleteEroomeps(int roomid, int epid) throws ElException;

	public boolean checkEroomeps(int roomid, int epid) throws ElException;

	public List<ExamPaper> getEroomeps(int roomid) throws ElException;

	public List<MyRoom> listEroomtesters(int roomid, int pageNOw, int pagesize)
			throws ElException;

	public int listEroomtesterssize(int roomid) throws ElException;

	public boolean checkuser2eroom(int roomid, int userid) throws ElException;

	public void adduser2eroom(int roomid, int userid, int valid)
			throws ElException;
//	public void adduser2eroom_cisco(int roomid, int userid,int valid,int joinway)
//		throws ElException;
	public void adduser2eroom(int roomid, int userid) throws ElException;
	public void adduser2eroom_cisco(int classid,int roomid, int userid,int joinway) throws ElException;
	public void deleteuser2eroom(int roomid, int userid) throws ElException;

	public List<ExamPaper> getEroomepwithusizes(int roomid) throws ElException;

	public List<ELUser> listroom2userbyurid(int epid, int roomid)
			throws ElException;

	public List<ELUser> listroom2userbyurid(int epid, int roomid,int pageNow,int pageSize)
	throws ElException;
	
	public int listroom2userbyuridSize(int epid, int roomid)
	throws ElException;

	public List<ExamRoom> listExamRoomValid(int userid) throws ElException;
	
	public int listExamRoomValidsize(int userid, int role,String sqlW) throws ElException;//hwc

	public List<ExamRoom> listExamRoomValid(int userid ,int role ,String sqlW , int pageNow, int pageSize) throws ElException;//hwc
	
	public List<ExamRoom> listExamRoomSelectings(int userid ,int role ,String sqlW , int pageNow, int pageSize) throws ElException;//hwc
	
	public int listExamRoomSelectingsSize(int userid, int role,String sqlW) throws ElException;//hwc

	public List<ExamRoom> listExamRoomSh(int pageNow, int pageSize, int libid)
			throws ElException;
	
	public List<ExamRoom> listExamRoomSh(EroomLib eroomLibTree,int pageNow, int pageSize, int libid, int role )
	throws ElException;//根据权限树查数据  hwc
	
	public int listExamRoomShSize(EroomLib eroomLibTree,int libid, int role ) throws ElException;//根据权限树查数据  hwc

	public List<ExamRoom> listExamRoomSh(int pageNow, int pageSize,EroomLib eroomLibTree, int[] erids)
	throws ElException;

	public int listExamRoomShCount(int pageNow, int pageSize,EroomLib eroomLibTree, int[] erids)
	throws ElException;

	public int listExamRoomShSize(int libid) throws ElException;
	

	public List<ExamRoom> listExamRoomRead(int userid,String sqlw , int pageNOw, int pageSize)
			throws ElException;

	public int listExamRoomReadsize(int userid,String sqlw) throws ElException;

	public List<MyExamPaper> listReadPapers(int roomid, int pN, int pS)
			throws ElException;

	public int listReadPapersSize(int roomid) throws ElException;

	public void requiz(int id) throws ElException;

	public void examRoomSh(int roomid, int svalid) throws ElException;
	
	public void examRoomS(int roomid, int valid) throws ElException;

	public void examRoomUvalid(int roomid,int uvalid) throws ElException;

	public List<Department> listCanAssign2dep(int roomid, int epid)
			throws ElException;

	public List<Department> listAssigned2dep(int roomid, int epid)
			throws ElException;

	public void assign2dep(int roomid, int depid, int epid) throws ElException;

	public void unassign2dep(int roomid, int depid, int epid)
			throws ElException;

	public List<ELUser> listUsersBydep(int depid) throws ElException;

	public List<Department> listpracCanAssign2dep(int roomid)
			throws ElException;

	public List<Department> listpracAssigned2dep(int roomid) throws ElException;

	public void pracassign2dep(int roomid, int depid) throws ElException;

	public void pracunassign2dep(int roomid, int depid) throws ElException;

	/**
	 * Description:考试批次管理
	 * 
	 * @Version1.0 2011-9-5 上午11:39:53 by 闻益舜（wenyishun110@163.com）创建
	 * @param parentid
	 * @return
	 * @throws ElException
	 */
	public List<EroomBatchLib> getErbatchLibChilds(int parentid)
			throws ElException;

	public EroomBatchLib getErbatchLibById(int id) throws ElException;

	public void alterErbatchLib(EroomBatchLib erbatchLib) throws ElException;

	public void addErbatchLib(EroomBatchLib erbatchLib) throws ElException;

	public void deleteErbatchLib(int id) throws ElException;

	public void deleteErbatchLibAndSub(int id) throws ElException;

	public EroomBatchLib getErbatchLibRoot() throws ElException;

	public EroomBatchLib getErbatchLibTree(int from, int stop,
			boolean containStop) throws ElException;

	public EroomBatchLib getErbatchLibTree(int userid, String op, int stop,
			boolean containStop) throws ElException;

	public void addErblOpusers(String type, int userid, int depid)
			throws ElException;

	public void deleteErblOpusers(String type, int userid, int depid)
			throws ElException;

	public boolean checkErblOpUsers(String type, int userid, int depid)
			throws ElException;

	public List<ELUser> getErblOpUsers(String type, int depid)
			throws ElException;

	public EroomBatch getErbatchById(int id) throws ElException;

	public void alterErbatch(EroomBatch erbatch) throws ElException;

	public void addErbatch(EroomBatch erbatch) throws ElException;

	public void deleteErbatch(int id) throws ElException;

	public List<EroomBatch> listErbatch(int pageNow, int pageSize)
			throws ElException;
	
	public int listErbatchCount()throws ElException;

	public void addErbatchRoom(int roomid, int erblid) throws ElException;

	public void deleteErbatchRoom(int roomid, int erblid) throws ElException;

	public boolean checkErbatchRoom(int roomid, int erblid) throws ElException;

	public List<ExamRoom> listErbatchRooms(int erblid) throws ElException;

	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public Department listAssignedDep(int depid, int courseid, int state,
			List<Integer> userid, String starttime, String endtime,
			String classname) throws ElException;

	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int depid,
			int courseid, int state, List<Integer> userid, String starttime,
			String endtime, String classname, int examRoomId, int examPaperId,
			ELUser elUser, ElClType cltype, ElClType cltypeTree)
			throws ElException;
	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listAssignedUser(int pageNow, int pageSize,int role ,Department depTree , int depid,
			int courseid, int state, List<Integer> userid, String starttime,
			String endtime, String classname, int examRoomId, int examPaperId,
			ELUser elUser, ElClType cltype, ElClType cltypeTree)
			throws ElException;

	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
//	public int listAssignedUserSize(int depid, int courseid, int state,
//			List<Integer> userid, String starttime, String endtime,
//			String classname, int examRoomId, int examPaperId, ELUser elUser,
//			ElClType cltype, ElClType cltypeTree) throws ElException;
	
	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
//	public int listAssignedUserSize(int role ,Department depTree ,int depid, int courseid, int state,
//			List<Integer> userid, String starttime, String endtime,
//			String classname, int examRoomId, int examPaperId, ELUser elUser,
//			ElClType cltype, ElClType cltypeTree) throws ElException;


	/**
	 * 查询已经分配的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public Department listCanAssignDep(int depid, int courseid, int state,
			int examRoomId, int examPaperId) throws ElException;

	/**
	 * Description:监考
	 * 
	 * @Version1.0 2011-9-15 上午08:29:09 by 闻益舜（wenyishun110@163.com）创建
	 * @param roomid
	 * @param pageNOw
	 * @param pagesize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listEroomjks(int roomid, int pageNOw, int pagesize)
			throws ElException;

	public int listEroomjksize(int roomid) throws ElException;

	public String getDztest(Question question, int age) throws ElException;

	public void testerAddTime(int id,  int time)
			throws ElException;

	public void testersAddTime(int roomid, int time) throws ElException;
	
	//考场组合搜索
	public List<ExamRoom> combinationSearchExamroom(ExamRoom examRoom,EroomLib eroomLibTree, int role ,int Lid,int pageNow,
			int pageSize)throws ElException;
	public int combinationSearchExamroomCount(ExamRoom examRoom,EroomLib eroomLibTree,int role,int Lid)throws ElException;
	
	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException;
	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserUseGrant(int userId) throws ElException;
	
	/**
	 * @param examRoomAudit
	 * @throws ElException
	 * 审核回复
	 */
	public void UExamRoomAuditContents(ExamRoomAuditDescribes erAuditdes) throws ElException;
	/**
	 * @param roomid
	 * @throws ElException
	 * 申请修改审核查询
	 */
	public ExamRoomAuditDescribes getExamRoomAuditDescribesByRoomid(int roomid) throws ElException;
	
	/**
	 * @param examRoomAudit
	 * @throws ElException
	 * 插入审核回复
	 */
	public void openExamRoomAudit(ExamRoomAuditDescribes examRoomAudit) throws ElException; 
	/**
	 * 根据用户id和课程id获取所对应的classid
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getStudyCourseInClass(int courseid) throws ElException;
	
	/**
	 * 学员的某一班级（课程考试）分配到考场
	 * @param roomid
	 * @param userid
	 * @param valid
	 * @param classid
	 * @throws ElException
	 */
	public void adduser2eroom(int roomid, int userid, int valid,int classid,int joinway)
	throws ElException;
	
	/**
	 * 检查用户是否已经分配到该考场
	 */
	public boolean checkuser2eroom(int roomid, int userid,int classid) throws ElException;
	
	/**
	 * 检查用户是否通过该培训班分配到该考场
	 */
	public boolean checkusereroom(int roomid, int userid,int classid) throws ElException;
	/**
	 * 查询可以培训的学员(加培训班)
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int depid,
			int courseid, int state, List<Integer> userid, String starttime,
			String endtime, String classname, int examRoomId, int examPaperId,
			ELUser elUser, ElClType cltype, ElClType cltypeTree,int classid,Department depTree)
			throws ElException;
	
	//更新关联表状态
	public void setClassBindingCourse(int classId, int courseid,int eroomid) throws ElException;
	//更新关联表状态
	public void setClassBindingCourse(int classId, int courseid,int eroomid,String tableName) throws ElException;
	//获取到考场的试卷
	public int getExamRoomByQuizinfoId(int roomid,int classid,int epid) throws ElException;
	//绑定考场试卷
	public void setEroomBindingQuizinfo(int classId, int courseid,int sqiid) throws ElException;
	/**
	 * 检查用户是否已经分配到该考场
	 */
	public boolean checkuserClassBindingCourse(int classId, int courseid) throws ElException;
	//获取考场Id
	public ExamRoom getClassBindingCourseByRoomId(int classId, int courseid) throws ElException;
	public int getClassBindingCourseByRoomId(int classId) throws ElException;
	
	/**
	 * 获取课程绑定的考场id
	 * @param classId
	 * @param status 课程为必修 还是选修
	 * @return
	 * @throws ElException
	 */
	public String getBindingCourseByRoomId(int classId,int status) throws ElException;
	/**
	 * 解除用户mac地址
	 * @param userid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int updateMacAddr(int userid,int roomid) throws ElException;
	/**考场密码修改
	 * @param userid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int eroom_pwdalter(String pwd,Date d, int roomid) throws ElException;
	/**考场的试卷缓存刷新
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public void eroom_epcacherefresh(int roomid) throws ElException;
	/**
	 * 更新考场信息的绑定班级
	 * @param examRoomId
	 * @param classId
	 * @param courseid
	 * @throws ElException
	 */
	public void updateExamRoomInBandClassid(int examRoomId,int bandclassid) throws ElException;
	/**
	 * 检测该考场的是否有绑定班级课程或单独课程(根据班级和课程检测)
	 * @param examRoomId
	 * @return
	 * @throws ElException
	 */
	public boolean checkExamRoomIsBand(int courseId,int bandClassid) throws ElException;
	/**
	 * 取消考场的班级绑定
	 * @param examRoomId
	 * @throws ElException
	 */
	public void cancelExamRoomBandClass(int courseId,int bandClassid) throws ElException;
	/**
	 * 根据课程id获取他的所有考场信息
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listExamRoom2(int courseid,int classid) throws ElException;
	/**
	 * 根据试卷id获取试题
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public List<Integer> getQuestionids(int epid)throws ElException;
	/**
	 * 根据考场Id获取试卷Id
	 */
	public int getEpidByRoomid(int roomid)throws ElException;
	/**
	 * 根据考场，班级查出学员
	 * @param roomid
	 * @param courseid
	 * @param classid
	 * @throws ElException
	 */
	public void updateStudySqiidInit(int roomid,int courseid,int classid) throws ElException;
	/**
	 * 更新学员课程的sqiid
	 * @param roomid
	 * @param courseid
	 * @param classid
	 * @throws ElException
	 */
	public void updateStudySqiid(int userid,int courseid,int classid,int sqiid) throws ElException;
	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public int listAssignedUserSize(int depid, int courseid, int state,
			List<Integer> userid, String starttime, String endtime,
			String classname, int examRoomId, int examPaperId, ELUser elUser,
			ElClType cltype, ElClType cltypeTree,int classid,Department depTree) throws ElException;
	/**
	 * 删除试卷的练习
	 * @param erid
	 * @param epid
	 * @throws ElException
	 */
	public void deleteEroomepsLx(int erid, int epid) throws ElException;
	/**
	 * 获取考场的数量
	 * @param userid
	 * @param examRoom
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listMyExamRoomCount(int userid, ExamRoom examRoom) throws ElException;
	/**
	 * 分配考生list显示
	 * eroomLibTree 权限树
	 * erlibid 树id
	 * Hwc
	 */
//	public List<ExamRoom> listErWithoutCourse(EroomLib eroomLibTree,int erlibid, int role ,String sqlW,ExamRoom examRoom, int pageNow,
//			int pageSize) throws ElException;
	/**
	 * 分配考生list数量
	 * @param eroomLibTree
	 * @param erlibid
	 * @param role
	 * @param sqlW
	 * @param examRoom
	 * @return
	 * @throws ElException
	 */
//	public int listErWithoutCourseSize(EroomLib eroomLibTree,int erlibid , int role,String sqlW,ExamRoom examRoom) throws ElException;
	/**
	 * 场次复核list
	 * @param userid
	 * @param role
	 * @param sqlW
	 * @param examRoom
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listExamRoomValid(int userid,int role ,String sqlW ,ExamRoom examRoom,int pageNow,
			int pageSize) throws ElException;
	/**
	 * 场次复核list数量
	 * @param userid
	 * @param role
	 * @param sqlW
	 * @param examRoom
	 * @return
	 * @throws ElException
	 */
	public int listExamRoomValidsize(int userid,int role,String sqlW,ExamRoom examRoom) throws ElException;
	/**
	 * 人员选拔list
	 */
	public List<ExamRoom> listExamRoomSelectings(int userid,int role ,String sqlW ,ExamRoom examRoom,int pageNow,
			int pageSize) throws ElException;
	/**
	 * 人员选拔list数量
	 * @param userid
	 * @param role
	 * @param sqlW
	 * @param examRoom
	 * @return
	 * @throws ElException
	 */
	public int listExamRoomSelectingsSize(int userid,int role,String sqlW,ExamRoom examRoom) throws ElException;
	/**
	 * 考场初审list
	 * @param eroomLibTree
	 * @param erlibid
	 * @param role
	 * @param sqlW
	 * @param examEoom
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listMyDepExamRoom(EroomLib eroomLibTree,int erlibid, int role ,String sqlW,ExamRoom examRoom, int pageNow,
			int pageSize) throws ElException;
	/**
	 * 考场初审list数量
	 * @param eroomLibTree
	 * @param erlibid
	 * @param role
	 * @param sqlW
	 * @param examRoom
	 * @return
	 * @throws ElException
	 */
	public int listMyDepExamRoomSize(EroomLib eroomLibTree,int erlibid , int role,String sqlW,ExamRoom examRoom) throws ElException ;
	/**
	 * 考核考试阅卷list
	 * @param userid
	 * @param examRoom
	 * @param pageNOw
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listExamRoomRead(int userid,String sqlw,ExamRoom examRoom,int roleid, int pageNOw, int pageSize)
	throws ElException;
	/**
	 * 考核考试阅卷list数量
	 */
	public int listExamRoomReadsize(int userid,String sqlw,ExamRoom examRoom,int roleid) throws ElException;
	/**
	 * 流程备注
	 * */
	public List<CRE_note> getById(int id,String type)throws ElException;

	public List<CRE_note> getById(int elclassid,int courseid,int eroomid,String type)throws ElException;
	
	public void addCRE_note(CRE_note cre_note)throws ElException;
	
	/**
	 * 考场申请条件验证
	 * @param eroomid
	 * @return
	 * @throws ElException
	 */
	public boolean checkElclassRegistration(int eroomid)throws ElException;
	/**
	 * 增加考场申请条件
	 * @param erRegistration
	 * @throws ElException
	 */
	public void addEroomRegistration(EroomRegistration erRegistration) throws ElException;
	/**
	 * 更新考场申请条件
	 * @param erRegistration
	 * @throws ElException
	 */
	public void alterEroomRegistration(EroomRegistration erRegistration) throws ElException;
	/**
	 * 查询考场申请条件失败
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public EroomRegistration getEroomRegistration(int classid) throws ElException;
	/**
	 * 查询申请考场列表
	 * @param eroomLibTree
	 * @param erlibid
	 * @param role
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getApplyForeEroom(EroomLib eroomLibTree, int erlibid,ExamRoom eroom,int role,String sqlw, int pageNow,int pageSize) throws ElException;
	public int getApplyForeEroomSize(EroomLib eroomLibTree, int erlibid,ExamRoom eroom,int role,String sqlw) throws ElException;
	/**
	 *   根据eroomid查询可申请考场
	 * @param eroomid
	 * @return
	 * @throws ElException
	 */
	public ExamRoom getApplyForeEroomById(int eroomid) throws ElException;
	/**
	 * 获取考场学员信息
	 * @param roomid
	 * @param eluser
	 * @param epstatus
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listEroomjks(int roomid,ELUser eluser,int epstatus,int pageNow, int pageSize,Timestamp beginTime,Timestamp endTime)
	throws ElException;
	/**
	 * 查询考场学员的数量
	 * @param roomid
	 * @param eluser
	 * @param epstatus
	 * @return
	 * @throws ElException
	 */
	public int listEroomjksize(int roomid,ELUser eluser,int epstatus) throws ElException;
	/**
	 * 获取参加了考试的人数
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getJoinNumber(int eroomid)throws ElException;	
	/**
	 * 获取参加了考试的员信息
	 * @param eroomid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getEluserByExamRoomId(int eroomid)throws ElException;
	/**
	 * 校验userid(用户)在eroomids(多个考场)考场里是有否通过某个考场
	 * 当eroomids为一个考场时， 验证该用户是否通过该考场
	 * @param eroomids 考场id字符串
	 * @param userid 用户id
	 * @return
	 * @throws ElException
	 */
	public boolean checkEroomIspassed(String eroomids , int userid , String sqlWhere)throws ElException;	/**
	 * 校验userid(用户)在eroomids(多个培训班)培训班里是有否通过某个培训班
	 * 当eroomids为一个培训班时， 验证该用户是否通过该培训班
	 * @param eroomids 考场id字符串
	 * @param userid 用户id
	 * @return
	 * @throws ElException
	 */
	public boolean checkElclassIspassed(String elclassids , int userid,String sqlWhere)throws ElException;
	/**
	 * 练习人员查看
	 * @param eprid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listassignedepracusers(int eprid, int pageNow, int pageSize) throws ElException;
	/**
	 * 练习人员数量查看
	 * @param eprid
	 * @return
	 * @throws ElException
	 */
	public int listassignedepracusersSize(int eprid) throws ElException;
	/**
	 * 更新考场库的父节点
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateElibParentid(int pid, int npid) throws ElException;
	/**
	 * 更新考场的类别
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateExamroomParentid(int pid, int npid) throws ElException;
	/**
	 * 删除考场库
	 * @param ct
	 * @param id
	 * @throws ElException
	 */
	public void deleteElibAndSub(int id) throws ElException;
	/**
	 * 获取与当前考场考试时间重叠的考场
	 * @param elclass
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getExamRoomTimeoverList(ExamRoom examRoom, int pageNow, int pageSize) throws ElException;
	/**
	 * 获取与当前考场考试时间重叠的考场数量
	 * @param elclass
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getExamRoomTimeoverListCount(ExamRoom examRoom) throws ElException;
	/**
	 * 获取考场信息以及创建者信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public ExamRoom getExamRoomByid2(int id) throws ElException;
	/**
	 * 添加考场中的试卷
	 * @param erid
	 * @param epid
	 * @param pracid
	 * @param practime
	 * @param pracscore
	 * @param passgrade
	 * @param stuview
	 * @param quizlook
	 * @param scorelook
	 * @throws ElException
	 */
	public void addEroomeps(int erid, int epid, int pracid, int practime,
			float pracscore, float passgrade, int stuview,int quizlook,int scorelook,int quizcount,int passmanner,int sortid) throws ElException;
	/**
	 * 更新考场中的试卷
	 * @param roomid
	 * @param epid
	 * @param pracid
	 * @param practime
	 * @param pracscore
	 * @param passgrade
	 * @param stuview
	 * @param quizlook
	 * @param scorelook
	 * @throws ElException
	 */
	public void alterEroomeps(int roomid, int epid, int pracid, int practime,
			float pracscore, float passgrade, int stuview,int quizlook,int scorelook,int quizcount,int passmanner) throws ElException;
	/**
	 * 获取该培训班中所有被绑定的考场
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listExamRoomByClass(int classid) throws ElException;
	public List<ExamRoom> listExamRoomByClass_cisco(int classid,int userid) throws ElException;
	/**
	 * 根据学员考场相关信息搜索学员
	 * @param erParas
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnEroomSeach(List<ErPara> oldErParas,int queryManner,ELUser elUser) throws ElException;
	/**
	 * 根据学员考场相关信息搜索学员(分页)
	 * @param oldErParas
	 * @param roomid
	 * @param epid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnEroomSeach(List<ErPara> oldErParas,int roomid,int epid,int queryManner,ELUser elUser,int pageNow,int pageSize) throws ElException;
	/**
	 * 根据学员考场相关信息搜索学员数量
	 * @param oldErParas
	 * @return
	 * @throws ElException
	 */
	public int listUserOnEroomSeachSize(List<ErPara> oldErParas,int queryManner,ELUser elUser) throws ElException;
	/**
	 * 根据学员考场相关信息搜索学员(分页，适应培训班分配人员)
	 * @param oldErParas
	 * @param roomid
	 * @param epid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnEroomSeach(List<ErPara> oldErParas,int classid,int queryManner,ELUser elUser,int pageNow,int pageSize) throws ElException;
	/**
	 * 更新考场试卷的状态
	 * @param erid
	 * @param epid
	 * @param status
	 * @throws ElException
	 */
	public void udpateEroomepStatus(int erid, int epid,int status) throws ElException;
	/**
	 * 获取问卷集合信息
	 */
	public List<ExamRoom> listExamRoom(ElNode eroomLibTree,int sublibs,String sqlW,ExamRoom examRoom, int pageNow,
			int pageSize) throws ElException;
	public List<ExamRoom> listExamRoom2(ElNode eroomLibTree,int sublibs,String sqlW,ExamRoom examRoom, int pageNow,
			int pageSize) throws ElException;
	/*
	 * 获取问卷集合信息数量
	 */
	public int listExamRoomSize(ElNode eroomLibTree,int sublibs,String sqlW,ExamRoom examRoom) throws ElException;
	/*
	 * 获取问卷集合信息数量
	 */
	public int listExamRoomSize2(ElNode eroomLibTree,int sublibs,String sqlW,ExamRoom examRoom) throws ElException;
	/**
	 * 获取已参与问卷人数
	 */
	public int getStudyQueSize(int eroomid)throws ElException;
	/**
	 * 更新学员试卷状态
	 * @param erid
	 * @param epid
	 * @param delStatus
	 * @throws ElException
	 */
	public void udpateStudyepStatus(int erid, int epid, int delStatus) throws ElException;
	/**
	 * 学员的某一班级（课程考试）分配到考场
	 * @param roomid
	 * @param userid
	 * @param valid
	 * @param classid
	 * @throws ElException
	 */
	public void addusereroom(int roomid, int userid, int valid, int classid,
			int joinway) throws ElException;
	/**
	 * 更新学员考场状态
	 * @param erid
	 * @param epid
	 * @param delStatus
	 * @throws ElException
	 */
	public void udpateStudyRoomStatus(int roomid,int userid,int status) throws ElException;
	/**
	 * 获取可申请考场所有为审核的人员
	 * @param roomid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listEroomNoAuditUser(ELUser eu,ExamRoom room,Department dep,boolean consub,int status, int pageNow, int pageSize)
	throws ElException;
	/**
	 * 获取可申请考场所有未审核的人员数量
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int listEroomNoAuditUserSize(ELUser eu,ExamRoom room,Department dep,boolean consub,int status) throws ElException;
	/**
	 * 获取可报名的考场数量
	 * @param userid
	 * @param roleid
	 * @return
	 * @throws ElException
	 */
	public int getEroomAppcount(int userid,int roleid) throws ElException;
	/**
	 * 添加备注（可申请且需审核的考场（培训班）不通过原因）
	 * @param simpleRemack
	 * @throws ElException
	 */
	public void addSimpleRemack(SimpleRemack simpleRemack) throws ElException;
	/**
	 * 查询可申请且需审核的考场（培训班）不通过原因备注信息
	 * @param simpleRemack
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<SimpleRemack> listSimpleRemack(SimpleRemack simpleRemack, int pageNow, int pageSize) throws ElException;
	/**
	 * 获取该考场学员报名人数
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int getStudyApplyCount(int roomid) throws ElException;
	/**
	 * 获取考场计划招收人数
	 * 
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public int getEroomPlanNumber(int erid) throws ElException;
	/**
	 * 获取考场人数
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public int getEroomUserSize(int roomid) throws ElException;
	/**
	 * 获取考场人员信息
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listEroomStudyInfo(int roomid,int status,int pageNow,int pageSize) throws ElException;
	/**
	 * 获取考场人员信息数量
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public int listEroomStudyInfoSize(int roomid,int status) throws ElException;
	/**
	 * 获取考场中的所有试卷（不包括已删除的）
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> getEroomEps(int roomid) throws ElException;
	/**
	 * 删除学员考场记录
	 * @param roomid
	 * @param userid
	 * @throws ElException
	 */
	public void deleteStudyRoomApply(int roomid, int userid) throws ElException;
	/**
	 * 添加考场工种信息
	 * @param roomid
	 * @param jsIds
	 * @throws ElException
	 */
	public void addEroomJingzhong(int roomid, String[] jzIds) throws ElException;
	/**
	 * 查询考场所有工种信息
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<Integer> listEroomAllJingzhong(int roomid) throws ElException;
	/**
	 * 查询考场所有工种信息(加状态)
	 * @param roomid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Integer> listEroomAllJingzhong(int roomid,int status) throws ElException;
	/**
	 * 更新考场工种状态
	 * @param roomid
	 * @param jzIds
	 * @param status
	 * @throws ElException
	 */
	public void updateEroomJingzhong(int roomid, String[] jzIds,int status) throws ElException;
	/**
	 * 假删除考场库
	 * @param ct
	 * @param id
	 * @throws ElException
	 */
	public void deleteElibAndSubNot(int id) throws ElException;
	/**
	 * 更新考场库的状态
	 * @param ct
	 * @param id
	 * @throws ElException
	 */
	public void deleteeroomLibNot(int id) throws ElException;
	
	/**检测考场是否过期 (是return true;)
	 * @param id
	 * @throws ElException
	 */
	public boolean checkEroomIsTimeOut(int id)throws ElException;
	/**
	 * 更新考场试卷的序号
	 * @param sortid
	 * @param manner 1：上移 2：下移
	 * @throws ElException
	 */
	public void updateEroomEpSortid(int roomid,int epid,int sortid,int manner) throws ElException;
	/**
	 * 恢复学员试卷删除状态
	 * @param userid
	 * @param roomid
	 * @throws ElException
	 */
	public void udpateStudyepStatus(int userid, int roomid) throws ElException;
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
			int roomid,int epid,ELUser elUser, int pageNow,int pageSize) throws ElException;
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
	 * 根据学员培训班相关信息搜索学员
	 * 
	 * @param erParas
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnClassSeach(List<ClassPara> oldClassParas,
			 ELUser elUser) throws ElException;
	
	/**
	 * 获取课程的考场
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public int getCourseByRoomId(int courseid) throws ElException;
	/**
	 * 更新课程库的课程的考场给培训班
	 * @param courseid
	 * @param elclssid
	 * @param eroomid
	 * @throws ElException
	 */
	public void updateExamroom(int courseid, int elclssid,int eroomid) throws ElException; 
	/**
	 * 更新考试成绩
	 * @param myroom
	 * @throws ElException
	 */
	public void updateStudyroom(MyRoom myroom) throws ElException; 
	/**
	 * 线下考场添加考试成绩
	 */
	public void addStudyRoom(MyRoom myroom)throws ElException;
	
	/**
	 * 根据roomid和courseid得到title
	 */
	
	public List<ExamRoom> gettitles(int roomid,int courseid)throws ElException;
	/**
	 * 获取课程绑定的考场id
	 * @param classId
	 * @param status 课程为必修 还是选修
	 * @return
	 * @throws ElException
	 */
	public String getBindingCourseByRoomId(int classId,int status,String tableName) throws ElException;
	/**
	 * 培训班分配考场
	 * @param examroomid
	 * @param classid
	 * @throws ElException
	 */
	public void assignRoom(int examroomid,int classid ,int firstLearnLaterExam,int standardLine) throws ElException;
	public int getRoomidByClassid_cisco(int classid) throws ElException;
	
	/**
	 * 考场是否通过
	 * @param userid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int getIsPass(int userid,int roomid) throws ElException;
	
	/**
	 * 根据考场id获取我的考场信息
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public MyRoom getMyRoom(int roomid,int userid) throws ElException;
	
	/**
	 * 更新我的章节信息
	 * @param userid
	 * @param roomid
	 * @throws ElException
	 */
	public void updateMyCPage(int userid,int roomid) throws ElException;
	
	/**
	 * 获取试卷信息
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public MyExamPaper getExamPaperByRoomid(int roomid) throws ElException;
	
	/**
	 * 章节关联考场
	 * @param myCpage
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public MyCPage getBindingExamRooms (MyCPage myCpage,int userid) throws ElException;
	/**
	 * 章节关联考场
	 * @param cpageid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getCPageRooms(int cpageid,int courseid) throws ElException;
	
	/**
	 * 判断章节考场是否能考试
	 * @param examRoom
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean setExamRoomCanExam(ExamRoom examRoom,int courseid,int userid) throws ElException;
	/**
	 * 删除考场--试卷对应表中的数据
	 * @param roomid
	 * @throws ElException
	 */
	public void deleteEroomepByErid(int roomid)throws ElException;
	/**
	 * 删除试卷分配表中的数据
	 */
	public void deleteErEpUsersByErid(int roomid)throws ElException;
	/**
	 * 删除考场分配表中的数据
	 * @param roomid
	 * @throws ElException
	 */
	public void deleteErUserByErid(int roomid)throws ElException;
	
	/**
	 * 是否线下考场
	 */
	public boolean cheEroomIsXianxia(int roomid)throws ElException;
	
	/**
	 * 获取章节完成的考场
	 * @param userid
	 * @param myCPage
	 * @return
	 * @throws ElException
	 */
	public ExamRoom getFinishExamRoom(int userid,int courseid,MyCPage myCPage) throws ElException;
}
