title("functions")

var x
var y = 3 
function plus1(x) { return x+1; }
console.log("plus:"+plus1(y)) // => 4
var square = function(x){ return x*x; }
console.log("square:"+square(plus1(y))) //=>16

/*functions joined with objects are methods  ---------------------------------------------------------------------------------------------*/
var obj={name:'o1'}
obj.say = function(){return this.name} //=>this refers to scope of obj
console.log("obj method:"+obj.say())


/*scope of variables  -------------------------------------------------------------------------------------------------------------------------------*/
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

/*global variables deleting  ------------------------------------------------------------------------------------------------------------------------*/
var truevar = 1; // A properly declared global variable, nondeletable.
fakevar = 2; // Creates a deletable property of the global object.
this.fakevar2 = 3; // This does the same thing.
delete truevar // => false: variable not deleted
delete fakevar // => true: variable deleted
delete this.fakevar2 // => true: variable deleted

/*prototyping -------------------------------------------------------------------------------------------------------------------------------------------*/
/*Define a functional object to hold persons in javascript. Add dynamically to the already defined object a new method print()*/
title("function prototyping")
var Person1 = function(name) {this.name = name;this.age=23};
Person1.prototype.print = function() {console.log('name=' + this.name+'|age='+this.age);};

var myPerson1 = new Person1("John")
myPerson1.print()//works
console.log("John's obj proto=")
console.log(myPerson1.__proto__)
/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Linking objects:
Now I link the objects and to do so, we link the prototype of Person2 to a new instance of Person. 
The protype is the base that will be used to construct all new instances and also, will modify dynamically all already 
Constructed objects because in Javascript objects retain a pointer to the prototype */

var Person2 = function(name) { this.name = name;}
Person2.prototype = new Person1()     
var myPerson2 = new Person2('Peter')
myPerson2.print();//works with 'age' pulled from Person1

//If I add new methods to Person1, they will be added to Person2, but if I add new methods to Person2 they won't be added to Person1.
Person2.prototype.setGender = function(gender) {this.gender = gender;}
Person2.prototype.getGender = function() {return this.gender;}

myPerson2.setGender('male')
console.log('name='+myPerson2.name+'|gender='+myPerson2.getGender()) //work
//myPerson1.setGender('female')//error thrown

/*weird prototyping-----------------------------------------------------------------------------------------------------------------*/
var f = function(x){
	this.a1 = 1
	var ax2 =2
	console.log("f=>"+x+"|this.a1="+this.a1+"|ax2="+ax2); 
}
f("called as a simple variable")//=>called as a simple variable|f.a1=1 (a1 belongs to f object)

//2| adding another property to function object 
f.x3 = 2
f.y = function(x){console.log("f.y=>"+x+"|this.a1="+this.a1+"|this.x3 ="+this.x3)}//ax2 not in scope
f.y("called as a property of a function object")//=>called as a property of a function object

//3|using Object to get real prototype
var p = Object.getPrototypeOf(f)
console.log("prototype of f=>")
console.log(p)//=>function Empty() {}
p.z = function(x){console.log("f.z=>"+x+"|this.a1="+this.a1)}//ax2 not in scope
p.z("called from parent")//f.z=>called from parent|f.a=undefined
f.z("called from child")//f.z=>called from child|f.a=undefined

//4| prototype keyword is useless without new
f.prototype.z1 = function(x){console.log("f.z1=>"+x+"|this.a1="+this.a1)}//fails silently,ax2 not in scope
console.log(f.prototype)//=>{}
try{
	f.z1("called as inherited function")//does not work! 
}catch(e){console.log("using protoype keyword is useless without 'new' keyword")}

//5| using 'new' keyword
var x = new f("called from a new object from f") //=>13
console.log(x)//=>{a:1}
f.prototype.z2 = function(x){console.log("f.z2=>"+x+"|this.a1="+this.a1)}//ax2 not in scope 
x.z2("called as a inherited function")// =>called as a inherited function|f.a=1

/*scopes-----------------------------------------------------------------------------------------------------------------*/
var o = { 
	m: function() { 
			var self = this; 
			console.log("scope-this:"+(this === o)); //=>"true": this is the object o.
			f(); 
			function f() { 
				console.log("scope-this:"+(this === o)); //=> "false": this is global or undefined
				console.log("scope-this:"+(self === o)); //=> "true": self is the outer this value.
			}
		}
};
o.m()

/*params------------------------------------------------------------------------------------------------------*/
/* funtion param types and presence not checked */
function s(x1,x2){console.log("params:x1="+x1+"|x2="+x2)}
s(); s(1);s(1,2)

//arguments keyword - its similar to an array with params but does not support many methods
function sum(){
	if(arguments.length == 0) return;
	var sum = 0
	for(i in arguments) sum += arguments[i]
	return sum;	
}
console.log("arguments-sum="+sum(12,2,3,44))




