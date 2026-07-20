package com.sopia.courseman.dao;

import java.util.List;

import org.adl.parsers.dom.ADLItem;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.questionman.tags.PracticePaper;

public interface CoursePageDao {
	public void addCoursePage(CoursePage cp) throws ElException;

	public int maxSortIdInCp(int courseid) throws ElException;

	public List<CoursePage> listCps(int courseid) throws ElException;

	public List<PracticePaper> listPps(int courseid) throws ElException;

	/**
	 * 根据id获取课程章节
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public CoursePage getCp(int id) throws ElException;

	public void alterCp(CoursePage cp) throws ElException;

//	public int getFirstCpId(int courseid) throws ElException;
	public int getFirstCpId(int courseid ) throws ElException;

	public void sortCps(int courseid, int sortid, int upordown)
			throws ElException;
	public void sortRoom(int roomid, int sortid, int upordown,int courseid,int cpid)
		throws ElException;

	public void deleteCp(int cpid) throws ElException;
	
	public int getCDuringAScpage(int courseid)throws ElException;
	/**
	 * 添加课程章节
	 * @param cp
	 * @throws ElException
	 */
	public void addCoursePage2(CoursePage cp) throws ElException;
	/**
	 * 修改课程章节
	 * @param cp
	 * @throws ElException
	 */
	public void alterCp2(CoursePage cp) throws ElException;
	
	/**
	 * Description: scorm 课程的items添加 
	* @Version1.0 2012-7-21 下午02:32:09 by 闻益舜（wenyishun110@163.com）创建
	 * @param cp
	 * @throws ElException
	 */
//	public void addSCItem(CourseItem ci) throws ElException;
	
	/**
	 * 判断章节是否可学习
	 */
	public boolean checkPageCanlearn(int sortid,int courseid,int userid,int cpid) throws ElException;
	
	/**
	 * 获取当前章节的前一章节信息
	 * @param courseid
	 * @param sortid
	 * @throws ElException
	 */
	public CoursePage getBeginCPage(int courseid,int sortid) throws ElException;
	
	/**
	 * 添加附件
	 */
	public void addStuff(String addr,String title,int id)throws ElException;
	/**
	 * 获得附件
	 */
	public List<StuffLib> getStuffs(int cpageid)throws ElException;
	/**
	 * 修改附件
	 */
	public void alterStuff(int id,String stuffaddr,String title) throws ElException;
	/**
	 * 删除附件
	 */
	public void deleteStuffByid(int id)throws ElException;
}
