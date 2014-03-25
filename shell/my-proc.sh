free #show memory usage
top #system perf

ls -l /proc #list numbered dirs (pids) and a bunch of files (cpuinfo meminfo)

#inside each pid dir----------------------------------------------------------------
cd 1232
ls /1232

cat cmdline #command line of the command.
cat environ # environment variables.
cd fd # file descriptors which is linked to the appropriate files.
cat limits # specific limits to the process.
cat mounts # mount related information

cwd #Link to current working directory of the process.
exe #Link to executable of the process.
root #Link to the root directory of the process.

#all the system data------------------------------------------------------------------
/proc/cpuinfo # information about CPU,
/proc/meminfo # information about memory,
/proc/loadvg # load average,
/proc/partitions # partition related information,
/proc/version # linux version
/proc/cmdline # Kernel command line
/proc/cpuinfo # Information about the processors.
/proc/devices # List of device drivers configured into the currently running kernel.
/proc/dma # Shows which DMA channels are being used at the moment.
/proc/fb # Frame Buffer devices.
/proc/filesystems # File systems supported by the kernel.
/proc/interrupts # Number of interrupts per IRQ on architecture.
/proc/iomem # This file shows the current map of the system’s memory for its various devices
/proc/ioports # provides a list of currently registered port regions used for input or output communication with a device
/proc/loadavg # Contains load average of the system
/proc/locks # Displays the files currently locked by the kernel
/proc/meminfo # Current utilization of primary memory on the system
/proc/misc # This file lists miscellaneous drivers registered on the miscellaneous major device, which is number 10
/proc/modules # Displays a list of all modules that have been loaded by the system
/proc/mounts # This file provides a quick list of all mounts in use by the system
/proc/partitions # Very detailed information on the various partitions currently available to the system
/proc/pci # Full listing of every PCI device on your system
/proc/stat # Keeps track of a variety of different statistics about the system since it was last restarted
/proc/swap # Measures swap space and its utilization
/proc/uptime # Contains information about uptime of the system
/proc/version # Version of the Linux kernel, gcc, name of the Linux flavor installed.

