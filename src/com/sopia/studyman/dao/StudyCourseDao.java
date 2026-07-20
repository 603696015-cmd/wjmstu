package com.sopia.studyman.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseNote;
import com.sopia.courseman.entities.CourseType;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyCourseRecord;
import com.sopia.studyman.entities.MyRoom;

public interface StudyCourseDao {
	public void addCnote(CourseNote cnote) throws ElException;

	public List<CourseNote> listCnotes(int userid, int courseid)
			throws ElException;

	public void submitCnotes(CourseNote courseNote) throws ElException;

	public List<MyCourse> listMyCreditCourse(int userid) throws ElException;

	public CourseNote getCnoteByid(int id) throws ElException;

	public void deleteCnote(int id) throws ElException;

	public void alterCnote(CourseNote cnote) throws ElException;

	public List<CourseNote> listMyCnotes(int userid) throws ElException;

	public List<MyCourse> listMyCourse(int userid, int status)
			throws ElException;

	public List<MyCourse> listMyCourse(int userid) throws ElException;

	public List<MyCourse> listMyCourse(int userid, int status, int pageNow,
			int pageSize) throws ElException; 
	public List<MyCourse> listMyCourse(int userid, int pageNow, int pageSize)
			throws ElException;
	public List<MyCourse> listMyCepingCourse(int userid, int pageNow, int pageSize)
	throws ElException;
	
	public List<MyCourse> listMyAllCourse(int userid, int pageNow, int pageSize)
	throws ElException;
	/**
	 * 个人中心首页我的课程
	 * @param userid
	 * @param number
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> study_index_listMyAllCourse(int userid, int number)
	throws ElException;
	
	public List<MyCourse> listMyCourseByClassid(int userid,int classid,int pageNow,int pageSize) throws ElException;
	
	public int listMyObCourseSize(int userid,int classid)throws ElException;

	public int listMyCourseSize(int userid) throws ElException;
	public int listMyCepingCourseSize(int userid) throws ElException;
	
	public int listMyAllCourseSize(int userid) throws ElException;

	public int listMyCourseSize(int userid, int status) throws ElException;

	public void saveMyCPage(MyCPage myCPage) throws ElException;
	/**课程学习保存
	 * @param myCPage
	 * @throws ElException
	 */
	public void saveMyCourseStudy(MyCPage myCPage) throws ElException;
	
	public void updateStudyNextCpage(MyCPage myCPage) throws ElException;

	public void setCPagePassed(MyCPage myCPage) throws ElException;

	public void saveMyCourse(MyCourse myCourse) throws ElException;

	public void setCoursePassed(MyCourse myCPage) throws ElException;

	public boolean courseIsPassed(MyCourse myCourse) throws ElException;

	public boolean checkMyCourse(MyCourse myCourse) throws ElException;

	// public boolean checkMySelectCourse(int userid, int courseid)
	// throws ElException;

	public void intoMyCourse(MyCourse myCourse) throws ElException;

	public void intoMyCPage(MyCPage myCPage) throws ElException;

	public boolean checkMyCPage(MyCPage myCPage) throws ElException;

	public MyCPage getMyCPage(int userid, int cpid) throws ElException;

	public int getMyLastCpage(int userid, int courseid) throws ElException;

	public List<MyCPage> listCpsbyCUid(int courseid, int userid)
			throws ElException;

	public boolean checkCpageIsFinish(int cpid, int userid) throws ElException;

	public boolean checkPpaperIsFinish(int ppid, int userid) throws ElException;

	public boolean checkQpaperIsFinish(int qpid, int userid) throws ElException;

	public boolean checkCourseIsFinish(int courseid, int userid)
			throws ElException;

	public boolean checkCourseIsPassed(int courseid, int userid)
			throws ElException;

	public List<Course> listAllCourseFromThis(int userid, int depid,
			String name, int ctid, int pageNow, int pageSize)
			throws ElException;
	
	public List<Course> listAllCourseFromThis(CourseType ctypeTree,int userid, int depid,
			String name, int ctid, int pageNow, int pageSize)
			throws ElException;

