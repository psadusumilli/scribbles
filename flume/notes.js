Flume:
******
Apache Flume is a distributed, reliable, and available system for 
efficiently collecting, aggregating and moving large amounts of log data from many different sources to a centralized data store.

The use of Apache Flume is not only restricted to log data aggregation. 
Since data sources are customizable, Flume can be used to transport massive quantities of event data,
including but not limited to network traffic data, social-media-generated data, email messages and pretty much any data source possible.

A 'Flume event' is defined as a unit of data flow having a byte payload and an optional set of string attributes. 
A 'Flume agent' is a (JVM) process that hosts the components '(source+channel+sink)' through which events flow from an external source to the next destination (hop)
Source and Sinks can be of many types like Avro, Thrift, Filesystem, HDFS.

Flume allows a user to build 'multi-hop flows' where events travel through multiple agents before reaching the final destination. 
It also allows fan-in and fan-out flows, contextual routing and backup routes (fail-over) for failed hops.

reliability - event removed from a channel only when it makes to the other hop channel
Flume uses a transactional approach to guarantee the reliable delivery of the events
sources, sinks, channels need to have unique names to wire them up in text-based configuration


$ bin/flume-ng agent -n $agent_name -c conf -f conf/flume-conf.properties.template


----------------------------------------------------------------------------------------------------------------------------------------------------
sample 1
*********
netcat(44444) source-> in-memory channel -> logger sink 

sample1.properties
-------------------
		a1.sources = r1
		a1.sinks = k1
		a1.channels = c1

		# Describe/configure the source
		a1.sources.r1.type = netcat
		a1.sources.r1.bind = localhost
		a1.sources.r1.port = 44444

		# Describe the sink
		a1.sinks.k1.type = logger

		# Use a channel which buffers events in memory
		a1.channels.c1.type = memory
		a1.channels.c1.capacity = 1000
		a1.channels.c1.transactionCapacity = 100

		# Bind the source and sink to the channel
		a1.sources.r1.channels = c1
		a1.sinks.k1.channel = c1

$ ./bin/flume-ng agent --conf conf --conf-file conf/sample1.properties --name a1 -Dflume.root.logger=INFO,console

$ telnet localhost 44444
		Trying 127.0.0.1...
		Connected to localhost.
		Escape character is '^]'.
		hey there
		OK
		m learning u flume
		OK
		r u fine
		OK

2015-10-18 12:35:38,980 (lifecycleSupervisor-1-0) [INFO - org.apache.flume.source.NetcatSource.start(NetcatSource.java:150)] Source starting
2015-10-18 12:35:39,097 (lifecycleSupervisor-1-0) [INFO - org.apache.flume.source.NetcatSource.start(NetcatSource.java:164)] Created serverSocket:sun.nio.ch.ServerSocketChannelImpl[/127.0.0.1:44444]
2015-10-18 12:36:41,227 (SinkRunner-PollingRunner-DefaultSinkProcessor) [INFO - org.apache.flume.sink.LoggerSink.process(LoggerSink.java:94)] Event: { headers:{} body: 68 65 79 20 74 68 65 72 65 0D                   hey there. }
2015-10-18 12:36:50,843 (SinkRunner-PollingRunner-DefaultSinkProcessor) [INFO - org.apache.flume.sink.LoggerSink.process(LoggerSink.java:94)] Event: { headers:{} body: 6D 20 6C 65 61 72 6E 69 6E 67 20 75 20 66 6C 75 m learning u flu }
2015-10-18 12:36:59,845 (SinkRunner-PollingRunner-DefaultSinkProcessor) [INFO - org.apache.flume.sink.LoggerSink.process(LoggerSink.java:94)] Event: { headers:{} body: 72 20 75 20 66 69 6E 65 0D                      r u fine. }

sample 2
*********
using zookeeper is still in beta 
z => Zookeeper connection string. Comma separated list of hostname:port
p => Base Path in Zookeeper to store Agent configurations
$ bin/flume-ng agent –conf conf -z zkhost:2181,zkhost1:2181 -p /flume –name a1 -Dflume.root.logger=INFO,console
----------------------------------------------------------------------------------------------------------------------------------------------------

plugin architecture:
-------------------
flume-home/plugins.d
		plugins.d/custom-source-1/
		plugins.d/custom-source-1/lib/my-source.jar
		plugins.d/custom-source-1/libext/spring-core-2.5.6.jar
		plugins.d/custom-source-2/
		plugins.d/custom-source-2/lib/custom.jar
		plugins.d/custom-source-2/native/gettext.so

	lib - the plugin’s jar(s)
	libext - the plugin’s dependency jar(s)
	native - any required native libraries, such as .so files

