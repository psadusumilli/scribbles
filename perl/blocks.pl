#BEGIN -> execute block as soon as it is compiled, even before compiling anything else.
#The other blocks, including normal code, do not execute until after the entire program has been compiled. 
#When anything other than a BEGIN block is encountered, they are compiled and scheduled for execution, but perl continues compiling the rest of the program.
#The execution order is CHECK, INIT, normal code, and END blocks.


#INIT, normal code, and END blocks.
#CHECK -> Schedule these blocks for execution after all source code has been compiled.
#INIT-> Schedule these blocks for execution after the CHECK blocks have executed 
#normal code -> Schedule normal code to execute after all INIT blocks.
#END -> Schedule for execution after normal code has completed.

#Multiple BEGIN blocks are executed immediately in NORMAL declaration order.
#Multiple CHECK blocks are scheduled to execute in REVERSE declaration order.
#Multiple INIT blocks are scheduled to execute in NORMAL declaration order.
#Multiple END blocks are scheduled to execute in REVERSE declaration order.

#!/bin/perl
use warnings;
use strict;
use Data::Dumper;

END { print "END 1\n"; }
CHECK { print "CHECK 1\n";}
BEGIN { print "BEGIN 1\n";}
INIT { print "INIT 1\n" ;}
print "normal\n";
INIT { print "INIT 2\n"; }
BEGIN { print "BEGIN 2\n";}
CHECK { print "CHECK 2\n";}
END { print "END 2\n" ;}




