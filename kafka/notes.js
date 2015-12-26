#!/usr/bin/env bash

reference:
**********
http://kafka.apache.org/documentation.html
https://engineering.linkedin.com/kafka/benchmarking-apache-kafka-2-million-writes-second-three-cheap-machines
http://www.confluent.io/blog/how-to-choose-the-number-of-topicspartitions-in-a-kafka-cluster/



QUICKIE:
********

tar -xzf kafka_2.10-0.8.2.0.tgz
cd kafka_2.10-0.8.2.0
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties

#single-node:
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
bin/kafka-topics.sh --list --zookeeper localhost:2181
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test 
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning

#multi-node 
----------- 
config/server-1.properties:
    broker.id=1
    port=9093
    log.dir=/tmp/kafka-logs-1
 
config/server-2.properties:
    broker.id=2
    port=9094
    log.dir=/tmp/kafka-logs-2

bin/kafka-server-start.sh config/server-1.properties &
bin/kafka-server-start.sh config/server-2.properties &
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 --partitions 1 --topic my-replicated-topic
bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic my-replicated-topic
	topic:my-replicated-topic	PartitionCount:1	ReplicationFactor:3	Configs:
		Topic: my-replicated-topic	Partition: 0	Leader: 1	Replicas: 1,2,0	Isr: 1,2,0
	"leader" is the node responsible for all reads and writes for the given partition. Each node will be the leader for a randomly selected portion of the partitions.
	"replicas" is the list of nodes that replicate the log for this partition regardless of whether they are the leader or even if they are currently alive.
	"isr" is the set of "in-sync" replicas. This is the subset of the replicas list that is currently alive and caught-up to the leader.

produce messages to one broker 
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic my-replicated-topic

#test failover
--------------
ps -ef | grep server-1.properties
kill -9 <pid>

bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic my-replicated-topic
Topic:my-replicated-topic	PartitionCount:1	ReplicationFactor:3	Configs:
	Topic: my-replicated-topic	Partition: 0	Leader: 2	Replicas: 1,2,0	Isr: 2,0


---------------------------------------------------------------------------------------------------------------------
PRINCIPLES:
**********
'topic' is a logical unit for creation/consumption of messages
topic is made up of 'partitions', an append-only,immutable commit logs
'producers' select a partition to dump a message based on their distribution choice.
messages in partitions are retained based on retention policies
1 complete partition per server
all partitions are replicated across servers
1 partition in a server is leader handling all read/writes, others passively replicated for failover

'consumers' read from partitions using 'offset', just a counter on where they are in the log
consumers can read multiple times from same parition by resetting the offset
consumers are grouped together into groups

only one consumer is guranteed to receive a message in a group, no duplicate receipt
'point': if all consumers belong to same group, they are load balanced within group for every message
'broadcast': if all consumers belong to different groups , they are distributed across groups for every message

1 partition/1 consumer always -> strong ordering

Messages sent by a producer to a particular topic partition will be appended in the order they are sent. 
That is, if a message M1 is sent by the same producer as a message M2, and M1 is sent first, then M1 will have a lower offset than M2 and appear earlier in the log.
A consumer instance sees messages in the order they are stored in the log.
For a topic with replication factor N, we will tolerate up to N-1 server failures without losing any messages committed to the log.
there cannot be more consumer instances than partitions.

GIST
****
topic -> 
	'Parallelism' - N partitions [1 partition/server, 1 partition/consumer (strong ordering)]
	'Replication' - Each partition in N is replicated across N servers with one as leader for R/W, other just Read


---------------------------------------------------------------------------------------------------------------------
ARCHITECTURE
**************
Persistence:
-------------
The key fact about disk performance is that the throughput of hard drives has been diverging from the latency of a disk seek for the last decade. 
As a result the performance of linear writes on a JBOD configuration with six 7200rpm SATA RAID-5 array is about 600MB/sec but the performance of random writes is only about 100k/sec—a difference of over 6000X. 
These linear reads and writes are the most predictable of all usage patterns, and are heavily optimized by the operating system. 
A modern operating system provides read-ahead and write-behind techniques that prefetch data in large block multiples and group smaller logical writes into large physical writes. 

All disk reads and writes will go through the disk cache in RAM.
In-process memory cache like JVM heap suffer from big/many objects and GC, so pagecache looks better.
Disk cache will stay warm even if the service is restarted, whereas the in-process cache will need to be rebuilt in memory 

