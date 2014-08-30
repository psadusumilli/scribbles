#!/bin/bash
function pocket(){
	dir=.pocket
	index=$dir/.index
	menu=$dir/.menu

	if [ ! -e $dir ]; then mkdir $dir; fi	
	if [ ! -e $index ]; then touch $index; fi

	sed -i.bak 's/^ *//; s/ *$//; /^$/d' $index
	rm -f $index.bak

    case $1 in 
    	"find" ) 
			if [ ! -s $index ]
			then 
				echo "no pages yet|$ pocket add"
				return
			fi	
			grep -E -i $2 $index | grep -E '(.+?)\|' -o | sed 's/|//g' > $menu 
			grep --color=always -E '[0-9]*' $menu
			echo "#--------------------------------------------------------#"
			echo -ne "\033[36mselect page\033[39m ::> "
			read page_no
			page=$(grep $page_no $menu | sed 's/[0-9]*:://g')
			echo "opening $page ..."
			open -a Google\ Chrome $page
			;;
		"add" )
			echo -ne "enter page|tags \033[36m{'http://vijayrc.com|code,tech,blog'}\033[39m ::>  "
			read new_page_and_tags			 
			new_page=$(echo $new_page_and_tags | grep -E '.*\|' -o | sed 's/\|//')						
			last_page_no=$(tail -n 1 $index | grep -E '[0-9]{1,5}::' -o | sed 's/:://' | head -1)
			new_page_no=$[last_page_no+1]

			echo -n "want to add page content? [y|n]: "	
			read download
			if [ $download = "y" ]
			then 
				new_page_content=$(curl -s $new_page | tr -d '\n|\t| \*')				
				echo "$new_page_no::$new_page_and_tags,$new_page_content" >> $index			
			else
				echo "$new_page_no::$new_page_and_tags" >> $index
			fi

			sed -i.bak 's/^ *//; s/ *$//; /^$/d' $index
			echo "added $new_page"
			;;
		"list" ) 
			echo "#--------------------------------------------------------#"
			cat $index | grep -E '(.+?)\|' -o | sed 's/|//g' | grep --color=always -E '[0-9]*'
			;;	
		"backup" ) 
			now=$(date +"%k:%M:%S_%m-%d-%Y")
			cp $index $index.$now
			;;	
		"*" ) echo "usage pocket find|add"
		
	esac
}
