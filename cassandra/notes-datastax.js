Cassandra 3.* from DataStax





//.........................................................................................................................................................
CHAP2
*********

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
