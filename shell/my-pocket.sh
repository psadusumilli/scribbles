#!/bin/bash
function pocket(){
	if [ ! -e .pocket ] 
	then 
		touch .pocket;
		echo "new .pocket";
	fi	
	grep $1 .pocket | grep -E '(.+?)\|' -o | sed 's/|//g' | tee .pocket-menu
	echo -n "enter ur choice: "
	read choice
		grep $choice .pocket-menu

}

pocket news;