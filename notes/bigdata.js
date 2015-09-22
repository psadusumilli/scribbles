Big Data
#########

CHAP 1 -Introduction
********************
'Incremental Arch': crud complex systems
'Lambda Arch': wipe and start over - batch, speed, serving layers

A data system answers questions based on information that was acquired in the past up to the present.
query = func(all data)

'Desired properties'
--------------------
	fault tolerant - including human error
	latency - low read times and reasonable update times 
	scalable - just add machines
	minimal maintenance - complexity must be pushed outside core components into repeatable process (eg bulk indexer)
	extensible - easy to add features
	generalization - applicable to any domain
	ad-hoc queries must be supported
	debuggability - tracing issues 

'Incremental Arch issues'
------------------------
familiar complexity aka inherent complexity
	operational complexity - for eg: online compaction of unused data is resource-intensive, 
	eventual consistency complexity - network partitioning and data
	human error intolerance - constantly modified prone to errors

'lamda arch'
------------
	1 batch layer
		apply functions on raw data, produce batch views (remember couchdb map-reduce)
			function runBatchLayer():
				while(true):
				recomputeBatchViews()
		batch views are a bit outdated
		batch computations are written simple like single threaded apps
		scales by adding more resources

	2 serving layer
		A serving layer database supports batch updates and random reads. 
		Most notably, it doesn’t need to support random writes.	
		noDB, ElephantDB
	
	3 speed layer
		realtime views generated for data not present in batch

The Lambda Architecture in full is summarized by these three equations:
	batch_view = function (all_data)
	realtime_view = function(realtime_view, new_data)
	query = function (batch view, realtime view)
The batch/speed layer split gives you the flexibility to use the 'exact algorithm' on the batch layer and an 'approximate algorithm' on the speed layer. 
The batch layer repeatedly overrides the speed layer, so the approximation gets corrected and your system exhibits the property of eventual accuracy.

Trends
------
	batch processing - high throughput, high latency - hadoop
	realtime processing - high throughput, low latency - storm, spark
	serialization - type->byte->type for any language
	messaging - reliable data movement - kafka
	nosql dbs - all are unique

--------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAP 2|3 -Batch Layer-Data model
******************************
properties of data;
	'raw': 	Storing raw data is hugely valuable because you rarely know in advance all the questions you want answered. 
			By keeping the rawest data possible, you maximize your ability to obtain new insights, 
			whereas summarizing, overwriting, or deleting information limits what your data can tell you 
			(facebook-> friend, unfriend events -> then calculate derived data like current number of friends)
			some 'semantic normalization' (name/location standardization) can happen on data

	'immutable': 
			safe from human errors
			facts stored based on time dimension 
			can replay 

	'eternally true': 
			that is, a piece of data, once true, must always be true.		

fact-based
-----------
Is queryable at any time in its history
Tolerates human errors
Handles partial information
Has the advantages of both normalized (master) and denormalized forms (batch views)

graph schema
------------
	with nodes, edges and properties give the relations between facts
	implement an enforceable schema using a 'serialization framework'

serialization framework
-----------------------
	enforces the data schema across lanugages
	tools like Thrift, Avro etc can enforce some validation on required fields and types.
	rich validation would need to duplicated across multiple languages if done
	or validtion can be first stage in batch processing.

illustration with Thrift - no notes
	nodes, edges and properties modelled usinf thrift 'types,union and struct'
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAP 4|5- Batch Layer-Data Persistence
***************************************
Write in bulk, read many
must be scalable, immutable, compressible.
	
'key-value stores': 
		meant for random crud, does not work out for immutable data
'distributed filesystems' : linear storage of data across many machines. 
		The operations you can do with a distributed filesystem are often more limited than you can do with a regular filesystem. 
		For instance, you may not be able to write to the middle of a file or even modify a file at all after creation
		eg 'HDFS' 
			file split into datanodes (64-256 MB blocks) and namenodes to track 'block-file' mapping.
		vertical partitioning can help (splitting facts data by say person-age/date/)	

illustration with HDFS and Pail:
----------------------------------
	smaller supplier files leads to more map-reduce tasks 
	needs consolidation with a lib like 'Pail',a thin abstraction over files and folders from the dfs-datastores library
	Pail is just a Java library that uses the standard Hadoop API s. 
	It handles the low-level filesystem interaction, providing an API that isolates you from the complexity of Hadoop’s internals

The advantages of Pail for storing the master dataset

'Write'
--------
	1 'Efficient appends of newdata'
	 	Pail has a first-class interface for appending data and prevents you from performing invalid operations—something the raw HDFS API won’t do for you.
	2 'Scalable storage'
	 	The namenode holds the entire HDFS namespace in memory and can be taxed if the filesystem contains a vast number of small files. 
	 	Pail’s consolidate operator decreases the total number of HDFS blocks and eases the demand on the namenode.
