
//FROM DEFINITIVE GUIDE


CHAP1-INTRODUCTION
*******************
“Apache Cassandra is an open source, distributed, decentralized, elastically scalable,
highly available, fault-tolerant, tuneably consistent, column-oriented database that
bases its distribution design on Amazon’s Dynamo and its data model on Google Bigtable.
Created at Facebook, it is now used at some of the most popular sites on the Web.”

0.0 'general rdbms'
    ACID, 2 phase commit  to scale across machines
    sharding - feature-based, key-based, lookup table
    ORM & schema not matching real domain entities
    denormalizing & star schemas

0.1 'rdbms comparision'
    Handles moderate incoming data velocity / Handles high incoming data velocity
    Data arriving from one/few locations / Data arriving from many locations
    Manages primarily structured data / Manages all types of data
    Supports complex/nested transactions / Supports simple transactions
    Single points of failure with failover / No single points of failure; constant uptime
    Supports moderate data volumes / Supports very high data volumes
    Centralized deployments / Decentralized deployments
    Data written in mostly one location /  Data written in many locations
    Supports read scalability (with consistency sacrifices) / Supports read and write scalability
    Deployed in vertical scale up fashion / Deployed in horizontal scale out fashion

//.........................................................................................................................................................
CHAP2-QUICKSTART
****************

'step-0: download the tar, untar in some tools folder'
    apache-cassandra-2.2.3:  cd bin
    ➜  bin  ls
    cassandra         cassandra.in.sh  cqlsh.bat  debug-cql.bat  source-conf.ps1  sstableloader      sstablescrub.bat    sstableverify      stop-server.bat
    cassandra.bat     cassandra.ps1    cqlsh.py   nodetool       sstablekeys      sstableloader.bat  sstableupgrade      sstableverify.bat  stop-server.ps1
    cassandra.in.bat  cqlsh            debug-cql  nodetool.bat   sstablekeys.bat  sstablescrub       sstableupgrade.bat  stop-server

'step-1: start server'
    ➜  bin ./cassandra


'step-2: talk using cql shell'
    ➜  bin  ./cqlsh
    Connected to Test Cluster at 127.0.0.1:9042.
    [cqlsh 5.0.1 | Cassandra 2.2.3 | CQL spec 3.3.1 | Native protocol v4]
    Use HELP for help.
    cqlsh> CREATE KEYSPACE mykeyspace
       ... WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
    cqlsh> use mykeyspace;
    cqlsh:mykeyspace> CREATE TABLE users (user_id int PRIMARY KEY,fname text,lname text);
    cqlsh:mykeyspace> INSERT INTO users (user_id,  fname, lname) VALUES (1745, 'john', 'smith');
    cqlsh:mykeyspace> INSERT INTO users (user_id,  fname, lname) VALUES (1744, 'john', 'doe');
    cqlsh:mykeyspace> INSERT INTO users (user_id,  fname, lname) VALUES (1746, 'john', 'smith');
    cqlsh:mykeyspace> SELECT * FROM users;

     user_id | fname | lname
    ---------+-------+-------
        1745 |  john | smith
        1744 |  john |   doe
        1746 |  john | smith

    (3 rows)
    cqlsh:mykeyspace> CREATE INDEX ON users (lname);
    cqlsh:mykeyspace>
    cqlsh:mykeyspace> SELECT * FROM users WHERE lname = 'smith';

     user_id | fname | lname
    ---------+-------+-------
        1745 |  john | smith
        1746 |  john | smith

    (2 rows)
    cqlsh:mykeyspace>

//.........................................................................................................................................................

