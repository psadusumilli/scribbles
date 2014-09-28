funtion todo(){
  #index=$HOME/.todo
  index=.todo
  if [-e $index]; then touch $index ; fi
 
  case $1 in 
	"sort" )
       		 
