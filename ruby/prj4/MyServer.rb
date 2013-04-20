require 'gserver'

class MyServer < GServer
  
 def initialize
   super(12345)	  
 end

 def serve(client)
  client.puts read_from_file  
 end
 
 private
 def read_from_file()
  lines = []
  File.open("/home/vijayrc/work/ruby/prj4/file.log").each do |line| 
   lines << line
  end
  return lines
 end
 
end

myS = MyServer.new
myS.start.join


