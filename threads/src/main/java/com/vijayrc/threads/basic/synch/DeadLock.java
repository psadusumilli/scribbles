package com.vijayrc.threads.basic.synch;

import static com.vijayrc.threads.util.Printer.log;

public class DeadLock {
    private static final String lock1 = "lock1";
    private static final String lock2 = "lock2";

    public static class Agent1 implements Runnable{
        @Override
        public void run() {
           synchronized (lock1){
               log("agent1 got lock1");
               synchronized (lock2){
                   log("agent1 got lock2");
               }
           }
        }
    }
    public static class Agent2 implements Runnable{
        @Override
        public void run() {
            synchronized (lock2){
                log("agent2 got lock2");
                synchronized (lock1){
                    log("agent2 got lock1");
                }
            }
        }
    }
    public static void main(String[] args){
        log("main start");
        Thread t1 = new Thread(new Agent1());
        Thread t2 = new Thread(new Agent2());
        t1.start();
        t2.start();
        log("main end");
    }
}
