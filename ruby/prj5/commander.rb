
class Commander

 def run()
   command = `echo $PATH`
   puts "Command = [#{command}]"
 end

end

c = Commander.new()
c.run()
