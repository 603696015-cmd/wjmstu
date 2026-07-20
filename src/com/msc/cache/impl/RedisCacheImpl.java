package com.msc.cache.impl;



import java.util.Date;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

import com.msc.cache.IBaseCache;
import com.msc.cache.util.JsonOrObjectUtil;

/**
 * redis缓存
 * 
 * @author zahj
 * 
 */
public class RedisCacheImpl implements IBaseCache {

	protected static ReentrantLock lockPool = new ReentrantLock();

	protected static ReentrantLock lockJedis = new ReentrantLock();

	// redis服务器
	private static String ADDR_ARRAY = "10.0.1.100";

	// redis 端口号
	private static int PORT = 6379;

	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 100;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 8;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 3000;

	// 超时时间
	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = false;

	private static JedisPool jedisPool = null;

	protected static Jedis jedis = null;

	private static RedisCacheImpl redisCache = null;

	public static RedisCacheImpl getInstance() {
		if (redisCache == null) {
			synchronized (RedisCacheImpl.class) {
				if (redisCache == null) {
					redisCache = new RedisCacheImpl();
				}
			}
		}

		return redisCache;
	}

	/**
	 * redis过期时间,以秒为单位
	 */
	public final static int EXRP_HOUR = 60 * 60; // 一小时
	public final static int EXRP_DAY = 60 * 60 * 24; // 一天
	public final static int EXRP_MONTH = 60 * 60 * 24 * 30; // 一个月

	/**
	 * 初始化连接池
	 */
	private void initalPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[0],PORT, TIMEOUT);
		} catch (Exception e) {
            try{
	            JedisPoolConfig config = new JedisPoolConfig();  
	            config.setMaxTotal(MAX_ACTIVE);  
	            config.setMaxIdle(MAX_IDLE);  
	            config.setMaxWaitMillis(MAX_WAIT);  
	            config.setTestOnBorrow(TEST_ON_BORROW);  
	            jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[1], PORT, TIMEOUT);  
	        } catch (Exception e2) {  
	        }  
		}
	}

	private synchronized void poolInit() {
		if (jedisPool == null) {
			initalPool();
		}
	}

	public synchronized Jedis getJedis() {
		if (jedisPool == null) {
			poolInit();
		}

		Jedis jedis = null;

		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
			}
		} catch (Exception e) {
			RedisCacheImpl.clearBuffer(jedisPool, jedis);
		} finally {
			RedisCacheImpl.releaseConnection(jedisPool, jedis);
		}
		return jedis;
	}
	
	 /**
     * 超时等异常时清空该对象上次执行命令的结果缓存
     * @param pool
     * @param jedis
     * @param <T>
     */
    public static <T> void clearBuffer(Pool<T> pool, T jedis) {
        if (pool != null && jedis != null) {
            pool.returnBrokenResource(jedis);
        }
    }
	
    
    /**
     * 释放连接
     * @param pool
     * @param jedis
     * @param <T>
     */
    public static <T> void releaseConnection(Pool<T> pool, T jedis) {
        if (pool != null && jedis != null) {
            pool.returnResource(jedis);
        }
    }

	/**
	 * 释放jedis资源
	 */
	public static void retrunResource(final Jedis jedis) {
		if (jedis != null && jedisPool != null) {
			jedis.close();
		}
	}

	@Override
	public synchronized boolean put(String paramString, Object paramObject) {
		getJedis().set(paramString,
				JsonOrObjectUtil.beanToJson(paramObject, this));
		return true;
	}

	@Override
	public synchronized boolean put(String paramString, Object paramObject,
			Date paremDate) {
		getJedis().setex(paramString, 10000,
				JSONObject.fromObject(paramObject).toString());
		return true;
	}

	@Override
	public synchronized String get(String paramString) {
		if (getJedis() == null || !getJedis().exists(paramString)) {
			return null;
		}
		String value = "";
		try {
			value =  getJedis().get(paramString);
		} catch (Exception e) {
			System.out.println(paramString+"出现了错误");
		}
		return value;
	}

	@Override
	public synchronized boolean remove(String paramString) {
		getJedis().del(paramString);
		return true;
	}

	@Override
	public boolean removeAll() {
		getJedis().flushDB();
		return true;
	}

	@Override
	public synchronized boolean containsKey(String key) {
		return getJedis().exists(key);
	}

	@Override
	public Set<String> getAllKey() {
		// TODO Auto-generated method stub
		return getJedis().keys("*");
	}

}
