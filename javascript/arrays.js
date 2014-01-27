

title("arrays")
var a = []
a[0] = 1; a[1] = "one"
console.log(a)

/*array is just special object, the following is still valid */
a[-1.23] = true; // This creates a property named "-1.23"
a["1000"] = 123; // This the 1001st element of the array
a[1.000] // Array index 1. Same as a[1]
console.log("a['1000']="+a["1000"]+"|a[-1.23]="+a[-1.23])	

/*truncating arrays_______________________________________________________________________________________________*/
console.log("truncating..")
a = [1,2,3,4,5]; 
a.length = 3; // a is now [1,2,3].
console.log(a)
a.length = 0; // Delete all elements. a is [].
a.length = 5; // Length is 5, but no elements, like new Array(5)

Object.defineProperty(a, "length", {writable: false});

a = [1,2,3,4,5];
delete a[2] //makes a[2] null
console.log("after delete:a.length="+a.length)
console.log(a)

/*push|pop|unshift|shift ___________________________________________________________________________________________________________*/
a = []; // Start with an empty array
a.push("zero") // Add a value at the end. a = ["zero"]
a.push("one", "two") // Add two more values. a = ["zero", "one", "two"]
a.pop()
console.log("push/pop:"+a.join("|"))

a = []; // a:[]
a.unshift(1); // a:[1] Returns: 1
a.unshift(22); // a:[22,1] Returns: 2
a.shift(); // a:[1] Returns: 22
a.unshift(3,[4,5]); // a:[3,[4,5],1] Returns: 3
a.shift(); // a:[[4,5],1] Returns: 3
a.shift(); // a:[1] Returns: [4,5]
a.shift(); // a:[] Returns: 1


/*looping ________________________________________________________________________________________________________*/
a = [1,2,3,4,5];
a["b"] = "b"
//loop with i is preferred as forin will print non-index obj properties also
for(i=0;i<a.length;i++){
	if( i in a){//check if index exists for sparse arrays
		console.log("loop1:"+a[i])
	}
}	
for(i in a){
	console.log("loop2:"+a[i])
}
/*sort ___________________________________________________________________________________________________________*/
a = ['ant', 'Bug', 'cat', 'Dog']
a.sort(); // case-sensitive sort: ['Bug','Dog','ant',cat']
console.log("sort1:"+a.join("|"))
a.sort(function(s,t) { // case-insensitive sort
	var a = s.toLowerCase();
	var b = t.toLowerCase();
	if (a < b) return -1;
	if (a > b) return 1;
	return 0;
});
console.log("sort2:"+a.join("|"))
/*concat/slice/splice______________________________________________________________________________________________*/
a.concat([4,5],[6,7]) // Returns [1,2,3,4,5,6,7]
a.concat(4, [5,[6,7]]) // Returns [1,2,3,4,5,[6,7]]

a = [1,2,3,4,5];
a.slice(0,3); // Returns [1,2,3]
a.slice(3); // Returns [4,5]
a.slice(1,-1); // Returns [2,3,4]
a.slice(-3,-2); // Returns [3]

//unlike concat, slice, splice alters the same array 
var a = [1,2,3,4,5,6,7,8];
a.splice(4); // Returns [5,6,7,8]; a is [1,2,3,4]
a.splice(1,2); // Returns [2,3]; a is [1,4]
a.splice(1,1); // Returns [4]; a is [1]

/*ecma5 methods______________________________________________________________________________________________*/
//forEach
var data = [1,2,3,4,5];
var sum = 0;
data.forEach(function(value) { sum += value; });
console.log("foreach: sum="+sum)
data.forEach(function(v, i, a) { a[i] = v + 1; });
console.log("foreach: data="+data)

//map
a = [1, 2, 3];
b = a.map(function(x) { return x*x; });// b is [1, 4, 9]

//filter
a = [5, 4, 3, 2, 1];
smallvalues = a.filter(function(x) { return x < 3 });// [2, 1]
everyother = a.filter(function(x,i) { return i%2==0 }); // [5, 3, 1]
var dense = a.filter(function() { return true; });//skips null/undefined, so easy way to dense

//every
a = [1,2,3,4,5];
a.every(function(x) { return x < 10; })// => true: all values < 10.
a.every(function(x) { return x % 2 === 0; }) // => false: not all values even.

//some
a = [1,2,3,4,5];
a.some(function(x) { return x%2===0; })// => true a has some even numbers.
a.some(isNaN)// => false: a has no non-numbers

//reduce/reduceRight (works from right to left)
a = [1,2,3,4,5]
var sum = a.reduce(function(x,y) { return x+y }, 0);// Sum of values
var product = a.reduce(function(x,y) { return x*y }, 1); // Product of values
var max1 = a.reduce(function(x,y) { return (x>y)?x:y; }); // Largest value
console.log("reduce:"+sum+"|"+product+"|"+max1)

Array.prototype.x = "x is x"
console.log("a.x|"+a.x)











