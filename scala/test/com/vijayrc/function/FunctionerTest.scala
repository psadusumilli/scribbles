package com.vijayrc.function

import org.scalatest.FunSuite

class FunctionerTest extends FunSuite{
  val functioner = new Functioner

  test("curse someone"){
    val curse: String = functioner.say("stupid")
    assert(curse == "Hey stupid fucker")
  }

  test("call function literal"){
    val filtered: List[Int] = functioner.filter(List(1, 2, 3, 4, 5, 6, 7))
    filtered.foreach(x => print(x+","))
  }




}
