package com.vijayrc.collect

import org.scalatest.FunSuite

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

}
