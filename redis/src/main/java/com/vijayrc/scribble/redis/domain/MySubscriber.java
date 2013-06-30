package com.vijayrc.scribble.redis.domain;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.JedisPubSub;

@Log4j
public class MySubscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        log.info("channel=" + channel + "|message=" + message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
       log.info("started subscribing to " + channel);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        log.info("stopped subscribing to " + channel);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
    }
}
