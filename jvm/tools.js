REFERENCE
http://www.ibm.com/developerworks/library/j-5things8/
https://dzone.com/articles/how-analyze-java-thread-dumps

TOOLS
   //get running pid
   jps -v => to list running java process
   kill -3 pid > dump.log => kills the process though

   jstack -l pid >> dump.log => to take dump of running process without killing.
   http://docs.oracle.com/javase/7/docs/technotes/tools/share/jstack.html

   $ jcmd <PID> Thread.print
   $ jcmd MyProgram help (or "jcmd 2125 help")
            2125:
            The following commands are available:
            JFR.stop
            JFR.start
            JFR.dump
            JFR.check
            VM.native_memory
            VM.check_commercial_features
            VM.unlock_commercial_features
            ManagementAgent.stop
            ManagementAgent.start_local
            ManagementAgent.start
            Thread.print
            GC.class_stats
            GC.class_histogram
            GC.heap_dump
            GC.run_finalization
            GC.run
            VM.uptime
            VM.flags
            VM.system_properties
            VM.command_line
            VM.version
            help
   https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jcmd.html
   https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/tooldescr006.html

   jstat
   http://docs.oracle.com/javase/1.5.0/docs/tooldocs/share/jstat.html

THREAD DUMPS
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  STATES
   0 NEW
   The thread is created but has not been processed yet.
   1 RUNNABLE
   The thread is occupying the CPU and processing a task. (It may be in WAITING status due to the OS resource distribution.)
   2 WAITING
   A thread goes to wait state once it calls wait(), join(), park() on an Object. This is called Waiting State.
   Once a thread reaches waiting state, it will need to wait till some other thread notify() or notifyAll() on the object.
   3 BLOCKED
   Once this thread is notified, it will not be runnable.
   It might be that other threads are also notified(using notifyAll) or the first thread has not finished his work, so it is still blocked till it gets its chance.
   This is called Blocked State. 'deadlock' is mutual blocking.
   4 TIMED_WAITING
   The thread is waiting by using a sleep, wait, join or park method.
   The difference from WAITING is that the maximum waiting time is specified by the method parameter, and WAITING can be relieved by time as well as external change

ANALYSIS
1 'When high CPU usage'
    ps -mo pid.lwp.stime.time.cpu -C java
    PID         LWP          STIME                  TIME        %CPU
    10029               -         Dec07          00:02:02           99.5
           -       10039        Dec07          00:00:00              0.1
           -       10040        Dec07          00:00:00           95.5
    Acquire the Light Weight Process (LWP) that uses the CPU the most and convert its unique number (10039) into a hexadecimal number (0x2737).
    Extract the thread dump of an application with a PID of 10029, then find the thread with an nid of 0x2737.
    Check the trace.

2 'When the Processing Performance is Abnormally Slow'
    After acquiring thread dumps several times, find the list of threads with BLOCKED status.
    Find which thread owns the blocking monitor, that blocks these guyz.
    Frequently with DBMS scenarios.

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
HEAP
jcmd <pid> GC.heap_dump Myheapdump
jcmd <pid> GC.class_histogram >> Myheaphistogram

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
FLIGHT MONITOR
  java -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=3614 -Dcom.sun.management.jmxremote.ssl=false
   -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false -XX:+UnlockCommercialFeatures
   -XX:+FlightRecorder -jar target/standalone.jar
   Run jmc.exe (Java Mission Control) located in your "JAVA_HOME/bin" folder.
   In order to connect into Dev server JVM go to "File->Connect->Create New Connection" and populate the following fields:
       Host: acsd1lc9a003.app.c9.equifax.com
       Port: 3614

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
POSTMORTEM ANALYSIS TOOLS
'Heap Dump -> .hprof'
1 Eclipse Memory Analyzer helps in memory leaks identification. - 'good for heap dumps .hprof taken from jcmd'
2 JvisualVM -> 'not good enuf'

'Thread Dump -> .tdump'
1 JvisualVM sucks  => plugin blocked by proxy
2  Spotify => 'good' http://spotify.github.io/threaddump-analyzer/
