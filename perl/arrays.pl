#!/bin/perl
use warnings;
use strict;
use Data::Dumper;

#arrays -------------------------------------------------------------------------------
my @arr = qw("abc", 1, 3.14);
print "array=$arr[0] $arr[-1]\n";

#push/pop from end of array-------------------------------
push(@arr,qw(4, 5));
foreach my $i (@arr){
   print "$i,";
}
my $popped = pop(@arr); 
print "\npopped=$popped\n";

#shift/unshift from end of array--------------------------
unshift(@arr,qw(11,12)); 
my $shifted = shift(@arr); 
print "shifted=$shifted";

my @sorted = sort(@arr);
print "\nsorted=";
foreach my $j (@sorted){
   print "$j,";
}

#sort-----------------------------------------------------
my $x1 = 0;
my $x2 = 0;
my @scores = ( 1000, 13, 27, 200, 76, 150 );
my @sorted_array = sort {$x1<=>$x2} (@scores);
print "\n".Dumper \@sorted_array ;

my @greets = qw ( hello there );
print "\n".Dumper \@greets;
splice(@greets, 1, 0, 'out');
print join(" ", @greets );

@greets = (); #emptied 
print "\n".Dumper \@greets;

#2d array-------------------------------------------------
my @matrix;
$matrix[0][0] = 'zero-zero';
$matrix[1][1] = 'one-one';
$matrix[1][2] = 'one-two';
print "\n".Dumper \@matrix;











