#1 INSTALL
-----------
wget https://download.elastic.co/elasticsearch/elasticsearch/elasticsearch-1.5.2.zip
unzip elasticsearch-1.5.2.zip
./bin/elasticsearch

curl -X GET http://localhost:9200/
{
  "status" : 200,
  "name" : "Amelia Voght",
  "cluster_name" : "elasticsearch",
  "version" : {
    "number" : "1.5.2",
    "build_hash" : "62ff9868b4c8a0c45860bebb259e21980778ab1c",
    "build_timestamp" : "2015-04-27T09:21:06Z",
    "build_snapshot" : false,
    "lucene_version" : "4.10.4"
  },
  "tagline" : "You Know, for Search"
}
-------------------------------------------------------------------------------------------------------------------------------------------
#2 CONCEPTS:
------------
cluster
  |=> 1-* node
    |=> 1-* index (database) 

      //physical
      |=> 1-* shards (primary|replica) (spread across many nodes)
          |=> segments   

      //logical    
      |=> 1-* types(table)
        |=> 1-1 document (row) (mapping gives the document schema)
          |=> 1-* field (column)



2.1 A "node" is one jvm, which automatically forms a single-node cluster 
Multiple nodes can be run on a single machine.
  
2.2 "cluster"
bunch of interconnected nodes. default cluster name is elasticsearch.
the node names are picked from the Marvel characters, can be overridden by 
  ./elasticsearch --cluster.name my_cluster_name --node.name my_node_name
'starting another node, will ping and join it, forming a two node cluster'

2.3 "index"
Similar to database containing many "types" 
It is a logical mapping of primary shards and their replica shards

2.4 "type"
A type is like a table in a relational database. 
Each type has a list of "fields that can be specified for documents" of that type. 
The "mapping" defines how each field in the document is analyzed.

2.5 "mapping"
A mapping is like a schema definition in a relational database. 
Each index has a mapping, which defines each type within the index, plus a number of index-wide settings. 
A mapping can either be defined explicitly, or it will be generated automatically when a document is indexed.

2.6 "shards" are for partitioning data across nodes.
A shard is a single Lucene instance. 
It is a low-level “worker” unit which is managed automatically by elasticsearch. 
Elasticsearch distributes shards amongst all nodes in the cluster, 
and can move shards automatically from one node to another in the case of node failure, or the addition of new nodes.
1 whole shard must reside in a node, no partials anywhere. 
    
  2.6.1 "Primary shard"
  Each document is stored in a single primary shard. 
  When you index a document, it is indexed first on the primary shard, then on all replicas. 
  By default, an index has 5 primary shards. 

  2.6.2 "replica shard"
  Each primary shard can have zero or more replicas. A replica is a copy of the primary shard, and has two purposes:

  'increase failover': a replica shard can be promoted to a primary shard if the primary fails
  'increase performance': get and search requests can be handled by primary or replica shards. 
  By default, each primary shard has one replica, but the number of replicas can be changed dynamically on an existing index. 
  A replica shard will never be started on the same node as its primary shard.

2.7 "document"
A document is a JSON document which is stored in elasticsearch. 
It is like a row in a table in a relational database. 
Each document is stored in an index and has a type and an id.

2.8 "field"
A document contains a list of fields, or key-value pairs. 
The value can be a simple (scalar) value (eg a string, integer, date), or a nested structure like an array or an object. 
A field is similar to a column in a table in a relational database.

2.9 "segments"
Lucence index is made of immutable "segments" (never deleted just a flag to indicate deletion, lot of compression happens in segments)
"Near Real Time", elasticsearch buffers incoming documents for 1 sec and then creates the lucene segments 
Elasticsearch merges the buffered segments before persisting.


-------------------------------------------------------------------------------------------------------------------------
QUICKIE
********

library is the "index", book is a "type"
make 4 shards of a new index named 'library'  - s1, s2, s3, s4  

  curl -XPUT http://localhost:9200/library -d '{"index.number_of_shards":4}'
  {"acknowledged":true}

