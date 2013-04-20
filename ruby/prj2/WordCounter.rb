
class WordCounter  
  def start()
     count(ARGV[0])
  end

  def count(str)    
    map = Hash.new(0) 
    if(str.nil?)
       return  "No string entered"
    end
    str.scan(/[\w']+/).each do |word|
       map[word] +=1    
    end
    return map
  end
end

wc = WordCounter.new()
p wc.start()