'Read'
-------
	1 'Support for parallel processing'
	 	The number of tasks in a MapReduce job is determined by the number of blocks in the dataset. 
	 	Consolidating the contents of a pail lowers the number of required tasks and increases the efficiency of processing the data.

	2 'Ability to vertically partition data' 
		Output written into a pail is automatically partitioned with each fact stored in its appropriate directory. 
		This directory structure is strictly enforced for all Pail operations.
'both'
------
	1 'Tunable storage/processing costs'
	 	Pail has built-in support to coerce data into the format specified by the pail structure. 
	 	This coercion occurs automatically while performing operations on the pail.

	2 'Enforceable immutability'
		Because Pail is just a thin wrapper around files and folders, you can enforce immutability, 
		just as you can with HDFS directly, by setting the appropriate permissions.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAP 6|7 - Batch Layer
*********************
batch views computations:
---------------------------
1 -> recomputation 2 -> Incremental

'Performance' 
	1 Requires computational effort to process the entire master dataset. 
	2 Requires less computational resources but may generate much larger batch views
'Human-fault tolerance' 
	1 Extremely tolerant of human errors because the batch views are continually rebuilt 
	2 Doesn’t facilitate repairing errors in the batch views; repairs are ad hoc and may require estimates
'Generality'
	1 Complexity of the algorithm is addressed during precomputation, resulting in simple batch views and low-latency, on-the-fly processing. 
	2 Requires special tailoring; may shift complexity to on-the-fly query processing
'Conclusion' 
	1 Essential to supporting a robust data processing system 
	2 Can increase the efficiency of your system, but only as a supplement to recomputation algorithms

scalability
------------
system must be linearly scalable

map-reduce
----------
1 execute in a fully distributed fashion with no central point of contention.
2 is scalable: the map and reduce functions you provide are executed in parallel across the cluster.
3 handles the challenges of concurrency and assigning tasks to machines
4 fault tolerant - retries the jobs on another node, if it fails in one. The job must be 'deterministic' i.e; for given input, same output

'spark'
Spark’s computation model is 'resilient distributed datasets'
Spark isn’t any more general or scalable than MapReduce, 
but its model allows it to have much higher performance for algorithms that have to repeatedly iterate over the same dataset
(because Spark is able to cache that data in memory rather than read it from disk every time). 
Many machine-learning algorithms iterate over the same data repeatedly, making Spark particularly well suited for that use case.

low-level nature of map-reduce
------------------------------
1 multistep computation requires multiple jobs talking to each other only via file systems.
2 linking data sql joins are tedious
3 map-reduce not very good for modularizing code.

'pipe-diagrams' help in mapping flows as a series of computation steps, outside of map-reduce complexity
Jcascade is a implementation of pipe-diagrams

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAP 8 - Batch Layer - illustration
************************************
Three batch views:
 'Pageview counts by URL sliced by time'
	“What were the pageviews for each day over the past year?” 
	“How many pageviews have there been in the past 12 hours?”

 'Unique visitors by URL sliced by time'
 	“How many unique users frequented this domain in 2010?” 
 	“How many unique people visited this domain each hour for the past three days?”

 'Bounce-rate analysis'
 	“What percentage of people visit the page without visiting any other pages on this website?

1 balance between precomputated batch view sizes and realtime computation for a query is critical
2 precomputation at coarser dimension values helps (say pageview_counts calculated and stored at an hour/day/month)
3 unique user counts are not additive like pageview counts, must use approximation algos like HyperLogLog here with 2% error factored in.
4 bounce rate = bounced visits/total visits. visit = group of pageviews within a reasonable timeperiod.

batch_view computation flow:
-----------------------------
1 append new data to existing master dataset
2 standardize urls, names etc
3 filter duplicates
4 compute batch_views

pure re-computation is from scratch always need not be done, partial computation can is also ok,but pure must be defined.
iterative-graph algos help in consolidating user nodes joined by equiv-edges
	pick the lowest id value from 1 user sub-graph (say 1->3 3->2 4->3, make everything point to 1: 3->1, 2->1)
	do it repeatedly until output = input
	this will give the unique users (cookie or user emailid versions)	

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAP 8 - Serving Layer
***********************
batch views computed must be sharded and served.
2 primary metrics -> latency and throughput

'sharding strategy' must take in account the hdd disk-seek-time and network io between servers.
	if a query returns 20 keys -> 20 seeks &
	if hdd seek time/disk -> 500 seeks/sec 
	then 500/20 -> 25 queries/sec is the max/disk 

scans are cheaper than random seeks, best way is to colocate related data together like all batch_views results linearly for a given url in 1 machine of the cluster.
so for 1 key => 1 seek + 1 scan

