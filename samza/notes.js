samza:
******
Gives a higher level abstrction around streams and resources
samza=kafka+ yarn
though kafka is not the messaging system, it can be DB updates, or log tails etc.
yarn gives the resource management

Samza is a stream processing framework with the following features:
-------------------------------------------------------------------
	'Simple API': 
		Unlike most low-level messaging system APIs, Samza provides a very simple callback-based “process message” API comparable to MapReduce.
	'Managed state': 
		Samza manages snapshotting and restoration of a stream processor’s state. 
		When the processor is restarted, Samza restores its state to a consistent snapshot. Samza is built to handle large amounts of state (many gigabytes per partition).
	'Fault tolerance': 
		Whenever a machine in the cluster fails, Samza works with YARN to transparently migrate your tasks to another machine.
	'Durability': 
		Samza uses Kafka to guarantee that messages are processed in the order they were written to a partition, and that no messages are ever lost.
	'Scalability': 
		Samza is partitioned and distributed at every level. Kafka provides ordered, partitioned, replayable, fault-tolerant streams. 
		YARN provides a distributed environment for Samza containers to run in.
	'Pluggable': 
		Though Samza works out of the box with Kafka and YARN, Samza provides a pluggable API that lets you run Samza with other messaging systems and execution environments.
	'Processor isolation': 
		Samza works with Apache YARN, which supports Hadoop’s security model, and resource isolation through Linux CGroups.

Samza’s main differentiators are:
--------------------------------
	Samza supports fault-tolerant local state. State can be thought of as tables that are split up and co-located with the processing tasks. 
	State is itself modeled as a stream. If the local state is lost due to machine failure, the state stream is replayed to restore it.
	Streams are ordered, partitioned, replayable, and fault tolerant.
	YARN is used for processor isolation, security, and fault tolerance.
	Jobs are decoupled: if one job goes slow and builds up a backlog of unprocessed messages, the rest of the system is not affected.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------
concepts:
*********
streams:
-------
Samza processes streams. A stream is composed of immutable messages of a similar type or category. 
For example, a stream could be all the clicks on a website, or all the updates to a particular database table, or all the logs produced by a service, or any other type of event data. 
Messages can be appended to a stream or read from a stream. A stream can have any number of consumers, and reading from a stream doesn’t delete the message (so each message is effectively broadcast to all consumers). 
Messages can optionally have an associated key which is used for partitioning

Jobs:
-----
A Samza job is code that performs a logical transformation on a set of input streams to append output messages to set of output streams.
If scalability were not a concern, streams and jobs would be all we need. 
However, in order to scale the throughput of the stream processor, we chop streams and jobs up into smaller units of parallelism: partitions and tasks.

1 Job -> 'X' input streams, 'Y' output streams

Partitions:
----------
Each stream is broken into one or more partitions. Each partition in the stream is a totally ordered sequence of messages.
Each message in this sequence has an identifier called the offset, which is unique per partition. 
The offset can be a sequential integer, byte offset, or string depending on the underlying system implementation.

1 stream has 'Z' partitions

Tasks:
------
A job is scaled by breaking it into multiple tasks. 
The task is the unit of parallelism of the job, just as the partition is to the stream. 
Each task consumes data from one partition for each of the job’s input streams.
A task processes messages from each of its input partitions sequentially, in the order of message offset. 
There is no defined ordering across partitions. This allows each task to operate independently. 
The YARN scheduler assigns each task to a machine, so the job as a whole can be distributed across many machines.
The number of tasks in a job is determined by the number of input partitions (there cannot be more tasks than input partitions, or there would be some tasks with no input).
However, you can change the computational resources assigned to the job (the amount of memory, number of CPU cores, etc.) to satisfy the job’s needs. 
The assignment of partitions to tasks never changes: if a task is on a machine that fails, the task is restarted elsewhere, still consuming the same stream partitions.

1 Task consumes from each partition of 'X' input streams of 1 job

Dataflow Graphs
---------------
We can compose multiple jobs to create a dataflow graph, where the nodes are streams containing data, and the edges are jobs performing transformations. 
This composition is done purely through the streams the jobs take as input and output. can be cyclic/acyclic.

Containers
-----------
Partitions and tasks are both logical units of parallelism—they don’t correspond to any particular assignment of computational resources (CPU, memory, disk space, etc). 
Containers are the unit of physical parallelism, and a container is essentially a Unix process (or Linux cgroup). 
Each container runs one or more tasks. 
The number of tasks is determined automatically from the number of partitions in the input and is fixed, but the number of containers (and the CPU and memory resources associated with them) is specified by the user at run time and can be changed at any time.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
quickie
********
'step-1'
git clone git://git.apache.org/samza-hello-samza.git hello-samza
cd hello-samza

