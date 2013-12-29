package com.vijayrc.threads.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.vijayrc.threads.util.Printer.log;
import static org.apache.commons.lang.StringUtils.join;

/**
 * A ReentrantReadWriteLock maintains a pair of associated locks, one for read-only operations and one for writing.
 * The read lock may be held simultaneously by multiple reader threads, so long as there are no writers. The write lock is exclusive.
 * A read-write lock allows for a greater level of concurrency in accessing shared data than that permitted by a mutual exclusion lock.
 * It exploits the fact that while only a single thread at a time (a writer thread) can modify the shared data, in many cases any number of threads can concurrently read the data (hence reader threads).
 * In theory, the increase in concurrency permitted by the use of a read-write lock will lead to performance improvements over the use of a mutual exclusion lock.
 * In practice this increase in concurrency will only be fully realized on a multi-processor, and then only if the access patterns for the shared data are suitable.
 *
 * In this example, the single Writer holds a lock on Board for 20s and writes every 2 sec.
 * Meanwhile 4 Readers can still read the updates
 */
public class NoticeBoard {
    public static class Board {
        private final List<String> messages = new ArrayList<>();
        public String read() {return join(messages, "|");}
        public void write(String message) {messages.add(message);}
    }
    public static class Reader implements Runnable{
        private Board board;
        private String name;
        private Lock lock;
        public Reader(String name, Board board, ReadWriteLock rwl) {
            this.name = name ;this.board = board;this.lock = rwl.readLock();
        }
        @Override
        public void run() {
            for(int i=0;i<30;i++){
                lock.lock();
                log(name+" read "+board.read());
                try {
                    log(name+" reader sleeping");
                    Thread.sleep((long) (Math.random()*5000));
                } catch (InterruptedException e) {log(e);}
                lock.unlock();
            }
        }
    }
    public static class Writer implements Runnable{
        private Board board;
        private String name;
        private Lock lock;
        public Writer(String name, Board board,ReadWriteLock lock) {
            this.name = name; this.board = board; this.lock = lock.writeLock();
        }
        @Override
        public void run() {
            for(int i=0;i<30;i++){
                String msg = "m"+i;
                lock.lock();
                board.write(msg);
                log(name + " wrote " + msg);
                lock.unlock();

                if(i%5==0){
                    try { Thread.sleep(5000);log(name+" writer sleeping");
                    } catch (InterruptedException e) {log(e);}
                }
            }
        }
    }
    public static void main(String[] args){
        final Board b = new Board();
        final ReadWriteLock rwl = new ReentrantReadWriteLock();
        ExecutorService exe = Executors.newFixedThreadPool(6);
        try {
            exe.submit(new Writer("W", b, rwl));
            for(int i = 1;i<5;i++)
                exe.submit(new Reader("R"+i,b,rwl));
        } finally {
            exe.shutdown();
        }
    }
}


