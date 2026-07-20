package com.msc.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
private static JedisPool jedisPool = null;
    
    public static JedisPool getJedisPool(){
        synchronized(JedisPoolUtil.class){  //解决高并发问题
            JedisPoolConfig jpc = new JedisPoolConfig();    //获取jedispool连接池配置类
            jpc.setMaxIdle(300);         //最大空闲连接
            jpc.setMaxTotal(500);       //最大活动连接
            jpc.setMaxWaitMillis(100000); //最长等待时间
            jpc.setTestOnBorrow(false);
            
            jedisPool = new JedisPool(jpc, "10.0.1.100", 6379);
        }
        return jedisPool;
    }
    
    public static void main(String[] args) {
    	JedisPool jp = JedisPoolUtil.getJedisPool();
        Jedis jedis = jp.getResource();
        jedis.set("a", "jjj");
        String a = jedis.get("a");
        System.out.println(a);
        jedis.close();
	}
}
