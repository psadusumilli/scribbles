Common Cache Attributes 
************************
	• maximum size, e.g. quantity of entries 
	• cache algorithm used for invalidation/eviction, e.g.: 
		• least recently used (LRU) 
		• least frequently used (LFU) 
		• FIFO 
	• eviction percentage 
	• expiration, e.g.
		• time-to-live (TTL) 
		• absolute/relative time-based expiration

Cache access patterns:
*********************
	'cache-aside': 
		app is responsible for r/w in cache, cache does not interact with storage			
	'read-through': 
		app calls storage through cache, so cache manages everything
	'write-behind-pattern': 
		cache periodically drains modified entries into storage, offer higher throughput than read-through, but conflicts can happen bcos of external updates to storage.
	'refresh-ahead-pattern': 
		recently fetched entries are refreshed ahead of expiration
cache types:
************
	'local cache':
		one node only, not scalable 
		ehcache w/o terracotta, guava
	'replicated cache':
		any new/modified entry is replicated across nodes by talking to each other 
		good for reads, bad for writes, that too with the multiple process talking to each other over network
		echcache+terracotta, oracle coherence
	'distributed cache'
		cache entries are split between primary nodes, with replication setup (secondary) only for backup and failover
		'GET': fetched over network from the entry-owning node
		'PUT': will always be persisted in the owning node and backups
		number of network calls lesser than replicated cache
		allows failover by promoting the backup as primary

cache running modes:
********************
	'local cache': embeded within app
	'remote cache': app acts as a client to cache dedicated process
	'near cache': fronting a remote cache with a local cache


---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
IN-MEMORY DATA GRID
********************
'What is an In Memory Data Grid?'
It is not an in-memory relational database, a NOSQL database or a relational database.  
It is a different breed of software datastore.
In summary an IMDG is an ‘off the shelf’ software product that exhibits the following characteristics:

	1 The data model is distributed across many servers in a single location or across multiple locations.  This distribution is known as a data fabric.  This distributed model is known as a ‘shared nothing’ architecture.
	2 All servers can be active in each site.
	3 All data is stored in the RAM of the servers.
	4 Servers can be added or removed non-disruptively, to increase the amount of RAM available.
	5 The data model is non-relational and is object-based. 
	6 Distributed applications written on the .NET and Java application platforms are supported.
	7 The data fabric is resilient, allowing non-disruptive automated detection and recovery of a single server or multiple servers.

There are also hardware appliances that exhibit all these characteristics.  
I use the term in-memory data grid appliance to describe this group of products and these were excluded from my research.
There are six products in the market that I would consider for a proof of concept, or as a starting point for a product selection and evaluation: 

	VMware Gemfire                                                 (Java)
	Oracle Coherence                                             (Java)
	Alachisoft NCache                                              (.Net)
	Gigaspaces XAP Elastic Caching Edition            (Java)
	Hazelcast                                                           (Java)
	Scaleout StateServer                                          (.Net)

And here are the rest of products available in the market now, that I consider IMDGs:
	IBM eXtreme Scale
	Terracotta Enterprise Suite
	Jboss (Redhat) Infinispan
Relative newcomers to this space, and worthy of watching closely are Microsoft and Tibco.

'Why would I want an In Memory Data Grid? '
Let’s compare this with our old friend the traditional relational database:
	1 Performance – using RAM is faster than using disk.  No need to try and predict what data will be used next.  It’s already in memory to use.
	2 Data Structure – using a key/value store allows greater flexibility for the application developer.  The data model and application code are inextricably linked.  
						More so than a relational structure.
	3 Operations – Scalability and resiliency are easy to provide and maintain.  Software / hardware upgrades can be performed non-disruptively.

'How does an In Memory Data Grid map to real business benefits?'
	1 Competitive Advantage – businesses will make better decisions faster.
	2 Safety – businesses can improve the quality of their decision-making.
	3 Productivity – improved business process efficiency reduces waster and likely to improve profitability.
	4 Improved Customer Experience – provides the basis for a faster, reliable web service which is a strong differentiator in the online business sector.

