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
myPerson1.setGender('female')//error thrown





