#!/bin/perl
use warnings;
use strict;
use Data::Dumper;

print "hello world\n";

my $name = 'sharavan';
my $greet = "hello $name\n";
print $greet;

my ($fname, $lname) = qw( vijay raj);
print "$fname $lname \n";

#string ops ---------------------------------------------------------------------------
my $concat = "rekha"." srimalu". "\n";
print $concat;

my $multiply = "=" x 30;
print "$multiply: length=".length($multiply). "\n";
print substr("hey you moron",3,4)."\n";

my ($w1, $w2, $w3, $w4) = split(/[0-9]{2}/,"respect12my23authorita34bitch");
print "$w1, $w2, $w3, $w4"."\n";
print join("-",$w1, $w2, $w3, $w4)."\n";

#numbers -------------------------------------------------------------------------------
my $z1 = 2;
my $z2 = $z1*3;
print "$z2\n";

my $str_z1 = $z1.'';
print $str_z1."\n";

#operator------------------------------------------------------------------------------
my $a = 0 || 1;
my $b = 0 or 1;
print "$a $b\n"; # a=1, since || has higher precedence than =

#ternary operator
my $c = $a==0? "hey": $b==0? "dude":"bum";
print $c."\n"; 
my $d = \$c;
print "reference of c=$d\n";

#file----------------------------------------------------------------------------------
open(my $myfile,'>file.txt') or print "cannot open file";
print $myfile "respect ma authorita\n";
close($myfile);

#arrays -------------------------------------------------------------------------------
my @arr = qw("abc", 1, 3.14);
print "array=$arr[0] $arr[-1]\n";

push(@arr,qw(4, 5));#add to end of array
foreach my $i (@arr){
   print "$i,";
}
my $popped = pop(@arr); #pop from end of array
print "\npopped=$popped\n";

unshift(@arr,qw(11,12)); #add to start of array
my $shifted = shift(@arr); #take from start of array
print "shifted=$shifted";

my @sorted = sort(@arr);
print "\nsorted=";
foreach my $j (@sorted){
   print "$j,";
}

my $x1 = 0;
my $x2 = 0;
my @scores = ( 1000, 13, 27, 200, 76, 150 );
my @sorted_array = sort {$x1<=>$x2} (@scores);
print "\n".Dumper \@sorted_array ;











