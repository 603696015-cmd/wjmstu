package com.sopia.frontman.dao;

import java.util.List;

import com.sopia.assistman.entities.Poll;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.questionman.entities.StuffLib;

public interface FrontDao {
	public List<Course> listCourseByType(int pageNow, int pageSize, int type,
			boolean subcon) throws ElException;

	/**
	 * 根据类别和热度获取新闻信息
	 * @param pageNow
	 * @param pageSize
	 * @param typeid
	 * @param hot
	 * @return
	 * @throws ElException
	 */
	public List<News> listNewsByTidhot(int pageNow, int pageSize, int typeid,
			int hot) throws ElException;
	
	
	/**
	 *  根据部门、类型和热度获取新闻信息
	 * @param pageNow
	 * @param pageSize
	 * @param styleid  类型ID
	 * @param deptid	部门ID
	 * @param hot
	 * @return
	 * @throws ElException
	 */
	public List<News> listNewsByNsidByDepthot(int pageNow, int pageSize, int styleid,int deptid,
			int hot) throws ElException;
	
	/**
	 * 取得有二级页面的所有部门及其下级部门的ID,Name,Discription.
	 * @return
	 * @throws ElException
	 */
	public List<Department> listDeptByIssp() throws ElException;
	
	/**
	 * 根据类型和热度获取新闻信息
	 * @param pageNow
	 * @param pageSize
	 * @param styleid
	 * @param hot
	 * @return
	 * @throws ElException
	 */
	public List<News> listNewsByNsidhot(int pageNow, int pageSize, int styleid,
			int hot) throws ElException;
	
	public Course listCourseByTypeHot(int type) throws ElException;

	public List<Course> listCourseByHot(int pageNow, int pageSize, int hot)
	throws ElException;
	public List<Course> listCourseByHot(int pageNow, int pageSize, int hot,int depid)
	throws ElException;
	/**
	 * 获取最新的是否可申请课程
	 * @param pageNow
	 * @param pageSize
	 * @param isapplication 可申请 1  不可申请 0
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseByNewTime(int pageNow, int pageSize,int isapplication)throws ElException;
	public List<Course> listCourseByNewTime(int pageNow, int pageSize,int isapplication,int depid)throws ElException;
	
	/**
	 * 获取最新的是否可申请培训班
	 * @param pageNow
	 * @param pageSize
	 * @param isapplication 可申请 1  不可申请 0
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> listClassByNewTime(int pageNow, int pageSize,int isapplication)throws ElException;
	public List<ElClass> listClassByNewTime(int pageNow, int pageSize,int isapplication,int depid)
	throws ElException;
	/**
	 * 获取最新的是否可申请考场
	 * @param pageNow
	 * @param pageSize
	 * @param isapplication 可申请 1  不可申请 0
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listExamRoomByNewTime(int pageNow, int pageSize,int isapplication)
	throws ElException;
	public List<ExamRoom> listExamRoomByNewTime(int pageNow, int pageSize,int isapplication,int depid)
	throws ElException;
	public int listCourseCountByType(int type, boolean subcon)
	throws ElException;

	public List<Course> listPhCourse(int pageNow, int pageSize)
	throws ElException;

	public List<ExamRoom> listExamRooms(int eplibid, int pageNow, int pageSize)
	throws ElException;

	public int listExamRoomsSize(int eplibid) throws ElException;

	public List<ELUser> listPhUsers(int pageNow, int pageSize)
	throws ElException;
	
	/**
	 * 获取弹窗新闻
	 */
	public News getNewsInPop() throws ElException;
	/**
	 * 根据标题和类别获取最新的新闻
	 * @param pageNow
	 * @param pageSize
	 * @param typeid
	 * @param subcon
	 * @param title
	 * @return
	 * @throws ElException
	 */
	public List<News> listNewsByTid(int pageNow, int pageSize, int typeid,
			boolean subcon, String title) throws ElException;

	public List<News> listNewsByTidHot(int typeid, int hot, int pageNow,
			int pageSize) throws ElException;

