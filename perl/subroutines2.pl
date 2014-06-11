#!/bin/perl
use warnings;
use strict;
use Data::Dumper;

#accessing arguments-------------------------------------------------------------
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

#args passed by COPY, so changes are NOT reflected -BOOK NOT RIGHT--------------
sub swap { 
  (@_) = reverse(@_); 
  print Dumper \@_;	  	
}
my $one = "I am one";
my $two = "I am two";
swap($one,$two);
warn "one is '$one'";
warn "two is '$two'";

#function pointers or block executions------------------------------------------
my $temp = sub {print "Hello\n";};
&{$temp};
&$temp;
$temp->(); # preferred

#implied arguments--------------------------------------------------------------
sub m2 {
  print Dumper \@_;
}
sub m1 {
  &m2; #using '&' sigil will pass @_ implicitly.
  m2; #not passed implcitly
    
}
m1(1,2,3);
#returning values---------------------------------------------------------------
sub ret_scal {return "boo";}
my $scal_var = ret_scal;
print Dumper \$scal_var;

sub ret_arr {return (1,2,3);}
my @arr_var =ret_arr;
print Dumper \@arr_var;











