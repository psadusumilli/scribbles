YARN
*****

YARN (Yet Another Resource Negotiator) is Hadoop’s next-generation cluster scheduler. 
It allows you to allocate a number of containers (processes) in a cluster of machines, and execute arbitrary commands on them.
When an application interacts with YARN, it looks something like this:
	Application: I want to run command X on two machines with 512MB memory.
	YARN: Cool, where’s your code?
	Application: http://path.to.host/jobs/download/my.tgz
	YARN: I’m running your job on node-1.grid and node-2.grid.

Systems uses YARN to manage deployment, fault tolerance, logging, resource isolation, security, and locality. 
A brief overview of YARN is below; see this page from Hortonworks for a much better overview.

YARN Architecture
******************
YARN has three important pieces: a 'ResourceManager, a NodeManager, and an ApplicationMaster'. 
In a YARN grid, every machine runs a NodeManager, which is responsible for launching processes on that machine. 
A ResourceManager talks to all of the NodeManagers to tell them what to run. 
Applications, in turn, talk to the ResourceManager when they wish to run something on the cluster. 
The third piece, the ApplicationMaster, is actually application-specific code that runs in the YARN cluster. 
It’s responsible for managing the application’s workload, asking for containers (usually UNIX processes), and handling notifications when one of its containers fails.

