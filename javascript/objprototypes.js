title("object prototyping")
var alien = {  kind: 'alien'}
var person = {  kind: 'person'}
var zack = {};
zack.__proto__ = alien
console.log('zack kind='+zack.kind); //=> ‘alien’
zack.__proto__ = person
console.log('zack kind='+zack.kind); //=> ‘person’
