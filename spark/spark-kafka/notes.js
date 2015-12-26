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

3 'compute' runs inside the spark executor
keeps fetching the messages from kafka using 'MessageAndMetadata.messageHandler' to user-defined type, default being Tuple[key, value]
offset ranges are defined in advance on the driver, then read directly from Kafka by executors, the messages returned by a particular KafkaRDD are 'deterministic'
Safe to re-try a task if it fails. If a Kafka leader is lost, for instance, the compute method will just sleep for the amount of time defined by the refresh.leader.backoff.ms