
class BlockHead
 
 def accept(&block)
  @block = block
 end

 def run(param)
  @block.call(param)
 end

 def give()
   return @block   
 end

end

bh = BlockHead.new()
bh.accept{|param| puts "Param given is #{param}"}
bh.run('Vijay')

b0 = bh.give()
b0.call('Rekha')

b1 = lambda{|param| puts "Called with #{param}"}
b1.call 100
b1.call "Cat"

b2 = ->(param1, param2) {puts "b2 Called with #{param1} and #{param2}"}
b2.call "Vijay", "Rekha"

b3 = -> a,*b, &block do 
 puts "a=#{a.inspect}"
 puts "b=#{b.inspect}"
 block.call 'Vijay'
end
b3.call(1,2,3,4) {|name| puts "Hi #{name}"}
