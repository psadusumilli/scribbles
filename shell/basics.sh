	
echo "start dude on `date`"
echo $?
#----------------------------------------------------------------------------------------------------
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
#----------------------------------------------------------------------------------------------------
#read input
echo "give fname and lname:"
read fname lname
echo "hey! $fname $lname"

#----------------------------------------------------------------------------------------------------
#conditionals
if test $x -lt $y -a  -d "/home/vijayr"
    then echo "x < y and dir exists"
else 
    echo "x > y"
fi

if test "vijay" = "vijay"
    then echo "same vijay names"
elif ["rek"="rek"]
    then echo "same rek names"
fi

echo "enter no1:"
read x
echo "enter no2:"
read y

if test $x -lt $y
 then echo "more"
elif [ $x -gt $y ]
 then echo "less"
else
 echo "equals"
fi
#----------------------------------------------------------------------------------------------------
#loops
echo "enter number for multiplication table"
read number
for i in 1 2 3 4 5 6 7 8 9
do 
    echo "$number * $i = $[number*i]"
done
for ((i=1;i<10;i++))
do
    echo "$number * $i = $[number*i]"
done
i=1
while [ $i -le 10 ]
do
    echo "$number * $i = $[number*i]"
    i=$[i+1]
done
#----------------------------------------------------------------------------------------------------
echo "files modified in the last one hour 1/24 = 0.041667"
find . -mtime -0.041666667 -print






