title("objects")



//prototypes
/* creates a new object which prototype is person */
var p1 = {}
console.log(Object.getPrototypeOf(p1))
var p2 = new Object()
console.log(Object.getPrototypeOf(p2))

var person1 = {  kind: 'person1'}
var zack1 = Object.create(person1)
console.log('zack1 kind='+zack1.kind+" & its prototype 'person1'=") // => ‘person1’
console.log(Object.getPrototypeOf(zack1))

//__proto__
var alien = {  kind: 'alien'}
var person = {  kind: 'person'}
var zack = {}
zack.__proto__ = alien
console.log('zack kind='+zack.kind) //=> ‘alien’
zack.__proto__ = person
console.log('zack kind='+zack.kind) //=> ‘person’

/**updating obj property will not affect its proto, but proto will affect obj property*/
zack.kind='zack'
console.log('zack kind='+zack.kind) //=> ‘zack’
console.log('person kind='+person.kind) //=> ‘person’

//delete
console.log("delete")
var obj = { x: 1, y: 2, z:{ z1:3, z2:4 } }; 
delete obj.x; 
console.log("x" in obj); // => false
delete obj.z.z2
console.log(obj)
//deleting inherited properties not possible,can be done on the owning parent only
var parent  = {a:1, b:2}
obj.__proto__= parent
console.log(Object.getPrototypeOf(obj))
delete(obj.a)
console.log(parent)
console.log(obj.a)
delete(parent.a)
console.log(obj.a)



