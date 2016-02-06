GENERATIONAL HEAP
  Any generational heap is broken into
    1 | Eden
        New objs created here, frequent GC
        TLAB (thread local allocation buffers) makes sure each threads gets it own contiguous allocation space
        When TLAB is full, allocation is moved to shared eden space
        If Eden gets full, GC is triggered, or else new objects allocation into old generation
        'mark-and-copy'
          When Eden is being collected, GC walks all the reachable objects from the roots and marks them as alive
          Then all 'live' objects are 'copied' to one of the 'survivor' spaces

    2 | Survivor (from|to)
        One of the survivor spaces is always maintained empty
        During Eden GC, all objects from Eden + non-empty 'from' survivor are copied to the 'to' survivor
        Then roles switch between the survivor-spaces
        The cycle happens continuously, when any objects had survived the threshold cycle number (age), it moves to tenured.

    3 | Tenured
         Old objects here, GC less frequent
         GC steps:
           NO 'mark-and-copy' but 'mark-delete-compact'
           Mark reachable objects by setting the marked bit next to all objects accessible through GC roots
           Delete all unreachable objects
           Compact the content of old space by copying the live objects contiguously to the beginning of the Old space

    'Metaspace'
      Perm Gen got removed in JDK 8, so class definitions are stored here
      By default, Metaspace size is only limited by the amount of native memory available to the Java process
      java -XX:MaxMetaspaceSize=256m com.mycompany.MyApplication

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
COLLECTIONS
  1 'Minor Collection'
    Collecting garbage from the Young space is called Minor GC
    1.1
    Minor GC is always triggered when the JVM is unable to allocate space for a new object, e.g. Eden is getting full.
    So the higher the allocation rate, the more frequently Minor GC occurs.
    Tenured Generation is effectively ignored.
    1.2
    References from Tenured Generation to Young Generation are considered to be GC roots.
    References from Young Generation to Tenured Generation are simply ignored during the mark phase.
    1.3
    Do pause application threads, negligible time so not noticed if all new objs are garbage, if not, you can feel the pause.

  2 'Major Collection'
     Collecting garbage from Old space
     No clear demarcation between Full and Major Collection (no proper terminology anywhere)
     roughly:
        Major GC is cleaning the Old space.
        Full GC is cleaning the entire Heap – both Young and Old spaces.
     So focus on finding out whether the GC at hand stopped all the application threads or was able to progress concurrently with the application threads.

  jstat -gc -t 4235 1s (statistics on GC given a process-id with an polling interval of 1 sec)
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GC ALGORITHMS


//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
VM AND SWAPPING
  hacksaw pattern is normal GC cycle with minor collections whenever ~80% usage is reached
  Full GC is needed when it cant cope up.

  'How to get right heap size?'
  The heap usage should look something like a saw-tooth pattern, going up and up until it reaches 100% and then drop down to a value below 100%.
  These "drops" are caused by garbage collection.  Check the percentage at the bottom of each "drop".
  This is how much of your Java heap is occupied by live objects.
  Say for example that 25% of a 2 GB heap is occupied after each garbage collection, which means that you have 0.5 GB of live objects.
  A simple rule-of-thumb minimum heap size is  0.5 *2 = 1 GB

  'JVM release memory? Nope'
  It wont  typically release much memory once its warmed up, even when the application is lightly loaded or even idle at a later time.
  Though -XX:+UseSerialGC can occasionally shrink the heap and return memory,  almost never used in production for an obvious performance reason.
  The parallel collector and the concurrent mark sweep (CMS) collector, which are often used in production, almost never shrink the heap and return memory.
  
//-------------------------------------------------------------------------------------------------------------------
REFERENCE
https://plumbr.eu/handbook/garbage-collection-in-java#survivor-spaces
https://plumbr.eu/blog/garbage-collection/minor-gc-vs-major-gc-vs-full-gc
https://blogs.oracle.com/jrockitdetails/entry/on_java_heap_sizing_migrated
http://psy-lob-saw.blogspot.com/2014/10/the-jvm-write-barrier-card-marking.html
http://hiroshiyamauchi.blogspot.com/2013/06/making-jvm-release-memory.html
