Big Data
#########

CHAP1
*****
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






