package com.vijayrc.threads.basic;

import java.util.ArrayList;
import java.util.List;
import static com.vijayrc.threads.util.Printer.log;

/**
 * wrote all classes as 'public static' just to enclose them into a single file Factory.java, nothing more
 * they could have been put in separate files, did just for convenience to read
 *
 * a thread calling lock.wait() will go to sleep until woken up by a lock.notify() by another thread
 * wait and notify should be made on the locking object, and within a monitor context(synchronised)
 *
 */
public class Factory {
    public static class Inventory {
        private List<String> items = new ArrayList<>();
        public boolean isFull(){return items.size() >= 5;}
        public boolean isEmpty(){return items.isEmpty();}

        public void add(String item){
            log("P|"+item);
            items.add(item);
        }
        public void remove() {
            if (isEmpty()) return;
            String remove = items.remove(0);
            log("C|"+remove);
            items.remove(remove);
        }
    }
    public static abstract class Actor extends Thread{
        protected final Inventory inventory;
        protected boolean stop;
        public Actor(Inventory inventory) {this.inventory = inventory;}
        public void shutdown() {stop = true;}
        protected void inventoryNotify(){inventory.notify();}
        protected void inventoryWait() {
            try {inventory.wait();
            } catch (InterruptedException e) {log(e);}
        }
        protected void sleep(){
            try {Thread.sleep(500);
            } catch (InterruptedException e) {log(e);}
        }
    }
    /**
     * When inventory is full, Producer notifies Consumer and waits
     */
    public static class Producer extends Actor{
        public Producer(Inventory inventory) {super(inventory);}

        @Override
        public void run() {
            int count = 0;
            synchronized (inventory){
                while(!stop){
                    if(inventory.isFull()){
                        log("full|producer waiting and notifies consumer");
                        inventoryNotify();
                        inventoryWait();  //thread sleeps waiting on the object
                        log("producer waiting done");
                    }else{
                        inventory.add("p-" + count++);
                        sleep();
                    }
                }
                log("producer shutdown");
                inventoryNotify(); //release any lock
            }
        }
    }
    /**
     * consumer waits and notifies when inventory is empty
     * or just keep consuming
     */
    public static class Consumer extends Actor{
        public Consumer(Inventory inventory) {super(inventory);}
        @Override
        public void run() {
            synchronized (inventory){
                while(!stop){
                    if(inventory.isEmpty()){
                        log("empty|consumer notifies and waiting");
                        inventoryNotify();
                        inventoryWait();  //thread sleeps waiting on the object
                        log("consumer waiting done");
                    }else{
                        inventory.remove();
                        sleep();
                    }
                }
                log("consumer stopped");
                inventoryNotify(); //release any lock
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Inventory inventory = new Inventory();
        Producer producer = new Producer(inventory);
        Consumer consumer = new Consumer(inventory);
        producer.start();
        consumer.start();

        log("main sleeping");
        Thread.sleep(9000);
        log("main woke up");
        producer.shutdown();
        consumer.shutdown();
        log("main end");
    }

}
