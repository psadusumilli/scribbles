package modules;
use strict;
use warnings;
use Data::Dumper;
use lib '/home/vijayrc/projs/VRC5/scribbles/perl/modules'; #or use PERLLIB env variable
use dog;

dog::speak;
dog->speak; #method call

#inheritance--------------------------------------------------------
use sheppard;
sheppard->speak(3);
sheppard->track;
if(dog->can("track")){print "dog can track";}
if(sheppard->can("track")){print "sheppard can track";}

#oop----------------------------------------------------------------
use Animal;
my $tiger = Animal->makeNew("hobbes");
$tiger->speak;

