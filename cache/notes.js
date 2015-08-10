Common Cache Attributes 
************************
	• maximum size, e.g. quantity of entries 
	• cache algorithm used for invalidation/eviction, e.g.: 
		• least recently used (LRU) 
		• least frequently used (LFU) 
		• FIFO 
	• eviction percentage 
	• expiration, e.g.
		• time-to-live (TTL) 
		• absolute/relative time-based expiration

Cache access patterns:
*********************
	'cache-aside': 
		app is responsible for r/w in cache, cache does not interact with storage			
	'read-through': 
		app calls storage through cache, so cache manages everything
	'write-behind-pattern': 
		cache periodically drains modified entries into storage, offer higher throughput than read-through, but conflicts can happen bcos of external updates to storage.
	'refresh-ahead-pattern': 
		recently fetched entries are refreshed ahead of expiration
cache types:
************
	'local cache':
		one node only, not scalable 
		ehcache w/o terracotta, guava
	'replicated cache':
		any new/modified entry is replicated across nodes by talking to each other 
		good for reads, bad for writes, that too with the multiple process talking to each other over network
		echcache+terracotta, oracle coherence
		


