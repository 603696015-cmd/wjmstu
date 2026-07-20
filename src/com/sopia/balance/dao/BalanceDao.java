package com.sopia.balance.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.balance.entites.Balance;
import com.sopia.balance.entites.Income;
import com.sopia.balance.entites.RechargeInfo;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.CourseType;
import com.sopia.duman.entities.ELUser;
import com.sopia.shopping.entities.Shopping;

public interface  BalanceDao {
	
	public List<Balance> getUserlistBalance(ElNode tree,ELUser eluser,int sublibs, int pN,int pS) throws ElException;
	public int getUserlistBalanceSize(ElNode tree,ELUser eluser,int sublibs) throws ElException;
	/**
	 * 手动增资
	 * @param balanceValue
	 * @param username
	 * @param auserid
	 * @throws ElException
	 */
	public void addbalance(Float balanceValue,int username,int auserid)throws ElException ;
	/**
	 * 得到增资记录
	 * @param username
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public List<RechargeInfo>  getUserRechargeInfoById(int username,int type) throws ElException;
	
	public List<RechargeInfo> getUserRechargeInfoById(ElNode tree,ELUser eluser,int sublibs,String caozuoname,String caozuousername, int type, int pN,int pS)throws ElException; 
	
	public int getUserRechargeInfoById(ElNode tree, ELUser elUser,
			int sublibs,String caozuoname,String caozuousername,int type) throws ElException ;
	/**
	 * 余额转移先减自己的资金
	 * @param userid
	 * @param username
	 * @param balance
	 * @throws ElException 
	 */
	public void subBalance(int userid ,int username,float balance) throws ElException;
	/**
	 * 通过ID获得该用户的余额信息
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public float getmybalance(int userid) throws ElException;
	/**
	 * 查询转移金额是否大于用户余额
	 * @param userid
	 * @param balance
	 * @return
	 * @throws ElException
	 */
	public boolean checkbalance(int userid,float balance) throws ElException;
	/**
	 * 余额转移增资
	 * @param balanceValue
	 * @param username
	 * @param auserid
	 * @throws ElException
	 */
	
	public void subaddbalance(Float balanceValue,int username ,int auserid)throws ElException;
	/**
	 * 查询用户的余额信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Balance  getbalancebyid(int id) throws ElException;
	/**
	 * 查询用户的收支列表
	 * @param userid
	 * @param pN
	 * @param pS
	 * @return
	 * @throws ElException
	 */	
	public List<Income> myIncomebyuserid(int userid, int pN, int pS) throws ElException;
	public int myIncomebyuseridsize(int userid) throws ElException;
	/**
	 * 查询收支记录详细信息 失败
	 * @param id  ： 记录ID
	 * @return
	 * @throws ElException
	 */
	public RechargeInfo getRechargeInfoById(int id) throws ElException ;
	
	/**
	 * 查询部门总消费记录概况
	 * @param tree
	 * @param sublibs
	 * @return
	 * @throws ElException
	 */
	public Shopping getdempshopping(ElNode tree, 
			int sublibs,ELUser elUser,Timestamp start,Timestamp end) throws ElException;
	/**
	 * 查询部门消费记录明细
	 * @param tree
	 * @param sublibs
	 * @param pN
	 * @param pS
	 * @return
	 * @throws ElException
	 */
	public List<Shopping> getdempshopping(ElNode tree, 
			int sublibs,ELUser elUser,Timestamp start,Timestamp end, int pN, int pS) throws ElException;
	public int getdempshoppingSize(ElNode tree, 
			int sublibs,ELUser elUser,Timestamp start,Timestamp end) throws ElException;
	
	
	
	public void updatemybalance(int userid,float balance) throws ElException;
}
