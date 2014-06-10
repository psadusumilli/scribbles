'FACEBOOK:'
#########
0| 'Linux and Apache'
1| 'Memcache':
	It is a memory caching system that is used to speed up dynamic database-driven websites (like Facebook) by caching data and objects in RAM to reduce reading time. 
	Memcache is Facebook’s primary form of caching and helps alleviate the database load. Having a caching system allows Facebook to be as fast as it is at recalling your data.
2| 'Thrift' (protocol):
	 It is a lightweight 'remote procedure call' framework for scalable cross-language services development. 
	Thrift supports C++, PHP, Python, Perl, Java, Ruby, Erlang, and others.
	Business logic is exposed as services using Thrift. Some of these services are implemented in PHP, C++ or Java depending on service requirements 
3| 'Cassandra' (database):
	It is a database management system designed to handle large amounts of data spread out across many servers.
	powers Facebook’s Inbox Search feature and provides a structured key-value store with eventual consistency.
4| 'HipHop' for PHP: 
	It is a source code transformer for PHP script code and was created to save server resources.
	 HipHop transforms PHP source code into optimized C++. After doing this, it uses g++ to compile it to machine code.
5| 'MySQL':
	lot of work on innoDB compression
	custom partitioning scheme in which a global ID is assigned to all data. 
6| Offline processing is done using 'Hadoop and Hive'.
7| Data such as logging, clicks and feeds transit using 'Scribe'and are aggregating and stored in HDFS using Scribe-HDFS [8], thus allowing extended analysis using MapReduce
8| 'BigPipe' is their custom technology to accelerate page rendering using a pipelining logic
9| 'Varnish' Cache is used for HTTP proxying. They prefered it for its high performance and efficiency.
11| The storage of the billions of photos posted by the users is handled by Haystack, an ad-hoc storage solution developed by Facebook which brings low level optimizations and append-only writes [12].
12| Facebook Messages is using its own architecture which is notably based on infrastructure sharding and dynamic cluster management. Business logic and persistence is encapsulated in so-called 'Cell'. Each Cell handles a part of users ; 		new Cells can be added as popularity grows [13]. Persistence is achieved using HBase [14].
13| Facebook Messages  search engine is built with an inverted index stored in HBase [15]
14| Facebook Search Engine  implementation details are unknown as far as I know
15| The typeahead search uses a custom storage and retrieval logic [16]
16| Chat is based on an Epoll server developed in Erlang and accessed using Thrift [17]
17| They have built an automated system that respond to monitoring alert by launching the appropriated repairing workflow, or escalating to humans if the outage could not be overcome [18]. 
18| About the resources provisioned for each of these components, some information and numbers are known:
19| Facebook is estimated to own more than 60,000 servers [18]. Their recent datacenter in Prineville, Oregon is based on entirely self-designed hardware [19] that was recently unveiled as Open Compute Project [20].
20| 300 TB of data is stored in Memcached processes [21]
21| Their Hadoop and Hive cluster is made of 3000 servers with 8 cores, 32 GB RAM, 12 TB disks that is a total of 24k cores, 96 TB RAM and 36 PB disks [22]
22| 100 billion hits per day, 50 billion photos, 3 trillion objects cached, 130 TB of logs per day as of july 2010 [22]
***********************************************************************************************************************************************************************************************************************
SPOTIFY:
#######
The Spotify backend architecture is heavily 'service oriented'. 
The backend is composed of about a hundred services, most of them fairly small and simple. 
Services are written in 'Python or Java' with a few exceptions. 
Services communicate primarily using our own protocol called 'Hermes', which is a message based and built on ZeroMQ and Protobuf. Older services still use HTTP and XML/JSON payloads. 
Storage is primarily 'PostgreSQL, Cassandra' or various 'static' indexes. The latter primarily being used by the various content services, e.g. for search or metadata.
Audio files are stored in Amazon S3 and cached in our backend or using CDNs for low latency.
The various clients keeps a persistent connection to a backend service called "accesspoint". 
It basically works as a messages router on steroids, handling communication with the needed services. The protocol between clients and the accesspoint is proprietary.
Clients for desktop, mobiles and our embeddable library, 'libspotify', all share a common code base. 
Each client then builds on this core to provide user interfaces and other platform specific adoptions. 
The shared code base is written in 'C++' and the platform adoptions in the platform native languages, e.g. ObjC on iOS. 
Also, many views and apps in the desktop client are now implemented as web apps, using an embedded instance of Chromium.
Audio is retrieved from local cache, peer-to-peer or from our storage. Our P2P solution has been described in detail in various papers by Gunnar Kreitz (papers).
Infrastructure is heavily based on 'Debian' and open source software in general.
***********************************************************************************************************************************************************************************************************************
PINTEREST
#########

