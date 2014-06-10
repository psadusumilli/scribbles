#!/bin/perl
use warnings;
use strict;
use Data::Dumper;

#hashes -------------------------------------------------------
my %inventory;
$inventory{apples}=42;
$inventory{pears}=17;
$inventory{bananas}=5;
print Dumper \%inventory;

#exists-------------------------------------------------------- 
my %pets = ("cats"=>2,"dogs"=>1,"dino"=>1);
print Dumper \%pets;

unless(exists($pets{"fish"})){ print "no fishes here\n";}
if(exists($pets{"cats"})){  print "cats here\n";}
while(my($pet,$qty)=each(%pets)) { print "pet='$pet', qty='$qty'\n";}

my @pet_keys = keys(%pets);
my @pet_vals = values(%pets);
print Dumper \@pet_keys;
print Dumper \@pet_vals;

delete($pets{"dogs"});
print Dumper \%pets;

#nested maps---------------------------------------------------
#unintentional additional of keys wth null values
my %states;
$states{Florida}->{Abbreviation}='FL';
if(exists($states{Maine})) {
  if(exists($states{Maine}->{StateBird})){ warn "it exists"; }
}
print Dumper \%states;
















