#!/bin/bash
function pocket(){
	dir=$HOME/.pocket
	index=$dir/.index
	menu=$dir/.menu
	
	#create new files if non-existent
	if [ ! -e $dir ]
	then 
		mkdir $dir
	fi	
	if [ ! -e $index ] 
	then 
		touch $index
	fi

	#clean up index file
	sed -i.bak 's/^ *//; s/ *$//; /^$/d' $index

	#switch as per commands find|add|delete
    case $1 in 
    	#find and open page
    	"find" ) 
			if [ ! -s $index ]
			then 
				echo "no pages yet! :: $ pocket add"
				exit 0;
			fi	
			grep $2 $index | grep -E '(.+?)\|' -o | sed 's/|//g' | tee $menu
			echo "#--------------------------------------------------------#"
			echo -n "select page :: "
			read page_no
			page=$(grep $page_no $menu | sed 's/[0-9]*:://g')
			echo "opening $page ..."
			open -a Google\ Chrome http://$page
			;;
		#add a new page	
		"add" )
			echo -n "please enter url|tags :: 'vijayrc.com|code,tech,blog' :: "
			read newpage

			#pick last number and add entry to index
			tail -n 1 .pocket | grep -E '[0-9]*' -o 
			#read and save html
			#tr -d '\n' < index.html > index.html.2
			

			echo "added $newpage"
			;;
		#usage instructions	
		"*" ) echo "usage pocket find|add"
	esac
	rm $index.bak
}

pocket find news;