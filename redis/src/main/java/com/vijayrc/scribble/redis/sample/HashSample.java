package com.vijayrc.scribble.redis.sample;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;

@Log4j
public class HashSample extends BaseSample {

    @Override
    public void run() throws Exception {
        Jedis jedis = newJedis();

        jedis.hset("profile_1", "name", "steve");
        jedis.hset("profile_1", "gender", "male");
        jedis.hset("profile_1", "profession", "coder");

        jedis.sadd("skills_1", "java, ruby, scala");
        jedis.hset("profile_1", "skills", "skills_1");

        log.info("full profile_1: " + jedis.hgetAll("profile_1"));
        log.info("skills of profile_1: "+ jedis.smembers(jedis.hget("profile_1","skills")));

        jedis.hdel("profile_1","gender");
        log.info("full profile_1: " + jedis.hgetAll("profile_1"));

        jedis.del("profile_1");
    }
}
