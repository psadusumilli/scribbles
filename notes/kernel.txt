$uname -r  -> get kernel version
$rpm -q kernel -> list kernel versions in machine, Fedora keeps 3 versions of kernel by default
$yum remove kernel-3.7.8-202.fc18.x86_64 -> remove a kernel version 
$yum update kernel -> 

$modprobe -> to dynamically load/remove kernel modules
$lsmod ->  list modules loaded currently
$rmmod wl -> remove module 'wl'
#insmod wl -> install module 
