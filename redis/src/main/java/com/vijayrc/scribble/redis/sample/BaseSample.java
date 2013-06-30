package com.vijayrc.scribble.redis.sample;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public abstract class BaseSample implements Sample {
    private JedisPool pool;

    public BaseSample() {
        pool = new JedisPool(new JedisPoolConfig(), "localhost");
    }

    protected void close(Jedis jedis) {
        pool.returnResource(jedis);
    }

    protected Jedis newJedis() {
        return pool.getResource();
    }
}
