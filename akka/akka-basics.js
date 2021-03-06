AKKA
******
Chapter1:
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
An actor is a container for State, Behavior, a Mailbox, Children and a Supervisor Strategy.
All of this is encapsulated behind an Actor Reference.

#1 Supervision:
****************
#1.1
 An actor system will during its creation start at least three actors
	/user: - parent of all user created actors. Can be configured via SystemConfigurator.
	/system:- controls logging during the orderly shutdown of /user: actors, though logging itself is implemented by /user: actors
	/root: - no parent, bubble-walker, SupervisorStrategy.stoppingStrategy to recursively stop all children.

#1.2
Supervisor has following options on event of a failure:
	•  Resume the subordinate, keeping its accumulated internal state
	•  Restart the subordinate, clearing out its accumulated internal state
	•  Terminate the subordinate permanently
	•  Escalate the failure, thereby failing itself
#1.3
Actor restart happens for failures like:
	• Systematic (i.e. programming) error for the specific message received
	• (Transient) failure of some external resource used during processing the message
	• Corrupt internal state of the actor
New instance of the underlying Actor class and replacing the failed instance with the fresh one inside the child’s ActorRef; the ability to do this is one of the reasons for
encapsulating actors within special references. The new actor then resumes processing its mailbox, meaning that the restart is not visible outside of the actor itself with the notable exception that the message during which the failure occurred is not re-processed.
The precise sequence of events during a restart is the following:
	1. suspend the actor (which means that it will not process normal messages until resumed), and recursively suspend all children
	2. call the old instance’s preRestart hook (defaults to sending termination requests to all children and calling postStop)
	3. wait for all children which were requested to terminate (using context.stop()) during preRestart to actually terminate; this—
	    like all actor operations—is non-blocking, the termination notice from the last killed child will effect the progression to the next step
	4. create new actor instance by invoking the originally provided factory again
	5. invoke postRestart on the new instance (which by default also calls preStart)
	6. send restart request to all children which were not killed in step 3; restarted children will follow the same process recursively, from step 2
	7.resume the actor
#1.4
Lifecycle Monitoring:
	Any actor can monitor the other for this deathWatch using ActorContext.watch(targetActorRef), ActorContext.unwatch(targetActorRef).
	message will be delivered irrespective of the order in which the monitoring request and target’s termination occur.
#1.5
Supervision strategies:
	OneForOneStrategy => only to the failed child
	AllForOneStrategy => only to the failed child plus siblings

#2 Actor References:
***********************
An actor reference is a subtype of ActorRef, whose foremost purpose is to support sending messages to the actor it represents.
Each actor has access to its canonical (local) reference through the 'self' field; this reference is also included as 'sender' reference by default for all messages sent to other actor.
#2.1
Types of actors:
	Purely local actor references without remoting, used for talking in the same jvm
	Local actor references with remoting, used as gateways into their jvm, local mirror to remote on the opposite side
	Remote actor references point to actors in other JVM with message serialisation
	Router references, will just send the message to their children without any processing from their side.
	Local Special:
		PromiseActorRef is the special representation of a Promise for the purpose of being completed by the response from an actor. akka.pattern.ask creates this actor reference.
		DeadLetterActorRef is the default implementation of the dead letters service to which Akka routes all messages whose destinations are shut down or non-existent.
		EmptyLocalActorRef is what Akka returns when looking up a non-existent local actor path:
#2.2
Actor Path:
consists of an anchor, which identifies the actor system, followed by the concatenation of the path elements, from root guardian to the designated actor; the path elements are the names of the traversed actors and are separated by slashes.
	"akka://my-sys/user/service-a/worker1" // purely local
	"akka.tcp://my-sys@host.example.com:5678/user/service-b" // remote
	"cluster://my-cluster/service-c" // clustered (Future Extension)
akka.tcp is the default remote transport for the 2.2 release; other transports are pluggable
types:
	"Logical path": From each parent to child, a complete ancestry path
	"Physical path": Parent and child can exist in different jvms, so the path is actually wiring up network jumping info.
				  However, a child can be directly accessed instead of going via parent to avoid needless routing
	"Virtual path": WIP, moving clusters as a whole from system to another since everything is virtual.

top level paths:
	• "/user" is the guardian actor for all user-created top-level actors;  actors created using ActorSystem.actorOf are found below this one.
	• "/system" is the guardian actor for all system-created top-level actors, e.g. logging listeners or actors automatically deployed by configuration at the start of the actor system.
	• "/deadLetters" is the dead letter actor, which is where all messages sent to stopped or non-existing actors are re-routed
	• "/temp" is the guardian for all short-lived system-created actors, e.g. those which are used in the implementation of ActorRef.ask.
	• "/remote" is an artificial path below which all actors reside whose supervisors are remote actor references

#2.3
Actor creation:
	1 "ActorSystem.actorOf" =>  actorOf only ever creates a new actor, and it creates it as a direct child of the actor context on which this method is invoked.
	2 "ActorContext.actorOf" => get existing actor and with that actor context, access its children, parent.
	3 "ActorSystem.actorSelection" =>  only ever looks up existing actors with path startinng from root, when messages are delivered.Does not create/verify
	4 "ActorContext.actorSelection" => similar to 3, but path starts from current actor instead of root.
								For eg:
								context.actorSelection("../brother") ! msg => send to sibling
								context.actorSelection("../*") ! msg => send to all children including himself
	5 "actorFor" => (deprecated in favor of actorSelection) only ever looks up an existing actor, i.e. does not create one.
#2.4
Actor equality:
	Two actor references are compared equal when they have the same path and point to the same actor incarnation.
	Restart of an actor caused by a failure still means that it is the same actor, while terminated actor is not.

