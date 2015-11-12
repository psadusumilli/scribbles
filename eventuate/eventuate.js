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

'--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
