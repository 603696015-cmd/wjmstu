package com.sopia.duman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.studyman.entities.MyCourse;

public interface StationDao {
	
	public Station getStTree_level1(int pid, int stopid, boolean containStop)
	throws ElException;

	public Station getStTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException;
	
	/**
	 * 取得二级页面的所有部门
	 * @return
	 * @throws ElException
	 */
	public List<Station> getStByIssp() throws ElException;
	
	/**
	 * 检测岗位编号是否存在
	 * 
	 * @param bh
	 * @return
	 * @throws ElException
	 */
	public boolean checkStBh(String bh) throws ElException;
	
	/**
	 * 岗位插入
	 * 
	 * @param department
	 * @throws ElException
	 */
	public void addSt(Station station) throws ElException;
	
	public List<Station> liststChildsByPId(int parentid) throws Exception;
	
	/**
	 * 按id查询岗位
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Station getStById(int id) throws ElException;
	public List<ELUser> getOpUsers(String type, int depid) throws ElException;
	
	/**
	 * 岗位更新
	 * 
	 * @param station
	 * @throws ElException
	 */
	public void alterSta(Station station) throws ElException;
	
	/**
	 * 删除岗位(并入上级)
	 * @param staid
	 * @param stationid
	 * @throws ElException
	 */
	public void deleteSta(int staid,int staParentid) throws ElException;
	
	/**
	 * 假删除岗位
	 * @param id
	 * @throws ElException
	 */
	public void deleteStaAndSubNot(int id) throws ElException;
	
	public void deleteOpusers(String type, int userid, int staid)
	throws ElException;
	
	public List<Course> getCourseList() throws ElException;
	
	//public int getCourseCount()throws ElException;
	
	/**
	 *将课程加入岗位，并分配给该岗位下的所有人员 
	 */
	
	public void addCourse(int courseid,int jieyeid,int staid,int classid)throws ElException;
	
	/**
	 * 岗位添加人员时，将该岗位下的所有课程分配给新加人员
	 */
	public void addCourse2(int courseid,int jieyeid,int userid,int classid)throws ElException;
	
	
	/**
	 * 获得该岗位下的所有人员
	 */
	public List<ELUser> listUser(int staid) throws ElException;
	/***
	 * 
	 * 将该岗位下课程所在的考场加入到该岗位下所有人员中
	 */
	public void addUserRoom(int examRoomid,int userid,int classid) throws ElException;
	
	/**
	 * 获得所有岗位
	 */
	
	public List<Station> getAllSta()throws ElException;
	
	/**
	 * 将课程加入岗位课程表
	 */
	public void addStationCourse(int staid,int courseid,int jieyeid,int classid)throws ElException;
	
	
	public List<Course> getCourseList(int staid)throws ElException;
	
	
	public int getClassid(int userid)throws ElException;
	
	/**
	 * 根据courseid获得所在的考场exam_room
	 */
	public ExamRoom getExamRoom(int courseid)throws ElException;
	
	/**
	 * 根据roomid得到试卷
	 */
	public List<ExamPaper> getAllExamPaper(int roomid)throws ElException;
	
	
	public int getStationCourse(int staid) throws ElException;
	
	
	public int getBiXiuCourse(int staid, int classid)throws ElException;
	
	public int getBiXiuScore(int staid,int classid)throws ElException;
	
	/**
	 * 岗位培训班
	 */
	
	public void updateSta(int staid,int classid)throws ElException;
	
	public int getClassid2(int staid)throws ElException;
	
	public Station getStationRoot()throws ElException;
	
	public Station getStTreeById(int id)throws ElException;
	
	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException;
	
	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserOpOrUseGrant(int userId,String type ,int depid) throws ElException;
	

	public void addOpusers(String type, int userid, int staid)
	throws ElException;
}
