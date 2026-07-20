package com.msc.cache.modules.service.impl;



import com.sopia.cache.modules.cache.SystemModulesCacheManager;
import com.sopia.cache.modules.service.ISystemModuleCacheService;

public class ISystemModuleCacheServiceImpl implements ISystemModuleCacheService{
	
	private SystemModulesCacheManager cacheManager;
	
	

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

	
	
}
