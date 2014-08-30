#!/bin/bash
function pocket(){
	dir=$HOME/.pocket
	index=$dir/.index
	menu=$dir/.menu
	downloaded=$dir/.downloaded

	#create new files if non-existent
	if [ ! -e $dir ]
	then mkdir $dir
	fi	
	if [ ! -e $index ] 
	then touch $index
	fi
	rm -f $index.bak

	#clean up index file
	sed -i.bak 's/^ *//; s/ *$//; /^$/d' $index

	#switch as per commands find|add|delete
    case $1 in 
    	
    	#find and open page
    	"find" ) 
			if [ ! -s $index ]
			then 
				echo "no pages yet|$ pocket add"
				return
			fi	
			grep -E $2 $index | grep -E '(.+?)\|' -o | sed 's/|//g' | tee $menu
			echo "#--------------------------------------------------------#"
			echo -n "select page ::>"
			read page_no
			page=$(grep $page_no $menu | sed 's/[0-9]*:://g')
			echo "opening $page ..."
			open -a Google\ Chrome $page
			;;

		#add a new page
		"add" )
			echo -n "please enter page|tags {'vijayrc.com|code,tech,blog'} ::>  "
			read new_page_and_tags
			 
			new_page=$(echo $new_page_and_tags | grep -E '.*\|' -o | sed 's/\|//')			
			#new_page_content=$(curl -s $new_page | tr -d '\n|\t| \*')				
			last_page_no=$(tail -n 1 $index | grep -E '[0-9]{1,5}::' -o | sed 's/:://')
			new_page_no=$[last_page_no+1]

			#echo "$new_page_no::$new_page_and_tags [ $new_page_content ]" >> $index			
			echo "$new_page_no::$new_page_and_tags" >> $index
			sed -i.bak 's/^ *//; s/ *$//; /^$/d' $index
			echo "added $new_page"
			;;
		#usage instructions	
		"*" ) echo "usage pocket find|add"
			;;
	esac
	rm -f $index.bak
}

pocket find emotional;