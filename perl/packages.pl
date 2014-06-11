use warnings;
use strict;
use Data::Dumper;

#our-lexical scoping ----------------------------------------
#variable declared with 'our' belongs to the package namespace, can be used outside with proper package prefix
package Hogs;
our $speak = 'oink';
{ 
	package Heifers;
	our $speak = 'moo';
	warn "speak is '$speak'"; 
} 
warn "speak is '$speak'";

#my-lexical scoping------------------------------------------
#not tied to package namespace, tied to code blocks 
my $name="vrc1";
{
	my $name = "vrc2";
	warn "name=$name";
}
warn "name=$name";