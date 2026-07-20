package com.msc.cache;

import com.msc.cache.impl.MapCachedBaseCache;
import com.msc.cache.impl.RedisCacheImpl;


/**
 * »º´æ¹¤³§
 * @author dongke
 *
 */
public class CacheFactory {

public static final String PREFIX_CACHE_KEY = "SELF_";
	
	public static IBaseCache getRedisCache(){
		return RedisCacheImpl.getInstance();
	}

	public static IBaseCache getMapCache(){
		return MapCachedBaseCache.getInstance();
	}
}
