package com.vijayrc.collect

import org.scalatest.FunSuite

class ListTest extends FunSuite{

  test("basics of list"){
      val myList = 1::2::3::Nil
      assert(1 == myList.head)
      assert(List(2,3) == myList.tail)
  }

}
