package com.vijayrc.storm.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisRepo {
    private JedisPool pool;

    public JedisRepo() {
        pool = new JedisPool(new JedisPoolConfig(), "localhost");
    }
    public void close(Jedis jedis) {
        pool.returnResource(jedis);
    }
    public Jedis newJedis() {
        return pool.getResource();
    }
    public void shutdown() {
        pool.destroy();
    }
}