	public List<Course> listAllCourseFromSuper(int userid, int depid,
			String name, int ctid, int pageNow, int pageSize)
			throws ElException;

	public int listAllCourseSizeFromThis(int userid, int depid, String name,
			int ctid) throws ElException;
	public int listAllCourseSizeFromThis(CourseType ctypeTree,int userid, int depid, String name,
			int ctid) throws ElException;

	public int listAllCourseSizeFromSuper(int userid, int depid, String name,
			int ctid) throws ElException;

	public void studyApplyCourse(int userid, int courseid) throws ElException;

	public void courseFinishSet(int userid, int courseid) throws ElException;

	public List<Course> listPhCourse(int pageNow, int pageSize)
			throws ElException;

	public int listPhCourseSize() throws ElException;

	public void study_course_delete(int userid, int courseid)
			throws ElException;

	public boolean study_course_delete_check(int userid, int courseid)
			throws ElException;

	public MyCourse getMyCourseInfo(int userid) throws ElException;

	public MyCourse getMyStudyCourse(int userid, int courseid)
			throws ElException;
	/**
	 * 查询课程申请状态
	 * @author jiahaijiang
	 * @param myCourse
	 * @return
	 * @throws ElException
	 */
	public int checkMyCourseValid(MyCourse myCourse) throws ElException;
	
	/**
	 * 保存用户学习的进度
	 * @param myCourse
	 * @param classid
	 * @throws ElException
	 */
	public void saveMyCourse(MyCourse myCourse,int classid) throws ElException;
	
	/**
	 * 获取学员的课程信息
	 * @param userid
	 * @param courseid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public MyCourse getMyStudyCourse(int userid, int courseid,int classid)throws ElException;
	
	/**
	 * 保存课程的时间(多章节的)
	 * @param myCPage
	 * @param classid
	 * @throws ElException
	 */
	public void saveMyCPage(MyCPage myCPage,int classid) throws ElException;
	/**
	 * 保存学员学习时间(加班级)
	 * @param myCPage
	 * @param classid
	 * @throws ElException
	 */
	public void intoMyCPage(MyCPage myCPage,int classid) throws ElException;
	
	/**
	 *检测该页面是否被注册 
	 * @param myCPage
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public boolean checkMyCPage(MyCPage myCPage,int classid) throws ElException;
	
	
	/**
	 * 查看章节学习情况（绑定班级）
	 * @param courseid
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<MyCPage> listCpsbyCUid(int courseid, int userid,int classid) throws ElException;
	public List<MyCPage> listCpsbyCUid_wjm(int courseid, int userid,int classid) throws ElException;
	
	/**
	 * 获取标准课程某章节信息(加班级)
	 */
	public MyCPage getMyCPage(int userid, int cpid,int classid) throws ElException;
	/**
	 * 保存学员的实际学习时长
	 * @param myCourse
	 * @param classid
	 * @throws ElException
	 */
	public void setStudyCoursePasstime2(MyCourse myCourse,int classid) throws ElException;
	/**
	 * 保存学员课程章节的实际学习时长
	 * @param myCourse
	 * @param classid
	 * @throws ElException
	 */
	public void setStudyCpagePasstime2(MyCPage myCPage,int classid) throws ElException;
	
	/**
	 * 保存课程章节
	 * @param myCPage
	 * @param classid
	 * @param ispassed 为1：代表考过了
	 * @param during
	 * @throws ElException
	 */
	public void saveMyCPage(MyCPage myCPage,int classid,int ispassed,int passtime) throws ElException;
	/**
	 * 更新学员课程章节练习的成绩
	 * @param myCPage
	 * @param score
	 * @param classid
	 * @throws ElException
	 */
	public void updateStudyCpageCcore(MyCPage myCPage,float score,int classid) throws ElException;
	/**
	 * 设置考过
	 * @param myCPage
	 * @param classid
	 * @throws ElException
	 */
	public void updateStudyCpagePassed2(MyCPage myCPage,int classid) throws ElException;
	/**
	 * 获取学员课程章节信息
	 * @param courseid
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<MyCPage> getStudyCpageInfo(int courseid, int userid,int classid) throws ElException;
	/**
	 * 获取最后学习时间的章节id
	 * @param userid
	 * @param courseid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getMyLastCpage(int userid, int courseid,int classid) throws ElException;
	/**
	 * 更新学员的最后学习时间
	 * @param userid
	 * @param courseid
	 * @throws ElException
	 */
	public void cPageFinishSet(int userid, int cpid,int classid) throws ElException;
	
	
	/**
	 * Description: scorm課程章節學習保存
	* @Version1.0 2012-7-23 下午04:25:06 by 闻益舜（wenyishun110@163.com）创建
	 * @param myCPage
	 * @throws ElException
	 */
	public void saveMyCPage_S(MyCPage myCPage) throws ElException;