Constant time O(1)
------------------
Traditional messagsing systems use B-tree O(LogN) which coupled with disk speed (say 10ms a pop) is pretty slow.
A simple write-at-head, read-at-tail without blocking each other gives O(N)
help utlilize cheap slow-seek drives

Efficiency
----------
Once poor disk access patterns have been eliminated, there are two common causes of inefficiency in this type of system: 
	too many small I/O operations, and excessive byte copying.

'solution: batch copy - too many io ops'
To avoid this, our protocol is built around a "message set" abstraction that naturally groups messages together. 
This allows network requests to group messages together and amortize the overhead of the network roundtrip rather than sending a single message at a time. 
The server in turn appends chunks of messages to its log in one go, and the consumer fetches large linear chunks at a time.	

'solution: custom binary format - '
client and server share common binary format
The message log maintained by the broker is itself just a directory of files, each populated by a sequence of message sets that have been written to disk in the same format used by the producer and consumer.
Maintaining this common format allows optimization of the most important operation: network transfer of persistent log chunks. 
Modern unix operating systems offer a highly optimized code path for transferring data out of pagecache to a socket; in Linux this is done with the 'sendfile' system call.

To understand the impact of 'sendfile', it is important to understand the common data path for transfer of data from file to socket:
	The operating system reads data from the disk into pagecache in kernel space
	The application reads the data from kernel space into a user-space buffer
	The application writes the data back into kernel space into a socket buffer
	The operating system copies the data from the socket buffer to the NIC buffer where it is sent over the network

This is clearly inefficient, there are four copies and two system calls. 
Using sendfile, this re-copying is avoided by allowing the OS to send the data from pagecache to the network directly. 
So in this optimized path, only the final copy to the NIC buffer is needed.

We expect a common use case to be multiple consumers on a topic.
Using the zero-copy optimization above, data is copied into pagecache exactly once and reused on each consumption instead of being stored in memory and copied out to kernel space every time it is read. 
This combination of 'pagecache and sendfile' means that on a Kafka cluster where the consumers are mostly caught up you will see no read activity on the disks whatsoever as they will be serving data entirely from cache.


Compression:
-----------
To save network io cost, efficient compression requires compressing multiple messages together rather than compressing each message individually.
Kafka supports this by allowing recursive message sets. A batch of messages can be clumped together compressed and sent to the server in this form. 
This batch of messages will be written in compressed form and will remain compressed in the log and will only be decompressed by the consumer.
'gzip and snappy'

producer
--------
Producers always talk to Brokers who abstract everything like which partition is the leader, which server is alive.
But producer can do the partition mechanism and dump in any partition they want.
'Batching' is one of the big drivers of efficiency, and to enable batching the Kafka producer will attempt to accumulate data in memory and to send out larger batches in a single request. 
The batching can be configured to accumulate no more than a fixed number of messages and to wait no longer than some fixed latency bound (say 64k or 10 ms).

consumer:
---------
'Pull based' consuming giving control to consumers for batch pulls
advantage of a pull-based system is that it lends itself to aggressive batching of data sent to the consumer.
The deficiency of a naive pull-based system is that if the broker has no data the consumer may end up polling in a tight loop, effectively busy-waiting for data to arrive. 
To avoid this we have parameters in our pull request that allow the consumer request to block in a "long poll" waiting until data arrives 
(and optionally waiting until a given number of bytes is available to ensure large transfer sizes).

'No message acknowledgement' mechanism which leads to inefficiency and lost messages tracking. 
Just track the int offset number provided by consumer

A consumer can deliberately rewind back to an old 'offset' and re-consume data. 
This violates the common contract of a queue, but turns out to be an essential feature for many consumers
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Message Semantics
****************
In general, in any messaging system, the following Semantics are possible.
'At most once'—Messages may be lost but are never redelivered.
'At least once'—Messages are never lost but may be redelivered.
'Exactly once'—this is what people actually want, each message is delivered once and only once.

Kafka guarantees 'at-least-once' delivery by default.
But allows the user to implement 'at most once' delivery by disabling retries on the producer and committing its offset prior to processing a batch of messages. 
'Exactly-once delivery' requires co-operation with the destination storage system but Kafka provides the offset which makes implementing this straight-forward
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Replication
***********
'X' Topic can have 'Y' Partitions across 'Z' Brokers
For X, Y > Z
'Only one Leader handling R/W in Y partitions'
Leader chooses the ordering of values provided to it.
Followers are just like any consumer feeding from leader

