#!/bin/bash
function pocket(){
	if [ ! -e .pocket ] 
	then 
		touch .pocket;
		echo "new .pocket";
	fi	
	grep $1 .pocket | grep -E '(.+?)\|' -o | sed 's/|//g' | tee .pocket-menu
	echo "#--------------------------------------------------------#"
	echo -n "enter ur choice: "
	read choice
	page=$(grep $choice .pocket-menu | sed 's/[0-9]*:://g')
	echo "opening $page ..."
	open -a Google\ Chrome http://$page
}

pocket news;