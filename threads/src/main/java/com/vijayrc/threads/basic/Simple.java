package com.vijayrc.threads.basic;

import static com.vijayrc.threads.util.Printer.log;

/**
 * main thread ends fast but jvm holds up until the thread is done
 */
public class Simple extends Thread{
    public Simple(String name) {
        super(name);
    }

    public static void main(String[] args){
        log("main start");
        new Simple("S1").start();
        log("main end");
    }

    @Override
    public void run() {
       for(int i = 0;i<5;i++){
           log(getName() + "|" + i);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}

