package com.msc.cache;


import java.util.Date;
import java.util.Set;

/**
 * 基础缓存API接口
 * 
 * @author dongke
 * 
 */
public interface IBaseCache {

	/**
	 * 放入缓存
	 * 
	 * @param paramString
	 * @param obj
	 * @return
	 */
	public boolean put(String paramString, Object obj);

	/**
	 * 放入缓存过期时间
	 */
	public boolean put(String paramString, Object obj, Date exTime);

	/**
	 * 是否存在缓存中
	 */
	public boolean containsKey(String key);

	/**
	 * 移除缓存
	 */
	public boolean remove(String key);

	/**
	 * 获取所有缓存
	 */
	public Set<String> getAllKey();

	public Object get(String key);

	/**
	 * 清空缓存
	 */
	public boolean removeAll();

}
