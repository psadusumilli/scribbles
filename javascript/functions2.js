title("functions 2")

/*caller/callee properties of arguments------------------------------------------------*/
var factorial = function(x) {
	if (x <= 1) return 1;
	return x * arguments.callee(x-1);
};
console.log("3!="+factorial(3))

/*function properties -----------------------------------------------------------------*/
uniqueInteger.counter = 0;
function uniqueInteger() { // function definition like this creates a object uniqueInteger in global namespace
	return uniqueInteger.counter++; // Increment and return counter property
}
console.log(uniqueInteger()+"|"+uniqueInteger())
/*closure ------------------------------------------------------------------------------*/
var scope = "global scope";
function checkscope() {
	var scope = "local scope";
	function f() { return scope; }
	return f();
}
console.log("closure:"+checkscope())// => "local scope"
/*defining and invoking simultaneously--------------------------------------------------*/
var uniqueInteger2 = (function() {
	var counter = 0; // given to nested function via closure (scope chains)
	return function() { return counter++; };
}());
console.log(uniqueInteger2()+"|"+uniqueInteger2())
/*length-number of parameters - arity---------------------------------------------------*/
function check(args) {
	var actual = args.length; 
	var expected = args.callee.length; 
	if (actual !== expected) 
		throw Error("Expected " + expected + "args; got " + actual);
}
/*call/apply----------------------------------------------------------------------------*/
var f = function(x){console.log(x+"|"+this.y)}
var a = {y:"a"}
f.call(a,"call")
f.apply(a,["apply"])//arguments passed as array. good if a function is defined to accept an arbitrary number of arguments,

/*bind----------------------------------------------------------------------------------*/
function f1(y) { return this.x + y; } // This function needs to be bound
var o = { x : 1 }; // An object we'll bind to
var g = f1.bind(o); // Calling g(x) invokes o.f(x)
console.log("bind:"+g(2)) // => 3

/*Function Constructor------------------------------------------------------------------*/
var f = new Function("x", "y", "return x*y;");
console.log("func:"+f(3,2))
/*higher order function------------------------------------------------------------------*/
/*takes functions as args, works on them*/
function not(f){
	return function(){
		return 2+f.apply(this,arguments)	
	}	
}
var f2 = function(x){return x+1}
console.log("not:"+not(f2)(4))//=>7

/*memoization. ----------------------------------------------------------------------------
The code below shows a higher-order function, memoize() that accepts a function as its argument and
returns a memoized version of the function:*/
function memoize(f) {
	var cache = {}; // Value cache stored in the closure.
	return function() {
		var key = arguments.length + Array.prototype.join.call(arguments,",");
		if (key in cache) return cache[key];
		else return cache[key] = f.apply(this, arguments);
	};
}







