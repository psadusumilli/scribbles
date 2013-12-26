package com.vijayrc.threads.lock;

import java.util.concurrent.locks.*;
import java.util.concurrent.locks.ReentrantLock;

import static com.vijayrc.threads.util.Printer.log;

/**
 * using lock interface api, using reentrant api for implementation
 * we can write our own implementation of Lock if required,
 * but must keep in mind reentrancy, starvation, fairness
 */
public class ExplicitLock {
    public static class RestRoom{
        private Lock lock = new ReentrantLock();
        public void use(){
            String name = Thread.currentThread().getName();
            try {
                log(name +"-waiting..");
                lock.lock();
                log(name +"-crapping");
                Thread.sleep((long) (Math.random()*2000));
            } catch (InterruptedException e) {
                log(e);
            } finally {
                lock.unlock();
                log(name +"-done");
            }
        }
    }
    public static class Guest extends Thread{
        private RestRoom restRoom;
        public Guest(RestRoom restRoom, String name) {super(name);this.restRoom = restRoom;}
        @Override
        public void run() {restRoom.use();}
    }
    public static void main(String[] args) throws InterruptedException {
        RestRoom restRoom = new RestRoom();
        Guest g1 = new Guest(restRoom,"g1");
        Guest g2 = new Guest(restRoom,"g2");
        Guest g3 = new Guest(restRoom,"g3");
        g1.start();g2.start();g3.start();
        g1.join();g2.join();g3.join();
        log("party over");
    }

}
