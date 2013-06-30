package com.vijayrc.scribble.redis.sample;

import com.vijayrc.scribble.redis.domain.MyPublisher;
import com.vijayrc.scribble.redis.domain.MySubscriber;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class PubSubSample extends BaseSample {

    @Override
    public void run() throws Exception {
        final String channel = "news";
        final Jedis subscriberJedis = newJedis();
        final JedisPubSub mySubscriber = new MySubscriber();

        Thread subscriberThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String channel = "news";
                        subscriberJedis.subscribe(mySubscriber, channel);
                    }
                }
        );
        subscriberThread.start();

        Jedis publisherJedis = newJedis();
        MyPublisher myPublisher = new MyPublisher(publisherJedis, channel);
        myPublisher.publish();

        mySubscriber.unsubscribe();
        close(subscriberJedis);
        close(publisherJedis);
    }
}
