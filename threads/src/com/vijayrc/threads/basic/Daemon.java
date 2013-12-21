package com.vijayrc.threads.basic;

import static com.vijayrc.threads.util.Printer.log;

/**
 * jvm does not bother about daemon threads while shutting down
 */
public class Daemon extends Thread{
    public Daemon(String name) {
        super(name);
        this.setDaemon(true);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true) log(getName()+"'s demons run amok");
    }

    public static void main(String[] args){
        Daemon satan = new Daemon("satan");
        satan.start();
        log("main end");
    }
}
