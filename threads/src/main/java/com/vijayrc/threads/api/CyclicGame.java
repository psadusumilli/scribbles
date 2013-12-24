package com.vijayrc.threads.api;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.vijayrc.threads.util.Printer.log;

/**
 * interrupted-exception:thrown when this thread is interrupted while waiting on barrier
 * broken-barrier-exception:thrown if any other thread waiting on barrier is interrupted
 */
public class CyclicGame {
    public static final CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
        @Override
        public void run() {log("barrier|all gamers joined!");}
    });
    public static class Gamer implements Runnable{
        private String name;
        private Gamer(String name) {this.name = name;}
        @Override
        public void run() {
            log(name+"|waiting for other gamers..");
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {log(e);}
            log(name+"|logged in");
        }
    }
    public static void main(String[] args){
        ExecutorService executors = Executors.newFixedThreadPool(5);
        try {
            for(int i=0;i<5;i++)
                executors.submit(new Gamer("G"+i));
        } finally {
            executors.shutdown();
        }
    }
}
