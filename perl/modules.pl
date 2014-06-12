package modules;
use strict;
use warnings;
use Data::Dumper;
use lib '/home/vijayrc/projs/VRC5/scribbles/perl/modules'; #or use PERLLIB env variable
use dog;

dog::speak;
dog->speak; #method call

use sheppard;
sheppard->speak(3);
sheppard->track;