CHAP3-DATA MODEL
*****************
3.1 features
------------
  'distributed & elastic scalability',
    no-master, gossip protocol
    scales well with multi-core and multiple datacenters.
    high write throughput.
    good for multi node deployments of significant scale.
    data is sharded based on default or custom partitioner
    keyspaces are replicated across nodes: replication_factor=0,1,2...
    sharding and replication auto-balance on adding/removing nodes
    scale up and down by adding nodes, balances automatically
    highly available and fault tolerant

  'Tunable data consistency'
    is supported across single or multiple data centers, and you have a number of different consistency options from which to choose.
    Consistency is 'configurable on a per-query basis', meaning you can decide how strong or eventual consistency should be per SELECT, INSERT, UPDATE, and DELETE operation.
    For example, if you need a particular transaction to be available on all nodes throughout the world, you can specify that all nodes must respond before a transaction is marked complete. On the other hand, a less critical piece of data (e.g., a social media update) may only need to be propagated eventually, so in that case, the consistency requirement can be greatly relaxed.
    in general consistency types => strict, causal (vector clocks), eventual

      'replication factor' -> number of nodes to propagate a write action
      'consistency factor' -> number of nodes to acknowledge a write action
            consistency factor < replication factor => available system
            consistency factor = replication factor => strict consistency
            consistency factor > replication factor => not possible.
 'CAP'
      CA -> many nodes with replicated data, must block for every write to synch all nodes to be consistent.
      CP -> many nodes with partitioned data, can lose a shard when the node goes down.
      AP -> many nodes with partitioned & replicated data, consistency is eventual.
      Only 'AID' portion of 'ACID'. Writes to Cassandra are atomic, isolated, and durable.
      The 'C' of ACID—​consistency—​does not apply to Cassandra, as there is no concept of referential integrity or foreign keys.
      tunable data consistency across a database cluster.
      You might want a particular request to complete if just one node responds, or you might want to wait until all nodes respond.

      Cassandra also supplies 'lightweight transactions', or a compare-and-set mechanism.
      Using and extending the Paxos consensus protocol (which allows a distributed system to agree on proposed data modifications with a quorum-based algorithm,
          and without the need for any one "master" database or two-phase commit), Cassandra offers a way to ensure a transaction isolation level similar to the serializable level offered by relational database.

 'data store'
      Cassandra is a wide-row-store database that uses a highly denormalized model designed to capture and query data performantly.
      Although Cassandra has objects that resemble a relational database (e.g., tables, primary keys, indexes, etc.),
      Cassandra data modeling techniques necessarily depart from the relational tradition.
      Can have 1000+ columns unlike rdbms
      internally, cassandra is more row-oriented than column-oriented, stores as multi-dimensional hashmap.
      “Sparse” means that for any given row you can have one or more columns,
      but each row doesn’t need to have all the same columns as other rows like it (as in a relational model)
      each row has a unique key

      Cassandra includes:
          'Keyspace'
              a container for data tables and indexes; analogous to a database in many relational databases.
              It is also the level at which replication is defined.
          'Table'
              somewhat like a relational table, but capable of holding vastly large volumes of data.
              A table is also able to provide very fast row inserts and column level reads.
          'Primary key'
              used to identity a row uniquely in a table and also distribute a table’s rows across multiple nodes in a cluster.
          'Index'
              similar to a relational index in that it speeds some read operations; also different from relational indices in important ways.

  'cql'
      DDL (CREATE, ALTER, DROP), DML (INSERT, UPDATE, DELETE, TRUNCATE), and query (SELECT) operations are all supported.

3.2 columns, family, super columns
----------------------------------
  [Keyspace][ColumnFamily][Key][Column]
  [Keyspace][ColumnFamily][Key][SuperColumn][SubColumn]

  'column and column family'
    a big list of different types of maps
    the basic unit is a key-value pair called column(rdbms column),
    bunch of them make a columnfamily(rdbms table)
    list of related columns is row(must be wholly saved in 1 node)
    unlike rdbms the column-name can be any datatype in adddition to string since its a map key
    no null values, just no entry for the key, hence Sparse
    column-family has attributes like 'keys-cached, rows-cached, comment, read-repair-chance, preload-row-cache'

  'timestamp'
    each column entry must have a last-updated timestamp
    the row (list of related columns in the table) is not associated with the timestamp
    timestamp needed for conflict resolution
    not automatic provided metadata, but should be given by client everytime

  'super-column family' - map of maps
    say person is a map of address, employment, dependents column families with its own row key to club them all together

  'keyspace'
    related column families with set of attributes like
      Replication factor - how many node copies
      Replica placement strategy - simple, oldnetworktopologystrategy, NetworkTopologyStrategy

  'sorting'
    column sorting is controllable, but key sorting isn’t; row keys always sort in byte order.
    columns are sorted by the 'compare_with' type defined on their enclosing column famil
    types => AsciiType, BytesType, LexicalUUIDType, Integer Type, LongType, TimeUUIDType, or UTF8Type
    in rdbms, sorting order (ascending or descending) can be specified at query time, but in cassandra its fixed at table design
    though 'Slicerange' query can help in sort order change

  'no-joins | denormalized'
    there are no joins..period.
    no referential integrity
    design your tables with queries as pivot -
    invoice table will have copied fields from order and customer tables

3.3 Patterns
--------------
  'materialized views'
    -> if you have a 'user' column family
    user_name(PK) | first_name | city | state ...
    and you want to find users in a particular city, create  then column family called 'user_city'
    city (PK) | user_name
  'valueless column'
    above example in 'user_city', if user had an UUID row key user_id
    city (PK) | user_id
  'aggregate keys'
    combine 2+ more columns
    state:city (PK) | user_id

