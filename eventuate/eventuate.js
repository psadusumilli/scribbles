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
      events are shared only when actors share a aggregate id
      "batching" =>  uses batching to optimize read and write throughput.
      "conflict resolution" => can be automated or manual intervention
      Eventuate the ConfirmedDelivery trait.

'Event source views'
     can read replicated events from actors but not produce.
     can persist to a datastore like cassandra for the query layer in CQRS.


'--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'

SAMPLES

'create a event sourced actor that takes in commands, spawns events, prints state if asked for '

import scala.util._
import akka.actor._
import com.rbmhtechnology.eventuate.EventsourcedActor

// Commands
case object Print
case class Append(entry: String)

// Command replies
case class AppendSuccess(entry: String)
case class AppendFailure(cause: Throwable)

// Event
case class Appended(entry: String)

class ExampleActor(override val id: String,
                   override val aggregateId: Option[String],
                   override val eventLog: ActorRef) extends EventsourcedActor {

  private var currentState: Vector[String] = Vector.empty

  override val onCommand: Receive = {
    case Print =>
      println(s"[id = $id, aggregate id = ${aggregateId.getOrElse("<undefined>")}] ${currentState.mkString(",")}")
    case Append(entry) => persist(Appended(entry)) {
      case Success(evt) =>
        onEvent(evt)
        sender() ! AppendSuccess(entry)
      case Failure(err) =>
        sender() ! AppendFailure(err)
    }
  }

  override val onEvent: Receive = {
    case Appended(entry) => currentState = currentState :+ entry
  }
}

'spawn instances of that actor, send commands to it'

val system: ActorSystem = // ...
val eventLog: ActorRef = // ...

//restart
val ea1 = system.actorOf(Props(new ExampleActor("1", Some("a"), eventLog)))
ea1 ! Append("a")
ea1 ! Append("b")
ea1 ! Print //[id = 1, aggregate id = a] a,b
//restart, will replay
ea1 ! Print //[id = 1, aggregate id = a] a,b

//replicate with same aggregate 'd'
val d4 = system.actorOf(Props(new ExampleActor("4", Some("d"), eventLog)))// created at location 1
val d5 = system.actorOf(Props(new ExampleActor("5", Some("d"), eventLog)))// created at location 2
d4 ! Append("a")
d5 ! Append("b")
d4 ! Print //d4[id = 4, aggregate id = d] a,b
d5 ! Print //d5[id = 5, aggregate id = d] a,b

'conflicts tracking - lastVectorTimeStamp'

class ExampleActor(override val id: String,..
    private var currentState: Vector[String] = Vector.empty

    //option1
    private var updateTimestamp: VectorTime = VectorTime()
    //option2 
    private var versionedState: ConcurrentVersions[Vector[String], String] = ConcurrentVersions(Vector.empty, (s, a) => s :+ a)
    //Internally, ConcurrentVersions maintains versions of actor state in a tree structure where each concurrent update creates a new branch. 
    //The shape of the tree is determined solely by the vector timestamps of the corresponding update events.

    override val onEvent: Receive = {
        case Appended(entry) =>

          //option1
          if (updateTimestamp < lastVectorTimestamp) {
            // regular update
            currentState = currentState :+ entry
            updateTimestamp = lastVectorTimestamp
          } else if (updateTimestamp conc lastVectorTimestamp) {
            // concurrent update
            // TODO: track conflicting versions
          }

          //option2
          versionedState = versionedState.update(entry, lastVectorTimestamp)
          if (versionedState.conflict) {
            val conflictingVersions: Seq[Versioned[Vector[String]]] = versionedState.all
            // TODO: resolve conflicting versions
          } else {
            val currentState: Vector[String] = versionedState.all.head.value
            // ...
          }
    }

'event views'

case class Appended(entry: String)
case class Resolved(selectedTimestamp: VectorTime)

case object GetAppendCount
case class GetAppendCountReply(count: Long)

case object GetResolveCount
case class GetResolveCountReply(count: Long)

class ExampleView(override val id: String, override val eventLog: ActorRef) extends EventsourcedView {
  private var appendCount: Long = 0L
  private var resolveCount: Long = 0L

  //this is for querying current counts
  override val onCommand: Receive = {
    case GetAppendCount => sender() ! GetAppendCountReply(appendCount)
    case GetResolveCount => sender() ! GetResolveCountReply(resolveCount)
  }
  //Appended,Resolved events are produced by event actors into event log
  override val onEvent: Receive = {
    case Appended(_) => appendCount += 1L
    case Resolved(_) => resolveCount += 1L
  }
}
'--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
''
CRDT

If state update operations commute, there’s no need to use Eventuate’s ConcurrentVersions utility.
Eventuate currently implements 4 out of 12 operation-based CRDTs specified in the paper. 
These are Counter, MV-Register, LWW-Register and OR-Set. 
CRDT services free applications from dealing with low-level details like event-sourced actors or command messages directly.
'read the paper'

'--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
DIFFERENCE FROM AKKA-PERSISTENCE
0 persistactor/Eventsourcedactor, AtLeastOnceDelivery/ConfirmedDelivery
1 PA must be singletons, EAs can be replicated
2 No causality or global ordering of events of multiple PAs. 

3 CAP: 
  The write availability of an Akka Persistence application is constrained by the write availability of the underlying storage backend. 
  According to the CAP theorem, write availability of a strongly consistent, distributed storage backed is limited. 
  Consequently, the command side of an Akka Persistence application chooses CP from CAP.

  EAs and their underlying event logs remain writeable even during inter-location network partitions. 
  From this perspective, a multi-location Eventuate application chooses 'AP' from CAP. 

4 Query:
  akka-persistence: persistence-view replaced with persistence-query
  storage plugins may provide support for 'aggregating events from multiple PAs' and serve the result as Akka Streams

  Eventuate: the query side can be implemented with EventsourcedViews (EVs). 
  An EV can consume events from 'all EAs that share an event log', even if they are globally distributed. 
  Events are always consumed 'in correct causal order'. 
  An application can either have a single replicated event log or several event logs, organized around topics, for example. 
  Future extensions will allow EVs to consume events from multiple event logs. 
  An Akka Streams API in Eventuate is also planned.

5 storage:
  akka-persistence: 1 index per PA persistenceId
  Aggregating events from several PAs requires either the creation of an additional index in the storage backend or 
  an on-the-fly event stream composition when serving a query

  eventuate: EAs without aggregateId share same event log

6 throughput:
  A consequence of synchronizing internal state with an event log is decreased throughput. 
  Synchronizing internal state has a stronger impact in Akka Persistence because of the details how event batch writes are implemented. 
  In Akka Persistence, events are batched on PA level, but only when using persistAsync. 
  In Eventuate there’s a separate batching layer between EAs and the storage plugin, so that events emitted by different EA instances, even if they sync their internal state with the event log, can be batched for writing.  
