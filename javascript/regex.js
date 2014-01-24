title("regex")
var text = "testing: 1, 2, 3"; // Sample text
var pattern = /\d+/g // Matches all instances of one or more digits, regex expression between slashes /regex/
pattern.test(text) // => true: a match exists
console.log("regex:"+text.search(pattern)) // => 9: position of first match
console.log("regex:"+text.match(pattern)) // => ["1", "2", "3"]: array of all matches