make one copy of a shard to another -  s1s2 s2s4 s3s1 s4s3

  curl -XPUT http://localhost:9200/library/_settings -d '{"index.number_of_replicas":1}'
  {"acknowledged":true}
  or 
  curl -XPUT http://localhost:9200/library/
  {
     "settings" : {
        "number_of_shards" : 3,
        "number_of_replicas" : 1
     }
  }

  curl -XPUT http://localhost:9200/library/book/1 -d 
    '{"title" : "100 days of cholera", "author" : "Garcia Marquez", "language" : "spanish" }'

  curl -XGET http://localhost:9200/library/book/1                                                                                          
  {"_index":"library","_type":"book","_id":"1","_version":1,"found":true,"_source":{"title" : "100 days of cholera", "author" : "Garcia Marquez", "language" : "spanish" }}%  

  curl -XDELETE http://localhost:9200/library/book/1

  curl -xPOST http://localhost:9200/library/book/_search -d 
  {
    "query" : {
        "match" : {
            "title" : "100 days of Cholera"
        }
      }
  }


Cold cache..?
Filters do not contribute to score and are cached.
Queries contribute to score, but not cached.
Aggregations help in aggregating results

"Node client" -9300 port
The node client joins a local cluster as a non data node. 
In other words, it doesn’t hold any data itself, but it knows what data lives on which node in the cluster.
Then it can forward requests directly to the correct node.

"Transport client" - 9300 port
The lighter-weight transport client can be used to send requests to a remote cluster. 
It doesn’t join the cluster itself, but simply forwards requests to a node in the cluster.





-------------------------------------------------------------------------------------------------------------------------------------------
#3 DISTRIBUTED DOCUMENT STORE:
-------------------------------

Elasticsearch tries hard to hide the complexity of distributed systems. 

"master" node is sole entity to create/delete index or other nodes.
master node need not be used for search
all nodes know where the data is and route the client
all nodes can talk to each other
shards can be in single node or go across multiple nodes
nodes can be added seamlessly and shards will be balanced automatically!
users can talk to any node, no bottleneck in search

curl -XGET http://localhost:9200/_cluster/health
{
  "cluster_name":"elasticsearch",
  "status":"yellow", // only primary shards are up, no point in replica shards running on the same machine.
  "timed_out":false,
  "number_of_nodes":1,
  "number_of_data_nodes":1,
  "active_primary_shards":8, // all are primary since its 1 local node
  "active_shards":8,
  "relocating_shards":0,
  "initializing_shards":0,
  "unassigned_shards":8,
  "number_of_pending_tasks":0
}

Adding a 'node', start another elastic instance in the same machine, it will run on ports 9301, 9201

 curl -XGET http://localhost:9200/_cluster/health
{
  "cluster_name":"elasticsearch",
  "status":"yellow",
  "timed_out":false,
  "number_of_nodes":2,
  "number_of_data_nodes":2,
  "active_primary_shards":8,
  "active_shards":8,
  "relocating_shards":0,
  "initializing_shards":2,
  "unassigned_shards":6,
  "number_of_pending_tasks":0
}

on a 'node failover', the replicas get promoted to primary status instanteously.
on getting the node back, the old indexes are updated with the changes that happened during its downtime.


3.1 Adding a node in a networked setting
-------------------------------------
It is usually handled automatically. If autodiscovery doesnt work. Edit the elastic search config file, by enabling unicast discovery

Node 1:

    cluster.name: mycluster
    node.name: "node1"
    node.master: true
    node.data: true
    discovery.zen.ping.multicast.enabled: false
    discovery.zen.ping.unicast.hosts: ["node1.example.com"]
Node 2:

    cluster.name: mycluster
    node.name: "node2"
    node.master: false
    node.data: true
    discovery.zen.ping.multicast.enabled: false
    discovery.zen.ping.unicast.hosts: ["node1.example.com"]

and so on for node 3,4,5. 
Make node 1 master, and the rest only as data nodes.
They may or may not be data nodes, though.

Also, in case auto-discovery doesnt work, most probable reason is because the network doesnt allow it (and therefore disabled). 

