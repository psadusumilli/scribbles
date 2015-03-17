package com.vijayrc.macros

import org.scalatest.FunSuite
import com.vijayrc.macros.AllMacros._

object MacroUser extends App{
  import AllMacros._
  val x = 23
//  hello()
//  printparam("one second")
//  debug(x)
//  printf("hello %s!", "world")


  //myif(4>1, {println("yes");println("ok")},{println("no")})
  //myif2(4>1, Map("then"->print("y"), "else"->print("n")))

  myif(4>1, {case then => print("y") case otherwise => print("n")})
  myif(1>4, {case then  => print("y") case otherwise => print("n")})
  myif(4>1, {case then  => print("n")})
  myif(1>4, {case then  => print("y")})

}

class MacroTest extends FunSuite{
  test("macro should work when both 'then & 'else are given"){
    myif(3>1, {case then => print("ok") case otherwise => print("oops")}) //ok
    myif(1>3, {case otherwise => print("oops") case then => print("ok")}) //oops
  }
  test("macro should work when either 'then or 'otherwise given"){
    myif(3>1, {case then => print("ok")}) //ok
    myif(3>1, {case otherwise => print("oops")})//oops
  }
}