'How do use an In Memory Data Grid?'
	Simply install your servers in a single site or across multiple sites.  
	Each group of servers within a site is referred to as a cluster.
	Install the IMDG software on all the servers and choose the appropriate topology for the product.  
	For multi-site operations I always recommend a partitioned and replicated cache.
	Setup your APIs, or GUI interfaces to allow replicated between the various servers.
	Develop your data model and the business logic around the model.
	With a partitioned and replicated cache, you simply partition the cache on the servers that best suits the business needs to trying to fulfil, and the replicated part ensures there are sufficient copies across all the servers.  
	This means that if a server dies, there is no effect on the business service.  
	Providing you have provisioned enough capacity of course.

The key here is to design a topology that mitigates all business risk, so that if a server or a site is inoperable, the service keeps running seamlessly in the background. 
There are also some tough decisions you may need to make regarding data consistency vs performance.  You can trade the performance to improve data consistency and vice versa.

'Are there any proven use cases for In Memory Data Grid adoption?'
	Oh yes, and if you’re a competitor in these markets, you may want to rethink your solution.

	1 Financial Services: 
		Improve decision-making, profitability and market competitiveness through increased performance in financial stock-trading markets. 
		Reduction in processing times from 60 minutes to 60 seconds.

	2 Online Retailer: 
		Providing a highly available, easily maintainable and scalable solution for 3+ million visitors per month in the online card retailer market.

	3 Aviation: 
		Three-site active / active / active flight booking system for a major European budget-airline carrier. Three sites are London, Dublin and Frankfurt.

	Check out the VMware Gemfire and Alachisoft NCache websites for more details on these proven use cases.



---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
HAZELCAST
**********
Hazelcast® is an open source in-memory data grid based on Java. It is also the name of the company developing the product.

The primary capabilities that Hazelcast provides include:
	'Elasticity' means that Hazelcast clusters can grow capacity simply by adding new nodes.

	'Redundancy' means that you have great flexibility when you configure Hazelcast clusters for data replication policy (which defaults to one synchronous backup copy). 
	To support these capabilities, Hazelcast has a concept of members; Members are JVMs that join a Hazelcast cluster. 
	A cluster provides a single extended environment where data can be synchronized between (and processed by) members of the cluster.

toplogies
*********
	'embdedded'
		hazelcast node is embedded in app jvm
	'client plus member'
		hazelcast nodes are part of a seperate process	

In production applications, the Hazelcast client should be reused between threads and operations. It is
designed for multithreaded operation, and creation of a new Hazelcast client is relatively expensive, as it
handles cluster events, heartbeating, etc., so as to be transparent to the user.

'failover'
In the case of a hard failure (e.g. JVM crash or kernel panic), Hazelcast has recovery and failover capabilities
to prevent data loss. In the worst case, there is always at least one synchronous backup of every piece of data
(unless that was explicitly disabled), so the loss of a single member will never cause data loss.

Hazelcast provides a certain amount of control over how data is replicated. The default is 1 synchronous backup. 
For data structures such as IMap and IQueue, this can be configured using the config parameters backup-count and async-backup-count. 
These parameters provide additional backups, but at the cost of higher memory consumption (each backup consumes memory equivalent to the size of the original data structure). 
The asynchronous option is really designed for low-latency systems where some very small window of data loss is preferable over increased write latency.

'Serialization'
Hazelcast’s IMap provides the ability to store the data in memory in several different ways. By default,
Hazelcast serializes objects to byte arrays as they are stored, and deserializes them as they are read, which
simplifies synchronization between Hazelcast nodes. However, this is not as performant as some applications
would like because a lot of CPU time can be spent serializing and deserializing.
To compensate for this, Hazelcast provides two other possible storage strategies controlled by the in-memory-
format setting. They allow values (but not keys) to be stored in the following formats:
	1.	BINARY (the default): Values are serialized and deserialized immediately.
	2.	OBJECT: Values are stored locally as objects, and are only serialized when moving to a different node.
	3.	CACHED: The values are stored in binary-format, but on first access are deserialized and additionally stored as objects.

Supports JCache(JSR 107), a java standard	
'CAP'
Hazelcast will give up Consistency (“C”) and remain Available (“A”) whilst Partitioned (“P”).
However, unlike some NoSQL implementations, C is not given up unless a network partition occurs.