----------------------------------------------------------------------------------------------------------------------------------------------------
fan-out
---------
done by distributing events from source to many channels
This is achieved by defining a flow multiplexer that can 'replicate or multiplex' route an event to one or more channels.
1 source can write to many channels, but 1 sink can read from 1 channel

		<Agent>.sources = <Source>
		<Agent>.sinks = <Sink>
		<Agent>.channels = <Channel1> <Channel2>

		# set channel for source
		<Agent>.sources.<Source>.channels = <Channel1> <Channel2> ...

		# set channel for sink
		<Agent>.sinks.<Sink>.channel = <Channel1>

sample
******

The selector checks for a header called 'State'. 
if 'CA' then 'mem-channel-1', 
else if 'AZ' then 'file-channel-2' or \
else if its'NY' then 'both'. 
else then 'mem-channel-1' default.

retries are not done for 'optional' channels	

		# list the sources, sinks and channels in the agent
		agent_foo.sources = avro-AppSrv-source1
		agent_foo.sinks = hdfs-Cluster1-sink1 avro-forward-sink2
		agent_foo.channels = mem-channel-1 file-channel-2

		# set channels for source
		agent_foo.sources.avro-AppSrv-source1.channels = mem-channel-1 file-channel-2

		# set channel for sinks
		agent_foo.sinks.hdfs-Cluster1-sink1.channel = mem-channel-1
		agent_foo.sinks.avro-forward-sink2.channel = file-channel-2

		# channel selector configuration
		agent_foo.sources.avro-AppSrv-source1.selector.type = multiplexing
		agent_foo.sources.avro-AppSrv-source1.selector.header = State
		agent_foo.sources.avro-AppSrv-source1.selector.mapping.CA = mem-channel-1
		agent_foo.sources.avro-AppSrv-source1.selector.mapping.AZ = file-channel-2
		agent_foo.sources.avro-AppSrv-source1.selector.mapping.NY = mem-channel-1 file-channel-2
		agent_foo.sources.avro-AppSrv-source1.selector.default = mem-channel-1
		agent_foo.sources.avro-AppSrv-source1.selector.optional.CA = mem-channel-1 file-channel-2

----------------------------------------------------------------------------------------------------------------------------------------------------
SOURCES
*******
1 avro
-------
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.type = avro
	a1.sources.r1.channels = c1
	a1.sources.r1.bind = 0.0.0.0
	a1.sources.r1.port = 4141

2 Thrift
---------
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.type = thrift
	a1.sources.r1.channels = c1
	a1.sources.r1.bind = 0.0.0.0
	a1.sources.r1.port = 4141	

3 Exec
------
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.type = exec
	a1.sources.r1.command = tail -F /var/log/secure
	a1.sources.r1.channels = c1

	a1.sources.tailsource-1.type = exec
	a1.sources.tailsource-1.shell = /bin/bash -c
	a1.sources.tailsource-1.command = for i in /path/file.txt; do cat $i; done

4 JMS
------
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.type = jms
	a1.sources.r1.channels = c1
	a1.sources.r1.initialContextFactory = org.apache.activemq.jndi.ActiveMQInitialContextFactory
	a1.sources.r1.connectionFactory = GenericConnectionFactory
	a1.sources.r1.providerURL = tcp://mqserver:61616
	a1.sources.r1.destinationName = BUSINESS_DATA
	a1.sources.r1.destinationType = QUEUE

5 spooling directory
--------------------
	This source lets you ingest data by placing files to be ingested into a “spooling” directory on disk. 
	This source will watch the specified directory for new files, and will parse events out of new files as they appear. 
	The event parsing logic is pluggable. 
	After a given file has been fully read into the channel, it is renamed to indicate completion (or optionally deleted).

	a1.channels = ch-1
	a1.sources = src-1
	a1.sources.src-1.type = spooldir
	a1.sources.src-1.channels = ch-1
	a1.sources.src-1.spoolDir = /var/log/apache/flumeSpool
	a1.sources.src-1.fileHeader = true	

	serializers: read events from dropped files => text line, avro, blob.

6 1% twitter firehose (beta)
------------------------------
Experimental source that connects via Streaming API to the 1% sample twitter firehose, continously downloads tweets, 
converts them to Avro format and sends Avro events to a downstream Flume sink. 
Requires the consumer and access tokens and secrets of a Twitter developer account. 	 

