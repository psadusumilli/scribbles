#!/bin/perl
use warnings;
use strict;

print "hello world\n";

my $name = 'sharavan';
my $greet = "hello $name\n";
print $greet;

my ($fname, $lname) = qw( vijay raj);
print "$fname $lname \n";

#string ops
my $concat = "rekha"." srimalu". "\n";
print $concat;

my $multiply = "=" x 30;
print "$multiply: length=".length($multiply). "\n";
print substr("hey you moron",3,4)."\n";

my ($w1, $w2, $w3, $w4) = split(/[0-9]{2}/,"respect12my23authorita34bitch");
print "$w1, $w2, $w3, $w4";
