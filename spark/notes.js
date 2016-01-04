SPARK
**********
Modules:
spark core => basic functionality of spark: task scheduling, memory management, fault recovery, storage system communication.
spark sql => working with structured data via SQL/HQL - supports Hive tables, Parquet, JSON.
spark streaming => process live streams of data
mlib => bunch of ready made machine learning functions
graphx => library for manipulating graphs via RDD.
cluster managers => standalone, YARN, Mesos.

INSTALL
download tar and /bin
start spark shell $ /bin/spark-shell

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CONCEPTS
'driver program' written in scala main() =>access 'Spark Context to build RDDs' from datasources sc.textFile()
driver program splits the workload, say portions of huge text file for wordcounts into 'tasks' that gets assigned to 'executors' on 'worker nodes'

 1 step:  start a maven proj, add spark-core as dependency
 2 step:  write driver program say
    com.vijayrc.spark.WordCount
        val conf = new SparkConf().setAppName("wordcount")
        val sc = new SparkContext(conf)
        val inputRDD = sc.textFile("file://some-input-file")
        val wordsRDD = inputRDD.flatMap( line => line.split(" "))
        val countsRDD = wordsRDD.map(word => (word, 1)).reducebyKey{ case (x, y) => x+y }
        countsRDD.saveAsTextFile("some-output-file")
 3 step: build the proj into a single uber jar say 'spark-wordcount.jar'
 4 step: submit and run
        $ SPARK-HOME/bin/spark-submit --class  com.vijayrc.spark.WordCount ./target/spark-wordcount.jar
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

RDD
is a immutable distributed collection of java/scala/python objects
split into partitions that are spread across nodes in cluster for execution

'creation'
     1 sc.parallelize(List("mark", "caollin")) => not recommended
     2 sc.textFile("file//some-input-file") => load from text data sources: json, csv, tsv all fall into this mode.
     3 sc.filter, sc.map => transformations of one RDD leads to another

 'transformations' =>
      filter, map, flatMap, distinct,  ..are all lazily evaluated until a action
      set functions: union, intersect, subtract, catesian
 'action' =>
      reduce((x,y) => x+y),
      fold (0)((x,y) => x+y), //siliar to reduce, but takes a initial value like 0 for +, 1 for *
      count , take, top,
      collect //returns all elements in a rdd, must fit inside a node memory
      //avg of a set numbers
      var result = numbers.aggregate{
        (0,0),
        (acc, value) => (acc._1 + value, acc._2 + 1), //acc is a struct ._1 stores sum, ._2 stores count
        (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2) //merge accumulators from all executor nodes.
        }
      val average = result._1/result._2

      foreach() => applies func on each element, returns nothing.

'functions'
      rdd.map(func)
      func => if func is a obj method or uses a obj field, then the entire class must be seriliazable.

'persist'
      all RDD are calculated from scratch for every transformation in the chain which is costly.
      calling 'rdd.persist(mode)' saves the RDD in memory or disk

      MEMORY_ONLY => memory, low cpu, more space.
      MEMORY_ONLY_SER => memory, saved in serialized format, high cpu, low space
      MEMORY_AND_DISK => memory but spilled to disk, low cpu, more space.
      MEMORY_AND_DISK_SER => memory but spilled to disk, high cpu, low space
      DISK_ONLY => high cpu, low space

'PairRDD' => key value pairs
      .keys, values, groupbyKey, reducebyKey, sortByKey, mapValues, combineByKey
      join //pick only common keys
      rightOuterJoin // 'a.b' take common keys + b keys
      leftOuterJoin // 'a.b' take common keys + a keys
      //find avg of values for each key

    val result = pairs.combineByKey{
        value => (value, 1), //k1->v11, 1, v12, 1: k2->v21,1, k2-v22,1...
        (acc: (Int, Int), value) => (acc._1+value, acc._2+1), // k1->(v11+v12, 2)
        (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2) //merge from all partitions for each key
      }.map{ case (key, value) => (key, value._1/value._2)} //calc avg for each key k1->22.33,k2->44.23

'partitioning'
      rdd.partitionby(new HashParitioner(100))
      rdd.partitioner -> Some(HashParitioner)
      custom -> subclass org.apache.spark.Partitioner
