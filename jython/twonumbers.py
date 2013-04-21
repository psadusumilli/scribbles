class twonumbers:

     def __init__(self, x, y):
        self.x = x
        self.y = y

     def add(self):
         return self.x + self.y

     def multiply(self):
         return self.x * self.y

     def loop(self):
     	i=0
     	while i <= self.x:
     		print i
     		i=i+1
     	print "----------------"	
     	for j in range(self.y):
     		print j

     def ask(self):
     	yourname = raw_input("say ur name: ")
     	print "yo %s" %(yourname)		
     	val = input("please provide an expression: ")
     	print val



