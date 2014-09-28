      __  __        __   __     __  
      |  | |__  |__) |    /  \ / _` | /  ` 
      |/\| |___ |__) |___ \__/ \__> | \__, 
                                           
features like j2ee support, clustering, coherence caching, special spring suppport, network channels, work managers, domains,diagnostics and security
chap1-install:
1) download the right install jar 
2) gui mode sucks in mac so run : http://oracle-webcenter.blogspot.com/2012/06/installing-weblogic-1036-in-command.html
   java -d64 i-Dspace.detection=false -jar wls1036_generic.jar -mode=console
3) I installed in $HOME/Tools/oracle, after installation got weblogic 10.3 and coherence 3.7 in that folder.
4) set MW_HOME to $HOME/Tools/oracle
5) bash functions to create/start domains
  	function wl-start-domain(){
 		cd $HOME/Code/c1/wlsdomain/$1;
 		. $MW_HOME/wlserver_10.3/server/bin/setWLSEnv.sh ;
 		# . $MW_HOME/wlserver/server/bin/setWLSEnv.sh ;
 		./startWebLogic.sh;
	 }
 	function wl-make-domain(){
  		cd $HOME/Code/c1/wlsdomain/;
  		mkdir $1;
  		cd $1;
 		. $MW_HOME/wlserver_10.3/server/bin/setWLSEnv.sh ;
 		java -Dweblogic.management.allowPasswordEcho=true -Xmx1024m -XX:MaxPermSize=256m weblogic.Server;
 	}
 
6) From console http://localhost:7001/console, Environment> Coherence Server > Setup new
   localhost|8888|coherence1
   Enter the following params for server start:
	6.1) Java Home - Specify the Java home directory (path on the machine running Node Manager) to use when starting this server. Specify the parent directory of the JDK's bin directory. For example, c:\Oracle\Middleware\jdk160_20.
	6.2) Java Vendor - Specify the Java vendor value to use when starting this server. For example, BEA, Sun, and such.
	6.3) BEA Home - Specify the directory on the Node Manager machine under which all of Oracle's WebLogic products were installed. For example, c:\Oracle\Middleware\.
	6.4) Root Directory - Specify the directory that this server uses as its root directory. This directory must be on the computer that hosts the Node Manager. If you do not specify a Root Directory value, the domain directory is used by default.
	6.5) Class Path - Specify the classpath (path on the machine running Node Manager) to use when starting this server. If you need to add user classes to the classpath, in addition you will need to add the following: FEATURES_HOME/weblogic.server.modules.coherence.server_10.3.4.0.jar:COHERENCE_HOME/lib/coherence.jar, where FEATURES_HOME is the features directory, typically MW_HOME\modules\features, and COHERENCE_HOME is the Coherence directory, typically MW_HOME\coherence_3.6, on the Node Manager machine. If you do not specify a classpath, the preceding classpath will be used by default.
The operating system determines which character you use to separate path elements. On Windows use a semicolon (;). On UNIX use a colon (:).
	6.6) Arguments - The arguments to use when starting this server. These are the first arguments appended immediately after 

7) Setup a machine; Environment > Machines ; localhost|5556|machine1





 


chap2- administration:
***********************
jython, web console, weblogic scripting tool (WLST), SNMP, command line util and config wizard.
1)  create domains
    a: manually => mkdir <some-path>/domain1; . $MW_HOME/wlserver/server/bin/setWLSEnv.sh  ; java weblogic.Server;
    this copies files from $MW_HOME to domain1, startWeblogic.sh for starting server
    b: using config wizard => $MW_HOME/wlserver/common/bin/config.sh
2)  migrate domains:
    a:s using pack/unpack commands
3)  your app config files stored in domains are archived and audited. Must be read-only in prod for sanity.
4)  recource adaptors exist for jdbc/jms etc. can be configured via admin console or scripts.
5)  The Node Manager is a utility for remote control of Administration Servers and Managed Servers. 
    It runs separately from WebLogic Server and lets you start up and shut down servers.
6)  deploy using console, scripting or deployment apis.
7)  Work Managers configure how your application prioritizes the execution of its work. 
    Based on rules you define and by monitoring actual run-time performance.
8)  Ant tasks are available. Agents are available to talk in SNMP protocol
9)  JMX, JEE Management APIs JSR77,Deployment APIs JSR88,diagnostics APIs are available.
10) Changes made to the console are staged and activate only by the Change Center. 
    Lock must be obtained and released to distribute your changes across servers.

chap3-domains
**************
1)  a domain is a logical collection of WebLogic Server services designed for a specific purpose 
2)  it can span across 1-* servers, 1-* clusters, locations, can be bound to a functional unit.
3)  one administration server/domain to control the managed servers. A bunch of managed servers with loadbalancing is called a cluster.
4)  a developer domain is a simple domain which packs the admin server and also hosts the apps.
5)  coherence clusters also follow the same domain concept.
6)  node manager concepts similar to websphere
7)  machine definition is a file used to link hardware with the server. used by the node manager.
8)  network channels define ports and protocols, used to link clients with servers. can be applied across managed servers.

chap4-clustering:
*****************
1)  a cluster cannot span across domains. a domain can contains multiple clusters, all managed by the single admin server.
2)  can be dynamic, where members can be added runtime, but must share the same server template.

chap5-deployment:
******************
1) for developer setup, copy archive (ear/war/jar) or exploded archive into the domain1/autodeploy; start server;
2) use admin console

chap6-data sources:
*******************
Generic Data Sources—Generic data sources and their connection pools provide connection management processes that help keep your system runsning efficiently.
Link Data Sources—An event-based data source that adaptively responds to state changes in an Oracle RAC instance.
Multi data sources—An abstraction around a group of generic data sources that provides load balancing or failover processing.

chap7-jms
**********






