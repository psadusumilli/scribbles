stack = []
stack.push "Kenny"
stack.push "Kyle"
stack.push "Cartmany"
p stack

stack.pop
stack.pop
puts "After 2 pops: #{stack}"

queue = []
queue.push "Kenny"
queue.push "Kyle"
p queue
queue.unshift "Cartman"
puts "After unshift: #{queue}"
queue.shift
queue.shift
puts "After 2 shifts: #{queue}"

