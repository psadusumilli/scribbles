class Employee
 
 attr_reader :dept 
 attr_writer :name
 attr_accessor :manager

 def salary=(new_salary)
   @salary = new_salary
 end
 
 def initialize(name, salary, dept, manager)
   @name = name
   @salary = Float(salary)
   @dept = dept	
   @manager = manager
 end

 def to_s
   "Name:#{@name}|Salary:#{@salary}|Dept:#{@dept}|Manager:#{@manager}"
 end

 def name
   @name
 end


 def increment=(bonus)
   @salary = @salary+bonus
 end

 def decrement=(cut)
   @salary = @salary-cut
 end

end

e1 = Employee.new('Vijay', 3500, 'Dev','Moron1')
e2 = Employee.new('Rekha', 4500, 'BA','Moron2')
e3 = Employee.new('Prithvi', 6500, 'BA','Moron1')

p e1
puts e2
puts e3.name
puts e3.dept

e2.salary = 5000
puts e2
e2.increment = 1000
puts "After increment: #{e2}"
e2.decrement = 500
puts "After decrement: #{e2}"


e1.manager='Moron3'
puts e1