We use 'python' + heavily-modified 'Django' at the application layer. 
'Tornado and (very selectively) node.js' as web-servers. 
'Memcached' and 'membase/redis' for object- and logical-caching, respectively.  
'RabbitMQ' as a message queue.  
'Nginx, HAproxy and Varnish' for 'static'-delivery and load-balancing.  
Persistent data storage using 'MySQL'.  70 master databases with a parallel set of backup databases in different regions around the world for redundancy.
'MrJob' on EMR for map-reduce.

EC2:
	150 EC2 instances in the web tier.
	90 instances for in-memory caching, which removes database load.
	35 instances used for internal purposes.
***********************************************************************************************************************************************************************************************************************	
EBAY
#####
- Web applications are mostly build in Java using a home grown framework called 'V4', 
	it brings all the Java advantages (type safety, composition, dependency management, extensibility ...) to HTML/JS/CSS. 
	The Javascript IDE (Eclipse plug-in) and runtime is open sourced now (https://www.ebayopensource.org/i...)
- Services are build mostly in Java using an originally home grown and now open sourced  SOA framework called 'Turmeric' (https://www.ebayopensource.org/i...)
- Services wrap everything from search to selling and from buying to identity management 
- Data is horizontally split based on number of pattern (that fit the nature of the data), Randy presentation talks about this more (http://www.addsimplicity.com/dow...)
- Since a lot of things are asyn, there is a massive message bus that allows sevices and applications send and receive messages - this is a home grown light weight message bus (not JMS based),
- Every system uses the central logging system (CAL) for logging, monitoring and certain analytics (a must for every large enterprise)
- eBay does 'not use ACID transaction', databases are 'Oracle' and SQL based with 'Non-SQL DBs used for caching, metadata, configuration and other proper use cases' - but eBay is not a No-SQL only shop.
- Physical deployment is also split into units called "Pool"s...
	There are mostly a collection of physical services located in multiple data centers, but increasingly they are becoming cloud-based i.e a collection of logical processing units.  
	Major services have their own pool, so do large applications. Other apps and services share a pool 
	
***********************************************************************************************************************************************************************************************************************	
GITHUB:
#######
hosted in 'rackspace'
3 protocols - http, git, ssh
Loadbalancers - 2 Xen instances running 'ldirectord' monitoring and routing to a pool of servers.
Front end servers - 4 (fe1,fe21, fe3, fe4)'Nginx' on 8 core 16 Gb machines 
'Nginx' accepts the connection and sends it to a Unix domain socket upon which sixteen 'Unicorn'(HTTP server for Ruby) worker processes are selecting. 
One of these workers grabs the request and runs the 'Rails' code necessary to fulfill it.
'MySQL' database runs on two 8 core, 32GB RAM bare metal servers with 15k RPM SAS drives. 
Their names are db1a and db1b. At any given time, one of them is master and one is slave. MySQL 'replication' is accomplished via DRBD
If the page requires information about a Git repository and that data is not cached, then it will use our 'Grit' library to retrieve the data.

***********************************************************************************************************************************************************************************************************************	
TWIITER:
########
Twitter mostly uses 'Ruby on Rails' for their front-end and primarily 'Scala and Java' for back-end services. 
They use Apache 'Thrift' (originally developed by Facebook) to communicate between different internal services. 
For storage they use 'MySQL and Cassandra' that are accessed by various services through Thrift. 
'FlockDB' is a storage solution built on top of MySQL to store relationships (follows etc). 
'Kestrel (a queuing system)' written in Scala, and 'Starling (Ruby)' also use the same storage engines.
They cache data and requests at multiple levels using 'Memcached and cache-money'. 
HTTP requests hit 'Varnish' (load balancing and reverse proxy) and then 'Apache' (with mod_proxy) to Rails (unicorn).






