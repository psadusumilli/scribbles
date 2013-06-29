package com.vijayrc.scribble.redis.sample;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;

@Log4j
public class ListSample extends BaseSample {

    public ListSample(Jedis jedis) {
        super(jedis);
    }

    @Override
    public void run() throws Exception {
        jedis.lpush("boys", "stan", "kyle", "cartman", "kenny");
        jedis.rpush("boys", "butter", "token");
        log.info("southpark boys = " + jedis.llen("boys"));

        log.info("r-pop:"+jedis.rpop("boys"));
        log.info("l-pop:"+jedis.lpop("boys"));
        log.info("southpark boys = " + jedis.llen("boys"));

        log.info("l-index of 1:"+jedis.lindex("boys",1));

        jedis.del("boys");
        log.info("southpark boys = " + jedis.llen("boys"));
    }
}
