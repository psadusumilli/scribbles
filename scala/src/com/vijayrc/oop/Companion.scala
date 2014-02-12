package com.vijayrc.oop

class Companion {
  def m1{println("m1"); Companion.M1}
  private def m2{println("m2")}
}
class Stranger{
  def m3{print("m3"); Companion.M1}
}
object Companion {
  val c = new Companion
  def M1{println("M1"); c.m1 ; c.m2}
}
object Stranger{
  val c = new Companion
  def M2{println("M2");c.m1} //c.m2 not possible
}
