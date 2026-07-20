package com.sopia.classman.dao;

import java.sql.Connection;
import java.util.List;

import com.sopia.classman.entities.ElClType;
import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;

public interface ElClTypeDao {
	public int addCltype(ElClType elcltype) throws ElException;

	public void alterCltype(ElClType elcltype) throws ElException;

	public ElClType getCltypeTree(int from, int stop, boolean containStop/*,String status*/)
			throws ElException;
	
	public ElClType getClTypeById(int id)throws ElException;
	
	public ElClType getCltypeRoot() throws ElException;
	
	public void deleteCltype(ElClType elcltype)throws ElException;

	/**
	 * 构建培训班类型树，树的查找分为有权限的和共享节点的。
	 * @author luocw
	 * @param from
	 * @param stop
	 * @param containStop
	 * @param isShared 是否需要加载共享节点，true需要，false不需要。
	 * @param permtype 管理权限和使用权限 这些就直接传表名方便处理
	 * @return
	 * @throws ElException
	 */
	public ElClType getCltypeTreeByPerOrShar(int from, int stop, boolean containStop,int userid,boolean isShared,String permtype) throws ElException ;

	/**
	 * 获取管理或使用权限的用户
	 * @param permtype
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getOpUsers(String permtype, int typeid) throws ElException;
	
	/**
	 * 判断权限是否存在
	 * @author luocw
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @return
	 * @throws ElException
	 */
	public boolean checkOpUsers(String type, int userid, int ctypeid)throws ElException;
	
	/**
	 * 添加培训班类型权限
	 * @author luocw
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @throws ElException
	 */
	public void addOpusers(String type, int userid, int ctypeid)throws ElException;

	/**
	 * 删除培训类型可管理可维护人员
	 * @param optype
	 * @param userId
	 * @param cltypeId
	 */
	public void deleteOpusers(String optype, int userId, int cltypeId) throws ElException;
	
	public ElClType getClassLibTree(int userid, String op, int stopid,	boolean containStop) throws ElException;
	 
	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException;
	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserUseGrant(int userId) throws ElException;
	/**
	 * 设置上级类别
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void setCtypeparent(int pid, int npid) throws ElException;
	/**
	 * 设置上级类别
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void setClassparent(int pid, int npid) throws ElException;
	/**
	 * 删除培训班库
	 */
	public void deleteCtypeAndSub(int id) throws ElException;
	/**
	 * 更新培训班类别状态
	 * @param cltypeid
	 * @throws ElException
	 */
	public void deleteCltypeNot(int cltypeid) throws ElException;
	/**
	 * 假删除培训班库
	 * @param id
	 * @throws ElException
	 */
	public void deleteCtypeAndSubNot(int id) throws ElException;
}
