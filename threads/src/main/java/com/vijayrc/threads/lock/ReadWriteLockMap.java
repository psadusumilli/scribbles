package com.vijayrc.threads.lock;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A ReadWriteLockMap maintains a pair of associated locks, one for read-only operations and one for writing.
 * The read lock may be held simultaneously by multiple reader threads, so long as there are no writers. The write lock is exclusive.
 * A read-write lock allows for a greater level of concurrency in accessing shared data than that permitted by a mutual exclusion lock.
 * It exploits the fact that while only a single thread at a time (a writer thread) can modify the shared data, in many cases any number of threads can concurrently read the data (hence reader threads).
 * In theory, the increase in concurrency permitted by the use of a read-write lock will lead to performance improvements over the use of a mutual exclusion lock.
 * In practice this increase in concurrency will only be fully realized on a multi-processor, and then only if the access patterns for the shared data are suitable.
 */
public class ReadWriteLockMap {
    static class RWDictionary {
        private final Map<String, String> m = new TreeMap<>();
        private final ReadWriteLock rwl = new ReentrantReadWriteLock();
        private final Lock r = rwl.readLock();
        private final Lock w = rwl.writeLock();

        public String get(String key) {
            r.lock();
            try { return m.get(key); }
            finally { r.unlock(); }
        }
        public Set<String> allKeys() {
            r.lock();
            try { return m.keySet(); }
            finally { r.unlock(); }
        }
        public String put(String key, String value) {
            w.lock();
            try { return m.put(key, value); }
            finally { w.unlock(); }
        }
        public void clear() {
            w.lock();
            try { m.clear(); }
            finally { w.unlock(); }
        }
    }
}
