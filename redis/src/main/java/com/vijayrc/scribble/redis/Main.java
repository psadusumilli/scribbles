package com.vijayrc.scribble.redis;

import com.vijayrc.scribble.redis.sample.KeyValueSample;
import com.vijayrc.scribble.redis.sample.Sample;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Main {

    public static void main(String[] args){
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        Jedis jedis = pool.getResource();

        new KeyValueSample(jedis).run();

    }
}
