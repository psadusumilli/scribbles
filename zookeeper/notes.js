Apache ZooKeeper 
****************

is a high-performance coordination server for distributed applications. It exposes common services 
-- such as naming and configuration management, synchronization, and group services 
-- in a simple interface, relieving the user from the need to program from scratch. 
It comes with off-the-shelf support for implementing consensus, group management, leader election, and presence protocols. 

'Name service:'
	A name service is a service that maps a name to some information associated with that name. 
'Locking:'
	To allow for serialized access to a shared resource in your distributed system, you may need to implement distributed mutexes. 
	ZooKeeper provides for an easy way for you to implement them.
'Synchronization:'
	Hand in hand with distributed mutexes is the need for synchronizing access to shared resources. Whether implementing a producer-consumer queue or a barrier, 
	ZooKeeper provides for a simple interface to implement that.
'Configuration management:'
 	You can use ZooKeeper to centrally store and manage the configuration of your distributed system. 
 	This means that any new nodes joining will pick up the up-to-date centralized configuration from ZooKeeper as soon as they join the system.	

'client-server-ensemble'
------------------------
ZooKeeper follows a simple 'client-server model' where clients are nodes (i.e., machines) that make use of the service, and servers are nodes that provide the service. 
A collection of ZooKeeper servers forms a ZooKeeper 'ensemble'. 
At any given time, '1 client is connected to 1 server'. 
'1 server can handle a large number of client' connections at the same time. 
Each client periodically sends pings to the ZooKeeper server it is connected to let it know that it is alive and connected. 
The ZooKeeper server in question responds with an acknowledgment of the ping, indicating the server is alive as well. 
When the client doesnt receive an acknowledgment from the server within the specified time, 
the client connects to another server in the ensemble, and the client session is transparently transferred over to the new ZooKeeper server.

'znode'
------
ZooKeeper has a file system-like data model composed of znodes. 
Think of znodes (ZooKeeper data nodes) as files in a traditional UNIX-like system, except that they can have child nodes. 
Another way to look at them is as directories that can have data associated with themselves. 
Each of these directories is called a znode.

The 'znode hierarchy is stored in memory' within each of the ZooKeeper servers. 
This allows for scalable and quick responses to reads from the clients. 
Each ZooKeeper server also maintains a 'transaction log on the disk', which logs all write requests. 
This transaction log is also the most performance critical part of ZooKeeper because a ZooKeeper server must sync transactions to disk before it returns a successful response. 
The default maximum size of data that can be stored in a znode is 1 MB

'reads/writes'
------------
'Read' is always between any 1 server in ensemble and client
'Write' is given to 1 leader who in turn writes to a 'quorum' of 'N' servers. 
 in a ensemble of 3 servers, a strict majority of 2 servers should acknowledge the write request to make it successful.

	(ensemble: 1,quorum: 1, max_fail: 0) => no high availability
	(ensemble: 2,quorum: 2, max_fail: 0) => need both to be up to keep the service running, 1/2 is not majority
	(ensemble: 3,quorum: 2, max_fail: 1) => 2/3 is majority 
	(ensemble: 4,quorum: 3, max_fail: 1) => 3/4 is majority (no better than ensemble 3 )
	(ensemble: 5,quorum: 3, max_fail: 2) => 3/5 is majority 

higher nodes is too much quorum,so chatty,so high latency for every write. (kafka offset commits r slower)
adding more servers does not increase read performance, as it read is always between 1 server and client.

