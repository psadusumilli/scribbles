'Akka Persistence' - AKKA 2.4

enables stateful actors to persist their internal state so that it can be recovered when
	1 an actor is started,
	2 restarted after a JVM crash or by a supervisor,
	3 migrated in a cluster.
The key concept behind Akka persistence is that only changes to an actor internal state are persisted but never its current state directly (except for optional snapshots).
These changes are only ever appended to storage, nothing is ever mutated, which allows for very high transaction rates and efficient replication.
Stateful actors are recovered by replaying stored changes to these actors from which they can rebuild internal state.
This can be either the full history of changes or starting from a snapshot which can dramatically reduce recovery times.
Akka persistence also provides point-to-point communication with at-least-once message delivery semantics.

'persistence plugins'
			1 including in-memory heap based journal,
			2 local file-system based snapshot-store
			3 LevelDB based journal.

'Architecture':
			1 'PersistentActor':
				write events to journal, can replay and restore state.
				can be both command or event-sourced actor
			2 'PersistentView': (deprecated, now Persistent Query)
				can read from the events from PersistentActor, but cannot produce events itself
			3 'AtLeastOnceDelivery':
				To send messages with at-least-once delivery semantics to destinations, also in case of sender and receiver JVM crashes.
			4 'AsynchWriteJournal':
				persistence of events to pluggable storages
			5 'snapshot'
				hasten recovery time, pluggable storages

'Event Sourcing'
			A persistent actor receives a (non-persistent) 'command' which is first 'validated' if it can be applied to the current state.
			Here, validation can mean anything, from simple inspection of a command message fields up to a conversation with several external services, for example.
			If validation succeeds, 'events are generated' from the command, representing the effect of the command.
			These 'events are then persisted and, after successful persistence, used to change the actor's state.
			When the persistent actor needs to be recovered, only the persisted events are replayed of which we know that they can be successfully applied.
			In other words, events cannot fail when being replayed to a persistent actor, in contrast to commands.
			Event sourced actors may of course also process commands that do not change application state, such as 'query commands', for example.

