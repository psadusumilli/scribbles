title("object prototyping")
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

/* creates a new object which prototype is person */
var person1 = {  kind: 'person1'}
var zack1 = Object.create(person1)
console.log('zack1 kind='+zack1.kind+" & its prototype 'person1'=") // => ‘person1’
console.log(Object.getPrototypeOf(zack1))
