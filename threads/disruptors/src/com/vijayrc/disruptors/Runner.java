package com.vijayrc.disruptors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        Consumer<Event> consumer  = new Consumer<>();

        Disruptor<Event> disruptor = new Disruptor<>(Event.factory, 1024, exec);
        disruptor.handleEventsWith(consumer);
        RingBuffer<Event> ringBuffer = disruptor.start();

        for (long i = 10; i < 2000; i++) {
            long seq = ringBuffer.next();
            Event event = ringBuffer.get(seq);
            event.setValue("m-" + i);
            ringBuffer.publish(seq);
        }
        disruptor.shutdown();
        exec.shutdown();
    }
}
