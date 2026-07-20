package com.sopia.statman.dao;

import java.util.List;

import com.sopia.batchman.entities.Batch;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;

/**
 * Description: 培训班统计 数据处理 Copyright (c) Department of Research and
 * Development/wenyishun110@163.com. All Rights Reserved.
 * 
 * @version 1.0 2011-9-4 上午12:26:21 by 闻益舜（wenyishun110@163.com）创建
 */
public interface StatisticClassDao {
	public List<ElClass> listClassByDepid(int depid) throws ElException;

	public List<ElClass> listAllClass() throws ElException;

	public List<ELUser> listClassView(int depid, int classid, int pageNow,
			int pageSize) throws ElException;

	public int listClassViewSize(int depid, int classid) throws ElException;

	public List<ElClass> listClassByCreater(int userid) throws ElException;

	public List<ElClass> listClassByGlobal(int gid) throws ElException;

	public List<Department> listDepPassPer(int classid) throws ElException;

	public List<ElClass> listElclassStateByName(String name, int type,
			int pagenow, int pagesize) throws ElException;
	public List<ElClass> listElclassStateByName(String name,ElClType cltypeTree, int type,
			int pagenow, int pagesize) throws ElException;
	public List<ElClass> listElclassStateByName(String name,ElClType cltypeTree, int type) throws ElException;//EXCEL
//
//	public List<ElClass> listElclassStateByName(String name, ElClType cltypeTree,int[] types,
//			int pagenow, int pagesize) throws ElException;
	
	public List<ElClass> listElclassStateByName(String name, ElClType cltypeTree,int[] types) throws ElException;//EXCEL
	
	public int listElclassStateByNamesize(String name, int type)
			throws ElException;
	public int listElclassStateByNamesize(String name,ElClType cltypeTree, int type)
			throws ElException;
//	public int listElclassStateByNamesize(String name, ElClType cltypeTree, int[] types)
//	throws ElException;
	/**
	 * 培训班统计（获取培训班列表）
	 * @param name
	 * @param cltypeTree
	 * @param pagenow
	 * @param pagesize
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> listElclassStateByName(String name, ElClType cltypeTree,
			int pagenow, int pagesize) throws ElException;
	
	
	
	public List<ElClass> listElclassStateByName22(String name, Station cltypeTree,
			int pagenow, int pagesize) throws ElException;
	/**
	 * 培训班统计（获取培训班列表数量）
	 * @param name
	 * @param cltypeTree
	 * @return
	 * @throws ElException
	 */
	public int listElclassStateByNamesize(String name,ElClType cltypeTree) throws ElException;
	
	
	
	public int listElclassStateByNamesize22(String name,Station cltypeTree) throws ElException;
	/**获取培训班批次列表
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Batch > listBatchs(int userid ,int pageNow,int pageSize)throws ElException;
	
	/**培训班批次数量
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int listBatchssize(int userid)throws ElException;
	/**
	 * 培训班统计查询（学分排序）
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> classStudent(String classid,ElNode tree,ELUser elUser,int pageNow, int pageSize) throws ElException;
	/**
	 * 培训班统计查询学员数量
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int classStudentSize(String classid,ElNode tree,ELUser elUser) throws ElException;
	/**
	 * 培训班统计查询学员（学分排序）（不分页、用于导出）
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> classStudent(String classids,ElNode tree,ELUser elUser) throws ElException;
	/**获取批次中的培训班标识符集
	 * @param batchid
	 * @return
	 * @throws ElException
	 */
	public String batchclassids(int batchid) throws ElException;
	public List<ElClass> listElclassStateByName(String name, ElClType cltypeTree,int[] types,
			int pagenow, int pagesize) throws ElException;
	public int listElclassStateByNamesize(String name, ElClType cltypeTree, int[] types)
	throws ElException;
}
