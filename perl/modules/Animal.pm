package Animal;

sub makeNew {
  my ($invocant,$name)=@_;
  my $object = {};
  $object->{name}=$name;
  bless($object, $invocant);
  return $object;
}

sub speak {
 my ($obj)=@_;
 my $name = $obj->{name};
 print "$name growls\n";
}
1;

