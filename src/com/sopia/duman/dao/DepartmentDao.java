package com.sopia.duman.dao;

import java.sql.Connection;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.SystemConf;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

public interface DepartmentDao {
/**
	 * 取得二级页面的所有部门
	 * @return
	 * @throws ElException
	 */
	public List<Department> getDepByIssp() throws ElException;
	/**
	 * 部门插入
	 * 
	 * @param department
	 * @throws ElException
	 */
	public void addDep(Department department) throws ElException;
	public int addDep1(Department department) throws ElException;

	public int updateDepUser(Department department) throws ElException;

	public int updateDep(Department department) throws ElException;

	/**
	 * 按id查询部门
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Department getDepById(int id) throws ElException;

	public Department getDepByBH(String bh) throws ElException;

	/**
	 * 部门更新
	 * 
	 * @param department
	 * @throws ElException
	 */
	public void alterDep(Department department) throws ElException;

	/**
	 * 部门删除
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void deleteDep(int id) throws ElException;

	public void deleteDepAndSub(int id) throws ElException;

	/**
	 * 设置上级部门
	 * 
	 * @param id
	 * @param parentid
	 * @throws ElException
	 */
	public void setParent(int id, int parentid) throws ElException;

	/**
	 * 得到id的直接下级部门
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
//	 public List<Department> listDepChild(int id)throws ElException;
	/**
	 * 部门树
	 * 
	 * @param pid
	 * @param stopid
	 * @param containStop
	 * @return
	 * @throws ElException
	 */
	public Department getDepTree(int pid, int stopid, boolean containStop)
			throws ElException;

	public Department getDepTree(int userid, String type, int stopid,
			boolean containStop) throws ElException;

	public Department getDepTree_level1(int pid, int stopid, boolean containStop)
			throws ElException;
	public List<Department> listdepChildsByPId(int parentid) throws Exception;

	public Department getDepTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException;
	public Department getDepTree_level_competence(int kledgeid,String tablename,int stopid,boolean containStop) throws ElException;

	public void alterSystemconf(SystemConf sc) throws ElException;

	public SystemConf getSystemConfByType(int type) throws ElException;

	public void addOpusers(String type, int userid, int depid)
			throws ElException;

	public void addCompetenceOpusers(String tablename,int type ,int kledgeid, int depid)
	throws ElException;
	
	public void deleteOpusers(String type, int userid, int depid)
			throws ElException;

	public boolean checkOpUsers(String type, int userid, int depid)
			throws ElException;

	public List<ELUser> getOpUsers(String type, int depid) throws ElException;

	// public Department getSecondDep(int depid)throws ElException;

	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException;
	/**
	 * 删除用户权限（知识管理）
	 * @param kledgeid
	 * @param tablename
	 * @throws ElException
	 */
	public void deleteCompetenceUserOpGrant(int kledgeid,String tablename) throws ElException;

	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserUseGrant(int userId) throws ElException;

	/**
	 * 根据部门名称模糊获取部门信息
	 * 
	 * @param depName
	 * @return
	 * @throws ElException
	 */
	public List<Department> listDepartmentsByName(String depName, int pageNow,
			int pageSize) throws ElException;

	/**
	 * 根据部门名称模糊获取部门信息数量
	 * 
	 * @param depName
	 * @return
	 * @throws ElException
	 */
	public int getDepartmentCount(String depName) throws ElException;

	/**
	 * 检测部门编号是否存在
	 * 
	 * @param bh
	 * @return
	 * @throws ElException
	 */
	public boolean checkDepBh(String bh) throws ElException;
	
	
	public int getDepId(String sjbh)throws ElException;
	
	public boolean checkDepName(String name) throws ElException;
	/**
	 * 根据id串获取name串
	 * @param ids
	 * @return
	 * @throws ElException
	 */
	public String getDepInId(String ids) throws ElException ;
	/**
	 * 删除部门(并入上级)
	 * @param depid
	 * @param depParentid
	 * @throws ElException
	 */
	public void deleteDep(int depid,int depParentid) throws ElException;
	/**
	 * 获取练习分配给的部门树
	 * @param userid
	 * @param type
	 * @param stopid
	 * @param containStop
	 * @return
	 * @throws ElException
	 */
	public Department getExampracDepTree(int pracid, int stopid,boolean containStop) throws ElException;
	/**
	 * 获取部门的左右id
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Department getDepLRid(int id) throws ElException;
	/**
	 * 假删除部门
	 * @param id
	 * @throws ElException
	 */
	public void deleteDepAndSubNot(int id) throws ElException;
	
	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserOpOrUseGrant(int userId,String type ,int depid) throws ElException;
	
	/**
	 * 没有节点，创建节点；有节点，获取节点
	 */
	public int checkDepForMonth(String month) throws ElException;
	
	/**
	 * 判断选择的是否是三级节点
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public boolean checkDepidIsThreeNode(int id) throws ElException;
	/**
	 * 根据用户查询用所在单位
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public Department getUnitByUserDepid(int depid) throws ElException ;
	/**
	 * 获取下级部门id 
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public String getByIdXiaJi(int depid) throws ElException ;
}