'parallelize'
     rdd.reducebyKey((x,y) => x+y, 10) //fixing number of partitions per rdd instead of spark default
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
LOADING/WRITING DATA
      1 //text
      sc.textFile("file://") => json (each line is a json record, use your preferred json lib like jackson ), text, csv (OpenCSV to parse), tsv;
      rdd.saveAsTextFile => to write rdd to file

      2 //hdfs
      sc.sequenceFile("hdfs://") => hdfs
      sc.hadoopFile, newAPIHadoopFile => hadoop
      spark supports many compression codecs like snappy, gzip, zlib by default.

      3 //jdbc
      val createConnection() ={ Class.forName(...)}
      rdd= new JdbcRDD(sc, createConnection, "select * from users", ...)

      4 //cassandra
      val conf = new SparkConf(true).set("spark.cassandra.connection.host","localhost")
      val sc= new SparkContext(conf)
      //load  an cassandra table test.kv(key text PRIMARY KEY, value int)
      val dataRDD = sc.cassandraTable("test", "kv")

      5 //kafka
      spark streaming has old Receiver based and Direct API after 1.5.2
            'receiver'
            import org.apache.spark.streaming.kafka._
            val kafkaStream = KafkaUtils.createStream(streamingContext,  [ZK quorum], [consumer group id], [per-topic number of Kafka partitions to consume])

            'direct'
            import org.apache.spark.streaming.kafka._
            val directKafkaStream = KafkaUtils.createDirectStream[ [key class], [value class], [key decoder class], [value decoder class] ]
                                                        (streamingContext, [map of Kafka parameters], [set of topics to consume])

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
BROADCAST AND ACCUMULATOR

val globalcount = sc.accumulator(0) // accumulator aggregates data from across all nodes,
custom accumulator is by extending AccumulatorParam

broadcast_var = sc.broadcast(some_var) //read-only shared variable across all nodes
normal variables passed in any transformation closure are sent as dedicated copies, sometimes multiple times.
broadcast is good for large variables to be passed in bittorrent like efficient mechanism.

pipe() is used to external programs written in languages like R

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CLUSTER

1 driver program main()
  => action
      => directed acyclic graph of transformations
        => spark scheduler launches a 'job' per action
        =>  job has 1-* 'stages'. stages are pipelined together, executed in order - 'serial'
         => stage has * 'tasks', tasks are 'parallel', 1 task/1 core
          => tasks are run on Y 'executors'(jvm) on Z 'nodes'(machines)

 driver program schedules tasks and manages executors.
 executors run tasks and store cached copies of RDD
driver: http://localhost:4040/

 CLUSTER MANAGER

1 'spark cluster manager'
  install spark on all nodes
  edit conf/slaves on master
  bin/start-all.sh
  bin/stop-all.sh
  spark-submit --master spark://masternode:7077 my-app
  http://masternode:8080

  spark-submit options
    --client/cluster => driver runs as client in the starting laptop, or in one of the nodes of cluster
    --executor-memory => 1G for each executor jvm
    --total-executor-cores => cores for the entire application

  if 20 nodes each with 4 cores,
    executor-memory = 1G, --total-executor-cores = 8 => 8 nodes (with spreadout using 1 core/machine), 2 nodes (w/o spreadout)

2 hadoop YARN
    export HADOOP_CONF_DIR=/etc/hadoop/conf
    spark-submit --master yarn myapp
    uses a fixed number of executors --num-executors

3 apache MESOS
    spark-submit --master mesos://masternode:5050 myapp
    'fine-shared mode' => executors can request more/less CPUs from Mesos as per demand -default
    'coarse mode' =>  fixed number of CPU per executor

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PERFORMANCE

      .persist helps in the truncation of rdd execution pipelining by saving processed forms.
      .cache is spark optimizing behind the scenes will reduce the stages
      http://localhost:4040 on the driver machine helps in analyzing
      each RDD maintains a pointer to parent RDD making a DAG, .toDebugString()

'parallelism'
       1 task for data stored in 1 partition
       inputRDD picks parallel partitions based on storage, for eg in 1 partition/ 1 HDFS block
       rdd.repartition() to choose number of partitions
       rdd.coalesce() to reduce number of partitions. //a filter to remove blank lines in a 95% blank-line file.
       rdd.getNumPartitions()
'serialization'
        spark default is java serialization aint great, use kyro serialization, register your classes with kryo.
'memory'
        rdd storage via persist, cache in JVM heap => spark.storage.memoryFraction
        rdd shuffle intermediate storage => spark.shuffle.memoryFraction
        default 60% rdd storage, 20% user program, 20% shuffle storage
        cache() is always MEMORY_ONLY, so better to persist in MEMORY_AND_DISK for large rdd objects which will definitely spill-over
        cache/persist MEMORY_ONLY_SER/MEMORY_AND_DISK_SER to use serialization to avoid GC pauses. serialization will group objects in  a giant buffer.
        JVM GC is dependent on number of objects than the size in bytes.
