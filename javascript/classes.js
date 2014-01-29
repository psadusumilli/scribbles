title("classes")

M = function(x,y){
	var v1 = 5
	this.v2 = 10

	this.m0 =  function(){return x+y}
	this.m1 = function(){return v1*(x+y)}
	this.m2 = function(){return this.v2*(x+y)} //this.v2 ('this' reqd)
}

M.prototype.p1 = function() {return this.v2*(x+y)};

var m = new M(1,2)

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

//-----------------------------------------------------------------------

//-----------------------------------------------------------------------

//-----------------------------------------------------------------------