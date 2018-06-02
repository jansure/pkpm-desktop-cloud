package com.pkpmdesktopcloud.redis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.ibatis.cache.Cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

public class RedisCache implements Cache{
    private final ReadWriteLock readWriteLock = new DummyReadWriteLock();
    private String id;
    private static JedisPool pool;
    private static JedisSentinelPool jSentinelPool;
    private  RedisConfig redisConfig;
    private Integer timeOut;

    public RedisCache(final String id) {
        try {
            if (id == null)
                throw new IllegalArgumentException("Cache instances require an ID");
            this.id = id;
            redisConfig = RedisConfigurationBuilder.getInstance().parseConfiguration();
            pool = new JedisPool(redisConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getConnectionTimeout(),
                    redisConfig.getSoTimeout(), redisConfig.getPassword(), redisConfig.getDatabase(), redisConfig.getClientName(),
                    redisConfig.isSsl(), redisConfig.getSslSocketFactory(), redisConfig.getSslParameters(),
                    redisConfig.getHostnameVerifier());
            }
        catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
        }

    }
    
    //通过哨兵获取可用主机
//    public RedisCache(final String id) {
//    	
//    	try {
//    		if (id == null)
//    			throw new IllegalArgumentException("Cache instances require an ID");
//    		this.id = id;
//    		redisConfig = RedisConfigurationBuilder.getInstance().parseConfiguration();
//	        
//	        jSentinelPool=new JedisSentinelPool(redisConfig.getMaster(), redisConfig.getNodes(), redisConfig);
//	        Jedis jedis = jSentinelPool.getResource();
//	        System.out.println(redisConfig.getMaster() + "--" + jedis.getClient().getHost() + ":" + jedis.getClient().getPort());
//	        
//		    pool = new JedisPool(redisConfig, jedis.getClient().getHost(), jedis.getClient().getPort(), redisConfig.getConnectionTimeout(),
//		    redisConfig.getSoTimeout(), redisConfig.getPassword(), redisConfig.getDatabase(), redisConfig.getClientName(),
//		    redisConfig.isSsl(), redisConfig.getSslSocketFactory(), redisConfig.getSslParameters(),
//		    redisConfig.getHostnameVerifier());
//		    
//		    jSentinelPool.close();
//		    jSentinelPool.destroy();
//	        
//    	}catch (Exception e) {
//          System.err.println(e.getStackTrace().toString());
//      }
//    }
    
    
    private Object execute(RedisCallback redisCallback) {
        Jedis jedis = pool.getResource();
        try {
            return redisCallback.dowithRedis(jedis);
        }
        finally {
            jedis.close();
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getSize(){
        return (Integer)execute(
                jedis -> {
                    Map<byte[],byte[]> result = jedis.hgetAll(id.getBytes());
                    return result.size();
                }
        );
    }

    @Override
    public void putObject(final Object key,final Object value) {
        this.execute(jedis -> {
           final byte[] idBytes = id.getBytes();
           jedis.hset(idBytes,key.toString().getBytes(),
                   redisConfig.getSerializer().serialize(value));
           if(timeOut != null && jedis.ttl(idBytes) == -1) {
               jedis.expire(idBytes,timeOut);
           }
           return null;
        });
    }

    @Override
    public Object getObject(final Object key) {
        return this.execute(
                jedis -> {
                    return redisConfig.getSerializer().deserialize(
                            jedis.hget(id.getBytes(),key.toString().getBytes())
                    );
                }
        );
    }
    
    /**
     * 
     * @Title: getAll  
     * @Description: 获取所有的键值对
     * @return Map<byte[],byte[]>    返回类型  
     * @throws
     */
    public Map<byte[],byte[]> getAll() {
        return (Map<byte[],byte[]>)this.execute(
        		jedis -> {
                    Map<byte[],byte[]> result = jedis.hgetAll(id.getBytes());
                    return result;
                }
        );
    }

    @Override
    public Object removeObject(final Object key) {
        return execute(jedis -> {
            return jedis.hdel(id,key.toString());
        });
    }

    @Override
    public void clear() {
        this.execute(jedis ->{
            jedis.del(id);
            return null;
        });
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    @Override
    public String toString() {
        return "Redis {" + id + "}\n";
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }
    
    public void destroy() {
    	pool.close();
    	pool.destroy();
    }
}
