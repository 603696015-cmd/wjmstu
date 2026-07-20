package com.sopia.cache.modules.service.impl;


import com.sopia.cache.modules.cache.SystemModulesCacheManager;
import com.sopia.cache.modules.service.ISystemModuleCacheService;
import com.sopia.common.ElException;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.entities.Department;

public class ISystemModuleCacheServiceImpl implements ISystemModuleCacheService{
	
	private SystemModulesCacheManager cacheManager;
	
	private DepartmentDao departmentDao;
	

	@Override
	public void synchronizeCacheByDataBase() {
		
	}

	@Override
	public void synchroizeCacheByDepartment() {
		
		
	}

	@Override
	public String getSystemModules(String paramString) {
		return null;
	}

	public SystemModulesCacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(SystemModulesCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	
	
}
