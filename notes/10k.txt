http://berb.github.io/diploma-thesis/original/042_serverarch.html

1) Multi-Process
*****************
1 process/request used in older unix servers like 'CERN httpd'
Processes are pre-forked and waiting for connections to serve while sharing a threadsafe socket descriptor.
Does not scale owing to the heavy weight structure of process.

2) Multi-Thread 
***************
Threads lighter than process as they share the same address space and hence share global variables and state.
Easier to spawn and stack size is smaller.
It is a common architecture to place a single dispatcher thread (acceptor thread) in front of a pool of threads for connection handling
Whenever a thread is stuck in IO (like SQL query wait), another thread takes over in CPU resulting in context switch.
On huge loads, large stack frames and frequent context switches lead to poor performance

3) Event-Based
****************
One thread runs a event loop working on a event queue.
This queue contains all events like incoming connections, IO response from a previous IO request, etc.
Each event is linked with its callback to execute
No context-switching it is a single thread serving connections. But dedicated OS kernel threads do IO and put a response event back?

 3.1) Reactor pattern:
 *********************
 synchronous, non-blocking I/O handling and relies on an event notification interface	
 On startup, application registers a set or resources (socket) and events (incoming connections)
 Core component is a synchronous event demultiplexer waiting on events
 On receipt of an event, it forwards to a dispatcher and waits for the next event.
 Only the dispatcher processes the event with the registered callback
 Therefore the event multiplexer still maintains the purity of a single thread.
 The event handlers must also be non-blocking, one blocking operation can stop the entire application.
 Can use a thread pool
 TODO: Must read more

 3.2) Proactor pattern:
 **********************
 Non-synchronous, non-blocking I/O operations 
 Support for completion events instead of blocking event notification interfaces.
 Core component is a Proactive initiator, a single application thread that initiates asynch IO calls.
 IO handling is done by OS
 Can use a thread pool for event handling



