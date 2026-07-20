package com.sopia.forumman.dao;

import java.util.List;

import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.shopping.entities.ShoppingCart;

public interface ForumCourseDao {
	
	
	public List<Course> listAllCourseFromThis(CourseType ctypeTree, int depid,
			int role, Course course, int ctid, int pageNow, int pageSize,
			String status, String sqlw) throws ElException ;
	
	
	public int listAllCourseSizeFromThis(CourseType ctypeTree, int depid,
			int role, Course course, int ctid, String status, String sqlw)
			throws ElException ;
	public List<Course> listAllCourseFromThishuiyuanzhongxin(CourseType ctypeTree, int depid,
			int role, Course course, int ctid, int pageNow, int pageSize,
			String status, String sqlw) throws ElException ;
	public int listAllCourseFromThissizehuiyuanzhongxin(CourseType ctypeTree, int depid,
			int role, Course course, int ctid, String status, String sqlw)
			throws ElException ;
	public List<ElClass> getApplyForeElclass(ElClType tree, int cltid,
			ElClass elClass, int role, String sqlw, int pageNow, int pageSize)
			throws ElException ;
	
	/**
	 * 会员服务系统的培训班页
	 * @param tree
	 * @param depid
	 * @param cltid
	 * @param elClass
	 * @param role
	 * @param sqlw
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getApplyForeElclasshuiyuanfuwu(ElClType tree,int depid,int cltid,
			ElClass elClass, int role, String sqlw, int pageNow, int pageSize)
			throws ElException ;
	
	public int getApplyForeElclasssizehuiyuanfuwu(ElClType tree,int depid, int cltid,
			ElClass elClass, int role, String sqlw)
			throws ElException ;
	public int getApplyForeElclasssize(ElClType tree, int cltid,
			ElClass elClass, int role, String sqlw)
			throws ElException;
	/**
	 * 获得课程目录的二级跟节点
	 * @return
	 * @throws ElException
	 */
	public List<CourseType> getcourseerjijiedian() throws ElException;
	/**
	 * 获得培训班目录的二级跟节点
	 * @return
	 * @throws ElException
	 */
	public List<ElClType> getclasserjijiedian() throws ElException;
	
	public List<Course> listAllCourseFromThis( int pageNow, int pageSize
	) throws ElException ;
}
