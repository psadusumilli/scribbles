package com.vijayrc.function

import org.scalatest.FunSuite

class FunctionerTest extends FunSuite{
  val functioner = new Functioner

  test("curse someone"){
    val curse: String = functioner.local("stupid")
    assert(curse == "Hey stupid fucker")
  }

  test("call function literal"){
    val list: List[Int] = List(1, 2, 3, 4, 5, 6, 7)
    val filtered: List[Int] = functioner.objects(list)
    filtered.foreach(x => print(x+","))
    assert(28==functioner.closure(list))
  }

  test("should join strings"){
    assert("stan|cartman|kyle|kenny|" == functioner.repeatedArg("|","stan","cartman","kyle","kenny"))
    assert(1 == functioner.recursion(0))
    assert(1 == functioner.recursion(1))
    assert(6 == functioner.recursion(3))
  }




}
