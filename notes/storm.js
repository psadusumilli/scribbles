Storm:
------
Chapter1: Terms
******************
1) Topology made of Bolts and Spouts
2) Stream is a bunch of tuples
3) Spouts are data feeders, can be reliable or not, can emit 0-* streams. uses SpoutOutputCollector and OutputFieldsDeclarer
4) Bolts are data manipulators, can emit 0-* streams, uses OutputCollector and OutputFieldsDeclarer
5) InputDeclarer got after setBolt/setSpout decides stream grouping(shuffle, field, direct, all, global
6) Each spout or bolt executes as many tasks across the cluster. 
   Each task corresponds to one thread of execution, and stream groupings define how to send tuples from one set of tasks to another set of tasks

7) Topologies execute across one or more worker processes. 
   Each worker process is a physical JVM and executes a subset of all the tasks for the topology. 
   For eg, if the combined parallelism of the topology is 300 and 50 workers are allocated, then each worker will execute 6 tasks (as threads within the worker). 
   Storm tries to spread the tasks evenly across all the workers

Chapter2: Configuration
************************

1) Nimbus is the main work coordinator in the cluster
2) Supervisors are agents which control bolts/spouts and talk with Nimbus.
3) Every Configuration has a default value in 'defaults.yaml' which can be overriden by 'storm.yaml' in Nimbus and Supervisors.
4) The preference order for configuration values is 
  < defaults.yaml 
  < storm.yaml 
  < topology specific Configuration (Config Api)
  < internal component specific configuration (getComponentConfiguration() in bolt/spout)
  < external component specific configuration.(setBolt/setSpout returns addConfiguration())

Chapter3: Parallelism
***********************
1) A machine in a cluster can run 1-* worker process for 1-* topologies.
2) A worker process can run many 1-* executors backed the workers' child threads.
3) A executor can run 1-* tasks of the same component(bolt/spout).
4) Cluster -> Machines -> Worker Process -> Executors (thread pools) -> tasks (bolt/spout running instance)
5) Parallelism hint given in setBolt/setSpout is the initial number of executors.
6) How to set number of workers? 
    TOPOLOGY_WORKERS (config files)
    Config.setNumberOfWorkers()
7) How to set number of executors?
    setBolt/setSpout
8) rebalance executors in a running topology
   $ storm rebalance mytopology -n 5 -e blue-spout=3 -e yellow-bolt=10
   or use storm ui