7 kafka
-------
Kafka Source is an Apache Kafka consumer that reads messages from a Kafka topic. If you have multiple Kafka sources running, 
you can configure them with the same Consumer Group so each will read a unique set of partitions for the topic.

	a1.sources.source1.type = org.apache.flume.source.kafka.KafkaSource
	a1.sources.source1.channels = channel1
	a1.sources.source1.zookeeperConnect = localhost:2181
	a1.sources.source1.topic = test1
	a1.sources.source1.groupId = flume
	a1.sources.source1.kafka.consumer.timeout.ms = 100

8 netcat
--------
listen on socket 
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.type = netcat
	a1.sources.r1.bind = 0.0.0.0
	a1.sources.r1.bind = 6666
	a1.sources.r1.channels = c1	

9 sequence
----------
A simple sequence generator that continuously generates events with a counter that starts from 0 and increments by 1. 
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.type = seq
	a1.sources.r1.channels = c1	

10 syslog network
-----------------
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.type = syslogtcp
	a1.sources.r1.port = 5140
	a1.sources.r1.host = localhost
	a1.sources.r1.channels = c1	

	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.type = multiport_syslogtcp
	a1.sources.r1.channels = c1
	a1.sources.r1.host = 0.0.0.0
	a1.sources.r1.ports = 10001 10002 10003
	a1.sources.r1.portHeader = port

	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.type = syslogudp
	a1.sources.r1.port = 5140
	a1.sources.r1.host = localhost
	a1.sources.r1.channels = c1

11 http
--------
Flume Events by HTTP POST and GET.
HTTP requests are converted into flume events by a pluggable “handler” which must implement the HTTPSourceHandler interface. 
This handler takes a HttpServletRequest and returns a list of flume events
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.type = http
	a1.sources.r1.port = 5140
	a1.sources.r1.channels = c1
	a1.sources.r1.handler = org.example.rest.RestHandler
	a1.sources.r1.handler.nickname = random props	

jsonhandler, blobhandler

12 stress
----------
a1.sources = stresssource-1
a1.channels = memoryChannel-1
a1.sources.stresssource-1.type = org.apache.flume.source.StressSource
a1.sources.stresssource-1.size = 10240
a1.sources.stresssource-1.maxTotalEvents = 1000000
a1.sources.stresssource-1.channels = memoryChannel-1	

13 legacy sources
-----------------
for flume older agents, older avro sources

----------------------------------------------------------------------------------------------------------------------------------------------------
SINKS
******
1 HDFS
-------
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = hdfs
	a1.sinks.k1.channel = c1
	a1.sinks.k1.hdfs.path = /flume/events/%y-%m-%d/%H%M/%S
	a1.sinks.k1.hdfs.filePrefix = events-
	a1.sinks.k1.hdfs.round = true
	a1.sinks.k1.hdfs.roundValue = 10
	a1.sinks.k1.hdfs.roundUnit = minute

2 hive
------
	create table weblogs ( id int , msg string )
    partitioned by (continent string, country string, time string)
    clustered by (id) into 5 buckets
    stored as orc;

   	a1.channels = c1
	a1.channels.c1.type = memory
	a1.sinks = k1
	a1.sinks.k1.type = hive
	a1.sinks.k1.channel = c1
	a1.sinks.k1.hive.metastore = thrift://127.0.0.1:9083
	a1.sinks.k1.hive.database = logsdb
	a1.sinks.k1.hive.table = weblogs
	a1.sinks.k1.hive.partition = asia,%{country},%y-%m-%d-%H-%M
	a1.sinks.k1.useLocalTimeStamp = false
	a1.sinks.k1.round = true
	a1.sinks.k1.roundValue = 10
	a1.sinks.k1.roundUnit = minute
	a1.sinks.k1.serializer = DELIMITED
	a1.sinks.k1.serializer.delimiter = "\t"
	a1.sinks.k1.serializer.serdeSeparator = '\t'
	a1.sinks.k1.serializer.fieldnames =id,,msg 	

3 logger
---------	
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = logger
	a1.sinks.k1.channel = c1

4 avro
-------
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = avro
	a1.sinks.k1.channel = c1
	a1.sinks.k1.hostname = 10.10.10.10
	a1.sinks.k1.port = 4545		

5 thrift
----------
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = thrift
	a1.sinks.k1.channel = c1
	a1.sinks.k1.hostname = 10.10.10.10
	a1.sinks.k1.port = 4545	

