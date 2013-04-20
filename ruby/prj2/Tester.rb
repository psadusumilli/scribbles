require 'test/unit'
require_relative 'WordCounter'

class TestHasher < Test::Unit::TestCase
  
 def test_empty_input
   wc = WordCounter.new()
   assert_equal("No string entered", wc.count(nil))    
 end

 def test_output_for_line
   wc = WordCounter.new()
   map = { 'is' => 2, 'a' => 1}
   assert_equal(map, wc.count('is a is'))
 end

end
