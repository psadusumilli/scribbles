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
/*updating obj property will not affect its proto, but proto will affect obj property.
 __proto__ is not supported in certain browsers, then use Object.create()*/
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

var p = Object.defineProperties({}, {
	x: { value: 1, writable: true, enumerable:true, configurable:true },
	y: { value: 1, writable: true, enumerable:true, configurable:true },
	r: {get: function() { return Math.sqrt(this.x*this.x + this.y*this.y) },enumerable:true,configurable:true}
});
console.log("attr:p=")
console.log(p)
console.log("attr:p.x property desc=")
console.log(Object.getOwnPropertyDescriptor(p,"x"))

/* class attribute_______________________________________________________________________________________
no direct way to access the attribute, only through the .toString method*/
function classof(o) {
	if (o === null) return "Null";
	if (o === undefined) return "Undefined";
	return Object.prototype.toString.call(o).slice(8,-1);
}
function f(){}
console.log("class of :"+classof(3)+"|"+classof("3")+"|"+classof(new f()))

/* extensible attribute__________________________________________________________________________________
specifies whether new properties can be added to the object or not.*/
var o = {x:1}
Object.preventExtensions(o)//can be called only once on a obj and cannot be undone
o.y = 2 
console.log("ext: x="+o.x+"|o.y="+o.y+"|"+Object.isExtensible(o))//=>ext: x=1|o.y=undefined|false
Object.seal(o) //makes o non-extensible, also makes its properties non-configurable and non-deletable
delete o.x
console.log("ext: x="+o.x+"|"+Object.isSealed(o))
Object.freeze(o)//tighter than seal, now properties are made readable only
o.x = 3
console.log("ext: x="+o.x+"|"+Object.isFrozen(o))

/*serialisation _________________________________________________________________________________________*/
o = {x:1, y:{z:[false,null,""]}}; 
s = JSON.stringify(o); //parses only enumerable properties
console.log("serial:"+s)// s is '{"x":1,"y":{"z":[false,null,""]}}'
p = JSON.parse(s);// p is a deep copy of o
console.log(p)
 









