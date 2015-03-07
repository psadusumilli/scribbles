#!/bin/sh
echo "start dude on `date`"
echo $?
#----------------------------------------------------------------------------------------------------
#read input
echo "give fname and lname:"
read fname lname
echo "hey! $fname $lname"
#----------------------------------------------------------------------------------------------------
#positional
echo "positional args: "
echo "w0=> " $0
echo "w1=> " $1
echo "w2=> " $2
echo "w3=> " $3
#----------------------------------------------------------------------------------------------------
#$@ contains list of arguments
for i in $@; do
    echo $i
done