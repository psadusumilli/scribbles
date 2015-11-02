
0 INTRODUCTION
***************
    highly available, AP from CAP
    no master, a ring of similar nodes talking via 'gossip'
    linear scalability by more nodes 
    nodes that fail can easily be restored or replaced.
    multi-datacente spread
    flexible data model
    compression
    tunable data consistency - lenient to strict
    cql
    apple, facebook, twitter, comcast, spotify


0.1 rdbms comparision
----------------------
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

0.2 reads/writes
----------------
    'write': client =>commit log (disk)=>memtable(memory)=>spill over to SStable(disk)=>compact sstables periodically
    'read': client=>bloom filter(memory)=>partition summary(memory)=>partition index(disk)=>compression offsets(memory)=>data(disk)=>client

0.3 Cluster
------------
    data is sharded based on default or custom partitioner
    keyspaces are replicated across nodes: replication_factor=0,1,2...
    sharding and replication auto-balance on adding/removing nodes

0.4 data model
--------------
    Cassandra is a wide-row-store database that uses a highly denormalized model designed to capture and query data performantly. 
    Although Cassandra has objects that resemble a relational database (e.g., tables, primary keys, indexes, etc.), 
    Cassandra data modeling techniques necessarily depart from the relational tradition.
    Can have 1000+ columns unlike rdbms

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


0.5 transactions
----------------
    Only 'AID' portion of 'ACID'. Writes to Cassandra are atomic, isolated, and durable. 
    The 'C' of ACID—​consistency—​does not apply to Cassandra, as there is no concept of referential integrity or foreign keys.
    tunable data consistency across a database cluster. 
    You might want a particular request to complete if just one node responds, or you might want to wait until all nodes respond. 
    'Tunable data consistency' is supported across single or multiple data centers, and you have a number of different consistency options from which to choose.
    Consistency is 'configurable on a per-query basis', meaning you can decide how strong or eventual consistency should be per SELECT, INSERT, UPDATE, and DELETE operation. 
    For example, if you need a particular transaction to be available on all nodes throughout the world, you can specify that all nodes must respond before a transaction is marked complete. 
    On the other hand, a less critical piece of data (e.g., a social media update) may only need to be propagated eventually, so in that case, the consistency requirement can be greatly relaxed.
    
    Cassandra also supplies 'lightweight transactions', or a compare-and-set mechanism. 
    Using and extending the Paxos consensus protocol (which allows a distributed system to agree on proposed data modifications with a quorum-based algorithm, 
        and without the need for any one "master" database or two-phase commit), Cassandra offers a way to ensure a transaction isolation level similar to the serializable level offered by relational database.


-------------------------------------------------------------------------------------------------------------------------------------------
1 QUICKSTART
*************

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
    cqlsh> use mykeyspace
       ... ;
    cqlsh:mykeyspace> CREATE TABLE users (
                  ...   user_id int PRIMARY KEY,
                  ...   fname text,
                  ...   lname text
                  ... );
    cqlsh:mykeyspace> INSERT INTO users (user_id,  fname, lname)
                  ...   VALUES (1745, 'john', 'smith');
    cqlsh:mykeyspace> INSERT INTO users (user_id,  fname, lname)
                  ...   VALUES (1744, 'john', 'doe');
    cqlsh:mykeyspace> INSERT INTO users (user_id,  fname, lname)
                  ...   VALUES (1746, 'john', 'smith');
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

------------------------------------------------------------------------------------------------------------------------------------------------------


