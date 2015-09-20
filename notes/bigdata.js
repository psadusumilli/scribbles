Big Data
#########

CHAP1-Introduction
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
CHAP2-Batch Layer-Data model
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

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAP4- Batch Layer-Data Persistence
************************************
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