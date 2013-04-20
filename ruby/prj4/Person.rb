class Person
 
 include Comparable
 attr_reader :name

 def initialize(name)
  @name = name
 end

 def <=>(other)
   self.name <=> other.name
 end  
 
 def to_s
  "#{@name}"
 end

end

p1 = Person.new("Kenny")
p2 = Person.new("Stan")
p3 = Person.new("Stan")

puts "p1 > p3 : #{p1 > p3}"
puts "p1 < p2 : #{p1 < p2}" 
puts [p1, p3, p2].sort()
