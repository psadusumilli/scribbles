
batch data model:
*****************
	fact-based data, so adding time-dimension to all incoming data.
	graph schema, with nodes, edges and properties give the relations between facts

	schema/serialization systems => enforces the data schema across lanugages; xsd, json (old mature frameworks) ; thrift, avro
	would serialization frameworks really suffice for a rich domain like us?

	cda is close to a flat graph format, but needs serdes to be written for every engagement
	need to work on the cda alternative
	
	compaction must be kept in mind
	


batch data validation
**********************
	occurs at entry into batch layer ie factory, no bad data inside or do validation at 
	same validation rules must also apply during entry into speed layer or do we wait for batch layer to correct it?
	validation rules must be write-once and pluggable-anywhere as a library
	does dep provide an plugpoint for normalization rules?

batch view computation:
***********************
	pesistence forms (say custom flat-file) and processing forms (say RDD) are different
	inspiration: GraphX RDD, Git, clojure Persistent data structures
	no data movement, colocation of related data and processing.




'Wild theories':
1 Why not ElasticSearch for Serving layer?
	gives sharding, compression, replication, failover
2 Must look into Git internals again, we might get some insights there.
3 Must look into Persistent 



pluggable tools for each layer
resources and apis
microservices 