'hardware'
          spark.executor.memory, --executor-memory
          spark.executor.cores, --executor-cores
          YARN can limit number of executors --num-executors
          STANDALONE and MESOS will greedily consume all cores, limit by spark.cores.max
          spark does linear scaling with more cores and memory.
          'disk' best to use local disk rdd storage for a node
              YARN has it own way for this
              STANDALONE use SPARK_LOCAL_DIRS env variable in spark-env.sh in each node to point to list of local storage locations
          too much 'memory' can use large GC pauses in executors, < 64GB is ok
          MESOS and YARN makes sure to have small sized executors
          STANDALONE can have multiple executors per node by starting them inidividually.
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
SPARK SQL

spark works with structured data using SchemaRDD by
  loading it from json, hive, Parquet
  makes it available in spark program, for general sql clients
  merges with sql to join results.
  even from regular RDD by assigning schema (scala implicits) (rdd.registerTempTable("tweets"))

uses 'SchemaRDD', enhanced RDD to support a schema on RDD
rdd.get("column")

spark sql can be used with/without HIVE, the hadoop sql engine.
  1 'with Hive' default
      org.apache.spark:spark-hive_2.10 : 1.2.0
      supports HIVE tables, UDF, SerDes (serialization/deserialization), HQL (hive query language)
      entry => HiveContext
  2 'without Hive'
      org.apache.spark:spark-sql_2.10 : 1.2.0
      subset of features given by 1
      entry => SQLContext

Spark SQL is based on HiveQL

rdd = hiveCtx.jsonFile("tweets.json").
rdd.registerTempTable("tweets")
topTweets = hiveCtx.sql("select * from tweets order by retweetcount limit 10")
val textsRDD =  topTweets.map(row => row.getString(0))

SchemaRDD similar to jdbc resultset, now upgrading to 'DataFrames' in future Spark version.

'caching'
hiveCtx.cacheTable("tweets")// stores in columnar format, transient until driver programs exits.
sql
  CACHE TABLE tweets
  UNCACHE TABLE tweets

'jdbc server'
Spark can run a sql server that will provide other client tools to access SchemaRDD
sbin/start-thriftserver.sh => localhost:10000
Beeline is the packaged jdbc client, gives a sql shell.

 'UDF' //tbd
 'performance' //tbd

 //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 SPARK STREAMING

DStream -> discretized stream of RDDs over a time interval built from sources like Kafka, Flume or HDFS.
'Transformations' => yield new DStreams,
'output operations' => write the data

SAMPLE
to listen on socket 7777 and filter out lines containing ERROR
      val ssc = new StreaminContext(conf, Seconds(1)) // 1 sec batch
      val lines  = ssc.socketTextStream("localhost",7777)
      val errors = lines.filter( x => x.contains("ERROR"));
      errors.print() //output action

      ssc.start() //will start tasks for consuming and creating RDDs
      ssc.awaitTermination()

step 0: streamingContext starts 2 tasks
step 1: one long receiver task to collect data , converts into RDDs in one executor
step 2: RDDs are cached into another executor where the real short tasks are run like transformations and actions are done.
step 4: 'checkpointing' replicates ~4-5 batches of data for fault tolerance in HDFS, S3

'points'
Once a context has been started, no new streaming computations can be set up or added to it.
Once a context has been stopped, it cannot be restarted.
Only one StreamingContext can be active in a JVM at the same time.
stop() on StreamingContext also stops the SparkContext. To stop only the StreamingContext, set the optional parameter of stop() called stopSparkContext to false.
A SparkContext can be re-used to create multiple StreamingContexts, as long as the previous StreamingContext is stopped (without stopping the SparkContext) before the next StreamingContext is created.

Every input DStream (except file stream, discussed later in this section) is associated with a 'Receiver'
      Receiver is long running task to collect data
      Processors are short-lived tasks to work on data
      use 'local[N]' where N > number of receivers, when running local streaming samples,bcos 'local' will local the 1 main thread just for receiving.
      'Reliable Receiver' - A reliable receiver correctly sends acknowledgment to a reliable source when the data has been received and stored in Spark with replication.
      'Unreliable Receiver' - An unreliable receiver does not send acknowledgment to a source.
      This can be used for sources that do not support acknowledgment, or even for reliable sources when one does not want or need to go into the complexity of acknowledgment.

