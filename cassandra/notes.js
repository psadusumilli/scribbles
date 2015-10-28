

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