For Kafka node liveness or being in 'sync' has two conditions
	A node must be able to maintain its session with ZooKeeper (via ZooKeeper heartbeat mechanism)
	If it is a slave it must replicate the writes happening on the leader and not fall "too far" behind
 
Quorum
------
A message is considered "committed" when all in sync  'ISR' replicas for that partition have applied it to their log.	
 
In general, for a quorum :
 Let say we have 2f+1 replicas. 
 If f+1 replicas must receive a message prior to a commit being declared by the leader, 
 and if we elect a new leader by electing the follower with the most complete log from at least f+1 replicas, then, with no more than f failures, 
 the leader is guaranteed to have all committed messages. 
 This is because among any f+1 replicas, there must be at least one replica that contains all committed messages.

 To tolerate 1 failure requires 3 copies of the data, 
 and to tolerate 2 failures requires 5 copies of the data. 
 In our experience having only enough redundancy to tolerate a single failure is not enough for a practical system, 
 but doing every write five times, with 5x the disk space requirements and 1/5th the throughput, is not very practical for large volume data problems. 

 'ISR'
 Kafka takes a slightly different approach to choosing its quorum set. 
 Instead of majority vote, Kafka dynamically maintains a set of in-sync replicas (ISR) that are caught-up to the leader. 
 Only members of this set are eligible for election as leader. 
 A write to a Kafka partition is not considered committed until all in-sync replicas have received the write. 
 This ISR set is persisted to ZooKeeper whenever it changes. 
 Because of this, any replica in the ISR is eligible to be elected leader.
 This is an important factor for Kafka usage model where there are many partitions and ensuring leadership balance is important. 
 With this ISR model and 'f+1' replicas, a Kafka topic can tolerate 'f' failures without losing committed messages.
 
 'isr failures'
 A failed ISR must fully re-sync again even if it lost unflushed data in its crash.
 In case of all ISR failures
	 1 Wait for a replica in the ISR to come back to life and choose this replica as the leader (hopefully it still has all its data).
	 2 Choose the first replica (not necessarily in the ISR) that comes back to life as the leader.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Log Compaction
***************
ensures that Kafka will always retain at least the 'last known value for each message key' within the log of data for a single topic partition.
Log compaction is a mechanism to give finer-grained per-record retention, rather than the coarser-grained time-based retention. 
The idea is to selectively remove records where we have a more recent update with the same primary key. 
This way the log is guaranteed to have at least the last state for each key.
This retention policy can be set per-topic, so a single cluster can have some topics where retention is enforced by size or time and other topics where retention is enforced by compaction.

compaction happens at older offsets at tail end of log.
if say 10, 21, 32 got compacted, 32 will be returned for any call to 10|21|32

Compaction also allows for deletes. A message with a key and a null payload will be treated as a delete from the log. 
The compaction is done in the background by periodically recopying log segments. 

Cleaning does not block reads and can be throttled to use no more than a configurable amount of I/O throughput to avoid impacting producers and consumers

The log cleaner is disabled by default. To enable it set the server config
  log.cleaner.enable=true
This will start the pool of cleaner threads. To enable log cleaning on a particular topic you can add the log-specific property
  log.cleanup.policy=compact
This can be done either at topic creation time or using the alter topic command.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Producer API:
*************

Producer API that wraps the 2 low-level producers
	1 kafka.producer.SyncProducer
	2 kafka.producer.async.AsyncProducer.

	class Producer {
		
	  /* Sends the data, partitioned by key to the topic using either the */
	  /* synchronous or the asynchronous producer */
	  public void send(kafka.javaapi.producer.ProducerData<K,V> producerData);

	  /* Sends a list of data, partitioned by key to the topic using either */
	  /* the synchronous or the asynchronous producer */
	  public void send(java.util.List<kafka.javaapi.producer.ProducerData<K,V>> producerData);

	  /* Closes the producer and cleans up */	
	  public void close();

	}	

Goals:
-------
1 'batching'
handle queueing/buffering of multiple producer requests and asynchronous dispatch of the batched data -
kafka.producer.Producer provides the ability to batch multiple produce requests (producer.type=async), before serializing and dispatching them to the appropriate kafka broker partition. 
The size of the batch can be controlled by a few config parameters. 

As events enter a queue, they are 'buffered in a queue', until either 'queue.time or batch.size' is reached.
'A background thread (kafka.producer.async.ProducerSendThread) dequeues' the batch of data and lets the kafka.producer.
'EventHandler' serialize and send the data to the appropriate kafka broker partition. 
A custom event handler can be plugged in through the event.handler config parameter. 
At various stages of this producer queue pipeline, it is helpful to be able to 'inject callbacks', either for plugging in custom logging/tracing code or custom monitoring logic. 
This is possible by implementing the 'kafka.producer.async.CallbackHandler' interface and setting callback.handler config parameter to that class.

