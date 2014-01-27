title("objects")

/*prototypes_________________________________________________________________________*/
/* creates a new object which prototype is person */
var p1 = {}
console.log(Object.getPrototypeOf(p1))
var p2 = new Object()
console.log(Object.getPrototypeOf(p2))

var person1 = {  kind: 'person1'}
var zack1 = Object.create(person1)
console.log('zack1 kind='+zack1.kind+" & its prototype 'person1'=") // => ‘person1’
console.log(Object.getPrototypeOf(zack1))

/*__proto_____________________________________________________________________________*/
/*updating obj property will not affect its proto, but proto will affect obj property*/
var alien = {  kind: 'alien'}
var person = {  kind: 'person'}
var zack = {}
zack.__proto__ = alien
console.log('zack kind='+zack.kind) //=> ‘alien’
zack.__proto__ = person
console.log('zack kind='+zack.kind) //=> ‘person’
zack.kind='zack'
console.log('zack kind='+zack.kind) //=> ‘zack’
console.log('person kind='+person.kind) //=> ‘person’

/*delete_____________________________________________________________________________*/
/*deleting inherited properties not possible,can be done on the owning parent only */
console.log("delete")
var obj = { x: 1, y: 2, z:{ z1:3, z2:4 } }; 
delete obj.x; 
console.log("x" in obj); // => false
delete obj.z.z2
console.log(obj)

var parent  = {a:1, b:2}
obj.__proto__= parent
console.log(Object.getPrototypeOf(obj))
delete(obj.a)
console.log(parent);console.log(obj.a)
delete(parent.a)
console.log(obj.a)

delete Object.prototype;// not possible
var x = 1; 
delete this.x; //not possible
function f() {}
delete this.f; //not possible

/*obj methods_____________________________________________________________________________*/
var obj={name:'o1',age:3}
obj.say = function(){return this.age} //=>this refers to scope of obj
obj.print = function(){console.log("obj method:name="+this.name+"|age="+this.age)}
console.log("obj method:"+obj.say())
obj.print()

/*getters|setters - 2 point to polar coordinates____________________________________________________*/
var point = {
	x : 1, 
	y : 2,
	get r() {return Math.sqrt(this.x*this.x+this.y*this.y);},
	set r(newvalue) {
		var oldvalue = Math.sqrt(this.x*this.x + this.y*this.y);
		var ratio = newvalue/oldvalue;
		this.x *= ratio;
		this.y *= ratio;
	},
	get theta() { return Math.atan2(this.y, this.x); }//readonly
}
point.print = function(){console.log("point: x="+this.x+"|y="+this.y)}	
point.print()
console.log("point.r="+point.r)
point.r = 4
point.print()
console.log("point.theta="+point.theta)

/*object attributes___________________________________________________________________________*/
var o = {};
Object.defineProperty(o, "x", { value : 1,writable: true,enumerable: false,configurable: true});
console.log("attr: o.x="+o.x+"|"+Object.keys(o))// check that the property is there but is nonenumerable => 1| [] 

Object.defineProperty(o, "x", { writable: false });// modify the property x so that it is read-only
o.x = 2;// fails silently or throws TypeError in strict mode
console.log("attr: o.x="+o.x)//=>1

Object.defineProperty(o, "x", { value: 2 });// the property is still configurable, so we can change its value like this:
console.log("attr: o.x="+o.x)// => 2

Object.defineProperty(o, "x", { get: function() { return 0; } });// Now change x from a data property to an accessor property
console.log("attr: o.x="+o.x)// => 0









