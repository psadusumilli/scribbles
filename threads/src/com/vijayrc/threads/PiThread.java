package com.vijayrc.threads;

import static com.vijayrc.threads.Printer.log;

public class PiThread extends Thread {
    boolean negative = true;
    double pi;

    public void run() {
        for (int i = 3; i < 100000; i += 2) {
            if (negative)
                pi -= (1.0 / i);
            else
                pi += (1.0 / i);
            negative = !negative;
            log(pi);
        }
        pi += 1.0;
        pi *= 4.0;
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
        PiThread piThread = new PiThread();
        piThread.start();
        log("intermediate pi value = " + piThread.pi);
    }

    private static void checkIfAliveAndPrintFinalValue() throws InterruptedException {
        PiThread piThread = new PiThread();
        piThread.start();
        while(piThread.isAlive()){
            Thread.sleep(1000);
            log("pi thread is alive, so main sleeping");
        }
        log("final pi value = " + piThread.pi);
    }

    private static void joinWaitAndPrintFinalValue() throws InterruptedException {
        PiThread piThread = new PiThread();
        piThread.start();
        piThread.join();
        log("final pi value = " + piThread.pi);
    }
}

