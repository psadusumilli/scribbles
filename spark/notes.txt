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

creation
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

'persist'
all RDD are calculated from scratch for every transformation in the chain which is costly.
calling 'rdd.persist(mode)' saves the RDD in memory or disk

MEMORY_ONLY => memory, low cpu, more space.
MEMORY_ONLY_SER => memory, saved in serialized format, high cpu, low space
MEMORY_AND_DISK => memory but spilled to disk, low cpu, more space.
MEMORY_AND_DISK_SER => memory but spilled to disk, high cpu, low space
DISK_ONLY => high cpu, low space

PairRDD => key value pairs
.keys, values, groupbyKey, reducebyKey, sortByKey, mapValues, combineByKey
join //pick only common keys
rightOuterJoin // 'a.b' take common keys + b keys
leftOuterJoin // 'a.b' take common keys + a keys
//find avg of values for each key

pairs.combineByKey{
    value => (value, 1),
    (acc: (Int, Int), value) => (acc._1+value, acc._2+1),
    (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
  }
