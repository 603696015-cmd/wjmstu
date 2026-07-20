package com.sopia.simulation.dao;

import com.sopia.common.ElException;
import com.sopia.simulation.entity.SimulationResult;

public interface SimulationDao {

	/**
	 * 添加学生记录接口
	 * @param sr
	 */
	public void addSimulation(SimulationResult sr)throws ElException;
	
	/**
	 * 通过用户id获取
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public SimulationResult getSimlationResultById(String id,String paperId) throws Exception;
}
