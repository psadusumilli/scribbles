Swap Space:
************
Swap space in Linux is used when the amount of physical memory (RAM) is full. 
If the system needs more memory resources and the RAM is full, inactive pages in memory are moved to the swap space. 
While swap space can help machines with a small amount of RAM, it should not be considered a replacement for more RAM. 
Swap space is located on hard drives, which have a slower access time than physical memory.

Swap space can be a dedicated swap partition (recommended), a swap file, or a combination of swap partitions and swap files.
Swap should equal 2x physical RAM for up to 2 GB of physical RAM, and then an additional 1x physical RAM for any amount above 2 GB, but never less than 32 MB.
So, if:

M = Amount of RAM in GB, and S = Amount of swap in GB, then
If M < 2
	S = M *2
Else
	S = M + 2

Using this formula, a system with 2 GB of physical RAM would have 4 GB of swap, while one with 3 GB of physical RAM would have 5 GB of swap. 
Creating a large swap space partition can be especially helpful if you plan to upgrade your RAM at a later time.
For systems with really large amounts of RAM (more than 32 GB) you can likely get away with a smaller swap partition (around 1x, or less, of physical RAM).

'Paging' is when individual memory segments, or pages, are moved to or from the swap area. 
When memory is low portions of a process (data areas, but not instructions which are available from local or remote file systems) are moved to free up memory space. 
Segments are chosen to be moved if they havent been referenced recently. When the process next tries to reference this segment a page fault occurs and the process is suspended until the segment is returned to memory. A page fault is normally returned the first time a program is started, as it won't be in memory. It's then paged from the local or remote file system.

'Swapping' happens under a heavier work load. With swapping the kernel moves all segments belonging to a process to the swap area. 
The process is chosen if its not expected to be run for a while. Before the process can run again it must be copied back into physical memory.


Swapping:     
*********  
Whole process is moved from the swap device to the main memory for execution. Process size must be less than or equal to the available main memory. 
It is easier to implementation and overhead to the system. Swapping systems does not handle the memory more flexibly as compared to the paging systems.
Lets say you start ten heavyweight processes (for example, five xterms, a couple netscapes, a sendmail, and a couple pines) on an old 486 box running Linux with 16MB of RAM. 
Basically, you *do not have* enough physical RAM to accommodate the text, data, and stack segments of all these processes at once. 
Since the kernel cannot find enough RAM to fit things in, it makes use of the available virtual memory by a process known as swapping. 
It selects the least busy process and moves it in its entirety (meaning the program in-RAM text, stack, and data segments) to disk. 
As more RAM becomes available, it swaps the process back in from disk into RAM. 
While this use of the virtual memory system makes it possible for you to continue to use the machine, it comes at a very heavy price. 
Remember, disks are (by the factor of a million) than CPUs and you can feel this disparity rather severely when the machine is swapping. 
Swapping is not considered a normal system activity. It is basically a sign that you need to buy more RAM. 
In Unix SVR4, the process handling swapping is called sched (in other Unix variants, it is sometimes called swapper). 
It always runs as process 0. When the free memory falls so far below minfree thatpageout is not able to recover memory by page stealing, sched invokes the syscall sched(). Syscallswapout is then called to free all the memory pages associated with the process chosen for being swapping out. On a later invocation of sched(), the process may be swapped back in from disk if there is enough memory.

Paging:          
******
Only the required memory pages are moved to main memory from the swap device for execution. 
Process size does not matter. Gives the concept of the virtual memory.
When a process starts in Unix, not all its memory pages are read in from the disk at once. 
Instead, the kernel loads into RAM only a few pages at a time. After the CPU digests these, the next page is requested. 
If it is not found in RAM, a page fault occurs, signaling the kernel to load the next few pages from disk into RAM. 
This is called demand paging and is a perfectly normal system activity in Unix. 
(Just so you know, it is possible for you, as a programmer, to read in entire processes if there is enough memory available to do so.)
The Unix SVR4 daemon which performs the paging out operation is called pageout. 
It is a long running daemon and is created at boot time. The pageout process cannot be killed. 

There are three kernel variables which control the paging operation (Unix SVR4):
	minfree  - 	the absolute minimum of free RAM needed. 
				If free memory falls below this limit, the memory management system does its best to get back above it. 
				It does so by page stealingfrom other, running processes, if practical.

	desfree  - 	the amount of RAM the kernel wants to have free at all times. 
				If free memory is less than desfree, the pageout syscall is called every clock cycle.

	lotsfree - 	the amount of memory necessary before the kernel stops calling pageout. Betweendesfree and lotsfree, pageout is called 4 times a second.