//.......................................................................................................................................................
CHAP4-SAMPLE APP
*****************
Hotel Search Site

'queries come first unlike rdbms where entities and relations come first'
• Find hotels in a given area.
• Find information about a given hotel, such as its name and location.
• Find points of interest near a given hotel.
• Find an available room in a given date range.
• Find the rate and amenities for a room.
• Book the selected room by entering guest information.


step 4.1. Schema definition in cassandra.yaml
keyspaces:
- name: Hotelier
  replica_placement_strategy: org.apache.cassandra.locator.RackUnawareStrategy replication_factor: 1
column_families:
  - name: Hotel
    compare_with: UTF8Type
  - name: HotelByCity
    compare_with: UTF8Type
  - name: Guest
    compare_with: BytesType
  - name: Reservation
    compare_with: TimeUUIDType
  - name: PointOfInterest
    column_type: Super
    compare_with: UTF8Type
    compare_subcolumns_with: UTF8Type
  - name: Room
    column_type: Super
    compare_with: BytesType
    compare_subcolumns_with: BytesType
  - name: RoomAvailability
    column_type: Super
    compare_with: BytesType
    compare_subcolumns_with: BytesType

Outdated code from 'definitive guide book' but is raw and in-synch with column families, no SQL syntax to make u feel comfortable.

//.......................................................................................................................................................
CHAP5- ARCHITECTURE
*********************
5.1 'System keyspace'
        'Schema' column family holds user keyspace and schema definitions
        'Migrations' column family records the changes made to a keyspace.
5.2 'peer-to-peer'
        add nodes, give them time to become full fledged ring member
5.3 'replication and failure tolerance'
        state replication and failure detection via 'gossip' protocol
        When a server node is started, it registers itself with the gossiper to receive endpoint state information.
        org.apache .cassandra.gms.Gossiper class is responsible for managing gossip for the local node.

        1. Periodically (according to the settings in its TimerTask), the G=gossiper will choose a random node in the ring and initialize a gossip session with it. Each round of gossip requires three messages.
        2. The gossip initiator sends its chosen friend a GossipDigestSynMessage.
        3. When the friend receives this message, it returns a GossipDigestAckMessage.
        4. When the initiator receives the ack message from the friend, it sends the friend a GossipDigestAck2Message to complete the round of gossip.

        'Phi Accrual Failure Detection'
        not just simple heartbeat but more intelligent to work flexible and also be suspicious
        org.apache.cassandra.gms .FailureDetector
        org.apache.cassandra.gms.IFailureDetector

5.4 'anti-entropy'
        anti-entropy is the opposite of gossip, corrects whatever gossip misses out
        org.apache.cassandra.service.AntiEntropyService
        One'merkle tree' per column is built during compaction
          merkle trees are hash trees wherethe leaves are the data blocks (typically files on a filesystem) to be summarized.
          Every parent node in the tree is a hash of its direct child node, which tightly compacts the summary.
        during each update, checksum of tree is compared across nodes
        if mismatch, then tree is shared and corrected
        This requires using a 'time window' to ensure that peers have had a chance to receive the most recent update so that the system is not constantly and unnecessarily executing anti-entropy.
        To keep the operation fast, nodes internally maintain an 'inverted index keyed by timestamp' and only exchange the most recent updates.
5.5 'read-repair'
        when data read from multiple node replicas, if they are found not be in synch, latest is always returned to client.
        then read-repair happens based on client consistency level
          ONE -> just return latest from one node, do correction across all nodes in background
          QUORUM -> block until read-repair is done in quorum nodes, then return
          ALL -> block until read-repair is done in all nodes

5.6 'Memtables, SSTables, and Commit Logs'
      On write
          1 write to commit log
              1 commit log per column family for entire server
              1 bit flag in commit log, which will be set to 0 once memtable is flushed to disk
              has rollover mechanisms
          2 write to memory tables.
              multiple memtables exists per column family, 1 current, rest waiting to flush
          3 flush to storage tables in disk once memory table threshold is reached
              immutable
              each SSTable also has an associated 'Bloom filter' for performance
              When a query is performed, the Bloom filter is checked first before accessing disk. Because false-negatives are not possible, if the filter indicates that the element does not exist in the set, it certainly doesn’t; but if the filter thinks that the element is in the set, the disk is accessed to make sure.

      'fast writes'
          just plain sequential append gives fast insertion, since random seek to update data
          Compaction is intended to amortize the reorganization of data for better read performance, but it uses sequential IO to do so
      'on reads'
          will check the memtable first to find the value