6 irc
------
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = irc
	a1.sinks.k1.channel = c1
	a1.sinks.k1.hostname = irc.yourdomain.com
	a1.sinks.k1.nick = flume
	a1.sinks.k1.chan = #flume

7 logger
---------
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = file_roll
	a1.sinks.k1.channel = c1
	a1.sinks.k1.sink.directory = /var/log/flume	

8 null
---------
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = null
	a1.sinks.k1.channel = c1	

9 hbase
---------
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = hbase
	a1.sinks.k1.table = foo_table
	a1.sinks.k1.columnFamily = bar_cf
	a1.sinks.k1.serializer = org.apache.flume.sink.hbase.RegexHbaseEventSerializer
	a1.sinks.k1.channel = c1	

	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = asynchbase
	a1.sinks.k1.table = foo_table
	a1.sinks.k1.columnFamily = bar_cf
	a1.sinks.k1.serializer = org.apache.flume.sink.hbase.SimpleAsyncHbaseEventSerializer
	a1.sinks.k1.channel = c1

10 solr 
---------
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = org.apache.flume.sink.solr.morphline.MorphlineSolrSink
	a1.sinks.k1.channel = c1
	a1.sinks.k1.morphlineFile = /etc/flume-ng/conf/morphline.conf
	# a1.sinks.k1.morphlineId = morphline1
	# a1.sinks.k1.batchSize = 1000
	# a1.sinks.k1.batchDurationMillis = 1000	

11 elasticsearch
----------------
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = elasticsearch
	a1.sinks.k1.hostNames = 127.0.0.1:9200,127.0.0.2:9300
	a1.sinks.k1.indexName = foo_index
	a1.sinks.k1.indexType = bar_type
	a1.sinks.k1.clusterName = foobar_cluster
	a1.sinks.k1.batchSize = 500
	a1.sinks.k1.ttl = 5d
	a1.sinks.k1.serializer = org.apache.flume.sink.elasticsearch.ElasticSearchDynamicSerializer
	a1.sinks.k1.channel = c1	

12 kafka sink
----------------
	a1.sinks.k1.type = org.apache.flume.sink.kafka.KafkaSink
	a1.sinks.k1.topic = mytopic
	a1.sinks.k1.brokerList = localhost:9092
	a1.sinks.k1.requiredAcks = 1
	a1.sinks.k1.batchSize = 20
	a1.sinks.k1.channel = c1	

13 custom
-----------
	a1.channels = c1
	a1.sinks = k1
	a1.sinks.k1.type = org.example.MySink
	a1.sinks.k1.channel = c1	
----------------------------------------------------------------------------------------------------------------------------------------------------
CHANNELS
*********
1 memory
---------
	a1.channels = c1
	a1.channels.c1.type = memory
	a1.channels.c1.capacity = 10000
	a1.channels.c1.transactionCapacity = 10000
	a1.channels.c1.byteCapacityBufferPercentage = 20
	a1.channels.c1.byteCapacity = 800000	
2 jdbc
-------
	a1.channels = c1
	a1.channels.c1.type = jdbc	

3 kafka
---------
	a1.channels.channel1.type = org.apache.flume.channel.kafka.KafkaChannel
	a1.channels.channel1.capacity = 10000
	a1.channels.channel1.transactionCapacity = 1000
	a1.channels.channel1.brokerList=kafka-2:9092,kafka-3:9092
	a1.channels.channel1.topic=channel1
	a1.channels.channel1.zookeeperConnect=kafka-1:2181	

4 file
-------
	a1.channels = c1
	a1.channels.c1.type = file
	a1.channels.c1.checkpointDir = /mnt/flume/checkpoint
	a1.channels.c1.dataDirs = /mnt/flume/data	

5 spillable memory (disk used when memory is full)
-------------------
	a1.channels = c1
	a1.channels.c1.type = SPILLABLEMEMORY
	a1.channels.c1.memoryCapacity = 10000
	a1.channels.c1.overflowCapacity = 1000000
	a1.channels.c1.byteCapacity = 800000
	a1.channels.c1.checkpointDir = /mnt/flume/checkpoint
	a1.channels.c1.dataDirs = /mnt/flume/data	


----------------------------------------------------------------------------------------------------------------------------------------------------------
SINK PROCESSORS:
*****************
Sink groups allow users to group multiple sinks into one entity. 
Sink processors can be used to provide load balancing capabilities over all sinks inside the group or to achieve fail over from one sink to another in case of temporal failure.

