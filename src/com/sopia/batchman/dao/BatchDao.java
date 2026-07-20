package com.sopia.batchman.dao;

import java.util.List;

import com.sopia.batchman.entities.Batch;
import com.sopia.batchman.entities.Flow;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;

/**
 * 培训批次DAO
 * 
 * @author luocw
 *
 */
public interface BatchDao {

	/**
	 * 新增培训批次
	 * @param batch
	 * @throws ElException
	 */
	public void addBatch(Batch batch) throws ElException;

	/**
	 * 更新培训批次
	 * @param batch
	 * @throws ElException
	 */
	public void updateBatch(Batch batch) throws ElException;

	/**
	 * 获取培训批次大小
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public int getBatchListSize(String name) throws ElException;

	/**
	 * 获取培训批次
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Batch> getBatchList(String name, int pageNow, int pageSize) throws ElException;
	
	/**
	 * 根据id获取培训批次
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Batch getBatchById(int id) throws ElException;

	/**
	 * 根据ID删除培训批次
	 * @param id
	 * @throws ElException
	 */
	public void deleteBatch(int id) throws ElException;

	/**
	 * 获取培训批次中的培训班
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getBatchElclass(int id) throws ElException;
	
	/**
	 * 添加培训批次和培训班关系
	 * @param batchId
	 * @param classId
	 * @throws ElException
	 */
	public void addBatchClass(int batchId, int classId) throws ElException;

	/**检查添加培训批次和培训班关系
	 * @param batchId
	 * @param classId
	 * @throws ElException
	 */
	public boolean checkBatchClass(int batchId, int classId) throws ElException;
	/**
	 * 获取统计培训批次
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	public List<Batch> getBatchStatList(String name, int pageNow, int pageSize) throws ElException;

	/**
	 * 获取统计培训批次列表大小
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	public int getBatchStatListSize(String name) throws ElException;

	/**
	 * 获取统计培训批次中的培训班
	 * @param batchId
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getBatchElclassState(int batchId) throws ElException;

	/**
	 * 删除培训批次中的培训班
	 * @param batchId
	 * @param elClassId
	 * @throws ElException
	 */
	public void delBatchClass(int batchId, int elClassId) throws ElException;
	
	/**流量统计
	 * @return
	 * @throws ElException
	 */
	public Flow getFlow()throws ElException;
	
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
}