	public int listNewsCountByTid(int typeid, boolean subcon, String title)
	throws ElException;

	public ElClass indexCountInfo() throws ElException;

	public List<Knowledge> listZxKnows(int pageNow, int pageSize)
	throws ElException;

	public List<Knowledge> listKnowsByType(int type, int pageNow, int pageSize)
	throws ElException;

	public Knowledge listKnowByType(int type) throws ElException;

	public List<Knowledge> listHotKnows(int pageNow, int pageSize, int hot)
	throws ElException;

	public List<Knowledge> listHotKnowsByDept(int pageNow, int pageSize, int hot,int deptid)
	throws ElException;
	
	public List<Department> listPhDeps(int pageNow, int pageSize)
	throws ElException;

	public List<Course> listCourseByName(int pageNow, int pageSize, String name)
	throws ElException;

	public int listCourseByNameSize(String name) throws ElException;

	public List<StuffLib> listStuff(String title, int pageNow, int pageSize)
	throws ElException;

	public int listStuffCount(String title) throws ElException;

	public List<ElClass> listClassByName(int pageNow, int pageSize, String name)
	throws ElException;

	public int listClassByNameSize(String name) throws ElException;

	public List<ElClass> listClassByTid(int pageNow, int pageSize, int id)
	throws ElException;

	public int listClassByTidSize(int tid) throws ElException;

	public boolean checkUserInClass(int userid, int classid) throws ElException;
	// public List<CourseType> listCtype(int pageNow, int pageSize) throws
	// ElException;
	//搜索资讯
	//最新推荐的帮助中心
	public List<News> listHotNnows(int pageNow, int pageSize, int hot)throws ElException;
	public List<News> listHotNnows(int pageNow, int pageSize,int type, int hot)throws ElException;
	public List<News> listHotNnowsByNewsStyle(int pageNow, int pageSize,int styleid, int hot)throws ElException;
	public List<News> SearchNews(String title, int pageNow,int pageSize) throws ElException;
//	public int SearchNewsCountByTid(String title)throws ElException;
	public List<Course> listCourseByName(int pageNow, int pageSize, Course course)throws ElException;
	//搜索课程
	public List<Course> listCourseByName(int pageNow, int pageSize, Course course,CourseType ctypeTree)throws ElException;
	public int listCourseByNameSize(int pageNow, int pageSize, Course course,CourseType ctypeTree)throws ElException;
	
	public List<NewsType> getNewsType()throws ElException;
	public List<News> SearchNews(News news, int pageNow,int pageSize) throws ElException;
	public int SearchNewsCountByTid(News news)throws ElException;
	//首页访问次数
	public void updateFlow()throws ElException;
	/**
	 * 根据标题获取新闻集合
	 * @param pageNow
	 * @param pageSize
	 * @param typeid
	 * @param subcon
	 * @param title
	 * @return
	 * @throws ElException
	 */
	public List<News> listNewsByTid_list(int pageNow, int pageSize, int typeid,
			boolean subcon, String title) throws ElException;
	/**
	 * 获取最新的新闻
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<News> listZxNews(int pageNow, int pageSize) throws ElException;
	/**
	 * 获取最新推荐的新闻
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<News> listZxNews(int pageNow, int pageSize,int hot) throws ElException;
	
	/**
	 * 显示更新时间最大的新闻
	 * @param pageNow
	 * @param pageSize
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<News> listNewsByTidNewTime(int pageNow, int pageSize, int typeid) throws ElException;
	public List<News> listNewsByTidNewTime(int pageNow, int pageSize, int typeid,int depid) throws ElException;
	/**
	 * 获取前10排行榜 
	 * @return
	 * @throws ElException
	 */
	public List<Department> getElclassDepPassing_phDeps(int classid ,int pageNow,int pageSize) throws ElException ; 
	public Poll getPoolMaxId() throws ElException;
	
	public void addUserIsPoll(int pollid,int userid) throws ElException;
}