#3 Location Transparency:
******************************
Distributed by default: design from keeping everything remote, then move to local by optimising. The reverse way is bad.
Its asynchronous messaging with serialised messages over the wire, including closures.
scaling up with routers and uses parallel cores

#4 Akka and Java Memory Model
***********************************
#4.1 Actors & JMM:
To prevent visibility and reordering problems on actors, Akka guarantees the following two "happens before" rules:
	• The "actor send rule": the send of the message to an actor happens before the receive of that message by the same actor.
	• The "actor subsequent processing rule": processing of one message happens before processing of the next message by the same actor.
	   So actor state need not be declared volatile.
#4.2 Futures & JMM:
	The completion of a Future “happens before” the invocation of any callbacks registered to it are executed.
#4.3 STM & JMM:
	• The "transactional reference rule": a successful write during commit, on an transactional reference, happens before every subsequent read of the same transactional reference.
	all writes are buffered until a commit happens, so a dirty read is possible.

#5 Message Delivery
***********************
• "at-most-once" delivery means that for each message handed to the mechanism, that message is delivered zero or one times
	messages may be lost.
	good performance and cheap
• "at-least-once"
	delivery means that for each message handed to the mechanism potentially multiple attempts are made at delivering it, such that at least one succeeds;
 	messages may be duplicated but not lost.
• "exactly-once" delivery means that for each message handed to the mechanism exactly one delivery is made to the recipient;
	the message can neither be lost nor duplicated.
	very expensive,state to be kept at the receiving end in order to filter out duplicate deliveries.

#5.1 Message ordering:
Actor 'A1' sends messages 'M1, M2, M3' to 'A2'
Actor 'A3' sends messages 'M4, M5, M6' to 'A2'
This means that:
	1. If M1 is delivered it must be delivered before M2 and M3
	2. If M2 is delivered it must be delivered before M3
	3. If M4 is delivered it must be delivered before M5 and M6
	4. If M5 is delivered it must be delivered before M6
	5. A2 can see messages from A1 interleaved with messages from A3
	6. Since there is no guaranteed delivery, any of the messages may be dropped, i.e. not arrive at A2.

#5.2 Failure(system) messages and Normal messages have different mailboxes
	Child actor C sends message M to its parent P
	Child actor fails with failure F
	Parent actor P might receive the two events either in order M, F or F, M

#5.3 Local (within single JVM) messaging
	Local sends can fail in Akka-specific ways:
	• if the mailbox does not accept the message (e.g. full BoundedMailbox)
	• if the receiving actor fails while processing the message or is already terminated.
	  The sender of a message does not get feedback if there was an exception while processing, that notification goes to the supervisor instead.

#5.4 Dead Letters
	Messages which cannot be delivered will land here, helpful for debugging.
	Actor subscribed to 'akka.actor.DeadLetter' event will receive dead messages
	Not probagated over network so one dead letter collector per jvm.
	"akka.dispatch.Terminate" and "akka.dispatch.Terminated" are harmless residual messages flying around when a complex actor system is shutdown

#6 Configuration
*******************
Things that can be configured
	• log level and logger backend
	• enable remoting
	• message serializers
	• definition of routers
	• tuning of dispatchers
All configuration for Akka is held within instances of ActorSystem
The default is to parse all "application.conf, application.json and application.properties" in root classpath and merge with library "reference.conf"
The philosophy is that code never contains default values, but instead relies upon their presence in the "reference.conf" supplied with the library in question.
If you are writing an Akka application, keep you configuration in "application.conf" at the root of the classpath.
If you are writing an Akka-based library, keep its configuration in "reference.conf" at the root of the JAR file.

#6.1 good practice: have a file "dev.conf" which imports "application.conf" with defaults and overrides selectively.
include "application"
	akka {
		loglevel = "DEBUG"
	}
...}
#6.2
"ConfigFactory.load()" is the API to load via code.
If using API you can replace "application.conf" by defining "-Dconfig.resource=whatever, -Dconfig.file=whatever, -Dconfig.url=whatever"
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAPTER2: Actors
*******************
Props is a configuration class to specify options for the creation of actors
Using Companion objects is good practice.
ActorSystem is a heavy object: create only one per application
Check out examples in scribbles/akka

If the current actor behavior does not match a received message, unhandled is called,
which by default publishes an 'akka.actor.UnhandledMessage(message, sender, recipient)' on the actor system’s event stream
(set configuration item 'akka.actor.debug.unhandled' to on to have them converted into actual Debug messages).

An 'ActorRef' always represents an incarnation (path and UID) not just a given path.
Therefore if an actor is stopped and a new one with the same name is created an ActorRef of the old incarnation will not point to the new one. But restarts on failure, point to the same old one.
'ActorSelection' on the other hand points to the path. Send an 'Identity' to the 'ActorSelection', it will reply back with an 'ActorIdentity'

Actors may be 'restarted' in case an exception is thrown while processing a message. Involves hooks 'preRestart,preStart,actorOf,postRestart'
In restarts, the mailbox is intact, so processing of remaining messages starts except for that poison message.

'postStop' is called after a actor is stopped with dequeuing of mailbox being stopped. All further messages will be redirected to 'deadLetters' of System.

#2 Message Sending:
	• '!' means 'fire-and-forget', e.g. send a message asynchronously and return immediately. Also known as 'tell'.
	• '?' sends a message asynchronously and returns a 'Future' representing a possible reply. Also known as 'ask'.

#2.1 Ask
The ask operation involves creating an internal actor for handling this reply, which needs to have a timeout after which it is destroyed in order not to leak resources.
