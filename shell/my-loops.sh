	
#----------------------------------------------------------------------------------------------------
#conditionals - AND logic
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

echo -n "enter no1:"
read x
echo -n "enter no2:"
read y
if test $x -lt $y
 then echo "more"
elif [ $x -gt $y ]
 then echo "less"
else
 echo "equals"
fi

if [ $(id -u) != "0" ] 
    then echo "not a super user"
else 
    echo "super user"
fi
#----------------------------------------------------------------------------------------------------
#loops
echo -n "enter number for multiplication table: "
read number
for i in 1 2 3 4 5 6 7 8 9
do 
    echo "$number * $i = $[number*i]"
done
for ((i=1;i<10;i++))
do
    echo "$number * $i = $[number*i]"
done
count=0
for i in $(cat ./files/names); do
    count=$((count + 1))
    echo "Word $count ($i) contains $(echo -n $i | wc -c) characters"
done
#----------------------------------------------------------------------------------------------------
i=1
while [ $i -le 10 ]
do
    echo "$number * $i = $[number*i]"
    i=$[i+1]
done
i=1
until [ $i -ge 10 ]
do
    echo "$number * $i = $[number*i]"
    i=$[i+1]
done




