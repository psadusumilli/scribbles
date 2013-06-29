package com.vijayrc.scribble.redis.sample;

import redis.clients.jedis.Jedis;

public abstract class BaseSample implements Sample{
    protected Jedis jedis;

    public BaseSample(Jedis jedis) {
        this.jedis = jedis;
    }
}
