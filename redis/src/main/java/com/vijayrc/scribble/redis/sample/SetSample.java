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
        jedis.sadd("set_boys_1", "cartman", "kenny", "kyle", "cartman");
        jedis.sadd("set_boys_2", "token", "jimmy", "butters", "kyle");

        log.info("members of set_boys_1: " + jedis.smembers("set_boys_1"));
        log.info("members of set_boys_2: " + jedis.smembers("set_boys_2"));
        log.info("is kyle in set_boys: " + jedis.sismember("set_boys_1", "kyle"));
        log.info("intersection of set boys: " + jedis.sinter("set_boys_1", "set_boys_2"));

        jedis.del("set_boys_1", "set_boys_2");

        jedis.zadd("scores", 99, "math");
        jedis.zadd("scores", 69, "english");
        jedis.zadd("scores", 78, "physics");
        jedis.zadd("scores", 92, "zoology");

        log.info("no. of scores: " + jedis.zcard("scores"));
        log.info("no. of scores between 90 and 100:" + jedis.zcount("scores", 90, 100));
        log.info("scores between 70 and 100:" + jedis.zrangeByScore("scores", 70, 100));
        log.info("last 3 scores:" + jedis.zrange("scores", 0, 2));
        log.info("rank of zoology:" + jedis.zrank("scores", "zoology"));

        jedis.del("scores");
    }
}
