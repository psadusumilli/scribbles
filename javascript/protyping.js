//Define a functional object to hold persons in javascript
//Add dynamically to the already defined object a new getter
var Person = function(name) {this.name = name;};
Person.prototype.getName = function() {return this.name;};
var john = new Person("John");
alert(john.getName()); //works

Person.prototype.sayMyName = function() {alert('Hello, my name is ' + this.getName());};
john.sayMyName(); //works

//-----------------
var Customer = function(name) {
    this.name = name;
};

//Now I link the objects and to do so, we link the prototype of Customer to 
//a new instance of Person. The protype is the base that will be used to 
//construct all new instances and also, will modify dynamically all already 
//constructed objects because in Javascript objects retain a pointer to the 
//prototype
Customer.prototype = new Person();     
var myCustomer = new Customer('Dream Inc.');
myCustomer.sayMyName();//works

//If I add new methods to Person, they will be added to Customer, but if I
//add new methods to Customer they won't be added to Person. Example:
Customer.prototype.setAmountDue = function(amountDue) {this.amountDue = amountDue;};
Customer.prototype.getAmountDue = function() {return this.amountDue;};

myCustomer.setAmountDue(2000);
alert(myCustomer.getAmountDue()); //
john.setAmountDue(1000); //error thrown




