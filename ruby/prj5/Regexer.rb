
class Regexer
 
 def find()
   line = ARGV[0]
   regex = ARGV[1]
   puts  "#{line} #{regex}"
   matches = line.scan(regex)

   if matches.empty?
     p "No matches found"
     return
   end 
   puts "Matches for #{regex} in #{line} = #{matches.inspect}"
 end 

end

rg = Regexer.new()
rg.find()
