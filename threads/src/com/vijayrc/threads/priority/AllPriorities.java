package com.vijayrc.threads.priority;

import static com.vijayrc.threads.util.Printer.log;
import static com.vijayrc.threads.util.Printer.log2;

public class AllPriorities {
    public static class Pi extends Thread{
        private boolean negative = true;
        private boolean shouldYield = false;
        private double value;

        public Pi(String name) {
            super(name);
        }
        public void run() {
            for (int i = 3; i < 1000; i += 2) {
                if (negative) value -= (1.0 / i);
                else value += (1.0 / i);
                negative = !negative;
                log2(getName() + "|");
                if(shouldYield) Thread.yield();
            }
            value += 1.0;
            value *= 4.0;
            log("\n"+getName()+"|"+"finished calculating pi="+value);
        }
        public void relent(){
            shouldYield = true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        withoutPriorities();
        withPriorities();
        withPrioritiesAndYield();
    }
    /**
     * mixed p1|p2|p1|p2|p2|p1
     */
    private static void withoutPriorities() {
        Pi p1 = new Pi("p1"); Pi p2 = new Pi("p2");
        p1.start(); p2.start();
    }
    /**
     * some order repetitively p1|p1|p1|p2|p2..
     * not guaranteed owing to time-slicing of processor
     */
    private static void withPriorities() {
        Pi p1 = new Pi("p1"); Pi p2 = new Pi("p2");
        p1.setPriority(9); p2.setPriority(1);
        p1.start(); p2.start();
    }
    /**
     * p1 yields to p2 in every loop
     * again not guaranteed owing to time-slicing
     */
    private static void withPrioritiesAndYield() {
        Pi p1 = new Pi("p1"); Pi p2 = new Pi("p2");
        p1.setPriority(9); p2.setPriority(1);
        p1.relent();
        p1.start(); p2.start();
    }
}
