LINUX VIRTUAL SERVER
LVS
    is a software tool that directs network connections to multiple servers that share their workload
    ensures HA and scalability
    comprises of loadbalancers, real-servers and shared storage
    loadbalancers talk to each other using ARP spoofing (gratutious ARP)
    loadbalancers uses IP loadbalancing techniques
//...............................................................................................................
IP LOADBALANCING TECHNIQUES
  'LVS/NAT'
    LB uses network-address-translation techniques to move the IP to real-server
    a real-server is selected from the cluster by a scheduling algorithm,
    the connection is added into the hash table which records connections.
    Then, the destination address and the port of the packet are rewritten to those of the selected server, and the packet is forwarded to the server.
    When an incoming packet belongs to an established connection, the connection can be found in the hash table and the packet will be rewritten and forwarded to the right server.
      When response packets come back, the load balancer rewrites the source address and port of the packets to those of the virtual service.
      When a connection terminates or timeouts, the connection record will be removed in the hash table.

    OK for private networks (10~20)
    pros: real-servers can run on any OS
    cons: LB cannot scale

  'LVS/TUN'
    LB embed a inner datagram inside the incoming datagram to route to real-server
    When the real-server receives the encapsulated packet, it decapsulates the packet and finds the inside packet is destined for VIP that is on its tunnel device,
    so it processes the request, and returns the result to the user directly.

    OK for WAN/LAN(~100)
    pros: can scale well as there is not much connection handling overhead,
      since lb directs request to real-server which replies directly to client
    cons: real-servers should be on linux

  'LVS/Direct routing'
    The load balancer and the real-servers must have one of their interfaces physically linked by an uninterrupted segment of LAN such as a HUB/Switch.
    The virtual IP address is shared by real-servers and the load balancer.
    the load balancer simply changes the MAC address of data frame to that of the server and retransmits it on the LAN.
    When the server receives the forwarded packet,
      the server finds that the packet is for the address on its loopback alias interface and processes the request,
      finally returns the result directly to the user.

    OK for LAN(~100)
    pros: no connection overhead, so scales well
    cons: server OS should have loopback alias interface that doesn’t do ARP response
//...............................................................................................................
 LOADBALANCING STRATEGIES
 Round-Robin
 Weighted Round-Robin
 Least-Connection
 Weighted Least-Connection

LIMITAITONS OF LVS
1 Since LVS just supporting IP load balancing techniques, it cannot do content-based scheduling
2 The failover or takeover of the primary load balancer to the backup will cause the established connection in the state table lost,
  which will require the clients to send their requests again to access the services.
