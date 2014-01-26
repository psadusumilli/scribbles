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

//for loop
console.log("for-loop-1")
for(var i = 0, j=10; i < 10; i++,j--) 
	console.log(i*j);

console.log("for-loop-2")
var a = [1,2,3,4]
for(var p in a) 
	console.log(a[p]);

//while loop
console.log("while-loop")
var i=1
while(i<=4){
	console.log(i)
	i++
}

//switch
console.log("switch")
var decide = function(x){
	switch(x){
		case 1: console.log("1");break;
		case 2: console.log("2");break;	
		case 3: console.log("3");break;	
		default: console.log("X");break;
	}
}
decide(2)
decide(12)
	
//in-operator
console.log("in-operator")
var point = { x:1, y:1 }; 
var a1= "x" in point // => true
var a2 = "z" in point // => false
var a3 = "toString" in point // => true: object inherits toString method
console.log(a1+"|"+a2+"|"+a3)

var data = [0,1,2]; 
a1 = "0" in data // => true
a2 = 1 in data // => true
a3 = 3 in data // => false
console.log(a1+"|"+a2+"|"+a3)

var d = new Date(); 
var t = d instanceof Date;	
console.log("instance of ="+t)

//eval
console.log("eval")
var geval = eval; // Using another name does a global eval
var x = "global", y = "global";

function f() { 
	var x = "local"; 
	eval("x += '-changed';"); 
	return x; 
}
function g() { 
	var y = "local"; 
	geval("y += '-changed';"); 
	return y; 
}
console.log(f()+"|"+x); // Local variable changed: prints "local-changed global":
console.log(g()+"|"+y); // Global variable changed: prints "local global-changed":

var a = [1,2,3]; 
delete a[2]; 
console.log(a.length+"|"+a[2]) // => 3|undefined: 


//try-catch-throw
console.log("error")
function minus(x){
	try{
		if(x<0) throw new Error("x is -ve")
	}catch(e){
		console.log(e.toString())
	}finally{
		console.log("all done")
	}
}
minus(-2)







