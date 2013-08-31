package com.vijayrc.collect

import org.scalatest.FunSuite

/**
 * Created by vchakrav on 8/30/13.
 */
class MyListTest extends FunSuite {

  test("list elements pattern matching"){
      val list = List(1,2,3,4)
      val a::b::c = list
      assert(a == 1)
      assert(b == 2)
      assert(c == List(3,4))
  }

  test("test append"){
    val myList = new MyList
    assert(1::2::3::4::5::6::Nil == myList.append(List(1,2,3),4::5::6::Nil))
  }

}