'step-2 - install zk, kafka, samza'
bin/grid bootstrap 
//this command will download, install, and start ZooKeeper, Kafka, and YARN. 
//It will also check out the latest version of Samza and build it. All package files will be put in a sub-directory called “deploy” inside hello-samza’s root folder.
	hello-samza git:(master) bin/grid bootstrap
	Bootstrapping the system...
	EXECUTING: stop kafka
	Kafka is not installed. Run: bin/grid install kafka
	EXECUTING: stop yarn
	YARN is not installed. Run: bin/grid install yarn
	EXECUTING: stop zookeeper
	Zookeeper is not installed. Run: bin/grid install zookeeper
	EXECUTING: install zookeeper
	Downloading zookeeper-3.4.3.tar.gz...
	  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
	                                 Dload  Upload   Total   Spent    Left  Speed
	100 15.4M  100 15.4M    0     0  69159      0  0:03:53  0:03:53 --:--:-- 73566
	EXECUTING: install yarn
	Downloading hadoop-2.4.0.tar.gz...
	  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
	                                 Dload  Upload   Total   Spent    Left  Speed
	100  132M  100  132M    0     0   156k      0  0:14:28  0:14:28 --:--:--  365k
	EXECUTING: install kafka
	Downloading kafka_2.10-0.8.2.1.tgz...
	  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
	                                 Dload  Upload   Total   Spent    Left  Speed
	100 15.4M  100 15.4M    0     0   446k      0  0:00:35  0:00:35 --:--:--  580k
	EXECUTING: start zookeeper
	JMX enabled by default
	Using config: /home/vijayrc/Projs/VRC5/hello-samza/deploy/zookeeper/bin/../conf/zoo.cfg
	Starting zookeeper ... STARTED
	EXECUTING: start yarn
	starting resourcemanager, logging to /home/vijayrc/Projs/VRC5/hello-samza/deploy/yarn/logs/yarn-vijayrc-resourcemanager-vijayrc.out
	starting nodemanager, logging to /home/vijayrc/Projs/VRC5/hello-samza/deploy/yarn/logs/yarn-vijayrc-nodemanager-vijayrc.out

'step-3'
Open YARN in browser: http://localhost:8088/cluster

'step-4 : Make a YARN job package'
mvn clean package
mkdir -p deploy/samza
	tar -xvf ./target/hello-samza-0.9.1-dist.tar.gz -C deploy/samza

'step-5: start jobs'
job will consume a feed of real-time edits from Wikipedia, and produce them to a Kafka topic called “wikipedia-raw”. 
parse and publish stats into other topics

deploy/samza/bin/run-job.sh --config-factory=org.apache.samza.config.factories.PropertiesConfigFactory --config-path=file://$PWD/deploy/samza/config/wikipedia-feed.properties
deploy/samza/bin/run-job.sh --config-factory=org.apache.samza.config.factories.PropertiesConfigFactory --config-path=file://$PWD/deploy/samza/config/wikipedia-parser.properties
deploy/samza/bin/run-job.sh --config-factory=org.apache.samza.config.factories.PropertiesConfigFactory --config-path=file://$PWD/deploy/samza/config/wikipedia-stats.properties

monitor the topics
------------------
deploy/kafka/bin/kafka-console-consumer.sh  --zookeeper localhost:2181 --topic wikipedia-edits
{"is-talk":2,"bytes-added":5276,"edits":13,"unique-titles":13}
{"is-bot-edit":1,"is-talk":3,"bytes-added":4211,"edits":30,"unique-titles":30,"is-unpatrolled":1,"is-new":2,"is-minor":7}
{"bytes-added":3180,"edits":19,"unique-titles":19,"is-unpatrolled":1,"is-new":1,"is-minor":3}
{"bytes-added":2218,"edits":18,"unique-titles":18,"is-unpatrolled":2,"is-new":2,"is-minor":3}
------------------------------------------------------------------------------------------------------------------------------------------------------------------------

differentiators:
****************

1 The Stream Model
--------------------
Streams are the input and output to Samza jobs. Samza has a very strong model of a stream—it is more than just a simple message exchange mechanism. 
A stream in Samza is a partitioned, ordered-per-partition, replayable, multi-subscriber, lossless sequence of messages. 
Streams are not just inputs and outputs to the system, but also buffers that isolate processing stages from each other.
Strong stream model greatly simplifies the implementation of features in the Samza framework. 
Each job need only be concerned with its own inputs and outputs, and in the case of a fault, each job can be recovered and restarted independently. 
There is no need for central control over the entire dataflow graph.
The tradeoff we need to make for this stronger stream model is that messages are written to disk.

2 The State is local:
---------------------
Only the very simplest stream processing problems are stateless (i.e. can process one message at a time, independently of all other messages). Many stream processing applications require a job to maintain some state. For example:

	If you want to know how many events have been seen for a particular user ID, you need to keep a counter for each user ID.
	If you want to know how many distinct users visit your site per day, you need to keep a set of all user IDs for which you’ve seen at least one event today.
	If you want to join two streams (for example, if you want to determine the click-through rate of adverts by joining a stream of ad impression events with a stream of ad click events) you need to store the event from one stream until you receive the corresponding event from the other stream.
	If you want to augment events with some information from a database (for example, extending a page-view event with some information about the user who viewed the page), the job needs to access the current state of that database.

Key criteria is to keep state local to each node (so that queries don’t need to go over the network), and to make it robust to machine failures by replicating state changes to another stream.

Example:
--------
This approach is especially interesting when combined with database change capture. 
Take the example above, where you have a stream of page-view events including the ID of the user who viewed the page, and you want to augment the events with more information about that user. 
At first glance, it looks as though you have no choice but to query the user database to look up every user ID you see (perhaps with some caching). With Samza, we can do better.
Change capture means that every time some data changes in your database, you get an event telling you what changed.
If you have that stream of change events, going all the way back to when the database was created, you can reconstruct the entire contents of the database by replaying the stream. 
That changelog stream can also be used as input to a Samza job.
Now you can write a Samza job that takes both the page-view event and the changelog as inputs. 
You make sure that they are partitioned on the same key (e.g. user ID). 
Every time a changelog event comes in, you write the updated user information to the task’s local storage. 
Every time a page-view event comes in, you read the current information about that user from local storage. 
That way, you can keep all the state local to a task, and never need to query a remote database.

3 execution framework:
----------------------
YARN is good and pluggable, it can be replaced, but its awesome as of now. 
samza does not require it to compile.