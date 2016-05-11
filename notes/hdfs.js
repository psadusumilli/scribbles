LINKS
1 https://developer.yahoo.com/hadoop/tutorial/module2.html

Precursor was NFS where remote directories stored on 1 machines is shared to multiple client as if they are local drives

PRINCIPLES
   'No random reads' Applications that use HDFS are assumed to perform long sequential streaming reads from files.
   HDFS is optimized to provide streaming read performance; this comes at the expense of random seek times to arbitrary positions in files.

   'No updates to data' Data will be written to the HDFS once and then read several times; updates to files after they have already been closed are not supported.
   (An extension to Hadoop will provide support for appending new data to the ends of files; it is scheduled to be included in Hadoop 0.19 but is not available yet.)

   'No caching'
   Due to the large size of files, and the sequential nature of reads, the system does not provide a mechanism for local caching of data.
   The overhead of caching is great enough that data should simply be re-read from HDFS source.

   'Data replicated'
   is designed to store a very large amount of information (terabytes or petabytes). This requires spreading the data across a large number of machines. It also supports much larger file sizes than NFS.
   should provide fast, scalable access to this information. It should be possible to serve a larger number of clients by simply adding more machines to the cluster.
   should integrate well with Hadoop MapReduce, allowing data to be read and computed upon locally when possible.
   Individual machines are assumed to fail on a frequent basis, both permanently and intermittently.
   The cluster must be able to withstand the complete failure of several machines, possibly many happening at the same time (e.g., if a rack fails all together).
   While performance may degrade proportional to the number of machines lost, the system as a whole should not become overly slow, nor should information be lost. Data replication strategies combat this problem.

STRUCTURE
   big blocks 64-128 MB >> small blocks of EXT NFS 4-8 KB
   so not much metadata per block
   files stored are assumed to be big long sequentially read files
   'datanodes' store the data blocks with each block having a unique id
   'namenode' stores metadata - 'logical filename -> block-ids'
            'usr/fimon/file1' -> 1, 3, 4  'usr/fimon/file2' -> 2, 5
            namenode failure is far severe than datanode
   POSIX tools wont work with hdfs blocks, use hdfs shell

INSTALL
1 download and unzip apache hadoop | wget http://www-us.apache.org/dist/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz
2  vim /home/vijayrc/tools/hadoop-2.7.2/etc/hadoop.hdf-site.xml | do it in each machine of the hdfs cluster
            <configuration>
               <property>
                  <name>fs.default.name</name>
                  <value>hdfs://vijayrc:9000</value>
               </property>
               <property>
                  <name>dfs.data.dir</name>
                  <value>/home/vijayrc/tools/hdfs/data</value>
               </property>
               <property>
                  <name>dfs.name.dir</name>
                  <value>/home/vijayrc/tools/hdfs/name</value>
               </property>
            </configuration>
3 mkdir -p home/vijayrc/tools/hdfs/data, home/vijayrc/tools/hdfs/name
4
