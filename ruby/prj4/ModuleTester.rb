require 'Modular'

M1.say("Vijay")
M2.say("Vijay")

class ModuleUser
 
 include M3
 
 def to_s
   "I am a Module Tester Class"
 end

end

mu = ModuleUser.new()
mu.say('Rekha')
mu.who_am_i()
#mu.what_module()
