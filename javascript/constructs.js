title("constructs")
var x=0,y=0,z=1
if ((x == 0 && y == 0) || !(z == 0)) 
	console.log("x="+x+"|y="+y+"|z="+z)

/*falsy values that evaluate to false (undefined,null,0,-0,NaN,"" )*/
var a = 4, b = 2
if (a == 4)
	b = b + 1;
else
	a = a + 1;
console.log("a="+a+"|b="+b)