3.2 Scaling
------------
Number of Primary shards cannot be changed, decides the max amt of data stored.
Number of replica shards can be changed runtime, decides the max read throughput

How many nodes:
===============
Minimum nodes= 3 - splitbrain 2
If u have 2 node, say master M1 and slave S2
then suppose M1 is drunk
S2 promotes to master M2 since no other option exists
M1 comes back, see M2, thinks M2 misbehaved and he drink 2 much that night
so split brain 

Say if u have 3 nodes,M1, S2, S3
S2->M2 when M1 is drinking in bar
M1 comes back, but S3 will vouch for M2 now..saying ' u got drunk..M1'
if you have 'N nodes, then by convention, N/2+1 nodes should be masters for fail-over' mechanisms.

How many shards?
================
A shard is not free. 
A shard is a Lucene index under the covers, which uses file handles, memory, and CPU cycles.
Every search request needs to hit a copy of every shard in the index. 
That’s fine if every shard is sitting on a different node, but not if many shards have to compete for the same resources.

shard number should be derived by performance baselining.
  1 Create a cluster consisting of a single server, with the hardware that you are considering using in production.
  2 Create an index with the same settings and analyzers that you plan to use in production, but with only 1 primary shard and 0 replicas.
  3 Fill it with real documents (or as close to real as you can get).
  4 Run real queries and aggregations (or as close to real as you can get).

Say 1 million records is the max in 1 shard to give a latency < 0.1 second
so if total record count is 100 million, we need 100 shards

Once you define the capacity of a single shard, it is easy to extrapolate that number to your whole index. 
Take the total amount of data that you need to index, plus some extra for future growth, and divide by the capacity of a single shard. 
The result is the number of primary shards that you will need.





















------------------------------------------------------------------------------------------------------------------------------------------
#4 DOCUMENT:
---------------

 "document" is a json object with each field indexed and searcheable in Lucene
 "Mappings" are the layer that Elasticsearch uses to map complex JSON documents into the simple flat documents that Lucene expects to receive.
Each document has "_id, _type, _index, _source and _version"

GET /website/blog/123?_source=title,text //select only certain partsof source
GET /website/blog/123/_source //select without metata
XHEAD /website/blog/123 //check if 123 exists, no body returned.

//updating a document
documents are immutable, so updating give a document updates the "version"
Internally, Elasticsearch has marked the old document as deleted and added an entirely new document. 
The old version of the document doesn’t disappear immediately, although you won’t be able to access it. 
Elasticsearch cleans up deleted documents in the background as you continue to index more data.

PUT to create document with a user given id or Update a document
POST to create a new document with elasticsearch generated id.

conflicts:
---------
"Pessimistic concurrency control":
lock the data in that row, widely used in DBs.
"Optimistic concurrency control"
reads a data, does not block, when attempting to update that data, check if changed (version), the decides go/nogo.
Simlar to CouchDb in managing conflicts using the latest version number
Changes to a index are replicated to all nodes


updates:
--------
"complete or full" -> PUT /website/blog/123 -> document retrieve-change-reindexed 
"partial" -> POST /website/blog/1/_update -> posting only a subset of fields, document retrieve-change-reindexed
  
Mutltiget is done by "/books/_mget"
Bulk operations can also be done to avoid multiple network calls
POST /_bulk
{ "delete": { "_index": "website", "_type": "blog", "_id": "123" }} //action : metadata
{ "create": { "_index": "website", "_type": "blog", "_id": "123" }} //in case of create/index/update, the next line is the source document
{ "title":    "My first blog post" }
{ "index":  { "_index": "website", "_type": "blog" }}
{ "title":    "My second blog post" }
{ "update": { "_index": "website", "_type": "blog", "_id": "123", "_retry_on_conflict" : 3} }
{ "doc" : {"title" : "My updated blog post"} } 
bulk payloads consume memory, 5-15MB is safe 

------------------------------------------------------------------------------------------------------------------------------------------
#5 DISTRIBUTED DOCUMENTS HANDLING
-----------------------------------
"default sharding" => shard = hash(doc_id) % number_of_shards

When sending requests, it is good practice to round-robin through all the nodes in the cluster, in order to spread the load.