1 load_balance
--------------
	a1.sinkgroups = g1
	a1.sinkgroups.g1.sinks = k1 k2
	a1.sinkgroups.g1.processor.type = load_balance
	a1.sinkgroups.g1.processor.backoff = true
	a1.sinkgroups.g1.processor.selector = random

2 fail-over
----------
	a1.sinkgroups = g1
	a1.sinkgroups.g1.sinks = k1 k2
	a1.sinkgroups.g1.processor.type = failover
	a1.sinkgroups.g1.processor.priority.k1 = 5
	a1.sinkgroups.g1.processor.priority.k2 = 10
	a1.sinkgroups.g1.processor.maxpenalty = 10000	
-----------------------------------------------------------------------------------------------------------------------------------------------------------------
Interceptors
*************
Flume has the capability to modify/drop events in-flight. This is done with the help of interceptors. 
Interceptors are classes that implement 'org.apache.flume.interceptor.Interceptor' interface. 
An interceptor can modify or even drop events based on any criteria chosen by the developer of the interceptor. 
Flume supports chaining of interceptors. This is made possible through by specifying the list of interceptor builder class names in the configuration. 
Interceptors are specified as a whitespace separated list in the source configuration. 
The order in which the interceptors are specified is the order in which they are invoked. 
The list of events returned by one interceptor is passed to the next interceptor in the chain. 
Interceptors can modify or drop events. 
If an interceptor needs to drop events, it just does not return that event in the list that it returns. 
If it is to drop all events, then it simply returns an empty list. Interceptors are named components, here is an example of how they are created through configuration:

	a1.sources = r1
	a1.sinks = k1
	a1.channels = c1
	a1.sources.r1.interceptors = i1 i2
	a1.sources.r1.interceptors.i1.type = org.apache.flume.interceptor.HostInterceptor$Builder
	a1.sources.r1.interceptors.i1.preserveExisting = false
	a1.sources.r1.interceptors.i1.hostHeader = hostname
	a1.sources.r1.interceptors.i2.type = org.apache.flume.interceptor.TimestampInterceptor$Builder
	a1.sinks.k1.filePrefix = FlumeData.%{CollectorHost}.%Y-%m-%d
	a1.sinks.k1.channel = c1

1 TimestampInterceptor
----------------------
inserts timestamp into each flume event.
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.channels =  c1
	a1.sources.r1.type = seq
	a1.sources.r1.interceptors = i1
	a1.sources.r1.interceptors.i1.type = timestamp	

2 HostInterceptor
-------------------
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.interceptors = i1
	a1.sources.r1.interceptors.i1.type = host
	a1.sources.r1.interceptors.i1.hostHeader = hostname	

3 StaticInterceptor
-------------------
	a1.sources = r1
	a1.channels = c1
	a1.sources.r1.channels =  c1
	a1.sources.r1.type = seq
	a1.sources.r1.interceptors = i1
	a1.sources.r1.interceptors.i1.type = static
	a1.sources.r1.interceptors.i1.key = datacenter
	a1.sources.r1.interceptors.i1.value = NEW_YORK	

4 UUID interceptor emdeds uuid into each event	
5 Morphline interceptor does all morphline transformations

6 Search and Replace Interceptor - regex replace
	a1.sources.avroSrc.interceptors = search-replace
	a1.sources.avroSrc.interceptors.search-replace.type = search_replace

	# Remove leading alphanumeric characters in an event body.
	a1.sources.avroSrc.interceptors.search-replace.searchPattern = ^[A-Za-z0-9_]+
	a1.sources.avroSrc.interceptors.search-replace.replaceString =
-----------------------------------------------------------------------------------------------------------------------------------------------------------------
Log4j Appender
*************

A flume appender is available where Log4j events are wired to a flume agent’s avro source

#
log4j.appender.flume = org.apache.flume.clients.log4jappender.Log4jAppender
log4j.appender.flume.Hostname = example.com
log4j.appender.flume.Port = 41414
log4j.appender.flume.UnsafeMode = true

# configure a class logger to output to the flume appender
log4j.logger.org.example.MyClass = DEBUG,flume

-----------------------------------------------------------------------------------------------------------------------------------------------------------------
Monitoring
-----------
export JAVA_OPTS=”-Dcom.sun.management.jmxremote 
-Dcom.sun.management.jmxremote.port=5445 
-Dcom.sun.management.jmxremote.authenticate=false 
-Dcom.sun.management.jmxremote.ssl=false”