'denormalization/normalization'
batch layer is normalized, but batch_views are highly denormalized with additional aggregations tailored for queries
denormalization is duplicate copies, if errors happen, recompute from normalized master data again.

'requirements':
	'Batch writable':
	The batch views for a serving layer are produced from scratch. 
	When a new version of a view becomes available, it must be possible to completely swap out the older version with the updated view.

 	'Scalable'
	A serving layer database must be capable of handling views of arbitrary size. 
	As with the distributed filesystems and batch computation framework previously discussed, this requires it to be distributed across multiple machines.
 
 	'Random reads'
 	A serving layer database must support random reads, with indexes providing direct access to small portions of the view. 
 	This requirement is necessary to have low latency on queries.
 
 	'Fault-tolerant'
 	Because a serving layer database is distributed, it must be tolerant of machine failures.

 	'No random writes' are required, it is taken care of by speed layer

-----------------------------------------------------------------------------------------------
ElephantDB
-----------
is a key/value database where both keys and values are stored as byte arrays. 
partitions the batch_views over a fixed number of 'shards', and each Elephant DB server is responsible for some subset of those shards.
once a batch_view assigned to a shard, the key/value is stored in a local indexing engine, say Berkeley DB.

stage1: 'view-creation'
shards are created by a MapReduce job whose input is a set of key/value pairs. 
The number of reducers is configured to be the number of Elephant DB shards, and the keys are partitioned to the reducers using the specified sharding scheme. 
Consequently, each reducer is responsible for producing exactly one shard of an Elephant DB view. 
Each shard is then indexed (such as into a Berkeley DB index) and uploaded to the distributed filesystem.

stage2: 'view-serving'
An Elephant DB cluster is composed of a number of machines that divide the work of serving the shards. 
To fairly share the load, the shards are evenly distributed among the 'servers'.
Elephant DB also supports replication, where each shard is redundantly hosted across a predetermined number of servers. 
For example, with 40 shards, 8 servers, and a replication factor of 3, each server would host 15 shards, and each shard would exist on 3 different servers.

shard versioning: servers slowly pulls the latest shard when available.
after downloading shards, just makes the data available via 'simple key lookup API'

---------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAP 11 - Speed Layer
***********************
requirements: random reads, random writes, scalable and fault tolerant.
Cassandra (Persistence) + ElasticSearch (indexing)

'eventual accuracy' -> Because all data is eventually represented in the batch and serving layer views, any approximations you make in the speed layer are continually corrected.
'size' -> stores only hours worth of data, avoid online compaction and concurrency issues.

'cap theorem': batch and serving layer chose availability, not even eventually consistent as they lag behind by hours

'sync/asych writes to realtime':
	synch systems is like regular DB transaction, request waits until transaction completes, needed if human involved, can slow in spike loads
	aysnch buffer in a queue and do stream processing, best for spike loads, can also batch transactions, good if no human involved.(efm kafka app ingestion)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAP 12 - Speed Layer | Cassandra
***********************************
datamodel is a sortedmap of sortedmaps
'columnfamily' ->users
	'key'->andre 
		'columns'->name: Andre; age: 25
	'key'->peter 
		'columns'->occupation: coder; status: married
more reading on Cassandra required.		
	
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAP 14 - Speed Layer - Queues | One-at-a-time Stream processing
***************************************************
'Persistent Queues'
	1 Single Consumer- 1 queue/application; message deleted after acknowledgement; queue keeps track of consumption status
					rabbitmq, activemq
	2 Multi Consumer - 1 queue/applications; messages not deleted; applications keeps track of consumption offsets
					kafka

'Stream processing' messages -> realtime views -> queues and workers
	1 one-at-a-time,	2 micro-batching	

storm
-----
'spouts' -> feed 'tuples' (list-of-name-value pairs -> 'bolts' (process)
instances of spouts/bolts are called 'tasks' which are inherently parallel, 
'partitioning' tuples required to balance load across the tasks
spread of spouts & bolts across machines is called 'topology'

'one-at-time'
intermediate queues add latency and maintenance headaches
but you can maintain 'at-least-once' guarantee without intermediate queues
if something fails, start that tuple from the root again, instead of tracking all intermediate points
idempotent operations will lead to 'exactly-once' semantics

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAP 16 - Speed Layer - Micro batch processing
***************************************************
Small batches of tuples are processed at one time, and if anything in a batch fails, the entire batch is replayed. In addi-
tion, the batches are processed in a strict order. 
This approach allows you to make use of new techniques in order to achieve 'exactly-once semantics' in your processing, rather
than relying on 'inherently idempotent functions as one-at-a-time processing does'.
micro-batch stream processing can give you the fault-tolerant accuracy you need, at the cost of higher latency.

To achieve exactly-once
	1 strong ordering
		store the last processed tuple id, even if it repeats, u get to know its already processed.












