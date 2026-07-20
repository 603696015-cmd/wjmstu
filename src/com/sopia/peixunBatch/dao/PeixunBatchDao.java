package com.sopia.peixunBatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.ELUser;
import com.sopia.peixunBatch.entities.PeixunBatch;
import com.sopia.statman.entities.MyClass;

public interface PeixunBatchDao {
	
	public void save_batch(PeixunBatch peixunBatch,int userid) throws ElException;
	
	public List<PeixunBatch> getBatchList(String name, int pageNow, int pageSize) throws ElException;
	
	public List<BaseDataType> getBaseDataType() throws ElException;
	
	/**
	 * 根据类别查询数据(分页)
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatat> getBaseDatatByTypeid2() throws ElException ;

	/**
	 * 获取培训批次大小
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public int getBatchListSize(int userid) throws ElException;
	
	public List<PeixunBatch> getBatchElClssList(int id) throws ElException;
	
	public List<ELUser> getBatchElUserList(int id) throws ElException;
	
	public void delete_batch(int id) throws ElException;
	
//	public PeixunBatch getList(int id) throws ElException;
	public void addBatchClass(int batchid,int classid) throws ElException;
	public int maxSortIdInBe(int elclassid) throws ElException ;
	public int listcombinationSearchClassCount(ElClass elClass,
			ElClType cltypeTree, int pageNow, int pageSize, int peixunBatchId) throws ElException;
	
	public List<ElClass> listcombinationSearchClass(ElClass elClass,
			ElClType cltypeTree, String sqlw, int pageNow, int pageSize,int peixunBatchId)
			throws ElException;

	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int batchId) throws ElException;
	
	public int listAssignedUserSize(int pageNow, int pageSize, int batchId) throws ElException;
	
	public int listUsersSize(ElNode dep, ElNode sta,int subdep, ELUser eu,int peixunBatchId)
	throws ElException;
	
	public List<ELUser> listUsers(ElNode dep, ElNode sta,int subdep, ELUser eu,
			int pageNow, int pageSize,int peixunBatchId) throws ElException;
	
	public void addBatchEluser(int batchid,int userid) throws ElException;
	
	public void delete_elclass(int elclassid) throws ElException;
	
	public PeixunBatch getPeixunBatchById(int batchId) throws ElException;
	
	public List<ElClass> getElclassList(int batchId) throws ElException;
	
	public List<PeixunBatch> getMyBatchList(int id, int pageNow, int pageSize) throws ElException;
	
	/**
	 * //将培训批次、培训批次对应的培训班、培训班中的课程分配给用户
	 * @param batchid
	 * @throws ElException
	 */
	public void addBatchClass_course(int batchid,int userid,int joinway) throws ElException;
	
	public List<MyClass> getMyBatchDetail(int userid) throws ElException;
	
	public int getMyBatchDetailSize(int userid) throws ElException;
	
	public void sortCps(int courseid, int sortid, int upordown,int batchid)
	throws ElException ;
	
	/**
	 * 更新培训batch进度
	 * @param batchid
	 * @param userid
	 * @throws ElException
	 */
	public void updateBatchProcess(int batchid,int userid) throws ElException;
	
	public List<ElClass> getClassList(ElNode tree, ElClass elclass,
			int sublibs, String status, String sqlw, int pageNow, int pageSize,int peixunBatchId)
			throws ElException;
	
	
	public int getClassListSize(ElNode tree, ElClass elclass, int sublibs,
			String status,int peixunBatchId) throws ElException ;
	
	/**
	 * 获取完成的培训班或正在学的培训班
	 * @param batchid
	 * @param userid
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public ElClass getDoneOrNowElClass(int batchid,int userid ,int type) throws ElException;
	
	/**
	 * 判断培训批次是否分配给用户
	 * @param batchid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkPeixunBatchIsAssignToUser(int batchid,int userid) throws ElException;
	
	/**
	 * 用户培训batch进度
	 * @param batchid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public double getPeixunBatchProcess(int batchid,int userid) throws ElException;
	
	/**
	 * 系统培训班学习情况
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> listElClasses() throws ElException;


}