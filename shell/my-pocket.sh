function pocket(){
	if [ ! -e .pocket ] 
	then 
		touch .pocket;
		echo "new .pocket";
	fi	
	grep $1 .pocket | grep -E '(.+?)\|' -o | sed 's/|//g'
}

pocket news;