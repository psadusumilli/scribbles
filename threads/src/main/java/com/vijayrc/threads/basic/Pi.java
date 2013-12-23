package com.vijayrc.threads.basic;

import static com.vijayrc.threads.util.Printer.log;

/**
 * join, isAlive
 */
public class Pi extends Thread {
    boolean negative = true;
    double value;
    public void run() {
        for (int i = 3; i < 100000; i += 2) {
            if (negative) value -= (1.0 / i);
            else value += (1.0 / i);
            negative = !negative;
            log(value);
        }
        value += 1.0;
        value *= 4.0;
        log("finished calculating pi");
    }

    public static void main(String[] args) throws InterruptedException {
        log("main start");
        doNotWaitAndPrintIntermediateValue();
        checkIfAliveAndPrintFinalValue();
        joinWaitAndPrintFinalValue();
        log("main end");
    }

    private static void doNotWaitAndPrintIntermediateValue() {
        Pi pi = new Pi();
        pi.start();
        log("intermediate pi value = " + pi.value);
    }

    private static void checkIfAliveAndPrintFinalValue() throws InterruptedException {
        Pi pi = new Pi();
        pi.start();
        while(pi.isAlive()){
            Thread.sleep(1000);
            log("pi thread is alive, so main sleeping");
        }
        log("final pi value after death = " + pi.value);
    }

    private static void joinWaitAndPrintFinalValue() throws InterruptedException {
        Pi pi = new Pi();
        pi.start();
        pi.join();
        log("final pi value after join = " + pi.value);
    }
}

