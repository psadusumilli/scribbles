http://presos.dsyer.com/decks/cloud-boot-netflix.html


1 'Netflix Archaius'-> Library for 'configuration management' API 
	Dynamic, Typed Properties
	High throughput and Thread Safe Configuration operations
	A polling framework that allows obtaining property changes of a Configuration Source
	A Callback mechanism that gets invoked on effective/"winning" property mutations (in the ordered hierarchy of Configurations)
	A JMX MBean that can be accessed via JConsole to inspect and invoke operations on properties
	Out of the box, Composite Configurations (With ordered hierarchy) for applications (and most web applications willing to use convention based property file locations)
	Implementations of dynamic configuration sources for URLs, JDBC and Amazon DynamoDB
	Scala dynamic property wrappers

https://github.com/Netflix/archaius/wiki
http://www.java-allandsundry.com/2015/05/netflix-archaius-for-property.html

----------------------------------------------------------------------------------------------------------------------------------------------------------------------
2 'Consul' is a tool for 'service discovery, monitoring and configuration'. It is distributed, highly available, and extremely scalable.

	'Service Discovery'
		Consul makes it simple for services to register themselves and to discover other services via a DNS or HTTP interface. 
		External services such as SaaS providers can be registered as well.
	'Health Checking'
		Health Checking enables Consul to quickly alert operators about any issues in a cluster. 
		The integration with service discovery prevents routing traffic to unhealthy hosts and enables service level circuit breakers.
	'Key/Value Storage'
		A flexible key/value store enables storing dynamic configuration, feature flagging, coordination, leader election and more. 
		The simple HTTP API makes it easy to use anywhere.
	'Multi-Datacenter' - Consul is built to be datacenter aware, and can support any number of regions without complex configuration.

http://www.consul.io 
https://github.com/hashicorp/consul
http://www.consul.io/intro/getting-started/install.html
http://www.consul.io/docs
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
3 'Netflix Eureka' 'Service Discovery'
Eureka is a REST based service that is primarily used in the AWS cloud 
for 'locating services for the purpose of load balancing and failover of middle-tier servers'.

At Netflix, Eureka is used for the following purposes apart from playing a critical part in mid-tier load balancing.
For aiding Netflix Asgard - an open source service which makes cloud deployments easier, in
	Fast rollback of versions in case of problems avoiding the re-launch of 100s of instances which could take a long time.
	In rolling pushes, for avoiding propagation of a new version to all instances in case of problems.
	For our cassandra deployments to take instances out of traffic for maintenance.
	For our memcached caching services to identify the list of nodes in the ring.
	For carrying other additional application specific metadata about services for various other reasons.

https://github.com/Netflix/eureka
https://github.com/Netflix/eureka/wiki
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
4 'Netflix Curator' -'ZooKeeper client' wrapper and rich ZooKeeper framework 
http://netflix.github.com/curator
http://curator.apache.org/
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
5 'Netflix Zuul'
Zuul is the 'front door' for all requests from devices and web sites to the backend of the Netflix streaming application. 
As an edge service application, Zuul is built to enable dynamic routing, monitoring, resiliency and security. 
It also has the ability to route requests to multiple Amazon Auto Scaling Groups as appropriate.

https://github.com/Netflix/zuul/wiki
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
6 'Netflix Hystrix'
Hystrix is a 'latency and fault tolerance library' designed to isolate points of access to remote systems, services and 3rd party libraries, 
stop cascading failure and enable resilience in complex distributed systems where failure is inevitable.

https://github.com/Netflix/Hystrix