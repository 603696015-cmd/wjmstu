package com.sopia.statman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.statman.entities.Flow;
import com.sopia.statman.entities.Queryobj;
import com.sopia.statman.entities.Resources;
import com.sopia.statman.entities.Statisticobj;


public interface StatisticFlowAndResourseDao {
	/**
	 * 获取流量统计信息
	 * @return
	 * @throws ElException
	 */
	public Flow getFlowStatisticInfo(int depId) throws ElException;
	/**
	 * 获取流量统计登录信息
	 * @return
	 * @throws ElException
	 */
	public Flow getFlowStatisticLoginInfo(int depId) throws ElException;
	/**
	 * 获取注册用户的数量
	 * @return
	 * @throws ElException
	 */
	public int getRegisterUserCount(int depId) throws ElException;
	/**
	 * 获取当前登录用户的数量
	 * @return
	 * @throws ElException
	 */
	public int getLoginUserCount(int depId) throws ElException;
	/**
	 * 资源统计
	 * @param depid 
	 * @return
	 * @throws ElException
	 */
	public Resources getResourceStatistic(int depid)throws ElException;
	/**
	 * 统计用户登陆信息
	 * @return
	 * @throws ElException
	 */
	public Statisticobj getLoginUserInfo() throws ElException;
	/**
	 * 统计学员学习（或者考试、练习）的记录信息(例如当前在线学习人次，今天学习人次，昨天学习人次)
	 * @return
	 * @tAlias 表的别名
	 * @throws ElException
	 */
	public Statisticobj getCeRecordInfo(String tAlias) throws ElException;
	/**
	 * 获取所有学习(考试，练习等)记录信息
	 * @param queryobj
	 * @param tAlias
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Queryobj> getAllCeRecordInfo(Queryobj queryobj,String tAlias, int pageNow,
			int pageSize) throws ElException;
	/**
	 * 获取所有学习(考试，练习等)记录信息数量
	 * @param queryobj
	 * @param tAlias
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getAllCeRecordInfoSize(Queryobj queryobj,String tAlias) throws ElException;
	/**
	 * 根据条件删除学习(考试，练习等)记录信息
	 * @param queryobj
	 * @throws ElException
	 */
	public void deleteCeRecordInfo(Queryobj queryobj) throws ElException;
}
