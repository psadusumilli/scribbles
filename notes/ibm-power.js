'IBM Power 780 server':
-------------------*
one big black box like a fridge
support aix, linux, IBM i
less floorspace, less power
PowerVM virtualization
upto 128 POWER 7+ processors 

'POWER 7+ processor':
-------------------*
L3 cache is 2.5 times previous POWER 7 processors
Imbedded accelerators for memory compression and AIX file encryption now offload processors from these tasks and significantly improve performance
Can run at a higher clock cycles, but needs more power.
Intelligent Threads:
	which enables workload optimization by dynamically switching between threading modes. 
	Each application can be run in the most suitable threading mode; 
		either single thread per core, simultaneous multithread (SMT) with two threads per core, or SMT with four threads per core.

'POWER VM':
----------*
provided with IBM Power Systems firmware, which includes 'logical partitioning (LPAR)' technologies. 
allows any individual LPAR to access the maximum amount of memory and CPU cores that are available on the server.
managed through 'hardware management console (HMC)' or through 'IBM Systems Director software' with the VMControl virtualization management plug-in.
includes 'Micro-Partitioning® and Virtual I/O Server (VIOS) capabilities', which are designed to allow businesses to increase system resource utilization
'Multiple Shared Processor Pools', which allows for automatic non-disruptive balancing of processing power between partitions assigned to the shared pools.
'Shared Dedicated Capacity', which helps optimize use of processor cycles.
'Live Partition Mobility' allows a partition to be relocated from one server to another with virtually no impact to the applications running inside the partition.
'Active Memory Sharing' is an advanced memory virtualization technol-ogy that intelligently flows memory from one partition to another.
	IBM i, AIX, and Linux partitions can share a pool of memory and have PowerVM automatically allocate the memory based on the workload demands of each partition.
'Dynamic Platform Optimizer'=> monitors processor and memory affinity and adjusts workload placement to optimize performance in a virtualized consolidated environment.	
'Active Memory Expansion'=> allows the effective memory capacity of the system to be much larger than the true physical memory. 
	Innovative compression/decompression of memory content can allow memory expansion up to 100 percent.
'Active Memory Mirroring' for Hypervisor, which is designed to prevent a system outage in the event of an uncor-rectable error in memory being used by the system hypervisor

'OS':
------*
Supports Linux, AIX but 'IBM i' is the integrated OS for power systems
IBM i offers a virus-resistant architecture with a proven reputation for excep-tional business resilience

