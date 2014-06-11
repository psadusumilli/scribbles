#!/bin/perl
use warnings;
use strict;

#using $cnt declared outside functions is closure
#use local() to temporarily stash a package variable
{
	my $cnt=0;
	sub inc{$cnt++; print "inc:cnt=$cnt'\n";}
	sub dec{$cnt--; print "dec:cnt=$cnt'\n";}
}
inc;
inc;
inc;
dec;
dec;
inc;