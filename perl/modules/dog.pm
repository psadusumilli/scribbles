#!/bin/perl

package dog;
use strict;
use warnings;

sub speak {
  my ($invocant, $count) = @_;
  if($invocant && $count) {
    print "invocant = $invocant\n";
    for(1 .. $count) { print "Woof\n";} 
  }else{print "whimper\n";}
}
1; #must return 1

