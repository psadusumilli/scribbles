#!/bin/bash
function pocket(){
	if [ ! -e .pocket ] 
	then 
		touch .pocket;
		echo "new .pocket";
	fi	
	
    case $1 in 
    	"find" ) 
			grep $2 .pocket | grep -E '(.+?)\|' -o | sed 's/|//g' | tee .pocket-menu
			echo "#--------------------------------------------------------#"
			echo -n "enter choice: "
			read choice
			page=$(grep $choice .pocket-menu | sed 's/[0-9]*:://g')
			echo "opening $page ..."
			open -a Google\ Chrome http://$page
			;;
		"add" )
			echo -n "please enter url|tags ::'vijayrc.com|code,tech,blog': "
			read newpage
			echo "added $newpage"
			;;
		"*" ) echo "usage pocket find|add"
	esac
}

pocket add news;