package com.vijayrc.threads;

/**
 * main thread ends fast but jvm holds up until the thread is done
 */
public class SimpleThread extends Thread{
    public SimpleThread(String name) {
        super(name);
    }

    public static void main(String[] args){
        System.out.println("main start");
        new SimpleThread("S1").start();
        System.out.println("main end");
    }

    @Override
    public void run() {
       for(int i = 0;i<5;i++){
           System.out.println(getName()+"|"+i);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}

