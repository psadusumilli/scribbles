console.log("<h4 style='color:cornflowerblue;margin-bottom:-1%'>Object  Prototyping</h4>")
var alien = {  kind: 'alien'}
var person = {  kind: 'person'}
var zack = {};
zack.__proto__ = alien
console.log('zack kind='+zack.kind); //=> ‘alien’
zack.__proto__ = person
console.log('zack kind='+zack.kind); //=> ‘person’
