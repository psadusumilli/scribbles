#!/bin/bash
function todo(){
  #index=$HOME/.todo
  index=.todo
  if [ -e $index ]; then touch $index ; fi

  case $1 in 
	"sort" )
		echo "sort"	
	;;
	"list" )
		cat $index
	;;
	"*" )
		echo "enter a valid command"
	;;

   esac
}	
	      		 