Spark Streaming provides two categories of built-in streaming sources.
    'Basic sources': Sources directly available in the StreamingContext API. Examples: file systems, socket connections, and Akka actors.
    'Advanced sources': Sources like Kafka, Flume, Kinesis, Twitter, etc. are available through extra utility classes.
    'samples'
        streamingContext.fileStream[KeyClass, ValueClass, InputFormatClass](dataDirectory)
        streamingContext.actorStream(actorProps, actor-name)
        streamingContext.queueStream(queueOfRDDs)

 STATELESS TRANSFORMATION
 The transformations are scoped within the RDD of a single batch.
 Similar to basic RDD, map(), filter(), groupbyKey, reducebyKey
 Joins can be done on other RDDs
  'sample'
         val spamInfoRDD = ssc.sparkContext.newAPIHadoopRDD(...) // RDD containing spam information
         val cleanedDStream = wordCounts.transform(rdd => {
           rdd.join(spamInfoRDD).filter(...) // join data stream with spam information to do data cleaning
           ...
         })
dstreams can also be joined (right, left, outer..)
      val stream1: DStream[String, String] = ...
      val stream2: DStream[String, String] = ...
      val joinedStream = stream1.join(stream2)

      val windowedStream1 = stream1.window(Seconds(20))
      val windowedStream2 = stream2.window(Minutes(1))
      val joinedStream = windowedStream1.join(windowedStream2)

STATEFUL TRANSFORMATION
https://github.com/apache/spark/blob/master/examples/src/main/scala/org/apache/spark/examples/streaming/StatefulNetworkWordCount.scala
The 'updateStateByKey' operation allows you to maintain arbitrary state while continuously updating it with new information.
To use this, you will have to do two steps.
      Define the state - The state can be an arbitrary data type.
      Define the state update function - Specify with a function how to update the state using the previous state and the new values from an input stream.
'sample'
      def updateFunction(newValues: Seq[Int], runningCount: Option[Int]): Option[Int] = {
          val newCount = ...  // add the new values with the previous running count to get the new count
          Some(newCount)
      }
      val runningCounts = pairs.updateStateByKey[Int](updateFunction _) //pairs is word occurence pair before reducing => (word, 1)

WINDOW OPERATIONS
apply transformations over a sliding window of data.
'window length' - The duration of the window,  say 3 batches
'sliding interval' - The interval at which the window operation is performed, say 2 batches
so the common overlay is 3-2=1 batch between windows
      'sample' // Reduce last 30 seconds of data, every 10 seconds
      val windowedWordCounts = pairs.reduceByKeyAndWindow((a:Int,b:Int) => (a + b), Seconds(30), Seconds(10))

1 'window(windowLength, slideInterval)' =>
         Return a new DStream which is computed based on windowed batches of the source DStream.
2 'countByWindow(windowLength, slideInterval)' =>
         Return a sliding window count of elements in the stream.
3 'reduceByWindow(func, windowLength, slideInterval)' =>
         Return a new single-element stream, created by aggregating elements in the stream over a sliding interval using func.
         The function should be associative so that it can be computed correctly in parallel.
4 'reduceByKeyAndWindow(func, windowLength, slideInterval, [numTasks])' =>
         When called on a DStream of (K, V) pairs, returns a new DStream of (K, V) pairs where the values for each key are aggregated using the given reduce function func over batches in a sliding window.
         Note: By default, this uses Sparks default number of parallel tasks (2 for local mode, and in cluster mode the number is determined by the config property spark.default.parallelism) to do the grouping.
         You can pass an optional numTasks argument to set a different number of tasks.
5 'reduceByKeyAndWindow(func, invFunc, windowLength, slideInterval, [numTasks])' =>
         A more efficient version of the above reduceByKeyAndWindow() where the reduce value of each window is calculated incrementally using the reduce values of the previous window.
         This is done by reducing the new data that enters the sliding window, and “inverse reducing” the old data that leaves the window.
         An example would be that of “adding” and “subtracting” counts of keys as the window slides.
         However, it is applicable only to “invertible reduce functions”, that is, those reduce functions which have a corresponding “inverse reduce” function (taken as parameter invFunc).
         Like in reduceByKeyAndWindow, the number of reduce tasks is configurable through an optional argument. Note that checkpointing must be enabled for using this operation.
