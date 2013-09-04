package com.vijayrc.abstracttype


class Real extends Ghost{
  type T = String
  val initial = "Hi"
  var current = "there"
  def transform(x:String) = x+x

}
