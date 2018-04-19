package com.pkpmdesktopcloud.redis;

import org.apache.ibatis.cache.Cache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

public class RedisCache implements Cache{
    private final ReadWriteLock readWriteLock = new DummyReadWriteLock();
    private String id;
    private static JedisPool pool;
    private  RedisConfig redisConfig;
    private Integer timeOut;

    public RedisCache(final String id) {
        try {
            if (id == null)
                throw new IllegalArgumentException("Cache instances require an ID");
            this.id = id;
            redisConfig = RedisConfigurationBuilder.getInstance().parseConfiguration();
            pool = new JedisPool(redisConfig, redisConfig.getHost(), redisConfig.getPort(),
                    redisConfig.getConnectionTimeout(), redisConfig.getSoTimeout(), redisConfig.getPassword(),
                    redisConfig.getDataBase(), redisConfig.getClientName(), redisConfig.getSsl(),
                    redisConfig.getSslSocketFactory(), redisConfig.getSslParameters(), redisConfig.getHostNameVerifier());
        }
        catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
        }

    }
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
}