6 'countByValueAndWindow(windowLength, slideInterval, [numTasks])' =>
         When called on a DStream of (K, V) pairs, returns a new DStream of (K, Long) pairs where the value of each key is its frequency within a sliding window.
         Like in reduceByKeyAndWindow, the number of reduce tasks is configurable through an optional argument.


OUTPUT OPERATIONS
1 'print'
   => Prints the first ten elements of every batch of data in a DStream on the driver node running the streaming application.
2 'saveAsTextFiles(prefix, [suffix])'
   => Save this DStream contents as text files.
         The file name at each batch interval is generated based on prefix and suffix:  'prefix-TIME_IN_MS[.suffix]'
3 'saveAsObjectFiles(prefix, [suffix])'
	=> Save this DStreams contents as SequenceFiles of serialized Java objects.
         The file name at each batch interval is generated based on prefix and suffix: "prefix-TIME_IN_MS[.suffix]".
4 'saveAsHadoopFiles(prefix, [suffix])'
	=> Save this DStreams contents as Hadoop files.
5 'foreachRDD(func)'
	=> The most generic output operator that applies a function, func, to each RDD generated from the stream.
         This function should push the data in each RDD to an external system, such as saving the RDD to files, or  writing it over the network to a database.
         Note that the function func is executed in the driver process running the streaming application, and will usually have RDD actions in it that will force the computation of the streaming RDDs.

         'caution '
         dstream.foreachRDD { rdd => //runs on driver
           rdd.foreachPartition { partitionOfRecords => // runs on executors
             // ConnectionPool is a static, lazily initialized pool of connections
             val connection = ConnectionPool.getConnection()
             partitionOfRecords.foreach(record => connection.send(record))
             ConnectionPool.returnConnection(connection)  // return to the pool for future reuse
           }
         }

CACHING
DStreams generated by window-based operations are automatically persisted in memory, without the developer calling 'persist()'
For input streams that receive data over the network (such as, Kafka, Flume, sockets, etc.), the default persistence level is set to replicate the data to 'two nodes' for fault-tolerance.

CHECKPOINTING
'Metadata checkpointing'
=> Saving of the information defining the streaming computation to fault-tolerant storage like HDFS.
      This is used to recover from failure of  driver node. It includes
            'Configuration' - The configuration that was used to create the streaming application.
            'DStream operations' - The set of DStream operations that define the streaming application.
            'Incomplete batches' - Batches whose jobs are queued but have not completed yet.
'Data checkpointing'
=> Saving of the generated RDDs to reliable storage. This is necessary in some 'stateful transformations' that combine data across multiple batches.
5 - 10 sliding intervals of a DStream is a good setting to try.
'sample'
            def functionToCreateContext(): StreamingContext = {
                val ssc = new StreamingContext(...)   // new context
                val lines = ssc.socketTextStream(...) // create DStreams and rdd processing
                ...
                ssc.checkpoint(checkpointDirectory)   // set checkpoint directory
                ssc
            }
            val context = StreamingContext.getOrCreate(checkpointDirectory, functionToCreateContext _)
            context. ...// Do additional setup on context that needs to be done, irrespective of whether it is being started or restarted

            context.start()
            context.awaitTermination()

MONITORING
Spark web UI shows an additional Streaming tab
Processing Time - The time to process each batch of data.
Scheduling Delay - the time a batch waits in a queue for the processing of previous batches to finish.

PERFORMANCE TUNING
1 Reduce the processinfg time by using the cluster resources well

   1.1 'data receive parallelism'
          increase the number of dstreams, say for eg, instead of 1 dstream reading 2 topics, make 2 dstreams on 2 machines, then union them.
          play with 'spark.streaming.blockInterval', data gets chunked ito blocks before consumed by spark. For eg block interval of 200 ms will create 10 tasks per 2 second batches. Make sure all cores are used. Minimum 50ms.

   1.2 'data processing parallelism'

            1.2.1 'Input data serialization':
            By default, the input data received through Receivers is stored in the executors’ memory with 'StorageLevel.MEMORY_AND_DISK_SER_2'.
            That is, the data is serialized into bytes to reduce GC overheads, and replicated for tolerating executor failures.
            Also, the data is kept first in memory, and spilled over to disk only if the memory is insufficient to hold all of the input data necessary for the streaming computation.
            This serialization obviously has overheads – the receiver must deserialize the received data and re-serialize it using Spark’s serialization format.
            try not using serialization if low batch size and no window operations.

            1.2.2 'Persisted RDDs serialization':
            RDDs generated by streaming computations may be persisted in memory.
            For example, window operations persist data in memory as they would be processed multiple times.
            However, unlike the Spark Core default of StorageLevel.MEMORY_ONLY, persisted RDDs generated by streaming computations are persisted with StorageLevel.MEMORY_ONLY_SER (i.e. serialized) by default to minimize GC overheads.

            1.2.3 'tasks launch overheads'
            Task Serialization => Using Kryo serialization for serializing tasks can reduce the task sizes, and therefore reduce the time taken to send them to the slaves.
            Execution mode => Running Spark in Standalone mode or coarse-grained Mesos mode leads to better task launch times than the fine-grained Mesos mode.