A) "update/create doc"
---------------------
whenever doc says doc1 is updated,
  1) master node determines which primary shard (say s1 in node n1)
  2) s1 is updated
  3) if s1 has replicas r1 in node n2, r2 in node3, they are also updated.
  4) after every shard is in synch, success response is given back to client.

The above process is "sync", it can be "async" which does not gurantee replica synch up (not advised)
"consistency"

**************************************************
quorum -> ((primary + number_of_replicas)/ 2) + 1

**************************************************
if a quorum is reached among primary and replicas, the change (CRUD) is marked as success.


B) "get doc"
------------
whenever doc says doc1 is updated,
  1) master node determines which primary shard (say s1 in node n1), but will pick from any replica r1,r2 to maintain load balancing.

"timeout" can control how long to wait for an operation.

C) "partial update"
--------------------
  1) master node determines which primary shard (say s1 in node n1)
  2) if some process has already updated after its retrieval, it retries step 3 up to retry_on_conflict times, before giving up.

"pagination"
GET /_search?size=5
GET /_search?size=5&from=5
GET /_search?size=5&from=10
  
"multi-index" search can go across multiple shards of different types, but the mechanisms are still the same.











------------------------------------------------------------------------------------------------------------------------------------------
#6 MAPPING AND ANALYSIS
------------------------

"Analysers" work on normalizing the data before entering them into the inverted index.
Query Strings are also analysed to match the normalized stored values

1 "Standard analyzer"
The standard analyzer is the default analyzer that Elasticsearch uses. It is the best general choice for analyzing text that may be in any language. 
It splits the text on word boundaries, as defined by the Unicode Consortium, and removes most punctuation. Finally, it lowercases all terms. It would produce
'set, the, shape, to, semi, transparent, by, calling, set_trans, 5'

2 "Simple analyzer"
The simple analyzer splits the text on anything that isn’t a letter, and lowercases the terms. It would produce
'set, the, shape, to, semi, transparent, by, calling, set, trans'

3 "Whitespace analyzer"
The whitespace analyzer splits the text on whitespace. It doesn’t lowercase. It would produce
'Set, the, shape, to, semi-transparent, by, calling, set_trans(5)'

4 "Language analyzers" - knows the specific language rules, does stemming.

To understand analyzer, run
GET /_analyze?analyzer=standard -d 'I am a bad bad man'


"mapping" to types dynamically
------------------------------------
Elasticsearch supports the following simple field types:
String: string
Whole number: byte, short, integer, long
Floating-point: float, double
Boolean: boolean
Date: date

GET /gb/_mapping/tweet

"Custom mappings"
---------------------
  explicit integer mapping to number_of_clicks
  {
      "number_of_clicks": {
          "type": "integer"
      }
  }
The two most important mapping attributes for string fields are "index and analyzer".
The "index" attribute controls how the string will be indexed. 
It can contain one of three values:
  analyzed => First analyze the string and then index it. In other words, index this field as full text.
  not_analyzed => Index this field, so it is searchable, but index the value exactly as specified. Do not analyze it.
  no => Don’t index this field at all. This field will not be searchable.

The default value of index for a string field is analyzed. 
If we want to map the field as an exact value, we need to set it to "not_analyzed"
{
    "tag": {
        "type":     "string",
        "index":    "not_analyzed"
    }
}

For analyzed string fields, use the "analyzer" attribute to specify which analyzer to apply both at search time and at index time.
By default, Elasticsearch uses the standard analyzer, but you can change this by specifying one of the built-in analyzers, such as "whitespace, simple, or english"

sample:
To demonstrate both ways of specifying mappings, let’s first delete the gb index:

Then create a new index "profile", specifying that the tweet field should use the english analyzer:

PUT /profile 
{
  "mappings": {
    "tweet" : {
      "properties" : {
        "tweet" : {
          "type" :    "string",
          "analyzer": "english"
        },
        "date" : {
          "type" :   "date"
        },
        "name" : {
          "type" :   "string"
        },
        "user_id" : {
          "type" :   "long"
        }
      }
    }
  }
}

