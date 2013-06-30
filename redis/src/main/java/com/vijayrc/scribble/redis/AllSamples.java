package com.vijayrc.scribble.redis;

import com.vijayrc.scribble.redis.sample.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class AllSamples {

    public static void main(String[] args) throws Exception {
        new KeyValueSample().run();
        new ListSample().run();
        new SetSample().run();
        new HashSample().run();
        new PubSubSample().run();
    }
}
