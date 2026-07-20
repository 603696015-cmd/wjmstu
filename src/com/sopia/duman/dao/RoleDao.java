package com.sopia.duman.dao;

import java.sql.Connection;
import java.util.List;


import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.ElGroup;
import com.sopia.duman.entities.ElRole;

public interface RoleDao {
	public List<ElRole> listRoles() throws ElException;

//	public List<ElRole> listRoles(int unequalTo) throws ElException;//unequalTo != 角色id

	public ElRole getRoleById(int id) throws ElException;

	public int addRole(ElRole role) throws ElException;

	public void alterRole(ElRole role) throws ElException;

	public void deleteRole(int id) throws ElException;

	public void addRoleFunc(ElRole role) throws ElException;

	public void addUserRoleFunc(ElRole role,int userid) throws ElException;  
	
	public void addFunc(ElFunc f) throws ElException;

	public List<ElFunc> getFuncsByRid(int rid) throws ElException;

	public void deleteFunc(int id) throws ElException;

	public ElFunc getFuncById(int id) throws ElException;

	public void alterFunc(ElFunc f) throws ElException;

	public ElFunc getFuncTree() throws ElException;
	
	public ElFunc getFuncTree1() throws ElException;
	

	public ElFunc getMenu(String funccode, int role,int userid) throws ElException;

	public List<ElFunc> getMenus(int parentid, int role,int userid) throws ElException;
	
	public List<ElFunc> getMenus_newversion(int parentid, int role,int userid) throws ElException;

	public List<ElGroup> listGroups() throws ElException;

	public ElGroup getGroupById(int id) throws ElException;

	public List<ELUser> listAssignUsers(int gid, int pageNow, int pageSize)
			throws ElException;

	public int listAssignUsersSize(int gid) throws ElException;

	public boolean checkUserIngroup(int userid, int groupid) throws ElException;

	public void groupAssign2User(int userid, int groupid) throws ElException;

	public void groupUnAssign2User(int userid, int groupid) throws ElException;

	public void addGroup(ElGroup group) throws ElException;

	public void deleteGroup(int id) throws ElException;

	public void alterGroup(ElGroup group) throws ElException;
	
	public ElRole getRoleByName(String name) throws ElException;

	public List<ElGroup> listGroupsBytype(int type) throws ElException;
	
	/**给单个用户分配功能权限。
	 * @param userid
	 * @param funccode
	 * @param role
	 * @throws ElException
	 */
	public void setUserfunc(int userid ,String funccode,int role ) throws ElException;
	
	public void checkUserfunc(int userid, String funccode,String table)throws ElException;
	/**判断角色是否有某权限
	 * @param role
	 * @param funccode
	 * @throws ElException
	 */
	public boolean checkRolefunc(int role, String funccode)throws ElException;
	/**
	 * 获取功能权限树(加角色限制)
	 * @param roleId
	 * @return
	 * @throws ElException
	 */
	public ElFunc getFuncTreeByRoleId(int roleId) throws ElException;
	/**
	 * 添加角色
	 * @param role
	 * @return
	 * @throws ElException
	 */
//	public int addRole2(ElRole role) throws ElException ;
	
	public List<String> listFuncs() throws ElException;
	
	public List<Integer> listFuncs_id() throws ElException ;
	/**
	 * 获取我创建的所有角色
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<ElRole> listMyRoles(int userid) throws ElException;
	/**
	 * 根据角色和创建者获取角色列表
	 * @param roleid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<ElRole> listRoles(int roleid,int userid) throws ElException;
	public List<ElFunc> getMenus(int parentid, int role, int userid, boolean sub)
	throws ElException ;
	/**
	 * 获取功能权限树(加userid限制)
	 * 
	 * @param roleId
	 * @return
	 * @throws ElException
	 */
	public ElFunc getFuncTreeByUserid(int userid) throws ElException;
	
	/**
	 * 判断是否是二级菜单
	 * 2是2级菜单、3是三级菜单
	 * @param id
	 * @return
	 * @throws ELException
	 */
	public int checkFuncIsTwoOrThree(int gerenzhongxinid,int id) throws ElException;
}
