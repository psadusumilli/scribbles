package com.vijayrc.disruptors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tests {
    private ExecutorService executor;
    private Disruptor<Event> disruptor;

    @Before
    public void setup(){
        executor = Executors.newCachedThreadPool();
    }
    @Test
    public void shouldRunUnicast(){
        Handler handler = new Handler("h1");
        disruptor = new Disruptor<>(Event.factory, 1024, executor);
        disruptor.handleEventsWith(handler);
        RingBuffer<Event> ringBuffer = disruptor.start();

        for (int i = 0; i < 2000; i++) {
            long seq = ringBuffer.next();
            Event event = ringBuffer.get(seq);
            event.setValue("p-" + i);
            ringBuffer.publish(seq);
        }
        disruptor.shutdown();
    }
    @Test
    public void shouldRunUnicastWithCustomPublisher() throws Exception {
        Handler handler = new Handler("h1");
        disruptor = new Disruptor<>(Event.factory, 1024, executor);
        disruptor.handleEventsWith(handler);

        Publisher publisher = new Publisher("p1", 600, disruptor);
        disruptor.publishEvent(publisher);
        disruptor.start();
        executor.submit(publisher);

        sleep();
        publisher.stop();
    }
    @Test
    public void shouldRunBroadcastWithSinglePublisherAndMultipleHandlers() throws Exception {
        disruptor = new Disruptor<>(Event.factory, 1024, executor);
        disruptor.handleEventsWith(new Handler("h1"),new Handler("h2"),new Handler("h3"));

        Publisher publisher = new Publisher("p1", 600, disruptor);
        disruptor.publishEvent(publisher);
        disruptor.start();
        executor.submit(publisher);

        sleep();
        publisher.stop();
    }
    @Test
    public void shouldRunBroadcastWithSinglePublisherAndLinkedMultipleHandlers() throws Exception {
        disruptor = new Disruptor<>(Event.factory, 1024, executor);
        disruptor.handleEventsWith(new Handler("h1")).then(new Handler("h2")).then(new Handler("h3"));

        Publisher publisher = new Publisher("p1", 600, disruptor);
        disruptor.publishEvent(publisher);
        disruptor.start();
        executor.submit(publisher);

        sleep();
        publisher.stop();
    }

    @After
    public void shutDown(){
        disruptor.shutdown();
        executor.shutdown();
}
    private void sleep() throws InterruptedException {Thread.sleep(3000);}
}
