module M1
 def M1. say(name)
   puts "M1 says hi to #{name}"
 end
end

module M2
 def M2.say(name)
   puts "M2 says Hi to #{name}"   
 end
end

module M3
 mod_name = "M3 Basha"
 def say(name)
   puts "#{self.class.name} says Hi to #{name}"
 end

 def who_am_i()
   puts "#{self.to_s()}"   
 end

 #Wont work
 def what_module()
   puts "#{M3::mod_name}"
 end
end

