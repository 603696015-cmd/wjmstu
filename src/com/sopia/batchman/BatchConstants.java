package com.sopia.batchman;

/**
 * 培训批次常量
 * @author luocw
 *
 */
public class BatchConstants {
	 
	/***培训批次****/
	/**
	 * 培训批次新增
	 */
	public static final String BATCH_ADD="batch.add";
	/**
	 * 更新培训批次
	 */
	public static final String BATCH_MODIFY="batch.modify";
	/**
	 * 获取培训批次List
	 */
	public static final String BATCH_LIST="batch.list";
	/**
	 * 获取培训批次List大小
	 */
	public static final String BATCH_LIST_SIZE="batch.list.size";
	/**
	 * 根据ID获取培训批次
	 */
	public static final String BATCH_BY_ID="batch.by.id";
	/**
	 * 根据ID删除培训批次
	 */
	public static final String BATCH_DEL_ID="batch.del.id";
	/**
	 * 根据ID删除培训批次和培训班关联
	 */
	public static final String BATCH_CLASS_RELATION_DEL="batch.class.relation.del";
	
	/**
	 * 获取培训批次中的培训班
	 */
	public static final String BATCH_ELCLASS="batch.elclass";
	/**
	 * 添加培训批次的培训班
	 */
	public static final String BATCH_CLASS_ADD="batch.class.add";
	/**
	 * 删除培训批次的培训班
	 */
	public static final String BATCH_CLASS_DEL="batch.class.del";
	/**
	 * 添加统计的培训批次
	 */
	public static final String BATCH_STAT_LIST="batch.stat.list";
	/**
	 * 添加统计的培训批次List大小
	 */
	public static final String BATCH_STAT_LIST_SIZE="batch.stat.list.size";
	/**
	 * 获取培训批次中培训班通过率
	 */
	public static final String BATCH_CLASS_STAT="batch.class.stat";
	
 }
