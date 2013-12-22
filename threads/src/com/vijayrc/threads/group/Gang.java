package com.vijayrc.threads.group;

import static com.vijayrc.threads.util.Printer.log;

public class Gang {
    /**
     * each gang member waits on a interrupt call from his boss
     * the lock is the string constant "phone"
     * since they execute in a synchronised context, they respond one-by-one
     * the gang is heirarchical, g1->g2, g1.interrupt would call everyone recursively
     */
    public static class Member extends Thread{
        public Member(ThreadGroup group,String name) {super(group,name);}

        @Override
        public void run() {
            synchronized ("phone"){
                log(getName()+"|member=>boss did not call, I can sleep around");
                try {
                    "phone".wait();
                } catch (InterruptedException e) {
                    log(getName()+"|member=> shit..boss called, must inform the next guy");
                }
            }
        }
    }

    public static void main(String[] args){
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        log("main thread's group|"+mainGroup);
        log("main thread's group's parent|"+mainGroup.getParent());

        ThreadGroup g1 = new ThreadGroup("G1");
        ThreadGroup g2 = new ThreadGroup(g1,"G2");

        Thread t1 = new Member(g1,"t1");t1.start();
        Thread t2 = new Member(g1,"t2");t2.start();
        Thread t3 = new Member(g2,"t3");t3.start();
        Thread t4 = new Member(g2,"t4");t4.start();

        g1.list();
        log("g1 boss calling");
        g1.interrupt();
    }
}
