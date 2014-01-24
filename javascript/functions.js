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