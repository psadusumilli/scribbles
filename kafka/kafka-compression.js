REFERENCE
http://nehanarkhede.com/2013/03/28/compression-in-kafka-gzip-or-snappy/ - 2013
http://www.confluent.io/blog/compression-in-apache-kafka-is-now-34-percent-faster - 2015

NOTES
Compression needs more CPU but saves IO cost
2 popular compression codecs – gzip and snappy.
Multiple messages are bundled and compressed. Then the compressed messages are turned into a special kind of message and appended to Kafka’s log file.
Batching is to increase compression efficiency, i.e., compressors work better with bigger data.
Compression is now 34% faster because they recoded stuff on that bundled special message and bytearrayoutptu stream

'R 0.8 flow'
   1 Producer compresses message
   2 Broker:  leader decompresses data, assigns offsets, compresses it again and then appends the re-compressed data to disk. high CPU cost
   3 Consumer: readds compressed, then decompresses

'gzip vs snappy' - 2013
1 cpu load => 'snappy > gzip' in cpu consumption of consumer
2 Compression ratio =>  gzip was 2.8 x '>'  Snappy was 2 x
3 consumer thoughtput => gzip '=' Snappy ! why?
         though gzip compresses 30% better leading to lesser calls, high-level consumer has buffer queue which is populated in polls of 1 sec from broker.
         gzip would have more data (atfer compression) into that buffer queue than snappy
4 producer throughput =>
         snappy 150% > gzip : when ack by leader after write data to log
         snappy 228% > gzip : no ack, similar to Kafka 0.7 behavior
5 cross data center replication => gzip (30% more compression) > snappy
