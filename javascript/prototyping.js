/*Define a functional object to hold persons in javascript. Add dynamically to the already defined object a new method print()*/
console.log("<h3 style='color:cornflowerblue'>Function  Prototyping</h3>")
var Person1 = function(name) {this.name = name;this.age=23};
Person1.prototype.print = function() {console.log('name=' + this.name+'|age='+this.age);};

var myPerson1 = new Person1("John");
myPerson1.print(); //works
/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Linking objects:
Now I link the objects and to do so, we link the prototype of Person2 to a new instance of Person. 
The protype is the base that will be used to construct all new instances and also, will modify dynamically all already 
Constructed objects because in Javascript objects retain a pointer to the prototype */

var Person2 = function(name) { this.name = name;};
Person2.prototype = new Person1();     
var myPerson2 = new Person2('Peter');
myPerson2.print();//works with 'age' pulled from Person1

//If I add new methods to Person1, they will be added to Person2, but if I add new methods to Person2 they won't be added to Person1.
Person2.prototype.setGender = function(gender) {this.gender = gender;};
Person2.prototype.getGender = function() {return this.gender;};

myPerson2.setGender('male');
console.log('name='+myPerson2.name+'|gender='+myPerson2.getGender()); //works
myPerson1.setGender('female'); //error thrown




