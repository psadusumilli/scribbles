title("functions")
//function is a object
var x
var y = 3 
function plus1(x) { return x+1; }
console.log("plus:"+plus1(y)) // => 4

var square = function(x){ return x*x; }
console.log("square:"+square(plus1(y))) //=>16

//functions joined with objects are methods
var obj={name:'o1'}
obj.say = function(){return this.name} //=>this refers to scope of obj
console.log("obj method:"+obj.say())

/*JavaScript’s function scope means that all variables declared within a function are visible
throughout the body of the function. Curiously, this means that variables are even
visible before they are declared. This feature of JavaScript is informally known as hoisting:
JavaScript code behaves as if all variable declarations in a function (but not any
associated assignments) are “hoisted” to the top of the function.*/
var g1 = "g1"
var g2 = "g2"
function scopetest() {
	var i = 0;
	console.log("g1="+g1+"|g2="+g2)//=> g1=undefined|g2=g2  
	//so global is overridden by local g1 declaration hoisted to top of function, global g2 is not touched
	if (true) {
		var j = 21; 
		var g1 = "local-g1"
		for(var k=0; k < 2; k++) { 
			console.log("k inside loop="+k); //=>0-2
		}
		console.log("k outside loop="+k); //=>2
	}
	console.log("outside 'if' block j="+j); //=>21
	console.log("g1="+g1+"|g2="+g2) //g1=local-g1|g2=g2
}
scopetest()

//global variables deleting
var truevar = 1; // A properly declared global variable, nondeletable.
fakevar = 2; // Creates a deletable property of the global object.
this.fakevar2 = 3; // This does the same thing.
delete truevar // => false: variable not deleted
delete fakevar // => true: variable deleted
delete this.fakevar2 // => true: variable deleted

