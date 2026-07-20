package com.sopia.shebeipinggu.dao;

import java.util.List;

import com.sopia.common.ElException; 
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.entities.Policy;
import com.sopia.pfms.entities.PolicyLib;
import com.sopia.pfms.entities.ProductType;

public interface PG_PolicyDao {

	public PolicyLib getPolicyLibTree(int from, int stop, boolean constop) throws ElException;
	public PolicyLib getPolicyLibTree(int userid, String op, int stopid,boolean containStop) throws ElException ;
	public PolicyLib getPtypeByid(int id) throws ElException;
	public List<ELUser> getOpUsers(int typeid)throws ElException;
	public void addPolicyLib(PolicyLib ptype) throws ElException; 
	public void alterpolicylib(PolicyLib ptype) throws ElException;
	public void deletePolicylibAndSub(int id) throws ElException;
	 
	public void deletePtype(int id) throws ElException ;
	public void updatePolicyTypeParentid(int pid, int npid) throws ElException ; 
	public void updatePolicyParentid(int pid, int npid) throws ElException;

	
	
	/**
	 * 增加保单信息
	 * @param policy
	 * @return
	 * @throws ElException
	 */
	public int addPolicy(Policy policy) throws ElException;
	/**
	 * 修改保单信息
	 * @param policy
	 * @return
	 * @throws ElException
	 */
	public int alterPolicy(Policy policy) throws ElException;
	/**
	 * 查询保单列表信息
	 * @param policy
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Policy> getPolicyList(Policy policy,int nid,ProductType ptypeTree,boolean isCreate, int pageNow, int pageSize) throws ElException;
	/**
	 * 查询保单列表数量
	 * @param policy
	 * @return
	 * @throws ElException
	 */
	public int getPolicyListSize(Policy policy,int nid,ProductType ptypeTree,boolean isCreate) throws ElException;
	/**
	 * 修改保单状态
	 * @param policyId
	 * @param valid
	 * @throws ElException
	 */
	public void alterPolicyValid(int policyId , int valid) throws ElException ;
	public Policy getPolicyById(int id) throws ElException;
	/**
	 * 更新扫描件
	 * @param id
	 * @param scanning
	 * @throws ElException
	 */
	public void updateScanning(int id, String scanning) throws ElException;
}