update a mapping with a new field "tag"
PUT /profile/_mapping/tweet
{
  "properties" : {
    "tag" : {
      "type" :    "string",
      "index":    "not_analyzed"
    }
  }
}

Complex Types
--------------
1 Multivalue Fields : Array

{ "tag": [ "search", "nosql" ]}

There is no special mapping required for arrays. 
Any field can contain zero, one, or more values, in the same way as a full-text field is analyzed to produce multiple terms.  

2 Inner objects, nothing special, elasticsearch just flattens it out
sample-1
{
    "tweet":            "Elasticsearch is very flexible",
    "user": {
        "id":           "@johnsmith",
        "gender":       "male",
        "age":          26,
        "name": {
            "full":     "John Smith",
            "first":    "John",
            "last":     "Smith"
        }
    }
}
translated:
{
    "tweet":            [elasticsearch, flexible, very],
    "user.id":          [@johnsmith],
    "user.gender":      [male],
    "user.age":         [26],
    "user.name.full":   [john, smith],
    "user.name.first":  [john],
    "user.name.last":   [smith]
}
------------------------------------------------------
sample-2
{
    "followers": [
        { "age": 35, "name": "Mary White"},
        { "age": 26, "name": "Alex Jones"},
        { "age": 19, "name": "Lisa Smith"}
    ]
}
{
    "followers.age":    [19, 26, 35],
    "followers.name":   [alex, jones, lisa, smith, mary, white]
}
-------------------------------------------------------------------------------------------------------------------------------------------
#7 QUERYING:
------------
Empty Search -> GET /_search
Search across multiple types -> GET /index_2014*/type1,type2/_search

HTTP GET is used with payloads!! 
The truth is that RFC 7231—the RFC that deals with HTTP semantics and content—does not define what should happen to a GET request with a body! 
As a result, some HTTP servers allow it, and some—especially caching proxies—don’t. 
POST is also allowed as a backup

pagination ->  POST /_search {"from": 30, "size": 10}

Query DSL
----------
GET /_search
{
  "query": {
      "match": {
          "tweet": "elasticsearch"
      }
  }
}

Query clauses are simple building blocks that can be combined with each other to create complex queries. Clauses can be as follows:

"Leaf clauses" (like the match clause) that are used to compare a field (or fields) to a query string.
Compound clauses that are used to combine other query clauses. 
For instance, a "bool" clause allows you to combine other clauses that either must "match, must_not match, or should match" if possible:
{
    "bool": {
        "must":     { "match": { "tweet": "elasticsearch" }},
        "must_not": { "match": { "name":  "mary" }},
        "should":   { "match": { "tweet": "full text" }}
    }
}

Filters
---------

A "filter" asks a yes|no question of every document and is used for fields that contain exact values:
  Is the created date in the range 2013 - 2014?
  Does the status field contain the term published?
The output from most filter clauses—a simple list of the documents that match the filter—is quick to calculate and easy to cache in memory, using only 1 bit per document. 
These cached filters can be reused efficiently for subsequent requests. 
The goal of filters is to 'reduce the number of documents that have to be examined by the query'.

A "query" is similar to a filter, but also asks the question: How well does this document match? 
A query calculates how relevant each document is to the query, and assigns it a relevance _score
  Best matching the words full text search
  Containing the word run, but maybe also matching runs, running, jog, or sprint
  Containing the words quick, brown, and fox—the closer together they are, the more relevant the document  

As a general rule, use query clauses for full-text search or for any condition that should affect the relevance score, and use filter clauses for everything else.  
Some important filters are 'term, terms, bool, range and bool'.

To combine query and filters, use "filtered"
  "filtered": {
          "query":  { "match": { "email": "business opportunity" }},
          "filter": { "term":  { "folder": "inbox" }}
      }

There are query and filter "context", and each can work within the other.

Query Validation
----------------
GET /gb/tweet/_validate/query
{
   "query": {
      "tweet" : {
         "match" : "really powerful"
      }
   }
}
explain
--------
GET /gb/tweet/_validate/query?explain to get more detail on the validation errors if any, and the parsed format.
