package com.vijayrc.threads.basic.variables;

import java.util.ArrayList;
import java.util.List;

import static com.vijayrc.threads.util.Printer.log;

/**
 *  The volatile keyword ensures that when a thread writes to a volatile shared field variable,
 *  the JVM modifies the main-memory copy(ram), not the thread's working-memory copy (cpu caches).
 *  Similarly, the JVM ensures that a thread always reads from the main-memory copy
 *  The volatile can be faster than synchronised at times just for simple shared variables
 */
public class Volatility {
    private volatile List<Integer> fibonaccis = new ArrayList<>();

    public Volatility() {
        fibonaccis.add(0); fibonaccis.add(1);
    }
    public int update(){
      int i = fibonaccis.size()-1;
      int p = fibonaccis.get(i) + fibonaccis.get(i - 1);
      fibonaccis.add(p);
      return p;
    }
    public void print(){
        log("size="+fibonaccis.size());
        StringBuilder builder = new StringBuilder();
        for (Integer fibonacci : fibonaccis)
            builder.append(fibonacci+",");
        log(builder);
    }

    public static class Updater extends Thread{
        private Volatility v;
        public Updater(Volatility v, String name) {super(name);this.v = v;}
        @Override
        public void run() {
           for(int i= 0;i<8;i++)
               log(getName()+"|"+v.update());
        }
    }

    /**
     * Both threads update a volatile list of fibonacci series
     * which should be correct as 'volatile' makes sure the latest is picked up by any thread
     */
    public static void main(String[] args) throws InterruptedException {
        Volatility v = new Volatility();
        Thread t1 = new Updater(v,"t1");
        Thread t2 = new Updater(v,"t2");
        Thread t3 = new Updater(v,"t3");
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        v.print();
    }

}

