
arr = %w{ A B C D E F }
p arr

puts "arr[0]= #{ arr[0]}"
puts "arr[1..3]=#{arr[1..3]}"
puts "arr[-3..-1]=#{arr[-3..-1]}"

arr[6] = "G"
puts "After adding arr[6]:#{arr}" 

arr[8]= "J"
puts "After adding arr[8]:#{arr}"

arr[7] = ['H','I']
puts "After adding arr[7]=[H I]: #{arr}"
puts "arr[7] = #{arr[7]}"

arr[1,2] = ['B1','C1']
puts "After arr[1,2] = ['B1','C1']: #{arr}"

puts "arr.first(3) = #{arr.first(3)}" 
puts "arr.last(3) = #{arr.last(3)}"

b = [[11,12,13],[21,22,23],[31,32,33]]
p b 
puts "b[1][2] = #{b[1][2]}"
b.each do |a,b,c|
 puts "|#{a} #{b} #{c}|"
end  

c=[1,2,3,4,5,6,7]
p c
puts "First Number > 5 = #{c.find{|n| n > 5}}" 
puts "Numbers > 5 = #{
           c.collect do |n|
                if(n > 5)
                  n
                end 
            end
     }" 

puts "Sum: #{ c.inject(0){|sum,n| sum + n}}"
puts "Product: #{c.inject(1){|product,n| product * n}}"
