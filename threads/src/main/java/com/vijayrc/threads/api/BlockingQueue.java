package com.vijayrc.threads.api;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

import static com.vijayrc.threads.util.Printer.log;

public class BlockingQueue<T> {
    private List<T> queue = new LinkedList<>();
    private int limit = 2;
    private boolean isFull() {return queue.size() == limit;}
    private boolean isEmpty() {return queue.size() == 0; }
    public BlockingQueue(int limit) {this.limit = limit;}

    public synchronized void enqueue(T item) throws InterruptedException {
        while (isFull()) wait();
        if (isEmpty()) notifyAll();//ask other fillers to put in elements
        queue.add(item);
        print();
    }
    public synchronized T dequeue() throws InterruptedException {
        while (isEmpty()) wait();
        if (isFull()) notifyAll(); //since you are removing a element, notify any filling threads to put in a element
        T t = queue.remove(0);
        print();
        return t;
    }

    private void print(){
        log(StringUtils.join(queue, ","));
    }

    public static class Producer extends Thread{
        private BlockingQueue<String> queue;
        public Producer(String name, BlockingQueue<String> queue) {
            super(name);
            this.queue = queue;
        }
        @Override
        public void run() {
            for(int i = 0; i<10; i++)
                try {
                    queue.enqueue(getName() + "|" + i);
                } catch (InterruptedException e) {log(e);}
        }
    }
    public static class Consumer extends Thread{
        private BlockingQueue<String> queue;
        public Consumer(String name, BlockingQueue<String> queue) {
            super(name);
            this.queue = queue;
        }
        @Override
        public void run() {
            for(int i = 0; i<10; i++)
                try {
                    queue.dequeue();
                } catch (InterruptedException e) {log(e);}
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new BlockingQueue<>(10);
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
