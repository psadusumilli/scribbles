console.log("<h4 style='color:cornflowerblue;margin-bottom:-1%'>Functions</h4>")
var x
var y = 3 
function plus1(x) { return x+1; }
console.log("plus:"+plus1(y)) // => 4

var square = function(x){ return x*x; }
console.log("square:"+square(plus1(y))) //=>16