2 'encoding'
handles the serialization of data through a user-specified Encoder -
interface Encoder<T> {
  public Message toMessage(T data);
}
The default is the no-op kafka.serializer.DefaultEncoder

2 'partitioning'
provides software load balancing through an optionally user-specified Partitioner -
The routing decision is influenced by the kafka.producer.Partitioner.

interface Partitioner<T> {
   int partition(T key, int numPartitions);
}
The partition API uses the key and the number of available broker partitions to return a 'partition id'. 
This id is used as an index into a sorted list of broker_ids and partitions to pick a broker partition for the producer request.
The default partitioning strategy is 'hash(key)%numPartitions'. 
If the key is null, then a random broker partition is picked. A custom partitioning strategy can also be plugged in using the partitioner.class config parameter.

Consumer API
*************
2 levels of consumer APIs. 
	1 
	The low-level "simple" API maintains a connection to a single broker and has a close correspondence to the network requests sent to the server. 
	This API is completely stateless, with the offset being passed in on every request, allowing the user to maintain this metadata however they choose.
	2
	The high-level API hides the details of brokers from the consumer and allows consuming off the cluster of machines without concern for the underlying topology. 
	It also maintains the state of what has been consumed. 
	The high-level API also provides the ability to subscribe to topics that match a filter expression 

	ConsumerConnector connector = Consumer.create(consumerConfig);
	interface ConsumerConnector {
	  /**
	   * This method is used to get a list of KafkaStreams, which are iterators over
	   * MessageAndMetadata objects from which you can obtain messages and their
	   * associated metadata (currently only topic).
	   *  Input: a map of <topic, #streams>
	   *  Output: a map of <topic, list of message streams>
	   */
	  public Map<String,List<KafkaStream>> createMessageStreams(Map<String,Int> topicCountMap);

	  /**
	   * You can also obtain a list of KafkaStreams, that iterate over messages
	   * from topics that match a TopicFilter. (A TopicFilter encapsulates a
	   * whitelist or a blacklist which is a standard Java regex.)
	   */
	  public List<KafkaStream> createMessageStreamsByFilter(
	      TopicFilter topicFilter, int numStreams);

	  /* Commit the offsets of all messages consumed so far. */
	  public commitOffsets()
	  
	  /* Shut down the connector */
	  public shutdown()
	}

This API is centered around iterators, implemented by the KafkaStream class. 
Each 'KafkaStream' represents the stream of messages from one or more partitions on one or more servers. 
Each stream is used for single threaded processing, so the client can provide the number of desired streams in the create call. 
Thus a stream may represent the merging of multiple server partitions (to correspond to the number of processing threads), but each partition only goes to one stream.
So
	topicCountMap of t1-2, t2->3 gives a stream map of t1->2 streams, t2->3 streams
	1 stream processed by 1 thread

The createMessageStreams call registers the consumer for the topic, which results in rebalancing the consumer/broker assignment. 
The 'API encourages creating many topic streams in a single call in order to minimize this rebalancing'. 
The createMessageStreamsByFilter call (additionally) registers watchers to discover new topics that match its filter. 
Note that each stream that createMessageStreamsByFilter returns may iterate over messages from multiple topics (i.e., if multiple topics are allowed by the filter).

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Network Layer
**************
The network layer is a fairly straight-forward 'NIO server'. 
The sendfile implementation is done by giving the 'MessageSet' interface a writeTo method. 
This allows the file-backed message set to use the more efficient 'transferTo' implementation instead of an in-process buffered write. 
The threading model is a single acceptor thread and N processor threads which handle a fixed number of connections each.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Messages|Log
*************
Messages consist of a fixed-size header and variable length opaque byte array payload. 
The header contains a format version and a CRC32 checksum to detect corruption or truncation.
The MessageSet interface is simply an iterator over messages with specialized methods for bulk reading and writing to an NIO Channel.
On-disk format of a message

Mesage of 'n 'size would contain
message length : 4 bytes (value: 1+4+n) 
"magic" value  : 1 byte
crc            : 4 bytes
payload        : n bytes

Each log file is named with the offset of the first message it contains. So the first file created will be 00000000000.kafka.
Each additional file will have an integer name roughly 'S' bytes from the previous file where 'S' is the max log file size given in the configuration.

