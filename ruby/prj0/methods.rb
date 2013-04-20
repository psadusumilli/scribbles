#E1
def greet(name)
 puts "Hey #{name.capitalize}"
 yield('S1')
 yield('S2')
end
greet('Stan'){|statement|puts "Down with #{statement}"}

#E2
boys = %w{Stan Kenny Cartman Kyle}
boys.each{|boy| puts "#{boy} is a southpark 4th grader"}


#E3
5.times{print "*"}
3.upto(10) {|i| print i} 
puts ""                        

