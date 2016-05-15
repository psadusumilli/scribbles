
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
2.0
 'How READ/WRITE happens'
    'write': client =>commit log (disk)=>memtable(memory)=>spill over to SStable(disk)=>compact sstables periodically
    'read': client=>bloom filter(memory)=>partition summary(memory)=>partition index(disk)=>compression offsets(memory)=>data(disk)=>client

    Nodes persist WRITE to a 'commit log'.
    Data is then indexed and written to an in-memory structure, called a 'memtable', which resembles a write-back cache.
    Once the memory structure is full, the data is written to disk in an 'SSTable' data file.
    A sorted string table (SSTable) is an immutable data file to which Cassandra writes memtables periodically.
    SSTables are append only and stored on disk sequentially and maintained for each Cassandra table.
    All writes are automatically 'partitioned and replicated' throughout the cluster using 'gossip' every second.
    Using 'compaction' Cassandra periodically consolidates SSTables, discarding obsolete data and tombstones (an indicator that data was deleted).

    commit log=>memtable=>sstable
    node=>datacenter(group of nodes in a location)=>cluster(across datacenters)

2.1
'gossip' is a peer-to-peer protocol that transmits state data every sec to synch up.
approx 3 seed nodes are required per datacenter to bootstrap gossip during restarts.

2.2 failure
'node failure' is determined by gossip quality
'phi_convict_threshold' is used to tune the failure determination sensitivity.
'hinted handoff' is done by replicas of died node which gather WRITE hints
'nodetool' is used to start/stop nodes and resynch state

2.3 partition
'partitioner' spreads the data using the hash of PK of every row.
Each node has multiple tokens leading to multiple 'vnodes'.
This leads to non-contiguous data distribution of partitioned data.
• 'Murmur3Partitioner' (default): uniformly distributes data across the cluster based on MurmurHash hash values.
    64-bit hash value, possible range of hash values is from 2^-63 to 2^+63-1.
• 'RandomPartitioner': uniformly distributes data across the cluster based on MD5 hash values. 0-(2^127-1)
• 'ByteOrderedPartitioner': keeps an ordered distribution of data lexically by key bytes

2.4 replication
'replication_factor'=1 means only 1 copy in 1 node, 2 means 2 copies in 2 nodes. there is no primary version, all are copies since no master.
'SimpleStrategy' => single data center
'NetworkTopologyStrategy' => multiple data centers
  •'Two replicas in each data center':
   This configuration tolerates the failure of a single node per replication group and still allows local reads at a consistency level of ONE.
  •'Three replicas in each data center':
  This configuration tolerates either the failure of a one node per replication group at a strong consistency level of LOCAL_QUORUM or multiple node failures per data center using consistency level ONE.

2.5 snitch
A 'snitch' defines groups of machines into data centers and racks (the topology) that the replication strategy uses to place replicas.
  'dynamic snitch'=>snitch layer that monitors read latency and, when possible, routes requests away from poorly-performing nodes.
  'simple snitch'=>single data center
  'rackinferring snitch'=>determines the location of nodes by rack and data center, which are assumed to correspond to the 3rd and 2nd octet of the node IP address.
  'propertyfile snitch'=>use /etc/cassandra/cassandra-topology.properties to map names to IPs
   SAMPLE
   If you had non-uniform IPs and two physical data centers with two racks in each, and a third logical data center for replicating analytics data, the cassandra-topology.properties file might look like this:

      Note: Data center and rack names are case-sensitive.
      # Data Center One
      175.56.12.105 =DC1:RAC1
      120.53.24.101 =DC1:RAC2
      # Data Center Two
      110.56.12.120 =DC2:RAC1
      110.54.35.184 =DC2:RAC1
      50.33.23.120 =DC2:RAC2
      50.45.14.220 =DC2:RAC2
      # Analytics Replication Group
      172.106.12.120 =DC3:RAC1
      172.106.12.121 =DC3:RAC1
    'ec2snitch, ec2multiregionsnitch, googlecloudsnitch, cloudstacksnitch' => meant for aws, google cloud and apache cloudstack
