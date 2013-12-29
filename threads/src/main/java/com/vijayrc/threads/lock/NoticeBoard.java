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
 */
public class NoticeBoard {
    public static class Board {
        private final List<String> messages = new ArrayList<>();
        private final ReadWriteLock rwl = new ReentrantReadWriteLock();
        private final Lock r = rwl.readLock();
        private final Lock w = rwl.writeLock();

        public String read() {
            r.lock();
            try { return join(messages, "|"); }
            finally { r.unlock(); }
        }
        public void write(String message) {
            w.lock();
            try { messages.add(message); }
            finally { w.unlock(); }
        }
    }
    public static class Reader implements Runnable{
        private Board board;
        private String name;
        public Reader(String name, Board board) {this.name = name ;this.board = board;}
        @Override
        public void run() {
            for(int i=0;i<40;i++){
                log(name+" read "+board.read());
                try { Thread.sleep(500);
                } catch (InterruptedException e) {log(e);}
            }
        }
    }
    public static class Writer implements Runnable{
        private Board board;
        private String name;
        public Writer(String name, Board board) {this.name = name; this.board = board;}
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                String msg = "m"+i;
                board.write(msg);
                log(name+" wrote "+msg);
                try { Thread.sleep(2000);
                } catch (InterruptedException e) {log(e);}
            }
        }
    }
    public static void main(String[] args){
        Board b = new Board();
        ExecutorService executors = Executors.newFixedThreadPool(6);
        try {
            executors.submit(new Writer("W",b));
            for(int i = 1;i<5;i++)
                executors.submit(new Reader("R"+i,b));
        } finally {
            executors.shutdown();
        }
    }
}
