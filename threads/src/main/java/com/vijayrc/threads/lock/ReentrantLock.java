package com.vijayrc.threads.lock;

import static com.vijayrc.threads.util.Printer.log;

/**
 * Intrinsic locks are reentrant, if a thread tries to acquire a lock that it already holds, the request succeeds.
 * Reentrancy means that locks are acquired on a per thread rather than per invocation basis.
 * Reentrancy is implemented by associating with each lock an acquisition count and an owning thread.
 * When the count is zero, the lock is considered unheld
 *
 * Simply put, reentrant is ability to repeatedly call synchronised methods on the same locked object
 */
public class ReentrantLock {
    public static class Engine{
        public synchronized void start(){
            log("engine started, new lock on engine object");
        }
    }
    public static class Car{
        private Engine engine = new Engine();
        protected synchronized void start(){
            engine.start();
            log("car started, lock already held on 'car' object if subclass 'start' is called");
        }
    }
    public static class Corolla extends Car{
        public synchronized void start(){
            super.start();
            log("corolla started");
        }
    }
    public static class Driver extends Thread{
        private Car car;
        public Driver(Car car) {this.car = car;}
        @Override
        public void run() {
            car.start();
        }
    }
    public static void main(String[] args){
        Corolla corolla = new Corolla();
        Driver driver = new Driver(corolla);
        driver.run();
        log("main ended");
    }
}
