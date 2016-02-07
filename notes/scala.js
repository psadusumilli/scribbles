1)
// type scala in terminal to start interpreter
scala> 4 + 5 
res0: Int = 9

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
2) 
//'val' keyword maps to final in java
// type is inferred by scala compiler, 
scala> val msg="hey bro"                                                    
msg: java.lang.String = hey bro

scala> println(msg)
hey bro

scala> msg="hey sis"
<console>:6: error: reassignment to val
       msg="hey sis"
          ^
scala> val msg2: java.lang.String = "Hello again, world!"
scala> val msg3: String = "Hello yet again, world!"

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
3) 
//var is for variables
scala> var msg3="hey dude"
msg3: java.lang.String = hey dude

scala> msg3="hey gal"
msg3: java.lang.String = hey gal

scala> println(msg3)
hey gal
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
4)
//function definition and usage.  no explicit return statements 
scala> def max(x:Int, y:Int):Int = {
     | if (x>y) x
     | else y
     | }
max: (x: Int,y: Int)Int

scala> max(8,4)
res4: Int = 8

scala> def max2(x: Int, y: Int) = if (x > y) x else y //no braces if body is single line, return type can also be inferred but a must for recursive functions.

scala> def greet() = println("hey bug") 
greet: ()Unit

scala> greet
hey bug
 	
//Unit is for void methods
scala> def printArgs(args: Array[String]): Unit = {
args.foreach(println)
}

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
5)
//to quit intepreter
scala> :quit 
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
6) 
//create file hello.scala
$ scala hello.scala
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------method names not operator overloading 
1+2 --> (1).+(2) . ie '+' is the method name of Int class wherin you pass 2 as argument.
curses(0) --> curses.apply(0). any variable followed by parentheses is actually apply()
curses(0) = "hi" --> curses.update(0, "hi")
Array("hi", "ciao") --> Array.apply("hi","ciao")

List members are immutable unlike Java
val list1 = List("A","B","C")
val list2 = List("D","E")
val list3 = list1 ::: list2  --> is actually list2.::(list1) , note its the method of  right operand now. Thumb rule is anytin ending with colon ( ::, ::: ) belongs to right operand. 

method arguments are vals, you cannot reassign them inside a function.


 	





















