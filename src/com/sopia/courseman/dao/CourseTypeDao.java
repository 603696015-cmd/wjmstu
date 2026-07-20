package com.sopia.courseman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.duman.entities.ELUser;

public interface CourseTypeDao {
	/**
	 * 所有课程类别列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public List<CourseType> getCtypeChilds(int parentid) throws ElException;

	/**
	 * 得到指定id的课程类别
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public CourseType getCtypeById(int id) throws ElException;

	/**
	 * 修改课程类别
	 * 
	 * @param ctype
	 * @throws ElException
	 */
	public void alterCtype(CourseType ctype) throws ElException;

	/**
	 * 添加课程类别
	 * 
	 * @param ctype
	 * @throws ElException
	 */
	public int addCtype(CourseType ctype) throws ElException;

	/**
	 * 删除课程类别
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void deleteCtype(int id) throws ElException;

	public void deleteCtypeAndSub(int id) throws ElException;

	public CourseType getCtypeRoot() throws ElException;

	public CourseType getCtypeTree(int from, int stop, boolean containStop)
			throws ElException;

	public List<ELUser> listUserByCtype(int ctypeid) throws ElException;

	public void addCtypeUser(int userid, int ctypeid) throws ElException;

	public boolean checkCtypeUser(int userid, int ctypeid) throws ElException;

	public void deleteCtypeUser(int userid, int ctypeid) throws ElException;

	public List<CourseType> listCtypeByUser(int userid) throws ElException;

	/**
	 * 构建课程类型树，树的查找分为有权限的和共享节点的。
	 * 
	 * @author jiahaijiang
	 * @param from
	 * @param stop
	 * @param containStop
	 * @param isShared
	 *            是否需要加载共享节点，true需要，false不需要。
	 * @param permtype
	 *            管理权限和使用权限 这些就直接传表名方便处理
	 * @return
	 * @throws ElException
	 */
	public CourseType getCtypeTreeByPerOrShar(int from, int stop,
			boolean containStop, String userid, boolean isShared,
			String permtype) throws ElException;

	/**
	 * 获取管理或使用权限的用户
	 * 
	 * @author jiahaijiang
	 * @param permtype
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getOpUsers(String permtype, int typeid)
			throws ElException;

	/**
	 * 删除管理或使用权限的用户
	 * 
	 * @author jiahaijiang
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @throws ElException
	 */
	public void deleteOpusers(String type, int userid, int ctypeid)
			throws ElException;

	/**
	 * 判断权限是否存在
	 * 
	 * @author jiahaijiang
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @return
	 * @throws ElException
	 */
	public boolean checkOpUsers(String type, int userid, int ctypeid)
			throws ElException;

	/**
	 * 添加课程类型权限
	 * 
	 * @author jiahaijiang
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @throws ElException
	 */
	public void addOpusers(String type, int userid, int ctypeid)
			throws ElException;

	public CourseType getCourseLibTree(int from, int stop, boolean containStop)
			throws ElException;

	public CourseType getCourseLibTree(int userid, String op, int stop,
			boolean containStop) throws ElException;
	
	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserUseGrant(int userId) throws ElException;
	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException;
	/**
	 * 假删除课程类别
	 * @param id
	 * @throws ElException
	 */
	public void deleteCtypeAndSubNot(int id) throws ElException;
	
}
