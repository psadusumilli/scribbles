require 'csv'
require_relative 'Book'
require_relative 'Library'

class Tester

 def run()
   library = Library.new()   
   library.add(Book.new('100 days of Solitude','Marquis Garcia','isbn1'))
   library.books()
 end

 def run2()  
  library = Library.new()
  library.read('file1.csv')
  library.books 
  library.byAuthor('Kafka')
 end 


end

test = Tester.new()
test.run()
test.run2()
