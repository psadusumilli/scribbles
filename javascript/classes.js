title("classes")

//#1 simple function call
function f1(){
	return this.v0
}
f1.v0 = 1;
console.log("f1|simple function call="+f1()) //=>undefined
this.v0 = 2 //=> global scope
console.log("f1|simple function call="+f1()) //=>2
/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
//#2 method call on objects 
function f2(z){
	return this.x+this.y+z
}
var obj={x:1,y:2}
obj.m = f2; //obj becomes invocation context
console.log("f2|simple method call="+obj.m(3))//=>6
console.log("f2|call()="+f2.call(obj,3))//=>6
console.log("f2|apply()="+f2.apply(obj,[3]))//=>6
var f2b = f2.bind(obj)
console.log("f2|bind call="+f2b(3))//=>6

/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
//#3 - function constructors and prototypes
M = function(x,y){
	var v1 = 5
	this.v2 = 10
	this.m0 =  function(){return x+y}
	this.m1 = function(){return v1*(x+y)}
	this.m2 = function(){return this.v2*(x+y)} //this.v2 ('this' reqd)
	console.log("M called: v1="+v1+"|v2="+this.v2)
}
M.v3 = 9
M.m3 = function(){return this.v3}//x, y, v2 cannot come here (m and M are not related)

var n = M.prototype
n.v4= 15
n.m4 = function() {return this.v4*this.v2; } //local x, y, v1 cannot be reached, v3=undefined, belongs to M object
/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

var m = new M(1,2) //=>M called: v1=5|v2=10
//-----------------------------------------------------------------------
console.log("v1|local variable="+m.v1) //=>undefined
//-----------------------------------------------------------------------
console.log("v2|obj property="+m.v2) //=>10
//-----------------------------------------------------------------------
console.log("m0|simple method call with constructor params="+m.m0())//=>3
//-----------------------------------------------------------------------
console.log("m1|method call with constructor params and local variable="+m.m1())//=>15 v1 available via scope chain
//-----------------------------------------------------------------------
console.log("m2|method call with constructor params and object property="+m.m2())//=>20 v2 is bound to the new object 
//-----------------------------------------------------------------------
console.log("m|function object=")
console.log(m)
//-----------------------------------------------------------------------
console.log("m|Object.getPrototypeOf(m)=")
console.log(Object.getPrototypeOf(m))
console.log("M|M.prototype=")
console.log(M.prototype)
console.log("m4|inherited method call with owned and inherited object properties="+m.m4())//=>150
//-----------------------------------------------------------------------
console.log("M.m3|method call on M object="+M.m3())//=>9







//-----------------------------------------------------------------------

//-----------------------------------------------------------------------
