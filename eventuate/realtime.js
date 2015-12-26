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
   'Materializer' : decides how the flow is implemented (akka actors or spark..)
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
      'versioned state' => state can be versioned with vector timestamps
      "batching" =>  uses batching to optimize read and write throughput.
      "conflict resolution" => can be automated or manual intervention.
         If an EA’s internal state is a CRDT, for example, the conflict can be resolved automatically (see also Eventuate’s operation-based CRDTs).
         If internal state is not a CRDT, Eventuate provides further means to track and resolve conflicts, either automatically or interactively.
      Eventuate the ConfirmedDelivery trait.

      "replication" via akka remoting - netty tcp

'Event source views'
     can read replicated events from actors but not produce.
     can persist to a datastore like cassandra for the query layer in CQRS.
     can read events of one actor if given a aggregate id, if not, can read all events of all actors

     --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
     EVENTUATE VS AKKA-PERSISTENCE
     0 persistactor/Eventsourcedactor, AtLeastOnceDelivery/ConfirmedDelivery
     1 PA must be singletons, EAs can be replicated
     2 No causality or global ordering of events of multiple PAs.

     3 'CAP':
       The write availability of an Akka Persistence application is constrained by the write availability of the underlying storage backend.
       According to the CAP theorem, write availability of a strongly consistent, distributed storage backed is limited.
       Consequently, the command side of an Akka Persistence application chooses CP from CAP.

       EAs and their underlying event logs remain writeable even during inter-location network partitions.
       From this perspective, a multi-location Eventuate application chooses AP from CAP.

     4 'Query':
       akka-persistence: persistence-view replaced with persistence-query
       storage plugins may provide support for 'aggregating events from multiple PAs' and serve the result as Akka Streams

       Eventuate: the query side can be implemented with EventsourcedViews (EVs).
       An EV can consume events from 'all EAs that share an event log', even if they are globally distributed.
       Events are always consumed 'in correct causal order'.
       An application can either have a single replicated event log or several event logs, organized around topics, for example.
       Future extensions will allow EVs to consume events from multiple event logs.
       An Akka Streams API in Eventuate is also planned.

     5 'storage':
       akka-persistence: 1 index per PA persistenceId
       Aggregating events from several PAs requires either the creation of an additional index in the storage backend or
       an on-the-fly event stream composition when serving a query

       eventuate: EAs without aggregateId share same event log

     6 'throughput':
       A consequence of synchronizing internal state with an event log is decreased throughput.
       Synchronizing internal state has a stronger impact in Akka Persistence because of the details how event batch writes are implemented.
       In Akka Persistence, events are batched on PA level, but only when using persistAsync.
       In Eventuate there’s a separate batching layer between EAs and the storage plugin, so that events emitted by different EA instances, even if they sync their internal state with the event log, can be batched for writing.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
PATTERNS
1 Actors serving as source ingestion agents  (file readers) : kafka => spray => router actor => processing actor => cassandra actor
2 modelling domain around cqrs ->
         aggregates like order, customer,
         derive aggregate lifecycle states as events
         one transaction /aggregate with compensating transactions. If compensation not ok, merge the aggregates into one.