	/**
	 * Description: scorm課程學習保持
	* @Version1.0 2012-7-23 下午04:25:26 by 闻益舜（wenyishun110@163.com）创建
	 * @param myCourse
	 * @throws ElException
	 */
	public void saveMyCourse_S(MyCourse myCourse)  throws ElException;
	/**
	 * 添加学员课程学习记录
	 * @param myCourseRecord
	 * @throws ElException
	 */
//	public void addStudyCourseRecord(MyCourseRecord myCourseRecord) throws ElException;
	/**
	 * 添加学员课程学习记录
	 * @param myCourseRecord
	 * @throws ElException
	 */
	public int addStudyCourseRecord(int courseid,int classid,int cpid,int userid) throws ElException;
	/**
	 * 更新学员课程学习记录的状态和退出时间
	 * @param status
	 * @param endtime 退出时间（如果退出时间等于null，那么这条数据记录有误，可能是服务器重启造成）
	 * @throws ElException
	 */
	public void updateStudyCourseRecordStatusByid(int recordId,int status,Timestamp endtime) throws ElException;
	/**
	 * 更新学员课程学习记录的状态和退出时间
	 * @param status
	 * @param endtime 退出时间（如果退出时间等于null，那么这条数据记录有误，可能是服务器重启造成）
	 * @throws ElException
	 */
	public void updateStudyCourseRecordStatus(int courseid,int classid,int cpid,int userid,int status,Timestamp endtime) throws ElException;
	/**
	 * 更新该学员所有结束时间为空的学习记录（用户退出和session销毁时有调用）
	 * @param userid
	 * @param status
	 * @param endtime
	 * @throws ElException
	 */
	public void updateStudyCourseRecordStatus(int userid,int status,Timestamp endtime) throws ElException;
	/**
	 * 保存学习记录的学习时间
	 * @param id
	 * @param passtime
	 * @throws ElException
	 */
	public void saveStudyCourseRecordPasstime(int id,int passtime) throws ElException;
	
	/**
	 * 合计我的所有课程 信息
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<Integer> listMyAllCourse(int userid)
	throws ElException ;
	
	/**
	 * 调用JTM接口插入课程分配表时，删除上次插入的课程
	 * @param userid
	 * @param classid
	 * @throws ElException
	 */
	public void deleteCePingCoursesByUseridAndClassid(int userid,int classid ) throws ElException;
	/**
	 * 调用JTM接口返回课程ids，插入课程分配表
	 * @param userid
	 * @param courseid
	 * @throws ElException
	 */
	public void insertCepingCourse(int userid,int courseid) throws ElException;
	
	/**
	 * 检查章节状态
	 */
	public int checkPass(int userid,MyCPage myCPage) throws ElException;
	
	
	
	public List<MyCPage> myCPages(int userid,MyCPage myCPage)throws ElException;
	
	/**
	 * 获取下一个章节（章节中不带考场）
	 * @param classid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public int getNextCpid(int classid,int courseid,int cpid) throws ElException;
	
	/**
	 * 修改学员学习记录
	 */
	public void updateStudyCourse(MyCourse mycourse)throws ElException;
	//在用户打开时插入study_course一条记录
	public void insertOneRecord(int userid,int courseid)throws ElException;
	//检测用户记录是否插入
	public boolean  isRecord(int userid,int courseid)throws ElException;
	
}
