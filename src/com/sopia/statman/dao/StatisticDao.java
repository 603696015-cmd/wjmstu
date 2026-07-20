package com.sopia.statman.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.assistman.entities.Offline;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.statman.entities.MyClass;
import com.sopia.statman.entities.Resources;

public interface StatisticDao {
	public List<Department> listDepinfo(int depid) throws ElException;

	public Department getDepinfo(int depid) throws ElException;

	public List<ELUser> getDepUserCredit(int depid, int pageNow, int pageSize)
			throws ElException;

	public int getDepUserCreditSize(int depid) throws ElException;

//	public List<ELUser> getStatUserByDep(int depid, int subdep, ELUser eu,
//			int pageNow, int pageSize) throws ElException;

//	public int getStatUserByDepCount(int depid, int subdep, ELUser eu)
//			throws ElException;

	public List<ELUser> getStatTalentByDep(int depid, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException;
	public List<ELUser> getStatTalentByDep(int depid, int subdep, ELUser eu,int elclassid) throws ElException;
	
	public List<ELUser> getStatTalentByDep(int depid, int subdep, ELUser eu,int elclassid,
			int pageNow, int pageSize) throws ElException;

	public int getStatTalentByDepSize(int depid, int subdep, ELUser eu)
			throws ElException;
	
	public int getStatTalentByDepSize(int depid, int subdep, ELUser eu,int elclassid)
	throws ElException;

	public void setStatUser(ELUser elUser) throws ElException;

	public List<ELUser> getStatTalentByDep(int depid, int subdep, ELUser eu)
			throws ElException;

	public List<ELUser> getStatUcreditUserByDep(int depid, int subdep,
			ELUser eu, int pageNow, int pageSize) throws ElException;

	public int getStatUcreditUserByDepCount(int depid, int subdep, ELUser eu)
			throws ElException;

	public List<Offline> listStatOfflines(String name, Timestamp begintime,
			Timestamp endtime, int pageB, int pageE) throws ElException;

	public int listStatOfflinesSize(String name, Timestamp begintime,
			Timestamp endtime) throws ElException;

	public List<ELUser> listStatOffline2Users(int offid) throws ElException;

	public Offline getStatOffline(int id) throws ElException;
	
	//资源统计
//	public Resources getResourceStatistic()throws ElException;
	/**
	 * 学习统计的查询
	 * @param tree
	 * @param sublibs
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getStatUserByDep(ElNode tree, int sublibs, ELUser eu,
			int pageNow, int pageSize) throws ElException;
	/**
	 * 学习统计的查询(数量)
	 * @param tree
	 * @param sublibs
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getStatUserByDepCount(ElNode tree, int sublibs, ELUser eu) throws ElException;
	
	
}
