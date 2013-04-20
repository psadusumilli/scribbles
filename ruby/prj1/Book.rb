class Book

 attr_accessor :isbn
 attr_accessor :author  
 attr_accessor :title    

 def initialize(title, author, isbn)
   @title = title
   @author = author
   @isbn = isbn
 end
 
 def to_s
    "Title:#{@title}|ISBN:#{@isbn}|Author:#{@author}"
 end
 
end
