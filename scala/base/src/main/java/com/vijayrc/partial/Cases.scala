package com.vijayrc.partial

object Cases extends App{
  def append(first:List[Int], second:List[Int]):List[Int] = {
    first match{
      case List() => second
      case head::tail => head::append(tail,second)
    }
  }
  val result = 1 :: 2 :: 3 :: 4 :: 5 :: 6 :: Nil
  assert(result == append(List(1,2,3),4::5::6::Nil))

  /*0 - simple anonymous function without case*/
  List(1, 2, 3).map(x=>x+1).foreach(print)//234
  println()

  /*1 - simple match case */
  val x = "name" match {
    case i:String => i+" is a string"
    case _ => "unknown"
  }
  println(x)

  /*2 - cases in partial functions/exceptions.
  Partial function unlike a total function, can certain arguments depending on given pre-conditions*/
  val list: List[Any] = List(41, "cat", 32f)
  try{
     list map {
       case i:Int ⇒ i + 1
       case i:String => i + "s"
     }
  } catch {
     case e:MatchError => println("match error") //32f float
     case e:Exception => println("generic error"+e.getMessage)
  }

  //'collect' does not throw exception for "cat" as it uses 'isDefinedAt'
  val ints: List[Int] = list collect {case i: Int ⇒ i + 1}
  println(ints)

  /*3 -partial function on a map */
  var map = Map[Int,String](1->"stan",2->"kenny",3->"kyle",4->"cartman")
  map.foreach{case(i,s) => println(i+"->"+s)} //partial function, see flower brackets
  map.foreach(print)
  println()

  /*4 - explicit trait*/
  val f1 = new PartialFunction[Int, String] {//will take only Int, and returns String
    def apply(d: Int) = 42 / d  +""
    def isDefinedAt(d: Int) = d != 0
  }
  def f2: PartialFunction[Int, Int] = { case d: Int if d != 0 ⇒ 42 / d }
  def f3: PartialFunction[Any, Int] = { case i: Int ⇒ i + 1 }

  println(f1.isDefinedAt(0)+"|"+f1.isDefinedAt(3)+"|"+f1(3))//false|true|14
  println(f2.isDefinedAt(0)+"|"+f2.isDefinedAt(3)+"|"+f2(3))//false|true|14
  println(f3.isDefinedAt(0)+"|"+f3.isDefinedAt(3)+"|"+f3(3))//true|true|4

  val f = (x:PartialFunction[Any,Int]) => x.isDefinedAt(0) + "|" + x.isDefinedAt(3) + "|" + x(3)
  println(f(f3)) //using 'f' to avoid the repetition above

  /*5*/
  //general anonymous function notation
  val family = (x:String) =>  x match { case "rekha" => "wife" case "shravan" => "son" case _ => ""}
  //partial function in long form
  val family1:PartialFunction[String, String] =  { case "rekha" => "wife" case "shravan" => "son" case _ => ""}
  //partial function in short form
  val family2:String  => Int =  { case "rekha" => 1 case "shravan" => 2 case _ => -1}

  assert("wife" == family("rekha"))
  assert("wife" == family1("rekha"))
  assert(1 == family2("rekha"))

  /* @ operator */
  val o: Option[Int] = Some(2)
  o match {
    case Some(x) => println(x) //2
    case None =>
  }
  o match {
    case x @ Some(_) => println(x) //Some(2)
    case None =>
  }
}
