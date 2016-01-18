SECURITY
   1  authenticate users using either Kerberos or TLS client certificates, so we now know who is making each request to Kafka.
   2  unix-like permissions system to control which users can access which data.
   3  ssl encryption on the wire to protect sensitive data on an untrusted network, not enabled by default
   4  only new producer consumer have these features.

KAFKA CONNECT
   1 Kafka Connect facilitates large-scale, real-time data import and export for Kafka.
   2 It abstracts away common problems that each such data integration tool needs to solve to be viable for 24x7 production environments:
      fault tolerance, partitioning, offset management and delivery semantics, operations, and monitoring.
   3 It offers the capability to run a pool of processes that host a bunch of Kafka connectors while handling load balancing and fault tolerance.

MULTI-TENANCY (QUOTA)
   Multiple users sharing the same cluster with different SLA
   Users have the ability to enforce quotas on a per-client basis.
   'Producer-side quotas' are defined in terms of bytes written per second per client id while 'consumer quotas' are defined in terms of bytes read per second per client id.
   Each client receives a default quota (e.g. 10MBps read, 5MBps write) which can be overridden dynamically on a per-client basis.
   The per-client quota is enforced on a per-broker basis. For instance, each client can publish/fetch a maximum of X MBps per broker before it gets throttled.
   Kafka throttles in case of quota violation.

CONSUMER
   New unified API - best of high and low level consumer apis
   Consumer need not know Zookeeper details, broker manages it now.
   Better group loadbalancing
   More control on offset committing.
