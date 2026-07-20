package com.sopia.statman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;

public interface StatisticCourseDao {
	public List<Course> listCourseByDepid(int depid) throws ElException;

	public List<Course> listCourseBYCtype(int ctid, String name)
			throws ElException;
	/**
	 * 课程统计分页
	 * @author jiahaijiang
	 * @param ctid
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseBYCtypePage(CourseType ctypeTree,int ctid, String name,int pageNow, int pageSize)
	throws ElException;
//	public List<Course> listCourseBYCtypePage(CourseType ctypeTree,int[] ctids, String name,int pageNow, int pageSize)
//	throws ElException;
	
	/**
	 * EXCEL导出源
	 * @param ctypeTree
	 * @param ctids
	 * @param name 
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseBYCtypePage(CourseType ctypeTree,int[] ctids, String name)throws ElException;
	
	/**
	 * 课程统计分页
	 * @author jiahaijiang
	 * @param ctid
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public int listCourseBYCtypePageCount(CourseType ctypeTree,int ctid, String name,int pageNow, int pageSize)throws ElException ;
	public int listCourseBYCtypePageCount(CourseType ctypeTree,int[] ctids, String name,int pageNow, int pageSize)throws ElException ;

	public List<MyCourse> course_user_list(int cid) throws ElException;
	
	public List<MyCourse> course_user_list_BYCtypePage(CourseType ctypeTree,int ctid,String name, int cid,int pageNow, int pageSize) throws ElException;//hwc
	
	public List<MyCourse> course_user_list_BYCtypePage(CourseType ctypeTree,int ctid,String name, int cid) throws ElException;//hdl

	
	public int course_user_list_BYCtypeCount(CourseType ctypeTree,int ctid,String name, int cid) throws ElException;//hwc
 
	public List<Course> listCourseByCreater(int userid) throws ElException;

	public List<MyCourse> listMyCourse(int userid) throws ElException;

//	public List<ELUser> getStatCtimeUserByDep(int depid, int subdep, ELUser eu,
//			int pageNow, int pageSize) throws ElException;

//	public int getStatCtimeUserByDepCount(int depid, int subdep, ELUser eu)
//			throws ElException;

	public List<ELUser> getStatCnoteUserByDep(int depid, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException;

	public int getStatCnoteUserByDepCount(int depid, int subdep, ELUser eu)
			throws ElException;

	public List<ELUser> getStatCtimeUserByDep(int depid, int subdep, ELUser eu)
			throws ElException;

	/**
	 * 获取课程中所有学员
	 * @param ctypeTree
	 * @param ctid
	 * @param name
	 * @param cid
	 * @param classid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> course_user_list_BYCtypePage(CourseType ctypeTree,
			int ctid, String name, int cid,int classid, int pageNow, int pageSize,String roomTitle)
			throws ElException;
	/**
	 * 获取课程中学员数量
	 * @param ctypeTree
	 * @param ctid
	 * @param name
	 * @param cid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int course_user_list_BYCtypeCount(CourseType ctypeTree, int ctid,
			String name, int cid,int classid) throws ElException;
	/**
	 * 获取课程中所有学员(未分页)
	 * @param ctypeTree
	 * @param ctid
	 * @param name
	 * @param cid
	 * @param classid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> course_user_list_BYCtypePageCount(CourseType ctypeTree,
			int ctid, String name, int cid,int classid,String roomTitle)
			throws ElException ;
	/**
	 * 学时统计用户查询
	 * @param tree
	 * @param sublibs
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getStatCtimeUserByDep(ElNode tree, int sublibs, ELUser eu,
			int pageNow, int pageSize) throws ElException;
	/**
	 * 学时统计的查询(数量)
	 * @param tree
	 * @param sublibs
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getStatCtimeUserByDepCount(ElNode tree, int sublibs, ELUser eu) throws ElException;
	/**
	 * 课程统计列表查询
	 * @param tree
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseBYCtypePage(ElNode tree, String name, int pageNow, int pageSize)
	throws ElException;
	/**
	 * 课程统计列表查询数量
	 * @param tree
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public int listCourseBYCtypePageSize(ElNode tree, String name) throws ElException;
	/**
	 * 课程统计列表查询（未分页，用于导出）
	 * @param tree
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseBYCtypePage(ElNode tree, String name) throws ElException;
	/**
	 * 统计学员培训班学习和章节练习轨迹
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyCPage> statisticStudyLearnLocus(int userid,int classid,int courseid,int pageNow, int pageSize)throws ElException;
	/**
	 * 统计学员培训班学习和章节练习轨迹数据数量
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int statisticStudyLearnLocusSize(int userid,int classid,int courseid)throws ElException;
	
	
	/**
	 * 根据userid得到roomid
	 */
	public List<Integer> getroomid(int userid)throws ElException;
}
