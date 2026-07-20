package com.sopia.cache;

import com.sopia.cache.impl.MapCachedBaseCache;
import com.sopia.cache.impl.RedisCacheImpl;




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
	
	public static void main(String[] args) {
		IBaseCache redisCache = CacheFactory.getRedisCache();
		System.out.println(redisCache.get("xiaoming").toString());
	}
}
