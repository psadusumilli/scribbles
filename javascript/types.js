console.log("<h4 style='color:cornflowerblue;margin-bottom:-1%'>Types</h4>")
var x; 
x = 0; 
x 

//dynamic typing
x = 1; 
x = 0.01; //just one number type for integers and reals.
x = "hello world"; 
x = 'JavaScript'; 
console.log("x="+x)
x = true; 
x = false; 
console.log("x="+x)

//object
var book = { 
	topic: "JavaScript", 
	fat: true 
};
console.log("obj:book[topic="+book.topic+"|fat="+book["fat"]+"]")
book.author = "Flanagan"; 
book.contents = {}; 

//array
var primes = [2, 3, 5, 7]
console.log("array:"+primes[0]+"|"+primes.length) 
primes[4] = 9; 
primes[4] = 11;
console.log("array:"+primes[4]+"|"+primes.length) 

var a = [];
a.push(1,2,3); 
a.reverse();
console.log("array:"+a[0]+"|"+a.length) 

//arrays and objects can hold other arrays and objects:
var points = [ 
	{x:0, y:0}, 
	{x:1, y:1}
];
var data = { 
	trial1: [[1,2], [3,4]], 
	trial2: [[2,3], [4,5]] 
};
console.log("array of objects|"+points[1].x)//=>1
console.log("object of arrays|"+data.trial1[1][1])//=>4