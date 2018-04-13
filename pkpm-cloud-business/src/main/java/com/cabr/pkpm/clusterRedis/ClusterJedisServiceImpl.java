/*package com.cabr.pkpm.clusterRedis;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisCluster;

public class ClusterJedisServiceImpl implements JedisService {
	
	@Autowired
    private JedisCluster jedisCluster;
	@Override
	public String set(String key, String value) {
		String string = jedisCluster.set(key, value);
		return string;
	}
	
	@Override
	public String get(String key) {
		String string = jedisCluster.get(key);
		return string;
	}
	
	@Override
	public Long hset(String key, String field, String value) {
		Long hset = jedisCluster.hset(key, field, value);
		return hset;
	}
	
	@Override
	public String hget(String key, String field) {
		String hget = jedisCluster.hget(key, field);
		return hget;
	}
	
	@Override
	public Long hdel(String key, String fields) {
		Long hdel = jedisCluster.hdel(key, fields);
		return hdel;
	}

	@Override
	public Long expire(String key, int seconds) {
		Long expire = jedisCluster.expire(key, seconds);
		return expire;
	}

	@Override
	public Long ttl(String key) {
		Long ttl = jedisCluster.ttl(key);
		return ttl;
	}

}
*/