package com.vijayrc.threads.api;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.vijayrc.threads.util.Printer.log;

/**
 * 2 smugglers exchange gold and money at rendezvous point
 */
public class Smuggling {
   private static Exchanger<String> exchanger = new Exchanger<>();
   public static class BadGuy implements Runnable{
       private String name;
       private String item;
       public BadGuy(String name, String item) {this.name = name; this.item = item;}
       @Override
       public String toString() {return name+"|"+item;}
       @Override
       public void run() {
         log(toString()+"|travelling to rendezvous point");
           try {
               Thread.sleep((long) (Math.random()*3000));
               item = exchanger.exchange(item);
               log(toString()+"|exchange done");
           } catch (InterruptedException e) {
               log(toString()+"|shit..police patrol");
           }
       }
   }
   public static void main(String[] args) throws InterruptedException {
       ExecutorService executors = Executors.newFixedThreadPool(2);
       try {
           executors.submit(new BadGuy("G1","gold"));
           executors.submit(new BadGuy("G2","money"));
           executors.awaitTermination(3, TimeUnit.SECONDS);
       } finally {
           executors.shutdown();
           log("smuggling is bad..m-kay");
       }
   }
}
