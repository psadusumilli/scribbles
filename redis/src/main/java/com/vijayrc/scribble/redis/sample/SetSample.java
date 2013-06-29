package com.vijayrc.scribble.redis.sample;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;

@Log4j
public class SetSample extends BaseSample {
    public SetSample(Jedis jedis) {
        super(jedis);
    }

    @Override
    public void run() throws Exception {
        jedis.sadd("set_boys","cartman","kenny","kyle","cartman");
        log.info("members of set_boys: "+jedis.smembers("set_boys"));
        log.info("is kyle in set_boys: "+jedis.sismember("set_boys","kyle"));

        jedis.del("set_boys");
    }
}
