Oracle has two major Java Virtual Machine (JVM) implementations, the Java HotSpot VM and the Oracle JRockit JVM. 
In the short-to-medium term, both will continue as strategic JVMs with active investment. 
Over time, Oracle plans to converge the functionality and code bases of these JVMs.
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Sun/Oracle HotSpot
-------------------*
HVM-> HotSpot VM
CVM-> Classic VM before HVM

-1|	Client HVM, tuned for best performance when running applications in a client environment by reducing application start-up time and memory footprint.
	Server HVM, which is designed for maximum program execution speed for applications running in a server environment.

0|	Heap has 'eden, from-survivor, target-survivor, tenured'

1| 	Unlike CVM, HotSpot VM does not use indirect handles for obj references (they were originally helpful for GC compaction)
	This avoids indirection by using direct pointers
2|	Uses 2 word obj headers unlike 3-words for CVM, saves space.
3| 	Classes, methods, and other internal reflective data are represented directly as objects on the heap, simplifies internal VM object model.
4|  Uses native OS threads and scheduling

5| 	GC:
	'Young generation'=>'Parallel Young' is the default
	'Old generation' => can be configured to use 'serial','concurrent-mark-sweep', 'parallel old'.
	'permanent generation' => only collected during fullGC

	'G1' is the newest algo from java7, breaks heap into small regions, regions with most garbage collected first, runs concurrently

6| 	Ultrafast synchronisation:	
	A 'contended lock' has at least one thread waiting for it; it may have many more. 
	Note that a contended lock becomes 'uncontended lock'when threads are no longer waiting to acquire it.
	'Uncontended synch'=> which dynamically comprise the majority of synchronizations, are implemented with ultra-fast, constant-time techniques.	
	'Contended synch'=>Uses advanced adaptive spinning techniques to improve throughput even for applications with significant amounts of lock contention 
7| 	64bit architecture:
	Users can select either 32-bit or 64-bit operation by using commandline flags -d32 or -d64 respectively
	those that store very large data sets in memory can use 64bit addressing.
	Object packing => pack type-related fields together (int, long, object) to save space.

8| 	Adaptive Optimisation:
	'Full Optimisation' 
	is not effective because
		dynamic loading/unloading of classes is prevalent during runtime
		casting, polymorphic method calls are also present during runtime
	'HotSpot Identification' 
	JIT used to compile every method to machine code on its first execution
	Instead of compiling all bytecode methods to machine code, HVM uses intepreter first
	Then follows usage patterns, the compiles these potions alone using native compiler.
	'Method Inlining'
	Helps to avoid unnecessary method calls
	Bigger blocks of code available for optimisation.
	'Dynamic deoptimisation' 
	on class unloading/loading, must depotimise and reoptimise.

9| Ergonomics - changing according to physical hardware
				self tuning heap sizes (young/old), no need to explicitly set it up.
10|Fast reflection code
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Oracle JRockit:
---------------*

Either a single continuous heap or generational GC
Broken into 'nursery' and 'tenured'
A separate portion of nursery is marked as 'keep' which is left untouched for a while
New allocations happen in rest of the nursery
Once nursery is full, 'keep' is used, so keep always contains newest objs
All live objs in 'non-keep' areas are ported to 'tenured'
Now the non-keep and keep areas switch roles
nursery GC is stop the world
tenured GC can be parallel, concurrent

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
IBM J9:
-------*

1| 	Has different heap structure (Allocate, Survivor, Tenured).
2| 	No permanent generation so class data is subject to GC.
3| 	No eden space
4| 	Ahead of Time (AOT) Compiler:
		Works to reduce some overhead form JITing code whenever JVM starts. 
		memory maps pre-compiled class file so that they dont need to go JIT compilation when IBM JVM runs.
5| 	sharing classes across jvms 
6| 	supported 'compressedOOPs' before jdk 6 started
	'XX:+UseCompressedOops' => in a 64bit jvm, the memory pointers become 64bit thereby leading to inefficient cpu cache usage.
	this option mitigates the overhead, but limits the max heap size to 32G.

7| 	Allocator:	
	used for allocating large areas of memory
	still small compared to the task of garbage collectors
	'cache allocation'=>first allocation is always from cache (thread local heap), max objsize = 512K
	'heap allocation'=>second allocation if cache overflows, then head to 'heap' lock 
	'large object allocation LOA'=> separate areas for large objects (>64kb), can grow/shrink based on usage, not used in balanced garbage policy mode.

8| 	GC: 
	'Generational collectors', can be only enabled via '-Xgcpolicy:gencon', available from JDK 6
	'Classic Mark and Sweep'(default)
	all application threads are stopped while the garbage is collected.
	Not good for heaps beyond 100M
	Goes through the three phases: 
		8.1|'mark' => 
		trace from root objs in thread stackframes to everything reachable 

		8.2|'sweep' => identify chunks of unreachable objs thereby memory spaces to reclaim

		8.3|'compaction' => if required, defragmentation to close pockets
		



		


 
