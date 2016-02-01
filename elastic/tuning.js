Purpose
The aim is used to baseline ElasticSearch deployed to suit DEP needs for a given engagement. Each engagement will differ with respect to scale, search complexity, index read/write cycles and infrastructure.  
Tuning Factors Checklist
Number of nodes and machines.
Number of nodes per machine.
Number of master and data nodes
Number of shards
Number of replicas
Node heap size (1/2 of RAM to 30GB max). Mlockall - lock the memory.
Node discovery means ( Zookeeper or Zen unicast list of nodes)
Node separation: read nodes / write nodes
OS: sysctl params like vm.swappiness, open file handles etc.
OS: HDD vs SSD
Clients: TCP vs HTTP
Bulk Write: Index throttling for SSD
Bulk Write: Replica disabling
Bulk Write: Index Refresh time (turn off if no one is reading the index during bulk load process)
Bulk Write: Index API asynch, only primary shard acknowledge is enough
Bulk Write: Transaction flush log commit parameters
Bulk Write: indices.memory.index_buffer_size - reserve more heap for writing than reading.
Bulk Write API: Batch Size, Actions, Flush Interval & Concurrent Requests. Find the sweet spot of the 4 parameters.
Mappings: disable _all, _source, norms.enabled=false
Index must be optimized to ideal number of segment = 1
Strategy
Search Queries
The below steps are meant only for read with no writes to the index during the course of load testing. 
Pre-fill the index to the production size or more.  
Apply the known de-facto tuning settings from the above list on the index and machine.
Identify the most searched queries for a given engagement
Get them in the pure Elasticsearch API format using HTTP. We use only the generic HTTP client for baselining.
Use a load testing client like Gatlin to hit with different concurrencies like 30,60,90 & 120.
Gather the time taken for all the search queries.
Use nmon to monitor nodes.
Gather metrics from node, cluster & index using ElasticSearch Metrics APIs.
The above steps can be repeated for suitable topologies
1 primary shard/1 node/1 machine
1 shard/N nodes(replica shards)/N machines
N shards/N nodes/N machines 
Bulk Writes 
The below steps are meant only for bulk writes to the index, with zero to minimals reads during the course of load testing.  
Client Process uses Bulk API to load the index
Apply the known de-facto tuning settings from the above list on the index and machine from above
Write on only node, no replicas.
Find the optimal balance for Bulk API parameters like actions, concurrent requests, size and flush interval.
Gather metrics from node, cluster & index using ElasticSearch Metrics APIs.
Scale to N client process on a singe node, if possible.
Use nmon to monitor nodes.
Reference
https://www.elastic.co/guide/en/elasticsearch/guide/current/heap-sizing.html
vim /etc/elasticsearch/elasticsearch.yml ; add bootstrap.mlockall: true
vim /etc/init.d/elasticsearch ; add ES_HEAP_SIZE=16g
vim /etc/sysctl.conf; add  vm.swappiness = 1
sysctl -p
sudo swapoff -a 
http://mrzard.github.io/blog/2015/03/25/elasticsearch-enable-mlockall-in-centos-7/
https://www.elastic.co/guide/en/elasticsearch/reference/current/setup-configuration.html
https://blog.codecentric.de/en/2014/05/elasticsearch-indexing-performance-cheatsheet/
https://www.elastic.co/guide/en/elasticsearch/reference/current/index-modules-translog.html
https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-indices.html
http://radar.oreilly.com/2015/04/10-elasticsearch-metrics-to-watch.html
http://blog.mikemccandless.com/2011/02/visualizing-lucenes-segment-merges.html
https://www.ibm.com/developerworks/aix/library/au-analyze_aix/
Stats APIs
https://www.elastic.co/guide/en/elasticsearch/reference/1.4/cluster-nodes-stats.html
https://www.elastic.co/guide/en/elasticsearch/reference/1.4/indices-stats.html
https://www.elastic.co/guide/en/elasticsearch/reference/1.4/_stats_and_info_apis.html
