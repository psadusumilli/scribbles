
a = (1..10)
p a

enum = a.to_enum
loop do
 puts enum.next
end

enum2 = a.each_slice(3)
loop do
 p "#{enum2.next}, "
end

#Condensed form
enum3 = (1..10).enum_for(:each_slice, 3)
p enum3.to_a

enum4 = Enumerator.new do |yielder|
 count = 1
 number = 0
 loop do
  number += count
  count += 1
  yielder.yield number 
 end
end
5.times{puts enum4.next}

class Playlist

 def initialize()
 # @songs = ["S1","S2","S3","S4"]
  @songs =(1..10)
  @enum = @songs.to_enum
 end

 def each
  loop do
    yield @enum.next
  end
 end

end

p = Playlist.new()
p.each{|song| puts "Playing #{song}"}

