transaction management:
------------------------------------
Any transaction that spans across multiple systems will involve these players

AC	| application components 	=> 	clients to data sources like web apps
RM	| resource managers 		=> 	gateway to data sources like db/files
TM	| transaction managers 		=> 	coordinates transactions between application components and resource managers. 
									sends around a transaction context to all participants
2-phase commit 				=>	before a commit, in 1st phase TM checks with all RM, after they check back ok, then commit happens in 2nd phase
resource enlistment				=>  	register a resource with the TM.

industry standards
----------------------------
#1 	x/Open Distributed Transaction Processing Model : (DTP)
	tx_* methods implemented by TM
	xa_* methods implemented by RM
	xa+  methods implemented by varying TMs
	xa compliance- Oracle DB, Microsoft SQL Server, Microsoft Transaction Server, IBM MQ Series

#2 	Object Transaction Service (OTS) is a distributed transaction processing service specified by the Object Management Group (OMG). 
	This specification extends the CORBA model and defines a set of interfaces to perform transaction processing across multiple CORBA objects.
	The OTS model is based on the X/Open DTP model with the following enhancements:
		The OTS model replaces the functional XA and TX interfaces with CORBA IDL interfaces.
		The various objects in this model communicate via CORBA method calls over IIOP.	
	The OTS architecture consists of the following components:
		Transaction Client: A program or object that invokes operations on transactional objects.
		Transactional Object: A CORBA object that encapsulates or refers to persistent data, and whose behavior depends on whether or not its operations are invoked during a transaction.
		Recoverable Object: A transactional object that directly maintains persistent data, and participates in transaction protocols.
		Transactional Server: A collection of one or more transactional objects.
		Recoverable Server: A collection of objects, of which at least one of which is recoverable.
		Resource Object: A resource object is an object in the transaction service that is registered for participation in the two-phase commit and recovery protocol.
#3 	JTA /JTS- Java transaction API/Java Transaction Service
		JTS specifies the implementation of a Java transaction manager. 
		This transaction manager supports the JTA, using which application servers can be built to support transactional Java applications.
		 Internally the JTS implements the Java mapping of the OMG OTS 1.1 specifications. 
		The Java mapping is specified in two packages: org.omg.CosTransactions and org.omg.CosTSPortability. 
		Although the JTS is a Java implementation of the OMG OTS 1.1 specification, the JTA retains the simplicity of the XA and TX functional interfaces of the X/Open DTP model.
		The JTA specifies an architecture for building transactional application servers and defines a set of interfaces for various components of this architecture. 
		The components are: the application, resource managers, and the application server, 
#4 	Microsoft Transaction Server (MTS) is a component based transaction server for components based on the Microsoft's Component Object Model (COM).
#5  	Enterprise Java Beans (EJB) - address transaction processing, resource pooling, security, threading, persistence, remote access, life cycle apart from the transactions
		An enterprise bean is specified by two interfaces: the home interface and the remote interface. 
		The home interface specifies how a bean can created or found. 
		With the help of this interface, a client or another bean can obtain a reference to a bean residing in a container on an EJB server. 
		The remote interface specifies application specific methods that are relevant to entity or session beans.
	#5.1|EJB Transaction Model
	The EJB framework does not specify any specific transaction service (such as the JTS) or protocol for transaction management.
	However, the specification requires that the javax.transaction.UserTransaction interface of the JTS be exposed to enterprise beans. 
	This interface is required for programmatic transaction demarcation as discussed in the next section.
	Similar to the MTS, the EJB framework provides for declarative demarcation of transactions. 
	The container performs automatic demarcation depending on the transaction attributes specified at the time of deploying an enterprise bean in a container.

	#5.2| The following attributes determine how transactions are created.
	a) NotSupported: The container invokes the bean without a global transaction context.
	b) Required: The container invokes the bean within a global transaction context. 
		If the invoking thread already has a transaction context associated, the container invokes the bean in the same context. 
		Otherwise, the container creates a new transaction and invokes the bean within the transaction context.
	c) Supports: The bean is transaction-ready. If the client invokes the bean within a transaction, the bean is also invoked within the same transaction. Otherwise, the bean is invoked without a transaction context.
	d) RequiresNew: The container invokes the bean within a new transaction irrespective of whether the client is associated with a transaction or not.
	e) Mandatory: The container must invoke the bean within a transaction. The caller should always start a transaction before invoking any method on the bean.
	
	#5.3  types of transaction demarcation.
	a) Declarative Demarcation: This is also called as container managed demarcation. The container demarcates transactions on behalf of the bean. 
	The required attribute is specified in a deployment descriptor at the time of deploying the bean on an EJB server. The bean can use the javax.ejb.EJBContext.setRollbackOnly() method to mark the transaction for rollback.
	b) Bean Managed Demarcation: This is similar to the client-managed demarcation.
	c) Client Managed Demarcation: Java clients can use the javax.transaction.UserTransaction interface to demarcate transactions programmatically.