1 Message-id
-------------
The use of the message offset as the message id is unusual. 
Our original idea was to use a GUID generated by the producer, and maintain a mapping from GUID to offset on each broker. 
But since a consumer must maintain an ID for each server, the global uniqueness of the GUID provides no value. 
Furthermore the complexity of maintaining the mapping from a random id to an offset requires a heavy weight index structure which must be synchronized with disk, essentially requiring a full persistent random-access data structure. 
Thus to simplify the lookup structure we decided to use a simple per-partition atomic counter which could be coupled with the partition id and node id to uniquely identify a message

2 Writes
--------
The log allows serial appends which always go to the last file. This file is rolled over to a fresh file when it reaches a configurable size (say 1GB). 
The log takes two configuration parameter .
	'M' which gives the number of messages to write before forcing the OS to flush the file to disk, 
	'S' which gives a number of seconds after which a flush is forced. 
This gives a durability guarantee of losing at most M messages or S seconds of data in the event of a system crash.

3 Reads
-------
Reads are done by giving the 64-bit logical offset of a message and an S-byte max chunk size. 
This will return an iterator over the messages contained in the S-byte buffer. S is intended to be larger than any single message, 
but in the event of an abnormally large message, the read can be retried multiple times, each time doubling the buffer size, until the message is read successfully
The following is the format of the results sent to the consumer.

	MessageSetSend (fetch result)
	------------------------------
	total length     : 4 bytes
	error code       : 2 bytes
	message 1        : x bytes
	...
	message n        : x bytes

	MultiMessageSetSend (multiFetch result)
	----------------------------------------
	total length       : 4 bytes
	error code         : 2 bytes
	messageSetSend 1
	...
	messageSetSend n

4 deletes
----------
Data is deleted one log segment at a time. 
The log manager allows pluggable delete policies to choose which files are eligible for deletion. 
The current policy deletes any log with a modification time of more than N days ago, though a policy which retained the last N GB could also be useful. 
To avoid locking reads while still allowing deletes that modify the segment list we use a 'copy-on-write' style segment list implementation that provides consistent views to allow a binary search to proceed on an immutable static snapshot view of the log segments while deletes are progressing.	
copy-on-write is a copy made before modification, so that reads can use it until modification is done.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Distribution:
*************

1 How many partitions?
---------------------
A rough formula for picking the number of partitions is based on throughput. 
For a single partition, if throughput is 'p' and consumption is 'c', target cluster throughput is 't'
then you need to have at least 'max(t/p, t/c)' partitions
The per-partition throughput 'p' that one can achieve on the producer depends on configurations such as the 
	'batching size, compression codec, type of acknowledgement, replication factor'.

Though number of partitions can be increased in future, it causes issues because messages were previously partitioned using 'hash(key)%no_of_partitions'
Now on increasing, they get disordered, so its best to add a little more partitions than required to anticipate load for 3-4 ysrs ahead.

'More Partitions Requires More Open File Handles'
Each partition maps to a directory in the file system in the broker. Within that log directory, there will be two files (one for the index and another for the actual data) per log segment. 
Currently, in Kafka, each broker opens a file handle of both the index and the data file of every log segment. 
So, the more partitions, the higher that one needs to configure the open file handle limit in the underlying operating system. 

'more partitions leads to unavailability'
However, when a broker is shut down uncleanly (e.g., kill -9), the observed unavailability could be proportional to the number of partitions. 
Suppose that a broker has a total of 2000 partitions, each with 2 replicas. Roughly, this broker will be the leader for about 1000 partitions. 
When this broker fails uncleanly, all those 1000 partitions become unavailable at exactly the same time. 
Suppose that it takes 5 ms to elect a new leader for a single partition. It will take up to 5 seconds to elect the new leader for all 1000 partitions. 
So, for some partitions, their observed unavailability can be 5 seconds plus the time taken to detect the failure.

'more partition leads to more e2e latency'
a published message is accepted only if all ISRs are committed through replication, leading to more e2e latency.
As a rule of thumb, if you care about latency, 
its probably a good idea to limit the number of partitions per broker 
	to '100 x b x r', 
where 'b' is the number of brokers in a Kafka cluster and 
	  'r' is the replication factor.

'more partitions leads to more client memory'
'producers'
If one increases the number of partitions, message will be accumulated in more partitions in the producer. 
The aggregate amount of memory used may now exceed the configured memory limit. When this happens, the producer has to either block or drop any new message, neither of which is ideal. 
To prevent this from happening, one will need to reconfigure the producer with a larger memory size.	  
'consumer'
The consumer fetches a batch of messages per partition. The more partitions that a consumer consumes, the more memory it needs. 

