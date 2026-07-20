package com.msc.cache.modules.service;


public interface ISystemModuleCacheService {
	
	/**
	 * 同步菜单缓存
	 */
	public void synchronizeCacheByDataBase();
	
	/**
	 * 同步部门缓存
	 */
	public void synchroizeCacheByDepartment();
	
	/**
	 * 获取菜单对象
	 * 
	 */
	public String getSystemModules(String paramString);
	
}