'logs'
------
As changes are made to the znodes these changes are appended to a 'transaction log'.
Occasionally, when a log grows large ('snapCount', a 'snapshot' of the current state of all znodes will be written to the filesystem. 
This snapshot supercedes all previous logs.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------
Quickie - 3 node cluster
*******
$ wget http://www.bizdirusa.com/mirrors/apache/ZooKeeper/stable/zookeeper3.4.5.
$ tar.gz tar xzvf zookeeper3.4.5.tar.gz

$ mkdir /var/lib/zookeeper

$ vim zookeeper3.4.5/conf/zoo.cfg
	tickTime=2000
	dataDir=/var/lib/zookeeper
	clientPort=2181
	initLimit=5
	syncLimit=2
	server.1=zkserver1.mybiz.com:2888:3888
	server.2=zkserver2.mybiz.com:2888:3888
	server.3=zkserver3.mybiz.com:2888:3888
2181 - client port
2888 - servers intercommunication
3888 - leader election	

The contents of this file would be just the numeral 1 on zkserver1.mybiz.com, numeral 2 on zkserver2.mybiz.com, and numeral 3 onzkserver3.mybiz.com.
$ vim /var/lib/zookeeper/myid
$ cat /var/lib/zookeeper/myid
1

---------------------------------------------------------
start 3 servers
$ bin/zkServer.sh start

start client to crud znodes
---------------------------
$ bin/zkCli.sh server
zkserver1.mybiz.com:2181,zkserver2.mybiz.com:2181,zkserver3.mybiz.com:2181

/create/
[zk: localhost:2181(CONNECTED) 3] create /mynode node1
Created /mynode

/fetch/
[zk: localhost:2181(CONNECTED) 4] get /mynode 
node1
cZxid = 0x2
ctime = Thu Sep 03 21:05:55 EDT 2015
mZxid = 0x2
mtime = Thu Sep 03 21:05:55 EDT 2015
pZxid = 0x2
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 5
numChildren = 0

/update/
create /mynode node1

/delete/
[zk: localhost:2181(CONNECTED) 5] rmr /mynode


$ bin/zkServer.sh stop
---------------------------------------------------------------------------------------------------------------------------------------------------------------------

Admin Guide
************
JDK 6+
minimum 3 servers. At Yahoo!, ZooKeeper is usually deployed on dedicated RHEL boxes, with dual-core processors, 2GB of RAM, and 80GB IDE hard drives.

heap is needed, since znodes are read from memory, Be conservative - 'use a maximum heap size of 3GB for a 4GB machine'.
tolerate the failure of 'F' machines, you should count on deploying '2xF+1' machines. 
dont share zookeeper with other software
zk provides durability by writing to log files sequentially, without seeking.
sharing your log device with other processes can cause seeks and contention, which in turn can cause multi-second delays.
A ZooKeeper server will not remove old snapshots and log files when using the default configuration
Automatic purging of the snapshots and corresponding transaction logs was introduced in version 3.4.0 
	and can be enabled via the following configuration parameters autopurge.snapRetainCount and autopurge.purgeInterval
Monitor your ZK processes with tools like daemontools SMF, nagios 	


Minimum Configuration
-----------------------
'clientPort':
The port to listen for client connections; that is, the port that clients attempt to connect to.

'dataDir':
The location where ZooKeeper will store the in-memory database snapshots and, unless specified otherwise, the transaction log of updates to the database.
Be careful where you put the transaction log. A dedicated transaction log device is key to consistent good performance. 
Putting the log on a busy device will adversely effect performance.

'tickTime':
The length of a single tick, which is the basic time unit used by ZooKeeper, as measured in milliseconds. 
It is used to regulate heartbeats, and timeouts. For example, the minimum session timeout will be two ticks.

optional configuration
-----------------------
'dataLogDir': 
location to store transaction logs, so that 'dataDir' contains only the snapshots

'maxClientCnxns':
max connection per IP to a server to prevent DOS attacks. 60 default

'leaderServes' : true default where leader also serves clients, can be turned off where it takes care of coordination work only
	
When running zkServer.sh autocreate can be disabled by setting the environment variable 'ZOO_DATADIR_AUTOCREATE_DISABLE to 1.'
this will stop creating invalid datadirs

NIO/Netty
----------
NIO is the default client/server communication subsystem. 
Its threading model comprises 1 'acceptor thread', 1-N 'selector' threads and 0-M socket I/O 'worker' threads. 
In the request processing pipeline the system can be configured to process multiple read request at once while maintaining the same consistency guarantee (same-session read-after-write). 
The Commit Processor threading model comprises 1 main thread and 0-N worker threads.
Post 3.4, Netty is used instead of directly using NIO APIs.

Admin server
-------------
ZK has a admin server,an embedded Jetty server that provides an HTTP interface to the four letter word commands. 
By default, the server is started on port 8080, and commands are issued by going to the URL "/commands/[command name]", e.g., http://localhost:8080/commands/stat
http://localhost:8080/commands

4 letter Commands
------------------
ZooKeeper responds to a small set of commands. Each command is composed of four letters. You issue the commands to ZooKeeper via telnet or nc, at the client port.

conf: New in 3.3.0: Print details about serving configuration.
cons: New in 3.3.0: List full connection/session details for all clients connected to this server. Includes information on numbers of packets received/sent, session id, operation latencies, last operation performed, etc...
crst: New in 3.3.0: Reset connection/session statistics for all connections.
dump: Lists the outstanding sessions and ephemeral nodes. This only works on the leader.
envi: Print details about serving environment
ruok: Tests if server is running in a non-error state. The server will respond with imok if it is running. Otherwise it will not respond at all.
		A response of "imok" does not necessarily indicate that the server has joined the quorum, just that the server process is active and bound to the specified client port. Use "stat" for details on state wrt quorum and client connection information.

srst: Reset server statistics.
srvr: New in 3.3.0: Lists full details for the server.
stat: Lists brief details for the server and connected clients.
wchs: New in 3.3.0: Lists brief information on watches for the server.
wchc: New in 3.3.0: Lists detailed information on watches for the server, by session. 
		This outputs a list of sessions(connections) with associated watches (paths). 
		Note, depending on the number of watches this operation may be expensive (ie impact server performance), use it carefully.

wchp: New in 3.3.0: Lists detailed information on watches for the server, by path. 
		This outputs a list of paths (znodes) with associated sessions. 
		Note, depending on the number of watches this operation may be expensive (ie impact server performance), use it carefully.

mntr:  New in 3.4.0: Outputs a list of variables that could be used for monitoring the health of the cluster.