'------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
PERSISENT ACTOR
				'receiveCommand'(event)
					1 'persist'(event) (event-handler) =>
						1.1 event-handler has handle to sender
						1.2 events are 'stashed' 1 event and its event-handler execution (blocking), so low throughput
					2 'persistAsynch' => no stashing (non-blocking), batch writes also increase throughput
					3 'deferAsynch' => recommended for read commands

				'receiveRecover'(event)
					event from persist
					snap

			Nested => persist, persistAsynch can be nested within each other, ordering is guranteed.

			'Failure'
			If persistence of an event fails,
				'onPersistFailure' will be invoked and the actor will unconditionally be stopped.
			If persistence of an event is rejected before it is stored, e.g. due to serialization error,
				'onPersistRejected' will be invoked and the actor continues with next message.
			If there is a problem with recovering the state of the actor from the journal when the actor is started,
				'onRecoveryFailure' is called (logging the error by default) and the actor will be stopped.

			'Deletion' => rarely used, but mostly in conjuction with snapshotting
			Stopping a PersistentActor with PoisonPill message can cause stahed commands to be lost prematurely

			'snapshots'
			Persistent actors can save snapshots of internal state by calling the saveSnapshot method
			(SaveSnapshotSuccess message, otherwise a SaveSnapshotFailure message

					var state: Any = _

					override def receiveCommand: Receive = {
							  case "snap"                                => saveSnapshot(state)
							  case SaveSnapshotSuccess(metadata)         => // ...
							  case SaveSnapshotFailure(metadata, reason) => // ...
					}
					override def receiveRecover: Receive = {
							  case SnapshotOffer(metadata, offeredSnapshot) => state = offeredSnapshot
							  case RecoveryCompleted                        =>
							  case event                                    => // ...
					}
			snapshots can be deleted, have a lifecycle on its own
			'------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
PERSISTENCE QUERY
							PersistentView is deprecated. Use 'Persistence Query' instead.
							PersistentView is tied to reading from one entity event stream, but Persistence Query allows using 'Akka Streams' module for querying the journal and retrieving streams of events.
							As of 2.4, its experimental
							Must be implemented by backing journal systems lke MySQL, Cassandra.
							From design perspective, Persistence Query should NOT be the Query in CQRS, it can be the feeder to a separate datastore for Query (in DEP, realtime views)

							'sample'
										object StreamingJournalQueries {
												  def main(args: Array[String]): Unit = {
												    implicit val system = ActorSystem()
												    implicit val materializer = ActorMaterializer()

												    // create a ReadJournal for LevelDB journal backend
												    val readJournal = PersistenceQuery(system).readJournalFor[LeveldbReadJournal](LeveldbReadJournal.Identifier)
												    // create a query for all events associated with a specific persistent entity as a Source of events
												    val source: Source[EventEnvelope, Unit] = readJournal.eventsByPersistenceId("entity-1234", 0, Long.MaxValue)
												    // materialize the query with a Sink printing out events
												    source.runForeach(envelope ⇒ println(envelope.event))
												  }
										}

							'types of queries'
							1 AllPersistenceIdsQuery  => all the persistenceIds in the live journal
							2 CurrentPersistenceIdsQuery => if your usage does not require a live stream
							3 EventsByPersistenceId =>query equivalent to replaying a PersistentActor
							4 CurrentEventsByPersistenceIdQuery => if your usage does not require a live stream
							5 EventsByTag  => allows querying events regardless of which persistenceId they are associated with.
																						 The goal of this query is to allow querying for all events which are "tagged" with a specific tag.
							'EventsByTag'
							WRITE
							public class MyTaggingEventAdapter implements WriteEventAdapter {
												  @Override
												  public Object toJournal(Object event) {
														    if (event instanceof String) {
																      String s = (String) event;
																      Set<String> tags = new HashSet<String>();
																      if (s.contains("green")) tags.add("green");
																      if (s.contains("black")) tags.add("black");
																      if (s.contains("blue")) tags.add("blue");
																      if (tags.isEmpty())
																        return event;
																      else
																        return new Tagged(event, tags);
														    } else {
														      		return event;
														    }
												  }
												  @Override
												  public String manifest(Object event) {
												    return "";
												  }
						}
						READ
						final Source<EventEnvelope, BoxedUnit> blueThings = readJournal.eventsByTag("blue", 0L);
						final Future<List<Object>> top10BlueThings =   (Future<List<Object>>) blueThings.map(t -> t.event()).take(10) // cancels the query stream after pulling 10 elements
																														    .<List<Object>>runFold(new ArrayList<>(10), (acc, e) -> {  acc.add(e); return acc; }, mat);
						Source<EventEnvelope, BoxedUnit> blue = readJournal.eventsByTag("blue", 10);// start another query, from the known offset

MATERIALIZED VIEWS

For example, in a bidding system it is important to "take the write" and respond to the bidder that we have accepted the bid as soon as possible,
which means that write-throughput is of highest importance for the write-side – often this means that data stores which are able to scale to accomodate these requirements have a less expressive query
On the other hand the same application may have some complex statistics view or we may have analists working with the data to figure out best bidding strategies and trends –
this often requires some kind of expressive query capabilities like for example 'SQL or writing Spark jobs' to analyse the data.
Therefore the data stored in the write-side needs to be projected into the other read-optimised datastore.

When refering to Materialized Views in Akka Persistence think of it as "some persistent storage of the result of a Query".
In other words, it means that the view is created once, in order to be afterwards queried multiple times, as in this format it may be more efficient or interesting to query it (instead of the source events directly).

'--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
ATLEAST ONCE DELIVERY
			To send messages with at-least-once delivery semantics to destinations you can mix-in 'AtLeastOnceDelivery' trait to your PersistentActor on the sending side.
			It takes care of re-sending messages when they have not been confirmed within a configurable timeout.

					import akka.actor.{ Actor, ActorSelection }
					import akka.persistence.AtLeastOnceDelivery

					case class Msg(deliveryId: Long, s: String)
					case class Confirm(deliveryId: Long)
					sealed trait Evt
					case class MsgSent(s: String) extends Evt
					case class MsgConfirmed(deliveryId: Long) extends Evt

					class MyPersistentActor(destination: ActorSelection)  extends PersistentActor with AtLeastOnceDelivery {
								  override def persistenceId: String = "persistence-id"
								  override def receiveCommand: Receive = {
									    case s: String   => persist(MsgSent(s))(updateState)
									    case Confirm(deliveryId) => persist(MsgConfirmed(deliveryId))(updateState)
								  }
								  override def receiveRecover: Receive = {
								    	case evt: Evt => updateState(evt)
								  }
								  def updateState(evt: Evt): Unit = evt match {
									    case MsgSent(s) =>
									      deliver(destination)(deliveryId => Msg(deliveryId, s))
									    case MsgConfirmed(deliveryId) => confirmDelivery(deliveryId)
								  }
					}
					class MyDestination extends Actor {
							  def receive = {
							    case Msg(deliveryId, s) =>
							      sender() ! Confirm(deliveryId)
							  }
					}
'------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
EventAdaptor=>separate journal event schema from domain event
FSM Actors => domain events change state of actor
Storage Plugins => community plugins available for Cassandra, Kafka, MySQL
A journal plugin extends AsyncWriteJournal, AsyncRecovery
'------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
SCHEMA EVOLUTION
Default is java serialization, dont use it for Prod.
pluggable schema mechanisms -> Avro, Thrift, ProtocolBuf..
serialization bindings can be at each message type level.
'------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
