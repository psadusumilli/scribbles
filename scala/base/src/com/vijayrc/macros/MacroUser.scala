package com.vijayrc.macros

object MacroUser extends App{
  import AllMacros._
  val x = 23
//  hello()
//  printparam("one second")
//  debug(x)
//  printf("hello %s!", "world")


  //myif(4>1, {println("yes");println("ok")},{println("no")})
  //myif2(4>1, Map("then"->print("y"), "else"->print("n")))

  myif3(4>1, {case "then" => print("y") case "else" => print("n")})
  myif3(1>4, {case "then" => print("y") case "else" => print("n")})
  myif3(4>1, {case "else" => print("n")})
  myif3(1>4, {case "then" => print("y")})

}