2 'Set right batch size'
 Start with conservative batch interval (say, 5-10 seconds) and a low data rate.
 To verify whether the system is able to keep up with the data rate, you can check the value of the end-to-end delay experienced by each processed batch
  - (either look for “Total delay” in Spark driver log4j logs, or use the StreamingListener interface).
 If the delay is maintained to be comparable to the batch size, then system is stable.
 Otherwise, if the delay is continuously increasing, it means that the system is unable to keep up and it therefore unstable.
 Once you have an idea of a stable configuration, you can try increasing the data rate and/or reducing the batch size.
 Note that a momentary increase in the delay due to temporary data rate increases may be fine as long as the delay reduces back to a low value (i.e., less than batch size).

3 'memory tuning'
For example, if you want to use a window operation on the last 10 minutes of data, then your cluster should have sufficient memory to hold 10 minutes worth of data in memory
3.1 'Tuning GC overheads':
         3.1.1 'Persistence Level of DStreams'
         => As mentioned earlier in the Data Serialization section, the input data and RDDs are by default persisted as serialized bytes.
               This reduces both the memory usage and GC overheads, compared to deserialized persistence.
               Enabling Kryo serialization further reduces serialized sizes and memory usage.
               Further reduction in memory usage can be achieved with compression (see the Spark configuration spark.rdd.compress), at the cost of CPU time.
         3.1.2 'Clearing old data:'
         => By default, all input data and persisted RDDs generated by DStream transformations are automatically cleared.
         Spark Streaming decides when to clear the data based on the transformations that are used.
         For example, if you are using a window operation of 10 minutes, then Spark Streaming will keep around the last 10 minutes of data, and actively throw away older data.
         Data can be retained for a longer duration (e.g. interactively querying older data) by setting streamingContext.remember.

         3.1.3 'CMS Garbage Collector':
         => Use of the concurrent mark-and-sweep GC is strongly recommended for keeping GC-related pauses consistently low.
         Even though concurrent GC is known to reduce the overall processing throughput of the system, its use is still recommended to achieve more consistent batch processing times.
         Make sure you set the CMS GC on both the driver (using --driver-java-options in spark-submit) and the executors (using Spark configuration spark.executor.extraJavaOptions).

         3.1.4 'Other tips': To further reduce GC overheads, here are some more tips to try.
         Use Tachyon for off-heap storage of persisted RDDs. See more detail in the Spark Programming Guide.
         Use more executors with smaller heap sizes. This will reduce the GC pressure within each JVM heap.

FAULT TOLERANCE
Spark operates on data in fault-tolerant file systems like HDFS or S3. Hence, all of the RDDs generated from the fault-tolerant data are also fault-tolerant.
However, this is not the case for Spark Streaming as the data in most cases is received over the network (except when fileStream is used).
To achieve the same fault-tolerance properties for all of the generated RDDs, the received data is replicated among multiple Spark executors in worker nodes in the cluster (default replication factor is 2).
      'Data received and replicated' - This data survives failure of a single worker node as a copy of it exists on one of the other nodes.
      'Data received but buffered for replication' - Since this is not replicated, the only way to recover this data is to get it again from the source.
      'Failure of a Worker Node' - Any of the worker nodes running executors can fail, and all in-memory data on those nodes will be lost. If any receivers were running on failed nodes, then their buffered data will be lost.
      'Failure of the Driver Node' - If the driver node running the Spark Streaming application fails, then obviously the SparkContext is lost, and all executors with their in-memory data are lost.

      There are three types of guarantees that a system can provide under all possible operating conditions (despite failures, etc.)
      'At most once': Each record will be either processed once or not processed at all.
      'At least once': Each record will be processed one or more times. This is stronger than at-most once as it ensure that no data will be lost. But there may be duplicates.
      'Exactly once': Each record will be processed exactly once - no data will be lost and no data will be processed multiple times. This is obviously the strongest guarantee of the three.
