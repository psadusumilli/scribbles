package com.vijayrc

class Basics {

  //Any (~Object in Java) is subdivided into AnyVal and AnyRef
  //AnyVal has Int,Double,Float,Boolean,Char,Byte
  //AnyRef has String
  def valueTypes(){
      println(42.equals(23))
      assert (43 == (42 max 43))

  }

  def conditional(x:Int):Unit = {
    if (x  > 0){
      var i = 0
      while ( i < x ) {
        print( i + ",")
        i += 1
      }
    }else{
      println("enter a +ve no")
      println(
        """|I am a "RAW" string
          |so me 'BAD'""".stripMargin)//triple double-quote pairs can have pack any raw string
    }

    //functional conditionals
    val x2 = if(x>=0)x else -1*x
    print("\nx2 is "+x2)
  }

  def looping(args:Array[String]):Unit = {
    args.foreach(arg=>print(arg+","))
    println("")
    args.foreach((arg: String) => print(arg+","))
    println("")
    args.foreach(print)
    println("")

    for(arg <- args) //note that arg is a val
      print(arg+",")
    for(i <- 1 to 4) print(i)
    println("")
    for(i <- 1 until 4) print(i)

  }

  def array():Unit = {
    val curses = new Array[String](3)
    curses(0)= "stan"
    curses(1)="kyle"
    curses(2)="cartman"
    for ( i <- 0 to 2) print(curses( i )+" ")

    val greets = Array("hi", "ciao")
    greets.foreach(greet => print(greet+","))
  }

  def list():Unit = {
    val list1 = List("A","B","C")
    val list2 = List("D","E")
    val list3 = list1 ::: list2
    val list4 = "Z" :: list1
    list3.foreach(print)
    list4.foreach(print)

    val list5 = 1::2::3::Nil
    println(list5(2))

    list3.drop(2).foreach(print)
    println("")
    list3.dropRight(2).foreach(print)
    println("")
    println(list1.exists(a=>a  == "B"))
    list5.filter(a=>a > 2).foreach(print)
    println("")
    println(list3.forall(a=>a.endsWith("S")))
    println("")
    list3.map(a=>a+"1").foreach(print)
    println("")
    list3.filter(a=>a == "A").foreach(print)
    println("")
  }

  def set():Unit = {
    var set1 = Set("spitfire", "b52") //HashSet ("spitfire", "b52") in case there is a need to avoid default scala implementation
    set1 += "cessna"
    set1.foreach(a => print(a+", "))
    println("")

    val set2 = scala.collection.mutable.Set("bike", "car")
    set2.foreach(a => print(a+", "))
    println("")
  }

  def tupler():Unit = {
    val tuple = (99, "mama")
    println(tuple._1+", "+ tuple._2.toUpperCase())
  }

  def map():Unit = {
    var map1 = Map[Int, String]()
    map1 += (1-> "a1") // invoking method -> on (1) which returns tuple ( 1, "a1") , then method on map (map1).+=(1,"a1")
    map1 += (2-> "a2") // -> method is available on any object.
    map1 += (3-> "a3")
    println(map1(2))

    val romanNumeral = Map(1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV", 5 -> "V")
    println(romanNumeral(4))
  }

  //note the closures here
  def asserter():Unit = {
    def link(args: Array[String]): String = args.mkString("--")
    assert(link(Array("A","B")) == "A--B" )

    def addplus(x:String) = x+"--"
    val chars = List("A","B","C")
    chars.map(a=> addplus(a)).foreach(print)
  }

  def methodAsOperator(){
    println()
    println("I'm a string".indexOf("m"))
    println("I'm a string" indexOf "s") //indexOf is now like a operator
    println("he he" toUpperCase) // no empty () needed, this is indicate methods with no arguments and with side-effects
  }

  def except(){
    var r = 0
    try{
        val a = Array(1,2)
        r =  a(1)
        r =  a(2)
    }catch {
      case ex:IndexOutOfBoundsException => print("check the index")
      case ex:Exception => print("generic error")
    }finally {
       r = 99
    }
    print("r is "+r)
  }

  def matcher(gadget:String):String = {
      gadget match{
        case "laptop" => "mouse"
        case "phone" => "handsfree"
        case "tv" => "hdmi"
        case _ => "wtf?"
      }
  }


}
