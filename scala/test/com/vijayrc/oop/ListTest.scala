package com.vijayrc.oop

import org.scalatest.FunSuite
import scala.collection.mutable.ListBuffer

class ListTest extends FunSuite{

  test("basics of list"){
      val myList = 1::2::3::4::5::Nil
      assert(1 == myList.head)
      assert(List(2,3,4,5) == myList.tail)
      assert(5::4::3::2::1::Nil == myList.reverse)
      assert(5 == myList.last)
      assert(1::2::3::4::Nil == myList.init)
      assert(5 == myList.length)

      assert(1::2::Nil == (myList take 2))
      assert(3::4::5::Nil == (myList drop 2))
      assert(myList splitAt 2 equals (1::2::Nil,3::4::5::Nil))//tuple of 2 lists
      assert(3 equals myList.apply(2))
      assert(3 equals myList(2))

      println("a"::"b"::"c"::"d"::"e"::Nil zip myList)
  }

  test("higher order functions"){
    val boys = "cartman"::"kyle"::"stan"::"kenny"::Nil

    val capBoys: List[String] = boys.map(boy => boy.toUpperCase())
    println(boys.mkString(",")+"|"+capBoys)

    var sum=0
    boys foreach(sum += _.length)
    println("sum="+sum)

    println(boys.map(_.length+4))
    println(boys.flatMap(_.toUpperCase))
    println((1::2::4::8::Nil).flatMap(_::9::Nil))
    println(List.range(3,6).flatMap(x=> List.range(1,x)).mkString("|"))

    println("filter:",List.range(1,9).filter(_% 2 == 0))
    println("partition:",List.range(1,9).partition(_% 2 == 0))
    println("forall:",List.range(1,9).forall(_ > 0))

    println("takeWhile:",List.range(9,-30,-3) takeWhile(_ > 0)) //unlike filter, stops collecting the moment condition fails
    println("dropWhile:",List.range(9,-30,-3) dropWhile(_ > 0))

    println("folding left:", ("southpark:" /: boys)(_+"|"+_))
    println("folding right:", (boys :\ "southpark")(_+"|"+_))

  }

  test("list buffer"){
    val buffer = new ListBuffer[Int]
    buffer += 1
    buffer += 3
    val buffer2 = 4 +: buffer
    println(buffer.toList, "|", buffer2.toList)
  }

}
