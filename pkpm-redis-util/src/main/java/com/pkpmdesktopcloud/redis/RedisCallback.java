package com.pkpmdesktopcloud.redis;

import redis.clients.jedis.Jedis;

public interface RedisCallback {
    Object dowithRedis(Jedis jedis);
}
