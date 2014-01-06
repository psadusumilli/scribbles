package com.vijayrc.disruptors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorTest {
    private ExecutorService executor = Executors.newCachedThreadPool();

    @Before
    public void setup(){
        executor = Executors.newCachedThreadPool();
    }
    @Test
    public void shouldRunUnicast(){
        Handler<Event> handler = new Handler<>(name);
        Disruptor<Event> disruptor = new Disruptor<>(Event.factory, 1024, executor);
        disruptor.handleEventsWith(handler);
        RingBuffer<Event> ringBuffer = disruptor.start();

        for (int i = 10; i < 2000; i++) {
            long seq = ringBuffer.next();
            Event event = ringBuffer.get(seq);
            event.setValue("m-" + i);
            ringBuffer.publish(seq);
        }
        disruptor.shutdown();
    }

    @After
    public void shutDown(){executor.shutdown();}
}
