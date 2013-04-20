b = ->(x) { print "#{x} "}
4.times{|x|  b.call x }
puts " "
0.upto(10) {|x| print "#{x} "}
puts " "
0.step(12,3) {|x| print "#{x} "}
puts " "
for i in 1..3
 print "#{i} " 
end
puts " "

i = 0
loop do
 i +=1
 next if i< 3
 print "#{i} "
 redo if i == 5
 break if i >10
end
puts " "


