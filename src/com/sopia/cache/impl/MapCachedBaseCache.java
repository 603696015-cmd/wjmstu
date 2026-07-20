package com.sopia.cache.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sopia.cache.IBaseCache;


/**
 * 
 * @author zahj
 *
 */
public class MapCachedBaseCache implements IBaseCache{

protected static Map<String,Object> baseMap = new HashMap<String,Object>();
	
	private static volatile MapCachedBaseCache mapCachedBaseCache = null;
	
	/**
	 * 双重检查单利模式 线程安全 lazy加载，效率较高
	 * @return
	 */
	public static MapCachedBaseCache getInstance(){
		if(mapCachedBaseCache == null){
			synchronized (MapCachedBaseCache.class) {
				if(mapCachedBaseCache == null){
					mapCachedBaseCache = new MapCachedBaseCache();
				}
			}
		}
		
		return mapCachedBaseCache;
	}
	
	@Override
	public   boolean put(String paramString, Object paramObject) {
		baseMap.put(paramString, paramObject);
		return true;
	}

	@Override
	public synchronized  boolean put(String paramString, Object paramObject, Date paremDate) {
		baseMap.put(paramString, paramObject);
		return true;
	}

	@Override
	public synchronized  Object get(String paramString) {
		return baseMap.get(paramString);
	}

	@Override
	public synchronized  boolean remove(String paramString) {
		baseMap.remove(paramString);
		return true;
	}

	@Override
	public synchronized  boolean removeAll() {
		return false;
	}

	@Override
	public synchronized boolean containsKey(String key) {
		return baseMap.containsKey(key);
	}

	@Override
	public synchronized  Set<String> getAllKey() {
		return baseMap.keySet();
	}



}
