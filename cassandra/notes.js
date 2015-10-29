
0 INTRODUCTION
***************
    highly available, AP from CAP
    no master, a ring of similar nodes
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


