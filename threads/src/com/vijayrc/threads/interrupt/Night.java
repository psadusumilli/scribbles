package com.vijayrc.threads.interrupt;

import static com.vijayrc.threads.util.Printer.log;

public class Night {
    public static class Alarm extends Thread{
        private Thread sleeper;
        public Alarm(Thread sleeper) {this.sleeper = sleeper;}
        @Override
        public void run() {
            log("alarm=>snoozing for 5s");
            try {Thread.sleep(5000);} catch (InterruptedException e) {log(e);}
            log("alarm=>snooze done, interrupting sleeper");
            sleeper.interrupt();
            log("alarm=>job done");
        }
    }
    public static class Sleeper extends Thread{
        @Override
        public void run() {
            while(!isInterrupted()){
                log("sleeper=>i can still snooze for 1s");
                try {Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log("sleeper=> i was rudely woken up");
                    interrupt(); // set the interrupt flag=true to break while loop
                }
            }
            log("sleeper=>I am totally awake now");
        }
    }
    public static void main(String[] args){
        Thread sleeper = new Sleeper();
        Thread alarm = new Alarm(sleeper);
        alarm.start();
        sleeper.start();
    }
}
