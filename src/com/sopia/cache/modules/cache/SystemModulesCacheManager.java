package com.sopia.cache.modules.cache;

import java.util.List;

import com.sopia.cache.IBaseCache;

public class SystemModulesCacheManager {
private static volatile SystemModulesCacheManager systemModulesCacheManager;
	
	/**
	 * 基础缓存
	 */
	private IBaseCache baseCache;
	
	/**
	 * 获取缓存
	 */
	public IBaseCache getBaseCache(){
		return this.baseCache;
	}
	
	public void setBaseCache(IBaseCache baseCache){
		this.baseCache = baseCache;
	}
	
	public static SystemModulesCacheManager getInstance(){
		if(systemModulesCacheManager == null){
			synchronized (SystemModulesCacheManager.class) {
				if(systemModulesCacheManager == null){
					systemModulesCacheManager = new SystemModulesCacheManager();
				}
			}
		}
		return systemModulesCacheManager;
	}
	
	/**
	 * 同步系统缓存
	 * @param dep
	 */
	public synchronized void synchronizedSysDepCache(List<String> deps){
		if(deps!= null && deps.size()>0){
			this.baseCache.put("allDepList", deps);
		}else{
			this.baseCache.put("test", 123);
		}
	}
}
