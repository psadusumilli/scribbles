require 'csv'
require_relative 'Book' 

class Library

 def initialize()
   @books = []
 end

 def add(book)
    @books << book
 end

 def books
   puts "Books:"
   @books.each{|book| puts book}
   line
 end

 def read(file)   
   CSV.foreach(file, headers: true) do |row| 
      @books << Book.new(row['title'],row['author'],row['isbn'])
     end   
 end

 public
 def byAuthor(author)
   @authorbooks = []
   @books.each do |book|
     if(book.author == author)
 	@authorbooks << book
     end
    end
    puts "Books by author #{author}"
    @authorbooks.each{|book| puts book }
    line
 end

 private
 def line()
   puts "------------------------------------------------"
 end

end
