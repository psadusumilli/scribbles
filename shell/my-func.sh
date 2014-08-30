function hello(){
	echo "say hello"
}

function say() {
	echo "hey dude $1"
	#hello; 
}
say "sweet"

true || echo "echo1"
false || echo "echo2"
true && echo "echo3"
false && echo "echo4"
