function pocket(){
	grep $1 .pocket | grep -E '(.+?)\|' -o | sed 's/|//g'
}

pocket news;