require 'open-uri'

class Downloader

 def initialize(url)
    @url = url
 end

 def start(file)
    file_content = File.open(file,"w")
   begin
    web_content = open(@url)
    while line = web_content.gets
      file_content.puts line
    end    
    file_content.close
   rescue Exception
    puts "Exception occurred #{$!}"
    file_content.close
    File.delete(file)
    raise
   end
 end

 def catch()
   catch (:done) do
     (1..10).each do |x|
        print "#{x} "  
         throw :done if x == 5            
     end
   end
 end
 
end

downloader = Downloader.new('http://google.com')
#downloader.start('google.html')
downloader.catch()




