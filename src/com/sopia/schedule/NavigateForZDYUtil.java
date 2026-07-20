package com.sopia.schedule;

import com.sopia.common.ElException;
import com.sopia.duman.dao.impl.FuncDaoImpl;
import com.sopia.duman.entities.ElFunc;
import com.sopia.schedule.dao.impl.ModuleManageDaoImpl;

public class NavigateForZDYUtil {
	
	private ElFunc ef;
	public FuncDaoImpl funcDao = new FuncDaoImpl();
	public ModuleManageDaoImpl moduleManageDao = new ModuleManageDaoImpl();

	public ElFunc getEf() {
		return ef;
	}

	public void setEf(ElFunc ef) {
		this.ef = ef;
	}
	
	
	public ElFunc getElFuncByTableNameAndParams(String tablename,String actionName,Integer rx) throws ElException{
		ef = funcDao.getElFuncByTableNameAndParams(tablename,actionName,rx);
		return ef;
	}
	
	
	
	public ElFunc getElFuncByTableNameForViewOrUpdate(String tablename,String actionName) throws ElException{
		ef = funcDao.getElFuncByTableNameForViewOrUpdate(tablename,actionName);
		return ef;
	}
	
	
}
