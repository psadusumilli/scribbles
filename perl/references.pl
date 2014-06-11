#!/bin/perl
package references;

use warnings;
use strict;
use Data::Dumper;
use Storable qw(nstore dclone retrieve);

#references -------------------------------------------------------
my $age = 42;
my @colors = qw( red green blue );
my %pets=(fish=>3,cats=>2,dogs=>1);
my @refrigerator=qw(milk bread eggs);

my $r_age = \$age;
my $r_colors = \@colors;
my $r_pets = \%pets;

#dereferencing - access referenced scalar using {}-----------------
${$r_age}++; 
print "\nage is '$age";

pop(@{$r_colors});
print Dumper $r_colors;

${$r_pets}{fish} = 4;
print Dumper $r_pets;
print "type of reference=".ref($r_pets)."\n";

#anonymous referents -  no underlying scalar- different brackets [] {} from ()
my $r_colors_2 = [ 'red', 'green', 'blue' ];
print Dumper $r_colors_2;
my $r_pets_2 = { fish=>3,cats=>2,dogs=>1 };
print Dumper $r_pets_2;


#complex struct-----------------------------------------------------
my $house={ #note - flower brackets
	pets=>\%pets,
	refrigerator=>\@refrigerator
};
# Alice added more canines
$house->{pets}->{dogs}+=5;
# Bob drank all the milk
shift(@{$house->{refrigerator}});
print Dumper $house;

#multi-dimensional array--------------------------------------------
my $mda;
for(my $i=0;$i<2;$i++){
	for(my $j=0;$j<2;$j++) {
		for(my $k=0;$k<2;$k++){
			$mda->[$i]->[$j]->[$k] ="row=$i, col=$j, depth=$k";
		}
	}
}
print Dumper $mda;

#cloning--------------------------------------------------------------
my $twin_house = dclone $house;
print Dumper $twin_house;

nstore ($house, 'house.txt');
my $new_house = retrieve('house.txt');
print Dumper $new_house;














