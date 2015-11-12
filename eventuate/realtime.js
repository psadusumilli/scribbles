REALTIME LAYER DESIGN

AKKA BASIC
An actor is a container for 'State, Behavior, a Mailbox, Children and a Supervisor Strategy', all of which is encapsulated behind an Actor Reference.
Distributed by default: design from keeping everything remote, then move to local by optimising.
         'scaling' => with routers, remote/local actors and using parallel cores
         'hierarchial actors' => root, parent & children
         'become/unbecome' => actor can change behavior on demand
         'on-error' => child can report errors to parent who can 'escalate, resume, stop, restart'
         'mailboxes' =>    multiple pluggable types or custom
         'dispatchers' => actors are backed by thread pools, many types

'asynchronous messaging' with serialised messages over the wire, including closures.
         • "at-most-once" -  message is delivered 0-* times,  messages may be lost, good performance and cheap
         • "at-least-once" -  message is delivered 1-* times,  messages may be duplicated but not lost, require idemptency
         • "exactly-once" -  message is delivered 1 time, messages can neither be lost nor duplicated,  very expensive,state to be kept at the receiving end in order to filter out duplicate deliveries.
         • "tell" '!' means 'fire-and-forget', e.g. send a message asynchronously and return immediately.
         • "ask" '?' sends a message asynchronously and returns a 'Future' representing a possible reply.

4 core operations
      create (2.7 million/GB RAM)
      send
      supervise
      become

'cluster'

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PERSISTENCE
entity actors backed by pluggable journals
eventsouring can replay state
high throughput because of append-only state, no mutation
'cqrs' : command => validation => events persisted to journal => change state of actor => take journal snapshot

      'receiveCommand'(event)
         1 'persist'(event) (event-handler) =>
            1.1 event-handler has handle to sender
            1.2 events are 'stashed' 1 event and its event-handler execution (blocking), so low throughput
         2 'persistAsynch' => no stashing (non-blocking), batch writes also increase throughput
         3 'deferAsynch' => recommended for read commands
      'receiveRecover'(event)
         event from persist
         snap

'Query'
'PersistentView' (deprecated) is tied to reading from one entity event stream, but Persistence Query allows using 'Akka Streams' module for querying the journal and retrieving streams of events.
'types of queries'
         1 AllPersistenceIdsQuery  => all the persistenceIds in the live journal
         2 CurrentPersistenceIdsQuery => if your usage does not require a live stream
         3 EventsByPersistenceId =>query equivalent to replaying a PersistentActor
         4 CurrentEventsByPersistenceIdQuery => if your usage does not require a live stream
         5 EventsByTag  => allows querying events regardless of which persistenceId they are associated with.

the data stored in the 'write-side' needs to be projected into the other read-optimised datastore (realtime views )
journal => persistent query => realtime views

'serialization'
Default is java serialization, dont use it for Prod.
pluggable schema mechanisms -> Avro, Thrift, ProtocolBuf..
serialization bindings can be at each message type level.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------s
STREAMING
'Intent': Fix all shortcomings in plain actor messaging, add 'backpressure, buffering, transformations and failure recovery'.
   'Source': something with exactly one output stream
   'Sink': something with exactly one input stream
   'Flow': something with exactly one input and one output stream
   'BidirectionalFlow': something with exactly two input streams and two output streams that conceptually behave like two Flows of opposite direction
   'Graph': a packaged stream processing topology that exposes a certain set of input and output ports, characterized by an object of type Shape.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
HTTP
'Spray' is deprecated, akka-http is the Spray 2.0

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
EVENTUATE
in 'akka-persistence', event-sourced actors must be 'cluster-wide singletons.'
With eventuate, it allows several instances of an event-sourced actor to be updated concurrently on multiple nodes and conflicts (if any) to be detected and resolved.
Also, it supports event aggregation from several (even globally distributed)  producers in a scalable way together with a deterministic replay of these events.

'Eventsourced actors'
      allows 'multiple locations to concurrently update replicated application state (multi-master)'
      supports interactive and automated 'conflict resolution strategies in case of conflicting updates' (incl. Operation-based 'CRDTs').
      Events captured at a location are stored in a 'local event log' and asynchronously replicated to other locations based on a 'replication protocol' that preserves the causality
      Eventuate has implemented its own asynchronous inter-site replication independent from concrete event storage backends such as LevelDB, Kafka, Cassandra or whatever

      'vector clocks' for tracking causality between events
      A location shall also remain available for writes if there are inter-site network partitions. When a partition heals, updates from different sites must be merged and conflicts (if any) resolved.

      'local storage': Asynchronous event replication across locations is independent of the storage technologies used at individual locations (LevelDB, Kafka, Cassandra)
      storage backends for local event log can be anything from small filedb in mobile device to cassandra cluster on a datacenter

      'ordering' - strong in local storage, no guarantees of global ordering among many replicated local storage
      'cqrs' : command => validation => events persisted to journal => change state of actor => take journal snapshot
      "event collaboration" can be for state synch between actors of same type, or for a business flow between actors of different types.

'Event source views'
     can read replicated events from actors but not produce.
     can persist to a datastore like cassandra for the query layer in CQRS.

Eventuate internally uses batching to optimize read and write throughput.


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PATTERNS
1 Actors serving as source ingestion agents  (file readers) : kafka => spray => router actor => processing actor => cassandra actor