2 How many consumers?
----------------------
1 consumer (even in a group) can only consume messages from 1 partition (strong ordering)
consumer group has a unique id shared among them.


3 offset-tracking
-----------------
Older kafka used to store offsets in ZooKeeper.

The high-level consumer tracks the maximum offset it has consumed in each partition and periodically commits its offset vector so that it can resume from those offsets in the event of a restart. 
Kafka provides the option to 'store all the offsets for a given consumer group in a designated broker (for that group) called the offset manager'
The high-level consumer handles this automatically. If you use the simple consumer you will need to manage offsets manually. 
This is currently unsupported in the Java simple consumer which can only commit or fetch offsets in ZooKeeper.

When the offset manager receives an 'OffsetCommitRequest', it appends the request to a special compacted Kafka topic named '__consumer_offsets'. 
The offset manager sends a successful offset commit response to the consumer only after all the replicas of the offsets topic receive the offsets.

4 ZooKeeper Directories
------------------------
{desc => znode dir --> has this}(ephemeral|permanent)
'Broker Node Registry' => /brokers/ids/[0...N] --> host:port (ephemeral node)
'Broker Topic Registry' => /brokers/topics/[topic]/[0...N] --> nPartions (ephemeral node)
'Consumer id registry' => /consumers/[group_id]/ids/[consumer_id] --> {"topic1": #streams, ..., "topicN": #streams} (ephemeral node)
'Consumer offset registry' => /consumers/[group_id]/offsets/[topic]/[broker_id-partition_id] --> offset_counter_value ((persistent node) //if stored in zookeeper
'Partition Owner registry' => /consumers/[group_id]/owners/[topic]/[broker_id-partition_id] --> consumer_node_id (ephemeral node) //consumer(group) owning partition

5 Consumer rebalancing algorithm
---------------------------------
The consumer rebalancing algorithms allows all the consumers in a group to come into consensus on which consumer is consuming which partitions. 
Consumer rebalancing is triggered on each addition or removal of both broker nodes and other consumers within the same group. 
For a given topic and a given consumer group, broker partitions are divided evenly among consumers within the group. 
A partition is always consumed by a single consumer.

Each consumer does the following during rebalancing:

   1. For each topic T that Ci subscribes to 
   2.   let PT be all partitions producing topic T
   3.   let CG be all consumers in the same group as Ci that consume topic T
   4.   sort PT (so partitions on the same broker are clustered together)
   5.   sort CG
   6.   let i be the index position of Ci in CG and let N = size(PT)/size(CG)
   7.   assign partitions from i*N to (i+1)*N - 1 to consumer Ci
   8.   remove current entries owned by Ci from the partition owner registry
   9.   add newly assigned partitions to the partition owner registry
        (we may need to re-try this until the original partition owner releases its ownership)


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Operations:
***********

1 Topics and partitions
------------------------
Topics are added and modified using the topic tool shown below, if topics are added dynamically with a message sent to a non-existent topic, default configs should be solid
 	> bin/kafka-topics.sh --zookeeper zk_host:port/chroot --create --topic my_topic_name --partitions 20 --replication-factor 3 --config x=y
 	> bin/kafka-topics.sh --zookeeper zk_host:port/chroot --alter --topic my_topic_name --partitions 40 

There are several impacts of the partition count. First 'each partition must fit entirely on a single server'. 
So if you have 20 partitions the full data set (and read and write load) will be handled by no more than 20 servers (no counting replicas

'To add configs:'	
	> bin/kafka-topics.sh --zookeeper zk_host:port/chroot --alter --topic my_topic_name --config x=y
'To remove a config:'
 	> bin/kafka-topics.sh --zookeeper zk_host:port/chroot --alter --topic my_topic_name --deleteConfig x
'And finally deleting a topic:'
 	> bin/kafka-topics.sh --zookeeper zk_host:port/chroot --delete --topic my_topic_name
'Topic deletion option is disabled by default. To enable it set the server config'
	delete.topic.enable=true

Kafka does not currently support reducing the number of partitions for a topic or changing the replication factor.

2 shutdown
-----------
'graceful shutdown'
The Kafka cluster will automatically detect any broker shutdown or failure and elect new leaders for the partitions on that machine. 
When a server is stopped gracefully it has two optimizations it will take advantage of:
'controlled.shutdown.enable=true'
 a)	It will sync all its logs to disk to avoid needing to do any log recovery when it restarts (i.e. validating the checksum for all messages in the tail of the log). 
 	Log recovery takes time so this speeds up intentional restarts.
 b)	It will migrate any partitions the server is the leader for to other replicas prior to shutting down. 
 	This will make the leadership transfer faster and minimize the time each partition is unavailable to a few milliseconds.
Balancing leadership

'crashes'
Whenever a broker stops or crashes leadership for that broker partitions transfers to other replicas. 
This means that by default when the broker is restarted it will only be a follower for all its partitions, meaning it will not be used for client reads and writes.
To avoid this imbalance, Kafka has a notion of 'preferred replicas'. 
If the list of replicas for a partition is 1,5,9 then node 1 is preferred as the leader to either node 5 or 9 because it is earlier in the replica list. You can have the Kafka cluster try to restore leadership to the restored replicas by running the command:

 > bin/kafka-preferred-replica-election.sh --zookeeper zk_host:port/chroot
Since running this command can be tedious you can also configure Kafka to do this automatically by setting the following configuration:
    auto.leader.rebalance.enable=true

3 mirroring
-----------
Different from a replication as offset numbers are retained.
muliple clusters can be mirrored into a single cluster

> bin/kafka-run-class.sh kafka.tools.MirrorMaker
       --consumer.config consumer-1.properties --consumer.config consumer-2.properties 
       --producer.config producer.properties --whitelist my-topic


4 checking consumer offsets
---------------------------
 > bin/kafka-run-class.sh kafka.tools.ConsumerOffsetChecker --zkconnect localhost:2181 --group test
Group           Topic                          Pid Offset          logSize         Lag             Owner
my-group        my-topic                       0   0               0               0               test_jkreps-mn-1394154511599-60744496-0
my-group        my-topic                       1   0               0               0               test_jkreps-mn-1394154521217-1a0be913-0


5 expanding cluster
--------------------
Adding servers to a Kafka cluster is easy, just assign them a unique broker id and start up Kafka on your new servers. 
However these new servers will not automatically be assigned any data partitions, so unless partitions are moved to them they wont be doing any work until new topics are created. So usually when you add machines to your cluster you will want to migrate some existing data to these machines.
The process of migrating data is manually initiated but fully automated. Under the covers what happens is that Kafka will add the new server as a follower of the partition it is migrating and allow it to fully replicate the existing data in that partition. When the new server has fully replicated the contents of this partition and joined the in-sync replica one of the existing replicas will delete their partitions data.

The partition reassignment tool can be used to move partitions across brokers. 
An ideal partition distribution would ensure even data load and partition sizes across all brokers. 
In 0.8.1, the partition reassignment tool does not have the capability to automatically study the data distribution in a Kafka cluster and move partitions around to attain an even load distribution. 
As such, the admin has to figure out which topics or partitions should be moved around.

The partition reassignment tool can run in 3 mutually exclusive modes -
--generate: In this mode, given a list of topics and a list of brokers, the tool generates a candidate reassignment to move all partitions of the specified topics to the new brokers. This option merely provides a convenient way to generate a partition reassignment plan given a list of topics and target brokers.
--execute: In this mode, the tool kicks off the reassignment of partitions based on the user provided reassignment plan. (using the --reassignment-json-file option). This can either be a custom reassignment plan hand crafted by the admin or provided by using the --generate option
--verify: In this mode, the tool verifies the status of the reassignment for all partitions listed during the last --execute. The status can be either of successfully completed, failed or in progress

6 shrinking cluster
-------------------
no special tool, use the above partition reassignment tool.

7 increasing replication
-------------------------
Increasing the replication factor of an existing partition is easy. 
Just specify the extra replicas in the custom reassignment json file and use it with the --execute option to increase the replication factor of the specified partitions.

> cat increase-replication-factor.json
{"version":1,
 "partitions":[{"topic":"foo","partition":0,"replicas":[5,6,7]}]}
Then, use the json file with the --execute option to start the reassignment process-
> bin/kafka-reassign-partitions.sh --zookeeper localhost:2181 --reassignment-json-file increase-replication-factor.json --execute
Current partition replica assignment


8 Datacenters
---------------
Some deployments will need to manage a data pipeline that spans multiple datacenters. 
Our recommended approach to this is to deploy a local Kafka cluster in each datacenter with application instances in each datacenter,
 interacting only with their local cluster and mirroring between clusters 
It is generally not advisable to run a single Kafka cluster that spans multiple datacenters over a high-latency link. 

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Kafka Configuration
********************

The most important 'producer' configurations control
	compression
	sync vs async production
	batch size (for async producers)
The most important 'consumer' configuration is the 
	fetch size

LinkedIn Server Config
--------------------------------------
		# Replication configurations
		num.replica.fetchers=4
		replica.fetch.max.bytes=1048576
		replica.fetch.wait.max.ms=500
		replica.high.watermark.checkpoint.interval.ms=5000
		replica.socket.timeout.ms=30000
		replica.socket.receive.buffer.bytes=65536
		replica.lag.time.max.ms=10000
		replica.lag.max.messages=4000

		controller.socket.timeout.ms=30000
		controller.message.queue.size=10

		# Log configuration
		num.partitions=8
		message.max.bytes=1000000
		auto.create.topics.enable=true
		log.index.interval.bytes=4096
		log.index.size.max.bytes=10485760
		log.retention.hours=168
		log.flush.interval.ms=10000
		log.flush.interval.messages=20000
		log.flush.scheduler.interval.ms=2000
		log.roll.hours=168
		log.retention.check.interval.ms=300000
		log.segment.bytes=1073741824

		# ZK configuration
		zookeeper.connection.timeout.ms=6000
		zookeeper.sync.time.ms=2000

		# Socket server configuration
		num.io.threads=8
		num.network.threads=8
		socket.request.max.bytes=104857600
		socket.receive.buffer.bytes=1048576
		socket.send.buffer.bytes=1048576
		queued.max.requests=16
		fetch.purgatory.purge.interval.requests=100
		producer.purgatory.purge.interval.requests=100	

	-Xms4g -Xmx4g -XX:PermSize=48m -XX:MaxPermSize=48m -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=35	

For reference, here are the stats on one of LinkedIn busiest clusters (at peak): 
	- 15 brokers - 15.5k partitions (replication factor 2) 
	- 400k messages/sec in - 70 MB/sec inbound, 400 MB/sec+ outbound 
The tuning looks fairly aggressive, but all of the brokers in that cluster have a 90% GC pause time of about 21ms, and they are doing less than 1 young GC per second.

Hardware
--------
We are using 'dual quad-core Intel Xeon machines with 24GB of memory.'
You need sufficient memory to buffer active readers and writers. 
You can do a back-of-the-envelope estimate of memory needs by assuming you want to be able to buffer for 30 seconds and compute your memory need as write_throughput*30.
The disk throughput is important. We have '8x7200 rpm SATA drives'. In general disk throughput is the performance bottleneck, and more disks is more better. 

OS
---
Linux or Solaris:
2 configurations that may be important:
	We upped the number of file descriptors since we have lots of topics and lots of connections.
	We upped the max socket buffer size to enable high-performance data transfer between data centers

Disks and Filesystem
----------------------
Gist: either app balancing or let RAID do balancing

We recommend using multiple drives to get good throughput and not sharing the same drives used for Kafka data with application logs or other OS filesystem activity to ensure good latency. 
As of 0.8 you can either RAID these drives together into a single volume or format and mount each drive as its own directory. 
Since Kafka has replication the redundancy provided by RAID can also be provided at the application level. This choice has several tradeoffs.
If you configure multiple data directories partitions will be assigned round-robin to data directories. 
Each partition will be entirely in one of the data directories. 
If data is not well balanced among partitions this can lead to load imbalance between disks.

RAID can potentially do better at balancing load between disks (although it doesnt always seem to) because it balances load at a lower level. 
The primary downside of RAID is that it is usually a big performance hit for write throughput and reduces the available disk space.

Another potential benefit of RAID is the ability to tolerate disk failures. 
However our experience has been that rebuilding the RAID array is so I/O intensive that it effectively disables the server, so this does not provide much real availability improvement.

Application vs. OS Flush Management
-----------------------------------

Kafka always immediately writes all data to the filesystem and supports the ability to configure the flush policy that controls when data is forced out of the OS cache and onto disk using the and flush. 
This flush policy can be controlled to force data to disk after a period of time or after a certain number of messages has been written. 
There are several choices in this configuration.
'We recommend using the default flush settings which disable application fsync entirely'. 
This means relying on the background flush done by the OS and Kafka own background flush.

Monitoring
----------
Kafka uses Yammer Metrics for metrics reporting in both the server and the client. This can be configured to report stats using pluggable stats reporters to hook up to your monitoring system.
The easiest way to see the available metrics to fire up jconsole and point it at a running kafka client or server; this will all browsing all metrics with JMX.