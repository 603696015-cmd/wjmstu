package com.sopia.peice.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.CourseType;
import com.sopia.peice.entities.Peice;

public interface PeiceDao {
	/**
	 * 获取当前用户所创建的所有课程价格信息列表
	 * @return
	 */
	public List<Peice> getMyAll(CourseType ctypeTree ,int type , String name, String status,
			String userid,int dprice ,int role, int pageNow,int pageSize) throws ElException ;
	/**
	 *  获取当前用户所创建的所有课程价格信息列表Size
	 * @param ctypeTree
	 * @param typeid
	 * @param name
	 * @param status
	 * @param userid
	 * @param dprice
	 * @param role
	 * @return
	 * @throws ElException
	 */
	public int getMyAllSize(CourseType ctypeTree ,int typeid , String name, String status,
			String userid,int dprice ,int role) throws ElException ;
	/**
	 * 获取当前所有用户的非制作中课程
	 * @throws ElException
	 */
	
	public List<Peice> peice_AuditList(CourseType ctypeTree ,int typeid ,String name, String status,
			 int dprice, int role,int pageNow, int pageSize) throws ElException;
	/**
	 * 获取所有课程价格信息列表Size
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @param dprice
	 * @return
	 * @throws ElException
	 */
	public int peice_AuditListSize(CourseType ctypeTree, int  typeid,
			String name, String status, int dprice, int role)
			throws ElException;
	/**
	 * 修改课程价格信息
	 * @param peicevale:要修改的价格数值
	 * @param courseid：课程id
	 * @param courseid：要修改的哪种价格 
	 * @throws ElException
	 */
	public void peice_change(float peicevale,int courseid, int peicetype)throws ElException;
	
	/**
	 * 审核课程价格
	 * @param courseid：被审核课程
	 * @param userid：审核人ID
	 * @throws ElException
	 */
	public void peice_audit(int courseid,int userid,int setstatus) throws ElException;
	/**
	 * 提交审核
	 * @param courseid
	 * @throws ElException
	 */
	public void peice_Submit(int courseid)throws ElException;
	
}
