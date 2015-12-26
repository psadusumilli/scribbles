'Old Receiver Approach'
    At a high-level, the earlier Kafka integration worked with Write Ahead Logs (WALs) as follows:
        The Kafka data is continuously received by 'Kafka Receivers running in the Spark workers/executors'.
        This used the 'high-level consumer API of Kafka'.
        The received data is stored in Spark’s worker/executor memory as well as to the WAL (replicated on HDFS).
        The 'Kafka Receiver updated Kafka’s offsets to Zookeeper only after the data has been persisted to the log'.
        The information about the received data and its WAL locations is also stored reliably.
        On failure, this information is used to re-read the data and continue processing.
    'problems'
        2 phase commit: Executor memory + WAL.
        A message written to WAL, would not have sent a acknowledgenent to ZK, leading to duplicate processing

'New Direct Approach w/o WAL and Receivers'
        Instead of receiving the data continuously using Receivers and storing it in a WAL, we simply decide at the beginning of every batch interval what is the range of offsets to consume.
        Later, when each batch’s jobs are executed, the data corresponding to the offset ranges is read from Kafka for processing (similar to how HDFS files are read).
        Kafka 'Simple Consumer' is used.
        These offsets are also saved reliably (with checkpoints) and used to recompute the data to recover from failures.
        Direct API eliminates the need for both WALs and Receivers for Kafka, while ensuring that each Kafka record is effectively received by Spark Streaming exactly once.
        1:1 mapping with kafka and RDD partitions

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
APIs

'KafkaRDD'
    Recall that an RDD is defined by:
            A method to divide the work into partitions (getPartitions).
            A method to do the work for a given partition (compute).
            A list of parent RDDs. KafkaRDD is an input, not a transformation, so it has no parents.
            Optionally, a partitioner defining how keys are hashed. KafkaRDD doesn’t define one.
            Optionally, a list of preferred hosts for a given partition, in order to push computation to where the data is (getPreferredLocations).

    Takes in a cluster of Kafka of kafka brokers
    Maps 1 KafkaRDD/1 partition/1 topic/1 offset range


    methods:
    1'getPartitions' method of KafkaRDD takes each OffsetRange in the array and turns it into an RDD:
    1:1 correspondence between Kafka partition and RDD partition.
    offsetRanges can also be fed into a overloaded constructor

    2'getPreferredLocations' method uses the Kafka leader for the given partition as the preferred host.

    3 'compute' runs inside the spark executor process
    keeps fetching the messages from kafka using 'MessageAndMetadata.messageHandler' to user-defined type, default being Tuple[key, value]
    offset ranges are defined in advance on the driver, then read directly from Kafka by executors, the messages returned by a particular KafkaRDD are 'deterministic'
    Safe to re-try a task if it fails. If a Kafka leader is lost, for instance, the compute method will just sleep for the amount of time defined by the refresh.leader.backoff.ms

'DirectKafkaInputStream'

The KafkaRDD returned by KafkaUtils.createRDD is usable in batch jobs if you have existing code to obtain and manage offsets.
In most cases however, you’ll probably be using KafkaUtils.createDirectStream, which returns a DirectKafkaInputDStream.
Similar to an RDD, a DStream is defined by:

    A list of parent DStreams. Again, this is an input DStream, not a transformation, so it has no parents.
    A time interval at which the stream will generate batches. This stream uses the interval of the streaming context.
    A method to generate an RDD for a given time interval (compute)
