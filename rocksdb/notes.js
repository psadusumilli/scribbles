 ROCKSDB,
 an embeddable, persistent key-value store for fast storage that we built and use here at Facebook.
RocksDB builds on 'LevelDB', Google open source key value database library, to satisfy several goals:
vision:
      Scales to run on servers with many CPU cores.
      Uses fast storage efficiently.
      Is flexible to allow for innovation.
      Supports IO-bound, in-memory, and write-once workloads.

1.    RocksDB scales to run on servers with many CPU cores
            One advantage is that the semantics it provides are simpler than a traditional DBMS.
            For example, it supports MVCC, but only for read-only transactions.
            The database is logically partitioned into a read-only path and a read-write path.
            Both of these reduce the usage of locks, and reducing lock contention is a prerequisite for supporting high-concurrency workloads.
2.    RocksDB makes efficient use of storage -- more IOPS, improved compression, less write wear
            Compared to an update-in-place B-tree, it is possible to get better compression and less write amplification with a write-optimized database like RocksDB
            more IOPs with flash
3 .    Flexible architecture
            RocksDB code is manageable and easy to extend.
            For example, we’ve added a merge operator that makes some updates write-only rather than read-modify-write, where "read" and "write" imply potential storage reads and writes.
            This reduces the amount of IO for some write-intensive workloads.
4.     RocksDB supports IO-bound, in-memory, and write-once workloads
            Work in progress to tune for one-time insert, in-memory workloads
            NOT a distributed database--rather the focus is on making an efficient, high-performance, single-node database engine.

ARCHITECTURE
1 RocksDB is a C++ library that persistently stores keys and values.
   Keys and values are arbitrary byte streams, and keys are stored in sorted sequences.
   New writes occur to new places in the storage, and a background compaction process eliminates duplicates and processes delete markers.
   Data is stored in the form of a 'log-structured merge tree'. ( 2 trees , 1 in memory, 1 in disk with periodic reconciliation)
   There is support for atomically writing a set of keys into the database, and backward and forward iterations over the keys are supported.

2 RocksDB is built using a "pluggable" architecture, which makes it easy to replace parts of it without impacting the overall architecture of the system.
