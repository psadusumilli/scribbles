'Websphere Server':
#-----------------
#-------------------------------------------------------------------------------------------------------------------------------------
CHAP1:intro
************
'versions:'
Express V8.5 => A lower-cost, readyto-go solution to build dynamic websites and applications, no clustering
Developers V8.5 => for local developer setup, free
Base V8.5 => single node
Network Deployment V8.5 => multi-nodes, clustering, deployment manager
z/OS V8.5 => tuned for z/os

'management tools'
IBM installation manager=> a repo of packages and patches
administrative agent=> manager multiple nodes and servers.
centralised installation manager=>simplify installation steps and to apply maintenanceon system
job manager=>asynchronously submit and administer jobs to servers and administrative agents. 
		The jobs can manage applications,modify production configuration, start and stop applications, and distribute files.
high performance extensive logging=>coordinate logs across multiple nodes.

'interoperability':
webservices=>jaxws, jaxrs, soap 1.1
messaging and transactions=>jms 1.1 default messaging provider, connects with MQ, JTA, JCA.

#-------------------------------------------------------------------------------------------------------------------------------------
CHAP2: concepts
****************
Can run:
'Java Platform Enterprise Edition applications'=> 
	support Java EE6 specs
	packaged as ear, built with RAD
	web/ejb container
'Portlet applications'=>
	Portlet applications are intended to be combined with other portlets collectively to create a single page of output('JSR 286')
	packaged as ear/war, built with RAD
	portlet container
'Session Initiation Protocol applications'=>
	SIP is used to establish, modify, and terminate multimedia IP sessions.'JSR 289'
	SIP negotiates the medium, the transport, and the encoding for the call. 
	After the SIP call is established, the communication takes place over the specified transport mechanism, independent of SIP. 
	Examples of application types that use SIP include voice over IP (VOIP), click-to-call, and instant messaging.
	sip container
'Business-level applications'=>
	Business-level applications have a grouping notion. 
	It includes WebSphere artifacts, such as Java EE artifacts and Service Component Architecture (SCA) packages, libraries, and proxy filters under a single application definition.
'OSGi applications'=>
	that use the Java EE 6 and OSGi R4 V4.2 Service Platform technologies. uses Eclipse Equinox 3.6.
'Batch applications'=>
	implemented as simple Java classes, and run according to job definitions described in xJCL job control language.
	batch container

'Topology':
------+------
'profiles'=>
	that represent a WebSphere Application Server configuration consisting of 
	'Product files' are a set of read-only static files or product binary files that are shared by any instances of WAS.
	'Configuration files (profiles)' are a set of user-customizable data files. 
	This file set includes WebSphere configuration, installed applications, resource adapters, properties,and log files.
'Cell'=>
	is a virtual unit that is built of a 'Deployment Manager' and one or more 'nodes';
'Deployment Manager'=>
	is a process (in fact it is an special WebSphere instance) responsible for managing the Applications, Connection Pools and other resources related to a J2EE environment. 
	It is also responsible for centralizing user repositories for application and also for WebSphere authentication and authorization.
'Node agent'=>
	Deployment Manager communicates with the Nodes through another special WebSphere process, the Node Agent.
	The Node Agent it the process responsible for spawning and killing server processes and also responsible for configuration synchronization between the Deployment Manager and the Node. 
	Extra care must be taken when changing security configurations for the cell, since communication between Deployment Manager and Node Agent is ciphered and secured when security is enabled.
	Node Agent needs to have configuration fully resynchronized when impacting changes are made to Cell security configuration.
'Node'=>
	an administrative grouping of application servers for configuration and operational management within one operating system instance
'administrative agent'=>
	is a component that provides enhanced management capabilities for stand-alone application servers.
'job manager'=>
	run management jobs/scripts on administrative agents, deployment managers.
#------------------------------------------------------------------------------------------------------ 

'WebServer'
managed=> the http server is controlled by a node agent running on it.
unmanaged=> no node agent, manual push (file copy-paste) of plugin files
ibm-unmanaged=> no node agent, but files pushed via http

'security'
administrative(wsadmin, console users), 
application(security role mapping to ldap,identity systems) 
java security policies

'service integration'
1|jms 1.1=>default messaging provider
2|service integration bus (bus)=> is the communication infrastructure that provides service integration through messaging. 
	It is an administrative concept that is used to configure and host messaging resources like jms, webservices, more like a mini esb.
3|webservice gateway=> serves as a front to webservices running inside the server. (just like b2 router)

'clustering'
vertical=>servers in same machine
horizontal=>servers in different machines
mix=>some in same machines, some across machines
supports HTTP session memory-to-memory replication,and session database persistence that can replicate session data between cluster members.

#-------------------------------------------------------------------------------------------------------------------------------------
CHAP3: integration with other software
*****************************************
1|'Tivoli Access Manager':
TAS client is bundled with WAS Network Deployment.
All communication between the clients and the TAM server is run through the Java Authorization Contract for Containers (JACC) application programming interface (API).
The 'WebSEAL' server is a resource manager in the Tivoli Access Manager architecture that you can use to manage and protect web content resources. 
WebSEAL works as a reverse HTTP/HTTPS proxy server in front of the web servers or application servers. It

2|'Tivoli LDAP'
If you use a federated repository, choose from the following repository solutions based on LDAP:
	Single LDAP (full LDAP tree)
	Subtree of an LDAP (used only when a group in LDAP needs access to WebSphere Application Server)
	Multiple LDAPs (uses a unique user ID through all the LDAP trees)

3|'MQ'
WAS provides 3 messaging systems
	default messaging provider=>JMS 1.1 compliant system with queue implemented by SIB, which in turn can connect to MQ, Webservices.
	MQ provider=>direct MQ 	implemented queues
	3rd party providers=>for others
4| 'DataPower'
Dp boxes can be controlled by WAS admin console in 8.5	

5|'DB2'
normal JDBC datasources
connect via high-performing IBM PureQuery
datastore for scheduler jobs, session data, UDDI.
can be configured as a SIB member

6|'Tivoli Workload Scheduler TWS'
helps you establish an enterprise workload automation backbone by driving composite workloads according to business policies.
WSGrid workload connector connects WAS and TWS









