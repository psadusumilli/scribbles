package com.vijayrc.impliciter
/*implicits are like C# extension functions, must be declared inside 'objects' just like static imports in java*/

/*class - must be inside a scope where method definitions are allowed (not at top level)*/
object Test1 extends App{
  implicit class RichInt(n: Int){
    def min(m: Int): Int = if (n <= m) n else m
  }
  val r = RichInt(2) //auto-generated constructor by implicit
  val r2:RichInt = 2 //auto-generated constructor by implicit
  assert(2 == r.min(4))
  assert(1 == r2.min(1))
}

/*method, augmenting string type with implied extra functionality */
trait MyString{}
object Test2 extends App{
  implicit def xx(s:String) =
    new MyString{
      def length = s.length
      def getChar(i: Int) = s.charAt(i)
    }
  assert('u' == "sun".getChar(1))
}

/*method - implicit conversion happening when dollar passed to euro 'add' function */
class Dollar(val value:Double){
}
class Euro(val value:Double){
  def add(euro:Euro) = {value+euro.value}
}
object Dollar{
  implicit def toEuro(dollar:Dollar) = {new Euro(dollar.value*0.8)}
}
object Test3 extends App{
  val euro = new Euro(2)
  val dollar = new Dollar(3)
  assert(4.4 == euro.add(dollar))
}

