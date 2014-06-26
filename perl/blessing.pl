package blessing;
use strict;
use warnings;

my @arr=(1,2,3); #referent
my $r_arr = \@arr; #reference to referent
bless($r_arr, "Counter");
my $str = ref($r_arr); 
warn "str is '$str'"; #bless makes ref return Counter

