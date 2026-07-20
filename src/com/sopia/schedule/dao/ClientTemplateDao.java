package com.sopia.schedule.dao;

import com.sopia.common.ElException;
import com.sopia.schedule.entities.ModuleZDY;

public interface ClientTemplateDao {
	/**
	 * 根据模块id查询该模块上传模板信息
	 * @param moduleid
	 * @return
	 * @throws ElException
	 */
	public ModuleZDY select_moduleZDY_by_moduleid(int moduleid) throws ElException;
	
	/**
	 * 根据模块id修改添加页面或者修改页面或者查看页面的filename
	 * @param moduleid
	 * @param uploadType
	 * @param stFileName
	 * @throws ElException
	 */
	public void updateModuleZDYByModuleid(int moduleid,int uploadType,String stFileName) throws ElException;

}
