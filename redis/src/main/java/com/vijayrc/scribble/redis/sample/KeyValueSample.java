package com.vijayrc.scribble.redis.sample;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;

@Log4j
public class KeyValueSample extends BaseSample{

    public KeyValueSample(Jedis jedis) {
        super(jedis);
    }

    @Override
    public void run() {
        jedis.set("key_1", "value_1");
        log.info("key_1 => " + jedis.get("key_1"));

        jedis.append("key_1"," value_1.1");
        log.info("key_1 => " + jedis.get("key_1"));

        jedis.incr("key_2");
        log.info("key_2 => " + jedis.get("key_2"));
        jedis.incrBy("key_2",3);
        log.info("key_2 => " + jedis.get("key_2"));
        jedis.decrBy("key_2",2);
        log.info("key_2 => " + jedis.get("key_2"));

        jedis.del("key_1","key_2");
    }
}
