#!/bin/bash
function pocket(){
	dir=$HOME/.pocket
	index=$dir/.index
	menu=$dir/.menu

	if [ ! -e $dir ]; then mkdir $dir; fi	
	if [ ! -e $index ]; then echo "total:0" >  $index; fi

    case $1 in 
    	        "find" ) 
			##########check if empty index##########
			last_page_no=$(head -1 $index | grep -E '[0-9]+' -o)
			if [ $last_page_no = '0' ]
			then 
				echo -e "\033[93mno pages yet! \033[36m{$ pocket add}"
				return
			fi	
			##########search and open########## 
			grep -E -i $2 $index | grep -E '(.+?)\|' -o | sed 's/|//g' > $menu 
			grep --color=always -E '[0-9]*' $menu
			
			echo -e "#\033[90m--------------------------------------------------------#"
			echo -ne "\033[36mselect page\033[39m ::> "
			read page_no
			page=$( grep -E ^$page_no:::: $menu | sed 's/[0-9]*:::://g')
			echo -e "\033[93mopening $page ..."
			open -a Google\ Chrome $page
			;;
		"add" )
			echo -ne "\033[93mpage|tags \033[36m{'http://vijayrc.com|code,tech,blog'}\033[39m ::>  "
			read new_page_and_tags			 
			new_page=$(echo $new_page_and_tags | grep -E '.*\|' -o | sed 's/\|//')			

			##########check if exists##########
			exists=`grep ::::$new_page $index -c`
			if [ ! $exists == '0' ] 
			then 
				echo -ne "\033[93mpage already present, want to continue? \033[36m[y|n]\033[39m: "
				read ok_to_add
				if [ ! $ok_to_add = "y" ]; then return ; fi 
			fi

			##########get page no##########
			last_page_no=$(head -1 $index | grep -E '[0-9]+' -o)
			new_page_no=$[last_page_no+1]

			##########download page content##########
			echo -ne "\033[93mpage content? \033[36m[y|n]\033[39m: "	
			read download
			if [ $download = "y" ]
			then 
				new_page_content=$(curl -s $new_page | tr -d '\n|\t| \*')				
				echo "$new_page_no::::$new_page_and_tags,$new_page_content" >> $index			
			else
				echo "$new_page_no::::$new_page_and_tags" >> $index
			fi

			##########clean up##########
			sed -i.bak "s/^ *//; s/ *$//; /^$/d; s/total:$last_page_no/total:$new_page_no/" $index
			rm -f $index.bak
			echo -e "\033[93mtotal=$new_page_no, added $new_page"
			;;
		"list" ) 
			echo -e "\033[90m#--------------------------------------------------------#"
			cat $index | grep -E '(.+?)\|' -o | sed 's/|//g' | grep --color=always -E '[0-9]*'
			;;	
		"backup" ) 
			now=$(date +"%k:%M:%S_%m-%d-%Y")
			cp $index $index.$now
			;;	
		"*" ) echo -ne "\033[93musage pocket find|add"
		
	esac
}
