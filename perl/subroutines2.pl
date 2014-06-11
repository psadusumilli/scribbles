#!/bin/perl
use warnings;
use strict;
use Data::Dumper;

sub ping{
  my @args = @_;
  my ($left, $right) = @_;	
  print Dumper \@args;
  print "left=$left|right=$right";		
}

my $a1 = "cartman";
my $a2 = "kenny";
my $a3 = "kyle";

ping($a1,$a2,$a3);
