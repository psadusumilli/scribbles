GARBAGE COLLECTORS

All of them are generational collectors

1 Serial Collector =>
      Single CPU collector for both young and old generations.
      NOT meant for server environment since it freezes all threads during its run.
      good for embedded systems without a lot of processing power.
      by default, do not use this unless you have less than 2 processors and less than 2GB of memory. Activated by '-XX:+UseSerialGC'
2 Parallel Compacting Collector =>
      Young and old generation are both collected in parallel using multiple threads and compacted.
      activated with '-XX:+UseParallelOldGC'
3 Parallel Collector =>
      collects new in parallel , but old by serial algorithm.
      this is the JVM default collector
      'freezes app threads,so significant pauses' during minor and full GC, so app must be tolerant.
      low CPU usage
      activated by '-XX:+UseParallelGC'
5 Concurrent Mark Sweep (CMS) Collector =>
      low latency (minimal pauses) & non-compacting.
      'no freezing of app threads', multiple threads run along with app threads to concurrently mark and sweep.
      STW (stop the world) can still happen when old generation has no space for new comers from newer generation in a minor collection.
      good until heap < 4 GB
      high CPU usage
      activated by '-XX:+UseConcMarkSweepGC | -XX:+USeParNewGC'
      Came with Sun 'HotSpot' to
4 G1 Garbage Collector =>
      planned to be the long-term replacement for the CMS GC. Controversial, since CMS also solves the Parallel GC STW pauses, some claim in a better way.
      came in jdk 7 from Oracle
      good for heap > 6 GB
      divides the heap in 1-32MB regions, runs collection and compaction concurrently
      STW can still happen if the collection does not catch up with heavy app usage.
      activated by '-XX:+UseG1GC'
      JDK8 removed Permanent generation, introduced string deduplication
      will be default for JDK 9

//-------------------------------------------------------------------------------------------------------------------
MINOR|MAJOR|FULL collections
https://plumbr.eu/handbook/garbage-collection-in-java#survivor-spaces
Any generational heap is broken into
  1 | Eden
  2 | Survivor 1
  3 | Survivor 2
  4 | Tenured

https://plumbr.eu/blog/garbage-collection/minor-gc-vs-major-gc-vs-full-gc


//-------------------------------------------------------------------------------------------------------------------
JVM AND SWAPPING
      hacksaw pattern is normal GC cycle with minor collections whenever ~80% usage is reached
      Full GC is needed when it cant cope up.
