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
'loading data into rdd'

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
'broadcast and accumulator'
val globalcount = sc.accumulator(0) // accumulator aggregates data from across all nodes,
custom accumulator is by extending AccumulatorParam

broadcast_var = sc.broadcast(some_var) //read-only shared variable across all nodes
normal variables passed in any transformation closure are sent as dedicated copies, sometimes multiple times.
broadcast is good for large variables to be passed in bittorrent like efficient mechanism.

pipe() is used to external programs written in languages like R

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
'cluster'

1 driver program main()
  => action
      => directed acyclic graph of transformations
        => spark scheduler launches a 'job' per action
        =>  job has 1-N 'stages'. stages are pipelined together, executed in order - 'serial'
         => stage has N 'tasks', tasks are 'parallel'
          => tasks are run on Y 'executors'(jvm) on Z 'nodes'(machines)

 driver program schedules tasks and manages executors.
 executors run tasks and store cached copies of RDD
driver: http://localhost:4040/

 'cluster managers'
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

.persist helps in the truncation of rdd execution pipelining by saving processed forms.
.cache is spark optimizing behind the scenes will reduce the stages
http://localhost:4040 on the driver machine helps in analyzing
