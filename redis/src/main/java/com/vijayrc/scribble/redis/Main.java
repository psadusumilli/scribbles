package com.vijayrc.scribble.redis;

import com.vijayrc.scribble.redis.sample.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Main {

    public static void main(String[] args) throws Exception {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        Jedis jedis = pool.getResource();

        //new KeyValueSample(jedis).run();
        //new ListSample(jedis).run();
        //new SetSample(jedis).run();
        new HashSample(jedis).run();

        jedis.flushAll();
        jedis.flushDB();
    }
}
