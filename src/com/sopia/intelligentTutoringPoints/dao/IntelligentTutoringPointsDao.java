package com.sopia.intelligentTutoringPoints.dao;

import java.util.List;

import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.intelligentTutoringPoints.entities.IntelligentAcademic;
import com.sopia.intelligentTutoringPoints.entities.IntelligentAcademicCourse;
import com.sopia.intelligentTutoringPoints.entities.IntelligentClass;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLearnWeek;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLogin;
import com.sopia.intelligentTutoringPoints.entities.IntelligentProportion;
import com.sopia.intelligentTutoringPoints.entities.IntelligentRecoding;
import com.sopia.intelligentTutoringPoints.entities.IntelligentTutoringPoints;

public interface IntelligentTutoringPointsDao {
	/**
	 * 获取用户当前等级智能辅导分
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public float getPoints(int userid,int classid) throws ElException;
	/**
	 * 获取用户各项智能辅导分
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public IntelligentTutoringPoints getDifferentPoints(int userid,int classid) throws ElException;
	/**
	 * 智能辅导分用户列表
	 * @param searchDep
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> intelligentUsers(Department searchDep,ELUser elUser,int pageNow,int pageSize) throws ElException;
	/**
	 * 智能辅导分用户数量
	 * @param searchDep
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int intelligentCount(Department searchDep,ELUser elUser,int pageNow,int pageSize) throws ElException;
	
	/**
	 * 登录详情
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<IntelligentLogin> getLoginInfos(int userid,int classid,int pageNow,int pageSize) throws ElException;
	/**
	 * 登录详情COUNT
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getLoginInfosCount(int userid,int classid) throws ElException;
	
	/**
	 * 周学习时间详情
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<IntelligentLearnWeek> getWeekInfos(int userid,int classid,int pageNow,int pageSize) throws ElException;
	/**
	 * 周学习时间详情COUNT
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getWeekInfosCount(int userid,int classid) throws ElException;
	
	/**
	 * 登录天数
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getLoginInfosDays(int userid,int classid) throws ElException;
	
	/**
	 * 获取等级学习时长HOUR
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public float getClassInfoHour(int userid,int classid) throws ElException;
	
	/**
	 * 复听智能辅导分
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public IntelligentProportion getProportion(int userid,int classid) throws ElException;
	/**
	 * 录音智能辅导分
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public IntelligentRecoding getRecoding(int userid,int classid) throws ElException;
	
	/**
	 * 模块考试详情
	 * @param userid
	 * @param classid
	 * @param pageNow
	 * @param pageSize
	 * @param course
	 * @param coursePage
	 * @return
	 * @throws ElException
	 */
	public List<IntelligentAcademic> getAcademicInfos(int userid,int classid,int pageNow,int pageSize,Course course,CoursePage coursePage) throws ElException;
	/**
	 * 模块详情COUNT
	 * @param userid
	 * @param classid
	 * @param course
	 * @param coursePage
	 * @return
	 * @throws ElException
	 */
	public int getAcademicInfosCount(int userid,int classid,Course course,CoursePage coursePage) throws ElException;
	/**
	 * 单元考试详情
	 * @param userid
	 * @param classid
	 * @param pageNow
	 * @param pageSize
	 * @param course
	 * @param coursePage
	 * @return
	 * @throws ElException
	 */
	public List<IntelligentAcademicCourse> getAcademicCourseInfos(int userid,int classid,int pageNow,int pageSize,Course course,CoursePage coursePage) throws ElException;
	/**
	 * 单元考试详情COUNT
	 * @param userid
	 * @param classid
	 * @param course
	 * @param coursePage
	 * @return
	 * @throws ElException
	 */
	public int getAcademicCourseInfosCount(int userid,int classid,Course course,CoursePage coursePage) throws ElException;
	
	/**
	 * 返回当前传入得classid的name
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public String getElClssName(int classid) throws ElException;
	
	/**
	 * 获取用户各项智能辅导分(将等级合为一个查看)
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public IntelligentTutoringPoints getDifferentPoints_new(int userid,int classid,String classname) throws ElException;
	
	/**
	 * 获得ab两个等级培训班
	 * @param classname
	 * @return
	 * @throws ElException
	 */
	public List<String> getElClssList(String classname) throws ElException;
}