5.7 'hinted handoffs'
    when node A receives a write meant for node B which is dead
    it will create a 'hint' (write packaged) for node B to take up when it comes back
    problem: all nodes can pile up hints which can flood the node B once its comes up

5.8 'compaction'
    A compaction operation in Cassandra is performed in order to merge SSTables.
    During compaction, the data in SSTables is merged: the keys are merged, columns are com- bined, tombstones are discarded, and a new index is created.

5.9 'tombstone'
    is like a soft-delete tag on data,
    stays around for Garbage Collection Grace Seconds (10 days), then wiped out.

5.10 'Staged Event-Driven Architecture (SEDA)'
    similar to actor based concurrency
    each stage is a unit for work which could be done by a different thread pool managed by java.util.ExecutorService
    The following operations are represented as stages in Cassandra:
      • Read
      • Mutation
      • Gossip
      • Response
      • Anti-Entropy
      • Load Balance
      • Migration
      • Streaming

5.11 'Cassandra Daemon'
    The org.apache.cassandra.service.CassandraDaemon interface represents the life cycle of the Cassandra service running on a single node.
    It includes the typical life cycle operations that you might expect: start, stop, activate, deactivate, and destroy.

5.12 'Storage Service'
    The Cassandra database service is represented by org.apache.cassandra.service .StorageService.
    The storage service contains the node’s 'token', which is a marker indicating the range of data that the node is responsible for.

5.14 'MessagingService'
    The purpose of org.apache.cassandra.net.MessagingService is to create socket listeners for inbound/outbound message exchanges.
    Message streaming is Cassandra’s optimized way of sending sections of SSTable files from one node to another; all other communication between nodes occurs via serialized messages.

//.......................................................................................................................................................
CHAP6-CONFIGURATION
*****************
6.1 'Replication'
  Each node in the ring is assigned a single, unique token.
  Each node claims ownership of the range of values from its token to the token of the previous node.
  This is captured in the org.apache.cassandra.dht.Range class.
        'SimpleStrategy | RackUnawareStrategy' => replicate within 1 datacenter, but hardware rack unaware within it.
        'Oldnetworktopologystrategy | RackAwareStrategy' => specifically for when nodes in the same Cassandra cluster spread over 2  data centers and using a replication factor of 3
        'NewNetworkTopologyStrategy' -=> allows you to specify more evenly than the RackAwareStrategy how replicas should be placed across data centers.
        To use it, you supply parameters in which you indicate the desired replication strategy for each data center

        'replication factor' is set per keyspace, and is specified in the server’s config file.
        indicates how many nodes you want to use to store a value during each write operation.

        'consistency level' ispecified per query, by the client.
        specifies how many nodes the client has decided must respond in order to feel confident of a successful read or write operation

        restart nodes after changing replication level
        clients need to wait until replication is complete when fresh after a change

6.2 'Partitioning'
      only for the row-key

        'random partitioner',
              is the default;  It uses a BigIntegerToken with an MD5 hash applied to it to determine where to place the keys on the node ring.
              'advantage' of spreading your keys evenly across your cluster, because the distribution is random.
              'disadvantage' of causing inefficient range queries, because keys within a specified range might be placed in a variety of disparate locations in the ring, and key range queries will return data in an essentially random order.
              order-preserving partitioner
              collating order-preserving partitioner.
              custom using org.apache.cassandra.dht.IPartitioner
        'order-preserving partitioner'
              implements IPartitioner<StringToken>, the token is a UTF-8 string, based on a key
              'advantage' -> Configuring your column family to use order-preserving partitioning (OPP) allows you to perform range slices.
              'disadvantage' -> lopsided nodes, some heavy, some light like names grouped with starting alphabet A-Z, need to rebalance manually
        'Collating Order-Preserving Partitioner'
              orders keys according to a United States English locale (EN_US). Like OPP, it requires that the keys are UTF-8 strings
              rarely used
        'Byte-Ordered Partitioner'
              treats the data as raw bytes, instead of converting them to strings the way the order-preserving partitioner and collating order-preserving partitioner do

6.3 'Snitches'
        The job of a snitch is simply to determine relative host proximity. Snitches gather some information about your network topology so that Cassandra can efficiently route requests.
        The snitch will figure out where nodes are in relation to other nodes. Inferring data centers is the job of the replication strategy.
        'Simple Snitch'
              compares 2 (same datacenter) and 3 (same rack) octets of IP to decide machine proximity
        'PropertyFileSnitch'
              write out machines explicitly
6.4 'Security'
         The default is any , next is 'SimpleAuthenticator' using name/passwd with plain text/MD5 hash, then 'Custom'
