package com.vijayrc.macros

object MacroUser extends App{
  import AllMacros._
  val x = 23
  hello()
  printparam("one second")
  debug(x)
  printf("hello %s!", "world")

}
