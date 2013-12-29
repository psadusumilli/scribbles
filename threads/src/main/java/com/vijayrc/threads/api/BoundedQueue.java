package com.vijayrc.threads.api;

import java.util.LinkedList;
import java.util.List;

import static com.vijayrc.threads.util.Printer.log;
import static org.apache.commons.lang.StringUtils.join;

public class BoundedQueue<T> {
    private List<T> queue = new LinkedList<>();
    private int limit = 2;
    private boolean isFull() {return queue.size() == limit;}
    private boolean isEmpty() {return queue.size() == 0; }
    public BoundedQueue(int limit) {this.limit = limit;}

    public synchronized void put(T item) throws InterruptedException {
        while (isFull()) wait();
        if (isEmpty()) notifyAll();//ask other fillers to put in elements
        queue.add(item);
        print();
    }
    public synchronized T get() throws InterruptedException {
        while (isEmpty()) wait();
        if (isFull()) notifyAll(); //since you are removing a element, notify any filling threads to put in a element
        T t = queue.remove(0);
        print();
        return t;
    }
    private void print(){log(join(queue, ","));}

    public static class Producer extends Thread{
        private BoundedQueue<String> queue;
        public Producer(String name, BoundedQueue<String> queue) {super(name);this.queue = queue;}
        @Override
        public void run() {
            for(int i = 0; i<10; i++)
                try {
                    queue.put(getName() + "|" + i);
                } catch (InterruptedException e) {log(e);}
        }
    }
    public static class Consumer extends Thread{
        private BoundedQueue<String> queue;
        public Consumer(String name, BoundedQueue<String> queue) {super(name);this.queue = queue;}
        @Override
        public void run() {
            for(int i = 0; i<10; i++)
                try {
                    queue.get();
                } catch (InterruptedException e) {log(e);}
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BoundedQueue<String> queue = new BoundedQueue<>(10);
        for(int i=0;i<20;i++){
            Producer producer = new Producer("P" + i, queue);
            Consumer consumer = new Consumer("C" + i, queue);
            producer.start();
            consumer.start();
            producer.join();
            consumer.join();
        }
        log("main end");
    }
}
