package com.vijayrc.abstracttype

trait Ghost {
  type T
  val initial:T
  var current:T
  def transform(x:T):T
}
class Real extends Ghost{
  type T = String
  val initial = "Hi"
  var current = "there"
  def transform(x:String) = x+x
}
