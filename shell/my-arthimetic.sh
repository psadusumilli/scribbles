#!/bin/sh
echo -n "enter no: "
read x
echo "x*3 = $[x*3]"
echo "x+3 = $[x+3]"
echo "x-3 = $[x-3]"
echo "x/3 = $[x/3]"
echo "x%2 = $[x%2]"

#arithmetic
x=12
y=13

z0=`expr $x + $y`
z1=$((x+y))
z2=$[x+y]
echo  $x+$y is expr $z0, $z1, $z2

z3=$((++x))
z4=$((y++))
echo pre=$z3, post=$z4

echo '45/5'|bc
