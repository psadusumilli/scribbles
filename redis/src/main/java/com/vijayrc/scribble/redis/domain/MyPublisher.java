package com.vijayrc.scribble.redis.domain;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Log4j
public class MyPublisher {
    private Jedis jedis;
    private String channel;

    public MyPublisher(Jedis jedis, String channel) {
        this.jedis = jedis;
        this.channel = channel;
    }

    public void publish() throws Exception {
        log.info("Type messages to publish (q for terminate)..");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = reader.readLine();
            if ("q".equalsIgnoreCase(line)) break;
            jedis.publish(channel, line);
        }
    }
}
