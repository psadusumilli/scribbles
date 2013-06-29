package com.vijayrc.scribble.redis.sample;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;

@Log4j
public class KeyValueSample extends BaseSample {

    public KeyValueSample(Jedis jedis) {
        super(jedis);
    }

    @Override
    public void run() throws Exception {
        jedis.set("key_1", "value_1");
        print("key_1");
        jedis.append("key_1", " value_1.1");
        print("key_1");

        jedis.incr("key_2");
        print("key_2");
        jedis.incrBy("key_2", 3);
        print("key_2");
        jedis.decrBy("key_2", 2);
        print("key_2");

        jedis.set("key_3", "value_3");
        print("key_3");
        jedis.expire("key_3", 2);
        Thread.sleep(3000);
        print("key_3");

        jedis.del("key_1", "key_2", "key_3");
    }

    private void print(String key) {
        log.info(key + " => " + jedis.get(key));
    }
}
