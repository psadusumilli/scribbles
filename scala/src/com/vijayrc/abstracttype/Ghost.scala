package com.vijayrc.abstracttype

trait Ghost {
  type T
  val initial:T
  var current:T
  def transform(x:T):T
}
