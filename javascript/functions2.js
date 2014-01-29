title("functions 2")

/*caller/callee properties of arguments-----------------------------------------------------------------------------*/
var factorial = function(x) {
	if (x <= 1) return 1;
	return x * arguments.callee(x-1);
};
console.log("3!="+factorial(3))

/*function properties ------------------------------------------------------------------------------------------------------*/
uniqueInteger.counter = 0;
function uniqueInteger() { // function definition like this creates a object uniqueInteger in global namespace
	return uniqueInteger.counter++; // Increment and return counter property
}
console.log(uniqueInteger()+"|"+uniqueInteger())
/*closure -------------------------------------------------------------------------------------------------------------------------*/
var scope = "global scope";
function checkscope() {
	var scope = "local scope";
	function f() { return scope; }
	return f();
}
console.log("closure:"+checkscope())// => "local scope"
/*defining and invoking simultaneously-------------------------------------------------------------------------------*/
var uniqueInteger2 = (function() {
	var counter = 0; // given to nested function via closure (scope chains)
	return function() { return counter++; };
}());
console.log(uniqueInteger2()+"|"+uniqueInteger2())














